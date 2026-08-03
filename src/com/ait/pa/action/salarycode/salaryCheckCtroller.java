package com.ait.pa.action.salarycode;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.sys.service.salaryMappingSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.salary.PaComputeItemSer;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.pa.service.salary.PaInputItemSer;
import com.ait.pa.service.wagebase.PaBasicItemSer;
import com.ait.pa.service.salarycode.salaryCodeSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/pa/salarycode")
public class salaryCheckCtroller {
	Logger logger = Logger.getLogger(salaryCheckCtroller.class);

	@Autowired
	private salaryMappingSer salaryMappingSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private PaBasicItemSer paBasicItemSer;
	@Autowired
	private PaComputeItemSer paComputeItemSer;

	@Autowired
	private salaryCodeSer salaryCodeSer;

	@Autowired
	private PaInputItemParamSer paInputItemParamSer;
	
	@Autowired
	private AuthorityUtil authorityUtil;
	
	@Autowired
	private EmpInfoSer empInfoSer;

	/**
	 * 跳转到基础项目库首页(view Pa Basic Item)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSalaryCheckList")
	public ModelAndView viewSalaryCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		List salaryCheckList = this.salaryMappingSer
				.getSalaryItemCheckMapList(request);
		int salaryCheckCnt = this.salaryMappingSer.getSalaryItemCheckMapCnt(request);
		modelMap.put("salaryCheckList", salaryCheckList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, salaryCheckCnt);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2526"));
		return new ModelAndView("/pa/salarycode/viewSalaryCheckList", modelMap);
	}

	/**
	 * 到公共添加页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addSalaryCodeAffirmView")
	public ModelAndView addSalaryCodeAffirmView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String personName = admin.getLocalName();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID",
				paramMap.get("CPNY_ID") == null ? admin.getCpnyId() : paramMap
						.get("CPNY_ID").toString());
		modelMap.put("date", date);
		modelMap.put("personName", personName);
		return new ModelAndView("/pa/salarycode/addSalaryCodeAffirmView",
				modelMap);
	}

	/**
	 * 申请新代码（工资代码包括基础项目、输入项目、计算项目）
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAffirmSalaryCodeInfo")
	@ResponseBody
	public Map addAffirmSalaryCodeInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String item_id = request.getParameter("ITEM_ID");
		String item_no = request.getParameter("ITEM_NO");
		String param_item_no = request.getParameter("ITEM_NO");
		String cpny_id = admin.getCpnyId();
		String activity_type = request.getParameter("ACTIVITY_TYPE");
		if (activity_type == "1" || "1".equals(activity_type)) {
			int resultNum = this.salaryMappingSer
					.addAffirmSalaryCodeInfo(request);
			if (resultNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"pa.salarycode.affirm.success", request));
				map.put("navTabId", "pa1104");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"pa.salarycode.affirm.fail", request));
			}
		} else {
			String project_type = request.getParameter("PROJECT_TYPE");
			if (project_type == "1" || "1".equals(project_type)) {
				int errorInt = this.paBasicItemSer
						.checkAddPaBasicItemParamInfo(request, cpny_id, item_no);
				if (errorInt == 0) {
					int resultNum1 = this.salaryMappingSer
							.addAffirmSalaryInfo(request);
					if (resultNum1 == 1) {
						map.put("statusCode", "200");
						map.put("message", TipMessage.getTipMessage(
								"pa.salarycode.affirm.success", request));
						map.put("navTabId", "pa1104");
					} else {
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage(
								"pa.salarycode.affirm.fail", request));
					}
				}else{
					map.put("statusCode", "300");
	   			     map.put("message", TipMessage.getTipMessage(
	   					 "ar.item.title.itemapply", request));
				}
			}else if (project_type == "2" || "2".equals(project_type)) {
				int errorInt = this.paInputItemParamSer.checkAddPaInputItemParamInfo(request, cpny_id, param_item_no);
				if (errorInt == 0) {
					int resultNum1 = this.salaryMappingSer
							.addAffirmSalaryInfo(request);
					if (resultNum1 == 1) {
						map.put("statusCode", "200");
						map.put("message", TipMessage.getTipMessage(
								"pa.salarycode.affirm.success", request));
						map.put("navTabId", "pa1104");
					} else {
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage(
								"pa.salarycode.affirm.fail", request));
					}
				}else{
					map.put("statusCode", "300");
	   			     map.put("message", TipMessage.getTipMessage(
	   					 "ar.item.title.itemapply", request));
				}
			}else if (project_type == "3" || "3".equals(project_type)) {
				int errorInt = this.paComputeItemSer.checkAddPaComputeItemParamInfo(request, cpny_id, item_no);
				if (errorInt == 0) {
					int resultNum1 = this.salaryMappingSer
							.addAffirmSalaryInfo(request);
					if (resultNum1 == 1) {
						map.put("statusCode", "200");
						map.put("message", TipMessage.getTipMessage(
								"pa.salarycode.affirm.success", request));
						map.put("navTabId", "pa1104");
					} else {
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage(
								"pa.salarycode.affirm.fail", request));
					}
				}else{
					map.put("statusCode", "300");
	   			     map.put("message", TipMessage.getTipMessage(
	   					 "ar.item.title.itemapply", request));
				}
			}
		}
		return map;
	}
	

	/**
	 * 去代码启用申请页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaBasicParamAffirmview")
	public ModelAndView addPaBasicParamAffirmview(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String personName = admin.getLocalName();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间
		Object salaryCodeInfo = this.salaryCodeSer.getSalaryCodeInfo(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("salaryCodeInfo", salaryCodeInfo);
		modelMap.put("date", date);
		modelMap.put("personName", personName);
		return new ModelAndView("/pa/salarycode/addPaBasicParamAffirmview",
				modelMap);
	}

	/**
	 * 指定法人(add Pa Basic Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addSalaryCodeParamInfo")
	@ResponseBody
	public Map addSalaryCodeParamInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String project_type = request.getParameter("PROJECT_TYPE");
		String item_no = request.getParameter("ITEM_NO");
		String param_item_no = request.getParameter("ITEM_NO");
		String cpny_id = request.getParameter("CPNY_ID");
		if (project_type == "1" || "1".equals(project_type)) {
			int errorInt = this.paBasicItemSer.checkAddPaBasicItemParamInfo(
					request, cpny_id, item_no);
			if (errorInt == 0) {
				int resultNum = this.paBasicItemSer.addPaBasicItemParamInfo(
						request, cpny_id, item_no);
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_success", request));
					map.put("navTabId", "pa1105");
					map.put("callbackType", "closeCurrent");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));
				}
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"pa.salarycode.affirm.success_exist", request));
			}
		} else if (project_type == "2" || "2".equals(project_type)) {
			int errorInt = this.paInputItemParamSer
					.checkAddPaInputItemParamInfo(request, cpny_id, item_no);
			if (errorInt == 0) {
				int resultNum = this.paInputItemParamSer
						.addPaInputItemParamInfo(request, cpny_id,
								param_item_no);
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_success", request));
					map.put("navTabId", "pa1105");
					map.put("callbackType", "closeCurrent");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));
				}
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"pa.salarycode.affirm.success_exist", request));
			}
		} else if (project_type == "3" || "3".equals(project_type)) {
			int errorInt = this.paComputeItemSer
					.checkAddPaComputeItemParamInfo(request, cpny_id, item_no);
			if (errorInt == 0) {
				int returnNum = this.paComputeItemSer
						.addPaComputeItemParamInfo(request, cpny_id, item_no);
				if (returnNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("navTabId", "pa1105");
					map.put("callbackType", "closeCurrent");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"pa.salarycode.affirm.success_exist", request));
			}
		}
		return map;
	}
}
