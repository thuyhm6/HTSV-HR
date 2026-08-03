package com.ait.ar.action.attendanceSettings;

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
import com.ait.ar.service.ArAnnualStandardSer;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAnnualStandardCtroller.java
 * @Description:
 * @Create date: 2012-2-14 下午12:52:04
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class ArAnnualStandardCtroller {
Logger logger = Logger.getLogger(ArAnnualStandardCtroller.class) ;
	
	@Autowired
	private ArAnnualStandardSer arAnnualStandardSer ;
	@Autowired
	private CompanySer companySer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	/**
	 * 显示年假标准页面(view ArAnnual Standard)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArAnnualStandard")
	public ModelAndView viewArAnnualStandardList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		List arAnnualStandardList = this.arAnnualStandardSer.getArAnnualStandardList(request);
//		List arAnnualMonthList = this.arAnnualStandardSer.getArAnnualMonthList(request);
		int arAnnualStandardCnt = this.arAnnualStandardSer.getArAnnualStandardCnt(request) ;
		
//		modelMap.put("arAnnualMonthList", arAnnualMonthList) ;
		modelMap.put("arAnnualStandardList", arAnnualStandardList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, arAnnualStandardCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2356")) ;
		
//		List cpnyList = this.companySer.getCompanyItemAllList(request);
//		modelMap.put("cpnyList", cpnyList) ;
		
	    return new ModelAndView("/ar/attendanceSettings/viewArAnnualStandard", modelMap);
	}
	
	/**
	 * 删除年假标准信息(delete ArAnnual Standard)
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteArAnnualStandard")
	@ResponseBody
	public Map deleteArAnnualStandard(HttpServletRequest request)throws Exception{
			
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.arAnnualStandardSer.deleteArAnnualStandard(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "ar0307");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	
		
		return map;
	}

	/**
	 * 显示年假标准更新页面(view ArAnnual Standard)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateArAnnualStandardView")
	public ModelAndView updateArAnnualStandardView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("annualStandardInfo", arAnnualStandardSer.getArAnnualStandardInfo(request)) ;
		
		return new ModelAndView("/ar/attendanceSettings/updateArAnnualStandardView",modelMap);
	}
	
	/**
	 * 修改年假标准信息(update ArAnnualStandard Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateArAnnualStandardInfo")
	@ResponseBody
	public Map updateArAnnualStandardInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int errorInt = this.arAnnualStandardSer.checkAddArAnnualStandardInfo(request) ;
		
		if(errorInt <= 1){
		
			int result = this.arAnnualStandardSer.updateArAnnualStandardInfo(request) ;
			
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "ar0307");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("ar.viewArAnnualStandard.title.remonth",request));//您所添加的开始月数已存在,不能重复添加!
		}
		return map;
	}
	
	/**
	 * 进入年假标准添加页面(add ArAnnualStandard View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addArAnnualStandardView")
	public ModelAndView addArAnnualStandardView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		return new ModelAndView("/ar/attendanceSettings/addArAnnualStandardView",modelMap);
	}
	
	/**
	 * 添加年假标准(add ArAnnualStandard Info)
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addArAnnualStandardInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addArAnnualStandardInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int errorInt = this.arAnnualStandardSer.checkAddArAnnualStandardInfo(request) ;
		
		if(errorInt == 0){
			
			int result = arAnnualStandardSer.addArAnnualStandardInfo(request) ;
			
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
				map.put("navTabId", "ar0307");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("ar.viewArAnnualStandard.title.upmonth",request));//您所修改的开始月数在已有信息中已存在,要求开始月数不能重复!
		}
		
		return map;
	}
	
}
