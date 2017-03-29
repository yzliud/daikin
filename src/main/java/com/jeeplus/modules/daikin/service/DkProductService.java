/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkProduct;
import com.jeeplus.modules.daikin.dao.DkProductDao;

/**
 * 商品Service
 * @author LD
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = true)
public class DkProductService extends CrudService<DkProductDao, DkProduct> {

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
		super.save(dkProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkProduct dkProduct) {
		super.delete(dkProduct);
	}
	
	
	
	
}