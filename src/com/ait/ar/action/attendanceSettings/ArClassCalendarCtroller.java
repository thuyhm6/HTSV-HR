package com.ait.ar.action.attendanceSettings;


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
import com.ait.ar.service.EmpCalendarSer;
import com.ait.ar.service.EmpShiftSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.SessionUtil;
import java.util.List;

/**
 * 
 * @ClassName: ArClassCalendarCtroller
 * @Description: TODO
 * @author yuanxq@ait.net.cn
 * @date 2014-6-24 下午01:45:51
 * 
 */
@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class ArClassCalendarCtroller {
//班次日历
	Logger logger = Logger.getLogger(ArClassCalendarCtroller.class);
//	@Autowired
//	private EmpCalendarSer empCalendarSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private  EmpShiftSer empShiftSer;
	@Autowired
	private  ArClassCalendarSer arClassCalendarSer;
	@Autowired
	private  EmpCalendarSer empCalendarSer;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewClassCalendar")
	public ModelAndView viewClassCalendar(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
        String cpnyid=admin.getCpnyId();
		String shift=(String) request.getParameter("person_id");
		String GROUP=(String) request.getParameter("GROUP");
//		System.out.println(GROUP+"ss*******+{"+shift);
		if(GROUP==null || GROUP.length() == 0){
			request.setAttribute("GROUP", "400224");
			GROUP="400224";
		}
		//String STAT_NO=GROUP.equals("CH_W2")?"219948":"141436";


		String calendarHtml = arClassCalendarSer
		        .getArClassCalendarViewHtml(request);
		//List shiftNo=arClassCalendarSer.getShiftNo(request);
		

		modelMap.put("empid", admin.getEmpID());
		modelMap.put("GROUP", GROUP);
		modelMap.put("name", admin.getLocalName());
		modelMap.put("deptname", admin.getDepartment());
		modelMap.put("person_id", admin.getPersonId());
		modelMap.put("cpny_id", admin.getCpnyId());
		modelMap.put("MENU_CODE", request.getParameter("MENU_CODE")==null?request.getParameter("navTabId"):request.getParameter("MENU_CODE"));
		String menuNoStr = "14013715";
		if(((String)modelMap.get("MENU_CODE")).indexOf("ar")!=-1)
			menuNoStr = "216695";
		//modelMap.put("STAT_NO", STAT_NO);
		modelMap.put("calendarHtml", calendarHtml);
		//modelMap.put("shiftNo", shiftNo);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request,menuNoStr));
		//modelMap.put("shiftsList", shiftsList) ;


		return new ModelAndView("/ar/attendanceSettings/viewClassCalendar",
				modelMap);
	}
	
	@RequestMapping(value = "/updateClassCalendarInfo",method = RequestMethod.POST)
	@ResponseBody
	public String updateClassCalendarInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
        String returnString = "" ;
		
		int result = this.empCalendarSer.updateClassCalendarInfo(request) ;
		
		if(result == 1){
			returnString = "Y" ;
		}else{
			returnString = "N" ;
		}
	    
		return returnString;
	}
	@RequestMapping(value = "/createArDEtailClassCalendarInfo",method = RequestMethod.POST)
	@ResponseBody
	public ModelMap createArDEtailClassCalendarInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
        String returnString = "" ;
		
		int result = this.empCalendarSer.createArDEtailClassCalendarInfo(request) ;
		
		if(result == 1){
			modelMap.put("statusCode", "200");
		}else{
			modelMap.put("statusCode", "300");
		}
	    
		return modelMap;
	}
 }
