/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkContractPay;
import com.jeeplus.modules.daikin.dao.DkContractPayDao;

/**
 * 付款计划Service
 * @author LD
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = true)
public class DkContractPayService extends CrudService<DkContractPayDao, DkContractPay> {

	public DkContractPay get(String id) {
		return super.get(id);
	}
	
	public List<DkContractPay> findList(DkContractPay dkContractPay) {
		return super.findList(dkContractPay);
	}
	
	public Page<DkContractPay> findPage(Page<DkContractPay> page, DkContractPay dkContractPay) {
		return super.findPage(page, dkContractPay);
	}
	
	@Transactional(readOnly = false)
	public void save(DkContractPay dkContractPay) {
		super.save(dkContractPay);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkContractPay dkContractPay) {
		super.delete(dkContractPay);
	}
	
	
	
	
}