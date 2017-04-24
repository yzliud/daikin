/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.api.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.api.dao.ContractDao;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkContract;

/**
 * 合同Service
 * 
 * @author LD
 * @version 2017-04-09
 */
@Service
@Transactional(readOnly = true)
public class ContractService extends CrudService<ContractDao, DkContract> {

	public List<HashMap<String, Object>> findListByMobile(String mobile,int beginNum,int pageSize) {
		// TODO Auto-generated method stub
		return dao.findListByMobile(mobile,beginNum,pageSize);
	}

	public List<HashMap<String, Object>> findListByInstall(String sysId, Integer beginNum, Integer pageSize) {
		// TODO Auto-generated method stub
		return dao.findListByInstall(sysId,beginNum,pageSize);
	}
	
	public List<HashMap<String, Object>> findListByInstallSecrch(String sysId, Integer beginNum, Integer pageSize,String search) {
		// TODO Auto-generated method stub
		return dao.findListByInstallSecrch(sysId,beginNum,pageSize,search);
	}

}