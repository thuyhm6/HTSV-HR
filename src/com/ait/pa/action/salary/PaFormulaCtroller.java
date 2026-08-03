package com.ait.pa.action.salary;

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
import com.ait.pa.service.insurance.InsuranceFormulaSer;
import com.ait.pa.service.salary.PaComputeItemSer;
import com.ait.pa.service.salary.PaFormulaSer;
import com.ait.pa.service.salary.PaResultSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.GetMapByPaArUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: PaFormulaCtroller.java
 * @Description:
 * @Create date: 2012-5-31 上午10:40:38
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/salary")
public class PaFormulaCtroller {
	Logger logger = Logger.getLogger(PaFormulaCtroller.class);

	@Autowired
	private PaFormulaSer paFormulaSer;

	@Autowired
	private PaResultSer paResultSer;

	@Autowired
	private PaComputeItemSer paComputeItemSer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private InsuranceFormulaSer insuranceFormulaSer;

	/**
	 * 跳转到月别公式主页面并根据当前登录人的公司id(CNPY_ID)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaFormula")
	public ModelAndView viewPaFormula(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		// 查询出pa_item表里面的数据根据登录人的cnpy_id
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List paItemList = paComputeItemSer.getPaItemListForFormula(request);

		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("paItemList", paItemList);
		modelMap.put("menuNo", request.getParameter("menuNo"));
		return new ModelAndView("/pa/salary/viewPaFormula", modelMap);
	}

	/**
	 * 跳转到月别公式主页面并根据当前登录人的公司id(CNPY_ID)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaDayFormula")
	public ModelAndView viewPaDayFormula(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		// 查询出pa_item表里面的数据根据登录人的cnpy_id
		List paDayItemList = paComputeItemSer
				.getPaItemListForDayFormula(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		modelMap.put("paItemList", paDayItemList);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("menuNo", request.getParameter("menuNo"));
		return new ModelAndView("/pa/salary/viewPaDayFormula", modelMap);
	}

	/**
	 * 根据项目号PA_ITEM_NO查询出PA_FORMULAR表里面的记录
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaFormulaList")
	public ModelAndView getPaFormulaList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paFormulaList = this.paFormulaSer.getPaFormulaList(request);
		List insuranceFormulaForCNList = this.insuranceFormulaSer
				.getInsuranceFormulaForCNList(request);
		Map<String, String> itemMap = new HashMap<String, String>();

		for (int i = 0; i < insuranceFormulaForCNList.size(); i++) {
			if (((Map) insuranceFormulaForCNList.get(i)).get("ID") != null
					&& ((Map) insuranceFormulaForCNList.get(i)).get("NAME") != null) {
				itemMap.put(((Map) insuranceFormulaForCNList.get(i)).get("ID")
						.toString(), ((Map) insuranceFormulaForCNList.get(i))
						.get("NAME").toString());
			}
		}
		GetMapByPaArUtil.formularToCN(paFormulaList, itemMap, "PA");

		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("ITEM_NAME", request.getParameter("ITEM_NAME"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
		modelMap.put("paFormulaList", paFormulaList);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2409"));

		return new ModelAndView("/pa/salary/viewPaFormulaList", modelMap);
	}

	/**
	 * 修改计算顺序CALCU_ORDER
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaFormulaByConditionSeq")
	@ResponseBody
	public Map<String, Object> updatePaFormulaByConditionSeq(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int type = Integer.parseInt(request.getParameter("type"));
		String formular_no = request.getParameter("formular_no");
		String condition_seq = request.getParameter("condition_seq");
		String item_no = request.getParameter("item_no");

		int c = this.paFormulaSer.updatePaFormularByCalcuOrder(request, type,
				formular_no, condition_seq, item_no);

		Map<String, Object> jo = new HashMap<String, Object>();
		if (c == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			jo.put("rel", "viewPaFormulaData");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}

		return jo;

	}

	/**
	 * 根据项目号PA_ITEM_NO查询出PA_FORMULAR表里面的记录
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaDayFormulaList")
	public ModelAndView getPaDayFormulaList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paDayFormulaList = this.paFormulaSer.getPaDayFormulaList(request);
		List insuranceFormulaForCNList = this.insuranceFormulaSer
				.getInsuranceFormulaForCNList(request);
		Map<String, String> itemMap = new HashMap<String, String>();
		for (int i = 0; i < insuranceFormulaForCNList.size(); i++) {
			if (((Map) insuranceFormulaForCNList.get(i)).get("ID") != null
					&& ((Map) insuranceFormulaForCNList.get(i)).get("NAME") != null) {
				itemMap.put(((Map) insuranceFormulaForCNList.get(i)).get("ID")
						.toString(), ((Map) insuranceFormulaForCNList.get(i))
						.get("NAME").toString());
			}
		}
		GetMapByPaArUtil.formularToCN(paDayFormulaList, itemMap, "PA");

		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
		modelMap.put("paDayFormulaList", paDayFormulaList);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2521"));

		return new ModelAndView("/pa/salary/viewPaDayFormulaList", modelMap);
	}

	/**
	 * 修改计算顺序CALCU_ORDER
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaDayFormulaByConditionSeq")
	@ResponseBody
	public Map<String, Object> updatePaDayFormulaByConditionSeq(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int type = Integer.parseInt(request.getParameter("type"));
		String formular_no = request.getParameter("formular_no");
		String condition_seq = request.getParameter("condition_seq");
		String item_no = request.getParameter("item_no");

		int c = this.paFormulaSer.updatePaDayFormularByCalcuOrder(request,
				type, formular_no, condition_seq, item_no);

		Map<String, Object> jo = new HashMap<String, Object>();
		if (c == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			jo.put("rel", "viewPaDayFormulaData");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return jo;
	}

	/**
	 * 跳转到添加页面并且吧PA_ITEM_NO带入到添加页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaFormulaView")
	public ModelAndView addPaFormulaView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));

		return new ModelAndView("/pa/salary/addPaFormulaView", modelMap);
	}

	/**
	 * 跳转到添加页面并且吧PA_ITEM_NO带入到添加页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaDayFormulaView")
	public ModelAndView addPaDayFormulaView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));

		return new ModelAndView("/pa/salary/addPaDayFormulaView", modelMap);
	}

	/**
	 * 查询出所有和公式有关的列表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaFormularTool")
	public ModelAndView viewFormularTool(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.addAllAttributes(this.paResultSer.getPaResultAllItem(request));

		return new ModelAndView("/pa/salary/viewPaFormularTool", modelMap);
	}

	/**
	 * 查询出所有和公式有关的列表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaDayFormularTool")
	public ModelAndView viewDayFormularTool(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.addAllAttributes(this.paResultSer.getPaResultAllItem(request));

		return new ModelAndView("/pa/salary/viewPaDayFormularTool", modelMap);
	}

	/**
	 * 执行添加
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaFormulaInfo")
	@ResponseBody
	public Map<String, Object> addPaFormulaInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.paFormulaSer.addPaFormulaInfo(request);
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("rel", "viewPaFormulaData");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}

		return map;
	}

	/**
	 * 执行添加
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaDayFormulaInfo")
	@ResponseBody
	public Map<String, Object> addPaDayFormulaInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.paFormulaSer.addPaDayFormulaInfo(request);
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("rel", "viewPaDayFormulaData");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}

		return map;
	}

	/**
	 * 跳转到修改页面 根据FORMULAR_NO查询出PA_FORMULAR对象(paFormulaInfo)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaFormulaView")
	public ModelAndView updatePaFormulaView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Object paFormulaInfo = this.paFormulaSer.getPaFormulaInfo(request);

		modelMap.put("paFormulaInfo", paFormulaInfo);
		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));

		return new ModelAndView("/pa/salary/updatePaFormulaView", modelMap);
	}

	/**
	 * 跳转到修改页面 根据FORMULAR_NO查询出PA_FORMULAR对象(paFormulaInfo)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaDayFormulaView")
	public ModelAndView updatePaDayFormulaView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Object paDayFormulaInfo = this.paFormulaSer
				.getPaDayFormulaInfo(request);

		modelMap.put("paFormulaInfo", paDayFormulaInfo);
		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));

		return new ModelAndView("/pa/salary/updatePaDayFormulaView", modelMap);
	}

	/**
	 * 执行修改
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaFormulaInfo")
	@ResponseBody
	public Map<String, Object> updatePaFormulaInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.paFormulaSer.updatePaFormulaInfo(request);
		try {
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("rel", "viewPaFormulaData");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 执行修改
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaDayFormulaInfo")
	@ResponseBody
	public Map<String, Object> updatePaDayFormulaInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.paFormulaSer.updatePaDayFormulaInfo(request);
		try {
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("rel", "viewPaDayFormulaData");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 执行删除 根据FORMULAR_NO执行删除
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePaFormulaInfo")
	@ResponseBody
	public Map<String, Object> deletePaFormulaInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.paFormulaSer.deletePaFormulaInfo(request);
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("rel", "viewPaFormulaData");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	/**
	 * 执行删除 根据FORMULAR_NO执行删除
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePaDayFormulaInfo")
	@ResponseBody
	public Map<String, Object> deletePaDayFormulaInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.paFormulaSer.deletePaDayFormulaInfo(request);
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("rel", "viewPaDayFormulaData");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPaFormulaList")
	@ResponseBody
	public List getPaFormulaList(HttpServletRequest request) throws Exception {

		List paFormulaList = this.paFormulaSer.getPaFormulaList(request);

		return paFormulaList;
	}

}
