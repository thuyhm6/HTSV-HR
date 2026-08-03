package com.ait.ess.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.NumberUtils;

import com.ait.ess.service.AttendanceInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.ViewOptionDao;
import com.ait.sys.service.CompanySer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.ViewOptionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AttendanceInfoSerImp.java
 * @Description:
 * @Create date: 2012-5-23 下午06:18:31
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class AttendanceInfoSerImp implements AttendanceInfoSer {

	Logger logger = Logger.getLogger(AttendanceInfoSerImp.class);
	
	@Autowired
	private ViewOptionDao viewOptionDao;
	@Autowired
	private ViewOptionUtil viewOptionUtil;
	
	/**
	 * 个人考勤(make DataTable)
	 * @param request
	 * @return String
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String makeDataTable(HttpServletRequest request, String menuNo) {
		
		String dataTable = "";
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("makeType", "ar");
		paramMap.put("ess", "1");
		
		paramMap.put("MENU_NO", menuNo);
		
		if(paramMap.get("essYear") != null && !"".equals(paramMap.get("essYear").toString())
				&& paramMap.get("essMonth") != null && !"".equals(paramMap.get("essMonth").toString())){
			
			paramMap.put("essMonth", paramMap.get("essYear").toString()+paramMap.get("essMonth").toString());
		}
		
		dataTable = viewOptionUtil.makeDataTable(paramMap);
		
		return dataTable;
	}
	
	/******************************20150206 zyh start**************************************/
	/**
	 * 个人考勤追溯查看页面(view Personal Attendance)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalAttendanceBack(HttpServletRequest request, ModelMap modelMap) {
		List retrunList = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = this.viewOptionDao.personalAttendanceBack(modelMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = this.viewOptionDao.personalAttendanceBack(modelMap);
		}
		return retrunList;
	}
	public int getPersonalAttendanceBackCnt(HttpServletRequest request, ModelMap modelMap) {
		return viewOptionDao.getPersonalAttendanceBackCnt(modelMap);
	}
	/******************************20150206 zyh end**************************************/

}
