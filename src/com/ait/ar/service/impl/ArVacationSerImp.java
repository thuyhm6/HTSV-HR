package com.ait.ar.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ar.dao.ArVacationDao;
import com.ait.ar.service.ArVacationSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;


@Service
public class ArVacationSerImp implements ArVacationSer {
	Logger logger = Logger.getLogger(ArVacationSerImp.class);
	
	@Autowired
	private ArVacationDao arVacationDao ;

	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getArVacationLiquidationList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(request.getParameter("liquidation")!=null){
			paramMap.put("liquidation", request.getParameter("liquidation"));
		}else{
			paramMap.put("liquidation", "125082");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date= df.format(new Date()).toString();// new Date()为获取当前系统时间
		String year="";
		if(request.getParameter("year")!=null){
			year=request.getParameter("year").toString();
		}else{
			year="2013";
		}
		paramMap.put("year", year);
		retrunList = arVacationDao.getArVacationLiquidationList(paramMap) ;
		
		return retrunList ;
	}
	@SuppressWarnings("unchecked")
	public int saveVacationLiquidation(HttpServletRequest request) {
		int retrunCnt= 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(request.getParameter("liquidation")!=null){
			paramMap.put("liquidation", request.getParameter("liquidation"));
		}else{
			paramMap.put("liquidation", "125082");
		}
		paramMap.put("year", request.getParameter("baseyear_ar0506"));
		paramMap.put("ADMINID",admin.getAdminID());
		try{
			 arVacationDao.saveVacationLiquidation(paramMap) ;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1 ;
	}
	
	@SuppressWarnings("unchecked")
	public int getArVacationLiquidationCnt(HttpServletRequest request) {
		int retrunCnt= 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(request.getParameter("liquidation")!=null){
			paramMap.put("liquidation", request.getParameter("liquidation"));
		}else{
			paramMap.put("liquidation", "125082");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date= df.format(new Date()).toString();// new Date()为获取当前系统时间
		String year="";
		if(request.getParameter("year")!=null){
			year=request.getParameter("year").toString();
		}else{
			year="2013";
		}
		paramMap.put("year", year);
		retrunCnt = arVacationDao.getArVacationLiquidationCnt(paramMap) ;
		
		return retrunCnt ;
	}
	
	@SuppressWarnings("unchecked")
	public List getArVacationUpdateMonthList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = request.getParameter("year") ;
		String month = request.getParameter("month") ;
		String arMonth=null;
		if(year == null){
			 Date d=new Date();
				String dates = new java.text.SimpleDateFormat("yyyy-MM-dd").format(d);
				String[] ds=dates.split("-");
				year=ds[0].toString();
				month=ds[1].toString(); 
		} 
		arMonth=year+month;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("armonth", arMonth);
		paramMap.put("years", Integer.parseInt(year)-1);
		
		retrunList = arVacationDao.getArVacationUpdateMonthList(paramMap) ;
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getArVacationMonthExcel(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = request.getParameter("year") ;
		String month = request.getParameter("month") ;
		String arMonth=null;
		if(year == null){
			 Date d=new Date();
				String dates = new java.text.SimpleDateFormat("yyyy-MM-dd").format(d);
				String[] ds=dates.split("-");
				year=ds[0].toString();
				month=ds[1].toString(); 
		} 
		arMonth=year+month;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("armonth", arMonth);
		paramMap.put("years", Integer.parseInt(year)-1);
		
		retrunList = arVacationDao.getArVacationMonthExcel(paramMap) ;
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public String monthVacCalculate(HttpServletRequest request) {
		String returnString = "" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("adminId", admin.getPersonId());
		returnString = arVacationDao.monthVacCalculate(paramMap) ;
		
		return returnString ;
	}

	@SuppressWarnings("unchecked")
	public List getLeaveViewList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = request.getParameter("seach_insYear") ;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date= df.format(new Date()).toString();// new Date()为获取当前系统时间
		
		if(year==null){
			year=date.substring(0,4);
		}
		String month = request.getParameter("seach_insMonth") ;
		if(month==null){
			month=date.substring(5,7);
		}
		
		String armonth=year+month;
		paramMap.put("arMonth", armonth);
		//paramMap.put("key", request.getParameter(""));
		retrunList = arVacationDao.getLeaveViewList(paramMap) ;
		
		return retrunList ;
	}
	@SuppressWarnings("unchecked")
	public int getLeaveViewListCnt(HttpServletRequest request) {
		int returnCnt=0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String year = request.getParameter("seach_insYear") ;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date= df.format(new Date()).toString();// new Date()为获取当前系统时间
		
		if(year==null){
			year=date.substring(0,4);
		}
		String month = request.getParameter("seach_insMonth") ;
		if(month==null){
			month=date.substring(5,7);
		}
		
		String armonth=year+month;
		paramMap.put("arMonth", armonth);
		returnCnt = arVacationDao.getLeaveViewListCnt(paramMap) ;
		
		return returnCnt ;
	}
	
	
	/**
	 * 考勤机当月考勤状态是否关闭（锁定）
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int RetrieveAttStatus(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;

		paramMap.put("CPNY_ID", admin.getCpnyId().toString());
		paramMap.put("flag", 1);//月考勤锁定标识
		
		return arVacationDao.RetrieveAttStatus(paramMap);
	}
	
	/**
	 * 是否已经更新月年假
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int monthVacationCnt(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;

		paramMap.put("CPNY_ID", admin.getCpnyId().toString());
		paramMap.put("flag", 1);
		
		return arVacationDao.monthVacationCnt(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public String monthVacCreate(HttpServletRequest request) {
		String returnString = "" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		returnString = arVacationDao.monthVacCreate(paramMap) ;
		
		return returnString ;
	}
	
	@Override
	public int getArVacationUpdateYearCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("supervisor", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = request.getParameter("vac_id") ;
		if(year == null){
				year=new java.text.SimpleDateFormat("yyyy").format(new GregorianCalendar().getTime());
		} 
		paramMap.put("year", year);
		
		return arVacationDao.getArVacationUpdateYearCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	public List getArVacationUpdateYearList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("supervisor", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String year = request.getParameter("vac_id") ;
		if(year == null){
				year=new java.text.SimpleDateFormat("yyyy").format(new GregorianCalendar().getTime());
		} 
		paramMap.put("year", year);
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = arVacationDao.getArVacationUpdateYearList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}else{
			retrunList = arVacationDao.getArVacationUpdateYearList(paramMap,-1,-1) ;
		}
		return retrunList ;
	}


	
	
	
	

	@SuppressWarnings("unchecked")
	public String yearVacCreate(HttpServletRequest request) {
		String returnString = "" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String year = request.getParameter("vac_id") ;
		paramMap.put("vac_id",year); 
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("adminId", admin.getPersonId());
		returnString = arVacationDao.yearVacCreate(paramMap) ;
		
		return returnString ;
	}
	

	
	@SuppressWarnings("unchecked")
	public List getArVacationNextList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("supervisor", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String vac_id = request.getParameter("vac_id") ;
		if(vac_id == null){
			vac_id=new java.text.SimpleDateFormat("yyyy").format(new GregorianCalendar().getTime());
		} 
		paramMap.put("vac_id", vac_id);
		paramMap.put("year", vac_id);
		if (UiUtil.getPageNum(request) > 0){
			retrunList = arVacationDao.getArVacationNextList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}else{
			retrunList = arVacationDao.getArVacationNextList(paramMap,-1,-1) ;
		}
		return retrunList ;
	}

	@SuppressWarnings("unchecked")
	public int updateArVacationYear(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("adminId", admin.getPersonId());
		try {
			
			this.arVacationDao.updateArVacationYear(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int yearVacationCnt(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;

		paramMap.put("CPNY_ID", admin.getCpnyId().toString());
		return arVacationDao.yearVacationCnt(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public String yearVacCalculate(HttpServletRequest request) {
		String returnString = "" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnString = arVacationDao.yearVacCalculate(paramMap) ;
		
		return returnString ;
	}
	
	
	
	@Override
	public int getArVacationNextCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("supervisor", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String vac_id = request.getParameter("vac_id") ;
		if(vac_id == null){
			vac_id=new java.text.SimpleDateFormat("yyyy").format(new GregorianCalendar().getTime());
		} 
		paramMap.put("vac_id", vac_id);
		paramMap.put("year", vac_id);
		return arVacationDao.getArVacationNextCnt(paramMap) ;
	}
	
	


	@SuppressWarnings("unchecked")
	public String nextVacationMove(HttpServletRequest request) {
		String returnString = "" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String year = request.getParameter("vac_id") ;
		paramMap.put("year",year); 
		paramMap.put("nextyear",(Integer.parseInt(year)+1));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("adminId", admin.getPersonId());
		returnString = arVacationDao.nextVacationMove(paramMap) ;
		
		return returnString ;
	}
	
	@SuppressWarnings("unchecked")
	public String getTAWelfare(HttpServletRequest request) {
		String returnString = "" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		returnString = arVacationDao.getTAWelfare(paramMap);
		
		return returnString ;
	}
	
}
