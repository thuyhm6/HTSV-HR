package com.ait.report.pa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ait.report.pa.dao.PaReportC04Dao;
import com.ait.report.pa.dao.PaReportC11Dao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaReportC11DaoImpl  extends SqlMapClientSupport  implements PaReportC11Dao {	
	
	
	 
	
	/**
	 * 工资单，added on 2012-10-09
	 * @param object
	 * @return list
	 */
	public List retrievePaJasperReportPayrollC11Data(Object parameterObject) {

		List list = null;
		try {
			list = this.queryForList("report.pac11.retrievePaJasperReportPayrollC11Data",parameterObject);
			//list = commonSQLMapAdapter.executeQueryForMulti("report.pa.retrievePaJasperReportPayrollData", parameterObject);

		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			
		}
		return list;
	}

	/**
	 * 导出员工考勤月表
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getArMonthShiftList(Map paramMap) {

		List list = null;
		try {
			list = this.queryForList("report.pac11.getArMonthShiftList",paramMap);
		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
		}
		return list;
	}
}
