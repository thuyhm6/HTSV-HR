package com.ait.hrm.action.searchTransferOrder;

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

import com.ait.hrm.service.TransferOrderSer;

//担当业务变更
@Controller
@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchTransferActBusinessCtroller {
	
	Logger logger = Logger.getLogger(SearchTransferActBusinessCtroller.class);
	
	@Autowired
	private  TransferOrderSer transferOrderSer;
	
	/**
	 * 担当业务变更
	 */
	@RequestMapping(value="/searchTransferActBusiness" )
	public ModelAndView getTransferActBusiness(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{

		return new ModelAndView("/hrm/transferOrder/searchTransferActBusiness",modelMap);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTransferActBusinessList")
	@ResponseBody
	public Map getTransferActBusinessList(HttpServletRequest request) throws Exception{		
		logger.info("getTransferActBusinessList.start...");
		Map temp=transferOrderSer.getTransferActBusinessForSearch(request);//担当业务变更列表
		return temp;
	}
}
