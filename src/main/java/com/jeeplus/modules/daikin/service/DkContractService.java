/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.daikin.entity.DkInstallPerson;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.modules.daikin.dao.DkContractDao;

/**
 * 合同Service
 * @author LD
 * @version 2017-03-23
 */
@Service
@Transactional(readOnly = true)
public class DkContractService extends CrudService<DkContractDao, DkContract> {

	public DkContract get(String id) {
		return super.get(id);
	}
	
	public List<DkContract> findList(DkContract dkContract) {
		return super.findList(dkContract);
	}
	
	public Page<DkContract> findPage(Page<DkContract> page, DkContract dkContract) {
		return super.findPage(page, dkContract);
	}
	
	@Transactional(readOnly = false)
	public void save(DkContract dkContract) {
		super.save(dkContract);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkContract dkContract) {
		super.delete(dkContract);
	}
	
	public Page<DkInstallPerson> findPageBydkInstallPerson(Page<DkInstallPerson> page, DkInstallPerson dkInstallPerson) {
		dkInstallPerson.setPage(page);
		page.setList(dao.findListBydkInstallPerson(dkInstallPerson));
		return page;
	}
	
	
	
}