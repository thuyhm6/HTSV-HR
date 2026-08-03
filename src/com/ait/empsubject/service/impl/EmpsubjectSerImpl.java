package com.ait.empsubject.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ait.empsubject.dao.EmpsubjectDao;
import com.ait.empsubject.service.EmpsubjectSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.LoginDao;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.inct.service.SalesmanEvaluationSer;

/**
 * Copyright:    LG CNS
 * Company:      LG CNS
 * @fileName:    EmpsubjectSerImpl.java
 * @Create date: 2014.06.25
 * @Create by:   L.H.H
 * @version 1.0
 */
@Service
public class EmpsubjectSerImpl implements EmpsubjectSer {

	Logger logger = Logger.getLogger(EmpsubjectSerImpl.class);

	@Autowired
	private EmpsubjectDao empsubjectDao;
	
	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private SalesmanEvaluationSer salesmanEvaluationSer;

	/**
	 * 课程组管理
	 * @param request
	 * @return List
	 * @Create date: 2014.06.26
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getSubjectGroupList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
        if (request.getParameter("USE_YN")==null && ( paramMap.get("USE_YN")==null || ((String)paramMap.get("USE_YN")).equals("")))
        	paramMap.put("USE_YN", "Y");  
		
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					empsubjectDao.getSubjectGroupList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empsubjectDao.getSubjectGroupList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.26
		@SuppressWarnings("unchecked")
		@Override
	public List getGroupList(HttpServletRequest request) {
			
			List retrunList = new ArrayList() ;
			
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("language", Messages.getLanguage(request));
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("PERSON_ID",admin.getAdminID() );
			
			
			if (UiUtil.getPageNum(request) > 0){
				retrunList = 
						empsubjectDao.getGroupList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
			}
			else{
				retrunList = empsubjectDao.getGroupList(paramMap) ;
			}
			
			return retrunList ;
	}
		
	// @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	@Override
	public int getSubjectGroupListCnt(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
        if (request.getParameter("USE_YN")==null && ( paramMap.get("USE_YN")==null || ((String)paramMap.get("USE_YN")).equals("")))
        	paramMap.put("USE_YN", "Y");  
		
		return empsubjectDao.getSubjectGroupListCnt(paramMap) ;
	}
	
	// @Create date: 2014.06.26
	@Override
	@SuppressWarnings("unchecked")
	public Object getSubjectGroupInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
        paramMap.put("USE_YN", ""); 
		returnObj = empsubjectDao.getSubjectGroupInfo(paramMap) ;
		return returnObj ;
	}

	// @Create date: 2014.06.26
	@Override
	@SuppressWarnings("unchecked")
	public int updateSubjectGroup(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDT_USER", admin.getEmpID()) ;
			this.empsubjectDao.updateSubjectGroup(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	// @Create date: 2014.06.26
	@Override
	@SuppressWarnings("unchecked")
	public int addSubjectGroup(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDT_USER", admin.getEmpID()) ;
			this.empsubjectDao.addSubjectGroup(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}


	/**************************************************************************************************************/
	/**
	 * 课程管理
	 * @param request
	 * @return List
	 * @Create date: 2014.06.30
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getSubjectList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
        if (request.getParameter("USE_YN")==null && ( paramMap.get("USE_YN")==null || ((String)paramMap.get("USE_YN")).equals("")))
        	paramMap.put("USE_YN", "Y");  
		
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					empsubjectDao.getSubjectList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empsubjectDao.getSubjectList(paramMap) ;
		}
		
		return retrunList ;
	}
			
	// @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	@Override
	public int getSubjectListCnt(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
        if (request.getParameter("USE_YN")==null && ( paramMap.get("USE_YN")==null || ((String)paramMap.get("USE_YN")).equals("")))
        	paramMap.put("USE_YN", "Y");  
		
		return empsubjectDao.getSubjectListCnt(paramMap) ;
	}
	
	// @Create date: 2014.06.30
	@Override
	@SuppressWarnings("unchecked")
	public Object getSubjectInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(paramMap.get("NO")!=null){
			paramMap.put("POST_NO", paramMap.get("NO"));
		}
		returnObj = empsubjectDao.getSubjectInfo(paramMap) ;
		return returnObj ;
	}

	// @Create date: 2014.06.30
	@Override
	@SuppressWarnings("unchecked")
	public int updateSubject(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDT_USER", admin.getEmpID()) ;
			
			String startDate = request.getParameter("START_DATE");
			if (!(startDate == null || startDate.equals(""))){
				String timeArr1[] = startDate.split("-");
				startDate = timeArr1[0]+timeArr1[1]+timeArr1[2];
				paramMap.put("START_DATE", startDate); 
			}

			String endDate = request.getParameter("END_DATE");
			if (!(endDate == null || endDate.equals(""))){
				String timeArr2[] = endDate.split("-");
				endDate = timeArr2[0]+timeArr2[1]+timeArr2[2];
				paramMap.put("END_DATE", endDate); 
			}
			this.empsubjectDao.updateSubject(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	// @Create date: 2014.06.30
	@Override
	@SuppressWarnings("unchecked")
	public int addSubject(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDT_USER", admin.getEmpID()) ;

			String startDate = request.getParameter("START_DATE");
			if (!(startDate == null || startDate.equals(""))){
				String timeArr1[] = startDate.split("-");
				startDate = timeArr1[0]+timeArr1[1]+timeArr1[2];
				paramMap.put("START_DATE", startDate); 
			}

			String endDate = request.getParameter("END_DATE");
			if (!(endDate == null || endDate.equals(""))){
				String timeArr2[] = endDate.split("-");
				endDate = timeArr2[0]+timeArr2[1]+timeArr2[2];
				paramMap.put("END_DATE", endDate); 
			}
			this.empsubjectDao.addSubject(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	/**************************************************************************************************************/
	/**
	 * 课程别实绩汇总
	 * @param request
	 * @return List
	 * @Create date: 2014.07.01
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getGradeStatisticsList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
		
		/*if (!(paramMap.get("areaStr") == null || paramMap.get("areaStr").equals(""))){
			String PAY_AREA_CD_NOS= paramMap.get("areaStr").toString() ;
			String[] NOS = PAY_AREA_CD_NOS.split("!");
			paramMap.put("PAY_AREA_CD", NOS);
		}*/

		if(paramMap.get("BRANCH_EDU_3") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_EDU_3").toString());
		}
		
		String startDate = request.getParameter("seach_START_DATE");
		if (!(startDate == null || startDate.equals(""))){
			String timeArr1[] = startDate.split("-");
			startDate = timeArr1[0]+timeArr1[1]+timeArr1[2];
			paramMap.put("START_DATE", startDate);
		}
		String endDate = request.getParameter("seach_END_DATE");
		if (!(endDate == null || endDate.equals(""))){
		String timeArr2[] = endDate.split("-");
		endDate = timeArr2[0]+timeArr2[1]+timeArr2[2];
		paramMap.put("END_DATE", endDate);
		}
		
        if (request.getParameter("seach_PROD_TP")==null && ( paramMap.get("PROD_TP")==null || ((String)paramMap.get("PROD_TP")).equals(""))){
        	paramMap.put("PROD_TP", "");
			paramMap.put("PROD_TP_NM", "");		
        }
        
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					empsubjectDao.getGradeStatisticsList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empsubjectDao.getGradeStatisticsList(paramMap) ;
		}
		
		return retrunList ;
	}		
	
	/**
	 * 课程别实绩汇总count
	 * @param request
	 * @return int
	 * @Create date: 2014.07.01
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getGradeStatisticsListCnt(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
		
		/*if (!(paramMap.get("areaStr") == null || paramMap.get("areaStr").equals(""))){
			String PAY_AREA_CD_NOS= paramMap.get("areaStr").toString() ;
			String[] NOS = PAY_AREA_CD_NOS.split("!");
			paramMap.put("PAY_AREA_CD", NOS);
		}*/

		if(paramMap.get("BRANCH_EDU_3") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_EDU_3").toString());
		}
		
		String startDate = request.getParameter("seach_START_DATE");
		if (!(startDate == null || startDate.equals(""))){
			String timeArr1[] = startDate.split("-");
			startDate = timeArr1[0]+timeArr1[1]+timeArr1[2];
			paramMap.put("START_DATE", startDate);
		}
		String endDate = request.getParameter("seach_END_DATE");
		if (!(endDate == null || endDate.equals(""))){
		String timeArr2[] = endDate.split("-");
		endDate = timeArr2[0]+timeArr2[1]+timeArr2[2];
		paramMap.put("END_DATE", endDate);
		}
		
        if (request.getParameter("seach_PROD_TP")==null && ( paramMap.get("PROD_TP")==null || ((String)paramMap.get("PROD_TP")).equals(""))){
        	paramMap.put("PROD_TP", "");
			paramMap.put("PROD_TP_NM", "");		
        }
		
		return empsubjectDao.getGradeStatisticsListCnt(paramMap) ;
	}
	
	/**
	 * 课程别实绩汇总Excel导出
	 * @param request
	 * @return
	 * @Create date: 2014.07.02
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getGradeStatisticsListExcel(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
		
		/*if (!(paramMap.get("areaStr") == null || paramMap.get("areaStr").equals(""))){
			String PAY_AREA_CD_NOS= paramMap.get("areaStr").toString() ;
			String[] NOS = PAY_AREA_CD_NOS.split("!");
			paramMap.put("PAY_AREA_CD", NOS);
		}*/

		if(paramMap.get("BRANCH_EDU_3") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_EDU_3").toString());
		}
		
		String startDate = request.getParameter("START_DATE");
		if (!(startDate == null || startDate.equals(""))){
			String timeArr1[] = startDate.split("-");
			startDate = timeArr1[0]+timeArr1[1]+timeArr1[2];
			paramMap.put("START_DATE", startDate);
		}
		String endDate = request.getParameter("END_DATE");
		if (!(endDate == null || endDate.equals(""))){
		String timeArr2[] = endDate.split("-");
		endDate = timeArr2[0]+timeArr2[1]+timeArr2[2];
		paramMap.put("END_DATE", endDate);
		}
		
        if (paramMap.get("PROD_TP")==null || ((String)paramMap.get("PROD_TP")).equals("")){
        	paramMap.put("PROD_TP", "");
			paramMap.put("PROD_TP_NM", "");		
        }

		retrunList = empsubjectDao.getGradeStatisticsList(paramMap) ;
		
		return retrunList ;
	}
	/**************************************************************************************************************/
	/**
	 * 课程周别汇总
	 * @param request
	 * @return List
	 * @Create date: 2014.07.04
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getGradeWeekList(HttpServletRequest request) throws Exception {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
		
		/*if (!(paramMap.get("areaStr") == null || paramMap.get("areaStr").equals(""))){
			String PAY_AREA_CD_NOS= paramMap.get("areaStr").toString() ;
			String[] NOS = PAY_AREA_CD_NOS.split("!");
			paramMap.put("PAY_AREA_CD", NOS);
		}*/

		if(paramMap.get("BRANCH_EDU_2") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_EDU_2").toString());
		}
		
		Date sysdate =new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Calendar   cal   =   Calendar.getInstance(); 

		String TIME = request.getParameter("seach_START_DATE");
		if (TIME == null || TIME.equals("")){
	    	TIME = df.format(sysdate);
		    cal.setTime(df.parse(TIME)); 
		}else {
			String timeArr[] = TIME.split("-");
			TIME = timeArr[0]+timeArr[1]+timeArr[2];
			cal.setTime(df.parse(TIME)); 
		}

	    cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
	    cal.add(Calendar.DAY_OF_MONTH,-1);
	    String afterTime=df.format(cal.getTime());
	    cal.add(Calendar.DAY_OF_MONTH,-6);
	    String beforeTime=df.format(cal.getTime());
	    
		paramMap.put("beforeTime",beforeTime );
		paramMap.put("afterTime",afterTime );
		paramMap.put("TIME",TIME );
        if (request.getParameter("seach_PROD_TP")==null && ( paramMap.get("PROD_TP")==null || ((String)paramMap.get("PROD_TP")).equals(""))){
        	paramMap.put("PROD_TP", "");
			paramMap.put("PROD_TP_NM", "");		
        }
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					empsubjectDao.getGradeWeekList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empsubjectDao.getGradeWeekList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * 课程周别汇总count
	 * @param request
	 * @return int
	 * @Create date: 2014.07.08
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getGradeWeekListCnt(HttpServletRequest request) throws Exception {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
		
		/*if (!(paramMap.get("areaStr") == null || paramMap.get("areaStr").equals(""))){
			String PAY_AREA_CD_NOS= paramMap.get("areaStr").toString() ;
			String[] NOS = PAY_AREA_CD_NOS.split("!");
			paramMap.put("PAY_AREA_CD", NOS);
		}*/

		if(paramMap.get("BRANCH_EDU_2") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_EDU_2").toString());
		}
		
		Date sysdate =new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Calendar   cal   =   Calendar.getInstance(); 

		String TIME = request.getParameter("seach_START_DATE");
		if (TIME == null || TIME.equals("")){
	    	TIME = df.format(sysdate);
		    cal.setTime(df.parse(TIME)); 
		}else {
			String timeArr[] = TIME.split("-");
			TIME = timeArr[0]+timeArr[1]+timeArr[2];
			cal.setTime(df.parse(TIME)); 
		}

	    cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
	    cal.add(Calendar.DAY_OF_MONTH,-1);
	    String afterTime=df.format(cal.getTime());
	    cal.add(Calendar.DAY_OF_MONTH,-6);
	    String beforeTime=df.format(cal.getTime());
	    
		paramMap.put("beforeTime",beforeTime );
		paramMap.put("afterTime",afterTime );
		paramMap.put("TIME",TIME );
        if (request.getParameter("seach_PROD_TP")==null && ( paramMap.get("PROD_TP")==null || ((String)paramMap.get("PROD_TP")).equals(""))){
        	paramMap.put("PROD_TP", "");
			paramMap.put("PROD_TP_NM", "");		
        }
				
		return empsubjectDao.getGradeWeekListCnt(paramMap) ;
	}
	
	/**
	 * 课程周别汇总Excel导出
	 * @param request
	 * @return
	 * @Create date: 2014.07.04
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getGradeWeekListExcel(HttpServletRequest request) throws Exception {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
		
		/*if (!(paramMap.get("areaStr") == null || paramMap.get("areaStr").equals(""))){
			String PAY_AREA_CD_NOS= paramMap.get("areaStr").toString() ;
			String[] NOS = PAY_AREA_CD_NOS.split("!");
			paramMap.put("PAY_AREA_CD", NOS);
		}*/

		if(paramMap.get("BRANCH_EDU_2") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_EDU_2").toString());
		}
		
		Date sysdate =new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Calendar   cal   =   Calendar.getInstance(); 

		String TIME = request.getParameter("START_DATE");
		if (TIME == null || TIME.equals("")){
	    	TIME = df.format(sysdate);
		    cal.setTime(df.parse(TIME)); 
		}else {
			String timeArr[] = TIME.split("-");
			TIME = timeArr[0]+timeArr[1]+timeArr[2];
			cal.setTime(df.parse(TIME)); 
		}

	    cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
	    cal.add(Calendar.DAY_OF_MONTH,-1);
	    String afterTime=df.format(cal.getTime());
	    cal.add(Calendar.DAY_OF_MONTH,-6);
	    String beforeTime=df.format(cal.getTime());
	    
		paramMap.put("beforeTime",beforeTime );
		paramMap.put("afterTime",afterTime );
		paramMap.put("TIME",TIME );
        if (paramMap.get("PROD_TP")==null || ((String)paramMap.get("PROD_TP")).equals("")){
        	paramMap.put("PROD_TP", "");
			paramMap.put("PROD_TP_NM", "");		
        }
        
		retrunList = empsubjectDao.getGradeWeekList(paramMap) ;
		
		return retrunList ;
	}
	/**************************************************************************************************************/
	/**
	 * 教育实绩
	 * @param request
	 * @return List
	 * @Create date: 2014.07.08
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPromotoGradeList(HttpServletRequest request) throws Exception {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
		paramMap.put("EMPNO", paramMap.get("dwz.person.empId"));
		paramMap.put("USER_NO", admin.getUserNo());
		
		/*if (!(paramMap.get("areaStr") == null || paramMap.get("areaStr").equals(""))){
			String PAY_AREA_CD_NOS= paramMap.get("areaStr").toString() ;
			String[] NOS = PAY_AREA_CD_NOS.split("!");
			paramMap.put("PAY_AREA_CD", NOS);
		}*/

		if(paramMap.get("BRANCH_EDU_1") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_EDU_1").toString());
		}
		
		Date sysdate =new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Calendar   cal   =   Calendar.getInstance(); 

		String startDate = request.getParameter("seach_START_DATE");
		if (startDate == null || startDate.equals("")){
			startDate = df.format(sysdate);
		    cal.setTime(df.parse(startDate)); 
		}else {
			String timeArr1[] = startDate.split("-");
			startDate = timeArr1[0]+timeArr1[1]+timeArr1[2];
			cal.setTime(df.parse(startDate)); 
		}
		paramMap.put("START_DATE", df.format(cal.getTime())); 

		String endDate = request.getParameter("seach_END_DATE");
		if (endDate == null || endDate.equals("")){
			endDate = df.format(sysdate);
		    cal.setTime(df.parse(endDate)); 
		}else {
			String timeArr2[] = endDate.split("-");
			endDate = timeArr2[0]+timeArr2[1]+timeArr2[2];
			cal.setTime(df.parse(endDate)); 
		}
		paramMap.put("END_DATE", df.format(cal.getTime())); 
		
		
        if (request.getParameter("USE_YN")==null && ( paramMap.get("USE_YN")==null || ((String)paramMap.get("USE_YN")).equals("")))
        	paramMap.put("USE_YN", "Y");      
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					empsubjectDao.getPromotoGradeList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empsubjectDao.getPromotoGradeList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 教育实绩count
	 * @param request
	 * @return int
	 * @Create date: 2014.07.08
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPromotoGradeListCnt(HttpServletRequest request) throws Exception {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
		paramMap.put("EMPNO", paramMap.get("dwz.person.empId"));
		paramMap.put("USER_NO", admin.getUserNo());
		
		/*if (!(paramMap.get("areaStr") == null || paramMap.get("areaStr").equals(""))){
			String PAY_AREA_CD_NOS= paramMap.get("areaStr").toString() ;
			String[] NOS = PAY_AREA_CD_NOS.split("!");
			paramMap.put("PAY_AREA_CD", NOS);
		}*/

		if(paramMap.get("BRANCH_EDU_1") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_EDU_1").toString());
		}
		
		Date sysdate =new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Calendar   cal   =   Calendar.getInstance(); 

		String startDate = request.getParameter("seach_START_DATE");
		if (startDate == null || startDate.equals("")){
			startDate = df.format(sysdate);
		    cal.setTime(df.parse(startDate)); 
		}else {
			String timeArr1[] = startDate.split("-");
			startDate = timeArr1[0]+timeArr1[1]+timeArr1[2];
			cal.setTime(df.parse(startDate)); 
		}
		paramMap.put("START_DATE", df.format(cal.getTime())); 

		String endDate = request.getParameter("seach_END_DATE");
		if (endDate == null || endDate.equals("")){
			endDate = df.format(sysdate);
		    cal.setTime(df.parse(endDate)); 
		}else {
			String timeArr2[] = endDate.split("-");
			endDate = timeArr2[0]+timeArr2[1]+timeArr2[2];
			cal.setTime(df.parse(endDate)); 
		}
		paramMap.put("END_DATE", df.format(cal.getTime()));     
		
        if (request.getParameter("USE_YN")==null && ( paramMap.get("USE_YN")==null || ((String)paramMap.get("USE_YN")).equals("")))
        	paramMap.put("USE_YN", "Y");	      	
				
		return empsubjectDao.getPromotoGradeListCnt(paramMap) ;
	}
	
	/**
	 * 教育实绩Excel导出
	 * @param request
	 * @return
	 * @Create date: 2014.07.08
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPromotoGradeListExcel(HttpServletRequest request) throws Exception {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
		paramMap.put("USER_NO",admin.getUserNo());
		/*if (!(paramMap.get("areaStr") == null || paramMap.get("areaStr").equals(""))){
			String PAY_AREA_CD_NOS= paramMap.get("areaStr").toString() ;
			String[] NOS = PAY_AREA_CD_NOS.split("!");
			paramMap.put("PAY_AREA_CD", NOS);
		}*/

		if(paramMap.get("BRANCH_EDU_1") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_EDU_1").toString());
		}
		
		Date sysdate =new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Calendar   cal   =   Calendar.getInstance(); 

		String startDate = request.getParameter("START_DATE");
		if (startDate == null || startDate.equals("")){
			startDate = df.format(sysdate);
		    cal.setTime(df.parse(startDate)); 
		}else {
			String timeArr1[] = startDate.split("-");
			startDate = timeArr1[0]+timeArr1[1]+timeArr1[2];
			cal.setTime(df.parse(startDate)); 
		}
		paramMap.put("START_DATE", df.format(cal.getTime())); 

		String endDate = request.getParameter("END_DATE");
		if (endDate == null || endDate.equals("")){
			endDate = df.format(sysdate);
		    cal.setTime(df.parse(endDate)); 
		}else {
			String timeArr2[] = endDate.split("-");
			endDate = timeArr2[0]+timeArr2[1]+timeArr2[2];
			cal.setTime(df.parse(endDate)); 
		}
		paramMap.put("END_DATE", df.format(cal.getTime())); 	    
		
        if (request.getParameter("USE_YN")==null && ( paramMap.get("USE_YN")==null || ((String)paramMap.get("USE_YN")).equals("")))
        	paramMap.put("USE_YN", "Y");      

		retrunList = empsubjectDao.getPromotoGradeList(paramMap) ;
		
		return retrunList ;
	}
	
	/**
	 * 教育实绩删除
	 * @param request
	 * @return int
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings("unchecked")
	public int deletePromotoGrade(HttpServletRequest request){
		
		// 页面参数
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> arDetailInfoList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		try {
			
			empsubjectDao.deletePromotoGrade(arDetailInfoList) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 教育实绩导入结果查询
	 * @param request
	 * @return
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPromotoGradeResultList(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		paramMap.put("SUBSD_CD", admin.getCpnyId()) ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList =  empsubjectDao.getPromotoGradeResultList(paramMap , 
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
					);
		}else{
			retrunList =  empsubjectDao.getPromotoGradeResultList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 教育实绩导入结果查询count
	 * @param request
	 * @return
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPromotoGradeResultListCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		paramMap.put("SUBSD_CD", admin.getCpnyId()) ;
		return empsubjectDao.getPromotoGradeResultListCnt(paramMap);
	}
	
	/**
	 * 教育实绩导入结果查询error count
	 * @param request
	 * @return
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPromotoGradeErrCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		paramMap.put("SUBSD_CD", admin.getCpnyId()) ;
		return empsubjectDao.getPromotoGradeErrCnt(paramMap);
	}
	
	/**
	 * 教育实绩验证并导入正式表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * @Create date: 2014.07.10
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String importPromotoGradeRAWFromExcel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String result = "";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDT_USER", admin.getEmpID()) ;
		paramMap.put("SUBSD_CD", admin.getCpnyId()) ;
		paramMap.put("PAY_AREA_CD", admin.getPayAreaCd()) ;
		result = this.empsubjectDao.importPromotoGradeRAWFromExcel(paramMap);
		return result;
	}
	
	/**
	 * 大区选择框
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * @Create date: 2014.07.24
	 */
	@SuppressWarnings("unchecked")
	public List getDeptAreaList(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return this.empsubjectDao.getDeptAreaList(paramMap) ;
	}	
	/*subject group code list*/
	@Override
	public List getSubjectGroupCodeList(Map paramMap){
		List retrunList = new ArrayList() ;		
		retrunList = empsubjectDao.getSubjectGroupCodeList(paramMap);		
		return retrunList ;
	}

	/**************************************************************************************************************/
}
