/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.daikin.entity.DkContract;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 安装付款Entity
 * @author LD
 * @version 2017-05-06
 */
public class DkContractInstallCost extends DataEntity<DkContractInstallCost> {
	
	private static final long serialVersionUID = 1L;
	private DkContract dkContract;		// 合同ID
	private Date payDate;		// 支付时间
	private Double payFee;		// 费用
	private String installTeam;		// 安装队
	private String reviewStatus;		// 审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）
	private User reviewBy;		// 审核者
	private String isReview;		// 是否有审核记录(0-没有；1-有)
	private String remark;		// 备注
	private Date beginPayDate;		// 开始 支付时间
	private Date endPayDate;		// 结束 支付时间
	
	public DkContractInstallCost() {
		super();
	}

	public DkContractInstallCost(String id){
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
	
	@NotNull(message="费用不能为空")
	@ExcelField(title="费用", align=2, sort=3)
	public Double getPayFee() {
		return payFee;
	}

	public void setPayFee(Double payFee) {
		this.payFee = payFee;
	}
	
	@Length(min=1, max=20, message="安装队长度必须介于 1 和 20 之间")
	@ExcelField(title="安装队", align=2, sort=4)
	public String getInstallTeam() {
		return installTeam;
	}

	public void setInstallTeam(String installTeam) {
		this.installTeam = installTeam;
	}
	
	@ExcelField(title="审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）", align=2, sort=5)
	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	
	@ExcelField(title="审核者", fieldType=User.class, value="reviewBy.name", align=2, sort=6)
	public User getReviewBy() {
		return reviewBy;
	}

	public void setReviewBy(User reviewBy) {
		this.reviewBy = reviewBy;
	}
	
	@ExcelField(title="是否有审核记录(0-没有；1-有)", align=2, sort=7)
	public String getIsReview() {
		return isReview;
	}

	public void setIsReview(String isReview) {
		this.isReview = isReview;
	}
	
	@ExcelField(title="备注", align=2, sort=8)
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