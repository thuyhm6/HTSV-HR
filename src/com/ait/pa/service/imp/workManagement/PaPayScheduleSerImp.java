package com.ait.pa.service.imp.workManagement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.PaPayScheduleDao;
import com.ait.pa.dao.PaProgressDao;
import com.ait.pa.service.salary.PaProgressSer;
import com.ait.pa.service.workManagement.PaPayScheduleSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class PaPayScheduleSerImp implements PaPayScheduleSer {

	Logger logger = Logger.getLogger(PaPayScheduleSerImp.class);
	
	@Autowired
	private PaPayScheduleDao paPayScheduleDao ;
	
	@SuppressWarnings("unchecked")
	public List getPayScheduleList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		// 判断支付日期参数是否为空,则不查数据
		if(paramMap.get("START_PAY_DATE")!=null || paramMap.get("END_PAY_DATE")!=null){
			retrunList = paPayScheduleDao.getPayScheduleList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request,20)) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPayScheduleAllList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		retrunList = paPayScheduleDao.getPayScheduleAllList(paramMap) ;
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPayScheduleAllWithPaConfirmList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		retrunList = paPayScheduleDao.getPayScheduleAllWithPaConfirmList(paramMap) ;
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getPayScheduleCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		// 判断支付日期参数是否为空,则不查数据
		if(paramMap.get("START_PAY_DATE")!=null || paramMap.get("END_PAY_DATE")!=null){
			retrunInt = paPayScheduleDao.getPayScheduleCnt(paramMap) ;
		}
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public int addPaPayScheduleInfo(HttpServletRequest request){
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		try {
			
			this.paPayScheduleDao.addPaPayScheduleInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public Object getPaPayScheduleInfo(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.paPayScheduleDao.getPaPayScheduleInfo(paramMap) ; 
	}
	/**
	 * 更新区间信息(update PaPaySchedule Info)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatePaPayScheduleInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			
			this.paPayScheduleDao.updatePaPayScheduleInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
	/**
	 * 确认或解除工资支付计划信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int confirmOrRelievePaySchedule(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			
			this.paPayScheduleDao.confirmOrRelievePaySchedule(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
	/**
	 * 删除工资支付计划信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int doDeletePayScheduleInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			
			this.paPayScheduleDao.doDeletePayScheduleInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List dayPersonCountInfoList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		retrunList = paPayScheduleDao.getPayScheduleAllList(paramMap) ;
		return retrunList ;
	}
	
	 
	
}
