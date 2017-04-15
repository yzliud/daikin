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
 * 安装进度Entity
 * @author LD
 * @version 2017-04-11
 */
public class DkContractSchedule extends DataEntity<DkContractSchedule> {
	
	private static final long serialVersionUID = 1L;
	private DkContract dkContract;		// 合同
	private String descript;		// 进度描述
	private Date submitDate;		// 提交日期
	private String pic;		// 上传图片
	private Integer percent;		// 进度百分比
	private Date beginSubmitDate;		// 开始 提交日期
	private Date endSubmitDate;		// 结束 提交日期
	
	public DkContractSchedule() {
		super();
	}

	public DkContractSchedule(String id){
		super(id);
	}

	@ExcelField(title="合同ID", align=2, sort=1)
	public DkContract getDkContract() {
		return dkContract;
	}

	public void setDkContract(DkContract dkContract) {
		this.dkContract = dkContract;
	}
	
	@ExcelField(title="进度描述", align=2, sort=2)
	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="提交日期", align=2, sort=3)
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	
	@ExcelField(title="上传图片", align=2, sort=4)
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	@ExcelField(title="进度百分比", align=2, sort=5)
	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}
	
	public Date getBeginSubmitDate() {
		return beginSubmitDate;
	}

	public void setBeginSubmitDate(Date beginSubmitDate) {
		this.beginSubmitDate = beginSubmitDate;
	}
	
	public Date getEndSubmitDate() {
		return endSubmitDate;
	}

	public void setEndSubmitDate(Date endSubmitDate) {
		this.endSubmitDate = endSubmitDate;
	}
		
}