/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.api.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.api.dao.ContractScheduleDao;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkContractSchedule;

/**
 * 合同Service
 * 
 * @author LD
 * @version 2017-04-09
 */
@Service
@Transactional(readOnly = true)
public class ContractScheduleService extends CrudService<ContractScheduleDao, DkContractSchedule> {

	public List<HashMap<String, Object>> findListByContractId(String contractId, Integer beginNum, Integer pageSize) {
		// TODO Auto-generated method stub
		return dao.findListByContractId(contractId,beginNum,pageSize);
	}

}