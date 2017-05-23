/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.IndexReport;
import com.jeeplus.modules.daikin.dao.IndexReportDao;

/**
 * 用户Service
 * @author LD
 * @version 2017-04-15
 */
@Service
@Transactional(readOnly = true)
public class IndexReportService extends CrudService<IndexReportDao, IndexReport> {
	
	public List<IndexReport> getContractFee() {
		return dao.getContractFee();
	}
	
	public List<IndexReport> getPayFee() {
		return dao.getPayFee();
	}
	
	public List<IndexReport> getSaleFee() {
		return dao.getSaleFee();
	}
}