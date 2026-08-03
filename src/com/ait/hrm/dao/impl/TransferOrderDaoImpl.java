package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.hrm.dao.TransferOrderDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: TransferOrderDaoImpl.java
 * @Description:
 * @Create date: 2012-2-23 下午10:32:07
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class TransferOrderDaoImpl extends SqlMapClientSupport implements
		TransferOrderDao {

	public String getNextExpInside() {
		String returnInt = "";
		try {
			returnInt = ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getNextExpInside"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	public String getNextResignNo() {
		String returnInt = "";
		try {
			returnInt = ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getNextResignNo"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	public String getNextPersonId() {
		String returnInt = "";
		try {
			returnInt = ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getNextPersonId"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	public String getPersonId(Map paramMap) {
		String returnInt = "";
		try {
			returnInt = (String) this.queryForObject(
					"hrm.transferOrder.getPersonId", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	public String getNextUserNo() {
		String returnInt = "";
		try {
			returnInt = ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getNextUserNo"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	public String getRoleGroupNo(Object obj) {
		String returnInt = "";
		try {
			returnInt = (String) this.queryForObject(
					"hrm.transferOrder.getRoleGroupNo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@SuppressWarnings("unchecked")
	public List getRoleGroupList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getRoleGroupList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public int getValidationEmpid(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"hrm.transferOrder.getValidationEmpid", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	public int getValidationIdCardNo(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getValidationIdCardNo",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	public int getParamInfoValue(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(
							ObjectUtils.toString(this.queryForObject("hrm.transferOrder.getParamInfoValue", obj)),
							Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getTransferOrderList(Map object) {
		Map object2 = new LinkedHashMap();
		try {
			int skipResults = Integer.parseInt(object.get("page").toString());
			int maxResults = Integer
					.parseInt(object.get("pagesize").toString());

			List Rows = this.queryForList(
					"hrm.transferOrder.getTransferOrderList", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.transferOrder.getTransferOrderListCnt", object);

			object2.put("Rows", Rows);
			object2.put("Total", count);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getPluralityList(Map object) {
		Map object2 = new LinkedHashMap();
		try {
			int skipResults = Integer.parseInt(object.get("page").toString());
			int maxResults = Integer
					.parseInt(object.get("pagesize").toString());

			List Rows = this.queryForList("hrm.transferOrder.getPluralityList",
					object, skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.transferOrder.getPluralityListCnt", object);

			object2.put("Rows", Rows);
			object2.put("Total", count);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDispatchInfo(Map object) {
		Map temp = new LinkedHashMap();
		try {
			temp = (Map) this.queryForObject(
					"hrm.transferOrder.getDispatchInfo", object);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveResign(List list) throws Exception {
		this.insertForList("hrm.transferOrder.saveResign", list);
	}

	@Override
	public void saveExperienceForTransfer(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForTransfer", obj);
	}
	@Override
	public void updateExperienceForTransfer(Object obj) throws Exception {
		this.insert("hrm.transferOrder.updateExperienceForTransfer", obj);
	}
	
	@Override
	public void saveExperienceForEmpTypeTransfer(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForEmpTypeTransfer", obj);
	}
	@Override
	public Map callActiveEmpTypeTransfer(Map paramMap) throws Exception {
		this.insert("hrm.transferOrder.callActiveEmpTypeTransfer", paramMap);
		return paramMap;
	}

	/**
	 * 提交调动发令（save Transfer Order Upgrade）
	 * 
	 * @param obj
	 */
	public void saveExperienceForRemoveANDAffirmor(Object obj) throws Exception {
		// 保存到HR_AFFIRM
		this.insert("hrm.transferOrder.saveAffirmor", obj);
		this.insert("hrm.transferOrder.saveExperienceForRemove", obj);
	}

	/**
	 * 提交调动发令（save Transfer Order Upgrade）
	 * 
	 * @param obj
	 *            list
	 * @return
	 * @throws Exception
	 */
	public void saveExperienceForRemoveAndAffirmList(Object obj, List list)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
		this.insert("hrm.transferOrder.saveExperienceForRemove", obj);
	}

	@Override
	public void saveAffirmor(Object obj) throws Exception {

		this.insert("hrm.transferOrder.saveAffirmor", obj);
	}

	@Override
	public void saveExperienceForTransNormal(Object obj) throws Exception {

		this.insert("hrm.transferOrder.saveExperienceForTransNormal", obj);
	}

	@Override
	public void saveExperienceForTransNormalAndUpHrEmployee(Object obj)
			throws Exception {
		this.insert("hrm.transferOrder.updateHrEmployeeForTransferNormal", obj);
		this.insert("hrm.transferOrder.saveExperienceForTransNormal", obj);
	}

	@Override
	public void saveExperienceForTransNormalAndUpHrEmployeeAndUpDateStarted(
			Object obj) throws Exception {
		this.insert("hrm.transferOrder.updateHrEmployeeForTransferNormal", obj);
		this.insert("hrm.transferOrder.saveExperienceForTransNormal", obj);
		this.insert("hrm.transferOrder.updateHrEmployeeDateStated", obj);
	}

	@Override
	public void saveExperienceForTransNormalAndAffirm(Object obj)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insert("hrm.transferOrder.saveAffirmor", obj);
		this.insert("hrm.transferOrder.saveExperienceForTransNormal", obj);
	}

	@Override
	public void saveExperienceForTransNormalAndAffirmList(Object obj, List list)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
		this.insert("hrm.transferOrder.saveExperienceForTransNormal", obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveReward(List list) throws Exception {
		this.insertForList("hrm.transferOrder.saveReward", list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void savePunishment(List list) throws Exception {
		this.insertForList("hrm.transferOrder.savePunishment", list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveDispath(List list) throws Exception {
		this.insertForList("hrm.transferOrder.saveDispath", list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveDispathEnd(List list) throws Exception {
		this.insertForList("hrm.transferOrder.saveDispathEnd", list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void savePlurality(List list) throws Exception {
		this.insertForList("hrm.transferOrder.savePlurality", list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void savePluralityEnd(List list) throws Exception {
		this.insertForList("hrm.transferOrder.savePluralityEnd", list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveBusiness(List list) throws Exception {
		this.insertForList("hrm.transferOrder.saveBusiness", list);
	}

	@Override
	public void saveHire(Object obj) throws Exception {

		// 保存到HR_EMPLOYEE
		this.insert("hrm.transferOrder.saveEmployeeInfo", obj);
		// 保存到HR_PERSONAL_INFO
		this.insert("hrm.transferOrder.savePersonalInfo", obj);
		// 保存到HR_EMP_PA_INFO
		this.insert("hrm.transferOrder.saveEmpPaInfo", obj);
		// 保存到HR_EXPERIENCE_INSIDE
		this.insert("hrm.transferOrder.saveExperienceInside", obj);
		// 保存到HR_EMP_DEPT
		this.insert("hrm.transferOrder.saveHrEmpDept", obj);
		// 保存到HR_EMP_DUTY
		this.insert("hrm.transferOrder.saveHrEmpDuty", obj);
		// 保存到HR_EMP_POSITION
		this.insert("hrm.transferOrder.saveHrEmpPosition", obj);
		// 保存到HR_EMP_POST
		this.insert("hrm.transferOrder.saveHrEmpPost", obj);
		// 保存到HR_EMP_POST_GRADE
		this.insert("hrm.transferOrder.saveHrEmpPostGrade", obj);
		// 保存到HR_EMP_WORK_AREA
		this.insert("hrm.transferOrder.saveHrEmpWorkArea", obj);
		// 保存到SY_USER
		this.insert("hrm.transferOrder.saveSyUser", obj);
	}

	public void saveSyUser(Object obj) throws Exception {
		// 保存到SY_USER_RELATION
		this.insert("hrm.transferOrder.saveSyUserRelation", obj);
	}

	public void saveHireEffective(Object obj) throws Exception {

		// 保存到HR_EMPLOYEE
		this.insert("hrm.transferOrder.saveHrEmployeeInfo", obj);
		// 保存到HR_EMPLOYEE_TEMP
		this.insert("hrm.transferOrder.saveEmployeeInfo", obj);
		// 保存到HR_PERSONAL_INFO
		this.insert("hrm.transferOrder.saveHrPersonalInfo", obj);
		// 保存到HR_PERSONAL_INFO_TEMP
		this.insert("hrm.transferOrder.savePersonalInfo", obj);
		// 保存到HR_EMP_PA_INFO
		this.insert("hrm.transferOrder.saveHrEmpPaInfo", obj);
		// 保存到HR_EMP_PA_INFO_TEMP
		this.insert("hrm.transferOrder.saveEmpPaInfo", obj);
		// 保存到HR_EXPERIENCE_INSIDE
		this.insert("hrm.transferOrder.saveHrExperienceInside", obj);
		// 保存到SY_USER
		this.insert("hrm.transferOrder.saveSyUser", obj);
		// 保存到HR_EMP_DEPT
		this.insert("hrm.transferOrder.saveHrEmpDept", obj);
		// 保存到HR_EMP_DUTY
		this.insert("hrm.transferOrder.saveHrEmpDuty", obj);
		// 保存到HR_EMP_POSITION
		this.insert("hrm.transferOrder.saveHrEmpPosition", obj);
		// 保存到HR_EMP_POST
		this.insert("hrm.transferOrder.saveHrEmpPost", obj);
		// 保存到HR_EMP_POST_GRADE
		this.insert("hrm.transferOrder.saveHrEmpPostGrade", obj);
		// 保存到HR_EMP_WORK_AREA
		this.insert("hrm.transferOrder.saveHrEmpWorkArea", obj);

	}

	@SuppressWarnings("unchecked")
	public void saveHireEffectiveANDUserRelation(Object obj) throws Exception {
		// 保存到HR_EMPLOYEE
		this.insert("hrm.transferOrder.saveHrEmployeeInfo", obj);
		// 保存到HR_EMPLOYEE_TEMP
		this.insert("hrm.transferOrder.saveEmployeeInfo", obj);
		// 保存到HR_PERSONAL_INFO
		this.insert("hrm.transferOrder.saveHrPersonalInfo", obj);
		// 保存到HR_PERSONAL_INFO_TEMP
		this.insert("hrm.transferOrder.savePersonalInfo", obj);
		// 保存到HR_EMP_PA_INFO
		this.insert("hrm.transferOrder.saveHrEmpPaInfo", obj);
		// 保存到HR_EMP_PA_INFO_TEMP
		this.insert("hrm.transferOrder.saveEmpPaInfo", obj);
		// 保存到HR_EXPERIENCE_INSIDE
		this.insert("hrm.transferOrder.saveHrExperienceInside", obj);
		// 保存到SY_USER
		this.insert("hrm.transferOrder.saveSyUser", obj);
		// 保存到HR_EMP_DEPT
		this.insert("hrm.transferOrder.saveHrEmpDept", obj);
		// 保存到HR_EMP_DUTY
		this.insert("hrm.transferOrder.saveHrEmpDuty", obj);
		// 保存到HR_EMP_POSITION
		this.insert("hrm.transferOrder.saveHrEmpPosition", obj);
		// 保存到HR_EMP_POST
		this.insert("hrm.transferOrder.saveHrEmpPost", obj);
		// 保存到HR_EMP_POST_GRADE
		this.insert("hrm.transferOrder.saveHrEmpPostGrade", obj);
		// 保存到HR_EMP_WORK_AREA
		this.insert("hrm.transferOrder.saveHrEmpWorkArea", obj);
		// 保存到SY_USER_RELATION
		// this.insert("hrm.transferOrder.saveSyUserRelation", obj);

		List roleGroupList = this.queryForList(
				"hrm.transferOrder.getRoleGroupList", obj);
		// int syUserNo = this.getSyUserSeqNextVal();
		Map ma = (Map) obj;

		// 如果该法人下有默认的入职权限组
		if (roleGroupList.size() > 0) {
			for (int j = 0; j < roleGroupList.size(); j++) {
				String syGroupNo = (String) roleGroupList.get(j);
				((Map) obj).put("ROLE_GROUP_NO", syGroupNo);
				((Map) obj).put("SY_USER_SEQ", ma.get("USER_NO").toString());
				this.insert("ess.trans.insertSyUserRelationForEntryTrans", obj);
			}
		}
	}

	/**
	 * 取当前SY_USER表的序列(get sy_user sequences next value)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getSyUserSeqNextVal() throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.trans.getSyUserSeqNextVal")),
				Integer.class);
	}

	/**
	 * 提交入职发令（save Transfer Order Hire）
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public void saveHireAffirm(Object obj) throws Exception {

		// 保存到HR_AFFIRM
		//this.insert("hrm.transferOrder.saveAffirmor", obj);
		// 保存到HR_EMPLOYEE
		this.insert("hrm.transferOrder.saveEmployeeFromY", obj);
		// 保存到HR_PERSONAL_INFO
		this.insert("hrm.transferOrder.savePersonalFromY", obj);
		// 保存到HR_EMP_PA_INFO
		this.insert("hrm.transferOrder.saveEmpPaFromY", obj);
		// 保存到HR_EXPERIENCE_INSIDE
		this.insert("hrm.transferOrder.saveExperienceFromY", obj);

	}

	@SuppressWarnings("rawtypes")
	public void saveHireAffirmList(Object obj, List list) throws Exception {

		// 保存到HR_EMPLOYEE
		this.insert("hrm.transferOrder.saveEmployeeInfo", obj);
		// 保存到HR_PERSONAL_INFO
		this.insert("hrm.transferOrder.savePersonalInfo", obj);
		// 保存到HR_EMP_PA_INFO
		// 직원계정정보는 입사시점에 저장 안함
		 this.insert("hrm.transferOrder.saveEmpPaInfo", obj);
		// 保存到HR_EXPERIENCE_INSIDE
		this.insert("hrm.transferOrder.saveExperienceInside", obj);
		// 保存到HR_AFFIRM
		//this.insertForList("hrm.transferOrder.saveAffirmor", list);
		Map map = (LinkedHashMap) obj;
		String cnpy_id = map.get("CPNY_ID").toString();
		if(!cnpy_id.equals("TSTO")){
			this.saveHireAffirm(obj);
		}
	}

	@Override
	public void updateHrEmpDept(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpDept", obj);
	}

	@Override
	public void updateHrEmpDuty(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpDuty", obj);
	}

	@Override
	public void updateHrEmpPost(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpPost", obj);
	}

	@Override
	public void updateHrEmpPostGrade(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpPostGrade", obj);
	}

	@Override
	public void updateHrEmpPosition(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpPosition", obj);
	}

	@Override
	public void updateHrEmpWorkArea(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpWorkArea", obj);
	}

	@Override
	public void saveHrEmpDept(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpDept", obj);
		this.insert("hrm.transferOrder.saveHrEmpDept", obj);
	}

	@Override
	public void saveHrEmpDuty(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpDuty", obj);
		this.insert("hrm.transferOrder.saveHrEmpDuty", obj);
	}

	@Override
	public void saveHrEmpPost(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpPost", obj);
		this.insert("hrm.transferOrder.saveHrEmpPost", obj);
	}

	@Override
	public void saveHrEmpPostGrade(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpPostGrade", obj);
		this.insert("hrm.transferOrder.saveHrEmpPostGrade", obj);
	}

	@Override
	public void saveHrEmpPosition(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpPosition", obj);
		this.insert("hrm.transferOrder.saveHrEmpPosition", obj);
	}

	@Override
	public void saveHrEmpWorkArea(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpWorkArea", obj);
		this.insert("hrm.transferOrder.saveHrEmpWorkArea", obj);
	}

	@Override
	public void updateHrEmployee(Object obj) throws Exception {
		this.insert("hrm.transferOrder.updateHrEmployee", obj);
	}

	@Override
	public void saveExperienceForRemoveAndUpHrEmployee(Object obj)
			throws Exception {
		this.insert("hrm.transferOrder.updateHrEmployee", obj);
		this.insert("hrm.transferOrder.saveExperienceForRemove", obj);
	}

	@Override
	public void saveExperienceForRemoveAndNoUpHE(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForRemove", obj);
	}

	@Override
	public void updateHrEmployeeForTransferNormal(Object obj) throws Exception {
		this.insert("hrm.transferOrder.updateHrEmployeeForTransferNormal", obj);
	}

	@Override
	public void hrUpdateAuto() throws Exception {
		this.update("hrm.transferOrder.hrUpdateAuto");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDispatchForSearch(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"hrm.searchTransferOrder.getDispatch", object, skipResults,
					maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.searchTransferOrder.getDispatchCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getHortationForSearch(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"hrm.searchTransferOrder.getRewardInfo", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.searchTransferOrder.getRewardInfoCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getPluralityForSearch(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"hrm.searchTransferOrder.getPlurality", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.searchTransferOrder.getPluralityCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getPunishMentForSearch(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"hrm.searchTransferOrder.getPunishMent", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.searchTransferOrder.getPunishMentCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getResignForSearch(Object object, int skipResults, int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"hrm.searchTransferOrder.getResignation", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.searchTransferOrder.getResignationCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getSuspendForSearch(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"hrm.searchTransferOrder.getExpInside", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.searchTransferOrder.getExpInsideCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getTransferActBusinessForSearch(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"hrm.searchTransferOrder.getActBusiness", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.searchTransferOrder.getActBusinessCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getTransferNormalForSearch(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"hrm.searchTransferOrder.getExpInside", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.searchTransferOrder.getExpInsideCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getTransferPostForSearch(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"hrm.searchTransferOrder.getExpInside", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.searchTransferOrder.getExpInsideCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getTransferPromoteForSearch(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"hrm.searchTransferOrder.getExpInside", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"hrm.searchTransferOrder.getExpInsideCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getViewUpgradeList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getViewUpgradeList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getViewUpgradeList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getViewUpgrade", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getViewUpgrade", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息总数（get Insurance Personnel Cnt）
	 * 
	 * @param List
	 * @return
	 */
	public int getViewUpgradeCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"hrm.transferOrder.getViewUpgradeCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorIdListByPersonal(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getAffirmorIdListByPersonal", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorIdListByPersonal1(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getAffirmorIdListByPersonal1", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorIdListByDept(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getAffirmorIdListByDept", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSpecialAffirmorList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getSpecialAffirmorList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	public Object getSpecialAffirmorPersonId(Object obj) {
		Object object = null;
		try {
			object = this.queryForObject(
					"hrm.transferOrder.getSpecialAffirmorPersonId", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return object;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveAffirmor(List list) throws Exception {

		this.insertForList("hrm.transferOrder.saveAffirmor", list);
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPositionList(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this.queryForList("hrm.transferOrder.getPositionList",
					obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDutyList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("hrm.transferOrder.getDutyList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPostGradeList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getPostGradeList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getOldPostGradeList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getOldPostGradeList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPostList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("hrm.transferOrder.getPostList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getWorkAreaList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.transferOrder.getWorkAreaList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPostListByPostGradeNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getPostListByPostGradeNo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDutyListByPostGradeNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getDutyListByPostGradeNo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getWorkAreaByDept(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getWorkAreaByDept", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSocialSecurityAreaByWorkArea(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getSocialSecurityAreaByWorkArea", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getTransferNormalList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getTransferNormalList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getTransferNormalList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getTransferNormal", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getTransferNormal", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息总数（get Insurance Personnel Cnt）
	 * 
	 * @param List
	 * @return
	 */
	public int getTransferNormalCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getTransferNormalCnt",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getTransferPromoteList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getTransferPromoteList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getTransferPromoteList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getTransferPromote", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getTransferPromote", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息总数（get Insurance Personnel Cnt）
	 * 
	 * @param List
	 * @return
	 */
	public int getTransferPromoteCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getTransferPromoteCnt",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map searchEmpHistory(Object object, int skipResults, int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"hrm.searchTransferOrder.searchEmpHistoryById", object);
			Integer count = (Integer) this.queryForObject(
					"hrm.searchTransferOrder.searchEmpHistoryByIdCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public void updateRollBackUpgrade(Object object) throws Exception {
		this.update("hrm.searchTransferOrder.rollBackUpgrade", object);

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map searchEmpHistoryForActBusiness(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"hrm.searchTransferOrder.viewActBusiness", object);
			Integer count = (Integer) this.queryForObject(
					"hrm.searchTransferOrder.viewActBusinessCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public void updateRollBackActBusiness(Object object) throws Exception {
		this.update("hrm.searchTransferOrder.rollBackActBusiness", object);

	}

	@Override
	public void updateRollBackDispatch(Object object) throws Exception {
		this.update("hrm.searchTransferOrder.rollBackDispatch", object);

	}

	@Override
	public void updateRollBackHortation(Object object) throws Exception {
		this.update("hrm.searchTransferOrder.rollBackHortation", object);

	}

	@Override
	public void updateRollBackPlurality(Object object) throws Exception {
		this.update("hrm.searchTransferOrder.rollBackPlurality", object);

	}

	@Override
	public void updateRollBackPunishMent(Object object) throws Exception {
		this.update("hrm.searchTransferOrder.rollBackPunishMent", object);

	}

	@Override
	public void updateRollBackResign(Object object) throws Exception {
		this.update("hrm.searchTransferOrder.rollBackResign", object);

	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */

	@Override
	public int getNextEmpid(Object object) {
		// TODO Auto-generated method stub
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getNextEmpid", object)),
					Integer.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return returnInt;
	}

	@Override
	public String getNextPrEmpid(Object object) {
		// TODO Auto-generated method stub
		String srtn = "";
		try {
			srtn = ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getNextPrEmpid", object));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return srtn;
	}

	@SuppressWarnings("unchecked")
	public List getViewResignList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getViewResignList(obj, -1, -1);

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getViewResignList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getViewResign", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getViewResign", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	public int getViewResignCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"hrm.transferOrder.getViewResignCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public List getViewTempEmpList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getViewTempEmpList(obj, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getViewTempEmpList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getViewTempEmpList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getViewTempEmpList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	public int getViewTempEmpCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"hrm.transferOrder.getViewTempEmpCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public List getViewResignEditList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getViewResignEditList(obj, -1, -1);

		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getViewResignEditList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getViewResignEditList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getViewResignEditList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	public int getViewResignEditCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"hrm.transferOrder.getViewResignEditCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	@Override
	public void saveExperienceForResignation(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForResignation", obj);
	}
	
	@Override
	public void updateExperienceForResignation(Object obj) throws Exception {
		this.insert("hrm.transferOrder.updateExperienceForResignation", obj);
	}

	@Override
	public void saveExperienceForResignationAndAffirm(Object obj)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insert("hrm.transferOrder.saveAffirmor", obj);
		this.insert("hrm.transferOrder.saveExperienceForResignation", obj);
	}

	@Override
	public void saveExperienceForResignationAndAffirmList(Object obj, List list)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
		this.insert("hrm.transferOrder.saveExperienceForResignation", obj);
	}

	@Override
	public void updateAllHrEmp(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmpDeptForResignation", obj);
		this.update("hrm.transferOrder.updateHrEmpDutyForResignation", obj);
		this.update("hrm.transferOrder.updateHrEmpPostForResignation", obj);
		this.update("hrm.transferOrder.updateHrEmpPostGradeForResignation",obj);
		this.update("hrm.transferOrder.updateHrEmpPositionForResignation", obj);
		this.update("hrm.transferOrder.updateHrEmpWorkAreaForResignation", obj);
	}

	@Override
	public void saveExperienceForResignationAndUpEmpAndUpAllHrEmp(Object obj)
			throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForResignation", obj);
		this.update("hrm.transferOrder.updateHrEmployeeForResignation", obj);
//		this.update("hrm.transferOrder.updateHrEmpDeptForResignation", obj);
//		this.update("hrm.transferOrder.updateHrEmpDutyForResignation", obj);
//		this.update("hrm.transferOrder.updateHrEmpPostForResignation", obj);
//		this.update("hrm.transferOrder.updateHrEmpPostGradeForResignation",obj);
//		this.update("hrm.transferOrder.updateHrEmpPositionForResignation", obj);
//		this.update("hrm.transferOrder.updateHrEmpWorkAreaForResignation", obj);
	}

	@Override
	public void updateHrEmployeeForResignation(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmployeeForResignation", obj);
	}

	@Override
	public void saveExperienceForPromote(Object obj) throws Exception {

		this.insert("hrm.transferOrder.saveExperienceForPromote", obj);
	}

	@Override
	public void saveExperienceForPromoteAndAffirm(Object obj) throws Exception {
		// 保存到HR_AFFIRM
		this.insert("hrm.transferOrder.saveAffirmor", obj);
		this.insert("hrm.transferOrder.saveExperienceForPromote", obj);
	}

	@Override
	public void saveExperienceForPromoteAndAffirmList(Object obj, List list)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
		this.insert("hrm.transferOrder.saveExperienceForPromote", obj);
	}

	@Override
	public void saveExperienceForPromoteAndUpHrEmployee(Object obj)
			throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForPromote", obj);
		this.insert("hrm.transferOrder.updateHrEmployee", obj);
	}

	@Override
	public void saveExperienceForPromoteAndNoUpHEe(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForPromote", obj);
	}

	public int checkSaveTransferOrderUpgradeInprogres(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"hrm.transferOrder.checkSaveTransferOrderUpgradeInprogres",
							object), "0"), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	public int checkSaveTransferOrderUpgrade1(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"hrm.transferOrder.checkSaveTransferOrderUpgrade1",
							object), "0"), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	public int checkSaveTransfer(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkSaveTransfer",
							object), "0"), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	public int checkSaveTransferNormal(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(
											this
													.queryForObject(
															"hrm.transferOrder.checkSaveTransferNormal",
															object), "0"),
							Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	public int checkSaveTransferPromote(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"hrm.transferOrder.checkSaveTransferPromote",
							object), "0"), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	public int checkSaveResignation(Object object) {
		int returnInt = 0;
		try {
			returnInt += NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkSaveResignation",
							object), "0"), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getViewPluralityList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getViewResignList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getViewPluralityList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getViewPluralityList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getViewPluralityList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息总数（get Insurance Personnel Cnt）
	 * 
	 * @param List
	 * @return
	 */
	public int getViewPluralityCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getViewPluralityCnt",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 检查此人是否可做兼职发令
	 * 
	 * @param List
	 * @return
	 */
	public int checkSavePlurality(Object object) {
		int returnInt = 0;
		try {

			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkSavePlurality",
							object), "0"), Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 保存兼职发令
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public void saveExperienceForPlurality(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperiencePlurality", obj);

	}

	/**
	 * 保存兼职发令
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public void saveExperienceForPluralityAndAffirm(Object obj)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insert("hrm.transferOrder.saveAffirmor", obj);
		this.insert("hrm.transferOrder.saveExperiencePlurality", obj);
	}

	/**
	 * 保存兼职发令
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public void saveExperienceForPluralityAndAffirmList(Object obj, List list)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
		this.insert("hrm.transferOrder.saveExperiencePlurality", obj);
	}

	@Override
	public void updateExperienceForPlurality(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateExperienceForPlurality", obj);
	}

	@Override
	public String getExpInsideNo(Object obj) throws Exception {
		String returnInt = "";
		returnInt = (String) this.queryForObject(
				"hrm.transferOrder.getExpInsideNo", obj);
		return returnInt;
	}

	@Override
	public String getExpInsideNo1(Object obj) throws Exception {
		String returnInt = "";
		returnInt = (String) this.queryForObject(
				"hrm.transferOrder.getExpInsideNo1", obj);
		return returnInt;
	}

	/**
	 * 检查此人是否可做解除兼职发令
	 * 
	 * @param List
	 * @return
	 */
	public int checkedExpInsideNo(Object object) {
		int returnInt = 0;
		try {

			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkedExpInsideNo",
							object), "0"), Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForRemovePlurality(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForRemovePlurality", obj);
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForRemovePluralityAndUpPlurality(Object obj)
			throws Exception {
		this.update("hrm.transferOrder.updatePlurality", obj);
		this.insert("hrm.transferOrder.saveExperienceForRemovePlurality", obj);
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForRemovePluralityAndAffirm(Object obj)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insert("hrm.transferOrder.saveAffirmor", obj);
		this.insert("hrm.transferOrder.saveExperienceForRemovePlurality", obj);
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForRemovePluralityAndAffirmList(Object obj,
			List list) throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
		this.insert("hrm.transferOrder.saveExperienceForRemovePlurality", obj);
	}

	@SuppressWarnings("unchecked")
	public List getPluralityForRemove(Object obj) throws Exception {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getPluralityForRemove", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public void updatePlurality(Object obj) throws Exception {
		this.update("hrm.transferOrder.updatePlurality", obj);
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List for Suspended ）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSuspendList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getSuspendList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List for Suspended ）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSuspendList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getSuspendList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getSuspendList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息总数（get Insurance Personnel Cnt for Suspended ）
	 * 
	 * @param List
	 * @return
	 */
	public int getSuspendListCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"hrm.transferOrder.getSuspendListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 检查此人是否可做停职发令
	 * 
	 * @param List
	 * @return
	 */
	public int checkSaveSuspend(Object object) {
		int returnInt = 0;
		try {

			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkSaveSuspend",
							object), "0"), Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForSuspend(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForSuspend", obj);
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForSuspendAndAffirm(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForSuspend", obj);
		this.insert("hrm.transferOrder.saveAffirmor", obj);
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForSuspendAndAffirmList(Object obj, List list)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
		this.insert("hrm.transferOrder.saveExperienceForSuspend", obj);
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForSuspendNoaffirm(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForSuspend", obj);
	}

	@Override
	public void updateHrEmployeeForSuspend(Object object) throws Exception {
		this.update("hrm.transferOrder.updateHrEmployeeForSuspend", object);
		this.insert("hrm.transferOrder.saveExperienceForSuspend", object);
		this.insert("hrm.transferOrder.insertArApplyResultForArApply", object);
		this.insert("hrm.transferOrder.caculateDetailP", object);
	}

	@Override
	public String getExpInsideNoForSuspend(Object obj) throws Exception {
		String returnInt = "";
		returnInt = (String) this.queryForObject(
				"hrm.transferOrder.getExpInsideNoForSuspend", obj);
		return returnInt;
	}

	@Override
	public String getStatusCodeForSuspend(Object obj) throws Exception {
		String returnInt = "";
		returnInt = (String) this.queryForObject(
				"hrm.transferOrder.getStatusCodeForSuspend", obj);
		return returnInt;
	}

	@Override
	public void updateSuspend(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateSuspend", obj);
		this.insert("hrm.transferOrder.saveExperienceForSuspend", obj);
		this.update("hrm.transferOrder.updateHrEmployeeForSuspend", obj);
	}

	@Override
	public void updateHrEmployeeForReinstated(Object obj) throws Exception {
		this.update("hrm.transferOrder.updateHrEmployeeForReinstated", obj);
		this.insert("hrm.transferOrder.saveExperienceForSuspend", obj);
		this.update("hrm.transferOrder.updateSuspend", obj);
		this.update("hrm.transferOrder.updateArApplyResult", obj);
		this.insert("hrm.transferOrder.caculateDetailP", obj);
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getRewardList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getRewardList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getRewardList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getRewardList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getRewardList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息总数（get Insurance Personnel Cnt）
	 * 
	 * @param List
	 * @return
	 */
	public int getRewardListCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"hrm.transferOrder.getRewardListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForReward(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForReward", obj);
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForRewardAndAffirm(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForReward", obj);
		this.insert("hrm.transferOrder.saveAffirmor", obj);
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForRewardAndAffirmList(Object obj, List list)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
		this.insert("hrm.transferOrder.saveExperienceForReward", obj);
	}

	/**
	 * 检查此人是否可做奖励发令
	 * 
	 * @param List
	 * @return
	 */
	public int checkSaveReward(Object object) {
		int returnInt = 0;
		try {

			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(
					this.queryForObject("hrm.transferOrder.checkSaveReward",
							object), "0"), Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPunishMentList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getPunishMentList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPunishMentList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getPunishMentList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getPunishMentList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息总数（get Insurance Personnel Cnt）
	 * 
	 * @param List
	 * @return
	 */
	public int getPunishMentListCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getPunishMentListCnt",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	@Override
	public List getOrderParmList(Object obj) throws Exception {
		List returnList = new ArrayList();
		try {

			returnList = this.queryForList("hrm.empinfo.getOrderParmList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 检查此人是否可做奖励发令
	 * 
	 * @param List
	 * @return
	 */
	public int checkSavePunishMent(Object object) {
		int returnInt = 0;
		try {

			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkSavePunishMent",
							object), "0"), Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForPunishMent(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForPunishMent", obj);
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForPunishMentAndAffirm(Object obj)
			throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForPunishMent", obj);
		this.insert("hrm.transferOrder.saveAffirmor", obj);
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForPunishMentAndAffirmList(Object obj, List list)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
		this.insert("hrm.transferOrder.saveExperienceForPunishMent", obj);
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPayriseList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getPayriseList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPayriseList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getPayriseList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getPayriseList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息总数（get Insurance Personnel Cnt）
	 * 
	 * @param List
	 * @return
	 */
	public int getPayriseListCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"hrm.transferOrder.getPayriseListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 获取pa_basic_item表里的数据
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List paBasicItemList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.transferOrder.paBasicItemList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getReturnValueByItemNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getReturnValueByItemNo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	public int checkSavePayrise(Object object) {
		int returnInt = 0;
		try {

			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkSavePayrise",
							object), "0"), Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	public int checkSavePayriseByStartDate(Object object) {
		int returnInt = 0;
		try {

			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"hrm.transferOrder.checkSavePayriseByStartDate",
							object), "0"), Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForPayrise(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForPayrise", obj);
		this.insert("hrm.transferOrder.saveAffirmor", obj);
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForPayriseAndAffirmList(Object obj, List list)
			throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForPayrise", obj);
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
	}

	@SuppressWarnings("unchecked")
	public void saveExperienceForPayriseNoAffirm(Object obj) throws Exception {
		this.insert("hrm.transferOrder.updatePayrise", obj);
		this.insert("hrm.transferOrder.saveExperienceForPayrise", obj);
		this.insert("hrm.transferOrder.updatePaBasicData", obj);
		this.insert("hrm.transferOrder.savePaBasicData", obj);

	}

	@SuppressWarnings("unchecked")
	public void saveExperienceOnlyPayrise(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForPayrise", obj);
	}

	/**
	 * 取得日期差(get date count)
	 * 
	 * @param List
	 * @return
	 */
	public int getDateCount(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getDateCount", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 根据当前生效时间和person_id取出HR_EMP_DEPT表里面的 当前生效时间前一条
	 * 
	 * @param obj
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getBeforeDataForDept(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getBeforeDataForDept", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 根据当前生效时间和person_id取出HR_EMP_DEPT表里面的 当前生效时间后一条
	 * 
	 * @param obj
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getAfterDataForDept(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getAfterDataForDept", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 插入中间表数据并修改上一条数据 调动发令(部门变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveAndUpdateForUpGradeHrEmpDept(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpDeptForUpGrade", obj);
		this.insert("hrm.transferOrder.updateHrEmpDeptForUpGrade", obj);
	}

	/**
	 * 插入中间表数据并修改上一条数据 调动发令(部门变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveAndUpdateForAgentHrEmpDept(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpDeptForAgent", obj);
		this.insert("hrm.transferOrder.updateHrEmpDeptForUpGrade", obj);
	}

	/**
	 * 插入中间表数据 调动发令(部门变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveHrEmpDeptForUpGrade(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpDeptForUpGrade", obj);
	}

	/**
	 * 插入中间表数据 代理发令(部门变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveHrEmpDeptForAgent(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpDeptForAgent", obj);
	}

	/**
	 * 插入中间表数据并修改上一条数据 调动发令(职责变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveAndUpdateForUpGradeHrEmpDuty(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpDutyForUpGrade", obj);
		this.insert("hrm.transferOrder.updateHrEmpDutyForUpgrade", obj);
	}

	/**
	 * 插入中间表数据并修改上一条数据 调动发令(代理)
	 * 
	 * @param obj
	 */
	@Override
	public void saveAndUpdateForAgentHrEmpDuty(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpDutyForAgent", obj);
		this.insert("hrm.transferOrder.updateHrEmpDutyForAgent", obj);
	}

	/**
	 * 根据当前生效时间和person_id取出HR_EMP_DUTY表里面的 当前生效时间前一条 (职责变动)
	 * 
	 * @param obj
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getBeforeDataForDuty(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getBeforeDataForDuty", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 根据当前生效时间和person_id取出HR_EMP_DUTY表里面的 当前生效时间后一条 (职责变动)
	 * 
	 * @param obj
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getAfterDataForDuty(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getAfterDataForDuty", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 插入中间表数据 调动发令(职责变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveHrEmpDutyForUpGrade(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpDutyForUpGrade", obj);
	}

	/**
	 * 插入中间表数据 调动发令(代理)
	 * 
	 * @param obj
	 */
	@Override
	public void saveHrEmpDutyForAgent(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpDutyForAgent", obj);
	}

	/**
	 * 根据当前生效时间和person_id取出HR_EMP_POST表里面的 当前生效时间前一条 职级名称(职务)变动
	 * 
	 * @param obj
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getBeforeDataForPost(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getBeforeDataForPost", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 根据当前生效时间和person_id取出HR_EMP_POST表里面的 当前生效时间后一条 职级名称(职务)变动
	 * 
	 * @param obj
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getAfterDataForPost(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getAfterDataForPost", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 插入中间表数据并修改上一条数据 调动发令(职级名称(职务)变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveAndUpdateForUpGradeHrEmpPost(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpPostForUpGrade", obj);
		this.insert("hrm.transferOrder.updateHrEmpPostForUpgrade", obj);
	}

	/**
	 * 插入中间表数据 调动发令(部门变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveHrEmpPostForUpGrade(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpPostForUpGrade", obj);
	}

	/**
	 * 根据当前生效时间和person_id取出HR_EMP_POST表里面的 当前生效时间前一条 职级名称(职务)变动
	 * 
	 * @param obj
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getBeforeDataForPostGrade(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getBeforeDataForPostGrade", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 根据当前生效时间和person_id取出HR_EMP_POST表里面的 当前生效时间后一条 职级名称(职务)变动
	 * 
	 * @param obj
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getAfterDataForPostGrade(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getAfterDataForPostGrade", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 插入中间表数据并修改上一条数据 调动发令(职级变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveAndUpdateForUpGradeHrEmpPostGrade(Object obj)
			throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpPostGradeForUpGrade", obj);
		this.insert("hrm.transferOrder.updateHrEmpPostGradeForUpgrade", obj);
	}

	/**
	 * 插入中间表数据 调动发令(部门变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveHrEmpPostGradeForUpGrade(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpPostGradeForUpGrade", obj);
	}

	/**
	 * 根据当前生效时间和person_id取出HR_EMP_POSITION表里面的 当前生效时间前一条 职(岗)位变动
	 * 
	 * @param obj
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getBeforeDataForPosition(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getBeforeDataForPosition", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 根据当前生效时间和person_id取出HR_EMP_POSITION表里面的 当前生效时间后一条 职(岗)位变动
	 * 
	 * @param obj
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getAfterDataForPosition(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getAfterDataForPosition", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 插入中间表数据并修改上一条数据 调动发令(职级名称(职务)变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveAndUpdateForUpGradeHrEmpPosition(Object obj)
			throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpPositionForUpGrade", obj);
		this.insert("hrm.transferOrder.updateHrEmpPositionForUpgrade", obj);
	}

	/**
	 * 插入中间表数据并修改上一条数据 代理发令(职级名称(职务)变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveAndUpdateForAgentHrEmpPosition(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpPositionForAgent", obj);
		this.insert("hrm.transferOrder.updateHrEmpPositionForUpgrade", obj);
	}

	/**
	 * 插入中间表数据 调动发令(部门变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveHrEmpPositionForUpGrade(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpPositionForUpGrade", obj);
	}

	/**
	 * 插入中间表数据 代理发令(部门变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveHrEmpPositionForAgent(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpPositionForAgent", obj);
	}

	/**
	 * 根据当前生效时间和person_id取出HR_EMP_POSITION表里面的 当前生效时间前一条 工作地变动
	 * 
	 * @param obj
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getBeforeDataForWorkArea(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getBeforeDataForWorkArea", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 根据当前生效时间和person_id取出HR_EMP_POSITION表里面的 当前生效时间后一条 工作地变动
	 * 
	 * @param obj
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getAfterDataForWorkArea(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getAfterDataForWorkArea", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 插入中间表数据并修改上一条数据 调动发令(工作地变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveAndUpdateForUpGradeHrEmpWorkArea(Object obj)
			throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpWorkAreaForUpGrade", obj);
		this.insert("hrm.transferOrder.updateHrEmpWorkAreaForUpgrade", obj);
	}

	/**
	 * 插入中间表数据并修改上一条数据 代理发令(工作地变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveAndUpdateForAgentHrEmpWorkArea(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpWorkAreaForAgent", obj);
		this.insert("hrm.transferOrder.updateHrEmpWorkAreaForUpgrade", obj);
	}

	/**
	 * 插入中间表数据 调动发令(工作地变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveHrEmpWorkAreaForUpGrade(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpWorkAreaForUpGrade", obj);
	}

	/**
	 * 插入中间表数据 代理发令(工作地变动)
	 * 
	 * @param obj
	 */
	@Override
	public void saveHrEmpWorkAreaForAgent(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveHrEmpWorkAreaForAgent", obj);
	}

	/**
	 * 取得所有人员信息列表（get Insurance agent List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAgentList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getAgentList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAgentList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getAgentList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getAgentList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息总数(get Insurance agent Cnt)
	 * 
	 * @param List
	 * @return
	 */
	public int getAgentCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getAgentCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	public int checkSaveTransferOrderAgent(Object object) {
		int returnInt = 0;
		try {

			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"hrm.transferOrder.checkSaveTransferOrderAgent",
							object), "0"), Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 提交代理发令（save Transfer Order agent）
	 * 
	 * @param obj
	 *            list
	 * @return
	 * @throws Exception
	 */
	public void saveExperienceForAgentAndAffirmList(Object obj, List list)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
		this.insert("hrm.transferOrder.saveExperienceForAgent", obj);
	}

	/**
	 * 代理发令()
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveExperienceForAgent(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForAgent", obj);
	}

	@Override
	public void saveExperienceForAgentAndUpHrEmployee(Object obj)
			throws Exception {
		this.insert("hrm.transferOrder.updateHrEmployee", obj);
		this.insert("hrm.transferOrder.saveExperienceForAgent", obj);
		this.insert("hrm.transferOrder.updateHrEmployeeAgentPostGradeNo", obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getAgentCountForHrEmployeesAgentPostGradeNo(Object object) {
		int returnInt = 0;
		try {

			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(
											this
													.queryForObject(
															"hrm.transferOrder.getAgentCountForHrEmployeesAgentPostGradeNo",
															object), "0"),
							Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getDateDifference(Object object) {
		int returnInt = 0;
		try {

			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getDateDifference",
							object), "0"), Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getAgentCountByPid(Object object) {
		int returnInt = 0;
		try {

			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getAgentCountByPid",
							object), "0"), Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	@Override
	public String getExpInsideNoForAgent(Object obj) throws Exception {
		String returnInt = "";
		returnInt = (String) this.queryForObject(
				"hrm.transferOrder.getExpInsideNoForAgent", obj);
		return returnInt;
	}

	@Override
	public void saveAgentAndUpHrEmployeeForCancel(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForAgent", obj);
		this.insert("hrm.transferOrder.updateHrEmployeeAgentPostGradeNo", obj);
		this.insert("hrm.transferOrder.updateAgentEndDate", obj);
	}

	@Override
	public void saveAgentForCancel(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForAgent", obj);
	}

	@Override
	public int checkNeedChangeHeEmptypeCode(Object object) {
		int returnInt = 0;
		try {

			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"hrm.transferOrder.checkNeedChangeHeEmptypeCode",
							object), "0"), Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	// 定时器start

	/**
	 * 查询需要更新到hr_employee表里面的 from hr_employee_temp
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEmployeeTempList() {
		List returnList = new ArrayList();

		try {
			returnList = this
					.queryForList("hrm.transferOrder.getEmployeeTempList");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改HR_EXPERIENCE_INSIDE activity=1
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrEmployeeTemp(Object obj) {
		try {
			this.update("hrm.transferOrder.updateHrEmployeeTemp", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询需要更新到hr_personal_info表里面的 from hr_personal_info_temp
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalInfoTempList() {
		List returnList = new ArrayList();

		try {
			returnList = this
					.queryForList("hrm.transferOrder.getPersonalInfoTempList");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 保存到hr_employee,hr_personal_info(入职发令)
	 * 
	 * @param List
	 */
	@SuppressWarnings("unchecked")
	public void saveEmpAndPerForHireTimers(List emplist, List perList) {
		try {
			this
					.insertForList(
							"hrm.transferOrder.saveEmployeeToEmployeeTypeList",
							emplist);
			this.insertForList(
					"hrm.transferOrder.savePersonalInfoToPersonalInfoTempList",
					perList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 保存到hr_employee(入职发令)
	 * 
	 * @param List
	 */
	@SuppressWarnings("unchecked")
	public void saveEmpForHireTimers(List emplist) {
		try {
			this
					.insertForList(
							"hrm.transferOrder.saveEmployeeToEmployeeTypeList",
							emplist);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存到hr_personal_info(入职发令)
	 * 
	 * @param List
	 */
	@SuppressWarnings("unchecked")
	public void savePerForHireTimers(List perList) {
		try {
			this.insertForList(
					"hrm.transferOrder.savePersonalInfoToPersonalInfoTempList",
					perList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询出调动已生效未更新的数据list (调动发令)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getExperienceInsideListForUpgrade() {
		List returnList = new ArrayList();

		try {
			returnList = this
					.queryForList("hrm.transferOrder.getExperienceInsideListForUpgrade");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 查询出调动已生效未更新的数据list (调动发令)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getExperienceInsideListForPayStep() {
		List returnList = new ArrayList();

		try {
			returnList = this
					.queryForList("hrm.transferOrder.getExperienceInsideListForPayStep");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改HR_EMPLOYEE HR_EXPERIENCE_INSIDE(调动发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateEmpAndExpForUpgradeTimers(Object obj) {
		try {
			String transNo = ((Map) obj).get("TRANS_NO").toString();

			this.update("hrm.transferOrder.updateHrEmployeeForUpgrade", obj);

			// 取消兼职 123315,代理解除 123353,借调解除 123358,待处理解除 123360
			if (transNo.equals("123315") || transNo.equals("123353")
					|| transNo.equals("123358") || transNo.equals("123360")) {
				String Exp_Inside_No = ((Map) obj).get("EXP_INSIDE_NO")
						.toString();
				((Map) obj).put("EXP_INSIDE_NO", Exp_Inside_No);
				this
						.update(
								"hrm.transferOrder.updateHrExperienceInsideForUpgradeEndDate",
								obj);
			}

			this.update("hrm.transferOrder.updateHrExperienceInsideForUpgrade",
					obj);
			// 调令类型
			Map empMap = (LinkedHashMap) obj;
			String orderType = StringUtil.checkNull(empMap.get("TRANS_NO"));
			if (orderType.equals("123347")) {// 转正调令
				String transCode = StringUtil.checkNull(empMap
						.get("TRANS_CODE"));
				if (transCode.equals("4085")) {// 实习转试用
					empMap.put("STATUS_CODE1", "1374");
					this.update(
							"ess.trans.updateHrEmpStatusForTransactionTrans",
							empMap);
				} else if (!transCode.equals("")) {
					empMap.put("STATUS_CODE1", "14891");// 正式
					this.update(
							"ess.trans.updateHrEmpStatusForTransactionTrans",
							empMap);
				}
			} else if (orderType.equals("123318")) {// 休职
				empMap.put("EMP_OFFICE1", "123450");// 休职
				empMap.put("STATUS_CODE1", "1377");// 停职
				this.update(
						"ess.trans.updateHrEmpOfficeStatusForTransactionTrans",
						empMap);
			} else if (orderType.equals("123346")) {// 复职
				empMap.put("EMP_OFFICE1", "15119");// 在职
				empMap.put("STATUS_CODE1", "14891");// 正式
				this.update(
						"ess.trans.updateHrEmpOfficeStatusForTransactionTrans",
						empMap);
			} else if (orderType.equals("123356")) {// 离职
				empMap.put("EMP_OFFICE1", "15120");// 离职
				empMap.put("STATUS_CODE1", "1375");// 离职
				empMap.put("DATE_LEFT", empMap.get("START_DATE").toString()
						.subSequence(0, 10));// 离职日期

				this
						.update(
								"ess.trans.updateHrEmpOfficeStatusForTransactionTransDateLeft",
								empMap);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询需要更新到hr_employee表里面的 from HR_PROBATION (转正发令)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getProbationListForTransferNormal() {
		List returnList = new ArrayList();

		try {
			returnList = this
					.queryForList("hrm.transferOrder.getProbationListForTransferNormal");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改HrEmployee (转正发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrEmployeeForTN(Object obj) {
		try {
			this.update("hrm.transferOrder.updateHrEmployeeForTN", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 修改HrEmployee (转正发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrEmployeeDateStated(Object obj) {
		try {
			this.update("hrm.transferOrder.updateHrEmployeeDateStated", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 修改probation activity=1 (转正发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateProbationForTransferNormal(Object obj) {
		try {
			this.update("hrm.transferOrder.updateProbationForTransferNormal",
					obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询需要更新到hr_employee表里面的 from HR_EXPERIENCE_INSIDE (晋升降职发令)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getExperienceInsideListForTransferPromote() {
		List returnList = new ArrayList();

		try {
			returnList = this
					.queryForList("hrm.transferOrder.getExperienceInsideListForTransferPromote");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 获取兼职发令列表 (兼职、取消兼职发令)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPluralityList() {
		List returnList = new ArrayList();

		try {
			returnList = this
					.queryForList("hrm.transferOrder.getPluralityList");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 获取取消兼职发令列表 (兼职、取消兼职发令)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List PluralityListForCancle() {
		List returnList = new ArrayList();

		try {
			returnList = this
					.queryForList("hrm.transferOrder.PluralityListForCancle");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改PLURALITY (兼职发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updatePluralityForTimer(Object obj) {
		try {
			this.update("hrm.transferOrder.updatePluralityForTimer", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 修改PLURALITY (取消兼职发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updatePluralityCancleForTimer(Object obj) {
		try {
			this.update("hrm.transferOrder.updatePluralityForTimer", obj);
			this.update("hrm.transferOrder.updatePluralityCancleForTimer", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取停职发令列表 (停职发令)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSuspendListForTimer() {
		List returnList = new ArrayList();

		try {
			returnList = this
					.queryForList("hrm.transferOrder.getSuspendListForTimer");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改hr_employee STATUS_CODE(停职发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrEmployeesStatusCode(Object obj) {
		try {
			this.update("hrm.transferOrder.updateHrEmployeesStatusCode", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据personId获取cpnyId(停职发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public String getCpnyIdByPersonId(Object obj) {
		String returnInt = "";
		try {
			returnInt = (String) this.queryForObject(
					"hrm.transferOrder.getCpnyIdByPersonId", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * (停职发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateDateForSuspendTimers(Object obj) {
		try {
			this.update("hrm.transferOrder.updateHrSupension", obj);
			this.update("hrm.transferOrder.updateHrEmployeesStatusCode", obj);
			this.insert("hrm.transferOrder.insertArApplyResultForTimers", obj);
			this.insert("hrm.transferOrder.caculateDetailP", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * (复职发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateDateForReinstatedTimers(Object obj) {
		try {
			this.update("hrm.transferOrder.updateHrEmployeeForReinstated", obj);
			this.update("hrm.transferOrder.updateHrSuspensionForReinstated",
					obj);
			this.update("hrm.transferOrder.updateArApplyResult", obj);
			this.insert("hrm.transferOrder.caculateDetailP", obj);
			this.update("hrm.transferOrder.updateHrSupension", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取复职发令列表 (复职发令)
	 * 
	 * @return returnList
	 */
	@SuppressWarnings("unchecked")
	public List getReinstatedListForTimer() {
		List returnList = new ArrayList();

		try {
			returnList = this
					.queryForList("hrm.transferOrder.getReinstatedListForTimer");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 获取奖励发令列表 (奖励发令)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getRewardListForTimer() {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("hrm.transferOrder.getRewardListForTimer");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改HrReward (奖励发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrRewardForTimers(Object obj) {
		try {
			this.update("hrm.transferOrder.updateHrRewardForTimers", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取惩戒发令列表 (惩戒发令)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPunishmentListForTimer() {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("hrm.transferOrder.getPunishmentListForTimer");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改HrPunishment (惩戒发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrPunishmentForTimers(Object obj) {
		try {
			this.update("hrm.transferOrder.updateHrPunishmentForTimers", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取离职发令列表 (离职发令)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getResignListForTimer() {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("hrm.transferOrder.getResignListForTimer");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改HrPunishment (惩戒发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrResignForTimers(Object obj) {
		try {
			this.update("hrm.transferOrder.updateHrEmployeeForTimers", obj);
			this.update("hrm.transferOrder.updateHrEmpDeptForTimers", obj);
			this.update("hrm.transferOrder.updateHrEmpDutyForTimers", obj);
			this.update("hrm.transferOrder.updateHrEmpPostForTimers", obj);
			this.update("hrm.transferOrder.updateHrEmpPostGradeForTimers", obj);
			this.update("hrm.transferOrder.updateHrEmpPositionForTimers", obj);
			this.update("hrm.transferOrder.updateHrEmpWorkAreaForTimers", obj);
			this.update("hrm.transferOrder.updateHrResignForTimers", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取薪资调整发令列表 (薪资调整发令)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPayriseListForTimer() {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("hrm.transferOrder.getPayriseListForTimer");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改HrParise (薪资调整发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrPayriseForTimers(Object obj) {
		try {
			this.update("hrm.transferOrder.updatePayriseForTimers", obj);
			this.update("hrm.transferOrder.updatePaBasicDataForTimers", obj);
			this.insert("hrm.transferOrder.savePaBasicDataForTimers", obj);
			this
					.insert("hrm.transferOrder.updatePayRiseActivityForTimers",
							obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取代理发令列表 (代理发令)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAgentListForTimer() {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("hrm.transferOrder.getAgentListForTimer");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改HrEmployee (代理发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHremployeeForTimers(Object obj) {
		try {
			this.insert("hrm.transferOrder.updateEmpForTimers", obj);
			this.update("hrm.transferOrder.updateHrAgentForTimers", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取取消代理发令列表 (取消代理发令)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCancleAgentListForTimer() {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("hrm.transferOrder.getCancleAgentListForTimer");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改HrEmployee (取消代理发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHremployeeForCancleAgentTimers(Object obj) {
		try {
			this.insert("hrm.transferOrder.updateHrEmployeeAgentPostGradeNo",
					obj);
			this.insert("hrm.transferOrder.updateAgentEndDate", obj);
			this.update("hrm.transferOrder.updateHrAgentForTimers", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 定时器end

	/**
	 * 取得所有人员信息列表（get Insurance agent List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEmpSearchList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getEmpSearchList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEmpSearchList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getEmpSearchList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getEmpSearchList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有人员信息总数(get Insurance agent Cnt)
	 * 
	 * @param List
	 * @return
	 */
	public int getEmpSearchCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getEmpSearchCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 取得所有人员信息列表（get Insurance Personnel List）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEidListForSearch(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getEidListForSearch", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 检查该人员在此生效日期 是否可以做调动发令
	 * 
	 * @param obj
	 * @return returnInt
	 */
	public int checkPersonId(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkPersonId", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	public int checkedIdcardNoReqStatus(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkedIdcardNoReqStatus", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	public int checkIdcardNo(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkIdcardNo", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 检查再入职时是否为离职
	 * 
	 */
	public List checkIdcardNoAgain(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.checkIdcardNoAgain", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取号俸列表
	 */
	@SuppressWarnings("unchecked")
	public List getHaoFengList(Object object) {

		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.transferOrder.getHaoFengList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public void savePersonChangeInfo(Object object) throws SQLException {
		this.getSqlMapClientTemplate().insert(
				"hrm.transferOrder.savePersonChangeInfo",
				(LinkedHashMap) object);
	}

	// 转正时在HR_CHANGE_STATUS表中添加一条数据HR_CHANGE_STATUS表
	public void savePersonStatus(Object object) {
		this.getSqlMapClientTemplate().insert(
				"hrm.transferOrder.savePersonStatus", (LinkedHashMap) object);
	}

	// 转正时更新HR_CHANGE_STATUS表
	public void updatePersonStatus(Object object) {
		this.getSqlMapClientTemplate().insert(
				"hrm.transferOrder.updatePersonStatus", (LinkedHashMap) object);
	}

	// 离职时在HR_CHANGE_STATUS表中添加一条数据HR_CHANGE_STATUS表
	public void savePersonStatusl(Object object) {
		this.getSqlMapClientTemplate().insert("hrm.transferOrder.savePersonStatusl", (LinkedHashMap) object);
	}

	// 离职时更新HR_ASSIGNMENT表
	public void saveResignOrder(Object object) {
		this.getSqlMapClientTemplate().insert("hrm.transferOrder.saveResignOrder",(LinkedHashMap) object);
	}

	// 离职不需裁决时查询该职员的最后一条打卡数据
	public String getLastRecord(Object object) {
		List list = this.getSqlMapClientTemplate().queryForList("hrm.transferOrder.getLastRecord", (LinkedHashMap) object);
		if (list.size() != 0 && list != null) {
			LinkedHashMap map = (LinkedHashMap) list.get(0);
			return map.get("LAST_RECORD").toString();
		}
		return "";
	}

	// 离职时更新HR_RESIGNATION表中LAST_CARD_DATE字段
	public void changeLastRecord(Object object) throws SQLException {
		this.update("hrm.transferOrder.changeLastRecord",(LinkedHashMap) object);
	}

	/**
	 * 号奉发令查询list
	 */
	@SuppressWarnings("unchecked")
	public List getPayStepList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getPayStepList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 号奉发令查询list
	 */
	@SuppressWarnings("unchecked")
	public List getPayStepList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getPayStepList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getPayStepList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 号奉发令查询cnt
	 */
	public int getPayStepListCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"hrm.transferOrder.getPayStepListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	// 如果为法人为C04，劳务转正时，修改员工职级1级为4级，并修改入职日期
	public void specialUpdateForC01(Object object) throws SQLException {
		this.update("hrm.transferOrder.specialUpdateForC01ForEmp",
				(LinkedHashMap) object);
		// this.update("hrm.transferOrder.specialUpdateForC01ForIns",(LinkedHashMap)
		// object);
	}

	public int getPopMarkFlag(Object obj) {
		int returnInt = 0;
		try {
			String param_value = ObjectUtils.toString(this.queryForObject(
					"hrm.transferOrder.getPopMarkFlag", obj));

			if (null == param_value || "".equals(param_value)) {
				return returnInt;
			} else {
				returnInt = NumberUtils.parseNumber(param_value, Integer.class);
			}

			// returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
			// .queryForObject("hrm.transferOrder.getPopMarkFlag", obj)),
			// Integer.class);

			//			
			// Object a =
			// this.queryForObject("hrm.transferOrder.getPopMarkFlag", obj);
			// String b = ObjectUtils.toString(a);
			// int c = NumberUtils.parseNumber(b,Integer.class);
			// System.out.println(a);
			// System.out.println(b);
			// System.out.println(c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnInt;
	}

	@SuppressWarnings("unchecked")
	public List getSaParamItemParamList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getSaParamItemParamList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getBnParamItemParamList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getBnParamItemParamList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getInParamItemParamList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getInParamItemParamList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getPaBasicItemParamList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getPaBasicItemParamList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public void saveSaBnIn(HttpServletRequest request, LinkedHashMap sadata,
			LinkedHashMap bndata, LinkedHashMap indata, LinkedHashMap padata,
			LinkedHashMap paramMap) throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("interCpnyID", admin.getCpnyId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", (String) this.queryForObject(
				"hrm.transferOrder.getPersonId", paramMap));

		List getSaParamItemParamList = this.queryForList(
				"hrm.transferOrder.getSaParamItemParamList", paramMap);
		List getBnParamItemParamList = this.queryForList(
				"hrm.transferOrder.getBnParamItemParamList", paramMap);
		List getInParamItemParamList = this.queryForList(
				"hrm.transferOrder.getInParamItemParamList", paramMap);
		List paBasicItemParamList = this.queryForList(
				"hrm.transferOrder.getPaBasicItemParamList", paramMap);

		// 如果paBasicItemParamList不为空 那么 循环取出对应的param——no 和对应的值
		if (paBasicItemParamList.size() > 0) {
			for (int i = 0; i < paBasicItemParamList.size(); i++) {
				LinkedHashMap palinkedHM = (LinkedHashMap) paBasicItemParamList
						.get(i);
				String paramno = palinkedHM.get("PARAM_NO").toString();
				paramMap.put("PARAM_NO", paramno);
				paramMap.put("RETURN_VALUE", padata.get(paramno));
				this.insert("hrm.transferOrder.savePaItemParamData", paramMap);
			}
		}

		// 如果saParamItemParamList不为空 那么 循环取出对应的param——no 和对应的值
		if (getSaParamItemParamList.size() > 0) {
			for (int i = 0; i < getSaParamItemParamList.size(); i++) {
				LinkedHashMap salinkedHM = (LinkedHashMap) getSaParamItemParamList
						.get(i);
				String paramno = salinkedHM.get("PARAM_NO").toString();
				paramMap.put("PARAM_NO", paramno);
				paramMap.put("RETURN_VALUE", sadata.get(paramno));
				this.insert("hrm.transferOrder.savePaParamData", paramMap);
			}
		}

		// 如果getBnParamItemParamList不为空 那么 循环取出对应的param——no 和对应的值
		if (getBnParamItemParamList.size() > 0) {
			for (int i = 0; i < getBnParamItemParamList.size(); i++) {
				LinkedHashMap bnlinkedHM = (LinkedHashMap) getBnParamItemParamList
						.get(i);
				String paramno = bnlinkedHM.get("PARAM_NO").toString();
				paramMap.put("PARAM_NO", paramno);
				paramMap.put("RETURN_VALUE", bndata.get(paramno));
				this.insert("hrm.transferOrder.saveBnParamData", paramMap);
			}
		}

		// 如果getInParamItemParamList不为空 那么 循环取出对应的param——no 和对应的值
		if (getInParamItemParamList.size() > 0) {
			for (int i = 0; i < getInParamItemParamList.size(); i++) {
				LinkedHashMap inlinkedHM = (LinkedHashMap) getInParamItemParamList
						.get(i);
				String paramno = inlinkedHM.get("PARAM_NO").toString();
				paramMap.put("PARAM_NO", paramno);
				paramMap.put("RETURN_VALUE", indata.get(paramno));
				this.insert("hrm.transferOrder.saveInParamData", paramMap);
			}
		}

	}

	// 获取号俸列表
	public List getHaofengList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.transferOrder.haoFengList",
					(LinkedHashMap) object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	// 获取旧职级列表
	public List getOldPostGradeList2(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getOldPostGradeList2",
					(LinkedHashMap) object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	// 检查有无其他号俸发令
	public int checkSavePayStep(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkSavePayStep",
							object), "0"), Integer.class);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return returnInt;
	}

	public void saveExperienceForPayStepAndAffirm(Object obj, List list)
			throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
		this.insert("hrm.transferOrder.saveExperienceForPayStep", obj);
	}

	public void saveExperienceForPayStep(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForPayStep", obj);
	}

	public void savePayStep(Object obj) throws Exception {
		this.insert("hrm.transferOrder.saveExperienceForPayStep", obj);
		this.update("hrm.transferOrder.updateEmployee", obj);
		if (this.isFirstChangePayStep(obj) > 1) {
			this.update("hrm.transferOrder.updateEmpPayStep", obj);
		}
		this.insert("hrm.transferOrder.insertEmpPayStep", obj);
	}

	public void savePayStepForTimer(Object object) throws SQLException {
		this.update("hrm.transferOrder.updateEmployee", object);
		if (this.isFirstChangePayStep(object) > 0) {
			this.update("hrm.transferOrder.updateEmpPayStep", object);
		}
		this.insert("hrm.transferOrder.insertEmpPayStep", object);
	}

	public int isFirstChangePayStep(Object object) {
		int result = 0;
		try {
			result = Integer.parseInt(this.queryForObject(
					"hrm.transferOrder.isFirstChangePayStep", object)
					+ "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public int haveEalierPayStepData(Object object) {
		int result = 0;
		try {
			result = Integer.parseInt(this.queryForObject(
					"hrm.transferOrder.haveEalierPayStepData", object)
					+ "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据职级参数 查询对应的职等
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getGradeLevelNoByPostGradeNoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getGradeLevelNoByPostGradeNoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;

	}

	/**
	 * 查询详细路径信息
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getRecSourceList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getRecSourceList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 查询详细路径信息
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getRecSourceListForUpdate(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getRecSourceListForUpdate", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@Override
	public List getRecSourceDetailByRecSource(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getRecSourceDetailByRecSource", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@Override
	public List getTransList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.transferOrder.getTransList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 身份证18转15 的时候(首次入职)
	 */
	@Override
	public int checkIdcardNo15(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkIdcardNo15", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 身份证15转18 的时候（首次入职）
	 */
	@Override
	public int checkIdcardNo18(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.checkIdcardNo18", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 验证外国人身份证号或者护照号
	 */
	@Override
	public int checkedPassportNo(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"hrm.transferOrder.checkedPassportNo", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@Override
	public List checkIdcardNoAgain15(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this.queryForList(
					"hrm.transferOrder.checkIdcardNoAgain15", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List checkIdcardNoAgain18(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.checkIdcardNoAgain18", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getBLACKLISTList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getBLACKLISTList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getHrEmployeeList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getHrEmployeeList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getTranferOrderTitile(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getTranferOrderTitile", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getHrExperienceInsideByPersonId(Object object) {
		List returnList = new ArrayList();
		try {

			returnList = this
					.queryForList(
							"hrm.transferOrder.getHrExperienceInsideByPersonId",
							object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public void submitAddHrDispatch(List list) throws Exception {
		this.updateForList("hrm.transferOrder.submitAddHrDispatch", list);
	}
	@SuppressWarnings("unchecked")
	public void updatesubmitAddHrDispatch(List list) throws Exception {
		this.updateForList("hrm.transferOrder.updatesubmitAddHrDispatch", list);
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
			returnList = this.queryForList("hrm.transferOrder.getEmpIdList",
					obj);
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
				returnList = this.queryForList(
						"hrm.transferOrder.getEmpIdList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getEmpIdList", obj);
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
					.queryForObject("hrm.transferOrder.getEmpIdListCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	public int getAddHrDispatchCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.transferOrder.getAddHrDispatchCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	public void SaveHrExperienceInside(Object obj) throws Exception {
		this.insert("hrm.transferOrder.SaveHrExperienceInside", obj);
	}

	@SuppressWarnings("unchecked")
	public void SaveHrDispInside(String transNo) throws Exception {
		this.insert("hrm.transferOrder.SaveHrDispInside", transNo);
	}

	@SuppressWarnings("unchecked")
	public List getHrDispatch(Object obj) throws Exception {

		List returnList = new ArrayList();
		try {

			returnList = this.queryForList("hrm.transferOrder.getHrDispatch",
					obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;

	}
	@SuppressWarnings("unchecked")
	public List getHrDispatchUpdate(Object obj) throws Exception {

		List returnList = new ArrayList();
		try {

			returnList = this.queryForList("hrm.transferOrder.getHrDispatchUpdate",
					obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;

	}
	@SuppressWarnings("unchecked")
	public List getHrDispatch2(Object obj) throws Exception {

		List returnList = new ArrayList();
		try {

			returnList = this.queryForList("hrm.transferOrder.getHrDispatch2",
					obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;

	}

	@SuppressWarnings("unchecked")
	public List getHrDispatch3(Object obj) throws Exception {

		List returnList = new ArrayList();
		try {

			returnList = this.queryForList("hrm.transferOrder.getHrDispatch3",
					obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;

	}

	@SuppressWarnings("unchecked")
	public int getHrDispatchCnt(Object obj) throws Exception {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"hrm.transferOrder.getHrDispatchCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@SuppressWarnings("unchecked")
	public int getHrDispatchUpdateCnt(Object obj) throws Exception {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"hrm.transferOrder.getHrDispatchUpdateCnt", obj)),
					Integer.class);
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return returnInt;
	}

	@SuppressWarnings("unchecked")
	public List getHrDispatch(Object obj, int currentPage, int pageSize)
			throws Exception {

		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getHrDispatch", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getHrDispatch", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;

	}
	
	@SuppressWarnings("unchecked")
	public List getHrDispatchUpdate(Object obj, int currentPage, int pageSize)
			throws Exception {

		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getHrDispatchUpdate", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getHrDispatchUpdate", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;

	}

	@SuppressWarnings("unchecked")
	public void SaveHrExperienceInsideSave(Object obj) throws Exception {
		this.insert("hrm.transferOrder.SaveHrExperienceInsideSave", obj);
	}

	@SuppressWarnings("unchecked")
	public void SaveHrExperienceInsideSave1(Object obj) throws Exception {
		this.insert("hrm.transferOrder.SaveHrExperienceInsideSave1", obj);
	}

	@SuppressWarnings("unchecked")
	// 保存到临时表
	public void SaveHrExperienceInsideSave_send_1(Object obj) throws Exception {
		this.insert("hrm.transferOrder.SaveHrExperienceInsideSave_send_1", obj);
	}

	// 保存到正式表
	@SuppressWarnings("unchecked")
	public void SaveHrExperienceInsideSave_send_2(Object obj) throws Exception {
		this.insert("hrm.transferOrder.SaveHrExperienceInsideSave_send_2", obj);
	}

	/**
	 * 查询兼职调令是否发过取消兼职发令
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCountTransByrelation(Object obj) throws Exception {
		return this.queryForList("hrm.transferOrder.getCountTransByrelation",
				obj);
	}

	/**
	 * 根据输入的员工编号查询员工
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEmployeeByEmpId(Map paramMap) throws Exception {

		return this.queryForList("hrm.transferOrder.getEmployeeByEmpId",
				paramMap);
	}

	/**
	 * 保存“奖励”发令信息(临时储存)
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void storeReward(LinkedHashMap paramMap) throws Exception {
		this.insert("hrm.transferOrder.storeReward", paramMap);
	}

	/**
	 * 查询"奖励"保存过的记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getStoredRewardList(LinkedHashMap paramMap) throws Exception {
		return this.queryForList("hrm.transferOrder.getStoredRewardList",
				paramMap);
	}

	/**
	 * 保存"惩罚"发令信息(临时储存)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void storePunishment(LinkedHashMap paramMap) throws Exception {
		this.insert("hrm.transferOrder.storePunishment", paramMap);

	}

	/**
	 * 查询"惩罚"保存过的记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getStoredPunishmentList(LinkedHashMap paramMap)
			throws Exception {
		return this.queryForList("hrm.transferOrder.getStoredPunishmentList",
				paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getTransferOrderEmpList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getTransferOrderEmpList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getTransferOrderEmpList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getTransferOrderEmpList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getTransferOrderEmpList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getTransferOrderEmpListCnt(Object obj) throws Exception {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"hrm.transferOrder.getTransferOrderEmpListCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 保存“奖励”记录之前删除上次记录
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-8-28 上午12:24:01
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteStroedRewardRecordsForSave(LinkedHashMap map)
			throws Exception {
		this.delete("hrm.transferOrder.deleteStroedRewardRecordsForSave", map);
	}

	/**
	 * 发令时删除已保存的“奖励”
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-8-27 下午11:51:32
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteStoredRewardRecordsByParams(Map paramMap)
			throws Exception {
		this.delete("hrm.transferOrder.deleteStoredRewardRecordsByParams",
				paramMap);
	}

	/**
	 * 保存“惩罚”记录之前删除上次记录
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-8-28 上午12:24:01
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteStroedPunishmentRecordsForSave(LinkedHashMap map)
			throws Exception {
		this.delete("hrm.transferOrder.deleteStroedPunishmentRecordsForSave",
				map);

	}

	/**
	 * 发令时删除已保存的“惩罚”
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-8-27 下午11:51:32
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteStoredPunishmentRecordsByParams(Map paramMap)
			throws Exception {
		this.delete("hrm.transferOrder.deleteStoredPunishmentRecordsByParams",
				paramMap);
	}

	/**
	 * 查询“奖励”或“惩罚”记录条数
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-8-28 上午11:02:38
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getRewardOrPunishmentCnt(LinkedHashMap map) throws Exception {
		return (Integer) this.queryForObject(
				"hrm.transferOrder.getRewardOrPunishmentCnt", map);
	}

	/**
	 * 根据传入的PARENT_CODE_NO 查询出对应的CODE和NAME
	 */
	@Override
	public List getCodeList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("hrm.transferOrder.getCodeList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 通过职等 关联职级
	 */
	@Override
	public List getZhiDengAndZhiJi(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getZhiDengAndZhiJi", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 通过职级关联职责
	 */
	@Override
	public List getZhiJiAndZhiZe(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getZhiJiAndZhiZe", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 通过职级关联职级名称
	 */
	@Override
	public List getZhiJiAndZhiJiMing(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getZhiJiAndZhiJiMing", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 保存学历信息临时表
	 */
	@Override
	public void addEduactionInfo(Object obj) throws Exception {

		this.insert("hrm.transferOrder.insertEducation", obj);
	}

	/**
	 * 修改员工个人信息临时最终学历信息
	 */
	@Override
	public void updataEduaction(Object object) throws Exception {
		this.update("hrm.transferOrder.updataEduaction", object);

	}

	@Override
	public List getHrExperienceInsideSaveByTransCode(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getHrExperienceInsideSaveByTransCode",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getHrExperienceInsideByTransCode(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getHrExperienceInsideByTransCode",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getHrExperienceInsideByTransCodeDefault(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList(
							"hrm.transferOrder.getHrExperienceInsideByTransCodeDefault",
							object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public void deleteHrExperienceInsideSaveByPersonIds(Object object)
			throws Exception {
		this.delete(
				"hrm.transferOrder.deleteHrExperienceInsideSaveByPersonIds",
				object);
	}

	public void deleteHrExperienceInsideSaveByPersonId(Object object)
			throws Exception {
		this.delete("hrm.transferOrder.deleteHrExperienceInsideSaveByPersonId",
				object);
	}

	@Override
	public List getTranferOrderTitileInside(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getTranferOrderTitileInside", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getTranferOrderTitileInside1(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getTranferOrderTitileInside1", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getTranferOrderinsideListSerach(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getTranferOrderTitileInsideSerach",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getTranferOrderinsideList(Object object) {

		return this.getTranferOrderinsideList(object, -1, -1);
	}

	@Override
	public List getTranferOrderinsideList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(

				"hrm.transferOrder.getTranferOrderinsideList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getTranferOrderinsideList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getTranferOrderinsideListCnt(Object object) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"hrm.transferOrder.getTranferOrderinsideListCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 获得单个发令里决裁过的总数
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPassHrAffirmCountByExpInsideNo(Object obj) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"hrm.transView.getPassHrAffirmCountByExpInsideNo",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int cancelHrPluralityByNo(List list) throws Exception {
		try {
			this.updateForList("hrm.transferOrder.cancelHrPluralityByNo", list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List getPaHrVList(Object object) throws Exception {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.transferOrder.getPaHrVList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 提交异动发令（save Transfer Order Upgrade）
	 * 
	 * @param obj
	 *            list
	 * @return
	 * @throws Exception
	 */
	public void saveDiaoDongAffirmList(Object obj, List list) throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor", list);
		// this.insert("hrm.transferOrder.saveExperienceForRemove", obj);
	}

	/**
	 * 提交异动发令（save Transfer Order Upgrade）
	 * 
	 * @param obj
	 *            list
	 * @return
	 * @throws Exception
	 */
	public void saveDiaoDongAffirmList1(Object obj, List list) throws Exception {
		// 保存到HR_AFFIRM
		this.insertForList("hrm.transferOrder.saveAffirmor1", list);
		// this.insert("hrm.transferOrder.saveExperienceForRemove", obj);
	}

	/**
	 * 根据ID获得codename
	 * 
	 * @param obj
	 *            list
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public List getSycodeByCodeId(Object object) throws Exception {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.transferOrder.getSycodeByCodeId", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public int getHrExperienceInsideTransNum(Object object)
			throws NumberFormatException, SQLException {
		int result = Integer.parseInt(this.queryForObject(
				"hrm.transferOrder.getHrExperienceInsideTransNum", object)
				.toString());
		return result;
	}

	@Override
	public Integer getRewardAndPunishmentInsideTransNum(Map paramMap)
			throws Exception {
		return (Integer) this.queryForObject(
				"hrm.transferOrder.getRewardAndPunishmentInsideTransNum",
				paramMap);
	}

	@Override
	public List getSelectedRewardEmpList(LinkedHashMap paramMap)
			throws Exception {
		return this.queryForList("hrm.transferOrder.getSelectedRewardEmpList",
				paramMap);
	}

	/**
	 * 根据职责查询对应职级
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getPostGradeNoByDuty(LinkedHashMap paramMap) throws Exception {
		return this.queryForList("hrm.transferOrder.getPostGradeNoByDuty",
				paramMap);
	}

	/**
	 * 根据职级获得对应的职级
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getHrPostGradeByPostGradeNo(LinkedHashMap paramMap)
			throws Exception {
		return this.queryForList(
				"hrm.transferOrder.getHrPostGradeByPostGradeNo", paramMap);
	}

	/**
	 * 根据职级名称NO获得对应的职级名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getPostNoNameByPostsNo(LinkedHashMap paramMap) throws Exception {
		return this.queryForList("hrm.transferOrder.getPostNoNameByPostsNo",
				paramMap);
	}

	/**
	 * 根据职种获得职位
	 */
	@Override
	public List getZhiZhongAndZhiWei(Object object) throws Exception {
		return this.queryForList("hrm.transferOrder.getZhiZhongAndZhiWei",
				object);
	}

	/**
	 * 根据ID获得级联
	 */
	@Override
	public List viewSelectTag(Object object) throws Exception {
		return this.queryForList("hrm.transferOrder.viewSelectTag", object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ait.hrm.dao.TransferOrderDao#
	 * deleteHrExperienceInsideSaveByPersonIdNExpInsideNo(java.lang.Object)
	 */
	public void deleteHrExperienceInsideSaveByPersonIdNExpInsideNo(Object object)
			throws Exception {
		this
				.delete(
						"hrm.transferOrder.deleteHrExperienceInsideSaveByPersonIdNExpInsideNo",
						object);
	}

	/**
	 * 删除奖励或惩戒的一行数据
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-9-29 下午04:12:20
	 * @version V1.0
	 */
	@Override
	public int deleteCurrentPunishment(LinkedHashMap paramMap) throws Exception {
		int result = 0;
		try {
			this.delete("hrm.transferOrder.deleteCurrentPunishment", paramMap);
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}

	/**
	 * 删除奖励或惩戒的一行数据
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-9-29 下午04:12:20
	 * @version V1.0
	 */
	@Override
	public int deleteCurrentReward(LinkedHashMap paramMap) throws Exception {
		int result = 0;
		try {
			this.delete("hrm.transferOrder.deleteCurrentReward", paramMap);
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}

	/**
	 * 根据expno查询当前调令的最大级别
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author heran heran@ait.net.cn
	 * @date 2013-10-17 下午10:00:20
	 * @version V1.0
	 */
	@Override
	public List getHrAffirmByExpinsideNo(Map paramMap) throws Exception {
		return this.queryForList("hrm.transferOrder.getHrAffirmByExpinsideNo",
				paramMap);
	}

	public int updateHrAffirmByExpinsideNo(Map paramMap) throws Exception {
		int result = 0;
		try {
			this.delete("hrm.transferOrder.updateHrAffirmByExpinsideNo",
					paramMap);
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}

	/**
	 * 根据expno查询当前2级裁决人
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author heran heran@ait.net.cn
	 * @date 2013-10-17 下午10:50:20
	 * @version V1.0
	 */
	@Override
	public List getTwoCurrentAffirmIdByExpinsideNo(Map paramMap)
			throws Exception {
		return this.queryForList(
				"hrm.transferOrder.getTwoCurrentAffirmIdByExpinsideNo",
				paramMap);
	}

	@Override
	public List getEmpInfoByPersonIdAndExinsideNo(Map paramMap)
			throws Exception {
		return this.queryForList(
				"hrm.transferOrder.getTwoCurrentAffirmIdByExpinsideNo",
				paramMap);

	}
	
	/**
	 * 临时职人员离职数据导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getTempEmpResignDataImportResultList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.transferOrder.getResignationImpResultList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("hrm.transferOrder.getResignationImpResultList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}	
	/**
	 * 临时职人员离职数据导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getTempEmpResignDataImportResultList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getTempEmpResignDataImportResultList(obj, -1, -1) ;
		
		return returnList ;
	}
	/**
	 * 临时职人员离职数据导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getTempEmpResignDataImportResultListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.transferOrder.getResignationImpResultListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 临时职人员离职数据导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getTempEmpResignDataImportResultListErrCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.transferOrder.getResignationImpResultListErrCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 临时职人员离职数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importTempEmpResignDataRAWFromExcel(Map paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("hrm.transferOrder.importTempEmpResignRAWFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}	
	/**
	 * 调动列表
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getTempEmpTransferOrderList(Object obj, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getTempEmpTransferOrderList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getTempEmpTransferOrderList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 临时职人员调动列表
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getTempEmpTransferOrderList(Object obj) {
		List returnList = new ArrayList() ;		
		returnList = this.getTempEmpTransferOrderList(obj, -1, -1) ;		
		return returnList ;
	}

	/**
	 * 调动总数
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getTempEmpTransferOrderListCnt(Object obj){
		try {
			return NumberUtils.parseNumber(
							ObjectUtils.toString(this.queryForObject(
								"hrm.transferOrder.getTempEmpTransferOrderListCnt",
								obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public List getTempEmpTransferOrderEditList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getTempEmpTransferOrderEditList(obj, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getTempEmpTransferOrderEditList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getTempEmpTransferOrderEditList", obj, currentPage,pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getTempEmpTransferOrderEditList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public int getTempEmpTransferOrderEditListCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"hrm.transferOrder.getTempEmpTransferOrderEditListCnt", obj)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	/**
	 * 临时职人员发令数据导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getTransferOrderImpResultList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.transferOrder.getTransferOrderImpResultList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("hrm.transferOrder.getTransferOrderImpResultList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}	
	/**
	 * 临时职人员发令数据导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getTransferOrderImpResultList(Object obj) {
		List returnList = new ArrayList() ;		
		returnList = this.getTransferOrderImpResultList(obj, -1, -1) ;		
		return returnList ;
	}
	/**
	 * 临时职人员发令数据导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getTransferOrderImpResultListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.transferOrder.getTransferOrderImpResultListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 临时职人员发令数据导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getTransferOrderImpResultListErrCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.transferOrder.getTransferOrderImpResultListErrCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 临时职人员发令数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importTransferOrderImpRAWFromExcel(Map paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("hrm.transferOrder.importTransferOrderImpRAWFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}
	/**
	 * 人员类型变更发令数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importEmpTypeTransferOrderImpRAWFromExcel(Map paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("hrm.transferOrder.importEmpTypeTransferOrderImpRAWFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}
	
	/**撤消临时职发令*/
	@Override
	public Map deleteTransferOrderUpgrade(Map paramMap) throws Exception {
		this.insert("hrm.transferOrder.deleteTransferOrderUpgrade", paramMap);	
		return paramMap;
	}
	/**
	 * 检查当前发令是否生效
	 */
	@Override
	public int checkDeleteTransferOrderUpgrade(Map paramMap){
		try {
			return NumberUtils.parseNumber(
							ObjectUtils.toString(this.queryForObject(
								"hrm.transferOrder.getCheckDeleteTransferOrderUpgradeCnt",
								paramMap)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/*正规职调动*/
	/**
	 * 调动列表
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getReguEmpTransferOrderList(Object obj, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getReguEmpTransferOrderList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getReguEmpTransferOrderList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 临时职人员调动列表
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getReguEmpTransferOrderList(Object obj) {
		List returnList = new ArrayList() ;		
		returnList = this.getReguEmpTransferOrderList(obj, -1, -1) ;		
		return returnList ;
	}

	/**
	 * 调动总数
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getReguEmpTransferOrderListCnt(Object obj){
		try {
			return NumberUtils.parseNumber(
							ObjectUtils.toString(this.queryForObject(
								"hrm.transferOrder.getReguEmpTransferOrderListCnt",
								obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public List getReguEmpTransferOrderEditList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getReguEmpTransferOrderEditList(obj, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getReguEmpTransferOrderEditList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.transferOrder.getReguEmpTransferOrderEditList", obj, currentPage,pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.transferOrder.getReguEmpTransferOrderEditList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public int getReguEmpTransferOrderEditListCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"hrm.transferOrder.getReguEmpTransferOrderEditListCnt", obj)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	public void addconfirmReqHire(Object obj) throws Exception {
		this.update("hrm.transferOrder.addconfirmReqHire", obj) ;
		//保存附件
		LinkedHashMap param = (LinkedHashMap)obj;
		if (param.get("hire_fileName") != null && !"".equals(StringUtil.checkNull(param.get("hire_fileName")))) {
			String[] fileName = StringUtil.checkNull(param.get("hire_fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(param.get("hire_fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + param.get("PERSON_ID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", param.get("REQ_ID"));
					fileMap.put("APPLY_TYPE", param.get("APPLY_TYPE_NO"));
					fileMap.put("CREATED_BY", param.get("CREATED_BY"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
	}

	@Override
	public void insertAffirmor(Object object)  throws Exception{
		this.insert("promoter.insertAffirmor", object) ;
	}

	@Override
	public void delTempEmpInfo(Object obj)throws Exception {
		this.delete("hrm.transferOrder.delTempEmpInfo", obj);
	}
	@Override
	public void delTempEmpInfoPe(Object obj)throws Exception {
		this.delete("hrm.transferOrder.delTempEmpInfoPe", obj);
	}
	@Override
	public void delTempEmpInfoPa(Object obj)throws Exception {
		this.delete("hrm.transferOrder.delTempEmpInfoPa", obj);
	}
	@Override
	public void delTempEmpInfoExp(Object obj)throws Exception {
		this.delete("hrm.transferOrder.delTempEmpInfoExp", obj);
	}

	@Override
	public void addConfirmReqResign(Object obj) throws Exception {
		this.update("hrm.transferOrder.addConfirmReqResign", obj) ;
	}
	
	/*删除离职发令*/
	@Override
	public Map deleteResignation(Map paramMap) throws Exception {
		this.insert("hrm.transferOrder.deleteResignation", paramMap);	
		return paramMap;
	}

	/*
	 * 撤销离职发令
	 * */
	@Override
	public Map callRevokeResignation(Map paramMap) throws Exception {
		this.insert("hrm.transferOrder.callRevokeResignation", paramMap);	
		return paramMap;
	}
	@Override
	public void addConfirmReqRevokeResign(Object obj) throws Exception {
		this.update("hrm.transferOrder.saveExperienceForRevokeResign", obj) ;
		this.update("hrm.transferOrder.updateExperienceForRevokeResign", obj) ;
	}
	
	@Override
	public void addAttachFiles(Object obj) throws Exception {
		//保存附件
		LinkedHashMap param = (LinkedHashMap)obj;
		if (param.get("fileName") != null && !"".equals(StringUtil.checkNull(param.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(param.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(param.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + param.get("PERSON_ID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", param.get("REQ_ID"));
					fileMap.put("APPLY_TYPE", param.get("APPLY_TYPE_NO"));
					fileMap.put("CREATED_BY", param.get("CREATED_BY"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}		
	}
	public int getValidTempEmpTypeTrCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"hrm.transferOrder.getValidTempEmpTypeTrCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	public int getValidDeptNoCnt(Object obj){
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"hrm.transferOrder.getValidDeptNoCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	/**
	 * 派遣地发令修改
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateSendAndSendOffNew(LinkedHashMap paramMap) throws Exception{
		this.update("hrm.transferOrder.updateSendAndSendOffNew", paramMap);
	}
	/*
	 * 撤销临时职发令
	 * */
	public int getDelTempTROInvalidStateCnt(Object obj){
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"hrm.transferOrder.getDelTempTROInvalidStateCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	@Override
	public void cancelTransferOrderInBatch(Object obj) throws Exception {
		this.update("hrm.transferOrder.cancelTransferOrderInBatch", obj) ;
	}
}
