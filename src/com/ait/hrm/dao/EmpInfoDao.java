package com.ait.hrm.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: EmpInfoDao.java
 * @Create date: Jan 16, 2012 3:36:16 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
@SuppressWarnings("unchecked")
public interface EmpInfoDao {
	public List getEmpList(Object object);

	public int getEmpCnt(Object object);

	public Object getBasicInfo(Object object);

	public int getContractCnt(Object object);

	public Map getPaEmpInfoForGrid(Object object, int skipResults,int maxResults);

	public Map getSinfoForGrid(Object object, int skipResults, int maxResults);

	public Map getBizlistForGrid(Object object, int skipResults, int maxResults);

	public Map getAppendInfoForGrid(Object object, int skipResults,int maxResults);

	public Map getResignationForGrid(Object object, int skipResults,int maxResults);

	public Map getLanuageInfoForGrid(Object object, int skipResults,int maxResults);

	public Map getITLevelInfoForGrid(Object object, int skipResults,int maxResults);

	public Map getQualificationInfoForGrid(Object object, int skipResults,int maxResults);

	public Map getEvaluateForGrid(Object object, int skipResults, int maxResults);

	public Map getEvalForGrid(Object object, int skipResults, int maxResults);

	public Map getHealthInfoForGrid(Object object, int skipResults,int maxResults);

	public Map getExpInsideForGrid(Object object, int skipResults,int maxResults);

	public Map getFamilyInfoForGrid(Object object, int skipResults,int maxResults);

	public Map getSocietyRelationForGrid(Object object, int skipResults,int maxResults);

	public Map getPunishMentForGrid(Object object, int skipResults,int maxResults);

	public Map getRewardForGrid(Object object, int skipResults, int maxResults);

	public Map getEduForGrid(Object object, int skipResults, int maxResults);

	public Map getSuspendForGrid(Object object, int skipResults, int maxResults);

	public Map getPluralityForGrid(Object object, int skipResults,int maxResults);

	public Map getDispatchForGrid(Object object, int skipResults, int maxResults);

	public Map getExperienceInfoForGrid(Object object, int skipResults,int maxResults);

	public Map getSysCodeForSelect(Object object);
	/**
	 * 更新家庭关系
	 * 
	 * @param object
	 */
	public void updateFamilyInfo(Object object) throws Exception;

	public void addSocietyRelation(Object object) throws Exception;

	public void updateSocietyRelation(Object object) throws Exception;

	public void deleteSocietyRelation(Object object) throws Exception;

	public void addExperienceInfo(Object object) throws Exception;

	public void updateExperienceInfo(Object object) throws Exception;

	public void deleteExperienceInfo(Object object) throws Exception;

	public void addFappendInfo(Object object) throws Exception;

	public void updateFappendInfo(Object object) throws Exception;

	public void deleteFappendInfo(Object object) throws Exception;

	public void addHealthInfo(Object object) throws Exception;

	public void updateHealthInfo(Object object) throws Exception;

	public void addLanuageInfo(Object object) throws Exception;

	public void updateLanuageInfo(Object object) throws Exception;

	public void deleteLanuageInfo(Object object) throws Exception;

	public void addPaEmpInfo(Object object) throws Exception;

	public void updatePaEmpInfo(Object object) throws Exception;

	public void deletePaEmpInfo(Object object) throws Exception;

	public void updateQualificationInfo(Object object) throws Exception;

	public void deleteQualificationInfo(Object object) throws Exception;

	public List getPersonalList(Object object);

	public List getPersonalList(Object object, int currentPage, int pageSize);

	public int getPersonalCnt(Object object);

	public Object getPersonalInfo(Object object);

	public Object getPersonalInfoByPid(Object object);
	
	public Object getPersonalInfoByPid2(Object object);
	
	public Object getTitle(Object object);
	
	public Object SinglePersonalInfo(Object object);
	
	public Object getfinaEdu(Object object);
	
	public Object viewEmpInfo(Object object);
	
	public String copyAddress(Object object);
	
	public List codeReason(Object object);
	
	public List queryDepartment(Object object);
	
	public List searchTitlename(Object object);
	
	public List gethrEmergencyAddressList(Object object);
	
	public List viewAddressMattersList(Object object);
	
	public List searchTanchu(Object object);
	
	public List zhiji(Object object);
	
	public List employeeSearchResultsTanchu(Object object);
	
	public String querySearchContent(Object object);
	
	public List searchname(Object object);
	
	public List chengbenzhongxin(Object object);
	
	public List viewFamilyList(Object object);
	
	public List gethrAddressMattersLists(Object object);
	
	public List gethrFamilyList(Object object);
	
	public List getStatisticsBureau(Object object);
	
	public List getHRDaily(Object object);
	
	public List getPostStores(Object object);
	
	public List BirthdayWelfare(Object object);
	
	public List RecruitReport(Object object);
	
	public List RuZhiEmployee(Object object);
	
	public List LiZhiEmployee(Object object);
	
	public List RuZhiStatistic(Object object);
	
	public List LiZhiStatistic(Object object);
	
	public List getLaborDispatch(Object object);
	
	public List getResident(Object object);
	
	public List getWorkArea(Object object);
	
	public List getCaiWu(Object object);
	
	public List getWorkPerson(Object object);
	
	public List getShangYe(Object object);
	
	public List getCheJian(Object object);
	
	public List getIndividualIncomeTax(Object object);
	
	public List getCount(Object object);
	
	public List getCountWages(Object object);
	
	public List getCOntractDaoqi(Object object);
	
	public List getPersonalInfo1(Object object);
	
	public List getBanGongShi(Object object);
	
	public List getTC(Object object);
	
	public List getDongyuanDian(Object object);
	
	public List getNinghaiDian(Object object);
	
	public List getShiguDian(Object object);
	
	public List getHongYueCheng(Object object);
	
	public List getLongJiang(Object object);
	
	public List getZhuJiang(Object object);
	
	public List getHuNan(Object object);
	
	public List getHuDengFang(Object object);
	
	public List getQingJiang(Object object);
	
	public List getWanDa(Object object);
	
	public List getBingRunHui(Object object);
	
	public List getCenterShopping(Object object);
	
	public List getJingFeng(Object object);
	
	public List getSunCity(Object object);
	
	public List getMaoYe(Object object);
	
	public List getHuanQiuGang(Object object);
	
	public List getNewCentury(Object object);
	
	public List getChangFa(Object object);
	
	public List getBaoLong(Object object);
	
	public List getJiuZhou(Object object);
	
	public List getCStatistics(Object object);
	
	public List getLastWeek(Object object);
	
	public List XingbXuelMinz(Object object);
	
	public List AgeStatus(Object object);
	
	public List WorkStatus(Object object);
	
	public List getJishiDingban(Object object);
	
	public List getCZJishiDingban(Object object);
	
	public List getHuKou(Object object);
	
	public List getStartPointList(Object object);
	
	public List getStartPointList1(Object object);
	
	public List getExperiencePointList(Object object);
	
	public List viewEducationMatter(Object object); 
	
	public List viewBidMatter(Object object);
	
	public List getAccountInfo(Object object);
	
	public List viewEvaluateInfo(Object object);
	
	public List viewForeignLanguage(Object object);
	
	public List viewTrainingBasic(Object object);
	
	public List viewRecognition(Object object);
	
	public List viewPunishment(Object object);
	
	public List viewSingleTrain(Object object);
	
	public List viewTrain(Object object);
	
	public List viewEvaInformation(Object object);
	
	public List viewSpecialMatter(Object object);
	
	public List viewPassportPerson(Object object);
	
	public List viewEmployeePhoto(Object object);
	
	public List viewEmployeePhotoInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public int deleteEmployeePhotoInfo(Object object) throws Exception;
	
	public String querydeptno(Object object);
	
	public List emergencyAddress(Object object);
	
	public List familySearch(Object object);
	
	public List experienceSearch(Object object);
	
	public List educationSearch(Object object);
	
	public List bidSearch(Object object);
	
	public List gradeSearch(Object object);
	
	public List addressSearch(Object object);
	
	public List recognitionSearch(Object object);
	
	public List punishmentSearch(Object object);
	
	public List retireSearch(Object object);
	
	public List viewPassportPersonMain(Object object);
	
	public Object viewHrPersonalInfo(Object object);
	
	/**
	 * 获取Supervisor履历批量信息
	 */
	public List viewSupervisorInfoList(Object object);
	
	public Object viewSingleHrEmergencyAddress(Object object);
	
	public Object viewSingleAddressMatters(Object object);
	
	public Object viewSingleFamily(Object object);
	
	public Object viewSingleExperiencePoint(Object object);
	
	public Object viewSingleEducationMatter(Object object);
	
	public Object viewSingleBidMatter(Object object);
	
	public Object viewSingleEvaluateInfo(Object object);
	
	public Object viewSingleTrainingBasic(Object object);
	
	public List viewSingleForeignLanguage(Object object);
	
	
	public Object viewSingleForeignLanguage1(Object object);
	
	public Object viewSingleRecognition(Object object);
	
	public Object viewSinglePunishment(Object object);
	
	public Object viewSingleSpecialMatter(Object object);
	
	public Object viewSinglePassportPerson(Object object);
	
	public Object informationSearchMain(Object object);
	
	public Object viewSingleStartPoint(Object object);
	
	public Object addStartPointEmployee(Object object);
	
	public Object querydepartNo(Object object);
	
	public List querydepartPrep(Object object);
	
	public Object querydepartNo_prep(Object object);
	
	public Object viewPrepdepart(Object object);
	
	public int gethrEmergencyAddressList_count(Object object);
	
	public int gethrviewAddressMattersList_count(Object object);
	
	public int getStartPointList_count(Object object);
	
	public int getExperiencePointList_count(Object object);
	
	public int viewBidMatter_count(Object object);
	
	public int viewEvaluateInfo_count(Object object);
	
	public int viewForeignLanguage_count(Object object);
	
	public int viewTrainingBasic_count(Object object);
	
	public int viewRecognition_count(Object object);
	
	public int viewPunishment_count(Object object);
	
	public int viewSpecialMatter_count(Object object);
	
	public int viewPassportPerson_count(Object object);
	
	public int viewEducationMatter_count(Object object);
	
	public int getviewFamilyList_count(Object object);
	
	public Object viewEmergencyAddress(Object object);

	public List getEducationList(Object object);

	public List getEducationList(Object object, int currentPage, int pageSize);

	public void addEduactionInfo(Object object) throws Exception;

	public void deleteEduactionInfo(Object object) throws Exception;

	public void editEduPerInfo(Object object) throws Exception;
	
	public void editEmpInfo(Object object) throws Exception;
	
	public void editEmpInfo_TSTO(Object object) throws Exception;
	
	public void editHrPersonInfo(Object object) throws Exception;
	
	public void updateHrEmployee(Object object) throws Exception;
	
	public void deleteHrEmergencyAddress(Object object) throws Exception;
	
	public void deleteHrAddressMatters(Object object) throws Exception;
	
	public void deleteHrFamily(Object object) throws Exception;
	
	public void deleteStartPoint(Object object) throws Exception;
	
	public void updateActivity(Object object) throws Exception;
	
	public Object queryMaxCreateDate(Object object) throws Exception;
	
	public void deleteExperiencePoint(Object object) throws Exception;
	
	public void deleteEducationMatter(Object object) throws Exception;
	
	public void deleteBidMatter(Object object) throws Exception;
	
	public void deleteEvaluateInfo(Object object) throws Exception;
	
	public void deleteTrainingBasic(Object object) throws Exception;
	
	public void deleteForeignLanguage(Object object) throws Exception;
	
	public void deleteRecognition(Object object) throws Exception;
	
	public void deletePunishment(Object object) throws Exception;
	
	public void deleteSpecialMatter(Object object) throws Exception;
	
	public void deletePassportPerson(Object object) throws Exception;
	
	public void deleteTitlename(Object object) throws Exception;
	
	public void deleteSearchname(Object object) throws Exception;
	
	public void editHrEmergencyAddress(Object object) throws Exception;
	
	public void editHrAddressMatters(Object object) throws Exception;
	
	public void editHrEMPMatters(Object object) throws Exception;
	
	public void insertResults(Object object) throws Exception;
	
	public void insertResultsName(Object object) throws Exception;
	
	public void editHrFamily(Object object) throws Exception;
	
	public void editExperiencePoint(Object object) throws Exception;
	
	public void editEducationMatter(Object object) throws Exception;
	
	public void editRecognition(Object object) throws Exception;
	
	public void editPunishment(Object object) throws Exception;
	
	public void editSpecialMatter(Object object) throws Exception;
	
	public void editPassportPerson(Object object) throws Exception;
	
	public void editBidMatter(Object object) throws Exception;
	
	public void editEvaluateInfo(Object object) throws Exception;
	
	public void editTrainingBasic(Object object) throws Exception;
	
	public void editForeignLanguage(Object object) throws Exception;
	
	public void updatePersonInfoPhoto(Object object) throws Exception;
	
	public void updateEssFile(Object object) throws Exception;
	
	public void editStartPoint(Object object) throws Exception;
	
	public void updateFuzhiDate(Object object) throws Exception;
	
	public void updateSyJieshuDate(Object object) throws Exception;
	
	public void updateXiuzhiLinkTransNo(Object object) throws Exception;
	
	public void updateSyLinkTransNo(Object object) throws Exception;
	
	public void updateBeforeActivity(Object object) throws Exception;
	
	public void updateEmp_Exp(Object object) throws Exception;
	
	public void updateMainBus(Object object) throws Exception;
	
	public void updateLizhi(Object object) throws Exception;
	
	public void updateLizhiOrg(Object object) throws Exception;
	
	public void updateXiuzhi(Object object) throws Exception;
	
	public void updateWaibao(Object object) throws Exception;
	
	public void updateShiyongjieshu(Object object) throws Exception;
	
	public void updatexiuzhi(Object object) throws Exception;
	
	public void updatefuzhi(Object object) throws Exception;
	
	public void updateShiyongyanchang(Object object) throws Exception;
	
	public void zaipin(Object object) throws Exception;
	
	public void zhuanzheng(Object object) throws Exception;
	
	public void updateGongzuodi(Object object) throws Exception;
	
	public void updateBiangeng(Object object) throws Exception;
	
	public void updatejinsheng(Object object) throws Exception;
	
	public void updatejinshengOrg(Object object) throws Exception;
	
	public void updateZhizebiangeng(Object object) throws Exception;
	
	public void updateRenzhi(Object object) throws Exception;
	
	public void updateZhijitixi(Object object) throws Exception;
	
	public void updatehrDepartRenzhi(Object object) throws Exception;
	
	public void updatehrDepartjianzhi(Object object) throws Exception;
	
	public void updatehrDepartJianzhijiechu(Object object) throws Exception;
	
	public void updateAllJianzhijiechu(Object object) throws Exception;
	
	public void togetherFuzhi(Object object) throws Exception;
	
	public void togethershiyongjieshu(Object object) throws Exception;
	
	public void updatehrDepartRenzhijiechu(Object object) throws Exception;
	
	public void updateLinkExpInsideNokong(Object object) throws Exception;
	
	public void updateEmp_Exp_Activity(Object object) throws Exception;
	
	public void updateBumen(Object object) throws Exception;
	
	public void updateGongzuodiyidong(Object object) throws Exception;
	
	public void updateJinsheng(Object object) throws Exception;
	
	public void updateZhiqun(Object object) throws Exception;
	
	public void updateZhize(Object object) throws Exception;
	
	public void updateOther(Object object) throws Exception;
	
	public void updateEmpAll(Object object) throws Exception;
	
	public void updateArDetailTsto(Object object) throws Exception;
	
	public void updateArDetailSst(Object object) throws Exception;
	
	public void insertHrEmergencyAddress(Object object) throws Exception;
	
	public void tiquziliao(Object object) throws Exception;
	
	public void tiquziliao_new(Object object) throws Exception;
	
	public void insertHr(Object object) throws Exception;
	
	public void insertHrAddressMatters(Object object) throws Exception;
	
	public void insertHrFamily(Object object) throws Exception;
	
	public void insertExperiencePoint(Object object) throws Exception;
	
	public void insertEducationMatter(Object object) throws Exception;
	
	public void insertRecognition(Object object) throws Exception;
	
	public void insertPunishment(Object object) throws Exception;
	
	public void insertSpecialMatter(Object object) throws Exception;
	
	public void insertPassportPerson(Object object) throws Exception;
	
	public void insertBidMatter(Object object) throws Exception;
	
	public void insertEvaluateInfo(Object object) throws Exception;
	
	public void insertTrainingBasic(Object object) throws Exception;
	
	public void insertForeignLanguage(Object object) throws Exception;
	
	public void deleteEssFile(Object object) throws Exception;
	
	public void deletePhotoEssFile(Object object) throws Exception;
	
	public void insertEssFile(Object object) throws Exception;
	
	public void insertStartPoint(Object object) throws Exception;
	
	public void updatePositivePointActivity(Object object) throws Exception;
	
	public void insertSyUserRelation(Object object) throws Exception;
	
	public void deleteSyUserRelation(Object object) throws Exception;
	
	public void deleteYufaling(Object object) throws Exception;
	
	public void updatePrepManagerEmpId(Object object) throws Exception;
	
	public void updateManagerEmpId(Object object) throws Exception;
	
	public void leftKaoQin(Object object) throws Exception;
	
	public void falingcunchu(Object object) throws Exception;
	
	public void falingcunchuRecognition(Object object) throws Exception;
	
	public String queryExpInsideNo(Object object) throws Exception;
	
	public String queryEducNo(Object object) throws Exception;
	
	public String queryUserNo(Object object) throws Exception;
	
	public String queryCountHeader(Object object) throws Exception;
	
	public String queryJianHeader(Object object) throws Exception;
	
	public String queryprepmanagerempid(Object object) throws Exception;
	
	public String queryRoleGroupNo(Object object) throws Exception;
	
	public String querySyUserRelation(Object object) throws Exception;
	
	public String queryIsNotManager(Object object) throws Exception;
	
	public String querymaxStartDate(Object object) throws Exception;
	
	public Object querymaxMap(Object object) throws Exception;
	
	public String querySearchNo(Object object) throws Exception;
	
	public String queryLanguageNo(Object object) throws Exception;
	
	public String queryQualNo(Object object) throws Exception;
	
	public String queryEvaluateNo(Object object) throws Exception;
	
	public String queryTrainNo(Object object) throws Exception;
	
	public void insertHrExpInsideHistory(Object object) throws Exception;

	public void editEducation(Object object) throws Exception;

	public List getExpInsideList(Object object);
	
	public List queryYuFaling(Object object);

	public List getExpInsideList(Object object, int currentPage, int pageSize);

	public List getAssignmentList(Object object);

	public List getAssignmentList(Object object, int currentPage, int pageSize);

	public List getResignationInfo(Object object);

	public List getResignationInfo(Object object, int currentPage, int pageSize);

	public List getTradeunionList(Object object);

	public List getTradeunionList(Object object, int currentPage, int pageSize);

	public List getEvsInfoList(Object object);

	public List getEvsInfoList(Object object, int currentPage, int pageSize);

	public void addEvsInfo(Object object) throws Exception;

	public Object getEvsInfo(Object object);

	public void editEvsInfo(Object object) throws Exception;

	public void deleteEvsInfo(Object object) throws Exception;

	public List getReward(Object object);

	public List getReward(Object object, int currentPage, int pageSize);

	public List getPunishment(Object object);

	public List getPunishment(Object object, int currentPage, int pageSize);

	public List getPluralityList(Object object);

	public List getPluralityList(Object object, int currentPage, int pageSize);

	public List getTrainingInfoList(Object object);

	public List getTrainingInfoList(Object object, int currentPage, int pageSize);

	public void addTrainingInfo(Object object) throws Exception;

	public void editTrainingInfo(Object object) throws Exception;

	public void deleteTrainingInfo(Object object) throws Exception;

	public List getFamilyList(Object object);

	public List getFamilyList(Object object, int currentPage, int pageSize);

	public void addFamilyInfo(Object object) throws Exception;

	public void deleteFamilyInfo(Object object) throws Exception;

	public void editFamilyInfo(Object object) throws Exception;

	public List getHomeRelationList(Object object);
	
	public void addHomeRelationInfo(Object object) throws Exception;

	public void deleteHomeRelationInfo(Object object) throws Exception;

	public int editHomeRelation(Object object) throws Exception;

	public List getHealthList(Object object);

	public List getHealthList(Object object, int currentPage, int pageSize);

	public void editHealthInfo(Object object) throws Exception;

	public void deleteHealthInfo(Object object) throws Exception;

	public List getWorkExperienceList(Object object);

	public List getWorkExperienceList(Object object, int currentPage,int pageSize);

	public void addWorkExperienceInfo(Object object) throws Exception;

	public void editWorkExperienceInfo(Object object) throws Exception;

	public void deleteWorkExpreienceInfo(Object object) throws Exception;

	public List getQualificationList(Object object);

	public List getQualificationList(Object object, int currentPage,int pageSize);

	public List getLanguageLevelList(Object object);

	public List getLanguageLevelList(Object object, int currentPage,int pageSize);

	public void editLanguageLevelInfo(Object object) throws Exception;

	public void addQualificationInfo(Object object) throws Exception;

	public void editQualificationInfo(Object object) throws Exception;

	public void addLanguageLevelInfo(Object object) throws Exception;

	public void deleteQualicationInfo(Object object) throws Exception;

	public void deleteLanguageLevelInfo(Object object) throws Exception;

	public List getCodeList(Object object);
	
	public List getCodeListNO(Object object);
	
	public List getCodeList1(Object object);
	
	public List getCodeListRecruitType(Object object);
	
	public List getCodeListEmpStatus(Object object);

	public List getAdditionalList(Object object);

	public List getAdditionalList(Object object, int currentPage, int pageSize);

	public void addAdditionalInfo(Object object) throws Exception;

	public void editAdditionalInfo(Object object) throws Exception;

	public void deleteAdditionalInfo(Object object) throws Exception;

	public List getAccountList(Object object);

	public List getAccountList(Object object, int currentPage, int pageSize);

	public List getContractList(Object object);

	public List getContractList(Object object, int currentPage, int pageSize);

	public List getFileList(Object object);

	public List getFileList(Object object, int currentPage, int pageSize);

	public void addFileInfo(Object object) throws Exception;

	public void editFileInfo(Object object) throws Exception;

	public void deleteFileInfo(Object object) throws Exception;

	public List getGoAbroadList(Object object);

	public List getGoAbroadList(Object object, int currentPage, int pageSize);

	public void addGoAbroadInfo(Object object) throws Exception;

	public void editGoAbroadInfo(Object object) throws Exception;

	public void deleteGoAbroadInfo(Object object) throws Exception;

	public List getCredentialList(Object object);

	public List getCredentialList(Object object, int currentPage, int pageSize);

	public void addCredentialInfo(Object object) throws Exception;

	public void editCredentialInfo(Object object) throws Exception;

	public void deleteCredentialInfo(Object object) throws Exception;

	public List getEmpIdList(Object object);

	public List getEmpIdList(Object object, int currentPage, int pageSize);

	public int getEmpIdListCnt(Object object);

	public int getPersonCnt(Object object);

	public List getPidEidList(Object object);
	
	public List getPidEidListXiao(Object object);
	
	
	public List getPidEidList2(Object object);

	public List getPidEidList(Object object, int currentPage, int pageSize);
	
	public List getPidEidListXiao(Object object, int currentPage, int pageSize);
	
	public List getPidEidList2(Object object, int currentPage, int pageSize);

	public List getOldPostGradeList(Object object, int currentPage, int pageSize);

	public List getPostGradeList(Object object, int currentPage, int pageSize);

	public void editPhotoPath(Object object) throws Exception;

	public List getOrderParmList(Object object);

	public List getHaoFengList(Object object);

	public List getMenuThirdListList(Map paramMap);

	/**
	 * 根据法人获取公司人员信息 导出模板使用
	 * 
	 * @param paramMap
	 * @return
	 */
	public List getEmpListToModelExcel(Map paramMap);

	/**
	 * 查询ess tab菜单
	 * 
	 * @param menu_code
	 * @param request
	 * @return
	 */
	public List getTabMenuListList(Map paramMap);
	/**
	 * 残疾信息
	 * @param paramMap
	 * @return
	 */
	public List getDisabilityinfoList(Object object);
	/**
	 * 残疾信息-分页
	 * @param paramMap
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	public List getDisabilityinfoList(Object object, int currentPage,
			int pageSize);
	/**
	 * 添加残疾信息
	 * @param paramMap
	 */
	public void addDisabledInfo(Object object) throws Exception;
	/**
	 * 删除残疾信息
	 * @param paramMap
	 */
	public void deleteDisabledInfo(Object object) throws Exception;
	/**
	 * 修改残疾信息
	 * @param paramMap
	 */
	public void editDisabledInfo(Object object) throws Exception;
	/**
	 * 添加工会信息(add addTradeunionInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void addTradeunionInfo(Map paramMap) throws Exception;

	/**
	 * 添加工会信息(add deleteTradeunionInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void deleteTradeunionInfo(Object object) throws Exception;
	/**
	 * 添加工会信息(add editTradeunionInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void editTradeunionInfo(Object object) throws Exception;

	public Object getPersonalInfoByPid1(Object object);

	public List getPersonalInfoForInformation(Object ob);

	public List getPersonalInfoForInformation(Object object, int currentPage,
			int pageSize);

	public Object getPersonalInfoForInformationCount(Object object);

	/**
	 * 统计残疾的结束日期是NUll 的数量 >0表示基本信息中 这个人残疾Y
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author liagnwei@ait.net.cn
	 * @date 2013-8-12 上午11:12:49
	 * @version V1.0
	 */
	public int getdisabledCnt(Object object);

	/**
	 * 修改基本中的残疾状态
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author liagnwei@ait.net.cn
	 * @date 2013-8-12 上午11:47:23
	 * @version V1.0
	 */
	public void updateEmployeeDisabled(Object object) throws Exception;

	/**
	 * 增删改工作经历时候修改基本中的司外工作经历时间
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author liagnwei@ait.net.cn
	 * @date 2013-8-12 下午4:56:50
	 * @version V1.0
	 */
	public void updateEmployeeWorkExperience(Object object) throws Exception;
	/**
	 * 统计用户是否有最终学历
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author liagnwei@ait.net.cn
	 * @date 2013-8-13 下午5:02:59
	 * @version V1.0
	 */
	public int getFinalNum(Object object);

	/**
	 * 修改员工个人信息表最终学历信息
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author liagnwei@ait.net.cn
	 * @date 2013-8-14 下午5:12:12
	 * @version V1.0
	 */
	public void updataEduaction(Object object) throws Exception;

	/**
	 * 通过人力关联表查询出关联的下拉列表 hr_human_relevance
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author liangwei@ait.net.cn
	 * @date 2013-8-16 下午3:57:05
	 * @version V1.0
	 */
	public List getRelevance(Object object);

	/**
	 * 判断本人的学历信息中是否还有最终学历 如果没有 则将基本表中最终学历清空
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lwei liangwei@ait.net.cn
	 * @date 2013-9-15 下午3:40:34
	 * @version V1.0
	 */
	public int getEduactionY(Object object);

	/**
	 * addTestInfo
	 * @throws SQLException
	 */
	public void addTestInfo(Object object) throws SQLException;

	/**
	 * 获取是否参加工会标示
	 * 
	 * @param personId
	 * @return
	 * @throws SQLException
	 */
	public String getJoinFlagByPersonId(Object object) throws SQLException;

	/**
	 * 添加黑色档案信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void addBadArchivesInfo(Object object) throws SQLException;

	/**
	 * 获取黑色档案信息列表
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getBadArchivesList(Object object) throws SQLException;

	public int getBadArchivesListCnt(Map paramMap);

	/**
	 * 删除黑色档案信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void deleteBadArchivesInfo(Object object) throws SQLException;

	/**
	 * 更新黑色档案信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void editBadArchivesInfo(Object object) throws SQLException;

	public List getBadArchivesList(Object obj, int currentPage, int pageSize);

	/**
	 * 员工信息查询
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpInfoList(Object object) throws SQLException;

	public List getEmpInfoList(Object obj, int currentPage, int pageSize);
	
	public List getEmpInfoSHList(Object obj, int currentPage, int pageSize);
	
	public List getEmpInfoListAr(Object obj, int currentPage, int pageSize);

	public int getEmpInfoListCnt(Object obj);
	
	public int getEmpInfoListArCnt(Object obj);

	/**
	 * 获取辅助信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getAssistList(Object object) throws SQLException;

	public List getAssistList(Object obj, int currentPage, int pageSize);

	/**
	 * 获取预转正信息列表
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws SQLException
	 */
	public List getPerConversionList(Object object) throws SQLException;

	public List getPerConversionList(Object obj, int currentPage, int pageSize);

	/**
	 * 获取预转正信息数量
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws SQLException
	 */
	public int getPerConversionListCnt(Object obj);

	/**
	 * 添加辅助信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void addAssistInfo(Object object) throws SQLException;

	/**
	 * 删除辅助信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void deleteAssistInfo(Object object) throws SQLException;

	/**
	 * 更新辅助信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void editAssistInfo(Object object) throws SQLException;

	/**
	 * 删除兼卖产品信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void deleteProductInfo(Object object) throws SQLException;

	/**
	 * 更新兼卖产品信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void addProductInfo(Object object) throws SQLException;
	
	/**
	 * 获取职责列表工页面查询使用
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getPositionList(Object obj) throws SQLException;

	public List getDqmcList(Object obj) throws SQLException;
	public List getDqmcListNew(Object obj) throws SQLException;
	
	public Object getPersonalInfoByLeave(Object object);

	public Object getArchivesInfo(Map paramMap);
	
	/**
	 * 获取社员兼卖产品类型
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpProductList(Object object);
	
	/**
	 * 根据person_id获取email
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmailByPersonId(Object object);
	
	/**
	 * 根据法人获取人员类型组
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpTypeGroup(Object object);

	public List getEmpInfoTempList(Object object, int i, int j);

	public int getEmpInfoTempCnt(LinkedHashMap paramMap);

	public int getEmpInfoTempErrCnt(LinkedHashMap paramMap);

	public String importEmpInfoTempListExcel(LinkedHashMap paramMap);



	public List getTempLanguageTempList(Object object, int pageNum, int numPerPage);

	public List getTempLanguageTempList(Object object);

	public int getTempLanguageTempErrorCnt(Object object);

	public int getTempLanguageTempCnt(Object object);
	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importFromExcel(Object object)throws Exception;

	public int getTempDisabledTempErrorCnt(Object object);

	public int getTempDisabledTempCnt(Object object);

	public List getTempDisabledTempList(Object object, int pageNum, int numPerPage);

	public List getTempDisabledTempList(Object object);


	public List getWorkExperienceInfoTempList(Object object, int pageNum,
			int numPerPage);

	public List getWorkExperienceInfoTempList(Object object);

	public int getWorkExperienceInfoTempCnt(LinkedHashMap paramMap);

	public int getWorkExperienceInfoTempErrCnt(LinkedHashMap paramMap);

	public String importInfoFromExcel(Object object) throws SQLException;

	public List getEvsInfoTempList(Object object, int pageNum,
			int numPerPage);

	public List getEvsInfoTempList(Object object);

	public int getEvsInfoTempErrCnt(LinkedHashMap paramMap);

	public int getEvsInfoTempCnt(LinkedHashMap paramMap);

	public int getTradeUnionInfoTempCnt(LinkedHashMap paramMap);

	public List getTradeUnionInfoTempList(Object object, int pageNum,
			int numPerPage);

	public List getTradeUnionInfoTempList(Object object);

	public int getTradeUnionInfoTempErrCnt(LinkedHashMap paramMap);

	/**
	 * 获取培训导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTrainingImportTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取培训导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTrainingImportTempList(Object object);
	
	/**
	 * 获取出错的培训导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTrainingImportTempErrorCnt(Object object);
	
	/**
	 * 获取培训导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTrainingImportTempCnt(Object object);
	
	/**
	 * 人员信息mapping 新增
	 */
	public void addEmpMapping(Object object) throws Exception ;
	
	/**
	 * 人员信息mapping 修改
	 */
	public void updateEmpMapping(Object object) throws Exception ;
	
	/**
	 * 人员信息mapping 删除
	 */
	public void deleteEmpMapping(Object object) throws Exception ;
	
	/**
	 * 人员信息mapping查询
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List viewEmpMappingList(Object object) throws SQLException ;
	
	/**
	 * 人员信息mapping查询
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List viewEmpMappingList(Object obj, int currentPage, int pageSize) ;
	
	/**
	 * 人员信息mapping查询 总数
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public int viewEmpMappingListCnt(Object obj);
	
	/**
	 * 人员信息mapping查询 BY NO
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpMappingByNo(Object obj)  throws SQLException;


	public List getworkExperienceTempList(Object object, int currentPage, int pageSize);
    //外国语数据验证及数据导入正式表
	public String importLanguageExcel(Object object)throws Exception;

	public int getTempContactTempErrorCnt(Object object);

	public int getTempContactTempCnt(Object object);

	public List getTempContactTempList(Object object, int pageNum, int numPerPage);

	public List getTempContactTempList(Object object);

	public int getTempAssistTempErrorCnt(Object object);

	public int getTempAssistTempCnt(Object object);

	public List getTempAssistTempList(Object object, int pageNum, int numPerPage);

	public List getTempAssistTempList(Object object);

	public List getEmpInfoTempList(Object object);

	public String importInfoExcel(Object object)throws Exception;

	public List getQualInfoTempList(Object object);

	public List getQualInfoTempList(Object object, int pageNum,
			int numPerPage);

	public int getQualInfoTempErrCnt(LinkedHashMap paramMap);

	public int getQualInfoTempCnt(LinkedHashMap paramMap);

	public List getProductInfoTempList(Object object);

	public int getProductInfoTempErrCnt(LinkedHashMap paramMap);

	public int getProductInfoTempCnt(LinkedHashMap paramMap);

	public List getProductInfoTempList(Object object, int pageNum,
			int numPerPage);

	public List getEmpTypeList(Map paramMap);

	public List getTempEmpInfoList(Map paramMap, int pageNum, int numPerPage);

	public List getTempEmpInfoList(Map paramMap);

	public Object getTempEmpInfoListCnt(Map paramMap);

	public void editTempEduPerInfo(Object object) throws Exception;

	/**
	 * 员工信息查询(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	public Object getPersonalInfoByLeaveApply(Object object) ;

	public List getTempEmpAffirmInfoListBatch(Object object);
	public List getTempEmpAffirmInfoListBatch(Object object, int currentPage, int pageSize);
	public int getTempEmpAffirmInfoListCntBatch(Object obj) throws Exception;

	@SuppressWarnings("rawtypes")
	public List getImportTmpEmpResultList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("rawtypes")
	public List getImportTmpEmpResultList(Object obj);
	public int getImportTmpEmpResultCnt(Object obj);
	public int getImportTmpEmpErrCnt(Object obj);
	@SuppressWarnings("rawtypes")
	public String importTmpEmpFromExcel(LinkedHashMap paramMap);
	@SuppressWarnings("rawtypes")
	public int delTmpEmpInBatch(LinkedHashMap paramMap);
	public List getTempEmpBatchReqList(Object object) throws Exception;
	public List getTempEmpBatchReqList(Object obj, int currentPage, int pageSize);
	public int getTempEmpBatchReqCnt(Object obj);
	public Object getTempEmpReqDetail(Object obj) ;
	
	/**
	 * 验证mapping的社号身份证号是否一致
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @date 2014-09-03
	 * @version V1.0
	 */
	public int validIdCard(Object object);
	/**
	 * 验证mapping的社号身份证号是否一致
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @date 2014-09-03
	 * @version V1.0
	 */
	public int validIsDuplicate(Object object) ;

	/** 
	* @Title: 查询法人人员所用的所有福利地区
	* @Description: TODO 
	* @param @param paramMap
	* @param @return    
	* @return Object    
	* @throws 
	*/
	public Object getInsrareaForCpnyId(LinkedHashMap paramMap);
	public int getEmpIdCardNoCnt(Object obj);
	
	/**
	 * 获取新旧社号mapping批量导入信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getMappingTempList(Object object) ;
	
	/**
	 * 获取新旧社号mapping批量导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getMappingTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取新旧社号mapping批量导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getMappingTempCnt(Object object);
	
	/**
	 * 获取出错的新旧社号mapping批量导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getMappingTempErrorCnt(Object object);
	

	public List gettreeDomeList(Object obj );

	/**
	 * 标签查询(sql)
	 * 
	 * @param parent_code_no
	 * @return retrunList
	 */
	public List getCodeListBySql(Object object);
	
	/**
	 * 人事信息卡查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	public List viewCardInfoList(Object obj );

	/**
	 * 人事信息卡1查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	public List viewCardInfoList1(Object obj );
	/**
	 * 人事信息卡1基本信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	public List viewBaseInfoList(Object obj );
	/**
	 * 人事信息卡1教育信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	public List viewJiaoyuInfoList(Object obj );
	/**
	 * 人事信息卡1经历查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	public List viewJingliInfoList(Object obj );
	/**
	 * 人事信息卡1家庭信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	public List viewJiatingInfoList(Object obj );
	/**
	 * 人事信息卡1评价信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	public List viewPingjiaInfoList(Object obj );
	/**
	 * 人事信息卡1资格信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	public List viewZigeInfoList(Object obj );
	/**
	 * 人事信息卡1培训信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	public List viewPeixunInfoList(Object obj );
	/**
	 * 人事信息卡1奖励信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	public List viewJiangliInfoList(Object obj );
	/**
	 * 人事信息卡1惩罚信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	public List viewChengfaInfoList(Object obj );
	/**
	 * 人事信息卡1发令信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	public List viewFalingInfoList(Object obj );
	
	/**
	 * 标签查询(QueryLabelInformationVolume)
	 * 
	 * @param parent_code_no
	 * @return retrunList
	 */
	public List getCodeListDESCRIPTION(Object object);
	
	/**
	 * viewPregnantManagement
	 * @param object
	 * @return
	 */
	public List getPregnantManagementList(Object object);
	
	public int getPregnantManagementList_count(Object object);
	
	public void editPregnantManagement(Object object) throws Exception;
	
	public void insertPregnantManagement(Object object) throws Exception;
	
	public Object viewSinglePregnantManagement(Object object);	
	
	public void deletePregnantManagement(Object object) throws Exception;


	/**
	 * 标签查询(QueryLabelInformationVolume)
	 * 
	 * @param parent_code_no
	 * @return retrunList
	 */
	public List getCodeListForEvs(Object object);

	public Object viewCurrentHrInfo(Object object);
	
	public List getEmpSimpleInfoList(Object object);
	public List getDeptAllList(Object object);
	public List viewDeptDemissionList(Object object);
	public List hrDemissionRateReport(Object object);
	public List hrDemissionRateByDeptNoReport(Object object);
	public List hrDemissionRateByShopReport(Object object);
	public List hrDemissionRateFullThreeMonthsReport(Object object);
	public List hrDemissionRateFullShopThreeMonthsReport(Object object);
	public List hrDemissionRateByXdfMonthsReport(Object object);
	public List hrStroeNumberReport(Object object);
	public void updateEmpinfo(Object object, String target) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateList(String target, List list)throws Exception ;
}
