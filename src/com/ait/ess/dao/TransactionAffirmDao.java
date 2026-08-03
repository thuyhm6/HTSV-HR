package com.ait.ess.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 发令决裁(Transaction affirm)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: TransactionAffirmDao.java
 * @Description:
 * @Create date: Feb 20, 2012 2:50:02 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 20, 2012 2:50:02 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
public interface TransactionAffirmDao {
	/**
	 * 入职决裁列表(entry transaction affirm list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEntryTransAfiirmList(Object object) throws Exception;

	/**
	 * 入职决裁列表(entry transaction affirm list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEntryTransAfiirmList(Object object, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 入职决裁的总数(get entry transaction affirm total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getEntryTransAfiirmListCnt(Object object) throws Exception;

	/**
	 * 薪资调整决裁列表(view salary adjustment transaction affirm list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaAdjustTransAffirmList(Object object) throws Exception;

	/**
	 * 薪资调整决裁列表(view salary adjustment transaction affirm list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaAdjustTransAffirmList(Object object, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 薪资调整决裁总数(get salary adjustment transaction affirm total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getPaAdjustTransAffirmListCnt(Object object) throws Exception;

	/**
	 * 转正决裁列表(view probation transaction affirm list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getProbationTransAffirmList(Object object) throws Exception;

	/**
	 * 转正决裁列表(view probation transaction affirm list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getProbationTransAffirmList(Object object, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 转正决裁总数(get probation transaction affirm total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getProbationTransAffirmListCnt(Object object) throws Exception;

	/**
	 * 代理决裁列表(view agent transaction affirm list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAgentTransAffirmList(Object object) throws Exception;

	/**
	 * 代理决裁列表(view agent transaction affirm list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAgentTransAffirmList(Object object, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 代理决裁总数(get agent transaction affirm total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getAgentTransAffirmListCnt(Object object) throws Exception;

	/**
	 * 奖励决裁列表(view reward transaction affirm list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getRewardTransAffirmList(Object object) throws Exception;

	/**
	 * 奖励决裁列表(view reward transaction affirm list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getRewardTransAffirmList(Object object, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 奖励决裁总数(get reward transaction affirm total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getRewardTransAffirmListCnt(Object object) throws Exception;

	/**
	 * 惩戒决裁列表(view punishment transaction affirm list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPunishmentTransAffirmList(Object object) throws Exception;

	/**
	 * 惩戒决裁列表(view punishment transaction affirm list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPunishmentTransAffirmList(Object object, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 惩戒决裁总数(get punishment transaction affirm total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getPunishmentTransAffirmListCnt(Object object) throws Exception;

	/**
	 * 离职决裁列表(view resignation transaction affirm list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getResignationTransAffirmList(Object object) throws Exception;

	/**
	 * 离职决裁列表(view resignation transaction affirm list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getResignationTransAffirmList(Object object, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 离职决裁总数(get resignation transaction affirm total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getResignationTransAffirmListCnt(Object object) throws Exception;

	/**
	 * 调动决裁列表(view transaction transaction affirm list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTransferTransAffirmList(Object object) throws Exception;

	/**
	 * 调动决裁列表(view transaction transaction affirm list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getTransferTransAffirmList(Object object, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 调动决裁总数(get transaction transaction affirm total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getTransferTransAffirmListCnt(Object object) throws Exception;

	/**
	 * 升职/降职决裁列表(view promotion and relegation transaction affirm list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPromotRelegatTransAffirmList(Object object) throws Exception;

	/**
	 * 升职/降职决裁列表(view promotion and relegation transaction affirm list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPromotRelegatTransAffirmList(Object object, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 升职/降职决裁总数(get promotion and relegation transaction affirm total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getPromotRelegatTransAffirmListCnt(Object object)
			throws Exception;

	/**
	 * 兼职决裁列表(view plurality transaction affirm list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPluralityTransAffirmList(Object object) throws Exception;

	/**
	 * 兼职决裁列表(view plurality transaction affirm list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPluralityTransAffirmList(Object object, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 兼职决裁总数(get plurality transaction affirm total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getPluralityTransAffirmListCnt(Object object) throws Exception;

	/**
	 * 停职/复职决裁列表(view suspension transaction affirm list)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSuspensionTransAffirmList(Object object) throws Exception;

	/**
	 * 停职/复职决裁列表(view suspension transaction affirm list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSuspensionTransAffirmList(Object object, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 停职/复职决裁总数(get suspension transaction affirm total count)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getSuspensionTransAffirmListCnt(Object object) throws Exception;

	/**
	 * 批量通过/否决入职发令(batch pass and reject entry transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApproveEntryTransInBatch(List list) throws Exception;

	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveEntryTrans(Object object) throws Exception;

	/**
	 * 批量通过/否决薪资调整发令(batch pass and reject salary adjustment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApproveSalaryAdjustTransInBatch(List list) throws Exception;

	/**
	 * 通过/否决薪资调整发令(batch pass and reject salary adjustment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveSalaryAdjustTrans(Object object) throws Exception;

	/**
	 * 批量通过/否决转正调令(batch pass and reject Probation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApproveProbationTransInBatch(List list) throws Exception;

	/**
	 * 通过/否决转正调令(batch pass and reject Probation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveProbationTrans(LinkedHashMap object) throws Exception;

	/**
	 * 批量通过/否决代理调令(batch pass and reject agent transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApproveAgentTransInBatch(List list) throws Exception;

	/**
	 * 通过/否决代理调令(pass and reject agent transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveAgentTrans(Object object) throws Exception;

	/**
	 * 批量通过/否决奖励调令(batch pass and reject Reward transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApproveRewardTransInBatch(List list) throws Exception;

	/**
	 * 通过/否决奖励调令(pass and reject Reward transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveRewardTrans(Object object) throws Exception;

	/**
	 * 批量通过/否决惩戒调令(batch pass and reject punishment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApprovePunishmentTransInBatch(List list) throws Exception;

	/**
	 * 通过/否决惩戒调令(pass and reject Punishment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApprovePunishmentTrans(Object object) throws Exception;

	/**
	 * 批量通过/否决离职调令(batch pass and reject Resignation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApproveResignationTransInBatch(List list) throws Exception;

	/**
	 * 通过/否决离职调令(pass and reject Resignation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveResignationTrans(LinkedHashMap object) throws Exception;

	/**
	 * 批量通过/否决调动发令(batch pass and reject Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApproveTransactionTransInBatch(List list) throws Exception;

	/**
	 * 通过/否决调动发令(pass and reject Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveTransactionTrans(LinkedHashMap object) throws Exception;

	/**
	 * 批量通过/否决升职/降职调令(batch pass and reject PromotRelegat transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApprovePromotRelegatTransInBatch(List list) throws Exception;

	/**
	 * 批量通过/否决升职/降职调令(batch pass and reject PromotRelegat transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApprovePromotRelegatTrans(LinkedHashMap object) throws Exception;

	/**
	 * 批量通过/否决兼职调令(batch pass and reject Plurality transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApprovePluralityTransInBatch(List list) throws Exception;

	/**
	 * 通过/否决兼职调令(pass and reject Plurality transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApprovePluralityTrans(Object object) throws Exception;

	/**
	 * 批量通过/否决停职/复职调令(batch pass and reject Suspension transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApproveSuspensionTransInBatch(List list) throws Exception;

	/**
	 * 通过/否决 停职/复职调令(pass and reject Suspension transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveSuspensionTrans(Object object) throws Exception;

	/**
	 * 获得当前发令决裁流程中最大的决裁级别(get Max Affirm Flag By ExpInsideNo )
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getMaxAffirmLevelByExpInsideNo(Object obj) throws Exception;

	/**
	 * 通过发令号获得该发令决裁流程中的决裁者(get affirmer list by ExpInsideNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getAffirmerListByExpInsideNo(Object obj) throws Exception;

	/**
	 * 取当前发令流程中的决裁级别(get current affirm level)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getCurrentAffirmLevel(Object obj) throws Exception;

	/**
	 * 获得下一步决裁信息(get next hr_affirm information by expInsideNo)
	 * 
	 * @param object
	 * @return
	 */
	public Object getHrAffirmInfoByExpInsideNoAndLevel(Object object)
			throws Exception;

	/**
	 * 通过发令NO获得信息(get information by ExpInsideNo)
	 * 
	 * @param object
	 * @return
	 */
	public Object getExpInsideAndHrAffirmByNo(Object object) throws Exception;

	/**
	 * 通过发令NO获得信息--入职用(get information by ExpInsideNo for entry transaction)
	 * 
	 * @param object
	 * @return
	 */
	public Object getExpInsideAndHrAffirmByNoForEntryTrans(Object object)
			throws Exception;

	/**
	 * 取当前SY_USER表的序列(get sy_user sequences next value)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getSyUserSeqNextVal() throws Exception;

	public List getPayStepTransAffirmList(Object obj, int currentPage, int pageSize);
	
	public List getPayStepTransAffirmList(Object obj) throws Exception;

	int saveApprovePayStepTransInBatch(List list) throws Exception;

	public int getPayStepTransAffirmListCnt(Object obj) throws Exception;
	
	public int saveApprovePayStepTrans(LinkedHashMap object)throws Exception;
	
	public List getHrExperienceInsideSaveByTransCodeByEss(Object object) throws Exception;
	public List getHrExperienceInsideSaveByTransCodeByEss(Object obj, int currentPage, int pageSize) throws Exception;

	public int getHrExperienceInsideSaveByTransCodeByEssCnt(Object obj)
			throws Exception;
	

	/**
	 *异动决裁列表(view reward transaction affirm list)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public List getHrExpInsideList(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public List getHrExpInsideList(Object object, int currentPage,
			int pageSize) throws Exception;

	int getHrExpInsideListCnt(Object obj) throws Exception;

	
}