package com.ait.report.pa.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface PaReportC11Ser {
 
	/**
	 * 工资单导出pdf
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object retrievePaJasperReportPayrollC11Data(HttpServletRequest request);

	/**
	 * 导出员工考勤月表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getArMonthShiftList(HttpServletRequest request);

}
