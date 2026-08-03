package com.ait.ess.dao;

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
public interface EssEmpInfoDao {
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
	
	public Object getEssPersonInfoById(Object object);

	public List getEducationList(Object object);

	public List getEducationList(Object object, int currentPage, int pageSize);

	public void addEduactionInfo(Object object) throws Exception;

	public void deleteEduactionInfo(Object object) throws Exception;

	public void editEduPerInfo(Object object) throws Exception;

	public void editEducation(Object object) throws Exception;

	public List getExpInsideList(Object object);

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
	public Object getRewardInfo(Object object);

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
	public List getEmergencyAddressList(Object object);
	public void addEmergencyAddressInfo(Object object) throws Exception;

	public void deleteEmergencyAddressInfo(Object object) throws Exception;

	public void editEmergencyAddressInfo(Object object) throws Exception;
	//地址信息
	public List getAddressList(Object object);
	
	public Object getAddressInfo(Object object);
	public void addAddressInfo(Object object) throws Exception;

	public void deleteAddressInfo(Object object) throws Exception;

	public void editAddressInfo(Object object) throws Exception;

	public void addHomeRelationInfo(Object object) throws Exception;

	public void deleteHomeRelationInfo(Object object) throws Exception;

	public int editHomeRelation(Object object) throws Exception;

	public List getHealthList(Object object);

	public List getHealthList(Object object, int currentPage, int pageSize);

	public void editHealthInfo(Object object) throws Exception;

	public void deleteHealthInfo(Object object) throws Exception;

	public Object getWorkExperienceList(Object object);
	public Object getWorkExperienceList2(Object object);
	public Object getProductInfo(Object object);
	public List getProductInfoList(Object object);

	public Object getEducationInfo(Object object);

	public Object getWorkExperienceList(Object object, int currentPage,int pageSize);

	public void addWorkExperienceInfo(Object object) throws Exception;

	public void editWorkExperienceInfo(Object object) throws Exception;

	public void deleteWorkExpreienceInfo(Object object) throws Exception;

	public List getQualificationList(Object object);
	public Object getQualificationInfo(Object object);

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

	public int getEmpInfoListCnt(Object obj);

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
	public List getWorkInfo(Object object);

	public void essAddWorkInfo(Object object) throws Exception;
	public void essAddProductInfo(Object object) throws Exception;
	public void essAddEducationInfo(Object object) throws Exception;
	public void essAddQualificationInfo(Object object) throws Exception;
	public void essAddRewardInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
 public 	List getEssApplyList(Object object, int pageNum, int numPerPage);

	@SuppressWarnings("unchecked")
	public List getEssApplyList(Object object);

	public	int getEssApplyListCnt(Object object);
	
	    //////////////////////////////////////////////////////////////////////////////
	public List getEssAddressApplyList(Object object);
	public List getEssEmergencyList(Object object);
	public List getEssHomeRelationApplyList(Object object);
	public List getEssWorkApplyList(Object object);
	public List getEssProductApplyList(Object object);
	public List getEssEducationApplyList(Object object);
	public List getEssQualificationApplyList(Object object);
	public List getEssRewardList(Object object);
	public List getPersonalApplyList(Object object);

	
	
	
	   //////////////Object
	public Object getEssAddressApplyObject(Object object);
	public Object getEssEmergencyObject(Object object);
	public Object getEssHomeRelationApplyObject(Object object);
	public Object getEssWorkApplyObject(Object object);
	public Object getEssProductApplyObject(Object object);
	public Object getEssEducationApplyObject(Object object);
	public Object getEssQualificationApplyObject(Object object);
	public Object getEssRewardObject(Object object);
	public Object getPersonalApplyObject(Object object);

	//////////////////
	
	public void addPersonal(Object object) throws Exception;

	/**
	 * 个人照片申请上传
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addRecruitInfo(Object object)throws Exception ;
	
	public List viewInfoList(Object object, String target);


}
