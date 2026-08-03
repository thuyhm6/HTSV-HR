package com.ait.edu.action;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.edu.service.TrainReportSer;
import com.ait.report.ar.service.ArReportSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;

@Controller
@RequestMapping(value = "/edu/trainreport")
public class TrainReportCtroller {
	Logger logger = Logger.getLogger(TrainReportCtroller.class);
	@Autowired
	private  TrainReportSer trainReportSer;
	@Autowired
    private ArReportSer arReportSer;
	
	// 报表列表首页面
	@RequestMapping(value = "/viewTrainReport")
	public ModelAndView viewTrainReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		//List codeInfoTreeList =  this.arReportSer.getCodeListByParentCode(request,"22115") ;   //人事报表类型code_no
		List reportList =  this.arReportSer.getreportList(request) ;   //报表
		//modelMap.put("codeInfoTreeList", codeInfoTreeList);
		modelMap.put("reportList", reportList);
		return new ModelAndView("/edu/trainreport/viewTrainReport",
				modelMap);
	}
	
	@RequestMapping(value = "/courseTrainReport")
	public ModelAndView courseTrainReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/edu/trainreport/courseTrainReport",
				modelMap);
	}
	
	@RequestMapping(value = "/courseTrainReportExcel")
	public ModelAndView courseTrainReportExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("TRAIN_DIFF_CODE", request.getParameter("TRAIN_DIFF_CODE"));
		modelMap.put("TRAIN_TYPE_CODE", request.getParameter("TRAIN_TYPE_CODE"));
		modelMap.put("COURSE_NAME_CODE", request.getParameter("COURSE_NAME_CODE"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		modelMap.put("reportList", this.trainReportSer.courseTrainList(request));
		

		return new ModelAndView("/edu/trainreport/courseTrainReportExcel",
				modelMap);
	}
	
	@RequestMapping(value = "/postGradeTrainReport")
	public ModelAndView postGradeTrainReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("reportList", this.trainReportSer.postGradeTrainList(request));
		modelMap.put("POST_GRADE_NAME", request.getParameter("POST_GRADE_NAME"));
		return new ModelAndView("/edu/trainreport/postGradeTrainReport",
				modelMap);
	}
	
	@RequestMapping(value = "/postGradeTrainReportExcel")
	public ModelAndView postGradeTrainReportExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		modelMap.put("reportList", this.trainReportSer.postGradeTrainList(request));
		modelMap.put("POST_GRADE_NAME", request.getParameter("POST_GRADE_NAME"));

		return new ModelAndView("/edu/trainreport/postGradeTrainReportExcel",
				modelMap);
	}
	@RequestMapping(value = "/deptTrainReportExcel")
	public ModelAndView deptTrainReportExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		modelMap.put("reportList", this.trainReportSer.deptTrainList(request));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		
		return new ModelAndView("/edu/trainreport/deptTrainReportExcel",
				modelMap);
	}
	
	@RequestMapping(value = "/deptTrainReport")
	public ModelAndView deptTrainReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return new ModelAndView("/edu/trainreport/deptTrainReport",
				modelMap);
	}
	
	@RequestMapping(value = "/yearTrainReport")
	public ModelAndView yearTrainReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/edu/trainreport/yearTrainReport",
				modelMap);
	}
	
	@RequestMapping(value = "/yearTrainReportExcel")
	public ModelAndView yearTrainReportExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		modelMap.put("reportList", this.trainReportSer.yearTrainList(request));
		modelMap.put("COURSE_DATE", request.getParameter("COURSE_DATE"));

		return new ModelAndView("/edu/trainreport/yearTrainReportExcel",
				modelMap);
	}
	
	@RequestMapping(value = "/monthTrainReport")
	public ModelAndView monthTrainReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/edu/trainreport/monthTrainReport",
				modelMap);
	}
	
	@RequestMapping(value = "/monthTrainReportExcel")
	public ModelAndView monthTrainReportExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		modelMap.put("reportList", this.trainReportSer.monthTrainList(request));
		modelMap.put("COURSE_DATE", request.getParameter("COURSE_DATE"));

		return new ModelAndView("/edu/trainreport/monthTrainReportExcel",
				modelMap);
	}
	
	@RequestMapping(value = "/formTrainReport")
	public ModelAndView formTrainReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/edu/trainreport/formTrainReport",
				modelMap);
	}
	
	@RequestMapping(value = "/formTrainReportExcel")
	public ModelAndView formTrainReportExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		modelMap.put("reportList", this.trainReportSer.formTrainList(request));
		modelMap.put("TRAIN_FORM_CODE", request.getParameter("TRAIN_FORM_CODE"));

		return new ModelAndView("/edu/trainreport/formTrainReportExcel",
				modelMap);
	}
	
	

}
