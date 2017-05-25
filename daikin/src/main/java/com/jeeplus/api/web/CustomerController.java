package com.jeeplus.api.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.jeeplus.api.service.ContractScheduleService;
import com.jeeplus.api.service.ContractService;

@Controller
@RequestMapping(value = "${adminPath}/api/customer")
public class CustomerController {

	@Autowired
	private ContractService contractService;
	
	@Autowired
	private ContractScheduleService contractScheduleService;

	/**
	 * 首页跳转
	 */
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request, HttpServletResponse response) {

		return "redirect:../../../webpage/api/customer_index.html";
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
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null || "".equals(pageNum)) {
			pageNum = "1";
		}
		Integer pageSize = 3;
		
		Integer beginNum = (Integer.valueOf(pageNum)-1)*pageSize;
		List<HashMap<String, Object>> list = contractService.findListByMobileOrContract(search,beginNum,pageSize);
		Gson gson = new Gson();
		writer.println(gson.toJson(list));
		writer.flush();
		writer.close();
		
	}

	/**
	 * 获取合同进度接口
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
		
		Integer beginNum = (Integer.valueOf(pageNum)-1)*pageSize;
		List<HashMap<String, Object>> list = contractScheduleService.findListByContractId(contractId,beginNum,pageSize);
		Gson gson = new Gson();
		writer.println(gson.toJson(list));
		writer.flush();
		writer.close();

	}
}
