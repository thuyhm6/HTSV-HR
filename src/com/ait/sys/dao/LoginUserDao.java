package com.ait.sys.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName LoginUserDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:22:33
 * @version 5.0
 *
 */
public interface LoginUserDao {
	
	public Object getLoginUser(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getLoginUserRolesGroupList(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List  getloginUserInfoRolesGSODList(Object obj);
	
	
	@SuppressWarnings("unchecked")
	public List getEmpTypeCodeList(Map paramMap);
	
	 
	@SuppressWarnings("unchecked")
	public List getSySupervisorEmpTypeCodeList(Map paramMap);



	
	@SuppressWarnings("unchecked")
	public List getLoginUserList(Object object);
	
	public int getLoginUserCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getLoginUserList(Object object, int currentPage, int pageSize);
	
	public int addLoginUserInfo(Object object) throws Exception;
	
	public int updateLoginUserInfo(Object object) throws Exception;
	
	public int deleteLoginUserInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getLoginUserMaxDeptList(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getLoginUserDeptList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getDeptInfoTree(Object object) ;
	
	public int validatePersonIdExist(Object object) throws Exception;
	
	public void deleteLoginUser(Object object)throws Exception;
	public void changePassword(Object object) throws Exception ;
	
	
	@SuppressWarnings("unchecked")
	public List getLoginUserIPList(Object object);
	
	@SuppressWarnings("unchecked")
	public List viewFileInfoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List viewFileUrlInfo(Object object);
	@SuppressWarnings("unchecked")
	public Object viewFileInfo(Object object);
	
public int addFileInfo(Object object) throws Exception;
	
	public int updateFileInfo(Object object) throws Exception;
	
	public int deleteFileInfo(Object object);
}
