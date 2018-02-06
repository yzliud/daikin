/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.Consts;
import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.modules.daikin.dao.DkAuditRecordDao;
import com.jeeplus.modules.daikin.dao.DkContractDao;
import com.jeeplus.modules.daikin.dao.DkProductDao;
import com.jeeplus.modules.daikin.dao.DkProductStockRecordDao;
import com.jeeplus.modules.daikin.entity.DkAuditRecord;
import com.jeeplus.modules.daikin.entity.DkProduct;
import com.jeeplus.modules.daikin.entity.DkProductStockRecord;
import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.entity.DkMember;
import com.jeeplus.modules.daikin.entity.DkContractProduct;
import com.jeeplus.modules.daikin.dao.DkContractProductDao;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 合同Service
 * @author LD
 * @version 2017-04-09
 */
@Service
@Transactional(readOnly = true)
public class DkContractService extends CrudService<DkContractDao, DkContract> {

	@Autowired
	private DkContractProductDao dkContractProductDao;
	
	@Autowired
	private DkAuditRecordDao dkAuditRecordDao;
	
	@Autowired
	private DkProductDao dkProductDao;
	
	@Autowired
	private DkProductStockRecordDao dkProductStockRecordDao;
	
	public DkContract get(String id) {
		DkContract dkContract = super.get(id);
		dkContract.setDkContractProductList(dkContractProductDao.findList(new DkContractProduct(dkContract)));
		return dkContract;
	}
	
	public List<DkContract> findList(DkContract dkContract) {
		return super.findList(dkContract);
	}
	
	public Page<DkContract> findPage(Page<DkContract> page, DkContract dkContract) {
		dkContract.getSqlMap().put("dsf", dataScopeFilter(dkContract.getCurrentUser(), "o", "saleUser"));
		return super.findPage(page, dkContract);
	}
	
	@Transactional(readOnly = false)
	public void save(DkContract dkContract) {
		super.save(dkContract);
		
		DkContractProduct dcpEntity = new DkContractProduct();
		dcpEntity.setContractId(dkContract.getId());
		dkContractProductDao.delete(dcpEntity);
		
		for (DkContractProduct dkContractProduct : dkContract.getDkContractProductList()){
			/*if (dkContractProduct.getId() == null){
				continue;
			}
			DkProduct dkProduct = dkProductDao.get(dkContractProduct.getProductId());
			dkContractProduct.setBrandId(dkProduct.getBrandId());
			dkContractProduct.setClassifyId(dkProduct.getClassifyId());
			dkContractProduct.setModel(dkProduct.getModel());
			dkContractProduct.setCapacityModel(dkProduct.getCapacityModel());
			dkContractProduct.setName(dkProduct.getName());
			dkContractProduct.setPlace(dkProduct.getPlace());
			dkContractProduct.setUnit(dkProduct.getUnit());
			dkContractProduct.setProductType(dkProduct.getProductType());
			dkContractProduct.setCostPrice(dkProduct.getCostPrice());
			if (DkContractProduct.DEL_FLAG_NORMAL.equals(dkContractProduct.getDelFlag())){
				if (StringUtils.isBlank(dkContractProduct.getId())){
					dkContractProduct.setContractId(dkContract.getId());
					dkContractProduct.preInsert();
					dkContractProductDao.insert(dkContractProduct);
				}else{
					dkContractProduct.preUpdate();
					dkContractProductDao.update(dkContractProduct);
				}
			}else{
				dkContractProductDao.delete(dkContractProduct);
			}*/
			if(dkContractProduct.getProductId() != null && !dkContractProduct.getProductId().equals("") ){
				if(dkContract.getDeleteIds().equals("") ||( !dkContract.getDeleteIds().contains(dkContractProduct.getId()) )){
					DkProduct dkProduct = dkProductDao.get(dkContractProduct.getProductId());
					dkContractProduct.setBrandId(dkProduct.getBrandId());
					dkContractProduct.setClassifyId(dkProduct.getClassifyId());
					dkContractProduct.setModel(dkProduct.getModel());
					dkContractProduct.setCapacityModel(dkProduct.getCapacityModel());
					dkContractProduct.setName(dkProduct.getName());
					dkContractProduct.setPlace(dkProduct.getPlace());
					dkContractProduct.setUnit(dkProduct.getUnit());
					dkContractProduct.setProductType(dkProduct.getProductType());
					dkContractProduct.setId("");
					dkContractProduct.setContractId(dkContract.getId());
					dkContractProduct.preInsert();
					dkContractProductDao.insert(dkContractProduct);
				}
			}
		}
		
		dao.updateContractCostFee(dkContract);
		
		if(dkContract.getReviewStatus().equals(Consts.ReviewStatus_9)){
			DkContract d = new DkContract();
			d.setParent(dkContract.getParent());
			dao.updateContractTotalFee(d);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(DkContract dkContract) {
		super.delete(dkContract);
		dkContractProductDao.delete(new DkContractProduct(dkContract));
	}
	
	public Page<DkContract> findPageByparent(Page<DkContract> page, DkContract parent) {
		parent.setPage(page);
		parent.getSqlMap().put("dsf", dataScopeFilter(parent.getCurrentUser(), "o", "saleUser"));
		page.setList(dao.findListByparent(parent));
		return page;
	}
	public Page<DkQuotation> findPageBydkQuotation(Page<DkQuotation> page, DkQuotation dkQuotation) {
		dkQuotation.setPage(page);
		dkQuotation.getSqlMap().put("dsf", dataScopeFilter(dkQuotation.getCurrentUser(), "o", "saleUser"));
		page.setList(dao.findListBydkQuotation(dkQuotation));
		return page;
	}
	public Page<DkMember> findPageBydkMember(Page<DkMember> page, DkMember dkMember) {
		dkMember.setPage(page);
		page.setList(dao.findListBydkMember(dkMember));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void add(DkContract dkContract) {
		dkContract.setReviewStatus(Consts.ReviewStatus_0);
		dkContract.preInsert();
		if(dkContract.getParent() == null || dkContract.getParent().getId()==null || dkContract.getParent().getId().equals("")){
			DkContract dc = new DkContract();
			dc.setId(dkContract.getId());
			dkContract.setParent(dc);
		}
		
		dao.add(dkContract);
		dkContractProductDao.add(dkContract);
		//dao.updateContractCostFee(dkContract);
	}
	
	public DkContract getSingle(DkContract dc) {
		DkContract dkContract = dao.getSingle(dc);
		return dkContract;
	}
	
	@Transactional(readOnly = false)
	public void reviewContract(DkContract dc) {
		dc.preUpdate();
		if( dc.getReviewStatus().equals(Consts.ReviewStatus_2) || dc.getReviewStatus().equals(Consts.ReviewStatus_9)){
			DkAuditRecord dkAuditRecord = new DkAuditRecord();
			dkAuditRecord.setRecordId(dc.getId());
			dkAuditRecord.setRecordType(Consts.RecordType_1);
			dkAuditRecord.setReviewStatus(dc.getReviewStatus());
			dkAuditRecord.setRemark(dc.getRemark());
			dkAuditRecord.setTuser(UserUtils.getUser());
			dkAuditRecord.preInsert();
			dkAuditRecordDao.insert(dkAuditRecord);
		
		}
		dao.reviewContract(dc);
		
		if(dc.getReviewStatus().equals(Consts.ReviewStatus_9)){
			DkContract d = new DkContract();
			d.setParent(dc.getParent());
			dao.updateContractTotalFee(d);
		}
	}
	
	@Transactional(readOnly = false)
	public void assignInstall(DkContract dc) {
		dao.assignInstall(dc);
	}
	
	@Transactional(readOnly = false)
	public void stockSave(DkContract dkContract) {
		
		for (DkContractProduct dkContractProduct : dkContract.getDkContractProductList()){
			if(dkContractProduct.getProductId() != null && !dkContractProduct.getProductId().equals("") ){
				if(dkContract.getDeleteIds().equals("") ||( !dkContract.getDeleteIds().contains(dkContractProduct.getId()) )){
					if(dkContractProduct.getNowStockOut() > 0){
						//合同商品表处理
						dkContractProduct.setStockOut(dkContractProduct.getStockOut() + dkContractProduct.getNowStockOut());
						dkContractProductDao.updateStockOut(dkContractProduct);
						
						//商品表库存处理
						DkProduct dp = dkProductDao.get(dkContractProduct.getProductId());
						int oldStock = dp.getStock();
						int nowStock  = oldStock - dkContractProduct.getNowStockOut();
						DkProduct d = new DkProduct();
						d.setId(dp.getId());
						d.setStock(nowStock);
						dkProductDao.updateProductStock(d);
						
						//商品出入口表添加出库数据
						DkProductStockRecord dkProductStockRecord = new DkProductStockRecord();
						dkProductStockRecord.setDkProduct(d);
						dkProductStockRecord.setFlag(Consts.ProductStockFlag_1);
						dkProductStockRecord.setAmount(dkContractProduct.getNowStockOut());
						dkProductStockRecord.setStockAmount(nowStock);
						dkProductStockRecord.setOperateTime(new Date());
						dkProductStockRecord.setContractNum(dkContract.getContractNumber());
						dkProductStockRecord.setTuser(UserUtils.getUser());
						dkProductStockRecord.setRemark(dkContract.getAddress()+":"+dkContractProduct.getStockOutDes());
						dkProductStockRecord.preInsert();
						dkProductStockRecordDao.insert(dkProductStockRecord);
					}
				}
			}
		}
	}
}