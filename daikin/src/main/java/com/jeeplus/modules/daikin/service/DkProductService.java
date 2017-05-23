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
import com.jeeplus.modules.daikin.entity.DkProduct;
import com.jeeplus.modules.daikin.entity.DkProductStockRecord;
import com.jeeplus.modules.daikin.dao.DkProductDao;
import com.jeeplus.modules.daikin.dao.DkProductStockRecordDao;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 商品Service
 * @author LD
 * @version 2017-04-05
 */
@Service
@Transactional(readOnly = true)
public class DkProductService extends CrudService<DkProductDao, DkProduct> {
	
	@Autowired
	private DkProductDao dkProductDao;
	
	@Autowired
	private DkProductStockRecordDao dkProductStockRecordDao;

	public DkProduct get(String id) {
		return super.get(id);
	}
	
	public List<DkProduct> findList(DkProduct dkProduct) {
		return super.findList(dkProduct);
	}
	
	public Page<DkProduct> findPage(Page<DkProduct> page, DkProduct dkProduct) {
		return super.findPage(page, dkProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(DkProduct dkProduct) {
		boolean isnew = dkProduct.getIsNewRecord();
		super.save(dkProduct);
		if (isnew){
			if(dkProduct.getStock() > 0){
				DkProductStockRecord dkProductStockRecord = new DkProductStockRecord();
				dkProductStockRecord.setDkProduct(dkProduct);
				dkProductStockRecord.setAmount(dkProduct.getStock());
				dkProductStockRecord.setStockAmount(dkProduct.getStock());
				dkProductStockRecord.setFlag(Consts.ProductStockFlag_0);
				dkProductStockRecord.setRemark("商品录入时入库数量");
				dkProductStockRecord.setTuser(UserUtils.getUser());
				dkProductStockRecord.preInsert();
				dkProductStockRecordDao.insert(dkProductStockRecord);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(DkProduct dkProduct) {
		super.delete(dkProduct);
	}
	
	public DkProduct getByName(DkProduct dkProduct) {
		return dkProductDao.getByName( dkProduct);
	}
	
	
}