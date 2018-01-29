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
import com.jeeplus.modules.daikin.entity.DkContractOrder;
import com.jeeplus.modules.daikin.service.DkContractOrderService;

/**
 * 订货单Controller
 * @author LD
 * @version 2018-01-29
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkContractOrder")
public class DkContractOrderController extends BaseController {

	@Autowired
	private DkContractOrderService dkContractOrderService;
	
	@ModelAttribute
	public DkContractOrder get(@RequestParam(required=false) String id) {
		DkContractOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkContractOrderService.get(id);
		}
		if (entity == null){
			entity = new DkContractOrder();
		}
		return entity;
	}
	
	/**
	 * 订货单列表页面
	 */
	@RequiresPermissions("daikin:dkContractOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkContractOrder dkContractOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkContractOrder> page = dkContractOrderService.findPage(new Page<DkContractOrder>(request, response), dkContractOrder); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractOrderList";
	}

	/**
	 * 查看，增加，编辑订货单表单页面
	 */
	@RequiresPermissions(value={"daikin:dkContractOrder:view","daikin:dkContractOrder:add","daikin:dkContractOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkContractOrder dkContractOrder, Model model) {
		model.addAttribute("dkContractOrder", dkContractOrder);
		return "modules/daikin/dkContractOrderForm";
	}

	/**
	 * 保存订货单
	 */
	@RequiresPermissions(value={"daikin:dkContractOrder:add","daikin:dkContractOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkContractOrder dkContractOrder, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkContractOrder)){
			return form(dkContractOrder, model);
		}
		if(!dkContractOrder.getIsNewRecord()){//编辑表单保存
			DkContractOrder t = dkContractOrderService.get(dkContractOrder.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkContractOrder, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkContractOrderService.save(t);//保存
		}else{//新增表单保存
			dkContractOrderService.save(dkContractOrder);//保存
		}
		addMessage(redirectAttributes, "保存订货单成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractOrder/?repage";
	}
	
	/**
	 * 删除订货单
	 */
	@RequiresPermissions("daikin:dkContractOrder:del")
	@RequestMapping(value = "delete")
	public String delete(DkContractOrder dkContractOrder, RedirectAttributes redirectAttributes) {
		dkContractOrderService.delete(dkContractOrder);
		addMessage(redirectAttributes, "删除订货单成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractOrder/?repage";
	}
	
	/**
	 * 批量删除订货单
	 */
	@RequiresPermissions("daikin:dkContractOrder:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkContractOrderService.delete(dkContractOrderService.get(id));
		}
		addMessage(redirectAttributes, "删除订货单成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractOrder/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkContractOrder:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkContractOrder dkContractOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订货单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkContractOrder> page = dkContractOrderService.findPage(new Page<DkContractOrder>(request, response, -1), dkContractOrder);
    		new ExportExcel("订货单", DkContractOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出订货单记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractOrder/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkContractOrder:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkContractOrder> list = ei.getDataList(DkContractOrder.class);
			for (DkContractOrder dkContractOrder : list){
				try{
					dkContractOrderService.save(dkContractOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条订货单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条订货单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入订货单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractOrder/?repage";
    }
	
	/**
	 * 下载导入订货单数据模板
	 */
	@RequiresPermissions("daikin:dkContractOrder:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订货单数据导入模板.xlsx";
    		List<DkContractOrder> list = Lists.newArrayList(); 
    		new ExportExcel("订货单数据", DkContractOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractOrder/?repage";
    }
	
	
	/**
	 * 选择合同
	 */
	@RequestMapping(value = "selectdkContract")
	public String selectdkContract(DkContract dkContract, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		dkContract.setProductType("0");
		Page<DkContract> page = dkContractOrderService.findPageBydkContract(new Page<DkContract>(request, response),  dkContract);
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