package com.ait.ess.action;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ArDetailSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.ess.service.PersonShiftSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/ess/workgroup")
public class WorkGroupExpericeCtroller {

	Logger logger = Logger.getLogger(WorkGroupExpericeCtroller.class);
	
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private PersonShiftSer personShiftSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private InfoApplySer infoApplySer;
	/**
	 * 班组经历查询
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewWorkGroupExperList")
	public ModelAndView viewOvertimeInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("WorkGroupList",personShiftSer.viewArShiftGroupList(request));
		
		return new ModelAndView("/ess/workgroup/viewWorkGroupExperList", modelMap);
	}
	/**
	 * 个人班次查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonShiftList")
	public ModelAndView viewPersonShiftList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
		Calendar c = Calendar.getInstance();    
		if((request.getParameter("seach_sDate")==""||request.getParameter("seach_sDate")==null )&& (request.getParameter("seach_eDate")==""||request.getParameter("seach_eDate")==null)){
			//获取当前月第一天：
			c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());
			modelMap.put("sDate",first);
			//获取当前月最后一天：
			c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
			String last = format.format(c.getTime());
			modelMap.put("eDate",last);
		}
		List personShift = this.personShiftSer.viewPersonShiftList(request);
		modelMap.put("personShift", personShift);
		return new ModelAndView("/ess/workgroup/viewPersonShiftList", modelMap);
	}
}