/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.daikin.entity.DkContract;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同回款记录Entity
 * @author LD
 * @version 2017-03-31
 */
public class DkContractPay extends DataEntity<DkContractPay> {
	
	private static final long serialVersionUID = 1L;
	private DkContract dkContract;		// 合同ID
	private Date payDate;		// 支付时间
	private Double payFee;		// 支付金额
	private String reviewStatus;		// 审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）
	private String reviewBy;		// 审核者
	private String remark;		// 备注
	private Date beginPayDate;		// 开始 支付时间
	private Date endPayDate;		// 结束 支付时间
	
	public DkContractPay() {
		super();
	}

	public DkContractPay(String id){
		super(id);
	}

	@NotNull(message="合同ID不能为空")
	@ExcelField(title="合同ID", align=2, sort=1)
	public DkContract getDkContract() {
		return dkContract;
	}

	public void setDkContract(DkContract dkContract) {
		this.dkContract = dkContract;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="支付时间不能为空")
	@ExcelField(title="支付时间", align=2, sort=2)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@NotNull(message="支付金额不能为空")
	@Min(value=1,message="支付金额的最小值不能小于1")
	@Max(value=1000000,message="支付金额的最大值不能超过1000000")
	@ExcelField(title="支付金额", align=2, sort=3)
	public Double getPayFee() {
		return payFee;
	}

	public void setPayFee(Double payFee) {
		this.payFee = payFee;
	}
	
	@ExcelField(title="审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）", dictType="review_status", align=2, sort=4)
	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	
	@ExcelField(title="审核者", align=2, sort=5)
	public String getReviewBy() {
		return reviewBy;
	}

	public void setReviewBy(String reviewBy) {
		this.reviewBy = reviewBy;
	}
	
	@ExcelField(title="备注", align=2, sort=6)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getBeginPayDate() {
		return beginPayDate;
	}

	public void setBeginPayDate(Date beginPayDate) {
		this.beginPayDate = beginPayDate;
	}
	
	public Date getEndPayDate() {
		return endPayDate;
	}

	public void setEndPayDate(Date endPayDate) {
		this.endPayDate = endPayDate;
	}
		
}