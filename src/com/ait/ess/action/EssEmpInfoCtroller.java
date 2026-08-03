package com.ait.ess.action;

import java.io.File;
import com.ait.ess.dao.InfoApplyLeaveDao;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ait.hrm.service.EmpInfoSer;
import com.ait.ess.service.EssEmpInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UserConfiguration;

/**
 * 员工信息查看
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: EssEmpInfoCtroller.java
 * @Description:
 * @Create date: Feb 28, 2012 10:23:59 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 28, 2012 10:23:59 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/empinfo")
public class EssEmpInfoCtroller {
	Logger logger = Logger.getLogger(EssEmpInfoCtroller.class);
	@Autowired
	private EssEmpInfoSer empInfoSer;
	@Autowired
	private EmpInfoSer empInfoSerr;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	// 测试修改菜单
	@Autowired
	private ToolMenuSer toolMenuSer;

	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	/**
	 * 查看员工基础信息（工作信息）(view Staff foundation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEssPersonalInfo")
	public org.springframework.web.portlet.ModelAndView viewEssPersonalInfo(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		logger.info("essViewPersonalInfo.start...");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("sign", "1");
		modelMap.put("statusCode", "200");
		modelMap.put("message", TipMessage.getTipMessage(
				"hr.alert.message.upload_success", request));// 上传成功
		modelMap.put("navTabId", "ess3002");
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
	 
		//经历事项
		modelMap.put("workExperienceList", empInfoSer.getWorkExperienceList(request));
		modelMap.put("personInfo", linkMap);
		modelMap.put("forwardUrl", "/ess/empinfo/viewEssPersonalInfo?pageNum=1&menuNo=" + modelMap.get("menuNo")
				+ "&navTabId=" + modelMap.get("navTabId") + "&PERSON_ID="
				+ admin.getPersonId() + "&navTabId=ess3002");
		//培训信息
		//modelMap.put("viewTraining", empInfoSerr.viewTrainingBasic(request));
		
		//评价事项
		//List objectList = this.empInfoSerr.viewEvaluateInfo(request);
		//modelMap.put("objectList", objectList);

		//人事命令
		modelMap.put("expInsideList",empInfoSer.getExpInsideList(request));
		
		//产品信息
		//modelMap.put("ProductInfoList", empInfoSer.getProductInfoList(request));
		//合同档案
		//modelMap.put("contracList", empInfoSer.getContractList(request));

		 
		request.getSession().removeAttribute("TABS_SELECTED");
		 
		return new org.springframework.web.portlet.ModelAndView(modelMap);
	}
	

	/**
	 * 资格信息(view transation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewQualificationInfo")
	public ModelAndView viewQualificationInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
         modelMap.put("personInfo", linkMap);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		admin.getDepartment();
	
		
		//资格信息
		
	  modelMap.put("qualificationList",empInfoSer.getQualificationList(request));
		  
		  //毕业院校
	  modelMap.put("educationList",this.empInfoSer.getEducationList(request));
		//表彰事项  
		 modelMap.put("rewardList", empInfoSer.getReward(request));

		return new ModelAndView("/ess/empinfo/viewQualificationInfo", modelMap);
	}
	
	/**
	 * 学历信息(view transation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewEducationInfo")
	public ModelAndView essViewEducationInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
         modelMap.put("personInfo", linkMap);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		admin.getDepartment();
		 modelMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
		 
		 modelMap.put("defaultCpny",admin.getCpnyId());

		return new ModelAndView("/ess/empinfo/essViewEducationInfo", modelMap);
	}
	
	

	/**
	 * 查看发令信息(view transation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewPromote")
	public ModelAndView essViewPromote(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "essViewPromote");
		modelMap.put("expInsideList", empInfoSer.getExpInsideList(request));
		modelMap.put("resignationInfo", empInfoSer.getResignationInfo(request));
		return new ModelAndView("/ess/empinfo/essViewPromote", modelMap);
	}

	/**
	 * 查看评价信息(view Evaluation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewEvaluate")
	public ModelAndView essViewEvaluate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		modelMap.put("PhotoPath", PhotoPath
				+ "/"
				+ (linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID")
						.toString() : "")
				+ "/"
				+ (linkMap.get("EMPID") != null ? linkMap.get("EMPID")
						.toString() : "") + ".jpg");
		modelMap.put("photoId", "essViewEvaluate");
		modelMap.put("EvsInfo", empInfoSer.getEvsInfoList(request));
		return new ModelAndView("/ess/empinfo/essViewEvaluate", modelMap);
	}

	/**
	 * 查看奖励/惩戒信息(view REWARD/PUNISHMENT information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewReward")
	public ModelAndView essViewReward(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "essViewReward");
		modelMap.put("rewardList", empInfoSer.getReward(request));
		modelMap.put("punishmentList", empInfoSer.getPunishment(request));
		return new ModelAndView("/ess/empinfo/essViewReward", modelMap);
	}

	/**
	 * 查看兼职信息(view Plurality information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewTranslate")
	public ModelAndView essViewTranslate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "essViewTranslate");
		modelMap.put("pluralityList", empInfoSer.getPluralityList(request));
		return new ModelAndView("/ess/empinfo/essViewTranslate", modelMap);
	}

	/**
	 * 查看培训信息(view Training information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewTraining")
	public ModelAndView essViewTraining(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "essViewTraining");
		modelMap.put("trainingInfoList", empInfoSer
				.getTrainingInfoList(request));
		return new ModelAndView("/ess/empinfo/essViewTraining", modelMap);
	}

	/**
	 * 查看社会关系信息(view social relations information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewRelation")
	public ModelAndView essViewRelation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "essViewRelation");
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		return new ModelAndView("/ess/empinfo/essViewRelation", modelMap);
	}

	/**
	 * 查看健康信息(view Health information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewHealth")
	public ModelAndView essViewHealth(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "essViewHealth");
		modelMap.put("healthList", empInfoSer.getHealthList(request));
		return new ModelAndView("/ess/empinfo/essViewHealth", modelMap);
	}

	/**
	 * 查看工作经历信息(view work experience information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewWorkInfo")
	public ModelAndView essViewWorkInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		/*LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);*/
		/*modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";*/
	/*	modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");*/

	//	modelMap.put("photoId", "essViewWorkInfo");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());
		if(request.getParameter("WORK_EXPER_NO")!=null&&!request.getParameter("WORK_EXPER_NO").equals("")){
			LinkedHashMap linkMap = (LinkedHashMap)empInfoSer
		.getWorkExperienceList(request);
		modelMap.put("workExperienceList",linkMap);
		}
		modelMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
		modelMap.put("WORK_EXPER_NO",request.getParameter("WORK_EXPER_NO"));
		return new ModelAndView("/ess/empinfo/essViewWorkInfo", modelMap);
	}
	
	
	
	/**
	 * 添加工作经历信息(view work experience information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essAddWorkInfo")
		@ResponseBody
		public Map<String, Object> essAddWorkInfo(HttpServletRequest request)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.essAddWorkInfo(request);
		//String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "ess3002");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/viewEssPersonalInfo?navTabId=ess3002"+"&PERSON_ID="+admin.getPersonId()) ;
			
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;		
	}
	
	
	/**
	 * 添加产品信息(view work experience information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	    @RequestMapping(value = "/essAddProductInfo")
		@ResponseBody
		public Map<String, Object> essAddProductInfo(HttpServletRequest request)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.essAddProductInfo(request) ;
		//String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "ess3002");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/viewEssPersonalInfo?navTabId=ess3002"+"&PERSON_ID="+admin.getPersonId()) ;
			
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;		
	}

	/**
	 * 查看特殊事项(view Special matters information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewAdditional")
	public ModelAndView essViewAdditional(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		modelMap.put("PhotoPath", PhotoPath
				+ "/"
				+ (linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID")
						.toString() : "")
				+ "/"
				+ (linkMap.get("EMPID") != null ? linkMap.get("EMPID")
						.toString() : "") + ".jpg");
		modelMap.put("photoId", "essViewAdditional");
		modelMap.put("additionalList", empInfoSer.getAdditionalList(request));
		return new ModelAndView("/ess/empinfo/essViewAdditional", modelMap);
	}

	/**
	 * 查看资格信息(view Competence information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewCompetence")
	public ModelAndView essViewCompetence(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "essViewCompetence");
		modelMap.put("qualificationList", empInfoSer
				.getQualificationList(request));
		modelMap.put("languageLevelList", empInfoSer
				.getLanguageLevelList(request));
		return new ModelAndView("/ess/empinfo/essViewCompetence", modelMap);
	}

	/**
	 * 查看账户信息(view account information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewAccount")
	public ModelAndView essViewAccount(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "essViewAccount");
		modelMap.put("accountList", empInfoSer.getAccountList(request));
		return new ModelAndView("/ess/empinfo/essViewAccount", modelMap);
	}

	/**
	 * 查看担当业务信息(view act business information)
	 * 
	 * @param ac
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewActBusiness")
	public ModelAndView essViewActBusiness(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		modelMap.put("PhotoPath", PhotoPath
				+ "/"
				+ (linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID")
						.toString() : "")
				+ "/"
				+ (linkMap.get("EMPID") != null ? linkMap.get("EMPID")
						.toString() : "") + ".jpg");
		modelMap.put("photoId", "essViewActBusiness");
		modelMap.put("basicInfo", empInfoSer.getBasicInfo(request));
		return new ModelAndView("/ess/empinfo/essViewActBusiness", modelMap);
	}

	/**
	 * 查看合同信息(view contract information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewContract")
	public ModelAndView essViewContract(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "essViewContract");
		modelMap.put("contracList", empInfoSer.getContractList(request));
		modelMap.put("fileList", empInfoSer.getFileList(request));
		return new ModelAndView("/ess/empinfo/essViewContract", modelMap);
	}

	/**
	 * 查看档案信息(view files information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewFile")
	public ModelAndView essViewFile(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		modelMap.put("PhotoPath", PhotoPath
				+ "/"
				+ (linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID")
						.toString() : "")
				+ "/"
				+ (linkMap.get("EMPID") != null ? linkMap.get("EMPID")
						.toString() : "") + ".jpg");
		modelMap.put("photoId", "essViewFile");
		modelMap.put("contracList", empInfoSer.getContractList(request));
		modelMap.put("fileList", empInfoSer.getFileList(request));
		return new ModelAndView("/ess/empinfo/essViewFile", modelMap);
	}

	/**
	 * 查看出国信息(view goAbroad information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewGoAbroad")
	public ModelAndView essViewGoAbroad(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		//String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		//modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");

		modelMap.put("photoId", "essViewGoAbroad");
		List goAbroadList = empInfoSer.getGoAbroadList(request);
		List viewRegisterInfoList = empInfoSer.viewInfoList(request, "viewRegisterInfoListESS");
		modelMap.put("viewRegisterInfoList", viewRegisterInfoList);
		modelMap.put("goAbroadList", goAbroadList);
		modelMap.put("goAbroadSize", goAbroadList.size());
		return new ModelAndView("/ess/empinfo/essViewGoAbroad", modelMap);
	}

	/**
	 * 查看证照信息(view credential information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewCredential")
	public ModelAndView essViewCredential(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap
				.get("CPNY_ID").toString()
				: "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap
				.get("EMPID").toString()
				: "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID
				+ EMPID + ".jpg");

		modelMap.put("photoId", "essViewCredential");
		modelMap.put("credentialList", empInfoSer.getCredentialList(request));
		return new ModelAndView("/ess/empinfo/essViewCredential", modelMap);
	}
	
	/**
	 * 转到添加/修改产品信息(view work experience information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/essViewProductInfo")
	public ModelAndView essViewProductInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());
		if(request.getParameter("PRODUCT_NO")!=null&&!request.getParameter("PRODUCT_NO").equals("")){
			LinkedHashMap linkMap = (LinkedHashMap)empInfoSer
		.getProductInfo(request);
		modelMap.put("ProductInfoList",linkMap);
		}
		modelMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
		modelMap.put("PRODUCT_NO",request.getParameter("PRODUCT_NO"));
		return new ModelAndView("/ess/empinfo/essViewProductInfo", modelMap);
	}
	
	/**
	 * 转到修改产品信息(view work experience information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateProductInfo")
	public ModelAndView updateProductInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());
		if(request.getParameter("PRODUCT_NO")!=null&&!request.getParameter("PRODUCT_NO").equals("")){
			LinkedHashMap linkMap = (LinkedHashMap)empInfoSer
		.getProductInfo(request);
		modelMap.put("ProductInfoList",linkMap);
		}
		modelMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
		modelMap.put("PRODUCT_NO",request.getParameter("PRODUCT_NO"));
		return new ModelAndView("/ess/empinfo/updateProductInfo", modelMap);
	}
	
	
	
	/**
	 * 转到修改学历信息(view work experience information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEducationInfo")
	public ModelAndView updateEducationInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny",admin.getCpnyId());
			LinkedHashMap linkMap = (LinkedHashMap)empInfoSer
		.getEducationInfo(request);
		String educ_no = StringUtil.checkNull(request.getParameter("EDUC_NO"));
		if (!"0".equals(educ_no) && !"".equals(educ_no)) {
				// 附件下载功能
				LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "hrEducation");
				fileParam.put("APPLY_NO", educ_no);
				List fileList = infoApplyLeaveDao.getEssFileList(fileParam);

				modelMap.put("fileList", fileList);
			}
		modelMap.put("educationList",linkMap);
		modelMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
		modelMap.put("EDUC_NO",request.getParameter("EDUC_NO"));
		return new ModelAndView("/ess/empinfo/updateEducationInfo", modelMap);
	}
	
	/**
	 * 添加学历信息(view work experience information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	   @RequestMapping(value = "/essAddEducationInfo")
		@ResponseBody
		public Map<String, Object> essAddEducationInfo(HttpServletRequest request)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.essAddEducationInfo(request) ;
		//String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
				map.put("navTabId", "ess3003");
			//	map.put("navTabId", "hr0107");
				map.put("forwardUrl","/ess/empinfo/viewQualificationInfo?navTabId=ess3003"+"&PERSON_ID="+admin.getPersonId()) ;
			
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;		
	}
	   
	   
	   /**
		 * 转到添加资格信息(view work experience information)
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return ModelAndView
		 * @throws Exception
		 */
		@RequestMapping(value = "/essViewQualificationInfo")
		public ModelAndView essViewQualificationInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			modelMap.put("defaultCpny",admin.getCpnyId());
			modelMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
			
			return new ModelAndView("/ess/empinfo/essViewQualificationInfo", modelMap);
		}
		/**
		 * 添加资格信息(view work experience information)
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return ModelAndView
		 * @throws Exception
		 */
		   @RequestMapping(value = "/essAddQualificationInfo")
			@ResponseBody
			public Map<String, Object> essAddQualificationInfo(HttpServletRequest request)throws Exception{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);

			Map<String, Object> map = new HashMap<String, Object>();
			
			int result = this.empInfoSer.essAddQualificationInfo(request) ;
			//String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
			if(result == 1){
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
					map.put("navTabId", "ess3003");
				//	map.put("navTabId", "hr0107");
					map.put("forwardUrl","/ess/empinfo/viewQualificationInfo?navTabId=ess3003"+"&PERSON_ID="+admin.getPersonId()) ;
				
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
			return map;		
		}
		   /**
			 * 转到修改资格信息(view work experience information)
			 * 
			 * @param request
			 * @param response
			 * @param modelMap
			 * @return ModelAndView
			 * @throws Exception
			 */
			@RequestMapping(value = "/updateQualificationInfo")
			public ModelAndView updateQualificationInfo(HttpServletRequest request,
					HttpServletResponse response, ModelMap modelMap) throws Exception {
				
				AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				String qual_no = StringUtil.checkNull(request.getParameter("QUAL_NO"));
				if (!"0".equals(qual_no) && !"".equals(qual_no)) {
						// 附件下载功能
						LinkedHashMap fileParam = new LinkedHashMap();
						fileParam.put("APPLY_TYPE", "hrQualification");
						fileParam.put("APPLY_NO", qual_no);
						List fileList = infoApplyLeaveDao.getEssFileList(fileParam);

						modelMap.put("fileList", fileList);
					}
				modelMap.put("defaultCpny",admin.getCpnyId());
				modelMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
				modelMap.put("QUAL_NO",request.getParameter("QUAL_NO"));
				modelMap.put("qualificationInfo",
						  empInfoSer.getQualificationInfo(request));
				
				

				
				return new ModelAndView("/ess/empinfo/updateQualificationInfo", modelMap);
			}   
			
			
			/**
			 * 转到添加表彰事项(view work experience information)
			 * 
			 * @param request
			 * @param response
			 * @param modelMap
			 * @return ModelAndView
			 * @throws Exception
			 */
			@RequestMapping(value = "/essViewRewardInfo")
			public ModelAndView essViewRewardInfo(HttpServletRequest request,
					HttpServletResponse response, ModelMap modelMap) throws Exception {
				
				AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				modelMap.put("defaultCpny",admin.getCpnyId());
				modelMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
				
				return new ModelAndView("/ess/empinfo/essViewRewardInfo", modelMap);
			}
			
			
			/**
			 * 添加资格信息(view work experience information)
			 * 
			 * @param request
			 * @param response
			 * @param modelMap
			 * @return ModelAndView
			 * @throws Exception
			 */
			   @RequestMapping(value = "/essAddRewardInfo")
				@ResponseBody
				public Map<String, Object> essAddRewardInfo(HttpServletRequest request)throws Exception{
				AdminBean admin = SessionUtil.getLoginUserFromSession(request);

				Map<String, Object> map = new HashMap<String, Object>();
				
				int result = this.empInfoSer.essAddRewardInfo(request) ;
				//String isEssSystem = StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
				if(result == 1){
						map.put("statusCode", "200");
						map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//保存成功
						map.put("navTabId", "ess3003");
					//	map.put("navTabId", "hr0107");
						map.put("forwardUrl","/ess/empinfo/viewQualificationInfo?navTabId=ess3003"+"&PERSON_ID="+admin.getPersonId()) ;
					
				}else{
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
				}
				return map;		
			}
			   

				/**
				 * 转到修改表彰事项(view work experience information)
				 * 
				 * @param request
				 * @param response
				 * @param modelMap
				 * @return ModelAndView
				 * @throws Exception
				 */
				@RequestMapping(value = "/updateRewardInfo")
				public ModelAndView updateRewardInfo(HttpServletRequest request,
						HttpServletResponse response, ModelMap modelMap) throws Exception {
					
					AdminBean admin = SessionUtil.getLoginUserFromSession(request);
					modelMap.put("defaultCpny",admin.getCpnyId());
					modelMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
					//表彰事项  
					 modelMap.put("rewardInfo", empInfoSer.getRewardInfo(request));
						modelMap.put("REWARD_NO",request.getParameter("REWARD_NO"));


					return new ModelAndView("/ess/empinfo/updateRewardInfo", modelMap);
				}   
		
				
				/**
				 * 
				 * 附件上传
				 * @param request
				 * @return
				 * @throws Exception
				 */
				@SuppressWarnings("unchecked")
				@RequestMapping(value = "/upload")
				public String upload(HttpServletRequest request,
						HttpServletResponse response, ModelMap modelMap) throws Exception {

					Map<String, Object> map = new HashMap<String, Object>();
					String PERSON_ID = request.getParameter("PERSON_ID");
					MultipartHttpServletRequest multipartRequest = null;
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
					if (!(request instanceof MultipartHttpServletRequest) && multipartResolver.isMultipart(request)) {
						try {
							multipartResolver.setMaxUploadSize(20971520);
							multipartRequest = multipartResolver.resolveMultipart(request);
						} catch(MaxUploadSizeExceededException e) {
							return null;
						}
					} else if (request instanceof MultipartHttpServletRequest){
						multipartRequest = (MultipartHttpServletRequest)request;
					} else {
						return null;
					}
					AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
					 /**构建图片保存的目录**/    
					 String logoPathDir = "/resources/photo/"+admin.getCpnyId()+"APPLY";// dateformat.format(new Date());     
					 /**得到图片保存目录的真实路径**/    
					 String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);     
					/**根据真实路径创建目录**/    
					 File logoSaveFile = new File(logoRealPathDir);     
					 if(!logoSaveFile.exists()){
						 logoSaveFile.mkdirs();
					 }
					 String time=new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date() );
					/**页面控件的文件流**/
					MultipartFile multipartFile = multipartRequest.getFile("file");
					/**获取文件的后缀**/
					String suffix = multipartFile.getOriginalFilename().substring
					(multipartFile.getOriginalFilename().lastIndexOf("."));
					/**拼成完整的文件保存路径加文件**/
					String fileName = logoRealPathDir + File.separator  +time+ PERSON_ID + suffix;
					File file = new File(fileName);
					
					try {
						multipartFile.transferTo(file);
						//上传成功，保存记录
						this.empInfoSer.updateRecruitPhotoInfo(request, logoPathDir + "/"   +time+ PERSON_ID + suffix);
						map.put("statusCode", "200");
						map.put("message", TipMessage.getTipMessage("ess.viewApply.title.apply",request));//"申请成功"
						map.put("photoPath", logoPathDir + "/"  +time+ PERSON_ID + suffix);
					 } catch (IllegalStateException e) {
						e.printStackTrace();
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage("pa.salarycode.affirm.fail",request));//"申请失败"
					 } catch (IOException e) {
						e.printStackTrace();
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage("pa.salarycode.affirm.fail",request));//"申请失败"
					 }
					 ObjectMapper mapper = new ObjectMapper();
					 String content = mapper.writeValueAsString(map);
					 response.getWriter().write(content);
					 return null;
				}
				
				
}
