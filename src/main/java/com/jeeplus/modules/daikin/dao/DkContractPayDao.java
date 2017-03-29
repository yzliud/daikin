/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkContractPay;

/**
 * 付款计划DAO接口
 * @author LD
 * @version 2017-03-24
 */
@MyBatisDao
public interface DkContractPayDao extends CrudDao<DkContractPay> {

	
}