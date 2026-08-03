package com.ait.ess.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ViewApplySer {

	/**
	 * 个人信息申请(view personal apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewPersonalInfoList(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班信息申请(view overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewOvertimeInfoList(HttpServletRequest request)
			throws Exception;

	/**
	 * 休假信息申请(view leave apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewLeaveInfoList(HttpServletRequest request) throws Exception;

	/**
	 * 出差信息申请(view evection apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewEvectionInfoList(HttpServletRequest request)
			throws Exception;

	/**
	 * 外出信息申请(view egression apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewEgressionInfoList(HttpServletRequest request)
			throws Exception;

	/**
	 * 个人信息申请个数(view personal apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int viewPersonalInfoListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班信息申请个数(view overtime apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int viewOvertimeInfoListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 休假信息申请个数(view leave apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int viewLeaveInfoListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 出差信息申请个数(view evection apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int viewEvectionInfoListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 外出信息申请个数(view egression apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int viewEgressionInfoListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 删除未审核个人信息申请(delete personal apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delPersonInfoApply(HttpServletRequest request)
			throws Exception;

	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delViewOtviewApply(HttpServletRequest request)
			throws Exception;

	/**
	 * 删除未审核休假信息申请(delete leave apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delViewLeaveviewApply(HttpServletRequest request)
			throws Exception;

	/**
	 * 删除未审核出差信息申请(delete evection apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delViewEvectionview(HttpServletRequest request)
			throws Exception;

	/**
	 * 删除未审核外出信息申请(delete egress apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delViewEgressionview(HttpServletRequest request)
			throws Exception;

	/**
	 * 审批状态查询
	 */
	public List getAffirmorList(HttpServletRequest request) throws Exception;
}
