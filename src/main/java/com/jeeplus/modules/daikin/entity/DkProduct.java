/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品Entity
 * @author LD
 * @version 2017-03-24
 */
public class DkProduct extends DataEntity<DkProduct> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String model;		// 型号
	private String classifyId;		// 分类
	private String picPath;		// 图片
	private String descript;		// 描述
	private Double price;		// 价格
	private Double inventory;		// 库存
	
	public DkProduct() {
		super();
	}

	public DkProduct(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="型号", align=2, sort=2)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	@ExcelField(title="分类", align=2, sort=3)
	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}
	
	@ExcelField(title="图片", align=2, sort=4)
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	@ExcelField(title="描述", align=2, sort=5)
	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
	@Min(value=0,message="价格的最小值不能小于0")
	@Max(value=500000,message="价格的最大值不能超过500000")
	@ExcelField(title="价格", align=2, sort=6)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Min(value=0,message="库存的最小值不能小于0")
	@Max(value=10000,message="库存的最大值不能超过10000")
	@ExcelField(title="库存", align=2, sort=7)
	public Double getInventory() {
		return inventory;
	}

	public void setInventory(Double inventory) {
		this.inventory = inventory;
	}
	
}