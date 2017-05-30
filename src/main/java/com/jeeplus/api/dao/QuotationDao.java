package com.jeeplus.api.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.api.entity.Quotation;
import com.jeeplus.api.entity.QuotationProduct;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkQuotation;

/**
 * 报价单DAO接口
 * @author LD
 * @version 2017-04-09
 */
@MyBatisDao
public interface QuotationDao extends CrudDao<DkQuotation> {

	List<HashMap<String, Object>> getAllProduct();

	void saveQuotation(Quotation quotation);

	void saveQuotationProduct(QuotationProduct quotationProduct);

	
}
