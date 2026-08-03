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

import com.ait.ar.dao.ArProgressDao;
import com.ait.ar.service.ArProgressSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArProgressSerImp.java
 * @Description:
 * @Create date: 2012-2-12 下午12:02:59
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ArProgressSerImp implements ArProgressSer {
	Logger logger = Logger.getLogger(ArProgressSerImp.class);
	
	@Autowired
	private ArProgressDao arProgressDao;

	/**
	 * 查询月考勤锁定(get ArProgress List)
	 * 
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArProgressList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = ObjectUtils.toString(paramMap.get("year_ar0208")) ;
		String month = ObjectUtils.toString(paramMap.get("month_ar0208")) ;
		String arMonth = year + month;
		if(arMonth.length() == 0){
			arMonth = DateUtil.getCurrentMonthStr() ;
		}
		
		paramMap.put("arMonth", arMonth) ;
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				arProgressDao.getArProgressList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = arProgressDao.getArProgressList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 取得月考勤条数(get Cycle count)
	 * @param request
	 * @return int
	 */
	@Override
	public int getArProgressCnt(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = ObjectUtils.toString(paramMap.get("year_ar0208")) ;
		String month = ObjectUtils.toString(paramMap.get("month_ar0208")) ;
		String arMonth = year + month;
		if(arMonth.length() == 0){
			arMonth = DateUtil.getCurrentMonthStr();
		}
		
		paramMap.put("arMonth", arMonth) ;
		
		return arProgressDao.getArProgressCnt(paramMap) ;
	}
	
	/**
	 * 更新考勤锁定(update ArProgress Info)
	 * 
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int updateArProgressInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		
		this.arProgressDao.updateArProgressInfo(paramMap) ;
		
		return 0 ;
	}
	
	
}
