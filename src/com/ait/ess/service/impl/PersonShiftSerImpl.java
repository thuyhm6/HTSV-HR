package com.ait.ess.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.PersonShiftDao;
import com.ait.ess.service.PersonShiftSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class PersonShiftSerImpl implements PersonShiftSer{
  
	@Autowired
	private PersonShiftDao personShiftDao;
	
	Logger logger = Logger.getLogger(RecordTestSerImpl.class);

	@SuppressWarnings("unchecked")
	public List viewPersonShiftList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
		Calendar c = Calendar.getInstance();    
		if((request.getParameter("seach_sDate")==""||request.getParameter("seach_sDate")==null )&& (request.getParameter("seach_eDate")==""||request.getParameter("seach_eDate")==null)){
			//获取当前月第一天：
			c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());
			paramMap.put("sDate",first);
			//获取当前月最后一天：
			c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
			String last = format.format(c.getTime());
			paramMap.put("eDate",last);
		}
		
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		if(admin.getStatNo() != null && !admin.getStatNo().equals("")){
			paramMap.put("STAT_NO", admin.getStatNo());
		}
			
		retrunList = personShiftDao.viewPersonShiftList(paramMap) ;
		
		return retrunList ;
	}
	@SuppressWarnings("unchecked")
	public List viewArShiftGroupList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		if(admin.getStatNo() != null && !admin.getStatNo().equals("")){
			paramMap.put("STAT_NO", admin.getStatNo());
		}
			retrunList = personShiftDao.viewArShiftGroupList(paramMap) ;
		return retrunList ;
	}

	@SuppressWarnings("unchecked")
	public int viewPersonShiftListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		if(admin.getStatNo() != null && !admin.getStatNo().equals("")){
			paramMap.put("STAT_NO", admin.getStatNo());
		}
		
		retrunInt = personShiftDao.viewPersonShiftListCnt(paramMap) ;
		
		return retrunInt ;
	}
}
