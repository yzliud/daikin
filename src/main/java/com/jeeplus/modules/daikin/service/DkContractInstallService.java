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
import com.jeeplus.modules.daikin.entity.DkContractInstall;
import com.jeeplus.modules.daikin.dao.DkContractInstallDao;

/**
 * 预约安装Service
 * @author LD
 * @version 2017-11-28
 */
@Service
@Transactional(readOnly = true)
public class DkContractInstallService extends CrudService<DkContractInstallDao, DkContractInstall> {

	public DkContractInstall get(String id) {
		return super.get(id);
	}
	
	public List<DkContractInstall> findList(DkContractInstall dkContractInstall) {
		return super.findList(dkContractInstall);
	}
	
	public Page<DkContractInstall> findPage(Page<DkContractInstall> page, DkContractInstall dkContractInstall) {
		return super.findPage(page, dkContractInstall);
	}
	
	@Transactional(readOnly = false)
	public void save(DkContractInstall dkContractInstall) {
		super.save(dkContractInstall);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkContractInstall dkContractInstall) {
		super.delete(dkContractInstall);
	}
	
	public Page<DkContract> findPageBydkContract(Page<DkContract> page, DkContract dkContract) {
		dkContract.setPage(page);
		page.setList(dao.findListBydkContract(dkContract));
		return page;
	}
	
	
	
}