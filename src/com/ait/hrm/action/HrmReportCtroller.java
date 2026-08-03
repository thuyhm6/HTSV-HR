package com.ait.hrm.action;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.hrm.service.EssApplyInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

@Controller
@RequestMapping(value = "/hrm/report")
public class HrmReportCtroller {

	Logger logger = Logger.getLogger(HrmReportCtroller.class);

	@Resource
	private DataSource dataSource;
	@Autowired
	private EssApplyInfoSer EssApplyInfoSer;

	/**
	 * 人事报表 (HR report)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewHrmReportsList")
	public ModelAndView viewHrmReportsList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/hrm/report/viewHrmReportsList", modelMap);
	}

	/**
	 * 人事报表 (HR report) 条件
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewHrmReportCondition")
	public ModelAndView viewHrmReportCondition(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String checkVal = request.getParameter("checkVal");
		modelMap.put("checkVal", request.getParameter("checkVal"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("ETIME", this.getDateNow("yyyyMMdd"));
		return new ModelAndView("/hrm/report/viewHrmReportCondition", modelMap);
	}

	public String getDateNow(String forma) {
		SimpleDateFormat format = new SimpleDateFormat(forma);

		String sysdate = "";

		Calendar c = Calendar.getInstance();
		sysdate = format.format(c.getTime());

		return sysdate;

	}

	@RequestMapping(value = "/payReport04")
	public void payReportC14(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String strSql = "";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (paramMap == null)
			return;
		String EMPID = paramMap.get("empid") != null ? paramMap.get("empid")
				.toString() : "";

		String PERSON_ID = paramMap.get("PERSON_ID") != null ? paramMap.get(
				"PERSON_ID").toString() : "";
		String ENTER_DATE = paramMap.get("ENTER_DATE") != null ? paramMap.get(
				"ENTER_DATE").toString() : "";
		String COMPANY = paramMap.get("company") != null ? paramMap.get(
				"company").toString() : "";
		String SEARCHDATE = paramMap.get("searchDate") != null ? paramMap.get(
				"searchDate").toString() : "";
		String checkVal = paramMap.get("checkVal") != null ? paramMap.get(
				"checkVal").toString() : "";
		String filename = paramMap.get("filename") != null ? paramMap.get(
				"filename").toString() : "tPayroll(C14)";

		paramMap.put("PERSON_ID", PERSON_ID);
		paramMap.put("ENTER_DATE", ENTER_DATE);
		paramMap.put("EMPID", EMPID);
		paramMap.put("COMPANY", COMPANY);
		paramMap.put("SEARCHDATE", SEARCHDATE);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("ADMIN_NAME", admin.getLocalName());
		paramMap.put("CURRENT_DATE", DateUtil.getSysdateStr("dd/MM/yyyy HH:mm"));

		Connection conn = dataSource.getConnection();
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String realPath = "";

		if (checkVal.equals("report1")) {

			filename = "empDaily";
			int DateNumber = Integer.parseInt(SEARCHDATE);// 2016/01/12之前的报表使用老版本
			if (DateNumber <= 20160111) {
				realPath = servletContext
						.getRealPath("/resources/reportFile/HRM/hrPersonRecordInfo1.jasper");
			} else {

				realPath = servletContext
						.getRealPath("/resources/reportFile/HRM/hrPersonRecordInfo.jasper");
			}
		} else if (checkVal.equals("report2")) {
			realPath = servletContext
					.getRealPath("/resources/reportFile/HRM/hrRenewTheContract.jasper");
		} else if (checkVal.equals("report3")) {
			realPath = servletContext
					.getRealPath("/resources/reportFile/HRM/hrRenewTheContract10.jasper");
		} else if (checkVal.equals("report4")) {
			realPath = servletContext
					.getRealPath("/resources/reportFile/HRM/hrDimissionCertification.jasper");
		} else if (checkVal.equals("report5")) {
			realPath = servletContext
					.getRealPath("/resources/reportFile/HRM/hrRenewTheContract.jasper");
		} else if (checkVal.equals("report6")) {
			realPath = servletContext
					.getRealPath("/resources/reportFile/HRM/hrmCard/"
							+ admin.getCpnyId() + "/hrmCard.jasper");
			paramMap.put("SUBREPORT_DIR", request
					.getRealPath("/resources/reportFile/HRM/hrmCard/"
							+ admin.getCpnyId())
					+ "/");
			paramMap.put("PHOTO_PATH", request.getRealPath(""));
		} else if (checkVal.equals("report7")) {
			realPath = servletContext.getRealPath("/resources/reportFile/HRM/hrmCard/" 
					+ admin.getCpnyId() + "/hrmCardList.jasper");
			paramMap.put("PHOTO_PATH", request.getRealPath(""));
		} else if (checkVal.equals("report8")) {
			realPath = servletContext.getRealPath("/resources/reportFile/HRM/hrmCard/"
							+ admin.getCpnyId() + "/hrmCardDetail.jasper");
			paramMap.put("SUBREPORT_DIR", request.getRealPath("/resources/reportFile/HRM/hrmCard/"
							+ admin.getCpnyId())
					+ "/");
			paramMap.put("PHOTO_PATH", request.getRealPath(""));
		} else if (checkVal.equals("report9")) {
			realPath = servletContext
			.getRealPath("/resources/reportFile/HRM/hrmCard/"
					+ admin.getCpnyId() + "/hrmCardevs.jasper");
			paramMap.put("SUBREPORT_DIR", request
					.getRealPath("/resources/reportFile/HRM/hrmCard/"
							+ admin.getCpnyId())
					+ "/");
			paramMap.put("PHOTO_PATH", request.getRealPath(""));
		}else if (checkVal.equals("report10")) {
			realPath = servletContext
			.getRealPath("/resources/reportFile/HRM/hrmCard/"
					+ admin.getCpnyId() + "/hrmCardDetailEvs.jasper");
			paramMap.put("SUBREPORT_DIR", request
					.getRealPath("/resources/reportFile/HRM/hrmCard/"
							+ admin.getCpnyId())
					+ "/");
			paramMap.put("PHOTO_PATH", request.getRealPath(""));
		} else if (checkVal.equals("report11")) {
			realPath = servletContext
			.getRealPath("/resources/reportFile/HRM/hrmCard/"
					+ admin.getCpnyId() + "/hrmCard.jasper");
			paramMap.put("SUBREPORT_DIR", request
					.getRealPath("/resources/reportFile/HRM/hrmCard/"
							+ admin.getCpnyId())
					+ "/");
			paramMap.put("PHOTO_PATH", request.getRealPath(""));
		} else if (checkVal.equals("report12")) {
			realPath = servletContext
			.getRealPath("/resources/reportFile/HRM/hrmCard/"
					+ admin.getCpnyId() + "/hrmCarden.jasper");
			paramMap.put("SUBREPORT_DIR", request
					.getRealPath("/resources/reportFile/HRM/hrmCard/"
							+ admin.getCpnyId())
					+ "/");
			paramMap.put("PHOTO_PATH", request.getRealPath(""));
		} else {
			String pdfName = StringUtil.checkNull(paramMap.get("pdfName"));
			if (!"".equals(pdfName)) {
				realPath = servletContext
						.getRealPath("/resources/reportFile/HRM/" + pdfName);
			} else {
				return;
			}
		}
		// 装载jasper文件application
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(realPath);
		ByteArrayOutputStream oStream = null;

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
							"attachment; filename=\\" + filename + ".pdf");
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

	// 生成人员日报
	@RequestMapping(value = "/callPD")
	@ResponseBody
	public Map<String, Object> callPD(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		int result = this.EssApplyInfoSer.callPD(request);

		Map<String, Object> map = new HashMap<String, Object>();
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "生成成功");
		} else {
			map.put("statusCode", "300");
			map.put("message", "生成失败");
		}
		return map;
	}

	@RequestMapping(value = "/changeOrzTemp")
	public ModelAndView changeOrzTemp(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("DATE", request.getParameter("seach_DATE"));
		modelMap.put("allDeptList", EssApplyInfoSer.getAllDeptList(request));
		return new ModelAndView("/hrm/report/changeOrzTemp", modelMap);
	}

	@RequestMapping(value = "/saveOrzTemp")
	@ResponseBody
	public Map<String, Object> saveOrzTemp(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		int result = 0;
		if (request.getParameter("from_Value") != null
				&& !request.getParameter("from_Value").equals("")) {
			result = this.EssApplyInfoSer.saveOrzTemp(request);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
}
