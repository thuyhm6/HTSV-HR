package com.ait.ess.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AttendanceInfoSer.java
 * @Description:
 * @Create date: 2012-5-23 下午06:16:43
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface AttendanceInfoSer {
	
	@SuppressWarnings("unchecked")
	public String makeDataTable(HttpServletRequest request, String str) ;
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
	public List getPersonalAttendanceBack(HttpServletRequest request, ModelMap modelMap) ;
	public int getPersonalAttendanceBackCnt(HttpServletRequest request, ModelMap modelMap) ;
	/******************************20150206 zyh end**************************************/

}
