package com.ait.report.pa.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.CycleSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.report.pa.service.PaReportC04Ser;
import com.ait.sys.bean.AdminBean;
import com.ait.web.config.ConfigurationException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.ReportUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UserConfiguration;

@Controller
@RequestMapping(value = "/report/pac09")
public class PaReportC09Ctroller extends SqlMapClientSupport {
	@Autowired
	private PaReportC04Ser paReportC04Ser;
	@Autowired
	private CycleSer cycleSer;
	@Autowired
	InfoApplySer infoApplySer;
	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	/**
	 * 获得员工类型
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaJobOfferList")
	public ModelAndView getEmpType(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		List getEmpType = this.paReportC04Ser.getEmpType(request);
		modelMap.put("getEmpType", getEmpType);
		return new ModelAndView("/report/pac04/viewPaJobOfferList", modelMap);
	}

	/**
	 * 导出工报盘报表的数据
	 * 
	 * @param object
	 * @return list
	 */
	@RequestMapping(value = "/viewPaJobOfferrInfo")
	public ModelAndView getviewPaJobOfferrInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Calendar cal = Calendar.getInstance();
		request.setAttribute("YEAR", cal.get(Calendar.YEAR));
		request.setAttribute("MONTH", cal.get(Calendar.MONTH) + 1);

		return new ModelAndView("/report/pac04/viewPaJobOfferrInfo");
	}

	/**
	 * 导出工报盘报表的数据
	 * 
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaJobOfferExcel")
	public ModelAndView getPaJobOfferList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		@SuppressWarnings("unused")
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List getPaJobOfferList = this.paReportC04Ser.getPaJobOfferList(request);

		List listinfo = new ArrayList();
		for (int i = 0; i < getPaJobOfferList.size(); i++) {
			StringBuffer sb = new StringBuffer();
			Map sm = (HashMap) getPaJobOfferList.get(i);
			sb.append(sm.get("EMPID"));
			sb.append(sm.get("NAME"));
			sb.append(sm.get("CARD"));
			sb.append(sm.get("GONGZI"));
			sb.append(sm.get("DATES"));
			sb.append("\r\n");

			listinfo.add(sb);

		}
		modelMap.put("listinfo", listinfo);
		return new ModelAndView("/report/pa/viewPaJobOfferExcel", modelMap);
	}

	/**
	 * 跳转到查看正式工工资信息（按月份查询）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaOfficialPayOffInfo")
	public ModelAndView viewPaOfficialPayOffInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		// modelMap.put("paTranserList",this.paReportSer.getPaTranserList(request));
		// modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.paReportSer.getPaTranserListCnt(request));
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");
		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());
		return new ModelAndView("/report/pa/viewPaOfficialPayOffInfo", modelMap);
	}

	/**
	 * 正式工工资按工种信息（按月份查询），导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaOfficialPayOffTranserExcel")
	public ModelAndView viewPaTranserExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ddate = sdf.format(dt);
		modelMap.put("date", ddate);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));

		modelMap.put("viewPaOfficialPayOffList", this.paReportC04Ser
				.getPaOfficialPayOffList(request));// 获取员工工资信息
		modelMap.put("viewPaDeptEmpIdList", this.paReportC04Ser
				.getPaDeptEmpIdList(request));// 获取部门id
		modelMap.put("viewPaAllDeptNameList", this.paReportC04Ser
				.getPaAllDeptNameList(request));
		modelMap.put("viewPaOfficialPayOffSumList", this.paReportC04Ser
				.getPaOfficialPayOffSumList(request));// 合计
		modelMap.put("viewPaOfficialPayOffZongJiList", this.paReportC04Ser
				.getPaOfficialPayOffZongJiList(request));// 总计
		return new ModelAndView("/report/pa/viewPaOfficialPayOffTranserExcel",
				modelMap);
	}

	/**
	 * 工资对照（按月份查询），导出Excel用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPacontrastExcel")
	public ModelAndView viewPacontrastExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ddate = sdf.format(dt);
		modelMap.put("date", ddate);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		modelMap.put("viewPacontrastList", this.paReportC04Ser
				.getPacontrastList(request));// 获取员工工资信息
		modelMap.put("viewPacontrastListsum", this.paReportC04Ser
				.getPacontrastListsum(request));// 获取员工工资信息合计
		return new ModelAndView("/report/pa/viewPacontrastExcel", modelMap);
	}

	/**
	 * 个税（按月份查询），导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonaltaxExcel")
	public ModelAndView viewPersonaltaxExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ddate = sdf.format(dt);
		modelMap.put("date", ddate);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		modelMap.put("viewPersonaltaxList", this.paReportC04Ser
				.getPersonaltaxList(request));// 获取员工工资信息
		return new ModelAndView("/report/pa/viewPersonaltaxExcel", modelMap);
	}

	/**
	 * 公积金，导出Excel用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWageFundExcel")
	public ModelAndView viewWageFundExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ddate = sdf.format(dt);
		modelMap.put("date", ddate);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		modelMap.put("paWageFundList", this.paReportC04Ser
				.getpaWageFundList(request));// 获取员工工资信息
		return new ModelAndView("/report/pa/viewWageFundExcel", modelMap);
	}

	/**
	 * 正式工工资按工种信息（按月份查询），导出pdf用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaOfficialPayOffTranserPDF")
	public void viewPaTranserPDF(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String year = paramMap.get("YEAR") != null ? paramMap.get("YEAR")
				.toString() : "";
		String month = paramMap.get("MONTH") != null ? paramMap.get("MONTH")
				.toString() : "";
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH", year + month);
		paramMap.put("EMP_TYPE_CODE", "1369");// 1369 正式工

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@10.231.221.30:1521:SSHR", "ss_hr", "ss_hr");

		ServletContext servletContext = request.getSession()
				.getServletContext();
		String realPath = servletContext
				.getRealPath("/resources/reportFile/C04/GongZiFaFangBiao(ZhengShi)PDF.jasper");
		// 装载jasper文件application
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(realPath);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				paramMap, conn);
		if (null != jasperPrint) {
			FileBufferedOutputStream fbos = new FileBufferedOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, fbos);
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,
					jasperPrint);

			try {
				exporter.exportReport();
				if (fbos.size() > 0) {
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
									"attachment; filename=\tGongZiFaFang(ZhengShi).pdf");
					response.setContentLength(fbos.size());
					ServletOutputStream outStream = response.getOutputStream();
					try {
						fbos.writeData(outStream);
						fbos.dispose();
						outStream.flush();
					} finally {
						if (null != outStream) {
							outStream.close();
						}
					}
				}
			} catch (JRException e) {
				e.printStackTrace();
			} finally {
				if (null != fbos) {
					fbos.close();
					fbos.dispose();
				}
			}
		}
	}

	/**
	 * 跳转到查看劳务工工资信息（按月份和工作区域查询）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaLabourPayOffInfo")
	public ModelAndView viewPaLabourPayOffInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		// modelMap.put("paTranserList",this.paReportSer.getPaTranserList(request));
		// modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.paReportSer.getPaTranserListCnt(request));
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");
		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());

		return new ModelAndView("/report/pa/viewPaLabourPayOffInfo", modelMap);
	}

	/**
	 * 劳务工工资按工种信息（按工作区域及月份查询），导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaLabourPayOffTranserExcel")
	public ModelAndView getPaLabourPayOffTranserExcel(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ddate = sdf.format(dt);
		modelMap.put("date", ddate);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		modelMap.put("viewPaLabourPayOffList", this.paReportC04Ser
				.getPaLabourPayOffList(request));// 获取员工工资信息
		modelMap.put("viewPaDeptEmpIdList", this.paReportC04Ser
				.getPaDeptEmpIdList(request));// 获取部门id
		modelMap.put("viewPaAllDeptNameList", this.paReportC04Ser
				.getPaAllDeptNameList(request));
		modelMap.put("viewPaLabourPayOffSumList", this.paReportC04Ser
				.getPaLabourPayOffSumList(request));// 小计
		modelMap.put("viewPaLabourPayOffZongJiList", this.paReportC04Ser
				.getPaLabourPayOffZongJiList(request));// 总计
		return new ModelAndView("/report/pa/viewPaLabourPayOffTranserExcel",
				modelMap);
	}

	/**
	 * 劳务工工资按工种信息（按工作区域及月份查询），导出PDF用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaLabourPayOffTranserPDF")
	public void getPaLabourPayOffTranserPDF(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String year = paramMap.get("YEAR") != null ? paramMap.get("YEAR")
				.toString() : "";
		String month = paramMap.get("MONTH") != null ? paramMap.get("MONTH")
				.toString() : "";
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH", year + month);
		paramMap.put("SERVICES_BELONG", paramMap.get("service_belong"));
		paramMap.put("EMP_TYPE_CODE", "14890");// 14890劳务工

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@10.231.221.30:1521:SSHR", "ss_hr", "ss_hr");

		ServletContext servletContext = request.getSession()
				.getServletContext();
		String realPath = servletContext
				.getRealPath("/resources/reportFile/C04/GongZiFaFangBiao(LaoWu)PDF.jasper");
		// 装载jasper文件application
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(realPath);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				paramMap, conn);
		if (null != jasperPrint) {
			FileBufferedOutputStream fbos = new FileBufferedOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, fbos);
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,
					jasperPrint);

			try {
				exporter.exportReport();
				if (fbos.size() > 0) {
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
							"attachment; filename=\tGongZiFaFang(LaoWu).pdf");
					response.setContentLength(fbos.size());
					ServletOutputStream outStream = response.getOutputStream();
					try {
						fbos.writeData(outStream);
						fbos.dispose();
						outStream.flush();
					} finally {
						if (null != outStream) {
							outStream.close();
						}
					}
				}
			} catch (JRException e) {
				e.printStackTrace();
			} finally {
				if (null != fbos) {
					fbos.close();
					fbos.dispose();
				}
			}
		}

	}

	/**
	 * 跳转到工资单页面（按月份查询）(query the pa list info of employee by monthed)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaListInfo")
	public ModelAndView viewPaTranserInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		// modelMap.put("paTranserList",this.paReportSer.getPaTranserList(request));
		// modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.paReportSer.getPaTranserListCnt(request));
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");

		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());
		return new ModelAndView("/report/pac04/viewPaListInfo", modelMap);
	}

	/**
	 * 工资单页面导出pdf（按月份查询）(query the pa list info of employee by monthed)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings( { "deprecation", "unchecked" })
	@RequestMapping(value = "/jasperServlet")
	public void retrievePaJasperReportPayrollData(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Logger.getLogger("C04工资单------------开始生成--------111111");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String CpnyId = admin.getCpnyId();
		String servletPath = request.getRealPath("");
		// 报表参数
		Map parameters = new HashMap();
		String reportId = StringUtil.checkNull(request
				.getParameter("reportName"));
		String pamonth = StringUtil.checkNull(request.getParameter("pamonth"));
		String deptid = StringUtil.checkNull(request.getParameter("deptid"));
		String empid = StringUtil.checkNull(request.getParameter("empid"));
		// 取得数据集合
		List sourceList = new ArrayList();
		if (!reportId.equals("")) {
			int init = servletPath.indexOf("\\");
			String server = "win";
			if (init < 0) { // WebServer的os为unix/linux
				server = "u/l";
			}
			if ("win".equals(server)) {
			} else {
			}
			parameters.put("pamonth", pamonth);
			parameters.put("CPNY_ID", admin.getCpnyId());
			parameters.put("EMPID", empid);
			parameters.put("deptid", deptid);
			parameters.put("EMP_TYPE_CODE", "14890");

			String fileName = createPDFByXmlName(sourceList, parameters, admin,
					request, reportId, request.getSession(), response);
			request.getSession().setAttribute("PDFNAME", fileName);

			File file = new File(servletPath + "/resources/reportFile/"
					+ CpnyId + "/pdf/painfo" + CpnyId + ".pdf");
			response.setContentType("application/pdf");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment;filename="
					+ "painfo" + CpnyId + ".pdf");
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);

			byte[] b = new byte[2048];
			long k = 0;
			OutputStream myout = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 2048);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
		}
	}

	/**
	 * 报表导出通用方法
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "static-access"})
	@RequestMapping(value = "/exportDatilyReport")
	public void exportDatilyReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String CpnyId = admin.getCpnyId();
		String servletPath = request.getRealPath("");
		String reportName = StringUtil.checkNull(request.getParameter("reportName"));
		String suffix = StringUtil.checkNull(request.getParameter("suffix").toLowerCase());
        String pay_area_cd = request.getParameter("seach_PAY_AREA_CD");
		if (!reportName.equals("") && !suffix.equals("")) {
			String reportId = reportName;
			String fileName = createPDFByXmlName1(admin, request, reportId,
					suffix, request.getSession(), response);
			request.getSession().setAttribute("PDFNAME", fileName);
			ReportUtil rUtil = new ReportUtil();
			String filePath = servletPath + "/resources/reportFile/"
			+ CpnyId + "/painfo" + CpnyId + "." + suffix;
			if("xls".equals(suffix)){//导出excel时才加密
				String passWord = request.getParameter("password").toString();
				if(StringUtils.isNotBlank(passWord)){//设置密码
					rUtil.excelEncrypt(filePath, passWord);
				}
			}
			
			File file = new File(filePath);
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment;filename=" + reportName + "." + suffix);
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);
			byte[] b = new byte[2048];
			long k = 0;
			OutputStream myout = response.getOutputStream();
			
			while (k < file.length()) {
				int j = buff.read(b, 0, 2048);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
			myout.close();
			buff.close();
			fis.close();
		}
	}

	@SuppressWarnings( { "unchecked", "deprecation", "rawtypes" })
	public String createPDFByXmlName1(AdminBean admin,
			HttpServletRequest request, String reportId, String suffix,
			HttpSession session, HttpServletResponse response)
			throws UnsupportedEncodingException, JRException, ServletException,
			ConfigurationException, SQLException {
		JasperReport jasperReport;
		JasperPrint jasperPrint;
		String reportFileName = "";
		String servletPath = request.getRealPath("");
		try {
			String CpnyId = admin.getCpnyId();
			int init = servletPath.indexOf("\\");
			String server = "win";
			if (init < 0) { // WebServer的os为unix/linux
				server = "u/l";
			}
			if ("win".equals(server)) {
				reportFileName = servletPath + "\\resources\\reportFile\\"
						+ reportId;// + ".jasper";
			} else {
				reportFileName = servletPath + "/resources/reportFile/"
						+ reportId;// + ".jasper";
			}
			File file = new File(reportFileName + ".jasper");
			// 如果jasper文件不存在，就调用jrxml文件编译生成
			if (!file.exists()) {
				JasperCompileManager.compileReportToFile(reportFileName
						+ ".jrxml", reportFileName + ".jasper");
			}
			 LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		        String pay_area_cds = request.getParameter("seach_PAY_AREA_CD");
		        String PAY_AREA_CD = "";
		        int num =0;
		        if(pay_area_cds!=null && !"".equals(pay_area_cds)){
		           String[] pay_area_cdss = pay_area_cds.split(",");
		           if(pay_area_cdss.length>1){
		        	   for(int i=1;i<=pay_area_cdss.length;i++){
		        		   if(num==0){
		        			   PAY_AREA_CD = "'"+pay_area_cdss[0]+"'";
							}else{
								PAY_AREA_CD+= ","+"'"+pay_area_cdss[i-1]+"'";
							}
		        		   num++;
		        	   }
		           }else if(pay_area_cdss.length==1){
		        	   PAY_AREA_CD = "'"+pay_area_cdss[0]+"'";
		           }
		           paramMap.put("PAY_AREA_CD", PAY_AREA_CD);
		        }
		    if(request.getParameter("id")=="11" || "11".equals(request.getParameter("id"))){
		    	paramMap.put("PERSON_ID",request.getParameter("dwz.person.personId"));
		    	paramMap.put("EMPNO", request.getParameter("dwz.person.empId"));
		    	paramMap.put("EN_NM",request.getParameter("dwz.person.cnName"));
		    	paramMap.put("CN_NM", request.getParameter("dwz.person.empName"));
		    	paramMap.put("GNDR", request.getParameter("dwz.person.sex"));
		    	paramMap.put("SUBSD_HIRE_DATE", request.getParameter("dwz.person.jion"));
		    	paramMap.put("PROB_END_DATE",request.getParameter("dwz.person.end"));
		    	paramMap.put("JOB_POSI_CD",request.getParameter("dwz.person.posision"));
		    	paramMap.put("JOB_GRADE", request.getParameter("dwz.person.emppostGradeName"));
		    	paramMap.put("JOB_DUTY_CD",request.getParameter("dwz.person.duty"));
		    	paramMap.put("CN_ORG_NM",request.getParameter("dwz.person.empDept"));
		    } 
		    if("".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == null){
		    	paramMap.put("JOB_TP","211807");
		    }
		    paramMap.put("PERSON_ID_JOB",admin.getPersonId());
		    if("211807".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == "211807"){
		    	paramMap.put("JOB_NAME","全部人员类型");
		    }
		    if("".equals(paramMap.get("PROD_TP")) || paramMap.get("PROD_TP") == null){
		    	paramMap.put("PROD_TP","211424");
		    }
		    if(paramMap.get("PROD_TP").equals("211424")){
				String PROD_NM = "全部产品";
		    	paramMap.put("PROD_NM",PROD_NM);
		    }
			// List mapList = (List)this.paReportC04Ser.retrievePaJasperReportPayrollData(paramMap);
			jasperReport = (JasperReport) JRLoader.loadObject(file);
			// JRDataSource dataSource = new JRBeanCollectionDataSource(mapList);
			// 此处为关键，将对象列表设为数据源
			// jasperPrint = JasperFillManager.fillReport(jasperReport,paramMap, dataSource);

			jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap,super.getConnection());
			// 因模版上没有传入参数，所以此处第二个参数为空

			String fileName = "/painfo" + CpnyId + "." + suffix;
			File fi = new File(servletPath + "/resources/reportFile/" + CpnyId);
			if (!fi.exists()) {
				fi.mkdir();
			}
			if ("xls".equals(suffix)) {
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, "UTF-8");
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME,fi + fileName);
				exporter.exportReport();
			} else if ("pdf".equals(suffix)) {
				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME,fi + fileName);
				// 注意此处用的不是JRPdfExporterParameter.OUTPUT_FILE，要用这个，还需新建File
				exporter.exportReport();
			}
			session.getServletContext().setAttribute("FilePath",servletPath + "/" + suffix);
			return fileName;
		} catch (JRException e) {
			e.printStackTrace();
			throw new JRException(e);
		}
	}
	
	/**
	 * 报表导出通用方法2
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "static-access"})
	@RequestMapping(value = "/exportDatilyReport2")
	public void exportDatilyReport2(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String CpnyId = admin.getCpnyId();
		String servletPath = request.getRealPath("");
		String title = request.getParameter("TITLE");
		String reportName = StringUtil.checkNull(request.getParameter("reportName"));
		String suffix = StringUtil.checkNull(request.getParameter("suffix").toLowerCase());
		if("101010".equals(title)){
	          reportName="welAmountYearbyCompanyEmp";
		}else {                
			  reportName="welAmountYearbyPersonEmp";
		}
		if (!reportName.equals("") && !suffix.equals("")) {
  			String reportId = reportName;
  			String fileName = createPDFByXmlName2(admin, request, reportId,suffix, request.getSession(), response);
  			request.getSession().setAttribute("PDFNAME", fileName);
  			
  			ReportUtil rUtil = new ReportUtil();
  			String filePath = servletPath + "/resources/reportFile/" + CpnyId + "/" + reportName + "." + suffix;
  			if("xls".equals(suffix)){//导出excel时才加密
  				String passWord = request.getParameter("password").toString();
  				if(StringUtils.isNotBlank(passWord)){//设置密码
  					rUtil.excelEncrypt(filePath, passWord);
  				}
  			}
  			
  			File file = new File(filePath);
  			response.setContentLength((int) file.length());
  			response.setHeader("Content-Disposition", "attachment;filename=" + reportName + "." + suffix);
  			FileInputStream fis = new FileInputStream(file);
  			BufferedInputStream buff = new BufferedInputStream(fis);
  			byte[] b = new byte[2048];
  			long k = 0;
  			OutputStream myout = response.getOutputStream();
  			
  			while (k < file.length()) {
  				int j = buff.read(b, 0, 2048);
  				k += j;
  				myout.write(b, 0, j);
  			}
  			myout.flush();
  			myout.close();
  			buff.close();
  			fis.close();
  		}
	}
	
	@SuppressWarnings( { "unchecked", "deprecation" })
	public String createPDFByXmlName2(AdminBean admin,
			HttpServletRequest request, String reportId, String suffix,
			HttpSession session, HttpServletResponse response)
			throws UnsupportedEncodingException, JRException, ServletException,
			ConfigurationException, SQLException {
		JasperReport jasperReport;
		JasperPrint jasperPrint;
		String reportFileName = "";
		String servletPath = request.getRealPath("");
		try {
			String CpnyId = admin.getCpnyId();
			int init = servletPath.indexOf("\\");
			String server = "win";
			if (init < 0) { // WebServer的os为unix/linux
				server = "u/l";
			}
			if ("win".equals(server)) {
				reportFileName = servletPath + "\\resources\\reportFile\\" + reportId;// + ".jasper";
			} else {
				reportFileName = servletPath + "/resources/reportFile/" + reportId;// + ".jasper";
			}
			File file = new File(reportFileName + ".jasper");
			// 如果jasper文件不存在，就调用jrxml文件编译生成
			if (!file.exists()) {
				JasperCompileManager.compileReportToFile(reportFileName + ".jrxml", reportFileName + ".jasper");
			}
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			
			String deptNo = paramMap.get("DEPTNO") == null ? "" : paramMap.get("DEPTNO").toString();
			String cnpyId = paramMap.get("CPNY_ID") == null ? "" : paramMap.get("CPNY_ID").toString();
			String year = paramMap.get("YEAR") == null ? "2014" : paramMap.get("YEAR").toString();
			String month = paramMap.get("MONTH") == null ? "" : paramMap.get("MONTH").toString();
			//获取部门对应的大区Code 
			String deptName = cycleSer.getDeptNameByDeptNo(request, deptNo, cnpyId);
			paramMap.put("PA_MONTH", year + month);
			paramMap.put("DEPT_NAME_BY_NO", deptName);
		    
		    if("".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == null){
		    	paramMap.put("JOB_TP","211807");
		    }
		    paramMap.put("PERSON_ID_JOB",admin.getPersonId());
		    if("211807".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == "211807"){
		    	paramMap.put("JOB_NAME","全部人员类型");
		    }
		    if("".equals(paramMap.get("PROD_TP")) || paramMap.get("PROD_TP") == null){
		    	paramMap.put("PROD_TP","211424");
		    }
		    if(paramMap.get("PROD_TP").equals("211424")){
				String PROD_NM = "全部产品";
		    	paramMap.put("PROD_NM",PROD_NM);
		    }
			// List mapList = (List)this.paReportC04Ser.retrievePaJasperReportPayrollData(paramMap);
			jasperReport = (JasperReport) JRLoader.loadObject(file);
			//JRDataSource dataSource = new JRBeanCollectionDataSource(mapList);
			//此处为关键，将对象列表设为数据源
			//jasperPrint = JasperFillManager.fillReport(jasperReport,paramMap, dataSource);
			jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap,super.getConnection());
			//因模版上没有传入参数，所以此处第二个参数为空
			String fileName = "/" + reportId + "." + suffix;
			File fi = new File(servletPath + "/resources/reportFile/" + CpnyId);
			if (!fi.exists()) {
				fi.mkdir();
			}
			if ("xls".equals(suffix)) {
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, "UTF-8");
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME,fi + fileName);
				exporter.exportReport();
			} else if ("pdf".equals(suffix)) {
				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME,fi + fileName);
				// 注意此处用的不是JRPdfExporterParameter.OUTPUT_FILE，要用这个，还需新建File
				exporter.exportReport();
			}
			session.getServletContext().setAttribute("FilePath",servletPath + "/" + suffix);
			return fileName;
		} catch (JRException e) {
			e.printStackTrace();
			throw new JRException(e);
		}
	}
	
	/**
	 * 报表页面显示通用方法
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked"})
	@RequestMapping(value = "/exportDatilyHtmlReport2")
	public void exportDatilyHtmlReport2(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String servletPath = request.getRealPath("");
		String reportName = StringUtil.checkNull(request.getParameter("reportName"));
		
		String suffix = StringUtil.checkNull(request.getParameter("suffix").toLowerCase());
		String JOB_TP = request.getParameter("JOB_TP");
		String title = request.getParameter("TITLE");
		String job_name = request.getParameter("JOB_NAME");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		@SuppressWarnings("unused")
		String CpnyId = admin.getCpnyId();
		if("101010".equals(title)){
	          reportName="welAmountYearbyCompanyEmp";
		}else {                
			  reportName="welAmountYearbyPersonEmp";
		}
		if (!reportName.equals("") && !suffix.equals("")) {
			if(suffix.equals("html")){
				String filePath = servletPath + "/resources/reportFile/"+ reportName;
				LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
				String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"2014";
				String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"09";
			    paramMap.put("PA_MONTH", year+month);
			    if(paramMap.get("DEPTNO")==null && "".equals(paramMap.get("DEPTNO"))){
				       paramMap.put("DEPTNO",admin.getDeptNo());
				}
			    paramMap.put("SUBSD_CD", paramMap.get("CPNY_ID")!=null?paramMap.get("CPNY_ID").toString():admin.getCpnyId());
			    if("".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == null){
			    	paramMap.put("JOB_TP","211807");
			    }else{
			    	paramMap.put("JOB_NAME", job_name);
			    }
			    paramMap.put("PERSON_ID_JOB",admin.getPersonId());
			    if("211807".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == "211807"){
			    	paramMap.put("JOB_NAME","全部人员类型");
			    }
			    if("".equals(paramMap.get("PROD_TP")) || paramMap.get("PROD_TP") == null){
			    	paramMap.put("PROD_TP","211424");
			    }
			    if(paramMap.get("PROD_TP").equals("211424")){
					String PROD_NM = "全部产品";
			    	paramMap.put("PROD_NM",PROD_NM);
			    }
			    
				File reportFile = new File(filePath+".jasper");
				if (!reportFile.exists()) {
					JasperCompileManager.compileReportToFile(filePath+ ".jrxml", filePath + ".jasper");
				}
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap,super.getConnection());
				PrintWriter out = response.getWriter();
				
				JRHtmlExporter exporter = new JRHtmlExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
				exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);
				exporter.exportReport();
				out.flush();
				out.close();
			}else if(suffix.equals("pdf")){
				String filePath = servletPath + "/resources/reportFile/"+ reportName;
				LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
				String year = paramMap.get("YEAR")!=null?paramMap.get("YEAR").toString():"2014";
				String month = paramMap.get("MONTH")!=null?paramMap.get("MONTH").toString():"07";
			    paramMap.put("PA_MONTH", year+month);
			    if("".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == null){
			    	paramMap.put("JOB_TP","211807");
			    }else{
			    	paramMap.put("JOB_NAME", job_name);
			    }
			    paramMap.put("PERSON_ID_JOB",admin.getPersonId());
			    if("211807".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == "211807"){
			    	paramMap.put("JOB_NAME","全部人员类型");
			    }
			    if("".equals(paramMap.get("PROD_TP")) || paramMap.get("PROD_TP") == null){
			    	paramMap.put("PROD_TP","211424");
			    }
			    if(paramMap.get("PROD_TP").equals("211424")){
					String PROD_NM = "全部产品";
			    	paramMap.put("PROD_NM",PROD_NM);
			    }
				File reportFile = new File(filePath+".jasper");
				if (!reportFile.exists()) {
					JasperCompileManager.compileReportToFile(filePath+ ".jrxml", filePath + ".jasper");
				}
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap,super.getConnection());
				ServletOutputStream out = response.getOutputStream();
				
				JRPdfExporter pdfExporter = new JRPdfExporter();
				pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				pdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                pdfExporter.exportReport();
				out.flush();
				out.close();

			}
		}
	}
	
	/**
	 * 报表页面显示通用方法
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked"})
	@RequestMapping(value = "/exportDatilyHtmlReport")
	public void exportDatilyHtmlReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String servletPath = request.getRealPath("");
		String reportName = StringUtil.checkNull(request.getParameter("reportName"));
		String suffix = StringUtil.checkNull(request.getParameter("suffix").toLowerCase());
		String job_name = request.getParameter("JOB_NAME");
		if (!reportName.equals("") && !suffix.equals("")) {
			if(suffix.equals("html")){
			    String filePath = servletPath + "/resources/reportFile/"+ reportName;
			    LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
				
				paramMap = this.fillParamsInMapForReportPop(request, paramMap);
				
			    if("".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == null){
			    	paramMap.put("JOB_TP","211807");
			    }else{
			    	paramMap.put("JOB_NAME",job_name);
			    }
			    paramMap.put("PERSON_ID_JOB",admin.getPersonId());
			    if("211807".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == "211807"){
			    	paramMap.put("JOB_NAME","全部人员类型");
			    }
			    if("".equals(paramMap.get("PROD_TP")) || paramMap.get("PROD_TP") == null){
			    	paramMap.put("PROD_TP","211424");
			    }
			    if(paramMap.get("PROD_TP").equals("211424")){
					String PROD_NM = "全部产品";
			    	paramMap.put("PROD_NM",PROD_NM);
			    }
			    File reportFile = new File(filePath+".jasper");
			    if (!reportFile.exists()) {
				    JasperCompileManager.compileReportToFile(filePath+ ".jrxml", filePath + ".jasper");
			    }
			    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
			    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap,super.getConnection());
			    PrintWriter out = response.getWriter();
			    
			    JRHtmlExporter exporter = new JRHtmlExporter();
			    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
			    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);
			    exporter.exportReport();
			    out.flush();
			    out.close();
			}else if(suffix.equals("pdf")){
				String filePath = servletPath + "/resources/reportFile/"+ reportName;
				LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
				
				paramMap = this.fillParamsInMapForReportPop(request, paramMap);
				
				if("".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == null){
			    	paramMap.put("JOB_TP","211807");
			    }else{
			    	paramMap.put("JOB_NAME",job_name);
			    }
				if("".equals(paramMap.get("PROD_TP")) || paramMap.get("PROD_TP") == null){
			    	paramMap.put("PROD_TP","211424");
			    }
				if(paramMap.get("PROD_TP").equals("211424")){
					String PROD_NM = "全部产品";
			    	paramMap.put("PROD_NM",PROD_NM);
			    }
				paramMap.put("PERSON_ID_JOB",admin.getPersonId());
				if("211807".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == "211807"){
			    	paramMap.put("JOB_NAME","全部人员类型");
			    }
				File reportFile = new File(filePath+".jasper");
				if (!reportFile.exists()) {
					JasperCompileManager.compileReportToFile(filePath+ ".jrxml", filePath + ".jasper");
				}
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap,super.getConnection());
				ServletOutputStream out = response.getOutputStream();
				
				JRPdfExporter pdfExporter = new JRPdfExporter();
				pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				pdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                pdfExporter.exportReport();
				out.flush();
				out.close();
			}
		}
	}
	
	private LinkedHashMap fillParamsInMapForReportPop(HttpServletRequest request,LinkedHashMap paramMap){
		String paramStr = StringUtil.checkNull(request.getParameter("params")) ;
		
		String[] params = paramStr.split("@") ;
		
		String[] tempStr = null ;
		
		if(params.length > 0){
			for (int i = 0; i < params.length; i++) {
				tempStr = params[i].split("=");
				if(tempStr.length == 2 )
					paramMap.put(tempStr[0], tempStr[1]);
			}
		}
		return paramMap ;
	}
	
	/**
	 * 报表页面显示通用方法
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked"})
	@RequestMapping(value = "/exportDatilyHtmlReport3")
	public void exportDatilyHtmlReport3(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String servletPath = request.getRealPath("");
		String reportName = StringUtil.checkNull(request.getParameter("reportName"));
		String suffix = StringUtil.checkNull(request.getParameter("suffix").toLowerCase());
		if (!reportName.equals("") && !suffix.equals("")) {
			if(suffix.equals("html")){
			    String filePath = servletPath + "/resources/reportFile/"+ reportName;
			    LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			    if("".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == null){
			    	paramMap.put("JOB_TP","211807");
			    }else{
			    	paramMap.put("JOB_NAME",request.getParameter("seach_JOB_NAME"));
			    }
			    paramMap.put("PERSON_ID_JOB",admin.getPersonId());
			    if("211807".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == "211807"){
			    	paramMap.put("JOB_NAME","全部人员类型");
			    }
			    paramMap.put("PA_MONTH", paramMap.get("YEAR").toString() + paramMap.get("MONTH").toString());
			    File reportFile = new File(filePath+".jasper");
			    if (!reportFile.exists()) {
				    JasperCompileManager.compileReportToFile(filePath+ ".jrxml", filePath + ".jasper");
			    }
			    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
			    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap,super.getConnection());
			    PrintWriter out = response.getWriter();
			
			    JRHtmlExporter exporter = new JRHtmlExporter();
			    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
			    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);
			    exporter.exportReport();
			    out.flush();
			    out.close();
			}else if(suffix.equals("pdf")){
				String filePath = servletPath + "/resources/reportFile/"+ reportName;
				LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
				if("".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == null){
			    	paramMap.put("JOB_TP","211807");
			    }else{
			    	paramMap.put("JOB_NAME",request.getParameter("seach_JOB_NAME"));
			    }
				paramMap.put("PERSON_ID_JOB",admin.getPersonId());
				if("211807".equals(paramMap.get("JOB_TP")) || paramMap.get("JOB_TP") == "211807"){
			    	paramMap.put("JOB_NAME","全部人员类型");
			    }
				paramMap.put("PA_MONTH", paramMap.get("YEAR").toString() + paramMap.get("MONTH").toString());
				File reportFile = new File(filePath+".jasper");
				if (!reportFile.exists()) {
					JasperCompileManager.compileReportToFile(filePath+ ".jrxml", filePath + ".jasper");
				}
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap,super.getConnection());
				ServletOutputStream out = response.getOutputStream();
				
				JRPdfExporter pdfExporter = new JRPdfExporter();
				pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				pdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                pdfExporter.exportReport();
				out.flush();
				out.close();

			}
		}
		//return new ModelAndView("/report/pac04/exportDatilyHtmlReport", modelMap);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public static String createPDFByXmlName(List mapList, Map parameters,
			AdminBean admin, HttpServletRequest request, String reportId,
			HttpSession session, HttpServletResponse response)
			throws UnsupportedEncodingException, JRException, ServletException,
			ConfigurationException {

		Logger.getLogger("C04工资单----pdf---------开始生成--------");
		System.out.println("C04工资单----pdf---------开始生成--------");
		JasperReport jasperReport;
		JasperPrint jasperPrint;
		String reportFileName = "";
		String servletPath = request.getRealPath("");
		try {
			String CpnyId = admin.getCpnyId();

			int init = servletPath.indexOf("\\");
			String server = "win";
			if (init < 0) { // WebServer的os为unix/linux
				server = "u/l";
			}
			if ("win".equals(server)) {
				reportFileName = servletPath + "\\resources\\reportFile\\"
						+ CpnyId + "\\" + reportId;// + ".jasper";
				// path="WebRoot\\resources\\reportFile\\" + CpnyId+ "\\";
			} else {
				reportFileName = servletPath + "/resources/reportFile/"
						+ CpnyId + "/" + reportId;// + ".jasper";
				// path="WebRoot/resources/reportFile/" + CpnyId+ "/";
			}

			Logger.getLogger("------------生成jasper---------");
			System.out.println("-生成jasper------");
			File file = new File(reportFileName + ".jasper");
			// 如果jasper文件不存在，就调用jrxml文件编译生成
			// JasperCompileManager.compileReportToFile(String sourceFileName,
			// String destFileName)
			if (!file.exists()) {
				JasperCompileManager.compileReportToFile(reportFileName
						+ ".jrxml", reportFileName + ".jasper");
			}
			Logger.getLogger("------------完成生成jasper---------");
			System.out.println("--完成生成-----");
			Logger.getLogger("------------ftp上传 jasper---------");
			System.out.println("--ftp上传 jasper----");
			jasperReport = (JasperReport) JRLoader.loadObject(file);
			Logger.getLogger("------------读取 jasper---------");
			System.out.println("--读取 jasper-------");
			JRDataSource dataSource = new JRBeanCollectionDataSource(mapList);
			// 此处为关键，将对象列表设为数据源
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					parameters, dataSource);
			// 因模版上没有传入参数，所以此处第二个参数为空

			// String fileName = "pdf/"+reportName+session.getId()+".pdf";
			String fileName = "pdf/painfo" + CpnyId + ".pdf";
			File fi = new File(servletPath + "/resources/reportFile/" + CpnyId
					+ "/pdf");
			if (!fi.exists()) {
				fi.mkdir();
			}
			// 生成方法1
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,jasperPrint);
			exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING,"UTF-8");
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME,
					servletPath + "/resources/reportFile/" + CpnyId + "/"+ fileName);
			// 注意此处用的不是JRPdfExporterParameter.OUTPUT_FILE，要用这个，还需新建File
			exporter.exportReport();
			session.getServletContext().setAttribute("FilePath",servletPath + "/pdf");
			return fileName;
		} catch (JRException e) {
			e.printStackTrace();
			throw new JRException(e);
		}
	}

	/**
	 * 跳转到导出工资支付现状报表页面（按月份查询）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaPayOffStatusByMonth")
	public ModelAndView viewPaPayOffStatusByMonth(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");

		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());
		return new ModelAndView("/report/pa/viewPaPayOffStatusByMonth",
				modelMap);
	}

	/**
	 * 基本工资比较，查看页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPacontrast")
	public ModelAndView viewPacontrast(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");

		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());

		return new ModelAndView("/report/pac04/viewPacontrast", modelMap);
	}

	/**
	 * 个税，查看页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonaltax")
	public ModelAndView viewPersonaltax(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");

		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());
		return new ModelAndView("/report/pac04/viewPersonaltax", modelMap);
	}

	/**
	 * 基金，查看页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWageFund")
	public ModelAndView viewWageFund(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");
		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());
		return new ModelAndView("/report/pac04/viewWageFund", modelMap);
	}

	/**
	 * 工资支付现状（按月份查询），导出Excel用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaPayOffStatusByMonthTranserExcel")
	public ModelAndView viewPaPayOffStatusByMonthTranserExcel(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ddate = sdf.format(dt);
		modelMap.put("date", ddate);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		// 正式员工
		List normalEmpSalaryList = this.paReportC04Ser
				.getNormalEmpSalaryList(request);
		modelMap.put("normalEmpSalaryList", normalEmpSalaryList);
		// 劳务派遣工
		List paiQianEmpSalaryList = this.paReportC04Ser
				.getPaiQianEmpSalaryList(request);
		modelMap.put("paiQianEmpSalaryList", paiQianEmpSalaryList);

		// 查询驻在员工资
		LinkedHashMap zhuZaiYuan = (LinkedHashMap) this.paReportC04Ser
				.getPaSalaryZhuZaiYuanMap(request);
		modelMap.put("zhuZaiYuanSalaryMap", zhuZaiYuan);

		// 查询中方总计
		LinkedHashMap totalChina = (LinkedHashMap) this.paReportC04Ser
				.getPaSalaryTotalChinaMap(request);
		modelMap.put("totalChina", totalChina);

		// 查询公司总计
		LinkedHashMap totalCompany = (LinkedHashMap) this.paReportC04Ser
				.getPaSalaryTotalCompanyMap(request);
		modelMap.put("totalCompany", totalCompany);

		// 查询销售金额
		LinkedHashMap salaryAmountMap = (LinkedHashMap) this.paReportC04Ser
				.getPaSalaryAmountMap(request);
		modelMap.put("salaryAmountMap", salaryAmountMap);

		// 查询生产金额
		LinkedHashMap productionAmountMap = (LinkedHashMap) this.paReportC04Ser
				.getPaProductionAmountMap(request);
		modelMap.put("productionAmountMap", productionAmountMap);

		return new ModelAndView(
				"/report/pa/viewPaPayOffStatusByMonthTranserExcel", modelMap);
	}

	/**
	 * 跳转到导出工资支付现状报表页面（累计）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaPayOffStatusAddup")
	public ModelAndView viewPaPayOffStatusAddup(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");

		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());

		return new ModelAndView("/report/pa/viewPaPayOffStatusAddup", modelMap);
	}

	/**
	 * 工资支付现状（累计），导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaPayOffStatusAddupTranserExcel")
	public ModelAndView viewPaPayOffStatusAddupTranserExcel(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ddate = sdf.format(dt);
		modelMap.put("date", ddate);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		// 正式员工
		List normalEmpSalaryList = this.paReportC04Ser
				.getNormalEmpSalarySumList(request);
		modelMap.put("normalEmpSalaryList", normalEmpSalaryList);
		// 劳务派遣工
		List paiQianEmpSalaryList = this.paReportC04Ser
				.getPaiQianEmpSalarySumList(request);
		modelMap.put("paiQianEmpSalaryList", paiQianEmpSalaryList);

		// 查询驻在员工资
		LinkedHashMap zhuZaiYuan = (LinkedHashMap) this.paReportC04Ser
				.getPaSalaryZhuZaiYuanSumMap(request);
		modelMap.put("zhuZaiYuanSalaryMap", zhuZaiYuan);

		// 查询中方总计
		LinkedHashMap totalChina = (LinkedHashMap) this.paReportC04Ser
				.getPaSalaryTotalChinaSumMap(request);
		modelMap.put("totalChina", totalChina);

		// 查询公司总计
		LinkedHashMap totalCompany = (LinkedHashMap) this.paReportC04Ser
				.getPaSalaryTotalCompanySumMap(request);
		modelMap.put("totalCompany", totalCompany);

		// 查询销售金额
		LinkedHashMap salaryAmountMap = (LinkedHashMap) this.paReportC04Ser
				.getPaSalaryAmountSumMap(request);
		modelMap.put("salaryAmountMap", salaryAmountMap);

		// 查询生产金额
		LinkedHashMap productionAmountMap = (LinkedHashMap) this.paReportC04Ser
				.getPaProductionAmountSumMap(request);
		modelMap.put("productionAmountMap", productionAmountMap);

		return new ModelAndView(
				"/report/pa/viewPaPayOffStatusAddupTranserExcel", modelMap);
	}

	/**
	 * 跳转到导出人件费表页面（按月份查询）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaLivewareInfo")
	public ModelAndView viewPaLiveware(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");
		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());
		return new ModelAndView("/report/pa/viewPaLivewareInfo", modelMap);
	}

	/**
	 * 正式工 人件费表 （按月份查询），导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaLivewareTranserExcel")
	public ModelAndView viewPaLivewareTranserExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ddate = sdf.format(dt);
		modelMap.put("date", ddate);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));

		modelMap.put("viewPaOfficialPayOffList", this.paReportC04Ser
				.getPaOfficialPayOffList(request));// 获取员工工资信息
		modelMap.put("viewPaDeptEmpIdList", this.paReportC04Ser
				.getPaDeptEmpIdList(request));// 获取部门id
		modelMap.put("viewPaAllDeptNameList", this.paReportC04Ser
				.getPaAllDeptNameList(request));
		modelMap.put("viewPaOfficialPayOffSumList", this.paReportC04Ser
				.getPaOfficialPayOffSumList(request));// 合计
		modelMap.put("viewPaOfficialPayOffZongJiList", this.paReportC04Ser
				.getPaOfficialPayOffZongJiList(request));// 总计
		return new ModelAndView("/report/pa/viewPaLivewareTranserExcel",
				modelMap);
	}

	/**
	 * 跳转到导出工资汇总表页面（按月份查询）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaSummarizeInfo")
	public ModelAndView viewPaSummarizeInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");
		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());
		return new ModelAndView("/report/pac04/viewPaSummarizeInfo", modelMap);
	}

	/**
	 * 工资汇总表 （按月份查询），导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaSummarizeInfoTranserExcel")
	public ModelAndView viewPaSummarizeInfoTranserExcel(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ddate = sdf.format(dt);
		modelMap.put("date", ddate);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));

		modelMap.put("viewPaSummarizeList", this.paReportC04Ser
				.getPaSummarizeList(request));// 获取员工工资信息 正式工 生产部 直接
		modelMap.put("viewPaSummarizeQiTaList", this.paReportC04Ser
				.getPaSummarizeQiTaList(request));// 获取员工工资信息 正式工 生产部 其他员工
		modelMap.put("zhengShiShengChanXiaoJi", this.paReportC04Ser
				.getZhengShiShengChanXiaoJiList(request));// 获取员工工资信息 正式工 生产部
															// 直接小计 跟间接小计 总计
		modelMap.put("zhengShiGuanLi", this.paReportC04Ser
				.getZhengShiGuanLiList(request));// 获取员工工资信息 正式工 管理部 间接 间接小计
		modelMap.put("zhuZaiYuan", this.paReportC04Ser
				.getZhuZaiYuanList(request));// 获取员工工资信息 驻在员
		modelMap.put("zhengShiGuanLiXiaoJi", this.paReportC04Ser
				.getZhengShiGuanLiXiaoJiList(request));// 获取员工工资信息 正式工 管理部 间接
														// 间接小计

		modelMap.put("viewPaSummarizeLaoWuList", this.paReportC04Ser
				.getPaSummarizeLaoWuList(request));// 获取员工工资信息 劳务工 生产部 直接
		modelMap.put("viewPaSummarizeLaoWuXiaoJiList", this.paReportC04Ser
				.getPaSummarizeLaoWuXiaoJiList(request));// 获取员工工资信息 劳务工 生产部
															// 直接小计
		modelMap.put("laoWuShengChanJianJieList", this.paReportC04Ser
				.getLaoWuShengChanJianJieList(request));// 获取员工工资信息 劳务工 生产部
														// jian接小计
		modelMap.put("laoWuGuanLiJianJieList", this.paReportC04Ser
				.getLaoWuGuanLiJianJieList(request));// 获取员工工资信息 劳务工 管你部 间接
		modelMap.put("laoWuGuanLiJianJieXiaoJiList", this.paReportC04Ser
				.getLaoWuGuanLiJianJieXiaoJiList(request));// 获取员工工资信息 劳务工 管你部
		modelMap.put("quanGongSiZhongFang", this.paReportC04Ser
				.getQuanGongSiZhongFangList(request));// 获取员工工资信息 全公司中方合计

		return new ModelAndView(
				"/report/pac04/viewPaSummarizeInfoTranserExcel", modelMap);
	}

	/**
	 * 跳转到导出科别考勤支付现状表页面（按月份查询）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaDivisionsAttendanceInfo")
	public ModelAndView viewPaDivisionsAttendance(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");

		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());
		return new ModelAndView("/report/pac04/viewPaDivisionsAttendanceInfo",
				modelMap);
	}

	/**
	 * 科别考勤支付现状表 （按月份查询），导出Excel用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaDivisionsAttendanceTranserExcel")
	public ModelAndView viewPaDivisionsAttendanceTranserExcel(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ddate = sdf.format(dt);
		modelMap.put("date", ddate);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));

		modelMap.put("shengChanList", this.paReportC04Ser
				.getShengChanList(request));// 获取员工工资信息
		modelMap.put("shengChanXiaoJiList", this.paReportC04Ser
				.getShengChanXiaoJiList(request));// 获取员工工资信息 小计
		modelMap.put("shengChanBuMenXiaoJiList", this.paReportC04Ser
				.getShengChanBuMenXiaoJiList(request));// 获取员工工资信息 部门小计
		modelMap.put("shengChanBuMenHeJiXiaoJiList", this.paReportC04Ser
				.getShengChanBuMenHeJiXiaoJiList(request));// 获取员工工资信息 部门合计里面的小计
		modelMap.put("shengChanZhiJieHeJiList", this.paReportC04Ser
				.getShengChanZhiJieHeJiList(request));// 获取员工工资信息 生产直接合计
		modelMap.put("shengChanZhiJieHeJiXiaoJiList", this.paReportC04Ser
				.getShengChanZhiJieHeJiXiaoJiList(request));// 获取员工工资信息
															// 生产直接合计里面的小计
		modelMap.put("shengChanZongHeList", this.paReportC04Ser
				.getShengChanZongHeList(request));// 获取员工工资信息 生产总和
		modelMap.put("shengChanZongHeXiaoJiList", this.paReportC04Ser
				.getShengChanZongHeXiaoJiList(request));// 获取员工工资信息 生产总和 小计

		modelMap.put("zongWuRenShiXiaoJiList", this.paReportC04Ser
				.getZongWuRenShiXiaoJiList(request));// 获取总务人事小计 正式 劳务 的
		modelMap.put("zongWuRenShiZongXiaoJiList", this.paReportC04Ser
				.getZongWuRenShiZongXiaoJiList(request));// 获取总务人事小计 总小计

		modelMap.put("guanLiBuHeJiList", this.paReportC04Ser
				.getGuanLiBuHeJiList(request));// 管理部合计 正式 劳务 的
		modelMap.put("guanLiBuHeJiXiaoJiList", this.paReportC04Ser
				.getGuanLiBuHeJiXiaoJiList(request));// 管理部合计 总小计

		modelMap.put("zhuZaiYuanMonthList", this.paReportC04Ser
				.getZhuZaiYuanMonthList(request));// 获取驻在员
		modelMap.put("gongSiZongJi", this.paReportC04Ser
				.getGongSiZongJiList(request));// 获取公司总总计 正式 劳务 分别 合计
		modelMap.put("gongSiZongJiXiaoJi", this.paReportC04Ser
				.getGongSiZongJiXiaoJiList(request));// 获取公司 总计的小计
		modelMap.put("gongSiZongJiRS", this.paReportC04Ser
				.getGongSiZongJiRSList(request));// 获取公司总总计人数

		modelMap.put("xiaoShouJinE", this.paReportC04Ser
				.getXiaoShouJinEList(request));// 获取销售金额

		return new ModelAndView(
				"/report/pac04/viewPaDivisionsAttendanceTranserExcel", modelMap);
	}
}
