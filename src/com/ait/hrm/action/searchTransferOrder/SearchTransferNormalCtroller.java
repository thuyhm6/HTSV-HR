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
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpInfoCtroller.java
 * @Create date: Jan 16, 2012 10:44:58 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchTransferNormalCtroller {
	
	Logger logger = Logger.getLogger(SearchTransferNormalCtroller.class);
	
	
	@Autowired
	private TransactionViewSer transactionViewSer;
	
	/**
	 * 转正
	 */
	@RequestMapping(value="/searchTransferNormal" )
	public ModelAndView viewGetTransferNormalList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("hrTransferNormalList", this.transactionViewSer.getTransferNormalViewList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer.getTransferNormalViewListCnt(request));
		
		return new ModelAndView("/hrm/transferOrder/searchTransferNormal",modelMap);
	}
	
	/**
	 * 转正历史记录
	 */
//	@RequestMapping(value="/searchTransferNormalForHistory" )
//	public ModelAndView viewTransferNormalForHistoryList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
//		
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//		modelMap.put("defaultCpny", admin.getCpnyId());
//		modelMap.put("hrTransferNormalList", this.transactionViewSer.getTransferNormalViewList(request));
//		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer.getTransferNormalViewListCnt(request));
//		
//		return new ModelAndView("/hrm/transferOrder/searchTransferNormalForHistory",modelMap);
//	}
	
	/**
	 * 批量取消转正发令(batch cancel Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelTransferNormaInBatch")
	@ResponseBody
	public Map<String, Object> cancelTransferNormaInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionViewSer.cancelHrTransferNormalInsideByNo(request);
		if (result == 1) {
			map.put("navTabId", "hr0210");
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_success",request));//发令取消成功
			map.put("statusCode", "200");
		} else if (result == 2) {
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cannot_be_cancle",request));//有发令不能被取消,请重试!
			map.put("statusCode", "300");
		} else {
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_fail",request));//发令取消失败
			map.put("statusCode", "300");
		}
		return map;
	}
	
}
