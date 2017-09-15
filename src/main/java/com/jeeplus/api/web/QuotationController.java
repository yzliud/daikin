package com.jeeplus.api.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.jeeplus.api.entity.Quotation;
import com.jeeplus.api.entity.QuotationProduct;
import com.jeeplus.api.service.QuotationService;
import com.jeeplus.modules.daikin.entity.DkMember;
import com.jeeplus.modules.daikin.entity.DkProduct;
import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.entity.DkWorker;
import com.jeeplus.modules.daikin.service.DkMemberService;
import com.jeeplus.modules.daikin.service.DkProductService;
import com.jeeplus.modules.daikin.service.DkQuotationService;
import com.jeeplus.modules.daikin.service.DkWorkerService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.daikin.service.SysUserService;

@Controller
@RequestMapping(value = "${adminPath}/api/quotation")
public class QuotationController {
	
	@Autowired
	private QuotationService quotationService;

	@Autowired
	private DkWorkerService workerService;
	
	@Autowired
	private DkQuotationService dkQuotationService;
	
	@Autowired
	private DkProductService dkProductService;
	
	@Autowired
	private DkMemberService dkMemberService;
	
	@Autowired
	private SysUserService SysUserService;
	
	/**
	 * 首页跳转
	 */
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		String openId = (String) request.getSession().getAttribute("openId");
		System.out.println("-----q------"+openId);
		if (openId == null) {
			return "redirect:../../../webpage/api/getOpen.html?cmethod=quotation";
		} else {
			DkWorker worker = workerService.findUniqueByProperty("open_id", openId);
			String workerMobile = worker.getMobile();
			if (workerMobile != null && !workerMobile.equals("")) {
				request.getSession().setAttribute("sysId", worker.getSysUserId());
				return "redirect:../../../webpage/api/quotation_1.html";
			} else {
				return "redirect:../../../webpage/api/worker_index.html?jump=quotation";
			}
		}

	}

	/**
	 * 报价单名称是否存在
	 * @throws IOException 
	 */
	@RequestMapping(value = "ishave")
	public void ishave(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();

		String name = request.getParameter("name");

		DkQuotation quotation = new DkQuotation();
		quotation.setName(name);
		DkQuotation quotation1 = dkQuotationService.getSingle(quotation);
		if(quotation1==null){
			map.put("msg", "no");//不存在
		}else{
			map.put("msg", "yes");//存在
		}
		writer.println(gson.toJson(map));
		writer.flush();
		writer.close();
	}
	
	/**
	 * 获取类型列表接口
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "getAllType")
	public void getAllType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		List<HashMap<String, Object>> list = quotationService.getAllType();
		Gson gson = new Gson();
		writer.println(gson.toJson(list));
		writer.flush();
		writer.close();
		
	}
	
	/**
	 * 获取商品列表接口
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "getAllProduct")
	public void getAllProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		String product_type = request.getParameter("product_type");
		List<HashMap<String, Object>> list = quotationService.getAllProduct(product_type);
		Gson gson = new Gson();
		writer.println(gson.toJson(list));
		writer.flush();
		writer.close();
		
	}
	
	/**
	 * 保存报价单
	 * @throws IOException 
	 */
	@RequestMapping(value = "create")
	public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();

		String sysId = (String) request.getSession().getAttribute("sysId");
		//sysId="001";
		if(sysId==null){
			map.put("msg", "fail");//失败
		}else{			
			String name = request.getParameter("name");
			String member_name = request.getParameter("member_name");
			String mobile = request.getParameter("mobile");
			String address = request.getParameter("address");
			String remark = request.getParameter("remark");
			String product_type = request.getParameter("product_type");
			String products_str = request.getParameter("product");//id:数量,id:数量,
			
			Quotation quotation = new Quotation();
			String quotation_id = UUID.randomUUID().toString().replace("-", ""); 
			quotation.setId(quotation_id);
			quotation.setName(name);
			quotation.setMember_name(member_name);
			quotation.setMobile(mobile);
			quotation.setAddress(address);
			
			DkMember member = dkMemberService.findUniqueByProperty("address", address);
			if(member==null){
				String openId = (String) request.getSession().getAttribute("openId");
				DkWorker worker = workerService.findUniqueByProperty("open_id", openId);
				User createBy = new User();
				createBy.setId(worker.getSysUserId());
				member = new DkMember();
				member.setIsNewRecord(true);
				member.setId(UUID.randomUUID().toString().replace("-", ""));
				member.setName(member_name);
				member.setMobile(mobile);
				member.setAddress(address);
				member.setRemark(remark);
				member.setRecordBy(createBy);
				member.setCreateBy(createBy);
				member.setUpdateBy(createBy);
				dkMemberService.save(member);
			}
			quotation.setMember_id(member.getId());
			
			quotation.setProduct_type(product_type);
			quotation.setSale_by(sysId);
			quotation.setReview_status("1");
			quotation.setIs_review("0");
			quotation.setDel_flag("0");
			quotation.setCreate_by(sysId);
			quotation.setCreate_date(new Date());
			quotation.setUpdate_by(sysId);
			quotation.setUpdate_date(new Date());
			
			List<QuotationProduct> quotationProductList = Lists.newArrayList();
			double total_fee = 0;
			double total_cost_fee = 0;
			String[] products = products_str.split(",");
			for (String product_str : products) {
				String product_id = product_str.split(":")[0];
				String product_num = product_str.split(":")[1];
				
				
				
				DkProduct product = dkProductService.get(product_id);
				
				int amount = Integer.valueOf(product_num);
				double price = product.getPrice();
				double costPrice = product.getCostPrice();
				
				
					
				QuotationProduct quotationProduct = new QuotationProduct();
				quotationProduct.setId(UUID.randomUUID().toString().replace("-", ""));
				quotationProduct.setQuotation_id(quotation_id);
				quotationProduct.setProduct_id(product_id); 
				quotationProduct.setPrice(price); 
				quotationProduct.setCost_price(costPrice);
				quotationProduct.setAmount(amount);				
				quotationProduct.setTotal_price(price * amount);
				quotationProduct.setTotal_cost_price(costPrice * amount);
				total_cost_fee = total_cost_fee + costPrice * amount;
				total_fee = total_fee + price * amount;
				
				quotationProduct.setPower(product.getPower()); 
				quotationProduct.setBrand_id(product.getBrandId());
				quotationProduct.setClassify_id(product.getClassifyId());
				quotationProduct.setModel(product.getModel());
				quotationProduct.setCapacity_model(product.getCapacityModel());
				quotationProduct.setName(product.getName());
				quotationProduct.setPlace(product.getPlace());
				quotationProduct.setUnit(product.getUnit());
				quotationProduct.setProduct_type(product.getProductType());
				quotationProduct.setCost_price(product.getCostPrice());
				//quotationProduct.setPosition();                                        // 位置
				//quotationProduct.setFloor();                                           // 楼层
				//quotationProduct.setDemandArea();                                      // 需求面积
				quotationProduct.setDescript(product.getDescript());                     // 描述
				quotationProduct.setCreate_by(sysId);
				quotationProduct.setCreate_date(new Date());
				quotationProduct.setUpdate_by(sysId);
				quotationProduct.setUpdate_date(new Date());
				quotationProduct.setDel_flag("0");
				quotationProductList.add(quotationProduct);
			}
			quotation.setQuotationProductList(quotationProductList);
			quotation.setTotal_fee(total_fee);
			quotation.setCost_fee(total_cost_fee);
			quotation.setSign_fee(total_fee);
			
			quotationService.save(quotation);
			
			map.put("msg", "success");//成功
			map.put("id", quotation_id);
		}
		
		writer.println(gson.toJson(map));
		writer.flush();
		writer.close();
	}
	
	@RequestMapping(value = "form")
	public void form(String id, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		DkQuotation dkQuotation = dkQuotationService.get(id);
		Gson gson = new Gson();
		writer.println(gson.toJson(dkQuotation));
		writer.flush();
		writer.close();
	}


}
