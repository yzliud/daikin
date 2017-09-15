package com.jeeplus.api.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.jeeplus.modules.daikin.entity.DkMember;
import com.jeeplus.modules.daikin.entity.DkWorker;
import com.jeeplus.modules.daikin.service.DkMemberService;
import com.jeeplus.modules.daikin.service.DkWorkerService;
import com.jeeplus.modules.sys.entity.User;

@Controller
@RequestMapping(value = "${adminPath}/api/member")
public class MemberController {

	@Autowired
	private DkMemberService dkMemberService;
	
	@Autowired
	private DkWorkerService workerService;
	
	/**
	 * 首页跳转
	 */
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		String openId = (String) request.getSession().getAttribute("openId");
		System.out.println("-----m------"+openId);
		if (openId == null) {
			return "redirect:../../../webpage/api/getOpen.html?cmethod=member";
		} else {
			DkWorker worker = workerService.findUniqueByProperty("open_id", openId);
			String workerMobile = worker.getMobile();
			if (workerMobile != null && !workerMobile.equals("")) {
				request.getSession().setAttribute("sysId", worker.getSysUserId());
				return "redirect:../../../webpage/api/member.html";
			} else {
				return "redirect:../../../webpage/api/worker_index.html?jump=member";
			}
		}

	}
	
	@RequestMapping(value = "save")
	public void save(DkMember dkMember, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
       
//		String openId = (String) request.getSession().getAttribute("openId");
//		DkWorker worker = workerService.findUniqueByProperty("open_id", openId);
//		User createBy = new User();
//		createBy.setId(worker.getSysUserId());
		dkMember.setIsNewRecord(true);
		dkMember.setId(UUID.randomUUID().toString().replace("-", ""));
//		dkMember.setRecordBy(createBy);
//		dkMember.setCreateBy(createBy);
//		dkMember.setUpdateBy(createBy);
		dkMemberService.save(dkMember);//保存
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("rtnCode", "0");
		
		writer.println(gson.toJson(map));
		writer.flush();
		writer.close();
	}
	
	/**
	 * 判断名称是否重复
	 */
	@RequestMapping(value = {"checkAddress"})
	public void checkAddress(DkMember dkMember, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		DkMember dq = dkMemberService.checkAddress(dkMember);		
		
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		if(dq == null){
			map.put("rtnCode", "0");
		}else{
			map.put("rtnCode", "500");
		}
		
		writer.println(gson.toJson(map));
		writer.flush();
		writer.close();
		
	}

}
