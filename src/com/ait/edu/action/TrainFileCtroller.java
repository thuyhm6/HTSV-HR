package com.ait.edu.action;

import java.sql.Clob;
import java.util.LinkedHashMap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.CompanyCalendarSer;
import com.ait.edu.service.TrainEducationSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;


@Controller
@RequestMapping(value = "/edu/trainfile")
public class TrainFileCtroller {
	Logger logger = Logger.getLogger(TrainFileCtroller.class);
	
	@Autowired
	private TrainEducationSer eduTrainser;
	@Autowired
	private CompanyCalendarSer companyCalendarSer;
	
	// 培训日历页面
	@RequestMapping(value = "/trainCalendar")  
	public ModelAndView trainCalendar(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		String calendarHtml = this.companyCalendarSer.getCalendarViewHtml(request) ;
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		modelMap.put("reqest_year", StringUtil.checkNull(request.getParameter("year")));
		modelMap.put("reqest_month", StringUtil.checkNull(request.getParameter("month")));
		modelMap.put("calendarHtml", calendarHtml) ;
		modelMap.put("year", year);
		modelMap.put("month", month);

		return new ModelAndView("/edu/trainfile/trainCalendar",
				modelMap);
	}
	
	//计划管理 点击查看页面
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/trainCalendarDetail")
	public ModelAndView trainCalendarDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.planManagerInfoDetail(request);
		if(linkMap.get("TEACHER_NAME") !=null){
			Clob teacherName = (Clob) linkMap.get("TEACHER_NAME");
			
			String detailinfo = teacherName.getSubString((long)1,(int)teacherName.length());
			
			modelMap.put("TEACHER_NAME", detailinfo);
		}
		modelMap.put("planManagerInfo", linkMap);
		modelMap.put("AR_DATE_STR",request.getParameter("TR_DATE_STR"));
		
		return new ModelAndView("/edu/trainfile/trainCalendarDetail",
				modelMap);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/syllabusInfo")
	public ModelAndView syllabusInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		List slist = eduTrainser.syllabusInfo(request);
		
		modelMap.put("syllabusInfo", slist);
		return new ModelAndView("/edu/trainfile/syllabusInfo",
				modelMap);
	}
	
	// 个人培训日历页面
	@RequestMapping(value = "/personalTrainCalendar")  
	public ModelAndView personalTrainCalendar(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		String calendarHtml = this.companyCalendarSer.getCalendarViewHtml(request) ;
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		modelMap.put("reqest_year", StringUtil.checkNull(request.getParameter("year")));
		modelMap.put("reqest_month", StringUtil.checkNull(request.getParameter("month")));
		modelMap.put("calendarHtml", calendarHtml) ;
		modelMap.put("year", year);
		modelMap.put("month", month);

		return new ModelAndView("/edu/trainfile/personalTrainCalendar",
				modelMap);
	}
	


}
