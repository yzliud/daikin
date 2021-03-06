/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.api.dao;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkContract;

/**
 * 合同DAO接口
 * @author LD
 * @version 2017-04-09
 */
@MyBatisDao
public interface ContractDao extends CrudDao<DkContract> {

	public List<HashMap<String, Object>> findListByMobileOrContract(String mobile,int beginNum,int pageSize);

	public List<HashMap<String, Object>> findListByInstall(String sysId, Integer beginNum, Integer pageSize);
	
	public List<HashMap<String, Object>> findListByInstallSecrch(String sysId, Integer beginNum, Integer pageSize,String search);
}