/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.daikin.entity.SysDict;
import com.jeeplus.modules.daikin.service.SysDictService;

/**
 * 类别Controller
 * @author LD
 * @version 2017-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/sysDict")
public class SysDictController extends BaseController {

	@Autowired
	private SysDictService sysDictService;
	
	@ModelAttribute
	public SysDict get(@RequestParam(required=false) String id) {
		SysDict entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysDictService.get(id);
		}
		if (entity == null){
			entity = new SysDict();
		}
		return entity;
	}
	
	/**
	 * 类别列表页面
	 */
	@RequiresPermissions("daikin:sysDict:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysDict sysDict, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysDict> page = sysDictService.findPage(new Page<SysDict>(request, response), sysDict); 
		model.addAttribute("page", page);
		return "modules/daikin/sysDictList";
	}

	/**
	 * 查看，增加，编辑类别表单页面
	 */
	@RequestMapping(value = "form")
	public String form(SysDict sysDict, Model model) {
		model.addAttribute("sysDict", sysDict);
		return "modules/daikin/sysDictForm";
	}

	/**
	 * 保存类别
	 */
	@RequestMapping(value = "save")
	public String save(SysDict sysDict, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysDict)){
			return form(sysDict, model);
		}
		if(!sysDict.getIsNewRecord()){//编辑表单保存
			SysDict t = sysDictService.get(sysDict.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysDict, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysDictService.save(t);//保存
		}else{//新增表单保存
			sysDictService.save(sysDict);//保存
		}
		addMessage(redirectAttributes, "保存成功");
		if(sysDict.getType().equals("brand_id")){
			return "redirect:"+Global.getAdminPath()+"/daikin/sysDict/brandList";
		}else if(sysDict.getType().equals("product_type")){
			return "redirect:"+Global.getAdminPath()+"/daikin/sysDict/productTypeList";
		}else if(sysDict.getType().equals("classify_id")){
			return "redirect:"+Global.getAdminPath()+"/daikin/sysDict/classifyList";
		}else{
			return "redirect:"+Global.getAdminPath()+"/daikin/sysDict/?repage";
		}
	}
	
	/**
	 * 删除类别
	 */
	@RequestMapping(value = "delete")
	public String delete(SysDict sysDict, RedirectAttributes redirectAttributes) {
		sysDictService.delete(sysDict);
		addMessage(redirectAttributes, "删除成功");
		if(sysDict.getType().equals("brand_id")){
			return "redirect:"+Global.getAdminPath()+"/daikin/sysDict/brandList";
		}else if(sysDict.getType().equals("product_type")){
			return "redirect:"+Global.getAdminPath()+"/daikin/sysDict/productTypeList";
		}else if(sysDict.getType().equals("classify_id")){
			return "redirect:"+Global.getAdminPath()+"/daikin/sysDict/classifyList";
		}else{
			return "redirect:"+Global.getAdminPath()+"/daikin/sysDict/?repage";
		}
	}
	
	/**
	 * 批量删除类别
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysDictService.delete(sysDictService.get(id));
		}
		addMessage(redirectAttributes, "删除类别成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/sysDict/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:sysDict:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysDict sysDict, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "类别"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysDict> page = sysDictService.findPage(new Page<SysDict>(request, response, -1), sysDict);
    		new ExportExcel("类别", SysDict.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出类别记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/sysDict/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:sysDict:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysDict> list = ei.getDataList(SysDict.class);
			for (SysDict sysDict : list){
				try{
					sysDictService.save(sysDict);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条类别记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条类别记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入类别失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/sysDict/?repage";
    }
	
	/**
	 * 下载导入类别数据模板
	 */
	@RequiresPermissions("daikin:sysDict:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "类别数据导入模板.xlsx";
    		List<SysDict> list = Lists.newArrayList(); 
    		new ExportExcel("类别数据", SysDict.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/sysDict/?repage";
    }
	
	
	/**
	 * 品牌列表页面
	 */
	@RequiresPermissions("daikin:sysDict:brandList")
	@RequestMapping(value = {"brandList"})
	public String brandList(SysDict sysDict, HttpServletRequest request, HttpServletResponse response, Model model) {
		sysDict.setType("brand_id");
		Page<SysDict> page = sysDictService.findPage(new Page<SysDict>(request, response), sysDict); 
		model.addAttribute("page", page);
		return "modules/daikin/sysDictBrandList";
	}

	/**
	 * 品牌表单页面
	 */
	@RequestMapping(value = "brandForm")
	public String brandForm(SysDict sysDict, Model model) {
		sysDict.setType("brand_id");
		model.addAttribute("sysDict", sysDict);
		return "modules/daikin/sysDictForm";
	}
	
	/**
	 * 商品类型列表页面
	 */
	@RequiresPermissions("daikin:sysDict:productTypeList")
	@RequestMapping(value = {"productTypeList"})
	public String productTypeList(SysDict sysDict, HttpServletRequest request, HttpServletResponse response, Model model) {
		sysDict.setType("product_type");
		Page<SysDict> page = sysDictService.findPage(new Page<SysDict>(request, response), sysDict); 
		model.addAttribute("page", page);
		return "modules/daikin/sysDictProductTypeList";
	}

	/**
	 * 商品类型表单页面
	 */
	@RequestMapping(value = "productTypeForm")
	public String productTypeForm(SysDict sysDict, Model model) {
		sysDict.setType("product_type");
		model.addAttribute("sysDict", sysDict);
		return "modules/daikin/sysDictForm";
	}
	
	/**
	 * 分类列表页面
	 */
	@RequiresPermissions("daikin:sysDict:classifyList")
	@RequestMapping(value = {"classifyList"})
	public String classifyList(SysDict sysDict, HttpServletRequest request, HttpServletResponse response, Model model) {
		sysDict.setType("classify_id");
		Page<SysDict> page = sysDictService.findPage(new Page<SysDict>(request, response), sysDict); 
		model.addAttribute("page", page);
		return "modules/daikin/sysDictClassifyList";
	}

	/**
	 * 分类表单页面
	 */
	@RequestMapping(value = "classifyForm")
	public String classifyForm(SysDict sysDict, Model model) {
		sysDict.setType("classify_id");
		model.addAttribute("sysDict", sysDict);
		return "modules/daikin/sysDictForm";
	}

}