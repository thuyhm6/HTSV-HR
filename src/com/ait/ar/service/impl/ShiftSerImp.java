package com.ait.ar.service.impl;

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
import org.springframework.util.NumberUtils;

import bsh.StringUtil;

import com.ait.ar.dao.ShiftDao;
import com.ait.ar.service.ShiftSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil1;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ShiftSerImp.java
 * @Description:
 * @Create date: 2012-1-9 下午02:52:50
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ShiftSerImp implements ShiftSer {

	Logger logger = Logger.getLogger(ShiftSerImp.class);
	
	@Autowired
	private ShiftDao shiftDao;
	
	@SuppressWarnings("unchecked")
	public Object getShift(HttpServletRequest request) {
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return this.shiftDao.getShift(paramMap) ; 
	}
	
	/**
	 * 显示班次列表(get Shift List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getShiftList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return shiftDao.getShiftList(paramMap);
	}
	
	/**
	 * 显示班次列表(get Shift List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getShiftList1(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String userName = admin.getUsername()!=null?admin.getUsername().toString():"";
		/*if(!"IT".equals(userName)){
			paramMap.put("DEPT_DISTINGUISH_NO", "1");
		}*/
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		return shiftDao.getShiftList(paramMap);
	}
	/**
	 * (get Shift List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getWorkTimeLsit(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String userName = admin.getUsername()!=null?admin.getUsername().toString():"";
		/*if(!"IT".equals(userName)){
			paramMap.put("DEPT_DISTINGUISH_NO", "1");
		}*/
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		
		return shiftDao.getWorkTimeLsit(paramMap);
	}
	/**
	 * (get Shift List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getDateTypeLsit(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
	
		return shiftDao.getDateTypeLsit(paramMap);
	}
	/**
	 * (get Shift List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getWorkTimeLsit1(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		return shiftDao.getWorkTimeLsit1(paramMap);
	}
	/**
	 * (get Shift List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getWorkTimeLsit2(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		return shiftDao.getWorkTimeLsit2(paramMap);
	}
	
	/**
	 * 保存班次信息(add Shift Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addShiftInfo(HttpServletRequest request){
		
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.remove("SHIFT_PARAMETER") ;
		paramMap.put("CREATED_BY", admin.getPersonId()) ;
		
		// 页面提交的JSON信息
		String jsonString = request.getParameter("SHIFT_PARAMETER") ;

		List<LinkedHashMap<String, Object>> insertShiftParameterList = ObjectBindUtil.getRequestJsonData(jsonString, paramMap) ;
		
		paramMap.put("insertShiftParameterList", insertShiftParameterList) ;
	 
		LinkedHashMap tempMap = new LinkedHashMap() ;
		tempMap.put("PERSON_ID", admin.getPersonId());
		/*String distinguishNo = "";
		distinguishNo = this.shiftDao.getDeptDistinguishNo(tempMap) ;
		
		paramMap.put("distinguishNo", distinguishNo);*/
		String OT_TIME_START = "";
		String F_WORK_TIME = "";
		String T_WORK_TIME = "";
		String DATATYPE = "";
		String SHIFT_WORK_TIOME = "";
        
		if (StringUtil1.checkNull( paramMap.get("F_WORK_TIME_H")).equals("")||StringUtil1.checkNull( paramMap.get("F_WORK_TIME_M")).equals("")
			||StringUtil1.checkNull( paramMap.get("T_WORK_TIME_H")).equals("")||StringUtil1.checkNull( paramMap.get("T_WORK_TIME_M")).equals("")	) {
			F_WORK_TIME="";
			T_WORK_TIME="";
		}else {
			F_WORK_TIME=paramMap.get("F_WORK_TIME_H").toString()+paramMap.get("F_WORK_TIME_M");
			T_WORK_TIME=paramMap.get("T_WORK_TIME_H").toString()+paramMap.get("T_WORK_TIME_M");
		}
		if (StringUtil1.checkNull( paramMap.get("DATATYPE")).equals("")) {
			DATATYPE="";
		}else {
			DATATYPE=paramMap.get("DATATYPE").toString();
		}
		if (!"".equals(DATATYPE)) {
			if ("1440".equals(DATATYPE)) {
				SHIFT_WORK_TIOME = F_WORK_TIME+"-"+T_WORK_TIME;
			}else {
				SHIFT_WORK_TIOME = F_WORK_TIME+"-"+T_WORK_TIME+"休";
			}
		}else{
			SHIFT_WORK_TIOME="0800-1700";
		}
		paramMap.put("SHIFT_WORK_TIOME", SHIFT_WORK_TIOME);
		if (StringUtil1.checkNull( paramMap.get("OT_TIME_START_H")).equals("")||StringUtil1.checkNull( paramMap.get("OT_TIME_START_M")).equals("")) {
			 OT_TIME_START = "";
			
		}else{
			 OT_TIME_START = paramMap.get("OT_TIME_START_H").toString()+":"+paramMap.get("OT_TIME_START_M").toString();
		}
		paramMap.put("OT_TIME_START", OT_TIME_START);
		//String otAllowance=
		
		try {
			
			this.shiftDao.addShiftInfo(paramMap) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 修改保存(update ShiftInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateShiftInfo(HttpServletRequest request) {
		
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATE_BY", admin.getAdminID()) ;
		paramMap.remove("SHIFT_PARAMETER") ;
		
		// 页面提交的JSON信息
		String jsonString = request.getParameter("SHIFT_PARAMETER") ;

		List<LinkedHashMap<String, Object>> insertShiftParameterList = ObjectBindUtil.getRequestJsonData(jsonString, paramMap) ;
		
		paramMap.put("insertShiftParameterList", insertShiftParameterList) ;
		
		
		return this.shiftDao.updateShiftInfo(paramMap);
		
	}
	
	/**
	 * 修改保存(update ShiftInfoByNO)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateShiftInfoByNO(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//修改人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getPersonId());
		try {
			String timeStart = com.ait.web.util.StringUtil.checkNull(paramMap.get("OT_TIME_START_H"));
			String timeEnd = com.ait.web.util.StringUtil.checkNull(paramMap.get("OT_TIME_START_M"));
			if(timeStart.equals("")||timeEnd.equals(""))
				paramMap.put("OT_TIME_START", "");
			else
				paramMap.put("OT_TIME_START", timeStart+":"+timeEnd);
			String F_WORK_TIME = "";
			String T_WORK_TIME = "";
			String DATATYPE = "";
			String SHIFT_WORK_TIOME = "";
			if (StringUtil1.checkNull( paramMap.get("F_WORK_TIME_H")).equals("")||StringUtil1.checkNull( paramMap.get("F_WORK_TIME_M")).equals("")
					||StringUtil1.checkNull( paramMap.get("T_WORK_TIME_H")).equals("")||StringUtil1.checkNull( paramMap.get("T_WORK_TIME_M")).equals("")	) {
					F_WORK_TIME="";
					T_WORK_TIME="";
				}else {
					F_WORK_TIME=paramMap.get("F_WORK_TIME_H").toString()+paramMap.get("F_WORK_TIME_M");
					T_WORK_TIME=paramMap.get("T_WORK_TIME_H").toString()+paramMap.get("T_WORK_TIME_M");
				}
				if (StringUtil1.checkNull( paramMap.get("DATATYPE")).equals("")) {
					DATATYPE="";
				}else {
					DATATYPE=paramMap.get("DATATYPE").toString();
				}
				if (!"".equals(DATATYPE)) {
					if ("1440".equals(DATATYPE)) {
						SHIFT_WORK_TIOME = F_WORK_TIME+"-"+T_WORK_TIME;
					}else {
						SHIFT_WORK_TIOME = F_WORK_TIME+"-"+T_WORK_TIME+"休";
					}
				}else{
					SHIFT_WORK_TIOME="0800-1700";
				}
				paramMap.put("SHIFT_WORK_TIOME", SHIFT_WORK_TIOME);
			this.shiftDao.updateShiftInfoByNO(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
		
		
	}
	
	/**
	 * 删除班次(delete Shift)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int deleteShiftInfo(HttpServletRequest request) {
		
		// 页面提交数据
//		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
//		return this.shiftDao.deleteShiftInfo(paramMap) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("PERSON_ID",admin.getPersonId());
		
		try {
			
			this.shiftDao.deleteShiftInfo(paramMap) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
		
	}

	@Override
	public int getShiftCnt(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		return shiftDao.getShiftCnt(paramMap) ;
	}
	
	/**
	 * 查询班次参数列表(get ShiftParameter List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getShiftParameterList(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return shiftDao.getShiftParameterList(paramMap) ;
	}
	
	/**
	 * 检查班次信息(add Shift Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int checkShiftInfo(HttpServletRequest request){
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		LinkedHashMap tempMap = new LinkedHashMap() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		tempMap.put("PERSON_ID", admin.getPersonId());
		/*String distinguishNo = "";
		distinguishNo = this.shiftDao.getDeptDistinguishNo(tempMap) ;*/
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//paramMap.put("distinguishNo", distinguishNo);
		
		return this.shiftDao.checkShiftInfo(paramMap);
	}

	/**
	 * 查询班次列表(get Item List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@Override
	public List getItemList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List retrunList = new ArrayList() ;
		// session用户信息
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return shiftDao.getItemList(paramMap);
	}
	
	
	/**
	 * G/P平均值列表(get Item List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@Override
	public List calculateAvg(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List retrunList = new ArrayList() ;
		// session用户信息
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return shiftDao.calculateAvg(paramMap);
	}

}
