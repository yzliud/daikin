/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.daikin.entity.DkProduct;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkProductStockRecord;
import com.jeeplus.modules.daikin.dao.DkProductStockRecordDao;

/**
 * 商品进销存Service
 * @author LD
 * @version 2017-04-05
 */
@Service
@Transactional(readOnly = true)
public class DkProductStockRecordService extends CrudService<DkProductStockRecordDao, DkProductStockRecord> {

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
		super.save(dkProductStockRecord);
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