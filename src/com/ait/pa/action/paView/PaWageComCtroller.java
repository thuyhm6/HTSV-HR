package com.ait.pa.action.paView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.CycleSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.insurance.InsuranceInputItemSer;
import com.ait.pa.service.paView.PaAvgViewSer;
import com.ait.pa.service.paView.PaBasicViewSer;
import com.ait.pa.service.paView.PaWageComViewSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
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
public class PaWageComCtroller {
Logger logger = Logger.getLogger(PaWageComCtroller.class);
	
	@Autowired
	private PaAvgViewSer PaAvgViewSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private PaWageComViewSer paWageComViewSer;
	@Autowired
	private CycleSer cycleSer;
	@Autowired
	private InsuranceInputItemSer insuranceInputItemSer;

	@Autowired
	private EmpInfoSer empInfoSer;

	/**
	 * 工资基础页面(view Pa Basic)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWageCom")
	public ModelAndView viewPersonalAttendance(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		//默认
		String menuNo = "218089";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
        request.setAttribute("from", (String)request.getAttribute("Yearfrom")+request.getAttribute("Monthfrom"));
 
		if(paramMap.get("seach_TYPE1")!=null && paramMap.get("seach_PARAM_NO")!=null && !"".equals(paramMap.get("seach_PARAM_NO").toString())){
			List getList = this.paWageComViewSer.getPaWageComList(request);
			modelMap.put("getList", getList);
			List getItemNameList = this.insuranceInputItemSer.getItemNameListPa2(request);
			modelMap.put("getItemNameList", getItemNameList);
			
		}
		modelMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );

				//modelMap.put("positionList", empInfoSer.getPositionList(request));
				modelMap.put("seach_ITEM_DISTINGUISH", paramMap.get("seach_ITEM_DISTINGUISH"));
				modelMap.put("seach_DEPTNO", paramMap.get("seach_DEPTNO"));
				modelMap.put("seach_TYPE1", paramMap.get("seach_TYPE1"));
				modelMap.put("seach_PARAM_NO", paramMap.get("seach_PARAM_NO"));
				modelMap.put("TYPE2", paramMap.get("TYPE2"));
				modelMap.put("money", paramMap.get("money"));
				modelMap.put("Yearfrom", paramMap.get("Yearfrom"));
				modelMap.put("Yearto", paramMap.get("Yearto"));
				modelMap.put("Monthfrom", paramMap.get("Monthfrom"));
				modelMap.put("Monthto", paramMap.get("Monthto"));
				modelMap.put("seach_KEY", paramMap.get("seach_KEY"));
				modelMap.put("seach_EMP_TYPE", paramMap.get("seach_EMP_TYPE"));

 		return new ModelAndView("/pa/paView/viewWageCom",modelMap);
	}
	
	@RequestMapping(value = "/viewWageComExcel")
	public ModelAndView viewPaAllowanceExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		//默认
		String menuNo = "218089";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
        request.setAttribute("from", (String)request.getAttribute("Yearfrom")+request.getAttribute("Monthfrom"));
 
		if(paramMap.get("seach_TYPE1")!=null){
			List getListExcel = this.paWageComViewSer.getPaWageComList(request);
			modelMap.put("getListExcel", getListExcel);
		}
		if(paramMap.get("seach_TYPE1").equals("shang")){
		return new ModelAndView("/pa/paView/viewWageComExcel",modelMap);
		}else {
		return new ModelAndView("/pa/paView/viewWageComExcel2",modelMap);
		}
	}
	
	
	

}