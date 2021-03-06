/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.api.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkContractSchedule;

/**
 * 合同DAO接口
 * @author LD
 * @version 2017-04-09
 */
@MyBatisDao
public interface ContractScheduleDao extends CrudDao<DkContractSchedule> {

	public List<HashMap<String, Object>> findListByContractId(String contractId, Integer beginNum, Integer pageSize);
}