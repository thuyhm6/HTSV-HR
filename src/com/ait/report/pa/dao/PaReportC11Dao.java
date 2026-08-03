package com.ait.report.pa.dao;

import java.util.List;
import java.util.Map;


/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaReportC11Dao.java
 * @Description: interface Class PaReportC04Dao.java
 * @Create date: Sep 21, 2012 5:29:38 PM
 * @Create by: zhanghaiyuan (zhanghaiyuan@ait.net.cn)
 * @version 5.1
 */
public interface PaReportC11Dao {
	
	 
	/**
	 * 工资单，added on 2012-10-09
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List retrievePaJasperReportPayrollC11Data(Object object);

	/**
	 * 导出员工考勤月表
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getArMonthShiftList(Map paramMap);
}
