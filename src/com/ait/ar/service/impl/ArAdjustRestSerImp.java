package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArAdjustRestDao;
import com.ait.ar.service.ArAdjustRestSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAdjustRestSerImp.java
 * @Description:
 * @Create date: 2012-3-23 下午04:28:54
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ArAdjustRestSerImp implements ArAdjustRestSer {
	Logger logger = Logger.getLogger(ArAdjustRestSerImp.class);
	
	@Autowired
	private ArAdjustRestDao arAdjustRestDao ;

	/**
	 * 显示个人调休页面(get ArAdjustRest List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArAdjustRestList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("PERSON_ID", admin.getPersonId());

		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				arAdjustRestDao.getArAdjustRestList(paramMap , 
						UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
					) ;
		}
		else{
			retrunList = arAdjustRestDao.getArAdjustRestList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 显示个人调休数量(get ArAdjustRest Cnt)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getArAdjustRestCnt(HttpServletRequest request){
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		return arAdjustRestDao.getArAdjustRestCnt(paramMap) ;
	}
	
	/**
	 * 显示个人调休页面(get ArAdjustRest List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public Object getArAdjustRestInfo(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;

		return arAdjustRestDao.getArAdjustRestInfo(paramMap) ;
	}
}
