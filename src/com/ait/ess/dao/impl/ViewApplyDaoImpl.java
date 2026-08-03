package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.ViewApplyDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class ViewApplyDaoImpl extends SqlMapClientSupport implements
		ViewApplyDao {
	/**
	 * 删除未审核个人信息申请(delete personal apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delPersonInfoApply(Object object) throws Exception {
		Boolean falg = true;
		try {
			this.delete("ess.viewApply.delPersonInfoApply", object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return falg;
	}

	/**
	 * 查询决裁状态(search approve status)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorList(Object object) throws Exception {
		return this.queryForList("ess.viewApply.getAffirmorList", object);
	}

	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delViewOtviewApply(Object object) throws Exception {
		Boolean falg = true;
		Map delApply = (Map) this.queryForObject("ess.viewApply.getOtviewByID",
				object);

		if (delApply != null) {
			delApply.put("AR_DETAIL", ((Map) object).get("AR_DETAIL"));
			delApply.put("itemno", Arrays.asList(new Integer[] { 3, 4, 5 }));
			this.delete("ess.viewApply.delArDetail", delApply);
		}
		this.delete("ess.viewApply.delEssApplyOt", object);
		this.delete("ess.viewApply.delEssAffirm", object);
		this.delete("ess.viewApply.delArApplyResult", object);
		return falg;
	}

	/**
	 * 删除未审核休假信息申请(delete leave apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delViewLeaveviewApply(Object object) throws Exception {
		// TODO Auto-generated method stub
		Boolean falg = true;
		Map delApply = (Map) this.queryForObject("ess.viewApply.getLeaveviewByID", object);

		if (delApply != null) {
			delApply.put("AR_DETAIL", ((Map) object).get("AR_DETAIL"));
			delApply.put("itemno", Arrays.asList(new Integer[] { 16, 17, 18,19, 20, 22, 23, 24 }));
			this.delete("ess.viewApply.delArDetail", delApply);
		}
		this.delete("ess.viewApply.delEssLeaveApplyTb", object);
		this.delete("ess.viewApply.delEssAffirm", object);
		this.delete("ess.viewApply.delArApplyResult", object);
		return falg;
	}

	/**
	 * 删除未审核出差信息申请(delete evection apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delViewEvectionview(Object object) throws Exception {
		Boolean falg = true;
		Map delApply = (Map) this.queryForObject(
				"ess.viewApply.getLeaveviewByID", object);

		if (delApply != null) {
			delApply.put("AR_DETAIL", ((Map) object).get("AR_DETAIL"));
			delApply.put("itemno", Arrays
					.asList(new Integer[] { 9, 10, 11, 12 }));
			this.delete("ess.viewApply.delArDetail", delApply);
		}
		this.delete("ess.viewApply.delEssLeaveApplyTb", object);
		this.delete("ess.viewApply.delEssAffirm", object);
		this.delete("ess.viewApply.delArApplyResult", object);
		return falg;
	}

	/**
	 * 删除未审核外出信息申请(delete egress apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delViewEgressionview(Object object) throws Exception {
		Boolean falg = true;
		Map delApply = (Map) this.queryForObject(
				"ess.viewApply.getLeaveviewByID", object);
		if (delApply != null) {
			delApply.put("AR_DETAIL", ((Map) object).get("AR_DETAIL"));
			delApply.put("itemno", Arrays
					.asList(new Integer[] { 9, 10, 11, 12 }));
			this.delete("ess.viewApply.delArDetail", delApply);
		}
		this.delete("ess.viewApply.delEssLeaveApplyTb", object);
		this.delete("ess.viewApply.delEssAffirm", object);
		this.delete("ess.viewApply.delArApplyResult", object);
		return falg;
	}

	/**
	 * 外出信息申请(view egression apply information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewEgressionInfoList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.viewApply.getEssviewEgressionviewList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.viewApply.getEssviewEgressionviewList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 外出信息申请(view egression apply information)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewEgressionInfoList(Object object) {
		List returnList = new ArrayList();
		returnList = this.viewEgressionInfoList(object, -1, -1);
		return returnList;
	}

	/**
	 * 出差信息申请(view evection apply information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewEvectionInfoList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.viewApply.getEssviewEvectionviewList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.viewApply.getEssviewEvectionviewList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 出差信息申请(view evection apply information)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewEvectionInfoList(Object object) {
		List returnList = new ArrayList();
		returnList = this.viewEvectionInfoList(object, -1, -1);
		return returnList;
	}

	/**
	 * 休假信息申请(view leave apply information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewLeaveInfoList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.viewApply.getEssViewLeaveviewList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.viewApply.getEssViewLeaveviewList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 休假信息申请(view leave apply information)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewLeaveInfoList(Object object) {
		List returnList = new ArrayList();
		returnList = this.viewLeaveInfoList(object, -1, -1);
		return returnList;
	}

	/**
	 * 加班信息申请(view overtime apply information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewOvertimeInfoList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.viewApply.getEssOvertimeApplyInfoList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.viewApply.getEssOvertimeApplyInfoList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 加班信息申请(view overtime apply information)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewOvertimeInfoList(Object object) {
		List returnList = new ArrayList();
		returnList = this.viewOvertimeInfoList(object, -1, -1);
		return returnList;
	}

	/**
	 * 个人信息申请(view personal apply information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewPersonalInfoList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.viewApply.getEssPersonalInfoList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.viewApply.getEssPersonalInfoList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 个人信息申请(view personal apply information)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewPersonalInfoList(Object object) {
		List returnList = new ArrayList();
		returnList = this.viewPersonalInfoList(object, -1, -1);
		return returnList;
	}

	/**
	 * 外出信息申请个数(view egression apply information count)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int viewEgressionInfoListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"ess.viewApply.getEssviewEgressionviewListCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 出差信息申请个数(view evection apply information count)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int viewEvectionInfoListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"ess.viewApply.getEssviewEvectionviewListCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 休假信息申请个数(view leave apply information count)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int viewLeaveInfoListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.viewApply.getEssviewLeaveviewListCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 加班信息申请个数(view overtime apply information count)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int viewOvertimeInfoListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.viewApply.getEssviewOtviewListCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 个人信息申请个数(view personal apply information count)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int viewPersonalInfoListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.viewApply.getEssPersonalInfoListCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 查询加班申请对应的默认转换类型
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getDefaultTurnDaoxiu(Object obj) throws Exception {
		Object daoXiu = null;
		daoXiu = (Object) this.queryForObject("ess.infoApply.getDefaultTurnDaoxiu", obj);
		return daoXiu;
	}
	
	/**
	 * 获得申请时间的长度(get length of apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getLengthOfApply(Object obj) throws Exception {
		String length = "0.0";
		length = (String) this.queryForObject("ess.infoApply.retrieveApplyLeaveLength", obj);
		return length;
	}

	/**
	 * 通过申请NO(view personal apply information count)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int getAffirmedApplyInfoCntByApplyNo(Object object) throws Exception {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject(
							"ess.viewApply.getAffirmedApplyInfoCntByApplyNo",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 获得申请时间的长度(get length of apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getDeductTimeCountInOtTimeByCpnyId(Object obj) throws Exception {
		String length = "0.0";
		length = (String) this.queryForObject("ess.viewApply.getDeductTimeCountInOtTimeByCpnyId", obj);
		return length;
	}

	/**
	 * 获得申请时间的长度(get length of apply)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int getDeductFromTimeByCpnyId(Object object)
			throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.viewApply.getDeductFromTimeByCpnyId",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获得申请时间的长度(get length of apply)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int getDeductToTimeByCpnyId(Object object)
			throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.viewApply.getDeductToTimeByCpnyId",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewLeaveInfoList_c11(Object object) {
		List returnList = new ArrayList();
		returnList = this.viewLeaveInfoList_c11(object, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewLeaveInfoList_c11(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ess.viewApply.getEssViewLeaveviewList_c11", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ess.viewApply.getEssViewLeaveviewList_c11", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
}