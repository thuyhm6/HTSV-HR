package com.ait.report.pa.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.pa.service.salary.PaProgressSer;
import com.ait.report.ar.service.ArReportSer;
import com.ait.report.hr.service.HrReportSer;
import com.ait.report.pa.service.PaReportC01Ser;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaReportC01Ctroller.java
 * @Description: Controller Class PaReportC01Ctroller.java
 * @Create date: Sep 14, 2012 5:29:38 PM
 * @Create by: lufeng (lufeng@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/report/pac01")
public class PaReportC01Ctroller {
	Logger logger = Logger.getLogger(PaReportCtroller.class);
	@Autowired
	private PaReportC01Ser paReportSer;
	@Autowired
    private ArReportSer arReportSer;
	@Autowired
    private HrReportSer hrReportSer;
	@Autowired
	private PaProgressSer paProgressSer ;
	
	 /**
	 * 跳转到乐天玛特异常明细--人员情况报表页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAbnormalEmpInfo")
	public ModelAndView viewAbnormalEmpInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String ddate = "";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    ddate=sdf.format(dt);
	    String b[] = ddate.split("-");
	    
	    modelMap.put("YEAR", b[0].trim().toString());
	    modelMap.put("MONTH", b[1].trim().toString());
	    modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
	  //取得门店信息
		List deptDistinguishList = this.paProgressSer.getDeptDistinguishList(request);
		modelMap.put("deptDistinguishList", deptDistinguishList);
		
		return new ModelAndView("/report/pac01/viewAbnormalEmpInfo",modelMap);
	}
	
	/**
	 * 乐天玛特异常明细--人员情况，导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAbnormalEmpExcel")
	public ModelAndView viewAbnormalEmpExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		//AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//LinkedHashMap linkMap = (LinkedHashMap)hrReportSer.getSysdate(request);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		LinkedHashMap map = new LinkedHashMap();
		map = (LinkedHashMap)this.paReportSer.getAbnormalEmpExcelList(request);
		
		//门店名称
		modelMap.put("DISTINGUISH_NAME",map.get("DISTINGUISH_NAME"));
        //上月末、本月末在职人数
		modelMap.put("lastEmpList",map.get("lastEmpList"));
		modelMap.put("lastEmpListCnt",map.get("lastEmpListCnt"));
		modelMap.put("sysEmpList",map.get("sysEmpList"));
		modelMap.put("sysEmpListCnt",map.get("sysEmpListCnt"));
		//本月入职、离职、本月1日离职生效人数
		modelMap.put("ruZhiEmpList",map.get("ruZhiEmpList"));
		modelMap.put("ruZhiEmpListCnt",map.get("ruZhiEmpListCnt"));
		modelMap.put("liZhiEmpList",map.get("liZhiEmpList"));
		modelMap.put("liZhiEmpListCnt",map.get("liZhiEmpListCnt"));
		modelMap.put("liZhiFirstEmpList",map.get("liZhiFirstEmpList"));
		modelMap.put("liZhiFirstEmpListCnt",map.get("liZhiFirstEmpListCnt"));
		//本月调入、调出人数
		modelMap.put("diaoRuEmpList",map.get("diaoRuEmpList"));
		modelMap.put("diaoRuEmpListCnt",map.get("diaoRuEmpListCnt"));
		modelMap.put("diaoChuEmpList",map.get("diaoChuEmpList"));
		modelMap.put("diaoChuEmpListCnt",map.get("diaoChuEmpListCnt"));
		//本月计薪人数
		modelMap.put("jiXinList",map.get("jiXinList"));
		modelMap.put("jiXinListCnt",map.get("jiXinListCnt"));
		//店内合同工、劳务工、小时工及店内总人数
		modelMap.put("normalEmpList",map.get("normalEmpList"));
		modelMap.put("normalEmpListCnt",map.get("normalEmpListCnt"));
		modelMap.put("laborEmpList",map.get("laborEmpList"));
		modelMap.put("laborEmpListCnt",map.get("laborEmpListCnt"));
		modelMap.put("hourEmpList",map.get("hourEmpList"));
		modelMap.put("hourEmpListCnt",map.get("hourEmpListCnt"));
		modelMap.put("totalEmpList",map.get("totalEmpList"));
		modelMap.put("totalEmpListCnt",map.get("totalEmpListCnt"));
		//店内某月派入、派出人数
		modelMap.put("disInEmpList",map.get("disInEmpList"));
		modelMap.put("disInEmpListCnt",map.get("disInEmpListCnt"));
		modelMap.put("disOutEmpList",map.get("disOutEmpList"));
		modelMap.put("disOutEmpListCnt",map.get("disOutEmpListCnt"));
		//店内某月实发工资为0的人
		modelMap.put("actualSalaryZero",map.get("actualSalaryZero"));
		modelMap.put("actualSalaryZeroCnt",map.get("actualSalaryZeroCnt"));
        
		return new ModelAndView("/report/pac01/viewAbnormalEmpExcel",modelMap);
	}
	
	/**
	 * 跳转到乐天玛特异常明细--工资情况报表页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAbnormalPaInfo")
	public ModelAndView viewAbnormalPaInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String ddate = "";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    ddate=sdf.format(dt);
	    String b[] = ddate.split("-");
	    
	    modelMap.put("YEAR", b[0].trim().toString());
	    modelMap.put("MONTH", b[1].trim().toString());
	    modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
	    
		return new ModelAndView("/report/pac01/viewAbnormalPaInfo",modelMap);
	}
	
	/**
	 * 乐天玛特异常明细--工资情况，导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAbnormalPaExcel")
	public ModelAndView viewAbnormalPaExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		//AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//LinkedHashMap linkMap = (LinkedHashMap)hrReportSer.getSysdate(request);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		modelMap.put("actualSalaryList",this.paReportSer.getPaActualSalaryExcelList(request));	
		
		return new ModelAndView("/report/pac01/viewAbnormalPaExcel",modelMap);
	}
	
	/**
	 * 跳转到乐天玛特异常明细--保险情况报表页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAbnormalInsInfo")
	public ModelAndView viewAbnormalInsInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String ddate = "";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    ddate=sdf.format(dt);
	    String b[] = ddate.split("-");
	    
	    modelMap.put("YEAR", b[0].trim().toString());
	    modelMap.put("MONTH", b[1].trim().toString());
	    modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
	    
		return new ModelAndView("/report/pac01/viewAbnormalInsInfo",modelMap);
	}
	
	/**
	 * 乐天玛特异常明细--保险情况，导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAbnormalInsExcel")
	public ModelAndView viewAbnormalInsExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//LinkedHashMap linkMap = (LinkedHashMap)hrReportSer.getSysdate(request);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		modelMap.put("abnormalInsList",this.paReportSer.getAbnormalInsExcelList(request));	
		
		return new ModelAndView("/report/pac01/viewAbnormalInsExcel",modelMap);
	}
	 
	 
	/**
	 * 跳转到乐天玛特实发薪资汇总表（总公司）报表页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaActualSalaryInfo")
	public ModelAndView viewActualSalaryInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String ddate = "";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    ddate=sdf.format(dt);
	    String b[] = ddate.split("-");
	    
	    modelMap.put("YEAR", b[0].trim().toString());
	    modelMap.put("MONTH", b[1].trim().toString());
	    modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
	    
		return new ModelAndView("/report/pac01/viewPaActualSalaryInfo",modelMap);
	}
	
	/**
	 * 乐天玛特实发薪资汇总表（总公司）信息，导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaActualSalaryExcel")
	public ModelAndView viewActualSalaryExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		//AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//LinkedHashMap linkMap = (LinkedHashMap)hrReportSer.getSysdate(request);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		modelMap.put("actualSalaryList",this.paReportSer.getPaActualSalaryExcelList(request));	
		
		return new ModelAndView("/report/pac01/viewPaActualSalaryExcel",modelMap);
	}
	
	/**
	 * 跳转到乐天玛特薪资成本汇总表（总公司）报表页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaPayrollCostsInfo")
	public ModelAndView viewPaPayrollCostsInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String ddate = "";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    ddate=sdf.format(dt);
	    String b[] = ddate.split("-");
	    
	    modelMap.put("YEAR", b[0].trim().toString());
	    modelMap.put("MONTH", b[1].trim().toString());
	    modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
	    
		return new ModelAndView("/report/pac01/viewPaPayrollCostsInfo",modelMap);
	}
	
	/**
	 * 乐天玛特薪资成本汇总表（总公司）信息，导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaPayrollCostsExcel")
	public ModelAndView viewPaPayrollCostsExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		//AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//LinkedHashMap linkMap = (LinkedHashMap)hrReportSer.getSysdate(request);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		
		modelMap.put("payrollCostsList",this.paReportSer.getPaPayrollCostsExcelList(request));
		
		return new ModelAndView("/report/pac01/viewPaPayrollCostsExcel",modelMap);
	}
	
	/**
	 * 跳转到乐天玛特--外派人员薪资信息报表页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewDispatchEmpPaInfo")
	public ModelAndView viewDispatchEmpPaInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String ddate = "";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    ddate=sdf.format(dt);
	    String b[] = ddate.split("-");
	    
	    modelMap.put("YEAR", b[0].trim().toString());
	    modelMap.put("MONTH", b[1].trim().toString());
	    modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
	    
		return new ModelAndView("/report/pac01/viewDispatchEmpPaInfo",modelMap);
	}
	
	/**
	 * 乐天玛特--外派人员薪资信息报表信息，导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewDispatchEmpPaExcel")
	public ModelAndView viewDispatchEmpPaExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		//AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//LinkedHashMap linkMap = (LinkedHashMap)hrReportSer.getSysdate(request);
		modelMap.put("YEAR", request.getParameter("seach_YEAR"));
		modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		
		modelMap.put("dispatchEmpPaList",this.paReportSer.getDispatchEmpPaExcelList(request));
		
		return new ModelAndView("/report/pac01/viewDispatchEmpPaExcel",modelMap);
	}
	
	/**
	 * 跳转到乐天玛特--外派人员保险信息报表页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewDispatchEmpInsInfo")
	public ModelAndView viewDispatchEmpInsInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String ddate = "";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    ddate=sdf.format(dt);
	    String b[] = ddate.split("-");
	    
	    modelMap.put("YEAR_INS", b[0].trim().toString());
	    modelMap.put("MONTH_INS", b[1].trim().toString());
	    modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
	    
		return new ModelAndView("/report/pac01/viewDispatchEmpInsInfo",modelMap);
	}
	
	/**
	 * 乐天玛特--外派人员保险信息报表信息，导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewDispatchEmpInsExcel")
	public ModelAndView viewDispatchEmpInsExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		//AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//LinkedHashMap linkMap = (LinkedHashMap)hrReportSer.getSysdate(request);
		modelMap.put("YEAR_INS", request.getParameter("seach_YEAR_INS"));
		modelMap.put("MONTH_INS", request.getParameter("seach_MONTH_INS"));
		
		modelMap.put("dispatchEmpInsList",this.paReportSer.getDispatchEmpInsExcelList(request));
		
		return new ModelAndView("/report/pac01/viewDispatchEmpInsExcel",modelMap);
	}
}
