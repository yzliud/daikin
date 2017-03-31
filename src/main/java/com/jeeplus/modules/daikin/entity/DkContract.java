/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.entity.DkMember;
import com.jeeplus.modules.sys.entity.User;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同Entity
 * @author LD
 * @version 2017-03-31
 */
public class DkContract extends DataEntity<DkContract> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private DkQuotation dkQuotation;		// 报价单ID
	private String contractNumber;		// 合同号
	private String memberName;		// 顾客名称
	private String mobile;		// 联系方式
	private String address;		// 联系地址
	private DkMember dkMember;		// 会员ID
	private Double totalFee;		// 合同总金额
	private Double connectionRatio;		// 连接率
	private User iuser;		// 安装人员
	private User suser;		// 销售人员
	private String productType;		// 合同类型
	private String reviewStatus;		// 审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）
	private User ruser;		// 审核者
	private String reviewTime;		// 审核日期
	private String remark;		// 备注
	private List<DkContractProduct> dkContractProductList = Lists.newArrayList();		// 子表列表
	
	public DkContract() {
		super();
	}

	public DkContract(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="报价单ID", align=2, sort=2)
	public DkQuotation getDkQuotation() {
		return dkQuotation;
	}

	public void setDkQuotation(DkQuotation dkQuotation) {
		this.dkQuotation = dkQuotation;
	}
	
	@ExcelField(title="合同号", align=2, sort=3)
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	@ExcelField(title="顾客名称", align=2, sort=4)
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@ExcelField(title="联系方式", align=2, sort=5)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="联系地址", align=2, sort=6)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="会员ID", align=2, sort=7)
	public DkMember getDkMember() {
		return dkMember;
	}

	public void setDkMember(DkMember dkMember) {
		this.dkMember = dkMember;
	}
	
	@ExcelField(title="合同总金额", align=2, sort=8)
	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	
	@ExcelField(title="连接率", align=2, sort=9)
	public Double getConnectionRatio() {
		return connectionRatio;
	}

	public void setConnectionRatio(Double connectionRatio) {
		this.connectionRatio = connectionRatio;
	}
	
	@ExcelField(title="安装人员", fieldType=User.class, value="iuser.name", align=2, sort=10)
	public User getIuser() {
		return iuser;
	}

	public void setIuser(User iuser) {
		this.iuser = iuser;
	}
	
	@ExcelField(title="销售人员", fieldType=User.class, value="suser.name", align=2, sort=11)
	public User getSuser() {
		return suser;
	}

	public void setSuser(User suser) {
		this.suser = suser;
	}
	
	@ExcelField(title="合同类型", dictType="product_type", align=2, sort=12)
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	@ExcelField(title="审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）", dictType="review_status", align=2, sort=13)
	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	
	@ExcelField(title="审核者", fieldType=User.class, value="ruser.name", align=2, sort=14)
	public User getRuser() {
		return ruser;
	}

	public void setRuser(User ruser) {
		this.ruser = ruser;
	}
	
	@ExcelField(title="审核日期", align=2, sort=15)
	public String getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}
	
	@ExcelField(title="备注", align=2, sort=16)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public List<DkContractProduct> getDkContractProductList() {
		return dkContractProductList;
	}

	public void setDkContractProductList(List<DkContractProduct> dkContractProductList) {
		this.dkContractProductList = dkContractProductList;
	}
}