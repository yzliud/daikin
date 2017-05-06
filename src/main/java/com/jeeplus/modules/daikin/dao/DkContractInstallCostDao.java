/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.modules.daikin.entity.DkContract;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkContractInstallCost;

/**
 * 安装付款DAO接口
 * @author LD
 * @version 2017-05-06
 */
@MyBatisDao
public interface DkContractInstallCostDao extends CrudDao<DkContractInstallCost> {

	public List<DkContract> findListBydkContract(DkContract dkContract);
	
	public void checkInstallCost(DkContractInstallCost dis);
	
}