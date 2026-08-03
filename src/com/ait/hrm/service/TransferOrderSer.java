package com.ait.hrm.service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: TransferOrderSer.java
 * @Description:
 * @Create date: 2012-2-23 下午10:32:50
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface TransferOrderSer {
	
	@SuppressWarnings("unchecked")
	public Map getTransferOrderList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public Map getDispatchInfo(HttpServletRequest request);
	
	public int saveTransferOrderHire(HttpServletRequest request);
	
	public Map saveTransferOrderUpgrade(HttpServletRequest request);
	public Map saveEmpTypeTransferOrderUpgrade(HttpServletRequest request);
	
	public Map checkSaveTransferOrderUpgrade(HttpServletRequest request);
	public Map checkSaveEmpTypeTransferOrderUpgrade(HttpServletRequest request);
	public int checkSaveTransferOrderUpgrade1(Object obj);
	public int checkSavePayrise(HttpServletRequest request);
	
	public int checkSavePayriseByStartDate(HttpServletRequest request);
	
	public String checkSaveTransfer(HttpServletRequest request);
	
	public String checkSaveTransferPormote(HttpServletRequest request);
	
	public int checkSaveTransferNormal(HttpServletRequest request);
	
	public int checkSaveTransferPromote(HttpServletRequest request);
	
	public int checkSaveResignation(HttpServletRequest request);
	
	public int getValidationEmpid(HttpServletRequest request);
	
	public int getValidationIdCardNo(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getViewUpgradeList(HttpServletRequest request);
	
	public int getViewUpgradeCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPositionList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getDutyList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPostGradeList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getOldPostGradeList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPostList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getWorkAreaList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPostListByPostGradeNo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getDutyListByPostGradeNo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getWorkAreaByDept(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getSocialSecurityAreaByWorkArea(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getTransferNormalList(HttpServletRequest request);
	
	public int saveTransferNormal(HttpServletRequest request);
	
	public int getTransferNormalCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getTransferPromoteList(HttpServletRequest request);
	
	public int getTransferPromoteCnt(HttpServletRequest request);
	
	public int saveTransferPromote(HttpServletRequest request);
	/**
	 * 查看转正
	 */
	@SuppressWarnings("unchecked")
	public Map getTransferNormalForSearch (HttpServletRequest request);
	/**
	 * 查看转职
	 */
	@SuppressWarnings("unchecked")
	public Map getTransferPostForSearch (HttpServletRequest request);
	/**
	 * 查看晋升
	 */
	@SuppressWarnings("unchecked")
	public Map getTransferPromoteForSearch (HttpServletRequest request);
	/**
	 * 查看担当业务变更
	 */
	@SuppressWarnings("unchecked")
	public Map getTransferActBusinessForSearch (HttpServletRequest request);
	/**
	 * 查看兼职
	 */
	@SuppressWarnings("unchecked")
	public Map getPluralityForSearch (HttpServletRequest request);
	/**
	 * 查看派遣
	 */
	@SuppressWarnings("unchecked")
	public Map getDispatchForSearch (HttpServletRequest request);
	/**
	 * 查看停职
	 */
	@SuppressWarnings("unchecked")
	public Map getSuspendForSearch (HttpServletRequest request);
	/**
	 * 查看奖励
	 */
	@SuppressWarnings("unchecked")
	public Map getHortationForSearch (HttpServletRequest request);
	/**
	 * 查看惩戒
	 */
	@SuppressWarnings("unchecked")
	public Map getPunishMentForSearch (HttpServletRequest request);
	/**
	 * 查看离职
	 */
	@SuppressWarnings("unchecked")
	public Map getResignForSearch (HttpServletRequest request);
	/**
	 * 查看职员调令历史
	 */
	@SuppressWarnings("unchecked")
	public Map searchEmpHistory (HttpServletRequest request);
	/**
	 * 查看职员担当调令历史
	 */
	@SuppressWarnings("unchecked")
	public Map searchEmpHistoryForActBusiness (HttpServletRequest request);
	/**
	 * 调令取消
	 */
	public String rollBackUpgrade(List<LinkedHashMap<String, Object>> list,HttpServletRequest request)throws Exception;
	
	public int getNextEmpid(HttpServletRequest request);
	
	
	/**
	 * 查看离职
	 */
	@SuppressWarnings("unchecked")
	public List getViewResignList(LinkedHashMap paramMap, HttpServletRequest request);
	
	/**
	 * 查看离职数量
	 */
	public int getViewResignCnt(LinkedHashMap paramMap, HttpServletRequest request);
	/**
	 * 查看添加离职
	 */
	public List getViewTempEmpList(LinkedHashMap paramMap, HttpServletRequest request);
	/**
	 * 查看添加离职数量
	 */
	public int getViewTempEmpCnt(LinkedHashMap paramMap, HttpServletRequest request);
	
	/**
	 * 查看修改离职
	 */
	public List getViewResignEditList(LinkedHashMap paramMap, HttpServletRequest request);	
	public List getViewResignEditListAll(LinkedHashMap paramMap, HttpServletRequest request);
	/**
	 * 查看修改离职数量
	 */
	public int getViewResignEditCnt(LinkedHashMap paramMap, HttpServletRequest request);
	
	/**
	 * 保存离职
	 */
	public int saveResignation(HttpServletRequest request);
	
	/**
	 * 查看兼职
	 */
	@SuppressWarnings("unchecked")
	public List getViewPluralityList(HttpServletRequest request);
	
	/**
	 * 查看兼职
	 */
	public int getViewPluralityCnt(HttpServletRequest request);
	
	/**
	 * 检查是否可兼职发令
	 */
	public int checkSavePlurality(HttpServletRequest request);

	/**
	 * 保存兼职
	 */
	public int savePlurality(HttpServletRequest request);
	
	/**
	 * 查看停职
	 */
	@SuppressWarnings("unchecked")
	public List getSuspendList(HttpServletRequest request);
	
	/**
	 * 查看停职
	 */
	public int getSuspendListCnt(HttpServletRequest request);
	
	
	/**
	 * 检查是否可停职发令
	 */
	public int checkSaveSuspend(HttpServletRequest request);
	
	/**
	 * 保存停职
	 */
	public int saveSuspend(HttpServletRequest request);
	
	
	/**
	 * 查看奖励
	 */
	@SuppressWarnings("unchecked")
	public List getRewardList(HttpServletRequest request);
	
	/**
	 * 查看奖励
	 */
	public int getRewardListCnt(HttpServletRequest request);
	
	
	
	/**
	 * 保存奖励
	 */
	public int saveReward(HttpServletRequest request);
	
	
	
	/**
	 * 检查是否可奖励发令
	 */
	public int checkSaveReward(HttpServletRequest request);
	
	
	
	/**
	 * 查看惩戒
	 */
	@SuppressWarnings("unchecked")
	public List getPunishMentList(HttpServletRequest request);
	
	/**
	 * 查看惩戒
	 */
	public int getPunishMentListCnt(HttpServletRequest request);
	
	
	/**
	 * 检查是否可奖励发令
	 */
	public int checkSavePunishMent(HttpServletRequest request);
	
	
	/**
	 * 保存惩戒
	 */
	public int savePunishMent(HttpServletRequest request);
	
	
	/**
	 * 查看薪资调整
	 */
	@SuppressWarnings("unchecked")
	public List getPayriseList(HttpServletRequest request);
	
	/**
	 * 查看薪资调整
	 */
	public int getPayriseListCnt(HttpServletRequest request);
	
	
	/**
	 * 查看惩戒
	 */
	@SuppressWarnings("unchecked")
	public List paBasicItemList(HttpServletRequest request);
	
	
	@SuppressWarnings("unchecked")
	public List getReturnValueByItemNo(HttpServletRequest request);
	
	
	/**
	 * 保存薪资调整
	 */
	public int savePayrise(HttpServletRequest request);
	
	
	
	/**
	 * 查看代理
	 */
	@SuppressWarnings("unchecked")
	public List getAgentList(HttpServletRequest request);
	
	/**
	 * 查看薪代理
	 */
	public int getAgentCnt(HttpServletRequest request);
	
	/**
	 * 检查是否可做代理发令
	 */
	public int checkSaveTransferOrderAgent(HttpServletRequest request);
	
	/**
	 * 检查是否可做代理发令
	 */
	public String checkSaveTransferAgent(HttpServletRequest request);
	
	/**
	 * 代理发令
	 */
	public int saveTransferOrderAgent(HttpServletRequest request);
	
	
	//定时器 start---------------------------------------------------
	
	/**
	 * 查询需要更新到hr_employee表里面的    from hr_employee_temp   (入职发令)
	 */
	@SuppressWarnings("unchecked")
	public List getEmployeeTempList();
	
	
	/**
	 * 修改HR_EXPERIENCE_INSIDE activity=1 	(入职发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateHrEmployeeTemp(Object obj);
	
	
	
	/**
	 * 查询需要更新到hr_personal_info表里面的    from hr_personal_info_temp	(入职发令)
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalInfoTempList();
	
	
	/**
	 * 保存到hr_employee,hr_personal_info(入职发令)
	 */
	@SuppressWarnings("unchecked")
	public void saveEmpAndPerForHireTimers(List empList,List perList);
	
	/**
	 * 查询出调动已生效未更新的数据list  (调动发令)
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getExperienceInsideListForUpgrade();
	
	

	
	/**
	 * 修改HR_EMPLOYEE HR_EXPERIENCE_INSIDE(调动发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateEmpAndExpForUpgradeTimers(Object obj);
	
	
	/**
	 * 查询需要更新到hr_employee表里面的    from HR_PROBATION   (转正发令)
	 */
	@SuppressWarnings("unchecked")
	public List getProbationListForTransferNormal();
	
	
	
	/**
	 * 修改hr_employee END_PROBATION_DATE  STATUS_CODE  EMP_TYPE_CODE (转正发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateHrEmployeeForTN(Object obj);
	
	
	/**
	 * 修改hr_employee  DATE_STARTED(转正发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateHrEmployeeDateStated(Object obj);
	
	/**
	 * 修改hr_probation  activity=1(转正发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateProbationForTransferNormal(Object obj);
	
	
	
	/**
	 * 查询需要更新到hr_employee表里面的    from HR_EXPERIENCE_INSIDE   (晋升降职发令)
	 */
	@SuppressWarnings("unchecked")
	public List getExperienceInsideListForTransferPromote();
	
	
	/**
	 * 获取兼职发令列表 (兼职、取消兼职发令)
	 */
	@SuppressWarnings("unchecked")
	public List getPluralityList();
	
	/**
	 * 获取取消兼职发令列表 (兼职、取消兼职发令)
	 */
	@SuppressWarnings("unchecked")
	public List PluralityListForCancle();
	
	
	/**
	 * 修改hr_plurality  activity(兼职发令)
	 */
	@SuppressWarnings("unchecked")
	public void updatePluralityForTimer(Object obj);
	
	
	/**
	 * 修改hr_plurality  activity(取消兼职发令)
	 */
	@SuppressWarnings("unchecked")
	public void updatePluralityCancleForTimer(Object obj);
	
	
	/**
	 * 获取停职发令列表 (停职发令)
	 */
	@SuppressWarnings("unchecked")
	public List getSuspendListForTimer();
	
	
	/**
	 * 根据personId获取cpnyId(停职发令)
	 */
	@SuppressWarnings("unchecked")
	public String getCpnyIdByPersonId(Object obj);

	
	
	/**
	 * (停职发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateDateForSuspendTimers(Object obj);
	
	/**
	 * (复职发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateDateForReinstatedTimers(Object obj);
	
	
	/**
	 * 获取复职发令列表 (复职发令)
	 */
	@SuppressWarnings("unchecked")
	public List getReinstatedListForTimer();

	
	/**
	 * 获取奖励发令列表 (奖励发令)
	 */
	@SuppressWarnings("unchecked")
	public List getRewardListForTimer();
	
	
	/**
	 * 修改HrReward  (奖励发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateHrRewardForTimers(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getOrderParmList(HttpServletRequest request,String sortNameNo) throws Exception ;
	
	/**
	 * 获取惩戒发令列表 (惩戒发令)
	 */
	@SuppressWarnings("unchecked")
	public List getPunishmentListForTimer();
	
	
	
	/**
	 * 修改HrPunishment  (惩戒发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateHrPunishmentForTimers(Object obj);
	
	
	
	

	/**
	 * 获取离职发令列表 (离职发令)
	 */
	@SuppressWarnings("unchecked")
	public List getResignListForTimer();
	
	
	
	/**
	 * 修改HrResign  (惩戒发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateHrResignForTimers(Object obj);
	
	
	
	/**
	 * 获取薪资调整发令列表 (薪资调整发令)
	 */
	@SuppressWarnings("unchecked")
	public List getPayriseListForTimer();
	
	
	/**
	 * 修改HrPayrise  (薪资调整发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateHrPayriseForTimers(Object obj);
	
	
	
	/**
	 * 获取代理发令列表 (代理发令)
	 */
	@SuppressWarnings("unchecked")
	public List getAgentListForTimer();
	
	
	
	/**
	 * 修改HrPayrise  (薪资调整发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateHremployeeForTimers(Object obj);
	
	
	
	/**
	 * 获取取消代理发令列表 (取消代理发令)
	 */
	@SuppressWarnings("unchecked")
	public List getCancleAgentListForTimer();
	
	
	
	/**
	 * 修改hr——employee(取消代理发令)
	 */
	@SuppressWarnings("unchecked")
	public void updateHremployeeForCancleAgentTimers(Object obj);
	
	//定时器 end--------------------------------------------------------
	
	
	
	
	
	
	
	/**
	 * 搜索人员
	 */
	@SuppressWarnings("unchecked")
	public List getEmpSearchList(HttpServletRequest request);
	
	/**
	 * 搜索人员
	 */
	public int getEmpSearchCnt(HttpServletRequest request);
	
	
	@SuppressWarnings("unchecked")
	public List getEidListForSearch(HttpServletRequest request);
	
	
	/**
	 * 检查该人员在此生效日期 是否可以做调动发令
	 */
	public String checkPersonId(HttpServletRequest request);
	

	@SuppressWarnings("unchecked")
	public int checkedIdcardNoReqStatus(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public int checkIdcardNo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int checkedPassportNo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List checkIdcardNoAgains(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getHaoFengLists(HttpServletRequest request);
	
	
	/**
	 * 获取号奉发令列表(号奉发令)
	 */
	@SuppressWarnings("unchecked")
	public List getPayStepList(HttpServletRequest request);
	
	/**
	 * 获取号奉发令列表(号奉发令)
	 */
	public int getPayStepListCnt(HttpServletRequest request);
	

	@SuppressWarnings("unchecked")
	public int  getParamInfoValue(HttpServletRequest request);

	/**
	 * 保存号俸发令
	 */
	public int savePayStep(HttpServletRequest request);

	public List getHaoFengList(HttpServletRequest request);

	public List getOldPostGradeList2(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public int getPopMarkFlag(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getSaParamItemParamList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getBnParamItemParamList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getInParamItemParamList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemParamList(HttpServletRequest request);
	
	/**
	 *  保存工资保险奖金数据
	 */
	public int saveSaBnIn(HttpServletRequest request);
	
	

	public int checkSavePayStep(HttpServletRequest request);
	
	public String haveEalierPayStepData(HttpServletRequest request);

	public List getExperienceInsideListForPayStep();

	public void savePayStepForTimer(Object object);


	
	@SuppressWarnings("unchecked")
	/**
	 * 根据职级参数   查询对应的职等
	 */
	public List getGradeLevelNoByPostGradeNoList(HttpServletRequest request);

	public List getRecSourceList(HttpServletRequest request);

	public List getRecSourceDetailByRecSource(HttpServletRequest request);

	public List getRecSourceListForUpdate(HttpServletRequest request);

	public List getTransList(LinkedHashMap paramMap, HttpServletRequest request);

	public List getBLACKLISTList(LinkedHashMap paramMap, HttpServletRequest request);

	public List getHrEmployeeList(HttpServletRequest request);
	public List getTranferOrderTitile(HttpServletRequest request);
	public List getHrExperienceInsideByPersonId(HttpServletRequest request);
	
	
	public List getEmpIdList(HttpServletRequest request) ;
	public int getEmpIdListCnt(HttpServletRequest request);


	
	public Map submitAddHrDispatch(HttpServletRequest request) throws Exception;
	
	public Map updatesubmitSendAndSendOff(HttpServletRequest request) throws Exception;
	
	//派遣地
	public List getHrDispatch(HttpServletRequest request) throws Exception;
	
	//派遣地
	public List getHrDispatchUpdate(HttpServletRequest request) throws Exception;
	
	public List getHrDispatch2(HttpServletRequest request) throws Exception;
	
	public List getHrDispatch3(HttpServletRequest request) throws Exception;
	
	public int getHrDispatchCnt(HttpServletRequest request) throws Exception;
	public int getHrDispatchUpdateCnt(HttpServletRequest request) throws Exception;
	
	//调令发令
	public int SaveHrExperienceInside(HttpServletRequest request) throws Exception;
	//调令保存
	public int SaveHrExperienceInsideSave(HttpServletRequest request) throws Exception;
	//调令保存正式表
	public int SaveHrExperienceInsideSave1(HttpServletRequest request) throws Exception;

	
	//调令保存临时表
	public int SaveHrExperienceInsideSave_send_1(HttpServletRequest request) throws Exception;
	//调令保存正式表
	public int SaveHrExperienceInsideSave_send_2(HttpServletRequest request) throws Exception;
	

	/**
	 * 根据输入的员工编号查询员工
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEmployeeByEmpId(HttpServletRequest request)throws Exception;

	@SuppressWarnings("unchecked")
	public List getTransferOrderEmpList(HttpServletRequest request)throws Exception ;

	public int getTransferOrderEmpListCnt(HttpServletRequest request) throws Exception;

	/**
	 * 保存“奖励”发令信息(临时储存)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int storeReward(HttpServletRequest request)throws Exception;

	/**
	 * 保存"惩罚"发令信息(临时储存)
	 * @param request
	 * @return
	 */
	public int storePunishment(HttpServletRequest request);

	/**
	 * 查询"奖励"保存过的记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getStoredRewardList(HttpServletRequest request)throws Exception;

	/**
	 * 查询"惩罚"保存过的记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getStoredPunishmentList(HttpServletRequest request)throws Exception;
	
	
	/**
	 * 根据传入的PARENT_CODE_NO 查询出对应的CODE和NAME
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-21 下午2:19:09 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getCodeList(String str, HttpServletRequest request);
	
	/**
	 * 根据传入的PARENT_CODE_NO 查询出对应的CODE和NAME
	 * */
	@SuppressWarnings("unchecked")
	public List getCodeListByParam(LinkedHashMap paramMap);

	/**
	 * 通过职等 关联职级
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-22 上午11:57:36 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getZhiDengAndZhiJi(HttpServletRequest request);
	
	/**
	 * 通过职级关联职责
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-22 下午3:42:20 
	* @version V1.0
	 */
	public List getZhiJiAndZhiZe(HttpServletRequest request);

	/**
	 * 通过职级关联职级名称
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-8-22 下午4:05:48 
	* @version V1.0
	 */
	public List getZhiJiAndZhiJiMing(HttpServletRequest request);
	public List getHrExperienceInsideSaveByTransCode(HttpServletRequest request);
	
	/**
	 * 通过personId 删除临时表的调令
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-9-01 下午4:05:48 
	* @version V1.0
	 * @throws Exception 
	 */
	public int deleteHrExperienceInsideSaveByPersonIds(HttpServletRequest request) throws Exception;

	/**
	 * 根据调令类型 查看相应的调令内容
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-2 下午6:39:34 
	* @version V1.0
	 */
	public List viewTranferOrderinsideList(HttpServletRequest request) throws Exception;
	/**
	 * 根据调令类型 查看相应的调令内容
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-9-5 下午3:39:34 
	* @version V1.0
	 */
	public List viewTranferOrderinsideListSerach(HttpServletRequest request) throws Exception;
	/**
	 * 根据调令类型 查看相应的调令内容 分页
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-2 下午7:15:35 
	* @version V1.0
	 */
	public int getTranferOrderinsideListCnt(HttpServletRequest request,
			LinkedHashMap paramMap1);
	/**
	 * 批量取消发令
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-2 下午8:54:45 
	* @version V1.0
	 */
	public int cancelPluralityBatchInsideByNo(HttpServletRequest request) throws Exception;
	
	/**
	 * 取出当前最大调令编号
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-9-10 上午2:00:45 
	* @version V1.0
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	public int getHrExperienceInsideTrans_Num(HttpServletRequest request) throws NumberFormatException, SQLException;

	/**
	 * 取出当前最大调令编号(奖惩)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-10 下午10:30:43 
	* @version V1.0
	 */
	public Integer getRewardAndPunishmentInsideTransNum(HttpServletRequest request)throws Exception;

	/**
	 * 根据选择的员工编号查询指定员工
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSelectedEmpList(HttpServletRequest request)throws Exception;
	
	/**
	 * 根据职责查询对应职级
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPostGradeNoByDuty(HttpServletRequest request)throws Exception;
	/**
	 * 根据职级获得职级名称
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPositionInfoByPostGradeNo(HttpServletRequest request)throws Exception;

	/**
	 * 根据职种获得职位
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-16 下午9:28:48 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getZhiZhongAndZhiWei(HttpServletRequest request)throws Exception;;

	/**
	 * 根据ID获得级联
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-9-24下午8:45:15 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List viewSelectTag(HttpServletRequest request)throws Exception;;
	
	/**
	 * 根据ID获得级联
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-9-24下午8:45:15 
	* @version V1.0
	 */
	public int deleteHrExperienceInsideSaveByPersonIdNExpInsideNo(HttpServletRequest request) throws Exception;

	/**
	 * 删除奖励或惩戒的一行数据
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-29 下午04:12:20 
	* @version V1.0
	 */
	public int deleteCurrentRewardOrPunishment(HttpServletRequest request)throws Exception;
	

	/**
	 * 查询人员
	 */
	@SuppressWarnings("unchecked")
	public List viewEmpinfoList(HttpServletRequest request)throws Exception;
	
	//临时职人员离职数据导入结果
	@SuppressWarnings("unchecked")
	public List getTempEmpResignDataImportResultList(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public List getTempEmpResignDataImportResultListExcel(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getTempEmpResignDataImportResultListCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getTempEmpResignDataImportResultListErrCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public String importTempEmpResignDataRAWFromExcel(HttpServletRequest request, Map paramMap);	
	
	//临时职调动发令
	@SuppressWarnings("unchecked")
	public List getTempEmpTransferOrderList(Map paramMap,HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int getTempEmpTransferOrderListCnt(Map paramMap,HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getTempEmpTransferOrderEditList(LinkedHashMap paramMap, HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int getTempEmpTransferOrderEditListCnt(LinkedHashMap paramMap, HttpServletRequest request);	
	//临时职人员发令数据导入结果
	@SuppressWarnings("unchecked")
	public List getTransferOrderImpResultList(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public List getTransferOrderImpResultListExcel(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getTransferOrderImpResultListCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getTransferOrderImpResultListErrCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public String importTransferOrderImpRAWFromExcel(HttpServletRequest request, Map paramMap);	
	@SuppressWarnings("unchecked")
	public Map deleteTransferOrderUpgrade(Map paramMap);
	
	//正规职调动发令
	@SuppressWarnings("unchecked")
	public List getReguEmpTransferOrderList(Map paramMap,HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int getReguEmpTransferOrderListCnt(Map paramMap,HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getReguEmpTransferOrderEditList(LinkedHashMap paramMap, HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int getReguEmpTransferOrderEditListCnt(LinkedHashMap paramMap, HttpServletRequest request);

	public Map confirmReqHire(HttpServletRequest request) ;
	public Map delTempEmpInfo(HttpServletRequest request);
	/**
	 * 离职发令申请
	 */
	public Map confirmResignation(HttpServletRequest request);
	public List getAffirmorList(String APPLY_TYPE_NO, HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public Map deleteResignation(Map paramMap);

	public Map confirmTempEmpBatch(HttpServletRequest request) ;

	/**
	 * 撤销离职发令申请
	 */
	@SuppressWarnings("unchecked")
	public Map confirmRevokeResignation(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public String importEmpTypeTransferOrderImpRAWFromExcel(HttpServletRequest request, Map paramMap);
	
	/**
	 * 派遣地发令修改
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateSendAndSendOffNew(HttpServletRequest request);
	/**
	 * 撤销临时职发令申请
	 */
	@SuppressWarnings("unchecked")
	public Map cancelTransferOrderInBatch(HttpServletRequest request);
	/**
	 * 撤销正规职发令申请
	 */
	@SuppressWarnings("unchecked")
	public Map cancelReguEmpTransferOrder(HttpServletRequest request);
}
