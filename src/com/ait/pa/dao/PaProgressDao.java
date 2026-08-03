package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;


public interface PaProgressDao {

	@SuppressWarnings("unchecked")
	public List getPaProgressList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaProgressList(Object object, int currentPage, int pageSize);
	
	public int getPaProgressCnt(Object object);
	
	
	@SuppressWarnings("unchecked")
	public List getPawithholdingProgressList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPawithholdingProgressList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getPawithholdingProgressCnt(Object object);
	
	
	public int updatePaProgressInfo(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getDeptDistinguishList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaProgressByDeptList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaProgressByDeptList(Object object, int currentPage, int pageSize);
	
	public int getPaProgressByDeptCnt(Object object);
	
	public int updatePaProgressByDept(Object object) ;
	
	public int deletePaProgressByDept(Object object) ;
	
	public int checkPaProgressByDeptCnt(Object object);
	
	public int copyToNextMonthPaProgressByDept(Object object) ;

	/**
	 * 工资锁定 根据工资月查询出 工资发放日期
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-11-4 下午2:30:08 
	* @version V1.0
	 */
	public List getSalaryLockDatePa(Object obj);

	/**
	 * 根据工资发放日查询区间
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-11-4 下午4:28:24 
	* @version V1.0
	 */
	public List getSalaryLockStatNo(Object obj);
	
}
