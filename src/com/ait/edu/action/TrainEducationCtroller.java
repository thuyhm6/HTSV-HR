package com.ait.edu.action;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.edu.dao.TrainEducationDao;
import com.ait.edu.service.TrainEducationSer;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

@Controller
@RequestMapping(value = "/edu/traineducation")
public class TrainEducationCtroller {
	Logger logger = Logger.getLogger(TrainEducationCtroller.class);
	@Autowired
	private TrainEducationSer eduTrainser;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private TrainEducationDao eduTrainDao;
	
	// 培训教育首页面
	@RequestMapping(value = "/viewTrainEducation")
	public ModelAndView viewTrainEducation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/edu/traineducation/viewTrainEducation",
				modelMap);
	}
    // *******************************************培训设置*********************************************
	// =========================================系统管理===============================================
	// 系统管理页面查询
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/systemManager")
	public ModelAndView systemManager(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List elist = eduTrainser.systemManager(request);
		modelMap.put("systemManagerList", elist);
		modelMap.put("systemManagerListCount", elist.size());
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("TRAIN_DIFF_CODE", request.getParameter("TRAIN_DIFF_CODE"));
		modelMap.put("TRAIN_TYPE_CODE", request.getParameter("TRAIN_TYPE_CODE"));
		return new ModelAndView("/edu/traineducation/systemManager", modelMap);
	}

	// 修改查询页面
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/systemManagerInfo")
	public ModelAndView systemManagerInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap linkMap = (LinkedHashMap) eduTrainser
				.systemManagerInfo(request);
		modelMap.put("systemManagerInfo", linkMap);
		return new ModelAndView("/edu/traineducation/systemManagerInfo",
				modelMap);
	}

	// 体系管理 添加页面
	@RequestMapping(value = "/addSystemManager")
	public ModelAndView addSystemManager(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/edu/traineducation/addSystemManager",
				modelMap);
	}

	// 体系管理 添加页面 插入数据
	@RequestMapping(value = "/addSystemManagerInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTrainEducationInfo(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.eduTrainser.addSystemManagerInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));//添加成功
			map.put("navTabId", "edu0101");
		} else {
			map.put("statusCode", "300");
			if (result == 2) {
				map.put("message", "Loại hình đào tạo không được trùng lặp!");
			} else {
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));//添加失败
			}

		}
		return map;
	}

	// 体系管理 修改数据
	@RequestMapping(value = "/updateSystemManager", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSystemManager(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.eduTrainser.updateSystemManager(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));//修改成功
			map.put("navTabId", "edu0101");
		} else {
			map.put("statusCode", "300");
			map.put("message",
					TipMessage.getTipMessage("alert.message.update_fail", request));//修改失败
		}
		return map;
	}

	// =========================================课程管理===============================================
	
	    // 课程管理页面查询
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/courseManager")
		public ModelAndView courseManager(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List elist = eduTrainser.courseManager(request);
			modelMap.put("courseManagerList", elist);
			modelMap.put("courseManagerListCount", elist.size());
			modelMap.put("defaultCpny", admin.getCpnyId());
			modelMap.put("TRAIN_TYPE_CODE", request.getParameter("TRAIN_TYPE_CODE"));
			modelMap.put("TRAIN_DIFF_CODE", request.getParameter("TRAIN_DIFF_CODE"));
			modelMap.put("COURSE_NAME_CODE", request.getParameter("COURSE_NAME_CODE"));
			return new ModelAndView("/edu/traineducation/courseManager", modelMap);
		}
	
		// 课程管理添加页面
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/addCourseManager")
		public ModelAndView addCourseManager(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List elist = eduTrainser.systemManager(request);
			modelMap.put("systemManagerList", elist);
			modelMap.put("defaultCpny", admin.getCpnyId());
			return new ModelAndView("/edu/traineducation/addCourseManager",
					modelMap);
		}
	
		// 体系管理 添加页面 插入数据
		@RequestMapping(value = "/addCourseManagerInfo", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addCourseManagerInfo(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.addCourseManagerInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));//添加成功
				map.put("navTabId", "edu0102");
			} else {
				map.put("statusCode", "300");
				if (result == 2) {
					map.put("message", "课程名称不允许重复!");
				} else {
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));//添加失败
				}

			}
			return map;
		}
	
		// 课程管理修改查询页面
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/courseManagerInfo")
		public ModelAndView courseManagerInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.courseManagerInfo(request);
			modelMap.put("courseManagerInfo", linkMap);
			return new ModelAndView("/edu/traineducation/courseManagerInfo",
					modelMap);
		}
	
		
		// 课程管理 修改数据
		@RequestMapping(value = "/updateCourseManager", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateCourseManager(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.updateCourseManager(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));//修改成功
				map.put("navTabId", "edu0102");
			} else {
				map.put("statusCode", "300");
				map.put("message",
						TipMessage.getTipMessage("alert.message.update_fail", request));//修改失败
			}
			return map;
		}
		
		//========================================计划管理=========================================
		// 计划管理页面查询
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/planManager")
		public ModelAndView planManager(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List elist = eduTrainser.planManager(request);
			modelMap.put("planManagerList", elist);
			modelMap.put("planManagerListCount", elist.size());
			modelMap.put("TRAIN_TYPE_CODE", request.getParameter("TRAIN_TYPE_CODE"));
			modelMap.put("TRAIN_DIFF_CODE", request.getParameter("TRAIN_DIFF_CODE"));
			modelMap.put("COURSE_NAME_CODE", request.getParameter("COURSE_NAME_CODE"));
			modelMap.put("defaultCpny", admin.getCpnyId());
			return new ModelAndView("/edu/traineducation/planManager", modelMap);
		}
		
		// 计划管理添加页面
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/addPlanManager")
		public ModelAndView addPlanManager(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List elist = eduTrainser.courseManager(request);
			String planno = eduTrainser.queryPlanNo(request);
			modelMap.put("planno", planno);
			modelMap.put("courseManagerList", elist);
			modelMap.put("defaultCpny", admin.getCpnyId());
			return new ModelAndView("/edu/traineducation/addPlanManager",
					modelMap);
		}
		
		//按照指定部门查询指定人员
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/desEmployee")
		public ModelAndView desEmployee(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.desEmployee(request);
			modelMap.put("desEmployeeList", elist);
			modelMap.put("desEmployeeListCount", elist.size());
			modelMap.put("EMPID", request.getParameter("EMPID"));
			modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
			modelMap.put("fenlei", request.getParameter("fenlei"));
			modelMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
			return new ModelAndView("/edu/traineducation/desEmployee", modelMap);
		}
		
		//搜索讲师
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/teacherSearch")
		public ModelAndView teacherSearch(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.teacherSearch(request);
			modelMap.put("teacherSearchList", elist);
			modelMap.put("teacherSearchListCount", elist.size());
			modelMap.put("teacherEmpid", request.getParameter("teacherEmpid"));
			return new ModelAndView("/edu/traineducation/teacherSearch", modelMap);
		}
		
		//查看课程表
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/queryCourseSyllabus")
		public ModelAndView queryCourseSyllabus(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.queryCourseSyllabus(request);
			modelMap.put("queryCourseSyllabusList", elist);
			modelMap.put("queryCourseSyllabusListCount", elist.size());
			modelMap.put("PLAN_NO",request.getParameter("PLAN_NO"));
			return new ModelAndView("/edu/traineducation/queryCourseSyllabus", modelMap);
		}
		//查看课程表
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/queryCourseSyllabus2")
		public ModelAndView queryCourseSyllabus2(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.queryCourseSyllabus(request);
			modelMap.put("queryCourseSyllabusList", elist);
			modelMap.put("queryCourseSyllabusListCount", elist.size());
			return new ModelAndView("/edu/traineducation/queryCourseSyllabus2", modelMap);
		}
		//日历查看课程表
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/queryCourseSyllabus3")
		public ModelAndView queryCourseSyllabus3(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.queryCourseSyllabus3(request);
			modelMap.put("queryCourseSyllabusList", elist);
			modelMap.put("queryCourseSyllabusListCount", elist.size());
			return new ModelAndView("/edu/traineducation/queryCourseSyllabus3", modelMap);
		}
		
		// 计划管理 添加页面 插入数据
		@RequestMapping(value = "/addPlanManagerInfo", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addPlanManagerInfo(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.addPlanManagerInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));//添加成功
				map.put("navTabId", "edu0103");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));//添加失败

			}
			return map;
		}
		
		// 计划管理修改查询页面
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/planManagerInfo")
		public ModelAndView planManagerInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.planManagerInfo(request);
			modelMap.put("planManagerInfo", linkMap);
			String desEmployee=eduTrainser.queryDesEmployee(request);
			modelMap.put("DES_EMPLOYEE", desEmployee);
			String desEmployeeName=eduTrainser.queryDesEmployeeName(request);
			modelMap.put("DES_EMPLOYEE_NAME", desEmployeeName);
			String teacherEmpid=eduTrainser.queryTeacherName(request);
			String teacherName=eduTrainser.queryTeacherNameEmpid(request);
			if(!"".equals(teacherName) && teacherName != null){
				modelMap.put("TEACHER_NAME", teacherName);
				modelMap.put("TEACHER_EMPID", teacherEmpid);
			}else{
				modelMap.put("TEACHER_NAME", teacherEmpid);
			}
			
			String plan_no=StringUtil.checkNull(request.getParameter("PLAN_NO"));
				//附件下载功能
			if(!"".equals(plan_no) && plan_no != null){
				LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "eduPlanManager");
				fileParam.put("APPLY_NO", plan_no);
				List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
				linkMap.put("fileList",fileList);
			}
			modelMap.put("defaultCpny", admin.getCpnyId());
			modelMap.put("PERSON_ID", admin.getAdminID());
			return new ModelAndView("/edu/traineducation/planManagerInfo",
					modelMap);
		}
		
		//获得当前课程的指定部门的目录树
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/getDeptTree")
		@ResponseBody
		public List getDeptTree(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			List getDeptTree = this.eduTrainser.getDeptTree(request);
			return getDeptTree;
		}
		
		// 计划管理 删除数据
		@RequestMapping(value = "/deletePlanManager")
		@ResponseBody
		public Map<String, Object> deletePlanManager(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.deletePlanManager(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));// 删除成功
				map.put("formId", "planManager");
			} else {
				map.put("statusCode", "300");
			    map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));// 删除失败
			}
			return map;
		}
		// 计划管理课程 删除数据
		@RequestMapping(value = "/deleteCourseSyllabus")
		@ResponseBody
		public Map<String, Object> deleteCourseSyllabus(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.deleteCourseSyllabus(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));// 删除成功
				map.put("formId", "courseSyllabus");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));// 删除失败
			}
			return map;
		}
		
		// 计划管理 修改数据
		@RequestMapping(value = "/updatePlanManager", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updatePlanManager(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.updatePlanManager(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));//修改成功
				map.put("navTabId", "edu0103");
			} else {
				map.put("statusCode", "300");
				map.put("message",
						TipMessage.getTipMessage("alert.message.update_fail", request));//修改失败
			}
			return map;
		}
		
		//计划管理 点击查看页面
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/singlePlanManagerInfo")
		public ModelAndView singlePlanManagerInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.planManagerInfo(request);
			int courseNum =  eduTrainser.getcourseNum(request);
			modelMap.put("planManagerInfo", linkMap);
			String desEmployee=eduTrainser.queryDesEmployee(request);
			modelMap.put("DES_EMPLOYEE", desEmployee);
			String desEmployeeName=eduTrainser.queryDesEmployeeName(request);
			modelMap.put("DES_EMPLOYEE_NAME", desEmployeeName);
			String teacherEmpid=eduTrainser.queryTeacherName(request);
			String teacherName=eduTrainser.queryTeacherNameEmpid(request);
			if(!"".equals(teacherName) && teacherName != null){
				modelMap.put("TEACHER_NAME", teacherName);
				modelMap.put("TEACHER_EMPID", teacherEmpid);
			}else{
				modelMap.put("TEACHER_NAME", teacherEmpid);
			}
			
			String plan_no=StringUtil.checkNull(request.getParameter("PLAN_NO"));
				//附件下载功能
			if(!"".equals(plan_no) && plan_no != null){
				LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "eduPlanManager");
				fileParam.put("APPLY_NO", plan_no);
				List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
				linkMap.put("fileList",fileList);
			}
			modelMap.put("PERSON_ID", admin.getAdminID());
			return new ModelAndView("/edu/traineducation/singlePlanManagerInfo",
					modelMap);
		}
		
		//========================================讲师管理=========================================
		// 讲师管理页面查询
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/teacherManager")
		public ModelAndView teacherManager(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List elist = eduTrainser.teacherManager(request);
			modelMap.put("teacherManagerList", elist);
			modelMap.put("teacherManagerListCount", elist.size());
			modelMap.put("TEACH_NAME_EMPID", StringUtil.checkNull(request.getParameter("TEACH_NAME_EMPID")));
			modelMap.put("TEACH_FIELD_CODE", StringUtil.checkNull(request.getParameter("TEACH_FIELD_CODE")));
			modelMap.put("TEACH_LEVEL_CODE", StringUtil.checkNull(request.getParameter("TEACH_LEVEL_CODE")));
			modelMap.put("TEACH_STATUS_CODE", StringUtil.checkNull(request.getParameter("TEACH_STATUS_CODE")));
			modelMap.put("defaultCpny", admin.getCpnyId());
			return new ModelAndView("/edu/traineducation/teacherManager", modelMap);
		}
		
		// 教师管理添加页面
		@RequestMapping(value = "/addTeacherManager")
		public ModelAndView addTeacherManager(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			modelMap.put("defaultCpny", admin.getCpnyId());
			return new ModelAndView("/edu/traineducation/addTeacherManager",
					modelMap);
		}
		
		// 教师管理添加页面 插入数据
		@RequestMapping(value = "/addTeacherManagerInfo", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addTeacherManagerInfo(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.addTeacherManagerInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));//添加成功
				map.put("navTabId", "edu0104");
			} else {
				map.put("statusCode", "300");
				if(result == 2){
					map.put("message", "请先选择人员,再进行添加!");
				}else{
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));//添加失败
				}
				
			}
			return map;
		}
		
		//按照姓名/工号模糊查询讲师
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/queryTeacher")
		public ModelAndView queryTeacher(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.queryTeacher(request);
			modelMap.put("queryTeacherList", elist);
			modelMap.put("queryTeacherListCount", elist.size());
			return new ModelAndView("/edu/traineducation/queryTeacher",
					modelMap);
		}
		// 教师管理修改查询页面
		@SuppressWarnings({ "rawtypes" })
		@RequestMapping(value = "/teacherManagerInfo")
		public ModelAndView teacherManagerInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.teacherManagerInfo(request);
			modelMap.put("teacherManagerInfo", linkMap);
			modelMap.put("defaultCpny", admin.getCpnyId());
			return new ModelAndView("/edu/traineducation/teacherManagerInfo",
					modelMap);
		}
		
		// 教师管理修改数据
		@RequestMapping(value = "/updateTeacherManager", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateTeacherManager(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.updateTeacherManager(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));//修改成功
				map.put("navTabId", "edu0104");
			} else {
				map.put("statusCode", "300");
				map.put("message",
						TipMessage.getTipMessage("alert.message.update_fail", request));//修改失败
			}
			return map;
		}
		
		
		// 教师管理 删除数据
		@RequestMapping(value = "/deleteTeacherManager")
		@ResponseBody
		public Map<String, Object> deleteTeacherManager(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.deleteTeacherManager(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));// 删除成功
				map.put("formId", "teacherManager");
			} else {
				map.put("statusCode", "300");
			    map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));// 删除失败
			}
			return map;
		}
		// 体系管理 删除数据
		@RequestMapping(value = "/deleteSystemMan")
		@ResponseBody
		public Map<String, Object> deleteSystemMan(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.deleteSystemMan(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));// 删除成功
				map.put("formId", "teacherManager");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));// 删除失败
			}
			return map;
		}
		
		
		//教师管理点击姓名查看教师信息页面
		/*@SuppressWarnings({ "rawtypes" })
		@RequestMapping(value = "/singleTeacherInformation")
		public ModelAndView singleTeacherManagerInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.singleTeacherInformation(request);
			modelMap.put("teacherInformationList", elist);
			modelMap.put("teacherInformationListCount", elist.size());
			return new ModelAndView("/edu/traineducation/singleTeacherInformation",
					modelMap);
		}*/
		
		//========================================培训机构=========================================
		// 培训机构页面查询
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/trainOrgan")
		public ModelAndView trainOrgan(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.trainOrgan(request);
			modelMap.put("trainOrganList", elist);
			modelMap.put("trainOrganListCount", elist.size());
			modelMap.put("ORGAN_NAME", StringUtil.checkNull(request.getParameter("ORGAN_NAME")));
			modelMap.put("ADDRESS", StringUtil.checkNull(request.getParameter("ADDRESS")));
			if(elist != null && elist.size() > 0){
				for(int i=0;i<elist.size();i++){
					LinkedHashMap linkMap =new LinkedHashMap();
					linkMap=(LinkedHashMap) elist.get(i);
					Object organ_no=linkMap.get("ORGAN_NO");
					organ_no=String.valueOf(organ_no);
					//附件下载功能
					LinkedHashMap fileParam = new LinkedHashMap();
					fileParam.put("APPLY_TYPE", "eduTrainOrgan");
					fileParam.put("APPLY_NO", organ_no);
					List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
					linkMap.put("fileList",fileList);
				}
			}
			return new ModelAndView("/edu/traineducation/trainOrgan", modelMap);
		}
		
		// 培训机构添加页面
		@RequestMapping(value = "/addTrainOrgan")
		public ModelAndView addTrainOrgan(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			return new ModelAndView("/edu/traineducation/addTrainOrgan",
					modelMap);
		}
		
		
		// 培训机构添加页面 插入数据
		@RequestMapping(value = "/addTrainOrganInfo", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addTrainOrganInfo(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.addTrainOrganInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));//添加成功
				map.put("navTabId", "edu0106");
			} else {
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));//添加失败
			}
			return map;
		}
		
		// 培训机构修改查询页面
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/trainOrganInfo")
		public ModelAndView trainOrganInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainOrganInfo(request);
			String organ_no=StringUtil.checkNull(request.getParameter("ORGAN_NO"));
			//附件下载功能
			if(!"".equals(organ_no) && organ_no != null){
				LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "eduTrainOrgan");
				fileParam.put("APPLY_NO", organ_no);
				List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
				linkMap.put("fileList",fileList);
			}
			modelMap.put("PERSON_ID", admin.getAdminID());
			modelMap.put("trainOrganInfo", linkMap);
			return new ModelAndView("/edu/traineducation/trainOrganInfo",
					modelMap);
		}
		
		// 培训机构修改数据
		@RequestMapping(value = "/updateTrainOrgan", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateTrainOrgan(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.updateTrainOrgan(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));//修改成功
				map.put("navTabId", "edu0106");
			} else {
				map.put("statusCode", "300");
				map.put("message",
						TipMessage.getTipMessage("alert.message.update_fail", request));//修改失败
			}
			return map;
		}
		
		// 培训机构 删除数据
		@RequestMapping(value = "/deleteTrainOrgan")
		@ResponseBody
		public Map<String, Object> deleteTrainOrgan(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.deleteTrainOrgan(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));// 删除成功
				map.put("formId", "trainOrgan");
			} else {
				map.put("statusCode", "300");
			    map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));// 删除失败
			}
			return map;
		}
		
		//培训机构点击查看页面
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/singleTrainOrganInfo")
		public ModelAndView singleTrainOrganInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainOrganInfo(request);
			modelMap.put("trainOrganInfo", linkMap);
			String organ_no=StringUtil.checkNull(request.getParameter("ORGAN_NO"));
			//附件下载功能
			if(!"".equals(organ_no) && organ_no != null){
				LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "eduTrainOrgan");
				fileParam.put("APPLY_NO", organ_no);
				List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
				linkMap.put("fileList",fileList);
			}
			return new ModelAndView("/edu/traineducation/singlePlanManagerInfo",
					modelMap);
		}
		
		
		//========================================培训协议=========================================
		// 培训协议页面查询
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/trainAgreement")
		public ModelAndView trainAgreement(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List elist = eduTrainser.trainAgreement(request);
			modelMap.put("trainAgreementList", elist);
			modelMap.put("trainAgreementListCount", elist.size());
			modelMap.put("AGREE_DEPTNO", StringUtil.checkNull(request.getParameter("AGREE_DEPTNO")));
			modelMap.put("AGREE_NAME_EMPID", StringUtil.checkNull(request.getParameter("AGREE_NAME_EMPID")));
			modelMap.put("CON_START_DATE", StringUtil.checkNull(request.getParameter("CON_START_DATE")));
			modelMap.put("CON_END_DATE", StringUtil.checkNull(request.getParameter("CON_END_DATE")));
			if(elist != null && elist.size() > 0){
				for(int i=0;i<elist.size();i++){
					LinkedHashMap linkMap =new LinkedHashMap();
					linkMap=(LinkedHashMap) elist.get(i);
					Object agree_no=linkMap.get("AGREE_NO");
					agree_no=String.valueOf(agree_no);
					//附件下载功能
					LinkedHashMap fileParam = new LinkedHashMap();
					fileParam.put("APPLY_TYPE", "eduTrainAgreement");
					fileParam.put("APPLY_NO", agree_no);
					List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
					linkMap.put("fileList",fileList);
				}
			}
			modelMap.put("defaultCpny", admin.getCpnyId());
			return new ModelAndView("/edu/traineducation/trainAgreement", modelMap);
		}
		
		//培训协议添加页面
		@RequestMapping(value = "/addTrainAgreement")
		public ModelAndView addTrainAgreement(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			return new ModelAndView("/edu/traineducation/addTrainAgreement",
					modelMap);
		}
		
		//按照姓名模糊查询讲师
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/queryPeixun")
		public ModelAndView queryPeixun(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.queryPeixun(request);
			modelMap.put("EmpOffice",request.getParameter("EmpOffice") != null ? request.getParameter("EmpOffice") : "15119" );
			modelMap.put("empidname",request.getParameter("empidname") );
			modelMap.put("DEPTNO",request.getParameter("DEPTNO") );
			modelMap.put("queryPeixunList", elist);
			modelMap.put("queryPeixunListCount", elist.size());
			return new ModelAndView("/edu/traineducation/queryPeixun",
					modelMap);
		}
		
		// 培训协议添加页面 插入数据
		@RequestMapping(value = "/addTrainAgreementInfo", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addTrainAgreementInfo(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.addTrainAgreementInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));//添加成功
				map.put("navTabId", "edu0105");
			} else {
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));//添加失败
			}
			return map;
		}
		
		// 培训协议修改查询页面
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/trainAgreementInfo")
		public ModelAndView trainAgreementInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainAgreementInfo(request);
			String agree_no=StringUtil.checkNull(request.getParameter("AGREE_NO"));
			//附件下载功能
			if(!"".equals(agree_no) && agree_no != null){
				LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "eduTrainAgreement");
				fileParam.put("APPLY_NO", agree_no);
				List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
				linkMap.put("fileList",fileList);
			}
			modelMap.put("PERSON_ID", admin.getAdminID());
			modelMap.put("trainAgreementInfo", linkMap);
			return new ModelAndView("/edu/traineducation/trainAgreementInfo",
					modelMap);
		}
		
		//培训协议修改数据
		@RequestMapping(value = "/updateTrainAgree", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateTrainAgree(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.updateTrainAgree(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));//修改成功
				map.put("navTabId", "edu0105");
			} else {
				map.put("statusCode", "300");
				map.put("message",
						TipMessage.getTipMessage("alert.message.update_fail", request));//修改失败
			}
			return map;
		}		
		
		
		// 培训协议 删除数据
		@RequestMapping(value = "/deleteTrainAgreement")
		@ResponseBody
		public Map<String, Object> deleteTrainAgreement(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.deleteTrainAgreement(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));// 删除成功
				map.put("formId", "trainOrgan");
			} else {
				map.put("statusCode", "300");
			    map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));// 删除失败
			}
			return map;
		}
		//协议管理导入模板下载
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/trainAgreeImportDemoLoad")
		public void trainAgreeImportDemoLoad(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			List aliasNameList = new ArrayList();
			List list = new ArrayList();
			List mapList = new ArrayList();
			List mapNameList = new ArrayList();
			String flag=StringUtil.checkNull(request.getParameter("flag"));
			String name = eduTrainser.getTrainAgreeList(request, aliasNameList, list , mapList, mapNameList,flag);
			LinkedHashMap sqlContentmap = this.excelUtilSer
					.putIntoSqlContentMap(list);
			this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
		}
		//培训结果模板导入模板下载
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/trainResultImportDemoLoad")
		public void trainResultImportDemoLoad(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
			List aliasNameList = new ArrayList();
			List list = new ArrayList();
			List mapList = new ArrayList();
			List mapNameList = new ArrayList();
			String flag=StringUtil.checkNull(request.getParameter("flag"));
			String name = eduTrainser.gettrainResultImportDemoLoad(request, aliasNameList, list , mapList, mapNameList,flag);
			LinkedHashMap sqlContentmap = this.excelUtilSer
			.putIntoSqlContentMap(list);
			this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
		}
		//讲师评价模板导入模板下载
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/teacherEvaluateInfoImportDemoLoad")
		public void teacherEvaluateInfoImportDemoLoad(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
			List aliasNameList = new ArrayList();
			List list = new ArrayList();
			List mapList = new ArrayList();
			List mapNameList = new ArrayList();
			String flag=StringUtil.checkNull(request.getParameter("flag"));
			String name = eduTrainser.getTeacherEvaluateInfo(request, aliasNameList, list , mapList, mapNameList,flag);
			LinkedHashMap sqlContentmap = this.excelUtilSer
			.putIntoSqlContentMap(list);
			this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
		}
		
		// *******************************************培训信息*********************************************
		// =========================================基本信息===============================================
		
		//查询基本信息
		@SuppressWarnings({"rawtypes" })
		@RequestMapping(value = "/trainBasicInformation")
		public ModelAndView trainBasicInformation(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List elist = eduTrainser.trainBasicInformation(request);
			modelMap.put("trainBasicInformation", elist);
			modelMap.put("trainBasicInformationListCount", elist.size());
			modelMap.put("defaultCpny", admin.getCpnyId());
			modelMap.put("coursename", request.getParameter("coursename"));
			modelMap.put("startdate", request.getParameter("startdate"));
			modelMap.put("enddate", request.getParameter("enddate"));
			return new ModelAndView("/edu/traineducation/trainBasicInformation", modelMap);
		}
		
		//基本信息添加页面
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/addTrainBasicInformation")
		public ModelAndView addTrainBasicInformation(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List elist = eduTrainser.planManager_basic(request);
			modelMap.put("planManagerList", elist);
			modelMap.put("defaultCpny", admin.getCpnyId());
			return new ModelAndView("/edu/traineducation/addTrainBasicInformation",
					modelMap);
		}
		//查询没有上传照片的员工
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/photoMissing")
		public ModelAndView photoMissing(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.photoMissing(request);
			modelMap.put("photoMissingList", elist);
			modelMap.put("photoMissingListCount", elist.size());
			return new ModelAndView("/edu/traineducation/photoMissing", modelMap);
		}
		
		//查询计划管理
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/queryAllPlan")
		@ResponseBody
		public Map queryAllPlan(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			Map<String, Object> map = new HashMap<String, Object>();
			LinkedHashMap qmap=(LinkedHashMap) eduTrainser.planManagerInfo(request);
			map.put("PLAN_NO", qmap.get("PLAN_NO"));
			map.put("train_type_code", qmap.get("TRAIN_TYPE_CODE"));
			map.put("course_name_code", qmap.get("COURSE_NAME_CODE"));
			map.put("train_form_code", qmap.get("TRAIN_FORM_CODE"));
			map.put("train_form_code_name", qmap.get("TRAIN_FORM_CODE_NAME"));
			map.put("train_address", qmap.get("TRAIN_ADDRESS"));
			map.put("plan_startdate", qmap.get("PLAN_STARTDATE"));
			map.put("plan_enddate", qmap.get("PLAN_ENDDATE"));
			map.put("class_hour", qmap.get("CLASS_HOUR"));
			map.put("class_unit", qmap.get("CLASS_UNIT"));
			map.put("period_time", qmap.get("PERIOD_TIME"));
			String desEmployee=eduTrainser.queryDesEmployee(request);
			String desEmployeeName=eduTrainser.queryDesEmployeeName(request);
			String teacherEmpid=eduTrainser.queryTeacherName(request);
			String teacherName=eduTrainser.queryTeacherNameEmpid(request);
			map.put("teacher_name", teacherName);
			map.put("teacher_name_empid", teacherEmpid);
			//如果没有指定人员,只是指定部门,就查出这个部门所有人员
			if(desEmployee==null||"".equals(desEmployee)){
				String deptno=(String)qmap.get("DES_DEPARTMENT");
				if(!"".equals(deptno)&&deptno!=null){
					String ss="";
					String []array=deptno.split(",");
					for(int y=0;y<array.length;y++){
						ss=ss+"'"+array[y]+"',";
					}
					ss=ss.substring(0,ss.length()-1);
					modelMap.put("DEPTNO", ss);
					modelMap.put("CPNY_ID", admin.getCpnyId());
					List elist = eduTrainDao.desEmployee(modelMap);
					if(elist!=null&&elist.size()>0){
						String strid="";
						String strname="";
						for(int i=0;i<elist.size();i++){
							LinkedHashMap emap=(LinkedHashMap) elist.get(i);
							strid=strid+(String)emap.get("EMPID")+",";
							strname=strname+(String)emap.get("LOCAL_NAME")+",";
						}
						desEmployee=strid.substring(0,strid.length()-1);
						desEmployeeName=strname.substring(0,strname.length()-1);
					}
				}
				
			}
			map.put("des_employee", desEmployee);
			map.put("des_employee_name", desEmployeeName);
			return map; 
		}
		//查询一般讲师
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/commonTeacher")
		public ModelAndView commonTeacher(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.commonTeacher(request);
			modelMap.put("commonTeacherList", elist);
			modelMap.put("commonTeacherListCount", elist.size());
			modelMap.put("alempid", request.getParameter("alempid"));
			return new ModelAndView("/edu/traineducation/commonTeacher", modelMap);
		}
		//查询计划指定人员
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/planEmployee")
		public ModelAndView planEmployee(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.planEmployee(request);
			modelMap.put("planEmployeeList", elist);
			modelMap.put("planEmployeeListCount", elist.size());
			modelMap.put("checkplanempid", request.getParameter("checkplanempid"));
			modelMap.put("checkotherplanempid", request.getParameter("checkotherplanempid"));
			return new ModelAndView("/edu/traineducation/planEmployee", modelMap);
		}
		//查询实际自选人员
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/otherPlanEmployee")
		public ModelAndView otherPlanEmployee(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.otherPlanEmployee(request);
			modelMap.put("otherPlanEmployeeList", elist);
			modelMap.put("otherPlanEmployeeListCount", elist.size());
			modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
			modelMap.put("fenlei", request.getParameter("fenlei"));
			modelMap.put("checkotherplanempid", request.getParameter("checkotherplanempid"));
			modelMap.put("checkplanempid", request.getParameter("checkplanempid"));
			return new ModelAndView("/edu/traineducation/otherPlanEmployee", modelMap);
		}
		
		//查询最后人员
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/finalstudent")
		public ModelAndView finalstudent(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.finalstudent(request);
			modelMap.put("finalstudentList", elist);
			modelMap.put("finalstudentListCount", elist.size());
			modelMap.put("finalempid1", request.getParameter("finalempid1"));
			modelMap.put("finalname1", request.getParameter("finalname1"));
			modelMap.put("flag", request.getParameter("flag"));
			modelMap.put("finalempid2", request.getParameter("finalempid2"));
			return new ModelAndView("/edu/traineducation/finalstudent", modelMap);
		}
		
		
		//基本信息添加页面 插入数据
		@RequestMapping(value = "/addTrainBasicInformationInfo", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addTrainBasicInformationInfo(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.addTrainBasicInformationInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));//添加成功
				map.put("navTabId", "edu0201");
			} else {
				    map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));//添加失败
			}
			return map;
		}
		//基本信息修改查询页面
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/trainBasicInformationInfo")
		public ModelAndView trainBasicInformationInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
			//查询clob
			String cteacherempid="COM_TEACHER_EMPID";
			String commonTeacherempid=eduTrainser.queryClob(request,cteacherempid);
			String cteachername="COM_TEACHER_NAME";
			String commonTeachername=eduTrainser.queryClob(request,cteachername);
			String evaempid="EVA_TEACHER_EMPID";
			String evateacherempid=eduTrainser.queryClob(request,evaempid);
			String evaname="EVA_TEACHER_NAME";
			String evateachername=eduTrainser.queryClob(request,evaname);
			String pmpid="PLAN_EMPLOYEE_EMPID";
			String planempid=eduTrainser.queryClob(request,pmpid);
			String pname="PLAN_EMPLOYEE_NAME";
			String planname=eduTrainser.queryClob(request,pname);
			
			linkMap.put("commonTeacherempid", commonTeacherempid);
			linkMap.put("commonTeachername", commonTeachername);
			linkMap.put("evateacherempid", evateacherempid);
			linkMap.put("evateachername", evateachername);
			linkMap.put("planempid", planempid);
			linkMap.put("planname", planname);
			
			//查询实际自选人员
			List elist = eduTrainser.queryFreeEmployee(request);
			if(elist!=null&&elist.size()>0){
				String actempid="";
				String actname="";
				String freeempid="";
				String freename="";
				String applyempid="";
				String applyname="";
				for(int i=0;i<elist.size();i++){
					LinkedHashMap emap = (LinkedHashMap) elist.get(i);
					String flag=(String) emap.get("FLAG");
					if("1".equals(flag)){
						actempid=actempid+(String)emap.get("EMPID")+",";
						actname=actname+(String)emap.get("LOCAL_NAME")+",";
					}else if("2".equals(flag)){
						freeempid=freeempid+(String)emap.get("EMPID")+",";
						freename=freename+(String)emap.get("LOCAL_NAME")+",";
					}else if("3".equals(flag)){
						applyempid=applyempid+(String)emap.get("EMPID")+",";
						applyname=applyname+(String)emap.get("LOCAL_NAME")+",";
					}
					
					
				}
				if(!"".equals(actempid.replace(",", ""))){
					actempid=actempid.substring(0,actempid.length()-1);
					actname=actname.substring(0,actname.length()-1);
				}
				
				if(!"".equals(freeempid.replace(",", ""))){
					freeempid=freeempid.substring(0,freeempid.length()-1);
					freename=freename.substring(0,freename.length()-1);
				}
				if(!"".equals(applyempid.replace(",", ""))){
					applyempid=applyempid.substring(0,applyempid.length()-1);
					applyname=applyname.substring(0,applyname.length()-1);
				}
				modelMap.put("actempid", actempid);
				modelMap.put("actname", actname);
				modelMap.put("freeempid", freeempid);
				modelMap.put("freename", freename);
				modelMap.put("applyempid", applyempid);
				modelMap.put("applyname", applyname);
			}
			
			//查询最后实际人员
			String finalempid="";
			String finalname="";
			List flist = eduTrainser.queryfinalstudent(request);
			if(flist!=null&&flist.size()>0){
				for(int i=0;i<flist.size();i++){
					LinkedHashMap fmap = (LinkedHashMap) flist.get(i);
					finalempid=finalempid+(String)fmap.get("FINAL_STUDENT_EMPID")+",";
					finalname=finalname+(String)fmap.get("FINAL_STUDENT_NAME")+",";
				}
			}
			if(!"".equals(finalempid.replace(",", ""))){
				finalempid=finalempid.substring(0,finalempid.length()-1);
				finalname=finalname.substring(0,finalname.length()-1);
			}
			modelMap.put("finalempid", finalempid);
			modelMap.put("finalname", finalname);
			
			modelMap.put("PERSON_ID", admin.getAdminID());
			modelMap.put("trainBasicInformationInfo", linkMap);
			return new ModelAndView("/edu/traineducation/trainBasicInformationInfo",
					modelMap);
		}
		
		
		//培训课程信息查看
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/queryBasicInformation")
		public ModelAndView queryBasicInformation(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
				
			modelMap.put("PERSON_ID", admin.getAdminID());
			modelMap.put("trainBasicInformationInfo", linkMap);
			
			return new ModelAndView("/edu/traineducation/queryBasicInformation",
					modelMap);
		}
		
		//基本信息修改数据
		@RequestMapping(value = "/updateTrainBasicInformationInfo", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateTrainBasicInformationInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.updateTrainBasicInformationInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));//修改成功
				map.put("navTabId", "edu0201");
			} else {
				map.put("statusCode", "300");
				map.put("message",
						TipMessage.getTipMessage("alert.message.update_fail", request));//修改失败
			}
			return map;
		}
		
		
		//基本信息删除数据
		@RequestMapping(value = "/deleteTrainBasicInformation")
		@ResponseBody
		public Map<String, Object> deleteTrainBasicInformation(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.deleteTrainBasicInformation(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));// 删除成功
				map.put("formId", "trainBasicInformation");
			} else {
				map.put("statusCode", "300");
			    map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));// 删除失败
			}
			return map;
		}
		
		//计划管理是否需要考评(学员评价:1,讲师评价:2,培训评价:3)
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public List isKaoping(HttpServletRequest request,List list,String type){
			List elist=list;
			List rlist=new ArrayList();
			if(elist!=null && elist.size()>0){
				for(int i=0;i<elist.size();i++){
					LinkedHashMap linkMap=(LinkedHashMap) elist.get(i);
					String basicno=String.valueOf(linkMap.get("BASIC_NO"));
					String isnotEva=eduTrainser.queryIsnotEva(request,basicno);
					if(isnotEva.indexOf(type)>-1){
						rlist.add(linkMap);
					}
				}
			}
			
			return rlist;
		}
		// =========================================学员考评===============================================
		
		//查询基本信息
		@SuppressWarnings({"rawtypes", "unchecked" })
		@RequestMapping(value = "/studentEvaluate")
		public ModelAndView studentEvaluate(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String empid=admin.getEmpID();
			List elist = eduTrainser.trainBasicInformation(request);
			elist=isKaoping(request,elist,"1");
			if(empid.equals("11111111")||empid.equals("11111112")||empid.equals("30100104")||empid.equals("40110008")){
			}else{
				List relist = new ArrayList();
				if(elist!=null&&elist.size()>0){
					for(int i=0;i<elist.size();i++){
						LinkedHashMap linkMap =(LinkedHashMap) elist.get(i);
						String basicno=String.valueOf(linkMap.get("BASIC_NO"));
						String content="EVA_TEACHER_EMPID";
						String table="edu_basic_information";
						String term="basic_no='"+basicno+"'";
						String teaempid=eduTrainser.queryClobSecond(request,content,table,term);
					     if(teaempid.indexOf(empid)>-1){
					    	 relist.add(linkMap);
					     }
					}
					elist=relist;
				}
			}
			//查询是否进行中,进行中,课程变蓝
			String strbasicno="";
			String teapersonid=admin.getAdminID();
			List blist=eduTrainser.queryBasicNo(request,teapersonid);
			if(blist!=null&&blist.size()>0){
				
				for(int i=0;i<blist.size();i++){
					LinkedHashMap bmap=(LinkedHashMap) blist.get(i);
					strbasicno=strbasicno+bmap.get("BASIC_NO")+",";
				}
				strbasicno=strbasicno.substring(0,strbasicno.length()-1);
			}
			
			modelMap.put("studentEvaluateList", elist);
			modelMap.put("studentEvaluateListCount", elist.size());
			modelMap.put("defaultCpny", admin.getCpnyId());
			modelMap.put("startdate", request.getParameter("startdate"));
			modelMap.put("enddate", request.getParameter("enddate"));
			modelMap.put("strbasicno", strbasicno);
			return new ModelAndView("/edu/traineducation/studentEvaluate", modelMap);
		}
		
		//学员考评页面
		@SuppressWarnings({ "rawtypes" })
		@RequestMapping(value = "/studentEvaluateInfo")
		public ModelAndView studentEvaluateInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
			//查询所有学员
			List elist = eduTrainser.queryEvaAllemployee(request);
			//算出所有学生的评价结果
			//判断两个培训担当,如果是评价讲师,就看见考评,不是就看不见考评
			/*String flag="1";
			String teaempid=admin.getEmpID();
			if("30100104".equals(teaempid)||"40110008".equals(teaempid)){
				String basicno=String.valueOf(linkMap.get("BASIC_NO"));
				String content="EVA_TEACHER_EMPID";
				String table="edu_basic_information";
				String term="basic_no='"+basicno+"'";
				String allteaempid=eduTrainser.queryClobSecond(request,content,table,term);
			     if(allteaempid.indexOf(teaempid)>-1){
			    	 flag="1";
			     }else{
			    	 flag="0";
			     }
			}*/
			modelMap.put("studentEvaluateList", elist);
			modelMap.put("studentEvaluateListCount", elist.size());
			modelMap.put("PERSON_ID", admin.getAdminID());
			modelMap.put("ADMINEMPID", admin.getEmpID());
			modelMap.put("studentEvaluateInfo", linkMap);
			/*modelMap.put("flag", flag);
			modelMap.put("ALLTEACHEREMPID", teaempid);*/
			return new ModelAndView("/edu/traineducation/studentEvaluateInfo",
					modelMap);
		}
		//查询学员评价页面
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/studentKaoping")
		public ModelAndView studentKaoping(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			//查询老师和学生是否唯一
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.studentKaopingInfo(request);
			if(linkMap!=null){
				modelMap.put("checkno",linkMap.get("CHECK_NO"));
				modelMap.put("curTeacherPersonid", (String)linkMap.get("TEA_PERSON_ID"));
				modelMap.put("curTeacherName", (String)linkMap.get("TEA_LOCAL_NAME"));
				modelMap.put("curStudentEmpid", (String)linkMap.get("STU_EMPID"));
				modelMap.put("curStudentName", (String)linkMap.get("STU_LOCAL_NAME"));
			}else{
				modelMap.put("curTeacherPersonid", admin.getAdminID());
				modelMap.put("curTeacherName", admin.getLocalName());
				modelMap.put("curStudentEmpid", request.getParameter("studentempid"));
				modelMap.put("curStudentName", request.getParameter("studentname"));
			}
			modelMap.put("studentKaopingInfo", linkMap);
			modelMap.put("BASIC_NO", request.getParameter("BASIC_NO"));
			return new ModelAndView("/edu/traineducation/studentKaoping", modelMap);
		}
		
		//学员评价插入数据
		@RequestMapping(value = "/addStudentKaoping", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addStudentKaoping(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.addStudentKaoping(request);
			if (result == 1) {
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));//添加成功
			}else if(result == 2){
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));//修改成功
			}else{
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));//添加失败
			}
			return map;
		}
		
		//查询学生评价基本信息
		@SuppressWarnings({"rawtypes" })
		@RequestMapping(value = "/studentChakan")
		public ModelAndView studentChakan(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.studentChakan(request);
			modelMap.put("studentChakanList", elist);
			modelMap.put("studentChakanListCount", elist.size());
			modelMap.put("curStudentEmpid", request.getParameter("studentempid"));
			modelMap.put("curStudentName", request.getParameter("studentname"));
			return new ModelAndView("/edu/traineducation/studentChakan", modelMap);
		}
		
		//学生考试成绩修改数据
		@RequestMapping(value = "/updateStudentEvaluateInfo", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateStudentEvaluateInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.updateStudentEvaluateInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));//修改成功
				map.put("navTabId", "edu0202");
			} else {
				map.put("statusCode", "300");
				map.put("message",
						TipMessage.getTipMessage("alert.message.update_fail", request));//修改失败
			}
			return map;
		}
		
		
		//学员考试成绩导入模板下载
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/studentEvaImportDemoLoad")
		public void studentEvaImportDemoLoad(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			List aliasNameList = new ArrayList();
			List list = new ArrayList();
			List mapList = new ArrayList();
			List mapNameList = new ArrayList();
			String flag=StringUtil.checkNull(request.getParameter("flag"));
			String name = eduTrainser.getStudentEvaList(request, aliasNameList, list , mapList, mapNameList,flag);
			LinkedHashMap sqlContentmap = this.excelUtilSer
					.putIntoSqlContentMap(list);
			this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
		}
		
		//培训计划课程表模板下载
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/planImportDemoLoad")
		public void planImportDemoLoad(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			List aliasNameList = new ArrayList();
			List list = new ArrayList();
			List mapList = new ArrayList();
			List mapNameList = new ArrayList();
			String flag=StringUtil.checkNull(request.getParameter("flag"));
			String name = eduTrainser.getPlanCourse(request, aliasNameList, list , mapList, mapNameList,flag);
			LinkedHashMap sqlContentmap = this.excelUtilSer
					.putIntoSqlContentMap(list);
			this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
		}
		
		//查看学员考评页面
		@SuppressWarnings({ "rawtypes" })
		@RequestMapping(value = "/studentEvaluateSingle")
		public ModelAndView studentEvaluateSingle(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
			//查询所有学员
			List elist = eduTrainser.queryEvaAllemployee(request);
			//算出所有学生的评价结果
			/*if(elist!=null&&elist.size()>0){
				for(int i=0;i<elist.size();i++){
					LinkedHashMap eMap=(LinkedHashMap) elist.get(i);
					LinkedHashMap paramMap=new LinkedHashMap();
					paramMap.put("studentempid", eMap.get("EMPID"));
					paramMap.put("BASIC_NO", eMap.get("BASIC_NO"));
					int testscore=0;
					if(eMap.get("TEST_SCORE")==null||"".equals(eMap.get("TEST_SCORE"))){
						testscore=0;
					}else{
						testscore=Integer.parseInt((String)eMap.get("TEST_SCORE"));
					}
					List slist = eduTrainser.studentChakanSecond(request,paramMap);
					int allTotalscore=0;
					int finaResult=0;
					if(slist!=null&&slist.size()>0){
						int count=slist.size();
						for(int y=0;y<slist.size();y++){
							LinkedHashMap scoreMap=(LinkedHashMap) slist.get(y);
							int allscore=0;
							if(scoreMap.get("ALLSCORE")==null||"".equals(scoreMap.get("ALLSCORE"))){
								allscore=0;
							}else{
								allscore=Integer.parseInt((String)scoreMap.get("ALLSCORE"));
							}
							allTotalscore=allTotalscore+allscore;
						}
						finaResult=(int) (testscore*0.6+allTotalscore*2/(count*5));
					}else{
						finaResult=(int) (testscore*0.6);
					}
					if(finaResult>0){
						eMap.put("finaResult", finaResult);
					}
				}
			}*/
			
			//判断两个培训担当,如果是评价讲师,就看见考评,不是就看不见考评
			String flag="1";
			String teaempid=admin.getEmpID();
			if("30100104".equals(teaempid)||"40110008".equals(teaempid)){
				String basicno=String.valueOf(linkMap.get("BASIC_NO"));
				String content="EVA_TEACHER_EMPID";
				String table="edu_basic_information";
				String term="basic_no='"+basicno+"'";
				String allteaempid=eduTrainser.queryClobSecond(request,content,table,term) != null ? eduTrainser.queryClobSecond(request,content,table,term) :"";
			     if(allteaempid.indexOf(teaempid)>-1){
			    	 flag="1";
			     }else{
			    	 flag="0";
			     }
			}
			modelMap.put("studentEvaluateList", elist);
			modelMap.put("studentEvaluateListCount", elist.size());
			modelMap.put("PERSON_ID", admin.getAdminID());
			modelMap.put("ADMINEMPID", admin.getEmpID());
			modelMap.put("studentEvaluateInfo", linkMap);
			modelMap.put("flag", flag);
			modelMap.put("ALLTEACHEREMPID", teaempid);
			return new ModelAndView("/edu/traineducation/studentEvaluateSingle",
					modelMap);
		}
		
		// =========================================讲师评价===============================================
		
		//查询讲师评价基本信息
		@SuppressWarnings({"rawtypes", "unchecked" })
		@RequestMapping(value = "/teacherEvaluate")
		public ModelAndView teacherEvaluate(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			//学生登陆者的empid
			String empid=admin.getEmpID();
			List elist = eduTrainser.trainBasicInformation(request);
			elist=isKaoping(request,elist,"2");
			if(empid.equals("11111111")||empid.equals("11111112")||empid.equals("30100104")||empid.equals("40110008")){
			}else{
				List relist = new ArrayList();
				if(elist!=null&&elist.size()>0){
					for(int i=0;i<elist.size();i++){
						LinkedHashMap linkMap =(LinkedHashMap) elist.get(i);
						String basicno=String.valueOf(linkMap.get("BASIC_NO"));
						//与所有培训学生匹配
						String content="count(1)";
						String table="edu_free_employee";
						String term="empid='"+empid+"' and basic_no='"+basicno+"' and activity=1 and cpny_id='"+admin.getCpnyId()+"'";
						String count=eduTrainser.queryClobSecond(request,content,table,term);
					     if(Integer.parseInt(count)>0){
					    	 relist.add(linkMap);
					     }
					}
					elist=relist;
				}
			}
			//查询有学生是否进行评价中,如果进行中,课程变蓝
			String strbasicno="";
			String strempid=admin.getEmpID();
			List blist=eduTrainser.queryStrBasicNo(request,strempid);
			if(blist!=null&&blist.size()>0){
				for(int i=0;i<blist.size();i++){
					LinkedHashMap bmap=(LinkedHashMap) blist.get(i);
					strbasicno=strbasicno+bmap.get("BASIC_NO")+",";
				}
				strbasicno=strbasicno.substring(0,strbasicno.length()-1);
			}
			
			modelMap.put("teacherEvaluateList", elist);
			modelMap.put("teacherEvaluateListCount", elist.size());
			modelMap.put("defaultCpny", admin.getCpnyId());
			modelMap.put("startdate", request.getParameter("startdate"));
			modelMap.put("enddate", request.getParameter("enddate"));
			modelMap.put("teabasicno", strbasicno);
			return new ModelAndView("/edu/traineducation/teacherEvaluate", modelMap);
		}
		
		
		//讲师评价页面
		@SuppressWarnings({ "rawtypes" })
		@RequestMapping(value = "/teacherEvaluateInfo")
		public ModelAndView teacherEvaluateInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
			//查询所有待评价的老师
			List elist = eduTrainser.queryEvaAllTeacher(request);
			//判断两个培训担当,如果是培训学生,就看见考评,不是就看不见考评
			String flag="1";
			String stuempid=admin.getEmpID();
			if("30100104".equals(stuempid)||"40110008".equals(stuempid)){
				String basicno=String.valueOf(linkMap.get("BASIC_NO"));
				String content="count(1)";
				String table="edu_free_employee";
				String term="empid='"+stuempid+"' and basic_no='"+basicno+"' and activity=1 and cpny_id='"+admin.getCpnyId()+"'";
				String count=eduTrainser.queryClobSecond(request,content,table,term);
				if(Integer.parseInt(count)>0){
					flag="1";
			     }else{
			    	 flag="0";
			     }
			}
			modelMap.put("teacherEvaluateList", elist);
			modelMap.put("teacherEvaluateListCount", elist.size());
			modelMap.put("PERSON_ID", admin.getAdminID());
			modelMap.put("ADMINEMPID", admin.getEmpID());
			modelMap.put("teacherEvaluateInfo", linkMap);
			modelMap.put("flag", flag);
			modelMap.put("ALLTEACHEREMPID", stuempid);
			return new ModelAndView("/edu/traineducation/teacherEvaluateInfo",
					modelMap);
		}
		//讲师评价页面
		@SuppressWarnings({ "rawtypes" })
		@RequestMapping(value = "/teacherEvaluateTSTOInfo")
		public ModelAndView teacherEvaluateTSTOInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
			//查询所有待评价的老师
			List elist = eduTrainser.queryEvaAllTSTOTeacher(request);
			//判断两个培训担当,如果是培训学生,就看见考评,不是就看不见考评
			String flag="1";
			String stuempid=admin.getEmpID();
			if("30100104".equals(stuempid)||"40110008".equals(stuempid)){
				String basicno=String.valueOf(linkMap.get("BASIC_NO"));
				String content="count(1)";
				String table="edu_free_employee";
				String term="empid='"+stuempid+"' and basic_no='"+basicno+"' and activity=1 and cpny_id='"+admin.getCpnyId()+"'";
				String count=eduTrainser.queryClobSecond(request,content,table,term);
				if(Integer.parseInt(count)>0){
					flag="1";
				}else{
					flag="0";
				}
			}
			modelMap.put("teacherEvaluateList", elist);
			modelMap.put("teacherEvaluateListCount", elist.size());
			modelMap.put("PERSON_ID", admin.getAdminID());
			modelMap.put("ADMINEMPID", admin.getEmpID());
			modelMap.put("teacherEvaluateInfo", linkMap);
			modelMap.put("flag", flag);
			modelMap.put("ALLTEACHEREMPID", stuempid);
			modelMap.put("ALLTEACHEREMPID", stuempid);
			return new ModelAndView("/edu/traineducation/teacherEvaluateTSTOInfo",
					modelMap);
		}
		
		//查询讲师评价页面
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/teacherKaoping")
		public ModelAndView teacherKaoping(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.teacherKaopingInfo(request);
			modelMap.put("curStudentEmpid", admin.getEmpID());
			modelMap.put("curStudentName", admin.getLocalName());
			modelMap.put("teacherKaopingInfo", linkMap);
			modelMap.put("BASIC_NO", request.getParameter("BASIC_NO"));
			return new ModelAndView("/edu/traineducation/teacherKaoping", modelMap);
		}
		
		
		//讲师评价插入数据
		@RequestMapping(value = "/updateTeacherKaoping", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateTeacherKaoping(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.updateTeacherKaoping(request);
			if(result == 1){
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));//修改成功
			}else{
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));//修改失败
			}
			return map;
		}
		
		
		
		//查询老师评价基本信息
		@SuppressWarnings({"rawtypes" })
		@RequestMapping(value = "/teacherChakan")
		public ModelAndView teacherChakan(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.teacherChakan(request);
			modelMap.put("teacherChakanList", elist);
			modelMap.put("teacherChakanListCount", elist.size());
			modelMap.put("curteacherempid", request.getParameter("teacherempid"));
			modelMap.put("curteachername", request.getParameter("teachername"));
			return new ModelAndView("/edu/traineducation/teacherChakan", modelMap);
		}
		//查询老师评价基本信息
		@SuppressWarnings({"rawtypes" })
		@RequestMapping(value = "/teacherTSTOChakan")
		public ModelAndView teacherTSTOChakan(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.teacherChakan(request);
			modelMap.put("teacherChakanList", elist);
			modelMap.put("teacherChakanListCount", elist.size());
			modelMap.put("curteacherempid", request.getParameter("teacherempid"));
			modelMap.put("curteachername", request.getParameter("teachername"));
			return new ModelAndView("/edu/traineducation/teacherTSTOChakan", modelMap);
		}
		
		
		
		//查看讲师评价页面
		@SuppressWarnings({ "rawtypes" })
		@RequestMapping(value = "/teacherEvaluateSingle")
		public ModelAndView teacherEvaluateSingle(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
			//查询所有待评价的老师
			List elist = eduTrainser.queryEvaAllTeacher(request);
			//判断两个培训担当,如果是培训学生,就看见考评,不是就看不见考评
			String flag="1";
			String stuempid=admin.getEmpID();
			if("30100104".equals(stuempid)||"40110008".equals(stuempid)){
				String basicno=String.valueOf(linkMap.get("BASIC_NO"));
				String content="count(1)";
				String table="edu_free_employee";
				String term="empid='"+stuempid+"' and basic_no='"+basicno+"' and activity=1 and cpny_id='"+admin.getCpnyId()+"'";
				String count=eduTrainser.queryClobSecond(request,content,table,term);
				if(Integer.parseInt(count)>0){
					flag="1";
			     }else{
			    	 flag="0";
			     }
			}
			modelMap.put("teacherEvaluateList", elist);
			modelMap.put("teacherEvaluateListCount", elist.size());
			modelMap.put("PERSON_ID", admin.getAdminID());
			modelMap.put("ADMINEMPID", admin.getEmpID());
			modelMap.put("teacherEvaluateInfo", linkMap);
			modelMap.put("flag", flag);
			modelMap.put("ALLTEACHEREMPID", stuempid);
			return new ModelAndView("/edu/traineducation/teacherEvaluateSingle",
					modelMap);
		}
		//查看讲师评价页面
		@SuppressWarnings({ "rawtypes" })
		@RequestMapping(value = "/teacherEvaluateTSTOSingle")
		public ModelAndView teacherEvaluateTSTOSingle(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
			//查询所有待评价的老师
			List elist = eduTrainser.queryEvaAllTSTOTeacher(request);
			//判断两个培训担当,如果是培训学生,就看见考评,不是就看不见考评
			String flag="1";
			String stuempid=admin.getEmpID();
			if("30100104".equals(stuempid)||"40110008".equals(stuempid)){
				String basicno=String.valueOf(linkMap.get("BASIC_NO"));
				String content="count(1)";
				String table="edu_free_employee";
				String term="empid='"+stuempid+"' and basic_no='"+basicno+"' and activity=1 and cpny_id='"+admin.getCpnyId()+"'";
				String count=eduTrainser.queryClobSecond(request,content,table,term);
				if(Integer.parseInt(count)>0){
					flag="1";
				}else{
					flag="0";
				}
			}
			modelMap.put("teacherEvaluateList", elist);
			modelMap.put("teacherEvaluateListCount", elist.size());
			modelMap.put("PERSON_ID", admin.getAdminID());
			modelMap.put("ADMINEMPID", admin.getEmpID());
			modelMap.put("teacherEvaluateInfo", linkMap);
			modelMap.put("flag", flag);
			modelMap.put("ALLTEACHEREMPID", stuempid);
			return new ModelAndView("/edu/traineducation/teacherEvaluateTSTOSingle",
					modelMap);
		}
		
		// =========================================培训结果===============================================
		
		//查询培训结果基本信息
		@SuppressWarnings({"rawtypes", "unchecked" })
		@RequestMapping(value = "/trainResult")
		public ModelAndView trainResult(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			//学生登陆者的empid
			String empid=admin.getEmpID();
			List elist = eduTrainser.trainBasicInformation(request);
			elist=isKaoping(request,elist,"3");
			if(empid.equals("11111111")||empid.equals("11111112")||empid.equals("30100104")||empid.equals("40110008")){
				//判断两个培训担当,如果是培训学生,就看见考评,不是就看不见考评
				String flag="1";
				if("30100104".equals(empid)||"40110008".equals(empid)){
					if(elist!=null&&elist.size()>0){
						for(int i=0;i<elist.size();i++){
							LinkedHashMap linkMap =(LinkedHashMap) elist.get(i);
							String basicno=String.valueOf(linkMap.get("BASIC_NO"));
							String content="count(1)";
							String table="edu_free_employee";
							String term="basic_no='"+basicno+"' and activity=1 and cpny_id='"+admin.getCpnyId()+"'";
							String count=eduTrainser.queryClobSecond(request,content,table,term);
							if(Integer.parseInt(count)>0){
								flag="1";
						     }else{
						    	 flag="0";
						     }
							linkMap.put("flag", flag);
						}
					}
					
				}
			}else{
				List relist = new ArrayList();
				if(elist!=null&&elist.size()>0){
					for(int i=0;i<elist.size();i++){
						LinkedHashMap linkMap =(LinkedHashMap) elist.get(i);
						String basicno=String.valueOf(linkMap.get("BASIC_NO"));
						//与所有培训学生匹配
						String content="count(1)";
						String table="edu_free_employee";
						String term="empid='"+empid+"' and basic_no='"+basicno+"' and activity=1 and cpny_id='"+admin.getCpnyId()+"'";
						String count=eduTrainser.queryClobSecond(request,content,table,term);
						linkMap.put("flag", "1");
						if(Integer.parseInt(count)>0){
					    	 relist.add(linkMap);
					     }
					}
					elist=relist;
				}
			}
			//查询当前课程是否进行评价中,如果进行中,课程变蓝
			String strbasicno="";
			List blist=eduTrainser.queryResultBasicNo(request);
			if(blist!=null&&blist.size()>0){
				for(int i=0;i<blist.size();i++){
					LinkedHashMap bmap=(LinkedHashMap) blist.get(i);
					strbasicno=strbasicno+bmap.get("BASIC_NO")+",";
				}
				strbasicno=strbasicno.substring(0,strbasicno.length()-1);
			}
			
			
			modelMap.put("trainResultList", elist);
			modelMap.put("trainResultListCount", elist.size());
			modelMap.put("defaultCpny", admin.getCpnyId());
			modelMap.put("startdate", request.getParameter("startdate"));
			modelMap.put("enddate", request.getParameter("enddate"));
			modelMap.put("resubasicno", strbasicno);
			modelMap.put("ALLTEACHEREMPID", empid);
			return new ModelAndView("/edu/traineducation/trainResult", modelMap);
		}
		
		
		
		//培训结果页面
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/trainResultInfo")
		public ModelAndView trainResultInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainResultInfo(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			modelMap.put("trainResultInfo", linkMap);
			modelMap.put("BASIC_NO", request.getParameter("BASIC_NO"));
			modelMap.put("PERSON_ID",admin.getPersonId());
			String resultno=StringUtil.checkNull(request.getParameter("RESULT_NO"));
			//附件下载功能
			if(!"".equals(resultno) && resultno != null){
				LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "eduTrainResult");
				fileParam.put("APPLY_NO", resultno);
				List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
				linkMap.put("fileList",fileList);
			}
			return new ModelAndView("/edu/traineducation/trainResultInfo", modelMap);
		}
		//培训结果页面
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/trainResultTSTOInfo")
		public ModelAndView trainResultTSTOInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List list =  eduTrainser.trainResultInfoEveList(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap  basicLinkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
			modelMap.put("alreadyTrainResultInfo", basicLinkMap);
			modelMap.put("trainResultInfoList", list);
			modelMap.put("BASIC_NO", request.getParameter("BASIC_NO"));
			modelMap.put("PERSON_ID",admin.getPersonId());
			String resultno=StringUtil.checkNull(request.getParameter("BASIC_NO"));
			//附件下载功能
			if(!"".equals(resultno) && resultno != null){
				LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "eduTrainResult");
				fileParam.put("APPLY_NO", resultno);
				List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
				modelMap.put("fileList",fileList);
			}
			return new ModelAndView("/edu/traineducation/trainResultTSTOInfo", modelMap);
		}
		
		
		//讲师评价插入数据
		@RequestMapping(value = "/updateTrainResultInfo", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateTrainResultInfo(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.updateEduTrainResult(request);
			if(result == 1){
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));//修改成功
			}else{
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));//修改失败
			}
			return map;
		}
		
		
		//查询培训结果基本信息
		@SuppressWarnings({"rawtypes", "unchecked" })
		@RequestMapping(value = "/alreadyTrainResultInfo")
		public ModelAndView alreadyTrainResultInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			LinkedHashMap  basicLinkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainResultInfo(request);
			modelMap.put("alreadyTrainResultInfo", basicLinkMap);
			modelMap.put("trainResultInfo", linkMap);
			modelMap.put("BASIC_NO", request.getParameter("BASIC_NO"));
			String resultno=StringUtil.checkNull(request.getParameter("RESULT_NO"));
			//附件下载功能
			if(!"".equals(resultno) && resultno != null){
				LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "eduTrainResult");
				fileParam.put("APPLY_NO", resultno);
				List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
				linkMap.put("fileList",fileList);
			}
			return new ModelAndView("/edu/traineducation/alreadyTrainResultInfo", modelMap);
		}
		
		//培训担当查询培训结果基本信息
		@SuppressWarnings({"rawtypes", "unchecked" })
		@RequestMapping(value = "/alreadyTrainResultInfoSingle")
		public ModelAndView alreadyTrainResultInfoSingle(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			LinkedHashMap  basicLinkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
			List rlist=eduTrainser.alreadyTrainResultList(request);
			modelMap.put("alreadyTrainResultInfo", basicLinkMap);
			modelMap.put("alreadyTrainResultList", rlist);
			modelMap.put("alreadyTrainResultListCount", rlist.size());
			modelMap.put("BASIC_NO", request.getParameter("BASIC_NO"));
			if(rlist!=null&&rlist.size()>0){
				for(int i=0;i<rlist.size();i++){
					LinkedHashMap linkMap=(LinkedHashMap) rlist.get(i);
					String resultno=String.valueOf(linkMap.get("RESULT_NO"));
					//附件下载功能
					if(!"".equals(resultno) && resultno != null){
						LinkedHashMap fileParam = new LinkedHashMap();
						fileParam.put("APPLY_TYPE", "eduTrainResult");
						fileParam.put("APPLY_NO", resultno);
						List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
						linkMap.put("fileList",fileList);
					}
					
				}
			}
			return new ModelAndView("/edu/traineducation/alreadyTrainResultInfoSingle", modelMap);
		}
		//培训担当查询培训结果基本信息
		@SuppressWarnings({"rawtypes", "unchecked" })
		@RequestMapping(value = "/alreadyTrainResultTSTOInfoSingle")
		public ModelAndView alreadyTrainResultTSTOInfoSingle(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			LinkedHashMap  basicLinkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
			List rlist=eduTrainser.alreadyTrainResultTSTOList(request);
			modelMap.put("alreadyTrainResultInfo", basicLinkMap);
			modelMap.put("alreadyTrainResultList", rlist);
			modelMap.put("alreadyTrainResultListCount", rlist.size());
			modelMap.put("BASIC_NO", request.getParameter("BASIC_NO"));
			
					String resultno=String.valueOf(request.getParameter("BASIC_NO"));
					//附件下载功能
					if(!"".equals(resultno) && resultno != null){
						LinkedHashMap fileParam = new LinkedHashMap();
						fileParam.put("APPLY_TYPE", "eduTrainResult");
						fileParam.put("APPLY_NO", resultno);
						List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
						modelMap.put("fileList",fileList);
					}
			return new ModelAndView("/edu/traineducation/alreadyTrainResultTSTOInfoSingle", modelMap);
		}
		//培训结果查看考评的学员
		@SuppressWarnings({"rawtypes", "unchecked" })
		@RequestMapping(value = "/checkTrainResultTSTOInfoPer")
		public ModelAndView checkTrainResultTSTOInfoPer(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			LinkedHashMap  basicLinkMap = (LinkedHashMap) eduTrainser.trainBasicInformationInfo(request);
			List rlist=eduTrainser.checkTrainResultTSTOInfoPer(request);
			modelMap.put("alreadyTrainResultInfo", basicLinkMap);
			modelMap.put("trainResultTSTOInfoPerList", rlist);
			modelMap.put("trainResultTSTOInfoPerListCount", rlist.size());
			modelMap.put("BASIC_NO", request.getParameter("BASIC_NO"));
			return new ModelAndView("/edu/traineducation/checkTrainResultTSTOInfoPer", modelMap);
		}
		
		// =========================================费用管理===============================================
		//查询基本信息页面
		@SuppressWarnings({"rawtypes" })
		@RequestMapping(value = "/trainCostManager")
		public ModelAndView trainCostManager(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List elist = eduTrainser.trainCostManager(request);
			modelMap.put("trainCostManagerList", elist);
			modelMap.put("trainCostManagerListCount", elist.size());
			modelMap.put("coursename", request.getParameter("coursename"));
			modelMap.put("startdate", request.getParameter("startdate"));
			modelMap.put("enddate", request.getParameter("enddate"));
			modelMap.put("CPNY_ID", admin.getCpnyId());
			return new ModelAndView("/edu/traineducation/trainCostManager", modelMap);
		}
		
		
		//费用管理查询修改页面
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/trainCostManagerInfo")
		public ModelAndView trainCostManagerInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap linkMap = (LinkedHashMap) eduTrainser.trainCostManagerInfo(request);
			modelMap.put("trainCostManagerInfo", linkMap);
			String costno=StringUtil.checkNull(request.getParameter("COST_NO"));
			//附件下载功能
			if(!"".equals(costno) && costno != null){
				LinkedHashMap fileParam = new LinkedHashMap();
				fileParam.put("APPLY_TYPE", "eduCostManager");
				fileParam.put("APPLY_NO", costno);
				List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
				linkMap.put("fileList",fileList);
			}
			modelMap.put("CPNY_ID", admin.getCpnyId());
			return new ModelAndView("/edu/traineducation/trainCostManagerInfo",
					modelMap);
		}	
		
		//费用管理修改数据
		@RequestMapping(value = "/updateTrainCostManagerInfo", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateTrainCostManagerInfo(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.updateTrainCostManagerInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));//修改成功
				map.put("navTabId", "edu0205");
			} else {
				map.put("statusCode", "300");
				map.put("message",
						TipMessage.getTipMessage("alert.message.update_fail", request));//修改失败
			}
			return map;
		}
		
		
		//费用管理删除数据
		@RequestMapping(value = "/deleteTrainCostManager")
		@ResponseBody
		public Map<String, Object> deleteTrainCostManager(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.deleteTrainCostManager(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));// 删除成功
				map.put("formId", "trainCostManager");
			} else {
				map.put("statusCode", "300");
			    map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));// 删除失败
			}
			return map;
		}
		
		//查询培训档案页面
		@SuppressWarnings({"rawtypes", "unchecked" })
		@RequestMapping(value = "/trainArchives")
		public ModelAndView trainArchives(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.trainArchives(request);
			if(elist!=null&&elist.size()>0){
				for(int i=0;i<elist.size();i++){
					LinkedHashMap linkMap=(LinkedHashMap) elist.get(i);
					String resultno=String.valueOf(linkMap.get("RESULT_NO"));
					//附件下载功能
					if(!"".equals(resultno) && resultno != null){
						LinkedHashMap fileParam = new LinkedHashMap();
						fileParam.put("APPLY_TYPE", "eduTrainResult");
						fileParam.put("APPLY_NO", resultno);
						List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
						linkMap.put("fileList",fileList);
					}
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			if(request.getParameter("actStartdate")==""||request.getParameter("actStartdate")==null){
				//获取当前月第一天：
				Calendar c = Calendar.getInstance();    
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("actStartdate",first);
			}else{
				modelMap.put("actStartdate",request.getParameter("actStartdate"));
			}
		    if(request.getParameter("actEnddate")==""||request.getParameter("actEnddate")==null){
		    	Calendar c = Calendar.getInstance();
		    	c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
		    	modelMap.put("actEnddate",last);
			}else{
				modelMap.put("actEnddate",request.getParameter("actEnddate"));
			}
			modelMap.put("trainArchivesList", elist);
			modelMap.put("trainArchivesListCount", elist.size());
			modelMap.put("arcEmpidName", request.getParameter("arcEmpidName"));
			modelMap.put("arcDepartno", request.getParameter("arcDepartno"));
			modelMap.put("arcCourseName", request.getParameter("arcCourseName"));
			//modelMap.put("actStartdate", request.getParameter("actStartdate"));
			//modelMap.put("actEnddate", request.getParameter("actEnddate"));
			modelMap.put("arcTrainContent", request.getParameter("arcTrainContent"));
			modelMap.put("arcDepartnoId", request.getParameter("arcDepartnoId"));
			return new ModelAndView("/edu/traineducation/trainArchives", modelMap);
		}
		
		//============================================课程申请==============================================
		//课程申请查询页面
		@SuppressWarnings({"rawtypes", "unchecked" })
		@RequestMapping(value = "/courseApply")
		public ModelAndView courseApply(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String adminEmpid=admin.getEmpID();
			String adminPersonid=admin.getPersonId();
			//查询那些学生学习那些课程
			List elist = eduTrainser.courseApply(request);
			if(elist!=null&&elist.size()>0){
				List relist=new ArrayList();
				for(int i=0;i<elist.size();i++){
					LinkedHashMap linkmap=(LinkedHashMap) elist.get(i);
					String planno=String.valueOf(linkmap.get("PLAN_NO"));
					String basicno=String.valueOf(linkmap.get("BASIC_NO"));
					String content="TO_CHAR(DES_EMPLOYEE)";
					String table="edu_plan_manager";
					String term="PLAN_NO='"+planno+"' and activity<>0";
					String stuempid=eduTrainser.queryClobSecond(request,content,table,term) != null ? eduTrainser.queryClobSecond(request,content,table,term) : "" ;
					String [] arraystuempid=stuempid.split(",");
					String countnum=eduTrainser.queryCountnum(request,basicno,adminPersonid);
					String alreadycountnum=eduTrainser.alreadycountnum(request,basicno);
					//如果指定人员有这些人的名字或者这些人没有申请过的,可以申请课程
				     if(stuempid.indexOf(adminEmpid)>-1&&!"1".equals(countnum)){
				    	 linkmap.put("alreadycountnum", alreadycountnum+"/"+String.valueOf(arraystuempid.length));
				    	 relist.add(linkmap);
				     }
				}
				elist=relist;
			}
			//查询登录者的默认决裁者,也就是part长
			List mlist = eduTrainser.queryDefaultMaker(request);
			
			modelMap.put("courseApplyList", elist);
			modelMap.put("courseApplyListCount", elist.size());
			modelMap.put("makerList", mlist);
			modelMap.put("makerListCount", mlist.size());
			modelMap.put("applyname", request.getParameter("applyname"));
			modelMap.put("applystartdate", request.getParameter("applystartdate"));
			modelMap.put("appleenddate", request.getParameter("appleenddate"));
			modelMap.put("adminEmpid", adminEmpid);
			modelMap.put("adminLocalName", admin.getLocalName());
			return new ModelAndView("/edu/traineducation/courseApply", modelMap);
		}
		
		//查询决裁者
		@SuppressWarnings({"rawtypes" })
		@RequestMapping(value = "/queryMaker")
		public ModelAndView queryMaker(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List elist = eduTrainser.queryPeixun(request);
			modelMap.put("EmpOffice",request.getParameter("EmpOffice") != null ? request.getParameter("EmpOffice") : "15119" );
			modelMap.put("empidname",request.getParameter("empidname") );
			modelMap.put("DEPTNO",request.getParameter("DEPTNO") );
			modelMap.put("queryMakerList", elist);
			modelMap.put("queryMakerListCount", elist.size());
			return new ModelAndView("/edu/traineducation/queryMaker", modelMap);
		}
		
		//基本信息添加页面 插入数据
	    @RequestMapping(value = "/addCourseApply", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addCourseApply(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.addCourseApply(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));//添加成功
				map.put("formId", "courseApply");
			} else {
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));//添加失败
			}
			return map;
		}
		//============================================课程决裁=========================================
	    //课程决裁查看页面
  		@SuppressWarnings({"rawtypes" })
  		@RequestMapping(value = "/courseMaker")
  		public ModelAndView courseMaker(HttpServletRequest request,
  				HttpServletResponse response, ModelMap modelMap) throws Exception {
  			//查询那些学生学习那些课程
  			List elist = eduTrainser.courseMaker(request);
  			modelMap.put("courseMakerList", elist);
  			modelMap.put("courseMakerListCount", elist.size());
  			modelMap.put("makEmpidName", request.getParameter("makEmpidName"));
  			modelMap.put("makcoursename", request.getParameter("makcoursename"));
  			modelMap.put("makstartdate", request.getParameter("makstartdate"));
  			modelMap.put("makenddate", request.getParameter("makenddate"));
  			modelMap.put("apply_flag", request.getParameter("apply_flag"));
  			return new ModelAndView("/edu/traineducation/courseMaker", modelMap);
  		}
	  		
  		
  	    //决裁页面修改数据
  		@RequestMapping(value = "/updateCourseMaker", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateCourseMaker(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.updateCourseMaker(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"pa.salary.canShu.caozuo_success", request));//修改成功
				map.put("formId", "courseMaker");
			} else {
				map.put("statusCode", "300");
				map.put("message",
						TipMessage.getTipMessage("pa.salary.canShu.caozuo_fail", request));//修改失败
			}
			return map;
		}
  		
  	  //============================================课程确认=========================================
	    //课程确认查看页面
  		@SuppressWarnings({"rawtypes" })
  		@RequestMapping(value = "/courseConfirm")
  		public ModelAndView courseConfirm(HttpServletRequest request,
  				HttpServletResponse response, ModelMap modelMap) throws Exception {
  			//查询决裁的课程
  			List elist = eduTrainser.courseConfirm(request);
  			modelMap.put("courseConfirmList", elist);
  			modelMap.put("courseConfirmListCount", elist.size());
  			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
  			if(request.getParameter("confirmstartdate")==""||request.getParameter("confirmstartdate")==null){
				//获取当前月第一天：
				Calendar c = Calendar.getInstance();    
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("confirmstartdate",first);
			}else{
				modelMap.put("confirmstartdate",request.getParameter("confirmstartdate"));
			}
		    if(request.getParameter("confirmenddate")==""||request.getParameter("confirmenddate")==null){
		    	Calendar c = Calendar.getInstance();
		    	c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
		    	modelMap.put("confirmenddate",last);
			}else{
				modelMap.put("confirmenddate",request.getParameter("confirmenddate"));
			}
  			modelMap.put("confirmEmpidName", request.getParameter("confirmEmpidName"));
  			modelMap.put("confirmcoursename", request.getParameter("confirmcoursename"));
  			modelMap.put("confirm_flag", request.getParameter("confirm_flag"));
  			modelMap.put("confirmDepartno", request.getParameter("confirmDepartno"));
  			return new ModelAndView("/edu/traineducation/courseConfirm", modelMap);
  		}
  		
  	   //课程确认页面修改数据
  		@RequestMapping(value = "/updateCourseConfirm", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateCourseConfirm(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.updateCourseConfirm(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));//修改成功
				map.put("formId", "courseConfirm");
			} else {
				map.put("statusCode", "300");
				map.put("message",
						TipMessage.getTipMessage("alert.message.update_fail", request));//修改失败
			}
			return map;
		}
  		
  	  //============================================课程进行情况=========================================
	    //课程确认查看页面
  		@SuppressWarnings({"rawtypes", "unchecked" })
  		@RequestMapping(value = "/makerSituation")
  		public ModelAndView makerSituation(HttpServletRequest request,
  				HttpServletResponse response, ModelMap modelMap) throws Exception {
  			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String adminEmpid=admin.getEmpID();
			String adminPersonid=admin.getPersonId();
  			//查询此人所有的课程
  			List elist = eduTrainser.makerSituation(request);
  			if(adminPersonid.equals("2000560")||adminPersonid.equals("2000889")){
  				if(elist!=null&&elist.size()>0){
  					List relist=new ArrayList();
  					for(int i=0;i<elist.size();i++){
  						LinkedHashMap linkmap=(LinkedHashMap) elist.get(i);
  						String stupersonid=(String) linkmap.get("STU_PERSON_ID");
  						String basicno=String.valueOf(linkmap.get("BASIC_NO"));
  						String content="PLAN_EMPLOYEE_EMPID";
  						String table="edu_basic_information";
  						String term="basic_no='"+basicno+"'";
  						String stuempid=eduTrainser.queryClobSecond(request,content,table,term) != null ? eduTrainser.queryClobSecond(request,content,table,term):"" ;
  						String [] arraystuempid=stuempid.split(",");
  						String alreadycountnum=eduTrainser.alreadycountnum(request,basicno);
  						//如果指定人员有这些人的名字或者这些人没有申请过的,可以申请课程/
  						String str="";
  						for(int z=0;z<arraystuempid.length;z++){
					    		 str=str+"'"+arraystuempid[z]+"',";
					     }
  						str=str.substring(0,str.length()-1);
  						List hlist = eduTrainser.queryEmployee(request,str);
  						if(hlist!=null&&hlist.size()>0){
  						for(int a=0;a<hlist.size();a++){
  						LinkedHashMap hmap=(LinkedHashMap) hlist.get(a);
  						String cpersonid=(String) hmap.get("PERSON_ID");
  						if(cpersonid.equals(stupersonid)){
				    	 linkmap.put("alreadycountnum", alreadycountnum+"/"+String.valueOf(arraystuempid.length));
			    		   linkmap.put("EMPID", hmap.get("EMPID"));
			    		   linkmap.put("LOCAL_NAME", hmap.get("LOCAL_NAME"));
			    		   linkmap.put("DEPTNAME", hmap.get("DEPTNAME"));
			    		   linkmap.put("POST_GRADE_NO_NAME", hmap.get("POST_GRADE_NO_NAME"));
			    		   relist.add(linkmap);
				     }
				}
			}
  					
		}
  					elist=relist;
  				}
  			}else{
  				if(elist!=null&&elist.size()>0){
  					List relist=new ArrayList();
  					for(int i=0;i<elist.size();i++){
  						LinkedHashMap linkmap=(LinkedHashMap) elist.get(i);
  						String stupersonid=(String) linkmap.get("STU_PERSON_ID");
  						String basicno=String.valueOf(linkmap.get("BASIC_NO"));
  						String content="PLAN_EMPLOYEE_EMPID";
  						String table="edu_basic_information";
  						String term="basic_no='"+basicno+"'";
  						String stuempid=eduTrainser.queryClobSecond(request,content,table,term) != null ? eduTrainser.queryClobSecond(request,content,table,term) : "";
  						String [] arraystuempid=stuempid.split(",");
  						String alreadycountnum=eduTrainser.alreadycountnum(request,basicno);
  						//如果指定人员有这些人的名字或者这些人没有申请过的,可以申请课程/
  					     if(adminPersonid.equals(stupersonid)){
  					    	 linkmap.put("alreadycountnum", alreadycountnum+"/"+String.valueOf(arraystuempid.length));
  					    	 List hlist = eduTrainser.queryEmployee(request,"'"+adminEmpid+"'");
  					    	   for(int y=0;y<hlist.size();y++){
  					    		   LinkedHashMap hmap=(LinkedHashMap) hlist.get(y);
  					    		   linkmap.put("EMPID", hmap.get("EMPID"));
  					    		   linkmap.put("LOCAL_NAME", hmap.get("LOCAL_NAME"));
  					    		   linkmap.put("DEPTNAME", hmap.get("DEPTNAME"));
  					    		   linkmap.put("POST_GRADE_NO_NAME", hmap.get("POST_GRADE_NO_NAME"));
  					    		   relist.add(linkmap);
  					    	   }
  					    	 
  					     }
  					}
  					elist=relist;
  				}
  			}
  			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
  			if (request.getParameter("situationstartdate") == null || "".equals(request.getParameter("situationstartdate"))) {
  				//获取当前月第一天：
  				Calendar c = Calendar.getInstance();
  				c.add(Calendar.MONTH, 0);
  				//设置为1号,当前日期既为本月第一天 
  				c.set(Calendar.DAY_OF_MONTH, 1);
  				String first = format.format(c.getTime());
  				modelMap.put("situationstartdate", first);
  			} else {
  				modelMap.put("situationstartdate", request.getParameter("situationstartdate"));
  			}
  			if ("".equals(request.getParameter("situationenddate")) || request.getParameter("situationenddate") == null) {
  				Calendar c = Calendar.getInstance();
  				c.add(Calendar.MONTH, 0);
  				//设置为当月月底
  				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
  				String last = format.format(c.getTime());
  				modelMap.put("situationenddate", last);
  			} else {
  				modelMap.put("situationenddate", request.getParameter("situationenddate"));
  			}
  			modelMap.put("makerSituationList", elist);
  			modelMap.put("makerSituationListCount", elist.size());
  			modelMap.put("adminPersonid", adminPersonid);
  			modelMap.put("situationEmpidName", request.getParameter("situationEmpidName"));
  			modelMap.put("situationcoursename", request.getParameter("situationcoursename"));
  			modelMap.put("situation_flag", request.getParameter("situation_flag"));
  			return new ModelAndView("/edu/traineducation/makerSituation", modelMap);
  		}	
  		//课程进行情况查询页面(HUB)
  		@SuppressWarnings({"rawtypes", "unchecked" })
  		@RequestMapping(value = "/makerSituationHUB")
  		public ModelAndView makerSituationHUB(HttpServletRequest request,
  				HttpServletResponse response, ModelMap modelMap) throws Exception {
  			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
  			String adminEmpid=admin.getEmpID();
  			String adminPersonid=admin.getPersonId();
  			//查询此人所有的课程
  			List elist = eduTrainser.makerSituation(request);
  			modelMap.put("makerSituationList", elist);
  			modelMap.put("makerSituationListCount", elist.size());
  			modelMap.put("adminPersonid", adminPersonid);
  			
  			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			if(request.getParameter("situationstartdate")==""||request.getParameter("situationstartdate")==null){
				//获取当前月第一天：
				Calendar c = Calendar.getInstance();    
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("situationstartdate",first);
			}else{
				modelMap.put("situationstartdate",request.getParameter("situationstartdate"));
			}
		    if(request.getParameter("situationenddate")==""||request.getParameter("situationenddate")==null){
		    	Calendar c = Calendar.getInstance();
		    	c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
		    	modelMap.put("situationenddate",last);
			}else{
				modelMap.put("situationenddate",request.getParameter("situationenddate"));
			}
  			
  			modelMap.put("situationEmpidName", request.getParameter("situationEmpidName"));
  			modelMap.put("situationcoursename", request.getParameter("situationcoursename"));
  			//modelMap.put("situationstartdate", request.getParameter("situationstartdate"));
  			//modelMap.put("situationenddate", request.getParameter("situationenddate"));
  			modelMap.put("situation_flag", request.getParameter("situation_flag"));
  			modelMap.put("confirmDepartno", request.getParameter("confirmDepartno"));
  			return new ModelAndView("/edu/traineducation/makerSituationHUB", modelMap);
  		}	
  		//取消申请
		@RequestMapping(value = "/cancelApply")
		@ResponseBody
		public Map<String, Object> cancelApply(
				HttpServletRequest request, HttpServletResponse response,
				ModelMap modelMap) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			int result = this.eduTrainser.cancelApply(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));// 删除成功
				map.put("formId", "makerSituation");
			} else {
				map.put("statusCode", "300");
			    map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));// 删除失败
			}
			return map;
		}
  		
		
		
  		
}
