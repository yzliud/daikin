/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.modules.Consts;
import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.modules.daikin.entity.DkContractPay;
import com.jeeplus.modules.daikin.entity.DkContractSchedule;
import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.entity.DkMember;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.daikin.service.DkContractPayService;
import com.jeeplus.modules.daikin.service.DkContractScheduleService;
import com.jeeplus.modules.daikin.service.DkContractService;

/**
 * 合同Controller
 * @author LD
 * @version 2017-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkContract")
public class DkContractController extends BaseController {

	@Autowired
	private DkContractService dkContractService;
	
	@Autowired
	private DkContractScheduleService dkContractScheduleService;
	
	@Autowired
	private DkContractPayService dkContractPayService;
	
	@ModelAttribute
	public DkContract get(@RequestParam(required=false) String id) {
		DkContract entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkContractService.get(id);
		}
		if (entity == null){
			entity = new DkContract();
		}
		return entity;
	}
	
	/**
	 * 合同列表页面
	 */
	@RequiresPermissions("daikin:dkContract:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkContract dkContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkContract> page = dkContractService.findPage(new Page<DkContract>(request, response), dkContract); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractList";
	}
	
	/**
	 * 待审核合同
	 */
	@RequiresPermissions("daikin:dkContract:uncheck")
	@RequestMapping(value = {"uncheck"})
	public String uncheck(DkContract dkContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContract.setReviewStatus(Consts.ReviewStatus_1);
		Page<DkContract> page = dkContractService.findPage(new Page<DkContract>(request, response), dkContract); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractUncheckList";
	}
	
	/**
	 * 审核通过合同
	 */
	@RequiresPermissions("daikin:dkContract:checkPass")
	@RequestMapping(value = {"checkPass"})
	public String checkPass(DkContract dkContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContract.setReviewStatus(Consts.ReviewStatus_9);
		Page<DkContract> page = dkContractService.findPage(new Page<DkContract>(request, response), dkContract); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractCheckPassList";
	}

	/**
	 * 查看，增加，编辑合同表单页面
	 */
	@RequiresPermissions(value={"daikin:dkContract:view","daikin:dkContract:add","daikin:dkContract:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkContract dkContract, Model model) {
		model.addAttribute("dkContract", dkContract);
		return "modules/daikin/dkContractForm";
	}

	/**
	 * 保存合同
	 */
	@RequiresPermissions(value={"daikin:dkContract:add","daikin:dkContract:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkContract dkContract, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkContract)){
			return form(dkContract, model);
		}
		dkContract.setReviewStatus(Consts.ReviewStatus_0);
		if(!dkContract.getIsNewRecord()){//编辑表单保存
			DkContract t = dkContractService.get(dkContract.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkContract, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkContractService.save(t);//保存
		}else{//新增表单保存
			dkContractService.save(dkContract);//保存
		}
		addMessage(redirectAttributes, "保存合同成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
	}
	
	/**
	 * 删除合同
	 */
	@RequiresPermissions("daikin:dkContract:del")
	@RequestMapping(value = "delete")
	public String delete(DkContract dkContract, RedirectAttributes redirectAttributes) {
		dkContractService.delete(dkContract);
		addMessage(redirectAttributes, "删除合同成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
	}
	
	/**
	 * 批量删除合同
	 */
	@RequiresPermissions("daikin:dkContract:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkContractService.delete(dkContractService.get(id));
		}
		addMessage(redirectAttributes, "删除合同成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkContract:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkContract dkContract, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "合同"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkContract> page = dkContractService.findPage(new Page<DkContract>(request, response, -1), dkContract);
    		new ExportExcel("合同", DkContract.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出合同记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkContract:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkContract> list = ei.getDataList(DkContract.class);
			for (DkContract dkContract : list){
				try{
					dkContractService.save(dkContract);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条合同记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条合同记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入合同失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
    }
	
	/**
	 * 下载导入合同数据模板
	 */
	@RequiresPermissions("daikin:dkContract:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "合同数据导入模板.xlsx";
    		List<DkContract> list = Lists.newArrayList(); 
    		new ExportExcel("合同数据", DkContract.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
    }
	
	
	/**
	 * 选择主合同ID
	 */
	@RequestMapping(value = "selectparent")
	public String selectparent(DkContract parent, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		parent.setContractFlag(Consts.ContractFlag_0);
		Page<DkContract> page = dkContractService.findPageByparent(new Page<DkContract>(request, response),  parent);
		try {
			fieldLabels = URLDecoder.decode(fieldLabels, "UTF-8");
			fieldKeys = URLDecoder.decode(fieldKeys, "UTF-8");
			searchLabel = URLDecoder.decode(searchLabel, "UTF-8");
			searchKey = URLDecoder.decode(searchKey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("labelNames", fieldLabels.split("\\|"));
		model.addAttribute("labelValues", fieldKeys.split("\\|"));
		model.addAttribute("fieldLabels", fieldLabels);
		model.addAttribute("fieldKeys", fieldKeys);
		model.addAttribute("url", url);
		model.addAttribute("searchLabel", searchLabel);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("obj", parent);
		model.addAttribute("page", page);
		return "modules/daikin/gridSelectFour";
	}
	/**
	 * 选择报价单ID
	 */
	@RequestMapping(value = "selectdkQuotation")
	public String selectdkQuotation(DkQuotation dkQuotation, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkQuotation.setReviewStatus(Consts.ReviewStatus_9);
		Page<DkQuotation> page = dkContractService.findPageBydkQuotation(new Page<DkQuotation>(request, response),  dkQuotation);
		try {
			fieldLabels = URLDecoder.decode(fieldLabels, "UTF-8");
			fieldKeys = URLDecoder.decode(fieldKeys, "UTF-8");
			searchLabel = URLDecoder.decode(searchLabel, "UTF-8");
			searchKey = URLDecoder.decode(searchKey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("labelNames", fieldLabels.split("\\|"));
		model.addAttribute("labelValues", fieldKeys.split("\\|"));
		model.addAttribute("fieldLabels", fieldLabels);
		model.addAttribute("fieldKeys", fieldKeys);
		model.addAttribute("url", url);
		model.addAttribute("searchLabel", searchLabel);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("obj", dkQuotation);
		model.addAttribute("page", page);
		return "modules/sys/gridselect";
	}
	/**
	 * 选择会员ID
	 */
	@RequestMapping(value = "selectdkMember")
	public String selectdkMember(DkMember dkMember, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkMember> page = dkContractService.findPageBydkMember(new Page<DkMember>(request, response),  dkMember);
		try {
			fieldLabels = URLDecoder.decode(fieldLabels, "UTF-8");
			fieldKeys = URLDecoder.decode(fieldKeys, "UTF-8");
			searchLabel = URLDecoder.decode(searchLabel, "UTF-8");
			searchKey = URLDecoder.decode(searchKey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("labelNames", fieldLabels.split("\\|"));
		model.addAttribute("labelValues", fieldKeys.split("\\|"));
		model.addAttribute("fieldLabels", fieldLabels);
		model.addAttribute("fieldKeys", fieldKeys);
		model.addAttribute("url", url);
		model.addAttribute("searchLabel", searchLabel);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("obj", dkMember);
		model.addAttribute("page", page);
		return "modules/sys/gridselect";
	}
	
	/**
	 * 保存合同
	 */
	@RequiresPermissions(value={"daikin:dkContract:add"})
	@RequestMapping(value = "add")
	public String add(DkContract dkContract, Model model, RedirectAttributes redirectAttributes) throws Exception{
		/*if (!beanValidator(model, dkContract)){
			return form(dkContract, model);
		}*/
		
		dkContractService.add(dkContract);//保存
		
		addMessage(redirectAttributes, "新增合同成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
	}
	
	/**
	 * 查看，增加，编辑合同表单页面
	 */
	@RequiresPermissions(value={"daikin:dkContract:add"})
	@RequestMapping(value = "forwardAdd")
	public String forwardAdd(DkContract dkContract, Model model) {
		dkContract.setContractFlag(Consts.ContractFlag_0);
		model.addAttribute("dkContract", dkContract);
		return "modules/daikin/dkContractAdd";
	}
	
	/**
	 * 合同列表页面
	 */
	@RequestMapping(value = {"checkContract"})
	public String checkContract(DkContract dkContract, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		DkContract dk = dkContractService.getSingle(dkContract);
    	String result = "";
		
		if(dk == null){
			result = "{\"rtnCode\":0}";
		}else{
			result = "{\"rtnCode\":500}";
		}
		
		response.reset();
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.getOutputStream().write(result.getBytes("utf-8"));
		response.getOutputStream().flush();

		return null;
		
	}
	
	/**
	 * 查看，增加，编辑合同表单页面
	 */
	@RequestMapping(value = "detail")
	public String detail(DkContract dkContract, String checkType, Model model) {
		model.addAttribute("dkContract", dkContract);
		model.addAttribute("checkType", checkType);
		return "modules/daikin/dkContractDetail";
	}
	
	/**
	 * 查看整个合同的详细
	 */
	@RequestMapping(value = "totalDetail")
	public String totalDetail(DkContract dkContract, Model model) {
		model.addAttribute("dkContract", dkContract);
		//增补合同
		DkContract dc = new DkContract();
		dc.setParent(dkContract);
		dc.setContractFlag(Consts.ContractFlag_1);
		dc.setReviewStatus(Consts.ReviewStatus_9);
		List<DkContract> dcSubList = dkContractService.findList(dc);
		model.addAttribute("contractSubList", dcSubList);
		//进度
		DkContractSchedule dcs = new DkContractSchedule();
		dcs.setDkContract(dkContract);
		List<DkContractSchedule> dscList = dkContractScheduleService.findList(dcs);
		model.addAttribute("contractScheduleList", dscList);
		
		int scheduleCent = 0;
		if(dscList != null && dscList.get(0) != null){
			scheduleCent = dscList.get(0).getPercent();
		}
		model.addAttribute("scheduleCent", scheduleCent);
		
		//回款
		DkContractPay dkContractPay = new DkContractPay();
		dkContractPay.setDkContract(dkContract);
		dkContractPay.setReviewStatus(Consts.ReviewStatus_9);
		List<DkContractPay> dspList =  dkContractPayService.findList(dkContractPay);
		model.addAttribute("contractPayList", dspList);
		
		double payCent = dkContract.getArriveFee()/dkContract.getTotalFee();
		model.addAttribute("payCent", String.format("%.2f", payCent));
		return "modules/daikin/dkContractTotalDetail";
	}
	
	/**
	 * 审核合同
	 * @param dkContract
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "reviewContract")
	public String reviewContract(DkContract dkContract, RedirectAttributes redirectAttributes) {
		String isReview = "";
		String url = "";
		if(dkContract.getReviewStatus().equals(Consts.ReviewStatus_1)){
			addMessage(redirectAttributes, "审核提交成功");
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
		}else if(dkContract.getReviewStatus().equals(Consts.ReviewStatus_2)){
			addMessage(redirectAttributes, "该合同已驳回");
			isReview = Consts.IsReview_1;
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContract/uncheck";
		}else{
			addMessage(redirectAttributes, "合同审核通过");
			isReview = Consts.IsReview_1;
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContract/uncheck";
		}
		dkContract.setIsReview(isReview);
		dkContractService.reviewContract(dkContract);
		return url;
	}
	
	/**
	 * 审核合同
	 * @param dkContract
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "formalContractDetail")
	public String formalContractDetail(DkContract dkContract, RedirectAttributes redirectAttributes) {
		String isReview = "";
		String url = "";
		if(dkContract.getReviewStatus().equals(Consts.ReviewStatus_1)){
			addMessage(redirectAttributes, "审核提交成功");
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
		}else if(dkContract.getReviewStatus().equals(Consts.ReviewStatus_2)){
			addMessage(redirectAttributes, "该报价单已驳回");
			isReview = Consts.IsReview_1;
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContract/uncheck";
		}else{
			addMessage(redirectAttributes, "该报价单审核通过");
			isReview = Consts.IsReview_1;
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContract/uncheck";
		}
		dkContract.setIsReview(isReview);
		dkContractService.reviewContract(dkContract);
		return url;
	}
	
	/**
	 * 跳转安装设置
	 */
	@RequestMapping(value = "forwardAssign")
	public String forwardAssign(DkContract dkContract, Model model) {
		dkContract.setContractFlag(Consts.ContractFlag_0);
		model.addAttribute("dkContract", dkContract);
		return "modules/daikin/dkContractAssignInstall";
	}
	
	/**
	 * 分配安装人员
	 * @param dkContract
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "assignInstall")
	public String assignInstall(DkContract dkContract, RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes, "安装人员分配成功");
		dkContractService.assignInstall(dkContract);
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/checkPass";
	}

}