package com.ait.sys.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.MenuDao;
import com.ait.sys.service.MenuSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
/**
 * 
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName MenuSerImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午04:37:20
 * @version 5.0
 *
 */
@Service
public class MenuSerImpl implements MenuSer {

	@Autowired
	private MenuDao menuDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List getMenuList(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return menuDao.getMenuList(paramMap , 
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
		);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getMenuListCnt(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNYID", admin.getCpnyId()!=null? admin.getCpnyId():null);
		return menuDao.getMenuListCnt(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int disableMenu(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.menuDao.updateMenuActivity(paramMap);;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 当前登录所在法人下的所有菜单
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getMenuTree(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(request.getParameter("SYS_TYPE")!=null){
			if(request.getParameter("SYS_TYPE").equals("1"))
				paramMap.put("withEss", "OK");
			else
				paramMap.put("notWithEss", "OK");
		}
		return menuDao.getMenuTree(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int saveMenu(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin!=null?admin.getAdminID():null);
		try {
			this.menuDao.saveMenu(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getMenuByNo(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.menuDao.getMenuByNo(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateMenu(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.menuDao.updateMenu(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuParamList(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return menuDao.getMenuParamList(paramMap , 
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
		);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getMenuParamListCnt(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return menuDao.getMenuParamListCnt(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuParamByNo(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.menuDao.getMenuParamByNo(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuTreeForAll(HttpServletRequest request) throws Exception {
		Map paramMap =new LinkedHashMap();
		paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(paramMap.get("CPNY_ID")==null)
		paramMap.put("CPNY_ID", admin.getCpnyId()!=null? admin.getCpnyId():null);
		return menuDao.getMenuTreeForAll(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public int saveMenuParam(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.menuDao.saveMenuParam(paramMap,request);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateMenuParam(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.menuDao.updateMenuParam(paramMap,request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getMenuParamByPK(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.menuDao.getMenuParamByPK(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuForRolesGroup(HttpServletRequest request)
			throws Exception {
		Map paramMap =new LinkedHashMap();
		paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(paramMap.get("CPNY_ID")==null){
			paramMap.put("CPNY_ID", admin.getCpnyId()!=null? admin.getCpnyId():null);
		}else{
			paramMap.put("SCREEN_GRANT_ID",paramMap.get("CPNY_ID").toString()+paramMap.get("SCREEN_GRANT_NO"));
		}
		return menuDao.getMenuForRolesGroup(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getMenuForRolesGroupCnt(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId()!=null? admin.getCpnyId():null);
		return menuDao.getMenuForRolesGroupCnt(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuTreeByParentMenu(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		return menuDao.getMenuTreeByParentMenu(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuTreeByParentMenuSelect(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return menuDao.getMenuTreeByParentMenuSelect(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List validateMenuIdExist(HttpServletRequest request) throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.menuDao.validateMenuIdExist(paramMap);
	}
}
