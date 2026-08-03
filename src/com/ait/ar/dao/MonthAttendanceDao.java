package com.ait.ar.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ait.sys.bean.ReportItem;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ViewOptionDao.java
 * @Description:
 * @Create date: 2012-5-8 上午11:45:11
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface MonthAttendanceDao {
	
	@SuppressWarnings("unchecked")
	public List retrieveReportTableList(Object object);

	@SuppressWarnings("unchecked")
	public List retrieveReportItemList(Object object);
	
	@SuppressWarnings("unchecked")
	public List retrieveReportDataList(List list,Map parameterObject) throws Exception;
	
	/**
	 * 考勤查看-月上班日程
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkSchedule(Object object, int currentPage, int pageSize);
	
	/**
	 * 考勤查看-月上班日程
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkSchedule(Object object);
	
	/**
	 * 考勤查看-月上班日程
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int getMonthWorkScheduleCnt(Object object);
	
	/**
	 * 考勤查看-月考勤查看
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int getMonthWorkListCnt(Object object);
	
	/**
	 * 考勤查看-月考勤查看
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int getMonthWorkLGETAListCnt(Object object);
	
	
	
	/**
	 * 未刷卡查询
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int noSwipingCardListCnt(Object object);
	/**
	 * 考勤查看-月考勤查看
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkList(Object object, int currentPage, int pageSize);
	
	/**
	 * 考勤查看-月考勤查看
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkList(Object object);
	
	/**
	 * 考勤查看-月考勤查看(TA)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkLGETAList(Object object);
	
	
	/**
	 * 为刷卡查询
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List noSwipingCardList(Object object);
	
	/**
	 * 考勤查看-月考勤查看
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getEveryDayWorkList(Object object, int currentPage, int pageSize);
	
	/**
	 * 考勤查看-月考勤查看
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getEveryDayWorkList(Object object);
	
	/**
	 * 考勤查看-年假使用现状
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAnnualUsage(Object object);
	
	/**
	 * 考勤查看-年假使用现状-分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAnnualUsage(Object object, int currentPage, int pageSize);
	
	/**
	 * 考勤查看-年假使用现状-总条数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int getAnnualUsageCnt(Object object);
	
	
	/**
	 * 遍历LIST取出每个人当月每天的班次
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getMonthWorkScheduleForAR(Object obj) throws Exception;
	
	/**
	 * 遍历LIST取出每个人当月每天的考勤信息
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getMonthWorkListForAR(Object obj) throws Exception;
	/**
	 * 获取所有的考勤状态
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-27 下午06:55:33 
	* @version V1.0
	 */
	public List getControlItemList(Map paramMap);
	/**
	 * 月考勤监控
	 * @param request
	 * @return List
	 */
	public List getMonthControlList(Map paramMap);

	public int getMonthControlListCnt(Map<String, Object> paramMap);

	public List getMonthControlList(Object paramMap, int currentPage, int pageSize);
	
	/**
	 *  月考勤查看(个人新)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkNewOne(Object object);
	
	/**
	 *  月考勤查看(个人新)(TA)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getMonthWorkLGETANewOne(Object object);

	public List getAnnualUsageFact(Map paramMap);
	
	public List noSwipingCardList(Object object, int currentPage, int pageSize);

	public List getAnnualUsageFactLGE(Map paramMap);


	public List getArAfrimExceptionList(Object object, int pageNum, int numPerPage);

	public List getArAfrimExceptionList(Object object);

	public int getArAfrimExceptionCnt(Object object);

	public int getArExceptionTempErrorCnt(Object object);

	public int getArExceptionTempCnt(Object object);

	public List getArExceptionTempList(Object object, int pageNum, int numPerPage);

	public List getArExceptionTempList(Object object);





	public int getDetailControlListCnt(Map<String, Object> paramMap);

	List getDetailControlList(Object paramMap, int currentPage, int pageSize);

	List getDetailControlList(Map paramMap);

	public List getAllApplyList(Object paramMap, int pageNum,
			int numPerPage);


	public int getAllApplyListCnt(Map<String, Object> paramMap);

	List getAllApplyList(Map paramMap);

	List getItemForApplyList(Map paramMap);

	public List getItemForKaoQinList(Map paramMap);

}

