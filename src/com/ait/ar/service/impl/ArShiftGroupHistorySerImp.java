package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArShiftGroupHistoryDao;
import com.ait.ar.dao.EmpCalendarDao;
import com.ait.ar.service.ArShiftGroupHistorySer;
import com.ait.pa.dao.PaPayObjDao;
import com.ait.pa.service.workManagement.PaPayObjSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class ArShiftGroupHistorySerImp implements ArShiftGroupHistorySer {

	Logger logger = Logger.getLogger(ArShiftGroupHistorySerImp.class);
	
	@Autowired
	private ArShiftGroupHistoryDao arShiftGroupHistoryDao ;
	
	/**
	 * 考勤基本事项
	 */
	@SuppressWarnings("unchecked")
	public List getArBaseEmpInfoList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("firstView")!=null){
			retrunList = arShiftGroupHistoryDao.getArBaseEmpInfoList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getArBaseEmpInfoCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("firstView")!=null){
			retrunInt = arShiftGroupHistoryDao.getArBaseEmpInfoCnt(paramMap) ;
		}
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public Object getArBaseEmpInfoDetail(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.arShiftGroupHistoryDao.getArBaseEmpInfoDetail(paramMap) ; 
	}
	
	@SuppressWarnings("unchecked")
	public Object getArEmpShiftGroupFinalInfo(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.arShiftGroupHistoryDao.getArEmpShiftGroupFinalInfo(paramMap) ; 
	}
	
	@SuppressWarnings("unchecked")
	public int updateArBaseEmpInfoDetail(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.arShiftGroupHistoryDao.updateArBaseEmpInfoDetail(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 班组履历的查询
	 */
	@SuppressWarnings("unchecked")
	public List getArShiftRecordCheckList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		retrunList = arShiftGroupHistoryDao.getArShiftRecordCheckList(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getArShiftRecordCheckListCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("firstView")!=null){
			retrunInt = arShiftGroupHistoryDao.getArShiftRecordCheckListCnt(paramMap) ;
		}
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public Object getArShiftRecordCheckInfoDetail(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.arShiftGroupHistoryDao.getArShiftRecordCheckInfoDetail(paramMap) ; 
	}
	
	/**
	 * 查看班次日历(get EmpCalendar List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getArShiftMonthCheckListViewHtml(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyId=admin.getCpnyId();
		String SHIFT_NO= ObjectUtils.toString(request.getParameter("seach_SHIFT_NO"));
		List retrunList = new ArrayList();

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		if(SHIFT_NO.equals("400224")){
			retrunList = arShiftGroupHistoryDao.getArClassCalendarList(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		}else{
      		retrunList = arShiftGroupHistoryDao.getArClassCalendarListForNormalShift(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getArShiftMonthCheckListViewHtmlCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("firstView")!=null){
			retrunInt = arShiftGroupHistoryDao.getArShiftMonthCheckListViewHtmlCnt(paramMap) ;
		}
		return retrunInt ;
	}
}
