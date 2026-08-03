package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.AnnualadjustmentInfoDao;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.service.InfoApplySer;
import com.ait.hrm.dao.RecruitManageDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.MailManager;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
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
public class InfoApplyDaoImpl extends SqlMapClientSupport implements InfoApplyDao {
	
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	@Autowired
	private AnnualadjustmentInfoDao annualadjustmentInfoDao;
	@Autowired
	private InfoApplySer infoApplySer;
	@Autowired
	private RecruitManageDao recruitManageDao;
	private static String APPLY_TYPE_NO = "31";

	//合同邀请名称
	private static String APPLY_TYPE_NAME = "加班审批邀请";
	
	/**
	 * 员工信息查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	@Override
	public Object getPersonalInfoByPid(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApply.getPersonalInfoByPersonId",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	/**
	 * 根据person_id查询该员工上上月最后一天日期(get last day of last last month)
	 * @param obj
	 * @return object
	 */              
	@Override
	public String getLLastMonthLastDay(Object object) {
		String obj = "";
		try {
			obj = (String)this.queryForObject("ess.infoApply.getLLastMonthLastDay",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	@Override
	public Map getPersonIdShift(Object object) {
		Map obj = new HashMap();
		try {
			obj = (Map) this.queryForObject("ess.infoApply.getPersonIdShift",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	@Override
	public String getPersonIdWorkTime(Object object) {
		String obj = "";
		try {
			obj = (String)this.queryForObject("ess.infoApply.getPersonIdWorkTime",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
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
		String object2 = StringUtil.checkNull( this.queryForObject("ess.infoApply.arValidLastMonth", obj));
		return object2 ;
	}
	/**
	 * @param obj
	 * @return object
	 */
	public String otApplyCheckEndDate(Object object){
		String str = "";
		
		try{
			
		Object	obj = this.queryForObject("ess.infoApply.otApplyCheckEndDate",object);
		str=obj!=null?String.valueOf(obj):"";
		}catch(SQLException e){
			e.printStackTrace();	
		}
		return str;
		
	}
	/**
	 * 查询当前考勤开始日期
	 * 
	 * 2014-08-05  by:wangqiang
	 */
	public String getCurrentArDateOt(Object obj){
		
		String str = null;
		try{
			Object  result = this.queryForObject("ess.infoApply.getCurrentArDateOt",obj);
			str = result!=null?String.valueOf(result):str;
			
		}catch(SQLException e){
			 e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 查询当前考勤开始日期
	 * 
	 * 2014-08-05  by:wangqiang
	 */
	
	public String getCurrentArDate(Object obj){
		
		String str = null;
		try{
			Object  result = this.queryForObject("ess.infoApply.getCurrentArDate",obj);
			str = result!=null?String.valueOf(result):str;
			
		}catch(SQLException e){
			 e.printStackTrace();
		}
		return str;
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
	public List getOtApplyArMonthList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.getOtApplyArMonthList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	
	
	/**
	 * 查询批量加班申请决裁信息列表(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmInfoListBatch(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getOtAffirmInfoListBatch(obj, -1, -1);
		return returnList;
	}
	/**
	 * 查询加班申请原因(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmInfoListWhy(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getOtAffirmInfoListWhy(obj, -1, -1);
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
	public List getOtAffirmInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApply.getOtAffirmInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * Myhome 个人加班申请明细
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonalOtInfoDetailList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApply.getPersonalOtInfoDetailList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 加班个人搜索,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonOtApplyInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.getPersonOtApplyInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	public List getOtAffirmInfoListBatch(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			/*if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.infoApply.getOtAffirmInfoListBatchForCoord",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.infoApply.getOtAffirmInfoListBatchForCoord",obj);
			}*/
			returnList = this.queryForList("ess.infoApply.getOtAffirmInfoListBatchForCoord",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 批量调休与处理(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAdjustLeaveTSTOBatchList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.viewAdjustLeaveTSTOBatchList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 查询加班申请原因,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmInfoListWhy(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.infoApply.getOtAffirmInfoListWhy",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.infoApply.getOtAffirmInfoListWhy",obj);
			}
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
	public int getOtAffirmInfoListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApply.getOtAffirmInfoListCnt", obj)),Integer.class);
	}

	/**
	 * 查询是否按特殊设置审批者
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int isHasPersonAffirmSpecial(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApply.isHasPersonAffirmSpecial", obj)),Integer.class);
	}
	
	/**
	 * 查询是否按特殊设置个人审批者
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int isHasPersonAffirmSpecialPerson(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApply.isHasPersonAffirmSpecialPerson", obj)),Integer.class);
	}
	
	
	/**
	 * 查询是否按特殊个人设置审批者
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int isHasPersonAffirm(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApply.isHasPersonAffirm", obj)),Integer.class);
	}

	/**
	 * 加班申请决裁信息列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOtAffirmInfoListCntBatch(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApply.getOtAffirmInfoListCntBatch", obj)),Integer.class);
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
		return this.queryForList("ess.infoApply.getAffirmorListByPersonID", obj);
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
			return this.queryForObject("ess.infoApply.getPersonInfoByPersonId",object);
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
		return this.queryForList("ess.infoApply.getAffirmorListByDeptNo", obj);
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
		return this.queryForList("ess.infoApply.getAffirmorListByNormal",object);
	}
	/**
	 * 取申请时长
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public LinkedHashMap getOtApplyLength(LinkedHashMap map) {
		LinkedHashMap dataMap = new LinkedHashMap();
		try {
			
			if(map.get("CPNY_ID").equals("TSTO")){
				dataMap = (LinkedHashMap)this.queryForObject("ess.infoApply.countArDetailLength",map);
			}else {
				
			}
		
		} catch (SQLException e) {	
			dataMap.put("OT_HOUR", "0");
			dataMap.put("OT_MINUTE", "0");
			dataMap.put("HOUR", "0");
			e.printStackTrace();
		}
		if(dataMap==null){
			dataMap.put("OT_HOUR", "0");
			dataMap.put("OT_MINUTE", "0");
			dataMap.put("HOUR", "0");
			 
		}
		return dataMap;	
	}
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public Object getOtApplyDate(LinkedHashMap map) {
		String otDateString="";
		try {
			otDateString = (String) this.queryForObject("ess.infoApply.getOtApplyDate",map);
			
		} catch (SQLException e) {	
			
		}
		return otDateString;	
	}
	/**
	 * 取工作形态
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public LinkedHashMap getOtApplyWorkTime(LinkedHashMap map) {
		LinkedHashMap dataMap = new LinkedHashMap();
		try {
			dataMap = (LinkedHashMap)this.queryForObject("ess.infoApply.getOtApplyWorkTime",map);
		} catch (SQLException e) {	
			dataMap.put("FIRST_TIME", "08:00");
			dataMap.put("LAST_TIME", "17:00");
			e.printStackTrace();
		}
		if(dataMap==null){
			dataMap.put("FIRST_TIME", "08:00");
			dataMap.put("LAST_TIME", "17:00");
		}
		return dataMap;	
	}
	/**
	 * 取工作形态
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public LinkedHashMap getOtApplyWorkTimeU(LinkedHashMap map) {
		LinkedHashMap dataMap = new LinkedHashMap();
		try {
			dataMap = (LinkedHashMap)this.queryForObject("ess.infoApply.getOtApplyWorkTimeU",map);
		} catch (SQLException e) {	
			dataMap.put("FIRST_TIME", "08:00");
			dataMap.put("LAST_TIME", "17:00");
			e.printStackTrace();
		}
		if(dataMap==null){
			dataMap.put("FIRST_TIME", "08:00");
			dataMap.put("LAST_TIME", "17:00");
		}
		return dataMap;	
	}
	
	/**
	 * 取本考勤月申请总时长（不包括本次申请）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap getOtApplyLengthZong(LinkedHashMap map){
		LinkedHashMap dataMap = new LinkedHashMap();
		try {
		   dataMap = (LinkedHashMap)this.queryForObject("ess.infoApply.getOtApplyLengthZong",map);
		} catch (SQLException e) {	
			dataMap.put("OT_HOUR", "0");
			dataMap.put("OT_MINUTE", "0");
			dataMap.put("HOUR", "0");
			e.printStackTrace();
		}
		if(dataMap==null){
			dataMap.put("OT_HOUR", "0");
			dataMap.put("OT_MINUTE", "0");
			dataMap.put("HOUR", "0");
			 
		}
		return dataMap;	
	}
	
	/**
	 * 当天的加班申请是否已经有过--申请类型为时间长度
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List otApplyExsitByApplyDate(Object obj) throws Exception {
		LinkedHashMap object = (LinkedHashMap) obj;
		return this.queryForList("ess.infoApply.otApplyExsitByApplyDate",object);
	}
	
	/**
	 * 导入的加班申请是否已经有过--申请类型为时间长度L
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List otImportExsitByApplyDate(Object obj) throws Exception {
		LinkedHashMap object = (LinkedHashMap) obj;
		return this.queryForList("ess.infoApply.otImportExsitByApplyDate",object);
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
	public void addOvertimeApplyInBatch(List list,String type,String APPLY_NO) throws Exception {
	
		if(!"".equals(type)&&!"".equals(APPLY_NO)){
			LinkedHashMap tempMap = new LinkedHashMap();
			tempMap.put("APPLY_NO", APPLY_NO);
			this.delete("ess.infoApply.delOtApplyImportByApplyNoBatchOtAffirm", tempMap);
			this.delete("ess.infoApply.delOtApplyImportByApplyNoBatch", tempMap);
			this.delete("ess.infoApply.delOtApplyImportByApplyNoBatchOT", tempMap); 
		} 
		this.addOvertimeApplyInBatch(list);
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
	public void addOvertimeApplyInBatch(List list) throws Exception {
		if (list.size() > 0) {
			
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap map = (LinkedHashMap) list.get(i);
				LinkedHashMap  obj = (LinkedHashMap) map.get("PARAM_MAP");
				
				LinkedHashMap tempMap = new LinkedHashMap();
				int essApplySeq = getEssApplySeq();
				obj.put("APPLY_NO_SEQ", essApplySeq);
				obj.put("OT_FROM_TIME", obj.get("FROM_TIME"));
				obj.put("OT_TO_TIME", obj.get("TO_TIME"));
				obj.put("CREATED_IP", ((LinkedHashMap) obj).get("CREATED_IP"));
                //修改申请表
				this.insert("ess.infoApply.insertOvertimeApply", obj);
			/*	//修改明细表
				LinkedHashMap detailMap = this.getOtArDetailForPkNo(obj);
				 
				LinkedHashMap dataMap = new LinkedHashMap(); 
				dataMap.put("PK_NO",detailMap.get("PK_NO"));
				dataMap.put("OT_FROM_TIME",obj.get("FROM_TIME"));
				dataMap.put("OT_TO_TIME",obj.get("TO_TIME"));
				dataMap.put("otherReason",obj.get("APPLY_REMARK"));
				dataMap.put("AFFIRM_FLAG",obj.get("AFFIRM_FLAG"));
				dataMap.put("APPLY_DATE",obj.get("APPLY_OT_DATE"));
				dataMap.put("ITEM_NO",obj.get("ITEM_NO"));
				dataMap.put("ADJUST_YN",obj.get("ADJUST_YN"));
				SimpleDateFormat sdfDateFormat1=new SimpleDateFormat("yyyyMM");
			    Date AR_MONTH = sdfDateFormat1.parse(obj.get("APPLY_OT_DATE").toString().substring(0,4)+   obj.get("APPLY_OT_DATE").toString().substring(5,7));
			    Calendar calendar = Calendar.getInstance();//日历对象
			    calendar.setTime(AR_MONTH);//设置当前日期
			    calendar.add(Calendar.MONTH, 1);//月份加1
			    dataMap.put("AR_MONTH_STR", sdfDateFormat1.format(calendar.getTime()));
				dataMap.put("APPLY_LENGTH",obj.get("otLength"));
				dataMap.put("APPLY_NO_FOR_DETAIL",essApplySeq);
				dataMap.put("CREATED_BY",obj.get("CREATED_BY"));
				dataMap.put("CREATED_IP",obj.get("CREATED_IP"));
				dataMap.put("ACTIVITY",0);
				
				List dataList = this.getOtArDetailForMoreOt(obj);
				double ot = 0.00;
				LinkedHashMap objHashMap = new LinkedHashMap();
				LinkedHashMap objHashMap2 = new LinkedHashMap();
				for(int j=0;j<dataList.size();j++){
					//获取到加班的每条数据
					objHashMap = (LinkedHashMap) dataList.get(j);
					ot = +Double.parseDouble( objHashMap.get("QUANTITY").toString());
				
				}
				if (ot==0.00 ) {
					this.update("ess.infoApply.updatSubmitArDetailBatchApplyForPerson", dataMap);
				}else {
					objHashMap2 = (LinkedHashMap) dataList.get(0);
					objHashMap2.put("APPLY_LENGTH",obj.get("otLength"));
					objHashMap2.put("APPLY_DATE",obj.get("APPLY_OT_DATE"));
					objHashMap2.put("OT_FROM_TIME",obj.get("FROM_TIME"));
					objHashMap2.put("OT_TO_TIME",obj.get("TO_TIME"));
					objHashMap2.put("DEPTNO",objHashMap2.get("DEPT_NO"));
					objHashMap2.put("AFFIRM_FLAG",obj.get("AFFIRM_FLAG"));
					objHashMap2.put("ITEM_NO",obj.get("ITEM_NO"));
					objHashMap2.put("reason","");
					objHashMap2.put("otherReason",obj.get("APPLY_REMARK"));
					objHashMap2.put("allowance",objHashMap2.get("ALLOWANCE"));
					this.insertSubmitLeaveApplyInBatch(objHashMap2);
				}*/
				//修改决裁表
				tempMap.put("CREATED_BY", ((LinkedHashMap) obj).get("CREATED_BY"));
				tempMap.put("APPLY_TYPE_NO", ((LinkedHashMap) obj).get("OT_TYPE_CODE"));
				tempMap.put("APPLY_NO_SEQ", essApplySeq);
						
				if (map != null && map.get("DISTINCT_LIST") != null) {
					List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
							tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							tempMap.put("AFFIRM_COM_TYPE", parmers.get("AFFIRM_COM_TYPE"));
							this.insert("ess.infoApply.insertApplyReviewerPer",tempMap);
						}
					}
						
				}
					
			}
 		
		}
		
	}
	
	//查找明细表中加班的数据
	@SuppressWarnings("unchecked")
	public LinkedHashMap getOtArDetailForPkNo(LinkedHashMap worHashMap) {
		LinkedHashMap map = new LinkedHashMap();
		try {
			map = (LinkedHashMap) this.queryForObject(
					"ess.infoApply.getOtArDetailForPkNo", worHashMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}	
	//查找明细表中加班的数据
	@SuppressWarnings("unchecked")
	public List getOtArDetailForMoreOt(LinkedHashMap obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.getOtArDetailForMoreOt",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}	

	/**
		 * 批量加班添加数据(验证通过后--从临时表导入到正式表)
		 * @param 
		 * @return
		 * by:wangqiang
		 */
		@SuppressWarnings("unchecked")
		public String callInsertBatchOt(Object object)  throws Exception{
			String returnString = "" ;		
			try {
				LinkedHashMap paramMap = (LinkedHashMap)object;
				paramMap.put("message", "") ;
				this.insert("ess.infoApply.callInsertBatchOt", paramMap) ;			
				returnString = ObjectUtils.toString(paramMap.get("message")) ;
				List list = new ArrayList();
				if("F".equals(returnString)){
					throw new CommonException(TipMessage.getTipMessage("alert.message.ess.infoApply.notFor_PKG_ESS_OT_EXCEL_IMP_F", "zh")
						 );
				
				}
				
				LinkedHashMap paramMap2 = new LinkedHashMap();
				paramMap2.put("BATCH_APPLY_NO", returnString);
				list = this.queryForList("ess.infoApply.queryBatchApplyNo", (Object)paramMap2);
				String affirmFlag = paramMap.get("AFFIRM_FLAG")!=null?paramMap.get("AFFIRM_FLAG").toString():"0";
				 if(!"-1".equals(affirmFlag)){
					 this.sendToLGEPInsert(list);
				 }
			} catch (SQLException e) {	
				returnString = e.getMessage() ;			
				throw e;
			}
			return returnString ;
		}


 
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	@SuppressWarnings("unchecked")
	private void sendToLGEPInsert(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO_SEQ"));
			lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_TITLE", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_EMPID", paramMap.get("PERSON_ID"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewOtAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO_SEQ"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewOtAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO_SEQ"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
			
			 
				this.affirmInfoToLGEPSer.crateAffirm(lgepMap);	
			 
		
	}
	
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	@SuppressWarnings("unchecked")
	private void sendToLGEPInsert(List list){
		for(int i=0;i<list.size();i++){
			LinkedHashMap paramMap = (LinkedHashMap)list.get(i);
		 	List OtList = this.getOtAffirmInfo2List(paramMap);
		 
			LinkedHashMap lgepMap = (LinkedHashMap)OtList.get(0);
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", lgepMap.get("APPLY_NO"));
			lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_TITLE", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_EMPID", lgepMap.get("PERSON_ID"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewOtAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + lgepMap.get("APPLY_NO"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewOtAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + lgepMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + lgepMap.get("APPLY_NO"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", lgepMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", lgepMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
		}
	}
	
	@Override
	public List getOtAffirmInfo2List(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.getOtAffirmInfo2List",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 验证批量导入的加班申请(add overtime apply)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String checkImportOtApply(Object object)  throws Exception{
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("pa.tempsale.importInfoFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			throw e;
		}
		return returnString ;
	}
	
	/**
	   * 更新批量导入加班申请的check结果( cancel overtime apply)
	   * 
	   * @param object
	   * @return
	   * @throws Exception
	   */
	  @Override
	  public int updateOtApplyCheckResult(Object object) throws Exception {
	    int flag = 0;
	    this.update("ess.infoApply.updateOtApplyCheckResult", object);
	    
	    return flag;
	  }
	  /**
	   * 查询员工是否可以被管理
	   * 
	   * @param object
	   * @return
	   * @throws Exception
	   */
	  @Override
	  public int selectOtApplyCheckResult(Object object) throws Exception {
	    int flag = 0;
	   
	    flag =  Integer.parseInt(String.valueOf(this.queryForObject("ess.infoApply.selectOtApplyCheckResult",object)));;
	    
	    return flag;
	  }
	
	/**
	 * 批量修改加班申请(update overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateOvertimeApplyInBatch(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap map = (LinkedHashMap) list.get(i);
				LinkedHashMap tempMap = new LinkedHashMap();
				Map map3 = new HashMap();	
				Map<String,List>  map2 = (Map<String,List>)map.get("PARAM_MAP");
				 
				 
				Iterator it = map2.keySet().iterator();
				while(it.hasNext()){	
					String key = (String) it.next();
					
					try{
					if(key.equals("OPE_FLAG")){  
					map3.put("OPE_FLAG", map2.get(key));
					}
					if(key.equals("APPLY_NO")){  
						map3.put("APPLY_NO", map2.get(key));
						}	 
					}catch(Exception e){
						e.printStackTrace();
						 
					} 
					}

				String opeFlag = map3.get("OPE_FLAG")!=null?map3.get("OPE_FLAG").toString():"";
				int applyNo = Integer.parseInt(map3.get("APPLY_NO")!=null?map3.get("APPLY_NO").toString():"");
				//如果是修改个人申请的加班信息，需要重新插入决裁者信息
				if(!"".equals(opeFlag) && "UPDATE_PERSON".equals(opeFlag)){
					if (map != null && map.get("PARAM_MAP") != null) {
			        	LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
			        	obj.put("APPLY_NO_SEQ", applyNo);
			        	obj.put("APPLY_TYPE",APPLY_TYPE_NO);
			        	//更新加班申请信息
			        	this.update("ess.infoApply.updateOvertimeApply", obj);
			        	//删除原来的决裁者信息
			        	this.delete("ess.infoApply.delOtApplyAffirmByApplyNo", obj);
			        	tempMap.put("CREATED_BY", ((LinkedHashMap) obj).get("CREATED_BY"));
			        	tempMap.put("APPLY_TYPE_NO", ((LinkedHashMap) obj).get("APPLY_TYPE_NO"));
			        	tempMap.put("APPLY_NO_SEQ", applyNo);
			        	

						//先删除附件
						LinkedHashMap fileMapDel = new LinkedHashMap();
						fileMapDel.put("APPLY_TYPE", APPLY_TYPE_NO);
						fileMapDel.put("APPLY_NO", applyNo);
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
					}

					if (map != null && map.get("DISTINCT_LIST") != null) {
						List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
						if (aList != null && aList.size() > 0) {
							for (LinkedHashMap parmers : aList) {
								tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
								tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
								tempMap.put("AFFIRM_COM_TYPE", parmers.get("AFFIRM_COM_TYPE"));
								this.insert("ess.infoApply.insertApplyReviewer",tempMap);
							}
							//这里是否还需要对新加入的决裁信息进行update排序，因为affirm_level可能不是连续的，预留
							
						}
					}
				//批量修改加班申请信息时不修改决裁者信息
				}else{
					if (map != null && map.get("PARAM_MAP") != null) {
			        	LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");	
			        	this.update("ess.infoApply.updateOvertimeApply", obj);
			        }
				}
			}
		}
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
			return this.queryForObject("ess.infoApply.getParamInfoValue",
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
			return this.queryForList("ess.infoApply.getDateByPersonIdAndCpny", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 获取日期类型(get the date type code)
	 @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-12-6 下午4:48:18 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDateTypeByDateAndCpny(Object obj) {
		try {
			return this.queryForList("ess.infoApply.getDateTypeByDateAndCpny", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map getDefaultStartEndTime(Object obj) {
		
		try {
			return (Map) this.queryForObject("ess.infoApply.getDefaultStartEndTime", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map getOtLimit(Object obj) {
		
		try {
			return (Map) this.queryForObject("ess.infoApply.getOtLimit", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getChangeOtType(Object obj) {
		try {
			return this.queryForList("ess.infoApply.getChangeOtType", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 获取日期类型(get the date type code)
	 @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-12-6 下午4:48:18 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDataType2(Object obj) {
		try {
			return this.queryForList("ess.infoApply.getDataType2", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 获取日期类型(get the date type code)
	 @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author hr heran@ait.net.cn 
	* @date 2013-12-6 下午4:48:18 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDataType2w(Object obj) {
		try {
			return this.queryForList("ess.infoApply.getDataType2w", obj);
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
			returnList = this.queryForList("ess.infoApply.getApplyorByApplyNoList",obj);
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
			returnList = this.queryForList("ess.infoApply.getAffirmorByApplyNoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 根据加班申请NO决裁信息查询(search ot info list)小页面用
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorByApplyNoListXiao(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.getAffirmorByApplyNoListXiao",obj);
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
			returnList = this.queryForList("ess.infoApply.getCheckorByApplyNoList",obj);
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
	public List getCheckorByApplyNoListXiao(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.getCheckorByApplyNoListXiao",obj);
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
	public int delOvertimeApplyInBatch(List list) throws Exception {
		
		try {
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				
				String PERSON_ID = getArDetailTstoApplyOfPERSONID(obj);
				obj.put("APPLY_NO_FOR_APP", getArDetailTstoApplyOfApplyNo(obj));//获取当前PK_NO的apply_no
				if (!"".equals(PERSON_ID)&& PERSON_ID!= null&&!"0".equals(getArDetailTstoApplyOfApplyNo(obj))) {
					this.delete("ar.detail.deleteApplyOtTableSST", obj);//删除申请表中的数据
					this.delete("ar.detail.deleteEssAffrimOtSST", obj);//删除申请表中的数据
					this.update("ar.detail.delTempLeaveApplySST", obj);//修改当前要删除的数据
				}else {
					this.delete("ar.detail.delTempLeaveApplySSTNULL", obj);//修改当前要删除的数据
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	/**
	 * 加班管理--批量删除加班申请的数据(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int delOvertimeApplyInBatchTSTO(List list) throws Exception {
		try {
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				
				String PERSON_ID = getArDetailTstoApplyOfPERSONID(obj);
				obj.put("APPLY_NO_FOR_APP", getArDetailTstoApplyOfApplyNo(obj));//获取当前PK_NO的apply_no
				if (!"".equals(PERSON_ID)&& PERSON_ID!= null) {
					
					this.delete("ar.detail.deleteApplyOtTableAdjustTSTO", obj);//删除申请表中的数据
					this.delete("ar.detail.deleteEssAffrimOtAdjustTSTO", obj);//删除决裁表中的数据
					//删除通过存储
					this.delete("ar.detail.deleteArDetailOvertime", obj);//删除申请表中的数据
				}else {
					this.delete("ar.detail.delTempLeaveApplySSTNULL", obj);//删除NULL数据
				}
				//删除多余的加班数据
				LinkedHashMap deleteMap = new LinkedHashMap();
				deleteMap.put("FROM_DATE", obj.get("APPLY_DATE"));
				deleteMap.put("TO_DATE", obj.get("APPLY_DATE"));
				this.delete("ar.detail.deleteOtAdjustForOnlyOne",deleteMap );
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	/**
	 * 批量删除倒休申请的数据(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int delOvertimeApplyInBatchAdjustTSTO(List list) throws Exception {
		
		try {
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				
				String PERSON_ID = getArDetailTstoApplyOfPERSONID(obj);
				obj.put("APPLY_NO_FOR_APP", getArDetailTstoApplyOfApplyNo(obj));//获取当前PK_NO的apply_no
				if (!"".equals(PERSON_ID)&& PERSON_ID!= null&&!"0".equals(getArDetailTstoApplyOfApplyNo(obj))) {
					
				  
					
					this.delete("ar.detail.deleteApplyOtTableAdjustTSTO", obj);//删除申请表中的数据
					this.delete("ar.detail.deleteEssAffrimOtAdjustTSTO", obj);//删除决裁表中的数据
					//删除通过存储
					this.delete("ar.detail.deleteArDetailAdjust", obj);//删除申请表中的数据
				}else {
					this.delete("ar.detail.delTempLeaveApplySSTNULL", obj);//删除NULL数据
				}
				//删除多余的加班数据
				LinkedHashMap deleteMap = new LinkedHashMap();
				deleteMap.put("FROM_DATE", obj.get("APPLY_DATE"));
				deleteMap.put("TO_DATE", obj.get("APPLY_DATE"));
				this.delete("ar.detail.deleteOtAdjustForOnlyOne",deleteMap );
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
	public int updateOvertimeApplyInBatchForCancel(List list) throws Exception {
		
		try {
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				Map paramMap = new LinkedHashMap();
				//obj.put("APPLY_NO", getArDetailTstoApplyOfApplyNoForOt(obj));//获取当前PK_NO的apply_no
				this.update("ess.infoApplyLeave.PR_DELETE_OT_CONFIRM", obj);
				this.update("ess.infoApply.updateApplyOtTable", obj);
				paramMap = (Map)this.queryForObject("ess.infoApply.getAffirmEmailParam",obj);
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
	
	//获取正常出勤原先数据
	public String getOtApplyPkNoIs0(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApply.getOtApplyPkNoIs0", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}
	
	/**
	 * 批量提交加班申请
	 * 
	 * @param obj
	 * @return
	 */
	public int submitOtApplyInBatch(List list) throws Exception {
		try {
			this.updateForList("ess.infoApply.submitTempOtApply", list);
	
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delOvertimeApply(Object object) throws Exception {
		Boolean falg = true;
		this.delete("ess.infoApply.delOtApplyDetailByApplyNo", object);
		this.delete("ess.infoApply.delOtApplyAffirmByApplyNo", object);
		this.delete("ess.infoApply.delOtApplyByApplyNo", object);
		return falg;
	}
	
	/**
	 * 删除导入加班信息申请(delete overtime apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delOvertimeApplyImport(Object object) throws Exception {
		Boolean flag = true;
		this.delete("ess.infoApply.delOtApplyImportByApplyNo", object);
		return flag;
	}
	
	/**
	 * 删除临时表中所有导入的加班信息申请(delete overtime apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean cancelOvertimeApplyImport(Object object) throws Exception {
		Boolean falg = true;
		this.delete("ess.infoApply.cancelOvertimeApplyImport", object);
		return falg;
	}
	
	/**
	 * 取消已审核通过的加班申请( cancel overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean cancelOvertimeApply(Object object) throws Exception {
		Boolean falg = true;
		String  otFlag = "N";
		LinkedHashMap paramMap = (LinkedHashMap)object;	
		//LGEPN法人周末加班调休，通过之后不可取消
		String flag_Cancel = paramMap.get("FLAG")!=null?paramMap.get("FLAG").toString():"A";
		if(!"BATCH".equals(flag_Cancel)){
			this.insert("ess.infoApply.callCancelOt", paramMap) ;	
		}else{
			this.insert("ess.infoApply.callCancelOtBatch", paramMap) ;
		}
	
		otFlag  = ObjectUtils.toString(paramMap.get("message"));
		if("Y".equals(otFlag)){
			
			if(!"BATCH".equals(flag_Cancel)){
				this.delete("ess.infoApply.delOtApplyDetailByApplyNo", object);
				this.delete("ess.infoApply.delOtApplyDetailByApplyNoBatCht", object);
				this.delete("ess.infoApply.delOtApplyDetailByApplyNoLast", object);
				this.update("ess.infoApply.cancelOtApplyByApplyNo", object);
			}else{
				this.delete("ess.infoApply.delOtApplyDetailByApplyNo", object);
				 
				this.delete("ess.infoApply.delOtApplyDetailByApplyNoLast", object);
				//在个人申请页面取消某条批量的加班
				this.update("ess.infoApply.cancelOtApplyByApplyNoBatch_PERSON", object);
			}
		
			falg = true;
		}else{
			falg =false;
		}
		 
		return falg;
	}
	
	/**
	 * 批量加班申请--已申请的加班信息列表(get overtime apply list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtApplyInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getOtApplyInfoList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 批量加班申请--已申请的加班信息列表(get overtime apply list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtApplyInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.infoApply.getOtApplyInfoList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.infoApply.getOtApplyInfoList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 批量加班申请--已申请的加班信息列表总数(get overtime apply list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOtApplyInfoListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.infoApply.getOtApplyInfoListCnt", obj)),
				Integer.class);
	}
	
	/**
	 * 加班申请时，根据页面参数获取对应的加班转换类型
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getOtTypeCodeByTurn(Object obj) {
		LinkedHashMap dataMap = new LinkedHashMap();
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.getOtTypeCodeByTurn",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(returnList.size()>0){
			dataMap = (LinkedHashMap)returnList.get(0); 
		}else{
			dataMap.put("OT_TYPE_CODE", "32");
			dataMap.put("APPLY_CODE_NO", "32");
			dataMap.put("ADJUST_YN", "0");
			dataMap.put("TESHU_YN", "0");
			dataMap.put("OT_PLACE_TYPE", "INSIDE");
		}
		
		return dataMap;
	}
	/**
	 * 
	 * 返回加班是否追溯的标识
	 */
	public  String getIfZhuiSu(Object obj){
	  
		String obj1 = "";
		try {
			obj1 = (String)this.queryForObject("ess.infoApply.getIfZhuiSu",obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj1;	
	}
	/**
	 * 加班申请时，根据页面参数获取是否是夜班
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getOtTypeNight(Object obj) {
		LinkedHashMap dataMap = new LinkedHashMap();
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.getOtTypeNight",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		if(returnList.size()>0){
			dataMap = (LinkedHashMap)returnList.get(0); 
		}else{
			dataMap.put("OT_TYPE_CODE", "32");
			dataMap.put("APPLY_CODE_NO", "32");
			dataMap.put("ADJUST_YN", "0");
			dataMap.put("TESHU_YN", "0");
			dataMap.put("OT_PLACE_TYPE", "INSIDE");
		}
		
		return dataMap;
	}
	
	/**
	 * 取申请加班的人事政策
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getOtTypeCodeRemark(Object obj) {
		LinkedHashMap dataMap = new LinkedHashMap();
		
		LinkedHashMap paramMap = (LinkedHashMap)obj;
		String APPLY_TYPE_CODE =(paramMap.get("APPLY_TYPE_CODE")!=null&&!"".equals(paramMap.get("APPLY_TYPE_CODE")))?paramMap.get("APPLY_TYPE_CODE").toString():"";
		List returnList = new ArrayList();
		try {
			if(!"32".equals(paramMap.get("APPLY_TYPE_CODE"))&&!"33".equals(paramMap.get("APPLY_TYPE_CODE"))&&!"34".equals(paramMap.get("APPLY_TYPE_CODE"))){
				returnList = this.queryForList("ess.infoApply.getOtTypeCodeRemarkTwo",obj);
			}else{
				returnList = this.queryForList("ess.infoApply.getOtTypeCodeRemark",obj);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(returnList.size()>0){
			dataMap = (LinkedHashMap)returnList.get(0); 
		}else{
			dataMap.put("OT_TYPE_CODE", "32");
			dataMap.put("APPLY_CODE_NO", "32");
			dataMap.put("ADJUST_YN", "0");
			dataMap.put("TESHU_YN", "0");
			dataMap.put("OT_PLACE_TYPE", "INSIDE");
			dataMap.put("DETAIL_CONTENT", "无");
		}
		
		return dataMap;
	}
	
	/**
	 * 加班申请时，根据页面参数获取对应的加班转换类型
	 * 
	 * @param parameterObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getOtLotNo(Object obj) {
		LinkedHashMap paramMap = (LinkedHashMap)obj;
		String cpnyId = paramMap.get("CPNY_ID").toString();
		 
		 
			Date d = new Date();
		 String applyOtDate = (new SimpleDateFormat("yyyyMMdd")).format(d);
		 
		String  otTimeType = (String) paramMap.get("OT_TIME_TYPE");
		 
		String lotNoStr = "";
		String lotNo = paramMap.get("BNO").toString();
	 
		 
		lotNoStr = cpnyId + "-"+applyOtDate+"-"+otTimeType+"-"+lotNo;
		 
		return lotNoStr;
	}
	
	/**
	 * 导入加班申请--查看信息列表(get overtime import list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtImportInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getOtImportInfoList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 导入加班申请--查看信息列表(get overtime import list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtImportInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.infoApply.getOtImportInfoList",obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.infoApply.getOtImportInfoList",obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 导入加班申请--查看信息列表总数(get overtime import list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOtImportInfoListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.infoApply.getOtImportInfoListCnt", obj)),Integer.class);
	}
	
	/**
	 * 导入加班申请--查看错误信息列表总数(get error overtime import list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOtImportInfoListErrorCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.infoApply.getOtImportInfoListErrorCnt", obj)),Integer.class);
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
					"ess.infoApply.retrievePersonalInfo", obj);
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
	 *  根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param object
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonListOt(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPersonListOt(obj, -1, -1) ;
		return returnList ;
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
				returnList = this.queryForList("ess.infoApply.getOtPersonList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.infoApply.getOtPersonList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	

	@SuppressWarnings("unchecked")
	public List getPersonListOt(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.infoApply.getOtPersonListOt", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ess.infoApply.getOtPersonListOt", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
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
				.queryForObject("ess.infoApply.getOtPersonListCnt", obj)),
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
			this.delete("ess.infoApply.delEssPersonalInfo", obj);
			// 添加新的人员信息申请(add new person-information-apply)
			this.insert("ess.infoApply.insertEssPersonalInfo", obj);
			// this.commitTransation();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
			return this.queryForList("ess.infoApply.getEmpShift", obj);
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
			return this.queryForList("ess.infoApply.getOtDeductTimeList", obj);
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
		this.insert("ess.infoApply.insertOvertimeApply", obj);
		tempMap.put("CREATED_BY", ((LinkedHashMap) obj).get("CREATED_BY"));
		tempMap.put("APPLY_NO_SEQ", essApplySeq);

		if (list != null && list.size() > 0) {
			for (LinkedHashMap parmers : list) {
				tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
				tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
				tempMap.put( "APPLY_TYPE_NO",parmers.get("APPLY_TYPE_NO") );
				this.insert("ess.infoApply.insertApplyReviewer", tempMap);
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
					this.insert("ess.infoApply.insertOvertimeApply", obj);
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
							this.insert("ess.infoApply.insertApplyReviewer",tempMap);
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
		List object2 = this.queryForList("ess.infoApply.retrieveVacationEmpREST", obj);
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
		String object2 = (String) this.queryForObject("ess.infoApply.getVacationEmpREST", obj);
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
		String object2 = (String) this.queryForObject("ess.infoApply.getSurplusAdjustRest", obj);
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
		String object2 = (String) this.queryForObject("ess.infoApply.getAdjustRestNew", obj);
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
				this.insert("ess.infoApply.insertLeaveApply", obj);
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
						this.insert("ess.infoApply.insertApplyReviewer",
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
	@SuppressWarnings("unchecked")
	@Override
	public void addLeaveApplyInBatch(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap map = (LinkedHashMap) list.get(i);
				LinkedHashMap tempMap = new LinkedHashMap();
				int essApplySeq = getEssApplySeq();
				
				if (map != null && map.get("PARAM_MAP") != null) {
					LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
					obj.put("APPLY_NO_SEQ", essApplySeq);
					this.insert("ess.infoApply.insertLeaveApply", obj);
					tempMap.put("CREATED_BY", ((LinkedHashMap) obj)
							.get("CREATED_BY"));
					tempMap.put("APPLY_NO_SEQ", essApplySeq);
				}

				if (map != null && map.get("DISTINCT_LIST") != null) {
					List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
							tempMap.put("AffirmLevel", parmers
									.get("AFFIRM_LEVEL"));
							tempMap.put("AffirmorId", parmers
									.get("AFFIRMOR_ID"));
							this.insert("ess.infoApply.insertApplyReviewer",
									tempMap);
						}
					}
				}
			}
		}
	}

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
							.insert("ess.infoApply.insertApplyReviewer",
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
				this.insert("ess.infoApply.insertLeaveApply", obj);
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
						this.insert("ess.infoApply.insertApplyReviewer",
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
				this.insert("ess.infoApply.insertLeaveApply", obj);
			}

			tempMap.put("CREATED_BY", obj.get("CREATED_BY"));
			tempMap.put("APPLY_NO_SEQ", essApplySeq);

			if (leaveMap.get("DISTINCT_LIST") != null) {
				List<LinkedHashMap> list = (List) leaveMap.get("DISTINCT_LIST");
				if (list != null && list.size() > 0) {
					for (LinkedHashMap parmers : list) {
						tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
						tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
						this.insert("ess.infoApply.insertApplyReviewer",
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
			return this.queryForList("ess.infoApply.getExistOtApplyDate", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 社外申请加班验证是否出差(get person exist overtime apply date)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List otApplyCheckOut(Object obj) throws Exception {
		try {
			return this.queryForList("ess.infoApply.otApplyCheckOut", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 社外申请加班验证是否出差(get person exist overtime apply date)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List otApplyCheckOutCh(Object obj) throws Exception {
		List list = null;
		try {
			list =  this.queryForList("ess.infoApply.otApplyCheckOutCh", obj);
			if(list.isEmpty()){
				return null;
			}
			 

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
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
			 
			 
			String   shiftno =  StringUtil.checkNull(this.queryForObject("ess.infoApply.getOtApplyDateWithShift_NO", obj),"");
			LinkedHashMap map = (LinkedHashMap) obj;
			map.put("SHIFT_NO",shiftno);
			
			return this.queryForList("ess.infoApply.getOtApplyDateWithShift",
					map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 取得加班申请导入临时表里的其它加班申请(get overtime apply with import other)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtApplyDateWithImportOther(Object obj) throws Exception {
		try {
			return this.queryForList("ess.infoApply.getOtApplyDateWithImportOther",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 取得加班申请导入临时表里的其它加班申请(get overtime apply with import other)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getOtApplyDateWithImportOtherWQ(LinkedHashMap paramMap) throws Exception {
		String flag = "";
		try {
			 
		   this.insert("ess.infoApply.getOtApplyDateWithImportOtherWQ", paramMap);
		   
			flag =ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 
	 * 加班导入提交的验证
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getOtApplyDateWithImportOtherAll(LinkedHashMap paramMap) throws Exception {
		String flag = "";
		try {
			 
		   this.insert("ess.infoApply.getOtApplyDateWithImportOtherAll", paramMap);
		   
			flag =ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 加班申请验证
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getOtApplyConfirmCpny(LinkedHashMap paramMap) throws Exception {
		String flag = "";
		try {
			 
		   this.insert("ess.infoApply.getOtApplyConfirmCpny", paramMap);
		   
			flag =ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
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
			return this.queryForList("ess.infoApply.getExistLeaveDate", obj);
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
			return NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this.queryForObject("ess.infoApply.getDataType", object),0)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获得nd法人加班判断(get calendar type)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getDataTypeReturn(Object object) throws Exception {
		String val="";
		try {
			val=  ObjectUtils.toString(StringUtil.checkNull(this.queryForObject("ess.infoApply.getDataTypeReturn", object),"J"));
		} catch (SQLException e) {
			val="";
			 
			e.printStackTrace();
			
		}
		return val;
	}
	@Override
	public String getDataTypeReturnFront(Object object) throws Exception {
		String val="";
		try {
			val=  ObjectUtils.toString(StringUtil.checkNull(this.queryForObject("ess.infoApply.getDataTypeReturnFront", object),"J"));
		} catch (SQLException e) {
			val="";
			 
			e.printStackTrace();
			
		}
		return val;
	}
	@Override
	public int getotlengthlgend(Object object) throws Exception{
		int val = 0; 
		try{
			val= NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this.queryForObject("ess.infoApply.getOtlengthLgend", object),0)),Integer.class);
		}catch(SQLException e){
			val=0;
			e.printStackTrace();
		}
		return val;
	}
	
	@Override
	public int getotlengthtotal(Object object) throws Exception{
		int val = 0; 
		try{
			val= NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this.queryForObject("ess.infoApply.getotlengthtotal", object),0)),Integer.class);
		}catch(SQLException e){
			val=0;
			e.printStackTrace();
		}
		return val;
	}
	
	
	/**
	 * 获得日历类型(get calendar type)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getDataTypeCheck(Object object) throws Exception {
		int val=0;
		try {
			val= NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this.queryForObject("ess.infoApply.getDataTypeCheck", object),0)),Integer.class);
		} catch (SQLException e) {
			val=0;
			 
			e.printStackTrace();
			
		}
		return val;
	}
	
	/**
	 * 获得日历类型(get calendar type)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getDataTypeL(Object object) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this.queryForObject("ess.infoApply.getDataTypeL", object),0)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获得日历类型(get calendar type)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getDataTypeWQ(Object object) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this.queryForObject("ess.infoApply.getDataTypeWQ", object),0)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获得员工的加班限制标志(get ot apply limit flag)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOtApplyLimitFlag(Object object) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this.queryForObject("ess.infoApply.getOtApplyLimitFlag", object),0)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获得员工考勤期间内的加班时数
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOtApplyOT36(Object object) throws Exception {
		int val=0;
		try {
			val =  NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this.queryForObject("ess.infoApply.getOtApplyOT36", object),0)),Integer.class);
		} catch (SQLException e) {
			val = 0;
			e.printStackTrace();
		}catch(Exception e){
			val = 0;
			e.printStackTrace();
		}
		return val;
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
			return this.queryForObject("ess.infoApply.vacApplying", object);
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
			return this.queryForObject("ess.infoApply.restVac", object);
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
			return NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this
					.queryForObject("ess.infoApply.getEssApplySeq"),0)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获得信息申请序列(get information apply sequences)
	 * 
	 * @param object
	 * @return
	 */
	private int getEssApplyBatchSeq() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this
					.queryForObject("ess.infoApply.getEssApplyBatchSeq"),0)),
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
		return this.queryForObject("ess.infoApply.getParamInfoValue", obj);
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
				"ess.infoApply.getLeaveApplyLength", obj);
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
			return this.queryForList("ess.infoApply.getOvertimeApplyAllDateList", obj);
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
				"ess.infoApply.retrieveVacationEmpRESTQN", obj);
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
			returnString=(String) this.queryForObject("ess.infoApply.getspouseBirth", obj);
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
					"ess.infoApply.getviewVacationStandard", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return length;
	}
	
	
	@Override
	public String getLeaveTypeCode(Object obj) {
		String returnString = "";
		try {
			returnString = (String) this.queryForObject("ess.infoApplyLeave.getLeaveTypeCodeNo", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return returnString;
	}
	
	@Override
	public String getOtTypeCode(Object obj) {
		String returnString = "";
		try {
			returnString = (String) this.queryForObject("ess.infoApplyLeave.getOtTypeCodeNo", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return returnString;
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
						this.insert("ess.infoApply.insertLeaveApply", obj);
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
								this.insert("ess.infoApply.insertApplyReviewer",
										tempMap);
							}
						}
					}
				}
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String getLeaveAffirmLevel(Object object) throws Exception{
		String affirmLength =",,";
		LinkedHashMap affirmMap = (LinkedHashMap) object;
		String applyTypeCode = (String) affirmMap.get("APPLY_TYPE_CODE");
		String applyLength = (String) affirmMap.get("APPLY_LENGTH");	
		List<LinkedHashMap> essApplyParamList = this.queryForList("ess.infoApply.getEssApplyParamList", object);
		if(essApplyParamList.size()!=0){
			String caseSql =  " CASE ";
			for (int i = 0; i < essApplyParamList.size(); i++) {
				LinkedHashMap essApplyParam = essApplyParamList.get(i);
				String referencnFromFlag = essApplyParam.get("REFERENCN_FROM_FLAG").toString();
				String referencnFromRelation = essApplyParam.get("REFERENCN_FROM_RELATION").toString();
				String referencnFromOffset = essApplyParam.get("REFERENCN_FROM_OFFSET").toString();
				String referencnToFlag = essApplyParam.get("REFERENCN_TO_FLAG").toString();
				String referencnToRelation =  essApplyParam.get("REFERENCN_TO_RELATION").toString();
				String referencnToOffset =  essApplyParam.get("REFERENCN_TO_OFFSET").toString();
				String affirmLevel = essApplyParam.get("AFFIRM_LEVEL").toString();
				String lowLevel =  String.valueOf(essApplyParam.get("LOW_LEVEL"));
				String highLevel = String.valueOf(essApplyParam.get("HIGH_LEVEL"));
				if(Integer.parseInt(referencnFromFlag)==1&&Integer.parseInt(referencnToFlag)==1){
					caseSql = caseSql + " WHEN "+ applyLength + referencnFromRelation + referencnFromOffset	+ " AND " + applyLength	+ 
						referencnToRelation + referencnToOffset + " THEN '" + affirmLevel + "," + lowLevel + "," + highLevel + "'";
				}else if(Integer.parseInt(referencnFromFlag)==1&&Integer.parseInt(referencnToFlag)==0){
					caseSql = caseSql + " WHEN "+ applyLength + referencnFromRelation + referencnFromOffset	+ " THEN '" + affirmLevel + "," + lowLevel + "," + highLevel + "'";
				}else if(Integer.parseInt(referencnFromFlag)==0&&Integer.parseInt(referencnToFlag)==1){
					caseSql = caseSql + " WHEN "+ applyLength + referencnToRelation + referencnToOffset + " THEN '" + affirmLevel + "," + lowLevel + "," + highLevel + "'";
				}
			}
			caseSql = caseSql + " ELSE ',,' END AS AFFIRMLEVEL ";
			LinkedHashMap affirmLevelMap = new LinkedHashMap();
			affirmLevelMap.put("CASE_SQL", caseSql);
			affirmLength = ObjectUtils.toString(this.queryForObject("ess.infoApply.getaffirmLevel", affirmLevelMap)) ;			
		}
		return affirmLength;
	};
	
	/**
	 * 根据PERSONID或DEPTNO取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorListByPersonIdNew(Object obj) throws Exception {
		return this.queryForList("ess.infoApply.getAffirmorListByPersonIdNew", obj);
	}
	
	/**
	 * 根据PERSONID取得特殊审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getSpecialAffirmorListByPersonIdNew(Object obj) throws Exception {
		return this.queryForList("ess.infoApply.getSpecialAffirmorListByPersonIdNew", obj);
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
	public List getAffirmorListByDeptNoNew(Object obj) throws Exception {
		return this.queryForList("ess.infoApply.getAffirmorListByDeptNoNew", obj);
	}
	
	
	/**
	 * 根据是否有针对部门取得特殊审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getSpecialAffirmorListByDeptNoNew(Object obj) throws Exception {
		return this.queryForList("ess.infoApply.getSpecialAffirmorListByDeptNoNew", obj);
	}
	
	
	/**
	 * 根据parent_code获取子code列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCodeList(Object obj) throws Exception {
		return this.queryForList("ess.infoApply.getCodeList", obj);
	}
	

	/**
	 * 员工信息查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	@Override
	public Object getPersonalInfoForApplyParam(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApply.getPersonalInfoForApplyParam",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	/**
	 * 员工班次开始结束(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	@Override
	public Object getPersonalInfoForApplyShift(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApply.getPersonalInfoForApplyShift",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}

	/**
	 * 员工班次结束时间查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	@Override
	public Object getShiftEndTime(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApply.getShiftEndTime",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}

	/* 
	* Title: getDataTypeForPeople
	* Description:查询个人排班的某天日期类型
	* @author 孙鹏  
	* @date 2014年12月13日 下午1:30:09  
	* @param paramMap
	* @return 
	* @see com.ait.ess.dao.InfoApplyDao#getDataTypeForPeople(java.util.Map) 
	*/
	@Override
	public List getDataTypeForPeople(Map paramMap) {
		try {
			return this.queryForList("ess.infoApply.getDataTypeForPeople", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	public List getDataTypeForPeopleW(Map paramMap) {
		try {
			return this.queryForList("ess.infoApply.getDataTypeForPeopleW", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List getAffirmorListByDept(Object obj) throws Exception {
		return this.queryForList("ess.infoApply.getAffirmorListByDept", obj);
	}

	@Override
	public String getEmpTypeCode(Object object) throws Exception {
		String obj = null;
		try {
			obj = StringUtil.checkNull(this.queryForObject("ess.infoApply.getEmpTypeCode",object));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	

	/**
	 * 批量申请审批者 修改
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void modifyAffirmorForBatch(Map map) throws Exception {
						
		if (map != null && map.get("affirmList") != null) {
			List<LinkedHashMap> aList = (List) map.get("affirmList");
			if (aList != null && aList.size() > 0) {
	        	//删除原来的决裁者信息
	        	this.delete("ess.infoApply.delOtApplyAffirmByApplyNo", map);
				for (LinkedHashMap parmers : aList) {
					map.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
					map.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
					this.insert("ess.infoApply.insertApplyReviewerBatch",map);
				}
				//临促修改当前审批者
				if(map.get("APPLY_TYPE") != null && "218064".equals(StringUtil.checkNull(map.get("APPLY_TYPE")))){
					LinkedHashMap parmers = aList.get(0);
					map.put("CURRENT_AFFIRM_ID", parmers.get("AFFIRMOR_ID"));
					this.insert("pa.tempsale.updateCurrentAffirmId",map);
				}
				//批量休假修改当前审批者
				if(map.get("APPLY_TYPE") != null && "21".equals(StringUtil.checkNull(map.get("APPLY_TYPE")))){
					LinkedHashMap parmers = aList.get(0);
					map.put("CURRENT_AFFIRM_ID", parmers.get("AFFIRMOR_ID"));
					this.insert("ess.infoApplyLeave.updateCurrentAffirmId",map);
				}
				//批量加班修改当前审批者
				if(map.get("APPLY_TYPE") != null && "31".equals(StringUtil.checkNull(map.get("APPLY_TYPE")))){
					LinkedHashMap parmers = aList.get(0);
					map.put("CURRENT_AFFIRM_ID", parmers.get("AFFIRMOR_ID"));
					this.insert("ess.infoApply.updateCurrentAffirmId",map);
				}
				//批量漏刷卡修改当前审批者
				if(map.get("APPLY_TYPE") != null && "218294".equals(StringUtil.checkNull(map.get("APPLY_TYPE")))){
					LinkedHashMap parmers = aList.get(0);
					map.put("CURRENT_AFFIRM_ID", parmers.get("AFFIRMOR_ID"));
					this.insert("ess.macRecordApply.updateCurrentAffirmId",map);
				}
				//批量年假调整修改当前审批者
				if(map.get("APPLY_TYPE") != null && "216691".equals(StringUtil.checkNull(map.get("APPLY_TYPE")))){
					LinkedHashMap parmers = aList.get(0);
					map.put("CURRENT_AFFIRM_ID", parmers.get("AFFIRMOR_ID"));
					this.insert("ess.Annualadjustment.updateCurrentAffirmId",map);
				}
				//批量旷工申请修改当前审批者
				if(map.get("APPLY_TYPE") != null && "218197".equals(StringUtil.checkNull(map.get("APPLY_TYPE")))){
					LinkedHashMap parmers = aList.get(0);
					map.put("CURRENT_AFFIRM_ID", parmers.get("AFFIRMOR_ID"));
					this.insert("ess.infoApply.updateCurrentAffirmIdCwa",map);
				}
			}
		}
	}
	
	/**
	 * 根据cpnyId获取加班开始、结束时间显示
	 * @param obj
	 * @return object
	 */
	public List getOtTimeByCpnyId(Map paramMap){
		List timeList = new ArrayList();
		try {
			timeList = this.queryForList("ess.infoApply.getOtTimeByCpnyId", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return timeList;
	}
	
	/**
	 * 获取审批线sql
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getAffirmorlistByPersonIdStr(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApply.getAffirmorlistByPersonIdStr", obj);
		if(object2 ==null){
			object2 = "SELECT 1 FROM DUAL WHERE 1=0";
		}
		return object2 ;
	}
	
	/**
	 * 根据审批线sql取得审批人列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorlistByPersonIdList(Object obj) throws Exception {
		return this.queryForList("ess.infoApply.getAffirmorlistByPersonIdList", obj);
	}
	
	/**
	 * 获取当月漏刷卡次数
	 * @param request
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public int arGetMacApplyCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApply.arGetMacApplyCnt", obj)),Integer.class);
	}
	
	/**
	 * 获取旷工导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssAbsenteeismTempList(Object object){
		return this.getEssAbsenteeismTempList(object, -1, -1);
	}
	
	/**
	 * 获取旷工导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssAbsenteeismTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.infoApply.getEssAbsenteeismTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.infoApply.getEssAbsenteeismTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取旷工导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssAbsenteeismTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.infoApply.getEssAbsenteeismTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取出错的旷工导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssAbsenteeismTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.infoApply.getEssAbsenteeismTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 删除未审核：in/out旷工信息申请(delete ar mac record apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delArCwaApplyInfo(List list) throws Exception {
		Boolean falg = true;
		for(int i=0;i<list.size();i++){
			LinkedHashMap map = (LinkedHashMap)list.get(i);
			map.put("APPLY_TYPE", "218197");
			//删除发送LGEP
			affirmInfoToLGEPSer.deleteAffirm(map);
		}
		this.deleteForList("ess.infoApply.delArCwaAffirmRelation", list);
		this.deleteForList("ess.infoApply.delArCwaApplyByRecordNo", list);
		this.deleteForList("ess.infoApply.delArCwaApplyByBatchRecordNo", list);
		return falg;
	}
	

	/**
	 * 批量提交旷工申请
	 * 
	 * @param obj
	 * @return
	 */
	public int submitArCwaApplyInBatch(List list) throws Exception {
		try {
			this.updateForList("ess.infoApply.submitTempArCwaApply", list);
			this.sendToLGEPInsertCwa(list);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	

	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	@SuppressWarnings("unchecked")
	private void sendToLGEPInsertCwa(List list){
		for(int i=0;i<list.size();i++){
			LinkedHashMap paramMap = (LinkedHashMap)list.get(i);
		 	List OtList = annualadjustmentInfoDao.getCwaAbnormalAffirmByApplyNOList(paramMap);
		 
			LinkedHashMap lgepMap = (LinkedHashMap)OtList.get(0);
			lgepMap.put("APPLY_TYPE", "218197");
			lgepMap.put("APPLY_NO", lgepMap.get("APPLY_NO"));
			lgepMap.put("APPLY_TYPE_NAME", "批量旷工审批邀请");
			lgepMap.put("APPLY_TITLE", "批量旷工审批邀请");
			lgepMap.put("APPLY_EMPID", lgepMap.get("PERSON_ID"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewCwaAbnormalAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO=218197&personId=123&APPLY_NO=" + lgepMap.get("APPLY_NO"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewCwaAbnormalAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO=218197&personId=" + lgepMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + lgepMap.get("APPLY_NO"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", lgepMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", lgepMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
		}
	}
	
	/**
	 * 获取旷工申请批量信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEssArCwaList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getEssArCwaList(object, -1, -1);
		return returnList;
	}
	
	/**
	 * 获取旷工申请批量信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArCwaList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.infoApply.getEssArCwaList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.infoApply.getEssArCwaList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取旷工申请批量数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArCwaCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.infoApply.getEssArCwaCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@Override
	public int getExceptionInt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.infoApply.getExceptionInt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@Override
	public void updatSubmitLeaveApplyInBatchForDeletePrepare(LinkedHashMap paramMap) throws Exception{
		this.update("ess.infoApply.updatSubmitLeaveApplyInBatchForDeletePrepare", paramMap);
	}
	@Override
	public void insertSubmitLeaveApplyInBatch(LinkedHashMap paramMap) throws Exception{
		String APPLY_NO = getARDetailApplyNo(paramMap);
		int APPLY_NO_SEQ = getARDetailApplySeq();
		paramMap.put("APPLY_NO_SEQ",APPLY_NO_SEQ);
		paramMap.put("APPLY_NO",APPLY_NO);
		this.insert("ess.infoApply.insertSubmitLeaveApplyInBatch", paramMap);
	}
	
	public String  getARDetailApplyNo(Object obj) throws Exception {
		try {
			return  (String) queryForObject(("ess.infoApply.getARDetailApplyNo"),
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}	
	
	public String  getReasonString(Object obj) throws Exception {
		try {
			return  (String) queryForObject(("ess.infoApply.getReasonString"),
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
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
					.queryForObject("ess.infoApply.getARDetailApplySeq")),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}	
	
	/**
	 * 批量添加加班申请(来自内务)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addOtApplyInArDetailBatch(LinkedHashMap obj) throws Exception {
	
				LinkedHashMap tempMap = new LinkedHashMap();
			
				int essApplySeq = getEssApplySeq();
					obj.put("APPLY_NO_SEQ", essApplySeq);
					obj.put("PERSON_ID", obj.get("PERSON_ID"));
					obj.put("APPLY_TYPE_NO","31");
					obj.put("OT_TYPE_CODE",obj.get("APPLY_TYPE_CODE"));
					obj.put("APPLY_OT_DATE", obj.get("APPLY_DATE"));
					obj.put("APPLY_TYPE", "BATCH");
					obj.put("LEAVE_TIME_TYPE", "31");
					String OT_FROM_TIME = (String) obj.get("OT_FROM_TIME");
					String OT_TO_TIME = (String) obj.get("OT_TO_TIME");
					obj.put("OT_FROM_TIME",OT_FROM_TIME);
					obj.put("OT_TO_TIME",OT_TO_TIME);
					obj.put("hour", obj.get("Lotlengthonehour"));
					obj.put("min", obj.get("Lotlengthonemin"));
					obj.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
					obj.put("APPLY_REMARK", "原因:"+getReasonString(obj)+"  其他原因:"+obj.get("otherReason"));
					obj.put("OT_TIME_TYPE","P");
					if (!"".equals(obj.get("ADJST_YN"))&&obj.get("ADJST_YN") != null) {
						obj.put("ADJUST_YN",obj.get("ADJST_YN"));
					}else {
						obj.put("ADJUST_YN","0");
					}
					obj.put("CREATED_BY", obj.get("CREATED_BY"));
					obj.put("CREATED_IP", obj.get("CREATED_IP"));
					obj.put("ACTIVITY", obj.get("ACTIVITY"));
					obj.put("CURRENT_AFFIRM_ID",obj.get("CURRENT_AFFIRM_ID"));
					this.insert("ess.infoApply.insertOvertimeApply", obj);
			
				if (obj != null) {
					List<LinkedHashMap> aList = (List<LinkedHashMap>) obj.get("affirmList");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
						tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
						    tempMap.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
						    tempMap.put("ACTIVITY", obj.get("ACTIVITY"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							tempMap.put("APPLY_TYPE_NO",obj.get("APPLY_TYPE_CODE"));
							tempMap.put("APPLY_NO_SEQ", essApplySeq);
							this.insert("ess.infoApplyLeave.insertApplyReviewer2",tempMap);
						}
					}
				}
	}
	
	
	/**
	 * 批量添加倒休管理的数据
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addOtApplyInArDetailBatchAdjust(LinkedHashMap obj) throws Exception {
	
				LinkedHashMap tempMap = new LinkedHashMap();
			
				int essApplySeq = getEssApplySeq();
					obj.put("APPLY_NO_SEQ", essApplySeq);
					obj.put("PERSON_ID", obj.get("PERSON_ID"));
					obj.put("APPLY_TYPE_NO","31");
					obj.put("OT_TYPE_CODE",obj.get("APPLY_TYPE_CODE"));
					obj.put("APPLY_OT_DATE", obj.get("APPLY_DATE"));
					obj.put("APPLY_TYPE", "BATCH");
					obj.put("LEAVE_TIME_TYPE", "31");
					String OT_FROM_TIME = (String) obj.get("OT_FROM_TIME");
					String OT_TO_TIME = (String) obj.get("OT_TO_TIME");
					obj.put("OT_FROM_TIME",OT_FROM_TIME);
					obj.put("OT_TO_TIME",OT_TO_TIME);
					obj.put("hour", obj.get("Lotlengthonehour"));
					obj.put("min", obj.get("Lotlengthonemin"));
					obj.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
					obj.put("APPLY_REMARK", "原因:"+getReasonString(obj)+"  其他原因:"+obj.get("otherReason"));
					obj.put("OT_TIME_TYPE","P");
					if (!"".equals(obj.get("ADJST_YN"))&&obj.get("ADJST_YN") != null) {
						obj.put("ADJUST_YN",obj.get("ADJST_YN"));
					}else {
						obj.put("ADJUST_YN","0");
					}
					obj.put("CREATED_BY", obj.get("CREATED_BY"));
					obj.put("CREATED_IP", obj.get("CREATED_IP"));
					obj.put("ACTIVITY", obj.get("ACTIVITY"));
					obj.put("CURRENT_AFFIRM_ID",obj.get("CURRENT_AFFIRM_ID"));
					this.insert("ess.infoApply.insertOvertimeApplyAdjust", obj);
			
				if (obj != null) {
					List<LinkedHashMap> aList = (List<LinkedHashMap>) obj.get("affirmList");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
						tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
						    tempMap.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
						    tempMap.put("ACTIVITY", obj.get("ACTIVITY"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							tempMap.put("APPLY_TYPE_NO",obj.get("APPLY_TYPE_CODE"));
							tempMap.put("APPLY_NO_SEQ", essApplySeq);
							this.insert("ess.infoApplyLeave.insertApplyReviewer2",tempMap);
						}
					}
				}
	}
	/**
	 * 批量保存加班管理的数据 TSTO
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public int saveArOvertimeManagent(Object obj,AdminBean admin) throws Exception {
//		List<LinkedHashMap<String, Object>> dataList = (List<LinkedHashMap<String, Object>>)obj ;
//		LinkedHashMap personMap = new LinkedHashMap();
//		personMap.put("APPLY_PERSON", admin.getPersonId());
//		for(int i=0;i<dataList.size();i++){
//			LinkedHashMap paramMap = (LinkedHashMap) dataList.get(i);
//		    personMap = (LinkedHashMap) this.getPersonInfoByPersonId(paramMap);
//		    String AFFIRM_FLAG = (String) paramMap.get("AFFIRM_FLAG");
//			boolean passFlag = false;
//			passFlag = this.arApplyCheckBatchOverTime(paramMap, personMap, admin.getLanguage());
//			String PK_NO = (String) paramMap.get("APPLY_NO");
//			paramMap.put("PK_NO", PK_NO);
//			paramMap.put("CPNY_ID", "TSTO");
//			
//			//获取数据库该条选中数据的决裁状态
//			String oldAffrim = infoApplyLeaveDao.getChechedAffrim(paramMap) ;
//			paramMap.put("oldAffrim", oldAffrim);
//			
//			//修改考勤的班次及时间
//			if (!paramMap.get("OLD_SHIFT_NO").equals(paramMap.get("SHIFT_NO"))) {
//				String kaoQinNo = "";
//				   kaoQinNo = infoApplyLeaveDao.getkaoQinNo(paramMap);
//				
//				if ("141439".equals(kaoQinNo) || "141440".equals(kaoQinNo)) {
//					String APPLY_NO_FOR_SHIFTNO = infoApplyLeaveDao.getkaoQinNoPkNo(paramMap);
//					//通过加班修改班次，获取修改班次的开始结束时间
//					LinkedHashMap flMap = new LinkedHashMap();
//					flMap.put("CPNY_ID",admin.getCpnyId() );
//					flMap.put("from_date", paramMap.get("APPLY_DATE"));
//					flMap.put("SHIFT_NO", paramMap.get("SHIFT_NO"));
//					LinkedHashMap timeMap  = infoApplyDao.getOtApplyWorkTimeU(flMap) ;
//					paramMap.put("FIRST_FOR_SHIFTNO",timeMap.get("FIRST_TIME"));
//					paramMap.put("LAST_FOR_SHIFTNO", timeMap.get("LAST_TIME"));
//					//修改考勤的班次，开始结束时间
//					paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
//					this.infoApplyDao.updatArDetailBatchApplyForSHIFT(paramMap);
//				}else {
//					throw new CommonException(personMap.get("LOCAL_NAME")+ "已经进行了休假申请");
//				}
//				
//			 }
//			//修改考勤的类型
//			if (!paramMap.get("OLD_DATE_TYPE").equals(paramMap.get("DATE_TYPE"))) {
//				String kaoQinNo = "";
//					 kaoQinNo = infoApplyLeaveDao.getkaoQinNo(paramMap);
//				if ("141439".equals(kaoQinNo) || "141440".equals(kaoQinNo)) {
//					String APPLY_NO_FOR_SHIFTNO = infoApplyLeaveDao.getkaoQinNoPkNo(paramMap);
//					paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
//					this.infoApplyDao.updatArDetailBatchApplyForDATETYPE(paramMap);
//				}else {
//					throw new CommonException(personMap.get("LOCAL_NAME")+ "已经进行了休假申请");
//				}
//				
//			}
//			//修改考勤的班组
//			if (!paramMap.get("OLD_GROUP_ID").equals(paramMap.get("GROUP_ID"))) {
//				String kaoQinNo = "";
//				
//				   kaoQinNo = infoApplyLeaveDao.getkaoQinNo(paramMap);
//				if ("141439".equals(kaoQinNo) || "141440".equals(kaoQinNo)) {
//					String APPLY_NO_FOR_SHIFTNO = infoApplyLeaveDao.getkaoQinNoPkNo(paramMap);
//					paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
//					this.infoApplyDao.updatArDetailBatchApplyForGROUP(paramMap);
//				}else {
//					throw new CommonException(personMap.get("LOCAL_NAME")+ "已经进行了休假申请");
//				}
//				
//			}
//			
//			 //开始:14014306; 部门申请: 14014307;部门长批准 ：14014308;部门长返回:14014309;上申取消:14014310;部门申请(后):14014311;部门长批准(后):14014312
//			if (passFlag==true) {
//				if ("14014306".equals(oldAffrim)) {
//					//如果做部门申请，初始状态就做插入
//					this.infoApplyDao.addOtApplyInArDetailBatch(paramMap);
//					//this.infoApplyDao.updatSubmitLeaveApplyInBatchForDeletePrepare(paramMap); 
//			        //this.infoApplyDao.insertSubmitLeaveApplyInBatch(paramMap); 
//					this.infoApplyDao.updatSubmitArDetailBatchApplyForFirstAdd(paramMap);//修改detail表
//				} else {
//					//部门长申请(后)
//					if ("14014306".equals(AFFIRM_FLAG)) {
//						//把原先的记录修改为初始
//						String SHIFT_NO = (String) paramMap.get("OLD_SHIFT_NO");
//						LinkedHashMap dataMap = new LinkedHashMap();
//						dataMap.put("SHIFT_NO",SHIFT_NO);
//						dataMap.put("FROM_DATE",paramMap.get("APPLY_DATE"));
//						dataMap.put("CPNY_ID",admin.getCpnyId());
//					    LinkedHashMap   shiftMap  = this.getWorkTimeFirstLast(dataMap);  
//						
//						paramMap.put("OT_FROM_TIME", shiftMap.get("FIRST_TIME"));
//						paramMap.put("OT_TO_TIME", shiftMap.get("LAST_TIME"));
//						paramMap.put("APPLY_LENGTH", 0.00);
//						this.infoApplyDao.updatSubmitArDetailBatchApplyForChuShi(paramMap);
//						this.infoApplyDao.updateOtApplyInArDetailBatchApply(paramMap);//修改申请表
//					}else {
//						this.infoApplyDao.updatSubmitArDetailBatchApply(paramMap);
//						this.infoApplyDao.updateOtApplyInArDetailBatchApply(paramMap);//修改申请表
//					}
//					
//				}
//			} else {
//				String ERROR_MSG = (String) paramMap.get("ERROR_MSG");
//				 throw new CommonException(ERROR_MSG);
//			}
//			
//		}
//	}
    /**
     * 加班修改
     */
	@SuppressWarnings("unchecked")
	@Override
	public void updatSubmitArDetailBatchApply(LinkedHashMap paramMap)throws Exception {
		String ADJUST_YN = paramMap.get("ADJST_YN").toString();
		String OLD_ADJST_YN = paramMap.get("OLD_ADJST_YN").toString();
		if("0".equals(OLD_ADJST_YN)&&"1".equals(ADJUST_YN)&&!"".equals(ADJUST_YN)&&ADJUST_YN != null){
			this.insert("ess.infoApply.updatArDetailBatchApplyOtToAdjust", paramMap);
		}else {
		 this.update("ess.infoApply.updatSubmitArDetailBatchApply", paramMap);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void updatSubmitArDetailBatchApplyAdjust(LinkedHashMap paramMap)throws Exception {
		String ADJUST_YN = paramMap.get("ADJST_YN").toString();
		if("0".equals(ADJUST_YN)&&!"".equals(ADJUST_YN)&&ADJUST_YN != null){
			this.insert("ess.infoApply.updatArDetailBatchApplyAdjustToOt", paramMap);
		}else {
			this.update("ess.infoApply.updatSubmitArDetailBatchApplyAdjust", paramMap);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void updatSubmitArDetailBatchApplySST(LinkedHashMap paramMap)throws Exception {
		this.update("ess.infoApply.updatSubmitArDetailBatchApplySST", paramMap);
	}
	@SuppressWarnings("unchecked")
	public void updatSubmitArDetailBatchApplyForChuShi(LinkedHashMap paramMap)throws Exception {
		this.update("ess.infoApply.updatSubmitArDetailBatchApplyForChuShi", paramMap);
	}
	@SuppressWarnings("unchecked")
	public void updatSubmitArDetailBatchApplyAdjustForChuShi(LinkedHashMap paramMap)throws Exception {
		this.update("ess.infoApply.updatSubmitArDetailBatchApplyAdjustForChuShi", paramMap);
	}
	@SuppressWarnings("unchecked")
	public void updatSubmitArDetailBatchApplyForChuShiSST(LinkedHashMap paramMap)throws Exception {
		this.update("ess.infoApply.updatSubmitArDetailBatchApplyForChuShiSST", paramMap);
	}
	@Override
	public void updatArDetailBatchApplyForSHIFT(LinkedHashMap paramMap)throws Exception {
		this.update("ess.infoApply.updatArDetailBatchApplyForSHIFT", paramMap);
	}
	@Override
	public void updatArDetailBatchApplyForDATETYPE(LinkedHashMap paramMap)throws Exception {
		this.update("ess.infoApply.updatArDetailBatchApplyForDATETYPE", paramMap);
	}
	@Override
	public void updatArDetailBatchApplyForGROUP(LinkedHashMap paramMap)throws Exception {
		this.update("ess.infoApply.updatArDetailBatchApplyForGROUP", paramMap);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void updatSubmitArDetailBatchApplyForFirstAdd(LinkedHashMap paramMap)throws Exception {
		
		paramMap.put("APPLY_NO_FOR_DETAIL", getApplyNoForArDetail(paramMap));
		this.update("ess.infoApply.updatSubmitArDetailBatchApplyForFirstAdd", paramMap);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void updatSubmitArDetailBatchApplyForFirstAddAdjust(LinkedHashMap paramMap)throws Exception {
		
		paramMap.put("APPLY_NO_FOR_DETAIL", getApplyNoForArDetail(paramMap));
		this.update("ess.infoApply.updatSubmitArDetailBatchApplyForFirstAddAdjust", paramMap);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void updatSubmitArDetailBatchApplyForFirstAddSST(LinkedHashMap paramMap)throws Exception {
		
		paramMap.put("APPLY_NO_FOR_DETAIL", getApplyNoForArDetail(paramMap));
		this.update("ess.infoApply.updatSubmitArDetailBatchApplyForFirstAddSST", paramMap);
	}
	
	private String  getApplyNoForArDetail(Object obj) throws Exception {
		try {
		   String string =(String) queryForObject(("ess.infoApply.getApplyNoForArDetail"),
					obj);
			return string;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateOtApplyInArDetailBatchApply(LinkedHashMap obj) throws Exception {
	String APPLY_NO = getArDetailTstoApplyOfApplyNo(obj);
	if (!"".equals(APPLY_NO)&& APPLY_NO != null) {
		obj.put("hour", obj.get("Lotlengthonehour"));
		obj.put("min", obj.get("Lotlengthonemin"));
		String OT_FROM_TIME = (String) obj.get("OT_FROM_TIME");
		String OT_TO_TIME = (String) obj.get("OT_TO_TIME");
		obj.put("OT_TYPE_CODE",obj.get("APPLY_TYPE_CODE"));
		obj.put("OT_FROM_TIME",OT_FROM_TIME);
		obj.put("OT_TO_TIME",OT_TO_TIME);
		obj.put("APPLY_OT_DATE", obj.get("APPLY_DATE"));
	    String reasonString = this.getReasonString(obj);
		obj.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
		obj.put("APPLY_REMARK", "原因:"+reasonString+"  其他原因:"+obj.get("otherReason"));
		obj.put("UPDATED_BY", obj.get("CREATED_BY"));
		obj.put("UPDATE_IP", obj.get("CREATED_IP"));
		obj.put("ACTIVITY", obj.get("ACTIVITY"));
		obj.put("APPLY_NO", getArDetailTstoApplyOfApplyNo(obj));
		if (!"".equals(obj.get("ADJST_YN"))&&obj.get("ADJST_YN") != null) {
			obj.put("ADJUST_YN",obj.get("ADJST_YN"));
		}else {
			obj.put("ADJUST_YN","0");
		}
		this.update("ess.infoApply.updateOtApplyInArDetailBatchApply", obj);
		
			LinkedHashMap  tempMap = new LinkedHashMap();
			tempMap.put("APPLY_NO", APPLY_NO);
			tempMap.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
			tempMap.put("APPLY_TYPE", obj.get("APPLY_TYPE_CODE"));
			tempMap.put("ACTIVITY", obj.get("ACTIVITY"));
			tempMap.put("UPDATED_BY", obj.get("CREATED_BY"));
			this.update("ess.infoApply.updateOtApplyEssAffrim", tempMap);
		}                              
	}
	@SuppressWarnings("unchecked")
	@Override
	public void updateAdjustApplyInArDetailBatchApply(LinkedHashMap obj) throws Exception {
		String APPLY_NO = getArDetailTstoApplyOfApplyNo(obj);
		if (!"".equals(APPLY_NO)&& APPLY_NO != null) {
			obj.put("hour", obj.get("Lotlengthonehour"));
			obj.put("min", obj.get("Lotlengthonemin"));
			String OT_FROM_TIME = (String) obj.get("OT_FROM_TIME");
			String OT_TO_TIME = (String) obj.get("OT_TO_TIME");
			obj.put("OT_TYPE_CODE",obj.get("APPLY_TYPE_CODE"));
			obj.put("OT_FROM_TIME",OT_FROM_TIME);
			obj.put("OT_TO_TIME",OT_TO_TIME);
			obj.put("APPLY_OT_DATE", obj.get("APPLY_DATE"));
			String reasonString = this.getReasonString(obj);
			obj.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
			obj.put("APPLY_REMARK", "原因:"+reasonString+"  其他原因:"+obj.get("otherReason"));
			obj.put("UPDATED_BY", obj.get("CREATED_BY"));
			obj.put("UPDATE_IP", obj.get("CREATED_IP"));
			obj.put("ACTIVITY", obj.get("ACTIVITY"));
			obj.put("APPLY_NO", getArDetailTstoApplyOfApplyNo(obj));
			if (!"".equals(obj.get("ADJST_YN"))&&obj.get("ADJST_YN") != null) {
				obj.put("ADJUST_YN",obj.get("ADJST_YN"));
			}else {
				obj.put("ADJUST_YN","0");
			}
			this.update("ess.infoApply.updateOtApplyInArDetailBatchApply", obj);
			
			LinkedHashMap  tempMap = new LinkedHashMap();
			tempMap.put("APPLY_NO", APPLY_NO);
			tempMap.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
			tempMap.put("APPLY_TYPE", obj.get("APPLY_TYPE_CODE"));
			tempMap.put("ACTIVITY", obj.get("ACTIVITY"));
			tempMap.put("UPDATED_BY", obj.get("CREATED_BY"));
			this.update("ess.infoApply.updateOtApplyEssAffrim", tempMap);
		}                              
	}
	
	public String getArDetailTstoApplyOfApplyNo(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApply.getArDetailTstoApplyOfApplyNo", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}
	public String getArDetailTstoApplyOfPERSONID(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApply.getArDetailTstoApplyOfPERSONID", obj);
		return object2;
	}
	public String getArDetailTstoApplyOfApplyNoForOt(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ess.infoApply.getArDetailTstoApplyOfApplyNoForOt1", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}
	
	@Override
	public String getGradeYn(LinkedHashMap map) {
		
		Object obj = null;
		try {
			obj = this.queryForObject("ess.infoApply.getGradeYn",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return obj.toString();	
	}
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getWorkTimeFirstLast(LinkedHashMap map) {
		
		LinkedHashMap obj = null;
		try {
			obj = (LinkedHashMap) this.queryForObject("ess.infoApply.getWorkTimeFirstLast",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return obj;	
	}
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getOldWorkTimeInfoByPkNO(LinkedHashMap map) {
		
		LinkedHashMap obj = null;
		try {
			obj = (LinkedHashMap) this.queryForObject("ess.infoApply.getOldWorkTimeInfoByPkNO",map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return obj;	
	}
	/**
	 * 部门员工加班查询(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDeptOtInfoList(Object obj)  {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.getDeptOtInfoList",obj);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 内务加班查询页面(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCoordOtInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApply.getCoordOtInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 内务倒休查询页面(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCoordAdjustInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.getCoordAdjustInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getOverTimeLimtList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.getOverTimeLimtList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewPersonOverTimeLimtList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.viewPersonOverTimeLimtList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getOverTimeLimtShenPiList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.infoApply.getOverTimeLimtShenPiList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	
	/**
	 * 批量加班上限申请
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int approveOtLimitBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap map = (LinkedHashMap) list.get(i);
				this.update("ess.infoApply.updateOtLimit", map);//UPDATE DETAIL
		}
	return 1;
	}
	/**
	 * hub加班上限控制
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int approveOtLimitBatchHUB(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap map = (LinkedHashMap) list.get(i);
			this.update("ess.infoApply.approveOtLimitBatchHUB", map);//UPDATE DETAIL
		}
		return 1;
	}
	

	@Override
	public Object getShiftNoForOt(LinkedHashMap shiftMap) {
		try {
			   String string =(String) queryForObject(("ess.infoApply.getShiftNoForOt"),
					   shiftMap);
				return string;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getOtApplyShiftTime(LinkedHashMap worHashMap) {
		LinkedHashMap map = new LinkedHashMap();
		try {
			map = (LinkedHashMap) this.queryForObject(
					"ess.infoApply.getOtApplyShiftTime", worHashMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 加班验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOtCheckSST(LinkedHashMap worHashMap) {
		String result = "OK";
		try {
			result = StringUtil.checkNull( this.queryForObject(
					"ess.infoApply.getOtCheckSST", worHashMap));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 加班事后确认验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOtCheckSST2(LinkedHashMap worHashMap) {
		String result = "OK";
		try {
			result = StringUtil.checkNull( this.queryForObject(
					"ess.infoApply.getOtCheckSST2", worHashMap));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//查找明细表中加班的数据
	@SuppressWarnings("unchecked")
	public LinkedHashMap getOtArDetailForPkNoSST(LinkedHashMap worHashMap) {
		LinkedHashMap map = new LinkedHashMap();
		try {
			map = (LinkedHashMap) this.queryForObject(
					"ess.infoApply.getOtArDetailForPkNoSST", worHashMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}	

	/**
	 * SST加班申请
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addOvertimeApplySST(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap map = (LinkedHashMap) list.get(i);
				LinkedHashMap  obj = (LinkedHashMap) map.get("PARAM_MAP");
				
				int essApplySeq = getEssApplySeq();
				obj.put("APPLY_NO_SEQ", essApplySeq);
                //修改申请表
				this.insert("ess.infoApply.insertOvertimeApplySST", obj);
				/*//修改明细表
				LinkedHashMap detailMap = this.getOtArDetailForPkNoSST(obj);
				 
				obj.put("PK_NO",detailMap.get("PK_NO"));
				this.update("ess.infoApply.updatSubmitArDetailBatchApplyForPersonSST", obj);*/
				//修改决裁表
				LinkedHashMap personMap = (LinkedHashMap) map.get("personMap");
				obj.put("LAST_NAME", " OverTime Application(" + personMap.get("LOCAL_NAME") + ")[Date：" + obj.get("APPLY_DATE") + "]"); //加班申请
				obj.put("APPLY_PERSON_INFO", personMap.get("LOCAL_NAME") + "/" + StringUtil.checkNull(personMap.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(personMap.get("DEPTNAME")));

				//先插入申请人
				obj.put("AFFIRM_LEVEL", "0");
				obj.put("AFFIRMOR_ID", obj.get("adminID"));
				obj.put("AFFIRM_TYPE", "4");
				//如果加班只需多级审批者中的一人审批用一下
				this.insert("ess.infoApply.addOtSyAffirmInfo", obj);
				//this.insert("ess.infoApply.addSyAffirmInfo", obj);
				//插入审批人
				if (map != null && obj.get("affirmList") != null) {
					List<LinkedHashMap> aList = (List) obj.get("affirmList");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
							obj.put("AFFIRM_LEVEL",parmers.get("AFFIRM_LEVEL"));
							obj.put("AFFIRMOR_ID", parmers.get("AFFIRMOR_ID"));
							obj.put("AFFIRM_TYPE", parmers.get("AFFIRM_TYPE"));
							this.insert("ess.infoApply.addOtSyAffirmInfo",obj);
							//this.insert("ess.infoApply.addSyAffirmInfo", obj);
						}
					}
				}
			}
		}
	}

	/**
	 * SST 事后加班申请
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void modifyOvertimeApplySST(Map map) throws Exception {
		LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");

		// 修改申请表
		this.insert("ess.infoApply.modifyOvertimeApplySST", obj);
		// 修改明细表
		LinkedHashMap detailMap = this.getOtArDetailForPkNoSST(obj);

		obj.put("PK_NO", detailMap.get("PK_NO"));
		this.update("ess.infoApply.updatSubmitArDetailBatchApplyForPersonSST",
				obj);
		// 修改决裁表
		LinkedHashMap personMap = (LinkedHashMap) map.get("personMap");
		obj.put("LAST_NAME", "事后申请(" + personMap.get("LOCAL_NAME") + ")[日期："
				+ obj.get("APPLY_DATE") + "]");
		obj.put("APPLY_PERSON_INFO", personMap.get("LOCAL_NAME") + "/"
				+ StringUtil.checkNull(personMap.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(personMap.get("DEPTNAME")));

		//先插入申请人
		obj.put("AFFIRM_LEVEL", "0");
		obj.put("AFFIRMOR_ID", obj.get("adminID"));
		obj.put("AFFIRM_TYPE", "4");
		this.insert("ess.infoApply.addSyAffirmInfo", obj);
		//插入审批人
		if (map != null && obj.get("affirmList") != null) {
			List<LinkedHashMap> aList = (List) obj.get("affirmList");
			if (aList != null && aList.size() > 0) {
				for (LinkedHashMap parmers : aList) {
					obj.put("AFFIRM_LEVEL", parmers.get("AFFIRM_LEVEL"));
					obj.put("AFFIRMOR_ID", parmers.get("AFFIRMOR_ID"));
					obj.put("AFFIRM_TYPE", parmers.get("AFFIRM_TYPE"));
					this.insert("ess.infoApply.addSyAffirmInfo", obj);
				}
			}
		}
	}
	/**
	 * SST查询加班申请决裁信息-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getSSTOtAffirmInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApply.getSSTOtAffirmInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 审批箱查询
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewApprovalInfo(Object obj,String target) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.infoApply." + target,obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	

	/**
	 * 审批处理
	 * 
	 * @param
	 * @return by:wangqiang
	 */
	@SuppressWarnings("unchecked")
	public String executePro(Object object,String target) throws Exception {
		String returnString = "";
		try {
			LinkedHashMap paramMap = (LinkedHashMap) object;
			paramMap.put("message", "");
			this.insert("ess.infoApply." + target, paramMap);
			/*int i = (Integer) this.queryForObject("ess.infoApply.getIsAffirm", paramMap);
			if(i==0&&(paramMap.get("APPLY_TYPE").toString().equals("141443")||paramMap.get("APPLY_TYPE").toString().equals("141441")||paramMap.get("APPLY_TYPE").toString().equals("141442")||paramMap.get("APPLY_TYPE").toString().equals("14015448"))){
				this.insert("ess.infoApply.PR_ATTENDANCEEX_CONFIRM",paramMap);
			}*/
			
			//2017/11/1 lipeng注释
			/*int j = (Integer) this.queryForObject("ess.infoApply.getAffirmIsNot", paramMap);
			if(j>0){
				this.delete("ess.infoApply.delSyEmail",paramMap);
			}*/
			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			throw e;
		}
		return returnString;
	}
	
	@SuppressWarnings("unchecked")
	public int viewModifyApprovalInfo(Object object,String target)throws Exception {
		this.update("ess.infoApply." + target,object);
		return 1;
	}
	
	/**
	 * 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtLimitDataImportResultList(Object object) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("ess.infoApply.getOtLimitDataImportResultList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}	
	
	/**
	 * 
	 * @param 
	 * @return
	 */
	public String insertOTLimit(Map<String, Object> paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.update("pa.excelUtil.insertOTLimit", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}
	
	/**
	 * 倒休验证
	 * @param worHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCoordAdjustCheckTSTO(LinkedHashMap worHashMap) {
		String result = "OK";
		try {
			result = StringUtil.checkNull( this.queryForObject(
					"ess.infoApply.getCoordAdjustCheckTSTO", worHashMap));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	
	
	

	/**
	 * 判断明天是否休息日
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int isWeekend() throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(
				this.queryForObject("ess.infoApply.isWeekend", null)),Integer.class);
	}
	
	/**
	 * 保存加班信息
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveOtApplyAffirmForBatch(Map map) throws Exception {
		int returnInt = 0;
		List list = (List) map.get("otApplyAffirmList");
		Map paramMapo = (Map) map.get("paramMapo");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				returnInt = 0;
			    LinkedHashMap personMap = (LinkedHashMap) list.get(i);
                	personMap.put("adminID", paramMapo.get("adminID"));
                	personMap.put("adminIP", paramMapo.get("adminIP"));
                	personMap.put("CPNY_ID", paramMapo.get("CPNY_ID"));
                	personMap.put("interLanguage", paramMapo.get("interLanguage"));
                	personMap.put("BATCH_NO", "");
                	this.update("ess.infoApplyLeave.PR_DELETE_OT_CONFIRM",personMap);
                	this.delete("ess.infoApplyLeave.delArApplyResult",personMap);
                	//String dutyNo = (String) this.queryForObject("ess.infoApplyLeave.getDutyNoStr", personMap);
            		String otTypeCode = (String)personMap.get("OT_TYPE_CODE");
            		LinkedHashMap personMap1 = (LinkedHashMap) this.getPersonInfoByPersonId(personMap);
            		List affirmorList = this.infoApplySer.getAffirmorListByString("31",personMap.get("PERSON_ID").toString(),otTypeCode,personMap.get("OT_APPLY_HOUR").toString(), personMap.get("interLanguage").toString());		
            		if(affirmorList.size()<=0){
            			throw new Exception(personMap1.get("LOCAL_NAME")+"You Can't Apply Without the Approver");//没有审批者,不能申请
            		}
            		personMap1.put("sql", "select AR_GET_OT_CLASH('"+personMap.get("APPLY_NO")+"','"+personMap.get("PERSON_ID")+"','"+personMap.get("OT_FROM_TIME_CONVERT")+"','"+personMap.get("OT_TO_TIME_CONVERT")+"','"+personMap.get("OFFSET_YN")+"') FLAG from dual");
            		List otConfirm = recruitManageDao.doSql(personMap1);
            		LinkedHashMap otConfirmMap = (LinkedHashMap) otConfirm.get(0);
            		if (otConfirmMap.get("FLAG") == "-3" || "-3".equals(otConfirmMap.get("FLAG"))) {
            			throw new Exception(personMap1.get("LOCAL_NAME")+": Limit Overtime (30 or 100 hours)");//
            		}
            		this.update("ess.infoApply.updateOtApplyAffirmForSpc",personMap);
            		
            		personMap.put("LAST_NAME", "OverTime Application(" + personMap1.get("LOCAL_NAME") + ")[Date：" + personMap.get("APPLY_OT_DATE") + "]");//加班申请
            		personMap.put("APPLY_PERSON_INFO", personMap1.get("LOCAL_NAME") + "/" + StringUtil.checkNull(personMap1.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(personMap1.get("DEPTNAME")));

    				//先插入申请人
            		personMap.put("APPLY_NO_SEQ", personMap.get("APPLY_NO"));
            		personMap.put("APPLY_TYPE_CODE", otTypeCode);
            		personMap.put("AFFIRM_LEVEL", "0");
            		personMap.put("AFFIRMOR_ID", personMap.get("adminID"));
            		personMap.put("APPLY_AFFIRM_FLAG", "14014306");
            		personMap.put("APPLY_TYPE_NO", "31");
            		personMap.put("AFFIRM_TYPE", "4");
            		personMap.put("APPLY_FLAG", "0");
            		this.delete("ess.infoApply.deleteSyAffirmInfo", personMap);
            		//如果加班只需多级审批者中的一人审批用一下
    				this.insert("ess.infoApply.addOtSyAffirmInfo", personMap);
    				//this.insert("ess.infoApply.addSyAffirmInfo", personMap);
    				for(int j=0;j<affirmorList.size();j++){
            			if(!"".equals(affirmorList.get(j))){
            				LinkedHashMap map1 = (LinkedHashMap) affirmorList.get(j);
            				LinkedHashMap affirmMap = new LinkedHashMap();
            				personMap.put("AFFIRM_LEVEL", map1.get("AFFIRM_LEVEL"));
            				personMap.put("AFFIRMOR_ID", map1.get("AFFIRMOR_ID"));
            				personMap.put("PERSON_ID",personMap.get("APPLY_PERSON_ID"));
            				personMap.put("APPLY_TYPE_CODE",otTypeCode);
            				personMap.put("AFFIRM_TYPE", "1");
            				this.insert("ess.infoApply.addOtSyAffirmInfo", personMap); 
            				//this.insert("ess.infoApply.addSyAffirmInfo", personMap);
            			}
            		}
            	
            	returnInt = 1;
			}
		}
		return returnInt;
	}
	
	/**
	 * 保存加班信息(可添加删除审批者)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveOtApplyByAnyApproverForBatch(Map map) throws Exception {
		int returnInt = 0;
		List list = (List) map.get("otApplyAffirmList");
		Map paramMapo = (Map) map.get("paramMapo");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				returnInt = 0;
			    LinkedHashMap personMap = (LinkedHashMap) list.get(i);
                	personMap.put("adminID", paramMapo.get("adminID"));
                	personMap.put("adminIP", paramMapo.get("adminIP"));
                	personMap.put("CPNY_ID", paramMapo.get("CPNY_ID"));
                	personMap.put("interLanguage", paramMapo.get("interLanguage"));
                	personMap.put("BATCH_NO", "");
                	this.update("ess.infoApplyLeave.PR_DELETE_OT_CONFIRM",personMap);
                	this.delete("ess.infoApplyLeave.delArApplyResult",personMap);
                	//String dutyNo = (String) this.queryForObject("ess.infoApplyLeave.getDutyNoStr", personMap);
            		String otTypeCode = (String)personMap.get("OT_TYPE_CODE");
            		LinkedHashMap personMap1 = (LinkedHashMap) this.getPersonInfoByPersonId(personMap);
            		//lipeng 2018/02/23
            		List<LinkedHashMap<String, Object>> affirmorList = (List)personMap.get("affirmJsonData");
            		//List affirmorList = this.infoApplySer.getAffirmorListByString("31",personMap.get("PERSON_ID").toString(),otTypeCode,personMap.get("OT_APPLY_HOUR").toString(), personMap.get("interLanguage").toString());		
            		if(affirmorList.size()<=0){
            			throw new Exception(personMap1.get("LOCAL_NAME")+"You Can't Apply Without the Approver");//没有审批者,不能申请
            		}
            		this.update("ess.infoApply.updateOtApplyAffirmForSpc",personMap);
            		
            		personMap.put("LAST_NAME", "OverTime Application(" + personMap1.get("LOCAL_NAME") + ")[Date：" + personMap.get("APPLY_OT_DATE") + "]");//加班申请
            		personMap.put("APPLY_PERSON_INFO", personMap1.get("LOCAL_NAME") + "/" + StringUtil.checkNull(personMap1.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(personMap1.get("DEPTNAME")));

    				//先插入申请人
            		personMap.put("APPLY_NO_SEQ", personMap.get("APPLY_NO"));
            		personMap.put("APPLY_TYPE_CODE", otTypeCode);
            		personMap.put("AFFIRM_LEVEL", "0");
            		personMap.put("AFFIRMOR_ID", personMap.get("adminID"));
            		personMap.put("APPLY_AFFIRM_FLAG", "14014306");
            		personMap.put("APPLY_TYPE_NO", "31");
            		personMap.put("AFFIRM_TYPE", "4");
            		personMap.put("APPLY_FLAG", "0");
            		this.delete("ess.infoApply.deleteSyAffirmInfo", personMap);
            		//如果加班只需多级审批者中的一人审批用一下
    				this.insert("ess.infoApply.addOtSyAffirmInfo", personMap);
    				//this.insert("ess.infoApply.addSyAffirmInfo", personMap);
    				for(int j=0;j<affirmorList.size();j++){
            			if(!"".equals(affirmorList.get(j))){
            				LinkedHashMap map1 = (LinkedHashMap) affirmorList.get(j);
            				LinkedHashMap affirmMap = new LinkedHashMap();
            				personMap.put("AFFIRM_LEVEL", map1.get("AFFIRM_LEVEL"));
            				personMap.put("AFFIRMOR_ID", map1.get("AFFIRMOR_ID"));
            				personMap.put("PERSON_ID",personMap.get("APPLY_PERSON_ID"));
            				personMap.put("APPLY_TYPE_CODE",otTypeCode);
            				personMap.put("AFFIRM_TYPE", "1");
            				this.insert("ess.infoApply.addOtSyAffirmInfo", personMap); 
            				//this.insert("ess.infoApply.addSyAffirmInfo", personMap);
            			}
            		}
            	
            	returnInt = 1;
			}
		}
		return returnInt;
	}
	
	/**
	 * 批量保存生产值加班信息
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveOTApplyInfoForBatchHAE(Map map) throws Exception {
		int returnInt = 0;
		
		/*为了批量申请但申请时间一样*/		
		Date nowDate = new Date();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    String applyTime = sdf.format(nowDate);
		
		List list = (List) map.get("otApplyAffirmList");
		List affirmorList = (List) map.get("affirmList");
		Map paramMapo = (Map) map.get("paramMapo");
		int batchNoSeq = getEssApplyBatchNoSeq();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				returnInt = 0;
			    LinkedHashMap personMap = (LinkedHashMap) list.get(i);
                	personMap.put("adminID", paramMapo.get("adminID"));
                	personMap.put("adminIP", paramMapo.get("adminIP"));
                	personMap.put("CPNY_ID", paramMapo.get("CPNY_ID"));
                	personMap.put("interLanguage", paramMapo.get("interLanguage"));
                	
                	/*2017/12/28 lipeng 为了同时审批*/
                	personMap.put("BATCH_NO", batchNoSeq);
                	personMap.put("APPLY_TIME", applyTime);
                	
                	this.update("ess.infoApplyLeave.PR_DELETE_OT_CONFIRM",personMap);
                	this.delete("ess.infoApplyLeave.delArApplyResult",personMap);
                	//String dutyNo = (String) this.queryForObject("ess.infoApplyLeave.getDutyNoStr", personMap);
            		String otTypeCode = (String)personMap.get("OT_TYPE_CODE");
            		LinkedHashMap personMap1 = (LinkedHashMap) this.getPersonInfoByPersonId(personMap);
            		//List affirmorList = this.infoApplySer.getAffirmorListByString("31",personMap.get("PERSON_ID").toString(),otTypeCode,personMap.get("OT_APPLY_HOUR").toString(), personMap.get("interLanguage").toString());		
            		if(affirmorList.size()<=0){
            			throw new Exception(personMap1.get("LOCAL_NAME")+"You Can't Apply Without the Approver");//没有审批者,不能申请
            		}
            		this.update("ess.infoApply.updateOtApplyAffirmForSameTime",personMap);
            		
            		personMap.put("LAST_NAME", "OverTime Application(" + personMap1.get("LOCAL_NAME") + ")[Date：" + personMap.get("APPLY_OT_DATE") + "]");//加班申请
            		personMap.put("APPLY_PERSON_INFO", personMap1.get("LOCAL_NAME") + "/" + StringUtil.checkNull(personMap1.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(personMap1.get("DEPTNAME")));

    				//先插入申请人
            		personMap.put("APPLY_NO_SEQ", personMap.get("APPLY_NO"));
            		personMap.put("APPLY_TYPE_CODE", otTypeCode);
            		personMap.put("AFFIRM_LEVEL", "0");
            		personMap.put("AFFIRMOR_ID", personMap.get("adminID"));
            		personMap.put("APPLY_AFFIRM_FLAG", "14014306");
            		personMap.put("APPLY_TYPE_NO", "31");
            		personMap.put("AFFIRM_TYPE", "4");
            		personMap.put("APPLY_FLAG", "0");
            		this.delete("ess.infoApply.deleteSyAffirmInfo", personMap);
            		//如果加班只需多级审批者中的一人审批用一下
    				//this.insert("ess.infoApply.addOtSyAffirmInfo", personMap);
    				this.insert("ess.infoApply.addSyAffirmInfo", personMap);
    				for(int j=0;j<affirmorList.size();j++){
            			if(!"".equals(affirmorList.get(j))){
            				LinkedHashMap map1 = (LinkedHashMap) affirmorList.get(j);
            				LinkedHashMap affirmMap = new LinkedHashMap();
            				personMap.put("AFFIRM_LEVEL", map1.get("AFFIRM_LEVEL"));
            				personMap.put("AFFIRMOR_ID", map1.get("AFFIRMOR_ID"));
            				personMap.put("PERSON_ID",personMap.get("APPLY_PERSON_ID"));
            				personMap.put("APPLY_TYPE_CODE",otTypeCode);
            				personMap.put("AFFIRM_TYPE", "1");
            				//this.insert("ess.infoApply.addOtSyAffirmInfo", personMap); 
            				this.insert("ess.infoApply.addSyAffirmInfo", personMap);
            			}
            		}
            	
            	returnInt = 1;
			}
		}
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int saveAdjustHolidayApplyForBatch(Map map) throws Exception {
		int returnInt = 0;
		List list = (List) map.get("adjustHolidayApplyList");
		Map paramMapo = (Map) map.get("paramMapo");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				returnInt = 0;
			    LinkedHashMap personMap = (LinkedHashMap) list.get(i);
                	personMap.put("adminID", paramMapo.get("adminID"));
                	personMap.put("adminIP", paramMapo.get("adminIP"));
                	personMap.put("CPNY_ID", paramMapo.get("CPNY_ID"));
                	personMap.put("interLanguage", paramMapo.get("interLanguage"));
                	this.update("ess.infoApplyLeave.PR_DELETE_OT_CONFIRM",personMap);
                	this.delete("ess.infoApplyLeave.delArApplyResult",personMap);
                	if(!personMap.get("CPNY_ID").equals("HTSV")){
                		String otTypeCode = (String) this.queryForObject("ess.infoApply.getOtTypeCodeNo", personMap);
                		LinkedHashMap personMap1 = (LinkedHashMap) this.getPersonInfoByPersonId(personMap);
                		
                		List affirmorList = this.infoApplySer.getAffirmorListByString("31",personMap.get("PERSON_ID").toString(),otTypeCode,personMap.get("AD_OT_APPLY_HOUR").toString(), personMap.get("LANGUAGE").toString());		
                		if(affirmorList.size()<=0){
                			throw new Exception(personMap1.get("LOCAL_NAME")+"没有审批者,不能申请");
                		}
                		this.update("ess.infoApply.updateAdjustHolidayApplyForBatch",personMap);
                		
                		personMap.put("LAST_NAME", "加班申请(" + personMap1.get("LOCAL_NAME") + ")[日期：" + personMap.get("APPLY_OT_DATE") + "]");
                		personMap.put("APPLY_PERSON_INFO", personMap1.get("LOCAL_NAME") + "/" + StringUtil.checkNull(personMap1.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(personMap1.get("DEPTNAME")));

        				//先插入申请人
                		personMap.put("APPLY_NO_SEQ", personMap.get("APPLY_NO"));
                		personMap.put("APPLY_TYPE_CODE", otTypeCode);
                		personMap.put("AFFIRM_LEVEL", "0");
                		personMap.put("AFFIRMOR_ID", personMap.get("adminID"));
                		personMap.put("APPLY_AFFIRM_FLAG", "14014306");
                		personMap.put("APPLY_TYPE_NO", "31");
                		personMap.put("AFFIRM_TYPE", "4");
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
                				personMap.put("APPLY_TYPE_CODE",otTypeCode);
                				personMap.put("AFFIRM_TYPE", "1");
                				this.insert("ess.infoApply.addSyAffirmInfo", personMap); 
                			}
                		}
                	}else{
                		this.update("ess.infoApply.updateAdjustHolidayApplyForBatch",personMap);
                		String otTypeCode = (String) this.queryForObject("ess.infoApply.getOtTypeCodeNo", personMap);
                		LinkedHashMap personMap1 = (LinkedHashMap) this.getPersonInfoByPersonId(personMap);
                		personMap.put("LAST_NAME", "加班申请(" + personMap1.get("LOCAL_NAME") + ")[日期：" + personMap.get("APPLY_OT_DATE") + "]");
                		personMap.put("APPLY_PERSON_INFO", personMap1.get("LOCAL_NAME") + "/" + StringUtil.checkNull(personMap1.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(personMap1.get("DEPTNAME")));

        				//先插入申请人
                		personMap.put("APPLY_NO_SEQ", personMap.get("APPLY_NO"));
                		personMap.put("APPLY_TYPE_CODE", otTypeCode);
                		personMap.put("AFFIRM_LEVEL", "0");
                		personMap.put("AFFIRMOR_ID", personMap.get("adminID"));
                		personMap.put("APPLY_AFFIRM_FLAG", "14014308");
                		personMap.put("APPLY_TYPE_NO", "31");
                		personMap.put("AFFIRM_TYPE", "4");
                		personMap.put("APPLY_FLAG", "0");
                		this.delete("ess.infoApply.deleteSyAffirmInfo", personMap);
        				this.insert("ess.infoApply.addSyAffirmInfo", personMap);
                	}
                	returnInt = 1;
			}
		}
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int delOtApplyAffirmForBatch(List list) throws Exception {
		try {
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.update("ess.infoApplyLeave.PR_DELETE_OT_CONFIRM",obj);
				//this.delete("ess.infoApply.delOtApplyAffirmForBatch", obj);//删除申请表
				//this.delete("ess.infoApplyLeave.delArApplyResult",obj);//删除申请表
				this.update("ess.infoApply.updateApplyOtTable", obj);
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
	public int delAdjustHolidayApplyForBatch(List list) throws Exception {
		try {
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.update("ess.infoApplyLeave.PR_DELETE_OT_CONFIRM", obj);//删除申请表
				this.delete("ess.infoApply.delAdjustHolidayApplyForBatch", obj);//删除申请表
				this.delete("ess.infoApplyLeave.delArApplyResult",obj);
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
	public void addOtApplyAffirm(Map map) throws Exception {
		int essApplySeq = getEssApplySeq();
		LinkedHashMap obj = (LinkedHashMap) map.get("paramMapo");
		obj.put("APPLY_NO_SEQ",essApplySeq);
		this.insert("ess.infoApply.addOtApplyAffirm",obj);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void addAdjustHolidayApply(Map map) throws Exception {
		int essApplySeq = getEssApplySeq();
		LinkedHashMap obj = (LinkedHashMap) map.get("paramMapo");
		obj.put("APPLY_NO_SEQ",essApplySeq);
		this.insert("ess.infoApply.addAdjustHolidayApply",obj);
	}

	@Override
	public List getValidateInfo(Map map) throws Exception {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("ess.infoApply.getValidateInfo", map);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	public List getOtTempList(LinkedHashMap paramMap) throws Exception {
		return  this.queryForList("ess.infoApply.getOtTempList", paramMap);
	}
	@Override
	public int getOtTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.infoApply.getOtTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
		@Override
	public int getOtTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.infoApply.getOtTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
		
		@SuppressWarnings("unchecked")
		@Override
		public Map getOtLength(Object obj) {
			Map map = new LinkedHashMap();
			try {
					map = (Map) this.queryForObject("ess.infoApply.getOtLength",obj);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return map;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Map getOtShiftTime(Object obj) {
			Map map = new LinkedHashMap();
			try {
					map = (Map) this.queryForObject("ess.infoApply.getOtShiftTime",obj);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return map;
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
		
		@Override
		public int delOTExForBatchInfoListHAE(Object obj) throws Exception {
			return NumberUtils.parseNumber(ObjectUtils.toString(
					this.delete("ess.infoApply.delOTExForBatchInfoListHAE", obj)),Integer.class);
		}
		
		@Override
		public void updateDeductYn(Object object){
			try {
				this.update("ess.infoApply.updateDeductYn", object) ;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
}