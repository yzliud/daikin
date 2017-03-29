/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.web;

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
 * 付款计划Controller
 * @author LD
 * @version 2017-03-24
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
	 * 付款计划列表页面
	 */
	@RequiresPermissions("daikin:dkContractPay:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkContractPay dkContractPay, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkContractPay> page = dkContractPayService.findPage(new Page<DkContractPay>(request, response), dkContractPay); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractPayList";
	}

	/**
	 * 查看，增加，编辑付款计划表单页面
	 */
	@RequiresPermissions(value={"daikin:dkContractPay:view","daikin:dkContractPay:add","daikin:dkContractPay:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkContractPay dkContractPay, Model model) {
		model.addAttribute("dkContractPay", dkContractPay);
		return "modules/daikin/dkContractPayForm";
	}

	/**
	 * 保存付款计划
	 */
	@RequiresPermissions(value={"daikin:dkContractPay:add","daikin:dkContractPay:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkContractPay dkContractPay, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkContractPay)){
			return form(dkContractPay, model);
		}
		if(!dkContractPay.getIsNewRecord()){//编辑表单保存
			DkContractPay t = dkContractPayService.get(dkContractPay.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkContractPay, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkContractPayService.save(t);//保存
		}else{//新增表单保存
			dkContractPayService.save(dkContractPay);//保存
		}
		addMessage(redirectAttributes, "保存付款计划成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/?repage";
	}
	
	/**
	 * 删除付款计划
	 */
	@RequiresPermissions("daikin:dkContractPay:del")
	@RequestMapping(value = "delete")
	public String delete(DkContractPay dkContractPay, RedirectAttributes redirectAttributes) {
		dkContractPayService.delete(dkContractPay);
		addMessage(redirectAttributes, "删除付款计划成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/?repage";
	}
	
	/**
	 * 批量删除付款计划
	 */
	@RequiresPermissions("daikin:dkContractPay:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkContractPayService.delete(dkContractPayService.get(id));
		}
		addMessage(redirectAttributes, "删除付款计划成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkContractPay:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkContractPay dkContractPay, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "付款计划"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkContractPay> page = dkContractPayService.findPage(new Page<DkContractPay>(request, response, -1), dkContractPay);
    		new ExportExcel("付款计划", DkContractPay.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出付款计划记录失败！失败信息："+e.getMessage());
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
				failureMsg.insert(0, "，失败 "+failureNum+" 条付款计划记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条付款计划记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入付款计划失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/?repage";
    }
	
	/**
	 * 下载导入付款计划数据模板
	 */
	@RequiresPermissions("daikin:dkContractPay:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "付款计划数据导入模板.xlsx";
    		List<DkContractPay> list = Lists.newArrayList(); 
    		new ExportExcel("付款计划数据", DkContractPay.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractPay/?repage";
    }
	
	
	

}