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
import com.jeeplus.modules.daikin.entity.DkContractSchedule;
import com.jeeplus.modules.daikin.dao.DkContractScheduleDao;

/**
 * 安装进度Service
 * @author LD
 * @version 2017-04-16
 */
@Service
@Transactional(readOnly = true)
public class DkContractScheduleService extends CrudService<DkContractScheduleDao, DkContractSchedule> {

	public DkContractSchedule get(String id) {
		return super.get(id);
	}
	
	public List<DkContractSchedule> findList(DkContractSchedule dkContractSchedule) {
		return super.findList(dkContractSchedule);
	}
	
	public Page<DkContractSchedule> findPage(Page<DkContractSchedule> page, DkContractSchedule dkContractSchedule) {
		dkContractSchedule.getSqlMap().put("dsf", dataScopeFilter(dkContractSchedule.getCurrentUser(), "o", "saleUser"));
		return super.findPage(page, dkContractSchedule);
	}
	
	@Transactional(readOnly = false)
	public void save(DkContractSchedule dkContractSchedule) {
		super.save(dkContractSchedule);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkContractSchedule dkContractSchedule) {
		super.delete(dkContractSchedule);
	}
	
	public Page<DkContract> findPageBydkContract(Page<DkContract> page, DkContract dkContract) {
		dkContract.setPage(page);
		page.setList(dao.findListBydkContract(dkContract));
		return page;
	}
	
	
	
}