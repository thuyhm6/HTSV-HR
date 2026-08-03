package com.ait.sys.bean;

public class JsonSort implements Comparable<JsonSort>{

	private String aliasValue;
	
	private String aliasName;
	
	private String aliasType;
	
	private Integer aliasSort;
	
	private String aliasExpFlag;
	
	private String aliasDataType;

	public JsonSort() {
		super();
	}

	public JsonSort(String aliasValue, String aliasName, String aliasType,
			Integer aliasSort) {
		super();
		this.aliasValue = aliasValue;
		this.aliasName = aliasName;
		this.aliasType = aliasType;
		this.aliasSort = aliasSort;
	}
	

	public JsonSort(String aliasValue, String aliasName, String aliasType,
			Integer aliasSort,String aliasExpFlag) {
		super();
		this.aliasValue = aliasValue;
		this.aliasName = aliasName;
		this.aliasType = aliasType;
		this.aliasSort = aliasSort;
		this.aliasExpFlag = aliasExpFlag;
	}
	

	public JsonSort(String aliasValue, String aliasName, String aliasType,
			Integer aliasSort,String aliasExpFlag,String aliasDataType) {
		super();
		this.aliasValue = aliasValue;
		this.aliasName = aliasName;
		this.aliasType = aliasType;
		this.aliasSort = aliasSort;
		this.aliasExpFlag = aliasExpFlag;
		this.aliasDataType = aliasDataType;
	}

	public String getAliasValue() {
		return aliasValue;
	}

	public void setAliasValue(String aliasValue) {
		this.aliasValue = aliasValue;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getAliasType() {
		return aliasType;
	}

	public void setAliasType(String aliasType) {
		this.aliasType = aliasType;
	}

	public Integer getAliasSort() {
		return aliasSort;
	}

	public void setAliasSort(Integer aliasSort) {
		this.aliasSort = aliasSort;
	}

	public String getAliasExpFlag() {
		return aliasExpFlag;
	}

	public void setAliasExpFlag(String aliasExpFlag) {
		this.aliasExpFlag = aliasExpFlag;
	}

	@Override
	public int compareTo(JsonSort jsonSort) {
		return this.getAliasSort().compareTo(jsonSort.getAliasSort());
	}

	/**
	 * @return the aliasDataType
	 */
	public String getAliasDataType() {
		return aliasDataType;
	}

	/**
	 * @param aliasDataType the aliasDataType to set
	 */
	public void setAliasDataType(String aliasDataType) {
		this.aliasDataType = aliasDataType;
	}
	
}
