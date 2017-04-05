package com.jeeplus.common.utils;

import com.jeeplus.modules.daikin.dao.DkProductDao;
import com.jeeplus.modules.daikin.entity.DkProduct;

public class DkUtil {
	
	private static DkProductDao dkProductDao = SpringContextHolder.getBean(DkProductDao.class);
	
	/**
	 * 导出Excel使用，根据名字转换为id
	 */
	public static DkProduct getByDkProductName(String name){
		DkProduct dp = dkProductDao.getByName(name);
		if(dp != null){
			return dp;
		}else{
			return new DkProduct();
		}
	}

}
