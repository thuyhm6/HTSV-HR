package com.ait.ar.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ar.dao.EmpShiftDao;
import com.ait.ar.service.EmpShiftSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil1;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpShiftSerImp.java
 * @Description:
 * @Create date: 2012-1-17 下午03:39:12
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class EmpShiftSerImp implements EmpShiftSer {
      
	
	Logger logger = Logger.getLogger(EmpShiftSerImp.class);

	@Autowired
	private EmpShiftDao empShiftDao;

	/**
	 * 查看班次信息(get Shift010)
	 * @param
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getShift010() {
		// TODO Auto-generated method stub
		return empShiftDao.getShift010();
	}
	
	/**
	 * 查看班次信息(get Shift010 List)
	 * @param
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getShift010List(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return empShiftDao.getShift010List(paramMap);
	}
	
	/**
	 * 查看班次信息(get Shift010 List)
	 * @param
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getShift010List1(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//paramMap.put("DEPT_DISTINGUISH_NO", "1");
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		return empShiftDao.getShift010List(paramMap);
	}

	/**
	 * 按人员排班(add Emp Shift)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int addEmpShift(HttpServletRequest request) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String FROMDATE = request.getParameter("FROM_DATE");
		String TODATE = request.getParameter("TO_DATE");
		String SHIFT = request.getParameter("shift_no");
		String ADMINID = admin.getAdminID();
		String CPNY_ID = admin.getCpnyId();
		
		List shiftList = new ArrayList();
		String[] empid=request.getParameter("empids").toString().split(",");
		
		for (int i = 0; i < empid.length; i++) {
			
			Map paramMap = new LinkedHashMap();
			
			paramMap.put("TYPE", "EMPLOYEE") ;
			paramMap.put("FROMDATE",FROMDATE);
			paramMap.put("TODATE",TODATE);
			paramMap.put("SHIFT",SHIFT);
			paramMap.put("ADMINID", ADMINID) ;
			paramMap.put("CPNY_ID", CPNY_ID) ;
			paramMap.put("SHIFTID",empid[i]);
			paramMap.put("EMP_TYPE_CODE",request.getParameter("emp_type_code"));
			paramMap.put("CREATED_IP", admin.getAdminIP()) ;
			
			shiftList.add(paramMap);
		}
		
		try {
			
			this.empShiftDao.addEmpShift(shiftList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	  return 1;
	  
	}

	/**
	 * 按部门排班(add EmpShift ByDeptId)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int addEmpShiftByDeptId(HttpServletRequest request) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = new LinkedHashMap();
		String sonDeptFlag = request.getParameter("sonDeptFlag")!=null?request.getParameter("sonDeptFlag").toString():"NO";
		//按部门排班并且包含子部门
		if(!"".equals(sonDeptFlag) && "YES".equals(sonDeptFlag)){
			paramMap.put("TYPE", "DEPARTMENTES") ;
		//按部门排班不包含子部门	
		}else{
			paramMap.put("TYPE", "DEPARTMENT") ;
		}
		paramMap.put("FROMDATE",request.getParameter("FROM_DATE"));
		paramMap.put("TODATE",request.getParameter("TO_DATE"));
		paramMap.put("SHIFT",request.getParameter("shift_no"));
		paramMap.put("ADMINID", admin.getAdminID()) ;		
		paramMap.put("SHIFTID",request.getParameter("dept"));
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("EMP_TYPE_CODE",  request.getParameter("emp_type_code") ) ;
		paramMap.put("CREATED_IP", admin.getAdminIP()) ;
		
		List shiftList = new ArrayList();
		shiftList.add(paramMap);
		try {
			this.empShiftDao.addEmpShift(shiftList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	  return 1;
	}
	@SuppressWarnings("unchecked")
	public int getPersonCountByDep(HttpServletRequest request) throws NumberFormatException, SQLException{
		@SuppressWarnings("unused")
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String sonDeptFlag = request.getParameter("sonDeptFlag")!=null?request.getParameter("sonDeptFlag").toString():"NO";
		//按部门排班并且包含子部门
		if(!"".equals(sonDeptFlag) && "YES".equals(sonDeptFlag)){
			paramMap.put("TYPE", "DEPARTMENTES") ;
		//按部门排班不包含子部门	
		}else{
			paramMap.put("TYPE", "DEPARTMENT") ;
		}
		paramMap.put("DEPTNO",request.getParameter("dept"));
		paramMap.put("SUPERVISORID", admin.getPersonId());
		paramMap.put("EMP_TYPE_CODE", request.getParameter("emp_type_code")) ;
		paramMap.put("FROMDATE",request.getParameter("FROM_DATE"));
		int count = this.empShiftDao.getPersonCountByDep(paramMap);
		return count;
	}
	/**
	 * 按动态组排班(add EmpShift ByDeptId)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int addEmpShiftBydynamicGroup(HttpServletRequest request) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = new LinkedHashMap();
		
		paramMap.put("TYPE", "GROUP") ;
		paramMap.put("FROMDATE",request.getParameter("FROM_DATE"));
		paramMap.put("TODATE",request.getParameter("TO_DATE"));
		paramMap.put("SHIFT",request.getParameter("shift_no"));
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("SHIFTID",request.getParameter("dynamicGroupNo"));
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("CREATED_IP", admin.getAdminIP()) ;
		
		List shiftList = new ArrayList();
		shiftList.add(paramMap);
		
		try {
			
			this.empShiftDao.addEmpShift(shiftList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
			
	  return 1;
	}
	
	 @SuppressWarnings("unchecked")
	public int getPersonCountByDYNAMIC(HttpServletRequest request) throws NumberFormatException, SQLException{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap map = new LinkedHashMap();
		map.put("SHIFTID",request.getParameter("dynamicGroupNo"));
		map.put("CPNY_ID",admin.getCpnyId());
		int count = this.empShiftDao.getPersonCountByDYNAMIC(map);
		return count;
	 }
	 /**
		 * 按动态组排班(add EmpShift ByDeptId)
		 * @param request
		 * @return int
		 * @throws 
		 */
		@SuppressWarnings("unchecked")
		public int addEmpShiftByClassNum(HttpServletRequest request) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unused")
			HttpSession session = request.getSession() ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			Map paramMap = new LinkedHashMap();
			
			paramMap.put("TYPE", "CLASSNUM") ;
			paramMap.put("FROMDATE",request.getParameter("FROM_DATE"));
			paramMap.put("TODATE",request.getParameter("TO_DATE"));
			paramMap.put("SHIFT",request.getParameter("shift_no"));
			paramMap.put("ADMINID", admin.getAdminID()) ;
			paramMap.put("SHIFTID",request.getParameter("calssShiftNo"));
			paramMap.put("CPNY_ID", admin.getCpnyId()) ;
			paramMap.put("CREATED_IP", admin.getAdminIP()) ;
			
			List shiftList = new ArrayList();
			shiftList.add(paramMap);
			
			try {
				
				this.empShiftDao.addClassShift(paramMap);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
				
		  return 1;
		}
	
}
