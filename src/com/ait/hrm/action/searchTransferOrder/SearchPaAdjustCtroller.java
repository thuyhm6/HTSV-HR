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
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName SearchPaAdjustCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-3-24 下午02:42:03
 * @version 5.0
 */
//薪资调整
@Controller
@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchPaAdjustCtroller {
	
	Logger logger = Logger.getLogger(SearchDispatchCtroller.class);
	
	@Autowired
	private  TransferOrderSer transferOrderSer;
	
	@Autowired
	private TransactionViewSer transactionViewSer;

	//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/getPaAdjustList")
//	@ResponseBody
//	public Map getDispatchList(HttpServletRequest request) throws Exception{		
//		logger.info("getPaAdjustList.start...");
//		Map temp=transferOrderSer.getDispatchForSearch(request);//派遣列表
//		return temp;
//	}
	
	/**
	 * 查看薪资调整发令列表(view PaAdjust transaction affirm list)
	 */
	@RequestMapping(value="/searchPaAdjust" )
	public ModelAndView viewPaAdjustList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("hrPaAdjustList", this.transactionViewSer.getPaAdjustViewList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer.getPaAdjustViewListCnt(request));
		return new ModelAndView("/hrm/searchTransferOrder/searchPaAdjust",modelMap);
	}

	/**
	 * 批量取消薪资调整发令(batch cancel PaAdjust transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelPaAdjustInBatch")
	@ResponseBody
	public Map<String, Object> cancelPaAdjustInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionViewSer
				.cancelPaAdjustBatchInsideByNo(request);
		if (result == 1) {
			map.put("navTabId", "hr0512");
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
