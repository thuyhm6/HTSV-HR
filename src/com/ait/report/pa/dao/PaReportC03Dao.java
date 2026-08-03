package com.ait.report.pa.dao;

import java.util.List;
import java.util.LinkedHashMap;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaReportC01Dao.java
 * @Description: interface Class PaReportC04Dao.java
 * @Create date: Sep 21, 2012 5:29:38 PM
 * @Create by: zhanghaiyuan (zhanghaiyuan@ait.net.cn)
 * @version 5.1
 */
public interface PaReportC03Dao {
	/**
	 * 导出给予现状汇总报表
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPacurrentRenditionCollectList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPacurrentRenditionCollectList2(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPacurrentRenditionCollectList3(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPacurrentRenditionCollectList4(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPacurrentRenditionCollectList5(Object object);
	
	List getPaCurrentRenditionList(Object object);

	
	/**
	 * 导出给予现状汇总报表,导出excel用
	 * @param object
	 * @return list
	 */
	
	public int getEmpCnt1(Object object) ;
	
	public int getEmpCnt2(Object object) ;
	
	public int getEmpCnt3(Object object) ;
	
	public int getEmpCnt4(Object object) ;

	List getPaCurrentRenditionSumAvg(Object object);

	/**
	 * 小时工工资发放表
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaHourWorkInfo(LinkedHashMap paramMap);

	@SuppressWarnings("unchecked")
	public List<?> getPaHourWorkInfoByDeptNo(LinkedHashMap paramMap);

	@SuppressWarnings("unchecked")
	public List<?> getSumPaHourWorkInfo(LinkedHashMap paramMap);


}
