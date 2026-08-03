package com.ait.ar.action.attendanceVacations;

import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.GregorianCalendar;
import java.util.HashMap;

import java.util.GregorianCalendar;
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

import com.ait.ar.service.ArVacationSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;


@Controller
@RequestMapping(value = "/ar/attendanceVacations")
public class ArVacationCtroller {
Logger logger = Logger.getLogger(ArVacationCtroller.class) ;
	
	@Autowired
	private ArVacationSer arVacationSer ;
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	
	/**
	 * 年休假清算(view ArVacation Liquidation)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArVacationLiquidation")
	public ModelAndView viewArVacationLiquidation(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List arVacationLiquidationList = this.arVacationSer.getArVacationLiquidationList(request);
		int arVacationLiquidationCnt=this.arVacationSer.getArVacationLiquidationCnt(request);
		 //List arVacationUpdateYearList = this.arVacationSer.getArVacationUpdateYearList(request);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, arVacationLiquidationCnt) ;
		modelMap.put("defaultCpny", admin.getCpnyId());	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date= df.format(new Date()).toString();// new Date()为获取当前系统时间
		
		modelMap.put("year", date.substring(0,4));	
		modelMap.put("liquidation", request.getParameter("liquidation"));	
		modelMap.put("arVacationLiquidationList", arVacationLiquidationList);		
	    return new ModelAndView("/ar/attendanceVacations/viewArVacationLiquidation", modelMap);
	}
	
	
	/**
	 * 年休假清算(Excel)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArVacationLiquidationExcel")
	public ModelAndView viewArVacationLiquidationExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		List arVacationLiquidationList = this.arVacationSer.getArVacationLiquidationList(request);
		
		modelMap.put("arVacationLiquidationList", arVacationLiquidationList) ;
		
	    return new ModelAndView("/ar/attendanceVacations/viewArVacationLiquidationExcel", modelMap);
	}
	
	/**
	 *  清算年假(add Vacation Liquidation)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveVacationLiquidation")
	@ResponseBody
	public Map saveVacationLiquidation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List arVacationLiquidationList = this.arVacationSer.getArVacationLiquidationList(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.arVacationSer.saveVacationLiquidation(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
			map.put("navTabId", "hr0101");
		 	//map.put("forwardUrl","/hrm/empinfo/viewPersonalInfo?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		
		return map;
	}
	
	/**
	 * 显示月年休假更新页面(view ArAnnual update month)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArVacationMonth")
	public ModelAndView viewArVacationMonth(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		List arVacationUpdateMonthList = this.arVacationSer.getArVacationUpdateMonthList(request);
		
		modelMap.put("year", request.getParameter("year"));
		modelMap.put("month", request.getParameter("month"));
		modelMap.put("arVacationUpdateMonthList", arVacationUpdateMonthList) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "125073")) ;
		
		
	    return new ModelAndView("/ar/attendanceVacations/viewArVacationMonth", modelMap);
	}
	
	/**
	 * 显示月年休假更新明细页面(view detail ArAnnual month)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArVacationMonthExcel")
	public ModelAndView viewArVacationMonthExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		List vacationEmpList = this.arVacationSer.getArVacationMonthExcel(request);
		
		modelMap.put("vacationEmpList", vacationEmpList) ;
		
	    return new ModelAndView("/ar/attendanceVacations/viewArVacationMonthExcel", modelMap);
	}
	
	
	/**
	 * 月年假更新计算(monthVacation Calculate)
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/monthVacationCal")
	@ResponseBody
	public void monthVacationCal(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");
        
        String returnString = this.arVacationSer.monthVacCalculate(request) ;
        
        PrintWriter out = response.getWriter();
        
        out.println(JsonUtil.writeInternal(returnString));
        
		out.flush();
		out.close();
	}
	/**
	 * 年休假查看(view Leave the view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewLeaveView")
	public ModelAndView viewLeaveView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List viewLeaveViewList = this.arVacationSer.getLeaveViewList(request);
		String yearb="";
		if(request.getParameter("seach_inYear")!=null){
			yearb=String.valueOf(Integer.parseInt(request.getParameter("seach_inYear"))-1);
		}else{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String date= df.format(new Date()).toString();// new Date()为获取当前系统时间
			yearb=String.valueOf(Integer.parseInt(date.substring(0,4))-1);
		
		}
		//int renewContractCnt=this.arVacationSer.getLeaveViewListCnt(request);
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, renewContractCnt) ;
		modelMap.put("yearb", yearb);
		modelMap.put("year", request.getParameter("seach_inYear"));
		modelMap.put("KEY", request.getParameter("seach_KEY"));
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("month", request.getParameter("seach_inMonth"));
		modelMap.put("viewLeaveViewList", viewLeaveViewList) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "125075")) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
	    return new ModelAndView("/ar/attendanceVacations/viewLeaveView", modelMap);
	}
	

	/**
	 * 年假信息查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/viewLeaveViewForSearchExcel")
	public ModelAndView viewLeaveViewForSearchExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List viewLeaveViewList = this.arVacationSer.getLeaveViewList(request);
		modelMap.put("viewLeaveViewList",viewLeaveViewList);
		modelMap.put("defaultCpny", admin.getCpnyId());		
		return new ModelAndView("/ar/attendanceVacations/viewLeaveViewForSearchExcel",modelMap);
	}
	
	/**
	 * 月年假生成(monthVacation Create)
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/monthVacationCreate")
	@ResponseBody
	public void monthVacationCreate(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");
        
        String returnString = "";
        
        
        
        if (this.arVacationSer.RetrieveAttStatus(request) == 0) {
        	returnString = "考勤没有锁定";
		}else if (this.arVacationSer.monthVacationCnt(request) > 0) {
			returnString = "年假已经更新";
		}else {
			returnString = this.arVacationSer.monthVacCreate(request) ;
		}
        
        PrintWriter out = response.getWriter();
        
        out.println(JsonUtil.writeInternal(returnString));
        
		out.flush();
		out.close();
	}
	
	/**
	 * 显示月年休假更新页面(view ArAnnual update month)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArVacationYear")
	public ModelAndView viewArVacationYearList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		List arVacationUpdateYearList = this.arVacationSer.getArVacationUpdateYearList(request);
		int listCnt = this.arVacationSer.getArVacationUpdateYearCnt(request) ;
		
		
		modelMap.put("vac_id", request.getParameter("vac_id"));
		modelMap.put("arVacationUpdateYearList", arVacationUpdateYearList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, listCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "125076")) ;
		
		
	    return new ModelAndView("/ar/attendanceVacations/viewArVacationYear", modelMap);
	}
	
	/**
	 * 显示年年休假更新明细页面(view detail ArAnnual year)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArVacationYearExcel")
	public ModelAndView viewArVacationYearExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		List arVacationUpdateYearList = this.arVacationSer.getArVacationUpdateYearList(request);
		
		modelMap.put("arVacationUpdateYearList", arVacationUpdateYearList) ;
		
	    return new ModelAndView("/ar/attendanceVacations/viewArVacationYearExcel", modelMap);
	}
	
	/**
	 * 修改年假信息(update ArVacation Info)
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateArVacationYear", method = RequestMethod.POST)
	@ResponseBody
	public Map updateArVacationYear(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.arVacationSer.updateArVacationYear(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));// 修改成功
			map.put("navTabId", "ar0503");
			//map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));// 修改失败
		}
		return map;

	}
	
	/**
	 * 月年假更新计算(yearVacation Calculate)
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/yearVacationCal")
	@ResponseBody
	public Map yearVacationCal(HttpServletRequest request,HttpServletResponse response)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
        String returnString = this.arVacationSer.yearVacCalculate(request) ;
		if ("OK".equals(returnString)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("evs.viewProbationResumeList.SHENGCHENGGONGGONG.a", request));// 生成成功
			map.put("formId", "viewVacEmpListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", returnString);// 修改失败
		}
		return map;
        
	}
	
	/**
	 * 年年假生成(yearVacation Create)
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/yearVacationCreate")
	@ResponseBody
	public void yearVacationCreate(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");
        
        String returnString = "";
        
        if (this.arVacationSer.yearVacationCnt(request) > 0) {
			returnString = "已经生成不能重复生成";
		}else {
			returnString = this.arVacationSer.yearVacCreate(request) ;
		}
        
        PrintWriter out = response.getWriter();
        
        out.println(JsonUtil.writeInternal(returnString));
        
		out.flush();
		out.close();
	}
	
	/**
	 * 显示年休假移年页面(view ArAnnual next)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArVacationNext")
	public ModelAndView viewArVacationNextList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		String vac_id = request.getParameter("vac_id") ;
		if(vac_id == null){
			vac_id = new java.text.SimpleDateFormat("yyyy").format(new GregorianCalendar().getTime());
		}
		
		List arVacationEmpList = this.arVacationSer.getArVacationNextList(request);
		int listCnt = this.arVacationSer.getArVacationNextCnt(request) ;
		
		
		modelMap.put("vac_id", vac_id);
		modelMap.put("arVacationEmpList", arVacationEmpList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, listCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "125077")) ;
		
		
	    return new ModelAndView("/ar/attendanceVacations/viewArVacationNext", modelMap);
	}
	
	/**
	 * 显示年年休假更新明细页面(view detail ArAnnual year)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArVacationNextExcel")
	public ModelAndView viewArVacationNextExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		List arVacationEmpList = this.arVacationSer.getArVacationNextList(request);
		
		modelMap.put("arVacationEmpList", arVacationEmpList) ;
		
	    return new ModelAndView("/ar/attendanceVacations/viewArVacationNextExcel", modelMap);
	}
	
	/**
	 * 年年假生成(yearVacation Create)
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/nextVacationMove")
	@ResponseBody
	public void nextVacationMove(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");
        
        String returnString = "";
        
        returnString = this.arVacationSer.nextVacationMove(request) ;
        
        PrintWriter out = response.getWriter();
        
        out.println(JsonUtil.writeInternal(returnString));
        
		out.flush();
		out.close();
	}
	
	
	/**
	 *  进入TA 法人生成福利年假(view detail ArAnnual year)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTAWelfare")
	public ModelAndView viewTAWelfare(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		//List arVacationEmpList = this.arVacationSer.getArVacationNextList(request);
		
		//modelMap.put("arVacationEmpList", arVacationEmpList) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId());
		
	    return new ModelAndView("/ar/attendanceVacations/viewTAWelfare", modelMap);
	}
	
	/**
	 * TA 法人生成福利年假(yearVacation Create)
	 * @param request
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getTAWelfare")
	@ResponseBody
	public void getTAWelfare(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
        response.setHeader("Cache-Control", "no-cache");
        
        String returnString = "";
        
        returnString = this.arVacationSer.getTAWelfare(request) ;
        
        PrintWriter out = response.getWriter();
        
        out.println(JsonUtil.writeInternal(returnString));
        
		out.flush();
		out.close();
	}
	
	
	
}
