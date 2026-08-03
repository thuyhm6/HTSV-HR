package com.ait.ess.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.ViewOverInfoDao;
import com.ait.ess.service.viewOverInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class viewOverInfoSerImpl implements viewOverInfoSer{

	@Autowired
	ViewOverInfoDao viewOverInfoDao;
	

	private GregorianCalendar startTime = new GregorianCalendar();

	private GregorianCalendar endTime = new GregorianCalendar();
	
	public GregorianCalendar getStartTime() {
		return startTime;
	}


	public void setStartTime(GregorianCalendar startTime) {
		this.startTime = startTime;
	}


	public GregorianCalendar getEndTime() {
		return endTime;
	}


	public void setEndTime(GregorianCalendar endTime) {
		this.endTime = endTime;
	}


	@Override
	public List getOtDeductTimeList(HttpServletRequest request) throws Exception {
		Map param = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		param.put("CPNY_ID", admin.getCpnyId());
		param.put("PERSON_ID", admin.getAdminID());
		
		return viewOverInfoDao.getOtDeductTimeList(param);

		
	}


	@Override
	public List getSelectCode() throws Exception {
		
		return viewOverInfoDao.getSelectCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int addOvertimeApply(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		@SuppressWarnings("unused")
		String applyTypeNo = paramMap.get("APPLY_TYPE_NO") != null ? paramMap.get("APPLY_TYPE_NO").toString() : "";

		paramMap.get("person_id");
		paramMap.put("TO_DATE_TIME", paramMap.get("TO_DATE") != null ? paramMap.get("TO_DATE").toString() : paramMap.get("FROM_DATE"));
		paramMap.put("FROM_DATE_TIME", paramMap.get("FROM_DATE"));

		String fromTimeHour = paramMap.get("FROM_TIME_HOUR") != null ? paramMap.get("FROM_TIME_HOUR").toString() : "00";
		String formTimeMinute = paramMap.get("FROM_TIME_MINUTE") != null ? paramMap.get("FROM_TIME_MINUTE").toString(): "00";
		String toTimeHour = paramMap.get("TO_TIME_HOUR") != null ? paramMap.get("TO_TIME_HOUR").toString() : "00";
		String toTimeMinute = paramMap.get("TO_TIME_MINUTE") != null ? paramMap.get("TO_TIME_MINUTE").toString() : "00";
		List dateList = this.viewOverInfoDao.getOvertimeApplyAllDateList(paramMap);
		//判断是否为连续申请
		String continueApply = request.getParameter("continueApply") != null ? request.getParameter("continueApply") : "";
		//如果是连续申请
		if("1".equals(continueApply)){
			for (int i = 0; i < dateList.size(); i++) {
				LinkedHashMap dateMap = (LinkedHashMap) dateList.get(i);
				String fromDateTime = dateMap.get("FROM_DATE") != null ? dateMap.get("FROM_DATE").toString() : "";
				// if("1440".equals(dateTypeId))
				// break;
				dateMap = this.getLinkedMapByRequest(request, dateMap);
				dateMap.put("PERSON_ID", paramMap.get("PERSON_ID"));
				dateMap.put("APPLY_DATE", fromDateTime);
				dateMap.put("FROM_TIME", fromDateTime + " " + fromTimeHour + ":"+ formTimeMinute);
				dateMap.put("TO_TIME", fromDateTime + " " + toTimeHour + ":"+ toTimeMinute);
				dateMap.put("ifForcedTypeChoice", paramMap.get("ifForcedTypeChoice"));
				dateMap.put("APPLY_TYPE_CODE", paramMap.get("APPLY_TYPE_CODE"));
				dateMap.put("APPLY_REMARK", paramMap.get("APPLY_REMARK"));
				dateMap.put("APPLY_TYPE_NO", paramMap.get("APPLY_TYPE_NO"));
				LinkedHashMap otMap = this.preAddOvertimeApply(dateMap, admin.getLanguage());

				batchOtApplyList.add(otMap);
			}
		}else{
			LinkedHashMap dateMap = new LinkedHashMap();
			String fromDateTime = request.getParameter("FROM_DATE") != null ? request.getParameter("FROM_DATE").toString() : "";
			String toDateTime = request.getParameter("TO_DATE") != null ? request.getParameter("TO_DATE").toString() : "";
			// if("1440".equals(dateTypeId))
			// break;
			dateMap = this.getLinkedMapByRequest(request, dateMap);
			dateMap.put("PERSON_ID", paramMap.get("PERSON_ID"));
			dateMap.put("APPLY_DATE", fromDateTime);
			dateMap.put("FROM_TIME", fromDateTime + " " + fromTimeHour + ":"+ formTimeMinute);
			dateMap.put("TO_TIME", toDateTime + " " + toTimeHour + ":"+ toTimeMinute);
			dateMap.put("ifForcedTypeChoice", paramMap.get("ifForcedTypeChoice"));
			dateMap.put("APPLY_TYPE_CODE", paramMap.get("APPLY_TYPE_CODE"));
			dateMap.put("APPLY_REMARK", paramMap.get("APPLY_REMARK"));
			dateMap.put("APPLY_TYPE_NO", paramMap.get("APPLY_TYPE_NO"));
			dateMap.put("OT_DEDUCT_TIME", 0);
			LinkedHashMap otMap = this.preAddOvertimeApply(dateMap, admin.getLanguage());

			batchOtApplyList.add(otMap);
		}
		this.viewOverInfoDao.addOvertimeApplyInBatch(batchOtApplyList);
		return 1;
	}

	/**
	 * 通过request请求封装查询条件(get conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequest(HttpServletRequest request,
			LinkedHashMap paramMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}
	
	
	/**
	 * 通过request请求封装查询条件(get conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequest(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}

	
	/**
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddOvertimeApply(LinkedHashMap paramMap,
			String language) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		// List list = new ArrayList();
		LinkedHashMap essParamMap = new LinkedHashMap();
		List distinctList = new ArrayList();
		Object paramValueObj = null;
		String ifNeedAffirm = "";
		String ifNeedConfirm = "";
		// LinkedHashMap paramMap = this.getLinkedMapByRequest(request);

		// if (paramMap.get("PERSON_ID") == null) {
		// throw new CommonException("加班申请的人员标识不能为空,请重新申请!");
		// }
		// 需要决裁
		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
		essParamMap.put("ESS_PARAM_NO", "4159");
		paramValueObj = this.viewOverInfoDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedAffirm = paramValueObj != null ? paramValueObj.toString() : "0";
		essParamMap.put("ESS_PARAM_NO", "4160");
		paramValueObj = this.viewOverInfoDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedConfirm = paramValueObj != null ? paramValueObj.toString() : "0";
		LinkedHashMap personMap = (LinkedHashMap) this.viewOverInfoDao.getPersonInfoByPersonId(paramMap);
		List affirmerList = this.getAffirmorListByMap(paramMap);

		if (affirmerList.size() == 0 && "1".equals(ifNeedAffirm)) {
			// 未给该员工设置决裁者时
			throw new CommonException(TipMessage.getTipMessage("alert.message.ess.infoApply.notFor", language)
					+ personMap.get("LOCAL_NAME")
							+ TipMessage.getTipMessage("alert.message.ess.infoApply.pleaseSetAffirmorFirst",language));
		} else {
			int count = 1;
			boolean flag = true;
			Map filterMap = new LinkedHashMap();
			for (int i = 0; i < affirmerList.size(); i++) {
				LinkedHashMap affirmerMap = (LinkedHashMap) affirmerList.get(i);
				if (affirmerMap.get("AFFIRMOR_ID") != null) {
					if (flag) {
						// 只将流程第一步的决裁者取出来存到加班申请表
						paramMap.put("CURRENT_AFFIRM_ID", affirmerMap.get("AFFIRMOR_ID"));
						flag = false;
					}
					filterMap.put(affirmerMap.get("AFFIRMOR_ID").toString(),affirmerMap);
				}
			}
			// 判断saveAffirmorList里边重复的决裁者，然后重新排序
			for (Iterator iterator = filterMap.values().iterator(); iterator.hasNext();) {
				LinkedHashMap temp = (LinkedHashMap) iterator.next();
				temp.put("AFFIRM_LEVEL", count);
				count++;
				distinctList.add(temp);
			}
			String otFromDate = paramMap.get("FROM_TIME") != null ? paramMap.get("FROM_TIME").toString() : "";
			String otToDate = paramMap.get("TO_TIME") != null ? paramMap.get("TO_TIME").toString() : "";
			// 检查申请开始时间和结束时间,如果格式不对提示用户
			this.formatFromAndToDate(otFromDate, otToDate);
			//各种验证（日期、时间、类型等）
			//otApplyCheck(paramMap, personMap, language)
			if (true){
				if ("1".equals(ifNeedAffirm)) {
					paramMap.put("ACTIVITY", "0");
					returnMap.put("PARAM_MAP", paramMap);
					returnMap.put("DISTINCT_LIST", distinctList);
				} else if ("0".equals(ifNeedAffirm) && "1".equals(ifNeedConfirm)) {
					// 不需要决裁,但需要人事确认
					paramMap.put("ACTIVITY", "0");
					returnMap.put("PARAM_MAP", paramMap);
				} else if ("0".equals(ifNeedAffirm) && "0".equals(ifNeedConfirm)) {
					// 既不需要决裁也不需要人事确认
					throw new CommonException(
							TipMessage.getTipMessage("alert.message.ess.infoApply.otApplyShouldAffirmOrConfirm",language));
				}
			}
		}
		return returnMap;
	}
	
	
	/**
	 * 格式化开始时间和结束时间(format apply-from-date and apply-to-date)
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	private void formatFromAndToDate(String otFromDate, String otToDate) {
		this.setStartTime(DateUtil.ParseGregorianCalendar(otFromDate));
		this.setEndTime(DateUtil.ParseGregorianCalendar(otToDate));
	}
	
	/**
	 * 检查加班申请是否合格,并提示相应的信息(examine overtime apply if correct and give message)
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean otApplyCheck(LinkedHashMap paramMap,
			LinkedHashMap personMap, String language) throws Exception {
		LinkedHashMap essParamMap = new LinkedHashMap();
		Object paramValueObj = null;
		String paramValue = "";
		// 开始结束时间检查
		// if (this.applyStartEndTimeCheck()) {
		// throw new CommonException(
		// TipMessage
		// .getTipMessage(
		// "alert.message.ess.infoApply.finishDateShouldLaterThanStartDate",
		// language));
		// }
		String ifForcedTypeChoice = paramMap.get("ifForcedTypeChoice") != null ? 
				paramMap.get("ifForcedTypeChoice").toString(): "0";
		if ("0".equals(ifForcedTypeChoice)) {
			// 加班时间与班次时间检查 不检测节假日加班申请
			if (!"34".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				if (this.getOtApplyDateWithShift(paramMap)) {
					throw new CommonException(
							TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
							+ personMap.get("LOCAL_NAME")
							+ TipMessage
								.getTipMessage("alert.message.ess.infoApply.otApplyDateConflictWithThatDayShift",language));
				}
			}
			String applyDateStr = paramMap.get("APPLY_DATE") != null ? paramMap.get("APPLY_DATE").toString() : "";
			paramMap.put("APPLY_DATE_STR", applyDateStr.replaceAll("-", "/"));

			// 日历类型:1440平时,1441公休,1442节假
			int dataType = this.viewOverInfoDao.getDataType(paramMap);
			// 检测节假日加班申请的日期是否是节假日，如果不是节假日，不允许申请
			if ("34".equals(paramMap.get("APPLY_TYPE_CODE")) && !"1442".equals(String.valueOf(dataType))) {
				throw new CommonException(
						TipMessage
							.getTipMessage("alert.message.ess.infoApply.otHolidayApplyDateOnlyBeAppliedInHoliday",language));
			}
			// 根据班次检查手工输入的加班类型
			if ("32".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				if (!"1440".equals(String.valueOf(dataType))) {
					throw new CommonException(
							TipMessage.getTipMessage(
									"alert.message.ess.infoApply.failedPerson",
									language)
									+ personMap.get("LOCAL_NAME")
									+ TipMessage
											.getTipMessage(
													"alert.message.ess.infoApply.overtimeApplyInWorkdaysConflictWithShift",
													language));//  
				}
			} else if ("33".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				if (!"1441".equals(String.valueOf(dataType))) {
					throw new CommonException(
							TipMessage.getTipMessage(
									"alert.message.ess.infoApply.failedPerson",
									language)
									+ personMap.get("LOCAL_NAME")
									+ TipMessage
											.getTipMessage(
													"alert.message.ess.infoApply.overtimeApplyInWeekendConflictWithShift",
													language));// alert.ess.overtime.fail_ot_type_shift
				}
			} else if ("34".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				if (!"1442".equals(String.valueOf(dataType))) {
					throw new CommonException(
							TipMessage.getTipMessage(
									"alert.message.ess.infoApply.failedPerson",
									language)
									+ personMap.get("LOCAL_NAME")
									+ TipMessage
											.getTipMessage(
													"alert.message.ess.infoApply.overtimeApplyInHolidaysConflictWithShift",
													language));
				}
			}
		}

		// 加班时间与之前申请加班时间检查
		// if (true) { this.essSysparam.isCheckOtApplyOtConflict()
		if (this.otApplyConflictWithExsitOtApply(paramMap)) {
			throw new CommonException(
					TipMessage.getTipMessage(
							"alert.message.ess.infoApply.failedPerson",
							language)
							+ personMap.get("LOCAL_NAME")
							+ TipMessage
									.getTipMessage(
											"alert.message.ess.infoApply.otApplyDateConflictWithThatDayShift",
											language));
		}
		// }
		// 加班时间与之前申请休假时间检查
		// if (true) {// this.essSysparam.isCheckOtApplyLeaveConflict()
		if (this.otApplyConflictWithExsitLeaveApply(paramMap)) {
			throw new CommonException(
					TipMessage.getTipMessage(
							"alert.message.ess.infoApply.failedPerson",
							language)
							+ personMap.get("LOCAL_NAME")
							+ TipMessage
									.getTipMessage(
											"alert.message.ess.infoApply.otApplyDateConflictWithExistLeaveApplyDate",
											language));
		}
		// }

		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
		// 加班申请是否在可以申请日期上限范围内
		essParamMap.put("ESS_PARAM_NO", "4161");
		paramValueObj = this.viewOverInfoDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		
		paramValue = paramValueObj != null ? paramValueObj.toString() : "-1";
		if ("-1".equals(paramValue)) {
			if (this.otCheckOtApplyDaysBefore(paramMap, 1)) {
				throw new CommonException(
						TipMessage
								.getTipMessage(
										"alert.message.ess.infoApply.applyFailed_cannotApply",
										language)
								+ paramValue
								+ TipMessage
										.getTipMessage(
												"alert.message.ess.infoApply.daysBeforeOvertimeApply",
												language));
			}
		}

		// 加班申请是否在可以申请日期下限范围内
		essParamMap.put("ESS_PARAM_NO", "4162");
		paramValueObj = this.viewOverInfoDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		paramValue = paramValueObj != null ? paramValueObj.toString() : "-1";
		if (!"-1".equals(paramValue)) {
			if (this.otCheckOtApplyDaysAfter(paramMap, 1)) {
				throw new CommonException(
						TipMessage
								.getTipMessage(
										"alert.message.ess.infoApply.applyFailed_cannotApply",
										language)
								+ paramValue
								+ TipMessage
										.getTipMessage(
												"alert.message.ess.infoApply.daysLatersOvertimeApply",
												language));
			}
		}
		return true;
	}
	

	/**
	 * 取得审批人列表:1.先取特殊设置人员的决裁者;2.再取特殊设置部门的决裁者;3.最后按流程取决裁者 (get approver list:1
	 * get approver by special-person's-approver setup.2 get approver by
	 * special-department's-approver setup.3 get approver by approve-flow)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List getAffirmorListByMap(LinkedHashMap paramMap) throws Exception {
		LinkedHashMap personMap = (LinkedHashMap) this.viewOverInfoDao.getPersonInfoByPersonId(paramMap);
		paramMap.put("CPNY_ID", personMap.get("CPNY_ID"));
		paramMap.put("DEPT_NO", personMap.get("DEPT_NO"));
		paramMap.put("DUTY_NO", personMap.get("DUTY_NO"));
		//paramMap.put("FROM_TIME", paramMap.get("FROM_DATE") + " " + paramMap.get("FROM_TIME"));
		//paramMap.put("TO_TIME", paramMap.get("TO_DATE") + " " + paramMap.get("TO_TIME"));
		// 先取特殊设置人员的决裁者(get approver by special-person's-approver setup first)
		List<LinkedHashMap> spePersonList = this.viewOverInfoDao.getAffirmorListByPersonID(paramMap);
		if (spePersonList.size() == 0) {
			// 再取特殊设置部门的决裁者(get approver by special-department's-approver setup second)
			List<LinkedHashMap> speDeptList = this.viewOverInfoDao.getAffirmorListByDeptNo(paramMap);
			if (speDeptList.size() == 0) {
				// 最后按流程取决裁者 (get approver by approve-flow last)
				return this.viewOverInfoDao.getAffirmorListByNormal(paramMap);
			} else {
				return speDeptList;
			}
		} else {
			return spePersonList;
		}
	}
	
	/**
	 * 取得加班申请日期的班次时间(get overtime apply with shift)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean getOtApplyDateWithShift(Map paramMap) {
		try {
			List list = this.viewOverInfoDao.getOtApplyDateWithShift(paramMap);
			for (int i = 0; i < list.size(); i++) {
				paramMap = (Map) list.get(i);
				String shiftFrom = paramMap.get("") != null ? paramMap.get("")
						.toString() : "";
				String shiftTo = paramMap.get("") != null ? paramMap.get("")
						.toString() : "";
				@SuppressWarnings("unused")
				GregorianCalendar applyFrom = DateUtil
						.ParseGregorianCalendar(shiftFrom);
				@SuppressWarnings("unused")
				GregorianCalendar applyTo = DateUtil
						.ParseGregorianCalendar(shiftTo);

				GregorianCalendar shiftFromDate = DateUtil
						.ParseGregorianCalendar(shiftFrom);
				GregorianCalendar shiftToDate = DateUtil
						.ParseGregorianCalendar(shiftTo);
				GregorianCalendar startTime = new GregorianCalendar();
				GregorianCalendar endTime = new GregorianCalendar();
				if (DateUtil.DateCross(startTime, endTime, shiftFromDate,
						shiftToDate, "MILLISECOND") > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 加班申请是否与已有加班申请冲突(overtime apply conflict with exist overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean otApplyConflictWithExsitOtApply(Map paraMap)
			throws Exception {
		List list = this.viewOverInfoDao.getExistOtApplyDate(paraMap);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		LinkedHashMap paramMap = null;

		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			String applyOtFrom = paramMap.get("FROM_TIME") != null ? paramMap
					.get("FROM_TIME").toString() : sb.format(date);
			String applyOtTo = paramMap.get("TO_TIME") != null ? paramMap.get(
					"TO_TIME").toString() : sb.format(date);
			GregorianCalendar applyFrom = DateUtil
					.ParseGregorianCalendar(applyOtFrom);
			GregorianCalendar applyTo = DateUtil
					.ParseGregorianCalendar(applyOtTo);
			if (DateUtil.DateCross(this.startTime, this.endTime, applyFrom,
					applyTo, "MILLISECOND") > 0) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 加班申请是否与已有休假/出差/外出申请冲突(overtime apply conflict with exist
	 * leave/evection/egression apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean otApplyConflictWithExsitLeaveApply(Map paramMap)
			throws Exception {
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List list = this.viewOverInfoDao.getExistLeaveDate(paramMap);

		for (int i = 0; i < list.size(); i++) {
			paramMap = (Map) list.get(i);
			String applyOtFrom = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap
					.get("LEAVE_FROM_TIME").toString()
					: sb.format(date);
			String applyOtTo = paramMap.get("LEAVE_TO_TIME") != null ? paramMap
					.get("LEAVE_TO_TIME").toString() : sb.format(date);
			GregorianCalendar applyFrom = DateUtil
					.ParseGregorianCalendar(applyOtFrom);
			GregorianCalendar applyTo = DateUtil
					.ParseGregorianCalendar(applyOtTo);
			if (DateUtil.DateCross(this.startTime, this.endTime, applyFrom,
					applyTo, "MILLISECOND") > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 加班申请是否在可以申请日期上线范围内
	 * 
	 * @param paramMap
	 * @param otApplyDaysBefore
	 */
	@SuppressWarnings("unchecked")
	private boolean otCheckOtApplyDaysBefore(Map paramMap, int otApplyDaysBefore) {
		GregorianCalendar otDate = DateUtil.ParseGregorianCalendar(paramMap
				.get("APPLY_DATE") != null ? paramMap.get("APPLY_DATE")
				.toString() : "");
		GregorianCalendar createDate = DateUtil.ParseGregorianCalendar(paramMap
				.get("APPLY_DATE") != null ? paramMap.get("APPLY_DATE")
				.toString() : "");
		if (otApplyDaysBefore >= 0) {
			GregorianCalendar dayStart = DateUtil.DateAdd(createDate, "DAY", -1
					* otApplyDaysBefore);
			Logger.getLogger(getClass()).debug(dayStart);
			if (otDate.before(dayStart)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 加班申请是否在可以申请日期下限范围内
	 * 
	 * @param paramMap
	 * @param otApplyDaysAfter
	 */
	@SuppressWarnings("unchecked")
	private boolean otCheckOtApplyDaysAfter(Map paramMap, int otApplyDaysAfter) {
		GregorianCalendar otDate = DateUtil.ParseGregorianCalendar(paramMap
				.get("APPLY_DATE") != null ? paramMap.get("APPLY_DATE")
				.toString() : "");
		GregorianCalendar createDate = DateUtil.ParseGregorianCalendar(paramMap
				.get("APPLY_DATE") != null ? paramMap.get("APPLY_DATE")
				.toString() : "");
		if (otApplyDaysAfter >= 0) {
			GregorianCalendar dayEnd = DateUtil.DateAdd(createDate, "DAY",
					otApplyDaysAfter);
			if (otDate.after(dayEnd)) {
				return true;
			}
		}
		return false;
	}


	@Override
	public List getDateByPersonIdAndCpny(HttpServletRequest request) {
		List retrunList = new ArrayList() ;	
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", request.getParameter("personid"));
		paramMap.put("FROM_DATE", request.getParameter("time"));
		paramMap.put("CPNY_ID", admin.getCpnyId() );//interLanguage
		paramMap.put("interLanguage", admin.getLanguage());
		retrunList = viewOverInfoDao.getDateByPersonIdAndCpny(paramMap) ;		
		return retrunList ;
	}


	@Override
	public List getAffirmorList(LinkedHashMap paramMap) throws Exception {
		return this.getAffirmorListByMap(paramMap);
	}


	@Override
	public Object viewOvertimeInfoList(HttpServletRequest request) throws Exception {
		List list = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		paramMap.put("PERSON_ID",admin.getPersonId());
		paramMap.put("menuNum", request.getParameter("menuNum"));
		
		if (UiUtil.getPageNum(request) > 0) {
			list = viewOverInfoDao.viewOvertimeInfoList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = viewOverInfoDao.viewOvertimeInfoList(paramMap);
		}
		List returnList = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		LinkedHashMap childMap = new LinkedHashMap();
		List affirmerList = null;
		for (int i = 0; i < list.size(); i++) {
			// 获得人事决裁状态和是否可删除标识(get person-confirm-status and if can be deleted)
			map = (LinkedHashMap) list.get(i);
			// 查询决裁者集合并存入返回的list里面
			paramMap.put("APPLY_NO", map.get("APPLY_NO"));
			affirmerList = viewOverInfoDao.getAffirmorList(paramMap);
			map.put("affirmerList", affirmerList);

			String applyFromDateStr = "";
			String applyToDateStr = "";
			String fromTIme = "";
			String toTime = "";
			String applyTypeCode = map.get("APPLY_TYPE_CODE") != null ? map.get("APPLY_TYPE_CODE").toString() : "";

			if (!"32".equals(applyTypeCode)) {
				if (map.get("OT_FROM_TIME") != null && !"".equals(map.get("OT_FROM_TIME").toString())) {
					fromTIme = map.get("OT_FROM_TIME") != null ? map.get("OT_FROM_TIME").toString() : "";
					toTime = map.get("OT_TO_TIME") != null ? map.get("OT_TO_TIME").toString() : "";

					applyFromDateStr = fromTIme.substring(0, 10);
					applyToDateStr = toTime.substring(0, 10);
					childMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
					childMap.put("APPLY_FROM_DATE_STR", applyFromDateStr);
					childMap.put("APPLY_TO_DATE_STR", applyToDateStr);
					childMap.put("FROM_DATE", fromTIme);
					childMap.put("TO_DATE", toTime);
					String otLength = map.get("OT_LENGTH") != null ? map.get("OT_LENGTH").toString() : "0";
					String deductLength = "0";

					childMap.put("OT_APPLY_TYPE_CODE", applyTypeCode);
					if (this.viewOverInfoDao.getDeductFromTimeByCpnyId(childMap) > 0) {
						//----- 获得申请的长度
						deductLength = this.viewOverInfoDao.getDeductTimeCountInOtTimeByCpnyId(childMap);
					}
					map.put("OT_LENGTH", Double.parseDouble(otLength) - Double.parseDouble(deductLength));
				}
			}
			returnList.add(map);
		}
		return returnList;
	}


	/**
	 * 加班信息申请个数(view overtime apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int viewOvertimeInfoListCnt(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);

		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		paramMap.put("menuNum", request.getParameter("menuNum"));
		return viewOverInfoDao.viewOvertimeInfoListCnt(paramMap);
	}

	
	
	
	
	
	
	/**
	 * 通过request请求封装查询条件(get search conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	private LinkedHashMap getLinkedMapByRequestForSearch(
			HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}

}
