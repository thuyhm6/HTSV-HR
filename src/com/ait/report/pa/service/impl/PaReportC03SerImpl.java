package com.ait.report.pa.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.report.pa.dao.PaReportC03Dao;
import com.ait.report.pa.service.PaReportC03Ser;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
@Service
public class PaReportC03SerImpl  implements PaReportC03Ser{
		
	@Autowired
	private PaReportC03Dao paReportC03Dao;
	/**
	 * 导出给予现状汇总报表
	 * 
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPacurrentRenditionCollectList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("pamonth",paramMap.get("seach_YEAR").toString()+paramMap.get("seach_MONTH").toString());
		/*paramMap.put("dept_No", "C0315");
		paramMap.put("dept_No", "C0327");
		paramMap.put("dept_No", "C034");
		paramMap.put("dept_No", "other");
*/
		retrunList = paReportC03Dao.getPaCurrentRenditionSumAvg(paramMap);
		return retrunList;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPacurrentRenditionCollectList2(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("pamonth",paramMap.get("seach_YEAR").toString()+paramMap.get("seach_MONTH").toString());
		

		retrunList = paReportC03Dao.getPacurrentRenditionCollectList2(paramMap);
		return retrunList;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPacurrentRenditionCollectList3(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("pamonth",paramMap.get("seach_YEAR").toString()+paramMap.get("seach_MONTH").toString());

		retrunList = paReportC03Dao.getPacurrentRenditionCollectList3(paramMap);
		return retrunList;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPacurrentRenditionCollectList4(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("pamonth",paramMap.get("seach_YEAR").toString()+paramMap.get("seach_MONTH").toString());


		retrunList = paReportC03Dao.getPacurrentRenditionCollectList4(paramMap);
		return retrunList;

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPacurrentRenditionCollectList5(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("pamonth",paramMap.get("seach_YEAR").toString()+paramMap.get("seach_MONTH").toString());


		retrunList = paReportC03Dao.getPacurrentRenditionCollectList5(paramMap);
		return retrunList;

	}
	/**
	 * 导出给予现状报表(按员工)
	 */
	@Override
	public List getPaCurrentRenditionList(HttpServletRequest request) {
		String monthStr = request.getParameter("seach_YEAR") + request.getParameter("seach_MONTH");
		String deptNo = request.getParameter("deptNo");
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("dept_No", deptNo);
		paramMap.put("monthStr", monthStr);
		List list =  paReportC03Dao.getPaCurrentRenditionList(paramMap);
		return list;
	}
	
	/**
	 * 导出给予现状报表(求和与求平均)
	 */
	@Override
	public List getPaCurrentRenditionSumAvg(HttpServletRequest request) {
		String monthStr = request.getParameter("seach_YEAR") + request.getParameter("seach_MONTH");
		String deptNo = request.getParameter("deptNo");
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("dept_No", deptNo);
		paramMap.put("monthStr", monthStr);
		List list =  paReportC03Dao.getPaCurrentRenditionSumAvg(paramMap);
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public int empCount1(HttpServletRequest request){		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String pa_month = request.getParameter("seach_YEAR")+request.getParameter("seach_MONTH");
		paramMap.put("PA_MONTH", pa_month);
		return paReportC03Dao.getEmpCnt1(paramMap) ;

		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public int empCount2(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String pa_month = request.getParameter("seach_YEAR")+request.getParameter("seach_MONTH");
		paramMap.put("PA_MONTH", pa_month);
		return paReportC03Dao.getEmpCnt2(paramMap) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int empCount3(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String pa_month = request.getParameter("seach_YEAR")+request.getParameter("seach_MONTH");
		paramMap.put("PA_MONTH", pa_month);
		return paReportC03Dao.getEmpCnt3(paramMap) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int empCount4(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String pa_month = request.getParameter("seach_YEAR")+request.getParameter("seach_MONTH");
		paramMap.put("PA_MONTH", pa_month);
		return paReportC03Dao.getEmpCnt4(paramMap) ;
	}

	/**
	 * 导出给予小时工工资发放表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaHourWorkInfo(LinkedHashMap paramMap) {
		return paReportC03Dao.getPaHourWorkInfo(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> getPaHourWorkInfoByDeptNo(LinkedHashMap paramMap) {
		return paReportC03Dao.getPaHourWorkInfoByDeptNo(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> getSumPaHourWorkInfo(LinkedHashMap paramMap) {
		return paReportC03Dao.getSumPaHourWorkInfo(paramMap);
	}
}
