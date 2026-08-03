package com.ait.empsubject.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.MonthAttendanceSer;
import com.ait.empsubject.service.EmpsubjectSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AjaxSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;
import com.ait.inct.service.SalesmanEvaluationSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;

/**
 * Copyright:    LG CNS
 * Company:      LG CNS
 * @fileName:    EmpsubjectCtroller.java
 * @Create date: 2014.06.25
 * @Create by:   L.H.H
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/empsubject")
public class EmpsubjectCtroller {
	Logger logger = Logger.getLogger(EmpsubjectCtroller.class);
	
	@Autowired
	private EmpsubjectSer empsubjectSer ;
	
	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private ExcelUtilSer excelUtilSer;

	@Autowired
	private SalesmanEvaluationSer salesmanEvaluationSer;
	@Autowired
    private AjaxSer ajaxSer;
	@Autowired
	private MonthAttendanceSer monthAttendanceSer;

	public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");
	
	/**************************************************************************************************************/
	/**
	 * 课程组管理
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.26
	 */
	@RequestMapping(value = "/viewSubjectGroupList")
	public ModelAndView viewSubjectGroupList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("subjectGroup", empsubjectSer.getSubjectGroupList(request));
		modelMap.put("groupList", empsubjectSer.getGroupList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empsubjectSer.getSubjectGroupListCnt(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
        if (request.getParameter("USE_YN")==null && ( modelMap.get("USE_YN")==null || ((String)modelMap.get("USE_YN")).equals("")))
    		modelMap.put("USE_YN", "Y");
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216727"));
		
		return new ModelAndView("/empsubject/viewSubjectGroupList",modelMap);
	}

	// @Create date: 2014.06.26
	@RequestMapping(value = "/updateSubjectGroupView")
	public ModelAndView updateSubjectGroup(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("personName",admin.getLocalName() );
		modelMap.put("personId",admin.getAdminID());
		modelMap.put("subjectGroupInfo", empsubjectSer.getSubjectGroupInfo(request));
		return new ModelAndView("/empsubject/updateSubjectGroupView", modelMap);
	}

	// @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateSubjectGroup")
	@ResponseBody
	public Map updateSubjectGroup(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = empsubjectSer.updateSubjectGroup(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "jy0300");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	// @Create date: 2014.06.26
	@RequestMapping(value = "/addSubjectGroupView")
	public ModelAndView addSubjectGroupView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personId",admin.getAdminID());
		modelMap.put("personName",admin.getLocalName() );
		return new ModelAndView("/empsubject/addSubjectGroupView", modelMap);
	}

	// @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addSubjectGroup")
	@ResponseBody
	public Map addSubjectGroup(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = empsubjectSer.addSubjectGroup(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "jy0300");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}
	
	/**************************************************************************************************************/
	/**
	 * 课程管理
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.30
	 */
	@RequestMapping(value = "/viewSubjectList")
	public ModelAndView viewSubjectList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("subject", empsubjectSer.getSubjectList(request));
		modelMap.put("groupList", empsubjectSer.getGroupList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empsubjectSer.getSubjectListCnt(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
        if (request.getParameter("USE_YN")==null && ( modelMap.get("USE_YN")==null || ((String)modelMap.get("USE_YN")).equals("")))
    		modelMap.put("USE_YN", "Y");
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217882"));
		
		return new ModelAndView("/empsubject/viewSubjectList",modelMap);
	}

	// @Create date: 2014.06.30
	@RequestMapping(value = "/updateSubjectView")
	public ModelAndView updateSubject(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("personName",admin.getLocalName() );
		modelMap.put("personId",admin.getAdminID());
		modelMap.put("empId",admin.getEmpID());
		modelMap.put("defaultCpny",admin.getCpnyId());
		modelMap.put("subjectInfo", empsubjectSer.getSubjectInfo(request));
		return new ModelAndView("/empsubject/updateSubjectView", modelMap);
	}

	// @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateSubject")
	@ResponseBody
	public Map updateSubject(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = empsubjectSer.updateSubject(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "jy0600");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	// @Create date: 2014.06.30
	@RequestMapping(value = "/addSubjectView")
	public ModelAndView addSubjectView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personId",admin.getAdminID());
		modelMap.put("empId",admin.getEmpID());
		modelMap.put("personName",admin.getLocalName() );	
		modelMap.put("groupList", empsubjectSer.getGroupList(request));
		return new ModelAndView("/empsubject/addSubjectView", modelMap);
	}

	// @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addSubject")
	@ResponseBody
	public Map addSubject(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = empsubjectSer.addSubject(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "jy0600");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}
	
	/**************************************************************************************************************/
	/**
	 * 课程别实绩汇总
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.07.01
	 */
	@RequestMapping(value = "/viewGradeStatisticsList")
	public ModelAndView viewGradeStatisticsList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("gradeStatistics", empsubjectSer.getGradeStatisticsList(request));
		modelMap.put("groupList", empsubjectSer.getGroupList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empsubjectSer.getGradeStatisticsListCnt(request));
		//modelMap.put("payAreaList", empsubjectSer.getDeptAreaList(request)) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		/*if (!(request.getParameter("areaStr") == null || request.getParameter("areaStr").equals(""))){
			String PAY_AREA_CD_NOS= request.getParameter("areaStr").toString() ;
			String[] NOS = PAY_AREA_CD_NOS.split("!");
			modelMap.put("checkedAreaList", NOS); 
		}*/
        
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216726"));
		
		return new ModelAndView("/empsubject/viewGradeStatisticsList",modelMap);
	}
	
	/**
	 * 课程别实绩汇总导出Excel
	 * @param request
	 * @param responses
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * @Create date: 2014.07.02
	 */

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/viewGradeStatisticsListExcel")
	public void  viewGradeStatisticsListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{

		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		String lang = searchMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("大区");
		aliasNameList.add("支社");
		aliasNameList.add("产品");
		aliasNameList.add("课程组 ");
		aliasNameList.add("课程名称");
		aliasNameList.add("总人数");
		aliasNameList.add("参加人员数量");
		aliasNameList.add("覆盖率");
		aliasNameList.add("成绩");
		aliasNameList.add("课程满意度");
		aliasNameList.add("讲师满意度");
		aliasNameList.add("NPS");
		aliasNameList.add("课时");
		aliasNameList.add("培训开始日期 ");
		aliasNameList.add("培训地点");
		aliasNameList.add("NPS推荐理由");
		aliasNameList.add("NPS不推荐理由");
		aliasNameList.add("Sales Talk内容");
		aliasNameList.add("竞争社信息");
		aliasNameList.add("促销员其他反馈");

		String[] columns = { "PAY_AREA_CD", "ORG_NM", "PROD_TP", "SUBJT_GR_NM", "SUBJT_NM", "TOTAL_COUNT"
				           , "STUDY_COUNT", "RATE", "GRADE_POINT", "COURSE_S","LECTURER_S", "NPS", "SUBJT_TIME", "EDU_TIME", "EDU_RM", "NPS_REASON"
				           , "NPS_NO_REASON", "SALES_TALK", "COM_CLUB_INFO", "OTHER_FEEDBACK"};
		//提取导出数据列表
		List aliasValueList  = this.empsubjectSer.getGradeStatisticsListExcel(request) ;
		String name = "gradeStatisticsList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}
	/**************************************************************************************************************/
	/**
	 * 课程周别汇总
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.07.04
	 */
	@RequestMapping(value = "/viewGradeWeekList")
	public ModelAndView viewGradeWeekList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("gradeWeek", empsubjectSer.getGradeWeekList(request));
		modelMap.put("groupList", empsubjectSer.getGroupList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empsubjectSer.getGradeWeekListCnt(request));
		//modelMap.put("payAreaList", empsubjectSer.getDeptAreaList(request)) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		/*if (!(request.getParameter("areaStr") == null || request.getParameter("areaStr").equals(""))){
			String PAY_AREA_CD_NOS= request.getParameter("areaStr").toString() ;
			String[] NOS = PAY_AREA_CD_NOS.split("!");
			modelMap.put("checkedAreaList", NOS); 
		}*/
		
		Date sysdate =new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");		
	    if (request.getParameter("seach_START_DATE")==null && ( modelMap.get("START_DATE")==null || ((String)modelMap.get("START_DATE")).equals("")))
		    modelMap.put("START_DATE", df.format(sysdate));
	            
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216729"));
		
		return new ModelAndView("/empsubject/viewGradeWeekList",modelMap);
	}
	
	/**
	 * 课程周别汇总导出Excel
	 * @param request
	 * @param responses
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * @Create date: 2014.07.04
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/viewGradeWeekListExcel")
	public void viewGradeWeekListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{

		/*AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		Date sysdate =new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");		
	    if (request.getParameter("seach_START_DATE")==null && ( modelMap.get("START_DATE")==null || ((String)modelMap.get("START_DATE")).equals("")))
		    modelMap.put("START_DATE", df.format(sysdate));
	    
        if (request.getParameter("seach_BRANCH_CD")==null && ( modelMap.get("BRANCH_CD")==null || ((String)modelMap.get("BRANCH_CD")).equals("")))
    		modelMap.put("BRANCH_CD", admin.getBRANCH_CD());

		List ContractByInsertList = this.empsubjectSer.getGradeWeekListExcel(request) ;
		modelMap.put("itemList", ContractByInsertList);
		return new ModelAndView("/empsubject/viewGradeWeekListExcel",modelMap);
		*/
		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		String lang = searchMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("大区");
		aliasNameList.add("支社");
		aliasNameList.add("渠道");
		aliasNameList.add("课程组 ");
		aliasNameList.add("课程名称");
		aliasNameList.add("前周累积");
		aliasNameList.add("今周");
		aliasNameList.add("现在累积");
		aliasNameList.add("总人数");
		aliasNameList.add("比率");

		String[] columns = { "PAY_AREA_CD", "ORG_NM", "CHANNEL_NM", "SUBJT_GR_NM", "SUBJT_NM", "PRE_WEEK_ACCOUNT"
				           , "CURRENT_WEEK", "NOW_ACCOUNT", "TOTAL_COUNT", "RATE"};
		
		Date sysdate =new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");		
	    if (request.getParameter("seach_START_DATE")==null && ( modelMap.get("START_DATE")==null || ((String)modelMap.get("START_DATE")).equals("")))
		    modelMap.put("START_DATE", df.format(sysdate));
        
		//提取导出数据列表
		List aliasValueList  = this.empsubjectSer.getGradeWeekListExcel(request) ;
		String name = "gradeWeekList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}
	/**************************************************************************************************************/
	/**
	 * 教育实绩
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.07.08
	 */
	@RequestMapping(value = "/viewPromotoGradeList")
	public ModelAndView viewPromotoGradeList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("EMPNO", searchMap.get("dwz.person.empId"));
		modelMap.put("searchMap", searchMap);
		modelMap.put("promotoGrade", empsubjectSer.getPromotoGradeList(request));
		modelMap.put("groupList", empsubjectSer.getGroupList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empsubjectSer.getPromotoGradeListCnt(request));
		//modelMap.put("payAreaList", empsubjectSer.getDeptAreaList(request)) ;
		modelMap.put("defaultCpny", admin.getCpnyId()); 
		modelMap.put("DEL_USER", admin.getEmpID());
		
		/*if (!(request.getParameter("areaStr") == null || request.getParameter("areaStr").equals(""))){
			String PAY_AREA_CD_NOS= request.getParameter("areaStr").toString() ;
			String[] NOS = PAY_AREA_CD_NOS.split("!");
			modelMap.put("checkedAreaList", NOS); 
		}*/
		
		Date sysdate =new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");		
	    if ((request.getParameter("seach_START_DATE")==null || request.getParameter("seach_START_DATE").equals("")) && ( modelMap.get("START_DATE")==null || ((String)modelMap.get("START_DATE")).equals("")))
		    modelMap.put("START_DATE", df.format(sysdate));    
	    if ((request.getParameter("seach_END_DATE")==null || request.getParameter("seach_END_DATE").equals(""))  && ( modelMap.get("END_DATE")==null || ((String)modelMap.get("END_DATE")).equals("")))
		    modelMap.put("END_DATE", df.format(sysdate));
	    
        if (request.getParameter("USE_YN")==null && ( modelMap.get("USE_YN")==null || ((String)modelMap.get("USE_YN")).equals("")))
    		modelMap.put("USE_YN", "Y");
        modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216728"));
		System.out.print("###############   "+modelMap.toString());
		return new ModelAndView("/empsubject/viewPromotoGradeList",modelMap);
	}
	
	/**
	 * 教育实绩导出Excel
	 * @param request
	 * @param responses
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * @Create date: 2014.07.08
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/viewPromotoGradeListExcel")
	public void viewPromotoGradeListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		Date sysdate =new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");		
	    if (modelMap.get("START_DATE")==null || ((String)modelMap.get("START_DATE")).equals(""))
		    modelMap.put("START_DATE", df.format(sysdate));	    
	    if (modelMap.get("END_DATE")==null || ((String)modelMap.get("END_DATE")).equals(""))
		    modelMap.put("END_DATE", df.format(sysdate));

        if (request.getParameter("USE_YN")==null && ( modelMap.get("USE_YN")==null || ((String)modelMap.get("USE_YN")).equals("")))
    		modelMap.put("USE_YN", "Y");
		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang = searchMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("大区");
		aliasNameList.add("支社");
		aliasNameList.add("城市");
		aliasNameList.add("流通  ");
		aliasNameList.add("门店名称");
		aliasNameList.add("门店等级");
		aliasNameList.add("社号");
		aliasNameList.add("姓名");
		aliasNameList.add("性别");
		aliasNameList.add("电话");
		aliasNameList.add("主责产品");
		aliasNameList.add("课程组");
		aliasNameList.add("课程名称");
		aliasNameList.add("线上区分");
		aliasNameList.add("成绩");
		aliasNameList.add("平均成绩");
		aliasNameList.add("课程满意度");
		aliasNameList.add("讲师满意度");
		aliasNameList.add("NPS");
		aliasNameList.add("课时");
		aliasNameList.add("讲师姓名");
		aliasNameList.add("培训日期");
		aliasNameList.add("培训地点");
		aliasNameList.add("NPS推荐理由");
		aliasNameList.add("NPS不推荐理由");
		aliasNameList.add("Sales Talk内容");
		aliasNameList.add("竞争社信息");
		aliasNameList.add("促销员其他反馈");
		aliasNameList.add("更新时间");
		aliasNameList.add("更新人");
		aliasNameList.add("使用与否");
		
		String[] columns = { "PAY_AREA_NM", "BRANCH_NM", "CITY_NAME", "CHANNEL1_NAME" 
						   , "SHOP_NAME", "SHOP_LEVEL"
				           , "EMPNO", "EMP_NM", "SEX_NAME", "OFFICE_PHONE"
				           , "CN_CD_NM", "SUBJT_GR_NM", "SUBJT_NM", "ONLINE_STATE"
				           , "GRADE_POINT", "AVG_POINT", "COURSE_S", "LECTURER_S"
				           , "NPS", "SUBJT_TIME", "TCR_NM", "EDU_TIME","EDU_RM"
				           , "NPS_REASON", "NPS_NO_REASON", "SALES_TALK", "COM_CLUB_INFO", "OTHER_FEEDBACK"
				           , "UPDT_DTIME", "UPDT_USER", "USE_YN"};

		//提取导出数据列表
		List aliasValueList  = this.empsubjectSer.getPromotoGradeListExcel(request) ;
		String name = "promotoGradeList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}
	/**
	 * 教育实绩删除
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.07.10
	 */
	@RequestMapping(value = "/deletePromotoGrade")
	@ResponseBody
	public String deletePromotoGrade(HttpServletRequest request)throws Exception{
			
		int resultCode = this.empsubjectSer.deletePromotoGrade(request) ;

		JSONObject jo = new JSONObject();
		if(resultCode == 1){
			jo.put("jieguo", "Y");
		}else{	
			jo.put("jieguo", "Y");
		}	
		return jo.toString();
		
	}
	/**
	 * 教育实绩导入结果查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPromotoGradeResultList")
	public ModelAndView viewPromotoGradeResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());

		
		List itemList = this.empsubjectSer.getPromotoGradeResultList(request, searchMap);
		int impTotalCnt = this.empsubjectSer.getPromotoGradeResultListCnt(request, searchMap);	
		int impErrCnt   = this.empsubjectSer.getPromotoGradeErrCnt(request, searchMap);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "218297"));
		return new ModelAndView("/empsubject/viewPromotoGradeResultList",modelMap);
	}
	
	/**
	 * 教育实绩导入正式表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createPromotoGradeResult")
	@ResponseBody
	public int createPromotoGradeResult(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = this.empsubjectSer.importPromotoGradeRAWFromExcel(request,response,modelMap);
		return result.equals("OK")?1:0;
	}
	/**
	 * 教育实绩导入  导入的数据列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPromotoGradeResultListExcel")
	public ModelAndView viewPromotoGradeResultListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		searchMap.put("SUBSD_CD", admin.getCpnyId());


		List salesmanEvalDataImpList = this.empsubjectSer.getPromotoGradeResultList(request, searchMap) ;
		modelMap.put("MDATA", salesmanEvalDataImpList);
		return new ModelAndView("/empsubject/viewPromotoGradeResultListExcel",modelMap);
	}
	/*
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/viewPromotoGradeResultListExcel")
	public void viewPromotoGradeResultListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		searchMap.put("SUBSD_CD", admin.getCpnyId());
		
		String lang = searchMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("公司");
		aliasNameList.add("社号");
		aliasNameList.add("大区");
		aliasNameList.add("部门");
		aliasNameList.add("课程组");
		aliasNameList.add("课程名称");
		aliasNameList.add("成绩");
		aliasNameList.add("课程满意度");
		aliasNameList.add("讲师满意度");
		aliasNameList.add("NPS");
		aliasNameList.add("讲师姓名");
		aliasNameList.add("培训开始日期");
		aliasNameList.add("培训地点");
		aliasNameList.add("状态<");
		aliasNameList.add("课时");
		aliasNameList.add("NPS推荐理由");
		aliasNameList.add("NPS不推荐理由");
		aliasNameList.add("Sales Talk内容");
		aliasNameList.add("竞争社信息");
		aliasNameList.add("促销员其他反馈");		
		aliasNameList.add("验证结果");
		aliasNameList.add("更新人");
		aliasNameList.add("更新时间");
		
		String[] columns = { "SUBSD_CD","EMPNO","PAY_AREA_CD", "BRANCH_CD", "SUBJT_GR", "SUBJT_ID", "GRADE_POINT", "COURSE_S", "LECTURER_S", "NPS"
							, "TCR_NM", "EDU_TIME","EDU_RM", "USE_YN", "SUBJT_TIME", "NPS_REASON", "NPS_NO_REASON", "SALES_TALK", "COM_CLUB_INFO", "OTHER_FEEDBACK"
				           , "ERROR_INFO", "UPDT_USER", "UPDT_DTIME"};

		//提取导出数据列表
		List aliasValueList  = this.empsubjectSer.getPromotoGradeResultList(request, searchMap) ;
		String name = "promotoGradeResultList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}*/
	/**
	 * 教育实绩模板下载
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
		
	@RequestMapping(value = "/downloadPromotoGradeImpTemplate")
	public void downloadPromotoGradeImpTemplate(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		/*List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		List tipList = new ArrayList();
		String name = empsubjectSer.getTemplateInfo(request, aliasNameList, list , mapList, mapNameList, tipList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);*/
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		

		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		
		List aliasNameList = new ArrayList();
		aliasNameList.add("地域*");
		aliasNameList.add("大区*");
		aliasNameList.add("支社*");
		aliasNameList.add("促销员社号*");
		aliasNameList.add("课程组ID*");
		aliasNameList.add("课程ID*");
		aliasNameList.add("促销员考试成绩*");
		aliasNameList.add("课程满意度*");
		aliasNameList.add("讲师满意度*");
		aliasNameList.add("NPS*");
		aliasNameList.add("讲师姓名*");
		aliasNameList.add("培训日期*");
		aliasNameList.add("地点*");
		aliasNameList.add("使用状态*");
		aliasNameList.add("课时*");
		aliasNameList.add("NPS推荐理由*");
		aliasNameList.add("NPS不推荐理由*");
		aliasNameList.add("Sales Talk内容*");
		aliasNameList.add("竞争社信息*");
		aliasNameList.add("促销员其他反馈*");
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "TSTO");
		map.put("CELL1", "212738");
		map.put("CELL2", "112919");
		map.put("CELL3", "YCH123456");
		map.put("CELL4", "K01");
		map.put("CELL5", "K01-001-LTV");
		map.put("CELL6", "80");
		map.put("CELL7", "4.7");
		map.put("CELL8", "5.0");
		map.put("CELL9", "85");
		map.put("CELL10", "张三");
		map.put("CELL11", "20140818");
		map.put("CELL12", "北京");
		map.put("CELL13", "Y");
		map.put("CELL14", "8");
		map.put("CELL15", "我有很多问题啊");
		map.put("CELL16", "我有很多问题啊");
		map.put("CELL17", "我有很多问题啊");
		map.put("CELL18", "我有很多问题啊");
		map.put("CELL19", "我有很多问题啊");
		list.add(map);
		
		List tipList = new ArrayList();

		//地域 TIP
        LinkedHashMap tipMap_SubSd = new LinkedHashMap();
        tipMap_SubSd.put("TIP_COLUMN", "地域*");
        tipMap_SubSd.put("TIP_CONTENT", "请统一写LGECH");
        tipList.add(tipMap_SubSd);
		//促销员社号 TIP
        LinkedHashMap tipMap_EmpNo = new LinkedHashMap();
        tipMap_EmpNo.put("TIP_COLUMN", "促销员社号*");
        tipMap_EmpNo.put("TIP_CONTENT", "填写社号,如无社号填写身份证号");
        tipList.add(tipMap_EmpNo);
		//支社 TIP
        LinkedHashMap tipMap_BRANCH = new LinkedHashMap();
        tipMap_BRANCH.put("TIP_COLUMN", "支社*");
        tipMap_BRANCH.put("TIP_CONTENT", "填写支社ID。"+"\n"+"参考支社ID参考sheet");
        tipList.add(tipMap_BRANCH);
		//课程组ID TIP
        LinkedHashMap tipMap_SUBJT_GR_ID = new LinkedHashMap();
        tipMap_SUBJT_GR_ID.put("TIP_COLUMN", "课程组ID*");
        tipMap_SUBJT_GR_ID.put("TIP_CONTENT", "填写课程组ID。"+"\n"+"参考课程标准编码");
        tipList.add(tipMap_SUBJT_GR_ID);
		//课程ID TIP
        LinkedHashMap tipMap_SUBJT_ID = new LinkedHashMap();
        tipMap_SUBJT_ID.put("TIP_COLUMN", "课程ID*");
        tipMap_SUBJT_ID.put("TIP_CONTENT", "填写课程ID。"+"\n"+"参考课程标准编码");
        tipList.add(tipMap_SUBJT_ID);
		//促销员考试成绩 TIP
        LinkedHashMap tipMap_GradePoint = new LinkedHashMap();
        tipMap_GradePoint.put("TIP_COLUMN", "促销员考试成绩*");
        tipMap_GradePoint.put("TIP_CONTENT", "数字0-100，如果为空，请写0");
        tipList.add(tipMap_GradePoint);
		//课程满意度 TIP
        LinkedHashMap tipMap_CorseS = new LinkedHashMap();
        tipMap_CorseS.put("TIP_COLUMN", "课程满意度*");
        tipMap_CorseS.put("TIP_CONTENT", "数字0-5。"+"\n"+"因为是不记名问卷，计算该课程的平均值即可。"+"\n"+"请保留1位小数，如果为空，请写0");
        tipList.add(tipMap_CorseS);    
        //讲师满意度 TIP
        LinkedHashMap tipMap_LecturerS = new LinkedHashMap();
        tipMap_LecturerS.put("TIP_COLUMN", "讲师满意度*");
        tipMap_LecturerS.put("TIP_CONTENT", "数字0-5。"+"\n"+"因为是不记名问卷，计算该课程的平均值即可。"+"\n"+"请保留1位小数，如果为空，请写0");
        tipList.add(tipMap_LecturerS);
        //NPS TIP
        LinkedHashMap tipMap_NPS = new LinkedHashMap();
        tipMap_NPS.put("TIP_COLUMN", "NPS*");
        tipMap_NPS.put("TIP_CONTENT", "数字0-100。"+"\n"+"NPS一个课程只有一个值，保留个位，不要有小数点，也不要带%。"+"\n"+"如果为空，请写0。");
        tipList.add(tipMap_NPS);
        //讲师姓名 TIP
        LinkedHashMap tipMap_TCR_NM = new LinkedHashMap();
        tipMap_TCR_NM.put("TIP_COLUMN", "讲师姓名*");
        tipMap_TCR_NM.put("TIP_CONTENT", "讲师姓名");
        tipList.add(tipMap_TCR_NM);
        //培训日期 TIP
        LinkedHashMap tipMap_EDU_TIME = new LinkedHashMap();
        tipMap_EDU_TIME.put("TIP_COLUMN", "培训日期*");
        tipMap_EDU_TIME.put("TIP_CONTENT", "培训日期为20110321这种格式");
        tipList.add(tipMap_EDU_TIME);
        //地点 TIP
        LinkedHashMap tipMap_EDU_RM = new LinkedHashMap();
        tipMap_EDU_RM.put("TIP_COLUMN", "地点*");
        tipMap_EDU_RM.put("TIP_CONTENT", "写城市名即可");
        tipList.add(tipMap_EDU_RM);
        //使用状态 TIP
        LinkedHashMap tipMap_USE_YN = new LinkedHashMap();
        tipMap_USE_YN.put("TIP_COLUMN", "使用状态*");
        tipMap_USE_YN.put("TIP_CONTENT", "只能是Y或N");
        tipList.add(tipMap_USE_YN);
        //课时 TIP
        LinkedHashMap tipMap_SUBJT_TIME = new LinkedHashMap();
        tipMap_SUBJT_TIME.put("TIP_COLUMN", "课时*");
        tipMap_SUBJT_TIME.put("TIP_CONTENT", "数字。"+"\n"+"填写具体课程进行的小时数，保留一位小数，如果为空，请写0");
        tipList.add(tipMap_SUBJT_TIME);
        //NPS推荐理由 TIP
        LinkedHashMap tipMap_NPS_REASON = new LinkedHashMap();
        tipMap_NPS_REASON.put("TIP_COLUMN", "NPS推荐理由*");
        tipMap_NPS_REASON.put("TIP_CONTENT", "不多于200汉字,可以为空。"+"\n"+"因为是不记名调查问卷，而且存在不填写，重复内容等情况，请讲师进行汇总后填写在上传表格的第一行，然后将内容复制在所有行，即可。");
        tipList.add(tipMap_NPS_REASON);
        //NPS不推荐理由 TIP
        LinkedHashMap tipMap_NPS_NO_REASON = new LinkedHashMap();
        tipMap_NPS_NO_REASON.put("TIP_COLUMN", "NPS不推荐理由*");
        tipMap_NPS_NO_REASON.put("TIP_CONTENT", "不多于200汉字,可以为空。"+"\n"+"因为是不记名调查问卷，而且存在不填写，重复内容等情况，请讲师进行汇总后填写在上传表格的第一行，然后将内容复制在所有行，即可。");
        tipList.add(tipMap_NPS_NO_REASON);
        //Sales Talk内容 TIP
        LinkedHashMap tipMap_SALES_TALK = new LinkedHashMap();
        tipMap_SALES_TALK.put("TIP_COLUMN", "Sales Talk内容*");
        tipMap_SALES_TALK.put("TIP_CONTENT", "不多于200汉字,可以为空。"+"\n"+"因为是不记名调查问卷，而且存在不填写，重复内容等情况，请讲师进行汇总后填写在上传表格的第一行，然后将内容复制在所有行，即可。");
        tipList.add(tipMap_SALES_TALK);
        //竞争社信息 TIP
        LinkedHashMap tipMap_COM_CLUB_INFO = new LinkedHashMap();
        tipMap_COM_CLUB_INFO.put("TIP_COLUMN", "竞争社信息*");
        tipMap_COM_CLUB_INFO.put("TIP_CONTENT", "不多于200汉字,可以为空。"+"\n"+"因为是不记名调查问卷，而且存在不填写，重复内容等情况，请讲师进行汇总后填写在上传表格的第一行，然后将内容复制在所有行，即可。");
        tipList.add(tipMap_COM_CLUB_INFO);
        //促销员其他反馈 TIP
        LinkedHashMap tipMap_OTHER_FEEDBACK = new LinkedHashMap();
        tipMap_OTHER_FEEDBACK.put("TIP_COLUMN", "促销员其他反馈*");
        tipMap_OTHER_FEEDBACK.put("TIP_CONTENT", "不多于200汉字,可以为空。"+"\n"+"因为是不记名调查问卷，而且存在不填写，重复内容等情况，请讲师进行汇总后填写在上传表格的第一行，然后将内容复制在所有行，即可。");
        tipList.add(tipMap_OTHER_FEEDBACK);    
        
        
		
        //课程组ID TIP
		//paramMap.put("CPNY_ID",admin.getCpnyId());
		/*List codeList =  empsubjectSer.getSubjectGroupCodeList(paramMap);
        Object[] TypeList = codeList.toArray();
        String EvalHeader = "说明:";
        for (int i = 0; i < TypeList.length; i++) {
        	EvalHeader = EvalHeader + "\n"+ ((Map)TypeList[i]).get("CODE_DESC");
        }
        LinkedHashMap tipMap_Eval = new LinkedHashMap();
        tipMap_Eval.put("TIP_COLUMN", "课程组ID*");
        tipMap_Eval.put("TIP_CONTENT", EvalHeader);
        tipList.add(tipMap_Eval);*/
        
        //大区code TIP
		paramMap.put("CPNY_ID",admin.getCpnyId());
  		List payAreaCdList = this.salesmanEvaluationSer.getPayAreaCodeList(paramMap);
  		Object[] TypeList = payAreaCdList.toArray();
        TypeList = payAreaCdList.toArray();
        String PayAreaHeader = "说明:";
        for (int i = 0; i < TypeList.length; i++) {
        	PayAreaHeader = PayAreaHeader + "\n"+ ((Map)TypeList[i]).get("CODE_DESC");
        }  
        LinkedHashMap tipMap_Area = new LinkedHashMap();
        tipMap_Area.put("TIP_COLUMN", "大区*");
        tipMap_Area.put("TIP_CONTENT", PayAreaHeader);
        tipList.add(tipMap_Area);
		
        String cpnyId = admin.getCpnyId();
        String codeSql = "SELECT HD.ORG_NAME_LOCAL AS CONTENT,HD.DEPTNO AS CODE FROM HR_DEPARTMENT HD  WHERE DEPT_TYPE = 'branch' AND HD.CPNY_ID = '"+cpnyId+"' ORDER BY HD.DEPTNO ";
		mapNameList.add("支社ID参考");
		mapList.add(codeSql);
		String name = "EducationPromotoGradeExcelImport";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		
		this.excelUtilSer.exportExcelMoreSheetWithHeaderTip(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name, tipList);
	}
	/**************************************************************************************************************/
	/**
	 * 教育实绩导出Excel
	 * @param request
	 * @param responses
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * @Create date: 2014.07.08
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/viewNoSwipingListExcel")
	public ModelAndView viewNoSwipingListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
/*
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		Date sysdate =new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");		
	    if (modelMap.get("beginTime")==null || ((String)modelMap.get("beginTime")).equals(""))
		    modelMap.put("beginTime", df.format(sysdate));	    
	    if (modelMap.get("endTime")==null || ((String)modelMap.get("endTime")).equals(""))
		    modelMap.put("endTime", df.format(sysdate));
		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang = searchMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("日期");
		aliasNameList.add("社号/姓名");
		aliasNameList.add("部门");
		aliasNameList.add("班次  ");
		aliasNameList.add("状态");
		aliasNameList.add("上班打卡时间");
		aliasNameList.add("下班打卡时间");
		aliasNameList.add("长度");
		
		String[] columns = { "arDateStr", "empIdAndName", "deptName", "shiftName", "itemName", "fromTime"
				           , "toTime", "lengthq"};

		//提取导出数据列表
		List aliasValueList  = this.empsubjectSer.getPromotoNoSwipingListExcel(request) ;
		String name = "NoSwipingList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);*/
	
			//姓名工号
			modelMap.put("empIdAndName", request.getParameter("seach_empIdAndName"));
			//部门
			modelMap.put("deptName", request.getParameter("seach_deptName"));
			//开始日期
			modelMap.put("beginTime", request.getParameter("seach_beginTime"));
			//结束日期
			modelMap.put("endTime", request.getParameter("seach_endTime"));
			//状态
			modelMap.put("status", request.getParameter("seach_status"));
			//考勤区分
			modelMap.put("attendanceDistinct", request.getParameter("seach_attendanceDistinct"));
			
			request.setAttribute("empIdAndName", request.getParameter("seach_empIdAndName"));
			request.setAttribute("deptName", request.getParameter("seach_deptName"));
			request.setAttribute("beginTime", request.getParameter("seach_beginTime"));
			request.setAttribute("endTime", request.getParameter("seach_endTime"));
			request.setAttribute("status", request.getParameter("seach_status"));
			request.setAttribute("attendanceDistinct", request.getParameter("seach_attendanceDistinct"));
			//查询状态
			List statusList = this.ajaxSer.statusList(request);     
			modelMap.put("statusList",statusList);
			//查询为刷卡的数据
			List noSwipingCardList = this.monthAttendanceSer.noSwipingCardList(request);     
			modelMap.put("noSwipingCardList",noSwipingCardList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.monthAttendanceSer.noSwipingCardListCnt(request));
			return new ModelAndView("/ar/empsubject/viewNoSwipingListExcel",modelMap);
	}
}
