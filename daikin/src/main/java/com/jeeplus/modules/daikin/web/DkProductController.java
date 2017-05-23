/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.jeeplus.modules.daikin.entity.DkProduct;
import com.jeeplus.modules.daikin.service.DkProductService;

/**
 * 商品Controller
 * @author LD
 * @version 2017-04-05
 */
@Controller
@RequestMapping(value = "${adminPath}/daikin/dkProduct")
public class DkProductController extends BaseController {

	@Autowired
	private DkProductService dkProductService;
	
	@ModelAttribute
	public DkProduct get(@RequestParam(required=false) String id) {
		DkProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dkProductService.get(id);
		}
		if (entity == null){
			entity = new DkProduct();
		}
		return entity;
	}
	
	/**
	 * 商品列表页面
	 */
	@RequiresPermissions("daikin:dkProduct:list")
	@RequestMapping(value = {"list", ""})
	public String list(DkProduct dkProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkProduct> page = dkProductService.findPage(new Page<DkProduct>(request, response), dkProduct); 
		model.addAttribute("page", page);
		return "modules/daikin/dkProductList";
	}

	/**
	 * 查看，增加，编辑商品表单页面
	 */
	@RequiresPermissions(value={"daikin:dkProduct:view","daikin:dkProduct:add","daikin:dkProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DkProduct dkProduct, Model model) {
		model.addAttribute("dkProduct", dkProduct);
		return "modules/daikin/dkProductForm";
	}

	/**
	 * 保存商品
	 */
	@RequiresPermissions(value={"daikin:dkProduct:add","daikin:dkProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DkProduct dkProduct, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dkProduct)){
			return form(dkProduct, model);
		}
		if(!dkProduct.getIsNewRecord()){//编辑表单保存
			DkProduct t = dkProductService.get(dkProduct.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dkProduct, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dkProductService.save(t);//保存
		}else{//新增表单保存
			dkProductService.save(dkProduct);//保存
		}
		addMessage(redirectAttributes, "保存商品成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkProduct/?repage";
	}
	
	/**
	 * 删除商品
	 */
	@RequiresPermissions("daikin:dkProduct:del")
	@RequestMapping(value = "delete")
	public String delete(DkProduct dkProduct, RedirectAttributes redirectAttributes) {
		dkProductService.delete(dkProduct);
		addMessage(redirectAttributes, "删除商品成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkProduct/?repage";
	}
	
	/**
	 * 批量删除商品
	 */
	@RequiresPermissions("daikin:dkProduct:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dkProductService.delete(dkProductService.get(id));
		}
		addMessage(redirectAttributes, "删除商品成功");
		return "redirect:"+Global.getAdminPath()+"/daikin/dkProduct/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daikin:dkProduct:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DkProduct dkProduct, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DkProduct> page = dkProductService.findPage(new Page<DkProduct>(request, response, -1), dkProduct);
    		new ExportExcel("商品", DkProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkProduct/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daikin:dkProduct:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			String rtnMsg = "";
			String rowMsg = "";
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DkProduct> list = ei.getDataList(DkProduct.class);
			int size = 2 ;
			for (DkProduct dkProduct : list){
				size++;
				rowMsg = "";
				try{
					if(dkProduct == null || dkProduct.getProductType() == null || ("").equals(dkProduct.getProductType())){
						rowMsg = rowMsg + " 类型";
					}
					if(dkProduct == null || dkProduct.getName() == null || ("").equals(dkProduct.getName())){
						rowMsg = rowMsg + " 名称";
					}
					if(dkProduct == null || dkProduct.getPrice() == null || ("").equals(dkProduct.getPrice()) || !StringUtils.isNumeric(dkProduct.getPrice()+"")){
						rowMsg = rowMsg + " 价格";
					}
					if(rowMsg.equals("") ){
						DkProduct dp = dkProductService.getByName(dkProduct);
						if(dp == null){
							dkProductService.save(dkProduct);
							successNum++;
						}else{
							failureNum++;
							rowMsg = rowMsg + " 名称重复";
						}
					}else{
						failureNum++;
					}
					if(!rowMsg.equals("")){
						rtnMsg = rtnMsg + "第"+size+"行"+rowMsg+" 数据有误";
					}
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品记录。"+rtnMsg);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkProduct/?repage";
    }
	
	/**
	 * 下载导入商品数据模板
	 */
	@RequiresPermissions("daikin:dkProduct:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品数据导入模板.xlsx";
    		List<DkProduct> list = Lists.newArrayList(); 
    		new ExportExcel("商品数据", DkProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daikin/dkProduct/?repage";
    }
	
    @RequestMapping(value = "checkDkProductName")
    public String checkDkProductName(DkProduct dkProduct, HttpServletResponse response) throws UnsupportedEncodingException, IOException  {
    	DkProduct dp = dkProductService.getByName(dkProduct);
    	String result = "";
		
		if(dp == null){
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