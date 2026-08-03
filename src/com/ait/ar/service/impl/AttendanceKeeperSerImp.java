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

import com.ait.ar.dao.AttendanceKeeperDao;
import com.ait.ar.service.AttendanceKeeperSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AttendanceKeeperSerImp.java
 * @Description:
 * @Create date: 2012-1-14 下午01:46:30
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class AttendanceKeeperSerImp implements AttendanceKeeperSer {

	Logger logger = Logger.getLogger(AttendanceKeeperSerImp.class);
	
	@Autowired
	private AttendanceKeeperDao attendanceKeeperDao;
	
	/**
	 * 取考勤员信息(get AttendanceKeeper)
	 * @param request
	 * @return Object
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getAttendanceKeeper(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("language",Messages.getLanguage(request));
		return this.attendanceKeeperDao.getAttendanceKeeper(paramMap) ; 
	}
	
	
	
	/**
	 * 查看考勤员列表(get AttendanceKeeper List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getAttendanceKeeperList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID",admin.getCpnyId());
		/*if(!"".equals(request.getParameter("PAY_AREA_NO")) && request.getParameter("PAY_AREA_NO")!=null){
			paramMap.put("PAY_AREA_NO", request.getParameter("PAY_AREA_NO").toString().replaceAll("!", ","));
		}*/
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				attendanceKeeperDao.getAttendanceKeeperList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = attendanceKeeperDao.getAttendanceKeeperList(paramMap) ;
		}
		
		return retrunList;
	}
	
	/**
	 * 取考勤员数量(get AttendanceKeeper count)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int getAttendanceKeeperCnt(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID",admin.getCpnyId());
		
		return attendanceKeeperDao.getAttendanceKeeperCnt(paramMap) ;
	}
	
	/**
	 * 取考勤员列表(get AttendanceKeeper count)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getAttendanceKeeperDeptList(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		List maxDeptList = this.attendanceKeeperDao.getMaxDeptList(paramMap);
		List personDeptList = this.attendanceKeeperDao.getAttendanceKeeperDeptList(paramMap);
		

		for (int i = 0 ; i < maxDeptList.size() ; ++i ){
			Map deptMap = (LinkedHashMap)maxDeptList.get(i) ;
			
			this.setChildDeptList(personDeptList, deptMap) ;
			
		}
		return maxDeptList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int addAttendanceKeeperInfo(HttpServletRequest request){
		
		// session用户信息
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("CREATED_IP", admin.getAdminIP());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.remove("jsonData");
		
		// 页面提交的JSON信息
		String jsonString = request.getParameter("jsonData") ;

		String[] postNos=request.getParameterValues("isChecked");
		paramMap.put("postNos", postNos);
		List<LinkedHashMap<String, Object>> insertAttendanceKeeperList = ObjectBindUtil.getRequestJsonData(jsonString) ;
		paramMap.put("deptTree", insertAttendanceKeeperList);
        
		return this.attendanceKeeperDao.addAttendanceKeeperInfo(paramMap);
	}
	
	/**
	 * 修改保存(update AttendanceKeeper Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public int updateAttendanceKeeperInfo(HttpServletRequest request) {
		
		// session用户信息
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.remove("jsonData");
		
		// 页面提交的JSON信息
		String jsonString = request.getParameter("jsonData") ;

		String[] postNos=request.getParameterValues("isChecked");
		paramMap.put("postNos", postNos);
		List<LinkedHashMap<String, Object>> insertAttendanceKeeperList = ObjectBindUtil.getRequestJsonData(jsonString) ;
		paramMap.put("deptTree", insertAttendanceKeeperList);

		return this.attendanceKeeperDao.updateAttendanceKeeperInfo(paramMap);
	}
	
	/**
	 * 删除考勤员信息(delete AttendanceKeeper Info)
	 * @param request
	 * @return int
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int deleteAttendanceKeeperInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.attendanceKeeperDao.deleteAttendanceKeeperInfo(paramMap);
	}

	/**
	 * 取得考勤部门列表(get AttendanceDept List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getAttendanceDeptList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		List maxDeptList = this.attendanceKeeperDao.getMaxDeptList(paramMap);
		List personDeptList = this.attendanceKeeperDao.getAttendanceDeptList(paramMap);
		

		for (int i = 0 ; i < maxDeptList.size() ; ++i ){
			Map deptMap = (LinkedHashMap)maxDeptList.get(i) ;
			
			this.setChildDeptList(personDeptList, deptMap) ;
			
		}
		return maxDeptList ;
	}
	
	/**
	 * 设置子部门信息(set ChildDept List)
	 * @param List
	 * @param Map
	 * @return
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setChildDeptList(List loginUserDeptList, Map deptMap){
		List deptList = new ArrayList(loginUserDeptList.size()) ;
		
		// 复制,然后清除deptMap,减少循环
		deptList.addAll(loginUserDeptList) ;
		deptList.remove(deptMap) ;
		
		List childDeptList = new ArrayList() ;
		
		int deptSize = deptList.size() ;
		for(int i = 0 ; i < deptSize ; ++i ){
			Map deptMapT = (LinkedHashMap)deptList.get(i) ;
			
			if(deptMapT.get("PARENT_DEPT_NO") != null && deptMapT.get("PARENT_DEPT_NO").equals(deptMap.get("DEPTNO"))){
				
				childDeptList.add(deptMapT) ;
				
				this.setChildDeptList(deptList, deptMapT) ;
			}
		}
		
		if(!childDeptList.isEmpty()){
			deptMap.put("childDeptList", childDeptList) ;
		}
	}

	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getPersonListView(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID",admin.getCpnyId());
		if(paramMap.get("LIZHI") != null && "1".equals(paramMap.get("LIZHI").toString())){
			paramMap.put("LIZHI","ALL");
			paramMap.put("LIZHIDATE","ALL");
		}
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		String supervisor = request.getParameter("supervisor") != null ? request.getParameter("supervisor") : "";
		
		if(!"".equals(supervisor)){
			paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		}else{
			paramMap.put("specialParam", admin.getSpecialParam());
			paramMap.put("userNo", admin.getUserNo());
			paramMap.put("deptNo", admin.getDeptNo());
			paramMap.put("ADMIN_ID", admin.getAdminID());
		}
		
		retrunList = attendanceKeeperDao.getPersonListView(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList;
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List viewKeeperListOtApplyCheck(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("LIZHI","ALL");
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		String supervisor = request.getParameter("supervisor") != null ? request.getParameter("supervisor") : "";
		
		if(!"".equals(supervisor)){
			paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		}else{
			paramMap.put("specialParam", admin.getSpecialParam());
			paramMap.put("userNo", admin.getUserNo());
			paramMap.put("deptNo", admin.getDeptNo());
			paramMap.put("ADMIN_ID", admin.getAdminID());
		}
		
		retrunList = attendanceKeeperDao.viewKeeperListOtApplyCheck(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList;
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getPersonListView2(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("LIZHI","ALL");
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		String supervisor = request.getParameter("supervisor") != null ? request.getParameter("supervisor") : "";
		
		if(!"".equals(supervisor)){
			paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		}else{
			paramMap.put("specialParam", admin.getSpecialParam());
			paramMap.put("userNo", admin.getUserNo());
			paramMap.put("deptNo", admin.getDeptNo());
			paramMap.put("ADMIN_ID", admin.getAdminID());
		}
		
		retrunList = attendanceKeeperDao.getPersonListView(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList;
	}

	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPersonListCnt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID",admin.getCpnyId());
		if(paramMap.get("LIZHI") != null && "1".equals(paramMap.get("LIZHI").toString())){
			paramMap.put("LIZHIDATE","ALL");
			paramMap.put("LIZHI","ALL");
		}
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		String supervisor = request.getParameter("supervisor") != null ? request.getParameter("supervisor") : "";
		
		if(!"".equals(supervisor)){
			paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		}else{
			paramMap.put("specialParam", admin.getSpecialParam());
			paramMap.put("userNo", admin.getUserNo());
			paramMap.put("deptNo", admin.getDeptNo());
			paramMap.put("ADMIN_ID", admin.getAdminID());
		}
		
		return attendanceKeeperDao.getPersonListCnt(paramMap) ;
	}
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPersonListOtApplyCheckCnt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("LIZHI","ALL");
		paramMap.put("PERSON_ID", admin.getPersonId());
        //paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		
		String supervisor = request.getParameter("supervisor") != null ? request.getParameter("supervisor") : "";
		
		if(!"".equals(supervisor)){
			paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		}else{
			paramMap.put("specialParam", admin.getSpecialParam());
			paramMap.put("userNo", admin.getUserNo());
			paramMap.put("deptNo", admin.getDeptNo());
			paramMap.put("ADMIN_ID", admin.getAdminID());
		}
		
		return attendanceKeeperDao.getPersonListOtApplyCheckCnt(paramMap) ;
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getEmpCalendarList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("USERNO",admin.getUserNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		
		retrunList = attendanceKeeperDao.getEmpCalendarList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList;
	}

	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@Override
	public int getEmpCalendarCnt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("USERNO",admin.getUserNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		return attendanceKeeperDao.getEmpCalendarCnt(paramMap) ;
	}
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getEmpCalendar2List(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("USERNO",admin.getUserNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		
		retrunList = attendanceKeeperDao.getEmpCalendar2List(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList;
	}

	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@Override
	public int getEmpCalendar2Cnt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("USERNO",admin.getUserNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		return attendanceKeeperDao.getEmpCalendar2Cnt(paramMap) ;
	}
	/**
	 * 部门树(get Dept Tree)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getDeptTree(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("AR_SUPERVISOR_ID", request.getParameter("AR_SUPERVISOR_ID") == null ? admin.getAdminID():request.getParameter("AR_SUPERVISOR_ID"));

		return this.attendanceKeeperDao.getDeptTree(paramMap);
	}
	
	/**
	 * 职位列表(get Position List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getPositionList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.attendanceKeeperDao.getPositionList(paramMap);
	}
	/**
	 * 查看部门最终管理列表(get AttendanceKeeper List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getDepartmentManageList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("AR_SUPERVISOR_ID",admin.getAdminID());
		retrunList =  attendanceKeeperDao.getDepartmentManageList(paramMap) ;
		
		return retrunList;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int addDepartManagerInfo(HttpServletRequest request){
		
		try {
			// session用户信息
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.attendanceKeeperDao.addDepartManagerInfo(dataList);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int addDepartManagerUnifyInfo(HttpServletRequest request){
		
		try {
			// session用户信息
			Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
			this.attendanceKeeperDao.addDepartManagerUnifyInfo(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
	}
	
}
