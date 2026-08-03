package com.ait.ar.action.attendanceMintenance;

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

import com.ait.ar.service.ArAdjustRestSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAdjustRestCtroller.java
 * @Description:
 * @Create date: 2012-3-23 下午04:18:45
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class ArAdjustRestCtroller {
Logger logger = Logger.getLogger(ArAdjustRestCtroller.class) ;
	
	@Autowired
	private ArAdjustRestSer arAdjustRestSer ;
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	/**
	 * 显示个人调休页面(view Ar AdjustRest)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArAdjustRest")
	public ModelAndView viewArAdjustRestList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		List arAdjustRestList = this.arAdjustRestSer.getArAdjustRestList(request);
		int arAdjustRestCnt = this.arAdjustRestSer.getArAdjustRestCnt(request) ;
		
		modelMap.put("arAdjustRestList", arAdjustRestList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, arAdjustRestCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2381")) ;
		
	    return new ModelAndView("/ar/attendanceMintenance/viewArAdjustRest", modelMap);
	}
	
	/**
	 * 显示个人调休信息(view ArAdjustRest Info)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArAdjustRestInfo")
	public ModelAndView viewArAdjustRestInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		Map arAdjustRestInfo = (LinkedHashMap)this.arAdjustRestSer.getArAdjustRestInfo(request);
		
		modelMap.put("arAdjustRestInfo", arAdjustRestInfo) ;
		
	    return new ModelAndView("/ar/attendanceMintenance/viewArAdjustRestInfo", modelMap);
	}
}
