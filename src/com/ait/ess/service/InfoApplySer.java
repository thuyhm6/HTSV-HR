package com.ait.ess.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

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
public interface InfoApplySer {
	/**
	 * 员工信息查询(Employee Information Management)
	 * @param request
	 * @return object
	 */
	public Object getPersonalInfo(HttpServletRequest request);
	
	/**
	 * 根据person_id查询该员工上上月最后一天日期(get last day of last last month)
	 * @param request
	 * @return object
	 */
	public String getLLastMonthLastDay(HttpServletRequest request);
	
	/**
	 * 加班申请考勤区间列表(search ot apply ar_month info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtApplyArMonthList(HttpServletRequest request) throws Exception;
	
	/**
	 * 加班申请决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmInfoList(HttpServletRequest request) throws Exception;
	/**
	 * myhome 个人加班申请明细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalOtInfoDetailList(HttpServletRequest request) throws Exception;
	/**
	 * 个人--加班搜索(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonOtApplyInfoList(HttpServletRequest request) throws Exception;
	/**
	 * 批量加班申请决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmInfoListBatch(HttpServletRequest request) throws Exception;
	/**
	 * 批量调休处理(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewAdjustLeaveTSTOBatchList(HttpServletRequest request) throws Exception;
	
	/**
	 * 加班原因(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmInfoListWhy(HttpServletRequest request) throws Exception;


	/**
	 * 加班申请决裁信息总数(search ot info list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtAffirmInfoListCnt(HttpServletRequest request) throws Exception;
	
	/**
	 * 加班申请决裁信息总数(search ot info list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtAffirmInfoListCntBatch(HttpServletRequest request) throws Exception;
	
	
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
	 * 获取日期类型(get the date type code)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getDateTypeByDateAndCpny(HttpServletRequest request) throws Exception;
	
	public Map getDefaultStartEndTime(HttpServletRequest request) throws Exception;
	public Map getOtLimit(HttpServletRequest request) throws Exception;
	
	public List getChangeOtType(HttpServletRequest request) throws Exception;
	
	/**
	 * 获取员工日期类型(get the date type code)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getDataType(HttpServletRequest request) throws Exception;
	/**
	 * 获取员工日期类型(get the date type code)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getDataTypeW(HttpServletRequest request) throws Exception;
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getDataTypeTsto(HttpServletRequest request) throws Exception;
	
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
	 * 根据加班申请NO决裁信息查询(search ot info list)小页面用
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorByApplyNoListXiao(HttpServletRequest request) throws Exception;
	
	/**
	 * 根据加班申请NO决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorListByApplyNo(LinkedHashMap paramMap) throws Exception;
	
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
	 * 加班申请check信息查询(search ot info list)小页面用 决裁页面查看添加的check信息，能查看添加的所有check人
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCheckorByApplyNoListXiao(HttpServletRequest request) throws Exception;
	
	/**
	 * 加班申请check信息查询(search ot info list)  进行check的页面用，只查个人的
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCheckorByApplyNoListXiaoPerson(HttpServletRequest request) throws Exception;
	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delOvertimeApplyInBatch(HttpServletRequest request)throws Exception;
	/**
	 * 批量取消加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delOvertimeApplyInBatch2(HttpServletRequest request)throws Exception;
	
	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delOvertimeApply(HttpServletRequest request)throws Exception;
	
	/**
	 * 删除导入的加班信息申请(delete overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delOvertimeApplyImport(HttpServletRequest request)throws Exception;
	
	/**
	 * 删除临时表中所有导入的加班信息申请(delete overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelOvertimeApplyImport(HttpServletRequest request)throws Exception;
	
	/**
	 * 取消已审核通过的加班申请( cancel overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean cancelOvertimeApply(HttpServletRequest request)throws Exception;
	
	/**
	 * 批量加班申请--已申请的加班信息列表(get overtime apply list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtApplyInfoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 批量加班申请--已申请的加班信息列表总数(get overtime apply list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtApplyInfoListCnt(HttpServletRequest request) throws Exception;
	
	/**
	 * 导入加班申请--查看信息列表(get overtime import list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtImportInfoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 导入加班申请--查看信息列表总数(get overtime import list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtImportInfoListCnt(HttpServletRequest request,String cntType) throws Exception;
	
	/**
	 * 添加加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addBatchOtApply(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 添加导入的加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addImportOtApply(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 修改加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int updateBatchOtApply(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 添加导入的加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addImportOtApply2(HttpServletRequest request)
			throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	 * 人员查询(search person list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonListOt(HttpServletRequest request) throws Exception;

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
	 * 添加加班申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addOvertimeApply(HttpServletRequest request) throws Exception;
	
	
	public boolean isZhuisuOt(HttpServletRequest request) throws Exception;
	/**
	 * 添加加班申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String callInsertBatchOt(HttpServletRequest request) throws Exception;
	
	/**
	 * 修改加班申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int updateOvertimeApply(HttpServletRequest request) throws Exception;
	
	/**
	 * 取申请时长
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public String getOtApplyLength(HttpServletRequest request);
	/**
	 * 取申请时长
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getOtApplyLengthP(HttpServletRequest request);
	/**
	 * 取班次的开始结束
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getOtFIRSTLASTTimeForChange(HttpServletRequest request);
	/**
	 * 取申请时长
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getOtApplyLengthP2(HttpServletRequest request);
	/**
	 * 取工作形态
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getOtApplyWorkTime(HttpServletRequest request);
	
	/**
	 * 取本考勤月申请总时长（包括本次申请）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public double getOtApplyLengthPZong(HttpServletRequest request);
	
	/**
	 * 取申请加班的人事政策
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public String getOtTypeCodeRemark(HttpServletRequest request);

	/**
	 * 添加休假申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean addLeaveApply(HttpServletRequest request) throws Exception;

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
	 * 取得审批人列表
	 * 
	 * applyTypeNo(信息申请类型:如休假申请、加班申请等)
	 * personId(申请人的personId)
	 * applyTypeCode(申请的具体类型，例如休假申请中的病假、事假;加班申请中的平日加班、周末加班等)
	 * applyLength(申请具体类型的长度，例如病假2天、平日加班3个小时)
	 * 
	 * 如果  applyTypeCode 、applyLength 其中之一为空的话,将取全部的决裁者
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getAffirmorListByString(String applyTypeNo , String personId , String applyTypeCode , String applyLength ,String language )throws Exception;
	
	public String getLeaveTypeCode(HttpServletRequest request)throws Exception;

	public String getOtTypeCode(HttpServletRequest request)throws Exception;
	/**
	 * 根据parent_code获取子code列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getCodeList(HttpServletRequest request);

	public String getOtApplyLengthWq(HttpServletRequest request);
	/**
	 * 获取加班开始时间及增长间隔
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getApplyParam(HttpServletRequest request) throws Exception;
	/**
	 * 员工班次结束时间查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	public String getShiftEndTime(HttpServletRequest request);
	
	public List getAnnualAffirmorList(HttpServletRequest request) throws Exception ;

	public List getDataTypeForPeople(HttpServletRequest request) throws Exception;
	
	public List getDataTypeForPeopleW(HttpServletRequest request) throws Exception;

	public List getAffirmorListByDept(String applyTypeNo, String deptNo , String personId , String applyTypeCode , String applyLength )throws Exception;
	
	/**
	 * 判断
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getPersonIdForAffirmList(String applyPersonId, String adminId, String cpnyId) ;
	
	/**
	 * 批量申请审批者 修改
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int modifyAffirmorForBatch(HttpServletRequest request) throws Exception;
	
	/**
	 * 判断 促销员
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getApplyTypeCodeForAffirmList(String applyPersonId, String cpnyId, String applyTypeCode);
	
	/**
	 * 加班申请页面时间显示  取时间
	 * @param obj
	 * @return object
	 */
	public List getOtTimeByCpnyId(HttpServletRequest request,String timeFlag);
	
	/**
	 * 取得审批人列表
	 * 
	 * applyTypeNo(信息申请类型:如休假申请、加班申请等)
	 * personId(申请人的personId)
	 * applyTypeCode(申请的具体类型，例如休假申请中的病假、事假;加班申请中的平日加班、周末加班等)
	 * applyLength(申请具体类型的长度，例如病假2天、平日加班3个小时)
	 * 
	 * 如果  applyTypeCode 、applyLength 其中之一为空的话,将取全部的决裁者
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorListByString2(String applyTypeNo , String personId , String applyTypeCode , String applyLength ) throws Exception;
	
	/**
	 * 获取当月漏刷卡次数
	 * @param request
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public int arGetMacApplyCnt(HttpServletRequest request);
	

	/**
	 * 获取旷工导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssAbsenteeismTempList(HttpServletRequest request) ;
	
	/**
	 * 获取旷工导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssAbsenteeismTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 休假旷工申请excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelEssAbsenteeismEmpData(HttpServletRequest request);
	
	/**
	 * 批量删除旷工申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delArCwaApplyInBatchForBatch(HttpServletRequest request)
			throws Exception ;
	

	/**
	 * 旷工批量申请详细信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getArCwaBatchAffirmInfoList(HttpServletRequest request) throws Exception ;

	/**
	 * 旷工批量申请详细信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getArCwaBatchAffirmInfoCnt(HttpServletRequest request);


	public int getExceptionInt(HttpServletRequest request) throws Exception;

	public void updateException(HttpServletRequest request);

	public Map getPersonIdShift(HttpServletRequest request);
	public String getPersonIdWorkTime(HttpServletRequest request);

	public String getGradeYn(HttpServletRequest request);

	/**
	 * 部门员工加班查询(get Leave Affirm Info List)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDeptOtInfoList(HttpServletRequest request) throws Exception;

		/**
	 * 加班信息查询页面(get Leave Affirm Info List)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCoordOtInfoList(HttpServletRequest request) throws Exception;
	/**
	 * 倒休信息查询页面(get Leave Affirm Info List)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCoordAdjustInfoList(HttpServletRequest request) throws Exception;
	/**
	 * 加班上限页面(get Leave Affirm Info List)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOverTimeLimtList(HttpServletRequest request) throws Exception;
	/**
	 * 个人加班上限控制(get Leave Affirm Info List)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewPersonOverTimeLimtList(HttpServletRequest request) throws Exception;
	/**
	 * 加班上限审批页面(get Leave Affirm Info List)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOverTimeLimtShenPiList(HttpServletRequest request) throws Exception;

	public int approveOtLimitBatch(HttpServletRequest request) throws Exception;
	public int approveOtLimitBatchHUB(HttpServletRequest request) throws Exception;
	public int approveOtLimitBatchShenPi(HttpServletRequest request) throws Exception;
	 
	/**
	 * SST加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addSSTOvertimeApply(HttpServletRequest request) throws Exception ;
	
	/**
	 * SST加班申请决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSSTOtAffirmInfoList(HttpServletRequest request) throws Exception;

	/**
	 * 审批箱查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewApprovalInfo(HttpServletRequest request,String target) throws Exception;

	public String executePro(HttpServletRequest request,String target);

	/**
	 * 获取上次申请时的审批线
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorListByHistory(HttpServletRequest request,String applyTypeNo) throws Exception ;

	/**
	 * SST事后确认
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int modifySSTOvertimeApply(HttpServletRequest request) throws Exception ;

	public String executeProBatch(HttpServletRequest request,String target);
	
	public String executeProBatchByBatchNo(HttpServletRequest request,String target);

	/**
	 * 审批箱更新
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int viewModifyApprovalInfo(HttpServletRequest request,String target) throws Exception;

	public String getOTLimitList(HttpServletRequest request, List aliasNameList, List list, List mapList, List mapNameList, String flag) throws Exception;
	public String getVacPlanList(HttpServletRequest request, List aliasNameList, List list, List mapList, List mapNameList, String flag) throws Exception;

	public List getOtLimitDataImportResultList(HttpServletRequest request, Map searchMap);

	public String insertOTLimit(HttpServletRequest request);

	public int delAdjustApplyLeaveCoordForm(HttpServletRequest request) throws Exception;
	public int saveOtApplyAffirmForBatch(HttpServletRequest request) throws Exception ;
	public int saveOtApplyByAnyApproverForBatch(HttpServletRequest request) throws Exception;
	public int saveAdjustHolidayApplyForBatch(HttpServletRequest request) throws Exception ;
	public int delOtApplyAffirmForBatch(HttpServletRequest request) throws Exception;
	public int delAdjustHolidayApplyForBatch(HttpServletRequest request) throws Exception;
	public int addOtApplyAffirm(HttpServletRequest request) throws Exception;
	public int addAdjustHolidayApply(HttpServletRequest request) throws Exception;
	public List getValidateInfo(Map searchMap) throws Exception;
	public String submitImportExcelOtData(HttpServletRequest request) throws Exception ; 	
	public String valImportExcelOtData(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getOtTempList(HttpServletRequest request) throws Exception ;
	public int getOtTempCnt(HttpServletRequest request, String errorFlag)  throws Exception ;
	@SuppressWarnings("unchecked")
	public Object getOtLength(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public Object getOtShiftTime(HttpServletRequest request) throws Exception;
	
	public int saveOTApplyInfoForBatchHAE(HttpServletRequest request) throws Exception;
}