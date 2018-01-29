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
import com.jeeplus.modules.daikin.entity.DkContractOrder;
import com.jeeplus.modules.daikin.dao.DkContractOrderDao;

/**
 * 订货单Service
 * @author LD
 * @version 2018-01-29
 */
@Service
@Transactional(readOnly = true)
public class DkContractOrderService extends CrudService<DkContractOrderDao, DkContractOrder> {

	public DkContractOrder get(String id) {
		return super.get(id);
	}
	
	public List<DkContractOrder> findList(DkContractOrder dkContractOrder) {
		return super.findList(dkContractOrder);
	}
	
	public Page<DkContractOrder> findPage(Page<DkContractOrder> page, DkContractOrder dkContractOrder) {
		return super.findPage(page, dkContractOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(DkContractOrder dkContractOrder) {
		super.save(dkContractOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkContractOrder dkContractOrder) {
		super.delete(dkContractOrder);
	}
	
	public Page<DkContract> findPageBydkContract(Page<DkContract> page, DkContract dkContract) {
		dkContract.setPage(page);
		page.setList(dao.findListBydkContract(dkContract));
		return page;
	}
	
	
	
}