package com.ait.report.ar.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jofc2.OFCException;
import jofc2.model.Chart;
import jofc2.model.axis.Label;
import jofc2.model.axis.XAxis;
import jofc2.model.axis.YAxis;
import jofc2.model.elements.LineChart;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ait.report.ar.dao.ArReportDao;
import com.ait.report.ar.service.ArReportSer;
import com.ait.report.hr.dao.HrReportDao;
import com.ait.report.hr.service.HrReportSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArReportSerImpl.java
 * @Description: implement Class ArReportSerImpl.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
@Service
public class ArReportSerImpl implements ArReportSer {
	Logger logger = Logger.getLogger(ArReportSerImpl.class);
	@Autowired
	private ArReportDao arReportDao;

	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(HttpServletRequest request,
			String parent_code_no) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PARENT_CODE_NO", parent_code_no);
		paramMap.put("CPNY", admin.getCpnyId());
		returnList = arReportDao.getCodeListByParentCode(paramMap);

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getArCodeNameByCode(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		returnList = arReportDao.getArCodeNameByCode(paramMap);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getreportList(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		paramMap.put("CPNY", admin.getCpnyId());
		returnList = arReportDao.getreportList(paramMap);

		return returnList;
	}

	/**
	 * 按部门查出员工考勤 导出excel用
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getArDeptShiftList(Map paramMap) {

		List returnList = arReportDao.getArDeptShiftList(paramMap);

		return returnList;
	}

	/**
	 * 查找公司日历排班
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getArCompanyCalendarShiftList(Map paramMap) {
		List returnList = arReportDao.getArCompanyCalendarShiftList(paramMap);

		return returnList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ait.report.ar.service.ArReportSer#getCodeListByParentCode(javax.servlet.http.HttpServletRequest,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List getHrPersonFormalList(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		String WM_FLAG = request.getParameter("WM_FLAG");// 判断是周还是月

		if (WM_FLAG.equals("1")) {
			String FROM_DATE = request.getParameter("seach_FROM_DATE");
			String TO_DATE = request.getParameter("seach_TO_DATE");
			String FROM_DATES = this.getMonthLastDay(FROM_DATE);
			String TO_DATES = this.getMonthLastDay(TO_DATE);
			String FROM_DATE_F = this.getMonthFirstDay(FROM_DATE);
			String TO_DATE_F = this.getMonthFirstDay(TO_DATE);
			paramMap.put("FROM_DATE", FROM_DATES);// 之前月的最后一天
			paramMap.put("TO_DATE", TO_DATES);// 当前月的最后一天 /若是现在的月份则是今天
			paramMap.put("FROM_DATE_F", FROM_DATE_F);// 之前月的第一天
			paramMap.put("TO_DATE_F", TO_DATE_F);// 当前前月的第一天

			returnList = arReportDao.getHrPersonFormalList(paramMap);
		} else {

			String TO_DATE = request.getParameter("seach_TO_DATE_W");

			String TO_DATES = this.getWeekLastDay(TO_DATE);

			paramMap.put("FROM_DATE", TO_DATES);// 上周三
			paramMap.put("TO_DATE", TO_DATE);// 这周三
			paramMap.put("FROM_DATE_F", TO_DATES);// 上周三开始
			paramMap.put("TO_DATE_F", TO_DATE);// 这周三
			returnList = arReportDao.getHrPersonFormalList(paramMap);
		}
		return returnList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ait.report.ar.service.ArReportSer#getCodeListByParentCode(javax.servlet.http.HttpServletRequest,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List getHrPersonEntryList(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String WM_FLAG = request.getParameter("WM_FLAG");// 判断是周还是月

		if (WM_FLAG.equals("1")) {
			String FROM_DATE = request.getParameter("seach_FROM_DATE");
			String TO_DATE = request.getParameter("seach_TO_DATE");
			String FROM_DATES = this.getMonthLastDay(FROM_DATE);
			String TO_DATES = this.getMonthLastDay(TO_DATE);
			String FROM_DATE_F = this.getMonthFirstDay(FROM_DATE);
			String TO_DATE_F = this.getMonthFirstDay(TO_DATE);
			paramMap.put("FROM_DATE", FROM_DATES);// 之前月的最后一天
			paramMap.put("TO_DATE", TO_DATES);// 当前月的最后一天 /若是现在的月份则是今天
			paramMap.put("FROM_DATE_F", FROM_DATE_F);// 之前月的第一天
			paramMap.put("TO_DATE_F", TO_DATE_F);// 当前前月的第一天

			returnList = arReportDao.getHrPersonEntryList(paramMap);
		} else {

			String TO_DATE = request.getParameter("seach_TO_DATE_W");

			String TO_DATES = this.getWeekLastDay(TO_DATE);

			paramMap.put("FROM_DATE", TO_DATES);// 上周三
			paramMap.put("TO_DATE", TO_DATE);// 这周三
			paramMap.put("FROM_DATE_F", TO_DATES);// 上周三开始
			paramMap.put("TO_DATE_F", TO_DATES);// 上周三
			returnList = arReportDao.getHrPersonEntryList1(paramMap);
		}

		return returnList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ait.report.ar.service.ArReportSer#getCodeListByParentCode(javax.servlet.http.HttpServletRequest,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List getHrPersonRankList(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String WM_FLAG = request.getParameter("WM_FLAG");// 判断是周还是月

		if (WM_FLAG.equals("1")) {
			String FROM_DATE = request.getParameter("seach_FROM_DATE");
			String TO_DATE = request.getParameter("seach_TO_DATE");
			String FROM_DATES = this.getMonthLastDay(FROM_DATE);
			String TO_DATES = this.getMonthLastDay(TO_DATE);
			String FROM_DATE_F = this.getMonthFirstDay(FROM_DATE);
			String TO_DATE_F = this.getMonthFirstDay(TO_DATE);
			paramMap.put("FROM_DATE", FROM_DATES);// 之前月的最后一天
			paramMap.put("TO_DATE", TO_DATES);// 当前月的最后一天 /若是现在的月份则是今天
			paramMap.put("FROM_DATE_F", FROM_DATE_F);// 之前月的第一天
			paramMap.put("TO_DATE_F", TO_DATE_F);// 当前前月的第一天

			returnList = arReportDao.getHrPersonRankList(paramMap);
		} else {

			String TO_DATE = request.getParameter("seach_TO_DATE_W");

			String TO_DATES = this.getWeekLastDay(TO_DATE);

			paramMap.put("FROM_DATE", TO_DATES);// 上周三
			paramMap.put("TO_DATE", TO_DATE);// 这周三
			paramMap.put("FROM_DATE_F", TO_DATES);// 上周三开始
			paramMap.put("TO_DATE_F", TO_DATE);// 这周三
			returnList = arReportDao.getHrPersonRankList(paramMap);
		}

		return returnList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ait.report.ar.service.ArReportSer#getCodeListByParentCode(javax.servlet.http.HttpServletRequest,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List getHrPersonDeptList(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		String WM_FLAG = request.getParameter("WM_FLAG");// 判断是周还是月

		if (WM_FLAG.equals("1")) {
			String FROM_DATE = request.getParameter("seach_FROM_DATE");
			String TO_DATE = request.getParameter("seach_TO_DATE");
			String FROM_DATES = this.getMonthLastDay(FROM_DATE);
			String TO_DATES = this.getMonthLastDay(TO_DATE);
			String FROM_DATE_F = this.getMonthFirstDay(FROM_DATE);
			String TO_DATE_F = this.getMonthFirstDay(TO_DATE);
			paramMap.put("FROM_DATE", FROM_DATES);// 之前月的最后一天
			paramMap.put("TO_DATE", TO_DATES);// 当前月的最后一天 /若是现在的月份则是今天
			paramMap.put("FROM_DATE_F", FROM_DATE_F);// 之前月的第一天
			paramMap.put("TO_DATE_F", TO_DATE_F);// 当前前月的第一天

			returnList = arReportDao.getHrPersonDeptList(paramMap);
		} else {

			String TO_DATE = request.getParameter("seach_TO_DATE_W");

			String TO_DATES = this.getWeekLastDay(TO_DATE);

			paramMap.put("FROM_DATE", TO_DATES);// 上周三
			paramMap.put("TO_DATE", TO_DATE);// 这周三
			paramMap.put("FROM_DATE_F", TO_DATES);// 上周三开始
			paramMap.put("TO_DATE_F", TO_DATE);// 这周三
			returnList = arReportDao.getHrPersonDeptList(paramMap);
		}

		return returnList;
	}

	// //一月中最大天数 若是时间为空 默认为本月最大天数
	public String getMonthLastDay(String month) {
		Calendar a = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");

		String date = "";
		if (month != null && !month.equals("")) {
			String dates[] = month.split("/");
			int year = Integer.parseInt(dates[0]);
			int months = Integer.parseInt(dates[1]);
			int day = 1;
			Calendar cal = Calendar.getInstance();
			cal.set(year, months - 1, day);
			int last = 1;
			// 如果是当前月 last
			String monthNow = format.format(a.getTime());
			if (month.equals(monthNow)) {
				last = a.get(Calendar.DATE);
			} else {
				// 如果不是则是最后一天
				last = cal.getActualMaximum(Calendar.DATE);

			}
			date = year + "/" + months + "/" + last;

		}

		return date;
	}

	// 取得此月份的第一天
	public String getMonthFirstDay(String month) {

		String date = "";
		if (month != null && !month.equals("")) {
			String dates[] = month.split("/");
			int year = Integer.parseInt(dates[0]);
			int months = Integer.parseInt(dates[1]);
			int day = 1;
			Calendar cal = Calendar.getInstance();
			cal.set(year, months - 1, day);
			int last = 1;

			last = cal.get(Calendar.DATE);

			date = year + "/" + months + "/" + last;

		}

		return date;
	}

	// //取得日期的
	public String getWeekLastDay(String month) {

		String date = "";

		if (month != null && !month.equals("")) {
			String dates[] = month.split("/");
			int year = Integer.parseInt(dates[0]);
			int months = Integer.parseInt(dates[1]);
			int day = Integer.parseInt(dates[2]);
			Calendar cal = Calendar.getInstance();
			cal.set(year, months - 1, day);

			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
			int offset1 = 1 - dayOfWeek;

			cal.add(Calendar.DATE, offset1 - 5);

			date = format.format(cal.getTime());
		}
		return date;
	}

}
