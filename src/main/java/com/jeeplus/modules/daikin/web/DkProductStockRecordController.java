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

import com.jeeplus.modules.daikin.entity.DkProduct;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.daikin.entity.DkProductStockRecord;
import com.jeeplus.modules.daikin.service.DkProductStockRecordService;

/**
 * 商品进销存Controller
 * @author LD
 * @version 2017-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkProductStockRecord")
public class DkProductStockRecordController extends BaseController {

	@Autowired
	private DkProductStockRecordService dkProductStockRecordService;
	
	@ModelAttribute
	public DkProductStockRecord get(@RequestParam(required=false) String id) {
		DkProductStockRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkProductStockRecordService.get(id);
		}
		if (entity == null){
			entity = new DkProductStockRecord();
		}
		return entity;
	}
	
	/**
	 * 商品进销存列表页面
	 */
	@RequiresPermissions("daikin:dkProductStockRecord:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkProductStockRecord dkProductStockRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkProductStockRecord> page = dkProductStockRecordService.findPage(new Page<DkProductStockRecord>(request, response), dkProductStockRecord); 
		model.addAttribute("page", page);
		return "modules/daikin/dkProductStockRecordList";
	}

	/**
	 * 查看，增加，编辑商品进销存表单页面
	 */
	@RequiresPermissions(value={"daikin:dkProductStockRecord:view","daikin:dkProductStockRecord:add","daikin:dkProductStockRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkProductStockRecord dkProductStockRecord, Model model) {
		model.addAttribute("dkProductStockRecord", dkProductStockRecord);
		return "modules/daikin/dkProductStockRecordForm";
	}

	/**
	 * 保存商品进销存
	 */
	@RequiresPermissions(value={"daikin:dkProductStockRecord:add","daikin:dkProductStockRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkProductStockRecord dkProductStockRecord, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkProductStockRecord)){
			return form(dkProductStockRecord, model);
		}
		if(!dkProductStockRecord.getIsNewRecord()){//编辑表单保存
			DkProductStockRecord t = dkProductStockRecordService.get(dkProductStockRecord.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkProductStockRecord, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkProductStockRecordService.save(t);//保存
		}else{//新增表单保存
			dkProductStockRecordService.save(dkProductStockRecord);//保存
		}
		addMessage(redirectAttributes, "保存商品进销存成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkProductStockRecord/?repage";
	}
	
	/**
	 * 删除商品进销存
	 */
	@RequiresPermissions("daikin:dkProductStockRecord:del")
	@RequestMapping(value = "delete")
	public String delete(DkProductStockRecord dkProductStockRecord, RedirectAttributes redirectAttributes) {
		dkProductStockRecordService.delete(dkProductStockRecord);
		addMessage(redirectAttributes, "删除商品进销存成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkProductStockRecord/?repage";
	}
	
	/**
	 * 批量删除商品进销存
	 */
	@RequiresPermissions("daikin:dkProductStockRecord:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkProductStockRecordService.delete(dkProductStockRecordService.get(id));
		}
		addMessage(redirectAttributes, "删除商品进销存成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkProductStockRecord/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkProductStockRecord:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkProductStockRecord dkProductStockRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品进销存"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkProductStockRecord> page = dkProductStockRecordService.findPage(new Page<DkProductStockRecord>(request, response, -1), dkProductStockRecord);
    		new ExportExcel("商品进销存", DkProductStockRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品进销存记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkProductStockRecord/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkProductStockRecord:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkProductStockRecord> list = ei.getDataList(DkProductStockRecord.class);
			for (DkProductStockRecord dkProductStockRecord : list){
				try{
					dkProductStockRecordService.save(dkProductStockRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品进销存记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品进销存记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品进销存失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkProductStockRecord/?repage";
    }
	
	/**
	 * 下载导入商品进销存数据模板
	 */
	@RequiresPermissions("daikin:dkProductStockRecord:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品进销存数据导入模板.xlsx";
    		List<DkProductStockRecord> list = Lists.newArrayList(); 
    		new ExportExcel("商品进销存数据", DkProductStockRecord.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkProductStockRecord/?repage";
    }
	
	
	/**
	 * 选择商品ID
	 */
	@RequestMapping(value = "selectdkProduct")
	public String selectdkProduct(DkProduct dkProduct, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkProduct> page = dkProductStockRecordService.findPageBydkProduct(new Page<DkProduct>(request, response),  dkProduct);
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
		model.addAttribute("obj", dkProduct);
		model.addAttribute("page", page);
		return "modules/sys/gridselect";
	}
	

}