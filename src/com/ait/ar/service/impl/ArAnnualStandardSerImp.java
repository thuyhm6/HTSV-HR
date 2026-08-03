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
import com.ait.ar.dao.ArAnnualStandardDao;
import com.ait.ar.service.ArAnnualLeaveSer;
import com.ait.ar.service.ArAnnualStandardSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAnnualStandardSerImp.java
 * @Description:
 * @Create date: 2012-2-14 下午12:54:40
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ArAnnualStandardSerImp implements ArAnnualStandardSer {
	Logger logger = Logger.getLogger(ArAnnualStandardSerImp.class);
	
	@Autowired
	private ArAnnualStandardDao arAnnualStandardDao ;

	/**
	 * 查询个人年假信息(get ArAnnualLeave Info)
	 * @param request
	 * @return Object
	 * @throws 
	 */
	public Object getArAnnualStandardInfo(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.arAnnualStandardDao.getArAnnualStandardInfo(paramMap) ;
	}
	
	/**
	 * 显示个人年假页面(view ArAnnual Leave)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArAnnualStandardList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				arAnnualStandardDao.getArAnnualStandardList(paramMap , 
						UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
					) ;
		}
		else{
			retrunList = arAnnualStandardDao.getArAnnualStandardList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 年假标准开始月LIST(get ArAnnual Month List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArAnnualMonthList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		retrunList = arAnnualStandardDao.getArAnnualMonthList(paramMap) ;
		
		return retrunList ;
	}
	
	/**
	 * 显示个人年假数量(get ArAnnualLeave Cnt)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getArAnnualStandardCnt(HttpServletRequest request){
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		return arAnnualStandardDao.getArAnnualStandardCnt(paramMap) ;
	}
	
	/**
	 * 删除个人年假信息(delete ArAnnual Leave)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int deleteArAnnualStandard(HttpServletRequest request){
		
		// 页面参数
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> arDetailInfoList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		
		try {
			
			arAnnualStandardDao.deleteArAnnualStandard(arDetailInfoList) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 修改个人年假信息(update ArAnnualStandard Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int updateArAnnualStandardInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		// 从session中取得登陆用户信息
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getPersonId()) ;
		
		try {
			
			this.arAnnualStandardDao.updateArAnnualStandard(paramMap) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 添加个人年假(add ArAnnualStandard Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int addArAnnualStandardInfo(HttpServletRequest request) {
		
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		// 从session中取得登陆用户信息
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId()) ;
		
		try {
			
			this.arAnnualStandardDao.addArAnnualStandard(paramMap) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 检查个人年假添加信息(check AddArAnnualStandard Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int checkAddArAnnualStandardInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.arAnnualStandardDao.checkAddArAnnualStandardInfo(paramMap) ;
	}
}
