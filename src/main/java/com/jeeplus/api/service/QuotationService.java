package com.jeeplus.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.api.dao.QuotationDao;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkQuotation;

@Service
@Transactional(readOnly = true)
public class QuotationService  extends CrudService<QuotationDao, DkQuotation>{

	public void save(DkQuotation dkQuotation,String product) {
				
	}

}
