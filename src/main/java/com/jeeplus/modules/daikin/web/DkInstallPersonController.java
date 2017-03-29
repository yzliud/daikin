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
import com.jeeplus.modules.daikin.entity.DkInstallPerson;
import com.jeeplus.modules.daikin.service.DkInstallPersonService;

/**
 * 安装人员Controller
 * @author LD
 * @version 2017-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkInstallPerson")
public class DkInstallPersonController extends BaseController {

	@Autowired
	private DkInstallPersonService dkInstallPersonService;
	
	@ModelAttribute
	public DkInstallPerson get(@RequestParam(required=false) String id) {
		DkInstallPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkInstallPersonService.get(id);
		}
		if (entity == null){
			entity = new DkInstallPerson();
		}
		return entity;
	}
	
	/**
	 * 安装人员列表页面
	 */
	@RequiresPermissions("daikin:dkInstallPerson:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkInstallPerson dkInstallPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkInstallPerson> page = dkInstallPersonService.findPage(new Page<DkInstallPerson>(request, response), dkInstallPerson); 
		model.addAttribute("page", page);
		return "modules/daikin/dkInstallPersonList";
	}

	/**
	 * 查看，增加，编辑安装人员表单页面
	 */
	@RequiresPermissions(value={"daikin:dkInstallPerson:view","daikin:dkInstallPerson:add","daikin:dkInstallPerson:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkInstallPerson dkInstallPerson, Model model) {
		model.addAttribute("dkInstallPerson", dkInstallPerson);
		return "modules/daikin/dkInstallPersonForm";
	}

	/**
	 * 保存安装人员
	 */
	@RequiresPermissions(value={"daikin:dkInstallPerson:add","daikin:dkInstallPerson:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkInstallPerson dkInstallPerson, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkInstallPerson)){
			return form(dkInstallPerson, model);
		}
		if(!dkInstallPerson.getIsNewRecord()){//编辑表单保存
			DkInstallPerson t = dkInstallPersonService.get(dkInstallPerson.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkInstallPerson, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkInstallPersonService.save(t);//保存
		}else{//新增表单保存
			dkInstallPersonService.save(dkInstallPerson);//保存
		}
		addMessage(redirectAttributes, "保存安装人员成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkInstallPerson/?repage";
	}
	
	/**
	 * 删除安装人员
	 */
	@RequiresPermissions("daikin:dkInstallPerson:del")
	@RequestMapping(value = "delete")
	public String delete(DkInstallPerson dkInstallPerson, RedirectAttributes redirectAttributes) {
		dkInstallPersonService.delete(dkInstallPerson);
		addMessage(redirectAttributes, "删除安装人员成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkInstallPerson/?repage";
	}
	
	/**
	 * 批量删除安装人员
	 */
	@RequiresPermissions("daikin:dkInstallPerson:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkInstallPersonService.delete(dkInstallPersonService.get(id));
		}
		addMessage(redirectAttributes, "删除安装人员成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkInstallPerson/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkInstallPerson:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkInstallPerson dkInstallPerson, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "安装人员"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkInstallPerson> page = dkInstallPersonService.findPage(new Page<DkInstallPerson>(request, response, -1), dkInstallPerson);
    		new ExportExcel("安装人员", DkInstallPerson.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出安装人员记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkInstallPerson/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkInstallPerson:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkInstallPerson> list = ei.getDataList(DkInstallPerson.class);
			for (DkInstallPerson dkInstallPerson : list){
				try{
					dkInstallPersonService.save(dkInstallPerson);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条安装人员记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条安装人员记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入安装人员失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkInstallPerson/?repage";
    }
	
	/**
	 * 下载导入安装人员数据模板
	 */
	@RequiresPermissions("daikin:dkInstallPerson:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "安装人员数据导入模板.xlsx";
    		List<DkInstallPerson> list = Lists.newArrayList(); 
    		new ExportExcel("安装人员数据", DkInstallPerson.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkInstallPerson/?repage";
    }
	
	
	

}