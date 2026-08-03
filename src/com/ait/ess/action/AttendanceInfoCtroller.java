package com.ait.ess.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.ait.ess.service.AttendanceInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.ViewOptionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AttendanceInfoCtroller.java
 * @Description:
 * @Create date: 2012-5-23 下午06:13:14
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/infoView")
public class AttendanceInfoCtroller {
Logger logger = Logger.getLogger(AttendanceInfoCtroller.class);
	
	@Autowired
	private AttendanceInfoSer attendanceInfoSer;
	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 个人考勤页面(view Personal Attendance)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAttendanceInfo")
	public ModelAndView viewPersonalAttendance(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		//默认
		String menuNo = "2505";
		
		String dataTable = this.attendanceInfoSer.makeDataTable(request, menuNo) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		modelMap.put("view_EMPID", admin.getEmpID());
		
		modelMap.put("view_LOCALNAME", admin.getLocalName());
		
		modelMap.put("dataTable", dataTable) ;
		
		modelMap.put("essYear", request.getParameter("essYear")) ;
		
		modelMap.put("essMonth", request.getParameter("essMonth")) ;
		
		return new ModelAndView("/ess/infoView/viewAttendanceInfo",modelMap);
	}
	/******************************20150206 zyh start**************************************/
	/**
	 * 个人考勤追溯查看页面(view Personal Attendance)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonalAttendanceBack")
	public ModelAndView viewPersonalAttendanceBack(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Date d=new Date();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PERSON_ID", admin.getPersonId());//只能查询自己的
		if (paramMap.get("sDate") == null && paramMap.get("eDate") == null) {// 页面搜索
			SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");  //数据库中的格式为yy/mm/dd 使用yy-mm-dd查不出数据 
		    modelMap.put("sDate", df.format(new Date(d.getTime() - 1 * 24 * 60 * 60 * 1000)));
			modelMap.put("eDate", df.format(new Date(d.getTime() - 1 * 24 * 60 * 60 * 1000))); //开始时间和结束时间为同一天(昨天的日期)
		}else{
			 modelMap.put("sDate", paramMap.get("sDate"));
			 modelMap.put("eDate",paramMap.get("eDate")); //开始时间和结束时间为同一天(昨天的日期)
		}
		modelMap.put("interCpnyID",paramMap.get("interCpnyID")); 
		List list=this.attendanceInfoSer.getPersonalAttendanceBack(request,modelMap) ;
		int count=this.attendanceInfoSer.getPersonalAttendanceBackCnt(request,modelMap) ;
		modelMap.put("PersonalAttendanceList",list);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, count);
		return new ModelAndView("/ess/infoView/viewPersonalAttendanceBack",modelMap);
	}
	/******************************20150206 zyh end**************************************/
}