/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkQuotationProduct;

/**
 * 报价单DAO接口
 * @author LD
 * @version 2017-04-05
 */
@MyBatisDao
public interface DkQuotationProductDao extends CrudDao<DkQuotationProduct> {

	public List<String> findListByproductId(String productId);
	
}