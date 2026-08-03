package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;


import com.ait.ess.dao.TransactionAffirmDao;
import com.ait.hrm.dao.TransferOrderDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * 发令决裁(Transaction affirm)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: TransactionAffirmDaoImpl.java
 * @Description:
 * @Create date: Feb 20, 2012 3:26:29 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 20, 2012 3:26:29 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Repository
public class TransactionAffirmDaoImpl extends SqlMapClientSupport implements
		TransactionAffirmDao {
	@Autowired
	private TransferOrderDao transferOrderDao;
	/**
	 * 入职决裁列表(entry transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getEntryTransAfiirmList(Object obj) throws Exception {
		return this.getEntryTransAfiirmList(obj, -1, -1);
	}

	/**
	 * 入职决裁列表(entry transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getEntryTransAfiirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList("ess.trans.getEntryTransAfiirmList",
					obj, currentPage, pageSize);
		} else {
			returnList = this.queryForList("ess.trans.getEntryTransAfiirmList",
					obj);
		}
		return returnList;
	}

	/**
	 * 入职决裁的总数(get entry transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getEntryTransAfiirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.trans.getEntryTransAfiirmListCnt", obj)),
				Integer.class);
	}

	/**
	 * 薪资调整决裁列表(view salary adjustment transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getPaAdjustTransAffirmList(Object obj) throws Exception {
		return this.getPaAdjustTransAffirmList(obj, -1, -1);
	}

	/**
	 * 薪资调整决裁列表(view salary adjustment transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getPaAdjustTransAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.trans.getPaAdjustTransAffirmList", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.trans.getPaAdjustTransAffirmList", obj);
		}
		return returnList;
	}

	/**
	 * 薪资调整决裁总数(get salary adjustment transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPaAdjustTransAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils
				.toString(this.queryForObject(
						"ess.trans.getPaAdjustTransAffirmListCnt", obj)),
				Integer.class);
	}

	/**
	 * 转正决裁列表(view probation transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getProbationTransAffirmList(Object obj) throws Exception {
		return this.getProbationTransAffirmList(obj, -1, -1);
	}

	/**
	 * 转正决裁列表(view probation transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getProbationTransAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.trans.getProbationTransAffirmList", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.trans.getProbationTransAffirmList", obj);
		}
		return returnList;
	}

	/**
	 * 转正决裁总数(get probation transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getProbationTransAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils
				.toString(this.queryForObject(
						"ess.trans.getProbationTransAffirmListCnt", obj)),
				Integer.class);
	}

	/**
	 * 代理决裁列表(view agent transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getAgentTransAffirmList(Object obj) throws Exception {
		return this.getAgentTransAffirmList(obj, -1, -1);
	}

	/**
	 * 代理决裁列表(view agent transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getAgentTransAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList("ess.trans.getAgentTransAffirmList",
					obj, currentPage, pageSize);
		} else {
			returnList = this.queryForList("ess.trans.getAgentTransAffirmList",
					obj);
		}
		return returnList;
	}

	/**
	 * 代理决裁总数(get agent transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getAgentTransAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.trans.getAgentTransAffirmListCnt", obj)),
				Integer.class);
	}

	/**
	 * 奖励决裁列表(view reward transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getRewardTransAffirmList(Object obj) throws Exception {
		return this.getRewardTransAffirmList(obj, -1, -1);
	}

	/**
	 * 奖励决裁列表(view reward transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getRewardTransAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.trans.getRewardTransAffirmList", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.trans.getRewardTransAffirmList", obj);
		}
		return returnList;
	}

	/**
	 * 奖励决裁总数(get reward transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getRewardTransAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.trans.getRewardTransAffirmListCnt", obj)),
				Integer.class);
	}

	/**
	 * 惩戒决裁列表(view punishment transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getPunishmentTransAffirmList(Object obj) throws Exception {
		return this.getPunishmentTransAffirmList(obj, -1, -1);
	}

	/**
	 * 惩戒决裁列表(view punishment transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getPunishmentTransAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.trans.getPunishmentTransAffirmList", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.trans.getPunishmentTransAffirmList", obj);
		}
		return returnList;
	}

	/**
	 * 惩戒决裁总数(get punishment transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPunishmentTransAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.trans.getPunishmentTransAffirmListCnt",
						obj)), Integer.class);
	}

	/**
	 * 离职决裁列表(view resignation transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getResignationTransAffirmList(Object obj) throws Exception {
		return this.getResignationTransAffirmList(obj, -1, -1);
	}

	/**
	 * 离职决裁列表(view resignation transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getResignationTransAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.trans.getResignationTransAffirmList", obj,
					currentPage, pageSize);
		} else {
			returnList = this.queryForList(
					"ess.trans.getResignationTransAffirmList", obj);
		}
		return returnList;
	}

	/**
	 * 离职决裁总数(get resignation transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getResignationTransAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.trans.getResignationTransAffirmListCnt",
						obj)), Integer.class);
	}

	/**
	 * 调动决裁列表(view transaction transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getTransferTransAffirmList(Object obj) throws Exception {
		return this.getTransferTransAffirmList(obj, -1, -1);
	}

	/**
	 * 调动决裁列表(view transaction transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getTransferTransAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.trans.getTransferTransAffirmList", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.trans.getTransferTransAffirmList", obj);
		}
		return returnList;
	}

	/**
	 * 调动决裁总数(get transaction transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getTransferTransAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils
				.toString(this.queryForObject(
						"ess.trans.getTransferTransAffirmListCnt", obj)),
				Integer.class);
	}

	/**
	 * 升职/降职决裁列表(view promotion and relegation transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getPromotRelegatTransAffirmList(Object obj) throws Exception {
		return this.getPromotRelegatTransAffirmList(obj, -1, -1);
	}

	/**
	 * 升职/降职决裁列表(view promotion and relegation transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getPromotRelegatTransAffirmList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.trans.getPromotRelegatTransAffirmList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.trans.getPromotRelegatTransAffirmList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 升职/降职决裁总数(get promotion and relegation transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPromotRelegatTransAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.trans.getPromotRelegatTransAffirmListCnt",
						obj)), Integer.class);
	}

	/**
	 * 兼职决裁列表(view plurality transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getPluralityTransAffirmList(Object obj) throws Exception {
		return this.getPluralityTransAffirmList(obj, -1, -1);
	}

	/**
	 * 兼职决裁列表(view plurality transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getPluralityTransAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.trans.getPluralityTransAffirmList", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.trans.getPluralityTransAffirmList", obj);
		}
		return returnList;
	}

	/**
	 * 兼职决裁总数(get plurality transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPluralityTransAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils
				.toString(this.queryForObject(
						"ess.trans.getPluralityTransAffirmListCnt", obj)),
				Integer.class);
	}

	/**
	 * 停职/复职决裁列表(view suspension transaction affirm list
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getSuspensionTransAffirmList(Object obj) throws Exception {
		return this.getSuspensionTransAffirmList(obj, -1, -1);

	}

	/**
	 * 停职/复职决裁列表(view suspension transaction affirm list
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getSuspensionTransAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.trans.getSuspensionTransAffirmList", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.trans.getSuspensionTransAffirmList", obj);
		}
		return returnList;
	}

	/**
	 * 停职/复职决裁总数(get suspension transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getSuspensionTransAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.trans.getSuspensionTransAffirmListCnt",
						obj)), Integer.class);
	}

	/**
	 * 批量通过/否决入职发令(batch pass and reject entry transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveApproveEntryTransInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString(): "0";
			this.update("ess.trans.updateHrAffirm", map);
			this.update("ess.trans.updateHrExperience", map);
			
			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			if ("1".equals(String.valueOf(flag))) {
				this.insert("ess.trans.insertHrEmpDeptByExpInsideNo", map);//hr_emp_dept
				this.insert("ess.trans.insertHrEmpPostByExpInsideNo", map);//hr_emp_post
				this.insert("ess.trans.insertHrEmpPositionByExpInsideNo", map);//hr_emp_position
				this.insert("ess.trans.insertHrEmpPostGradeByExpInsideNo", map);//hr_emp_post_grade
				//this.insert("ess.trans.insertHrEmpPayStepByExpInsideNo", map);//hr_emp_duty
				this.insert("ess.trans.insertHrEmpDutyByExpInsideNo", map);//hr_emp_duty
				this.insert("ess.trans.insertHrEmpWorkAreaByExpInsideNo", map);//hr_emp_work_area
				
				this.insert("ess.trans.insertHrEmployeeFromHrEmployeeTemp",map);//hr_employee
				this.insert("ess.trans.insertHrPersonalInfoFromHrPersonalInfoTemp",map);//hr_personal_info
				this.insert("ess.trans.insertHrEmpInfoFromHrEmpInfoTemp", map);//hr_emp_pa_info
				this.insert("ess.trans.insertHrEducationFromHrEducationTemp", map);//hr_education

				this.insert("ess.trans.insertSyUserForForEntryTrans", map);
				List syGroupNoList = this.queryForList("ess.trans.getEntryRoleGroupNoByCpnyId", map);
				//更新hr_change_status表
				this.insert("ess.trans.savePersonChangeInfo", map);//hr_change_status
				// 如果该法人下有默认的入职权限组
				if (syGroupNoList.size() > 0) {
					for (int j = 0; j < syGroupNoList.size(); j++) {
						String syGroupNo = (String) syGroupNoList.get(j);
						((Map) map).put("ROLE_GROUP_NO", syGroupNo);
						this.insert("ess.trans.insertSyUserRelationForEntryTrans",map);
					}
				}
				
				
			}
		}
		return 1;
	}

	/**
	 * 通过/否决入职发令(pass and reject entry transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApproveEntryTrans(Object object) throws Exception {
		String flag = ((Map) object).get("FLAG") != null ? ((Map) object).get(
				"FLAG").toString() : "0";
		this.update("ess.trans.updateHrAffirm", object);
		this.update("ess.trans.updateHrExperience", object);

		// 如果是决裁流程的最后一步决裁为通过且生效时间不晚于当前时间的时候执行下面的操作,否则交给计划任务去处理
		if ("1".equals(String.valueOf(flag))) {
			this.insert("ess.trans.insertHrEmpDeptByExpInsideNo", object);//hr_emp_dept
			this.insert("ess.trans.insertHrEmpPostByExpInsideNo", object);//hr_emp_post
			this.insert("ess.trans.insertHrEmpPositionByExpInsideNo", object);//hr_emp_position
			this.insert("ess.trans.insertHrEmpPostGradeByExpInsideNo", object);//hr_emp_post_grade
			this.insert("ess.trans.insertHrEmpDutyByExpInsideNo", object);//hr_emp_duty
			this.insert("ess.trans.insertHrEmpWorkAreaByExpInsideNo", object);//hr_emp_work_area
			//this.insert("ess.trans.insertHrEmpPayStepByExpInsideNo", object); 奉号

			this.insert("ess.trans.insertHrEmployeeFromHrEmployeeTemp", object);
			this.insert("ess.trans.insertHrPersonalInfoFromHrPersonalInfoTemp",object);
			
			//입사완료 시점의 급여관련 정보 입력은 제외된다. 
			//this.insert("ess.trans.insertHrEmpInfoFromHrEmpInfoTemp", object);
			
			this.insert("ess.trans.insertHrEducationFromHrEducationTemp", object);

			this.insert("ess.trans.insertSyUserForForEntryTrans", object);

			List syGroupNoList = this.queryForList(
					"ess.trans.getEntryRoleGroupNoByCpnyId", object);

			int syUserNo = this.getSyUserSeqNextVal();
			//更新hr_change_status表
			this.insert("ess.trans.savePersonChangeInfo", object);//变量哪里来的START_DATE
			// 如果该法人下有默认的入职权限组
			if (syGroupNoList.size() > 0) {
				for (int j = 0; j < syGroupNoList.size(); j++) {
					String syGroupNo = (String) syGroupNoList.get(j);
					((Map) object).put("ROLE_GROUP_NO", syGroupNo);
					((Map) object).put("SY_USER_SEQ", syUserNo);
					this.insert("ess.trans.insertSyUserRelationForEntryTrans",
							object);
				}
			}
		}
		return 1;
	}

	/**
	 * 批量通过/否决薪资调整发令(batch pass and reject salary adjustment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveApproveSalaryAdjustTransInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString(): "0";
			this.update("ess.trans.updateHrAffirm", map);
			this.update("ess.trans.updatePayRiseTransByExpInsideNo", map);
			// 如果是决裁流程的最后一步决裁为通过且生效时间不晚于当前时间的时候执行下面的操作,否则交给计划任务去处理
			if ("1".equals(String.valueOf(flag))) {
				this.insert("ess.trans.updatePaBasicData", map);
				this.insert("ess.trans.insertPaBasicData", map);
			}
		}
		return 1;
	}

	/**
	 * 通过/否决薪资调整发令(batch pass and reject salary adjustment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveSalaryAdjustTrans(Object object) throws Exception {
		String flag = ((Map) object).get("FLAG") != null ? ((Map) object).get(
				"FLAG").toString() : "0";
		this.update("ess.trans.updateHrAffirm", object);
		this.update("ess.trans.updatePayRiseTransByExpInsideNo", object);
		// 如果是决裁流程的最后一步决裁为通过且生效时间不晚于当前时间的时候执行下面的操作,否则交给计划任务去处理
		if ("1".equals(String.valueOf(flag))) {
			this.insert("ess.trans.updatePaBasicData", object);
			this.insert("ess.trans.insertPaBasicData", object);
		}
		return 1;
	}

	/**
	 * 批量通过/否决转正调令(batch pass and reject Probation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveApproveProbationTransInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString(): "0";
			// 修改发令决裁表信息
			this.update("ess.trans.updateHrAffirm", map);
			// 修改发令表信息
			this.update("ess.trans.updateProbationByExpInsideNo", map);
			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			if ("1".equals(String.valueOf(flag))) {
				String transCode = map.get("TRANS_CODE") != null ? map.get("FLAG").toString() : "0";
				LinkedHashMap probationMap = (LinkedHashMap) this.queryForObject("ess.trans.getProbationByExpInsideNo",map);
				//更新hr_change_status表
				this.update("ess.trans.updatePersonStatus", map);
				this.insert("ess.trans.savePersonStatus", map);
				if (probationMap != null) {
					if ("4085".equals(transCode)) {// 实习转试用
						probationMap.put("PRO_TRANS_FLAG", 1);
					}else if ("1678".equals(transCode)) {// 实习转正
						probationMap.put("PRO_TRANS_FLAG", 2);
					} else {
						probationMap.put("PRO_TRANS_FLAG", 3);
					}
					probationMap.put("UPDATED_BY", map.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmployeeForProbation",probationMap);
				}
				//如果为法人为C04，劳务转正时，修改员工职级1级为4级，并修改入职日期
				if(map.get("CPNY_ID").toString().equals("C04") && map.get("TRANS_CODE").toString().equals("21368") && map.get("EMP_TYPE_CODE").toString().equals("14890")){
					this.update("ess.trans.specialUpdateForC01ForEmp",map);
				}
			}
		}
		return 1;
	}

	/**
	 * 通过/否决转正调令(batch pass and reject Probation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveApproveProbationTrans(LinkedHashMap map) throws Exception {
		String flag = map.get("FLAG") != null ? map.get("FLAG").toString() : "0";
		// 修改发令决裁表信息
		this.update("ess.trans.updateHrAffirm", map);
		// 修改发令表信息
		this.update("ess.trans.updateProbationByExpInsideNo", map);
		// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
		if ("1".equals(String.valueOf(flag))) {
			String transCode = map.get("TRANS_CODE") != null ? map.get("FLAG").toString() : "0";
			LinkedHashMap probationMap = (LinkedHashMap) this.queryForObject("ess.trans.getProbationByExpInsideNo", map);
			//更新hr_change_status表
			this.insert("ess.trans.savePersonStatus", map);
			this.update("ess.trans.updatePersonStatus", map);
			if (probationMap != null) {
				if ("4085".equals(transCode)) {// 实习转试用
					probationMap.put("PRO_TRANS_FLAG", "1");
				}else if ("1678".equals(transCode)) {// 实习转正
					probationMap.put("PRO_TRANS_FLAG", "2");
				}else {
					probationMap.put("PRO_TRANS_FLAG", "3");
				}
				probationMap.put("UPDATED_BY", map.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmployeeForProbation",probationMap);
			}
			//如果为法人为C04，劳务转正时，修改员工职级1级为4级，并修改入职日期
			if(map.get("CPNY_ID").toString().equals("C04")&& map.get("TRANS_CODE").toString().equals("21368") && map.get("EMP_TYPE_CODE").toString().equals("14890")){
				this.update("ess.trans.specialUpdateForC01ForEmp",map);
			}
		}
		return 1;
	}

	/**
	 * 批量通过/否决代理调令(batch pass and reject agent transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveApproveAgentTransInBatch(List list) throws Exception {
		this.updateForList("ess.trans.updateHrAffirm", list);
		this.updateForList("ess.trans.updateAgentTransByExpInsideNo", list);
		return 1;
	}

	/**
	 * 通过/否决代理调令(pass and reject agent transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveAgentTrans(Object object) throws Exception {
		this.update("ess.trans.updateHrAffirm", object);
		this.update("ess.trans.updateAgentTransByExpInsideNo", object);
		return 1;
	}

	/**
	 * 批量通过/否决奖励调令(batch pass and reject Reward transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveApproveRewardTransInBatch(List list) throws Exception {
		this.updateForList("ess.trans.updateHrAffirm", list);
		this.updateForList("ess.trans.updateHrRewardByExpInsideNo", list);
		return 1;
	}

	/**
	 * 通过/否决奖励调令(pass and reject Reward transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveRewardTrans(Object object) throws Exception {
		// 取得是否是决裁流程最后一步的标识
		// String flag = ((Map) object).get("FLAG") != null ? ((Map) object)
		// .get("FLAG").toString() : "0";
		this.update("ess.trans.updateHrAffirm", object);
		this.update("ess.trans.updateHrRewardByExpInsideNo", object);

		// // 如果是决裁流程的最后一步且为通过的时候执行下面的操作
		// if ("1".equals(String.valueOf(flag))) {
		// this.update("ess.trans.updateHrEmpDeptByExpInsideNo", object);
		// this.update("ess.trans.updateHrEmpPostByExpInsideNo", object);
		// this.update("ess.trans.updateHrEmpPositionByExpInsideNo",
		// object);
		// this.update("ess.trans.updateHrEmpPostGradeByExpInsideNo",
		// object);
		//
		// this.insert("ess.trans.insertHrEmpDeptByExpInsideNo", object);
		// this.insert("ess.trans.insertHrEmpPostByExpInsideNo", object);
		// this.insert("ess.trans.insertHrEmpPositionByExpInsideNo",
		// object);
		// this.insert("ess.trans.insertHrEmpPostGradeByExpInsideNo",
		// object);
		// }
		return 1;
	}

	/**
	 * 批量通过/否决惩戒调令(batch pass and reject punishment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveApprovePunishmentTransInBatch(List list) throws Exception {
		this.updateForList("ess.trans.updateHrAffirm", list);
		this.updateForList("ess.trans.updateHrPunishmentByExpInsideNo", list);
		return 1;
	}

	/**
	 * 通过/否决惩戒调令(pass and reject Punishment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApprovePunishmentTrans(Object object) throws Exception {
		// 取得是否是决裁流程最后一步的标识
		// String flag = ((Map) object).get("FLAG") != null ? ((Map) object)
		// .get("FLAG").toString() : "0";
		this.update("ess.trans.updateHrAffirm", object);
		this.update("ess.trans.updateHrPunishmentByExpInsideNo", object);

		// // 如果是决裁流程的最后一步且为通过的时候执行下面的操作
		// if ("1".equals(String.valueOf(flag))) {
		// this.update("ess.trans.updateHrEmpDeptByExpInsideNo", object);
		// this.update("ess.trans.updateHrEmpPostByExpInsideNo", object);
		// this.update("ess.trans.updateHrEmpPositionByExpInsideNo",
		// object);
		// this.update("ess.trans.updateHrEmpPostGradeByExpInsideNo",
		// object);
		//
		// this.insert("ess.trans.insertHrEmpDeptByExpInsideNo", object);
		// this.insert("ess.trans.insertHrEmpPostByExpInsideNo", object);
		// this.insert("ess.trans.insertHrEmpPositionByExpInsideNo",
		// object);
		// this.insert("ess.trans.insertHrEmpPostGradeByExpInsideNo",
		// object);
		// }
		return 1;
	}

	/**
	 * 批量通过/否决离职调令(batch pass and reject Resignation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveApproveResignationTransInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			// 取得是否是决裁流程最后一步的标识
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString()
					: "0";
			this.update("ess.trans.updateHrAffirm", map);
			this.update("ess.trans.updateResignationByExpInsideNo", map);

			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			if ("1".equals(String.valueOf(flag))) {
				LinkedHashMap resignMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getResignationByExpInsideNo", map);
				
				//更新hr_change_status表
				this.insert("ess.trans.savePersonStatusl", map);
				this.update("ess.trans.updatePersonStatusl", map);
				if (resignMap != null) {
					resignMap.put("UPDATED_BY", map.get("UPDATED_BY"));
					resignMap.put("TRANS_TABLE", "HR_RESIGNATION");
					this.update("ess.trans.updateHrEmpDeptByExpInsideNo",
							resignMap);
					this.update("ess.trans.updateHrEmpPostByExpInsideNo",
							resignMap);
					this.update("ess.trans.updateHrEmpPositionByExpInsideNo",
							resignMap);
//					this.update("ess.trans.updateHrEmpPostGradeByExpInsideNo",
//							resignMap);
//					this.update("ess.trans.updateHrEmpWorkAreaByExpInsideNo",
//							resignMap);

					this.update("ess.trans.updateHrEmployeeForResignation",
							resignMap);
				}
				List l  = this.queryForList("ess.trans.getLastRecord",map);
				if(l.size()!=0 && l != null){
					LinkedHashMap recMap = (LinkedHashMap) l.get(0);
					String lastRecord = recMap.get("LAST_RECORD").toString();
					map.put("LAST_RECORD", lastRecord);
					this.update("ess.trans.changeLastRecord", map);
				}
			}
		}
		return 1;
	}

	/**
	 * 通过/否决离职调令(pass and reject Resignation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveResignationTrans(LinkedHashMap object)
			throws Exception {
		// 取得是否是决裁流程最后一步的标识
		String flag = ((Map) object).get("FLAG") != null ? ((Map) object).get(
				"FLAG").toString() : "0";
		this.update("ess.trans.updateHrAffirm", object);
		this.update("ess.trans.updateResignationByExpInsideNo", object);

		// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
		if ("1".equals(String.valueOf(flag))) {
			LinkedHashMap resignMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getResignationByExpInsideNo", object);
			//更新hr_change_status表
			this.insert("ess.trans.savePersonStatusl", object);
			this.update("ess.trans.updatePersonStatusl", object);
			if (resignMap != null) {
				object.put("TRANS_TABLE", "HR_RESIGNATION");
				this.update("ess.trans.updateHrEmpDeptByExpInsideNo", object);
				this.update("ess.trans.updateHrEmpPostByExpInsideNo", object);
				this.update("ess.trans.updateHrEmpPositionByExpInsideNo",
						object);
//				this.update("ess.trans.updateHrEmpPostGradeByExpInsideNo",
//						object);
//				this.update("ess.trans.updateHrEmpWorkAreaByExpInsideNo",
//						object);
 
				resignMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmployeeForResignation",
						resignMap);
			}
			List l  = this.queryForList("ess.trans.getLastRecord",object);
			if(l.size()!=0 && l != null){
				LinkedHashMap recMap = (LinkedHashMap) l.get(0);
				String lastRecord = recMap.get("LAST_RECORD").toString();
				object.put("LAST_RECORD", lastRecord);
				this.update("ess.trans.changeLastRecord", object);
			}
		}
		return 1;
	}

	/**
	 * 批量通过/否决调动发令(batch pass and reject Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveApproveTransactionTransInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap object = (LinkedHashMap) list.get(i);
			// 取得是否是决裁流程最后一步的标识
			String flag = object.get("FLAG") != null ? object.get("FLAG")
					.toString() : "0";
			this.update("ess.trans.updateHrAffirm", object);
			this.update("ess.trans.updateHrExperience", object);

			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			if ("1".equals(String.valueOf(flag))) {
				/*
				LinkedHashMap beforeMap = null;
				LinkedHashMap afterMap = null;
				LinkedHashMap resumeMap = new LinkedHashMap();
				resumeMap.putAll(object);

				object.put("HR_EMP_TABLE", "HR_EMP_DEPT");
				beforeMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getLatestEmpDataInfoBeforeEffectDate",
						object);
				beforeMap.put("HR_EMP_TABLE", "HR_EMP_DEPT");
				afterMap = (LinkedHashMap) this
						.queryForObject(
								"ess.trans.getLatestEmpDataInfoAfterEffectDate",
								object);
				afterMap.put("HR_EMP_TABLE", "HR_EMP_DEPT");
				if (beforeMap.get("S_EXP_INSIDE_NO") == null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpDeptInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") == null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					this.insert("ess.trans.insertHrEmpDeptInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpDeptInfo", object);
				} else {
					this.insert("ess.trans.insertHrEmpDeptInfo", object);
				}
				object = null;
				beforeMap = null;
				afterMap = null;
				object = resumeMap;
				object.put("HR_EMP_TABLE", "HR_EMP_DUTY");
				beforeMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getLatestEmpDataInfoBeforeEffectDate",
						object);
				beforeMap.put("HR_EMP_TABLE", "HR_EMP_DUTY");
				afterMap = (LinkedHashMap) this
						.queryForObject(
								"ess.trans.getLatestEmpDataInfoAfterEffectDate",
								object);
				afterMap.put("HR_EMP_TABLE", "HR_EMP_DUTY");
				if (beforeMap.get("S_EXP_INSIDE_NO") == null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpDutyInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") == null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					this.insert("ess.trans.insertHrEmpDutyInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpDutyInfo", object);
				} else {
					this.insert("ess.trans.insertHrEmpDutyInfo", object);
				}
				object = null;
				beforeMap = null;
				afterMap = null;
				object = resumeMap;
				object.put("HR_EMP_TABLE", "HR_EMP_POSITION");
				beforeMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getLatestEmpDataInfoBeforeEffectDate",
						object);
				beforeMap.put("HR_EMP_TABLE", "HR_EMP_POSITION");
				afterMap = (LinkedHashMap) this
						.queryForObject(
								"ess.trans.getLatestEmpDataInfoAfterEffectDate",
								object);
				afterMap.put("HR_EMP_TABLE", "HR_EMP_POSITION");
				if (beforeMap.get("S_EXP_INSIDE_NO") == null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpPositionInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") == null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					this.insert("ess.trans.insertHrEmpPositionInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpPositionInfo", object);
				} else {
					this.insert("ess.trans.insertHrEmpPositionInfo", object);
				}
				object = null;
				beforeMap = null;
				afterMap = null;
				object = resumeMap;
				object.put("HR_EMP_TABLE", "HR_EMP_POST");
				beforeMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getLatestEmpDataInfoBeforeEffectDate",
						object);
				beforeMap.put("HR_EMP_TABLE", "HR_EMP_POST");
				afterMap = (LinkedHashMap) this
						.queryForObject(
								"ess.trans.getLatestEmpDataInfoAfterEffectDate",
								object);
				afterMap.put("HR_EMP_TABLE", "HR_EMP_POST");
				if (beforeMap.get("S_EXP_INSIDE_NO") == null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpPostInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") == null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					this.insert("ess.trans.insertHrEmpPostInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpPostInfo", object);
				} else {
					this.insert("ess.trans.insertHrEmpPostInfo", object);
				}
				object = null;
				beforeMap = null;
				afterMap = null;
				object = resumeMap;
				object.put("HR_EMP_TABLE", "HR_EMP_POST_GRADE");
				beforeMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getLatestEmpDataInfoBeforeEffectDate",
						object);
				beforeMap.put("HR_EMP_TABLE", "HR_EMP_POST_GRADE");
				afterMap = (LinkedHashMap) this
						.queryForObject(
								"ess.trans.getLatestEmpDataInfoAfterEffectDate",
								object);
				afterMap.put("HR_EMP_TABLE", "HR_EMP_POST_GRADE");
				if (beforeMap.get("S_EXP_INSIDE_NO") == null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") == null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
				} else {
					this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
				}
				object = null;
				beforeMap = null;
				afterMap = null;
				object = resumeMap;
				object.put("HR_EMP_TABLE", "HR_EMP_WORK_AREA");
				beforeMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getLatestEmpDataInfoBeforeEffectDate",
						object);
				beforeMap.put("HR_EMP_TABLE", "HR_EMP_WORK_AREA");

				afterMap = (LinkedHashMap) this
						.queryForObject(
								"ess.trans.getLatestEmpDataInfoAfterEffectDate",
								object);
				afterMap.put("HR_EMP_TABLE", "HR_EMP_WORK_AREA");
				if (beforeMap.get("S_EXP_INSIDE_NO") == null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") == null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
				} else {
					this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
				}
				*/
				LinkedHashMap empMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getTransactionTransByExpInsideNo", object);
				//调令类型
				String orderType = StringUtil.checkNull(object.get("ORDERTYPE"));
				if(orderType.equals("123347")){//转正调令
					String transCode = StringUtil.checkNull(empMap.get("TRANS_CODE"));
					if(transCode.equals("4085")){//实习转试用 
						empMap.put("STATUS_CODE1", "1374");
						this.update("ess.trans.updateHrEmpStatusForTransactionTrans", empMap);
					}else if(!transCode.equals("")){
						empMap.put("STATUS_CODE1", "14891");
						this.update("ess.trans.updateHrEmpStatusForTransactionTrans", empMap);
					}
				}else if(orderType.equals("123318")){//休职
					empMap.put("EMP_OFFICE1", "123450");//休职
					empMap.put("STATUS_CODE1", "1377");
					this.update("ess.trans.updateHrEmpOfficeStatusForTransactionTrans", empMap);
				}else if(orderType.equals("123346")){//复职
					empMap.put("EMP_OFFICE1", "15119");//在职
					empMap.put("STATUS_CODE1", "14891");
					this.update("ess.trans.updateHrEmpOfficeStatusForTransactionTrans", empMap);
				}else if(orderType.equals("123356")){//离职
					empMap.put("EMP_OFFICE1", "15120");//离职
					empMap.put("STATUS_CODE1", "1375");
					this.update("ess.trans.updateHrEmpOfficeStatusForTransactionTrans", empMap);
				}
				this.update("ess.trans.updateHrEmployeeForTransactionTrans", empMap);
			}
		}
		return 1;
	}

	/**
	 * 通过/否决调动发令(pass and reject Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApproveTransactionTrans(LinkedHashMap object)
			throws Exception {
		// 取得是否是决裁流程最后一步的标识
		String flag = object.get("FLAG") != null ? object.get("FLAG").toString() : "0";
		this.update("ess.trans.updateHrAffirm", object);
		this.update("ess.trans.updateHrExperience", object);

		// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
		if ("1".equals(String.valueOf(flag))) {
			/*
			LinkedHashMap beforeMap = null;
			LinkedHashMap afterMap = null;
			LinkedHashMap resumeMap = new LinkedHashMap();
			resumeMap.putAll(object);

			object.put("HR_EMP_TABLE", "HR_EMP_DEPT");
			beforeMap = (LinkedHashMap) this.queryForObject("ess.trans.getLatestEmpDataInfoBeforeEffectDate", object);
			beforeMap.put("HR_EMP_TABLE", "HR_EMP_DEPT");
			afterMap = (LinkedHashMap) this.queryForObject("ess.trans.getLatestEmpDataInfoAfterEffectDate", object);
			afterMap.put("HR_EMP_TABLE", "HR_EMP_DEPT");
			if (beforeMap.get("S_EXP_INSIDE_NO") == null && afterMap.get("S_EXP_INSIDE_NO") != null) {
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpDeptInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null && afterMap.get("S_EXP_INSIDE_NO") == null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				this.insert("ess.trans.insertHrEmpDeptInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null && afterMap.get("S_EXP_INSIDE_NO") != null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpDeptInfo", object);
			} else {
				this.insert("ess.trans.insertHrEmpDeptInfo", object);
			}
			object = null;
			beforeMap = null;
			afterMap = null;
			object = resumeMap;
			object.put("HR_EMP_TABLE", "HR_EMP_DUTY");
			beforeMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoBeforeEffectDate", object);
			beforeMap.put("HR_EMP_TABLE", "HR_EMP_DUTY");
			afterMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoAfterEffectDate", object);
			afterMap.put("HR_EMP_TABLE", "HR_EMP_DUTY");
			if (beforeMap.get("S_EXP_INSIDE_NO") == null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpDutyInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") == null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				this.insert("ess.trans.insertHrEmpDutyInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpDutyInfo", object);
			} else {
				this.insert("ess.trans.insertHrEmpDutyInfo", object);
			}
			object = null;
			beforeMap = null;
			afterMap = null;
			object = resumeMap;
			object.put("HR_EMP_TABLE", "HR_EMP_POSITION");
			beforeMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoBeforeEffectDate", object);
			beforeMap.put("HR_EMP_TABLE", "HR_EMP_POSITION");
			afterMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoAfterEffectDate", object);
			afterMap.put("HR_EMP_TABLE", "HR_EMP_POSITION");
			if (beforeMap.get("S_EXP_INSIDE_NO") == null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpPositionInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") == null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				this.insert("ess.trans.insertHrEmpPositionInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpPositionInfo", object);
			} else {
				this.insert("ess.trans.insertHrEmpPositionInfo", object);
			}
			object = null;
			beforeMap = null;
			afterMap = null;
			object = resumeMap;
			object.put("HR_EMP_TABLE", "HR_EMP_POST");
			beforeMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoBeforeEffectDate", object);
			beforeMap.put("HR_EMP_TABLE", "HR_EMP_POST");
			afterMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoAfterEffectDate", object);
			afterMap.put("HR_EMP_TABLE", "HR_EMP_POST");
			if (beforeMap.get("S_EXP_INSIDE_NO") == null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpPostInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") == null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				this.insert("ess.trans.insertHrEmpPostInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpPostInfo", object);
			} else {
				this.insert("ess.trans.insertHrEmpPostInfo", object);
			}
			object = null;
			beforeMap = null;
			afterMap = null;
			object = resumeMap;
			object.put("HR_EMP_TABLE", "HR_EMP_POST_GRADE");
			beforeMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoBeforeEffectDate", object);
			beforeMap.put("HR_EMP_TABLE", "HR_EMP_POST_GRADE");
			afterMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoAfterEffectDate", object);
			afterMap.put("HR_EMP_TABLE", "HR_EMP_POST_GRADE");
			if (beforeMap.get("S_EXP_INSIDE_NO") == null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") == null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
			} else {
				this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
			}
			object = null;
			beforeMap = null;
			afterMap = null;
			object = resumeMap;
			object.put("HR_EMP_TABLE", "HR_EMP_WORK_AREA");
			beforeMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoBeforeEffectDate", object);
			beforeMap.put("HR_EMP_TABLE", "HR_EMP_WORK_AREA");
			afterMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoAfterEffectDate", object);
			afterMap.put("HR_EMP_TABLE", "HR_EMP_WORK_AREA");
			if (beforeMap.get("S_EXP_INSIDE_NO") == null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") == null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
			} else {
				this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
			}
			*/
			LinkedHashMap empMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getTransactionTransByExpInsideNo", object);
		
			//调令类型
			String orderType = StringUtil.checkNull(object.get("ORDERTYPE"));
			if(orderType.equals("123347")){//转正调令
				String transCode = StringUtil.checkNull(empMap.get("TRANS_CODE"));
				if(transCode.equals("4085")){//实习转试用 
					empMap.put("STATUS_CODE1", "1374");
					this.update("ess.trans.updateHrEmpStatusForTransactionTrans", empMap);
				}else if(!transCode.equals("")){
					empMap.put("STATUS_CODE1", "14891");
					this.update("ess.trans.updateHrEmpStatusForTransactionTrans", empMap);
				}
			}else if(orderType.equals("123318")){//休职
				empMap.put("EMP_OFFICE1", "123450");//休职
				empMap.put("STATUS_CODE1", "1377");
				this.update("ess.trans.updateHrEmpOfficeStatusForTransactionTrans", empMap);
			}else if(orderType.equals("123346")){//复职
				empMap.put("EMP_OFFICE1", "15119");//在职
				empMap.put("STATUS_CODE1", "14891");
				this.update("ess.trans.updateHrEmpOfficeStatusForTransactionTrans", empMap);
			}else if(orderType.equals("123356")){//离职
				empMap.put("EMP_OFFICE1", "15120");//离职
				empMap.put("STATUS_CODE1", "1375");
				empMap.put("DATE_LEFT", empMap.get("START_DATE").toString().subSequence(0, 10));//离职日期					   
				this.update("ess.trans.updateHrEmpOfficeStatusForTransactionTransDateLeft", empMap);
			}
			empMap.put("START_DATE", empMap.get("START_DATE").toString().subSequence(0, 10));
			object.put("START_DATE", empMap.get("START_DATE"));
			String Exp_Inside_No=((Map)object).get("EXP_INSIDE_NO").toString();
			object.put("PERSON_ID", empMap.get("PERSON_ID"));
			//现部门异动  部门变化
			String newDeptNo=empMap.get("DEPTNO").toString();
			String oldDeptNo=empMap.get("OLD_DEPTNO").toString();
			if(!newDeptNo.equals(oldDeptNo)){
				object.put("PERSON_ID", empMap.get("PERSON_ID"));
				object.put("NOW_DEPARTMENT_DATE", "Y");
				object.put("EXP_INSIDE_NO", Exp_Inside_No);
				empMap.put("NOW_DEPARTMENT_DATE", "Y");
				
				
			}
			String newPostGrade=empMap.get("POST_GRADE_NO").toString();
			String oldPostGrade=empMap.get("OLD_POST_GRADE_NO").toString();
			if(!newPostGrade.equals(oldPostGrade)){
				object.put("PERSON_ID", empMap.get("PERSON_ID"));
				object.put("EXP_INSIDE_NO", Exp_Inside_No);
				object.put("POST_GRADE_CHANGE_DATE", "Y");
				empMap.put("POST_GRADE_CHANGE_DATE", "Y");
				empMap.put("POST_GRADE_CHANGE_DATE", "Y");
				
				
			}
			//职责晋升 123316   职级晋升123317    职责降职123524  职级降职123525
			String transNo=empMap.get("TRANS_NO").toString();
			if(transNo.equals("123316")||transNo.equals("123317")||transNo.equals("123524")||transNo.equals("123525")){
				
				object.put("PERSON_ID", empMap.get("PERSON_ID"));
				object.put("EXP_INSIDE_NO", Exp_Inside_No);
				object.put("PROMOTION_DATE", "Y");
				empMap.put("PROMOTION_DATE", "Y");
			}
				
			if (transNo.equals("123347")) {
				object.put("IN_THE_DIFFERENCE", "N");
				object.put("EXP_INSIDE_NO", Exp_Inside_No);
			    empMap.put("IN_THE_DIFFERENCE", "N");
			 }
			this.update("hrm.transferOrder.updateHrEmployeeForUpgrade", object);
			//取消兼职  123315,代理解除  123353,借调解除 123358,待处理解除 123360  
			if(transNo.equals("123315")||transNo.equals("123353")||transNo.equals("123358")||transNo.equals("123360")){
				
				object.put("PERSON_ID", empMap.get("PERSON_ID"));
				object.put("EXP_INSIDE_NO", Exp_Inside_No);
				this.update("hrm.transferOrder.updateHrExperienceInsideForUpgradeEndDate",
						object);
			}
			this.update("hrm.transferOrder.updateHrExperienceInsideForUpgrade",object);
			this.update("ess.trans.updateHrEmployeeForTransactionTrans", empMap);
			//兼职 123314  ,离职 123356 ,待处理 123359
			if(transNo.equals("123314")||transNo.equals("123356")||transNo.equals("123359")){
				//结束时间
				String end_date="";
				if(empMap.get("END_DATE")!=null){
					 end_date= empMap.get("END_DATE").toString().subSequence(0, 10).toString();
				}else{
					 end_date="0000-00-00";
				}
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				//当前时间
				String curDate=formatter.format(new Date()).substring(0,10).toString();
				String endTrans="";
				if(transNo.equals("123314")){
					endTrans="123315";
				}else if(transNo.equals("123356")||transNo.equals("123359")){
					endTrans="642";
				}
				if(transNo.equals("123356")){
					/*RESIGN_TYPE   离职类型  960
					RESIGN_REASON 离职原因  4291
					 EXP_INSIDE_NO             ,
	                  PERSON_ID                 ,
	                  TRANS_NO                  ,
	                  TRANS_CODE                ,
	                  PUN_TYPE_ID				,
	                  DATE_PUNISHED				,
	                  PUN_REASON				,
	                  CREATE_DATE               ,
	                  CREATED_BY                ,
	                  ORDERNO					,
	                  POSITION_NO               ,
	                  POST_NO					,
	                  DEPTNO					,
	                  PUN_BONUS	
	                  				,*/
					if(empMap.get("RESIGN_TYPE").equals("960")&&empMap.get("RESIGN_REASON").equals("4291")){
						
					
						Map paramMap1=new LinkedHashMap<String, Object>();
						paramMap1.put("PERSONID", empMap.get("PERSON_ID"));
					    List paHrVList=transferOrderDao.getPaHrVList(paramMap1);
						LinkedHashMap paramMap = new LinkedHashMap();
						paramMap.put("PERSON_ID",empMap.get("PERSON_ID"));
						paramMap.put("TRANS_NO", endTrans);
						paramMap.put("TRANS_CODE","16184");//违纪解除契约
						paramMap.put("PUN_TYPE_ID","16184");
						paramMap.put("POSITION_NO",((Map)paHrVList.get(0)).get("CUR_POSITION_NO"));
						paramMap.put("POST_NO",((Map)paHrVList.get(0)).get("CUR_POST_NO"));
						paramMap.put("ORDERNO", "");
						paramMap.put("DEPTNO",((Map)paHrVList.get(0)).get("CUR_DEPTNO"));
						paramMap.put("CREATED_BY", "");
						paramMap.put("ACTIVITY", "1");
						paramMap.put("DATE_PUNISHED",curDate);
						paramMap.put("PUN_REASON",empMap.get("REMARK"));
						paramMap.put("PUN_BONUS","0");
						String expInsideNo = transferOrderDao.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("CPNY_ID", empMap.get("CPNY_ID"));
						this.transferOrderDao.saveExperienceForPunishMent(paramMap);
						
						
					}	
						
				}
				
				/*
				 * 待处理调令生效时 生成一条生效的惩罚调令(惩罚方式：待处理)
				 */
				if(transNo.equals("123359")){
					Map paramMap1=new LinkedHashMap<String, Object>();
					paramMap1.put("PERSONID", empMap.get("PERSON_ID"));
				    List paHrVList=transferOrderDao.getPaHrVList(paramMap1);
					LinkedHashMap paramMap = new LinkedHashMap();
					paramMap.put("PERSON_ID",empMap.get("PERSON_ID"));
					paramMap.put("TRANS_NO", endTrans);
					paramMap.put("TRANS_CODE","123527");//处理类型
					paramMap.put("PUN_TYPE_ID","123527");
					paramMap.put("POSITION_NO",((Map)paHrVList.get(0)).get("CUR_POSITION_NO"));
					paramMap.put("POST_NO",((Map)paHrVList.get(0)).get("CUR_POST_NO"));
					paramMap.put("ORDERNO", "");
					paramMap.put("DEPTNO",((Map)paHrVList.get(0)).get("CUR_DEPTNO"));
					paramMap.put("CREATED_BY", "");
					paramMap.put("ACTIVITY", "1");
					paramMap.put("DATE_PUNISHED",curDate);
					paramMap.put("PUN_REASON",empMap.get("REMARK"));
					paramMap.put("PUN_BONUS","0");
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("CPNY_ID", empMap.get("CPNY_ID"));
					this.transferOrderDao.saveExperienceForPunishMent(paramMap);
				}
				
				if(end_date.equals(curDate)){
					if(transNo.equals("123314")){
						Map paramMap=new LinkedHashMap<String, Object>();
						paramMap.put("PERSONID", empMap.get("PERSON_ID"));
						List paHrVList=transferOrderDao.getPaHrVList(paramMap);
						//插入兼职解除
						Map paramMap1=new LinkedHashMap<String, Object>();
						//对应的兼职序列
						paramMap1.put("RELATION_EXP_INSIDE_NO", Exp_Inside_No);
						paramMap1.put("PERSON_ID",  empMap.get("PERSON_ID"));
						paramMap1.put("TRANS_NO",  endTrans);
						paramMap1.put("START_DATE", empMap.get("END_DATE").toString().subSequence(0, 10));
						paramMap1.put("CUR_DUTY_NO", ((Map)paHrVList.get(0)).get("CUR_DUTY_NO"));
						paramMap1.put("CUR_GRADE_LEVEL", ((Map)paHrVList.get(0)).get("CUR_GRADE_LEVEL"));
						paramMap1.put("CUR_DEPTNO", ((Map)paHrVList.get(0)).get("CUR_DEPTNO"));
						paramMap1.put("CUR_POSITION_NO", ((Map)paHrVList.get(0)).get("CUR_POSITION_NO"));
						paramMap1.put("CUR_POST_NO", ((Map)paHrVList.get(0)).get("CUR_POST_NO"));
						paramMap1.put("CUR_POST_GRADE_NO", ((Map)paHrVList.get(0)).get("CUR_POST_GRADE_NO"));
						paramMap1.put("TRANS_NO",  "123315");
						paramMap1.put("TRANS_CODE",  "123315");
						String expInsideNo1 = transferOrderDao.getNextExpInside();
						paramMap1.put("ACTIVITY", "1");
						paramMap1.put("EXP_INSIDE_NO", expInsideNo1);
						paramMap1.put("CURRENT_AFFIRM_ID", "");	
						Map paramMapCount=new LinkedHashMap<String, Object>();
						paramMapCount.put("RELATION_EXP_INSIDE_NO", Exp_Inside_No);
						paramMap1.put("EXTRA_INSIDE_NO","AUTO_123315");
						List Listcount=transferOrderDao.getCountTransByrelation(paramMapCount);
						int count=Integer.parseInt(((Map)Listcount.get(0)).get("COUNT").toString());
						if(count>0){
							return 2;
						}
						//logger.debug("jjy:::paramMap1:::"+paramMap1);
						transferOrderDao.SaveHrExperienceInsideSave1(paramMap1);
					}
				}
			}
			
			//判断当前裁决是否是兼职，如果是，自动创建一条兼职解除调令
			/*if(transNo.equals("123314")){
				Map paramMap=new LinkedHashMap<String, Object>();
				paramMap.put("PERSONID", empMap.get("PERSON_ID"));
				List paHrVList=transferOrderDao.getPaHrVList(paramMap);
				//插入兼职解除
				Map paramMap1=new LinkedHashMap<String, Object>();
				//对应的兼职序列
				paramMap1.put("RELATION_EXP_INSIDE_NO", Exp_Inside_No);
				paramMap1.put("PERSON_ID",  empMap.get("PERSON_ID"));
				paramMap1.put("TRANS_NO",  "123315");
				paramMap1.put("START_DATE", empMap.get("END_DATE").toString().subSequence(0, 10));
				CUR_DUTY_NO	VARCHAR2(30)	Y			现职责
				CUR_POST_GRADE_NO	VARCHAR2(30)	Y			现职级
				CUR_POST_NO	VARCHAR2(30)	Y			现职级名称
				CUR_POSITION_NO	VARCHAR2(30)	Y			现职位
				CUR_DEPTNO	VARCHAR2(30)	Y			现部门
				CUR_GRADE_LEVEL	VARCHAR2(30)	Y			现职等
				
				*  --现部门
       				HRE.DEPTNO CUR_DEPTNO,
			       --现职等
			       Hre.GRADE_LEVEL CUR_GRADE_LEVEL,
			       --现职责
			       HRE.DUTY_NO CUR_DUTY_NO,
			       --现职级
			       HRE.POST_GRADE_NO CUR_POST_GRADE_NO,
			       --现职级名称
			       HRE.POST_NO CUR_POST_NO,
			       --现职位
			       HRE.POSITION_NO CUR_POSITION_NO
				
				paramMap1.put("CUR_DUTY_NO", ((Map)paHrVList.get(0)).get("CUR_DUTY_NO"));
				paramMap1.put("CUR_GRADE_LEVEL", ((Map)paHrVList.get(0)).get("CUR_GRADE_LEVEL"));
				paramMap1.put("CUR_DEPTNO", ((Map)paHrVList.get(0)).get("CUR_DEPTNO"));
				paramMap1.put("CUR_POSITION_NO", ((Map)paHrVList.get(0)).get("CUR_POSITION_NO"));
				paramMap1.put("CUR_POST_NO", ((Map)paHrVList.get(0)).get("CUR_POST_NO"));
				paramMap1.put("CUR_POST_GRADE_NO", ((Map)paHrVList.get(0)).get("CUR_POST_GRADE_NO"));
				paramMap1.put("TRANS_NO",  "123315");
				paramMap1.put("TRANS_CODE",  "123315");
				//AdminBean admin = SessionUtil.getLoginUserFromSession(object);
				paramMap.put("CPNY_ID", empMap.get("CPNY_ID"));
				paramMap.put("CREATED_BY", empMap.get("PERSON_ID"));//CREATED_BY   
				//查询当前裁决人
				*//**
				 start
				 *//*
			String expInsideNo="";
			LinkedHashMap paramMapDiaoling = new LinkedHashMap<String,Object>();
			String expInsid="";
			if(ExpInsideNo!=null){
				expInsid=ExpInsideNo[i];
			}else{
				expInsid="0";
			}
			//String expInsideNo2 = transferOrderDao.getNextExpInside();
			String expInsideNo1 = transferOrderDao.getNextExpInside();
			paramMapDiaoling.put("EXP_INSIDE_NO",expInsideNo1);
			paramMapDiaoling.put("CREATED_BY", empMap.get("PERSON_ID"));
			paramMapDiaoling.put("PERSON_ID", empMap.get("PERSON_ID"));
			paramMapDiaoling.put("CPNY_ID", empMap.get("CPNY_ID"));
			paramMapDiaoling.put("TRANS_CODE", "123315");
			paramMapDiaoling.put("TYPE", "yidong");
			paramMapDiaoling.put("ADMIN", empMap.get("PERSON_ID"));
			//EXP_INSIDE_NO，CREATED_BY，PERSON_ID，CPNY_ID，TRANS_CODE
			List affirmorList = transferOrderDao.getAffirmorIdListByPersonal1(paramMapDiaoling);
			String Current_Affirm_id="";
			// 如果特殊设置不为空
			if (affirmorList.size() != 0) {
				for (int j = 0; j < affirmorList.size(); j++) {
					if (j == 0) {
						// 取当前决裁者
						paramMapDiaoling.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
						Current_Affirm_id=((Map)affirmorList.get(j)).get("AFFIRMOR_ID").toString();
					}
				}
				if(affirmorList.size()>1&&empMap.get("PERSON_ID").equals(Current_Affirm_id)){
					((Map)affirmorList.get(0)).put("AFFIRM_FLAG", "1");
					Current_Affirm_id=((Map)affirmorList.get(1)).get("AFFIRMOR_ID").toString();
					for(int g=1;g<affirmorList.size();g++){
						((Map)affirmorList.get(g)).put("AFFIRM_FLAG", "0");
					}
					
				}else if(affirmorList.size()==1&&empMap.get("PERSON_ID").equals(Current_Affirm_id)){
					((Map)affirmorList.get(0)).put("AFFIRM_FLAG", "1");
					 Current_Affirm_id=((Map)affirmorList.get(0)).get("AFFIRMOR_ID").toString();
				}
				
				
				
				// 保存发令信息
				transferOrderDao.saveDiaoDongAffirmList1(paramMap, affirmorList);
			} else {
				// 取出被发令人的决裁者信息(部门设置)
				List affirmorListByDept = transferOrderDao
						.getAffirmorIdListByDept(paramMapDiaoling);
				if (affirmorListByDept.size() != 0) {
					for (int j = 0; j < affirmorListByDept.size(); j++) {
						// 取当前决裁者
						if (j == 0) {
							//paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
							paramMapDiaoling.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorListByDept.get(j)).get("AFFIRMOR_ID"));
							Map map99=(Map)affirmorListByDept.get(j);
							Current_Affirm_id=map99.get("AFFIRMOR_ID").toString();
						}
					}
					if(affirmorListByDept.size()>1&&empMap.get("PERSON_ID").equals(Current_Affirm_id)){
						((Map)affirmorList.get(0)).put("AFFIRM_FLAG", "1");
						Current_Affirm_id=((Map)affirmorListByDept.get(1)).get("AFFIRMOR_ID").toString();
						for(int g=1;g<affirmorListByDept.size();g++){
							((Map)affirmorListByDept.get(g)).put("AFFIRM_FLAG", "0");
						}
						
					}else if(affirmorListByDept.size()==1&&empMap.get("PERSON_ID").equals(Current_Affirm_id)){
						((Map)affirmorListByDept.get(0)).put("AFFIRM_FLAG", "1");
						 Current_Affirm_id=((Map)affirmorListByDept.get(0)).get("AFFIRMOR_ID").toString();
					}
					// 保存发令信息
					transferOrderDao.saveDiaoDongAffirmList1(paramMapDiaoling,affirmorListByDept);
				} else {
					// 自定义取决裁者
					Map flagMap = getAffiram(paramMapDiaoling, expInsideNo1);
					String flag1 = flagMap.get("AFFIRM_FLAG") != null ? flagMap.get("AFFIRM_FLAG").toString() : "";
					if ("2".equals(flag1)) {
						return 2;
					}
					List newSaveAffirmorList = (List) paramMapDiaoling.get("newSaveAffirmorList");
					Map map99=(Map)newSaveAffirmorList.get(0);
					Current_Affirm_id=map99.get("AFFIRMOR_ID").toString();
					if(newSaveAffirmorList.size()>1&&empMap.get("PERSON_ID").equals(Current_Affirm_id)){
						((Map)newSaveAffirmorList.get(0)).put("AFFIRM_FLAG", "1");
						Current_Affirm_id=((Map)newSaveAffirmorList.get(1)).get("AFFIRMOR_ID").toString();
						for(int g=1;g<newSaveAffirmorList.size();g++){
							((Map)newSaveAffirmorList.get(g)).put("AFFIRM_FLAG", "0");
						}
						
					}else if(newSaveAffirmorList.size()==1&&empMap.get("PERSON_ID").equals(Current_Affirm_id)){
						((Map)newSaveAffirmorList.get(0)).put("AFFIRM_FLAG", "1");
						 Current_Affirm_id=((Map)newSaveAffirmorList.get(0)).get("AFFIRMOR_ID").toString();
					}
					
					transferOrderDao.saveDiaoDongAffirmList1(paramMapDiaoling,newSaveAffirmorList);
				}
			}
			*//**
			 	end
			 *//*
			// 当前裁决人CURRENT_AFFIRM_ID    
			//最大级别 EXP_INSIDE_NO
			paramMap.put("EXP_INSIDE_NO", expInsideNo1);
			List maxLevelList=transferOrderDao.getHrAffirmByExpinsideNo(paramMap);
			List newSaveAffirmorList = (List) paramMapDiaoling.get("newSaveAffirmorList");
		 	//int maxLevel=Integer.parseInt(((Map)maxLevelList.get(0)).get("COUNT").toString());
			
			//最大级别大于1时：1.更新裁决表对应信息1级裁决人的状态为通过  2.更新调令表当前裁决人为2级裁决人
			
			
			if(empMap.get("PERSON_ID").equals(Current_Affirm_id)){
				paramMap1.put("ACTIVITY", "1");
			}else{
				paramMap1.put("ACTIVITY", "0");
			}
			paramMap1.put("ACTIVITY", "0");
			paramMap1.put("EXP_INSIDE_NO", expInsideNo1);
			paramMap1.put("CURRENT_AFFIRM_ID", Current_Affirm_id);	
			transferOrderDao.SaveHrExperienceInsideSave1(paramMap1);
			}*/
			
		}
		return 1;
	}
	//查询裁决人
	@SuppressWarnings("unchecked")
	public Map getAffiram(Map paramMap, String expInsideNo) {

		// 取出被发令人的决裁者信息(部门设置)
		List affirmorListByDept = transferOrderDao.getAffirmorIdListByDept(paramMap);
		// 自定义取决裁者
		List saveAffirmorList = new ArrayList();
		List specialAffirmorList = transferOrderDao.getSpecialAffirmorList(paramMap);
		
		List newSaveAffirmorList = new ArrayList();
		int count = 0;
		int affirm_level = 0;
		boolean flag = false;
		for (int k = 0; k < specialAffirmorList.size(); k++) {
			Map affirmorMap = new LinkedHashMap();
			String person_id = ((LinkedHashMap) specialAffirmorList.get(k)).get("PERSON_ID") != null ? 
					((LinkedHashMap) specialAffirmorList.get(k)).get("PERSON_ID").toString() : "";
			affirm_level = NumberUtils.parseNumber(((LinkedHashMap) specialAffirmorList.get(k)).get("AFFIRM_LEVEL").toString(), Integer.class);
			affirmorMap.put("AFFIRMOR_ID", person_id);
			affirmorMap.put("AFFIRM_LEVEL", affirm_level - count);
			affirmorMap.put("EXP_INSIDE_NO", expInsideNo);
			affirmorMap.put("CREATED_BY", paramMap.get("ADMINID"));

			if (person_id.length() > 0) {
				flag = true;
				saveAffirmorList.add(affirmorMap);
			}
		}
		if (saveAffirmorList.size() != 0) {
			Map filterMap = new LinkedHashMap();
			for (int m = 0; m < saveAffirmorList.size(); m++) {
				Map temp = (Map) saveAffirmorList.get(m);
				filterMap.put(temp.get("AFFIRMOR_ID").toString(), temp);
			}
			int a = 1;
			for (Iterator iterator = filterMap.values().iterator(); iterator.hasNext();) {
				Map temp = (Map) iterator.next();
				temp.put("AFFIRM_LEVEL", a);
				a++;
				newSaveAffirmorList.add(temp);
			}
			paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) newSaveAffirmorList.get(0)).get("AFFIRMOR_ID"));
		}
		if (flag) {
			paramMap.put("AFFIRM_FLAG", "1");
		} else {
			paramMap.put("AFFIRM_FLAG", "2");
		}
		paramMap.put("newSaveAffirmorList", newSaveAffirmorList);
		return paramMap;
	}
	
	
	
	/**
	 * 批量通过/否决升职/降职调令(batch pass and reject PromotRelegat transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveApprovePromotRelegatTransInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap object = (LinkedHashMap) list.get(i);
			// 取得是否是决裁流程最后一步的标识
			String flag = object.get("FLAG") != null ? object.get("FLAG")
					.toString() : "0";
			this.update("ess.trans.updateHrAffirm", object);
			this.update("ess.trans.updateHrExperience", object);

			// 如果是决裁流程的最后一步决裁为通过且生效时间不晚于当前时间的时候执行下面的操作,否则交给计划任务去处理
			if ("1".equals(String.valueOf(flag))) {
				this
						.update("ess.trans.updateHrEmpPayStepByExpInsideNo",
								object);
				this
						.insert("ess.trans.insertHrEmpPayStepByExpInsideNo",
								object);

				LinkedHashMap beforeMap = null;
				LinkedHashMap afterMap = null;
				LinkedHashMap resumeMap = new LinkedHashMap();
				resumeMap.putAll(object);

				object.put("HR_EMP_TABLE", "HR_EMP_DEPT");
				beforeMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getLatestEmpDataInfoBeforeEffectDate",
						object);
				beforeMap.put("HR_EMP_TABLE", "HR_EMP_DEPT");
				afterMap = (LinkedHashMap) this
						.queryForObject(
								"ess.trans.getLatestEmpDataInfoAfterEffectDate",
								object);
				afterMap.put("HR_EMP_TABLE", "HR_EMP_DEPT");
				if (beforeMap.get("S_EXP_INSIDE_NO") == null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpDeptInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") == null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					this.insert("ess.trans.insertHrEmpDeptInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpDeptInfo", object);
				} else {
					this.insert("ess.trans.insertHrEmpDeptInfo", object);
				}

				object = null;
				beforeMap = null;
				afterMap = null;
				object = resumeMap;
				object.put("HR_EMP_TABLE", "HR_EMP_DUTY");
				beforeMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getLatestEmpDataInfoBeforeEffectDate",
						object);
				beforeMap.put("HR_EMP_TABLE", "HR_EMP_DUTY");
				afterMap = (LinkedHashMap) this
						.queryForObject(
								"ess.trans.getLatestEmpDataInfoAfterEffectDate",
								object);
				afterMap.put("HR_EMP_TABLE", "HR_EMP_DUTY");
				if (beforeMap.get("S_EXP_INSIDE_NO") == null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpDutyInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") == null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					this.insert("ess.trans.insertHrEmpDutyInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpDutyInfo", object);
				} else {
					this.insert("ess.trans.insertHrEmpDutyInfo", object);
				}
				object = null;
				object = resumeMap;
				beforeMap = null;
				afterMap = null;
				object.put("HR_EMP_TABLE", "HR_EMP_POSITION");
				beforeMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getLatestEmpDataInfoBeforeEffectDate",
						object);
				beforeMap.put("HR_EMP_TABLE", "HR_EMP_POSITION");
				afterMap = (LinkedHashMap) this
						.queryForObject(
								"ess.trans.getLatestEmpDataInfoAfterEffectDate",
								object);
				afterMap.put("HR_EMP_TABLE", "HR_EMP_POSITION");
				if (beforeMap.get("S_EXP_INSIDE_NO") == null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpPositionInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") == null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					this.insert("ess.trans.insertHrEmpPositionInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpPositionInfo", object);
				} else {
					this.insert("ess.trans.insertHrEmpPositionInfo", object);
				}
				object = null;
				beforeMap = null;
				afterMap = null;
				object = resumeMap;

				object.put("HR_EMP_TABLE", "HR_EMP_POST");
				beforeMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getLatestEmpDataInfoBeforeEffectDate",
						object);
				beforeMap.put("HR_EMP_TABLE", "HR_EMP_POST");
				afterMap = (LinkedHashMap) this
						.queryForObject(
								"ess.trans.getLatestEmpDataInfoAfterEffectDate",
								object);
				afterMap.put("HR_EMP_TABLE", "HR_EMP_POST");
				if (beforeMap.get("S_EXP_INSIDE_NO") == null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpPostInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") == null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					this.insert("ess.trans.insertHrEmpPostInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpPostInfo", object);
				} else {
					this.insert("ess.trans.insertHrEmpPostInfo", object);
				}
				object = null;
				beforeMap = null;
				afterMap = null;
				object = resumeMap;
				object.put("HR_EMP_TABLE", "HR_EMP_POST_GRADE");
				beforeMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getLatestEmpDataInfoBeforeEffectDate",
						object);
				beforeMap.put("HR_EMP_TABLE", "HR_EMP_POST_GRADE");
				afterMap = (LinkedHashMap) this
						.queryForObject(
								"ess.trans.getLatestEmpDataInfoAfterEffectDate",
								object);
				afterMap.put("HR_EMP_TABLE", "HR_EMP_POST_GRADE");
				if (beforeMap.get("S_EXP_INSIDE_NO") == null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") == null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
				} else {
					this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
				}
				object = null;
				beforeMap = null;
				afterMap = null;
				object = resumeMap;
				object.put("HR_EMP_TABLE", "HR_EMP_WORK_AREA");
				beforeMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getLatestEmpDataInfoBeforeEffectDate",
						object);
				beforeMap.put("HR_EMP_TABLE", "HR_EMP_WORK_AREA");

				afterMap = (LinkedHashMap) this
						.queryForObject(
								"ess.trans.getLatestEmpDataInfoAfterEffectDate",
								object);
				afterMap.put("HR_EMP_TABLE", "HR_EMP_WORK_AREA");
				if (beforeMap.get("S_EXP_INSIDE_NO") == null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") == null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
				} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
						&& afterMap.get("S_EXP_INSIDE_NO") != null) {
					beforeMap.put("ACTIVITY", "1");
					beforeMap.put("E_EXP_INSIDE_NO", object
							.get("EXP_INSIDE_NO"));
					beforeMap.put("START_DATE", object.get("START_DATE"));
					beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
					this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
					object.put("ENDDATE", afterMap.get("START_DATE"));
					object.put("E_EXP_INSIDE_NO", afterMap
							.get("S_EXP_INSIDE_NO"));
					this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
				} else {
					this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
				}
				
				LinkedHashMap empMap = (LinkedHashMap) this.queryForObject(
						"ess.trans.getTransactionTransByExpInsideNo", object);
				this.update("ess.trans.updateHrEmployeeForTransactionTrans", empMap);
			}
		}
		return 1;
	}

	/**
	 * 批量通过/否决升职/降职调令(batch pass and reject PromotRelegat transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApprovePromotRelegatTrans(LinkedHashMap object)
			throws Exception {
		// 取得是否是决裁流程最后一步的标识
		String flag = ((Map) object).get("FLAG") != null ? ((Map) object).get(
				"FLAG").toString() : "0";
		this.update("ess.trans.updateHrAffirm", object);
		this.update("ess.trans.updateHrExperience", object);

		// 如果是决裁流程的最后一步决裁为通过且生效时间不晚于当前时间的时候执行下面的操作,否则交给计划任务去处理
		if ("1".equals(String.valueOf(flag))) {
			this.update("ess.trans.updateHrEmpPayStepByExpInsideNo", object);
			this.insert("ess.trans.insertHrEmpPayStepByExpInsideNo", object);

			LinkedHashMap beforeMap = null;
			LinkedHashMap afterMap = null;
			LinkedHashMap resumeMap = new LinkedHashMap();
			resumeMap.putAll(object);

			object.put("HR_EMP_TABLE", "HR_EMP_DEPT");
			beforeMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoBeforeEffectDate", object);
			beforeMap.put("HR_EMP_TABLE", "HR_EMP_DEPT");
			afterMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoAfterEffectDate", object);
			afterMap.put("HR_EMP_TABLE", "HR_EMP_DEPT");
			if (beforeMap.get("S_EXP_INSIDE_NO") == null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpDeptInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") == null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				this.insert("ess.trans.insertHrEmpDeptInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpDeptInfo", object);
			} else {
				this.insert("ess.trans.insertHrEmpDeptInfo", object);
			}
			object = null;
			beforeMap = null;
			afterMap = null;
			object = resumeMap;
			object.put("HR_EMP_TABLE", "HR_EMP_DUTY");
			beforeMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoBeforeEffectDate", object);
			beforeMap.put("HR_EMP_TABLE", "HR_EMP_DUTY");
			afterMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoAfterEffectDate", object);
			afterMap.put("HR_EMP_TABLE", "HR_EMP_DUTY");
			if (beforeMap.get("S_EXP_INSIDE_NO") == null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpDutyInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") == null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				this.insert("ess.trans.insertHrEmpDutyInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpDutyInfo", object);
			} else {
				this.insert("ess.trans.insertHrEmpDutyInfo", object);
			}
			object = null;
			beforeMap = null;
			afterMap = null;
			object = resumeMap;
			object.put("HR_EMP_TABLE", "HR_EMP_POSITION");
			beforeMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoBeforeEffectDate", object);
			beforeMap.put("HR_EMP_TABLE", "HR_EMP_POSITION");
			afterMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoAfterEffectDate", object);
			afterMap.put("HR_EMP_TABLE", "HR_EMP_POSITION");
			if (beforeMap.get("S_EXP_INSIDE_NO") == null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpPositionInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") == null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				this.insert("ess.trans.insertHrEmpPositionInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpPositionInfo", object);
			} else {
				this.insert("ess.trans.insertHrEmpPositionInfo", object);
			}
			object = null;
			beforeMap = null;
			afterMap = null;
			object = resumeMap;
			object.put("HR_EMP_TABLE", "HR_EMP_POST");
			beforeMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoBeforeEffectDate", object);
			beforeMap.put("HR_EMP_TABLE", "HR_EMP_POST");
			afterMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoAfterEffectDate", object);
			afterMap.put("HR_EMP_TABLE", "HR_EMP_POST");
			if (beforeMap.get("S_EXP_INSIDE_NO") == null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpPostInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") == null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				this.insert("ess.trans.insertHrEmpPostInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpPostInfo", object);
			} else {
				this.insert("ess.trans.insertHrEmpPostInfo", object);
			}
			object = null;
			beforeMap = null;
			afterMap = null;
			object = resumeMap;
			object.put("HR_EMP_TABLE", "HR_EMP_POST_GRADE");
			beforeMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoBeforeEffectDate", object);
			beforeMap.put("HR_EMP_TABLE", "HR_EMP_POST_GRADE");
			afterMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoAfterEffectDate", object);
			afterMap.put("HR_EMP_TABLE", "HR_EMP_POST_GRADE");
			if (beforeMap.get("S_EXP_INSIDE_NO") == null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") == null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
			} else {
				this.insert("ess.trans.insertHrEmpPostGradeInfo", object);
			}
			object = null;
			beforeMap = null;
			afterMap = null;
			object = resumeMap;
			object.put("HR_EMP_TABLE", "HR_EMP_WORK_AREA");
			beforeMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoBeforeEffectDate", object);
			beforeMap.put("HR_EMP_TABLE", "HR_EMP_WORK_AREA");
			afterMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getLatestEmpDataInfoAfterEffectDate", object);
			afterMap.put("HR_EMP_TABLE", "HR_EMP_WORK_AREA");
			if (beforeMap.get("S_EXP_INSIDE_NO") == null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") == null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
			} else if (beforeMap.get("S_EXP_INSIDE_NO") != null
					&& afterMap.get("S_EXP_INSIDE_NO") != null) {
				beforeMap.put("ACTIVITY", "1");
				beforeMap.put("E_EXP_INSIDE_NO", object.get("EXP_INSIDE_NO"));
				beforeMap.put("START_DATE", object.get("START_DATE"));
				beforeMap.put("UPDATED_BY", object.get("UPDATED_BY"));
				this.update("ess.trans.updateHrEmpTableInfo", beforeMap);
				object.put("ENDDATE", afterMap.get("START_DATE"));
				object.put("E_EXP_INSIDE_NO", afterMap.get("S_EXP_INSIDE_NO"));
				this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
			} else {
				this.insert("ess.trans.insertHrEmpWorkAreaInfo", object);
			}
			LinkedHashMap empMap = (LinkedHashMap) this.queryForObject(
					"ess.trans.getTransactionTransByExpInsideNo", object);
			this.update("ess.trans.updateHrEmployeeForTransactionTrans", empMap);
		}
		return 1;
	}

	/**
	 * 批量通过/否决兼职调令(batch pass and reject Plurality transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveApprovePluralityTransInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			// 取得是否是决裁流程最后一步的标识
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString()
					: "0";
			this.update("ess.trans.updateHrAffirm", map);
			this.update("ess.trans.updatePluralityByExpInsideNo", map);
			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			if ("1".equals(String.valueOf(flag))) {
				this.update("ess.trans.updatePluralityByRelationExpInsideNo",
						map);
			}
		}
		return 1;
	}

	/**
	 * 通过/否决兼职调令(pass and reject Plurality transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveApprovePluralityTrans(Object object) throws Exception {
		this.update("ess.trans.updateHrAffirm", object);
		this.update("ess.trans.updatePluralityByExpInsideNo", object);
		// 取得是否是决裁流程最后一步的标识
		String flag = ((Map) object).get("FLAG") != null ? ((Map) object).get(
				"FLAG").toString() : "0";

		// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
		if ("1".equals(String.valueOf(flag))) {
			this.update("ess.trans.updatePluralityByRelationExpInsideNo",
					object);
		}
		return 1;
	}

	/**
	 * 批量通过/否决停职/复职调令(batch pass and reject Suspension transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveApproveSuspensionTransInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			// 取得是否是决裁流程最后一步的标识
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString()
					: "0";
			this.update("ess.trans.updateHrAffirm", map);
			this.update("ess.trans.updateSuspensionByExpInsideNo", map);
			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			if ("1".equals(String.valueOf(flag))) {
				this.update("ess.trans.updateSuspensionByRelationExpInsideNo",
						map);
				this.update("ess.trans.updateHrEmployeeForSuspension", map);
			}
		}
		return 1;
	}

	/**
	 * 通过/否决 停职/复职调令(pass and reject Suspension transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveApproveSuspensionTrans(Object object) throws Exception {
		this.update("ess.trans.updateHrAffirm", object);
		this.update("ess.trans.updateSuspensionByExpInsideNo", object);
		// 取得是否是决裁流程最后一步的标识
		String flag = ((Map) object).get("FLAG") != null ? ((Map) object).get(
				"FLAG").toString() : "0";

		// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
		if ("1".equals(String.valueOf(flag))) {
			this.update("ess.trans.updateSuspensionByRelationExpInsideNo",
					object);
			this.update("ess.trans.updateHrEmployeeForSuspension", object);
		}
		return 1;
	}

	/**
	 * 获得当前发令决裁流程中最大的决裁级别(get Max Affirm Flag By ExpInsideNo )
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getMaxAffirmLevelByExpInsideNo(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils
				.toString(this.queryForObject(
						"ess.trans.getMaxAffirmLevelByExpInsideNo", obj)),
				Integer.class);
	}

	/**
	 * 通过发令号获得该发令决裁流程中的决裁者(get affirmer list by ExpInsideNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getAffirmerListByExpInsideNo(Object obj) throws Exception {
		return this.queryForList("ess.trans.getAffirmerListByExpInsideNo", obj);

	}

	/**
	 * 取当前发令流程中的决裁级别(get current affirm level)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getCurrentAffirmLevel(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.trans.getCurrentAffirmLevel", obj)),
				Integer.class);
	}

	/**
	 * 获得下一步决裁信息(get next hr_affirm information by expInsideNo)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public Object getHrAffirmInfoByExpInsideNoAndLevel(Object object)
			throws Exception {
		return this.queryForObject(
				"ess.trans.getHrAffirmInfoByExpInsideNoAndLevel", object);
	}

	/**
	 * 通过发令NO获得信息(get information by ExpInsideNo)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public Object getExpInsideAndHrAffirmByNo(Object object) throws Exception {
		return this.queryForObject("ess.trans.getExpInsideAndHrAffirmByNo",
				object);

	}

	/**
	 * 通过发令NO获得信息(get information by ExpInsideNo for entry transaction)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public Object getExpInsideAndHrAffirmByNoForEntryTrans(Object object)
			throws Exception {
		return this.queryForObject(
				"ess.trans.getExpInsideAndHrAffirmByNoForEntryTrans", object);
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
	 * 号俸决裁列表(view promotion and relegation transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getPayStepTransAffirmList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.trans.getPayStepTransAffirmList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.trans.getPayStepTransAffirmList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	public List getPayStepTransAffirmList(Object obj) throws Exception {
		return this.getPayStepTransAffirmList(obj, -1, -1);
	}
	
	/**
	 * 批量通过/否决号俸发令
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */		
	@Override 
	public int saveApprovePayStepTransInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			// 取得是否是决裁流程最后一步的标识
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString()
					: "0";
			this.update("ess.trans.updateHrAffirm", map);
			this.update("ess.trans.updateHrExperience", map);

			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			if ("1".equals(String.valueOf(flag))) {
				this.update("hrm.transferOrder.updateEmployee", map);
				if(this.isFirstChangePayStep(map) > 0){
					this.update("hrm.transferOrder.updateEmpPayStep", map);
				}
				this.insert("hrm.transferOrder.insertEmpPayStep", map);
			}
		}
		return 1;
	}
	
	public int saveApprovePayStepTrans(LinkedHashMap object)throws Exception {
		// 取得是否是决裁流程最后一步的标识
		String flag = ((Map) object).get("FLAG") != null ? ((Map) object).get(
		"FLAG").toString() : "0";
		this.update("ess.trans.updateHrAffirm", object);
		this.update("ess.trans.updateHrExperience", object);

		// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
		if ("1".equals(String.valueOf(flag))) {
			this.update("hrm.transferOrder.updateEmployee", object);
			if(this.isFirstChangePayStep(object) > 0){
				this.update("hrm.transferOrder.updateEmpPayStep", object);
			}
			this.insert("hrm.transferOrder.insertEmpPayStep", object);
		}
		return 1;
	}
	
	public int getPayStepTransAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.trans.getPayStepTransAffirmListCnt",
						obj)), Integer.class);
	}

	public int isFirstChangePayStep(Object object){
		int result = 0;
		try {
			result = Integer.parseInt(this.queryForObject("ess.trans.isFirstChangePayStep",object)+"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;
		
	}
	
	/**
	 * 薪资调整决裁列表(view salary adjustment transaction affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getHrExperienceInsideSaveByTransCodeByEss(Object obj) throws Exception {
		return this.getHrExperienceInsideSaveByTransCodeByEss(obj, -1, -1);
	}

	/**
	 * 薪资调整决裁列表(view salary adjustment transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List getHrExperienceInsideSaveByTransCodeByEss(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.trans.getHrExperienceInsideSaveByTransCodeByEss", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.trans.getHrExperienceInsideSaveByTransCodeByEss", obj);
		}
		return returnList;
	}

	/**
	 * 异动裁决总数(get salary adjustment transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getHrExperienceInsideSaveByTransCodeByEssCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils
				.toString(this.queryForObject(
						"ess.trans.getPaAdjustTransAffirmListCnt", obj)),
				Integer.class);
	}
	
	/**
	 * 异动决裁列表(view reward transaction affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getHrExpInsideList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.trans.getHrExpInsideList", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.trans.getHrExpInsideList", obj);
		}
		return returnList;
	}

	/**
	 * 异动决裁总数(get reward transaction affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getHrExpInsideListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.trans.getHrExpInsideListCnt", obj)),
				Integer.class);
	}

	@Override
	public List getHrExpInsideList(Object object) throws Exception {
		// TODO Auto-generated method stub
		return this.getHrExpInsideList(object, -1, -1);
	}
	public List getTranferOrderinsideList(Object object) {
		
		 return this.getTranferOrderinsideList(object, -1, -1);
	}

	
	public List getTranferOrderinsideList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						                  
						"ess.trans.getTranferOrderinsideList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.trans.getTranferOrderinsideList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	
	public int getTranferOrderinsideListCnt(Object object) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"hrm.transferOrder.getTranferOrderinsideListCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	
}