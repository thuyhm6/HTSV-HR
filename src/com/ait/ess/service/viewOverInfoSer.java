package com.ait.ess.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface viewOverInfoSer {
	public List getOtDeductTimeList(HttpServletRequest request) throws Exception;

	public List getSelectCode() throws Exception;

	public int addOvertimeApply(HttpServletRequest request) throws Exception;

	public List getDateByPersonIdAndCpny(HttpServletRequest request);

	public List getAffirmorList(LinkedHashMap map) throws Exception;

	public Object viewOvertimeInfoList(HttpServletRequest request) throws Exception;

	public int viewOvertimeInfoListCnt(HttpServletRequest request) throws Exception;
}
