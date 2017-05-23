/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 审核记录Entity
 * @author LD
 * @version 2017-04-09
 */
public class DkAuditRecord extends DataEntity<DkAuditRecord> {
	
	private static final long serialVersionUID = 1L;
	private String recordId;		// 记录ID
	private String recordType;		// 记录类型（0-报价单 1-合同  2-合同到款）
	private String reviewStatus;		// 审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）
	private User tuser;		// 审核者
	private String remark;		// 审核意见
	
	public DkAuditRecord() {
		super();
	}

	public DkAuditRecord(String id){
		super(id);
	}

	@ExcelField(title="记录ID", align=2, sort=1)
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	@ExcelField(title="记录类型（0-报价单 1-合同  2-合同到款）", dictType="record_type", align=2, sort=2)
	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
	@ExcelField(title="审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）", dictType="review_status", align=2, sort=3)
	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	
	@ExcelField(title="审核者", fieldType=User.class, value="tuser.name", align=2, sort=4)
	public User getTuser() {
		return tuser;
	}

	public void setTuser(User tuser) {
		this.tuser = tuser;
	}
	
	@ExcelField(title="审核意见", align=2, sort=5)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}