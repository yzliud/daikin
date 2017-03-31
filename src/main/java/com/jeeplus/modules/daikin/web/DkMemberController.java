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
import com.jeeplus.modules.daikin.entity.DkMember;
import com.jeeplus.modules.daikin.service.DkMemberService;

/**
 * 会员Controller
 * @author LD
 * @version 2017-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkMember")
public class DkMemberController extends BaseController {

	@Autowired
	private DkMemberService dkMemberService;
	
	@ModelAttribute
	public DkMember get(@RequestParam(required=false) String id) {
		DkMember entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkMemberService.get(id);
		}
		if (entity == null){
			entity = new DkMember();
		}
		return entity;
	}
	
	/**
	 * 会员列表页面
	 */
	@RequiresPermissions("daikin:dkMember:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkMember dkMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkMember> page = dkMemberService.findPage(new Page<DkMember>(request, response), dkMember); 
		model.addAttribute("page", page);
		return "modules/daikin/dkMemberList";
	}

	/**
	 * 查看，增加，编辑会员表单页面
	 */
	@RequiresPermissions(value={"daikin:dkMember:view","daikin:dkMember:add","daikin:dkMember:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkMember dkMember, Model model) {
		model.addAttribute("dkMember", dkMember);
		return "modules/daikin/dkMemberForm";
	}

	/**
	 * 保存会员
	 */
	@RequiresPermissions(value={"daikin:dkMember:add","daikin:dkMember:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkMember dkMember, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkMember)){
			return form(dkMember, model);
		}
		if(!dkMember.getIsNewRecord()){//编辑表单保存
			DkMember t = dkMemberService.get(dkMember.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkMember, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkMemberService.save(t);//保存
		}else{//新增表单保存
			dkMemberService.save(dkMember);//保存
		}
		addMessage(redirectAttributes, "保存会员成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkMember/?repage";
	}
	
	/**
	 * 删除会员
	 */
	@RequiresPermissions("daikin:dkMember:del")
	@RequestMapping(value = "delete")
	public String delete(DkMember dkMember, RedirectAttributes redirectAttributes) {
		dkMemberService.delete(dkMember);
		addMessage(redirectAttributes, "删除会员成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkMember/?repage";
	}
	
	/**
	 * 批量删除会员
	 */
	@RequiresPermissions("daikin:dkMember:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkMemberService.delete(dkMemberService.get(id));
		}
		addMessage(redirectAttributes, "删除会员成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkMember/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkMember:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkMember dkMember, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会员"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkMember> page = dkMemberService.findPage(new Page<DkMember>(request, response, -1), dkMember);
    		new ExportExcel("会员", DkMember.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出会员记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkMember/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkMember:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkMember> list = ei.getDataList(DkMember.class);
			for (DkMember dkMember : list){
				try{
					dkMemberService.save(dkMember);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会员记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条会员记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入会员失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkMember/?repage";
    }
	
	/**
	 * 下载导入会员数据模板
	 */
	@RequiresPermissions("daikin:dkMember:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会员数据导入模板.xlsx";
    		List<DkMember> list = Lists.newArrayList(); 
    		new ExportExcel("会员数据", DkMember.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkMember/?repage";
    }
	
	
	

}