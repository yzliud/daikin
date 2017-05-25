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
import com.jeeplus.modules.daikin.entity.DkContractService;
import com.jeeplus.modules.daikin.service.DkContractServiceService;

/**
 * 维保记录Controller
 * @author LD
 * @version 2017-05-06
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkContractService")
public class DkContractServiceController extends BaseController {

	@Autowired
	private DkContractServiceService dkContractServiceService;
	
	@ModelAttribute
	public DkContractService get(@RequestParam(required=false) String id) {
		DkContractService entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkContractServiceService.get(id);
		}
		if (entity == null){
			entity = new DkContractService();
		}
		return entity;
	}
	
	/**
	 * 维保记录列表页面
	 */
	@RequiresPermissions("daikin:dkContractService:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkContractService dkContractService, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkContractService> page = dkContractServiceService.findPage(new Page<DkContractService>(request, response), dkContractService); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractServiceList";
	}
	
	@RequiresPermissions("daikin:dkContractService:realList")
	@RequestMapping(value = {"realList"})
	public String realList(DkContractService dkContractService, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContractService.setReviewStatus(Consts.ReviewStatus_9);
		Page<DkContractService> page = dkContractServiceService.findPage(new Page<DkContractService>(request, response), dkContractService); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractServiceList_real";
	}
	
	@RequiresPermissions("daikin:dkContractService:checkList")
	@RequestMapping(value = {"checkList"})
	public String checkList(DkContractService dkContractService, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContractService.setReviewStatus(Consts.ReviewStatus_1);
		Page<DkContractService> page = dkContractServiceService.findPage(new Page<DkContractService>(request, response), dkContractService); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractServiceList_check";
	}

	/**
	 * 查看，增加，编辑维保记录表单页面
	 */
	@RequiresPermissions(value={"daikin:dkContractService:view","daikin:dkContractService:add","daikin:dkContractService:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkContractService dkContractService, Model model) {
		model.addAttribute("dkContractService", dkContractService);
		return "modules/daikin/dkContractServiceForm";
	}
	
	/**
	 * detail
	 */
	@RequestMapping(value = "detail")
	public String detail(DkContractService dkContractService, String checkType, Model model) {
		model.addAttribute("dkContractService", dkContractService);
		model.addAttribute("checkType", checkType);
		return "modules/daikin/dkContractServiceForm_detail";
	}

	/**
	 * 保存维保记录
	 */
	@RequiresPermissions(value={"daikin:dkContractService:add","daikin:dkContractService:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkContractService dkContractService, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkContractService)){
			return form(dkContractService, model);
		}
		dkContractService.setReviewStatus(Consts.ReviewStatus_1);
		if(!dkContractService.getIsNewRecord()){//编辑表单保存
			DkContractService t = dkContractServiceService.get(dkContractService.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkContractService, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkContractServiceService.save(t);//保存
		}else{//新增表单保存
			dkContractServiceService.save(dkContractService);//保存
		}
		addMessage(redirectAttributes, "保存维保记录成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractService/?repage";
	}
	
	/**
	 * 删除维保记录
	 */
	@RequiresPermissions("daikin:dkContractService:del")
	@RequestMapping(value = "delete")
	public String delete(DkContractService dkContractService, RedirectAttributes redirectAttributes) {
		dkContractServiceService.delete(dkContractService);
		addMessage(redirectAttributes, "删除维保记录成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractService/?repage";
	}
	
	/**
	 * 批量删除维保记录
	 */
	@RequiresPermissions("daikin:dkContractService:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkContractServiceService.delete(dkContractServiceService.get(id));
		}
		addMessage(redirectAttributes, "删除维保记录成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractService/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkContractService dkContractService, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "维保记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkContractService> page = dkContractServiceService.findPage(new Page<DkContractService>(request, response, -1), dkContractService);
    		new ExportExcel("维保记录", DkContractService.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出维保记录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractService/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkContractService:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkContractService> list = ei.getDataList(DkContractService.class);
			for (DkContractService dkContractService : list){
				try{
					dkContractServiceService.save(dkContractService);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条维保记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条维保记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入维保记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractService/?repage";
    }
	
	/**
	 * 下载导入维保记录数据模板
	 */
	@RequiresPermissions("daikin:dkContractService:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "维保记录数据导入模板.xlsx";
    		List<DkContractService> list = Lists.newArrayList(); 
    		new ExportExcel("维保记录数据", DkContractService.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractService/?repage";
    }
	
	
	/**
	 * 选择合同ID
	 */
	@RequestMapping(value = "selectdkContract")
	public String selectdkContract(DkContract dkContract, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContract.setContractFlag(Consts.ContractFlag_0);
		Page<DkContract> page = dkContractServiceService.findPageBydkContract(new Page<DkContract>(request, response),  dkContract);
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
	
	/**
	 * 审核
	 * @param dkContract
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "checkService")
	public String checkService(DkContractService dkContractService, RedirectAttributes redirectAttributes) {
		String isReview = "";
		String url = "";
		if(dkContractService.getReviewStatus().equals(Consts.ReviewStatus_1)){
			addMessage(redirectAttributes, "审核提交成功");
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContractService/?repage";
		}else if(dkContractService.getReviewStatus().equals(Consts.ReviewStatus_2)){
			addMessage(redirectAttributes, "该维保记录已驳回");
			isReview = Consts.IsReview_1;
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContractService/checkList";
		}else{
			addMessage(redirectAttributes, "该维保记录审核通过");
			isReview = Consts.IsReview_1;
			url = "redirect:"+Global.getAdminPath()+"/daikin/dkContractService/checkList";
		}
		dkContractService.setIsReview(isReview);
		dkContractServiceService.checkService(dkContractService);
		return url;
	}
	

}