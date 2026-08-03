package com.ait.ess.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 信息申请(information apply)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: InfoApplyDao.java
 * @Description:
 * @Create date: Feb 17, 2012 10:51:41 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 17, 2012 10:51:41 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
public interface InfoApplyLeaveDao {
	 
	/**
	 * 员工信息查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	public Object getPersonalInfoByPid(Object object);
	
	/**
	 * 加班申请考勤区间列表(search ot apply ar_month info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveApplyArMonthList(Object object);
	/**
	 * 休假申请信息列表，分页(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBatchLeaveAffirmInfoList(Object object, int currentPage, int pageSize);

	/**
	 * 休假申请决裁信息列表(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveAffirmInfoList(Object object) throws Exception;
	/**
	 * 个人考勤申请明细
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalAttInfoDetailList(Object object) throws Exception;
	/**
	 * 异常考勤申请明细
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalAttInfoDetailList1(Object object) throws Exception;
	
	/**
	 * 异常考勤申请明细
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewCheckAttencetanceExForBatchList(Object object) throws Exception;
	
	/**
	 * 内务考勤查询页面(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCoordLeaveInfoList(Object object) throws Exception;
	/**
	 * 部门员工休假查询(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDeptLeaveInfoList(Object object);
	
	/**
	 * 休假申请信息列表(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBatchLeaveAffirmInfoList(Object object);
	/**
	 * 多天休假申请信息列表(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBatchLeaveAffirmMoreDayInfoList(Object object);
	
	/**
	 * 添加(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	
	public int delAttendanceExForBatchInfoList(Object obj) throws Exception;
	
	public int delAttendanceExForBatchInfoListHAE(Object obj) throws Exception;
	
	public int delOTApplyInfoForBatchInfoListHAE(Object obj) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getNullBatchLeaveAffirmInfoList(Object object);
	@SuppressWarnings("unchecked")
	public List getIdCardExpire(Object object);
	
	@SuppressWarnings("unchecked")
	public List viewAddAttendanceApplyInfoForBatch(Object object);
	@SuppressWarnings("unchecked")
	public List getFileList(Object object);
	@SuppressWarnings("unchecked")
	public List getAttendanceExForBatchInfoList(Object object);
	@SuppressWarnings("unchecked")
	public int getAttendanceExInfoListCnt(Object object);
	/**
	 * 添加(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getNullBatchOTSSTAffirmInfoList(Object object);
	/**
	 * 添加(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getNullBatchAJTSTOAffirmInfoList(Object object);
	
	/**
	 * 添加(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getNullBatchOTTSTOAffirmInfoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List viewNullBatchOTTSTOAffirmInfoList(Object object);
	
	@SuppressWarnings("unchecked")
	public int delNullBatchOTTSTOAffirmInfoList(Object object) throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public List viewAddOTApplyInfoForBatchHAE(Object obj);
	/**
	 * 加班申请决裁信息列表(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveAffirmInfo2List(Object object);
	
	/**
	 * 加班申请决裁者信息列表(get Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LinkedHashMap> getAffirmInfo2List(Object object);

	/**
	 * 加班申请决裁信息总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getLeaveAffirmInfoListCnt(Object obj) throws Exception;
	/**
	 * 加班申请信息总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getBatchLeaveAffirmInfoListCnt(Object obj) throws Exception;
	
	/**
	 * 根据EMPID 或部门ID取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getAffirmorListByPersonID(Object obj) throws Exception;
	
	/**
	 * 根据员工标识获得员工信息(get person information by personId)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public Object getPersonInfoByPersonId(Object object) throws Exception;
	
	/**
	 * 根据是否有针对部门取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getAffirmorListByDeptNo(Object obj) throws Exception;
	
	/**
	 * 无特殊设置取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getAffirmorListByNormal(Object obj) throws Exception;
	
	/**
	 * 根据法人获得该法人配置的参数(get ess param infomation by cpny_id)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public Object getEssParamInfoByParamNoAndCpnyId(Object object)
			throws Exception;
	
	/**
	 * 获取班次 ,加班类型(batch add leave apply)
	 @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-12-6 下午4:48:18 
	* @version V1.0
	 */
	public List getDateByPersonIdAndCpny(Object obj) throws Exception;
	
	/**
	 * 根据加班申请NO查询此次（个人/批量）申请的所有人(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getApplyorByApplyNoList(Object object);
	
	/**
	 * 根据加班申请NO决裁信息查询(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorByApplyNoList(Object object);
	
	/**
	 * 加班申请check信息查询(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCheckorByApplyNoList(Object object);
	
	/**
	 * 批量删除考勤 异常 申请(batch delete overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delApplyLeaveInfo(List list) throws Exception;
	
	/**
	 * 批量删除考勤 异常 申请(batch delete overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delAttencetanceEx(List list) throws Exception;
	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delLeaveApplyInBatch(List list) throws Exception;
	
	/**
	 * 批量删除考勤
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delAttendanceApplyInfoForBatch(List list) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int delAttendanceExInBatchForBatch(List list) throws Exception;
	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delLeaveApply(Object object) throws Exception;
	
	/**
	 * 取消已审核通过的加班申请( cancel overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean cancelLeaveApply(Object object) throws Exception;
	
	/**
	 * 取消已审核通过的加班申请( cancel leave apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void addXiaojiaLeaveApplyInfo(LinkedHashMap map) throws Exception;
	
	/**
	 * 显示个人信息申请修改页面(View personal information apply）
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Map getPersonInfo(Object obj) throws Exception;

	/**
	 * 批量加班申请的人员列表(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getPersonList(Object object, int currentPage, int pageSize);

	/**
	 * 批量加班申请的人员列表(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonList(Object object);
	/**
	 *(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  Object getAttendanceInformation(Object object);
	@SuppressWarnings("unchecked")
	public  Map getOTSSTInformation(Object object);
	@SuppressWarnings("unchecked")
	public  Map getAJTSTOInformation(Object object);
	@SuppressWarnings("unchecked")
	public  Map getOTTSTOInformation(Object object);

	/**
	 * 批量加班申请的人员列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getPersonListCnt(Object obj) throws Exception;

	/**
	 * 添加个人信息申请(add personal information apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean addPersonalInfoApply(Object obj);

	/**
	 * 添加加班申请(add overtime apply)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public void addOvertimeApply(Object obj, List<LinkedHashMap> list)
			throws Exception;

	/**
	 * 添加休假申请(add leave apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addLeaveApply(LinkedHashMap leaveMap) throws Exception;
	
	public void insertSSTOtIsNull(Object leaveMap) throws Exception;
	public void insertTSTOADJUSTIsNull(Object leaveMap) throws Exception;

	/**
	 * 批量添加休假申请(batch add leave apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addLeaveApplyInBatch(List list) throws Exception;
	/**
	 * 个人添加休假申请(batch add leave apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addLeaveApplyInPerson(List list) throws Exception;
	/**
	 * 批量添加休假申请(batch add leave apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addLeaveApplyInArDetailBatch(LinkedHashMap map) throws Exception;
	/**
	 * 批量修改休假申请(batch add leave apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void updateLeaveApplyInArDetailBatch(List list) throws Exception;
	/**
	 * 批量修改休假申请(batch add leave apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void updateLeaveApplyInArDetailBatchForTwoApply(LinkedHashMap map) throws Exception;
	
	public void updatAdjustFlag(LinkedHashMap map) throws Exception;
	
	 
	public void insertSubmitLeaveApplyInBatchForMoreDay(LinkedHashMap map) throws Exception;

	/**
	 * 添加出差申请(add Evection apply)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public void addEvectionApply(LinkedHashMap leaveMap) throws Exception;

	/**
	 * 添加外出申请(add egression apply)
	 * 
	 * @param leaveMap
	 * @throws Exception
	 */
	public void addEgressionApply(LinkedHashMap leaveMap) throws Exception;

	/**
	 * 获得决裁者
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getAffirmorList(Object obj) throws Exception;

	/**
	 * 查看班次
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getEmpShift(Object obj) throws Exception;

	/**
	 * 查询公司用于扣除时间的LIST(search ot deduct time list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getOtDeductTimeList(Object obj) throws Exception;
	
	/**
	 * 剩余年假剩余福利年假
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List retrieveVacationEmpREST(Object obj) throws Exception;
	
	/**
	 * 剩余年假计算--2013-12-27--lufeng
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getVacationEmpREST(Object obj) throws Exception;

	

	

	

	/**
	 * 获得该员工已申请的加班申请时间(get person exist overtime apply date)
	 * 
	 * @param obj
	 * @return
	 */
	public List getExistOtApplyDate(Object obj) throws Exception;

	/**
	 * 获得该员工已经存在的休假申请时间(get person exist leave apply date)
	 * 
	 * @param obj
	 * @return
	 */
	public List getExistLeaveDate(Object obj) throws Exception;

	/**
	 * 取得加班申请日期的班次时间(get overtime apply with shift)
	 * 
	 * @param obj
	 * @return
	 */
	public List getOtApplyDateWithShift(Object obj) throws Exception;

	/**
	 * 获得日历类型(get calendar type)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getDataType(Object object) throws Exception;

	/**
	 * 获得申请中的年假数(get applying Annual leave )
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object vacApplying(Object object) throws Exception;

	/**
	 * 获得剩余的年假数(get remaining annual leave )
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public Object restVac(Object object) throws Exception;

	/**
	 * 根据法人和参数号查找对应的值(get Param Value By CpnyId And ParamNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getParamValueByCpnyIdAndParamNo(Object obj) throws Exception;

	
	
	/**
	 * 剩余调休数(rest adjust leave)
	 * @param obj
	 * @return
	 * @throws Exception
	 */ 
	public String getSurplusAdjustRest(Object obj) throws Exception;
	
	/**
	 * 剩余调休数2013-06-20 lufeng 新增(rest adjust leave)
	 * @param obj
	 * @return
	 * @throws Exception
	 */ 
	public String getAdjustRestNew(Object obj) throws Exception;
	
	/**
	 * 获得申请时间的长度(get length of apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getLeaveApplyLength(Object obj) throws Exception;
	
	/**
	 * 将加班申请时间按天分解并封装到LIST(decompose overtime apply date for list)
	 * 
	 * @param obj
	 * @return
	 */ 
	public List getOvertimeApplyAllDateList(Object obj) throws Exception ;

	/**
	 * 去年剩余年假剩余福利年假
	 * @param obj
	 * @return
	 */
	public Integer retrieveVacationEmpRESTQN(Object obj)throws Exception ;

	/**
	 * 查询是否有个人的配偶出生日期
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-1 上午10:24:10 
	* @version V1.0
	 */
	public String getspouseBirth(Object obj);

	/**
	 * 根据员工的部门所在地查看 休假基准的说明
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-3 下午4:18:18 
	* @version V1.0
	 */
	public String getviewVacationStandard(Object obj);
	

	/**
	 * 连续申请出差
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-17 上午11:54:23 
	* @version V1.0
	 */
	public void addEvectionApplyList(List batchOtApplyList) throws Exception;

	public String getZhengce(LinkedHashMap map);
	
	public String getShenqingshichang(LinkedHashMap map);
	public LinkedHashMap getLeaveDispalydaoxiuOrnianjMap(LinkedHashMap map);
	public String getApplyTypeCode(LinkedHashMap map);
	public String getChechedAffrim(LinkedHashMap map);
	public String getkaoQinNo(LinkedHashMap map);
	public String getkaoQinNoSST(LinkedHashMap map);
	public String getkaoQinNoPkNo(LinkedHashMap map);
	public String getChechedPersonidIsNull(LinkedHashMap map);
	public int getCheckPersonCount(LinkedHashMap map) throws Exception;
	public String getYesBefAffrimNo(LinkedHashMap map);
	public int getApplyCountYN(LinkedHashMap map) throws Exception;
	public int getApplyLOCKYN(LinkedHashMap map) throws Exception;
	public int getApplyOTLOCKYN(LinkedHashMap map) throws Exception;
	public Object getChechedSex(LinkedHashMap map);
	public Object getTrainLocalName(LinkedHashMap map);
	public String getChechedItemName(LinkedHashMap map);

	/**
	 * 根据法人和参数号查找对应的值(get Param Value By CpnyId And ParamNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getLeaveInfoByLeave(Object obj) throws Exception;
	/**
	 * 根据法人和参数号查找对应的值(get Param Value By CpnyId And ParamNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getOtApplyPersonal(Object obj) throws Exception;
	/**
	 * 根据法人和参数号查找对应的值(get Param Value By CpnyId And ParamNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getOtApplyPersonalXiao(Object obj) throws Exception;
	
	/**
	 * 根据法人和参数号查找对应的值(get Param Value By CpnyId And ParamNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getOtApplyPersonalIfDisplay(Object obj) throws Exception;
	
	/**
	 * 取个人决裁者
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getAffirmorListByLeave(Object obj) throws Exception;
	
	/**
	 * 获得该员工已经存在的休假申请时间(get person exist leave apply date)
	 * 
	 * @param obj
	 * @return
	 */
	public List getExistLeaveDateForUpdate(Object obj) throws Exception;

	/**
	 * 获得申请中的年假数(get applying Annual leave )
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object vacApplyingForUpdate(Object object) throws Exception;

	/**
	 * 批量添加休假申请(batch add leave apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void updateLeaveApplyInBatch(List list) throws Exception;


	/**
	 * 休假申请临时表信息总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveAffirmBatchTempInfoList(Object object);

	public int getLeaveAffirmBatchTempInfoListCnt(Object obj) throws Exception;

	/**
	 * 删除休假临时表
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int delTempLeaveApply(Object object) throws Exception;

	/**
	 * 更新临时表错误信息(add Evection apply)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public void updateEssLeaveApplyTbTemp(LinkedHashMap leaveMap) throws Exception;

	/**
	 * 批量添加休假申请(batch add leave apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addLeaveApplyInBatchNew(List list) throws Exception;

	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delLeaveApplyInBatch(List list, LinkedHashMap personMap) throws Exception;
	
	/**
	 * 获取休假导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssLeaveTempList(Object object);
	
	/**
	 * 获取休假导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssLeaveTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取休假导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssLeaveTempCnt(Object object);
	
	/**
	 * 获取出错的休假导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssLeaveTempErrorCnt(Object object);
	/**
	 * 获取员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfo(Object obj) throws Exception;

	/**
	 * 剩余调休数2014-07-26 jiahch
	 * @param obj
	 * @return
	 * @throws Exception
	 */ 
	public String getTiaoxiu(Object obj) throws Exception;

	/**
	 * 剩余移年年假2014-07-26 jiahch
	 * @param obj
	 * @return
	 * @throws Exception
	 */ 
	public String getYiniannianjia(Object obj) throws Exception;

	/**
	 * 剩余福利年假数2014-07-26 jiahch
	 * @param obj
	 * @return
	 * @throws Exception
	 */ 
	public String getRestFulinianjia(Object obj) throws Exception;

	/**
	 * 剩余法定年假数2014-07-26 jiahch
	 * @param obj
	 * @return
	 * @throws Exception
	 */ 
	public String getRestFadingnianjia(Object obj) throws Exception;
	
	/**
	 * 休假申请详细信息列表，分页(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveBatchAffirmInfoList(Object object, int currentPage, int pageSize);
	
	
	/**
	 * 加班申请详细信息列表，分页(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtBatchAffirmInfoList(Object object, int currentPage, int pageSize);

	/**
	 * 休假申请详细信息列表(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveBatchAffirmInfoList(Object object);
	
	
	/**
	 * 加班申请详细信息列表(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtBatchAffirmInfoList(Object object);
	
	
	/**
	 * 休假申请详细信息总数(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getLeaveBatchAffirmInfoListCnt(Object obj) throws Exception ;

	/**
	 * 加班申请详细信息总数(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtBatchAffirmInfoListCnt(Object obj) throws Exception ;
	/**
	 * 获取该员工当前月考勤日期
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCurrentArDate(Object obj) throws Exception;
	
	
	/**
	 * 获取该员工当前月考勤日期
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCurrentArDateOt(Object obj) throws Exception;
	
	/**
	 * 批量提交休假申请
	 * 
	 * @param obj
	 * @return
	 */
	public int submitLeaveApplyInBatch(List list) throws Exception ;
	/**
	 * 批量提交休假申请
	 * 
	 * @param obj
	 * @return
	 */
	public int addtLeaveApplyInBatch(LinkedHashMap map) throws Exception ;
	/**
	 * 批量提交休假申请
	 * 
	 * @param obj
	 * @return
	 */
	public int addtOtApplyInBatch(LinkedHashMap map) throws Exception ;
	/**
	 * 批量提交加班申请
	 * 
	 * @param obj
	 * @return
	 */
	public int addtOtTSTOApplyInBatch(LinkedHashMap map) throws Exception ;
	/**
	 * 批量提交休假申请
	 * 
	 * @param obj
	 * @return
	 */
	public int addtOtApplyInBatchTSTO(LinkedHashMap map) throws Exception ;
	/**
	 * 添加倒休管理中的加班数据
	 * 
	 * @param obj
	 * @return
	 */
	public int addtAdjustApplyInBatchTSTO(LinkedHashMap map) throws Exception ;
	/**
	 * 批量提交修改的休假申请
	 * 
	 * @param obj
	 * @return
	 */
	public int insertSubmitLeaveApplyInBatch(LinkedHashMap map) throws Exception ;
	/**
	 * 批量修改部门长批准后
	 * 
	 * @param obj
	 * @return
	 */
	public int updatSubmitLeaveApplyInBatch(List list) throws Exception ;
	
	/**
	 * 批量修改--部门批准--二次申请
	 * 
	 * @param obj
	 * @return
	 */
	public int updatSubmitArDetailBatchForTwoApply(LinkedHashMap  map ) throws Exception ;
	/**
	 * 修改新添加的数据
	 * 
	 * @param obj
	 * @return
	 */
	public int updatSubmitArDetailBatchForNullPersonId(LinkedHashMap map) throws Exception ;
	/**
	 * 修改AR_DETAIL_TSTO_APPLY中的NULL_FLAG,方便在删除的时候恢复最先的数据
	 * 
	 * @param obj
	 * @return
	 */
	public int updatSubmitLeaveApplyInBatchForDeletePrepare(LinkedHashMap map) throws Exception ;
	/**
	 * 修改AR_DETAIL_TSTO_APPLY当前人的正常出勤
	 * 
	 * @param obj
	 * @return
	 */
	public int updatSubmitLeaveApplyInBatchForDeletePrepareOnPersonid(LinkedHashMap map) throws Exception ;
	/**
	 * 批量提交修改的休假申请(不够八小时)
	 * 
	 * @param obj
	 * @return
	 */
	public int updatSubmitLeaveApplyInBatchLess8(List list) throws Exception ;
	/**
	 * 
	 * 
	 * @param obj
	 * @return
	 */
	public int updatSubmitLeaveApplyInShenPiBatch(List list) throws Exception ;
	/**
	 * 
	 * 
	 * @param obj
	 * @return
	 */
	public int updatSubmitLeaveApplyInCancleBatch(List list) throws Exception ;
	/**
	 * 获取员工性别
	 * @param map
	 * @return
	 */
	public String getSex(LinkedHashMap map) ;
	
	/**
	 * 判断是否TA法人试用期
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List isProbation(Object obj) throws Exception;
	
	/**
	 * 查询附件
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEssFileList(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getPhotoFileList(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public Object queryEmployeePhoto(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List queryEmployeePhoto_name(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public int updatePath(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public int updatePathName(Object obj) ;
	
	/**
	 * 员工班次结束时间查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	public Object getShiftTime(Object object);
	
	/**
	 * 获取考勤年
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getCurrentYear(Object obj) throws Exception;
	
	/**
	 * 获取考勤月
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getCurrentMonth(Object obj) throws Exception ;
	
	/**
	 * 休假验证 
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String valLeaveLength(Object obj) throws Exception ;
	
	/**
	 * 休假最大时长验证 
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String valLeaveMaxLength(Object obj) throws Exception ;
	
	/**
	 * 获取考勤年开始结束日期限制
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getCurrentYearVacLimit(Object obj) throws Exception ;
	
	/**
	 * 获取考勤年开始结束日期限制
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String isFullOneYear(Object obj) throws Exception ;
	
	public void addXiaojiaLeaveApplyInfoNoAffirm(Map map) throws Exception ;
	
	/**
	 * 休假时长单位验证 
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String valLeaveLengthUtil(Object obj) throws Exception;
	
	/**
	 * 发令leave
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addLeaveAssigmentInBatch(List list) throws Exception ;
	
	/**
	 * 获取考勤年开始结束日期限制(年假基准日期)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String isFullOneYearYY(Object obj) throws Exception ;
	
	/**
	 * 追溯申请验证
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String arValidLastMonth(Object obj) throws Exception ;
	
	public int arMonthTime(Object obj) throws Exception ;
	
	/**
	 * 保存考勤
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int saveAttendanceApplyInfoForBatch(Map map) throws Exception;
	
	/**
	 * 保存考勤(可添加删除审批者)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int saveAttApplyInfoByAnyApproverForBatch(Map map) throws Exception;
	
	/**
	 * 生产值保存考勤
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int saveAttendanceApplyInfoForBatchHAE(Map map) throws Exception;
	
	public void saveAttenanceExBatchInfo(Map map) throws Exception;
	
	/**
	 * 添加考勤
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addAttendanceApplyInfoForBatch(Map map) throws Exception;
	
	/**
	 * 查询加班申请决裁信息列表(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveAffirmInfoBatchList(Object obj);
	@SuppressWarnings("unchecked")
	public List viewAdjustRecords(Object obj);

	/**
	 * 查询加班申请决裁信息-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveAffirmInfoBatchList(Object obj, int currentPage, int pageSize);

	/**
	 * 加班申请决裁信息列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getLeaveAffirmInfoBatchListCnt(Object obj) throws Exception;
	

	public void cancelLeaveApplyNoAffirm(Map map) throws Exception;
	
	/**
	 * HZ法人是否上传附件验证
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int isUploadFileForBatchLeave(List list) throws Exception;
	
	/**
	 * 判断PN法人工作日
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String isValidDate(Object obj) throws Exception;
	
	/**
	 * LGEYT法人产期检查假判断
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String VALID_482_LGEYT(Object obj) throws Exception ;
	
	/**
	 * 跨月验证
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String arValidKuaYue(Object obj) throws Exception ;

	public void deleteArDetialByNull(Object obj) throws Exception;

	public String getLeaveApplyCode(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public void deleteAllDataForAdd(LinkedHashMap paramMap) throws Exception;

	@SuppressWarnings("unchecked")
	public int getAllYearaffairsHour(LinkedHashMap paramMap) throws Exception;
	@SuppressWarnings("unchecked")
	public int getAppliedHour(LinkedHashMap paramMap) throws Exception;
	@SuppressWarnings("unchecked")
	public int getAppliedCount(LinkedHashMap paramMap) throws Exception;

	@SuppressWarnings("unchecked")
	public String getArDetailItemNo(Map paramMap) throws Exception;
	@SuppressWarnings("unchecked")
	public String getArDetailItemNoBaseApplyCode(Map paramMap) throws Exception;

	public LinkedHashMap getAttenceArDetailForPkNo(LinkedHashMap paramMap);

	/**
	 * 个人添加考勤申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addLeaveApplySST(LinkedHashMap map) throws Exception ;
	/**
	 * 天津年假信息申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addVacInfo(LinkedHashMap map) throws Exception ;
	
	/**
	 * 休假验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getLeaveCheckSST(LinkedHashMap worHashMap) ;
	/**
	 * 休假验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getLeaveCheckTSTO(LinkedHashMap worHashMap) ;
	/**
	 * TSTO休假验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOTCheckTSTO(LinkedHashMap worHashMap) ;
	/**
	 * TSTO倒休验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAdjustCheckTSTO(LinkedHashMap worHashMap) ;
	
	/**
	 * TSTO加班验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOverTimeCheckTSTO(LinkedHashMap worHashMap) ;
	/**
	 * TSTO内务加班验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCoordOverTimeCheckTSTO(LinkedHashMap worHashMap) ;
	

	/**
	 * 获取SST员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfoSST(Object obj) throws Exception;

	/**
	 * 上审取消SST
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delLeaveApplyInBatchSST(List list) throws Exception;
	public String getOldItemNo(LinkedHashMap paramMap) throws Exception;
	public List getDayArData(LinkedHashMap paramMap) throws Exception;


	/**
	 * 获取SST员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfoSSTForDisplay(Object obj) throws Exception;

	public String getOldAdjustFlag(LinkedHashMap paramMap)throws Exception;
	@SuppressWarnings("unchecked")
	public List getAttendanceTempList(LinkedHashMap paramMap) throws Exception;
	public int getAttendanceTempErrorCnt(Object object) throws Exception;
	public int getAttendanceTempCnt(Object object) throws Exception;
	public int PR_ADD_SHOP_SHIFT(Object object)throws Exception ;


	/**
	 * 个人考勤明细查询(search ot info list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewAttendancePersonalInfoList(Object obj) ;
	
	/**
	 * 获得人员列表(get PersonList View)
	 * @param Map
	 * @param int
	 * @param int
	 * @return List
	 * @throws 
	 */
	public List getPersonListView(Map paramMap, int currentPage, int pageSize);
	
	public int getPersonListViewCnt(Map paramMap);
	
	/**
	 * 批量添加考勤申请
	 * 
	 * @param List
	 * @return
	 * @throws Exception
	 */
	public void addAttendanceApplyInfoForBatchHAE(List paramList) throws Exception;
	
	public List viewAddAttendanceApplyInfoForBatchHAE(Object obj);
	
	public List viewApplyAttenanceBatchInfoHAEList(Object obj);
	
	public Object getApplyAttenanceBatchInfoHAEDetail(Object obj);
	
	/**
	 * 批量添加加班申请
	 * 
	 * @param List
	 * @return
	 * @throws Exception
	 */
	public void addOTApplyInfoForBatchHAE(List paramList) throws Exception;
	
	public List viewApplyOTBatchInfoHAEList(Object obj);
	
	public Object getApplyOTBatchInfoHAEDetail(Object obj);

}