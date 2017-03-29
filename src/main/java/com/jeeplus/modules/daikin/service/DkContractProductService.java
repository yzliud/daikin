/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.modules.daikin.entity.DkProduct;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkContractProduct;
import com.jeeplus.modules.daikin.dao.DkContractProductDao;

/**
 * 合同商品Service
 * @author LD
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = true)
public class DkContractProductService extends CrudService<DkContractProductDao, DkContractProduct> {

	public DkContractProduct get(String id) {
		return super.get(id);
	}
	
	public List<DkContractProduct> findList(DkContractProduct dkContractProduct) {
		return super.findList(dkContractProduct);
	}
	
	public Page<DkContractProduct> findPage(Page<DkContractProduct> page, DkContractProduct dkContractProduct) {
		return super.findPage(page, dkContractProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(DkContractProduct dkContractProduct) {
		super.save(dkContractProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkContractProduct dkContractProduct) {
		super.delete(dkContractProduct);
	}
	
	public Page<DkContract> findPageBydkContract(Page<DkContract> page, DkContract dkContract) {
		dkContract.setPage(page);
		page.setList(dao.findListBydkContract(dkContract));
		return page;
	}
	public Page<DkProduct> findPageBydkProduct(Page<DkProduct> page, DkProduct dkProduct) {
		dkProduct.setPage(page);
		page.setList(dao.findListBydkProduct(dkProduct));
		return page;
	}
	
	
	
}