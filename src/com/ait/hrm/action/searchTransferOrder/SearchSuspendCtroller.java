package com.ait.hrm.action.searchTransferOrder;

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

import com.ait.hrm.service.TransactionViewSer;
import com.ait.hrm.service.TransferOrderSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

//停职
@Controller
@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchSuspendCtroller {
	
	Logger logger = Logger.getLogger(SearchSuspendCtroller.class);
	
	@Autowired
	private  TransferOrderSer transferOrderSer;
	
	@Autowired
	private TransactionViewSer transactionViewSer;
	
//	/**
//	 * 停职
//	 */
//	@RequestMapping(value="/searchSuspend" )
//	public ModelAndView getSuspend(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
//
//		return new ModelAndView("/hrm/transferOrder/searchSuspend",modelMap);
//	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSuspendList")
	@ResponseBody
	public Map getSuspendList(HttpServletRequest request) throws Exception{		
		logger.info("getSuspendList.start...");
		Map temp=transferOrderSer.getSuspendForSearch(request);//停职列表
		return temp;
	}
	
	/**
	 * 查看停职发令列表(view Suspend transaction affirm list)
	 */
	@RequestMapping(value="/searchSuspend" )
	public ModelAndView viewSuspendList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("hrSuspendList", this.transactionViewSer.getSuspendViewList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer.getSuspendViewListCnt(request));
		return new ModelAndView("/hrm/transferOrder/searchSuspend",modelMap);
	}

	/**
	 * 批量取消停职发令(batch cancel Suspend transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelSuspendInBatch")
	@ResponseBody
	public Map<String, Object> cancelSuspendInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionViewSer
				.cancelSuspendBatchInsideByNo(request);
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
	@RequestMapping(value = "/viewEmployeeSuspendHistoryList")
	public ModelAndView viewEmployeeSuspendHistoryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("hrSuspendHistoryList", this.transactionViewSer
				.getEmployeeSuspendHistoryList(request));
		return new ModelAndView(
				"/hrm/searchTransferOrder/viewEmployeeSuspendHistoryList", modelMap);
	}

}
