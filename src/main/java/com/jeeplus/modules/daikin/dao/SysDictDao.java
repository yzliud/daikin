/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.SysDict;

/**
 * 类别DAO接口
 * @author LD
 * @version 2017-04-20
 */
@MyBatisDao
public interface SysDictDao extends CrudDao<SysDict> {

	
}