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

//奖励
//@Controller
//@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchHortationCtroller {
	
	Logger logger = Logger.getLogger(SearchHortationCtroller.class);
	
	@Autowired
	private  TransferOrderSer transferOrderSer;
	
	@Autowired
	private TransactionViewSer transactionViewSer;
	
//	/**
//	 * 奖励
//	 */
//	@RequestMapping(value="/searchHortation" )
//	public ModelAndView getHortation(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
//
//		return new ModelAndView("/hrm/transferOrder/searchHortation",modelMap);
//	}
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/getHortationList")
//	@ResponseBody
//	public Map getHortationList(HttpServletRequest request) throws Exception{		
//		logger.info("getHortationList.start...");
//		Map temp=transferOrderSer.getHortationForSearch(request);//奖励列表
//		return temp;
//	}
	
	/**
	 * 查看奖励发令列表(view Hortation transaction affirm list)
	 */
//	@RequestMapping(value="/searchHortation" )
//	public ModelAndView viewHortationList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//		modelMap.put("defaultCpny", admin.getCpnyId());
//		modelMap.put("hrHortationList", this.transactionViewSer.getHortationViewList(request));
//		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer.getHortationViewListCnt(request));
//		return new ModelAndView("/hrm/transferOrder/searchHortation",modelMap);
//	}

	/**
	 * 批量取消奖励发令(batch cancel Hortation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/cancelHortationInBatch")
//	@ResponseBody
//	public Map<String, Object> cancelHortationInBatch(
//			HttpServletRequest request) throws Exception {
//		Map<String, Object> map = new HashMap<String, Object>();
//		int result = this.transactionViewSer
//				.cancelHortationBatchInsideByNo(request);
//		if (result == 1) {
//			map.put("navTabId", "hr0507");
//			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_success",request));//发令取消成功
//			map.put("statusCode", "200");
//		} else if (result == 2) {
//			map.put("message", TipMessage.getTipMessage("hr.alert.message.cannot_be_cancle",request));//有发令不能被取消,请重试!
//			map.put("statusCode", "300");
//		} else {
//			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_fail",request));//发令取消失败
//			map.put("statusCode", "300");
//		}
//		return map;
//	}
//
}
