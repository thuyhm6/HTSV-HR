package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.ArAffirmPostDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.sys.service.ArAffirmPostSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;


/**
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName ArAffirmPostSerImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-13 上午10:15:28
 * @version 5.0
 * 
 */
@Service
public class ArAffirmPostSerImpl implements ArAffirmPostSer {
	
	Logger logger = Logger.getLogger(ArAffirmPostSerImpl.class);

	@Autowired
	private ArAffirmPostDao arAffirmPostDao;
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	@SuppressWarnings("unchecked")
	public List getArAffirmPostList(HttpServletRequest request,Object dutyNo){
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("DUTY_NO", dutyNo);
		returnList = this.arAffirmPostDao.getArAffirmPostList(paramMap); ;
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getArAffirmDutyList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if (UiUtil.getPageNum(request) > 0){
			returnList = this.arAffirmPostDao.getArAffirmDutyList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}else{
			returnList = this.arAffirmPostDao.getArAffirmDutyList(paramMap); ;
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getApplyDutyList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		returnList = this.arAffirmPostDao.getApplyDutyList(paramMap);
		
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public int  getArAffirmDutyListCnt(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return this.arAffirmPostDao.getArAffirmDutyListCnt(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public int saveArAffirmPost(HttpServletRequest request) {
		int result=0;
		try{
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			this.arAffirmPostDao.saveArAffirmPost(paramMap); 
			result=1;
		}catch(Exception e){
			e.printStackTrace();
			result=0;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public int updateArAffirmPost(HttpServletRequest request) {
		int result=0;
		try{
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			this.arAffirmPostDao.updateArAffirmPost(paramMap); 
			result=1;
		}catch(Exception e){
			e.printStackTrace();
			result=0;
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public int deleteArAffirmPostInfo(HttpServletRequest request) {
		int result=0;
		try{
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			this.arAffirmPostDao.deleteArAffirmPostInfo(paramMap); 
			result=1;
		}catch(Exception e){
			e.printStackTrace();
			result=0;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Object getAffirmPostById(HttpServletRequest request)  {
		Object returnObj = new Object() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			returnObj = this.arAffirmPostDao.getAffirmPostById(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	public List getSortByParentNo(HttpServletRequest request){
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = this.arAffirmPostDao.getSortByParentNo(paramMap); ;
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getDutyListByCpnyId (HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.arAffirmPostDao.getDutyListByCpnyId(paramMap);
	}
	public int validateArAffirmPostExist(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.arAffirmPostDao.validateArAffirmPostExist(paramMap);
	}
}
