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
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;















import org.apache.commons.httpclient.util.DateUtil;
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

import com.ait.ar.service.AttendanceKeeperSer;
import com.ait.ar.service.DynamicGroupSer;
import com.ait.ar.service.ItemsSer;
import com.ait.ar.service.ShiftSer;
import com.ait.ess.service.EssEmpInfoSer;
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
 * @Description:考勤申请：加班,休假,出差,外出
 * @Create date: Feb 14, 2012 10:09:38 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 14, 2012 10:09:38 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1   /ess/infoApplyAttendance/viewApplyAttenanceBatchInfoList
 */
@Controller
@RequestMapping(value = "/ess/infoApplyAttendance")
public class ApplyAttendanceCtroller{

	Logger logger = Logger.getLogger(TransferOrderCtroller.class);

	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private EssEmpInfoSer essEmpInfoSer;
	@Autowired
	private ItemsSer itemsSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@SuppressWarnings("unused")
	@Autowired
	private PersonInfoSer personInfoSer;
	@Autowired 
	private ToolMenuSer toolMenuSer;

	@Autowired
	private InfoApplyLeaveSer infoApplyLeaveSer;

	@Autowired
	private DynamicGroupSer dynamicGroupSer ;
	
	@Autowired
	private ShiftSer shiftSer;
	
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;	
	

	@Autowired
	private InfoApplySer infoApplySer;

	@Autowired
	private AuthorityUtil authorityUtil;
	
	@Autowired
	private AttendanceKeeperSer attendanceKeeperSer;
	 
	/**
	 * 考勤明细(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyAttendanceInfoList")
	public ModelAndView viewApplyAttendanceInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		List itemList = itemsSer.getItemParamList2(request);
		modelMap.put("itemList", itemList) ;
		String firstFlag= request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("FROM_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("TO_DATE",last);
			}
		}
		modelMap.put("LEAVE_APPLY_TYPE_CODE",request.getParameter("serach_LEAVE_APPLY_TYPE_CODE"));
		modelMap.put("AFFIRM_FLAG",request.getParameter("seach_AFFIRM_FLAG"));
//		if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("leaveAffirmList", infoApplyLeaveSer.getPersonalAttInfoDetailList(request));
//			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyLeaveSer.getLeaveAffirmInfoListCnt(request));
//		} else {
//			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
//		}
		
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/infoApplyAttendance/viewApplyAttendanceInfoList", modelMap);
	}
	/**
	 * 内务考勤查询(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCoordApplyAttendanceInfoList")  
	public ModelAndView viewCoordApplyAttendanceInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
		String FROM_DATE=request.getParameter("seach_FROM_DATE");
		String TO_DATE=request.getParameter("seach_TO_DATE");
		String firstFlag= request.getParameter("firstFlag");
		if(firstFlag==null){
		//当前日期的前一天
			c.add(Calendar.DATE, -1);		
			String first = format.format(c.getTime());
			modelMap.put("FROM_DATE",first);
			modelMap.put("TO_DATE", first); 
		}else{
			modelMap.put("FROM_DATE",FROM_DATE);
			modelMap.put("TO_DATE",TO_DATE); 
		}
		List shiftList = shiftSer.getShiftList1(request);
		List itemList = itemsSer.getItemParamList2(request);
		List workTimeLsit = shiftSer.getWorkTimeLsit(request);
		modelMap.put("workTimeList", workTimeLsit) ;
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
		modelMap.put("EmpOffice", request.getParameter("seach_EmpOffice"));*/
		if (firstFlag != null&& !"".equals(firstFlag)) {
			LinkedHashMap linkMap2= (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo",linkMap2);
		}
		modelMap.put("SHIFT_NAME", request.getParameter("seach_SHIFT_NO"));
		modelMap.put("leaveCoordList", infoApplyLeaveSer.getCoordLeaveInfoList(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/infoApplyAttendance/viewCoordApplyAttendanceInfoList", modelMap);
	}           
	
	/* 部门员工的考勤查询考勤(view Apply Leave Info List)
	* 
	* @param request
	* @param response
	* @param modelMap
	* @return
	* @throws Exception
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewDeptApplyAttenanceList")
	public ModelAndView viewDeptApplyAttenanceList(HttpServletRequest request,
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
	    /*modelMap.put("DEPTNO",request.getParameter("seach_DEPTNO") != null ? request.getParameter("seach_DEPTNO") : admin.getDeptNo());
		modelMap.put("ITEM_NO",request.getParameter("ITEM_NO"));*/
		List itemList = itemsSer.getItemParamList2(request);
		modelMap.put("itemList", itemList) ;
		//String firstFlag= request.getParameter("firstFlag");
		if (firstFlag != null && !"".equals(firstFlag)) {
			modelMap.put("leaveDeptList", infoApplyLeaveSer.getDeptLeaveInfoList(request));
		
	    } 
		
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/infoApplyAttendance/viewDeptApplyAttenanceList", modelMap);
	}
	
	
	//查询一般讲师
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAdjustRecords")
	public ModelAndView viewAdjustRecords(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List elist = infoApplyLeaveSer.viewAdjustRecords(request);
		modelMap.put("adjustItemList", elist);
		modelMap.put("adjustItemListCount", elist != null ? elist.size() : 0);
		modelMap.put("id",request.getParameter("ID"));
		return new ModelAndView("/ess/infoApplyAttendance/viewAdjustRecords", modelMap);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyAttendanceInfodayingList")
	public ModelAndView viewApplyAttendanceInfodayingList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personInfo", infoApplyLeaveSer.getPersonalInfo(request));
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("LEAVE_APPLY_TYPE_CODE",request.getParameter("serach_LEAVE_APPLY_TYPE_CODE"));
		modelMap.put("leaveAffirmList", infoApplyLeaveSer.getLeaveAffirmInfoList(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/infoApplyAttendance/viewApplyAttendanceInfodayingList", modelMap);
	}
	
	/**
	 * 休假决裁查看(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyLeaveInfoBatchList")
	public ModelAndView viewApplyLeaveInfoBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personInfo", infoApplyLeaveSer.getPersonalInfo(request));
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		
		modelMap.put("leaveAffirmList", infoApplyLeaveSer.getLeaveAffirmInfoBatchList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyLeaveSer.getLeaveAffirmInfoBatchListCnt(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/infoApplyLeave/viewApplyLeaveInfoBatchList", modelMap);
	}
	
	/**
	 * 显示休假申请(view overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyAttendance")
	public ModelAndView viewApplyLeaveInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		if(null != admin)
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		modelMap.put("DEFAULT_APPLY_TIME",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("empVacInfo",this.infoApplyLeaveSer.getEmpVacInfo(request));
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		return new ModelAndView("/ess/infoApplyAttendance/viewApplyAttendance", modelMap);
	}
	

	/**
	 * 显示休假申请(view overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAssigmentLeaveInfo")
	public ModelAndView viewAssigmentLeaveInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//判断修改还是新增
		String APPLY_NO = request.getParameter("APPLY_NO");
		if(null != APPLY_NO
				&& !"".equals(APPLY_NO)){
			//判断是修改
			modelMap.put("update_page", "1");
			modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			LinkedHashMap leaveApplyMap = (LinkedHashMap) infoApplyLeaveSer.getLeaveInfoByLeave(request);
			modelMap.put("leaveApplyMap", leaveApplyMap);
			
			LinkedHashMap personInfoMap = 
					(LinkedHashMap) empInfoSer.getPersonalInfoByLeaveApply(request,
							null == leaveApplyMap.get("PERSON_ID") ? "" : leaveApplyMap.get("PERSON_ID").toString());
			modelMap.put("personInfo", personInfoMap);
			modelMap.put("defaultCpny", personInfoMap.get("CPNY_ID"));
			
			List affirmorList = this.infoApplyLeaveSer.getAffirmorListByLeave(request);
			modelMap.put("affirmorList", affirmorList);
			modelMap.put("affirmorListCnt", affirmorList.size());
			modelMap.put("DEFAULT_APPLY_TIME",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}else{
			modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			if(null != admin)
				modelMap.put("defaultCpny", admin.getCpnyId());
			modelMap.put("personInfo", empInfoSer.getPersonalInfoByLeaveApply(request, null));
			modelMap.put("DEFAULT_APPLY_TIME",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}
		modelMap.put("empVacInfo",this.infoApplyLeaveSer.getEmpVacInfo(request));
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		return new ModelAndView("/ess/infoApplyLeave/viewAssigmentLeaveInfo", modelMap);
	}
	/**
	 * 批量里获取申请人年假信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getEmpVacInfo")
	public Map getEmpVacInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("TX_TOTAL",this.infoApplyLeaveSer.getEmpVacInfo(request).get("TX_TOTAL"));
		map.put("TX_SHENGYU",this.infoApplyLeaveSer.getEmpVacInfo(request).get("TX_SHENGYU"));
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
	@RequestMapping(value = "/addLeaveAttendance")
	@ResponseBody
	public Map addLeaveApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplyLeaveSer.addLeaveApply(request);
			if (result == 1) {
				map.put("navTabId", "ess3211");
				map.put("statusCode", "200");
				map.put("callbackType", "forward");
				map.put("forwardUrl", "/ess/infoApplyAttendance/viewApplyAttendanceInfoList");
			}else{
				map.put("statusCode", "300");
				map.put("message", "提交失败");
			}

		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
		    map.put("message", "提交休假申请出错,请重新申请");
			map.put("statusCode", "300");
		}
		return map;
	}
	/**
	 * 显示休假申请(view overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAssigmentLeaveInfoList")
	public ModelAndView viewAssigmentLeaveInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			modelMap.put("update_page", "1");
			modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			LinkedHashMap leaveApplyMap = (LinkedHashMap) infoApplyLeaveSer.getLeaveInfoByLeave(request);
			modelMap.put("leaveApplyMap", leaveApplyMap);
			
			LinkedHashMap personInfoMap = 
					(LinkedHashMap) empInfoSer.getPersonalInfoByLeaveApply(request,
							null == leaveApplyMap.get("PERSON_ID") ? "" : leaveApplyMap.get("PERSON_ID").toString());
			modelMap.put("personInfo", personInfoMap);
			modelMap.put("defaultCpny", personInfoMap.get("CPNY_ID"));
			
			List affirmorList = this.infoApplyLeaveSer.getAffirmorListByLeave(request);
			modelMap.put("affirmorList", affirmorList);
			modelMap.put("affirmorListCnt", affirmorList.size());
			modelMap.put("DEFAULT_APPLY_TIME",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("empVacInfo",this.infoApplyLeaveSer.getEmpVacInfo(request));
		return new ModelAndView("/ess/infoApplyAttendance/viewAssigmentLeaveInfoList", modelMap);
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
		List list = infoApplyLeaveSer.getDateByPersonIdAndCpny(request);
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
	@RequestMapping(value = "/viewFullApplyRemarkInfo")
	public ModelAndView viewFullApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List otApplyList = infoApplyLeaveSer.getLeaveAffirmInfoList(request);
		LinkedHashMap applyMap = (LinkedHashMap)otApplyList.get(0);
		modelMap.put("APPLY_NO", applyMap.get("APPLY_NO").toString());
		modelMap.put("APPLY_REMARK", applyMap.get("APPLY_REMARK").toString());
		
		response.setCharacterEncoding("UTF-8");
		return new ModelAndView("/ess/infoApplyLeave/viewFullApplyRemarkInfo", modelMap);
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
		List affirmorList = infoApplyLeaveSer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplyLeaveSer.getCheckorByApplyNoList(request);

		LinkedHashMap infoApplyLeave = (LinkedHashMap) infoApplyLeaveSer.getLeaveInfoByLeave(request);
		modelMap.put("infoApplyLeave", infoApplyLeave);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		return new ModelAndView("/ess/infoApplyLeave/viewFullApplyAffirmInfo", modelMap);
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
	@RequestMapping(value = "/viewFullApplyBatchAffirmInfo")
	public ModelAndView viewFullApplyBatchAffirmInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List affirmorList = infoApplyLeaveSer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplyLeaveSer.getCheckorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		modelMap.put("leaveBatchAffirmList", infoApplyLeaveSer.getLeaveBatchAffirmInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyLeaveSer.getLeaveBatchAffirmInfoCnt(request));
		modelMap.put("APPLY_NO",  request.getParameter("APPLY_NO"));
		
		return new ModelAndView("/ess/infoApplyLeave/viewFullApplyBatchAffirmInfo", modelMap);
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
	@RequestMapping(value = "/viewFullApplyBatchAffirmInfo1")
	public ModelAndView viewFullApplyBatchAffirmInfo1List(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List affirmorList = infoApplyLeaveSer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplyLeaveSer.getCheckorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		modelMap.put("leaveBatchAffirmList", infoApplyLeaveSer.getLeaveBatchAffirmInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyLeaveSer.getLeaveBatchAffirmInfoCnt(request));
		modelMap.put("APPLY_NO",  request.getParameter("APPLY_NO"));
		
		return new ModelAndView("/ess/infoApplyLeave/viewFullApplyBatchAffirmInfo1", modelMap);
	}
	
	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delAttedanceApplyInBatch")
	@ResponseBody
	public Map<String, Object> delLeaveApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplyLeaveSer.delLeaveApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess3211");
				map.put("message", TipMessage.getTipMessage("alert.message.QUXIAOCHENGGONG.b", request));//"取消成功"
				map.put("statusCode", "200");
				map.put("formId", "viewApplyLeaveInfoList");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "取消失败");//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
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
	@RequestMapping(value = "/delLeaveApply")
	@ResponseBody
	public String delLeaveApply(HttpServletRequest request) throws Exception {
		String result = "";
		boolean bol = infoApplyLeaveSer.delLeaveApply(request);
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
	@RequestMapping(value = "/cancelLeaveApply")
	@ResponseBody
	public String cancelLeaveApply(HttpServletRequest request) throws Exception {
		String result = "";
		boolean bol = infoApplyLeaveSer.cancelLeaveApply(request);
		if (bol) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
	
	/**
	 * 申请销假( cancel leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addXiaojiaLeaveApply")
	@ResponseBody
	public String addXiaojiaLeaveApply(HttpServletRequest request) throws Exception {
		String result = "";
		try{
			result = "Y";
			infoApplyLeaveSer.addXiaojiaLeaveApply(request);
		}catch(Exception e){
			e.printStackTrace();
			result = "N";
		}
		
		return result;
	}

	/**
	 * 申请销假( cancel leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addXiaojiaLeaveApplyNoAffirm")
	@ResponseBody
	public String addXiaojiaLeaveApplyNoAffirm(HttpServletRequest request) throws Exception {
		String result = "";
		try{
			result = "Y";
			infoApplyLeaveSer.addXiaojiaLeaveApplyNoAffirm(request);
		}catch(Exception e){
			e.printStackTrace();
			result = "N";
		}
		
		return result;
	}
	

	/**
	 * 申请销假( cancel leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelLeaveApplyNoAffirm")
	@ResponseBody
	public String cancelLeaveApplyNoAffirm(HttpServletRequest request) throws Exception {
		String result = "";
		try{
			result = "Y";
			infoApplyLeaveSer.cancelLeaveApplyNoAffirm(request);
		}catch(Exception e){
			e.printStackTrace();
			result = "N";
		}
		
		return result;
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
		List otDeductTimeList = this.infoApplyLeaveSer.getOtDeductTimeList(request) ;
		List list = infoApplyLeaveSer.getPersonList(request);
		
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("dynamicGroupList", dynamicGroupList);
		modelMap.put("otDeductTimeList", otDeductTimeList);
		modelMap.put("personList", list);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplyLeaveSer.getPersonListCnt(request));
 		
		return new ModelAndView("/ess/infoApplyLeave/viewOtBatchApplyPersonList",
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
		 List list = infoApplyLeaveSer.getPersonList(request);
		modelMap.put("personList", list);
		modelMap.put("codeList", codeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplyLeaveSer
				.getPersonListCnt(request));
 		
		return new ModelAndView("/ess/infoApplyLeave/viewOtBatchApplyPersonListNewList",
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
		modelMap.put("restAnnualLeave", this.infoApplyLeaveSer.retrieveVacationEmpREST(request));
		modelMap.put("restAnnualLeaveQN", this.infoApplyLeaveSer.retrieveVacationEmpRESTQN(request));
		//2013-06-20开始使用的计算剩余调休时数
		modelMap.put("adjustRest", this.infoApplyLeaveSer.getAdjustRestNew(request));
		//2013-06-20之前使用的计算剩余调休时数(保留中)
		//modelMap.put("adjustRest", this.infoApplyLeaveSer.getSurplusAdjustRest(request));
		//个人基本信息中是否有配偶出生年月日
		modelMap.put("SPOUSE_BIRTH", this.infoApplyLeaveSer.getspouseBirth(request));
		return new ModelAndView("/ess/infoApplyLeave/viewLeaveApplyInfo", modelMap);
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
		modelMap.put("personList", infoApplyLeaveSer.getPersonList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplyLeaveSer
				.getPersonListCnt(request));

		return new ModelAndView("/ess/infoApplyLeave/viewLeaveBatchApplyPersonList",
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
		return new ModelAndView("/ess/infoApplyLeave/viewEvectionApplyInfo",
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
		modelMap.put("personList", infoApplyLeaveSer.getPersonList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplyLeaveSer
				.getPersonListCnt(request));

		return new ModelAndView("/ess/infoApplyLeave/viewEvectionBatchApplyPersonList",
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
		return new ModelAndView("/ess/infoApplyLeave/viewEgressionApplyInfo",
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
		modelMap.put("personList", infoApplyLeaveSer.getPersonList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.infoApplyLeaveSer
				.getPersonListCnt(request));

		return new ModelAndView("/ess/infoApplyLeave/viewEgressionBatchApplyPersonList",
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
			result = infoApplyLeaveSer.addPersonalInfoApply(request);
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
			result = infoApplyLeaveSer.addBatchOvertimeApply(request);
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
			result = infoApplyLeaveSer.addBatchLeaveApply(request);
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
			result = infoApplyLeaveSer.addEvectionApply(request);
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
			result = infoApplyLeaveSer.addBatchEvectionApply(request);
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
			result = infoApplyLeaveSer.addEgressionApply(request);
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
			result = infoApplyLeaveSer.addBatchEgressionApply(request);
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
		modelMap.put("empShiftList", this.infoApplyLeaveSer.getEmpShift(request));

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
		List affirmorList = this.infoApplyLeaveSer.getAffirmorList(request);
		modelMap.put("affirmorList", affirmorList);
		return new ModelAndView(
				"/ess/infoApplyLeave/viewAffirmorByPersonIdAndAppCode", modelMap);
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
		modelMap.put("restAnnualLeave", this.infoApplyLeaveSer.retrieveVacationEmpREST(request));
		modelMap.put("restAnnualLeaveQN", this.infoApplyLeaveSer.retrieveVacationEmpRESTQN(request));
		//2013-06-20开始使用的计算剩余调休时数
		modelMap.put("adjustRest", this.infoApplyLeaveSer.getAdjustRestNew(request));
		//2013-06-20之前使用的计算剩余调休时数(保留中)
		//modelMap.put("adjustRest", this.infoApplyLeaveSer.getSurplusAdjustRest(request));
		
		return new ModelAndView("/ess/infoApplyLeave/viewLikeLeaveApplyInfo", modelMap);
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
		modelMap.put("restAnnualLeave", this.infoApplyLeaveSer.retrieveVacationEmpREST(request));
		modelMap.put("restAnnualLeaveQN", this.infoApplyLeaveSer.retrieveVacationEmpRESTQN(request));
		//2013-06-20开始使用的计算剩余调休时数
		modelMap.put("adjustRest", this.infoApplyLeaveSer.getAdjustRestNew(request));
		//2013-06-20之前使用的计算剩余调休时数(保留中)
		//modelMap.put("adjustRest", this.infoApplyLeaveSer.getSurplusAdjustRest(request));
		
		return new ModelAndView("/ess/infoApplyLeave/viewWorkingDaysApplyInfo", modelMap);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/proveFiledialog")
	public ModelAndView proveFiledialog(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
	modelMap.put("FILE_NAME", request.getParameter("FILE_NAME"));
	modelMap.put("FILE_URL", request.getParameter("FILE_URL"));
		return new ModelAndView("/ess/infoApplyLeave/proveFiledialog", modelMap);
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
		
		return new ModelAndView("/ess/infoApplyLeave/proveFileUploading",modelMap);
		
	}
	
	@RequestMapping("downloadFile")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data,charset=UTF-8");
		
		String url=new String(request.getParameter("fileName").getBytes("ISO-8859-1"),"UTF-8");
//		String fileName=request.getRealPath("")+request.getParameter("fileName");
		String fileNameAb = URLEncoder.encode(request.getParameter("file"), "UTF-8");
		String fileName=request.getRealPath("")+url;
		String fileNameA = new String(request.getParameter("file").getBytes("ISO-8859-1"),"UTF-8");
//		response.setHeader("Content-Disposition", "attachment;fileName="+fileNameA);
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ URLEncoder.encode(request.getParameter("file"), "UTF-8"));
		
		try {
			File file=new File(fileName);
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

		modelMap.put("VACATIONSTANDAR", infoApplyLeaveSer.getviewVacationStandard(request));
		//modelMap.put("personalInfo", this.personInfoSer.getHrPersonInfo(request));
		return new ModelAndView("/ess/infoApplyLeave/viewVacationStandard",modelMap);
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
		return new ModelAndView("/ess/infoApplyLeave/viewEvectionApplyInfoNewOne",
				modelMap);
	}
	
	/**
	 * 取得法规政策
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getZhengce")
	@ResponseBody
	public void getZhengce(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");

		String returnString = this.infoApplyLeaveSer.getZhengce(request);

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
	@RequestMapping(value = "/getShenqingshichang")
	@ResponseBody
	public Map getShenqingshichang(HttpServletRequest request,HttpServletResponse response) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		String ITEM_NO = request.getParameter("ITEM_NO");
		
		String returnString = this.infoApplyLeaveSer.getShenqingshichang(request);
		
		float times = Float.parseFloat(returnString);
		String timeStr = "";
		if ("141442".equals(ITEM_NO)||"141441".equals(ITEM_NO)||"14013783".equals(ITEM_NO)) {
				timeStr = times + "分";
		}else {
			if(times >= 8 && times%8==0){
				timeStr = (int)Math.floor(times/8) + "天";
			}else if(times > 8 && times%8!=0){
				timeStr = (int)Math.floor(times/8) + "天" + times%8 + "小时";
			}else if(times < 8){
				timeStr = times + "小时";
			}
		}
			
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", "200");
		map.put("message", TipMessage.getTipMessage(
				"alert.message.update_success", request));
		map.put("length", returnString);
		map.put("lengthStr", timeStr);
		return map;
	}
	
	
	/**
	 * 取休假，年假使用年况
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getLeaveDispalydaoxiuOrnianjMap")
	@ResponseBody
	public Map getLeaveDispalydaoxiuOrnianjMap(HttpServletRequest request,HttpServletResponse response) throws Exception {

		LinkedHashMap map = this.infoApplyLeaveSer.getLeaveDispalydaoxiuOrnianjMap(request);
		
		map.put("statusCode", "200");
		map.put("message", TipMessage.getTipMessage(
				"alert.message.update_success", request));
		return map;
	}
	
	
	/**
	 * 批量取申请时长
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShenqingshichangBatch")
	@ResponseBody
	public Map getShenqingshichangBatch(HttpServletRequest request,HttpServletResponse response) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int times2 = 0;
		String from_timeString = request.getParameter("leavefromtime");
		String to_timeString = request.getParameter("leavetotime");
		String fromDateString = from_timeString.substring(0, 10);
		String toDateString = to_timeString.substring(0, 10);
		if (!fromDateString.equals(toDateString)) {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Long s2= (sdf2.parse(toDateString).getTime() -sdf2.parse(fromDateString).getTime());
			 times2=(int) (s2/3600/24);
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date fromDate=sdf1.parse(from_timeString);
		Date toDate=sdf1.parse(to_timeString);
        Long s  = (toDate.getTime()-fromDate.getTime())/1000;
        float times=s/3600;
		
		
		String timeStr = "";
			if(times >= 8 && times%8==0){
				timeStr = (int)Math.floor(times/8) + "天";
			}else if(times > 8 && times%8!=0){
				timeStr = (int)Math.floor(times/8) + "天" + times%8 + "小时";
			}else if(times < 8){
				timeStr = times + "小时";
			}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", "200");
		map.put("message", TipMessage.getTipMessage(
				"alert.message.update_success", request));
		map.put("length", times2*8+times);
		map.put("lengthStr", times2+"天"+timeStr);
		return map;
	}
	/**
	 * 添加时获取考勤信息
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAttendanceInformation")
	@ResponseBody
	public Map getAttendanceInformation(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LinkedHashMap<String,String> returnMap= (LinkedHashMap<String, String>) infoApplyLeaveSer.getAttendanceInformation(request);
		return returnMap;
	}
	/**
	 * 添加时获取加班信息
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOTSSTInformation")
	@ResponseBody
	public Map getOTSSTInformation(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LinkedHashMap<String,String> returnMap= (LinkedHashMap<String, String>) infoApplyLeaveSer.getOTSSTInformation(request);
		return returnMap;
	}
	/**
	 * 添加时获取倒休信息
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAJTSTOInformation")
	@ResponseBody
	public Map getAJTSTOInformation(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LinkedHashMap<String,String> returnMap= (LinkedHashMap<String, String>) infoApplyLeaveSer.getAJTSTOInformation(request);
		return returnMap;
	}
	
	/**
	 * 添加时获取加班信息
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOTTSTOInformation")
	@ResponseBody
	public Map getOTTSTOInformation(HttpServletRequest request,HttpServletResponse response) throws Exception {
		LinkedHashMap<String,String> returnMap= (LinkedHashMap<String, String>) infoApplyLeaveSer.getOTTSTOInformation(request);
		return returnMap;
	}
	
	/**
	 * 取申请时长
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLeaveDate")
	@ResponseBody
	public Map getLeaveDate(HttpServletRequest request,HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String apply_type = request.getParameter("apply_type");
		String currentYear = DateUtil.formatDate(new Date(), "yyyy");
		
		if("482".equals(apply_type) || "14013811".equals(apply_type) ||
			"14013812".equals(apply_type) || "27".equals(apply_type)|| "16415".equals(apply_type) 
			|| "14013809".equals(apply_type)){
			String returnString = this.infoApplyLeaveSer.getSex(request);
			//1325:女；1326：男
			//14013811:集体哺乳假,482:产前检查假;27:产假；14013812：产假1 16415:哺乳假；14013809：护理假
			if(!"1325".equals(returnString)&&"14013811".equals(apply_type)){
				map.put("statusCode", "300");
				map.put("message", "哺乳集体假仅限女士申请。");
			}else if("1325".equals(returnString)&&"14013811".equals(apply_type)){
				map.put("statusCode", "200");
			}
			
			if(!"1325".equals(returnString)&&"482".equals(apply_type)){
				map.put("statusCode", "300");
				map.put("message", "产期检查假仅限女士申请。");
			}else if("1325".equals(returnString)&&"482".equals(apply_type)){
				map.put("statusCode", "200");
			}
			
			if(!"1325".equals(returnString)&&"27".equals(apply_type)){
				map.put("statusCode", "300");
				map.put("message", "产假仅限女士申请。");
			}else if("1325".equals(returnString)&&"27".equals(apply_type)){
				map.put("statusCode", "200");
			}
			
			if(!"1325".equals(returnString)&&"14013812".equals(apply_type)){
				map.put("statusCode", "300");
				map.put("message", "产假1仅限女士申请。");
			}else if("1325".equals(returnString)&&"14013812".equals(apply_type)){
				map.put("statusCode", "200");
			}
			if(!"1325".equals(returnString)&&"16415".equals(apply_type)){
				map.put("statusCode", "300");
				map.put("message", "哺乳假仅限女士申请。");
			}else if("1325".equals(returnString)&&"16415".equals(apply_type)){
				map.put("statusCode", "200");
			}
			if(!"1326".equals(returnString)&&"14013809".equals(apply_type)){
				map.put("statusCode", "300");
				map.put("message", "护理假仅限男士申请。");
			}else if("1326".equals(returnString)&&"14013809".equals(apply_type)){
				map.put("statusCode", "200");
			}
			
			
		}else{
			map.put("statusCode", "200");
		}
		return map;
	}
	/**
	 * 获取选中数据的决裁状态
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getChechedAffrim")
	@ResponseBody
	public Map getChechedAffrim(HttpServletRequest request,HttpServletResponse response) throws Exception {

        AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		String returnString = this.infoApplyLeaveSer.getChechedAffrim(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", "200");
		map.put("message", TipMessage.getTipMessage(
				"alert.message.update_success", request));
		map.put("apply_affrim", returnString);
		return map;
	}
	/**
	 * 获取前一天的决裁状态
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getYesBefAffrimNo")
	@ResponseBody
	public Map getYesBefAffrimNo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		String returnString = this.infoApplyLeaveSer.getYesBefAffrimNo(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", "200");
		map.put("message", TipMessage.getTipMessage(
				"alert.message.update_success", request));
		map.put("affrimNo", returnString);
		return map;
	}
	/**
	 * 获取这个人在申请间间内是否进行了考勤申请
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getApplyCountYN")
	@ResponseBody
	public Map getApplyCountYN(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		int returnString = this.infoApplyLeaveSer.getApplyCountYN(request);
		String applyCountYN = "";
	    if (returnString == 0) {
	    	applyCountYN = "N";
		}else {
			applyCountYN = "Y";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("applyCountYN", applyCountYN);
		return map;
	}
	
	/**
	 * 获取这个人在申请间间内是否进行了考勤申请
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getApplyLOCKYN")
	@ResponseBody
	public Map getApplyLOCKYN(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		int returnString = this.infoApplyLeaveSer.getApplyLOCKYN(request);
		String LOCKYN = "";
	    if (returnString == 0) {
	    	LOCKYN = "N";
		}else {
			LOCKYN = "Y";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("LOCKYN", LOCKYN);
		return map;
	}
	/**
	 * 
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOTLOCKYN")
	@ResponseBody
	public Map getOTLOCKYN(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		int returnString = this.infoApplyLeaveSer.getApplyOTLOCKYN(request);
		String OTLOCKYN = "";
		if (returnString == 0) {
			OTLOCKYN = "N";
		}else {
			OTLOCKYN = "Y";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("OTLOCKYN", OTLOCKYN);
		return map;
	}
	
	/**
	 * 获取选中人的性别
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getChechedSex")
	@ResponseBody
	public Map getChechedSex(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap<String,String> returnMap= (LinkedHashMap<String, String>) this.infoApplyLeaveSer.getChechedSex(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", "200");
		map.put("message", TipMessage.getTipMessage(
				"alert.message.update_success", request));
		map.put("sex", returnMap.get("SEXCODE"));
		map.put("LOCAL_NAME", returnMap.get("LOCAL_NAME"));
		return map;
	}

	@RequestMapping(value = "/getTrainLocalName")
	@ResponseBody
	public Map getTrainLocalName(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap<String,String> returnMap= (LinkedHashMap<String, String>) this.infoApplyLeaveSer.getTrainLocalName(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", "200");
		map.put("message", TipMessage.getTipMessage(
				"alert.message.update_success", request));
		map.put("LOCAL_NAME", returnMap.get("LOCAL_NAME"));
		return map;
	}
	/**
	 * 获取选中人的性别
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getChechedItemName")
	@ResponseBody
	public Map getChechedItemName(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		String returnString = this.infoApplyLeaveSer.getChechedItemName(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", "200");
		map.put("message", TipMessage.getTipMessage(
				"alert.message.update_success", request));
		map.put("itemName", returnString);
		return map;
	}
	/**
	 * 附件上传
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-12 下午4:18:11 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload")
	public ModelAndView upload(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		
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
//		String PARAMDATANO=multipartRequest.getParameter("seach_PARAMDATANO").toString();
//		String PARAMDATANO = String.valueOf(System.currentTimeMillis());
		
		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");     
		 /**构建图片保存的目录**/    
		 String logoPathDir = "/resources/temp/apply/"+"applyleave/"+PERSON_ID;// dateformat.format(new Date());     
		 /**得到图片保存目录的真实路径**/    
		 String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);     
		/**根据真实路径创建目录**/    
		 File logoSaveFile = new File(logoRealPathDir);     
		 if(!logoSaveFile.exists())     
		logoSaveFile.mkdirs();           
		/**页面控件的文件流**/    
		MultipartFile multipartFile = multipartRequest.getFile("file");      
		/**获取文件的后缀**/    
		String suffix = multipartFile.getOriginalFilename().substring  
		(multipartFile.getOriginalFilename().lastIndexOf("."));     
		 /**使用UUID生成文件名称**/    
		// String logImageName = request.getParameter("PARAM_DATA_NO").toString()+ suffix;
		//构建文件名称     
//		String logImageNameA = multipartFile.getOriginalFilename();  
//		String logImageName = logImageNameA+suffix;
		String logImageName = multipartFile.getOriginalFilename();
		/**拼成完整的文件保存路径加文件**/    
		String fileName = logoRealPathDir + File.separator   + logImageName;                
		File file = new File(fileName);           
		try {     
		  multipartFile.transferTo(file);  
//		  LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
//			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//			map.put("fileName", logoPathDir+"/"+logImageName); //路径 
//			map.put("PARAMDATANO", PARAMDATANO); //对应申请的编号 
//			map.put("CREATED_BY", admin.getAdminID());
//			map.put("ORIGINAL_NAME", logImageNameA);
		//	request.setAttribute("fileName", fileName);
		//	int result = this.insuranceInputItemSer.insertAccessory(map);
			modelMap.put("sign", 1);
		 } catch (IllegalStateException e) {     
		 e.printStackTrace(); 
		 modelMap.put("sign", -1);
		} catch (IOException e) {            
		 e.printStackTrace();  
		 modelMap.put("sign", -1);
		 }   
		
		return new ModelAndView("/ess/infoApplyLeave/upload",modelMap);
		
	}
	
	/**
	 * 批量考勤休假Leave(/view Apply Leave Batch Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyAttenanceBatchInfoList")
	public ModelAndView viewApplyAttenanceBatchInfoList (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)  throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String type = request.getParameter("type");
		List nullLeaveAffirmList = new ArrayList();
		String firstPage = request.getParameter("firstPage");
		if (firstPage == null || "".equals(firstPage)) {
			if(type !=null && !"".equals(type)){
				 nullLeaveAffirmList = infoApplyLeaveSer.viewAddAttendanceApplyInfoForBatch(request);
			}else{
				 nullLeaveAffirmList = infoApplyLeaveSer.getNullBatchLeaveAffirmInfoList(request);
			}
		}
		modelMap.put("nullLeaveAffirmList",nullLeaveAffirmList );
		modelMap.put("nullLeaveAffirmListCnt",nullLeaveAffirmList==null ? 0: nullLeaveAffirmList.size());
		/*if (nullLeaveAffirmList.size() > 0) {
			for (int i = 0; i < nullLeaveAffirmList.size(); i++) {
				if (nullLeaveAffirmList.get(i) != null && !nullLeaveAffirmList.get(i).equals("")) {
						Map applyMap = (Map) nullLeaveAffirmList.get(i);// 取出部门ID
						applyMap.put("fileList", infoApplyLeaveSer.getFileList(applyMap));
				}
			}
		}*/
		modelMap.put("LEAVE_TYPE_CODE" , JsonUtil.writeInternal(essEmpInfoSer.getCodeList("21", request)));
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				/*c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("END_DATE",last);*/
				modelMap.put("START_DATE", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				modelMap.put("END_DATE", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
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
		return new ModelAndView("/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoList", modelMap);
	}
	
	/**
	 * 批量考勤休假Leave任意选择审批者(/view Apply Leave Batch Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyAttBatchByAnyApproverList")
	public ModelAndView viewApplyAttBatchByAnyApproverList (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)  throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String type = request.getParameter("type");
		List nullLeaveAffirmList = new ArrayList();
		if(type !=null && !"".equals(type)){
			 nullLeaveAffirmList = infoApplyLeaveSer.viewAddAttendanceApplyInfoForBatch(request);
		}else{
			 nullLeaveAffirmList = infoApplyLeaveSer.getNullBatchLeaveAffirmInfoList(request);
		}
		modelMap.put("nullLeaveAffirmList",nullLeaveAffirmList );
		modelMap.put("nullLeaveAffirmListCnt",nullLeaveAffirmList==null ? 0: nullLeaveAffirmList.size());
		if (nullLeaveAffirmList.size() > 0) {
			for (int i = 0; i < nullLeaveAffirmList.size(); i++) {
				if (nullLeaveAffirmList.get(i) != null && !nullLeaveAffirmList.get(i).equals("")) {
						Map applyMap = (Map) nullLeaveAffirmList.get(i);// 取出部门ID
						applyMap.put("fileList", infoApplyLeaveSer.getFileList(applyMap));
				}
			}
		}
		modelMap.put("LEAVE_TYPE_CODE" , JsonUtil.writeInternal(essEmpInfoSer.getCodeList("21", request)));
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
		String spacing = "30";
		while (today.before(tomorrow) || today.equals(tomorrow)) {
			
			String time = sdf.format(today.getTime());
			timeStr = timeStr + "'CODENO':'"+time+"','CODENAME':'"+time+"'},{";
			today.add(Calendar.MINUTE, Integer.parseInt(spacing));
			
		}
		timeStr = timeStr +"}]";
		modelMap.put("TIME_STR", timeStr);
		
		return new ModelAndView("/ess/infoApplyAttendance/viewApplyAttBatchByAnyApproverList", modelMap);
	}
	
	/**
	 * 批量考勤休假Leave(/view Apply Leave Batch Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyAttenanceBatchInfoHAE")
	public ModelAndView viewApplyAttenanceBatchInfoHAE (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)  throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String type = request.getParameter("deleteYN");
		List nullLeaveAffirmList = new ArrayList();
		if("Y".equals(type)){
			this.infoApplyLeaveSer.delAttendanceExForBatchInfoListHAE(request);
		}else{
			 nullLeaveAffirmList = infoApplyLeaveSer.viewAddAttendanceApplyInfoForBatchHAE(request);
		}
		modelMap.put("nullLeaveAffirmList",nullLeaveAffirmList );
		modelMap.put("nullLeaveAffirmListCnt",nullLeaveAffirmList==null ? 0: nullLeaveAffirmList.size());
		/*if (nullLeaveAffirmList.size() > 0) {
			for (int i = 0; i < nullLeaveAffirmList.size(); i++) {
				if (nullLeaveAffirmList.get(i) != null && !nullLeaveAffirmList.get(i).equals("")) {
						Map applyMap = (Map) nullLeaveAffirmList.get(i);// 取出部门ID
						applyMap.put("fileList", infoApplyLeaveSer.getFileList(applyMap));
				}
			}
		}*/
		modelMap.put("LEAVE_TYPE_CODE" , JsonUtil.writeInternal(essEmpInfoSer.getCodeList("21", request)));
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
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
		String spacing = "30";
		while (today.before(tomorrow) || today.equals(tomorrow)) {
			
			String time = sdf.format(today.getTime());
			timeStr = timeStr + "'CODENO':'"+time+"','CODENAME':'"+time+"'},{";
			today.add(Calendar.MINUTE, Integer.parseInt(spacing));
			
		}
		timeStr = timeStr +"}]";
		modelMap.put("TIME_STR", timeStr);
		
		return new ModelAndView("/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoHAE", modelMap);
	}
	
	/**
	 * 生产职批量考勤申请明细
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyAttenanceBatchInfoHAEList")
	public ModelAndView viewApplyAttenanceBatchInfoHAEList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		String firstFlag = request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if ("Y".equals(firstFlag)) {
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
		
		List applyAttenanceBatchInfoHAEList = new ArrayList();
		     applyAttenanceBatchInfoHAEList = infoApplyLeaveSer.viewApplyAttenanceBatchInfoHAEList(request);
		     
		modelMap.put("applyAttenanceBatchInfoHAEList", applyAttenanceBatchInfoHAEList);
		//List viewArShiftRecordCheckList = this.arShiftGroupHistorySer.getArShiftRecordCheckList(request);

		//modelMap.put("viewArShiftRecordCheckList", viewArShiftRecordCheckList);
		modelMap.put("applyAttenanceBatchInfoHAEListSize", applyAttenanceBatchInfoHAEList == null ? 0:applyAttenanceBatchInfoHAEList.size());
		return new ModelAndView("/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoHAEList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyAttenanceBatchInfoHAEDetail")
	public ModelAndView viewApplyAttenanceBatchInfoHAEDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("viewApplyAttenanceBatchInfoHAEDetail", infoApplyLeaveSer.getApplyAttenanceBatchInfoHAEDetail(request));
		return new ModelAndView("/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoHAEDetail", modelMap);
	}
	
	/**
	 * 生产职批量加班申请明细
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyOTBatchInfoHAEList")
	public ModelAndView viewApplyOTBatchInfoHAEList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String firstFlag = request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if ("Y".equals(firstFlag)) {
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
		List applyOTBatchInfoHAEList = new ArrayList();
		     applyOTBatchInfoHAEList = infoApplyLeaveSer.viewApplyOTBatchInfoHAEList(request);
		     
		modelMap.put("applyOTBatchInfoHAEList", applyOTBatchInfoHAEList);
		//List viewArShiftRecordCheckList = this.arShiftGroupHistorySer.getArShiftRecordCheckList(request);

		//modelMap.put("viewArShiftRecordCheckList", viewArShiftRecordCheckList);
		modelMap.put("applyOTBatchInfoHAEListSize", applyOTBatchInfoHAEList == null ? 0:applyOTBatchInfoHAEList.size());
		return new ModelAndView("/ess/infoApplyAttendance/viewApplyOTBatchInfoHAEList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyOTBatchInfoHAEDetail")
	public ModelAndView viewApplyOTBatchInfoHAEDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("viewApplyOTBatchInfoHAEDetail", infoApplyLeaveSer.getApplyOTBatchInfoHAEDetail(request));
		return new ModelAndView("/ess/infoApplyAttendance/viewApplyOTBatchInfoHAEDetail", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getLeaveTypeCode")
	@ResponseBody
	public Map getLeaveTypeCode(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)  throws Exception {
		modelMap.put("LEAVE_TYPE_CODE" , JsonUtil.writeInternal(essEmpInfoSer.getCodeList("21", request)));//有问题
		return modelMap;
	}
	
	/**
	 * 批量考勤休假Leave(/view Apply Leave Batch Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAttendanceExForBatchInfoList")
	public ModelAndView viewAttendanceExForBatchInfoList (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)  throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
			String firstFlag = request.getParameter("firstFlag");
			if (firstFlag ==null || "".equals(firstFlag)) {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
				Calendar c = Calendar.getInstance();    
				if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
					//获取当前月第一天：
					c.add(Calendar.MONTH, 0);
					//设置为1号,当前日期既为本月第一天 
					c.set(Calendar.DAY_OF_MONTH,1);
					String first = format.format(c.getTime());
					modelMap.put("START_DATE",first);
					c.add(Calendar.MONTH, 0);
					//设置为1号,当前日期既为本月第一天 
					c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
					String last = format.format(c.getTime());
					modelMap.put("END_DATE",last);
					
					int attendanceExCnt = infoApplyLeaveSer.getAttendanceExInfoListCnt(request,modelMap);
					if(attendanceExCnt>2000){
						Calendar calendar = Calendar.getInstance();   
						calendar.add(Calendar.DATE, -1); //得到前一天
						first = format.format(calendar.getTime());
						last = format.format(calendar.getTime());
						modelMap.put("START_DATE",first);
						modelMap.put("END_DATE",last);
					}
				}
			}
		List attendanceExForBatchInfoList = infoApplyLeaveSer.getAttendanceExForBatchInfoList(request,modelMap);
		modelMap.put("attendanceExForBatchInfoList",attendanceExForBatchInfoList );
		modelMap.put("attendanceExForBatchInfoListCnt",attendanceExForBatchInfoList==null ? 0: attendanceExForBatchInfoList.size());

		return new ModelAndView("/ess/infoApplyAttendance/viewAttendanceExForBatchInfoList", modelMap);
	}
	/**
	 * 休假Leave(临时)(/view Apply Leave Batch Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyLeaveBatchTempInfoList")
	public ModelAndView viewApplyLeaveBatchTempInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
//		临时页面提交数据
		modelMap.put("leaveAffirmTempList", infoApplyLeaveSer.getLeaveAffirmBatchTempInfoList(request));
		
		return new ModelAndView("/ess/infoApplyLeave/viewApplyLeaveBatchTempInfoList", modelMap);
	}

	/**
	 * 休假批量申请导入模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchLeaveModule")
	public void exportBatchLeaveModule(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号");
		aliasNameList.add("申请人");
		aliasNameList.add("开始日期");
		aliasNameList.add("开始时间");
		aliasNameList.add("结束日期"); 
		aliasNameList.add("结束时间"); 
		aliasNameList.add("休假类型");
		aliasNameList.add("申请事由");

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "12000003");
		map.put("CELL1", "张XX");
		map.put("CELL2", "2014-07-10");
		map.put("CELL3", "08:00");
		map.put("CELL4", "2014-07-10");
		map.put("CELL5", "17:00");
		map.put("CELL6", "事假");
		map.put("CELL7", "有事");
		list.add(map);
		
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		mapNameList.add("休假类型参考");
		mapList.add(" SELECT distinct NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U,SY_CODE_PARAM SY WHERE T.ACTIVITY = 1 AND T.CODE_NO = U.NO(+) AND SY.CODE_NO = T.CODE_NO AND SY.CPNY_ID = '" + admin.getCpnyId() + "' AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO in ('21','18','16201') and T.CODE_NO not in ('218112','16415') ");
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"ess_leave");
	}

	/**
	 * 休假批量申请数据导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchLeaveData")
	public void exportBatchLeaveData(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号");
		aliasNameList.add("申请人");
		aliasNameList.add("开始时间");
		aliasNameList.add("结束时间"); 
		aliasNameList.add("休假类型");
		aliasNameList.add("申请事由");
		aliasNameList.add("验证结果");

		List list = new ArrayList();

		List paEssLeaveTempList = this.infoApplyLeaveSer.getEssLeaveTempList(request);
		for(int i=0;i<paEssLeaveTempList.size();i++){
			LinkedHashMap dataMap = (LinkedHashMap)paEssLeaveTempList.get(i);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", dataMap.get("EMPID"));
			map.put("CELL1", dataMap.get("APPLY_NAME"));
			map.put("CELL2", dataMap.get("LEAVE_FROM_TIME"));
			map.put("CELL3", dataMap.get("LEAVE_TO_TIME"));
			map.put("CELL4", dataMap.get("LEAVE_TYPE"));
			map.put("CELL5", dataMap.get("LEAVE_REASON"));
			map.put("CELL6", dataMap.get("UPLOAD_ERROR_MSG") == null ? "" : dataMap.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		mapNameList.add("休假类型参考");
		mapList.add(" SELECT distinct NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U,SY_CODE_PARAM SY WHERE T.ACTIVITY = 1 AND T.CODE_NO = U.NO(+) AND SY.CODE_NO = T.CODE_NO AND SY.CPNY_ID = '" + admin.getCpnyId() + "' AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO in ('21','18','16201') and T.CODE_NO not in ('218112','16415') ");
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"ess_leave");
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
	@RequestMapping(value = "/addTempApplyLeaveInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addTempApplyLeaveInfo(
			HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		
		String stype = request.getParameter("stype");
		if("2".equals(stype)){
//			删除临时表
			
			int result = 0;
			try {
				result = this.infoApplyLeaveSer.delTempLeaveApply(request);
				if (result == 1) {
					map.put("message", "删除成功");//
					map.put("statusCode", "200");
//					map.put("callbackType", "closeCurrent");
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
//					map.put("callbackType", "closeCurrent");
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
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
/*	@RequestMapping(value = "/delAttendanceApplyInBatchForBatch")
	@ResponseBody
	public Map<String, Object> delLeaveApplyInBatchForBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String op_flag = request.getParameter("OP_FLAG");
		String msg = "添加";
		if("0".equals(op_flag)){
		    msg = "删除";
		}
		if("1".equals(op_flag)){
			msg = "提交";
		}
		try {
			result = infoApplyLeaveSer.delLeaveApplyInBatchForBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0246");
				map.put("message", msg + "成功！");//"批量删除休假申请决裁成功!"
				map.put("statusCode", "200");
				map.put("formId", "viewApplyLeaveBatchInfoList");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
			map.put("formId", "viewApplyLeaveBatchInfoList");
		} catch (Exception e) {
			map.put("message", msg + "失败！");//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
			map.put("formId", "viewApplyLeaveBatchInfoList");
		}
		map.put("result", result);
		return map;
	}*/
	
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
	@RequestMapping(value = "/viewImportExcelEssLeaveDataListess0240")
	public ModelAndView viewImportExcelEssLeaveDataListess0240(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paEssLeaveTempList = this.infoApplyLeaveSer.getEssLeaveTempList(request);
		int paEssLeaveTempCnt = this.infoApplyLeaveSer.getEssLeaveTempCnt(request , "T");
		int errorCnt = this.infoApplyLeaveSer.getEssLeaveTempCnt(request , "E");
		
		modelMap.put("paEssLeaveTempList", paEssLeaveTempList);
		modelMap.put("paEssLeaveTempCnt", paEssLeaveTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paEssLeaveTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paEssLeaveTempCnt);
		return new ModelAndView("/pa/tempsale/viewImportExcelEssLeaveDataListess0240", modelMap);
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
	@RequestMapping(value = "/viewImportExcelEssLeaveDataList")
	public ModelAndView viewImportExcelEssLeaveDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paEssLeaveTempList = this.infoApplyLeaveSer.getEssLeaveTempList(request);
		int paEssLeaveTempCnt = this.infoApplyLeaveSer.getEssLeaveTempCnt(request , "T");
		int errorCnt = this.infoApplyLeaveSer.getEssLeaveTempCnt(request , "E");
		
		modelMap.put("paEssLeaveTempList", paEssLeaveTempList);
		modelMap.put("paEssLeaveTempCnt", paEssLeaveTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paEssLeaveTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paEssLeaveTempCnt);
		return new ModelAndView("/pa/tempsale/viewImportExcelEssLeaveDataList", modelMap);
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
	@RequestMapping(value = "/submitImportExcelEssLeaveEmpData")
	@ResponseBody
	public Map submitImportExcelEssLeaveEmpData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.infoApplyLeaveSer.submitImportExcelEssLeaveEmpData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功
			jo.put("navTabId", request.getParameter("LEAVE_TYPE"));
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
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
	@RequestMapping(value = "/getAffirmList")
	@ResponseBody
	public Map getAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String applyTypeNo = request.getParameter("applyParentType");
		String personId = request.getParameter("personId");
		String applyTypeCode = request.getParameter("applyType");
		String applyLength = request.getParameter("applyLength");
		String language = admin.getLanguage();
		List list = this.infoApplySer.getAffirmorListByString(applyTypeNo, personId, 
						this.infoApplySer.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),applyTypeCode), applyLength,language);
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
	
	/**
	 * 休假、销假 子类型联动 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCodeList")
	@ResponseBody
	public Map getCodeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = this.infoApplySer.getCodeList(request);
		if (list != null && list.size() > 0) {
			map.put("codeList", list);
			map.put("codeListCnt", list.size());
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
	

	/**
	 * 附件上传
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lwei liangwei@ait.net.cn
	 * @date 2013-9-12 下午4:18:11
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadBatch")
	public ModelAndView uploadBatch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String personId = request.getParameter("PERSON_ID");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		if (!(request instanceof MultipartHttpServletRequest)
				&& multipartResolver.isMultipart(request)) {
			try {
				multipartResolver.setMaxUploadSize(524288000);
				multipartRequest = multipartResolver.resolveMultipart(request);
			} catch (MaxUploadSizeExceededException e) {
				return null;
			}
		} else if (request instanceof MultipartHttpServletRequest) {
			multipartRequest = (MultipartHttpServletRequest) request;
		} else {
			return null;
		}
		String uuid = String.valueOf(UUID.randomUUID());

		// MultipartHttpServletRequest multipartRequest =
		// (MultipartHttpServletRequest) request;

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
		/** 构建附件保存的目录 **/
		String logoPathDir = "/resources/temp/apply/applyleave/" + personId;// dateformat.format(new Date());
		/** 得到附件保存目录的真实路径 **/
		String logoRealPathDir = request.getSession().getServletContext()
				.getRealPath(logoPathDir);
		/** 根据真实路径创建目录 **/
		File logoSaveFile = new File(logoRealPathDir);
		if (!logoSaveFile.exists())
			logoSaveFile.mkdirs();
		/** 页面控件的文件流 **/
		MultipartFile multipartFile = multipartRequest.getFile("file");
		// 构建文件名称
		String logImageName = multipartFile.getOriginalFilename();
		/** 获取文件的后缀 **/
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
		/** 使用UUID生成文件名称 **/
		String fileName = logoRealPathDir + File.separator + uuid + suffix;
		/** 拼成完整的文件保存路径加文件 **/
		File file = new File(fileName);
		try {
			multipartFile.transferTo(file);
			modelMap.put("sign", 1);
			modelMap.put("logImageName", logImageName);
			modelMap.put("fileUrl", uuid + suffix);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			modelMap.put("sign", -1);
		} catch (IOException e) {
			e.printStackTrace();
			modelMap.put("sign", -1);
		}
		return new ModelAndView("/ess/infoApplyLeave/uploadBatch", modelMap);
	}
	

	/**
	 * 附件上传(按perosn_id分组)
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lwei liangwei@ait.net.cn
	 * @date 2013-9-12 下午4:18:11
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadBatch2")
	public ModelAndView uploadBatch2(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String personId = request.getParameter("PERSON_ID");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		if (!(request instanceof MultipartHttpServletRequest)
				&& multipartResolver.isMultipart(request)) {
			try {
				multipartResolver.setMaxUploadSize(524288000);
				multipartRequest = multipartResolver.resolveMultipart(request);
			} catch (MaxUploadSizeExceededException e) {
				return null;
			}
		} else if (request instanceof MultipartHttpServletRequest) {
			multipartRequest = (MultipartHttpServletRequest) request;
		} else {
			return null;
		}
		String uuid = String.valueOf(UUID.randomUUID());

		// MultipartHttpServletRequest multipartRequest =
		// (MultipartHttpServletRequest) request;

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
		/** 构建附件保存的目录 **/
		String logoPathDir = "/resources/temp/apply/applyleave/" + personId;// dateformat.format(new Date());
		/** 得到附件保存目录的真实路径 **/
		String logoRealPathDir = request.getSession().getServletContext()
				.getRealPath(logoPathDir);
		/** 根据真实路径创建目录 **/
		File logoSaveFile = new File(logoRealPathDir);
		if (!logoSaveFile.exists())
			logoSaveFile.mkdirs();
		/** 页面控件的文件流 **/
		MultipartFile multipartFile = multipartRequest.getFile("file");
		// 构建文件名称
		String logImageName = multipartFile.getOriginalFilename();
		/** 获取文件的后缀 **/
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
		/** 使用UUID生成文件名称 **/
		String fileName = logoRealPathDir + File.separator + uuid + suffix;
		/** 拼成完整的文件保存路径加文件 **/
		File file = new File(fileName);
		try {
			multipartFile.transferTo(file);
			modelMap.put("sign", 1);
			modelMap.put("logImageName", logImageName);
			modelMap.put("fileUrl", uuid + suffix);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			modelMap.put("sign", -1);
		} catch (IOException e) {
			e.printStackTrace();
			modelMap.put("sign", -1);
		}
		return new ModelAndView("/ess/infoApplyLeave/uploadBatch2", modelMap);
	}
	
	/**
	 * 员工班次结束时间查询
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShiftTime")
	@ResponseBody
	public void getShiftTime(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");

		String returnString = this.infoApplyLeaveSer.getShiftTime(request);

		PrintWriter out = response.getWriter();
        
	    out.println(JsonUtil.writeInternal(returnString));
	        
		out.flush();
		out.close();
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
	@RequestMapping(value = "/modifyAffirmorForBatchLeave")
	@ResponseBody
	public Map modifyAffirmorForBatchLeave(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.modifyAffirmorForBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0246");
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
	 * 加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAssigmentLeaveApply")
	@ResponseBody
	public Map addAssigmentLeaveApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String AFFIRM_FLAG = request.getParameter("AFFIRM_FLAG");
		try {
			int result = infoApplyLeaveSer.addLeaveAssigment(request);
			if (result == 1) {
				map.put("navTabId", "ess0240");
				if(infoApplyLeaveSer.isZhuisuLeave(request)){
					map.put("message", "发令成功，申请期间已经关闭，该申请将在下月反映。");
				}else{
					map.put("message", "发令成功");
				}
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
				map.put("formId", "viewApplyLeaveInfoList");
			}else{
				map.put("statusCode", "300");
				if("-1".equals(AFFIRM_FLAG) ){
					map.put("message", "暂存失败");
				}else{
					map.put("message", "发令失败");
				}
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			if("-1".equals(AFFIRM_FLAG) ){
				map.put("message", "暂存发令Leave出错,请重新发令");
			}else{
				map.put("message", "发令Leave出错,请重新发令");
			}
			map.put("statusCode", "300");
		}
		return map;
	}
	
	

	/**
	 * 附件上传
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lwei liangwei@ait.net.cn
	 * @date 2013-9-12 下午4:18:11
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadBatchTemp")
	public ModelAndView uploadBatchTemp(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		if (!(request instanceof MultipartHttpServletRequest)
				&& multipartResolver.isMultipart(request)) {
			try {
				multipartResolver.setMaxUploadSize(524288000);
				multipartRequest = multipartResolver.resolveMultipart(request);
			} catch (MaxUploadSizeExceededException e) {
				return null;
			}
		} else if (request instanceof MultipartHttpServletRequest) {
			multipartRequest = (MultipartHttpServletRequest) request;
		} else {
			return null;
		}
		String uuid = String.valueOf(UUID.randomUUID());

		// MultipartHttpServletRequest multipartRequest =
		// (MultipartHttpServletRequest) request;

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
		/** 构建附件保存的目录 **/
		String logoPathDir = "/resources/temp/apply/tempsale";// dateformat.format(new Date());
		/** 得到附件保存目录的真实路径 **/
		String logoRealPathDir = request.getSession().getServletContext()
				.getRealPath(logoPathDir);
		/** 根据真实路径创建目录 **/
		File logoSaveFile = new File(logoRealPathDir);
		if (!logoSaveFile.exists())
			logoSaveFile.mkdirs();
		/** 页面控件的文件流 **/
		MultipartFile multipartFile = multipartRequest.getFile("file");
		// 构建文件名称
		String logImageName = multipartFile.getOriginalFilename();
		/** 获取文件的后缀 **/
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
		/** 使用UUID生成文件名称 **/
		String fileName = logoRealPathDir + File.separator + uuid + suffix;
		/** 拼成完整的文件保存路径加文件 **/
		File file = new File(fileName);
		try {
			multipartFile.transferTo(file);
			modelMap.put("sign", 1);
			modelMap.put("logImageName", logImageName);
			modelMap.put("fileUrl", uuid + suffix);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			modelMap.put("sign", -1);
		} catch (IOException e) {
			e.printStackTrace();
			modelMap.put("sign", -1);
		}
		return new ModelAndView("/ess/infoApplyLeave/uploadBatchTemp", modelMap);
	}

	
	/**
	 * 显示休假申请(view overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSSTApplyAttendance")
	public ModelAndView viewSSTApplyAttendance(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("APPLY_DATE", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		modelMap.put("empVacInfo",this.infoApplyLeaveSer.getEmpVacInfoSST(request));
		return new ModelAndView("/ess/infoApplyAttendance/viewSSTApplyAttendance", modelMap);
	}
	
	//考勤/加班/考勤异常共用
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmorList")
	@ResponseBody
	public Map viewAffirmorList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String applyTypeNo = request.getParameter("applyTypeNo");
		String applyTypeCode = request.getParameter("applyTypeCode");
		String applyLength = request.getParameter("applyLength");
		map.put("affirmorList", this.infoApplySer.getAffirmorListByString(applyTypeNo,admin.getAdminID(),applyTypeCode,applyLength,admin.getLanguage()));
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmorByPersonIdList")
	@ResponseBody
	public Map viewAffirmorByPersonIdList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String applyTypeNo = request.getParameter("applyTypeNo");
		String applyTypeCode = request.getParameter("applyTypeCode");
		String applyLength = request.getParameter("applyLength");
		String personId = request.getParameter("personId");
		String language = admin.getLanguage();
		map.put("affirmorList", this.infoApplySer.getAffirmorListByString(applyTypeNo,personId,applyTypeCode,applyLength,language));
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmorByPersonIdListForCodeName")
	@ResponseBody
	public Map viewAffirmorByPersonIdListForCodeName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String applyTypeNo = request.getParameter("applyTypeNo");
		String applyTypeCode = this.infoApplySer.getLeaveTypeCode(request);
		String applyLength = request.getParameter("applyLength");
		String personId = request.getParameter("personId");
		String language = admin.getLanguage();
		map.put("affirmorList", this.infoApplySer.getAffirmorListByString(applyTypeNo,personId,applyTypeCode,applyLength,language));
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmorByPersonIdListForCode")
	@ResponseBody
	public Map viewAffirmorByPersonIdListForCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String applyTypeNo = request.getParameter("applyTypeNo");
		String applyTypeCode = request.getParameter("applyTypeCode");
		String applyLength = request.getParameter("applyLength");
		String personId = request.getParameter("personId");
		String language = admin.getLanguage();
		map.put("affirmorList", this.infoApplySer.getAffirmorListByString(applyTypeNo,personId,applyTypeCode,applyLength,language));
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmorByPersonIdListForOt")
	@ResponseBody
	public Map viewAffirmorByPersonIdListForOt(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String applyTypeNo = request.getParameter("applyTypeNo");
		//String applyTypeCode = this.infoApplySer.getOtTypeCode(request);
		String applyTypeCode = request.getParameter("applyTypeCode");
		String applyLength = request.getParameter("applyLength");
		String personId = request.getParameter("personId");
		String language = admin.getLanguage();
		map.put("affirmorList", this.infoApplySer.getAffirmorListByString(applyTypeNo,personId,applyTypeCode,applyLength,language));
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAffirmorByApplyNoList")
	@ResponseBody
	public Map getAffirmorByApplyNoList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List viewAffirmList = this.infoApplySer.viewApprovalInfo(request,"getAffirmByApplyNo");
		map.put("affirmorList", viewAffirmList);
		return map;
	}
	
	/**
	 * 男女假别判断
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLeaveDateSST")
	@ResponseBody
	public Map getLeaveDateSST(HttpServletRequest request,HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String apply_type = request.getParameter("apply_type");
		String returnString = this.infoApplyLeaveSer.getSex(request);
		//产假、哺乳假、流产假、产期检查假
		if(!("1325".equals(returnString)||"14013823".equals(returnString))&&"16415".equals(apply_type)){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.BURUJIAJINXIANNVSHISHENQING.b", request));//"哺乳假仅限女士申请。"
		}
		if(!("1325".equals(returnString)||"14013823".equals(returnString))&&"482".equals(apply_type)){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.CHANQIANJIANCHAJIAJINXIANNVSHISHENQING.b", request));//"产前检查假仅限女士申请。"
		}
		/*if(!("1325".equals(returnString)||"14013823".equals(returnString))&&"80000108".equals(apply_type)){
			map.put("statusCode", "300");
			map.put("message", "哺乳假(集中)仅限女士申请。");
		}
		if(!("1325".equals(returnString)||"14013823".equals(returnString))&&"482".equals(apply_type)){
			map.put("statusCode", "300");
			map.put("message", "产前检查假仅限女士申请。");
		}*/
		if(!("1325".equals(returnString)||"14013823".equals(returnString))&&"27".equals(apply_type)){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.CHANJIAJINXIANNVSHISHENQING.b", request));//"产假仅限女士申请。"
		}
		//陪产假
		if(!("1326".equals(returnString)||"14013822".equals(returnString))&&"28".equals(apply_type)){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.PEICHANJIAJINXIANNANSHISHENQING.b", request));//"陪产假仅限男士申请。"
		}
		return map;
	}
/**
	 * 批量删除考勤申请考勤员
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delAttendanceApplyInfoForBatch")
	@ResponseBody
	public Map<String, Object> delAttendanceApplyInfoForBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplyLeaveSer.delAttendanceApplyInfoForBatch(request);
			if (result == 1) {
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));//"删除成功"
				map.put("statusCode", "200");
				map.put("formId", "viewApplyAttenanceBatchInfoList");
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
	@RequestMapping(value = "/delAttendanceExInBatchForBatch")
	@ResponseBody
	public Map<String, Object> delAttendanceExInBatchForBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplyLeaveSer.delAttendanceExInBatchForBatch(request);
			if (result == 1) {
				map.put("message", "删除成功");//"批量删除休假申请决裁成功!"
				map.put("statusCode", "200");
				map.put("formId", "viewApplyAttenanceEXBatchInfoList");
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
	 * 考勤申请考勤员
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveAttendanceApplyInfoForBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map saveAttendanceApplyInfoForBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try{
			result = infoApplyLeaveSer.saveAttendanceApplyInfoForBatch(request);
			if (result==1) {
				map.put("message", TipMessage.getTipMessage("ar.alert.message.addempshift.success",request));//保存成功
				map.put("statusCode", "200");
				map.put("formId", "viewApplyAttenanceBatchInfoList");
			}
			/*else if (result==2) {
				map.put("message",TipMessage.getTipMessage("alert.message.ess.infoApply.applyTimeCanNotLessThanMinValue", request));
				map.put("statusCode", "300");
			}*/
			else{
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败！
				map.put("statusCode", "300");
			}
		}catch (Exception e) {
		    map.put("message", e.getMessage());
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 考勤申请考勤员(可添加删除审批者)
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveAttApplyInfoByAnyApproverForBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map saveAttApplyInfoByAnyApproverForBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try{
			result = infoApplyLeaveSer.saveAttApplyInfoByAnyApproverForBatch(request);
			if (result==1) {
				map.put("message", TipMessage.getTipMessage("ar.alert.message.addempshift.success",request));//保存成功
				map.put("statusCode", "200");
				map.put("formId", "viewApplyAttenanceBatchInfoList");
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
	
	@RequestMapping(value = "/saveAttenanceExBatchInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map saveAttenanceExBatchInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try{
			result = infoApplyLeaveSer.saveAttenanceExBatchInfo(request);
			if (result==1) {
				map.put("message", TipMessage.getTipMessage("ar.alert.message.addempshift.success",request));//"保存成功!"
				map.put("statusCode", "200");
				map.put("formId", "viewApplyAttenanceExBatchInfoList");
			}else{
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//"保存失败!"
				map.put("statusCode", "300");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
		    map.put("message", e.getMessage());
			map.put("statusCode", "300");
		}
			return map;
	}
	
	@RequestMapping(value = "/addAttendanceApplyInfoForBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map addAttendanceApplyInfoForBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try{
			result = infoApplyLeaveSer.addAttendanceApplyInfoForBatch(request);
			if (result==1) {
				map.put("statusCode", "200");
				map.put("formId", "viewApplyAttenanceBatchInfoList");
			}else{
				map.put("statusCode", "300");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;

	}
	/**
	 * 导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author mxq
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportAttendanceTempList")
	public ModelAndView viewImportAttendanceTempList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List attendanceTempList = this.infoApplyLeaveSer.getAttendanceTempList(request);
		int attendanceTempCnt = this.infoApplyLeaveSer.getAttendanceTempCnt(request , "T");
		int errorCnt = this.infoApplyLeaveSer.getAttendanceTempCnt(request , "E");
		
		modelMap.put("attendanceTempList", attendanceTempList);
		modelMap.put("attendanceTempCnt", attendanceTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", attendanceTempCnt);
		return new ModelAndView("/ess/infoApplyAttendance/viewImportAttendanceTempList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelAttendanceData")
	@ResponseBody
	public Map submitImportExcelAttendanceData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.infoApplyLeaveSer.submitImportExcelAttendanceData(request);
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
	/**
	 * 内务考勤查询(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAttendancePersonalInfoList")
	public ModelAndView viewAttendancePersonalInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
		if("".equals(StringUtil.checkNull(request.getParameter("seach_FROM_DATE"))) 
				&& "".equals(StringUtil.checkNull(request.getParameter("seach_TO_DATE")))){
			//当前日期的前一天
			c.add(Calendar.DATE, -1);		
			String first = format.format(c.getTime());
			modelMap.put("FROM_DATE",first);
			modelMap.put("TO_DATE", first); 
		}
		List itemList = itemsSer.getItemParamList2(request);
		modelMap.put("itemList", itemList) ;
		modelMap.put("leaveCoordList", infoApplyLeaveSer.viewAttendancePersonalInfoList(request));
		return new ModelAndView("/ess/infoApplyAttendance/viewAttendancePersonalInfoList", modelMap);
	}
	
	/**
	 * 获得人员列表(view ApplyBatchEmp List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBatchApplyEmpList")
	public ModelAndView viewBatchApplyEmpList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		//modelMap.put("positionList", attendanceKeeperSer.getPositionList(request));
		List personList = infoApplyLeaveSer.getPersonListView(request);
		modelMap.put("personList", personList);
		modelMap.put("addType",request.getParameter("addType"));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyLeaveSer.getPersonListViewCnt(request));
		
		return new ModelAndView("/ar/attendanceMintenance/viewBatchApplyEmpList", modelMap);
	}
	
	@RequestMapping(value = "/addAttendanceApplyInfoForBatchEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map addAttendanceApplyInfoForBatchEmp(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try{
			result = infoApplyLeaveSer.addAttendanceApplyInfoForBatchHAE(request);
			//result = 1;
			if (result==1) {
				map.put("statusCode", "200");
				map.put("formId", "viewApplyAttenanceBatchInfoListHAE");
			}else{
				map.put("statusCode", "300");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;

	}
	
	@RequestMapping(value = "/addOTApplyInfoForBatchEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map addOTApplyInfoForBatchEmp(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try{
			result = infoApplyLeaveSer.addOTApplyInfoForBatchHAE(request);
			//result = 1;
			if (result==1) {
				map.put("statusCode", "200");
				map.put("formId", "viewApplyOTBatchInfoListHAE");
			}else{
				map.put("statusCode", "300");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;

	}
	
	/**
	 * 考勤申请考勤员
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveAttendanceApplyInfoForBatchHAE", method = RequestMethod.POST)
	@ResponseBody
	public Map saveAttendanceApplyInfoForBatchHAE(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try{
			result = infoApplyLeaveSer.saveAttendanceApplyInfoForBatchHAE(request);
			if (result==1) {
				map.put("message", TipMessage.getTipMessage("ar.alert.message.addempshift.success",request));//保存成功
				map.put("statusCode", "200");
				map.put("formId", "viewApplyAttenanceBatchInfoList");
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
	
}