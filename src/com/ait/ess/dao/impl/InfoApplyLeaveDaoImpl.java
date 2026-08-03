package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import bsh.This;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ar.dao.ItemsDao;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.service.InfoApplySer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * 信息申请(information apply)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: InfoApplyDaoImpl.java
 * @Description:
 * @Create date: Feb 3, 2012 10:39:12 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn) 
 * @Update Date: Feb 3, 2012 10:39:12 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Repository
public class InfoApplyLeaveDaoImpl extends SqlMapClientSupport implements InfoApplyLeaveDao {

	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	@Autowired
	private InfoApplyDao infoApplyDao;
	@Autowired
	private InfoApplySer infoApplySer;
	@Autowired
	private ItemsDao ItemsDao;
	
	private static String APPLY_TYPE_NO = "21";

	//合同邀请名称
	private static String APPLY_TYPE_NAME = "休假决裁邀请";
	
	/**
	 * 员工信息查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	@Override
	public Object getPersonalInfoByPid(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getPersonalInfoByPersonId",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	/**
	 * 加班申请考勤区间列表(search ot apply ar_month info list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getLeaveApplyArMonthList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getLeaveApplyArMonthList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 内务考勤查询页面(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCoordLeaveInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApplyLeave.getCoordLeaveInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 部门员工休假查询(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDeptLeaveInfoList(Object obj)  {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getDeptLeaveInfoList",obj);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 查询加班申请信息列表(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getBatchLeaveAffirmInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getBatchLeaveAffirmInfoList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 查询加班申请决裁信息-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getLeaveAffirmInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApplyLeave.getLeaveAffirmInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 个人考勤申请明细
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonalAttInfoDetailList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApplyLeave.getPersonalAttInfoDetailList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 异常考勤申请明细
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonalAttInfoDetailList1(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApplyLeave.getPersonalAttInfoDetailList1",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 异常考勤申请明细
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewCheckAttencetanceExForBatchList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApplyLeave.viewCheckAttencetanceExForBatchList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 查询加班申请信息-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getBatchLeaveAffirmInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			/*if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.infoApplyLeave.getBatchLeaveAffirmInfoList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.infoApplyLeave.getBatchLeaveAffirmInfoList",obj);
			}*/
			
			returnList = this.queryForList("ess.infoApplyLeave.getBatchLeaveAffirmInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 查询加班申请信息-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getBatchLeaveAffirmMoreDayInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getBatchLeaveAffirmMoreDayInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 查询加班申请信息-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getNullBatchLeaveAffirmInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getNullBatchLeaveAffirmInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 身份证到期导出
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getIdCardExpire(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getExpiredIdCardList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewAddAttendanceApplyInfoForBatch(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.viewAddAttendanceApplyInfoForBatch",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getFileList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getFileList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@Override
	public int delAttendanceExForBatchInfoList(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.delete("ess.infoApplyLeave.delAttendanceExForBatchInfoList", obj)),Integer.class);
	}
	
	@Override
	public int delAttendanceExForBatchInfoListHAE(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.delete("ess.infoApplyLeave.delAttendanceExForBatchInfoListHAE", obj)),Integer.class);
	}
	
	@Override
	public int delOTApplyInfoForBatchInfoListHAE(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.delete("ess.infoApplyLeave.delOTApplyInfoForBatchInfoListHAE", obj)),Integer.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAttendanceExForBatchInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getAttendanceExForBatchInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int getAttendanceExInfoListCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = (Integer) this.queryForObject("ess.infoApplyLeave.getAttendanceExInfoListCnt",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	/**
	 * 查询加班申请信息-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getNullBatchOTSSTAffirmInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getNullBatchOTSSTAffirmInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 查询空的倒休数据
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getNullBatchAJTSTOAffirmInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getNullBatchAJTSTOAffirmInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 查询空的加班数据数据
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getNullBatchOTTSTOAffirmInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getNullBatchOTTSTOAffirmInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewNullBatchOTTSTOAffirmInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.viewNullBatchOTTSTOAffirmInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewAddOTApplyInfoForBatchHAE(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.viewAddOTApplyInfoForBatchHAE",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public int delNullBatchOTTSTOAffirmInfoList(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.delete("ess.infoApplyLeave.delNullBatchOTTSTOAffirmInfoList", obj)),Integer.class);
	}
	
	/**
	 * 加班申请决裁信息列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getLeaveAffirmInfoListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApplyLeave.getLeaveAffirmInfoListCnt", obj)),Integer.class);
	}
	/**
	 * 加班申请信息列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getBatchLeaveAffirmInfoListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApplyLeave.getBatchLeaveAffirmInfoListCnt", obj)),Integer.class);
	}
	
	/**
	 * 根据PERSONID或DEPTNO取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorListByPersonID(Object obj) throws Exception {
		return this.queryForList("ess.infoApplyLeave.getAffirmorListByPersonID", obj);
	}
	
	/**
	 * 根据员工标识获得员工信息(get person information by personId)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getPersonInfoByPersonId(Object object) throws Exception {
		try {
			return this.queryForObject("ess.infoApplyLeave.getPersonInfoByPersonId",object);
		} catch (Exception e) {
			throw new Exception("Validate vacationEmp date Exception. ", e);
		}
	}
	
	/**
	 * 根据是否有针对部门取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorListByDeptNo(Object obj) throws Exception {
		return this.queryForList("ess.infoApplyLeave.getAffirmorListByDeptNo", obj);
	}
	
	/**
	 * 无特殊设置取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorListByNormal(Object obj) throws Exception {
		LinkedHashMap object = (LinkedHashMap) obj;
		return this.queryForList("ess.infoApplyLeave.getAffirmorListByNormal",object);
	}
	
	/**
	 * 批量添加加班申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addLeaveApplyInBatch(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap map = (LinkedHashMap) list.get(i);
				LinkedHashMap tempMap = new LinkedHashMap();
				int essApplySeq = getEssApplySeq();
				String applyTypeCode = "";
				
				if (map != null && map.get("PARAM_MAP") != null) {
					LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
					obj.put("APPLY_NO_SEQ", essApplySeq);
					obj.put("COORDINATOR_NO", "");
					//插入待申请表
					this.insert("ess.infoApplyLeave.insertApplyLeave", obj);
					//插入的明细表
					//String PK_NO  = (String) this.getArDetailForLeaveApply(obj);
					
					tempMap.put("CREATED_BY", obj.get("CREATED_BY"));
					tempMap.put("APPLY_TYPE_NO", obj.get("APPLY_TYPE_NO"));
					tempMap.put("APPLY_NO_SEQ", essApplySeq);
					applyTypeCode = (null == obj.get("APPLY_TYPE_CODE") ? "" : obj.get("APPLY_TYPE_CODE").toString());
				}

				if (map != null && map.get("DISTINCT_LIST") != null) {
					List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
							tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							tempMap.put("APPLY_TYPE_NO", applyTypeCode);
							this.insert("ess.infoApplyLeave.insertApplyReviewer",tempMap);
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 个人添加考勤申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addLeaveApplyInPerson(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap map = (LinkedHashMap) list.get(i);
				LinkedHashMap tempMap = new LinkedHashMap();
				int essApplySeq = getEssApplySeq();
				String applyTypeCode = "";
				
				if (map != null && map.get("PARAM_MAP") != null) {
					LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
					obj.put("APPLY_NO_SEQ", essApplySeq);
					obj.put("COORDINATOR_NO", "");
					//插入待申请表
					this.insert("ess.infoApplyLeave.insertApplyLeave", obj);
					//插入的明细表
					String PK_NO  = this.getArDetailForLeaveApply(obj);
					LinkedHashMap dataMap = new LinkedHashMap();
					dataMap.put("APPLY_NO",PK_NO);
					dataMap.put("CPNY_ID",obj.get("CPNY_ID"));
					//备份数据的修改信息
					dataMap.put("UPDATED_BY",obj.get("CREATED_BY"));
					dataMap.put("UPDATED_IP",obj.get("CREATED_IP"));
					if (!"".equals(PK_NO)&&PK_NO != null) {
						this.updatSubmitLeaveApplyInBatchForDeletePrepare(dataMap);
						//封装数据
						dataMap.put("PERSON_ID",obj.get("PERSON_ID"));
						dataMap.put("AR_DATE_STR",obj.get("APPLY_TIME"));
						dataMap.put("LEAVE_FROM_TIME",obj.get("LEAVE_FROM_TIME"));
						dataMap.put("LEAVE_TO_TIME",obj.get("LEAVE_TO_TIME"));
						dataMap.put("APPLY_LENGTH",obj.get("APPLY_LENGTH"));
						dataMap.put("DEPTNO",obj.get("DEPTNO"));
						dataMap.put("reason",obj.get("LEAVE_REASON"));
						//获取班次类型
						LinkedHashMap shiftMap = new LinkedHashMap();
						shiftMap.put("AR_DATE_STR",obj.get("APPLY_TIME"));
						shiftMap.put("CPNY_ID",obj.get("CPNY_ID"));
						shiftMap.put("PERSON_ID",obj.get("PERSON_ID"));
						String SHIFT_NO = this.getShiftNoForPerson(shiftMap);
						dataMap.put("SHIFT_NO",SHIFT_NO);
						dataMap.put("CPNY_ID",obj.get("CPNY_ID"));
						dataMap.put("personId",obj.get("PERSON_ID"));
						applyTypeCode = (null == obj.get("APPLY_TYPE_CODE") ? "" : obj.get("APPLY_TYPE_CODE").toString());
						dataMap.put("applyTypeCode",applyTypeCode);
						dataMap.put("interLanguage",obj.get("interLanguage"));
						String DATATYPE = this.getdataTypeMapForPerson(dataMap);
						dataMap.put("DATE_TYPE",DATATYPE);
						String itemNo = this.getItemNoForPerson(dataMap);
						String iweek = this.getIWeekForPerson(shiftMap);
						dataMap.put("IWEEK",iweek);
						dataMap.put("ITEM_NO",itemNo);
						dataMap.put("PERSON_ID",obj.get("PERSON_ID"));
						dataMap.put("UNIT","HOUR");
						dataMap.put("AR_MONTH_STR",obj.get("AR_MONTH_STR"));
						dataMap.put("CREATED_BY",obj.get("CREATED_BY"));
						dataMap.put("CREATED_IP",obj.get("CREATED_IP"));
						dataMap.put("ACTIVITY",0);
						dataMap.put("LOCK_YN","N");
						dataMap.put("AFFIRM_FLAG",14014307);
						LinkedHashMap  personMap = this.getPersonInfoForPerson(dataMap);
						dataMap.put("GROUP_ID",personMap.get("SHIFT_NO"));
						dataMap.put("STATUS_CODE",personMap.get("STATUS_CODE"));
						dataMap.put("STATUS_NAME",personMap.get("STATUS_NAME"));
						dataMap.put("POST_GRADE_NO",personMap.get("POST_GRADE_NO"));
						this.insertSubmitLeaveApplyInBatch(dataMap);
						
					}
					tempMap.put("CREATED_BY", obj.get("CREATED_BY"));
					tempMap.put("APPLY_TYPE_NO", obj.get("APPLY_TYPE_NO"));
					tempMap.put("APPLY_NO_SEQ", essApplySeq);
					applyTypeCode = (null == obj.get("APPLY_TYPE_CODE") ? "" : obj.get("APPLY_TYPE_CODE").toString());
				}

				if (map != null && map.get("DISTINCT_LIST") != null) {
					List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
							tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							tempMap.put("APPLY_TYPE_NO", applyTypeCode);
							this.insert("ess.infoApplyLeave.insertApplyReviewer",tempMap);
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private String getArDetailForLeaveApply(LinkedHashMap obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.getArDetailForLeaveApply", obj));
		return object2 ;
	}
	private String getShiftNoForPerson(LinkedHashMap obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.getShiftNoForPerson", obj));
		return object2 ;
	}
	private String getdataTypeMapForPerson(LinkedHashMap obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.getdataTypeMapForPerson", obj));
		return object2 ;
	}
	private String getItemNoForPerson(LinkedHashMap obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.getItemNoForPerson", obj));
		return object2 ;
	}
	private String getIWeekForPerson(LinkedHashMap obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.getIWeekForPerson", obj));
		return object2 ;
	}

	/**
	 * 批量添考勤申请(来自内务)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addLeaveApplyInArDetailBatch(LinkedHashMap obj) throws Exception {
	
				LinkedHashMap tempMap = new LinkedHashMap();
				int essApplySeq = getEssApplySeq();
					obj.put("APPLY_NO_SEQ", essApplySeq);
					obj.put("PERSON_ID", obj.get("personId"));
					obj.put("APPLY_TYPE_NO","21");
					obj.put("APPLY_TYPE_CODE",getLeaveApplyCode(obj));
					obj.put("APPLY_TIME", obj.get("AR_DATE_STR"));
					obj.put("APPLY_TYPE", "BATCH");
					obj.put("LEAVE_TIME_TYPE", "21");
					obj.put("LEAVE_FROM_TIME",obj.get("LEAVE_FROM_TIME"));
					obj.put("LEAVE_TO_TIME",obj.get("LEAVE_TO_TIME") );
					obj.put("APPLY_LENGTH", obj.get("APPLY_LENGTH"));
					obj.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
					obj.put("LEAVE_REASON", obj.get("reason"));
					obj.put("CREATED_BY", obj.get("CREATED_BY"));
					obj.put("CREATED_IP", obj.get("CREATED_IP"));
					obj.put("ACTIVITY", obj.get("ACTIVITY"));
					obj.put("CURRENT_AFFIRM_ID", obj.get("CURRENT_AFFIRM_ID"));
					this.insert("ess.infoApplyLeave.insertApplyLeave", obj);
			
				if (obj != null) {
					List<LinkedHashMap> aList = (List<LinkedHashMap>) obj.get("affirmList");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
						tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
							tempMap.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
							tempMap.put("ACTIVITY", obj.get("ACTIVITY"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							tempMap.put("APPLY_TYPE_NO", getLeaveApplyCode(obj));
							tempMap.put("APPLY_NO_SEQ", essApplySeq);
							this.insert("ess.infoApplyLeave.insertApplyReviewer2",tempMap);
						}
					}
				}
	}
	/**
	 * 批量添修改班申请(来自内务)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateLeaveApplyInArDetailBatch(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				
				obj.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
				obj.put("LEAVE_REASON", obj.get("reason"));
				obj.put("DESTINATION", "");
				obj.put("LIAISON", "");
				obj.put("CREATED_BY", obj.get("CREATED_BY"));
				obj.put("CREATED_IP", obj.get("CREATED_IP"));
				obj.put("ACTIVITY", "0");
				obj.put("APPLY_NO", getArDetailTstoApplyOfApplyNo(obj));
				obj.put("CURRENT_AFFIRM_ID", "");
				this.update("ess.infoApplyLeave.updateArDetailApplyLeave", obj);
			}
		}
	}
	/**
	 * 批量部门批准(来自内务)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateLeaveApplyInArDetailBatchForTwoApply(LinkedHashMap obj) throws Exception {
		
				
				String APPLY_NO = getArDetailTstoApplyOfApplyNo(obj);
				if (!"".equals(APPLY_NO)&&APPLY_NO != null) {
					obj.put("APPLY_TYPE_CODE",getLeaveApplyCode(obj));
					obj.put("APPLY_TIME", obj.get("AR_DATE_STR"));
					obj.put("LEAVE_FROM_TIME",obj.get("LEAVE_FROM_TIME"));
					obj.put("LEAVE_TO_TIME",obj.get("LEAVE_TO_TIME") );
					obj.put("APPLY_LENGTH", obj.get("APPLY_LENGTH"));
					
					obj.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
					obj.put("LEAVE_REASON", obj.get("reason"));
					obj.put("DESTINATION", "");
					obj.put("LIAISON", "");
					obj.put("UPDATED_BY", obj.get("CREATED_BY"));
					obj.put("UPDATE_IP", obj.get("CREATED_IP"));
					obj.put("ACTIVITY", obj.get("ACTIVITY"));
					obj.put("APPLY_NO",APPLY_NO );
					obj.put("CURRENT_AFFIRM_ID", obj.get("CURRENT_AFFIRM_ID"));
					this.update("ess.infoApplyLeave.updateLeaveApplyInArDetailBatchForTwoApply", obj);
					
					LinkedHashMap  tempMap = new LinkedHashMap();
					tempMap.put("APPLY_NO", APPLY_NO);
					tempMap.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
					tempMap.put("ACTIVITY", obj.get("ACTIVITY"));
					tempMap.put("UPDATED_BY", obj.get("CREATED_BY"));
					this.update("ess.infoApplyLeave.updateOtApplyEssAffrim", obj);
				}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void updatAdjustFlag(LinkedHashMap obj) throws Exception {
	   this.update("ess.infoApplyLeave.updatAdjustFlag", obj);
	}
	
	/**
	 * 根据法人获得该法人配置的参数(get ess param infomation by cpny_id)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getEssParamInfoByParamNoAndCpnyId(Object object)
			throws Exception {
		try {
			return this.queryForObject("ess.infoApplyLeave.getParamInfoValue",
					object);
		} catch (Exception e) {
			throw new Exception(
					"getEssParamInfoByParamNoAndCpnyId Exception. ", e);
		}
	}
	/**
	 * 获取班次 ,加班类型(batch add leave apply)
	 @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-12-6 下午4:48:18 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDateByPersonIdAndCpny(Object obj) {
		try {
			return this.queryForList("ess.infoApplyLeave.getDateByPersonIdAndCpny", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 根据加班申请NO查询此次（个人/批量）申请的所有人(search ot info list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getApplyorByApplyNoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getLeaveApplyorByApplyNoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 根据加班申请NO决裁信息查询(search ot info list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorByApplyNoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getAffirmorByApplyNoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 加班申请check信息查询(search ot info list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCheckorByApplyNoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getCheckorByApplyNoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int delLeaveApplyInBatch(List list) throws Exception {
	
		try {
			
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.update("ess.infoApplyLeave.PR_DELETE_LEAVE_CONFIRM",obj);
				//this.insert("ess.infoApplyLeave.PR_DELETE_SHOP_SHIFT", obj);
				this.insert("ess.infoApplyLeave.insertArShiftChange",obj);
				this.update("ess.infoApplyLeave.updateEssLeaveApplyTable", obj);//删除申请表
				Map paramMap = (Map)this.queryForObject("ess.infoApply.getAffirmEmailParam",obj);
				if(paramMap!=null){
					paramMap.put("adminID", obj.get("UPDATED_BY"));
					paramMap.put("adminIP", obj.get("UPDATED_IP"));
					this.update("ess.infoApply.PR_AFFIRM_CANCEL", paramMap);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int delAttendanceApplyInfoForBatch(List list) throws Exception {
	
		try {
			
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.update("ess.infoApplyLeave.PR_DELETE_LEAVE_CONFIRM",obj);
				//this.insert("ess.infoApplyLeave.PR_DELETE_SHOP_SHIFT", obj);
				this.insert("ess.infoApplyLeave.insertArShiftChange",obj);
				//this.delete("ess.infoApplyLeave.delAttendanceApplyInfoForBatch", obj);//删除申请表
				//this.delete("ess.infoApplyLeave.delArApplyResult",obj); //删除申请表
				this.update("ess.infoApplyLeave.updateEssLeaveApplyTable", obj);//取消申请
				Map paramMap = (Map)this.queryForObject("ess.infoApply.getAffirmEmailParam",obj);
				if(paramMap!=null){
					paramMap.put("adminID", obj.get("UPDATED_BY"));
					paramMap.put("adminIP", obj.get("UPDATED_IP"));
					this.update("ess.infoApply.PR_AFFIRM_CANCEL", paramMap);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int delAttendanceExInBatchForBatch(List list) throws Exception {
	
		try {
			
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.delete("ess.infoApplyLeave.delAttendanceExInBatchForBatch", obj);//删除申请表
				/*Map paramMap = (Map)this.queryForObject("ess.infoApply.getAffirmEmailParam",obj);
				paramMap.put("adminID", obj.get("UPDATED_BY"));
				paramMap.put("adminIP", obj.get("UPDATED_IP"));
				this.update("ess.infoApply.PR_AFFIRM_CANCEL", paramMap);*/
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	/**
	 * 批量考勤异常 加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int delApplyLeaveInfo(List list) throws Exception {
	
		try {
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.update("ess.infoApplyLeave.PR_DELETE_ATTENDANCEEX_CONFIRM",obj);
				this.update("ess.infoApplyLeave.updateEssCardApplyTable", obj);//删除申请表
				/*Map paramMap = (Map)this.queryForObject("ess.infoApply.getAffirmEmailParam",obj);
				paramMap.put("adminID", obj.get("UPDATED_BY"));
				paramMap.put("adminIP", obj.get("UPDATED_IP"));
				this.update("ess.infoApply.PR_AFFIRM_CANCEL", paramMap);*/
				this.delete("ess.infoApplyLeave.updateSyAffrimEmailTable", obj);//删除决裁表
				this.update("ess.infoApplyLeave.updateArDetailTable", obj);//修改AR_DETAIL_HTSV
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	public int delAttencetanceEx(List list) throws Exception {
		
		try {
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.update("ess.infoApplyLeave.PR_DELETE_ATTENDANCEEX_CONFIRM",obj);
				this.update("ess.infoApplyLeave.updateEssCardApplyTableForBatch", obj);//删除申请表
				/*Map paramMap = (Map)this.queryForObject("ess.infoApply.getAffirmEmailParam",obj);
				paramMap.put("adminID", obj.get("UPDATED_BY"));
				paramMap.put("adminIP", obj.get("UPDATED_IP"));
				this.update("ess.infoApply.PR_AFFIRM_CANCEL", paramMap);*/
				this.delete("ess.infoApplyLeave.updateSyAffrimEmailTable", obj);//删除决裁表
				this.update("ess.infoApplyLeave.updateArDetailTable", obj);//修改AR_DETAIL_HTSV
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

	/**
	 * 上审取消SST
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int delLeaveApplyInBatchSST(List list) throws Exception {
	
		try {
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				Map paramMap = (Map)this.queryForObject("ess.infoApply.getAffirmEmailParam",obj);
				paramMap.put("adminID", obj.get("UPDATED_BY"));
				paramMap.put("adminIP", obj.get("UPDATED_IP"));
				this.update("ess.infoApply.PR_AFFIRM_CANCEL", paramMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	public String getArDetailTstoApplyOfApplyNoForOt(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApply.getArDetailTstoApplyOfApplyNoForOt", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}
	
	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delLeaveApply(Object object) throws Exception {
		Boolean falg = true;
		this.delete("ess.infoApplyLeave.delOtApplyDetailByApplyNo", object);
		this.delete("ess.infoApplyLeave.delOtApplyAffirmByApplyNo", object);
		this.delete("ess.infoApplyLeave.delOtApplyByApplyNo", object);
		return falg;
	}
	/**
	 * 删除NULL数据(delete )
	 * 
	 * @param object
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@Override
	public void deleteArDetialByNull(Object object) throws Exception {
		this.delete("ess.infoApplyLeave.deleteArDetialByNull", object);
	}
	
	/**
	 * 取消已审核通过的休假申请( cancel leave apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean cancelLeaveApply(Object object) throws Exception {
		Boolean falg = true;
		this.delete("ess.infoApplyLeave.delLeaveApplyDetailByApplyNo", object);
		this.update("ess.infoApplyLeave.cancelLeaveApplyByApplyNo", object);
		return falg;
	}

	/**
	 * 显示个人信息申请修改页面(View personal information apply）
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getPersonInfo(Object obj) throws Exception {
		Map map = new LinkedHashMap();
		try {
			map = (Map) this.queryForObject(
					"ess.infoApplyLeave.retrievePersonalInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 查询人员结果列表(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPersonList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 查询人员结果列表-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.infoApplyLeave.getOtPersonList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.infoApplyLeave.getOtPersonList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getAttendanceInformation(Object obj) {
		Map map = new LinkedHashMap();
		try {
				map = (Map) this.queryForObject("ess.infoApplyLeave.getAttendanceInformation",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map getOTSSTInformation(Object obj) {
		Map map = new LinkedHashMap();
		try {
			map = (Map) this.queryForObject("ess.infoApplyLeave.getOTSSTInformation",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map getAJTSTOInformation(Object obj) {
		Map map = new LinkedHashMap();
		try {
			map = (Map) this.queryForObject("ess.infoApplyLeave.getAJTSTOInformation",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map getOTTSTOInformation(Object obj) {
		Map map = new LinkedHashMap();
		try {
			map = (Map) this.queryForObject("ess.infoApplyLeave.getOTTSTOInformation",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 批量加班申请的人员列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPersonListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.infoApplyLeave.getOtPersonListCnt", obj)),
				Integer.class);
	}

	/**
	 * 添加个人信息申请(add personal information apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean addPersonalInfoApply(Object obj) {
		try {
			// this.startTransaction();
			// 删除老的人员信息申请(delete old person-information-apply)
			this.delete("ess.infoApplyLeave.delEssPersonalInfo", obj);
			// 添加新的人员信息申请(add new person-information-apply)
			this.insert("ess.infoApplyLeave.insertEssPersonalInfo", obj);
			// this.commitTransation();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 拆分
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public void insertSubmitLeaveApplyInBatchForMoreDay(LinkedHashMap obj) throws Exception{
		 if ("TSTO".equals(obj.get("CPNY_ID"))) {
			 this.insert("ess.infoApplyLeave.insertSubmitLeaveApplyInBatchForMoreDay", obj);
		 }else {
			 this.insert("ess.infoApplyLeave.insertSubmitLeaveApplyInBatchForMoreDaySST", obj);
		 }
		
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
	@Override
	public List getAffirmorList(Object obj) throws Exception {
		// 先取特殊设置人员的决裁者(get approver by special-person's-approver setup first)
		List<LinkedHashMap> spePersonList = this.getAffirmorListByPersonID(obj);
		if (spePersonList.size() == 0) {
			// 再取特殊设置部门的决裁者(get approver by special-department's-approver setup
			// second)
			List<LinkedHashMap> speDeptList = this.getAffirmorListByDeptNo(obj);
			if (speDeptList.size() == 0) {
				// 最后按流程取决裁者 (get approver by approve-flow last)
				return this.getAffirmorListByNormal(obj);
			} else {
				return speDeptList;
			}
		} else {
			return spePersonList;
		}
	}

	/**
	 * 得到员工班次 (get person time shift)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpShift(Object obj) throws Exception {
		try {
			return this.queryForList("ess.infoApplyLeave.getEmpShift", obj);
		} catch (SQLException e) {
			throw new CommonException("获得员工班次失败,请重试!");
		}
	}

	/**
	 * 查询公司用于扣除时间的LIST(search ot deduct time list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtDeductTimeList(Object obj) throws Exception {
		try {
			return this.queryForList("ess.infoApplyLeave.getOtDeductTimeList", obj);
		} catch (SQLException e) {
			throw new CommonException("获得扣除时间的LIST失败,请重试!");
		}
	}
	
	/**
	 * 添加加班申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addOvertimeApply(Object obj, List<LinkedHashMap> list)
			throws Exception {
		LinkedHashMap tempMap = new LinkedHashMap();
		int essApplySeq = getEssApplySeq();
		((LinkedHashMap) obj).put("APPLY_NO_SEQ", essApplySeq);
		this.insert("ess.infoApplyLeave.insertOvertimeApply", obj);
		tempMap.put("CREATED_BY", ((LinkedHashMap) obj).get("CREATED_BY"));
		tempMap.put("APPLY_NO_SEQ", essApplySeq);

		if (list != null && list.size() > 0) {
			for (LinkedHashMap parmers : list) {
				tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
				tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
				tempMap.put( "APPLY_TYPE_NO",parmers.get("APPLY_TYPE_NO") );
				this.insert("ess.infoApplyLeave.insertApplyReviewer", tempMap);
			}
		}
	}

	/**
	 * 批量添加加班申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addOvertimeApplyInBatch2(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap map = (LinkedHashMap) list.get(i);
				LinkedHashMap tempMap = new LinkedHashMap();
				int essApplySeq = getEssApplySeq();

				if (map != null && map.get("PARAM_MAP") != null) {
					LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
					obj.put("APPLY_NO_SEQ", essApplySeq);
					this.insert("ess.infoApplyLeave.insertOvertimeApply", obj);
					tempMap.put("CREATED_BY", ((LinkedHashMap) obj).get("CREATED_BY"));
					tempMap.put("APPLY_TYPE_NO", ((LinkedHashMap) obj).get("APPLY_TYPE_NO"));
					tempMap.put("APPLY_NO_SEQ", essApplySeq);
				}

				if (map != null && map.get("DISTINCT_LIST") != null) {
					List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
							tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							this.insert("ess.infoApplyLeave.insertApplyReviewer",tempMap);
						}
					}
				}
			}
		}
	}

	/**
	 * 剩余年假数(rest annual leave)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List retrieveVacationEmpREST(Object obj) throws Exception {
		List object2 = this.queryForList("ess.infoApplyLeave.retrieveVacationEmpREST", obj);
		if(object2 ==null){
			object2 = null;
		}
		return object2;
	}

	/**
	 * 剩余年假计算--2013-12-27--lufeng
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getVacationEmpREST(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApplyLeave.getVacationEmpREST", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}
	@Override
	public String getLeaveApplyCode(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApplyLeave.getLeaveApplyCode", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}

	public String getArDetailTstoApplyOfApplyNo(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApplyLeave.getArDetailTstoApplyOfApplyNo", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}
	public String getArDetailTstoApplyOfApplyNoForApplyNo(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApplyLeave.getArDetailTstoApplyOfApplyNoForApplyNo", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}
	//获取正常出勤原先数据
	public String getLeaveApplyPkNoNomal(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApplyLeave.getLeaveApplyPkNoNomal", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}
	
	/**
	 * 剩余调休数(rest adjust leave)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getSurplusAdjustRest(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApplyLeave.getSurplusAdjustRest", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2 ;
	}
	
	/**
	 * 剩余调休数 2013-06-20 lufeng 新增(rest adjust leave)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getAdjustRestNew(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApplyLeave.getAdjustRestNew", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2 ;
	}

	/**
	 * 添加休假申请(add leave apply)
	 * 
	 * @param leaveMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addLeaveApply(LinkedHashMap leaveMap) throws Exception {
		LinkedHashMap obj = new LinkedHashMap();
		if (leaveMap != null) {
			LinkedHashMap tempMap = new LinkedHashMap();
			int essApplySeq = getEssApplySeq();
			if (leaveMap.get("PARAM_MAP") != null) {
				obj = (LinkedHashMap) leaveMap.get("PARAM_MAP");
				((LinkedHashMap) obj).put("APPLY_NO_SEQ", essApplySeq);
				this.insert("ess.infoApplyLeave.insertLeaveApply", obj);
			}

			tempMap.put("CREATED_BY", obj.get("CREATED_BY"));
			tempMap.put("APPLY_TYPE_NO", obj.get("APPLY_TYPE_NO"));
			tempMap.put("APPLY_NO_SEQ", essApplySeq);

			if (leaveMap.get("DISTINCT_LIST") != null) {
				List<LinkedHashMap> list = (List) leaveMap.get("DISTINCT_LIST");
				if (list != null && list.size() > 0) {
					for (LinkedHashMap parmers : list) {
						tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
						tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
						this.insert("ess.infoApplyLeave.insertApplyReviewer",
								tempMap);
					}
				}
			}
		}
	}

	/**
	 * 批量添加休假申请(batch add leave apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public void addLeaveApplyInBatch(List list) throws Exception {
//		if (list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				LinkedHashMap map = (LinkedHashMap) list.get(i);
//				LinkedHashMap tempMap = new LinkedHashMap();
//				int essApplySeq = getEssApplySeq();
//				
//				if (map != null && map.get("PARAM_MAP") != null) {
//					LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
//					obj.put("APPLY_NO_SEQ", essApplySeq);
//					this.insert("ess.infoApplyLeave.insertLeaveApply", obj);
//					tempMap.put("CREATED_BY", ((LinkedHashMap) obj)
//							.get("CREATED_BY"));
//					tempMap.put("APPLY_NO_SEQ", essApplySeq);
//				}
//
//				if (map != null && map.get("DISTINCT_LIST") != null) {
//					List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
//					if (aList != null && aList.size() > 0) {
//						for (LinkedHashMap parmers : aList) {
//							tempMap.put("AffirmLevel", parmers
//									.get("AFFIRM_LEVEL"));
//							tempMap.put("AffirmorId", parmers
//									.get("AFFIRMOR_ID"));
//							this.insert("ess.infoApplyLeave.insertApplyReviewer",
//									tempMap);
//						}
//					}
//				}
//			}
//		}
//	}

	/**
	 * 添加决裁者
	 * @param tempReviewer
	 * @param tempObject
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addAffirmorList(List<Map> tempReviewer, Map tempObject)
			throws Exception {
		try {
			if (tempReviewer != null && tempReviewer.size() > 0) {
				for (Map parmers : tempReviewer) {
					tempObject.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
					tempObject.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
					this
							.insert("ess.infoApplyLeave.insertApplyReviewer",
									tempObject);
				}
			}
		} catch (Exception e) {
			throw new CommonException("添加决裁者失败,请重试!");
		}
	}

	

	

	

	/**
	 * 添加出差申请(add Evection apply)
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addEvectionApply(LinkedHashMap leaveMap) throws Exception {
		LinkedHashMap obj = new LinkedHashMap();
		if (leaveMap != null) {
			LinkedHashMap tempMap = new LinkedHashMap();
			int essApplySeq = getEssApplySeq();
			if (leaveMap.get("PARAM_MAP") != null) {
				obj = (LinkedHashMap) leaveMap.get("PARAM_MAP");
				((LinkedHashMap) obj).put("APPLY_NO_SEQ", essApplySeq);
				this.insert("ess.infoApplyLeave.insertLeaveApply", obj);
			}

			tempMap.put("CREATED_BY", obj.get("CREATED_BY"));
			tempMap.put("APPLY_TYPE_NO", obj.get("APPLY_TYPE_NO"));
			tempMap.put("APPLY_NO_SEQ", essApplySeq);

			if (leaveMap.get("DISTINCT_LIST") != null) {
				List<LinkedHashMap> list = (List) leaveMap.get("DISTINCT_LIST");
				if (list != null && list.size() > 0) {
					for (LinkedHashMap parmers : list) {
						tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
						tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
						this.insert("ess.infoApplyLeave.insertApplyReviewer",
								tempMap);
					}
				}
			}
		}
	}

	/**
	 * 添加外出申请(add egression apply)
	 * 
	 * @param leaveMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addEgressionApply(LinkedHashMap leaveMap) throws Exception {
		LinkedHashMap obj = new LinkedHashMap();
		if (leaveMap != null) {
			LinkedHashMap tempMap = new LinkedHashMap();
			int essApplySeq = getEssApplySeq();
			if (leaveMap.get("PARAM_MAP") != null) {
				obj = (LinkedHashMap) leaveMap.get("PARAM_MAP");
				((LinkedHashMap) obj).put("APPLY_NO_SEQ", essApplySeq);
				this.insert("ess.infoApplyLeave.insertLeaveApply", obj);
			}

			tempMap.put("CREATED_BY", obj.get("CREATED_BY"));
			tempMap.put("APPLY_NO_SEQ", essApplySeq);

			if (leaveMap.get("DISTINCT_LIST") != null) {
				List<LinkedHashMap> list = (List) leaveMap.get("DISTINCT_LIST");
				if (list != null && list.size() > 0) {
					for (LinkedHashMap parmers : list) {
						tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
						tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
						this.insert("ess.infoApplyLeave.insertApplyReviewer",
								tempMap);
					}
				}
			}
		}
	}

	/**
	 * 获得该员工已申请的加班申请时间(get person exist overtime apply date)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getExistOtApplyDate(Object obj) throws Exception {
		try {
			return this.queryForList("ess.infoApplyLeave.getExistOtApplyDate", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取该员工当前月考勤日期
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getCurrentArDate(Object obj) throws Exception { 
		String  str = null;
		try {
			Object result = this.queryForObject("ess.infoApplyLeave.getCurrentArDate", obj);
			str = result != null ? String.valueOf(result) : str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 获取该员工当前月考勤日期
	 * 加班
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getCurrentArDateOt(Object obj) throws Exception { 
		String  str = null;
		try {
			Object result = this.queryForObject("ess.infoApply.getCurrentArDateOt", obj);
			str = result != null ? String.valueOf(result) : str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 取得加班申请日期的班次时间(get overtime apply with shift)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtApplyDateWithShift(Object obj) throws Exception {
		try {
			return this.queryForList("ess.infoApplyLeave.getOtApplyDateWithShift",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得该员工已经存在的休假申请时间(get person exist leave apply date)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getExistLeaveDate(Object obj) throws Exception {
		try {
			return this.queryForList("ess.infoApplyLeave.getExistLeaveDate", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得日历类型(get calendar type)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getDataType(Object object) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.infoApplyLeave.getDataType", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获得申请中的年假数(get applying Annual leave )
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object vacApplying(Object object) throws Exception {
		try {
			return this.queryForObject("ess.infoApplyLeave.vacApplying", object);
		} catch (Exception e) {
			throw new Exception("Validate vacationEmp date Exception. ", e);
		}
	}

	/**
	 * 获得剩余的年假数(get remaining annual leave )
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object restVac(Object object) throws Exception {
		try {
			return this.queryForObject("ess.infoApplyLeave.restVac", object);
		} catch (Exception e) {
			throw new Exception("Validate vacationEmp date Exception. ", e);
		}
	}

	/**
	 * 获得信息申请序列(get information apply sequences)
	 * 
	 * @param object
	 * @return
	 */
	private int getEssApplySeq() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.infoApplyLeave.getEssApplySeq")),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获得内务申请信息申请序列(get information apply sequences)
	 * 
	 * @param object
	 * @return
	 */
	private int getARDetailApplySeq() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.infoApplyLeave.getARDetailApplySeq")),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}	
	/**
	 * 获得内务申请信息申请序列(get information apply sequences)
	 * 
	 * @param object
	 * @return
	 */
	private int getARDetailApplySSTSeq() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.infoApplyLeave.getARDetailApplySSTSeq")),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}	
	/**
	 * 获得内务申请信息申请序列(get information apply sequences)
	 * 
	 * @param object
	 * @return
	 */
	private int getARDetailApplySeqSST() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.infoApplyLeave.getARDetailApplySeqSST")),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}	

	

	/**
	 * 根据法人和参数号查找对应的值(get Param Value By CpnyId And ParamNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getParamValueByCpnyIdAndParamNo(Object obj) throws Exception {
		return this.queryForObject("ess.infoApplyLeave.getParamInfoValue", obj);
	}

	/**
	 * 获得申请时间的长度(get length of apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getLeaveApplyLength(Object obj) throws Exception {
		String length = "0.0";
		length = (String) this.queryForObject(
				"ess.infoApplyLeave.getLeaveApplyLength", obj);
		return length;
	}

	/**
	 * 将加班申请时间按天分解并封装到LIST(decompose overtime apply date for list)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOvertimeApplyAllDateList(Object obj) throws Exception {
		try {
			return this.queryForList("ess.infoApplyLeave.getOvertimeApplyAllDateList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 去年剩余年假数(rest annual leave)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Integer retrieveVacationEmpRESTQN(Object obj) throws Exception {
		Integer object2 = (Integer) this.queryForObject(
				"ess.infoApplyLeave.retrieveVacationEmpRESTQN", obj);
		if(object2 ==null)
		{
			object2 = 0;
		}
		return object2;
	}

	@Override
	public String getspouseBirth(Object obj) {
		String returnString = null;
		try {
			returnString=(String) this.queryForObject("ess.infoApplyLeave.getspouseBirth", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return returnString ;
	}

	@Override
	public String getviewVacationStandard(Object obj) {
		String length = "";
		try {
			length = (String) this.queryForObject(
					"ess.infoApplyLeave.getviewVacationStandard", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return length;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void addEvectionApplyList(List batchOtApplyList) throws Exception {
		if (batchOtApplyList.size() > 0) {
			for (int i = 0; i < batchOtApplyList.size(); i++) {
				LinkedHashMap leaveMap = (LinkedHashMap) batchOtApplyList.get(i);
				LinkedHashMap obj = new LinkedHashMap();
				if (leaveMap != null) {
					LinkedHashMap tempMap = new LinkedHashMap();
					int essApplySeq = getEssApplySeq();
					if (leaveMap.get("PARAM_MAP") != null) {
						obj = (LinkedHashMap) leaveMap.get("PARAM_MAP");
						((LinkedHashMap) obj).put("APPLY_NO_SEQ", essApplySeq);
						this.insert("ess.infoApplyLeave.insertLeaveApply", obj);
					}
		
					tempMap.put("CREATED_BY", obj.get("CREATED_BY"));
					tempMap.put("APPLY_TYPE_NO", obj.get("APPLY_TYPE_NO"));
					tempMap.put("APPLY_NO_SEQ", essApplySeq);
		
					if (leaveMap.get("DISTINCT_LIST") != null) {
						List<LinkedHashMap> list = (List) leaveMap.get("DISTINCT_LIST");
						if (list != null && list.size() > 0) {
							for (LinkedHashMap parmers : list) {
								tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
								tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
								this.insert("ess.infoApplyLeave.insertApplyReviewer",
										tempMap);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List getLeaveAffirmInfo2List(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getLeaveAffirmInfo2List",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public void addXiaojiaLeaveApplyInfo(LinkedHashMap map) throws Exception {
		Boolean falg = true;
		LinkedHashMap tbMap = (LinkedHashMap) map.get("tb");
		int essApplySeq = getEssApplySeq();
		tbMap.put("APPLY_NO", essApplySeq);
		this.insert("ess.infoApplyLeave.xiaojiaLeaveApply", tbMap);
		
		List<LinkedHashMap> list = (List<LinkedHashMap>) map.get("affirm");
		for(LinkedHashMap affirmMap : list){
			affirmMap.put("APPLY_NO_SEQ", essApplySeq);
			this.insert("ess.infoApplyLeave.insertApplyReviewer", affirmMap);
		}
		this.sendToLGEPxiaojiaInsert(tbMap);
	}

	@Override
	public List<LinkedHashMap> getAffirmInfo2List(Object object) {
		List<LinkedHashMap> returnList = new ArrayList<LinkedHashMap>();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getAffirmInfo2List",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public String getZhengce(LinkedHashMap map) {
		
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getZhengce",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return (null == obj ? "" : obj.toString());	
	}

	@Override
	public String getShenqingshichang(LinkedHashMap map) {

		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getShenqingshichang",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return (null == obj ? "0" : obj.toString());	
	}
	@Override
	public LinkedHashMap getLeaveDispalydaoxiuOrnianjMap(LinkedHashMap obj) {
		
		LinkedHashMap map = new LinkedHashMap();
		try {
			map = (LinkedHashMap) this.queryForObject(
					"ess.infoApplyLeave.getLeaveDispalydaoxiuOrnianjMap", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
		 	
	}
	/**
	 * 内务根据考勤状态获取对应的考勤类型
	 */
	@Override
	public String getApplyTypeCode(LinkedHashMap map) {
		
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getApplyTypeCode",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return (null == obj ? "0" : obj.toString());	
	}
	@Override
	public String getChechedAffrim(LinkedHashMap map) {
		
		Object obj = null;
		try {
			if ("SST".equals(map.get("CPNY_ID"))) {
				if(map.get("OLD_APPLY_NO") != null && !"".equals(map.get("OLD_APPLY_NO"))){
					  obj = this.queryForObject("ess.infoApplyLeave.getChechedAffrimold",map);
				}else {
					  obj = this.queryForObject("ess.infoApplyLeave.getChechedAffrim",map);
				}
			}else {
				 obj = this.queryForObject("ess.infoApplyLeave.getChechedAffrim",map);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return obj.toString();	
	}
	@Override
	public String getkaoQinNo(LinkedHashMap map) {
		
		Object obj = null;
		try {
			if ("TSTO".equals(map.get("CPNY_ID"))) {
				obj = this.queryForObject("ess.infoApplyLeave.getkaoQinNo",map);
			}else {
				obj = this.queryForObject("ess.infoApplyLeave.getkaoQinNoSST",map);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return obj.toString();	
	}
	@Override
	public String getkaoQinNoSST(LinkedHashMap map) {
		
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getkaoQinNoSST",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return obj.toString();	
	}
	@Override
	public String getkaoQinNoPkNo(LinkedHashMap map) {
		Object obj = null;
		try {
			if ("TSTO".equals(map.get("CPNY_ID"))) {
				obj = this.queryForObject("ess.infoApplyLeave.getkaoQinNoPkNo",map);
			}else {
				obj = this.queryForObject("ess.infoApplyLeave.getkaoQinNoPkNoSST",map);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return obj.toString();	
	}
	@Override
	public String getChechedPersonidIsNull(LinkedHashMap map) {
		
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getChechedPersonidIsNull",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return obj.toString();	
	}
	@Override
	public int getCheckPersonCount(LinkedHashMap map) throws Exception {
		
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApplyLeave.getCheckPersonCount", map)),Integer.class);
	}
	@Override
	public String getYesBefAffrimNo(LinkedHashMap map) {
		
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getYesBefAffrimNo",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return obj.toString();	
	}
	
	@Override
	public Object getChechedSex(LinkedHashMap map) {
		
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getChechedSex",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return obj;	
	}
	@Override
	public Object getTrainLocalName(LinkedHashMap map) {
		
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getTrainLocalName",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return obj;	
	}
	@Override
	public String getChechedItemName(LinkedHashMap map) {
		
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getChechedItemName",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return obj.toString();	
	}
	
	@Override
	public Object getLeaveInfoByLeave(Object object) throws Exception {
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getLeaveInfoByLeave",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	@Override
	public Object getOtApplyPersonal(Object object) throws Exception {
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getOtApplyPersonal",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	@Override
	public Object getOtApplyPersonalXiao(Object object) throws Exception {
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getOtApplyPersonalXiao",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	@Override
	public Object getOtApplyPersonalIfDisplay(Object object) throws Exception {
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getOtApplyPersonalIfDisplay",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	@Override
	public List getAffirmorListByLeave(Object obj) throws Exception {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getAffirmorListByLeave",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获得该员工已经存在的休假申请时间(get person exist leave apply date)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getExistLeaveDateForUpdate(Object obj) throws Exception {
		try {
			return this.queryForList("ess.infoApplyLeave.getExistLeaveDateForUpdate", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object vacApplyingForUpdate(Object object) throws Exception {
		try {
			return this.queryForObject("ess.infoApplyLeave.vacApplyingForUpdate", object);
		} catch (Exception e) {
			throw new Exception("Validate vacationEmp date Exception. ", e);
		}
	}

	/**
	 * 批量添加加班申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateLeaveApplyInBatch(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap map = (LinkedHashMap) list.get(i);
				LinkedHashMap tempMap = new LinkedHashMap();
//				int essApplySeq = getEssApplySeq();
				String applyTypeCode = "";
				
				if (map != null && map.get("PARAM_MAP") != null) {
					LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
					this.insert("ess.infoApplyLeave.updateApplyLeave", obj);
					tempMap.put("CREATED_BY", obj.get("CREATED_BY"));
					tempMap.put("APPLY_TYPE_NO", obj.get("APPLY_TYPE_NO"));
					tempMap.put("APPLY_NO_SEQ", obj.get("APPLY_NO"));
					applyTypeCode = (null == obj.get("APPLY_TYPE_CODE") ? "" : obj.get("APPLY_TYPE_CODE").toString());
					obj.put("APPLY_NO_SEQ", obj.get("APPLY_NO"));
					
					//先删除附件
					LinkedHashMap fileMapDel = new LinkedHashMap();
					fileMapDel.put("APPLY_TYPE", APPLY_TYPE_NO);
					fileMapDel.put("APPLY_NO", obj.get("APPLY_NO"));
					this.insert("ess.infoApplyLeave.deleteEssFile",fileMapDel);
					//保存附件
					if (obj.get("FILE_NAME") != null && !"".equals(StringUtil.checkNull(obj.get("FILE_NAME")))) {
						String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME")).split(";");
						String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(";");
						if (fileUrl != null && fileUrl.length > 0) {
							for (int j=0;j<fileUrl.length ;j++) {
								LinkedHashMap fileMap = new LinkedHashMap();
								fileMap.put("fileName", fileName[j]);
								fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("PERSON_ID") + "/" + fileUrl[j]);
								fileMap.put("APPLY_NO", obj.get("APPLY_NO"));
								fileMap.put("APPLY_TYPE", APPLY_TYPE_NO);
								fileMap.put("CREATED_BY", obj.get("CREATED_BY"));
								this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
							}
						}
					}
					
					//提交发送LGEP
					if("0".equals(obj.get("AFFIRM_FLAG").toString())){
						sendToLGEPInsert(obj);
					}
				}
//				先删除决策者
				this.delete("ess.infoApplyLeave.deleteApplyReviewer",tempMap);
				if (map != null && map.get("DISTINCT_LIST") != null) {
					List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
							tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							tempMap.put("APPLY_TYPE_NO", applyTypeCode);
							this.insert("ess.infoApplyLeave.insertApplyReviewer",tempMap);
						}
					}
				}
			}
		}
	}

	@Override
	public List getLeaveAffirmBatchTempInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getLeaveAffirmBatchTempInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getLeaveAffirmBatchTempInfoListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApplyLeave.getLeaveAffirmBatchTempInfoListCnt", obj)),Integer.class);
	}

	/**
	 * 删除休假临时表
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delTempLeaveApply(Object object) throws Exception {
		
		try {
			this.delete("ess.infoApplyLeave.delTempLeaveApply", object);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

	@Override
	public void updateEssLeaveApplyTbTemp(LinkedHashMap leaveMap)
			throws Exception {
		
		this.update("ess.infoApplyLeave.updateEssLeaveApplyTbTemp", leaveMap);
	}

	@Override
	public void addLeaveApplyInBatchNew(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap map = (LinkedHashMap) list.get(i);
				LinkedHashMap tempMap = new LinkedHashMap();
				int essApplySeq = getEssApplySeq();
				String applyTypeCode = "";
				
				if (map != null && map.get("PARAM_MAP") != null) {
					LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
					obj.put("APPLY_NO_SEQ", essApplySeq);
					this.insert("ess.infoApplyLeave.insertApplyLeave", obj);
//					删除临时表
					this.delete("ess.infoApplyLeave.delTempLeaveApplyByApplyNo", obj);
					tempMap.put("CREATED_BY", obj.get("CREATED_BY"));
					tempMap.put("APPLY_TYPE_NO", obj.get("APPLY_TYPE_NO"));
					tempMap.put("APPLY_NO_SEQ", essApplySeq);
					applyTypeCode = (null == obj.get("APPLY_TYPE_CODE") ? "" : obj.get("APPLY_TYPE_CODE").toString());
				}

				if (map != null && map.get("DISTINCT_LIST") != null) {
					List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
							tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							tempMap.put("APPLY_TYPE_NO", applyTypeCode);
							this.insert("ess.infoApplyLeave.insertApplyReviewer",tempMap);
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delLeaveApplyInBatch(List list, LinkedHashMap personMap)
			throws Exception {
		try {
			for(int i=0;i<list.size();i++){
			
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				
				 String ITEM_NO =obj.get("ITEM_NO")!= null ? obj.get("ITEM_NO").toString():"";
				 String PK_NO = this.getLeaveApplyPkNoNomal(obj);//获取到正常出勤的PK_NO
				 obj.put("PK_NO", PK_NO);
				 if ("141440".equals(ITEM_NO)||"141441".equals(ITEM_NO)||"141442".equals(ITEM_NO)||"141443".equals(ITEM_NO)||"14013783".equals(ITEM_NO)) {
					//删除detail表中的申请数据
					 this.delete("ess.infoApplyLeave.delTempLeaveApply", obj);
					 if (!"0".equals(PK_NO)) {
						 obj.put("PK_NO", PK_NO);
						 this.update("ess.infoApplyLeave.updateArDetailForNullFlag", obj);
					 }else {
						 //找不到相应的正常出勤，就插入一条数据
						 if("TSTO".equals(obj.get("CPNY_ID"))){
						   this.insert("ess.infoApplyLeave.callArDetailFor141439", obj);
						 }else {
						   this.insert("ess.infoApplyLeave.callArDetailFor141439SST", obj);
						}
					 }
				 }else if("".equals(ITEM_NO)){
					 //添加的null数据直接删除
					 this.delete("ess.infoApplyLeave.delTempLeaveApply", obj);
				 }else {
					 
					//申请的表的apply_no不为空的话执行
					 String APPLY_NO_FOR_APPLY = getArDetailTstoApplyOfApplyNoForApplyNo(obj);//获取APPLY_NO
					 if( !"0".equals(APPLY_NO_FOR_APPLY)){
						 obj.put("APPLY_NO_FOR_APPLY", APPLY_NO_FOR_APPLY);
						 this.delete("ess.infoApplyLeave.updateApplyLeaveTbForNullFlag", obj);
						 this.delete("ess.infoApplyLeave.deleteEssAffim", obj);//删除决裁表的决裁信息
					 }
					 
                     if ("TSTO".equals(obj.get("CPNY_ID"))) {
						 if (!obj.get("FROM_DATE").equals(obj.get("TO_DATE")) ) {
							 this.insert("ess.infoApplyLeave.callArDetailForDeleteDaySaEN", obj);//删除多天的数据
						 }else {
							 this.delete("ess.infoApplyLeave.delTempLeaveApply", obj);//删除detail申请数据
							//恢复数据
							 if (!"0".equals(PK_NO)) {
							     this.update("ess.infoApplyLeave.updateArDetailForNullFlag", obj);
							 }else {
								//找不到相应的正常出勤，就插入一条数据
								 if("TSTO".equals(obj.get("CPNY_ID"))){
								   this.insert("ess.infoApplyLeave.callArDetailFor141439", obj);
								 }else {
								   this.insert("ess.infoApplyLeave.callArDetailFor141439SST", obj);
								 }
							 }
						 }
					 }else {
						 if (!obj.get("FROM_DATE").equals(obj.get("TO_DATE")) ) {
							       this.insert("ess.infoApplyLeave.callArDetailForDeleteDaySSTStaEnd", obj);//SST删除多天的数据
						 }else {
							       this.delete("ess.infoApplyLeave.delTempLeaveApply", obj);//删除detail申请数据
							   //恢复数据
							 if (!"0".equals(PK_NO)) {
							       this.update("ess.infoApplyLeave.updateArDetailForNullFlag", obj);
							 }else {
								//找不到相应的正常出勤，就插入一条数据
								   this.insert("ess.infoApplyLeave.callArDetailFor141439SST", obj);
							 }
						 }
					 }
					 
				}
				//当删除倒休的时候，要恢复
				 if("TSTO".equals(obj.get("CPNY_ID"))){
					 if ("14013845".equals(ITEM_NO)) {
                        if(!"".equals(obj.get("reason").toString())&&obj.get("reason").toString()!=null){
						 String AJ_DATE = obj.get("reason").toString().substring(1,obj.get("reason").toString().length()-2 );
						 obj.put("AJ_DATE", AJ_DATE);
                        }
                        String APPLY_LENGTH = obj.get("APPLY_LENGTH").toString();
                        List dataList= this.getAdjustListForHuiFu(obj);
                        LinkedHashMap ajMap= (LinkedHashMap) dataList.get(0);
                        String adjustFlag = StringUtil.checkNull(ajMap.get("ADJUST_FLAG").toString());
                        String QUANTITY =  StringUtil.checkNull(ajMap.get("QUANTITY").toString());
                        if (QUANTITY.equals(APPLY_LENGTH)&&"1".equals(adjustFlag)) {
							obj.put("ADJUST_FLAG", "0");
						}else if(!QUANTITY.equals(APPLY_LENGTH)&&"1".equals(adjustFlag)) {
							obj.put("ADJUST_FLAG", "2");
						}else {
							obj.put("ADJUST_FLAG", "0");
						}
						 this.update("ess.infoApplyLeave.updateAdjustFlagDelete", obj);
					 }
				 }
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	/**
	 * 获取休假导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssLeaveTempList(Object object){
		return this.getEssLeaveTempList(object, -1, -1);
	}
	
	/**
	 * 获取休假导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssLeaveTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.infoApplyLeave.getEssLeaveTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.infoApplyLeave.getEssLeaveTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	public List getAdjustListForHuiFu(Object object){
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("ess.infoApplyLeave.getAdjustListForHuiFu", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取休假导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssLeaveTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.infoApplyLeave.getEssLeaveTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取出错的临促导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssLeaveTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.infoApplyLeave.getEssLeaveTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfo(Object obj) throws Exception {
		Map map = new LinkedHashMap();
		try {
			map = (Map) this.queryForObject(
					"ess.infoApplyLeave.getEmpVacInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取SST员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfoSST(Object obj) throws Exception {
		Map map = new LinkedHashMap();
		try {
			map = (Map) this.queryForObject(
					"ess.infoApplyLeave.getEmpVacInfoSST", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取SST员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfoSSTForDisplay(Object obj) throws Exception {
		Map map = new LinkedHashMap();
		try {
			map = (Map) this.queryForObject(
					"ess.infoApplyLeave.getEmpVacInfoSSTForDisplay", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 剩余调休数 2014-07-26 jiahch
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getTiaoxiu(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApplyLeave.getTiaoxiu", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2 ;
	}

	/**
	 * 剩余移年年假 2014-07-26 jiahch
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getYiniannianjia(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApplyLeave.getYiniannianjia", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2 ;
	}


	/**
	 * 获取考勤年
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getCurrentYear(Object obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.getCurrentYear", obj));
		if(object2 ==null){
			object2 = DateUtil.getCurrentYearStr();
		}
		return object2 ;
	}
	
	/**
	 * 剩余福利年假数 2014-07-26 jiahch
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getRestFulinianjia(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApplyLeave.getRestFulinianjia", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2 ;
	}

	/**
	 * 剩余法定年假数 2014-07-26 jiahch
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getRestFadingnianjia(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApplyLeave.getRestFadingnianjia", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2 ;
	}
	
	/**
	 *休假申请详细信息信息列表(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getLeaveBatchAffirmInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getLeaveBatchAffirmInfoList(obj, -1, -1);
		return returnList;
	}
	/**
	 *休假申请详细信息信息列表(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtBatchAffirmInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getOtBatchAffirmInfoList(obj, -1, -1);
		return returnList;
	}

	
	/**
	 * 休假申请详细信息信息-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getLeaveBatchAffirmInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.infoApplyLeave.getEssLeaveBatchList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.infoApplyLeave.getEssLeaveBatchList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 加班申请详细信息信息-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtBatchAffirmInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.infoApplyLeave.getEssOtBatchList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.infoApplyLeave.getEssOtBatchList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}


	/**
	 * 休假申请详细信息列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getLeaveBatchAffirmInfoListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApplyLeave.getEssLeaveBatchCnt", obj)),Integer.class);
	}

	/**
	 * 休假申请详细信息列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOtBatchAffirmInfoListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApplyLeave.getEssOtBatchCnt", obj)),Integer.class);
	}
	
	/**
	 * 批量提交休假申请
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int submitLeaveApplyInBatch(List list) throws Exception {
		
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					LinkedHashMap obj = (LinkedHashMap) list.get(i);
					int essApplySeq = getARDetailApplySeq();
					obj.put("APPLY_NO_SEQ", essApplySeq);
					this.insert("ess.infoApplyLeave.submitTempLeaveApply", obj);
				}
			}
		return 1;
	}
	/**
	 * 批量提交休假申请
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addtLeaveApplyInBatch(LinkedHashMap obj) throws Exception {
		
		
		int essApplySeq = getARDetailApplySeq();
		obj.put("APPLY_NO_SEQ", essApplySeq);
		obj.put("CONFIRM_FLAG", 0);
		this.insert("ess.infoApplyLeave.addtLeaveApplyInBatch", obj);
		return 1;
	}
	/**
	 * 批量添加加班申请
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addtOtApplyInBatch(LinkedHashMap obj) throws Exception {
		
		
		int essApplySeq = getARDetailApplySSTSeq();
		obj.put("APPLY_NO_SEQ", essApplySeq);
		obj.put("CONFIRM_FLAG", 0);
		this.insert("ar.detail.addtOtApplyInBatch", obj);
		return 1;
	}
	
	/**
	 * 批量添加加班申请
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addtOtTSTOApplyInBatch(LinkedHashMap obj) throws Exception {
		
		
		int essApplySeq = getARDetailApplySeq();
		obj.put("APPLY_NO_SEQ", essApplySeq);
		obj.put("CONFIRM_FLAG", 0);
		this.insert("ar.detail.addtOtApplyInBatch", obj);
		return 1;
	}
	/**
	 * 批量添加倒休
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addtOtApplyInBatchTSTO(LinkedHashMap obj) throws Exception {
		
		
		int essApplySeq = getARDetailApplySeq();
		obj.put("APPLY_NO_SEQ", essApplySeq);
		obj.put("CONFIRM_FLAG", 0);
		this.insert("ar.detail.addtOtApplyInBatch", obj);
		return 1;
	}
	/**
	 * 批量添加倒休
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addtAdjustApplyInBatchTSTO(LinkedHashMap obj) throws Exception {
		int essApplySeq = getARDetailApplySeq();
		obj.put("APPLY_NO_SEQ", essApplySeq);
		this.insert("ar.detail.addtAdjustApplyInBatchTSTO", obj);
		return 1;
	}
	/**
	 * 批量部门长批准后
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatSubmitLeaveApplyInBatch(List list) throws Exception {
		
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.update("ess.infoApplyLeave.updatSubmitLeaveApplyInBatch", obj);
			}
		}
		return 1;
	}
	/**
	 * 批量部门批准后--二次申请
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatSubmitArDetailBatchForTwoApply(LinkedHashMap  obj) throws Exception {
		
		  if ("TSTO".equals(obj.get("CPNY_ID"))) {
			  if (!obj.get("OLD_TO_DATE").equals(obj.get("TO_DATE"))  ) {
				  this.insert("ess.infoApplyLeave.callArDetailForUpdateDaySaEN", obj);
			  }else {
				  if(!obj.get("FROM_DATE").equals(obj.get("TO_DATE"))){
					  this.update("ess.infoApplyLeave.updateArDetailMoreDayAffrim", obj);
				  }else {
					  this.update("ess.infoApplyLeave.updatSubmitArDetailBatchForTwoApply", obj);
				  }
			  }
		  }else {
			  //SST
			  if (!obj.get("OLD_TO_DATE").equals(obj.get("TO_DATE"))  ) {
				  if (obj.get("OLD_FROM_DATE").equals(obj.get("FROM_DATE"))) {//固定开始日期，延长缩短
					  this.insert("ess.infoApplyLeave.callArDetailForUpdateDaySSTStarEND", obj);//多天假的基础进行延长或者缩短操作的
				  }
				  if (obj.get("OLD_TO_DATE").equals(obj.get("TO_DATE"))) {//固定结束日期,缩短开始日期
					  this.insert("ess.infoApplyLeave.callArDetailForUpdateFromoEndDayonly", obj);
				  }
			  }else {
				  if(!obj.get("FROM_DATE").equals(obj.get("TO_DATE"))){
					  this.update("ess.infoApplyLeave.updateArDetailMoreDayAffrim", obj);//针对多天的假进行批量修改
				  }else {
					  //判断改成同一天之前的假期是多天还是同一天
					  if(obj.get("OLD_FROM_DATE").equals(obj.get("OLD_TO_DATE"))){
					  this.update("ess.infoApplyLeave.updatSubmitArDetailBatchForTwoApply", obj);//SST
					  }else {
					  this.insert("ess.infoApplyLeave.callArDetailForUpdateFromoEndDayonly", obj);
					}
				  }
			  }
		  }
		  
		return 1;
	}
	/**
	 * 
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatSubmitArDetailBatchForNullPersonId(LinkedHashMap obj) throws Exception {
				String ITEM_NO = (String) obj.get("ITEM_NO");
				if (!"141440".equals(ITEM_NO)&&!"141441".equals(ITEM_NO)&&!"141442".equals(ITEM_NO)&&!"141443".equals(ITEM_NO)&&!"14013783".equals(ITEM_NO)) {
					String APPLY_NO = getARDetailApplyNo(obj);
					obj.put("APPLY_NO_FOR_ESS_APP",APPLY_NO);
				}
				this.update("ess.infoApplyLeave.updatSubmitArDetailBatchForNullPersonId", obj);
		return 1;
	}
	/**
	 * 
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatSubmitLeaveApplyInBatchForDeletePrepare(LinkedHashMap obj) throws Exception {
		
				this.update("ess.infoApplyLeave.updatSubmitLeaveApplyInBatchForDeletePrepare", obj);
		
		return 1;
	}
	/**
	 * 
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatSubmitLeaveApplyInBatchForDeletePrepareOnPersonid(LinkedHashMap obj) throws Exception {
				this.update("ess.infoApplyLeave.updatSubmitLeaveApplyInBatchForDeletePrepareOnPersonid", obj);
		return 1;
	}
	/**
	 * 批量提交修改的休假申请
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatSubmitLeaveApplyInBatchLess8(List list) throws Exception {
		
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.update("ess.infoApplyLeave.updatSubmitLeaveApplyInBatchLess8", obj);
			}
		}
		return 1;
	}
	/**
	 * 批量提交修改的休假申请(走审批)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatSubmitLeaveApplyInShenPiBatch(List list) throws Exception {
		
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.update("ess.infoApplyLeave.updatSubmitLeaveApplyInShenPiBatch", obj);
			}
		}
		return 1;
	}
	/**
	 * 否决或者取消
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatSubmitLeaveApplyInCancleBatch(List list) throws Exception {
		
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.update("ess.infoApplyLeave.updatSubmitLeaveApplyInCancleBatch", obj);
			}
		}
		return 1;
	}
	/**
	 * 批量提交的休假申请
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int insertSubmitLeaveApplyInBatch(LinkedHashMap obj) throws Exception {
		        int APPLY_NO_SEQ; 
				String APPLY_NO = this.getARDetailApplyNo(obj);
				if ("TSTO".equals(obj.get("CPNY_ID"))) {
					 APPLY_NO_SEQ = this.getARDetailApplySeq();
				}else {
					 APPLY_NO_SEQ = this.getARDetailApplySeqSST();
				}
				obj.put("APPLY_NO_SEQ",APPLY_NO_SEQ);
				obj.put("APPLY_NO_FOR_ARDETAIL",APPLY_NO);
				this.insert("ess.infoApplyLeave.insertSubmitLeaveApplyInBatch", obj);
		return 1;
	}
	
	private String  getARDetailApplyNo(Object obj) throws Exception {
		try {
			return  (String) queryForObject(("ess.infoApplyLeave.getARDetailApplyNo"),
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}	
	/**
	 * 获取员工性别
	 * @param map
	 * @return
	 */
	public String getSex(LinkedHashMap map) {

		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getSex",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return (null == obj ? "0" : obj.toString());	
	}
	/**
	 * 判断是否TA法人试用期
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List isProbation(Object obj) throws Exception {
		try {
			return this.queryForList("ess.infoApplyLeave.isProbation", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List getConfrimFlag(LinkedHashMap obj) throws Exception {
		List returnList = new ArrayList();
		try {
			return this.queryForList("ess.infoApplyLeave.getConfrimFlag", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEPInsert(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO_SEQ"));
			lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_TITLE", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_EMPID", paramMap.get("PERSON_ID"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewLeaveAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO_SEQ"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewLeaveAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO_SEQ"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}
	
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEPInsert(List list){
		for(int i=0;i<list.size();i++){
			LinkedHashMap paramMap = (LinkedHashMap)list.get(i);
			List LeaveList = this.getLeaveAffirmInfo2List(paramMap);
			LinkedHashMap lgepMap = (LinkedHashMap)LeaveList.get(0);
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", lgepMap.get("APPLY_NO"));
			lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_TITLE", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_EMPID", lgepMap.get("PERSON_ID"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewLeaveAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + lgepMap.get("APPLY_NO"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewLeaveAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + lgepMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + lgepMap.get("APPLY_NO"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", lgepMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", lgepMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
		}
	}
	

	/**
	 * 查询附件
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEssFileList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApplyLeave.getEssFileList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 查询附件
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPhotoFileList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApplyLeave.getPhotoFileList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object queryEmployeePhoto(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.queryEmployeePhoto",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List queryEmployeePhoto_name(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApplyLeave.queryEmployeePhoto_name",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 插入图片
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int  updatePath(Object obj) {
		try {
				this.update("ess.infoApplyLeave.updatePath", obj);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 插入图片
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int  updatePathName(Object obj) {
		try {
				this.update("ess.infoApplyLeave.updatePathName", obj);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 员工班次结束时间查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	@Override
	public Object getShiftTime(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApplyLeave.getShiftTime",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	/**
	 * 获取考勤月
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getCurrentMonth(Object obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.getCurrentMonth", obj));
		if(object2 ==null || "".equals(object2)){
			object2 = DateUtil.getCurrentYearStr();
		}
		return object2 ;
	}
	

	/**
	 * 休假验证 
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String valLeaveLength(Object obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.valLeaveLength", obj));
		if(object2 == null || "".equals(object2)){
			object2 = "OK";
		}
		return object2 ;
	}

	/**
	 * 休假最大时长验证 
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String valLeaveMaxLength(Object obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.valLeaveMaxLength", obj));
		if(object2 == null || "".equals(object2)){
			object2 = "OK";
		}
		return object2 ;
	}
	

	/**
	 * 休假时长单位验证 
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String valLeaveLengthUtil(Object obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.valLeaveUtilLength", obj));
		if(object2 == null || "".equals(object2)){
			object2 = "OK";
		}
		return object2 ;
	}
	
	/**
	 * 获取考勤年开始结束日期限制
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getCurrentYearVacLimit(Object obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.getCurrentYearVacLimit", obj));
		return object2 ;
	}
	
	/**
	 * 获取考勤年开始结束日期限制
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String isFullOneYear(Object obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.isFullOneYear", obj));
		return object2 ;
	}
	public void addXiaojiaLeaveApplyInfoNoAffirm(Map map) throws Exception {
		map.put("ADMIN_ID", map.get("supervisorPersonId"));
		LinkedHashMap leaveApply = (LinkedHashMap) this.queryForObject(
				"ess.humanAffirm.getLeaveApplyInfoByApplyNo", map);
		leaveApply.put("CPNY_ID", map.get("interCpnyID"));
		leaveApply.put("LANGUAGE", map.get("interLanguage"));
		if (null != leaveApply.get("APPLY_TYPE")
				&& "BATCH".equals(leaveApply.get("APPLY_TYPE"))) {
			this.doBatchInsertArApplyResult(leaveApply);
		} else {
			this.doInsertArApplyResult(leaveApply);
		}
		this.update("ess.affirmLeaveApply.updateEssApplyLeaveByApplyNoNoAffirm",
				leaveApply);
	}
	

	public void cancelLeaveApplyNoAffirm(Map map) throws Exception {
		map.put("ADMIN_ID", map.get("supervisorPersonId"));
		LinkedHashMap leaveApply = (LinkedHashMap) this.queryForObject(
				"ess.humanAffirm.getLeaveApplyInfoByApplyNoBatch", map);
		leaveApply.put("CPNY_ID", map.get("interCpnyID"));
		leaveApply.put("LANGUAGE", map.get("interLanguage"));


		//先删除追溯表
		this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonIdZhuisu",leaveApply);
		this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",leaveApply);
		if(!this.isAfterLeave(leaveApply)){
			// 插入新的的AR_SHIFT_CHANGE的数据
			leaveApply.put("CHANGE_TYPE", "休假销假申请(BATCH)");
			this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfter",leaveApply);
		}
		
		this.update("ess.affirmLeaveApply.updateEssApplyLeaveBatchNoAffirm",
				leaveApply);
	}
	
	/**
	 * 审批通过并完成，插入ar_apply_result
	 * 
	 * @param paramMap
	 * @return
	 */
	private void doInsertArApplyResult(LinkedHashMap leaveApply)
			throws Exception {
			
			//先删除追溯表
			this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonIdZhuisu",leaveApply);
			this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",leaveApply);
			if(!this.isAfterLeave(leaveApply)){
				// 插入新的的AR_SHIFT_CHANGE的数据
				leaveApply.put("CHANGE_TYPE", "休假销假申请");
				this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfter",leaveApply);
			}
	}
	

	/**
	 * 批量申请审批通过并完成，插入ar_apply_result
	 * 
	 * @param paramMap
	 * @return
	 */
	private void doBatchInsertArApplyResult(LinkedHashMap leaveApply)
			throws Exception {
		//先删除追溯表
		this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonIdZhuisuBatch",leaveApply);
		this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonIdBatch",leaveApply);
		this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfterBatch",leaveApply);
	}
	/**
	 * 判断是否为事后申请休假 overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	private boolean isAfterLeave(LinkedHashMap paramMap) throws Exception {
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");

		String leaveApplyFrom =  StringUtil.checkNull(paramMap.get("AR_FROM_TIME")).substring(0, 10);
		String sysDateStr = sb.format(date);

		GregorianCalendar applyFrom = DateUtil
				.ParseGregorianCalendar(leaveApplyFrom);
		GregorianCalendar sysDate = DateUtil.ParseGregorianCalendar(sysDateStr);
		if(leaveApplyFrom.equals(sysDateStr)){
			return true;
		}
		if (sysDate.before(applyFrom)) {
			return true;
		}
		return false;
	}
	

	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEPxiaojiaInsert(LinkedHashMap lgepMap){
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", lgepMap.get("APPLY_NO"));
			lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_TITLE", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_EMPID", lgepMap.get("PERSON_ID"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewLeaveAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + lgepMap.get("APPLY_NO") + "&unDoApplyNo=" + StringUtil.checkNull(lgepMap.get("UNDO_APPLY_NO")));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewLeaveAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + lgepMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + lgepMap.get("APPLY_NO") + "&unDoApplyNo=" + StringUtil.checkNull(lgepMap.get("UNDO_APPLY_NO")));
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", lgepMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", lgepMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}
	

	/**
	 * 发令leave
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addLeaveAssigmentInBatch(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap map = (LinkedHashMap) list.get(i);
				LinkedHashMap tempMap = new LinkedHashMap();
				int essApplySeq = getEssApplySeq();
				String applyTypeCode = "";
				
				if (map != null && map.get("PARAM_MAP") != null) {
					LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
					obj.put("APPLY_NO_SEQ", essApplySeq);
					this.insert("ess.infoApplyLeave.insertApplyLeave", obj);
					tempMap.put("CREATED_BY", obj.get("CREATED_BY"));
					tempMap.put("APPLY_TYPE_NO", obj.get("APPLY_TYPE_NO"));
					tempMap.put("APPLY_NO_SEQ", essApplySeq);
					applyTypeCode = (null == obj.get("APPLY_TYPE_CODE") ? "" : obj.get("APPLY_TYPE_CODE").toString());
					
					//保存附件
					if (obj.get("FILE_NAME") != null && !"".equals(StringUtil.checkNull(obj.get("FILE_NAME")))) {
						String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME")).split(";");
						String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(";");
						if (fileUrl != null && fileUrl.length > 0) {
							for (int j=0;j<fileUrl.length ;j++) {
								LinkedHashMap fileMap = new LinkedHashMap();
								fileMap.put("fileName", fileName[j]);
								fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("PERSON_ID") + "/" + fileUrl[j]);
								fileMap.put("APPLY_NO", essApplySeq);
								fileMap.put("APPLY_TYPE", APPLY_TYPE_NO);
								fileMap.put("CREATED_BY", obj.get("CREATED_BY"));
								this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
							}
						}
					}
					obj.put("APPLY_NO", essApplySeq);
					obj.put("APPLY_DATE", obj.get("APPLY_TIME"));
					obj.put("FROM_TIME", obj.get("LEAVE_FROM_TIME"));
					obj.put("TO_TIME", obj.get("LEAVE_TO_TIME"));
					// 删除之前的AR_APPLY_RESULT的数据
					this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",obj);
					// 插入新的的AR_APPLY_RESULT的数据
					this.insert("ess.humanAffirm.insertLeaveApplyResultForArApply",obj);

					obj.put("AR_FROM_TIME", StringUtil.checkNull(obj.get("LEAVE_FROM_TIME")).substring(0, 10));
					obj.put("AR_TO_TIME", StringUtil.checkNull(obj.get("LEAVE_TO_TIME")).substring(0, 10));
					if(!this.isAfterLeave(obj)){
						// 插入新的的AR_SHIFT_CHANGE的数据
						obj.put("CHANGE_TYPE", "休假申请");
						this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfter",obj);
					}
				}
			}
		}
	}
	
	
	/**
	 * 判断是否为追溯休假 overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	public boolean isZhuisuLeave(LinkedHashMap paramMap) throws Exception {
		paramMap.put("FLAG", "S");// 获取考勤月开始日期
		String arStartDateStr = this.getCurrentArDate(paramMap);
		paramMap.put("FLAG", "E");// 获取考勤月结束日期
		String arEndDateStr = this.getCurrentArDate(paramMap);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		String leaveApplyFrom = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap
				.get("LEAVE_FROM_TIME").toString()
				: sb.format(date);
		String leaveApplyTo = paramMap.get("LEAVE_TO_TIME") != null ? paramMap
				.get("LEAVE_TO_TIME").toString() : sb.format(date);
		GregorianCalendar applyFrom = DateUtil
				.ParseGregorianCalendar(leaveApplyFrom);
		GregorianCalendar applyTo = DateUtil
				.ParseGregorianCalendar(leaveApplyTo);
		GregorianCalendar arStartDate = DateUtil
				.ParseGregorianCalendar(arStartDateStr);
		GregorianCalendar arEndDate = DateUtil
				.ParseGregorianCalendar(arEndDateStr);
		if ("TSTO".equals(paramMap.get("CPNY_ID"))) {
			arStartDate.add(2, -1);
			arEndDate.add(2, -1);
			if (applyFrom.after(arStartDate) && applyTo.before(arEndDate)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 获取考勤年开始结束日期限制(年假基准日期)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String isFullOneYearYY(Object obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.isFullOneYearYY", obj));
		return object2 ;
	}
	
	/**
	 * 追溯申请验证
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String arValidLastMonth(Object obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.arValidLastMonth", obj));
		return object2 ;
	}
	
	@Override
	public int arMonthTime(Object obj) throws Exception {
		int object2 = (Integer) this.queryForObject("ess.infoApplyLeave.arMonthTime", obj);
		return object2 ;
	}
	
	/**
	 * 保存考勤异常信息
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveAttendanceApplyInfoForBatch(Map map) throws Exception {
		int returnInt = 0;
		List list = (List) map.get("attendanceApplyInfoList");
		Map paramMapo = (Map) map.get("paramMapo");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
			    LinkedHashMap personMap = (LinkedHashMap) list.get(i);
                	personMap.put("adminID", paramMapo.get("adminID"));
                	personMap.put("adminIP", paramMapo.get("adminIP"));
                	personMap.put("CPNY_ID", paramMapo.get("CPNY_ID"));
                	personMap.put("interLanguage", paramMapo.get("interLanguage"));
                	personMap.put("leaveTypeCode", personMap.get("LEAVE_TYPE_CODE"));
                	personMap.put("BATCH_NO", "");
                	
                	personMap.put("APPLY_LEAVE_FLAG", "1");
                	personMap.put("APPLY_TYPE_CODE",personMap.get("LEAVE_TYPE_CODE"));
                	personMap.put("interCpnyID", paramMapo.get("CPNY_ID"));
            		/*List itemParamList = ItemsDao.getItemParameterList(personMap);
            		float k = 0;
            		float n = 0;
            		if (itemParamList.size() > 0) {
            			LinkedHashMap itemMap = (LinkedHashMap)itemParamList.get(0);
            			k = Float.parseFloat( itemMap.get("MIN_VALUE").toString()); //最小值
            			n = Float.parseFloat( personMap.get("APPLY_LENGTH").toString()) ; //申请时长
            			k = itemMap.get("UNIT").equals("DAY") ? k*8 : k;
            			k = itemMap.get("UNIT").equals("MINUTE") ? k/60 : k;
            			
            		}
            		if (n % k != 0) { //时长不能小于最小值.!
            			return returnInt = 2;
            		}*/
            		
                	this.delete("ess.infoApplyLeave.delArApplyResult",personMap);
                	//添加考勤取消ar_shift_change
                	this.update("ess.infoApplyLeave.PR_DELETE_LEAVE_CONFIRM",personMap);
                	String dutyNo = (String) this.queryForObject("ess.infoApplyLeave.getDutyNoStr", personMap);
                	
                	//lipeng 2017/10/16
                	List affirmorList = this.infoApplySer.getAffirmorListByString("21",personMap.get("PERSON_ID").toString(),personMap.get("leaveTypeCode").toString(),personMap.get("APPLY_LENGTH").toString(),personMap.get("interLanguage").toString());		
                	
                	LinkedHashMap personMap1 = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(personMap);
            		if(affirmorList.size()<=0){
            			throw new Exception(personMap1.get("LOCAL_NAME") + " You Can't Apply Without the Approver");//没有审批者,不能申请
            		}
            		this.insert("ess.infoApplyLeave.updateLeaveApplyInfoForBatch",personMap);
                	
        			if(personMap.get("LEAVE_FROM_TIME").toString().equals(personMap.get("LEAVE_TO_TIME").toString())){
        				personMap.put("LAST_NAME", "Apply(" + personMap1.get("LOCAL_NAME") + ")[Date：" + personMap.get("LEAVE_FROM_DATE") + "]");
        			}else{
        				personMap.put("LAST_NAME", "Apply(" + personMap1.get("LOCAL_NAME") + ")[Date：" + personMap.get("LEAVE_FROM_DATE") + " ~ " + personMap.get("LEAVE_TO_DATE") + "]");
        			}
        			personMap.put("APPLY_PERSON_INFO", personMap1.get("LOCAL_NAME") + "/" + StringUtil.checkNull(personMap1.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(personMap1.get("DEPTNAME")));

        			//先插入申请人       			
        			personMap.put("APPLY_NO_SEQ", personMap.get("APPLY_NO"));
        			personMap.put("APPLY_TYPE_CODE", personMap.get("LEAVE_TYPE_CODE"));
        			personMap.put("AFFIRM_LEVEL", "0");
        			personMap.put("paramMapo", personMap.get("adminID"));
        			personMap.put("AFFIRMOR_ID", personMap.get("adminID"));
        			personMap.put("AFFIRM_TYPE", "4");
        			personMap.put("APPLY_TYPE_NO", "21");
        			personMap.put("APPLY_AFFIRM_FLAG", "14014306");
        			personMap.put("APPLY_FLAG", "0");
     
        			this.delete("ess.infoApply.deleteSyAffirmInfo", personMap);
        			this.insert("ess.infoApply.addSyAffirmInfo", personMap); 
        			
            		for(int j=0;j<affirmorList.size();j++){
            			if(!"".equals(affirmorList.get(j))){
            				LinkedHashMap map1 = (LinkedHashMap) affirmorList.get(j);
            				LinkedHashMap affirmMap = new LinkedHashMap();
            				personMap.put("AFFIRM_LEVEL", map1.get("AFFIRM_LEVEL"));
            				personMap.put("AFFIRMOR_ID", map1.get("AFFIRMOR_ID"));
            				personMap.put("PERSON_ID",personMap.get("APPLY_PERSON_ID"));
            				personMap.put("APPLY_TYPE_CODE",personMap.get("LEAVE_TYPE_CODE"));
            				personMap.put("AFFIRM_TYPE", "1");
            				this.insert("ess.infoApply.addSyAffirmInfo", personMap); 
            			}
            		}
                	
            	}
            	returnInt = 1;
			}
		return returnInt;
	}
	
	/**
	 * 保存考勤申请信息(可添加删除审批者)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveAttApplyInfoByAnyApproverForBatch(Map map) throws Exception {
		int returnInt = 0;
		List list = (List) map.get("attendanceApplyInfoList");
		Map paramMapo = (Map) map.get("paramMapo");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
			    LinkedHashMap personMap = (LinkedHashMap) list.get(i);
                	personMap.put("adminID", paramMapo.get("adminID"));
                	personMap.put("adminIP", paramMapo.get("adminIP"));
                	personMap.put("CPNY_ID", paramMapo.get("CPNY_ID"));
                	personMap.put("interLanguage", paramMapo.get("interLanguage"));
                	personMap.put("leaveTypeCode", personMap.get("LEAVE_TYPE_CODE"));
                	personMap.put("BATCH_NO", "");
                	this.delete("ess.infoApplyLeave.delArApplyResult",personMap);
                	//添加考勤取消ar_shift_change
                	this.update("ess.infoApplyLeave.PR_DELETE_LEAVE_CONFIRM",personMap);
                	String dutyNo = (String) this.queryForObject("ess.infoApplyLeave.getDutyNoStr", personMap);
                	
                	//String affirmorString = (String)personMap.get("affirmJsonData");
                	List<LinkedHashMap<String, Object>> affirmorList = (List)personMap.get("affirmJsonData");
                	//lipeng 2017/10/16
                	//List affirmorList = this.infoApplySer.getAffirmorListByString("21",personMap.get("PERSON_ID").toString(),personMap.get("leaveTypeCode").toString(),personMap.get("APPLY_LENGTH").toString(),personMap.get("interLanguage").toString());		
                	
                	LinkedHashMap personMap1 = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(personMap);
            		if(affirmorList.size()<=0){
            			throw new Exception(personMap1.get("LOCAL_NAME") + " You Can't Apply Without the Approver");//没有审批者,不能申请
            		}
            		this.insert("ess.infoApplyLeave.updateLeaveApplyInfoForBatch",personMap);
                	
        			if(personMap.get("LEAVE_FROM_TIME").toString().equals(personMap.get("LEAVE_TO_TIME").toString())){
        				personMap.put("LAST_NAME", "Apply(" + personMap1.get("LOCAL_NAME") + ")[Date：" + personMap.get("LEAVE_FROM_DATE") + "]");
        			}else{
        				personMap.put("LAST_NAME", "Apply(" + personMap1.get("LOCAL_NAME") + ")[Date：" + personMap.get("LEAVE_FROM_DATE") + " ~ " + personMap.get("LEAVE_TO_DATE") + "]");
        			}
        			personMap.put("APPLY_PERSON_INFO", personMap1.get("LOCAL_NAME") + "/" + StringUtil.checkNull(personMap1.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(personMap1.get("DEPTNAME")));

        			//先插入申请人       			
        			personMap.put("APPLY_NO_SEQ", personMap.get("APPLY_NO"));
        			personMap.put("APPLY_TYPE_CODE", personMap.get("LEAVE_TYPE_CODE"));
        			personMap.put("AFFIRM_LEVEL", "0");
        			personMap.put("paramMapo", personMap.get("adminID"));
        			personMap.put("AFFIRMOR_ID", personMap.get("adminID"));
        			personMap.put("AFFIRM_TYPE", "4");
        			personMap.put("APPLY_TYPE_NO", "21");
        			personMap.put("APPLY_AFFIRM_FLAG", "14014306");
        			personMap.put("APPLY_FLAG", "0");
     
        			this.delete("ess.infoApply.deleteSyAffirmInfo", personMap);
        			this.insert("ess.infoApply.addSyAffirmInfo", personMap); 
        			
            		for(int j=0;j<affirmorList.size();j++){
            			if(!"".equals(affirmorList.get(j))){
            				LinkedHashMap map1 = (LinkedHashMap) affirmorList.get(j);
            				LinkedHashMap affirmMap = new LinkedHashMap();
            				personMap.put("AFFIRM_LEVEL", map1.get("AFFIRM_LEVEL"));
            				personMap.put("AFFIRMOR_ID", map1.get("AFFIRMOR_ID"));
            				personMap.put("PERSON_ID",personMap.get("APPLY_PERSON_ID"));
            				personMap.put("APPLY_TYPE_CODE",personMap.get("LEAVE_TYPE_CODE"));
            				personMap.put("AFFIRM_TYPE", "1");
            				this.insert("ess.infoApply.addSyAffirmInfo", personMap); 
            			}
            		}
                	
            	}
            	returnInt = 1;
			}
		return returnInt;
	}
	
	/**
	 * 批量保存生产值考勤申请信息
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveAttendanceApplyInfoForBatchHAE(Map map) throws Exception {
		int returnInt = 0;
		/*为了批量申请但申请时间一样*/		
		Date nowDate = new Date();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    String applyTime = sdf.format(nowDate);
		
		List list = (List) map.get("attendanceApplyInfoList");
		List affirmorList = (List) map.get("affirmList");
		Map paramMapo = (Map) map.get("paramMapo");
		int batchNoSeq = getEssApplyBatchNoSeq();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
			    LinkedHashMap personMap = (LinkedHashMap) list.get(i);
                	personMap.put("adminID", paramMapo.get("adminID"));
                	personMap.put("adminIP", paramMapo.get("adminIP"));
                	personMap.put("CPNY_ID", paramMapo.get("CPNY_ID"));
                	personMap.put("interLanguage", paramMapo.get("interLanguage"));
                	personMap.put("leaveTypeCode", personMap.get("LEAVE_TYPE_CODE"));
                	/*2017/12/18 lipeng 为了同时审批*/
                	personMap.put("BATCH_NO", batchNoSeq);
                	personMap.put("APPLY_TIME", applyTime);                	
                	//this.delete("ess.infoApplyLeave.delArApplyResult",personMap);
                	//添加考勤取消ar_shift_change
                	//this.update("ess.infoApplyLeave.PR_DELETE_LEAVE_CONFIRM",personMap);
                	String dutyNo = (String) this.queryForObject("ess.infoApplyLeave.getDutyNoStr", personMap);
                	
                	//lipeng 2017/10/16
                	//List affirmorList = this.infoApplySer.getAffirmorListByString("21",personMap.get("PERSON_ID").toString(),personMap.get("leaveTypeCode").toString(),personMap.get("APPLY_LENGTH").toString(),personMap.get("interLanguage").toString());		
                	
                	LinkedHashMap personMap1 = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(personMap);
            		if(affirmorList.size()<=0){
            			throw new Exception("You Can't Apply Without the Approver");//没有审批者,不能申请
            		}
            		this.insert("ess.infoApplyLeave.updateLeaveApplyInfoForBatchSameTime",personMap);
                	
        			if(personMap.get("LEAVE_FROM_TIME").toString().equals(personMap.get("LEAVE_TO_TIME").toString())){
        				personMap.put("LAST_NAME", "Apply(" + personMap1.get("LOCAL_NAME") + ")[Date：" + personMap.get("LEAVE_FROM_DATE") + "]");
        			}else{
        				personMap.put("LAST_NAME", "Apply(" + personMap1.get("LOCAL_NAME") + ")[Date：" + personMap.get("LEAVE_FROM_DATE") + " ~ " + personMap.get("LEAVE_TO_DATE") + "]");
        			}
        			personMap.put("APPLY_PERSON_INFO", personMap1.get("LOCAL_NAME") + "/" + StringUtil.checkNull(personMap1.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(personMap1.get("DEPTNAME")));

        			//先插入申请人       			
        			personMap.put("APPLY_NO_SEQ", personMap.get("APPLY_NO"));
        			personMap.put("APPLY_TYPE_CODE", personMap.get("LEAVE_TYPE_CODE"));
        			personMap.put("AFFIRM_LEVEL", "0");
        			personMap.put("paramMapo", personMap.get("adminID"));
        			personMap.put("AFFIRMOR_ID", personMap.get("adminID"));
        			personMap.put("AFFIRM_TYPE", "4");
        			personMap.put("APPLY_TYPE_NO", "21");
        			personMap.put("APPLY_AFFIRM_FLAG", "14014306");
        			personMap.put("APPLY_FLAG", "0");
     
        			//this.delete("ess.infoApply.deleteSyAffirmInfo", personMap);
        			this.insert("ess.infoApply.addSyAffirmInfo", personMap); 
        			
            		for(int j=0;j<affirmorList.size();j++){
            			if(!"".equals(affirmorList.get(j))){
            				LinkedHashMap map1 = (LinkedHashMap) affirmorList.get(j);
            				LinkedHashMap affirmMap = new LinkedHashMap();
            				personMap.put("AFFIRM_LEVEL", map1.get("AFFIRM_LEVEL"));
            				personMap.put("AFFIRMOR_ID", map1.get("AFFIRMOR_ID"));
            				personMap.put("PERSON_ID",personMap.get("APPLY_PERSON_ID"));
            				personMap.put("APPLY_TYPE_CODE",personMap.get("LEAVE_TYPE_CODE"));
            				personMap.put("AFFIRM_TYPE", "1");
            				this.insert("ess.infoApply.addSyAffirmInfo", personMap); 
            			}
            		}
                	
            	}
            	returnInt = 1;
			}
		return returnInt;
	}
	
	public int PR_ADD_SHOP_SHIFT(Object object)throws Exception {
		List list = (List)object;
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
			    LinkedHashMap personMap = (LinkedHashMap) list.get(i);
                try {
                	personMap.put("message", "");
    				this.insert("ess.infoApplyLeave.PR_ADD_SHOP_SHIFT", personMap) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return 1;
	}
	
	/**
	 * 获得信息申请序列(get information apply sequences)
	 * 
	 * @param object
	 * @return
	 */
	private int getAnnualadjustmentApplySeq() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.macRecordApply.getArMacRecordApplySeq")),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveAttenanceExBatchInfo(Map map) throws Exception {
		List list = (List) map.get("attendanceApplyExInfoList");
		Map paramMapo = (Map) map.get("paramMapo");
		int AnnualadjustmentApplySeq = getAnnualadjustmentApplySeq();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap personMap = (LinkedHashMap) list.get(i);
				personMap.put("ID", AnnualadjustmentApplySeq);
            	personMap.put("adminID", paramMapo.get("adminID"));
            	personMap.put("adminIP", paramMapo.get("adminIP"));
            	personMap.put("CPNY_ID", paramMapo.get("CPNY_ID"));
            	personMap.put("interCpnyID", paramMapo.get("CPNY_ID"));
            	personMap.put("interLanguage", paramMapo.get("interLanguage"));
				LinkedHashMap tempMap = new LinkedHashMap();
            	LinkedHashMap postGrade = (LinkedHashMap) this.queryForObject("ess.infoApplyLeave.selectPostGrade", personMap);
				List affirmorList = this.infoApplySer.getAffirmorListByString("218197",personMap.get("PERSON_ID").toString(),"218197","0",personMap.get("interLanguage").toString());		
        		if(affirmorList.size()<=0){
        			throw new Exception(postGrade.get("LOCAL_NAME") + " You Can't Apply Without the Approver");//没有审批者,不能申请
        		}

            	LinkedHashMap delMap = new LinkedHashMap();
            	delMap.put("APPLY_NO", personMap.get("PK_NO").toString());
            	delMap.put("interCpnyID", personMap.get("interCpnyID").toString());
            	delMap.put("APPLY_TYPE", "218197");
            	this.update("ess.Annualadjustment.delArDetail",delMap);
            	//修改决裁表
				tempMap.put("LAST_NAME", " Abnormal Attendance Application(" + postGrade.get("LOCAL_NAME") + ")[DATE：" + ((String) personMap.get("AR_DATE_STR")).replaceAll("/",".") + "]");//考勤异常申请
				tempMap.put("APPLY_PERSON_INFO", postGrade.get("LOCAL_NAME") + "/" + StringUtil.checkNull(postGrade.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(postGrade.get("DEPTNAME")));
    			//插入新数据
				this.insert("ess.infoApplyLeave.insertEssCardApplyNotConfirm", personMap);
				tempMap.put("APPLY_TYPE_NO",  "218197");
				tempMap.put("APPLY_NO_SEQ", personMap.get("PK_NO").toString());
				tempMap.put("adminID", personMap.get("adminID"));
				tempMap.put("adminIP", personMap.get("adminIP"));
				tempMap.put("APPLY_TYPE_CODE", personMap.get("ITEM_NO"));
				tempMap.put("APPLY_FLAG", "1");
				tempMap.put("PERSON_ID",personMap.get("PERSON_ID"));
				tempMap.put("APPLY_AFFIRM_FLAG", "14014306");
				//先插入申请人
				tempMap.put("AFFIRM_LEVEL", "0");
				tempMap.put("AFFIRMOR_ID", personMap.get("adminID"));
				tempMap.put("AFFIRM_TYPE", "4");
				this.insert("ess.infoApply.addSyAffirmInfoForBatch", tempMap); 

				for(int j=0;j<affirmorList.size();j++){
        			if(!"".equals(affirmorList.get(j))){
        				LinkedHashMap map1 = (LinkedHashMap) affirmorList.get(j);
        				LinkedHashMap affirmMap = new LinkedHashMap();
        				tempMap.put("AFFIRM_LEVEL", map1.get("AFFIRM_LEVEL"));
        				tempMap.put("AFFIRMOR_ID", map1.get("AFFIRMOR_ID"));
        				tempMap.put("AFFIRM_TYPE", "1");
        				this.insert("ess.infoApply.addSyAffirmInfoForBatch", tempMap); 
        			}
        		}
			}
		}
	}
	
	/**
	 * 添加考勤异常信息
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addAttendanceApplyInfoForBatch(Map map) throws Exception {
		int essApplySeq = getEssApplySeq();
		LinkedHashMap obj = (LinkedHashMap) map.get("paramMapo");
		obj.put("APPLY_NO_SEQ",essApplySeq);
		this.insert("ess.infoApplyLeave.addAttendanceApplyInfoForBatch",obj);
	}
	
	/**
	 * 查询加班申请决裁信息列表(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getLeaveAffirmInfoBatchList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getLeaveAffirmInfoBatchList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 查询加班申请决裁信息-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getLeaveAffirmInfoBatchList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.infoApplyLeave.getLeaveAffirmInfoBatchList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.infoApplyLeave.getLeaveAffirmInfoBatchList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewAdjustRecords(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.viewAdjustRecords",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 加班申请决裁信息列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getLeaveAffirmInfoBatchListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApplyLeave.getLeaveAffirmInfoBatchListCnt", obj)),Integer.class);
	}
	

	/**
	 * HZ法人是否上传附件验证
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int isUploadFileForBatchLeave(List list) throws Exception {
		if(list != null && list.size() > 0){
			for(int i = 0 ; i < list.size(); i++){
				LinkedHashMap personMap = (LinkedHashMap)list.get(i);
				int num = NumberUtils.parseNumber(ObjectUtils.toString(
						this.queryForObject("ess.infoApplyLeave.isUploadFileForBatchLeave", personMap)),Integer.class);
				if(num > 0){
					return num;
				}
			}
		}
		return 0;
	}
	
	/**
	 * 判断PN法人工作日
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public String isValidDate(Object obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.isValidDate", obj));
		return object2 ;
	}
	

	/**
	 * LGEYT法人产期检查假判断
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override 
	public String VALID_482_LGEYT(Object obj) throws Exception {
		String object2 = "OK";
		object2 = StringUtil.checkNull( this.queryForObject(
					"ess.infoApplyLeave.VALIDCJJLGEYT", obj));
		return object2;
	}
	
	/**
	 * 跨月验证
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String arValidKuaYue(Object obj) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.arValidKuaYue", obj));
		return object2 ;
	}

	@Override
	public void deleteAllDataForAdd(LinkedHashMap paramMap) throws Exception {
		this.delete("ess.infoApplyLeave.deleteAllDataForAdd",paramMap);
		
	}

	@Override
	public int getAllYearaffairsHour(LinkedHashMap paramMap) throws Exception {
			return NumberUtils.parseNumber(ObjectUtils.toString(
		this.queryForObject("ess.infoApplyLeave.getAllYearaffairsHour", paramMap)),Integer.class);
	}
	@Override
	public int getAppliedHour(LinkedHashMap paramMap) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApplyLeave.getAppliedHour", paramMap)),Integer.class);
	}
	@Override
	public int getAppliedCount(LinkedHashMap paramMap) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApplyLeave.getAppliedCount", paramMap)),Integer.class);
	}

	@Override
	public String getArDetailItemNo(Map paramMap) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.getArDetailItemNo", paramMap));
		return object2 ;
	}
	@Override
	public String getArDetailItemNoBaseApplyCode(Map paramMap) throws Exception {
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApplyLeave.getArDetailItemNoBaseApplyCode", paramMap));
		return object2 ;
	}
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getAttenceArDetailForPkNo(LinkedHashMap worHashMap) {
		LinkedHashMap map = new LinkedHashMap();
		try {
			map = (LinkedHashMap) this.queryForObject(
					"ess.infoApplyLeave.getAttenceArDetailForPkNo", worHashMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	public LinkedHashMap getPersonInfoForPerson(LinkedHashMap worHashMap) {
		LinkedHashMap map = new LinkedHashMap();
		try {
			map = (LinkedHashMap) this.queryForObject(
					"ess.infoApplyLeave.getPersonInfoForPerson", worHashMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public int getApplyCountYN(LinkedHashMap map) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApplyLeave.getApplyCountYN", map)),Integer.class);
	}
	@Override
	public int getApplyLOCKYN(LinkedHashMap map) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApplyLeave.getApplyLOCKYN", map)),Integer.class);
	}
	@Override
	public int getApplyOTLOCKYN(LinkedHashMap map) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApplyLeave.getApplyOTLOCKYN", map)),Integer.class);
	}
	
	/**
	 * 休假验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getLeaveCheckSST(LinkedHashMap worHashMap) {
		String result = "OK";
		try {
			result = StringUtil.checkNull( this.queryForObject(
					"ess.infoApplyLeave.getLeaveCheckSST", worHashMap));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	
	/**
	 * 休假验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getLeaveCheckTSTO(LinkedHashMap worHashMap) {
		String result = "OK";
		try {
			result = StringUtil.checkNull( this.queryForObject(
					"ess.infoApplyLeave.getLeaveCheckTSTO", worHashMap));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	
	/**
	 * 加班验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOTCheckTSTO(LinkedHashMap worHashMap) {
		String result = "OK";
		try {
			result = StringUtil.checkNull( this.queryForObject(
					"ess.infoApplyLeave.getOTCheckTSTO", worHashMap));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	
	/**
	 * 倒休验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAdjustCheckTSTO(LinkedHashMap worHashMap) {
		String result = "OK";
		try {
			result = StringUtil.checkNull( this.queryForObject(
					"ess.infoApplyLeave.getAdjustCheckTSTO", worHashMap));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	
	/**
	 * 加班验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOverTimeCheckTSTO(LinkedHashMap worHashMap) {
		String result = "OK";
		try {
			result = StringUtil.checkNull( this.queryForObject(
					"ess.infoApply.getOverTimeCheckTSTO", worHashMap));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	
	/**
	 * 内务加班验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCoordOverTimeCheckTSTO(LinkedHashMap worHashMap) {
		String result = "OK";
		try {
			result = StringUtil.checkNull( this.queryForObject(
					"ess.infoApply.getCoordOverTimeCheckTSTO", worHashMap));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	
	/**
	 * 个人添加考勤申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addLeaveApplySST(LinkedHashMap map) throws Exception {
		LinkedHashMap tempMap = new LinkedHashMap();
		int essApplySeq = getEssApplySeq();
		String applyTypeCode = "";
				
		if (map != null && map.get("PARAM_MAP") != null) {
			LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
			obj.put("APPLY_NO_SEQ", essApplySeq);
			obj.put("APPLY_NO", essApplySeq);
			//插入待申请表
			this.insert("ess.infoApplyLeave.insertApplyLeaveSST", obj);
			//this.insert("ess.infoApplyLeave.PR_ADD_SHOP_SHIFT", obj) ;
/*			this.update("ess.infoApplyLeave.deleteHistoryArDetailSST", obj);
			//封装数据
			this.insert("ess.infoApplyLeave.insertSubmitLeaveApplyInBatchSST", obj);*/
			
			//修改决裁表
			LinkedHashMap personMap = (LinkedHashMap) map.get("personMap");
			if(obj.get("LEAVE_FROM_TIME").toString().equals(obj.get("LEAVE_TO_TIME").toString())){
				obj.put("LAST_NAME", " Application(" + personMap.get("LOCAL_NAME") + ")[Date：" + obj.get("LEAVE_FROM_TIME") + "]"); //申请
			}else{
				obj.put("LAST_NAME", " Application(" + personMap.get("LOCAL_NAME") + ")[Date：" + obj.get("LEAVE_FROM_TIME") + " ~ " + obj.get("LEAVE_TO_TIME") + "]");
			}
			obj.put("APPLY_PERSON_INFO", personMap.get("LOCAL_NAME") + "/" + StringUtil.checkNull(personMap.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(personMap.get("DEPTNAME")));

			//先插入申请人
			obj.put("AFFIRM_LEVEL", "0");
			obj.put("AFFIRMOR_ID", obj.get("adminID"));
			obj.put("AFFIRM_TYPE", "4");
			this.insert("ess.infoApply.addSyAffirmInfo", obj); 
			//医疗天数表
			/*if(obj.get("interCpnyID").equals("SPC_NJ")&&obj.get("APPLY_TYPE_CODE").equals("15821")){
				this.delete("ess.infoApplyLeave.deleteArMedicalInfo",obj);
				this.insert("ess.infoApplyLeave.addArMedicalInfo",obj);
			}*/
			//插入审批人
			if (map != null && obj.get("affirmList") != null) {
				List<LinkedHashMap> aList = (List) obj.get("affirmList");
				if (aList != null && aList.size() > 0) {
					for (LinkedHashMap parmers : aList) {
						obj.put("AFFIRM_LEVEL", parmers.get("AFFIRM_LEVEL"));
						obj.put("AFFIRMOR_ID", parmers.get("AFFIRMOR_ID"));
						obj.put("AFFIRM_TYPE", parmers.get("AFFIRM_TYPE"));
						this.insert("ess.infoApply.addSyAffirmInfo",obj);
					}
				}
			}

			//附件上传
			if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName"))) && !"".equals(essApplySeq) && !"1".equals(essApplySeq)) {
				String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
				String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
				if (fileUrl != null && fileUrl.length > 0) {
					for (int j=0;j<fileUrl.length ;j++) {
						LinkedHashMap fileMap = new LinkedHashMap();
						fileMap.put("fileName", fileName[j]);
						fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
						fileMap.put("APPLY_NO", essApplySeq);
						fileMap.put("APPLY_TYPE", "LEAVE_APPLY");
						fileMap.put("CREATED_BY", obj.get("adminID"));
						this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
					}
				}
			}
		}
	}
	
	/**
	 * 天津年假信息申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addVacInfo(LinkedHashMap map) throws Exception {
		LinkedHashMap tempMap = new LinkedHashMap();
		int essApplySeq = getEssApplySeq();
		map.put("APPLY_NO", essApplySeq);
		this.insert("ess.infoApplyLeave.insertVacInfo", map);
	}

	@Override
	public String getOldItemNo(LinkedHashMap paramMap) throws Exception {
		return  (String) this.queryForObject("ess.infoApplyLeave.getOldItemNo", paramMap);
	}
	@Override
	public String getOldAdjustFlag(LinkedHashMap paramMap) throws Exception {
		return  (String) this.queryForObject("ess.infoApplyLeave.getOldAdjustFlag", paramMap);
	}
	@Override
	public List getDayArData(LinkedHashMap paramMap) throws Exception {
		return  this.queryForList("ess.infoApplyLeave.getDayArData", paramMap);
	}
   
	@SuppressWarnings("unchecked")
	@Override
	public void insertSSTOtIsNull(Object obj) throws Exception {
				this.insert("ess.infoApplyLeave.insertSSTOtIsNull", obj);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void insertTSTOADJUSTIsNull(Object obj) throws Exception {
		this.insert("ess.infoApplyLeave.insertTSTOADJUSTIsNull", obj);
	}
	@Override
	public List getAttendanceTempList(LinkedHashMap paramMap) throws Exception {
		return  this.queryForList("ess.infoApplyLeave.getAttendanceTempList", paramMap);
	}
	@Override
	public int getAttendanceTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.infoApplyLeave.getAttendanceTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@Override
	public int getAttendanceTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.infoApplyLeave.getAttendanceTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 个人考勤明细查询(search ot info list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendancePersonalInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.viewAttendancePersonalInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 获得人员列表(get PersonList View)
	 * @param Map
	 * @param int
	 * @param int
	 * @return List
	 * @throws 
	 */
	@Override
	public List getPersonListView(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getPersonListView", paramMap, currentPage, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return returnList ;
	}
	
	@Override
	public int getPersonListViewCnt(Map paramMap) {
		// TODO Auto-generated method stub
		int resultCnt = 0;
		try {
			resultCnt = Integer.parseInt(this.queryForObject("ess.infoApplyLeave.getPersonListViewCnt", paramMap).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return resultCnt ;
	}
	
	/**
	 * 批量添加考勤申请(Attendance Apply Batch)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addAttendanceApplyInfoForBatchHAE(List paramList) throws Exception {
		Map paramMap = new LinkedHashMap();
		for(int i=0;i < paramList.size();i++){
			paramMap = (Map)paramList.get(i);
			paramMap.put("APPLY_NO_SEQ", getEssApplySeq());
			this.insert("ess.infoApplyLeave.addAttendanceApplyInfoForBatchHAE",paramMap);
		}	
	}
	
	/**
	 * 批量添加加班申请(Add OT Apply Batch)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addOTApplyInfoForBatchHAE(List paramList) throws Exception {
		Map paramMap = new LinkedHashMap();
		for(int i=0;i < paramList.size();i++){
			paramMap = (Map)paramList.get(i);
			paramMap.put("APPLY_NO_SEQ", getEssApplySeq());
			this.insert("ess.infoApplyLeave.addOTApplyInfoForBatchHAE",paramMap);
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewAddAttendanceApplyInfoForBatchHAE(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.viewAddAttendanceApplyInfoForBatchHAE",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewApplyAttenanceBatchInfoHAEList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.viewApplyAttenanceBatchInfoHAEList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 取得批量考勤详细申请信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getApplyAttenanceBatchInfoHAEDetail(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getApplyAttenanceBatchInfoHAEDetail",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获得批量信息申请序列(get information applyBatchNo sequences)
	 * 
	 * @param object
	 * @return
	 */
	private int getEssApplyBatchNoSeq() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.infoApplyLeave.getEssApplyBatchNoSeq")),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewApplyOTBatchInfoHAEList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApplyLeave.viewApplyOTBatchInfoHAEList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 取得批量加班详细申请信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getApplyOTBatchInfoHAEDetail(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.infoApplyLeave.getApplyOTBatchInfoHAEDetail",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
}