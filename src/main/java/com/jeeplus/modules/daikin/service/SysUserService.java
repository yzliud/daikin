/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.SysUser;
import com.jeeplus.modules.daikin.dao.SysUserDao;

/**
 * 用户Service
 * @author LD
 * @version 2017-04-15
 */
@Service
@Transactional(readOnly = true)
public class SysUserService extends CrudService<SysUserDao, SysUser> {

	public SysUser get(String id) {
		return super.get(id);
	}
	
	public List<SysUser> findList(SysUser sysUser) {
		return super.findList(sysUser);
	}
	
	public Page<SysUser> findPage(Page<SysUser> page, SysUser sysUser) {
		return super.findPage(page, sysUser);
	}
	
	@Transactional(readOnly = false)
	public void save(SysUser sysUser) {
		super.save(sysUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysUser sysUser) {
		super.delete(sysUser);
	}
	
	@Transactional(readOnly = false)
	public SysUser findUniqueByProperty(String propertyName, String value) {
		return super.findUniqueByProperty(propertyName, value);
	}
	
	
}