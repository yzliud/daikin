package com.jeeplus.api.web;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.gson.Gson;
import com.jeeplus.api.service.ContractScheduleService;
import com.jeeplus.api.service.ContractService;
import com.jeeplus.api.util.Sms;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.daikin.entity.DkContractSchedule;
import com.jeeplus.modules.daikin.entity.DkWorker;
import com.jeeplus.modules.daikin.entity.SysUser;
import com.jeeplus.modules.daikin.service.DkContractScheduleService;
import com.jeeplus.modules.daikin.service.DkContractService;
import com.jeeplus.modules.daikin.service.DkWorkerService;
import com.jeeplus.modules.daikin.service.SysUserService;
import com.jeeplus.modules.sys.entity.User;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Controller
@RequestMapping(value = "${adminPath}/api/worker")
public class WorkerController extends BaseController {

	@Autowired
	private DkWorkerService workerService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private ContractService contractService;
	
	@Autowired
	private DkContractService dkContractService;

	@Autowired
	private ContractScheduleService contractScheduleService;
	
	@Autowired
	private DkContractScheduleService dkContractScheduleService;

	/**
	 * 首页跳转
	 */
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		String openId = (String) request.getSession().getAttribute("openId");
		if (openId == null) {
			return "redirect:../../../webpage/api/getOpen.html";
		} else {
			DkWorker worker = workerService.findUniqueByProperty("open_id", openId);
			String workerMobile = worker.getMobile();
			if (workerMobile != null && !workerMobile.equals("")) {
				request.getSession().setAttribute("sysId", worker.getSysUserId());
				return "redirect:../../../webpage/api/worker_contract.html";
			} else {
				return "redirect:../../../webpage/api/worker_index.html";
			}
		}

	}

	/**
	 * 发送验证码接口
	 */
	@RequestMapping(value = "sendCode")
	public void sendCode(HttpServletRequest request, HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		int code = (int) (Math.random() * 9000 + 1000);
		String content = "您的验证码是：";
		String msg = content + code;
		Sms.send(msg, mobile);
		request.getSession().setAttribute("code", code);
	}

	/**
	 * 绑定接口
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "binding")
	public void binding(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String msg = "";

		String mobile = request.getParameter("mobile");
		String par_code = request.getParameter("code");
		String code = String.valueOf(request.getSession().getAttribute("code"));
		String openId = (String) request.getSession().getAttribute("openId");

		if (code.equals(par_code)) {
			SysUser user = sysUserService.findUniqueByProperty("mobile", mobile);
			if (user != null) {
				DkWorker worker = workerService.findUniqueByProperty("open_id", openId);
				worker.setMobile(mobile);
				worker.setUpdateDate(new Date());
				worker.setName(user.getName());
				worker.setSysUserId(user.getId());
				User user2 = new User();
				user2.setId(user.getId());
				worker.setUpdateBy(user2);
				workerService.save(worker);
				msg = "success";
			}else{
				msg = "此手机号人员不存在！";
			}
		} else {
			msg = "验证码不正确！";
		}
		System.out.println(openId);

		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", msg);
		Gson gson = new Gson();
		writer.println(gson.toJson(map));
		writer.flush();
		writer.close();
	}

	/**
	 * 获取合同列表接口
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "getAllContracts")
	public void getAllContracts(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();

		String search = request.getParameter("search");
		String sysId = (String) request.getSession().getAttribute("sysId");
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null || "".equals(pageNum)) {
			pageNum = "1";
		}
		Integer pageSize = 3;

		Integer beginNum = (Integer.valueOf(pageNum) - 1) * pageSize;
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		if (sysId != null) {
			if(search!=null&&!search.equals("")){
				list = contractService.findListByInstallSecrch(sysId, beginNum, pageSize, "%"+search+"%");
			}else{
				list = contractService.findListByInstall(sysId, beginNum, pageSize);
			}	
		}
		Gson gson = new Gson();
		writer.println(gson.toJson(list));
		writer.flush();
		writer.close();

	}

	/**
	 * 获取合同进度接口
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "getContractSchedule")
	public void getContractSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();

		String contractId = request.getParameter("contractId");
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null || "".equals(pageNum)) {
			pageNum = "1";
		}
		Integer pageSize = 3;

		Integer beginNum = (Integer.valueOf(pageNum) - 1) * pageSize;
		List<HashMap<String, Object>> list = contractScheduleService.findListByContractId(contractId, beginNum,
				pageSize);
		Gson gson = new Gson();
		writer.println(gson.toJson(list));
		writer.flush();
		writer.close();

	}

	/**
	 * 进度上传接口
	 * @throws IOException 
	 */
	@RequestMapping(value = "submitSchedule")
	public void submitSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();

		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		
		String contractId = request.getParameter("contractId");
		String descript = request.getParameter("descript");
		String percent = request.getParameter("percent");
		String pic = request.getParameter("imgUrl");
		String[] str = pic.split(",");
		str = removeArrayEmptyTextBackNewArray(str);
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < str.length; i++){
			if("".equals(sb)){
				sb.append(str[i]);
			}else{
				sb. append(","+str[i]);
			}
		}
		pic = sb.toString();
		
		double old_percent = 0;
		List<HashMap<String, Object>> list = contractScheduleService.findListByContractId(contractId, 0, 1);
		if(list!=null && list.size() > 0 ){
			HashMap<String, Object> last = list.get(0);
			if(last.get("percent") != null){
				old_percent = Double.valueOf((Integer) last.get("percent"));
			}
		}
		double new_percent = Integer.valueOf(percent);
		if(new_percent >= old_percent){
			DkContractSchedule contractSchedule = new DkContractSchedule();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			contractSchedule.setId(uuid);
			contractSchedule.setDkContract(dkContractService.get(contractId));
			contractSchedule.setDescript(descript);
			contractSchedule.setSubmitDate(new Date());
			contractSchedule.setPic(pic);
			if(percent!=null&&!"".equals(percent)){
				contractSchedule.setPercent(Integer.valueOf(percent));
			}
			User user = new User();
			String sysId = (String) request.getSession().getAttribute("sysId");
			user.setId(sysId);
			contractSchedule.setCreateBy(user);
			contractSchedule.setCreateDate(new Date());
			contractSchedule.setIsNewRecord(true);
			dkContractScheduleService.save(contractSchedule);
			map.put("msg", "success");
		}else{
			map.put("msg", "fail");
		}
		
		writer.println(gson.toJson(map));
		writer.flush();
		writer.close();

	}

	/**
	 * 图片上传接口
	 */
	@RequestMapping(value = "upload")
	public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String allFilesName = "";
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){
        	String myFileName = "";
        	MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
        	List<MultipartFile> files = multiRequest.getFiles("uploaderInput");
        	float imp_cent = 0.5f;
        	DecimalFormat df=new DecimalFormat("0.00");
            for(MultipartFile file:files ){
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();  
                //取得上传文件  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    myFileName = file.getOriginalFilename();  
                    
                    //如果名称不为'',说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
                        //定义上传路径  
                        String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
                        String file_uuid = UUID.randomUUID().toString().replace("-", ""); 
                        String[] strs = myFileName.split("\\.");
                		String file_uuidname = file_uuid + "." + strs[strs.length - 1];
                        String path = uploadPath + "/" + file_uuidname;  
                        File localFile = new File(path);  
                        file.transferTo(localFile);
                        //压缩百分比
                        imp_cent = 0.5f;
                        double imgd = 0;
                        if (localFile.exists() && localFile.isFile()){  
                        	imgd = new Double(df.format(100/((double)localFile.length()/1024)).toString());
                        	if(imgd > 1){
                        		imgd = 1;
                        	}
                        }
                        imp_cent = (float)imgd;
                        resize(localFile, localFile, 1, imp_cent);
                        
                        if(allFilesName.equals("")){
                        	allFilesName = file_uuidname;
                        }else{
                        	allFilesName = allFilesName + "," + file_uuidname ;
                        }
                    }  
                }  
                //记录上传该文件后的时间  
                int finaltime = (int) System.currentTimeMillis();  
            }
        }
        Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("imgname", allFilesName);
		writer.println(gson.toJson(map));
		writer.flush();
		writer.close();
		
	}
	
	 /**
     * @param originalFile  原文件
     * @param resizedFile  压缩目标文件
     * @param quality  压缩质量（越高质量越好）
     * @param scale  缩放比例;  1等大.
     * @throws IOException
     */
    public static void resize(File originalFile, File resizedFile,double scale, float quality) throws IOException {  
        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());  
        Image i = ii.getImage();  
        int iWidth = (int) (i.getWidth(null)*scale);  
        int iHeight = (int) (i.getHeight(null)*scale); 
        //在这你可以自定义 返回图片的大小 iWidth iHeight
        Image resizedImage = i.getScaledInstance(iWidth,iHeight, Image.SCALE_SMOOTH);  
        // 获取图片中的所有像素
        Image temp = new ImageIcon(resizedImage).getImage();  
        // 创建缓冲
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),  
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);  
        // 复制图片到缓冲流中
        Graphics g = bufferedImage.createGraphics();  
        // 清除背景并开始画图
        g.setColor(Color.white);  
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));  
        g.drawImage(temp, 0, 0, null);  
        g.dispose();
        // 柔和图片.  
        float softenFactor =0.05f;  
        float[] softenArray = { 0, softenFactor, 0, softenFactor,  
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };  
        Kernel kernel = new Kernel(3, 3, softenArray);  
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);  
        bufferedImage = cOp.filter(bufferedImage, null);  
        FileOutputStream out = new FileOutputStream(resizedFile);  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);  
        param.setQuality(quality, true);  
        encoder.setJPEGEncodeParam(param);  
        encoder.encode(bufferedImage);
        bufferedImage.flush();
        out.close();
    } 
    
    private String[] removeArrayEmptyTextBackNewArray(String[] strArray) {
        List<String> strList= Arrays.asList(strArray);
        List<String> strListNew=new ArrayList<>();
        for (int i = 0; i <strList.size(); i++) {
            if (strList.get(i)!=null&&!strList.get(i).equals("")){
                strListNew.add(strList.get(i));
            }
        }
        String[] strNewArray = strListNew.toArray(new String[strListNew.size()]);
        return   strNewArray;
    }
}
