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

//兼职
@Controller
@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchPluralityCtroller {
	
	Logger logger = Logger.getLogger(SearchPluralityCtroller.class);
	
	@Autowired
	private  TransferOrderSer transferOrderSer;
	
	@Autowired
	private TransactionViewSer transactionViewSer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPluralityList")
	@ResponseBody
	public Map getPluralityList(HttpServletRequest request) throws Exception{		
		logger.info("getPluralityList.start...");
		Map temp=transferOrderSer.getPluralityForSearch(request);//兼职列表
		return temp;
	}
	
	
	
	/**
	 * 查看兼职发令列表(view Plurality transaction affirm list)
	 */
	@RequestMapping(value="/searchPlurality" )
	public ModelAndView viewPluralityList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("hrPluralityList", this.transactionViewSer.getPluralityViewList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer.getPluralityViewListCnt(request));
		return new ModelAndView("/hrm/transferOrder/searchPlurality",modelMap);
	}

	/**
	 * 批量取消兼职发令(batch cancel Plurality transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelPluralityInBatch")
	@ResponseBody
	public Map<String, Object> cancelPluralityInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionViewSer
				.cancelPluralityBatchInsideByNo(request);
		if (result == 1) {
			map.put("navTabId", "hr0503");
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
}
