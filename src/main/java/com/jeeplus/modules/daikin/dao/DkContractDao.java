/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.entity.DkMember;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

/**
 * 合同DAO接口
 * @author LD
 * @version 2017-04-09
 */
@MyBatisDao
public interface DkContractDao extends CrudDao<DkContract> {

	public List<DkContract> findListByparent(DkContract parent);
	public List<DkQuotation> findListBydkQuotation(DkQuotation dkQuotation);
	public List<DkMember> findListBydkMember(DkMember dkMember);
	public void add(DkContract dkContract);
	public DkContract getSingle(DkContract dkContract);
	public void reviewContract(DkContract dkContract);
	public void updateContractPay(DkContract dkContract);
	public void assignInstall(DkContract dkContract);
	public void updateContractTotalFee(DkContract dkContract);
	public void updateContractCostFee(DkContract dkContract);
}