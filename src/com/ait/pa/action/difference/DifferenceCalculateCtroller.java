package com.ait.pa.action.difference;

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
import com.ait.pa.service.difference.DifferenceCalculateSer;

@Controller
@RequestMapping(value = "/pa/difference")
public class DifferenceCalculateCtroller {
	Logger logger = Logger.getLogger(DifferenceCalculateCtroller.class);
	
	@Autowired
	private DifferenceCalculateSer differenceCalculateSer ;
	
	@RequestMapping(value = "/viewDifferenceCalculate",method = RequestMethod.GET)
	public ModelAndView viewDifferenceCalculate(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		return new ModelAndView("/pa/difference/viewDifferenceCalculate",modelMap);
	}
	
	@RequestMapping(value = "/differenceCalculate",method = RequestMethod.POST)
	@ResponseBody
	public String differenceCalculate(HttpServletRequest request)throws Exception{
		
		String returnString = this.differenceCalculateSer.differenceCalculate(request) ;
		
		return returnString ;		
	}
}
