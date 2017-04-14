package com.jeeplus.api.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUpload;
import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jeeplus.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/api/worker")
public class WorkerController extends BaseController{

	@RequestMapping(value = "upload")
	public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		String uuid = request.getParameter("uuid");
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
	    
		MultipartFile file1 = multipartRequest.getFile("img1");
		MultipartFile file2 = multipartRequest.getFile("img2");
		MultipartFile file3 = multipartRequest.getFile("img3");
		MultipartFile file4 = multipartRequest.getFile("img4");
		String url = "";
		if(file1!=null){
			url = saveImg(file1,uuid,uploadPath,"1");
		}
		
		if(file2!=null){
			url = saveImg(file1,uuid,uploadPath,"2");
		}
		
		if(file3!=null){
			url = saveImg(file1,uuid,uploadPath,"3");
		}
		
		if(file4!=null){
			url = saveImg(file1,uuid,uploadPath,"4");
		}
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		Map<String, String> map = new HashMap<String, String>();
		map.put("url", url);
		writer.println(gson.toJson(map));
		writer.flush();
		writer.close();
	}
	
	private String saveImg(MultipartFile file, String uuid, String uploadPath, String code) throws IllegalStateException, IOException {
		String oldName=file.getOriginalFilename();
	    String[] strs = oldName.split("\\.");
	    String newname = uuid+"-"+code+"."+strs[strs.length-1];  
		System.out.println(uploadPath);
		File uploadDir=new File(uploadPath);
		if(!uploadDir.exists()){
			uploadDir.mkdirs();
		}
		File uploadFile=new File(uploadPath+"/"+newname);
		file.transferTo(uploadFile);//上传
		return uploadFile.getAbsolutePath();
	}

	/**
	 * 首页跳转
	 */
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request , HttpServletResponse response){
		
		return "redirect:../../../webpage/api/worker_index.html";
	}
		
	/**
	 * 绑定接口
	 */
	@RequestMapping(value = "banding")
	public void login(HttpServletRequest request , HttpServletRequest response){
		String test = request.getParameter("test");
		
	}
}
