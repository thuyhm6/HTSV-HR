package com.ait.pa.action.salarycode;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.insurance.InsuranceComputeItemSer;
import com.ait.pa.service.insurance.InsuranceInputItemSer;
import com.ait.pa.service.salary.PaComputeItemSer;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.pa.service.salary.PaInputItemSer;
import com.ait.pa.service.salarycode.salaryCodeSer;
import com.ait.pa.service.wagebase.PaBasicItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.SendEmailSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/pa/salarycode")
public class salaryCodeCtroller {
	Logger logger = Logger.getLogger(salaryCodeCtroller.class);

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
	@Autowired
	private InsuranceInputItemSer insuranceInputItemSer;
	@Autowired
	private InsuranceComputeItemSer insuranceComputeItemSer;
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private AuthorityUtil authorityUtil;
	
	@Autowired
	private SendEmailSer sendEmailSer;
	

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
	@RequestMapping(value = "/viewSalaryCodeList")
	public ModelAndView viewSalaryCodeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List salaryCodeList = this.salaryCodeSer.getSalaryCodeList(request);
		int salaryCodeCnt = this.salaryCodeSer.getSalaryCodeCnt(request);

		modelMap.put("salaryCodeList", salaryCodeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, salaryCodeCnt);
		
		return new ModelAndView("/pa/salarycode/viewSalaryCodeList", modelMap);
	}

	/**
	 * 到公共添加页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addSalaryCodeView")
	public ModelAndView addSalaryCodeView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		//sendEmailSer.sendAffirmEmailHTSV(1);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID",
				paramMap.get("CPNY_ID") == null ? admin.getCpnyId() : paramMap
						.get("CPNY_ID").toString());

		return new ModelAndView("/pa/salarycode/addSalaryCodeView", modelMap);
	}

	/**
	 * 执行添加(add Pa Basic Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addSalaryCodeInfo")
	@ResponseBody
	public Map addSalaryCodeInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String itemType = request.getParameter("ITEM_TYPE");
		if (!itemType.equals("6")) {
			int resultNum = this.paInputItemSer.addPaInputItemInfo(request);
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("formId", "viewSalaryCodeListForm");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
		} else if (itemType.equals("6")) {
			int returnNum = this.paComputeItemSer
						.addPaComputeItemInfo(request);
				if (returnNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("formId", "viewSalaryCodeListForm");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
			}
		return map;
	}

	/**
	 * 去修改页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSalaryCodeView")
	public ModelAndView updateSalaryCodeView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Object salaryCodeInfo = this.salaryCodeSer.getSalaryCodeInfo(request);
		modelMap.put("salaryCodeInfo", salaryCodeInfo);
		return new ModelAndView("/pa/salarycode/updateSalaryCodeView", modelMap);
	}

	/**
	 * 修改工资项目信息(update Pa Basic Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateSalaryCodeInfo")
	@ResponseBody
	public Map updateSalaryCodeInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String itemType = request.getParameter("ITEM_TYPE");
		if (!itemType.equals("6")) {
			int result = this.paInputItemSer.updatePaInputItemInfo(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_success", request));
					map.put("navTabId", "pa1104");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));
				}
		} else if (itemType.equals("6")) {
			int returnNum = this.paComputeItemSer
						.updatePaComputeItemInfo(request);
				if (returnNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_success", request));
					map.put("navTabId", "pa1104");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));
				}
		}
		return map;
	}

	/**
	 * 执行删除 删除前判断有没有使用的数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSalaryCodeInfo")
	@ResponseBody
	public Map<String, Object> deleteSalaryCodeInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String project_type = request.getParameter("PROJECT_TYPE");
		if (project_type == "1" || project_type.equals("1")) {
			try {
				int errorInt = this.paBasicItemSer
						.checkDeletePaBasicItemInfo(request);

				if (errorInt == 0) {
					int resultNum = this.paBasicItemSer
							.deletePaBasicItemInfo(request);
					if (resultNum == 1) {
						map.put("statusCode", "200");
						map.put("message", TipMessage.getTipMessage(
								"alert.message.delete_success", request));
						map.put("navTabId", "pa1104");
					} else {
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage(
								"alert.message.delete_fail", request));
					}

				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_info_use", request));
				}
			} catch (Exception e) {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));
			}

		} else if (project_type == "2" || project_type.equals("2")) {
			int errorInt = this.paInputItemSer
					.checkDeletePaInputItemInfo(request);
			if (errorInt == 0) {
				int resultNum = this.paInputItemSer
						.deletePaInputItemInfo(request);
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("navTabId", "pa1104");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));
				}

			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_info_use", request));
			}
		} else if (project_type == "3" || project_type.equals("3")) {
			int errorNum = this.paComputeItemSer
					.checkDeletePaComputeItemInfo(request);
			if (errorNum > 0) {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_info_use", request));
			} else {
				int result = this.paComputeItemSer
						.deletePaComputeItemInfo(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("navTabId", "pa1104");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));
				}
			}
		}
		return map;
	}

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
	@RequestMapping(value = "/viewSalaryCodeMappingList")
	public ModelAndView viewSalaryCodeMappingList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List salaryCodeList = this.salaryCodeSer
				.getSalaryCodeMappingList(request);
		int salaryCodeCnt = this.salaryCodeSer.getSalaryCodeMappingCnt(request);
		List companyList = empInfoSer.getCompanyList(request);
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("salaryCodeList", salaryCodeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, salaryCodeCnt);
		modelMap.put("companyList", companyList);
		modelMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2526"));
		return new ModelAndView("/pa/salarycode/viewSalaryCodeMappingList",
				modelMap);
	}

	/**
	 * 去修改页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSalaryCodeMappingView")
	public ModelAndView updateSalaryCodeMappingView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List salaryCodeList = this.salaryCodeSer
				.getSalaryCodeMappingList(request);
		Object salaryCodeInfo = salaryCodeList.get(0);
		modelMap.put("salaryCodeInfo", salaryCodeInfo);
		return new ModelAndView("/pa/salarycode/updateSalaryCodeMappingView",
				modelMap);
	}

	/**
	 * 修改工资项目信息(update Pa Basic Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateSalaryCodeMappingInfo")
	@ResponseBody
	public Map updateSalaryCodeMappingInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String project_type = request.getParameter("PROJECT_TYPE");
		if (project_type == "1" || project_type.equals("1")) {
			int result = this.paBasicItemSer.updatePaBasicItemMappingInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa2014mp");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}

		} else if (project_type == "2" || project_type.equals("2")) {

			int result = this.paInputItemParamSer.updatePaInputItemMappingInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa2014mp");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}

		} else if (project_type == "3" || project_type.equals("3")) {

			int returnNum = this.paComputeItemSer
					.upPaComputeItemMappingInfo(request);
			if (returnNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa2014mp");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		}else if (project_type == "4" || project_type.equals("4")) {

			int returnNum = this.insuranceInputItemSer.updateIsInputItemParamInfo(request);
			if (returnNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa2014mp");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		}else if (project_type == "5" || project_type.equals("5")) {

			int returnNum = this.insuranceComputeItemSer
					.updateIsItemParamInfo(request);
			if (returnNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa2014mp");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		}
		return map;
	}
	
	/**
	 * 工资财务代码管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaItemManagerInfo")
	public ModelAndView viewPaItemManagerInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewPaItemList = this.salaryCodeSer.viewPaItemList(request);
		modelMap.put("viewPaItemList", viewPaItemList);

		List getPaItemList = this.salaryCodeSer.getPaItemList(request);
		modelMap.put("getPaItemList", getPaItemList);
		
		LinkedHashMap param = null;
		
		if(viewPaItemList!=null && viewPaItemList.size() > 0){
			param = (LinkedHashMap)viewPaItemList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("orgPaItemSize", viewPaItemList.size());
		}else{
			modelMap.put("orgPaItemSize", 0);
		}
		return new ModelAndView("/pa/salarycode/viewPaItemManagerInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddPaItemInfo")
	public ModelAndView viewAddPaItemInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewPaItemList = this.salaryCodeSer.viewPaItemList(request);

		List getPaItemList = this.salaryCodeSer.getPaItemList(request);
		modelMap.put("getPaItemList", getPaItemList);

		LinkedHashMap param = null;
		
		if(viewPaItemList!=null && viewPaItemList.size() > 0){
			param = (LinkedHashMap)viewPaItemList.get(0);
		}
		modelMap.put("paItemInfo", param);
		modelMap.put("START_DATE", DateUtil.getSysdateStr("yyyy.MM.dd"));
		modelMap.put("END_DATE", "9999.12.31");

		return new ModelAndView("/pa/salarycode/viewAddPaItemInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaItemInfo")
	@ResponseBody
	public Map addPaItemInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.salaryCodeSer.addPaItemInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("navTabId", "pa9999");
		} else if (result == 2) {
			map.put("statusCode", "300");
			map.put("message", "工资项目重复，请确认");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaItemInfo")
	@ResponseBody
	public Map deletePaItemInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.salaryCodeSer.deletePaItemInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			map.put("navTabId", "pa9999");
		} else {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
		}
		return map;
	}
	

	
	/**
	 * 工资财务代码公式管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaItemFormulaManagerInfo")
	public ModelAndView viewPaItemFormulaManagerInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewPaItemFormulaList = this.salaryCodeSer.viewPaItemFormulaList(request);
		modelMap.put("viewPaItemFormulaList", viewPaItemFormulaList);

		LinkedHashMap param = null;
		
		if(viewPaItemFormulaList!=null && viewPaItemFormulaList.size() > 0){
			param = (LinkedHashMap)viewPaItemFormulaList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("orgPaItemFormulaSize", viewPaItemFormulaList.size());
		}else{
			modelMap.put("orgPaItemFormulaSize", 0);
		}
		return new ModelAndView("/pa/salarycode/viewPaItemFormulaManagerInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddPaItemFormulaInfo")
	public ModelAndView viewAddPaItemFormulaInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List viewPaItemFormulaList = this.salaryCodeSer.viewPaItemFormulaList(request);

		LinkedHashMap param = null;
		
		if(viewPaItemFormulaList!=null && viewPaItemFormulaList.size() > 0){
			param = (LinkedHashMap)viewPaItemFormulaList.get(0);
		}
		modelMap.put("paItemFormulaInfo", param);
		modelMap.put("START_DATE", DateUtil.getSysdateStr("yyyy.MM.dd"));
		modelMap.put("END_DATE", "9999.12.31");

		return new ModelAndView("/pa/salarycode/viewAddPaItemFormulaInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaItemFormulaInfo")
	@ResponseBody
	public Map addPaItemFormulaInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.salaryCodeSer.addPaItemFormulaInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
			map.put("navTabId", "pa0801");
		} else if (result == 2) {
			map.put("statusCode", "300");
			map.put("message", "工资项目重复，请确认");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaItemFormulaInfo")
	@ResponseBody
	public Map deletePaItemFormulaInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.salaryCodeSer.deletePaItemFormulaInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			map.put("navTabId", "pa0801");
		} else {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
		}
		return map;
	}
}
