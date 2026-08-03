package com.ait.ar.action.attendanceView;

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

import com.ait.ar.action.attendanceSettings.CycleCtroller;
import com.ait.ar.service.CycleSer;
import com.ait.ar.service.PersonalAttendanceSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.ViewOptionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PersonalAttendanceCtroller.java
 * @Description:
 * @Create date: 2012-5-9 上午10:20:32
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceView")
public class PersonalAttendanceCtroller {
Logger logger = Logger.getLogger(PersonalAttendanceCtroller.class);
	
	@Autowired
	private PersonalAttendanceSer personalAttendanceSer;
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
	@RequestMapping(value = "/viewPersonalAttendance")
	public ModelAndView viewPersonalAttendance(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		//默认
		String menuNo = "2383";
		
		String dataTable = this.personalAttendanceSer.makeDataTable(request, menuNo) ;
		
		modelMap.put("dataTable", dataTable) ;
		
		modelMap.put("KEY", request.getParameter("KEY")) ;
		
		modelMap.put("arYear", request.getParameter("arYear")) ;
		
		modelMap.put("arMonth", request.getParameter("arMonth")) ;
		
		return new ModelAndView("/ar/attendanceView/viewPersonalAttendance",modelMap);
	}
}