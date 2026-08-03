package com.ait.ess.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 发令决裁(Transaction affirm)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: TransactionAffirmSer.java
 * @Description:
 * @Create date: Feb 20, 2012 2:29:08 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 20, 2012 2:29:08 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
public interface TransactionAffirmSer {
	/**
	 * 入职决裁列表(entry transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEntryTransAfiirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 入职决裁的总数(get entry transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getEntryTransAfiirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决入职发令(batch pass and reject entry transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveEntryTransInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveEntryTrans(HttpServletRequest request) throws Exception;

	/**
	 * 薪资调整决裁列表(view salary adjustment transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaAdjustTransAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 薪资调整决裁总数(get salary adjustment transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getPaAdjustTransAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决薪资调整发令(batch pass and reject salary adjustment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveSalaryAdjustTransInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决薪资调整发令(batch pass and reject salary adjustment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveSalaryAdjustTrans(HttpServletRequest request)
			throws Exception;

	/**
	 * 转正决裁列表(view probation transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getProbationTransAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 转正决裁总数(get probation transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getProbationTransAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决转正调令(batch pass and reject Probation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveProbationTransInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决转正调令(batch pass and reject Probation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveProbationTrans(HttpServletRequest request)
			throws Exception;

	/**
	 * 代理决裁列表(view agent transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAgentTransAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 代理决裁总数(get agent transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getAgentTransAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决代理调令(batch pass and reject agent transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveAgentTransInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决代理调令(pass and reject agent transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveAgentTrans(HttpServletRequest request) throws Exception;

	/**
	 * 奖励决裁列表(view reward transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getRewardTransAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 奖励决裁总数(get reward transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getRewardTransAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决奖励调令(batch pass and reject Reward transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveRewardTransInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决奖励调令(pass and reject Reward transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveRewardTrans(HttpServletRequest request) throws Exception;

	/**
	 * 惩戒决裁列表(view punishment transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPunishmentTransAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 惩戒决裁总数(get punishment transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getPunishmentTransAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决惩戒调令(batch pass and reject punishment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approvePunishmentTransInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决惩戒调令(pass and reject Punishment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approvePunishmentTrans(HttpServletRequest request)
			throws Exception;

	/**
	 * 离职决裁列表(view resignation transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getResignationTransAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 离职决裁总数(get resignation transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getResignationTransAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决离职调令(batch pass and reject Resignation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveResignationTransInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决离职调令(pass and reject Resignation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveResignationTrans(HttpServletRequest request)
			throws Exception;

	/**
	 * 调动决裁列表(view transaction transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTransferTransAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 调动决裁总数(get transaction transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getTransferTransAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决调动发令(batch pass and reject Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveTransactionTransInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决调动发令(pass and reject Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveTransactionTrans(HttpServletRequest request)
			throws Exception;

	/**
	 * 升职/降职决裁列表(view promotion and relegation transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPromotRelegatTransAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 升职/降职决裁总数(get promotion and relegation transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getPromotRelegatTransAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决升职/降职调令(batch pass and reject PromotRelegat transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approvePromotRelegatTransInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决升职/降职调令(batch pass and reject PromotRelegat transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approvePromotRelegatTrans(HttpServletRequest request)
			throws Exception;

	/**
	 * 兼职决裁列表(view plurality transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPluralityTransAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 兼职决裁总数(get plurality transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getPluralityTransAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决兼职调令(batch pass and reject Plurality transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approvePluralityTransInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决兼职调令(pass and reject Plurality transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approvePluralityTrans(HttpServletRequest request)
			throws Exception;

	/**
	 * 停职/复职决裁列表(view suspension transaction affirm list
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSuspensionTransAffirmList(HttpServletRequest request)
			throws Exception;

	/**
	 * 停职/复职决裁总数(get suspension transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getSuspensionTransAffirmListCnt(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决停职/复职调令(batch pass and reject Suspension transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveSuspensionTransInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决 停职/复职调令(pass and reject Suspension transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveSuspensionTrans(HttpServletRequest request)
			throws Exception;
	
	public List getPayStepAffirmList(HttpServletRequest request) throws Exception;
	
	public int approvePayStepTransInBatch(HttpServletRequest request)
	throws Exception;

	public Object getPayStepTransAffirmListCnt(HttpServletRequest request) throws Exception;
	
	public int approvePayStepTrans(HttpServletRequest request)
	throws Exception;
	//异动
	public List getHrExperienceInsideSaveByTransCodeByEss(HttpServletRequest request) throws Exception;
	//异动总数
	public int getHrExperienceInsideSaveByTransCodeByEssCnt(HttpServletRequest request) throws Exception;
	/**
	 * 根据不同的异动类型 裁决信息
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-2 下午1:54:56 
	* @version V1.0
	 * @throws Exception 
	 */
	/**
	 * 异动决裁列表(view reward transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List ViewHrExpInsideList(HttpServletRequest request)
			throws Exception;
	/**
	 * 异动决裁列表(view reward transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int ViewHrExpInsideListCnt(HttpServletRequest request)
			throws Exception;
	
	
	//
	
}
