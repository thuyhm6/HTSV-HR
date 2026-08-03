package com.ait.pa.action.affirmsalarycode;

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

import com.ait.pa.service.salary.PaComputeItemSer;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.pa.service.salary.PaInputItemSer;
import com.ait.pa.service.salarycode.salaryCodeSer;
import com.ait.pa.service.wagebase.PaBasicItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/pa/affirmsalarycode")
public class AffirmSalaryCodeCtoller {
	Logger logger = Logger.getLogger(AffirmSalaryCodeCtoller.class);

	@Autowired
	private salaryCodeSer salaryCodeSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private PaBasicItemSer paBasicItemSer;
	@Autowired
	private PaInputItemSer paInputItemSer;
	@Autowired
	private PaComputeItemSer paComputeItemSer;
	@Autowired
	private PaInputItemParamSer paInputItemParamSer;

	/**
	 * 跳转到基础项目库首页(view Pa Basic Item)(决裁查看)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmSalaryCodeList")
	public ModelAndView viewAffirmSalaryCodeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List salaryCodeList = this.salaryCodeSer
				.getAffirmSalaryCodeList(request);
		int salaryCodeCnt = this.salaryCodeSer.getAffirmSalaryCodeCnt(request);
		String cpny_id = admin.getCpnyId();
		if (cpny_id != null && !"".equals(cpny_id)) {
			modelMap.put("CPNY_ID", cpny_id);
		}
		modelMap.put("salaryCodeList", salaryCodeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, salaryCodeCnt);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216679"));
		return new ModelAndView(
				"/pa/affirmsalarycode/viewAffirmSalaryCodeList", modelMap);
	}
	
	/**
	 * 决裁情况查询
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmSalary")
	public ModelAndView viewAffirmSalary(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List salaryList = this.salaryCodeSer
				.getAffirmSalary(request);
		String cpny_id = admin.getCpnyId();
		if (cpny_id != null && !"".equals(cpny_id)) {
			modelMap.put("CPNY_ID", cpny_id);
		}
		modelMap.put("salaryList", salaryList);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216679"));
		return new ModelAndView(
				"/pa/affirmsalarycode/viewAffirmSalary", modelMap);
	}
	

	/**
	 * 跳转到基础项目库首页(view Pa Basic Item)(决裁列表)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmSalaryList")
	public ModelAndView viewAffirmSalaryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List salaryCodeList = this.salaryCodeSer.getAffirmSalaryList(request);
		int salaryCodeCnt = this.salaryCodeSer.getAffirmSalaryCnt(request);

		modelMap.put("salaryCodeList", salaryCodeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, salaryCodeCnt);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2526"));
		return new ModelAndView("/pa/affirmsalarycode/viewAffirmSalaryList",
				modelMap);
	}
	
	/**
	 * 跳转到决裁页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmSalaryItemInfo")
	public ModelAndView viewAffirmSalaryItemInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List salaryCodeList = this.salaryCodeSer.getAffirmSalaryList(request);
		Object salaryCodeInfo = null;
		if(salaryCodeList.size()>0){
			salaryCodeInfo = salaryCodeList.get(0);
		}
		modelMap.put("salaryCodeInfo", salaryCodeInfo);
		return new ModelAndView("/pa/affirmsalarycode/viewAffirmSalaryItemInfo",
				modelMap);
	}

	/**
	 * 进行决裁，如果决裁通过则工资代码直接进入相应的工资代码库中
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAffirmSalaryInfo")
	@ResponseBody
	public Map updateAffirmSalaryInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String project_type = request.getParameter("PROJECT_TYPE");
		String affirm_type = request.getParameter("ACTIVITY_TYPE");
		String item_no = request.getParameter("ITEM_NO");
		String cpny_id = request.getParameter("cpny");
		String flag = request.getParameter("flag");
		if (flag == "1" || "1".equals(flag)) {
			if (affirm_type == "1" || "1".equals(affirm_type)) {
				int resultInt = this.salaryCodeSer
						.updateAffirmSalaryInfo(request);
				if (resultInt == 1) {
					if (project_type == "1" || project_type.equals("1")) {
						int errorInt = this.paBasicItemSer
								.checkAddPaBasicItemInfo(request);
						if (errorInt == 0) {
							int result = this.paBasicItemSer
									.addPaBasicItemInfo(request,item_no);
							if (result == 1) {
								int errorNum = paBasicItemSer.checkAddPaBasicItemParamInfo(request);
								if(errorNum == 0){
									int resultNum = this.paBasicItemSer.addPaBasicItemParamInfo(request);
									if(resultNum==1){
										map.put("statusCode", "200");
										map.put("message", TipMessage.getTipMessage(
												"ess.viewApply.title.pass", request));
										map.put("navTabId", "pa1104jc");
										map.put("callbackType", "closeCurrent");
									}else {
										map.put("statusCode", "300");
										map.put("message", TipMessage.getTipMessage(
												"pa.salarycode.title.affirm_fail",
												request));
									}
								}
							} 
						} else {
							map.put("statusCode", "300");
							map.put("message", TipMessage.getTipMessage(
									"pa.salarycode.title.affirm_fail", request));
						}
					} else if (project_type == "2" || project_type.equals("2")) {
						int errorInt = this.paInputItemSer
								.checkAddPaInputItemInfo(request);
						if (errorInt == 0) {
							int result = this.paInputItemSer
									.addPaInputItemInfo(request,item_no);
							if (result == 1) {
								int errorNum = this.paInputItemParamSer.checkAddPaInputItemParamInfo(request);
								if(errorNum==0){
									int resultNum = this.paInputItemParamSer.addPaInputItemParamInfo(request, cpny_id, item_no);
									if(resultNum ==1){
										map.put("statusCode", "200");
										map.put("message", TipMessage.getTipMessage(
												"ess.viewApply.title.pass", request));
										map.put("navTabId", "pa1104jc");
										map.put("callbackType", "closeCurrent");
									}else {
										map.put("statusCode", "300");
										map.put("message", TipMessage.getTipMessage(
												"pa.salarycode.title.affirm_fail",
												request));
									}
								}
							} 
						} else {
							map.put("statusCode", "300");
							map.put("message", TipMessage.getTipMessage(
									"pa.salarycode.title.affirm_fail", request));
						}
					} else if (project_type == "3" || project_type.equals("3")) {
						int errorNum = this.paComputeItemSer
								.checkAddPaComputeItemInfo(request);
						if (errorNum == 0) {
							int returnNum = this.paComputeItemSer
									.addPaComputeItemInfo(request,item_no);
							if (returnNum == 1) {
								int errorInt = this.paComputeItemSer.checkAddPaComputeItemParamInfo(request);
								if(errorInt==0){
									int resultNum = this.paComputeItemSer.addPaComputeItemParamInfo(request);
									if(resultNum==1){
										map.put("statusCode", "200");
										map.put("message", TipMessage.getTipMessage(
												"ess.viewApply.title.pass", request));
										map.put("navTabId", "pa1104jc");
										map.put("callbackType", "closeCurrent");
									} else {
										map.put("statusCode", "300");
										map.put("message", TipMessage.getTipMessage(
												"pa.salarycode.title.affirm_fail",
												request));
									}
								}
							}
						} else {
							map.put("statusCode", "300");
							map.put("message", TipMessage.getTipMessage(
									"pa.salarycode.title.affirm_fail", request));
						}
					}
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"pa.salarycode.title.affirm_fail", request));
				}
			} else {
				if (affirm_type == "2" || "2".equals(affirm_type)) {
					int resultInt = this.salaryCodeSer
							.updateAffirmSalaryInfo(request);
					if (resultInt == 1) {
						if (project_type == "1" || project_type.equals("1")) {
							int errorInt = this.paBasicItemSer
									.checkAddPaBasicItemParamInfo(request, cpny_id, item_no);
							if (errorInt == 0) {
								int result = this.paBasicItemSer
										.addPaBasicItemParamInfo(request, cpny_id, item_no);
								if (result == 1) {
									map.put("statusCode", "200");
									map.put("message", TipMessage
											.getTipMessage(
													"ess.viewApply.title.pass",
													request));
									map.put("navTabId", "pa1104jc");
									map.put("callbackType", "closeCurrent");
								} else {
									map.put("statusCode", "300");
									map.put("message",
											TipMessage
													.getTipMessage(
															"pa.salarycode.title.affirm_fail",
															request));
								}
							} else {
								map.put("statusCode", "300");
								map.put("message", TipMessage.getTipMessage(
										"pa.salarycode.title.affirm_fail",
										request));
							}
						} else if (project_type == "2"
								|| project_type.equals("2")) {
							int errorInt = this.paInputItemParamSer.checkAddPaInputItemParamInfo(request, cpny_id, item_no);
							if (errorInt == 0) {
								int result = this.paInputItemParamSer
										.addPaInputItemParamInfo(request, cpny_id, item_no);
								if (result == 1) {
									map.put("statusCode", "200");
									map.put("message", TipMessage
											.getTipMessage(
													"ess.viewApply.title.pass",
													request));
									map.put("navTabId", "pa1104jc");
									map.put("callbackType", "closeCurrent");
								} else {
									map.put("statusCode", "300");
									map.put("message",
											TipMessage
													.getTipMessage(
															"pa.salarycode.title.affirm_fail",
															request));
								}
							} else {
								map.put("statusCode", "300");
								map.put("message", TipMessage.getTipMessage(
										"pa.salarycode.title.affirm_fail",
										request));
							}
						} else if (project_type == "3"
								|| project_type.equals("3")) {
							int errorNum = this.paComputeItemSer
									.checkAddPaComputeItemParamInfo(request, cpny_id, item_no);
							if (errorNum == 0) {
								int returnNum = this.paComputeItemSer.addPaComputeItemParamInfo(request, cpny_id, item_no);
								if (returnNum == 1) {
									map.put("statusCode", "200");
									map.put("message", TipMessage
											.getTipMessage(
													"ess.viewApply.title.pass",
													request));
									map.put("navTabId", "pa1104jc");
									map.put("callbackType", "closeCurrent");
								} else {
									map.put("statusCode", "300");
									map.put("message",
											TipMessage
													.getTipMessage(
															"pa.salarycode.title.affirm_fail",
															request));
								}
							} else {
								map.put("statusCode", "300");
								map.put("message", TipMessage.getTipMessage(
										"pa.salarycode.title.affirm_fail",
										request));
							}
						}
					} else {
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage(
								"pa.salarycode.title.affirm_fail", request));
					}
				}
			}
		} else {
			int resultInt = this.salaryCodeSer.updateAffirmSalaryInfo(request);
			if (resultInt == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"ess.viewApply.title.reject", request));
				map.put("navTabId", "pa1104jc");
				map.put("callbackType", "closeCurrent");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"pa.salarycode.title.affirm_fail", request));
			}
		}
		return map;
	}

	/**
	 * 执行删除 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSalaryCodeAffirmInfo")
	@ResponseBody
	public Map<String, Object> deleteSalaryCodeAffirmInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
				int resultNum = this.salaryCodeSer.deleteAffirmSalaryInfo(request);
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("navTabId", "pa1104jcck");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));
			    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
