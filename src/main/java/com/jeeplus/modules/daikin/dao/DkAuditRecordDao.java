/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkAuditRecord;

/**
 * 审核记录DAO接口
 * @author LD
 * @version 2017-04-09
 */
@MyBatisDao
public interface DkAuditRecordDao extends CrudDao<DkAuditRecord> {

	
}