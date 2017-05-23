/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import com.jeeplus.modules.daikin.entity.DkProduct;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品进销存Entity
 * @author LD
 * @version 2017-04-05
 */
public class DkProductStockRecord extends DataEntity<DkProductStockRecord> {
	
	private static final long serialVersionUID = 1L;
	private DkProduct dkProduct;		// 商品ID
	private String flag;		// 标识(0-入库 1-出库)
	private Date operateTime;		// 操作时间
	private Integer amount;		// 数量
	private User tuser;		// 操作者
	private Integer stockAmount;		// 库存数量
	private String contractNum;		// 合同号
	private String remark;		// 备注
	private Date beginOperateTime;		// 开始 操作时间
	private Date endOperateTime;		// 结束 操作时间
	
	public DkProductStockRecord() {
		super();
	}

	public DkProductStockRecord(String id){
		super(id);
	}

	@NotNull(message="商品ID不能为空")
	@ExcelField(title="商品ID", fieldType=DkProduct.class, value="dkProduct.name", align=2, sort=1)
	public DkProduct getDkProduct() {
		return dkProduct;
	}

	public void setDkProduct(DkProduct dkProduct) {
		this.dkProduct = dkProduct;
	}
	
	@ExcelField(title="标识(0-入库 1-出库)", dictType="stock_flag", align=2, sort=2)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="操作时间", align=2, sort=3)
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	@NotNull(message="数量不能为空")
	@ExcelField(title="数量", align=2, sort=4)
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@ExcelField(title="操作者", fieldType=User.class, value="tuser.name", align=2, sort=5)
	public User getTuser() {
		return tuser;
	}

	public void setTuser(User tuser) {
		this.tuser = tuser;
	}
	
	@ExcelField(title="库存数量", align=2, sort=6)
	public Integer getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Integer stockAmount) {
		this.stockAmount = stockAmount;
	}
	
	@ExcelField(title="合同号", align=2, sort=7)
	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	
	@ExcelField(title="备注", align=2, sort=8)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getBeginOperateTime() {
		return beginOperateTime;
	}

	public void setBeginOperateTime(Date beginOperateTime) {
		this.beginOperateTime = beginOperateTime;
	}
	
	public Date getEndOperateTime() {
		return endOperateTime;
	}

	public void setEndOperateTime(Date endOperateTime) {
		this.endOperateTime = endOperateTime;
	}
		
}