/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.daikin.entity.DkMember;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.dao.DkQuotationDao;

/**
 * 报价单Service
 * @author LD
 * @version 2017-03-31
 */
@Service
@Transactional(readOnly = true)
public class DkQuotationService extends CrudService<DkQuotationDao, DkQuotation> {

	public DkQuotation get(String id) {
		return super.get(id);
	}
	
	public List<DkQuotation> findList(DkQuotation dkQuotation) {
		return super.findList(dkQuotation);
	}
	
	public Page<DkQuotation> findPage(Page<DkQuotation> page, DkQuotation dkQuotation) {
		return super.findPage(page, dkQuotation);
	}
	
	@Transactional(readOnly = false)
	public void save(DkQuotation dkQuotation) {
		super.save(dkQuotation);
	}
	
	@Transactional(readOnly = false)
	public void delete(DkQuotation dkQuotation) {
		super.delete(dkQuotation);
	}
	
	public Page<DkMember> findPageBydkMember(Page<DkMember> page, DkMember dkMember) {
		dkMember.setPage(page);
		page.setList(dao.findListBydkMember(dkMember));
		return page;
	}
	
	
	
}