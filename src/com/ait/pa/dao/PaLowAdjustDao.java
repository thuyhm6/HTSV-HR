package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public interface PaLowAdjustDao {

	/**
	 * 最低工资调整列表(get low salary list)
	 * 
	 * @param object
	 * 			currentPage
	 * 			pageSize
	 * @return	List
	 * @throws Exception
	 */
	public List getLowSalaryList(Object object, int currentPage, int pageSize) throws Exception;

	/**
	 * 最低工资调整数(get low salary count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getLowSalaryListCnt(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getLowSalaryList(Object object) ;

	public int updateLowSalaryBatch(List encapsulationLowSalaryBatch) throws Exception;

	public void cancellowadjust(Map map) throws Exception;

	/**
	 * 最低工资调整列表-非促销员(get low salary list)
	 * 
	 * @param object
	 * 			currentPage
	 * 			pageSize
	 * @return	List
	 * @throws Exception
	 */
	public List getFeiCuLowSalaryList(Object object, int currentPage, int pageSize) throws Exception;

	/**
	 * 最低工资调整数-非促销员(get low salary count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getFeiCuLowSalaryListCnt(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public List getFeiCuLowSalaryList(Object object) ;
	
	/**
	 * 最低工资调整列表-非促销员-TA(get low salary list)
	 * 
	 * @param object
	 * 			currentPage
	 * 			pageSize
	 * @return	List
	 * @throws Exception
	 */
	public List getFeiCuLowSalaryForTAList(Object object, int currentPage, int pageSize) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getFeiCuLowSalaryForTAList(Object object) ;
	
	/**
	 * 最低工资调整列表-TA(get low salary list)
	 * 
	 * @param object
	 * 			currentPage
	 * 			pageSize
	 * @return	List
	 * @throws Exception
	 */
	public List getLowSalaryForTAList(Object object, int currentPage, int pageSize) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getLowSalaryForTAList(Object object) ;
	@SuppressWarnings("unchecked")
	public List getFeiCuLowSalaryForTRList(Object object);
	/**
	 * 最低工资调整列表-非促销员-TR(get low salary list)
	 * 
	 * @param object
	 * 			currentPage
	 * 			pageSize
	 * @return	List
	 * @throws Exception
	 */
	public List getFeiCuLowSalaryForTRList(Object object, int currentPage, int pageSize);
	/**
	 * 最低工资调整数-非促销员TR(get low salary count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getFeiCuLowSalaryListTRCnt(Object object);
}
