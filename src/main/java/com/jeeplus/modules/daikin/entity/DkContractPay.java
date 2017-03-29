/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 付款计划Entity
 * @author LD
 * @version 2017-03-24
 */
public class DkContractPay extends DataEntity<DkContractPay> {
	
	private static final long serialVersionUID = 1L;
	private String dkContractId;		// 合同ID
	private Date planDate;		// 计划付款时间
	private Double planFee;		// 计划付款金额
	private String planDesc;		// 计划付款描述
	private Date payDate;		// 支付时间
	private Double payFee;		// 支付金额
	private String remark;		// 备注
	
	public DkContractPay() {
		super();
	}

	public DkContractPay(String id){
		super(id);
	}

	@ExcelField(title="合同ID", align=2, sort=1)
	public String getDkContractId() {
		return dkContractId;
	}

	public void setDkContractId(String dkContractId) {
		this.dkContractId = dkContractId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="计划付款时间", align=2, sort=2)
	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	
	@ExcelField(title="计划付款金额", align=2, sort=3)
	public Double getPlanFee() {
		return planFee;
	}

	public void setPlanFee(Double planFee) {
		this.planFee = planFee;
	}
	
	@ExcelField(title="计划付款描述", align=2, sort=4)
	public String getPlanDesc() {
		return planDesc;
	}

	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="支付时间", align=2, sort=5)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@ExcelField(title="支付金额", align=2, sort=6)
	public Double getPayFee() {
		return payFee;
	}

	public void setPayFee(Double payFee) {
		this.payFee = payFee;
	}
	
	@ExcelField(title="备注", align=2, sort=7)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}