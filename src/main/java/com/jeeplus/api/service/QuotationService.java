package com.jeeplus.api.service;

import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.dao.DkQuotationDao;
import com.jeeplus.modules.daikin.entity.DkQuotation;

public class QuotationService  extends CrudService<DkQuotationDao, DkQuotation>{

	@Transactional(readOnly = false)
	public void save(DkQuotation dkQuotation,String product) {
				
	}
}
