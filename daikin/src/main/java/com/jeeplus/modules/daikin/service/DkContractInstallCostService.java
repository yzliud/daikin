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
import com.jeeplus.modules.daikin.entity.DkContractInstallCost;
import com.jeeplus.modules.daikin.dao.DkAuditRecordDao;
import com.jeeplus.modules.daikin.dao.DkContractInstallCostDao;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 安装付款Service
 * @author LD
 * @version 2017-05-06
 */
@Service
@Transactional(readOnly = true)
public class DkContractInstallCostService extends CrudService<DkContractInstallCostDao, DkContractInstallCost> {
	
	@Autowired
	private DkAuditRecordDao dkAuditRecordDao;

	public DkContractInstallCost get(String id) {
		return super.get(id);
	}
	
	public List<DkContractInstallCost> findList(DkContractInstallCost dkContractInstallCost) {
		return super.findList(dkContractInstallCost);
	}
	
	public Page<DkContractInstallCost> findPage(Page<DkContractInstallCost> page, DkContractInstallCost dkContractInstallCost) {
		return super.findPage(page, dkContractInstallCost);
	}
	
	@Transactional(readOnly = false)
	public void save(DkContractInstallCost dkContractInstallCost) {
		super.save(dkContractInstallCost);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkContractInstallCost dkContractInstallCost) {
		super.delete(dkContractInstallCost);
	}
	
	public Page<DkContract> findPageBydkContract(Page<DkContract> page, DkContract dkContract) {
		dkContract.setPage(page);
		page.setList(dao.findListBydkContract(dkContract));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void checkInstallCost(DkContractInstallCost dis) {
		dis.preUpdate();
		//更新审核记录
		if( dis.getReviewStatus().equals(Consts.ReviewStatus_2) || dis.getReviewStatus().equals(Consts.ReviewStatus_9)){
			DkAuditRecord dkAuditRecord = new DkAuditRecord();
			dkAuditRecord.setRecordId(dis.getId());
			dkAuditRecord.setRecordType(Consts.RecordType_3);
			dkAuditRecord.setReviewStatus(dis.getReviewStatus());
			dkAuditRecord.setRemark(dis.getRemark());
			dkAuditRecord.setTuser(UserUtils.getUser());
			dkAuditRecord.preInsert();
			dkAuditRecordDao.insert(dkAuditRecord);
		}

		dao.checkInstallCost(dis);
	}
	
}