package com.ait.ar.action.attendanceMintenance;

import java.util.LinkedHashMap;
import java.util.List;

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
import com.ait.ar.service.DailyDetailSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: DailyDetailCtroller.java
 * @Description:
 * @Create date: 2012-2-6 上午10:49:39
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class DailyDetailCtroller {
	Logger logger = Logger.getLogger(DailyDetailCtroller.class);
	@Autowired
	private DailyDetailSer dailyDetailSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private CycleSer cycleSer;
	/**
	 * 个人日历查看页面(add EmpShift View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewDailyDetail")
	public ModelAndView addEmpShiftView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
	   String calendarHtml = this.dailyDetailSer.getDailyDetailViewHtml(request) ;
	   
	   HttpSession session = request.getSession() ;
	   AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
	   if (ObjectUtils.toString(request.getParameter("person_id")).equals("")) {
			modelMap.put("empid", admin.getEmpID()) ;
			modelMap.put("name", admin.getLocalName()) ;
			modelMap.put("deptname", admin.getDepartment()) ;
			modelMap.put("person_id", admin.getPersonId()) ;
			modelMap.put("cpny_id", admin.getCpnyId()) ;
			modelMap.put("STAT_NO", admin.getStatNo() != null ? admin.getStatNo() : "") ;
		}else{
			LinkedHashMap empMap = (LinkedHashMap)this.dailyDetailSer.getEmpInfo(request);
			modelMap.put("empid", empMap.get("EMPID").toString()) ;
			modelMap.put("name", empMap.get("LOCAL_NAME").toString()) ;
			modelMap.put("deptname", empMap.get("DEPTNAME").toString()) ;
			modelMap.put("person_id", empMap.get("PERSON_ID").toString()) ;
			modelMap.put("cpny_id", empMap.get("CPNY_ID").toString()) ;
			modelMap.put("STAT_NO", empMap.get("STAT_NO") != null ? empMap.get("STAT_NO").toString() : "") ;
        }
	    modelMap.put("reqest_year", StringUtil.checkNull(request.getParameter("year")));
	    modelMap.put("reqest_month", StringUtil.checkNull(request.getParameter("month")));
		modelMap.put("calendarHtml", calendarHtml) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "121971")) ;
		return new ModelAndView("/ar/attendanceMintenance/viewDailyDetail",modelMap);
	}

	/**
	 * 个人日历修改页面(update DailyDetail View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDailyDetailView",method = RequestMethod.GET)
	public ModelAndView updateDailyDetailView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		String calendarHtml = this.dailyDetailSer.getDailyDetailViewHtml(request) ;
		
		modelMap.put("calendarHtml", calendarHtml) ;
		
		return new ModelAndView("/ar/attendanceMintenance/viewDailyDetail",modelMap);
	}
	
	/**
	 * 个人日历页面修改(update DailyDetail Info)
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDailyDetailInfo",method = RequestMethod.POST)
	@ResponseBody
	public String updateDailyDetailInfo(HttpServletRequest request)throws Exception{
		
		String returnString = "" ;
		
		int result = this.dailyDetailSer.updateDailyDetailInfo(request) ;
		
		if(result == 1){
			returnString = "Y" ;
		}else{
			returnString = "N" ;
		}
	    
		return returnString;
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewDailyDetailList")
	public ModelAndView viewEmpCalendarList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("personList", dailyDetailSer.getDailyDetailPersonList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, dailyDetailSer.getDailyDetailPersonCnt(request));
		
		return new ModelAndView("/ar/attendanceMintenance/viewDailyDetailList", modelMap);
	}
}
