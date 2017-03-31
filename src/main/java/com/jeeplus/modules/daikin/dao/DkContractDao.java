/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.entity.DkMember;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkContract;

/**
 * 合同DAO接口
 * @author LD
 * @version 2017-03-31
 */
@MyBatisDao
public interface DkContractDao extends CrudDao<DkContract> {

	public List<DkQuotation> findListBydkQuotation(DkQuotation dkQuotation);
	public List<DkMember> findListBydkMember(DkMember dkMember);
	
}