package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import bsh.ParseException;

import com.ait.ar.dao.ArReadCardDao;
import com.ait.web.util.SqlMapClientSupport;
import java.util.Date;
import java.text.SimpleDateFormat;

@Repository
public class ArReadCardDaoImpl extends SqlMapClientSupport implements
		ArReadCardDao {

	@SuppressWarnings("unchecked")
	public List getArCardRecordList(Object object) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();

		returnList = this.getArCardRecordList(object, -1, -1);

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getArCardRecordList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.arReadCard.getArCardRecordList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ar.arReadCard.getArCardRecordList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public int getArCardRecordListCnt(Object object) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.arReadCard.getArCardRecordListCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * C11-----插入刷卡数据(insert MacRecord List)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void insertMacRecordList(List params)throws Exception {
		
		this.deleteForList("ar.arReadCard.deleteMacRecordList", params) ;
		this.insertForList("ar.arReadCard.insertMacRecordList", params) ;
		//this.delete("ar.arReadCard.deleteMacRecordTempC11", params) ;
		//this.insertForList("ar.arReadCard.insertMacRecordListTemp", params) ;
		//调用存储更新IN OUT(2013-10-25 修改接口，传过来的数据自带IN、OUT标志，不需再自动更新)
		//this.insert("ar.arReadCard.updateC11inout") ;
	}
	
	/**
	 * C12-----插入刷卡数据(insert MacRecord List)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void insertC12MacRecordList(List params)throws Exception {
		this.deleteForList("ar.arReadCard.deleteMacRecordListC12", params) ;
		this.insertForList("ar.arReadCard.insertMacRecordListC12", params) ;
		//this.delete("ar.arReadCard.deleteMacRecordTempC12", params) ;
		//this.insertForList("ar.arReadCard.insertMacRecordListTempC12", params) ;
		//调用存储更新IN OUT(2013-10-25 修改接口，传过来的数据自带IN、OUT标志，不需再自动更新)
		//this.insert("ar.arReadCard.updateC12inout") ;
	}
	
	/**
	 * C13-----插入刷卡数据(insert MacRecord List)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void insertC13MacRecordList(List params)throws Exception {
		this.deleteForList("ar.arReadCard.deleteMacRecordListC13", params) ;
		this.insertForList("ar.arReadCard.insertMacRecordListC13", params) ;
		//this.delete("ar.arReadCard.deleteMacRecordTempC13", params) ;
		//this.insertForList("ar.arReadCard.insertMacRecordListTempC13", params) ;
		//调用存储更新IN OUT(2013-10-25 修改接口，传过来的数据自带IN、OUT标志，不需再自动更新)
		//this.insert("ar.arReadCard.updateC13inout") ;
	}
	
	/**
	 * 插入刷卡数据C04(insert MacRecord List)
	 * @param request
	 * @return
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public void insertMacRecordListC04(List params)throws Exception {	
		//插入临时表
		this.insertForList("ar.arReadCard.insertMacRecordListC04", params) ;
	}	
	
	/**
	 * 更新刷卡数据C04，从临时表插入正式库(insert MacRecord List)
	 * @param request
	 * @return
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public void updateMacRecordListC04(Map param)throws Exception {	
		//插入临时表
		this.insert("ar.arReadCard.updatePersonIdAndInsert", param) ;
	}	
	
	/**
	 * 插入刷卡数据C03(insert MacRecord List)
	 * @param request
	 * @return
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public void insertMacRecordListC03(List params)throws Exception {	
		//清空临时表
		this.delete("ar.arReadCard.delMacRecordTempC03");
		//插入临时表
		this.insertForList("ar.arReadCard.insertMacRecordListC03", params);
		//调用存储过程AR_ADD_MAC_RECORDS_C03_P，
		//插入 HR_MAC_RECORDS
		this.insert("ar.arReadCard.insertMacRecordsC03ByP", params);				
	}	 	
	
	/**
	 * 插入TSMS人员数据C03(insert MacRecord List)
	 * @param request
	 * @return
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public void insertEmpInfoListC03(List params)throws Exception {	
		//清空临时表 HR_TSMS_ENTRY_C03
		this.delete("ar.arReadCard.delHrTsmsEntryC03");		
		//插入临时表 HR_TSMS_ENTRY_C03
		this.insertForList("ar.arReadCard.insertHrTsmsEntryC03", params);
		//this.insertForList("ar.arReadCard.insertHrTsmsEntryC03BEIFEN", params);
		//调用存储过程HR_TSMS_ENTRY_C03_P，
		//插入 HR_EMPLOYEE,HR_PERSONAL_INFO,HR_EXPERIENCE_INSIDE,HR_CHANGE_STATUS
		this.insert("ar.arReadCard.updateEmpInfosByP", params);				
	}		
		
	/**
	 * 插入TSMS人员离职数据C03
	 * @param request
	 * @return
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public void insertEmpResignListC03(List params)throws Exception {	
		LinkedHashMap map = (LinkedHashMap)params.get(0);
		//清空临时表 HR_TSMS_RESIGNATION_C03
		this.delete("ar.arReadCard.deleteDuplicateRecordCompanyList", map);		
		//插入临时表 HR_TSMS_RESIGNATION_C03
		//this.insertForList("ar.arReadCard.insertEmpResignListC03", params);
		//调用存储过程HR_TSMS_RESIGNATION_C03_P，
		//插入 HR_RESIGNATION,HR_CHANGE_STATUS
		//更新HR_EMPLOYEE
		//this.insert("ar.arReadCard.hrTsmsResignationC03P", params);				
	}
	 
	
	 
	 

	@SuppressWarnings("unchecked")
	public String readMacRecordList(LinkedHashMap paramMap) throws Exception {
		String returnString = "";
		try {
			this.insert("ar.arReadCard.ar_record_read_supervisor_proc", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("empid"));
		} catch (SQLException e) {
			returnString = e.getMessage();

			e.printStackTrace();
		}

		return returnString;
	}
	@SuppressWarnings("unchecked")
	public String readMacRecordEmpIdIdList(LinkedHashMap paramMap) throws Exception {
		String returnString = "";
		try {
			this.insert("ar.arReadCard.ar_record_read_personid_proc", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("empid"));
		} catch (SQLException e) {
			returnString = e.getMessage();

			e.printStackTrace();
		}

		return returnString;
	}
	/*------------------------------------------------------------*/
	
	@Override
	public List getImportExcelTempMacRecordsList(LinkedHashMap paramMap,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.arReadCard.getImportExcelTempMacRecordsList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.arReadCard.getImportExcelTempMacRecordsList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public List getImportExcelTempMacRecordsList(LinkedHashMap paramMap) {
		return this.getImportExcelTempMacRecordsList(paramMap, -1, -1);
	}
	@Override
	public int getImportExcelTempMacRecordsListCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arReadCard.getImportExcelTempMacRecordsListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	@Override
	public int getImportExcelTempMacRecordsListErrCnt(LinkedHashMap paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arReadCard.getImportExcelTempMacRecordsListErrCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 工资基础项目数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@Override
	public String importMacRecordsExcelExcel(LinkedHashMap paramMap) {
	
			String returnString = "" ;		
			try {
				paramMap.put("message", "") ;
				this.insert("ar.arReadCard.importMacRecordsExcel", paramMap);			
				returnString = ObjectUtils.toString(paramMap.get("message")) ;
			} catch (SQLException e) {	
				returnString = e.getMessage() ;			
				e.printStackTrace();
			}
			return returnString ;
		}
	@Override
	public int ChenkMacRecordList(LinkedHashMap paramMap){
		int returnInt=1;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		 try {
			 df.setLenient(false);
			 df.parse(paramMap.get("FILENAME").toString());
			 
			 String fileDate = paramMap.get("FILENAME").toString();
			 String sDate = paramMap.get("from_date").toString().replace(".", "");
			 String eDate = paramMap.get("to_date").toString().replace(".", "");
			 if(fileDate.compareTo(sDate) == -1){
					returnInt=0;
			 }
			 if(fileDate.compareTo(eDate) == 1){
					returnInt=0;
			 }
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			returnInt=0;
		}
		return returnInt;
	}
	

	public int ChenkMacRecordAutoList(LinkedHashMap paramMap){
		int returnInt=1;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		 try {
			 df.setLenient(false);
			 df.parse(paramMap.get("FILENAME").toString());
			 
		} catch (java.text.ParseException e) {
			returnInt=0;
		}
		return returnInt;
	}
	@Override
	public int insertMacRecordRawList(List object)  throws Exception{
		int result=1;
		try {
			this.insertForList("ar.arReadCard.insertMacRecordRawList", object);
		} catch (SQLException e) {
			e.printStackTrace();
			result=0;
		}
		return result;
	}
	@Override
	public int insertSSTMacRecordRawList(List object)  throws Exception{
		int result=1;
		try {
			this.insertForList("ar.arReadCard.insertMacRecordRawListSST", object);
		} catch (SQLException e) {
			e.printStackTrace();
			result=0;
		}
		return result;
	}

	/**
	 * 读取打卡记录
	 */
	public int readMacRecordList(List object, String target){
		int result=1;
		try {
			this.insertForList("ar.arReadCard." + target, object);
		} catch (Exception e) {
			e.printStackTrace();
			result=0;
		}
		return result;
	}
	
	/**
	 * 读完卡添加日志信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int addSyLogInfo(Object object)  throws Exception{
		int result=1;
		try {
			this.insert("ar.arReadCard.addSyLogInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
			result=0;
		}
		return result;
	}
	
	public int updateMacArDateStr(){
		int result=1;
		try {
			this.update("ar.arReadCard.updateMacArDateStr");
		} catch (SQLException e) {
			e.printStackTrace();
			result=0;
		}
		return result;
	}
}
