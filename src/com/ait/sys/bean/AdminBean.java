package com.ait.sys.bean;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

public class AdminBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2034432429142674566L;

	public static final String DEFAULT_LANGUAGE_PREFERENCE = "en";

	public static final String DEFAULT_COUNTRY_PREFERENCE = "US";

	public static final Locale DEFAULT_Locale = new Locale("en", "US");

	private String adminNo;

	private String userNo;

	private String specialParam;

	private String adminID;

	private int adminLevel;

	private String username;

	private String password;

	private String chineseName;

	private String pinyin;

	private String englishName;

	private String postGradeName;

	private String koreanname;

	private String deptNo;

	private String department;

	private String englishdept;

	private String kordept;

	private String screenGrantNo;

	private String createDate;

	private String createdBy;

	private String updateDate;

	private String updatedBy;

	private int activity;
	
	private int isDob;
	
	private String nationalty;
	
	private int personalDataFlag;
	
	private int personalSendDataFlag;
	
	private int checkPassword;
	
	private int orderNo;

	private String languagePreference = DEFAULT_LANGUAGE_PREFERENCE;

	private String countryPreference = DEFAULT_COUNTRY_PREFERENCE;

	private String cpnyId;

	private String cpnyName;

	private String personId;

	private String localName;

	private String content;

	private String language;
	
	private String postFamily;
	
	private String postFamilyName;
	
	private int isBaoXianUser;
	
	
	public String getPostFamily() {
		return postFamily;
	}

	public void setPostFamily(String postFamily) {
		this.postFamily = postFamily;
	}

	public String getIncumbency() {
		return incumbency;
	}

	public void setIncumbency(String incumbency) {
		this.incumbency = incumbency;
	}

	public String getDatelastup() {
		return datelastup;
	}

	public void setDatelastup(String datelastup) {
		this.datelastup = datelastup;
	}

	public String getContractenddate() {
		return contractenddate;
	}

	public void setContractenddate(String contractenddate) {
		this.contractenddate = contractenddate;
	}

	public String getContractstartdate() {
		return contractstartdate;
	}

	public void setContractstartdate(String contractstartdate) {
		this.contractstartdate = contractstartdate;
	}

	private String incumbency;
	private String datelastup;
	private String contractenddate;
	private String contractstartdate;
	private String photo_path;
	
	
	
	public String getPhoto_path() {
		return photo_path;
	}

	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}

	@SuppressWarnings("unchecked")
	private Map limitsMap;

	private String empID;

	private String statNo;

	private String operationCodeNo;

	private String payAreaCd;

	private String payAreaNm;

	private String adminIP;
	
	private String dutyNo;
	
	private String dutyNoName;

	private String headDepartment;
	
	private String positionNoName;
	
	private String dateSrarted;

	public String getDutyNo() {
		return dutyNo;
	}

	public void setDutyNo(String dutyNo) {
		this.dutyNo = dutyNo;
	}

	public String getDutyNoName() {
		return dutyNoName;
	}

	public void setDutyNoName(String dutyNoName) {
		this.dutyNoName = dutyNoName;
	}

	public String getHeadDepartment() {
		return headDepartment;
	}

	public void setHeadDepartment(String headDepartment) {
		this.headDepartment = headDepartment;
	}

	public String getPositionNoName() {
		return positionNoName;
	}

	public void setPositionNoName(String positionNoName) {
		this.positionNoName = positionNoName;
	}



	public String getDateSrarted() {
		return dateSrarted;
	}

	public void setDateSrarted(String dateSrarted) {
		this.dateSrarted = dateSrarted;
	}

	public String getOperationCodeNo() {
		return operationCodeNo;
	}

	public void setOperationCodeNo(String operationCodeNo) {
		this.operationCodeNo = operationCodeNo;
	}

	public String getStatNo() {
		return statNo;
	}

	public void setStatNo(String statNo) {
		this.statNo = statNo;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	@SuppressWarnings("unchecked")
	public Map getLimitsMap() {
		return limitsMap;
	}

	@SuppressWarnings("unchecked")
	public void setLimitsMap(Map limitsMap) {
		this.limitsMap = limitsMap;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public AdminBean() {
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}

	public String getAdminID() {
		return this.adminID;
	}

	public void setAdminLevel(int adminLevel) {
		this.adminLevel = adminLevel;
	}

	public int getAdminLevel() {
		return this.adminLevel;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getScreenGrantNo() {
		return screenGrantNo;
	}

	public void setScreenGrantNo(String screenGrantNo) {
		this.screenGrantNo = screenGrantNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public int getActivity() {
		return activity;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCountryPreference() {
		return countryPreference;
	}

	public void setCountryPreference(String countryPreference) {
		this.countryPreference = countryPreference;
	}

	public String getLanguagePreference() {
		return languagePreference;
	}

	public void setLanguagePreference(String languagePreference) {
		this.languagePreference = languagePreference;
	}

	public Locale getLocale() {
		return new Locale(this.getLanguagePreference(), this
				.getCountryPreference());
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getKoreanname() {
		return koreanname;
	}

	public void setKoreanname(String koreanname) {
		this.koreanname = koreanname;
	}

	public String getEnglishdept() {
		return englishdept;
	}

	public void setEnglishdept(String englishdept) {
		this.englishdept = englishdept;
	}

	public String getKordept() {
		return kordept;
	}

	public void setKordept(String kordept) {
		this.kordept = kordept;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getAdminNo() {
		return adminNo;
	}

	public void setAdminNo(String adminNo) {
		this.adminNo = adminNo;
	}

	public String getCpnyId() {
		return cpnyId;
	}

	public void setCpnyId(String cpnyId) {
		this.cpnyId = cpnyId;
	}

	public String getCpnyName() {
		return cpnyName;
	}

	public void setCpnyName(String cpnyName) {
		this.cpnyName = cpnyName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getSpecialParam() {
		return specialParam;
	}

	public void setSpecialParam(String specialParam) {
		this.specialParam = specialParam;
	}

	public String getPayAreaCd() {
		return payAreaCd;
	}

	public void setPayAreaCd(String payAreaCd) {
		this.payAreaCd = payAreaCd;
	}

	public String getPayAreaNm() {
		return payAreaNm;
	}

	public void setPayAreaNm(String payAreaNm) {
		this.payAreaNm = payAreaNm;
	}

	public String getAdminIP() {
		return adminIP;
	}

	public void setAdminIP(String adminIP) {
		this.adminIP = adminIP;
	}

	public String getPostGradeName() {
		return postGradeName;
	}

	public void setPostGradeName(String postGradeName) {
		this.postGradeName = postGradeName;
	}

	public int getIsBaoXianUser() {
		return isBaoXianUser;
	}

	public void setIsBaoXianUser(int isBaoXianUser) {
		this.isBaoXianUser = isBaoXianUser;
	}

	public int getIsDob() {
		return isDob;
	}

	public void setIsDob(int isDob) {
		this.isDob = isDob;
	}

	public void setPostFamilyName(String postFamilyName) {
		this.postFamilyName = postFamilyName;
	}

	public String getPostFamilyName() {
		return postFamilyName;
	}
	
	public String getNationalty() {
		return nationalty;
	}

	public void setNationalty(String nationalty) {
		this.nationalty = nationalty;
	}

	public int getPersonalDataFlag() {
		return personalDataFlag;
	}

	public void setPersonalDataFlag(int personalDataFlag) {
		this.personalDataFlag = personalDataFlag;
	}
	
	public int getPersonalSendDataFlag() {
		return personalSendDataFlag;
	}

	public void setPersonalSendDataFlag(int personalSendDataFlag) {
		this.personalSendDataFlag = personalSendDataFlag;
	}

	public int getCheckPassword() {
		return checkPassword;
	}

	public void setCheckPassword(int checkPassword) {
		this.checkPassword = checkPassword;
	}

}
