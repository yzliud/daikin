/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.daikin.entity.DkContract;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 订货单Entity
 * @author LD
 * @version 2018-01-29
 */
public class DkContractOrder extends DataEntity<DkContractOrder> {
	
	private static final long serialVersionUID = 1L;
	private DkContract dkContract;		// 合同
	private Date orderDate;		// 订货时间
	private String remark;		// 备注
	private Date beginOrderDate;		// 开始 订货时间
	private Date endOrderDate;		// 结束 订货时间
	
	public DkContractOrder() {
		super();
	}

	public DkContractOrder(String id){
		super(id);
	}

	@NotNull(message="合同不能为空")
	@ExcelField(title="合同", align=2, sort=1,fieldType=DkContract.class,value="dkContract.name")
	public DkContract getDkContract() {
		return dkContract;
	}

	public void setDkContract(DkContract dkContract) {
		this.dkContract = dkContract;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="订货时间不能为空")
	@ExcelField(title="订货时间", align=2, sort=2)
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@ExcelField(title="备注", align=2, sort=3)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getBeginOrderDate() {
		return beginOrderDate;
	}

	public void setBeginOrderDate(Date beginOrderDate) {
		this.beginOrderDate = beginOrderDate;
	}
	
	public Date getEndOrderDate() {
		return endOrderDate;
	}

	public void setEndOrderDate(Date endOrderDate) {
		this.endOrderDate = endOrderDate;
	}
		
}