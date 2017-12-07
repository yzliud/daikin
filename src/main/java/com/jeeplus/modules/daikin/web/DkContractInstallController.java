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
import com.jeeplus.modules.daikin.entity.DkContractInstall;
import com.jeeplus.modules.daikin.service.DkContractInstallService;

/**
 * 预约安装Controller
 * @author LD
 * @version 2017-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkContractInstall")
public class DkContractInstallController extends BaseController {

	@Autowired
	private DkContractInstallService dkContractInstallService;
	
	@ModelAttribute
	public DkContractInstall get(@RequestParam(required=false) String id) {
		DkContractInstall entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkContractInstallService.get(id);
		}
		if (entity == null){
			entity = new DkContractInstall();
		}
		return entity;
	}
	
	/**
	 * 预约安装列表页面
	 */
	@RequiresPermissions("daikin:dkContractInstall:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkContractInstall dkContractInstall, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkContractInstall> page = dkContractInstallService.findPage(new Page<DkContractInstall>(request, response), dkContractInstall); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractInstallList";
	}

	/**
	 * 查看，增加，编辑预约安装表单页面
	 */
	@RequiresPermissions(value={"daikin:dkContractInstall:view","daikin:dkContractInstall:add","daikin:dkContractInstall:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkContractInstall dkContractInstall, Model model) {
		model.addAttribute("dkContractInstall", dkContractInstall);
		return "modules/daikin/dkContractInstallForm";
	}

	/**
	 * 保存预约安装
	 */
	@RequiresPermissions(value={"daikin:dkContractInstall:add","daikin:dkContractInstall:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkContractInstall dkContractInstall, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkContractInstall)){
			return form(dkContractInstall, model);
		}
		if(!dkContractInstall.getIsNewRecord()){//编辑表单保存
			DkContractInstall t = dkContractInstallService.get(dkContractInstall.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkContractInstall, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkContractInstallService.save(t);//保存
		}else{//新增表单保存
			dkContractInstallService.save(dkContractInstall);//保存
		}
		addMessage(redirectAttributes, "保存预约安装成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstall/?repage";
	}
	
	/**
	 * 删除预约安装
	 */
	@RequiresPermissions("daikin:dkContractInstall:del")
	@RequestMapping(value = "delete")
	public String delete(DkContractInstall dkContractInstall, RedirectAttributes redirectAttributes) {
		dkContractInstallService.delete(dkContractInstall);
		addMessage(redirectAttributes, "删除预约安装成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstall/?repage";
	}
	
	/**
	 * 批量删除预约安装
	 */
	@RequiresPermissions("daikin:dkContractInstall:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkContractInstallService.delete(dkContractInstallService.get(id));
		}
		addMessage(redirectAttributes, "删除预约安装成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstall/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkContractInstall:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkContractInstall dkContractInstall, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "预约安装"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkContractInstall> page = dkContractInstallService.findPage(new Page<DkContractInstall>(request, response, -1), dkContractInstall);
    		new ExportExcel("预约安装", DkContractInstall.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出预约安装记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstall/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkContractInstall:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkContractInstall> list = ei.getDataList(DkContractInstall.class);
			for (DkContractInstall dkContractInstall : list){
				try{
					dkContractInstallService.save(dkContractInstall);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条预约安装记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条预约安装记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入预约安装失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstall/?repage";
    }
	
	/**
	 * 下载导入预约安装数据模板
	 */
	@RequiresPermissions("daikin:dkContractInstall:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "预约安装数据导入模板.xlsx";
    		List<DkContractInstall> list = Lists.newArrayList(); 
    		new ExportExcel("预约安装数据", DkContractInstall.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractInstall/?repage";
    }
	
	
	/**
	 * 选择合同ID
	 */
	@RequestMapping(value = "selectdkContract")
	public String selectdkContract(DkContract dkContract, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkContract> page = dkContractInstallService.findPageBydkContract(new Page<DkContract>(request, response),  dkContract);
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
	

}