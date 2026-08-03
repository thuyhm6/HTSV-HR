package com.ait.laborUnion.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;


import com.ait.hrm.action.ContractInfoCtroller;
import com.ait.hrm.service.EmpInfoSer;

import com.ait.laborUnion.service.LaborUnionSer;

import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.mchange.v1.util.SimpleMapEntry;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: CycleSer.java
 * @Create date: Jan 6, 2012 3:43:13 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/laborUnion/laborUnionInfo")
public class LaborUnionInfoCtroller {	
	Logger logger = Logger.getLogger(LaborUnionInfoCtroller.class);
	
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private EmpInfoSer empInfoSer;
	
	@Autowired
	private LaborUnionSer laborUnionSer;
	
	@Autowired
	private ExcelUtilSer excelUtilSer;
/*	*//**
	 * 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: 未签合同查询 ：HQ 法人管理者 查看所有法人的 未签合同信息
	* @author yorio   youjia@ait.net.cn 
	* @date Aug 23, 2013 6:22:27 PM 
	* @version V1.0
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/test")
	public ModelAndView test(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String name = StringUtil.checkNull(paramMap.get("NAME"));
		modelMap.put("defaultName",name);
		
		List studentInfoList = this.laborUnionSer.getStudentList(request);
		int studentCnt = this.laborUnionSer.getStudentCnt(request) ;
		
		modelMap.put("sortNameNoList",this.empInfoSer.getOrderParmList(request,"18709"));
		modelMap.put("itemList", studentInfoList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, studentCnt) ;
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2556")) ;
		
		modelMap.put("numPerPage", request.getParameter("numPerPage")!=null?request.getParameter("numPerPage"):10);
		modelMap.put("pageNum", request.getParameter("pageNum")!=null?request.getParameter("pageNum"):1);
		
		return new ModelAndView("/hrm/contractInfo/test",modelMap);
	}*/
	
	
	/**
	 * 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: 工会查询
	* @author yorio   youjia@ait.net.cn 
	* @date Aug 23, 2013 6:22:27 PM 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/laborUnionList")
	public ModelAndView getLaborUnionSum(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		
		String pamonth =  StringUtil.checkNull(request.getParameter("seach_paMonth"));
		if (pamonth != null && !"".equals(pamonth)) {
		}else{
			java.util.Calendar now = java.util.Calendar.getInstance();
			String year = StringUtil.checkNull(now.get(Calendar.YEAR));
			String month = StringUtil.checkNull(now.get(Calendar.MONTH)+1);
			String day = StringUtil.checkNull(now.get(Calendar.DAY_OF_MONTH));
			month = ("0"+month).substring(("0"+month).length()-2,("0"+month).length());
			day = ("0"+day).substring(("0"+day).length()-2,("0"+day).length());
			pamonth = year + "-" + month + "-" + day;
		}
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PaMonth", pamonth);
		
		paramMap.put("SEXCODE", "1326");
		Object laborUnionInfoMale = this.laborUnionSer.getLaborUnionSum(paramMap);
		Object laborUnionInfoMale2M = this.laborUnionSer.getLaborUnionSum2(paramMap);
		
		paramMap.put("SEXCODE", "1325");
		Object laborUnionInfoFemale = this.laborUnionSer.getLaborUnionSum(paramMap);
		Object laborUnionInfoMale2F = this.laborUnionSer.getLaborUnionSum2(paramMap);
		
		paramMap.put("SEXCODE", "");
		Object laborUnionInfoSum = this.laborUnionSer.getLaborUnionSum(paramMap);
		Object laborUnionInfoMale2S = this.laborUnionSer.getLaborUnionSum2(paramMap);
		modelMap.put("itemListM", laborUnionInfoMale);
		modelMap.put("itemListF", laborUnionInfoFemale);
		modelMap.put("itemListS", laborUnionInfoSum);
		
		modelMap.put("itemListM2", laborUnionInfoMale2M);
		modelMap.put("itemListF2", laborUnionInfoMale2F);
		modelMap.put("itemListS2", laborUnionInfoMale2S);
		
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, 1) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2556")) ;		
		modelMap.put("numPerPage",1);
		modelMap.put("CPNY_ID",admin.getCpnyId());
		modelMap.put("paMonth",pamonth);
		
		return new ModelAndView("/laborUnion/laborUnionInfo/laborUnionList",modelMap);
	}
	
	
	
	
	/**
	 * 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: 工会查询
	* @author yorio   youjia@ait.net.cn 
	* @date Aug 23, 2013 6:22:27 PM 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/laborUnionListExcel")
	public ModelAndView getLaborUnionSumExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		
		String pamonth =  StringUtil.checkNull(request.getParameter("seach_paMonth"));
		if (pamonth != null && !"".equals(pamonth)) {
		}else{
			java.util.Calendar now = java.util.Calendar.getInstance();
			String year = StringUtil.checkNull(now.get(Calendar.YEAR));
			String month = StringUtil.checkNull(now.get(Calendar.MONTH)+1);
			String day = StringUtil.checkNull(now.get(Calendar.DAY_OF_MONTH));
			month = ("0"+month).substring(("0"+month).length()-2,("0"+month).length());
			day = ("0"+day).substring(("0"+day).length()-2,("0"+day).length());
			pamonth = year + "-" + month + "-" + day;
		}
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PaMonth", pamonth);
		
		
		Object laborUnionInfoExcel = this.laborUnionSer.getLaborUnionExcel(paramMap);
		modelMap.put("itemList", laborUnionInfoExcel);
		return new ModelAndView("/laborUnion/laborUnionInfo/laborUnionListExcel",modelMap);
	}
	
	

	
	
	 
	
}
