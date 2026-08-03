package com.ait.ess.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.ait.sys.bean.AdminBean;
import com.ait.web.util.CommonException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: EmpInfoSer.java
 * @Description: implement Class EmpInfoSerImpl.java
 * @Create date: Jan 16, 2012 3:24:55 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
@SuppressWarnings("unchecked")
public interface EssEmpInfoSer {
	public Map getEmpList(HttpServletRequest request);

	public Object getBasicInfo(HttpServletRequest request);

	public Map getPaEmpInfoForGrid(HttpServletRequest request);

	public Map getSinfoForGrid(HttpServletRequest request);

	public Map getBizlistForGrid(HttpServletRequest request);

	public Map getAppendInfoForGrid(HttpServletRequest request);

	public Map getResignationForGrid(HttpServletRequest request);

	public Map getLanuageInfoForGrid(HttpServletRequest request);

	public Map getITLevelInfoForGrid(HttpServletRequest request);

	public Map getQualificationInfoForGrid(HttpServletRequest request);

	public Map getEvaluateForGrid(HttpServletRequest request);

	public Map getEvalForGrid(HttpServletRequest request);

	public Map getHealthInfoForGrid(HttpServletRequest request);

	public Map getExpInsideForGrid(HttpServletRequest request);

	public Map getFamilyInfoForGrid(HttpServletRequest request);

	public Map getSocietyRelationForGrid(HttpServletRequest request);

	public Map getPunishMentForGrid(HttpServletRequest request);

	public Map getRewardForGrid(HttpServletRequest request);

	public Map getEduForGrid(HttpServletRequest request);

	public Map getSuspendForGrid(HttpServletRequest request);

	public Map getPluralityForGrid(HttpServletRequest request);

	public Map getDispatchForGrid(HttpServletRequest request);

	public Map getExperienceInfoForGrid(HttpServletRequest request);

	public Map getRelationalTypeCodeForSelect(HttpServletRequest request);

	public Map getDegreeCodeForSelect(HttpServletRequest request);

	public Map getOtherRelationForSelect(HttpServletRequest request);

	public Map getCheckResultForSelect(HttpServletRequest request);

	public Map getCheckWhetherForSelect(HttpServletRequest request);

	public Map getLanguageLevelCodeForSelect(HttpServletRequest request);

	public Map getLanguageExamCodeForSelect(HttpServletRequest request);

	public Map getLanguageTypeCodeForSelect(HttpServletRequest request);

	public Map getQualNameCodeForSelect(HttpServletRequest request);

	public Map getBankNameCodeForSelect(HttpServletRequest request);

	public Map getTogetherFlagForSelect(HttpServletRequest request);

	public String updateFamilyInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception;

	public String updateSocietyRelationGrid(
			List<LinkedHashMap<String, Object>> list) throws Exception;

	public String updateHealthInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception;

	public String updateExperienceInfoGrid(
			List<LinkedHashMap<String, Object>> list) throws Exception;

	public String updateFappendInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception;

	public String updateFappendInfoGrid(
			List<LinkedHashMap<String, Object>> list, HttpServletRequest request)
			throws Exception;

	public String updateQualificationInfoGrid(
			List<LinkedHashMap<String, Object>> list) throws Exception;

	public String updateLanuageInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception;

	public String updatePaEmpInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception;

	public String updatePaEmpInfoGrid(List<LinkedHashMap<String, Object>> list,
			HttpServletRequest request) throws Exception;

	public List getPersonalList(HttpServletRequest request);

	public int getPersonalCnt(HttpServletRequest request);

	public Object getPersonalInfo(HttpServletRequest request);

	public Object getPersonalInfoByPid(HttpServletRequest request);
	public Object getEssPersonInfoById(HttpServletRequest request);
	public List getEducationList(HttpServletRequest request);

	public int addEduactionInfo(HttpServletRequest request);

	public int deleteEduactionInfo(HttpServletRequest request);

	public int editEduPerInfo(HttpServletRequest request);

	public List getExpInsideList(HttpServletRequest request);

	public List getAssignmentList(HttpServletRequest request);

	public List getTradeunionList(HttpServletRequest request);

	public List getResignationInfo(HttpServletRequest request);

	public List getEvsInfoList(HttpServletRequest request);

	public int addEvsInfo(HttpServletRequest request);

	public Object getEvsInfo(HttpServletRequest request);

	public int editEvsInfo(HttpServletRequest request);

	public int deleteEvsInfo(HttpServletRequest request);

	public List getReward(HttpServletRequest request);
	public Object getRewardInfo(HttpServletRequest request);

	public List getPunishment(HttpServletRequest request);

	public List getPluralityList(HttpServletRequest request);

	public List getTrainingInfoList(HttpServletRequest request);

	public int addTrainingInfo(HttpServletRequest request);

	public int editTrainingInfo(HttpServletRequest request);

	public int deleteTrainingInfo(HttpServletRequest request);

	public List getFamilyList(HttpServletRequest request);

	public int addFamilyInfo(HttpServletRequest request);

	public int deleteFamilyInfo(HttpServletRequest request);

	public int editFamilyInfo(HttpServletRequest request);
	//地址信息
	public List getAddressList(HttpServletRequest request);
	
	public Object getAddressInfo(HttpServletRequest request);

	public int addAddressInfo(HttpServletRequest request);

	public int deleteAddressInfo(HttpServletRequest request);

	public int editAddressInfo(HttpServletRequest request);

	public List getHomeRelationList(HttpServletRequest request);
	public List getEmergencyAddressList(HttpServletRequest request);
//	public List getAddressList(HttpServletRequest request);
	public int addEmergencyAddressInfo(HttpServletRequest request);

	public int deleteEmergencyAddressInfo(HttpServletRequest request);

	public int editEmergencyAddressInfo(HttpServletRequest request);
	public int addHomeRelationInfo(HttpServletRequest request);

	public int deleteHomeRelationInfo(HttpServletRequest request);

	public int editHomeRelation(HttpServletRequest request);

	public List getHealthList(HttpServletRequest request);

	public int addHealthInfo(HttpServletRequest request);

	public int editHealthInfo(HttpServletRequest request);

	public int deleteHealthInfo(HttpServletRequest request);

	public Object getWorkExperienceList(HttpServletRequest request);
	public Object getWorkExperienceInfo(HttpServletRequest request);
	public Object getProductInfo(HttpServletRequest request);
	public List getProductInfoList(HttpServletRequest request);
	
	public Object getEducationInfo(HttpServletRequest request);


	
	public List getWorkExperienceInfoTempList(HttpServletRequest request);

	public int addWorkExperienceInfo(HttpServletRequest request);

	public int editWorkExperienceInfo(HttpServletRequest request);

	public int deleteWorkExpreienceInfo(HttpServletRequest request);

	public List getQualificationList(HttpServletRequest request);
	public Object getQualificationInfo(HttpServletRequest request);


	public List getLanguageLevelList(HttpServletRequest request);

	public int editCompetenceInfo(HttpServletRequest request);

	public int addCompetenceInfo(HttpServletRequest request);

	public int deleteCompetenceInfo(HttpServletRequest request);

	public List getCodeList(String parent_code_no, HttpServletRequest request);

	public List getAdditionalList(HttpServletRequest request);

	public int addAdditionalInfo(HttpServletRequest request);

	public int editAdditionalInfo(HttpServletRequest request);

	public int deleteAdditionalInfo(HttpServletRequest request);

	public List getAccountList(HttpServletRequest request);

	public List getContractList(HttpServletRequest request);

	public List getFileList(HttpServletRequest request);

	public int addFileInfo(HttpServletRequest request);

	public int editFileInfo(HttpServletRequest request);

	public int deleteFileInfo(HttpServletRequest request);

	public List getGoAbroadList(HttpServletRequest request);

	public int addGoAbroadInfo(HttpServletRequest request);

	public int editGoAbroadInfo(HttpServletRequest request);

	public int deleteGoAbroadInfo(HttpServletRequest request);

	public List getCredentialList(HttpServletRequest request);

	public int addCredentialInfo(HttpServletRequest request);

	public int editCredentialInfo(HttpServletRequest request);

	public int deleteCredentialInfo(HttpServletRequest request);

	public List getEmpIdList(HttpServletRequest request);

	public int getEmpIdListCnt(HttpServletRequest request);

	public int getPersonCnt(HttpServletRequest request);

	public List getPidEidList(HttpServletRequest request) throws Exception;
	
	public List getPidEidListXiao(HttpServletRequest request) throws Exception;
	
	public List getPidEidList2(HttpServletRequest request) throws Exception;

	public List getOldPostGradeList(HttpServletRequest request);

	public List getPostGradeList(HttpServletRequest request);

	public int editPhotoPath(HttpServletRequest request, String path);

	public List getOrderParmList(HttpServletRequest request, String sortNameNo);

	public List getHaoFengLists(HttpServletRequest request);

	public List getMenuThirdListList(String menu_code,
			HttpServletRequest request);

	/**
	 * 根据法人获取公司人员信息 导出模板使用
	 * 
	 * @param request
	 * @return
	 */
	public List getEmpListToModelExcel(HttpServletRequest request);

	/**
	 * 查询ess tab菜单
	 * 
	 * @param menu_code
	 * @param request
	 * @return
	 */
	public List getTabMenuListList(String menu_code, HttpServletRequest request);

	/**
	 * 残疾信息
	 * 
	 * @param request
	 * @return
	 */
	public List getDisabilityinfoList(HttpServletRequest request);

	/**
	 * 添加残疾信息
	 * 
	 * @param request
	 * @return
	 */
	public int addDisabledInfo(HttpServletRequest request);

	/**
	 * 删除残疾信息
	 * 
	 * @param request
	 * @return
	 */
	public int deleteDisabledInfo(HttpServletRequest request);

	/**
	 * 修改残疾信息
	 * 
	 * @param request
	 * @return
	 */
	public int editDisabledInfo(HttpServletRequest request);

	/**
	 * 添加工会信息(add addTradeunionInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addTradeunionInfo(HttpServletRequest request);
	/**
	 * 删除工会信息(add deleteTradeunionInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteTradeunionInfo(HttpServletRequest request);
	/**
	 * 修改工会信息(add updateTradeunionInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int editTradeunionInfo(HttpServletRequest request);
	/**
	 * 添加外国语信息(add addLanguageInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addLanguageInfo(HttpServletRequest request);
	/**
	 * 修改外国语信息(update addLanguageInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int updateLanguageInfo(HttpServletRequest request);
	/**
	 * 删除外国语信息(delete addLanguageInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteLanguageInfo(HttpServletRequest request);
	/**
	 * 修改毕业学校
	 * 
	 * @param request
	 * @return
	 */
	public int editEducation(HttpServletRequest request);

	public Object getPersonalInfoByPid1(HttpServletRequest request);

	public Object getPersonalInfoForInformation(HttpServletRequest request);

	public Object getPersonalInfoForInformationCount(HttpServletRequest request);
	/**
	 * 统计用户是否有最终学历
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yorio youjia@ait.net.cn
	 * @date 2013-8-13 下午5:00:25
	 * @version V1.0
	 */
	public int getFinalNum(HttpServletRequest request);
	/**
	 * 通过人力关联表查询出关联的下拉列表 hr_human_relevance
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yorio youjia@ait.net.cn
	 * @date 2013-8-16 下午3:51:34
	 * @version V1.0
	 */
	public List getRelevance(HttpServletRequest request);
	/**
	 *addTestInfo
	 * @throws SQLException
	 */
	public int addTestInfo(HttpServletRequest request) throws SQLException;

	/**
	 * 获取是否参加工会标示
	 * @param personId
	 * @return
	 * @throws SQLException
	 */
	public String getJoinFlagByPersonId(String personId) throws SQLException;
	/**
	 * 添加黑色档案信息
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public int addBadArchivesInfo(HttpServletRequest request)
			throws SQLException;
	/**
	 * 获取黑色档案信息列表
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getBadArchivesList(HttpServletRequest request)
			throws SQLException;
	
	public int getBadArchivesListCnt(HttpServletRequest request);

	/**
	 * 删除黑色档案信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public int deleteBadArchivesInfo(HttpServletRequest request)
			throws SQLException;

	/**
	 * 更新黑色档案信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public int editBadArchivesInfo(HttpServletRequest request)
			throws SQLException;
	/**
	 * 员工信息查询
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpInfoList(HttpServletRequest request) throws SQLException;

	public int getEmpInfoListCnt(HttpServletRequest request)
			throws SQLException;

	public String getTemplateInfo(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException;

	public String getPaiQianDiGuanLiMoBanModleInfo(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException;
	
	public String getPaiQianDiImportInfo(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException;

	public String exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanMoBanModle(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException;
	
	public String exportZuiDiGongZiBiaoZhunCuXiaoYuanMoBanModle(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException;
	
	public String getPaiQianDiJinTieBiaoZhunMoBanModleInfo(
			HttpServletRequest request, List aliasNameList, List list,
			List mapList, List mapNameList) throws SQLException;
	/**
	 * 获取辅助信息
	 * @param request
	 * @return
	 * @author weizhengchen
	 * @throws SQLException
	 */
	public List getAssistList(HttpServletRequest request) throws SQLException;
	/**
	 * 获取预转正信息列表
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getPerConversionList(HttpServletRequest request)
			throws SQLException;
	/**
	 * 获取预转正信息数量
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws SQLException
	 */
	public int getPerConversionListCnt(HttpServletRequest request)
			throws SQLException;
	/**
	 * 添加辅助信息
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws SQLException
	 */
	public int addAssistInfo(HttpServletRequest request) throws SQLException;
	/**
	 * 删除辅助信息
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws SQLException
	 */
	public int deleteAssistInfo(HttpServletRequest request) throws SQLException;
	/**
	 * 更新辅助信息
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws SQLException
	 */
	public int editAssistInfo(HttpServletRequest request) throws SQLException;
	
	/**
	 * 更新兼卖产品信息
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws SQLException
	 */
	public int editProductInfo(HttpServletRequest request) throws SQLException;
	
	/**
	 * 获取职责列表，工页面查询使用
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws SQLException
	 */
	public List getPositionList(HttpServletRequest request) throws SQLException;
	
	public List getDqmcList(HttpServletRequest request) throws SQLException;

	public List getDqmcListNew(HttpServletRequest request) throws SQLException ;
	
	/**
	 * 获取法人列表
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws SQLException
	 */
	public List getCompanyList(HttpServletRequest request) throws SQLException;
	public List getEmpInfoLxjList(HttpServletRequest request) throws SQLException;
	
	public List getPaInfoLxjList(HttpServletRequest request) throws SQLException;
	public List getPaInfoLxjLgechList(HttpServletRequest request) throws SQLException;
	public List getPaInfoLxjLgetaList(HttpServletRequest request) throws SQLException;
	

	public List getYearInfoLxjList(HttpServletRequest request) throws SQLException;
	public List getpayDetilInfoLxjList(HttpServletRequest request) throws SQLException;
	public List getpayDetilInfoLxjLgechList(HttpServletRequest request) throws SQLException;
	public List getpayDetilInfoLxjLgetaList(HttpServletRequest request) throws SQLException;
	
	public List getotherPayInfoLxjList(HttpServletRequest request) throws SQLException;
	public List getotherPayInfoLxjLgechList(HttpServletRequest request) throws SQLException;
	public List getotherPayInfoLxjLgetaList(HttpServletRequest request) throws SQLException;
	
	public List getwelfarePayInfoLxjList(HttpServletRequest request) throws SQLException;
	public List getwelfarePayInfoLxjLgechList(HttpServletRequest request) throws SQLException;
	public List getwelfarePayInfoLxjLgetaList(HttpServletRequest request) throws SQLException;

	public List getadministrationPayInfoLxjList(HttpServletRequest request) throws SQLException;
	public List getadministrationPayInfoLxjLgechList(HttpServletRequest request) throws SQLException;
	public List getadministrationPayInfoLxjLgetaList(HttpServletRequest request) throws SQLException;

	public List getpaManuallyInfoLxjList(HttpServletRequest request) throws SQLException;
	public List getpaManuallyInfoLxjLgechList(HttpServletRequest request) throws SQLException;
	public List getpaManuallyInfoLxjLgetaList(HttpServletRequest request) throws SQLException;

	public Object getPersonalInfoByLeave(HttpServletRequest request,
			String PERSON_ID);
	/**
	 * 根据编号获取整条数据
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-7-14 下午05:04:42 
	* @version V1.0
	 */
	public Object getArchivesInfo(HttpServletRequest request);
	
	/**
	 * 获取社员兼卖产品类型
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpProductList(HttpServletRequest request)
		throws SQLException;

	/**
	 * 根据法人获取人员类型组
	 */
	public List getEmpTypeGroup(HttpServletRequest request)
			throws SQLException ;

	public List getEmpInfoTempList(HttpServletRequest request);
	
	/**
	 * 获取培训信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTrainingImportTempList(HttpServletRequest request) ;
	
	/**
	 * 获取培训信息导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTrainingImportTempCnt(HttpServletRequest request, String errorFlag);

	public int getEmpInfoTempCnt(HttpServletRequest request,String errorFlag);
	/*
	public int getEmpInfoTempErrCnt(HttpServletRequest request);
*/
	public String importEmpInfoTempListExcel(HttpServletRequest request);
	
	/**
	 * 人员信息mapping 新增
	 */
	public int addEmpMapping(HttpServletRequest request);
	
	/**
	 * 人员信息mapping 修改
	 */
	public int updateEmpMapping(HttpServletRequest request);


	
	/**
	 * 获取外国语导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempLanguageTempList(HttpServletRequest request);
	/**
	 * 获取外国语导入信息记录数
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempLanguageTempCnt(HttpServletRequest request, String string);
	
	/**
	 * 获取残疾人信息导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @date 2014-9-03
	 * @version V1.0
	 */
	public List getTempDisabledTempList(HttpServletRequest request);
	/**
	 * 获取残疾人信息导入信息记录数
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @date 2014-9-03
	 * @version V1.0
	 */
	public int getTempDisabledTempCnt(HttpServletRequest request, String string);
    
	/**
	 * 基础信息导出信息记录数（外国语、残疾证）
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @date 2014-9-03
	 * @version V1.0
	 */
	public String getTemplateInfoByExcelData11(HttpServletRequest request, List aliasNameList, List list, List mapList, List mapNameList);

	/**
	 * 人员信息mapping 删除
	 */
	public int deleteEmpMapping(HttpServletRequest request);
	
	/**
	 * 人员信息mapping查询
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public int viewEmpMappingListCnt(HttpServletRequest request)
		throws SQLException ;
	
	/**
	 * 人员信息mapping查询
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List viewEmpMappingList(HttpServletRequest request)
			throws SQLException ;
	
	/**
	 * 人员信息mapping查询
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public LinkedHashMap getEmpMappingByNo(HttpServletRequest request)
			throws SQLException ;
	public int getWorkExperienceInfoTempCnt(HttpServletRequest request, String errorFlag);

	public String submitImportExcelWorkExperienceData(HttpServletRequest request);

	public List getEvsInfoTempList(HttpServletRequest request);

	public int getEvsInfoTempCnt(HttpServletRequest request, String errorFlag);

	public int getTradeUnionInfoTempCnt(HttpServletRequest request,
			String errorFlag);

	public List getTradeUnionInfoTempList(HttpServletRequest request);

	public String getWorkTemplateInfoByExcelData(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList);
    //紧急联系人信息
	public List getTempcontactTempList(HttpServletRequest request);
     //紧急联系人的记录数
	public int getTempContactTempCnt(HttpServletRequest request, String string);
   //辅助信息信息
	public List getTempAssistTempList(HttpServletRequest request);
   //总记录数
	public int getTempAssistTempCnt(HttpServletRequest request, String string);
    //基本信息的提交(辅助、残疾证、紧急联系人)
	public String submitImportInfoExcelTempData(HttpServletRequest request);

	public List getTradeUnionInfoList(HttpServletRequest request);

	public List getQualInfoTempList(HttpServletRequest request);

	public int getQualInfoTempCnt(HttpServletRequest request, String errorFlag);
	/**
	 * 兼卖信息
	 * @param request
	 * @return
	 */
	public List getProductInfoTempList(HttpServletRequest request);
	
	public int getProductInfoTempCnt(HttpServletRequest request, String string);

	public Object getTempEmpInfoList(HttpServletRequest request);

	public Object getTempEmpInfoListCnt(HttpServletRequest request);

	public Object getEmpTypeList(HttpServletRequest request);

	public int editTempEduPerInfo(HttpServletRequest request);

	public List getTmpEmpAffirmInfoListBatch(HttpServletRequest request) throws Exception;
	
	public int getTmpEmpAffirmInfoListCntBatch(HttpServletRequest request) throws Exception;

	public Object getPersonalInfoByLeaveApply(HttpServletRequest request, String PERSON_ID);

	/*************************************************************/
    /**
	 * 临时职导入结果
	 */
	@SuppressWarnings("rawtypes")
	public List getImportTmpEmpResultList(HttpServletRequest request, Map paramMap);
	
	@SuppressWarnings("rawtypes")
	public int getImportTmpEmpResultCnt(HttpServletRequest request, Map paramMap);
	
	@SuppressWarnings("rawtypes")
	public int getImportTmpEmpErrCnt(HttpServletRequest request, Map paramMap);
	
	public String importTmpEmpFromExcel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap);

	@SuppressWarnings("rawtypes")
	public List getImportTmpEmpFromExcel(HttpServletRequest request)throws Exception;

	public int delTmpEmpInBatch(HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List viewTempEmpBatchReq(HttpServletRequest request) throws Exception ;
	@SuppressWarnings("rawtypes")
	public int getTempEmpBatchReqCnt(HttpServletRequest request);

	public List getTempEmpBatchAffirmList(HttpServletRequest request) throws Exception;
	public int getTempEmpBatchAffirmCnt(HttpServletRequest request);

	public String getEmpBatchTemp(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList) throws SQLException;
	
	public Object getTempEmpReqDetail(HttpServletRequest request) ;
	/*************************************************************/
	public List getCompanyListHome(HttpServletRequest request)
		throws SQLException ;
	
	/**
	 * 获取员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfo (Map param) throws Exception;

	public Object getInsrareaList(HttpServletRequest request);
	
	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int syncEmpInfo(HttpServletRequest request)
			throws CommonException ;
	
	/**
	 * 新就社号mapping批量导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getMappingTempList(HttpServletRequest request) ;
	
	/**
	 * 新就社号mapping批量导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getMappingTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 新就社号mapping批量excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelMappingEmpData(HttpServletRequest request);
	
	public List getWorkInfo(HttpServletRequest request);

	public int essAddWorkInfo(HttpServletRequest request);
	public int essAddProductInfo(HttpServletRequest request);
	public int essAddEducationInfo(HttpServletRequest request);
	public int essAddQualificationInfo(HttpServletRequest request);
	public int essAddRewardInfo(HttpServletRequest request);

	
	//变更明细查询
	public List getEssApplyList(HttpServletRequest request);

	public int getEssApplyListCnt(HttpServletRequest request);
	
	  //////////////////////////////////////////////////////////////////////////////
	public List getEssAddressApplyList(HttpServletRequest request);
	public List getEssEmergencyList(HttpServletRequest request);
	public List getEssHomeRelationApplyList(HttpServletRequest request);
	public List getEssWorkApplyList(HttpServletRequest request);
	public List getEssProductApplyList(HttpServletRequest request);
	public List getEssEducationApplyList(HttpServletRequest request);
	public List getEssQualificationApplyList(HttpServletRequest request);
	public List getEssRewardList(HttpServletRequest request);
	public List getPersonalApplyList(HttpServletRequest request);

	
	
	/////////////////objiect  
	public Object getEssAddressApplyObject(HttpServletRequest request);
	public Object getEssEmergencyObject(HttpServletRequest request);
	public Object getEssHomeRelationApplyObject(HttpServletRequest request);
	public Object getEssWorkApplyObject(HttpServletRequest request);
	public Object getEssProductApplyObject(HttpServletRequest request);
	public Object getEssEducationApplyObject(HttpServletRequest request);
	public Object getEssQualificationApplyObject(HttpServletRequest request);
	public Object getEssRewardObject(HttpServletRequest request);
	public Object getPersonalApplyObject(HttpServletRequest request);

	
	////
	public int addPersonal(HttpServletRequest request);
	
	/**
	 * 上传申请照片
	 */
	@SuppressWarnings("unchecked")
	public int updateRecruitPhotoInfo(HttpServletRequest request, String photoPath) ;
	
	public List viewInfoList(HttpServletRequest request, String target) ;

}








