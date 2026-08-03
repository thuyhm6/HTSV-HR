package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArDetailDao;

import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArDetailDaoImpl.java
 * @Description:
 * @Create date: 2012-2-9 下午01:19:27
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ArDetailDaoImpl extends SqlMapClientSupport implements ArDetailDao {

	/**
	 * 取得所有考勤明细人员列表(get ArDetail List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArDetailList(Object object) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();

		returnList = this.getArDetailList(object, -1, -1);

		return returnList;
	}
	
	/**
	 * 取得所有考勤明细异常人员列表(get ArDetail List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttendanceExceptionList(Object object) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();

		returnList = this.getAttendanceExceptionList(object, -1, -1);

		return returnList;
	}

	
	
	/**
	 * 取得所有考勤明细人员列表(get ArDetail List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArDetailList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			/*Map paramMap = (Map)obj;
			if(paramMap.get("BIAO") == null){*/
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList("ar.detail.getArDetailList",
							obj, currentPage, pageSize);
				} else {
					returnList = this
							.queryForList("ar.detail.getArDetailList", obj);
				}
			/*}else{
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList("ar.detail.getArDetailListbiao",
							obj, currentPage, pageSize);
				} else {
					currentPage = 1;
					pageSize =  10;
					returnList = this.queryForList("ar.detail.getArDetailListbiao",
							obj, currentPage, pageSize);
			    }
			}*/

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	/**
	 * 取得所有考勤异常明细人员列表(get ArDetail List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttendanceExceptionList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ar.detail.getAttendanceExceptionList",
						obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList("ar.detail.getAttendanceExceptionList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	
	/**
	 * 取得所有考勤明细人员列表(get ArDetail List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArDetailListExcel(Object obj) {
		List returnList = new ArrayList();
		try {
			
		 returnList = this.queryForList("ar.detail.getArDetailListbiao",
							obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}


	
	@SuppressWarnings("unchecked")
	public int getArDetailListCnt(Object object) {
		int returnInt = 0;

		try {
			Map paramMap = (Map)object;
			if(paramMap.get("BIAO") == null){
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.detail.getArDetailListCnt", object)),
					Integer.class);
			}else{
				returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
						.queryForObject("ar.detail.getArDetailListbiaoCnt", object)),
						Integer.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 取考勤项目列表(get Item List)
	 * 
	 * @param Object
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getItemList(Object object) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		try {

			returnList = this.queryForList("ar.detail.getItemList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public int getStartDateStr(Object object) {
		// TODO Auto-generated method stub
		int result = 0;

		try {
			result = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.detail.getStartDateStr", object), "0"),
					Integer.class);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;

	}

	/**
	  * 修改考勤明细信息(update ArDetail Info)
	  * @param List
	  * @return void
	  * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateArDetailInfo(List list) throws Exception {

		this.updateForList("ar.detail.updateArDetailInfo", list);
	}

	/**
	 * 验证考勤是否锁定(validate DailyLock)
	 * 
	 * @param Object
	 * @return Object
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public Object validateDailyLock(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("ar.detail.validateDailyLock", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public Object validateDetailItemType(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("ar.detail.validateDetailItemType",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	  * 删除考勤明细信息(delete ArDetail Info)
	  * @param List
	  * @return void
	  * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteArDetailInfo(List list) throws Exception {
		this.deleteForList("ar.detail.deleteArDetailInfo", list);
	}

	/**
	  * 添加考勤明细信息(add ArDetail Info)
	  * @param request
	  * @return 
	  * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addArDetailInfo(List list) throws Exception {

		this.insertForList("ar.detail.addArDetailInfo", list);
	}
	
	/**
	  * 添加考勤明细信息(add ArDetail Info)
	  * @param request
	  * @return 
	  * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addArDetailInfo1(List list,List list1) throws Exception {

		//this.deleteForList("ar.detail.deleteArDetailInfo", list);
		this.insertForList("ar.detail.addArDetailInfo", list1);
	}

	@SuppressWarnings("unchecked")
	public Object getArDetailInfo(Object obj) {
		Object returnObj = new Object();
		List returnList = this.getArDetailList(obj);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}

	/**
	 * 邮件发送每日员工考勤异常明细信息(SearchArDetailExceptionInfo)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public List SearchArDetailExceptionInfo(Object object) {
		List returnList = null;
		try {
			returnList = this.queryForList(
					"ar.detail.SearchArDetailExceptionInfo", object);
			System.out.println(returnList.size()+"********************8");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return returnList;
	}

	/**
	 * 每周五发送部门异常信息给领导(SearchArDetailExceptionInfo)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public List SearchArDetailDeptExToMa(Object obj) {
		// TODO Auto-generated method stub
		List returnList = null;
		try {
			returnList = this.queryForList(
					"ar.detail.SearchArDetailDeptExToMa", obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return returnList;
	}

	/* (non-Javadoc)
	 * @see com.ait.ar.dao.ArDetailDao#SearchManagerInfo(java.lang.Object)
	 */
	@Override
	public List SearchManagerInfo(Object object) {
		// TODO Auto-generated method stub
		List returnList = null;
		try {
			returnList = this.queryForList(
					"ar.detail.SearchManagerInfo", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 明细维护  进行操作的时候保存操作记录  该条记录重点信息和操作类型（增加  修改  删除）和操作人
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addArDetailHistoryInfo(Object object) throws Exception{
		this.insert("ar.detail.addArDetailHistoryInfo", object);
	}
	@SuppressWarnings("unchecked")
	public String getModifyYnBySupervisorId(String object) {
		// TODO Auto-generated method stub
		String result = "";

		try {
			result =  StringUtil.checkNull(this.queryForObject("ar.detail.getModifyYnBySupervisorId", object));
		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;

	}
	@SuppressWarnings("unchecked")
	public String getItemNoOnApplyCode(LinkedHashMap object) {
		// TODO Auto-generated method stub
		String result = "";
		
		try {
			result =  StringUtil.checkNull(this.queryForObject("ar.detail.getItemNoOnApplyCode", object));
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return result;
		
	}
	/********************20150206 zyh 新增 start****************************/
	/**
	 *取得个人日考勤查看(get ArDetail List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPersonArDetailList(Object object) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		returnList = this.getPersonArDetailList(object, -1, -1);
		return returnList;
	}
	/**
	 * 取得个人日考勤查看(get ArDetail List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPersonArDetailList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			Map paramMap = (Map)obj;
			if(paramMap.get("BIAO") == null){
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList("ar.detail.getPersonArDetailList",
							obj, currentPage, pageSize);
				} else {
					returnList = this
							.queryForList("ar.detail.getPersonArDetailList", obj);
				}
			}else{
				if (currentPage > -1 && pageSize > -1) {
					returnList = this.queryForList("ar.detail.getArDetailListbiao",
							obj, currentPage, pageSize);
				} else {
					currentPage = 1;
					pageSize =  10;
					returnList = this.queryForList("ar.detail.getArDetailListbiao",
							obj, currentPage, pageSize);
			    }
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	/********************20150206 zyh 新增 end****************************/
	
	/**
	 * 获取明细导入信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArDetailTempList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getArDetailTempList(object, -1, -1);
		return returnList;
	}
	
	/**
	 * 获取明细导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getArDetailTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.detail.getArDetailTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.detail.getArDetailTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取明细导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getArDetailTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.detail.getArDetailTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取出错的明细导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getArDetailTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.detail.getArDetailTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	public String countArDetailLength(Object object){
		String returnInt = "0" ;
		try {
			returnInt = 
				  this.queryForObject("ar.detail.countArDetailLength", object).toString() ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	public int getEssApplySeq() {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this
					.queryForObject("ess.infoApply.getEssApplySeq"),0)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public int insertOverTimeApplyInfo(Object object) throws Exception {
		try {
			this.insert("ess.infoApply.insertOvertimeApply", object);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	public int insertAffirmListInfo(Object object) throws Exception {
		try {
			this.insert("ess.infoApply.insertApplyReviewer",object);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	public void updateArDetailInfoByNo(Object object) throws Exception {

		this.update("ar.detail.updateArDetailInfoByNo", object);
	}
	public void updateArDetailApplyInfoByNo(Object object) throws Exception {

		this.update("ar.detail.updateArDetailApplyInfoByNo", object);
	}
	public void updateArDetailAffirmInfoByNo(Object object) throws Exception {

		this.update("ar.detail.updateArDetailAffirmInfoByNo", object);
	}
	
	/**
	 * 加班管理页面,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmInfoListBatch(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.detail.getOtAffirmInfoListBatchForCoord",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 倒休管理页面,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewArAdjustHolidayManagent(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.detail.viewArAdjustHolidayManagent",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewArAdjustHolidayManagentNull(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.detail.viewArAdjustHolidayManagentNull",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 加班管理页面,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmInfoListBatchSST(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.detail.getOtAffirmInfoListBatchSST",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void updatSubmitArDetailBatchApply(LinkedHashMap paramMap)throws Exception {
		this.update("ar.detail.updatSubmitArDetailBatchApply", paramMap);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void updatSubmitArDetailBatchApplyForFirstAdd(LinkedHashMap paramMap)throws Exception {
		
		paramMap.put("APPLY_NO_FOR_DETAIL", getArDetailTstoApplyOfApplyNo(paramMap));
		this.update("ar.detail.updatSubmitArDetailBatchApplyForFirstAdd", paramMap);
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
					obj.put("ADJUST_YN",0);
					obj.put("CREATED_BY", obj.get("CREATED_BY"));
					obj.put("CREATED_IP", obj.get("CREATED_IP"));
					obj.put("ACTIVITY", obj.get("ACTIVITY"));
					obj.put("CURRENT_AFFIRM_ID",obj.get("CURRENT_AFFIRM_ID"));
					this.insert("ar.detail.insertOvertimeApply", obj);
			
				if (obj != null) {
					List<LinkedHashMap> aList = (List<LinkedHashMap>) obj.get("affirmList");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
						tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							tempMap.put("APPLY_TYPE_NO",obj.get("APPLY_TYPE_CODE"));
							tempMap.put("APPLY_NO_SEQ", essApplySeq);
							this.insert("ar.detail.insertApplyReviewer",tempMap);
						}
					}
				}
	}
	
	public String  getReasonString(Object obj) throws Exception {
		try {
			return  (String) queryForObject(("ar.detail.getReasonString"),
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateOtApplyInArDetailBatchApply(LinkedHashMap obj) throws Exception {
		obj.put("hour", obj.get("Lotlengthonehour"));
		obj.put("min", obj.get("Lotlengthonemin"));
		String OT_FROM_TIME = (String) obj.get("OT_FROM_TIME");
		String OT_TO_TIME = (String) obj.get("OT_TO_TIME");
		obj.put("OT_FROM_TIME",OT_FROM_TIME);
		obj.put("OT_TO_TIME",OT_TO_TIME);
		obj.put("APPLY_OT_DATE", obj.get("APPLY_DATE"));
	    String reasonString = this.getReasonString(obj);
		obj.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
		obj.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
		obj.put("APPLY_REMARK", "原因:"+reasonString+"  其他原因:"+obj.get("otherReason"));
		obj.put("UPDATED_BY", obj.get("CREATED_BY"));
		obj.put("UPDATE_IP", obj.get("CREATED_IP"));
		obj.put("ACTIVITY", obj.get("ACTIVITY"));
		obj.put("APPLY_NO", getArDetailTstoApplyOfApplyNo(obj));
		this.update("ar.detail.updateOtApplyInArDetailBatchApply", obj);
	}
	
	public String getArDetailTstoApplyOfApplyNo(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ar.detail.getArDetailTstoApplyOfApplyNo", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
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
	public List getSearchApplyOtInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ar.detail.getSearchApplyOtInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 班车页面(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getMyhomeCarInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.detail.getMyhomeCarInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 倒休搜索页面(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getSearchApplyAdjustInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.detail.getSearchApplyAdjustInfoList",obj);
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
	public List getApplyAttenanceManagentInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.detail.getApplyAttenanceManagentInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 加班管理查询多天假,(search employee result list and judge if pagenation）
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
			returnList = this.queryForList("ar.detail.getBatchLeaveAffirmMoreDayInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 批量添考勤申请(来自考勤管理页面)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addLeaveApplyInArDetailBatch(List list) throws Exception {
	if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap tempMap = new LinkedHashMap();
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				int essApplySeq = getEssApplySeq();
					obj.put("APPLY_NO_SEQ", essApplySeq);
					obj.put("PERSON_ID", obj.get("personId"));
					obj.put("APPLY_TYPE_NO","21");
					obj.put("APPLY_TYPE_CODE",getLeaveApplyCode(obj));
					obj.put("APPLY_TIME", obj.get("APPLY_DATE"));
					obj.put("APPLY_TYPE", "BATCH");
					obj.put("LEAVE_TIME_TYPE", "21");
					obj.put("LEAVE_FROM_TIME",obj.get("LEAVE_FROM_TIME"));
					obj.put("LEAVE_TO_TIME",obj.get("LEAVE_TO_TIME") );
					obj.put("APPLY_LENGTH", obj.get("APPLY_LENGTH"));
					obj.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
					obj.put("LEAVE_REASON", obj.get("reason"));
					obj.put("COORDINATOR_NO", "");
					obj.put("DESTINATION", "");
					obj.put("LIAISON", "");
					obj.put("CREATED_BY", obj.get("CREATED_BY"));
					obj.put("CREATED_IP", obj.get("CREATED_IP"));
					obj.put("ACTIVITY", obj.get("ACTIVITY"));
					obj.put("CURRENT_AFFIRM_ID",obj.get("CURRENT_AFFIRM_ID"));
					this.insert("ar.detail.insertApplyLeave", obj);
			
				if (obj != null) {
					List<LinkedHashMap> aList = (List<LinkedHashMap>) obj.get("affirmList");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
						tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							tempMap.put("APPLY_TYPE_NO", getLeaveApplyCode(obj));
							tempMap.put("APPLY_NO_SEQ", essApplySeq);
							this.insert("ar.detail.insertApplyReviewer",tempMap);
						}
					}
				}
			}
		}
	}
	
	public String getLeaveApplyCode(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ar.detail.getLeaveApplyCode", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}
	
  public void updatSubmitLeaveApplyInBatchForDeletePrepare(List list) throws Exception {
		
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.update("ar.detail.updatSubmitLeaveApplyInBatchForDeletePrepare", obj);
			}
		}
	}
  /**
	 * 批量提交的休假申请
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void insertSubmitLeaveApplyInBatch(List list) throws Exception {
		
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				String APPLY_NO = getARDetailApplyNo(obj);
				int APPLY_NO_SEQ = getARDetailApplySeq();
				obj.put("APPLY_NO_SEQ",APPLY_NO_SEQ);
				obj.put("APPLY_NO",APPLY_NO);
				this.insert("ar.detail.insertSubmitLeaveApplyInBatch", obj);
			}
		}
	}
	private String  getARDetailApplyNo(Object obj) throws Exception {
		try {
			return  (String) queryForObject(("ar.detail.getARDetailApplyNo"),
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
					.queryForObject("ar.detail.getARDetailApplySeq")),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * SST(get information apply sequences)
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
	 * 考勤管理，不是初始状态下的添加。
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updatSubmitArDetailBatchForMangemant(List list) throws Exception {
		
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				this.update("ar.detail.updatSubmitArDetailBatchForMangemant", obj);
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
	public void updateLeaveApplyInArDetailBatchForManagent(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				String applyNoString= getArDetailTstoApplyOfApplyNo(obj);
				if (!"".equals(applyNoString)&&applyNoString != null) {
					obj.put("APPLY_TYPE_CODE",getLeaveApplyCode(obj));
					obj.put("APPLY_TIME", obj.get("AR_DATE_STR"));
					obj.put("LEAVE_FROM_TIME",obj.get("LEAVE_FROM_TIME"));
					obj.put("LEAVE_TO_TIME",obj.get("LEAVE_TO_TIME") );
					obj.put("APPLY_LENGTH", obj.get("APPLY_LENGTH"));
					obj.put("AFFIRM_FLAG", obj.get("AFFIRM_FLAG"));
					obj.put("LEAVE_REASON", obj.get("reason"));
					obj.put("UPDATED_BY", obj.get("CREATED_BY"));
					obj.put("UPDATE_IP", obj.get("CREATED_IP"));
					obj.put("ACTIVITY", obj.get("ACTIVITY"));
					obj.put("APPLY_NO",applyNoString );
					obj.put("CURRENT_AFFIRM_ID",obj.get("CURRENT_AFFIRM_ID"));
					this.update("ar.detail.updateLeaveApplyInArDetailBatchForManagent", obj);
					obj.put("APPLY_NO", getArDetailTstoApplyOfApplyNo(obj));
				}
				
			}
		}
	}
	/**
	 * 加班管理删除
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int delLeaveApplyInBatch(List list, LinkedHashMap personMap)
			throws Exception {
		try {
			for(int i=0;i<list.size();i++){
				LinkedHashMap obj = (LinkedHashMap) list.get(i);
				String ITEM_NO =obj.get("ITEM_NO")!= null ? obj.get("ITEM_NO").toString():"";
				 if ("141440".equals(ITEM_NO)||"141441".equals(ITEM_NO)||"141442".equals(ITEM_NO)||"141443".equals(ITEM_NO)||"14013783".equals(ITEM_NO)) {
					 this.update("ar.detail.delTempLeaveApply", obj);
					 obj.put("PK_NO", getLeaveApplyPkNoNomal(obj));
					 this.update("ar.detail.updateArDetailForNullFlag", obj);
				 }else {
					 this.update("ar.detail.delTempLeaveApply", obj);
					 obj.put("PK_NO", getLeaveApplyPkNoNomal(obj));
					 this.update("ar.detail.updateArDetailForNullFlag", obj);
					 obj.put("APPLY_NO", getArDetailTstoApplyOfApplyNo(obj));
					 this.update("ar.detail.updateApplyLeaveTbForNullFlag", obj);
					 this.delete("ar.detail.deleteEssAffim", obj);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	//获取正常出勤原先数据
	public String getLeaveApplyPkNoNomal(Object obj) throws Exception {
		String object2 = (String) this.queryForObject("ar.detail.getLeaveApplyPkNoNomal", obj);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}
	
	/**
	 * (考勤管理)勤查询页面(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getLeaveManagentForSearchInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ar.detail.getLeaveManagentForSearchInfoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 考勤管理里--添加数据
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addtLeaveApplyInBatch(LinkedHashMap obj) throws Exception {
		
		//获取今天的锁定标识
		//List  lockList = this.getConfrimFlag(obj);
		//String CONFIRM_FLAG =  lockList.get(0).toString();
		int essApplySeq; 
		String APPLY_NO = this.getARDetailApplyNo(obj);
		if ("TSTO".equals(obj.get("CPNY_ID"))) {
			essApplySeq = this.getARDetailApplySeq();
		}else {
			essApplySeq = this.getARDetailApplySeqSST();
		}
		//int essApplySeq = getARDetailApplySeq();
		obj.put("APPLY_NO_SEQ", essApplySeq);
		obj.put("CONFIRM_FLAG", 0);
		this.insert("ar.detail.addtLeaveApplyInBatch", obj);
		return 1;
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
	 * 综合工时加班查询
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewSearchOtInfo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.detail.viewSearchOtInfo",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public void deleteOtAdjustForOnlyOne(LinkedHashMap deleteMap) {
		try {
			this.delete("ar.detail.deleteOtAdjustForOnlyOne",deleteMap );
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List viewAdjustRecords(LinkedHashMap paramMap) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.detail.viewAdjustRecords",paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 取得考勤员或部门长权限下的考勤异常人员(get ArDetail List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewAbnormalDetailInfo(Object object) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();

		try {
			returnList = this.queryForList("ar.detail.viewAbnormalDetailInfo",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
}
