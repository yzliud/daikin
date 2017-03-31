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
import com.jeeplus.modules.daikin.entity.DkWorker;
import com.jeeplus.modules.daikin.service.DkWorkerService;

/**
 * 微信用户Controller
 * @author LD
 * @version 2017-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkWorker")
public class DkWorkerController extends BaseController {

	@Autowired
	private DkWorkerService dkWorkerService;
	
	@ModelAttribute
	public DkWorker get(@RequestParam(required=false) String id) {
		DkWorker entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkWorkerService.get(id);
		}
		if (entity == null){
			entity = new DkWorker();
		}
		return entity;
	}
	
	/**
	 * 微信用户列表页面
	 */
	@RequiresPermissions("daikin:dkWorker:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkWorker dkWorker, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkWorker> page = dkWorkerService.findPage(new Page<DkWorker>(request, response), dkWorker); 
		model.addAttribute("page", page);
		return "modules/daikin/dkWorkerList";
	}

	/**
	 * 查看，增加，编辑微信用户表单页面
	 */
	@RequiresPermissions(value={"daikin:dkWorker:view","daikin:dkWorker:add","daikin:dkWorker:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkWorker dkWorker, Model model) {
		model.addAttribute("dkWorker", dkWorker);
		return "modules/daikin/dkWorkerForm";
	}

	/**
	 * 保存微信用户
	 */
	@RequiresPermissions(value={"daikin:dkWorker:add","daikin:dkWorker:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkWorker dkWorker, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkWorker)){
			return form(dkWorker, model);
		}
		if(!dkWorker.getIsNewRecord()){//编辑表单保存
			DkWorker t = dkWorkerService.get(dkWorker.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkWorker, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkWorkerService.save(t);//保存
		}else{//新增表单保存
			dkWorkerService.save(dkWorker);//保存
		}
		addMessage(redirectAttributes, "保存微信用户成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkWorker/?repage";
	}
	
	/**
	 * 删除微信用户
	 */
	@RequiresPermissions("daikin:dkWorker:del")
	@RequestMapping(value = "delete")
	public String delete(DkWorker dkWorker, RedirectAttributes redirectAttributes) {
		dkWorkerService.delete(dkWorker);
		addMessage(redirectAttributes, "删除微信用户成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkWorker/?repage";
	}
	
	/**
	 * 批量删除微信用户
	 */
	@RequiresPermissions("daikin:dkWorker:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkWorkerService.delete(dkWorkerService.get(id));
		}
		addMessage(redirectAttributes, "删除微信用户成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkWorker/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkWorker:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkWorker dkWorker, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微信用户"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkWorker> page = dkWorkerService.findPage(new Page<DkWorker>(request, response, -1), dkWorker);
    		new ExportExcel("微信用户", DkWorker.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出微信用户记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkWorker/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkWorker:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkWorker> list = ei.getDataList(DkWorker.class);
			for (DkWorker dkWorker : list){
				try{
					dkWorkerService.save(dkWorker);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条微信用户记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条微信用户记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入微信用户失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkWorker/?repage";
    }
	
	/**
	 * 下载导入微信用户数据模板
	 */
	@RequiresPermissions("daikin:dkWorker:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微信用户数据导入模板.xlsx";
    		List<DkWorker> list = Lists.newArrayList(); 
    		new ExportExcel("微信用户数据", DkWorker.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkWorker/?repage";
    }
	
	
	

}