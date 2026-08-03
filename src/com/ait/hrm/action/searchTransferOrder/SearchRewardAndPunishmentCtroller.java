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
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;


@Controller
@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchRewardAndPunishmentCtroller {
	
	Logger logger = Logger.getLogger(SearchPunishMentCtroller.class);
	
	@Autowired
	private  TransferOrderSer transferOrderSer;
	
	@Autowired
	private TransactionViewSer transactionViewSer;
	
	/**
	 * 选择显示"奖励查看"或"惩戒查看"
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-30 下午07:20:13 
	* @version V1.0
	 */
	@RequestMapping("/viewSearchRewardAndPunishmentList")
	public ModelAndView viewSearchRewardAndPunishmentList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String tag=request.getParameter("searchTransferOrder");
		modelMap.put("tag", tag);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		String transferOrderType=request.getParameter("transferOrderType");
		if("reward".equals(transferOrderType) || "1".equals(tag)){
			modelMap.put("hrHortationList", this.transactionViewSer.getHortationViewList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer.getHortationViewListCnt(request));
		}
		if("punishment".equals(transferOrderType) || "2".equals(tag)){
			modelMap.put("hrPunishMentList", this.transactionViewSer.getPunishMentViewList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer.getPunishMentViewListCnt(request));
		}
		modelMap.put("DEPT_NO", request.getParameter("DEPT_NO"));
		modelMap.put("KEY", request.getParameter("KEY"));
		modelMap.put("TRANS_CODE", request.getParameter("TRANS_CODE"));
		modelMap.put("FROM_DATE_REWARD", request.getParameter("FROM_DATE_REWARD"));
		modelMap.put("TO_DATE_REWARD", request.getParameter("TO_DATE_REWARD"));
		modelMap.put("FROM_DATE_PUNISHED", request.getParameter("FROM_DATE_PUNISHED"));
		modelMap.put("TO_DATE_PUNISHED", request.getParameter("TO_DATE_PUNISHED"));
		return new ModelAndView("/hrm/searchTransferOrder/viewSearchRewardAndPunishmentList",modelMap);
	}
	
	/**
	 * 奖励
	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/getHortationList1")
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
	@RequestMapping(value = "/cancelHortationInBatch")
	@ResponseBody
	public Map<String, Object> cancelHortationInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionViewSer
				.cancelHortationBatchInsideByNo(request);
		if (result == 1) {
		//	map.put("navTabId", "hr0510");
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_success",request));//发令取消成功
			map.put("statusCode", "200");
		} else if (result == 2) {
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cannot_be_cancle",request));//有发令不能被取消,请重试!
			map.put("statusCode", "300");
		} else {
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_fail",request));//发令取消失败
			map.put("statusCode", "300");
		}
		String tag=request.getParameter("searchTransferOrder");
		map.put("tag", tag);
		return map;
	}
	
	/**
	 * 惩戒
	 */
	@SuppressWarnings("unchecked")
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
//	@RequestMapping(value="/searchPunishMent1" )
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
	@RequestMapping(value = "/cancelPunishMentInBatch")
	@ResponseBody
	public Map<String, Object> cancelPunishMentInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionViewSer
				.cancelPunishMentBatchInsideByNo(request);
		if (result == 1) {
		//	map.put("navTabId", "hr0510");
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_success",request));//发令取消成功
			map.put("statusCode", "200");
		} else if (result == 2) {
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cannot_be_cancle",request));//有发令不能被取消,请重试!
			map.put("statusCode", "300");
		} else {
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_fail",request));//发令取消失败
			map.put("statusCode", "300");
		}
		String tag=request.getParameter("searchTransferOrder");
		map.put("tag", tag);
		map.put("result", result);
		return map;
	}

}
