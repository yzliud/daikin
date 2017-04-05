/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品Entity
 * @author LD
 * @version 2017-04-05
 */
public class DkProduct extends DataEntity<DkProduct> {
	
	private static final long serialVersionUID = 1L;
	private String productType;		// 商品类型
	private String classifyId;		// 分类
	private String name;		// 名称
	private String model;		// 规格
	private Double price;		// 单价
	private Double stock;		// 库存
	private Double power;		// 功率
	private String place;		// 产地
	private String brandId;		// 品牌
	private String unit;		// 单位
	private String descript;		// 描述
	
	public DkProduct() {
		super();
	}

	public DkProduct(String id){
		super(id);
	}

	@ExcelField(title="商品类型", dictType="product_type", align=2, sort=1)
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	@ExcelField(title="分类", dictType="classify_id", align=2, sort=2)
	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}
	
	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
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
	@Min(value=0,message="单价的最小值不能小于0")
	@Max(value=100000,message="单价的最大值不能超过100000")
	@ExcelField(title="单价", align=2, sort=5)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@NotNull(message="库存不能为空")
	@Min(value=0,message="库存的最小值不能小于0")
	@Max(value=100000,message="库存的最大值不能超过100000")
	@ExcelField(title="库存", align=2, sort=6)
	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}
	
	@ExcelField(title="功率", align=2, sort=7)
	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}
	
	@ExcelField(title="产地", align=2, sort=8)
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	@ExcelField(title="品牌", dictType="brand_id", align=2, sort=9)
	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
	@ExcelField(title="单位", align=2, sort=10)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="描述", align=2, sort=11)
	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
}