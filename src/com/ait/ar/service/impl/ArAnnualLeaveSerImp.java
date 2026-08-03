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

import com.ait.ar.dao.ArAnnualLeaveDao;
import com.ait.ar.service.ArAnnualLeaveSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAnnualLeaveSerImp.java
 * @Description:
 * @Create date: 2012-2-14 下午12:54:40
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ArAnnualLeaveSerImp implements ArAnnualLeaveSer {
	Logger logger = Logger.getLogger(ArAnnualLeaveSerImp.class);
	
	@Autowired
	private ArAnnualLeaveDao arAnnualLeaveDao ;

	/**
	 * 查询个人年假信息(get ArAnnualLeave Info)
	 * @param request
	 * @return Object
	 * @throws 
	 */
	public Object getArAnnualLeaveInfo(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.arAnnualLeaveDao.getArAnnualLeaveInfo(paramMap) ;
	}
	
	/**
	 * 显示个人年假页面(view ArAnnual Leave)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArAnnualLeaveList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("AR_ADMIN_ID", admin.getPersonId());

		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				arAnnualLeaveDao.getArAnnualLeaveList(paramMap , 
						UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
					) ;
		}
		else{
			retrunList = arAnnualLeaveDao.getArAnnualLeaveList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 显示个人年假数量(get ArAnnualLeave Cnt)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getArAnnualLeaveCnt(HttpServletRequest request){
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("AR_ADMIN_ID", admin.getPersonId());
		
		return arAnnualLeaveDao.getArAnnualLeaveCnt(paramMap) ;
	}
	
	private LinkedHashMap setGetArAnnualLeaveParam(HttpServletRequest request){
		// 从session中取得登陆用户信息
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("AR_ADMIN_ID", admin.getAdminID()) ;
		
		return paramMap ;
	}
	
	/**
	 * 删除个人年假信息(delete ArAnnual Leave)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int deleteArAnnualLeave(HttpServletRequest request){
		
		// 页面参数
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> arDetailInfoList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		
		try {
			
			arAnnualLeaveDao.deleteArAnnualLeave(arDetailInfoList) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 修改个人年假信息(update ArAnnualLeave Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int updateArAnnualLeaveInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		// 从session中取得登陆用户信息
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getPersonId()) ;
		
		try {
			
			this.arAnnualLeaveDao.updateArAnnualLeave(paramMap) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 添加个人年假(add ArAnnualLeave Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int addArAnnualLeaveInfo(HttpServletRequest request) {
		
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		// 从session中取得登陆用户信息
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId()) ;
		
		try {
			
			this.arAnnualLeaveDao.addArAnnualLeave(paramMap) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 检查个人年假添加信息(check AddArAnnualLeave Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int checkAddArAnnualLeaveInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.arAnnualLeaveDao.checkAddArAnnualLeaveInfo(paramMap) ;
	}
	
	/**
	 * 年假初始化(create ArAnnualLeave Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int createArAnnualLeaveInfo(HttpServletRequest request){
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		//所有人
		paramMap.put("IN_INIT_TYPE", "ALL");
		//考勤员权限
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		
		try {
			
			this.arAnnualLeaveDao.createArAnnualLeaveInfo(paramMap) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 获得人员列表(get Emp For Supervisor List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getEmpForSupervisorList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		
		paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		
		retrunList = arAnnualLeaveDao.getEmpForSupervisorList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList;
	}
	
	/**
	 * 获得人员列表(get Emp For Supervisor Cnt)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@Override
	public int getEmpForSupervisorCnt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		
		return arAnnualLeaveDao.getEmpForSupervisorCnt(paramMap) ;
	}
	
	/**
	 * 添加portal日志信息时的IP地址
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int addPortalIp(Map paramMap) {
		try {
			this.arAnnualLeaveDao.addPortalIp(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int createArAnnualLeaveAuto(Map paramMap) {
		
		return 0;
	}
}
