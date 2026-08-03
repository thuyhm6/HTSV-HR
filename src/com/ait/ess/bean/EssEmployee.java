package com.ait.ess.bean;

import com.ait.Interface.ParentBean;

public class EssEmployee extends ParentBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4987657495957385530L;
	/** 员工编号 */
	private String empId;
	/** 英文拼音 CHINESE_PINYIN */
	private String pinyin;
	/** 英文姓名ENGLISH_NAME */
	private String englishName;
	/** 职责NO POSITION_NO */
	private String positionNo;
	/** 职(岗)位NO DUTY_NO */
	private String dutyNo;
	/** 职务NO POST_NO */
	private String postNo;
	/** 员工在职状态ID(C系统) STATUS_CODE */
	private String statusCode;
	/** 员工工作级别（G系统传过来的） STATUS */
	private String status;
	/** 公司ID CPNY_ID */
	private String cpnyId;
	/** 部门序号 DEPTNO */
	private String deptNo;
	/** 本地姓名 LOCAL_NAME */
	private String localName;
	/** ID卡号 ID_CARD_NO */
	private String idCardNo;
	/** 班号 SHIFT_NO */
	private String shiftNo;
	/** 语言,用于读取数据 */
	private String language;
	/** 员工ID */
	private String personId;
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getPositionNo() {
		return positionNo;
	}
	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}
	public String getDutyNo() {
		return dutyNo;
	}
	public void setDutyNo(String dutyNo) {
		this.dutyNo = dutyNo;
	}
	public String getPostNo() {
		return postNo;
	}
	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCpnyId() {
		return cpnyId;
	}
	public void setCpnyId(String cpnyId) {
		this.cpnyId = cpnyId;
	}
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getShiftNo() {
		return shiftNo;
	}
	public void setShiftNo(String shiftNo) {
		this.shiftNo = shiftNo;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getPersonId() {
		return personId;
	}

}
