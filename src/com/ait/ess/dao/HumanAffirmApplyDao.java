package com.ait.ess.dao;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: HumanAffirmApplyDao.java
 * @Description:人事确认
 * @Create date: Apr 9, 2012 5:09:25 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Apr 9, 2012 5:09:25 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
public interface HumanAffirmApplyDao {
	/**
	 * 个人信息申请集合(personal information apply list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getPersonInfoApplyConfirmList(Object obj) throws Exception;

	/**
	 * 个人信息申请集合(personal information apply list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List getPersonInfoApplyConfirmList(Object obj, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 个人信息申请总数(personal information apply total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getPersonInfoApplyConfirmListCnt(Object obj) throws Exception;

	/**
	 * 人事确认通过/否决(human confirm pass or reject)
	 * 
	 * @param object
	 * @param conFlag
	 * @return
	 * @throws Exception
	 */
	public int savePersonInfoHumanConfirm(Object object) throws Exception;

	/**
	 * 批量人事确认通过/否决(batch human confirm pass or reject)
	 * 
	 * @param object
	 * @param conFlag
	 * @return
	 * @throws Exception
	 */
	public int savePersonInfoHumanConfirmForBatch(List list) throws Exception;

	/**
	 * 查询属于该法人的加班类型List
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getOverTimeApplyTypeList(Object obj) throws Exception;
	
	/**
	 * 查询属于该法人的加班转换类型List
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getConverTypeList(Object obj) throws Exception;
	
	/**
	 * 查询属于该法人的加班转默认调休类型List
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getDefaultDaoXiuList(Object obj) throws Exception;
	
	/**
	 * 人事确认--加班申请信息列表(personnel confirm:overtime apply information list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getOvertimeApplyConfirmList(Object obj) throws Exception;

	/**
	 * 人事确认--加班申请信息列表(personnel confirm:overtime apply information list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List getOvertimeApplyConfirmList(Object obj, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 人事确认--加班申请信息总数(personnel confirm:overtime apply information total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOvertimeApplyConfirmListCnt(Object obj) throws Exception;

	/**
	 * 人事确认--休假/出差/外出申请列表(personnel confirm:leave/evection/egression apply
	 * information list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getLeaveApplyConfirmList(Object obj) throws Exception;

	/**
	 * 人事确认--休假/出差/外出申请列表(personnel confirm:leave/evection/egression apply
	 * information list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List getLeaveApplyConfirmList(Object obj, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 人事确认--休假/出差/外出申请总数(personnel confirm:leave/evection/egression apply
	 * information total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getLeaveApplyConfirmListCnt(Object obj) throws Exception;

	/**
	 * 根据法人和参数号查找对应的值(get parameter Value By CpnyId And ParamNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getParamValueByCpnyIdAndParamNo(LinkedHashMap obj)
			throws Exception;

	/**
	 * 根据申请号获得休假申请(get Leave-Apply Information By ApplyNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getLeaveApplyInfoByApplyNo(LinkedHashMap obj)
			throws Exception;

	/**
	 * 根据申请号获得加班申请信息(get Overtime-Apply Information By ApplyNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getOvertimeApplyInfoByApplyNo(LinkedHashMap obj)
			throws Exception;

	/**
	 * 加班申请人事确认通过/否决(human confirm pass or reject)
	 * 
	 * @param object
	 * @param conFlag
	 * @return
	 * @throws Exception
	 */
	public int saveOvertimeApplyHumanConfirm(Object object) throws Exception;

	/**
	 * 加班申请批量人事确认通过/否决(batch human confirm pass or reject)
	 * 
	 * @param object
	 * @param conFlag
	 * @return
	 * @throws Exception
	 */
	public int saveOvertimeApplyHumanConfirmForBatch(List list)
			throws Exception;

	/**
	 * 休假申请人事确认通过/否决(human confirm pass or reject)
	 * 
	 * @param object
	 * @param conFlag
	 * @return
	 * @throws Exception
	 */
	public int saveLeaveApplyHumanConfirm(Object object) throws Exception;

	/**
	 * 休假申请批量人事确认通过/否决(batch human confirm pass or reject)
	 * 
	 * @param object
	 * @param conFlag
	 * @return
	 * @throws Exception
	 */ 
	public int saveLeaveApplyHumanConfirmForBatch(List list) throws Exception;

}
