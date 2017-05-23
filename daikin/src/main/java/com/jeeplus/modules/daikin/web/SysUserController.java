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
import com.jeeplus.modules.daikin.entity.SysUser;
import com.jeeplus.modules.daikin.service.SysUserService;

/**
 * 用户Controller
 * @author LD
 * @version 2017-04-15
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/sysUser")
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;
	
	@ModelAttribute
	public SysUser get(@RequestParam(required=false) String id) {
		SysUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserService.get(id);
		}
		if (entity == null){
			entity = new SysUser();
		}
		return entity;
	}
	
	/**
	 * 用户列表页面
	 */
	@RequiresPermissions("daikin:sysUser:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUser> page = sysUserService.findPage(new Page<SysUser>(request, response), sysUser); 
		model.addAttribute("page", page);
		return "modules/daikin/sysUserList";
	}

	/**
	 * 查看，增加，编辑用户表单页面
	 */
	@RequiresPermissions(value={"daikin:sysUser:view","daikin:sysUser:add","daikin:sysUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysUser sysUser, Model model) {
		model.addAttribute("sysUser", sysUser);
		return "modules/daikin/sysUserForm";
	}

	/**
	 * 保存用户
	 */
	@RequiresPermissions(value={"daikin:sysUser:add","daikin:sysUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysUser sysUser, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysUser)){
			return form(sysUser, model);
		}
		if(!sysUser.getIsNewRecord()){//编辑表单保存
			SysUser t = sysUserService.get(sysUser.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysUser, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysUserService.save(t);//保存
		}else{//新增表单保存
			sysUserService.save(sysUser);//保存
		}
		addMessage(redirectAttributes, "保存用户成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/sysUser/?repage";
	}
	
	/**
	 * 删除用户
	 */
	@RequiresPermissions("daikin:sysUser:del")
	@RequestMapping(value = "delete")
	public String delete(SysUser sysUser, RedirectAttributes redirectAttributes) {
		sysUserService.delete(sysUser);
		addMessage(redirectAttributes, "删除用户成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/sysUser/?repage";
	}
	
	/**
	 * 批量删除用户
	 */
	@RequiresPermissions("daikin:sysUser:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysUserService.delete(sysUserService.get(id));
		}
		addMessage(redirectAttributes, "删除用户成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/sysUser/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:sysUser:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysUser> page = sysUserService.findPage(new Page<SysUser>(request, response, -1), sysUser);
    		new ExportExcel("用户", SysUser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/sysUser/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:sysUser:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysUser> list = ei.getDataList(SysUser.class);
			for (SysUser sysUser : list){
				try{
					sysUserService.save(sysUser);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/sysUser/?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 */
	@RequiresPermissions("daikin:sysUser:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<SysUser> list = Lists.newArrayList(); 
    		new ExportExcel("用户数据", SysUser.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/sysUser/?repage";
    }
	
	
	

}