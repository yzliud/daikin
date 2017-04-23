/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daikin.entity;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 类别Entity
 * @author LD
 * @version 2017-04-20
 */
public class SysDict extends DataEntity<SysDict> {
	
	private static final long serialVersionUID = 1L;
	private String value;		// 数据值
	private String label;		// 标签名
	private String type;		// 类型
	private String description;		// 描述
	private Integer sort;		// 排序（升序）
	private SysDict parent;		// 父级编号
	
	public SysDict() {
		super();
	}

	public SysDict(String id){
		super(id);
	}

	@ExcelField(title="数据值", align=2, sort=1)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@ExcelField(title="标签名", align=2, sort=2)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@ExcelField(title="类型", align=2, sort=3)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="描述", align=2, sort=4)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@NotNull(message="排序（升序）不能为空")
	@ExcelField(title="排序（升序）", align=2, sort=5)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@JsonBackReference
	@ExcelField(title="父级编号", align=2, sort=6)
	public SysDict getParent() {
		return parent;
	}

	public void setParent(SysDict parent) {
		this.parent = parent;
	}
	
}