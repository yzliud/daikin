/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 报价单Entity
 * @author LD
 * @version 2017-04-05
 */
public class DkQuotationProduct extends DataEntity<DkQuotationProduct> {
	
	private static final long serialVersionUID = 1L;
	private String quotationId;		// 报价单ID 父类
	private String productId;		// 商品ID
	private String name;		// 名称
	private String model;		// 规格
	private Double price;		// 单价
	private Double costPrice;		// 成本价
	private Integer amount;		// 数量
	private Double totalPrice;		// 总价
	private String classifyId;		// 分类
	private Double power;		// 功率
	private String place;		// 产地
	private String brandId;		// 品牌
	private String unit;		// 单位
	private String productType;		// 商品类型
	private String position;		// 位置
	private String floor;		// 楼层
	private Double demandArea;		// 需求面积
	private String descript;		// 描述
	
	public DkQuotationProduct() {
		super();
	}

	public DkQuotationProduct(String id){
		super(id);
	}

	public DkQuotationProduct(DkQuotation quotation){
		this.quotationId = quotation.getId();
	}

	public String getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}
	
	@ExcelField(title="商品ID", align=2, sort=2)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@ExcelField(title="名称", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="规格", align=2, sort=4)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	@NotNull(message="单价不能为空")
	@Min(value=1,message="单价的最小值不能小于1")
	@Max(value=100000,message="单价的最大值不能超过100000")
	@ExcelField(title="单价", align=2, sort=5)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@NotNull(message="数量不能为空")
	@Min(value=1,message="数量的最小值不能小于1")
	@Max(value=1000,message="数量的最大值不能超过1000")
	@ExcelField(title="数量", align=2, sort=6)
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Min(value=1,message="总价的最小值不能小于1")
	@Max(value=1000000,message="总价的最大值不能超过1000000")
	@ExcelField(title="总价", align=2, sort=7)
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@ExcelField(title="分类", dictType="classify_id", align=2, sort=8)
	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}
	
	@ExcelField(title="功率", align=2, sort=9)
	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}
	
	@ExcelField(title="产地", align=2, sort=10)
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	@ExcelField(title="品牌", align=2, sort=11)
	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
	@ExcelField(title="单位", align=2, sort=12)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="商品类型", align=2, sort=13)
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	@ExcelField(title="位置", align=2, sort=14)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@ExcelField(title="楼层", align=2, sort=15)
	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}
	
	@ExcelField(title="需求面积", align=2, sort=16)
	public Double getDemandArea() {
		return demandArea;
	}

	public void setDemandArea(Double demandArea) {
		this.demandArea = demandArea;
	}
	
	@ExcelField(title="描述", align=2, sort=17)
	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	
}