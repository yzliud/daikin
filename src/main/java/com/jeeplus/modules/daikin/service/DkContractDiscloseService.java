/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkContractDisclose;
import com.jeeplus.modules.daikin.dao.DkContractDiscloseDao;

/**
 * 预约交底Service
 * @author LD
 * @version 2017-11-27
 */
@Service
@Transactional(readOnly = true)
public class DkContractDiscloseService extends CrudService<DkContractDiscloseDao, DkContractDisclose> {

	public DkContractDisclose get(String id) {
		return super.get(id);
	}
	
	public List<DkContractDisclose> findList(DkContractDisclose dkContractDisclose) {
		return super.findList(dkContractDisclose);
	}
	
	public Page<DkContractDisclose> findPage(Page<DkContractDisclose> page, DkContractDisclose dkContractDisclose) {
		dkContractDisclose.getSqlMap().put("dsf", dataScopeFilter(dkContractDisclose.getCurrentUser(), "o", "saleUser"));
		return super.findPage(page, dkContractDisclose);
	}
	
	@Transactional(readOnly = false)
	public void save(DkContractDisclose dkContractDisclose) {
		super.save(dkContractDisclose);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkContractDisclose dkContractDisclose) {
		super.delete(dkContractDisclose);
	}
	
	public Page<DkContract> findPageBydkContract(Page<DkContract> page, DkContract dkContract) {
		dkContract.setPage(page);
		dkContract.getSqlMap().put("dsf", dataScopeFilter(dkContract.getCurrentUser(), "o", "saleUser"));
		page.setList(dao.findListBydkContract(dkContract));
		return page;
	}
	
	
	
}