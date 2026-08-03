package com.ait.ar.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * @author Administrator
 *   业务接口
 */
public interface ArEatMealCountSer {
	@SuppressWarnings("unchecked")
	public List<String> initMealCount(LinkedHashMap paramMap) ;
	
	@SuppressWarnings("unchecked")
	public List getEatMealCountList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getEatMealCountCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getEatMealCountExcelList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getEatMealCountPersonList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getEatMealCountPersonListCnt(HttpServletRequest request) ;
	
	public String addBatchEatCount(HttpServletRequest request)throws Exception;
	
	public int deleteArEatCountInfo(HttpServletRequest request)throws Exception;
	
	public int updateArEatCountInfo(HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getEmpMealCountByDateList(HttpServletRequest request);
}
