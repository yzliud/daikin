package com.jeeplus.api.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeeplus.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/api/worker")
public class WorkerController extends BaseController{

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
