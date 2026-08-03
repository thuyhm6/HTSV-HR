package com.ait.ess.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.jexl2.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.service.AttendanceExConfirmSer;
import com.ait.web.i18n.TipMessage;


@Controller
@RequestMapping(value = "/ess/arConfirm")
public class ArConfirmCtroller {
	
	@Autowired
	private AttendanceExConfirmSer attendanceExConfirmSer;
	/**
	 * 考勤异常人事确认
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * */
	@RequestMapping(value = "/viewAttendanceExConfirm")
	@SuppressWarnings("unchecked")
	public ModelAndView viewAttendanceExConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List viewAttendanceExList = this.attendanceExConfirmSer.viewAttendanceExList(request, "viewAttendanceExList");
		modelMap.put("viewAttendanceExList", viewAttendanceExList);
		modelMap.put("viewAttendanceExListCnt", viewAttendanceExList == null ? 0 : viewAttendanceExList.size());
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
			}
		}
		return new ModelAndView("/ess/arConfirm/viewAttendanceExConfirm", modelMap);
	}
	
	/**
	 * 考勤异常人事确认
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attendanceExConfirm")
	@ResponseBody
	public Map attendanceExConfirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.attendanceExConfirmSer.addAttendanceExJsonPro(request, "attendanceExConfirm");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation", request));//"操作成功"
			map.put("formId", "viewAttendanceExConfirmListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure", request));//"操作失败"
		}
		return map;
	}
	
	/**
	 * 加班人事确认
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * */
	@RequestMapping(value = "/viewPOtApplyInfoConfirmList")
	@SuppressWarnings("unchecked")
	public ModelAndView viewPOtApplyInfoConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List viewPOtApplyInfoConfirmList = this.attendanceExConfirmSer.viewPOtApplyInfoConfirmList(request, "viewPOtApplyInfoConfirmList");
		modelMap.put("viewPOtApplyInfoConfirmList", viewPOtApplyInfoConfirmList);
		modelMap.put("viewPOtApplyInfoConfirmListCnt", viewPOtApplyInfoConfirmList == null ? 0 : viewPOtApplyInfoConfirmList.size());
		/*String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
			}
		}*/
		return new ModelAndView("/ess/arConfirm/viewPOtApplyInfoConfirmList", modelMap);
	}
	
	/**
	 * 加班人事确认
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pOtApplyInfoConfirm")
	@ResponseBody
	public Map pOtApplyInfoConfirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.attendanceExConfirmSer.addPOtApplyInfoJsonPro(request, "pOtApplyInfoConfirm");
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "操作成功");
			map.put("formId", "viewPOtApplyInfoConfirmListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", "操作失败");
		}
		return map;
	}
	
	/**
	 * 休假人事确认
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * */
	@RequestMapping(value = "/viewLeaveConfirmList")
	@SuppressWarnings("unchecked")
	public ModelAndView viewLeaveConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List viewLeaveConfirmList = this.attendanceExConfirmSer.viewLeaveConfirmList(request, "viewLeaveConfirmList");
		modelMap.put("viewLeaveConfirmList", viewLeaveConfirmList);
		modelMap.put("viewLeaveConfirmListCnt", viewLeaveConfirmList == null ? 0 : viewLeaveConfirmList.size());
		/*String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
			}
		}*/
		return new ModelAndView("/ess/arConfirm/viewLeaveConfirmList", modelMap);
	}
	
	/**
	 * 病假证明提交确认
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * */
	@RequestMapping(value = "/viewSickLeaveProofConfirmList")
	@SuppressWarnings("unchecked")
	public ModelAndView viewSickLeaveProofConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List viewSickLeaveProofConfirmList = this.attendanceExConfirmSer.viewLeaveConfirmList(request, "viewSickLeaveProofConfirmList");
		modelMap.put("viewSickLeaveProofConfirmList", viewSickLeaveProofConfirmList);
		modelMap.put("viewSickLeaveProofConfirmListCnt", viewSickLeaveProofConfirmList == null ? 0 : viewSickLeaveProofConfirmList.size());
		return new ModelAndView("/ess/arConfirm/viewSickLeaveProofConfirmList", modelMap);
	}
	
	@RequestMapping(value = "/viewVacApplyInfoConfirmList")
	@SuppressWarnings("unchecked")
	public ModelAndView viewVacConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {	
		List viewVacConfirmList = this.attendanceExConfirmSer.viewLeaveConfirmList(request, "viewVacConfirmList");
		modelMap.put("viewVacConfirmList", viewVacConfirmList);
		modelMap.put("viewVacConfirmListCnt", viewVacConfirmList == null ? 0 : viewVacConfirmList.size());
		/*String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
			}
		}*/
		return new ModelAndView("/ess/arConfirm/viewVacApplyInfoConfirmList", modelMap);
	}
	
	/**
	 * 休假人事确认
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/leaveConfirm")
	@ResponseBody
	public Map leaveConfirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.attendanceExConfirmSer.addLeaveJsonPro(request, "leaveConfirm");
		if (result == 1) {
			map.put("formId", "viewLeaveConfirmListForm");
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));//"操作成功"
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));//"操作失败"
		}
		return map;
	}
	
	/**
	 * 病假证明人事确认
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sickLeaveProofConfirm")
	@ResponseBody
	public Map sickLeaveProofConfirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.attendanceExConfirmSer.sickLeaveProofConfirm(request, "sickLeaveProofConfirm");
		if (result == 1) {
			map.put("formId", "viewSickLeaveConfirmListForm");
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success", request));//"操作成功"
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));//"操作失败"
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/vacConfirm")
	@ResponseBody
	public Map vacConfirm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int	result = this.attendanceExConfirmSer.addLeaveJsonPro(request, "vacConfirm");
		if (result == 1) {
			map.put("formId", "viewLeaveConfirmListForm");
			map.put("statusCode", "200");
			map.put("message", "操作成功");
		} else {
			map.put("statusCode", "300");
			map.put("message", "操作失败");
		}
		return map;
	}
}
