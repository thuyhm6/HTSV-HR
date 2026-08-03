package com.ait.ess.bean;

import java.util.Date;

import com.ait.Interface.ParentBean;

public class WageApplicationDecide extends ParentBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9018332793346519485L;
	/** 员工ID */
	private EssEmployee appEmp;
	/** 审批状态 code id */
	private Object state;
	/** 审批结果 */
	private Object result;
	/** 申请标题 */
	private String title;
	/** 申请日期 */
	private Date appTime;
	private Integer orderno;
	private String language;
	public EssEmployee getAppEmp() {
		return appEmp;
	}
	public void setAppEmp(EssEmployee appEmp) {
		this.appEmp = appEmp;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getAppTime() {
		return appTime;
	}
	public void setAppTime(Date appTime) {
		this.appTime = appTime;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLanguage() {
		return language;
	}
	public Object getState() {
		return state;
	}
	public void setState(Object state) {
		this.state = state;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Integer getOrderno() {
		return orderno;
	}
	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}


}
