package com.ait.pa.service.salary;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface PaProgressSer {
	
	@SuppressWarnings("unchecked")
	public List getPaProgressList(HttpServletRequest request) ;	
	
	public int getPaProgressCnt(HttpServletRequest request);
	
	public int updatePaProgressInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPawithholdingProgressList(HttpServletRequest request) ;	
	@SuppressWarnings("unchecked")
	public int getPawithholdingProgressCnt(HttpServletRequest request);
	
	
	@SuppressWarnings("unchecked")
	public List getDeptDistinguishList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaProgressByDeptList(HttpServletRequest request) ;	
	
	public int getPaProgressByDeptCnt(HttpServletRequest request);
	
	public int updatePaProgressByDept(HttpServletRequest request) ;
	
	public int deletePaProgressByDept(HttpServletRequest request) ;
	
	public int checkPaProgressByDeptCnt(HttpServletRequest request);
	
	public int copyToNextMonthPaProgressByDept(HttpServletRequest request) ;

	/**
	 * 工资锁定 根据工资月查询出 工资发放日期
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-11-4 下午2:24:32 
	* @version V1.0
	 */
	public List getSalaryLockDatePa(HttpServletRequest request);

	/**
	 * 根据工资发放日查询区间
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-11-4 下午4:27:15 
	* @version V1.0
	 */
	public List getSalaryLockStatNo(HttpServletRequest request);
	
}
