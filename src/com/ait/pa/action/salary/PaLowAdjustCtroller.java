package com.ait.pa.action.salary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
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

import com.ait.pa.service.salary.PaLowAdjustSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/pa/salary")
public class PaLowAdjustCtroller {
	Logger logger = Logger.getLogger(PaLowAdjustCtroller.class);

	@Autowired
	private PaLowAdjustSer paLowAdjustSer;

	/**
	 * 查看最低工资调整页面-促销(view low salary)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLowSalaryList")
	public ModelAndView viewLowSalaryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String paYear = request.getParameter("seach_paYear");
		if (null == paYear || "".equals(paYear)) {
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
			modelMap.put("paYear", timeFormatter.format(Calendar.getInstance()
					.getTime()));
			timeFormatter = new SimpleDateFormat("MM");
			modelMap.put("paMonth", timeFormatter.format(Calendar.getInstance()
					.getTime()));
		}

		modelMap.put("defaultCpny", admin.getCpnyId());
		// TA法人特殊处理
		if (null != admin.getCpnyId() && !"".equals(admin.getCpnyId())
				&& "SST".equals(admin.getCpnyId())) {
			modelMap.put("paLowAdjustList", this.paLowAdjustSer
					.getLowSalaryForTAList(request));
		} else {
			modelMap.put("paLowAdjustList", this.paLowAdjustSer
					.getLowSalaryList(request));
		}
//		modelMap.put("paLowAdjustList", this.paLowAdjustSer
//				.getLowSalaryList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paLowAdjustSer
				.getLowSalaryListCnt(request));

		return new ModelAndView("/pa/salary/viewLowSalaryList", modelMap);
	}

	/**
	 * 批量提交最低工资调整(update Low Salary Batch)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateLowSalaryBatch")
	@ResponseBody
	public Map<String, Object> updateLowSalaryBatch(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.paLowAdjustSer.updateLowSalaryBatch(request);
		if (result == 1) {
			map.put("navTabId", "pa0702");
			map.put("message", TipMessage.getTipMessage(
					"pa.low.adjust.zuidigongzitiaozhengpiliangcaozuochenggong",
					request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage(
					"pa.low.adjust.zuidigongzitiaozhengpiliangcaozuoshibai",
					request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 撤销调整(cancel low adjust)
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancellowadjust")
	@ResponseBody
	public Map cancellowadjust(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.paLowAdjustSer.cancellowadjust(request);

		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"pa.low.adjust.chexiaochenggong", request));// 撤销成功!
			map.put("navTabId", "pa0702");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"pa.low.adjust.chexiaoshibai", request));// 撤销失败!
		}

		return map;
	}

	/**
	 * 查看最低工资调整页面-历史(view low history salary)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLowSalaryHistoryList")
	public ModelAndView viewLowSalaryHistoryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String paYear = request.getParameter("seach_paYear");
		if (null == paYear || "".equals(paYear)) {
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
			modelMap.put("paYear", timeFormatter.format(Calendar.getInstance()
					.getTime()));
			timeFormatter = new SimpleDateFormat("MM");
			modelMap.put("paMonth", timeFormatter.format(Calendar.getInstance()
					.getTime()));
		}

		modelMap.put("defaultCpny", admin.getCpnyId());
		// TA法人特殊处理
		if (null != admin.getCpnyId() && !"".equals(admin.getCpnyId())
				&& "SST".equals(admin.getCpnyId())) {
			modelMap.put("paLowAdjustList", this.paLowAdjustSer
					.getLowSalaryForTAList(request));
		} else {
			modelMap.put("paLowAdjustList", this.paLowAdjustSer
					.getLowSalaryList(request));
		}

		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paLowAdjustSer
				.getLowSalaryListCnt(request));

		return new ModelAndView("/pa/salary/viewLowSalaryHistoryList", modelMap);
	}

	/**
	 * 查看最低工资调整页面-非促销(view low salary)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFeiCuLowSalaryList")
	public ModelAndView viewFeiCuLowSalaryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String paYear = request.getParameter("seach_paYear");
		if (null == paYear || "".equals(paYear)) {
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
			modelMap.put("paYear", timeFormatter.format(Calendar.getInstance()
					.getTime()));
			timeFormatter = new SimpleDateFormat("MM");
			modelMap.put("paMonth", timeFormatter.format(Calendar.getInstance()
					.getTime()));
		}

		modelMap.put("defaultCpny", admin.getCpnyId());
		// TA法人特殊处理
		if (null != admin.getCpnyId() && !"".equals(admin.getCpnyId())
				&& "SST".equals(admin.getCpnyId())) {
			modelMap.put("paLowAdjustList", this.paLowAdjustSer
					.getFeiCuLowSalaryForTAList(request));
		} else if (null != admin.getCpnyId() && !"".equals(admin.getCpnyId()) 
				&& "LGETR".equals(admin.getCpnyId())){
			modelMap.put("paLowAdjustList", this.paLowAdjustSer
					.getFeiCuLowSalaryForTRList(request));
		}
		else {
			modelMap.put("paLowAdjustList", this.paLowAdjustSer
					.getFeiCuLowSalaryList(request));
		}
//		modelMap.put("paLowAdjustList", this.paLowAdjustSer
//				.getFeiCuLowSalaryList(request));
	    if (null != admin.getCpnyId() && !"".equals(admin.getCpnyId()) 
				&& "LGETR".equals(admin.getCpnyId())){
	    	modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paLowAdjustSer
					.getFeiCuLowSalaryListTRCnt(request));
		}
		else {
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paLowAdjustSer
					.getFeiCuLowSalaryListCnt(request));
		}
		return new ModelAndView("/pa/salary/viewFeiCuLowSalaryList", modelMap);
	}

	/**
	 * 批量提交最低工资调整-非促销员(update Low Salary Batch)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateFeiCuLowSalaryBatch")
	@ResponseBody
	public Map<String, Object> updateFeiCuLowSalaryBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.paLowAdjustSer.updateLowSalaryBatch(request);
		if (result == 1) {
			map.put("navTabId", "pa0712");
			map.put("message", TipMessage.getTipMessage(
					"pa.low.adjust.zuidigongzitiaozhengpiliangcaozuochenggong",
					request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage(
					"pa.low.adjust.zuidigongzitiaozhengpiliangcaozuoshibai",
					request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 撤销调整-非促销员(cancel low adjust)
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelFeiCulowadjust")
	@ResponseBody
	public Map cancelFeiCulowadjust(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.paLowAdjustSer.cancellowadjust(request);

		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"pa.low.adjust.chexiaochenggong", request));// 撤销成功!
			map.put("navTabId", "pa0712");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"pa.low.adjust.chexiaoshibai", request));// 撤销失败!
		}

		return map;
	}

	/**
	 * 查看最低工资调整页面-非促销-历史(view low history salary)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFeiCuLowSalaryHistoryList")
	public ModelAndView viewFeiCuLowSalaryHistoryList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String paYear = request.getParameter("seach_paYear");
		if (null == paYear || "".equals(paYear)) {
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
			modelMap.put("paYear", timeFormatter.format(Calendar.getInstance()
					.getTime()));
			timeFormatter = new SimpleDateFormat("MM");
			modelMap.put("paMonth", timeFormatter.format(Calendar.getInstance()
					.getTime()));
		}

		modelMap.put("defaultCpny", admin.getCpnyId());
		// TA法人特殊处理
		if (null != admin.getCpnyId() && !"".equals(admin.getCpnyId())
				&& "SST".equals(admin.getCpnyId())) {
			modelMap.put("paLowAdjustList", this.paLowAdjustSer
					.getFeiCuLowSalaryForTAList(request));
		}else if (null != admin.getCpnyId() && !"".equals(admin.getCpnyId())
				&& "LGETR".equals(admin.getCpnyId())) {
			modelMap.put("paLowAdjustList", this.paLowAdjustSer
					.getFeiCuLowSalaryForTRList(request));
		} 
		else {
			modelMap.put("paLowAdjustList", this.paLowAdjustSer
					.getFeiCuLowSalaryList(request));
		}
		 if (null != admin.getCpnyId() && !"".equals(admin.getCpnyId()) 
					&& "LGETR".equals(admin.getCpnyId())){
		    	modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paLowAdjustSer
						.getFeiCuLowSalaryListTRCnt(request));
			}
			else {
				modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paLowAdjustSer
						.getFeiCuLowSalaryListCnt(request));
			}
		return new ModelAndView("/pa/salary/viewFeiCuLowSalaryHistoryList",
				modelMap);
	}
}
