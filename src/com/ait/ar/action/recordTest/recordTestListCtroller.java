package com.ait.ar.action.recordTest;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.action.attendanceSettings.CycleCtroller;
import com.ait.ar.service.RecordTestListSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.UiUtil;
@Controller
@RequestMapping(value = "/ar/recordTestList")
public class recordTestListCtroller {
	//查询查看打卡记录
	Logger logger = Logger.getLogger(CycleCtroller.class);
	@Autowired
	private RecordTestListSer recordTestListSer;
	@Autowired
	private ToolMenuSer toolMenuSer;


	@RequestMapping(value = "/viewRecordTestList")
	public ModelAndView viewRecordTestList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List viewRecordTestList = this.recordTestListSer.getRecordTestList(request) ;
		int viewRecordTestCnt = this.recordTestListSer.getRecordTestCnt(request) ;
		
		modelMap.put("viewRecordTestList", viewRecordTestList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, viewRecordTestCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2348")) ;
		
		return new ModelAndView("/ar/recordTestList/viewRecordTestList",modelMap);
	}
	
}
