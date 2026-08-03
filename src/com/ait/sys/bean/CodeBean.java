package com.ait.sys.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class CodeBean implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String codeNo ;
	
	private String parentCodeNo ;
	
	private LinkedHashMap<String, String> cpnyMap = new LinkedHashMap<String, String>() ;

	private LinkedHashMap<String, CodeBean> childCodeMap = new LinkedHashMap<String, CodeBean>() ;
	
	private LinkedHashMap<String, String> languageMap = new LinkedHashMap<String, String>() ;

	public LinkedHashMap<String, CodeBean> getChildCodeMap() {
		return childCodeMap;
	}

	public void setChildCodeMap(LinkedHashMap<String, CodeBean> childCodeMap) {
		this.childCodeMap = childCodeMap;
	}

	public String getParentCodeNo() {
		return parentCodeNo;
	}

	public void setParentCodeNo(String parentCodeNo) {
		this.parentCodeNo = parentCodeNo;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public LinkedHashMap<String, String> getCpnyMap() {
		return cpnyMap;
	}

	public void setCpnyMap(LinkedHashMap<String, String> cpnyMap) {
		this.cpnyMap = cpnyMap;
	}

	public LinkedHashMap<String, String> getLanguageMap() {
		return languageMap;
	}

	public void setLanguageMap(LinkedHashMap<String, String> languageMap) {
		this.languageMap = languageMap;
	}
}
