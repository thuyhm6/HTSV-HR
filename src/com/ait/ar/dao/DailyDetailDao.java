package com.ait.ar.dao;

import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: DailyDetailDao.java
 * @Description: implement Class DailyDetailDaoImpl.java
 * @Create date: 2012-2-6 上午10:52:48
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface DailyDetailDao {
   
	// EmpCalendarDaoImpl
	@SuppressWarnings("unchecked")
	public List getDailyDetailList(Object object);
	@SuppressWarnings("unchecked")
	public List getShiftList(Object object);
	@SuppressWarnings("unchecked")
	public int deleteDailyDetailInfo(Object object);
	@SuppressWarnings("unchecked")
	public int getDailyDetailInfo(Object object);
	@SuppressWarnings("unchecked")
	public void insertDailyDetailInfo(List list) throws Exception;
	@SuppressWarnings("unchecked")
	public Object getEmpInfo(Object object);
	@SuppressWarnings("unchecked")
	public String getShiftName(Object object);
	@SuppressWarnings("unchecked")
	public List getDailyDetailPersonList(Map paramMap, int pageNum, int numPerPage);
	@SuppressWarnings("unchecked")
	public int getDailyDetailPersonCnt(Map paramMap);
	@SuppressWarnings("unchecked")
	public List getLeaveList(Object object);
}
