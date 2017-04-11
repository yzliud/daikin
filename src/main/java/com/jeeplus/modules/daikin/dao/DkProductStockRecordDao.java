/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.dao;

import com.jeeplus.modules.daikin.entity.DkProduct;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.daikin.entity.DkProductStockRecord;

/**
 * 商品进销存DAO接口
 * @author LD
 * @version 2017-04-05
 */
@MyBatisDao
public interface DkProductStockRecordDao extends CrudDao<DkProductStockRecord> {

	public List<DkProduct> findListBydkProduct(DkProduct dkProduct);

}