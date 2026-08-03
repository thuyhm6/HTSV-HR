package com.ait.sys.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName RolesGroupDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:23:08
 * @version 5.0
 *
 */
public interface RolesGroupDao {
	
	public Object getRolesGroup(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getRolesGroupList(Object object);
	
	public int getRolesGroupCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getRolesGroupList(Object object, int currentPage, int pageSize);
	
	public int addRolesGroupInfo(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public int addRolesGroupInfo(List object);
	
	public int updateRolesGroupInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public int updateRolesGroupInfo(List object);
	
	public int deleteRolesGroupInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public int deleteRolesGroupInfo(List object);
	
	@SuppressWarnings("unchecked")
	public List getRolesGroupPageList(Object object);
	
	public int checkRolesGroupIdExsit(Object object);
	
	public void saveOrUpdateRolesGroupInfo(HttpServletRequest request,Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getSelectMenuForRoles(Object object);
	
	public String deleteRolesGroupView(HttpServletRequest request,Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupList(Object object);
	
	public int getSyRolesGroupCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getSyRolesIfCheckedList(Object object);
	
	public void saveOrUpdateSyRolesGroupInfo(HttpServletRequest request,Object object)throws Exception;
	
	public Object getSyRolesGroupInfo(Object obj) ;
	
	public void deleteSyRolesGroupView(HttpServletRequest request,Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getMenuForRolesGroup(Object obj);
	
	/***********************************GSOD权限**************************************/
	/**
	 * 查找gsod权限单条记录的详细信息
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public Object getGsodRoleGroup(Object object) ;
	
	/**
	 * 查找gsod权限的List（不分页）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getGsodRoleGroupList(Object object);
	
	/**
	 * 查找gsod权限的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getGsodRoleGroupCnt(Object object);
	
	/**
	 * 分页查找gsod权限的List
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getGsodRoleGroupList(Object object, int currentPage, int pageSize);
	
	/**
	 * 添加gsod的新纪录
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addGsodRoleGroupInfo(Object object) throws SQLException;
	
	/**
	 * 修改gsod权限
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteGsodRoleGroupInfoAll(Object object);
	
	public int findGroupNoFromGsod();
	
	/**
	 * 删除gsod权限记录
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteGsodRoleGroupInfo(Object object);
	
	/**
	 * 添加和修改的时候看名称是否已经存在
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int checkGsodRoleGroupExsit(Object object);
	
	/**
	 * 查找getSyRolesGroupListGsod权限的List（不分页） 添加使用
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupListGsodAdd(Object object);
	
	/**
	 * 查找getSyRolesGroupListGsod权限的List（不分页） 修改使用
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupListGsod(Object object);
	
	/**
	 * 取得用户所有权限的id
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getRoleIdByPersonId(Object obj);
	
	/**
	 * 查询是否存在于考勤员表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArSupervisor(Object obj) ;
	
	/**
	 * 获取员工的总数 首页显示
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getTotalEmpCountForMain(Object obj) ;
	
	/**
	 * 获取在职的员工 首页显示
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getInCpnyTotalEmpCountForMain(Object obj) ;
	
	/**
	 * 获取离职的员工 首页显示
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getLeftManTotalEmpCountForMain(Object obj) ;
	
	/**
	 * 获取新入职的员工 首页显示
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getNewManTotalEmpCountForMain(Object obj) ;
}
