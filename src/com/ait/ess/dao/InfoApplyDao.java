package com.ait.ess.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.util.NumberUtils;

import com.ait.sys.bean.AdminBean;

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
public interface InfoApplyDao {
	
	/**
	 * 员工信息查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	public Object getPersonalInfoByPid(Object object);
	
	/**
	 * 根据person_id查询该员工上上月最后一天日期(get last day of last last month)
	 * @param obj
	 * @return object
	 */
	public String getLLastMonthLastDay(Object object);
	/**
	 * 获取班次
	 * @param obj
	 * @return object
	 */
	public Map getPersonIdShift(Object object);
	/**
	 * 获取工作时间
	 * @param obj
	 * @return object
	 */
	public String getPersonIdWorkTime(Object object);
	
	/**
	 * 验证申请的加班是否追溯加班
	 * @param obj
	 * @return object
	 */
	
	public  String otApplyCheckEndDate(Object object);

	/**
	 * 追溯申请验证
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String arValidLastMonth(Object obj) throws Exception ;
	
	
	/**
	 * 
	 * 查询当前考勤开始日期
	 * @param object
	 * @return
	 */
	public String getCurrentArDateOt(Object object);
	
	/**
	 * 
	 * 查询当前考勤开始日期
	 * @param object
	 * @return
	 */
	public String getCurrentArDate(Object object);
	/**
	 * 加班申请考勤区间列表(search ot apply ar_month info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtApplyArMonthList(Object object);
	
	/**
	 * 加班申请决裁信息列表(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmInfo2List(Object object);
	
	/**
	 * 加班申请批次决裁信息列表，分页(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmInfoListBatch(Object object, int currentPage, int pageSize);
	/**
	 * 加班原因(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmInfoListWhy(Object object, int currentPage, int pageSize);


	/**
	 * 加班申请决裁信息列表(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmInfoList(Object object);
	/**
	 * Myhome 个人加班申请明细
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalOtInfoDetailList(Object object);
	/**
	 * 加班个人搜索(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonOtApplyInfoList(Object object);
	
	/**
	 * 加班申请决裁信息列表(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmInfoListBatch(Object object);
	/**
	 * 批量调休处理(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewAdjustLeaveTSTOBatchList(Object object);
	/**
	 * 加班原因查询(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getOtAffirmInfoListWhy(Object object);

	/**
	 * 加班申请决裁信息总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtAffirmInfoListCnt(Object obj) throws Exception;
	
	/**
	 * 加班申请决裁信息总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtAffirmInfoListCntBatch(Object obj) throws Exception;
	
	
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
	 * 取申请时长
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap getOtApplyLength(LinkedHashMap map);
	
	/**
	 * 取加班的开始结束时间
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getOtApplyDate(LinkedHashMap map);
	
	/**
	 * 取本考勤月申请总时长（不包括本次申请）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap getOtApplyLengthZong(LinkedHashMap map);
	
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
	 * 批量添加加班申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addOvertimeApplyInBatch(List list) throws Exception;
	
	/**
	 * 批量添加加班申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addOvertimeApplyInBatch(List list,String type,String updateapplyno) throws Exception;
	
	
	

	/**
		 * 批量加班插入正式表(验证通过后--从临时表导入到正式表)
		 * @param 
		 * @return
		 */
	@SuppressWarnings("unchecked")
	public String callInsertBatchOt(Object object)  throws Exception;
		

	/**
	 * 更新导入的加班申请的check结果(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int updateOtApplyCheckResult(Object object) throws Exception;
	/**
	 * 查询员工是否可以被管理
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int selectOtApplyCheckResult(Object object) throws Exception;
	
	/**
	 * 批量修改加班申请(update overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void updateOvertimeApplyInBatch(List list) throws Exception;
	
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
	 * 获取日期类型(get the date type code)
	 @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-12-6 下午4:48:18 
	* @version V1.0
	 */
	public List getDateTypeByDateAndCpny(Object obj) throws Exception;
	
	public Map getDefaultStartEndTime(Object obj) throws Exception;
	public Map getOtLimit(Object obj) throws Exception;
	
	public List getChangeOtType(Object obj) throws Exception;
	/**
	 * 获取员工日期类型(get the date type code)
	 @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr wangqiang@ait.net.cn 
	* @date 2013-12-6 下午4:48:18 
	* @version V1.0
	 */
	public List getDataType2(Object obj) throws Exception;
	/**
	 * 获取员工日期类型(get the date type code)
	 @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr wangqiang@ait.net.cn 
	* @date 2013-12-6 下午4:48:18 
	* @version V1.0
	 */
	public List getDataType2w(Object obj) throws Exception;
 
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
	 * 根据加班申请NO决裁信息查询(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorByApplyNoListXiao(Object object);
	
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
	 * 加班申请check信息查询(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCheckorByApplyNoListXiao(Object object);
	
	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delOvertimeApplyInBatch(List list) throws Exception;
	/**
	 * TSTO批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delOvertimeApplyInBatchTSTO(List list) throws Exception;
	/**
	 * 批量删除倒休申请的数据(batch delete overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delOvertimeApplyInBatchAdjustTSTO(List list) throws Exception;
	/**
	 * 批量取消加班申请(batch delete overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateOvertimeApplyInBatchForCancel(List list) throws Exception;
	
	/**
	 *  提交加班申请
	 *  
	 *  @param list
	 * 	@retrun 
	 *	 @throws Exception
	 * by:wangqiang  2014/09/3
	 */
	@SuppressWarnings("unchecked")
	public int submitOtApplyInBatch(List list) throws Exception;
	
	
	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delOvertimeApply(Object object) throws Exception;
	
	/**
	 * 删除导入的加班信息申请(delete overtime apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delOvertimeApplyImport(Object object) throws Exception;
	
	/**
	 * 删除临时表中所有导入的加班信息申请(delete overtime apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean cancelOvertimeApplyImport(Object object) throws Exception;
	
	/**
	 * 取消已审核通过的加班申请( cancel overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean cancelOvertimeApply(Object object) throws Exception;
	
	/**
	 * 批量加班申请--已申请的加班信息列表(get overtime apply list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtApplyInfoList(Object object);
	
	/**
	 * 批量加班申请--已申请的加班信息列表(get overtime apply list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getOtApplyInfoList(Object object, int currentPage, int pageSize);
	
	/**
	 * 批量加班申请--已申请的加班信息列表总数(get overtime apply list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtApplyInfoListCnt(Object obj) throws Exception;
	
	/**
	 * 加班申请时，根据页面参数获取对应的加班转换类型
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap getOtTypeCodeByTurn(Object obj);
	
	public String getIfZhuiSu(Object obj);
	
	/**
	 * 加班申请时，根据页面参数切换申请日期，和夜班加班
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap getOtTypeNight(Object obj);
	
	/**
	 * 取申请加班的人事政策
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap getOtTypeCodeRemark(Object obj);
	
	/**
	 * 加班申请时，根据页面参数获取加班批次号
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getOtLotNo(Object obj);
	
	/**
	 * 导入加班申请--查看信息列表(get overtime import list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtImportInfoList(Object object);
	
	/**
	 * 导入加班申请--查看信息列表(get overtime import list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getOtImportInfoList(Object object, int currentPage, int pageSize);
	
	/**
	 * 导入加班申请--查看信息列表总数(get overtime import list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtImportInfoListCnt(Object obj) throws Exception;
	
	/**
	 * 导入加班申请--查看信息列表总数(get overtime import list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtImportInfoListErrorCnt(Object obj) throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	 * 查询人员申请的人员列表(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonListOt(Object object);

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

	/**
	 * 批量添加休假申请(batch add leave apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addLeaveApplyInBatch(List list) throws Exception;

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
	 * 当天的加班申请是否已经有过--申请类型为时间长度
	 * 
	 * @param obj
	 * @return
	 */
	public List otApplyExsitByApplyDate(Object obj) throws Exception;

	/**
	 * 导入的加班申请是否已经有过--申请类型为时间长度L
	 * 
	 * @param obj
	 * @return
	 */
	public List otImportExsitByApplyDate(Object obj) throws Exception;
	
	/**
	 * 获得该员工已申请的加班申请时间(get person exist overtime apply date)
	 * 
	 * @param obj
	 * @return
	 */
	public List getExistOtApplyDate(Object obj) throws Exception;
	
	/**
	 * 社外申请加班验证是否出差(get person exist overtime apply date)
	 * 
	 * @param obj
	 * @return
	 */
	public List otApplyCheckOut(Object obj) throws Exception;
	/**
	 * 社外申请加班验证是否出差(get person exist overtime apply date)
	 * 
	 * @param obj
	 * @return
	 */
	public List otApplyCheckOutCh(Object obj) throws Exception;

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
	 * 取得加班申请导入临时表里的其它加班申请(get overtime apply with import other)
	 * 
	 * @param obj
	 * @return
	 */
	public List getOtApplyDateWithImportOther(Object obj) throws Exception;
	/**
	 * 取得加班申请导入临时表里的其它加班申请(get overtime apply with import other)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOtApplyDateWithImportOtherWQ(LinkedHashMap paramMap) throws Exception;
	@SuppressWarnings("unchecked")
	public String getOtApplyDateWithImportOtherAll(LinkedHashMap paramMap) throws Exception;
	@SuppressWarnings("unchecked")
	public String getOtApplyConfirmCpny(LinkedHashMap paramMap) throws Exception;
	/**
	 * 获得日历类型(get calendar type)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getDataType(Object object) throws Exception;
	
	/**
	 * 获得日历类型(get calendar type)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getDataTypeCheck(Object object) throws Exception;
	
	/**
	 *LGEND法人 获得加班判断(get calendar type)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public String getDataTypeReturn(Object object) throws Exception;
	
	/**
	 *LGETR法人 获得加班判断(get calendar type)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public String getDataTypeReturnFront(Object object) throws Exception;
	
	public int getotlengthlgend(Object object) throws Exception;
	
	public  int getotlengthtotal(Object object)throws 	Exception;
	
	/**
	 * 获得日历类型(get calendar type)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getDataTypeL(Object object) throws Exception;
	public int getDataTypeWQ(Object object) throws Exception;
	
	
	/**
	 * 获得员工的加班限制标志(get ot apply limit flag)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getOtApplyLimitFlag(Object object) throws Exception;
	
	
	
	/**
	 * 获得员工考勤期间内的加班时间
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getOtApplyOT36(Object object) throws Exception;

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
	
	public String getLeaveTypeCode(Object obj);
	
	public String getOtTypeCode(Object obj);
	

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
	
	/**
	 * 获取裁决者等级长度
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author llwei liuliwei@ait.net.cn 
	* @date 2014-07-10  下午19:14:23 
	* @version V1.0
	 */
	public String getLeaveAffirmLevel(Object obj) throws Exception;
	
	
	/**
	 * 根据EMPID 或部门ID取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getAffirmorListByPersonIdNew(Object obj) throws Exception;
	
	/**
	 * 根据EMPID取得特殊审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getSpecialAffirmorListByPersonIdNew(Object obj) throws Exception;
	
	/**
	 * 根据是否有针对部门取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getAffirmorListByDeptNoNew(Object obj) throws Exception;
	
	/**
	 * 根据是否有针对部门取得特殊审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getSpecialAffirmorListByDeptNoNew(Object obj) throws Exception;
	
	/**
	 * 根据parent_code获取子code列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCodeList(Object obj) throws Exception;
	
	/**
	 * 员工信息查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	public Object getPersonalInfoForApplyParam(Object object) ;
	/**
	 * 员工班次开始结束
	 * @param obj
	 * @return object
	 */
	public Object getPersonalInfoForApplyShift(Object object) ;
	
	/**
	 * 员工信息查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	public Object getShiftEndTime(Object object);
	
	public int isHasPersonAffirm(Object obj) throws Exception;
	
	
	public int isHasPersonAffirmSpecial(Object obj) throws Exception;
	
	
	public int isHasPersonAffirmSpecialPerson(Object obj) throws Exception;

	/** 
	* @Title: getDataTypeForPeople 
	* @Description: TODO 查询员工个人排班日期类型 
	* @param @param paramMap
	* @param @return    
	* @return List    
	* @throws 20141214 孙鹏修改
	*/
	public List getDataTypeForPeople(Map paramMap);
	public List getDataTypeForPeopleW(Map paramMap);

	public List getAffirmorListByDept(Object obj) throws Exception;
	
	public String getEmpTypeCode(Object object) throws Exception;
	
	/**
	 * 批量申请审批者 修改
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void modifyAffirmorForBatch(Map obj) throws Exception;
	
	/**
	 * 根据cpnyId获取加班开始、结束时间显示
	 * @param obj
	 * @return object
	 */
	public List getOtTimeByCpnyId(Map paramMap) ;
	
	/**
	 * 获取审批线sql
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getAffirmorlistByPersonIdStr(Object obj) throws Exception;
	
	/**
	 * 根据审批线sql取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorlistByPersonIdList(Object obj) throws Exception;
	
	/**
	 * 获取当月漏刷卡次数
	 * @param request
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public int arGetMacApplyCnt(Object obj) throws Exception ;
	
	/**
	 * 获取旷工导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssAbsenteeismTempList(Object object);
	
	/**
	 * 获取旷工导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssAbsenteeismTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取旷工导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssAbsenteeismTempCnt(Object object);
	
	/**
	 * 获取出错的旷工导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssAbsenteeismTempErrorCnt(Object object);
	
	/**
	 * 删除未审核：in/out旷工信息申请(delete ar mac record apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delArCwaApplyInfo(List list) throws Exception ;
	
	/**
	 * 批量提交旷工申请
	 * 
	 * @param obj
	 * @return
	 */
	public int submitArCwaApplyInBatch(List list) throws Exception;
	
	/**
	 * 获取旷工申请批量信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEssArCwaList(Object object);
	
	/**
	 * 获取旷工申请批量信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArCwaList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取旷工申请批量数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArCwaCnt(Object object);

	public int getExceptionInt(Object object);

	public LinkedHashMap getOtApplyWorkTime(LinkedHashMap map);
	public LinkedHashMap getOtApplyWorkTimeU(LinkedHashMap map);

	public void updatSubmitLeaveApplyInBatchForDeletePrepare(LinkedHashMap paramMap) throws Exception;

	/**
	 * 批量添加加班申请(batch add leave apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addOtApplyInArDetailBatch(LinkedHashMap paramMap) throws Exception;
	public void addOtApplyInArDetailBatchAdjust(LinkedHashMap paramMap) throws Exception;

	public void insertSubmitLeaveApplyInBatch(LinkedHashMap paramMap)throws Exception;

	public void updatSubmitArDetailBatchApply(LinkedHashMap paramMap) throws Exception;
	public void updatSubmitArDetailBatchApplyAdjust(LinkedHashMap paramMap) throws Exception;
	public void updatSubmitArDetailBatchApplySST(LinkedHashMap paramMap) throws Exception;
	public void updatSubmitArDetailBatchApplyForChuShi(LinkedHashMap paramMap) throws Exception;
	public void updatSubmitArDetailBatchApplyAdjustForChuShi(LinkedHashMap paramMap) throws Exception;
	public void updatSubmitArDetailBatchApplyForChuShiSST(LinkedHashMap paramMap) throws Exception;
	public void updatArDetailBatchApplyForSHIFT(LinkedHashMap paramMap) throws Exception;
	public void updatArDetailBatchApplyForDATETYPE(LinkedHashMap paramMap) throws Exception;
	public void updatArDetailBatchApplyForGROUP(LinkedHashMap paramMap) throws Exception;
	public void updatSubmitArDetailBatchApplyForFirstAdd(LinkedHashMap paramMap) throws Exception;
	public void updatSubmitArDetailBatchApplyForFirstAddAdjust(LinkedHashMap paramMap) throws Exception;
	public void updatSubmitArDetailBatchApplyForFirstAddSST(LinkedHashMap paramMap) throws Exception;

	public void updateOtApplyInArDetailBatchApply(LinkedHashMap paramMap)throws Exception;
	public void updateAdjustApplyInArDetailBatchApply(LinkedHashMap paramMap)throws Exception;

	public String getGradeYn(LinkedHashMap map);

	public List getDeptOtInfoList(Object object);
	public LinkedHashMap getOldWorkTimeInfoByPkNO(LinkedHashMap map);

//	public int saveArOvertimeManagent(Object object,AdminBean admin);
	/**
	 * 内务加班查询页面(get Leave Affirm Info List)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCoordOtInfoList(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public List getCoordAdjustInfoList(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public List getOverTimeLimtList(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public List viewPersonOverTimeLimtList(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public List getOverTimeLimtShenPiList(Object object) throws Exception;

	public int approveOtLimitBatch(List list) throws Exception;
	public int approveOtLimitBatchHUB(List list) throws Exception;

	@SuppressWarnings("unchecked")
	public Object getShiftNoForOt(LinkedHashMap shiftMap);

	public LinkedHashMap getOtApplyShiftTime(LinkedHashMap worHashMap);

	public LinkedHashMap getOtArDetailForPkNo(LinkedHashMap paramMap);
	public List getOtArDetailForMoreOt(LinkedHashMap paramMap);

	public LinkedHashMap getWorkTimeFirstLast(LinkedHashMap dataMap);

	/**
	 * 加班验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOtCheckSST(LinkedHashMap worHashMap);

	/**
	 * 加班事后确认验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOtCheckSST2(LinkedHashMap worHashMap);
	
	//查找明细表中加班的数据
	@SuppressWarnings("unchecked")
	public LinkedHashMap getOtArDetailForPkNoSST(LinkedHashMap worHashMap);

	/**
	 * SST加班申请
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addOvertimeApplySST(List list) throws Exception;
	
	/**
	 * SST查询加班申请决裁信息-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSSTOtAffirmInfoList(Object obj) ;

	/**
	 * 审批箱查询
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewApprovalInfo(Object obj,String target);


	/**
	 * 审批处理
	 * 
	 * @param
	 * @return by:wangqiang
	 */
	@SuppressWarnings("unchecked")
	public String executePro(Object object,String target) throws Exception;

	/**
	 * SST 事后加班申请
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void modifyOvertimeApplySST(Map map) throws Exception;
	public int viewModifyApprovalInfo(Object object,String target)throws Exception ;

	public List getOtLimitDataImportResultList(Object object);

	public String insertOTLimit(Map<String, Object> paramMap);

	/**
	 * TSTO内务倒休验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCoordAdjustCheckTSTO(LinkedHashMap worHashMap) ;

	/**
	 * 判断明天是否休息日
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int isWeekend() throws Exception;
	
	/**
	 * 保存加班
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int saveOtApplyAffirmForBatch(Map map) throws Exception;
	/**
	 * 保存加班(可添加删除修改者)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int saveOtApplyByAnyApproverForBatch(Map map) throws Exception;
	public int saveAdjustHolidayApplyForBatch(Map map) throws Exception;
	@SuppressWarnings("unchecked")
	public int delOtApplyAffirmForBatch(List list) throws Exception;
	@SuppressWarnings("unchecked")
	public int delAdjustHolidayApplyForBatch(List list) throws Exception;
	public void addOtApplyAffirm(Map map) throws Exception;
	public void addAdjustHolidayApply(Map map) throws Exception;
	public List getValidateInfo(Map map) throws Exception;
	public List getOtTempList(LinkedHashMap paramMap) throws Exception;
	public int getOtTempErrorCnt(Object object) throws Exception;
	public int getOtTempCnt(Object object) throws Exception;
	public Map getOtLength(Object object) throws Exception;
	public Map getOtShiftTime(Object object) throws Exception;
	
	public int saveOTApplyInfoForBatchHAE(Map map) throws Exception;
	public int delOTExForBatchInfoListHAE(Object obj) throws Exception;
	
	public void updateDeductYn(Object object);
	
}
