package com.ait.report.ar.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;


import com.ait.ar.service.ArDetailSer;
import com.ait.disc.action.RetrieveMasterListCtroller;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.JobTypeSer;
import com.ait.pa.service.workManagement.PaPayScheduleSer;
import com.ait.report.ar.service.ArReportSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArReportCtroller.java
 * @Description: Controller Class ArReportCtroller.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */

@Controller
@RequestMapping(value = "/report/ar")
@SuppressWarnings( { "unchecked", "static-access" })
public class ArReportCtroller {
	Logger logger = Logger.getLogger(ArReportCtroller.class);
	@Autowired
	private ArReportSer arReportSer;
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private JobTypeSer jobTypeSer;
	@Autowired
	private ArDetailSer arDetailSer;

	@Autowired
	private PaPayScheduleSer paPayScheduleSer;

	@Autowired
	private RetrieveMasterListCtroller RetrieveMasterListCtroller;

	/**
	 * 考勤报表 (AR report) and 工资报表 and 人事报表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArReportsList")
	public ModelAndView viewArReportsList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String mentNo = request.getParameter("menuNo");// 工资的是14013782
		modelMap.put("mentNo_Flag", mentNo);
		if (mentNo.equals("14013782")) {
			List codeInfoTreeList = this.arReportSer.getCodeListByParentCode(
					request, "22116"); // 工资的报表类型code_no 22116 考勤的的报表类型code_no
			// 22116 考勤的的报表类型code_no 22116
			// 人事报表的报表类型code_no 14015311
			List reportList = this.arReportSer.getreportList(request); // 报表
			modelMap.put("codeInfoTreeList", codeInfoTreeList);
			modelMap.put("reportList", reportList);
		} else if (mentNo.equals("14013665")) {// 人事的报表
			List codeInfoTreeList = this.arReportSer.getCodeListByParentCode(
					request, "14015311"); // 工资的报表类型code_no 22116
			// 人事报表的报表类型code_no 14015311
			List reportList = this.arReportSer.getreportList(request); // 报表
			modelMap.put("codeInfoTreeList", codeInfoTreeList);
			modelMap.put("reportList", reportList);
		} else if (mentNo.equals("14013758")) {
			List codeInfoTreeList = this.arReportSer.getCodeListByParentCode(
					request, "22114"); // 工资的报表类型code_no 22116 考勤的的报表类型code_no
			// 22116 考勤的的报表类型code_no 22116
			List reportList = this.arReportSer.getreportList(request); // 报表
			modelMap.put("codeInfoTreeList", codeInfoTreeList);
			modelMap.put("reportList", reportList);
		} else if (mentNo.equals("14014477")) {// 培训教育的报表
			List codeInfoTreeList = this.arReportSer.getCodeListByParentCode(
					request, "14015405");
			List reportList = this.arReportSer.getreportList(request); // 报表
			modelMap.put("codeInfoTreeList", codeInfoTreeList);
			modelMap.put("reportList", reportList);
		}
		
		return new ModelAndView("/report/ar/viewArReportsList", modelMap);
	}

	/**
	 * 培训报表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTrainReport")
	public ModelAndView viewTrainReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String mentNo = request.getParameter("menuNo");// 工资的是14013782
		if (mentNo.equals("14014477")) {// 培训教育的报表
			List codeInfoTreeList = this.arReportSer.getCodeListByParentCode(
					request, "14015405");
			List reportList = this.arReportSer.getreportList(request); // 报表
			modelMap.put("codeInfoTreeList", codeInfoTreeList);
			modelMap.put("reportList", reportList);
		}
		return new ModelAndView("/report/ar/viewTrainReport", modelMap);
	}

	/**
	 * 部门月考勤报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArMonthParamList")
	public ModelAndView viewArMonthParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/report/ar/viewArMonthParamList", modelMap);
	}

	/**
	 * 这个页面是工资月别对比页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayBankPayoutList")
	public ModelAndView viewPayBankPayoutList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("SUBREPORT_DIR", request
				.getRealPath("/resources/reportFile")
				+ "/");

		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView("/report/ar/viewPayBankPayoutList", modelMap);
	}

	/**
	 * 密码工资单
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPasswordPaCardList")
	public ModelAndView viewPasswordPaCardList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("SUBREPORT_DIR", request
				.getRealPath("/resources/reportFile")
				+ "/");

		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView("/report/ar/viewPasswordPaCardList", modelMap);
	}

	/**
	 * 部门工资明细报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayPayDetailList")
	public ModelAndView viewPayPayDetailList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/ar/viewPayPayDetailList", modelMap);
	}

	/**
	 * LGEHN部门工资明细报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayPayDetailListLGEHN")
	public ModelAndView viewPayPayDetailListLGEHN(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/ar/viewPayPayDetailListLGEHN",
				modelMap);
	}

	/**
	 * 人事财务核对报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayChrsAccMappingList")
	public ModelAndView viewPayChrsAccMappingList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView("/report/ar/viewPayChrsAccMappingList",
				modelMap);
	}

	/**
	 * 部门日考勤报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAttRetrieveindMonthByCollectList")
	public ModelAndView viewAttRetrieveindMonthByCollectList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView(
				"/report/ar/viewAttRetrieveindMonthByCollectList", modelMap);
	}

	/**
	 *员工月考勤报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 *             viewAttRetrieveDeptMonAttByEmpList
	 */
	@RequestMapping(value = "/viewAttRetrieveDeptMonAttByEmpList")
	public ModelAndView viewAttRetrieveDeptMonAttByEmpList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView(
				"/report/ar/viewAttRetrieveDeptMonAttByEmpList", modelMap);
	}

	/**
	 *员工加班报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 *             viewAttRetrieveDeptOTSumList
	 */
	@RequestMapping(value = "/viewAttRetrieveDeptOTSumList")
	public ModelAndView viewAttRetrieveDeptOTSumList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView("/report/ar/viewAttRetrieveDeptOTSumList",
				modelMap);
	}

	/**
	 *员工加班报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 *             viewAttRetrieveDepartmentKindList
	 */
	@RequestMapping(value = "/viewAttRetrieveDepartmentKindList")
	public ModelAndView viewAttRetrieveDepartmentKindList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		List getCodeList = arReportSer.getArCodeNameByCode(request);
		modelMap.put("getCodeList", getCodeList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/report/ar/viewAttRetrieveDepartmentKindList",
				modelMap);
	}

	/**
	 *年度工作类型加班报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 *             viewAttRetrieveJobtpAttYearList
	 */
	@RequestMapping(value = "/viewAttRetrieveJobtpAttYearList")
	public ModelAndView viewAttRetrieveJobtpAttYearList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/ar/viewAttRetrieveJobtpAttYearList",
				modelMap);
	}

	/**
	 *年度考勤总结报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 *             viewAttRetrieveMoAtttByYearList
	 */
	@RequestMapping(value = "/viewAttRetrieveMoAtttByYearList")
	public ModelAndView viewAttRetrieveMoAtttByYearList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/report/ar/viewAttRetrieveMoAtttByYearList",
				modelMap);
	}

	/**
	 *青岛员工加班报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 *             viewAttRetrieveLGEQDOTAppl_nosignList
	 */
	@RequestMapping(value = "/viewAttRetrieveLGEQDOTAppl_nosignList")
	public ModelAndView viewAttRetrieveLGEQDOTAppl_nosignList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView(
				"/report/ar/viewAttRetrieveLGEQDOTAppl_nosignList", modelMap);
	}

	/**
	 *年度部门加班统计报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 *             viewAttRetrieveDeptAttYearList
	 */
	@RequestMapping(value = "/viewAttRetrieveDeptAttYearList")
	public ModelAndView viewAttRetrieveDeptAttYearList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/report/ar/viewAttRetrieveDeptAttYearList",
				modelMap);
	}

	/**
	 *年度报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 *             viewAttRetrieveVacationList
	 */
	@RequestMapping(value = "/viewAttRetrieveVacationList")
	public ModelAndView viewAttRetrieveVacationList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/report/ar/viewAttRetrieveVacationList",
				modelMap);
	}

	/**
	 *员工加班报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 *             viewAttRetrieveEmpOTSumList
	 */
	@RequestMapping(value = "/viewAttRetrieveEmpOTSumList")
	public ModelAndView viewAttRetrieveEmpOTSumList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView("/report/ar/viewAttRetrieveEmpOTSumList",
				modelMap);
	}

	/**
	 *员工旷工报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 *             viewAttRetrieveAbsenceList
	 */
	@RequestMapping(value = "/viewAttRetrieveAbsenceList")
	public ModelAndView viewAttRetrieveAbsenceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/report/ar/viewAttRetrieveAbsenceList",
				modelMap);
	}

	/**
	 *员工日考勤报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAttRetrieveEmpAttDayList")
	public ModelAndView viewAttRetrieveEmpAttDayList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/report/ar/viewAttRetrieveEmpAttDayList",
				modelMap);
	}

	/**
	 *员工未刷卡报表条件页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 *             viewAttRetrieveNoCardList
	 */
	@RequestMapping(value = "/viewAttRetrieveNoCardList")
	public ModelAndView viewAttRetrieveNoCardList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/report/ar/viewAttRetrieveNoCardList",
				modelMap);
	}

	/**
	 * 考勤报表预览页 (AR report)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArMonthList")
	public ModelAndView viewArMonthList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		System.out.println(request.getParameter("seach_abc"));
		return new ModelAndView("/report/ar/viewArMonthList", modelMap);
	}

	/**
	 * 按部门导出考勤excel页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArDeptShiftList")
	public ModelAndView viewArDeptShiftList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");

		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());
		return new ModelAndView("/report/ar/viewArDeptShiftList", modelMap);
	}

	/**
	 * 导出考勤报表 按部门分组导出
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArDeptShiftListTranserExcel")
	public ModelAndView getArDeptShiftListTranserExcel(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		List aliasNameList = new ArrayList();
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");

		String year = request.getParameter("seach_YEAR");
		String month = request.getParameter("seach_MONTH");
		String searchTime = year + "-" + month + "-01";

		cal.setTime(datef.parse(searchTime));
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		int endDay = cal.getActualMaximum(cal.DAY_OF_MONTH);// 当前月的最后一天

		for (int i = 1; i <= endDay; i++) {
			if (i < 10) {
				aliasNameList.add(searchTime.substring(0, 8) + "0" + i);
			} else {
				aliasNameList.add(searchTime.substring(0, 8) + i);
			}
		}

		Map paramMap = new LinkedHashMap();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("AR_MONTH", year + "-" + month);
		paramMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		paramMap.put("STAT_NO", request.getParameter("STAT_NO"));

		List personalList = this.arReportSer.getArDeptShiftList(paramMap);
		int num = 0;
		List tempList = new ArrayList();
		for (int i = 0; i < personalList.size(); i = num) {
			LinkedHashMap tempMap = new LinkedHashMap();
			tempMap.put("EMPID", ((LinkedHashMap) personalList.get(i))
					.get("EMPID"));
			tempMap.put("LOCAL_NAME", ((LinkedHashMap) personalList.get(i))
					.get("LOCAL_NAME"));
			tempMap.put("DEPTNAME", ((LinkedHashMap) personalList.get(i))
					.get("DEPTNAME"));
			for (int j = i; j < personalList.size(); j++) {
				if (((LinkedHashMap) personalList.get(i)).get("EMPID").equals(
						((LinkedHashMap) personalList.get(j)).get("EMPID"))) {
					tempMap.put(((LinkedHashMap) personalList.get(j))
							.get("AR_DATE_STR"), ((LinkedHashMap) personalList
							.get(j)).get("SHIFT_NAME"));
					num++;
				} else {
					break;
				}
			}
			tempList.add(tempMap);
		}
		List returnList = new ArrayList();
		List comCal = this.arReportSer.getArCompanyCalendarShiftList(paramMap);
		for (int i = 0; i < tempList.size(); i++) {
			LinkedHashMap returnMap = new LinkedHashMap();
			returnMap.put("EMPID", ((LinkedHashMap) tempList.get(i))
					.get("EMPID"));
			returnMap.put("LOCAL_NAME", ((LinkedHashMap) tempList.get(i))
					.get("LOCAL_NAME"));
			returnMap.put("DEPTNAME", ((LinkedHashMap) tempList.get(i))
					.get("DEPTNAME"));
			List list = new ArrayList();
			for (int j = 0; j < aliasNameList.size(); j++) {
				System.out.println(((LinkedHashMap) tempList.get(i))
						.get(aliasNameList.get(j))
						+ "=================================="
						+ aliasNameList.get(j));
				if (null != ((LinkedHashMap) tempList.get(i)).get(aliasNameList
						.get(j))) {
					list.add(((LinkedHashMap) tempList.get(i))
							.get(aliasNameList.get(j)));
					// returnMap.put(aliasNameList.get(j),
					// ((LinkedHashMap)tempList.get(i)).get(aliasNameList.get(j)));
				} else {
					list.add(((LinkedHashMap) comCal.get(j)).get("SHIFT_NAME"));
					// returnMap.put(aliasNameList.get(j), "");
				}
			}
			returnMap.put("SHIFT_NAME", list);
			returnList.add(returnMap);
		}
		modelMap.put("returnList", returnList);
		modelMap.put("aliasNameList", aliasNameList);
		return new ModelAndView("/report/ar/viewArDeptShiftListTranserExcel",
				modelMap);
	}

	/**
	 * 正式工工资汇总页面 (AR report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaSalarySumList")
	public ModelAndView viewPaSalarySumList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("SUBREPORT_DIR", request
				.getRealPath("/resources/reportFile")
				+ "/");

		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView("/report/ar/viewPaSalarySumList", modelMap);
	}

	/**
	 * 正式工工资明细页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaSalaryDetailList")
	public ModelAndView viewPaSalaryDetailList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView("/report/ar/viewPaSalaryDetailList", modelMap);
	}

	/**
	 * 个人保险明细页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaInsuranceDetailList")
	public ModelAndView viewPaInsuranceDetailList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String S_DATE= simpleDateFormat.format(new Date());
		String E_DATE= simpleDateFormat.format(new Date());
		modelMap.put("S_DATE", S_DATE);
		modelMap.put("E_DATE", E_DATE);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView("/report/ar/viewPaInsuranceDetailList",
				modelMap);
	}

	/**
	 * 密码工资单页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaPasswordSalaryList")
	public ModelAndView viewPaPasswordSalaryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView("/report/ar/viewPaPasswordSalaryList", modelMap);
	}

	// 半自动excle报表

	/**
	 * 考勤报表 月间人力报告
	 * 
	 */
	@RequestMapping(value = "/viewHrPersonPowerListExport")
	public void viewHrPersonPowerListExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 正规非正规区分
		modelMap.put("getHrPersonFormalList", arReportSer
				.getHrPersonFormalList(request));
		// 入社增减区分
		modelMap.put("getHrPersonEntryList", arReportSer
				.getHrPersonEntryList(request));
		// 职级别人力
		modelMap.put("getHrPersonRankList", arReportSer
				.getHrPersonRankList(request));
		// part别增减情况
		modelMap.put("getHrPersonDeptList", arReportSer
				.getHrPersonDeptList(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String WM_FLAG = request.getParameter("WM_FLAG");
		if (WM_FLAG.equals("1")) {
			modelMap.put("XLS_NAME", "月间人力报告");

			modelMap.put("XLS_IN", "getHrPersonPowerListExport");
		} else {

			modelMap.put("XLS_NAME", "周间人力报告");

			modelMap.put("XLS_IN", "getHrPersonPowerListExportW");
		}
		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}

	/**
	 * 月间人力报告
	 * 
	 */
	@RequestMapping(value = "/viewHrPersonPowerList")
	public ModelAndView viewHrPersonPowerList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		String DATE = request.getParameter("seach_DATE");

		modelMap.put("FROM_DATE", this.getDateLast("yyyy/MM"));

		modelMap.put("TO_DATE", this.getDateNow("yyyy/MM"));

		modelMap.put("TO_DATE_W", this.getDateNow("yyyy/MM/dd"));

		return new ModelAndView("/report/ar/viewHrPersonPowerList", modelMap);
	}

	/**
	 * 申报个人所得税
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewIndividualIncome")
	public ModelAndView viewIndividualIncome(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		return new ModelAndView("/report/ar/viewIndividualIncome", modelMap);
	}

	// 获取上个月第一天 传入的是 日期格式
	public String getDateLast(String forma) {
		SimpleDateFormat format = new SimpleDateFormat(forma);
		String first = "";
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH, 0);
		first = format.format(c.getTime());
		return first;
	}

	// 获取当前日期

	public String getDateNow(String forma) {
		SimpleDateFormat format = new SimpleDateFormat(forma);

		String sysdate = "";

		Calendar c = Calendar.getInstance();
		sysdate = format.format(c.getTime());

		return sysdate;

	}

}
