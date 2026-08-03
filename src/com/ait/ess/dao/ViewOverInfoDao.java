package com.ait.ess.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ViewOverInfoDao {
	public List getOtDeductTimeList(Object obj) throws Exception;

	public List getSelectCode() throws Exception;

	public List getOvertimeApplyAllDateList(LinkedHashMap paramMap);

	public void addOvertimeApplyInBatch(List batchOtApplyList) throws Exception;

	public Object getEssParamInfoByParamNoAndCpnyId(LinkedHashMap essParamMap) throws Exception;

	public Object getPersonInfoByPersonId(LinkedHashMap paramMap) throws Exception;

	public int getDataType(LinkedHashMap paramMap);

	public List<LinkedHashMap> getAffirmorListByPersonID(LinkedHashMap paramMap) throws SQLException;

	public List<LinkedHashMap> getAffirmorListByDeptNo(LinkedHashMap paramMap) throws SQLException;

	public List getAffirmorListByNormal(LinkedHashMap paramMap) throws SQLException;

	public List getOtApplyDateWithShift(Map paramMap);

	public List getExistOtApplyDate(Map paraMap);

	public List getExistLeaveDate(Map paramMap);

	public List getDateByPersonIdAndCpny(Object obj);

	public List viewOvertimeInfoList(Object object, int pageNum,
			int numPerPage);

	public List viewOvertimeInfoList(Object object);

	public List getAffirmorList(Object object) throws Exception;

	public int getDeductFromTimeByCpnyId(Object object) throws Exception;

	public String getDeductTimeCountInOtTimeByCpnyId(Object object) throws Exception;

	public int viewOvertimeInfoListCnt(Object object);
}
