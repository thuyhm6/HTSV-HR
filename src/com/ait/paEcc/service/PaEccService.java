package com.ait.paEcc.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface PaEccService {

	public List getEccEmpInfo(HttpServletRequest request);
	
	public int getEccEstEmpCn(HttpServletRequest request);
	
	/**
	 * 预估补偿金--得到记录
	 */
	public List getEmpPaEcc(HttpServletRequest request);
	
	/**
	 * 计算--得到T_PA_ECC_RESULT中的记录
	 * 
	 */
	public List getPaEccInfo(HttpServletRequest request);
	
	public int getPaEccCnt(HttpServletRequest request);
	
	/**
	 * 计算--删除T_PA_ECC_RESULT中的记录
	 */
	public int delPaEccInfo(HttpServletRequest request);
	
	public int calculatePaEcc(Map parameterMap);
	
	public int updatePaEccInfo(List list);
	
	public int settlementPaEcc(HttpServletRequest request);
	
	public int cancelSettlementPaEcc(HttpServletRequest request);
	
	public List searchBatchesByPaMonth(Map parameterMap);
	
	public String getEccFlag(HttpServletRequest request);
	
	public Map getResignInfo(HttpServletRequest request); 
	
	public List getEccEmpPaInfo(HttpServletRequest request,Map parameterMap);
	
	public String getEmpPAStartMonth(Object parameterObject);
}
