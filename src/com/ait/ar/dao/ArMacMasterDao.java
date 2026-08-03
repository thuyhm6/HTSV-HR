package com.ait.ar.dao;

import java.sql.SQLException;
import java.util.List;

import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArMacMasterDao.java
 * @Description: implement Class ArMacMasterDaoImpl.java
 * @Create date: 2013-8-26 下午15:49:39
 * @Create by: lufeng(lufeng@ait.net.cn)
 * @version 5.1
 */
public interface ArMacMasterDao {
	/*刷卡机--人事信息--接口*/
	@SuppressWarnings("unchecked")
	public List getArHrmMasterExcelList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArHrmMasterList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArHrmMasterList(Object object,int currentPage, int pageSize);
	
	public int getArHrmMasterListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArHrmMasterPreExcelList(Object object);
	
	@SuppressWarnings("unchecked")
	public void insertHrmMasterInfo(List list) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public List getArDeptMasterPreExcelList(Object object);
	
	@SuppressWarnings("unchecked")
	public void insertDeptMasterInfo(List list) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public void addArMacLogInfo(Object object) throws SQLException;
	/*刷卡机--刷卡数据信息--接口*/
	@SuppressWarnings("unchecked")
	public List getArCardMacNoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordExcelList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordList(Object object,int currentPage, int pageSize);
	
	public int getArCardRecordListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public void insertCardRecordInfo(List list) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public List getArFingerPrintExcelList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArFingerPrintList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArFingerPrintList(Object object,int currentPage, int pageSize);
	
	public int getArFingerPrintListCnt(Object object);
}
