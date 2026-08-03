package com.ait.ess.action;

import java.util.ArrayList;
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

import com.ait.ess.dao.TransactionAffirmDao;
import com.ait.ess.service.TransactionAffirmSer;
import com.ait.hrm.action.TransferOrderCtroller;
import com.ait.hrm.dao.TransferOrderDao;
import com.ait.hrm.service.TransferOrderSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 调令决裁(Transaction Affirm)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: TransactionAffirmCtroller.java
 * @Description:
 * @Create date: Feb 20, 2012 11:10:35 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 20, 2012 11:10:35 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)viewOrderExamine
 */
@Controller
@RequestMapping(value = "/ess/trans")
public class TransactionAffirmCtroller {

	Logger logger = Logger.getLogger(TransferOrderCtroller.class);
	@Autowired
	private TransferOrderSer transferOrderSer;
	@Autowired
	private TransactionAffirmSer transactionAffirmSer;
	@Autowired
	private TransferOrderDao transferOrderDao;
	@Autowired
	private TransactionAffirmDao transactionAffirmDao;
	
	
	/**
	 * 入职决裁列表(entry transaction affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEntryTransAffirmList")
	public ModelAndView viewEntryTransAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("hrExperienceInsideList", this.transactionAffirmSer
				.getEntryTransAfiirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer
				.getEntryTransAfiirmListCnt(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("defaultPersonId", admin.getPersonId());

		if(request.getParameter("seach_STATUS_CODE")==null && ( modelMap.get("STATUS_CODE")==null || ((String)modelMap.get("STATUS_CODE")).equals(""))){
			modelMap.put("STATUS_CODE", "3530");
		}
		return new ModelAndView("/ess/trans/viewEntryTransAffirmList", modelMap);
	}

	/**
	 * 批量通过/否决入职发令(batch pass and reject entry transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveEntryTransInBatch")
	@ResponseBody
	public Map<String, Object> approveEntryTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.transactionAffirmSer
				.approveEntryTransInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess1001");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passEntryTransInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passEntryTransInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决入职发令(pass and reject entry transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveEntryTrans")
	@ResponseBody
	public Map<String, Object> approveEntryTrans(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer.approveEntryTrans(request);
		if (result == 1) {
			map.put("navTabId", "ess1001");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passEntryTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passEntryTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 查看薪资调整决裁列表(view salary adjustment transaction affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaAdjustTransAffirmList")
	public ModelAndView viewPaAdjustTransAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("paAdjustList", this.transactionAffirmSer
				.getPaAdjustTransAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer
				.getPaAdjustTransAffirmListCnt(request));
		return new ModelAndView("/ess/trans/viewPaAdjustTransAffirmList",
				modelMap);
	}

	/**
	 * 批量通过/否决薪资调整发令(batch pass and reject salary adjustment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveSalaryAdjustTransInBatch")
	@ResponseBody
	public Map<String, Object> approveSalaryAdjustTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approveSalaryAdjustTransInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess1002");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passSalaryTransInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passSalaryTransInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决薪资调整发令(batch pass and reject salary adjustment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveSalaryAdjustTrans")
	@ResponseBody
	public Map<String, Object> approveSalaryAdjustTrans(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approveSalaryAdjustTrans(request);
		if (result == 1) {
			map.put("navTabId", "ess1002");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passSalaryTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passSalaryTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 查看转正决裁列表(view probation transaction affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewProbationTransAffirmList")
	public ModelAndView viewProbationTransAffirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("probationList", this.transactionAffirmSer
				.getProbationTransAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer
				.getProbationTransAffirmListCnt(request));
		return new ModelAndView("/ess/trans/viewProbationTransAffirmList",
				modelMap);
	}

	/**
	 * 批量通过/否决转正调令(batch pass and reject Probation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveProbationTransInBatch")
	@ResponseBody
	public Map<String, Object> approveProbationTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approveProbationTransInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess1003");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passProbTransInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passProbTransInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决转正调令(batch pass and reject Probation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveProbationTrans")
	@ResponseBody
	public Map<String, Object> approveProbationTrans(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer.approveProbationTrans(request);
		if (result == 1) {
			map.put("navTabId", "ess1003");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passProbTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passProbTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 代理决裁列表(view agent transaction affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAgentTransAffirmList")
	public ModelAndView viewAgentTransAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("agentTransList", this.transactionAffirmSer
				.getAgentTransAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer
				.getAgentTransAffirmListCnt(request));
		return new ModelAndView("/ess/trans/viewAgentTransAffirmList", modelMap);
	}

	/**
	 * 批量通过/否决代理调令(batch pass and reject agent transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveAgentTransInBatch")
	@ResponseBody
	public Map<String, Object> approveAgentTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approveAgentTransInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess1004");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passAgentTransInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passAgentTransInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决代理调令(pass and reject agent transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveAgentTrans")
	@ResponseBody
	public Map<String, Object> approveAgentTrans(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer.approveAgentTrans(request);
		if (result == 1) {
			map.put("navTabId", "ess1004");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passAgentTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passAgentTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 奖励决裁列表(view reward transaction affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewRewardTransAffirmList")
	public ModelAndView viewRewardTransAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("rewardTransList", this.transactionAffirmSer
				.getRewardTransAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer
				.getRewardTransAffirmListCnt(request));
		if(request.getParameter("seach_STATUS_CODE")==null && ( modelMap.get("STATUS_CODE")==null || ((String)modelMap.get("STATUS_CODE")).equals(""))){
			modelMap.put("STATUS_CODE", "3530");
		}
		return new ModelAndView("/ess/trans/viewRewardTransAffirmList",
				modelMap);
	}

	/**
	 * 批量通过/否决奖励调令(batch pass and reject Reward transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveRewardTransInBatch")
	@ResponseBody
	public Map<String, Object> approveRewardTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approveRewardTransInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess1005");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passRewardTransInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passRewardTransInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决奖励调令(pass and reject Reward transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveRewardTrans")
	@ResponseBody
	public Map<String, Object> approveRewardTrans(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer.approveRewardTrans(request);
		if (result == 1) {
			map.put("navTabId", "ess1005");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passRewardTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passRewardTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 惩戒决裁列表(view punishment transaction affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPunishmentTransAffirmList")
	public ModelAndView viewPunishmentTransAffirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("punishmentTransList", this.transactionAffirmSer
				.getPunishmentTransAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer
				.getPunishmentTransAffirmListCnt(request));
		if(request.getParameter("seach_STATUS_CODE")==null && ( modelMap.get("STATUS_CODE")==null || ((String)modelMap.get("STATUS_CODE")).equals(""))){
			modelMap.put("STATUS_CODE", "3530");
		}
		return new ModelAndView("/ess/trans/viewPunishmentTransAffirmList",
				modelMap);
	}

	/**
	 * 批量通过/否决惩戒调令(batch pass and reject punishment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approvePunishmentTransInBatch")
	@ResponseBody
	public Map<String, Object> approvePunishmentTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approvePunishmentTransInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess1006");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passPunishTransInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passPunishTransInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决惩戒调令(pass and reject Punishment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approvePunishmentTrans")
	@ResponseBody
	public Map<String, Object> approvePunishmentTrans(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer.approvePunishmentTrans(request);
		if (result == 1) {
			map.put("navTabId", "ess1006");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passPunishTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passPunishTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 离职决裁列表(view resignation transaction affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResignationTransAffirmList")
	public ModelAndView viewResignationTransAffirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("resignationTransList", this.transactionAffirmSer
				.getResignationTransAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer
				.getResignationTransAffirmListCnt(request));
		return new ModelAndView("/ess/trans/viewResignationTransAffirmList",
				modelMap);
	}

	/**
	 * 批量通过/否决离职调令(batch pass and reject Resignation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveResignationTransInBatch")
	@ResponseBody
	public Map<String, Object> approveResignationTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approveResignationTransInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess1007");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passResignTransInBatch_success",request));
			map.put("statusCode", "200");
			map.put("callbackType", "forward");
			map.put("forwardUrl", "/ess/trans/viewResignationTransAffirmList");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passResignTransInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 批量通过/否决离职调令(batch pass and reject Resignation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveResignationTrans")
	@ResponseBody
	public Map<String, Object> approveResignationTrans(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer.approveResignationTrans(request);
		if (result == 1) {
			map.put("navTabId", "ess1007");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passResignTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passResignTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 调动决裁列表(view transaction transaction affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTransferTransAffirmList")
	public ModelAndView viewTransferTransAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("hrExperienceInsideList", this.transactionAffirmSer
				.getTransferTransAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer
				.getTransferTransAffirmListCnt(request));

		return new ModelAndView("/ess/trans/viewTransferTransAffirmList",
				modelMap);
	}

	/**
	 * 批量通过/否决调动发令(batch pass and reject Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveTransactionTransInBatch")
	@ResponseBody
	public Map<String, Object> approveTransactionTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approveTransactionTransInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess1008");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passTransacTransInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passTransacTransInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决调动发令(pass and reject Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveTransactionTrans")
	@ResponseBody
	public Map<String, Object> approveTransactionTrans(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer.approveTransactionTrans(request);
		if (result == 1) {
			map.put("navTabId", "ess1014");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passTransacTrans_success",request));
			map.put("statusCode", "200");
		}else if(result == 2){
			map.put("message", TipMessage.getTipMessage("alert.ess.message.isYesDiaoling",request));
			map.put("statusCode", "300");
		}
		else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passTransacTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 升职/降职决裁列表(view promotion and relegation transaction affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPromotRelegatTransAffirmList")
	public ModelAndView viewPromotRelegatTransAffirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("proReleTransList", this.transactionAffirmSer
				.getPromotRelegatTransAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer
				.getPromotRelegatTransAffirmListCnt(request));
		return new ModelAndView("/ess/trans/viewPromotRelegatTransAffirmList",
				modelMap);
	}

	/**
	 * 批量通过/否决升职/降职调令(batch pass and reject PromotRelegat transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approvePromotRelegatTransInBatch")
	@ResponseBody
	public Map<String, Object> approvePromotRelegatTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approvePromotRelegatTransInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess1009");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passProRelTransInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passProRelTransInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 批量通过/否决升职/降职调令(batch pass and reject PromotRelegat transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approvePromotRelegatTrans")
	@ResponseBody
	public Map<String, Object> approvePromotRelegatTrans(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approvePromotRelegatTrans(request);
		if (result == 1) {
			map.put("navTabId", "ess1009");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passProRelTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passProRelTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 兼职决裁(view plurality transaction affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPluralityTransAffirmList")
	public ModelAndView viewPluralityTransAffirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("pluralityTransList", this.transactionAffirmSer
				.getPluralityTransAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer
				.getPluralityTransAffirmListCnt(request));
		return new ModelAndView("/ess/trans/viewPluralityTransAffirmList",
				modelMap);
	}

	/**
	 * 批量通过/否决兼职调令(batch pass and reject Plurality transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approvePluralityTransInBatch")
	@ResponseBody
	public Map<String, Object> approvePluralityTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approvePluralityTransInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess1010");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passPluralityTransInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passPluralityTransInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决兼职调令(pass and reject Plurality transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approvePluralityTrans")
	@ResponseBody
	public Map<String, Object> approvePluralityTrans(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer.approvePluralityTrans(request);
		if (result == 1) {
			map.put("navTabId", "ess1010");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passPluralityTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passPluralityTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 停职/复职决裁列表(view suspension transaction affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSuspensionTransAffirmList")
	public ModelAndView viewSuspensionTransAffirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("suspensionTransList", this.transactionAffirmSer
				.getSuspensionTransAffirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer
				.getSuspensionTransAffirmListCnt(request));
		return new ModelAndView("/ess/trans/viewSuspensionTransAffirmList",
				modelMap);
	}

	/**
	 * 批量通过/否决停职/复职调令(batch pass and reject Suspension transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveSuspensionTransInBatch")
	@ResponseBody
	public Map<String, Object> approveSuspensionTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approveSuspensionTransInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess1011");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passSuspensionTransInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passSuspensionTransInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 通过/否决 停职/复职调令(pass and reject Suspension transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveSuspensionTrans")
	@ResponseBody
	public Map<String, Object> approveSuspensionTrans(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer.approveSuspensionTrans(request);
		if (result == 1) {
			map.put("navTabId", "ess1011");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passSuspensionTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passSuspensionTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 *查看号俸决裁
	 */

		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/viewPayStepTransAffirmList")
		public ModelAndView viewPayStepTransAffirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			modelMap.put("defaultCpny", admin.getCpnyId());
			modelMap.put("payStepList", this.transactionAffirmSer
				.getPayStepAffirmList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer
				.getPayStepTransAffirmListCnt(request));
			return new ModelAndView("/ess/trans/viewPayStepTransAffirmList",
				modelMap);
	}
		
	/**
	 * 批量通过/否决号俸发令
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approvePayStepTransInBatch")
	@ResponseBody
	public Map<String, Object> approvePayStepTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approvePayStepTransInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess1009");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passProRelTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passProRelTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}
	//通过号俸发令
	@RequestMapping(value = "/approvePayStepTrans")
	@ResponseBody
	public Map<String, Object> approvePayStepTrans(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionAffirmSer
				.approvePayStepTrans(request);
		if (result == 1) {
			map.put("navTabId", "ess1009");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passProRelTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passProRelTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}
		/**
		 * 异动决裁列表(view transaction transaction affirm list)
		 * 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")					  
		@RequestMapping(value = "/viewOrderExamine")
		public ModelAndView viewOrderExamineList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			
			modelMap.put("defaultCpny", admin.getCpnyId());
			modelMap.put("defaultPersonId", admin.getPersonId());
			modelMap.put("transCodeFromQuickMenu", request.getParameter("transCodeFromQuickMenu")==null ? "" : (String)request.getParameter("transCodeFromQuickMenu"));
		
			LinkedHashMap paramMap1 = ObjectBindUtil.getRequestParamData(request,"seach_");
			//logger.debug("jjy:::paramMap1:::"+paramMap1);
			if(request.getParameter("seach_OrderType")!=null&&!request.getParameter("seach_OrderType").equals("")){
				paramMap1.put("CODE", request.getParameter("seach_OrderType"));
				paramMap1.put("LAN",admin.getLanguage());
				paramMap1.put("CPNYID", admin.getCpnyId());
				paramMap1.put("TRANS_SEARCH_FLAG", "1");
				List listTitle=this.transferOrderDao.getTranferOrderTitileInside(paramMap1);
				modelMap.put("listTitle", listTitle);
				List insideList=this.transactionAffirmSer.ViewHrExpInsideList(request);
				//modelMap.put(UiUtil.TOTAL_COUNT_NAME, thapproveTransactionTransis.TransferOrderSer.getTranferOrderinsideListCnt(request,paramMap1));
				int insideListCnt=this.transactionAffirmSer.ViewHrExpInsideListCnt(request);
				modelMap.put(UiUtil.TOTAL_COUNT_NAME, insideListCnt);
				modelMap.put("insideList", insideList);
				modelMap.put("seach_OrderType", request.getParameter("seach_OrderType") == null ? "" : request.getParameter("seach_OrderType"));
			}
			
			//최초 결재목록 화면 오픈시 결재상태 항목을 미결재로 강제 지정
			if(request.getParameter("seach_STATUS_CODE")==null && ( modelMap.get("STATUS_CODE")==null || ((String)modelMap.get("STATUS_CODE")).equals(""))){
				modelMap.put("STATUS_CODE", "3530");
			}
			//logger.debug("jjy:::modelMap:::"+modelMap);
			return new ModelAndView("/ess/trans/viewOrderExamine",modelMap);
		}
		

		
	/**
	 * 选择显示"奖励决裁"或"惩戒决裁"
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-1 下午01:48:51 
	* @version V1.0
	 */
	@RequestMapping("/viewRewardAndPunishmentTransAffirmList")
	public ModelAndView viewRewardAndPunishmentTransAffirmList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String tag=request.getParameter("searchTransferOrderAffirm");
		modelMap.put("tag", tag);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("defaultPersonId", admin.getPersonId());
		modelMap.put("transCodeFromQuickMenu", request.getParameter("transCodeFromQuickMenu")==null ? "" : (String)request.getParameter("transCodeFromQuickMenu"));
		
		String transferOrderType=request.getParameter("transferOrderAffirmType");
		if("reward".equals(transferOrderType) || "1".equals(tag)){
			modelMap.put("rewardTransList", this.transactionAffirmSer.getRewardTransAffirmList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer.getRewardTransAffirmListCnt(request));
		}
		if("punishment".equals(transferOrderType) || "2".equals(tag)){
			modelMap.put("punishmentTransList", this.transactionAffirmSer.getPunishmentTransAffirmList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionAffirmSer.getPunishmentTransAffirmListCnt(request));
		}
		modelMap.put("DEPT_NO", request.getParameter("DEPT_NO"));
		modelMap.put("KEY", request.getParameter("KEY"));
		modelMap.put("STATUS_CODE", request.getParameter("STATUS_CODE"));
		modelMap.put("TRANS_CODE", request.getParameter("TRANS_CODE"));
		modelMap.put("REWARD_FROM_DATE", request.getParameter("REWARD_FROM_DATE"));
		modelMap.put("REWARD_TO_DATE", request.getParameter("REWARD_TO_DATE"));
		modelMap.put("DATE_FROM_PUNISHED", request.getParameter("DATE_FROM_PUNISHED"));
		modelMap.put("DATE_TO_PUNISHED", request.getParameter("DATE_TO_PUNISHED"));
		if(request.getParameter("seach_STATUS_CODE")==null && ( modelMap.get("STATUS_CODE")==null || ((String)modelMap.get("STATUS_CODE")).equals(""))){
			modelMap.put("STATUS_CODE", "3530");
		}
		return new ModelAndView("/ess/trans/viewRewardAndPunishmentTransAffirmList",modelMap);
	}
	/**
	 * 通过/否决 调令 发令(batch pass and reject Examine transaction)
	 * 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author heran heran@ait.net.cn
	* @date 2013-9-1 下午02:13:51 
	* @version V1.0
	 */
	@RequestMapping(value = "/examineTrans")
	@ResponseBody
	public Map<String, Object> examineTrans(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedHashMap paramMap1 = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		String examineType=paramMap1.get("seach_OrderType").toString();
		int result = this.transactionAffirmSer.approveSalaryAdjustTrans(request);
		//兼职
		if(examineType.equals("123314")){
			
		}
		//兼职解除
		else if(examineType.equals("123315")){
			
		}
		//职级升级(职等)
		else if(examineType.equals("123316")){
			
		}
		//职责晋升
		else if(examineType.equals("123317")){
			
		}
		//休职
		else if(examineType.equals("1233178")){
		
		}
		//复职
		else if(examineType.equals("123346")){
			
		}
		//转正
		else if(examineType.equals("123347")){
			
		}
		//异动
		else if(examineType.equals("123348")){
			
		}
		//保职
		else if(examineType.equals("123349")){
			
		}
		//组织变更
		else if(examineType.equals("123350")){
			
		}
		//职级变更
		else if(examineType.equals("123351")){
			
		}
		//代理
		else if(examineType.equals("123352")){
			
		}
		//代理解除
		else if(examineType.equals("123353")){
			
		}
		//离职
		else if(examineType.equals("123356")){
			
		}
		//借调
		else if(examineType.equals("123357")){
			
		}
		//借调解除
		else if(examineType.equals("123358")){
			
		}
		//待处理
		else if(examineType.equals("123359")){
			
		}
		//待处理解除
		else if(examineType.equals("123360")){
			
		}else if(examineType.equals("")){
			
		}
		
		
		
		if (result == 1) {
			map.put("navTabId", "ess1014");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passSalaryTrans_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.trans.passSalaryTrans_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

}

