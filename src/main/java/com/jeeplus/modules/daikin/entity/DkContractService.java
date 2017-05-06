/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.daikin.entity.DkContract;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 维保记录Entity
 * @author LD
 * @version 2017-05-06
 */
public class DkContractService extends DataEntity<DkContractService> {
	
	private static final long serialVersionUID = 1L;
	private DkContract dkContract;		// 合同ID
	private Date serviceDate;		// 维保时间
	private String servicePerson;		// 维保人员
	private String reviewStatus;		// 审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）
	private String reviewBy;		// 审核者
	private String isReview;		// 是否有审核记录(0-没有；1-有)
	private String serviceContent;		// 维保记录
	private Date beginServiceDate;		// 开始 维保时间
	private Date endServiceDate;		// 结束 维保时间
	
	public DkContractService() {
		super();
	}

	public DkContractService(String id){
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
	@NotNull(message="维保时间不能为空")
	@ExcelField(title="维保时间", align=2, sort=2)
	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	
	@Length(min=1, max=20, message="维保人员长度必须介于 1 和 20 之间")
	@ExcelField(title="维保人员", align=2, sort=3)
	public String getServicePerson() {
		return servicePerson;
	}

	public void setServicePerson(String servicePerson) {
		this.servicePerson = servicePerson;
	}
	
	@ExcelField(title="审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）", align=2, sort=4)
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
	
	@ExcelField(title="是否有审核记录(0-没有；1-有)", align=2, sort=6)
	public String getIsReview() {
		return isReview;
	}

	public void setIsReview(String isReview) {
		this.isReview = isReview;
	}
	
	@ExcelField(title="维保记录", align=2, sort=7)
	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}
	
	public Date getBeginServiceDate() {
		return beginServiceDate;
	}

	public void setBeginServiceDate(Date beginServiceDate) {
		this.beginServiceDate = beginServiceDate;
	}
	
	public Date getEndServiceDate() {
		return endServiceDate;
	}

	public void setEndServiceDate(Date endServiceDate) {
		this.endServiceDate = endServiceDate;
	}
		
}