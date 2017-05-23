/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkAuditRecord;
import com.jeeplus.modules.daikin.dao.DkAuditRecordDao;

/**
 * 审核记录Service
 * @author LD
 * @version 2017-04-09
 */
@Service
@Transactional(readOnly = true)
public class DkAuditRecordService extends CrudService<DkAuditRecordDao, DkAuditRecord> {

	public DkAuditRecord get(String id) {
		return super.get(id);
	}
	
	public List<DkAuditRecord> findList(DkAuditRecord dkAuditRecord) {
		return super.findList(dkAuditRecord);
	}
	
	public Page<DkAuditRecord> findPage(Page<DkAuditRecord> page, DkAuditRecord dkAuditRecord) {
		return super.findPage(page, dkAuditRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(DkAuditRecord dkAuditRecord) {
		super.save(dkAuditRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkAuditRecord dkAuditRecord) {
		super.delete(dkAuditRecord);
	}
	
	
	
	
}