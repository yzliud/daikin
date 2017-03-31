/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.daikin.entity.DkMember;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 报价单Entity
 * @author LD
 * @version 2017-03-31
 */
public class DkQuotation extends DataEntity<DkQuotation> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String memberName;		// 顾客名称
	private String mobile;		// 联系人手机号
	private String address;		// 联系地址
	private DkMember dkMember;		// 会员ID
	private Double totalFee;		// 合同总金额
	private String productType;		// 商品类型
	private User tuser;		// 销售人员
	private Double connectionRatio;		// 连接率
	private String reviewStatus;		// 审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）
	private User ruser;		// 审核者
	private String remark;		// 备注
	
	public DkQuotation() {
		super();
	}

	public DkQuotation(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="顾客名称", align=2, sort=2)
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@ExcelField(title="联系人手机号", align=2, sort=3)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="联系地址", align=2, sort=4)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@NotNull(message="会员ID不能为空")
	@ExcelField(title="会员ID", align=2, sort=5)
	public DkMember getDkMember() {
		return dkMember;
	}

	public void setDkMember(DkMember dkMember) {
		this.dkMember = dkMember;
	}
	
	@NotNull(message="合同总金额不能为空")
	@ExcelField(title="合同总金额", align=2, sort=6)
	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	
	@ExcelField(title="商品类型", dictType="product_type", align=2, sort=7)
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	@ExcelField(title="销售人员", fieldType=User.class, value="tuser.name", align=2, sort=8)
	public User getTuser() {
		return tuser;
	}

	public void setTuser(User tuser) {
		this.tuser = tuser;
	}
	
	@ExcelField(title="连接率", align=2, sort=9)
	public Double getConnectionRatio() {
		return connectionRatio;
	}

	public void setConnectionRatio(Double connectionRatio) {
		this.connectionRatio = connectionRatio;
	}
	
	@ExcelField(title="审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）", dictType="review_status", align=2, sort=10)
	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	
	@ExcelField(title="审核者", fieldType=User.class, value="ruser.name", align=2, sort=11)
	public User getRuser() {
		return ruser;
	}

	public void setRuser(User ruser) {
		this.ruser = ruser;
	}
	
	@ExcelField(title="备注", align=2, sort=12)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}