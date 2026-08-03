package com.ait.pa.service.salary;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface PaLowAdjustSer {
	
	/**
	 * 最低工资调整列表(get low salary list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getLowSalaryList(HttpServletRequest request) throws Exception;

	/**
	 * 最低工资调整数(get low salary count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getLowSalaryListCnt(HttpServletRequest request) throws Exception;

	/**
	 * 批量提交最低工资调整(update Low Salary Batch)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int updateLowSalaryBatch(HttpServletRequest request);

	/**
	 * 撤销调整(cancel low adjust)
	 * @param request
	 * @return 
	 * @throws Exception
	 */
	public int cancellowadjust(HttpServletRequest request);

	/**
	 * 最低工资调整列表-非促销员(get low salary list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getFeiCuLowSalaryList(HttpServletRequest request) throws Exception;

	/**
	 * 最低工资调整数-非促销员(get low salary count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getFeiCuLowSalaryListCnt(HttpServletRequest request) throws Exception;
	/**
	 * 最低工资调整数-非促销员-TR(get low salary count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getFeiCuLowSalaryListTRCnt(HttpServletRequest request) throws Exception;
	/**
	 * 最低工资调整列表-非促销员-TA(get low salary list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getFeiCuLowSalaryForTAList(HttpServletRequest request) throws Exception;
	/**
	 * 最低工资调整列表-非促销员-TR(get low salary list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getFeiCuLowSalaryForTRList(HttpServletRequest request) throws Exception;
	
	/**
	 * 最低工资调整列表-TA(get low salary list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getLowSalaryForTAList(HttpServletRequest request) throws Exception;
}
