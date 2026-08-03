package com.ait.report.pa.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaReportC01Ser.java
 * @Description: interface Class PaReportC01Ser.java
 * @Create date: Sep 14, 2012 5:29:38 PM
 * @Create by: lufeng (lufeng@ait.net.cn)
 * @version 5.1
 */
public interface PaReportC01Ser {
	/**
	 * 乐天玛特实发薪资汇总表（总公司）报表页面,Excel导出用
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaActualSalaryExcelList(HttpServletRequest request) ;
	
	/**
	 * 乐天玛特薪资成本汇总表（总公司）报表页面,Excel导出用
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaPayrollCostsExcelList(HttpServletRequest request) ;
	
	/**
	 * 查询系统时间 (query the sysdate )
	 * @param request
	 * @return Object
	 */
	public Object getSysdate(HttpServletRequest request);
	
	/**
	 * 按月份查询异常明细里的人员异常明细情况
	 * @param request
	 * @return Object
	 */
	public Object getAbnormalEmpExcelList(HttpServletRequest request);
	
	/**
	 * 查询上月月份 (query the sysdate )
	 * @param request
	 * @return Object
	 */
	public Object getLastMonthStr(HttpServletRequest request);
	
	/**
	 * 乐天玛特异常明细--保险情况明细报表页面,Excel导出用
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAbnormalInsExcelList(HttpServletRequest request) ;
	
	/**
	 * 乐天玛特--外派人员薪资信息报表信息,Excel导出用
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDispatchEmpPaExcelList(HttpServletRequest request) ;
	
	/**
	 * 乐天玛特--外派人员保险信息报表信息,Excel导出用
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDispatchEmpInsExcelList(HttpServletRequest request) ;
}
