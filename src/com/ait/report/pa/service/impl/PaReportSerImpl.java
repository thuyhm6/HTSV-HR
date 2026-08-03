package com.ait.report.pa.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.ait.ar.dao.countAttendanceDao;
import com.ait.report.ar.dao.ArReportDao;
import com.ait.report.pa.service.PaReportSer;
import com.ait.report.hr.dao.HrReportDao;
import com.ait.report.hr.service.HrReportSer;
import com.ait.report.pa.dao.PaReportDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaReportSerImpl.java
 * @Description: implement Class PaReportSerImpl.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
@Service
public class PaReportSerImpl implements PaReportSer {
	Logger logger = Logger.getLogger(PaReportSerImpl.class);
	@Autowired
	private PaReportDao paReportDao;
	@Autowired
	private countAttendanceDao viewDeptPerDao;
	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaTranserList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR_pa0108")!=null?paramMap.get("YEAR_pa0108").toString():"";
		String month = paramMap.get("MONTH_pa0108")!=null?paramMap.get("MONTH_pa0108").toString():"";
		
		if(request.getParameter("GIVE_DATE_pa0108")==null || !"".equals(request.getParameter("GIVE_DATE_pa0108").toString())){
			paramMap.put("GIVE_DATE",request.getParameter("GIVE_DATE_pa0108"));
		}
		
		paramMap.put("GIVE_DATE", paramMap.get("GIVE_DATE_pa0108")!=null?paramMap.get("GIVE_DATE_pa0108").toString():"");
				

		paramMap.put("PA_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		if(paramMap.get("CPNY_ID")!=null && "C02".equals(paramMap.get("CPNY_ID").toString())){
			if (UiUtil.getPageNum(request) > 0){
				returnList = 
					paReportDao.getPaTranserTwoList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
			}else{
				returnList = paReportDao.getPaTranserTwoList(paramMap) ;
			}
		}else{
			if (UiUtil.getPageNum(request) > 0){
				returnList = 
					paReportDao.getPaTranserList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
			}else{
				returnList = paReportDao.getPaTranserList(paramMap) ;
			}
		}
		
		return returnList ;
	}
	
	/**
	 * 员工工资转账信息的数量（按月份查询）(query the pa transer info count of employee by monthed)
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPaTranserListCnt(HttpServletRequest request) {
		int returnInt = 0 ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR_pa0108")!=null?paramMap.get("YEAR_pa0108").toString():"";
		String month = paramMap.get("MONTH_pa0108")!=null?paramMap.get("MONTH_pa0108").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//if(request.getParameter("GIVE_DATE_pa0108")==null || !"".equals(request.getParameter("GIVE_DATE_pa0108").toString())){
		//	 paramMap.put("GIVE_DATE",request.getParameter("GIVE_DATE_pa0108"));
		//}
		
		paramMap.put("GIVE_DATE", paramMap.get("GIVE_DATE_pa0108")!=null?paramMap.get("GIVE_DATE_pa0108").toString():"");

		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return 0;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return 0;
		}
		returnInt = paReportDao.getPaTranserListCnt(paramMap) ;
		return returnInt ;
	}
	
	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaTranserExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR_pa0108")!=null?paramMap.get("YEAR_pa0108").toString():"";
		String month = paramMap.get("MONTH_pa0108")!=null?paramMap.get("MONTH_pa0108").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//if(request.getParameter("GIVE_DATE_pa0108")!=null || !"".equals(request.getParameter("GIVE_DATE_pa0108").toString())){
		//	 paramMap.put("GIVE_DATE",request.getParameter("GIVE_DATE_pa0108"));
		//}
		
		paramMap.put("GIVE_DATE", paramMap.get("GIVE_DATE_pa0108")!=null?paramMap.get("GIVE_DATE_pa0108").toString():"");		
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		if(paramMap.get("CPNY_ID")!=null && "C02".equals(paramMap.get("CPNY_ID").toString())){
			returnList = paReportDao.getPaTranserTwoList(paramMap) ;
		}else{
			returnList = paReportDao.getPaTranserList(paramMap) ;
		}
		return returnList ;
	}
	
	/**
	 * 根据deptno获取SAP工资信息查询的横向表头（部门信息）（query the sap pa info for the header by the deptno）
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getSapDepartList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		
		returnList = paReportDao.getSapDepartList(paramMap) ;
		
		return returnList ;
	}
	
	/**
	 * 根据cpny_no获取SAP工资信息查询的纵向表头（工资项目）（query the sap pa item info for the header by the cpny_no）
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getSapPaItemList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		
		returnList = paReportDao.getSapPaItemList(paramMap) ;
		
		return returnList ;
	}
	
	/**
	 * 根据工资月、部门NO查询SAP工资信息（query the sap pa info by the condition）
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getSapPaInfoList(HttpServletRequest request) {
		LinkedHashMap returnMap = new LinkedHashMap();
		List<LinkedHashMap> hrmCountList = new ArrayList<LinkedHashMap>() ;
		List<LinkedHashMap> sapPaList = new ArrayList<LinkedHashMap>() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnMap;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnMap;
		}
		List<LinkedHashMap> departList = new ArrayList<LinkedHashMap>() ;
		List<LinkedHashMap> paItemList = new ArrayList<LinkedHashMap>() ;
		departList = (List<LinkedHashMap>)paReportDao.getSapDepartList(paramMap);
		paItemList = (List<LinkedHashMap>)paReportDao.getSapPaItemList(paramMap);
		
		if(departList.size()>0 && paItemList.size()>0){
			Map map = new LinkedHashMap();
			List<LinkedHashMap> empTypeHrmList = new ArrayList<LinkedHashMap>();
			LinkedHashMap dataMap = new LinkedHashMap();
			map.put("interLanguage", admin.getLanguage());
			map.put("interCpnyID", admin.getCpnyId());
			map.put("ADMINID", admin.getAdminID());
			map.put("CPNY_ID", admin.getCpnyId());
			map.put("PA_MONTH", year+month);
			for(LinkedHashMap departMap:departList){
				LinkedHashMap hrmMap = new LinkedHashMap();
				map.put("DEPTNO", departMap.get("DEPTNO"));
				//查询该部门、该月份中的所有人的员工类型有几种，放入到部门所在的MAP里
				int empTypeCount = 1;
				empTypeCount = (int)paReportDao.getPaEmpTypeByDeptno(map);
				departMap.put("empTypeCount", empTypeCount>0?empTypeCount:1);
				//把部门编号放入MAP中
				hrmMap.put("DEPTNO", departMap.get("DEPTNO"));
				//查询人数
					//1.部门总人数（暂时不用）
				hrmMap = (LinkedHashMap)paReportDao.getPaHrmCountByDept(map);
					//2.部门下按员工类型分组的类型和对应类型的人数
				empTypeHrmList = (List<LinkedHashMap>)paReportDao.getPaHrmCountByDeptAndEmptype(map);
				hrmMap.put("empTypeHrmList", empTypeHrmList);
				
				hrmCountList.add(hrmMap);
				for(LinkedHashMap paItemMap:paItemList){
					map.put("ITEM_ID", paItemMap.get("FIELD_ID"));
					//查询工资项目合计
					dataMap = (LinkedHashMap)paReportDao.getPaitemSumInfoByDept(map);
					dataMap.put("ITEM_ID", paItemMap.get("FIELD_ID"));
					sapPaList.add(dataMap);
				}
			}
		}
		//横向部门信息
		returnMap.put("departList", departList);
		//纵向工资项目信息
		returnMap.put("paItemList", paItemList);
		//部门人数统计
		returnMap.put("hrmCountList", hrmCountList);
		//部门工资项目统计
		returnMap.put("sapPaList", sapPaList);
		
		return returnMap ;
	}

	/**
	 * 员工在职证明信息（按工资月份查询）(query the office prove info of employee by pa monthed)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOfficeProveList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR_pa0109")!=null?paramMap.get("YEAR_pa0109").toString():"";
		String month = paramMap.get("MONTH_pa0109")!=null?paramMap.get("MONTH_pa0109").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		// if(request.getParameter("GIVE_DATE_pa0109")==null ||! "".equals(request.getParameter("GIVE_DATE_pa0109").toString())){
		//	 paramMap.put("GIVE_DATE",request.getParameter("GIVE_DATE_pa0109"));
		//  }
		 
	    paramMap.put("GIVE_DATE", paramMap.get("GIVE_DATE_pa0109")!=null?paramMap.get("GIVE_DATE_pa0109").toString():"");
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				paReportDao.getOfficeProveList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			returnList = paReportDao.getOfficeProveList(paramMap) ;
		}
		return returnList ;
	}

	/**
	 * 员工在职证明信息（按工资月份查询）(query the office prove info of employee by pa monthed)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getOfficeProveListCnt(HttpServletRequest request) {
		int returnInt = 0 ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR_pa0109")!=null?paramMap.get("YEAR_pa0109").toString():"";
		String month = paramMap.get("MONTH_pa0109")!=null?paramMap.get("MONTH_pa0109").toString():"";
		paramMap.put("PA_MONTH", year+month);
		
		//if(request.getParameter("GIVE_DATE_pa0109")==null || !"".equals(request.getParameter("GIVE_DATE_pa0109").toString())){
		//	 paramMap.put("GIVE_DATE",request.getParameter("GIVE_DATE_pa0109"));
		//}
		
		paramMap.put("GIVE_DATE", paramMap.get("GIVE_DATE_pa0109")!=null?paramMap.get("GIVE_DATE_pa0109").toString():"");		

		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return 0;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return 0;
		}
		returnInt = paReportDao.getOfficeProveListCnt(paramMap) ;
		return returnInt ;
	}
	
	/**
	 * 员工在职证明信息（按工资月份查询）(query the office prove info of employee by pa monthed)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOfficeProveExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("GIVE_DATE", paramMap.get("GIVE_DATE_pa0108")!=null?paramMap.get("GIVE_DATE_pa0108").toString():"");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportDao.getOfficeProveList(paramMap) ;
		return returnList ;
	}
	
	/**
	 * 根据法人Cpny_id查询该法人所有薪资项目
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaItemListByCpnyId(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("PA_YEAR")!=null?paramMap.get("PA_YEAR").toString():"";
		paramMap.put("PA_YEAR", year);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		if(paramMap.get("COMPANY_ID")==null || "".equals(paramMap.get("COMPANY_ID"))){
			return returnList;
		}
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				paReportDao.getPaInfoByItemList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			returnList = paReportDao.getPaInfoByItemList(paramMap) ;
		}
		return returnList ;
	}
	
	/**
	 * 根据法人Cpny_id、薪资月份查询该法人、该月的所有薪资发放日期
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaGiveDateListByCpnyIdAndMonth(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("PA_YEAR")!=null?paramMap.get("PA_YEAR").toString():"";
		paramMap.put("PA_YEAR", year);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		if(paramMap.get("COMPANY_ID")==null || "".equals(paramMap.get("COMPANY_ID"))){
			return returnList;
		}
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				paReportDao.getPaInfoByItemList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			returnList = paReportDao.getPaInfoByItemList(paramMap) ;
		}
		return returnList ;
	}
	
	/**
	 * 工资查看--特殊值查看页面，查看页面
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInfoByItemList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String year = paramMap.get("PA_YEAR")!=null?paramMap.get("PA_YEAR").toString():"";
		paramMap.put("PA_YEAR", year);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				paReportDao.getPaInfoByItemList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			returnList = paReportDao.getPaInfoByItemList(paramMap) ;
		}
		return returnList ;
	}

	/**
	 * 工资查看--特殊值查看页面，数量查询
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPaInfoByItemListCnt(HttpServletRequest request) {
		int returnInt = 0 ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String year = paramMap.get("PA_YEAR")!=null?paramMap.get("PA_YEAR").toString():"";
		paramMap.put("PA_YEAR", year);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return 0;
		}
		returnInt = paReportDao.getPaInfoByItemListCnt(paramMap) ;
		return returnInt ;
	}
	
	/**
	 * 工资查看--特殊值查看页面，导出Excel用
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInfoByItemExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String year = paramMap.get("PA_YEAR")!=null?paramMap.get("PA_YEAR").toString():"";
		paramMap.put("PA_YEAR", year);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		returnList = paReportDao.getPaInfoByItemList(paramMap) ;
		return returnList ;
	}
	
	/**
	 * 薪资查看--年工资，查看页面
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInfoByYearList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String year = paramMap.get("PA_YEAR")!=null?paramMap.get("PA_YEAR").toString():"";
		paramMap.put("PA_YEAR", year);
		paramMap.put("TYPE", "Y");
		
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		paramMap.put("TABLE", "PA_SUMMARY_"+paramMap.get("CPNY_ID"));
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				paReportDao.getPaInfoByYearList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			returnList = paReportDao.getPaInfoByYearList(paramMap) ;
		}
		return returnList ;
	}

	/**
	 * 薪资查看--年工资，查看页面
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPaInfoByYearListCnt(HttpServletRequest request) {
		int returnInt = 0 ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String year = paramMap.get("PA_YEAR")!=null?paramMap.get("PA_YEAR").toString():"";
		paramMap.put("PA_YEAR", year);
		paramMap.put("TYPE", "Y");
		//paramMap.put("TABLE", "PA_SUMMARY_"+admin.getCpnyId());
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return 0;
		}
		paramMap.put("TABLE", "PA_SUMMARY_"+paramMap.get("CPNY_ID"));
		returnInt = paReportDao.getPaInfoByYearListCnt(paramMap) ;
		return returnInt ;
	}
	
	/**
	 * 薪资查看--年工资，查看页面
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInfoByYearExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String year = paramMap.get("PA_YEAR")!=null?paramMap.get("PA_YEAR").toString():"";
		paramMap.put("PA_YEAR", year);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		returnList = paReportDao.getPaInfoByYearList(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资汇总表
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaSummaryList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = paReportDao.getPaSummaryList(paramMap) ;
		return returnList ;
	}
	/**
	 * 加班费对比分析
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtFeeContrastList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = paReportDao.getOtFeeContrastList(paramMap) ;
		return returnList ;
	}
	
	/**
	 * 应发工资对比分析
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getGrossPayList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = paReportDao.getGrossPayList(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaDecisionList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = paReportDao.getPaDecisionList(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaNetPayList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = paReportDao.getPaNetPayList(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaSendFeeList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = paReportDao.getPaSendFeeList(paramMap) ;
		return returnList ;
	}
	
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaOtFeeList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = paReportDao.getPaOtFeeList(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getPayInfoList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = paReportDao.getPayInfoList(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpDeftSpcBjList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = paReportDao.getEmpDeftSpcBjList(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpDeftInfoSpcBjList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = paReportDao.getEmpDeftInfoSpcBjList(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getSalaryTotalSpcBjList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = paReportDao.getSalaryTotalSpcBjList(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资奖金支付
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getWagesBonusList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = paReportDao.getWagesBonusList(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资分配
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaAllocationList(HttpServletRequest request,String deptNo,String deptType) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PARENT_DEPT_NO", deptNo);
		paramMap.put("DEPT_TYPE", deptType);
		returnList = paReportDao.getPaAllocationList(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资分配
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List paAllocationForOwned(HttpServletRequest request,String deptNo,String deptType,String owned) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PARENT_DEPT_NO", deptNo);
		paramMap.put("DEPT_TYPE", deptType);
		paramMap.put("EMPLOYEE_OWNED", owned);
		returnList = paReportDao.paAllocationForOwned(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资分配
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List paAllocationForDept(HttpServletRequest request,String deptNo,String owned) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("EMPLOYEE_OWNED", owned);
		paramMap.put("DEPTNO", deptNo);
		returnList = paReportDao.paAllocationForDept(paramMap) ;
		return returnList ;
	}
	/**
	 * 工资分配
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List paAllocationForDept(HttpServletRequest request,String deptNo,String owned,String empType) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("EMPLOYEE_OWNED", owned);
		paramMap.put("DEPTNO", deptNo);
		paramMap.put("EMP_TYPE_CODE", empType);
		returnList = paReportDao.paAllocationForDept(paramMap) ;
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getPaAllocationListForSpcBj(HttpServletRequest request,String target,String owned) {
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		param.put("EMPLOYEE_OWNED", owned);
		return paReportDao.getPaAllocationListForSpcBj(param,target);
	}
	/**
	 * 各  部  门  比  例  明  细
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getPaDetaiByDeptNolList(HttpServletRequest request,String flag,String dept_lv) {
		LinkedHashMap lMap = new LinkedHashMap();
		List list = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(!flag.equals("THIS_MONTH")){
			String month = (String) paramMap.get("MONTH");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM");
			Calendar c = Calendar.getInstance();
			try {
				Date date  = formatter.parse(month);
				c.setTime(date);
				c.add(Calendar.MONTH, -1);
				String monthStr = formatter.format(c.getTime()).toString();
				paramMap.put("MONTH",monthStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		paramMap.put("DEPT_LEVE", dept_lv);
		list = viewDeptPerDao.getSecDept(paramMap);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID 
					// key为 部门ID value=查询结果LIST
					paramMap.put("DEPTNO",dept_id.get("DEPTNO"));
					lMap.put(dept_id.get("DEPTNO"), this.paReportDao.getPaDetaiByDeptNolList(paramMap));
				}
			}
		} 
		lMap.put("deptList", list);
		return lMap ;
	}
	/**
	 * 某职群下职种
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getPositionList(HttpServletRequest request,String postFamily) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("POST_FAMILY",postFamily);
		returnList = paReportDao.getPositionList(paramMap) ;
		return returnList ;
	}
	/**
	 * 某职群下职种
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getPostGradeNOList(HttpServletRequest request,String position) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("POSITION",position);
		returnList = paReportDao.getPostGradeNOList(paramMap) ;
		return returnList ;
	}
	/**
	 * 某职群下职职级
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getPostGradeNOForPostFamilyList(HttpServletRequest request,String postFamily) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("POST_FAMILY",postFamily);
		returnList = paReportDao.getPostGradeNOForPostFamilyList(paramMap) ;
		return returnList ;
	}
	/**
	 * 子部门
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getDeptChildList(HttpServletRequest request,String deptNo) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PARENT_DEPT_NO",deptNo);
		returnList = paReportDao.getDeptChildList(paramMap) ;
		return returnList ;
	}
	/**
	 * 子部门
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List getDeptChildGroupList(HttpServletRequest request,String deptNo) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PARENT_DEPT_NO",deptNo);
		returnList = paReportDao.getDeptChildGroupList(paramMap) ;
		return returnList ;
	}
	/**
	 * 各部门实际情况
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getManagementSituationList(HttpServletRequest request,String deptLeve,String include) {
		LinkedHashMap lMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List list = new ArrayList();
		paramMap.put("DEPT_LEVE", deptLeve);
		paramMap.put("INCLUDE_DEPTNO", include);
		list = this.paReportDao.getDeptForLeve(paramMap);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map deteMap = (Map) list.get(i);// 取出部门ID
					paramMap.put("DEPTNO", deteMap.get("DEPTNO"));
					lMap.put(deteMap.get("DEPTNO"), this.paReportDao.getManagementSituationList(paramMap));
				}
			}
		} 
		lMap.put("deptList", list);
		return lMap ;
	}
	/**
	 * 工厂实际情况
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getFactorySituationList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List list = new ArrayList();
		list = this.getPostGradeNOForPostFamilyList(request,"14015817");
		paramMap.put("POST_FAMILY", "14015817");
		lMap.put("GC", this.paReportDao.getManagementSituationList(paramMap));
		return lMap ;
	}
	/**
	 * 店铺前厅实际情况
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getStoreOperationSituationList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List list = new ArrayList();
		list = this.getDeptChildList(request,"DL0302");	
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map deptMap = (Map) list.get(i);// 取出部门ID
					// key为 部门ID value=查询结果LIST
					paramMap.put("POST_FAMILY","14015815");
					paramMap.put("DEPT_NO",deptMap.get("DEPTNO"));
					lMap.put(deptMap.get("DEPTNO"), this.paReportDao.getManagementSituationList(paramMap));
				}
			}
		} 
		paramMap.put("DEPT_NO",null);
		paramMap.put("DEPTNO",this.changeCode(request));
		lMap.put("STOREALL", this.paReportDao.getManagementSituationList(paramMap));
		return lMap ;
	}
	
	public LinkedHashMap getStoreKitchenSituationList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List list = new ArrayList();
		list = this.getDeptChildGroupList(request,"'DL0403','DL0404'");	
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map deptMap = (Map) list.get(i);// 取出部门ID
					// key为 部门ID value=查询结果LIST
					paramMap.put("DEPTNAME",deptMap.get("DEPTNAMESTR"));
					lMap.put(deptMap.get("DEPTNAMESTR"), this.paReportDao.getStoreKitchenSituationList(paramMap));
				}
			}
		} 
		paramMap.put("DEPTNAME",null);
		paramMap.put("DEPTNO", "DL0401");
		lMap.put("DPZY", this.paReportDao.getStoreKitchenSituationList(paramMap));
		paramMap.put("DEPTNO", "DL040102");
		lMap.put("AS", this.paReportDao.getStoreKitchenSituationList(paramMap));
		paramMap.put("DEPTNO", "DL0402");
		lMap.put("TC", this.paReportDao.getStoreKitchenSituationList(paramMap));
		paramMap.put("DEPTNO", null);
		lMap.put("ALL", this.paReportDao.getStoreKitchenSituationList(paramMap));
		return lMap ;
	}
	
	public String  changeCode(HttpServletRequest request) {
		String codeType = "";
		List list = new ArrayList();
		list = this.getDeptChildList(request,"DL0302");	
		for (int i = 0; i < list.size(); i++) {
			Map deptMap = (Map) list.get(i);
			codeType = codeType + "'"+ deptMap.get("DEPTNO") +"',";
		}
		codeType = codeType +"'empty'";
		return codeType;
	}
}

