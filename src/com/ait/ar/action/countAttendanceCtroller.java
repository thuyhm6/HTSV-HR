package com.ait.ar.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ait.ar.service.ItemsSer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ait.ess.service.ViewDeptPerSer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ait.ar.service.countAttendanceSer;
import com.ait.disc.action.RetrieveMasterListCtroller;
import com.ait.ess.service.EssEmpInfoSer;
import com.ait.ess.service.PersonInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;

/**
 * 决裁情况查看(view apply approve result)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: ViewApplyCtroller.java
 * @Description:
 * @Create date: Feb 17, 2012 9:40:40 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 17, 2012 9:40:40 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/countAttendance")
public class countAttendanceCtroller {

	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	Logger logger = Logger.getLogger(countAttendanceCtroller.class);

	@Autowired
	private EssEmpInfoSer empInfoSer;

	@Autowired
	private PersonInfoSer personInfoSer;
	@Autowired
	private ViewDeptPerSer ViewDeptPerSer;

	@Autowired
	private ItemsSer itemsSer;
	@Autowired
	private countAttendanceSer countAttendenceSer;
	@Autowired
	private RetrieveMasterListCtroller RetrieveMasterListCtroller;

	/*
	 * @Autowired private EmpInfoSer empInfoSer;
	 * 
	 * @Autowired private PersonInfoSer personInfoSer;
	 */
	@Autowired
	private ToolMenuSer toolMenuSer;

	/* 考勤里的各种统计 上面可全部删除 */
	/******************************************************************
	 * ******************************************************************** *
	 * ******************************************************************** *
	 * ********************************************************************
	 */

	/**
	 * 部门现状
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForDeptCountInfoList")
	public ModelAndView arForDeptCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String currentIndex = request.getParameter("currentIndex");
		if ("1".equals(currentIndex)) {
			modelMap.put("currentIndex", 1);
		} else {
			modelMap.put("currentIndex", 0);
		}
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("managePart", admin.getDeptNo().substring(admin.getDeptNo().lastIndexOf("_")+1));
		modelMap.put("SON_FLAG", request.getParameter("SON_FLAG"));
		modelMap.put("STIME", this.getDateFirst("dd/MM/yyyy"));
		modelMap.put("ETIME", this.getDateNow("dd/MM/yyyy"));
		modelMap.put("empTypeCodeList", empInfoSer.getCodeList("13864", request));
		return new ModelAndView("/ar/countAttendance/arForDeptCountInfoList",
				modelMap);
	}

	/**
	 * 部门现状下面部分
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForDeptCountInfoSonList")
	public ModelAndView arForDeptCountInfoSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		if ("0".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.countAttendenceSer.arForDeptCountInfoSonArList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 0);
		} else if ("1".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.countAttendenceSer.arForDeptCountInfoSonOtList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 1);
		}
		return new ModelAndView("/ar/countAttendance/arForDeptCountInfoSonList", modelMap);
	}
	
	
	/**
	 * 医疗期天数统计
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForMedicalCountInfoList")
	public ModelAndView arForMedicalCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List itemList = itemsSer.getItemParamList2(request);
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO")); 
		modelMap.put("CONFIRM_FLAG", request.getParameter("seach_CONFIRM_FLAG")); 
		modelMap.put("empName", request.getParameter("dwz.person.empName")); 
		modelMap.put("empInfo", request.getParameter("dwz.person.empInfo")); 
		modelMap.put("GROUP_NO", request.getParameter("seach_GROUP_NO")); 
		modelMap.put("SHIFT_NO", request.getParameter("seach_SHIFT_NO")); 
		modelMap.put("ITEM_NO", request.getParameter("seach_ITEM_NO")); 
		modelMap.put("AFFIRM_FLAG", request.getParameter("seach_AFFIRM_FLAG")); 
		modelMap.put("CONFIRM_FLAG", request.getParameter("seach_CONFIRM_FLAG")); 
		modelMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE")); 
		modelMap.put("EmpOffice", request.getParameter("seach_EmpOffice")); 
		modelMap.put("KEY", request.getParameter("seach_KEY")); 
		modelMap.put("FROM_DATE", request.getParameter("seach_FROM_DATE"));
		modelMap.put("TO_DATE", request.getParameter("seach_FROM_DATE")); 
		
		List list = ViewDeptPerSer.arForMedicalCountInfoList(request);
		modelMap.put("itemList", itemList) ;
		modelMap.put("useOfAnnualLeaveList", list);
		//System.out.println(modelMap.get("useOfAnnualLeaveList"));
	    modelMap.put("viewUseOfAnnualLeaveListCnt", this.ViewDeptPerSer.viewUseOfAnnualLeaveListCnt(request));
	    modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ar/countAttendance/arForMedicalCountInfoList", modelMap);
	} 
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewMedicalInfo")  
	public ModelAndView viewMedicalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("leaveCoordList", this.ViewDeptPerSer.viewMedicalInfo(request));
		modelMap.put("leaveInfoList", this.ViewDeptPerSer.leaveInfoList(request));
		return new ModelAndView("/ar/countAttendance/viewMedicalInfo", modelMap);
	}           

	/**
	 * 部门现状提取资料
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForDeptCountInfoSonListExport")
	public void arForDeptCountInfoSonListExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		LinkedHashMap list = new LinkedHashMap();

		if ("0".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.countAttendenceSer
					.arForDeptCountInfoSonArList(request);
			modelMap.put("ValueList", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("XLS_NAME", "Department attendance status");
			modelMap.put("XLS_IN", "arForDeptCountInfoSonArListExport");
		} else if ("1".equals(currentIndex)) {
			LinkedHashMap codeInfoTreeList = this.countAttendenceSer
					.arForDeptCountInfoSonOtList(request);
			modelMap.put("ValueList", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("XLS_NAME", "Department overtime status");
			modelMap.put("XLS_IN", "arForDeptCountInfoSonOtListExport");
		}

		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}

	/**
	 * 个人现状
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arCountInfoList")
	public ModelAndView arCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String currentIndex = request.getParameter("currentIndex");
		if ("1".equals(currentIndex)) {
			modelMap.put("currentIndex", 1);
		} else {
			modelMap.put("currentIndex", 0);
		}
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPTNO", admin.getCpnyId().substring(admin.getCpnyId().lastIndexOf("_")+1));
		modelMap.put("SON_FLAG", request.getParameter("SON_FLAG"));
		modelMap.put("STIME", this.getDateFirst("dd/MM/yyyy"));
		modelMap.put("ETIME", this.getDateNow("dd/MM/yyyy"));

		return new ModelAndView("/ar/countAttendance/arCountInfoList", modelMap);
	}

	/**
	 * 个人现状下面部分
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arCountInfoSonList")
	public ModelAndView arCountInfoSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String currentIndex = request.getParameter("currentIndex");
		modelMap.put("PA_COUNT", request.getParameter("seach_PA_COUNT"));
		modelMap.put("KEY", request.getParameter("seach_KEY"));
		String dept_no = request.getParameter("seach_DEPT_NO");
		if ("0".equals(currentIndex)) {
				List list = countAttendenceSer.arCountInfoSonList(request);
				modelMap.put("personList", list);
			modelMap.put("currentIndex", 0);
		} else if ("1".equals(currentIndex)) {
				List list = countAttendenceSer.viewOtApplyPersonalList(request);
				modelMap.put("viewOtApplyPersonalList", list);
			modelMap.put("currentIndex", 1);
		} else if ("2".equals(currentIndex)) {
			List list = countAttendenceSer.viewOtYearPersonalList(request);
			modelMap.put("viewOtYearPersonalList", list);
			modelMap.put("currentIndex", 2);
		}
		return new ModelAndView("/ar/countAttendance/arCountInfoSonList",modelMap);
	}

	// 现况的报表
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arCountInfoSonList_Export")
	public void exportTest(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		String PA_COUNT = request.getParameter("seach_PA_COUNT");
		modelMap.put("PA_COUNT", PA_COUNT);
		String dept_no = request.getParameter("seach_DEPT_NO");
		String report = request.getParameter("report");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = new ArrayList();

		if ("0".equals(currentIndex)) {
//			if (dept_no != null && !dept_no.equals("")) {
				if (report != null && !report.equals("")) {
					modelMap.put("XLS_NAME", "Bảng thống kê nghỉ phép");
				} else {
					modelMap.put("XLS_NAME", "Thống kê nghỉ phép cá nhân");
				}
				modelMap.put("XLS_IN", "arCount_Export_Ar");
				list = countAttendenceSer.arCountInfoSonList(request);
				modelMap.put("personList", list);
//			}
			modelMap.put("currentIndex", 0);
		} else if ("1".equals(currentIndex)) {
//			if (dept_no != null && !dept_no.equals("")) {

				list = countAttendenceSer.viewOtApplyPersonalList(request);
				modelMap.put("viewOtApplyPersonalList", list);
					modelMap.put("XLS_NAME", "Thống kê tăng ca cá nhân");
					modelMap.put("XLS_IN", "arCount_Export_Ot");
//			}
			modelMap.put("currentIndex", 1);
		} else if ("2".equals(currentIndex)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy");
			if (request.getParameter("seach_STIME") == "" || request.getParameter("seach_STIME") == null) {
				// 获取当前月第一天：
				Calendar c = Calendar.getInstance();
				c.add(Calendar.MONTH, 0);
				// 设置为1号,当前日期既为本月第一天
				c.set(Calendar.DAY_OF_MONTH, 1);
				String first = format.format(c.getTime());
				modelMap.put("YEAR", first);
			}

				list = countAttendenceSer.viewOtYearPersonalList(request);
				modelMap.put("viewOtApplyPersonalList", list);
					modelMap.put("XLS_NAME", "arCount_Export_Year_Ot_Person");
					modelMap.put("XLS_IN", "arCount_Export_Year_Ot");
//			}
			modelMap.put("currentIndex", 2);
		}
		modelMap.put("ValueList", list);
		modelMap.put("CPNY_ID",admin.getCpnyId());
		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}

	/**
	 * 日期现状
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForDateCountInfoList")
	public ModelAndView arForDateCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String currentIndex = request.getParameter("currentIndex");
		if ("1".equals(currentIndex)) {
			modelMap.put("currentIndex", 1);
		} else {
			modelMap.put("currentIndex", 0);
		}
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPTNO", admin.getDeptNo().substring(admin.getDeptNo().lastIndexOf("_")+1));
		modelMap.put("SON_FLAG", request.getParameter("SON_FLAG"));
		modelMap.put("STIME", this.getDateFirst("dd/MM/yyyy"));
		modelMap.put("ETIME", this.getDateNow("dd/MM/yyyy"));
		return new ModelAndView("/ar/countAttendance/arForDateCountInfoList",
				modelMap);
	}

	/**
	 * 日期现状下面部分
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForDateCountInfoSonList")
	public ModelAndView arForDateCountInfoSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		if ("0".equals(currentIndex)) {
			List list = countAttendenceSer.arForDateCountInfoSonArList(request);
			modelMap.put("arForDateCountInfoSonArList", list);
			modelMap.put("currentIndex", 0);
		} else if ("1".equals(currentIndex)) {
			List list = countAttendenceSer.arForDateCountInfoSonOtList(request);
			modelMap.put("arForDateCountInfoSonOtList", list);
			modelMap.put("currentIndex", 1);
		}
		return new ModelAndView(
				"/ar/countAttendance/arForDateCountInfoSonList", modelMap);
	}

	/**
	 * 日期现状提取资料
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForDateCountInfoSonListExport")
	public void arForDateCountInfoSonListExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = new ArrayList();
		if ("0".equals(currentIndex)) {
			list = countAttendenceSer.arForDateCountInfoSonArList(request);
			modelMap.put("XLS_NAME", "Thống kê nghỉ phép theo ngày");
			modelMap.put("XLS_IN", "arForDateCountInfoSonArListExport");
		} else if ("1".equals(currentIndex)) {
			list = countAttendenceSer.arForDateCountInfoSonOtList(request);
			modelMap.put("XLS_NAME", "Thống kế tăng ca theo ngày");
			modelMap.put("XLS_IN", "arForDateCountInfoSonOtListExport");
		}
		modelMap.put("ValueList", list);
		modelMap.put("CPNY_ID",admin.getCpnyId());
		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}

	/**
	 * 职级现状
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForGradeCountInfoList")
	public ModelAndView arForGradeCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String currentIndex = request.getParameter("currentIndex");
		if ("1".equals(currentIndex)) {
			modelMap.put("currentIndex", 1);
		} else {
			modelMap.put("currentIndex", 0);
		}
		modelMap.put("STIME", this.getDateFirst("yyyy/MM/dd"));
		modelMap.put("ETIME", this.getDateNow("yyyy/MM/dd"));
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPTNO", admin.getCpnyId().substring(admin.getCpnyId().lastIndexOf("_")+1));
		modelMap.put("SON_FLAG", request.getParameter("SON_FLAG"));

		return new ModelAndView("/ar/countAttendance/arForGradeCountInfoList",
				modelMap);
	}

	/**
	 * 职级现状下面部分
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForGradeCountInfoSonList")
	public ModelAndView arForGradeCountInfoSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");

		if ("0".equals(currentIndex)) {
			List list = countAttendenceSer
					.arForGradeCountInfoSonArList(request);
			modelMap.put("arForDateCountInfoSonArList", list);
			modelMap.put("currentIndex", 0);
		} else if ("1".equals(currentIndex)) {
			List list = countAttendenceSer
					.arForGradeCountInfoSonOtList(request);
			modelMap.put("arForDateCountInfoSonOtList", list);
			modelMap.put("currentIndex", 1);
		}

		return new ModelAndView(
				"/ar/countAttendance/arForGradeCountInfoSonList", modelMap);
	}

	/**
	 * 职级现状提取资料
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForGradeCountInfoSonListExport")
	public void arForGradeCountInfoSonListExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = new ArrayList();
		if ("0".equals(currentIndex)) {
			list = countAttendenceSer.arForGradeCountInfoSonArList(request);
			modelMap.put("XLS_NAME", "职级-考勤现况");
			modelMap.put("XLS_IN", "arForGradeCountInfoSonArListExport");
		} else if ("1".equals(currentIndex)) {
			list = countAttendenceSer.arForGradeCountInfoSonOtList(request);
			modelMap.put("XLS_NAME", "职级-加班现况");
			modelMap.put("XLS_IN", "arForGradeCountInfoSonOtListExport");
		}
		modelMap.put("CPNY_ID",admin.getCpnyId());
		modelMap.put("ValueList", list);
		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}

	/**
	 * 业务现状
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForPositionCountInfoList")
	public ModelAndView arForPositionCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String currentIndex = request.getParameter("currentIndex");
		if ("1".equals(currentIndex)) {
			modelMap.put("currentIndex", 1);
		} else {
			modelMap.put("currentIndex", 0);
		}
		modelMap.put("DEPTNO", admin.getCpnyId().substring(admin.getCpnyId().lastIndexOf("_")+1));
		modelMap.put("STIME", this.getDateFirst("yyyy/MM/dd"));
		modelMap.put("ETIME", this.getDateNow("yyyy/MM/dd"));
		return new ModelAndView(
				"/ar/countAttendance/arForPositionCountInfoList", modelMap);
	}

	/**
	 * 业务现状下面部分
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForPositionCountInfoSonList")
	public ModelAndView arForPositionCountInfoSonList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		if ("0".equals(currentIndex)) {
			List list = countAttendenceSer
					.arForPositionCountInfoSonArList(request);
			modelMap.put("arForDateCountInfoSonArList", list);
			modelMap.put("currentIndex", 0);
		} else if ("1".equals(currentIndex)) {
			List list = countAttendenceSer
					.arForPositionCountInfoSonOtList(request);
			modelMap.put("arForDateCountInfoSonOtList", list);
			modelMap.put("currentIndex", 1);
		}
		return new ModelAndView(
				"/ar/countAttendance/arForPositionCountInfoSonList", modelMap);
	}

	/**
	 * 职级现状提取资料
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arForPositionCountInfoSonListExport")
	public void arForPositionCountInfoSonListExport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = new ArrayList();
		if ("0".equals(currentIndex)) {
			list = countAttendenceSer.arForPositionCountInfoSonArList(request);
			modelMap.put("XLS_NAME", "职责-考勤现况");
			modelMap.put("XLS_IN", "arForPositionCountInfoSonArListExport");
		} else if ("1".equals(currentIndex)) {
			list = countAttendenceSer.arForPositionCountInfoSonOtList(request);
			modelMap.put("XLS_NAME", "职责-加班现况");
			modelMap.put("XLS_IN", "arForPositionCountInfoSonOtListExport");
		}
		modelMap.put("CPNY_ID",admin.getCpnyId());
		modelMap.put("ValueList", list);
		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}

	// 获取当前月第一天 传入的是 日期格式
	public String getDateFirst(String forma) {
		SimpleDateFormat format = new SimpleDateFormat(forma);
		String first = "";
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		first = format.format(c.getTime());
		return first;
	}

	// 获取当前日期

	public String getDateNow(String forma) {
		SimpleDateFormat format = new SimpleDateFormat(forma);

		String sysdate = "";

		Calendar c = Calendar.getInstance();
		sysdate = format.format(c.getTime());

		return sysdate;

	}

	/**
	 * 个人现状考勤业务报表
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/arCountInfoReport")
	public ModelAndView arCountInfoReport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String currentIndex = request.getParameter("currentIndex");

		if (admin.getCpnyId().equals("TSTO")) {
			modelMap.put("DEPTNO", "D11EQ000");
		} else {
			modelMap.put("DEPTNO", "D11AR000");
		}
		modelMap.put("STIME", this.getDateFirst("yyyy/MM/dd"));
		modelMap.put("ETIME", this.getDateNow("yyyy/MM/dd"));

		return new ModelAndView("/ar/countAttendance/arCountInfoReport",
				modelMap);
	}

}
