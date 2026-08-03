package com.ait.hrm.action.searchTransferOrder;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.hrm.service.TransactionViewSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName SearchAgentCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-3-24 下午05:39:43
 * @version 5.0
 * 
 */
//代理
@Controller
@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchAgentCtroller {

	@Autowired
	private TransactionViewSer transactionViewSer;
	
	/**
	 * 查看代理发令列表(view Agent transaction affirm list)
	 */
	@RequestMapping(value="/searchAgent" )
	public ModelAndView viewAgentList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("hrAgentList", this.transactionViewSer.getAgentViewList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer.getAgentViewListCnt(request));
		return new ModelAndView("/hrm/searchTransferOrder/searchAgent",modelMap);
	}

	/**
	 * 批量取消代理发令(batch cancel Agent transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelAgentInBatch")
	@ResponseBody
	public Map<String, Object> cancelAgentInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionViewSer
				.cancelAgentBatchInsideByNo(request);
		if (result == 1) {
			map.put("navTabId", "hr0513");
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
