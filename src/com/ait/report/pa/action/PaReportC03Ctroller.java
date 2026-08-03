package com.ait.report.pa.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.report.pa.service.PaReportC03Ser;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Controller
@RequestMapping(value = "/report/pac03")
public class PaReportC03Ctroller {
	@Autowired
	private PaReportC03Ser paReportC03Ser;

	/**
	 * 跳转到导出给予现状汇总报表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	  @RequestMapping(value = "/currentRenditionCollect")
	public ModelAndView viewPaOfficialPayOffInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		//modelMap.put("paTranserList",this.paReportSer.getPaTranserList(request));
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.paReportSer.getPaTranserListCnt(request));		  
	    String ddate = "";
	      Date dt = new Date();
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	      ddate=sdf.format(dt);
	      String b[] = ddate.split("-");
	      
	      modelMap.put("YEAR", b[0].trim().toString());
	      modelMap.put("MONTH", b[1].trim().toString());
	    
	    
	    return new ModelAndView("/report/pac03/currentRenditionCollect",modelMap);
	}

	/**
	 * 跳转到导出给予现状汇总报表，导出Excel用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value ="/currentRenditionCollectExcel")
	public ModelAndView viewActualSalaryExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		 AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		 modelMap.put("YEAR", request.getParameter("seach_YEAR"));
	      modelMap.put("MONTH", request.getParameter("seach_MONTH"));
		modelMap.put("EmpCnt1",
				this.paReportC03Ser.empCount1(request));
		modelMap.put("EmpCnt2",
				this.paReportC03Ser.empCount2(request));
		modelMap.put("EmpCnt3",
				this.paReportC03Ser.empCount3(request));
		modelMap.put("EmpCnt4",
				this.paReportC03Ser.empCount4(request));
		modelMap.put("deptNo",request.getParameter("deptNo"));
		modelMap.put("viewPaCurrentRenditionList1",this.paReportC03Ser.getPaCurrentRenditionSumAvg(request));
//		modelMap.put("viewPaCurrentRenditionList2",this.paReportC03Ser.getPacurrentRenditionCollectList2(request));
//		modelMap.put("viewPaCurrentRenditionList3",this.paReportC03Ser.getPacurrentRenditionCollectList3(request));
		modelMap.put("viewPaCurrentRenditionList4",this.paReportC03Ser.getPacurrentRenditionCollectList4(request));
		modelMap.put("viewPaCurrentRenditionList5",this.paReportC03Ser.getPacurrentRenditionCollectList5(request));
		return new ModelAndView("/report/pac03/currentRenditionCollectExcel",
				modelMap);
	}

	/**
	   * 跳转到查看给予现况EXCEL导出页面（按月份查询）
	   * @param request
	   * @param response
	   * @param modelMap
	   * @return ModelAndView
	   * @throws Exception
	   */
	  @RequestMapping(value = "/viewPaCurrentRenditionInfo")
	  public ModelAndView viewPaCurrentRenditionInfo(HttpServletRequest request,
	        HttpServletResponse response,ModelMap modelMap) throws Exception{
	    
	    String ddate = "";
	      Date dt = new Date();
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	      ddate=sdf.format(dt);
	      String b[] = ddate.split("-");
	      
	      modelMap.put("YEAR", b[0].trim().toString());
	      modelMap.put("MONTH", b[1].trim().toString());
	    
	    
	    return new ModelAndView("/report/pac03/viewPaCurrentRenditionInfo",modelMap);
	  }
	  
	  /**
		 * 给予现况EXCEL导出（按工作区域及月份查询）
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return ModelAndView
		 * @throws Exception
		 */
		@RequestMapping(value = "/viewPaCurrentRenditionTranserExcel")
		public ModelAndView getPaCurrentRenditionTranserExcel(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			modelMap.put("YEAR", request.getParameter("seach_YEAR"));
			modelMap.put("MONTH", request.getParameter("seach_MONTH"));
			modelMap.put("deptNo",request.getParameter("deptNo"));
			modelMap.put("viewPaCurrentRenditionList",this.paReportC03Ser.getPaCurrentRenditionList(request));
			modelMap.put("getPaCurrentRenditionSumAvg",this.paReportC03Ser.getPaCurrentRenditionSumAvg(request));
			return new ModelAndView("/report/pac03/viewPaCurrentRenditionTranserExcel",modelMap);
		}
		
		
		/**
		   * 跳转到查看给予现况EXCEL导出页面（按月份查询）
		   * @param request
		   * @param response
		   * @param modelMap
		   * @return ModelAndView
		   * @throws Exception
		   */
		  @RequestMapping(value = "/viewPaHourWorkInfo")
		  public ModelAndView viewPaHourWorkInfo(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) throws Exception{
		    
			  String ddate = "";
		      Date dt = new Date();
		      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		      ddate=sdf.format(dt);
		      String b[] = ddate.split("-");
		      
		      modelMap.put("YEAR", b[0].trim().toString());
		      modelMap.put("MONTH", b[1].trim().toString());
		    
		      return new ModelAndView("/report/pac03/viewPaHourWorkInfo",modelMap);
		  }
		  
		  /**
			 * 给予现况EXCEL导出（按工作区域及月份查询）
			 * @param request
			 * @param response
			 * @param modelMap
			 * @return ModelAndView
			 * @throws Exception
			 */
			@SuppressWarnings("unchecked")
			@RequestMapping(value = "/viewPaHourWorkInfoTranserExcel")
			public ModelAndView getPaHourWorkInfoTranserExcel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception{
				modelMap.put("YEAR", request.getParameter("seach_YEAR"));
				modelMap.put("MONTH", request.getParameter("seach_MONTH"));
				modelMap.put("deptNo",request.getParameter("deptNo"));
				
				LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
				String pa_month = request.getParameter("seach_YEAR")+request.getParameter("seach_MONTH");
				paramMap.put("PA_MONTH", pa_month);
				
				List<?> paList = this.paReportC03Ser.getPaHourWorkInfo(paramMap);
				
				List<?> paDeptList = this.paReportC03Ser.getPaHourWorkInfoByDeptNo(paramMap);
				List<?> paSumList = this.paReportC03Ser.getSumPaHourWorkInfo(paramMap);
				
				modelMap.put("paList", paList);
				modelMap.put("paDeptList", paDeptList);
				modelMap.put("paSumList", paSumList);
				
				Date _date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
				String _rDate = format.format(_date);
				modelMap.put("_rDate", _rDate);
				
				return new ModelAndView("/report/pac03/viewPaHourWorkInfoTranserExcel",modelMap);
			}
}
