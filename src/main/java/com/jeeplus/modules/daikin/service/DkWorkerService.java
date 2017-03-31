/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkWorker;
import com.jeeplus.modules.daikin.dao.DkWorkerDao;

/**
 * 微信用户Service
 * @author LD
 * @version 2017-03-31
 */
@Service
@Transactional(readOnly = true)
public class DkWorkerService extends CrudService<DkWorkerDao, DkWorker> {

	public DkWorker get(String id) {
		return super.get(id);
	}
	
	public List<DkWorker> findList(DkWorker dkWorker) {
		return super.findList(dkWorker);
	}
	
	public Page<DkWorker> findPage(Page<DkWorker> page, DkWorker dkWorker) {
		return super.findPage(page, dkWorker);
	}
	
	@Transactional(readOnly = false)
	public void save(DkWorker dkWorker) {
		super.save(dkWorker);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkWorker dkWorker) {
		super.delete(dkWorker);
	}
	
	
	
	
}