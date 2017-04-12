package com.jeeplus.api.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.jeeplus.api.entity.JsonResult;
import com.jeeplus.api.service.ContractService;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.modules.daikin.entity.DkContractSchedule;
import com.jeeplus.modules.daikin.service.DkContractScheduleService;
import com.jeeplus.modules.daikin.service.DkContractService;

@Controller
@RequestMapping(value = "${adminPath}/api/customer")
public class CustomerController {

	@Autowired
	private ContractService contractService;

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

		String mobile = request.getParameter("mobile");
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null || "".equals(pageNum)) {
			pageNum = "1";
		}
		Integer pageSize = 3;
		
		Integer beginNum = (Integer.valueOf(pageNum)-1)*pageSize;

		List<DkContract> list = contractService.findListByMobile(mobile,beginNum,pageSize);
		Gson gson = new Gson();
		writer.println(gson.toJson(list));
		writer.flush();
		writer.close();
	}

	/**
	 * 获取合同进度接口
	 */
	@RequestMapping(value = "getContractSchedule")
	public void getContractSchedule(HttpServletRequest request, HttpServletResponse response) {
		JsonResult result = new JsonResult();
		String contractId = request.getParameter("contractId");
		contractId = "f8764fcb41a7435592f4dab7ca29d866";
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null || "".equals(pageNum)) {
			pageNum = "1";
		}
		String pageSize = "3";

	}
}
