/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.web;

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
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.daikin.entity.DkContractPay;
import com.jeeplus.modules.daikin.service.DkContractPayService;

/**
 * 合同到款Controller
 * @author LD
 * @version 2017-04-13
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkContractPay")
public class DkContractPayController extends BaseController {

	@Autowired
	private DkContractPayService dkContractPayService;
	
	@ModelAttribute
	public DkContractPay get(@RequestParam(required=false) String id) {
		DkContractPay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkContractPayService.get(id);
		}
		if (entity == null){
			entity = new DkContractPay();
		}
		return entity;
	}
	
	/**
	 * 合同到款列表页面
	 */
	@RequiresPermissions("daikin:dkContractPay:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkContractPay dkContractPay, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkContractPay> page = dkContractPayService.findPage(new Page<DkContractPay>(request, response), dkContractPay); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractPayList";
	}
	
	/**
	 * 待审核
	 */
	@RequiresPermissions("daikin:dkContractPay:uncheck")
	@RequestMapping(value = {"uncheck"})
	public String uncheck(DkContractPay dkContractPay, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContractPay.setReviewStatus(Consts.ReviewStatus_1);
		Page<DkContractPay> page = dkContractPayService.findPage(new Page<DkContractPay>(request, response), dkContractPay); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractPayUncheckList";
	}
	
	/**
	 * 审核通过
	 */
	@RequiresPermissions("daikin:dkContractPay:checkPass")
	@RequestMapping(value = {"checkPass"})
	public String checkPass(DkContractPay dkContractPay, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContractPay.setReviewStatus(Consts.ReviewStatus_9);
		Page<DkContractPay> page = dkContractPayService.findPage(new Page<DkContractPay>(request, response), dkContractPay); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractPayCheckPassList";
	}

	/**
	 * 查看，增加，编辑合同到款表单页面
	 */
	@RequiresPermissions(value={"daikin:dkContractPay:view","daikin:dkContractPay:add","daikin:dkContractPay:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkContractPay dkContractPay, Model model) {
		model.addAttribute("dkContractPay", dkContractPay);
		return "modules/daikin/dkContractPayForm";
	}

	/**
	 * 保存合同到款
	 */
	@RequiresPermissions(value={"daikin:dkContractPay:add","daikin:dkContractPay:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkContractPay dkContractPay, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkContractPay)){
			return form(dkContractPay, model);
		}
		dkContractPay.setReviewStatus(Consts.ReviewStatus_1);
		if(!dkContractPay.getIsNewRecord()){//编辑表单保存
			DkContractPay t = dkContractPayService.get(dkContractPay.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkContractPay, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkContractPayService.save(t);//保存
		}else{//新增表单保存
			dkContractPayService.save(dkContractPay);//保存
		}
		addMessage(redirectAttributes, "保存合同到款成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/?repage";
	}
	
	/**
	 * 删除合同到款
	 */
	@RequiresPermissions("daikin:dkContractPay:del")
	@RequestMapping(value = "delete")
	public String delete(DkContractPay dkContractPay, RedirectAttributes redirectAttributes) {
		dkContractPayService.delete(dkContractPay);
		addMessage(redirectAttributes, "删除合同到款成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/?repage";
	}
	
	/**
	 * 批量删除合同到款
	 */
	@RequiresPermissions("daikin:dkContractPay:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkContractPayService.delete(dkContractPayService.get(id));
		}
		addMessage(redirectAttributes, "删除合同到款成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkContractPay:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkContractPay dkContractPay, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "合同到款"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkContractPay> page = dkContractPayService.findPage(new Page<DkContractPay>(request, response, -1), dkContractPay);
    		new ExportExcel("合同到款", DkContractPay.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出合同到款记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkContractPay:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkContractPay> list = ei.getDataList(DkContractPay.class);
			for (DkContractPay dkContractPay : list){
				try{
					dkContractPayService.save(dkContractPay);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条合同到款记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条合同到款记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入合同到款失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/?repage";
    }
	
	/**
	 * 下载导入合同到款数据模板
	 */
	@RequiresPermissions("daikin:dkContractPay:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "合同到款数据导入模板.xlsx";
    		List<DkContractPay> list = Lists.newArrayList(); 
    		new ExportExcel("合同到款数据", DkContractPay.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/?repage";
    }
	
	
	/**
	 * 选择合同ID
	 */
	@RequestMapping(value = "selectdkContract")
	public String selectdkContract(DkContract dkContract, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContract.setContractFlag(Consts.ContractFlag_0);
		Page<DkContract> page = dkContractPayService.findPageBydkContract(new Page<DkContract>(request, response),  dkContract);
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
		model.addAttribute("obj", dkContract);
		model.addAttribute("page", page);
		return "modules/sys/gridselect";
	}
	
	/**
	 * 审核合同
	 * @param dkContract
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "reviewContractPay")
	public String reviewContractPay(DkContractPay dkContractPay, RedirectAttributes redirectAttributes) {
		String isReview = "";
		String url = "";
		if(dkContractPay.getReviewStatus().equals(Consts.ReviewStatus_1)){
			addMessage(redirectAttributes, "审核提交成功");
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/?repage";
		}else if(dkContractPay.getReviewStatus().equals(Consts.ReviewStatus_2)){
			addMessage(redirectAttributes, "该报价单已驳回");
			isReview = Consts.IsReview_1;
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/uncheck";
		}else{
			addMessage(redirectAttributes, "该报价单审核通过");
			isReview = Consts.IsReview_1;
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/uncheck";
		}
		dkContractPay.setIsReview(isReview);
		dkContractPayService.reviewContractPay(dkContractPay);
		return url;
	}
	
	/**
	 * 查看，增加，编辑合同到款表单页面
	 */
	@RequestMapping(value = "detail")
	public String detail(DkContractPay dkContractPay, String checkType, Model model) {
		model.addAttribute("dkContractPay", dkContractPay);
		model.addAttribute("checkType", checkType);
		return "modules/daikin/dkContractPayDetail";
	}
	
}