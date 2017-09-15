/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 上门登记Entity
 * @author 菜鸟
 * @version 2017-09-15
 */
public class DkVisit extends DataEntity<DkVisit> {
	
	private static final long serialVersionUID = 1L;
	private String mobile;		// 手机号
	private String address;		// 联系地址
	private String name;		// 姓名
	private User recordBy;		// 录入者
	private String content;		// 咨询内容
	private Date visitDate;		// 上门时间
	private String remark;		// 备注
	private Date beginVisitDate;		// 开始 上门时间
	private Date endVisitDate;		// 结束 上门时间
	
	public DkVisit() {
		super();
	}

	public DkVisit(String id){
		super(id);
	}

	@ExcelField(title="手机号", align=2, sort=1)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="联系地址", align=2, sort=2)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="姓名", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="录入者", fieldType=User.class, value="recordBy.name", align=2, sort=4)
	public User getRecordBy() {
		return recordBy;
	}

	public void setRecordBy(User recordBy) {
		this.recordBy = recordBy;
	}
	
	@ExcelField(title="咨询内容", align=2, sort=5)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="上门时间不能为空")
	@ExcelField(title="上门时间", align=2, sort=6)
	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	
	@ExcelField(title="备注", align=2, sort=7)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getBeginVisitDate() {
		return beginVisitDate;
	}

	public void setBeginVisitDate(Date beginVisitDate) {
		this.beginVisitDate = beginVisitDate;
	}
	
	public Date getEndVisitDate() {
		return endVisitDate;
	}

	public void setEndVisitDate(Date endVisitDate) {
		this.endVisitDate = endVisitDate;
	}
		
}