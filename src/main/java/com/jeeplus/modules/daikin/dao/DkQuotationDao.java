/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.modules.daikin.entity.DkMember;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkQuotation;

/**
 * 报价单DAO接口
 * @author LD
 * @version 2017-04-05
 */
@MyBatisDao
public interface DkQuotationDao extends CrudDao<DkQuotation> {

	public List<DkMember> findListBydkMember(DkMember dkMember);
	public void updateReviewStatus(DkQuotation dkQuotation);
	public DkQuotation getSingle(DkQuotation dkQuotation);
	
}