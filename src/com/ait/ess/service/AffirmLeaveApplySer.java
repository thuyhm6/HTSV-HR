package com.ait.ess.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
public interface AffirmLeaveApplySer {
	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getOtAffirmList(HttpServletRequest request) throws Exception;

	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getOtAffirmListCnt(HttpServletRequest request) throws Exception;

	/**
	 * 批量通过/否决加班申请(batch pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveOvertimeApplyInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决年假调整申请(batch pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int appAnnualadjustmentApplyInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决加班申请(pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveOvertimeApply(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决年假调整申请(pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int appAUUNApply(HttpServletRequest request) throws Exception;

	/**
	 * 加班申请check列表(overtime apply check list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getOtCheckList(HttpServletRequest request) throws Exception;

	/**
	 * 加班申请check总数(overtime apply check list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getOtCheckListCnt(HttpServletRequest request) throws Exception;

	/**
	 * check加班申请(check overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int checkApplyInfo(HttpServletRequest request) throws Exception;

	/**
	 * 添加checkor(add checkor)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addApplyCheckList(HttpServletRequest request) throws Exception;

	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getOvertimeAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getOvertimeAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 休假申请决裁列表(leave apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getLeaveApplyAffirmList(HttpServletRequest request)
			throws Exception;
	/**
	 * 休假申请决裁列表(leave apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getAttendanceAffirmList(HttpServletRequest request)
	throws Exception;

	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getLeaveApplyAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getEvectionApplyAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getEvectionApplyAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getEgeressionApplyAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getEgressionApplyAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决休假/出差/外出申请(batch pass and reject leave/evection/egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveLeaveApplyInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决休假/出差/外出申请(pass and reject leave/evection/egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveLeaveApply(HttpServletRequest request) throws Exception;

	/**
	 * 通过/否决休假申请(pass and reject leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveApplyLeave(HttpServletRequest request) throws Exception;

	public int approveApplyCwa(HttpServletRequest request) throws Exception;

	public int approveApplyCwaEP(HttpServletRequest request) throws Exception;
	/**
	 * 休假申请check列表(leave apply check list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getLeaveCheckList(HttpServletRequest request) throws Exception;

	/**
	 * 休假申请check总数(overtime apply check list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getLeaveCheckListCnt(HttpServletRequest request)
			throws Exception;

	public int approveApplication(HttpServletRequest request) throws Exception;

	public void updateApplicatCheck(HttpServletRequest request);

}
