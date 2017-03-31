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

import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.entity.DkMember;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.modules.daikin.service.DkContractService;

/**
 * 合同Controller
 * @author LD
 * @version 2017-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkContract")
public class DkContractController extends BaseController {

	@Autowired
	private DkContractService dkContractService;
	
	@ModelAttribute
	public DkContract get(@RequestParam(required=false) String id) {
		DkContract entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkContractService.get(id);
		}
		if (entity == null){
			entity = new DkContract();
		}
		return entity;
	}
	
	/**
	 * 合同列表页面
	 */
	@RequiresPermissions("daikin:dkContract:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkContract dkContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkContract> page = dkContractService.findPage(new Page<DkContract>(request, response), dkContract); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractList";
	}

	/**
	 * 查看，增加，编辑合同表单页面
	 */
	@RequiresPermissions(value={"daikin:dkContract:view","daikin:dkContract:add","daikin:dkContract:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkContract dkContract, Model model) {
		model.addAttribute("dkContract", dkContract);
		return "modules/daikin/dkContractForm";
	}

	/**
	 * 保存合同
	 */
	@RequiresPermissions(value={"daikin:dkContract:add","daikin:dkContract:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkContract dkContract, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkContract)){
			return form(dkContract, model);
		}
		if(!dkContract.getIsNewRecord()){//编辑表单保存
			DkContract t = dkContractService.get(dkContract.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkContract, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkContractService.save(t);//保存
		}else{//新增表单保存
			dkContractService.save(dkContract);//保存
		}
		addMessage(redirectAttributes, "保存合同成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
	}
	
	/**
	 * 删除合同
	 */
	@RequiresPermissions("daikin:dkContract:del")
	@RequestMapping(value = "delete")
	public String delete(DkContract dkContract, RedirectAttributes redirectAttributes) {
		dkContractService.delete(dkContract);
		addMessage(redirectAttributes, "删除合同成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
	}
	
	/**
	 * 批量删除合同
	 */
	@RequiresPermissions("daikin:dkContract:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkContractService.delete(dkContractService.get(id));
		}
		addMessage(redirectAttributes, "删除合同成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkContract:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkContract dkContract, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "合同"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkContract> page = dkContractService.findPage(new Page<DkContract>(request, response, -1), dkContract);
    		new ExportExcel("合同", DkContract.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出合同记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkContract:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkContract> list = ei.getDataList(DkContract.class);
			for (DkContract dkContract : list){
				try{
					dkContractService.save(dkContract);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条合同记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条合同记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入合同失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
    }
	
	/**
	 * 下载导入合同数据模板
	 */
	@RequiresPermissions("daikin:dkContract:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "合同数据导入模板.xlsx";
    		List<DkContract> list = Lists.newArrayList(); 
    		new ExportExcel("合同数据", DkContract.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContract/?repage";
    }
	
	
	/**
	 * 选择报价单ID
	 */
	@RequestMapping(value = "selectdkQuotation")
	public String selectdkQuotation(DkQuotation dkQuotation, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkQuotation> page = dkContractService.findPageBydkQuotation(new Page<DkQuotation>(request, response),  dkQuotation);
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
		model.addAttribute("obj", dkQuotation);
		model.addAttribute("page", page);
		return "modules/sys/gridselect";
	}
	/**
	 * 选择会员ID
	 */
	@RequestMapping(value = "selectdkMember")
	public String selectdkMember(DkMember dkMember, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkMember> page = dkContractService.findPageBydkMember(new Page<DkMember>(request, response),  dkMember);
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
		model.addAttribute("obj", dkMember);
		model.addAttribute("page", page);
		return "modules/sys/gridselect";
	}
	

}