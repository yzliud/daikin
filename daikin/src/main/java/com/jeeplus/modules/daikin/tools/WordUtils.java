package com.jeeplus.modules.daikin.tools;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.PrintWriter;  
import javax.servlet.http.HttpServletRequest;
import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.modules.daikin.entity.DkContractProduct;

/** 
 * 操作word 
 * 读取模板文件 
 *  
 * @author Administrator 
 * 
 */  
public class WordUtils {



	/** 
	 * 字符串转换为rtf编码 
	 * @param content 
	 * @return 
	 */  
	public static String strToRtf(String content){  
		char[] digital = "0123456789ABCDEF".toCharArray();  
		StringBuffer sb = new StringBuffer("");  
		byte[] bs = content.getBytes();  
		int bit;  
		for (int i = 0; i < bs.length; i++) {  
			bit = (bs[i] & 0x0f0) >> 4;  
			sb.append("\\'");  
			sb.append(digital[bit]);  
			bit = bs[i] & 0x0f;  
			sb.append(digital[bit]);  
		}
		return sb.toString();  
	}  



	
	/** 
	 * 替换文档的可变部分 
	 * @param content 
	 * @param replacecontent 
	 * @param flag 
	 * @return 
	 */  
	public static String replaceRTF(String content,DkContract dkContract){  
		String newCt = content;

		newCt = newCt.replace("$installUserName$",strToRtf(dkContract.getInstallUser().getName()));  
		newCt = newCt.replace("$memberName$",strToRtf(dkContract.getMemberName()));  
		newCt = newCt.replace("$mobile$",strToRtf(dkContract.getMobile()));  
		newCt = newCt.replace("$address$",strToRtf(dkContract.getAddress()));  
		newCt = newCt.replace("$contractName$",strToRtf(dkContract.getName()));  
		newCt = newCt.replace("$saleUserName$",strToRtf(dkContract.getSaleUser().getName())); 
		String str = "";
		for(DkContractProduct dcp:dkContract.getDkContractProductList()){
			 str = str + dcp.getName() + "(" + dcp.getAmount() + ")";
		}
		newCt = newCt.replace("$dkContractProductList$",strToRtf(str)); 
		return newCt;  
	}  



	/** 
	 * 获取文件路径 
	 * @param flag 
	 * @return 
	 */  
	public static String getSavePath(HttpServletRequest request) {  
		String uploadPath = request.getSession().getServletContext().getRealPath("/down");
		File fDirecotry = new File(uploadPath);  
		if (!fDirecotry.exists()) {  
			fDirecotry.mkdirs();  
		}  
		return uploadPath;  
	}  

	/** 
	 * 半角转为全角 
	 */  
	public static String ToSBC(String input){  
		char[] c = input.toCharArray();  
		for (int i = 0; i < c.length; i++){  
			if (c[i] == 32){  
				c[i] = (char) 12288;  
				continue;  
			}  
			if (c[i] < 127){  
				c[i] = (char) (c[i] + 65248);  
			}  
		}  
		return new String(c);  
	}  

	public static void rgModel(HttpServletRequest request,DkContract dkContract) {  
		// TODO Auto-generated method stub  
		String targetname = dkContract.getContractNumber()+"派工单.rtf";
		/* 字节形式读取模板文件内容,将结果转为字符串 */  
		String strpath = getSavePath(request);  
		String sourname = strpath + File.separator + "模板文件.rtf";  
		String sourcecontent = "";  
		InputStream ins = null;  
		try{  
			ins = new FileInputStream(sourname);  
			byte[] b = new byte[1024];  
			if (ins == null) {  
				System.out.println("源模板文件不存在");  
			}  
			int bytesRead = 0;  
			while (true) {  
				bytesRead = ins.read(b, 0, 1024); // return final read bytes counts  
				if(bytesRead == -1) {// end of InputStream  
					System.out.println("读取模板文件结束");  
					break;  
				}  
				sourcecontent += new String(b, 0, bytesRead); // convert to string using bytes  
			}  
		}catch(Exception e){  
			e.printStackTrace();  
		}  
		/* 修改变化部分 */  
		String targetcontent = "";  
		 
		targetcontent = replaceRTF(sourcecontent, dkContract);  

		/* 结果输出保存到文件 */  
		try {  
			FileWriter fw = new FileWriter(getSavePath(request)+ File.separator + targetname,true);  
			PrintWriter out = new PrintWriter(fw);  
			if(targetcontent.equals("")||targetcontent==""){  
				out.println(sourcecontent);  
			}else{  
				out.println(targetcontent);  
			}  
			out.close();  
			fw.close();  
			System.out.println(getSavePath(request)+"  该目录下生成文件" + targetname + " 成功");  
		} catch (IOException e) {  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}  
	}  

	public static void main(String[] args) {  

		// TODO Auto-generated method stub  

		//xinxiribao xxrb = new xinxiribao();  

		/** 
		 * 被替换内容以"~"符号分割,处理的时候将其拆分为数组即可 
		 */  
		//UserDate ud = new UserDate();  

		/*String td = ud.getLocalDate();  
		String yd = ud.getYesterday();  

		String yy = td.substring(0, 4);  
		String mm = td.substring(5, 7);  
		String dd = td.substring(8, 10);  

		String y1 = yd.substring(0, 4);  
		String m1 = yd.substring(5, 7);  
		String d1 = yd.substring(8, 10);  

		String y2 = yy;  
		String m2 = mm;  
		String d2 = dd;  

		String content = mm+"~"+dd+"~"+y1+"~"+m1+"~"+d1+"~"+y2+"~"+m2+"~"+d2+"~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~100~230~78~0~0~李瑞华";  

		System.out.println(content);  
		xxrb.rgModel(content);  */
	}  
}
