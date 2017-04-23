package com.jeeplus.modules.daikin.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 
 * @author datas
 *
 */
public class TestRtf {
 private int inext = 0;

 /**
  * 字符串转换为rtf编码
  * 
  * @param content
  * @return
  */
 public String strToRtf(String content) {
  char[] digital = "0123456789ABCDEF".toCharArray();
  StringBuffer sb = new StringBuffer("");
  byte[] bs = content.getBytes();
  int bit;
  for (int i = 0; i < bs.length; i++) {
   bit = (bs[i] & 0x0f0) >> 4;
   /*
    * 增加中文支持思路：通过getBytes获取的中文的assii小于0，根据rtf中文的的编码 所以只需要在中文的2个编码
    * 第一个编码前加 第二个编码后加 加了一个变量inext 用来判断中文的assii 前一个和后一个。
    * 这样在rtf中文的乱码就可以解决了。
    */
   if (bs[i] > 0) {
    sb.append("\\'");
   } else {
    if (inext == 0) {
     // 通过写字板创建的rtf模板 add by wde
     // sb.append("\\lang2052\\f1");
     // 通过WPS2009创建的rtf模板 add by wde
     // sb.append("\\lang1033 \\langnp1033 \\langfe2052 \\langfenp2052 \\cf1");
     // 通过MS word创建的rtf模板 add by wde
     sb.append("\\loch\\af2\\hich\\af2\\dbch\\f31505");
     sb.append("\\'");
     inext = 1;
    } else {
     sb.append("\\'");
    }
   }
   sb.append(digital[bit]);
   bit = bs[i] & 0x0f;
   sb.append(digital[bit]);
   if (bs[i] < 0 && inext == 1) {
    // 通过写字板创建的rtf模板 add by wde
    // sb.append("\\lang1033\\f0");
    // 通过WPS2009创建的rtf模板 add by wde
    // sb.append(" \\lang1033\\langnp1033 \\langfe2052\\langfenp2052 \\cf1");
    // 通过MS word创建的rtf模板 add by wde
    sb.append("\\hich\\af2\\dbch\\af31505\\loch\\f2");
    inext = 0;
   }
  }
  return sb.toString();
 }

 /**
  * 字节形式读取rtf模板内容
  */
 public String readByteRtf(String path) {
  String sourcecontent = "";
  try {
   InputStream ins = new FileInputStream(path);
   byte[] b = new byte[1024];
   if (ins == null) {
    System.out.println("源模板文件不存在");
   }
   int bytesRead = 0;
   while (true) {
    bytesRead = ins.read(b, 0, 1024);
    if (bytesRead == -1) {
     System.out.println("读取模板文件结束");
     break;
    }
    sourcecontent += new String(b, 0, bytesRead);
   }
  } catch (Exception e) {
   e.printStackTrace();
  }
  return sourcecontent;
 }

 public void saveFile(String sourcecontent, String targetcontent) {
  /* 构建生成文件名 targetname:12时10分23秒_文书.rtf */
  Date current = new Date();
  SimpleDateFormat sdf = new java.text.SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss");
  String targetname = sdf.format(current).substring(11, 13) + "时";
  targetname += sdf.format(current).substring(14, 16) + "分";
  targetname += sdf.format(current).substring(17, 19) + "秒";
  targetname += "_文书.doc";
  /* 结果输出保存到文件 */
  try {
   FileWriter fw = new FileWriter(getSavePath() + "\\" + targetname,
     true);
   PrintWriter out = new PrintWriter(fw);
   if (targetcontent.equals("") || targetcontent == "") {
    out.println(sourcecontent);
   } else {
    out.println(targetcontent);
   }
   out.close();
   fw.close();
   System.out.println(getSavePath() + "  该目录下生成文件" + targetname
     + " 成功");
  } catch (IOException e) {
   e.printStackTrace();
  }
 }

 /**
  * 替换文档的可变部分
  * 
  * @param content
  * @param replacecontent
  * @param flag
  * @return
  */
 public String replaceRTF(String content, String replacecontent, int flag) {
  String rc = strToRtf(replacecontent);
  String target = "";
  switch (flag) {
  case 0:
   target = content.replace("$1$", rc);
   break;
  case 1:
   target = content.replace("$2$", rc);
   break;
  case 2:
   target = content.replace("$3$", rc);
   break;
  case 3:
   target = content.replace("$4$", rc);
   break;
  case 4:
   target = content.replace("$5$", rc);
   break;
  case 5:
   target = content.replace("$6$", rc);
   break; 
  default:
   target = content.replaceAll("\\$\\d{1,2}\\$", " ");
   break;
  }
  return target;
 }

 /**
  * 获取文件路径
  * 
  * @param flag
  * @return
  */
 public String getSavePath() {

  String path = "e:\\";

  File fDirecotry = new File(path);
  if (!fDirecotry.exists()) {
   fDirecotry.mkdirs();
  }
  return path;
 }

 /**
  * 半角转为全角
  */
 public String ToSBC(String input) {
  char[] c = input.toCharArray();
  for (int i = 0; i < c.length; i++) {
   if (c[i] == 32) {
    c[i] = (char) 12288;
    continue;
   }
   if (c[i] < 127) {
    c[i] = (char) (c[i] + 65248);
   }
  }
  return new String(c);
 }

 public void rgModel(String[] content) {

  /* 字节形式读取模板文件内容,将结果转为字符串 */
  String strpath = getSavePath();
  String sourname = strpath + "\\" + "12.rtf";
  // 读取文件
  String sourcecontent = readByteRtf(sourname);
  /* 修改变化部分 */
  String targetcontent = "";
  /**
   * 拆分之后的数组元素与模板中的标识符对应关系
   */
  for (int i = 0; i < content.length; i++) {
   if (i == 0) {
    targetcontent = replaceRTF(sourcecontent, content[i], i);
    inext = 0;
   } else {
    targetcontent = replaceRTF(targetcontent, content[i], i);
   }
  }
  // 保存文件
  saveFile(sourcecontent, targetcontent);
 }

 public static void main(String[] args) {
  TestRtf rt = new TestRtf();
  //一定要加一个空字符串，才能把大于6的$7$等替换的成空。
  String[] content = {"好sss", "人aaas", "hasddsa在", "n3h在", "nssasads2在","dh要assd11"," "}; 
  rt.rgModel(content);
 }

}
