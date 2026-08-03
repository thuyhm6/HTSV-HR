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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.w3c.dom.Document;







import com.ait.ar.service.DynamicGroupSer;
import com.ait.ar.service.ItemsSer;
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
import com.ait.web.util.SessionUtil;
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
 * @version 5.1   /ess/infoApplyLeave/viewApplyLeaveInfo
 */    
@Controller
@RequestMapping(value = "/ess/infoApplyLeave")
public class InfoApplyLeaveCtroller {

	Logger logger = Logger.getLogger(TransferOrderCtroller.class);

	@Autowired
	private EmpInfoSer empInfoSer;
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
	private BasicMaintenanceDao basicMaintenanceDao;	
	

	@Autowired
	private InfoApplySer infoApplySer;

	@Autowired
	private AuthorityUtil authorityUtil;

	@Autowired
	private ItemsSer itemsSer;
	/**
	 * 考勤异常决裁查看(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyLeaveInfoList")
	public ModelAndView viewApplyLeaveInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personInfo", infoApplyLeaveSer.getPersonalInfo(request));
		List itemList = itemsSer.getItemParamList2(request);
		modelMap.put("itemList", itemList) ;
		LinkedHashMap linkMap = (LinkedHashMap)infoApplySer.getPersonalInfo(request);
		modelMap.put("personInfo", linkMap);
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
//		if ("N".equals(firstFlag)) {
			modelMap.put("leaveAffirmList", infoApplyLeaveSer.getPersonalAttInfoDetailList1(request,modelMap)); 
//		}
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/infoApplyLeave/viewApplyLeaveInfoList", modelMap);
	}
	
	
	/**
	 * 考勤异常决裁查看(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCheckAttencetanceExForBatchList")
	public ModelAndView viewCheckAttencetanceExForBatchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("personInfo", infoApplyLeaveSer.getPersonalInfo(request));
		LinkedHashMap linkMap = (LinkedHashMap)infoApplySer.getPersonalInfo(request);
		modelMap.put("personInfo", linkMap);
		String firstFlag= request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月 
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("FROM_DATE",first);
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("TO_DATE",last);
			}
		}
		modelMap.put("leaveAffirmList", infoApplyLeaveSer.viewCheckAttencetanceExForBatchList(request,modelMap)); 
		return new ModelAndView("/ess/infoApplyLeave/viewCheckAttencetanceExForBatchList", modelMap);
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
	@RequestMapping(value = "/viewApplyLeaveInfo")
	public ModelAndView viewApplyLeaveInfo(HttpServletRequest request,
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
		return new ModelAndView("/ess/infoApplyLeave/viewApplyLeaveInfo", modelMap);
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
	 * 加班申请 (add overtime apply)
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
		String AFFIRM_FLAG = request.getParameter("AFFIRM_FLAG");
		try {
			int result = infoApplyLeaveSer.addLeaveApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0240");
				if("-1".equals(AFFIRM_FLAG) ){
					map.put("message", "暂存成功");
				}else{
					if(infoApplyLeaveSer.isZhuisuLeave(request)){
						map.put("message", "提交成功，申请期间已经关闭，该申请将在下月反映。");
					}else{
						map.put("message", "提交成功");
					}
				}
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
				map.put("formId", "viewApplyLeaveInfoList");
			}else{
				map.put("statusCode", "300");
				if("-1".equals(AFFIRM_FLAG) ){
					map.put("message", "暂存失败");
				}else{
					map.put("message", "提交失败");
				}
			}

		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			if("-1".equals(AFFIRM_FLAG) ){
				map.put("message", "暂存休假申请出错,请重新申请");
			}else{
				map.put("message", "提交休假申请出错,请重新申请");
			}
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
	@RequestMapping(value = "/delLeaveApplyInBatch")
	@ResponseBody
	public Map<String, Object> delLeaveApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplyLeaveSer.delLeaveApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0240");
				map.put("message", TipMessage.getTipMessage("alert.message.AttendanceBatch_deletet_Success.b", request));//"批量删除休假申请成功！"
//				map.put("message", TipMessage.getTipMessage("批量删除休假申请成功！",request));//"批量删除休假申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.AttendanceBatch_deletet_Fail.b", request));//"批量删除休假申请失败，请重新操作！"
//			map.put("message", TipMessage.getTipMessage("批量删除休假申请失败，请重新操作！",request));//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 批量删除异常考勤申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delApplyLeaveInfo")
	@ResponseBody
	public Map<String, Object> delApplyLeaveInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplyLeaveSer.delApplyLeaveInfo(request);
			if (result == 1) {
				map.put("navTabId", "ess3206");
				map.put("message", TipMessage.getTipMessage("alert.message.abnormalAttendanceDeleteBatchSuccess.b", request));//"批量删除考勤异常申请成功！"
//				map.put("message", TipMessage.getTipMessage("批量删除休假申请成功！",request));//"批量删除休假申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.abnormalAttendanceDeleteBatchFail.b", request));//"批量删除考勤异常申请失败，请重新操作！"
//			map.put("message", TipMessage.getTipMessage("批量删除休假申请失败，请重新操作！",request));//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 批量删除异常考勤申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delAttencetanceEx")
	@ResponseBody
	public Map<String, Object> delAttencetanceEx(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = infoApplyLeaveSer.delAttencetanceEx(request);
			if (result == 1) {
				map.put("navTabId", "ess3466");
				map.put("message", "批量删除考勤异常申请成功！");//"批量删除休假申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "批量删除考勤异常申请失败，请重新操作！");//"批量删除休假申请决裁出错,请重新操作!"
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
	
	@RequestMapping("downloadFilePhoto")
	public void downloadFilePhoto(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data,charset=UTF-8");
		
		String url=request.getParameter("fileName");
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
	
   	
	private Document getFilePath(String docId) {
		// TODO Auto-generated method stub
		return null;
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
		
		String returnString = this.infoApplyLeaveSer.getShenqingshichang(request);
		
		float times = Float.parseFloat(returnString);
		
		String timeStr = "";
		if("TSTO".equals(admin.getCpnyId())){
			timeStr = times/8 + "天";
		}else{
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
	 * 休假Leave(/view Apply Leave Batch Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyLeaveBatchInfoList")
	public ModelAndView viewApplyLeaveBatchInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("admin", admin);
		
		modelMap.put("leaveAffirmList", infoApplyLeaveSer.getBatchLeaveAffirmInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyLeaveSer.getBatchLeaveAffirmInfoListCnt(request));
		
		return new ModelAndView("/ess/infoApplyLeave/viewApplyLeaveBatchInfoList", modelMap);
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
/*	@RequestMapping(value = "/delLeaveApplyInBatchForBatch")
	@ResponseBody
	public Map<String, Object> delLeaveApplyInBatchForBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String op_flag = request.getParameter("OP_FLAG");
		String msg = "删除";
		if("1".equals(op_flag)){
			msg = "提交";
		}
		try {
			result = infoApplyLeaveSer.delLeaveApplyInBatchForBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0246");
				map.put("message", msg + "成功！");//"批量删除休假申请决裁成功!"
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
		List list = this.infoApplySer.getAffirmorListByString(applyTypeNo, personId, 
						this.infoApplySer.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),applyTypeCode), applyLength, admin.getLanguage());
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
	 * 附件上传(insert)
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lwei liangwei@ait.net.cn
	 * @date 2013-9-12 下午4:18:11
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadBatchInsert")
	public ModelAndView uploadBatchInsert(HttpServletRequest request,
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
		return new ModelAndView("/ess/infoApplyLeave/uploadBatchInsert", modelMap);
	}
	
	/**
	 * 图片上传
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lwei liangwei@ait.net.cn
	 * @date 2013-9-12 下午4:18:11
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadBatchPhoto")
	public ModelAndView uploadBatchPhoto(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
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
		String logoPathDir = "/resources/photo/" + admin.getCpnyId();// dateformat.format(new Date());
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
		//String fileName = logoRealPathDir + File.separator + logImageName;
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
		return new ModelAndView("/ess/infoApplyLeave/uploadBatchPhoto", modelMap);
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
	 * SST休假申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addLeaveApplySST")
	@ResponseBody
	public Map addLeaveApplySST(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplyLeaveSer.addLeaveApplySST(request);
			if (result == 1) {
				map.put("navTabId", "ess0214");
				map.put("message", TipMessage.getTipMessage("alert.message.save_success", request));//保存成功
				map.put("statusCode", "200");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));//保存失败
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 天津年假信息申请
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addVacInfo")
	@ResponseBody
	public Map addVacInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplyLeaveSer.addVacInfo(request);
			if (result == 1) {
				map.put("navTabId", "ess3210");
				map.put("message", "保存成功");
				map.put("statusCode", "200");
			}else{
				map.put("statusCode", "300");
				map.put("message", "保存失败");
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		}
		return map;
	}
}