package com.ait.hrm.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: TransferOrderDao.java
 * @Description:
 * @Create date: 2012-2-23 下午10:31:02
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface TransferOrderDao {

	public String getNextExpInside();
	
	public String getNextResignNo();

	public String getNextPersonId();

	public String getPersonId(Map paramMap);

	public String getNextUserNo();

	public String getRoleGroupNo(Object object);

	public List getRoleGroupList(Object object);

	public int getSyUserSeqNextVal() throws Exception;

	public int getValidationEmpid(Object object);

	public int getValidationIdCardNo(Object object);

	public int getParamInfoValue(Object object);

	@SuppressWarnings("unchecked")
	public Map getTransferOrderList(Map object);

	@SuppressWarnings("unchecked")
	public Map getDispatchInfo(Map object);

	@SuppressWarnings("unchecked")
	public Map getPluralityList(Map object);

	@SuppressWarnings("unchecked")
	public void saveResign(List list) throws Exception;

	public void savePersonChangeInfo(Object object) throws Exception;

	public void saveExperienceForRemoveAndUpHrEmployee(Object obj)
			throws Exception;

	public void saveExperienceForRemoveAndNoUpHE(Object obj) throws Exception;

	public void saveExperienceForRemoveANDAffirmor(Object obj) throws Exception;

	public void saveExperienceForRemoveAndAffirmList(Object obj, List list)
			throws Exception;

	public void saveExperienceForTransNormal(Object obj) throws Exception;

	public void saveExperienceForTransNormalAndUpHrEmployee(Object obj)
			throws Exception;

	public void saveExperienceForTransNormalAndUpHrEmployeeAndUpDateStarted(
			Object obj) throws Exception;

	public void saveExperienceForTransNormalAndAffirmList(Object obj, List list)
			throws Exception;

	public void saveExperienceForTransNormalAndAffirm(Object obj)
			throws Exception;

	public void saveAffirmor(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveReward(List list) throws Exception;

	@SuppressWarnings("unchecked")
	public void savePunishment(List list) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveDispath(List list) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveDispathEnd(List list) throws Exception;

	@SuppressWarnings("unchecked")
	public void savePlurality(List list) throws Exception;

	@SuppressWarnings("unchecked")
	public void savePluralityEnd(List list) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveBusiness(List list) throws Exception;

	public void saveHire(Object obj) throws Exception;

	public void saveSyUser(Object obj) throws Exception;

	public void saveHireEffective(Object obj) throws Exception;

	public void saveHireEffectiveANDUserRelation(Object obj) throws Exception;

	public void saveHireAffirm(Object obj) throws Exception;

	public void saveHireAffirmList(Object obj, List list) throws Exception;

	public void hrUpdateAuto() throws Exception;

	@SuppressWarnings("unchecked")
	public List getViewUpgradeList(Object object);

	public int getViewUpgradeCnt(Object object);

	@SuppressWarnings("unchecked")
	public List getViewUpgradeList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public List getAffirmorIdListByPersonal(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorIdListByPersonal1(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAffirmorIdListByDept(Object object);

	@SuppressWarnings("unchecked")
	public List getSpecialAffirmorList(Object object);

	public Object getSpecialAffirmorPersonId(Object object);

	@SuppressWarnings("unchecked")
	public void saveAffirmor(List list) throws Exception;

	@SuppressWarnings("unchecked")
	public List getPositionList(Object object);

	@SuppressWarnings("unchecked")
	public List getDutyList(Object object);

	@SuppressWarnings("unchecked")
	public List getPostGradeList(Object object);

	@SuppressWarnings("unchecked")
	public List getOldPostGradeList(Object object);

	@SuppressWarnings("unchecked")
	public List getPostList(Object object);

	@SuppressWarnings("unchecked")
	public List getWorkAreaList(Object object);

	@SuppressWarnings("unchecked")
	public List getPostListByPostGradeNo(Object object);

	@SuppressWarnings("unchecked")
	public List getDutyListByPostGradeNo(Object object);

	@SuppressWarnings("unchecked")
	public List getWorkAreaByDept(Object object);

	@SuppressWarnings("unchecked")
	public List getSocialSecurityAreaByWorkArea(Object object);

	@SuppressWarnings("unchecked")
	public List getTransferNormalList(Object object);

	public int getTransferNormalCnt(Object object);

	@SuppressWarnings("unchecked")
	public List getTransferNormalList(Object object, int currentPage,
			int pageSize);

	public void updateHrEmpDept(Object obj) throws Exception;

	public void updateHrEmpDuty(Object obj) throws Exception;

	public void updateHrEmpPost(Object obj) throws Exception;

	public void updateHrEmpPostGrade(Object obj) throws Exception;

	public void updateHrEmpPosition(Object obj) throws Exception;

	public void updateHrEmpWorkArea(Object obj) throws Exception;

	public void saveHrEmpDept(Object obj) throws Exception;

	public void saveHrEmpDuty(Object obj) throws Exception;

	public void saveHrEmpPost(Object obj) throws Exception;

	public void saveHrEmpPostGrade(Object obj) throws Exception;

	public void saveHrEmpPosition(Object obj) throws Exception;

	public void saveHrEmpWorkArea(Object obj) throws Exception;

	public void updateHrEmployee(Object obj) throws Exception;

	public void updateHrEmployeeForTransferNormal(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public List getTransferPromoteList(Object object);

	public int getTransferPromoteCnt(Object object);

	@SuppressWarnings("unchecked")
	public List getTransferPromoteList(Object object, int currentPage,
			int pageSize);

	public void saveExperienceForPromote(Object obj) throws Exception;

	public void saveExperienceForPromoteAndAffirm(Object obj) throws Exception;

	public void saveExperienceForPromoteAndAffirmList(Object obj, List list)
			throws Exception;

	public void saveExperienceForPromoteAndUpHrEmployee(Object obj)
			throws Exception;

	public void saveExperienceForPromoteAndNoUpHEe(Object obj) throws Exception;

	public int checkSaveTransferOrderUpgradeInprogres(Object object);
	public int checkSaveTransferOrderUpgrade1(Object object);
	public int checkSaveTransfer(Object object);

	public int checkSaveTransferNormal(Object object);

	public int checkSaveTransferPromote(Object object);

	public int checkSaveResignation(Object object);

	/**
	 * 查看转正
	 */
	@SuppressWarnings("unchecked")
	public Map getTransferNormalForSearch(Object object, int skipResults,
			int maxResults);

	/**
	 * 查看转职
	 */
	@SuppressWarnings("unchecked")
	public Map getTransferPostForSearch(Object object, int skipResults,
			int maxResults);

	/**
	 * 查看晋升
	 */
	@SuppressWarnings("unchecked")
	public Map getTransferPromoteForSearch(Object object, int skipResults,
			int maxResults);

	/**
	 * 查看担当业务变更
	 */
	@SuppressWarnings("unchecked")
	public Map getTransferActBusinessForSearch(Object object, int skipResults,
			int maxResults);

	/**
	 * 查看兼职
	 */
	@SuppressWarnings("unchecked")
	public Map getPluralityForSearch(Object object, int skipResults,
			int maxResults);

	/**
	 * 查看派遣
	 */
	@SuppressWarnings("unchecked")
	public Map getDispatchForSearch(Object object, int skipResults,
			int maxResults);

	/**
	 * 查看停职
	 */
	@SuppressWarnings("unchecked")
	public Map getSuspendForSearch(Object object, int skipResults,
			int maxResults);

	/**
	 * 查看奖励
	 */
	@SuppressWarnings("unchecked")
	public Map getHortationForSearch(Object object, int skipResults,
			int maxResults);

	/**
	 * 查看惩戒
	 */
	@SuppressWarnings("unchecked")
	public Map getPunishMentForSearch(Object object, int skipResults,
			int maxResults);

	/**
	 * 查看离职
	 */
	@SuppressWarnings("unchecked")
	public Map getResignForSearch(Object object, int skipResults, int maxResults);

	/**
	 * 查看职员调令历史
	 */
	@SuppressWarnings("unchecked")
	public Map searchEmpHistory(Object object, int skipResults, int maxResults);

	/**
	 * 查看职员担当调令历史
	 */
	@SuppressWarnings("unchecked")
	public Map searchEmpHistoryForActBusiness(Object object, int skipResults,
			int maxResults);

	/**
	 * 取消调令
	 * 
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateRollBackUpgrade(Object object) throws Exception;

	/**
	 * 取消担当调令
	 * 
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateRollBackActBusiness(Object object) throws Exception;

	/**
	 * 取消派遣调令
	 * 
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateRollBackDispatch(Object object) throws Exception;

	/**
	 * 取消奖励调令
	 * 
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateRollBackHortation(Object object) throws Exception;

	/**
	 * 取消兼职调令
	 * 
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateRollBackPlurality(Object object) throws Exception;

	/**
	 * 取消惩戒调令
	 * 
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateRollBackPunishMent(Object object) throws Exception;

	/**
	 * 取消离职调令
	 * 
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateRollBackResign(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public int getNextEmpid(Object object);

	@SuppressWarnings("unchecked")
	public String getNextPrEmpid(Object object);

	@SuppressWarnings("unchecked")
	public List getViewResignList(Object object);

	@SuppressWarnings("unchecked")
	public List getViewResignList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public int getViewResignCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getViewTempEmpList(Object object);

	@SuppressWarnings("unchecked")
	public List getViewTempEmpList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public int getViewTempEmpCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getViewResignEditList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getViewResignEditList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public int getViewResignEditCnt(Object object);


	@SuppressWarnings("unchecked")
	public void saveExperienceForResignation(Object obj) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateExperienceForResignation(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForResignationAndAffirm(Object obj)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForResignationAndUpEmpAndUpAllHrEmp(Object obj)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForResignationAndAffirmList(Object obj, List list)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void updateAllHrEmp(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void updateHrEmployeeForResignation(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public List getViewPluralityList(Object object);

	@SuppressWarnings("unchecked")
	public List getViewPluralityList(Object object, int currentPage,
			int pageSize);

	@SuppressWarnings("unchecked")
	public int getViewPluralityCnt(Object object);

	@SuppressWarnings("unchecked")
	public int checkSavePlurality(Object object);

	@SuppressWarnings("unchecked")
	public void saveExperienceForPlurality(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForPluralityAndAffirm(Object obj)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForPluralityAndAffirmList(Object obj, List list)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void updateExperienceForPlurality(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public String getExpInsideNo(Object obj) throws Exception;
	
	@SuppressWarnings("unchecked")
	public String getExpInsideNo1(Object obj) throws Exception;


	@SuppressWarnings("unchecked")
	public int checkedExpInsideNo(Object object);

	@SuppressWarnings("unchecked")
	public void saveExperienceForRemovePlurality(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForRemovePluralityAndAffirm(Object obj)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForRemovePluralityAndUpPlurality(Object obj)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForRemovePluralityAndAffirmList(Object obj,
			List list) throws Exception;

	@SuppressWarnings("unchecked")
	public List getPluralityForRemove(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public void updatePlurality(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public List getSuspendList(Object object);

	@SuppressWarnings("unchecked")
	public List getSuspendList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public int getSuspendListCnt(Object object);

	@SuppressWarnings("unchecked")
	public int checkSaveSuspend(Object object);

	@SuppressWarnings("unchecked")
	public void saveExperienceForSuspend(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForSuspendAndAffirm(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForSuspendAndAffirmList(Object obj, List list)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void updateHrEmployeeForSuspend(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public String getExpInsideNoForSuspend(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public String getStatusCodeForSuspend(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void updateSuspend(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void updateHrEmployeeForReinstated(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForSuspendNoaffirm(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public List getRewardList(Object object);

	@SuppressWarnings("unchecked")
	public List getRewardList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public int getRewardListCnt(Object object);

	@SuppressWarnings("unchecked")
	public void saveExperienceForReward(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForRewardAndAffirm(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForRewardAndAffirmList(Object obj, List list)
			throws Exception;

	@SuppressWarnings("unchecked")
	public int checkSaveReward(Object object);

	@SuppressWarnings("unchecked")
	public List getPunishMentList(Object object);

	@SuppressWarnings("unchecked")
	public List getPunishMentList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public int getPunishMentListCnt(Object object);

	@SuppressWarnings("unchecked")
	public int checkSavePunishMent(Object object);

	@SuppressWarnings("unchecked")
	public void saveExperienceForPunishMent(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForPunishMentAndAffirm(Object obj)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForPunishMentAndAffirmList(Object obj, List list)
			throws Exception;

	@SuppressWarnings("unchecked")
	public List getPayriseList(Object object);

	@SuppressWarnings("unchecked")
	public List getPayriseList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public int getPayriseListCnt(Object object);

	@SuppressWarnings("unchecked")
	public List paBasicItemList(Object object);

	@SuppressWarnings("unchecked")
	public List getReturnValueByItemNo(Object object);

	@SuppressWarnings("unchecked")
	public int checkSavePayrise(Object object);

	@SuppressWarnings("unchecked")
	public int checkSavePayriseByStartDate(Object object);

	@SuppressWarnings("unchecked")
	public void saveExperienceForPayrise(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForPayriseAndAffirmList(Object obj, List list)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForPayriseNoAffirm(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceOnlyPayrise(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public int getDateCount(Object object);

	@SuppressWarnings("unchecked")
	public List getBeforeDataForDept(Object object);

	@SuppressWarnings("unchecked")
	public List getAfterDataForDept(Object object);

	@SuppressWarnings("unchecked")
	public void saveAndUpdateForUpGradeHrEmpDept(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveAndUpdateForAgentHrEmpDept(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveHrEmpDeptForUpGrade(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveHrEmpDeptForAgent(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public List getBeforeDataForDuty(Object object);

	@SuppressWarnings("unchecked")
	public List getAfterDataForDuty(Object object);

	@SuppressWarnings("unchecked")
	public void saveAndUpdateForUpGradeHrEmpDuty(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveHrEmpDutyForUpGrade(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveHrEmpDutyForAgent(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public List getBeforeDataForPost(Object object);

	@SuppressWarnings("unchecked")
	public List getAfterDataForPost(Object object);

	@SuppressWarnings("unchecked")
	public void saveAndUpdateForUpGradeHrEmpPost(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveHrEmpPostForUpGrade(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public List getBeforeDataForPostGrade(Object object);

	@SuppressWarnings("unchecked")
	public List getAfterDataForPostGrade(Object object);

	@SuppressWarnings("unchecked")
	public void saveAndUpdateForUpGradeHrEmpPostGrade(Object obj)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void saveHrEmpPostGradeForUpGrade(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public List getBeforeDataForPosition(Object object);

	@SuppressWarnings("unchecked")
	public List getAfterDataForPosition(Object object);

	@SuppressWarnings("unchecked")
	public void saveAndUpdateForUpGradeHrEmpPosition(Object obj)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void saveAndUpdateForAgentHrEmpPosition(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveHrEmpPositionForUpGrade(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveHrEmpPositionForAgent(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public List getBeforeDataForWorkArea(Object object);

	@SuppressWarnings("unchecked")
	public List getAfterDataForWorkArea(Object object);

	@SuppressWarnings("unchecked")
	public void saveAndUpdateForUpGradeHrEmpWorkArea(Object obj)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void saveAndUpdateForAgentHrEmpWorkArea(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveHrEmpWorkAreaForUpGrade(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveHrEmpWorkAreaForAgent(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public List getAgentList(Object object);

	@SuppressWarnings("unchecked")
	public List getAgentList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public int getAgentCnt(Object object);

	public int checkSaveTransferOrderAgent(Object object);

	public void saveExperienceForAgentAndAffirmList(Object obj, List list)
			throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForAgent(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveAndUpdateForAgentHrEmpDuty(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveExperienceForAgentAndUpHrEmployee(Object obj)
			throws Exception;

	@SuppressWarnings("unchecked")
	public int getAgentCountForHrEmployeesAgentPostGradeNo(Object object);

	@SuppressWarnings("unchecked")
	public int getDateDifference(Object object);

	@SuppressWarnings("unchecked")
	public int getAgentCountByPid(Object object);

	@SuppressWarnings("unchecked")
	public String getExpInsideNoForAgent(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveAgentAndUpHrEmployeeForCancel(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void saveAgentForCancel(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public int checkNeedChangeHeEmptypeCode(Object object);

	// 定时器start

	@SuppressWarnings("unchecked")
	public List getEmployeeTempList();

	@SuppressWarnings("unchecked")
	public void updateHrEmployeeTemp(Object obj);

	@SuppressWarnings("unchecked")
	public List getPersonalInfoTempList();

	@SuppressWarnings("unchecked")
	public void saveEmpAndPerForHireTimers(List emplist, List perList);

	@SuppressWarnings("unchecked")
	public List getExperienceInsideListForUpgrade();

	@SuppressWarnings("unchecked")
	public void updateEmpAndExpForUpgradeTimers(Object obj);

	@SuppressWarnings("unchecked")
	public List getProbationListForTransferNormal();

	@SuppressWarnings("unchecked")
	public void updateHrEmployeeForTN(Object obj);

	@SuppressWarnings("unchecked")
	public void updateHrEmployeeDateStated(Object obj);

	@SuppressWarnings("unchecked")
	public void updateProbationForTransferNormal(Object obj);

	@SuppressWarnings("unchecked")
	public List getExperienceInsideListForTransferPromote();

	@SuppressWarnings("unchecked")
	public List getPluralityList();

	@SuppressWarnings("unchecked")
	public List PluralityListForCancle();

	@SuppressWarnings("unchecked")
	public void updatePluralityForTimer(Object obj);

	@SuppressWarnings("unchecked")
	public void updatePluralityCancleForTimer(Object obj);

	@SuppressWarnings("unchecked")
	public List getSuspendListForTimer();

	@SuppressWarnings("unchecked")
	public void updateHrEmployeesStatusCode(Object obj);

	@SuppressWarnings("unchecked")
	public String getCpnyIdByPersonId(Object obj);

	@SuppressWarnings("unchecked")
	public void updateDateForSuspendTimers(Object obj);

	@SuppressWarnings("unchecked")
	public void updateDateForReinstatedTimers(Object obj);

	@SuppressWarnings("unchecked")
	public List getReinstatedListForTimer();

	@SuppressWarnings("unchecked")
	public List getRewardListForTimer();

	@SuppressWarnings("unchecked")
	public void updateHrRewardForTimers(Object obj);

	@SuppressWarnings("unchecked")
	public List getPunishmentListForTimer();

	@SuppressWarnings("unchecked")
	public void updateHrPunishmentForTimers(Object obj);

	@SuppressWarnings("unchecked")
	public List getResignListForTimer();

	@SuppressWarnings("unchecked")
	public void updateHrResignForTimers(Object obj);

	@SuppressWarnings("unchecked")
	public List getPayriseListForTimer();

	@SuppressWarnings("unchecked")
	public void updateHrPayriseForTimers(Object obj);

	@SuppressWarnings("unchecked")
	public List getAgentListForTimer();

	@SuppressWarnings("unchecked")
	public void updateHremployeeForTimers(Object obj);

	@SuppressWarnings("unchecked")
	public List getCancleAgentListForTimer();

	@SuppressWarnings("unchecked")
	public void updateHremployeeForCancleAgentTimers(Object obj);

	// 定时器end

	@SuppressWarnings("unchecked")
	public List getEmpSearchList(Object object);

	@SuppressWarnings("unchecked")
	public List getEmpSearchList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public int getEmpSearchCnt(Object object);

	@SuppressWarnings("unchecked")
	public List getEidListForSearch(Object object);

	@SuppressWarnings("unchecked")
	public int checkPersonId(Object object);

	@SuppressWarnings("unchecked")
	public int checkedIdcardNoReqStatus(Object object);

	@SuppressWarnings("unchecked")
	public int checkIdcardNo(Object object);

	@SuppressWarnings("unchecked")
	public List checkIdcardNoAgain(Object object);

	@SuppressWarnings("unchecked")
	public List getHaoFengList(Object object);

	// 转正时在HR_CHANGE_STATUS表中添加一条数据HR_CHANGE_STATUS表
	public void savePersonStatus(Object object);

	// 转正时更新HR_CHANGE_STATUS表
	public void updatePersonStatus(Object object);

	// 离职时在HR_CHANGE_STATUS表中添加一条数据HR_CHANGE_STATUS表
	public void savePersonStatusl(Object object);

	// 离职时更新HR_ASSIGNMENT表
	public void saveResignOrder(Object object);

	// 离职是查询该职员的最后一条打卡数据
	public String getLastRecord(Object object);

	// 离职时更新HR_RESIGNATION表中LAST_CARD_DATE字段
	public void changeLastRecord(Object object) throws SQLException;

	// 如果为法人为C04，劳务转正时，修改员工职级1级为4级，并修改入职日期
	public void specialUpdateForC01(Object object) throws SQLException;

	@SuppressWarnings("unchecked")
	public List getPayStepList(Object object);

	@SuppressWarnings("unchecked")
	public List getPayStepList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public int getPayStepListCnt(Object object);

	@SuppressWarnings("unchecked")
	public int getPopMarkFlag(Object object);

	@SuppressWarnings("unchecked")
	public List getSaParamItemParamList(Object object);

	@SuppressWarnings("unchecked")
	public List getBnParamItemParamList(Object object);

	@SuppressWarnings("unchecked")
	public List getInParamItemParamList(Object object);

	@SuppressWarnings("unchecked")
	public List getPaBasicItemParamList(Object object);

	@SuppressWarnings("unchecked")
	public void saveSaBnIn(HttpServletRequest request, LinkedHashMap sadata,
			LinkedHashMap bndata, LinkedHashMap indata, LinkedHashMap padata,
			LinkedHashMap paramMap) throws SQLException;

	// 获取旧职级列表
	@SuppressWarnings("unchecked")
	public List getOldPostGradeList2(Object object);

	public int checkSavePayStep(Object object);

	public void saveExperienceForPayStepAndAffirm(Object obj, List list)
			throws Exception;

	public void saveExperienceForPayStep(Object obj) throws Exception;

	public void savePayStep(Object obj) throws Exception;

	public int haveEalierPayStepData(Object object);

	public List getExperienceInsideListForPayStep();

	public void savePayStepForTimer(Object obj) throws SQLException;

	public List getGradeLevelNoByPostGradeNoList(Object obj);

	public List getRecSourceList(Object obj);

	public List getRecSourceDetailByRecSource(Object obj);

	public List getRecSourceListForUpdate(Object obj);

	public List getTransList(Object object);

	public int checkIdcardNo15(Object object);

	public int checkIdcardNo18(Object object);
	
	public int checkedPassportNo(Object object);

	public List checkIdcardNoAgain15(Object object);

	public List checkIdcardNoAgain18(Object object);

	public List getBLACKLISTList(Object object);
	
	public List getHrEmployeeList(Object object);
	
	public List getTranferOrderTitile(Object object);
	
	@SuppressWarnings("unchecked")
	public void submitAddHrDispatch(List list) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updatesubmitAddHrDispatch(List list) throws Exception;
	
	public List getHrExperienceInsideByPersonId(Object object);
	
	
	public List getEmpIdList(Object object, int currentPage, int pageSize);
	
	public List getEmpIdList(Object object);
	
	public int getEmpIdListCnt(Object object);
	
	public int getAddHrDispatchCnt(Object object);

	/**
	 * 根据输入的员工编号查询员工
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getEmployeeByEmpId(Map paramMap)throws Exception;

	@SuppressWarnings("unchecked")
	public List getOrderParmList(Object object)throws Exception;

	@SuppressWarnings("unchecked")
	public List getTransferOrderEmpList(Object object, int currentPage, int pageSize)throws Exception;

	@SuppressWarnings("unchecked")
	public List getTransferOrderEmpList(Object object)throws Exception;

	@SuppressWarnings("unchecked")
	public int getTransferOrderEmpListCnt(Object object)throws Exception;

	/**
	 * 保存“奖励”发令信息(临时储存)
	 * @param paramMap
	 * @throws Exception
	 */
	public void storeReward(LinkedHashMap paramMap)throws Exception;

	/**
	 * 保存"惩罚"发令信息(临时储存)
	 * @param request
	 * @return
	 */
	public void storePunishment(LinkedHashMap paramMap)throws Exception;

	/**
	 * 查询"奖励"保存过的记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getStoredRewardList(LinkedHashMap paramMap)throws Exception;

	/**
	 * 查询"惩罚"保存过的记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getStoredPunishmentList(LinkedHashMap paramMap)throws Exception;

	/**
	 * 保存“奖励”记录之前删除上次记录
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-28 上午12:24:01 
	* @version V1.0
	 */
	public void deleteStroedRewardRecordsForSave(LinkedHashMap map)throws Exception;
	

	@SuppressWarnings("unchecked")
	public List getHrDispatch(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getHrDispatchUpdate(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public List getHrDispatch2(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getHrDispatch3(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getHrDispatchCnt(Object object) throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public int getHrDispatchUpdateCnt(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getHrDispatch(Object object, int currentPage, int pageSize) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getHrDispatchUpdate(Object object, int currentPage, int pageSize) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void SaveHrDispInside(String transNo) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void SaveHrExperienceInside(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public void SaveHrExperienceInsideSave(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public void SaveHrExperienceInsideSave1(Object object) throws Exception;
	
	
	
	@SuppressWarnings("unchecked")
	//保存到临时表
	public void SaveHrExperienceInsideSave_send_1(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	//保存到正式表
	public void SaveHrExperienceInsideSave_send_2(Object object) throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public List getCountTransByrelation(Object obj) throws Exception;

	/**
	 * 删除已保存的“奖励”
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-27 下午11:51:32 
	* @version V1.0
	 */
	public void deleteStoredRewardRecordsByParams(Map paramMap)throws Exception;


	/**
	 * 删除已保存的“惩罚”
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-27 下午11:51:32 
	* @version V1.0
	 */
	public void deleteStoredPunishmentRecordsByParams(Map paramMap)throws Exception;

	/**
	 * 保存“惩罚”记录之前删除上次记录
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-28 上午12:24:01 
	* @version V1.0
	 */
	public void deleteStroedPunishmentRecordsForSave(LinkedHashMap map)throws Exception;

	/**
	 * 查询“奖励”或“惩罚”记录条数
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-28 上午11:02:38 
	* @version V1.0
	 */
	public Integer getRewardOrPunishmentCnt(LinkedHashMap map)throws Exception;

	/**
	 * 根据传入的PARENT_CODE_NO 查询出对应的CODE和NAME
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-21 下午2:23:31 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getCodeList(Object object);
	
	/**
	 * 通过职等 关联职级
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-22 下午1:29:15 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getZhiDengAndZhiJi(Object obj);
	
	/**
	 *   通过职级关联职责
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-22 下午3:45:59 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getZhiJiAndZhiZe(Object obj);
	
	/**
	 * 通过职级关联职级名称
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-22 下午4:06:41 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getZhiJiAndZhiJiMing(Object obj);
	
	/**
	 * 保存学历信息临时表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-26 下午4:41:29 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public void addEduactionInfo(Object object) throws Exception;
	
	/**
	 * 修改员工个人信息临时表最终学历信息	
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-26 下午4:51:53 
	* @version V1.0
	 */
	public void updataEduaction(Object object) throws Exception;
	//查询普通发令人员
	@SuppressWarnings("unchecked")
	public List getHrExperienceInsideSaveByTransCode(Object obj);
	//查询普通特殊发令人员
	@SuppressWarnings("unchecked")
	public List getHrExperienceInsideByTransCode(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getHrExperienceInsideByTransCodeDefault(Object obj);
	
	//保存时删除旧数据
	public void deleteHrExperienceInsideSaveByPersonIds(Object obj)throws Exception;
	
	//发调令时删除数据
	public void deleteHrExperienceInsideSaveByPersonId(Object obj)throws Exception;
	/**
	 * 查找到title的名字 以及对应 hr_experience_inside表中的字段名称
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-2 下午12:32:54 
	* @version V1.0
	 */
	public List getTranferOrderTitileInside(Object object);
	


	/**
	 *修改版  查找到title的名字 以及对应 hr_experience_inside表中的字段名称
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-2 下午12:32:54 
	* @version V1.0
	 */
	public List getTranferOrderTitileInside1(Object object);
	
	
	
	
	public int getPassHrAffirmCountByExpInsideNo(Object obj) throws Exception;

	/**
	 * 取消兼职发令(cancel HrPlurality)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int cancelHrPluralityByNo(List list) throws Exception;


	
	
	/**
	 * 取出人员详细信息(cancel HrPlurality)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaHrVList(Object object) throws Exception;

	/**
	 * 提交异动发令（save Transfer Order Upgrade）
	 * 
	 * @param obj
	 *            list
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public void saveDiaoDongAffirmList(Object obj, List list) throws Exception;
	/**
	 * 提交异动发令（save Transfer Order Upgrade）
	 * 
	 * @param obj
	 *            list
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public void saveDiaoDongAffirmList1(Object obj, List list) throws Exception;           
	public List getTranferOrderinsideListSerach(Object object)throws Exception;
	/**
	 * 根据ID获得codename
	 * 
	 * @param obj
	 *            list
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public List getSycodeByCodeId(Object obj)throws Exception;
	
	public int getHrExperienceInsideTransNum(Object obj) throws NumberFormatException, SQLException;

	public Integer getRewardAndPunishmentInsideTransNum(Map paramMap)throws Exception;

	public List getSelectedRewardEmpList(LinkedHashMap paramMap)throws Exception;

	
	public List getTranferOrderinsideList(Object object) ;
	public List getTranferOrderinsideList(Object object, int currentPage,
			int pageSize) ;
	public int getTranferOrderinsideListCnt(Object object);
	/**
	 * 根据职责查询对应职级
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getPostGradeNoByDuty(LinkedHashMap paramMap)throws Exception;
	/**
	 * 根据职级获得对应的职级名称NO
	 * @param request 
	 * @return
	 * @throws Exception
	 */
	public List getHrPostGradeByPostGradeNo(LinkedHashMap paramMap)throws Exception;
	/**
	 * 根据职级名称NO获得对应的职级名称
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getPostNoNameByPostsNo(LinkedHashMap paramMap)throws Exception;
	
	/**
	 * 根据职种获得职位
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-16 下午9:36:34 
	* @version V1.0
	 */
	public List getZhiZhongAndZhiWei(Object object)throws Exception;
	/**
	 * 根据ID获得级联
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-9-24 下午9:36:34 
	* @version V1.0
	 */
	public List viewSelectTag(Object object)throws Exception;
	
	/**
	 * delete HrExperienceInsideSave data
	* @Copyright:   LDCC (c)
	* @Company:     LDCC
	* @Description: delete HrExperienceInsideSave data
	* @author jjy
	* @date 2013-9-24 下午9:36:34 
	* @version V1.0
	 */
	public  void deleteHrExperienceInsideSaveByPersonIdNExpInsideNo(Object object)throws Exception;

	public int deleteCurrentReward(LinkedHashMap paramMap)throws Exception;

	public int deleteCurrentPunishment(LinkedHashMap paramMap)throws Exception;
	
	

	public List getHrAffirmByExpinsideNo(Map paramMap) throws Exception;
	public int updateHrAffirmByExpinsideNo(Map paramMap) throws Exception;
	public List getTwoCurrentAffirmIdByExpinsideNo(Map paramMap) throws Exception;
	
	
	public List getEmpInfoByPersonIdAndExinsideNo(Map paramMap) throws Exception;
	
	/* 临时职人员离职数据导入结果 */
	@SuppressWarnings("unchecked")
	public List getTempEmpResignDataImportResultList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getTempEmpResignDataImportResultList(Object object);
	@SuppressWarnings("unchecked")
	public int getTempEmpResignDataImportResultListCnt(Object obj);
	@SuppressWarnings("unchecked")
	public int getTempEmpResignDataImportResultListErrCnt(Object obj);
	@SuppressWarnings("unchecked")
	public String importTempEmpResignDataRAWFromExcel(Map paramMap);	
	
	//临时职调动发令
	@SuppressWarnings("unchecked")
	public List getTempEmpTransferOrderList(Object obj, int currentPage,int pageSize);
	@SuppressWarnings("unchecked")
	public List getTempEmpTransferOrderList(Object obj);
	@SuppressWarnings("unchecked")
	public int getTempEmpTransferOrderListCnt(Object obj);	
	@SuppressWarnings("unchecked")
	public List getTempEmpTransferOrderEditList(Object object);
	@SuppressWarnings("unchecked")
	public List getTempEmpTransferOrderEditList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public int getTempEmpTransferOrderEditListCnt(Object object);
	@SuppressWarnings("unchecked")
	public void saveExperienceForTransfer(Object obj) throws Exception;
	@SuppressWarnings("unchecked")
	public void updateExperienceForTransfer(Object obj) throws Exception;
	@SuppressWarnings("unchecked")
	public void saveExperienceForEmpTypeTransfer(Object obj) throws Exception;
	@SuppressWarnings("unchecked")
	public Map callActiveEmpTypeTransfer(Map paramMap) throws Exception;
	/* 临时职人员发令数据导入结果 */
	@SuppressWarnings("unchecked")
	public List getTransferOrderImpResultList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getTransferOrderImpResultList(Object object);
	@SuppressWarnings("unchecked")
	public int getTransferOrderImpResultListCnt(Object obj);
	@SuppressWarnings("unchecked")
	public int getTransferOrderImpResultListErrCnt(Object obj);
	@SuppressWarnings("unchecked")
	public String importTransferOrderImpRAWFromExcel(Map paramMap);	
	@SuppressWarnings("unchecked")
	public Map deleteTransferOrderUpgrade(Map paramMap) throws Exception;
	@SuppressWarnings("unchecked")
	public int checkDeleteTransferOrderUpgrade(Map paramMap) throws Exception;
	//正规职调动发令
	@SuppressWarnings("unchecked")
	public List getReguEmpTransferOrderList(Object obj, int currentPage,int pageSize);
	@SuppressWarnings("unchecked")
	public List getReguEmpTransferOrderList(Object obj);
	@SuppressWarnings("unchecked")
	public int getReguEmpTransferOrderListCnt(Object obj);	
	@SuppressWarnings("unchecked")
	public List getReguEmpTransferOrderEditList(Object object);
	@SuppressWarnings("unchecked")
	public List getReguEmpTransferOrderEditList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public int getReguEmpTransferOrderEditListCnt(Object object);
	public void addconfirmReqHire(Object obj) throws Exception;
	public void insertAffirmor(Object object)  throws Exception;
	public void delTempEmpInfo(Object object)throws Exception;
	public void delTempEmpInfoPe(Object obj)throws Exception;
	public void delTempEmpInfoPa(Object obj)throws Exception;
	public void delTempEmpInfoExp(Object obj)throws Exception;
	public void addConfirmReqResign(Object obj) throws Exception;
	@SuppressWarnings("unchecked")
	public Map deleteResignation(Map paramMap) throws Exception;
	@SuppressWarnings("unchecked")
	public Map callRevokeResignation(Map paramMap) throws Exception;
	public void addConfirmReqRevokeResign(Object obj) throws Exception;
	@SuppressWarnings("unchecked")
	public void addAttachFiles(Object obj) throws Exception;
	
	@SuppressWarnings("unchecked")
	public String importEmpTypeTransferOrderImpRAWFromExcel(Map paramMap);	
	@SuppressWarnings("unchecked")
	public int getValidTempEmpTypeTrCnt(Object obj);
	@SuppressWarnings("unchecked")
	public int getValidDeptNoCnt(Object obj);
	
	/**
	 * 派遣地发令修改
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateSendAndSendOffNew(LinkedHashMap paramMap) throws Exception;
	@SuppressWarnings("unchecked")
	public int getDelTempTROInvalidStateCnt(Object obj);
	@SuppressWarnings("unchecked")
	public void cancelTransferOrderInBatch(Object obj) throws Exception;
}
