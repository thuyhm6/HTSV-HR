package com.ait.ess.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

/**
 * 信息申请
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: InfoApplySer.java
 * @Description:
 * @Create date: Feb 17, 2012 2:02:09 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 17, 2012 2:02:09 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
public interface InfoApplyLeaveSer { 
	/**
	 * 员工信息查询(Employee Information Management)
	 * @param request
	 * @return object
	 */
	public Object getPersonalInfo(HttpServletRequest request);
	
	/**
	 * 加班申请考勤区间列表(search ot apply ar_month info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveApplyArMonthList(HttpServletRequest request) throws Exception;
	
	/**
	 * 休假申请决裁信息查询(get Leave Affirm Info List)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveAffirmInfoList(HttpServletRequest request) throws Exception;
	/**
	 * 个人考勤申请明细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalAttInfoDetailList(HttpServletRequest request) throws Exception;
	/**
	 * 异常考勤申请明细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalAttInfoDetailList1(HttpServletRequest request,ModelMap modelMap) throws Exception;
	/**
	 * 异常考勤申请明细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewCheckAttencetanceExForBatchList(HttpServletRequest request,ModelMap modelMap) throws Exception;
	/**
	 * 休假信息查询页面(get Leave Affirm Info List)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCoordLeaveInfoList(HttpServletRequest request) throws Exception;
	/**
	 * 部门员工休假查询(get Leave Affirm Info List)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDeptLeaveInfoList(HttpServletRequest request) throws Exception;

	/**
	 * 休假申请决裁信息总数(get Leave Affirm Info List Cnt)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getLeaveAffirmInfoListCnt(HttpServletRequest request) throws Exception;
	
	
	/**
	 * 取得审批人列表:1.先取特殊设置人员的决裁者;2.再取特殊设置部门的决裁者;3.最后按流程取决裁者 (get approver list:1
	 * get approver by special-person's-approver setup.2 get approver by
	 * special-department's-approver setup.3 get approver by approve-flow)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getAffirmorList(HttpServletRequest request) throws Exception;
	
	/**
	 * 获取班次 ,加班类型(batch add leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getDateByPersonIdAndCpny(HttpServletRequest request) throws Exception;
	
	/**
	 * 根据加班申请NO查询此次（个人/批量）申请的所有人(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getApplyorByApplyNoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 根据加班申请NO决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorByApplyNoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 加班申请check信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCheckorByApplyNoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delLeaveApplyInBatch(HttpServletRequest request)throws Exception;
	
	/**
	 * 批量删除考勤
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delAttendanceApplyInfoForBatch(HttpServletRequest request)throws Exception;
	
	public int delAttendanceExInBatchForBatch(HttpServletRequest request)throws Exception;
	
	/**
	 * 批量保存考勤
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveAttendanceApplyInfoForBatch(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 批量保存考勤(可添加删除审批者)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveAttApplyInfoByAnyApproverForBatch(HttpServletRequest request) throws Exception;
	
	public int saveAttenanceExBatchInfo(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 批量添加考勤
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addAttendanceApplyInfoForBatch(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 批量删除异常考勤申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delApplyLeaveInfo(HttpServletRequest request)throws Exception;
	
	
	/**
	 * 批量删除异常考勤申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delAttencetanceEx(HttpServletRequest request)throws Exception;
	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delLeaveApply(HttpServletRequest request)throws Exception;
	
	/**
	 * 取消已审核通过的加班申请( cancel overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean cancelLeaveApply(HttpServletRequest request)throws Exception;
	
	/**
	 * 申请销假( cancel leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void addXiaojiaLeaveApply(HttpServletRequest request)throws Exception;
	
	/**
	 * 显示个人信息申请修改页面（View personal information apply）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getPersonInfo(HttpServletRequest request) throws Exception;

	/**
	 * 查询公司用于扣除时间的LIST(search ot deduct time list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtDeductTimeList(HttpServletRequest request) throws Exception;
	
	/**
	 * 人员查询(search person list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonList(HttpServletRequest request) throws Exception;
	/**
	 * (search person list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getAttendanceInformation(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public Object getOTSSTInformation(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public Object getAJTSTOInformation(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public Object getOTTSTOInformation(HttpServletRequest request) throws Exception;

	/**
	 * 批量加班申请的人员列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getPersonListCnt(HttpServletRequest request) throws Exception;

	/**
	 * 添加个人信息申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean addPersonalInfoApply(HttpServletRequest request)
			throws Exception;

	/**
	 * 添加休假申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addLeaveApply(HttpServletRequest request) throws Exception;

//	/**
//	 * 添加休假申请
//	 * 
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean addLeaveApply(HttpServletRequest request) throws Exception;

	/**
	 * 添加出差申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean addEvectionApply(HttpServletRequest request)
			throws Exception;

	/**
	 * 添加外出申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean addEgressionApply(HttpServletRequest request)
			throws Exception;

	/**
	 * 获得员工的班次
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getEmpShift(HttpServletRequest request) throws Exception;

	/**
	 * 计算剩余年假(Calculate the remaining annual leave )
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public List retrieveVacationEmpREST(HttpServletRequest request)
			throws Exception;

	/**
	 * 计算剩余调休(Calculate the remaining adjust rest )
	 * 
	 * @param request
	 * @param modelMap
	 * @throws Exception
	 */
	public String getSurplusAdjustRest(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 计算剩余调休 2013-06-20 lufeng 新增(Calculate the remaining adjust rest )
	 * 
	 * @param request
	 * @param modelMap
	 * @throws Exception
	 */
	public String getAdjustRestNew(HttpServletRequest request)
			throws Exception;

	

	/**
	 * 添加加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addBatchOvertimeApply(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量添加休假申请(batch add leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addBatchLeaveApply(HttpServletRequest request) throws Exception;

	/**
	 * 批量添加出差申请(batch add evection apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addBatchEvectionApply(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量添加外出申请(batch add egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addBatchEgressionApply(HttpServletRequest request)
			throws Exception;
	/**
	 * 计算去年剩余年假(Calculate the remaining annual leave )
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public Integer retrieveVacationEmpRESTQN(HttpServletRequest request) throws Exception;

	public String getspouseBirth(HttpServletRequest request);

	/**
	 * 根据员工的部门所在地查看 休假基准的说明
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-3 下午4:15:53 
	* @version V1.0
	 */
	public String getviewVacationStandard(HttpServletRequest request);

	/**
	 * 取得法规政策
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public String getZhengce(HttpServletRequest request);
	
	/**
	 * 取申请时长
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public String getShenqingshichang(HttpServletRequest request);
	/**
	 * 获取个人倒休或者年假使用情况
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public LinkedHashMap getLeaveDispalydaoxiuOrnianjMap(HttpServletRequest request) throws Exception;
	/**
	 * 
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public String getChechedAffrim(HttpServletRequest request);
	/**
	 * 
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public String getYesBefAffrimNo(HttpServletRequest request);
	/**
	 * 
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getApplyCountYN(HttpServletRequest request);
	/**
	 * 
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getApplyLOCKYN(HttpServletRequest request);
	/**
	 * 
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getApplyOTLOCKYN(HttpServletRequest request);
	/**
	 * 
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getChechedSex(HttpServletRequest request);
	/**
	 * 
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getTrainLocalName(HttpServletRequest request);
	/**
	 * 
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public String getChechedItemName(HttpServletRequest request);

	/**
	 * 加班申请决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getLeaveInfoByLeave(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public Object getOtApplyPersonal(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public Object getOtApplyPersonalXiao(HttpServletRequest request) throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public Object getOtApplyPersonalIfDisplay(HttpServletRequest request) throws Exception;


	/**
	 * 取得审批人列表:1.先取特殊设置人员的决裁者;2.再取特殊设置部门的决裁者;3.最后按流程取决裁者 (get approver list:1
	 * get approver by special-person's-approver setup.2 get approver by
	 * special-department's-approver setup.3 get approver by approve-flow)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getAffirmorListByLeave(HttpServletRequest request) throws Exception;
	
	/**
	 * 休假申请决裁信息查询(get Leave Affirm Batch Info List)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveAffirmBatchInfoList(HttpServletRequest request) throws Exception;

	/**
	 * 休假申请决裁信息总数(get Leave Affirm Batch Info List Cnt)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getLeaveAffirmBatchInfoListCnt(HttpServletRequest request) throws Exception;

	/**
	 * 休假申请决裁信息查询(临时表)(get Leave Affirm Batch temp Info List)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveAffirmBatchTempInfoList(HttpServletRequest request) throws Exception;

	/**
	 * 休假申请决裁信息总数(临时)(get Leave Affirm Batch Info List Cnt)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getLeaveAffirmBatchTempInfoListCnt(HttpServletRequest request) throws Exception;

	/**
	 * 删除休假临时表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delTempLeaveApply(HttpServletRequest request)throws Exception;

	/**
	 * 添加加班申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addBatchApplyLeave(HttpServletRequest request) throws Exception;

	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
/*	public int delLeaveApplyInBatchForBatch(HttpServletRequest request) throws Exception;*/
	
	/**
	 * 获取休假导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssLeaveTempList(HttpServletRequest request) ;
	
	/**
	 * 获取休假导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssLeaveTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 休假批量申请excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelEssLeaveEmpData(HttpServletRequest request);
	
	/**
	 * 获取员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfo(HttpServletRequest request) throws Exception;
	
	/**
	 * 休假批量申请决裁信息总数(get Leave Affirm Info List Cnt)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getBatchLeaveAffirmInfoListCnt(HttpServletRequest request) throws Exception;
	
	/**
	 * 休假批量申请决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBatchLeaveAffirmInfoList(HttpServletRequest request) throws Exception;
	/**
	 * 查询多天的假(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBatchLeaveAffirmMoreDayInfoList(HttpServletRequest request) throws Exception;
	/**
	 * 查询NULL数据(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getNullBatchLeaveAffirmInfoList(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public List getIdCardExpire(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public List viewAddAttendanceApplyInfoForBatch(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public List getFileList(Map map) throws Exception;
	@SuppressWarnings("unchecked")
	public List getAttendanceExForBatchInfoList(HttpServletRequest request,ModelMap modelMap) throws Exception;
	@SuppressWarnings("unchecked")
	public List getNullBatchOTSSTAffirmInfoList(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public List getNullBatchAJTSTOAffirmInfoList(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public List getNullBatchOTTSTOAffirmInfoList(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public List viewNullBatchOTTSTOAffirmInfoList(HttpServletRequest request) throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public List viewAddOTApplyInfoForBatchHAE(HttpServletRequest request) throws Exception;
	/**
	 * 休假批量申请详细信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getLeaveBatchAffirmInfoCnt(HttpServletRequest request);
	/**
	 * 加班批量申请详细信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wangqiang@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getOtBatchAffirmInfoCnt(HttpServletRequest request);
	
	/**
	 * 休假批量申请详细信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveBatchAffirmInfoList(HttpServletRequest request) throws Exception ;
	
	/**
	 * 加班批量申请详细信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtBatchAffirmInfoList(HttpServletRequest request) throws Exception ;
	
	/**
	 * 获取员工性别
	 * @param request
	 * @return
	 */
	public String getSex(HttpServletRequest request);
	/**
	 * 判断是否为追溯休假 overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	public boolean isZhuisuLeave(HttpServletRequest request) throws Exception;
	
	/**
	 * 员工班次结束时间查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	public String getShiftTime(HttpServletRequest request);
	
	public List getEssFileList(Object paramMap) throws Exception;
	
	/**
	 * 申请销假( cancel leave apply)不走审批线
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addXiaojiaLeaveApplyNoAffirm(HttpServletRequest request)throws Exception ;
	
	/**
	 * leave发令
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addLeaveAssigment(HttpServletRequest request) throws Exception;
	
	/**
	 * 加班申请决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveAffirmInfoBatchList(HttpServletRequest request) throws Exception ;
	@SuppressWarnings("unchecked")
	public List viewAdjustRecords(HttpServletRequest request) throws Exception ;
	
	/**
	 * 休假申请决裁信息总数(get Leave Affirm Info List Cnt)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getLeaveAffirmInfoBatchListCnt(HttpServletRequest request) throws Exception ;
	
	/**
	 * 申请销假( cancel leave apply)不走审批线
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void cancelLeaveApplyNoAffirm(HttpServletRequest request)throws Exception ;

	public void deleteAllDataForAdd(HttpServletRequest request) throws Exception;

	/**
	 * 添加考勤申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addLeaveApplySST(HttpServletRequest request) throws Exception;
	
	/**
	 * 天津年假信息申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addVacInfo(HttpServletRequest request) throws Exception;
	
	/**
	 * 获取SST员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfoSST (HttpServletRequest request) throws Exception;

	/**
	 * 获取SST员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfoSSTForDisplay (Object obj) throws Exception ;
	
	/**
	 * 明细excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String valImportExcelAttendanceData(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getAttendanceTempList(HttpServletRequest request) throws Exception ;
	
	public int getAttendanceTempCnt(HttpServletRequest request, String errorFlag)  throws Exception ;
	public String submitImportExcelAttendanceData(HttpServletRequest request) throws Exception ;

	/**
	 * 个人考勤明细查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List viewAttendancePersonalInfoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 获得人员列表(view BatchApplyEmp List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	public List getPersonListView(HttpServletRequest request) throws Exception;
	
	public int getPersonListViewCnt(HttpServletRequest request) throws Exception;

	public int getAttendanceExInfoListCnt(HttpServletRequest request, ModelMap modelMap)
			throws Exception;
	
	/**
	 * 批量添加考勤申请(view BatchApplyEmp List)
	 * @param request
	 * @return int
	 * @throws 
	 */
	public int addAttendanceApplyInfoForBatchHAE(HttpServletRequest request) throws Exception;
	
	public List viewAddAttendanceApplyInfoForBatchHAE(HttpServletRequest request) throws Exception;
	
	public List viewApplyAttenanceBatchInfoHAEList(HttpServletRequest request) throws Exception;
	
	public Object getApplyAttenanceBatchInfoHAEDetail(HttpServletRequest request) throws Exception;
	
	/**
	 * 生产值批量保存考勤申请(save BatchApplyEmp List)
	 * @param request
	 * @return int
	 * @throws 
	 */
	public int saveAttendanceApplyInfoForBatchHAE(HttpServletRequest request) throws Exception;
	
	/**
	 * 批量添加加班申请(view BatchApplyEmp List)
	 * @param request
	 * @return int
	 * @throws 
	 */
	public int addOTApplyInfoForBatchHAE(HttpServletRequest request) throws Exception;
	
	public List viewApplyOTBatchInfoHAEList(HttpServletRequest request) throws Exception;
	
	public Object getApplyOTBatchInfoHAEDetail(HttpServletRequest request) throws Exception;
	
	public void delAttendanceExForBatchInfoListHAE(HttpServletRequest request) throws Exception;
	
	public void delOTApplyInfoForBatchInfoListHAE(HttpServletRequest request) throws Exception;
}