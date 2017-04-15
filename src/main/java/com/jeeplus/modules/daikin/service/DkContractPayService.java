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
import com.jeeplus.modules.daikin.entity.DkContractPay;
import com.jeeplus.modules.daikin.dao.DkAuditRecordDao;
import com.jeeplus.modules.daikin.dao.DkContractDao;
import com.jeeplus.modules.daikin.dao.DkContractPayDao;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 合同到款Service
 * @author LD
 * @version 2017-04-13
 */
@Service
@Transactional(readOnly = true)
public class DkContractPayService extends CrudService<DkContractPayDao, DkContractPay> {

	@Autowired
	private DkAuditRecordDao dkAuditRecordDao;
	
	@Autowired
	private DkContractDao dkContractDao;
	
	public DkContractPay get(String id) {
		return super.get(id);
	}
	
	public List<DkContractPay> findList(DkContractPay dkContractPay) {
		return super.findList(dkContractPay);
	}
	
	public Page<DkContractPay> findPage(Page<DkContractPay> page, DkContractPay dkContractPay) {
		return super.findPage(page, dkContractPay);
	}
	
	@Transactional(readOnly = false)
	public void save(DkContractPay dkContractPay) {
		super.save(dkContractPay);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkContractPay dkContractPay) {
		super.delete(dkContractPay);
	}
	
	public Page<DkContract> findPageBydkContract(Page<DkContract> page, DkContract dkContract) {
		dkContract.setPage(page);
		page.setList(dao.findListBydkContract(dkContract));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void reviewContractPay(DkContractPay dcp) {
		dcp.preUpdate();
		//更新审核记录
		if( dcp.getReviewStatus().equals(Consts.ReviewStatus_2) || dcp.getReviewStatus().equals(Consts.ReviewStatus_9)){
			DkAuditRecord dkAuditRecord = new DkAuditRecord();
			dkAuditRecord.setRecordId(dcp.getId());
			dkAuditRecord.setRecordType(Consts.RecordType_2);
			dkAuditRecord.setReviewStatus(dcp.getReviewStatus());
			dkAuditRecord.setRemark(dcp.getRemark());
			dkAuditRecord.setTuser(UserUtils.getUser());
			dkAuditRecord.preInsert();
			dkAuditRecordDao.insert(dkAuditRecord);
		}
		//到款通过后，将合同到款更新
		if(dcp.getReviewStatus()!= null && dcp.getReviewStatus().equals(Consts.ReviewStatus_9)){
			DkContract dkContract = new DkContract();
			dkContract.setId(dcp.getDkContract().getId());
			dkContract.setArriveFee(dcp.getPayFee());
			dkContractDao.updateContractPay(dkContract);
		}
		
		dao.reviewContractPay(dcp);
	}
	
}