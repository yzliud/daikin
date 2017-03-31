/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 微信用户Entity
 * @author LD
 * @version 2017-03-31
 */
public class DkWorker extends DataEntity<DkWorker> {
	
	private static final long serialVersionUID = 1L;
	private String sysUserId;		// 用户
	private String mobile;		// 手机号
	private String name;		// 姓名
	private String openId;		// 微信唯一标识
	private String nickName;		// 昵称
	private String sex;		// 性别
	private String country;		// 国家
	private String province;		// 省
	private String city;		// 城市
	private String headImg2;		// 头像
	
	public DkWorker() {
		super();
	}

	public DkWorker(String id){
		super(id);
	}

	@ExcelField(title="用户", align=2, sort=1)
	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}
	
	@ExcelField(title="手机号", align=2, sort=2)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="姓名", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="微信唯一标识", align=2, sort=4)
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@ExcelField(title="昵称", align=2, sort=5)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@ExcelField(title="性别", align=2, sort=6)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@ExcelField(title="国家", align=2, sort=7)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@ExcelField(title="省", align=2, sort=8)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@ExcelField(title="城市", align=2, sort=9)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@ExcelField(title="头像", align=2, sort=10)
	public String getHeadImg2() {
		return headImg2;
	}

	public void setHeadImg2(String headImg2) {
		this.headImg2 = headImg2;
	}
	
}