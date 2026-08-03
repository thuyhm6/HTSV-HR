package com.ait.ar.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ar.dao.MonthAttendanceDao;
import com.ait.ar.service.MonthAttendanceSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.ViewOptionDao;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.ViewOptionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PersonalAttendanceSerImp.java
 * @Description:
 * @Create date: 2012-5-9 上午11:05:15
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class MonthAttendanceSerImp implements MonthAttendanceSer {

	Logger logger = Logger.getLogger(MonthAttendanceSerImp.class);
	
	@Autowired
	private ViewOptionDao viewOptionDao;
	@Autowired
	private ViewOptionUtil viewOptionUtil;
	@Autowired
	private MonthAttendanceDao monthAttendanceDao;
	
	/**
	 * 个人考勤(make DataTable)
	 * @param request
	 * @return String
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String makeDataTable(HttpServletRequest request, String menuNo) {
		
		String dataTable = "";
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		
		paramMap.put("MENU_NO", menuNo);
		
		if(paramMap.get("arYear") != null && !"".equals(paramMap.get("arYear").toString())
				&& paramMap.get("arMonth") != null && !"".equals(paramMap.get("arMonth").toString())){
			
			paramMap.put("arMonth", paramMap.get("arYear").toString()+paramMap.get("arMonth").toString());
		}
		
		dataTable = viewOptionUtil.makeDataTable(paramMap);
		
		return dataTable;
	}

	/**
	 * 遍历LIST取出每个人当月每天的班次
	 * list
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List getMonthWorkScheduleForAR(List list, HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap1 = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//paramMap.put("YEAR", paramMap.get("paYear"));
		//paramMap.put("MONTH", paramMap.get("paMonth"));
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+paramMap.get("paMonth"));
		//paramMap.put("CPNY_ID", admin.getCpnyId());
		List returnList = new ArrayList();
		LinkedHashMap paramMap = null;
		LinkedHashMap childMap = null;
		

		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("paYear", paramMap1.get("paYear"));
			paramMap.put("paMonth", paramMap1.get("paMonth"));
			paramMap.put("STAT_NO", paramMap1.get("STAT_NO"));
			paramMap.put("CPNY_ID", admin.getCpnyId());
			//String hrefFlag = "1";
			List aList = this.monthAttendanceDao.getMonthWorkScheduleForAR(paramMap);
			List childList = new ArrayList();
			for (int j = 0; j < aList.size(); j++) {
				childMap = (LinkedHashMap) aList.get(j);
				//childMap.put("HREF_FLAG", hrefFlag);
				//String affirmFlag = childMap.get("AFFIRM_FLAG") != null ? childMap.get("AFFIRM_FLAG").toString(): "";
				// 如果这一步未决裁或者决裁未通过,则下一步通过和否决的链接屏蔽
				
				childList.add(childMap);
			}
			//hrefFlag = "0";
			paramMap.put("monthDay", childList);
			paramMap.put("ADMIN_ID", admin.getPersonId());
			returnList.add(paramMap);
		}
		return returnList;
	}
	
	/**
	 * 遍历LIST取出每个人当月每天的考勤信息
	 * list
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List getMonthWorkListForAR(List list, HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap1 = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		List returnList = new ArrayList();
		LinkedHashMap paramMap = null;
		LinkedHashMap childMap = null;
		String paYear = paramMap1.get("paYear")!=null?paramMap1.get("paYear").toString():"";
		String paMonth = paramMap1.get("paMonth")!=null?paramMap1.get("paMonth").toString():"";
		paMonth = paYear+paMonth;

		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("paMonth", paMonth);
			paramMap.put("STAT_NO", paramMap1.get("STAT_NO"));
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("interLanguage",admin.getLanguage());
			//String hrefFlag = "1";
			List aList = this.monthAttendanceDao.getMonthWorkListForAR(paramMap);
			List childList = new ArrayList();
			for (int j = 0; j < aList.size(); j++) {
				childMap = (LinkedHashMap) aList.get(j);
				//childMap.put("HREF_FLAG", hrefFlag);
				//String affirmFlag = childMap.get("AFFIRM_FLAG") != null ? childMap.get("AFFIRM_FLAG").toString(): "";
				// 如果这一步未决裁或者决裁未通过,则下一步通过和否决的链接屏蔽
				
				childList.add(childMap);
			}
			//hrefFlag = "0";
			paramMap.put("everyDayWorkList", childList);
			paramMap.put("ADMIN_ID", admin.getPersonId());
			returnList.add(paramMap);
		}
		return returnList;
	}
	
	/**
	 * 个人考勤(月上班日程)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getMonthWorkSchedule(HttpServletRequest request) throws Exception{
		List list = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String year = paramMap.get("PA_YEAR")!=null?paramMap.get("PA_YEAR").toString():"";
		paramMap.put("YEAR", paramMap.get("paYear"));
		paramMap.put("MONTH", paramMap.get("paMonth"));
		paramMap.put("STAT_NO", paramMap.get("STAT_NO"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//如果没有STAT_NO，不进行查询
		if(paramMap.get("STAT_NO")==null || "".equals(paramMap.get("STAT_NO"))){
			return list;
		}
		
		if (UiUtil.getPageNum(request) > 0){
			list = 
				monthAttendanceDao.getMonthWorkSchedule(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			list = monthAttendanceDao.getMonthWorkSchedule(paramMap) ;
		}
		//return returnList ;
		return this.getMonthWorkScheduleForAR(list, request);
	}
	
	/**
	 * 个人考勤(月上班日程) 导出EXCEL用
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getMonthWorkScheduleExcel(HttpServletRequest request) throws Exception{
		List list = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String year = paramMap.get("PA_YEAR")!=null?paramMap.get("PA_YEAR").toString():"";
		paramMap.put("YEAR", paramMap.get("paYear"));
		paramMap.put("MONTH", paramMap.get("paMonth"));
		paramMap.put("STAT_NO", paramMap.get("STAT_NO"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		
			list = monthAttendanceDao.getMonthWorkSchedule(paramMap) ;
		//return list ;
		return this.getMonthWorkScheduleForAR(list, request);
	}
	
	/**
	 * 个人考勤(月上班日程)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getMonthWorkScheduleCnt(HttpServletRequest request) {
		int returnInt = 0 ;
		List list = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String year = paramMap.get("PA_YEAR")!=null?paramMap.get("PA_YEAR").toString():"";
		paramMap.put("YEAR", paramMap.get("paYear"));
		paramMap.put("MONTH", paramMap.get("paMonth"));
		paramMap.put("STAT_NO", paramMap.get("STAT_NO"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//如果没有STAT_NO，不进行查询
		if(paramMap.get("STAT_NO")==null || "".equals(paramMap.get("STAT_NO"))){
			return 0;
		}
		returnInt = monthAttendanceDao.getMonthWorkScheduleCnt(paramMap) ;
		return returnInt ;
	}
	
	/**
	 * 个人考勤(月考勤查看)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getMonthWorkListCnt(HttpServletRequest request) {
		int returnInt = 0 ;
		List list = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String paYear = paramMap.get("paYear")!=null?paramMap.get("paYear").toString():"";
		String paMonth = paramMap.get("paMonth")!=null?paramMap.get("paMonth").toString():"";
		paMonth = paYear+paMonth;
		//paramMap.put("YEAR", paramMap.get("paYear"));
		paramMap.put("paMonth", paMonth);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("isCount", true);
		//如果没有STAT_NO，不进行查询
		if(paramMap.get("condition")==null || "".equals(paramMap.get("condition"))||paramMap.get("STAT_NO")==null || "".equals(paramMap.get("STAT_NO"))){
			return 0;
		}
		if(admin.getCpnyId().equals("SST") )
		{
			returnInt = monthAttendanceDao.getMonthWorkLGETAListCnt(paramMap) ;
		}
		else{
			returnInt = monthAttendanceDao.getMonthWorkListCnt(paramMap) ;
		}
		
		
		return returnInt ;
	}
	
	
	/**
	 * 未刷卡查询
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int noSwipingCardListCnt(HttpServletRequest request) {
		int returnInt = 0 ;
		List list = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String paYear = paramMap.get("paYear")!=null?paramMap.get("paYear").toString():"";
		String paMonth = paramMap.get("paMonth")!=null?paramMap.get("paMonth").toString():"";
		paMonth = paYear+paMonth;
		//paramMap.put("YEAR", paramMap.get("paYear"));
		paramMap.put("paMonth", paMonth);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("AR_DETAIL_TAB", "AR_DETAIL_" + admin.getCpnyId());
		
		//如果没有STAT_NO，不进行查询
		returnInt = monthAttendanceDao.noSwipingCardListCnt(paramMap) ;
		return returnInt ;
	}
	/**
	 * 个人考勤(月考勤查看)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getMonthWorkList(HttpServletRequest request) throws Exception{
		List list = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String paYear = paramMap.get("paYear")!=null?paramMap.get("paYear").toString():"";
		String paMonth = paramMap.get("paMonth")!=null?paramMap.get("paMonth").toString():"";
		String paTime = paYear+paMonth;
		paramMap.put("paYear", paramMap.get("paYear"));
		paramMap.put("paMonth", paTime);
		//paramMap.put("paTime", paTime);
		paramMap.put("STAT_NO", paramMap.get("STAT_NO"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//如果没有condition，不进行查询
		if(paramMap.get("condition")==null || "".equals(paramMap.get("condition"))||paramMap.get("STAT_NO")==null || "".equals(paramMap.get("STAT_NO"))){
			return list;
		}
		if(admin.getCpnyId().equals("SST") )
		{
			list = monthAttendanceDao.getMonthWorkLGETAList(paramMap) ;
		}
		else{
			list = monthAttendanceDao.getMonthWorkList(paramMap) ;
		}
		
		return list;//this.getMonthWorkListForAR(list, request);
		
	}
	
	/**
	 * 未刷卡查询
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List noSwipingCardList(HttpServletRequest request) throws Exception{
		List list = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("STAT_NO", paramMap.get("STAT_NO"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("AR_DETAIL_TAB", "AR_DETAIL_" + admin.getCpnyId());

		if (UiUtil.getPageNum(request) > 0){
			list = monthAttendanceDao.noSwipingCardList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			list = monthAttendanceDao.noSwipingCardList(paramMap) ;
		}
		return list;
	}
	
	/**
	 * 个人考勤(月考勤查看)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEveryDayWorkList(HttpServletRequest request) throws Exception{
		List list = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String paYear = paramMap.get("paYear")!=null?paramMap.get("paYear").toString():"";
		String paMonth = paramMap.get("paMonth")!=null?paramMap.get("paMonth").toString():"";
		paMonth = paYear+paMonth;
		//paramMap.put("YEAR", paramMap.get("paYear"));
		paramMap.put("paMonth", paMonth);
		paramMap.put("STAT_NO", paramMap.get("STAT_NO"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//如果没有condition，不进行查询
		if(paramMap.get("condition")==null || "".equals(paramMap.get("condition"))||paramMap.get("STAT_NO")==null || "".equals(paramMap.get("STAT_NO"))){
			return list;
		}
		
		if (UiUtil.getPageNum(request) > 0){
			list = 
				monthAttendanceDao.getEveryDayWorkList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			list = 
			monthAttendanceDao.getEveryDayWorkList(paramMap) ;
		}
		//return returnList ;
		return list;
		
	}
	
	/**
	 * 个人考勤(年假使用现状)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAnnualUsage(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		String dataFlag = request.getParameter("seach_DATA_FLAG")!=null?request.getParameter("seach_DATA_FLAG").toString():"";
		//第一次进入页面，不进行查询
		if(dataFlag==null || "".equals(dataFlag)){
			return returnList;
		}
		String arYear = request.getParameter("seach_AR_YEAR").toString();
		//String arMonth = request.getParameter("seach_AR_MONTH").toString();
		String deptno=request.getParameter("seach_DEPT_NO");
		String key = request.getParameter("seach_KEY").toString();
		
		paramMap.put("AR_YEAR", arYear);
		//paramMap.put("AR_MONTH", arYear+arMonth);
		paramMap.put("DEPT_NO", deptno);
		paramMap.put("KEY", key);
			
		if (UiUtil.getPageNum(request) > 0){			
			returnList = monthAttendanceDao.getAnnualUsage(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;						
		}else{
			returnList = monthAttendanceDao.getAnnualUsage(paramMap) ;
		}
		
		return returnList ;
	}
	
	/**
	 * 个人考勤(年假使用现状)总条数
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getAnnualUsageCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		String dataFlag = request.getParameter("seach_DATA_FLAG")!=null?request.getParameter("seach_DATA_FLAG").toString():"";
		
		//第一次进入页面，不进行查询
		if(dataFlag==null || "".equals(dataFlag)){		
			return retrunInt;
		}
		
		String arYear = request.getParameter("seach_AR_YEAR").toString();
		//String arMonth = request.getParameter("seach_AR_MONTH").toString();
		String deptno=request.getParameter("seach_DEPT_NO");
		String key = request.getParameter("seach_KEY").toString();
		
		paramMap.put("AR_YEAR", arYear);
		//paramMap.put("AR_MONTH", arYear+arMonth);
		paramMap.put("DEPT_NO", deptno);
		paramMap.put("KEY", key);
		
		retrunInt = monthAttendanceDao.getAnnualUsageCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	/**
	 * 个人考勤(年假使用现状) 导出EXCEL用
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAnnualUsageExcel(HttpServletRequest request) throws Exception{
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		String dataFlag = request.getParameter("seach_DATA_FLAG")!=null?request.getParameter("seach_DATA_FLAG").toString():"";		
		//第一次进入页面，不进行查询
		if(dataFlag==null || "".equals(dataFlag)){
			return returnList;
		}
		String arYear = request.getParameter("seach_AR_YEAR").toString();
		//String arMonth = request.getParameter("seach_AR_MONTH").toString();
		String deptno=request.getParameter("seach_DEPT_NO");
		String key = request.getParameter("seach_KEY").toString();
		
		paramMap.put("AR_YEAR", arYear);
		//paramMap.put("AR_MONTH", arYear+arMonth);
		paramMap.put("DEPT_NO", deptno);
		paramMap.put("KEY", key);
		
		returnList = monthAttendanceDao.getAnnualUsage(paramMap) ;
		
		return returnList;
	}
	/**
	 * 月考勤监控
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getMonthControlList(HttpServletRequest request){
		
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if("141441".equals(paramMap.get("ITEM1"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM2"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM3"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM4"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM5"))){
			paramMap.put("ITEM6", "141442");
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = monthAttendanceDao.getMonthControlList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = monthAttendanceDao.getMonthControlList(paramMap) ;
		}
		return retrunList;
	}
	/**
	 * 月考勤监控
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getMonthControlListCnt(HttpServletRequest request) throws Exception{
		int retrunInt = 0 ;
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if("141441".equals(paramMap.get("ITEM1"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM2"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM3"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM4"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM5"))){
			paramMap.put("ITEM6", "141442");
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunInt = monthAttendanceDao.getMonthControlListCnt(paramMap) ;
		return retrunInt ;
	}
	
	/**
	 * 获取所有的考勤状态(迟到和早退合到了一起)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getControlItemMap(HttpServletRequest request){
		List list = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		list = monthAttendanceDao.getControlItemList(paramMap) ;
		Map<String, String> map = new HashMap<String, String>();
		for(int i=0;i<list.size();i++){
			Map map1 = (Map) list.get(i);
			if(!"".equals(map1.get("ITEM_NO")) && map1.get("ITEM_NO")!=null && !"".equals(map1.get("SHORT_NAME")) && map1.get("SHORT_NAME")!=null){
			    map.put(map1.get("ITEM_NO").toString(), map1.get("SHORT_NAME").toString());
			}
		}
		return map;
	}
	
	/**
	 * 月考勤查看(个人新)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getMonthWorkNewOne(HttpServletRequest request) throws Exception{
		List list = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String paYear = paramMap.get("paYear")!=null?paramMap.get("paYear").toString():"";
		String paMonth = paramMap.get("paMonth")!=null?paramMap.get("paMonth").toString():"";
		String paTime = paYear+paMonth;
		paramMap.put("paYear", paramMap.get("paYear"));
		paramMap.put("paMonth", paTime);
		//paramMap.put("paTime", paTime);
		paramMap.put("STAT_NO", paramMap.get("STAT_NO"));
		paramMap.put("PERSONId", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//如果没有condition，不进行查询
		if(paramMap.get("condition")==null || "".equals(paramMap.get("condition"))||paramMap.get("STAT_NO")==null || "".equals(paramMap.get("STAT_NO"))){
			return list;
		}
		if(admin.getCpnyId().equals("SST") )
		{
			list = monthAttendanceDao.getMonthWorkLGETANewOne(paramMap) ;
		}
		else{
			list = monthAttendanceDao.getMonthWorkNewOne(paramMap) ;
		}
		
		return list;
		
	}

	@Override
	public List getAnnualUsageFact(HttpServletRequest request) {
		List returnList = new ArrayList() ;
        AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID", "");
		}
		if(paramMap.get("AR_YEAR")==null){
			paramMap.put("AR_YEAR", "");
		}
		if(admin.getCpnyId().equals("TSTO")){
		    returnList = monthAttendanceDao.getAnnualUsageFact(paramMap) ;
		}else{
			returnList = monthAttendanceDao.getAnnualUsageFactLGE(paramMap) ;
		}
		return returnList ;
	}


	@Override
	public int getArAfrimExceptionCnt(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		return monthAttendanceDao.getArAfrimExceptionCnt(paramMap);
	}

	@Override
	public List getArAfrimExceptionList(HttpServletRequest request) throws Exception {
		List returnList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(UiUtil.getPageNum(request)>0){
			returnList = monthAttendanceDao.getArAfrimExceptionList(paramMap,UiUtil.getPageNum(request),UiUtil.getNumPerPage(request));
		}else {
			returnList=monthAttendanceDao.getArAfrimExceptionList(paramMap);
		}
		return returnList;
	}

	@Override
	public int getArExceptionTempCnt(HttpServletRequest request, String errorFlag) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = monthAttendanceDao.getArExceptionTempErrorCnt(paramMap);
		}else{
			retrunInt = monthAttendanceDao.getArExceptionTempCnt(paramMap);
		}

		return retrunInt;
	}

	@Override
	public List getArExceptionTempList(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = monthAttendanceDao.getArExceptionTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = monthAttendanceDao.getArExceptionTempList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 日考勤监控
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDetailControlList(HttpServletRequest request){
		
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if("141441".equals(paramMap.get("ITEM1"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM2"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM3"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM4"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM5"))){
			paramMap.put("ITEM6", "141442");
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = monthAttendanceDao.getDetailControlList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = monthAttendanceDao.getDetailControlList(paramMap) ;
		}
		return retrunList;
	}
	/**
	 * 日考勤监控
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getDetailControlListCnt(HttpServletRequest request) throws Exception{
		int retrunInt = 0 ;
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if("141441".equals(paramMap.get("ITEM1"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM2"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM3"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM4"))){
			paramMap.put("ITEM6", "141442");
		}
		if("141441".equals(paramMap.get("ITEM5"))){
			paramMap.put("ITEM6", "141442");
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunInt = monthAttendanceDao.getDetailControlListCnt(paramMap) ;
		return retrunInt ;
	}

	
	/**
	 * 所有申请查看
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAllApplyList(HttpServletRequest request){
		
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		/*没有开始结束时间默认当天*/
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
		Calendar c = Calendar.getInstance();    
		if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
			//获取今天：
			/*c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH,1);*/
			String first = format.format(c.getTime());
			paramMap.put("START_DATE",first);
			//获取今天：
			c = Calendar.getInstance();  
			/*c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));*/
			String last = format.format(c.getTime());
			paramMap.put("END_DATE",last);
		}
		retrunList = monthAttendanceDao.getAllApplyList(paramMap) ;
		return retrunList;
	}
	/**
	 * 所有申请查看cnt
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getAllApplyListCnt(HttpServletRequest request) throws Exception{
		int retrunInt = 0 ;
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		retrunInt = monthAttendanceDao.getAllApplyListCnt(paramMap) ;
		return retrunInt ;
	}

	/* 
	* Title: getControlItemMap
	* Description:按照申请类型获取考勤项目
	* @author 孙鹏  
	* @date 2015年4月15日 下午3:34:49  
	* @param request
	* @return 
	* @see com.ait.ar.service.MonthAttendanceSer#getControlItemMap(javax.servlet.http.HttpServletRequest) 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List getItemForApplyList(HttpServletRequest request){
		List list = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(!"".equals(paramMap.get("APPLY_TYPE"))&&paramMap.get("APPLY_TYPE")!=null){
			if(paramMap.get("APPLY_TYPE").equals("21")){
				paramMap.put("PARENT_CODE_NO", "21");
			}
			else if(paramMap.get("APPLY_TYPE").equals("31")){
				paramMap.put("PARENT_CODE_NO", "31");
			}
			/*else if(paramMap.get("TB_TYPE").equals("KAOQIN")){
				paramMap.put("PARENT_CODE_NO", "1434");
			}*/
		}else{paramMap.put("PARENT_CODE_NO", "");}
		if(!"".equals(paramMap.get("APPLY_TYPE"))&&paramMap.get("APPLY_TYPE")!=null){
		/*if(paramMap.get("TB_TYPE").equals("KAOQIN")){
			list = monthAttendanceDao.getItemForKaoQinList(paramMap) ;
		}
		else{*/
			list = monthAttendanceDao.getItemForApplyList(paramMap) ;
		//}
		}
		Map<String, String> map = new HashMap<String, String>();
		for(int i=0;i<list.size();i++){
			Map map1 = (Map) list.get(i);
			map.put(map1.get("ITEM_NO").toString(), map1.get("SHORT_NAME").toString());
		}
		return list;
	}
	
	
}
