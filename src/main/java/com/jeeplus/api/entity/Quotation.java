package com.jeeplus.api.entity;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

public class Quotation {
	private String id;
	private String name;
	private String member_name;
	private String mobile;
	private String address;
	private String member_id;
	private Double total_fee;
	private String product_type;
	private String sale_by;
	private String connection_ratio;
	private String review_status;
	private String review_by;
	private String is_review;
	private String remark;
	private String create_by;
	private Date create_date;
	private String update_by;
	private Date update_date;
	private String del_flag;
	private List<QuotationProduct> quotationProductList = Lists.newArrayList();		// 子表列表
	
	
	public List<QuotationProduct> getQuotationProductList() {
		return quotationProductList;
	}
	public void setQuotationProductList(List<QuotationProduct> quotationProductList) {
		this.quotationProductList = quotationProductList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public Double getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public String getSale_by() {
		return sale_by;
	}
	public void setSale_by(String sale_by) {
		this.sale_by = sale_by;
	}
	public String getConnection_ratio() {
		return connection_ratio;
	}
	public void setConnection_ratio(String connection_ratio) {
		this.connection_ratio = connection_ratio;
	}
	public String getReview_status() {
		return review_status;
	}
	public void setReview_status(String review_status) {
		this.review_status = review_status;
	}
	public String getReview_by() {
		return review_by;
	}
	public void setReview_by(String review_by) {
		this.review_by = review_by;
	}
	public String getIs_review() {
		return is_review;
	}
	public void setIs_review(String is_review) {
		this.is_review = is_review;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public String getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	
	

}
