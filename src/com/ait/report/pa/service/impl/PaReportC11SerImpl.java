package com.ait.report.pa.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.report.pa.dao.PaReportC04Dao;
import com.ait.report.pa.dao.PaReportC11Dao;
import com.ait.report.pa.service.PaReportC04Ser;
import com.ait.report.pa.service.PaReportC11Ser;
import com.ait.sys.bean.AdminBean;
import com.ait.web.exception.GlRuntimeException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
@Service
public class PaReportC11SerImpl implements PaReportC11Ser {
	@Autowired
	private PaReportC11Dao paReportC11Dao;

 
	/**
	 * 工资单导出pdf
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object retrievePaJasperReportPayrollC11Data(HttpServletRequest request)
	{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String servletPath =request.getRealPath("/");
		// 报表参数
		Map parameters = new HashMap();
		// gain report file name
		String reportId = StringUtil.checkNull(request.getParameter("reportName"));
		String pamonth = StringUtil.checkNull(request.getParameter("pamonth"));
		String deptid = StringUtil.checkNull(request.getParameter("deptid"));
		String empid = StringUtil.checkNull(request.getParameter("empid"));
		String emp_type_code = StringUtil.checkNull(request.getParameter("emp_type_code"));
		//取得数据集合
		List sourceList = new ArrayList() ;
		
	//	System.out.println("reportId:"+reportId);
		if (!reportId.equals("")) {
			
		//	String reportFileName = servletPath +"WEB-INF"+File.separator+"view"+File.separator+ "report"+File.separator+"pac04" +File.separator+ reportId + ".jasper";
		//	log.debug("reportname : " + reportFileName);

			parameters.put("pamonth",pamonth);
			parameters.put("CPNY_ID", admin.getCpnyId());
			parameters.put("deptid",deptid);
			parameters.put("EMPID",empid);
			parameters.put("emp_type_code",emp_type_code);
			
			
		//	System.out.println("reportId:"+admin.getCpnyId());
			
			try {
				sourceList = paReportC11Dao.retrievePaJasperReportPayrollC11Data(parameters) ;
				//System.out.println("sourceList:"+sourceList.size());
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
	}
		return sourceList;
	}

	/**
	 * 导出员工考勤月表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getArMonthShiftList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"";
		String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"";
		paramMap.put("AR_MONTH", year+month);
		paramMap.put("DEPTID", request.getParameter("DEPTID"));
		paramMap.put("LANGUAGE", admin.getLanguage());
		
		returnList = paReportC11Dao.getArMonthShiftList(paramMap);

		return returnList;
	}


}
