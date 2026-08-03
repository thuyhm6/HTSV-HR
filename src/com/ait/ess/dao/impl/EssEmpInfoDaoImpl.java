package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.ess.dao.EssEmpInfoDao;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: EmpInfoDaoImpl.java
 * @Description: implement Class EmpInfoDao.java
 * @Create date: Jan 16, 2012 3:29:38 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
@Repository
@SuppressWarnings("unchecked")
public class EssEmpInfoDaoImpl extends SqlMapClientSupport implements
		EssEmpInfoDao {
	public List getEmpList(Object object) {
		List list = new ArrayList();
		try {
			list = this.queryForList("ess.empinfo.getEmpList", object, Integer
					.parseInt(((Map) object).get("page").toString()), Integer
					.parseInt(((Map) object).get("pagesize").toString()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getEmpCnt(Object object) {
		int temp = 0;
		try {
			temp = Integer.parseInt(this.queryForObject(
					"ess.empinfo.getEmpCnt", object).toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	public Object getBasicInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("ess.empinfo.getBasicInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	public int getContractCnt(Object object) {
		int temp = 0;
		try {
			temp = Integer.parseInt(this.queryForObject(
					"ess.empinfo.getContractCnt", object).toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public Map getPaEmpInfoForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getPaEmpInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getPaEmpInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getPaEmpInfoCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getSinfoForGrid(Object object, int skipResults, int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getSinfo", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getSinfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getSinfoCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getBizlistForGrid(Object object, int skipResults, int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getBizlist", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getBizlist", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getBizlistCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getAppendInfoForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getAppendInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getAppendInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getAppendInfoCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getResignationForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getResignation",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getResignation", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getResignationCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getITLevelInfoForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getITLevelInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getITLevelInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getITLevelInfoCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getLanuageInfoForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getLanuageInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getLanuageInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getLanuageInfoCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getQualificationInfoForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 =
			// this.queryForGridList("ess.empinfo.getQualificationInfo", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getQualificationInfo",
					object, skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getQualificationInfoCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getEvalForGrid(Object object, int skipResults, int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getEval", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getEval", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getEvalCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getEvaluateForGrid(Object object, int skipResults, int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getEvaluate",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getEvaluate", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getEvaluateCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getHealthInfoForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getHealthInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getHealthInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getHealthInfoCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getExpInsideForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getExpInside",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getExpInside", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getExpInsideCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getFamilyInfoForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getFamilyInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getFamilyInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getFamilyInfoCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getSocietyRelationForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getSocietyRelation",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getSocietyRelation",
					object, skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getSocietyRelationCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getPunishMentForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getPunishMent",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getPunishMent", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getPunishMentCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getRewardForGrid(Object object, int skipResults, int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getReward", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getReward", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getRewardCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getEduForGrid(Object object, int skipResults, int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getEdu", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getEdu", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getEduCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getDispatchForGrid(Object object, int skipResults, int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getDispatch",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getDispatch", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getDispatchCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getPluralityForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getPlurality",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getPlurality", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getPluralityCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getSuspendForGrid(Object object, int skipResults, int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getSuspend", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getSuspend", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getSuspendCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getExperienceInfoForGrid(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			// object2 = this.queryForGridList("ess.empinfo.getExperienceInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("ess.empinfo.getExperienceInfo",
					object, skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.empinfo.getExperienceInfoCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getSysCodeForSelect(Object object) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList("ess.empinfo.getSysCode", object);
			object2.put("Rows", Rows != null ? Rows : null);
			object2.put("Total", Rows != null ? Rows.size() : 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	/**
	 *家人关系更新
	 */
	@Override
	public void updateFamilyInfo(Object object) throws Exception {
		this.update("ess.empinfo.updateFamilyInfo", object);

	}

	@Override
	public void addExperienceInfo(Object object) throws Exception {
		this.insert("ess.empinfo.insertExperienceInfo", object);

	}

	@Override
	public void addFappendInfo(Object object) throws Exception {
		this.insert("ess.empinfo.insertAdditionalInfo", object);

	}

	/**
	 *外国语信息添加
	 */
	@Override
	public void addLanuageInfo(Object object) throws Exception {
		this.insert("ess.empinfo.insertLanguageInfo", object);

	}

	/**
	 *帐户信息添加
	 */
	@Override
	public void addPaEmpInfo(Object object) throws Exception {
		this.insert("ess.empinfo.insertAccountsInfo", object);

	}

	/**
	 *社会关系添加
	 */
	@Override
	public void addSocietyRelation(Object object) throws Exception {
		this.insert("ess.empinfo.insertFamilyInfo", object);

	}

	@Override
	public void deleteExperienceInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteExperienceInfo", object);

	}

	@Override
	public void deleteFappendInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteAdditionalInfo", object);

	}

	@Override
	public void deleteLanuageInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteLanguageInfo", object);

	}

	@Override
	public void deletePaEmpInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteAccountsInfo", object);

	}

	@Override
	public void deleteQualificationInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteQualificationInfo", object);

	}

	@Override
	public void deleteSocietyRelation(Object object) throws Exception {
		this.delete("ess.empinfo.deleteFamilyInfo", object);

	}

	@Override
	public void updateExperienceInfo(Object object) throws Exception {
		this.update("ess.empinfo.updateExperienceInfo", object);

	}

	@Override
	public void updateFappendInfo(Object object) throws Exception {
		this.update("ess.empinfo.updateAdditionalInfo", object);

	}

	@Override
	public void updateHealthInfo(Object object) throws Exception {
		this.update("ess.empinfo.updateHealthInfo", object);

	}

	@Override
	public void updateLanuageInfo(Object object) throws Exception {
		this.update("ess.empinfo.updateLanguageInfo", object);

	}

	@Override
	public void updatePaEmpInfo(Object object) throws Exception {
		this.update("ess.empinfo.updateAccountsInfo", object);

	}

	@Override
	public void updateQualificationInfo(Object object) throws Exception {
		this.update("ess.empinfo.updateQualificationInfo", object);

	}

	@Override
	public void updateSocietyRelation(Object object) throws Exception {
		this.update("ess.empinfo.updateFamilyInfo", object);

	}

	/**
	 * 员工基础信息 (Staff foundation information)
	 * 
	 * @param obj
	 * @return List
	 */
	@Override
	public List getPersonalList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getPersonalList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 员工基础信息 (Staff foundation information)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 */
	@Override
	public List getPersonalList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getPersonalList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getPersonalList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 员工基础信息数 (personal number based information)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int getPersonalCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getPersonalCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 员工信息查询(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	public Object getPersonalInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this
					.queryForObject("ess.empinfo.getPersonalInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	/**
	 * 员工信息查询(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getPersonalInfoByPid(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"ess.empinfo.getPersonalInfoByPidNew", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	/**
	 * 员工信息查询(Employee Information Management)22
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getEssPersonInfoById(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("ess.empinfo.getEssPersonInfoById",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	/**
	 * 员工信息查询1(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getPersonalInfoByPid1(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForList("ess.empinfo.getPersonalInfoByPid1",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	/**
	 * 毕业学校查询 (Graduate school inquires)
	 * 
	 * @param obj
	 * @return List
	 */
	@Override
	public List getEducationList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getEducationList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 毕业学校查询 (Graduate school inquires)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	public List getEducationList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getEducationList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getEducationList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加毕业学校(add Graduate school)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addEduactionInfo(Object obj) throws Exception {

		this.insert("ess.empinfo.insertEducation", obj);

	}

	/**
	 * 删除毕业学校(delete Graduate school)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deleteEduactionInfo(Object obj) throws Exception {

		this.delete("ess.empinfo.deleteEducation", obj);

	}

	/**
	 * 修改个人信息(Edit Personal Information)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public void editEduPerInfo(Object object) throws Exception {
		this.update("ess.empinfo.updateEmployeeInfo", object);
		// ------------------执行updateEmployeeInfo--------------------
		this.update("ess.empinfo.updatePersonalInfo", object);
		// ------------------执行updatePersonalInfo--------------------
		this.update("ess.empinfo.updateEmpPaInfo", object);
		// ------------------执行updatePAInfo--------------------
	}

	public void editTempEduPerInfo(Object object) throws Exception {
		this.update("ess.empinfo.updateTempEmployeeInfo", object);
		// ------------------执行updateEmployeeInfo--------------------
		this.update("ess.empinfo.updateTempPersonalInfo", object);
		// ------------------执行updatePersonalInfo--------------------
		this.update("ess.empinfo.updateTempEmpPaInfo", object);
		// ------------------执行updatePAInfo--------------------
	}

	/**
	 * 修改学校信息(Edit school Information)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public void editEducation(Object object) throws Exception {
		this.update("ess.empinfo.updateEducation", object);
	}

	/**
	 * 发令查询 (dekreti inquires)
	 * 
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List getExpInsideList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getExpInsideList", obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	/**
	 * 发令查询 (dekreti inquires)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List getExpInsideList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getExpInsideList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getExpInsideList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 发令查询 (dekreti inquires)
	 * 
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List getAssignmentList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getAssignmentList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 发令查询 (dekreti inquires)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List getAssignmentList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getAssignmentList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getAssignmentList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 离职信息查询(Left information query)
	 * 
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List getResignationInfo(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getResignationInfo(obj, -1, -1);

		return returnList;
	}

	/**
	 * 离职信息查询(Left information query)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List getResignationInfo(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getResignationInfo", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getResignationInfo", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 评价信息(Evaluation information)
	 * 
	 * @param obj
	 * @return List
	 */
	@Override
	public List getEvsInfoList(Object obj) {

		List returnList = new ArrayList();

		returnList = this.getEvsInfoList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 评价信息(Evaluation information)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List getEvsInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getEvsInfoList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getEvsInfoList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加评价信息(add Evaluation information)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void addEvsInfo(Object obj) throws Exception {

		this.insert("ess.empinfo.addEvsInfo", obj);

	}

	/**
	 * 查询评价信息(select assessment information)
	 * 
	 * @param object
	 * @return object2
	 */
	@Override
	public Object getEvsInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("ess.empinfo.getEvsInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	/**
	 * 修改评价信息(Modify assessment information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editEvsInfo(Object object) throws Exception {
		this.update("ess.empinfo.updateEvsInfo", object);
	}

	/**
	 * 删除评价信息(delete assessment information)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void deleteEvsInfo(Object obj) throws Exception {
		this.delete("ess.empinfo.deleteEvsInfo", obj);
	}

	/**
	 * 奖励(Reward)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	@Override
	public List getReward(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getReward(obj, -1, -1);

		return returnList;
	}

	/**
	 * 奖励单个(Reward)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	@Override
	public Object getRewardInfo(Object obj) {

		try {
			return this.queryForObject("ess.empinfo.getRewardInfo", obj);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 奖励*(Reward)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getReward(Object obj, int currentPage, int pageSize) {

		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getReward", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getReward", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 惩戒(Punishment)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	@Override
	public List getPunishment(Object obj) {

		List returnList = new ArrayList();

		returnList = this.getPunishment(obj, -1, -1);

		return returnList;
	}

	/**
	 * 惩戒(Punishment)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getPunishment(Object obj, int currentPage, int pageSize) {

		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getPunishment",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("ess.empinfo.getPunishment", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 兼职(Plurality)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	@Override
	public List getPluralityList(Object obj) {

		List returnList = new ArrayList();

		returnList = this.getPluralityList(obj, -1, -1);

		return returnList;

	}

	/**
	 * 兼职(Plurality)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getPluralityList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getPluralityList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getPluralityList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 工会(Training List)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getTradeunionList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getTradeunionList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 培训(Training List)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getTradeunionList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getTradeUnionList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getTradeUnionList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 司内培训(Training inside)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	@Override
	public List getTrainingInfoList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getTrainingInfoList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 培训(Training List)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getTrainingInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getTrainingInfoList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getTrainingInfoList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加培训信息(add training information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void addTrainingInfo(Object obj) throws Exception {
		this.insert("ess.empinfo.addTrainingInfo", obj);
	}

	/**
	 * 修改培训信息(Modify training information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editTrainingInfo(Object object) throws Exception {
		this.update("ess.empinfo.editTrainingInfo", object);
	}

	/**
	 * 删除培训信息(delete training information)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void deleteTrainingInfo(Object obj) throws Exception {
		this.delete("ess.empinfo.deleteTrainingInfo", obj);
	}

	/**
	 * 社会关系(social relations)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	@Override
	public List getFamilyList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getFamilyList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 社会关系(social relations)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getFamilyList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getFamilyList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("ess.empinfo.getFamilyList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加社会关系(add familyInfo)
	 */
	@Override
	public void addFamilyInfo(Object object) throws Exception {
		this.insert("ess.empinfo.insertFamilyInfo", object);

	}

	/**
	 * 删除社会关系(delete family information)
	 */
	@Override
	public void deleteFamilyInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteFamilyInfo", object);

	}

	/**
	 * 修改社会关系(Modify family information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editFamilyInfo(Object object) throws Exception {
		this.update("ess.empinfo.editFamilyInfo", object);
	}

	/**
	 * 添加地址信息(add familyInfo)
	 */
	@Override
	public void addAddressInfo(Object object) throws Exception {
		this.insert("ess.empinfo.insertAddressInfo", object);

	}

	/**
	 * 删除地址信息(delete family information)
	 */
	@Override
	public void deleteAddressInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteFamilyInfo", object);

	}

	/**
	 * 修改地址信息(Modify family information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editAddressInfo(Object object) throws Exception {
		this.update("ess.empinfo.editAddressInfo", object);
	}

	/**
	 * 家人关系(social relations)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getHomeRelationList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getHomeRelationList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 紧急联系地址
	 */
	@Override
	public List getEmergencyAddressList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getEmergencyAddressList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 添加紧急联系地址(add familyInfo)
	 */
	@Override
	public void addEmergencyAddressInfo(Object object) throws Exception {
		this.insert("ess.empinfo.addEmergencyAddressInfo", object);

	}

	/**
	 * 删除紧急联系地址(delete family information)
	 */
	@Override
	public void deleteEmergencyAddressInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteFamilyInfo", object);

	}

	/**
	 * 修改紧急联系地址(Modify family information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editEmergencyAddressInfo(Object object) throws Exception {
		this.update("ess.empinfo.editEmergencyAddressInfo", object);
	}

	@Override
	public List getAddressList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getAddressList(obj, -1, -1);

		return returnList;
	}
	
	@Override
	public Object getAddressInfo(Object obj) {
		Object returnList = new Object();
 try{
		returnList = this.queryForObject("ess.empinfo.getAddressInfo",obj);
 }catch (Exception e) {
	// TODO: handle exception
}
		return returnList;
	}

	/**
	 * 家人关系(social relations)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	private List getHomeRelationList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getHomeRelationList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getHomeRelationList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 紧急联系地址(social relations)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	private List getEmergencyAddressList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getEmergencyAddressList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getEmergencyAddressList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 地址信息
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	private List getAddressList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getAddressList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getAddressList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加家人关系(add familyInfo)
	 * 
	 * @throws SQLException
	 */
	@Override
	public void addHomeRelationInfo(Object object) throws Exception {

		this.insert("ess.empinfo.insertHomeRelationInfo", object);
	}

	/**
	 * 删除家人关系(delete homerelation information)
	 */
	@Override
	public void deleteHomeRelationInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteFamilyInfo", object);
	}

	/**
	 * 修改家人关系(Modify family information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public int editHomeRelation(Object object) throws Exception {
		this.insert("ess.empinfo.insertHomeRelationInfo", object);
		return 1;
	}

	/**
	 * 健康信息(Health information)
	 * 
	 * @return returnList
	 */
	@Override
	public List getHealthList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getHealthList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 健康信息(Health information)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getHealthList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getHealthList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("ess.empinfo.getHealthList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加健康信息(add health information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void addHealthInfo(Object object) throws Exception {
		this.insert("ess.empinfo.insertHealthInfo", object);

	}

	/**
	 * 修改健康信息(Modify health information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editHealthInfo(Object object) throws Exception {
		this.update("ess.empinfo.editHealthInfo", object);
	}

	/**
	 * 删除健康信息(delete health information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void deleteHealthInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteHealthInfo", object);
	}

	/**
	 * 工作经验(work experience)
	 * 
	 * @return returnList
	 */
	@Override
	public Object getWorkExperienceList(Object obj) {

		return this.getWorkExperienceList(obj, -1, -1);

	}

	/**
	 * 得到产品信息单个(work experience)
	 * 
	 * @return returnList
	 */
	@Override
	public Object getProductInfo(Object obj) {

		try {

			return this.queryForObject("ess.empinfo.getProductInfo", obj);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 得到产品信息多个(work experience)
	 * 
	 * @return returnList
	 */
	@Override
	public List getProductInfoList(Object obj) {

		try {

			return this.queryForList("ess.empinfo.getProductInfoList", obj);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 工作经验(work experience)
	 * 
	 * @return returnList
	 */
	@Override
	public Object getWorkExperienceList2(Object obj) {

		return this.getWorkExperienceList2(obj, -1, -1);

	}

	/**
	 * 工作经验(work experience)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public Object getWorkExperienceList(Object obj, int currentPage,
			int pageSize) {
		Object returnList = new Object();
		try {

			returnList = this.queryForObject(
					"ess.empinfo.getWorkExperienceList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 工作经验(work experience)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */

	public Object getWorkExperienceList2(Object obj, int currentPage,
			int pageSize) {
		Object returnList = new Object();
		try {

			returnList = this.queryForList(
					"ess.empinfo.getWorkExperienceList2", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加工作经历信息(add workExperience information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void addWorkExperienceInfo(Object object) throws Exception {
		this.insert("ess.empinfo.insertWorkExperienceInfo", object);
	}

	/**
	 * 修改工作经历信息(Modify workExperience information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editWorkExperienceInfo(Object object) throws Exception {
		this.update("ess.empinfo.editWorkExperienceInfo", object);
	}

	/**
	 * 删除工作经历(delete WorkExpreienceInfo)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void deleteWorkExpreienceInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteWorkExpreienceInfo", object);
	}

	/**
	 * 资格信息(Competence information)
	 * 
	 * @param obj
	 * @return returnList
	 */
	@Override
	public List getQualificationList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getQualificationList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 资格信息(单个)(Competence information)
	 * 
	 * @param obj
	 * @return returnList
	 */
	@Override
	public Object getQualificationInfo(Object obj) {
		try {

			return this.queryForObject("ess.empinfo.getQualificationInfo", obj);
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}
	}

	/**
	 * 资格信息(Competence information)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getQualificationList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getQualificationList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getQualificationList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 外国语(language level)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	@Override
	public List getLanguageLevelList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getLanguageLevelList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 外国语(language level)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getLanguageLevelList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getLanguageLevelList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getLanguageLevelList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加资格证书(add QualificationInfo)
	 * 
	 * @param object
	 * @throws Exception
	 */
	public void addQualificationInfo(Object object) throws Exception {
		this.insert("ess.empinfo.insertQualificationInfo", object);
	}

	/**
	 * 添加外国语(add LanguageLevelInfo)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void addLanguageLevelInfo(Object object) throws Exception {
		this.insert("ess.empinfo.insertLanguageLevelInfo", object);
	}

	/**
	 * 修改资格证书信息(Modify qualification certificate of the information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editQualificationInfo(Object object) throws Exception {
		this.update("ess.empinfo.editQualificationInfo", object);
	}

	/**
	 * 修改外国语信息(Modify foreign language information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editLanguageLevelInfo(Object object) throws Exception {
		this.update("ess.empinfo.editLanguageLevelInfo", object);
	}

	/**
	 * 删除资格证书(Delete qualification certificate)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void deleteQualicationInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteQualicationInfo", object);

	}

	/**
	 * 删除外国语(Delete foreign language)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void deleteLanguageLevelInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteLanguageLevelInfo", object);

	}

	/**
	 * 标签查询(QueryLabelInformationVolume)
	 * 
	 * @param parent_code_no
	 * @return retrunList
	 */
	@Override
	public List getCodeList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getCodeList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 特殊事项(Special matters)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	@Override
	public List getAdditionalList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getAdditionalList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 特殊事项(Special matters)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getAdditionalList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getAdditionalList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getAdditionalList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加特殊事项(add special matters)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void addAdditionalInfo(Object object) throws Exception {
		this.insert("ess.empinfo.addAdditionalInfo", object);
	}

	/**
	 * 修改特殊事项(Modify pecial matters)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editAdditionalInfo(Object object) throws Exception {
		this.update("ess.empinfo.editAdditionalInfo", object);
	}

	/**
	 * 删除特殊事项(Delete pecial matters)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void deleteAdditionalInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteAdditionalInfo", object);

	}

	/**
	 * 账户(account)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	@Override
	public List getAccountList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getAccountList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 账户(account)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getAccountList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getAccountList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getAccountList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 合同(contract)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	public List getContractList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getContractList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 合同(contract)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getContractList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getContractList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getContractList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 档案(file)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	@Override
	public List getFileList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getFileList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 档案(file)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getFileList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getFileList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getFileList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加档案(add file)
	 * 
	 * @param object
	 */
	@Override
	public void addFileInfo(Object object) throws Exception {
		this.insert("ess.empinfo.addFileInfo", object);
	}

	/**
	 * 修改档案(modify file)
	 * 
	 * @param object
	 */
	@Override
	public void editFileInfo(Object object) throws Exception {
		this.update("ess.empinfo.editFileInfo", object);
	}

	/**
	 * 删除档案(delete file)
	 * 
	 * @param object
	 */
	@Override
	public void deleteFileInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteFileInfo", object);
	}

	/**
	 * 出国信息(goabroad information)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	@Override
	public List getGoAbroadList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getGoAbroadList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 出国信息(goabroad information)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getGoAbroadList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getGoAbroadList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getGoAbroadList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加出国信息(add goabroad information)
	 * 
	 * @param object
	 */
	@Override
	public void addGoAbroadInfo(Object object) throws Exception {
		this.insert("ess.empinfo.addGoAbroadInfo", object);
	}

	/**
	 * 修改出国信息(modify goabroad information)
	 * 
	 * @param object
	 */
	@Override
	public void editGoAbroadInfo(Object object) throws Exception {
		this.update("ess.empinfo.editGoAbroadInfo", object);
	}

	/**
	 * 删除出国信息(delete goabroad information)
	 * 
	 * @param object
	 */
	@Override
	public void deleteGoAbroadInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteGoAbroadInfo", object);
	}

	/**
	 * 证照信息(credential information)
	 * 
	 * @param obj
	 * @return retrunList
	 */
	@Override
	public List getCredentialList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getCredentialList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 证照信息(credential information)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getCredentialList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getCredentialList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getCredentialList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加证照信息(add credential information)
	 * 
	 * @param object
	 */
	@Override
	public void addCredentialInfo(Object object) throws Exception {
		this.insert("ess.empinfo.addCredentialInfo", object);
	}

	/**
	 * 修改证照信息(modify credential information)
	 * 
	 * @param object
	 */
	@Override
	public void editCredentialInfo(Object object) throws Exception {
		this.update("ess.empinfo.editCredentialInfo", object);
	}

	/**
	 * 删除证照信息(delete credential information)
	 * 
	 * @param object
	 */
	@Override
	public void deleteCredentialInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteCredentialInfo", object);
	}

	/**
	 * 根据条件查询员工信息(According to the condition inquires the employee information)
	 * 
	 * @param object
	 * @return retrunList
	 */
	@Override
	public List getEmpIdList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getEmpIdList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 根据条件查询员工信息(According to the condition inquires the employee information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getEmpIdList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getEmpIdList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getEmpIdList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 根据条件查询员工信息数量(According to the condition inquires the employee information
	 * count)
	 * 
	 * @param List
	 * @return
	 */
	public int getEmpIdListCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getEmpIdListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 根据EMPID查询出人员信息条数(EMPID inquires according to the number of personnel
	 * article information)
	 * 
	 * @param List
	 * @return
	 */
	public int getPersonCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getEmpIdListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param object
	 * @return retrunList
	 */
	@Override
	public List getPidEidList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getPidEidList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param object
	 * @return retrunList
	 */
	@Override
	public List getPidEidListXiao(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getPidEidListXiao(obj, -1, -1);

		return returnList;
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param object
	 * @return retrunList
	 */
	@Override
	public List getPidEidList2(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getPidEidList2(obj, -1, -1);

		return returnList;
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getPidEidList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getPidEidList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("ess.empinfo.getPidEidList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getPidEidListXiao(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getPidEidListXiao",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getPidEidListXiao",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getPidEidList2(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getPidEidList2",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getPidEidList2",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 查询old(薪资)职级,号俸(Inquires the old (salary) rank, no pay)
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public List getOldPostGradeList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getOldPostGradeList",
					obj, currentPage, pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 查询代理职级(Inquires the agency rank)
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public List getPostGradeList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getPostGradeList", obj,
					currentPage, pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 修改个人信息(Edit Personal Information)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public void editPhotoPath(Object object) throws Exception {
		this.update("ess.empinfo.editPhotoPath", object);
	}

	/**
	 * 查询排序(Queried Result Ranking)
	 * 
	 * @param List
	 * @return
	 */
	public List getOrderParmList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getOrderParmList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取号俸列表
	 */
	public List getHaoFengList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.transferOrder.haoFengList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取号俸列表
	 */
	public List getMenuThirdListList(Map paramMap) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getMenuThirdListList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 根据法人获取公司人员信息 导出模板使用
	 */
	@Override
	public List getEmpListToModelExcel(Map paramMap) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getEmpListToModelExcel", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 查询ess tab菜单
	 */
	@Override
	public List getTabMenuListList(Map paramMap) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getTabMenuListList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getDisabilityinfoList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getDisabilityinfoList(obj, -1, -1);

		return returnList;
	}

	@Override
	public List getDisabilityinfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getDisabilityinfoList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getDisabilityinfoList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 添加残疾信息
	 */
	@Override
	public void addDisabledInfo(Object object) throws Exception {
		this.insert("ess.empinfo.insertDisabledInfo", object);

	}

	/**
	 * 删除残疾信息
	 */
	@Override
	public void deleteDisabledInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteDisabledInfo", object);

	}

	/**
	 * 修改残疾信息
	 */
	@Override
	public void editDisabledInfo(Object object) throws Exception {
		this.update("ess.empinfo.updateDisabledInfo", object);

	}

	/**
	 * 添加工会信息
	 * 
	 * @throws SQLException
	 */
	public void addTradeunionInfo(Map paramMap) throws Exception {
		this.insert("ess.empinfo.insertTradeunionInfo", paramMap);
	}

	/**
	 * 删除健康信息(delete tradeunion information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void deleteTradeunionInfo(Object object) throws Exception {
		this.delete("ess.empinfo.deleteTradeunionInfo", object);
	}

	/**
	 * 修改工会信息(Modify tradeunion information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editTradeunionInfo(Object object) throws Exception {
		this.update("ess.empinfo.editTradeunionInfo", object);
	}

	public List getPersonalInfoForInformation(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getPersonalInfoForInformation(obj, -1, -1);

		return returnList;
	}

	/**
	 * 员工信息查询(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	public List getPersonalInfoForInformation(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.contractInfo.getPersonalInfoForInformation",
						object, currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.contractInfo.getPersonalInfoForInformation",
						object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 员工信息查询总数(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	public Object getPersonalInfoForInformationCount(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.contractInfo.getPersonalInfoForInformationCount",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public int getdisabledCnt(Object object) {
		int temp = 0;
		try {
			temp = Integer.parseInt(this.queryForObject(
					"ess.empinfo.getdisabledCnt", object).toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 修改基本中员工残疾的状态
	 */
	@Override
	public void updateEmployeeDisabled(Object object) throws Exception {
		this.update("ess.empinfo.updateEmployeeDisabled", object);

	}

	/**
	 * 增删改工作经历时候修改基本中的司外工作经历时间
	 */
	@Override
	public void updateEmployeeWorkExperience(Object object) throws Exception {
		this.update("ess.empinfo.updateEmployeeWorkExperience", object);

	}

	/**
	 * 统计用户是否有最终学历
	 */
	@Override
	public int getFinalNum(Object object) {
		int temp = 0;
		try {
			temp = Integer.parseInt(this.queryForObject(
					"ess.empinfo.getFinalNum", object).toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 修改员工个人信息表最终学历信息
	 */
	@Override
	public void updataEduaction(Object object) throws Exception {
		this.update("ess.empinfo.updataEduaction", object);

	}

	@Override
	public List getRelevance(Object obj) {
		List returnList = new ArrayList();

		try {
			returnList = this.queryForList("ess.empinfo.getRelevance", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	@Override
	public int getEduactionY(Object object) {
		int temp = 0;
		try {
			temp = Integer.parseInt(this.queryForObject(
					"ess.empinfo.getEduactionY", object).toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public void addTestInfo(Object object) throws SQLException {
		this.insert("ess.empinfo.addTestInfo", object);
	}

	public String getJoinFlagByPersonId(Object object) throws SQLException {
		return this.queryForObject("ess.empinfo.getJoinFlagByPersonId", object)
				.toString();
	}

	public String getBadArchivesInfoSeq() {
		String str = null;
		try {
			str = ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getBadArchivesInfoSeq"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public void addBadArchivesInfo(Object object) throws SQLException {
		String ARCHIVES_NO = getBadArchivesInfoSeq();
		Map obj = (Map) object;
		obj.put("ARCHIVES_NO", ARCHIVES_NO);
		this.insert("ess.empinfo.insertBadArchivesInfo", obj);
		// 保存附件
		if (obj.get("FILE_NAME") != null
				&& !"".equals(StringUtil.checkNull(obj.get("FILE_NAME")))) {
			String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME"))
					.split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(
					";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j = 0; j < fileUrl.length; j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/"
							+ obj.get("CREATED_BY") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", obj.get("ARCHIVES_NO"));
					fileMap.put("APPLY_TYPE", "0");
					fileMap.put("CREATED_BY", obj.get("CREATED_BY"));
					this.insert("ess.infoApplyLeave.insertEssFile", fileMap);
				}
			}
		}
	}

	@Override
	public void deleteBadArchivesInfo(Object object) throws SQLException {
		this.delete("ess.empinfo.deleteBadArchivesInfo", object);
		this.delete("ess.empinfo.deleteBadArchivesFilesInfo", object);
	}

	@Override
	public void editBadArchivesInfo(Object object) throws SQLException {
		Map obj = (Map) object;
		this.update("ess.empinfo.updateBadArchivesInfo", object);
		// 保存附件
		if (obj.get("FILE_NAME") != null
				&& !"".equals(StringUtil.checkNull(obj.get("FILE_NAME")))) {
			String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME"))
					.split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(
					";");
			LinkedHashMap fileMap1 = new LinkedHashMap();
			fileMap1.put("APPLY_NO", obj.get("ID").toString());
			fileMap1.put("APPLY_TYPE", "0");
			this.insert("ess.infoApplyLeave.deleteEssFile", fileMap1);
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j = 0; j < fileUrl.length; j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/"
							+ obj.get("CREATED_BY") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", obj.get("ID"));
					fileMap.put("APPLY_TYPE", "0");
					fileMap.put("CREATED_BY", obj.get("CREATED_BY"));
					this.insert("ess.infoApplyLeave.insertEssFile", fileMap);
				}
			}
		}
	}

	@Override
	public List getBadArchivesList(Object object) throws SQLException {
		return this.getBadArchivesList(object, -1, -1);
	}

	@Override
	public List getBadArchivesList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getBadArchivesList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getBadArchivesList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getBadArchivesListCnt(Map paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getBadArchivesListCnt",
							paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List getEmpInfoList(Object object) throws SQLException {
		return this.getEmpInfoList(object, -1, -1);
	}

	@Override
	public List getEmpInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getEmpInfoList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getEmpInfoList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getEmpInfoListCnt(Object obj) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getEmpInfoListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List getAssistList(Object object) throws SQLException {
		return this.getAssistList(object, -1, -1);
	}

	@Override
	public List getAssistList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getAssistList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("ess.empinfo.getAssistList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getPerConversionList(Object object) throws SQLException {
		return this.getPerConversionList(object, -1, -1);
	}

	@Override
	public List getPerConversionList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getPerConversionList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getPerConversionList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getPerConversionListCnt(Object obj) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"ess.empinfo.getPerConversionListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public void addAssistInfo(Object object) throws SQLException {
		this.insert("ess.empinfo.insertAssistInfo", object);

	}

	@Override
	public void deleteAssistInfo(Object object) throws SQLException {
		this.delete("ess.empinfo.deleteAssistInfo", object);

	}

	@Override
	public void editAssistInfo(Object object) throws SQLException {
		this.update("ess.empinfo.updateAssistInfo", object);
	}

	/**
	 * 删除兼卖产品信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void deleteProductInfo(Object object) throws SQLException {
		this.delete("ess.empinfo.deleteProductInfo", object);
	}

	/**
	 * 更新兼卖产品信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void addProductInfo(Object object) throws SQLException {
		this.insert("ess.empinfo.addProductInfo", object);
	}

	@Override
	public List getPositionList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getPositionList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getDqmcList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getDqmcList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getDqmcListNew(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getDqmcListNew", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 员工信息查询(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getPersonalInfoByLeave(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("ess.empinfo.getPersonalInfoByLeave",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	/**
	 * 员工信息查询(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getPersonalInfoByLeaveApply(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"ess.empinfo.getPersonalInfoByLeaveApply", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	/**
	 * 员工信息查询(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getArchivesInfo(Map paramMap) {
		Object object = null;
		try {
			object = this.queryForObject("ess.empinfo.getArchivesInfo",
					paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 获取社员兼卖产品类型
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpProductList(Object object) {
		List temp = null;
		try {
			temp = this.queryForList("ess.empinfo.getEmpProductList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 根据person_id获取email
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmailByPersonId(Object object) {
		List temp = null;
		try {
			temp = this.queryForList("ess.empinfo.getEmailByPersonId", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 根据法人获取人员类型组
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpTypeGroup(Object object) {
		List temp = null;
		try {
			temp = this.queryForList("ess.empinfo.getEmpTypeGroup", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	public List getEmpInfoTempList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getEmpInfoTempList", object, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getEmpInfoTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getEmpInfoTempList(Object object) {
		// TODO Auto-generated method stub
		return this.getEmpInfoTempList(object, 1, 10);
	}

	public int getEmpInfoTempCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"ess.empinfo.getEmpInfoTempCnt", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int getEmpInfoTempErrCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getEmpInfoTempErrCnt",
							paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public String importEmpInfoTempListExcel(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			paramMap.put("message", "");
			this.insert("ess.empinfo.updateEmpInfoTempListExcel", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			e.printStackTrace();
		}
		return returnString;
	}

	/**
	 * 获取外国语导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempLanguageTempList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getTempLanguageTempList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getTempLanguageTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取外国语导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempLanguageTempList(Object object) {
		return this.getTempLanguageTempList(object, 1, 10);
	}

	/**
	 * 获取外国语记录导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong
	 * @version V1.0
	 */
	public int getTempLanguageTempCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getTempLanguageTempCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 获取出错的导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong
	 * @version V1.0
	 */
	public int getTempLanguageTempErrorCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getTempLanguageTempErrorCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importFromExcel(Object object) throws Exception {
		String returnString = "";
		try {
			LinkedHashMap paramMap = (LinkedHashMap) object;
			paramMap.put("message", "");
			this.insert("ess.empinfo.importFromExcel", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			throw e;
		}
		return returnString;
	}

	/**
	 * 获取残疾人信息记录导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong
	 * @version V1.0
	 */
	public int getTempDisabledTempCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getTempDisabledTempCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 获取出错的导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong
	 * @version V1.0
	 */
	public int getTempDisabledTempErrorCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getTempDisabledTempErrorCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 获取残疾证信息导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempDisabledTempList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getTempDisabledTempList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getTempDisabledTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取残疾人信息导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempDisabledTempList(Object object) {
		return this.getTempDisabledTempList(object, 1, 10);
	}

	/**
	 *资格证信息
	 */

	@Override
	public List getQualInfoTempList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getQualInfoTempList", object, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getQualInfoTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getQualInfoTempList(Object object) {
		return this.getQualInfoTempList(object, 1, 10);
	}

	@Override
	public int getQualInfoTempCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"ess.empinfo.getQualInfoTempCnt", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getQualInfoTempErrCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getQualInfoTempErrCnt",
							paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 兼卖信息
	 */

	@Override
	public List getProductInfoTempList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getProductInfoTempList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getProductInfoTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getProductInfoTempList(Object object) {
		return this.getProductInfoTempList(object, 1, 10);
	}

	@Override
	public int getProductInfoTempCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getProductInfoTempCnt",
							paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getProductInfoTempErrCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getProductInfoTempErrCnt",
							paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List getWorkExperienceInfoTempList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getWorkExperienceInfoTempList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getWorkExperienceInfoTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getWorkExperienceInfoTempList(Object object) {
		return this.getWorkExperienceInfoTempList(object, 1, 10);
	}

	@Override
	public int getWorkExperienceInfoTempCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getWorkExperienceInfoTempCnt",
							paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getWorkExperienceInfoTempErrCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"ess.empinfo.getWorkExperienceInfoTempErrCnt",
							paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List getEvsInfoTempList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getEvsInfoTempList", object, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getEvsInfoTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getEvsInfoTempList(Object object) {
		return this.getEvsInfoTempList(object, 1, 10);
	}

	@Override
	public int getEvsInfoTempErrCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getEvsInfoTempErrCnt",
							paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getEvsInfoTempCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"ess.empinfo.getEvsInfoTempCnt", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getTradeUnionInfoTempCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getTradeUnionInfoTempCnt",
							paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List getTradeUnionInfoTempList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getTradeUnionInfoTempList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getTradeUnionInfoTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getTradeUnionInfoTempList(Object object) {
		return this.getTradeUnionInfoTempList(object, 1, 10);
	}

	@Override
	public int getTradeUnionInfoTempErrCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getTradeUnionInfoTempErrCnt",
							paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 */
	@Override
	public String importInfoFromExcel(Object object) throws SQLException {

		String returnString = "";
		try {
			LinkedHashMap paramMap = (LinkedHashMap) object;
			paramMap.put("message", "");
			this.insert("ess.empinfo.importInfoFromExcel", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			throw e;
		}
		return returnString;
	}

	/**
	 * 获取培训导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTrainingImportTempList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getTrainingImportTempList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getTrainingImportTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取培训导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTrainingImportTempList(Object object) {
		return this.getTrainingImportTempList(object, 1, 10);
	}

	/**
	 * 获取出错的培训导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTrainingImportTempErrorCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"ess.empinfo.getTrainingImportTempErrorCnt",
													object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 获取培训导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTrainingImportTempCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getTrainingImportTempCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 人员信息mapping 新增
	 */
	@Override
	public void addEmpMapping(Object object) throws Exception {
		this.update("ess.empinfo.addEmpMapping", object);
	}

	/**
	 * 验证mapping的社号身份证号是否一致
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @date 2014-09-03
	 * @version V1.0
	 */
	public int validIdCard(Object object) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.validIdCard", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 验证mapping的社号身份证号是否一致
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @date 2014-09-03
	 * @version V1.0
	 */
	public int validIsDuplicate(Object object) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.validIsDuplicate", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 人员信息mapping 修改
	 */
	@Override
	public void updateEmpMapping(Object object) throws Exception {
		this.insert("ess.empinfo.updateEmpMapping", object);
	}

	/**
	 * 人员信息mapping 删除
	 */
	public void deleteEmpMapping(Object object) throws Exception {
		this.delete("ess.empinfo.deleteEmpMapping", object);
	}

	/**
	 * 人员信息mapping查询
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List viewEmpMappingList(Object object) throws SQLException {
		return this.viewEmpMappingList(object, -1, -1);
	}

	/**
	 * 人员信息mapping查询
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List viewEmpMappingList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.viewEmpMappingList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.viewEmpMappingList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 人员信息mapping查询 总数
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public int viewEmpMappingListCnt(Object obj) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.viewEmpMappingCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 人员信息mapping查询 BY NO
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpMappingByNo(Object obj) throws SQLException {

		return this.queryForList("ess.empinfo.getEmpMappingByNo", obj);
	}

	/**
	 * 获取工作经历导入信息
	 */
	@Override
	public List getworkExperienceTempList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getWorkExperienceInfoTempList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getWorkExperienceInfoTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 外国语数据从临时表导入到正式表
	 * 
	 * 数据的验证
	 */
	@SuppressWarnings("unchecked")
	public String importLanguageExcel(Object object) throws Exception {
		String returnString = "";
		try {
			LinkedHashMap paramMap = (LinkedHashMap) object;
			paramMap.put("message", "");
			this.insert("ess.empinfo.importLanguageExcel", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			throw e;
		}
		return returnString;
	}

	/**
	 * 获取紧急联系人信息导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @date 2014-09-03
	 * @version V1.0
	 */

	public int getTempContactTempCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"ess.empinfo.getTempContactTempCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 获取发生错误紧急联系人信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @date 2014-09-03
	 * @version V1.0
	 */
	public int getTempContactTempErrorCnt(Object object) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getTempContactTempErrorCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 获取紧急联系人信息导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempContactTempList(Object object, int pageNum,
			int numPerPage) {
		List returnList = new ArrayList();
		try {
			if (pageNum > -1 && numPerPage > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getTempContactTempList", object, pageNum,
						numPerPage);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getTempContactTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取辅助信息导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempContactTempList(Object object) {
		return this.getTempContactTempList(object, 1, 10);
	}

	/**
	 * 获取辅助信息导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @date 2014-09-03
	 * @version V1.0
	 */
	public int getTempAssistTempCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"ess.empinfo.getTempAssistTempCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 获取发生错误辅助信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @date 2014-09-03
	 * @version V1.0
	 */
	public int getTempAssistTempErrorCnt(Object object) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getTempAssistTempErrorCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 获取辅助信息导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempAssistTempList(Object object, int pageNum, int numPerPage) {
		List returnList = new ArrayList();
		try {
			if (pageNum > -1 && numPerPage > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getTempAssistTempList", object, pageNum,
						numPerPage);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getTempAssistTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取辅助信息导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempAssistTempList(Object object) {
		return this.getTempAssistTempList(object, 1, 10);
	}

	/**
	 * 基本数据信息(复制信息、残疾证、紧急联系人)数据从临时表导入到正式表
	 * 
	 * 数据的验证
	 */
	@SuppressWarnings("unchecked")
	public String importInfoExcel(Object object) throws Exception {
		String returnString = "";
		try {
			LinkedHashMap paramMap = (LinkedHashMap) object;
			paramMap.put("message", "");
			this.insert("ess.empinfo.importInfoExcel", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			throw e;
		}
		return returnString;
	}

	@Override
	public List getEmpTypeList(Map paramMap) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getEmpTypeList",
					paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getTempEmpInfoList(Map obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getTempEmpInfoList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getTempEmpInfoList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getTempEmpInfoList(Map paramMap) {
		return this.getTempEmpInfoList(paramMap, -1, -1);
	}

	@Override
	public Object getTempEmpInfoListCnt(Map paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getTempEmpInfoListCnt",
							paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 临时职批量入职列表
	 */
	@Override
	public List getTempEmpAffirmInfoListBatch(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getTempEmpAffirmInfoListBatch(obj, -1, -1);
		return returnList;
	}

	@Override
	public List getTempEmpAffirmInfoListBatch(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getTempEmpAffirmInfoListBatch", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getTempEmpAffirmInfoListBatch", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getTempEmpAffirmInfoListCntBatch(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils
				.toString(this.queryForObject(
						"ess.empinfo.getTempEmpAffirmInfoCntBatch", obj)),
				Integer.class);
	}

	@Override
	public List getImportTmpEmpResultList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getImportTmpEmpResultList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getImportTmpEmpResultList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getImportTmpEmpResultList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getImportTmpEmpResultList(obj, -1, -1);

		return returnList;
	}

	@Override
	public int getImportTmpEmpResultCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"ess.empinfo.getImportTmpEmpResultCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@Override
	public int getImportTmpEmpErrCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getImportTmpEmpErrCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	public String importTmpEmpFromExcel(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			paramMap.put("message", "");
			this.insert("ess.empinfo.importTmpEmpFromExcel", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			e.printStackTrace();
		}
		return returnString;
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	public int delTmpEmpInBatch(LinkedHashMap paramMap) {
		int ret = 0;
		try {
			paramMap.put("message", "");
			this.insert("ess.empinfo.delTmpEmpInBatch", paramMap);
			ret = NumberUtils.parseNumber(paramMap.get("RET").toString(),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public List getTempEmpBatchReqList(Object obj) throws Exception {
		return this.getTempEmpBatchReqList(obj, -1, -1);
	}

	@Override
	public List getTempEmpBatchReqList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getTempEmpBatchReqList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getTempEmpBatchReqList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getTempEmpBatchReqCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getTempEmpBatchReqCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	public Object getTempEmpReqDetail(Object obj) {
		Object returnObj = new Object();
		try {
			returnObj = this.queryForObject("ess.empinfo.getTempEmpReqDetail",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	/*
	 * Title: getInsrareaForCpnyId Description:查询法人人员所用的所有福利地区
	 * 
	 * @author 孙鹏
	 * 
	 * @date 2015年3月16日 下午4:25:58
	 * 
	 * @param paramMap
	 * 
	 * @return
	 * 
	 * @see
	 * com.ait.hrm.dao.EmpInfoDao#getInsrareaForCpnyId(java.util.LinkedHashMap)
	 */
	@Override
	public Object getInsrareaForCpnyId(LinkedHashMap paramMap) {
		Object returnObj = new Object();
		try {
			returnObj = this.queryForList("ess.empinfo.getInsrareaForCpnyId",
					paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	public int getEmpIdCardNoCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getEmpIdCardNoCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 获取新旧社号mapping批量导入信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getMappingTempList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getMappingTempList(object, -1, -1);
		return returnList;
	}

	/**
	 * 获取新旧社号mapping批量导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getMappingTempList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.empinfo.getMappingTempList", object, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ess.empinfo.getMappingTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取新旧社号mapping批量导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getMappingTempCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getMappingTempCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 获取出错的新旧社号mapping批量导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getMappingTempErrorCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getMappingTempErrorCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 工作经历(social relations)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List getWorkInfo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getWorkExperienceList",
					obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	/**
	 * 添加工作经历(add familyInfo)
	 * 
	 * @throws SQLException
	 */
	@Override
	public void essAddWorkInfo(Object object) throws Exception {

		this.insert("ess.empinfo.essAddWorkInfo", object);
	}

	/**
	 * 添加产品信息(add familyInfo)
	 * 
	 * @throws SQLException
	 */
	@Override
	public void essAddProductInfo(Object object) throws Exception {

		this.insert("ess.empinfo.essAddProductInfo", object);
	}

	/**
	 * 添加产学历信息(add familyInfo)
	 * 
	 * @throws SQLException
	 */
	@Override
	public void essAddEducationInfo(Object object) throws Exception {

		this.insert("ess.empinfo.essAddEducationInfo", object);
	}

	/**
	 * 添加奖章信息(add familyInfo)
	 * 
	 * @throws SQLException
	 */
	@Override
	public void essAddRewardInfo(Object object) throws Exception {

		this.insert("ess.empinfo.essAddRewardInfo", object);
	}

	/**
	 * 添加个人修正申请(add familyInfo)
	 * 
	 * @throws SQLException
	 */
	@Override
	public void addPersonal(Object object) throws Exception {

		this.insert("ess.empinfo.addPersonal", object);
	}

	/**
	 * 添加资格历信息(add familyInfo)
	 * 
	 * @throws SQLException
	 */
	@Override
	public void essAddQualificationInfo(Object object) throws Exception {

		this.insert("ess.empinfo.essAddQualificationInfo", object);
	}

	/**
	 * 得到学历信息单个(work experience)
	 * 
	 * @return returnList
	 */
	@Override
	public Object getEducationInfo(Object obj) {

		try {

			return this.queryForObject("ess.empinfo.getEducationInfo", obj);
		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * 变更明细申请
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List getPersonalApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getPersonalApplyList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("查询失败");
		}
		return returnList;
	}

	@Override
	public int getEssApplyListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.empinfo.getEssApplyListCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssApplyList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.empinfo.getEssApplyList",
						object, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.empinfo.getEssApplyList",
						object);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssApplyList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getEssApplyList(object, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssAddressApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"ess.empinfo.getEssAddressApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssEmergencyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getEssEmergencyList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssHomeRelationApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"ess.empinfo.getEssHomeRelationApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssWorkApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getEssWorkApplyList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssProductApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"ess.empinfo.getEssProductApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssEducationApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"ess.empinfo.getEssEducationApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssQualificationApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"ess.empinfo.getEssQualificationApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssRewardList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getEssRewardList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	/*
	 * end list
	 */

	/*
	 * 变更明细申请单个Object start
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonalApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"ess.empinfo.getPersonalApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssAddressApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"ess.empinfo.getEssAddressApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssEmergencyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject("ess.empinfo.getEssEmergencyList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssHomeRelationApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"ess.empinfo.getEssHomeRelationApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssWorkApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject("ess.empinfo.getEssWorkApplyList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssProductApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"ess.empinfo.getEssProductApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssEducationApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"ess.empinfo.getEssEducationApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssQualificationApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"ess.empinfo.getEssQualificationApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssRewardObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject("ess.empinfo.getEssRewardList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	/*
	 * end list
	 */
	/**
	 * 个人照片修改上传申请
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addRecruitInfo(Object object) throws Exception {
		Map obj = (Map) object;
		if (obj.get("currentIndex") != null) {
			// 基本信息
			if ("5".equals(StringUtil.checkNull(obj.get("currentIndex")))) {
				this.update("ess.empinfo.updatePhotoPathInfo", object);
			}
		}
		return 1;
	}
	
	public List viewInfoList(Object object, String target) {
		List result = null;
		try {
			result = this.queryForList("ess.empinfo."+target,object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}

}
