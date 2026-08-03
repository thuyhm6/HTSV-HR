package com.ait.report.pa.action;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.JobTypeSer;
import com.ait.pa.service.insurance.InsuranceComputeItemSer;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.pa.service.salarycode.salaryCodeSer;
import com.ait.pa.service.workManagement.PaPayScheduleSer;
import com.ait.report.ar.service.ArReportSer;
import com.ait.report.hr.service.HrReportSer;
import com.ait.report.pa.service.PaReportSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AjaxSer;
import com.ait.sys.service.salaryMappingSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: PaReportCtroller.java
 * @Description: Controller Class PaReportCtroller.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/report/pa")
public class PaReportCtroller {
	Logger logger = Logger.getLogger(PaReportCtroller.class);
	@Autowired
	private PaReportSer paReportSer;
	@Autowired
	private JobTypeSer jobTypeSer;
	@Autowired
	private ArReportSer arReportSer;
	@Autowired
	private HrReportSer hrReportSer;
	@Autowired
	private AjaxSer ajaxSer;
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private InsuranceComputeItemSer insuranceComputeItemSer;
	@Autowired
	private salaryCodeSer salaryCodeSer;
	@Autowired
	private salaryMappingSer salaryMappingSer;
	@Autowired
	private PaPayScheduleSer paPayScheduleSer;

	/**
	 * 部门工资明细表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayPayDetailListLGECHList")
	public ModelAndView viewPayPayDetailListLGECHList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayPayDetailListLGECHList",
				modelMap);
	}

	/**
	 * 部门工资明细表-条件页面 LGEHZ法人(pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayPayDetailListLGEHZ")
	public ModelAndView viewPayPayDetailListLGEHZ(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayPayDetailListLGEHZ",
				modelMap);
	}

	/**
	 * 部门工资月汇总-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayPayMonDeptSumLGEList")
	public ModelAndView viewPayPayMonDeptSumLGEList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayPayMonDeptSumLGEList",
				modelMap);
	}

	/**
	 * 人员类型别月工资汇总表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayPayMonDeptSumForJobTpLGEList")
	public ModelAndView viewPayPayMonDeptSumForJobTpLGEList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView(
				"/report/pa/viewPayPayMonDeptSumForJobTpLGEList", modelMap);
	}

	/**
	 * FSE工资列表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaMonthParamList")
	public ModelAndView viewPaMonthParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPaMonthParamList", modelMap);
	}

	/**
	 * FSE工资个人所得税汇总表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayFsePersonTaxDetailList")
	public ModelAndView viewPayFsePersonTaxDetailList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayFsePersonTaxDetailList",
				modelMap);
	}

	/**
	 * FSE工资明细人事财务核对表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewFsePayDetailChrsAccMapping")
	public ModelAndView viewFsePayDetailChrsAccMapping(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewFsePayDetailChrsAccMapping",
				modelMap);
	}

	/**
	 * FSE税金详细报表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayPayFSETaxDetai")
	public ModelAndView viewPayPayFSETaxDetai(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayPayFSETaxDetai", modelMap);
	}

	/**
	 * FSE人事与财务工资核对表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewFsePayChrsAccMapping")
	public ModelAndView viewFsePayChrsAccMapping(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewFsePayChrsAccMapping", modelMap);
	}

	/**
	 * 福利JOB_TP汇总报表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWelAmountbyJob_TpList")
	public ModelAndView viewWelAmountbyJob_TpList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewWelAmountbyJob_TpList",
				modelMap);
	}

	/**
	 * 福利保险按类型详细-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWelTypeDetailList")
	public ModelAndView viewWelTypeDetailList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("itemList", insuranceComputeItemSer
				.getInsuranceComputeItemList(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewWelTypeDetailList", modelMap);
	}

	/**
	 * FSE详细报表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayFSEDetailListLGECH")
	public ModelAndView viewPayFSEDetailListLGECH(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayFSEDetailListLGECH",
				modelMap);
	}

	/**
	 * FSE住房详细报表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayHouseLGECH")
	public ModelAndView viewPayHouseLGECH(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayHouseLGECH", modelMap);
	}

	/**
	 * 促销工资明细报表查询条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayDetailListPromtrList")
	public ModelAndView viewPayDetailListPromtrList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayDetailListPromtrList",
				modelMap);
	}

	/**
	 * 促销预提工资明细报表查询条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewYuTiPayDetailListPromtrList")
	public ModelAndView viewYuTiPayDetailListPromtrList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewYuTiPayDetailListPromtrList",
				modelMap);
	}

	/**
	 * 法人别工资调整人员比例报表查询条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayRetrieveAdjustRateLgeList")
	public ModelAndView viewPayRetrieveAdjustRateLgeList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayRetrieveAdjustRateLgeList",
				modelMap);
	}

	/**
	 * ND法人部门工资明细(pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayPayDetailListLGENDList")
	public ModelAndView viewPayPayDetailListLGENDList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayPayDetailListLGENDList",
				modelMap);
	}

	/**
	 * TA法人福利公司个人(pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWelPersonCompanyList")
	public ModelAndView viewWelPersonCompanyList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewWelPersonCompanyList", modelMap);
	}

	/**
	 * 反映个人工资登录报表查询条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewpayPsnADInfactLGEQDList")
	public ModelAndView viewpayPsnADInfactLGEQDList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewpayPsnADInfactLGEQDList",
				modelMap);
	}

	/**
	 *福利按类型年度报表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWelInsuranceYearlyList")
	public ModelAndView viewWelInsuranceYearlyList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewWelInsuranceYearlyList",
				modelMap);
	}

	/**
	 *TA福利保险核对报表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWelRetrieveWealCheckListLGETAList")
	public ModelAndView viewWelRetrieveWealCheckListLGETAList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView(
				"/report/pa/viewWelRetrieveWealCheckListLGETAList", modelMap);
	}

	/**
	 *福利年度公司个人报表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonRetrieveWealCheckListLGETAList")
	public ModelAndView viewPersonRetrieveWealCheckListLGETAList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView(
				"/report/pa/viewPersonRetrieveWealCheckListLGETAList", modelMap);
	}

	/**
	 *福利报表组织查询-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWelAmountbyOrgList")
	public ModelAndView viewWelAmountbyOrgList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView("/report/pa/viewWelAmountbyOrgList", modelMap);
	}

	/**
	 *福利详细报表查询-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWelAmountbyEmpJVList")
	public ModelAndView viewWelAmountbyEmpJVList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewWelAmountbyEmpJVList", modelMap);
	}

	/**
	 *福利详细报表LGECH查询-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWelAmountbyEmpList")
	public ModelAndView viewWelAmountbyEmpList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewWelAmountbyEmpList", modelMap);
	}

	/**
	 *福利公司和个人承担查询-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWelAmountbyCompanyEmpList")
	public ModelAndView viewWelAmountbyCompanyEmpList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewWelAmountbyCompanyEmpList",
				modelMap);
	}

	/**
	 *个人工资基础确认表-条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayRetrievePsnBasicConfirmList")
	public ModelAndView viewPayRetrievePsnBasicConfirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView(
				"/report/pa/viewPayRetrievePsnBasicConfirmList", modelMap);
	}

	/**
	 * 工厂部门费用报表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayExpensebyDepartmentJVList")
	public ModelAndView viewPayExpensebyDepartmentJVList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayExpensebyDepartmentJVList",
				modelMap);
	}

	/**
	 * 个人所得税纳税申请报表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayRetrieveTaxYearFseList")
	public ModelAndView viewPayRetrieveTaxYearFseList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayRetrieveTaxYearFseList",
				modelMap);
	}

	/**
	 * 工资人员调整比例
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayRetrieveAdjustRateList")
	public ModelAndView viewPayRetrieveAdjustRateList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayRetrieveAdjustRateList",
				modelMap);
	}

	/**
	 * 年度人件费报表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayPayRetrieveRJFMonList")
	public ModelAndView viewPayPayRetrieveRJFMonList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayPayRetrieveRJFMonList",
				modelMap);
	}

	/**
	 * 临时职工资明细页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewpayPayMentbyTempEmpLGETAList")
	public ModelAndView viewpayPayMentbyTempEmpLGETAList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("insrarea", empInfoSer.getInsrareaList(request));
		return new ModelAndView("/report/pa/viewpayPayMentbyTempEmpLGETAList",
				modelMap);
	}

	/**
	 * 人事财务工资核对
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayChrsAccMappingList")
	public ModelAndView viewPayChrsAccMappingList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayChrsAccMappingList",
				modelMap);
	}

	/**
	 * 月别人件费
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayPayRetrieveRJFList")
	public ModelAndView viewPayPayRetrieveRJFList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayPayRetrieveRJFList",
				modelMap);
	}

	/**
	 * 个人说的税查询报表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayRetrieveTaxFindingFseList")
	public ModelAndView viewPayRetrieveTaxFindingFseList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayPayRetrieveRJFList",
				modelMap);
	}

	/**
	 * 
	 * 临时职打印工资条明细报表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewpayPayMentbyTempEmpList")
	public ModelAndView viewpayPayMentbyTempEmpList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		// List jobTypeList = this.jobTypeSer.getjob(request);
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("insrarea", empInfoSer.getInsrareaList(request));
		// modelMap.put("jobList", jobTypeList);
		return new ModelAndView("/report/pa/viewpayPayMentbyTempEmpList",
				modelMap);
	}

	/**
	 * 
	 * 临时职工资明细报表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayMentbyTempEmpLGETAList")
	public ModelAndView viewPayMentbyTempEmpLGETAList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("EMPID", admin.getEmpID());

		return new ModelAndView("/report/pa/viewPayMentbyTempEmpLGETAList",
				modelMap);
	}

	/**
	 * 
	 * 实发工资列表报表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayPayerList")
	public ModelAndView viewPayPayerList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		List jobTypeGroupNameList = this.jobTypeSer
				.getJobTypeGroupNameList(request);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView("/report/pa/viewPayPayerList", modelMap);
	}

	/**
	 * 
	 * 业务员工资明细报表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaySalesManList")
	public ModelAndView viewPaySalesManList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPaySalesManList", modelMap);
	}

	/**
	 * 
	 * 预提工资查询报表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayPayAcruedListLGETAList")
	public ModelAndView viewPayPayAcruedListLGETAList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		return new ModelAndView("/report/pa/viewPayPayAcruedListLGETAList",
				modelMap);
	}

	/**
	 * 费用按工资代码报表查询条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayAmountbyPayCodeList")
	public ModelAndView viewPayAmountbyPayCodeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));

		// request.getParameter(PAGE_NUM_NAME) != null &&
		// request.getParameter(PAGE_NUM_NAME) != ""
		List salaryCodeList = this.salaryMappingSer.getSalaryMappingListForAll(
				request, modelMap);// 查出某法人的工资代码

		// List salaryCodeList =
		// this.salaryCodeSer.getSalaryCodeList(request);//查出所有工资项目
		modelMap.put("salarycodelist", salaryCodeList);
		return new ModelAndView("/report/pa/viewPayAmountbyPayCodeList",
				modelMap);
	}

	/**
	 * 个人工资报表查询条件页面 (pa report Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPayRetrieveindividualPayList")
	public ModelAndView viewPayRetrieveindividualPayList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("EMPID", admin.getEmpID());
		modelMap.put("DEPTNO", admin.getDeptNo());
		return new ModelAndView("/report/pa/viewPayRetrieveindividualPayList",
				modelMap);
	}

	/**
	 * 工资报表 (PA report)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaReportsList")
	public ModelAndView viewPaReportsList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List codeInfoTreeList = this.arReportSer.getCodeListByParentCode(
				request, "22116"); // 工资报表类型code_no
		List reportList = this.arReportSer.getreportList(request); // 报表
		modelMap.put("codeInfoTreeList", codeInfoTreeList);
		modelMap.put("reportList", reportList);

		return new ModelAndView("/report/pa/viewPaReportsList", modelMap);
	}

	/**
	 * FSE工资报表 (PA report)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFSEPAReportList")
	public ModelAndView viewFSEPAReportList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List codeInfoTreeList = this.arReportSer.getCodeListByParentCode(
				request, "299024"); // 工资报表类型code_no
		List reportList = this.arReportSer.getreportList(request); // 报表
		modelMap.put("codeInfoTreeList", codeInfoTreeList);
		modelMap.put("reportList", reportList);

		return new ModelAndView("/report/pa/viewFSEPAReportList", modelMap);
	}

	/**
	 * 跳转到查看员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaTranserInfo")
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

		return new ModelAndView("/report/pa/viewPaTranserInfo", modelMap);
	}

	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)，查看页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaTranserList")
	public ModelAndView viewPaTranserList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("EMPID", request.getParameter("seach_EMPID"));
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		modelMap.put("pageNum",
				request.getParameter("pageNum") != null ? request
						.getParameter("pageNum") : "1");
		modelMap.put("numPerPage",
				request.getParameter("numPerPage") != null ? request
						.getParameter("numPerPage") : "10");

		modelMap.put("paTranserList", this.paReportSer
				.getPaTranserList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paReportSer
				.getPaTranserListCnt(request));

		return new ModelAndView("/report/pa/viewPaTranserList", modelMap);
	}

	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by
	 * monthed)，导出Excel用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaTranserExcel")
	public ModelAndView viewPaTranserExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));

		modelMap.put("paTranserList", this.paReportSer
				.getPaTranserExcelList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paReportSer
				.getPaTranserListCnt(request));

		return new ModelAndView("/report/pa/viewPaTranserExcel", modelMap);
	}

	/**
	 * 跳转到查看SAP工资信息页面(query the SAP pa info )
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSapPaError")
	public ModelAndView viewSapPaErrorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/report/pa/viewSapPaError", modelMap);
	}

	/**
	 * 跳转到查看SAP工资信息页面(query the SAP pa info )
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSapPaInfo")
	public ModelAndView viewSapPaInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");

		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());

		return new ModelAndView("/report/pa/viewSapPaInfo", modelMap);
	}

	/**
	 * SAP工资信息（按月份查询）(query the sap pa info)，查看页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSapPaList")
	public ModelAndView viewSapPaList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		modelMap.put("pageNum",
				request.getParameter("pageNum") != null ? request
						.getParameter("pageNum") : "1");
		modelMap.put("numPerPage",
				request.getParameter("numPerPage") != null ? request
						.getParameter("numPerPage") : "10");

		LinkedHashMap dataMap = new LinkedHashMap();
		dataMap = this.paReportSer.getSapPaInfoList(request);
		if (dataMap != null) {
			modelMap.put("departList", dataMap.get("departList"));
			modelMap.put("paItemList", dataMap.get("paItemList"));
			modelMap.put("hrmCountList", dataMap.get("hrmCountList"));
			modelMap.put("sapPaList", dataMap.get("sapPaList"));
		} else {
			modelMap.put("departList", "");
			modelMap.put("paItemList", "");
			modelMap.put("hrmCountList", "");
			modelMap.put("sapPaList", "");
		}
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, '0');

		return new ModelAndView("/report/pa/viewSapPaList", modelMap);
	}

	/**
	 * SAP工资信息（按月份查询）(query the sap pa info)，导出Excel用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSapPaExcel")
	public ModelAndView viewSapPaExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		modelMap.put("pageNum",
				request.getParameter("pageNum") != null ? request
						.getParameter("pageNum") : "1");
		modelMap.put("numPerPage",
				request.getParameter("numPerPage") != null ? request
						.getParameter("numPerPage") : "10");

		LinkedHashMap dataMap = new LinkedHashMap();
		dataMap = this.paReportSer.getSapPaInfoList(request);
		if (dataMap != null) {
			modelMap.put("departList", dataMap.get("departList"));
			modelMap.put("paItemList", dataMap.get("paItemList"));
			modelMap.put("paItemListCnt", ((List) dataMap.get("paItemList"))
					.size());
			modelMap.put("hrmCountList", dataMap.get("hrmCountList"));
			modelMap.put("sapPaList", dataMap.get("sapPaList"));
		} else {
			modelMap.put("departList", "");
			modelMap.put("paItemList", "");
			modelMap.put("paItemListCnt", "0");
			modelMap.put("hrmCountList", "");
			modelMap.put("sapPaList", "");
		}
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, '0');

		return new ModelAndView("/report/pa/viewSapPaExcel", modelMap);
	}

	/**
	 * 报表中心--跳转到在职证明页面(query the office prove info )
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOfficeProveInfo")
	public ModelAndView viewOfficeProveInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");

		modelMap.put("YEAR", b[0].trim().toString());
		modelMap.put("MONTH", b[1].trim().toString());

		return new ModelAndView("/report/pa/viewOfficeProveInfo", modelMap);
	}

	/**
	 * 报表中心--在职证明信息，查看页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOfficeProveList")
	public ModelAndView viewOfficeProveList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("EMPID", request.getParameter("seach_EMPID"));
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		modelMap.put("pageNum",
				request.getParameter("pageNum") != null ? request
						.getParameter("pageNum") : "1");
		modelMap.put("numPerPage",
				request.getParameter("numPerPage") != null ? request
						.getParameter("numPerPage") : "10");

		modelMap.put("empOfficeProveList", this.paReportSer
				.getOfficeProveList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paReportSer
				.getOfficeProveListCnt(request));

		return new ModelAndView("/report/pa/viewOfficeProveList", modelMap);
	}

	/**
	 * 报表中心--在职证明，导出Excel用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOfficeProveExcel")
	public ModelAndView viewOfficeProveExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap linkMap = (LinkedHashMap) hrReportSer.getSysdate(request);
		modelMap.put("YEAR", linkMap.get("YEA").toString());
		modelMap.put("MONTH", linkMap.get("MON").toString());
		modelMap.put("DDATE", linkMap.get("DAT").toString());
		modelMap.put("DATE", linkMap.get("DDATE").toString());
		modelMap.put("COMPANY_NAME", admin.getCpnyName());

		modelMap.put("empOfficeProveList", this.paReportSer
				.getOfficeProveList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paReportSer
				.getOfficeProveListCnt(request));

		return new ModelAndView("/report/pa/viewOfficeProveExcel", modelMap);
	}

	// --------2013-09-20-----------------工资查看------------------

	/**
	 * 工资查看--特殊值查看页面，查看页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaInfoByItemList")
	public ModelAndView viewPaInfoByItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 设置公司、项目、工资月、工资发放日信息搜索条件
		modelMap = this.getCompanyParam(request, modelMap);

		modelMap.put("pageNum",
				request.getParameter("pageNum") != null ? request
						.getParameter("pageNum") : "1");
		modelMap.put("numPerPage",
				request.getParameter("numPerPage") != null ? request
						.getParameter("numPerPage") : "10");

		modelMap.put("paInfoByItemList", this.paReportSer
				.getPaInfoByItemList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paReportSer
				.getPaInfoByItemListCnt(request));

		return new ModelAndView("/report/pa/viewPaInfoByItemList", modelMap);
	}

	/**
	 * 根据页面传入的request收集或者设置国家(OPERATION_NO)、大分类(CONTROL1_ID)、中分类(CONTROL2_ID)、
	 * 公司信息(CPNY_ID) 设置国家、大分类、中分类、公司信息搜索条件
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelMap getCompanyParam(HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		List getPaMonthList = new ArrayList();
		List getPaItemInfoList = new ArrayList();
		// 公司查找薪资项目、薪资月份
		getPaMonthList = this.ajaxSer.getPaMonthList(request);
		getPaItemInfoList = this.ajaxSer.getPaItemInfoList(request);
		// 薪资月份查找薪资发放日
		List getPaGiveDateList = this.ajaxSer.getPaGiveDateList(request);
		// 公司找部门
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		List deptInfoTreeList = this.ajaxSer.getDeptInfoTree(paramMap);

		List companyList = this.ajaxSer.getCompanyInfoList(request);
		modelMap.put("companyList", companyList);
		modelMap.put("getPaMonthList", getPaMonthList);
		modelMap.put("getPaItemInfoList", getPaItemInfoList);
		modelMap.put("getPaGiveDateList", getPaGiveDateList);
		modelMap.put("deptInfoTreeList", deptInfoTreeList);

		modelMap.put("CPNY_ID",
				request.getParameter("seach_CPNY_ID") != null ? request
						.getParameter("seach_CPNY_ID").toString() : "");
		modelMap.put("PA_MONTH",
				request.getParameter("seach_PA_MONTH") != null ? request
						.getParameter("seach_PA_MONTH").toString() : "");
		modelMap.put("GIVE_DATE",
				request.getParameter("seach_GIVE_DATE") != null ? request
						.getParameter("seach_GIVE_DATE").toString() : "");
		modelMap.put("DEPT_NO",
				request.getParameter("seach_DEPT_NO") != null ? request
						.getParameter("seach_DEPT_NO").toString() : "");
		modelMap.put("DEPT_NAME",
				request.getParameter("seach_DEPT_NAME") != null ? request
						.getParameter("seach_DEPT_NAME").toString() : "");
		modelMap.put("ITEM_NO",
				request.getParameter("seach_ITEM_NO") != null ? request
						.getParameter("seach_ITEM_NO").toString() : "");
		modelMap.put("EMPID",
				request.getParameter("seach_EMPID") != null ? request
						.getParameter("seach_EMPID").toString() : "");

		return modelMap;
	}

	/**
	 * 工资查看--特殊值查看页面，导出Excel用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaInfoByItemExcel")
	public ModelAndView viewPaInfoByItemExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("paInfoByItemList", this.paReportSer
				.getPaInfoByItemExcelList(request));

		return new ModelAndView("/report/pa/viewPaInfoByItemExcel", modelMap);
	}

	/**
	 * 薪资查看--年工资，查看页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaInfoByYearList")
	public ModelAndView viewPaInfoByYearList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");
		if (request.getParameter("seach_PA_YEAR") == null
				|| "".equals(request.getParameter("seach_PA_YEAR").toString())) {
			modelMap.put("seach_PA_YEAR", b[0].trim().toString());
		} else {
			modelMap.put("seach_PA_YEAR", request.getParameter("seach_PA_YEAR")
					.toString());
		}
		modelMap.put("seach_CPNY_ID", request.getParameter("seach_CPNY_ID"));
		modelMap.put("seach_DEPT_NO", request.getParameter("seach_DEPT_NO"));
		modelMap
				.put("seach_DEPT_NAME", request.getParameter("seach_DEPT_NAME"));
		modelMap.put("seach_EMPID", request.getParameter("seach_EMPID"));

		modelMap.put("pageNum",
				request.getParameter("pageNum") != null ? request
						.getParameter("pageNum") : "1");
		modelMap.put("numPerPage",
				request.getParameter("numPerPage") != null ? request
						.getParameter("numPerPage") : "10");

		modelMap.put("companyList", this.ajaxSer.getCompanyInfoList(request));
		modelMap.put("paInfoByYearList", this.paReportSer
				.getPaInfoByYearList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paReportSer
				.getPaInfoByYearListCnt(request));

		return new ModelAndView("/report/pa/viewPaInfoByYearList", modelMap);
	}

	/**
	 * 薪资查看--年工资，导出Excel用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaInfoByYearExcel")
	public ModelAndView viewPaInfoByYearExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("PA_YEAR", request.getParameter("seach_PA_YEAR")
				.toString());
		modelMap.put("paInfoByYearList", this.paReportSer
				.getPaInfoByYearList(request));

		return new ModelAndView("/report/pa/viewPaInfoByYearExcel", modelMap);
	}

	/**
	 * 工资查看--员工工资转账信息（按月份查询）(query the pa transer info of employee by
	 * monthed)，查看页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaTranserTwoList")
	public ModelAndView viewPaTranserTwoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");
		if (request.getParameter("seach_YEAR_pa0108") == null
				|| "".equals(request.getParameter("seach_YEAR_pa0108")
						.toString())) {
			modelMap.put("YEAR", b[0].trim().toString());
		} else {
			modelMap.put("YEAR", request.getParameter("seach_YEAR_pa0108")
					.toString());
		}
		if (request.getParameter("seach_MONTH_pa0108") == null
				|| "".equals(request.getParameter("seach_MONTH_pa0108")
						.toString())) {
			modelMap.put("MONTH", b[1].trim().toString());
		} else {
			modelMap.put("MONTH", request.getParameter("seach_MONTH_pa0108")
					.toString());
		}

		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("EMPID", request.getParameter("seach_EMPID"));
		modelMap.put("GIVE_DATE", request
				.getParameter("seach_GIVE_DATE_pa0108"));
		modelMap.put("pageNum",
				request.getParameter("pageNum") != null ? request
						.getParameter("pageNum") : "1");
		modelMap.put("numPerPage",
				request.getParameter("numPerPage") != null ? request
						.getParameter("numPerPage") : "10");

		modelMap.put("paTranserList", this.paReportSer
				.getPaTranserList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paReportSer
				.getPaTranserListCnt(request));

		return new ModelAndView("/report/pa/viewPaTranserTwoList", modelMap);
	}

	/**
	 * 工资查看--员工工资转账信息（按月份查询）(query the pa transer info of employee by
	 * monthed)，导出Excel用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaTranserTwoExcel")
	public ModelAndView viewPaTranserTwoExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("YEAR", request.getParameter("seach_YEAR_pa0108"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH_pa0108"));

		modelMap.put("paTranserList", this.paReportSer
				.getPaTranserExcelList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paReportSer
				.getPaTranserListCnt(request));

		return new ModelAndView("/report/pa/viewPaTranserTwoExcel", modelMap);
	}

	/**
	 * 薪资查看--在职证明，查看页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpProveList")
	public ModelAndView viewEmpProveList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String ddate = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ddate = sdf.format(dt);
		String b[] = ddate.split("-");
		if (request.getParameter("seach_YEAR_pa0109") == null
				|| "".equals(request.getParameter("seach_YEAR_pa0109")
						.toString())) {
			modelMap.put("YEAR", b[0].trim().toString());
		} else {
			modelMap.put("YEAR", request.getParameter("seach_YEAR_pa0109")
					.toString());
		}
		if (request.getParameter("seach_MONTH_pa0109") == null
				|| "".equals(request.getParameter("seach_MONTH_pa0109")
						.toString())) {
			modelMap.put("MONTH", b[1].trim().toString());
		} else {
			modelMap.put("MONTH", request.getParameter("seach_MONTH_pa0109")
					.toString());
		}

		modelMap.put("GIVE_DATE", request
				.getParameter("seach_GIVE_DATE_pa0109"));

		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("EMPID", request.getParameter("seach_EMPID"));

		modelMap.put("pageNum",
				request.getParameter("pageNum") != null ? request
						.getParameter("pageNum") : "1");
		modelMap.put("numPerPage",
				request.getParameter("numPerPage") != null ? request
						.getParameter("numPerPage") : "10");

		modelMap.put("empProveList", this.paReportSer
				.getOfficeProveList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paReportSer
				.getOfficeProveListCnt(request));

		return new ModelAndView("/report/pa/viewEmpProveList", modelMap);
	}

	/**
	 * 薪资查看--在职证明，导出Excel用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEmpProveExcel")
	public ModelAndView viewEmpProveExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap linkMap = (LinkedHashMap) hrReportSer.getSysdate(request);
		modelMap.put("YEAR", linkMap.get("YEA").toString());
		modelMap.put("MONTH", linkMap.get("MON").toString());
		modelMap.put("DDATE", linkMap.get("DAT").toString());
		modelMap.put("DATE", linkMap.get("DDATE").toString());
		modelMap.put("COMPANY_NAME", admin.getCpnyName());

		modelMap.put("empOfficeProveList", this.paReportSer
				.getOfficeProveList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paReportSer
				.getOfficeProveListCnt(request));

		return new ModelAndView("/report/pa/viewEmpProveExcel", modelMap);
	}

	/*
	 * 工资明细 TXT
	 */

	@RequestMapping(value = "/exportPayDetailTxt")
	public ModelAndView exportPayDetailTxt(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/report/pa/exportPayDetailTxt", modelMap);
	}
	/*
	 * 工资模块中夜班津贴
	 */
	
	@RequestMapping(value = "/exportArDetailAllowance")
	public ModelAndView exportArDetailAllowance(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paPayScheduleList = this.paPayScheduleSer
		.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/report/pa/exportArDetailAllowance", modelMap);
	}

	/**
	 * 申报个人所得税(员工新入社及退社(工资))
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewIndividualIncomePersonEO")
	public ModelAndView viewIndividualIncomePersonEO(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);
		return new ModelAndView("/report/ar/viewIndividualIncomePersonEO",
				modelMap);
	}

}
