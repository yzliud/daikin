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
import com.jeeplus.modules.daikin.entity.DkContractProduct;
import com.jeeplus.modules.daikin.service.DkContractProductService;

/**
 * 合同商品Controller
 * @author LD
 * @version 2017-04-14
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkContractProduct")
public class DkContractProductController extends BaseController {

	@Autowired
	private DkContractProductService dkContractProductService;
	
	@ModelAttribute
	public DkContractProduct get(@RequestParam(required=false) String id) {
		DkContractProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkContractProductService.get(id);
		}
		if (entity == null){
			entity = new DkContractProduct();
		}
		return entity;
	}
	
	/**
	 * 合同商品列表页面
	 */
	@RequiresPermissions("daikin:dkContractProduct:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkContractProduct dkContractProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkContractProduct> page = dkContractProductService.findPage(new Page<DkContractProduct>(request, response), dkContractProduct); 
		model.addAttribute("page", page);
		return "modules/daikin/dkContractProductList";
	}

	/**
	 * 查看，增加，编辑合同商品表单页面
	 */
	@RequiresPermissions(value={"daikin:dkContractProduct:view","daikin:dkContractProduct:add","daikin:dkContractProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkContractProduct dkContractProduct, Model model) {
		model.addAttribute("dkContractProduct", dkContractProduct);
		return "modules/daikin/dkContractProductForm";
	}

	/**
	 * 保存合同商品
	 */
	@RequiresPermissions(value={"daikin:dkContractProduct:add","daikin:dkContractProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkContractProduct dkContractProduct, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkContractProduct)){
			return form(dkContractProduct, model);
		}
		if(!dkContractProduct.getIsNewRecord()){//编辑表单保存
			DkContractProduct t = dkContractProductService.get(dkContractProduct.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkContractProduct, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkContractProductService.save(t);//保存
		}else{//新增表单保存
			dkContractProductService.save(dkContractProduct);//保存
		}
		addMessage(redirectAttributes, "保存合同商品成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractProduct/?repage";
	}
	
	/**
	 * 删除合同商品
	 */
	@RequiresPermissions("daikin:dkContractProduct:del")
	@RequestMapping(value = "delete")
	public String delete(DkContractProduct dkContractProduct, RedirectAttributes redirectAttributes) {
		dkContractProductService.delete(dkContractProduct);
		addMessage(redirectAttributes, "删除合同商品成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractProduct/?repage";
	}
	
	/**
	 * 批量删除合同商品
	 */
	@RequiresPermissions("daikin:dkContractProduct:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkContractProductService.delete(dkContractProductService.get(id));
		}
		addMessage(redirectAttributes, "删除合同商品成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractProduct/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkContractProduct:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkContractProduct dkContractProduct, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "合同商品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkContractProduct> page = dkContractProductService.findPage(new Page<DkContractProduct>(request, response, -1), dkContractProduct);
    		new ExportExcel("合同商品", DkContractProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出合同商品记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractProduct/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkContractProduct:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkContractProduct> list = ei.getDataList(DkContractProduct.class);
			for (DkContractProduct dkContractProduct : list){
				try{
					dkContractProductService.save(dkContractProduct);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条合同商品记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条合同商品记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入合同商品失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractProduct/?repage";
    }
	
	/**
	 * 下载导入合同商品数据模板
	 */
	@RequiresPermissions("daikin:dkContractProduct:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "合同商品数据导入模板.xlsx";
    		List<DkContractProduct> list = Lists.newArrayList(); 
    		new ExportExcel("合同商品数据", DkContractProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkContractProduct/?repage";
    }
	
	
	/**
	 * 选择商品ID
	 */
	@RequestMapping(value = "selectdkProduct")
	public String selectdkProduct(DkProduct dkProduct, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkProduct> page = dkContractProductService.findPageBydkProduct(new Page<DkProduct>(request, response),  dkProduct);
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