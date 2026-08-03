package com.ait.report.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sql.DATE;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.web.util.StringUtil;
/**
 * Copyright:   AIT
 * Company:     AIT
 * @fileName: ReportCommonCtroller.java
 * @Description: Controller Class ReportCommonCtroller.java
 * @Create 
 * @Create by: esprince (xingpeng@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/report/common")
public class ReportCommonCtroller {
	Logger logger = Logger.getLogger(ReportCommonCtroller.class);
	
	/**
	 * 弹出PDF pop
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/showPDFPop")
	public ModelAndView showPDFPop(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
//		String paramStr = StringUtil.checkNull(request.getParameter("params")) ;
//		
//		String[] params = paramStr.split("@") ;
//		
//		String[] tempStr = null ;
//		
//		if(params.length > 0){
//			for (int i = 0; i < params.length; i++) {
//				tempStr = params[i].split("=");
//				if(tempStr.length == 2 )
//					modelMap.put(tempStr[0], tempStr[1]);
//			}
//		}
//		
//		modelMap.put("SUBSD_CD", request.getParameter("SUBSD_CD"));
//		modelMap.put("JOB_TP", request.getParameter("JOB_TP"));
//		modelMap.put("ATT_MON", request.getParameter("ATT_MON"));
//		modelMap.put("reportName", request.getParameter("reportName"));
//		modelMap.put("SUBSD_NAME", request.getParameter("SUBSD_NAME"));
//		modelMap.put("ORG_ID", request.getParameter("ORG_ID"));
//		modelMap.put("suffix", request.getParameter("suffix"));
		modelMap.put("params", request.getParameter("params"));
		modelMap.put("actionUrl", request.getParameter("actionUrl"));	
		return new ModelAndView("/report/common/showPDFPop",modelMap);
	}
}
