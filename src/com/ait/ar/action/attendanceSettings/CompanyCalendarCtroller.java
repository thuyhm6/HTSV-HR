package com.ait.ar.action.attendanceSettings;

import java.text.SimpleDateFormat;
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

import com.ait.ar.service.ArClassCalendarSer;
import com.ait.ar.service.CompanyCalendarSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.workManagement.PaPayScheduleSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.DateUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: CompanyCalendarCtroller.java
 * @Description:
 * @Create date: 2012-1-12 下午08:14:37
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class CompanyCalendarCtroller {
	Logger logger = Logger.getLogger(CompanyCalendarCtroller.class);
	
	@Autowired
	private CompanyCalendarSer companyCalendarSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private  ArClassCalendarSer arClassCalendarSer;
	@Autowired
	private  PaPayScheduleSer paPayScheduleSer;
	@Autowired
	private EmpInfoSer empInfoSer;
	
	/**
	 * 公司日历查询(view Company Calendar)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCompanyCalendar")
	public ModelAndView viewCompanyCalendar(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		String calendarHtml = this.companyCalendarSer.getCompanyCalendarViewHtml(request) ;
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		
		modelMap.put("calendarHtml", calendarHtml) ;
		modelMap.put("year", year);
		modelMap.put("month", month);
		modelMap.put("reqest_year", StringUtil.checkNull(request.getParameter("year")));
		modelMap.put("reqest_month", StringUtil.checkNull(request.getParameter("month")));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2352")) ;
		
		return new ModelAndView("/ar/attendanceSettings/viewCompanyCalendar",modelMap);
	}
	/**
	 * 添加按钮跳转(add CompanyCalendar View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCompanyCalendarView",method = RequestMethod.GET)
	public ModelAndView addCompanyCalendarView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List shiftList = companyCalendarSer.getShiftList(request);
		modelMap.put("shifts", shiftList);
		return new ModelAndView("/ar/attendanceSettings/addCompanyCalendarView",modelMap);
	}
	/**
	 * 添加按钮跳转班次日历(add CompanyCalendar View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCompanyCalendarViewBanCi",method = RequestMethod.GET)
	public ModelAndView addCompanyCalendarViewBanCi(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List shiftList = companyCalendarSer.getShiftList(request);
		List shiftNo=arClassCalendarSer.getShiftNo(request);
		modelMap.put("shifts", shiftList);
		modelMap.put("shiftNo", shiftNo);
		return new ModelAndView("/ar/attendanceSettings/addCompanyCalendarViewBanCi",modelMap);
	}
	
	/**
	 * 保存公司日历(add CompanyCalendar Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCompanyCalendarInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCompanyCalendarInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.companyCalendarSer.addCompanyCalendarInfo(request);
		String from_date = request.getParameter("FROM_DATE") != null ? request.getParameter("FROM_DATE") : "";
		String year = "";
		String month = "";
		if(!from_date.equals("")){
			year = from_date.substring(0, 4);
			month = from_date.substring(1, 3);
		}
		
		if(result == 1){
			
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
			map.put("navTabId", "ar0102");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		
		return map;		
	}
	/**
	 * 保存班次日历(add CompanyCalendar Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCompanyCalendarInfoBanCi",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCompanyCalendarInfoBanCi(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.companyCalendarSer.addCompanyCalendarInfoBanCi(request);
		String from_date = request.getParameter("FROM_DATE") != null ? request.getParameter("FROM_DATE") : "";
		String year = "";
		String month = "";
		if(!from_date.equals("")){
			year = from_date.substring(6, 10);
			month = from_date.substring(3, 5);
		}
		
		if(result == 1){
			
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
			map.put("navTabId", "ar0102");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		
		return map;		
	}
	/**
	 * 修改按钮跳转(update CompanyCalendar View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCompanyCalendarView")
	public ModelAndView updateCompanyCalendarView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		String calendarHtml = this.companyCalendarSer.getCompanyCalendarViewHtml(request) ;
		//String a = StringUtil.checkNull(request.getParameter("month"));
		
		modelMap.put("reqest_year", StringUtil.checkNull(request.getParameter("year")));
		modelMap.put("request_month", StringUtil.checkNull(request.getParameter("month")));
		
		modelMap.put("calendarHtml", calendarHtml);
		List shiftList = companyCalendarSer.getShiftList(request);
		modelMap.put("shifts", shiftList);
		
		return new ModelAndView("/ar/attendanceSettings/viewClassCalendar",modelMap);
	}
	/**
	 * 修改保存
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCompanyCalendarInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCompanyCalendarInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		
		int result = this.companyCalendarSer.updateCompanyCalendarInfo(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "ar0102");
			map.put("callbackType", "forward");
			map.put("forwardUrl", "/ar/attendanceSettings/viewCompanyCalendar?year="+year+"&month="+month);
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
		/**
		 * 添加按钮跳转(add StatutoryHolidays View)
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/addStatutoryHolidaysView",method = RequestMethod.GET)
		public ModelAndView addStatutoryHolidaysView(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			
			List shiftList = companyCalendarSer.getShiftList(request);
			modelMap.put("shifts", shiftList);
			return new ModelAndView("/ar/attendanceSettings/addStatutoryHolidaysView",modelMap);
		}
		/**
		 * 保存法定节假日(add StatutoryHolidays Info)
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/addStatutoryHolidaysInfo",method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addStatutoryHolidaysInfo(HttpServletRequest request)throws Exception{
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			int result = this.companyCalendarSer.addStatutoryHolidaysInfo(request);
			String from_date = request.getParameter("FROM_DATE") != null ? request.getParameter("FROM_DATE") : "";
			String year = "";
			String month = "";
			if(!from_date.equals("")){
				year = from_date.substring(0, 4);
				month = from_date.substring(1, 3);
			}
			
			if(result == 1){
				
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
				map.put("navTabId", "ar0130");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
			
			return map;		
		}
		/**
		 * 修改法定节假日按钮跳转(update StatutoryHolidays View)
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/updateStatutoryHolidaysView")
		public ModelAndView updateStatutoryHolidaysView(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			
			List shiftList = companyCalendarSer.getShiftList(request);
			modelMap.put("shifts", shiftList);
			//查询某一天法定节假日
			modelMap.put("statutoryHoliday", companyCalendarSer.getOneStatutoryHolidayInfo(request));
			return new ModelAndView("/ar/attendanceSettings/updateStatutoryHolidaysView",modelMap);
		}
		/**
		 * 修改保存法定节假日
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/updateStatutoryHolidaysInfo",method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateStatutoryHolidaysInfo(HttpServletRequest request)throws Exception{
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			int result = this.companyCalendarSer.updateStatutoryHolidaysInfo(request) ;
			
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("formId", "viewStatutoryHolidays");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			}
			return map;
		}
		/**
		 * 删除法定节假日设置
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/deleteStatutoryHolidaysInfo",method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteStatutoryHolidaysInfo(HttpServletRequest request)throws Exception{
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			int result = this.companyCalendarSer.deleteStatutoryHolidaysInfo(request) ;
			
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
				map.put("formId", "viewStatutoryHolidays");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//修删除失败
			}
			return map;
		}
		
		/**
		 * 年假使用管理
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked") 
		@RequestMapping(value = "/viewVacEmpList")
		public ModelAndView viewVacEmpList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			String firstFlag = request.getParameter("firstFlag");
			List paPayScheduleList = this.paPayScheduleSer.getPayScheduleAllList(request);
			if(firstFlag == null || "".equals(firstFlag)){
				List viewVacEmpList = this.companyCalendarSer.viewVacEmpList(request);
				modelMap.put("viewVacEmpList", viewVacEmpList);
				modelMap.put("viewVacEmpListCnt", viewVacEmpList == null ? 0 : viewVacEmpList.size());

				LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
				modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				modelMap.put("personInfo",linkMap);
			}else{
				AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				modelMap.put("YEAR",DateUtil.getSysdateStr("yyyy"));
			}
			modelMap.put("paPayScheduleList", paPayScheduleList);
			return new ModelAndView("/ar/attendanceSettings/viewVacEmpList", modelMap);
		}
		
		/**
		 * 年假使用管理页面的年假清算页面
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked") 
		@RequestMapping(value = "/viewVacClearInfo")
		public ModelAndView viewVacClearInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List paPayScheduleList = this.paPayScheduleSer.getPayScheduleAllList(request);
			modelMap.put("paPayScheduleList", paPayScheduleList);
			modelMap.put("clearYear", request.getParameter("year"));
			return new ModelAndView("/ar/attendanceSettings/viewVacClearInfo", modelMap);
		}
		

		@SuppressWarnings("unchecked") 
		@RequestMapping(value = "/executeVacClear")
		@ResponseBody
		public Map executeVacClear(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			String returnStr = this.companyCalendarSer.executeVacClear(request);
			if(returnStr == "ok"){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_success",request));//"操作成功"
				map.put("formId", "viewVacEmpListForm");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail",request));//"操作失败"
			}
			return map;
		}
		
		/**
		 * 保存年假信息
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/saveEmpVacInfo")
		@ResponseBody
		public Map<String, Object> saveEmpVacInfo(HttpServletRequest request)throws Exception{
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			int result = this.companyCalendarSer.saveEmpVacInfo(request) ;
			
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
				map.put("formId", "viewVacEmpListForm");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
			return map;
		}
		
		/**
		 * 倒休使用管理
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewTxEmpList")
		public ModelAndView viewTxEmpList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			String firstFlag = request.getParameter("firstFlag");
			SimpleDateFormat sformat = new SimpleDateFormat("yyyyMM");
			String ar_month_str =  sformat.format(new Date());
			modelMap.put("AR_MONTH", ar_month_str);
			if(firstFlag == null || "".equals(firstFlag)){
				List viewTxEmpList = this.companyCalendarSer.viewTxEmpList(request);
				modelMap.put("viewTxEmpList", viewTxEmpList);
				modelMap.put("viewTxEmpListCnt", viewTxEmpList == null ? 0 : viewTxEmpList.size());

				LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
				modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				modelMap.put("personInfo",linkMap);
			}
			
			return new ModelAndView("/ar/attendanceSettings/viewTxEmpList", modelMap);
		}
		
		/**
		 * TSTO倒休使用管理
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewTxEmpTSTOList")
		public ModelAndView viewTxEmpTSTOList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			String firstFlag = request.getParameter("firstFlag");
			SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
			String ar_month_str =  sformat.format(new Date());
			modelMap.put("AR_MONTH", ar_month_str);
			if(firstFlag == null || "".equals(firstFlag)){
				List viewTxEmpList = this.companyCalendarSer.viewTxEmpTSTOList(request);
				modelMap.put("viewTxEmpList", viewTxEmpList);
				modelMap.put("viewTxEmpListCnt", viewTxEmpList == null ? 0 : viewTxEmpList.size());
				
				LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
				modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				modelMap.put("personInfo",linkMap);
			}
			
			return new ModelAndView("/ar/attendanceSettings/viewTxEmpTSTOList", modelMap);
		}
		
		/**
		 * 保存年假信息
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/saveEmpTxInfo")
		@ResponseBody
		public Map<String, Object> saveEmpTxInfo(HttpServletRequest request)throws Exception{
			
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.companyCalendarSer.saveEmpTxInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", "保存成功");//删除成功
				map.put("formId", "viewTxEmpListForm");
			}else{
				map.put("statusCode", "300");
				map.put("message", "保存失败");//修删除失败
			}
			return map;
		}
		
		/**
		 * 保存年假信息
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/saveEmpTxInfo2")
		@ResponseBody
		public Map<String, Object> saveEmpTxInfo2(HttpServletRequest request)throws Exception{
			
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.companyCalendarSer.saveEmpTxInfo2(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", "保存成功");//删除成功
				map.put("formId", "viewTxEmpListForm");
			}else{
				map.put("statusCode", "300");
				map.put("message", "保存失败");//修删除失败
			}
			return map;
		}
}
