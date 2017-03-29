/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.modules.daikin.entity.DkProduct;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同商品Entity
 * @author LD
 * @version 2017-03-24
 */
public class DkContractProduct extends DataEntity<DkContractProduct> {
	
	private static final long serialVersionUID = 1L;
	private DkContract dkContract;		// 合同
	private DkProduct dkProduct;		// 商品
	private String productName;		// 商品名称
	private String model;		// 商品型号
	private Integer amount;		// 商品数量
	private Double unitFee;		// 商品单价
	private Double totalFee;		// 商品总价
	private String place;		// 安装位置
	private Double area;		// 需求面积
	private String remark;		// 备注
	
	public DkContractProduct() {
		super();
	}

	public DkContractProduct(String id){
		super(id);
	}

	@ExcelField(title="合同", align=2, sort=1)
	public DkContract getDkContract() {
		return dkContract;
	}

	public void setDkContract(DkContract dkContract) {
		this.dkContract = dkContract;
	}
	
	@ExcelField(title="商品", align=2, sort=2)
	public DkProduct getDkProduct() {
		return dkProduct;
	}

	public void setDkProduct(DkProduct dkProduct) {
		this.dkProduct = dkProduct;
	}
	
	@ExcelField(title="商品名称", align=2, sort=3)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@ExcelField(title="商品型号", align=2, sort=4)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	@ExcelField(title="商品数量", align=2, sort=5)
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@ExcelField(title="商品单价", align=2, sort=6)
	public Double getUnitFee() {
		return unitFee;
	}

	public void setUnitFee(Double unitFee) {
		this.unitFee = unitFee;
	}
	
	@ExcelField(title="商品总价", align=2, sort=7)
	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	
	@ExcelField(title="安装位置", align=2, sort=8)
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	@ExcelField(title="需求面积", align=2, sort=9)
	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}
	
	@ExcelField(title="备注", align=2, sort=10)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}