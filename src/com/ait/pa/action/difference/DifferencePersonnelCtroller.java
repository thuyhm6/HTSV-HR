package com.ait.pa.action.difference;

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

import com.ait.pa.service.difference.DifferencePersonnelSer;

@Controller
@RequestMapping(value = "/pa/difference")
public class DifferencePersonnelCtroller {
	Logger logger = Logger.getLogger(DifferencePersonnelCtroller.class);
	
	@Autowired
	private DifferencePersonnelSer differencePersonnelSer ;
	
	@RequestMapping(value = "/viewDifferencePersonnel",method = RequestMethod.GET)
	public ModelAndView viewAttendanceKeeper(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		return new ModelAndView("/pa/difference/viewDifferencePersonnel",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDifferencePersonnelList",method = RequestMethod.POST)
	@ResponseBody
	public Map getDifferencePersonnelList(HttpServletRequest request)throws Exception{
		
		List differencePersonnelList = this.differencePersonnelSer.getDifferencePersonnelList(request) ;
		
		int differencePersonnelCnt = this.differencePersonnelSer.getDifferencePersonnelCnt(request) ;
		
		Map model=new HashMap();
		model.put("Rows", differencePersonnelList) ;
		model.put("Total", differencePersonnelCnt) ;
		
		return model ;	
	}
	
	@RequestMapping(value = "/addDifferencePersonnelView",method = RequestMethod.GET)
	public ModelAndView addDifferencePersonnelInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		return new ModelAndView("/pa/difference/addDifferencePersonnelView",modelMap);
	}
	
	@RequestMapping(value = "/addDifferencePersonnelInfo",method = RequestMethod.POST)
	@ResponseBody
	public String addDifferencePersonnelInfo(HttpServletRequest request)throws Exception{
		
		this.differencePersonnelSer.addDifferencePersonnelInfo(request) ;
		
		return "Y";		
	}
	
	@RequestMapping(value = "/deleteDifferencePersonnelInfo",method = RequestMethod.POST)
	@ResponseBody
	public String deleteDifferencePersonnelInfo(HttpServletRequest request)throws Exception{
		
		this.differencePersonnelSer.deleteDifferencePersonnelInfo(request) ;
		
		return "Y";		
	}
}
