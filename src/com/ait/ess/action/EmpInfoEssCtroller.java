 
package com.ait.ess.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.service.EssEmpInfoSer;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.hrm.service.TransactionViewSer;
import com.ait.hrm.service.TransferOrderSer;
import com.ait.is.service.CompanyMaintainSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.test.FtpUploadFileSample;
import com.ait.web.i18n.TipMessage;
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
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpInfoCtroller.java
 * @Create date: Jan 16, 2012 10:44:58 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/empinfo")
@SuppressWarnings("unchecked")
public class EmpInfoEssCtroller {
	
	Logger logger = Logger.getLogger(EmpInfoEssCtroller.class);
	@Autowired
	private EssEmpInfoSer empInfoSer;
	@Autowired
	private CompanyMaintainSer manageSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	InfoApplySer infoApplySer;
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
	
	public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");
	
	@RequestMapping(value = "/searchEmp",method = RequestMethod.GET)
	public ModelAndView getSearchEmp(HttpServletRequest request) throws Exception{		
		return new ModelAndView("/ess/empinfo/searchEmp");
	}
	@RequestMapping(value = "/getEmpList")
	@ResponseBody
	public Map getEmpList(HttpServletRequest request) throws Exception{		
		Map info = empInfoSer.getEmpList(request);
		Map map = new HashMap();
		map.put("Rows", info.get("list"));
		map.put("Total", info.get("count"));
		return map;
	}
	
	@RequestMapping(value = "/getExpInsideList")
	@ResponseBody
	public Map getExpInsideList(HttpServletRequest request) throws Exception{		
		logger.info("getExpInsideList.start...");
		Map temp=empInfoSer.getExpInsideForGrid(request);//发令事项
		return temp;
	}
	@RequestMapping(value = "/getResignationList")
	@ResponseBody
	public Map getResignationList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getResignationForGrid(request);
		return temp;
	}
	
	@RequestMapping(value = "/getEvalList")
	@ResponseBody
	public Map getEvalList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getEvalForGrid(request);
		return temp;
	}
	@RequestMapping(value = "/getEvaluateList")
	@ResponseBody
	public Map getEvaluateList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getEvaluateForGrid(request);
		return temp;
	}
	
	@RequestMapping(value = "/getPunishMentList")
	@ResponseBody
	public Map getPunishMentList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getPunishMentForGrid(request);//惩戒
		return temp;
	}
	@RequestMapping(value = "/getRewardList")
	@ResponseBody
	public Map getRewardList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getRewardForGrid(request);
		return temp;
	}
	
	@RequestMapping(value = "/getDispatchList")
	@ResponseBody
	public Map getDispatchList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getDispatchForGrid(request);//派遣
		return temp;
	}
	@RequestMapping(value = "/getPluralityList")
	@ResponseBody
	public Map getPluralityList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getPluralityForGrid(request);//兼职
		return temp;
	}
	@RequestMapping(value = "/getSuspendList")
	@ResponseBody
	public Map getSuspendList(HttpServletRequest request) throws Exception{		
		return empInfoSer.getSuspendForGrid(request);
	}
	
	@RequestMapping(value = "/getEduList")
	@ResponseBody
	public Map getEduList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getEduForGrid(request);
		return temp;
	}
	
	@RequestMapping(value = "/getFamilyInfoList")
	@ResponseBody
	public Map getFamilyInfoList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getFamilyInfoForGrid(request);
		return temp;
	}
	@RequestMapping(value = "/getSocietyRelationList")
	@ResponseBody
	public Map getSocietyRelationList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getSocietyRelationForGrid(request);
		return temp;
	}
	@RequestMapping(value = "/updateFamilyInfoOrsocietyRelationGrid")
	public ModelAndView updateFamilyInfoOrsocietyRelationGrid(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		logger.info("updateFamilyInfoGrid.start...");
		Map degreeCodeMap  = this.empInfoSer.getDegreeCodeForSelect(request);
		Map relationalTypeCodeMap  = this.empInfoSer.getRelationalTypeCodeForSelect(request);
		Map otherRelationMap  = this.empInfoSer.getOtherRelationForSelect(request);
		Map liveTogetherFlagMap=this.empInfoSer.getTogetherFlagForSelect(request);
		request.setAttribute("basicInfo", empInfoSer.getBasicInfo(request));
		request.setAttribute("degreeCodeMap", JsonUtil.writeInternal(degreeCodeMap));
		request.setAttribute("relationalTypeCodeMap", JsonUtil.writeInternal(relationalTypeCodeMap));
		request.setAttribute("otherRelationMap", JsonUtil.writeInternal(otherRelationMap));
		request.setAttribute("liveTogetherFlagMap", JsonUtil.writeInternal(liveTogetherFlagMap));
		//String familyInfoJson=request.getParameter("#familyInfoGrid");
		//String societyRelationJson=request.getParameter("#societyRelationGrid");
		//String famBackInfo=empInfoSer.updateFamilyInfoGrid(JsonUtil.getUpdateList(familyInfoJson));
		//String socBackInfo=empInfoSer.updateSocietyRelationGrid(JsonUtil.getUpdateList(societyRelationJson));
		request.getRequestDispatcher("/WEB-INF/view/ess/empinfo/viewRelation.jsp").forward(request,response); 
		return null;
	}
	@RequestMapping(value = "/updateSocietyRelationGrid")
	@ResponseBody
	public String updateSocietyRelationGrid(HttpServletRequest request) throws Exception{		
		logger.info("updateSocietyRelationGrid.start...");
		String temp=empInfoSer.updateSocietyRelationGrid(JsonUtil.getUpdateList(request));
		return temp;
	}
	
	@RequestMapping(value = "/getHealthInfoList")
	@ResponseBody
	public Map getHealthInfoList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getHealthInfoForGrid(request);
		return temp;
	}
	@RequestMapping(value = "/updateHealthInfoGrid")
	public ModelAndView updateHealthInfoGrid(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		logger.info("updateHealthInfoGrid.start...");
		Map checkWhetherMap  = this.empInfoSer.getCheckWhetherForSelect(request);
		Map checkResultMap  = this.empInfoSer.getCheckResultForSelect(request);
		request.setAttribute("checkWhetherMap", JsonUtil.writeInternal(checkWhetherMap));
		request.setAttribute("checkResultMap", JsonUtil.writeInternal(checkResultMap));
		request.setAttribute("basicInfo", empInfoSer.getBasicInfo(request));
		//String healthInfoJson=request.getParameter("#healthInfoGrid");
		//String famBackInfo=empInfoSer.updateHealthInfoGrid(JsonUtil.getUpdateList(healthInfoJson));
		request.getRequestDispatcher("/WEB-INF/view/ess/empinfo/viewHealth.jsp").forward(request,response); 
		return null;
	}
	
	@RequestMapping(value = "/getExperienceInfoList")
	@ResponseBody
	public Map getExperienceInfoList(HttpServletRequest request) throws Exception{		
		Map temp= empInfoSer.getExperienceInfoForGrid(request);
		return temp;
	}
	@RequestMapping(value = "/getQualificationInfoList")
	@ResponseBody
	public Map getQualificationInfoList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getQualificationInfoForGrid(request);
		return temp;
	}
	@RequestMapping(value = "/updateExperienceInfoGrid")
	public ModelAndView updateExperienceInfoGrid(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		logger.info("updateHealthInfoGrid.start...");
		request.setAttribute("basicInfo", empInfoSer.getBasicInfo(request));
		//String experienceInfoJson=request.getParameter("#experienceInfoGrid");
		//String famBackInfo=empInfoSer.updateExperienceInfoGrid(JsonUtil.getUpdateList(experienceInfoJson));
		request.getRequestDispatcher("/WEB-INF/view/ess/empinfo/viewWorkInfo.jsp").forward(request,response); 
		return null;
	}
	
	@RequestMapping(value = "/getAppendInfoList")
	@ResponseBody
	public Map getAppendInfoList(HttpServletRequest request) throws Exception{	
		Map temp=empInfoSer.getAppendInfoForGrid(request);
		return temp;
	}
	@RequestMapping(value = "/updateFappendInfoGrid")
	public ModelAndView updateFappendInfoGrid(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		logger.info("updateHealthInfoGrid.start...");
		request.setAttribute("basicInfo", empInfoSer.getBasicInfo(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);admin.getAdminID();
		//String fappendInfoGridJson=request.getParameter("#fappendInfoGrid");
		//String famBackInfo=empInfoSer.updateFappendInfoGrid(JsonUtil.getUpdateList(fappendInfoGridJson),request);
		request.getRequestDispatcher("/WEB-INF/view/ess/empinfo/viewAppendInfo.jsp").forward(request,response); 
		return null;
	}
	
	@RequestMapping(value = "/getLanuageInfoList")
	@ResponseBody
	public Map getLanuageInfoList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getLanuageInfoForGrid(request);
		return temp;
	}
	@RequestMapping(value = "/getITLevelInfoList")
	@ResponseBody
	public Map getITLevelInfoList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getITLevelInfoForGrid(request);
		return temp;
	}
	@RequestMapping(value = "/updateQualificationInfoOrLanuageInfoGrid")
	public ModelAndView updateQualificationInfoOrLanuageInfoGrid(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		logger.info("updateHealthInfoGrid.start...");
		Map languageLevelCodeMap  = this.empInfoSer.getLanguageLevelCodeForSelect(request);
		Map languageExamCodeMap  = this.empInfoSer.getLanguageExamCodeForSelect(request);
		Map languageTypeCodeMap  = this.empInfoSer.getLanguageTypeCodeForSelect(request);
		Map qualNameCodeMap  = this.empInfoSer.getQualNameCodeForSelect(request);
		request.setAttribute("languageLevelCodeMap", JsonUtil.writeInternal(languageLevelCodeMap));
		request.setAttribute("languageExamCodeMap", JsonUtil.writeInternal(languageExamCodeMap));
		request.setAttribute("languageTypeCodeMap", JsonUtil.writeInternal(languageTypeCodeMap));
		request.setAttribute("qualNameCodeMap", JsonUtil.writeInternal(qualNameCodeMap));
		request.setAttribute("basicInfo", empInfoSer.getBasicInfo(request));
		//String qualificationInfoJson=request.getParameter("#qualificationInfoGrid");
		//String lanuageInfoJson=request.getParameter("#lanuageInfoGrid");
		//String famBackInfo=empInfoSer.updateQualificationInfoGrid(JsonUtil.getUpdateList(qualificationInfoJson));
		//String socBackInfo=empInfoSer.updateLanuageInfoGrid(JsonUtil.getUpdateList(lanuageInfoJson));
		request.getRequestDispatcher("/WEB-INF/view/ess/empinfo/viewCompetence.jsp").forward(request,response); 
		return null;
	}
	/**
	 * 工资信息
	 * @param ac
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/viewAccountsInfo" )
	public ModelAndView getAccountsInfo(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		Map bankNameCodeMap  = this.empInfoSer.getBankNameCodeForSelect(request);
		modelMap.put("bankNameCodeMap", JsonUtil.writeInternal(bankNameCodeMap));
		modelMap.put("basicInfo", empInfoSer.getBasicInfo(request));
		return new ModelAndView("/ess/empinfo/viewAccountsInfo",modelMap);
	}
	@RequestMapping(value = "/getSinfoList")
	@ResponseBody
	public Map getSinfoList(HttpServletRequest request) throws Exception{
		Map temp=empInfoSer.getSinfoForGrid(request);
		return temp;
	}
	@RequestMapping(value = "/getPaEmpInfoList")
	@ResponseBody
	public Map getPaEmpInfoList(HttpServletRequest request) throws Exception{
		Map temp=empInfoSer.getPaEmpInfoForGrid(request);
		return temp;
	}
	@RequestMapping(value = "/updatePaEmpInfoGrid")
	public ModelAndView updatePaEmpInfoGrid(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		logger.info("updateHealthInfoGrid.start...");
		Map bankNameCodeMap  = this.empInfoSer.getBankNameCodeForSelect(request);
		request.setAttribute("bankNameCodeMap", JsonUtil.writeInternal(bankNameCodeMap));
		request.setAttribute("basicInfo", empInfoSer.getBasicInfo(request));
		//String paEmpInfoJson=request.getParameter("#paEmpInfoGrid");
		//String famBackInfo=empInfoSer.updatePaEmpInfoGrid(JsonUtil.getUpdateList(paEmpInfoJson),request);
		request.getRequestDispatcher("/WEB-INF/view/ess/empinfo/viewAccountsInfo.jsp").forward(request,response); 
		return null;
	}
	/**
	 * 担当业务
	 * @param ac
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/viewActBusiness" )
	public ModelAndView getActBusiness(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		modelMap.put("basicInfo", empInfoSer.getBasicInfo(request));
		return new ModelAndView("/ess/empinfo/viewActBusiness",modelMap);
	}
	@RequestMapping(value = "/getBizlistList")
	@ResponseBody
	public Map getBizlistList(HttpServletRequest request) throws Exception{		
		Map temp=empInfoSer.getBizlistForGrid(request);
		return temp;
	}

	@RequestMapping(value = "/getContractList")
	@ResponseBody
	public Map getContractList(HttpServletRequest request,
				HttpServletResponse response) throws Exception{
		logger.info("getContractList.start..."+request.getParameter("empid"));
		//Map info = empInfoSer.getContractList(request);
		Map map = new HashMap();
		//map.put("Rows", info.get("list"));
		//map.put("Total", info.get("count"));
		return map;
	}

	/**
	 * ESS员工基础信息(Staff foundation information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonalInfoForEss")
	public ModelAndView getPersonalInfoForEss(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		getPersonalInfo(request, response, modelMap);
		return new ModelAndView("/ess/empinfo/essViewPersonalInfo",modelMap);
	}
	/**
	 * 员工基础信息(Staff foundation information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonalInfo")
	public ModelAndView getPersonalInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		logger.info("viewPersonalInfo.start...");
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		String tabsSelected = request.getSession().getAttribute("TABS_SELECTED") ==null?"0": request.getSession().getAttribute("TABS_SELECTED").toString();
           
		modelMap.put("personInfo", linkMap);
		//毕业学校
		//modelMap.put("educationList",this.empInfoSer.getEducationList(request));
		
	//	modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
	//			toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2540")) ;
		
	//	modelMap.put("menuThirdList",this.empInfoSer.getMenuThirdListList("",request));
		//工会信息
	//	modelMap.put("toolbartradeunion", toolMenuSer.getToolMenuForNo(request, "123194")) ;
	//	modelMap.put("tradeUnionList", empInfoSer.getTradeunionList(request));
		//黑色档案信息
	//	modelMap.put("toolBadArchives", toolMenuSer.getToolMenuForNo(request, "2550")) ;
	//	modelMap.put("badArchives", empInfoSer.getBadArchivesList(request));
		//辅助信息
	//	modelMap.put("toolAssist", toolMenuSer.getToolMenuForNo(request, "216001")) ;
	//	modelMap.put("assistList", empInfoSer.getAssistList(request));
		//评价信息
	//	modelMap.put("toolbarInfopingjia", toolMenuSer.getToolMenuForNo(request, "2542")) ;
	//	modelMap.put("EvsInfo", empInfoSer.getEvsInfoList(request));
		//外国语信息
	//	modelMap.put("toolbarInfozige", toolMenuSer.getToolMenuForNo(request, "123192")) ;
	//	modelMap.put("languageLevelList", empInfoSer.getLanguageLevelList(request));
		//社会关系
	//	modelMap.put("toolbarInfoshehui", toolMenuSer.getToolMenuForNo(request, "123191")) ;
	//	modelMap.put("familyList", empInfoSer.getFamilyList(request));
		//家庭关系
		modelMap.put("toolbarInfoHome", toolMenuSer.getToolMenuForNo(request, "123190")) ;
		modelMap.put("homeRelationList", empInfoSer.getHomeRelationList(request));
		//紧急联系地址
		modelMap.put("emergencyAddressList", empInfoSer.getEmergencyAddressList(request));
		//地址信息
		modelMap.put("addressList", empInfoSer.getAddressList(request));
		//获取兼卖产品类型
	//	List productList = empInfoSer.getEmpProductList(request);
	//	modelMap.put("productList", productList);
		//残疾信息
	//	modelMap.put("toolbarInfodisability", toolMenuSer.getToolMenuForNo(request, "123193")) ;
	//	modelMap.put("disabilityinfoList", empInfoSer.getDisabilityinfoList(request));
		//工作经历
		/*
modelMap.put("toolbarInfogongzuo",  toolMenuSer.getToolMenuForNo(request, "2551")) ;
modelMap.put("workExperienceList", empInfoSer.getWorkExperienceList(request));
	*/	//培训
	//	modelMap.put("toolbarInfopeixun", toolMenuSer.getToolMenuForNo(request, "2545")) ;
	//	modelMap.put("trainingInfoList", empInfoSer.getTrainingInfoList(request));
		//合同
	//	modelMap.put("toolbarInfohetong", toolMenuSer.getToolMenuForNo(request, "2491")) ;
	//	modelMap.put("contracList", empInfoSer.getContractList(request));
		
		//发令信息
	//	modelMap.put("toolbarInfofl", toolMenuSer.getToolMenuForNo(request, "2541")) ;
	//	modelMap.put("assignmentList", empInfoSer.getAssignmentList(request));
		
		//派遣地
	//	modelMap.put("toolbarInfofl", toolMenuSer.getToolMenuForNo(request, "2491")) ;
	//	modelMap.put("expInsideList", empInfoSer.getExpInsideList(request));
		
		//资格信息
	//	modelMap.put("toolbarInfozige", toolMenuSer.getToolMenuForNo(request, "2554")) ;
	//	modelMap.put("qualificationList", empInfoSer.getQualificationList(request));
		
		modelMap.put("tabsSelected",tabsSelected);
		request.getSession().removeAttribute("TABS_SELECTED") ;
		modelMap.put("isEssSystem",request.getParameter("isEssSystem") != null ? (String)request.getParameter("isEssSystem") : "0");
		return new ModelAndView("/ess/empinfo/viewPersonalInfo",modelMap);
	}
	
	/**
	 * 转到添加学校信息(Turn to add school information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEducationInfo")
	public ModelAndView viewEducationInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList",empInfoSer.getCodeList("741",request));
		modelMap.put("degreeList",empInfoSer.getCodeList("1665",request));
		//modelMap.put("particularDegreeList",empInfoSer.getCodeList("123255",request));
		modelMap.put("subjectClassifyList",empInfoSer.getCodeList("123412",request));//专业分类
		modelMap.put("siteProvinceList",empInfoSer.getCodeList("4602",request));//所在地省
		modelMap.put("FINALNUM",empInfoSer.getFinalNum(request));//统计用户是否有最终学历0为没有
		
		
	    request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		
		return new ModelAndView("/ess/empinfo/viewEducationInfo",modelMap);
	}
	
	
	/**
	 * 添加毕业学校信息(add school information)
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
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
			map.put("navTabId", "hr0101");
		 	map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		
		return map;
	}
	
	/**
	 * 转到删除毕业学校信息(Turn to delete school information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEducation")
	public ModelAndView deleteEducation(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("educationList",this.empInfoSer.getEducationList(request));
	    request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/ess/empinfo/deleteEducation",modelMap);
	}
	
	/**
	 * 删除毕业学校信息(delete school information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEducationInfo")
	@ResponseBody
	public Map deleteEducation(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteEduactionInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0101");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	
	/**
	 * 转到个人信息(Turn to add school and personal information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEduPerInfo")
	public ModelAndView viewEduPerInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//用户详细信息
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		List corpList = manageSer.getIsCorpInfo(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo",linkMap);
		
		modelMap.put("defaultCpny", admin.getCpnyId());//法人ID
		modelMap.put("photoId", "viewEduPerInfo");
		modelMap.put("corpList", corpList);
		 request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		//获取兼卖产品类型
		List productList = empInfoSer.getEmpProductList(request);
		modelMap.put("productList", productList);
		modelMap.put("isEssSystem",request.getParameter("isEssSystem") != null ? (String)request.getParameter("isEssSystem") : "0");
			
		return new ModelAndView("/ess/empinfo/viewEduPerInfo",modelMap); 
	}
	
	/**
	 * 转到个人信息(Turn to add school and personal information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTempEduPerInfo")
	public ModelAndView viewTempEduPerInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//用户详细信息
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		List corpList = manageSer.getIsCorpInfo(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo",linkMap);
		
		modelMap.put("defaultCpny", admin.getCpnyId());//法人ID
		modelMap.put("photoId", "viewEduPerInfo");
		modelMap.put("corpList", corpList);
		 request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		//获取兼卖产品类型
		List productList = empInfoSer.getEmpProductList(request);
		modelMap.put("productList", productList);
		modelMap.put("isEssSystem",request.getParameter("isEssSystem") != null ? (String)request.getParameter("isEssSystem") : "0");
			
		return new ModelAndView("/ess/empinfo/viewTempEduPerInfo",modelMap); 
	}
	
	/**
	 * 修改个人信息和学校信息(Modify personal information and school information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEduPerInfo")
	@ResponseBody
	public Map editEduPerInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editEduPerInfo(request);
		String isEssSystem = request.getParameter("isEssSystem")!=null ? (String)request.getParameter("isEssSystem") : "0";
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//保存成功
			
			if(isEssSystem.equals("1")){
				map.put("navTabId", "ess0101");
				map.put("forwardUrl","/ess/empinfo/essViewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&&navTabId=ess0101") ;
				//?pageNum=1&menuNo=2419&
			}else{
				map.put("navTabId", "hr0101");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&&navTabId=hr0101") ;
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;
	}

	@RequestMapping(value = "/editTempEduPerInfo")
	@ResponseBody
	public Map editTempEduPerInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editTempEduPerInfo(request);
		String isEssSystem = request.getParameter("isEssSystem")!=null ? (String)request.getParameter("isEssSystem") : "0";
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//保存成功
			
			if(isEssSystem.equals("1")){
				map.put("navTabId", "ess0101");
				map.put("forwardUrl","/ess/empinfo/essViewTempPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&&navTabId=ess0101") ;
				//?pageNum=1&menuNo=2419&
			}else{
				map.put("navTabId", "hr0101");
				map.put("forwardUrl","/ess/empinfo/viewTempPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&&navTabId=hr0101") ;
			}
		}else if(result == 2){
			map.put("statusCode", "300");
			map.put("message", "ID卡号已存在!");//保存失败
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;
	}

	
	/**
	 * 发令信息(dekreti information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPromote")
	public ModelAndView getPromote(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfo(request);
		modelMap.put("personInfo", linkMap);
		modelMap.put("expInsideList", empInfoSer.getExpInsideList(request));
		modelMap.put("resignationInfo", empInfoSer.getResignationInfo(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		modelMap.put("photoId", "viewPromote");
		return new ModelAndView("/ess/empinfo/viewPromote",modelMap);
	}
	

	/**
	 * 评价信息(Evaluation information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/viewEvaluate")
	public ModelAndView getEvaluate(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfo(request);
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("EvsInfo", empInfoSer.getEvsInfoList(request));
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		modelMap.put("photoId", "viewEvaluate");
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2542")) ;
		return new ModelAndView("/ess/empinfo/viewEvaluate",modelMap);
	}
	/**
	 * 转到添加评价信息(Turn to add Evaluation information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvsInfo")
	public ModelAndView viewEvsInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList",empInfoSer.getCodeList("14895",request));
		modelMap.put("gradeCodeList",empInfoSer.getCodeList("3538",request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewEvsInfo",modelMap);
	}
	
	
	/**
	 * 添加评价信息(add Evaluation information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEvsInfo")
	@ResponseBody
	public Map addEvsInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.addEvsInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0103");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;
	}
	

	/**
	 * 转到修改评价信息(Turn to modify assessment information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/viewEditEvsInfo")
	public ModelAndView viewEditEvsInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("evsInfoList", empInfoSer.getEvsInfoList(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewEditEvsInfo",modelMap);
	}
	
	
	/**
	 * 修改评价信息(Modify assessment information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEvsInfo")
	@ResponseBody
	public Map editEvsInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editEvsInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0103");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;
	}
	
	
	
	/**
	 * 转到删除评价信息(Turn to delete EVS information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEvs")
	public ModelAndView deleteEvs(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("EvsInfo", empInfoSer.getEvsInfoList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteEvs",modelMap);
	}

	/**
	 * 删除评价信息(delete assessment information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEvsInfo")
	@ResponseBody
	public Map deleteEvsInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteEvsInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0103");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}

	/**
	 * 奖励/惩戒(REWARD/PUNISHMENT)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/viewReward" )
	public ModelAndView getReward(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("rewardList", empInfoSer.getReward(request));
		modelMap.put("punishmentList", empInfoSer.getPunishment(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewReward");
		
		return new ModelAndView("/ess/empinfo/viewReward",modelMap);
	}
	

	/**
	 * 兼职(Plurality)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/viewTranslate" )
	public ModelAndView getTranslate(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("pluralityList", empInfoSer.getPluralityList(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewTranslate");
				
		return new ModelAndView("/ess/empinfo/viewTranslate",modelMap);
	}
	/**
	 * 工会(TrainingInfo)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/viewTradeunion" )
	public ModelAndView getTradeunion(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("trainingInfoList", empInfoSer.getTradeUnionInfoList(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
				
		modelMap.put("photoId", "viewTraining");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "123194")) ;
		
		return new ModelAndView("/ess/empinfo/viewTradeunion",modelMap);
	}
	
	/**
	 * 培训(TrainingInfo)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/viewTraining" )
	public ModelAndView getTraining(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("trainingInfoList", empInfoSer.getTrainingInfoList(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
				
		modelMap.put("photoId", "viewTraining");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2545")) ;
		
		return new ModelAndView("/ess/empinfo/viewTraining",modelMap);
	}
	
	/**
	 * 转到添加培训信息(Turn to add Training information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTrainingInfo")
	public ModelAndView viewTrainingInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("trainingMethodList",empInfoSer.getCodeList("123271",request));
		modelMap.put("mustList",empInfoSer.getCodeList("123376",request));
		modelMap.put("differentiateList",empInfoSer.getCodeList("123459",request));
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/ess/empinfo/viewTrainingInfo",modelMap);
	}
	
	/**
	 * 培训信息添加(add TrainingInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTrainingInfo")
	@ResponseBody
	public Map<String, Object> addTrainingInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		
			int result = this.empInfoSer.addTrainingInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0106");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
				
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	
	/**
	 * 转到修改培训信息(Turn to modify Training information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateTrainingInfo")
	public ModelAndView updateTrainingInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("trainingInfoList", empInfoSer.getTrainingInfoList(request));
		modelMap.put("trainingMethodList",empInfoSer.getCodeList("123271",request));
		modelMap.put("mustList",empInfoSer.getCodeList("123376",request));
		modelMap.put("differentiateList",empInfoSer.getCodeList("123459",request));
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/ess/empinfo/updateTrainingInfo",modelMap);
	}
	
	/**
	 * 修改培训信息(Modify training information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editTrainingInfo")
	@ResponseBody
	public Map editTrainingInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editTrainingInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0106");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	/**
	 * 转到删除培训信息(Turn to delete training information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTraining")
	public ModelAndView deleteTraining(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("trainingInfoList", empInfoSer.getTrainingInfoList(request));
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/ess/empinfo/deleteTraining",modelMap);
	}
	
	
	/**
	 * 删除培训信息(delete training information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTrainingInfo")
	@ResponseBody
	public Map deleteTrainingInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteTrainingInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0106");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;
	}
	
	
	/**
	 * 紧急地址(social relations)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/viewRelation" )
	public ModelAndView getRelation(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		modelMap.put("homeRelationList", empInfoSer.getHomeRelationList(request));
		
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewRelation");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2549")) ;
		
		return new ModelAndView("/ess/empinfo/viewRelation",modelMap);
	}
	
	
	
	/**
	 * 转到添加紧急联系信息(Turn to add Relation family information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmergencyAddressInfo")
	public ModelAndView viewEmergencyAddressInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(modelMap.get("PERSON_ID")==""||modelMap.get("PERSON_ID")==null){
			modelMap.put("PERSON_ID", admin.getPersonId());
			}
			
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList", empInfoSer.getCodeList("1693", request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewEmergencyAddressInfo",modelMap);
	}
	
	/**
	 * 添加紧急联系人(add familyInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEmergencyAddressInfo")
	@ResponseBody
	public Map<String, Object> addEmergencyAddressInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
			//String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		
			
			int result = this.empInfoSer.addEmergencyAddressInfo(request) ;
			
			if(result == 1){
				//if(isEssSystem.equals("1")){//ESS添加过来的
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
					map.put("navTabId", "ess3001");
				//	map.put("navTabId", "hr0107");
					map.put("forwardUrl","/ess/empinfo/viewPersonalInfoForEss?navTabId=ess3001") ;
				/*}else{//业务系统添加过来的
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
					map.put("navTabId", "hr0101");
				//	map.put("navTabId", "hr0107");
					map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
				}*/
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	
	/**
	 * 转到删除紧急联系地址(Turn to delete EmergencyAddressInfo information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEmergencyAddressInfo")
	public ModelAndView deleteEmergencyAddressInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteEmergencyAddressInfo",modelMap);
	}
	
	/**
	 * 删除社会关系(delete family information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEmergencyAddress")
	@ResponseBody
	public Map deleteEmergencyAddress(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		int result = this.empInfoSer.deleteFamilyInfo(request);
		if(result == 1){
			if(isEssSystem.equals("1")){//ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//保存成功
				map.put("navTabId", "ess0101");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/essViewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=ess0101") ;
			}else{//业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	
	/**
	 * 转到修改紧急联系信息(Turn to modify family information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateEmergencyAddress")
	public ModelAndView updateEmergencyAddress(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("EmergencyAddressList", empInfoSer.getEmergencyAddressList(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/EmergencyAddress",modelMap);
	}
	
	/**
	 * 修改紧急地址(Modify family information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEmergencyAddressInfo")
	@ResponseBody
	public Map editEmergencyAddressInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editEmergencyAddressInfo(request);
		//String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if(result == 1){	
		//	if(isEssSystem.equals("1")){//ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "ess3001");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/essViewPersonalInfoForEss?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=ess3001") ;
		/*	}else{//业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			
			}*/
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	/**
	 * 转到删除社会关系(Turn to delete family information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFamily")
	public ModelAndView deleteFamily(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteFamily",modelMap);
	}
	
	/**
	 * 删除社会关系(delete family information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFamilyInfo")
	@ResponseBody
	public Map deleteFamilyInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		int result = this.empInfoSer.deleteFamilyInfo(request);
		if(result == 1){
			if(isEssSystem.equals("1")){//ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//保存成功
				map.put("navTabId", "ess0101");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/essViewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=ess0101") ;
			}else{//业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	
	/**
	 * 转到修改社会关系信息(Turn to modify family information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateFamilyInfo")
	public ModelAndView updateFamilyInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/updateFamilyInfo",modelMap);
	}
	
	/**
	 * 修改社会信息(Modify family information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editFamilyInfo")
	@ResponseBody
	public Map editFamilyInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editFamilyInfo(request);
		String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if(result == 1){	
			if(isEssSystem.equals("1")){//ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "ess0101");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/essViewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=ess0101") ;
			}else{//业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	/**
	 * 家人关系(social relations)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/viewHomeRelation" )
	public ModelAndView getHomeRelation(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		modelMap.put("homeRelationList", empInfoSer.getHomeRelationList(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewRelation");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2549")) ;
		
		return new ModelAndView("/ess/empinfo/viewRelation",modelMap);
	}
	
	/**
	 * 转到添加家人关系信息(Turn to add Relation homerelation information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewHomeRelationInfo")
	public ModelAndView viewHomeRelationInfo(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
      	modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList", empInfoSer.getCodeList("950", request));
		modelMap.put("codeList1", empInfoSer.getCodeList("123276", request));
		modelMap.put("addressList", empInfoSer.getAddressList(request));

		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewHomeRelationInfo",modelMap);
	}
	
	/**
	 * 添加家人关系(add familyInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHomeRelationInfo")
	@ResponseBody
	public Map<String, Object> addHomeRelationInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.addHomeRelationInfo(request) ;
		//String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "ess3001");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfoForEss?navTabId=ess3001") ;
			
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;		
	}
	
	/**
	 * 转到删除家人关系(Turn to delete homerelation information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHomeRelation")
	public ModelAndView deleteHomeRelationInfo(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		modelMap.put("deleteRelationInfoList", empInfoSer.getHomeRelationList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteHomeRelation",modelMap);
	}
	
	/**
	 * 删除家人关系(delete family information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHomeRelationInfo")
	@ResponseBody
	public Map deleteHomeRelationInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteHomeRelationInfo(request);
		String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if(result == 1){
			if(isEssSystem.equals("1")){//ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
				map.put("navTabId", "ess0101");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/essViewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=ess0101") ;
			}else{//业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
				map.put("navTabId", "hr0101");
		//		map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	
	/**
	 * 转到修改家人关系信息(Turn to modify family information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateHomeRelation")
	public ModelAndView updateHomeRelation(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("homeRelationList", empInfoSer.getHomeRelationList(request));
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
	//	modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		modelMap.put("addressInfo", empInfoSer.getAddressInfo(request));
		modelMap.put("navTabId", "ess3001");
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

	//	request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/updateHomeRelation",modelMap);
	}
	
	/**
	 * 修改家人信息(Modify family information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHomeRelationInfo")
	@ResponseBody
	public Map editHomeRelationInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editHomeRelation(request);
		if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "ess3001");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfoForEss?navTabId=ess3001") ;

		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	/**
	 * 健康信息(Health information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/viewHealth" )
	public ModelAndView getHealth(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{

		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("healthList", empInfoSer.getHealthList(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewHealth");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2550")) ;
		
		return new ModelAndView("/ess/empinfo/viewHealth",modelMap);
	}
	
	
	/**
	 * 转到添加健康信息(Turn to add health information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewHealthInfo")
	public ModelAndView viewHealthInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		modelMap.put("physicalTypeList", empInfoSer.getCodeList("4570", request));
		modelMap.put("industryDistinguishList", empInfoSer.getCodeList("14900", request));
		modelMap.put("bloodTypeList", empInfoSer.getCodeList("4573", request));
		modelMap.put("generalHealthList", empInfoSer.getCodeList("4572", request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewHealthInfo",modelMap);
	}
	
	
	/**
	 * 添加健康信息(add addHealthInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHealthInfo")
	@ResponseBody
	public Map<String, Object> addHealthInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		
			int result = this.empInfoSer.addHealthInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0108");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	
	/**
	 * 转到修改健康信息(Turn to modify health information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateHealthInfo")
	public ModelAndView updateHealthInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("healthList", empInfoSer.getHealthList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/updateHealthInfo",modelMap);
		
	}
	
	
	/**
	 * 修改健康信息(Modify health information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editHealthInfo")
	@ResponseBody
	public Map editHealthInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editHealthInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0108");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	
	
	/**
	 * 转到删除健康信息(Turn to delete health information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHealth")
	public ModelAndView deleteHealth(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("healthList", empInfoSer.getHealthList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteHealth",modelMap);
	}
	
	/**
	 * 删除健康信息(delete health information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHealthInfo")
	@ResponseBody
	public Map deleteHealthInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteHealthInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0101");
			//map.put("navTabId", "hr0108");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	

	/**
	 * 工作经验(work experience)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/viewWorkInfo" )
	public ModelAndView getWorkInfo(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("workExperienceList", empInfoSer.getWorkExperienceList(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewWorkInfo");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2551")) ;
		
		return new ModelAndView("/ess/empinfo/viewWorkInfo",modelMap);
	}
	
	/**
	 * 转到添加工作经验信息(Turn to add workExperience family information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWorkExperienceInfo")
	public ModelAndView viewWorkExperienceInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		modelMap.put("workExperienceList", empInfoSer.getWorkExperienceList(request));
		 request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/ess/empinfo/viewWorkExperienceInfo",modelMap);
	}
	
	/**
	 * 添加工作经历(add workExperience)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addWorkExperienceInfo")
	@ResponseBody
	public Map<String, Object> addWorkExperienceInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		
			int result = this.empInfoSer.addWorkExperienceInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				 map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0109");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	
	
	/**
	 * 转到修改工作经验信息(Turn to modify workExperience information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateWorkExperienceInfo")
	public ModelAndView updateWorkExperienceInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("workExperienceList1", empInfoSer.getWorkExperienceList(request));
		 request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/ess/empinfo/updateWorkExperienceInfo",modelMap);
		
	}
	
	
	/**
	 * 修改工作经验信息(Modify workExperience information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editWorkExperienceInfo")
	@ResponseBody
	public Map editWorkExperienceInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editWorkExperienceInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			 map.put("navTabId", "hr0101");
			// map.put("navTabId", "hr0109");
			 map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	
	/**
	 * 转到删除工作经历(Turn to delete WorkExpreience )
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteWorkExpreience")
	public ModelAndView deleteWorkExpreience(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("workExperienceList", empInfoSer.getWorkExperienceList(request));
		 request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/ess/empinfo/deleteWorkExpreience",modelMap);
	}
	
	
	/**
	 * 删除工作经历(delete WorkExpreienceInfo)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteWorkExpreienceInfo")
	@ResponseBody
	public Map deleteWorkExpreienceInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteWorkExpreienceInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			 map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0109");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;
	}

	/**
	 * 资格信息(Competence information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/viewCompetence" )
	public ModelAndView viewCompetenceList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("qualificationList", empInfoSer.getQualificationList(request));
		modelMap.put("languageLevelList", empInfoSer.getLanguageLevelList(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewCompetence");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2554")) ;
		
		return new ModelAndView("/ess/empinfo/viewCompetence",modelMap);
	}
	
	
	/**
	 * 转到添加资格信息(Turn to add Competence information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCompetenceInfo")
	public ModelAndView viewCompetence(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		modelMap.put("qualLevelList", empInfoSer.getCodeList("14910", request));
		modelMap.put("qualGradeList", empInfoSer.getCodeList("123485", request));
		modelMap.put("acquisitionModesList", empInfoSer.getCodeList("123268", request));

		modelMap.put("languageTypeCodeList", empInfoSer.getCodeList("1703", request));
		modelMap.put("examNameCodeList", empInfoSer.getCodeList("1394", request));
		modelMap.put("languageLevelCodeList", empInfoSer.getCodeList("1401", request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		
		return new ModelAndView("/ess/empinfo/viewCompetenceInfo",modelMap);
	}
	
	
	/**
	 * 添加资格信息(add addCompetenceInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCompetenceInfo")
	@ResponseBody
	public Map<String, Object> addCompetenceInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		
			int result = this.empInfoSer.addCompetenceInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "hr0101");
				map.put("PERSON_ID", request.getParameter("PERSON_ID"));
			//	map.put("navTabId", "hr0111");
			//	map.put("forwardUrl","/ess/empinfo/viewCompetence?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;	
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	
	/**
	 * 转到修改资格信息(Turn to modify Competence information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateCompetenceInfo")
	public ModelAndView updateCompetenceInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("qualificationList", empInfoSer.getQualificationList(request));
		modelMap.put("languageLevelList", empInfoSer.getLanguageLevelList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/updateCompetenceInfo",modelMap);
		
	}
	
	
	/**
	 * 修改资格信息(Modify Competence information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editCompetenceInfo")
	@ResponseBody
	public Map editCompetenceInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editCompetenceInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
			map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		//	map.put("navTabId", "hr0111");
		//	map.put("forwardUrl","/ess/empinfo/viewCompetence?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;	
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	
	/**
	 * 转到删除资格信息(Turn to delete Competence )
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCompetenceInfo")
	public ModelAndView deleteCompetenceInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("qualificationList", empInfoSer.getQualificationList(request));
		modelMap.put("languageLevelList", empInfoSer.getLanguageLevelList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteCompetenceInfo",modelMap);
		
	}
	
	/**
	 * 删除资格信息(delete CompetenceInfo)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCompetence")
	@ResponseBody
	public Map deleteCompetence(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
	//	System.out.println( request.getParameter("PERSON_ID"));
		map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		int result = this.empInfoSer.deleteCompetenceInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			
	 		map.put("navTabId", "hr0101");
	//		map.put("navTabId", "hr0111");
  //		map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID"));
		
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;	
	//		map.put("forwardUrl","/ess/empinfo/viewCompetence?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
		
	}
	
	/**
	 * 特殊事项(Special matters)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="viewAdditional" )
	public ModelAndView viewAdditionalList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("additionalList", empInfoSer.getAdditionalList(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewAdditional");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2552")) ;
		
		return new ModelAndView("/ess/empinfo/viewAdditional",modelMap);
	}
	
	
	/**
	 * 转到添加特殊事项(Turn to add a special matters)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAdditionalInfo")
	public ModelAndView viewAdditionalInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_NAME", admin.getLocalName());
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("infoTypeCodeList", empInfoSer.getCodeList("14903", request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewAdditionalInfo",modelMap);
	}
	
	
	
	/**
	 * 添加特殊事项(add special matters)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAdditionalInfo")
	@ResponseBody
	public Map<String, Object> addAdditionalInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		
			int result = this.empInfoSer.addAdditionalInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0110");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	
	
	
	/**
	 * 转到修改特殊事项(Turn to modify special matters)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateAdditionalInfo")
	public ModelAndView updateAdditionalInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("additionalList", empInfoSer.getAdditionalList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/updateAdditionalInfo",modelMap);
		
	}
	

	/**
	 * 修改特殊事项(Modify special matters)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editAdditionalInfo")
	@ResponseBody
	public Map editAdditionalInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editAdditionalInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0110");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	
	/**
	 * 转到删除特殊事项(Turn to special matters)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAdditional")
	public ModelAndView deleteAdditionalInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("additionalList", empInfoSer.getAdditionalList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteAdditional",modelMap);
	}
	
	
	
	/**
	 * 删除特殊事项(delete special matters)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAdditionalInfo")
	@ResponseBody
	public Map deleteAdditionalInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteAdditionalInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0101");
	//		map.put("navTabId", "hr0110");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	
	
	
	
	/**
	 * 账户(account)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="viewAccount" )
	public ModelAndView viewAccountList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("accountList", empInfoSer.getAccountList(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewAccount");
		
		return new ModelAndView("/ess/empinfo/viewAccount",modelMap);
	}
	
	
	
	/**
	 * 合同/档案(contract/files)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewContract")
	public ModelAndView getContractInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("contracList", empInfoSer.getContractList(request));
		modelMap.put("fileList", empInfoSer.getFileList(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewContract");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2491")) ;
		
		return new ModelAndView("/ess/empinfo/viewContract",modelMap);
	}
	
	
	
	/**
	 * 转到添加档案(Turn to add a file)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewContractInfo")
	public ModelAndView viewContractInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("fileTypeList", empInfoSer.getCodeList("4577", request));
		modelMap.put("fileRelationCodeList", empInfoSer.getCodeList("1384", request));
		modelMap.put("fileAreaList", empInfoSer.getCodeList("4578", request));
		modelMap.put("fileInfoYnList", empInfoSer.getCodeList("14892", request));
		return new ModelAndView("/ess/empinfo/viewContractInfo",modelMap);
	}
	
	
	
	/**
	 * 添加档案(add addFileInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addFileInfo")
	@ResponseBody
	public Map<String, Object> addFileInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		
			int result = this.empInfoSer.addFileInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0114");
			//	map.put("forwardUrl","/ess/empinfo/viewContract?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;	
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	
	
	
	
	/**
	 * 转到修改档案信息(Turn to modify file information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateFileInfo")
	public ModelAndView updateFileInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("fileList", empInfoSer.getFileList(request));
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/ess/empinfo/updateFileInfo",modelMap);
		
	}
	
	
	/**
	 * 修改档案信息(Modify file information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editFileInfo")
	@ResponseBody
	public Map editFileInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editFileInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
		//	map.put("navTabId", "hr0114");
		//	map.put("forwardUrl","/ess/empinfo/viewContract?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			map.put("navTabId", "hr0101");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;	
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	
	
	/**
	 * 转到删除档案(Turn to delete file)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFile")
	public ModelAndView deleteFile(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("fileList", empInfoSer.getFileList(request));
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));

		return new ModelAndView("/ess/empinfo/deleteFile",modelMap);
	}
	
	

	/**
	 * 删除档案(delete file)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFileInfo")
	@ResponseBody
	public Map deleteFileInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteFileInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0114");
		//	map.put("forwardUrl","/ess/empinfo/viewContract?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;	
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	
	
	
	
	/**
	 * 出国信息(goAbroad information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewGoAbroad")
	public ModelAndView viewGoAbroadList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("goAbroadList", empInfoSer.getGoAbroadList(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewGoAbroad");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "3676")) ;
		
		return new ModelAndView("/ess/empinfo/viewGoAbroad",modelMap);
	}
	
	
	/**
	 * 转到添加出国信息(Turn to add goabroad)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewGoAbroadInfo")
	public ModelAndView viewGoAbroadInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("countryList", empInfoSer.getCodeList("870", request));
		 request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewGoAbroadInfo",modelMap);
	}
	
	
	
	/**
	 * 添加出国信息(add goAbroadInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addGoAbroadInfo")
	@ResponseBody
	public Map<String, Object> addGoAbroadInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		
			int result = this.empInfoSer.addGoAbroadInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0115");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	
	
	/**
	 * 转到修改出国信息(Turn to modify goabroad information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateGoAbroadInfo")
	public ModelAndView updateGoAbroadInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("goAbroadList", empInfoSer.getGoAbroadList(request));
		 request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/updateGoAbroadInfo",modelMap);
		
	}
	
	
	/**
	 * 修改出国信息(Modify file goAbroad information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editGoAbroadInfo")
	@ResponseBody
	public Map editGoAbroadInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editGoAbroadInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0115");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	
	
	/**
	 * 转到删除特殊事项(Turn to special matters)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteGoAbroad")
	public ModelAndView deleteGoAbroad(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("goAbroadList", empInfoSer.getGoAbroadList(request));
		 request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteGoAbroad",modelMap);
	}
	
	
	
	
	/**
	 * 删除出国信息(delete goAbroad information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteGoAbroadInfo")
	@ResponseBody
	public Map deleteGoAbroadInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteGoAbroadInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0115");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}

	
	/**
	 * 证照信息(credential information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCredential")
	public ModelAndView viewCredential(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("credentialList", empInfoSer.getCredentialList(request));
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewCredential");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "3690")) ;
		
		return new ModelAndView("/ess/empinfo/viewCredential",modelMap);
	}

	
	
	/**
	 * 转到添加证照信息(Turn to add credential)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCredentialInfo")
	public ModelAndView viewCredentialInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		modelMap.put("credentialList", empInfoSer.getCodeList("4297", request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewCredentialInfo",modelMap);
	}
	
	
	
	/**
	 * 添加证照信息(add credential)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCredentialInfo")
	@ResponseBody
	public Map<String, Object> addCredentialInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		
			int result = this.empInfoSer.addCredentialInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0116");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}

	
	
	/**
	 * 转到修改证照信息(Turn to modify credential information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateCredentialInfo")
	public ModelAndView updateCredentialInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("credentialList", empInfoSer.getCredentialList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/updateCredentialInfo",modelMap);
		
	}
	
	
	
	/**
	 * 修改证照信息(Modify file credential)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editCredentialInfo")
	@ResponseBody
	public Map editCredentialInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editCredentialInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0116");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	
	
	/**
	 * 转到删除特殊事项(Turn to special matters)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCredential")
	public ModelAndView deleteCredential(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("credentialList", empInfoSer.getCredentialList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteCredential",modelMap);
	}
	
	
	
	/**
	 * 删除证照信息(delete credential information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCredentialInfo")
	@ResponseBody
	public Map deleteCredentialInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteCredentialInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0116");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	
	
	
	/**
	 * 根绝EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
	 * @param request
	 * @param response
	 * @param modelMapviewEmpIdList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpIdList")
	public ModelAndView viewEmpIdList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("sortNameNoList",this.empInfoSer.getOrderParmList(request,"18706"));
		modelMap.put("turn_to_url",request.getParameter("turnToUrl"));
		
		
		modelMap.put("empList",this.empInfoSer.getEmpIdList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.empInfoSer.getEmpIdListCnt(request)) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/ess/empinfo/viewEmpIdList",modelMap);		
	} 
	
	
	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonCnt")
	@ResponseBody
	public Map getPersonCnt(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
	 	List pidEidList=this.empInfoSer.getPidEidList(request);
		
		map.put("perCnt", pidEidList.size());
		if(pidEidList.size()==1){
			map.put("personId", ((Map)pidEidList.get(0)).get("PERSON_ID"));
			map.put("empId", ((Map)pidEidList.get(0)).get("EMPID"));
		}
		
		return map;
	}
	
	
	
	 
	
	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonCnt2")
	@ResponseBody
	public Map getPersonCnt2(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
	 	List pidEidList=this.empInfoSer.getPidEidList2(request);
		
		map.put("perCnt", pidEidList.size());
		if(pidEidList.size()==1){
			map.put("personId", ((Map)pidEidList.get(0)).get("PERSON_ID"));
			map.put("empId", ((Map)pidEidList.get(0)).get("EMPID"));
		}
		
		return map;
	}
	
	
	
	/**
	 * 跳转到上传照片页面(turn to phothChange)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/phothChange")
	public ModelAndView phothChange(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		modelMap.put("EMPID",request.getParameter("EMPID"));
		modelMap.put("PERSON_ID",request.getParameter("PERSON_ID"));
		modelMap.put("CPNY_ID",request.getParameter("CPNY_ID"));
		
		return new ModelAndView("/ess/empinfo/phothChange",modelMap);		
	} 
	
	
	
	/**
	 * 上传照片(Upload Photo)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadPhoto")
	public ModelAndView uploadPhoto(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String cpnyId = request.getParameter("CPNY_ID");
		String EMPID = request.getParameter("EMPID");
		
		String sign="1";
		try {
			//byte[] image = null;
			uploadpic up = new uploadpic(request, response);
				
				if (up.getdata()) {
					//String fileName = "";
					up.initFileComents();
					up.disposeData(EMPID);
					up.deletefile(cpnyId, EMPID + ".jpg");
					up.setFilePath("",cpnyId);
					sign=up.WriteMdata();
				}
			
				FtpUploadFileSample ftpUpload = new FtpUploadFileSample(request, response);
				
				sign = ftpUpload.upload(cpnyId,EMPID);
				String path = config.getString("hrm.photo.path") != null ? config.getString("hrm.photo.path") : "";
				
				if("1".equals(sign)){
					int returnInt = this.empInfoSer.editPhotoPath(request,path);
					
					if(returnInt == 1){
						modelMap.put("sign", "1");
						modelMap.put("statusCode", "200");
						map.put("message", TipMessage.getTipMessage("hr.alert.message.upload_success",request));//上传成功
						modelMap.put("navTabId", "hr0101");
						modelMap.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101");
					}else{
						modelMap.put("sign", "fail");
					}
				}else if("-1".equals(sign)){
					modelMap.put("sign", "-1");
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("hr.alert.message.create_file_fail",request));//文件创建失败
					modelMap.put("navTabId", "hr0101");
					modelMap.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101");
				}else if("-2".equals(sign)){
					modelMap.put("sign", "-2");
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("hr.alert.message.file_transfer_failure",request));//文件传输失败
					modelMap.put("navTabId", "hr0101");
					modelMap.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101");
				}else if("-3".equals(sign)){
					modelMap.put("sign", "-3");
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("hr.alert.message.potos_uploaded_failure",request));//照片上传失败
					modelMap.put("navTabId", "hr0101");
					modelMap.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101");
				}else if("-4".equals(sign)){
					modelMap.put("sign", "-4");
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("hr.alert.message.portal_uploaded_failure",request));//上传portal失败
					modelMap.put("navTabId", "hr0101");
					modelMap.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101");
				}else if("-5".equals(sign)){
					modelMap.put("sign", "-5");
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("hr.alert.message.photo_size",request));//照片文件超过规定2M,上传失败
					modelMap.put("navTabId", "hr0101");
					modelMap.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101");
				}else{
					modelMap.put("sign", "0");
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("hr.alert.message.not_upload_file",request));//文件未上传
					modelMap.put("navTabId", "hr0101");
					modelMap.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101");
				}
			} catch (Exception e) {
				e.printStackTrace();
				modelMap.put("sign", "fail");
			}
		return new ModelAndView("/ess/empinfo/uploadPhoto",modelMap);		
	}
	
	/**
	 * 转到添加残疾信息(Turn to add disabled information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/viewDisabledInfo")
	public ModelAndView viewDisabledInfo(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		modelMap.put("disabledTypeList", empInfoSer.getCodeList("123264", request));
		modelMap.put("disabilityLevelList", empInfoSer.getCodeList("123279", request));
		modelMap.put("disabilityValidityList", empInfoSer.getCodeList("123462", request));
		
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewDisabledInfo",modelMap);
	}
	
	/**
	 * 添加残疾信息(add DisabledInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDisabledInfo")
	@ResponseBody
	public Map<String, Object> addDisabledInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
			int result = this.empInfoSer.addDisabledInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0108");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	
	/**
	 * 转到删除健康信息(Turn to delete disabled information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteDisabled")
	public ModelAndView deleteDisabled(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("disabledList", empInfoSer.getDisabilityinfoList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteHealth",modelMap);
	}
	
	/**
	 * 删除残疾信息(delete disabled information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteDisabledInfo")
	@ResponseBody
	public Map deleteDisabledInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteDisabledInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0101");
			//map.put("navTabId", "hr0108");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	
	/**
	 * 转到修改残疾信息(Turn to modify disabled information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateDisabledInfo")
	public ModelAndView updateDisabledInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("disabledList", empInfoSer.getDisabilityinfoList(request));
		modelMap.put("disabilityValidityList", empInfoSer.getCodeList("123462", request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/updateDisabledInfo",modelMap);	
	}
	
	/**
	 * 修改残疾信息(Modify Disabled information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editDisabledInfo")
	@ResponseBody
	public Map editDisabledInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.editDisabledInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0108");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	/**
	 * 转到添加工会信息(Turn to add health information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTradeunionInfo")
	public ModelAndView viewTradeunionInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("join_flag", empInfoSer.getJoinFlagByPersonId(request.getParameter("PERSON_ID")));
		modelMap.put("codeList",empInfoSer.getCodeList("123251",request));
		modelMap.put("paymentStatusList", empInfoSer.getCodeList("123224", request));
		modelMap.put("paymentTypeList", empInfoSer.getCodeList("211654", request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewTradeunionInfo",modelMap);
	}

	/**
	 * 添加工会信息(add addTradeunionInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTradeunionInfo")
	@ResponseBody
	public Map<String, Object> addTradeunionInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		
			int result = this.empInfoSer.addTradeunionInfo(request) ;
			if(result == 1){
				
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0108");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	/**
	 * 转到删除工会信息(Turn to delete health information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTradeunion")
	public ModelAndView deleteTradeunion(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		//modelMap.put("healthList", empInfoSer.getHealthList(request));
		modelMap.put("tradeUnionList", empInfoSer.getTradeunionList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteTradeunion",modelMap);
	}
	/**
	 * 删除工会信息(delete health information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTradeunionInfo")
	@ResponseBody
	public Map deleteTradeunionInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteTradeunionInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0101");
			//map.put("navTabId", "hr0108");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	/**
	 * 转到修改工会信息(Turn to modify health information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateTradeunion")
	public ModelAndView updateTradeunion(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("tradeUnionList", empInfoSer.getTradeunionList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		                                      
		return new ModelAndView("/ess/empinfo/updateTradeunion",modelMap);
		
	}
	/**
	 * 修改工会信息(Modify trade information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editTradeunionInfo")
	@ResponseBody
	public Map editTradeunionInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editTradeunionInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0108");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	/**
	 * 转到添加外国语信息(Turn to add LanguageLevel information)
	 * @param r1equest
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewLanguageLevelInfo")
	public ModelAndView viewLanguageLevelInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList",empInfoSer.getCodeList("123192",request));
		modelMap.put("examNameCodeList", empInfoSer.getCodeList("1394", request));
		modelMap.put("languageLevelCodeList", empInfoSer.getCodeList("1401", request));
	    request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewLanguageLevelInfo",modelMap);
	}
	/**
	 * 添加外国语信息(add viewCompetenceInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addLanguageInfo")
	@ResponseBody
	public Map<String, Object> addLanguageInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		
			int result = this.empInfoSer.addLanguageInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0108");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	/**
	 * 转到修改外国语信息(Turn to modify language information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateLanguage")
	public ModelAndView updateLanguage(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("examNameCodeList", empInfoSer.getCodeList("1394", request));
		modelMap.put("languageLevelList", empInfoSer.getLanguageLevelList(request));
		modelMap.put("languageLevelCodeList", empInfoSer.getCodeList("1401", request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		  
			
			                                      
		return new ModelAndView("/ess/empinfo/updateLanguage",modelMap);
		
	}
	/**
	 * 修改外国语信息(Modify lungaug information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editLanguageInfo")
	@ResponseBody
	public Map editLanguageInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.updateLanguageInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0108");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	/**
	 * 转到删除外国语信息(Turn to delete Language information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteLanguage")
	public ModelAndView deleteLanguage(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		//modelMap.put("healthList", empInfoSer.getHealthList(request));
		modelMap.put("languageLevelList", empInfoSer.getLanguageLevelList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteLanguage",modelMap);
	}
	/**
	 * 删除外国语信息(delete language information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteLanguageInfo")
	@ResponseBody
	public Map deleteLanguageInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteLanguageInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0101");
			//map.put("navTabId", "hr0108");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	
	
	/**
	 * 转到修改毕业学校(Turn to modify education information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateEducation")
	public ModelAndView updateEducation(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("educationList",this.empInfoSer.getEducationList(request));
		
		//专业
		//modelMap.put("subjectList",empInfoSer.getCodeList("741",request));
		//学历
		modelMap.put("degreeCodeList",empInfoSer.getCodeList("1665",request));
		//详细学历
		//modelMap.put("particularDegreeList",empInfoSer.getCodeList("123255",request));
		modelMap.put("subjectClassifyList",empInfoSer.getCodeList("123412",request));//专业分类
		modelMap.put("siteProvinceList",empInfoSer.getCodeList("4602",request));//所在地省
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/updateEducation",modelMap);	
	}
	
	/**
	 * 修改毕业信息
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio   youjia@ait.net.cn 
	* @date Jul 12, 2013 4:53:38 PM 
	* @version V1.0
	 */
	@RequestMapping(value = "/editEducation")
	@ResponseBody
	public Map editEducation(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editEducation(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0108");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	/**
	 * 通过人力关联表查询出关联的下拉列表 hr_human_relevance
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author liangwei@ait.net.cn 
	* @date 2013-8-16 下午3:50:04 
	* @version V1.0
	 */
	
	@RequestMapping(value = "/getRelevance")
	@ResponseBody
	public Map getRelevance (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
			List getRelevanceList=empInfoSer.getRelevance(request);
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			for(int i=0;i<getRelevanceList.size();i++){
				map.put((String)((Map) getRelevanceList.get(i)).get("CODE_NO"), ((Map) getRelevanceList.get(i)).get("NAME"));
			}
			return map;
		
	}
	/**
	 * ajax访问后台 用来加载ait标签
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author liangwei@ait.net.cn 
	* @date 2013-8-19 下午6:25:51 
	* @version V1.0
	 */
	
	@RequestMapping(value = "/selectTag")
	public String selectTag(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		request.setAttribute("count", request.getParameter("count"));
		request.setAttribute("name",  request.getParameter("name"));
		return "/ess/empinfo/selectTag";
	}
	
	/**
	 * 2014-04-10  测试讲解使用
	 * Test(TrainingInfo)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/viewTest" )
	public ModelAndView getViewTest(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("trainingInfoList", empInfoSer.getTrainingInfoList(request));
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
				
		modelMap.put("photoId", "viewTraining");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "123194")) ;
		
		return new ModelAndView("/ess/empinfo/viewTest",modelMap);
	}
	/**
	 * Test(add CycleParam View)
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTest")
	public ModelAndView addTest(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("defaultCpnyID", admin.getCpnyId()) ;
		return new ModelAndView("/ess/empinfo/addTest",modelMap);
	}
	/**
	 * 保存区间参数信息(add Cycle ParamInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTestInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addTestInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		//int unique = this.cycleSer.checkCycleInfoUnique(request);
	
			//int result = this.cycleSer.addCycleParamInfo(request);
		int result =this.empInfoSer.addTestInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			
			map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
			map.put("navTabId", "test0000");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;
	}
	
	/**
	 * 黑色档案首页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewBadArchivesList")
	public ModelAndView viewBadArchivesList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("codeList",empInfoSer.getCodeList("125239",request));
		modelMap.put("archiveList", empInfoSer.getBadArchivesList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer.getBadArchivesListCnt(request));
		String tabsSelected = request.getSession().getAttribute("TABS_SELECTED") ==null?"0": 
			   request.getSession().getAttribute("TABS_SELECTED").toString();
		modelMap.put("tabsSelected", tabsSelected);
		modelMap.put("toolbarInfo", toolMenuSer.getToolMenuForNo(request, "2550")) ;
		return new ModelAndView("/ess/empinfo/viewBadArchivesList",modelMap); 
	}
	
	/**
	 * 转到添加工会信息(Turn to add health information)viewBadArchinesInfo
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewBadArchivesInfo")
	public ModelAndView viewBadArchivesInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList",empInfoSer.getCodeList("125239",request));
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewBadArchivesInfo",modelMap); 
	}
	
	/**
	 * 添加或修改黑色档案(单个)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAddBadArchivesInfo")
	public ModelAndView viewAddBadArchivesInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("codeList",empInfoSer.getCodeList("125239",request));
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		return new ModelAndView("/ess/empinfo/viewAddBadArchivesInfo",modelMap); 
	}
	
	/**
	 * 添加或修改黑色档案(单个)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewUpdateBadArchivesInfo")
	public ModelAndView viewUpdateBadArchivesInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("codeList",empInfoSer.getCodeList("125239",request));
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		String ARCH_ID = request.getParameter("ARCH_ID");
		if(StringUtils.isNotBlank(ARCH_ID)){
			modelMap.put("archive", empInfoSer.getArchivesInfo(request));
		}
		return new ModelAndView("/ess/empinfo/viewUpdateBadArchivesInfo",modelMap); 
	}
	
	/**
	 * 添加工会信息(add addBadArchivesInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateBadArchivesInfo")
	@ResponseBody
	public Map<String, Object> addOrUpdateBadArchivesInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String ID = request.getParameter("ID");
		int result = 0;
		if(StringUtils.isBlank(ID)){
			result = this.empInfoSer.addBadArchivesInfo(request) ;
		}else {
			result = this.empInfoSer.editBadArchivesInfo(request);
		}
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));//保存成功
			map.put("navTabId", "hr0108");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));//保存失败
		}
		return map;		
	}

	/**
	 * 添加工会信息(add addBadArchivesInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBadArchivesInfo")
	@ResponseBody
	public Map<String, Object> addBadArchivesInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
			int result = this.empInfoSer.addBadArchivesInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0108");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
			
		return map;		
	}
	/**
	 * 转到删除工会信息(Turn to delete health information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBadArchives")
	public ModelAndView deleteBadArchives(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		//modelMap.put("healthList", empInfoSer.getHealthList(request));
		modelMap.put("badArchivesList", empInfoSer.getBadArchivesList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteBadArchives",modelMap);
	}
	/**
	 * 删除工会信息(delete health information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBadArchivesInfo")
	@ResponseBody
	public Map deleteBadArchivesInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.empInfoSer.deleteBadArchivesInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			//map.put("navTabId", "hr0101");
			map.put("navTabId", "hr0108");
			//map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	/**
	 * 转到修改工会信息(Turn to modify health information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateBadArchives")
	public ModelAndView updateBadArchives(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("badArchivesList", empInfoSer.getBadArchivesList(request));
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
	    modelMap.put("codeList",empInfoSer.getCodeList("125239",request));
		                                      
		return new ModelAndView("/ess/empinfo/updateBadArchives",modelMap);
		
	}
	/**
	 * 修改工会信息(Modify trade information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editBadArchivesInfo")
	@ResponseBody
	public Map editBadArchivesInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editBadArchivesInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
		//	map.put("navTabId", "hr0108");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	/**
	 * 预转正信息查询(Staff foundation information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/viewPerConversionList")
	public ModelAndView viewPerConversionList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2540")) ;
		
		//预转正信息列表
		modelMap.put("perConversionList", empInfoSer.getPerConversionList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer.getPerConversionListCnt(request));
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/ess/empinfo/viewPerConversionList",modelMap);
	}
	
	/**
	 * 员工基本信息查询(Staff foundation information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewUpdateEmpProduct")
	public ModelAndView viewUpdateEmpProduct(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List productList = empInfoSer.getEmpProductList(request);
		List codeList = empInfoSer.getCodeList("211424",request);
		if(productList != null){
			for(int i=0; i<productList.size(); i++){
				LinkedHashMap productMap = (LinkedHashMap)productList.get(i);
				for(int j=0;j<codeList.size();j++){
					LinkedHashMap codeMap = (LinkedHashMap)codeList.get(j);
					if(productMap.get("PRODUCT_NO").toString().equals(codeMap.get("CODE_NO").toString())){
						codeMap.put("SELECTED", 1);
						break;
					}
				}
			}
		}
	    modelMap.put("productList", productList);
	    modelMap.put("codeList", codeList);
	    modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return new ModelAndView("/ess/empinfo/viewUpdateEmpProduct",modelMap);
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
	public ModelAndView viewImportExcelTempLanguageDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paTempLanguageTempList = this.empInfoSer.getTempLanguageTempList(request);
		int paTempLanguageTempCnt = this.empInfoSer.getTempLanguageTempCnt(request , "T");
		int errorCnt = this.empInfoSer.getTempLanguageTempCnt(request , "E");
		
		modelMap.put("paTempLanguageTempList", paTempLanguageTempList);
		modelMap.put("paTempSalesTempCnt", paTempLanguageTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paTempLanguageTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempLanguageTempCnt);
		return new ModelAndView("/ess/empinfo/viewImportExcelTempLanguageDataList", modelMap);
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
	public ModelAndView viewImportExcelTempDisableDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paTempDisabledTempList = this.empInfoSer.getTempDisabledTempList(request);
		int paTempDisabledTempCnt = this.empInfoSer.getTempDisabledTempCnt(request , "T");
		int errorCnt = this.empInfoSer.getTempDisabledTempCnt(request , "E");
		
		modelMap.put("paTempDisabledTempList", paTempDisabledTempList);
		modelMap.put("paTempDisabledTempCnt", paTempDisabledTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paTempDisabledTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempDisabledTempCnt);
		return new ModelAndView("/ess/empinfo/viewImportExcelTempDisableDataList", modelMap);
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
	public ModelAndView viewImportExcelTempContactDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paTempContactTempList = this.empInfoSer.getTempcontactTempList(request);
		int paTempContactTempCnt = this.empInfoSer.getTempContactTempCnt(request , "T");
		int errorCnt = this.empInfoSer.getTempContactTempCnt(request , "E");
		
		modelMap.put("paTempContactTempList", paTempContactTempList);
		modelMap.put("paTempContactTempCnt", paTempContactTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paTempContactTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempContactTempCnt);
		return new ModelAndView("/ess/empinfo/viewImportExcelTempContactDataList", modelMap);
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
		public ModelAndView viewImportExcelTempAssistDataList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List paTempAssistTempList = this.empInfoSer.getTempAssistTempList(request);
			int paTempAssistTempCnt = this.empInfoSer.getTempAssistTempCnt(request , "T");
			int errorCnt = this.empInfoSer.getTempAssistTempCnt(request , "E");
			
			modelMap.put("paTempAssistTempList", paTempAssistTempList);
			modelMap.put("paTempAssistTempCnt", paTempAssistTempCnt);

			modelMap.put("errCnt", errorCnt);
			modelMap.put("totalCnt", paTempAssistTempCnt);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempAssistTempCnt);
			return new ModelAndView("/ess/empinfo/viewImportExcelTempAssistDataList", modelMap);
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
		public Map submitImportInfoExcelTempData (HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception{
			Map<String, Object> jo = new HashMap<String, Object>();

			String msg= this.empInfoSer.submitImportInfoExcelTempData(request);
			if("OK".equals(msg)){
				jo.put("statusCode", "200");
				jo.put("message", "提交成功");//保存成功

				if(request.getParameter("accrual") == null){
					jo.put("navTabId", "hrm4533");
				}else{
					jo.put("navTabId", "hr0601");
				}
				jo.put("callbackType", "closeCurrent");
			}else{
				jo.put("statusCode", "200");
				jo.put("message", "提交失败");//保存失败
			}
			return jo;

		}
	/**
	 * 综合数据信息导入报表导出（外国语、残疾证、）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadInfoByExcelData11")
	public void downloadInfoByExcelData11(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.getTemplateInfoByExcelData11(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet2(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
		
	}
	/**
	 * 员工基本信息导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/importEmpInfo")
	public ModelAndView importEmpInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/ess/empinfo/importEmpInfo",modelMap);
	}
	/**
	 * 员工基本信息导入结果展示页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/importEmpInfoTempList")
	public ModelAndView viewImportEmpInfoTempList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = empInfoSer.getEmpInfoTempList(request);
		int impTotalCnt = empInfoSer.getEmpInfoTempCnt(request,"T");	
		int impErrCnt   = empInfoSer.getEmpInfoTempCnt(request,"E");
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/ess/empinfo/importEmpInfoTempList",modelMap);
	}
	
	/**
	 * 员工工作经历信息导入结果展示页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value="/viewImportExcelTempWorkExperienceList")
	public ModelAndView viewImportExcelTempWorkExperienceList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = empInfoSer.getWorkExperienceInfoTempList(request);
		int impTotalCnt = empInfoSer.getWorkExperienceInfoTempCnt(request, "T");	
		int impErrCnt   = empInfoSer.getWorkExperienceInfoTempCnt(request, "E");
		modelMap.put("itemList", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/ess/empinfo/viewImportExcelTempWorkExperienceList",modelMap);
	}
	
	
	/**
	 * 员工资格证信息导入结果展示页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value="/viewImportExcelTempQualList")
	public ModelAndView viewImportExcelTempQualList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = empInfoSer.getQualInfoTempList(request);
		int impTotalCnt = empInfoSer.getQualInfoTempCnt(request, "T");	
		int impErrCnt   = empInfoSer.getQualInfoTempCnt(request, "E");
		modelMap.put("itemList", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/ess/empinfo/viewImportExcelTempQualList",modelMap);
	}
	
	/**
	 * 员工兼卖产品信息导入结果展示页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value="/viewImportProductDataList")
	public ModelAndView viewImportProductDataList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = empInfoSer.getProductInfoTempList(request);
		int impTotalCnt = empInfoSer.getProductInfoTempCnt(request, "T");	
		int impErrCnt   = empInfoSer.getProductInfoTempCnt(request, "E");
		modelMap.put("itemList", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/ess/empinfo/viewImportProductDataList",modelMap);
	}
	
	/**
	 * 员工评价信息导入结果展示页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value="/viewImportExcelTempEvsList")
	public ModelAndView viewImportExcelTempEvsInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = empInfoSer.getEvsInfoTempList(request);
		int impTotalCnt = empInfoSer.getEvsInfoTempCnt(request, "T");	
		int impErrCnt   = empInfoSer.getEvsInfoTempCnt(request, "E");
		modelMap.put("itemList", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/ess/empinfo/viewImportExcelTempEvsList",modelMap);
	}
	/**
	 * 员工工会信息导入结果展示页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value="/viewImportExcelTempTradeUnionList")
	public ModelAndView viewImportExcelTempTradeUnionInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = empInfoSer.getTradeUnionInfoTempList(request);
		int impTotalCnt = empInfoSer.getTradeUnionInfoTempCnt(request, "T");	
		int impErrCnt   = empInfoSer.getTradeUnionInfoTempCnt(request, "E");
		modelMap.put("itemList", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/ess/empinfo/viewImportExcelTempTradeUnionList",modelMap);
	}
	
	
	/**
	 * 员工基本信息导入结果导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewExportEmpInfoExcelTempList")
	@ResponseBody
	public void viewExportEmpInfoExcelTempList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
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
			/* aliasNameList.add("人员类型(CHR)");*/
			 aliasNameList.add("工作类型(CHR)");
			 /*aliasNameList.add("人员类型生效日期");*/
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
		for(int i=0;i<dataList.size();i++){
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
			/*valueMap.put("CELL19", map.get("EMP_TYPE_CODE"));*/
			
			valueMap.put("CELL19", map.get("PROMTR_WORK_TP"));
			/*valueMap.put("CELL21", map.get("EMP_TYPE_START_DATE"));*/
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
			valueMap.put("CELL36", map.get("UPLOAD_ERROR_MSG") == null ? "" : map.get("UPLOAD_ERROR_MSG"));
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
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	/**
	 * 员工基本信息由临时表提交到正是表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/createImportEmpInfoTempListExcel")
	@ResponseBody
	public int createImportEmpInfoTempListExcel(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = empInfoSer.importEmpInfoTempListExcel(request);
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 员工基本信息模板下载
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadExcelTemplate")
	public void downloadExcelTemplate(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.getTemplateInfo(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
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
		List trainingTempList = this.empInfoSer.getTrainingImportTempList(request);
		
		int impTotalCnt = empInfoSer.getTrainingImportTempCnt(request, "T");	
		int impErrCnt   = empInfoSer.getTrainingImportTempCnt(request, "E");
		modelMap.put("itemList", trainingTempList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		return new ModelAndView("/ess/empinfo/viewImportTrainingDataList", modelMap);
	}
	
	
	/**
	 * 派遣地管理模版下载
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/exportPaiQianDiGuanLiMoBanModle")
	public void exportPaiQianDiGuanLiMoBanModle(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.getPaiQianDiGuanLiMoBanModleInfo(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		
		this.excelUtilSer.exportPaiQianDiModelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	/**
	 * 最低工资标准模版下载--非促销员
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanMoBanModle")
	public void exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanMoBanModle(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanMoBanModle(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);

	}
	
	@RequestMapping(value = "/exportZuiDiGongZiBiaoZhunCuXiaoYuanMoBanModle")
	public void exportZuiDiGongZiBiaoZhunCuXiaoYuanMoBanModle(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.exportZuiDiGongZiBiaoZhunCuXiaoYuanMoBanModle(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);

	}
	
	/**
	 * 派遣津贴标准模版下载
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/exportPaiQianDiJinTieBiaoZhunMoBanModle")
	public void exportPaiQianDiJinTieBiaoZhunMoBanModle(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.getPaiQianDiJinTieBiaoZhunMoBanModleInfo(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	/**
	 * 转到辅助信息viewBadArchinesInfo
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAssistInfo")
	public ModelAndView viewAssistInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewAssistInfo",modelMap); 
	}

	/**
	 * 添加辅助信息(add addAssistInfo)
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAssistInfo")
	@ResponseBody
	public Map<String, Object> addAssistInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		
			int result = this.empInfoSer.addAssistInfo(request) ;
			if(result == 1){
				
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0108");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	/**
	 * 转到辅助信息(Turn to delete health information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAssist")
	public ModelAndView deleteAssist(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		//modelMap.put("healthList", empInfoSer.getHealthList(request));
		modelMap.put("AssistList", empInfoSer.getAssistList(request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteAssist",modelMap);
	}
	/**
	 * 删除辅助信息(delete health information)
	 * @param request
	 * @author weizhengchen
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAssistInfo")
	@ResponseBody
	public Map deleteAssistInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.deleteAssistInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0101");
			//map.put("navTabId", "hr0108");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	/**
	 * 转到修改辅助信息(Turn to modify health information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateAssist")
	public ModelAndView updateAssist(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("AssistList", empInfoSer.getAssistList(request));
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
	    modelMap.put("codeList",empInfoSer.getCodeList("125239",request));
		                                      
		return new ModelAndView("/ess/empinfo/updateAssist",modelMap);
		
	}
	/**
	 * 修改辅助信息(Modify trade information)
	 * @param request
	 * @author weizhengchen
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editAssistInfo")
	@ResponseBody
	public Map editAssistInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editAssistInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	/**
	 * 获取兼卖产品信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/viewEmpInfoList")
	public ModelAndView viewEmpInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag != null && !"".equals(firstFlag)){
			//员工信息查询
			modelMap.put("empInfo", empInfoSer.getEmpInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer.getEmpInfoListCnt(request));
		}else{
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}

		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		
		return new ModelAndView("/ess/empinfo/viewEmpInfoList",modelMap);
	}
	
	@RequestMapping(value = "/viewTempEmpInfoList")
	public ModelAndView viewTempEmpInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag != null && !"".equals(firstFlag)){
			//员工信息查询
			modelMap.put("empInfo", empInfoSer.getTempEmpInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer.getTempEmpInfoListCnt(request));
		}else{
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("empTypeList", empInfoSer.getEmpTypeList(request));
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		
		return new ModelAndView("/ess/empinfo/viewTempEmpInfoList",modelMap);
	}
	
	/**
	 * 修改兼卖产品信息(Modify trade information)
	 * @param request
	 * @author weizhengchen
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editProductInfo")
	@ResponseBody
	public Map editProductInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editProductInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "hr0101");
			map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}

	/**
	 * 根据法人获取人员类型组
	 * @param request
	 * @author weizhengchen
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/getEmpTypeGroup")
	@ResponseBody
	public Map getEmpTypeGroup(HttpServletRequest request)throws Exception{
		
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
	public Map submitImportExcelWorkExperienceData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.empInfoSer.submitImportExcelWorkExperienceData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功

			if(request.getParameter("accrual") == null){
				jo.put("navTabId", "hrm4533");
			}else{
				jo.put("navTabId", "hr0601");
			}
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadWorkExperienceExcelTemplateByExcelData")
	public void downloadWorkExperienceExcelTemplateByExcelData(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
	List aliasNameList = new ArrayList();
	List list = new ArrayList();
	List mapList = new ArrayList();
	List mapNameList = new ArrayList();
	String name = empInfoSer.getWorkTemplateInfoByExcelData(request, aliasNameList, list , mapList, mapNameList);
	LinkedHashMap sqlContentmap = this.excelUtilSer
			.putIntoSqlContentMap(list);
	this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	
}

	/**
	 * 临时职批量入职列表
	 */
	@RequestMapping(value = "/viewTmpEmpBatchList")
	public ModelAndView viewTmpEmpBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("admin", admin);

		if(!paramMap.containsKey("FROM_TIME")){
			paramMap.put("FROM_TIME", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("TO_TIME", DateUtil.getSysdateStr());			
		}

		modelMap.put("searchMap", paramMap);
		
        String seach_FIRST_FLAG = request.getParameter("seach_FIRST_FLAG");
        if(seach_FIRST_FLAG != null && !"".equals(seach_FIRST_FLAG)){
    		modelMap.put("tmpEmpAffirmList", empInfoSer.getTmpEmpAffirmInfoListBatch(request));
    		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer.getTmpEmpAffirmInfoListCntBatch(request));
        }
		
		return new ModelAndView("/ess/empinfo/viewTmpEmpBatchList", modelMap);
	}

	/**
	 * 临时职批量导入模板
	 */
	@RequestMapping(value = "/expEmpBatchTemp")
	public void expEmpBatchTemp(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = empInfoSer.getEmpBatchTemp(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		
		this.excelUtilSer.exportExcelMoreSheetAndMoreContent(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/viewImportTempEmpResultList")
	public ModelAndView viewImportTempEmpResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());

		List itemList   = empInfoSer.getImportTmpEmpResultList(request, searchMap);
		int impTotalCnt = empInfoSer.getImportTmpEmpResultCnt(request, searchMap);	
		int impErrCnt   = empInfoSer.getImportTmpEmpErrCnt(request, searchMap);
		modelMap.put("item", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "300105"));
		return new ModelAndView("/ess/empinfo/viewImportTempEmpResultList",modelMap);
	}

	@RequestMapping(value = "/createImportTmpEmpResult")
	@ResponseBody
	public int createImportTmpEmpResult(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = empInfoSer.importTmpEmpFromExcel(request,response,modelMap);
		return result.equals("OK")?1:0;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewImportTmpEmpResultExcel")
	public ModelAndView viewImportTmpEmpResultExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List itemList = empInfoSer.getImportTmpEmpFromExcel(request) ;
		modelMap.put("itemList", itemList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/ess/empinfo/viewImportTmpEmpResultExcel",modelMap);
	}

	@RequestMapping(value = "/tempEmpApplyInBatch")
	@ResponseBody
	public Map<String, Object> tempEmpApplyInBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String msg = "删除";
		String op_flag = request.getParameter("BATCH_EMP_OP_FLAG");
		if("1".equals(op_flag)){
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
			map.put("message", msg + "失败！");//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	@RequestMapping(value = "/viewTempEmpBatchReqList")
	public ModelAndView viewTempEmpBatchReqList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());
		List itemList = empInfoSer.viewTempEmpBatchReq(request);
		int  totalCnt = empInfoSer.getTempEmpBatchReqCnt(request);
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("itemList", itemList);
		modelMap.put("totalCnt", totalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, totalCnt);
		modelMap.put("affirmorList", transactionViewSer.getApplyFeeList("", request));
		String   ls = "";
		String[] isChecked = request.getParameterValues("tempEmpCheck");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
			}
		}
		modelMap.put("BATCH_NOS", ls);
		
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "300105"));
		return new ModelAndView("/ess/empinfo/viewTempEmpBatchReqList",modelMap);
	}

	@RequestMapping(value = "/confirmTempEmpBatch")
	@ResponseBody
	public Map confirmTempEmpBatch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		retMap = transferOrderSer.confirmTempEmpBatch(request) ;
		String result = retMap.get("RET").toString();

		if(result.equals("1")){
			map.put("statusCode", "200");
			map.put("message", "提交申请成功");
			map.put("navTabId", "hr0517");
		}else{	
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

		List fileList =  new ArrayList();
		LinkedHashMap paraMap = new LinkedHashMap();
		paraMap.put("APPLY_TYPE", "23292329");
		paraMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		fileList = infoApplySerOt.getEssFileList(paraMap);

		modelMap.put("itemList", empInfoSer.getTempEmpBatchAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer.getTempEmpBatchAffirmCnt(request));
		modelMap.put("BATCH_NO",  request.getParameter("BATCH_NO"));
		modelMap.put("APPLY_NO",  request.getParameter("APPLY_NO"));
		modelMap.put("fileList", fileList);
		
		return new ModelAndView("/ess/empinfo/viewTempEmpBatchAffirmList", modelMap);
	}

	@RequestMapping(value = "/viewTempEmpReqDetail")
	public ModelAndView viewTempEmpReqDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map map = ObjectBindUtil.getRequestParamData(request);
		
		List affirmorList = new ArrayList();
		List checkorList = new ArrayList();
		List fileList =  new ArrayList();
		if(map.get("APPLY_NO") != null && !map.get("APPLY_NO").equals("")){
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
		
		return new ModelAndView("/ess/empinfo/viewTempEmpReqDetail", modelMap);
	}
	
	//地址信息  start
	
	/**
	 * 地址信息(social relations)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/getAddress" )
	public ModelAndView getAddress(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		modelMap.put("homeRelationList", empInfoSer.getHomeRelationList(request));
		
		
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		modelMap.put("photoId", "viewRelation");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2549")) ;
		
		return new ModelAndView("/ess/empinfo/viewRelation",modelMap);
	}
	
	
	
	/**
	 * 转到添加社会关系信息(Turn to add Relation family information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAddressInfo")
	public ModelAndView viewAddressInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(modelMap.get("PERSON_ID")==""||modelMap.get("PERSON_ID")==null){
			modelMap.put("PERSON_ID", admin.getPersonId());
			}
			
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("codeList", empInfoSer.getCodeList("1693", request));
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/viewAddressInfo",modelMap);
	}
	
	/**
	 * 添加地址信息(add familyInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAddressInfo")
	@ResponseBody
	public Map<String, Object> addAddressInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		//	String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		
			
			int result = this.empInfoSer.addAddressInfo(request) ;
			
			if(result == 1){
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
					map.put("navTabId", "ess3001");
				//	map.put("navTabId", "hr0107");
					map.put("forwardUrl","/ess/empinfo/viewPersonalInfoForEss?navTabId=ess3001") ;
				
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		return map;		
	}
	
	/**
	 * 转到删除社会关系(Turn to delete family information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAddress")
	public ModelAndView deleteAddress(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/deleteFamily",modelMap);
	}
	
	/**
	 * 删除社会关系(delete family information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAddressInfo")
	@ResponseBody
	public Map deleteAddressInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		int result = this.empInfoSer.deleteFamilyInfo(request);
		if(result == 1){
			if(isEssSystem.equals("1")){//ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//保存成功
				map.put("navTabId", "ess0101");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/essViewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=ess0101") ;
			}else{//业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	
	/**
	 * 转到地址信息(Turn to modify family information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateAddressInfo")
	public ModelAndView updateAddressInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("AddressList", empInfoSer.getAddressList(request));
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("isEssSystem", request.getParameter("isEssSystem"));
	
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/updateAddressInfo",modelMap);
	}
	
	/**
	 * 修改社会信息(Modify family information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/editAddressInfo")
	@ResponseBody
	public Map editAddressInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.editAddressInfo(request);
		String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if(result == 1){	
			if(isEssSystem.equals("1")){//ESS添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "ess0101");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/essViewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=ess0101") ;
			}else{//业务系统添加过来的
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "hr0101");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	
	
	
	
	
	/**
	 * 转到个人信息修改信息(Turn to modify family information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updatePersonalInfo")
	public ModelAndView updatePersonalInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personalInfo", empInfoSer.getEssPersonInfoById(request));
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("LOCAL_NAME", admin.getLocalName());

		
	    
		  request.getSession().setAttribute("TABS_SELECTED", request.getParameter("TABS_SELECTED"));
		return new ModelAndView("/ess/empinfo/updatePersonalInfo",modelMap);
	}
	
	

	/**
	 * 个人修正信息(Modify family information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePersonal")
	@ResponseBody
	public Map updatePersonal(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int result = this.empInfoSer.addPersonal(request);
		String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if(result == 1){	
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//修改成功
				map.put("navTabId", "ess3001");
			//	map.put("navTabId", 0"hr0107");
				map.put("forwardUrl","/ess/empinfo/viewPersonalInfoForEss?PERSON_ID=" + request.getParameter("PERSON_ID")+"&menu_code=ess3001");
		
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		
		return map;
	}
	
	

}
