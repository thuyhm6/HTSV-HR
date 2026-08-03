package com.ait.sys.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.ToolMenuDao;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ToolMenuSerImpl.java
 * @Description:
 * @Create date: 2012-3-21 上午11:20:15
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ToolMenuSerImpl implements ToolMenuSer {
	
	Logger logger = Logger.getLogger(ToolMenuSerImpl.class);
	
	@Autowired
	private ToolMenuDao toolMenuDao;

	/**
	 * 取得按钮权限(get Tool Menu)
	 * @param request
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object getToolMenu(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("USER_NO", admin.getUserNo());
		
		return this.toolMenuDao.getToolMenu(paramMap) ; 
	}
	
	/**
	 * 取得按钮权限(get Tool Menu)
	 * @param request
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object getToolMenuForNo(HttpServletRequest request, String menuNo) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("menuNo", menuNo);
		paramMap.put("USER_NO", admin.getUserNo());
		
		return this.toolMenuDao.getToolMenu(paramMap) ; 
	}

	@Override
	public Object getLinkMapByName(Map map, String sqlName) {
		return toolMenuDao.getLinkMapByName(map,sqlName);
	}
}
