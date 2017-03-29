/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkInstallPerson;
import com.jeeplus.modules.daikin.dao.DkInstallPersonDao;

/**
 * 安装人员Service
 * @author LD
 * @version 2017-03-23
 */
@Service
@Transactional(readOnly = true)
public class DkInstallPersonService extends CrudService<DkInstallPersonDao, DkInstallPerson> {

	public DkInstallPerson get(String id) {
		return super.get(id);
	}
	
	public List<DkInstallPerson> findList(DkInstallPerson dkInstallPerson) {
		return super.findList(dkInstallPerson);
	}
	
	public Page<DkInstallPerson> findPage(Page<DkInstallPerson> page, DkInstallPerson dkInstallPerson) {
		return super.findPage(page, dkInstallPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(DkInstallPerson dkInstallPerson) {
		super.save(dkInstallPerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkInstallPerson dkInstallPerson) {
		super.delete(dkInstallPerson);
	}
	
	
	
	
}