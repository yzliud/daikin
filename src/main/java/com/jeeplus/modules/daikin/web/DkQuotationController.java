/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.web;

import java.io.IOException;
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
import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.service.DkQuotationService;

/**
 * 报价单Controller
 * @author LD
 * @version 2017-04-05
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkQuotation")
public class DkQuotationController extends BaseController {

	@Autowired
	private DkQuotationService dkQuotationService;
	
	@ModelAttribute
	public DkQuotation get(@RequestParam(required=false) String id) {
		DkQuotation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkQuotationService.get(id);
		}
		if (entity == null){
			entity = new DkQuotation();
		}
		return entity;
	}
	
	/**
	 * 报价单列表页面
	 */
	@RequiresPermissions("daikin:dkQuotation:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkQuotation dkQuotation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkQuotation> page = dkQuotationService.findPage(new Page<DkQuotation>(request, response), dkQuotation); 
		model.addAttribute("page", page);
		return "modules/daikin/dkQuotationList";
	}
	
	/**
	 * 未审核列表
	 */
	@RequiresPermissions("daikin:dkQuotation:uncheck")
	@RequestMapping(value = {"uncheck"})
	public String uncheck(DkQuotation dkQuotation, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkQuotation.setReviewStatus(Consts.ReviewStatus_1);
		Page<DkQuotation> page = dkQuotationService.findPage(new Page<DkQuotation>(request, response), dkQuotation); 
		model.addAttribute("page", page);
		return "modules/daikin/dkQuotationUncheckList";
	}
	
	/**
	 * 审核通过列表
	 */
	@RequiresPermissions("daikin:dkQuotation:checkPass")
	@RequestMapping(value = {"checkPass"})
	public String checkPass(DkQuotation dkQuotation, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkQuotation.setReviewStatus(Consts.ReviewStatus_9);
		Page<DkQuotation> page = dkQuotationService.findPage(new Page<DkQuotation>(request, response), dkQuotation); 
		model.addAttribute("page", page);
		return "modules/daikin/dkQuotationCheckPassList";
	}

	/**
	 * 查看，增加，编辑报价单表单页面
	 */
	@RequiresPermissions(value={"daikin:dkQuotation:view","daikin:dkQuotation:add","daikin:dkQuotation:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkQuotation dkQuotation, Model model) {
		model.addAttribute("dkQuotation", dkQuotation);
		return "modules/daikin/dkQuotationForm";
	}

	/**
	 * 保存报价单
	 */
	@RequiresPermissions(value={"daikin:dkQuotation:add","daikin:dkQuotation:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkQuotation dkQuotation, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkQuotation)){
			return form(dkQuotation, model);
		}
		if(!dkQuotation.getIsNewRecord()){//编辑表单保存
			DkQuotation t = dkQuotationService.get(dkQuotation.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkQuotation, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkQuotationService.save(t);//保存
		}else{//新增表单保存
			dkQuotationService.save(dkQuotation);//保存
		}
		addMessage(redirectAttributes, "保存报价单成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkQuotation/?repage";
	}
	
	/**
	 * 删除报价单
	 */
	@RequiresPermissions("daikin:dkQuotation:del")
	@RequestMapping(value = "delete")
	public String delete(DkQuotation dkQuotation, RedirectAttributes redirectAttributes) {
		dkQuotationService.delete(dkQuotation);
		addMessage(redirectAttributes, "删除报价单成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkQuotation/?repage";
	}
	
	/**
	 * 批量删除报价单
	 */
	@RequiresPermissions("daikin:dkQuotation:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkQuotationService.delete(dkQuotationService.get(id));
		}
		addMessage(redirectAttributes, "删除报价单成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkQuotation/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkQuotation:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkQuotation dkQuotation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "报价单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkQuotation> page = dkQuotationService.findPage(new Page<DkQuotation>(request, response, -1), dkQuotation);
    		new ExportExcel("报价单", DkQuotation.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出报价单记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkQuotation/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkQuotation:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkQuotation> list = ei.getDataList(DkQuotation.class);
			for (DkQuotation dkQuotation : list){
				try{
					dkQuotationService.save(dkQuotation);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条报价单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条报价单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入报价单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkQuotation/?repage";
    }
	
	/**
	 * 下载导入报价单数据模板
	 */
	@RequiresPermissions("daikin:dkQuotation:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "报价单数据导入模板.xlsx";
    		List<DkQuotation> list = Lists.newArrayList(); 
    		new ExportExcel("报价单数据", DkQuotation.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkQuotation/?repage";
    }
	
	
	/**
	 * 选择会员
	 */
	@RequestMapping(value = "selectdkMember")
	public String selectdkMember(DkMember dkMember, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkMember> page = dkQuotationService.findPageBydkMember(new Page<DkMember>(request, response),  dkMember);
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
		return "modules/daikin/gridMember";
	}
	
	/**
	 * 提交审核
	 * @param dkQuotation
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "updateReviewStatus")
	public String updateReviewStatus(DkQuotation dkQuotation, RedirectAttributes redirectAttributes) {
		dkQuotationService.updateReviewStatus(dkQuotation);
		addMessage(redirectAttributes, "审核提交成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkQuotation/?repage";
	}

	/**
	 * 查看报价单详细
	 */
	@RequiresPermissions(value={"daikin:dkQuotation:view","daikin:dkQuotation:add","daikin:dkQuotation:edit"},logical=Logical.OR)
	@RequestMapping(value = "detail")
	public String detail(DkQuotation dkQuotation, String checkType, Model model) {
		model.addAttribute("dkQuotation", dkQuotation);
		model.addAttribute("checkType", checkType);
		return "modules/daikin/dkQuotationDetail";
	}
	
	/**
	 * 审核报价单
	 */
	@RequestMapping(value = "checkQuotation")
	public String checkQuotation(DkQuotation dkQuotation, RedirectAttributes redirectAttributes) {
		if(dkQuotation.getReviewStatus().equals(Consts.ReviewStatus_2)){
			addMessage(redirectAttributes, "该报价单已驳回");
		}else{
			addMessage(redirectAttributes, "该报价单审核通过");
		}
		dkQuotation.setIsReview(Consts.IsReview_1);
		dkQuotationService.updateReviewStatus(dkQuotation);
		return "redirect:"+Global.getAdminPath()+"/daikin/dkQuotation/uncheck";
	}
	
	/**
	 * 判断名称是否重复
	 */
	@RequestMapping(value = {"getSingle"})
	public String getSingle(DkQuotation dkQuotation, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		DkQuotation dq = dkQuotationService.getSingle(dkQuotation);
    	String result = "";
		
		if(dq == null){
			result = "{\"rtnCode\":0}";
		}else{
			result = "{\"rtnCode\":500}";
		}
		
		response.reset();
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.getOutputStream().write(result.getBytes("utf-8"));
		response.getOutputStream().flush();

		return null;
		
	}

}