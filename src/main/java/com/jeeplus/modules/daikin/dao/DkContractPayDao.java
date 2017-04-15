/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.modules.daikin.entity.DkContract;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkContractPay;

/**
 * 合同到款DAO接口
 * @author LD
 * @version 2017-04-13
 */
@MyBatisDao
public interface DkContractPayDao extends CrudDao<DkContractPay> {

	public List<DkContract> findListBydkContract(DkContract dkContract);
	public void reviewContractPay(DkContractPay dkContractPay);
}