package com.jeeplus.modules.daikin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.dao.DkCommonDao;
import com.jeeplus.modules.daikin.entity.DkCommon;
import com.jeeplus.modules.daikin.entity.DkProduct;

@Service
@Transactional(readOnly = true)
public class DkCommonService extends CrudService<DkCommonDao, DkCommon>{

	public DkCommon get(String id) {
		return super.get(id);
	}
	
	public Page<DkProduct> findPageBydkProduct(Page<DkProduct> page, DkProduct dkProduct) {
		dkProduct.setPage(page);
		page.setList(dao.findListBydkProduct(dkProduct));
		return page;
	}
	
}
