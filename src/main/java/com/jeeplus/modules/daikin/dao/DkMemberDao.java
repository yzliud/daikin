/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkMember;

/**
 * 会员DAO接口
 * @author LD
 * @version 2017-04-01
 */
@MyBatisDao
public interface DkMemberDao extends CrudDao<DkMember> {

	List<DkMember> findEffectiveList(String beginTime, String endTime);

	
}