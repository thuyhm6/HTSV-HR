package com.ait.report.pa.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.report.pa.dao.PaReportC04Dao;
import com.ait.report.pa.service.PaReportC04Ser;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
@Service
public class PaReportC04SerImpl implements PaReportC04Ser {
	@Autowired
	private PaReportC04Dao paReportC04Dao;

	/**
	 * 导出工报盘报表的数据
	 * 
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaJobOfferList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("pamonth",paramMap.get("seach_YEAR").toString()+paramMap.get("seach_MONTH").toString());
		retrunList = paReportC04Dao.getPaJobOfferList(paramMap);
		return retrunList;

	}

	/**
	 * 查询员工类型
	 * 
	 * @param object
	 * @return list
	 */
	public List getEmpType(HttpServletRequest request) {

		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		retrunList = paReportC04Dao.getEmpType(paramMap);
		return retrunList;

	}

	/**
	 * 获取工资发放表（正式工）的数据
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaOfficialPayOffList(HttpServletRequest request) {
		
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE_CODE", "1369");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportC04Dao.getPaOfficialPayOffList(paramMap);
		return returnList;
	}
	
	/**
	 * 获取正式员工部门编号
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaDeptEmpIdList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("DEPTNO", "C041");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		returnList = paReportC04Dao.getPaDeptEmpIdList(paramMap);
		return returnList;
	}
	
	/**
	 * 获劳务派遣员工部门编号
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaLabourDeptEmpIdList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE_CODE", "14890");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportC04Dao.getPaDeptEmpIdList(paramMap);
		return returnList;
	}
	
	/**
	 * 获取工资发放表（劳务工）的数据
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaLabourPayOffList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		String services_belong =  (String) paramMap.get("service_belong");
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE_CODE", "14890");
		paramMap.put("SERVICES_BELONG",services_belong);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportC04Dao.getPaLabourPayOffList(paramMap);
		return returnList;
	}

	/**
	 * 根据法人获取所有部门的名称
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaAllDeptNameList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		returnList = paReportC04Dao.getPaAllDeptNameList(paramMap);
		return returnList;
	}

	/**
	 * 获取正式工按不同部门区分，并算出该部门员工工资和（Excel求小计用）
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaOfficialPayOffSumList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE_CODE", "1369");
		returnList = paReportC04Dao.getPaOfficialPayOffSumList(paramMap);
		return returnList;
	}
	
	/**
	 * 获取正式工按不同部门区分，并算出该部门员工工资和（Excel求总计用）
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaOfficialPayOffZongJiList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE_CODE", "1369");
		returnList = paReportC04Dao.getPaOfficialPayOffZongJiList(paramMap);
		return returnList;
	}

	/**
	 * 获取劳务工按不同部门区分，并算出该部门员工工资和（Excel求小计用）
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaLabourPayOffSumList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		String services_belong =  (String) request.getParameter("seach_service_belong");
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE_CODE", "14890");
		paramMap.put("SERVICES_BELONG",services_belong);
		returnList = paReportC04Dao.getPaLabourPayOffSumList(paramMap);
		return returnList;
	}

	/**
	 * 获取劳务工按不同部门区分，并算出该部门员工工资和（Excel求总计用）
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaLabourPayOffZongJiList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		String services_belong =  (String) request.getParameter("seach_service_belong");
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE_CODE", "14890");
		paramMap.put("SERVICES_BELONG",services_belong);
		returnList = paReportC04Dao.getPaLabourPayOffZongJiList(paramMap);
		return returnList;
	}

	/**
	 * 工资单导出pdf
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object retrievePaJasperReportPayrollData(LinkedHashMap paramMap) {
		List sourceList = new ArrayList();
		try {
			sourceList = paReportC04Dao.retrievePaJasperReportPayrollData(paramMap);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return sourceList;
	}

	/**
	 * 工资对照导出
	 */
	@Override
	public List getPacontrastList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		System.out.println("--------------------------"+year+month);
		paramMap.put("EMP_TYPE_CODE", "1369");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportC04Dao.getPacontrastList(paramMap);
		return returnList;
	}
	/**
	 * 工资对照导出(合计)
	 */
	@Override
	public List getPacontrastListsum(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE_CODE", "1369");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportC04Dao.getPacontrastListsum(paramMap);
		return returnList;
	}
	/**
	 * 个税
	 */
	@Override
	public List getPersonaltaxList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		System.out.println("--------------------------"+year+month);
		paramMap.put("EMP_TYPE_CODE", "1369");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportC04Dao.getPersonaltaxList(paramMap);
		return returnList;
	}

	/**
	 * 工资支付现状（按月份计算）导出Excel
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaStatusByMonthPayOffList(HttpServletRequest request) {
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
		returnList = paReportC04Dao.getPaStatusByMonthPayOffList(paramMap);
		return returnList;
	}
	
	
	
	
	
	//------------------单月------------------------
	/**
	 * 查询销售金额（按月份计算）C04专用--导出Excel--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaSalaryAmountMap(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//47为工资输入项目的 销售金额
		paramMap.put("PARAM_NO", "47");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return map;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return map;
		}
		list = paReportC04Dao.getPaSalaryAmountList(paramMap);
		if(0==list.size()){//这些金额在页面要用作除数，所以不能为0
			map.put("LAST_YEAR_AMOUNT", 1);
			map.put("LAST_MONTH_AMOUNT", 1);
			map.put("CURRENT_AMOUNT", 1);
		}else{
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap = list.get(0)!=null?(LinkedHashMap)list.get(0):dataMap;
			String last_year_salary = "1";
			String last_month_salary = "1";
			String current_salary = "1";

			if(map.get("LAST_YEAR_AMOUNT")!=null && !"0".equals(map.get("LAST_YEAR_AMOUNT").toString())){
				last_year_salary = map.get("LAST_YEAR_AMOUNT").toString();
			}
			
			if(map.get("LAST_MONTH_AMOUNT")!=null && !"0".equals(map.get("LAST_MONTH_AMOUNT").toString())){
				last_month_salary = map.get("LAST_MONTH_AMOUNT").toString();
			}
			
			if(map.get("CURRENT_AMOUNT")!=null && !"0".equals(map.get("CURRENT_AMOUNT").toString())){
				current_salary = map.get("CURRENT_AMOUNT").toString();
			}
			map.put("LAST_YEAR_AMOUNT", last_year_salary);
			map.put("LAST_MONTH_AMOUNT", last_month_salary);
			map.put("CURRENT_AMOUNT", current_salary);
		}
		
		return map;
	}
	
	/**
	 * 查询生产金额（按月份算）C04专用--导出Excel--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaProductionAmountMap(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//168为工资输入项目的 生产金额
		paramMap.put("PARAM_NO", "168");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return map;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return map;
		}
		list = paReportC04Dao.getPaProductionAmountList(paramMap);
		if(0==list.size()){//这些金额在页面要用作除数，所以不能为0
			map.put("LAST_YEAR_AMOUNT", 1);
			map.put("LAST_MONTH_AMOUNT", 1);
			map.put("CURRENT_AMOUNT", 1);
		}else{
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap = list.get(0)!=null?(LinkedHashMap)list.get(0):dataMap;
			
			String last_year_salary = "1";
			String last_month_salary = "1";
			String current_salary = "1";
			
			if(map.get("LAST_YEAR_AMOUNT")!=null && !"0".equals(map.get("LAST_YEAR_AMOUNT").toString())){
				last_year_salary = map.get("LAST_YEAR_AMOUNT").toString();
			}
			
			if(map.get("LAST_MONTH_AMOUNT")!=null && !"0".equals(map.get("LAST_MONTH_AMOUNT").toString())){
				last_month_salary = map.get("LAST_MONTH_AMOUNT").toString();
			}
			
			if(map.get("CURRENT_AMOUNT")!=null && !"0".equals(map.get("CURRENT_AMOUNT").toString())){
				current_salary = map.get("CURRENT_AMOUNT").toString();
			}
			map.put("LAST_YEAR_AMOUNT", last_year_salary);
			map.put("LAST_MONTH_AMOUNT", last_month_salary);
			map.put("CURRENT_AMOUNT", current_salary);
		}
		return map;
	}
	
	/**
	 * 驻在员工资 (导出的为实发工资项目)C04专用--导出Excel用--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaSalaryZhuZaiYuanMap(HttpServletRequest request) {
		List list = new ArrayList() ;
		LinkedHashMap returnMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH", year+month);
		//paramMap.put("PA_ITEM", "NET_PAY_WAGE");//实发工资
		paramMap.put("PA_ITEM", "COMPANY_TOTAL");//工资总额、公司社保、住房公积金
		paramMap.put("EMP_TYPE", "TYPE");
		paramMap.put("EMP_TYPE_CODE", "3943");//驻在员
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return list;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return list;
		}
		list = paReportC04Dao.getPaItemSumByEmpType(paramMap);
		LinkedHashMap map = new LinkedHashMap();
		if(0==list.size()){
			returnMap.put("LAST_YEAR_SALARY", 0);
			returnMap.put("LAST_MONTH_SALARY", 0);
			returnMap.put("CURRENT_SALARY", 0);
		}else{
			map = list.get(0)!=null?(LinkedHashMap)list.get(0):map;
			if(map!=null){
				returnMap.put("LAST_YEAR_SALARY", map.get("LAST_YEAR_SALARY")!=null?map.get("LAST_YEAR_SALARY").toString():"");
				returnMap.put("LAST_MONTH_SALARY", map.get("LAST_MONTH_SALARY")!=null?map.get("LAST_MONTH_SALARY").toString():"");
				returnMap.put("CURRENT_SALARY", map.get("CURRENT_SALARY")!=null?map.get("CURRENT_SALARY").toString():"");
			}
		}
		
		return returnMap;
	}
	
	/**
	 * 中方 (导出的为实发工资项目)C04专用--导出Excel用--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaSalaryTotalChinaMap(HttpServletRequest request) {
		List list = new ArrayList() ;
		LinkedHashMap returnMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("PA_ITEM", "COMPANY_TOTAL");//实发工资
		paramMap.put("EMP_TYPE", "CHINA");//员工类别为空，取公司合计
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return list;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return list;
		}
		list = paReportC04Dao.getPaItemSumByEmpType(paramMap);
		LinkedHashMap map = new LinkedHashMap();
		if(0==list.size()){
			returnMap.put("LAST_YEAR_SALARY", 0);
			returnMap.put("LAST_MONTH_SALARY", 0);
			returnMap.put("CURRENT_SALARY", 0);
		}else{
			map = list.get(0)!=null?(LinkedHashMap)list.get(0):map;
			if(map!=null){
				returnMap.put("LAST_YEAR_SALARY", map.get("LAST_YEAR_SALARY")!=null?map.get("LAST_YEAR_SALARY").toString():"");
				returnMap.put("LAST_MONTH_SALARY", map.get("LAST_MONTH_SALARY")!=null?map.get("LAST_MONTH_SALARY").toString():"");
				returnMap.put("CURRENT_SALARY", map.get("CURRENT_SALARY")!=null?map.get("CURRENT_SALARY").toString():"");
			}
		}
		
		return returnMap;
	}
	
	/**
	 * 公司总计 (导出的为实发工资项目)C04专用--导出Excel用--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaSalaryTotalCompanyMap(HttpServletRequest request) {
		List list = new ArrayList() ;
		LinkedHashMap returnMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("PA_ITEM", "COMPANY_TOTAL");//实发工资
		paramMap.put("EMP_TYPE", "ALL");//员工类别为空，取公司合计
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return list;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return list;
		}
		list = paReportC04Dao.getPaItemSumByEmpType(paramMap);
		LinkedHashMap map = new LinkedHashMap();
		if(0==list.size()){
			returnMap.put("LAST_YEAR_SALARY", 1);
			returnMap.put("LAST_MONTH_SALARY", 1);
			returnMap.put("CURRENT_SALARY", 1);
		}else{
			map = list.get(0)!=null?(LinkedHashMap)list.get(0):map;
			if(map!=null){
				String last_year_salary = "1";
				String last_month_salary = "1";
				String current_salary = "1";
				
				if(map.get("LAST_YEAR_SALARY")!=null && !"0".equals(map.get("LAST_YEAR_SALARY").toString())){
					last_year_salary = map.get("LAST_YEAR_SALARY").toString();
				}
				
				if(map.get("LAST_MONTH_SALARY")!=null && !"0".equals(map.get("LAST_MONTH_SALARY").toString())){
					last_month_salary = map.get("LAST_MONTH_SALARY").toString();
				}
				
				if(map.get("CURRENT_SALARY")!=null && !"0".equals(map.get("CURRENT_SALARY").toString())){
					current_salary = map.get("CURRENT_SALARY").toString();
				}
				returnMap.put("LAST_YEAR_SALARY", last_year_salary);
				returnMap.put("LAST_MONTH_SALARY", last_month_salary);
				returnMap.put("CURRENT_SALARY", current_salary);
			}
		}
		
		return returnMap;
	}
	
	/**
	 * 正式员工 (导出的为实发工资项目)C04专用--导出Excel用--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getNormalEmpSalaryList(HttpServletRequest request) {
		List list = new ArrayList() ;
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE", "TYPE");
		paramMap.put("EMP_TYPE_CODE", "1369");//正式员工
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return list;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return list;
		}
		//正式员工需要的一些薪资项目{1应得基本工资,2加班费合计,3夜班费,4其它,5应得合计,6扣款合计,7工资总额,8社会保险(公司),9公积金(公司),10薪资合计}
		String paItems[] = {"BASE_SALARY","TOTAL_OT_FEE","NIGHT_SHIFT_FEE","OTHER_SALARY","TOTAL_MONTH_WAGE","DEDUCT_SALARY",
				"TOTAL_WAGE","SOCIAL_INSURANCE_COMPANY","HOUSE_FUNDING_COMPANY","TOTAL_SALARY"};
		for(int i=0;i<paItems.length;i++){
			LinkedHashMap returnMap = new LinkedHashMap();
			returnMap.put("EMP_TYPE_CODE", "1369");
			returnMap.put("PA_ITEM_NAME", paItems[i]);
			
			paramMap.remove("PA_ITEM");
			paramMap.put("PA_ITEM", paItems[i]);
			list = paReportC04Dao.getPaItemSumByEmpType(paramMap);
			
			LinkedHashMap map = new LinkedHashMap();
			if(0==list.size()){
				returnMap.put("LAST_YEAR_SALARY", 0);
				returnMap.put("LAST_MONTH_SALARY", 0);
				returnMap.put("CURRENT_SALARY", 0);
			}else{
				map = list.get(0)!=null?(LinkedHashMap)list.get(0):map;
				if(map!=null){
					returnMap.put("LAST_YEAR_SALARY", map.get("LAST_YEAR_SALARY")!=null?map.get("LAST_YEAR_SALARY").toString():"0");
					returnMap.put("LAST_MONTH_SALARY", map.get("LAST_MONTH_SALARY")!=null?map.get("LAST_MONTH_SALARY").toString():"0");
					returnMap.put("CURRENT_SALARY", map.get("CURRENT_SALARY")!=null?map.get("CURRENT_SALARY").toString():"0");
				}
			}
			returnList.add(returnMap);
		}
		
		return returnList;
	}
	
	/**
	 * 劳务派遣员工 (导出的为实发工资项目)C04专用--导出Excel用--工资支付现状(单月)
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaiQianEmpSalaryList(HttpServletRequest request) {
		List list = new ArrayList() ;
		List returnList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE", "TYPE");
		paramMap.put("EMP_TYPE_CODE", "14890");//劳务派遣
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return list;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return list;
		}
		//劳务派遣员工需要的一些薪资项目{1应得基本工资,2加班费合计,3夜班费,4其它,5应得合计,6扣款合计,7工资总额,8社会保险(公司),9薪资合计}
		String paItems[] = {"BASE_SALARY","TOTAL_OT_FEE","NIGHT_SHIFT_FEE","OTHER_SALARY","TOTAL_MONTH_WAGE","DEDUCT_SALARY",
				"TOTAL_WAGE","SOCIAL_INSURANCE_COMPANY","TOTAL_SALARY"};
		for(int i=0;i<paItems.length;i++){
			LinkedHashMap returnMap = new LinkedHashMap();
			returnMap.put("EMP_TYPE_CODE", "14890");
			returnMap.put("PA_ITEM_NAME", paItems[i]);
			
			paramMap.remove("PA_ITEM");
			paramMap.put("PA_ITEM", paItems[i]);
			list = paReportC04Dao.getPaItemSumByEmpType(paramMap);
			LinkedHashMap map = new LinkedHashMap();
			if(0==list.size()){
				returnMap.put("LAST_YEAR_SALARY", 0);
				returnMap.put("LAST_MONTH_SALARY", 0);
				returnMap.put("CURRENT_SALARY", 0);
			}else{
				map = list.get(0)!=null?(LinkedHashMap)list.get(0):map;
				if(map!=null){
					returnMap.put("LAST_YEAR_SALARY", map.get("LAST_YEAR_SALARY")!=null?map.get("LAST_YEAR_SALARY").toString():"0");
					returnMap.put("LAST_MONTH_SALARY", map.get("LAST_MONTH_SALARY")!=null?map.get("LAST_MONTH_SALARY").toString():"0");
					returnMap.put("CURRENT_SALARY", map.get("CURRENT_SALARY")!=null?map.get("CURRENT_SALARY").toString():"0");
				}
			}
			returnList.add(returnMap);
		}
		
		return returnList;
	}
	//------------------累计------------------------
	/**
	 * 查询销售金额（按月份计算）C04专用--导出Excel--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaSalaryAmountSumMap(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		map.put("LAST_YEAR", Integer.parseInt(year)-1);
		map.put("CURRENT_YEAR", year);
		map.put("MONTH", month);
		//47为工资输入项目的 销售金额
		paramMap.put("PARAM_NO", "47");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return map;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return map;
		}
		list = paReportC04Dao.getPaSalaryAmountSumList(paramMap);
		if(0==list.size()){//这些金额在页面要用作除数，所以不能为0
			map.put("LAST_YEAR_AMOUNT", 1);
			map.put("CURRENT_YEAR_AMOUNT", 1);
		}else{
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap = list.get(0)!=null?(LinkedHashMap)list.get(0):dataMap;
			String last_year_salary = "1";
			String current_year_salary = "1";

			if(map.get("LAST_YEAR_AMOUNT")!=null && !"0".equals(map.get("LAST_YEAR_AMOUNT").toString())){
				last_year_salary = map.get("LAST_YEAR_AMOUNT").toString();
			}
			if(map.get("CURRENT_YEAR_AMOUNT")!=null && !"0".equals(map.get("CURRENT_YEAR_AMOUNT").toString())){
				current_year_salary = map.get("CURRENT_YEAR_AMOUNT").toString();
			}
			map.put("LAST_YEAR_AMOUNT", last_year_salary);
			map.put("CURRENT_YEAR_AMOUNT", current_year_salary);
		}
		
		return map;
	}
	
	/**
	 * 查询生产金额（按月份算）C04专用--导出Excel--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaProductionAmountSumMap(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		//168为工资输入项目的 生产金额
		paramMap.put("PARAM_NO", "168");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return map;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return map;
		}
		list = paReportC04Dao.getPaProductionAmountSumList(paramMap);
		if(0==list.size()){//这些金额在页面要用作除数，所以不能为0
			map.put("LAST_YEAR_AMOUNT", 1);
			map.put("CURRENT_YEAR_AMOUNT", 1);
		}else{
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap = list.get(0)!=null?(LinkedHashMap)list.get(0):dataMap;
			
			String last_year_salary = "1";
			String current_year_salary = "1";

			if(map.get("LAST_YEAR_AMOUNT")!=null && !"0".equals(map.get("LAST_YEAR_AMOUNT").toString())){
				last_year_salary = map.get("LAST_YEAR_AMOUNT").toString();
			}
			if(map.get("CURRENT_YEAR_AMOUNT")!=null && !"0".equals(map.get("CURRENT_YEAR_AMOUNT").toString())){
				current_year_salary = map.get("CURRENT_YEAR_AMOUNT").toString();
			}
			map.put("LAST_YEAR_AMOUNT", last_year_salary);
			map.put("CURRENT_YEAR_AMOUNT", current_year_salary);
		}
		return map;
	}
	
	/**
	 * 驻在员工资 (导出的为实发工资项目)C04专用--导出Excel用--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaSalaryZhuZaiYuanSumMap(HttpServletRequest request) {
		List list = new ArrayList() ;
		LinkedHashMap returnMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH", year+month);
		//paramMap.put("PA_ITEM", "NET_PAY_WAGE");//实发工资
		paramMap.put("PA_ITEM", "COMPANY_TOTAL");//工资总额、公司社保、住房公积金
		paramMap.put("EMP_TYPE", "TYPE");
		paramMap.put("EMP_TYPE_CODE", "3943");//驻在员
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return list;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return list;
		}
		list = paReportC04Dao.getPaItemSumByEmpTypeByYear(paramMap);
		LinkedHashMap map = new LinkedHashMap();
		if(0==list.size()){
			returnMap.put("LAST_YEAR_SALARY", 0);
			returnMap.put("CURRENT_YEAR_SALARY", 0);
		}else{
			map = list.get(0)!=null?(LinkedHashMap)list.get(0):map;
			if(map!=null){
				returnMap.put("LAST_YEAR_SALARY", map.get("LAST_YEAR_SALARY")!=null?map.get("LAST_YEAR_SALARY").toString():"0");
				returnMap.put("CURRENT_YEAR_SALARY", map.get("CURRENT_YEAR_SALARY")!=null?map.get("CURRENT_YEAR_SALARY").toString():"0");
			}
		}
		
		return returnMap;
	}
	
	/**
	 * 中方 (导出的为实发工资项目)C04专用--导出Excel用--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaSalaryTotalChinaSumMap(HttpServletRequest request) {
		List list = new ArrayList() ;
		LinkedHashMap returnMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("PA_ITEM", "COMPANY_TOTAL");//实发工资
		paramMap.put("EMP_TYPE", "CHINA");//员工类别为空，取公司合计
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return list;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return list;
		}
		list = paReportC04Dao.getPaItemSumByEmpTypeByYear(paramMap);
		LinkedHashMap map = new LinkedHashMap();
		if(0==list.size()){
			returnMap.put("LAST_YEAR_SALARY", 0);
			returnMap.put("CURRENT_YEAR_SALARY", 0);
		}else{
			map = list.get(0)!=null?(LinkedHashMap)list.get(0):map;
			if(map!=null){
				returnMap.put("LAST_YEAR_SALARY", map.get("LAST_YEAR_SALARY")!=null?map.get("LAST_YEAR_SALARY").toString():"0");
				returnMap.put("CURRENT_YEAR_SALARY", map.get("CURRENT_YEAR_SALARY")!=null?map.get("CURRENT_YEAR_SALARY").toString():"0");
			}
		}
		
		return returnMap;
	}
	
	/**
	 * 公司总计 (导出的为实发工资项目)C04专用--导出Excel用--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaSalaryTotalCompanySumMap(HttpServletRequest request) {
		List list = new ArrayList() ;
		LinkedHashMap returnMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("PA_ITEM", "COMPANY_TOTAL");//实发工资
		paramMap.put("EMP_TYPE", "ALL");//员工类别为空，取公司合计
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return list;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return list;
		}
		list = paReportC04Dao.getPaItemSumByEmpTypeByYear(paramMap);
		LinkedHashMap map = new LinkedHashMap();
		if(0==list.size()){
			returnMap.put("LAST_YEAR_SALARY", 0);
			returnMap.put("CURRENT_YEAR_SALARY", 0);
		}else{
			map = list.get(0)!=null?(LinkedHashMap)list.get(0):map;
			if(map!=null){
				String last_year_salary = "1";
				String current_year_salary = "1";
				
				if(map.get("LAST_YEAR_SALARY")!=null && !"0".equals(map.get("LAST_YEAR_SALARY").toString())){
					last_year_salary = map.get("LAST_YEAR_SALARY").toString();
				}
				if(map.get("CURRENT_YEAR_SALARY")!=null && !"0".equals(map.get("CURRENT_YEAR_SALARY").toString())){
					current_year_salary = map.get("CURRENT_YEAR_SALARY").toString();
				}
				returnMap.put("LAST_YEAR_SALARY", last_year_salary);
				returnMap.put("CURRENT_YEAR_SALARY", current_year_salary);
			}
		}
		
		return returnMap;
	}
	
	/**
	 * 正式员工 (导出的为实发工资项目)C04专用--导出Excel用--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getNormalEmpSalarySumList(HttpServletRequest request) {
		List list = new ArrayList() ;
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE", "TYPE");
		paramMap.put("EMP_TYPE_CODE", "1369");//正式员工
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return list;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return list;
		}
		//正式员工需要的一些薪资项目{1应得基本工资,2加班费合计,3夜班费,4其它,5应得合计,6扣款合计,7工资总额,8社会保险(公司),9公积金(公司),10薪资合计}
		String paItems[] = {"BASE_SALARY","TOTAL_OT_FEE","NIGHT_SHIFT_FEE","OTHER_SALARY","TOTAL_MONTH_WAGE","DEDUCT_SALARY",
				"TOTAL_WAGE","SOCIAL_INSURANCE_COMPANY","HOUSE_FUNDING_COMPANY","TOTAL_SALARY"};
		for(int i=0;i<paItems.length;i++){
			LinkedHashMap returnMap = new LinkedHashMap();
			returnMap.put("EMP_TYPE_CODE", "1369");
			returnMap.put("PA_ITEM_NAME", paItems[i]);
			
			paramMap.remove("PA_ITEM");
			paramMap.put("PA_ITEM", paItems[i]);
			list = paReportC04Dao.getPaItemSumByEmpTypeByYear(paramMap);
			
			LinkedHashMap map = new LinkedHashMap();
			if(0==list.size()){
				returnMap.put("LAST_YEAR_SALARY", 0);
				returnMap.put("CURRENT_YEAR_SALARY", 0);
			}else{
				map = list.get(0)!=null?(LinkedHashMap)list.get(0):map;
				if(map!=null){
					returnMap.put("LAST_YEAR_SALARY", map.get("LAST_YEAR_SALARY")!=null?map.get("LAST_YEAR_SALARY").toString():"0");
					returnMap.put("CURRENT_YEAR_SALARY", map.get("CURRENT_YEAR_SALARY")!=null?map.get("CURRENT_YEAR_SALARY").toString():"0");
				}
			}
			returnList.add(returnMap);
		}
		
		return returnList;
	}
	
	/**
	 * 劳务派遣员工 (导出的为实发工资项目)C04专用--导出Excel用--工资支付现状(累计)
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaiQianEmpSalarySumList(HttpServletRequest request) {
		List list = new ArrayList() ;
		List returnList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE", "TYPE");
		paramMap.put("EMP_TYPE_CODE", "14890");//劳务派遣
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return list;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return list;
		}
		//劳务派遣员工需要的一些薪资项目{1应得基本工资,2加班费合计,3夜班费,4其它,5应得合计,6扣款合计,7工资总额,8社会保险(公司),9薪资合计}
		String paItems[] = {"BASE_SALARY","TOTAL_OT_FEE","NIGHT_SHIFT_FEE","OTHER_SALARY","TOTAL_MONTH_WAGE","DEDUCT_SALARY",
				"TOTAL_WAGE","SOCIAL_INSURANCE_COMPANY","TOTAL_SALARY"};
		for(int i=0;i<paItems.length;i++){
			LinkedHashMap returnMap = new LinkedHashMap();
			returnMap.put("EMP_TYPE_CODE", "14890");
			returnMap.put("PA_ITEM_NAME", paItems[i]);
			
			paramMap.remove("PA_ITEM");
			paramMap.put("PA_ITEM", paItems[i]);
			list = paReportC04Dao.getPaItemSumByEmpTypeByYear(paramMap);
			LinkedHashMap map = new LinkedHashMap();
			if(0==list.size()){
				returnMap.put("LAST_YEAR_SALARY", 0);
				returnMap.put("CURRENT_YEAR_SALARY", 0);
			}else{
				map = list.get(0)!=null?(LinkedHashMap)list.get(0):map;
				if(map!=null){
					returnMap.put("LAST_YEAR_SALARY", map.get("LAST_YEAR_SALARY")!=null?map.get("LAST_YEAR_SALARY").toString():"0");
					returnMap.put("CURRENT_YEAR_SALARY", map.get("CURRENT_YEAR_SALARY")!=null?map.get("CURRENT_YEAR_SALARY").toString():"0");
				}
			}
			returnList.add(returnMap);
		}
		
		return returnList;
	}
	
	
	
	
	
	
	
	
	/**
	 * 查询销售金额总和（按月份算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaSumSalaryAmountList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("START_MONTH", year+"01");//从1月份开始
		paramMap.put("END_MONTH", year+month);
		paramMap.put("PARAM_NO", "47");//47为工资输入项目的 销售金额
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportC04Dao.getPaSumSalaryAmountList(paramMap);
		LinkedHashMap map = new LinkedHashMap();
		if(0==returnList.size()){
			map.put("RETURN_VALUE1", 1);
			map.put("RETURN_VALUE2", 1);
		}else{
			map.put("RETURN_VALUE1",((LinkedHashMap)returnList.get(0)).get("RETURN_VALUE1")==null?1:((LinkedHashMap)returnList.get(0)).get("RETURN_VALUE1"));
			map.put("RETURN_VALUE2",((LinkedHashMap)returnList.get(0)).get("RETURN_VALUE2")==null?1:((LinkedHashMap)returnList.get(0)).get("RETURN_VALUE2"));
		}
		List list = new ArrayList();
		list.add(map);
		return list;
	}
	
	/**
	 * 查询驻在员总和（按月份算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaSalaryZhuZaiYuanZongHeList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("START_MONTH", year+"01");//从1月份开始
		paramMap.put("END_MONTH", year+month);
		paramMap.put("EMP_TYPE_CODE", "3943");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportC04Dao.getPaSalaryZhuZaiYuanZongHeList(paramMap);
		if(0==returnList.size()){
			String RETURN_VALUE = "1";
			returnList.add(RETURN_VALUE);
		}
		return returnList;
	}
	
	
	
	/**
	 * 查询生产金额总和（按月份算）导出Excel
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaSumProductionAmountList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("START_MONTH", year+"01");//从1月份开始
		paramMap.put("END_MONTH", year+month);
		paramMap.put("PARAM_NO", "47");//47为工资输入项目的 销售金额
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportC04Dao.getPaSumProductionAmountList(paramMap);
		
		LinkedHashMap map = new LinkedHashMap();
		if(0==returnList.size()){
			map.put("RETURN_VALUE1", 1);	
			map.put("RETURN_VALUE2", 1);	
		}else{
			map.put("RETURN_VALUE1",((LinkedHashMap)returnList.get(0)).get("RETURN_VALUE1")==null?1:((LinkedHashMap)returnList.get(0)).get("RETURN_VALUE1"));
			map.put("RETURN_VALUE2",((LinkedHashMap)returnList.get(0)).get("RETURN_VALUE2")==null?1:((LinkedHashMap)returnList.get(0)).get("RETURN_VALUE2"));
		}

		List list = new ArrayList();
		list.add(map);
		return list;
	}
	
	/**
	 * 工资支付现状（累计），导出Excel用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaPayOffStatusAddupPayOffList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("START_MONTH", year+"01");
		paramMap.put("END_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		
		returnList = paReportC04Dao.getPaPayOffStatusAddupPayOffList(paramMap);
		return returnList;
	}

	/**
	 * 工资支付现状（累计） 变动数据比较，结果大于0返回相减的结果   小于0的返回-1  等于0的返回0     导出Excel用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaPayOffStatusAddupPayOffResourceListGt(
			HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("START_MONTH", year+"01");
		paramMap.put("END_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		Map map = new LinkedHashMap();
		int count = 0;
		returnList = paReportC04Dao.getPaPayOffStatusAddupPayOffResourceListGt(paramMap);
		for (int i = 0; i < returnList.size(); i++) {
			map = (LinkedHashMap)returnList.get(i);
			Collection c = map.values();
			for (Iterator iterator = c.iterator(); iterator.hasNext();) {
				String s = iterator.next().toString();
				if("-1".equals(s)||"0".equals(s)){
					count++;
				}
			}
		}
		map.put("count", count);
		returnList.add(map);
		return returnList;
	}
	
	
	/**
	 * 查询工资支付现状报表      降低的个数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getPaPayOffStatusAddupPayOffResourceListLtCount(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("START_MONTH", year+"01");
		paramMap.put("END_MONTH", year+month);
		
		Map map = new LinkedHashMap();
		int count = 0;
		returnList = paReportC04Dao.getPaPayOffStatusAddupPayOffResourceListLt(paramMap);
		for (int i = 0; i < returnList.size(); i++) {
			map = (LinkedHashMap)returnList.get(i);
			Collection c = map.values();
			for (Iterator iterator = c.iterator(); iterator.hasNext();) {
				String s = iterator.next().toString();
				if("1".equals(s)||"0".equals(s)){
					count++;
				}
			}
		}
		map.put("count", count);
		return map;
	}
	
	/**
	 * 查询工资支付现状报表      提高的个数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getPaPayOffStatusAddupPayOffResourceListGtCount(
			HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("START_MONTH", year+"01");
		paramMap.put("END_MONTH", year+month);
		
		Map map = new LinkedHashMap();
		int count = 0;
		returnList = paReportC04Dao.getPaPayOffStatusAddupPayOffResourceListGt(paramMap);
		for (int i = 0; i < returnList.size(); i++) {
			map = (LinkedHashMap)returnList.get(i);
			Collection c = map.values();
			for (Iterator iterator = c.iterator(); iterator.hasNext();) {
				if("-1".equals(iterator.next().toString())){
					count++;
				}
			}
		}
		map.put("count", count);
		return map;
	}
	
	/**
	 * 工资支付现状（累计） 变动数据比较，结果小于0返回相减的结果   大于0的返回1  等于0的返回0     导出Excel用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaPayOffStatusAddupPayOffResourceListLt(
			HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("START_MONTH", year+"01");
		paramMap.put("END_MONTH", year+month);
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		
		returnList = paReportC04Dao.getPaPayOffStatusAddupPayOffResourceListLt(paramMap);
		return returnList;
	}

	@Override
	public Object getpaWageFundList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("START_MONTH", year+"01");
		paramMap.put("END_MONTH", year+"11");
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		returnList = paReportC04Dao.getpaWageFundList(paramMap);
		return returnList;
	}
	
	

	/**
	 * 按部门找出员工工资     正式工  生产部   直接  导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaSummarizeList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PRODUCTION_DISTINGUISH", "3328");//直接
		paramMap.put("EMP_TYPE_CODE", "1369");//正式工
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportC04Dao.getPaSummarizeList(paramMap);
		List deptList = new ArrayList() ;
		paramMap.put("PARENT1","C0421" );
		paramMap.put("PARENT2","C0426" );
		deptList= paReportC04Dao.getdeptNoListByParentNo(paramMap);
		for (int i = 0; i < deptList.size(); i++) {
			int num = 0;
			for (int j = 0; j < returnList.size(); j++) {
				if(((HashMap)returnList.get(j)).get("DEPTNO").equals(((HashMap)deptList.get(i)).get("DEPTNO"))){
					num=1;
					break;
				}
			}
			if(0==num){
				Map map = new HashMap();
				map.put("DEPTNO", ((LinkedHashMap)deptList.get(i)).get("DEPTNO"));
				map.put("PARENT_DEPT_NO", ((LinkedHashMap)deptList.get(i)).get("PARENT_DEPT_NO"));
				map.put("DEPT_NAME", ((LinkedHashMap)deptList.get(i)).get("DEPT_NAME"));
				returnList.add(map);
			}
			
		}
		return returnList;
	}

	/**
	 * 按部门找出员工工资     正式工  生产部   其他  导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPaSummarizeQiTaList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("EMP_TYPE_CODE", "1369");//正式工
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportC04Dao.getPaSummarizeList(paramMap);
		
		return returnList;
	}
	
	/**
	 * 正式工生产部工资小计   导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getZhengShiShengChanXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("EMP_TYPE_CODE", "1369");//正式工
		//如果没有cpny_id，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}
		//如果没有工资月，不进行查询
		if(year==null || "".equals(year) || month==null || "".equals(month)){
			return returnList;
		}
		returnList = paReportC04Dao.getZhengShiShengChanXiaoJiList(paramMap);
		
		return returnList;
	}

	/**
	 * 获取员工工资信息     正式工  管理部   间接    导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getZhengShiGuanLiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("EMP_TYPE_CODE", "1369");//正式工
		paramMap.put("PRODUCTION_DISTINGUISH", "3329");//间接
		returnList = paReportC04Dao.getZhengShiGuanLiList(paramMap);
		Map deptMap = new HashMap();
		deptMap.put("C041", "总经办");
		deptMap.put("C047", "办公室");
		deptMap.put("C048", "财务科");
		deptMap.put("C0415", "总务人事");
		deptMap.put("C0414", "食堂系");
		deptMap.put("C0413", "车管系");
		deptMap.put("C046", "库房");
		deptMap.put("C049", "采购科");
		deptMap.put("C0410", "贸易PART");
		
		
		Iterator it = deptMap.keySet().iterator();
		while(it.hasNext()){	
			String key = (String) it.next();	
			int num = 0;
			for (int j = 0; j < returnList.size(); j++) {
				if(key.equals(((HashMap)returnList.get(j)).get("DEPTNO"))){
					num=1;
					break;
				}
			}
			if(0==num){
				Map map = new HashMap();
				map.put("DEPTNO", key);
				map.put("DEPT_NAME", (deptMap.get(key)));
				returnList.add(map);
			}
		}
		return returnList;
	}

	/**
	 * 获取员工工资信息     正式工  管理部     间接小计     导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getZhengShiGuanLiXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("EMP_TYPE_CODE", "1369");//正式工
		paramMap.put("PRODUCTION_DISTINGUISH", "3329");//间接
		returnList = paReportC04Dao.getZhengShiGuanLiXiaoJiList(paramMap);
		
		return returnList;
	}

	/**
	 * 劳务工生产部直接 工资小计     导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaSummarizeLaoWuList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PRODUCTION_DISTINGUISH", "3328");//直接
		paramMap.put("EMP_TYPE_CODE", "14890");//劳务工
		
		returnList = paReportC04Dao.getPaSummarizeList(paramMap);
		int num = 0;
		for (int i = 0; i < returnList.size(); i++) {
			if(((LinkedHashMap)returnList.get(i)).get("DEPTNO").equals("C043")){
				num = 1;
				break;
				
			}
		}
		if(0==num){
			Map map = new HashMap();
			map.put("DEPT_NAME", "品质管理科");
			map.put("DEPTNO", "C043");
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 获取员工工资信息    劳务工  生产部   直接小计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getPaSummarizeLaoWuXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PRODUCTION_DISTINGUISH", "3328");//直接
		paramMap.put("EMP_TYPE_CODE", "14890");//劳务工
		
		returnList = paReportC04Dao.getPaSummarizeLaoWuXiaoJiList(paramMap);
		
		
		return returnList;
	}

	/**
	 * 获取员工工资信息    劳务工  生产部   间接小计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getLaoWuShengChanJianJieList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PRODUCTION_DISTINGUISH", "3328");//直接
		paramMap.put("EMP_TYPE_CODE", "14890");//劳务工
		
		returnList = paReportC04Dao.getPaSummarizeLaoWuXiaoJiList(paramMap);
		
		return returnList;
	}

	/**
	 * 获取员工工资信息    劳务工  管你部   间接
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getLaoWuGuanLiJianJieList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PRODUCTION_DISTINGUISH", "3329");//间接
		paramMap.put("EMP_TYPE_CODE", "14890");//劳务工
		
		returnList = paReportC04Dao.getLaoWuGuanLiJianJieList(paramMap);

		Map deptMap = new HashMap();
		deptMap.put("C0414", "食堂系");
		deptMap.put("C0415", "总务人事");
		deptMap.put("C046", "库房");
		
		
		Iterator it = deptMap.keySet().iterator();
		while(it.hasNext()){	
			String key = (String) it.next();	
			int num = 0;
			for (int j = 0; j < returnList.size(); j++) {
				if(key.equals(((HashMap)returnList.get(j)).get("DEPTNO"))){
					num=1;
					break;
				}
			}
			if(0==num){
				Map map = new HashMap();
				map.put("DEPTNO", key);
				map.put("DEPT_NAME", (deptMap.get(key)));
				returnList.add(map);
			}
		}
		return returnList;
	}

	/**
	 * 获取员工工资信息    劳务工  管你部   间接小计   导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getLaoWuGuanLiJianJieXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PRODUCTION_DISTINGUISH", "3328");//间接
		paramMap.put("EMP_TYPE_CODE", "14890");//劳务工
		
		returnList = paReportC04Dao.getLaoWuGuanLiJianJieXiaoJiList(paramMap);

		return returnList;
	}

	/**
	 * 获取员工工资信息   驻在员   导出Excel用 工资汇总表 
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getZhuZaiYuanList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("EMP_TYPE_CODE", "3943"); 
		
		returnList = paReportC04Dao.getZhuZaiYuanList(paramMap);

		return returnList;
	}

	/**
	 * 获取员工工资信息   全公司中方合计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getQuanGongSiZhongFangList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		
		returnList = paReportC04Dao.getQuanGongSiZhongFangList(paramMap);

		return returnList;
	}

	/**
	 * 获取员工工资信息       生产部  
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getShengChanList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		
		returnList = paReportC04Dao.getShengChanList(paramMap);

		List binggan = new ArrayList() ;
		List kouxiangtang = new ArrayList() ;
		LinkedHashMap map = new LinkedHashMap();
//		for (int i = 0; i < returnList.size(); i++) {
//			int num = 0;
//			String ideptno = null;
//			String jdeptno = null;
//			String iempTypeCode = null;
//			String jempTypeCode = null;
//			String parentdeptno = null;
//			for (int j = i+1; j < returnList.size(); j++) {
//				ideptno = (String) ((LinkedHashMap)returnList.get(i)).get("DEPTNO");
//				jdeptno = (String) ((LinkedHashMap)returnList.get(j)).get("DEPTNO");
//				iempTypeCode = (String) ((LinkedHashMap)returnList.get(i)).get("EMP_TYPE_CODE");
//				jempTypeCode = (String) ((LinkedHashMap)returnList.get(j)).get("EMP_TYPE_CODE");
//				parentdeptno = (String)((LinkedHashMap)returnList.get(i)).get("PARENT_DEPT_NO");
//				if("C0426".equals(parentdeptno)&& ideptno.equals(jdeptno)&&(iempTypeCode!=jempTypeCode)){
//					binggan.add(returnList.get(i));
//					binggan.add(returnList.get(j));
//					returnList.remove(i);
//					returnList.remove(j);
//					num++;
//					break;
//				}else if("C0421".equals(parentdeptno)&& ideptno.equals(jdeptno)&&(iempTypeCode!=jempTypeCode)){
//					kouxiangtang.add(returnList.get(i));
//					kouxiangtang.add(returnList.get(j));
//					returnList.remove(i);
//					returnList.remove(j);
//					num++;
//					break;
//				}
//				
//			}
//			if(0==num&&("C0421".equals(parentdeptno)||"C0426".equals(parentdeptno))){
//				Map tempMap = new HashMap();
//				tempMap.put("DEPTNO", ((LinkedHashMap)returnList.get(i)).get("DEPTNO"));
//				tempMap.put("PARENT_DEPT_NO", ((LinkedHashMap)returnList.get(i)).get("PARENT_DEPT_NO"));
//				tempMap.put("DEPT_NAME", ((LinkedHashMap)returnList.get(i)).get("DEPT_NAME"));
//				if(iempTypeCode.equals("1369")){
//					tempMap.put("EMP_TYPE_CODE", "1369");
//				}else{
//					tempMap.put("EMP_TYPE_CODE", "14890");
//				}
//				returnList.remove(i);
//			}
//		}
		return returnList;
	}

	/**
	 * 获取员工工资信息       生产部  
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getShengChanXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("DEPT_NO", "1");
		
		returnList = paReportC04Dao.getShengChanXiaoJiList(paramMap);

		return returnList;
	}

	/**
	 * 获取员工工资信息   部门  小计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getShengChanBuMenXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PARENT_DEPT_NO", "1");
		
		returnList = paReportC04Dao.getShengChanXiaoJiList(paramMap);

		return returnList;
	}

	/**
	 * 获取员工工资信息   部门  小计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getShengChanBuMenHeJiXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PARENT_DEPT_NO", "0");
		
		returnList = paReportC04Dao.getShengChanXiaoJiList(paramMap);

		return returnList;
	}

	/**
	 * 获取员工工资信息   生产直接合计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getShengChanZhiJieHeJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("EMP_TYPE_CODE", "1");
		returnList = paReportC04Dao.getShengChanZhiJieHeJiList(paramMap);

		return returnList;
	}

	/**
	 * 获取员工工资信息   生产直接合计里面的小计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getShengChanZhiJieHeJiXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		
		
		returnList = paReportC04Dao.getShengChanZhiJieHeJiList(paramMap);

		return returnList;
	}

	/**
	 * 获取员工工资信息    生产总和
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getShengChanZongHeList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("EMP_TYPE_CODE", "1");
		paramMap.put("DEPT1", "C0422");
		paramMap.put("DEPT2", "C0423");
		paramMap.put("DEPT3", "C0424");
		paramMap.put("DEPT4", "C0425");
		paramMap.put("DEPT5", "C0427");
		paramMap.put("DEPT6", "C0428");
		paramMap.put("DEPT7", "C0429");
		paramMap.put("DEPT8", "C0418");
		paramMap.put("DEPT9", "C0419");
		returnList = paReportC04Dao.getShengChanZongHeList(paramMap);

		return returnList;
	}

	/**
	 * 获取员工工资信息    生产总和小计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getShengChanZongHeXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("DEPT1", "C0422");
		paramMap.put("DEPT2", "C0423");
		paramMap.put("DEPT3", "C0424");
		paramMap.put("DEPT4", "C0425");
		paramMap.put("DEPT5", "C0427");
		paramMap.put("DEPT6", "C0428");
		paramMap.put("DEPT7", "C0429");
		paramMap.put("DEPT8", "C0418");
		paramMap.put("DEPT9", "C0419");
		
		returnList = paReportC04Dao.getShengChanZongHeList(paramMap);

		return returnList;
	}

	/**
	 * 获取总务人事小计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getZongWuRenShiXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("EMP_TYPE_CODE", "1");
		paramMap.put("DEPT1", "C0410");
		paramMap.put("DEPT2", "C0413");
		paramMap.put("DEPT3", "C0414");
		paramMap.put("DEPT4", "C0415");
		paramMap.put("DEPT5", "");
		paramMap.put("DEPT6", "");
		paramMap.put("DEPT7", "");
		paramMap.put("DEPT8", "");
		paramMap.put("DEPT9", "");
		
		returnList = paReportC04Dao.getShengChanZongHeList(paramMap);

		return returnList;
	}

	/**
	 * 获取总务人事小计 总小计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getZongWuRenShiZongXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		
		paramMap.put("DEPT1", "C0410");
		paramMap.put("DEPT2", "C0413");
		paramMap.put("DEPT3", "C0414");
		paramMap.put("DEPT4", "C0415");
		paramMap.put("DEPT5", "");
		paramMap.put("DEPT6", "");
		paramMap.put("DEPT7", "");
		paramMap.put("DEPT8", "");
		paramMap.put("DEPT9", "");
		
		returnList = paReportC04Dao.getShengChanZongHeList(paramMap);

		return returnList;
	}

	/**
	 * 管理部合计       正式 劳务 的
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getGuanLiBuHeJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("EMP_TYPE_CODE", "1");
		paramMap.put("DEPT1", "C0410");
		paramMap.put("DEPT2", "C0413");
		paramMap.put("DEPT3", "C0414");
		paramMap.put("DEPT4", "C0415");
		paramMap.put("DEPT5", "C048");
		paramMap.put("DEPT6", "C046");
		paramMap.put("DEPT7", "C047");
		paramMap.put("DEPT8", "");
		paramMap.put("DEPT9", "");
		
		returnList = paReportC04Dao.getShengChanZongHeList(paramMap);

		return returnList;
	}

	/**
	 * 管理部合计    总小计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getGuanLiBuHeJiXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("DEPT1", "C0410");
		paramMap.put("DEPT2", "C0413");
		paramMap.put("DEPT3", "C0414");
		paramMap.put("DEPT4", "C0415");
		paramMap.put("DEPT5", "C048");
		paramMap.put("DEPT6", "C046");
		paramMap.put("DEPT7", "C047");
		paramMap.put("DEPT8", "");
		paramMap.put("DEPT9", "");
		
		returnList = paReportC04Dao.getShengChanZongHeList(paramMap);

		return returnList;
	}

	/**
	 *驻在员  科别考勤表
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getZhuZaiYuanMonthList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		paramMap.put("EMP_TYPE_CODE", "3943");
		
		returnList = paReportC04Dao.getZhuZaiYuanMonthList(paramMap);

		return returnList;
	}

	/**
	 * 获取公司总总计  正式 劳务 分别 合计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getGongSiZongJiList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		
		returnList = paReportC04Dao.getGongSiZongJiList(paramMap);

		return returnList;
	}

	/**
	 * 获取公司总计人数
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getGongSiZongJiRSList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		
		int day = Integer.parseInt(year+month);
		
		List rsList = paReportC04Dao.getGongSiZongJiRenShuList(paramMap);
		Map map = new HashMap();
		int num1 = 0;
		int num2 = 0;
		int num3 = 0;
		int num4 = 0;
		
		for (int i = 0; i < rsList.size(); i++) {
			
			if(((LinkedHashMap)rsList.get(i)).get("PA_MONTH")!=null && ((day-100)+"").equals(((LinkedHashMap)rsList.get(i)).get("PA_MONTH")) ){
				num1 = 1;
			}
			if(((LinkedHashMap)rsList.get(i)).get("PA_MONTH")!=null && (year+month).equals(((LinkedHashMap)rsList.get(i)).get("PA_MONTH")) ){
				num2 = 1;
			}
			
			if(((LinkedHashMap)rsList.get(i)).get("PA_MONTH")!=null && ("HEJI1").equals(((LinkedHashMap)rsList.get(i)).get("PA_MONTH")) ){
				num3 = 1;
			}
			if(((LinkedHashMap)rsList.get(i)).get("PA_MONTH")!=null && ("HEJI2").equals(((LinkedHashMap)rsList.get(i)).get("PA_MONTH")) ){
				num4 = 1;
			}
			
		}
		for (int i = 0; i < rsList.size(); i++) {
			
			if(num1 == 0){
				map.put("L1", "1");
				map.put("Z1", "1");
			}else {
				if(((day-100)+"").equals(((LinkedHashMap)rsList.get(i)).get("PA_MONTH"))&&"1369".equals(((LinkedHashMap)rsList.get(i)).get("EMP_TYPE_CODE"))){
					map.put("Z1", ((LinkedHashMap)rsList.get(i)).get("RS"));
					continue;
				}else if(((day-100)+"").equals(((LinkedHashMap)rsList.get(i)).get("PA_MONTH"))&&"14890".equals(((LinkedHashMap)rsList.get(i)).get("EMP_TYPE_CODE"))){
					map.put("L1", ((LinkedHashMap)rsList.get(i)).get("RS"));
					continue;
				}
			}
			if(num2 == 0){
				map.put("L2", "1");
				map.put("Z2", "1");
			}else {
				if(((day)+"").equals(((LinkedHashMap)rsList.get(i)).get("PA_MONTH"))&&"1369".equals(((LinkedHashMap)rsList.get(i)).get("EMP_TYPE_CODE"))){
					map.put("Z2", ((LinkedHashMap)rsList.get(i)).get("RS"));
					continue;
				}else if(((day)+"").equals(((LinkedHashMap)rsList.get(i)).get("PA_MONTH"))&&"14890".equals(((LinkedHashMap)rsList.get(i)).get("EMP_TYPE_CODE"))){
					map.put("L2", ((LinkedHashMap)rsList.get(i)).get("RS"));
					continue;
				}
			}
			if(num3 == 0){
				map.put("L3", "1");
				map.put("Z3", "1");
			}else {
				if(("HEJI1").equals(((LinkedHashMap)rsList.get(i)).get("PA_MONTH"))&&"1369".equals(((LinkedHashMap)rsList.get(i)).get("EMP_TYPE_CODE"))){
					map.put("Z3", ((LinkedHashMap)rsList.get(i)).get("RS"));
					continue;
				}else if(("HEJI1").equals(((LinkedHashMap)rsList.get(i)).get("PA_MONTH"))&&"14890".equals(((LinkedHashMap)rsList.get(i)).get("EMP_TYPE_CODE"))){
					map.put("L3", ((LinkedHashMap)rsList.get(i)).get("RS"));
					continue;
				}
			}
			if(num4 == 0){
				map.put("L4", "1");
				map.put("Z4", "1");
			}else {
				if(("HEJI2").equals(((LinkedHashMap)rsList.get(i)).get("PA_MONTH"))&&"1369".equals(((LinkedHashMap)rsList.get(i)).get("EMP_TYPE_CODE"))){
					map.put("Z4", ((LinkedHashMap)rsList.get(i)).get("RS"));
					continue;
				}else if(("HEJI2").equals(((LinkedHashMap)rsList.get(i)).get("PA_MONTH"))&&"14890".equals(((LinkedHashMap)rsList.get(i)).get("EMP_TYPE_CODE"))){
					map.put("L4", ((LinkedHashMap)rsList.get(i)).get("RS"));
					continue;
				}
			}
		}
		returnList.add(map);

		return returnList;
	}

	/**
	 * 获取公司     总计的小计
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getGongSiZongJiXiaoJiList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		
		returnList = paReportC04Dao.getGongSiZongJiXiaoJiList(paramMap);

		return returnList;
	}

	/**
	 * 获取公司      销售金额
	 * @param request
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getXiaoShouJinEList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("PA_MONTH", year+month);
		
		returnList = paReportC04Dao.getXiaoShouJinEList(paramMap);

		return returnList;
	}

}
