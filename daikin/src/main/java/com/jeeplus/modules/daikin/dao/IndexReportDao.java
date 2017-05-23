package com.jeeplus.modules.daikin.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.IndexReport;

@MyBatisDao
public interface IndexReportDao extends CrudDao<IndexReport>{

	public List<IndexReport> getContractFee();
	public List<IndexReport> getPayFee();
	public List<IndexReport> getSaleFee();
}
