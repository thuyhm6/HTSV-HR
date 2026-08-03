package com.ait.sys.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName RolesGroupSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:24:05
 * @version 5.0
 *
 */
public interface RolesGroupSer {

	public Object getRolesGroup(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getRolesGroupList(HttpServletRequest request) ;
	
	public int getRolesGroupCnt(HttpServletRequest request) ;
	
	public int addRolesGroupInfo(HttpServletRequest request) ;
	
	public int updateRolesGroupInfo(HttpServletRequest request) ;
	
	public int deleteRolesGroupInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getRolesGroupPageList(HttpServletRequest request) ;
	
	public int checkRolesGroupIdExsit(HttpServletRequest request);
    
	public int saveOrUpdateRolesGroupInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getSelectMenuForRoles(HttpServletRequest request) ;
	
	public int deleteRolesGroupView(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupList(HttpServletRequest request) ;
	
	public int getSyRolesGroupCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getSyRolesIfCheckedList(HttpServletRequest request) ;
	
	public int saveOrUpdateSyRolesGroupInfo(HttpServletRequest request);
	
	public Object getSyRolesGroupInfo(HttpServletRequest request);
	
	public int deleteSyRolesGroupView(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getMenuForRolesGroup(HttpServletRequest request);
	
	/***********************************GSOD权限**************************************/
	/**
	 * 查找gsod权限单条记录的详细信息
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public Object getGsodRoleGroup(HttpServletRequest request) ;
	
	/**
	 * 查找gsod权限的List
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getGsodRoleGroupList(HttpServletRequest request);
	
	/**
	 * 查找gsod权限的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getGsodRoleGroupCnt(HttpServletRequest request);
	
	/**
	 * 添加gsod的新纪录
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addGsodRoleGroupInfo(HttpServletRequest request) throws SQLException;
	
	/**
	 * 修改gsod权限
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteGsodRoleGroupInfoAll(HttpServletRequest request);
	
	/**
	 * 删除gsod权限记录
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteGsodRoleGroupInfo(HttpServletRequest request);
	
	/**
	 * 添加和修改的时候看名称是否已经存在
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int checkGsodRoleGroupExsit(HttpServletRequest request);
	
	/**
	 * 查找getSyRolesGroupListGsod权限的List（不分页）添加使用
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupListGsodAdd(HttpServletRequest request);
	
	/**
	 * 查找getSyRolesGroupListGsod权限的List（不分页） 修改使用
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupListGsod(HttpServletRequest request);
}
