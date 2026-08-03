package com.ait.ar.service.impl;

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

import com.ait.ar.dao.ArBasicDao;
import com.ait.ar.dao.DynamicGroupDao;
import com.ait.ar.dao.ItemsDao;
import com.ait.ar.service.ArBasicSer;
import com.ait.ar.service.PersonalAttendanceSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.ViewOptionDao;
import com.ait.sys.service.CompanySer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.ViewOptionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PersonalAttendanceSerImp.java
 * @Description:
 * @Create date: 2012-5-9 上午11:05:15
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class PersonalAttendanceSerImp implements PersonalAttendanceSer {

	Logger logger = Logger.getLogger(PersonalAttendanceSerImp.class);
	
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
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		
		paramMap.put("MENU_NO", menuNo);
		
		if(paramMap.get("arYear") != null && !"".equals(paramMap.get("arYear").toString())
				&& paramMap.get("arMonth") != null && !"".equals(paramMap.get("arMonth").toString())){
			
			paramMap.put("arMonth", paramMap.get("arYear").toString()+paramMap.get("arMonth").toString());
		}
		
		dataTable = viewOptionUtil.makeDataTable(paramMap);
		
		return dataTable;
	}
}
