package com.jeeplus.api.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeeplus.modules.daikin.entity.DkWorker;
import com.jeeplus.modules.daikin.service.DkWorkerService;

@Controller
@RequestMapping(value = "${adminPath}/api/quotation")
public class QuotationController {
	
	@Autowired
	private DkWorkerService workerService;

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
	
}
