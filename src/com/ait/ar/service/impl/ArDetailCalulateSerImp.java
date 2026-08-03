package com.ait.ar.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
import com.ait.ar.dao.ArDetailCalulateDao;
import com.ait.ar.service.ArDetailCalulateSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArDetailCalulateSerImp.java
 * @Description:
 * @Create date: 2012-2-7 下午12:01:36
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ArDetailCalulateSerImp implements ArDetailCalulateSer {
Logger logger = Logger.getLogger(ArDetailCalulateSerImp.class);

	 @Autowired	
	 private ArDetailCalulateDao arDetailCalulateDao;

	 /**
	  * 查询考勤员列表(get ArSupervisor List)
	  * @param request
	  * @return List
	  * @throws 
	*/
 	@SuppressWarnings("unchecked")
	public List getArSupervisorList(HttpServletRequest request) {
		// TODO Auto-generated method stub
 		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
 		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
 		paramMap.put("AR_SUPERVISIOR_INFO",admin.getPersonId());
		return arDetailCalulateDao.getArSupervisorList(paramMap);
	}

 	/**
	 * 明细计算(detail Calculate)
	 * @param request
	 * @return String
	 * @throws 
	 */
 	@SuppressWarnings("unchecked")
	public String detailCalculate(HttpServletRequest request) {
		String returnString = "" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnString = arDetailCalulateDao.detailCalculate(paramMap) ;
		
		return returnString ;
	}
 	
	/**
	 * 事后日考勤计算(detail ShihouCalculate)
	 * @param request
	 * @return String
	 * @throws 
	 */
 	@SuppressWarnings("unchecked")
	public String detailShiHouCalculate(HttpServletRequest request) {
		String returnString = "" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnString = arDetailCalulateDao.detailShiHouCalculate(paramMap) ;
		
		return returnString ;
	}
 	/**
	 * 追溯考勤计算(detail ShihouCalculate)
	 * @param request
	 * @return String
	 * @throws 
	 */
 	@SuppressWarnings("unchecked")
	public String detailLastMonthCalculate(HttpServletRequest request) {
		String returnString = "" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnString = arDetailCalulateDao.detailLastMonthCalculate(paramMap) ;
		
		return returnString ;
	}
 	
 	
 	/**
	 * 郵件發送(detail Calculate)
	 * @param request
	 * @return String
	 * @throws 
	 */
 	@SuppressWarnings("unchecked")
	public List getardetailsendview(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//returnString = arDetailCalulateDao.getardetailsendview(paramMap) ;
		
		return arDetailCalulateDao.getardetailsendview(paramMap) ;
	}
}
