package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.sys.bean.ReportItem;
import com.ait.sys.dao.AffirmDao;
import com.ait.ar.dao.MonthAttendanceDao;
import com.ait.sys.dao.ViewOptionDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ViewOptionDaoImpl.java
 * @Description:
 * @Create date: 2012-5-8 上午11:42:23
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class MonthAttendanceDaoImpl extends SqlMapClientSupport implements MonthAttendanceDao {

	/**
	 * retrieve report table list
	 * 
	 * @param parameterObject
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List retrieveReportTableList(Object parameterObject) {

		List list = new ArrayList() ;
		
		try {
			list = this.queryForList(
					"sys.commons.retrieveReportTableList", parameterObject);

		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * retrieve report item list
	 * 
	 * @param parameterObject
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List retrieveReportItemList(Object parameterObject) {

		List list = new ArrayList() ;
		
		try {
			list = this.queryForList(
					"sys.commons.retrieveReportItemList", parameterObject);

		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * retrieve report data list
	 * 
	 * @param parameterObject
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List retrieveReportDataList(List itemList,Map parameterObject)
	 	throws Exception {
		int report_type = parameterObject.get("REPORT_TYPE") != null ? Integer.parseInt(parameterObject.get("REPORT_TYPE").toString()) : 0;
		String cpnyID = parameterObject.get("interCpnyID").toString();
		List returnList = new ArrayList();
		Map sMap = null ;
		String sql = parameterObject.get("sql") + "";
		String whereSQL = "" ;
		if(parameterObject.get("KEY") != null){
			whereSQL = " WHERE PERSON_ID IN (SELECT PERSON_ID FROM HR_EMPLOYEE " +
					" WHERE CPNY_ID = '" + cpnyID + "' " +
					" AND (EMPID LIKE '%" + parameterObject.get("KEY") + "%' " +
					" OR LOCAL_NAME LIKE '%" + parameterObject.get("KEY") + "%')" +
					" AND ROWNUM = 1)" ;
		}
		if(parameterObject.get("PERSON_ID") != null){
			whereSQL = " WHERE PERSON_ID = '" + parameterObject.get("PERSON_ID") + "'" ;
		}
		if(parameterObject.get("arMonth") != null){
			if(whereSQL.equals("")){
				whereSQL = " WHERE "+(report_type != 1 ? "AR_MONTH"
						: "PA_MONTH")+" = '" + parameterObject.get("arMonth") + "'" ;
			}else{
				whereSQL += " AND "+(report_type != 1 ? "AR_MONTH"
						: "PA_MONTH")+" = '" + parameterObject.get("arMonth") + "'"  ;
			}
		}
		if(parameterObject.get("paMonth") != null){
			if(whereSQL.equals("")){
				whereSQL = " WHERE "+(report_type != 1 ? "AR_MONTH"
						: "PA_MONTH")+" = '" + parameterObject.get("paMonth") + "'"  ;
			}else{
				whereSQL += " AND "+(report_type != 1 ? "AR_MONTH"
						: "PA_MONTH")+" = '" + parameterObject.get("paMonth") + "'"  ;
			}
		}
		if(parameterObject.get("essMonth") != null){
			if(whereSQL.equals("")){
				whereSQL = " WHERE "+(report_type != 1 ? "AR_MONTH"
						: "PA_MONTH")+" = '" + parameterObject.get("essMonth") + "'"  ;
			}else{
				whereSQL += " AND "+(report_type != 1 ? "AR_MONTH"
						: "PA_MONTH")+" = '" + parameterObject.get("essMonth") + "'"  ;
			}
		}
		
		//ess模块查看考勤信息
		if(parameterObject.get("makeType") != null &&"ar".equals(parameterObject.get("makeType").toString()) &&parameterObject.get("ess")!= null){
			if(whereSQL.equals("")){
				whereSQL = " WHERE "+ (report_type != 1 ? "AR_MONTH"
						: "PA_MONTH") +" in (select t.pa_month_str from pa_progress t where t.STAT_NO = GET_AR_STATNO(PERSON_ID,'"+ cpnyID +"') and t.ATT_MO_LOCK_FLAG = 1 AND t.CPNY_ID = '"+ cpnyID +"') " ;
			}else{
				whereSQL += " AND "+ (report_type != 1 ? "AR_MONTH"
						: "PA_MONTH") +" in(select t.pa_month_str from pa_progress t where t.STAT_NO = GET_AR_STATNO(PERSON_ID,'"+ cpnyID +"') and t.ATT_MO_LOCK_FLAG = 1 AND t.CPNY_ID = '"+ cpnyID +"') " ;
			}
		}
		
		//ess模块查看工资信息
		if(parameterObject.get("makeType") != null &&"pa".equals(parameterObject.get("makeType").toString()) &&parameterObject.get("ess")!= null){
			if(whereSQL.equals("")){
				whereSQL = " WHERE "+ (report_type != 1 ? "AR_MONTH"
						: "PA_MONTH") +" in(select t.pa_month_str from pa_progress t where t.STAT_NO = GET_AR_STATNO(PERSON_ID,'"+ cpnyID +"') and t.PA_OPEN_FLAG = 1 AND t.CPNY_ID = '"+ cpnyID +"') " ;
			}else{
				whereSQL += " AND "+ (report_type != 1 ? "AR_MONTH"
						: "PA_MONTH") +" in(select t.pa_month_str from pa_progress t where t.STAT_NO = GET_AR_STATNO(PERSON_ID,'"+ cpnyID +"') and t.PA_OPEN_FLAG = 1 AND t.CPNY_ID = '"+ cpnyID +"') " ;
			}
		}
			
		if(parameterObject.get("deptNO") != null){
			whereSQL += " AND EXISTS ( SELECT     * "+
		           	"FROM HR_DEPARTMENT B1 "+
		           	"WHERE B1.DEPTNO="+(report_type != 1 ? "AR_SUMMARY_"
							: "PA_SUMMARY_")+cpnyID+".DEPTNO "+
		       	"START WITH B1.DEPTNO = '"+parameterObject.get("deptNO")+"'"+
		       	" CONNECT BY PRIOR B1.DEPTNO = B1.PARENT_DEPT_NO )" ;
		}
		//根据AR_SUPERVISIOR_INFO/PA_SUPERVISIOR_INFO/ESS_INFO 分别加不同的权限
		if(parameterObject.get("AR_SUPERVISIOR_INFO") != null){
			whereSQL += " AND EXISTS( " +
			 		  	" SELECT * " + 
						"  FROM AR_SUPERVISOR_INFO ," +(report_type != 1 ? "AR_SUMMARY_"
								: "PA_SUMMARY_")+cpnyID+
						" WHERE AR_SUPERVISOR_INFO.DEPTNO = " + (report_type != 1 ? "AR_SUMMARY_"
								: "PA_SUMMARY_")+cpnyID+".DEPTNO " +
						"   AND AR_SUPERVISOR_INFO.PERSON_ID = '" + parameterObject.get("AR_SUPERVISIOR_INFO") + "' " +
			 		    "   ) " ;
		}
		
		if(parameterObject.get("PA_SUPERVISIOR_INFO") != null){
			whereSQL += " AND EXISTS( " +
			 		  	" SELECT * " +
						"  FROM PA_SUPERVISOR_INFO " +
						" WHERE PA_SUPERVISOR_INFO.DEPTNO = " + (report_type != 1 ? "AR_SUMMARY_"
								: "PA_SUMMARY_")+cpnyID+".DEPTNO " +
								"   AND PA_SUPERVISOR_INFO.PERSON_ID = '" + parameterObject.get("PA_SUPERVISIOR_INFO") + "' " +
			 		    "   ) " ;
		}
		
		//年工资
		if(parameterObject.get("PA_YEAR") != null){
			whereSQL += " AND " + (report_type != 1 ? " SUBSTR(AR_MONTH,0,4) "
					: " SUBSTR(PA_MONTH,0,4) ")+" = '" + parameterObject.get("PA_YEAR") + "'"  ; ;
		}
		if(parameterObject.get("KEY") != null || parameterObject.get("deptNO") != null || parameterObject.get("PERSON_ID") != null){ 
			try {
				if(!whereSQL.equals("")){
					Map sqlMap = new LinkedHashMap();
					sqlMap.put("sql", sql+whereSQL);
					List list = new ArrayList();
					list = this.queryForList("sys.commons.retrieveReportDataList", sqlMap);
					for(int i=0;i<list.size();i++){
						sMap = new LinkedHashMap() ;
						Map tempMap = new LinkedHashMap();
						tempMap = (LinkedHashMap)list.get(i);
						for (int j=0;j < itemList.size();j++) {
							LinkedHashMap lmap = (LinkedHashMap)itemList.get(j);
							sMap.put(lmap.get("REF_ITEM_NAME"), tempMap.get(lmap.get("REF_ITEM_NAME")));
						}
						returnList.add(sMap);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 }
		return returnList;
	}
	/**
	 * 考勤查看查看--月上班日程 不分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkSchedule(Object object){
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("ar.attendanceView.getMonthWorkSchedule", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * 考勤查看查看--月上班日程
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkSchedule(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.attendanceView.getMonthWorkSchedule", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.attendanceView.getMonthWorkSchedule", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 考勤查看查看--月上班日程 查看总数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int getMonthWorkScheduleCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject("ar.attendanceView.getMonthWorkScheduleCnt",object)), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 考勤查看--月考勤查看（个人） 查看总数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int getMonthWorkListCnt(Object object){
		int returnInt = 0 ;
		try {
			Object o = this.queryForObject("ar.attendanceView.getMonthWorkList",object);
			if(o instanceof Map)
				returnInt = NumberUtils.parseNumber(ObjectUtils
						.toString(((Map) o).get("COUNT(*)")), Integer.class);
			else
				returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(o), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 考勤查看--月考勤查看（个人） 查看总数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int getMonthWorkLGETAListCnt(Object object){
		int returnInt = 0 ;
		try {
			Object o = this.queryForObject("ar.attendanceView.getMonthWorkLGETAList",object);
			if(o instanceof Map)
				returnInt = NumberUtils.parseNumber(ObjectUtils
						.toString(((Map) o).get("COUNT(*)")), Integer.class);
			else
				returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(o), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 未刷卡查询
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int noSwipingCardListCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject("ar.attendanceView.noSwipingCardListCnt",object)), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 考勤查看--月考勤查看（个人）
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.attendanceView.getMonthWorkList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.attendanceView.getMonthWorkList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 考勤查看--月考勤查看（个人）
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceView.getMonthWorkList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 考勤查看--月考勤查看（个人） TA
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkLGETAList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceView.getMonthWorkLGETAList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 考勤查看--月考勤查看（个人）
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List noSwipingCardList(Object object){
		return this.noSwipingCardList(object, -1, -1) ;
	}
	

	public List noSwipingCardList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.attendanceView.noSwipingCardList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.attendanceView.noSwipingCardList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 考勤查看--月考勤查看（个人）
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getEveryDayWorkList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.attendanceView.getKaoQinList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.attendanceView.getKaoQinList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 考勤查看--月考勤查看（个人）
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getEveryDayWorkList(Object object){
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("ar.attendanceView.getKaoQinList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 考勤查看--年假使用现状
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAnnualUsage(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceView.getAnnualUsage", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 考勤查看--年假使用现状-- 分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAnnualUsage(Object obj, int currentPage, int pageSize){		
		
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.attendanceView.getAnnualUsage", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.attendanceView.getAnnualUsage", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 考勤查看--年假使用现状--总条数
	 * @param object
	 * @return list
	 */	
	public int getAnnualUsageCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.attendanceView.getAnnualUsageCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	
	/**
	 * 遍历LIST取出每个人当月每天的班次
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getMonthWorkScheduleForAR(Object obj) throws Exception {
		return this.queryForList("ar.attendanceView.getMonthDay", obj);

	}
	
	/**
	 * 遍历LIST取出每个人当月每天的考勤信息
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getMonthWorkListForAR(Object obj) throws Exception {
		return this.queryForList("ar.attendanceView.getKaoQinList", obj);

	}
	/**
	 * 获取所有的考勤状态
	 * @param obj
	 * @return
	 */
	@Override
	public List getControlItemList(Map paramMap) {
		try {
			return this.queryForList("ar.attendanceView.getControlItemList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}
	/**
	 * 获取相应的考勤数据
	 * @param obj
	 * @return
	 */
	@Override
	public List getMonthControlList(Map paramMap) {
		try {
			return this.queryForList("ar.attendanceView.getMonthControlList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}
	
	/**
	 * 获取相应的考勤数据
	 * @param obj
	 * @return
	 */
	@Override
	public List getMonthControlList(Object paramMap, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.attendanceView.getMonthControlList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.attendanceView.getMonthControlList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getMonthControlListCnt(Map<String, Object> paramMap){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.attendanceView.getMonthControlListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 *  月考勤查看(个人新)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkNewOne(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceView.getMonthWorkNewOne", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 *  月考勤查看(个人新) TA
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkLGETANewOne(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceView.getMonthWorkLGETANewOne", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public List getAnnualUsageFact(Map paramMap) {
		List list = new ArrayList();
		try {
			list = this.queryForList("ar.attendanceView.getAnnualUsageFactList",paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List getAnnualUsageFactLGE(Map paramMap) {
		List list = new ArrayList();
		try {
			list = this.queryForList("ar.attendanceView.getAnnualUsageLGEList",paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	public int getArAfrimExceptionCnt(Object obj) {
       int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arMonth.getArAfrimExceptionCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	@SuppressWarnings("unchecked")
	public List getArAfrimExceptionList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.arMonth.getArAfrimExceptionList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.arMonth.getArAfrimExceptionList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	@SuppressWarnings("unchecked")
	public List getArAfrimExceptionList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getArAfrimExceptionList(obj, -1, -1) ;
		return returnList ;
	}

	@Override
	public int getArExceptionTempCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arMonth.getArExceptionTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@Override
	public int getArExceptionTempErrorCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arMonth.getArExceptionTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@Override
	public List getArExceptionTempList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.arMonth.getArExceptionTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.arMonth.getArExceptionTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getArExceptionTempList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getArExceptionTempList(obj, -1, -1) ;
		return returnList ;
	}

	/**
	 * 获取日考勤查看相应的考勤数据
	 * @param obj
	 * @return
	 */
	@Override
	public List getDetailControlList(Map paramMap) {
		try {
			return this.queryForList("ar.attendanceView.getDetailControlList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}
	
	/**
	 * 获取相应的考勤数据
	 * @param obj
	 * @return
	 */
	@Override
	public List getDetailControlList(Object paramMap, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.attendanceView.getDetailControlList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.attendanceView.getDetailControlList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getDetailControlListCnt(Map<String, Object> paramMap){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.attendanceView.getDetailControlListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 获取相应的申请数据
	 * @param obj
	 * @return
	 */
	@Override
	public List getAllApplyList(Object paramMap, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.attendanceView.getAllApplyList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.attendanceView.getAllApplyList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getAllApplyListCnt(Map<String, Object> paramMap){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.attendanceView.getAllApplyListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 获取申请的数据
	 * @param obj
	 * @return
	 */
	@Override
	public List getAllApplyList(Map paramMap) {
		try {
			return this.queryForList("ar.attendanceView.getAllApplyList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}
	/**
	 * 按照申请类型查询考勤项目
	 * @param obj
	 * @return
	 */
	@Override
	public List getItemForApplyList(Map paramMap) {
		try {
			return this.queryForList("ar.attendanceView.getItemForApplyList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}
	
	/**
	 * 按照申请类型查询考勤项目
	 * @param obj
	 * @return
	 */
	@Override
	public List getItemForKaoQinList(Map paramMap) {
		try {
			return this.queryForList("ar.attendanceView.getItemForKaoQinList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}
}

