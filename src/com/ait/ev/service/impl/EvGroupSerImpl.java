package com.ait.ev.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ev.dao.EvGroupDao;
import com.ait.ev.service.EvGroupService;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Service
public class EvGroupSerImpl implements EvGroupService{

	@Autowired
	private EvGroupDao evdao;

	@Override
	public int delEvGroupInfo(HttpServletRequest request) throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		map.put("ID", request.getParameter("ID"));
		if(evdao.getGroupHasChild(map)){
			return 0;//有子对象，不能删除
		}
		return evdao.delEvGroupInfo(map);
	}

	@Override
	public List getEvGroupInfo(HttpServletRequest request) throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		return evdao.getEvGroupInfo(map);
	}

	@Override
	public int insertEvGroupInfo(HttpServletRequest request) {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("TITLE", request.getParameter("TITLE"));
		map.put("CONTENT", request.getParameter("CONTENT"));
		map.put("CREATE_BY",admin.getAdminID());
		map.put("PARENT_ID", request.getParameter("PARENT_ID")!=""?
					Integer.parseInt(request.getParameter("PARENT_ID")):0);
		map.put("DEPTH", request.getParameter("DEPTH")!=""?
					Integer.parseInt(request.getParameter("DEPTH")):1);
		return evdao.insertEvGroupInfo(map);
	}

	@Override
	public int updateEvGroupInfo(HttpServletRequest request) {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("ID", request.getParameter("ID"));
		map.put("TITLE", request.getParameter("TITLE"));
		map.put("CONTENT", request.getParameter("CONTENT"));
		map.put("UPDATED_BY",admin.getAdminID());
		return evdao.updateEvGroupInfo(map);
	}

	@Override
	public LinkedHashMap getEvGroupInfoById(HttpServletRequest request)
			throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		map.put("ID", request.getParameter("ID"));
		return evdao.getEvGroupInfoById(map);
	}

	@Override
	public List getEvGroupTree(HttpServletRequest request) throws Exception {
		Map parameterObject = ObjectBindUtil.getRequestParamData(request);
		return evdao.getEvGroupTree(parameterObject);
	}
	
	
}
