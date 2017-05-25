package com.jeeplus.modules.daikin.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkCommon;
import com.jeeplus.modules.daikin.entity.DkProduct;

@MyBatisDao
public interface DkCommonDao extends CrudDao<DkCommon> {

	public List<DkProduct> findListBydkProduct(DkProduct dkProduct);
	
}
