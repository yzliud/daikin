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
import com.jeeplus.modules.daikin.entity.DkContractDisclose;
import com.jeeplus.modules.daikin.service.DkContractDiscloseService;

/**
 * 预约交底Controller
 * @author LD
 * @version 2017-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkContractDisclose")
public class DkContractDiscloseController extends BaseController {

	@Autowired
	private DkContractDiscloseService dkContractDiscloseService;
	
	@ModelAttribute
	public DkContractDisclose get(@RequestParam(required=false) String id) {
		DkContractDisclose entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkContractDiscloseService.get(id);
		}
		if (entity == null){
			entity = new DkContractDisclose();
		}
		return entity;
	}
	
	/**
	 * 预约交底列表页面
	 */
	@RequiresPermissions("daikin:dkContractDisclose:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkContractDisclose dkContractDisclose, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkContractDisclose> page = dkContractDiscloseService.findPage(new Page<DkContractDisclose>(request, response), dkContractDisclose); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractDiscloseList";
	}

	/**
	 * 查看，增加，编辑预约交底表单页面
	 */
	@RequiresPermissions(value={"daikin:dkContractDisclose:view","daikin:dkContractDisclose:add","daikin:dkContractDisclose:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkContractDisclose dkContractDisclose, Model model) {
		model.addAttribute("dkContractDisclose", dkContractDisclose);
		return "modules/daikin/dkContractDiscloseForm";
	}

	/**
	 * 保存预约交底
	 */
	@RequiresPermissions(value={"daikin:dkContractDisclose:add","daikin:dkContractDisclose:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkContractDisclose dkContractDisclose, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkContractDisclose)){
			return form(dkContractDisclose, model);
		}
		if(!dkContractDisclose.getIsNewRecord()){//编辑表单保存
			DkContractDisclose t = dkContractDiscloseService.get(dkContractDisclose.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkContractDisclose, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkContractDiscloseService.save(t);//保存
		}else{//新增表单保存
			dkContractDiscloseService.save(dkContractDisclose);//保存
		}
		addMessage(redirectAttributes, "保存预约交底成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractDisclose/?repage";
	}
	
	/**
	 * 删除预约交底
	 */
	@RequiresPermissions("daikin:dkContractDisclose:del")
	@RequestMapping(value = "delete")
	public String delete(DkContractDisclose dkContractDisclose, RedirectAttributes redirectAttributes) {
		dkContractDiscloseService.delete(dkContractDisclose);
		addMessage(redirectAttributes, "删除预约交底成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractDisclose/?repage";
	}
	
	/**
	 * 批量删除预约交底
	 */
	@RequiresPermissions("daikin:dkContractDisclose:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkContractDiscloseService.delete(dkContractDiscloseService.get(id));
		}
		addMessage(redirectAttributes, "删除预约交底成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractDisclose/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkContractDisclose:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkContractDisclose dkContractDisclose, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "预约交底"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkContractDisclose> page = dkContractDiscloseService.findPage(new Page<DkContractDisclose>(request, response, -1), dkContractDisclose);
    		new ExportExcel("预约交底", DkContractDisclose.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出预约交底记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractDisclose/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkContractDisclose:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkContractDisclose> list = ei.getDataList(DkContractDisclose.class);
			for (DkContractDisclose dkContractDisclose : list){
				try{
					dkContractDiscloseService.save(dkContractDisclose);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条预约交底记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条预约交底记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入预约交底失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractDisclose/?repage";
    }
	
	/**
	 * 下载导入预约交底数据模板
	 */
	@RequiresPermissions("daikin:dkContractDisclose:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "预约交底数据导入模板.xlsx";
    		List<DkContractDisclose> list = Lists.newArrayList(); 
    		new ExportExcel("预约交底数据", DkContractDisclose.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractDisclose/?repage";
    }
	
	
	/**
	 * 选择合同ID
	 */
	@RequestMapping(value = "selectdkContract")
	public String selectdkContract(DkContract dkContract, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContract.setContractFlag(Consts.ContractFlag_0);
		Page<DkContract> page = dkContractDiscloseService.findPageBydkContract(new Page<DkContract>(request, response),  dkContract);
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