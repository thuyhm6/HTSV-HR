package com.ait.ar.dao;

import java.util.List;

public interface ArEmailDao {
	//考勤月汇总
	@SuppressWarnings("unchecked")
	public List getArEmailList(Object object);

	//日考勤明细
	@SuppressWarnings("unchecked")
	public List getArDetailListEmail(Object object);
	
	//查找考勤员
	@SuppressWarnings("unchecked")
	public List getAttKeeperList(Object object);
	
	//查找部门
	@SuppressWarnings("unchecked")
	public List getOrgDeptList(Object object);
}
