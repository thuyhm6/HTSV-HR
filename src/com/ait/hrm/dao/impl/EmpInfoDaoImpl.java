package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.hrm.dao.EmpInfoDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;
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
public class EmpInfoDaoImpl extends SqlMapClientSupport implements EmpInfoDao {
	public List getEmpList(Object object) {
		List list = new ArrayList();
		try {
			list = this.queryForList("hrm.empinfo.getEmpList", object, Integer
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
					"hrm.empinfo.getEmpCnt", object).toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	public Object getBasicInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("hrm.empinfo.getBasicInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	public int getContractCnt(Object object) {
		int temp = 0;
		try {
			temp = Integer.parseInt(this.queryForObject(
					"hrm.empinfo.getContractCnt", object).toString());
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
			// object2 = this.queryForGridList("hrm.empinfo.getPaEmpInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getPaEmpInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getPaEmpInfoCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getSinfo", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getSinfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getSinfoCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getBizlist", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getBizlist", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getBizlistCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getAppendInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getAppendInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getAppendInfoCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getResignation",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getResignation", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getResignationCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getITLevelInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getITLevelInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getITLevelInfoCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getLanuageInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getLanuageInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getLanuageInfoCnt", object);
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
			// this.queryForGridList("hrm.empinfo.getQualificationInfo", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getQualificationInfo",
					object, skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getQualificationInfoCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getEval", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getEval", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getEvalCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getEvaluate",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getEvaluate", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getEvaluateCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getHealthInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getHealthInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getHealthInfoCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getExpInside",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getExpInside", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getExpInsideCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getFamilyInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getFamilyInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getFamilyInfoCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getSocietyRelation",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getSocietyRelation",
					object, skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getSocietyRelationCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getPunishMent",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getPunishMent", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getPunishMentCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getReward", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getReward", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getRewardCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getEdu", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getEdu", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getEduCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getDispatch",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getDispatch", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getDispatchCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getPlurality",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getPlurality", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getPluralityCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getSuspend", object,
			// skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getSuspend", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getSuspendCnt", object);
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
			// object2 = this.queryForGridList("hrm.empinfo.getExperienceInfo",
			// object, skipResults,maxResults);
			List Rows = this.queryForList("hrm.empinfo.getExperienceInfo",
					object, skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.empinfo.getExperienceInfoCnt", object);
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
			List Rows = this.queryForList("hrm.empinfo.getSysCode", object);
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
		this.update("hrm.empinfo.updateFamilyInfo", object);

	}

	@Override
	public void addExperienceInfo(Object object) throws Exception {
		this.insert("hrm.empinfo.insertExperienceInfo", object);

	}

	@Override
	public void addFappendInfo(Object object) throws Exception {
		this.insert("hrm.empinfo.insertAdditionalInfo", object);

	}

	/**
	 *外国语信息添加
	 */
	@Override
	public void addLanuageInfo(Object object) throws Exception {
		this.insert("hrm.empinfo.insertLanguageInfo", object);

	}

	/**
	 *帐户信息添加
	 */
	@Override
	public void addPaEmpInfo(Object object) throws Exception {
		this.insert("hrm.empinfo.insertAccountsInfo", object);

	}

	/**
	 *社会关系添加
	 */
	@Override
	public void addSocietyRelation(Object object) throws Exception {
		this.insert("hrm.empinfo.insertFamilyInfo", object);

	}

	@Override
	public void deleteExperienceInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteExperienceInfo", object);

	}

	@Override
	public void deleteFappendInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteAdditionalInfo", object);

	}

	@Override
	public void deleteLanuageInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteLanguageInfo", object);

	}

	@Override
	public void deletePaEmpInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteAccountsInfo", object);

	}

	@Override
	public void deleteQualificationInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteQualificationInfo", object);

	}

	@Override
	public void deleteSocietyRelation(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteFamilyInfo", object);

	}

	@Override
	public void updateExperienceInfo(Object object) throws Exception {
		this.update("hrm.empinfo.updateExperienceInfo", object);

	}

	@Override
	public void updateFappendInfo(Object object) throws Exception {
		this.update("hrm.empinfo.updateAdditionalInfo", object);

	}

	@Override
	public void updateHealthInfo(Object object) throws Exception {
		this.update("hrm.empinfo.updateHealthInfo", object);

	}

	@Override
	public void updateLanuageInfo(Object object) throws Exception {
		this.update("hrm.empinfo.updateLanguageInfo", object);

	}

	@Override
	public void updatePaEmpInfo(Object object) throws Exception {
		this.update("hrm.empinfo.updateAccountsInfo", object);

	}

	@Override
	public void updateQualificationInfo(Object object) throws Exception {
		this.update("hrm.empinfo.updateQualificationInfo", object);

	}

	@Override
	public void updateSocietyRelation(Object object) throws Exception {
		this.update("hrm.empinfo.updateFamilyInfo", object);

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
				returnList = this.queryForList("hrm.empinfo.getPersonalList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getPersonalList",
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
					.queryForObject("hrm.empinfo.getPersonalCnt", obj)),
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
					.queryForObject("hrm.empinfo.getPersonalInfo", object);
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
					"hrm.empinfo.getPersonalInfoByPidNew", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 * 转正员工信息导出(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getPersonalInfoByPid2(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.contractInfo.daochuBecomeRegularWarn", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object getTitle(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.getTitle", object);
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
	public Object SinglePersonalInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.SinglePersonalInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	
	
	
	/**
	 * 最终学历查询(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getfinaEdu(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.getfinaEdu", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 * 基本信息页面的employee(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewEmpInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewEmpInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 * 复制地址
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public String copyAddress(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"hrm.empinfo.copyAddress", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 发令区分和发令原因联动
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List codeReason(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.codeReason",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 部门查询
	 * @param obj
	 * @return object2
	 */
	@Override
	public List queryDepartment(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.queryDepartment",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	public List querydepartPrep(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.querydepartPrep",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 搜索标题名称
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List searchTitlename(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.searchTitlename",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	
	
	/**
	 * 紧急联系人(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List gethrEmergencyAddressList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.gethrEmergencyAddressList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 地址类型(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewAddressMattersList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.gethrAddressMattersLists",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List searchTanchu(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.searchTanchu",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List zhiji(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.zhiji",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List employeeSearchResultsTanchu(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.employeeSearchResultsTanchu",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public String querySearchContent(Object object) {
		String str=null;
		try {
			Map obj = (Map)this.queryForObject("hrm.empinfo.querySearchContent",object);
			if(obj != null){
				CLOB clob = (CLOB)obj.get("CONTENT");
				str = clob.getSubString(1, (int) clob.length());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List searchname(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.searchname",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List chengbenzhongxin(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.chengbenzhongxin",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 家庭关系(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewFamilyList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.gethrFamilyList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 地址类型(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List gethrAddressMattersLists(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.gethrAddressMattersLists",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 家庭关系(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List gethrFamilyList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.gethrFamilyList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getStatisticsBureau(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getStatisticsBureau",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getHRDaily(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getHRDaily",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getPostStores(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getPostStores",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List BirthdayWelfare(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.BirthdayWelfare",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List RecruitReport(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.RecruitReport",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List RuZhiEmployee(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.RuZhiEmployee",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List LiZhiEmployee(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.LiZhiEmployee",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List RuZhiStatistic(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.RuZhiStatistic",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List LiZhiStatistic(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.LiZhiStatistic",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getLaborDispatch(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getLaborDispatch",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getResident(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getResident",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getWorkArea(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getWorkArea",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getCaiWu(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getCaiWu",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getWorkPerson(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getWorkPerson",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getShangYe(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getShangYe",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getCheJian(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getCheJian",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getIndividualIncomeTax(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getIndividualIncomeTax",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getCount(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getCount",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getCountWages(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getCountWages",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getCOntractDaoqi(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getCOntractDaoqi",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getPersonalInfo1(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getPersonalInfo1",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getBanGongShi(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getBanGongShi",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getTC(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getTC",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getDongyuanDian(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getDongyuanDian",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getNinghaiDian(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getNinghaiDian",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getShiguDian(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getShiguDian",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getHongYueCheng(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getHongYueCheng",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getLongJiang(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getLongJiang",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getZhuJiang(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getZhuJiang",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getHuNan(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getHuNan",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getHuDengFang(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getHuDengFang",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getQingJiang(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getQingJiang",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getWanDa(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getWanDa",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getBingRunHui(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getBingRunHui",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getCenterShopping(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getCenterShopping",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getJingFeng(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getJingFeng",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getSunCity(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getSunCity",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getMaoYe(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getMaoYe",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getHuanQiuGang(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getHuanQiuGang",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getNewCentury(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getNewCentury",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getChangFa(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getChangFa",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getBaoLong(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getBaoLong",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getJiuZhou(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getJiuZhou",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getCStatistics(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getCStatistics",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getLastWeek(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getLastWeekC",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List XingbXuelMinz(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.XingbXuelMinz",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List AgeStatus(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.AgeStatus",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List WorkStatus(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.WorkStatus",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getJishiDingban(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getJishiDingban",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getCZJishiDingban(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getCZJishiDingban",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List getHuKou(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getHuKou",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 发令表(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List getStartPointList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getStartPointList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	@Override
	public List getStartPointList1(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getStartPointList1",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 工作经历表(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List getExperiencePointList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getExperiencePointList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 学历表(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewEducationMatter(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewEducationMatter",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 资格表(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewBidMatter(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewBidMatter",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	/**
	 * 工资卡号
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List getAccountInfo(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getAccountInfo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	/**
	 * 评价信息表(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewEvaluateInfo(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewEvaluateInfo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 语言表(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewForeignLanguage(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewForeignLanguage",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	
	/**
	 * 培训信息(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewTrainingBasic(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewTrainingBasic",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 表彰表(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewRecognition(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewRecognition",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 惩戒表(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewPunishment(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewPunishment",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List viewSingleTrain(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewSingleTrain",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List viewTrain(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewTrain",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	
	@Override
	public List viewEvaInformation(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewEvaInformation",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	/**
	 * 特记事项表(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewSpecialMatter(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewSpecialMatter",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 护照签证表(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewPassportPerson(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewPassportPerson",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewEmployeePhoto(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewEmployeePhoto",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List viewEmployeePhotoInfo(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewEmployeePhotoInfo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int deleteEmployeePhotoInfo(Object object) throws Exception {
	
		try {
			this.update("hrm.empinfo.deleteEmployeePhotoInfo",object);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	/**
	 * 紧急联系人信息搜素(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List emergencyAddress(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.emergencyAddress",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 家庭搜索(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List familySearch(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.familySearch",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 经历搜索(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List experienceSearch(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.experienceSearch",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 学历搜索(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List educationSearch(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.educationSearch",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 资格搜索(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List bidSearch(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.bidSearch",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 职级搜索(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List gradeSearch(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.gradeSearch",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 地址搜索(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List addressSearch(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.addressSearch",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 表彰搜索(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List recognitionSearch(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.recognitionSearch",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 惩罚搜索(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List punishmentSearch(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.punishmentSearch",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 退职搜索(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List retireSearch(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.retireSearch",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 护照签证表(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewPassportPersonMain(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewPassportPersonMain",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * 基本信息页面的hr_personal_info(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewHrPersonalInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewHrPersonalInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 * 获取Supervisor履历信息
	 */
	public List viewSupervisorInfoList(Object object) {
		List result = null;
		try {
			result = this.queryForList("hrm.empinfo.viewSupervisorInfoList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 紧急联系人单一的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSingleHrEmergencyAddress(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSingleHrEmergencyAddress", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 * 地址类型单一的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSingleAddressMatters(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSingleAddressMatters", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 *家庭关系单一的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSingleFamily(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSingleFamily", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 *经历事项的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSingleExperiencePoint(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSingleExperiencePoint", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 *学历事项的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSingleEducationMatter(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSingleEducationMatter", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 *资格事项的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSingleBidMatter(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSingleBidMatter", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	/**
	 *评价信息的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSingleEvaluateInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSingleEvaluateInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	
	/**
	 *外语能力的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List viewSingleForeignLanguage(Object object) {
		List list = null;
		try {
			list = this.queryForList(
					"hrm.empinfo.viewSingleForeignLanguage", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 *外语能力的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSingleForeignLanguage1(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSingleForeignLanguage1", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	/**
	 *培训信息的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSingleTrainingBasic(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSingleTrainingBasic", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	
	/**
	 *表彰事项的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSingleRecognition(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSingleRecognition", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 *表彰事项的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSinglePunishment(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSinglePunishment", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 *特记事项的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSingleSpecialMatter(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSingleSpecialMatter", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 *护照信息的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSinglePassportPerson(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSinglePassportPerson", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 *护照信息的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object informationSearchMain(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.informationSearchMain", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 *发令事项单一的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSingleStartPoint(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSingleStartPoint", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	/**
	 *
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object addStartPointEmployee(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.addStartPointEmployee", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object querydepartNo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.querydepartNo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object querydepartNo_prep(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.querydepartNo_prep", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object viewPrepdepart(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewPrepdepart", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	/**
	 * 紧急联系人的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int gethrEmergencyAddressList_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.gethrEmergencyAddressList_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 地址类型的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int getviewFamilyList_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.getviewFamilyList_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 家庭关系的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int gethrviewAddressMattersList_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.gethrviewAddressMattersList_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 发令事项的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int getStartPointList_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.getStartPointList_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 经历事项的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int getExperiencePointList_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.getExperiencePointList_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 资格事项的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int viewBidMatter_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.viewBidMatter_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	/**
	 * 培训信息的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int viewEvaluateInfo_count(Object obj) {
		int returnInt = 0;
		
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.viewEvaluateInfo_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnInt;
	}
	
	/**
	 * 外语能力的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int viewForeignLanguage_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.viewForeignLanguage_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	
	
	/**
	 * 培训信息的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int viewTrainingBasic_count(Object obj) {
		int returnInt = 0;
		
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.viewTrainingBasic_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnInt;
	}
	
	/**
	 * 表彰事项的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int viewRecognition_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.viewRecognition_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 惩戒事项的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int viewPunishment_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.viewPunishment_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 特记事项的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int viewSpecialMatter_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.viewSpecialMatter_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 护照信息的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int viewPassportPerson_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.viewPassportPerson_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 学历事项的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int viewEducationMatter_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.viewEducationMatter_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	/**
	 * 基本信息页面的hr_emergency_address(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewEmergencyAddress(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewEmergencyAddress", object);
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
			object2 = this.queryForList("hrm.empinfo.getPersonalInfoByPid1",
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
				returnList = this.queryForList("hrm.empinfo.getEducationList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getEducationList",
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

		this.insert("hrm.empinfo.insertEducation", obj);

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

		this.delete("hrm.empinfo.deleteEducation", obj);

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
		this.update("hrm.empinfo.updateEmployeeInfo", object);
		// ------------------执行updateEmployeeInfo--------------------
		this.update("hrm.empinfo.updatePersonalInfo", object);
		// ------------------执行updatePersonalInfo--------------------
		this.update("hrm.empinfo.updateEmpPaInfo", object);
		// ------------------执行updatePAInfo--------------------
	}
	
	
	@Override
	public void editEmpInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editEmpInfo", object);
		this.update("recruit.manage.editEmployeeInfo", object);
		int i = (Integer) this.queryForObject("hrm.empinfo.selectAccountCnt", object);
		//if(i>0){
		//	this.update("hrm.empinfo.updateAccount", object);
		//}else{
		//	this.insert("hrm.empinfo.insertAccount", object);
		//}
		
		// ------------------执行updateEmployeeInfo--------------------
		//this.insert("hrm.contractInfo.updateContractInfo", object);
		//this.insert("hrm.contractInfo.insertChangeContractInfo", object);
	}
	
	@Override
	public void editEmpInfo_TSTO(Object object) throws Exception {
		this.update("hrm.empinfo.editEmpInfo_TSTO", object);
		// ------------------执行updateEmployeeInfo--------------------
	}
	@Override
	public void editHrPersonInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editHrEmployee", object);
		// ------------------执行updateEmployee--------------------
		this.update("hrm.empinfo.editHrPersonalInfo", object);
		// ------------------执行updateHrPersonalInfo--------------------
		//this.update("hrm.empinfo.editHrAddressMatterssXinzhuzhi", object);
		// ------------------执行updateHrAddressMatterss现住址内容--------------------
		//this.update("hrm.empinfo.editHrAddressMatterssYuanjidizhi", object);
		// ------------------执行updateHrAddressMatterss原籍地址内容--------------------
		//this.update("hrm.empinfo.editHrAddressMatterssHujidizhi", object);
		// ------------------执行updateHrAddressMatterss户籍地址内容--------------------
	}
	
	@Override
	public void deleteHrEmergencyAddress(Object object) throws Exception {
		this.update("hrm.empinfo.deleteHrEmergencyAddress", object);
		// ------------------执行updateHrEmergencyAddress--------------------
	}
	
	@Override
	public void deleteHrFamily(Object object) throws Exception {
		this.update("hrm.empinfo.deleteHrFamily", object);
		// ------------------执行updateHrHrFamily--------------------
	}
	
	@Override
	public void deleteStartPoint(Object object) throws Exception {
		this.update("hrm.empinfo.deleteStartPoint", object);
		// ------------------执行updateHrHrFamily--------------------
	}
	
	@Override
	public void updateActivity(Object object) throws Exception {
		this.update("hrm.empinfo.updateActivity", object);
		//this.update("hrm.empinfo.updateActivity2", object);
		// ------------------执行updateHrHrFamily--------------------
	}
	
	public Object queryMaxCreateDate(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("hrm.empinfo.queryMaxCreateDate", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public void deleteExperiencePoint(Object object) throws Exception {
		this.update("hrm.empinfo.deleteExperiencePoint", object);
		// ------------------执行updateHrWorkExperience--------------------
	}
	
	@Override
	public void deleteEducationMatter(Object object) throws Exception {
		this.update("hrm.empinfo.deleteEducationMatter", object);
		// ------------------执行updateHrEducation--------------------
	}
	
	@Override
	public void deleteBidMatter(Object object) throws Exception {
		this.update("hrm.empinfo.deleteBidMatter", object);
		// ------------------执行updateHrEducation--------------------
	}
	
	@Override
	public void deleteEvaluateInfo(Object object) throws Exception {
		this.update("hrm.empinfo.deleteEvaluateInfo", object);
		// ------------------执行updateHrEducation--------------------
	}
	
	@Override
	public void deleteTrainingBasic(Object object) throws Exception {
		this.update("hrm.empinfo.deleteTrainingBasic", object);
		// ------------------执行updateHrEducation--------------------
	}
	
	@Override
	public void deleteForeignLanguage(Object object) throws Exception {
		this.update("hrm.empinfo.deleteForeignLanguage", object);
		// ------------------执行updateHrEducation--------------------
	}
	
	@Override
	public void deleteRecognition(Object object) throws Exception {
		this.update("hrm.empinfo.deleteRecognition", object);
		// ------------------执行updateHrEducation--------------------
	}
	
	@Override
	public void deletePunishment(Object object) throws Exception {
		this.update("hrm.empinfo.deletePunishment", object);
		// ------------------执行updateHrEducation--------------------
	}
	
	@Override
	public void deleteSpecialMatter(Object object) throws Exception {
		this.update("hrm.empinfo.deleteSpecialMatter", object);
		// ------------------执行updateHrEducation--------------------
	}
	
	@Override
	public void deletePassportPerson(Object object) throws Exception {
		this.update("hrm.empinfo.deletePassportPerson", object);
		// ------------------执行updateHrEducation--------------------
	}
	
	@Override
	public void deleteTitlename(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteTitlename", object);
	}
	
	@Override
	public void deleteSearchname(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteSearchname", object);
	}
	@Override
	public void deleteHrAddressMatters(Object object) throws Exception {
		this.update("hrm.empinfo.deleteHrAddressMatters", object);
		// ------------------执行updateHrAddressMatters--------------------
	}
	
	@Override
	public void editHrEmergencyAddress(Object object) throws Exception {
		
		this.update("hrm.empinfo.editHrEmergencyAddress", object);
		// ------------------执行updateHrEmergencyAddress--------------------
	}
	
	@Override
	public void editHrFamily(Object object) throws Exception {
		
		this.update("hrm.empinfo.editHrFamily", object);
		// ------------------执行updateHrFamily--------------------
	}
	
	@Override
	public void editExperiencePoint(Object object) throws Exception {
		
		this.update("hrm.empinfo.editExperiencePoint", object);
		// ------------------执行updateHrWorkExperience--------------------
	}
	
	@Override
	public void editEducationMatter(Object object) throws Exception {
		
		this.update("hrm.empinfo.editEducationMatter", object);
		// ------------------执行updateHrWorkExperience--------------------
	}
	
	@Override
	public void editRecognition(Object object) throws Exception {
		
		this.update("hrm.empinfo.editRecognition", object);
		// ------------------执行updateHrWorkExperience--------------------
	}
	
	@Override
	public void editPunishment(Object object) throws Exception {
		
		this.update("hrm.empinfo.editPunishment", object);
		// ------------------执行updateHrWorkExperience--------------------
	}
	
	@Override
	public void editSpecialMatter(Object object) throws Exception {
		
		this.update("hrm.empinfo.editSpecialMatter", object);
		// ------------------执行updatehr_special_matter--------------------
	}
	
	@Override
	public void editPassportPerson(Object object) throws Exception {
		
		this.update("hrm.empinfo.editPassportPerson", object);
		// ------------------执行updatehr_special_matter--------------------
	}
	
	@Override
	public void editBidMatter(Object object) throws Exception {
		
		this.update("hrm.empinfo.editBidMatter", object);
		// ------------------执行updateHrWorkExperience--------------------
	}
	@Override
	public void editEvaluateInfo(Object object) throws Exception {
		
		this.update("hrm.empinfo.editEvaluateInfo", object);
		// ------------------执行updateHrWorkExperience--------------------
	}
	@Override
	public void editTrainingBasic(Object object) throws Exception {
		
		this.update("hrm.empinfo.editTrainingBasic", object);
		// ------------------执行updateHrWorkExperience--------------------
	}
	
	@Override
	public void editForeignLanguage(Object object) throws Exception {
		
		this.update("hrm.empinfo.editForeignLanguage", object);
		// ------------------执行updateHrLanguageLevel--------------------
	}
	
	@Override
	public void updatePersonInfoPhoto(Object object) throws Exception {
		
		this.update("hrm.empinfo.updatePersonInfoPhoto", object);
		// ------------------执行updateHrLanguageLevel--------------------
	}
	
	@Override
	public void updateEssFile(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateEssFile", object);
		// ------------------执行updateHrLanguageLevel--------------------
	}
	
	@Override
	public void editStartPoint(Object object) throws Exception {
		Map obj = (Map)object;
		//试用期转正 修改试用期结束日期
		if("400422".equals(obj.get("TRANS_CODE"))){
			this.update("hrm.empinfo.modifyHrProbation", object);
		}
		this.update("hrm.empinfo.editStartPoint", object);
		LinkedHashMap map = (LinkedHashMap) this.queryMaxCreateDate(object);
		this.update("hrm.empinfo.updateActivity2", map);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateFuzhiDate(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateFuzhiDate", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateSyJieshuDate(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateSyJieshuDate", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateXiuzhiLinkTransNo(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateXiuzhiLinkTransNo", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateSyLinkTransNo(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateSyLinkTransNo", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateBeforeActivity(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateBeforeActivity", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateEmp_Exp(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateEmp_Exp", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateMainBus(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateMainBus", object);
	}
	
	@Override
	public void updateLizhi(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateLizhi", object);
	}
	
	@Override
	public void updateLizhiOrg(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateLizhiOrg", object);
	}
	
	@Override
	public void updateXiuzhi(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateXiuzhi", object);
	}
	
	@Override
	public void updateWaibao(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateWaibao", object);
	}
	
	@Override
	public void updateShiyongjieshu(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateShiyongjieshu", object);
	}
	
	@Override
	public void updatexiuzhi(Object object) throws Exception {
		
		this.update("hrm.empinfo.updatexiuzhi", object);
	}
	
	@Override
	public void updatefuzhi(Object object) throws Exception {
		
		this.update("hrm.empinfo.updatefuzhi", object);
	}
	
	@Override
	public void updateShiyongyanchang(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateShiyongyanchang", object);
	}
	
	@Override
	public void zaipin(Object object) throws Exception {
		
		this.update("hrm.empinfo.zaipin", object);
	}
	
	@Override
	public void zhuanzheng(Object object) throws Exception {
		
		this.update("hrm.empinfo.zhuanzheng", object);
	}
	
	@Override
	public void updateGongzuodi(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateGongzuodi", object);
	}
	
	@Override
	public void updateBiangeng(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateBiangeng", object);
	}
	
	@Override
	public void updatejinsheng(Object object) throws Exception {
		
		this.update("hrm.empinfo.updatejinsheng", object);
	}
	
	@Override
	public void updatejinshengOrg(Object object) throws Exception {
		
		this.update("hrm.empinfo.updatejinshengOrg", object);
	}
	
	@Override
	public void updateZhizebiangeng(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateZhizebiangeng", object);
	}
	
	@Override
	public void updateRenzhi(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateRenzhi", object);
	}
	
	@Override
	public void updateZhijitixi(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateZhijitixi", object);
	}
	
	@Override
	public void updatehrDepartRenzhi(Object object) throws Exception {
		
		this.update("hrm.empinfo.updatehrDepartRenzhi", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updatehrDepartjianzhi(Object object) throws Exception {
		
		this.update("hrm.empinfo.updatehrDepartjianzhi", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updatehrDepartJianzhijiechu(Object object) throws Exception {
		
		this.update("hrm.empinfo.updatehrDepartJianzhijiechu", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateAllJianzhijiechu(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateAllJianzhijiechu", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updatehrDepartRenzhijiechu(Object object) throws Exception {
		
		this.update("hrm.empinfo.updatehrDepartRenzhijiechu", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateLinkExpInsideNokong(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateLinkExpInsideNokong", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateEmp_Exp_Activity(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateEmp_Exp_Activity", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateBumen(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateBumen", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateGongzuodiyidong(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateGongzuodiyidong", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateJinsheng(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateJinsheng", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateZhiqun(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateZhiqun", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateZhize(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateZhize", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateOther(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateOther", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateEmpAll(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateEmpAll", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateArDetailTsto(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateArDetailTsto", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void updateArDetailSst(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateArDetailSst", object);
		// ------------------执行updateHrExperienceInside--------------------
	}
	
	@Override
	public void editHrAddressMatters(Object object) throws Exception {
		
		this.update("hrm.empinfo.editHrAddressMatters", object);
		// ------------------执行updateHrAddressMatters--------------------
	}
	
	@Override
	public void editHrEMPMatters(Object object) throws Exception {
		
		this.update("hrm.empinfo.editHrEMPMatters", object);
		// ------------------执行updateHrAddressMatters--------------------
	}
	
	@Override
	public void insertResults(Object object) throws Exception {
		
		this.update("hrm.empinfo.insertResults", object);
		// ------------------执行updateHrAddressMatters--------------------
	}
	
	@Override
	public void insertResultsName(Object object) throws Exception {
		
		this.update("hrm.empinfo.insertResultsName", object);
		// ------------------执行updateHrAddressMatters--------------------
	}
	
	
	@Override
	public void insertHrEmergencyAddress(Object object) throws Exception {
		
		this.update("hrm.empinfo.insertHrEmergencyAddress", object);
		// ------------------执行insertHrEmergencyAddress--------------------
	}
	
	@Override
	public void tiquziliao(Object object) throws Exception {
		
		this.update("hrm.empinfo.tiquziliao", object);
		// ------------------执行insertHrEmergencyAddress--------------------
	}
	
	@Override
	public void tiquziliao_new(Object object) throws Exception {
		
		this.update("hrm.empinfo.tiquziliao_new", object);
		// ------------------执行insertHrEmergencyAddress--------------------
	}
	
	@Override
	public void insertHr(Object object) throws Exception {
		
		this.update("hrm.empinfo.insertHr", object);
		// ------------------执行insertHrEmergencyAddress--------------------
	}
	
	@Override
	public void insertHrAddressMatters(Object object) throws Exception {
		
		this.update("hrm.empinfo.insertHrAddressMatters", object);
		// ------------------执行insertHrAddressMatters--------------------
	}
	
	@Override
	public void insertHrFamily(Object object) throws Exception {
		
		this.update("hrm.empinfo.insertHrFamily", object);
		// ------------------执行insertHrFamily--------------------
	}
	
	@Override
	public void insertExperiencePoint(Object object) throws Exception {
		
		this.update("hrm.empinfo.insertExperiencePoint", object);
		// ------------------执行insertHrWorkExperience--------------------
	}
	
	@Override
	public void insertEducationMatter(Object object) throws Exception {
		Map obj=(Map)object;
		String seq=String.valueOf(this.insert("hrm.empinfo.insertEducationMatter", object));
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", obj.get("educNo"));
					fileMap.put("APPLY_TYPE", "hrEducation");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		// ------------------执行insertHrEducation--------------------
	}
	
	@Override
	public void insertRecognition(Object object) throws Exception {
		Map obj=(Map)object;
		String seq=String.valueOf(this.insert("hrm.empinfo.insertRecognition", object));
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", seq);
					fileMap.put("APPLY_TYPE", "hrReward");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		// ------------------执行insertHrEducation--------------------
	}
	
	@Override
	public void insertPunishment(Object object) throws Exception {
		Map obj=(Map)object;
		String seq=String.valueOf(this.insert("hrm.empinfo.insertPunishment", object));
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", seq);
					fileMap.put("APPLY_TYPE", "hrPunishment");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		// ------------------执行insertHrEducation--------------------
	}
	
	@Override
	public void insertSpecialMatter(Object object) throws Exception {
		Map obj=(Map)object;
		String seq=String.valueOf(this.insert("hrm.empinfo.insertSpecialMatter", object));
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", seq);
					fileMap.put("APPLY_TYPE", "hrSpecialMatter");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		// ------------------执行inserthr_special_matter--------------------
	}
	
	@Override
	public void insertPassportPerson(Object object) throws Exception {
		Map obj=(Map)object;
		String seq=String.valueOf(this.insert("hrm.empinfo.insertPassportPerson", object));
		String flag=(String) obj.get("FLAG");
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", seq);
					//if("1".equals(flag)){
						fileMap.put("APPLY_TYPE", "hrPassportPerson");
					//}else if("2".equals(flag)){
					//	fileMap.put("APPLY_TYPE", "hrPassportFamily");
					//}
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		// ------------------执行inserthr_special_matter--------------------
	}
	
	@Override
	public void insertBidMatter(Object object) throws Exception {
		Map obj=(Map)object;
		String seq=String.valueOf(this.insert("hrm.empinfo.insertBidMatter", object));
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", obj.get("qualNo"));
					fileMap.put("APPLY_TYPE", "hrQualification");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		// ------------------执行insertHrEducation--------------------
	}
	@Override
	public void insertEvaluateInfo(Object object) throws Exception {
		Map obj=(Map)object;
		String seq=String.valueOf(this.insert("hrm.empinfo.insertEvaluateInfo", object));
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", obj.get("evaluateNo"));
					fileMap.put("APPLY_TYPE", "hrEvaluateInfo");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		// ------------------执行insertHrEducation--------------------
	}
	@Override
	public void insertTrainingBasic(Object object) throws Exception {
		Map obj=(Map)object;
		String seq=String.valueOf(this.insert("hrm.empinfo.insertTrainingBasic", object));
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", obj.get("trainNo"));
					fileMap.put("APPLY_TYPE", "hrTrainification");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		// ------------------执行insertHrEducation--------------------
	}
	
	@Override
	public void insertForeignLanguage(Object object) throws Exception {
		Map obj=(Map)object;
		String seq=String.valueOf(this.insert("hrm.empinfo.insertForeignLanguage", object));
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", obj.get("languageNo"));
					fileMap.put("APPLY_TYPE", "hrLanguageLevel");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		// ------------------执行insertHrEducation--------------------
	}
	
	@Override
	public void deleteEssFile(Object object) throws Exception {
		
		this.update("hrm.empinfo.deleteEssFile", object);
		// --------------------------------------
	}
	
	@Override
	public void deletePhotoEssFile(Object object) throws Exception {
		
		this.update("hrm.empinfo.deletePhotoEssFile", object);
		// --------------------------------------
	}
	
	@Override
	public void insertEssFile(Object object) throws Exception {
		
		this.update("hrm.empinfo.insertEssFile", object);
		// --------------------------------------
	}
	@Override
	public String queryExpInsideNo(Object object) {
		String str = null;
		try {
			str = ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.queryExpInsideNo"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String querydeptno(Object object) {
		String str = null;
		try {
			str = this.queryForObject("hrm.empinfo.querydeptno", object)
					.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String queryEducNo(Object object) {
		String str = null;
		try {
			str = ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.queryEducNo"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String querySearchNo(Object object) {
		String str = null;
		try {
			str = ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.querySearchNo"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String queryLanguageNo(Object object) {
		String str = null;
		try {
			str = ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.queryLanguageNo"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String queryQualNo(Object object) {
		String str = null;
		try {
			str = ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.queryQualNo"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	@Override
	public String queryEvaluateNo(Object object) {
		String str = null;
		try {
			str = ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.queryEvaluateNo"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	@Override
	public String queryTrainNo(Object object) {
		String str = null;
		try {
			str = ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.queryTrainNo"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	@Override
	public void insertStartPoint(Object object) throws Exception {

		Map obj = (Map)object;
		/*if("400438".equals(obj.get("TRANS_CODE")) || "400439".equals(obj.get("TRANS_CODE"))){
			this.update("hrm.empinfo.deleteActivity", object);
		}*/
		//试用期转正 修改试用期结束日期
		if("400422".equals(obj.get("TRANS_CODE"))){
			this.update("hrm.empinfo.modifyHrProbation", object);
		}
		this.update("hrm.empinfo.insertStartPoint", object);
		this.update("hrm.empinfo.updateActivity2", object);
	}
	
	@Override
	public void updatePositivePointActivity(Object object) throws Exception {

		this.update("hrm.empinfo.updatePositivePointActivity", object);
	}
	
	@Override
	public void togetherFuzhi(Object object) throws Exception {
		
		this.update("hrm.empinfo.togetherFuzhi", object);
	}
	
	@Override
	public void togethershiyongjieshu(Object object) throws Exception {
		
		this.update("hrm.empinfo.togethershiyongjieshu", object);
	}
	
	@Override
	public void insertSyUserRelation(Object object) throws Exception {
		
		this.update("hrm.empinfo.insertSyUserRelation", object);
	}
	
	@Override
	public void deleteSyUserRelation(Object object) throws Exception {
		
		this.update("hrm.empinfo.deleteSyUserRelation", object);
	}
	
	
	@Override
	public void deleteYufaling(Object object) throws Exception {
		
		this.update("hrm.empinfo.deleteYufaling", object);
	}
	
	@Override
	public void updatePrepManagerEmpId(Object object) throws Exception {
		
		this.update("hrm.empinfo.updatePrepManagerEmpId", object);
	}
	
	@Override
	public void updateManagerEmpId(Object object) throws Exception {
		
		this.update("hrm.empinfo.updateManagerEmpId", object);
	}
	
	@Override
	public void leftKaoQin(Object object) throws Exception {
		
		this.insert("hrm.empinfo.leftKaoQin", object);
		// ------------------执行insertHrFamily--------------------
	}
	
	@Override
	public void falingcunchu(Object object) throws Exception {
		
		this.insert("hrm.empinfo.falingcunchu", object);
		// ------------------执行insertHrFamily--------------------
	}
	
	@Override
	public void falingcunchuRecognition(Object object) throws Exception {
		
		this.update("hrm.empinfo.falingcunchuRecognition", object);
		// ------------------执行insertHrFamily--------------------
	}
	
	@Override
	public void insertHrExpInsideHistory(Object object) throws Exception {
		
		this.update("hrm.empinfo.insertHrExpInsideHistory", object);
		// ------------------执行insertHrFamily--------------------
	}
	
	public void editTempEduPerInfo(Object object) throws Exception {
		this.update("hrm.empinfo.updateTempEmployeeInfo", object);
		// ------------------执行updateEmployeeInfo--------------------
		this.update("hrm.empinfo.updateTempPersonalInfo", object);
		// ------------------执行updatePersonalInfo--------------------
		this.update("hrm.empinfo.updateTempEmpPaInfo", object);
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
		this.update("hrm.empinfo.updateEducation", object);
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
		returnList = this.getExpInsideList(obj, -1, -1);
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
				returnList = this.queryForList("hrm.empinfo.getExpInsideList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getExpInsideList",
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
				returnList = this.queryForList("hrm.empinfo.getAssignmentList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getAssignmentList",
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
						"hrm.empinfo.getResignationInfo", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.empinfo.getResignationInfo", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	@Override
	public List queryYuFaling(Object obj) {
		List list = new ArrayList();
		try {
				list = this.queryForList(
						"hrm.empinfo.queryYuFaling", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
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
				returnList = this.queryForList("hrm.empinfo.getEvsInfoList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getEvsInfoList",
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

		this.insert("hrm.empinfo.addEvsInfo", obj);

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
			object2 = this.queryForObject("hrm.empinfo.getEvsInfo", object);
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
		this.update("hrm.empinfo.updateEvsInfo", object);
	}

	/**
	 * 删除评价信息(delete assessment information)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void deleteEvsInfo(Object obj) throws Exception {
		this.delete("hrm.empinfo.deleteEvsInfo", obj);
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
	 * 奖励(Reward)
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
				returnList = this.queryForList("hrm.empinfo.getReward", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getReward", obj);
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
				returnList = this.queryForList("hrm.empinfo.getPunishment",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("hrm.empinfo.getPunishment", obj);
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
				returnList = this.queryForList("hrm.empinfo.getPluralityList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getPluralityList",
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
				returnList = this.queryForList(
						"hrm.empinfo.getTradeUnionList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.empinfo.getTradeUnionList", obj);
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
						"hrm.empinfo.getTrainingInfoList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.empinfo.getTrainingInfoList", obj);
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
		this.insert("hrm.empinfo.addTrainingInfo", obj);
	}

	/**
	 * 修改培训信息(Modify training information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editTrainingInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editTrainingInfo", object);
	}

	/**
	 * 删除培训信息(delete training information)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void deleteTrainingInfo(Object obj) throws Exception {
		this.delete("hrm.empinfo.deleteTrainingInfo", obj);
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
				returnList = this.queryForList("hrm.empinfo.getFamilyList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("hrm.empinfo.getFamilyList", obj);
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
		this.insert("hrm.empinfo.insertFamilyInfo", object);

	}

	/**
	 * 删除社会关系(delete family information)
	 */
	@Override
	public void deleteFamilyInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteFamilyInfo", object);

	}

	/**
	 * 修改社会关系(Modify family information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editFamilyInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editFamilyInfo", object);
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
				returnList = this.queryForList("hrm.empinfo.getFamilyList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("hrm.empinfo.getFamilyList", obj);
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

		this.insert("hrm.empinfo.insertFamilyInfo", object);
	}

	/**
	 * 删除家人关系(delete homerelation information)
	 */
	@Override
	public void deleteHomeRelationInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteFamilyInfo", object);
	}

	/**
	 * 修改家人关系(Modify family information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public int editHomeRelation(Object object) throws Exception {
		this.update("hrm.empinfo.editFamilyInfo", object);
		return 0;
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
				returnList = this.queryForList("hrm.empinfo.getHealthList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("hrm.empinfo.getHealthList", obj);
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
		this.insert("hrm.empinfo.insertHealthInfo", object);

	}

	/**
	 * 修改健康信息(Modify health information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editHealthInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editHealthInfo", object);
	}

	/**
	 * 删除健康信息(delete health information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void deleteHealthInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteHealthInfo", object);
	}

	/**
	 * 工作经验(work experience)
	 * 
	 * @return returnList
	 */
	@Override
	public List getWorkExperienceList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getWorkExperienceList(obj, -1, -1);

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
	@Override
	public List getWorkExperienceList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.empinfo.getWorkExperienceList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.empinfo.getWorkExperienceList", obj);
			}
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
		this.insert("hrm.empinfo.insertWorkExperienceInfo", object);
	}

	/**
	 * 修改工作经历信息(Modify workExperience information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editWorkExperienceInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editWorkExperienceInfo", object);
	}

	/**
	 * 删除工作经历(delete WorkExpreienceInfo)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void deleteWorkExpreienceInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteWorkExpreienceInfo", object);
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
						"hrm.empinfo.getQualificationList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.empinfo.getQualificationList", obj);
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
						"hrm.empinfo.getLanguageLevelList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.empinfo.getLanguageLevelList", obj);
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
		this.insert("hrm.empinfo.insertQualificationInfo", object);
	}

	/**
	 * 添加外国语(add LanguageLevelInfo)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void addLanguageLevelInfo(Object object) throws Exception {
		this.insert("hrm.empinfo.insertLanguageLevelInfo", object);
	}

	/**
	 * 修改资格证书信息(Modify qualification certificate of the information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editQualificationInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editQualificationInfo", object);
	}

	/**
	 * 修改外国语信息(Modify foreign language information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editLanguageLevelInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editLanguageLevelInfo", object);
	}

	/**
	 * 删除资格证书(Delete qualification certificate)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void deleteQualicationInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteQualicationInfo", object);

	}

	/**
	 * 删除外国语(Delete foreign language)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void deleteLanguageLevelInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteLanguageLevelInfo", object);

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
			returnList = this.queryForList("hrm.empinfo.getCodeList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public List getCodeListNO(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.getCodeListNO", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public List getCodeList1(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.getCodeList1", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public List getCodeListRecruitType(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.getCodeListRecruitType", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public List getCodeListEmpStatus(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.getCodeListEmpStatus", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 标签查询(QueryLabelInformationVolume)
	 * 
	 * @param parent_code_no
	 * @return retrunList
	 */
	@Override
	public List getCodeListForEvs(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.getCodeListForEvs", object);
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
				returnList = this.queryForList("hrm.empinfo.getAdditionalList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getAdditionalList",
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
		this.insert("hrm.empinfo.addAdditionalInfo", object);
	}

	/**
	 * 修改特殊事项(Modify pecial matters)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editAdditionalInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editAdditionalInfo", object);
	}

	/**
	 * 删除特殊事项(Delete pecial matters)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void deleteAdditionalInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteAdditionalInfo", object);

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
				returnList = this.queryForList("hrm.empinfo.getAccountList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getAccountList",
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
				returnList = this.queryForList("hrm.empinfo.getContractList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getContractList",
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
				returnList = this.queryForList("hrm.empinfo.getFileList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getFileList", obj);
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
		this.insert("hrm.empinfo.addFileInfo", object);
	}

	/**
	 * 修改档案(modify file)
	 * 
	 * @param object
	 */
	@Override
	public void editFileInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editFileInfo", object);
	}

	/**
	 * 删除档案(delete file)
	 * 
	 * @param object
	 */
	@Override
	public void deleteFileInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteFileInfo", object);
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
				returnList = this.queryForList("hrm.empinfo.getGoAbroadList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getGoAbroadList",
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
		this.insert("hrm.empinfo.addGoAbroadInfo", object);
	}

	/**
	 * 修改出国信息(modify goabroad information)
	 * 
	 * @param object
	 */
	@Override
	public void editGoAbroadInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editGoAbroadInfo", object);
	}

	/**
	 * 删除出国信息(delete goabroad information)
	 * 
	 * @param object
	 */
	@Override
	public void deleteGoAbroadInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteGoAbroadInfo", object);
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
				returnList = this.queryForList("hrm.empinfo.getCredentialList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getCredentialList",
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
		this.insert("hrm.empinfo.addCredentialInfo", object);
	}

	/**
	 * 修改证照信息(modify credential information)
	 * 
	 * @param object
	 */
	@Override
	public void editCredentialInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editCredentialInfo", object);
	}

	/**
	 * 删除证照信息(delete credential information)
	 * 
	 * @param object
	 */
	@Override
	public void deleteCredentialInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteCredentialInfo", object);
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
			returnList = this.queryForList("hrm.empinfo.getEmpIdList", obj);
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
				returnList = this.queryForList("hrm.empinfo.getEmpIdList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getEmpIdList", obj);
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
					.queryForObject("hrm.empinfo.getEmpIdListCnt", obj)),
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
					.queryForObject("hrm.empinfo.getEmpIdListCnt", obj)),
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
				returnList = this.queryForList("hrm.empinfo.getPidEidList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("hrm.empinfo.getPidEidList", obj);
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
				returnList = this.queryForList("hrm.empinfo.getPidEidListXiao",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getPidEidListXiao",
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
				returnList = this.queryForList("hrm.empinfo.getPidEidList2",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getPidEidList2",
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
			returnList = this.queryForList("hrm.empinfo.getOldPostGradeList",
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
			returnList = this.queryForList("hrm.empinfo.getPostGradeList", obj,
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
		this.update("hrm.empinfo.editPhotoPath", object);
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
			returnList = this.queryForList("hrm.empinfo.getOrderParmList", obj);
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
						"hrm.empinfo.getDisabilityinfoList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.empinfo.getDisabilityinfoList", obj);
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
		this.insert("hrm.empinfo.insertDisabledInfo", object);

	}

	/**
	 * 删除残疾信息
	 */
	@Override
	public void deleteDisabledInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteDisabledInfo", object);

	}

	/**
	 * 修改残疾信息
	 */
	@Override
	public void editDisabledInfo(Object object) throws Exception {
		this.update("hrm.empinfo.updateDisabledInfo", object);

	}

	/**
	 * 添加工会信息
	 * 
	 * @throws SQLException
	 */
	public void addTradeunionInfo(Map paramMap) throws Exception {
		this.insert("hrm.empinfo.insertTradeunionInfo", paramMap);
	}

	/**
	 * 删除健康信息(delete tradeunion information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void deleteTradeunionInfo(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteTradeunionInfo", object);
	}

	/**
	 * 修改工会信息(Modify tradeunion information)
	 * 
	 * @param object
	 * @throws Exception
	 */
	@Override
	public void editTradeunionInfo(Object object) throws Exception {
		this.update("hrm.empinfo.editTradeunionInfo", object);
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
					"hrm.empinfo.getdisabledCnt", object).toString());
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
		this.update("hrm.empinfo.updateEmployeeDisabled", object);

	}

	/**
	 * 增删改工作经历时候修改基本中的司外工作经历时间
	 */
	@Override
	public void updateEmployeeWorkExperience(Object object) throws Exception {
		this.update("hrm.empinfo.updateEmployeeWorkExperience", object);

	}

	/**
	 * 统计用户是否有最终学历
	 */
	@Override
	public int getFinalNum(Object object) {
		int temp = 0;
		try {
			temp = Integer.parseInt(this.queryForObject(
					"hrm.empinfo.getFinalNum", object).toString());
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
		this.update("hrm.empinfo.updataEduaction", object);

	}

	@Override
	public List getRelevance(Object obj) {
		List returnList = new ArrayList();

		try {
			returnList = this.queryForList("hrm.empinfo.getRelevance", obj);
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
					"hrm.empinfo.getEduactionY", object).toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public void addTestInfo(Object object) throws SQLException {
		this.insert("hrm.empinfo.addTestInfo", object);
	}

	public String getJoinFlagByPersonId(Object object) throws SQLException {
		return this.queryForObject("hrm.empinfo.getJoinFlagByPersonId", object)
				.toString();
	}
	
	public String queryprepmanagerempid(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"hrm.empinfo.queryprepmanagerempid", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	public String queryCountHeader(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"hrm.empinfo.queryCountHeader", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public String queryJianHeader(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"hrm.empinfo.queryJianHeader", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	public String queryUserNo(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"hrm.empinfo.queryUserNo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public String queryRoleGroupNo(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"hrm.empinfo.queryRoleGroupNo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	public String querySyUserRelation(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"hrm.empinfo.querySyUserRelation", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public String queryIsNotManager(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"hrm.empinfo.queryIsNotManager", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public String querymaxStartDate(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"hrm.empinfo.querymaxStartDate", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	public String getBadArchivesInfoSeq(){
		String str = null;
		try {
			str = ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.getBadArchivesInfoSeq"));
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
		this.insert("hrm.empinfo.insertBadArchivesInfo", obj);
		//保存附件
		if (obj.get("FILE_NAME") != null && !"".equals(StringUtil.checkNull(obj.get("FILE_NAME")))) {
			String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("CREATED_BY") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", obj.get("ARCHIVES_NO"));
					fileMap.put("APPLY_TYPE", "0");
					fileMap.put("CREATED_BY", obj.get("CREATED_BY"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
	}

	@Override
	public void deleteBadArchivesInfo(Object object) throws SQLException {
		this.delete("hrm.empinfo.deleteBadArchivesInfo", object);
		this.delete("hrm.empinfo.deleteBadArchivesFilesInfo", object);
	}

	@Override
	public void editBadArchivesInfo(Object object) throws SQLException {
		Map obj = (Map) object;
		this.update("hrm.empinfo.updateBadArchivesInfo", object);
		//保存附件
				if (obj.get("FILE_NAME") != null && !"".equals(StringUtil.checkNull(obj.get("FILE_NAME")))) {
					String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME")).split(";");
					String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(";");
					LinkedHashMap fileMap1 = new LinkedHashMap();
					fileMap1.put("APPLY_NO", obj.get("ID").toString());
					fileMap1.put("APPLY_TYPE", "0");
					this.insert("ess.infoApplyLeave.deleteEssFile",fileMap1);
					if (fileUrl != null && fileUrl.length > 0) {
						for (int j=0;j<fileUrl.length ;j++) {
							LinkedHashMap fileMap = new LinkedHashMap();
							fileMap.put("fileName", fileName[j]);
							fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("CREATED_BY") + "/" + fileUrl[j]);
							fileMap.put("APPLY_NO", obj.get("ID"));
							fileMap.put("APPLY_TYPE", "0");
							fileMap.put("CREATED_BY", obj.get("CREATED_BY"));
							this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
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
				returnList = this.queryForList("hrm.empinfo.getBadArchivesList", obj, currentPage,pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getBadArchivesList", obj);
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
					.queryForObject("hrm.empinfo.getBadArchivesListCnt",
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
		Map object = (Map)obj;
		try {
			if (object.get("OT_LIMIT_PARAM")!= null || !"".equals(StringUtil.checkNull(object.get("OT_LIMIT_PARAM")))) {
				returnList = this.queryForList("hrm.empinfo.getEmpInfoList",obj);
			}else {
				returnList = this.queryForList("hrm.empinfo.getEmpInfoList",obj, currentPage, pageSize);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public List getEmpInfoSHList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.getEmpInfoSHList",
						obj, currentPage, pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public List getEmpInfoListAr(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.getEmpInfoListAr",
					obj, currentPage, pageSize);
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
					.queryForObject("hrm.empinfo.getEmpInfoListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int getEmpInfoListArCnt(Object obj) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.getEmpInfoListArCnt", obj)),
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
				returnList = this.queryForList("hrm.empinfo.getAssistList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("hrm.empinfo.getAssistList", obj);
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
						"hrm.empinfo.getPerConversionList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.empinfo.getPerConversionList", obj);
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
							"hrm.empinfo.getPerConversionListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public void addAssistInfo(Object object) throws SQLException {
		this.insert("hrm.empinfo.insertAssistInfo", object);

	}

	@Override
	public void deleteAssistInfo(Object object) throws SQLException {
		this.delete("hrm.empinfo.deleteAssistInfo", object);

	}

	@Override
	public void editAssistInfo(Object object) throws SQLException {
		this.update("hrm.empinfo.updateAssistInfo", object);
	}

	/**
	 * 删除兼卖产品信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void deleteProductInfo(Object object) throws SQLException {
		this.delete("hrm.empinfo.deleteProductInfo", object);
	}

	/**
	 * 更新兼卖产品信息
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void addProductInfo(Object object) throws SQLException {
		this.insert("hrm.empinfo.addProductInfo", object);
	}

	@Override
	public List getPositionList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.getPositionList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getDqmcList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.getDqmcList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public List getDqmcListNew(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.getDqmcListNew", obj);
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
			object2 = this.queryForObject("hrm.empinfo.getPersonalInfoByLeave",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object querymaxMap(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("hrm.empinfo.querymaxMap",
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
			object2 = this.queryForObject("hrm.empinfo.getPersonalInfoByLeaveApply",
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
	public Object getArchivesInfo(Map paramMap) {
		Object object = null;
		try {
			object = this.queryForObject("hrm.empinfo.getArchivesInfo",
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
			temp = this.queryForList("hrm.empinfo.getEmpProductList", object);
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
			temp = this.queryForList("hrm.empinfo.getEmailByPersonId", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	/**
	 * 根据法人获取人员类型组
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpTypeGroup(Object object){
		List temp = null;		
		try {
			temp = this.queryForList("hrm.empinfo.getEmpTypeGroup",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}

	public List getEmpInfoTempList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.empinfo.getEmpInfoTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getEmpInfoTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public List getEmpInfoTempList(Object object) {
		// TODO Auto-generated method stub
		return this.getEmpInfoTempList(object, 1, 10);
	}

	public int getEmpInfoTempCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getEmpInfoTempCnt", paramMap)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int getEmpInfoTempErrCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getEmpInfoTempErrCnt", paramMap)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public String importEmpInfoTempListExcel(LinkedHashMap paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("hrm.empinfo.updateEmpInfoTempListExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}



	/**
	 * 获取外国语导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempLanguageTempList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.empinfo.getTempLanguageTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getTempLanguageTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
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
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getTempLanguageTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
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
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getTempLanguageTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importFromExcel(Object object) throws Exception {
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("hrm.empinfo.importFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			throw e;
		}
		return returnString ;
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
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getTempDisabledTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
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
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getTempDisabledTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 获取残疾证信息导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempDisabledTempList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.empinfo.getTempDisabledTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getTempDisabledTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
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
	public List getQualInfoTempList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.empinfo.getQualInfoTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getQualInfoTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
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
					.toString(this.queryForObject("hrm.empinfo.getQualInfoTempCnt", paramMap)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getQualInfoTempErrCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getQualInfoTempErrCnt", paramMap)),Integer.class);
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
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.empinfo.getProductInfoTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getProductInfoTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getProductInfoTempList(Object object) {
		return this.getProductInfoTempList(object, 1, 10);
	}

	@Override
	public int getProductInfoTempCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getProductInfoTempCnt", paramMap)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getProductInfoTempErrCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getProductInfoTempErrCnt", paramMap)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}


	@Override
	public List getWorkExperienceInfoTempList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.empinfo.getWorkExperienceInfoTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getWorkExperienceInfoTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getWorkExperienceInfoTempList(Object object) {
		return this.getWorkExperienceInfoTempList(object, 1, 10);
	}

	@Override
	public int getWorkExperienceInfoTempCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getWorkExperienceInfoTempCnt", paramMap)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getWorkExperienceInfoTempErrCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getWorkExperienceInfoTempErrCnt", paramMap)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}


	@Override
	public List getEvsInfoTempList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.empinfo.getEvsInfoTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getEvsInfoTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getEvsInfoTempList(Object object) {
		return this.getEvsInfoTempList(object, 1, 10);
	}

	@Override
	public int getEvsInfoTempErrCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getEvsInfoTempErrCnt", paramMap)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getEvsInfoTempCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getEvsInfoTempCnt", paramMap)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	
	

	@Override
	public int getTradeUnionInfoTempCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getTradeUnionInfoTempCnt", paramMap)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List getTradeUnionInfoTempList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.empinfo.getTradeUnionInfoTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getTradeUnionInfoTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getTradeUnionInfoTempList(Object object) {
		return this.getTradeUnionInfoTempList(object, 1, 10);
	}

	@Override
	public int getTradeUnionInfoTempErrCnt(LinkedHashMap paramMap) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getTradeUnionInfoTempErrCnt", paramMap)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public String importInfoFromExcel(Object object) throws SQLException {
		
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("hrm.empinfo.importInfoFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			throw e;
		}
		return returnString ;
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
	public List getTrainingImportTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.empinfo.getTrainingImportTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getTrainingImportTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
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
	public List getTrainingImportTempList(Object object){
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
	public int getTrainingImportTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getTrainingImportTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
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
	public int getTrainingImportTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getTrainingImportTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 人员信息mapping 新增
	 */
	@Override
	public void addEmpMapping(Object object) throws Exception {
		this.update("hrm.empinfo.addEmpMapping", object);
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
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.validIdCard", object)),Integer.class);
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
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.validIsDuplicate", object)),Integer.class);
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
		this.insert("hrm.empinfo.updateEmpMapping", object);
	}
	/**
	 * 人员信息mapping 删除
	 */
	public void deleteEmpMapping(Object object) throws Exception {
		this.delete("hrm.empinfo.deleteEmpMapping", object);
	}
	
	/**
	 * 人员信息mapping查询
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List viewEmpMappingList(Object object) throws SQLException {
		return this.viewEmpMappingList(object, -1, -1);
	}

	/**
	 * 人员信息mapping查询
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List viewEmpMappingList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("hrm.empinfo.viewEmpMappingList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.viewEmpMappingList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 人员信息mapping查询 总数
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public int viewEmpMappingListCnt(Object obj) {
		int count = 0;
		try {
			count = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.viewEmpMappingCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 人员信息mapping查询 BY NO
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpMappingByNo(Object obj) throws SQLException{
		
		return this.queryForList("hrm.empinfo.getEmpMappingByNo",
					obj);
	}

	/**
	 * 获取工作经历导入信息
	 */
	@Override
	public List getworkExperienceTempList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.empinfo.getWorkExperienceInfoTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getWorkExperienceInfoTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
     /**
      * 外国语数据从临时表导入到正式表
      * 
      * 数据的验证
      */
	@SuppressWarnings("unchecked")
	public String importLanguageExcel(Object object) throws Exception{
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("hrm.empinfo.importLanguageExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			throw e;
		}
		return returnString ;
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
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getTempContactTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
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
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getTempContactTempErrorCnt", object)),Integer.class);
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
	public List getTempContactTempList(Object object, int pageNum, int numPerPage) {
		List returnList = new ArrayList() ;
		try {
			if(pageNum > -1 && numPerPage > -1){
				returnList = this.queryForList("hrm.empinfo.getTempContactTempList", object, pageNum, numPerPage);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getTempContactTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
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
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getTempAssistTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
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
			count = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject("hrm.empinfo.getTempAssistTempErrorCnt", object)),Integer.class);
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
		List returnList = new ArrayList() ;
		try {
			if(pageNum > -1 && numPerPage > -1){
				returnList = this.queryForList("hrm.empinfo.getTempAssistTempList", object, pageNum, numPerPage);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getTempAssistTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
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
	public String importInfoExcel(Object object) throws Exception{
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("hrm.empinfo.importInfoExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			throw e;
		}
		return returnString ;
	}

	@Override
	public List getEmpTypeList(Map paramMap) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.getEmpTypeList", paramMap);
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
				returnList = this.queryForList("hrm.empinfo.getTempEmpInfoList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getTempEmpInfoList",
						obj);
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
					.queryForObject("hrm.empinfo.getTempEmpInfoListCnt", paramMap)),
					Integer.class);
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
	public List getTempEmpAffirmInfoListBatch(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("hrm.empinfo.getTempEmpAffirmInfoListBatch",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getTempEmpAffirmInfoListBatch",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getTempEmpAffirmInfoListCntBatch(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("hrm.empinfo.getTempEmpAffirmInfoCntBatch", obj)),Integer.class);
	}

	@Override
	public List getImportTmpEmpResultList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.empinfo.getImportTmpEmpResultList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("hrm.empinfo.getImportTmpEmpResultList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getImportTmpEmpResultList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getImportTmpEmpResultList(obj, -1, -1) ;
		
		return returnList ;
	}

	@Override
	public int getImportTmpEmpResultCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getImportTmpEmpResultCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@Override
	public int getImportTmpEmpErrCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getImportTmpEmpErrCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String importTmpEmpFromExcel(LinkedHashMap paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("hrm.empinfo.importTmpEmpFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int delTmpEmpInBatch(LinkedHashMap paramMap) {
		int ret = 0 ;		
		try {
			paramMap.put("message", "") ;
			this.insert("hrm.empinfo.delTmpEmpInBatch", paramMap) ;			
			ret = NumberUtils.parseNumber(paramMap.get("RET").toString(),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret ;
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
				returnList = this.queryForList("hrm.empinfo.getTempEmpBatchReqList", obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.empinfo.getTempEmpBatchReqList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getTempEmpBatchReqCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getTempEmpBatchReqCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	public Object getTempEmpReqDetail(Object obj) {
		Object returnObj = new Object();
		try {
			returnObj = this.queryForObject("hrm.empinfo.getTempEmpReqDetail",obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnObj;
	}

	/* 
	* Title: getInsrareaForCpnyId
	* Description:查询法人人员所用的所有福利地区
	* @author 孙鹏  
	* @date 2015年3月16日 下午4:25:58  
	* @param paramMap
	* @return 
	* @see com.ait.hrm.dao.EmpInfoDao#getInsrareaForCpnyId(java.util.LinkedHashMap) 
	*/
	@Override
	public Object getInsrareaForCpnyId(LinkedHashMap paramMap) {
		Object returnObj = new Object();
		try {
			returnObj = this.queryForList("hrm.empinfo.getInsrareaForCpnyId",paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnObj;
	}
	
	public int getEmpIdCardNoCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.getEmpIdCardNoCnt", obj)),
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
	public List getMappingTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.empinfo.getMappingTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.empinfo.getMappingTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
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
	public int getMappingTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getMappingTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
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
	public int getMappingTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getMappingTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	

	public List gettreeDomeList(Object obj ) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("hrm.empinfo.gettreeDomeList",
						obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 标签查询(sql)
	 * 
	 * @param parent_code_no
	 * @return retrunList
	 */
	public List getCodeListBySql(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.getCodeListBySql", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 人事信息卡查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List viewCardInfoList(Object obj ) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("hrm.empinfo.viewCardInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 人事信息卡1查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List viewCardInfoList1(Object obj ) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.viewCardInfoList1", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 人事信息卡1基本信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List viewBaseInfoList(Object obj ) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.viewBaseInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 人事信息卡1教育信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List viewJiaoyuInfoList(Object obj ) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.viewJiaoyuInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 人事信息卡1经历信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List viewJingliInfoList(Object obj ) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.viewJingliInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 人事信息卡1家庭信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List viewJiatingInfoList(Object obj ) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.viewJiatingInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 人事信息卡1评价信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List viewPingjiaInfoList(Object obj ) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.viewPingjiaInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 人事信息卡1资格信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List viewZigeInfoList(Object obj ) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.viewZigeInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 人事信息卡1培训信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List viewPeixunInfoList(Object obj ) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.viewPeixunInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 人事信息卡1奖励信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List viewJiangliInfoList(Object obj ) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.viewJiangliInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 人事信息卡1惩罚信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List viewChengfaInfoList(Object obj ) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.viewChengfaInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 人事信息卡1发令信息查询
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return retrunList
	 */
	@Override
	public List viewFalingInfoList(Object obj ) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.empinfo.viewFalingInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 标签查询(QueryLabelInformationVolume)
	 * 
	 * @param parent_code_no
	 * @return retrunList
	 */
	@Override
	public List getCodeListDESCRIPTION(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.empinfo.getCodeListDESCRIPTION", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	/**
	 * viewPregnantManagement
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public List getPregnantManagementList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getPregnantManagementList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	/**
	 * viewPregnantManagement的数量
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public int getPregnantManagementList_count(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.empinfo.getPregnantManagementList_count", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@Override
	public void editPregnantManagement(Object object) throws Exception {
		
		this.update("hrm.empinfo.editPregnantManagement", object);
		// ------------------执行updatePregnantManagement--------------------
	}
	
	@Override
	public void insertPregnantManagement(Object object) throws Exception {
		
		this.update("hrm.empinfo.insertPregnantManagement", object);
		// ------------------执行insertPregnantManagement--------------------
	}
	
	/**
	 *PregnantManagement的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewSinglePregnantManagement(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewSinglePregnantManagement", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public void deletePregnantManagement(Object object) throws Exception {
		this.update("hrm.empinfo.deletePregnantManagement", object);
		// ------------------执行updateHrWorkExperience--------------------
	}
	
	/**
	 * 地址类型单一的基本信息
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object viewCurrentHrInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"hrm.empinfo.viewCurrentHrInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	public List getEmpSimpleInfoList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getEmpSimpleInfoList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	public List viewDeptDemissionList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.viewDeptDemissionList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	public List hrDemissionRateReport(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.hrDemissionRateReport",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	public List hrDemissionRateByDeptNoReport(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.hrDemissionRateByDeptNoReport",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	public List hrDemissionRateByShopReport(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.hrDemissionRateByShopReport",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	public List hrDemissionRateFullThreeMonthsReport(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.hrDemissionRateFullThreeMonthsReport",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	public List hrDemissionRateFullShopThreeMonthsReport(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.hrDemissionRateFullShopThreeMonthsReport",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	public List hrDemissionRateByXdfMonthsReport(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.hrDemissionRateByXdfMonthsReport",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	public List getDeptAllList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.getDeptAllList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	public List hrStroeNumberReport(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("hrm.empinfo.hrStroeNumberReport",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	@Override
	public void updateHrEmployee(Object object) throws Exception {
		this.update("hrm.empinfo.updateHrEmployee", object);
	}
	
	@Override
	public void updateEmpinfo(Object object, String target) throws Exception {
		this.update("hrm.empinfo."+target, object);
		// ------------------执行updateHrEducation--------------------
	}
	
	@SuppressWarnings("unchecked")
	public void updateList(String target, List list) throws Exception {

		this.updateForList("hrm.empinfo."+target, list);
	}
}
