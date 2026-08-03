package com.ait.report.pa.dao;

import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaReportC01Dao.java
 * @Description: interface Class PaReportC01Dao.java
 * @Create date: Sep 14, 2012 5:29:38 PM
 * @Create by: lufeng (lufeng@ait.net.cn)
 * @version 5.1
 */
public interface PaReportC01Dao {
	
	/**
	 * 跳转到乐天玛特实发薪资汇总表（总公司）报表页面
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaActualSalaryExcelList(Object object);
	
	/**
	 * 跳转到乐天玛特薪资成本汇总表（总公司）报表页面
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaPayrollCostsExcelList(Object object);
	
	/**
	 * 查询系统时间 (query the sysdate )
	 * @param object
	 * @return Object
	 */
	public Object getSysdate();
	
	/**
	 * 查询上月月份 (query the sysdate )
	 * @param object
	 * @return Object
	 */
	public Object getLastMonthStr(Object object);
	
	/**
	 * 根据门店编号查询门店名称
	 * @param object
	 * @return Object
	 */
	public Object getDistinguishNameByNo(Object object);
	
	/**
	 * 按月份查询某月末员工在职人数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getEmpInfoListByMonth(Object object);
	
	/**
	 * 按月份查询某门店调入人数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDiaoRuList(Object object);
	
	/**
	 * 按月份查询某门店调出人数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDiaoChuList(Object object);
	
	/**
	 * 按月份查询某门店入职人数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getRuZhiList(Object object);
	
	/**
	 * 按月份查询某门店离职人数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getLiZhiList(Object object);
	
	/**
	 * 按月份查询本月计薪人数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoList(Object object);
	
	/**
	 * 按月份、员工类型查询在职人数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getEmpInfoListByEmpType(Object object);
	
	/**
	 * 异常明细--人员情况 按月份、门店查询当月派入、派出人数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDispatchEmpInfoList(Object object);
	
	/**
	 * 异常明细--人员情况 按月份、门店查询本月实发工资为0的人数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getActualSalaryZeroList(Object object);
	
	/**
	 * 跳转到乐天玛特异常明细--保险情况明细报表页面，Excel导出用
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAbnormalInsExcelList(Object object);
	
	/**
	 * 乐天玛特--外派人员薪资信息报表信息,Excel导出用
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDispatchEmpPaExcelList(Object object);
	
	/**
	 * 乐天玛特--外派人员保险信息报表信息,Excel导出用
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDispatchEmpInsExcelList(Object object);

}
