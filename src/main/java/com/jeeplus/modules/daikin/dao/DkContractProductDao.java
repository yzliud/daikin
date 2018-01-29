/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.modules.daikin.entity.DkContract;
import com.jeeplus.modules.daikin.entity.DkProduct;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkContractProduct;

/**
 * 合同商品DAO接口
 * @author LD
 * @version 2017-04-16
 */
@MyBatisDao
public interface DkContractProductDao extends CrudDao<DkContractProduct> {

	public List<DkContract> findListBydkContract(DkContract dkContract);
	public List<DkProduct> findListBydkProduct(DkProduct dkProduct);
	public void add(DkContract dkContract);
	public void updateStockOut(DkContractProduct dkContractProduct);
	
}