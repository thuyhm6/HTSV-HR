package com.ait.ar.action.attendanceMintenance;

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

import com.ait.ar.service.ArAnnualLeaveSer;
import com.ait.ar.service.CycleSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.exception.ConfigurationException;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.IthrToPortalIfUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAnnualLeaveCtroller.java
 * @Description:
 * @Create date: 2012-2-14 下午12:52:04
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class ArAnnualLeaveCtroller {
Logger logger = Logger.getLogger(ArAnnualLeaveCtroller.class) ;
	
	@Autowired
	private ArAnnualLeaveSer arAnnualLeaveSer ;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private CycleSer cycleSer;
	
	/**
	 * 显示个人年假页面(view ArAnnual Leave)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArAnnualLeave")
	public ModelAndView viewArAnnualLeaveList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		List arAnnualLeaveList = this.arAnnualLeaveSer.getArAnnualLeaveList(request);
		int arAnnualLeaveCnt = this.arAnnualLeaveSer.getArAnnualLeaveCnt(request) ;
		
		modelMap.put("arAnnualLeaveList", arAnnualLeaveList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, arAnnualLeaveCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2389")) ;
		
	    return new ModelAndView("/ar/attendanceMintenance/viewArAnnualLeave", modelMap);
	}
	
	/**
	 * 删除个人年假信息(delete ArAnnual Leave)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteArAnnualLeave")
	@ResponseBody
	public Map deleteArAnnualLeave(HttpServletRequest request)throws Exception{
			
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.arAnnualLeaveSer.deleteArAnnualLeave(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "ar0112");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	
		
		return map;
	}

	/**
	 * 显示个人年假更新页面(view ArAnnual Leave)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateArAnnualLeaveView")
	public ModelAndView updateArAnnualLeaveView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("annualLeaveInfo", arAnnualLeaveSer.getArAnnualLeaveInfo(request)) ;
		
		return new ModelAndView("/ar/attendanceMintenance/updateArAnnualLeaveView",modelMap);
	}
	
	/**
	 * 修改个人年假信息(update ArAnnualLeave Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateArAnnualLeaveInfo")
	@ResponseBody
	public Map updateArAnnualLeaveInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.arAnnualLeaveSer.updateArAnnualLeaveInfo(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "ar0112");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		
		return map;
	}
	
	/**
	 * 进入个人年假添加页面(add ArAnnualLeave View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addArAnnualLeaveView")
	public ModelAndView addArAnnualLeaveView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		return new ModelAndView("/ar/attendanceMintenance/addArAnnualLeaveView",modelMap);
	}
	
	/**
	 * 添加个人年假(add ArAnnualLeave Info)
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addArAnnualLeaveInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addArAnnualLeaveInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int errorInt = this.arAnnualLeaveSer.checkAddArAnnualLeaveInfo(request) ;
		
		if(errorInt == 0){
			
			int result = arAnnualLeaveSer.addArAnnualLeaveInfo(request) ;
			
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("ar.alert.message.addempshift.success",request));//保存成功
				map.put("navTabId", "ar0112");
				map.put("callbackType", "closeCurrent");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败！
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.info_exits",request));//该信息已经存在,不能重复添加
		}
		
		return map;
	}
	
	/**
	 * 年假初始化(create ArAnnualLeave Info)
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/createArAnnualLeaveInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map createArAnnualLeaveInfo(HttpServletRequest request)throws Exception{
			
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = arAnnualLeaveSer.createArAnnualLeaveInfo(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ar.viewarannualeave.title.initsuccess",request));//初始化成功
			map.put("navTabId", "ar0112");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("ar.viewarannualeave.title.initfail",request));//初始化失败
		}	
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public int addPortalIp(Map paramMap) throws ConfigurationException{
	 	int logResult = -1 ;
	 	logResult = this.arAnnualLeaveSer.addPortalIp(paramMap);
	 	return logResult;
	}
	
	/**
	 * 每天凌晨4:00自动进行年假初始化(create ArAnnualLeave Info auto at 4:00 am)
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	public int createArAnnualLeaveAuto(Map paramMap)throws Exception{
		int logResult = -1 ;
	 	logResult = this.arAnnualLeaveSer.createArAnnualLeaveAuto(paramMap);
	 	return logResult;
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpForSupervisorList")
	public ModelAndView viewEmpForSupervisorList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("personList", arAnnualLeaveSer.getEmpForSupervisorList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, arAnnualLeaveSer.getEmpForSupervisorCnt(request));
		
		return new ModelAndView("/ar/attendanceMintenance/viewEmpCalendarList", modelMap);
	}
}
