package com.ait.pa.action.bonus;

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
import com.ait.pa.service.bonus.BonusComputeItemParamSer;
import com.ait.pa.service.bonus.BonusComputeItemSer;
import com.ait.pa.service.bonus.BonusFormulaSer;
import com.ait.pa.service.bonus.BonusResultSer;
import com.ait.pa.service.insurance.InsuranceFormulaSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.GetMapByPaArUtil;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: BonusFormulaCtroller.java
 * @Description:
 * @Create date: 2012-1-17 下午03:18:12
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/bonus")
public class BonusFormulaCtroller {
	Logger logger = Logger.getLogger(BonusFormulaCtroller.class);

	@Autowired
	private BonusFormulaSer bonusFormulaSer;

	@Autowired
	private BonusComputeItemSer bonusComputeItemSer;

	@SuppressWarnings("unused")
	@Autowired
	private BonusComputeItemParamSer bonusComputeItemParamSer;

	@Autowired
	private BonusResultSer bonusResultSer;

	@SuppressWarnings("unused")
	@Autowired
	private CompanySer companySer;

	@Autowired
	private InsuranceFormulaSer insuranceFormulaSer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 奖金月别公式-并获得计算项目
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusFormula")
	public ModelAndView viewBonusFormula(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List itemList = this.bonusComputeItemSer
				.getBonusComputeItemList(request);
		modelMap.put("itemList", itemList);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/pa/bonus/viewBonusFormula", modelMap);
	}

	/**
	 * 点击左侧奖金计算项目名称-刷新右侧公式列表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusFormulaList")
	public ModelAndView getBonusFormulaList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List bonusFormulaList = this.bonusFormulaSer
				.getBonusFormulaList(request);
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
		GetMapByPaArUtil.formularToCN(bonusFormulaList, itemMap, "PA");

		modelMap.put("bonusFormulaList", bonusFormulaList);
		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2373"));

		return new ModelAndView("/pa/bonus/viewBonusFormulaList", modelMap);
	}

	/**
	 * 修改计算顺序CALCU_ORDER
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBnFormulaByConditionSeq")
	@ResponseBody
	public Map<String, Object> updateBnFormulaByConditionSeq(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int type = Integer.parseInt(request.getParameter("type"));
		String formular_no = request.getParameter("formular_no");
		String condition_seq = request.getParameter("condition_seq");
		String item_no = request.getParameter("item_no");

		int c = this.bonusFormulaSer.updateBnFormularByCalcuOrder(request,
				type, formular_no, condition_seq, item_no);

		Map<String, Object> jo = new HashMap<String, Object>();
		if (c == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			jo.put("rel", "jbsxViewBonusFormula");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}

		return jo;

	}

	/**
	 * 跳转到奖金月别公式添加页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBonusFormulaView")
	public ModelAndView addBonusFormulaView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));

		return new ModelAndView("/pa/bonus/addBonusFormulaView", modelMap);
	}

	/**
	 * 处理奖金月别公式保存请求
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBonusFormulaInfo")
	@ResponseBody
	public Map<String, Object> addBonusFormulaInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.bonusFormulaSer.addBonusFormulaInfo(request);

		if (result == 0) {
			map.put("statusCode", "200");
			map.put("rel", "jbsxViewBonusFormula");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
		} else {
			// TODO Auto-generated catch block
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 跳转到奖金月别公式修改页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBonusFormulaView")
	public ModelAndView updateBonusFormulaView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("formula", this.bonusFormulaSer
				.getBonusFormulaInfo(request));
		return new ModelAndView("/pa/bonus/updateBonusFormulaView", modelMap);
	}

	/**
	 * 处理奖金月别公式修改请求
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBonusFormulaInfo")
	@ResponseBody
	public Map<String, Object> updateBonusFormulaInfo(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.bonusFormulaSer.updateBonusFormulaInfo(request);
		if (result == 0) {
			map.put("statusCode", "200");
			map.put("rel", "jbsxViewBonusFormula");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}

		return map;
	}

	/**
	 * 处理奖金月别公式删除请求
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBonusFormulaInfo")
	@ResponseBody
	public Map<String, Object> deleteBonusFormulaInfo(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.bonusFormulaSer.deleteBonusFormulaInfo(request);
		if (result == 0) {
			map.put("statusCode", "200");
			map.put("rel", "jbsxViewBonusFormula");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));

		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));

		}
		return map;
	}

	/**
	 * 工具按钮跳转
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusFormularTool")
	public ModelAndView ViewFormularTool(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.addAllAttributes(this.bonusResultSer
				.getBonusResultAllItem(request));
		return new ModelAndView("/pa/bonus/viewBonusFormularTool", modelMap);
	}
}
