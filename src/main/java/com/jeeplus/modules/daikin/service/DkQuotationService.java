/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.Consts;
import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.dao.DkAuditRecordDao;
import com.jeeplus.modules.daikin.dao.DkProductDao;
import com.jeeplus.modules.daikin.dao.DkQuotationDao;
import com.jeeplus.modules.daikin.entity.DkAuditRecord;
import com.jeeplus.modules.daikin.entity.DkMember;
import com.jeeplus.modules.daikin.entity.DkProduct;
import com.jeeplus.modules.daikin.entity.DkQuotationProduct;
import com.jeeplus.modules.daikin.dao.DkQuotationProductDao;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 报价单Service
 * @author LD
 * @version 2017-04-05
 */
@Service
@Transactional(readOnly = true)
public class DkQuotationService extends CrudService<DkQuotationDao, DkQuotation> {

	@Autowired
	private DkQuotationProductDao dkQuotationProductDao;
	
	@Autowired
	private DkProductDao dkProductDao;
	
	@Autowired
	private DkAuditRecordDao dkAuditRecordDao;
	
	public DkQuotation get(String id) {
		DkQuotation dkQuotation = super.get(id);
		dkQuotation.setDkQuotationProductList(dkQuotationProductDao.findList(new DkQuotationProduct(dkQuotation)));
		return dkQuotation;
	}
	
	public List<DkQuotation> findList(DkQuotation dkQuotation) {
		return super.findList(dkQuotation);
	}
	
	public Page<DkQuotation> findPage(Page<DkQuotation> page, DkQuotation dkQuotation) {
		dkQuotation.getSqlMap().put("dsf", dataScopeFilter(dkQuotation.getCurrentUser(), "o", "tuser"));
		return super.findPage(page, dkQuotation);
	}
	
	@Transactional(readOnly = false)
	public void save(DkQuotation dkQuotation) {
		dkQuotation.setReviewStatus(Consts.ReviewStatus_0);
		super.save(dkQuotation);
		DkQuotationProduct dqpEntity = new DkQuotationProduct();
		dqpEntity.setQuotationId(dkQuotation.getId());
		dkQuotationProductDao.delete(dqpEntity);
		
		for (DkQuotationProduct dkQuotationProduct : dkQuotation.getDkQuotationProductList()){
			/*if (dkQuotationProduct.getId() == null){
				continue;
			}
			DkProduct dkProduct = dkProductDao.get(dkQuotationProduct.getProductId());
			dkQuotationProduct.setBrandId(dkProduct.getBrandId());
			dkQuotationProduct.setClassifyId(dkProduct.getClassifyId());
			dkQuotationProduct.setModel(dkProduct.getModel());
			dkQuotationProduct.setCapacityModel(dkProduct.getCapacityModel());
			dkQuotationProduct.setName(dkProduct.getName());
			dkQuotationProduct.setPlace(dkProduct.getPlace());
			dkQuotationProduct.setUnit(dkProduct.getUnit());
			dkQuotationProduct.setProductType(dkProduct.getProductType());
			dkQuotationProduct.setCostPrice(dkProduct.getCostPrice());
			
			if (DkQuotationProduct.DEL_FLAG_NORMAL.equals(dkQuotationProduct.getDelFlag())){
				if (StringUtils.isBlank(dkQuotationProduct.getId())){
					dkQuotationProduct.setQuotationId(dkQuotation.getId());
					dkQuotationProduct.preInsert();
					dkQuotationProductDao.insert(dkQuotationProduct);
				}else{
					dkQuotationProduct.preUpdate();
					dkQuotationProductDao.update(dkQuotationProduct);
				}
			}else{
				dkQuotationProductDao.delete(dkQuotationProduct);
			}*/
			if(dkQuotationProduct.getProductId() != null && !dkQuotationProduct.getProductId().equals("") ){
				if(dkQuotation.getDeleteIds().equals("") ||( !dkQuotation.getDeleteIds().contains(dkQuotationProduct.getId()) )){
					DkProduct dkProduct = dkProductDao.get(dkQuotationProduct.getProductId());
					dkQuotationProduct.setBrandId(dkProduct.getBrandId());
					dkQuotationProduct.setClassifyId(dkProduct.getClassifyId());
					dkQuotationProduct.setModel(dkProduct.getModel());
					dkQuotationProduct.setCapacityModel(dkProduct.getCapacityModel());
					dkQuotationProduct.setName(dkProduct.getName());
					dkQuotationProduct.setPlace(dkProduct.getPlace());
					dkQuotationProduct.setUnit(dkProduct.getUnit());
					dkQuotationProduct.setProductType(dkProduct.getProductType());
					//dkQuotationProduct.setCostPrice(dkProduct.getCostPrice());
					dkQuotationProduct.setId("");
					dkQuotationProduct.setQuotationId(dkQuotation.getId());
					dkQuotationProduct.preInsert();
					dkQuotationProductDao.insert(dkQuotationProduct);
				}
			}
			
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(DkQuotation dkQuotation) {
		super.delete(dkQuotation);
		dkQuotationProductDao.delete(new DkQuotationProduct(dkQuotation));
	}
	
	public Page<DkMember> findPageBydkMember(Page<DkMember> page, DkMember dkMember) {
		dkMember.setPage(page);
		dkMember.getSqlMap().put("dsf", dataScopeFilter(dkMember.getCurrentUser(), "o", "tuser"));
		page.setList(dao.findListBydkMember(dkMember));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void updateReviewStatus(DkQuotation dkQuotation) {
		if( dkQuotation.getReviewStatus().equals(Consts.ReviewStatus_2) || dkQuotation.getReviewStatus().equals(Consts.ReviewStatus_9)){
			DkAuditRecord dkAuditRecord = new DkAuditRecord();
			dkAuditRecord.setRecordId(dkQuotation.getId());
			dkAuditRecord.setRecordType(Consts.RecordType_0);
			dkAuditRecord.setReviewStatus(dkQuotation.getReviewStatus());
			dkAuditRecord.setRemark(dkQuotation.getRemark());
			dkAuditRecord.setTuser(UserUtils.getUser());
			dkAuditRecord.preInsert();
			dkAuditRecordDao.insert(dkAuditRecord);
		}
		dkQuotation.setRuser(UserUtils.getUser());
		dao.updateReviewStatus(dkQuotation);
	}
	
	public DkQuotation getSingle(DkQuotation dkQuotation) {
		return dao.getSingle(dkQuotation);
	}
	
}