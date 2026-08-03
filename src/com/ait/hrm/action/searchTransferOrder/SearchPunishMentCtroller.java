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

//惩戒
//@Controller
//@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchPunishMentCtroller {
	
	Logger logger = Logger.getLogger(SearchPunishMentCtroller.class);
	
	@Autowired
	private  TransferOrderSer transferOrderSer;
	
	@Autowired
	private TransactionViewSer transactionViewSer;
//	/**
//	 * 惩戒
//	 */
//	@RequestMapping(value="/searchPunishMent" )
//	public ModelAndView getPunishMent(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
//
//		return new ModelAndView("/hrm/transferOrder/searchPunishMent",modelMap);
//	}
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/getPunishMentList")
//	@ResponseBody
//	public Map getPunishMentList(HttpServletRequest request) throws Exception{		
//		logger.info("getPunishMentList.start...");
//		Map temp=transferOrderSer.getPunishMentForSearch(request);//惩戒列表
//		return temp;
//	}
	
	/**
	 * 查看惩戒发令列表(view PunishMent transaction affirm list)
	 */
//	@RequestMapping(value="/searchPunishMent" )
//	public ModelAndView viewPunishMentList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//		modelMap.put("defaultCpny", admin.getCpnyId());
//		modelMap.put("hrPunishMentList", this.transactionViewSer.getPunishMentViewList(request));
//		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer.getPunishMentViewListCnt(request));
//		return new ModelAndView("/hrm/transferOrder/searchPunishMent",modelMap);
//	}

	/**
	 * 批量取消惩戒发令(batch cancel PunishMent transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/cancelPunishMentInBatch")
//	@ResponseBody
//	public Map<String, Object> cancelPunishMentInBatch(
//			HttpServletRequest request) throws Exception {
//		Map<String, Object> map = new HashMap<String, Object>();
//		int result = this.transactionViewSer
//				.cancelPunishMentBatchInsideByNo(request);
//		if (result == 1) {
//			map.put("navTabId", "hr0506");
//			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_success",request));//发令取消成功
//			map.put("statusCode", "200");
//		} else if (result == 2) {
//			map.put("message", TipMessage.getTipMessage("hr.alert.message.cannot_be_cancle",request));//有发令不能被取消,请重试!
//			map.put("statusCode", "300");
//		} else {
//			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_fail",request));//发令取消失败
//			map.put("statusCode", "300");
//		}
//		map.put("result", result);
//		return map;
//	}
	 

}
