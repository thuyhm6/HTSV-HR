package com.ait.ar.dao;

import java.util.List;

import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAnnualLeaveDao.java
 * @Description: implement Class ArAnnualStandardDaoImpl.java
 * @Create date: 2012-2-14 下午12:58:31
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArAnnualStandardDao {
	
	@SuppressWarnings("unchecked")
	public Object getArAnnualStandardInfo(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getArAnnualStandardList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getArAnnualMonthList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getArAnnualStandardList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getArAnnualStandardCnt(Object object) ;
	
	@SuppressWarnings("unchecked")
	public void deleteArAnnualStandard(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateArAnnualStandard(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addArAnnualStandard(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int checkAddArAnnualStandardInfo(Object object) ;

}
