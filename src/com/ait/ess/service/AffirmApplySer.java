package com.ait.ess.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface AffirmApplySer {
	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmList(HttpServletRequest request)
			throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getOtAffirmListTwo(HttpServletRequest request)
			throws Exception;
	@SuppressWarnings("unchecked")
	public List getOtAffirmListFinal(HttpServletRequest request,List list)
			throws Exception;
	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getOtAffirmListCnt(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getOtAffirmListTwoCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getOtAffirmListFinalCnt(HttpServletRequest request,List list)
			throws Exception;
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
	public int appAUUNApply(HttpServletRequest request)throws Exception;
			
	
	public int appAUUNApplyEP(HttpServletRequest request) throws Exception;
	/**
	 * 加班申请check列表(overtime apply check list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtCheckList(HttpServletRequest request)
			throws Exception;
	/**
	 * 加班申请check总数(overtime apply check list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getOtCheckListCnt(HttpServletRequest request)
			throws Exception;

	
	/**
	 * check加班申请(check overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int checkApplyInfo(HttpServletRequest request)
			throws Exception;
	
	/**
	 * check加班申请，最后一步check之后将ess_affirm中的current_check_id置空(check pa for left apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int checkPaForLeftApplyInfo(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 添加checkor(add checkor)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addApplyCheckList(HttpServletRequest request)
			throws Exception;
	/**
	 * 添加checkor(add checkor)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addApplyCheckListXiao(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 加班申请--编辑列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditOtApplyList(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 加班申请--批量修改编辑列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditOtApplyBatchList(HttpServletRequest request)
			throws Exception;
	/**
	 * 加班申请--编辑列表总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEditOtApplyListCnt(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 加班申请--编辑列表总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEditOtApplyListBatchCnt(HttpServletRequest request)
			throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOvertimeAffirmList(HttpServletRequest request)
			throws Exception;
	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getOvertimeAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 休假申请决裁列表(leave apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveApplyAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getLeaveApplyAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEvectionApplyAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEvectionApplyAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEgeressionApplyAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
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
	
	@SuppressWarnings("rawtypes")
	public List getHireAffirmList(HttpServletRequest request)throws Exception;
	public int getHireAffirmListCnt(HttpServletRequest request) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getHireAffirmByReqID(HttpServletRequest request)throws Exception;
	@SuppressWarnings("rawtypes")
	public List getCheckorByByReqID(HttpServletRequest request) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getHireAffirmListByReqID(HttpServletRequest request) throws Exception;
	public int getHireAffirmListByReqIDCnt(HttpServletRequest request) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getHireAffirm(HttpServletRequest request)throws Exception;
	public int approveApplyHire(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List getResignAffirmList(LinkedHashMap paramMap, HttpServletRequest request)throws Exception;
	public int getResignAffirmListCnt(LinkedHashMap paramMap, HttpServletRequest request) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getResignAffirmByReqID(HttpServletRequest request)throws Exception;
	@SuppressWarnings("rawtypes")
	public List getResignCheckorByByReqID(HttpServletRequest request) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getResignAffirmListByReqID(HttpServletRequest request) throws Exception;
	public int getResignAffirmListByReqIDCnt(HttpServletRequest request) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getResignAffirm(HttpServletRequest request)throws Exception;
	public int approveApplyResign(HttpServletRequest request) throws Exception;
	
	/**
	 * 考勤异常批量审批
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveCwaApplyInBatch(HttpServletRequest request)
			throws Exception ;
	
	@SuppressWarnings("rawtypes")
	public List getSellOutAffirmList(HttpServletRequest request)throws Exception;
	public int getSellOutAffirmListCnt(HttpServletRequest request) throws Exception;
}
