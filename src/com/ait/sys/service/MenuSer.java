package com.ait.sys.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName MenuSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:23:48
 * @version 5.0
 *
 */
public interface MenuSer {
 
	@SuppressWarnings("unchecked")
	public List getMenuList(HttpServletRequest request)throws Exception;
	
	public int getMenuListCnt(HttpServletRequest request)throws Exception;
	
	public int disableMenu(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getMenuTree(HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("unchecked")
	public Map getMenuByNo(HttpServletRequest request)throws Exception;
	
	public int saveMenu(HttpServletRequest request) throws Exception;
	
	public int updateMenu(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getMenuParamList(HttpServletRequest request)throws Exception;
	
	public int getMenuParamListCnt(HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getMenuParamByNo(HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getMenuTreeForAll(HttpServletRequest request)throws Exception;
	
	public int saveMenuParam(HttpServletRequest request) throws Exception;
	
	public int updateMenuParam(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public Map getMenuParamByPK(HttpServletRequest request)throws Exception;
	/**
	 * 为权限组查询菜单子集
	 */
	@SuppressWarnings("unchecked")
	public List getMenuForRolesGroup(HttpServletRequest request) throws Exception;
	/**
	 * 为权限组查询菜单子集数量
	 */
	public int getMenuForRolesGroupCnt(HttpServletRequest request) throws Exception;
	/**
	 * 根据父级菜单NO获得子菜单
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getMenuTreeByParentMenu(HttpServletRequest request) throws Exception;
	/**
	 * 根据父级菜单NO法人获得菜单法人关系子菜单
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getMenuTreeByParentMenuSelect(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List validateMenuIdExist(HttpServletRequest request) throws Exception;
}
