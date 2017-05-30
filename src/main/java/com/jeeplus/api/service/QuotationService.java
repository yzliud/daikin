package com.jeeplus.api.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.api.dao.QuotationDao;
import com.jeeplus.api.entity.Quotation;
import com.jeeplus.api.entity.QuotationProduct;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkQuotation;

@Service
@Transactional(readOnly = false)
public class QuotationService  extends CrudService<QuotationDao, DkQuotation>{

	public void save(Quotation quotation) {
		dao.saveQuotation(quotation);
		
		List<QuotationProduct> quotationProductList = quotation.getQuotationProductList();
		for (QuotationProduct quotationProduct : quotationProductList) {
			dao.saveQuotationProduct(quotationProduct);
		}
		
	}

	public List<HashMap<String, Object>> getAllProduct() {
		// TODO Auto-generated method stub
		return dao.getAllProduct();
	}

}
