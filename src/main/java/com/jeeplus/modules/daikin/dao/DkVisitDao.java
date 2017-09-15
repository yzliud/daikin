/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkVisit;

/**
 * 上门登记DAO接口
 * @author 菜鸟
 * @version 2017-09-15
 */
@MyBatisDao
public interface DkVisitDao extends CrudDao<DkVisit> {

	
}