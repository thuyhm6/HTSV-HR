package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.AjaxDao;
import com.ait.sys.service.AjaxSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;


/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AjaxSerImpl.java
 * @Description: implement Class AjaxSer.java
 * @Create date: Jan 16, 2012 3:24:55 PM
 * @author : hanzhe(hanzhe@ait.net.cn)
 * @version 5.1
 */
@Service
public class AjaxSerImpl implements AjaxSer {
	Logger logger = Logger.getLogger(AjaxSerImpl.class);

	@Autowired
	private AjaxDao ajaxDao;
	
	/**
	 * 查询所有公司信息(query all the company info)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getCompanyInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
	 	Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ; 
	 	retrunList = ajaxDao.getCompanyInfoList(paramMap);
	 	
		return retrunList;
	}	
	
	/**
	 * 根据法人Cpny_id查询工资月份信息（query the pa_month info list by cpny_id）
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaMonthList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
	 	String cpnyId = paramMap.get("CPNY_ID")!=null?paramMap.get("CPNY_ID").toString():"";
	 	if("".equals(cpnyId)){
	 		return retrunList;
	 	}
	 	retrunList = ajaxDao.getPaMonthList(paramMap);
		return retrunList;
	}	
	
	/**
	 * 根据法人Cpny_id、工资月份查询薪资发放日期（query the GIVE_DATE info list by cpny_id 、pa_month）
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaGiveDateList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
	 	Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
	 	String cpnyId = paramMap.get("CPNY_ID")!=null?paramMap.get("CPNY_ID").toString():"";
	 	if("".equals(cpnyId)){
	 		return retrunList;
	 	}
	 	retrunList = ajaxDao.getPaGiveDateList(paramMap);
	 	
		return retrunList;
	}	
	
	/**
	 * 根据法人Cpny_id查询工资项目信息（query the pa item info list by cpny_id）
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaItemInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
	 	String cpnyId = paramMap.get("CPNY_ID")!=null?paramMap.get("CPNY_ID").toString():"";
	 	if("".equals(cpnyId)){
	 		return retrunList;
	 	}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("interLanguage", admin.getLanguage());
	 	retrunList = ajaxDao.getPaItemInfoList(paramMap);
		return retrunList;
	}	
	
	/**
	 * 根据公司ID查询该公司的部门树（query the dept tree by the company id ）
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDeptInfoTree(Map param) {
		List list=new ArrayList();
		list = ajaxDao.getDeptInfoTree(param);
		
		return list;
	}	
	/**
	 * 根据传入的年月输出该月所以的 *月*日格式
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDayAll(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		String year = paramMap.get("paYear")!=null?paramMap.get("paYear").toString():"";
		paramMap.put("PA_YEAR", year);
		//AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//如果没有paYear，不进行查询
		if(paramMap.get("paYear")==null || "".equals(paramMap.get("paYear"))){
			System.out.println("");
			return returnList;
		}
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				ajaxDao.getDayAll(paramMap) ;
		}else{
			returnList = ajaxDao.getDayAll(paramMap) ;
		}
		return returnList ;
	}
	
	/**
	 * 根据公司ID查询该公司考勤区间
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getTimeIntervalList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//如果没有paYear，不进行查询
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			System.out.println("");
			return returnList;
		}
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				ajaxDao.getTimeIntervalList(paramMap) ;
		}else{
			returnList = ajaxDao.getTimeIntervalList(paramMap) ;
		}
		return returnList ;
	}
	
	/**
	 * 获取未刷卡查询的页面中的状态（旷工）
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List statusList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID"))){
			return returnList;
		}else{
			returnList = ajaxDao.statusList(paramMap) ;
		}
		return returnList ;
	}
}
