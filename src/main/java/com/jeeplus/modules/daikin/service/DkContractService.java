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
import com.jeeplus.modules.Consts;
import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.modules.daikin.dao.DkContractDao;
import com.jeeplus.modules.daikin.entity.DkQuotation;
import com.jeeplus.modules.daikin.entity.DkMember;
import com.jeeplus.modules.daikin.entity.DkContractProduct;
import com.jeeplus.modules.daikin.dao.DkContractProductDao;

/**
 * 合同Service
 * @author LD
 * @version 2017-04-09
 */
@Service
@Transactional(readOnly = true)
public class DkContractService extends CrudService<DkContractDao, DkContract> {

	@Autowired
	private DkContractProductDao dkContractProductDao;
	
	public DkContract get(String id) {
		DkContract dkContract = super.get(id);
		dkContract.setDkContractProductList(dkContractProductDao.findList(new DkContractProduct(dkContract)));
		return dkContract;
	}
	
	public List<DkContract> findList(DkContract dkContract) {
		return super.findList(dkContract);
	}
	
	public Page<DkContract> findPage(Page<DkContract> page, DkContract dkContract) {
		return super.findPage(page, dkContract);
	}
	
	@Transactional(readOnly = false)
	public void save(DkContract dkContract) {
		super.save(dkContract);
		for (DkContractProduct dkContractProduct : dkContract.getDkContractProductList()){
			if (dkContractProduct.getId() == null){
				continue;
			}
			if (DkContractProduct.DEL_FLAG_NORMAL.equals(dkContractProduct.getDelFlag())){
				if (StringUtils.isBlank(dkContractProduct.getId())){
					dkContractProduct.setContractId(dkContract.getId());
					dkContractProduct.preInsert();
					dkContractProductDao.insert(dkContractProduct);
				}else{
					dkContractProduct.preUpdate();
					dkContractProductDao.update(dkContractProduct);
				}
			}else{
				dkContractProductDao.delete(dkContractProduct);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(DkContract dkContract) {
		super.delete(dkContract);
		dkContractProductDao.delete(new DkContractProduct(dkContract));
	}
	
	public Page<DkContract> findPageByparent(Page<DkContract> page, DkContract parent) {
		parent.setPage(page);
		page.setList(dao.findListByparent(parent));
		return page;
	}
	public Page<DkQuotation> findPageBydkQuotation(Page<DkQuotation> page, DkQuotation dkQuotation) {
		dkQuotation.setPage(page);
		page.setList(dao.findListBydkQuotation(dkQuotation));
		return page;
	}
	public Page<DkMember> findPageBydkMember(Page<DkMember> page, DkMember dkMember) {
		dkMember.setPage(page);
		page.setList(dao.findListBydkMember(dkMember));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void add(DkContract dkContract) {
		dkContract.setReviewStatus(Consts.ReviewStatus_0);
		dkContract.preInsert();
		dao.add(dkContract);
		dkContractProductDao.add(dkContract);
	}
	
}