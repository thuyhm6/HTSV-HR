package com.ait.ar.action.attendanceSettings;

import java.util.ArrayList;
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

import com.ait.ar.service.AttendanceKeeperSer;
import com.ait.ar.service.CycleSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.AuthorityUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AttendanceKeeperCtroller.java
 * @Description:
 * @Create date: 2012-1-14 下午01:43:31
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class AttendanceKeeperCtroller {
	Logger logger = Logger.getLogger(AttendanceKeeperCtroller.class);
	
	@Autowired
	private AttendanceKeeperSer attendanceKeeperSer ;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private CycleSer cycleSer;
	@Autowired
	private AuthorityUtil authorityUtil;
	
	/**
	 * 查看考勤员(view AttendanceKeeper List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAttendanceKeeper")
	public ModelAndView viewAttendanceKeeperList(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		List<LinkedHashMap<String, Object>> attendanceKeeperList = this.attendanceKeeperSer.getAttendanceKeeperList(request) ;
		int attendanceKeeperCnt = this.attendanceKeeperSer.getAttendanceKeeperCnt(request) ;
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("order", "desc") ;
		modelMap.put("attendanceKeeperList", attendanceKeeperList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendanceKeeperCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2355")) ;
	
		return new ModelAndView("/ar/attendanceSettings/viewAttendanceKeeper", modelMap);
	}

	/**
	 * 添加跳转(add AttendanceKeeper View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addAttendanceKeeperView")
	public ModelAndView addAttendanceKeeperView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
	    AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//	    modelMap.put("CPNYID", CPNYID);
		/*List empTypeCodeList = this.cycleSer.getEmpTypeCodeListSUPERVISOR(request);
		List jobTypeGroupList=this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("empTypeCodeList", empTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);*/
		modelMap.put("CPNYID",admin.getCpnyId());
//		List attendanceKeeperDeptList = this.attendanceKeeperSer.getAttendanceDeptList(request);
//		modelMap.put("attendanceKeeperDeptList", attendanceKeeperDeptList);
		return new ModelAndView("/ar/attendanceSettings/addAttendanceKeeperView", modelMap);
	}
	
	/**
	 * 部门树(get Dept Tree)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDeptTree")
	@ResponseBody
	public List getDeptTree(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List getDeptTree = this.attendanceKeeperSer.getDeptTree(request);
		return getDeptTree;
	}
	
	/**
	 * 保存方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAttendanceKeeperInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAttendanceKeeperInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = attendanceKeeperSer.addAttendanceKeeperInfo(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
			map.put("navTabId", "ar0202");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;		
	}
	/**
	 * 获得人员列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewKeeperList")
	public ModelAndView viewKeeperList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("personList", attendanceKeeperSer.getPersonListView(request));
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendanceKeeperSer.getPersonListCnt(request));
		
		return new ModelAndView("/ar/attendanceSettings/viewKeeperList", modelMap);
	}
	/**
	 * 获得人员列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewKeeperListOtApplyCheck")
	public ModelAndView viewKeeperListOtApplyCheck(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("personList", attendanceKeeperSer.viewKeeperListOtApplyCheck(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendanceKeeperSer.getPersonListOtApplyCheckCnt(request));
		
		return new ModelAndView("/ar/attendanceSettings/viewKeeperListOtApplyCheck", modelMap);
	}
	
	/**
	 * 获得人员列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewKeeperList2")
	public ModelAndView viewKeeperList2(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("personList", attendanceKeeperSer.getPersonListView(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendanceKeeperSer.getPersonListCnt(request));
		
		return new ModelAndView("/ar/attendanceSettings/viewKeeperList2", modelMap);
	}
	
	/**
	 * 修改跳转(update AttendanceKeeper View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAttendanceKeeperView",method = RequestMethod.GET)
	public ModelAndView updateAttendanceKeeperView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
	AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//	    String CPNYID = admin.getCpnyId();
//	    modelMap.put("CPNYID", CPNYID);
		//List empTypeCodeList = this.cycleSer.getEmpTypeCodeListSUPERVISOR(request) ;
		//List statisticList= this.cycleSer.getKeeperEmpTypeCodeList(request) ; 
		/*List statisticList = this.cycleSer.getKeeperJobGroupList(request) ; 
		List jobTypeGroupList=this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());*/
//		List attendanceKeeperDeptList = this.attendanceKeeperSer.getAttendanceKeeperDeptList(request);
//		modelMap.put("attendanceKeeperDeptList", attendanceKeeperDeptList);
		/*modelMap.put("jobTypeGroupList", jobTypeGroupList) ;
		//modelMap.put("empTypeCodeList", empTypeCodeList) ;
		modelMap.put("statisticList", statisticList) ;*/
		modelMap.put("attendanceKeeperInfo", attendanceKeeperSer.getAttendanceKeeper(request)) ;
		modelMap.put("AR_SUPERVISOR_ID", request.getParameter("PERSON_ID")) ;
		
		return new ModelAndView("/ar/attendanceSettings/updateAttendanceKeeperView", modelMap);
	}
	
	/**
	 * 修改保存(update AttendanceKeeper Info)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAttendanceKeeperInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAttendanceKeeperInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		int result = attendanceKeeperSer.updateAttendanceKeeperInfo(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "ar0202");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;		
	}
	
	/**
	 * 删除考勤员信息(delete AttendanceKeeper Info)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAttendanceKeeperInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAttendanceKeeperInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.attendanceKeeperSer.deleteAttendanceKeeperInfo(request) ;
		map=ObjectBindUtil.getRequestParamData(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "ar0202");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;			
	}
	/**
	 * 部门最终管理(view Department Manager List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewDepartManagerList")
	public ModelAndView viewDepartManagerList(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		List viewDepartManagerList =this.attendanceKeeperSer.getDepartmentManageList(request) ;
		
		modelMap.put("viewDepartManagerList", viewDepartManagerList) ;
		modelMap.put("viewDepartManagerListCnt", viewDepartManagerList == null ? 0 : viewDepartManagerList.size());
		if(request.getParameter("seach_LOCK_DATE") == null || "".equals(request.getParameter("seach_LOCK_DATE"))){
			modelMap.put("LOCK_DATE", DateUtil.getSysdateStr("dd/MM/yyyy"));
		}
		

		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "14013733")) ;
	
		return new ModelAndView("/ar/attendanceSettings/viewDepartManagerList", modelMap);
	}	
	
	/**
	 * 保存部门最终管理方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDepartManagerInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDepartManagerInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = attendanceKeeperSer.addDepartManagerInfo(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
			map.put("formId", "viewDepartManagerListForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;		
	}
	
	/**
	 * 保存部门最终管理期间统一方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDepartManagerUnifyInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDepartManagerUnifyInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = attendanceKeeperSer.addDepartManagerUnifyInfo(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
			map.put("formId", "viewDepartManagerListForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;		
	}

}
