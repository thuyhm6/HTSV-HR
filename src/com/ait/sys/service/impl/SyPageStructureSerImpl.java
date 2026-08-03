package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.SyPageStructureDao;
import com.ait.sys.service.SyPageStructureSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Service
public class SyPageStructureSerImpl implements SyPageStructureSer {
	Logger logger = Logger.getLogger(SyPageStructureSerImpl.class);

	@Autowired
	private SyPageStructureDao syPageStructureDao;

	@SuppressWarnings("unchecked")
	public List getIsCanBeBuildPage(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		retrunList = syPageStructureDao.getIsCanBeBuildPage(paramMap);
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	public List getPageStructureInfoList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return syPageStructureDao.getPageStructureInfoList(paramMap);
	}

	@SuppressWarnings("unchecked")
	public String addPageStructure(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATE_BY", admin.getAdminID());
		int result = syPageStructureDao.addPageStructure(paramMap);
		if (result == 1) {
			return "Y";

		} else {
			return "添加失败！";
		}

	}

	@SuppressWarnings("unchecked")
	public String deletePageStructure(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		List list = new ArrayList();
		String[] RT_NO = paramMap.get("RT_NO").toString().split(",");
		for (int i = 0; i < RT_NO.length; i++) {
			LinkedHashMap map = new LinkedHashMap();
			map.put("RT_NO", RT_NO[i]);
			list.add(map);
		}
		int result = syPageStructureDao.deletePageStructure(list);
		if (result == 1) {
			return "Y";
		} else {
			return "删除失败！";
		}
	}

	@SuppressWarnings("unchecked")
	public List getAddPageStructureDetail(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String table_name = request.getParameter("REPORT_TYPE");
		if (table_name.equals("1")) {
			paramMap.put("TABLE_NAME", "AR_HISTORY");
		} else {
			paramMap.put("TABLE_NAME", "PA_HISTORY");
		}
		if (paramMap.get("page") != null && paramMap.get("pagesize") != null) {
			retrunList = syPageStructureDao.getAddPageStructureDetail(paramMap,
					NumberUtils.parseNumber(ObjectUtils.toString(paramMap
							.get("page")), Integer.class), NumberUtils
							.parseNumber(ObjectUtils.toString(paramMap
									.get("pagesize")), Integer.class));
		} else {
			retrunList = syPageStructureDao.getAddPageStructureDetail(paramMap);
		}

		return retrunList;
	}

	@SuppressWarnings("unchecked")
	public int getAddPageStructureDetailCnt(HttpServletRequest request) {
		int i = 0;

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		String table_name = request.getParameter("REPORT_TYPE");
		if (table_name.equals("1")) {
			paramMap.put("TABLE_NAME", "AR_HISTORY");
		} else {
			paramMap.put("TABLE_NAME", "PA_HISTORY");
		}
		i = syPageStructureDao.getAddPageStructureDetailCnt(paramMap);

		return i;
	}

	@SuppressWarnings("unchecked")
	public String AddPageStructureDetailInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String jsonString = request.getParameter("jsonData");
		String RT_NO = request.getParameter("RT_NO");
		String table_name = request.getParameter("REPORT_TYPE");
		if (table_name.equals("1")) {
			table_name = "AR_HISTORY";
		} else {
			table_name = "PA_HISTORY";
		}
		List<LinkedHashMap<String, Object>> detailList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		int result = 0;
		for (int i = 0; i < detailList.size(); i++) {
			LinkedHashMap parammap = (LinkedHashMap) detailList.get(i);
			parammap.put("RT_NO", RT_NO);
			parammap.put("TABLE_NAME", table_name);
			parammap.put("CREATE_BY", admin.getAdminID());
			result = syPageStructureDao.AddPageStructureDetailInfo(parammap);
		}

		if (result == 1) {
			return "Y";

		} else {
			return "添加失败！";
		}

	}

	@SuppressWarnings("unchecked")
	public List getUpdatePageStructureDetail(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (paramMap.get("page") != null && paramMap.get("pagesize") != null) {
			retrunList = syPageStructureDao.getUpdatePageStructureDetail(
					paramMap, NumberUtils.parseNumber(ObjectUtils
							.toString(paramMap.get("page")), Integer.class),
					NumberUtils.parseNumber(ObjectUtils.toString(paramMap
							.get("pagesize")), Integer.class));
		} else {
			retrunList = syPageStructureDao
					.getUpdatePageStructureDetail(paramMap);
		}

		return retrunList;
	}

	@SuppressWarnings("unchecked")
	public int getUpdatePageStructureDetailCnt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		int i = 0;

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		i = syPageStructureDao.getUpdatePageStructureDetailCnt(paramMap);

		return i;
	}

	@SuppressWarnings("unchecked")
	public String deletePageStructureDetail(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String jsonString = request.getParameter("jsonData");

		List<LinkedHashMap<String, Object>> detailList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		int result = 0;
		for (int i = 0; i < detailList.size(); i++) {
			LinkedHashMap parammap = (LinkedHashMap) detailList.get(i);
			result = syPageStructureDao.deletePageStructureDetail(parammap);
		}

		if (result == 1) {
			return "Y";

		} else {
			return "删除失败！";
		}
	}

	@SuppressWarnings("unchecked")
	public String updatePageStructureDetailInfo(HttpServletRequest request) {
		String jsonString = request.getParameter("jsonData");
		int result = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List<LinkedHashMap<String, Object>> detailList = ObjectBindUtil
				.getRequestJsonData(jsonString);

		for (int i = 0; i < detailList.size(); i++) {
			LinkedHashMap parammap = (LinkedHashMap) detailList.get(i);
			parammap.put("UPDATE_BY", admin.getAdminID());
			result = syPageStructureDao
					.updatePageStructureDetailInfo(parammap);
		}
		if (result == 1) {
			return "Y";
		} else {
			return "删除失败！";
		}
	}

}
