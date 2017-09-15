/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.modules.daikin.entity.DkQuotation;

import javax.validation.constraints.NotNull;

import com.jeeplus.modules.daikin.entity.DkMember;
import com.jeeplus.modules.sys.entity.User;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同Entity
 * @author LD
 * @version 2017-04-09
 */
public class DkContract extends DataEntity<DkContract> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private DkContract parent;		// 主合同ID
	private String contractFlag;		// 合同类型(0-主合同；1-增补合同)
	private DkQuotation dkQuotation;		// 报价单ID
	private String contractNumber;		// 合同号
	private String memberName;		// 顾客名称
	private String mobile;		// 联系方式
	private String address;		// 联系地址
	private DkMember dkMember;		// 会员ID
	private Double contractFee;		// 合同金额
	private Double totalFee;		// 合同总金额（包含增补合同）
	private Double arriveFee;		// 已到账金额
	private Double connectionRatio;		// 连接率
	private User installUser;		// 安装人员
	private User saleUser;		// 销售人员
	private User supervisionUser;		// 监理人员
	private String productType;		// 商品类型
	private String contractStatus;   //合同状态（0-未签订、1-已签订、2、安装中、3-已完工  9-已完结）
	private String reviewStatus;		// 审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）
	private User reviewUser;		// 审核者
	private Date reviewTime;		// 审核日期
	private String isReview;		// 是否有审核记录(0-没有；1-有)
	private String remark;		// 备注
	private Double costFee;		// 成本价
	private Double totalCostFee;		// 成本总价
	private Double signFee;		// 成本价
	private Double totalSignFee;		// 成本总价
	private List<DkContractProduct> dkContractProductList = Lists.newArrayList();		// 子表列表
	private Date beginDate;		// 开始 时间
	private Date endDate;		// 结束 时间
	private String isPay;         	//是否支付完
	private Double noPayFee;      //待支付金额
	private String deleteIds;
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

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
	
	public DkContract getParent() {
		return parent;
	}

	public void setParent(DkContract parent) {
		this.parent = parent;
	}
	
	@ExcelField(title="合同类型", dictType="contract_flag", align=2, sort=2)
	public String getContractFlag() {
		return contractFlag;
	}

	public void setContractFlag(String contractFlag) {
		this.contractFlag = contractFlag;
	}
	
	@NotNull(message="报价单ID不能为空")
	@ExcelField(title="报价单", align=2, sort=3,fieldType=DkQuotation.class,value="dkQuotation.name")
	public DkQuotation getDkQuotation() {
		return dkQuotation;
	}

	public void setDkQuotation(DkQuotation dkQuotation) {
		this.dkQuotation = dkQuotation;
	}
	
	@ExcelField(title="合同号", align=2, sort=4)
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	@ExcelField(title="顾客名称", align=2, sort=5)
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@ExcelField(title="联系方式", align=2, sort=6)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="联系地址", align=2, sort=7)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public DkMember getDkMember() {
		return dkMember;
	}

	public void setDkMember(DkMember dkMember) {
		this.dkMember = dkMember;
	}
	
	@NotNull(message="销售金额不能为空")
	@ExcelField(title="销售金额", align=2, sort=8)
	public Double getContractFee() {
		return contractFee;
	}

	public void setContractFee(Double contractFee) {
		this.contractFee = contractFee;
	}
	
	@ExcelField(title="销售总金额", align=2, sort=9)
	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	
	@ExcelField(title="成本价", align=2, sort=10)
	public Double getCostFee() {
		return costFee;
	}

	public void setCostFee(Double costFee) {
		this.costFee = costFee;
	}

	@ExcelField(title="成本总价", align=2, sort=11)
	public Double getTotalCostFee() {
		return totalCostFee;
	}

	public void setTotalCostFee(Double totalCostFee) {
		this.totalCostFee = totalCostFee;
	}
	
	@ExcelField(title="签单价", align=2, sort=12)
	public Double getSignFee() {
		return signFee;
	}

	public void setSignFee(Double signFee) {
		this.signFee = signFee;
	}
	
	@ExcelField(title="签单总价", align=2, sort=13)
	public Double getTotalSignFee() {
		return totalSignFee;
	}

	public void setTotalSignFee(Double totalSignFee) {
		this.totalSignFee = totalSignFee;
	}
	
	@ExcelField(title="已到账金额", align=2, sort=14)
	public Double getArriveFee() {
		return arriveFee;
	}

	public void setArriveFee(Double arriveFee) {
		this.arriveFee = arriveFee;
	}
	
	@ExcelField(title="剩余尾款", align=2, sort=15)
	public Double getNoPayFee() {
		return noPayFee;
	}

	public void setNoPayFee(Double noPayFee) {
		this.noPayFee = noPayFee;
	}
	
	@ExcelField(title="连接率", align=2, sort=16)
	public Double getConnectionRatio() {
		return connectionRatio;
	}

	public void setConnectionRatio(Double connectionRatio) {
		this.connectionRatio = connectionRatio;
	}
	
	@ExcelField(title="安装人员", fieldType=User.class, value="installUser.name", align=2, sort=17)
	public User getInstallUser() {
		return installUser;
	}

	public void setInstallUser(User installUser) {
		this.installUser = installUser;
	}
	
	@ExcelField(title="工程监理", fieldType=User.class, value="supervisionUser.name", align=2, sort=18)
	public User getSupervisionUser() {
		return supervisionUser;
	}

	public void setSupervisionUser(User supervisionUser) {
		this.supervisionUser = supervisionUser;
	}
	
	@NotNull(message="销售人员不能为空")
	@ExcelField(title="销售人员", fieldType=User.class, value="saleUser.name", align=2, sort=19)
	public User getSaleUser() {
		return saleUser;
	}

	public void setSaleUser(User saleUser) {
		this.saleUser = saleUser;
	}
	
	@ExcelField(title="商品类型", dictType="product_type", align=2, sort=20)
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	@ExcelField(title="审核状态", dictType="review_status", align=2, sort=21)
	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	
	@ExcelField(title="审核者", fieldType=User.class, value="reviewUser.name", align=2, sort=22)
	public User getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(User reviewUser) {
		this.reviewUser = reviewUser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核日期", align=2, sort=23)
	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	
	public String getIsReview() {
		return isReview;
	}

	public void setIsReview(String isReview) {
		this.isReview = isReview;
	}
	
	@ExcelField(title="备注", align=2, sort=24)
	public String getRemark() {
		return remark;
	}
	
	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
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

	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	public String getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}
}