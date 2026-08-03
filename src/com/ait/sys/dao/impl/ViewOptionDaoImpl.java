package com.ait.sys.dao.impl;

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
public class ViewOptionDaoImpl extends SqlMapClientSupport implements ViewOptionDao {

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
		String sqlAvg = parameterObject.get("sqlAvg") + ""!= null ? parameterObject.get("sqlAvg").toString() : "";

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
		if(parameterObject.get("paMonthfrom") != null){
			if(whereSQL.equals("")){
				whereSQL = " WHERE PA_MONTH between '" + 
				           parameterObject.get("paMonthfrom") + "' and '" +
				           parameterObject.get("paMonthto") +"'";
			}else{
				whereSQL += " AND PA_MONTH between '" + 
				           parameterObject.get("paMonthfrom") + "' and '" +
				           parameterObject.get("paMonthto") +"'";
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
						sMap.put("工资月", tempMap.get("工资月"));
						for (int j=0;j < itemList.size();j++) {
							LinkedHashMap lmap = (LinkedHashMap)itemList.get(j);
							sMap.put(lmap.get("REF_ITEM_NAME"), tempMap.get(lmap.get("REF_ITEM_NAME")));
						}
						returnList.add(sMap);
					}
				}
				if(!whereSQL.equals("")){
					Map sqlMap = new LinkedHashMap();
					sqlMap.put("sql", sqlAvg+whereSQL);
					List list = new ArrayList();
					list = this.queryForList("sys.commons.retrieveReportDataList", sqlMap);
					for(int i=0;i<list.size();i++){
						sMap = new LinkedHashMap() ;
						Map tempMap = new LinkedHashMap();
						tempMap = (LinkedHashMap)list.get(i);
						sMap.put("工资月", tempMap.get("平均工资"));

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
	/******************************20150206 zyh start**************************************/
	/**
	 * 个人考勤追溯查看页面(view Personal Attendance)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List personalAttendanceBack(Object object) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		returnList = this.personalAttendanceBack(object, -1, -1);
		return returnList;
	}
	@SuppressWarnings("unchecked")
	public List personalAttendanceBack(Object obj, int currentPage, int pageSize){
		List returnList = new ArrayList();
		try {
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList("ess.personinfo.personalAttendanceBack",
							obj, currentPage, pageSize);
				} else {
					returnList = this
							.queryForList("ess.personinfo.personalAttendanceBack", obj);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	public int getPersonalAttendanceBackCnt(Object object){
		int returnCnt= 0;
		try {
			returnCnt=NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.personinfo.personalAttendanceBackCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnCnt;
	}
	/******************************20150206 zyh end**************************************/
}
