/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.SysUser;

/**
 * 用户DAO接口
 * @author LD
 * @version 2017-04-15
 */
@MyBatisDao
public interface SysUserDao extends CrudDao<SysUser> {

	
}