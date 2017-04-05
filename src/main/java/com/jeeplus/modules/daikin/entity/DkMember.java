/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 会员Entity
 * @author LD
 * @version 2017-04-01
 */
public class DkMember extends DataEntity<DkMember> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String mobile;		// 手机号
	private String address;		// 联系地址
	private String remark;		// 备注
	
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
	
	@ExcelField(title="备注", align=2, sort=4)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}