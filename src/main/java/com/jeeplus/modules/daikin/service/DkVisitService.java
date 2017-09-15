/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkVisit;
import com.jeeplus.modules.daikin.dao.DkVisitDao;

/**
 * 上门登记Service
 * @author 菜鸟
 * @version 2017-09-15
 */
@Service
@Transactional(readOnly = true)
public class DkVisitService extends CrudService<DkVisitDao, DkVisit> {

	public DkVisit get(String id) {
		return super.get(id);
	}
	
	public List<DkVisit> findList(DkVisit dkVisit) {
		return super.findList(dkVisit);
	}
	
	public Page<DkVisit> findPage(Page<DkVisit> page, DkVisit dkVisit) {
		dkVisit.getSqlMap().put("dsf", dataScopeFilter(dkVisit.getCurrentUser(), "o", "recordBy"));
		return super.findPage(page, dkVisit);
	}
	
	@Transactional(readOnly = false)
	public void save(DkVisit dkVisit) {
		super.save(dkVisit);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkVisit dkVisit) {
		super.delete(dkVisit);
	}
	
	
	
	
}