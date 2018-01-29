package com.jeeplus.modules.daikin.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.daikin.entity.DkProduct;
import com.jeeplus.modules.daikin.service.DkCommonService;

@Controller
@RequestMapping(value = "${adminPath}/daikin/dkCommon")
public class DkCommonController extends BaseController{

	@Autowired
	private DkCommonService dkCommonService;
	
	/**
	 * 选择商品ID
	 */
	@RequestMapping(value = "selectdkProduct")
	public String selectdkProduct(DkProduct dkProduct, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DkProduct> page = dkCommonService.findPageBydkProduct(new Page<DkProduct>(request, response),  dkProduct);
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
		model.addAttribute("productType", dkProduct.getProductType());
		model.addAttribute("fieldKeys", fieldKeys);
		model.addAttribute("url", url);
		model.addAttribute("searchLabel", searchLabel);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("obj", dkProduct);
		model.addAttribute("page", page);
		return "modules/daikin/gridProduct";
	}
}
