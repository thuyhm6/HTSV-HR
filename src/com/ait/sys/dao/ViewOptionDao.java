package com.ait.sys.dao;

import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ViewOptionDao.java
 * @Description:
 * @Create date: 2012-5-8 上午11:45:11
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ViewOptionDao {
	
	@SuppressWarnings("unchecked")
	public List retrieveReportTableList(Object object);

	@SuppressWarnings("unchecked")
	public List retrieveReportItemList(Object object);
	
	@SuppressWarnings("unchecked")
	public List retrieveReportDataList(List list,Map parameterObject) throws Exception;
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
	public List personalAttendanceBack(Object object) ;
	@SuppressWarnings("unchecked")
	public List personalAttendanceBack(Object obj, int currentPage, int pageSize);
	public int getPersonalAttendanceBackCnt(Object object) ;
	/******************************20150206 zyh end**************************************/

}
