package com.jeeplus.api.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.jeeplus.api.service.QuotationService;
import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.entity.DkWorker;
import com.jeeplus.modules.daikin.service.DkQuotationService;
import com.jeeplus.modules.daikin.service.DkWorkerService;

@Controller
@RequestMapping(value = "${adminPath}/api/quotation")
public class QuotationController {
	
	@Autowired
	private QuotationService quotationService;

	@Autowired
	private DkWorkerService workerService;
	
	@Autowired
	private DkQuotationService dkQuotationService;

	/**
	 * 首页跳转
	 */
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		String openId = (String) request.getSession().getAttribute("openId");
		if (openId == null) {
			return "redirect:../../../webpage/api/getOpen.html?cmethod=quotation";
		} else {
			DkWorker worker = workerService.findUniqueByProperty("open_id", openId);
			String workerMobile = worker.getMobile();
			if (workerMobile != null && !workerMobile.equals("")) {
				request.getSession().setAttribute("sysId", worker.getSysUserId());
				return "redirect:../../../webpage/api/quotation_1.html";
			} else {
				return "redirect:../../../webpage/api/worker_index.html?jump=quotation";
			}
		}

	}

	/**
	 * 报价单名称是否存在
	 * @throws IOException 
	 */
	@RequestMapping(value = "ishave")
	public void ishave(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();

		String name = request.getParameter("name");

		DkQuotation quotation = new DkQuotation();
		quotation.setName(name);
		DkQuotation quotation1 = dkQuotationService.getSingle(quotation);
		if(quotation1==null){
			map.put("msg", "no");//不存在
		}else{
			map.put("msg", "yes");//存在
		}
		writer.println(gson.toJson(map));
		writer.flush();
		writer.close();
	}
	
	/**
	 * 保存报价单
	 * @throws IOException 
	 */
	@RequestMapping(value = "isExist")
	public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();

		String name = request.getParameter("name");
		String member_name = request.getParameter("member_name");
		String mobile = request.getParameter("mobile");
		String address = request.getParameter("address");
		String product_type = request.getParameter("product_type");
		String product = request.getParameter("product");//id:数量,id:数量
		
		DkQuotation quotation = new DkQuotation();
		quotation.setIsNewRecord(true);

		
		
		map.put("msg", "success");//成功
		writer.println(gson.toJson(map));
		writer.flush();
		writer.close();
	}


}
