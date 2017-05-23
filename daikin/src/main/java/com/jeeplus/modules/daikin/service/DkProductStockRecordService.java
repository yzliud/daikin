/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.Consts;
import com.jeeplus.modules.daikin.entity.DkProduct;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkProductStockRecord;
import com.jeeplus.modules.daikin.dao.DkProductDao;
import com.jeeplus.modules.daikin.dao.DkProductStockRecordDao;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 商品进销存Service
 * @author LD
 * @version 2017-04-05
 */
@Service
@Transactional(readOnly = true)
public class DkProductStockRecordService extends CrudService<DkProductStockRecordDao, DkProductStockRecord> {

	@Autowired
	private DkProductDao dkProductDao;
	
	public DkProductStockRecord get(String id) {
		return super.get(id);
	}
	
	public List<DkProductStockRecord> findList(DkProductStockRecord dkProductStockRecord) {
		return super.findList(dkProductStockRecord);
	}
	
	public Page<DkProductStockRecord> findPage(Page<DkProductStockRecord> page, DkProductStockRecord dkProductStockRecord) {
		return super.findPage(page, dkProductStockRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(DkProductStockRecord dkProductStockRecord) {
		DkProduct dp = dkProductDao.get(dkProductStockRecord.getDkProduct().getId());
		int oldStock = dp.getStock();
		int nowStock = 0;
		if(dkProductStockRecord.getFlag().equals(Consts.ProductStockFlag_0)){
			nowStock = oldStock + dkProductStockRecord.getAmount();
		}else{
			nowStock = oldStock - dkProductStockRecord.getAmount();
		}
		DkProduct d = new DkProduct();
		d.setId(dp.getId());
		d.setStock(nowStock);
		dkProductStockRecord.setStockAmount(nowStock);
		dkProductStockRecord.setTuser(UserUtils.getUser());
		super.save(dkProductStockRecord);
		dkProductDao.updateProductStock(d);
		
	}
	
	@Transactional(readOnly = false)
	public void delete(DkProductStockRecord dkProductStockRecord) {
		super.delete(dkProductStockRecord);
	}
	
	public Page<DkProduct> findPageBydkProduct(Page<DkProduct> page, DkProduct dkProduct) {
		dkProduct.setPage(page);
		page.setList(dao.findListBydkProduct(dkProduct));
		return page;
	}
	
	
	
}