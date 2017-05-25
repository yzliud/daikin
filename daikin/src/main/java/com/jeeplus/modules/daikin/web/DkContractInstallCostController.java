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
import com.jeeplus.modules.daikin.entity.DkContractInstallCost;
import com.jeeplus.modules.daikin.service.DkContractInstallCostService;

/**
 * 安装付款Controller
 * @author LD
 * @version 2017-05-06
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkContractInstallCost")
public class DkContractInstallCostController extends BaseController {

	@Autowired
	private DkContractInstallCostService dkContractInstallCostService;
	
	@ModelAttribute
	public DkContractInstallCost get(@RequestParam(required=false) String id) {
		DkContractInstallCost entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkContractInstallCostService.get(id);
		}
		if (entity == null){
			entity = new DkContractInstallCost();
		}
		return entity;
	}
	
	/**
	 * 安装付款列表页面
	 */
	@RequiresPermissions("daikin:dkContractInstallCost:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkContractInstallCost dkContractInstallCost, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkContractInstallCost> page = dkContractInstallCostService.findPage(new Page<DkContractInstallCost>(request, response), dkContractInstallCost); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractInstallCostList";
	}
	
	/**
	 * 实际安装付款列表
	 */
	@RequiresPermissions("daikin:dkContractInstallCost:realList")
	@RequestMapping(value = {"realList"})
	public String realList(DkContractInstallCost dkContractInstallCost, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContractInstallCost.setReviewStatus(Consts.ReviewStatus_9);
		Page<DkContractInstallCost> page = dkContractInstallCostService.findPage(new Page<DkContractInstallCost>(request, response), dkContractInstallCost); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractInstallCostList_real";
	}
	
	/**
	 * 审核安装付款列表
	 */
	@RequiresPermissions("daikin:dkContractInstallCost:checkList")
	@RequestMapping(value = {"checkList"})
	public String checkList(DkContractInstallCost dkContractInstallCost, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContractInstallCost.setReviewStatus(Consts.ReviewStatus_1);
		Page<DkContractInstallCost> page = dkContractInstallCostService.findPage(new Page<DkContractInstallCost>(request, response), dkContractInstallCost); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractInstallCostList_check";
	}

	/**
	 * 查看，增加，编辑安装付款表单页面
	 */
	@RequiresPermissions(value={"daikin:dkContractInstallCost:view","daikin:dkContractInstallCost:add","daikin:dkContractInstallCost:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkContractInstallCost dkContractInstallCost, Model model) {
		model.addAttribute("dkContractInstallCost", dkContractInstallCost);
		return "modules/daikin/dkContractInstallCostForm";
	}
	
	/**
	 * detail
	 */
	@RequestMapping(value = "detail")
	public String detail(DkContractInstallCost dkContractInstallCost, String checkType, Model model) {
		model.addAttribute("dkContractInstallCost", dkContractInstallCost);
		model.addAttribute("checkType", checkType);
		return "modules/daikin/dkContractInstallCostForm_detail";
	}

	/**
	 * 保存安装付款
	 */
	@RequiresPermissions(value={"daikin:dkContractInstallCost:add","daikin:dkContractInstallCost:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkContractInstallCost dkContractInstallCost, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkContractInstallCost)){
			return form(dkContractInstallCost, model);
		}
		dkContractInstallCost.setReviewStatus(Consts.ReviewStatus_1);
		if(!dkContractInstallCost.getIsNewRecord()){//编辑表单保存
			DkContractInstallCost t = dkContractInstallCostService.get(dkContractInstallCost.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkContractInstallCost, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkContractInstallCostService.save(t);//保存
		}else{//新增表单保存
			dkContractInstallCostService.save(dkContractInstallCost);//保存
		}
		addMessage(redirectAttributes, "保存安装付款成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstallCost/?repage";
	}
	
	/**
	 * 删除安装付款
	 */
	@RequiresPermissions("daikin:dkContractInstallCost:del")
	@RequestMapping(value = "delete")
	public String delete(DkContractInstallCost dkContractInstallCost, RedirectAttributes redirectAttributes) {
		dkContractInstallCostService.delete(dkContractInstallCost);
		addMessage(redirectAttributes, "删除安装付款成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstallCost/?repage";
	}
	
	/**
	 * 批量删除安装付款
	 */
	@RequiresPermissions("daikin:dkContractInstallCost:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkContractInstallCostService.delete(dkContractInstallCostService.get(id));
		}
		addMessage(redirectAttributes, "删除安装付款成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstallCost/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	//@RequiresPermissions("daikin:dkContractInstallCost:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkContractInstallCost dkContractInstallCost, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			dkContractInstallCost.setReviewStatus(Consts.ReviewStatus_9);
            String fileName = "安装付款"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkContractInstallCost> page = dkContractInstallCostService.findPage(new Page<DkContractInstallCost>(request, response, -1), dkContractInstallCost);
    		new ExportExcel("安装付款", DkContractInstallCost.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出安装付款记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstallCost/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkContractInstallCost:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkContractInstallCost> list = ei.getDataList(DkContractInstallCost.class);
			for (DkContractInstallCost dkContractInstallCost : list){
				try{
					dkContractInstallCostService.save(dkContractInstallCost);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条安装付款记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条安装付款记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入安装付款失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstallCost/?repage";
    }
	
	/**
	 * 下载导入安装付款数据模板
	 */
	@RequiresPermissions("daikin:dkContractInstallCost:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "安装付款数据导入模板.xlsx";
    		List<DkContractInstallCost> list = Lists.newArrayList(); 
    		new ExportExcel("安装付款数据", DkContractInstallCost.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstallCost/?repage";
    }
	
	
	/**
	 * 选择合同ID
	 */
	@RequestMapping(value = "selectdkContract")
	public String selectdkContract(DkContract dkContract, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContract.setContractFlag(Consts.ContractFlag_0);
		Page<DkContract> page = dkContractInstallCostService.findPageBydkContract(new Page<DkContract>(request, response),  dkContract);
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
	@RequestMapping(value = "checkInstallCost")
	public String checkInstallCost(DkContractInstallCost dkContractInstallCost, RedirectAttributes redirectAttributes) {
		String isReview = "";
		String url = "";
		if(dkContractInstallCost.getReviewStatus().equals(Consts.ReviewStatus_1)){
			addMessage(redirectAttributes, "审核提交成功");
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstallCost/?repage";
		}else if(dkContractInstallCost.getReviewStatus().equals(Consts.ReviewStatus_2)){
			addMessage(redirectAttributes, "该安装付款已驳回");
			isReview = Consts.IsReview_1;
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstallCost/checkList";
		}else{
			addMessage(redirectAttributes, "该安装付款审核通过");
			isReview = Consts.IsReview_1;
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstallCost/checkList";
		}
		dkContractInstallCost.setIsReview(isReview);
		dkContractInstallCostService.checkInstallCost(dkContractInstallCost);
		return url;
	}
	

}