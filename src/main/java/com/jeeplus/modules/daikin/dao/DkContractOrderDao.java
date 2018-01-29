/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.modules.daikin.entity.DkContract;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkContractOrder;

/**
 * 订货单DAO接口
 * @author LD
 * @version 2018-01-29
 */
@MyBatisDao
public interface DkContractOrderDao extends CrudDao<DkContractOrder> {

	public List<DkContract> findListBydkContract(DkContract dkContract);
	
}