package com.jeeplus.api.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jeeplus.api.service.ContractScheduleService;
import com.jeeplus.api.service.ContractService;
import com.jeeplus.api.util.Sms;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.daikin.entity.DkContractSchedule;
import com.jeeplus.modules.daikin.entity.DkWorker;
import com.jeeplus.modules.daikin.entity.SysUser;
import com.jeeplus.modules.daikin.service.DkContractScheduleService;
import com.jeeplus.modules.daikin.service.DkContractService;
import com.jeeplus.modules.daikin.service.DkWorkerService;
import com.jeeplus.modules.daikin.service.SysUserService;
import com.jeeplus.modules.sys.entity.User;

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
		request.getSession().setAttribute("sysId","001");// XQNtest
		request.getSession().setAttribute("openId","001");// XQNtest
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
			DkWorker worker = workerService.findUniqueByProperty("open_id", openId);
			worker.setMobile(mobile);
			worker.setUpdateDate(new Date());
			SysUser user = sysUserService.findUniqueByProperty("mobile", mobile);
			if (user != null) {
				worker.setName(user.getName());
				worker.setSysUserId(user.getId());
				User user2 = new User();
				user2.setId(user.getId());
				worker.setUpdateBy(user2);
			}
			workerService.save(worker);
			msg = "success";
		} else {
			msg = "验证码不正确";
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

		String sysId = (String) request.getSession().getAttribute("sysId");
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null || "".equals(pageNum)) {
			pageNum = "1";
		}
		Integer pageSize = 3;

		Integer beginNum = (Integer.valueOf(pageNum) - 1) * pageSize;
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		if (sysId != null) {
			list = contractService.findListByInstall(sysId, beginNum, pageSize);
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

		String id = request.getParameter("uid");
		String contractId = request.getParameter("contractId");
		String descript = request.getParameter("descript");
		String percent = request.getParameter("percent");
		String submit_data = request.getParameter("choiceTime");
		String pic = request.getParameter("imgUrl1")
				.concat(",")
				.concat(request.getParameter("imgUrl2"))
				.concat(",")
				.concat(request.getParameter("imgUrl3"))
				.concat(",")
				.concat(request.getParameter("imgUrl4"));
		
		DkContractSchedule contractSchedule = new DkContractSchedule();
		contractSchedule.setId(id);
		contractSchedule.setDkContract(dkContractService.get( contractId));
		contractSchedule.setDescript(descript);
		if(submit_data!=null&&!"".equals(submit_data)){
			contractSchedule.setSubmitDate(DateUtils.parseDate(submit_data));
		}
		contractSchedule.setPic(pic);
		if(percent!=null&&!"".equals(percent)){
			contractSchedule.setPercent(Integer.valueOf(percent));
		}
		User user = new User();
		String sysId = (String) request.getSession().getAttribute("sysId");
		user.setId(sysId);
		contractSchedule.setCreateBy(user);
		contractSchedule.setCreateDate(new Date());
		dkContractScheduleService.save(contractSchedule);

		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", "success");
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

		String uuid = request.getParameter("uuid");
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		MultipartFile file1 = multipartRequest.getFile("img1");
		MultipartFile file2 = multipartRequest.getFile("img2");
		MultipartFile file3 = multipartRequest.getFile("img3");
		MultipartFile file4 = multipartRequest.getFile("img4");
		String nwename = "";
		if (file1 != null) {
			nwename = saveImg(file1, uuid, uploadPath, "1");
		}

		if (file2 != null) {
			nwename = saveImg(file2, uuid, uploadPath, "2");
		}

		if (file3 != null) {
			nwename = saveImg(file3, uuid, uploadPath, "3");
		}

		if (file4 != null) {
			nwename = saveImg(file4, uuid, uploadPath, "4");
		}
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		Map<String, String> map = new HashMap<String, String>();
		map.put("imgname", nwename);
		writer.println(gson.toJson(map));
		writer.flush();
		writer.close();
	}

	private String saveImg(MultipartFile file, String uuid, String uploadPath, String code)
			throws IllegalStateException, IOException {
		String oldName = file.getOriginalFilename();
		String[] strs = oldName.split("\\.");
		String newname = uuid + "-" + code + "." + strs[strs.length - 1];
		System.out.println(uploadPath);
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		File uploadFile = new File(uploadPath + "/" + newname);
		file.transferTo(uploadFile);// 上传
		return newname;
	}
}
