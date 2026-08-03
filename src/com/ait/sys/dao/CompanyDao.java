package com.ait.sys.dao;

import java.util.List;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName CompanyDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:22:19
 * @version 5.0
 *
 */
public interface CompanyDao {
	
	public Object getCompany(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getCompanyList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getCompanyItemAllList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getEmpInfoLxjList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaInfoLxjList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaInfoLxjLgechList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaInfoLxjLgetaList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getYearInfoLxjList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getpayDetilInfoLxjList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getpayDetilInfoLxjLgechList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getpayDetilInfoLxjLgetaList(Object object);
	
	
	@SuppressWarnings("unchecked")
	public List getotherPayInfoLxjList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getotherPayInfoLxjLgechList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getotherPayInfoLxjLgetaList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getwelfarePayInfoLxjList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getwelfarePayInfoLxjLgechList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getwelfarePayInfoLxjLgetaList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getadministrationPayInfoLxjList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getadministrationPayInfoLxjLgechList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getadministrationPayInfoLxjLgetaList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getpaManuallyInfoLxjList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getpaManuallyInfoLxjLgechList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getpaManuallyInfoLxjLgetaList(Object object);
	
	public int getCompanyListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public String getRoleID(Object object);
	
	@SuppressWarnings("unchecked")
	public List getCompanyList(Object object, int currentPage, int pageSize);
	
	public void addCompanyInfo(Object object) throws Exception;
	
	public void updateCompanyInfo(Object object) throws Exception;
	
	public void deleteCompanyInfo(Object object) throws Exception;
	
	public int checkCompanyIdExsit(Object object);
	
	@SuppressWarnings("unchecked")
	public List getHrOpeationList(Object obj);
	public List getCompanyBouns(Object obj);
	
	public List getCompanyYuti(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getCompanyItemAllListHome(Object obj);
}
