package com.ait.sys.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName MenuDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:22:55
 * @version 5.0
 *
 */
public interface MenuDao {
	 
	@SuppressWarnings("unchecked")
	public List getMenuList(Object object, int currentPage, int pageSize)throws Exception;

	@SuppressWarnings("unchecked")
	public List getMenuList(Object object);
	
	public int getMenuListCnt(Object object)throws Exception;
	
	public void updateMenuActivity(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public Map getMenuByID(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getMenuTree(Object object)throws Exception;
	
	public String saveMenu(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public Map getMenuByNo(Object object)throws Exception;
	
	public void updateMenu(Object object)throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public List getMenuParamList(Object object, int currentPage, int pageSize)throws Exception;
 
	public int getMenuParamListCnt(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getMenuParamByNo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getMenuTreeForAll(Object object)throws Exception;
	
	public String saveMenuParam(Object object,HttpServletRequest request)throws Exception;
	
	public String updateMenuParam(Object object,HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("unchecked")
	public  Map getMenuParamByPK(Object object)throws Exception;
	
	/**
	 * 权限组查询配置菜单子集
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getMenuForRolesGroup(Object object)throws Exception;
	
	/**
	 * 权限组查询配置菜单子集数量
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getMenuForRolesGroupCnt(Object object)throws Exception;
	/**
	 * 根据父级菜单NO查找子级菜单
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getMenuTreeByParentMenu(Object object)throws Exception;
	/**
	 * 根据父级菜单NO法人查找已关联子级菜单
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getMenuTreeByParentMenuSelect(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List validateMenuIdExist(Object object)throws Exception;
	
}
