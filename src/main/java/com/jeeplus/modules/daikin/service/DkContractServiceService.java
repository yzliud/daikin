/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.Consts;
import com.jeeplus.modules.daikin.entity.DkAuditRecord;
import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkContractService;
import com.jeeplus.modules.daikin.dao.DkAuditRecordDao;
import com.jeeplus.modules.daikin.dao.DkContractServiceDao;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 维保记录Service
 * @author LD
 * @version 2017-05-06
 */
@Service
@Transactional(readOnly = true)
public class DkContractServiceService extends CrudService<DkContractServiceDao, DkContractService> {
	
	@Autowired
	private DkAuditRecordDao dkAuditRecordDao;

	public DkContractService get(String id) {
		return super.get(id);
	}
	
	public List<DkContractService> findList(DkContractService dkContractService) {
		return super.findList(dkContractService);
	}
	
	public Page<DkContractService> findPage(Page<DkContractService> page, DkContractService dkContractService) {
		return super.findPage(page, dkContractService);
	}
	
	@Transactional(readOnly = false)
	public void save(DkContractService dkContractService) {
		super.save(dkContractService);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkContractService dkContractService) {
		super.delete(dkContractService);
	}
	
	public Page<DkContract> findPageBydkContract(Page<DkContract> page, DkContract dkContract) {
		dkContract.setPage(page);
		page.setList(dao.findListBydkContract(dkContract));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void checkService(DkContractService dis) {
		dis.preUpdate();
		//更新审核记录
		if( dis.getReviewStatus().equals(Consts.ReviewStatus_2) || dis.getReviewStatus().equals(Consts.ReviewStatus_9)){
			DkAuditRecord dkAuditRecord = new DkAuditRecord();
			dkAuditRecord.setRecordId(dis.getId());
			dkAuditRecord.setRecordType(Consts.RecordType_4);
			dkAuditRecord.setReviewStatus(dis.getReviewStatus());
			dkAuditRecord.setRemark(dis.getServiceContent());
			dkAuditRecord.setTuser(UserUtils.getUser());
			dkAuditRecord.preInsert();
			dkAuditRecordDao.insert(dkAuditRecord);
		}

		dao.checkService(dis);
	}
	
}