package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.changeDao;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.LoginUserDao;
import com.ait.sys.service.LoginUserSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class LoginUserSerImp implements LoginUserSer {

	Logger logger = Logger.getLogger(LoginUserSerImp.class);
	
	@Autowired
	private LoginUserDao loginUserDao;
	
	@Autowired
	private changeDao changeDao;
	
	@SuppressWarnings("unchecked")
	public Object getLoginUser(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		return this.loginUserDao.getLoginUser(paramMap) ; 
	}
	
	@SuppressWarnings("unchecked")
	public List getLoginUserRolesGroupList(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.loginUserDao.getLoginUserRolesGroupList(paramMap) ; 
	}
	@SuppressWarnings("unchecked")
	public List getloginUserInfoRolesGSODList(HttpServletRequest request){
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.loginUserDao.getloginUserInfoRolesGSODList(paramMap);
	}
	

	/**
		 * 取得员工类别(get EmpTypeCode List)
		 * @param request
		 * @return List
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public List getEmpTypeCodeList(HttpServletRequest request) {
			
			List retrunList = new ArrayList() ;
			Map paramMap = ObjectBindUtil.getRequestParamData(request);
			
			return this.loginUserDao.getEmpTypeCodeList(paramMap);
		}

	/**
		 * 取得员工类别(get EmpTypeCode List)
		 * @param request
		 * @return List
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public List getSySupervisorEmpTypeCodeList(HttpServletRequest request) {
			
			Map paramMap = ObjectBindUtil.getRequestParamData(request);
			
			return this.loginUserDao.getSySupervisorEmpTypeCodeList(paramMap);
		}



	
	
	@SuppressWarnings("unchecked")
	public List getLoginUserList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
//		String user_name = admin.getUsername()!=null?admin.getUsername().toString():"IT";
		//如果不是IT用户登陆的话，就不能看到IT账号的登陆信息和密码
//		if(!"IT".equals(user_name)){
//			paramMap.put("IT_YN", "IT_NOT");
//		}
		if(request.getParameter("seach_IT_YN")==null)
			paramMap.put("IT_YN", "IT_NOT");
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				loginUserDao.getLoginUserList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;					
		}else{
			retrunList = loginUserDao.getLoginUserList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getLoginUserCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
//		String user_name = admin.getUsername()!=null?admin.getUsername().toString():"IT";
//		//如果不是IT用户登陆的话，就不能看到IT账号的登陆信息和密码
//		if(!"IT".equals(user_name)){
//			paramMap.put("IT_YN", "IT_NOT");
//		}
		if(request.getParameter("seach_IT_YN")==null)
			paramMap.put("IT_YN", "IT_NOT");
		return loginUserDao.getLoginUserCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	public List getLoginUserExcelList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String user_name = admin.getUsername()!=null?admin.getUsername().toString():"IT";
		//如果不是IT用户登陆的话，就不能看到IT账号的登陆信息和密码
		if(!"IT".equals(user_name)){
			paramMap.put("IT_YN", "IT_NOT");
		}
		
		retrunList = loginUserDao.getLoginUserList(paramMap) ;
		
		return retrunList ;
	}
	
	// 数据,总条数,统一取得Map方法
	@SuppressWarnings({ "unchecked", "unused" })
	private Map getLoginUserParamMap(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return paramMap ;
	}
	
	@SuppressWarnings("unchecked")
	public int addLoginUserInfo(HttpServletRequest request){
		try{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.remove("jsonData") ;
			paramMap.put("UPDATED_BY", admin.getAdminID()) ;
			String[] screenGrantNos = request.getParameterValues("SCREEN_GRANT_NO") ;
			paramMap.put("SCREEN_GRANT_NO", screenGrantNos) ;
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> insertLoginUserDeptList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			paramMap.put("insertLoginUserDeptList", insertLoginUserDeptList) ;
			paramMap.put("CREATED_BY", admin.getAdminID());
			this.loginUserDao.addLoginUserInfo(paramMap) ;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int updateLoginUserInfo(HttpServletRequest request) {
		try{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.remove("jsonData") ;
			paramMap.put("UPDATED_BY", admin.getAdminID()) ;
			String[] screenGrantNos = request.getParameterValues("SCREEN_GRANT_NO") ;
			paramMap.put("SCREEN_GRANT_NO", screenGrantNos) ;
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> insertLoginUserDeptList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			paramMap.put("insertLoginUserDeptList", insertLoginUserDeptList) ;
			String[] postNos=request.getParameterValues("isChecked");
			paramMap.put("postNos", postNos);
			paramMap.put("CREATED_BY", admin.getAdminID());
			this.loginUserDao.updateLoginUserInfo(paramMap) ;
			//密码修改
			 
			 paramMap.put("NEW_PS", request.getParameter("NEW_PS"));
			loginUserDao.changePassword(paramMap);
			 
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public int deleteLoginUserInfo(HttpServletRequest request) {
		return 0 ;
	}
	
	@SuppressWarnings("unchecked")
	public List getLoginUserDeptList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(paramMap.get("USERNO")==null||(paramMap.get("USERNO")!=null&&paramMap.get("USERNO").toString().equals(""))){
			paramMap.put("USERNO", paramMap.get("NO"));
		}
		List loginUserMaxDeptList = loginUserDao.getLoginUserMaxDeptList(paramMap) ;
		List loginUserDeptList = loginUserDao.getLoginUserDeptList(paramMap) ;
		for (int i = 0 ; i < loginUserMaxDeptList.size() ; ++i ){
			Map deptMap = (LinkedHashMap)loginUserMaxDeptList.get(i) ;
			this.setChildDeptList(loginUserDeptList, deptMap) ;
		}
		return loginUserMaxDeptList ;
	}
	
	
	@SuppressWarnings("unchecked")
	private void setChildDeptList(List loginUserDeptList, Map deptMap){
		List deptList = new ArrayList(loginUserDeptList.size()) ;
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
	
	@SuppressWarnings("unchecked")
	public List getLoginUserRoleGroupRelation(HttpServletRequest request,Object obj){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("USERNO", obj);
		return this.loginUserDao.getLoginUserRolesGroupList(paramMap) ; 
	}
	
	@SuppressWarnings("unchecked")
	public List getDeptInfoTree(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.loginUserDao.getDeptInfoTree(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public int validatePersonIdExist(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		int result=0;
		try {
			result=this.loginUserDao.validatePersonIdExist(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteLoginUser(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		int result=0;
		try {
			this.loginUserDao.deleteLoginUser(paramMap);
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
			result=0;
		} 
		return result;
	}
	
	

	@SuppressWarnings("unchecked")
	public List getLoginUserIPList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
  
			retrunList = loginUserDao.getLoginUserIPList(paramMap) ;
	 
		return retrunList ;
	}
	
	
	@SuppressWarnings("unchecked")
	public List viewFileInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
  
			retrunList = loginUserDao.viewFileInfoList(paramMap) ;
	 
		return retrunList ;
	}
	
	
	@SuppressWarnings("unchecked")
	public List viewFileUrlInfo(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
  
			retrunList = loginUserDao.viewFileUrlInfo(paramMap) ;
	 
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public Object viewFileInfo(HttpServletRequest request) {
		Object Ob=new Object();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
  
		 Ob = loginUserDao.viewFileInfo(paramMap) ;
	 
		return  Ob ;
	}
	
	

	@SuppressWarnings("unchecked")
	public int addFileInfo(HttpServletRequest request){
		try{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			 
			this.loginUserDao.addFileInfo(paramMap) ;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int updateFileInfo(HttpServletRequest request) {
		try{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			 
			paramMap.put("CREATED_BY", admin.getAdminID());
			this.loginUserDao.updateFileInfo(paramMap) ;
			 
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public int deleteFileInfo(HttpServletRequest request) {
		return 0 ;
	}
	
	
	
}
