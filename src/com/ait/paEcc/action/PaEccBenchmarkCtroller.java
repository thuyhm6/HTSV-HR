package com.ait.paEcc.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.pa.action.bonus.BonusCalculateCtroller;
import com.ait.paEcc.service.PaEccService;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
/**
 * Copyright: AIT Company: AIT
 * 
 * @fileName: PaEccBenchmarkCtroller.java
 * @Description: 经济补偿金===预估补偿金
 * @Create date: 2014-1-17 下午02:55:16
 * @Create by: 
 * @version 5.5
 */
@Controller
@RequestMapping(value="/paEcc/benchmark")
public class PaEccBenchmarkCtroller {
	
	@Autowired
	private PaEccService eccService;
	
	Logger logger = Logger.getLogger(BonusCalculateCtroller.class);
	/**
	 * 进入预估补偿金页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewEccBenchEmp")
	public ModelAndView viewEccBenchEmp(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap){
		String empId = request.getParameter("dwz.person.empId");
		String STIME = request.getParameter("STIME");
		String xieyijin = request.getParameter("xieyijin");
		if(empId!=null&&STIME!=null&&xieyijin!=null
					&&empId!=""&&STIME!=""&&xieyijin!=""){
			List empEccInfos = eccService.getEmpPaEcc(request);
			modelMap.put("eccList", empEccInfos);
			modelMap.put("empId", empId);
			modelMap.put("LOCAL_NAME", request.getParameter("seach_LOCAL_NAME"));
			modelMap.put("xieyijin", xieyijin);
			modelMap.put("statusCode", "200");
			modelMap.put("STIME", STIME);
		}
		return new ModelAndView("/paEcc/benchmark/viewEccBenchEmp",modelMap);
		
	}
	/**
	 * 查询员工信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/searchEccBenchEmp")
	public ModelAndView searchEccBenchEmp(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap){
		
		modelMap.put("pageNum", request.getParameter("pageNum")!=null?request.
					getParameter("pageNum"):"1");
		modelMap.put("numPerPage", request.getParameter("numPerPage")!=null?request.
				getParameter("numPerPage"):"10");
		List empinfos = eccService.getEccEmpInfo(request);
		modelMap.put("empinfos", empinfos);
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("EMPID", request.getParameter("seach_EMPID"));
		modelMap.put("LOCAL_NAME", request.getParameter("seach_LOCAL_NAME"));
//		modelMap.put("STIME", request.getParameter("seach_STIME"));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,eccService.getEccEstEmpCn(request));
//		modelMap.addAttribute("numPerPage",)
		return new ModelAndView("/paEcc/benchmark/searchEccBenchEmp",modelMap);
	}
	
	/**
	 * 基准生成---得到预估赔偿金信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getEmpEccInfo")
	@ResponseBody
	public Map getEmpEccInfo(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap){
		
		Map<String, Object> map = new HashMap<String, Object>();
		String empId = request.getParameter("employeeId");
		String STIME = request.getParameter("STIME");
		String xieyijin = request.getParameter("xieyijin");
		if(empId!=null&&STIME!=null&&xieyijin!=null){
			List empEccInfos = eccService.getEmpPaEcc(request);
			map.put("eccList", empEccInfos);
			map.put("empId", empId);
			map.put("LOCAL_NAME", request.getParameter("seach_LOCAL_NAME"));
			map.put("xieyijin", xieyijin);
			map.put("statusCode", "200");
		}
		return map;
	}
	///paEcc/benchmark/viewReportApplication
	
	/**
	 * 进入预估补偿金页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewReportApplication")
	public ModelAndView viewReportApplication(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap){
		
		return new ModelAndView("/paEcc/benchmark/viewReportApplication",modelMap);
		
	}
}
