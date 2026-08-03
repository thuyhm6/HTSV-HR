package com.ait.hrm.action;

import java.text.SimpleDateFormat;

import com.ait.ess.dao.InfoApplyLeaveDao;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.servlet.ModelAndView;

import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.EssApplyInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;

/**
 * 个人 信息申请
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
@RequestMapping(value = "/hrm/approve")
public class EssApplyInfoApCtroller {
	Logger logger = Logger.getLogger(EssApplyInfoApCtroller.class);
	@Autowired
	private EssApplyInfoSer EssApplyInfoSer;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	@Autowired
	private EmpInfoSer empInfoSer;
	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	@Autowired
	private AuthorityUtil authorityUtil;

	/**
	 *查看申请信息(view Staff foundation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEssApplyInfo")
	public ModelAndView viewEssApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		logger.info("viewEssApplyInfo.start...");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("sign", "1");
		modelMap.put("activity_type", request.getParameter("ACTIVITY"));
		modelMap.put("KEY", request.getParameter("seach_KEY"));
		String seach_KEY= request.getParameter("seach_KEY");
		if(seach_KEY!=null){
			if(!seach_KEY.equals("")){
				modelMap.put("pernames", request.getParameter("pernames"));
			}
		}
		modelMap.put("personInfo", EssApplyInfoSer
				.getPersonalInfoByPid(request));
		if (request.getParameter("seach_sDate") == null
				|| request.getParameter("seach_sDate") == "") {

			modelMap.put("sDate", "");
			modelMap.put("eDate", "");
		} else {
			modelMap.put("sDate", request.getParameter("seach_sDate"));
			modelMap.put("eDate", request.getParameter("seach_eDate"));

		}

		modelMap.put("ESS_TYPE_CODE", request
				.getParameter("seach_ESS_TYPE_CODE"));
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));

		if (request.getParameter("seach_ACTIVITY") == null
				/*|| request.getParameter("seach_ACTIVITY") == ""*/) {

			modelMap.put("ACTIVITY", "1");
		} else {

			modelMap.put("ACTIVITY", request.getParameter("seach_ACTIVITY"));

		}

		modelMap.put("applyList", EssApplyInfoSer.applyList(request));
		modelMap.put("getLocalName", admin.getLocalName());
		return new ModelAndView("/hrm/approve/viewEssApplyInfo", modelMap);
	}

	/*
	 * viewApply start 九个页面！！
	 */
	/**
	 * 地址信息申请详细
	 */
	@RequestMapping(value = "/viewApplyAddressInfo")
	public ModelAndView viewApplyAddressInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("AddressList", EssApplyInfoSer
				.getEssAddressApplyObject(request));
		modelMap.put("AddressInfoPro", EssApplyInfoSer
				.getEssAddressApplyObject2(request));
		return new ModelAndView("/hrm/approve/viewApplyAddressInfo", modelMap);
	}

	/**
	 * 个人信息申请详细
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewApplyPersonalInfo")
	public ModelAndView viewApplyPersonalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("personalInfo", EssApplyInfoSer
				.getPersonalApplyObject(request));
		modelMap.put("personalInfoPro", EssApplyInfoSer
				.getPersonalApplyObject2(request));

		return new ModelAndView("/hrm/approve/viewApplyPersonalInfo", modelMap);
	}

	/**
	 * 学历信息详细
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewApplyEducationInfo")
	public ModelAndView viewApplyEducationInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("educationInfoList", EssApplyInfoSer
				.getEssEducationApplyObject(request));
		modelMap.put("educationInfoPro", EssApplyInfoSer
				.getEssEducationApplyObject2(request));
		String educ_no_no = StringUtil.checkNull(request.getParameter("EDUC_NO_NO"));
		modelMap.put("educ_no_no", educ_no_no);
		if (!"0".equals(educ_no_no) && !"".equals(educ_no_no)) {
				// 附件下载功能
				LinkedHashMap fileParampro = new LinkedHashMap();
				fileParampro.put("APPLY_TYPE", "hrEducation");
				fileParampro.put("APPLY_NO", educ_no_no);
				List fileList = infoApplyLeaveDao.getEssFileList(fileParampro);
				modelMap.put("fileListpro", fileList);
			}
		 Map list =(Map) EssApplyInfoSer.getEssEducationApplyObject(request);
		
		//String APPLY_TYPE_NUM= list.get("APPLY_TYPE_NUM").toString(); 判断是否是删除
		 
		 String file= (String)list.get("FILENOSSTR");
		 if(file!=null){
		 String[] FILENOSSTR =file.split(",");
		 for(int i =0;i<FILENOSSTR.length-1;i++){
			 LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "hrEducation");
				fileParam.put("FILE_NO", FILENOSSTR[i]);
				List fileLists = infoApplyLeaveDao.getEssFileList(fileParam);
				modelMap.put("fileList", fileLists);
		 	}
		 }
		return new ModelAndView("/hrm/approve/viewApplyEducationInfo", modelMap);
	}

	/**
	 * 紧急联系地址信息
	 */
	@RequestMapping(value = "/viewApplyEmergencyAddress")
	public ModelAndView viewApplyEmergencyAddress(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("EmergencyAddressList", EssApplyInfoSer
				.getEssEmergencyObject(request));
		modelMap.put("EmergencyAddressInfoPro", EssApplyInfoSer
				.getEssEmergencyObject2(request));

		return new ModelAndView("/hrm/approve/viewApplyEmergencyAddress",
				modelMap);
	}

	/**
	 * 家人关系详细信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewApplyHomeRelation")
	public ModelAndView viewApplyHomeRelation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("homeRelationList", EssApplyInfoSer
				.getEssHomeRelationApplyObject(request));

		modelMap.put("homeRelationInfoPro", EssApplyInfoSer
				.getEssHomeRelationApplyObject2(request));

		return new ModelAndView("/hrm/approve/viewApplyHomeRelation", modelMap);
	}

	/**
	 * 产品信息详细
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewApplyProductInfo")
	public ModelAndView viewApplyProductInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("ProductInfoList", EssApplyInfoSer
				.getEssProductApplyObject(request));

		modelMap.put("ProductInfoPro", EssApplyInfoSer
				.getEssProductApplyObject2(request));

		return new ModelAndView("/hrm/approve/viewApplyProductInfo", modelMap);
	}

	/**
	 * 资格信息详细
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewApplyQualificationInfo")
	public ModelAndView viewApplyQualificationInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("qualificationInfo", EssApplyInfoSer
				.getEssQualificationApplyObject(request));
		modelMap.put("qualificationInfoPro", EssApplyInfoSer
				.getEssQualificationApplyObject2(request));
		
		String QUAL_NO_NO = StringUtil.checkNull(request.getParameter("QUAL_NO_NO"));
		modelMap.put("QUAL_NO_NO", QUAL_NO_NO);
		if (!"0".equals(QUAL_NO_NO) && !"".equals(QUAL_NO_NO)) {
				// 附件下载功能
				LinkedHashMap fileParampro = new LinkedHashMap();
				fileParampro.put("APPLY_TYPE", "hrQualification");
				fileParampro.put("APPLY_NO", QUAL_NO_NO);
				List fileList = infoApplyLeaveDao.getEssFileList(fileParampro);
				modelMap.put("fileListpro", fileList);
			}
		 Map list =(Map) EssApplyInfoSer.getEssQualificationApplyObject(request);
		
		//String APPLY_TYPE_NUM= list.get("APPLY_TYPE_NUM").toString(); 判断是否是删除
		 
		 String file= (String)list.get("FILENOSSTR");
		 if(file!=null){
		 String[] FILENOSSTR =file.split(",");
		 for(int i =0;i<FILENOSSTR.length-1;i++){
			 LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "hrQualification");
				fileParam.put("FILE_NO", FILENOSSTR[i]);
				List fileLists = infoApplyLeaveDao.getEssFileList(fileParam);
				modelMap.put("fileList", fileLists);
		 	}
		 }

		return new ModelAndView("/hrm/approve/viewApplyQualificationInfo",
				modelMap);
	}

	/**
	 * 奖励信息申请详细
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewApplyRewardInfo")
	public ModelAndView viewApplyRewardInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("rewardInfo", EssApplyInfoSer.getEssRewardObject(request));
		modelMap.put("rewardInfoPro", EssApplyInfoSer
				.getEssRewardObject2(request));

		return new ModelAndView("/hrm/approve/viewApplyRewardInfo", modelMap);
	}

	@RequestMapping(value = "/viewApplyWorkInfo")
	public ModelAndView viewApplyWorkInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("workExperienceList", EssApplyInfoSer
				.getEssWorkApplyObject(request));
		modelMap.put("workExperienceInfoPro", EssApplyInfoSer
				.getEssWorkApplyObject2(request));

		return new ModelAndView("/hrm/approve/viewApplyWorkInfo", modelMap);
	}

	/*
	 * viewApply end
	 */

	// 综合简介查询弹出页面 放大镜
	@RequestMapping(value = "/viewEmpInfoListSearch")
	public ModelAndView viewEmpInfoTanchuList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		String searchChange = StringUtil.checkNull(request
				.getParameter("searchChange"));
		if (firstFlag != null && !"".equals(firstFlag)) {
			// 员工信息查询
			modelMap.put("empInfo", empInfoSer.getEmpInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer
					.getEmpInfoListCnt(request));
		} else {
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("KEY", StringUtil.checkNull(request
				.getParameter("seach_KEY")));
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil
				.isSuperUser(admin.getPersonId()));
		modelMap.put("searchChange", searchChange);
		modelMap.put("defaultCpny",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		// 分页
		modelMap.put("pageNum", request.getParameter("pageNum"));
		modelMap.put("numPerPage", request.getParameter("numPerPage"));
		return new ModelAndView("/hrm/approve/viewEmpInfoListSearch", modelMap);
	}

	/**
	 * 审批十个 增删改统一为 update APPLY_TYPE 1添加 2修改 3删除
	 */
	/**
	 * 添加学历信息(view work experience information)
	 */
	@RequestMapping(value = "/updateEducationInfo")
	@ResponseBody
	public Map<String, Object> updateEducationInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.EssApplyInfoSer.updateEducationInfo(request);
		// String isEssSystem =
		// StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
			map.put("formId", "viewEssApplyInfo_form");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.cshibai", request));//"审批失败"
		}
		return map;
	}

	@RequestMapping(value = "/updateAddressInfo")
	@ResponseBody
	public Map<String, Object> updateAddressInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.EssApplyInfoSer.updateAddressInfo(request);
		// String isEssSystem =
		// StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
			map.put("formId", "viewEssApplyInfo_form");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.cshibai", request));//"审批失败"
		}
		return map;
	}

	@RequestMapping(value = "/updatePersonalInfo")
	@ResponseBody
	public Map<String, Object> updatePersonalInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.EssApplyInfoSer.updatePersonalInfo(request);
		// String isEssSystem =
		// StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
			map.put("formId", "viewEssApplyInfo_form");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.cshibai", request));//"审批失败"
		}
		return map;
	}

	@RequestMapping(value = "/updateHomeRelationInfo")
	@ResponseBody
	public Map<String, Object> updateHomeRelationInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.EssApplyInfoSer.updateHomeRelationInfo(request);
		// String isEssSystem =
		// StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
			map.put("formId", "viewEssApplyInfo_form");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.cshibai", request));//"审批失败"
		}
		return map;
	}

	@RequestMapping(value = "/updateEmergencyInfo")
	@ResponseBody
	public Map<String, Object> updateEmergencyInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.EssApplyInfoSer.updateEmergencyInfo(request);
		// String isEssSystem =
		// StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
			map.put("formId", "viewEssApplyInfo_form");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.cshibai", request));//"审批失败"
		}
		return map;
	}

	@RequestMapping(value = "/updateWorkInfo")
	@ResponseBody
	public Map<String, Object> updateWorkInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.EssApplyInfoSer.updateWorkInfo(request);
		// String isEssSystem =
		// StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
			map.put("formId", "viewEssApplyInfo_form");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.cshibai", request));//"审批失败"
		}
		return map;
	}

	@RequestMapping(value = "/updateProductInfo")
	@ResponseBody
	public Map<String, Object> updateProductInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.EssApplyInfoSer.updateProductInfo(request);
		// String isEssSystem =
		// StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
			map.put("formId", "viewEssApplyInfo_form");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.cshibai", request));//"审批失败"
		}
		return map;
	}

	@RequestMapping(value = "/updateQualificationInfo")
	@ResponseBody
	public Map<String, Object> updateQualificationInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.EssApplyInfoSer.updateQualificationInfo(request);
		// String isEssSystem =
		// StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
			map.put("formId", "viewEssApplyInfo_form");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.cshibai", request));//"审批失败"
		}
		return map;
	}

	@RequestMapping(value = "/updateRewardInfo")
	@ResponseBody
	public Map<String, Object> updateRewardInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.EssApplyInfoSer.updateRewardInfo(request);

		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
			map.put("formId", "viewEssApplyInfo_form");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.cshibai", request));//"审批失败"
		}
		return map;
	}

	/**
	 * 
	 */

	/**
	 * 修改申请表状态 activity 1提交 2审批 3退回 4取消
	 */

	/**
	 * 
	 */

	/**
	 * 
	 * 统计 模块
	 * 
	 * 
	 */

	/**
	 * 部门别人事统计
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ManageCountInfoList")
	public ModelAndView ManageCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String currentIndex = request.getParameter("currentIndex");
		if ("1".equals(currentIndex)) {
			modelMap.put("currentIndex", 1);
		} else if ("2".equals(currentIndex)) {
			modelMap.put("currentIndex", 2);
		} else if ("3".equals(currentIndex)) {
			modelMap.put("currentIndex", 3);
		} else if ("4".equals(currentIndex)) {
			modelMap.put("currentIndex", 4);
		} else if ("5".equals(currentIndex)) {
			modelMap.put("currentIndex", 5);
		} else {
			modelMap.put("currentIndex", 0);
		}
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("managePart", admin.getCpnyId().substring(admin.getCpnyId().lastIndexOf("_")+1));
		modelMap.put("FROM_DATE", this.getDateFirst("MM/yyyy"));
		modelMap.put("TO_DATE", this.getDateNow("MM/yyyy"));
		modelMap.put("empTypeCodeList", empInfoSer.getCodeList("13864", request));
		modelMap.put("empStatusCodeList", empInfoSer.getCodeList("1372", request));
		return new ModelAndView("/hrm/approve/ManageCountInfoList", modelMap);
	}

	/**
	 * 部门类别人事统计
	 */

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ManageCountInfoSonList")
	public ModelAndView ManageCountInfoSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		if ("0".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getParentCodeGradeList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("deptListCount3", codeInfoTreeList.get("deptListCount3"));
			modelMap.put("postGradeBGZCodeList", empInfoSer.getCodeList("14015813", request));
			modelMap.put("postGradeYBZCodeList", empInfoSer.getCodeList("14015814", request));
			modelMap.put("postGradeSCZCodeList", empInfoSer.getCodeList("14015815", request));
			modelMap.put("currentIndex", 0);
		} else if ("1".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getParentCodePositionList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 1);
		} else if ("2".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getParentCodeEduList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("deptListCount3", codeInfoTreeList.get("deptListCount3"));
			modelMap.put("currentIndex", 2);
		} else if ("3".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getParentCodeAgeList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 3);
		} else if ("4".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getParentCodeEmpTypeList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("empTypeList", empInfoSer.getCodeList("13864", request));
			modelMap.put("currentIndex", 4);
		} else if ("5".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getParentCodeWorkAgeList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 5);
		}

		return new ModelAndView("/hrm/approve/ManageCountInfoSonList", modelMap);
	}

	/**
	 * 生成部门树
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getParentTreeData")
	@ResponseBody
	public List getParentTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		LinkedHashMap map = this.EssApplyInfoSer.getParentCodeAgeList(request);
		List codeInfoTreeList = (ArrayList) map.get("deptList");
		return codeInfoTreeList;
	}

	/**
	 * 
	 * 统计 模块
	 * 
	 * 
	 */

	/**
	 * 部门别人事统计 入职区分
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hrCountInfoList")
	public ModelAndView hrCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String currentIndex = request.getParameter("currentIndex");
		if ("1".equals(currentIndex)) {
			modelMap.put("currentIndex", 1);
		} else if ("2".equals(currentIndex)) {
			modelMap.put("currentIndex", 2);
		} else if ("3".equals(currentIndex)) {
			modelMap.put("currentIndex", 3);
		} else if ("4".equals(currentIndex)) {
			modelMap.put("currentIndex", 4);
		} else {
			modelMap.put("currentIndex", 0);
		}
		modelMap.put("DEPTNO", admin.getCpnyId().substring(admin.getCpnyId().lastIndexOf("_")+1));
		modelMap.put("FROM_DATE", this.getDateFirst("dd/MM/yyyy"));
		modelMap.put("TO_DATE", this.getDateNow("dd/MM/yyyy"));
		modelMap.put("empTypeCodeList", empInfoSer.getCodeList("13864", request));

		return new ModelAndView("/hrm/approve/hrCountInfoList", modelMap);
	}

	/**
	 * 部门类别人事统计 入职区分
	 */

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hrCountInfoSonList")
	public ModelAndView hrCountInfoSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");

		if ("1".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getParentCodePositionList1(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 1);
			modelMap.put("positionCodeList", empInfoSer.getCodeListNO("'400414','400435','400429'", request));
		} else if("2".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getParentCodeGradeList1(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("deptListCount3", codeInfoTreeList.get("deptListCount3"));
			modelMap.put("postGradeBGZCodeList", empInfoSer.getCodeList("14015813", request));
			modelMap.put("postGradeYBZCodeList", empInfoSer.getCodeList("14015814", request));
			modelMap.put("postGradeSCZCodeList", empInfoSer.getCodeList("14015815", request));
			modelMap.put("currentIndex", 2);
		} else if ("0".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getParentCodeEmpTypeList1(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 0);
			modelMap.put("empTypeCodeList", empInfoSer.getCodeList("13864", request));
		}

		return new ModelAndView("/hrm/approve/hrCountInfoSonList", modelMap);
	}

	/**
	 * 部门别人事统计退职区分
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hrResignCountInfoList")
	public ModelAndView hrResignCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String currentIndex = request.getParameter("currentIndex");

		modelMap.put("FROM_DATE", this.getDateFirst("dd/MM/yyyy"));
		modelMap.put("TO_DATE", this.getDateNow("dd/MM/yyyy"));
		if ("1".equals(currentIndex)) {
			modelMap.put("currentIndex", 1);
		} else if ("2".equals(currentIndex)) {
			modelMap.put("currentIndex", 2);

		} else if ("3".equals(currentIndex)) {

			modelMap.put("currentIndex", 3);
		} else if ("4".equals(currentIndex)) {
			modelMap.put("currentIndex", 4);
		} else {
			modelMap.put("currentIndex", 0);
		}
		if (admin.getCpnyId().equals("TSTO")) {
			modelMap.put("managePart", "D11EQ000");
		} else {
			modelMap.put("managePart", "D11AR000");
		}
		modelMap.put("empTypeCodeList", empInfoSer.getCodeList("13864", request));

		return new ModelAndView("/hrm/approve/hrResignCountInfoList", modelMap);
	}

	/**
	 * 
	 * 退职区分
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hrResignCountInfoSonList")
	public ModelAndView hrResignCountInfoSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		
		if ("0".equals(currentIndex)) {

			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getParentCodeGradeList2(request);

			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("deptListCount3", codeInfoTreeList.get("deptListCount3"));
			modelMap.put("currentIndex", 0);
			modelMap.put("postGradeBGZCodeList", empInfoSer.getCodeList("14015813", request));
			modelMap.put("postGradeYBZCodeList", empInfoSer.getCodeList("14015814", request));
			modelMap.put("postGradeSCZCodeList", empInfoSer.getCodeList("14015815", request));
		} else if ("1".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getParentCodePositionList2(request);

			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 1);
		} else if ("2".equals(currentIndex)) {

			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getResignResonList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 2);
			modelMap.put("resignResonList", empInfoSer.getCodeList("14013966", request));
		} else {
			LinkedHashMap codeInfoTreeList = this.EssApplyInfoSer.getParentCodeEmpTypeList2(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 4);
			modelMap.put("empTypeCodeList", empInfoSer.getCodeList("13864", request));
		}

		return new ModelAndView("/hrm/approve/hrResignCountInfoSonList",modelMap);
	}

	// 批量 审批

	@RequestMapping(value = "/essApplyBatchApproval")
	@ResponseBody
	public Map<String, Object> essApplyBatchApproval(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.EssApplyInfoSer.essApplyBatchApproval(request);

		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.cshibai", request));//"审批失败"
		}
		return map;
	}

	// 获取当前月第一天 传入的是 日期格式
	public String getDateFirst(String forma) {
		SimpleDateFormat format = new SimpleDateFormat(forma);
		String first = "";
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
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
	/**
	 * 
	 * （公用）人事部门统计与ess部门长部门别统计
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthPersonCountInfoSonList")
	public ModelAndView monthPersonCountInfoSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		
		List monthPersonCountInfoSonList = this.EssApplyInfoSer.monthPersonCountInfoSonList(request);
		
		modelMap.put("monthPersonCountInfoSonList", monthPersonCountInfoSonList);
		
		String index = request.getParameter("INDEX");
		modelMap.put("INDEX", index);
		String deptno = request.getParameter("seach_DEPTNO");
		modelMap.put("DEPTNO", deptno);
		if(index.equals("0")){
			modelMap.put("GRADE", request.getParameter("GRADE"));
		}else if(index.equals("1")){
			modelMap.put("MONTH", request.getParameter("MONTH"));
			modelMap.put("LIZHI", request.getParameter("type"));
		}else if(index.equals("2")){
			if(request.getParameter("strFlag").toString().equals("add")){
				if(!request.getParameter("EDU").equals("0")){
					modelMap.put("EDU", request.getParameter("EDU"));
				}else{
					modelMap.put("SEX", request.getParameter("SEX"));
				}
			}else{
				modelMap.put("RESIGNRESON", request.getParameter("RESIGNRESON"));
			}
		}else if(index.equals("3")){
			modelMap.put("MINAGE", request.getParameter("MINAGE"));
			modelMap.put("MAXAGE", request.getParameter("MAXAGE"));
		}else if(index.equals("4")){
			modelMap.put("empTypeForTable", request.getParameter("empTypeForTable"));
		}else if(index.equals("5")){
			modelMap.put("MINWORKAGE", request.getParameter("MINWORKAGE"));
			modelMap.put("MAXWORKAGE", request.getParameter("MAXWORKAGE"));
		}
		// 增加人员 减少人员标识
		modelMap.put("strFlag", request.getParameter("strFlag"));
		modelMap.put("FROM_DATE", request.getParameter("seach_FROM_DATE"));
		modelMap.put("END_DATE", request.getParameter("seach_TO_DATE"));

		if (request.getParameter("currentIndex") != null
				&& !request.getParameter("currentIndex").equals("")) {
			modelMap.put("currentIndex", request.getParameter("currentIndex"));
		} else {
			modelMap.put("currentIndex", 0);

		}
		// 月环比人员
		return new ModelAndView("/pa/workManagement/monthPersonCountInfoSonList", modelMap);
	}
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthPersonCountInfoSonList1")
	public ModelAndView monthPersonCountInfoSonList1(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		
		List monthPersonCountInfoSonList = this.EssApplyInfoSer.monthPersonCountInfoSonList1(request);
		
		modelMap.put("monthPersonCountInfoSonList", monthPersonCountInfoSonList);
		
		String index = request.getParameter("INDEX");
		modelMap.put("INDEX", index);
		String deptno = request.getParameter("seach_DEPTNO");
		modelMap.put("DEPTNO", deptno);
		if(index.equals("0")){
			modelMap.put("EMPTYPE", request.getParameter("EMPTYPE"));
		}else if(index.equals("1")){
			modelMap.put("SOCIAL_DIFFERENTIATION", request.getParameter("SOCIAL_DIFFERENTIATION"));
		}else if(index.equals("2")){
			modelMap.put("GRADE", request.getParameter("GRADE"));
		}
		// 增加人员 减少人员标识
		modelMap.put("strFlag", request.getParameter("strFlag"));

		if (request.getParameter("currentIndex") != null
				&& !request.getParameter("currentIndex").equals("")) {
			modelMap.put("currentIndex", request.getParameter("currentIndex"));
		} else {
			modelMap.put("currentIndex", 0);

		}
		// 月环比人员
		return new ModelAndView("/hrm/approve/monthPersonCountInfoSonList1", modelMap);
	}
}
