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
import com.jeeplus.modules.daikin.entity.DkVisit;
import com.jeeplus.modules.daikin.service.DkVisitService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 上门登记Controller
 * @author 菜鸟
 * @version 2017-09-15
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkVisit")
public class DkVisitController extends BaseController {

	@Autowired
	private DkVisitService dkVisitService;
	
	@ModelAttribute
	public DkVisit get(@RequestParam(required=false) String id) {
		DkVisit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkVisitService.get(id);
		}
		if (entity == null){
			entity = new DkVisit();
		}
		return entity;
	}
	
	/**
	 * 记录列表页面
	 */
	@RequiresPermissions("daikin:dkVisit:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkVisit dkVisit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkVisit> page = dkVisitService.findPage(new Page<DkVisit>(request, response), dkVisit); 
		model.addAttribute("page", page);
		return "modules/daikin/dkVisitList";
	}

	/**
	 * 查看，增加，编辑记录表单页面
	 */
	@RequiresPermissions(value={"daikin:dkVisit:view","daikin:dkVisit:add","daikin:dkVisit:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkVisit dkVisit, Model model) {
		model.addAttribute("dkVisit", dkVisit);
		return "modules/daikin/dkVisitForm";
	}

	/**
	 * 保存记录
	 */
	@RequiresPermissions(value={"daikin:dkVisit:add","daikin:dkVisit:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkVisit dkVisit, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkVisit)){
			return form(dkVisit, model);
		}
		if(!dkVisit.getIsNewRecord()){//编辑表单保存
			DkVisit t = dkVisitService.get(dkVisit.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkVisit, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkVisitService.save(t);//保存
		}else{//新增表单保存
			User user = UserUtils.getUser();
			dkVisit.setRecordBy(user);
			dkVisitService.save(dkVisit);//保存
		}
		addMessage(redirectAttributes, "保存记录成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkVisit/?repage";
	}
	
	/**
	 * 删除记录
	 */
	@RequiresPermissions("daikin:dkVisit:del")
	@RequestMapping(value = "delete")
	public String delete(DkVisit dkVisit, RedirectAttributes redirectAttributes) {
		dkVisitService.delete(dkVisit);
		addMessage(redirectAttributes, "删除记录成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkVisit/?repage";
	}
	
	/**
	 * 批量删除记录
	 */
	@RequiresPermissions("daikin:dkVisit:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkVisitService.delete(dkVisitService.get(id));
		}
		addMessage(redirectAttributes, "删除记录成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkVisit/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkVisit:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkVisit dkVisit, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkVisit> page = dkVisitService.findPage(new Page<DkVisit>(request, response, -1), dkVisit);
    		new ExportExcel("记录", DkVisit.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出记录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkVisit/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkVisit:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkVisit> list = ei.getDataList(DkVisit.class);
			for (DkVisit dkVisit : list){
				try{
					dkVisitService.save(dkVisit);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkVisit/?repage";
    }
	
	/**
	 * 下载导入记录数据模板
	 */
	@RequiresPermissions("daikin:dkVisit:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "记录数据导入模板.xlsx";
    		List<DkVisit> list = Lists.newArrayList(); 
    		new ExportExcel("记录数据", DkVisit.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkVisit/?repage";
    }
	
	
	

}