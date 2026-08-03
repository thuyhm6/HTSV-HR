package com.ait.hrm.action.searchTransferOrder;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.ait.hrm.service.TransactionViewSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 调动发令查看(transaction transaction view)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: SearchUpgradeCtroller.java
 * @Description:
 * @Create date: Feb 27, 2012 10:16:44 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 27, 2012 10:16:44 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchUpgradeCtroller {

	Logger logger = Logger.getLogger(SearchUpgradeCtroller.class);

	@Autowired
	private TransactionViewSer transactionViewSer;

	/**
	 * 查看调动发令列表(view transaction transaction affirm list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTransactionTransViewList")
	public ModelAndView viewTransactionTransViewList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("hrExperienceInsideList", this.transactionViewSer
				.getTransactionTransViewList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer
				.getTransactionTransViewListCnt(request));

		return new ModelAndView(
				"/ess/searchTransferOrder/viewTransactionTransViewList",
				modelMap);
	}

	/**
	 * 批量取消调动发令(batch cancel Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelTransactionTransInBatch")
	@ResponseBody
	public Map<String, Object> cancelTransactionTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionViewSer
				.cancelHrExperienceInsideByNo(request);
		if (result == 1) {
			map.put("navTabId", "hr0501");
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_success",request));//发令取消成功
			map.put("statusCode", "200");
		} else if (result == 2) {
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cannot_be_cancle",request));//有发令不能被取消,请重试!
			map.put("statusCode", "300");
		} else {
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_fail",request));//发令取消失败
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 查看员工发令历史记录(view employee transaction history List )
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmployeeHistoryList")
	public ModelAndView viewEmployeeHistoryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("hrExperienceInsideList", this.transactionViewSer
				.getEmployeeTransHistoryList(request));
		// modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer
		// .getTransactionTransViewList(request));
		return new ModelAndView(
				"/hrm/searchTransferOrder/viewEmployeeHistoryList", modelMap);
	}
}
