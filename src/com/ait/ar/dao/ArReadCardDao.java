package com.ait.ar.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ArReadCardDao {
	@SuppressWarnings("unchecked")
	public List getArCardRecordList(Object object);
	@SuppressWarnings("unchecked")
	public List getArCardRecordList(Object object,int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getArCardRecordListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public void insertMacRecordList(List params)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public void insertC12MacRecordList(List params)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public void insertC13MacRecordList(List params)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public String readMacRecordList(LinkedHashMap object)throws Exception ;
	
	
	@SuppressWarnings("unchecked")
	public String readMacRecordEmpIdIdList(LinkedHashMap object)throws Exception ;

	@SuppressWarnings("unchecked")
	public void insertMacRecordListC04(List params)throws Exception ;

	@SuppressWarnings("unchecked")
	public void insertMacRecordListC03(List params)throws Exception ;

	@SuppressWarnings("unchecked")
	public void insertEmpInfoListC03(List params)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public void insertEmpResignListC03(List params)throws Exception ;
	public List getImportExcelTempMacRecordsList(LinkedHashMap paramMap,
			int pageNum, int numPerPage);
	public List getImportExcelTempMacRecordsList(LinkedHashMap paramMap);
	public int getImportExcelTempMacRecordsListCnt(LinkedHashMap paramMap);
	
	public int getImportExcelTempMacRecordsListErrCnt(LinkedHashMap paramMap);
	
	public String importMacRecordsExcelExcel(LinkedHashMap paramMap);	
	
	public int ChenkMacRecordList(LinkedHashMap object) ;
	
	public int insertMacRecordRawList(List params)throws Exception ;
	
	public int insertSSTMacRecordRawList(List object)  throws Exception;
	 
	/**
	 * 读完卡添加日志信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int addSyLogInfo(Object object)  throws Exception;
	public int ChenkMacRecordAutoList(LinkedHashMap paramMap);

	public int updateMacArDateStr();

	/**
	 * 读取打卡记录
	 */
	public int readMacRecordList(List object, String target);
}
