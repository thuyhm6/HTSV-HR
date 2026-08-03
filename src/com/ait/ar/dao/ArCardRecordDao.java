package com.ait.ar.dao;

import java.util.List;

public interface ArCardRecordDao {
	
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordForSelfList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordList(Object object,int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordForSelfList(Object object,int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getArCardRecordListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getArCardRecordListForSelfCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public void updateArCardRecordInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void deleteArCardRecordInfo(List list)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public void deleteArCardInfoCommon(List list, String target)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public void addArCardRecordInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public Object getArCardRecordInfo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public void addArShiftChangeInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addArShiftChangeForPreDeleteInfo(List list)throws Exception ;
	public List getArCardRecordDayList(Object obj);
	
	public List getArCardRecordCommon(Object obj, String target);
	
	/**
	 * 取得刷卡数据
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getArCardRecordInfoS(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordMealList(Object object);
}
