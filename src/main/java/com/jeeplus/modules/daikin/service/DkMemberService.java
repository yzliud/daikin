/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkMember;
import com.jeeplus.modules.daikin.dao.DkMemberDao;

/**
 * 会员Service
 * @author LD
 * @version 2017-04-01
 */
@Service
@Transactional(readOnly = true)
public class DkMemberService extends CrudService<DkMemberDao, DkMember> {

	public DkMember get(String id) {
		return super.get(id);
	}
	
	public List<DkMember> findList(DkMember dkMember) {
		return super.findList(dkMember);
	}
	
	public Page<DkMember> findPage(Page<DkMember> page, DkMember dkMember) {
		return super.findPage(page, dkMember);
	}
	
	@Transactional(readOnly = false)
	public void save(DkMember dkMember) {
		super.save(dkMember);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkMember dkMember) {
		super.delete(dkMember);
	}

	/**
	 * 获取有效客户列表
	 * @param endTime 有效结束时间
	 * @param beginTime 有效开始时间
	 * @return List<DkMember>
	 */
	public Page<DkMember> findEffectivePage(Page<DkMember> page, DkMember d) {
		d.setPage(page);
		page.setList(dao.findEffectiveList(d));
		return page;
	}
	
	public List<DkMember> findEffectiveList(DkMember d) {
		return dao.findEffectiveList(d);
	}
	
	
	
	
}