/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.daikin.entity.SysDict;
import com.jeeplus.modules.daikin.dao.SysDictDao;

/**
 * 类别Service
 * @author LD
 * @version 2017-04-20
 */
@Service
@Transactional(readOnly = true)
public class SysDictService extends CrudService<SysDictDao, SysDict> {

	public SysDict get(String id) {
		return super.get(id);
	}
	
	public List<SysDict> findList(SysDict sysDict) {
		return super.findList(sysDict);
	}
	
	public Page<SysDict> findPage(Page<SysDict> page, SysDict sysDict) {
		return super.findPage(page, sysDict);
	}
	
	@Transactional(readOnly = false)
	public void save(SysDict sysDict) {
		if (sysDict.getIsNewRecord()){
			sysDict.preInsert();
			sysDict.setValue(IdGen.uuid());
			dao.insert(sysDict);
		}else{
			sysDict.preUpdate();
			dao.update(sysDict);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(SysDict sysDict) {
		super.delete(sysDict);
	}
	
	
	
	
}