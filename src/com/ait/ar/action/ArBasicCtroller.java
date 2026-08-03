package com.ait.ar.action;

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

import com.ait.ar.service.ArBasicSer;
import com.ait.web.util.StringUtil;

@Controller
@RequestMapping(value = "/ar/basic")
public class ArBasicCtroller {
	Logger logger = Logger.getLogger(ArBasicCtroller.class);
	
	@Autowired
	private ArBasicSer arBasicSer ;
	
	@RequestMapping(value = "/viewArSearchEmployee",method = RequestMethod.GET)
	public ModelAndView viewAttendanceKeeper(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("reqest_SELECT_TYPE", StringUtil.checkNull(request.getParameter("SELECT_TYPE")));
		return new ModelAndView("/ar/basic/viewArSearchEmployee",modelMap);
	}
	
	@RequestMapping(value = "/getArSearchEmployeeList",method = RequestMethod.POST)
	@ResponseBody
	public Map getArSearchEmployeeList(HttpServletRequest request)throws Exception{
		
		List arSearchEmployeeList = this.arBasicSer.getArSearchEmployeeList(request) ;
		
		int arSearchEmployeeCnt = this.arBasicSer.getArSearchEmployeeCnt(request) ;
		
		Map model=new HashMap();
		model.put("arSearchEmployeeList", arSearchEmployeeList) ;
		model.put("arSearchEmployeeCnt", arSearchEmployeeCnt) ;
	
		return model ;	
	}
	@RequestMapping(value = "/viewArSearchEmployeeWithCheckBox",method = RequestMethod.GET)
	public ModelAndView viewArSearchEmployeeWithCheckBox(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("reqest_SELECT_TYPE", StringUtil.checkNull(request.getParameter("SELECT_TYPE")));
		return new ModelAndView("/ar/basic/viewArSearchEmployeeWithCheckBox",modelMap);
	}
}
