/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.daikin.entity.DkInstallPerson;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 合同Entity
 * @author LD
 * @version 2017-03-23
 */
public class DkContract extends DataEntity<DkContract> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String contractNumber;		// 合同号
	private String mobile;		// 联系人手机号
	private String telephone;		// 联系人电话
	private String address;		// 联系地址
	private Date orderDate;		// 订购时间
	private String memberId;		// 会员ID
	private Double totalFee;		// 合同总金额
	private Double payFee;		// 已支付金额
	private Date payDate;		// 支付时间
	private String contractStatus;		// 状态(0-已签订；1-进行中；2-已完成)
	private String payStatus;		// 支付状态(0-未支付；1-部分支付；2-已支付)
	private String installStatus;		// 安装状态(0-未安装；1-进行中；2-已完成)
	private DkInstallPerson dkInstallPerson;		// 安装人员
	private User tuser;		// 销售人员
	private String remark;		// 备注
	
	public DkContract() {
		super();
	}

	public DkContract(String id){
		super(id);
	}

	@Length(min=1, max=20, message="名称长度必须介于 1 和 20 之间")
	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=50, message="合同号长度必须介于 1 和 50 之间")
	@ExcelField(title="合同号", align=2, sort=2)
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	@Length(min=11, max=11, message="联系人手机号长度必须介于 11 和 11 之间")
	@ExcelField(title="联系人手机号", align=2, sort=3)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="联系人电话", align=2, sort=4)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@ExcelField(title="联系地址", align=2, sort=5)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="订购时间不能为空")
	@ExcelField(title="订购时间", align=2, sort=6)
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@ExcelField(title="会员ID", align=2, sort=7)
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@ExcelField(title="合同总金额", align=2, sort=8)
	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	
	@ExcelField(title="已支付金额", align=2, sort=9)
	public Double getPayFee() {
		return payFee;
	}

	public void setPayFee(Double payFee) {
		this.payFee = payFee;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="支付时间", align=2, sort=10)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@ExcelField(title="状态(0-已签订；1-进行中；2-已完成)", dictType="contract_status", align=2, sort=11)
	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	
	@ExcelField(title="支付状态(0-未支付；1-部分支付；2-已支付)", dictType="pay_status", align=2, sort=12)
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	@ExcelField(title="安装状态(0-未安装；1-进行中；2-已完成)", dictType="install_status", align=2, sort=13)
	public String getInstallStatus() {
		return installStatus;
	}

	public void setInstallStatus(String installStatus) {
		this.installStatus = installStatus;
	}
	
	@ExcelField(title="安装人员", align=2, sort=14)
	public DkInstallPerson getDkInstallPerson() {
		return dkInstallPerson;
	}

	public void setDkInstallPerson(DkInstallPerson dkInstallPerson) {
		this.dkInstallPerson = dkInstallPerson;
	}
	
	@ExcelField(title="销售人员", fieldType=User.class, value="tuser.name", align=2, sort=15)
	public User getTuser() {
		return tuser;
	}

	public void setTuser(User tuser) {
		this.tuser = tuser;
	}
	
	@ExcelField(title="备注", align=2, sort=16)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}