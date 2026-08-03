package com.ait.sys.action;

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
import com.ait.sys.service.SyPageStructureSer;

@Controller
@RequestMapping(value = "/sys/sysSettings")
public class SyPageStructureCtroller {
	
	Logger logger = Logger.getLogger(SyPageStructureCtroller.class);
	
	@Autowired
	private SyPageStructureSer syPageStructureSer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSyPageStructure",method = RequestMethod.GET)
	public ModelAndView viewSyPageStructure(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List getIsCanBeBuildPage = this.syPageStructureSer.getIsCanBeBuildPage(request) ;
		modelMap.put("getIsCanBeBuildPage", getIsCanBeBuildPage) ;
        return new ModelAndView("/sys/sysSettings/viewSyPageStructure",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPageStructureInfoList",method = RequestMethod.POST)
	@ResponseBody
	public List getPageStructureInfoList(HttpServletRequest request) {
		List pageStructureInfoList = this.syPageStructureSer.getPageStructureInfoList(request) ;
		return pageStructureInfoList ;	
	}
	
	@RequestMapping(value = "/addPageStructureView",method = RequestMethod.GET)
	public ModelAndView addPageStructureView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
        return new ModelAndView("/sys/sysSettings/addPageStructureView",modelMap);
	}
	
	@RequestMapping(value = "/addPageStructure",method = RequestMethod.POST)
	@ResponseBody
	public String addPageStructure(HttpServletRequest request) {
		return this.syPageStructureSer.addPageStructure(request) ;
	}
	
	@RequestMapping(value = "/deletePageStructure",method = RequestMethod.POST)
	@ResponseBody
	public String deletePageStructure(HttpServletRequest request) {
		return this.syPageStructureSer.deletePageStructure(request) ;
	}
	
	@RequestMapping(value = "/viewAddPageStructureDetail",method = RequestMethod.GET)
	public ModelAndView viewAddPageStructureDetail(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("RT_NO", request.getParameter("RT_NO"));
		modelMap.put("REPORT_TYPE", request.getParameter("REPORT_TYPE"));
	    return new ModelAndView("/sys/sysSettings/viewAddPageStructureDetail",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAddPageStructureDetail",method = RequestMethod.POST)
	@ResponseBody
	public Map getAddPageStructureDetail(HttpServletRequest request)throws Exception{
		List getAddPageStructureDetail = this.syPageStructureSer.getAddPageStructureDetail(request) ;
		int getAddPageStructureDetailCnt=this.syPageStructureSer.getAddPageStructureDetailCnt(request);
		Map model=new HashMap();
		model.put("Rows", getAddPageStructureDetail) ;
		model.put("Total", getAddPageStructureDetailCnt) ;
		return model;		
	}
	
	@RequestMapping(value = "/AddPageStructureDetailInfo",method = RequestMethod.POST)
	@ResponseBody
	public String AddPageStructureDetailInfo(HttpServletRequest request) {
		return this.syPageStructureSer.AddPageStructureDetailInfo(request) ;
	}
	
	@RequestMapping(value = "/viewUpdatePageStructureDetail",method = RequestMethod.GET)
	public ModelAndView viewUpdatePageStructureDetail(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("RT_NO", request.getParameter("RT_NO"));
		modelMap.put("REPORT_TYPE", request.getParameter("REPORT_TYPE"));
	    return new ModelAndView("/sys/sysSettings/viewUpdatePageStructureDetail",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getUpdatePageStructureDetail",method = RequestMethod.POST)
	@ResponseBody
	public Map getUpdatePageStructureDetail(HttpServletRequest request)throws Exception{
		List getUpdatePageStructureDetail = this.syPageStructureSer.getUpdatePageStructureDetail(request) ;
		int getUpdatePageStructureDetailCnt=this.syPageStructureSer.getUpdatePageStructureDetailCnt(request);
		Map model=new HashMap();
		model.put("Rows", getUpdatePageStructureDetail) ;
		model.put("Total", getUpdatePageStructureDetailCnt) ;
		return model;		
	}
	
	@RequestMapping(value = "/deletePageStructureDetail",method = RequestMethod.POST)
	@ResponseBody
	public String deletePageStructureDetail(HttpServletRequest request) {
		return this.syPageStructureSer.deletePageStructureDetail(request) ;
	}
	
	@RequestMapping(value = "/updatePageStructureDetailInfo",method = RequestMethod.POST)
	@ResponseBody
	public String updatePageStructureDetailInfo(HttpServletRequest request) {
		return this.syPageStructureSer.updatePageStructureDetailInfo(request) ;
	}
	 
}
