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
/**
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName SearchAgentCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-3-24 下午05:39:43
 * @version 5.0
 * 
 */
//派遣
@Controller
@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchDispatchCtroller {
	
	Logger logger = Logger.getLogger(SearchDispatchCtroller.class);
	
	@Autowired
	private  TransferOrderSer transferOrderSer;
	
	/**
	 * 派遣
	 */
	@RequestMapping(value="/searchDispatch" )
	public ModelAndView getDispatch(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{

		return new ModelAndView("/hrm/transferOrder/searchDispatch",modelMap);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDispatchList")
	@ResponseBody
	public Map getDispatchList(HttpServletRequest request) throws Exception{		
		logger.info("getDispatchList.start...");
		Map temp=transferOrderSer.getDispatchForSearch(request);//派遣列表
		return temp;
	}
}
