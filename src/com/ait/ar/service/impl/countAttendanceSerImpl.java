package com.ait.ar.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.countAttendanceDao;
import com.ait.ar.service.countAttendanceSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 *部门员工信息查询(dept employee Info view) Copyright: LDCC Company: LDCC
 * 
 * @fileName: ViewApplySerImpl.java
 * @Description:
 * @Create date: Feb 10, 2012 2:47:58 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 10, 2012 2:47:58 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Service
public class countAttendanceSerImpl implements countAttendanceSer {

	Logger logger = Logger.getLogger(countAttendanceSerImpl.class);

	@Autowired
	private countAttendanceDao ViewDeptPerDao;

	/**
	 * 部门员工信息查询,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");

		// LinkedHashMap paramMap =
		// getLinkedMapByRequestForSearch(request,"seach_");
		// KEY=, GROUP_NO=, DEPT_NO=C025
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		/*
		 * if (UiUtil.getPageNum(request) > 0) { returnList =
		 * ViewDeptPerDao.getPersonList(paramMap, UiUtil.getPageNum(request),
		 * UiUtil.getNumPerPage(request)); } else { returnList =
		 * ViewDeptPerDao.getPersonList(paramMap); }
		 */
		// }
		// }

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			returnList = ViewDeptPerDao.getPersonList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = ViewDeptPerDao.getPersonList(paramMap);
		}
		/*
		 * List list = new ArrayList(); LinkedHashMap lMap = new
		 * LinkedHashMap(); LinkedHashMap searchAnLeave = paramMap;
		 * LinkedHashMap searchSurpLeave = paramMap; for (int i = 0; i <
		 * returnList.size(); i++) { searchAnLeave = (LinkedHashMap)
		 * returnList.get(i); searchAnLeave.put("EMPID",admin.getPersonId());
		 * searchAnLeave.put("PERSON_ID", admin.getPersonId());
		 * 
		 * Date date = new Date(); SimpleDateFormat sb = new
		 * SimpleDateFormat("yyyyMM"); int dateInt =
		 * Integer.parseInt(sb.format(date)); String yearAndMonth =
		 * String.valueOf(dateInt); String year = yearAndMonth.substring(0, 4);
		 * 
		 * //lMap.put("REST_ANNUAL_LEAVE",
		 * this.retrieveVacationEmpREST(searchAnLeave, admin)); //2013-12-27
		 * lufeng 修改 //2014/08/11 其中函数报错，未知是否需要查询剩余年假，先注释了 by:wangqiang
		 * lMap.put("REST_ANNUAL_LEAVE", this.getVacationEmpREST(searchAnLeave,
		 * admin)); lMap.put("REST_ANNUAL_LEAVEQN",
		 * this.retrieveVacationEmpRESTQN(searchAnLeave, admin));
		 * searchAnLeave.put("YEAR_NONTH", yearAndMonth);
		 * searchAnLeave.put("YEAR", year);
		 * searchAnLeave.put("CPNY_ID",admin.getCpnyId());
		 * searchAnLeave.put("PERSON_ID", paramMap.get("PERSON_ID"));
		 * //lMap.put("SUR_ADJUST_REST",
		 * this.ViewDeptPerDao.getSurplusAdjustRest(searchSurpLeave));
		 * //2013-12-11 lufeng 修改 //2014/08/11 其中函数报错，未知是否需要查询剩余年假，先注释了
		 * by:wangqiang lMap.put("SUR_ADJUST_REST",
		 * this.ViewDeptPerDao.getAdjustRestNew(searchSurpLeave));
		 * 
		 * list.add(searchAnLeave); }
		 */
		return returnList;

	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List getPersonListOt(HttpServletRequest request) { List
	 * retrunList = new ArrayList() ; AdminBean admin =
	 * SessionUtil.getLoginUserFromSession(request); Map paramMap =
	 * ObjectBindUtil.getRequestParamData(request) ; paramMap.put("CPNY_ID",
	 * admin.getCpnyId()); paramMap.put("specialParam",admin.getSpecialParam());
	 * paramMap.put("deptNo",admin.getDeptNo()); paramMap.put("userNo",
	 * admin.getUserNo()); paramMap.put("ADMIN_ID", admin.getPersonId()); String
	 * empNameStr=request.getParameter("LOCAL_NAME"); try {
	 * paramMap.put("LOCAL_NAME",
	 * java.net.URLDecoder.decode(empNameStr,"UTF-8")); } catch
	 * (UnsupportedEncodingException e) { e.printStackTrace(); } if
	 * (UiUtil.getPageNum(request) > 0){ retrunList =null ; }else{ retrunList=
	 * ViewDeptPerDao.getPersonListOt(paramMap) ; } return retrunList ; }
	 */
	/**
	 * 分页单位(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPersonListCnt(HttpServletRequest request) throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.getPersonListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	/**
	 * 通过request请求封装查询条件(get search conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequestForSearch(
			HttpServletRequest request, String flag) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				flag);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}

	/**
	 * 通过request请求封装查询条件 manage(get search conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequestForManageSearch(
			HttpServletRequest request, String flag) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				flag);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return paramMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("EMPID", request.getParameter("EMPID"));
		return this.ViewDeptPerDao.getPersonInfo(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonInfo2(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("EMPID", request.getParameter("EMPID"));
		return this.ViewDeptPerDao.getPersonInfo2(paramMap);
	}

	/**
	 * 查看动态组列表(get DynamicGroup List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getDynamicGroup1List(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("DEPT_DISTINGUISH_NO", "1");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = ViewDeptPerDao.getDynamicGroupList(paramMap);
		return retrunList;
	}

	/**
	 * 单个考勤明细(get DynamicGroup List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List viewArPersonalSingleList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CODE_NO", request.getParameter("CODE_NO"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		System.out.println("444444444444444444444"
				+ request.getParameter("STIMESS"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = ViewDeptPerDao.viewArPersonalSingleList(paramMap);
		return retrunList;
	}

	/**
	 *单个待条件加班明细(get DynamicGroup List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List viewOtApplySingleList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CODE_NO", request.getParameter("CODE_NO"));

		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = ViewDeptPerDao.viewOtApplySingleList(paramMap);
		return retrunList;
	}

	/**
	 *个人考勤现况,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewArPersonalList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);

		}

		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("ETIME", sysdate);

		}

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			returnList = ViewDeptPerDao.viewArPersonalList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = ViewDeptPerDao.viewArPersonalList(paramMap);
		}
		return returnList;

	}

	/**
	 *动态列,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAutoColumnList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		returnList = ViewDeptPerDao.viewAutoColumnList(paramMap);

		return returnList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public int viewArPersonalListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);

		}

		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("ETIME", sysdate);

		}
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.viewArPersonalListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	// end

	@SuppressWarnings("unchecked")
	@Override
	public int viewOtApplyPersonalListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("FROM_DATE", first);

		}

		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_ETIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("TO_DATE", sysdate);

		}
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.viewOtApplyPersonalListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	// //end

	/**
	 *旷工查询,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAbsenteeismInfoList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			returnList = ViewDeptPerDao.viewAbsenteeismInfoList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = ViewDeptPerDao.viewAbsenteeismInfoList(paramMap);
		}
		return returnList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public int viewAbsenteeismInfoListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.viewAbsenteeismInfoListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	// //end

	/**
	 *考勤出入数据查询,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewEntryInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			returnList = ViewDeptPerDao.viewEntryInfoList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = ViewDeptPerDao.viewEntryInfoList(paramMap);
		}
		return returnList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public int viewEntryInfoListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.viewEntryInfoListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	// //end

	/**
	 *manage下的部门员工任职经历 ,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List ManageEmpPositionInfoList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			returnList = ViewDeptPerDao.ManageEmpPositionInfoList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = ViewDeptPerDao.ManageEmpPositionInfoList(paramMap);
		}
		return returnList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public int ManageEmpPositionInfoListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForManageSearch(
				request, "seach_");
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.ManageEmpPositionInfoListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	// //end

	/**
	 *manage下的部门员工任职经历 ,个人
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List ManageEmpPositionSinglList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("interLanguage", admin.getLanguage());

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.ManageEmpPositionSinglList(paramMap);

		return returnList;

	}

	/**
	 *manage下的部门员工任职经历 ,个人
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEmpInfoById(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		// 重新配置权限 manage

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("interLanguage", admin.getLanguage());

		return ViewDeptPerDao.getEmpInfoById(paramMap);

	}

	// //end

	// /////////公司日历班次////////start/////////////////////////////

	/*
	 * @author xuehaifei
	 * 
	 * 班次日历 2014-7-9
	 */
	@SuppressWarnings("unchecked")
	public String getCompanyCalendarViewHtml(HttpServletRequest request) {
		String actionType = ObjectUtils.toString(request
				.getParameter("actionType"));

		List calendarList = this.getArClassCalendarList(request);
		if (calendarList.size() == 0) {
			return "";
		}
		String temp = this.getFrist(request, calendarList, actionType);

		int out = Integer.parseInt(temp.substring(temp.lastIndexOf("*") + 1,
				temp.length()));
		String frist = temp.substring(0, temp.lastIndexOf("*"));
		String Default = this
				.getDefault(request, out, calendarList, actionType);
		return frist + Default;
	}

	/**
	 * 查看班次日历(get EmpCalendar List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getArClassCalendarList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyid = admin.getCpnyId();
		String arMonth = "";
		String year = ObjectUtils.toString(request.getParameter("year"));
		String month = ObjectUtils.toString(request.getParameter("month"));
		String group_id = ObjectUtils.toString(request.getParameter("GROUP"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (group_id == null || group_id.length() == 0) {
			// group_id=ViewDeptPerDao.getDefaultGroup(paramMap);
			group_id = "400224";
		}

		// String STAT_NO=group_id.equals("CH_W2")?"219948":"141436";
		if (year == null || year.length() == 0) {
			Calendar calendar = Calendar.getInstance();
			year = new java.text.SimpleDateFormat("yyyy").format(calendar
					.getTime());
			month = new java.text.SimpleDateFormat("MM").format(calendar
					.getTime());
		}
		arMonth = year + month;

		HttpSession session = request.getSession();
		// AdminBean user = SessionUtil.getLoginUserFromSession(request);

		paramMap.put("AR_MONTH", arMonth);
		paramMap.put("GROUP_ID", group_id);
		paramMap.put("interLanguage", admin.getLanguage());
		// paramMap.put("STAT_NO", STAT_NO);

		retrunList = ViewDeptPerDao.getArClassCalendarList(paramMap);
		/*
		 * int countlist=retrunList.size(); if (countlist==0){ retrunList =
		 * ViewDeptPerDao.getArClassCalendarListGs(paramMap); }
		 */
		return retrunList;
	}

	/**
	 * 得到第1天是星期几(get )
	 * 
	 * @param List
	 * @param String
	 * @return String
	 * @throws
	 */
	private String getFrist(HttpServletRequest request, List calendarList,
			String actionType) {

		String frist = "";
		int out = 0;

		if (calendarList.size() > 0) {
			// 得到第1天是星期几
			LinkedHashMap calendarMap0 = (LinkedHashMap) calendarList.get(0);
			out = 7 - NumberUtils.parseNumber(calendarMap0.get("IWEEK")
					.toString(), Integer.class);

			for (int i = 0; i < 7 - out; ++i) {
				frist += "<td ><div></div></td>";
			}

			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap paramMap = new LinkedHashMap();
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("interLanguage", admin.getLanguage());

			// paramMap.put("DEPT_DISTINGUISH_NO", "1");
			paramMap.put("PERSON_ID", admin.getPersonId());

			for (int i = 0; i < out; i++) {
				LinkedHashMap calendarMap = (LinkedHashMap) calendarList.get(i);

				frist += this.createViewCalendarHtml(paramMap, calendarMap,
						actionType);
			}
		}

		return "<tr height=\"60px\">" + frist + "</tr>*" + out;
	}

	/**
	 * 动态拼装个人日历(create ViewCalendar Html)
	 * 
	 * @param LinkedHashMap
	 * @param String
	 * @return String
	 * @throws
	 */
	private String createViewCalendarHtml(LinkedHashMap paramMap,
			LinkedHashMap calendarMap, String actionType) {

		List shiftList = (List) ViewDeptPerDao.getShiftList(paramMap);// 获取班次

		boolean flag = false;
		if (!ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO")).equals(
				"")) {
			flag = true;
		}

		String calendarHtml = "";

		calendarHtml += "<td>";
		calendarHtml += "<div><p>";

		calendarHtml += "<span class=\"day_input\"><input name=\"day\" value=\""
				+ calendarMap.get("IDAY").toString()
				+ "\" type= \"checkbox\"  "
				+ "\" title= \""
				+ calendarMap.get("DDATE_STR").toString() + "\" /></span>";

		calendarHtml += "<b>"
				+ this.getDayColor(NumberUtils.parseNumber(calendarMap.get(
						"IDAY").toString(), Integer.class), NumberUtils
						.parseNumber(calendarMap.get("IWEEK").toString(),
								Integer.class)) + "</b><span class=\"onwork\">";
		// String type = flag ?
		// "&nbsp;&nbsp<font color=\"red\">休</font>&nbsp;&nbsp;班"
		// : "&nbsp;&nbsp工&nbsp;&nbsp;班";
		String language = "zh";
		if (paramMap.get("interLanguage") != null
				&& !"".equals(paramMap.get("interLanguage").toString())) {
			language = paramMap.get("interLanguage").toString();
		}

		String type = "";
		if (!ObjectUtils.toString(calendarMap.get("TYPEID")).equals("")
				&& ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
						"1441")) {
			// 公班
			/*
			 * if(flag){ type =
			 * "<b><font style=\"color:red\">"+TipMessage.getTipMessage
			 * ("ar.viewempcalender.title.gongxiu"
			 * ,language)+"&nbsp;</font><font style=\"color:red\">"
			 * +TipMessage.getTipMessage
			 * ("ar.viewempcalender.title.ban",language)+"</font></b>"; }else{
			 */
			type = "<b><font style=\"color:red\">"
					+ TipMessage.getTipMessage(
							"ar.viewempcalender.title.gongxiu", language)
					+ "&nbsp;"
					+ TipMessage.getTipMessage("ar.viewempcalender.title.rest",
							language) + "</font></b>";
			// }

		} else if (!ObjectUtils.toString(calendarMap.get("TYPEID")).equals("")
				&& ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
						"1442")) {
			/*
			 * if(flag){ //节班 type =
			 * "<b><font style=\"color:red\">"+TipMessage.getTipMessage
			 * ("ar.viewempcalender.title.jie",language)
			 * +"&nbsp;</font><font style=\"color:red\">"
			 * +TipMessage.getTipMessage
			 * ("ar.viewempcalender.title.ban",language)+"</font></b>"; }else{
			 */
			type = "<b><font style=\"color:red\">"
					+ TipMessage.getTipMessage("ar.viewempcalender.title.jie",
							language)
					+ "&nbsp;"
					+ TipMessage.getTipMessage("ar.viewempcalender.title.rest",
							language) + "</font></b>";
			// }

		} else {
			// 工班
			/*
			 * if(flag){ type =
			 * "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong"
			 * ,language)+"&nbsp;</font><font style=\"color:red\">"+TipMessage.
			 * getTipMessage
			 * ("ar.viewempcalender.title.ban",language)+"</font></b>"; }else{
			 */
			type = "<b><font>"
					+ TipMessage.getTipMessage("ar.viewempcalender.title.gong",
							language)
					+ "&nbsp;</font>"
					+ TipMessage.getTipMessage("ar.viewempcalender.title.ban",
							language) + "</b>";
			// }
		}

		calendarHtml += type + "</span></p>";
		calendarHtml += "<h4>";

		calendarHtml += "<select name=\"SHIFT_NO_\"  "
				+ calendarMap.get("IDAY").toString() + "\" id=\"SHIFT_NO_"
				+ calendarMap.get("IDAY").toString() + "\">";
		for (int i = 0; i < shiftList.size(); i++) {
			LinkedHashMap map = (LinkedHashMap) shiftList.get(i);
			String temp = "";
			String tempShitNo = "";

			if (flag) {
				tempShitNo = calendarMap.get("SCHEDULE_SHIFT_NO") != null ? calendarMap
						.get("SCHEDULE_SHIFT_NO").toString()
						: "";
			} else {
				tempShitNo = calendarMap.get("SHIFT_NO") != null ? calendarMap
						.get("SHIFT_NO").toString() : "";
			}
			if (tempShitNo.equals(map.get("SHIFT_NO").toString())) {
				temp = "selected";
			}
			calendarHtml += "<option value=\"" + map.get("SHIFT_NO").toString()
					+ "\"" + temp + ">";
			calendarHtml += map.get("SHIFT_NAME").toString() + "</option>";
		}

		calendarHtml += "</h4>";
		calendarHtml += "</div></td>";

		// System.out.println("--------------------------------"+calendarHtml);

		return calendarHtml;
	}

	private String getDayColor(int day, int week) {
		String daycolor = null;
		switch (week) {
		case 0:
			daycolor = "<span>" + day + "</span>";
			break;
		case 1:
			daycolor = Integer.toString(day);
			break;
		case 2:
			daycolor = Integer.toString(day);
			break;
		case 3:
			daycolor = Integer.toString(day);
			break;
		case 4:
			daycolor = Integer.toString(day);
			break;
		case 5:
			daycolor = Integer.toString(day);
			break;
		case 6:
			daycolor = "<span>" + day + "</span>";
			break;
		}
		return daycolor;
	}

	private String getWorkName(int i, String language) {
		if (language != null && language.equals("zh")) {
			if (i == 0)
				return "<span class=\"nowork\"><b>休息</b></span>";
			else
				return "<span class=\"onwork\"><b>工作</b></span>";
		} else if (language != null && language.equals("ko")) {
			if (i == 0)
				return "<span class=\"nowork\"><b>休息</b></span>";
			else
				return "<span class=\"onwork\"><b>工作</b></span>";
		} else if (language != null && language.equals("vi")) {
			if (i == 0)
				return "<span class=\"nowork\"><b>休息</b></span>";
			else
				return "<span class=\"onwork\"><b>工作</b></span>";
		} else {
			if (i == 0)
				return "<span class=\"nowork\"><b>Rest Day</b></span>";
			else
				return "<span class=\"onwork\"><b>Work Day</b></span>";
		}

	}

	/**
	 * 取默认日历(get Default)
	 * 
	 * @param int
	 * @param List
	 * @param String
	 * @return String
	 * @throws
	 */
	private String getDefault(HttpServletRequest request, int out,
			List calendarList, String actionType) {

		int r = 0;
		int rows = 0;
		if ((calendarList.size() - out) % 7 != 0) {// 算出 剩余的有几行
			rows = (calendarList.size() - out) / 7 + 1;
		} else {
			rows = (calendarList.size() - out) / 7;
		}
		rows = rows * 7;// 总共多少格子
		rows = rows - (calendarList.size() - out);// 到最后一行剩余几格子
		String Default = "";

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());

		// paramMap.put("DEPT_DISTINGUISH_NO", "1");
		paramMap.put("PERSON_ID", admin.getPersonId());

		for (int i = out; i < calendarList.size(); i++) {
			LinkedHashMap calendarMap = (LinkedHashMap) calendarList.get(i);
			if (r == 0 || r == 14 || r == 28) {// 加换行
				Default += "<tr   height=\"60px\"   style=\"background:#FFFFCC\">";
			}
			if (r == 7 || r == 21) {
				Default += "<tr height=\"60px\">";
			}
			// ---------------------------------------------
			List shiftList = (List) ViewDeptPerDao.getShiftList(paramMap);// 获取班次

			boolean flag = false;
			if (!ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO"))
					.equals("")) {
				flag = true;
			}

			String calendarHtml = "";

			calendarHtml += "<td>";
			calendarHtml += "<div><p>";

			calendarHtml += "<span class=\"day_input\"><input name=\"day\" value=\""
					+ calendarMap.get("IDAY").toString()
					+ "\" type= \"checkbox\""
					+ "\" title= \""
					+ calendarMap.get("DDATE_STR").toString() + "\" /></span>";

			calendarHtml += "<b>"
					+ this.getDayColor(NumberUtils.parseNumber(calendarMap.get(
							"IDAY").toString(), Integer.class), NumberUtils
							.parseNumber(calendarMap.get("IWEEK").toString(),
									Integer.class))
					+ "</b><span class=\"onwork\">";
			// String type = flag ?
			// "&nbsp;&nbsp<font color=\"red\">休</font>&nbsp;&nbsp;班"
			// : "&nbsp;&nbsp工&nbsp;&nbsp;班";
			String language = "zh";
			if (paramMap.get("interLanguage") != null
					&& !"".equals(paramMap.get("interLanguage").toString())) {
				language = paramMap.get("interLanguage").toString();
			}

			String type = "";
			if (!ObjectUtils.toString(calendarMap.get("TYPEID")).equals("")
					&& ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
							"1441")) {
				// 公班
				if (flag) {
					type = "<b><font style=\"color:red\">"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.gongxiu",
									language)
							+ "&nbsp;</font><font style=\"color:red\">"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.rest", language)
							+ "</font></b>";
				} else {
					type = "<b><font style=\"color:red\">"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.gongxiu",
									language)
							+ "&nbsp;</font>"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.ban", language)
							+ "</b>";
				}

			} else if (!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
					"")
					&& ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
							"1442")) {
				if (flag) {
					// 节班
					type = "<b><font style=\"color:red\">"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.jie", language)
							+ "&nbsp;</font><font style=\"color:red\">"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.rest", language)
							+ "</font></b>";
				} else {
					type = "<b><font style=\"color:red\">"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.jie", language)
							+ "&nbsp;</font>"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.ban", language)
							+ "</b>";
				}

			} else {
				// 工班
				if (flag) {
					type = "<b><font>"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.gong", language)
							+ "&nbsp;</font>"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.ban", language)
							+ "</b>";
				} else {
					type = "<b><font>"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.gong", language)
							+ "&nbsp;</font>"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.ban", language)
							+ "</b>";
				}
			}

			calendarHtml += type + "</span></p>";
			calendarHtml += "<h4>";

			if (r == 0 || r == 2 || r == 1 || r == 3 || r == 4 || r == 5
					|| r == 6 || r == 14 || r == 15 || r == 16 || r == 17
					|| r == 18 || r == 19 || r == 20 || r == 28 || r == 29
					|| r == 30 || r == 31 || r == 32 || r == 33 || r == 34
					|| r == 35) {// 加换行
				calendarHtml += "<select name=\"SHIFT_NO_\" style=\"background:#FFFFCC\" "
						+ calendarMap.get("IDAY").toString()
						+ "\" id=\"SHIFT_NO_"
						+ calendarMap.get("IDAY").toString() + "\">";
			}

			if (r == 7 || r == 8 || r == 9 || r == 10 || r == 11 || r == 12
					|| r == 13 || r == 21 || r == 22 || r == 23 || r == 24
					|| r == 25 || r == 26 || r == 27) {
				calendarHtml += "<select name=\"SHIFT_NO_\"  "
						+ calendarMap.get("IDAY").toString()
						+ "\" id=\"SHIFT_NO_"
						+ calendarMap.get("IDAY").toString() + "\">";
			}

			for (int j = 0; j < shiftList.size(); j++) {
				LinkedHashMap map = (LinkedHashMap) shiftList.get(j);
				String temp = "";
				String tempShitNo = "";

				if (flag) {
					tempShitNo = calendarMap.get("SCHEDULE_SHIFT_NO") != null ? calendarMap
							.get("SCHEDULE_SHIFT_NO").toString()
							: "";
				} else {
					tempShitNo = calendarMap.get("SHIFT_NO") != null ? calendarMap
							.get("SHIFT_NO").toString()
							: "";
				}
				if (tempShitNo.equals(map.get("SHIFT_NO").toString())) {
					temp = "selected";
				}
				calendarHtml += "<option value=\""
						+ map.get("SHIFT_NO").toString() + "\"" + temp + ">";
				calendarHtml += map.get("SHIFT_NAME").toString() + "</option>";
			}

			calendarHtml += "</h4>";
			calendarHtml += "</div></td>";
			// -----------------------------------------------
			// Default += this.createViewCalendarHtml(paramMap, calendarMap,
			// actionType);
			Default += calendarHtml;
			if (r == 6 || r == 13 || r == 20 || r == 27 || r == 34) {// 加换行
				Default += "</tr>";
			}
			r += 1;
		}
		// 补空格
		if (rows > 0) {
			String temp = "";
			for (int i = 0; i < rows; i++) {
				temp += "<td border=\"2\"><div></div></td>";
			}
			Default += temp + "</tr>";
		}
		// System.out.println("---------------------整个样式-------------------------------"+Default);
		return Default;
	}

	// ///公司日历 end

	/**
	 * manage count
	 */
	/**
	 *manage下年龄统计,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List manageAgeCountList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.manageAgeCountList(paramMap);

		return returnList;

	}

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeAgeList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("managePart", admin.getDeptNo());
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		List list = new ArrayList();

		list = ViewDeptPerDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));

					// key为 部门ID value=查询结果LIST

					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao
							.getAgeListByDeptNo(parMap));

				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	// /年龄end

	/**
	 * manage count
	 */
	/**
	 *manage下职级统计,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List manageGradeCountList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.manageAgeCountList(paramMap);

		return returnList;

	}

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeGradeList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("managePart", admin.getDeptNo());
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		List list = new ArrayList();

		list = ViewDeptPerDao.getAllDept(paramMap);

		// test

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {// 遍历部门
					// 不为空的传入递归
					// 得到LIST
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));

					// key为 部门ID value=查询结果LIST

					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao
							.getGradeListByDeptNo(parMap));

				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	// //职级END

	/**
	 * manage count
	 */
	/**
	 *manage下职务统计,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List managePositionCountList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.manageAgeCountList(paramMap);

		return returnList;

	}

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodePositionList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("managePart", admin.getDeptNo());
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		List list = new ArrayList();

		list = ViewDeptPerDao.getAllDeptDif(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门区分DEPT_DISTINGUISH_STANDARD
					parMap.put("DEPT_DISTINGUISH_STANDARD", dept_id
							.get("DEPT_DISTINGUISH_STANDARD"));

					// key为 部门ID value=查询结果LIST

					lMap2.put(dept_id.get("DEPT_DISTINGUISH_STANDARD"),
							ViewDeptPerDao.getPositionListByDeptNo(parMap));

				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeDifList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	// //职务end

	/**
	 * manage count
	 */
	/**
	 *manage下学历统计,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List manageEduCountList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.manageAgeCountList(paramMap);

		return returnList;

	}

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeEduList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map lMap3 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("managePart", admin.getDeptNo());
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		List list = new ArrayList();

		list = ViewDeptPerDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao
							.getEduListByDeptNo(parMap));
					lMap3.put(dept_id.get("DEPTNO"), ViewDeptPerDao
							.getSexListByDeptNo(parMap));

				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		lMap.put("deptListCount3", lMap3);
		return lMap;
	}

	// /学历end

	/**
	 * manage count
	 */
	/**
	 *manage下职员类型统计,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List manageEmpTypeCountList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.manageAgeCountList(paramMap);

		return returnList;

	}

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeEmpTypeList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("managePart", admin.getDeptNo());
		// 获取选定月份最后一天
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;

		List list = new ArrayList();

		list = ViewDeptPerDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao
							.getEmpTypeListByDeptNo(parMap));
				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	// //职员类型end

	// //一月中最大天数 若是时间为空 默认为本月最大天数
	public String getMonthLastDay(String month) {
		Calendar a = Calendar.getInstance();

		if (month != null && month != "") {
			String dates[] = month.split("/");
			int year = Integer.parseInt(dates[0]);
			int months = Integer.parseInt(dates[1]);
			int day = 1;
			Calendar cal = Calendar.getInstance();
			cal.set(year, months - 1, day);
			int last = cal.getActualMaximum(Calendar.DATE);
			String date = year + "." + months + "." + last;
			return date;

		} else {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
			Calendar ca = Calendar.getInstance();

			String last = format.format(ca.getTime());

			return last;

		}
	}

	// /MANAGE人员类型条件
	public Map changeCode(Map paramMap) {
		String codeType = "";
		int j = 0;
		for (int i = 0; i < 5; i++) {
			String code = (String) paramMap.get("EMP_TYPE_CODE" + i);
			if (code != null && !code.equals("")) {
				j++;
				if (j == 1) {
					codeType = "'" + code + "'";

				} else {
					codeType += ",'" + code + "'";
				}

			}
		}

		paramMap.put("EMP_TYPE_CODE", codeType);

		return paramMap;

	}

	/**
	 * 月别采用现状
	 */

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeMonthList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("managePart", admin.getDeptNo());
		// 默认为当前的一天
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("date")));
		// 若 页面条件 为空 获取当前年份
		Calendar a = Calendar.getInstance();
		if (paramMap.get("YEAR") == null || paramMap.get("YEAR") == "") {

			paramMap.put("YEAR", a.get(Calendar.YEAR));

		}
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;

		List list = new ArrayList();

		list = ViewDeptPerDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao
							.getMonthListByDeptNo(parMap));
				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	/**
	 * 月别退职现状
	 */
	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeMonthResignList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("managePart", admin.getDeptNo());
		// 默认为当前的一天
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("date")));
		// 若 页面条件 为空 获取当前年份
		Calendar a = Calendar.getInstance();
		if (paramMap.get("YEAR") == null || paramMap.get("YEAR") == "") {

			paramMap.put("YEAR", a.get(Calendar.YEAR));

		}
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;

		List list = new ArrayList();

		list = ViewDeptPerDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao
							.getMonthResignListByDeptNo(parMap));
				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	/**
	 *个人考勤现况,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List arCountInfoSonList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if (request.getParameter("seach_STIME") == "" || request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());
			paramMap.put("STIME", first);
		}
		if (request.getParameter("seach_ETIME") == "" || request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
			paramMap.put("ETIME", sysdate);
		}
		if(request.getParameter("seach_DEPT_NO")==null||request.getParameter("seach_DEPT_NO")==""){
			paramMap.put("DEPT_NO", admin.getDeptNo().substring(admin.getDeptNo().lastIndexOf("_")+1));
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		String report = request.getParameter("report");
		if (report != null && !report.equals("")) {
			returnList = ViewDeptPerDao.viewArPersonalReport(paramMap);
		} else {
			returnList = ViewDeptPerDao.viewArPersonalList(paramMap);
		}
		return returnList;
	}

	/**
	 *个人加班现况,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewOtApplyPersonalList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if (request.getParameter("seach_STIME") == "" || request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());
			paramMap.put("STIME", first);
		}
		if (request.getParameter("seach_ETIME") == "" || request.getParameter("seach_ETIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
			paramMap.put("ETIME", sysdate);
		}
		if(request.getParameter("seach_DEPT_NO")==null||request.getParameter("seach_DEPT_NO")==""){
			paramMap.put("DEPT_NO", admin.getDeptNo().substring(admin.getDeptNo().lastIndexOf("_")+1));
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		returnList = ViewDeptPerDao.viewOtApplyPersonalList(paramMap);
		return returnList;
	}
	
	/**
	 *个人年加班现况,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewOtYearPersonalList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if (request.getParameter("seach_STIME") == "" || request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());
			paramMap.put("STIME", first);
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		returnList = ViewDeptPerDao.viewOtYearPersonalList(paramMap);
		return returnList;
	}

	/**
	 * 部门现状
	 */
	/**
	 * 考勤
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap arForDeptCountInfoSonArList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		// 添加条件
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		String[] str = request.getParameterValues("EMP_TYPE_CODE");
		Map parMap = paramMap;
		if(paramMap.get("managePart")==null){
			paramMap.put("managePart", admin.getDeptNo().substring(admin.getDeptNo().lastIndexOf("_")+1));
		}
		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);

		}

		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("ETIME", sysdate);

		}
		List list = new ArrayList();
		list = ViewDeptPerDao.getAllDept(paramMap);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao.getDeptArListByDeptNo(parMap));
				}
			}
		}
		lMap.put("deptList", ViewDeptPerDao.getParentCodeDeptList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}
	
	
	/**
	 * 医疗期天数统计
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap arForMedicalCountInfoList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		// 添加条件
		Map paramMap = this.getSomeSearch(request);
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		if(paramMap.get("managePart")==null){
			paramMap.put("managePart", admin.getCpnyId().substring(admin.getCpnyId().lastIndexOf("_")+1));
		}
		List list = new ArrayList();
		list = ViewDeptPerDao.getAllDept(paramMap);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao.getDeptArListByDeptNo(parMap));
				}
			}
		}
		lMap.put("deptList", ViewDeptPerDao.getParentCodeDeptList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	/**
	 * 加班
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap arForDeptCountInfoSonOtList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		// 添加条件
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		Map parMap = paramMap;
		if(paramMap.get("managePart")==null){
			paramMap.put("managePart", admin.getCpnyId().substring(admin.getCpnyId().lastIndexOf("_")+1));
		}
		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);

		}

		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("ETIME", sysdate);

		}
		List list = new ArrayList();

		list = ViewDeptPerDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao.getDeptOtListByDeptNo(parMap));
				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeDeptList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	/**
	 * 日期现状
	 */
	/**
	 * 考勤
	 */
	@SuppressWarnings("unchecked")
	public List arForDateCountInfoSonArList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);

		}

		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("ETIME", sysdate);

		}

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if(request.getParameter("seach_DEPT_NO")==null||request.getParameter("seach_DEPT_NO")==""){
			paramMap.put("DEPT_NO", admin.getDeptNo().substring(admin.getDeptNo().lastIndexOf("_")+1));
		}
		returnList = ViewDeptPerDao.getDateArListByDeptNo(paramMap);

		return returnList;
	}

	/**
	 * 日期现状 加班
	 */
	@SuppressWarnings("unchecked")
	public List arForDateCountInfoSonOtList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);
		}
		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_ETIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
			paramMap.put("ETIME", sysdate);
		}
		if(request.getParameter("seach_DEPT_NO")==null||request.getParameter("seach_DEPT_NO")==""){
			paramMap.put("DEPT_NO", admin.getDeptNo().substring(admin.getDeptNo().lastIndexOf("_")+1));
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		returnList = ViewDeptPerDao.getDateOtListByDeptNo(paramMap);
		return returnList;

	}

	/**
	 * 职级现状 考勤
	 */
	@SuppressWarnings("unchecked")
	public List arForGradeCountInfoSonArList(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());
			paramMap.put("STIME", first);
		}
		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
			paramMap.put("ETIME", sysdate);
		}

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if(request.getParameter("seach_DEPT_NO")==null||request.getParameter("seach_DEPT_NO")==""){
			paramMap.put("DEPT_NO", admin.getCpnyId().substring(admin.getCpnyId().lastIndexOf("_")+1));
		}
		returnList = ViewDeptPerDao.getGradeArListByDeptNo(paramMap);

		return returnList;
	}

	/**
	 * 职级现状加班
	 */
	@SuppressWarnings("unchecked")
	public List arForGradeCountInfoSonOtList(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());
			paramMap.put("STIME", first);
		}
		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
			paramMap.put("ETIME", sysdate);
		}
		if(request.getParameter("seach_DEPT_NO")==null||request.getParameter("seach_DEPT_NO")==""){
			paramMap.put("DEPT_NO", admin.getCpnyId().substring(admin.getCpnyId().lastIndexOf("_")+1));
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.getGradeOtListByDeptNo(paramMap);

		return returnList;

	}

	/**
	 * 业务现状考勤
	 */
	@SuppressWarnings("unchecked")
	public List arForPositionCountInfoSonArList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());
			paramMap.put("STIME", first);
		}
		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
			paramMap.put("ETIME", sysdate);
		}
		if(request.getParameter("seach_DEPT_NO")==null||request.getParameter("seach_DEPT_NO")==""){
			paramMap.put("DEPT_NO", admin.getCpnyId().substring(admin.getCpnyId().lastIndexOf("_")+1));
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		returnList = ViewDeptPerDao.getPositionArListByDeptNo(paramMap);
		return returnList;
	}

	/**
	 * 业务现状加班
	 */
	@SuppressWarnings("unchecked")
	public List arForPositionCountInfoSonOtList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());
			paramMap.put("STIME", first);
		}
		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
			paramMap.put("ETIME", sysdate);
		}
		if(request.getParameter("seach_DEPT_NO")==null||request.getParameter("seach_DEPT_NO")==""){
			paramMap.put("DEPT_NO", admin.getCpnyId().substring(admin.getCpnyId().lastIndexOf("_")+1));
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.getPositionOtListByDeptNo(paramMap);

		return returnList;

	}

	public Map getSomeSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		// 时间默认为本月第一天和当前日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);

		}

		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("ETIME", sysdate);

		}
		// 一个树起始参数7

		paramMap = this.changeCode(paramMap);
		return paramMap;

	}

}