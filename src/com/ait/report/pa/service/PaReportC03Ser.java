package com.ait.report.pa.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface PaReportC03Ser {
	/**
	 * 导出给予现状汇总报表
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPacurrentRenditionCollectList(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
	public List getPacurrentRenditionCollectList2(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPacurrentRenditionCollectList3(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPacurrentRenditionCollectList4(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
	public List getPacurrentRenditionCollectList5(HttpServletRequest request) ;
	
	public List getPaCurrentRenditionList(HttpServletRequest request);
	
	public int empCount1(HttpServletRequest request) ;
	
	public int empCount2(HttpServletRequest request) ;
	
	public int empCount3(HttpServletRequest request) ;
	
	public int empCount4(HttpServletRequest request) ;

	List getPaCurrentRenditionSumAvg(HttpServletRequest request);

	/**
	 * 小时工工资发放表
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaHourWorkInfo(LinkedHashMap paramMap);

	/**
	 * 各部门小时工工资发放表
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<?> getPaHourWorkInfoByDeptNo(LinkedHashMap paramMap);

	/**
	 * 总小时工工资
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<?> getSumPaHourWorkInfo(LinkedHashMap paramMap);
}
