package com.ait.pa.action.paView;

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

import com.ait.pa.service.paView.PaAvgViewSer;
import com.ait.pa.service.paView.PaBasicViewSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.ViewOptionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaBasicViewCtroller.java
 * @Description:
 * @Create date: 2012-5-23 下午02:43:51
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/paView")
public class PaAvgViewCtroller {
Logger logger = Logger.getLogger(PaAvgViewCtroller.class);
	
	@Autowired
	private PaAvgViewSer PaAvgViewSer;
	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 工资基础页面(view Pa Basic)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaAvg")
	public ModelAndView viewPersonalAttendance(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		//默认
		String menuNo = "217858";
		
		String dataTable = this.PaAvgViewSer.makeDataTable(request, menuNo) ;
		
		modelMap.put("dataTable", dataTable) ;
		
		modelMap.put("KEY", request.getParameter("KEY")) ;
		
		modelMap.put("paYearfrom", request.getParameter("paYearfrom")) ;
		
		modelMap.put("paMonthfrom", request.getParameter("paMonthfrom")) ;

		modelMap.put("paYearto", request.getParameter("paYearto")) ;
		
		modelMap.put("paMonthto", request.getParameter("paMonthto")) ;
		
		return new ModelAndView("/pa/paView/viewPaAvg",modelMap);
	}
}