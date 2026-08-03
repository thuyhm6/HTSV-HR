package com.ait.evs.action;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.evs.service.EvsManageSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.HtmlRegexpUtil;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UserConfiguration;
import com.ait.web.i18n.TipMessage;

/**
 * 
 * @fileName: EvsManageCtroller.java 
 * @Create by: wzc(weizhengchen@ait.net.cn)
 * @version 5.5
 */
@Controller
@RequestMapping(value = "/evs/manage")
public class EvsManageCtroller {
	Logger logger = Logger.getLogger(EvsManageCtroller.class);

	@Autowired
	private EvsManageSer evsManageSer;

	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;

	@Autowired
	private EmpInfoSer empInfoSer;
	
	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	/**
	 * 考核首页
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvsIndex")
	public ModelAndView viewOrgIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
		return new ModelAndView("/evs/manage/viewEvsIndex", modelMap);
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

		List resumeList = this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList", resumeList);

		LinkedHashMap param = null;
		
		if(resumeList!=null && resumeList.size() > 0){
			param = (LinkedHashMap)resumeList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("resumeSize", resumeList.size());
		}else{
			modelMap.put("resumeSize", 0);
		}
		modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
		return new ModelAndView("/evs/manage/viewResumeList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddResumeInfo")
	public ModelAndView viewAddResumeInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		
		LinkedHashMap param = new LinkedHashMap();
		
		if(resumeList!=null && resumeList.size() > 0){
			param = (LinkedHashMap)resumeList.get(0);
			
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "EVS_RESUME");
			fileParam.put("APPLY_NO", param.get("SEQ"));
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList",fileList);
		}
		modelMap.put("resumeInfo", param);
		modelMap.put("resumeCodeList", this.evsManageSer.viewEvsInfoList(request,"viewResumeCodeList"));
		String evsType = request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType");
		modelMap.put("evsType", evsType);
		modelMap.put("navTabId", "performance".equals(evsType) ? "evs0101" : "evs0201");
		return new ModelAndView("/evs/manage/viewAddResumeInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addResumeInfo")
	@ResponseBody
	public Map addResumeInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//seq不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if(!"".equals(seq)){
			result = this.evsManageSer.addEvsInfo(request, "updateResumeInfo");
		}else{
			result = this.evsManageSer.addEvsResumeInfo(request);
		}
		String evsType = request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
			if("performance".equals(evsType)){
				map.put("navTabId", "evs0101");
			}else{
				map.put("navTabId", "evs0201");
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));// 保存失败！
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteResumeInfo")
	@ResponseBody
	public Map deleteResumeInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsInfo(request,"deleteResumeInfo");
		String evsType = request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功！
			if("performance".equals(evsType)){
				map.put("navTabId", "evs0101");
			}else{
				map.put("navTabId", "evs0201");
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败！
		}
		return map;
	}
	
	/**
	 * 考核进行 日程管理
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvsSchedulePanel")
	public ModelAndView viewEvsSchedulePanellList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");

		if("1".equals(currentIndex)){
			modelMap.put("currentIndex", 1);
		}else if("2".equals(currentIndex)){
			modelMap.put("currentIndex", 2);
		}else{
			modelMap.put("currentIndex", 0);
		}
		String evsType = request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType");
		List resumeList = null;
		if("probation".equals(evsType)){
			resumeList = this.evsManageSer.viewEvsInfoList(request,"viewProbationResumeList");
		}else{
			resumeList = this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		}
		modelMap.put("resumeList",resumeList);
		//第一次进入页面取RESUME_SEQ，以便其他信息查询
		this.getResumeSeq(request, resumeList, modelMap);
		modelMap.put("evsType", evsType);
		
		return new ModelAndView("/evs/manage/viewEvsSchedulePanel", modelMap);
	}

	/**
	 * 变更详细信息查看List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsScheduleInfoList")
	public ModelAndView viewEvsScheduleInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		if(request.getParameter("FLAG") == null){
			this.evsManageSer.addEvsInfo(request,"deleteEmptyScheduleInfo");
		}else{
			this.evsManageSer.addEvsInfo(request,"addScheduleInfo");
		}
		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){ //部门日程
			List scheduleInfoList = this.evsManageSer.viewEvsInfoList(request,"viewScheduleList");
			modelMap.put("scheduleInfoList", scheduleInfoList);
			modelMap.put("scheduleInfoListSize", scheduleInfoList == null ? 0 : scheduleInfoList.size());
			modelMap.put("currentIndex", 1);
			String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || GET_DEPT_NAME(DEPTNO, '"+admin.getLanguage()+"') DESCRIPTION,DEPTNO CODE_NO,GET_DEPT_NAME(DEPTNO, '"+admin.getLanguage()+"') CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ";
			modelMap.put("dept" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(DEPT_NO_SQL)));  //部门
		}else if("2".equals(currentIndex)){ //个人日程
			List scheduleInfoList = this.evsManageSer.viewEvsInfoList(request,"viewScheduleList");
			modelMap.put("scheduleInfoList", scheduleInfoList);
			modelMap.put("scheduleInfoListSize", scheduleInfoList == null ? 0 : scheduleInfoList.size());
			modelMap.put("currentIndex", 2);
		}else{ //公司日程
			List scheduleInfoList = this.evsManageSer.viewEvsInfoList(request,"viewScheduleList");
			modelMap.put("scheduleInfoList", scheduleInfoList);
			modelMap.put("scheduleInfoListSize", scheduleInfoList == null ? 0 : scheduleInfoList.size());
			modelMap.put("currentIndex", 0);
		}
		
		modelMap.put("evsStep" , JsonUtil.writeInternal(empInfoSer.getCodeList("14015065", request))); //评价进行区分
		
		return new ModelAndView("/evs/manage/viewEvsScheduleInfoList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addScheduleInfo")
	@ResponseBody
	public Map addScheduleInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 1;
		String currentIndex = request.getParameter("currentIndex");
		if("2".equals(currentIndex)){ //个人
			result = this.evsManageSer.addEvsInfoByJson(request,"updateScheduleInfoByEmp");
		}else{
			result = this.evsManageSer.addEvsInfoByJson(request,"updateScheduleInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
			map.put("formId", "viewEvsSchedulePanelForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));// 保存失败！
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteScheduleInfo")
	@ResponseBody
	public Map deleteScheduleInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.deleteEvsInfo(request,"deleteScheduleInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功！
			map.put("formId", "viewEvsSchedulePanelForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败！
		}
		return map;
	}
	
	/**
	 * 第一次进入页面取RESUME_SEQ，以便其他信息查询
	 * @param request
	 * @param resumeList
	 * @param modelMap
	 */
	private void getResumeSeq(HttpServletRequest request,List resumeList,ModelMap modelMap){
		String RESUME_SEQ = StringUtil.checkNull(request.getParameter("RESUME_SEQ"));
		if(!"".equals(RESUME_SEQ)){
			request.getSession().setAttribute("RESUME_SEQ_SESSION",RESUME_SEQ);
		}
		String RESUME_SEQ_SESSION = StringUtil.checkNull(request.getSession().getAttribute("RESUME_SEQ_SESSION"));
		//第一次进入页面取RESUME_SEQ，以便其他信息查询
		if("".equals(RESUME_SEQ)){
			if(!"".equals(RESUME_SEQ_SESSION)){
				RESUME_SEQ = RESUME_SEQ_SESSION;
				modelMap.put("RESUME_SEQ", RESUME_SEQ_SESSION);
			}else{
				if(resumeList != null && resumeList.size() > 0){
					Map map = (Map)resumeList.get(0);
					modelMap.put("RESUME_SEQ", map.get("SEQ"));
					RESUME_SEQ = map.get("SEQ").toString();
					request.getSession().setAttribute("RESUME_SEQ_SESSION", map.get("SEQ"));
				}
			}
		}
		if(RESUME_SEQ != null){
			for(int i=0;i<resumeList.size();i++){
				Map subMap = (LinkedHashMap)resumeList.get(i);
				if(RESUME_SEQ.equals(StringUtil.checkNull(subMap.get("SEQ")))){
					modelMap.put("ACTIVITY", subMap.get("ACTIVITY"));
					break;
				}
			}
		}
	}
	
	/**
	 * 第一次进入页面取RESUME_SEQ，以便其他信息查询
	 * @param request
	 * @param resumeList
	 * @param modelMap
	 */
	private void getResumeSeqPartner(HttpServletRequest request,List resumeList,ModelMap modelMap){
		String RESUME_SEQ = StringUtil.checkNull(request.getParameter("RESUME_SEQ"));
		//第一次进入页面取RESUME_SEQ，以便其他信息查询
		if("".equals(RESUME_SEQ)){
			if(resumeList != null && resumeList.size() > 0){
				Map map = (Map)resumeList.get(0);
				RESUME_SEQ = map.get("SEQ").toString();
				modelMap.put("RESUME_SEQ", map.get("SEQ"));
			}
		}
		if(RESUME_SEQ != null){
			for(int i=0;i<resumeList.size();i++){
				Map subMap = (LinkedHashMap)resumeList.get(i);
				if(RESUME_SEQ.equals(StringUtil.checkNull(subMap.get("SEQ")))){
					modelMap.put("EVS_CYCLE", subMap.get("EVS_CYCLE"));
					break;
				}
			}
		}
	}

	/**
	 * 用公司日程的数据 同步个人日程
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/syncScheduleInfo")
	@ResponseBody
	public Map syncScheduleInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsInfo(request,"syncScheduleInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.sync_success", request)); //同步成功!
			map.put("formId", "viewEvsSchedulePanelForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.sync_fail", request));//同步失败!
		}
		return map;
	}

	/**
	 * 考核运营 基准
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvsParamPanel")
	public ModelAndView viewEvsParamPanelList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");

		if("1".equals(currentIndex)){
			modelMap.put("currentIndex", 1);
		}else if("2".equals(currentIndex)){
			modelMap.put("currentIndex", 2);
		}else if("3".equals(currentIndex)){
			modelMap.put("currentIndex", 3);
		}else if("4".equals(currentIndex)){
			modelMap.put("currentIndex", 4);
		}else if("5".equals(currentIndex)){
			modelMap.put("currentIndex", 5);
		}else if("6".equals(currentIndex)){
			modelMap.put("currentIndex", 6);
		}else if("7".equals(currentIndex)){
			modelMap.put("currentIndex", 7);
		}else{
			modelMap.put("currentIndex", 0);
		}
		String evsType = request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType");
		List resumeList = null;
		if("probation".equals(evsType)){
			resumeList = this.evsManageSer.viewEvsInfoList(request,"viewProbationResumeList");
		}else{
			resumeList = this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		}
		modelMap.put("resumeList",resumeList);
		//第一次进入页面取RESUME_SEQ，以便其他信息查询
		this.getResumeSeq(request, resumeList, modelMap);
		modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
		
		return new ModelAndView("/evs/manage/viewEvsParamPanel", modelMap);
	}

	/**
	 * 变更详细信息查看List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsParamInfoList")
	public ModelAndView viewEvsParamInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String currentIndex = request.getParameter("currentIndex");
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		if("1".equals(currentIndex)){ //Grade by item
			if(request.getParameter("FLAG") == null){
				this.evsManageSer.addEvsInfo(request,"deleteEmptyParamInfo");
			}else{
				this.evsManageSer.addEvsInfo(request,"addParamInfo");
			}
			List paramList = this.evsManageSer.viewEvsInfoList(request,"viewParamList");
			modelMap.put("paramList", paramList);
			modelMap.put("paramListSize", paramList == null ? 0 : paramList.size());
			modelMap.put("currentIndex", currentIndex);
			
		}else if("2".equals(currentIndex)){ //考核对象 类型 定义
			if(request.getParameter("FLAG") == null){
				this.evsManageSer.addEvsInfo(request,"deleteEmptyParamObjectInfo");
			}else{
				this.evsManageSer.addEvsInfo(request,"addParamObjectInfo");
			}
			List paramList = this.evsManageSer.viewEvsInfoList(request,"viewParamObjectList");
			modelMap.put("paramList", paramList);
			modelMap.put("paramListSize", paramList == null ? 0 : paramList.size());
			modelMap.put("currentIndex", currentIndex);
			modelMap.put("evsTypeSelect" , JsonUtil.writeInternal(empInfoSer.getCodeList("14015137", request))); //评价分类
			modelMap.put("evsGradeSelect" , empInfoSer.getCodeList("14015161", request)); //评价等级
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			String FORMULA_SQL = "SELECT SEQ CODE_NO,CODE_NAME CODENAME FROM EVS_FORMULA WHERE CPNY_ID = '" + admin.getCpnyId() + "'  AND ACTIVITY = 1 ORDER BY TO_NUMBER(SEQ) ";
			modelMap.put("formulaSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(FORMULA_SQL))); //基准式
		}else if("3".equals(currentIndex)){ //考核表 类型 定义
			if(request.getParameter("FLAG") == null){
				this.evsManageSer.addEvsInfo(request,"deleteEmptyParamInfo");
			}else{
				this.evsManageSer.addEvsInfo(request,"addParamInfo");
			}
			List paramList = this.evsManageSer.viewEvsInfoList(request,"viewParamList");
			modelMap.put("paramList", paramList);
			modelMap.put("paramListSize", paramList == null ? 0 : paramList.size());
			modelMap.put("currentIndex", currentIndex);

			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			modelMap.put("evsStepSelect" , JsonUtil.writeInternal(empInfoSer.getCodeList("14015351", request))); //评价等级
			String FORMULA_SQL = "SELECT SEQ CODE_NO,CODE_NAME CODENAME FROM EVS_FORMULA WHERE CPNY_ID = '" + admin.getCpnyId() + "'  AND ACTIVITY = 1 ORDER BY TO_NUMBER(SEQ) ";
			modelMap.put("formulaSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(FORMULA_SQL))); //基准式
		}else if("4".equals(currentIndex)){ //考核群定义
			if(request.getParameter("FLAG") == null){
				this.evsManageSer.addEvsInfo(request,"deleteEmptyParamInfo");
			}else{
				this.evsManageSer.addEvsInfo(request,"addParamInfo");
			}
			List paramList = this.evsManageSer.viewEvsInfoList(request,"viewParamList");
			modelMap.put("paramList", paramList);
			modelMap.put("paramListSize", paramList == null ? 0 : paramList.size());
			modelMap.put("currentIndex", currentIndex);
			
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			String FORMULA_SQL = "SELECT SEQ CODE_NO,CODE_NAME CODENAME FROM EVS_FORMULA WHERE CPNY_ID = '" + admin.getCpnyId() + "'  AND ACTIVITY = 1 ORDER BY TO_NUMBER(SEQ) ";
			modelMap.put("formulaSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(FORMULA_SQL))); //基准式
		}else if("5".equals(currentIndex)){ //考核职业 群 定义
			if(request.getParameter("FLAG") == null){
				this.evsManageSer.addEvsInfo(request,"deleteEmptyParamInfo");
			}else{
				this.evsManageSer.addEvsInfo(request,"addParamInfo");
			}
			List paramList = this.evsManageSer.viewEvsInfoList(request,"viewParamList");
			modelMap.put("paramList", paramList);
			modelMap.put("paramListSize", paramList == null ? 0 : paramList.size());
			modelMap.put("currentIndex", currentIndex);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			String FORMULA_SQL = "SELECT SEQ CODE_NO,CODE_NAME CODENAME FROM EVS_FORMULA WHERE CPNY_ID = '" + admin.getCpnyId() + "'  AND ACTIVITY = 1 ORDER BY TO_NUMBER(SEQ) ";
			modelMap.put("formulaSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(FORMULA_SQL))); //基准式
		}else if("6".equals(currentIndex)){ //考核指标 群 定义
			if(request.getParameter("FLAG") == null){
				this.evsManageSer.addEvsInfo(request,"deleteEmptyParamInfo");
			}else{
				this.evsManageSer.addEvsInfo(request,"addParamInfo");
			}
			List paramList = this.evsManageSer.viewEvsInfoList(request,"viewParamList");
			modelMap.put("paramList", paramList);
			modelMap.put("paramListSize", paramList == null ? 0 : paramList.size());
			modelMap.put("currentIndex", currentIndex);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			String FORMULA_SQL = "SELECT SEQ CODE_NO,CODE_NAME CODENAME FROM EVS_FORMULA WHERE CPNY_ID = '" + admin.getCpnyId() + "'  AND ACTIVITY = 1 ORDER BY TO_NUMBER(SEQ) ";
			modelMap.put("formulaSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(FORMULA_SQL))); //基准式
		}else if("7".equals(currentIndex)){ //考核者 基准 定义
			if(request.getParameter("FLAG") == null){
				this.evsManageSer.addEvsInfo(request,"deleteEmptyAffirmRuleInfo");
			}else{
				this.evsManageSer.addEvsInfo(request,"addAffirmRuleInfo");
			}
			List affirmRuleList = this.evsManageSer.viewEvsInfoList(request,"viewAffirmRuleList");
			modelMap.put("affirmRuleList", affirmRuleList);
			modelMap.put("affirmRuleListSize", affirmRuleList == null ? 0 : affirmRuleList.size());
			modelMap.put("currentIndex", 2);
			modelMap.put("evsStepSelect" , JsonUtil.writeInternal(empInfoSer.getCodeList("14015060", request))); //评价等级
			modelMap.put("ruleIdSelect" , JsonUtil.writeInternal(empInfoSer.getCodeList("14015172", request))); //评价者规则
			String EVS_GROUP_SQL = "SELECT SEQ CODE_NO,CODE_NAME CODENAME FROM EVS_PARAM WHERE RESUME_SEQ = '" + paramMap.get("RESUME_SEQ") + "' AND ACTIVITY = 1 AND PARAM_TYPE = 'GROUP' ORDER BY TO_NUMBER(SEQ) ";
			modelMap.put("evsGroupSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(EVS_GROUP_SQL))); //评价群
		}else{ //考核等级 属性定义
			if(request.getParameter("FLAG") == null){
				this.evsManageSer.addEvsInfo(request,"deleteEmptyGradeInfo");
			}else{
				this.evsManageSer.addEvsInfo(request,"addGradeInfo");
			}
			List gradeList = this.evsManageSer.viewEvsInfoList(request,"viewGradeList");
			modelMap.put("gradeList", gradeList);
			modelMap.put("gradeListSize", gradeList == null ? 0 : gradeList.size());
			modelMap.put("currentIndex", 0);
			modelMap.put("evsTypeSelect" , JsonUtil.writeInternal(empInfoSer.getCodeList("14015137", request))); //评价分类
			modelMap.put("evsGradeSelect" , empInfoSer.getCodeList("14015161", request)); //评价等级
		}
		
		return new ModelAndView("/evs/manage/viewEvsParamInfoList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addParamInfo")
	@ResponseBody
	public Map addParamInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 1;
		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){ //Grade by item
			result = this.evsManageSer.addEvsInfoByJson(request,"updateParamInfo");
		}else if("2".equals(currentIndex)){ //考核对象 类型 定义
			result = this.evsManageSer.addEvsInfoByJson(request,"updateParamObjectInfo");
		}else if("3".equals(currentIndex)){ //考核表 类型 定义
			result = this.evsManageSer.addEvsInfoByJson(request,"updateParamInfo");
		}else if("4".equals(currentIndex)){ //考核群定义
			result = this.evsManageSer.addEvsInfoByJson(request,"updateParamInfo");
		}else if("5".equals(currentIndex)){ //考核职业 群 定义
			result = this.evsManageSer.addEvsInfoByJson(request,"updateParamInfo");
		}else if("6".equals(currentIndex)){ //考核指标 群 定义
			result = this.evsManageSer.addEvsInfoByJson(request,"updateParamInfo");
		}else if("7".equals(currentIndex)){ //考核者 基准 定义
			result = this.evsManageSer.addEvsInfoByJson(request,"updateAffirmRuleInfo");
		}else{ //考核等级 属性定义
			result = this.evsManageSer.addEvsInfoByJson(request,"updateGradeInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
			map.put("formId", "viewEvsParamPanelForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));// 保存失败！
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteParamInfo")
	@ResponseBody
	public Map deleteParamInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 1;
		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){ //Grade by item
		}else if("2".equals(currentIndex)){ //考核对象 类型 定义
			result = this.evsManageSer.deleteEvsInfo(request,"deleteParamInfo");
		}else if("3".equals(currentIndex)){ //考核表 类型 定义
			result = this.evsManageSer.deleteEvsInfo(request,"deleteParamInfo");
		}else if("4".equals(currentIndex)){ //考核群定义
			result = this.evsManageSer.deleteEvsInfo(request,"deleteParamInfo");
		}else if("5".equals(currentIndex)){ //考核职业 群 定义
			result = this.evsManageSer.deleteEvsInfo(request,"deleteParamInfo");
		}else if("6".equals(currentIndex)){ //考核指标 群 定义
			result = this.evsManageSer.deleteEvsInfo(request,"deleteParamInfo");
		}else if("7".equals(currentIndex)){ //考核者 基准 定义
			result = this.evsManageSer.deleteEvsInfo(request,"deleteAffirmRuleInfo");
		}else{ //考核等级 属性定义
			result = this.evsManageSer.deleteEvsInfo(request,"deleteGradeInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功！
			map.put("formId", "viewEvsParamPanelForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败！
		}
		return map;
	}


	/**
	 * 分配率 管理
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvsDistributionRatePanel")
	public ModelAndView viewEvsDistributionRatePanelList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");

		if("1".equals(currentIndex)){
			modelMap.put("currentIndex", 1);
		}else if("2".equals(currentIndex)){
			modelMap.put("currentIndex", 2);
		}else{
			modelMap.put("currentIndex", 0);
		}
		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		//第一次进入页面取RESUME_SEQ，以便其他信息查询
		this.getResumeSeq(request, resumeList, modelMap);
		modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
		
		return new ModelAndView("/evs/manage/viewEvsDistributionRatePanel", modelMap);
	}

	/**
	 * 分配率 管理
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsDistributionRateInfoList")
	public ModelAndView viewEvsDistributionRateInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){ //分配率评价群管理
			List scheduleInfoList = this.evsManageSer.viewEvsInfoList(request,"viewGradeList");
			modelMap.put("scheduleInfoList", scheduleInfoList);
			modelMap.put("scheduleInfoListSize", scheduleInfoList == null ? 0 : scheduleInfoList.size());
			modelMap.put("currentIndex", 1);
		}else if("2".equals(currentIndex)){ //分配率例外管理
			List scheduleInfoList = this.evsManageSer.viewEvsInfoList(request,"viewGradeList");
			modelMap.put("scheduleInfoList", scheduleInfoList);
			modelMap.put("scheduleInfoListSize", scheduleInfoList == null ? 0 : scheduleInfoList.size());
			modelMap.put("currentIndex", 2);
		}else{ //分配率 基准 管理
			List gradeList = this.evsManageSer.viewEvsInfoList(request,"viewGradeList");
			List scoreList = this.evsManageSer.viewEvsInfoList(request,"viewScoreList");
			modelMap.put("gradeList", gradeList);
			modelMap.put("scoreList", scoreList);
			modelMap.put("currentIndex", 0);
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String WORK_AREA_SQL = "SELECT CODE_NO || ' > ' || CODE_NAME DESCRIPTION, CODE_NO,CODE_NAME CODENAME FROM ORG_WORK_AREA WHERE ACTIVITY = '14013912' AND CPNY_ID = '" + admin.getCpnyId() + "' ";

		modelMap.put("evsStep" , JsonUtil.writeInternal(empInfoSer.getCodeList("14015065", request))); //评价进行区分
		modelMap.put("dept" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(WORK_AREA_SQL)));  //部门
		return new ModelAndView("/evs/manage/viewEvsDistributionRateInfoList", modelMap);
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvsDistributionRateInfo")
	@ResponseBody
	public Map addEvsDistributionRateInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 1;
		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){ //分配率评价群管理
		}else if("2".equals(currentIndex)){ //分配率例外管理
		}else{ //分配率 基准 管理
			result = this.evsManageSer.addEvsInfoByJson(request,"updateScoreInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
			map.put("formId", "viewEvsDistributionRateInfoPanelForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));// 保存失败！
		}
		return map;
	}
	
	/**
	 * 考核对象及考核者 设定
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsAffirmorSetup")
	public ModelAndView viewEvsAffirmorSetupList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String searchType = request.getParameter("searchType");
		List objectList = null;
		if(searchType != null && "0".equals(searchType)){
			objectList = this.evsManageSer.viewEvsInfoList(request,"viewObjectListNo");
		}else{
			objectList = this.evsManageSer.viewEvsInfoList(request,"viewObjectList");
		}
		modelMap.put("objectList", objectList);
		modelMap.put("objectListSize", objectList == null ? 0 : objectList.size());
		
		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		//第一次进入页面取RESUME_SEQ，以便其他信息查询
		this.getResumeSeq(request, resumeList, modelMap);

		String OCC_GROUP_SQL = "SELECT CODE_NO ,CODE_NAME CODENAME FROM EVS_PARAM EVS WHERE EVS.PARAM_TYPE = 'FAMILY' AND EVS.ACTIVITY = 1 AND EVS.RESUME_SEQ =  '" + request.getParameter("RESUME_SEQ") + "'";
		String GROUP_SQL = "SELECT CODE_NO ,CODE_NAME CODENAME FROM EVS_PARAM EVS WHERE EVS.PARAM_TYPE = 'GROUP' AND EVS.ACTIVITY = 1 AND EVS.RESUME_SEQ =  '" + request.getParameter("RESUME_SEQ") + "'";
		String LIST_TYPE_SQL = "SELECT CODE_NO ,CODE_NAME CODENAME FROM EVS_PARAM EVS WHERE EVS.PARAM_TYPE = 'LIST' AND EVS.ACTIVITY = 1 AND EVS.RESUME_SEQ =  '" + request.getParameter("RESUME_SEQ") + "'";
		
		modelMap.put("evsOccGroupSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(OCC_GROUP_SQL))); //考核职业群
		modelMap.put("evsGroupSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(GROUP_SQL))); //考核群
		modelMap.put("evsListTypeSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(LIST_TYPE_SQL))); //考核表类型
		
		return new ModelAndView("/evs/manage/viewEvsAffirmorSetup", modelMap);
	}
	
	/**
	 * 考核对象及考核者 设定
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsConfirmSetupList")
	public ModelAndView viewEvsConfirmSetupList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List objectList = objectList = this.evsManageSer.viewEvsInfoList(request,"viewObjectConfirmList");
		modelMap.put("objectList", objectList);
		modelMap.put("objectListSize", objectList == null ? 0 : objectList.size());
		
		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		//第一次进入页面取RESUME_SEQ，以便其他信息查询
		this.getResumeSeq(request, resumeList, modelMap);

		String OCC_GROUP_SQL = "SELECT CODE_NO ,CODE_NAME CODENAME FROM EVS_PARAM EVS WHERE EVS.PARAM_TYPE = 'FAMILY' AND EVS.ACTIVITY = 1 AND EVS.RESUME_SEQ =  '" + request.getParameter("RESUME_SEQ") + "'";
		String GROUP_SQL = "SELECT CODE_NO ,CODE_NAME CODENAME FROM EVS_PARAM EVS WHERE EVS.PARAM_TYPE = 'GROUP' AND EVS.ACTIVITY = 1 AND EVS.RESUME_SEQ =  '" + request.getParameter("RESUME_SEQ") + "'";
		String LIST_TYPE_SQL = "SELECT CODE_NO ,CODE_NAME CODENAME FROM EVS_PARAM EVS WHERE EVS.PARAM_TYPE = 'LIST' AND EVS.ACTIVITY = 1 AND EVS.RESUME_SEQ =  '" + request.getParameter("RESUME_SEQ") + "'";
		
		modelMap.put("evsOccGroupSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(OCC_GROUP_SQL))); //考核职业群
		modelMap.put("evsGroupSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(GROUP_SQL))); //考核群
		modelMap.put("evsListTypeSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(LIST_TYPE_SQL))); //考核表类型
		
		return new ModelAndView("/evs/manage/viewEvsConfirmSetupList", modelMap);
	}
	
	/**
	 * 考核对象及考核者 设定
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createEvsTarget")
	@ResponseBody
	public Map createEvsTarget(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.evsManageSer.addEnsInfoProcedure(request,"createEvsTarget");
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("evs.viewProbationResumeList.SHENGCHENGGONGGONG.a", request)); //生成成功
			map.put("formId", "viewEvsAffirmorSetupForm");
		} else {
			map.put("statusCode", "300");
			map.put("message",TipMessage.getTipMessage("evs.viewProbationResumeList.SHENGCHENGSHIBAI.a", request)); //生成失败
			map.put("message", result);
		}
		return map;
	}
	
	/**
	 * 业绩考核结果
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsResult")
	public ModelAndView viewEvsResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List objectList = this.evsManageSer.viewEvsInfoList(request,"viewEvsResultList");
		modelMap.put("objectList", objectList);
		modelMap.put("objectListSize", objectList == null ? 0 : objectList.size());
		
		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		//第一次进入页面取RESUME_SEQ，以便其他信息查询
		this.getResumeSeq(request, resumeList, modelMap);
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo",linkMap);

		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
			param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
		}
		List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
		modelMap.put("viewGradeList",viewGradeList);

		List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
		modelMap.put("scoreList", scoreList);

//		String evsGradeSelect = "SELECT GET_CODE_NAME(EVS_GRADE, 'zh') CODE_NO, GET_CODE_NAME(EVS_GRADE, 'zh') CODENAME FROM EVS_GRADE WHERE RESUME_SEQ = " + param.get("RESUME_SEQ") + " ORDER BY GET_CODE_NAME(EVS_GRADE, 'zh')";
		modelMap.put("evsGradeSelect" , empInfoSer.getCodeList("14015161", request));  //评价等级
		modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
		return new ModelAndView("/evs/manage/viewEvsResult", modelMap);
	}

	/**
	 * 考核对象状态变更
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/changeEvsActivity")
	@ResponseBody
	public Map changeEvsActivity(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsInfo(request,"changeEvsActivity");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.change_success", request));// 变更成功!
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.change_fail", request));// 变更失败!
		}
		return map;
	}
	
	/**
	 * 业绩考核目标登记状态
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsTargetInfoList")
	public ModelAndView viewEvsTargetInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {


		List objectList = this.evsManageSer.viewEvsInfoList(request,"viewObjectListForItem");
		modelMap.put("objectList", objectList);
		modelMap.put("objectListSize", objectList == null ? 0 : objectList.size());
		
		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		//第一次进入页面取RESUME_SEQ，以便其他信息查询
		this.getResumeSeq(request, resumeList, modelMap);
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo",linkMap);
		
		modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
		return new ModelAndView("/evs/manage/viewEvsTargetInfoList", modelMap);
	}
	
	/**
	 * 力量项目定义
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvsItemPanel")
	public ModelAndView viewEvsItemPanelList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");

		if("1".equals(currentIndex)){
			modelMap.put("currentIndex", 1);
		}else if("2".equals(currentIndex)){
			modelMap.put("currentIndex", 2);
		}else{
			modelMap.put("currentIndex", 0);
		}
		String evsType = request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType");
		List resumeList = null;
		if("probation".equals(evsType)){
			resumeList = this.evsManageSer.viewEvsInfoList(request,"viewProbationResumeList");
		}else{
			resumeList = this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		}
		modelMap.put("resumeList",resumeList);
		//第一次进入页面取RESUME_SEQ，以便其他信息查询
		this.getResumeSeq(request, resumeList, modelMap);
		modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
		
		return new ModelAndView("/evs/manage/viewEvsItemPanel", modelMap);
	}

	/**
	 * 力量项目定义
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsItemList")
	public ModelAndView viewEvsItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){ //力量项目定义
			if(request.getParameter("FLAG") == null){
				//this.evsManageSer.addEvsInfo(request,"deleteEmptyEvsItemParamInfo");
			}else{
				this.evsManageSer.addEvsInfo(request,"addEvsItemParamInfo");
			}
			List scheduleInfoList = this.evsManageSer.viewEvsInfoList(request,"viewEvsItemParamList");
			modelMap.put("scheduleInfoList", scheduleInfoList);
			modelMap.put("scheduleInfoListSize", scheduleInfoList == null ? 0 : scheduleInfoList.size());
			modelMap.put("currentIndex", 1);

			String OCC_GROUP_SQL = "SELECT CODE_NO ,CODE_NAME CODENAME FROM EVS_PARAM EVS WHERE EVS.PARAM_TYPE = 'FAMILY' AND EVS.ACTIVITY = 1 AND EVS.RESUME_SEQ =  '" + request.getParameter("RESUME_SEQ") + "'";
			String GROUP_SQL = "SELECT CODE_NO ,CODE_NAME CODENAME FROM EVS_PARAM EVS WHERE EVS.PARAM_TYPE = 'GROUP' AND EVS.ACTIVITY = 1 AND EVS.RESUME_SEQ =  '" + request.getParameter("RESUME_SEQ") + "'";
			String ITEM_SQL = "SELECT ITEM_CODE CODE_NO, REMARK CODENAME FROM EVS_ITEM WHERE ACTIVITY = 1 AND RESUME_SEQ = '" + request.getParameter("RESUME_SEQ") + "' ORDER BY TO_NUMBER(SEQ)";
			
			modelMap.put("evsOccGroupSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(OCC_GROUP_SQL))); //考核职业群
			modelMap.put("evsGroupSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(GROUP_SQL))); //考核群
			modelMap.put("itemSelect" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(ITEM_SQL))); //力量考核项目
		}else{ 
			if(request.getParameter("FLAG") == null){
				this.evsManageSer.addEvsInfo(request,"deleteEmptyEvsItemInfo");
			}else{
				this.evsManageSer.addEvsInfo(request,"addEvsItemInfo");
			}
			List viewEvsItemList = this.evsManageSer.viewEvsInfoList(request,"viewEvsItemList");
			modelMap.put("viewEvsItemList", viewEvsItemList);
			modelMap.put("viewEvsItemListSize", viewEvsItemList == null ? 0 : viewEvsItemList.size());
			modelMap.put("currentIndex", 0);

			modelMap.put("evsGroupNo" , JsonUtil.writeInternal(empInfoSer.getCodeList("14015376", request))); //力度群
		}
		
		return new ModelAndView("/evs/manage/viewEvsItemList", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvsItemInfo")
	@ResponseBody
	public Map addEvsItemInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 1;
		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){ //指定力量项目
			result = this.evsManageSer.addEvsInfoByJson(request,"updateEvsItemParamInfo");
		}else{ //力量项目定义
			result = this.evsManageSer.addEvsInfoByJson(request,"updateEvsItemInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
			map.put("formId", "viewEvsItemPanelForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));// 保存失败！
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteEvsItemInfo")
	@ResponseBody
	public Map deleteEvsItemInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 1;
		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){ //指定力量项目
			result = this.evsManageSer.deleteEvsInfo(request,"deleteEvsItemParamInfo");
		}else{ //力量项目定义
			result = this.evsManageSer.deleteEvsInfo(request,"deleteEvsItemInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功！
			map.put("formId", "viewEvsItemPanelForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败！
		}
		return map;
	}
	
	/**
	 * 评价基准式
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsFormulaList")
	public ModelAndView viewEvsFormulaInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List EvsFormulaList = this.evsManageSer.viewEvsInfoList(request,"viewEvsFormulaList");
		modelMap.put("EvsFormulaList", EvsFormulaList);

		LinkedHashMap param = null;
		
		if(EvsFormulaList!=null && EvsFormulaList.size() > 0){
			param = (LinkedHashMap)EvsFormulaList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("EvsFormulaSize", EvsFormulaList.size());
		}else{
			modelMap.put("EvsFormulaSize", 0);
		}
		return new ModelAndView("/evs/manage/viewEvsFormulaList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddEvsFormulaInfo")
	public ModelAndView viewAddEvsFormulaInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List EvsFormulaList = this.evsManageSer.viewEvsInfoList(request,"viewEvsFormulaList");
		
		LinkedHashMap param = new LinkedHashMap();
		
		if(EvsFormulaList!=null && EvsFormulaList.size() > 0){
			param = (LinkedHashMap)EvsFormulaList.get(0);
		}
		modelMap.put("EvsFormulaInfo", param);
		return new ModelAndView("/evs/manage/viewAddEvsFormulaInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvsFormulaInfo")
	@ResponseBody
	public Map addEvsFormulaInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//seq不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if(!"".equals(seq)){
			result = this.evsManageSer.addEvsInfo(request, "updateEvsFormulaInfo");
		}else{
			result = this.evsManageSer.addEvsInfo(request, "addEvsFormulaInfo");
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
			map.put("navTabId", "evs0301");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));// 保存失败！
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteEvsFormulaInfo")
	@ResponseBody
	public Map deleteEvsFormulaInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsInfo(request,"deleteEvsFormulaInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功！
			map.put("navTabId", "evs0301");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败！
		}
		return map;
	}

	/**
	 * 考核项目注册
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewRegPersonalTarget")
	public ModelAndView viewRegPersonalTargetList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String evsType = request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType");
		List resumeList = null;
		if("probation".equals(evsType)){
			resumeList = this.evsManageSer.viewEvsInfoList(request,"viewProbationResumeList");
		}else{
			resumeList = this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		}
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewEvsObjectInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsObjectInfo");
			if(viewEvsObjectInfo != null && viewEvsObjectInfo.size() > 0){
				Map tempMap = (Map)viewEvsObjectInfo.get(0);
				modelMap.put("viewEvsObjectInfo",tempMap);
				param.put("EVS_OBJECT_SEQ", tempMap.get("SEQ"));
			}
			List viewSSTEvsItem =  this.evsManageSer.viewEvsInfoList(param,"viewSSTEvsItem");
			modelMap.put("viewSSTEvsItem",viewSSTEvsItem);
			modelMap.put("objectTargetCnt",viewSSTEvsItem == null ? 0:viewSSTEvsItem.size());
		}
		
		return new ModelAndView("/evs/manage/viewRegPersonalTarget", modelMap);
	}

	/**
	 * 保存考核项目
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRegPersonalTarget")
	@ResponseBody
	public Map addRegPersonalTarget(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addRegPersonalTarget(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
			map.put("formId", "viewRegPersonalTargetForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}

	/**
	 * 目标确认
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyObjectActivity")
	@ResponseBody
	public Map modifyObjectActivity(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.evsManageSer.addEnsInfoProcedure(request, "modifyObjectActivity");
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
		} else {
			map.put("statusCode", "300");
			map.put("message", result);
		}
		return map;
	}

	/**
	 * 评价存储
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/excuteEvsPro")
	@ResponseBody
	public Map excuteEvsPro(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.evsManageSer.addEnsInfoProcedure(request, "excuteEvsPro");
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}

	/**
	 * 目标确认
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyObjectActivityForAffirm")
	@ResponseBody
	public Map modifyObjectActivityForAffirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.modifyObjectActivityForAffirm(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
		} else {
			map.put("statusCode", "300");
			map.put("message", result);
		}
		return map;
	}
	
	/**
	 * 目标一次确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewConfirmTarget1")
	public ModelAndView viewConfirmTarget1List(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "1" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewConfirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewConfirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
		}
		return new ModelAndView("/evs/manage/viewConfirmTarget1", modelMap);
	}

	/**
	 * 目标2次确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewConfirmTarget2")
	public ModelAndView viewConfirmTarget2List(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "2" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewConfirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewConfirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
		}
		return new ModelAndView("/evs/manage/viewConfirmTarget2", modelMap);
	}

	/**
	 * 考核项目注册
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewConfirmTargetInfo")
	public ModelAndView viewConfirmTargetInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewEvsObjectInfo =  this.evsManageSer.viewEvsInfoList(request,"viewEvsObjectInfo");
		List viewSSTEvsItem =  this.evsManageSer.viewEvsInfoList(request,"viewSSTEvsItem");
		if(viewEvsObjectInfo != null && viewEvsObjectInfo.size() > 0){
			Map tempMap = (Map)viewEvsObjectInfo.get(0);
			modelMap.put("viewEvsObjectInfo",tempMap);
		}
		modelMap.put("ACTIVITY", request.getParameter("ACTIVITY") == null ? "0" : request.getParameter("ACTIVITY"));
		modelMap.put("LEVEL", request.getParameter("LEVEL"));
		modelMap.put("viewSSTEvsItem",viewSSTEvsItem);
		modelMap.put("objectTargetCnt",viewSSTEvsItem == null ? 0:viewSSTEvsItem.size());
		
		return new ModelAndView("/evs/manage/viewConfirmTargetInfo", modelMap);
	}

	/**
	 * 考核项目注册
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewConfirmTargetInfoTSTO")
	public ModelAndView viewConfirmTargetInfoTSTO(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewEvsObjectInfo =  this.evsManageSer.viewEvsInfoList(request,"viewEvsObjectInfo");
		List viewSSTEvsItem =  this.evsManageSer.viewEvsInfoList(request,"viewSSTEvsItem");
		if(viewEvsObjectInfo != null && viewEvsObjectInfo.size() > 0){
			Map tempMap = (Map)viewEvsObjectInfo.get(0);
			modelMap.put("viewEvsObjectInfo",tempMap);
		}
		modelMap.put("ACTIVITY", request.getParameter("ACTIVITY") == null ? "0" : request.getParameter("ACTIVITY"));
		modelMap.put("viewSSTEvsItem",viewSSTEvsItem);
		
		List viewGradeList =  this.evsManageSer.viewEvsInfoList(request,"viewGradeList");
		modelMap.put("viewGradeList",viewGradeList);
		
		return new ModelAndView("/evs/manage/viewConfirmTargetInfoTSTO", modelMap);
	}

	/**
	 * 本人考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsBySelf")
	public ModelAndView viewEvsBySelf(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
	
			List viewEvsObjectInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsObjectInfo");
			if(viewEvsObjectInfo != null && viewEvsObjectInfo.size() > 0){
				Map tempMap = (Map)viewEvsObjectInfo.get(0);
				modelMap.put("viewEvsObjectInfo",tempMap);
				param.put("EVS_OBJECT_SEQ", tempMap.get("SEQ"));
			}
			//获取自我评价信息
			param.put("AFFIRM_LEVEL", "0");
			List viewEvsAffirmInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsAffirmInfo");
			if(viewEvsAffirmInfo != null && viewEvsAffirmInfo.size() > 0){
				Map tempMap = (Map)viewEvsAffirmInfo.get(0);
				modelMap.put("viewEvsAffirmInfo",tempMap);
			}
			
			List viewSSTEvsItem =  this.evsManageSer.viewEvsInfoList(param,"viewSSTEvsItem");
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewSSTEvsItem",viewSSTEvsItem);
			modelMap.put("viewGradeList",viewGradeList);
			modelMap.put("objectTargetCnt",viewSSTEvsItem == null ? 0:viewSSTEvsItem.size());
		}
		return new ModelAndView("/evs/manage/viewEvsBySelf", modelMap);
	}

	/**
	 * 保存考核项目
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvsBySelf")
	@ResponseBody
	public Map addEvsBySelf(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsBySelf(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
			map.put("formId", "viewEvsBySelfForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}
	
	/**
	 * 一次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget1")
	public ModelAndView viewAffirmTarget1List(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "1" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
			modelMap.put("currentEvsCnt", countCurrentEvsCnt(viewConfirmTarget,"14015357"));
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget1", modelMap);
	}

	/**
	 * 2次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget2")
	public ModelAndView viewAffirmTarget2List(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "2" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
			modelMap.put("currentEvsCnt", countCurrentEvsCnt(viewConfirmTarget,"14015358"));
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget2", modelMap);
	}
	
	/**
	 *  考核开始
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/evsStart")
	@ResponseBody
	public Map evsStart(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.evsManageSer.addEnsInfoProcedure(request,"PR_EVS_START");
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
			map.put("formId", "viewEvsAffirmorSetupForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", result);// 操作失败!
		}
		return map;
	}
	
	/**
	 *  考核开始
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/evsCreateArDetail")
	@ResponseBody
	public Map evsCreateArDetail(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.evsManageSer.addEnsInfoProcedure(request,"PR_CREATE_AR_DETAIL");
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("evs.viewProbationResumeList.SHENGCHENGGONGGONG.a", request)); //生成成功
		} else {
			map.put("statusCode", "300");
			map.put("message",TipMessage.getTipMessage("evs.viewProbationResumeList.SHENGCHENGSHIBAI.a", request)); //生成失败!
		}
		return map;
	}
	
	/**
	 *  考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/affirmTarget")
	@ResponseBody
	public Map affirmTarget(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsInfoByJson(request,"modifyEvsAffirmBySelf");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));// 保存失败！
		}
		return map;
	}

	/**
	 * 2次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsComment")
	public ModelAndView viewEvsCommentList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("editFlag", request.getParameter("editFlag"));
		modelMap.put("index", request.getParameter("index"));
		return new ModelAndView("/evs/manage/viewEvsComment", modelMap);
	}

	/**
	 * 本人考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsBySelfHTSV")
	public ModelAndView viewEvsBySelfHTSVList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
	
			List viewEvsObjectInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsObjectInfo");
			if(viewEvsObjectInfo != null && viewEvsObjectInfo.size() > 0){
				Map tempMap = (Map)viewEvsObjectInfo.get(0);
				modelMap.put("viewEvsObjectInfo",tempMap);
				param.put("EVS_OBJECT_SEQ", tempMap.get("SEQ"));
			}
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			//获取自我评价信息
			param.put("AFFIRM_LEVEL", "0");
			List viewEvsAffirmInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsAffirmInfo");
			if(viewEvsAffirmInfo != null && viewEvsAffirmInfo.size() > 0){
				Map tempMap = (Map)viewEvsAffirmInfo.get(0);
				modelMap.put("viewEvsAffirmInfo",tempMap);
			}
			
			List viewSSTEvsItem =  this.evsManageSer.viewEvsInfoList(param,"viewSSTEvsItem");
			modelMap.put("viewSSTEvsItem",viewSSTEvsItem);
			modelMap.put("objectTargetCnt",viewSSTEvsItem == null ? 0:viewSSTEvsItem.size());
		}
		return new ModelAndView("/evs/manage/viewEvsBySelfHTSV", modelMap);
	}

	/**
	 * 本人考核(力量)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsBySelfHTSVAbility")
	public ModelAndView viewEvsBySelfHTSVAbility(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
	
			List viewEvsObjectInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsObjectInfo");
			if(viewEvsObjectInfo != null && viewEvsObjectInfo.size() > 0){
				Map tempMap = (Map)viewEvsObjectInfo.get(0);
				modelMap.put("viewEvsObjectInfo",tempMap);
				param.put("EVS_OBJECT_SEQ", tempMap.get("SEQ"));
			}
			//获取自我评价信息
			param.put("AFFIRM_LEVEL", "0");
			List viewEvsAffirmInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsAffirmInfo");
			if(viewEvsAffirmInfo != null && viewEvsAffirmInfo.size() > 0){
				Map tempMap = (Map)viewEvsAffirmInfo.get(0);
				modelMap.put("viewEvsAffirmInfo",tempMap);
			}

			param.put("APPLY_PERSON_ID", param.get("adminID"));
			List viewEvsItem =  this.evsManageSer.viewEvsInfoList(param,"viewEvsItemAbility");
			modelMap.put("viewEvsItem",viewEvsItem);
		}
		return new ModelAndView("/evs/manage/viewEvsBySelfHTSVAbility", modelMap);
	}
	
	/**
	 * 保存力量自我评价信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvsBySelfTSTOAbility")
	@ResponseBody
	public Map addEvsBySelfTSTOAbility(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsBySelfTSTOAbility(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
			map.put("formId", "viewEvsBySelfHTSVAbilityForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}
	
	/**
	 * 保存力量自我评价
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvsBySelfHTSV")
	@ResponseBody
	public Map addEvsBySelfHTSV(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsBySelfHTSV(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
			map.put("formId", "viewEvsBySelfHTSVForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}
	
	
	/**
	 * 一次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget1TSTO")
	public ModelAndView viewAffirmTarget1TSTOList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "1" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget1TSTO", modelMap);
	}

	/**
	 * 2次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget2TSTO")
	public ModelAndView viewAffirmTarget2TSTOList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "2" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget2TSTO", modelMap);
	}

	/**
	 * 一次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget11TSTO")
	public ModelAndView viewAffirmTarget11TSTOList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "1" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
			
			modelMap.put("currentEvsCnt", countCurrentEvsCnt(viewConfirmTarget,"14015357"));
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget11TSTO", modelMap);
	}

	/**
	 * 2次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget22TSTO")
	public ModelAndView viewAffirmTarget22TSTOList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "2" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
			
			modelMap.put("currentEvsCnt", countCurrentEvsCnt(viewConfirmTarget,"14015358"));
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget22TSTO", modelMap);
	}

	/**
	 * 3次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget33TSTO")
	public ModelAndView viewAffirmTarget33TSTOList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "3" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
			
			modelMap.put("currentEvsCnt", countCurrentEvsCnt(viewConfirmTarget,"14015359"));
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget33TSTO", modelMap);
	}


	/**
	 * 3次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget3TSTO")
	public ModelAndView viewAffirmTarget3TSTOList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "3" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget3TSTO", modelMap);
	}
	
	/**
	 * 保存考核项目
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvsDetailInfoTSTO")
	@ResponseBody
	public Map addEvsDetailInfoTSTO(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsDetailInfoTSTO(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}

	/**
	 *  保存人事等级
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveConfirmFinalGrade")
	@ResponseBody
	public Map saveConfirmFinalGrade(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsInfoByJson(request,"saveConfirmFinalGrade");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));// 保存失败！
		}
		return map;
	}
	
	/**
	 * 保存评价人
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveEvsObjectInfo")
	@ResponseBody
	public Map saveEvsObjectInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.saveEvsObjectInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
		} else {
			map.put("statusCode", "300");
			map.put("message", result);
		}
		return map;
	}

	/**
	 * 删除考核者
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteEvsObjectInfo")
	@ResponseBody
	public Map deleteEvsObjectInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.deleteEvsInfoByJson(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功！
		} else {
			map.put("statusCode", "300");
			map.put("message", result);
		}
		return map;
	}
	
	/**
	 * 力量一次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget1HTSVAbility")
	public ModelAndView viewAffirmTarget1HTSVAbilityList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "1" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget1HTSVAbility", modelMap);
	}

	/**
	 * 力量2次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget2HTSVAbility")
	public ModelAndView viewAffirmTarget2HTSVAbilityList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "2" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget2HTSVAbility", modelMap);
	}


	/**
	 * 力量3次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget3TSTOAbility")
	public ModelAndView viewAffirmTarget3TSTOAbilityList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "3" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget3TSTOAbility", modelMap);
	}

	/**
	 * 复制考核等级
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/copyEvsGrade")
	@ResponseBody
	public Map copyEvsGrade(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.evsManageSer.addEnsInfoProcedure(request,"copyEvsGrade");
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}
	
	/**
	 * 本人考核(力量)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewConfirmTargetInfoHTSVAbility")
	public ModelAndView viewConfirmTargetInfoHTSVAbility(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewEvsObjectInfo =  this.evsManageSer.viewEvsInfoList(request,"viewEvsObjectInfo");
		if(viewEvsObjectInfo != null && viewEvsObjectInfo.size() > 0){
			Map tempMap = (Map)viewEvsObjectInfo.get(0);
			modelMap.put("viewEvsObjectInfo",tempMap);
		}

		List viewEvsItem =  this.evsManageSer.viewEvsInfoList(request,"viewEvsItemAbility");
		modelMap.put("viewEvsItem",viewEvsItem);
		
		modelMap.put("ACTIVITY", request.getParameter("ACTIVITY") == null ? "0" : request.getParameter("ACTIVITY"));
		
		List viewGradeList =  this.evsManageSer.viewEvsInfoList(request,"viewGradeList");
		modelMap.put("viewGradeList",viewGradeList);
		
		return new ModelAndView("/evs/manage/viewConfirmTargetInfoHTSVAbility", modelMap);
	}

	/**
	 * 力量1次考核
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvsDetailInfoHTSVAbility")
	@ResponseBody
	public Map addEvsDetailInfoHTSVAbility(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsDetailInfoHTSVAbility(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}

	/**
	 * 本人考核(力量)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsBySelfSSTAbility")
	public ModelAndView viewEvsBySelfSSTAbility(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
	
			List viewEvsObjectInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsObjectInfo");
			if(viewEvsObjectInfo != null && viewEvsObjectInfo.size() > 0){
				Map tempMap = (Map)viewEvsObjectInfo.get(0);
				modelMap.put("viewEvsObjectInfo",tempMap);
				param.put("EVS_OBJECT_SEQ", tempMap.get("SEQ"));
			}
			//获取自我评价信息
			param.put("AFFIRM_LEVEL", "0");
			List viewEvsAffirmInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsAffirmInfo");
			if(viewEvsAffirmInfo != null && viewEvsAffirmInfo.size() > 0){
				Map tempMap = (Map)viewEvsAffirmInfo.get(0);
				modelMap.put("viewEvsAffirmInfo",tempMap);
			}
			//获取评价等级item
			param.put("PARAM_TYPE", "ITEM");
			List viewEvsGradeItem =  this.evsManageSer.viewEvsInfoList(param,"viewParamList");
			modelMap.put("viewEvsGradeItem",viewEvsGradeItem);

			param.put("APPLY_PERSON_ID", param.get("adminID"));
			List viewEvsItem =  this.evsManageSer.viewEvsInfoList(param,"viewEvsItemAbility");
			modelMap.put("viewEvsItem",viewEvsItem);
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
		}
		return new ModelAndView("/evs/manage/viewEvsBySelfSSTAbility", modelMap);
	}
	
	/**
	 * 保存力量自我评价信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvsBySelfSSTAbility")
	@ResponseBody
	public Map addEvsBySelfSSTAbility(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsBySelfSSTAbility(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
			map.put("formId", "viewEvsBySelfSSTAbilityForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}

	/**
	 * 力量一次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget1Ability")
	public ModelAndView viewAffirmTarget1Ability(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "1" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget1Ability", modelMap);
	}

	/**
	 * 力量2次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmTarget2Ability")
	public ModelAndView viewAffirmTarget2AbilityList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "2" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewAffirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewAffirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
			
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
			
			List scoreList = this.evsManageSer.viewEvsInfoList(param,"viewScoreList");
			modelMap.put("scoreList", scoreList);
		}
		
		return new ModelAndView("/evs/manage/viewAffirmTarget2Ability", modelMap);
	}
	
	/**
	 * 1次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewConfirmTargetInfoAbility")
	public ModelAndView viewConfirmTargetInfoAbility(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewEvsObjectInfo =  this.evsManageSer.viewEvsInfoList(request,"viewEvsObjectInfo");
		if(viewEvsObjectInfo != null && viewEvsObjectInfo.size() > 0){
			Map tempMap = (Map)viewEvsObjectInfo.get(0);
			modelMap.put("viewEvsObjectInfo",tempMap);
		}

		List viewEvsItem =  this.evsManageSer.viewEvsInfoList(request,"viewEvsItemAbility");
		modelMap.put("viewEvsItem",viewEvsItem);
		
		modelMap.put("ACTIVITY", request.getParameter("ACTIVITY") == null ? "0" : request.getParameter("ACTIVITY"));
		
		List viewGradeList =  this.evsManageSer.viewEvsInfoList(request,"viewGradeList");
		modelMap.put("viewGradeList",viewGradeList);

		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		//获取评价等级item
		param.put("PARAM_TYPE", "ITEM");
		List viewEvsGradeItem =  this.evsManageSer.viewEvsInfoList(param,"viewParamList");
		modelMap.put("viewEvsGradeItem",viewEvsGradeItem);
		
		return new ModelAndView("/evs/manage/viewConfirmTargetInfoAbility", modelMap);
	}
	
	/**
	 * 统计当前可操作人数
	 * @param objectList
	 * @param activity
	 * @return
	 */
	private int countCurrentEvsCnt(List objectList,String activity){
		int cnt = 0;
		if(objectList != null && objectList.size() > 0){
			for(int i=0;i<objectList.size();i++){
				Map map = (Map)objectList.get(i);
				if(activity.equals(StringUtil.checkNull(map.get("ACTIVITY")))){
					cnt = cnt + 1;
				}
			}
		}
		return cnt;
	}

	
	/**
	 * 1次考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewConfirmTargetInfoSST")
	public ModelAndView viewConfirmTargetInfoSST(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewEvsObjectInfo =  this.evsManageSer.viewEvsInfoList(request,"viewEvsObjectInfo");
		if(viewEvsObjectInfo != null && viewEvsObjectInfo.size() > 0){
			Map tempMap = (Map)viewEvsObjectInfo.get(0);
			modelMap.put("viewEvsObjectInfo",tempMap);
		}
		
		List viewGradeList =  this.evsManageSer.viewEvsInfoList(request,"viewGradeList");
		modelMap.put("viewGradeList",viewGradeList);

		List viewSSTEvsItem =  this.evsManageSer.viewEvsInfoList(request,"viewSSTEvsItem");
		modelMap.put("viewSSTEvsItem",viewSSTEvsItem);
		modelMap.put("objectTargetCnt",viewSSTEvsItem == null ? 0:viewSSTEvsItem.size());
		
		modelMap.put("ACTIVITY", request.getParameter("ACTIVITY") == null ? "0" : request.getParameter("ACTIVITY"));
		
		return new ModelAndView("/evs/manage/viewConfirmTargetInfoSST", modelMap);
	}

	
	/**
	 * 考核对象及考核者 设定
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddEvsObject")
	public ModelAndView viewAddEvsObjectList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/evs/manage/viewAddEvsObject", modelMap);
	}
	
	/**
	 * 考核对象及考核者 设定
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvsObject")
	@ResponseBody
	public Map addEvsObject(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.evsManageSer.addEvsObject(request);
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.org.add_success", request));// 添加成功!
			map.put("formId", "viewEvsAffirmorSetupForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", result);// 添加失败!
		}
		return map;
	}
	
	/**
	 * Partner考核结果
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsResultEmp")
	public ModelAndView viewEvsResultEmp(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List objectList = this.evsManageSer.viewEvsInfoList(request,"viewEvsResultEmp");
		modelMap.put("objectList", objectList);
		
		return new ModelAndView("/evs/manage/viewEvsResultEmp", modelMap);
	}
	
	/**
	 * Hub考核结果
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsResultPer")
	public ModelAndView viewEvsResultPer(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("PERSON_ID")!=null){
			paramMap.put("PERSON_ID",request.getParameter("PERSON_ID"));
		}else{
			paramMap.put("PERSON_ID",admin.getAdminID());
		}
		List objectList = this.evsManageSer.viewEvsInfoList(paramMap,"viewEvsResultPerson");
		modelMap.put("objectList", objectList);
		
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo",linkMap);
		
		return new ModelAndView("/evs/manage/viewEvsResultPer", modelMap);
	}
	
	/**
	 * 考核项目注册
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewConfirmTargetInfoPrint")
	public ModelAndView viewConfirmTargetInfoPrint(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewEvsObjectInfo =  this.evsManageSer.viewEvsInfoList(request,"viewEvsObjectInfo");
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		for(int i=0;i<viewEvsObjectInfo.size();i++){
			Map tempMap = (Map)viewEvsObjectInfo.get(i);
			paramMap.put("EVS_OBJECT_SEQ", tempMap.get("SEQ"));
			List viewSSTEvsItem =  this.evsManageSer.viewEvsInfoList(paramMap,"viewSSTEvsItem");
			if (viewSSTEvsItem.size() == 0) {
				List viewEvsItem =  this.evsManageSer.viewEvsInfoList(request,"viewEvsItemAbility");
				tempMap.put("viewSSTEvsItem",viewEvsItem);
			} else {
				tempMap.put("viewSSTEvsItem", viewSSTEvsItem);
			}
		}
		modelMap.put("ACTIVITY", request.getParameter("ACTIVITY") == null ? "0" : request.getParameter("ACTIVITY"));
		modelMap.put("viewEvsObjectInfo", viewEvsObjectInfo);
		
		return new ModelAndView("/evs/manage/viewConfirmTargetInfoPrint", modelMap);
	}
	
	/**
	 * 保存确认人
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveEvsObjectConfirmInfo")
	@ResponseBody
	public Map saveEvsObjectConfirmInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.saveEvsObjectConfirmInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
		} else {
			map.put("statusCode", "300");
			map.put("message", result);
		}
		return map;
	}
	


	/**
	 * 考核项目注册
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewRegPersonalTargetTSTO")
	public ModelAndView viewRegPersonalTargetTSTOList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewEvsObjectInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsObjectInfo");
			if(viewEvsObjectInfo != null && viewEvsObjectInfo.size() > 0){
				Map tempMap = null;
				for(int i=0;i<resumeList.size();i++){
					tempMap = (Map)resumeList.get(i);
					if(StringUtil.checkNull(tempMap.get("SEQ")).equals(StringUtil.checkNull(param.get("RESUME_SEQ")))){
						break;
					}
				}
				modelMap.put("viewEvsObjectInfo",tempMap);
				param.put("EVS_OBJECT_SEQ", tempMap.get("SEQ"));
			}
			List viewSSTEvsItem =  this.evsManageSer.viewEvsInfoList(param,"viewSSTEvsItem");
			modelMap.put("viewSSTEvsItem",viewSSTEvsItem);
			modelMap.put("objectTargetCnt",viewSSTEvsItem == null ? 0:viewSSTEvsItem.size());
		}
		
		return new ModelAndView("/evs/manage/viewRegPersonalTargetTSTO", modelMap);
	}

	
	/**
	 * 目标一次确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewConfirmTarget1TSTO")
	public ModelAndView viewConfirmTarget1TSTOList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "1" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewConfirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewConfirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
		}
		return new ModelAndView("/evs/manage/viewConfirmTarget1TSTO", modelMap);
	}

	/**
	 * 目标2次确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewConfirmTarget2TSTO")
	public ModelAndView viewConfirmTarget2TSTOList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "2" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewConfirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewConfirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
		}
		return new ModelAndView("/evs/manage/viewConfirmTarget2TSTO", modelMap);
	}

	/**
	 * 目标3次确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewConfirmTarget3TSTO")
	public ModelAndView viewConfirmTarget3TSTOList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList =  this.evsManageSer.viewEvsInfoList(request,"viewResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
			modelMap.put("AFFIRM_LEVEL", request.getParameter("AFFIRM_LEVEL") == null ? "3" : request.getParameter("AFFIRM_LEVEL"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			List viewConfirmTarget =  this.evsManageSer.viewEvsInfoList(param,"viewConfirmTarget");
			modelMap.put("viewConfirmTargetCnt",this.evsManageSer.viewEvsInfoCnt(param,"viewConfirmTargetCnt"));
			modelMap.put("viewConfirmTarget",viewConfirmTarget);
			modelMap.put("viewConfirmTargetSize",viewConfirmTarget == null ? 0 : viewConfirmTarget.size());
		}
		return new ModelAndView("/evs/manage/viewConfirmTarget3TSTO", modelMap);
	}
	
	/**
	 * 试用期概要管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewProbationResumeList")
	public ModelAndView viewProbationResumeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.evsManageSer.viewEvsInfoList(request,"viewProbationResumeList");
		modelMap.put("resumeList", resumeList);
		modelMap.put("resumeSize", resumeList == null ? 0 : resumeList.size());

		modelMap.put("evsType", request.getParameter("evsType") == null ? "probation" : request.getParameter("evsType"));
		return new ModelAndView("/evs/manage/viewProbationResumeList", modelMap);
	}
	
	/**
	 * 力量项目定义
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewProbationEvsItemPanel")
	public ModelAndView viewProbationEvsItemPanelList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

			modelMap.put("currentIndex", 0);
		String evsType = request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType");
		List resumeList = this.evsManageSer.viewEvsInfoList(request,"viewProbationResumeList");
		modelMap.put("resumeList",resumeList);
		//第一次进入页面取RESUME_SEQ，以便其他信息查询
		this.getResumeSeq(request, resumeList, modelMap);
		modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
		
		return new ModelAndView("/evs/manage/viewProbationEvsItemPanel", modelMap);
	}

	/**
	 * 力量项目定义
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewProbationEvsItemList")
	public ModelAndView viewProbationEvsItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		if(request.getParameter("FLAG") == null){
			this.evsManageSer.addEvsInfo(request,"deleteEmptyProbationEvsItemInfo");
		}else{
			this.evsManageSer.addEvsInfo(request,"addProbationEvsItemInfo");
		}
		List viewEvsItemList = this.evsManageSer.viewEvsInfoList(request,"viewProbationEvsItemList");
		modelMap.put("viewEvsItemList", viewEvsItemList);
		modelMap.put("viewEvsItemListSize", viewEvsItemList == null ? 0 : viewEvsItemList.size());

		return new ModelAndView("/evs/manage/viewProbationEvsItemList", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addProbationEvsItemInfo")
	@ResponseBody
	public Map addProbationEvsItemInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 1;
			result = this.evsManageSer.addEvsInfoByJson(request,"updateProbationEvsItemInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
			map.put("formId", "viewProbationEvsItemPanelForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));// 保存失败！
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteProbationEvsItemInfo")
	@ResponseBody
	public Map deleteProbationEvsItemInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 1;
		result = this.evsManageSer.addEvsInfo(request,"deleteProbationEvsItemInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功！
			map.put("formId", "viewProbationEvsItemPanelForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败！
		}
		return map;
	}

	/**
	 * 考核项目注册
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewRegPersonalProbation")
	public ModelAndView viewRegPersonalProbationList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.evsManageSer.viewEvsInfoList(request,"viewProbationResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			Map tempMap = null;
			for(int i=0;i<resumeList.size();i++){
				tempMap = (Map)resumeList.get(i);
				if(StringUtil.checkNull(tempMap.get("SEQ")).equals(StringUtil.checkNull(param.get("RESUME_SEQ")))){
					break;
				}
			}
			modelMap.put("viewEvsObjectInfo",tempMap);
			param.put("EVS_OBJECT_SEQ", tempMap.get("EVS_OBJECT_SEQ"));

			//获取自我评价信息
			param.put("AFFIRM_LEVEL", "0");
			List viewEvsAffirmInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsAffirmInfo");
			if(viewEvsAffirmInfo != null && viewEvsAffirmInfo.size() > 0){
				Map tempAffirmMap = (Map)viewEvsAffirmInfo.get(0);
				modelMap.put("viewEvsAffirmInfo",tempAffirmMap);
			}
			//考核者
			List viewEvsAffirmList =  this.evsManageSer.viewEvsInfoList(param,"viewProbationEvsAffirmList");
			modelMap.put("viewEvsAffirmList",viewEvsAffirmList);
			modelMap.put("viewEvsAffirmListSize",viewEvsAffirmList == null ? 0 : viewEvsAffirmList.size());
			
			
			List viewSSTEvsItem =  this.evsManageSer.viewEvsInfoList(param,"viewSSTEvsItem");
			modelMap.put("viewSSTEvsItem",viewSSTEvsItem);
			modelMap.put("objectTargetCnt",viewSSTEvsItem == null ? 0:viewSSTEvsItem.size());
			
		}
		
		return new ModelAndView("/evs/manage/viewRegPersonalProbation", modelMap);
	}

	/**
	 * 保存考核项目
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRegPersonalTargetProbation")
	@ResponseBody
	public Map addRegPersonalTargetProbation(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addRegPersonalTargetProbation(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
			map.put("formId", "viewRegPersonalTargetProbationForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}

	/**
	 * 试用期考核
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewProbationEvsAffirm")
	public ModelAndView viewProbationEvsAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.evsManageSer.viewEvsInfoList(request,"viewProbationResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			Map tempMap = null;
			for(int i=0;i<resumeList.size();i++){
				tempMap = (Map)resumeList.get(i);
				if(StringUtil.checkNull(tempMap.get("SEQ")).equals(StringUtil.checkNull(param.get("RESUME_SEQ")))){
					break;
				}
			}
			modelMap.put("viewEvsObjectInfo",tempMap);
			param.put("EVS_OBJECT_SEQ", tempMap.get("EVS_OBJECT_SEQ"));
			//奖励、惩戒信息
			modelMap.put("getEvsRewardInfo",this.evsManageSer.viewEvsInfoList(tempMap,"getEvsRewardInfo"));
			modelMap.put("getEvsPunishmentInfo",this.evsManageSer.viewEvsInfoList(tempMap,"getEvsPunishmentInfo"));

			//获取自我评价信息
			param.put("AFFIRM_LEVEL", "0");
			List viewEvsAffirmInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsAffirmInfo");
			if(viewEvsAffirmInfo != null && viewEvsAffirmInfo.size() > 0){
				Map tempAffirmMap = (Map)viewEvsAffirmInfo.get(0);
				modelMap.put("viewEvsAffirmInfo",tempAffirmMap);
			}
			//考核者
			List viewEvsAffirmList =  this.evsManageSer.viewEvsInfoList(param,"viewProbationEvsAffirmList");
			modelMap.put("viewEvsAffirmList",viewEvsAffirmList);
			modelMap.put("viewEvsAffirmListSize",viewEvsAffirmList == null ? 0 : viewEvsAffirmList.size());
			
			List viewSSTEvsItem =  this.evsManageSer.viewEvsInfoList(param,"viewSSTEvsItem");
			modelMap.put("viewSSTEvsItem",viewSSTEvsItem);
			modelMap.put("objectTargetCnt",viewSSTEvsItem == null ? 0:viewSSTEvsItem.size());
			
			List viewSSTProbationEvsItem =  this.evsManageSer.viewEvsInfoList(param,"viewProbationEvsItemList");
			modelMap.put("viewSSTProbationEvsItem",viewSSTProbationEvsItem);
			
			List viewProAffScore =  this.evsManageSer.viewEvsInfoList(param,"viewProbationEvsAffirmScore");
			modelMap.put("viewProAffScore",viewProAffScore);
		}
		
		return new ModelAndView("/evs/manage/viewProbationEvsAffirm", modelMap);
	}

	/**
	 * 保存试用期考核信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addProbationEvsAffirmInfo")
	@ResponseBody
	public Map addProbationEvsAffirmInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addProbationEvsAffirmInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
			map.put("formId", "viewAffirmTargetProbationForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}
	
	/**
	 * 试用期考核结果
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewProbationEvsResult")
	public ModelAndView viewProbationEvsResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.evsManageSer.viewEvsInfoList(request,"viewProbationResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeq(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			Map tempMap = null;
			for(int i=0;i<resumeList.size();i++){
				tempMap = (Map)resumeList.get(i);
				if(StringUtil.checkNull(tempMap.get("SEQ")).equals(StringUtil.checkNull(param.get("RESUME_SEQ")))){
					break;
				}
			}
			modelMap.put("viewEvsObjectInfo",tempMap);
			param.put("EVS_OBJECT_SEQ", tempMap.get("EVS_OBJECT_SEQ"));
			//奖励、惩戒信息
			modelMap.put("getEvsRewardInfo",this.evsManageSer.viewEvsInfoList(tempMap,"getEvsRewardInfo"));
			modelMap.put("getEvsPunishmentInfo",this.evsManageSer.viewEvsInfoList(tempMap,"getEvsPunishmentInfo"));

			//获取自我评价信息
			param.put("AFFIRM_LEVEL", "0");
			List viewEvsAffirmInfo =  this.evsManageSer.viewEvsInfoList(param,"viewEvsAffirmInfo");
			if(viewEvsAffirmInfo != null && viewEvsAffirmInfo.size() > 0){
				Map tempAffirmMap = (Map)viewEvsAffirmInfo.get(0);
				modelMap.put("viewEvsAffirmInfo",tempAffirmMap);
			}
			//考核者
			List viewEvsAffirmList =  this.evsManageSer.viewEvsInfoList(param,"viewProbationEvsAffirmList");
			modelMap.put("viewEvsAffirmList",viewEvsAffirmList);
			modelMap.put("viewEvsAffirmListSize",viewEvsAffirmList == null ? 0 : viewEvsAffirmList.size());

			int viewEvsAffirmSize = 0;
			if(viewEvsAffirmList != null && viewEvsAffirmList.size() > 0){
				for(int i=0;i<viewEvsAffirmList.size();i++){
					Map affirmMap = (Map)viewEvsAffirmList.get(i);
					if("1".equals(StringUtil.checkNull(affirmMap.get("AFFIRM_TYPE")))){
						viewEvsAffirmSize++;
					}
				}
			}
			modelMap.put("viewEvsAffirmSize",viewEvsAffirmSize);
			List viewSSTEvsItem =  this.evsManageSer.viewEvsInfoList(param,"viewSSTEvsItem");
			modelMap.put("viewSSTEvsItem",viewSSTEvsItem);
			modelMap.put("objectTargetCnt",viewSSTEvsItem == null ? 0:viewSSTEvsItem.size());
			
			List viewSSTProbationEvsItem =  this.evsManageSer.viewEvsInfoList(param,"viewProbationEvsItemList");
			modelMap.put("viewSSTProbationEvsItem",viewSSTProbationEvsItem);
			
			List viewProAffScore =  this.evsManageSer.viewEvsInfoList(param,"viewProbationEvsAffirmScore");
			modelMap.put("viewProAffScore",viewProAffScore);
			//获取评价等级item
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
		}
		
		return new ModelAndView("/evs/manage/viewProbationEvsResult", modelMap);
	}

	
	/**
	 * 考核对象及考核者 设定
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createEvsTargetPro")
	@ResponseBody
	public Map createEvsTargetPro(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.evsManageSer.addEnsInfoProcedure(request,"createEvsTargetPro");
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("evs.viewProbationResumeList.SHENGCHENGGONGGONG.a", request)); //生成成功
			map.put("formId", "viewEvsAffirmorSetupForm");
		} else {
			map.put("statusCode", "300");
			map.put("message",TipMessage.getTipMessage("evs.viewProbationResumeList.SHENGCHENGSHIBAI.a", request)); //生成失败
		}
		return map;
	}

	/**
	 * 试用期考核结果保存
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveProbationResult")
	@ResponseBody
	public Map saveProbationResult(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.saveProbationResult(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}

	/**
	 * 试用期考核结束
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/finishProbationResult")
	@ResponseBody
	public Map finishProbationResult(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsInfo(request, "finishProbationResult");
		result = this.evsManageSer.addEvsInfo(request, "insertEvsMail");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));// 操作成功!
			map.put("formId", "viewProbationEvsResultForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));// 操作失败!
		}
		return map;
	}
	
	/**
	 * 试用期考核结果
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewProbationResultEmp")
	public ModelAndView viewProbationResultEmpList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List resumeList = this.evsManageSer.viewEvsInfoList(request,"viewProbationResumeList");
		modelMap.put("resumeList",resumeList);
		if(resumeList != null && resumeList.size() > 0){
			//第一次进入页面取RESUME_SEQ，以便其他信息查询
			this.getResumeSeqPartner(request, resumeList, modelMap);
			modelMap.put("evsType", request.getParameter("evsType") == null ? "performance" : request.getParameter("evsType"));
	
			Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
			if("".equals(StringUtil.checkNull(request.getParameter("RESUME_SEQ")))){
				param.put("RESUME_SEQ", modelMap.get("RESUME_SEQ"));
			}
			Map tempMap = null;
			for(int i=0;i<resumeList.size();i++){
				tempMap = (Map)resumeList.get(i);
				if(StringUtil.checkNull(tempMap.get("SEQ")).equals(StringUtil.checkNull(param.get("RESUME_SEQ")))){
					break;
				}
			}
			modelMap.put("viewEvsObjectInfo",tempMap);
			param.put("EVS_OBJECT_SEQ", tempMap.get("EVS_OBJECT_SEQ"));
			//奖励、惩戒信息
			modelMap.put("getEvsRewardInfo",this.evsManageSer.viewEvsInfoList(tempMap,"getEvsRewardInfo"));
			modelMap.put("getEvsPunishmentInfo",this.evsManageSer.viewEvsInfoList(tempMap,"getEvsPunishmentInfo"));

			//获取评价等级item
			List viewGradeList =  this.evsManageSer.viewEvsInfoList(param,"viewGradeList");
			modelMap.put("viewGradeList",viewGradeList);
		}
		
		return new ModelAndView("/evs/manage/viewProbationResultEmp", modelMap);
	}
	
	/**
	 * 考核历史信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvsResultHistory")
	public ModelAndView viewEvsResultHistoryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag == null || "".equals(firstFlag)){
			List objectList = this.evsManageSer.viewEvsInfoList(request,"viewEvsResultHistory");
			modelMap.put("objectList", objectList);
			modelMap.put("objectListSize", objectList == null ? 0 : objectList.size());
			
			LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo",linkMap);
		}else{
			modelMap.put("objectListSize", 0 );
			modelMap.put("EMP_OFFICE","'15119'");
			modelMap.put("EMP_OFFICE_NAME","在职");
		}
		return new ModelAndView("/evs/manage/viewEvsResultHistory", modelMap);
	}

	@RequestMapping(value = "/viewEvsResultHistoryExport")
	public void viewEvsResultHistoryListExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		aliasNameList.add("Name");
		aliasNameList.add("ID");
		aliasNameList.add("Department");
		aliasNameList.add("Post_grade"); 
		aliasNameList.add("Evaluation_year");
		aliasNameList.add("January");
		aliasNameList.add("February");
		aliasNameList.add("March");
		aliasNameList.add("April");
		aliasNameList.add("May");
		aliasNameList.add("June");
		aliasNameList.add("July");
		aliasNameList.add("August");
		aliasNameList.add("September");
		aliasNameList.add("October");
		aliasNameList.add("November");
		aliasNameList.add("December");
		aliasNameList.add("Ability");

		List objectList = this.evsManageSer.viewEvsInfoList(request,"viewEvsResultHistory");

		List list = new ArrayList();
		for(int i=0;i<objectList.size();i++){
			LinkedHashMap dataMap = (LinkedHashMap)objectList.get(i);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", dataMap.get("LOCAL_NAME"));
			map.put("CELL1", dataMap.get("EMPID"));
			map.put("CELL2", dataMap.get("DEPTNAME"));
			map.put("CELL3", dataMap.get("POST_GRADE_NAME"));
			map.put("CELL4", dataMap.get("EVS_YEAR"));
			map.put("CELL5", dataMap.get("EVS_MONTH1"));
			map.put("CELL6", dataMap.get("EVS_MONTH2"));
			map.put("CELL7", dataMap.get("EVS_MONTH3"));
			map.put("CELL8", dataMap.get("EVS_MONTH4"));
			map.put("CELL9", dataMap.get("EVS_MONTH5"));
			map.put("CELL10", dataMap.get("EVS_MONTH6"));
			map.put("CELL11", dataMap.get("EVS_MONTH7"));
			map.put("CELL12", dataMap.get("EVS_MONTH8"));
			map.put("CELL13", dataMap.get("EVS_MONTH9"));
			map.put("CELL14", dataMap.get("EVS_MONTH10"));
			map.put("CELL15", dataMap.get("EVS_MONTH11"));
			map.put("CELL16", dataMap.get("EVS_MONTH12"));
			map.put("CELL17", dataMap.get("EVS_MONTH13"));
			list.add(map);
		}

		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"evsHistory");

	}

	@RequestMapping(value = "/viewEvsResultExport")
	public void viewEvsResultExportListExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		aliasNameList.add("ID");
		aliasNameList.add("Name");
		aliasNameList.add("Department");
		aliasNameList.add("Post_grade"); 
		aliasNameList.add("Date_start");
		aliasNameList.add("State");
		aliasNameList.add("Myself_state");
		aliasNameList.add("Myself_score");
		aliasNameList.add("Myself_grade");
		aliasNameList.add("Once_name");
		aliasNameList.add("Once_rank");
		aliasNameList.add("Once_state");
		aliasNameList.add("Once_score");
		aliasNameList.add("Once_grade");
		aliasNameList.add("Second_name");
		aliasNameList.add("Second_rank");
		aliasNameList.add("Second_state");
		aliasNameList.add("Second_score");
		aliasNameList.add("Second_grade");
		aliasNameList.add("Third_name");
		aliasNameList.add("Third_rank");
		aliasNameList.add("Third_state");
		aliasNameList.add("Third_score");
		aliasNameList.add("Third_grade");
		aliasNameList.add("HR_evaluation");//等级考核人事
		aliasNameList.add("Opinion");
		aliasNameList.add("Update_time");
		aliasNameList.add("Update_by");
		aliasNameList.add("Myself_evaluation");
		aliasNameList.add("Once_evaluation");
		aliasNameList.add("Second_evaluation");
		aliasNameList.add("Third_evaluation");

		List objectList = this.evsManageSer.viewEvsInfoList(request,"viewEvsResultList");

		List list = new ArrayList();
		for(int i=0;i<objectList.size();i++){
			LinkedHashMap dataMap = (LinkedHashMap)objectList.get(i);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", dataMap.get("EMPID"));
			map.put("CELL1", dataMap.get("LOCAL_NAME"));
			map.put("CELL2", dataMap.get("DEPTNAME"));
			map.put("CELL3", dataMap.get("POST_GRADE_NAME"));
			map.put("CELL4", dataMap.get("DATE_STARTED"));
			map.put("CELL5", dataMap.get("ACTIVITY_NAME"));
			map.put("CELL6", dataMap.get("AFFIRM_FLAG_NAME0"));
			map.put("CELL7", dataMap.get("EVS_POINT0"));
			map.put("CELL8", dataMap.get("EVS_GRADE0"));
			map.put("CELL9", dataMap.get("LOCAL_NAME1"));
			map.put("CELL10", dataMap.get("POST_GRADE_NAME1"));
			map.put("CELL11", dataMap.get("AFFIRM_FLAG_NAME1"));
			map.put("CELL12", dataMap.get("EVS_POINT1"));
			map.put("CELL13", dataMap.get("EVS_GRADE1"));
			map.put("CELL14", dataMap.get("LOCAL_NAME2"));
			map.put("CELL15", dataMap.get("POST_GRADE_NAME2"));
			map.put("CELL16", dataMap.get("AFFIRM_FLAG_NAME2"));
			map.put("CELL17", dataMap.get("EVS_POINT2"));
			map.put("CELL18", dataMap.get("EVS_GRADE2"));
			map.put("CELL19", dataMap.get("LOCAL_NAME3"));
			map.put("CELL20", dataMap.get("POST_GRADE_NAME3"));
			map.put("CELL21", dataMap.get("AFFIRM_FLAG_NAME3"));
			map.put("CELL22", dataMap.get("EVS_POINT3"));
			map.put("CELL23", dataMap.get("EVS_GRADE3"));
			
			map.put("CELL24", dataMap.get("FINAL_GRADE"));
			map.put("CELL25", dataMap.get("FINAL_AFFIRM_CONTENT"));
			map.put("CELL26", dataMap.get("UPDATED_BY"));
			map.put("CELL27", dataMap.get("UPDATE_DATE"));
			map.put("CELL28", HtmlRegexpUtil.filterHtml(StringUtil.checkNull(dataMap.get("AFFIRM_CONTENT0"))).replaceAll("&nbsp;", " "));
			map.put("CELL29", HtmlRegexpUtil.filterHtml(StringUtil.checkNull(dataMap.get("AFFIRM_CONTENT1"))).replaceAll("&nbsp;", " "));
			map.put("CELL30", HtmlRegexpUtil.filterHtml(StringUtil.checkNull(dataMap.get("AFFIRM_CONTENT2"))).replaceAll("&nbsp;", " "));
			map.put("CELL31", HtmlRegexpUtil.filterHtml(StringUtil.checkNull(dataMap.get("AFFIRM_CONTENT3"))).replaceAll("&nbsp;", " "));
			list.add(map);
		}

		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"evsResult");

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
	@RequestMapping(value = "/viewFileRoomList")
	public ModelAndView viewFileRoomInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List fileRoomList = this.evsManageSer.viewEvsInfoList(request,"viewFileRoomList");
		modelMap.put("fileRoomList", fileRoomList);

		LinkedHashMap param = null;
		
		if(fileRoomList!=null && fileRoomList.size() > 0){
			param = (LinkedHashMap)fileRoomList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("fileRoomSize", fileRoomList.size());
		}else{
			modelMap.put("fileRoomSize", 0);
		}
		return new ModelAndView("/evs/manage/viewFileRoomList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddFileRoomInfo")
	public ModelAndView viewAddFileRoomInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List fileRoomList = this.evsManageSer.viewEvsInfoList(request,"viewFileRoomList");
		
		LinkedHashMap param = new LinkedHashMap();
		
		if(fileRoomList!=null && fileRoomList.size() > 0){
			param = (LinkedHashMap)fileRoomList.get(0);
			
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "FILE_ROOM");
			fileParam.put("APPLY_NO", param.get("SEQ"));
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList",fileList);
		}
		modelMap.put("fileRoomInfo", param);
		return new ModelAndView("/evs/manage/viewAddFileRoomInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addFileRoomInfo")
	@ResponseBody
	public Map addFileRoomInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//seq不为空：修改，为空：新增
		String seq = StringUtil.checkNull(request.getParameter("SEQ"));
		int result = 1;
		if(!"".equals(seq)){
			result = this.evsManageSer.addEvsInfo(request, "updateFileRoomInfo");
		}else{
			result = this.evsManageSer.addFileRoomInfo(request);
		}
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 保存成功！
			map.put("navTabId", "FILE_ROOM");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));// 保存失败！
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteFileRoomInfo")
	@ResponseBody
	public Map deleteFileRoomInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.evsManageSer.addEvsInfo(request,"deleteFileRoomInfo");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功！
			map.put("navTabId", "FILE_ROOM");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败！
		}
		return map;
	}
	
	//新增评价对象模板下载
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/evsAffirmorImportDemo")
	public void evsAffirmorImportDemo(HttpServletRequest request,	HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String flag=StringUtil.checkNull(request.getParameter("flag"));
		String name = this.evsManageSer.evsAffirmorImportDemo(request, aliasNameList, list , mapList, mapNameList,flag);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	//新增模板下载
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/evsAffirmTargetImportDemo")
		public void evsAffirmTargetImportDemo(HttpServletRequest request,	HttpServletResponse response,ModelMap modelMap) throws Exception{
			List aliasNameList = new ArrayList();
			List list = new ArrayList();
			List mapList = new ArrayList();
			List mapNameList = new ArrayList();
			String flag=StringUtil.checkNull(request.getParameter("flag"));
			String name = this.evsManageSer.evsAffirmTargetImportDemo(request, aliasNameList, list , mapList, mapNameList,flag);
			LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
			this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
		}
}
