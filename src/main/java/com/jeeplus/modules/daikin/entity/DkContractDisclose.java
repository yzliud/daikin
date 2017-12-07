/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.daikin.entity.DkContract;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 预约交底Entity
 * @author LD
 * @version 2017-11-27
 */
public class DkContractDisclose extends DataEntity<DkContractDisclose> {
	
	private static final long serialVersionUID = 1L;
	private DkContract dkContract;		// 合同ID
	private Date payDate;		// 交底时间
	private String remark;		// 备注
	private Date beginPayDate;		// 开始 交底时间
	private Date endPayDate;		// 结束 交底时间
	
	public DkContractDisclose() {
		super();
	}

	public DkContractDisclose(String id){
		super(id);
	}

	@ExcelField(title="合同", align=2, sort=1,fieldType=DkContract.class,value="dkContract.name")
	public DkContract getDkContract() {
		return dkContract;
	}

	public void setDkContract(DkContract dkContract) {
		this.dkContract = dkContract;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="交底时间", align=2, sort=2)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@ExcelField(title="备注", align=2, sort=3)
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