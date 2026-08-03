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

import com.ait.pa.service.difference.DifferenceResultSer;
import com.ait.pa.service.salary.PaResultSer;

@Controller
@RequestMapping(value = "/pa/difference")
public class DifferenceResultCtroller {
	Logger logger = Logger.getLogger(DifferenceResultCtroller.class);
	
	@Autowired
	private DifferenceResultSer differenceResultSer ;
	
	@Autowired
	private PaResultSer paResultSer ;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewDifferenceResult",method = RequestMethod.GET)
	public ModelAndView viewDifferenceCalculate(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.addAllAttributes(this.paResultSer.getPaResultAllItem(request)) ;
		
		return new ModelAndView("/pa/difference/viewDifferenceResult",modelMap);
	}
	
	@RequestMapping(value = "/differenceBalance",method = RequestMethod.POST)
	@ResponseBody
	public String differenceBalance(HttpServletRequest request)throws Exception{
		
		String returnString = this.differenceResultSer.differenceBalance(request) ;
		
		return returnString ;		
	}
}
