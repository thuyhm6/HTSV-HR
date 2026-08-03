package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName LoginUserSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:23:43
 * @version 5.0
 *
 */
public interface LoginUserSer {

	public Object getLoginUser(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getLoginUserRolesGroupList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getloginUserInfoRolesGSODList(HttpServletRequest requst);
	
	@SuppressWarnings("unchecked")
	public List getEmpTypeCodeList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getSySupervisorEmpTypeCodeList(HttpServletRequest request);

	
	@SuppressWarnings("unchecked")
	public List getLoginUserList(HttpServletRequest request) ;
	
	public int getLoginUserCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getLoginUserExcelList(HttpServletRequest request) ;
	
	public int addLoginUserInfo(HttpServletRequest request) ;
	
	public int updateLoginUserInfo(HttpServletRequest request) ;
	
	public int deleteLoginUserInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getLoginUserDeptList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getLoginUserRoleGroupRelation(HttpServletRequest request,Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getDeptInfoTree(HttpServletRequest request) ;
	
	public int validatePersonIdExist(HttpServletRequest request) ;
	
	public int deleteLoginUser(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
	public List getLoginUserIPList(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
	public List viewFileInfoList(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public List viewFileUrlInfo(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public Object viewFileInfo(HttpServletRequest request) ;
	
	
	
public int addFileInfo(HttpServletRequest request) ;
	
	public int updateFileInfo(HttpServletRequest request) ;
	
	public int deleteFileInfo(HttpServletRequest request) ;
	
	
	
}
