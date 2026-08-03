package com.ait.ess.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ItemsSer;
import com.ait.ess.service.AffirmApplySer;
import com.ait.ess.service.AffirmLeaveApplySer;
import com.ait.ess.service.AnnualadjustmentInfoSer;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * 信息决裁
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: AffirmApplyCtroller.java
 * @Description:
 * @Create date: Apr 6, 2012 12:32:56 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Apr 6, 2012 12:32:56 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/affirmLeaveApply")
public class AffirmLeaveApplyCtroller {

	Logger logger = Logger.getLogger(AffirmLeaveApplyCtroller.class);

	@Autowired
	private AffirmLeaveApplySer affirmApplySer;
	
	@Autowired
	private ItemsSer itemsSer;
	
	@Autowired
	private InfoApplyLeaveSer infoApplySer;
	
	@Autowired
	private EmpInfoSer empInfoSer;
	
	@Autowired
	private AnnualadjustmentInfoSer annualadjustmentInfoSer;
	
	@Autowired
	private AffirmApplySer affirmApplySers;
	
	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;

	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOtAffirmList")
	public ModelAndView viewOtAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("otApplyList", this.affirmApplySer.getOtAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getOtAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewOtAffirmList",modelMap);
	}
	
	
	/**
	 * 年假调整 决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAnnualadjustmentAffirmList")
	public ModelAndView viewAnnualadjustmentAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		
		modelMap.put("annualadjustmentInfoList",annualadjustmentInfoSer.getAnnualadjustmentAffirmapplyList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, annualadjustmentInfoSer.getAnnualadjustmentAffirmapplyListCnt(request));
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewAnnualadjustmentAffirmList",modelMap);
	}
	
	
	
	/**
	 * 批量通过/否决加班申请(batch pass or reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveOvertimeApplyInBatch")
	@ResponseBody
	public Map<String, Object> approveOvertimeApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveOvertimeApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0214");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirmInBatch_success",request));//"批量加班申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirmInBatch_fail",request));//"批量加班申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	
	/**
	 * 批量通过/否决年假调整申请(batch pass or reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appAnnualadjustmentApplyInBatch")
	@ResponseBody
	public Map<String, Object> appAnnualadjustmentApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.appAnnualadjustmentApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0214");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirmInBatch_success",request));//"批量加班申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirmInBatch_fail",request));//"批量加班申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
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
	@RequestMapping(value = "/viewFullApplyAffirmorList")
	public ModelAndView viewFullApplyAffirmorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", admin.getPersonId());
		
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		return new ModelAndView("/ess/affirmApply/viewFullApplyAffirmorList", modelMap);
	}
	
	
	/**
	 * 查看完整年假调整信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullAnnuApplyAffirmorList")
	public ModelAndView viewFullAnnuApplyAffirmorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		modelMap.put("APPLY_NO",paramMap.get("APPLY_NO"));
		modelMap.put("PERSON_ID", admin.getPersonId());
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		return new ModelAndView("/ess/affirmApply/viewFullAnnuApplyAffirmorList", modelMap);
	}
	
	
	/**
	 * 决裁---加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOtAffirmInfo")
	@ResponseBody
	public Map addOtAffirmInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = affirmApplySer.approveOvertimeApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0214");
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
	 * 查看-加班信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullApplyCheckInfo")
	public ModelAndView viewFullApplyCheckInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		
		return new ModelAndView("/ess/affirmApply/viewFullApplyCheckInfo", modelMap);
	}
	
	/**
	 * 添加check信息(view full information)
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
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO").toString());
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		
		return new ModelAndView("/ess/affirmApply/viewFullApplyRemarkInfo", modelMap);
	}
	
	/**
	 * 决裁---加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addApplyCheckList",method = RequestMethod.POST)
	@ResponseBody
	public Map addApplyCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		int result = affirmApplySer.addApplyCheckList(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("添加Checkor者成功！",request));//添加Checkor者成功！
			map.put("navTabId", "ess0242_affirm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("添加Checkor者失败！",request));//添加Checkor者失败！
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAnnuApplyCheckList",method = RequestMethod.POST)
	@ResponseBody
	public Map addAnnuApplyCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		int result = affirmApplySers.addApplyCheckList(request);
		//int result = affirmApplySer.addApplyCheckList(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("添加Checkor者成功！",request));//添加Checkor者成功！
			map.put("navTabId", "ess0304_affirm");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("添加Checkor者失败！",request));//添加Checkor者失败！
		}
		return map;
	}
	
	
	/**
	 * 决裁---年假调整申请 (add overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAuunAffirmInfo")
	@ResponseBody
	public Map addAuunAffirmInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = affirmApplySer.appAUUNApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0214");
				map.put("message", "年假调整决裁成功！"); 
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message","年假调整决裁失败 ！"); 
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 加班申请check列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOtCheckList")
	public ModelAndView viewOtCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("otCheckList", this.affirmApplySer.getOtCheckList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getOtCheckListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2355")) ;
		return new ModelAndView("/ess/affirmApply/viewOtCheckList",modelMap);
	}
	
	/**
	 * check加班信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkApplyCheckInfo")
	public ModelAndView checkApplyCheckInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//		is_check 用来判断查看或提交权限
		Map temp = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String is_check = (null == temp.get("IS_CHECK") ? "0" : temp.get("IS_CHECK").toString());

		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
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

		LinkedHashMap infoApplyLeave = (LinkedHashMap) infoApplySer.getLeaveInfoByLeave(request);
		modelMap.put("infoApplyLeave", infoApplyLeave);
		
		if(infoApplyLeave != null && infoApplyLeave.get("APPLY_TYPE") != null && "BATCH".equals(infoApplyLeave.get("APPLY_TYPE"))){
			modelMap.put("leaveBatchAffirmList", infoApplySer.getLeaveBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getLeaveBatchAffirmInfoCnt(request));
		}
		
		return new ModelAndView("/ess/affirmLeaveApply/checkApplyCheckInfo", modelMap);
	}
	
	/**
	 * Check---加班申请 (check overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkApplyInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map checkApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = affirmApplySer.checkApplyInfo(request);

		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("休假申请Check成功！",request));//Check成功
			map.put("navTabId", "ess0243");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("休假申请Check失败！",request));//Check失败
		}
		return map;
	}
	
	/**
	 * Check---加班申请 (check overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkApplicationInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map checkApplicationInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = affirmApplySer.checkApplyInfo(request);

		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("费用申请Check成功！",request));//Check成功
			map.put("navTabId", "ess0245");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("费用申请Check失败！",request));//Check失败
		}
		return map;
	}
	
	/**
	 * Check---加班申请 (check overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkCwaApplyInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map checkCwaApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = affirmApplySer.checkApplyInfo(request);

		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("考勤异常申请Check成功！",request));//Check成功
			map.put("navTabId", "ess0306");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("考勤异常申请Check失败！",request));//Check失败
		}
		return map;
	}
	
	
	/**
	 * Check-- 年假调休申请 (check overtime apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/AnnucheckApplyInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map AnnucheckApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = affirmApplySer.checkApplyInfo(request);

		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("年假调整申请Check成功！",request));//Check成功
			map.put("navTabId", "ess0308");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("年假调整申请Check失败！",request));//Check失败
		}
		return map;
	}
	
	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOvertimeAffirmList")
	public ModelAndView viewOvertimeAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("ovetimeApplyPersonList", this.affirmApplySer.getOvertimeAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getOvertimeAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewOvertimeAffirmList",
				modelMap);
	}
	/**
	 * 休假申请列表(leave apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLeaveAffirmList")
	public ModelAndView viewLeaveAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("leaveApplyAffirmList", this.affirmApplySer
				.getLeaveApplyAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer
				.getLeaveApplyAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		if(request.getParameter("parentParam") != null){
			String tempStr = StringUtil.checkNull(request.getParameter("parentParam")) ;
			String[] tempStrs =  tempStr.split("@");
			String[] tempStrsForMap = null ;
			for (int i = 0; i < tempStrs.length; i++) {
				if(tempStrs[i].indexOf("=") != -1){
					tempStrsForMap = tempStrs[i].split("=");
					if(tempStrsForMap.length == 2)
						modelMap.put(""+tempStrsForMap[0],tempStrsForMap[1] );
				}
			}
		}
		if("".equals(StringUtil.checkNull(request.getParameter("seach_AFFIRM_FLAG")))){
			modelMap.put("AFFIRM_FLAG", "10");
		}
		return new ModelAndView("/ess/affirmLeaveApply/viewLeaveAffirmList",
				modelMap);
	}
	/**
	 * 休假申请决裁列表(leave apply affirm list) Management
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAttendanceAffirmList")
	public ModelAndView viewAttendanceAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List itemList = itemsSer.getItemParamList2(request);
		modelMap.put("itemList", itemList) ;
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","14014304");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		modelMap.put("codeList",codeList);
		String firstFlag = request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_FROM_TIME")==""||request.getParameter("seach_FROM_TIME")==null )&& (request.getParameter("seach_TO_TIME")==""||request.getParameter("seach_TO_TIME")==null)){
				//获取当前月第一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("FROM_TIME",first);
				//获取当前月最后一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("TO_TIME",last);
			}
		}
		modelMap.put("codeList",codeList);
//		if("".equals(StringUtil.checkNull(request.getParameter("seach_AFFIRM_FLAG")))){
//			modelMap.put("AFFIRM_FLAG", "14014307");
//		}else {
//			modelMap.put("AFFIRM_FLAG", request.getParameter("seach_AFFIRM_FLAG"));
//		}
//		if(firstFlag != null && !"".equals(firstFlag)){
			
			modelMap.put("leaveApplyAffirmList", this.affirmApplySer
					.getAttendanceAffirmList(request));
//		}else {
//			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
//		}
		
		return new ModelAndView("/ess/affirmLeaveApply/viewAttendanceAffirmList",
				modelMap);
	}

	/**
	 * 出差申请决裁列表(evection apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvectionAffirmList")
	public ModelAndView viewEvectionAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("evectionApplyPersonList", this.affirmApplySer
				.getEvectionApplyAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer
				.getEvectionApplyAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewEvectionAffirmList",
				modelMap);
	}

	/**
	 * 外出申请决裁列表(egression apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEgressionAffirmList")
	public ModelAndView viewEgressionAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("egressionApplyPersonList", this.affirmApplySer
				.getEgeressionApplyAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer
				.getEgressionApplyAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewEgressionAffirmList",
				modelMap);
	}

	

	/**
	 * 通过/否决加班申请(pass or reject overtime Apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveOvertimeApply")
	@ResponseBody
	public Map<String, Object> approveOvertimeApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveOvertimeApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0801");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirm_success",request));//"加班申请决裁成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.overtimeApplyAffirm_fail",request));//"加班申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 批量通过/否决休假申请(batch pass or reject leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveLeaveApplyInBatch")
	@ResponseBody
	public Map<String, Object> approveLeaveApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentParam", request.getParameter("parentParam")); 
		int result = 0;
		try {
			result = affirmApplySer.approveLeaveApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess3413");
				map.put("formId", "viewAttendanceAffirmList");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.leaveApplyAffirmInBatch_success",request));//"批量休假申请决裁操作成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.leaveApplyAffirmInBatch_fail",request));//"批量休假申请决裁操作出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决休假申请(pass or reject leave Apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveLeaveApply")
	@ResponseBody
	public Map<String, Object> approveLeaveApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveLeaveApply(request);
			if (result == 1) {
				String navTabId=request.getParameter("navTabId");
				map.put("navTabId", navTabId);
				//map.put("navTabId", "ess0802");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.leaveApplyAffirm_success",request));//"休假申请决裁操作成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.leaveApplyAffirm_fail",request));//"休假申请决裁操作出错,请重新操作!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 批量通过/否决出差申请(batch pass or reject evection apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveEvectionApplyInBatch")
	@ResponseBody
	public Map<String, Object> approveEvectionApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveLeaveApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0805");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.evectionApplyAffirmInBatch_success",request));//"批量出差申请决裁操作成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.evectionApplyAffirmInBatch_fail",request));//"批量出差申请决裁操作出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决出差申请(pass or reject evection Apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveEvectionApply")
	@ResponseBody
	public Map<String, Object> approveEvectionApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveLeaveApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0805");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.evectionApplyAffirm_success",request));//"出差申请决裁操作成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.evectionApplyAffirm_fail",request));//"出差申请决裁操作出错,请重新操作!"
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 批量通过/否决外出申请(batch pass or reject egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveEgressionApplyInBatch")
	@ResponseBody
	public Map<String, Object> approveEgressionApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = affirmApplySer.approveLeaveApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0811");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.egressionApplyAffirmInBatch_success",request));//"批量外出申请决裁操作成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.egressionApplyAffirmInBatch_fail",request));//"批量外出申请决裁操作出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决外出申请(pass or reject egression Apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveEgressionApply")
	@ResponseBody
	public Map<String, Object> approveEgressionApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = this.affirmApplySer.approveLeaveApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0811");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.egressionApplyAffirm_success",request));//"外出申请决裁操作成功!"
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.affirmApply.egressionApplyAffirm_fail",request));//"外出申请决裁操作出错,请重新操作!"
			map.put("statusCode", "300");
		}
		return map;
	}
	/**
	 * 喜丧假决裁列表(leave apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLikeLeaveAffirmList")
	public ModelAndView viewLikeLeaveAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("leaveApplyAffirmList", this.affirmApplySer
				.getLeaveApplyAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer
				.getLeaveApplyAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewLikeLeaveAffirmList",
				modelMap);
	}
	/**
	 * 调休决裁列表(leave apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewWorkingDaysAffirmList")
	public ModelAndView viewWorkingDaysAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("leaveApplyAffirmList", this.affirmApplySer
				.getLeaveApplyAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer
				.getLeaveApplyAffirmListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/ess/affirmApply/viewWorkingDaysAffirmList",
				modelMap);
	}
	
	/**
	 * 决裁---休假申请 (add leave apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addLeaveAffirmInfo")
	@ResponseBody
	public Map addLeaveAffirmInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentParam", request.getParameter("parentParam")); 
		try {
			int result = affirmApplySer.approveApplyLeave(request);
			if (result == 1) {
				map.put("navTabId", "ess0242");
				map.put("message", TipMessage.getTipMessage(
						"liang.alert.message.ess.trans.Successful_operation", request));//"保存加班申请成功!"
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
				map.put("formId", "searchLeaveApplyAffirmForm_leave");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "休假决裁失败！请重新决裁");//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	

	/**
	 * Check---休假申请 (check leave apply)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkLeaveApplyInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map checkLeaveApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = affirmApplySer.checkApplyInfo(request);

		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("休假申请Check成功！",request));//Check成功
			map.put("navTabId", "ess0243");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("休假申请Check失败！",request));//Check失败
		}
		return map;
	}
	

	/**
	 * 查看完整休假事由信息(view leave information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLeaveApplyAffirmorList")
	public ModelAndView viewLeaveApplyAffirmorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", admin.getPersonId());
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		String essAffirmNo = "";
		//找到需要决裁的决裁编号
		for(int i=0;i<affirmorList.size();i++){
			LinkedHashMap applyorMap = (LinkedHashMap)affirmorList.get(i);
			if("0".equals(applyorMap.get("AFFIRM_FLAG").toString())){
				if(admin.getPersonId().equals(applyorMap.get("AFFIRMOR_ID").toString())){
					essAffirmNo = applyorMap.get("ESS_AFFIRM_NO").toString();
				}
				break;
			}
		}
		modelMap.put("essAffirmNo", essAffirmNo);
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		modelMap.put("defaultCpny", admin.getCpnyId());
		LinkedHashMap infoApplyLeave = (LinkedHashMap) infoApplySer.getLeaveInfoByLeave(request);
		modelMap.put("infoApplyLeave", infoApplyLeave);
		
		if(infoApplyLeave != null && infoApplyLeave.get("APPLY_TYPE") != null && "BATCH".equals(infoApplyLeave.get("APPLY_TYPE"))){
			modelMap.put("leaveBatchAffirmList", infoApplySer.getLeaveBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getLeaveBatchAffirmInfoCnt(request));
		}
		
		if(request.getParameter("parentParam") != null){
			modelMap.put("parentParam", request.getParameter("parentParam"));
		}
		
		return new ModelAndView("/ess/affirmLeaveApply/viewLeaveApplyAffirmorList", modelMap);
	}
	

	/**
	 * 查看-加班信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLeaveApplyCheckInfo")
	public ModelAndView viewLeaveApplyCheckInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		
		List affirmorList = infoApplySer.getAffirmorByApplyNoList(request);
		List checkorList = infoApplySer.getCheckorByApplyNoList(request);
		
		modelMap.put("affirmorList", affirmorList);
		modelMap.put("affirmorListCnt", affirmorList.size());
		modelMap.put("checkorList", checkorList);
		modelMap.put("checkorListCnt", checkorList.size());
		LinkedHashMap infoApplyLeave = (LinkedHashMap) infoApplySer.getLeaveInfoByLeave(request);
		modelMap.put("infoApplyLeave", infoApplyLeave);
		
		if(infoApplyLeave != null && infoApplyLeave.get("APPLY_TYPE") != null && "BATCH".equals(infoApplyLeave.get("APPLY_TYPE"))){
			modelMap.put("leaveBatchAffirmList", infoApplySer.getLeaveBatchAffirmInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplySer.getLeaveBatchAffirmInfoCnt(request));
		}
		return new ModelAndView("/ess/affirmLeaveApply/viewLeaveApplyCheckInfo", modelMap);
	}
	

	/**
	 * 添加check信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLeaveApplyRemarkInfo")
	public ModelAndView viewLeaveApplyRemarkInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO").toString());
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		//用于返回，找到应该刷新的页面
		modelMap.put("PAGE_FLAG", paramMap.get("PAGE_FLAG") );
		
		return new ModelAndView("/ess/affirmLeaveApply/viewLeaveApplyRemarkInfo", modelMap);
	}
	
	
	/**
	 * 添加check信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewMACApplyRemarkInfo")
	public ModelAndView viewMACApplyRemarkInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO").toString());
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		modelMap.put("APPLY_TYPE", "218294");
		return new ModelAndView("/ess/affirmLeaveApply/viewMACApplyRemarkInfo", modelMap);
	}
	
	
	/**
	 * 添加check信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCwaApplyRemarkInfo")
	public ModelAndView viewCwaApplyRemarkInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO").toString());
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		modelMap.put("APPLY_TYPE", "218197");
		
		return new ModelAndView("/ess/affirmLeaveApply/viewCwaApplyRemarkInfo", modelMap);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAnnuApplyRemarkInfo")
	public ModelAndView viewAnnuApplyRemarkInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO").toString());
		modelMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
		modelMap.put("APPLY_TYPE", "216691");
		return new ModelAndView("/ess/affirmLeaveApply/viewAnnuApplyRemarkInfo", modelMap);
	}

	
	/**
	 * 休假申请check列表(leave apply affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewLeaveCheckList")
	public ModelAndView viewLeaveCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("leaveCheckList", this.affirmApplySer.getLeaveCheckList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmApplySer.getLeaveCheckListCnt(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2355")) ;
		return new ModelAndView("/ess/affirmLeaveApply/viewLeaveCheckList",modelMap);
	}
}