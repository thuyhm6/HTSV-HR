package com.ait.ar.action.attendanceMintenance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.CycleSer;
import com.ait.ar.service.EmpCalendarSer;
import com.ait.ar.service.ShiftSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpCalendarCtroller.java
 * @Description:
 * @Create date: 2012-2-6 上午10:49:39
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class EmpCalendarCtroller {
	Logger logger = Logger.getLogger(EmpCalendarCtroller.class);
	@Autowired
	private EmpCalendarSer empCalendarSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private ShiftSer shiftSer;
    
 
	/**
	 * 个人日历查看页面(add EmpShift View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpCalendar")
	public ModelAndView addEmpShiftView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
	   String calendarHtml = this.empCalendarSer.getEmpCalendarViewHtml(request) ;
	   
	   HttpSession session = request.getSession() ;
	   AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
	   String noEmpFlag = request.getParameter("NO_EMP");
	   modelMap.put("NO_EMP", noEmpFlag);
	   if (ObjectUtils.toString(request.getParameter("person_id")).equals("")) {
		   if("Y".equals(noEmpFlag)){
			    modelMap.put("empid", "");
			    modelMap.put("name", "") ;
				modelMap.put("deptname", "") ;
				modelMap.put("person_id", "") ;
			}else{
				modelMap.put("empid", admin.getEmpID());
				modelMap.put("name", admin.getLocalName()) ;
				modelMap.put("deptname", admin.getDepartment()) ;
				modelMap.put("person_id", admin.getPersonId()) ;
			}
			modelMap.put("cpny_id", admin.getCpnyId()) ;
			modelMap.put("STAT_NO", admin.getStatNo() != null ? admin.getStatNo() : "") ;
		}else{
			LinkedHashMap empMap = (LinkedHashMap)this.empCalendarSer.getEmpInfo(request);
			if("Y".equals(noEmpFlag)){
			    modelMap.put("empid", "");
			    modelMap.put("name", "") ;
				modelMap.put("deptname", "") ;
				modelMap.put("person_id", "") ;
			}else{
				modelMap.put("empid", empMap.get("EMPID").toString());
				modelMap.put("name", empMap.get("LOCAL_NAME").toString()) ;
				modelMap.put("deptname", empMap.get("DEPTNAME").toString()) ;
				modelMap.put("person_id", empMap.get("PERSON_ID").toString()) ;
			}
			modelMap.put("cpny_id", empMap.get("CPNY_ID").toString()) ;
			modelMap.put("STAT_NO", empMap.get("STAT_NO") != null ? empMap.get("STAT_NO").toString() : "") ;
        }
	    modelMap.put("reqest_year", StringUtil.checkNull(request.getParameter("year")));
	    modelMap.put("reqest_month", StringUtil.checkNull(request.getParameter("month")));
		modelMap.put("calendarHtml", calendarHtml) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "14015886")) ;
		return new ModelAndView("/ar/attendanceMintenance/viewEmpCalendar",modelMap);
	}

	/**
	 * 个人日历修改页面(update EmpCalendar View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEmpCalendarView",method = RequestMethod.GET)
	public ModelAndView updateEmpCalendarView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		String calendarHtml = this.empCalendarSer.getEmpCalendarViewHtml(request) ;
		
		modelMap.put("calendarHtml", calendarHtml) ;
		
		return new ModelAndView("/ar/attendanceMintenance/viewEmpCalendar",modelMap);
	}
	
	/**
	 * 个人日历页面修改(update EmpCalendar Info)
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEmpCalendarInfo",method = RequestMethod.POST)
	@ResponseBody
	public String updateEmpCalendarInfo(HttpServletRequest request)throws Exception{
		
		String returnString = "" ;
		
		int result = this.empCalendarSer.updateEmpCalendarInfo(request) ;
		
		if(result == 1){
			returnString = "Y" ;
		}else{
			returnString = "N" ;
		}
	    
		return returnString;
	}
	
	//个人日历明细
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewEmpCalendarCheckInfoList")
	public ModelAndView viewEmpCalendarCheckInfoList (HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception{
		
		List empCalendarList = this.empCalendarSer.viewEmpCalendarCheckInfoList("viewEmpCalendarCheckInfoList", request);
		request.setAttribute("RESULT_FLAG", "E");
		List empCalendarListFail = this.empCalendarSer.viewEmpCalendarCheckInfoList("viewEmpCalendarCheckInfoList", request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","400223");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		//List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		//modelMap.put("codeList", codeList);
		List shiftList = shiftSer.getShiftList1(request);
		modelMap.put("shiftList", shiftList) ;
		
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag !=null || !"".equals(firstFlag)) {
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
		modelMap.put("seach_SHIFT_NO", request.getParameterValues("seach_SHIFT_NO"));
		modelMap.put("empCalendarInfoList", empCalendarList);
		modelMap.put("empCalendarInfoListCnt", empCalendarList.size());
		modelMap.put("empCalendarListFailCnt", empCalendarListFail.size());
		return new ModelAndView("/ar/attendanceMintenance/viewEmpCalendarCheckInfoList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelCalendarData")
	@ResponseBody
	public Map submitImportExcelCalendarData (HttpServletRequest request,	HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.empCalendarSer.submitImportExcelCalendarData(request, "PKG_ATT_EXCEL_EXP.PR_IMPORT_CALENDAR_DATA");
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
	
}
