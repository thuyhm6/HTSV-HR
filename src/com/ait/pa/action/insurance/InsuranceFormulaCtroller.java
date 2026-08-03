package com.ait.pa.action.insurance;

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
import com.ait.pa.service.insurance.InsuranceComputeItemSer;
import com.ait.pa.service.insurance.InsuranceFormulaSer;
import com.ait.pa.service.insurance.InsuranceResultSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.GetMapByPaArUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceFormulaCtroller.java
 * @Description:
 * @Create date: 2012-1-17 下午03:10:04
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/insurance")
public class InsuranceFormulaCtroller {
	Logger logger = Logger.getLogger(InsuranceFormulaCtroller.class);
	
	@Autowired
	private InsuranceFormulaSer insuranceFormulaSer ;
	
	@Autowired
	private InsuranceResultSer insuranceResultSer ;
	
	@Autowired
	private InsuranceComputeItemSer isComputeItemSer ;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	/**
	 * 保险计算公式-并获得计算项目(view Insurance Formula)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInsuranceFormula")
	public ModelAndView viewInsuranceFormula(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List itemList = this.isComputeItemSer
				.getInsuranceComputeItemParamListForFormula(request);
		modelMap.put("itemList", itemList);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("menuNo", request.getParameter("menuNo"));
		
		return new ModelAndView("/pa/insurance/viewInsuranceFormula",modelMap);
	}
	
	/**
	 * 点击左侧保险计算项目名称-刷新右侧公式列表(view Insurance Formula List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInsuranceFormulaList")
	public ModelAndView getInsuranceFormulaList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List insuranceFormulaList = this.insuranceFormulaSer
				.getInsuranceFormulaList(request);
		List insuranceFormulaForCNList = this.insuranceFormulaSer.getInsuranceFormulaForCNList(request);
		Map<String, String> itemMap = new HashMap<String, String>();
		for(int i = 0; i < insuranceFormulaForCNList.size(); i++){
			if (((Map)insuranceFormulaForCNList.get(i)).get("ID")!= null && ((Map)insuranceFormulaForCNList.get(i)).get("NAME") != null) {
				itemMap.put(((Map)insuranceFormulaForCNList.get(i)).get("ID").toString(), ((Map)insuranceFormulaForCNList.get(i)).get("NAME").toString());
			}
		}
		GetMapByPaArUtil.formularToCN(insuranceFormulaList, itemMap, "PA");
		modelMap.put("insuranceFormulaList", insuranceFormulaList);
		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2548")) ;

		return new ModelAndView("/pa/insurance/viewInsuranceFormulaList", modelMap);
	}
	
	/**
	 * 修改计算顺序CALCU_ORDER
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateIsFormulaByConditionSeq")
	@ResponseBody
	public Map<String,Object> updateIsFormulaByConditionSeq(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int type = Integer.parseInt(request.getParameter("type"));
		String formular_no = request.getParameter("formular_no");
		String condition_seq = request.getParameter("condition_seq");
		String item_no = request.getParameter("item_no");

		int c = this.insuranceFormulaSer.updateIsFormularByCalcuOrder(request, type,
				formular_no, condition_seq,item_no);


		Map<String, Object> jo = new HashMap<String, Object>();
		if (c == 1) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			jo.put("rel", "ViewInsuranceFormula");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}

		return jo;

	}
	
	/**
	 * 跳转到添加保险公式页面（add Insurance Formula View）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceFormulaView")
	public ModelAndView addInsuranceFormulaView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
		
		return new ModelAndView("/pa/insurance/addInsuranceFormulaView",modelMap);
	}
	
	/**
	 * 查看公式工具（view Insurance Formular Tool）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInsuranceFormularTool")
	public ModelAndView viewFormularTool(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.addAllAttributes(this.insuranceResultSer.getInsuranceResultAllItem(request)) ;
		
		return new ModelAndView("/pa/insurance/viewInsuranceFormularTool",modelMap);
	}
	
	/**
	 * 添加保险计算公式（add Insurance Formula Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceFormulaInfo")
	@ResponseBody
	public Map<String, Object> addInsuranceFormulaInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.insuranceFormulaSer.addInsuranceFormulaInfo(request);

		if (result == 0) {
			map.put("statusCode", "200");
			map.put("rel", "ViewInsuranceFormula");
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
	 * 跳转到修改保险计算公式（update Insurance Formula View）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceFormulaView")
	public ModelAndView updateInsuranceFormulaView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		Object insuranceFormulaInfo = this.insuranceFormulaSer.getInsuranceFormulaInfo(request) ;
		
		modelMap.put("insuranceFormulaInfo", insuranceFormulaInfo) ;
		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
		
		return new ModelAndView("/pa/insurance/updateInsuranceFormulaView",modelMap);
	}
	
	/**
	 * 修改保险计算公式（update Insurance Formula Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceFormulaInfo")
	@ResponseBody
	public Map<String, Object> updateInsuranceFormulaInfo(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.insuranceFormulaSer.updateInsuranceFormulaInfo(request) ;

		if (result == 0) {
			map.put("statusCode", "200");
			map.put("rel", "ViewInsuranceFormula");
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
	 * 删除保险计算公式（delete Insurance Formula Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteInsuranceFormulaInfo")
	@ResponseBody
	public Map<String, Object> deleteInsuranceFormulaInfo(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.insuranceFormulaSer.deleteInsuranceFormulaInfo(request) ;
		if (result == 0) {
			map.put("statusCode", "200");
			map.put("rel", "ViewInsuranceFormula"); 
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}
}
