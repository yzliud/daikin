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
import com.jeeplus.modules.daikin.entity.DkContractSchedule;
import com.jeeplus.modules.daikin.service.DkContractScheduleService;

/**
 * 安装进度Controller
 * @author LD
 * @version 2017-04-16
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkContractSchedule")
public class DkContractScheduleController extends BaseController {

	@Autowired
	private DkContractScheduleService dkContractScheduleService;
	
	@ModelAttribute
	public DkContractSchedule get(@RequestParam(required=false) String id) {
		DkContractSchedule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkContractScheduleService.get(id);
		}
		if (entity == null){
			entity = new DkContractSchedule();
		}
		return entity;
	}
	
	/**
	 * 安装进度列表页面
	 */
	@RequiresPermissions("daikin:dkContractSchedule:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkContractSchedule dkContractSchedule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkContractSchedule> page = dkContractScheduleService.findPage(new Page<DkContractSchedule>(request, response), dkContractSchedule); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractScheduleList";
	}

	/**
	 * 查看，增加，编辑安装进度表单页面
	 */
	@RequiresPermissions(value={"daikin:dkContractSchedule:view","daikin:dkContractSchedule:add","daikin:dkContractSchedule:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkContractSchedule dkContractSchedule, Model model) {
		model.addAttribute("dkContractSchedule", dkContractSchedule);
		return "modules/daikin/dkContractScheduleForm";
	}

	/**
	 * 保存安装进度
	 */
	@RequiresPermissions(value={"daikin:dkContractSchedule:add","daikin:dkContractSchedule:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkContractSchedule dkContractSchedule, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkContractSchedule)){
			return form(dkContractSchedule, model);
		}
		if(!dkContractSchedule.getIsNewRecord()){//编辑表单保存
			DkContractSchedule t = dkContractScheduleService.get(dkContractSchedule.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkContractSchedule, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkContractScheduleService.save(t);//保存
		}else{//新增表单保存
			dkContractScheduleService.save(dkContractSchedule);//保存
		}
		addMessage(redirectAttributes, "保存安装进度成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractSchedule/?repage";
	}
	
	/**
	 * 删除安装进度
	 */
	@RequiresPermissions("daikin:dkContractSchedule:del")
	@RequestMapping(value = "delete")
	public String delete(DkContractSchedule dkContractSchedule, RedirectAttributes redirectAttributes) {
		dkContractScheduleService.delete(dkContractSchedule);
		addMessage(redirectAttributes, "删除安装进度成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractSchedule/?repage";
	}
	
	/**
	 * 批量删除安装进度
	 */
	@RequiresPermissions("daikin:dkContractSchedule:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkContractScheduleService.delete(dkContractScheduleService.get(id));
		}
		addMessage(redirectAttributes, "删除安装进度成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractSchedule/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkContractSchedule:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkContractSchedule dkContractSchedule, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "安装进度"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkContractSchedule> page = dkContractScheduleService.findPage(new Page<DkContractSchedule>(request, response, -1), dkContractSchedule);
    		new ExportExcel("安装进度", DkContractSchedule.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出安装进度记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractSchedule/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkContractSchedule:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkContractSchedule> list = ei.getDataList(DkContractSchedule.class);
			for (DkContractSchedule dkContractSchedule : list){
				try{
					dkContractScheduleService.save(dkContractSchedule);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条安装进度记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条安装进度记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入安装进度失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractSchedule/?repage";
    }
	
	/**
	 * 下载导入安装进度数据模板
	 */
	@RequiresPermissions("daikin:dkContractSchedule:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "安装进度数据导入模板.xlsx";
    		List<DkContractSchedule> list = Lists.newArrayList(); 
    		new ExportExcel("安装进度数据", DkContractSchedule.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractSchedule/?repage";
    }
	
	
	/**
	 * 选择合同ID
	 */
	@RequestMapping(value = "selectdkContract")
	public String selectdkContract(DkContract dkContract, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContract.setContractFlag(Consts.ContractFlag_0);
		Page<DkContract> page = dkContractScheduleService.findPageBydkContract(new Page<DkContract>(request, response),  dkContract);
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