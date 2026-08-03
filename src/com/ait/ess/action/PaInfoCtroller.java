package com.ait.ess.action;

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

import com.ait.ess.service.PaInfoSer;
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
 * @fileName: PaInfoCtroller.java
 * @Description:
 * @Create date: 2012-5-23 下午06:50:02
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/infoView")
public class PaInfoCtroller {
Logger logger = Logger.getLogger(PaInfoCtroller.class);
	
	@Autowired
	private PaInfoSer paInfoSer;
	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 个人考勤页面(view Personal Attendance)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaInfo")
	public ModelAndView viewPersonalAttendance(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		//默认
		String menuNo = "2459";
		
		String dataTable = this.paInfoSer.makeDataTable(request, menuNo) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		modelMap.put("view_EMPID", admin.getEmpID());
		
		modelMap.put("view_LOCALNAME", admin.getLocalName());
		
		modelMap.put("dataTable", dataTable) ;
		
		modelMap.put("essYear", request.getParameter("essYear")) ;
		
		modelMap.put("essMonth", request.getParameter("essMonth")) ;
		
		return new ModelAndView("/ess/infoView/viewPaInfo",modelMap);
	}
}