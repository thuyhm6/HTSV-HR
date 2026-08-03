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

//转职
@Controller
@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchTransferPostCtroller {
	
	Logger logger = Logger.getLogger(SearchTransferPostCtroller.class);
	
	@Autowired
	private  TransferOrderSer transferOrderSer;
	
	/**
	 * 转职
	 */
	@RequestMapping(value="/searchTransferPost" )
	public ModelAndView getTransferPost(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{

		return new ModelAndView("/hrm/transferOrder/searchTransferPost",modelMap);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTransferPostList")
	@ResponseBody
	public Map getTransferPostList(HttpServletRequest request) throws Exception{		
		logger.info("getTransferPostList.start...");
		Map temp=transferOrderSer.getTransferPostForSearch(request);//转职列表
		return temp;
	}
}
