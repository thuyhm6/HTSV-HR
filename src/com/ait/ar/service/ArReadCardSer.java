package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArCardRecordSer.java
 * @Description: implement Class ArCardRecordSerImp.java
 * @Create date: 2012-4-18 下午06:03:06
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArReadCardSer {
	@SuppressWarnings("unchecked")
	public List getArCardRecordList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public int getArCardRecordListCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int getCardInterfacedata(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public String readMacRecordBJList(HttpServletRequest request);

	public List getImportExcelTempMacRecordsList(HttpServletRequest request);

	public int getImportExcelTempMacRecordsListCnt(HttpServletRequest request);

	public int getImportExcelTempMacRecordsListErrCnt(HttpServletRequest request);

	public String importMacRecordsExcelExcel(HttpServletRequest request);
	
	public String readMacRecordSSTList(HttpServletRequest request);
	/**
	 * 读取SST打卡数据
	 */
	public String readSSTMacRecordList();
	
	/**
	 * 读取TSTO打卡数据
	 */
	@SuppressWarnings("unchecked")
	public String readTSTOMacRecordList();
	/**
	 * TSTO打卡数据读取 old
	 */
	@SuppressWarnings("unchecked")
	public String readMacRecordOldList(HttpServletRequest request);

	/**
	 * 读取TSTO食堂打卡数据
	 */
	public String readMacRecordBJList(String cpnyId);

	/**
	 * 读取DL打卡数据
	 */
	public String readMacRecordDLList(String cpnyId);

	@SuppressWarnings("unchecked")
	public String readMacRecordDLList(HttpServletRequest request);

	/**
	 * 读取HZ打卡数据
	 */
	public String readMacRecordHZList(String cpnyId);
	
	@SuppressWarnings("unchecked")
	public String readMacRecordHZList(HttpServletRequest request);
}
