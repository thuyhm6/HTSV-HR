package com.ait.inct.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * @fileName SalesmanIncentiveDao.java
 * @author 			@Date			@version		@Desc
 * ---------------------------------------------------------------------------------------------------------------
 * penghaixia 		2014-6-4 		1.0				initial  
 *----------------------------------------------------------------------------------------------------------------
 */
public interface SalesmanIncentiveDao {
	/*营业员提成*/
	@SuppressWarnings("unchecked")
	public List getIncentiveCalcList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getIncentiveCalcList(Object object);
	@SuppressWarnings("unchecked")
	public int getIncentiveCalcListCnt(Object object);
	
	//取工资关帐标志
	@SuppressWarnings("unchecked")
	public int getSalesInctCalcClosFlagByQuarter(Object object);
	@SuppressWarnings("unchecked")
	public int getSalesInctAccrualCalcClosFlagByQuarter(Object obj);
	@SuppressWarnings("unchecked")
	public int getSalesInctCalcClosFlagByMonth(Object object);
	@SuppressWarnings("unchecked")
	public int getSalesInctAccrualCalcClosFlagByMonth(Object obj);
	
	/*营业员提成计算执行*/
	@SuppressWarnings("unchecked")
	public String callSalesmanIncentiveCalc(Map paramMap) throws Exception;
	/*营业员提成 读取变动工资执行执行*/
	@SuppressWarnings("unchecked")
	public String callSalesmanVariablePayRead(Map paramMap) throws Exception;
	
	/* 营业员提成数据 excel导入 */
	@SuppressWarnings("unchecked")
	public List getSalesIncCalculateImportList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getSalesIncCalculateImportList(Object object);	
	@SuppressWarnings("unchecked")
	public int getSalesIncCalculateImportListCnt(Object object);
	@SuppressWarnings("unchecked")
	public int getSalesIncCalculateImportErrCnt(Object object);	
	@SuppressWarnings("unchecked")
	public String callSalesmanIncentiveCalcImport(Map paramMap) throws Exception;
	
	/*营业员提成  员工别提成修改 */
	@SuppressWarnings("unchecked")
	public Map getSalesIncentiveCalcItem(Object object);
	@SuppressWarnings("unchecked")
	public void updateSalesIncAdjustByEmp(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public void updateSalesIncCalculateByEmp(Object object) throws Exception;
	/*营业员提成  员工别提成修改 (申请)*/
	@SuppressWarnings("unchecked")
	public int getIncCalcAdjuMstReqId(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public void insertIncCalcAdjuMst(Object object)  throws Exception;
	@SuppressWarnings("unchecked")
	public void insertIncCalcAdjuDtl(Object object)  throws Exception;
	/*营业员提成  员工别提成修改 (决裁)*/
	@SuppressWarnings("unchecked")
	public void insertAffirmor(Object object)  throws Exception;
	@SuppressWarnings("unchecked")
	public void updateEssAffirm(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public void deleteAffirmor(Object object)  throws Exception;
	@SuppressWarnings("unchecked")
	public void updateCheckFlagByEssAffirmNo(Object object)  throws Exception;
	@SuppressWarnings("unchecked")
	public void affirmSalesmanInctCalcAdju(Object object)  throws Exception;
	@SuppressWarnings("unchecked")
	public void approveSalesmanInctCalcAdju(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public List getAffirmorList(Object object);
	@SuppressWarnings("unchecked")
	public List getCheckList(Object object);
	
	/*营业员提成调整 */
	@SuppressWarnings("unchecked")
	public List getIncentiveCalcAdjuList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getIncentiveCalcAdjuList(Object object);
	@SuppressWarnings("unchecked")
	public int getIncentiveCalcAdjuListCnt(Object object);
	@SuppressWarnings("unchecked")
	public Map getIncentiveCalcAdjuListByReqId(Object object);
	@SuppressWarnings("unchecked")
	public List getIncentiveCalcAdjuDtlList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getIncentiveCalcAdjuDtlList(Object object);
	@SuppressWarnings("unchecked")
	public int getIncentiveCalcAdjuDtlListCnt(Object object);
	
}
