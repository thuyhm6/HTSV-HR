
package com.ait.ess.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.dao.ArCardRecordDao;
import com.ait.ar.service.ArDetailSer;
import com.ait.ar.service.CycleSer;
import com.ait.ar.service.DynamicGroupSer;
import com.ait.ar.service.ItemsSer;
import com.ait.ar.service.ShiftSer;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.service.AffirmApplySer;
import com.ait.ess.service.AffirmLeaveApplySer;
import com.ait.ess.service.AnnualadjustmentInfoSer;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.ess.service.PersonInfoSer;
import com.ait.hrm.action.TransferOrderCtroller;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: InfoApplyCtroller.java
 * @Description:考勤申请：加班,休假,出差,外出,年假调整申请
 * @Create date: Feb 14, 2012 10:09:38 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 14, 2012 10:09:38 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: 2014-06-20    
 * @Update by: lishunji(lishunji@ait.net.cn)   
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/infoApply")
public class InfoApplyCtroller {

	Logger logger = Logger.getLogger(TransferOrderCtroller.class);

	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private ShiftSer shiftSer;
	@Autowired
	private InfoApplyDao infoApplyDao;
	@SuppressWarnings("unused")
	@Autowired
	private PersonInfoSer personInfoSer;
	
	@Autowired
	private AuthorityUtil authorityUtil;
	
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private InfoApplyLeaveSer infoApplyLeaveSer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private InfoApplySer infoApplySer;

	@Autowired
	private ArDetailSer arDetailSer;
	
	@Autowired
	private AffirmLeaveApplySer affirmApplySers;
	
	@Autowired
	private DynamicGroupSer dynamicGroupSer ;
	
	@Autowired
	private AnnualadjustmentInfoSer annualadjustmentInfoSer;
	
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;	

	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	@Autowired
	private AffirmApplySer affirmApplySer;
	@Autowired
	private CycleSer cycleSer;
	@Autowired
	private ItemsSer itemsSer;
	
	@Autowired
	private ArCardRecordDao arCardRecordDao;
	/**
	 * 加班决裁查看(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtAffirmInfoList")
	public ModelAndView viewOvertimeInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List arMonthList = this.infoApplySer.getOtApplyArMonthList(request) ;
		modelMap.put("arMonthList", arMonthList);
		
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("AFFIRM_FLAG", request.getParameter("seach_AFFIRM_FLAG"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		modelMap.put("oTAffirmList", infoApplySer.getOtAffirmInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getOtAffirmInfoListCnt(request));
		
		return new ModelAndView("/ess/infoApply/viewOtAffirmInfoList", modelMap);
	}
	
	
	/**加班上限申请
	 * (batch pass or reject leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveOtLimitBatch")
	@ResponseBody
	public Map<String, Object> approveOtLimitBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.approveOtLimitBatch(request);
			if (result == 1) {
				String navTabId=request.getParameter("navTabId");
				map.put("navTabId", navTabId);
				map.put("formId", "viewOverTimeLimtList");
				//map.put("navTabId", "ess0802");
				map.put("message", "批量加班上限申请成功!");
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "批量加班上限申请失败!");
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	/**hub个人加班上限控制
	 * (batch pass or reject leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveOtLimitBatchHUB")
	@ResponseBody
	public Map<String, Object> approveOtLimitBatchHUB(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.approveOtLimitBatchHUB(request);
			if (result == 1) {
				String navTabId=request.getParameter("navTabId");
				map.put("navTabId", navTabId);
				map.put("formId", "viewPersonOverTimeLimtList");
				//map.put("navTabId", "ess0802");
				map.put("message", "加班上限修改成功!");
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "加班上限修改失败!");
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**加班上限审批
	 * (batch pass or reject leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveOtLimitBatchShenPi")
	@ResponseBody
	public Map<String, Object> approveOtLimitBatchShenPi(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.approveOtLimitBatchShenPi(request);
			if (result == 1) {
				String navTabId=request.getParameter("navTabId");
				map.put("navTabId", navTabId);
				map.put("formId", "viewOverTimeLimtShenPiList");
				//map.put("navTabId", "ess0802");
				map.put("message", "批量加班上限审批成功!");
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "批量加班上限审批失败!");
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	
	
	/**加班上限申请
	 * (batch pass or reject leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/calculateAvg")
	@ResponseBody
	public Map<String, Object> calculateAvg(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List gpList = shiftSer.calculateAvg(request);
		LinkedHashMap gpMap = (LinkedHashMap) gpList.get(0);
		map.put("TOTAL_G", gpMap.get("G"));
		map.put("TOTAL_P", gpMap.get("P"));
		return map;
	}
	
	
	//个人加班上限导入模板下载
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/personOTLimitImportDemoLoad")
	public void personOTLimitImportDemoLoad(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String flag=StringUtil.checkNull(request.getParameter("flag"));
		String name = infoApplySer.getOTLimitList(request, aliasNameList, list , mapList, mapNameList,flag);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	//年假计划导入模板下载
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/personVacPlanImportDemoLoad")
	public void personVacPlanImportDemoLoad(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String flag=StringUtil.checkNull(request.getParameter("flag"));
		String name = infoApplySer.getVacPlanList(request, aliasNameList, list , mapList, mapNameList,flag);
		LinkedHashMap sqlContentmap = this.excelUtilSer
		.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	
	/**
	 * 加班上限导入临时页面数据查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtLimitDataImportResultList")
	public ModelAndView viewOtLimitDataImportResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());
		List itemList = this.infoApplySer.getOtLimitDataImportResultList(request, searchMap);	
		modelMap.put("MDATA", itemList);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "215992"));
		return new ModelAndView("/ess/infoApply/viewOtLimitDataImportResultList",modelMap);
	}
	
	/**
	 * 加班上限导入到正式表
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOTLimit")
	@ResponseBody
	public int insertOTLimit(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = infoApplySer.insertOTLimit(request);
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 内务加班查询(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCoordApplyOtInfoList")
	public ModelAndView viewCoordApplyOtInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		List shiftList = shiftSer.getShiftList1(request);
		List itemList = itemsSer.getItemParamList2(request);
		modelMap.put("itemList", itemList) ;
		modelMap.put("shiftList", shiftList) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		/*modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO")); 
		modelMap.put("CONFIRM_FLAG", request.getParameter("seach_CONFIRM_FLAG")); 
		modelMap.put("empName", request.getParameter("dwz.person.empName")); 
		modelMap.put("empInfo", request.getParameter("dwz.person.empInfo")); 
		modelMap.put("GROUP_NO", request.getParameter("seach_GROUP_NO")); 
		modelMap.put("SHIFT_NO", request.getParameter("seach_SHIFT_NO")); 
		modelMap.put("ITEM_NO", request.getParameter("seach_ITEM_NO")); 
		modelMap.put("AFFIRM_FLAG", request.getParameter("seach_AFFIRM_FLAG")); 
		modelMap.put("CONFIRM_FLAG", request.getParameter("seach_CONFIRM_FLAG")); 
		modelMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE")); 
		modelMap.put("EmpOffice", request.getParameter("seach_EmpOffice")); */
		String firstFlag= request.getParameter("firstFlag");
		if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("otCoordList", infoApplySer.getCoordOtInfoList(request));
			LinkedHashMap linkMap2= (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo",linkMap2);
		}else{
			Date d=new Date();   
			SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");   
			modelMap.put("FROM_DATE", df.format(d));
			modelMap.put("TO_DATE", df.format(d)); 
		}
		modelMap.put("SHIFT_NO", request.getParameter("seach_SHIFT_NO"));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/infoApply/viewCoordApplyOtInfoList", modelMap);
	}   
	
	
	/**
	 * 内务倒休查询(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCoordApplyAdjustInfoList")
	public ModelAndView viewCoordApplyAdjustInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		List shiftList = shiftSer.getShiftList1(request);
		List itemList = itemsSer.getItemParamList2(request);
		modelMap.put("itemList", itemList) ;
		modelMap.put("shiftList", shiftList) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		Date d=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		modelMap.put("FROM_DATE", df.format(d));
		modelMap.put("TO_DATE", df.format(d)); 
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO")); 
		modelMap.put("CONFIRM_FLAG", request.getParameter("seach_CONFIRM_FLAG")); 
		modelMap.put("empName", request.getParameter("dwz.person.empName")); 
		modelMap.put("empInfo", request.getParameter("dwz.person.empInfo")); 
		modelMap.put("GROUP_NO", request.getParameter("seach_GROUP_NO")); 
		modelMap.put("SHIFT_NO", request.getParameter("seach_SHIFT_NO")); 
		modelMap.put("ITEM_NO", request.getParameter("seach_ITEM_NO")); 
		modelMap.put("AFFIRM_FLAG", request.getParameter("seach_AFFIRM_FLAG")); 
		modelMap.put("CONFIRM_FLAG", request.getParameter("seach_CONFIRM_FLAG")); 
		modelMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE")); 
		modelMap.put("EmpOffice", request.getParameter("seach_EmpOffice")); 
		modelMap.put("ADJSTYN", request.getParameter("seach_ADJSTYN")); 
		String firstFlag= request.getParameter("firstFlag");
		if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("otCoordList", infoApplySer.getCoordAdjustInfoList(request));
			LinkedHashMap linkMap2= (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo",linkMap2);
		}
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/infoApply/viewCoordApplyAdjustInfoList", modelMap);
	} 
	/**
	 * hub进行加班上限的控制(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonOverTimeLimtList")
	public ModelAndView viewPersonOverTimeLimtList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		
		String firstFlag= request.getParameter("firstFlag");
		modelMap.put("AR_MONTH", new SimpleDateFormat("yyyy-MM").format(new Date()));
		modelMap.put("DIV_GP", request.getParameter("seach_DIV_GP"));
		
		if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("otLimtList", infoApplySer.viewPersonOverTimeLimtList(request));
		}
		
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","14014304");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		modelMap.put("codeList", codeList);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/infoApply/viewPersonOverTimeLimtList", modelMap);
	}  
	
	
	/**
	 * part长进行加班上限申请(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOverTimeLimtList")
	public ModelAndView viewOverTimeLimtList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		
		String firstFlag= request.getParameter("firstFlag");
		modelMap.put("AR_MONTH", new SimpleDateFormat("yyyy-MM").format(new Date()));
		modelMap.put("DIV_GP", request.getParameter("seach_DIV_GP"));
		
		if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("otLimtList", infoApplySer.getOverTimeLimtList(request));
		}
		
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","14014304");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		modelMap.put("codeList", codeList);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/infoApply/viewOverTimeLimtList", modelMap);
	}  
	
	
	
	
	
	
	/**
	 * group长进行加班上限审批	(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOverTimeLimtShenPiList")
	public ModelAndView viewOverTimeLimtShenPiList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		
		String firstFlag= request.getParameter("firstFlag");
		modelMap.put("AR_MONTH", new SimpleDateFormat("yyyy-MM").format(new Date()));
		modelMap.put("DIV_GP", request.getParameter("seach_DIV_GP"));
	
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","14014304");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		modelMap.put("codeList", codeList);
		
		if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("otLimtList", infoApplySer.getOverTimeLimtShenPiList(request));
		}
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/infoApply/viewOverTimeLimtShenPiList", modelMap);
	}           
	
	
	/**
	 * 显示加班申请(view overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtApplyInfo")
	public ModelAndView viewOtApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		if(admin!=null){
			String cpnyId = admin.getCpnyId().toString();
			modelMap.put("defaultCpny", cpnyId);
		}
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		List otDeductTimeList = this.infoApplySer.getOtDeductTimeList(request) ;
		modelMap.put("otDeductTimeList", otDeductTimeList);
		List affirmorList = this.infoApplySer.getAffirmorList(request);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		
		return new ModelAndView("/ess/infoApply/viewOtApplyInfo", modelMap);
	}
	
	/**
	 * L加班--决裁查看(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLOtApplyInfoList")
	public ModelAndView viewLOtApplyInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		String from_time = request.getParameter("seach_FROM_TIME")!=null?
				request.getParameter("seach_FROM_TIME"):new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String to_time = request.getParameter("seach_TO_TIME")!=null?
				request.getParameter("seach_TO_TIME"):new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		 modelMap.put("FROM_TIME",from_time);
		 modelMap.put("TO_TIME",to_time);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("admin",admin);
		List oTAffirmList = infoApplySer.getOtAffirmInfoList(request);
		modelMap.put("oTAffirmList", oTAffirmList);
		if(oTAffirmList!=null && oTAffirmList.size()>0){
		    modelMap.put(UiUtil.TOTAL_COUNT_NAME, oTAffirmList.size());
		}else{
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		return new ModelAndView("/ess/infoApply/viewLOtApplyInfoList", modelMap);
	}

	/**
	 * 加班查看完整决裁详情信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullApplyOtBatchAffirmInfo")
	public ModelAndView viewFullOtApplyBatchAffirmInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
			//infoApplyLeaveSer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		

		modelMap.put("OtBatchAffirmList", infoApplyLeaveSer.getOtBatchAffirmInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyLeaveSer.getOtBatchAffirmInfoCnt(request));
		modelMap.put("APPLY_NO",  request.getParameter("APPLY_NO"));
		
		return new ModelAndView("/ess/infoApply/viewFullApplyOtBatchAffirmInfo", modelMap);
	}
	
	/**
	 * 内务加班批量处理(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPiciOtAffirmLBatchList")  
	public ModelAndView viewPiciOtAffirmLBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List nullOTTSTOAffirmList = new ArrayList();
		String type = request.getParameter("type");
		if(type !=null && !"".equals(type)){
			nullOTTSTOAffirmList = infoApplyLeaveSer.viewNullBatchOTTSTOAffirmInfoList(request);
		}else{
			nullOTTSTOAffirmList = infoApplyLeaveSer.getNullBatchOTTSTOAffirmInfoList(request);
		}
		modelMap.put("nullOTTSTOAffirmList", nullOTTSTOAffirmList);
		modelMap.put("nullOTTSTOAffirmListCnt",nullOTTSTOAffirmList==null ? 0: nullOTTSTOAffirmList.size());
		modelMap.put("OT_TYPE_CODE" , JsonUtil.writeInternal(empInfoSer.getCodeList("31", request)));
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, 0);
				//c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				//c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("END_DATE",last);
			}
		}
		//时间下拉菜单
		GregorianCalendar today = new GregorianCalendar();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String timeStr = "[{";
		String spacing1 = "02";
		String spacing2 = "13";
		String spacingTemp = "02";
		int i = 2;
		while (today.before(tomorrow) || today.equals(tomorrow)) {
			
			String time = sdf.format(today.getTime());
			timeStr = timeStr + "'CODENO':'"+time+"','CODENAME':'"+time+"'},{";
			if (i % 2 == 0) {
			    spacingTemp = spacing1;
			} else {
			    spacingTemp = spacing2;
			}
			today.add(Calendar.MINUTE, Integer.parseInt(spacingTemp));
			i++;
		}
		timeStr = timeStr +"}]";
		modelMap.put("TIME_STR", timeStr);
		modelMap.put("type", type);
		List list = shiftSer.getShiftList(request);
		modelMap.put("shiftList", list);
		modelMap.put("seach_SHIFT_NO", request.getParameter("seach_SHIFT_NO"));
		return new ModelAndView("/ess/infoApply/viewPiciOtAffirmLBatchList", modelMap);
	}
	
	/**
	 * 批量加班处理(可添加删除审批者)(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyOtLBatchByAnyApproverList")  
	public ModelAndView viewApplyOtLBatchByAnyApproverList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List nullOTTSTOAffirmList = new ArrayList();
		String type = request.getParameter("type");
		if(type !=null && !"".equals(type)){
			nullOTTSTOAffirmList = infoApplyLeaveSer.viewNullBatchOTTSTOAffirmInfoList(request);
		}else{
			nullOTTSTOAffirmList = infoApplyLeaveSer.getNullBatchOTTSTOAffirmInfoList(request);
		}
		modelMap.put("nullOTTSTOAffirmList", nullOTTSTOAffirmList);
		modelMap.put("nullOTTSTOAffirmListCnt",nullOTTSTOAffirmList==null ? 0: nullOTTSTOAffirmList.size());
		modelMap.put("OT_TYPE_CODE" , JsonUtil.writeInternal(empInfoSer.getCodeList("31", request)));
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("END_DATE",last);
			}
		}
		//时间下拉菜单
		GregorianCalendar today = new GregorianCalendar();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String timeStr = "[{";
		String spacing = "15";
		while (today.before(tomorrow) || today.equals(tomorrow)) {
			
			String time = sdf.format(today.getTime());
			timeStr = timeStr + "'CODENO':'"+time+"','CODENAME':'"+time+"'},{";
			today.add(Calendar.MINUTE, Integer.parseInt(spacing));
			
		}
		timeStr = timeStr +"}]";
		modelMap.put("TIME_STR", timeStr);
		
		return new ModelAndView("/ess/infoApply/viewApplyOtLBatchByAnyApproverList", modelMap);
	}
	
	/**
	 * 生产值批量加班申请(view OT Apply Batch)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyOTBatchInfoHAE")  
	public ModelAndView viewApplyOTBatchInfoHAE(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String type = request.getParameter("deleteYN");
		List nullOTAffirmList = new ArrayList();
		if("Y".equals(type)){
			this.infoApplyLeaveSer.delOTApplyInfoForBatchInfoListHAE(request);
		}else{
			nullOTAffirmList = infoApplyLeaveSer.viewAddOTApplyInfoForBatchHAE(request);
		}
		modelMap.put("nullOTAffirmList", nullOTAffirmList);
		modelMap.put("nullOTAffirmListCnt",nullOTAffirmList==null ? 0: nullOTAffirmList.size());
		modelMap.put("OT_TYPE_CODE" , JsonUtil.writeInternal(empInfoSer.getCodeList("31", request)));
		//时间下拉菜单
		GregorianCalendar today = new GregorianCalendar();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String timeStr = "[{";
		String spacing = "15";
		while (today.before(tomorrow) || today.equals(tomorrow)) {
			
			String time = sdf.format(today.getTime());
			timeStr = timeStr + "'CODENO':'"+time+"','CODENAME':'"+time+"'},{";
			today.add(Calendar.MINUTE, Integer.parseInt(spacing));
			
		}
		timeStr = timeStr +"}]";
		modelMap.put("TIME_STR", timeStr);
		
		return new ModelAndView("/ess/infoApplyAttendance/viewApplyOTBatchInfoHAE", modelMap);
	}
	
	/**
	 * p加班批次--决裁查看(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPiciOtAffirmPBatchList")
	public ModelAndView viewPiciOtAffirmPBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("admin", admin);
		 
		
		modelMap.put("oTAffirmList", infoApplySer.getOtAffirmInfoListBatch(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getOtAffirmInfoListCntBatch(request));
		
		return new ModelAndView("/ess/infoApply/viewPiciOtAffirmPBatchList", modelMap);
	}
	/**
	 * 个人加班--决裁查看(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPOtApplyInfoList")
	public ModelAndView viewPOtApplyInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String firstFlag = request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
				//获取当前月第一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("FROM_DATE",first);
				//获取当前月最后一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("TO_DATE",last);
			}
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("admin",admin);
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
//		if (firstFlag !=null && !"".equals(firstFlag)) {
			List oTAffirmList = infoApplySer.getPersonalOtInfoDetailList(request);
			modelMap.put("oTAffirmList", oTAffirmList);
//			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getOtAffirmInfoListCnt(request));
			
//		}else {
//			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
//		}
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "533"));
	
		return new ModelAndView("/ess/infoApply/viewPOtApplyInfoList", modelMap);
	}
	
	
	/**
	 * 个人加班--加班搜索(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonOtApplyInfoList")
	public ModelAndView viewPersonOtApplyInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String firstFlag = request.getParameter("firstFlag");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//第一次进页面 默认只查询当月
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
				//获取当前月第一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("FROM_DATE",first);
				//获取当前月最后一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("TO_DATE",last);
			}
		}
//		if (firstFlag !=null && !"".equals(firstFlag)) {
			List oTAffirmList = infoApplySer.getPersonOtApplyInfoList(request);
			modelMap.put("oTAffirmList", oTAffirmList);
			modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "533"));
//		}
				
		return new ModelAndView("/ess/infoApply/viewPersonOtApplyInfoList", modelMap);
	}
	
	
	/**
	 * 查询日期性质 (select date type by date and cpny_id)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDateTypeByDateAndCpny")
	@ResponseBody
	public Map getDateTypeByDateAndCpny(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = infoApplySer.getDateTypeByDateAndCpny(request);
		Map<String, Object> dateMap = (Map)list.get(0);	
		map.put("result", dateMap);
		return dateMap;
	}
	
	
	
	/**
	 * 查询员工日期性质 (select date type by date and cpny_id)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDateTypeByDateAndEmpCpny")
	@ResponseBody
	public Map getDateTypeByDateAndEmpCpny(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		//List list =infoApplySer.getDataType(request);//查询公司排班日期类型

		List list =infoApplySer.getDataTypeW(request);//查询公司排班日期类型
		//List listpeople=infoApplySer.getDataTypeForPeople(request);//查询个人排班类型
		List listpeople=infoApplySer.getDataTypeForPeopleW(request);//查询个人排班类型
		Map<String, Object> dateMap = (Map)list.get(0);	
		Map<String, Object> dateMapPeople = (Map)listpeople.get(0);	
		//map.put("result", dateMap);
		//map.put("result", dateMapPeople);
		dateMap.putAll(dateMapPeople);
		return dateMap;
	}
	
	
	/**
	 * 查询日期默认加班开始结束时间 (select date type by date and cpny_id)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDefaultStartEndTime")
	@ResponseBody
	public Map getDefaultStartEndTime(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();	 
	 
		Map<String, Object> dateMap = infoApplySer.getDefaultStartEndTime(request);	
		 	
		 
		return dateMap;
	}
	/**
	 * 获取加班上限 (select date type by date and cpny_id)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOtLimit")
	@ResponseBody
	public Map getOtLimit(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();	 
		
		Map<String, Object> dateMap = infoApplySer.getOtLimit(request);	
		
		
		return dateMap;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getChangeOtType")
	@ResponseBody
	public Map getChangeOtType(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

	 
		List list =infoApplySer.getChangeOtType(request); 
	 
	 
		Map<String, Object> dateMap = (Map)list.get(0);	
		 	
		 
		return dateMap;
	}
	
	/**
	 * 显示加班申请(view overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPOtApplyInfo")
	public ModelAndView viewPOtApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String APPLY_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		modelMap.put("CREATE_DATE", APPLY_DATE);
		if(admin!=null){
			String cpnyId = admin.getCpnyId().toString();
			modelMap.put("defaultCpny", cpnyId);
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
        String lastLastMonth = format.format(c.getTime());
        
        String llastDate = (String)infoApplySer.getLLastMonthLastDay(request);
        lastLastMonth = llastDate!=null?llastDate:lastLastMonth;
       // modelMap.put("LLASTMONTH", lastLastMonth);
        
        //  查询当前锁定月的上月份。
        LinkedHashMap dateMap = new LinkedHashMap();
    	dateMap.put("FLAG", "S");
		dateMap.put("CPNY_ID", admin.getCpnyId());
		dateMap.put("PERSON_ID", admin.getPersonId());
		dateMap.put("LANGUAGE", admin.getLanguage());
		String arStartDateStr=this.infoApplyDao.getCurrentArDate(dateMap);
		GregorianCalendar LLASTMONTH1   = DateUtil.ParseGregorianCalendar(arStartDateStr);
		LLASTMONTH1.add(2, -1);
		 lastLastMonth=DatatypeFactory.newInstance().newXMLGregorianCalendar(LLASTMONTH1).toString().substring(0, 10);
		 modelMap.put("LLASTMONTH", lastLastMonth);
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		Map shiftMap = (Map) infoApplySer.getPersonIdShift(request);
		modelMap.put("SHIFTNAME",shiftMap.get("SHIFTNAME") );
		modelMap.put("SHIFT_NO",shiftMap.get("SHIFT_NO") );
		modelMap.put("worktimeString", infoApplySer.getPersonIdWorkTime(request));
		List otDeductTimeList = this.infoApplySer.getOtDeductTimeList(request) ;
		modelMap.put("otDeductTimeList", otDeductTimeList);
		//查看当前日期的休假类型
		List list =infoApplySer.getDataTypeTsto(request);//查询公司排班日期类型
		
		Map<String, Object> dateTypeMap = (Map)list.get(0);	
		LinkedHashMap applyParam = infoApplySer.getApplyParam(request);
		if ("1440".equals(dateTypeMap.get("TYPEID"))) {
			modelMap.put("APPLY_TYPE_CODE", "32");
			modelMap.put("WORKTIMESTRING",applyParam.get("worktimeString"));
			modelMap.put("STARTTIME",applyParam.get("STARTTIME"));
			modelMap.put("ENDTIME",applyParam.get("ENDTIME"));
		}else if ("1441".equals(dateTypeMap.get("TYPEID"))) {
			modelMap.put("APPLY_TYPE_CODE", "33");
			modelMap.put("WORKTIMESTRING",applyParam.get("STARTTIME")+"-"+applyParam.get("STARTTIME"));
			modelMap.put("STARTTIME",applyParam.get("STARTTIME"));
			modelMap.put("ENDTIME",applyParam.get("STARTTIME"));
		} else {
			modelMap.put("APPLY_TYPE_CODE", "34");
			modelMap.put("WORKTIMESTRING",applyParam.get("STARTTIME")+"-"+applyParam.get("STARTTIME"));
			modelMap.put("STARTTIME",applyParam.get("STARTTIME"));
			modelMap.put("ENDTIME",applyParam.get("STARTTIME"));
		}
		
		
		
		//通过写的方法获取决裁线，方法目前有问题先搁置 by:wangqiang 2014/07/30 worktimeString
		String applyTypeNo = "31";
		String personId = (String) (request.getParameter("PERSON_ID")==null?admin.getPersonId():request.getParameter("PERSON_ID"));
		String language = (String) (request.getParameter("LANGUAGE")==null?admin.getPersonId():request.getParameter("LANGUAGE"));
		String applyTypeCode = request.getParameter("applyType")==null?"":request.getParameter("applyType");
		String applyLength = request.getParameter("applyLength")==null?"":request.getParameter("applyLength");
		List affirmorList = this.infoApplySer.getAffirmorListByString(applyTypeNo, personId, applyTypeCode, applyLength, language);
		request.setAttribute("PERSON_ID", personId);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("applyParam", applyParam);
		return new ModelAndView("/ess/infoApply/viewPOtApplyInfo", modelMap);
	}
	
	
	/**
	 * 显示加班申请(view overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLOtApplyInfo")
	public ModelAndView viewOtApplyInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		if(admin!=null){
			String cpnyId = admin.getCpnyId().toString();
			modelMap.put("defaultCpny", cpnyId);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
        String lastLastMonth = format.format(c.getTime());
        
        String llastDate = (String)infoApplySer.getLLastMonthLastDay(request);
        lastLastMonth = llastDate!=null?llastDate:lastLastMonth;
        LinkedHashMap dateMap = new LinkedHashMap();
            	dateMap.put("FLAG", "S");
    	dateMap.put("CPNY_ID", admin.getCpnyId());
    	dateMap.put("PERSON_ID", admin.getPersonId());
		String arStartDateStr=this.infoApplyDao.getCurrentArDate(dateMap);
		GregorianCalendar LLASTMONTH1   = DateUtil.ParseGregorianCalendar(arStartDateStr);
		LLASTMONTH1.add(2, -1);
		 lastLastMonth=DatatypeFactory.newInstance().newXMLGregorianCalendar(LLASTMONTH1).toString().substring(0, 10);
		//显示申请日期
		modelMap.put("APPLY_OT_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        modelMap.put("LLASTMONTH", lastLastMonth);
        
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		List otDeductTimeList = this.infoApplySer.getOtDeductTimeList(request) ;
		modelMap.put("otDeductTimeList", otDeductTimeList);
		
		//通过写的方法获取决裁线，方法目前有问题先搁置 by:wangqiang 2014/07/30
		String applyTypeNo = "31";
		String personId = (String) (request.getParameter("PERSON_ID")==null?admin.getPersonId():request.getParameter("PERSON_ID"));
		String applyTypeCode = request.getParameter("applyType")==null?"":request.getParameter("applyType");
		String applyLength = request.getParameter("applyLength")==null?"":request.getParameter("applyLength");
		List affirmorList = this.infoApplySer.getAffirmorListByString(applyTypeNo, 
				personId, this.infoApplySer.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),applyTypeCode), applyLength, admin.getLanguage());

		modelMap.put("affirmorList", affirmorList);
		
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("affirmorListCnt", affirmorList.size());
		
		return new ModelAndView("/ess/infoApply/viewLOtApplyInfo", modelMap);
	}
	
	/**
	 * 显示部门员工的加班信息(view overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewDeptOtApplyInfo")
	public ModelAndView viewDeptOtApplyInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
				//获取当前月第一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("FROM_DATE",first);
				//获取当前月最后一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("TO_DATE",last);
			}
		}
		
		modelMap.put("DEPTNO",request.getParameter("seach_DEPTNO") != null?request.getParameter("seach_DEPTNO") != null:admin.getDeptNo());//默认当前自己所在部门
		
    	if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("otDeptList", infoApplySer.getDeptOtInfoList(request));
		} 
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/infoApply/viewDeptOtApplyInfo", modelMap);
	}
	
	/**
	 * 加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOvertimeApply")
	@ResponseBody
	public Map addOvertimeApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.addOvertimeApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0201");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success",request));//"保存加班申请成功!"
				map.put("statusCode", "200");
			}

		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_fail",request));//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPOvertimeApply")
	@ResponseBody
	public Map addPOvertimeApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.addOvertimeApply(request);
			if (result == 1) {
				
				 
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success",request));//"保存加班申请成功!"
				map.put("navTabId", "ess3204");
				map.put("statusCode", "200");
				map.put("callbackType", "forward");
				map.put("forwardUrl","/ess/infoApply/viewPOtApplyInfoList");
			}                      
			if(result == 12){
				//map.put("navTabId", "ess0234");
				map.put("navTabId", "ess3204");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.zancun",request));//"保存加班申请成功!"
				map.put("statusCode", "200");
				map.put("callbackType", "forward");
				map.put("forwardUrl","/ess/infoApply/viewPOtApplyInfoList?");
			}

		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_fail",request));//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addLOvertimeApply")
	@ResponseBody
	public Map addLOvertimeApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.addOvertimeApply(request);
			if (result == 1) {
			//	map.put("navTabId", "ess0235");
				 
				 map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success",request));//"保存加班申请成功!"
				 
				map.put("navTabId", "ess0236");
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
			if(result == 12){
				 
				map.put("navTabId", "ess0236");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.zancun",request));//"暂存加班申请成功!"
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
			if (result == 15) {
			 	map.put("message", "提交成功，申请期间已经关闭，该申请将在下月反映。");
				 
			 	map.put("navTabId", "ess0236");
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
			if(result == 55){
				 
				map.put("navTabId", "ess0236");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.caozuo",request));//"操作成功，此申请将反应在下月!"
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}

		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_fail",request));//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 取申请时长（批量加班页面）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOtApplyLength")
	@ResponseBody
	public void getOtApplyLength(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
		String returnString = this.infoApplySer.getOtApplyLength(request);
		PrintWriter out = response.getWriter();
	    out.println(JsonUtil.writeInternal(returnString));
		out.flush();
		out.close();
	}
	
	/**
	 * 取申请时长
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOtApplyLengthWq")
	@ResponseBody
	public Map getOtApplyLengthWq(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> timeMap = new HashMap<String, Object>();
		 timeMap = (LinkedHashMap) this.infoApplySer.getOtApplyLengthP(request);
		return timeMap;
	}
	/**
	 * 取申班次的开始结束时间
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOtFIRSTLASTTimeForChange")
	@ResponseBody
	public Map getOtFIRSTLASTTimeForChange(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> timeMap = new HashMap<String, Object>();
		timeMap = (LinkedHashMap) this.infoApplySer.getOtFIRSTLASTTimeForChange(request);
		return timeMap;
	}
	
	/**
	 * 取申请时长
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOtApplyLengthWq2")
	@ResponseBody
	public Map getOtApplyLengthWq2(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> timeMap = new HashMap<String, Object>();
		 timeMap = (LinkedHashMap) this.infoApplySer.getOtApplyLengthP2(request);
		return timeMap;
	}
	
	/**
	 * 根据班次取工作形态
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOtApplyWorkTime")
	@ResponseBody
	public Map getOtApplyWorkTime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> timeMap = new HashMap<String, Object>();
		timeMap = (LinkedHashMap) this.infoApplySer.getOtApplyWorkTime(request);
		return timeMap;
	}
	
	/**
	 * 取申请总时长（本考勤月）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOtApplyLengthZong")
	@ResponseBody
	public void getOtApplyLengthZong(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        Double returnString = this.infoApplySer.getOtApplyLengthPZong(request);
		PrintWriter out = response.getWriter();
	    out.println(JsonUtil.writeInternal(returnString));
		out.flush();
		out.close();
	}
	
	
	/**
	 * 取申请加班的人事政策
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOtApplyRemark")
	@ResponseBody
	public void getOtApplyRemark(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
		String returnString = this.infoApplySer.getOtTypeCodeRemark(request);
		PrintWriter out = response.getWriter();
	    out.println(JsonUtil.writeInternal(returnString));
		out.flush();
		out.close();
	}
	
	/**
	 * 获取班次类型
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOtApplyShiftNo")
	@ResponseBody
	public Map getOtApplyShiftNo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> timeMap = new HashMap<String, Object>();
		 timeMap = (LinkedHashMap) this.infoApplySer.getPersonIdShift(request);
		return timeMap;
		

		
	}
	
	/**
	 * 批量休假申请 (add batch leave apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDateByPersonIdAndCpny")
	@ResponseBody
	public Map getDateByPersonIdAndCpny(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unused")
		int result = 0;
		List list = infoApplySer.getDateByPersonIdAndCpny(request);
		Map<String, Object> map1 = (Map)list.get(0);	
		map.put("result", map1);
		return map1;
	}
	
	/**
	 * 查看完整加班事由信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyContentInfo")
	public ModelAndView viewFullApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List otApplyList = infoApplySer.getPersonalOtInfoDetailList(request);
		LinkedHashMap applyMap = (LinkedHashMap)otApplyList.get(0);
		
		modelMap.put("APPLY_OT_REMARK", applyMap.get("APPLY_OT_REMARK")==null?"":(String)applyMap.get("APPLY_OT_REMARK"));
		
		response.setCharacterEncoding("UTF-8");
		return new ModelAndView("/ess/infoApply/viewApplyContentInfo", modelMap);
	}

	/**
	 * 加班申请 (add overtime apply)根据加班类型以及社内还是社外来获取真正的加班类型()
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * by:  wang qiang
	 * date :2014-09-18
	 *  
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOtTypeReal")
	@ResponseBody
	public Map getOtTypeReal(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedHashMap dateMap = new LinkedHashMap();
		String applyTypeCode = request.getParameter("applyType");
	 
		dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
		//周末加班的时候才获取页面的是否调休参数
		String adjustYn = "0";
		if("33".equals(applyTypeCode)){
			adjustYn = request.getParameter("ADJUST_YN") != null ? request.getParameter("ADJUST_YN"): "0";
		}else{
			adjustYn = "0";
		}
		dateMap.put("ADJUST_YN", adjustYn);
		String teshuYn = request.getParameter("TESHU_YN") != null ? request.getParameter("TESHU_YN"): "0";
		dateMap.put("TESHU_YN", teshuYn);
		String otPlaceType = request.getParameter("OT_PLACE_TYPE") != null ? request.getParameter("OT_PLACE_TYPE") : "";
		dateMap.put("OT_PLACE_TYPE", otPlaceType);
		//页面获取到的加班类型是基础，不是最终保存到数据库中的加班类型，需要组合得出（目前只允许周末可调休）
		//1.根据加班类型（32：平日加班；33：周末加班；34：法定假加班；）
		//2.是否调休
		//3.社内、社外
		//4.在ess_ot_code_mapping表中获取对应的加班类型
		String otTypeCode ="";
		if(applyTypeCode.equals("32")||applyTypeCode.equals("33")||applyTypeCode.equals("34")){
			 otTypeCode = ((LinkedHashMap)this.infoApplyDao.getOtTypeCodeByTurn(dateMap)).get("OT_TYPE_CODE").toString();

		}else{
			otTypeCode=applyTypeCode;
		}
	 
		String endDayOffset = "0";
		//Ta法人如果是页面加班且跨天，则转成  周末夜班加班(付薪)
		if("33".equals(applyTypeCode)&&"SST".equals(admin.getCpnyId())&&"1".equals(endDayOffset)){
		otTypeCode="217871";
		}
		if("34".equals(applyTypeCode)&&"SST".equals(admin.getCpnyId())&&"1".equals(endDayOffset)){
		otTypeCode="217872";
		}
		
		 map.put("otTypeCode", otTypeCode);
		 
		return map;
	}
	/**
	 * 加班申请 (add overtime apply)更改加班类型或长度时，获取决裁线
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 *  
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAffirmList")
	@ResponseBody
	public Map getAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String ottypecode=this.getOtTypeRealTWO(request, response, modelMap);
		String applyTypeNo = request.getParameter("applyParentType");
		String personId = request.getParameter("personId");
		String applyTypeCode = ottypecode==null?"32":ottypecode;
		String applyLength = request.getParameter("applyLength");
		List list =this.infoApplySer.getAffirmorListByString(applyTypeNo, 
				 personId, this.infoApplySer.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),applyTypeCode), applyLength, admin.getLanguage());

		if (list != null && list.size() > 0) {
			map.put("affirmList", list);
			map.put("affirmListCnt", list.size());
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}
	
	public String getOtTypeRealTWO(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedHashMap dateMap = new LinkedHashMap();
		String applyTypeCode = request.getParameter("applyType");
		
		dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
		//周末加班的时候才获取页面的是否调休参数
		String adjustYn = "0";
		if("33".equals(applyTypeCode)){
			adjustYn = request.getParameter("ADJUST_YN") != null ? request.getParameter("ADJUST_YN"): "0";
		}else{
			adjustYn = "0";
		}
		dateMap.put("ADJUST_YN", adjustYn);
		String teshuYn = request.getParameter("TESHU_YN") != null ? request.getParameter("TESHU_YN"): "0";
		dateMap.put("TESHU_YN", teshuYn);
		String otPlaceType = request.getParameter("OT_PLACE_TYPE") != null ? request.getParameter("OT_PLACE_TYPE") : "";
		dateMap.put("OT_PLACE_TYPE", otPlaceType);
		dateMap.put("cpny_id", admin.getCpnyId());
		//获取个人排班类型 查看是否夜班
		String peopleType = request.getParameter("peopleType") != null ? request.getParameter("peopleType").toString() : "";
		//如果是夜班，夜班休息，周末夜班 如果不是不插入
		if("217884".equals(peopleType)||"219627".equals(peopleType)||"217885 ".equals(peopleType)){
			dateMap.put("YEBAN_YN", "1");
		}else{
			dateMap.put("YEBAN_YN", "0");
		}

		//页面获取到的加班类型是基础，不是最终保存到数据库中的加班类型，需要组合得出（目前只允许周末可调休）
		//1.根据加班类型（32：平日加班；33：周末加班；34：法定假加班；）
		//2.是否调休
		//3.社内、社外
		//4.在ess_ot_code_mapping表中获取对应的加班类型
		String otTypeCode ="";
		if(applyTypeCode.equals("32")||applyTypeCode.equals("33")||applyTypeCode.equals("34")){
			 otTypeCode = ((LinkedHashMap)this.infoApplyDao.getOtTypeCodeByTurn(dateMap)).get("OT_TYPE_CODE").toString();

		}else{
			otTypeCode=applyTypeCode;
		}
	 
		String endDayOffset = "0";
		//Ta法人如果是页面加班且跨天，则转成  周末夜班加班(付薪)
		if("33".equals(applyTypeCode)&&"SST".equals(admin.getCpnyId())&&"1".equals(endDayOffset)){
		otTypeCode="217871";
		}
		if("34".equals(applyTypeCode)&&"SST".equals(admin.getCpnyId())&&"1".equals(endDayOffset)){
		otTypeCode="217872";
		}
		
		 map.put("otTypeCode", otTypeCode);
		 
		return otTypeCode;
	}
	/**
	 * 查看完整决裁详情信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullApplyAffirmInfo")
	public ModelAndView viewFullApplyAffirmInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		String seach_APPLY_TYPE_NO = request.getParameter("seach_APPLY_TYPE_NO");
		if(!"218197".equals(seach_APPLY_TYPE_NO)){
			LinkedHashMap infoApplyOt = (LinkedHashMap) infoApplyLeaveSer.getOtApplyPersonal(request);
			modelMap.put("infoApplyOt", infoApplyOt);
		}else if("218197".equals(seach_APPLY_TYPE_NO)){
			List applyorList = annualadjustmentInfoSer.getCwaAbnormalAffirmByApplyNOList(request);
			modelMap.put("applyorInfo", applyorList.get(0));
		}
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
	 
		
		return new ModelAndView("/ess/infoApply/viewFullApplyAffirmInfo", modelMap);
	}
	
	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delOvertimeApplyInBatch")
	@ResponseBody
	public Map<String, Object> delOvertimeApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.delOvertimeApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0212");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApplyInBatch_success.delete",request));//"批量加班申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApplyInBatch_fail.delete",request));//"批量加班申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 批量取消加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delPOvertimeApplyInBatch")
	@ResponseBody
	public Map<String, Object> delPOvertimeApplyInBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.delOvertimeApplyInBatch2(request);
			if (result == 1) {
				map.put("navTabId", "ess3204");
				map.put("message",TipMessage.getTipMessage("alert.message.JIABANQUXIAOCHENGGONG.b", request));//"加班取消成功"
				map.put("formId", "viewPOtApplyInfoList");
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message",TipMessage.getTipMessage("alert.message.JIABANQUXIAOSHIBAI.b", request));//"加班取消失败"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 批量删除L加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delLOvertimeApplyInBatch")
	@ResponseBody
	public Map<String, Object> delLOvertimeApplyInBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String msg = "删除";
		String op_flag = request.getParameter("OP_FLAG");
		if("1".equals(op_flag)){
			msg = "提交";
		}
		
		try {
			result = infoApplySer.delOvertimeApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess3403");
				map.put("message", msg + "成功！");
				map.put("statusCode", "200");
				map.put("formId", "viewPiciOtAffirmLBatchList");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
			map.put("formId", "viewPiciOtAffirmLBatchList");
		} catch (Exception e) {
			map.put("message", msg + "失败！");//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
			map.put("formId", "viewPiciOtAffirmLBatchList");
		}
		map.put("result", result);
		return map;
	}
	/**
	 * 单条删除未审核加班申请( delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delOvertimeApply")
	@ResponseBody
	public String delOvertimeApply(HttpServletRequest request) throws Exception {
		String result = "";
		boolean bol = infoApplySer.delOvertimeApply(request);
		if (bol) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
	/**
	 * 取消已审核通过的加班申请( cancel overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelOvertimeApply")
	@ResponseBody
	public String cancelOvertimeApply(HttpServletRequest request) throws Exception {
		String result = "";
		boolean bol = infoApplySer.cancelOvertimeApply(request);
		if (bol) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
	
	/**
	 * 显示批量加班新(view batch overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtAffirmPBatchList")
	public ModelAndView viewOtAffirmBatchPointList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List dynamicGroupList = this.dynamicGroupSer.getDynamicGroup1List(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyId = admin.getCpnyId().toString();
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","31");
		paramMap.put("language",admin.getLanguage());
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",cpnyId);
		
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", cpnyId);
		modelMap.put("dynamicGroupList", dynamicGroupList);
		List list = infoApplySer.getPersonList(request);
		modelMap.put("personList", list);
		modelMap.put("codeList", codeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer.getPersonListCnt(request));
		if("LGEQH".equals(admin.getCpnyId())){
			modelMap.put("hourParam", infoApplySer.getOtTimeByCpnyId(request,"0"));
			modelMap.put("muniteParam", infoApplySer.getOtTimeByCpnyId(request,"1"));
		}

		//modelMap.put("otApplyList", this.infoApplySer.getOtApplyInfoList(request));
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer.getOtApplyInfoListCnt(request));
		return new ModelAndView("/ess/infoApply/viewOtAffirmPBatchList",modelMap);
	}
	
	/**
	 * 显示批量加班新(view batch overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtAffirmLBatchList")
	public ModelAndView viewOtAffirmBatchLengthList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List dynamicGroupList = this.dynamicGroupSer.getDynamicGroup1List(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyId = admin.getCpnyId().toString();
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","31");
		paramMap.put("language",admin.getLanguage());
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",cpnyId);
		//人员组
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", cpnyId);
		modelMap.put("dynamicGroupList", dynamicGroupList);
		List list = infoApplySer.getPersonList(request);
		//人员组
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("personList", list);
		modelMap.put("codeList", codeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer.getPersonListCnt(request));
		//modelMap.put("otApplyList", this.infoApplySer.getOtApplyInfoList(request));
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer.getOtApplyInfoListCnt(request));
		return new ModelAndView("/ess/infoApply/viewOtAffirmLBatchList",modelMap);
	}
	
	
	/**
	 * 显示批量加班修改查询新(view batch overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtAffirmLBatchUpdateList")
	public ModelAndView viewOtAffirmBatchLengthUpdateList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List dynamicGroupList = this.dynamicGroupSer.getDynamicGroup1List(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyId = admin.getCpnyId().toString();
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","31");
		paramMap.put("language",admin.getLanguage());
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",cpnyId);
		
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", cpnyId);
		modelMap.put("dynamicGroupList", dynamicGroupList);
		
		 
		
		request.setAttribute("APPLY_TYPE", "L");
		modelMap.put("personList", this.affirmApplySer.getEditOtApplyBatchList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getEditOtApplyListBatchCnt(request));
		 
	 
		modelMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		modelMap.put("codeList", codeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer.getPersonListCnt(request));
		
		//modelMap.put("otApplyList", this.infoApplySer.getOtApplyInfoList(request));
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer.getOtApplyInfoListCnt(request));
		return new ModelAndView("/ess/infoApply/viewOtAffirmLBatchUpdateList",modelMap);
	}
	
	

	/**
	 * 显示批量加班修改查询新(view batch overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtAffirmPBatchUpdateList")
	public ModelAndView viewPOtAffirmBatchLengthUpdateList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List dynamicGroupList = this.dynamicGroupSer.getDynamicGroup1List(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyId = admin.getCpnyId().toString();
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","31");
		paramMap.put("language",admin.getLanguage());
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",cpnyId);
		
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", cpnyId);
		modelMap.put("dynamicGroupList", dynamicGroupList);
		
		 
		
		request.setAttribute("APPLY_TYPE", "L");
		modelMap.put("personList", this.affirmApplySer.getEditOtApplyBatchList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getEditOtApplyListBatchCnt(request));
		 
	 
		modelMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		modelMap.put("codeList", codeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer.getPersonListCnt(request));
		
		//modelMap.put("otApplyList", this.infoApplySer.getOtApplyInfoList(request));
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer.getOtApplyInfoListCnt(request));
		if("LGEQH".equals(admin.getCpnyId())){
			modelMap.put("hourParam", infoApplySer.getOtTimeByCpnyId(request,"0"));
			modelMap.put("muniteParam", infoApplySer.getOtTimeByCpnyId(request,"1"));
		}
		return new ModelAndView("/ess/infoApply/viewOtAffirmPBatchUpdateList",modelMap);
	}
	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPersonCnt")
	@ResponseBody
	public Map getPersonCnt(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
	 	List pidEidList=infoApplySer.getPersonListOt(request);
		
		map.put("perCnt", pidEidList.size());
		if(pidEidList.size()==1){
			map.put("emp_type_code", ((Map)pidEidList.get(0)).get("EMP_TYPE_CODE"));
			map.put("personId", ((Map)pidEidList.get(0)).get("PERSON_ID"));
			map.put("empId", ((Map)pidEidList.get(0)).get("EMPID"));
			map.put("empName", ((Map)pidEidList.get(0)).get("LOCAL_NAME"));
			map.put("deptName", ((Map)pidEidList.get(0)).get("DEPTNAME"));
		}
		return map;
	}
	
	/**
	 * 批量加班申请 (add batch ot apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPBatchOtApply")
	@ResponseBody
	public Map addPBatchOtApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String AFFIRM_FLAG =request.getParameter("AFFIRM_FLAG");
		int result = 0;
		try {
			result = infoApplySer.addBatchOtApply(request);
			if (result == 1) {
				if(AFFIRM_FLAG.equals("-1")){
					
					map.put("navTabId", "ess0213");
					map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.zancun",request));//"批量加班申请成功!"
					map.put("statusCode", "200");
				}else{
					
					map.put("navTabId", "ess0213");
					map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApplyInBatch_success",request));//"批量加班申请成功!"
					map.put("statusCode", "200");
				}
			}
			
			
			
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApplyInBatch_fail",request));//"批量加班申请失败,请重试!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 批量加班申请 (add batch ot apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPBatchOtApplyUpdate")
	@ResponseBody
	public Map addPBatchOtApplyUpdate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String AFFIRM_FLAG =request.getParameter("AFFIRM_FLAG");
		int result = 0;
		try {
			result = infoApplySer.addBatchOtApply(request);
			if (result == 1) {
				if(AFFIRM_FLAG.equals("-1")){
					
					map.put("navTabId", "ess02467");
					map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.zancun",request));//"批量加班申请成功!"
					map.put("statusCode", "200");
					 
					map.put("callbackType", "closeCurrent");
					
				}else{
					
					map.put("navTabId", "ess02467");
					map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApplyInBatch_success",request));//"批量加班申请成功!"
					map.put("statusCode", "200");
					map.put("callbackType", "closeCurrent");
				}
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApplyInBatch_fail",request));//"批量加班申请失败,请重试!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	
	
	
	/**
	 * 批量加班申请 (add batch ot apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addLBatchOtApplyUpdate")
	@ResponseBody
	public Map addLBatchOtApplyUpdate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		

		String AFFIRM_FLAG =request.getParameter("AFFIRM_FLAG");
			int result = 0;
			try {
				result = infoApplySer.addBatchOtApply(request);
				if (result == 1) {
					if(AFFIRM_FLAG.equals("-1")){
						
						map.put("navTabId", "ess02466");
						map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.zancun",request));//"批量加班申请成功!"
						map.put("statusCode", "200");
						 
						map.put("callbackType", "closeCurrent");
						
					}else{
						
						map.put("navTabId", "ess02466");
						map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApplyInBatch_success",request));//"批量加班申请成功!"
						map.put("statusCode", "200");
						map.put("callbackType", "closeCurrent");
					}
				}
				
			
			
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApplyInBatch_fail",request));//"批量加班申请失败,请重试!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	/**
	 * 批量加班申请 (add batch ot apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addLBatchOtApply")
	@ResponseBody
	public Map addLBatchOtApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String AFFIRM_FLAG =request.getParameter("AFFIRM_FLAG");
		int result = 0;
		try {
			result = infoApplySer.addBatchOtApply(request);
			if (result == 1) {
				
				if(AFFIRM_FLAG.equals("-1")){
					
					map.put("navTabId", "ess0231");
					map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success.zancun",request));//"批量加班申请成功!"
					map.put("statusCode", "200");
				}else{
					
					map.put("navTabId", "ess0231");
					map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApplyInBatch_success",request));//"批量加班申请成功!"
					map.put("statusCode", "200");
				}
			
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApplyInBatch_fail",request));//"批量加班申请失败,请重试!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 加班对象Excel导入模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchOtModuleP")
	public void exportBatchOtModuleP(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add("工号");//工号
		aliasNameList.add("申请人");
		aliasNameList.add("开始日期[2014-07-01]");
		aliasNameList.add("开始时间[18:20]");
		aliasNameList.add("结束日期[2014-07-01]");
		aliasNameList.add("结束时间[22:00]");
		aliasNameList.add("加班类型[32:平日加班;33:周末加班;34:节假日加班]");
		aliasNameList.add("是否调休[0:否;1:是]");
		aliasNameList.add("是否特殊[0:否;1:是]");
		aliasNameList.add("社内/外[INSIDE:社内;OUTSIDE:社外]");
		aliasNameList.add("加班事由(可以为空)" );//备注

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "14000001");
		map.put("CELL1", "张XX");
		map.put("CELL2", "2014-07-01");
		map.put("CELL3", "18:00");
		map.put("CELL4", "2014-07-01");
	    map.put("CELL5", "22:30");
		map.put("CELL6", "32");
		map.put("CELL7", "0");
		map.put("CELL8", "0");
		map.put("CELL9", "INSIDE");
		map.put("CELL10", "社内-平日加班，不可调休。");
		list.add(map);
		
		LinkedHashMap map2 = new LinkedHashMap();
		map2.put("CELL0", "14000002");
		map2.put("CELL1", "	李XX");
		map2.put("CELL2", "2014-07-05");
		map2.put("CELL3", "22:30");
		map2.put("CELL4", "2014-07-06");
		map2.put("CELL5", "06:00");
		map2.put("CELL6", "33");
		map2.put("CELL7", "1");
		map2.put("CELL8", "0");
		map2.put("CELL9", "OUTSIDE");
		map2.put("CELL10", "社外-周末加班，可调休。");
		list.add(map2);
		
		LinkedHashMap map3 = new LinkedHashMap();
		map3.put("CELL0", "14000003");
		map3.put("CELL1", "	王XX");
		map3.put("CELL2", "2014-05-01");
		map3.put("CELL3", "22:30");
		map3.put("CELL4", "2014-05-02");
		map3.put("CELL5", "06:00");
		map3.put("CELL6", "34");
		map3.put("CELL7", "0");
		map3.put("CELL8", "0");
		map3.put("CELL9", "INSIDE");
		map3.put("CELL10", "社内-节假日加班，不可调休。");
		list.add(map3);
		
		
		LinkedHashMap map4 = new LinkedHashMap();
		map4.put("CELL0", "14000005");
		map4.put("CELL1", "	诸葛XX");
		map4.put("CELL2", "2014-05-01");
		map4.put("CELL3", "22:30");
		map4.put("CELL4", "2014-05-02");
		map4.put("CELL5", "06:00");
		map4.put("CELL6", "32");
		map4.put("CELL7", "0");
		map4.put("CELL8", "1");
		map4.put("CELL9", "INSIDE");
		map4.put("CELL10", "特殊加班(平时)。");
		list.add(map4);
		
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}

	/**
	 * 导出加班导入临时表里的所有P类型加班信息，进行修改，然后再导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportPOtImportExcel")
	public void exportPOtImportExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add("申请人");
		aliasNameList.add("开始时间[例:2014-07-01 08:30]");
		aliasNameList.add("结束时间[例:2014-07-01 22:00]");
		aliasNameList.add("加班类型[例:32:平日加班;33:周末加班;34:节假日加班]");
		aliasNameList.add("是否调休[例:0:否;1:是]");
		aliasNameList.add("社内/外[例:INSIDE:社内;OUTSIDE:社外]");
		aliasNameList.add("备注");
		aliasNameList.add("正/异常");
		aliasNameList.add("错误提示");

		List dataList = new ArrayList();
		List otImportList = this.infoApplySer.getOtImportInfoList(request);
		//int otImportListCnt = this.infoApplySer.getOtImportInfoListCnt(request,"NORMAL");
		for(int i=0;i<otImportList.size();i++){
			LinkedHashMap otApplyMap = new LinkedHashMap();
			otApplyMap = (LinkedHashMap)otImportList.get(i);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", otApplyMap.get("EMPID")!=null?otApplyMap.get("EMPID").toString():"");
			map.put("CELL1", otApplyMap.get("LOCAL_NAME")!=null?otApplyMap.get("LOCAL_NAME").toString():"");
			map.put("CELL2", otApplyMap.get("OT_FROM_TIME")!=null?otApplyMap.get("OT_FROM_TIME").toString():"");
			map.put("CELL3", otApplyMap.get("OT_TO_TIME")!=null?otApplyMap.get("OT_TO_TIME").toString():"");
			map.put("CELL4", otApplyMap.get("APPLY_TYPE_CODE")!=null?otApplyMap.get("APPLY_TYPE_CODE").toString():"");
			map.put("CELL5", otApplyMap.get("ADJUST_YN")!=null?otApplyMap.get("ADJUST_YN").toString():"");
			map.put("CELL6", otApplyMap.get("OT_PLACE_TYPE")!=null?otApplyMap.get("OT_PLACE_TYPE").toString():"");
			map.put("CELL7", otApplyMap.get("APPLY_OT_REMARK")!=null?otApplyMap.get("APPLY_OT_REMARK").toString():"");
			String checkFlag = "";
			if(otApplyMap.get("CHECK_FLAG")!=null && "1".equals(otApplyMap.get("CHECK_FLAG").toString())){
				checkFlag = "异常";
			}else{
				checkFlag = "正常";
			}
			map.put("CELL8", checkFlag);
			map.put("CELL9", otApplyMap.get("CHECK_ERROR")!=null?otApplyMap.get("CHECK_ERROR").toString():"无");
			
			dataList.add(map);
		}
		
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(dataList);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}
	
	/**
	 * 导出加班导入临时表里的所有类型加班信息，进行修改，然后再导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportLOtImportExcel")
	public void viewLOtImportListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add("申请人");
		aliasNameList.add("加班日期[例:2014-07-01]");
		aliasNameList.add("加班小时[例:2]");
		aliasNameList.add("加班分钟[例:30]");
		aliasNameList.add("加班类型[例:32:平日加班;33:周末加班;34:节假日加班]");
		aliasNameList.add("是否调休[例:0:否;1:是]");
		aliasNameList.add("社内/外[例:INSIDE:社内;OUTSIDE:社外]");
		aliasNameList.add("备注");
		aliasNameList.add("正/异常");
		aliasNameList.add("错误提示");
		
		List dataList = new ArrayList();
		List otImportList = this.infoApplySer.getOtImportInfoList(request);
		//int otImportListCnt = this.infoApplySer.getOtImportInfoListCnt(request,"NORMAL");
		for(int i=0;i<otImportList.size();i++){
			LinkedHashMap otApplyMap = new LinkedHashMap();
			otApplyMap = (LinkedHashMap)otImportList.get(i);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", otApplyMap.get("EMPID")!=null?otApplyMap.get("EMPID").toString():"");
			map.put("CELL1", otApplyMap.get("LOCAL_NAME")!=null?otApplyMap.get("LOCAL_NAME").toString():"");
			map.put("CELL2", otApplyMap.get("APPLY_OT_DATE")!=null?otApplyMap.get("APPLY_OT_DATE").toString():"");
			map.put("CELL3", otApplyMap.get("OT_APPLY_HOUR")!=null?otApplyMap.get("OT_APPLY_HOUR").toString():"");
			map.put("CELL4", otApplyMap.get("OT_APPLY_MINUTE")!=null?otApplyMap.get("OT_APPLY_MINUTE").toString():"");
			map.put("CELL5", otApplyMap.get("APPLY_TYPE_CODE")!=null?otApplyMap.get("APPLY_TYPE_CODE").toString():"");
			map.put("CELL6", otApplyMap.get("ADJUST_YN")!=null?otApplyMap.get("ADJUST_YN").toString():"");
			map.put("CELL7", otApplyMap.get("OT_PLACE_TYPE")!=null?otApplyMap.get("OT_PLACE_TYPE").toString():"");
			map.put("CELL8", otApplyMap.get("APPLY_OT_REMARK")!=null?otApplyMap.get("APPLY_OT_REMARK").toString():"");
			String checkFlag = "";
			if(otApplyMap.get("CHECK_FLAG")!=null && "1".equals(otApplyMap.get("CHECK_FLAG").toString())){
				checkFlag = "异常";
			}else{
				checkFlag = "正常";
			}
			map.put("CELL9", checkFlag);
			map.put("CELL10", otApplyMap.get("CHECK_ERROR")!=null?otApplyMap.get("CHECK_ERROR").toString():"无");
			
			dataList.add(map);
		}
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(dataList);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}
	
	/**
	 * 显示批量导入的P加班信息(view import P overtime apply info)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPOtImportList")
	public ModelAndView viewPOtImportList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List otImportList = this.infoApplySer.getOtImportInfoList(request);
		int otImportListCnt = this.infoApplySer.getOtImportInfoListCnt(request,"NORMAL");
		int errorCnt = this.infoApplySer.getOtImportInfoListCnt(request , "ERROR");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", otImportListCnt);
		modelMap.put("otImportList", otImportList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, otImportListCnt);
		return new ModelAndView("/ess/infoApply/viewPOtImportList",modelMap);
	}
	
	/**
	 * 查看导入临时表里的完整加班事由信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullApplyInfoImport")
	public ModelAndView viewFullApplyInfoImport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List otApplyList = infoApplySer.getOtImportInfoList(request);
		LinkedHashMap applyMap=new LinkedHashMap();
		if(otApplyList.size()>0){
			  applyMap = (LinkedHashMap)otApplyList.get(0);
		}
		
		modelMap.put("APPLY_NO", applyMap.get("APPLY_NO")!=""?applyMap.get("APPLY_NO").toString():"");
		modelMap.put("APPLY_REMARK", applyMap.get("APPLY_OT_REMARK")!=null?applyMap.get("APPLY_OT_REMARK").toString():"");
		
		response.setCharacterEncoding("UTF-8");
		return new ModelAndView("/ess/infoApply/viewFullApplyInfoImport", modelMap);
	}
	
	/**
	 * 查看导入临时表里的完整加班事的check error信息(view full check error 信息)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullImportCheckError")
	public ModelAndView viewFullImportCheckError(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List otApplyList = infoApplySer.getOtImportInfoList(request);
		LinkedHashMap applyMap = (LinkedHashMap)otApplyList.get(0);
		modelMap.put("APPLY_NO", applyMap.get("APPLY_NO").toString());
		modelMap.put("CHECK_ERROR", applyMap.get("CHECK_ERROR").toString());
		
		response.setCharacterEncoding("UTF-8");
		return new ModelAndView("/ess/infoApply/viewFullImportCheckError", modelMap);
	}
	
	/**
	 * 批量导入P加班申请 (import batch ot apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/savePOtApplyImport")
	@ResponseBody
	public Map savePOtApplyImport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.addImportOtApply2(request);
			if (result == 1) {
				map.put("navTabId", "ess02467");
				map.put("message", "保存批量导入加班申请成功!");//保存批量导入加班申请成功!
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
				//modelMap.put("forwardUrl","/ess/infoApply/viewPOtImportList");
			}else if(result == 444){
			
				map.put("message", "批量加班申请失败，Package报错,请联系系统管理员！!");//导入的加班申请有错误,请修改!
				map.put("statusCode", "200");
				//map.put("forwardUrl", "/ess/infoApply/viewPOtImportList?pageNum=1");
			 
 			
			}else{
				map.put("message", "导入的加班申请有错误,请修改!");//导入的加班申请有错误,请修改!
				map.put("statusCode", "200");
				 
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "批量保存加班申请失败,请重试!");//批量导入加班申请,请重试!
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 批量导入L加班申请 (import batch ot apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveLOtApplyImport")
	@ResponseBody
	public Map saveLOtApplyImport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.addImportOtApply2(request);
			if (result == 1) {
				map.put("navTabId", "ess02466");
				map.put("message", "保存批量导入加班申请成功!");//保存批量导入加班申请成功!
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
				//modelMap.put("forwardUrl","/ess/infoApply/viewLOtImportList");
			}else if(result == 444){
			
				map.put("message", "导入的加班申请有错误,请修改!");//导入的加班申请有错误,请修改!
				map.put("statusCode", "200");
			 
			}else{
				map.put("message", "导入的加班申请有错误,请修改!");//导入的加班申请有错误,请修改!
				map.put("statusCode", "200");
				 
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "批量保存加班申请失败,请重试!");//批量导入加班申请,请重试!
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 取消所有导入的P加班申请( delete overtime apply import)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePOtApplyImport")
	@ResponseBody
	public Map deletePOtApplyImport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String msg= ""+this.infoApplySer.cancelOvertimeApplyImport(request);
		if("1".equals(msg)){
			map.put("statusCode", "200");
			map.put("message", "取消成功！");//取消成功
			map.put("navTabId", "ess0213");
			map.put("callbackType", "closeCurrent");
			//modelMap.put("forwardUrl","/ess/infoApply/viewPOtImportList");
		}else{
			map.put("statusCode", "300");
			map.put("message", "取消失败！");//取消失败
		}
		return map;
	}
	
	/**
	 * 取消所有导入的L加班申请( delete overtime apply import)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteLOtApplyImport")
	@ResponseBody
	public Map deleteLOtApplyImport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();

		String msg= ""+this.infoApplySer.cancelOvertimeApplyImport(request);
		if("1".equals(msg)){
			map.put("statusCode", "200");
			map.put("message", "取消成功！");//保存成功
			map.put("navTabId", "ess0231");
			map.put("callbackType", "closeCurrent");
			//modelMap.put("forwardUrl","/ess/infoApply/viewLOtImportList");
		}else{
			map.put("statusCode", "300");
			map.put("message", "取消失败！");//保存失败
		}
		return map;
	}

	/**
	 * 取消所有导入的加班申请( delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteOtApplyImport2")
	@ResponseBody
	public String cancelOvertimeApplyImport(HttpServletRequest request) throws Exception {
		String result = "";
		int bol = infoApplySer.cancelOvertimeApplyImport(request);
		if (bol==1) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
	
	/**
	 * 单条删除导入的加班申请( delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delOvertimeApplyImport")
	@ResponseBody
	public String delOvertimeApplyImport(HttpServletRequest request) throws Exception {
		String result = "";
		boolean bol = infoApplySer.delOvertimeApplyImport(request);
		if (bol) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
	
	/**
	 * 加班对象Excel导入模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchOtModuleL")
	public void exportBatchOtModuleL(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add("工号");//工号
		aliasNameList.add("申请人");
		aliasNameList.add("加班日期[2014-07-01]");
		aliasNameList.add("加班小时[2]");
		aliasNameList.add("加班分钟[30]");
		aliasNameList.add("加班类型[32:平日加班;33:周末加班;34:节假日加班]");
		aliasNameList.add("是否调休[0:否;1:是]");
		aliasNameList.add("社内/外[INSIDE:社内;OUTSIDE:社外]");
		aliasNameList.add("备注(可以为空)");//备注

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "14000001");
		map.put("CELL1", "张XX");
		map.put("CELL2", "2014-07-01");
		map.put("CELL3", "1");
		map.put("CELL4", "30");
		map.put("CELL5", "32");
		map.put("CELL6", "0");
		map.put("CELL7", "INSIDE");
		map.put("CELL8", "社内-平日加班，不可调休。");
		list.add(map);
		
		LinkedHashMap map2 = new LinkedHashMap();
		map2.put("CELL0", "14000002");
		map2.put("CELL1", "李XX");
		map2.put("CELL2", "2014-07-05");
		map2.put("CELL3", "3");
		map2.put("CELL4", "0");
		map2.put("CELL5", "33");
		map2.put("CELL6", "1");
		map2.put("CELL7", "OUTSIDE");
		map2.put("CELL8", "社外-周末加班，可调休。");
		list.add(map2);
		
		LinkedHashMap map3 = new LinkedHashMap();
		map3.put("CELL0", "14000003");
		map3.put("CELL1", "王XX");
		map3.put("CELL2", "2014-05-01");
		map3.put("CELL3", "3");
		map3.put("CELL4", "0");
		map3.put("CELL5", "34");
		map3.put("CELL6", "0");
		map3.put("CELL7", "INSIDE");
		map3.put("CELL8", "社内-节假日加班，不可调休。");
		list.add(map3);
		
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}
	
	/**
	 * 显示批量导入的L加班信息(view import L overtime apply info)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewLOtImportList")
	public ModelAndView viewLOtImportList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		//modelMap.put("otImportList", this.infoApplySer.getOtImportInfoList(request))
		List otImportList = this.infoApplySer.getOtImportInfoList(request);;
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer.getOtApplyInfoListCnt(request));
		
		int otImportListCnt = this.infoApplySer.getOtImportInfoListCnt(request,"NORMAL");
		int errorCnt = this.infoApplySer.getOtImportInfoListCnt(request , "ERROR");

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", otImportListCnt);
		modelMap.put("otImportList", otImportList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, otImportListCnt);
		return new ModelAndView("/ess/infoApply/viewLOtImportList",modelMap);
	}
	
	/**
	 * 临时表取消操作
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTempApplyOtInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addTempApplyOtInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String stype = request.getParameter("stype");
		if("2".equals(stype)){
			//删除临时表
			int result = 0;
			try {
				result = this.infoApplyLeaveSer.delTempLeaveApply(request);
				if (result == 1) {
					map.put("message", "删除成功");//
					map.put("statusCode", "200");
					//map.put("callbackType", "closeCurrent");
					map.put("navTabId", "ess0246");
				}
			} catch (CommonException e) {
				map.put("message", e.getMessage());
				map.put("statusCode", "300");
			} catch (Exception e) {
				map.put("message", "删除失败");//
				map.put("statusCode", "300");
			}
		}else{
			int result = 0;
			try {
				result = infoApplyLeaveSer.addBatchApplyLeave(request);
				if (result == 1) {
					map.put("message", "导入完成");//
					map.put("statusCode", "200");
					//map.put("callbackType", "closeCurrent");
					map.put("navTabId", "ess0246");
				}
			} catch (CommonException e) {
				map.put("message", e.getMessage());
				map.put("statusCode", "300");
			} catch (Exception e) {
				map.put("message", "导入失败");//
				map.put("statusCode", "300");
			}
		}
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 显示批量加班(view batch overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtBatchApplyPersonList")
	public ModelAndView viewOtBatchApplyPersonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List dynamicGroupList = this.dynamicGroupSer.getDynamicGroup1List(request) ;
		List otDeductTimeList = this.infoApplySer.getOtDeductTimeList(request) ;
		List list = infoApplySer.getPersonList(request);
		
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("dynamicGroupList", dynamicGroupList);
		modelMap.put("otDeductTimeList", otDeductTimeList);
		modelMap.put("personList", list);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer.getPersonListCnt(request));
 		
		return new ModelAndView("/ess/infoApply/viewOtBatchApplyPersonList",
				modelMap);
	}
	/**
	 * 显示批量加班新(view batch overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtBatchApplyPersonListNewList")
	public ModelAndView viewOtBatchApplyPersonListNewList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List dynamicGroupList = this.dynamicGroupSer.getDynamicGroup1List(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","31");
		paramMap.put("language",admin.getLanguage());
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		
		
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		
		
		
		
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("dynamicGroupList", dynamicGroupList);
		 List list = infoApplySer.getPersonList(request);
		modelMap.put("personList", list);
		modelMap.put("codeList", codeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer
				.getPersonListCnt(request));
 		
		return new ModelAndView("/ess/infoApply/viewOtBatchApplyPersonListNewList",
				modelMap);
	}
	
	
	
	
	/**
	 * 显示休假申请(view leave apply information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLeaveApplyInfo")
	public ModelAndView viewLeaveApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		//modelMap.put("personalInfo", this.personInfoSer.getHrPersonInfo(request));
		modelMap.put("restAnnualLeave", this.infoApplySer.retrieveVacationEmpREST(request));
		modelMap.put("restAnnualLeaveQN", this.infoApplySer.retrieveVacationEmpRESTQN(request));
		//2013-06-20开始使用的计算剩余调休时数
		modelMap.put("adjustRest", this.infoApplySer.getAdjustRestNew(request));
		//2013-06-20之前使用的计算剩余调休时数(保留中)
		//modelMap.put("adjustRest", this.infoApplySer.getSurplusAdjustRest(request));
		//个人基本信息中是否有配偶出生年月日
		modelMap.put("SPOUSE_BIRTH", this.infoApplySer.getspouseBirth(request));
		return new ModelAndView("/ess/infoApply/viewLeaveApplyInfo", modelMap);
	}

	/**
	 * 显示批量休假申请(view batch leave apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLeaveBatchApplyPersonList")
	public ModelAndView viewLeaveBatchApplyPersonList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		
		List dynamicGroupList = this.dynamicGroupSer.getDynamicGroup1List(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("dynamicGroupList", dynamicGroupList);
		modelMap.put("personList", infoApplySer.getPersonList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer
				.getPersonListCnt(request));

		return new ModelAndView("/ess/infoApply/viewLeaveBatchApplyPersonList",
				modelMap);
	}

	/**
	 * 显示出差申请(view evection apply information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvectionApplyInfo")
	public ModelAndView viewEvectionApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		//modelMap.put("personalInfo", this.personInfoSer.getHrPersonInfo(request));
		return new ModelAndView("/ess/infoApply/viewEvectionApplyInfo",
				modelMap);
	}
	
	/**
	 * 显示批量出差申请(view batch evection apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvectionBatchApplyPersonList")
	public ModelAndView viewEvectionBatchApplyPersonList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personList", infoApplySer.getPersonList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer
				.getPersonListCnt(request));

		return new ModelAndView("/ess/infoApply/viewEvectionBatchApplyPersonList",
				modelMap);
	}

	/**
	 * 显示外出申请(view egression apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEgressionApplyInfo")
	public ModelAndView viewEgressionApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		//modelMap.put("personalInfo", this.personInfoSer.getHrPersonInfo(request));
		return new ModelAndView("/ess/infoApply/viewEgressionApplyInfo",
				modelMap);
	}
	
	/**
	 * 显示可以批量外出申请的人员(view batch egression apply person list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEgressionBatchApplyPersonList")
	public ModelAndView viewEgressionBatchApplyPersonList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personList", infoApplySer.getPersonList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplySer
				.getPersonListCnt(request));

		return new ModelAndView("/ess/infoApply/viewEgressionBatchApplyPersonList",
				modelMap);
	}

	/**
	 * 个人信息申请(add personal information apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPersonalInfoApply")
	@ResponseBody
	public Map<String, Object> addPersonalInfoApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result;
		try {
			result = infoApplySer.addPersonalInfoApply(request);
			if (result) {
				map.put("navTabId", "ess0301");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addPersonalInfo_success",request));//个人信息申请成功！
				map.put("statusCode", "200");
				// map.put("callbackType", "closeCurrent");
			} else {
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addPersonalInfo_fail",request));//个人信息申请成功,请重试！
				map.put("statusCode", "300");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	

	/**
	 * 批量加班申请 (add batch overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBatchOvertimeApply")
	@ResponseBody
	public Map addBatchOvertimeApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.addBatchOvertimeApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0207");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApplyInBatch_success",request));//"批量加班申请成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApplyInBatch_fail",request));//"批量加班申请失败,请重试!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 休假申请 (add leave apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addLeaveApply")
	@ResponseBody
	public Map addLeaveApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result;
		try {
			result = infoApplySer.addLeaveApply(request);
			if (result) {
				//map.put("navTabId", "ess0202");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addLeaveApply_success",request));//"休假申请成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addLeaveApply_fail",request));//"休假申请失败,请重试!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 批量休假申请 (add batch leave apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBatchLeaveApply")
	@ResponseBody
	public Map addBatchLeaveApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.addBatchLeaveApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0208");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addLeaveApplyInBatch_success",request));//"批量休假申请成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addLeaveApplyInBatch_fail",request));//"批量休假申请失败,请重试!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 出差申请 (add Evection Apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvectionApply")
	@ResponseBody
	public Map addEvectionApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result;
		try {
			result = infoApplySer.addEvectionApply(request);
			if (result) {
				//map.put("navTabId", "ess0211");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addEvectionApply_success",request));//"出差申请成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addEvectionApply_fail",request));//"出差申请失败,请重试!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 批量出差申请 (add Evection Apply in batch)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBatchEvectionApply")
	@ResponseBody
	public Map addBatchEvectionApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.addBatchEvectionApply(request);
			if (result==1) {
				map.put("navTabId", "ess0217");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addEvectionApplyInBatch_success",request));//"批量出差申请成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addEvectionApplyInBatch_fail",request));//"批量出差申请失败,请重试!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 外出申请 (add egression Apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEgressionApply")
	@ResponseBody
	public Map addEgressionApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result;
		try {
			result = infoApplySer.addEgressionApply(request);
			if (result) {
				map.put("navTabId", "ess0216");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addEgressionApply_success",request));//"外出申请成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addEgressionApply_fail",request));//"外出申请失败,请重试!"
			map.put("statusCode", "300");
		}
		return map;
	}	
	
	/**
	 * 批量外出申请 (add egression Apply in batch)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBatchEgressionApply")
	@ResponseBody
	public Map addBatchEgressionApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.addBatchEgressionApply(request);
			if (result==1) {
				map.put("navTabId", "ess0218");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addEgressionApplyInBatch_success",request));//"批量外出申请成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addEgressionApplyInBatch_fail",request));//"批量外出申请失败,请重试!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 得到班次 (get person time shift)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchEmpShift")
	public ModelAndView searchEmpShift(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("empShiftList", this.infoApplySer.getEmpShift(request));

		modelMap.put("EMPID", request.getParameter("EMPID"));
		return new ModelAndView(modelMap);
	}

	/**
	 * 查看决裁者信息(view affirmor information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmorByPersonIdAndAppCode")
	public ModelAndView viewAffirmorByPersonIdAndAppCode(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		 //通过参数来查询决裁者
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String applyTypeNo = (String) paramMap.get("APPLY_TYPE_NO");
		String personId = (String)paramMap.get("PERSON_ID");
		String applyLength = (String)paramMap.get("otLength");
		String applyTypeCode= (String) paramMap.get("APPLY_TYPE_CODE");
		List affirmerList =this.infoApplySer.getAffirmorListByString(applyTypeNo, 
				 personId,this.infoApplySer.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),applyTypeCode), applyLength, admin.getLanguage());
		//List affirmorList = this.infoApplySer.getAffirmorList(request);
		modelMap.put("affirmorList", affirmerList);
		return new ModelAndView(
				"/ess/infoApply/viewAffirmorByPersonIdAndAppCode", modelMap);
	}
	/**
	 * 显示喜/丧假申请(view leave apply information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLikeLeaveApplyInfo")
	public ModelAndView viewLikeLeaveApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		//modelMap.put("personalInfo", this.personInfoSer.getHrPersonInfo(request));
		modelMap.put("restAnnualLeave", this.infoApplySer.retrieveVacationEmpREST(request));
		modelMap.put("restAnnualLeaveQN", this.infoApplySer.retrieveVacationEmpRESTQN(request));
		//2013-06-20开始使用的计算剩余调休时数
		modelMap.put("adjustRest", this.infoApplySer.getAdjustRestNew(request));
		//2013-06-20之前使用的计算剩余调休时数(保留中)
		//modelMap.put("adjustRest", this.infoApplySer.getSurplusAdjustRest(request));
		
		return new ModelAndView("/ess/infoApply/viewLikeLeaveApplyInfo", modelMap);
	}
	/**
	 * 显示喜/丧假申请(view leave apply information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewWorkingDaysApplyInfo")
	public ModelAndView viewWorkingDaysApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		//modelMap.put("personalInfo", this.personInfoSer.getHrPersonInfo(request));
		modelMap.put("restAnnualLeave", this.infoApplySer.retrieveVacationEmpREST(request));
		modelMap.put("restAnnualLeaveQN", this.infoApplySer.retrieveVacationEmpRESTQN(request));
		//2013-06-20开始使用的计算剩余调休时数
		modelMap.put("adjustRest", this.infoApplySer.getAdjustRestNew(request));
		//2013-06-20之前使用的计算剩余调休时数(保留中)
		//modelMap.put("adjustRest", this.infoApplySer.getSurplusAdjustRest(request));
		
		return new ModelAndView("/ess/infoApply/viewWorkingDaysApplyInfo", modelMap);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/proveFiledialog")
	public ModelAndView proveFiledialog(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
	modelMap.put("FILE_NAME", request.getParameter("FILE_NAME"));
	modelMap.put("FILE_URL", request.getParameter("FILE_URL"));
		return new ModelAndView("/ess/infoApply/proveFiledialog", modelMap);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/proveFileUploading")
	public ModelAndView proveFileUploading(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		
		
		
		

		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		if (!(request instanceof MultipartHttpServletRequest) && multipartResolver.isMultipart(request)) {
			try {
				multipartResolver.setMaxUploadSize(20971520);
				multipartRequest = multipartResolver.resolveMultipart(request);
			} catch(MaxUploadSizeExceededException e) {
				//return null;
			}
		} else if (request instanceof MultipartHttpServletRequest){
			multipartRequest = (MultipartHttpServletRequest)request;
		} else {
			//return null;
		}
		String PARAMDATANO=multipartRequest.getParameter("UPLOADING_NAME").toString();
		
		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
		//SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");     
		 /**构建图片保存的目录**/    
		 String logoPathDir = "/resources/temp/provefile/";// +PARAMDATANO;dateformat.format(new Date());     
		 /**得到图片保存目录的真实路径**/    
		 String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);     
		/**根据真实路径创建目录**/    
		 File logoSaveFile = new File(logoRealPathDir);     
		 if(!logoSaveFile.exists())     
		logoSaveFile.mkdirs();           
		/**页面控件的文件流**/    
		MultipartFile multipartFile = multipartRequest.getFile("proveFileName");      
		/**获取文件的后缀**/    
		String suffix = multipartFile.getOriginalFilename().substring  
		(multipartFile.getOriginalFilename().lastIndexOf("."));     
		 /**使用UUID生成文件名称**/    
		// String logImageName = request.getParameter("PARAM_DATA_NO").toString()+ suffix;
		//构建文件名称     
		String logImageNameA = multipartFile.getOriginalFilename();  
		String logImageName = PARAMDATANO+suffix;
		/**拼成完整的文件保存路径加文件**/    
		String fileName = logoRealPathDir + File.separator   + logImageName;                
		File file = new File(fileName);           
		try {     
		  multipartFile.transferTo(file);  
		  modelMap.put("sign",2);
		 } catch (IllegalStateException e) {     
		 e.printStackTrace(); 
		 modelMap.put("sign", -1);
		} catch (IOException e) {            
		 e.printStackTrace();  
		 modelMap.put("sign", -1);
		 }   
		
		return new ModelAndView("/ess/infoApply/proveFileUploading",modelMap);
		
	}
	
	@RequestMapping("downloadFile")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		
		String fileName=request.getRealPath("")+request.getParameter("fileName");
		String fileNameA = URLEncoder.encode(request.getParameter("file"), "UTF-8");
		response.setHeader("Content-Disposition", "attachment;fileName="+fileNameA);
		try {
			File file=new File(fileName);
			System.out.println(file.getAbsolutePath());
			InputStream inputStream=new FileInputStream(file);
			OutputStream os=response.getOutputStream();
			byte[] b=new byte[102400];
			int length;
			while((length=inputStream.read(b))>0){
				os.write(b,0,length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewVacationStandard")
	public ModelAndView viewVacationStandard(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("VACATIONSTANDAR", infoApplySer.getviewVacationStandard(request));
		//modelMap.put("personalInfo", this.personInfoSer.getHrPersonInfo(request));
		return new ModelAndView("/ess/infoApply/viewVacationStandard",modelMap);
	}
	
	/**
	 * 显示出差申请(view evection apply information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvectionApplyInfoNewOne")
	public ModelAndView viewEvectionApplyInfoNewOne(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		//modelMap.put("personalInfo", this.personInfoSer.getHrPersonInfo(request));
		return new ModelAndView("/ess/infoApply/viewEvectionApplyInfoNewOne",
				modelMap);
	}
	
	/**
	 * 批量删除年假调整申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delCwaAbnormalApplyInfo")
	@ResponseBody
	public Map<String, Object> delCwaAbnormalApplyInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = annualadjustmentInfoSer.delCwaAbnormalApplyInfo(request);
			if (result == 1) {
				map.put("navTabId", "ess0307");
				map.put("message", "考勤异常删除成功！"); 
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "考勤异常删除失败，请重新操作！"); 
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	

	/**
	 * 考勤 异常查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCwaAbnormalApplyInfo")
	public ModelAndView viewCwaAbnormalApplyInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String specialParam = admin.getSpecialParam();
		
		modelMap.put("specialParam", specialParam);
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		if(paramMap.get("deptID") != null && !"".equals(paramMap.get("deptID")) ){
			modelMap.put("deptID", paramMap.get("deptID").toString());
		}
		
		// 本月的第一天 
		Calendar calendar  =   new  GregorianCalendar();
		calendar.set( Calendar.DATE,  1 );
		SimpleDateFormat simpleFormate  =   new  SimpleDateFormat( "yyyy-MM-dd" );
		   
		// 本月的最后一天 
		Calendar calendar2  =   new  GregorianCalendar();
		calendar2.set( Calendar.DATE,  1 );
		calendar2.roll(Calendar.DATE,  - 1 );
		SimpleDateFormat simpleFormate2  =   new  SimpleDateFormat( "yyyy-MM-dd" );
		
		if(paramMap.get("sDate") != null && paramMap.get("sDate") != ""){
			modelMap.put("sDate", paramMap.get("sDate").toString() );
			modelMap.put("eDate", paramMap.get("eDate").toString() );
		}else{
			modelMap.put("sDate", simpleFormate.format(calendar.getTime()));
			modelMap.put("eDate", simpleFormate2.format(calendar2.getTime()));
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("admin", admin);
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		
		List list = annualadjustmentInfoSer.getCwaAbnormalApplyInfoList(request,"N"); 
		modelMap.put("CwaAbnormalApplyInfoList",list);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,annualadjustmentInfoSer.getCwaAbnormalApplyInfoListCnt(request,"N"));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "218302"));
		
		return new ModelAndView("/ess/infoApply/viewCwaAbnormalApplyInfo",
				modelMap);
	}
	
	
	/**
	 * 显示考勤异常申请页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewShowCwaAbnormalApply")
	public ModelAndView viewShowCwaAbnormalApplyList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String firstFlag = request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if (firstFlag == null  || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_sDate")==""||request.getParameter("seach_sDate")==null )&& (request.getParameter("seach_eDate")==""||request.getParameter("seach_eDate")==null)){
				//获取当前月第一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("sDate",first);
				//获取当前月最后一天： 
				c = Calendar.getInstance(); 
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH)); 
				String eDate = format.format(c.getTime());
				modelMap.put("eDate",eDate);
			}
		}
		if(request.getParameter("PK_NO")!="" && request.getParameter("PK_NO")!=null){
			modelMap.put("sDate","");
			modelMap.put("eDate","");
		}
		List getArDetailList = this.arDetailSer.getAttendanceExceptionList(request,modelMap);
		String supervisorId = admin.getAdminID();
		String specialParam = admin.getSpecialParam();
		modelMap.put("specialParam", specialParam);
		modelMap.put("supervisorId", supervisorId);
		modelMap.put("arDetailList", getArDetailList);
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, getArDetailList ==null ? "0" : getArDetailList.size());
		modelMap.put("getArDetailListCnt", getArDetailList ==null ? "0" : getArDetailList.size());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218302")) ;
		return new ModelAndView("/ess/infoApply/viewShowCwaAbnormalApply",
				modelMap);
	}
	
	
	
	
	/**
	 * 考勤异常申请(update ArDetail Info)
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCwaAbnormalApply", method = RequestMethod.POST)
	@ResponseBody
	public Map addCwaAbnormalApply(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = annualadjustmentInfoSer.addBatchCwaAbnormalApply(request);
			if (result==1) {
				map.put("message", TipMessage.getTipMessage("alert.message.abnormalAttendanceApplySuccess.b", request));//"考勤异常申请成功!"
				map.put("statusCode", "200");
				map.put("navTabId", "ess3205");
			}else if(result==2){
				map.put("message", TipMessage.getTipMessage("alert.message.abnormalAttendanceApplyFail.b", request));//"考勤申请失败!"
				map.put("statusCode", "300");
			}
		}catch (Exception e) {
			map.put("message", e.getMessage());//考勤异常申请失败,请重试!"
			map.put("statusCode", "300");
		}
		return map;

	}
	
	/**
	 * 显示考勤异常申请页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAbnormalApplyByAnyApprover")
	public ModelAndView viewAbnormalApplyByAnyApprover(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String firstFlag = request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if (firstFlag == null  || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_sDate")==""||request.getParameter("seach_sDate")==null )&& (request.getParameter("seach_eDate")==""||request.getParameter("seach_eDate")==null)){
				//获取当前月第一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("sDate",first);
				//获取当前月最后一天： 
				c = Calendar.getInstance(); 
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH)); 
				String eDate = format.format(c.getTime());
				modelMap.put("eDate",eDate);
			}
		}
		if(request.getParameter("PK_NO")!="" && request.getParameter("PK_NO")!=null){
			modelMap.put("sDate","");
			modelMap.put("eDate","");
		}
		List getArDetailList = this.arDetailSer.getAttendanceExceptionList(request,modelMap);
		String supervisorId = admin.getAdminID();
		String specialParam = admin.getSpecialParam();
		modelMap.put("specialParam", specialParam);
		modelMap.put("supervisorId", supervisorId);
		modelMap.put("arDetailList", getArDetailList);
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, getArDetailList ==null ? "0" : getArDetailList.size());
		modelMap.put("getArDetailListCnt", getArDetailList ==null ? "0" : getArDetailList.size());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218302")) ;
		return new ModelAndView("/ess/infoApply/viewAbnormalApplyByAnyApprover",
				modelMap);
	}
	
	/**
	 * 考勤异常申请(update ArDetail Info)
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAbnormalApplyByAnyApprover", method = RequestMethod.POST)
	@ResponseBody
	public Map addAbnormalApplyByAnyApprover(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = annualadjustmentInfoSer.addAbnormalApplyByAnyApprover(request);
			if (result==1) {
				map.put("message", TipMessage.getTipMessage("alert.message.abnormalAttendanceApplySuccess.b", request));//"考勤异常申请成功!"
				map.put("statusCode", "200");
				map.put("navTabId", "ess3214");
			}else if(result==2){
				map.put("message", TipMessage.getTipMessage("alert.message.abnormalAttendanceApplyFail.b", request));//"考勤申请失败!"
				map.put("statusCode", "300");
			}
		}catch (Exception e) {
			map.put("message", e.getMessage());//考勤异常申请失败,请重试!"
			map.put("statusCode", "300");
		}
		return map;

	}
	
	/**
	 *  kaoqinyichang   
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCwaAbnormalAffirmList")
	public ModelAndView viewCwaAbnormalAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("leaveApplyAffirmList", this.annualadjustmentInfoSer.getviewCwaAbnormalAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.annualadjustmentInfoSer.getviewCwaAbnormalAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		if("".equals(StringUtil.checkNull(request.getParameter("seach_AFFIRM_FLAG")))){
			modelMap.put("AFFIRM_FLAG", "10");
		}
		return new ModelAndView("/ess/infoApply/viewCwaAbnormalAffirmList",
				modelMap);	
	}
	
	
	
	/**
	 * 查看完整kaoqinyichang信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCwaApplyAffirmorList")
	public ModelAndView viewCwaApplyAffirmorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", admin.getPersonId());
		List applyorList = annualadjustmentInfoSer.getCwaAbnormalAffirmByApplyNOList(request);
		Map applyorInfo = (Map)applyorList.get(0);
		modelMap.put("applyorInfo", applyorInfo);
		
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());

		if( applyorInfo != null  && "Y".equals(StringUtil.checkNull(applyorInfo.get("BATCH_YN")))){
			modelMap.put("arCwaBatchAffirmList", infoApplySer.getArCwaBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getArCwaBatchAffirmInfoCnt(request));
		}
		return new ModelAndView("/ess/infoApply/viewCwaApplyAffirmorList", modelMap);
	}
	
	
	/**
	 * check列表(apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCwaCheckList")
	public ModelAndView viewLeaveCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List cwachecklist = this.annualadjustmentInfoSer.getCwaCheckList(request);
		modelMap.put("leaveCheckList", cwachecklist);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, annualadjustmentInfoSer.getCwaCheckCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2355")) ;
		return new ModelAndView("/ess/infoApply/viewCwaCheckList",modelMap);
	}
	
	
	/**
	 * check
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkCwaApplyCheckInfo")
	public ModelAndView checkApplyCheckInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List applyorList =  annualadjustmentInfoSer.getCwaAbnormalAffirmByApplyNOList(request);
//		is_check 用来判断查看或提交权限
		Map temp = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String is_check = (null == temp.get("IS_CHECK") ? "0" : temp.get("IS_CHECK").toString());
		
		if(applyorList != null){
			modelMap.put("applyorInfo", applyorList.get(0));
		}
		List affirmorList = annualadjustmentInfoSer.getAffirmorByApplyNoList(request);
		List checkorList = annualadjustmentInfoSer.getCheckorByApplyNoList(request);
		
		String essCheckNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<checkorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)checkorList.get(i);
			if("0".equals(applyorMap.get("CHECK_FLAG").toString())){
				if(admin.getPersonId().equals(applyorMap.get("CHECKOR_ID").toString())){
					essCheckNo = applyorMap.get("ESS_CHECK_NO").toString();
				}
				break;
			}
		}
		modelMap.put("is_check", is_check);
		modelMap.put("essCheckNo", essCheckNo);
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		return new ModelAndView("/ess/infoApply/checkCwaApplyCheckInfo", modelMap);
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
	@RequestMapping(value = "/addCwaAffirmInfo")
	@ResponseBody
	public Map addCwaAffirmInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = affirmApplySers.approveApplyCwa(request);
			if (result == 1) {
				map.put("navTabId", "ess0305");
				map.put("message", "考勤决裁成功!"); 
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "考勤决裁失败！请重新决裁"); 
			map.put("statusCode", "300");
		}
		return map;
	}
	

	/**
	 * 加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getShiftEndTime")
	@ResponseBody
	public void getShiftEndTime(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");

		String returnString = this.infoApplySer.getShiftEndTime(request);

		PrintWriter out = response.getWriter();
        
	    out.println(JsonUtil.writeInternal(returnString));
	        
		out.flush();
		out.close();
	}
	

	/**
	 * 加班查看完整决裁详情信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullApplyOtBatchAffirmInfo1")
	public ModelAndView viewFullOtApplyBatchAffirmInfo1List(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		
		modelMap.put("CPNY_ID", admin.getCpnyId());

		modelMap.put("OtBatchAffirmList", infoApplyLeaveSer.getOtBatchAffirmInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyLeaveSer.getOtBatchAffirmInfoCnt(request));
		modelMap.put("APPLY_NO",  request.getParameter("APPLY_NO"));
		modelMap.put("AFFIRM_FLAG",  request.getParameter("AFFIRM_FLAG"));
		
		return new ModelAndView("/ess/infoApply/viewFullApplyOtBatchAffirmInfo1", modelMap);
	}
	
	/**
	 * 批量加班审批者调整
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyAffirmorForBatchOt")
	@ResponseBody
	public Map modifyAffirmorForBatchOt(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.modifyAffirmorForBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess02467");
				map.put("message", "操作成功");//"保存加班申请成功!"
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_fail",request));//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 导出页面数据
	 */
	@RequestMapping(value = "/viewImportCwaAbnormalListExcel")
	public void viewImportCwaAbnormalListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		aliasNameList.add("社号");
		aliasNameList.add("姓名");
		aliasNameList.add("部门");
		aliasNameList.add("考勤日期");
		aliasNameList.add("异常开始时间");
		aliasNameList.add("异常结束时间");
		aliasNameList.add("异常类型");
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("deptID") != null && paramMap.get("deptID") != "" ){
			modelMap.put("deptID", paramMap.get("deptID").toString());
		}
		modelMap.put("itemNo", paramMap.get("itemNo"));
		Date d=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		modelMap.put("sDate", df.format(new Date(d.getTime() - 24*60*60*1000)));
	    modelMap.put("eDate", df.format(new Date(d.getTime() - 24*60*60*1000))); 
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		modelMap.put("BIAO", "A");
		List getArDetailList = this.arDetailSer.getArDetailListExcel(request,modelMap);
		String[] columns = { "EMPID", "LOCAL_NAME", "DEPTNAME","AR_DATE_STR", "FROMTIME_BIAO","TOTIME_BIAO", "ITEM_NAME"};
        String name = "viewCwaAbnormalListExcel";
        this.excelUtilSer.exportExcelByNamePwd(
	   request, response, modelMap, getArDetailList, aliasNameList, columns, name, paramMap);
	}
	

	/**
	 * 休假旷工申请导入模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchAbsenteeismModule")
	public void exportBatchAbsenteeismModule(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号");
		aliasNameList.add("申请人");
		aliasNameList.add("考勤日期");
		aliasNameList.add("备注");
		aliasNameList.add(" ");

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "12000003");
		map.put("CELL1", "张XX");
		map.put("CELL2", "2014-07-10");
		map.put("CELL3", "未上班");
		map.put("CELL4", " ");
		list.add(map);

		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList  ,"ess_absenteeism");
	}
	
	/**
	 * 休假批量申请数据导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchAbsenteeismData")
	public void exportBatchAbsenteeismData(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号");
		aliasNameList.add("申请人");
		aliasNameList.add("考勤日期");
		aliasNameList.add("备注");
		aliasNameList.add(" ");

		List list = new ArrayList();

		List paEssLeaveTempList = this.infoApplyLeaveSer.getEssLeaveTempList(request);
		for(int i=0;i<paEssLeaveTempList.size();i++){
			LinkedHashMap dataMap = (LinkedHashMap)paEssLeaveTempList.get(i);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", dataMap.get("EMPID"));
			map.put("CELL1", dataMap.get("LOCAL_NAME"));
			map.put("CELL2", dataMap.get("AR_DATE_STR"));
			map.put("CELL3", dataMap.get("REMARK"));
			map.put("CELL4", dataMap.get(" "));
			list.add(map);
		}
		
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"ess_absenteeism");
	}
	

	/**
	 * 休假导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelEssAbsenteeismDataListess0307")
	public ModelAndView viewImportExcelEssAbsenteeismDataListess0240(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paEssAbsenteeismTempList = this.infoApplySer.getEssAbsenteeismTempList(request);
		int paEssAbsenteeismTempCnt = this.infoApplySer.getEssAbsenteeismTempCnt(request , "T");
		int errorCnt = this.infoApplySer.getEssAbsenteeismTempCnt(request , "E");
		
		modelMap.put("paEssAbsenteeismTempList", paEssAbsenteeismTempList);
		modelMap.put("paEssAbsenteeismTempCnt", paEssAbsenteeismTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paEssAbsenteeismTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paEssAbsenteeismTempCnt);
		return new ModelAndView("/pa/tempsale/viewImportExcelEssAbsenteeismDataListess0307", modelMap);
	}
	

	/**
	 * 休假批量申请excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelEssAbsenteeismEmpData")
	@ResponseBody
	public Map submitImportExcelEssAbsenteeismEmpData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.infoApplySer.submitImportExcelEssAbsenteeismEmpData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功
			jo.put("navTabId", "ess0235");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
	}


	/**
	 * 考勤 异常查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArCwaBatchInfoList")
	public ModelAndView viewArCwaBatchInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("admin", admin);
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		
		List list = annualadjustmentInfoSer.getCwaAbnormalApplyInfoList(request,"Y");       
		modelMap.put("cwaInfoList",list);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,annualadjustmentInfoSer.getCwaAbnormalApplyInfoListCnt(request,"Y"));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "527"));
		
		return new ModelAndView("/ess/infoApply/viewArCwaBatchInfoList",
				modelMap);
	}
	

	/**
	 * 批量删除旷工申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delarCwaApplyInBatchForBatch")
	@ResponseBody
	public Map<String, Object> delarCwaApplyInBatchForBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String op_flag = request.getParameter("OP_FLAG");
		String msg = "删除";
		if("1".equals(op_flag)){
			msg = "提交";
		}
		try {
			result = infoApplySer.delArCwaApplyInBatchForBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0246");
				map.put("message", msg + "成功！");//"批量删除休假申请审批成功!"
				map.put("statusCode", "200");
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} 
		map.put("result", result);
		return map;
	}
	

	/**
	 * 查看完整决裁详情信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullCwaApplyAffirmInfo")
	public ModelAndView viewFullCwaApplyAffirmInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		List applyorList = annualadjustmentInfoSer.getCwaAbnormalAffirmByApplyNOList(request);
		Map applyorInfo = (Map)applyorList.get(0);
		modelMap.put("applyorInfo",applyorInfo );
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
	 
		if( applyorInfo != null  && "Y".equals(StringUtil.checkNull(applyorInfo.get("BATCH_YN")))){
			modelMap.put("arCwaBatchAffirmList", infoApplySer.getArCwaBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getArCwaBatchAffirmInfoCnt(request));
		}
		
		return new ModelAndView("/ess/infoApply/viewFullCwaApplyAffirmInfo", modelMap);
	}
	

	/**
	 * 查看完整决裁详情信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullCwaApplyAffirmInfo1")
	public ModelAndView viewFullCwaApplyAffirmInfo1List(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		List applyorList = annualadjustmentInfoSer.getCwaAbnormalAffirmByApplyNOList(request);
		Map applyorInfo = (Map)applyorList.get(0);
		modelMap.put("applyorInfo",applyorInfo );
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
	 
		if( applyorInfo != null  && "Y".equals(StringUtil.checkNull(applyorInfo.get("BATCH_YN")))){
			modelMap.put("arCwaBatchAffirmList", infoApplySer.getArCwaBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getArCwaBatchAffirmInfoCnt(request));
		}
		
		return new ModelAndView("/ess/infoApply/viewFullCwaApplyAffirmInfo1", modelMap);
	}
	

	/**
	 * 批量加班审批者调整
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyAffirmorForBatchArCwa")
	@ResponseBody
	public Map modifyAffirmorForBatchArCwa(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.modifyAffirmorForBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0235");
				map.put("message", "操作成功");//"保存加班申请成功!"
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "操作失败");
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 获取G职标记
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGradeYn")
	@ResponseBody
	public Map getGradeYn(HttpServletRequest request,HttpServletResponse response) throws Exception {

        AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		String returnString = this.infoApplySer.getGradeYn(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", "200");
		map.put("message", TipMessage.getTipMessage(
				"alert.message.update_success", request));
		map.put("GYN", returnString);
		return map;
	}
	
	/**
	 * SST事前加班申请
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSSTOtApplyInfo")
	public ModelAndView viewSSTOtApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("APPLY_DATE", DateUtil.getSysdateStr("dd/MM/yyyy"));
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		return new ModelAndView("/ess/infoApply/viewSSTOtApplyInfo", modelMap);
	}

	
	/**
	 * SST加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addSSTOvertimeApply")
	@ResponseBody
	public Map addSSTOvertimeApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.addSSTOvertimeApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0234");
				map.put("message", TipMessage.getTipMessage("alert.message.save_success", request));//"保存成功"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", e.getMessage());//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * SST加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifySSTOvertimeApply")
	@ResponseBody
	public Map modifySSTOvertimeApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.modifySSTOvertimeApply(request);
			if (result == 1) {
				map.put("formId", "viewSSTOtApplyInfoListForm");
				map.put("message", "保存成功");//"保存加班申请成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", e.getMessage());//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * SST事前加班申请
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSSTOtApplyInfoTx")
	public ModelAndView viewSSTOtApplyInfoTx(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));

		//获取上次申请时的审批线
		List affirmorList = this.infoApplySer.getAffirmorListByHistory(request,"31");
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCntTx", affirmorList.size());
		return new ModelAndView("/ess/infoApply/viewSSTOtApplyInfoTx", modelMap);
	}

	/**
	 * 加班时候确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSSTOtApplyInfoList")
	public ModelAndView viewSSTOtApplyInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("START_DATE", DateUtil.getSysdateStr("yyyy.MM") + ".01");
		//modelMap.put("END_DATE", DateUtil.getSysdateStr("yyyy.MM.dd"));
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		List otList = this.infoApplySer.getSSTOtAffirmInfoList(request);
		modelMap.put("otList", otList);
		modelMap.put("otListSize", otList == null ? 0 : otList.size());
		return new ModelAndView("/ess/infoApply/viewSSTOtApplyInfoList", modelMap);
	}

	/**
	 * 未审批信箱
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApprovalEmail")
	public ModelAndView viewApprovalEmailList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewApprovalEmail = this.infoApplySer.viewApprovalInfo(request,"viewApprovalEmail");
		modelMap.put("viewApprovalEmail", viewApprovalEmail);
		modelMap.put("viewApprovalEmailSize", viewApprovalEmail == null ? 0 : viewApprovalEmail.size());
		return new ModelAndView("/ess/infoApply/viewApprovalEmail", modelMap);
	}

	/**
	 * 未审批信箱
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApprovaledEmail")
	public ModelAndView viewApprovaledEmailList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
        
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
		Calendar c = Calendar.getInstance();    
		if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
			//获取当前年第一天：
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());
			modelMap.put("START_DATE",first);
			//获取当前日期：
			c = Calendar.getInstance();  
			c.add(Calendar.MONTH, 0);
			String last = format.format(c.getTime());
			modelMap.put("END_DATE",last);
		}

		List viewApprovaledEmail = this.infoApplySer.viewApprovalInfo(request,"viewApprovaledEmail");
		modelMap.put("viewApprovaledEmail", viewApprovaledEmail);
		modelMap.put("viewApprovaledEmailSize", viewApprovaledEmail == null ? 0 : viewApprovaledEmail.size());
		return new ModelAndView("/ess/infoApply/viewApprovaledEmail", modelMap);
	}
	
	/**
	 * 批量未审批信箱
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApprovaledBatchEmail")
	public ModelAndView viewApprovaledBatchEmailList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
        
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
		Calendar c = Calendar.getInstance();    
		if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
			//获取当前年第一天：
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());
			modelMap.put("START_DATE",first);
			//获取当前日期：
			c = Calendar.getInstance();  
			c.add(Calendar.MONTH, 0);
			String last = format.format(c.getTime());
			modelMap.put("END_DATE",last);
		}

		List viewApprovaledBatchEmail = this.infoApplySer.viewApprovalInfo(request,"viewApprovaledBatchEmail");
		modelMap.put("viewApprovaledBatchEmail", viewApprovaledBatchEmail);
		modelMap.put("viewApprovaledEmailSize", viewApprovaledBatchEmail == null ? 0 : viewApprovaledBatchEmail.size());
		return new ModelAndView("/ess/infoApply/viewApprovaledBatchEmail", modelMap);
	}

	/**
	 * 未审批信箱
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewNoticeedEmail")
	public ModelAndView viewNoticeedEmailList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
		Calendar c = Calendar.getInstance();    
		if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
			//获取当前年第一天：
			c.add(Calendar.MONTH, -1);
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());
			modelMap.put("START_DATE",first);
			//获取当前日期：
			c = Calendar.getInstance();  
			c.add(Calendar.MONTH, 0);
			String last = format.format(c.getTime());
			modelMap.put("END_DATE",last);
		}

		List viewNoticeedEmail = this.infoApplySer.viewApprovalInfo(request,"viewNoticeedEmail");
		modelMap.put("viewNoticeedEmail", viewNoticeedEmail);
		modelMap.put("viewNoticeedEmailSize", viewNoticeedEmail == null ? 0 : viewNoticeedEmail.size());
		return new ModelAndView("/ess/infoApply/viewNoticeedEmail", modelMap);
	}

	/**
	 * 加班审批页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApprovaledOt")
	public ModelAndView viewApprovaledOtList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List otApplyInfo = this.infoApplySer.viewApprovalInfo(request,"getSSTOtInfoList");
		if(otApplyInfo != null && otApplyInfo.size() > 0){
			modelMap.put("otApplyInfo", otApplyInfo == null ? null : otApplyInfo.get(0));
		}
		//获取当前审批者
		List viewAffirmList = this.infoApplySer.viewApprovalInfo(request,"viewAffirmList");
		if(viewAffirmList != null && viewAffirmList.size() > 0){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String activity = StringUtil.checkNull(request.getParameter("seach_ACTIVITY"));
			if("0".equals(activity)){
				for(int i=0;i<viewAffirmList.size() ;i++){
					Map map = (Map)viewAffirmList.get(i);
					if(admin.getAdminID().equals(StringUtil.checkNull(map.get("AFFIRM_PERSON_ID"))) && activity.equals(StringUtil.checkNull(map.get("ACTIVITY")))){
						modelMap.put("currentAffirmor",map);
						break;
					}
				}
			}
		}
		if("1".equals(StringUtil.checkNull(request.getParameter("READ_FLAG")))){
			this.infoApplySer.viewModifyApprovalInfo(request,"updateReadStatus");
		}
		String isHtml = request.getParameter("isHtml");
		modelMap.put("isHtml", isHtml);
		modelMap.put("viewAffirmList",viewAffirmList);
		return new ModelAndView("/ess/infoApply/viewApprovaledOt", modelMap);
	}
	
	/**
	 * 考勤异常审批页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAttendanceEx")
	public ModelAndView viewAttendanceExList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List attendanceExInfo = this.infoApplySer.viewApprovalInfo(request,"getAttendanceExList");
		if(attendanceExInfo != null && attendanceExInfo.size() > 0){
			modelMap.put("attendanceExInfo", attendanceExInfo == null ? null : attendanceExInfo.get(0));
		}
		//获取当前审批者
		List viewAffirmList = this.infoApplySer.viewApprovalInfo(request,"viewAffirmList");
		if(viewAffirmList != null && viewAffirmList.size() > 0){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String activity = StringUtil.checkNull(request.getParameter("seach_ACTIVITY"));
			if("0".equals(activity)){
				for(int i=0;i<viewAffirmList.size() ;i++){
					Map map = (Map)viewAffirmList.get(i);
					if(admin.getAdminID().equals(StringUtil.checkNull(map.get("AFFIRM_PERSON_ID"))) && activity.equals(StringUtil.checkNull(map.get("ACTIVITY")))){
						modelMap.put("currentAffirmor",map);
						break;
					}
				}
			}
		}
		if("1".equals(StringUtil.checkNull(request.getParameter("READ_FLAG")))){
			this.infoApplySer.viewModifyApprovalInfo(request,"updateReadStatus");
		}
		String isHtml = request.getParameter("isHtml");
		modelMap.put("isHtml", isHtml);
		modelMap.put("viewAffirmList",viewAffirmList);
		return new ModelAndView("/ess/infoApply/viewAttendanceEx", modelMap);
	}

	/**
	 * 休假审批页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApprovaledLeave")
	public ModelAndView viewApprovaledLeaveList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List leaveApplyInfo = this.infoApplySer.viewApprovalInfo(request,"getSSTLeaveAffirmInfoList");
		Map leaveApplyInfoMap = null;
		if(leaveApplyInfo != null && leaveApplyInfo.size() > 0){
			leaveApplyInfoMap = (Map)leaveApplyInfo.get(0);
		}
		if(leaveApplyInfoMap!=null ){
			
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "LEAVE_APPLY");
			fileParam.put("APPLY_NO", leaveApplyInfoMap.get("APPLY_NO"));
			fileParam.put("interCpnyID",leaveApplyInfoMap.get("CPNY_ID"));
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			leaveApplyInfoMap.put("fileList",fileList);
			fileParam.put("APPLY_PERSON_ID", leaveApplyInfoMap.get("PERSON_ID"));
			fileParam.put("YEAR", StringUtil.checkNull(leaveApplyInfoMap.get("LEAVE_FROM_TIME")).substring(0, 4));
			modelMap.put("empVacInfo",this.infoApplyLeaveSer.getEmpVacInfoSSTForDisplay(fileParam));
		}
		modelMap.put("leaveApplyInfo", leaveApplyInfoMap);

		
		List viewAffirmList = this.infoApplySer.viewApprovalInfo(request,"viewAffirmList");
		//获取当前审批者
		if(viewAffirmList != null && viewAffirmList.size() > 0){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String activity = StringUtil.checkNull(request.getParameter("seach_ACTIVITY"));
			if("0".equals(activity)){
				for(int i=0;i<viewAffirmList.size() ;i++){
					Map map = (Map)viewAffirmList.get(i);
					if(admin.getAdminID().equals(StringUtil.checkNull(map.get("AFFIRM_PERSON_ID"))) && activity.equals(StringUtil.checkNull(map.get("ACTIVITY")))){
						modelMap.put("currentAffirmor",map);
						break;
					}
				}
			}
		}
		if("1".equals(StringUtil.checkNull(request.getParameter("READ_FLAG")))){
			this.infoApplySer.viewModifyApprovalInfo(request,"updateReadStatus");
		}
		String isHtml = request.getParameter("isHtml");
		modelMap.put("isHtml", isHtml);
		modelMap.put("viewAffirmList",viewAffirmList);
		return new ModelAndView("/ess/infoApply/viewApprovaledLeave", modelMap);
	}

	/**
	 * 审批
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/executeAffirm")
	@ResponseBody
	public Map executeAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String result = infoApplySer.executePro(request,"PR_AFFIRM_EXECUTE");
			if ("OK".equals(result)) {
				map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//审批成功
				map.put("statusCode", "200");
				map.put("formId", "viewApprovalEmailForm");
			}else{
				map.put("message", result);//"保存加班申请成功!"
				map.put("statusCode", "300");
			}
		}catch (Exception e) {
			map.put("message", e.getMessage());//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * SST事前加班申请
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewModifySSTOtApplyInfo")
	public ModelAndView viewModifySSTOtApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		//获取加班信息
		List otApplyInfo = this.infoApplySer.viewApprovalInfo(request,"getSSTOtAffirmInfoList");
		modelMap.put("otApplyInfo", otApplyInfo == null ? null : otApplyInfo.get(0));
		//获取上次申请时的审批线
		List affirmorList = this.infoApplySer.getAffirmorListByHistory(request,"31");
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		return new ModelAndView("/ess/infoApply/viewModifySSTOtApplyInfo", modelMap);
	}

	/**
	 * 批量审批
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/executeAffirmBatch")
	@ResponseBody
	public Map executeAffirmBatch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String result = infoApplySer.executeProBatch(request,"PR_AFFIRM_EXECUTE");
			//if ("OK".equals(result)) {
				map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
				map.put("statusCode", "200");
				map.put("formId", "viewApprovalEmailForm");
			//}else{
				//map.put("message", result);//"保存加班申请成功!"
				//map.put("statusCode", "300");
			//}
		}catch (Exception e) {
			map.put("message", e.getMessage());//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 批量审批(一条审批信息,但详细人员批量审批)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/executeBatchAffirm")
	@ResponseBody
	public Map executeBatchAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String result = infoApplySer.executeProBatch(request,"PR_AFFIRM_EXECUTE");
			if ("OK".equals(result)) {
				map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
				map.put("statusCode", "200");
				map.put("formId", "viewApprovalBatchEmailForm");
			}else{
				map.put("message", result);//"保存加班申请成功!"
				map.put("statusCode", "300");
			}
		}catch (Exception e) {
			map.put("message", e.getMessage());//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 批量审批(一条审批数据,全部审批)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/executeAffirmBatchByBatchNo")
	@ResponseBody
	public Map executeAffirmBatchByBatchNo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String result = infoApplySer.executeProBatchByBatchNo(request,"PR_AFFIRM_EXECUTE");
			if ("OK".equals(result)) {
				map.put("message", TipMessage.getTipMessage("hr.contract.title.shenpi.chenggong", request));//"审批成功"
				map.put("statusCode", "200");
				map.put("formId", "viewApprovalBatchEmailForm");
			}else{
				map.put("message", result);//"保存加班申请成功!"
				map.put("statusCode", "300");
			}
		}catch (Exception e) {
			map.put("message", e.getMessage());//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 批量删除
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAffirmBatch")
	@ResponseBody
	public Map deleteAffirmBatch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.viewModifyApprovalInfo(request,"deleteAffirmBatch");
			if (result == 1) {
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));//"删除成功"
				map.put("statusCode", "200");
				map.put("formId", request.getParameter("FORM_ID"));
			}else{
				map.put("message", result);//"保存加班申请成功!"
				map.put("statusCode", "300");
			}
		}catch (Exception e) {
			map.put("message", e.getMessage());//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 批量删除
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAffirmByBatchNo")
	@ResponseBody
	public Map deleteAffirmByBatchNo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.viewModifyApprovalInfo(request,"deleteAffirmBatchByBatchNo");
			if (result == 1) {
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));//"删除成功"
				map.put("statusCode", "200");
				map.put("formId", request.getParameter("FORM_ID"));
			}else{
				map.put("message", result);//"保存加班申请成功!"
				map.put("statusCode", "300");
			}
		}catch (Exception e) {
			map.put("message", e.getMessage());//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 批量标记为已读
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/readedAffirmBatch")
	@ResponseBody
	public Map readedAffirmBatch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.viewModifyApprovalInfo(request,"readedAffirmBatch");
			if (result == 1) {
				map.put("message", TipMessage.getTipMessage("alert.message.Marked_Success.b", request));//"标记成功""
				map.put("statusCode", "200");
				map.put("formId", request.getParameter("FORM_ID"));
			}else{
				map.put("message", result);//"保存加班申请成功!"
				map.put("statusCode", "300");
			}
		}catch (Exception e) {
			map.put("message", e.getMessage());//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 批量标记为已读
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/readedAffirmByBatchNo")
	@ResponseBody
	public Map readedAffirmByBatchNo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.viewModifyApprovalInfo(request,"readedAffirmByBatchNo");
			if (result == 1) {
				map.put("message", TipMessage.getTipMessage("alert.message.Marked_Success.b", request));//"标记成功""
				map.put("statusCode", "200");
				map.put("formId", request.getParameter("FORM_ID"));
			}else{
				map.put("message", result);//"保存加班申请成功!"
				map.put("statusCode", "300");
			}
		}catch (Exception e) {
			map.put("message", e.getMessage());//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	

	/**
	 * 加班审批页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApprovaledOtInfo")
	public ModelAndView viewApprovaledOtInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List affirmParam = this.infoApplySer.viewApprovalInfo(request,"getAffirmEmailParam");
		Map map = new HashMap();
		if(affirmParam != null && affirmParam.size() > 0){
			map = (Map)affirmParam.get(0);
		}
		map.put("interLanguage",admin.getLanguage());
		map.put("LANGUAGE",admin.getLanguage());
		map.put("CPNY_ID",admin.getCpnyId());
		
		List otApplyInfo = this.infoApplyDao.viewApprovalInfo(map,"getSSTOtInfoList");
		if(otApplyInfo != null && otApplyInfo.size() > 0){
			modelMap.put("otApplyInfo", otApplyInfo == null ? null : otApplyInfo.get(0));
		}
		//获取当前审批者
		List viewAffirmList = this.infoApplyDao.viewApprovalInfo(map,"viewAffirmList1");
		modelMap.put("viewAffirmList",viewAffirmList);
		
		List getArCardRecordMealList = this.arCardRecordDao.getArCardRecordMealList(map);
		modelMap.put("getArCardRecordMealList",getArCardRecordMealList);
		
		return new ModelAndView("/ess/infoApply/viewApprovaledOtInfo", modelMap);
	}

	/**
	 * 休假审批页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApprovaledLeaveInfo")
	public ModelAndView viewApprovaledLeaveInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List affirmParam = this.infoApplySer.viewApprovalInfo(request,"getAffirmEmailParam");
		Map map = null;
		if(affirmParam != null && affirmParam.size() > 0){
			map = (Map)affirmParam.get(0);
		}
		map.put("interLanguage",admin.getLanguage());
		map.put("interCpnyID",admin.getCpnyId());
		List leaveApplyInfo = this.infoApplyDao.viewApprovalInfo(map,"getSSTLeaveAffirmInfoList");
		Map leaveApplyInfoMap = leaveApplyInfo == null ? null : (Map)leaveApplyInfo.get(0);
		if(leaveApplyInfoMap!=null ){
			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "LEAVE_APPLY");
			fileParam.put("APPLY_NO", leaveApplyInfoMap.get("APPLY_NO"));
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			leaveApplyInfoMap.put("fileList",fileList);
		}
		modelMap.put("leaveApplyInfo", leaveApplyInfoMap);

		List viewAffirmList = this.infoApplyDao.viewApprovalInfo(map,"viewAffirmList");
		List viewAffirmList2 = this.infoApplyDao.viewApprovalInfo(map,"viewAffirmList2");
		modelMap.put("empVacInfo",this.infoApplyLeaveSer.getEmpVacInfoSST(request));
		modelMap.put("viewAffirmList",viewAffirmList);
		modelMap.put("viewAffirmList2",viewAffirmList2);
		return new ModelAndView("/ess/infoApply/viewApprovaledLeaveInfo", modelMap);
	}
	
	
	/**
	 * 内务调休批量处理(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAdjustLeaveTSTOBatchList")
	public ModelAndView viewAdjustLeaveTSTOBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
         AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		//进行删除空数据
		String deleteYN = request.getParameter("deleteYN");
		if ("Y".equals(deleteYN)) {
			infoApplyLeaveSer.deleteAllDataForAdd(request);
		}
		
		
		//全部反应用
		modelMap.put("APP_OT_DATE",request.getParameter("APP_OT_DATE"));
		modelMap.put("fromTime1",request.getParameter("fromTime1"));
		modelMap.put("toTime1",request.getParameter("toTime1"));
		modelMap.put("reason",request.getParameter("reason"));
		modelMap.put("otherReason",request.getParameter("otherReason"));
		modelMap.put("FILLAFFIRMFLAG",request.getParameter("FILLAFFIRMFLAG"));
		modelMap.put("work_time_shift1",request.getParameter("work_time_shift1"));
		modelMap.put("CONFIRM_FLAG1",request.getParameter("CONFIRM_FLAG1"));
		
		List shiftList = shiftSer.getShiftList1(request);
		List itemList = itemsSer.getItemParamList2(request);
		List workTimeLsit = shiftSer.getWorkTimeLsit(request);
		List dateTypeLsit = shiftSer.getDateTypeLsit(request);
		modelMap.put("workTimeList", workTimeLsit) ;
		modelMap.put("dateTypeLsit", dateTypeLsit) ;
		modelMap.put("itemList", itemList) ;
		modelMap.put("shiftList", shiftList) ;
		
		//班次显示，根据日期类型
		List workTimeLsit1 = shiftSer.getWorkTimeLsit1(request);
		modelMap.put("workTimeList1", workTimeLsit1) ;
		List workTimeLsit2 = shiftSer.getWorkTimeLsit2(request);
		modelMap.put("workTimeList2", workTimeLsit2) ;
		
		Date d=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		String FROM_DATE = request.getParameter("seach_FROM_DATE") != null ? request.getParameter("seach_FROM_DATE") :df.format(new Date(d.getTime()-60*60*24*1000)) ;
		String TO_DATE = request.getParameter("seach_TO_DATE") != null ? request.getParameter("seach_TO_DATE") : df.format(new Date(d.getTime()-60*60*24*1000));
		modelMap.put("FROM_DATE",  FROM_DATE);
		modelMap.put("TO_DATE", TO_DATE);
		
		LinkedHashMap deleteMap = new LinkedHashMap();
		deleteMap.put("FROM_DATE", FROM_DATE);
		deleteMap.put("TO_DATE", TO_DATE);
		arDetailSer.deleteOtAdjustForOnlyOne(deleteMap);
		
		
		String   DEPTNO  =request.getParameter("seach_DEPTNO");
		modelMap.put("empName",request.getParameter("dwz.person.empName"));
		modelMap.put("empInfo",request.getParameter("dwz.person.empInfo"));
		modelMap.put("AFFIRM_FLAG",request.getParameter("seach_AFFIRM_FLAG"));
		modelMap.put("GROUP_ID",request.getParameter("seach_GROUP_ID"));
		modelMap.put("SHIFT_NO",request.getParameter("seach_SHIFT_NO"));
		modelMap.put("CONFIRM_FLAG",request.getParameter("seach_CONFIRM_FLAG"));
		modelMap.put("EMP_TYPE_CODE",request.getParameter("seach_EMP_TYPE_CODE"));
		//modelMap.put("adjusthidden",request.getParameter("adjusthidden"));
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("admin", admin);
		modelMap.put("DEPTNO", DEPTNO);
		Map paramMap=new LinkedHashMap();
		//审批code
		paramMap.put("PARENT_CODE_NO","14014304");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		
		//原因code
		Map paramMap2=new LinkedHashMap();
		paramMap2.put("PARENT_CODE_NO","14014313");
		paramMap2.put("interLanguage",admin.getLanguage());
		paramMap2.put("CPNY_ID",admin.getCpnyId());
		List codeList2 = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap2, -1, -1) ;
		//班组code
		Map paramMap4=new LinkedHashMap();
		paramMap4.put("PARENT_CODE_NO","400223");
		paramMap4.put("interLanguage",admin.getLanguage());
		paramMap4.put("CPNY_ID",admin.getCpnyId());
		List codeList4 = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap4, -1, -1) ;

		modelMap.put("codeList", codeList);
		modelMap.put("codeList2", codeList2);
		modelMap.put("codeList4", codeList4);
		String firstFlag = request.getParameter("firstFlag");
		String nullYN = request.getParameter("nullYN");
		if(firstFlag != null && !"".equals(firstFlag)&&!"Y".equals(nullYN)){
			modelMap.put("AdjustHolidayList", infoApplySer.viewAdjustLeaveTSTOBatchList(request));
			LinkedHashMap linkMap2 = (LinkedHashMap)empInfoSer.viewHrPersonalInfo2(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo",linkMap2);
		}else {
			List nullAJTSTOAffirmList = infoApplyLeaveSer.getNullBatchAJTSTOAffirmInfoList(request);
			modelMap.put("nullAJTSTOAffirmList", nullAJTSTOAffirmList);
		}
		return new ModelAndView("/ess/infoApply/viewAdjustLeaveTSTOBatchList", modelMap);
	}
	
	/**
	 * 内务倒休批量处理(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delAdjustApplyLeaveCoordForm")
	@ResponseBody
	public Map<String, Object> delAdjustApplyLeaveCoordForm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String msg = "删除";
		String op_flag = request.getParameter("OP_FLAG");
		if("1".equals(op_flag)){
			msg = "提交";
		}
		try {                   
			result = infoApplySer.delAdjustApplyLeaveCoordForm(request);
			if (result == 1) {
				map.put("navTabId", "ess3403");
				map.put("message", msg + "成功！");
				map.put("statusCode", "200");
				map.put("formId", "viewAdjustLeaveTSTOBatchList");
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
	
	/**
	 * 加班申请考勤员
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOtApplyAffirmForBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map saveAttendanceApplyInfoForBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try { 
			result = infoApplySer.saveOtApplyAffirmForBatch(request);
			if (result==1) {
				map.put("message", TipMessage.getTipMessage("alert.message.save_success", request));//"保存成功!"
				map.put("statusCode", "200");
				map.put("formId", "viewPiciOtAffirmLBatchList");
			}else if(result==2){
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));//"保存失败!"
				map.put("statusCode", "300");
			}
		}  catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 加班申请考勤员(可添加删除审批者)
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOtApplyByAnyApproverForBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map saveOtApplyByAnyApproverForBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try { 
			result = infoApplySer.saveOtApplyByAnyApproverForBatch(request);
			if (result==1) {
				map.put("message", TipMessage.getTipMessage("alert.message.save_success", request));//"保存成功!"
				map.put("statusCode", "200");
				map.put("formId", "viewApplyOtLBatchByAnyApproverList");
			}else if(result==2){
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));//"保存失败!"
				map.put("statusCode", "300");
			}
		}  catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 加班申请考勤员
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOTApplyInfoForBatchHAE", method = RequestMethod.POST)
	@ResponseBody
	public Map saveOTApplyInfoForBatchHAE(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try{
			result = infoApplySer.saveOTApplyInfoForBatchHAE(request);
			if (result==1) {
				map.put("message", TipMessage.getTipMessage("ar.alert.message.addempshift.success",request));//保存成功
				map.put("statusCode", "200");
				map.put("formId", "viewApplyOTBatchInfoListHAE");
			}else{
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败！
				map.put("statusCode", "300");
			}
		}catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		}
		return map;
	}
	
	@RequestMapping(value = "/saveAdjustHolidayApplyForBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map saveAdjustHolidayApplyForBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try { 
			result = infoApplySer.saveAdjustHolidayApplyForBatch(request);
			if (result==1) {
				map.put("message", "保存成功!");
				map.put("statusCode", "200");
				map.put("formId", "viewArAdjustHolidayManagent");
			}else if(result==2){
				map.put("message", "保存失败!");
				map.put("statusCode", "300");
			}
		}  catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		}
		return map;
	}
	/**
	 * 批量删除加班申请考勤员
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delOtApplyAffirmForBatch")
	@ResponseBody
	public Map<String, Object> delOtApplyAffirmForBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.delOtApplyAffirmForBatch(request);
			if (result == 1) {
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));//"删除成功"
				map.put("statusCode", "200");
				map.put("formId", "viewPiciOtAffirmLBatchList");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));//"删除失败"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 批量删除加班申请考勤员
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delAdjustHolidayApplyForBatch")
	@ResponseBody
	public Map<String, Object> delAdjustHolidayApplyForBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.delAdjustHolidayApplyForBatch(request);
			if (result == 1) {
				map.put("message", "删除成功");//"批量删除休假申请决裁成功!"
				map.put("statusCode", "200");
				map.put("formId", "viewArAdjustHolidayManagent");
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
	@RequestMapping(value = "/addOtApplyAffirm", method = RequestMethod.POST)
	@ResponseBody
	public Map addOtApplyAffirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.addOtApplyAffirm(request);
			if (result==1) {
				map.put("statusCode", "200");
				map.put("formId", "viewPiciOtAffirmLBatchList");
			}else if(result==2){
				map.put("statusCode", "300");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("statusCode", "300");
		}
		return map;
	}
	
	@RequestMapping(value = "/addAdjustHolidayApply", method = RequestMethod.POST)
	@ResponseBody
	public Map addAdjustHolidayApply(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplySer.addAdjustHolidayApply(request);
			if (result==1) {
				map.put("message", "添加成功!");
				map.put("statusCode", "200");
				map.put("formId", "viewArAdjustHolidayManagent");
			}else if(result==2){
				map.put("message", "添加失败!");
				map.put("statusCode", "300");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "添加失败");
			map.put("statusCode", "300");
		}
		return map;
	}
	
	@RequestMapping(value = "/getValidateInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map getValidateInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		List result = infoApplySer.getValidateInfo(paramMap);
		map.put("result", result);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequestForSearch(
			HttpServletRequest request, String flag) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				flag);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportOtTempList")
	public ModelAndView viewImportoOtTempList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List otTempList = this.infoApplySer.getOtTempList(request);
		int otTempCnt = this.infoApplySer.getOtTempCnt(request , "T");
		int errorCnt = this.infoApplySer.getOtTempCnt(request , "E");
		
		modelMap.put("otTempList", otTempList);
		modelMap.put("otTempCnt", otTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", otTempCnt);
		return new ModelAndView("/ess/infoApply/viewImportOtTempList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelOtData")
	@ResponseBody
	public Map submitImportExcelOtData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.infoApplySer.submitImportExcelOtData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("alert.message.Submit_Success.b", request));//提交成功
			jo.put("navTabId", "org0203");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("alert.message.Submit_Fail.b", request));//提交失败
		}
		return jo;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOtLength")
	@ResponseBody
	public Map getOtLength (HttpServletRequest request,HttpServletResponse response) throws Exception{
		LinkedHashMap<String,String> returnMap= (LinkedHashMap<String, String>) infoApplySer.getOtLength(request);
		return returnMap;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOtShiftTime")
	@ResponseBody
	public Map getOtShiftTime (HttpServletRequest request,HttpServletResponse response) throws Exception{
		LinkedHashMap<String,String> returnMap= (LinkedHashMap<String, String>) infoApplySer.getOtShiftTime(request);
		return returnMap;
	}
	
	
	/**
	 * 批量申请(一条审批)未审批信箱
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApprovalBatchEmail")
	public ModelAndView viewApprovalEmailBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewApprovalBatchEmail = this.infoApplySer.viewApprovalInfo(request,"viewApprovalBatchEmail");
		modelMap.put("viewApprovalBatchEmail", viewApprovalBatchEmail);
		modelMap.put("viewApprovalBatchEmailSize", viewApprovalBatchEmail == null ? 0 : viewApprovalBatchEmail.size());
		return new ModelAndView("/ess/infoApply/viewApprovalBatchEmail", modelMap);
	}
	
	/**
	 * 批量休假审批页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBatchApprovaledLeave")
	public ModelAndView viewBatchApprovaledLeaveList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List leaveApplyInfoList = this.infoApplySer.viewApprovalInfo(request,"getBatchLeaveAffirmInfoList");
		modelMap.put("leaveApplyInfoList", leaveApplyInfoList);

		
		List viewAffirmList = this.infoApplySer.viewApprovalInfo(request,"viewBatchAffirmList");
		//获取当前审批者
		if(viewAffirmList != null && viewAffirmList.size() > 0){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String activity = StringUtil.checkNull(request.getParameter("seach_ACTIVITY"));
			if("0".equals(activity)){
				for(int i=0;i<viewAffirmList.size() ;i++){
					Map map = (Map)viewAffirmList.get(i);
					if(admin.getAdminID().equals(StringUtil.checkNull(map.get("AFFIRM_PERSON_ID"))) && activity.equals(StringUtil.checkNull(map.get("ACTIVITY")))){
						modelMap.put("currentAffirmor",map);
						break;
					}
				}
			}
		}
		if("1".equals(StringUtil.checkNull(request.getParameter("READ_FLAG")))){
			this.infoApplySer.viewModifyApprovalInfo(request,"updateBatchReadStatus");
		}
		String isHtml = request.getParameter("isHtml");
		modelMap.put("isHtml", isHtml);
		modelMap.put("viewAffirmList",viewAffirmList);
		return new ModelAndView("/ess/infoApply/viewBatchApprovaledLeave", modelMap);
	}
	
	/**
	 * 批量加班审批页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBatchApprovaledOT")
	public ModelAndView viewBatchApprovaledOT(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List OTApplyInfoList = this.infoApplySer.viewApprovalInfo(request,"getBatchOTAffirmInfoList");
		modelMap.put("OTApplyInfoList", OTApplyInfoList);

		
		List viewAffirmList = this.infoApplySer.viewApprovalInfo(request,"viewBatchOTAffirmList");
		//获取当前审批者
		if(viewAffirmList != null && viewAffirmList.size() > 0){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String activity = StringUtil.checkNull(request.getParameter("seach_ACTIVITY"));
			if("0".equals(activity)){
				for(int i=0;i<viewAffirmList.size() ;i++){
					Map map = (Map)viewAffirmList.get(i);
					if(admin.getAdminID().equals(StringUtil.checkNull(map.get("AFFIRM_PERSON_ID"))) && activity.equals(StringUtil.checkNull(map.get("ACTIVITY")))){
						modelMap.put("currentAffirmor",map);
						break;
					}
				}
			}
		}
		if("1".equals(StringUtil.checkNull(request.getParameter("READ_FLAG")))){
			this.infoApplySer.viewModifyApprovalInfo(request,"updateBatchOTReadStatus");
		}
		String isHtml = request.getParameter("isHtml");
		modelMap.put("isHtml", isHtml);
		modelMap.put("viewAffirmList",viewAffirmList);
		return new ModelAndView("/ess/infoApply/viewBatchApprovaledOT", modelMap);
	}
	
	/**
	 * 获得部门长权限下的考勤异常列表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAbnormalDetailInfo")
	public ModelAndView viewAbnormalApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		List getAbnormalDetailList = this.arDetailSer.viewAbnormalDetailInfo(request);
		modelMap.put("ITEM_TYPE", request.getParameter("ITEM_TYPE"));
		modelMap.put("AbnormalDetailList", getAbnormalDetailList);
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("AbnormalDetailListCnt", getAbnormalDetailList ==null ? "0" : getAbnormalDetailList.size());
		
		return new ModelAndView("/ess/infoApply/viewAbnormalDetailInfo",
				modelMap);
	}
}