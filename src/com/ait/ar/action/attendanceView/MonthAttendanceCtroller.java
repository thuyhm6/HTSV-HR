package com.ait.ar.action.attendanceView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.Interface.ParentCtroller;
import com.ait.ar.service.CycleSer;
import com.ait.ar.service.MonthAttendanceSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AjaxSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: MonthAttendanceCtroller.java
 * @Description:
 * @Create date: 2012-5-16 下午08:35:55
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceView")
public class MonthAttendanceCtroller extends ParentCtroller{
Logger logger = Logger.getLogger(MonthAttendanceCtroller.class);
	
	@Autowired
	private MonthAttendanceSer monthAttendanceSer;
	@Autowired
    private AjaxSer ajaxSer;
		
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private CycleSer cycleSer;
	
	/**
	 * 个人考勤页面(view Personal Attendance)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewMonthAttendance")
	public ModelAndView viewPersonalAttendance(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		//默认
		String menuNo = "2385";
		
		String dataTable = this.monthAttendanceSer.makeDataTable(request, menuNo) ;
		
		modelMap.put("dataTable", dataTable) ;
		
		modelMap.put("deptNO", request.getParameter("deptNO")) ;
		
		modelMap.put("arYear", request.getParameter("arYear")) ;
		
		modelMap.put("arMonth", request.getParameter("arMonth")) ;
		
		return new ModelAndView("/ar/attendanceView/viewMonthAttendance",modelMap);
	}
	

	/**
	 * 考勤查看--月上班日程
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewMonthWorkSchedule")
	public ModelAndView viewMonthWorkSchedule(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		String ddate = "";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    ddate=sdf.format(dt);
	    String b[] = ddate.split("-");
	    if(request.getParameter("seach_PA_YEAR")==null || "".equals(request.getParameter("seach_PA_YEAR").toString())){
	    	modelMap.put("PA_YEAR", b[0].trim().toString());
	    }else{
	    	modelMap.put("PA_YEAR", request.getParameter("seach_PA_YEAR").toString());
	    }
	    modelMap.put("paYear", request.getParameter("seach_paYear"));
	    modelMap.put("paMonth", request.getParameter("seach_paMonth"));
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("STAT_NO", request.getParameter("seach_STAT_NO"));
		modelMap.put("EMPID", request.getParameter("seach_EMPID"));
		
		modelMap.put("pageNum", request.getParameter("pageNum")!=null?request.getParameter("pageNum"):"1");
		modelMap.put("numPerPage", request.getParameter("numPerPage")!=null?request.getParameter("numPerPage"):"10");
		
		//modelMap.put("timeIntervalList",this.ajaxSer.getTimeIntervalList(request));
		modelMap.put("qujianList",this.ajaxSer.getTimeIntervalList(request));
		modelMap.put("monthDay",this.ajaxSer.getDayAll(request));
		modelMap.put("monthWorkSchedule",this.monthAttendanceSer.getMonthWorkSchedule(request));	
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.monthAttendanceSer.getMonthWorkScheduleCnt(request));
		
		return new ModelAndView("/ar/attendanceView/viewMonthWorkSchedule",modelMap);
	}
	
	/**
	 * 考勤查看--月上班日程，导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewMonthWorkScheduleExcel")
	public ModelAndView viewMonthWorkScheduleExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("monthDay",this.ajaxSer.getDayAll(request));
		modelMap.put("monthWorkSchedule",this.monthAttendanceSer.getMonthWorkScheduleExcel(request));	
		
		return new ModelAndView("/ar/attendanceView/viewMonthWorkScheduleExcel",modelMap);
	}
	
	/**
	 * 考勤查看--月考勤查看（个人）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewMonthWorkList")
	public ModelAndView viewMonthWorkList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		initMenu(request, modelMap);
		//modelMap.put("pageNum", request.getParameter("pageNum")!=null?request.getParameter("pageNum"):"2");
		//modelMap.put("numPerPage","60");
		modelMap.put("qujianList",this.ajaxSer.getTimeIntervalList(request));
		//modelMap.put("monthWorkSchedule",this.monthAttendanceSer.getMonthWorkSchedule(request));
		modelMap.put("monthWorkList",this.monthAttendanceSer.getMonthWorkList(request));
		//modelMap.put("everyDayWorkList",this.monthAttendanceSer.getEveryDayWorkList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.monthAttendanceSer.getMonthWorkListCnt(request));
		return new ModelAndView("/ar/attendanceView/viewMonthWorkList",modelMap);
	}
	
	/**
	 * 未刷卡查询
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * <!--ar.attendanceView.viewNoSwipingCard.deptName              = 部门-->
	 *	<!--ar.attendanceView.viewNoSwipingCard.beginTime             = 开始日期-->
	 *	<!--ar.attendanceView.viewNoSwipingCard. endTime               = 结束日期-->
	 *	<!--ar.attendanceView.viewNoSwipingCard. status                = 状态-->
	 *	<!--ar.attendanceView.viewNoSwipingCard.attendanceDistinct    = 考勤区分-->
	 * public.title.empIdAndName
	 */
	@RequestMapping(value = "/viewNoSwipingCardList")
	public ModelAndView viewNoSwipingCardList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		//查询为刷卡的数据
		List noSwipingCardList = this.monthAttendanceSer.noSwipingCardList(request);   
		modelMap.put("noSwipingCardList",noSwipingCardList);
		modelMap.put("DEPTNO",request.getParameter("seach_DEPTNO"));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.monthAttendanceSer.noSwipingCardListCnt(request));
		return new ModelAndView("/ar/attendanceView/viewNoSwipingCardList",modelMap);
	}
	
	/**
	 * 考勤查看--月考勤查看（个人），导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewMonthWorkListExcel")
	public ModelAndView viewMonthWorkListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("monthWorkList",this.monthAttendanceSer.getMonthWorkList(request));
		return new ModelAndView("/ar/attendanceView/viewMonthWorkListExcel",modelMap);
	}
	
	
	/**
	 * 考勤查看--年假使用现状
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAnnualUsage")
	public ModelAndView viewAnnualUsageList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String ddate = "";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	    ddate=sdf.format(dt);
	    if(request.getParameter("seach_AR_YEAR")==null || "".equals(request.getParameter("seach_AR_YEAR").toString())){
	    	modelMap.put("AR_YEAR", ddate.trim().toString());
	    }else{
	    	modelMap.put("AR_YEAR", request.getParameter("seach_AR_YEAR").toString());
	    }
	    if(request.getParameter("seach_DEPT_NO" ) == null || "".equals(request.getParameter("seach_DEPT_NO") )){
	    	modelMap.put("DEPT_NO", admin.getDeptNo());
	    }else{
	    	modelMap.put("DEPT_NO", request.getParameter("seach_DEPT_NO").toString());
	    }
	    /*
	    if(request.getParameter("seach_AR_MONTH")==null || "".equals(request.getParameter("seach_AR_MONTH").toString())){
	    	modelMap.put("AR_MONTH", b[1].trim().toString());
	    }else{
	    	modelMap.put("AR_MONTH", request.getParameter("seach_AR_MONTH").toString());
	    }
	    */
	    List annualUsageList = this.monthAttendanceSer.getAnnualUsage(request);
	    int annualUsageListCnt = this.monthAttendanceSer.getAnnualUsageCnt(request);
	    modelMap.put("defaultCpny", admin.getCpnyId());
		//modelMap.put("DEPT_NO", request.getParameter("seach_DEPT_NO"));
		modelMap.put("KEY", request.getParameter("seach_KEY"));
		modelMap.put("annualUsageList",annualUsageList);	
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, annualUsageListCnt);	
		           
		return new ModelAndView("/ar/attendanceView/viewAnnualUsage",modelMap);
	}	
		
	/**
	 * 考勤查看--年假使用现状
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/showAnnualUsageList")
	public ModelAndView showAnnualUsageList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List annualUsageFactList = this.monthAttendanceSer.getAnnualUsageFact(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("annualUsageFactList",annualUsageFactList);
		return new ModelAndView("/ar/attendanceView/showAnnualUsageList",modelMap);
				}	
	/**
	 * 考勤查看--年假使用现状，导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAnnualUsageExcel")
	public ModelAndView viewAnnualUsageExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		String ddate = "";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    ddate=sdf.format(dt);
	    String b[] = ddate.split("-");
	    if(request.getParameter("seach_AR_YEAR")==null || "".equals(request.getParameter("seach_AR_YEAR").toString())){
	    	modelMap.put("AR_YEAR", b[0].trim().toString());
	    }else{
	    	modelMap.put("AR_YEAR", request.getParameter("seach_AR_YEAR").toString());
	    }
	    if(request.getParameter("seach_AR_MONTH")==null || "".equals(request.getParameter("seach_AR_MONTH").toString())){
	    	modelMap.put("AR_MONTH", b[1].trim().toString());
	    }else{
	    	modelMap.put("AR_MONTH", request.getParameter("seach_AR_MONTH").toString());
	    }
	    
	    List annualUsageList = this.monthAttendanceSer.getAnnualUsageExcel(request);
	    
		modelMap.put("DEPT_NO", request.getParameter("seach_DEPT_NO"));
		modelMap.put("KEY", request.getParameter("seach_KEY"));
		modelMap.put("annualUsageList",annualUsageList);
		
		return new ModelAndView("/ar/attendanceView/viewAnnualUsageExcel",modelMap);
	}
	
	/**
	 * 月考勤查看(个人新)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-17 上午10:38:00 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewMonthWorkNewOneList")
	public ModelAndView viewMonthWorkNewOne(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	    modelMap.put("paYear", request.getParameter("seach_paYear"));
	    modelMap.put("paMonth", request.getParameter("seach_paMonth"));
		modelMap.put("condition", request.getParameter("seach_condition"));
		modelMap.put("STAT_NO", request.getParameter("seach_STAT_NO")!=null?request.getParameter("seach_STAT_NO"):admin.getStatNo());	
		
		modelMap.put("qujianList",this.ajaxSer.getTimeIntervalList(request));		
		modelMap.put("monthWorkList",this.monthAttendanceSer.getMonthWorkList(request));
		
		
        //ESS시스템에서 사용하는 경우 사번검색기능을 막고 로그인한 사용자의 사번만으로 조회하도록 고정하기 위한 처리
		
		String empId = admin.getEmpID();
		String personId = admin.getPersonId();
		modelMap.put("empId", empId);
		modelMap.put("personId", personId);		

        modelMap.put("menuNo", (String)request.getParameter("menuNo"));        
        
		return new ModelAndView("/ar/attendanceView/viewMonthWorkNewOneList",modelMap);
	}
	/**
	 * 月考勤监控
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-27 下午05:54:27 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewMonthWorkControl")
	public ModelAndView viewMonthWorkControlList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	
		int dataListCnt = monthAttendanceSer.getMonthControlListCnt(request);
		modelMap.put("controlMap", monthAttendanceSer.getControlItemMap(request));
		modelMap.put("dataList", monthAttendanceSer.getMonthControlList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, dataListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217852"));
		return new ModelAndView("/ar/attendanceView/viewMonthWorkControl",modelMap);
	}
	
	/**
	 * 月考勤监控Excel导出
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-27 下午05:54:27 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewMonthWorkControlExcel")
	public ModelAndView viewMonthWorkControlExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("controlMap", monthAttendanceSer.getControlItemMap(request));
		modelMap.put("dataList", monthAttendanceSer.getMonthControlList(request));
		modelMap.put("ITEM1", request.getParameter("seach_ITEM1"));
		modelMap.put("ITEM2", request.getParameter("seach_ITEM2"));
		modelMap.put("ITEM3", request.getParameter("seach_ITEM3"));
		modelMap.put("ITEM4", request.getParameter("seach_ITEM4"));
		modelMap.put("ITEM5", request.getParameter("seach_ITEM5"));
		modelMap.put("START_DATE", request.getParameter("seach_START_DATE"));
		modelMap.put("END_DATE", request.getParameter("seach_END_DATE"));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217852"));
		return new ModelAndView("/ar/attendanceView/viewMonthWorkControlExcel",modelMap);
	}

	/**
	 * 考勤决裁例外
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArAffirmExceptionList")
	public ModelAndView viewArMonthPersonInfoEssList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List arAfrimExceptionList = this.monthAttendanceSer.getArAfrimExceptionList(request) ;		
		int  arAfrimCnt = this.monthAttendanceSer.getArAfrimExceptionCnt(request) ;
		
		modelMap.put("arException", arAfrimExceptionList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, arAfrimCnt) ;
		return new ModelAndView("/ar/attendanceView/viewArAffirmExceptionList",
				modelMap);
	}

	
	
	/**
	 * 考勤决裁例外临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportArExceptionTempList")
	public ModelAndView viewImportArExceptionTempList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paArExceptionTempList = this.monthAttendanceSer.getArExceptionTempList(request);
		int paArExceptionTempCnt = this.monthAttendanceSer.getArExceptionTempCnt(request , "T");
		int errorCnt = this.monthAttendanceSer.getArExceptionTempCnt(request , "E");
		
		modelMap.put("paArExceptionTempList", paArExceptionTempList);
		modelMap.put("paArExceptionTempCnt", paArExceptionTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paArExceptionTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paArExceptionTempCnt);
		return new ModelAndView("/ar/attendanceView/viewImportArExceptionTempList", modelMap);
	}
/**
	 * 日考勤监控
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-27 下午05:54:27 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewDetailWorkControl")
	public ModelAndView viewDetailWorkControlList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		int dataListCnt = monthAttendanceSer.getDetailControlListCnt(request);
		modelMap.put("controlMap", monthAttendanceSer.getControlItemMap(request));
		modelMap.put("dataList", monthAttendanceSer.getDetailControlList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, dataListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217852"));
		return new ModelAndView("/ar/attendanceView/viewMonthWorkControl",modelMap);
	}
	
	/**
	 * 日考勤监控Excel导出
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-27 下午05:54:27 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewDetailWorkControlExcel")
	public ModelAndView viewDetailWorkControlExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("controlMap", monthAttendanceSer.getControlItemMap(request));
		modelMap.put("dataList", monthAttendanceSer.getDetailControlList(request));
		modelMap.put("ITEM1", request.getParameter("seach_ITEM1"));
		modelMap.put("ITEM2", request.getParameter("seach_ITEM2"));
		modelMap.put("ITEM3", request.getParameter("seach_ITEM3"));
		modelMap.put("ITEM4", request.getParameter("seach_ITEM4"));
		modelMap.put("ITEM5", request.getParameter("seach_ITEM5"));
		modelMap.put("START_DATE", request.getParameter("seach_START_DATE"));
		modelMap.put("END_DATE", request.getParameter("seach_END_DATE"));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217852"));
		return new ModelAndView("/ar/attendanceView/viewDetailWorkControlExcel",modelMap);
	}
	
	/**
	 * 各种申请查看
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-27 下午05:54:27 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewAllApplyList")
	public ModelAndView viewAllApplyList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String first_flag = request.getParameter("first_flag");
		modelMap.put("controlMap", monthAttendanceSer.getItemForApplyList(request));
		modelMap.put("dataList", monthAttendanceSer.getAllApplyList(request));
		/*没有开始结束时间默认当天*/
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
		Calendar c = Calendar.getInstance();    
		if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
			//获取当前年第一天：
			/*c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH,1);*/
			String first = format.format(c.getTime());
			modelMap.put("START_DATE",first);
			//获取当前月最后一天：
			c = Calendar.getInstance();  
			/*c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));*/
			String last = format.format(c.getTime());
			modelMap.put("END_DATE",last);
		}
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "217852"));
		return new ModelAndView("/ar/attendanceView/viewAllApplyList",modelMap);
	}
	

	/** 
	* @Title: getEmpTypeForGroupToList 
	* @Description: TODO 
	* @param @param request
	* @param @return
	* @param @throws Exception    
	* @return Map    
	* @throws 
	*/
	@RequestMapping(value = "/getItemForApplyList")
	@ResponseBody
	public Map getItemForApplyList(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		List result = this.monthAttendanceSer.getItemForApplyList(request);
		map.put("statusCode", "200");
		map.put("result", result);
		return map;
	}
}