package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.ArAffirmDao;
import com.ait.sys.service.ArAffirmSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName ArAffirmSerImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-9 下午06:11:44
 * @version 5.0
 * 
 */
@Service
public class ArAffirmSerImpl implements ArAffirmSer{
	
	Logger logger = Logger.getLogger(ArAffirmSerImpl.class);

	@Autowired
	private ArAffirmDao arAffirmDao;
	
	@SuppressWarnings("unchecked")
	public List getArAffirmList(HttpServletRequest request,String type){
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("TYPE", type);
			returnList = arAffirmDao.getArAffirmList(paramMap) ;
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public int getArAffirmListCnt(HttpServletRequest request,String type){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("TYPE", type);
		return arAffirmDao.getArAffirmListCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	public int saveArAffirmInfo(HttpServletRequest request){
		int result=0;
		try{
			AdminBean admin=SessionUtil.getLoginUserFromSession(request);
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("CREATED_BY", admin.getAdminID());
			this.arAffirmDao.saveArAffirmInfo(paramMap); 
			result=1;
		}catch(Exception e){
			e.printStackTrace();
			result=0;
		}
		return result;
	}
	
	/**
	 * 获取申请类型
	 */
	@SuppressWarnings("unchecked")
	public List getApplyList(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return arAffirmDao.getApplyList(paramMap);
	}
	
	/**
	 * 获取职务类型
	 */
	@SuppressWarnings("unchecked")
	public List getPostList(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return arAffirmDao.getPostList(paramMap);
	}
	
	/**
	 * 获取职责类型
	 */
	@SuppressWarnings("unchecked")
	public List getDutyList(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return arAffirmDao.getDutyList(paramMap);
	}
	
	/**
	 * 列表页面查看详细
	 */
	@SuppressWarnings("unchecked")
	public List getDetailParamList(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return arAffirmDao.getDetailParamList(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public int deleteArAffirmInfo(HttpServletRequest request){
		int result=0;
		try {
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			this.arAffirmDao.deleteArAffirmInfo(paramMap);
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public int updateArAffirmInfo(HttpServletRequest request){
		int result=0;
		try{
			AdminBean admin=SessionUtil.getLoginUserFromSession(request);
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			this.arAffirmDao.updateArAffirmInfo(paramMap); 
			result=1;
		}catch(Exception e){
			e.printStackTrace();
			result=0;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Object getLeaveApplyParam (HttpServletRequest request){
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = this.arAffirmDao.getLeaveApplyParam(paramMap) ;
		return returnObj ;
	}
	
	/**
	 * 获取申请类型 如：
	 * 加班类型、休假类型、出差类型、哺乳假类型、年假调整类型、考勤异常、漏刷卡类型、离职类型、合同续签、临促工资申请
	 */
	@SuppressWarnings("unchecked")
	public List getApplyTypeNoList(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request , "seach_") ;
		return arAffirmDao.getApplyTypeNoList(paramMap);
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List getApplyTypeCodeList(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request , "seach_") ;
		if(paramMap.get("APPLY_TYPE_NO")!=null){
			return arAffirmDao.getApplyTypeCodeList(paramMap);
		}else{
			return null;
		}
		
	}
	

	@SuppressWarnings("unchecked")
	public List getArAffirmFinalList(HttpServletRequest request,String type){
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("TYPE", type);
		if (UiUtil.getPageNum(request) > 0){
			returnList = arAffirmDao.getArAffirmFinalList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}else{
			returnList = arAffirmDao.getArAffirmFinalList(paramMap) ;
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public int getArAffirmFinalListCnt(HttpServletRequest request,String type){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("TYPE", type);
		return arAffirmDao.getArAffirmFinalListCnt(paramMap) ;
	}
	

	@SuppressWarnings("unchecked")
	public int saveArAffirmFinalInfo(HttpServletRequest request){
		int result=0;
		try{
			AdminBean admin=SessionUtil.getLoginUserFromSession(request);
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("CREATED_BY", admin.getAdminID());
			this.arAffirmDao.saveArAffirmFinalInfo(paramMap); 
			result=1;
		}catch(Exception e){
			e.printStackTrace();
			result=0;
		}
		return result;
	}
	

	public Object getLeaveApplyFinalParam (HttpServletRequest request){
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = this.arAffirmDao.getLeaveApplyFinalParam(paramMap) ;
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	public int updateArAffirmFinalInfo(HttpServletRequest request){
		int result=0;
		try{
			AdminBean admin=SessionUtil.getLoginUserFromSession(request);
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			this.arAffirmDao.updateArAffirmFinalInfo(paramMap); 
			result=1;
		}catch(Exception e){
			e.printStackTrace();
			result=0;
		}
		return result;
	}

	public int deleteArAffirmFinalInfo(HttpServletRequest request){
		int result=0;
		try {
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			this.arAffirmDao.deleteArAffirmFinalInfo(paramMap);
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
