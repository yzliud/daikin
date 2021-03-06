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
import com.jeeplus.modules.daikin.entity.DkAuditRecord;
import com.jeeplus.modules.daikin.service.DkAuditRecordService;

/**
 * 审核记录Controller
 * @author LD
 * @version 2017-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkAuditRecord")
public class DkAuditRecordController extends BaseController {

	@Autowired
	private DkAuditRecordService dkAuditRecordService;
	
	@ModelAttribute
	public DkAuditRecord get(@RequestParam(required=false) String id) {
		DkAuditRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkAuditRecordService.get(id);
		}
		if (entity == null){
			entity = new DkAuditRecord();
		}
		return entity;
	}
	
	/**
	 * 审核记录列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(DkAuditRecord dkAuditRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkAuditRecord> page = dkAuditRecordService.findPage(new Page<DkAuditRecord>(request, response), dkAuditRecord); 
		model.addAttribute("page", page);
		return "modules/daikin/dkAuditRecordList";
	}

	/**
	 * 查看，增加，编辑审核记录表单页面
	 */
	@RequiresPermissions(value={"daikin:dkAuditRecord:view","daikin:dkAuditRecord:add","daikin:dkAuditRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkAuditRecord dkAuditRecord, Model model) {
		model.addAttribute("dkAuditRecord", dkAuditRecord);
		return "modules/daikin/dkAuditRecordForm";
	}

	/**
	 * 保存审核记录
	 */
	@RequiresPermissions(value={"daikin:dkAuditRecord:add","daikin:dkAuditRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkAuditRecord dkAuditRecord, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkAuditRecord)){
			return form(dkAuditRecord, model);
		}
		if(!dkAuditRecord.getIsNewRecord()){//编辑表单保存
			DkAuditRecord t = dkAuditRecordService.get(dkAuditRecord.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkAuditRecord, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkAuditRecordService.save(t);//保存
		}else{//新增表单保存
			dkAuditRecordService.save(dkAuditRecord);//保存
		}
		addMessage(redirectAttributes, "保存审核记录成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkAuditRecord/?repage";
	}
	
	/**
	 * 删除审核记录
	 */
	@RequiresPermissions("daikin:dkAuditRecord:del")
	@RequestMapping(value = "delete")
	public String delete(DkAuditRecord dkAuditRecord, RedirectAttributes redirectAttributes) {
		dkAuditRecordService.delete(dkAuditRecord);
		addMessage(redirectAttributes, "删除审核记录成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkAuditRecord/?repage";
	}
	
	/**
	 * 批量删除审核记录
	 */
	@RequiresPermissions("daikin:dkAuditRecord:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkAuditRecordService.delete(dkAuditRecordService.get(id));
		}
		addMessage(redirectAttributes, "删除审核记录成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkAuditRecord/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkAuditRecord:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkAuditRecord dkAuditRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "审核记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkAuditRecord> page = dkAuditRecordService.findPage(new Page<DkAuditRecord>(request, response, -1), dkAuditRecord);
    		new ExportExcel("审核记录", DkAuditRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出审核记录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkAuditRecord/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkAuditRecord:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkAuditRecord> list = ei.getDataList(DkAuditRecord.class);
			for (DkAuditRecord dkAuditRecord : list){
				try{
					dkAuditRecordService.save(dkAuditRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条审核记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条审核记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入审核记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkAuditRecord/?repage";
    }
	
	/**
	 * 下载导入审核记录数据模板
	 */
	@RequiresPermissions("daikin:dkAuditRecord:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "审核记录数据导入模板.xlsx";
    		List<DkAuditRecord> list = Lists.newArrayList(); 
    		new ExportExcel("审核记录数据", DkAuditRecord.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkAuditRecord/?repage";
    }
	
	
	

}