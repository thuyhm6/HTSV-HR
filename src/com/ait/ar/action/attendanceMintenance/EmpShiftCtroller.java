package com.ait.ar.action.attendanceMintenance;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.AttendanceKeeperSer;
import com.ait.ar.service.CycleSer;
import com.ait.ar.service.DynamicGroupSer;
import com.ait.ar.service.EmpShiftSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpShiftCtroller.java
 * @Description:
 * @Create date: 2012-1-17 下午03:28:23
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class EmpShiftCtroller {
Logger logger = Logger.getLogger(EmpShiftCtroller.class);
	
	@Autowired
	private  EmpShiftSer empShiftSer;
	@Autowired
	private DynamicGroupSer dynamicGroupSer ;
	@Autowired
	private AttendanceKeeperSer attendanceKeeperSer ;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private CycleSer cycleSer;
	/**
	 * 查看员工排班页面(add EmpShift View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEmpShiftView")
	public ModelAndView addEmpShiftView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List dynamicGroupList = this.dynamicGroupSer.getDynamicGroup1List(request) ;
		List shiftsList = this.empShiftSer.getShift010List1(request);
		modelMap.put("dynamicGroupList", dynamicGroupList) ;
		 modelMap.put("shiftsList", shiftsList) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2359")) ;
		return new ModelAndView("/ar/attendanceMintenance/addEmpShiftView",modelMap);
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpList")
	public ModelAndView viewEmpList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("personList", attendanceKeeperSer.getPersonListView(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendanceKeeperSer.getPersonListCnt(request));
		
		return new ModelAndView("/ar/attendanceMintenance/viewEmpList", modelMap);
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewShiftEmpList")
	public ModelAndView viewShiftEmpList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		//modelMap.put("positionList", attendanceKeeperSer.getPositionList(request));
		modelMap.put("personList", attendanceKeeperSer.getPersonListView(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendanceKeeperSer.getPersonListCnt(request));
		
		return new ModelAndView("/ar/attendanceMintenance/viewShiftEmpList", modelMap);
	}
	
	/**
	 * 按人员添加排班人员(add Emp Shift)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEmpShift")
	@ResponseBody
	public LinkedHashMap addEmpShift(HttpServletRequest request,
				HttpServletResponse response) throws Exception{
		
		int result = this.empShiftSer.addEmpShift(request);
		int count =request.getParameter("empids").toString().split(",").length;
		String returnString = "" ;
		LinkedHashMap map = new LinkedHashMap();
		if(result == 1){
			returnString = "Y" ;
		}else{
			returnString = "N" ;
		}
	    map.put("returnString", returnString);
	    map.put("count", count);
		return map;		
	}
	
	/**
	 * 按部门添加排班人员(add EmpShift ByDeptId)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEmpShiftByDeptId",method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap addEmpShiftByDeptId(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
       
		int result = this.empShiftSer.addEmpShiftByDeptId(request);
		int count = this.empShiftSer.getPersonCountByDep(request);
		String returnString = "" ;
		LinkedHashMap map = new LinkedHashMap();
		if(result == 1){
			returnString = "Y" ;
		}else{
			returnString = "N" ;
		}
		map.put("returnString", returnString);
	    map.put("count", count);
		return map;
	}
	
	/**
	 * 按动态组添加排班人员(add EmpShift BydynamicGroup)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEmpShiftBydynamicGroup",method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap addEmpShiftBydynamicGroup(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
       
		int result = this.empShiftSer.addEmpShiftBydynamicGroup(request);
		int count = this.empShiftSer.getPersonCountByDYNAMIC(request);
		String returnString = "" ;
        LinkedHashMap map = new LinkedHashMap ();
		if(result == 1){
			returnString = "Y" ;
		}else{
			returnString = "N" ;
		}
	    map.put("returnString", returnString);
	    map.put("count", count);
		return map;
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpCalendarList")
	public ModelAndView viewEmpCalendarList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin =  SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag == null){
			modelMap.put("personList", attendanceKeeperSer.getEmpCalendarList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendanceKeeperSer.getEmpCalendarCnt(request));
		}else{
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("limit", request.getParameter("limit"));
		modelMap.put("isEmployeement", request.getParameter("isEmployeement"));
		return new ModelAndView("/ar/attendanceMintenance/viewEmpCalendarList", modelMap);
	}
	
	

	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAddAffirmList")
	public ModelAndView viewAddAffirmList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin =  SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag == null){
			modelMap.put("personList", attendanceKeeperSer.getEmpCalendarList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendanceKeeperSer.getEmpCalendarCnt(request));
		}else{
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("limit", request.getParameter("limit"));
		modelMap.put("empidStr", request.getParameter("empidStr"));
		modelMap.put("empNameStr", request.getParameter("empNameStr")==null?"empNameStr":request.getParameter("empNameStr"));		
		modelMap.put("personidStr", request.getParameter("personidStr"));
		modelMap.put("personInfoStr", "Info"+request.getParameter("empidStr"));
		modelMap.put("positionIdStr", request.getParameter("positionIdStr")==null?"positionIdStr":request.getParameter("positionIdStr"));
		modelMap.put("deptIdStr", request.getParameter("deptIdStr")==null?"deptIdStr":request.getParameter("deptIdStr"));		
		modelMap.put("isEmployeement", request.getParameter("isEmployeement"));
		return new ModelAndView("/ar/attendanceMintenance/viewAddAffirmList", modelMap);
	}
	

	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAddAffirmEvsList")
	public ModelAndView viewAddAffirmEvsList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin =  SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag == null){
			modelMap.put("personList", attendanceKeeperSer.getEmpCalendarList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendanceKeeperSer.getEmpCalendarCnt(request));
		}else{
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("limit", request.getParameter("limit"));
		modelMap.put("empidStr", request.getParameter("empidStr"));
		modelMap.put("personidStr", request.getParameter("personidStr"));
		modelMap.put("personInfoStr", "Info"+request.getParameter("empidStr"));
		modelMap.put("isEmployeement", request.getParameter("isEmployeement"));
		modelMap.put("index", request.getParameter("index"));
		return new ModelAndView("/ar/attendanceMintenance/viewAddAffirmEvsList", modelMap);
	}
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAddBatchPerList")
	public ModelAndView viewAddBatchPerList(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin =  SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag == null){
			modelMap.put("personList", attendanceKeeperSer.getEmpCalendar2List(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendanceKeeperSer.getEmpCalendar2Cnt(request));
		}else{
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("limit", request.getParameter("limit"));
		modelMap.put("empidStr", request.getParameter("empidStr"));
		modelMap.put("personidStr", request.getParameter("personidStr"));
		modelMap.put("personInfoStr", "Info"+request.getParameter("empidStr"));
		modelMap.put("isEmployeement", request.getParameter("isEmployeement"));
		modelMap.put("empid", "EMPID"+request.getParameter("empidStr"));
		modelMap.put("cpnyid", "CPNYID"+request.getParameter("empidStr"));
		modelMap.put("DEPTNAME", "DEPTNAME"+request.getParameter("empidStr"));
		modelMap.put("GROUP_ID", "GROUP_ID"+request.getParameter("empidStr"));
		modelMap.put("GROUP_NAME", "GROUP_NAME"+request.getParameter("empidStr"));
		return new ModelAndView("/ar/attendanceMintenance/viewAddBatchPerList", modelMap);
	}

	/**
	 * 按班组添加排班人员(add EmpShift BydynamicGroup)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEmpShiftByClassNum",method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap addEmpShiftByClassNum(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
       
		int result = this.empShiftSer.addEmpShiftByClassNum(request);
	//	int count = this.empShiftSer.getPersonCountByDYNAMIC(request);
		String returnString = "" ;
        LinkedHashMap map = new LinkedHashMap ();
		if(result == 1){
			returnString = "Y" ;
		}else{
			returnString = "N" ;
		}
	    map.put("returnString", returnString);
	   // map.put("count", count);
		return map;
	}
}
