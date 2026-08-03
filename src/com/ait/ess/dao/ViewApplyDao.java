package com.ait.ess.dao;

import java.util.LinkedHashMap;
import java.util.List;

public interface ViewApplyDao {
	/**
	 * 个人信息申请(view personal apply information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewPersonalInfoList(Object object, int currentPage,
			int pageSize);

	/**
	 * 个人信息申请(view personal apply information)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewPersonalInfoList(Object object);

	/**
	 * 个人信息申请个数(view personal apply information count)
	 * 
	 * @param object
	 * @return
	 */
	public int viewPersonalInfoListCnt(Object object);

	/**
	 * 加班信息申请(view overtime apply information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewOvertimeInfoList(Object object, int currentPage,
			int pageSize);

	/**
	 * 加班信息申请(view overtime apply information)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewOvertimeInfoList(Object object);

	/**
	 * 加班信息申请个数(view overtime apply information count)
	 * 
	 * @param object
	 * @return
	 */
	public int viewOvertimeInfoListCnt(Object object);

	/**
	 * 休假信息申请(view leave apply information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewLeaveInfoList(Object object, int currentPage, int pageSize);

	/**
	 * 休假信息申请(view leave apply information)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewLeaveInfoList(Object object);

	/**
	 * 休假信息申请个数(view leave apply information count)
	 * 
	 * @param object
	 * @return
	 */
	public int viewLeaveInfoListCnt(Object object);

	/**
	 * 出差信息申请(view evection apply information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewEvectionInfoList(Object object, int currentPage,
			int pageSize);

	/**
	 * 出差信息申请(view evection apply information)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewEvectionInfoList(Object object);

	/**
	 * 出差信息申请个数(view evection apply information count)
	 * 
	 * @param object
	 * @return
	 */
	public int viewEvectionInfoListCnt(Object object);

	/**
	 * 外出信息申请(view egression apply information)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewEgressionInfoList(Object object, int currentPage,
			int pageSize);

	/**
	 * 外出信息申请(view egression apply information)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewEgressionInfoList(Object object);

	/**
	 * 外出信息申请个数(view egression apply information count)
	 * 
	 * @param object
	 * @return
	 */
	public int viewEgressionInfoListCnt(Object object);

	/**
	 * 删除未审核个人信息申请(delete personal apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delPersonInfoApply(Object object) throws Exception;

	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delViewOtviewApply(Object object) throws Exception;

	/**
	 * 删除未审核休假信息申请(delete leave apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delViewLeaveviewApply(Object object) throws Exception;

	/**
	 * 删除未审核出差信息申请(delete evection apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delViewEvectionview(Object object) throws Exception;

	/**
	 * 删除未审核外出信息申请(delete egress apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delViewEgressionview(Object object) throws Exception;

	/**
	 * 查询决裁状态(search approve status)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public List getAffirmorList(Object object) throws Exception;
	
	/**
	 * 查询加班申请对应的默认转换类型
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public Object getDefaultTurnDaoxiu(Object object) throws Exception;

	/**
	 * 获得申请时间的长度(get length of apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getLengthOfApply(Object obj) throws Exception;

	/**
	 * 通过申请NO(view personal apply information count)
	 * 
	 * @param object
	 * @return
	 */
	public int getAffirmedApplyInfoCntByApplyNo(Object object) throws Exception;

	/**
	 * 获得申请时间的长度(get length of apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getDeductTimeCountInOtTimeByCpnyId(Object obj) throws Exception;

	/**
	 * 获得申请时间的长度(get length of apply)
	 * 
	 * @param object
	 * @return
	 */
	public int getDeductFromTimeByCpnyId(Object object)
			throws Exception;
	
	/**
	 * 获得申请时间的长度(get length of apply)
	 * 
	 * @param object
	 * @return
	 */
	public int getDeductToTimeByCpnyId(Object object)
			throws Exception;

	/**
	 * 求C11法人的休假
	 * 
	 * @param object
	 * @return
	 */
	public List viewLeaveInfoList_c11(Object object);

	public List viewLeaveInfoList_c11(Object object, int currentPage, int pageSize);
	
}
