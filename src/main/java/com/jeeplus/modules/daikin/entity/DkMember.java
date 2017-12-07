/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.User;

/**
 * 会员Entity
 * @author LD
 * @version 2017-04-01
 */
public class DkMember extends DataEntity<DkMember> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String mobile;		// 手机号
	private String address;		// 联系地址
	private String sourceInfo;  // 信息来源
	private String remark;		// 备注
	
	private String beginTime;
	private String endTime;
	private String productType;
	
	private User recordBy;		// 录入者
	
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public DkMember() {
		super();
	}

	public DkMember(String id){
		super(id);
	}

	@Length(min=1, max=20, message="姓名长度必须介于 1 和 20 之间")
	@ExcelField(title="姓名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=20, message="手机号长度必须介于 1 和 20 之间")
	@ExcelField(title="手机号", align=2, sort=2)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=1, max=100, message="联系地址长度必须介于 1 和 100 之间")
	@ExcelField(title="联系地址", align=2, sort=3)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="备注", align=2, sort=5)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@ExcelField(title="录入者", fieldType=User.class, value="recordBy.name", align=2, sort=5)
	public User getRecordBy() {
		return recordBy;
	}

	public void setRecordBy(User recordBy) {
		this.recordBy = recordBy;
	}

	@ExcelField(title="信息来源", dictType="source_info", align=2, sort=4)
	public String getSourceInfo() {
		return sourceInfo;
	}

	public void setSourceInfo(String sourceInfo) {
		this.sourceInfo = sourceInfo;
	}
	
}