package com.ait.hrm.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.evs.service.EvsManageSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.RecruitManageSer;
import com.ait.hrm.service.TransactionViewSer;
import com.ait.hrm.service.TransferOrderSer;
import com.ait.is.service.CompanyMaintainSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.SendEmailSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.test.FtpUploadFileSample;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;
import com.ait.web.util.uploadpicture.uploadpic;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: EmpInfoCtroller.java
 * @Create date: Jan 16, 2012 10:44:58 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/hrm/empinfo")
@SuppressWarnings("unchecked")
public class EmpInfoCtroller {

	Logger logger = Logger.getLogger(EmpInfoCtroller.class);
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private CompanyMaintainSer manageSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	InfoApplySer infoApplySer;
	@Autowired
	private EvsManageSer evsManageSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private AuthorityUtil authorityUtil;
	@Autowired
	private TransactionViewSer transactionViewSer;
	@Autowired
	private TransferOrderSer transferOrderSer;
	@Autowired
	private InfoApplyLeaveSer infoApplySerOt;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	@Autowired
	private SendEmailSer sendEmailSer;
	@Autowired
	private RecruitManageSer recruitManageSer;

	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	@RequestMapping(value = "/searchEmp", method = RequestMethod.GET)
	public ModelAndView getSearchEmp(HttpServletRequest request)
			throws Exception {
		return new ModelAndView("/hrm/empinfo/searchEmp");
	}

	/**
	 * 人事系统首页面
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewHrMain")
	public ModelAndView viewPaMain(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int isSuperUser = authorityUtil.isSuperUser(admin.getPersonId());
		int isSuperHrUser = authorityUtil.isSuperHrUser(admin.getPersonId());
		int isHrUser = authorityUtil.isHrUser(admin.getPersonId());
		int isInformationUser = authorityUtil.isInformationUser(admin.getPersonId());
		int isRecruitUser = authorityUtil.isRecruitUser(admin.getPersonId());
		modelMap.put("isSuperUser", isSuperUser);
		modelMap.put("isSuperHrUser", isSuperHrUser);
		modelMap.put("isHrUser", isHrUser);
		modelMap.put("isInformationUser", isInformationUser);
		modelMap.put("isRecruitUser", isRecruitUser);
		String person_id = admin.getPersonId();
		modelMap.put("PERSON_ID", person_id);
		return new ModelAndView("/hrm/empinfo/viewHrMain", modelMap);

	}

	@RequestMapping(value = "/getEmpList")
	@ResponseBody
	public Map getEmpList(HttpServletRequest request) throws Exception {
		Map info = empInfoSer.getEmpList(request);
		Map map = new HashMap();
		map.put("Rows", info.get("list"));
		map.put("Total", info.get("count"));
		return map;
	}

	@RequestMapping(value = "/getExpInsideList")
	@ResponseBody
	public Map getExpInsideList(HttpServletRequest request) throws Exception {
		logger.info("getExpInsideList.start...");
		Map temp = empInfoSer.getExpInsideForGrid(request);// 发令事项
		return temp;
	}

	@RequestMapping(value = "/getResignationList")
	@ResponseBody
	public Map getResignationList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getResignationForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/getEvalList")
	@ResponseBody
	public Map getEvalList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getEvalForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/getEvaluateList")
	@ResponseBody
	public Map getEvaluateList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getEvaluateForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/getPunishMentList")
	@ResponseBody
	public Map getPunishMentList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getPunishMentForGrid(request);// 惩戒
		return temp;
	}

	@RequestMapping(value = "/getRewardList")
	@ResponseBody
	public Map getRewardList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getRewardForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/getDispatchList")
	@ResponseBody
	public Map getDispatchList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getDispatchForGrid(request);// 派遣
		return temp;
	}

	@RequestMapping(value = "/getPluralityList")
	@ResponseBody
	public Map getPluralityList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getPluralityForGrid(request);// 兼职
		return temp;
	}

	@RequestMapping(value = "/getSuspendList")
	@ResponseBody
	public Map getSuspendList(HttpServletRequest request) throws Exception {
		return empInfoSer.getSuspendForGrid(request);
	}

	@RequestMapping(value = "/getEduList")
	@ResponseBody
	public Map getEduList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getEduForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/getFamilyInfoList")
	@ResponseBody
	public Map getFamilyInfoList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getFamilyInfoForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/getSocietyRelationList")
	@ResponseBody
	public Map getSocietyRelationList(HttpServletRequest request)
			throws Exception {
		Map temp = empInfoSer.getSocietyRelationForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/updateFamilyInfoOrsocietyRelationGrid")
	public ModelAndView updateFamilyInfoOrsocietyRelationGrid(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		logger.info("updateFamilyInfoGrid.start...");
		Map degreeCodeMap = this.empInfoSer.getDegreeCodeForSelect(request);
		Map relationalTypeCodeMap = this.empInfoSer
				.getRelationalTypeCodeForSelect(request);
		Map otherRelationMap = this.empInfoSer
				.getOtherRelationForSelect(request);
		Map liveTogetherFlagMap = this.empInfoSer
				.getTogetherFlagForSelect(request);
		request.setAttribute("basicInfo", empInfoSer.getBasicInfo(request));
		request.setAttribute("degreeCodeMap", JsonUtil
				.writeInternal(degreeCodeMap));
		request.setAttribute("relationalTypeCodeMap", JsonUtil
				.writeInternal(relationalTypeCodeMap));
		request.setAttribute("otherRelationMap", JsonUtil
				.writeInternal(otherRelationMap));
		request.setAttribute("liveTogetherFlagMap", JsonUtil
				.writeInternal(liveTogetherFlagMap));
		// String familyInfoJson=request.getParameter("#familyInfoGrid");
		// String
		// societyRelationJson=request.getParameter("#societyRelationGrid");
		// String
		// famBackInfo=empInfoSer.updateFamilyInfoGrid(JsonUtil.getUpdateList(familyInfoJson));
		// String
		// socBackInfo=empInfoSer.updateSocietyRelationGrid(JsonUtil.getUpdateList(societyRelationJson));
		request.getRequestDispatcher(
				"/WEB-INF/view/hrm/empinfo/viewRelation.jsp").forward(request,
				response);
		return null;
	}

	@RequestMapping(value = "/updateSocietyRelationGrid")
	@ResponseBody
	public String updateSocietyRelationGrid(HttpServletRequest request)
			throws Exception {
		logger.info("updateSocietyRelationGrid.start...");
		String temp = empInfoSer.updateSocietyRelationGrid(JsonUtil
				.getUpdateList(request));
		return temp;
	}

	@RequestMapping(value = "/getHealthInfoList")
	@ResponseBody
	public Map getHealthInfoList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getHealthInfoForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/updateHealthInfoGrid")
	public ModelAndView updateHealthInfoGrid(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		logger.info("updateHealthInfoGrid.start...");
		Map checkWhetherMap = this.empInfoSer.getCheckWhetherForSelect(request);
		Map checkResultMap = this.empInfoSer.getCheckResultForSelect(request);
		request.setAttribute("checkWhetherMap", JsonUtil
				.writeInternal(checkWhetherMap));
		request.setAttribute("checkResultMap", JsonUtil
				.writeInternal(checkResultMap));
		request.setAttribute("basicInfo", empInfoSer.getBasicInfo(request));
		// String healthInfoJson=request.getParameter("#healthInfoGrid");
		// String
		// famBackInfo=empInfoSer.updateHealthInfoGrid(JsonUtil.getUpdateList(healthInfoJson));
		request
				.getRequestDispatcher(
						"/WEB-INF/view/hrm/empinfo/viewHealth.jsp").forward(
						request, response);
		return null;
	}

	@RequestMapping(value = "/getExperienceInfoList")
	@ResponseBody
	public Map getExperienceInfoList(HttpServletRequest request)
			throws Exception {
		Map temp = empInfoSer.getExperienceInfoForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/getQualificationInfoList")
	@ResponseBody
	public Map getQualificationInfoList(HttpServletRequest request)
			throws Exception {
		Map temp = empInfoSer.getQualificationInfoForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/updateExperienceInfoGrid")
	public ModelAndView updateExperienceInfoGrid(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		logger.info("updateHealthInfoGrid.start...");
		request.setAttribute("basicInfo", empInfoSer.getBasicInfo(request));
		// String
		// experienceInfoJson=request.getParameter("#experienceInfoGrid");
		// String
		// famBackInfo=empInfoSer.updateExperienceInfoGrid(JsonUtil.getUpdateList(experienceInfoJson));
		request.getRequestDispatcher(
				"/WEB-INF/view/hrm/empinfo/viewWorkInfo.jsp").forward(request,
				response);
		return null;
	}

	@RequestMapping(value = "/getAppendInfoList")
	@ResponseBody
	public Map getAppendInfoList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getAppendInfoForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/updateFappendInfoGrid")
	public ModelAndView updateFappendInfoGrid(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		logger.info("updateHealthInfoGrid.start...");
		request.setAttribute("basicInfo", empInfoSer.getBasicInfo(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		admin.getAdminID();
		// String fappendInfoGridJson=request.getParameter("#fappendInfoGrid");
		// String
		// famBackInfo=empInfoSer.updateFappendInfoGrid(JsonUtil.getUpdateList(fappendInfoGridJson),request);
		request.getRequestDispatcher(
				"/WEB-INF/view/hrm/empinfo/viewAppendInfo.jsp").forward(
				request, response);
		return null;
	}

	@RequestMapping(value = "/getLanuageInfoList")
	@ResponseBody
	public Map getLanuageInfoList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getLanuageInfoForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/getITLevelInfoList")
	@ResponseBody
	public Map getITLevelInfoList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getITLevelInfoForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/updateQualificationInfoOrLanuageInfoGrid")
	public ModelAndView updateQualificationInfoOrLanuageInfoGrid(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		logger.info("updateHealthInfoGrid.start...");
		Map languageLevelCodeMap = this.empInfoSer
				.getLanguageLevelCodeForSelect(request);
		Map languageExamCodeMap = this.empInfoSer
				.getLanguageExamCodeForSelect(request);
		Map languageTypeCodeMap = this.empInfoSer
				.getLanguageTypeCodeForSelect(request);
		Map qualNameCodeMap = this.empInfoSer.getQualNameCodeForSelect(request);
		request.setAttribute("languageLevelCodeMap", JsonUtil
				.writeInternal(languageLevelCodeMap));
		request.setAttribute("languageExamCodeMap", JsonUtil
				.writeInternal(languageExamCodeMap));
		request.setAttribute("languageTypeCodeMap", JsonUtil
				.writeInternal(languageTypeCodeMap));
		request.setAttribute("qualNameCodeMap", JsonUtil
				.writeInternal(qualNameCodeMap));
		request.setAttribute("basicInfo", empInfoSer.getBasicInfo(request));
		// String
		// qualificationInfoJson=request.getParameter("#qualificationInfoGrid");
		// String lanuageInfoJson=request.getParameter("#lanuageInfoGrid");
		// String
		// famBackInfo=empInfoSer.updateQualificationInfoGrid(JsonUtil.getUpdateList(qualificationInfoJson));
		// String
		// socBackInfo=empInfoSer.updateLanuageInfoGrid(JsonUtil.getUpdateList(lanuageInfoJson));
		request.getRequestDispatcher(
				"/WEB-INF/view/hrm/empinfo/viewCompetence.jsp").forward(
				request, response);
		return null;
	}

	/**
	 * 工资信息
	 * 
	 * @param ac
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAccountsInfo")
	public ModelAndView getAccountsInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map bankNameCodeMap = this.empInfoSer.getBankNameCodeForSelect(request);
		modelMap
				.put("bankNameCodeMap", JsonUtil.writeInternal(bankNameCodeMap));
		modelMap.put("basicInfo", empInfoSer.getBasicInfo(request));
		return new ModelAndView("/hrm/empinfo/viewAccountsInfo", modelMap);
	}

	@RequestMapping(value = "/getSinfoList")
	@ResponseBody
	public Map getSinfoList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getSinfoForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/getPaEmpInfoList")
	@ResponseBody
	public Map getPaEmpInfoList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getPaEmpInfoForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/updatePaEmpInfoGrid")
	public ModelAndView updatePaEmpInfoGrid(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		logger.info("updateHealthInfoGrid.start...");
		Map bankNameCodeMap = this.empInfoSer.getBankNameCodeForSelect(request);
		request.setAttribute("bankNameCodeMap", JsonUtil
				.writeInternal(bankNameCodeMap));
		request.setAttribute("basicInfo", empInfoSer.getBasicInfo(request));
		// String paEmpInfoJson=request.getParameter("#paEmpInfoGrid");
		// String
		// famBackInfo=empInfoSer.updatePaEmpInfoGrid(JsonUtil.getUpdateList(paEmpInfoJson),request);
		request.getRequestDispatcher(
				"/WEB-INF/view/hrm/empinfo/viewAccountsInfo.jsp").forward(
				request, response);
		return null;
	}

	/**
	 * 担当业务
	 * 
	 * @param ac
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewActBusiness")
	public ModelAndView getActBusiness(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("basicInfo", empInfoSer.getBasicInfo(request));
		return new ModelAndView("/hrm/empinfo/viewActBusiness", modelMap);
	}

	@RequestMapping(value = "/getBizlistList")
	@ResponseBody
	public Map getBizlistList(HttpServletRequest request) throws Exception {
		Map temp = empInfoSer.getBizlistForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/getContractList")
	@ResponseBody
	public Map getContractList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getContractList.start..." + request.getParameter("empid"));
		// Map info = empInfoSer.getContractList(request);
		Map map = new HashMap();
		// map.put("Rows", info.get("list"));
		// map.put("Total", info.get("count"));
		return map;
	}

	/**
	 * ESS员工基础信息(Staff foundation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonalInfoForEss")
	public ModelAndView getPersonalInfoForEss(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		getPersonalInfo(request, response, modelMap);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/ess/empinfo/essViewPersonalInfo", modelMap);
	}

	/**
	 * 员工基础信息(Staff foundation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewPersonalInfo")
	public ModelAndView getPersonalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		logger.info("viewPersonalInfo.start...");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		int isSuperUser = authorityUtil.isSuperUser(admin.getPersonId());
		int isSuperHrUser = authorityUtil.isSuperHrUser(admin.getPersonId());
		int isHrUser = authorityUtil.isHrUser(admin.getPersonId());
		modelMap.put("isSuperUser", isSuperUser);
		modelMap.put("isSuperHrUser", isSuperHrUser);
		modelMap.put("isHrUser", isHrUser);
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getPersonalInfoByPid(request);
		String personId = request.getParameter("PERSON_ID");
		String adminID = admin.getAdminID();
		
		LinkedHashMap param = null;
		if(linkMap!=null && linkMap.size() > 0){
			param = (LinkedHashMap)linkMap.get(0);
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "HR_RESUME");
			if(personId!=null && !"".equals(personId)){
				fileParam.put("APPLY_NO", personId);
			} else {
				fileParam.put("APPLY_NO", adminID);
			}
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			linkMap.put("fileList", fileList);
		}
		
		String tabsSelected = request.getSession().getAttribute("TABS_SELECTED") == null ? "0" : request.getSession().getAttribute("TABS_SELECTED").toString();

		modelMap.put("personInfo", linkMap);
		// SST法人的最终学校信息
		LinkedHashMap finaEdu = (LinkedHashMap) empInfoSer.getfinaEdu(request);
		modelMap.put("finaEdu", finaEdu);
		/*
		 * ==================================基本信息菜单==============================
		 * ==========
		 */
		// 紧急联系人
		modelMap.put("hrEmergencyAddressList", empInfoSer.gethrEmergencyAddressList(request));
		// 地址类型
		modelMap.put("hrAddressMattersList", empInfoSer.gethrAddressMattersLists(request));
		// 家庭关系
		modelMap.put("hrFamilyList", empInfoSer.gethrFamilyList(request));
		// 三级菜单
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2540"));
		modelMap.put("menuThirdList", this.empInfoSer.getMenuThirdListList("",request));

		/*
		 * ==================================工作信息菜单==============================
		 * ==========
		 */
		modelMap.put("toolbartradeunion", toolMenuSer.getToolMenuForNo(request,"14013949"));
		// 发令事项
		modelMap.put("hrStartPointList", empInfoSer.getStartPointList(request));
		// 合同
		modelMap.put("contracList", empInfoSer.getContractList(request));
		// 经历事项
		modelMap.put("hrExperiencePointList", empInfoSer.getExperiencePointList(request));
		// viewPregnantManagement事项
		modelMap.put("hrPregnantManagementList", empInfoSer.getPregnantManagementList(request));

		/*
		 * ==================================能力信息菜单==============================
		 * ==========
		 */
		// 学历事项
		modelMap.put("hrEducationMatterList", empInfoSer.viewEducationMatter(request));
		// 资格事项viewBidMatter
		modelMap.put("viewBidMatter", empInfoSer.viewBidMatter(request));
		// 外语能力
		modelMap.put("viewForeignLanguage", empInfoSer.viewForeignLanguage(request));
		// 特记事项
		modelMap.put("viewSpecialMatter", empInfoSer.viewSpecialMatter(request));
		//培训事项
		//modelMap.put("viewTraining", empInfoSer.viewTrainingBasic(request));
		/*
		 * ==================================奖惩信息菜单==============================
		 * ==========
		 */
		// 表彰事项
		modelMap.put("viewRecognition", empInfoSer.viewRecognition(request));
		// 惩戒事项
		modelMap.put("viewPunishment", empInfoSer.viewPunishment(request));
		
		modelMap.put("accountInfo", empInfoSer.getAccountInfo(request));
		// 培训事项
		modelMap.put("viewTrain", empInfoSer.viewTrain(request));
		// 评价事项
		 modelMap.put("viewEvaInformation",empInfoSer.viewEvaInformation(request));

		/*
		 * ==================================SST护照签证信息菜单==========================
		 * ==============
		 */
		String cpnyid = admin.getCpnyId();
		modelMap.put("viewPassportPerson", empInfoSer.viewPassportPerson(request));
		modelMap.put("viewPassportFamily", empInfoSer.viewPassportPerson(request));
		// }

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		} else {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		//List objectList = this.empInfoSer.viewEvaluateInfo(request);
		List objectList = this.evsManageSer.viewEvsInfoList(paramMap,"viewEvsResultPerson");
		modelMap.put("objectList", objectList);

		modelMap.put("tabsSelected", tabsSelected);
		modelMap.put("CPNY_ID", cpnyid);
		modelMap.put("language", Messages.getLanguage(request));
		modelMap.put("KEY", StringUtil.checkNull(request.getParameter("KEY")));
		request.getSession().removeAttribute("TABS_SELECTED");
		modelMap.put("isEssSystem",
				request.getParameter("isEssSystem") != null ? (String) request
						.getParameter("isEssSystem") : "0");
		return new ModelAndView("/hrm/empinfo/viewPersonalInfo", modelMap);
	}
 
	/**
	 * 临时职员工基础信息(Staff foundation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTempPersonalInfo")
	public ModelAndView getTempPersonalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		logger.info("viewTempPersonalInfo.start...");
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		String tabsSelected = request.getSession()
				.getAttribute("TABS_SELECTED") == null ? "0" : request
				.getSession().getAttribute("TABS_SELECTED").toString();

		modelMap.put("personInfo", linkMap);
		// 毕业学校
		modelMap
				.put("educationList", this.empInfoSer.getEducationList(request));

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2540"));

		modelMap.put("menuThirdList", this.empInfoSer.getMenuThirdListList("",
				request));
		// 工会信息
		modelMap.put("toolbartradeunion", toolMenuSer.getToolMenuForNo(request,
				"123194"));
		modelMap.put("tradeUnionList", empInfoSer.getTradeunionList(request));
		// 黑色档案信息
		modelMap.put("toolBadArchives", toolMenuSer.getToolMenuForNo(request,
				"2550"));
		modelMap.put("badArchives", empInfoSer.getBadArchivesList(request));
		// 辅助信息
		modelMap.put("toolAssist", toolMenuSer.getToolMenuForNo(request,
				"216001"));
		modelMap.put("assistList", empInfoSer.getAssistList(request));
		// 评价信息
		modelMap.put("toolbarInfopingjia", toolMenuSer.getToolMenuForNo(
				request, "2542"));
		modelMap.put("EvsInfo", empInfoSer.getEvsInfoList(request));
		// 外国语信息
		modelMap.put("toolbarInfozige", toolMenuSer.getToolMenuForNo(request,
				"123192"));
		modelMap.put("languageLevelList", empInfoSer
				.getLanguageLevelList(request));
		// 社会关系
		modelMap.put("toolbarInfoshehui", toolMenuSer.getToolMenuForNo(request,
				"123191"));
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		// 家庭关系
		modelMap.put("toolbarInfoHome", toolMenuSer.getToolMenuForNo(request,
				"123190"));
		modelMap.put("homeRelationList", empInfoSer
				.getHomeRelationList(request));

		// 获取兼卖产品类型
		List productList = empInfoSer.getEmpProductList(request);
		modelMap.put("productList", productList);
		// 残疾信息
		modelMap.put("toolbarInfodisability", toolMenuSer.getToolMenuForNo(
				request, "123193"));
		modelMap.put("disabilityinfoList", empInfoSer
				.getDisabilityinfoList(request));

		// 工作经历
		modelMap.put("toolbarInfogongzuo", toolMenuSer.getToolMenuForNo(
				request, "2551"));
		modelMap.put("workExperienceList", empInfoSer
				.getWorkExperienceList(request));
		// 培训
		modelMap.put("toolbarInfopeixun", toolMenuSer.getToolMenuForNo(request,
				"2545"));
		modelMap.put("trainingInfoList", empInfoSer
				.getTrainingInfoList(request));
		// 合同
		modelMap.put("toolbarInfohetong", toolMenuSer.getToolMenuForNo(request,
				"2491"));
		modelMap.put("contracList", empInfoSer.getContractList(request));

		// 发令信息
		modelMap.put("toolbarInfofl", toolMenuSer.getToolMenuForNo(request,
				"2541"));
		modelMap.put("assignmentList", empInfoSer.getAssignmentList(request));

		// 派遣地
		modelMap.put("toolbarInfofl", toolMenuSer.getToolMenuForNo(request,
				"2491"));
		modelMap.put("expInsideList", empInfoSer.getExpInsideList(request));

		// 资格信息
		modelMap.put("toolbarInfozige", toolMenuSer.getToolMenuForNo(request,
				"2554"));
		modelMap.put("qualificationList", empInfoSer
				.getQualificationList(request));

		modelMap.put("tabsSelected", tabsSelected);
		request.getSession().removeAttribute("TABS_SELECTED");
		modelMap.put("isEssSystem",
				request.getParameter("isEssSystem") != null ? (String) request
						.getParameter("isEssSystem") : "0");
		return new ModelAndView("/hrm/empinfo/viewTempPersonalInfo", modelMap);
	}

	/**
	 * 转到添加学校信息(Turn to add school information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEducationInfo")
	public ModelAndView viewEducationInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList", empInfoSer.getCodeList("741", request));
		modelMap.put("degreeList", empInfoSer.getCodeList("1665", request));
		// modelMap.put("particularDegreeList",empInfoSer.getCodeList("123255",request));
		modelMap.put("subjectClassifyList", empInfoSer.getCodeList("123412",
				request));// 专业分类
		modelMap.put("siteProvinceList", empInfoSer
				.getCodeList("4602", request));// 所在地省
		modelMap.put("FINALNUM", empInfoSer.getFinalNum(request));// 统计用户是否有最终学历0为没有

		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewEducationInfo", modelMap);
	}

	/**
	 * 添加毕业学校信息(add school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEducationInfo")
	@ResponseBody
	public Map addEducationInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.addEduactionInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}

		return map;
	}

	/**
	 * 转到删除毕业学校信息(Turn to delete school information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEducation")
	public ModelAndView deleteEducation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap
				.put("educationList", this.empInfoSer.getEducationList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/deleteEducation", modelMap);
	}

	/**
	 * 删除毕业学校信息(delete school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEducationInfo")
	@ResponseBody
	public Map deleteEducation(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteEduactionInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 转到个人信息(Turn to add school and personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEduPerInfo")
	public ModelAndView viewEduPerInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		List corpList = manageSer.getIsCorpInfo(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", linkMap);

		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("photoId", "viewEduPerInfo");
		modelMap.put("corpList", corpList);
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		// 获取兼卖产品类型
		List productList = empInfoSer.getEmpProductList(request);
		modelMap.put("productList", productList);
		modelMap.put("isEssSystem",
				request.getParameter("isEssSystem") != null ? (String) request
						.getParameter("isEssSystem") : "0");

		return new ModelAndView("/hrm/empinfo/viewEduPerInfo", modelMap);
	}

	/**
	 * 基本信息页面修改employee表的信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpInfo")
	public ModelAndView viewEmpInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.viewEmpInfo(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", linkMap);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("totalWorkAge", StringUtil.checkNull(request
				.getParameter("totalWorkAge")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewEmpInfo", modelMap);
	}

	/**
	 * 复制地址功能
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/copyAddress")
	@ResponseBody
	public Map copyAddress(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 用户详细信息
		String str = empInfoSer.copyAddress(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		map.put("addressContent", str);
		return map;
	}

	/**
	 * 发令区分和发令原因联动
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/codeReason")
	@ResponseBody
	public Map codeReason(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeReasonList", empInfoSer.codeReason(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 查询部门
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDepartment")
	@ResponseBody
	public Map queryDepartment(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queryDepartment", empInfoSer.queryDepartment(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 搜索标题名字下拉菜单
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchTitlename")
	@ResponseBody
	public Map searchTitlename(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchTitlename", empInfoSer.searchTitlename(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 基本信息页面修改employee表的信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewHrPersonalInfo")
	public ModelAndView viewHrPersonalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		// LinkedHashMap linkMap =
		// (LinkedHashMap)empInfoSer.viewEmpInfo(request);
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.viewHrPersonalInfo(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", linkMap);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewHrPersonalInfo", modelMap);
	}

	/**
	 * 紧急联系人单一的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSingleHrEmergencyAddress")
	public ModelAndView viewSingleHrEmergencyAddress(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.viewSingleHrEmergencyAddress(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", linkMap);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewSingleHrEmergencyAddress",
				modelMap);
	}

	/**
	 * 地址类型单一的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSingleAddressMatters")
	public ModelAndView viewSingleAddressMatters(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.viewSingleAddressMatters(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", linkMap);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("address_no", request.getParameter("ADDRESS_NO"));// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewSingleAddressMatters",
				modelMap);
	}

	/**
	 * 家庭关系单一的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSingleFamily")
	public ModelAndView viewSingleFamily(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.viewSingleFamily(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", linkMap);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("family_no", request.getParameter("FAMILY_NO"));// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewSingleFamily", modelMap);
	}

	/**
	 * 经历事项的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSingleExperiencePoint")
	public ModelAndView viewSingleExperiencePoint(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.viewSingleExperiencePoint(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", linkMap);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("work_exper_no", request.getParameter("WORK_EXPER_NO"));// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewSingleExperiencePoint",
				modelMap);
	}

	/**
	 * 学历事项的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSingleEducationMatter")
	public ModelAndView viewSingleEducationMatter(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.viewSingleEducationMatter(request);
		String educ_no = StringUtil.checkNull(request.getParameter("EDUC_NO"));
		if (!"0".equals(educ_no) && !"".equals(educ_no)) {
			// 附件下载功能
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "hrEducation");
			fileParam.put("APPLY_NO", educ_no);
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);

			param.put("fileList", fileList);
		}
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", param);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("educ_no", request.getParameter("EDUC_NO"));// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewSingleEducationMatter",
				modelMap);
	}

	/**
	 * 资格事项的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSingleBidMatter")
	public ModelAndView viewSingleBidMatter(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.viewSingleBidMatter(request);
		String qual_no = StringUtil.checkNull(request.getParameter("QUAL_NO"));
		if (!"0".equals(qual_no) && !"".equals(qual_no)) {
			// 附件下载功能
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "hrQualification");
			fileParam.put("APPLY_NO", qual_no);
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList", fileList);
		}
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", param);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("qual_no", request.getParameter("QUAL_NO"));// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewSingleBidMatter", modelMap);
	}

	/**
	 * 评价信息的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSingleEvaluateInfo")
	public ModelAndView viewSingleEvaluateInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.viewSingleEvaluateInfo(request);
		String evaluate_no = StringUtil.checkNull(request
				.getParameter("EVALUATE_NO"));
		if (!"0".equals(evaluate_no) && !"".equals(evaluate_no)) {
			// 附件下载功能
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "hrEvaluateInfo");
			fileParam.put("APPLY_NO", evaluate_no);
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList", fileList);
		}
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", param);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("evaluate_no", request.getParameter("EVALUATE_NO"));// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewSingleEvaluateInfo", modelMap);
	}

	/**
	 * 外语能力的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSingleForeignLanguage")
	public ModelAndView viewSingleForeignLanguage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.viewSingleForeignLanguage1(request);
		String language_no = StringUtil.checkNull(request
				.getParameter("LANGUAGE_NO"));

		if (!"0".equals(language_no) && !"".equals(language_no)) {
			// 附件下载功能
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "hrLanguageLevel");
			fileParam.put("APPLY_NO", language_no);
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList", fileList);
		}
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", param);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("language_no", request.getParameter("LANGUAGE_NO"));// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewSingleForeignLanguage",
				modelMap);
	}

	/**
	 * 培训信息的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSingleTrainingBasic")
	public ModelAndView viewSingleTrainingBasic(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.viewSingleTrainingBasic(request);
		String train_no = StringUtil
				.checkNull(request.getParameter("TRAIN_NO"));
		if (!"0".equals(train_no) && !"".equals(train_no)) {
			// 附件下载功能
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "hrTrainification");
			fileParam.put("APPLY_NO", train_no);
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList", fileList);
		}
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", param);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("train_no", request.getParameter("TRAIN_NO"));// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewSingleTrainingBasic",
				modelMap);
	}

	/**
	 * 表彰事项的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSingleRecognition")
	public ModelAndView viewSingleRecognition(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		String reward_no = StringUtil.checkNull(request
				.getParameter("REWARD_NO"));
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.viewSingleRecognition(request);
		if (!"0".equals(reward_no) && !"".equals(reward_no)) {
			// 附件下载功能
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "hrReward");
			fileParam.put("APPLY_NO", reward_no);
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList", fileList);
		}
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", param);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("reward_no", reward_no);// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewSingleRecognition", modelMap);
	}

	/**
	 * 惩戒事项的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSinglePunishment")
	public ModelAndView viewSinglePunishment(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		String punish_no = StringUtil.checkNull(request
				.getParameter("PUNISH_NO"));
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.viewSinglePunishment(request);
		if (!"0".equals(punish_no) && !"".equals(punish_no)) {
			// 附件下载功能
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "hrPunishment");
			fileParam.put("APPLY_NO", punish_no);
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList", fileList);
		}
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", param);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("punish_no", punish_no);// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewSinglePunishment", modelMap);
	}

	/**
	 * 特记事项的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSingleSpecialMatter")
	public ModelAndView viewSingleSpecialMatter(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		String special_no = StringUtil.checkNull(request.getParameter("SPECIAL_NO"));
		LinkedHashMap param = (LinkedHashMap) empInfoSer.viewSingleSpecialMatter(request);
		if (!"0".equals(special_no) && !"".equals(special_no)) {
			// 附件下载功能
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "hrSpecialMatter");
			fileParam.put("APPLY_NO", special_no);
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList", fileList);
		}
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", param);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("special_no", special_no);// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewSingleSpecialMatter",
				modelMap);
	}

	/**
	 * 护照信息的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSinglePassportPerson")
	public ModelAndView viewSinglePassportPerson(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		String passper_no = StringUtil.checkNull(request
				.getParameter("PASSPER_NO"));
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.viewSinglePassportPerson(request);
		if (!"0".equals(passper_no) && !"".equals(passper_no)) {
			// 附件下载功能
			LinkedHashMap fileParam = new LinkedHashMap();

			fileParam.put("APPLY_TYPE", "hrPassportPerson");
			fileParam.put("APPLY_NO", passper_no);
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList", fileList);
		}
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", param);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("passper_no", passper_no);// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewSinglePassportPerson",
				modelMap);

	}

	@RequestMapping(value = "/viewSinglePassportFamily")
	public ModelAndView viewSinglePassportFamily(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		String passper_no = StringUtil.checkNull(request
				.getParameter("PASSPER_NO"));
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.viewSinglePassportPerson(request);
		if (!"0".equals(passper_no) && !"".equals(passper_no)) {
			// 附件下载功能
			LinkedHashMap fileParam = new LinkedHashMap();

			fileParam.put("APPLY_TYPE", "hrPassportFamily");
			fileParam.put("APPLY_NO", passper_no);
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList", fileList);
		}
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", param);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("passper_no", passper_no);// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewSinglePassportFamily",
				modelMap);

	}

	/**
	 * 发令的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSingleStartPoint")
	public ModelAndView viewSingleStartPoint(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List elist = empInfoSer.getStartPointList(request);
		if (elist != null && elist.size() > 0) {
			modelMap.put("expInfo", elist.get(0));
		} else {
			LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
					.viewCurrentHrInfo(request);
			modelMap.put("expInfo", linkMap);
		}
/*		List elist1 = empInfoSer.getStartPointList1(request);
		modelMap.put("expInfo1", elist1);*/
		return new ModelAndView("/hrm/empinfo/viewSingleStartPoint", modelMap);
	}

	/**
	 * 基本信息页面查询紧急联系人的信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmergencyAddress")
	public ModelAndView viewEmergencyAddress(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 紧急联系人详细信息
		List elist = empInfoSer.gethrEmergencyAddressList(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("emergencyNo", param.get("EMERGENCY_NO"));
		} else {
			modelMap.put("emergencyNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("hrEmergencyAddressList", elist);
		modelMap.put("totalcount", empInfoSer
				.gethrEmergencyAddressList_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewEmergencyAddress", modelMap);
	}

	/**
	 * 基本信息页面地址类型的信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAddressMatters")
	public ModelAndView viewAddressMatters(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 紧急联系人详细信息
		List elist = empInfoSer.viewAddressMattersList(request);

		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("addressNo", param.get("ADDRESS_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("addressNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewAddressMattersList", elist);
		modelMap.put("totalcount", empInfoSer
				.gethrviewAddressMattersList_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewAddressMatters", modelMap);
	}

	/**
	 * 基本信息页面家庭关系的信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewFamily")
	public ModelAndView viewFamily(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 紧急联系人详细信息
		List elist = empInfoSer.viewFamilyList(request);

		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("familyNo", param.get("FAMILY_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("familyNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewFamilyList", elist);
		modelMap.put("totalcount", empInfoSer
				.gethrviewAddressMattersList_count(request));
		modelMap.put("totalcount", empInfoSer.getviewFamilyList_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewFamily", modelMap);
	}

	/**
	 * 工作信息发令事项的
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewStartPoint")
	public ModelAndView viewStartPoint(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
        AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		List elist = empInfoSer.getStartPointList(request);
		modelMap.put("viewStartPointList", elist);

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.viewHrPersonalInfo(request);
		modelMap.put("personInfo", linkMap);

		if (elist != null && elist.size() > 0) {
			LinkedHashMap param = (LinkedHashMap) elist.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("PERSON_ID", param.get("PERSON_ID"));
			modelMap.put("totalcount", elist.size());
		} else {
			if(request.getAttribute("PERSON_ID")==null || request.getAttribute("PERSON_ID")==""){
				modelMap.put("PERSON_ID", admin.getAdminID());
			}else{
				modelMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
			}
			modelMap.put("totalcount", 0);
		}

		return new ModelAndView("/hrm/empinfo/viewStartPoint", modelMap);
	}

	/**
	 * 工作信息经历事项的
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewExperiencePoint")
	public ModelAndView viewExperiencePoint(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 紧急联系人详细信息
		List elist = empInfoSer.getExperiencePointList(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("workExperNo", param.get("WORK_EXPER_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("workExperNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewExperiencePointList", elist);
		modelMap.put("totalcount", elist.size());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewExperiencePoint", modelMap);
	}

	/**
	 * 能力信息学历事项的
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEducationMatter")
	public ModelAndView viewEducationMatter(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 学历详细信息
		List elist = empInfoSer.viewEducationMatter(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("educNo", param.get("EDUC_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("educNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewEducationMatter", elist);
		modelMap.put("totalcount", empInfoSer
				.getExperiencePointList_count(request));
		modelMap.put("totalcount", empInfoSer
				.viewEducationMatter_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewEducationMatter", modelMap);
	}

	/**
	 * 能力信息资格事项的
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewBidMatter")
	public ModelAndView viewBidMatter(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 证书详细信息
		List elist = empInfoSer.viewBidMatter(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("qualNo", param.get("QUAL_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("qualNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewBidMatter", elist);
		modelMap.put("totalcount", empInfoSer.viewBidMatter_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewBidMatter", modelMap);
	}

	/**
	 * 能力信息评价信息的
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluateInfo")
	public ModelAndView viewEvaluateInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 紧急联系人详细信息
		List elist = empInfoSer.viewEvaluateInfo(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("evaluateNo", param.get("EVALUATE_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("evaluateNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewEvaluateInfo", elist);
		modelMap.put("totalcount", empInfoSer.viewEvaluateInfo_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewEvaluateInfo", modelMap);
	}

	/**
	 * 能力信息外语能力的
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewForeignLanguage")
	public ModelAndView viewForeignLanguage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 紧急联系人详细信息
		List elist = empInfoSer.viewForeignLanguage(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("languageNo", param.get("LANGUAGE_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("languageNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewForeignLanguage", elist);
		modelMap.put("totalcount", empInfoSer
				.viewForeignLanguage_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewForeignLanguage", modelMap);
	}

	/**
	 * 培训信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTrainingBasic")
	public ModelAndView viewTrainingBasic(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List elist = empInfoSer.viewTrainingBasic(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("TRAIN_NO", param.get("TRAIN_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("TRAIN_NO", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewTrainingBasic", elist);
		modelMap.put("totalcount", empInfoSer.viewTrainingBasic_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewTrainingBasic", modelMap);
	}

	/**
	 * 奖惩信息的表彰事项
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewRecognition")
	public ModelAndView viewRecognition(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		List elist = empInfoSer.viewRecognition(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("rewardNo", param.get("REWARD_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("rewardNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewRecognition", elist);
		modelMap.put("totalcount", empInfoSer.viewRecognition_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		modelMap.put("REWARDNO", StringUtil.checkNull(request
				.getParameter("REWARDNO")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewRecognition", modelMap);
	}

	/**
	 * 奖惩信息的惩戒事项
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPunishment")
	public ModelAndView viewPunishment(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 紧急联系人详细信息
		List elist = empInfoSer.viewPunishment(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("punishNo", param.get("PUNISH_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("punishNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewPunishment", elist);
		modelMap.put("totalcount", empInfoSer.viewPunishment_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		modelMap.put("PUNISHNO", request.getParameter("PUNISHNO"));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewPunishment", modelMap);
	}

	/**
	 * 特记事项
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSpecialMatter")
	public ModelAndView viewSpecialMatter(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 紧急联系人详细信息
		List elist = empInfoSer.viewSpecialMatter(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("specialNo", param.get("SPECIAL_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("specialNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewSpecialMatter", elist);
		modelMap.put("totalcount", empInfoSer.viewSpecialMatter_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewSpecialMatter", modelMap);
	}

	/**
	 * 护照签证事项
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPassportPerson")
	public ModelAndView viewPassportPerson(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 紧急联系人详细信息
		List elist = empInfoSer.viewPassportPerson(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("passperNo", param.get("PASSPER_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("passperNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewPassportPerson", elist);
		modelMap
				.put("totalcount", empInfoSer.viewPassportPerson_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewPassportPerson", modelMap);

	}

	/**
	 * 护照签证事项
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPassportFamily")
	public ModelAndView viewPassportFamily(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List elist = empInfoSer.viewPassportPerson(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("passperNo", param.get("PASSPER_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("passperNo", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewPassportPerson", elist);
		modelMap
				.put("totalcount", empInfoSer.viewPassportPerson_count(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewPassportFamily", modelMap);

	}

	@RequestMapping(value = "/editTitleName")
	public ModelAndView editTitleName(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List elist = empInfoSer.searchTitlename(request);
		modelMap.put("editTitleName", elist);
		return new ModelAndView("/hrm/empinfo/editTitleName", modelMap);
	}

	/**
	 * 信息搜索
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/informationSearch")
	public ModelAndView informationSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String dataSearch = StringUtil.checkNull(request
				.getParameter("dataSearch"));
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.informationSearchMain(request);
		List elist = null;
		if ("".equals(dataSearch) || dataSearch == null) {
			dataSearch = "emergencyAddress";
		}
		if ("emergencyAddress".equals(dataSearch)) {
			elist = empInfoSer.emergencyAddress(request);
			modelMap.put("emergencyAddress", elist);
			modelMap.put("dataDistinguish", "14014397");
			modelMap.put("MAIN_LIAISON_OFFICE", request
					.getParameter("MAIN_LIAISON_OFFICE"));
			modelMap.put("EMER_TYPE_CODE", request
					.getParameter("EMER_TYPE_CODE"));
			modelMap.put("ISDEPTNO10", request.getParameter("ISDEPTNO10"));
		} else if ("family".equals(dataSearch)) {
			elist = empInfoSer.familySearch(request);
			modelMap.put("familySearch", elist);
			modelMap.put("dataDistinguish", "14014399");
			modelMap.put("ISDEPTNO11", StringUtil.checkNull(request
					.getParameter("ISDEPTNO11")));
			modelMap.put("FAM_TYPE_CODE", StringUtil.checkNull(request
					.getParameter("FAM_TYPE_CODE")));
			modelMap.put("FAM_NAME", StringUtil.checkNull(request
					.getParameter("FAM_NAME")));
		} else if ("experience".equals(dataSearch)) {
			elist = empInfoSer.experienceSearch(request);
			modelMap.put("experienceSearch", elist);
			modelMap.put("dataDistinguish", "14014400");
			modelMap.put("ISDEPTNO12", StringUtil.checkNull(request
					.getParameter("ISDEPTNO12")));
			modelMap.put("START_DATE_EXP", StringUtil.checkNull(request
					.getParameter("START_DATE_EXP")));
			modelMap.put("END_DATE_EXP", StringUtil.checkNull(request
					.getParameter("END_DATE_EXP")));
			modelMap.put("CPNY_NAME", StringUtil.checkNull(request
					.getParameter("CPNY_NAME")));
			modelMap.put("POSITION", StringUtil.checkNull(request
					.getParameter("POSITION")));
		} else if ("education".equals(dataSearch)) {
			elist = empInfoSer.educationSearch(request);
			modelMap.put("educationSearch", elist);
			modelMap.put("dataDistinguish", "14014404");
			modelMap.put("ISDEPTNO13", StringUtil.checkNull(request
					.getParameter("ISDEPTNO13")));
			modelMap.put("START_DATE_EDU", StringUtil.checkNull(request
					.getParameter("START_DATE_EDU")));
			modelMap.put("END_DATE_EDU", StringUtil.checkNull(request
					.getParameter("END_DATE_EDU")));
			modelMap.put("DEGREE_CODE", StringUtil.checkNull(request
					.getParameter("DEGREE_CODE")));
			modelMap.put("FINAL_DEGREE_WHETHER", StringUtil.checkNull(request
					.getParameter("FINAL_DEGREE_WHETHER")));
			modelMap.put("INSTITUTION_NAME", StringUtil.checkNull(request
					.getParameter("INSTITUTION_NAME")));
			modelMap.put("SUBJECT", StringUtil.checkNull(request
					.getParameter("SUBJECT")));
		} else if ("bid".equals(dataSearch)) {// 资格搜索
			elist = empInfoSer.bidSearch(request);
			modelMap.put("bidSearch", elist);
			modelMap.put("dataDistinguish", "14014403");
			modelMap.put("ISDEPTNO14", StringUtil.checkNull(request
					.getParameter("ISDEPTNO14")));
			modelMap.put("START_DATE_BID", StringUtil.checkNull(request
					.getParameter("START_DATE_BID")));
			modelMap.put("END_DATE_BID", StringUtil.checkNull(request
					.getParameter("END_DATE_BID")));
			modelMap.put("QUAL_NAME", StringUtil.checkNull(request
					.getParameter("QUAL_NAME")));
			modelMap.put("QUAL_LEVEL", StringUtil.checkNull(request
					.getParameter("QUAL_LEVEL")));
		} else if ("grade".equals(dataSearch)) {// 职级搜索
			elist = empInfoSer.gradeSearch(request);
			modelMap.put("gradeSearch", elist);
			modelMap.put("dataDistinguish", "14014405");
			modelMap.put("ISDEPTNO15", StringUtil.checkNull(request
					.getParameter("ISDEPTNO15")));
		} else if ("address".equals(dataSearch)) {// 地址搜索
			elist = empInfoSer.addressSearch(request);
			modelMap.put("addressSearch", elist);
			modelMap.put("dataDistinguish", "14014398");
			modelMap.put("ISDEPTNO16", StringUtil.checkNull(request
					.getParameter("ISDEPTNO16")));
		} else if ("recognition".equals(dataSearch)) {// 表彰搜索
			elist = empInfoSer.recognitionSearch(request);
			modelMap.put("recognitionSearch", elist);
			modelMap.put("dataDistinguish", "14014406");
			modelMap.put("ISDEPTNO17", StringUtil.checkNull(request
					.getParameter("ISDEPTNO17")));
			modelMap.put("START_DATE_REC", StringUtil.checkNull(request
					.getParameter("START_DATE_REC")));
			modelMap.put("END_DATE_REC", StringUtil.checkNull(request
					.getParameter("END_DATE_REC")));
			modelMap.put("REWARD_TYPE", StringUtil.checkNull(request
					.getParameter("REWARD_TYPE")));
		} else if ("punishment".equals(dataSearch)) {// 惩罚搜索
			elist = empInfoSer.punishmentSearch(request);
			modelMap.put("punishmentSearch", elist);
			modelMap.put("dataDistinguish", "14014407");
			modelMap.put("ISDEPTNO18", StringUtil.checkNull(request
					.getParameter("ISDEPTNO18")));
			modelMap.put("START_DATE_PUN", StringUtil.checkNull(request
					.getParameter("START_DATE_PUN")));
			modelMap.put("END_DATE_PUN", StringUtil.checkNull(request
					.getParameter("END_DATE_PUN")));
			modelMap.put("PUNISH_CODE", StringUtil.checkNull(request
					.getParameter("PUNISH_CODE")));
		} else if ("retire".equals(dataSearch)) {// 退职搜索
			elist = empInfoSer.retireSearch(request);
			modelMap.put("retireSearch", elist);
			modelMap.put("dataDistinguish", "14014408");
			modelMap.put("ISDEPTNO19", StringUtil.checkNull(request
					.getParameter("ISDEPTNO19")));
			modelMap.put("START_DATE_RET", StringUtil.checkNull(request
					.getParameter("START_DATE_RET")));
			modelMap.put("END_DATE_RET", StringUtil.checkNull(request
					.getParameter("END_DATE_RET")));
		}

		if (elist != null && elist.size() > 0) {
			modelMap.put("count", elist.size());
		}
		modelMap.put("personinfo", param);
		modelMap.put("dataSearch", dataSearch);
		modelMap.put("lowerDepart", request.getParameter("lowerDepart"));
		modelMap.put("POST_FAMILY", request.getParameter("POST_FAMILY"));
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("MAIN_BUSINESS", request.getParameter("MAIN_BUSINESS"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
		modelMap.put("EMP_OFFICE", request.getParameter("EMP_OFFICE"));
		modelMap.put("START_DATE", request.getParameter("START_DATE"));
		modelMap.put("END_DATE", request.getParameter("END_DATE"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("LOCAL_TITLE", request.getParameter("LOCAL_TITLE"));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/informationSearch", modelMap);

	}

	// 退职搜索
	@RequestMapping(value = "/retireSearch")
	public ModelAndView retireSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.informationSearchMain(request);
		String flag = StringUtil.checkNull(request.getParameter("FLAG"));
		String dataSearch = StringUtil.checkNull(request
				.getParameter("dataSearch"));
		if ("1".equals(flag) || !"".equals(dataSearch)) {
			List elist = null;
			elist = empInfoSer.retireSearch(request);
			modelMap.put("retireSearch", elist);
			modelMap.put("dataDistinguish", "14014408");
			modelMap.put("ISDEPTNO19", StringUtil.checkNull(request
					.getParameter("ISDEPTNO19")));
			modelMap.put("START_DATE_RET", StringUtil.checkNull(request
					.getParameter("START_DATE_RET")));
			modelMap.put("END_DATE_RET", StringUtil.checkNull(request
					.getParameter("END_DATE_RET")));
			if (elist != null && elist.size() > 0) {
				modelMap.put("count", elist.size());
			}
		}

		modelMap.put("personinfo", param);
		modelMap.put("dataSearch", dataSearch);
		modelMap.put("lowerDepart", request.getParameter("lowerDepart"));
		modelMap.put("POST_FAMILY", request.getParameter("POST_FAMILY"));
		modelMap.put("zhiqunRETIREINFOR", request.getParameter("zhiqunRETIREINFOR"));
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("zhijiRETIREINFOR", request.getParameter("zhijiRETIREINFOR"));
		modelMap.put("MAIN_BUSINESS", request.getParameter("MAIN_BUSINESS"));
		modelMap.put("zhuyaoyewuRETIREINFOR", request.getParameter("zhuyaoyewuRETIREINFOR"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
		modelMap.put("zhiyuanleixingRETIREINFOR", request.getParameter("zhiyuanleixingRETIREINFOR"));
		modelMap.put("EMP_OFFICE", request.getParameter("EMP_OFFICE"));
		modelMap.put("renzhizhuangtaiRETIREINFOR", request.getParameter("renzhizhuangtaiRETIREINFOR"));
		modelMap.put("START_DATE", request.getParameter("START_DATE"));
		modelMap.put("END_DATE", request.getParameter("END_DATE"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("LOCAL_TITLE", request.getParameter("LOCAL_TITLE"));
		
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/retireSearch", modelMap);
	}

	// 惩戒搜索
	@RequestMapping(value = "/punishmentSearch")
	public ModelAndView punishmentSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.informationSearchMain(request);
		String flag = StringUtil.checkNull(request.getParameter("FLAG"));
		String dataSearch = StringUtil.checkNull(request
				.getParameter("dataSearch"));
		if ("1".equals(flag) || !"".equals(dataSearch)) {
			List elist = null;
			elist = empInfoSer.punishmentSearch(request);
			modelMap.put("punishmentSearch", elist);
			modelMap.put("dataDistinguish", "14014407");
			modelMap.put("ISDEPTNO18", StringUtil.checkNull(request
					.getParameter("ISDEPTNO18")));
			modelMap.put("START_DATE_PUN", StringUtil.checkNull(request
					.getParameter("START_DATE_PUN")));
			modelMap.put("END_DATE_PUN", StringUtil.checkNull(request
					.getParameter("END_DATE_PUN")));
			modelMap.put("PUNISH_CODE", StringUtil.checkNull(request
					.getParameter("PUNISH_CODE")));
			if (elist != null && elist.size() > 0) {
				modelMap.put("count", elist.size());
			}
		}
		modelMap.put("personinfo", param);
		modelMap.put("dataSearch", dataSearch);
		modelMap.put("lowerDepart", request.getParameter("lowerDepart"));
		modelMap.put("POST_FAMILY", request.getParameter("POST_FAMILY"));
		modelMap.put("zhiqunPUNISHINFOR", request.getParameter("zhiqunPUNISHINFOR"));
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("zhijiPUNISHINFOR", request.getParameter("zhijiPUNISHINFOR"));
		modelMap.put("MAIN_BUSINESS", request.getParameter("MAIN_BUSINESS"));
		modelMap.put("zhuyaoyewuPUNISHINFOR", request.getParameter("zhuyaoyewuPUNISHINFOR"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
		modelMap.put("zhiyuanleixingPUNISHINFOR", request.getParameter("zhiyuanleixingPUNISHINFOR"));
		modelMap.put("EMP_OFFICE", request.getParameter("EMP_OFFICE"));
		modelMap.put("renzhizhuangtaiPUNISHINFOR", request.getParameter("renzhizhuangtaiPUNISHINFOR"));
		modelMap.put("START_DATE", request.getParameter("START_DATE"));
		modelMap.put("END_DATE", request.getParameter("END_DATE"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("LOCAL_TITLE", request.getParameter("LOCAL_TITLE"));
		modelMap.put("chengfalxPUNISHINFOR", request.getParameter("chengfalxPUNISHINFOR"));
		
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/punishmentSearch", modelMap);

	}

	// 表彰搜索
	@RequestMapping(value = "/recognitionSearch")
	public ModelAndView recognitionSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.informationSearchMain(request);
		String flag = StringUtil.checkNull(request.getParameter("FLAG"));
		String dataSearch = StringUtil.checkNull(request
				.getParameter("dataSearch"));
		if ("1".equals(flag) || !"".equals(dataSearch)) {
			List elist = null;
			elist = empInfoSer.recognitionSearch(request);
			modelMap.put("recognitionSearch", elist);
			modelMap.put("dataDistinguish", "14014406");
			modelMap.put("ISDEPTNO17", StringUtil.checkNull(request.getParameter("ISDEPTNO17")));
			modelMap.put("START_DATE_REC", StringUtil.checkNull(request.getParameter("START_DATE_REC")));
			modelMap.put("END_DATE_REC", StringUtil.checkNull(request.getParameter("END_DATE_REC")));
			modelMap.put("REWARD_TYPE", StringUtil.checkNull(request.getParameter("REWARD_TYPE")));
			if (elist != null && elist.size() > 0) {
				modelMap.put("count", elist.size());
			}
		}
		modelMap.put("personinfo", param);
		modelMap.put("dataSearch", dataSearch);
		modelMap.put("lowerDepart", request.getParameter("lowerDepart"));
		modelMap.put("POST_FAMILY", request.getParameter("POST_FAMILY"));
		modelMap.put("zhiqunRECOGNINFOR", request.getParameter("zhiqunRECOGNINFOR"));
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("zhijiRECOGNINFOR", request.getParameter("zhijiRECOGNINFOR"));
		modelMap.put("gangweiRECOGNINFOR", request.getParameter("gangweiRECOGNINFOR"));
		modelMap.put("MAIN_BUSINESS", request.getParameter("MAIN_BUSINESS"));
		modelMap.put("zhuyaoyewuRECOGNINFOR", request.getParameter("zhuyaoyewuRECOGNINFOR"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
		modelMap.put("zhiyuanleixingRECOGNINFOR", request.getParameter("zhiyuanleixingRECOGNINFOR"));
		modelMap.put("EMP_OFFICE", request.getParameter("EMP_OFFICE"));
		modelMap.put("renzhizhuangtaiRECOGNINFOR", request.getParameter("renzhizhuangtaiRECOGNINFOR"));
		modelMap.put("START_DATE", request.getParameter("START_DATE"));
		modelMap.put("END_DATE", request.getParameter("END_DATE"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("LOCAL_TITLE", request.getParameter("LOCAL_TITLE"));
		modelMap.put("biaoyangRECOGNINFOR", request.getParameter("biaoyangRECOGNINFOR"));
		
		request.getSession().setAttribute("TABS_SELECTED",request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/recognitionSearch", modelMap);

	}

	// 地址搜索
	@RequestMapping(value = "/addressSearch")
	public ModelAndView addressSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.informationSearchMain(request);
		String flag = StringUtil.checkNull(request.getParameter("FLAG"));
		String dataSearch = StringUtil.checkNull(request
				.getParameter("dataSearch"));
		if ("1".equals(flag) || !"".equals(dataSearch)) {
			List elist = null;
			elist = empInfoSer.addressSearch(request);
			modelMap.put("addressSearch", elist);
			modelMap.put("dataDistinguish", "14014398");
			modelMap.put("ISDEPTNO16", StringUtil.checkNull(request
					.getParameter("ISDEPTNO16")));
			if (elist != null && elist.size() > 0) {
				modelMap.put("count", elist.size());
			}
		}
		modelMap.put("personinfo", param);
		modelMap.put("dataSearch", dataSearch);
		modelMap.put("lowerDepart", request.getParameter("lowerDepart"));
		modelMap.put("POST_FAMILY", request.getParameter("POST_FAMILY"));
		modelMap.put("zhiqunADDRESSINFOR", request.getParameter("zhiqunADDRESSINFOR"));
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("zhijiADDRESSINFOR", request.getParameter("zhijiADDRESSINFOR"));
		modelMap.put("MAIN_BUSINESS", request.getParameter("MAIN_BUSINESS"));
		modelMap.put("zhuyaoyewuADDRESSINFOR", request.getParameter("zhuyaoyewuADDRESSINFOR"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
		modelMap.put("zhiyuanleixingADDRESSINFOR", request.getParameter("zhiyuanleixingADDRESSINFOR"));
		modelMap.put("EMP_OFFICE", request.getParameter("EMP_OFFICE"));
		modelMap.put("renzhizhuangtaiADDRESSINFOR", request.getParameter("renzhizhuangtaiADDRESSINFOR"));
		modelMap.put("START_DATE", request.getParameter("START_DATE"));
		modelMap.put("END_DATE", request.getParameter("END_DATE"));
		modelMap.put("ADDRESS_CONTENT", request.getParameter("ADDRESS_CONTENT"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("LOCAL_TITLE", request.getParameter("LOCAL_TITLE"));
		
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/addressSearch", modelMap);

	}

	// 职级搜索
	@RequestMapping(value = "/gradeSearch")
	public ModelAndView gradeSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.informationSearchMain(request);
		String flag = StringUtil.checkNull(request.getParameter("FLAG"));
		String dataSearch = StringUtil.checkNull(request
				.getParameter("dataSearch"));
		if ("1".equals(flag) || !"".equals(dataSearch)) {
			List elist = null;
			elist = empInfoSer.gradeSearch(request);
			modelMap.put("gradeSearch", elist);
			modelMap.put("dataDistinguish", "14014405");
			modelMap.put("ISDEPTNO15", StringUtil.checkNull(request
					.getParameter("ISDEPTNO15")));
			if (elist != null && elist.size() > 0) {
				modelMap.put("count", elist.size());
			}
		}
		modelMap.put("personinfo", param);
		modelMap.put("dataSearch", dataSearch);
		modelMap.put("lowerDepart", request.getParameter("lowerDepart"));
		modelMap.put("POST_FAMILY", request.getParameter("POST_FAMILY"));
		modelMap.put("zhiqunGRADEINFOR", request.getParameter("zhiqunGRADEINFOR"));
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("zhijiGRADEINFOR", request.getParameter("zhijiGRADEINFOR"));
		modelMap.put("EMP_OFFICE", request.getParameter("EMP_OFFICE"));
		modelMap.put("renzhizhuangtaiGRADEINFOR", request.getParameter("renzhizhuangtaiGRADEINFOR"));
		modelMap.put("START_DATE", request.getParameter("START_DATE"));
		modelMap.put("END_DATE", request.getParameter("END_DATE"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("LOCAL_TITLE", request.getParameter("LOCAL_TITLE"));
		
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/gradeSearch", modelMap);

	}

	// 资格搜索
	@RequestMapping(value = "/bidSearch")
	public ModelAndView bidSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.informationSearchMain(request);
		String flag = StringUtil.checkNull(request.getParameter("FLAG"));
		String dataSearch = StringUtil.checkNull(request
				.getParameter("dataSearch"));
		if ("1".equals(flag) || !"".equals(dataSearch)) {
			List elist = null;
			elist = empInfoSer.bidSearch(request);
			modelMap.put("bidSearch", elist);
			modelMap.put("dataDistinguish", "14014403");
			modelMap.put("ISDEPTNO14", StringUtil.checkNull(request
					.getParameter("ISDEPTNO14")));
			modelMap.put("START_DATE_BID", StringUtil.checkNull(request
					.getParameter("START_DATE_BID")));
			modelMap.put("END_DATE_BID", StringUtil.checkNull(request
					.getParameter("END_DATE_BID")));
			modelMap.put("QUAL_NAME", StringUtil.checkNull(request
					.getParameter("QUAL_NAME")));
			modelMap.put("QUAL_LEVEL", StringUtil.checkNull(request
					.getParameter("QUAL_LEVEL")));
			if (elist != null && elist.size() > 0) {
				modelMap.put("count", elist.size());
			}
		}
		modelMap.put("personinfo", param);
		modelMap.put("dataSearch", dataSearch);
		modelMap.put("lowerDepart", request.getParameter("lowerDepart"));
		modelMap.put("POST_FAMILY", request.getParameter("POST_FAMILY"));
		modelMap.put("zhiqunBIDINFOR", request.getParameter("zhiqunBIDINFOR"));
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("zhijiBIDINFOR", request.getParameter("zhijiBIDINFOR"));
		modelMap.put("MAIN_BUSINESS", request.getParameter("MAIN_BUSINESS"));
		modelMap.put("zhuyaoyewuBIDINFOR", request.getParameter("zhuyaoyewuBIDINFOR"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
		modelMap.put("zhiyuanleixingBIDINFOR", request.getParameter("zhiyuanleixingBIDINFOR"));
		modelMap.put("EMP_OFFICE", request.getParameter("EMP_OFFICE"));
		modelMap.put("renzhizhuangtaiBIDINFOR", request.getParameter("renzhizhuangtaiBIDINFOR"));
		modelMap.put("START_DATE", request.getParameter("START_DATE"));
		modelMap.put("END_DATE", request.getParameter("END_DATE"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("LOCAL_TITLE", request.getParameter("LOCAL_TITLE"));
		
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/bidSearch", modelMap);

	}

	// 学历搜索
	@RequestMapping(value = "/educationSearch")
	public ModelAndView educationSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.informationSearchMain(request);
		String flag = StringUtil.checkNull(request.getParameter("FLAG"));
		String dataSearch = StringUtil.checkNull(request
				.getParameter("dataSearch"));
		if ("1".equals(flag) || !"".equals(dataSearch)) {
			List elist = null;
			elist = empInfoSer.educationSearch(request);
			modelMap.put("educationSearch", elist);
			modelMap.put("dataDistinguish", "14014404");
			modelMap.put("ISDEPTNO13", StringUtil.checkNull(request
					.getParameter("ISDEPTNO13")));
			modelMap.put("START_DATE_EDU", StringUtil.checkNull(request
					.getParameter("START_DATE_EDU")));
			modelMap.put("END_DATE_EDU", StringUtil.checkNull(request
					.getParameter("END_DATE_EDU")));
			modelMap.put("DEGREE_CODE", StringUtil.checkNull(request
					.getParameter("DEGREE_CODE")));
			modelMap.put("FINAL_DEGREE_WHETHER", StringUtil.checkNull(request
					.getParameter("FINAL_DEGREE_WHETHER")));
			modelMap.put("INSTITUTION_NAME", StringUtil.checkNull(request
					.getParameter("INSTITUTION_NAME")));
			modelMap.put("SUBJECT", StringUtil.checkNull(request
					.getParameter("SUBJECT")));
			if (elist != null && elist.size() > 0) {
				modelMap.put("count", elist.size());
			}
		}
		modelMap.put("personinfo", param);
		modelMap.put("dataSearch", dataSearch);
		modelMap.put("lowerDepart", request.getParameter("lowerDepart"));
		modelMap.put("POST_FAMILY", request.getParameter("POST_FAMILY"));
		modelMap.put("zhiqunEDUCATIONINFOR", request.getParameter("zhiqunEDUCATIONINFOR"));
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("zhijiEDUCATIONINFOR", request.getParameter("zhijiEDUCATIONINFOR"));
		modelMap.put("MAIN_BUSINESS", request.getParameter("MAIN_BUSINESS"));
		modelMap.put("zhuyaoyewuEDUCATIONINFOR", request.getParameter("zhuyaoyewuEDUCATIONINFOR"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
		modelMap.put("zhiyuanleixingEDUCATIONINFOR", request.getParameter("zhiyuanleixingEDUCATIONINFOR"));
		modelMap.put("START_DATE", request.getParameter("START_DATE"));
		modelMap.put("END_DATE", request.getParameter("END_DATE"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("LOCAL_TITLE", request.getParameter("LOCAL_TITLE"));
		modelMap.put("xueliEDUCATIONINFOR", request.getParameter("xueliEDUCATIONINFOR"));
		modelMap.put("EMP_OFFICE", request.getParameter("EMP_OFFICE"));
		modelMap.put("renzhizhuangtaiEDUCATIONINFOR", request.getParameter("renzhizhuangtaiEDUCATIONINFOR"));
		
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/educationSearch", modelMap);

	}

	// 经历搜索
	@RequestMapping(value = "/experienceSearch")
	public ModelAndView experienceSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.informationSearchMain(request);
		String flag = StringUtil.checkNull(request.getParameter("FLAG"));
		String dataSearch = StringUtil.checkNull(request
				.getParameter("dataSearch"));
		if ("1".equals(flag) || !"".equals(dataSearch)) {
			List elist = null;
			elist = empInfoSer.experienceSearch(request);
			modelMap.put("experienceSearch", elist);
			modelMap.put("dataDistinguish", "14014400");
			modelMap.put("ISDEPTNO12", StringUtil.checkNull(request
					.getParameter("ISDEPTNO12")));
			modelMap.put("START_DATE_EXP", StringUtil.checkNull(request
					.getParameter("START_DATE_EXP")));
			modelMap.put("END_DATE_EXP", StringUtil.checkNull(request
					.getParameter("END_DATE_EXP")));
			modelMap.put("CPNY_NAME", StringUtil.checkNull(request
					.getParameter("CPNY_NAME")));
			modelMap.put("POSITION", StringUtil.checkNull(request
					.getParameter("POSITION")));
			if (elist != null && elist.size() > 0) {
				modelMap.put("count", elist.size());
			}
		}
		modelMap.put("personinfo", param);
		modelMap.put("dataSearch", dataSearch);
		modelMap.put("lowerDepart", request.getParameter("lowerDepart"));
		modelMap.put("POST_FAMILY", request.getParameter("POST_FAMILY"));
		modelMap.put("zhiqunEXPEINFOR", request.getParameter("zhiqunEXPEINFOR"));
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("zhijiEXPEINFOR", request.getParameter("zhijiEXPEINFOR"));
		modelMap.put("MAIN_BUSINESS", request.getParameter("MAIN_BUSINESS"));
		modelMap.put("zhuyaoyewuEXPEINFOR", request.getParameter("zhuyaoyewuEXPEINFOR"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
		modelMap.put("zhiyuanleixingEXPEINFOR", request.getParameter("zhiyuanleixingEXPEINFOR"));
		modelMap.put("EMP_OFFICE", request.getParameter("EMP_OFFICE"));
		modelMap.put("renzhizhuangtaiEXPEINFOR", request.getParameter("renzhizhuangtaiEXPEINFOR"));
		modelMap.put("START_DATE", request.getParameter("START_DATE"));
		modelMap.put("END_DATE", request.getParameter("END_DATE"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("LOCAL_TITLE", request.getParameter("LOCAL_TITLE"));
		
		modelMap.put("zhiwuEXPEINFOR", request.getParameter("zhiwuEXPEINFOR"));
		
		
		
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/emergencyAddressSearch", modelMap);

	}

	// 紧急联系地址搜索
	@RequestMapping(value = "/emergencyAddressSearch")
	public ModelAndView emergencyAddressSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap param = (LinkedHashMap) empInfoSer.informationSearchMain(request);
		String flag = StringUtil.checkNull(request.getParameter("FLAG"));
		String dataSearch = StringUtil.checkNull(request
				.getParameter("dataSearch"));
		if ("1".equals(flag) || !"".equals(dataSearch)) {
			List elist = null;
			elist = empInfoSer.emergencyAddress(request);
			modelMap.put("emergencyAddress", elist);
			modelMap.put("dataDistinguish", "14014397");
			modelMap.put("MAIN_LIAISON_OFFICE", request
					.getParameter("MAIN_LIAISON_OFFICE"));
			modelMap.put("EMER_TYPE_CODE", request
					.getParameter("EMER_TYPE_CODE"));
			modelMap.put("ISDEPTNO10", request.getParameter("ISDEPTNO10"));
			if (elist != null && elist.size() > 0) {
				modelMap.put("count", elist.size());
			}
		}
		/*modelMap.put("KEY", request.getParameter("seach_KEY"));*/
		modelMap.put("personinfo", param);
		modelMap.put("dataSearch", dataSearch);
		modelMap.put("lowerDepart", request.getParameter("lowerDepart"));
		modelMap.put("POST_FAMILY", request.getParameter("POST_FAMILY"));
		modelMap.put("zhiqunEMERINFOR", request.getParameter("zhiqunEMERINFOR"));
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("zhijiEMERINFOR", request.getParameter("zhijiEMERINFOR"));
		modelMap.put("EMER_TYPE_CODE", request.getParameter("EMER_TYPE_CODE"));
		modelMap.put("guanxiEMERINFOR", request.getParameter("guanxiEMERINFOR"));
		modelMap.put("MAIN_BUSINESS", request.getParameter("MAIN_BUSINESS"));
		modelMap.put("zhuyaoyewuEMERINFOR", request.getParameter("zhuyaoyewuEMERINFOR"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
		modelMap.put("yuangongzhuangtaiEMERINFOR", request .getParameter("yuangongzhuangtaiEMERINFOR"));
		modelMap.put("EMP_OFFICE", request.getParameter("EMP_OFFICE"));
		modelMap.put("renzhizhuangtaiEMERINFOR", request.getParameter("renzhizhuangtaiEMERINFOR"));
		modelMap.put("START_DATE", request.getParameter("START_DATE"));
		modelMap.put("END_DATE", request.getParameter("END_DATE"));
		modelMap.put("MAIN_LIAISON_OFFICE", request.getParameter("MAIN_LIAISON_OFFICE"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("LOCAL_TITLE", request.getParameter("LOCAL_TITLE"));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/emergencyAddressSearch", modelMap);

	}

	// 家庭搜索
	@RequestMapping(value = "/familySearch")
	public ModelAndView familySearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap param = (LinkedHashMap) empInfoSer
				.informationSearchMain(request);
		String flag = StringUtil.checkNull(request.getParameter("FLAG"));
		String dataSearch = StringUtil.checkNull(request
				.getParameter("dataSearch"));
		if ("1".equals(flag) || !"".equals(dataSearch)) {
			List elist = null;
			elist = empInfoSer.familySearch(request);
			modelMap.put("familySearch", elist);
			modelMap.put("dataDistinguish", "14014399");
			modelMap.put("ISDEPTNO11", StringUtil.checkNull(request.getParameter("ISDEPTNO11")));
			modelMap.put("FAM_TYPE_CODE", StringUtil.checkNull(request.getParameter("FAM_TYPE_CODE")));
			modelMap.put("FAM_NAME", StringUtil.checkNull(request.getParameter("FAM_NAME")));
			if (elist != null && elist.size() > 0) {
				modelMap.put("count", elist.size());
			}
		}
		modelMap.put("personinfo", param);
		modelMap.put("dataSearch", dataSearch);
		modelMap.put("lowerDepart", request.getParameter("lowerDepart"));
		modelMap.put("POST_FAMILY", request.getParameter("POST_FAMILY"));
		modelMap.put("zhiqunFAMILYINFOR", request.getParameter("zhiqunFAMILYINFOR"));
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("zhijiFAMILYINFOR", request.getParameter("zhijiFAMILYINFOR"));
		modelMap.put("MAIN_BUSINESS", request.getParameter("MAIN_BUSINESS"));
		modelMap.put("zhuyaoyewuFAMILYINFOR", request.getParameter("zhuyaoyewuFAMILYINFOR"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
		modelMap.put("zhiyuanleixingFAMILYINFOR", request.getParameter("zhiyuanleixingFAMILYINFOR"));
		modelMap.put("EMP_OFFICE", request.getParameter("EMP_OFFICE"));
		modelMap.put("renzhizhuangtaiFAMILYINFOR", request.getParameter("renzhizhuangtaiFAMILYINFOR"));
		modelMap.put("START_DATE", request.getParameter("START_DATE"));
		modelMap.put("END_DATE", request.getParameter("END_DATE"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("LOCAL_TITLE", request.getParameter("LOCAL_TITLE"));
		modelMap.put("guanxiFAMILYINFOR", request.getParameter("guanxiFAMILYINFOR"));
		
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/familySearch", modelMap);

	}

	/**
	 * 员工搜索
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/employeeSearch")
	public ModelAndView employeeSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		return new ModelAndView("/hrm/empinfo/employeeSearch", modelMap);

	}

	/**
	 * supervisor履历
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSupervisorInfoList")
	public ModelAndView viewSupervisorInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag == null || "".equals(firstFlag)) {
			List viewSupervisorInfoList = this.empInfoSer
					.viewSupervisorInfoList(request);
			modelMap.put("viewSupervisorInfoList", viewSupervisorInfoList);
			modelMap.put("viewSupervisorInfoListCnt",
					viewSupervisorInfoList == null ? 0 : viewSupervisorInfoList
							.size());

			LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
					.viewHrPersonalInfo(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo", linkMap);
		} else {
			modelMap.put("MANAGE_OFFICE", "'15118'");
			modelMap.put("MANAGE_OFFICE_NAME", "'在职'");
			modelMap.put("EMP_OFFICE", "'15118'");
			modelMap.put("EMP_OFFICE_NAME", "'在职'");
		}

		return new ModelAndView("/hrm/empinfo/viewSupervisorInfoList", modelMap);
	}

	/**
	 * 照片导入
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/photoImport")
	public ModelAndView photoImport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String currentIndex = "0";
		// 附件下载功能
		LinkedHashMap fileParam = new LinkedHashMap();
		fileParam.put("APPLY_TYPE", "photo_import");
		fileParam.put("CPNY_ID", admin.getCpnyId());
		fileParam.put("interLanguage", admin.getLanguage());
		List pList = infoApplyLeaveDao.getPhotoFileList(fileParam);
		if (pList != null && pList.size() > 0) {
			String applyno = (String) ((LinkedHashMap) pList.get(0))
					.get("APPLY_NO");
			if (applyno.equals("photo_no")) {
				LinkedHashMap param = null;
				for (int i = 0; i < pList.size(); i++) {
					param = (LinkedHashMap) pList.get(i);
					String filename = (String) param.get("FILE_NAME");
					int num = filename.indexOf(".");
					String person_id_name = filename.substring(0, num);
					person_id_name = person_id_name.trim();
					fileParam.put("EMPID", person_id_name);
					LinkedHashMap linkMap = (LinkedHashMap) infoApplyLeaveDao
							.queryEmployeePhoto(fileParam);
					if (linkMap != null && linkMap.size() > 0) {
						param.put("EMPID", linkMap.get("EMPID"));
						param.put("LOCAL_NAME", linkMap.get("LOCAL_NAME"));
						param.put("DEPTNO_NAME", linkMap.get("ORG_NAME_LOCAL"));
						param.put("EMP_OFFICE_NAME", linkMap.get("EMP_OFFICE_NAME"));
					} else {
						param.put("CHAWUCIREN", "CHAWUCIREN");
					}
					List WWlist = new ArrayList();
					List oolist = new ArrayList();
					for (int c = 0; c < pList.size(); c++) {
						LinkedHashMap wwMap = (LinkedHashMap) pList.get(c);
						if ("CHAWUCIREN".equals((String) wwMap
								.get("CHAWUCIREN"))) {
							WWlist.add(wwMap);
						} else {
							oolist.add(wwMap);
						}
					}
					WWlist.addAll(oolist);
					pList = WWlist;
				}
			} else {
				List nlist = new ArrayList();
				LinkedHashMap param = null;
				for (int i = 0; i < pList.size(); i++) {
					param = (LinkedHashMap) pList.get(i);
					String filename = (String) param.get("FILE_NAME");
					int num = filename.indexOf(".");
					String person_id_name = filename.substring(0, num);
					person_id_name = person_id_name.trim();
					fileParam.put("LOCAL_NAME", person_id_name);
					List pplist = infoApplyLeaveDao
							.queryEmployeePhoto_name(fileParam);
					LinkedHashMap paramgg = null;
					if (pplist != null && pplist.size() > 0) {
						for (int y = 0; y < pplist.size(); y++) {
							LinkedHashMap parampp = new LinkedHashMap();
							paramgg = (LinkedHashMap) pplist.get(y);
							parampp.put("FILE_NO", (String) param
									.get("FILE_NO"));
							parampp.put("FILE_NAME", (String) param
									.get("FILE_NAME"));
							parampp.put("FILE_URL", (String) param
									.get("FILE_URL"));
							parampp.put("EMPID", (String) paramgg.get("EMPID"));
							parampp.put("LOCAL_NAME", (String) paramgg
									.get("LOCAL_NAME"));
							parampp.put("DEPTNO_NAME", (String) paramgg
									.get("ORG_NAME_LOCAL"));
							parampp.put("EMP_OFFICE_NAME", (String) paramgg
									.get("EMP_OFFICE_NAME"));

							if (pplist.size() >= 2) {
								parampp.put("CHONGFU", "CHONGFU");
							}
							nlist.add(parampp);
						}
					} else {
						LinkedHashMap parampp = new LinkedHashMap();
						parampp.put("FILE_NO", (String) param.get("FILE_NO"));
						parampp.put("FILE_NAME", (String) param
								.get("FILE_NAME"));
						parampp.put("FILE_URL", (String) param.get("FILE_URL"));
						parampp.put("CHAWUCIREN", "CHAWUCIREN");
						nlist.add(parampp);
					}
				}
				List wlist = new ArrayList();
				List clist = new ArrayList();
				List olist = new ArrayList();
				for (int w = 0; w < nlist.size(); w++) {
					LinkedHashMap wumap = (LinkedHashMap) nlist.get(w);
					if ("CHAWUCIREN".equals((String) wumap.get("CHAWUCIREN"))) {
						wlist.add(wumap);
					} else if ("CHONGFU".equals((String) wumap.get("CHONGFU"))) {
						clist.add(wumap);
					} else {
						olist.add(wumap);
					}

				}
				wlist.addAll(clist);
				wlist.addAll(olist);
				pList = wlist;
			}
			modelMap.put("applyno", applyno);
		}
		modelMap.put("fileList", pList);
		modelMap.put("allcount", pList.size());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("educ_no", request.getParameter("EDUC_NO"));// 如果是0就是添加页面,如果不是0就是进入修改页面
		String flag = StringUtil.checkNull(request.getParameter("flag"));
		if ("2".equals(flag)) {
			currentIndex = "1";
			List elist = empInfoSer.viewEmployeePhoto(request);
			modelMap.put("viewEmployeePhoto", elist);
			modelMap.put("viewEmployeePhotoCount", elist.size());
			modelMap.put("empid_namevalue", StringUtil.checkNull(request
					.getParameter("empid_namevalue")));
			modelMap.put("isWith", StringUtil.checkNull(request
					.getParameter("isWith")));
			modelMap.put("EMP_OFFICE", StringUtil.checkNull(request
					.getParameter("EMP_OFFICE")));
			modelMap.put("EMP_TYPE_CODE", StringUtil.checkNull(request
					.getParameter("EMP_TYPE_CODE")));
			modelMap.put("POST_FAMILY", StringUtil.checkNull(request
					.getParameter("POST_FAMILY")));
			modelMap.put("GRADE_NO", StringUtil.checkNull(request
					.getParameter("GRADE_NO")));
			modelMap.put("deptName", StringUtil.checkNull(request
					.getParameter("deptName")));
			modelMap.put("deptNo", StringUtil.checkNull(request
					.getParameter("deptNo")));
			modelMap.put("empid_name", StringUtil.checkNull(request
					.getParameter("empid_name")));
			modelMap.put("twodeptname", Integer.parseInt(request
					.getParameter("twodeptname")) - 1);
			modelMap.put("twodeptname1", Integer.parseInt(request
					.getParameter("twodeptname")));
			modelMap.put("QUERYDEPTNAME", StringUtil.checkNull(request
					.getParameter("QUERYDEPTNAME")));
		}
		modelMap.put("currentIndex", currentIndex);
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/photoImport", modelMap);
		// return modelMap;
	}

	/**
	 * 照片员工查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmployeePhoto")
	public ModelAndView viewEmployeePhoto(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String flag = StringUtil.checkNull(request.getParameter("flag"));
		if ("1".equals(flag)) {
		} else {
			List elist = empInfoSer.viewEmployeePhoto(request);
			modelMap.put("viewEmployeePhoto", elist);
			// modelMap.put("totalcount",
			// empInfoSer.viewPassportPerson_count(request));
		}
		return new ModelAndView("/hrm/empinfo/viewEmployeePhoto", modelMap);

	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/viewEmployeePhotoInfo")
	public Map viewHistoryDetailInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String flag = StringUtil.checkNull(request.getParameter("flag"));
		if (!"1".equals(flag)) {
			List elist = empInfoSer.viewEmployeePhotoInfo(request);
			map.put("viewEmployeePhoto", elist);
		}
		return map;
	}
	
	@RequestMapping(value = "/deleteEmployeePhotoInfo")
	@ResponseBody
	public Map<String, Object> delAttendanceApplyInfoForBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = empInfoSer.deleteEmployeePhotoInfo(request);
			if (result == 1) {
				map.put("message", "删除成功");//"批量删除休假申请决裁成功!"
				map.put("statusCode", "200");
				map.put("formId", "photoImport");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "删除失败");//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * Supervisor履历
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/supervisorVitae")
	public ModelAndView SupervisorVitae(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/SupervisorVitae", modelMap);

	}

	/**
	 * 转到个人信息(Turn to add school and personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTempEduPerInfo")
	public ModelAndView viewTempEduPerInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		List corpList = manageSer.getIsCorpInfo(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", linkMap);

		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("photoId", "viewEduPerInfo");
		modelMap.put("corpList", corpList);
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		// 获取兼卖产品类型
		List productList = empInfoSer.getEmpProductList(request);
		modelMap.put("productList", productList);
		modelMap.put("isEssSystem",
				request.getParameter("isEssSystem") != null ? (String) request
						.getParameter("isEssSystem") : "0");

		return new ModelAndView("/hrm/empinfo/viewTempEduPerInfo", modelMap);
	}

	/**
	 * 修改个人信息和学校信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEduPerInfo")
	@ResponseBody
	public Map editEduPerInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editEduPerInfo(request);
		String isEssSystem = request.getParameter("isEssSystem") != null ? (String) request
				.getParameter("isEssSystem")
				: "0";
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功

			if (isEssSystem.equals("1")) {
				map.put("navTabId", "ess0101");
				map.put("forwardUrl",
						"/ess/empinfo/essViewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&&navTabId=ess0101");
				// ?pageNum=1&menuNo=2419&
			} else {
				map.put("navTabId", "hr0101");
				map.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&&navTabId=hr0101");
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 修改个人基本信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEmpInfo")
	@ResponseBody
	public Map editEmpInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editEmpInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
			map.put("navTabId", "hr2100");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 修改hr_personal_info信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editHrPersonInfo")
	@ResponseBody
	public Map editHrPersonInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editHrPersonInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
			map.put("navTabId", "hr2100");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 修改hr_emergency_address信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editHrEmergencyAddress")
	@ResponseBody
	public Map editHrEmergencyAddress(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editHrEmergencyAddress(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editHrEmergencyAddress");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_address_matters信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editHrAddressMatters")
	@ResponseBody
	public Map editHrAddressMatters(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editHrAddressMatters(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editHrAddressMatters");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 存入标题模板
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertResults")
	@ResponseBody
	public Map insertResults(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.insertResults(request);
		String arrayno = StringUtil.checkNull(request.getParameter("arrayno"));
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "employeeSearch");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_family信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editHrFamily")
	@ResponseBody
	public Map editHrFamily(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editHrFamily(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editHrFamily");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_experince_inside信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editStartPoint")
	@ResponseBody
	public Map editStartPoint(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editStartPoint(request); 
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editStartPoint_searchForm");
			map.put("navTabId", "evs0201");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 修改hr_work_experince信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editExperiencePoint")
	@ResponseBody
	public Map editExperiencePoint(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editExperiencePoint(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editExperiencePoint");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_education信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEducationMatter")
	@ResponseBody
	public Map editEducationMatter(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editEducationMatter(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editEducationMatter");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}

		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_reward信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editRecognition")
	@ResponseBody
	public Map editRecognition(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editRecognition(request);
		String reward_no = StringUtil.checkNull(request
				.getParameter("REWARD_NO"));
		if (result == 1) {
			if (reward_no != null && !"".equals(reward_no)) {
				map.put("statusCode", "200");
				map.put("callbackType", "forward");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));// 修改成功
				map.put("forwardUrl", "/hrm/empinfo/viewRecognition?REWARDNO="
						+ request.getParameter("REWARD_NO") + "&PERSON_ID="
						+ request.getParameter("PERSON_ID"));
			} else {
				map.put("statusCode", "200");
				map.put("formId", "editRecognition");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));// 保存成功
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_Punishment信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editPunishment")
	@ResponseBody
	public Map editPunishment(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editPunishment(request);
		String punish_no = StringUtil.checkNull(request
				.getParameter("PUNISH_NO"));
		if (result == 1) {
			if (punish_no != null && !"".equals(punish_no)) {
				map.put("statusCode", "200");
				map.put("callbackType", "forward");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));// 修改成功
				map.put("forwardUrl", "/hrm/empinfo/viewPunishment?PUNISHNO="
						+ request.getParameter("PUNISH_NO") + "&PERSON_ID="
						+ request.getParameter("PERSON_ID"));
			} else {
				map.put("statusCode", "200");
				map.put("formId", "editPunishment");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));// 保存成功
			}

		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_special_matter信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editSpecialMatter")
	@ResponseBody
	public Map editSpecialMatter(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editSpecialMatter(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editSpecialMatter");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_special_matter信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editPassportPerson")
	@ResponseBody
	public Map editPassportPerson(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editPassportPerson(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editPassportPerson");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_special_matter信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editPassportFamily")
	@ResponseBody
	public Map editPassportFamily(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editPassportPerson(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editPassportFamily");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_qualification信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editBidMatter")
	@ResponseBody
	public Map editBidMatter(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editBidMatter(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editEducationMatter");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_evaluate_info信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEvaluateInfo")
	@ResponseBody
	public Map editEvaluateInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editEvaluateInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editEducationMatter");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_language_level信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editForeignLanguage")
	@ResponseBody
	public Map editForeignLanguage(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editForeignLanguage(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editForeignLanguage");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 修改hr_tarining_info信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editTrainingBasic")
	@ResponseBody
	public Map editTrainingBasic(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editTrainingBasic(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editEducationMatter");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePersonInfoPhoto")
	@ResponseBody
	public Map updatePersonInfoPhoto(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.updatePersonInfoPhoto(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("navTabId", "hr0601");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_emergency_address信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrEmergencyAddress")
	@ResponseBody
	public Map deleteHrEmergencyAddress(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteHrEmergencyAddress(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editHrEmergencyAddress");
			map.put("message", TipMessage.getTipMessage("删除成功", request));// 删除成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_address_matters信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrAddressMatters")
	@ResponseBody
	public Map deleteHrAddressMatters(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteHrAddressMatters(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editHrAddressMatters");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.detele_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_family信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrFamily")
	@ResponseBody
	public Map deleteHrFamily(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteHrFamily(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editHrFamily");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_experience_inside信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteStartPoint")
	@ResponseBody
	public Map deleteStartPoint(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteStartPoint(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editHrFamily");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_work_experience信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteExperiencePoint")
	@ResponseBody
	public Map deleteExperiencePoint(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteExperiencePoint(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editExperiencePoint");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_education信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEducationMatter")
	@ResponseBody
	public Map deleteEducationMatter(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteEducationMatter(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editEducationMatter");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_qualification信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBidMatter")
	@ResponseBody
	public Map deleteBidMatter(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteBidMatter(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editBidMatter");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_training_info信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTrainingBasic")
	@ResponseBody
	public Map deleteTrainingBasic(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteTrainingBasic(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "TrainingBasic");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_evaluate_info信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEvaluateInfo")
	@ResponseBody
	public Map deleteEvaluateInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteEvaluateInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editBidMatter");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_laguage_level信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteForeignLanguage")
	@ResponseBody
	public Map deleteForeignLanguage(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteForeignLanguage(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editForeignLanguage");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_reward信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteRecognition")
	@ResponseBody
	public Map deleteRecognition(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteRecognition(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editRecognition");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_qunishment信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePunishment")
	@ResponseBody
	public Map deletePunishment(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deletePunishment(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editPunishment");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_special_matter信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSpecialMatter")
	@ResponseBody
	public Map deleteSpecialMatter(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteSpecialMatter(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "viewEmpWomenInfo");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		map.put("KEY", request.getParameter("KEY"));
		map.put("INFOR_DIS_CODE", request.getParameter("INFOR_DIS_CODE"));
		map.put("DEPTNO", request.getParameter("DEPTNO"));
		return map;
	}

	/**
	 * 删除hr_passport_person信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePassportPerson")
	@ResponseBody
	public Map deletePassportPerson(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deletePassportPerson(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editPassportPerson");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除hr_passport_person信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePassportFamily")
	@ResponseBody
	public Map deletePassportFamily(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deletePassportPerson(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editPassportFamily");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	@RequestMapping(value = "/deleteTitlename")
	@ResponseBody
	public Map deleteTitlename(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteTitlename(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("navTabId", "hr3201");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	@RequestMapping(value = "/editTempEduPerInfo")
	@ResponseBody
	public Map editTempEduPerInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editTempEduPerInfo(request);
		String isEssSystem = request.getParameter("isEssSystem") != null ? (String) request
				.getParameter("isEssSystem")
				: "0";
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功

			if (isEssSystem.equals("1")) {
				map.put("navTabId", "ess0101");
				map.put("forwardUrl",
						"/ess/empinfo/essViewTempPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&&navTabId=ess0101");
				// ?pageNum=1&menuNo=2419&
			} else {
				map.put("navTabId", "hr0101");
				map.put("forwardUrl",
						"/hrm/empinfo/viewTempPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&&navTabId=hr0101");
			}
		} else if (result == 2) {
			map.put("statusCode", "300");
			map.put("message", "ID卡号已存在!");// 保存失败
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 发令信息(dekreti information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPromote")
	public ModelAndView getPromote(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfo(request);
		modelMap.put("personInfo", linkMap);
		modelMap.put("expInsideList", empInfoSer.getExpInsideList(request));
		modelMap.put("resignationInfo", empInfoSer.getResignationInfo(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");
		modelMap.put("photoId", "viewPromote");
		return new ModelAndView("/hrm/empinfo/viewPromote", modelMap);
	}

	/**
	 * 评价信息(Evaluation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluate")
	public ModelAndView getEvaluate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfo(request);
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("EvsInfo", empInfoSer.getEvsInfoList(request));
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");
		modelMap.put("photoId", "viewEvaluate");
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2542"));
		return new ModelAndView("/hrm/empinfo/viewEvaluate", modelMap);
	}

	/**
	 * 转到添加评价信息(Turn to add Evaluation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvsInfo")
	public ModelAndView viewEvsInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList", empInfoSer.getCodeList("14895", request));
		modelMap.put("gradeCodeList", empInfoSer.getCodeList("3538", request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewEvsInfo", modelMap);
	}

	/**
	 * 添加评价信息(add Evaluation information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEvsInfo")
	@ResponseBody
	public Map addEvsInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addEvsInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0103");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到修改评价信息(Turn to modify assessment information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/viewEditEvsInfo")
	public ModelAndView viewEditEvsInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("evsInfoList", empInfoSer.getEvsInfoList(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewEditEvsInfo", modelMap);
	}

	/**
	 * 修改评价信息(Modify assessment information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEvsInfo")
	@ResponseBody
	public Map editEvsInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editEvsInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0103");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到删除评价信息(Turn to delete EVS information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEvs")
	public ModelAndView deleteEvs(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("EvsInfo", empInfoSer.getEvsInfoList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteEvs", modelMap);
	}

	/**
	 * 删除评价信息(delete assessment information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEvsInfo")
	@ResponseBody
	public Map deleteEvsInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteEvsInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0103");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 奖励/惩戒(REWARD/PUNISHMENT)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewReward")
	public ModelAndView getReward(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("rewardList", empInfoSer.getReward(request));
		modelMap.put("punishmentList", empInfoSer.getPunishment(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewReward");

		return new ModelAndView("/hrm/empinfo/viewReward", modelMap);
	}

	/**
	 * 兼职(Plurality)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTranslate")
	public ModelAndView getTranslate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("pluralityList", empInfoSer.getPluralityList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewTranslate");

		return new ModelAndView("/hrm/empinfo/viewTranslate", modelMap);
	}

	/**
	 * 工会(TrainingInfo)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTradeunion")
	public ModelAndView getTradeunion(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("trainingInfoList", empInfoSer
				.getTradeUnionInfoList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewTraining");

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "123194"));

		return new ModelAndView("/hrm/empinfo/viewTradeunion", modelMap);
	}

	/**
	 * 培训(TrainingInfo)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTraining")
	public ModelAndView getTraining(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("trainingInfoList", empInfoSer
				.getTrainingInfoList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewTraining");

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2545"));

		return new ModelAndView("/hrm/empinfo/viewTraining", modelMap);
	}

	/**
	 * 转到添加培训信息(Turn to add Training information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTrainingInfo")
	public ModelAndView viewTrainingInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("trainingMethodList", empInfoSer.getCodeList("123271",
				request));
		modelMap.put("mustList", empInfoSer.getCodeList("123376", request));
		modelMap.put("differentiateList", empInfoSer.getCodeList("123459",
				request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewTrainingInfo", modelMap);
	}

	/**
	 * 培训信息添加(add TrainingInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTrainingInfo")
	@ResponseBody
	public Map<String, Object> addTrainingInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addTrainingInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0106");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");

		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到修改培训信息(Turn to modify Training information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateTrainingInfo")
	public ModelAndView updateTrainingInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("trainingInfoList", empInfoSer
				.getTrainingInfoList(request));
		modelMap.put("trainingMethodList", empInfoSer.getCodeList("123271",
				request));
		modelMap.put("mustList", empInfoSer.getCodeList("123376", request));
		modelMap.put("differentiateList", empInfoSer.getCodeList("123459",
				request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/updateTrainingInfo", modelMap);
	}

	/**
	 * 修改培训信息(Modify training information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editTrainingInfo")
	@ResponseBody
	public Map editTrainingInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editTrainingInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0106");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 转到删除培训信息(Turn to delete training information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTraining")
	public ModelAndView deleteTraining(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("trainingInfoList", empInfoSer
				.getTrainingInfoList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/deleteTraining", modelMap);
	}

	/**
	 * 删除培训信息(delete training information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTrainingInfo")
	@ResponseBody
	public Map deleteTrainingInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteTrainingInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0106");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 社会关系(social relations)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewRelation")
	public ModelAndView getRelation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		modelMap.put("homeRelationList", empInfoSer
				.getHomeRelationList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewRelation");

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2549"));

		return new ModelAndView("/hrm/empinfo/viewRelation", modelMap);
	}

	/**
	 * 转到添加社会关系信息(Turn to add Relation family information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewFamilyInfo")
	public ModelAndView viewFamilyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList", empInfoSer.getCodeList("1693", request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewFamilyInfo", modelMap);
	}

	/**
	 * 添加社会关系(add familyInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addFamilyInfo")
	@ResponseBody
	public Map<String, Object> addFamilyInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String isEssSystem = StringUtil.checkNull(request
				.getParameter("isEssSystem"), "0");
		int result = this.empInfoSer.addFamilyInfo(request);
		if (result == 1) {
			if (isEssSystem.equals("1")) {// ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));// 保存成功
				map.put("navTabId", "ess0101");
				// map.put("navTabId", "hr0107");
				map.put("forwardUrl",
						"/ess/empinfo/essViewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=ess0101");
			} else {// 业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));// 保存成功
				map.put("navTabId", "hr0101");
				// map.put("navTabId", "hr0107");
				map.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=hr0101");
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到删除社会关系(Turn to delete family information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFamily")
	public ModelAndView deleteFamily(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteFamily", modelMap);
	}

	/**
	 * 删除社会关系(delete family information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFamilyInfo")
	@ResponseBody
	public Map deleteFamilyInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String isEssSystem = StringUtil.checkNull(request
				.getParameter("isEssSystem"), "0");
		int result = this.empInfoSer.deleteFamilyInfo(request);
		if (result == 1) {
			if (isEssSystem.equals("1")) {// ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));// 保存成功
				map.put("navTabId", "ess0101");
				// map.put("navTabId", "hr0107");
				map.put("forwardUrl",
						"/ess/empinfo/essViewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=ess0101");
			} else {// 业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));// 删除成功
				map.put("navTabId", "hr0101");
				// map.put("navTabId", "hr0107");
				map.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=hr0101");
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 转到修改社会关系信息(Turn to modify family information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateFamilyInfo")
	public ModelAndView updateFamilyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/updateFamilyInfo", modelMap);
	}

	/**
	 * 修改社会信息(Modify family information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editFamilyInfo")
	@ResponseBody
	public Map editFamilyInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editFamilyInfo(request);
		String isEssSystem = StringUtil.checkNull(request
				.getParameter("isEssSystem"), "0");
		if (result == 1) {
			if (isEssSystem.equals("1")) {// ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));// 修改成功
				map.put("navTabId", "ess0101");
				// map.put("navTabId", "hr0107");
				map.put("forwardUrl",
						"/ess/empinfo/essViewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=ess0101");
			} else {// 业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));// 修改成功
				map.put("navTabId", "hr0101");
				// map.put("navTabId", "hr0107");
				map.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=hr0101");

			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 家人关系(social relations)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewHomeRelation")
	public ModelAndView getHomeRelation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		modelMap.put("homeRelationList", empInfoSer
				.getHomeRelationList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewRelation");

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2549"));

		return new ModelAndView("/hrm/empinfo/viewRelation", modelMap);
	}

	/**
	 * 转到添加家人关系信息(Turn to add Relation homerelation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewHomeRelationInfo")
	public ModelAndView viewHomeRelationInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList", empInfoSer.getCodeList("950", request));
		modelMap.put("codeList1", empInfoSer.getCodeList("123276", request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewHomeRelationInfo", modelMap);
	}

	/**
	 * 添加家人关系(add familyInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHomeRelationInfo")
	@ResponseBody
	public Map<String, Object> addHomeRelationInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addHomeRelationInfo(request);
		String isEssSystem = StringUtil.checkNull(request
				.getParameter("isEssSystem"), "0");
		if (result == 1) {
			if (isEssSystem.equals("1")) {// ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));// 保存成功
				map.put("navTabId", "ess0101");
				// map.put("navTabId", "hr0107");
				map.put("forwardUrl",
						"/ess/empinfo/essViewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=ess0101");
			} else {// 业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));// 保存成功
				map.put("navTabId", "hr0101");
				// map.put("navTabId", "hr0107");
				map.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=hr0101");

			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到删除家人关系(Turn to delete homerelation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHomeRelation")
	public ModelAndView deleteHomeRelationInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		modelMap.put("deleteRelationInfoList", empInfoSer
				.getHomeRelationList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteHomeRelation", modelMap);
	}

	/**
	 * 删除家人关系(delete family information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHomeRelationInfo")
	@ResponseBody
	public Map deleteHomeRelationInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteHomeRelationInfo(request);
		String isEssSystem = StringUtil.checkNull(request
				.getParameter("isEssSystem"), "0");
		if (result == 1) {
			if (isEssSystem.equals("1")) {// ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));// 删除成功
				map.put("navTabId", "ess0101");
				// map.put("navTabId", "hr0107");
				map.put("forwardUrl",
						"/ess/empinfo/essViewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=ess0101");
			} else {// 业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));// 删除成功
				map.put("navTabId", "hr0101");
				// map.put("navTabId", "hr0107");
				map.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=hr0101");

			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 转到修改家人关系信息(Turn to modify family information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateHomeRelation")
	public ModelAndView updateHomeRelation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("homeRelationList", empInfoSer
				.getHomeRelationList(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/updateHomeRelation", modelMap);
	}

	/**
	 * 修改家人信息(Modify family information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHomeRelationInfo")
	@ResponseBody
	public Map editHomeRelationInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editHomeRelation(request);
		String isEssSystem = StringUtil.checkNull(request
				.getParameter("isEssSystem"), "0");
		if (result == 1) {
			if (isEssSystem.equals("1")) {// ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));// 修改成功
				map.put("navTabId", "ess0101");
				// map.put("navTabId", "hr0107");
				map.put("forwardUrl",
						"/ess/empinfo/essViewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=ess0101");
			} else {// 业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));// 修改成功
				map.put("navTabId", "hr0101");

				// map.put("navTabId", "hr0107");
				map.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=hr0101");

			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 健康信息(Health information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewHealth")
	public ModelAndView getHealth(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("healthList", empInfoSer.getHealthList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewHealth");

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2550"));

		return new ModelAndView("/hrm/empinfo/viewHealth", modelMap);
	}

	/**
	 * 转到添加健康信息(Turn to add health information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewHealthInfo")
	public ModelAndView viewHealthInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("physicalTypeList", empInfoSer
				.getCodeList("4570", request));
		modelMap.put("industryDistinguishList", empInfoSer.getCodeList("14900",
				request));
		modelMap.put("bloodTypeList", empInfoSer.getCodeList("4573", request));
		modelMap.put("generalHealthList", empInfoSer.getCodeList("4572",
				request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewHealthInfo", modelMap);
	}

	/**
	 * 添加健康信息(add addHealthInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHealthInfo")
	@ResponseBody
	public Map<String, Object> addHealthInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addHealthInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到修改健康信息(Turn to modify health information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateHealthInfo")
	public ModelAndView updateHealthInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("healthList", empInfoSer.getHealthList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/updateHealthInfo", modelMap);

	}

	/**
	 * 修改健康信息(Modify health information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editHealthInfo")
	@ResponseBody
	public Map editHealthInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editHealthInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 转到删除健康信息(Turn to delete health information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHealth")
	public ModelAndView deleteHealth(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("healthList", empInfoSer.getHealthList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteHealth", modelMap);
	}

	/**
	 * 删除健康信息(delete health information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHealthInfo")
	@ResponseBody
	public Map deleteHealthInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteHealthInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 工作经验(work experience)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWorkInfo")
	public ModelAndView getWorkInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("workExperienceList", empInfoSer
				.getWorkExperienceList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewWorkInfo");

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2551"));

		return new ModelAndView("/hrm/empinfo/viewWorkInfo", modelMap);
	}

	/**
	 * 转到添加工作经验信息(Turn to add workExperience family information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWorkExperienceInfo")
	public ModelAndView viewWorkExperienceInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("workExperienceList", empInfoSer
				.getWorkExperienceList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewWorkExperienceInfo", modelMap);
	}

	/**
	 * 添加工作经历(add workExperience)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addWorkExperienceInfo")
	@ResponseBody
	public Map<String, Object> addWorkExperienceInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addWorkExperienceInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0109");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到修改工作经验信息(Turn to modify workExperience information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateWorkExperienceInfo")
	public ModelAndView updateWorkExperienceInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("workExperienceList1", empInfoSer
				.getWorkExperienceList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/updateWorkExperienceInfo",
				modelMap);

	}

	/**
	 * 修改工作经验信息(Modify workExperience information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editWorkExperienceInfo")
	@ResponseBody
	public Map editWorkExperienceInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editWorkExperienceInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0109");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 转到删除工作经历(Turn to delete WorkExpreience )
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteWorkExpreience")
	public ModelAndView deleteWorkExpreience(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("workExperienceList", empInfoSer
				.getWorkExperienceList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/deleteWorkExpreience", modelMap);
	}

	/**
	 * 删除工作经历(delete WorkExpreienceInfo)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteWorkExpreienceInfo")
	@ResponseBody
	public Map deleteWorkExpreienceInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteWorkExpreienceInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0109");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 资格信息(Competence information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCompetence")
	public ModelAndView viewCompetenceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("qualificationList", empInfoSer
				.getQualificationList(request));
		modelMap.put("languageLevelList", empInfoSer
				.getLanguageLevelList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewCompetence");

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2554"));

		return new ModelAndView("/hrm/empinfo/viewCompetence", modelMap);
	}

	/**
	 * 转到添加资格信息(Turn to add Competence information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCompetenceInfo")
	public ModelAndView viewCompetence(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("qualLevelList", empInfoSer.getCodeList("14910", request));
		modelMap
				.put("qualGradeList", empInfoSer.getCodeList("123485", request));
		modelMap.put("acquisitionModesList", empInfoSer.getCodeList("123268",
				request));

		modelMap.put("languageTypeCodeList", empInfoSer.getCodeList("1703",
				request));
		modelMap.put("examNameCodeList", empInfoSer
				.getCodeList("1394", request));
		modelMap.put("languageLevelCodeList", empInfoSer.getCodeList("1401",
				request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewCompetenceInfo", modelMap);
	}

	/**
	 * 添加资格信息(add addCompetenceInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCompetenceInfo")
	@ResponseBody
	public Map<String, Object> addCompetenceInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addCompetenceInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			map.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// map.put("navTabId", "hr0111");
			// map.put("forwardUrl","/hrm/empinfo/viewCompetence?PERSON_ID=" +
			// request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到修改资格信息(Turn to modify Competence information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateCompetenceInfo")
	public ModelAndView updateCompetenceInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("qualificationList", empInfoSer
				.getQualificationList(request));
		modelMap.put("languageLevelList", empInfoSer
				.getLanguageLevelList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/updateCompetenceInfo", modelMap);

	}

	/**
	 * 修改资格信息(Modify Competence information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editCompetenceInfo")
	@ResponseBody
	public Map editCompetenceInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editCompetenceInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			map.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// map.put("navTabId", "hr0111");
			// map.put("forwardUrl","/hrm/empinfo/viewCompetence?PERSON_ID=" +
			// request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 转到删除资格信息(Turn to delete Competence )
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCompetenceInfo")
	public ModelAndView deleteCompetenceInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("qualificationList", empInfoSer
				.getQualificationList(request));
		modelMap.put("languageLevelList", empInfoSer
				.getLanguageLevelList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteCompetenceInfo", modelMap);

	}

	/**
	 * 删除资格信息(delete CompetenceInfo)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCompetence")
	@ResponseBody
	public Map deleteCompetence(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		// System.out.println( request.getParameter("PERSON_ID"));
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		int result = this.empInfoSer.deleteCompetenceInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功

			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0111");
			// map.put("forwardUrl","/hrm/empinfo/viewPersonalInfo?PERSON_ID=" +
			// request.getParameter("PERSON_ID"));

			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
			// map.put("forwardUrl","/hrm/empinfo/viewCompetence?PERSON_ID=" +
			// request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;

	}

	/**
	 * 特殊事项(Special matters)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "viewAdditional")
	public ModelAndView viewAdditionalList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("additionalList", empInfoSer.getAdditionalList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewAdditional");

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2552"));

		return new ModelAndView("/hrm/empinfo/viewAdditional", modelMap);
	}

	/**
	 * 转到添加特殊事项(Turn to add a special matters)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAdditionalInfo")
	public ModelAndView viewAdditionalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_NAME", admin.getLocalName());
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("infoTypeCodeList", empInfoSer.getCodeList("14903",
				request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewAdditionalInfo", modelMap);
	}

	/**
	 * 添加特殊事项(add special matters)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAdditionalInfo")
	@ResponseBody
	public Map<String, Object> addAdditionalInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addAdditionalInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0110");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到修改特殊事项(Turn to modify special matters)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateAdditionalInfo")
	public ModelAndView updateAdditionalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("additionalList", empInfoSer.getAdditionalList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/updateAdditionalInfo", modelMap);

	}

	/**
	 * 修改特殊事项(Modify special matters)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editAdditionalInfo")
	@ResponseBody
	public Map editAdditionalInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editAdditionalInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0110");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 转到删除特殊事项(Turn to special matters)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAdditional")
	public ModelAndView deleteAdditionalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("additionalList", empInfoSer.getAdditionalList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteAdditional", modelMap);
	}

	/**
	 * 删除特殊事项(delete special matters)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAdditionalInfo")
	@ResponseBody
	public Map deleteAdditionalInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteAdditionalInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0110");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 账户(account)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "viewAccount")
	public ModelAndView viewAccountList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("accountList", empInfoSer.getAccountList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewAccount");

		return new ModelAndView("/hrm/empinfo/viewAccount", modelMap);
	}

	/**
	 * 合同/档案(contract/files)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewContract")
	public ModelAndView getContractInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("contracList", empInfoSer.getContractList(request));
		modelMap.put("fileList", empInfoSer.getFileList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewContract");

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2491"));

		return new ModelAndView("/hrm/empinfo/viewContract", modelMap);
	}

	/**
	 * 转到添加档案(Turn to add a file)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewContractInfo")
	public ModelAndView viewContractInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("fileTypeList", empInfoSer.getCodeList("4577", request));
		modelMap.put("fileRelationCodeList", empInfoSer.getCodeList("1384",
				request));
		modelMap.put("fileAreaList", empInfoSer.getCodeList("4578", request));
		modelMap
				.put("fileInfoYnList", empInfoSer.getCodeList("14892", request));
		return new ModelAndView("/hrm/empinfo/viewContractInfo", modelMap);
	}

	/**
	 * 添加档案(add addFileInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addFileInfo")
	@ResponseBody
	public Map<String, Object> addFileInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addFileInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0114");
			// map.put("forwardUrl","/hrm/empinfo/viewContract?PERSON_ID=" +
			// request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到修改档案信息(Turn to modify file information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateFileInfo")
	public ModelAndView updateFileInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("fileList", empInfoSer.getFileList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/updateFileInfo", modelMap);

	}

	/**
	 * 修改档案信息(Modify file information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editFileInfo")
	@ResponseBody
	public Map editFileInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editFileInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			// map.put("navTabId", "hr0114");
			// map.put("forwardUrl","/hrm/empinfo/viewContract?PERSON_ID=" +
			// request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			map.put("navTabId", "hr0101");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 转到删除档案(Turn to delete file)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFile")
	public ModelAndView deleteFile(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("fileList", empInfoSer.getFileList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/deleteFile", modelMap);
	}

	/**
	 * 删除档案(delete file)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFileInfo")
	@ResponseBody
	public Map deleteFileInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteFileInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0114");
			// map.put("forwardUrl","/hrm/empinfo/viewContract?PERSON_ID=" +
			// request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 出国信息(goAbroad information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewGoAbroad")
	public ModelAndView viewGoAbroadList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("goAbroadList", empInfoSer.getGoAbroadList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewGoAbroad");

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "3676"));

		return new ModelAndView("/hrm/empinfo/viewGoAbroad", modelMap);
	}

	/**
	 * 转到添加出国信息(Turn to add goabroad)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewGoAbroadInfo")
	public ModelAndView viewGoAbroadInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("countryList", empInfoSer.getCodeList("870", request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewGoAbroadInfo", modelMap);
	}

	/**
	 * 添加出国信息(add goAbroadInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addGoAbroadInfo")
	@ResponseBody
	public Map<String, Object> addGoAbroadInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addGoAbroadInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0115");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到修改出国信息(Turn to modify goabroad information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateGoAbroadInfo")
	public ModelAndView updateGoAbroadInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("goAbroadList", empInfoSer.getGoAbroadList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/updateGoAbroadInfo", modelMap);

	}

	/**
	 * 修改出国信息(Modify file goAbroad information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editGoAbroadInfo")
	@ResponseBody
	public Map editGoAbroadInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editGoAbroadInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0115");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 转到删除特殊事项(Turn to special matters)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteGoAbroad")
	public ModelAndView deleteGoAbroad(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("goAbroadList", empInfoSer.getGoAbroadList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteGoAbroad", modelMap);
	}

	/**
	 * 删除出国信息(delete goAbroad information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteGoAbroadInfo")
	@ResponseBody
	public Map deleteGoAbroadInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteGoAbroadInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0115");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 证照信息(credential information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCredential")
	public ModelAndView viewCredential(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("credentialList", empInfoSer.getCredentialList(request));

		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewCredential");

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "3690"));

		return new ModelAndView("/hrm/empinfo/viewCredential", modelMap);
	}

	/**
	 * 转到添加证照信息(Turn to add credential)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCredentialInfo")
	public ModelAndView viewCredentialInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("credentialList", empInfoSer.getCodeList("4297", request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewCredentialInfo", modelMap);
	}

	/**
	 * 添加证照信息(add credential)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCredentialInfo")
	@ResponseBody
	public Map<String, Object> addCredentialInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addCredentialInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0116");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到修改证照信息(Turn to modify credential information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateCredentialInfo")
	public ModelAndView updateCredentialInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("credentialList", empInfoSer.getCredentialList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/updateCredentialInfo", modelMap);

	}

	/**
	 * 修改证照信息(Modify file credential)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editCredentialInfo")
	@ResponseBody
	public Map editCredentialInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editCredentialInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0116");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 转到删除特殊事项(Turn to special matters)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCredential")
	public ModelAndView deleteCredential(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("credentialList", empInfoSer.getCredentialList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteCredential", modelMap);
	}

	/**
	 * 删除证照信息(delete credential information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCredentialInfo")
	@ResponseBody
	public Map deleteCredentialInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteCredentialInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0116");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 根绝EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
	 * 
	 * @param request
	 * @param response
	 * @param modelMapviewEmpIdList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpIdList")
	public ModelAndView viewEmpIdList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		modelMap.put("sortNameNoList", this.empInfoSer.getOrderParmList(
				request, "18706"));
		modelMap.put("turn_to_url", request.getParameter("turnToUrl"));

		modelMap.put("empList", this.empInfoSer.getEmpIdList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.empInfoSer.getEmpIdListCnt(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/hrm/empinfo/viewEmpIdList", modelMap);
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonCnt")
	@ResponseBody
	public Map getPersonCnt(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		List pidEidList = this.empInfoSer.getPidEidList(request);

		map.put("perCnt", pidEidList.size());
		if (pidEidList.size() == 1) {
			map.put("personId", ((Map) pidEidList.get(0)).get("PERSON_ID"));
			map.put("empId", ((Map) pidEidList.get(0)).get("EMPID"));
		}

		return map;
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonCnt2")
	@ResponseBody
	public Map getPersonCnt2(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		List pidEidList = this.empInfoSer.getPidEidList2(request);

		map.put("perCnt", pidEidList.size());
		if (pidEidList.size() == 1) {
			map.put("personId", ((Map) pidEidList.get(0)).get("PERSON_ID"));
			map.put("empId", ((Map) pidEidList.get(0)).get("EMPID"));
		}

		return map;
	}

	/**
	 * 跳转到上传照片页面(turn to phothChange)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/phothChange")
	public ModelAndView phothChange(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("EMPID", request.getParameter("EMPID"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));

		return new ModelAndView("/hrm/empinfo/phothChange", modelMap);
	}

	/**
	 * 上传照片(Upload Photo)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadPhoto")
	public ModelAndView uploadPhoto(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		String cpnyId = request.getParameter("CPNY_ID");
		String EMPID = request.getParameter("EMPID");

		String sign = "1";
		try {
			// byte[] image = null;
			uploadpic up = new uploadpic(request, response);

			if (up.getdata()) {
				// String fileName = "";
				up.initFileComents();
				up.disposeData(EMPID);
				up.deletefile(cpnyId, EMPID + ".jpg");
				up.setFilePath("", cpnyId);
				sign = up.WriteMdata();
			}

			FtpUploadFileSample ftpUpload = new FtpUploadFileSample(request,
					response);

			sign = ftpUpload.upload(cpnyId, EMPID);
			String path = config.getString("hrm.photo.path") != null ? config
					.getString("hrm.photo.path") : "";

			if ("1".equals(sign)) {
				int returnInt = this.empInfoSer.editPhotoPath(request, path);

				if (returnInt == 1) {
					modelMap.put("sign", "1");
					modelMap.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"hr.alert.message.upload_success", request));// 上传成功
					modelMap.put("navTabId", "hr0101");
					modelMap.put("forwardUrl",
							"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
									+ request.getParameter("PERSON_ID")
									+ "&navTabId=hr0101");
				} else {
					modelMap.put("sign", "fail");
				}
			} else if ("-1".equals(sign)) {
				modelMap.put("sign", "-1");
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.create_file_fail", request));// 文件创建失败
				modelMap.put("navTabId", "hr0101");
				modelMap.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=hr0101");
			} else if ("-2".equals(sign)) {
				modelMap.put("sign", "-2");
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.file_transfer_failure", request));// 文件传输失败
				modelMap.put("navTabId", "hr0101");
				modelMap.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=hr0101");
			} else if ("-3".equals(sign)) {
				modelMap.put("sign", "-3");
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.potos_uploaded_failure", request));// 照片上传失败
				modelMap.put("navTabId", "hr0101");
				modelMap.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=hr0101");
			} else if ("-4".equals(sign)) {
				modelMap.put("sign", "-4");
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.portal_uploaded_failure", request));// 上传portal失败
				modelMap.put("navTabId", "hr0101");
				modelMap.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=hr0101");
			} else if ("-5".equals(sign)) {
				modelMap.put("sign", "-5");
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.photo_size", request));// 照片文件超过规定2M,上传失败
				modelMap.put("navTabId", "hr0101");
				modelMap.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=hr0101");
			} else {
				modelMap.put("sign", "0");
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"hr.alert.message.not_upload_file", request));// 文件未上传
				modelMap.put("navTabId", "hr0101");
				modelMap.put("forwardUrl",
						"/hrm/empinfo/viewPersonalInfo?PERSON_ID="
								+ request.getParameter("PERSON_ID")
								+ "&navTabId=hr0101");
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("sign", "fail");
		}
		return new ModelAndView("/hrm/empinfo/uploadPhoto", modelMap);
	}

	/**
	 * 转到添加残疾信息(Turn to add disabled information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewDisabledInfo")
	public ModelAndView viewDisabledInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("disabledTypeList", empInfoSer.getCodeList("123264",
				request));
		modelMap.put("disabilityLevelList", empInfoSer.getCodeList("123279",
				request));
		modelMap.put("disabilityValidityList", empInfoSer.getCodeList("123462",
				request));

		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewDisabledInfo", modelMap);
	}

	/**
	 * 添加残疾信息(add DisabledInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDisabledInfo")
	@ResponseBody
	public Map<String, Object> addDisabledInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.addDisabledInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到删除健康信息(Turn to delete disabled information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteDisabled")
	public ModelAndView deleteDisabled(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("disabledList", empInfoSer.getDisabilityinfoList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteHealth", modelMap);
	}

	/**
	 * 删除残疾信息(delete disabled information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteDisabledInfo")
	@ResponseBody
	public Map deleteDisabledInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteDisabledInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 转到修改残疾信息(Turn to modify disabled information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateDisabledInfo")
	public ModelAndView updateDisabledInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("disabledList", empInfoSer.getDisabilityinfoList(request));
		modelMap.put("disabilityValidityList", empInfoSer.getCodeList("123462",
				request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/updateDisabledInfo", modelMap);
	}

	/**
	 * 修改残疾信息(Modify Disabled information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editDisabledInfo")
	@ResponseBody
	public Map editDisabledInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editDisabledInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 转到添加工会信息(Turn to add health information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTradeunionInfo")
	public ModelAndView viewTradeunionInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("join_flag", empInfoSer.getJoinFlagByPersonId(request
				.getParameter("PERSON_ID")));
		modelMap.put("codeList", empInfoSer.getCodeList("123251", request));
		modelMap.put("paymentStatusList", empInfoSer.getCodeList("123224",
				request));
		modelMap.put("paymentTypeList", empInfoSer.getCodeList("211654",
				request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewTradeunionInfo", modelMap);
	}

	/**
	 * 添加工会信息(add addTradeunionInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTradeunionInfo")
	@ResponseBody
	public Map<String, Object> addTradeunionInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addTradeunionInfo(request);
		if (result == 1) {

			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到删除工会信息(Turn to delete health information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTradeunion")
	public ModelAndView deleteTradeunion(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		// modelMap.put("healthList", empInfoSer.getHealthList(request));
		modelMap.put("tradeUnionList", empInfoSer.getTradeunionList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteTradeunion", modelMap);
	}

	/**
	 * 删除工会信息(delete health information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTradeunionInfo")
	@ResponseBody
	public Map deleteTradeunionInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteTradeunionInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 转到修改工会信息(Turn to modify health information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateTradeunion")
	public ModelAndView updateTradeunion(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("tradeUnionList", empInfoSer.getTradeunionList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/updateTradeunion", modelMap);

	}

	/**
	 * 修改工会信息(Modify trade information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editTradeunionInfo")
	@ResponseBody
	public Map editTradeunionInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editTradeunionInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 转到添加外国语信息(Turn to add LanguageLevel information)
	 * 
	 * @param r1equest
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewLanguageLevelInfo")
	public ModelAndView viewLanguageLevelInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList", empInfoSer.getCodeList("123192", request));
		modelMap.put("examNameCodeList", empInfoSer
				.getCodeList("1394", request));
		modelMap.put("languageLevelCodeList", empInfoSer.getCodeList("1401",
				request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewLanguageLevelInfo", modelMap);
	}

	/**
	 * 添加外国语信息(add viewCompetenceInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addLanguageInfo")
	@ResponseBody
	public Map<String, Object> addLanguageInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addLanguageInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到修改外国语信息(Turn to modify language information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateLanguage")
	public ModelAndView updateLanguage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("examNameCodeList", empInfoSer
				.getCodeList("1394", request));
		modelMap.put("languageLevelList", empInfoSer
				.getLanguageLevelList(request));
		modelMap.put("languageLevelCodeList", empInfoSer.getCodeList("1401",
				request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/updateLanguage", modelMap);

	}

	/**
	 * 修改外国语信息(Modify lungaug information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editLanguageInfo")
	@ResponseBody
	public Map editLanguageInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.updateLanguageInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 转到删除外国语信息(Turn to delete Language information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteLanguage")
	public ModelAndView deleteLanguage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		// modelMap.put("healthList", empInfoSer.getHealthList(request));
		modelMap.put("languageLevelList", empInfoSer
				.getLanguageLevelList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteLanguage", modelMap);
	}

	/**
	 * 删除外国语信息(delete language information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteLanguageInfo")
	@ResponseBody
	public Map deleteLanguageInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteLanguageInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 转到修改毕业学校(Turn to modify education information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateEducation")
	public ModelAndView updateEducation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap
				.put("educationList", this.empInfoSer.getEducationList(request));

		// 专业
		// modelMap.put("subjectList",empInfoSer.getCodeList("741",request));
		// 学历
		modelMap.put("degreeCodeList", empInfoSer.getCodeList("1665", request));
		// 详细学历
		// modelMap.put("particularDegreeList",empInfoSer.getCodeList("123255",request));
		modelMap.put("subjectClassifyList", empInfoSer.getCodeList("123412",
				request));// 专业分类
		modelMap.put("siteProvinceList", empInfoSer
				.getCodeList("4602", request));// 所在地省
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/updateEducation", modelMap);
	}

	/**
	 * 修改毕业信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yorio youjia@ait.net.cn
	 * @date Jul 12, 2013 4:53:38 PM
	 * @version V1.0
	 */
	@RequestMapping(value = "/editEducation")
	@ResponseBody
	public Map editEducation(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editEducation(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 通过人力关联表查询出关联的下拉列表 hr_human_relevance
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author liangwei@ait.net.cn
	 * @date 2013-8-16 下午3:50:04
	 * @version V1.0
	 */

	@RequestMapping(value = "/getRelevance")
	@ResponseBody
	public Map getRelevance(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List getRelevanceList = empInfoSer.getRelevance(request);

		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		for (int i = 0; i < getRelevanceList.size(); i++) {
			map.put((String) ((Map) getRelevanceList.get(i)).get("CODE_NO"),
					((Map) getRelevanceList.get(i)).get("NAME"));
		}
		return map;

	}

	/**
	 * ajax访问后台 用来加载ait标签
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author liangwei@ait.net.cn
	 * @date 2013-8-19 下午6:25:51
	 * @version V1.0
	 */

	@RequestMapping(value = "/selectTag")
	public String selectTag(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		request.setAttribute("count", request.getParameter("count"));
		request.setAttribute("name", request.getParameter("name"));
		return "/hrm/empinfo/selectTag";
	}

	/**
	 * 2014-04-10 测试讲解使用 Test(TrainingInfo)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTest")
	public ModelAndView getViewTest(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("trainingInfoList", empInfoSer
				.getTrainingInfoList(request));
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "viewTraining");

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "123194"));

		return new ModelAndView("/hrm/empinfo/viewTest", modelMap);
	}

	/**
	 * Test(add CycleParam View)
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTest")
	public ModelAndView addTest(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpnyID", admin.getCpnyId());
		return new ModelAndView("/hrm/empinfo/addTest", modelMap);
	}

	/**
	 * 保存区间参数信息(add Cycle ParamInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTestInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map addTestInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		// int unique = this.cycleSer.checkCycleInfoUnique(request);

		// int result = this.cycleSer.addCycleParamInfo(request);
		int result = this.empInfoSer.addTestInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");

			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "test0000");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 黑色档案首页
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewBadArchivesList")
	public ModelAndView viewBadArchivesList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("codeList", empInfoSer.getCodeList("125239", request));
		modelMap.put("archiveList", empInfoSer.getBadArchivesList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer
				.getBadArchivesListCnt(request));
		String tabsSelected = request.getSession()
				.getAttribute("TABS_SELECTED") == null ? "0" : request
				.getSession().getAttribute("TABS_SELECTED").toString();
		modelMap.put("tabsSelected", tabsSelected);
		modelMap.put("toolbarInfo", toolMenuSer.getToolMenuForNo(request,
				"2550"));
		return new ModelAndView("/hrm/empinfo/viewBadArchivesList", modelMap);
	}

	/**
	 * 转到添加工会信息(Turn to add health information)viewBadArchinesInfo
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewBadArchivesInfo")
	public ModelAndView viewBadArchivesInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList", empInfoSer.getCodeList("125239", request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewBadArchivesInfo", modelMap);
	}

	/**
	 * 添加或修改黑色档案(单个)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAddBadArchivesInfo")
	public ModelAndView viewAddBadArchivesInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("codeList", empInfoSer.getCodeList("125239", request));
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		return new ModelAndView("/hrm/empinfo/viewAddBadArchivesInfo", modelMap);
	}

	/**
	 * 添加或修改黑色档案(单个)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewUpdateBadArchivesInfo")
	public ModelAndView viewUpdateBadArchivesInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("codeList", empInfoSer.getCodeList("125239", request));
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		String ARCH_ID = request.getParameter("ARCH_ID");
		if (StringUtils.isNotBlank(ARCH_ID)) {
			modelMap.put("archive", empInfoSer.getArchivesInfo(request));
		}
		return new ModelAndView("/hrm/empinfo/viewUpdateBadArchivesInfo",
				modelMap);
	}

	/**
	 * 添加工会信息(add addBadArchivesInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateBadArchivesInfo")
	@ResponseBody
	public Map<String, Object> addOrUpdateBadArchivesInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String ID = request.getParameter("ID");
		int result = 0;
		if (StringUtils.isBlank(ID)) {
			result = this.empInfoSer.addBadArchivesInfo(request);
		} else {
			result = this.empInfoSer.editBadArchivesInfo(request);
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.Successful_operation",
					request));// 保存成功
			map.put("navTabId", "hr0108");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map
					.put("message", TipMessage.getTipMessage(
							"liang.alert.message.ess.trans.operation_failure",
							request));// 保存失败
		}
		return map;
	}

	/**
	 * 添加工会信息(add addBadArchivesInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBadArchivesInfo")
	@ResponseBody
	public Map<String, Object> addBadArchivesInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.addBadArchivesInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}

		return map;
	}

	/**
	 * 转到删除工会信息(Turn to delete health information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBadArchives")
	public ModelAndView deleteBadArchives(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		// modelMap.put("healthList", empInfoSer.getHealthList(request));
		modelMap.put("badArchivesList", empInfoSer.getBadArchivesList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteBadArchives", modelMap);
	}

	/**
	 * 删除工会信息(delete health information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBadArchivesInfo")
	@ResponseBody
	public Map deleteBadArchivesInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteBadArchivesInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			// map.put("navTabId", "hr0101");
			map.put("navTabId", "hr0108");
			// map.put("forwardUrl","/hrm/empinfo/viewPersonalInfo?PERSON_ID=" +
			// request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 转到修改工会信息(Turn to modify health information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateBadArchives")
	public ModelAndView updateBadArchives(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("badArchivesList", empInfoSer.getBadArchivesList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		modelMap.put("codeList", empInfoSer.getCodeList("125239", request));

		return new ModelAndView("/hrm/empinfo/updateBadArchives", modelMap);

	}

	/**
	 * 修改工会信息(Modify trade information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editBadArchivesInfo")
	@ResponseBody
	public Map editBadArchivesInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editBadArchivesInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 预转正信息查询(Staff foundation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/viewPerConversionList")
	public ModelAndView viewPerConversionList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2540"));

		// 预转正信息列表
		modelMap.put("perConversionList", empInfoSer
				.getPerConversionList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer
				.getPerConversionListCnt(request));
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/hrm/empinfo/viewPerConversionList", modelMap);
	}

	/**
	 * 员工基本信息查询(Staff foundation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewUpdateEmpProduct")
	public ModelAndView viewUpdateEmpProduct(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List productList = empInfoSer.getEmpProductList(request);
		List codeList = empInfoSer.getCodeList("211424", request);
		if (productList != null) {
			for (int i = 0; i < productList.size(); i++) {
				LinkedHashMap productMap = (LinkedHashMap) productList.get(i);
				for (int j = 0; j < codeList.size(); j++) {
					LinkedHashMap codeMap = (LinkedHashMap) codeList.get(j);
					if (productMap.get("PRODUCT_NO").toString().equals(
							codeMap.get("CODE_NO").toString())) {
						codeMap.put("SELECTED", 1);
						break;
					}
				}
			}
		}
		modelMap.put("productList", productList);
		modelMap.put("codeList", codeList);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return new ModelAndView("/hrm/empinfo/viewUpdateEmpProduct", modelMap);
	}

	/**
	 * 外国语导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author zhaozhiyong
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelTempLanguageDataList")
	public ModelAndView viewImportExcelTempLanguageDataList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List paTempLanguageTempList = this.empInfoSer
				.getTempLanguageTempList(request);
		int paTempLanguageTempCnt = this.empInfoSer.getTempLanguageTempCnt(
				request, "T");
		int errorCnt = this.empInfoSer.getTempLanguageTempCnt(request, "E");

		modelMap.put("paTempLanguageTempList", paTempLanguageTempList);
		modelMap.put("paTempSalesTempCnt", paTempLanguageTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paTempLanguageTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempLanguageTempCnt);
		return new ModelAndView(
				"/hrm/empinfo/viewImportExcelTempLanguageDataList", modelMap);
	}

	/**
	 * 残疾人信息导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author zhaozhiyong
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelTempDisableDataList")
	public ModelAndView viewImportExcelTempDisableDataList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List paTempDisabledTempList = this.empInfoSer
				.getTempDisabledTempList(request);
		int paTempDisabledTempCnt = this.empInfoSer.getTempDisabledTempCnt(
				request, "T");
		int errorCnt = this.empInfoSer.getTempDisabledTempCnt(request, "E");

		modelMap.put("paTempDisabledTempList", paTempDisabledTempList);
		modelMap.put("paTempDisabledTempCnt", paTempDisabledTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paTempDisabledTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempDisabledTempCnt);
		return new ModelAndView(
				"/hrm/empinfo/viewImportExcelTempDisableDataList", modelMap);
	}

	/**
	 * 紧急联系人信息导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author zhaozhiyong
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelTempContactDataList")
	public ModelAndView viewImportExcelTempContactDataList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List paTempContactTempList = this.empInfoSer
				.getTempcontactTempList(request);
		int paTempContactTempCnt = this.empInfoSer.getTempContactTempCnt(
				request, "T");
		int errorCnt = this.empInfoSer.getTempContactTempCnt(request, "E");

		modelMap.put("paTempContactTempList", paTempContactTempList);
		modelMap.put("paTempContactTempCnt", paTempContactTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paTempContactTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempContactTempCnt);
		return new ModelAndView(
				"/hrm/empinfo/viewImportExcelTempContactDataList", modelMap);
	}

	/**
	 * 辅助信息导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author zhaozhiyong
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelTempAssistDataList")
	public ModelAndView viewImportExcelTempAssistDataList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List paTempAssistTempList = this.empInfoSer
				.getTempAssistTempList(request);
		int paTempAssistTempCnt = this.empInfoSer.getTempAssistTempCnt(request,
				"T");
		int errorCnt = this.empInfoSer.getTempAssistTempCnt(request, "E");

		modelMap.put("paTempAssistTempList", paTempAssistTempList);
		modelMap.put("paTempAssistTempCnt", paTempAssistTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paTempAssistTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempAssistTempCnt);
		return new ModelAndView(
				"/hrm/empinfo/viewImportExcelTempAssistDataList", modelMap);
	}

	/**
	 * (辅助信息、残疾证、外国语、紧急联系人)excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author zhaozhiyong
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportInfoExcelTempData")
	@ResponseBody
	public Map submitImportInfoExcelTempData(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg = this.empInfoSer.submitImportInfoExcelTempData(request);
		if ("OK".equals(msg)) {
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");// 保存成功

			if (request.getParameter("accrual") == null) {
				jo.put("navTabId", "hrm4533");
			} else {
				jo.put("navTabId", "hr0601");
			}
			jo.put("callbackType", "closeCurrent");
		} else {
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");// 保存失败
		}
		return jo;

	}

	/**
	 * 综合数据信息导入报表导出（外国语、残疾证、）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadInfoByExcelData11")
	public void downloadInfoByExcelData11(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.getTemplateInfoByExcelData11(request,
				aliasNameList, list, mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet2(request, response, modelMap,
				sqlContentmap, aliasNameList, null, mapNameList, mapList, name);

	}

	/**
	 * 员工基本信息导入
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/importEmpInfo")
	public ModelAndView importEmpInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/hrm/empinfo/importEmpInfo", modelMap);
	}

	/**
	 * 员工基本信息导入结果展示页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/importEmpInfoTempList")
	public ModelAndView viewImportEmpInfoTempList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List itemList = empInfoSer.getEmpInfoTempList(request);
		int impTotalCnt = empInfoSer.getEmpInfoTempCnt(request, "T");
		int impErrCnt = empInfoSer.getEmpInfoTempCnt(request, "E");
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/hrm/empinfo/importEmpInfoTempList", modelMap);
	}

	/**
	 * 员工工作经历信息导入结果展示页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportExcelTempWorkExperienceList")
	public ModelAndView viewImportExcelTempWorkExperienceList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List itemList = empInfoSer.getWorkExperienceInfoTempList(request);
		int impTotalCnt = empInfoSer.getWorkExperienceInfoTempCnt(request, "T");
		int impErrCnt = empInfoSer.getWorkExperienceInfoTempCnt(request, "E");
		modelMap.put("itemList", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView(
				"/hrm/empinfo/viewImportExcelTempWorkExperienceList", modelMap);
	}

	/**
	 * 员工资格证信息导入结果展示页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportExcelTempQualList")
	public ModelAndView viewImportExcelTempQualList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List itemList = empInfoSer.getQualInfoTempList(request);
		int impTotalCnt = empInfoSer.getQualInfoTempCnt(request, "T");
		int impErrCnt = empInfoSer.getQualInfoTempCnt(request, "E");
		modelMap.put("itemList", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/hrm/empinfo/viewImportExcelTempQualList",
				modelMap);
	}

	/**
	 * 员工兼卖产品信息导入结果展示页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportProductDataList")
	public ModelAndView viewImportProductDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List itemList = empInfoSer.getProductInfoTempList(request);
		int impTotalCnt = empInfoSer.getProductInfoTempCnt(request, "T");
		int impErrCnt = empInfoSer.getProductInfoTempCnt(request, "E");
		modelMap.put("itemList", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/hrm/empinfo/viewImportProductDataList",
				modelMap);
	}

	/**
	 * 员工评价信息导入结果展示页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportExcelTempEvsList")
	public ModelAndView viewImportExcelTempEvsInfoList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List itemList = empInfoSer.getEvsInfoTempList(request);
		int impTotalCnt = empInfoSer.getEvsInfoTempCnt(request, "T");
		int impErrCnt = empInfoSer.getEvsInfoTempCnt(request, "E");
		modelMap.put("itemList", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/hrm/empinfo/viewImportExcelTempEvsList",
				modelMap);
	}

	/**
	 * 员工工会信息导入结果展示页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportExcelTempTradeUnionList")
	public ModelAndView viewImportExcelTempTradeUnionInfoList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List itemList = empInfoSer.getTradeUnionInfoTempList(request);
		int impTotalCnt = empInfoSer.getTradeUnionInfoTempCnt(request, "T");
		int impErrCnt = empInfoSer.getTradeUnionInfoTempCnt(request, "E");
		modelMap.put("itemList", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView(
				"/hrm/empinfo/viewImportExcelTempTradeUnionList", modelMap);
	}

	/**
	 * 员工基本信息导入结果导出
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewExportEmpInfoExcelTempList")
	@ResponseBody
	public void viewExportEmpInfoExcelTempList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号(必填)");
		aliasNameList.add("员工姓名(可为空)");
		aliasNameList.add("家庭住址");
		aliasNameList.add("籍贯");
		aliasNameList.add("民族");
		aliasNameList.add("政治面貌");
		aliasNameList.add("是否共产党员");
		aliasNameList.add("身高");
		aliasNameList.add("体重");
		aliasNameList.add("血型");
		aliasNameList.add("是否残疾");
		aliasNameList.add("招聘来源");
		aliasNameList.add("离职原因 ");
		aliasNameList.add("奖惩备注");
		aliasNameList.add("爱心基金支付方式");
		aliasNameList.add("是否支付爱心基金");
		aliasNameList.add("负担房租标志");
		aliasNameList.add("负担医疗费标志");
		aliasNameList.add("负担教育费标志");
		/* aliasNameList.add("人员类型(CHR)"); */
		aliasNameList.add("工作类型(CHR)");
		/* aliasNameList.add("人员类型生效日期"); */
		aliasNameList.add("福利地区(保险)");
		aliasNameList.add("工作地区");
		aliasNameList.add("劳动手册编号");
		aliasNameList.add("社外工龄 ");
		aliasNameList.add("福利地区(公积金)");
		aliasNameList.add("保险公司");
		aliasNameList.add("保险类型 ");
		aliasNameList.add("年假基准");
		aliasNameList.add("产品");
		aliasNameList.add("促销员所属");
		aliasNameList.add("星级级别");
		aliasNameList.add("是否兼卖");
		aliasNameList.add("是否共建促销员");
		aliasNameList.add("评价类型");
		aliasNameList.add("错误信息");
		List dataList = empInfoSer.getEmpInfoTempList(request);
		List list = new ArrayList();
		for (int i = 0; i < dataList.size(); i++) {
			LinkedHashMap valueMap = new LinkedHashMap();
			LinkedHashMap map = (LinkedHashMap) dataList.get(i);
			valueMap.put("CELL0", map.get("EMPID"));
			valueMap.put("CELL1", map.get("EMPNAME"));
			valueMap.put("CELL2", map.get("IDCARD_ADDR"));
			valueMap.put("CELL3", map.get("BORNPLACE_CODE"));
			valueMap.put("CELL4", map.get("NATION_CODE"));

			valueMap.put("CELL5", map.get("POLITY_CODE"));
			valueMap.put("CELL6", map.get("WHETHER_COMMUNIST"));
			valueMap.put("CELL7", map.get("HEIGHT"));
			valueMap.put("CELL8", map.get("WEIGHT"));
			valueMap.put("CELL9", map.get("BLOOD_TYPE"));

			valueMap.put("CELL10", map.get("DISABILITY_YN"));
			valueMap.put("CELL11", map.get("RECRUITMENT_SOURCE_TYPE"));
			valueMap.put("CELL12", map.get("LEAVE_REASON"));
			valueMap.put("CELL13", map.get("REMARK"));
			valueMap.put("CELL14", map.get("LOVE_FUND_PAYMENT_TYPE"));

			valueMap.put("CELL15", map.get("IF_PAYMENT_LOVE_FUND"));
			valueMap.put("CELL16", map.get("IF_PAYMENT_RENT"));
			valueMap.put("CELL17", map.get("IF_PAYMENT_MEDICAL"));
			valueMap.put("CELL18", map.get("IF_PAYMENT_EDUCATION"));
			/* valueMap.put("CELL19", map.get("EMP_TYPE_CODE")); */

			valueMap.put("CELL19", map.get("PROMTR_WORK_TP"));
			/* valueMap.put("CELL21", map.get("EMP_TYPE_START_DATE")); */
			valueMap.put("CELL20", map.get("INSRAREA_ID_NAME"));
			valueMap.put("CELL21", map.get("WORK_AREA"));
			valueMap.put("CELL22", map.get("MANUAL_NUM"));

			valueMap.put("CELL23", map.get("OUTER_WORK_YEAR"));
			valueMap.put("CELL24", map.get("INSRAREA_ID_INS_NAME"));
			valueMap.put("CELL25", map.get("INSURANCE_COMPANY"));
			valueMap.put("CELL26", map.get("INSURANCE_TYPE_CODE"));
			valueMap.put("CELL27", map.get("YY_VAC_STD_DATE"));

			valueMap.put("CELL28", map.get("PROD_TP"));
			valueMap.put("CELL29", map.get("PROMTR_TP"));
			valueMap.put("CELL30", map.get("STAR_TP"));
			valueMap.put("CELL31", map.get("PART_TIME_YN"));
			valueMap.put("CELL32", map.get("COMM_YN"));
			valueMap.put("CELL35", map.get("EVS_TYPE_NAME"));
			valueMap.put("CELL36", map.get("UPLOAD_ERROR_MSG") == null ? ""
					: map.get("UPLOAD_ERROR_MSG"));
			list.add(valueMap);
		}

		List mapNameList = new ArrayList();
		mapNameList.add("籍贯参考");
		mapNameList.add("民族参考");
		mapNameList.add("政治面貌参考");
		mapNameList.add("是否共产党员参考");
		mapNameList.add("血型参考");
		mapNameList.add("是否残疾参考");
		mapNameList.add("招聘来源参考");
		mapNameList.add("爱心基金支付方式参考");
		mapNameList.add("是否支付爱心基金参考");
		mapNameList.add("负担房租标志参考");
		mapNameList.add("负担医疗费标志参考");
		mapNameList.add("负担教育费标志参考");
		mapNameList.add("人员类型(CHR)参考");
		mapNameList.add("工作类型(CHR)参考");
		mapNameList.add("工作地区参考");
		mapNameList.add("福利地区参考");
		mapNameList.add("保险类型参考");
		mapNameList.add("产品参考");
		mapNameList.add("促销员所属参考");
		mapNameList.add("星级级别参考");
		mapNameList.add("是否兼卖参考");
		mapNameList.add("是否共建促销员参考");

		List mapList = new ArrayList();
		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		mapList.add(codeSql + "774");
		mapList.add(codeSql + "210942");
		mapList.add(codeSql + "210938");
		mapList.add(codeSql + "123224");
		mapList.add(codeSql + "4573");
		mapList.add(codeSql + "123224");
		mapList.add(codeSql + "3306");
		mapList.add(codeSql + "211654");

		mapList.add(codeSql + "123224");
		mapList.add(codeSql + "278667");
		mapList.add(codeSql + "123224");
		mapList.add(codeSql + "123224");
		mapList.add(codeSql + "1368");
		mapList.add(codeSql + "211554");
		mapList.add(codeSql + "211557");
		mapList.add(codeSql + "216736");
		mapList.add(codeSql + "483");
		mapList.add(codeSql + "211424");
		mapList.add(codeSql + "211837");
		mapList.add(codeSql + "215954");
		mapList.add(codeSql + "123224");
		mapList.add(codeSql + "123224");

		String name = "basicEmpInfo";
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,
				sqlContentmap, aliasNameList, null, mapNameList, mapList, name);
	}

	/**
	 * 员工基本信息由临时表提交到正是表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/createImportEmpInfoTempListExcel")
	@ResponseBody
	public int createImportEmpInfoTempListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String result = empInfoSer.importEmpInfoTempListExcel(request);
		return result.equals("OK") ? 1 : 0;
	}

	/**
	 * 员工基本信息模板下载
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadExcelTemplate")
	public void downloadExcelTemplate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.getTemplateInfo(request, aliasNameList, list,
				mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);

		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,
				sqlContentmap, aliasNameList, null, mapNameList, mapList, name);
	}

	/**
	 * 导入临时保存画面培训信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportTrainingDataList")
	public ModelAndView viewImportTrainingDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List trainingTempList = this.empInfoSer
				.getTrainingImportTempList(request);

		int impTotalCnt = empInfoSer.getTrainingImportTempCnt(request, "T");
		int impErrCnt = empInfoSer.getTrainingImportTempCnt(request, "E");
		modelMap.put("itemList", trainingTempList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/hrm/empinfo/viewImportTrainingDataList",
				modelMap);
	}

	/**
	 * 派遣地管理模版下载
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/exportPaiQianDiGuanLiMoBanModle")
	public void exportPaiQianDiGuanLiMoBanModle(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.getPaiQianDiGuanLiMoBanModleInfo(request,
				aliasNameList, list, mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);

		this.excelUtilSer.exportPaiQianDiModelMoreSheet(request, response,
				modelMap, sqlContentmap, aliasNameList, null, mapNameList,
				mapList, name);
	}

	/**
	 * 最低工资标准模版下载--非促销员
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanMoBanModle")
	public void exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanMoBanModle(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer
				.exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanMoBanModle(request,
						aliasNameList, list, mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,
				sqlContentmap, aliasNameList, null, mapNameList, mapList, name);

	}

	@RequestMapping(value = "/exportZuiDiGongZiBiaoZhunCuXiaoYuanMoBanModle")
	public void exportZuiDiGongZiBiaoZhunCuXiaoYuanMoBanModle(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.exportZuiDiGongZiBiaoZhunCuXiaoYuanMoBanModle(
				request, aliasNameList, list, mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,
				sqlContentmap, aliasNameList, null, mapNameList, mapList, name);

	}

	/**
	 * 派遣津贴标准模版下载
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/exportPaiQianDiJinTieBiaoZhunMoBanModle")
	public void exportPaiQianDiJinTieBiaoZhunMoBanModle(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.getPaiQianDiJinTieBiaoZhunMoBanModleInfo(
				request, aliasNameList, list, mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);

		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,
				sqlContentmap, aliasNameList, null, mapNameList, mapList, name);
	}

	/**
	 * 转到辅助信息viewBadArchinesInfo
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAssistInfo")
	public ModelAndView viewAssistInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/viewAssistInfo", modelMap);
	}

	/**
	 * 添加辅助信息(add addAssistInfo)
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAssistInfo")
	@ResponseBody
	public Map<String, Object> addAssistInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.addAssistInfo(request);
		if (result == 1) {

			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 转到辅助信息(Turn to delete health information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAssist")
	public ModelAndView deleteAssist(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		// modelMap.put("healthList", empInfoSer.getHealthList(request));
		modelMap.put("AssistList", empInfoSer.getAssistList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/hrm/empinfo/deleteAssist", modelMap);
	}

	/**
	 * 删除辅助信息(delete health information)
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAssistInfo")
	@ResponseBody
	public Map deleteAssistInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.deleteAssistInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0108");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/**
	 * 转到修改辅助信息(Turn to modify health information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateAssist")
	public ModelAndView updateAssist(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("AssistList", empInfoSer.getAssistList(request));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));
		modelMap.put("codeList", empInfoSer.getCodeList("125239", request));

		return new ModelAndView("/hrm/empinfo/updateAssist", modelMap);

	}

	/**
	 * 修改辅助信息(Modify trade information)
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editAssistInfo")
	@ResponseBody
	public Map editAssistInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editAssistInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 获取兼卖产品信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/viewEmpInfoList")
	public ModelAndView viewEmpInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag != null && !"".equals(firstFlag)) {
			// 员工信息查询
			modelMap.put("empInfo", empInfoSer.getEmpInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer
					.getEmpInfoListCnt(request));
		} else {
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}

		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil
				.isSuperUser(admin.getPersonId()));
		modelMap.put("defaultCpny",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));

		return new ModelAndView("/hrm/empinfo/viewEmpInfoList", modelMap);
	}

	// 综合简介查询弹出页面
	@RequestMapping(value = "/viewEmpInfoListTanchu")
	public ModelAndView viewEmpInfoTanchuList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		String searchChange = StringUtil.checkNull(request
				.getParameter("searchChange"));
		if (firstFlag != null && !"".equals(firstFlag)) {
			// 员工信息查询
			List elist = new ArrayList();
			if("SPC_SH".equals(admin.getCpnyId())){
				elist = empInfoSer.getEmpInfoSHList(request);
			} else {
				elist = empInfoSer.getEmpInfoList(request);
			}
			
			modelMap.put("empInfo", elist);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer
					.getEmpInfoListCnt(request));
		} else {
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		if ("TSTO".equals(admin.getCpnyId())) {
			if ("viewTxEmpTSTOList".equals(searchChange)) {
				modelMap.put("AR_MONTH", StringUtil.checkNull(request
						.getParameter("seach_AR_MONTH")));
			}
		}
		// 考勤搜搜
		if ("viewAttendanceManagentForSerchInfoList".equals(searchChange)) {
			modelMap.put("FROM_DATE", StringUtil.checkNull(request
					.getParameter("seach_FROM_DATE")));
			modelMap.put("TO_DATE", StringUtil.checkNull(request
					.getParameter("seach_TO_DATE")));
			modelMap.put("APPLY_CODE", StringUtil.checkNull(request
					.getParameter("APPLY_CODE")));
		}
		// 年假使用管理
		if ("viewVacEmpList".equals(searchChange)) {
			modelMap.put("YEAR", StringUtil.checkNull(request
					.getParameter("seach_YEAR")));
		}
		// 年假使用管理
		if ("viewTxEmpList".equals(searchChange)) {
			modelMap.put("AR_MONTH", StringUtil.checkNull(request
					.getParameter("seach_AR_MONTH")));
		}
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		/*
		 * String TANCHUEMPOFFICE =
		 * request.getParameter("TANCHUEMPOFFICE")==null
		 * ?"15119":request.getParameter("TANCHUEMPOFFICE");
		 * modelMap.put("TANCHUEMPOFFICE", TANCHUEMPOFFICE);
		 */
		modelMap.put("TANCHUDEPTNO", request.getParameter("TANCHUDEPTNO"));
		modelMap.put("authority", authorityUtil
				.isSuperUser(admin.getPersonId()));
		modelMap.put("searchChange",searchChange);
		modelMap.put("defaultCpny",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		modelMap.put("KEY", StringUtil.checkNull(request
				.getParameter("seach_KEY")));
		modelMap.put("PERSON_ID", StringUtil.checkNull(request
				.getParameter("PERSON_ID")));
		// 信息搜索用到的参数
		modelMap.put("dataSearch", StringUtil.checkNull(request
				.getParameter("dataSearch")));
		// 分页
		modelMap.put("pageNum", request.getParameter("pageNum"));
		modelMap.put("numPerPage", request.getParameter("numPerPage"));
		return new ModelAndView("/hrm/empinfo/viewEmpInfoListTanchu", modelMap);
	}

	/**
	 * 搜索弹出界面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchTanchu")
	public ModelAndView searchTanchu(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		String searchChange = StringUtil.checkNull(request
				.getParameter("searchChange"));
		if (firstFlag != null && !"".equals(firstFlag)) {
			// 员工信息查询
			String status = StringUtil
					.checkNull(request.getParameter("status"));
			if ("zhiji".equals(status)) {
				modelMap.put("empInfo", empInfoSer.zhiji(request));
			} else if ("chengbenzhongxin".equals(status)) {
				modelMap.put("empInfo", empInfoSer.chengbenzhongxin(request));
			} else if (status.indexOf("INFOR") > -1) {
				modelMap.put("empInfo", empInfoSer.zhijiInfor(request));
			} else {
				modelMap.put("empInfo", empInfoSer.searchTanchu(request));
			}

			modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer
					.getEmpInfoListCnt(request));
		} else {
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("status", StringUtil.checkNull(request
				.getParameter("status")));
		modelMap.put("nameid", StringUtil.checkNull(request
				.getParameter("nameid")));
		modelMap.put("typeFlag", StringUtil.checkNull(request
				.getParameter("typeFlag")));
		modelMap.put("idvalue", request.getParameter("idvalue"));
		return new ModelAndView("/hrm/empinfo/searchTanchu", modelMap);

	}

	/**
	 * 追加结果弹出界面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/additionalResultsTanchu")
	public ModelAndView additionalResultsTanchu(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		return new ModelAndView("/hrm/empinfo/additionalResultsTanchu",
				modelMap);

	}

	/**
	 * 搜索结果弹出界面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/employeeSearchResultsTanchu")
	public ModelAndView employeeSearchResultsTanchu(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		return new ModelAndView("/hrm/empinfo/employeeSearchResultsTanchu",
				modelMap);

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/employeeSearchResults")
	public ModelAndView employeeSearchResults(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List elist = empInfoSer.searchname(request);
		List list = empInfoSer.employeeSearchResultsTanchu(request);
		LinkedHashMap param = null;
		String str = "";
		if (elist != null && elist.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				param = (LinkedHashMap) list.get(i);
				LinkedHashMap eparam = null;
				int z = i + 1;
				String st = "<tr onclick='loadRenshika("
						+ param.get("PERSON_ID")
						+ ")' target='PERSON_ID' rel='"
						+ param.get("PERSON_ID") + "'><td >" + z + "</td>";
				String s = "";
				for (int y = 0; y < elist.size(); y++) {
					eparam = (LinkedHashMap) elist.get(y);
					String nameno = (String) eparam.get("NAME_NO");
					Object obj = param.get(nameno);
					if (obj == null) {
						obj = "";
					}
					String contractDate = "";
					if (nameno == "CONTRACT_DATE"||nameno.equals("CONTRACT_DATE")){
						String[] arrayToSort = obj.toString().split(",");
						Arrays.sort(arrayToSort);
						 for(int j=0;j<arrayToSort.length;j++){
							 if(j != arrayToSort.length-1){
								 contractDate = contractDate + arrayToSort[j] + ",";
							 }else{
								 contractDate = contractDate + arrayToSort[j];
							 }
							  
						 }
						s = s + "<td >" + contractDate.toString().replaceAll(",", "<br/>") + "</td>";
					}else{
						s = s + "<td >" + obj + "</td>";
					}

				}
				st = st + s + "</tr>";
				str = str + st;
				modelMap.put("searchnamelist", elist);
				modelMap.put("flag", "1");

			}
		} else {
			String ss = "";
			for (int i = 0; i < list.size(); i++) {
				param = (LinkedHashMap) list.get(i);
				Object name = param.get("LOCAL_NAME");
				Object empid = param.get("EMPID");
				if (name == null) {
					name = "";
				}
				if (empid == null) {
					empid = "";
				}
				int y = i + 1;
				ss = ss + "<tr><td >" + y + "</td><td >" + name + "</td>"
						+ "<td >" + empid + "</td></tr>";
			}
			str = str + ss;
			modelMap.put("flag", "2");
		}
		modelMap.put("titleName", request.getParameter("titleName"));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		modelMap.put("LOCAL_NAME", request.getParameter("LOCAL_NAME"));
		modelMap.put("EMPID", request.getParameter("EMPID"));
		modelMap.put("DUTY_NO", request.getParameter("DUTY_NO"));
		modelMap.put("POST_FAMILY", request.getParameter("POST_FAMILY"));
		modelMap.put("POST_GRADE_NO", request.getParameter("POST_GRADE_NO"));
		modelMap.put("MAIN_BUSINESS", request.getParameter("MAIN_BUSINESS"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
		modelMap.put("NATIONALITY_CODE", request.getParameter("NATIONALITY_CODE"));
		modelMap.put("SAFE_TYPE_CODE", request.getParameter("SAFE_TYPE_CODE"));
		modelMap.put("EMPLOYEE_OWNED", request.getParameter("EMPLOYEE_OWNED"));
		modelMap.put("POSITION_NO", request.getParameter("POSITION_NO"));
		modelMap.put("EMP_OFFICE", request.getParameter("EMP_OFFICE"));
		modelMap.put("RECRUIT_TYPE", request.getParameter("RECRUIT_TYPE"));
		modelMap.put("PRODUCT_TYPE", request.getParameter("PRODUCT_TYPE"));
		modelMap.put("NATION_CODE", request.getParameter("NATION_CODE"));
		modelMap.put("DEGREE_CODE", request.getParameter("DEGREE_CODE"));
		modelMap.put("SEXCODE", request.getParameter("SEXCODE"));
		modelMap.put("REG_TYPE_CODE", request.getParameter("REG_TYPE_CODE"));
		modelMap.put("DOB_START_DATE", request.getParameter("DOB_START_DATE"));
		modelMap.put("DOB_END_DATE", request.getParameter("DOB_END_DATE"));
		modelMap.put("BIRTHDAY", request.getParameter("BIRTHDAY"));
		modelMap.put("age_start", request.getParameter("age_start"));
		modelMap.put("age_end", request.getParameter("age_end"));
		modelMap.put("DATE_STARTED", request.getParameter("DATE_STARTED"));
		modelMap.put("DATE_END", request.getParameter("DATE_END"));
		modelMap.put("DATE_LEFT_START", request.getParameter("DATE_LEFT_START"));
		modelMap.put("DATE_LEFT_END", request.getParameter("DATE_LEFT_END"));
		modelMap.put("START_PROBATION_DATE", request.getParameter("START_PROBATION_DATE"));
		modelMap.put("END_PROBATION_DATE", request.getParameter("END_PROBATION_DATE"));
		modelMap.put("searchResultsList", str);
		modelMap.put("searchResultsListCount", list.size());
		return new ModelAndView("/hrm/empinfo/employeeSearchResults", modelMap);

	}

	@RequestMapping(value = "/viewTempEmpInfoList")
	public ModelAndView viewTempEmpInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag != null && !"".equals(firstFlag)) {
			// 员工信息查询
			List empInfo = empInfoSer.viewSpecialMatter(request);
			modelMap.put("empInfo",empInfo);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfo.size());
		} else {
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("INFOR_DIS_CODE", request.getParameter("INFOR_DIS_CODE"));
		modelMap.put("START_DATE", request.getParameter("START_DATE"));
		modelMap.put("END_DATE", request.getParameter("END_DATE"));
		//modelMap.put("empTypeList", empInfoSer.getEmpTypeList(request));
		//modelMap.put("positionList", empInfoSer.getPositionList(request));
		//modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny"));

		return new ModelAndView("/hrm/empinfo/viewTempEmpInfoList", modelMap);
	}

	/**
	 * 修改兼卖产品信息(Modify trade information)
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editProductInfo")
	@ResponseBody
	public Map editProductInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.empInfoSer.editProductInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "hr0101");
			map.put("forwardUrl", "/hrm/empinfo/viewPersonalInfo?PERSON_ID="
					+ request.getParameter("PERSON_ID") + "&navTabId=hr0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		}
		return map;
	}

	/**
	 * 根据法人获取人员类型组
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/getEmpTypeGroup")
	@ResponseBody
	public Map getEmpTypeGroup(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		List result = this.empInfoSer.getEmpTypeGroup(request);
		map.put("statusCode", "200");
		map.put("result", result);
		return map;
	}

	/**
	 * 员工工作经历excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author chenfeifei
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelWorkExperienceData")
	@ResponseBody
	public Map submitImportExcelWorkExperienceData(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg = this.empInfoSer
				.submitImportExcelWorkExperienceData(request);
		if ("OK".equals(msg)) {
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");// 保存成功

			if (request.getParameter("accrual") == null) {
				jo.put("navTabId", "hrm4533");
			} else {
				jo.put("navTabId", "hr0601");
			}
			jo.put("callbackType", "closeCurrent");
		} else {
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");// 保存失败
		}
		return jo;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadWorkExperienceExcelTemplateByExcelData")
	public void downloadWorkExperienceExcelTemplateByExcelData(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.getWorkTemplateInfoByExcelData(request,
				aliasNameList, list, mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,
				sqlContentmap, aliasNameList, null, mapNameList, mapList, name);

	}

	/**
	 * 临时职批量入职列表
	 */
	@RequestMapping(value = "/viewTmpEmpBatchList")
	public ModelAndView viewTmpEmpBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		List viewRegisterInfoList = this.recruitManageSer.viewRegisterInfoList(request,3);
		modelMap.put("viewRegisterInfoList", viewRegisterInfoList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("admin", admin);

		if (!paramMap.containsKey("FROM_TIME")) {
			paramMap.put("FROM_TIME", DateUtil.getCurrentMonthStrFormat()
					+ "-01");
			paramMap.put("TO_TIME", DateUtil.getSysdateStr());
		}

		modelMap.put("searchMap", paramMap);

		String seach_FIRST_FLAG = request.getParameter("seach_FIRST_FLAG");
		List viewTmpEmpBatchList = empInfoSer.getTmpEmpAffirmInfoListBatch(request);
		modelMap.put("tmpEmpAffirmList", viewTmpEmpBatchList);
		modelMap.put("viewTmpEmpBatchListCnt", viewTmpEmpBatchList.size());

		return new ModelAndView("/hrm/empinfo/viewTmpEmpBatchList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteTmpEmpBatchInfo")
	@ResponseBody
	public Map deleteTmpEmpBatchInfo(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.updateEmpinfo(request, "deleteTmpEmpBatchInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message",TipMessage.getTipMessage("alert.message.delete_success", request) );//"删除成功"
			map.put("formId", "viewTmpEmpBatchListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message",TipMessage.getTipMessage("alert.message.delete_fail", request) );//"删除失败"
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateTmpEmpBatchInfo")
	@ResponseBody
	public Map updateTmpEmpBatchInfo(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.updateEmpinfo(request, "updateTmpEmpBatchInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message",TipMessage.getTipMessage("alert.message.update_success", request) );//"删除成功"
			map.put("formId", "viewTmpEmpBatchListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message",TipMessage.getTipMessage("alert.message.update_fail", request) );//"删除失败"
		}
		return map;
	}

	/**
	 * 临时职批量导入模板
	 */
	@RequestMapping(value = "/expEmpBatchTemp")
	public void expEmpBatchTemp(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.getEmpBatchTemp(request, aliasNameList, list,
				mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);

		this.excelUtilSer.exportExcelMoreSheetAndMoreContent(request, response,
				modelMap, sqlContentmap, aliasNameList, null, mapNameList,
				mapList, name);
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	@RequestMapping(value = "/viewImportTempEmpResultList")
	public ModelAndView viewImportTempEmpResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		searchMap.put("SUBSD_CD", admin.getCpnyId());

		List itemList = empInfoSer
				.getImportTmpEmpResultList(request, searchMap);
		int impTotalCnt = empInfoSer.getImportTmpEmpResultCnt(request,
				searchMap);
		int impErrCnt = empInfoSer.getImportTmpEmpErrCnt(request, searchMap);
		modelMap.put("item", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "300105"));
		return new ModelAndView("/hrm/empinfo/viewImportTempEmpResultList",
				modelMap);
	}

	@RequestMapping(value = "/createImportTmpEmpResult")
	@ResponseBody
	public int createImportTmpEmpResult(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String result = empInfoSer.importTmpEmpFromExcel(request, response,
				modelMap);
		return result.equals("OK") ? 1 : 0;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewImportTmpEmpResultExcel")
	public ModelAndView viewImportTmpEmpResultExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List itemList = empInfoSer.getImportTmpEmpFromExcel(request);
		modelMap.put("itemList", itemList);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/hrm/empinfo/viewImportTmpEmpResultExcel",
				modelMap);
	}

	@RequestMapping(value = "/tempEmpApplyInBatch")
	@ResponseBody
	public Map<String, Object> tempEmpApplyInBatch(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String msg = "删除";
		String op_flag = request.getParameter("BATCH_EMP_OP_FLAG");
		if ("1".equals(op_flag)) {
			msg = "提交";
		}

		try {
			result = empInfoSer.delTmpEmpInBatch(request);
			if (result == 1) {
				map.put("navTabId", "hr0517");
				map.put("message", msg + "成功！");
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", msg + "失败！");// "批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	@RequestMapping(value = "/viewTempEmpBatchReqList")
	public ModelAndView viewTempEmpBatchReqList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		searchMap.put("SUBSD_CD", admin.getCpnyId());
		List itemList = empInfoSer.viewTempEmpBatchReq(request);
		int totalCnt = empInfoSer.getTempEmpBatchReqCnt(request);
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("itemList", itemList);
		modelMap.put("totalCnt", totalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, totalCnt);
		modelMap.put("affirmorList", transactionViewSer.getApplyFeeList("",
				request));
		String ls = "";
		String[] isChecked = request.getParameterValues("tempEmpCheck");
		if (isChecked != null) {
			for (int i = 0; i < isChecked.length; i++) {
				ls = ls.equals("") ? isChecked[i].toString()
						: (ls + "," + isChecked[i].toString());
			}
		}
		modelMap.put("BATCH_NOS", ls);

		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "300105"));
		return new ModelAndView("/hrm/empinfo/viewTempEmpBatchReqList",
				modelMap);
	}

	@RequestMapping(value = "/confirmTempEmpBatch")
	@ResponseBody
	public Map confirmTempEmpBatch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();

		retMap = transferOrderSer.confirmTempEmpBatch(request);
		String result = retMap.get("RET").toString();

		if (result.equals("1")) {
			map.put("statusCode", "200");
			map.put("message", "提交申请成功");
			map.put("navTabId", "hr0517");
		} else {
			map.put("statusCode", "300");
			map.put("message", retMap.get("MESSAGE").toString());
		}
		return map;
	}

	@RequestMapping(value = "/viewTempEmpBatchAffirmList")
	public ModelAndView viewTempEmpBatchAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);

		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());

		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());

		List fileList = new ArrayList();
		LinkedHashMap paraMap = new LinkedHashMap();
		paraMap.put("APPLY_TYPE", "23292329");
		paraMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		fileList = infoApplySerOt.getEssFileList(paraMap);

		modelMap.put("itemList", empInfoSer.getTempEmpBatchAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer
				.getTempEmpBatchAffirmCnt(request));
		modelMap.put("BATCH_NO", request.getParameter("BATCH_NO"));
		modelMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		modelMap.put("fileList", fileList);

		return new ModelAndView("/hrm/empinfo/viewTempEmpBatchAffirmList",
				modelMap);
	}

	@RequestMapping(value = "/viewTempEmpReqDetail")
	public ModelAndView viewTempEmpReqDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map map = ObjectBindUtil.getRequestParamData(request);

		List affirmorList = new ArrayList();
		List checkorList = new ArrayList();
		List fileList = new ArrayList();
		if (map.get("APPLY_NO") != null && !map.get("APPLY_NO").equals("")) {
			affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
			checkorList = infoApplySer.getCheckorByApplyNoList(request);
			LinkedHashMap paraMap = new LinkedHashMap();
			paraMap.put("APPLY_TYPE", "23292329");
			paraMap.put("APPLY_NO", map.get("APPLY_NO"));
			fileList = infoApplySerOt.getEssFileList(paraMap);
		}

		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		modelMap.put("fileList", fileList);

		modelMap.put("personInfo", empInfoSer.getTempEmpReqDetail(request));
		modelMap.put("codeList", empInfoSer.getEmpProductList(request));

		return new ModelAndView("/hrm/empinfo/viewTempEmpReqDetail", modelMap);
	}

	private static final long serialVersionUID = -8694640030455344419L;

	@RequestMapping(value = "/execute")
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 生成的ZIP文件名为Demo.zip
		String tmpFileName = "Photo.zip";
		byte[] buffer = new byte[1024];
		try {
			String url = request.getParameter("fileName");
			String downname = StringUtil
					.checkNull(request.getParameter("file"));
			String[] arrayUrl = url.split(",");
			String[] downFileName = downname.split(",");
			String FilePath = "";
			ZipOutputStream out = null;
			// 需要同时下载的两个文件result.txt ，source.txt
			if (arrayUrl != null && arrayUrl.length > 0) {
				for (int i = 0; i < arrayUrl.length; i++) {
					String fileurl1 = request.getRealPath("") + arrayUrl[i];
					String fileurl = fileurl1.substring(0, fileurl1
							.indexOf(admin.getCpnyId() + "/"))
							+ admin.getCpnyId() + "/";
					fileurl = fileurl.replace("\\", "/");
					FilePath = fileurl;
					String strfiname = fileurl + downFileName[i].trim();
					// response.setHeader("Content-Disposition",
					// "attachment;filename="+
					// URLEncoder.encode("ss_"+String.valueOf(i)+".jpg",
					// "UTF-8"));
					File nfile = new File(fileurl1);
					FileInputStream fis = new FileInputStream(nfile);

					if (i == 0) {
						String strZipPath = fileurl + tmpFileName;
						out = new ZipOutputStream(new FileOutputStream(
								strZipPath));
					}
					String str = nfile.getName();
					out.putNextEntry(new ZipEntry(downFileName[i]));
					// 设置压缩文件内的字符编码，不然会变成乱码
					out.setEncoding("GBK");
					int len;
					// 读入需要下载的文件的内容，打包到zip文件
					while ((len = fis.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					out.closeEntry();
					fis.close();
				}
			}
			out.close();
			this.downFile(response, tmpFileName, FilePath);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 文件下载
	 * 
	 * @param response
	 * @param str
	 */
	private void downFile(HttpServletResponse response, String str,
			String FilePath) {
		try {
			String path = FilePath + str;
			File file = new File(path);
			if (file.exists()) {
				InputStream ins = new FileInputStream(path);
				BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
				OutputStream outs = response.getOutputStream();// 获取文件输出IO流
				BufferedOutputStream bouts = new BufferedOutputStream(outs);
				response.setContentType("application/x-download");// 设置response内容的类型
				response.setHeader("Content-disposition",
						"attachment;filename="
								+ URLEncoder.encode(str, "UTF-8"));// 设置头部信息
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				// 开始向网络传输文件流
				while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
					bouts.write(buffer, 0, bytesRead);
				}
				bouts.flush();// 这里一定要调用flush()方法
				ins.close();
				bins.close();
				outs.close();
				bouts.close();
			} else {
				response.sendRedirect("../error.jsp");
			}
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int time = Integer.parseInt(format.format(date).replace("-", ""));

	}

	/**
	 * 人事信息卡查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */

	@RequestMapping(value = "/viewCardInfoList")
	public ModelAndView viewCardInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String firstFlag = request.getParameter("firstFlag");
		modelMap.put("language", Messages.getLanguage(request));
		if (firstFlag == null || "".equals(firstFlag)) {
			List viewCardInfoList = this.empInfoSer.viewCardInfoList(request);
			modelMap.put("viewCardInfoList", viewCardInfoList);
			modelMap.put("viewCardInfoListCnt", viewCardInfoList == null ? 0
					: viewCardInfoList.size());

			LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
					.viewHrPersonalInfo2(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo", linkMap);
		} else {
			modelMap.put("EMP_OFFICE", "'15119'");
			modelMap.put("EMP_OFFICE_NAME", TipMessage.getTipMessage(
					"hrm.empinfo.JOB", request));
			modelMap.put("START_DATE_JOIN", DateUtil.getMonthStrAgo(3,"dd/MM/yyyy"));
			modelMap.put("END_DATE_JOIN", DateUtil.getSysdateStr("dd/MM/yyyy"));
		}
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("zhijiCARD", request.getParameter("zhijiCARD"));
		return new ModelAndView("/hrm/empinfo/viewCardInfoList", modelMap);
	}

	/**
	 * 人事信息卡1查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/viewHTSVCardInfoList")
	public ModelAndView viewHTSVCardInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String firstFlag = request.getParameter("firstFlag");
		modelMap.put("language", Messages.getLanguage(request));
		if (firstFlag == null || "".equals(firstFlag)) {
			List viewCardInfoList = this.empInfoSer.viewCardInfoList1(request);
			modelMap.put("viewCardInfoList", viewCardInfoList);
			
			modelMap.put("viewCardInfoListCnt", viewCardInfoList == null ? 0
					: viewCardInfoList.size());
			
			LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
			.viewHrPersonalInfo2(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo", linkMap);
		} else {
			modelMap.put("EMP_OFFICE", "'15119'");
			modelMap.put("EMP_OFFICE_NAME", TipMessage.getTipMessage(
					"hrm.empinfo.JOB", request));
			modelMap.put("START_DATE_JOIN", DateUtil.getMonthStrAgo(3,"dd/MM/yyyy"));
			modelMap.put("END_DATE_JOIN", DateUtil.getSysdateStr("dd/MM/yyyy"));
		}
		modelMap.put("GRADE_NO", request.getParameter("GRADE_NO"));
		modelMap.put("zhijiCARD", request.getParameter("zhijiCARD"));
		return new ModelAndView("/hrm/empinfo/viewHTSVCardInfoList", modelMap);
	}
	
	/**
	 * viewPregnantManagement的查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPregnantManagement")
	public ModelAndView viewPregnantManagement(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 紧急联系人详细信息
		List elist = empInfoSer.getPregnantManagementList(request);
		LinkedHashMap param = null;
		if (elist != null && elist.size() > 0) {
			param = (LinkedHashMap) elist.get(0);
			modelMap.put("personid", param.get("PERSON_ID"));
			modelMap.put("HR_PREGNANT_MANAGE_NO", param
					.get("HR_PREGNANT_MANAGE_NO"));
			modelMap.put("LOCAL_NAME", param.get("LOCAL_NAME"));
		} else {
			modelMap.put("HR_PREGNANT_MANAGE_NO", "0");
			modelMap.put("personid", request.getParameter("PERSON_ID"));
		}
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getTitle(request);
		modelMap.put("title", linkMap);
		modelMap.put("viewPregnantManagementList", elist);
		// modelMap.put("totalcount",
		// empInfoSer.getPregnantManagementList_count(request));
		modelMap.put("totalcount", elist.size());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("LOCAL_TITLE", StringUtil.checkNull(request
				.getParameter("LOCAL_TITLE")));
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewPregnantManagement", modelMap);
	}

	/**
	 * PregnantManagement的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSinglePregnantManagement")
	public ModelAndView viewSinglePregnantManagement(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 用户详细信息
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.viewSinglePregnantManagement(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo", linkMap);
		modelMap.put("defaultCpny", admin.getCpnyId());// 法人ID
		modelMap.put("HR_PREGNANT_MANAGE_NO", request
				.getParameter("HR_PREGNANT_MANAGE_NO"));// 如果是0就是添加页面,如果不是0就是进入修改页面
		request.getSession().setAttribute("TABS_SELECTED",
				request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/hrm/empinfo/viewSinglePregnantManagement",
				modelMap);
	}

	/**
	 * 修改viewPregnantManagement信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editPregnantManagement")
	@ResponseBody
	public Map editPregnantManagement(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editPregnantManagement(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editPregnantManagement");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 删除PregnantManagement信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePregnantManagement")
	@ResponseBody
	public Map deletePregnantManagement(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deletePregnantManagement(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("formId", "editPregnantManagement");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return map;
	}

	/**
	 * 概要管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResumeList")
	public ModelAndView viewResumeInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.evsManageSer.viewEvsInfoList(request,
				"viewHrResumeList");
		modelMap.put("resumeList", resumeList);

		LinkedHashMap param = null;

		if (resumeList != null && resumeList.size() > 0) {
			param = (LinkedHashMap) resumeList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("resumeSize", resumeList.size());
		} else {
			modelMap.put("resumeSize", 0);
		}
		return new ModelAndView("/hrm/empinfo/viewResumeList", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddResumeInfo")
	public ModelAndView viewAddResumeInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.evsManageSer.viewEvsInfoList(request,
				"viewHrResumeList");

		LinkedHashMap param = new LinkedHashMap();

		if (resumeList != null && resumeList.size() > 0) {
			param = (LinkedHashMap) resumeList.get(0);

			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "HR_RESUME");
			fileParam.put("APPLY_NO", param.get("SEQ"));
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList", fileList);
		}
		modelMap.put("resumeInfo", param);
		return new ModelAndView("/hrm/empinfo/viewAddResumeInfo", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addResumeInfo")
	@ResponseBody
	public Map addResumeInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// seq不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if (!"".equals(seq)) {
			result = this.evsManageSer.addHrEvsInfo(request,
					"updateHrResumeInfo");
		} else {
			result = this.evsManageSer.addHrEvsInfo(request, "addHrResumeInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("navTabId", "hr3401");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteResumeInfo")
	@ResponseBody
	public Map deleteResumeInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer
				.addEvsInfo(request, "deleteHrResumeInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			map.put("navTabId", "hr3401");
		} else {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
		}
		return map;
	}

	/**
	 * 社会活动管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewActivityList")
	public ModelAndView viewActivityList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.evsManageSer.viewEvsInfoList(request,
				"viewActivityList");
		modelMap.put("resumeList", resumeList);

		LinkedHashMap param = null;

		if (resumeList != null && resumeList.size() > 0) {
			param = (LinkedHashMap) resumeList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("resumeSize", resumeList.size());
		} else {
			modelMap.put("resumeSize", 0);
		}
		return new ModelAndView("/hrm/empinfo/viewActivityList", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddActivityInfo")
	public ModelAndView viewAddActivityInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.evsManageSer.viewEvsInfoList(request,
				"viewActivityList");

		LinkedHashMap param = new LinkedHashMap();

		if (resumeList != null && resumeList.size() > 0) {
			param = (LinkedHashMap) resumeList.get(0);

			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "HR_ACTIVITY");
			fileParam.put("APPLY_NO", param.get("SEQ"));
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList", fileList);

			fileParam.put("ACTIVITY_SEQ", param.get("SEQ"));
			List activityEmpList = this.evsManageSer.viewEvsInfoList(fileParam,
					"viewActivityEmpList");
			modelMap.put("activityEmpListSize", activityEmpList == null ? 0
					: activityEmpList.size());
		}
		modelMap.put("resumeInfo", param);

		return new ModelAndView("/hrm/empinfo/viewAddActivityInfo", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addActivityInfo")
	@ResponseBody
	public Map addActivityInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// seq不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if (!"".equals(seq)) {
			result = this.evsManageSer.addActivityInfo(request,
					"updateActivityInfo");
		} else {
			result = this.evsManageSer.addActivityInfo(request,
					"addActivityInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("navTabId", "hr3501");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteActivityInfo")
	@ResponseBody
	public Map deleteActivityInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer
				.addEvsInfo(request, "deleteActivityInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			map.put("navTabId", "hr3501");
		} else {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
		}
		return map;
	}

	/**
	 * 社会活动对象管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewActivityEmpList")
	public ModelAndView viewActivityEmpList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List activityEmpList = this.evsManageSer.viewEvsInfoList(request,
				"viewActivityEmpList");
		modelMap.put("activityEmpList", activityEmpList);
		modelMap.put("activityEmpListSize", activityEmpList == null ? 0
				: activityEmpList.size());

		return new ModelAndView("/hrm/empinfo/viewActivityEmpList", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteActivityEmpInfo")
	@ResponseBody
	public Map deleteActivityEmpInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsInfo(request,
				"deleteActivityEmpInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			map.put("divId", "viewActivityEmpList_unit");
			map.put("divIdUrl",
					"/hrm/empinfo/viewActivityEmpList?seach_ACTIVITY_SEQ="
							+ request.getParameter("ACTIVITY_SEQ"));
		} else {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendActivityEmail")
	@ResponseBody
	public Map sendActivityEmail(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		this.sendEmailSer.sendActivityEmail(paramMap);
		map.put("statusCode", "200");
		map.put("message", "发送成功");
		return map;
	}
	
	@RequestMapping(value = "/addTempEmpInfoList")
	public ModelAndView addTempEmpInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/hrm/empinfo/addTempEmpInfoList", modelMap);
	}
	
	@RequestMapping(value = "/addTempEmpInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map addTempEmpInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.updateEmpinfo(request, "insertSpecialMatter");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 添加成功
			//map.put("formId", "viewEmpWomenInfo");
			map.put("navTabId", "hr2101");
		} else {
			map.put("statusCode", "300"); 
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

	@RequestMapping(value = "/updateTempEmpInfo", method = RequestMethod.GET)
	public ModelAndView updateTempEmpInfo(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		List returnList = this.empInfoSer.viewSpecialMatter(request);
		returnObj = (LinkedHashMap)returnList.get(0) ;
		modelMap.put("tempEmpinfo", returnObj);
		return new ModelAndView("/hrm/empinfo/updateTempEmpInfo", modelMap);
	}
	
	@RequestMapping(value = "/updateTempEmpInfoSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map updateTempEmpInfoSpecial(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.updateEmpinfo(request, "editSpecialMatter");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 添加成功
			map.put("formId", "viewEmpWomenInfo");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}

}
