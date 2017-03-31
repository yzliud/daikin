/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.daikin.entity.DkProduct;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同Entity
 * @author LD
 * @version 2017-03-31
 */
public class DkContractProduct extends DataEntity<DkContractProduct> {
	
	private static final long serialVersionUID = 1L;
	private DkContract contractId;		// 合同ID 父类
	private DkProduct dkProduct;		// 商品ID
	private String name;		// 名称
	private String model;		// 规格
	private Double price;		// 单价
	private Integer amount;		// 数量
	private Double totalPrice;		// 总价
	private String classifyId;		// 分类
	private Double power;		// 功率
	private String place;		// 产地
	private String brandId;		// 品牌
	private String unit;		// 单位
	private String productType;		// 商品类型(0-空调 1-地暖)
	private String position;		// 位置
	private String floor;		// 楼层
	private Double demandArea;		// 需求面积
	private String descript;		// 描述
	
	public DkContractProduct() {
		super();
	}

	public DkContractProduct(String id){
		super(id);
	}

	public DkContractProduct(DkContract contractId){
		this.contractId = contractId;
	}

	public DkContract getContractId() {
		return contractId;
	}

	public void setContractId(DkContract contractId) {
		this.contractId = contractId;
	}
	
	@NotNull(message="商品ID不能为空")
	@ExcelField(title="商品ID", align=2, sort=2)
	public DkProduct getDkProduct() {
		return dkProduct;
	}

	public void setDkProduct(DkProduct dkProduct) {
		this.dkProduct = dkProduct;
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
	@Max(value=1000000,message="单价的最大值不能超过1000000")
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
	
	@NotNull(message="总价不能为空")
	@Min(value=1,message="总价的最小值不能小于1")
	@Max(value=10000000,message="总价的最大值不能超过10000000")
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
	
	@ExcelField(title="品牌", dictType="brand_id", align=2, sort=11)
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
	
	@ExcelField(title="商品类型(0-空调 1-地暖)", dictType="product_type", align=2, sort=13)
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
	
	@ExcelField(title="楼层", dictType="floor", align=2, sort=15)
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
	
}