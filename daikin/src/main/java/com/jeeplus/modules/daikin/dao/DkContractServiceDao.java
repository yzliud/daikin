/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.modules.daikin.entity.DkContract;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkContractService;

/**
 * 维保记录DAO接口
 * @author LD
 * @version 2017-05-06
 */
@MyBatisDao
public interface DkContractServiceDao extends CrudDao<DkContractService> {

	public List<DkContract> findListBydkContract(DkContract dkContract);
	
	public void checkService(DkContractService DkContractService);
	
}