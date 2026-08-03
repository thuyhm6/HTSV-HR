package com.ait.ar.dao;

import java.util.List;

import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArMonthDao.java
 * @Description: implement Class ArMonthDaoImpl.java
 * @Create date: 2012-2-11 下午03:10:36
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArMonthDao {
	
	@SuppressWarnings("unchecked")
	public List getArColumns(Object object);
	@SuppressWarnings("unchecked")
	public List getArColumnsYN(Object object);
	@SuppressWarnings("unchecked")
	public List getArMonthList(Object object);
	@SuppressWarnings("unchecked")
	public List getArMonthEssList(Object obj);
	@SuppressWarnings("unchecked")
	public List getArMonthList(Object object,int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public int getArMonthListCnt(Object object);
	@SuppressWarnings("unchecked")
	public List getArColumnsParam(Object object);
	@SuppressWarnings("unchecked")
	public List retrieveMonthlyStatusList(Object object);
	@SuppressWarnings("unchecked")
	public int updateArMonthInfo(Object object);
	@SuppressWarnings("unchecked")
	public List getMonthlyStatusList(Object object);
	@SuppressWarnings("unchecked")
	public Object getLastDayByMonth(Object object);
}
