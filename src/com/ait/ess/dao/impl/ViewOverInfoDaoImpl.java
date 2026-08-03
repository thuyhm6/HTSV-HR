package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.ViewOverInfoDao;
import com.ait.web.util.CommonException;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class ViewOverInfoDaoImpl extends SqlMapClientSupport implements ViewOverInfoDao{

	@Override
	public List getOtDeductTimeList(Object obj) throws Exception {
		try {
			return this.queryForList("ess.infoApply.getOtDeductTimeList", obj);
		} catch (SQLException e) {
			throw new CommonException("获得扣除时间的LIST失败,请重试!");
		}
	}

	@Override
	public List getSelectCode() throws Exception {
		List list = null;
		
		try {
			return this.queryForList("ess.overtime.getselectcode");
		} catch (SQLException e) {
			throw new CommonException("获得扣除时间的LIST失败,请重试!");
			
		}
		
		
	}
	/**
	 * 将加班申请时间按天分解并封装到LIST(decompose overtime apply date for list)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOvertimeApplyAllDateList(LinkedHashMap paramMap) {
		try {
			return this.queryForList("ess.infoApply.getOvertimeApplyAllDateList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	private int getEssApplySeq() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.infoApply.getEssApplySeq")),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量添加加班申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addOvertimeApplyInBatch(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap map = (LinkedHashMap) list.get(i);
				LinkedHashMap tempMap = new LinkedHashMap();
				int essApplySeq = getEssApplySeq();

				if (map != null && map.get("PARAM_MAP") != null) {
					LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
					obj.put("APPLY_NO_SEQ", essApplySeq);
					this.insert("ess.infoApply.insertOvertimeApply", obj);
					tempMap.put("CREATED_BY", ((LinkedHashMap) obj).get("CREATED_BY"));
					tempMap.put("APPLY_TYPE_NO", ((LinkedHashMap) obj).get("APPLY_TYPE_NO"));
					tempMap.put("APPLY_NO_SEQ", essApplySeq);
				}

				if (map != null && map.get("DISTINCT_LIST") != null) {
					List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
							tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							this.insert("ess.infoApply.insertApplyReviewer",tempMap);
						}
					}
				}
			}
		}
		
	}
	/**
	 * 根据法人获得该法人配置的参数(get ess param infomation by cpny_id)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getEssParamInfoByParamNoAndCpnyId(LinkedHashMap essParamMap) 
				throws Exception {
			try {
				return this.queryForObject("ess.infoApply.getParamInfoValue",
						essParamMap);
			} catch (Exception e) {
				throw new Exception(
						"getEssParamInfoByParamNoAndCpnyId Exception. ", e);
			}
	}
	

	@Override
	public Object getPersonInfoByPersonId(LinkedHashMap paramMap) throws Exception {
		try {
			return this.queryForObject("ess.infoApply.getPersonInfoByPersonId",	paramMap);
		} catch (Exception e) {
			throw new Exception("Validate vacationEmp date Exception. ", e);
		}
	}
	/**
	 * 获得日历类型(get calendar type)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getDataType(LinkedHashMap paramMap) {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.infoApply.getDataType", paramMap)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 根据PERSONID或DEPTNO取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	@Override
	public List<LinkedHashMap> getAffirmorListByPersonID(LinkedHashMap paramMap) throws SQLException {
		return this.queryForList("ess.infoApply.getAffirmorListByPersonID", paramMap);
	}

	/**
	 * 根据是否有针对部门取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LinkedHashMap> getAffirmorListByDeptNo(LinkedHashMap paramMap) throws SQLException {
		return this.queryForList("ess.infoApply.getAffirmorListByDeptNo", paramMap);
	}

	
	/**
	 * 无特殊设置取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorListByNormal(LinkedHashMap paramMap) throws SQLException {
		LinkedHashMap object = (LinkedHashMap) paramMap;
		return this.queryForList("ess.infoApply.getAffirmorListByNormal",object);
	}
	/**
	 * 取得加班申请日期的班次时间(get overtime apply with shift)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtApplyDateWithShift(Map paramMap) {
		try {
			return this.queryForList("ess.infoApply.getOtApplyDateWithShift",
					paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获得该员工已申请的加班申请时间(get person exist overtime apply date)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getExistOtApplyDate(Map paraMap) {
		try {
			return this.queryForList("ess.infoApply.getExistOtApplyDate", paraMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获得该员工已经存在的休假申请时间(get person exist leave apply date)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getExistLeaveDate(Map paramMap) {
		try {
			return this.queryForList("ess.infoApply.getExistLeaveDate", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List getDateByPersonIdAndCpny(Object obj) {
		try {
			return this.queryForList("ess.infoApply.getDateByPersonIdAndCpny", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

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
	 * 查询决裁状态(search approve status)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorList(Object object) throws Exception {
		return this.queryForList("ess.overtime.getAffirmorList", object);
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
			return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.overtime.getDeductFromTimeByCpnyId",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
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
		length = (String) this.queryForObject("ess.overtime.getDeductTimeCountInOtTimeByCpnyId", obj);
		return length;
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
					.queryForObject("ess.overtime.getEssviewOtviewListCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

}
