package com.jeeplus.modules.daikin.entity;

import com.jeeplus.common.persistence.DataEntity;

public class IndexReport extends DataEntity<IndexReport>{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String monthStr;
	
	private double fee;

	public String getMonthStr() {
		return monthStr;
	}

	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
