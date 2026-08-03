package com.ait.ar.bean;

public class ArDetail {
	private String empID;

	private String empName;

	private String empPinyin;
	
	private String deptName;

	private String itemName;
	  
	private String positionName;
	
	private String itemShortName;

	private String shiftName;
	
	private String shiftShortName;
	
	private String shiftShortEnName;

	private Integer itemNo;

	private Integer shiftNo;

	private Integer pkNo;

	private String fromTime;

	private String toTime;

	private String ar_date_str;
	
	private Integer date_day;
	
	private Double quantity;

	private String isLock;

	private String unit;
	
	private String unitName;

	private String ar_month_str;

	private Integer date_type;
	
	private String remarks;
	// daily status 0:unlock 1:lock
	private Integer status;
	
	private String personID;

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpPinyin() {
		return empPinyin;
	}

	public void setEmpPinyin(String empPinyin) {
		this.empPinyin = empPinyin;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getItemShortName() {
		return itemShortName;
	}

	public void setItemShortName(String itemShortName) {
		this.itemShortName = itemShortName;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public String getShiftShortName() {
		return shiftShortName;
	}

	public void setShiftShortName(String shiftShortName) {
		this.shiftShortName = shiftShortName;
	}

	public String getShiftShortEnName() {
		return shiftShortEnName;
	}

	public void setShiftShortEnName(String shiftShortEnName) {
		this.shiftShortEnName = shiftShortEnName;
	}

	public Integer getItemNo() {
		return itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public Integer getShiftNo() {
		return shiftNo;
	}

	public void setShiftNo(Integer shiftNo) {
		this.shiftNo = shiftNo;
	}

	public Integer getPkNo() {
		return pkNo;
	}

	public void setPkNo(Integer pkNo) {
		this.pkNo = pkNo;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getAr_date_str() {
		return ar_date_str;
	}

	public void setAr_date_str(String arDateStr) {
		ar_date_str = arDateStr;
	}

	public Integer getDate_day() {
		return date_day;
	}

	public void setDate_day(Integer dateDay) {
		date_day = dateDay;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAr_month_str() {
		return ar_month_str;
	}

	public void setAr_month_str(String arMonthStr) {
		ar_month_str = arMonthStr;
	}

	public Integer getDate_type() {
		return date_type;
	}

	public void setDate_type(Integer dateType) {
		date_type = dateType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public ArDetail() {
	}

}
