package com.ait.pa.service.imp.wagebase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.PaSupervisorDao;
import com.ait.pa.service.wagebase.PaSupervisorSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaSupervisorSerImp.java
 * @Description:
 * @Create date: 2012-1-14 下午01:46:30
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class PaSupervisorSerImp implements PaSupervisorSer {

	Logger logger = Logger.getLogger(PaSupervisorSerImp.class);
	
	@Autowired
	private PaSupervisorDao paSupervisorDao;
	
	/**
	 * 取考勤员信息(get PaSupervisor)
	 * @param request
	 * @return Object
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Object getPaSupervisor(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("language",Messages.getLanguage(request));
		return this.paSupervisorDao.getPaSupervisor(paramMap) ; 
	}
	
	/**
	 * 查看考勤员列表(get PaSupervisor List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getPaSupervisorList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID",admin.getCpnyId());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				paSupervisorDao.getPaSupervisorList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paSupervisorDao.getPaSupervisorList(paramMap) ;
		}
		
		return retrunList;
	}
	
	/**
	 * 取考勤员数量(get PaSupervisor count)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPaSupervisorCnt(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID",admin.getCpnyId());
		
		return paSupervisorDao.getPaSupervisorCnt(paramMap) ;
	}
	
	/**
	 * 取考勤员列表(get PaSupervisor count)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaSupervisorDeptList(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		List maxDeptList = this.paSupervisorDao.getMaxDeptList(paramMap);
		List personDeptList = this.paSupervisorDao.getPaSupervisorDeptList(paramMap);
		

		for (int i = 0 ; i < maxDeptList.size() ; ++i ){
			Map deptMap = (LinkedHashMap)maxDeptList.get(i) ;
			
			this.setChildDeptList(personDeptList, deptMap) ;
			
		}
		return maxDeptList;
	}
	
	@SuppressWarnings("unchecked")
	public int addPaSupervisorInfo(HttpServletRequest request){
		
		// session用户信息
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.remove("jsonData");
		
		// 页面提交的JSON信息
		String jsonString = request.getParameter("jsonData") ;

		String[] postNos=request.getParameterValues("isChecked");
		paramMap.put("postNos", postNos);
		List<LinkedHashMap<String, Object>> insertPaSupervisorList = ObjectBindUtil.getRequestJsonData(jsonString) ;
		paramMap.put("deptTree", insertPaSupervisorList);

		return this.paSupervisorDao.addPaSupervisorInfo(paramMap) ;
	}
	
	/**
	 * 修改保存(update PaSupervisor Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int updatePaSupervisorInfo(HttpServletRequest request) {
		
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
		List<LinkedHashMap<String, Object>> insertPaSupervisorList = ObjectBindUtil.getRequestJsonData(jsonString) ;
		paramMap.put("deptTree", insertPaSupervisorList);

		return this.paSupervisorDao.updatePaSupervisorInfo(paramMap);
	}
	
	/**
	 * 删除考勤员信息(delete PaSupervisor Info)
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int deletePaSupervisorInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.paSupervisorDao.deletePaSupervisorInfo(paramMap);
	}

	/**
	 * 取得考勤部门列表(get AttendanceDept List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
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
		List maxDeptList = this.paSupervisorDao.getMaxDeptList(paramMap);
		List personDeptList = this.paSupervisorDao.getAttendanceDeptList(paramMap);
		

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
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonListView(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		String supervisor = request.getParameter("supervisor") != null ? request.getParameter("supervisor") : "";
		
		if(!"".equals(supervisor)){
			paramMap.put("PA_SUPERVISOR_ID", admin.getPersonId());
		}else{
			paramMap.put("specialParam", admin.getSpecialParam());
			paramMap.put("userNo", admin.getUserNo());
			paramMap.put("deptNo", admin.getDeptNo());
			paramMap.put("ADMIN_ID", admin.getAdminID());
		}
		
		retrunList = paSupervisorDao.getPersonListView(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
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
//		paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		
		String supervisor = request.getParameter("supervisor") != null ? request.getParameter("supervisor") : "";
		
		if(!"".equals(supervisor)){
			paramMap.put("PA_SUPERVISOR_ID", admin.getPersonId());
		}else{
			paramMap.put("specialParam", admin.getSpecialParam());
			paramMap.put("userNo", admin.getUserNo());
			paramMap.put("deptNo", admin.getDeptNo());
			paramMap.put("ADMIN_ID", admin.getAdminID());
		}
		
		return paSupervisorDao.getPersonListCnt(paramMap) ;
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpCalendarList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		
		paramMap.put("PA_SUPERVISOR_ID", admin.getPersonId());
		
		retrunList = paSupervisorDao.getEmpCalendarList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
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
	public int getEmpCalendarCnt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("PA_SUPERVISOR_ID", admin.getPersonId());
		return paSupervisorDao.getEmpCalendarCnt(paramMap) ;
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
		paramMap.put("PA_SUPERVISOR_ID", request.getParameter("PA_SUPERVISOR_ID"));

		return this.paSupervisorDao.getDeptTree(paramMap);
	}
	

	/**
	 * 部门树(get Dept Tree)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getDeptTreeResumeNo(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;

		return this.paSupervisorDao.getDeptTreeResumeNo(paramMap);
	}
}
