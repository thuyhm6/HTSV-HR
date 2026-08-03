package com.ait.ess.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: HumanAffirmApplySer.java
 * @Description:人事确认
 * @Create date: Apr 10, 2012 10:05:25 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Apr 10, 2012 10:05:25 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
public interface HumanAffirmApplySer {

	/**
	 * 个人信息申请集合(personal information apply list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonInfoApplyConfirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 个人信息申请总数(personal information apply total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPersonInfoApplyConfirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 人事确认通过/否决(human confirm pass or reject)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int savePersonInfoHumanConfirm(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量人事确认通过/否决(batch human confirm pass or reject)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int savePersonInfoHumanConfirmInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 人事确认--加班申请信息列表(personnel confirm:overtime apply information list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOvertimeApplyConfirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 查询属于该法人的加班类型List
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOverTimeApplyTypeList(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 查询属于该法人的加班转换类型List
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getConverTypeList(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 查询属于该法人的加班转默认调休类型List
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDefaultDaoXiuList(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 人事确认--加班申请信息总数(personnel confirm:overtime apply information total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getOvertimeApplyConfirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请人事确认通过/否决(human confirm pass or reject)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveOvertimeApplyHumanConfirm(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请批量人事确认通过/否决(batch human confirm pass or reject)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveOvertimeApplyHumanConfirmInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 人事确认--休假申请列表(personnel confirm:leave apply information list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveApplyConfirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 人事确认--休假申请列表(personnel confirm:leave apply information total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getLeaveApplyConfirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 人事确认--出差申请列表(personnel confirm:Evection apply information list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEvectionApplyConfirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 人事确认--出差申请列表(personnel confirm:Evection apply information total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEvectionApplyConfirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 人事确认-外出申请列表(personnel confirm:Egression apply information list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEgressionApplyConfirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 人事确认-外出申请总数(personnel confirm:Egression apply information total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEgressionApplyConfirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 休假申请批量人事确认通过/否决(batch human confirm pass or reject)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveLeaveApplyHumanConfirmInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 休假申请人事确认通过/否决(human confirm pass or reject)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveLeaveApplyHumanConfirm(HttpServletRequest request)
			throws Exception;

	/**
	 * 查看是否需要加班转换(get If Conver Value)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getIfConverValue(HttpServletRequest request) throws Exception;
}
