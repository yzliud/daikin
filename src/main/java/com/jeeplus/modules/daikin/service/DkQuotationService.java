/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.dao.DkQuotationDao;

import com.jeeplus.modules.daikin.entity.DkMember;

import com.jeeplus.modules.daikin.entity.DkQuotationProduct;
import com.jeeplus.modules.daikin.dao.DkQuotationProductDao;

/**
 * 报价单Service
 * @author LD
 * @version 2017-04-05
 */
@Service
@Transactional(readOnly = true)
public class DkQuotationService extends CrudService<DkQuotationDao, DkQuotation> {

	@Autowired
	private DkQuotationProductDao dkQuotationProductDao;
	
	public DkQuotation get(String id) {
		DkQuotation dkQuotation = super.get(id);
		dkQuotation.setDkQuotationProductList(dkQuotationProductDao.findList(new DkQuotationProduct(dkQuotation)));
		return dkQuotation;
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
		for (DkQuotationProduct dkQuotationProduct : dkQuotation.getDkQuotationProductList()){
			if (dkQuotationProduct.getId() == null){
				continue;
			}
			if (DkQuotationProduct.DEL_FLAG_NORMAL.equals(dkQuotationProduct.getDelFlag())){
				if (StringUtils.isBlank(dkQuotationProduct.getId())){
					dkQuotationProduct.setQuotationId(dkQuotation);
					dkQuotationProduct.preInsert();
					dkQuotationProductDao.insert(dkQuotationProduct);
				}else{
					dkQuotationProduct.preUpdate();
					dkQuotationProductDao.update(dkQuotationProduct);
				}
			}else{
				dkQuotationProductDao.delete(dkQuotationProduct);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(DkQuotation dkQuotation) {
		super.delete(dkQuotation);
		dkQuotationProductDao.delete(new DkQuotationProduct(dkQuotation));
	}
	
	public Page<DkMember> findPageBydkMember(Page<DkMember> page, DkMember dkMember) {
		dkMember.setPage(page);
		page.setList(dao.findListBydkMember(dkMember));
		return page;
	}
	
}