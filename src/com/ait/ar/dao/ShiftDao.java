package com.ait.ar.dao;

import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ShiftDao.java
 * @Description: implement Class ShiftDaoImpl.java
 * @Create date: 2012-1-9 下午02:54:44
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ShiftDao {
	
	@SuppressWarnings("unchecked")
	public Object getShift(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getShiftList(Object object);
	@SuppressWarnings("unchecked")
	public List getWorkTimeLsit(Object object);
	@SuppressWarnings("unchecked")
	public List getDateTypeLsit(Object object);
	@SuppressWarnings("unchecked")
	public List getWorkTimeLsit1(Object object);
	@SuppressWarnings("unchecked")
	public List getWorkTimeLsit2(Object object);
	
	@SuppressWarnings("unchecked")
	public int getShiftCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getShiftList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public void addShiftInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int updateShiftInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public void deleteShiftInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getShiftParameterList(Object object);
	
	@SuppressWarnings("unchecked")
	public int getShiftNewSeq(Object object);
	
	@SuppressWarnings("unchecked")
	public int checkShiftInfo(Object object) ;

	public List getItemList(Map paramMap);
	
	public List calculateAvg(Map paramMap);

	
	@SuppressWarnings("unchecked")
	public void updateShiftInfoByNO(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public String getDeptDistinguishNo(Object object);
}
