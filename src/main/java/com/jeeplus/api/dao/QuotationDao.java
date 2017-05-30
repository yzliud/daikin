package com.jeeplus.api.dao;

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

	
}
