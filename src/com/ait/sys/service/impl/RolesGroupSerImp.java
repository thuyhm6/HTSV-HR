package com.ait.sys.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.RolesGroupDao;
import com.ait.sys.service.RolesGroupSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class RolesGroupSerImp implements RolesGroupSer {

	Logger logger = Logger.getLogger(RolesGroupSerImp.class);
	
	@Autowired
	private RolesGroupDao rolesGroupDao;
	
	@SuppressWarnings("unchecked")
	public Object getRolesGroup(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(request.getParameter("NO")!=null&&!request.getParameter("NO").equals("")){
			paramMap.put("ROLE_NO", request.getParameter("NO"));
		}
		return this.rolesGroupDao.getRolesGroup(paramMap) ; 
	}
	
	@SuppressWarnings("unchecked")
	public List getRolesGroupList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = this.getRolesGroupParamMap(request) ;
		if(request.getParameter("ROLE_ID")!=null&&request.getParameter("CPNY_ID")!=null){
			paramMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
			paramMap.put("ROLE_ID", request.getParameter("ROLE_ID"));
		}
		if(request.getParameter("SYS_TYPE")!=null)
			paramMap.put("SYS_TYPE", request.getParameter("SYS_TYPE"));
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				rolesGroupDao.getRolesGroupList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = rolesGroupDao.getRolesGroupList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getRolesGroupCnt(HttpServletRequest request) {
		Map paramMap = this.getRolesGroupParamMap(request) ;
		return rolesGroupDao.getRolesGroupCnt(paramMap) ;
	}
	
	// 数据,总条数,统一取得Map方法
	@SuppressWarnings("unchecked")
	private Map getRolesGroupParamMap(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return paramMap ;
	}
	
	@SuppressWarnings("unchecked")
	public int addRolesGroupInfo(HttpServletRequest request){
		@SuppressWarnings("unused")
		String[] inert =request.getParameterValues("INSERT");
		@SuppressWarnings("unused")
		String[] delete =request.getParameterValues("DELETE");
		@SuppressWarnings("unused")
		String[] update =request.getParameterValues("UPDATE");
		@SuppressWarnings("unused")
		String[] view =request.getParameterValues("VIEW");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap<String, Object> appendMap = new LinkedHashMap<String, Object>() ;;
		appendMap.put("CREATED_BY", admin.getAdminID()) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.remove("jsonData") ;
		paramMap.putAll(appendMap) ;
		String jsonString = request.getParameter("jsonData") ;
		List<LinkedHashMap<String, Object>> insertRolesGroupPageList = ObjectBindUtil.getRequestJsonData(jsonString, paramMap) ;
		paramMap.put("insertRolesGroupPageList", insertRolesGroupPageList) ;
		try{
			this.rolesGroupDao.addRolesGroupInfo(paramMap) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
		return 0 ;
	}
	
	@SuppressWarnings("unchecked")
	public int updateRolesGroupInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap<String, Object> appendMap = new LinkedHashMap<String, Object>() ;;
		appendMap.put("UPDATE_BY", admin.getAdminID() ) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.remove("jsonData") ;
		paramMap.putAll(appendMap) ;
		String jsonString = request.getParameter("jsonData") ;
		List<LinkedHashMap<String, Object>> insertRolesGroupPageList = ObjectBindUtil.getRequestJsonData(jsonString, paramMap) ;
		paramMap.put("insertRolesGroupPageList", insertRolesGroupPageList) ;
		this.rolesGroupDao.updateRolesGroupInfo(paramMap) ;
		return 0 ;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteRolesGroupInfo(HttpServletRequest request) {
		String deleteIds[] = request.getParameter("deleteIds").split(",") ;
		List deleteList = new ArrayList() ;
		for(int i = 0; i < deleteIds.length ; ++i){
			Map<String, Object> deleteMap = new LinkedHashMap<String, Object>() ;
			deleteMap.put("SCREEN_GRANT_NO", deleteIds[i]) ;
			deleteList.add(deleteMap) ;
		}
		return rolesGroupDao.deleteRolesGroupInfo(deleteList) ;
	}
	
	@SuppressWarnings("unchecked")
	public List getRolesGroupPageList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		retrunList = rolesGroupDao.getRolesGroupPageList(paramMap) ;
		return retrunList ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int checkRolesGroupIdExsit(HttpServletRequest request) {
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		return this.rolesGroupDao.checkRolesGroupIdExsit(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int saveOrUpdateRolesGroupInfo(HttpServletRequest request) {
		//页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("SCREEN_GRANT_ID", paramMap.get("CPNY_ID").toString()+paramMap.get("SCREEN_GRANT_NO"));
		if(paramMap.get("NO")!=null&&!paramMap.get("NO").equals("")){
			paramMap.put("ROLE_NO",paramMap.get("NO"));
		}
		try {
			this.rolesGroupDao.saveOrUpdateRolesGroupInfo(request,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getSelectMenuForRoles(HttpServletRequest request) {
		Map paramMap =new LinkedHashMap();
		paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(paramMap.get("CPNY_ID")==null){
			paramMap.put("CPNY_ID", admin.getCpnyId()!=null? admin.getCpnyId():null);
		}else{
			paramMap.put("SCREEN_GRANT_ID", paramMap.get("CPNY_ID").toString()+paramMap.get("SCREEN_GRANT_NO"));
		}
		return this.rolesGroupDao.getSelectMenuForRoles(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteRolesGroupView(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
//		String temp=null;
		int result=0;
		try {
			 this.rolesGroupDao.deleteRolesGroupView(request,paramMap);
			  result=1;
		} catch (Exception e) {
//			temp="操作失败！";
			result=0;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap =new LinkedHashMap();
		paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				rolesGroupDao.getSyRolesGroupList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = rolesGroupDao.getSyRolesGroupList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getSyRolesGroupCnt(HttpServletRequest request) {
		Map paramMap =new LinkedHashMap();
		paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return rolesGroupDao.getSyRolesGroupCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getSyRolesIfCheckedList(HttpServletRequest request){
		Map paramMap =new LinkedHashMap();
		paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.rolesGroupDao.getSyRolesIfCheckedList(paramMap);
	}

	/**
	 * 保存SY_ROLE_GROUP
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveOrUpdateSyRolesGroupInfo(HttpServletRequest request) {
		//页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		if(paramMap.get("NO")!=null&&!paramMap.get("NO").equals("")){
			paramMap.put("ROLE_NO",paramMap.get("NO"));
		}
		String[] screenGrantNos = request.getParameterValues("ROLE_NOS") ;
		paramMap.put("ROLE_NOS",screenGrantNos);
		try {
			this.rolesGroupDao.saveOrUpdateSyRolesGroupInfo(request,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getSyRolesGroupInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(request.getParameter("NO")!=null&&!request.getParameter("NO").equals("")){
			paramMap.put("ROLE_NO", request.getParameter("NO"));
		}
		return this.rolesGroupDao.getSyRolesGroupInfo(paramMap) ; 
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public int deleteSyRolesGroupView(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			 this.rolesGroupDao.deleteSyRolesGroupView(request,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public List getMenuForRolesGroup(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return this.rolesGroupDao.getMenuForRolesGroup(paramMap);
	}
	
	/***********************************GSOD权限**************************************/
	/**
	 * 查找gsod权限单条记录的详细信息
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
    public Object getGsodRoleGroup(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(request.getParameter("NO")!=null&&!request.getParameter("NO").equals("")){
			paramMap.put("ROLE_GSOD_NO", request.getParameter("NO"));
		}
		return this.rolesGroupDao.getGsodRoleGroup(paramMap) ; 
	}
	
	/**
	 * 查找gsod权限的List
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getGsodRoleGroupList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
//		if(request.getParameter("GROUPNO")!=null && !"".equals(request.getParameter("GROUPNO"))){
//			paramMap.put("GROUPNO", request.getParameter("GROUPNO"));
//		} 
		if (UiUtil.getPageNum(request) > 0){
			retrunList = rolesGroupDao.getGsodRoleGroupList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = rolesGroupDao.getGsodRoleGroupList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 查找gsod权限的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getGsodRoleGroupCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		retrunInt = rolesGroupDao.getGsodRoleGroupCnt(paramMap) ;
		return retrunInt ;
	}
	
	/**
	 * 添加gsod的新纪录
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addGsodRoleGroupInfo(HttpServletRequest request) throws SQLException{
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int num = 0;
		paramMap.put("ROLE_GSOD_NAME", request.getParameter("role_GSOD_NAME"));
		paramMap.put("ACTIVITY", request.getParameter("ACTIVITY"));
		if(request.getParameter("flag")=="1" || "1".equals(request.getParameter("flag"))){
		    paramMap.put("CREATED_BY", admin.getPersonId());
		}else{
			paramMap.put("UPDATED_BY", admin.getPersonId());
		}
		String[] roleGroupNo = request.getParameterValues("check_gsod");
		int groupNo = this.rolesGroupDao.findGroupNoFromGsod();
		if(roleGroupNo.length>0){
			for(int i=0;i<roleGroupNo.length;i++){
				String[] rolesNo = roleGroupNo[i].split(",");
				paramMap.put("SY_ROLE_GROUP_NO", rolesNo[0]);
				paramMap.put("CPNY_ID", rolesNo[1]);
				paramMap.put("GROUPNo", groupNo+1);
				num = rolesGroupDao.addGsodRoleGroupInfo(paramMap);
			}
		}
		return num;
	}
	
	/**
	 * 修改gsod权限
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteGsodRoleGroupInfoAll(HttpServletRequest request){
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("GROUPNO", request.getParameter("GROUPNO"));
		return rolesGroupDao.deleteGsodRoleGroupInfoAll(map);
	}
	
	/**
	 * 删除gsod权限记录
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteGsodRoleGroupInfo(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		int num = 0;
		paramMap.put("ROLE_GSOD_NO",request.getParameter("ROLE_GSOD_NO1"));
		num = rolesGroupDao.deleteGsodRoleGroupInfo(paramMap);
		return num;
	}
	
	/**
	 * 添加和修改的时候看名称是否已经存在
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkGsodRoleGroupExsit(HttpServletRequest request){
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		String[] roleGroupNo = request.getParameterValues("check_gsod");
		int num = 0;
		if(roleGroupNo.length>0){
			for(int i=0;i<roleGroupNo.length;i++){
				String[] rolesNo = roleGroupNo[i].split(",");
				paramMap.put("SY_ROLE_GROUP_NO", rolesNo[0]);
				int num2 = this.rolesGroupDao.checkGsodRoleGroupExsit(paramMap);
				if(num2==1){
				   num=1;
				   break;
				}else if(num2==2){
					num=2;
				   break;
				}
			}
		}
		return num;
	}
	
	/**
	 * 查找getSyRolesGroupListGsod权限的List（不分页） 添加使用
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupListGsodAdd(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("SY_ROLE_GROUP_NAME", request.getParameter("SY_ROLE_GROUP_NAME"));
		retrunList = rolesGroupDao.getSyRolesGroupListGsodAdd(paramMap) ;
		return retrunList ;
	}
	
	/**
	 * 查找getSyRolesGroupListGsod权限的List（不分页） 修改使用
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupListGsod(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("SY_ROLE_GROUP_NAME", request.getParameter("SY_ROLE_GROUP_NAME"));
		retrunList = rolesGroupDao.getSyRolesGroupListGsod(paramMap) ;
		return retrunList ;
	}
}
