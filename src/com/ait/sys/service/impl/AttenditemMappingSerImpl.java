package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.AttendItemMappingDao;
import com.ait.sys.service.AttendItemMappingSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class AttenditemMappingSerImpl implements AttendItemMappingSer {
	Logger logger = Logger.getLogger(AttenditemMappingSerImpl.class);

	@Autowired
	private AttendItemMappingDao attendItemMappingDao;

	@Override
	public List getAttendItemMappingList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String project_type = request.getParameter("PROJECT_TYPE");
		if (project_type != null && !"".equals(project_type)) {
			paramMap.put("PROJECT_TYPE", project_type);
		}
		String CPNY = request.getParameter("seach_CPNY");
		paramMap.put(CPNY, CPNY);
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = attendItemMappingDao.getAttendItemMappingList(
					paramMap, UiUtil.getPageNum(request),
					UiUtil.getNumPerPage(request));
		} else {
			retrunList = attendItemMappingDao
					.getAttendItemMappingList(paramMap);
		}

		return retrunList;
	}

	@Override
	public int getAttendItemMappingCnt(HttpServletRequest request) {
		int retrunInt = 0;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String CPNY = request.getParameter("seach_CPNY");
		paramMap.put(CPNY, CPNY);
		retrunInt = attendItemMappingDao.getAttendItemMappingCnt(paramMap);
		return retrunInt;
	}
	
	/**
	 * 查找所有考勤项目（明细项目和汇总项目）的和法人匹配的信息列表   考勤代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttendItemCheckMapList(HttpServletRequest request){
		List retrunList = new ArrayList();
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String project_type = request.getParameter("PROJECT_TYPE");
		if (project_type != null && !"".equals(project_type)) {
			paramMap.put("PROJECT_TYPE", project_type);
		}
		String CPNY = request.getParameter("defaultCpny");
		if(CPNY == null || "".equals(CPNY)){
			paramMap.put("CPNY", admin.getCpnyId());
			paramMap.put("CPNY_ID",admin.getCpnyId());
		}else{
			paramMap.put("CPNY",CPNY);
			paramMap.put("CPNY_ID",CPNY);
		}
		
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = attendItemMappingDao.getAttendItemCheckMapList(
					paramMap, UiUtil.getPageNum(request),
					UiUtil.getNumPerPage(request));
		} else {
			retrunList = attendItemMappingDao
					.getAttendItemCheckMapList(paramMap);
		}

		return retrunList;
	}
	
	/**
	 * 查找所有考勤项目(明细项目和汇总项目)的和法人匹配的信息的数量 考勤代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAttendItemCheckMapCnt(HttpServletRequest request){
		int retrunInt = 0;
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String CPNY = request.getParameter("defaultCpny");
		if(CPNY == null || "".equals(CPNY)){
			paramMap.put("CPNY", admin.getCpnyId());
			paramMap.put("CPNY_ID",admin.getCpnyId());
		}else{
			paramMap.put("CPNY",CPNY);
			paramMap.put("CPNY_ID",CPNY);
		}
		retrunInt = attendItemMappingDao.getAttendItemCheckMapCnt(paramMap);
		return retrunInt;
	}

	@Override
	public Object getAttendItemMappingInfo(HttpServletRequest request) {
		Object returnObj = new Object();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.remove("sortname");
		returnObj = attendItemMappingDao.getAttendItemMappingInfo(paramMap);
		return returnObj;
	}

	@Override
	public int addAffirmAttendItemInfo(HttpServletRequest request)
			throws Exception {
		int num = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String PROJECT_TYPE = request.getParameter("PROJECT_TYPE");
		String ITEM_ID = request.getParameter("ITEM_ID");
		String ACTIVITY_TYPE = request.getParameter("ACTIVITY_TYPE");
		String affirm_name = admin.getLocalName();
		// 页面提交数据
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		if (request.getParameter("ACTIVITY") == null) {
			paramMap.put("ACTIVITY", 1);
		}
		paramMap.put("ITEM_ID", ITEM_ID);
		paramMap.put("AFFIRM_NAME", affirm_name);
		paramMap.put("PROJECT_TYPE", PROJECT_TYPE);
		paramMap.put("ACTIVITY_TYPE", ACTIVITY_TYPE);
		num=this.attendItemMappingDao.addAffirmAttendItemInfo(paramMap);
		return num;
	}

	@Override
	public int addAffirmAttendInfo(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String PROJECT_TYPE = request.getParameter("PROJECT_TYPE");
		String ITEM_ID = request.getParameter("ITEM_ID");
		String ITEM_NO = request.getParameter("ITEM_NO");
		String ACTIVITY_TYPE = request.getParameter("ACTIVITY_TYPE");
		String affirm_name = admin.getLocalName();
		// 页面提交数据
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		if (request.getParameter("ACTIVITY") == null) {
			paramMap.put("ACTIVITY", 1);
		}
		paramMap.put("ITEM_ID", ITEM_ID);
		paramMap.put("ITEM_NO", ITEM_NO);
		paramMap.put("AFFIRM_NAME", affirm_name);
		paramMap.put("PROJECT_TYPE", PROJECT_TYPE);
		paramMap.put("ACTIVITY_TYPE", ACTIVITY_TYPE);
		return this.attendItemMappingDao.addAffirmAttendInfo(paramMap);
	}

}
