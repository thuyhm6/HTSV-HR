package com.ait.ess.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;

import bsh.This;

import com.ait.web.util.AuthorityUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ar.service.impl.ArDetailSerImp;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.dao.PersonInfoDao;
import com.ait.ess.dao.ViewApplyDao;
import com.ait.ess.service.InfoApplySer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: InfoApplySerImpl.java
 * @Description:
 * @Create date: June 14, 2014 10:09:38 AM
 * @Create by: lufeng(lufeng@ait.net.cn)
 * @Update Date: June 14, 2014 10:09:38 AM
 * @Update by: lufeng(lufeng@ait.net.cn)
 * @version 5.1
 */
@Service
public class InfoApplySerImpl implements InfoApplySer {

	Logger logger = Logger.getLogger(InfoApplySerImpl.class);

	@Autowired
	private InfoApplyDao infoApplyDao;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	@Autowired
	PaTempSalesDAO paTempSalesDAO;

	@SuppressWarnings("unused")
	@Autowired
	private ViewApplyDao viewApplyDao;

	private GregorianCalendar startTime = new GregorianCalendar();

	private GregorianCalendar endTime = new GregorianCalendar();

	@Autowired
	private PersonInfoDao personInfoDao;
	@Autowired
	private AuthorityUtil authorityUtil;
	@Autowired
	private ArDetailSerImp arDetailSerImp;
	
	/**
	 * 员工信息查询(Employee Information Management)
	 * @param request
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public Object getPersonalInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return infoApplyDao.getPersonalInfoByPid(paramMap);
	}
	
	/**
	 * 根据person_id查询该员工上上月最后一天日期(get last day of last last month)
	 * @param request
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public String getLLastMonthLastDay(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return infoApplyDao.getLLastMonthLastDay(paramMap);
	}
	
	/**
	 * 加班申请考勤区间列表(search ot apply ar_month info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtApplyArMonthList(HttpServletRequest request) throws Exception {
		@SuppressWarnings("unused")
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		returnList = infoApplyDao.getOtApplyArMonthList(paramMap);
		
		return returnList;
	}
	
	/**
	 * 加班申请决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("PERSON_ID",admin.getPersonId()); //这里只查个人申请的，不查批量的
			returnList = infoApplyDao.getOtAffirmInfoList(paramMap);
		return returnList;
	}
	
	/**
	 * Myhome 个人加班申请明细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonalOtInfoDetailList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");

		String firstFlag = request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
				//获取当前月第一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				paramMap.put("FROM_DATE",first);
				//获取当前月最后一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				paramMap.put("TO_DATE",last);
			}
		}
		paramMap.put("PERSON_ID",admin.getPersonId()); //这里只查个人申请的，不查批量的
			returnList = infoApplyDao.getPersonalOtInfoDetailList(paramMap);
		return returnList;
	}
	
	/**
	 * 加班--个人搜索(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonOtApplyInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		String firstFlag = request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
				//获取当前月第一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				paramMap.put("FROM_DATE",first);
				//获取当前月最后一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				paramMap.put("TO_DATE",last);
			}
		}
		paramMap.put("PERSON_ID",admin.getPersonId()); //这里只查个人申请的，不查批量的
		returnList = infoApplyDao.getPersonOtApplyInfoList(paramMap);
		return returnList;
	}
	
	/**
	 * 加班申请决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmInfoListBatch(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("APPLY_TYPE", "BATCH");
		paramMap.put("PERSON_ID001", admin.getPersonId());
		Date d=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		paramMap.put("ot_date", df.format(new Date(d.getTime()-60*60*24*1000)));
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		} 
		/*if (UiUtil.getPageNum(request) > 0) {
			returnList = infoApplyDao.getOtAffirmInfoListBatch(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = infoApplyDao.getOtAffirmInfoListBatch(paramMap);
		}*/
		returnList = infoApplyDao.getOtAffirmInfoListBatch(paramMap);
		return returnList;
	}
	/**
	 * 批量调休(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAdjustLeaveTSTOBatchList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("APPLY_TYPE", "BATCH");
		paramMap.put("PERSON_ID001", admin.getPersonId());
		Date d=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		paramMap.put("ot_date", df.format(new Date(d.getTime()-60*60*24*1000)));
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		} 
		returnList = infoApplyDao.viewAdjustLeaveTSTOBatchList(paramMap);
		return returnList;
	}
	/**
	 * 加班原因(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmInfoListWhy(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		if (UiUtil.getPageNum(request) > 0) {
			returnList = infoApplyDao.getOtAffirmInfoListWhy(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = infoApplyDao.getOtAffirmInfoListWhy(paramMap);
		}
		return returnList;
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
		if(paramMap.get("PERSON_ID")==null || "".equals(paramMap.get("PERSON_ID"))){
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		return paramMap;
	}
	
	/**
	 * 加班申请决裁信息总数(search ot info list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getOtAffirmInfoListCnt(HttpServletRequest request) throws Exception {
		int ListCnt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,"seach_");
		
		paramMap.put("PERSON_ID",admin.getPersonId());
		
		ListCnt =  this.infoApplyDao.getOtAffirmInfoListCnt(paramMap);
		
		return ListCnt;
	}
	

	/**
	 * 加班申请决裁信息总数(search ot info list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getOtAffirmInfoListCntBatch(HttpServletRequest request) throws Exception {
		int ListCnt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("APPLY_TYPE", "BATCH");
		paramMap.put("PERSON_ID", admin.getPersonId());
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		} 
		ListCnt =  this.infoApplyDao.getOtAffirmInfoListCntBatch(paramMap);
		
		return ListCnt;
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
	public List getAffirmorList(HttpServletRequest request) throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		if(paramMap==null || paramMap.get("APPLY_TYPE_NO")==null || "".equals(paramMap.get("APPLY_TYPE_NO").toString())){
			paramMap.put("APPLY_TYPE_NO", "31");
		}
		return this.getAffirmorListByMap(paramMap);
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
	public List getAnnualAffirmorList(HttpServletRequest request) throws Exception {
		//封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		return this.getAffirmorListByString("216691", StringUtil.checkNull(paramMap.get("PERSON_ID")), null, null, StringUtil.checkNull(paramMap.get("LANGUAGE")));
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
		/*LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
		paramMap.put("CPNY_ID", personMap.get("CPNY_ID"));
		paramMap.put("DEPT_NO", personMap.get("DEPT_NO"));
		paramMap.put("DUTY_NO", personMap.get("DUTY_NO"));
		// 先取特殊设置人员的决裁者(get approver by special-person's-approver setup first)
		List<LinkedHashMap> spePersonList = this.infoApplyDao.getAffirmorListByPersonID(paramMap);
		if (spePersonList.size() == 0) {
			// 再取特殊设置部门的决裁者(get approver by special-department's-approver setup second)
			List<LinkedHashMap> speDeptList = this.infoApplyDao.getAffirmorListByDeptNo(paramMap);
			if (speDeptList.size() == 0) {
				// 最后按流程取决裁者 (get approver by approve-flow last)
				return this.infoApplyDao.getAffirmorListByNormal(paramMap);
			} else {
				return speDeptList;
			}
		} else {
			return spePersonList;
		}*/
		
		//通过参数来查询决裁者
		String applyTypeNo =   paramMap.get("APPLY_TYPE_NO")!=null?paramMap.get("APPLY_TYPE_NO").toString():"";
		String personId = paramMap.get("PERSON_ID")!=null?paramMap.get("PERSON_ID").toString():"";
		String applyLength =  paramMap.get("otLength")!=null?paramMap.get("otLength").toString():"";
		String applyTypeCode=  paramMap.get("OT_TYPE_CODE")!=null?  paramMap.get("OT_TYPE_CODE").toString():"";
		String language = paramMap.get("LANGUAGE")!=null?paramMap.get("LANGUAGE").toString():"";
		List affirmerList = this.getAffirmorListByString(applyTypeNo, personId, applyTypeCode, applyLength, language);
		
		return affirmerList;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getOtApplyLengthWq(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String personId = request.getParameter("PERSON_ID");
		String cpnyId = request.getParameter("CPNY_ID");
		String endDayOffset = request.getParameter("END_DAY_OFFSET");
		String otFromTime = request.getParameter("OT_FROM_TIME");
		String otToTime = request.getParameter("OT_TO_TIME");
		String FROM_DATE = request.getParameter("FROM_DATE");
		String otFromdate =FROM_DATE+" "+otFromTime;
		String otTOdate =FROM_DATE+" "+otToTime;
		map.put("PERSON_ID", personId);
		map.put("CPNY_ID",admin.getCpnyId());
		map.put("END_DAY_OFFSET", endDayOffset);
		map.put("OT_FROM_TIME", otFromdate);
		map.put("OT_TO_TIME", otTOdate);
		
		LinkedHashMap lengthMap = new LinkedHashMap();
		try {
			lengthMap = infoApplyDao.getOtApplyLength(map) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str = "";
		String hour = "";
	    String min = "";
	    hour = lengthMap.get("OT_HOUR").toString()==null?"0":lengthMap.get("OT_HOUR").toString();
	    min = lengthMap.get("OT_MINUTE").toString()==null?"0":lengthMap.get("OT_MINUTE").toString();
	    str = hour+min;
		return str;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String getOtApplyLength(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String personId = request.getParameter("PERSON_ID");
		 
		String endDayOffset = request.getParameter("END_DAY_OFFSET");
		String otFromTime = request.getParameter("OT_FROM_TIME");
		String otToTime = request.getParameter("OT_TO_TIME");
		map.put("PERSON_ID", personId);
		map.put("CPNY_ID",admin.getCpnyId() );
		map.put("END_DAY_OFFSET", endDayOffset);
		map.put("OT_FROM_TIME", otFromTime);
		map.put("OT_TO_TIME", otToTime);
		LinkedHashMap lengthMap = new LinkedHashMap();
		try {
			if(personId!=null&&!"".equals(personId)){
				lengthMap = infoApplyDao.getOtApplyLength(map) ;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str = "";
		if(personId!=null&&!"".equals(personId)){
		str = lengthMap.get("OT_HOUR").toString()+"小时"+lengthMap.get("OT_MINUTE").toString()+"分钟";
		}
		return str;
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public Object getOtApplyLengthP(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	 
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		Map praHashMap =  this.getOtApplyWorkTime(request);
		
		LinkedHashMap lengthMap = new LinkedHashMap();
		map.put("fromTime",map.get("fromTime"));
		map.put("toTime",map.get("toTime"));
		map.put("SHIFT_NO",map.get("SHIFT_NO"));
		map.put("FIRST_TIME",praHashMap.get("FIRST_TIME"));
		map.put("LAST_TIME",praHashMap.get("LAST_TIME"));
		map.put("from_date",map.get("from_date"));
			lengthMap = infoApplyDao.getOtApplyLength(map) ;
		
		return lengthMap;
	}
	@SuppressWarnings("unchecked")
	public Object getOtFIRSTLASTTimeForChange(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		Map praHashMap =  this.getOtApplyWorkTime(request);
		
		
		
		return praHashMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Object getOtApplyLengthP2(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
	
		String APPLY_DATE = (String) map.get("APPLY_OT_DATE");
		LinkedHashMap   shiftMap = new LinkedHashMap();
		LinkedHashMap   worHashMap= new LinkedHashMap();
	
		shiftMap.put("CPNY_ID", admin.getCpnyId().toString());
		shiftMap.put("PERSON_ID", admin.getPersonId());
		shiftMap.put("APPLY_OT_DATE", APPLY_DATE);
		worHashMap.put("CPNY_ID",shiftMap.get("CPNY_ID") );
		worHashMap.put("PERSON_ID",shiftMap.get("PERSON_ID") );
		worHashMap.put("APPLY_OT_DATE",shiftMap.get("APPLY_OT_DATE") );
		String SHIFT_NO = (String) infoApplyDao.getShiftNoForOt(shiftMap);
		worHashMap.put("SHIFT_NO", SHIFT_NO);
		LinkedHashMap praHashMap =  infoApplyDao.getOtApplyShiftTime(worHashMap);
		LinkedHashMap lengthMap = new LinkedHashMap();
		map.put("fromTime",map.get("FROM_TIME"));
		map.put("toTime",map.get("TO_TIME"));
		map.put("SHIFT_NO",SHIFT_NO);
		map.put("FIRST_TIME",praHashMap.get("FIRST_TIME"));
		map.put("LAST_TIME",praHashMap.get("LAST_TIME"));
		map.put("from_date",APPLY_DATE);
		lengthMap = infoApplyDao.getOtApplyLength(map) ;
		
		return lengthMap;
	}
	private Object getShiftNoForOt(LinkedHashMap shiftMap) throws Exception {
		
		return infoApplyDao.getShiftNoForOt(shiftMap);
	}

	@SuppressWarnings("unchecked")
	public Object getOtFromTimeOrToTime(LinkedHashMap map,String TYPE) {
        
		map.put("fromTime",map.get("fromTime"));
		map.put("toTime",map.get("toTime"));
		map.put("SHIFT_NO",map.get("SHIFT_NO"));
		map.put("FIRST_TIME",map.get("FIRST_TIME"));
		map.put("LAST_TIME",map.get("LAST_TIME"));
		map.put("from_date",map.get("APPLY_DATE"));
		map.put("TYPE", TYPE);
		String otDateString = (String) infoApplyDao.getOtApplyDate(map) ;
		
		//String str = "";
		//str = lengthMap.get("HOUR")==null?"0":lengthMap.get("HOUR").toString();
		
		return otDateString;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getOtApplyWorkTime(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("CPNY_ID",admin.getCpnyId() );
		map.put("from_date", map.get("from_date"));
		map.put("SHIFT_NO", map.get("SHIFT_NO"));
		LinkedHashMap timeMap = new LinkedHashMap();
		
		timeMap = infoApplyDao.getOtApplyWorkTime(map) ;
		
		//String str = "";
		//str = lengthMap.get("HOUR")==null?"0":lengthMap.get("HOUR").toString();
		
		return timeMap;
	}
	
	/**
	 * 取本考勤月申请总时长（包括本次申请）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public double getOtApplyLengthPZong(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		 
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		String personId = request.getParameter("PERSON_ID");
		 
		String endDayOffset = request.getParameter("END_DAY_OFFSET");
		String otFromTime = request.getParameter("OT_FROM_TIME");
		String otToTime = request.getParameter("OT_TO_TIME");
		map.put("PERSON_ID", personId);
		map.put("CPNY_ID",admin.getCpnyId() );
		map.put("END_DAY_OFFSET", endDayOffset);
		map.put("OT_FROM_TIME", otFromTime);
		map.put("OT_TO_TIME", otToTime);
		LinkedHashMap lengthMap1 = new LinkedHashMap();
	 
	    lengthMap1 = infoApplyDao.getOtApplyLength(map) ;
		 
		Double str = 0.0;
		String str1 = "";
		String str2 = "";
		str1 = lengthMap1.get("HOUR")==null?"0":lengthMap1.get("HOUR").toString();
		LinkedHashMap lengthMap = new LinkedHashMap();
	    lengthMap = infoApplyDao.getOtApplyLengthZong(map) ;
		str2 = lengthMap.get("HOUR")==null?"0":lengthMap.get("HOUR").toString();
		str = (Double.parseDouble(str1)+Double.parseDouble(str2));
		return str;
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public String getOtTypeCodeRemark(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String personId = request.getParameter("PERSON_ID");
		String cpnyId = request.getParameter("CPNY_ID");
		String applyTypeCode = request.getParameter("APPLY_TYPE_CODE");
		String otPlaceType = request.getParameter("OT_PLACE_TYPE");
		String adjustYn = request.getParameter("ADJUST_YN");
		String teshuYn = request.getParameter("TESHU_YN");
		map.put("CPNY_ID", admin.getCpnyId());
		map.put("APPLY_TYPE_CODE", applyTypeCode);
		map.put("OT_PLACE_TYPE", otPlaceType);
		map.put("ADJUST_YN", adjustYn);
		map.put("TESHU_YN", teshuYn);
		LinkedHashMap otMap = new LinkedHashMap();
		 
			otMap = infoApplyDao.getOtTypeCodeRemark(map) ;
	 
		String str = "";
		str = otMap.get("DETAIL_CONTENT")!=null?otMap.get("DETAIL_CONTENT").toString():"";
		
		return str;
	}

	/**
		 * 判断是否为追溯休假 overtime apply)
		 * 
		 * @param paramMap
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public boolean isZhuisuOt(HttpServletRequest request) throws Exception {

			Map paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("FLAG", "S");// 获取考勤月开始日期
			paramMap.put("PERSON_ID", paramMap.get("PERSON_ID"));// 获取考勤月开始日期
			String arDateStr = this.infoApplyLeaveDao
					.getCurrentArDateOt(paramMap);
			Date date = new Date();
			SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");


			String leaveApplyFrom = paramMap.get("APPLY_OT_DATE") != null ? paramMap
						.get("APPLY_OT_DATE").toString()
						: sb.format(date);
			GregorianCalendar applyFrom = DateUtil
						.ParseGregorianCalendar(leaveApplyFrom);
			GregorianCalendar arDate = DateUtil
						.ParseGregorianCalendar(arDateStr);
			GregorianCalendar arDate2 = DateUtil.ParseGregorianCalendar(arDateStr);
			GregorianCalendar sysDate = DateUtil.ParseGregorianCalendar(sb.format(date));
			 
			arDate.add(2, -1);
			GregorianCalendar dateCurrent = DateUtil.ParseGregorianCalendar(arDateStr);
			dateCurrent.add(2, 1);
			 
				
				paramMap.put("R_DATE",leaveApplyFrom);
				paramMap.put("interCpnyID",paramMap.get("CPNY_ID"));
				String lastMonthFlag = infoApplyDao.arValidLastMonth(paramMap);
				if("OK".equals(lastMonthFlag)){
					 
					return true;
				}else{
					return false;
				}
			
			
			 
		}
		
		 
	/**
	 * 添加加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addOvertimeApply(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		String person_id = paramMap.get("PERSON_ID") != null ? paramMap.get("PERSON_ID").toString() : admin.getPersonId();
		String applyType= paramMap.get("APPLY_TYPE") != null ? paramMap.get("APPLY_TYPE").toString() : "0";//区分加班休假
		String affirmFlag =  "14014307";
		String otTimeType = paramMap.get("OT_TIME_TYPE") != null ? paramMap.get("OT_TIME_TYPE").toString() : "";
		String applyTypeNo = paramMap.get("APPLY_TYPE_NO") != null ? paramMap.get("APPLY_TYPE_NO").toString() : "";
		String applyTypeCode = paramMap.get("APPLY_TYPE_CODE") != null ? paramMap.get("APPLY_TYPE_CODE").toString() : "";//加班类型
		String dateType = paramMap.get("dateType") != null ? paramMap.get("dateType").toString() : "";//日期类型
		
		
		String otApplyDate = paramMap.get("FROM_DATE") != null ? paramMap.get("FROM_DATE").toString() : "";
		if(otApplyDate==null || "".equals(otApplyDate)){
			otApplyDate = paramMap.get("FROM_DATE") != null ? paramMap.get("FROM_DATE").toString() : "";
		}
		LinkedHashMap shiftTimeMap = new LinkedHashMap();
		shiftTimeMap.put("PERSOIN_ID", person_id);
		shiftTimeMap.put("APPLY_OT_DATE", otApplyDate);
		String shiftNo =  paramMap.get("SHIFT_NO") != null ? paramMap.get("SHIFT_NO").toString(): "";//获取班次
		shiftTimeMap.put("SHIFT_NO", shiftNo);
		//获取班次的开始和结束日期
		LinkedHashMap praHashMap =  infoApplyDao.getOtApplyShiftTime(shiftTimeMap);
		String fromDate =praHashMap.get("FIRST_TIME").toString().substring(0, 10);  
		String toDate =praHashMap.get("LAST_TIME").toString().substring(0, 10);  
		//选择的开始结束时间
		String otFromTime = paramMap.get("OT_FROM_TIME") != null ?
				paramMap.get("OT_FROM_TIME").toString().substring(0, 2)+":"+ paramMap.get("OT_FROM_TIME").toString().substring(2, 4) : "";
		String otToTime = paramMap.get("OT_TO_TIME") != null ? 
				paramMap.get("OT_TO_TIME").toString().substring(0, 2)+":"+ paramMap.get("OT_TO_TIME").toString().substring(2, 4): "";
		String otLength = "";
		String hour = "";
		String min = "";
		otLength = paramMap.get("Lotlengthone") != null ? paramMap.get("Lotlengthone").toString():"0";
		hour = paramMap.get("Lotlengthonehour") != null ? paramMap.get("Lotlengthonehour").toString():"0";
		min = paramMap.get("Lotlengthonemin") != null ? paramMap.get("Lotlengthonemin").toString():"0";
		
		String applyRemark = paramMap.get("APPLY_REMARK") != null ? paramMap.get("APPLY_REMARK").toString() : "";
		
		
		LinkedHashMap dateMap = new LinkedHashMap();
		dateMap = this.getLinkedMapByRequest(request, dateMap);
		dateMap.put("PERSON_ID", person_id);
		dateMap.put("APPLY_TYPE", applyType);
		dateMap.put("APPLY_OT_DATE", otApplyDate);
		dateMap.put("APPLY_TYPE_NO", applyTypeNo);
		dateMap.put("dateType", dateType);
		
		dateMap.put("CPNY_ID", admin.getCpnyId());
		
		
		dateMap.put("FROM_TIME", fromDate + " " + otFromTime + ":"+ "00");
		dateMap.put("TO_TIME", toDate + " " + otToTime + ":"+ "00");
		
		String adjustYn = "0";
		
	    adjustYn = paramMap.get("ADJUST_YN") != null ? paramMap.get("ADJUST_YN").toString() : "0";
		dateMap.put("ADJUST_YN", adjustYn);
		dateMap.put("OT_TYPE_CODE", applyTypeCode);
		//1.根据加班类型（32：平日加班；33：周末加班；34：法定假加班；）
		String otTypeCode =applyTypeCode;	
		if ("33".equals(otTypeCode) && "1".equals(adjustYn)) {
			dateMap.put("ITEM_NO", "141452");
			dateMap.put("OT_TYPE_CODE", "141471");
		}else if("32".equals(otTypeCode) && "0".equals(adjustYn) ){
			dateMap.put("ITEM_NO", "141444");
			dateMap.put("OT_TYPE_CODE", otTypeCode);
			
		}else if("33".equals(otTypeCode) && "0".equals(adjustYn)){
			dateMap.put("ITEM_NO", "141445");
			dateMap.put("OT_TYPE_CODE", otTypeCode);
		}else {
			dateMap.put("ITEM_NO", "141446");
			dateMap.put("OT_TYPE_CODE", otTypeCode);
		}
		
		dateMap.put("AFFIRM_FLAG", affirmFlag);
		dateMap.put("OT_TIME_TYPE", otTimeType);
		dateMap.put("SHIFT_NO", shiftNo);
		dateMap.put("otLength", otLength);
		dateMap.put("hour", hour);
		dateMap.put("min", min);
		dateMap.put("APPLY_REMARK", applyRemark);
		//页面添加的决裁者列表
		paramMap.put("OT_TYPE_CODE", otTypeCode);
		dateMap.put("otLength", otLength);
		dateMap.put("CREATED_BY", admin.getPersonId());
		dateMap.put("CREATED_IP", admin.getAdminIP());
		dateMap.put("UPDATED_IP", admin.getAdminIP());
		dateMap.put("UPDATED_BY", admin.getPersonId());
		
		
		List affirmList = new ArrayList();
		String[] affirmIdStart = request.getParameterValues("AFFIRMOR_ID");
		if(affirmIdStart == null || affirmIdStart.length == 0){
			// 未给该员工设置决裁者时
			throw new CommonException("请先设置决裁者");// alert.ess.approval.no_approver
		}
		List<String> affirmId=new ArrayList();
	     for (String strings : affirmIdStart) {
	    	 if(!strings.equals("")){
	    		 affirmId.add(strings);
	    	 }
	    	 
	     }  
		//添加决裁者 
		for(int i=0; i<affirmId.size(); i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			affirmMap.put("AFFIRMOR_ID", affirmId.get(i));
			affirmMap.put("AFFIRM_LEVEL", i+1);
			affirmList.add(affirmMap);
		}
		dateMap.put("affirmList", affirmList);
		//插入数据库之前的数据的整理、验证，主要是决裁者与时间的验证，然后封装起来
		LinkedHashMap otMap = this.preAddOvertimeApply(dateMap, admin.getLanguage());
		batchOtApplyList.add(otMap);
		
		this.infoApplyDao.addOvertimeApplyInBatch(batchOtApplyList);
		
		if(affirmFlag.equals("-1")){
			return 12;
		}
		return 1;
	}
	
	/**
	 * 修改加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateOvertimeApply(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List otUpdateList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		String person_id = paramMap.get("PERSON_ID") != null ? paramMap.get("PERSON_ID").toString() : "";
		
		String applyNo = paramMap.get("APPLY_NO") != null ? paramMap.get("APPLY_NO").toString() : "";
		String applyType= paramMap.get("APPLY_TYPE") != null ? paramMap.get("APPLY_TYPE").toString() : "PERSON";
		String affirmFlag = paramMap.get("AFFIRM_FLAG") != null ? paramMap.get("AFFIRM_FLAG").toString() : "0";
		String otTimeType = paramMap.get("OT_TIME_TYPE") != null ? paramMap.get("OT_TIME_TYPE").toString() : "P";
		String applyTypeNo = "31";//考勤类型  31是加班
		
		String beginDayOffset = paramMap.get("BEGIN_DAY_OFFSET")!=null?paramMap.get("BEGIN_DAY_OFFSET").toString():"0";
		String endDayOffset = paramMap.get("END_DAY_OFFSET")!=null?paramMap.get("END_DAY_OFFSET").toString():"0";
		
		String fromDate = paramMap.get("FROM_DATE") != null ? paramMap.get("FROM_DATE").toString() : "";
		String toDate = paramMap.get("TO_DATE") != null ? paramMap.get("TO_DATE").toString() : "";
		String FILE_URL =  StringUtil.checkNull(paramMap.get("fileUrl"));
		String FILE_NAME = StringUtil.checkNull(paramMap.get("fileName"));
		
		if("P".equals(otTimeType)){
			//没有跨天，开始日期，结束日期是同一天
			if("0".equals(endDayOffset)){
				toDate = fromDate;
			//跨天，结束日期比结束日期晚一天
			}else{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
				Calendar c = Calendar.getInstance();
				Date date=null;
				date = format.parse(fromDate);
				c.setTime(date); 
				int day=c.get(Calendar.DATE); 
				c.set(Calendar.DATE,day+1); 
				
				toDate=format.format(c.getTime());
			}
		}
		String otApplyDate = paramMap.get("APPLY_OT_DATE") != null ? paramMap.get("APPLY_OT_DATE").toString() : "";
		if(otApplyDate==null || "".equals(otApplyDate)){
			otApplyDate = paramMap.get("FROM_DATE") != null ? paramMap.get("FROM_DATE").toString() : "";
		}
		String otFromTime = "";
		String otToTime = "";
		if("LGEQH".equals(admin.getCpnyId())){
			otFromTime = (paramMap.get("OT_FROM_TIME_H") != null ? paramMap.get("OT_FROM_TIME_H").toString(): "")+":"+
					(paramMap.get("OT_FROM_TIME_M") != null ? paramMap.get("OT_FROM_TIME_M").toString(): "");
			otToTime = (paramMap.get("OT_TO_TIME_H") != null ? paramMap.get("OT_TO_TIME_H").toString(): "")+":"+
					(paramMap.get("OT_TO_TIME_M") != null ? paramMap.get("OT_TO_TIME_M").toString(): "");
		}else{
			otFromTime = paramMap.get("OT_FROM_TIME") != null ? paramMap.get("OT_FROM_TIME").toString(): "";
			otToTime = paramMap.get("OT_TO_TIME") != null ? paramMap.get("OT_TO_TIME").toString(): "";
		}
		String otDeductTime = paramMap.get("OT_DEDUCT_TIME") != null ? paramMap.get("OT_DEDUCT_TIME").toString() : "0";
		
		String otApplyHour = "";
		
		
		if("TSTO".equals(admin.getCpnyId())){
			if(!"".equals(otApplyHour)&&paramMap.get("OT_APPLY_HOUR") != null){
				 otApplyHour =  paramMap.get("OT_APPLY_HOUR").toString();
			}else{
				otApplyHour = paramMap.get("OT_APPLY_HOUR_2")!=null?paramMap.get("OT_APPLY_HOUR_2").toString():"0";
			}
			 

		}else{
		  otApplyHour = paramMap.get("OT_APPLY_HOUR") != null ? paramMap.get("OT_APPLY_HOUR").toString(): (paramMap.get("Lotlengthtwo")!=null?paramMap.get("Lotlengthtwo").toString():"0");

		}
		
		
		String otLength = otApplyHour;
		
		if(otLength==null || "".equals(otLength)||"0".equals(otLength)){
			otLength=otApplyHour;
			 
			if(otLength==null || "".equals(otLength)){
				 otLength =  this.getOtApplyLengthWq(request);
			}
		 
		}
		
		
		String otApplyMinute = paramMap.get("OT_APPLY_MINUTE") != null ? paramMap.get("OT_APPLY_MINUTE").toString() : "0";
		
		String applyTypeCode = paramMap.get("APPLY_TYPE_CODE") != null ? paramMap.get("APPLY_TYPE_CODE").toString() : "";
		if(applyTypeCode==null || "".equals(applyTypeCode)){
			applyTypeCode = paramMap.get("APPLY_TYPE_CODE_NEW") != null ? paramMap.get("APPLY_TYPE_CODE_NEW").toString() : "";
		}
		if(applyTypeCode==null || "".equals(applyTypeCode)){
			applyTypeCode = paramMap.get("APPLY_TYPE_CODE_P") != null ? paramMap.get("APPLY_TYPE_CODE_P").toString() : "";
			 
		}
		String otPlaceType = paramMap.get("OT_PLACE_TYPE") != null ? paramMap.get("OT_PLACE_TYPE").toString() : "INSIDE";
		String applyRemark = paramMap.get("APPLY_REMARK") != null ? paramMap.get("APPLY_REMARK").toString() : "";
		//连续申请可能用到
		//List dateList = this.infoApplyDao.getOvertimeApplyAllDateList(paramMap);
		LinkedHashMap dateMap = new LinkedHashMap();
		dateMap = this.getLinkedMapByRequest(request, dateMap);
		dateMap.put("PERSON_ID", person_id);
		dateMap.put("APPLY_NO", applyNo);
		dateMap.put("APPLY_TYPE", applyType);
		
		dateMap.put("APPLY_OT_DATE", otApplyDate);
		dateMap.put("APPLY_TYPE_NO", applyTypeNo);
		//加班批次号：CH-20140501-O-001
		//dateMap.put("CPNY_ID", admin.getCpnyId());
		//String lotNoStr = this.infoApplyDao.getOtLotNo(dateMap);
		//dateMap.put("LOT_NO", lotNoStr);
		dateMap.put("OPE_FLAG", "UPDATE_PERSON");
		dateMap.put("FROM_TIME", fromDate + " " + otFromTime + ":"+ "00");
		dateMap.put("TO_TIME", toDate + " " + otToTime + ":"+ "00");
		dateMap.put("OT_DEDUCT_TIME", otDeductTime);
		dateMap.put("BEGIN_DAY_OFFSET", beginDayOffset);
		dateMap.put("END_DAY_OFFSET", endDayOffset);
		dateMap.put("FILE_NAME", FILE_NAME);
		dateMap.put("FILE_URL", FILE_URL);
		
		String adjustYn = "0";
		//周末加班的时候才获取页面的是否调休参数
		if("33".equals(applyTypeCode)){
			adjustYn = paramMap.get("ADJUST_YN") != null ? paramMap.get("ADJUST_YN").toString() : "0";
		}else{
			adjustYn = "0";
		}
		String teshu_YN=paramMap.get("TESHU_YN") != null ? paramMap.get("TESHU_YN").toString() : "0";
		//获取个人排班类型 查看是否夜班
		String peopleType = paramMap.get("peopleType") != null ? paramMap.get("peopleType").toString() : "";
		 
		//dateMap.put("YEBAN_YN", YEBAN_YN);
		dateMap.put("ADJUST_YN", adjustYn);
		dateMap.put("TESHU_YN", teshu_YN);
		dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
		dateMap.put("OT_PLACE_TYPE", otPlaceType);
		dateMap.put("CPNY_ID", admin.getCpnyId());
		 
		
		//页面获取到的加班类型是基础，不是最终保存到数据库中的加班类型，需要组合得出（目前只允许周末可调休）
		//1.根据加班类型（32：平日加班；33：周末加班；34：法定假加班；）
		//2.是否调休
		//3.社内、社外
		//4.在ess_ot_code_mapping表中获取对应的加班类型
		String otTypeCode ="";
		
		
		dateMap.put("CPNY_ID", admin.getCpnyId());
		if(!admin.getCpnyId().equals("TSTO")){
			String IF_DATE = "";
			LinkedHashMap Map2 = new LinkedHashMap();
			Map2 =this.infoApplyDao.getOtTypeNight(dateMap);//若是夜班加班进行班次转换
			applyTypeCode = Map2.get("OT_TYPE_CODE").toString();
			IF_DATE = Map2.get("IF_DATE").toString(); //班次是夜班时，并且是平日时，APPLY_OT_DATE减-天
			dateMap.put("APPLY_OT_DATE", IF_DATE);
			dateMap.put("OT_TYPE_CODE", applyTypeCode);
			dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
		}else{
			if(applyTypeCode.equals("32")||applyTypeCode.equals("33")||applyTypeCode.equals("34")){
				 otTypeCode = ((LinkedHashMap)this.infoApplyDao.getOtTypeCodeByTurn(dateMap)).get("OT_TYPE_CODE").toString();

			}else{
				otTypeCode=applyTypeCode;
			}
			dateMap.put("OT_TYPE_CODE", otTypeCode);
		}
		 
	 
		 
		
		dateMap.put("AFFIRM_FLAG", affirmFlag);
		dateMap.put("OT_TIME_TYPE", otTimeType);
		dateMap.put("OT_APPLY_HOUR", otApplyHour);
		dateMap.put("OT_APPLY_MINUTE", otApplyMinute);
		
		dateMap.put("APPLY_REMARK", applyRemark);
		paramMap.put("OT_TYPE_CODE", otTypeCode);
		paramMap.put("otLength", otLength);
		paramMap.put("APPLY_TYPE_NO", applyTypeNo);
		

		//页面添加的决裁者列表
		List affirmList = new ArrayList();
	 
		 
		String[] affirmIdStart = request.getParameterValues("AFFIRMOR_ID");
		if(affirmIdStart == null || affirmIdStart.length == 0){
			// 未给该员工设置决裁者时
			throw new CommonException("请先设置决裁者");// alert.ess.approval.no_approver
		}
		List<String> affirmId=new ArrayList();
	     for (String strings : affirmIdStart) {
	    	 if(!strings.equals("")){
	    		 affirmId.add(strings);
	    	 }
	    	 
	     }   
		//添加决裁者
		for(int i=0; i<affirmId.size(); i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			affirmMap.put("AFFIRMOR_ID", affirmId.get(i));
			affirmMap.put("AFFIRM_LEVEL", i+1);
			affirmList.add(affirmMap);
		}
		dateMap.put("affirmList", affirmList);
		dateMap.put("otLength", otLength);
		//插入数据库之前的数据的整理、验证，主要是决裁者与时间的验证，然后封装起来
		LinkedHashMap otMap = this.preAddOvertimeApply(dateMap, admin.getLanguage());
		otMap.put("APPLY_NO", applyNo);
		otMap.put("OPE_FLAG", "UPDATE_PERSON");
		otUpdateList.add(otMap);
		
		this.infoApplyDao.updateOvertimeApplyInBatch(otUpdateList);
		if(affirmFlag.equals("-1")){
			return 12;
		}
		return 1;
	}
	
	/**
	 * 添加加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addBatchOtApply(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		
		String otTimeType = ""; 
		String otType= request.getParameter("APPLY_TYPE")==null?"":request.getParameter("APPLY_TYPE");
		
		String type = request.getParameter("type")!=null?request.getParameter("type"): "";
		String updateApplyno = request.getParameter("update_apply_no")!=null?request.getParameter("update_apply_no"): "";
		otTimeType = request.getParameter("OT_TIME_TYPE");
		String[] paramData = request.getParameterValues("c1");
		@SuppressWarnings("unused")
		String cloumeCount=request.getParameter("cloumeCount")!=null ? request.getParameter("cloumeCount") : "";
		int j=-1;
		for (int k = 0; k <paramData.length; k++) {
			j=Integer.parseInt(paramData[k].split(",")[1]);
			String personId = paramData[k].split(",")[0];
			LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
			paramMap.put("PERSON_ID", personId);
			if("L".equals(otTimeType)){
				if(paramMap.get(personId + "_APPLY_OT_DATE_"+j)==null){
					continue;
				}
			}else{
				if(paramMap.get(personId + "_FROM_DATE_"+j)==null){
					continue;
				}
			}
	

			String applyTypeNo = paramMap.get("APPLY_TYPE_NO")!=null?paramMap.get("APPLY_TYPE_NO").toString(): "31";
			String applyType = paramMap.get("APPLY_TYPE") != null ? paramMap.get("APPLY_TYPE").toString(): "";
			String affirmFlag = paramMap.get("AFFIRM_FLAG") != null ? paramMap.get("AFFIRM_FLAG").toString(): "";;
			
			String fromDate = paramMap.get(personId+"_FROM_DATE_"+j) != null ? paramMap.get(personId + "_FROM_DATE_"+j).toString(): "";
			String toDate = "";
			String endDayOffset = paramMap.get(personId+"_END_DAY_OFFSET_"+j)!=null?paramMap.get(personId+"_END_DAY_OFFSET_"+j).toString():"0";
			if("P".equals(otTimeType)){
				//没有跨天，开始日期，结束日期是同一天
				if("0".equals(endDayOffset)){
					toDate = fromDate;
				//跨天，结束日期比结束日期晚一天
				}else{
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
					Calendar c = Calendar.getInstance();
					Date date=null;
					date = format.parse(fromDate);
					c.setTime(date); 
					int day=c.get(Calendar.DATE); 
					c.set(Calendar.DATE,day+1); 
					
					toDate=format.format(c.getTime());
				}
			}
			String otFromTime = "";
			String otToTime = "";
			if("LGEQH".equals(admin.getCpnyId())){
				otFromTime = (paramMap.get(personId+"_OT_FROM_TIME_H_"+j)!=null?paramMap.get(personId+"_OT_FROM_TIME_H_"+j).toString(): "")
						+":"+(paramMap.get(personId+"_OT_FROM_TIME_M_"+j)!=null?paramMap.get(personId+"_OT_FROM_TIME_M_"+j).toString(): "");
				otToTime = (paramMap.get(personId+"_OT_TO_TIME_H_"+j)!=null?paramMap.get(personId+"_OT_TO_TIME_H_"+j).toString(): "")+":"
						+(paramMap.get(personId+"_OT_TO_TIME_M_"+j)!=null?paramMap.get(personId+"_OT_TO_TIME_M_"+j).toString(): "");
			}else{
				otFromTime = paramMap.get(personId+"_OT_FROM_TIME_"+j)!=null?paramMap.get(personId+"_OT_FROM_TIME_"+j).toString(): "";
				otToTime = paramMap.get(personId+"_OT_TO_TIME_"+j)!=null?paramMap.get(personId+"_OT_TO_TIME_"+j).toString(): "";
			}
			String applyOtDate = paramMap.get(personId+"_APPLY_OT_DATE_"+j)!=null?paramMap.get(personId+"_APPLY_OT_DATE_"+j).toString(): "";
			String otApplyHour = paramMap.get(personId+"_OT_APPLY_HOUR_"+j)!=null?paramMap.get(personId+"_OT_APPLY_HOUR_"+j).toString(): "0";
			String otApplyMinute = paramMap.get(personId+"_OT_APPLY_MINUTE_"+j)!=null?paramMap.get(personId+"_OT_APPLY_MINUTE_"+j).toString(): "0";
			String applyTypeCode = paramMap.get(personId+"_OT_APPLY_TYPE_CODE_"+j)!=null?paramMap.get(personId+"_OT_APPLY_TYPE_CODE_"+j).toString():"";
			String adjustYn = paramMap.get(personId+"_ADJUST_YN_"+j)!=null?paramMap.get(personId+"_ADJUST_YN_"+j).toString(): "0";
			String teshuYn = paramMap.get(personId+"_TESHU_YN_"+j)!=null?paramMap.get(personId+"_TESHU_YN_"+j).toString(): "0";

			String otPlaceType = paramMap.get(personId+"_OT_PLACE_TYPE_"+j)!=null?paramMap.get(personId+"_OT_PLACE_TYPE_"+j).toString(): "INSIDE";
			String applyRemark = paramMap.get(personId+"_APPLY_REMARK_"+j)!=null?paramMap.get(personId + "_APPLY_REMARK_"+j).toString(): "";
			Double otLength=Double.parseDouble(otApplyHour)+Double.parseDouble(otApplyMinute)/60;
			LinkedHashMap dateMap = new LinkedHashMap();
			dateMap = this.getLinkedMapByRequest(request, dateMap);
			dateMap.put("PERSON_ID", personId);
			dateMap.put("OT_TIME_TYPE", otTimeType);
			dateMap.put("APPLY_TYPE", applyType);
			dateMap.put("AFFIRM_FLAG", affirmFlag);
			//时间点方式申请用开始时间的日期
			if("P".equals(otTimeType)){
				dateMap.put("APPLY_OT_DATE", fromDate);
			//时间长度的方式需要获取页面的参数
			}else if("L".equals(otTimeType)){
				dateMap.put("APPLY_OT_DATE", applyOtDate);
			}
			
			dateMap.put("FROM_TIME", fromDate + " " + otFromTime+":"+"00");
			dateMap.put("TO_TIME", toDate + " " + otToTime+":"+"00");
			dateMap.put("OT_DEDUCT_TIME", "0");
			dateMap.put("BEGIN_DAY_OFFSET", "0");
			dateMap.put("END_DAY_OFFSET", endDayOffset);
			dateMap.put("otLength",otLength);
			dateMap.put("OT_APPLY_HOUR", otApplyHour);
			dateMap.put("OT_APPLY_MINUTE", otApplyMinute);
			
			//获取个人排班类型 查看是否夜班
			String peopleType = paramMap.get(personId+"_peopleType_"+j) != null ? paramMap.get(personId+"_peopleType_"+j).toString() : "";
			//如果是夜班，夜班休息，周末夜班 如果不是不插入
			if("217884".equals(peopleType)||"219627".equals(peopleType)||"217885 ".equals(peopleType)){
				dateMap.put("YEBAN_YN", "1");
			}else{
				dateMap.put("YEBAN_YN", "0");
			}
			

			dateMap.put("APPLY_TYPE_NO", applyTypeNo);
			dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
			dateMap.put("ADJUST_YN", adjustYn);
			dateMap.put("TESHU_YN", teshuYn);
			dateMap.put("OT_PLACE_TYPE", otPlaceType);
			//页面获取到的加班类型是基础，不是最终保存到数据库中的加班类型，需要组合得出（目前只允许周末可调休）
			//1.根据加班类型（32：平日加班；33：周末加班；34：法定假加班；）
			//2.是否调休
			//3.社内、社外
			//4.在ess_ot_code_mapping表中获取对应的加班类型
			String otTypeCode = ((LinkedHashMap)this.infoApplyDao.getOtTypeCodeByTurn(dateMap)).get("OT_TYPE_CODE").toString();
			dateMap.put("OT_TYPE_CODE", otTypeCode);
			dateMap.put("APPLY_REMARK", applyRemark);
			
			//批量申请的员工无法进行决裁者的自主添加、修改、所以只能从系统里获取
			List affirmList = this.getAffirmorListByMap(paramMap);
			dateMap.put("affirmList", affirmList);
			dateMap.put("updateApplyno", updateApplyno);
			LinkedHashMap otMap = this.preAddOvertimeApply(dateMap, admin.getLanguage());
			otMap.put("updateApplyno", updateApplyno);
			batchOtApplyList.add(otMap);
		}
		
		
		
		this.infoApplyDao.addOvertimeApplyInBatch(batchOtApplyList,type,updateApplyno);
		if("BATCH".equals(otType)){
			//返回大批次
			String str = "";
			str=this.callInsertBatchOt(request);
			//根据大批次号查出小批次再循环发送lgep小页面
			if("F".equals(str)){
				throw new CommonException(TipMessage.getTipMessage("alert.message.ess.infoApply.notFor_PKG_ESS_OT_EXCEL_IMP", "zh"));
			}
		
		}
		
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public String callInsertBatchOt(HttpServletRequest request){
		
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String affirmFlag = paramMap.get("AFFIRM_FLAG") != null ? paramMap.get("AFFIRM_FLAG").toString(): "0";;
		paramMap.put("AFFIRM_FLAG", affirmFlag);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "pkg_ess_ot_excel_imp.pr_import_ess_ot_data");
		try {
			return this.infoApplyDao.callInsertBatchOt(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		
	}
	
	/**
	 * 添加导入的加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkImportOtApply(List importOtApplyList,LinkedHashMap paramMap,HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		 String affirmFlag = paramMap.get("AFFIRM_FLAG")!=null?paramMap.get("AFFIRM_FLAG").toString():"0";
		int result = 0;
		for (int k = 0; k <importOtApplyList.size(); k++) {
			int checkFlag = 0;
			LinkedHashMap otMap = this.getLinkedMapByRequest(request);
			otMap = (LinkedHashMap)importOtApplyList.get(k);
			String applyTempNo = otMap.get("APPLY_NO")!=null?otMap.get("APPLY_NO").toString():"";
			String personId = otMap.get("PERSON_ID")!=null?otMap.get("PERSON_ID").toString():"";
			String otLength = otMap.get("OT_LENGTH")!=null?otMap.get("OT_LENGTH").toString():"";
			String empId = otMap.get("EMPID")!=null?otMap.get("EMPID").toString():"";
			String applyType = paramMap.get("APPLY_TYPE") != null ? paramMap.get("APPLY_TYPE").toString(): "BATCH";//BATCH、PERSON
			String otTimeType = otMap.get("OT_TIME_TYPE")!=null?otMap.get("OT_TIME_TYPE").toString():"P";
			
			String otFromTime = otMap.get("OT_FROM_TIME")!=null?otMap.get("OT_FROM_TIME").toString():"";
			String otToTime = otMap.get("OT_TO_TIME")!=null?otMap.get("OT_TO_TIME").toString():"";
			
			String applyOtDate = otMap.get("APPLY_OT_DATE")!=null?otMap.get("APPLY_OT_DATE").toString():"";
			String otApplyHour = otMap.get("OT_APPLY_HOUR")!=null?otMap.get("OT_APPLY_HOUR").toString():"0";
			String otApplyMinute = otMap.get("OT_APPLY_MINUTE")!=null?otMap.get("OT_APPLY_MINUTE").toString():"0";
			
			String applyTypeNo = otMap.get("OT_TYPE_NO")!=null?otMap.get("OT_TYPE_NO").toString():"";
			String applyTypeCode = otMap.get("OT_TYPE_CODE")!=null?otMap.get("OT_TYPE_CODE").toString():"";
			
			String adjustYn = otMap.get("ADJUST_YN")!=null?otMap.get("ADJUST_YN").toString():"0";
			String otPlaceType = otMap.get("OT_PLACE_TYPE")!=null?otMap.get("OT_PLACE_TYPE").toString():"INSIDE";
			String applyRemark = otMap.get("APPLY_OT_REMARK")!=null?otMap.get("APPLY_OT_REMARK").toString():"";
			String empTypeCode =  otMap.get("EMP_TYPE_CODE")!=null?otMap.get("EMP_TYPE_CODE").toString():"";
			String groupNo =  otMap.get("JOBTYPE_GROUP_NO")!=null?otMap.get("JOBTYPE_GROUP_NO").toString():"";
			String TESHU_YN = otMap.get("TESHU_YN")!=null?otMap.get("TESHU_YN").toString():"0";
			LinkedHashMap dateMap = new LinkedHashMap();
			dateMap = this.getLinkedMapByRequest(request, dateMap);
			dateMap.put("APPLY_NO", applyTempNo);
			dateMap.put("PERSON_ID", personId);
			dateMap.put("TESHU_YN", TESHU_YN);
			dateMap.put("EMPID", empId);
			dateMap.put("EMP_TYPE_CODE",empTypeCode);
			dateMap.put("JOBTYPE_GROUP_NO",groupNo);
			dateMap.put("OT_TIME_TYPE", otTimeType);
			dateMap.put("APPLY_TYPE", applyType);
			//dateMap.put("AFFIRM_FLAG", affirmFlag);
			dateMap.put("APPLY_OT_DATE", applyOtDate);
			
			dateMap.put("FROM_TIME", otFromTime);
			dateMap.put("TO_TIME", otToTime);
			dateMap.put("OT_DEDUCT_TIME", "0");
			dateMap.put("BEGIN_DAY_OFFSET", "0");
			dateMap.put("END_DAY_OFFSET", "0");

			dateMap.put("OT_APPLY_HOUR", otApplyHour);
			dateMap.put("OT_APPLY_MINUTE", otApplyMinute);
			
			dateMap.put("APPLY_TYPE_NO", applyTypeNo);
			dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
			dateMap.put("ADJUST_YN", adjustYn);
			dateMap.put("OT_PLACE_TYPE", otPlaceType);
			//页面获取到的加班类型是基础，不是最终保存到数据库中的加班类型，需要组合得出（目前只允许周末可调休）
			//1.根据加班类型（32：平日加班；33：周末加班；34：法定假加班；）
			//2.是否调休
			//3.社内、社外
			//4.在ess_ot_code_mapping表中获取对应的加班类型
			String otTypeCode = ((LinkedHashMap)this.infoApplyDao.getOtTypeCodeByTurn(dateMap)).get("OT_TYPE_CODE").toString();
			dateMap.put("OT_TYPE_CODE", otTypeCode);
			dateMap.put("APPLY_REMARK", applyRemark);
			
			dateMap.put("UPLOAD_BY", admin.getPersonId());
			 
			dateMap.put("OT_LENGTH",otLength);
			 
	
			//批量申请的员工无法进行决裁者的自主添加、修改、所以只能从系统里获取
			String language = admin.getLanguage();
			paramMap.put("APPLY_TYPE_NO", "31");//加班申请
			paramMap.put("otLength",otLength);
			paramMap.put("language", language);
			paramMap.put("EMP_TYPE_CODE",empTypeCode);
			paramMap.put("JOBTYPE_GROUP_NO",groupNo);
			paramMap.put("OT_TYPE_CODE",otTypeCode);
			paramMap.put("PERSON_ID", personId);
			List affirmList = new ArrayList();
			//if(affirmFlag!="0"){
				if("TSTO".equals(admin.getCpnyId()) ){
					 affirmList =  this.getAffirmorListByString("31", 
						personId, this.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),otTypeCode), otLength, language);
					
					}else{
					    affirmList = this.getAffirmorListByMap(paramMap);
					}
			//}
			dateMap.put("AFFIRM_FLAG",affirmFlag);
			dateMap.put("affirmList", affirmList);
			checkFlag = this.preImportOvertimeApply(dateMap, admin.getLanguage());
			
			result = result + checkFlag;
		}
		return result;
	}
	
	/**
	 * check要插入的加班导入数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private int preImportOvertimeApply(LinkedHashMap paramMap,String language) throws Exception {
		LinkedHashMap essParamMap = new LinkedHashMap();
		int result = 0;//最后返回的结果
		@SuppressWarnings("unused")
		int opeFlag = -1;//每一步的操作结果
		
		int	 emp_role = 0;//查询是否管理此员工
		String errorContent = "";
		Object paramValueObj = null;
		String ifNeedAffirm = "";
		@SuppressWarnings("unused")
		String ifNeedConfirm = "";
		// 需要决裁
		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
		essParamMap.put("ESS_PARAM_NO", "4159");
		paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		
		ifNeedAffirm = paramValueObj != null ? paramValueObj.toString() : "0";
		essParamMap.put("ESS_PARAM_NO", "4160");
		paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedConfirm = paramValueObj != null ? paramValueObj.toString() : "0";
		//LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
		//从系统配置里获取决裁者
		//List affirmerList = this.getAffirmorListByMap(paramMap);
		//从系统配置、页面添加的决裁者组合以后得出的决裁者信息
		emp_role = this.infoApplyDao.selectOtApplyCheckResult(paramMap);
		if ("".equals(paramMap.get("EMPID"))|| paramMap.get("EMPID")==null) {
			errorContent = "工号不存在";
			paramMap.put("ERROR_CONTENT", errorContent);
			opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
			result = result + 1;
		}
		
		if (result==0&& emp_role >0) {
			errorContent = "[此社号不在你的管辖范围内]";
			paramMap.put("ERROR_CONTENT", errorContent);
			opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
			result = result + 1;
		}
		
		List affirmerList = (ArrayList<LinkedHashMap>)paramMap.get("affirmList");
		//1.加班申请需要决裁，但是未给该员工设置决裁者时-------------------
		if (result==0&&affirmerList.size() == 0 ) {
			errorContent = "[没有决裁者]";
			paramMap.put("ERROR_CONTENT", errorContent);
			opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
			result = result + 1;
		}
		//2.该加班既没有人事确认，也没有决裁------------------------------暂时不用
		/*if ("0".equals(ifNeedAffirm) && "0".equals(ifNeedConfirm)) {
			errorContent = "[加班申请既没有决裁，也没有人事确认，请设置一项]";
			paramMap.put("ERROR_CONTENT", errorContent);
			opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
			result = result + 1;
		}*/
		//2.加班限制标志是否为1，为1：只允许申请节假日加班；0：不限制；---------------------
	 
		
		//获取加班申请的类型：P:时间点方式;L:时间长度方式；
		String otTimeType = paramMap.get("OT_TIME_TYPE") != null ? paramMap.get("OT_TIME_TYPE").toString(): "P";
		//各种验证（日期、时间、类型等）
		//------------------------------------------------------------
		String paramValue = "";
		String applyDateStr = paramMap.get("APPLY_OT_DATE") != null ? paramMap.get("APPLY_OT_DATE").toString() : "";
		//如果加班申请类型是时间点方式P
		int dataType=0;
		if(result==0){
			    dataType = this.infoApplyDao.getDataTypeWQ(paramMap);
		}
		
		//追溯加班验证
		if (this.otApplyForUpdateimp(paramMap)) {
			errorContent =TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
			  + paramMap.get("zhuisuMsg");
			paramMap.put("ERROR_CONTENT", errorContent);
			opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
			result = result + 1;
			 
			}
	   
		if("P".equals(otTimeType)){
			String otFromDate = paramMap.get("FROM_TIME") != null ? paramMap.get("FROM_TIME").toString() : "";
			String otToDate = paramMap.get("TO_TIME") != null ? paramMap.get("TO_TIME").toString() : "";
			//检查申请开始时间和结束时间,如果格式不对提示用户
			//this.formatFromAndToDate(otFromDate, otToDate);
			//3.开始结束时间检查，结束时间必须晚于开始时间------------------
			if (result==0&&otFromDate!=null && !"".equals(otFromDate) && otToDate!=null && !"".equals(otToDate)) {
				GregorianCalendar fromTime = DateUtil.ParseGregorianCalendar(otFromDate);
				GregorianCalendar toTime = DateUtil.ParseGregorianCalendar(otToDate);
				if (fromTime.after(toTime)) {
					errorContent = "[结束时间没有开始时间大]";
					paramMap.put("ERROR_CONTENT", errorContent);
					opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
					result = result + 1;
				}
			}
			paramMap.put("APPLY_DATE", applyDateStr.replaceAll("-", "/"));
			//4.加班时间与班次时间检查(不检测节假日加班申请)----------------
			if (result==0&&"1440".equals(String.valueOf(dataType))) {
				if (this.getOtApplyDateWithShift(paramMap)) {
					errorContent = "[加班时间与班次时间重叠]";
					paramMap.put("ERROR_CONTENT", errorContent);
					opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
					result = result + 1;
				}
			}
		
			paramMap.put("APPLY_DATE_STR", applyDateStr.replaceAll("-", "/"));
			
			
			
			//8.加班时间与之前申请加班时间检查-----------------------------------
			// if (true) { this.essSysparam.isCheckOtApplyOtConflict()
			 if (result==0&&this.otApplyConflictWithExsitOtApply(paramMap)) {
				errorContent = "[加班时间与已有的加班申请时间重复，不能申请]";
				paramMap.put("ERROR_CONTENT", errorContent);
				opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
				result = result + 1;
			} 
			// }
			//9.加班时间与之前申请休假时间检查-------------------------------------
			// if (true) {// this.essSysparam.isCheckOtApplyLeaveConflict()
			/*if (result==0&&this.otApplyConflictWithExsitLeaveApply(paramMap)) {
				errorContent = "[加班时间与已有休假申请时间重复，不能申请]";
				paramMap.put("ERROR_CONTENT", errorContent);
				opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
				result = result + 1;
			}*/
			// }
			essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
			//10.加班申请是否在可以申请日期上限范围内--------------------------------
			essParamMap.put("ESS_PARAM_NO", "4161");
			if(result==0){
				paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
			}
			
			paramValue = paramValueObj != null ? paramValueObj.toString() : "-1";
			if (result==0&&"-1".equals(paramValue)) {
				if (this.otCheckOtApplyDaysBefore(paramMap, 1)) {
					errorContent = "[加班申请不允许申请上限之外的加班]";
					paramMap.put("ERROR_CONTENT", errorContent);
					opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
					result = result + 1;
				}
			}
			//11.加班申请是否在可以申请日期下限范围内-----------------------------------
			essParamMap.put("ESS_PARAM_NO", "4162");
			if(result==0){
				paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
			}
		
			paramValue = paramValueObj != null ? paramValueObj.toString() : "-1";
			if (result==0&&!"-1".equals(paramValue)) {
				if (this.otCheckOtApplyDaysAfter(paramMap, 1)) {
					errorContent = "[加班申请不允许申请下限之外的加班]";
					paramMap.put("ERROR_CONTENT", errorContent);
					opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
					result = result + 1;
				}
			}
			//12.加班申请是否在允许的考勤区间内（目前只允许申请本考勤区间、上个考勤区间，不允许申请上上个考勤区间的加班）---------
		  
		//如果加班方式是时间长度方式L
		}else if("TSTO".equals(paramMap.get("CPNY_ID"))){
			
			//加班限制标志是否为1，为1：只允许申请节假日加班；0：不限制；
			int limitFlag = this.infoApplyDao.getOtApplyLimitFlag(paramMap);
			if (result==0&&!"34".equals(paramMap.get("APPLY_TYPE_CODE")) && "1".equals(String.valueOf(limitFlag))) {
				errorContent = "[营业职或促销员，只允许申请法定假加班,请选择法定假日期!]";
				paramMap.put("ERROR_CONTENT", errorContent);
				opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
				result = result + 1;
			} 
			 
			//13.加班时间与之前申请加班时间检查----------------------------------------
			if (result==0&&!this.otApplyExsitByApplyDate(paramMap)) {
				errorContent = "[加班时间与已有的加班申请时间重复，不能申请]";
				paramMap.put("ERROR_CONTENT", errorContent);
				opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
				result = result + 1;
			}
			
			/*//5.检测导入临时表里是否有重复、重叠的加班申请-----------------
			if (result==0&&this.getOtApplyDateWithImportOther(paramMap)) {
				errorContent = "[导入的加班申请信息有重复或重叠的时间段]";
				paramMap.put("ERROR_CONTENT", errorContent);
				opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
				result = result + 1;
			}*/
		}
		 
		if(result==0){
			
			if( "TSTO".equals(paramMap.get("CPNY_ID"))){
				 dataType = this.infoApplyDao.getDataTypeL(paramMap);
			}else{
				 dataType = this.infoApplyDao.getDataTypeWQ(paramMap);
			}
			 
		}
		
		//14.导入的加班申请时间有重复或重叠，不能申请----------------------------------------
	 
		paramMap.put("DATE_TYPE",dataType);
	 //5.检测导入临时表里是否有重复、重叠的加班申请-----------------
		 if (result==0&&this.getOtApplyDateWithImportOtherWQ(paramMap)) {
		   errorContent = "[导入的加班申请信息有重复或重叠的时间段]";
		 paramMap.put("ERROR_CONTENT", errorContent);
				opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
				result = result + 1;
			}
		 //111检测36H验证
		 if (result==0&&this.getOtApplyDateWithImportOtherAll(paramMap)) {
			    result = result + 1;
			 }
		// 日历类型:1440平时,1441公休,1442节假
		//6.平日加班必须在日期性质为工作日申请-------------------------------
		if (result==0&&"32".equals(paramMap.get("APPLY_TYPE_CODE"))) {
			if (!"1440".equals(String.valueOf(dataType))) {
				errorContent = "[日期不是平日，不能进行平日加班申请]";
				paramMap.put("ERROR_CONTENT", errorContent);
				opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);  
				result = result + 1;
			}
		//7.周末加班必须在日期性质为周末申请--------------------------------
		} else if (result==0&&"33".equals(paramMap.get("APPLY_TYPE_CODE"))) {
			if (!"1441".equals(String.valueOf(dataType))) {
				errorContent = "[日期不是周末，不能进行周末加班申请]";
				paramMap.put("ERROR_CONTENT", errorContent);
				opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);  
				result = result + 1;
			}
		//7.节假日加班必须在日期性质为节假日申请----------------------------
		} else if (result==0&&"34".equals(paramMap.get("APPLY_TYPE_CODE"))) {
			if (!"1442".equals(String.valueOf(dataType))) {
				errorContent = "[日期不是节假日，不能进行法定假加班申请]";
				paramMap.put("ERROR_CONTENT", errorContent);
				opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
				result = result + 1;
			}
		}
		
	
		//社外申请加班 验证是否申请了出差
		if(result==0&&!"211814".equals(paramMap.get("JOBTYPE_GROUP_NO"))
				 		){ //促销员不验证社外是否出差
		if(result==0&&("17378".equals(paramMap.get("OT_TYPE_CODE")) ||
				"141470".equals(paramMap.get("OT_TYPE_CODE"))||
				"141472".equals(paramMap.get("OT_TYPE_CODE"))||
				"17379".equals(paramMap.get("OT_TYPE_CODE"))
				)&&!"LGEND".equals(paramMap.get("CPNY_ID"))
				
		   ){   
			if (this.otApplyCheckOutCh(paramMap)) {
				errorContent = "[社外加班需要先申请出差或外出]";
				paramMap.put("ERROR_CONTENT", errorContent);
				opeFlag = this.infoApplyDao.updateOtApplyCheckResult(paramMap);
				result = result + 1;
			}
			
		} 
		}
		
		 
		//------------------------------------------------------------
		//将最后的check结果返回
		return result;
	}
	
	/**
	 * 添加导入的加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addImportOtApply2(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		List importOtApplyList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		String affirmFlag = paramMap.get("AFFIRM_FLAG")!=null?paramMap.get("AFFIRM_FLAG").toString():"0";
		importOtApplyList = this.infoApplyDao.getOtImportInfoList(paramMap);
		int checkFlag = 0;
		//这里对所有导入加班临时表里的申请信息进行验证，并将验证结果存入申请信息临时表中
		checkFlag = checkImportOtApply(importOtApplyList,paramMap,request);
		//只有当所有的加班验证全部通过之后才能进行加班信息插入
		if(checkFlag==0){
			for (int k = 0; k <importOtApplyList.size(); k++) {
				LinkedHashMap otMap = this.getLinkedMapByRequest(request);
				otMap = (LinkedHashMap)importOtApplyList.get(k);
				String applyTempNo = otMap.get("APPLY_NO")!=null?otMap.get("APPLY_NO").toString():"";
				String personId = otMap.get("PERSON_ID")!=null?otMap.get("PERSON_ID").toString():"";
				String applyType = paramMap.get("APPLY_TYPE") != null ? paramMap.get("APPLY_TYPE").toString(): "BATCH";//BATCH、PERSON
				String otTimeType = otMap.get("OT_TIME_TYPE")!=null?otMap.get("OT_TIME_TYPE").toString():"P";
				
				String otFromTime = otMap.get("OT_FROM_TIME")!=null?otMap.get("OT_FROM_TIME").toString():"";
				String otToTime = otMap.get("OT_TO_TIME")!=null?otMap.get("OT_TO_TIME").toString():"";
				
				String applyOtDate = otMap.get("APPLY_OT_DATE")!=null?otMap.get("APPLY_OT_DATE").toString():"";
				String otApplyHour = otMap.get("OT_APPLY_HOUR")!=null?otMap.get("OT_APPLY_HOUR").toString():"0";
				String otApplyMinute = otMap.get("OT_APPLY_MINUTE")!=null?otMap.get("OT_APPLY_MINUTE").toString():"0";
				
				String applyTypeNo = otMap.get("OT_TYPE_NO")!=null?otMap.get("OT_TYPE_NO").toString():"";
				String applyTypeCode = otMap.get("OT_TYPE_CODE")!=null?otMap.get("OT_TYPE_CODE").toString():"";
				
				String adjustYn = otMap.get("ADJUST_YN")!=null?otMap.get("ADJUST_YN").toString():"0";
				String otPlaceType = otMap.get("OT_PLACE_TYPE")!=null?otMap.get("OT_PLACE_TYPE").toString():"INSIDE";
				String applyRemark = otMap.get("APPLY_OT_REMARK")!=null?otMap.get("APPLY_OT_REMARK").toString():"";
				String teshuYn = otMap.get("TESHU_YN")!=null?otMap.get("TESHU_YN").toString():"0";
				LinkedHashMap dateMap = new LinkedHashMap();
				dateMap = this.getLinkedMapByRequest(request, dateMap);
				dateMap.put("APPLY_NO", applyTempNo);
				dateMap.put("PERSON_ID", personId);
				dateMap.put("OT_TIME_TYPE", otTimeType);
				dateMap.put("APPLY_TYPE", applyType);
				dateMap.put("AFFIRM_FLAG", affirmFlag);
				dateMap.put("APPLY_OT_DATE", applyOtDate);
				
				dateMap.put("FROM_TIME", otFromTime);
				dateMap.put("TO_TIME", otToTime);
				dateMap.put("OT_DEDUCT_TIME", "0");
				dateMap.put("BEGIN_DAY_OFFSET", "0");
				dateMap.put("END_DAY_OFFSET", "0");
	
				dateMap.put("OT_APPLY_HOUR", otApplyHour);
				dateMap.put("OT_APPLY_MINUTE", otApplyMinute);
				
				dateMap.put("APPLY_TYPE_NO", applyTypeNo);
				dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
				dateMap.put("ADJUST_YN", adjustYn);
				dateMap.put("TESHU_YN", teshuYn);
				dateMap.put("OT_PLACE_TYPE", otPlaceType);
				//页面获取到的加班类型是基础，不是最终保存到数据库中的加班类型，需要组合得出（目前只允许周末可调休）
				//1.根据加班类型（32：平日加班；33：周末加班；34：法定假加班；）
				//2.是否调休
				//3.社内、社外
				//4.在ess_ot_code_mapping表中获取对应的加班类型
				
			
				
				dateMap.put("CPNY_ID", admin.getCpnyId());
				if(!admin.getCpnyId().equals("TSTO")){
					String IF_DATE = "";
					LinkedHashMap Map2 = new LinkedHashMap();
					Map2 =this.infoApplyDao.getOtTypeNight(dateMap);//若是夜班加班进行班次转换
					applyTypeCode = Map2.get("OT_TYPE_CODE").toString();
					IF_DATE = Map2.get("IF_DATE").toString(); //班次是夜班时，并且是平日时，APPLY_OT_DATE减-天
					dateMap.put("APPLY_OT_DATE", IF_DATE);
					dateMap.put("OT_TYPE_CODE", applyTypeCode);
					dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
				}else{
					String otTypeCode = ((LinkedHashMap)this.infoApplyDao.getOtTypeCodeByTurn(dateMap)).get("OT_TYPE_CODE").toString();
					dateMap.put("OT_TYPE_CODE", otTypeCode);
				}
				
				
				
				dateMap.put("APPLY_REMARK", applyRemark);
				
				//批量申请的员工无法进行决裁者的自主添加、修改、所以只能从系统里获取
				paramMap.put("APPLY_TYPE_NO", "31");//加班申请
				//查询是否追溯
				String  if_zhuisu = this.infoApplyDao.getIfZhuiSu(dateMap);
				
				paramMap.put("IF_ZS", if_zhuisu);
				dateMap.put("IF_ZS", if_zhuisu);
				 /**start
				LinkedHashMap applyMap = new LinkedHashMap();
				 
				if(otTimeType!=null && "P".equals(otTimeType)){
					String otFromDate = paramMap.get("FROM_TIME") != null ? paramMap.get("FROM_TIME").toString() : "";
					String otToDate = paramMap.get("TO_TIME") != null ? paramMap.get("TO_TIME").toString() : "";
					//检查申请开始时间和结束时间,如果格式不对提示用户
					this.formatFromAndToDate(otFromDate, otToDate);
				}
				
				paramMap.put("ACTIVITY", "0");
				applyMap.put("PARAM_MAP", paramMap);
				
				//**end*/
				//上面验证过 了，不再查询决裁者，批量导入的决裁者，实在最后调用存储获取的
				 //List affirmList = this.getAffirmorListByMap(paramMap);
				 dateMap.put("affirmList", "");
			 	LinkedHashMap applyMap = this.preAddOvertimeImport(dateMap, admin.getLanguage());
				batchOtApplyList.add(applyMap);
			}
			this.infoApplyDao.addOvertimeApplyInBatch(batchOtApplyList);
			
			//返回大批次
			 String str = "";
			 
				 str=this.callInsertBatchOt(request);
			 
			
						//根据大批次号查出小批次再循环发送lgep小页面
				 if("F".equals(str)){
					 return 444;
				 }
					 
					
			
			return 1;
		}else{
			return -1;
		}
	}
	
	/**
	 * 封装要导入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddOvertimeImport(LinkedHashMap paramMap,String language) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		LinkedHashMap essParamMap = new LinkedHashMap();
		List distinctList = new ArrayList();
	//	Object paramValueObj = null;
	//	String ifNeedAffirm = "";
	//	String ifNeedConfirm = "";
		// 需要决裁
		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
		essParamMap.put("ESS_PARAM_NO", "4159");
	//	paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		//ifNeedAffirm = paramValueObj != null ? paramValueObj.toString() : "0";
		essParamMap.put("ESS_PARAM_NO", "4160");
	//	paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		//ifNeedConfirm = paramValueObj != null ? paramValueObj.toString() : "0";
	//	LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
		//从系统配置里获取决裁者
		//List affirmerList = this.getAffirmorListByMap(paramMap);
		//从系统配置、页面添加的决裁者组合以后得出的决裁者信息
		//List affirmerList = (ArrayList<LinkedHashMap>)paramMap.get("affirmList");
		if(1==1)/* (affirmerList.size() == 0 && "1".equals(ifNeedAffirm)) {
			// 未给该员工设置决裁者时
			throw new CommonException(TipMessage.getTipMessage("alert.message.ess.infoApply.notFor", language)
					+ personMap.get("LOCAL_NAME")+ TipMessage.getTipMessage("alert.message.ess.infoApply.pleaseSetAffirmorFirst",language));
		} else*/ {
			/*int count = 1;
			boolean flag = true;
			Map filterMap = new LinkedHashMap();
			for (int i = 0; i < affirmerList.size(); i++) {
				LinkedHashMap affirmerMap = (LinkedHashMap) affirmerList.get(i);
				if (affirmerMap.get("AFFIRMOR_ID") != null) {
					if (flag) {
						if(affirmerMap.get("AFFIRMOR_ID")!=null && "1".equals(affirmerMap.get("AFFIRM_LEVEL").toString())){
							//只将流程第一步的决裁者取出来存到加班申请表
							paramMap.put("CURRENT_AFFIRM_ID", affirmerMap.get("AFFIRMOR_ID"));
							flag = false;
						}
					}
					filterMap.put(affirmerMap.get("AFFIRMOR_ID").toString(),affirmerMap);
				}
			}*/
			// 判断saveAffirmorList里边重复的决裁者，然后重新排序
			/*for (Iterator iterator = filterMap.values().iterator(); iterator.hasNext();) {
				LinkedHashMap temp = (LinkedHashMap) iterator.next();
				temp.put("AFFIRM_LEVEL", count);
				count++;
				distinctList.add(temp);
			}*/
			//start** 以下几行无用，只是设置一下参数
			LinkedHashMap temp = new LinkedHashMap();;
			temp.put("AFFIRM_LEVEL", "");
			distinctList.add(temp); 
			paramMap.put("CURRENT_AFFIRM_ID", ""); 
			 
			//end**
			String otTimeType = paramMap.get("OT_TIME_TYPE").toString();
			if(otTimeType!=null && "P".equals(otTimeType)){
				String otFromDate = paramMap.get("FROM_TIME") != null ? paramMap.get("FROM_TIME").toString() : "";
				String otToDate = paramMap.get("TO_TIME") != null ? paramMap.get("TO_TIME").toString() : "";
				//检查申请开始时间和结束时间,如果格式不对提示用户
				this.formatFromAndToDate(otFromDate, otToDate);
			}
			//各种验证（日期、时间、类型等）---前面已经验证过了，此处不需要再次验证
			 //if (otApplyCheck(paramMap, personMap, language)){
				 
					paramMap.put("ACTIVITY", "0");
					returnMap.put("PARAM_MAP", paramMap);
					returnMap.put("DISTINCT_LIST", distinctList);
				 
				// }
		}
		return returnMap;
	}
	
	/**
	 * 添加导入的加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addImportOtApply(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		List importOtApplyList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		String affirmFlag = paramMap.get("AFFIRM_FLAG")!=null?paramMap.get("AFFIRM_FLAG").toString():"0";
		importOtApplyList = this.infoApplyDao.getOtImportInfoList(paramMap);
		
		for (int k = 0; k <importOtApplyList.size(); k++) {
			LinkedHashMap otMap = this.getLinkedMapByRequest(request);
			otMap = (LinkedHashMap)importOtApplyList.get(k);
			String applyTempNo = otMap.get("APPLY_NO")!=null?otMap.get("APPLY_NO").toString():"";
			String personId = otMap.get("PERSON_ID")!=null?otMap.get("PERSON_ID").toString():"";
			String applyType = paramMap.get("APPLY_TYPE") != null ? paramMap.get("APPLY_TYPE").toString(): "BATCH";//BATCH、PERSON
			String otTimeType = otMap.get("OT_TIME_TYPE")!=null?otMap.get("OT_TIME_TYPE").toString():"P";
			
			String otFromTime = otMap.get("OT_FROM_TIME")!=null?otMap.get("OT_FROM_TIME").toString():"";
			String otToTime = otMap.get("OT_TO_TIME")!=null?otMap.get("OT_TO_TIME").toString():"";
			
			String applyOtDate = otMap.get("APPLY_OT_DATE")!=null?otMap.get("APPLY_OT_DATE").toString():"";
			String otApplyHour = otMap.get("OT_APPLY_HOUR")!=null?otMap.get("OT_APPLY_HOUR").toString():"0";
			String otApplyMinute = otMap.get("OT_APPLY_MINUTE")!=null?otMap.get("OT_APPLY_MINUTE").toString():"0";
			
			String applyTypeNo = otMap.get("OT_TYPE_NO")!=null?otMap.get("OT_TYPE_NO").toString():"";
			String applyTypeCode = otMap.get("OT_TYPE_CODE")!=null?otMap.get("OT_TYPE_CODE").toString():"";
			
			String adjustYn = otMap.get("ADJUST_YN")!=null?otMap.get("ADJUST_YN").toString():"0";
			String otPlaceType = otMap.get("OT_PLACE_TYPE")!=null?otMap.get("OT_PLACE_TYPE").toString():"INSIDE";
			String applyRemark = otMap.get("APPLY_OT_REMARK")!=null?otMap.get("APPLY_OT_REMARK").toString():"";

			LinkedHashMap dateMap = new LinkedHashMap();
			dateMap = this.getLinkedMapByRequest(request, dateMap);
			dateMap.put("APPLY_NO", applyTempNo);
			dateMap.put("PERSON_ID", personId);
			dateMap.put("OT_TIME_TYPE", otTimeType);
			dateMap.put("APPLY_TYPE", applyType);
			dateMap.put("AFFIRM_FLAG", affirmFlag);
			dateMap.put("APPLY_OT_DATE", applyOtDate);
			
			dateMap.put("FROM_TIME", otFromTime);
			dateMap.put("TO_TIME", otToTime);
			dateMap.put("OT_DEDUCT_TIME", "0");
			dateMap.put("BEGIN_DAY_OFFSET", "0");
			dateMap.put("END_DAY_OFFSET", "0");

			dateMap.put("OT_APPLY_HOUR", otApplyHour);
			dateMap.put("OT_APPLY_MINUTE", otApplyMinute);
			
			dateMap.put("APPLY_TYPE_NO", applyTypeNo);
			dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
			dateMap.put("ADJUST_YN", adjustYn);
			dateMap.put("OT_PLACE_TYPE", otPlaceType);
			//页面获取到的加班类型是基础，不是最终保存到数据库中的加班类型，需要组合得出（目前只允许周末可调休）
			//1.根据加班类型（32：平日加班；33：周末加班；34：法定假加班；）
			//2.是否调休
			//3.社内、社外
			//4.在ess_ot_code_mapping表中获取对应的加班类型
			String otTypeCode = ((LinkedHashMap)this.infoApplyDao.getOtTypeCodeByTurn(dateMap)).get("OT_TYPE_CODE").toString();
			dateMap.put("OT_TYPE_CODE", otTypeCode);
			dateMap.put("APPLY_REMARK", applyRemark);
			
			//批量申请的员工无法进行决裁者的自主添加、修改、所以只能从系统里获取
			paramMap.put("APPLY_TYPE_NO", "31");//加班申请
			List affirmList = this.getAffirmorListByMap(paramMap);
			dateMap.put("affirmList", affirmList);
			LinkedHashMap applyMap = this.preAddOvertimeApply(dateMap, admin.getLanguage());
			batchOtApplyList.add(applyMap);
		}
		this.infoApplyDao.addOvertimeApplyInBatch(batchOtApplyList);
		return 1;
	}
	
	/**
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List preAddAffirmList(LinkedHashMap paramMap,HttpServletRequest request) throws Exception {
		//AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		//String cpnyId=admin.getCpnyId();
		//String createBy=admin.getAdminID();
		//Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		List<LinkedHashMap> tempAffirmList = new ArrayList<LinkedHashMap>();
		List<LinkedHashMap> returnAffirmList = new ArrayList<LinkedHashMap>();
		List addAffirmList = new ArrayList();
		List personList=new ArrayList();
		Enumeration e = request.getParameterNames() ;
		//获取页面添加的决裁者的决裁等级（页面等级）
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement() ;
			if(key.indexOf("dwz.person.personId")>-1){
				String dwzName="dwz.person.personId";
				if(key.equals("dwz.person.personId")){
					personList.add(0);	
				}else{
					personList.add(Integer.parseInt(key.substring(dwzName.length())));
				}
			}
		}
		//对页面获取的决裁者信息进行排序（按照页面决裁等级排序）
		Collections.sort(personList);
		for(int i=0;i<personList.size();i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			String keyName="";
			String affirmLevel = personList.get(i).toString(); 
			if(affirmLevel.equals(0)){
				keyName="dwz.person.personId";
			}else{
				keyName="dwz.person.personId"+affirmLevel;
			}
			//LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
			//获取添加的决裁者的person_id
			String personId=paramMap.get(keyName).toString();
			affirmMap.put("AFFIRMOR_ID", personId);
			affirmMap.put("AFFIRM_LEVEL", affirmLevel);
			affirmMap.put("AFFIRM_COM_TYPE", "ADD_COM");
			
			addAffirmList.add(affirmMap);
		}
		//从数据库中根据决裁设置获取决裁者信息
		
		//通过参数来查询决裁者
		String applyTypeNo = (String) paramMap.get("APPLY_TYPE_NO");
		String personId = (String)paramMap.get("PERSON_ID");
		String language = (String)paramMap.get("LANGUAGE");
		String applyLength = (String)paramMap.get("otLength");
		String applyTypeCode= (String) paramMap.get("OT_TYPE_CODE");
		List affirmerList = this.getAffirmorListByString(applyTypeNo, personId, applyTypeCode, applyLength, language);
		
		//List affirmerList = this.getAffirmorListByMap(paramMap);
		if(addAffirmList!=null && addAffirmList.size()>0){
			for(int i=0;i<addAffirmList.size();i++){
				LinkedHashMap addMap = (LinkedHashMap)addAffirmList.get(i) ;
				int addDeptLevel = Integer.parseInt(addMap.get("AFFIRM_LEVEL").toString());
				for(int j=0;j<affirmerList.size();j++){
					LinkedHashMap dbMap = (LinkedHashMap)affirmerList.get(j) ;
					int dbDeptLevel = Integer.parseInt(dbMap.get("AFFIRM_LEVEL").toString());
					if(dbDeptLevel >= addDeptLevel){
						((LinkedHashMap)affirmerList.get(j)).put("AFFIRM_LEVEL", dbDeptLevel+1);
					}
				}
				tempAffirmList = affirmerList;
				tempAffirmList.add(addMap);
			}
		}else{
			tempAffirmList = affirmerList;
		}
		int maxAffirmLevel = 0;
		for(int i=0;i<tempAffirmList.size();i++){
			LinkedHashMap affirmMap = (LinkedHashMap)tempAffirmList.get(i);
			int affirmLevel = Integer.parseInt(affirmMap.get("AFFIRM_LEVEL").toString());
			if(affirmLevel > maxAffirmLevel){
				maxAffirmLevel = affirmLevel;
			}
		}
		for(int m=0;m<maxAffirmLevel;m++){
			for(int n=0;n<tempAffirmList.size();n++){
				LinkedHashMap dataMap = (LinkedHashMap)tempAffirmList.get(n);
				int affirmLevel = Integer.parseInt(dataMap.get("AFFIRM_LEVEL").toString());
				if(affirmLevel==m+1){
					returnAffirmList.add(dataMap);
					break;
				}
			}
		}
		return returnAffirmList;
	}
	
	/**
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List preUpdateAffirmList(LinkedHashMap paramMap,HttpServletRequest request) throws Exception {
		//AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		//String cpnyId=admin.getCpnyId();
		//String createBy=admin.getAdminID();
		//Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		List<LinkedHashMap> tempAffirmList = new ArrayList<LinkedHashMap>();
		List<LinkedHashMap> returnAffirmList = new ArrayList<LinkedHashMap>();
		List addAffirmList = new ArrayList();
		List personList=new ArrayList();
		Enumeration e = request.getParameterNames() ;
		//获取页面添加的决裁者的决裁等级（页面等级）
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement() ;
			if(key.indexOf("dwz.person.personId")>-1){
				String dwzName="dwz.person.personId";
				if(key.equals("dwz.person.personId")){
					personList.add(0);	
				}else{
					personList.add(Integer.parseInt(key.substring(dwzName.length())));
				}
			}
		}
		//对页面获取的决裁者信息进行排序（按照页面决裁等级排序）
		Collections.sort(personList);
		for(int i=0;i<personList.size();i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			String keyName="";
			String affirmLevel = personList.get(i).toString(); 
			if(affirmLevel.equals(0)){
				keyName="dwz.person.personId";
			}else{
				keyName="dwz.person.personId"+affirmLevel;
			}
			//LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
			//获取添加的决裁者的person_id
			String personId = paramMap.get(keyName).toString();
			affirmMap.put("AFFIRMOR_ID", personId);
			affirmMap.put("AFFIRM_LEVEL", affirmLevel);
			affirmMap.put("AFFIRM_COM_TYPE", "ADD_COM");
			
			addAffirmList.add(affirmMap);
		}
		//从数据库中根据决裁设置获取决裁者信息，加班申请时从数据库中获取
	//	 List affirmerList = this.getAffirmorListByMap(paramMap);
		 
			//通过参数来查询决裁者
			String applyTypeNo = (String) paramMap.get("APPLY_TYPE_NO");
			String personId = (String)paramMap.get("PERSON_ID");
			String language = (String)paramMap.get("LANGUAGE");
			String applyLength = (String)paramMap.get("otLength");
			String applyTypeCode= (String) paramMap.get("OT_TYPE_CODE");
			List affirmerList = this.getAffirmorListByString(applyTypeNo, personId, applyTypeCode, applyLength, language);
		//加班申请修改时，需要从页面获取未删除的决裁者信息
		//List affirmerList = new ArrayList();
		List affirmNameList = new ArrayList();
		//获取页面添加的决裁者的决裁等级（页面等级）
		Enumeration e2 = request.getParameterNames() ;
		while (e2.hasMoreElements()) {
			String key = (String) e2.nextElement() ;
			if(key.indexOf("essAffirmNo_")>-1){
				String dwzName="essAffirmNo_";
				if(key.equals("essAffirmNo_")){
					affirmNameList.add(0);	
				}else{
					affirmNameList.add(Integer.parseInt(key.substring(dwzName.length())));
				}
			}
		}
		//对页面获取的决裁者信息进行排序（按照页面决裁等级排序）
		Collections.sort(affirmNameList);
		for(int i=0;i<affirmNameList.size();i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			String keyName="";
			String affirmLevelStr = affirmNameList.get(i).toString(); 
			if(affirmLevelStr.equals(0)){
				keyName="essAffirmNo_";
			}else{
				keyName="essAffirmNo_"+affirmLevelStr;
			}
			//LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
			//获取添加的决裁者的person_id
			String essAffirmRelation=paramMap.get(keyName).toString();
		    String essAffirmNo = essAffirmRelation.split(",")[0];
		    String affirmorId = essAffirmRelation.split(",")[1];
		    String affirmLevel = essAffirmRelation.split(",")[2];
		    String affirmComType = essAffirmRelation.split(",")[3];
		    
			affirmMap.put("ESS_AFFIRM_NO", essAffirmNo);
			affirmMap.put("AFFIRMOR_ID", affirmorId);
			affirmMap.put("AFFIRM_LEVEL", affirmLevel);
			affirmMap.put("AFFIRM_COM_TYPE", affirmComType);
			
			affirmerList.add(affirmMap);
		}
		
		if(addAffirmList!=null && addAffirmList.size()>0){
			for(int i=0;i<addAffirmList.size();i++){
				LinkedHashMap addMap = (LinkedHashMap)addAffirmList.get(i) ;
				int addDeptLevel = Integer.parseInt(addMap.get("AFFIRM_LEVEL").toString());
				for(int j=0;j<affirmerList.size();j++){
					LinkedHashMap dbMap = (LinkedHashMap)affirmerList.get(j) ;
					int dbDeptLevel = Integer.parseInt(dbMap.get("AFFIRM_LEVEL").toString());
					if(dbDeptLevel >= addDeptLevel){
						((LinkedHashMap)affirmerList.get(j)).put("AFFIRM_LEVEL", dbDeptLevel+1);
					}
				}
				tempAffirmList = affirmerList;
				tempAffirmList.add(addMap);
			}
		}else{
			tempAffirmList = affirmerList;
		}
		int maxAffirmLevel = 0;
		for(int i=0;i<tempAffirmList.size();i++){
			LinkedHashMap affirmMap = (LinkedHashMap)tempAffirmList.get(i);
			int affirmLevel = Integer.parseInt(affirmMap.get("AFFIRM_LEVEL").toString());
			if(affirmLevel > maxAffirmLevel){
				maxAffirmLevel = affirmLevel;
			}
		}
		for(int m=0;m<maxAffirmLevel;m++){
			for(int n=0;n<tempAffirmList.size();n++){
				LinkedHashMap dataMap = (LinkedHashMap)tempAffirmList.get(n);
				int affirmLevel = Integer.parseInt(dataMap.get("AFFIRM_LEVEL").toString());
				if(affirmLevel==m+1){
					returnAffirmList.add(dataMap);
					break;
				}
			}
		}
		
		return returnAffirmList;
	}
	
	/**
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddOvertimeApply(LinkedHashMap paramMap,String language) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		LinkedHashMap essParamMap = new LinkedHashMap();
		List distinctList = new ArrayList();
		// 需要决裁
		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
		//从系统配置里获取决裁者
		//List affirmerList = this.getAffirmorListByMap(paramMap);
		//从系统配置、页面添加的决裁者组合以后得出的决裁者信息
		List affirmerList = (ArrayList<LinkedHashMap>)paramMap.get("affirmList");
		if (affirmerList.size() == 0 ) {
			// 未给该员工设置决裁者时
			throw new CommonException(TipMessage.getTipMessage("alert.message.ess.infoApply.notFor", language)
					+ personMap.get("LOCAL_NAME")+ TipMessage.getTipMessage("alert.message.ess.infoApply.pleaseSetAffirmorFirst",language));
		} else {
			int count = 1;
			boolean flag = true;
			Map filterMap = new LinkedHashMap();
			for (int i = 0; i < affirmerList.size(); i++) {
				LinkedHashMap affirmerMap = (LinkedHashMap) affirmerList.get(i);
				if (affirmerMap.get("AFFIRMOR_ID") != null) {
					if (flag) {
						if(affirmerMap.get("AFFIRMOR_ID")!=null && "1".equals(affirmerMap.get("AFFIRM_LEVEL").toString())){
							//只将流程第一步的决裁者取出来存到加班申请表
							paramMap.put("CURRENT_AFFIRM_ID", affirmerMap.get("AFFIRMOR_ID"));
							flag = false;
						}
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
			String otTimeType = paramMap.get("OT_TIME_TYPE").toString();
			if(otTimeType!=null && "P".equals(otTimeType)){
				String otFromDate = paramMap.get("FROM_TIME") != null ? paramMap.get("FROM_TIME").toString() : "";
				String otToDate = paramMap.get("TO_TIME") != null ? paramMap.get("TO_TIME").toString() : "";
				//检查申请开始时间和结束时间,如果格式不对提示用户
				this.formatFromAndToDate(otFromDate, otToDate);
			}
			
			//各种验证（日期、时间、类型等）
			if (otApplyCheck(paramMap, personMap, language)){
					paramMap.put("ACTIVITY", "0");
					returnMap.put("PARAM_MAP", paramMap);
					returnMap.put("DISTINCT_LIST", distinctList);
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
	private boolean isZhuisuOt(LinkedHashMap paramMap) throws Exception {
			paramMap.put("FLAG", "S");// 获取考勤月开始日期
			String arStartDateStr = this.infoApplyLeaveDao
					.getCurrentArDateOt(paramMap);
			paramMap.put("FLAG", "E");// 获取考勤月结束日期
			String arEndDateStr = this.infoApplyLeaveDao.getCurrentArDateOt(paramMap);
			Date date = new Date();
			SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			String leaveApplyFrom = paramMap.get("AR_FROM_TIME") != null ? paramMap
					.get("AR_FROM_TIME").toString()
					: sb.format(date);
			String leaveApplyTo = paramMap.get("AR_TO_TIME") != null ? paramMap
					.get("AR_TO_TIME").toString() : sb.format(date);
			GregorianCalendar applyFrom = DateUtil
					.ParseGregorianCalendar(leaveApplyFrom);
			GregorianCalendar applyTo = DateUtil
					.ParseGregorianCalendar(leaveApplyTo);
			GregorianCalendar arStartDate = DateUtil
					.ParseGregorianCalendar(arStartDateStr);
			GregorianCalendar arEndDate = DateUtil
					.ParseGregorianCalendar(arEndDateStr);
		 
				arStartDate.add(2, -1);
				arEndDate.add(2, -1);
				//if  申请的开始时间>追溯考勤月的开始时间  and  申请结束时间  <最追溯考勤月的结束日期
				//包括所有法人
				if (applyFrom.after(arStartDate) && applyTo.before(arEndDate)) {
					return true;
				}
			 
			return false;
		}
	/**
		 * 验证开始日期是否超出限制
		 * overtime apply)
		 * 
		 * @param paramMap
		 * @return
		 */
		@SuppressWarnings("unchecked")
		private boolean otApplyForUpdate(Map paramMap)
				throws Exception {
			paramMap.put("FLAG", "S");//获取考勤月开始日期
			String arDateStr = this.infoApplyLeaveDao.getCurrentArDateOt(paramMap);
			 
			String emp_type_code = paramMap.get("empTypeCodeGroup") != null ? paramMap.get("empTypeCodeGroup").toString():"";
			Date date = new Date();
			SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			String leaveApplyFrom = paramMap.get("APPLY_OT_DATE") != null ? paramMap
						.get("APPLY_OT_DATE").toString()
						: sb.format(date);
			GregorianCalendar applyFrom = DateUtil
						.ParseGregorianCalendar(leaveApplyFrom);
			GregorianCalendar arDate = DateUtil
						.ParseGregorianCalendar(arDateStr);
			GregorianCalendar arDate2 = DateUtil.ParseGregorianCalendar(arDateStr);
			GregorianCalendar sysDate = DateUtil.ParseGregorianCalendar(sb.format(date));
			 
			arDate.add(2, -1);
			GregorianCalendar dateCurrent = DateUtil.ParseGregorianCalendar(arDateStr);
			dateCurrent.add(2, 1);
			 
				
				paramMap.put("R_DATE",leaveApplyFrom);
				paramMap.put("interCpnyID",paramMap.get("CPNY_ID"));
				String lastMonthFlag = infoApplyDao.arValidLastMonth(paramMap);
				if(!"OK".equals(lastMonthFlag)){
					throw new CommonException(lastMonthFlag);
				}
 		  
			if (applyFrom.before(arDate)){
				paramMap.put("zhuisuMsg", "加班日期不能早于" + DatatypeFactory.newInstance().newXMLGregorianCalendar(arDate).toString().substring(0, 10));
				return true;
			} 
			
			return false;
		}
		
		/**
		 * 验证开始日期是否超出限制
		 * overtime apply)
		 * 
		 * @param paramMap
		 * @return
		 */
		@SuppressWarnings("unchecked")
		private boolean otApplyForUpdateimp(Map paramMap)
				throws Exception {
			paramMap.put("FLAG", "S");//获取考勤月开始日期
			String arDateStr = this.infoApplyLeaveDao.getCurrentArDateOt(paramMap);
			 
			String emp_type_code = paramMap.get("empTypeCodeGroup") != null ? paramMap.get("empTypeCodeGroup").toString():"";
			Date date = new Date();
			SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			String leaveApplyFrom = paramMap.get("APPLY_OT_DATE") != null ? paramMap
						.get("APPLY_OT_DATE").toString()
						: sb.format(date);
			GregorianCalendar applyFrom = DateUtil
						.ParseGregorianCalendar(leaveApplyFrom);
			GregorianCalendar arDate = DateUtil
						.ParseGregorianCalendar(arDateStr);
			GregorianCalendar arDate2 = DateUtil.ParseGregorianCalendar(arDateStr);
			GregorianCalendar sysDate = DateUtil.ParseGregorianCalendar(sb.format(date));
			 
			arDate.add(2, -1);
			GregorianCalendar dateCurrent = DateUtil.ParseGregorianCalendar(arDateStr);
			dateCurrent.add(2, 1);
			 
				
				paramMap.put("R_DATE",leaveApplyFrom);
				paramMap.put("interCpnyID",paramMap.get("CPNY_ID"));
				String lastMonthFlag = infoApplyDao.arValidLastMonth(paramMap);
				if(!"OK".equals(lastMonthFlag)){
					paramMap.put("zhuisuMsg",lastMonthFlag);
					return true;
				}
 		  
			if (applyFrom.before(arDate)){
				paramMap.put("zhuisuMsg", "加班日期不能早于" + DatatypeFactory.newInstance().newXMLGregorianCalendar(arDate).toString().substring(0, 10));
				return true;
			} 
			
			return false;
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
		//String ifForcedTypeChoice = paramMap.get("ifForcedTypeChoice") != null ? paramMap.get("ifForcedTypeChoice").toString(): "0";
		String otTimeType = paramMap.get("OT_TIME_TYPE") != null ? paramMap.get("OT_TIME_TYPE").toString(): "P";
		String applyDateStr = paramMap.get("APPLY_OT_DATE") != null ? paramMap.get("APPLY_OT_DATE").toString() : "";
		String cpnyid = paramMap.get("CPNY_ID")!=null ?paramMap.get("CPNY_ID").toString():"";
		String emptypecode = paramMap.get("EMP_TYPE_CODE") != null ? paramMap.get("EMP_TYPE_CODE").toString():"";
		double otlength =  paramMap.get("otLength")!=null ?Double.parseDouble(paramMap.get("otLength").toString()):0;
		String dateType =  paramMap.get("dateType")!=null ?paramMap.get("dateType").toString():"";
		String PERSON_ID =  paramMap.get("PERSON_ID").toString();
		String applytypecode = paramMap.get("OT_TYPE_CODE")!=null ?paramMap.get("OT_TYPE_CODE").toString():"";
		String FROM_TIME = paramMap.get("FROM_TIME")!=null ?paramMap.get("FROM_TIME").toString():"";
		String TO_TIME = paramMap.get("TO_TIME")!=null ?paramMap.get("TO_TIME").toString():"";
		String result = "";
		LinkedHashMap dataMap = new LinkedHashMap();
		LinkedHashMap checkMap = new LinkedHashMap();
		dataMap.put("PERSON_ID", PERSON_ID);
		dataMap.put("AR_DATE_STR", applyDateStr);
		//验证是否进行了加班申请
		
		List dataList = this.getOtArDetailForMoreOt(paramMap);
		if (!"1441".equals(dateType)) {
			for(int i=0;i<dataList.size();i++){
				LinkedHashMap detailMap = (LinkedHashMap) dataList.get(i);
				if ( Double.parseDouble(detailMap.get("QUANTITY").toString()) !=0.00 ) {
					throw new CommonException(
							TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
									+ personMap.get("LOCAL_NAME")
									+ "已经进行了加班申请");
				}
			}
		}
		//个人女性三期的验证
		if("TSTO".equals(cpnyid)){
			checkMap.put("APPLY_DATE", applyDateStr);
			checkMap.put("PERSON_ID", paramMap.get("PERSON_ID"));
		    result = this.infoApplyLeaveDao.getOTCheckTSTO(checkMap);
			if ( !"OK".equals(result) ) {
				throw new CommonException(result);
			}
		}
		if ("1441".equals(dateType)) {
			double ot = 0.00; 
			for(int i=0;i<dataList.size();i++){
				//获取到加班的每条数据
				LinkedHashMap objHashMap = (LinkedHashMap) dataList.get(i);
				SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat formStr = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				String oldFromDate = objHashMap.get("FROM_TIME_OLD").toString();
				Date  oldFromDateForDate = form.parse(oldFromDate);//已申请记录的开始结束时间
				String oldToDate = objHashMap.get("TO_TIME_OLD").toString();
				Date  oldToDateForDate = form.parse(oldToDate);//已申请记录的开始结束时间
				Date  nowFromDateForDate = formStr.parse(FROM_TIME);//现在记录的开始结束时间
				//Date nowToDateForDate =form.parse(paramMap.get("TO_TIME").toString());//现在输入
				if (oldToDateForDate.getTime() > nowFromDateForDate.getTime() && nowFromDateForDate.getTime() < oldToDateForDate.getTime()) {
					throw new CommonException(
							TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
									+ personMap.get("LOCAL_NAME")
									+ "在"+formStr.format(oldFromDateForDate)+'到'+formStr.format(oldToDateForDate)+"之间已进行了加班申请！！！");
				}
			}
			
			for(int i=0;i<dataList.size();i++){
				LinkedHashMap objHashMap22 = (LinkedHashMap) dataList.get(i);
				ot = ot+Double.parseDouble( objHashMap22.get("QUANTITY").toString());
			}
			
			if (otlength + ot > 8) {
				throw new CommonException(
						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
								+ personMap.get("LOCAL_NAME")
								+ "当前已申请时长"+ot+"小时，周末累计加班超出8小时");
			}
		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private LinkedHashMap getOtArDetailForPkNo(LinkedHashMap paramMap) {
		return infoApplyDao.getOtArDetailForPkNo(paramMap);
	}
	@SuppressWarnings("unchecked")
	private List getOtArDetailForMoreOt(LinkedHashMap paramMap) {
		return infoApplyDao.getOtArDetailForMoreOt(paramMap);
	}

	/**
	 * 验证加班是否超过日期限制
	 *   2014-08-15  by:wangqiang
	 * @throws DatatypeConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public boolean otApplyOtfromTimeCheck(Map paramMap) throws DatatypeConfigurationException{
		
		paramMap.put("FLAG", "S");//获取考勤月开始日期
		
		String arDateStr = this.infoApplyDao.getCurrentArDate(paramMap);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		String leaveApplyFrom = paramMap.get("FROM_TIME") != null ? paramMap
					.get("FROM_TIME").toString()
					: sb.format(date);
					//这里比的不仅是日期 还有时间，但只需要日期就可以了
					if(leaveApplyFrom.length()>=10){
						leaveApplyFrom=leaveApplyFrom.substring(0,10);
					}
					
		 if(paramMap.get("CPNY_ID").equals("TSTO")){
			 leaveApplyFrom = paramMap.get("APPLY_OT_DATE") != null ? paramMap
						.get("APPLY_OT_DATE").toString()
						: sb.format(date);

		 }			
		GregorianCalendar applyFrom = DateUtil
					.ParseGregorianCalendar(leaveApplyFrom);
		GregorianCalendar arDate = DateUtil
					.ParseGregorianCalendar(arDateStr);
	 
			arDate.add(2, -1);
		 
		if (applyFrom.before(arDate)){
			paramMap.put("zhuisuMsg", DatatypeFactory.newInstance().newXMLGregorianCalendar(arDate).toString().substring(0, 10));
			return true;
		}
		return false;
		
		
		
		 
	  
	}
	
	/**
	 * 获取班次 ,加班类型(batch add leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDateByPersonIdAndCpny(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;	
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", request.getParameter("personid"));
		paramMap.put("FROM_DATE", request.getParameter("time"));
		paramMap.put("CPNY_ID", admin.getCpnyId() );//interLanguage
		paramMap.put("interLanguage", admin.getLanguage());
		retrunList = infoApplyDao.getDateByPersonIdAndCpny(paramMap) ;		
		return retrunList ;
	}
	
	/**
	 * 获取日期类型(get the date type code)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDateTypeByDateAndCpny(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;	
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		//paramMap.put("PERSON_ID", request.getParameter("personid"));
		paramMap.put("DDATE_STR", request.getParameter("DDATE_STR"));
		paramMap.put("CPNY_ID", admin.getCpnyId() );//interLanguage
		paramMap.put("interLanguage", admin.getLanguage());
		retrunList = infoApplyDao.getDateTypeByDateAndCpny(paramMap) ;		
		return retrunList ;
	}
	@SuppressWarnings("unchecked")
	public Map getDefaultStartEndTime(HttpServletRequest request) throws Exception {
		Map dataMap = new HashMap();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String SHIFT_NO = (String) infoApplyDao.getShiftNoForOt(paramMap);
		paramMap.put("SHIFT_NO", SHIFT_NO);
		paramMap.put("PERSON_ID",SessionUtil.getLoginUserFromSession(request).getPersonId());
		dataMap = infoApplyDao.getDefaultStartEndTime(paramMap) ;		
		return dataMap ;
	}
	@SuppressWarnings("unchecked")
	public Map getOtLimit(HttpServletRequest request) throws Exception {
		Map dataMap = new HashMap();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		paramMap.put("PERSON_ID",SessionUtil.getLoginUserFromSession(request).getPersonId());
		dataMap = infoApplyDao.getOtLimit(paramMap) ;		
		return dataMap ;
	}
	@SuppressWarnings("unchecked")
	public List getChangeOtType(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;	
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		//paramMap.put("PERSON_ID", request.getParameter("personid"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID")!=null?request.getParameter("PERSON_ID"):admin.getPersonId());
	 
		paramMap.put("CPNY_ID", admin.getCpnyId() );//interLanguage
		paramMap.put("interLanguage", admin.getLanguage());
		retrunList = infoApplyDao.getChangeOtType(paramMap) ;		
		return retrunList ;
	}
	
	/**
	 * 获取日期类型(get the date type code)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDataType(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;	
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		//paramMap.put("PERSON_ID", request.getParameter("personid"));
		paramMap.put("DDATE_STR", request.getParameter("DDATE_STR")==null?request.getParameter("OT_DATE"):request.getParameter("DDATE_STR"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		paramMap.put("CPNY_ID", admin.getCpnyId() );//interLanguage
		paramMap.put("interLanguage", admin.getLanguage());
		retrunList = infoApplyDao.getDataType2(paramMap) ;		
		return retrunList ;
	}
	/**
	 * 获取日期类型(get the date type code)
	 * 
	 * @param request
	 * @return
	 * by: wangqiang
	 * @throws Exceptiom
	 */
	@SuppressWarnings("unchecked")
	public List getDataTypeW(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;	
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		//paramMap.put("PERSON_ID", request.getParameter("personid"));
		paramMap.put("DDATE_STR", request.getParameter("DDATE_STR")==null?request.getParameter("OT_DATE"):request.getParameter("DDATE_STR"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		paramMap.put("CPNY_ID", admin.getCpnyId() );//interLanguage
		paramMap.put("FROM_TIME",request.getParameter("FROM_TIME")!=null?request.getParameter("FROM_TIME"):"");
		paramMap.put("TESHU_YN",request.getParameter("TESHU_YN")!=null?request.getParameter("TESHU_YN"):"");
		paramMap.put("interLanguage", admin.getLanguage());
		retrunList = infoApplyDao.getDataType2w(paramMap) ;		
		return retrunList ;
	}
	/**
	 * 获取公司日期类型
	 */
	@SuppressWarnings("unchecked")
	public List getDataTypeTsto(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;	
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		//paramMap.put("PERSON_ID", request.getParameter("personid"));
		paramMap.put("DDATE_STR", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId() );//interLanguage
		paramMap.put("FROM_TIME",new SimpleDateFormat("yyyy-MM-dd").format(new Date())+""+"08:00:00");
		paramMap.put("TESHU_YN",request.getParameter("TESHU_YN")!=null?request.getParameter("TESHU_YN"):"");
		paramMap.put("interLanguage", admin.getLanguage());
		retrunList = infoApplyDao.getDataType2w(paramMap) ;		
		return retrunList ;
	}
	
	
	/** 
	* @Title: getDataTypeForPeople 
	* @Description: TODO 查询个人排班的日期类型
	* @param @param request
	* @param @return
	* @param @throws Exception    
	* @return List    
	* @throws 20141214 孙鹏修改
	*/
	@SuppressWarnings("unchecked")
	public List getDataTypeForPeople(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;	
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("DDATE_STR", request.getParameter("DDATE_STR")==null?request.getParameter("OT_DATE"):request.getParameter("DDATE_STR"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		paramMap.put("CPNY_ID", admin.getCpnyId() );//interLanguage
		paramMap.put("FROM_TIME",request.getParameter("FROM_TIME")!=null?request.getParameter("FROM_TIME"):"");
		paramMap.put("TESHU_YN",request.getParameter("TESHU_YN")!=null?request.getParameter("TESHU_YN"):"");
		paramMap.put("interLanguage", admin.getLanguage());
		if("SST".equals(admin.getCpnyId())){
			retrunList = infoApplyDao.getDataTypeForPeople(paramMap) ;	
			
		} 
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getDataTypeForPeopleW(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;	
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("DDATE_STR", request.getParameter("DDATE_STR")==null?request.getParameter("OT_DATE"):request.getParameter("DDATE_STR"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		paramMap.put("CPNY_ID", admin.getCpnyId() );//interLanguage
		paramMap.put("FROM_TIME",request.getParameter("FROM_TIME")!=null?request.getParameter("FROM_TIME"):"");
		paramMap.put("TESHU_YN",request.getParameter("TESHU_YN")!=null?request.getParameter("TESHU_YN"):"");
		paramMap.put("interLanguage", admin.getLanguage());
 
	 retrunList = infoApplyDao.getDataTypeForPeopleW(paramMap) ;	
			
		 
		return retrunList ;
	}
	
	/**
	 * 根据加班申请NO查询此次（个人/批量）申请的所有人(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getApplyorByApplyNoList(HttpServletRequest request) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("interLanguage", "zh");
			
		}else{
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		}
 
		List returnList = new ArrayList();
		// 页面提交数据
		returnList = infoApplyDao.getApplyorByApplyNoList(paramMap);
		
		return returnList;
	}
	
	/**
	 * 根据加班申请NO决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorByApplyNoList(HttpServletRequest request) throws Exception {
		 
		List returnList = new ArrayList();
		// 页面提交数据
		
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	 
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		returnList = infoApplyDao.getAffirmorByApplyNoList(paramMap);
		
		return returnList;
	}
	/**
	 * 根据加班申请NO决裁信息查询(search ot info list)小页面用
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorByApplyNoListXiao(HttpServletRequest request) throws Exception {
		 
		List returnList = new ArrayList();
		// 页面提交数据
		
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	 
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		returnList = infoApplyDao.getAffirmorByApplyNoListXiao(paramMap);
		
		return returnList;
	}

	/**
	 * 根据加班申请NO决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorListByApplyNo(LinkedHashMap paramMap) throws Exception {
		List returnList = new ArrayList();
		// 页面提交数据
		returnList = infoApplyDao.getAffirmorByApplyNoList(paramMap);
		
		return returnList;
	}
	
	/**
	 * 加班申请check信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCheckorByApplyNoList(HttpServletRequest request) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);


		List returnList = new ArrayList();
		
	
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("ADMIN_ID", admin.getPersonId());
		}
		// 页面提交数据
		returnList = infoApplyDao.getCheckorByApplyNoList(paramMap);
		
		return returnList;
	}
	/**
	 * 加班申请check信息查询(search ot info list)小页面用，决裁页面显示所添加的check人查所有人
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCheckorByApplyNoListXiao(HttpServletRequest request) throws Exception {
 
	 
		
		List returnList = new ArrayList();
		
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	 
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		//	paramMap.put("ADMIN_ID_temp", "ss");  
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
	 
		// 页面提交数据
		returnList = infoApplyDao.getCheckorByApplyNoListXiao(paramMap);
		
		return returnList;
	}
	/**
	 * 加班申请check信息查询(search ot info list)小页面用进行check用,只查个人
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCheckorByApplyNoListXiaoPerson(HttpServletRequest request) throws Exception {
 
	 
		
		List returnList = new ArrayList();
		
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	 
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			paramMap.put("ADMIN_ID_temp", "ss");
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
	 
		// 页面提交数据
		returnList = infoApplyDao.getCheckorByApplyNoListXiao(paramMap);
		
		return returnList;
	}
	
	/**
	 * 批量加班申请(batch overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delOvertimeApplyInBatch(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap personMap = new LinkedHashMap();
		personMap.put("APPLY_PERSON", admin.getPersonId());
		String op_flag = request.getParameter("OP_FLAG")!=null?request.getParameter("OP_FLAG"):"0";
		List deleteList = new ArrayList();
		List list = new ArrayList();
		try {
			// 批量封装加班申请数据并处理
			if (!"2".equals(op_flag)&&!"0".equals(op_flag)) {
			    list = this.encapsulationApplyNoListForBatch(request);
			}
			if("0".equals(op_flag)){//删除
				deleteList = arDetailSerImp.encapsulationOverTimeListForDelete(request);
				this.infoApplyDao.delOvertimeApplyInBatchTSTO(deleteList);//加班数据的删除
			}if("2".equals(op_flag)){//添加
				LinkedHashMap map = new LinkedHashMap();
				map.put("CPNY_ID",admin.getCpnyId());
				map.put("CREATED_BY",admin.getAdminID());
				map.put("CREATED_IP",admin.getAdminIP());
				map.put("applyBatchdate",request.getParameter("APPLY_DATE"));
				this.infoApplyLeaveDao.addtOtTSTOApplyInBatch(map);
			}else if ("3".equals(op_flag)) {
				List list2 = this.encapsulationApplyNoListForBatch2(request);
				this.infoApplyDao.updateOvertimeApplyInBatchForCancel(list2);
			}else{//提交
				for(int i=0;i<list.size();i++){
					LinkedHashMap paramMap = (LinkedHashMap) list.get(i);
				    personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);
					boolean passFlag = false;
					passFlag = this.arApplyCheckBatchOverTime(paramMap, personMap, admin.getLanguage());
					String PK_NO = (String) paramMap.get("APPLY_NO");
					paramMap.put("PK_NO", PK_NO);
					//获取数据库该条选中数据的决裁状态
					String oldAffrim = infoApplyLeaveDao.getChechedAffrim(paramMap) ;
					paramMap.put("oldAffrim", oldAffrim);
					
					//修改考勤的班次及时间
					if (!paramMap.get("OLD_SHIFT_NO").equals(paramMap.get("SHIFT_NO"))) {
						String kaoQinNo = infoApplyLeaveDao.getkaoQinNo(paramMap);
						if ("141439".equals(kaoQinNo)||"141440".equals(kaoQinNo)) {
							String APPLY_NO_FOR_SHIFTNO = infoApplyLeaveDao.getkaoQinNoPkNo(paramMap);
							LinkedHashMap flMap = new LinkedHashMap();
							flMap.put("CPNY_ID",admin.getCpnyId() );
							flMap.put("from_date", paramMap.get("APPLY_DATE"));
							flMap.put("SHIFT_NO", paramMap.get("SHIFT_NO"));
							LinkedHashMap timeMap  = infoApplyDao.getOtApplyWorkTimeU(flMap) ;
							paramMap.put("FIRST_FOR_SHIFTNO",timeMap.get("FIRST_TIME"));
							paramMap.put("LAST_FOR_SHIFTNO", timeMap.get("LAST_TIME"));
							paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
							this.infoApplyDao.updatArDetailBatchApplyForSHIFT(paramMap);
						}else {
							throw new CommonException(personMap.get("LOCAL_NAME")+ "已经进行了休假申请");
						}
						
					}
					
					 //开始:14014306; 部门申请: 14014307;部门长批准 ：14014308;部门长返回:14014309;上申取消:14014310;部门申请(后):14014311;部门长批准(后):14014312
					if (passFlag==true) {
						if ("14014306".equals(oldAffrim)) {
							//如果做部门申请，初始状态就做插入
							this.infoApplyDao.addOtApplyInArDetailBatch(paramMap);
							//this.infoApplyDao.updatSubmitLeaveApplyInBatchForDeletePrepare(paramMap); 
					        //this.infoApplyDao.insertSubmitLeaveApplyInBatch(paramMap); 
							this.infoApplyDao.updatSubmitArDetailBatchApplyForFirstAdd(paramMap);//修改detail表
						} else {
							
							this.infoApplyDao.updatSubmitArDetailBatchApply(paramMap);
							this.infoApplyDao.updateOtApplyInArDetailBatchApply(paramMap);//修改申请表
						 }
					} else {
						 String ERROR_MSG = (String) paramMap.get("ERROR_MSG");
						 throw new CommonException(ERROR_MSG);
					}
				}
			}
			
		}catch (CommonException e1) {
			throw e1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量加班的验证
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean arApplyCheckBatchOverTime(LinkedHashMap paramMap, LinkedHashMap personMap,
			String language) throws Exception {
		String AR_DATE_STR = paramMap.get("AR_DATE_STR") != null ? paramMap.get("AR_DATE_STR").toString() : "";
		String fromTime = paramMap.get("fromTime") != null ? paramMap.get("fromTime").toString() : "";
		String toTime = paramMap.get("toTime") != null ? paramMap.get("toTime").toString() : "";
		String APPLY_NO = paramMap.get("APPLY_NO") != null ? paramMap.get("APPLY_NO").toString() : "";
		String APPLY_LENGTH = paramMap.get("APPLY_LENGTH") != null ? paramMap.get("APPLY_LENGTH").toString() : "";
		String ITEM_NO = paramMap.get("ITEM_NO") != null ? paramMap.get("ITEM_NO").toString() : "";
		String APPLY_DATE = paramMap.get("APPLY_DATE") != null ? paramMap.get("APPLY_DATE").toString() : "";
		String CPNY_ID =  paramMap.get("CPNY_ID") != null ? paramMap.get("CPNY_ID").toString(): "";
		String result ="";
			//加班时间验证
			LinkedHashMap checkMap = new LinkedHashMap();
			
			checkMap.put("AR_DATE_STR", AR_DATE_STR);
			checkMap.put("APPLY_LENGTH", APPLY_LENGTH);
			checkMap.put("ITEM_NO", ITEM_NO);
			checkMap.put("PK_NO",APPLY_NO);
			checkMap.put("fromTime", fromTime);
			checkMap.put("toTime", toTime);
			checkMap.put("PERSON_ID", paramMap.get("PERSON_ID"));
		    result = this.infoApplyLeaveDao.getCoordOverTimeCheckTSTO(checkMap);
			if ( !"OK".equals(result) ) {
				paramMap.put("ERROR_MSG",result);
				return false;
			}
			//女性三期验证
			if("TSTO".equals(CPNY_ID)){
				checkMap.put("APPLY_DATE", APPLY_DATE);
				checkMap.put("PERSON_ID", paramMap.get("PERSON_ID"));
			    result = this.infoApplyLeaveDao.getOTCheckTSTO(checkMap);
				if ( !"OK".equals(result) ) {
					paramMap.put("ERROR_MSG",result);
					return false;
				}
			}
		return true;
	}
	
	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delOvertimeApplyInBatch2(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap personMap = new LinkedHashMap();
		personMap.put("APPLY_PERSON", admin.getPersonId());
		//String op_flag = request.getParameter("OP_FLAG")!=null?request.getParameter("OP_FLAG"):"0";
		
		//if ("3".equals(op_flag)) {
				List list2 = this.encapsulationApplyNoListForBatch2(request);
				/*if("TSTO".equals(admin.getCpnyId())){*/
					this.infoApplyDao.updateOvertimeApplyInBatchForCancel(list2);
				/*}else{
					this.infoApplyLeaveDao.delLeaveApplyInBatchSST(list2);
				}*/
		//}
		return 1;
	}

	/**
	 * 内务加班申请检查
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean arApplyCheckBatch2(LinkedHashMap paramMap, LinkedHashMap personMap,
			String language) throws Exception {
		String APPLY_DATE = paramMap.get("APPLY_DATE") != null ? paramMap.get("APPLY_DATE").toString() : "";
		
		String CPNY_ID =  paramMap.get("CPNY_ID") != null ? paramMap.get("CPNY_ID").toString(): "";
		//女性三期验证
		if("TSTO".equals(CPNY_ID)){
			//加班时间验证
			LinkedHashMap checkMap = new LinkedHashMap();
			
			checkMap.put("APPLY_DATE", APPLY_DATE);
			checkMap.put("PERSON_ID", paramMap.get("PERSON_ID"));
			String result = this.infoApplyLeaveDao.getOTCheckTSTO(checkMap);
			if ( !"OK".equals(result) ) {
				paramMap.put("ERROR_MSG",result);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 批量封装信息申请信息成List(encapsulation information apply from request to List for
	 * batch)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List encapsulationApplyNoListForBatch(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("c1");
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = new LinkedHashMap();
				String personId = paramMap.get("personid"+paramData[i]) != null ? paramMap.get("personid"+paramData[i]).toString():admin.getPersonId();
				String APPLY_DATE = paramMap.get("APPLY_DATE"+paramData[i]) != null ? paramMap.get("APPLY_DATE"+paramData[i]).toString():"";
				String fromTime = paramMap.get("fromTime"+paramData[i]) != null ? paramMap.get("fromTime"+paramData[i]).toString():"";
				String toTime = paramMap.get("toTime"+paramData[i]) != null ? paramMap.get("toTime"+paramData[i]).toString():"";
				String APPLY_LENGTH = paramMap.get("APPLY_LENGTH"+paramData[i]) != null ? paramMap.get("APPLY_LENGTH"+paramData[i]).toString():"0";
				String reason = paramMap.get("reason"+paramData[i]) != null ? paramMap.get("reason"+paramData[i]).toString():"";
				String otherReason = paramMap.get("otherReason"+paramData[i]) != null ? paramMap.get("otherReason"+paramData[i]).toString():"";
				String AFFIRM_FLAG = paramMap.get("AFFIRM_FLAG"+paramData[i]) != null ? paramMap.get("AFFIRM_FLAG"+paramData[i]).toString():"14014306";
				String ITEM_NO = paramMap.get("ITEM_NO"+paramData[i]) != null ? paramMap.get("ITEM_NO"+paramData[i]).toString():"";
				String APPLY_TYPE_CODE = paramMap.get("APPLY_TYPE_CODE"+paramData[i]) != null ? paramMap.get("APPLY_TYPE_CODE"+paramData[i]).toString():"";
				String SHIFT_NO = paramMap.get("SHIFT_NO"+paramData[i]) != null ? paramMap.get("SHIFT_NO"+paramData[i]).toString():"";
				String GROUP_ID = paramMap.get("GROUP_ID"+paramData[i]) != null ? paramMap.get("GROUP_ID"+paramData[i]).toString():"";
				String DEPTNO = paramMap.get("DEPTNO"+paramData[i]) != null ? paramMap.get("DEPTNO"+paramData[i]).toString():"";
				String DATE_TYPE = paramMap.get("DATE_TYPE"+paramData[i]) != null ? paramMap.get("DATE_TYPE"+paramData[i]).toString():"";
				String UNIT = paramMap.get("UNIT"+paramData[i]) != null ? paramMap.get("UNIT"+paramData[i]).toString():"";
				String STATUS_CODE = paramMap.get("STATUS_CODE"+paramData[i]) != null ? paramMap.get("STATUS_CODE"+paramData[i]).toString():"";
				String STATUS_NAME = paramMap.get("STATUS_NAME"+paramData[i]) != null ? paramMap.get("STATUS_NAME"+paramData[i]).toString():"";
				String IWEEK = paramMap.get("IWEEK"+paramData[i]) != null ? paramMap.get("IWEEK"+paramData[i]).toString():"";
				String LOCK_YN = paramMap.get("LOCK_YN"+paramData[i]) != null ? paramMap.get("LOCK_YN"+paramData[i]).toString():"";		
				String APPLY_LOCK = paramMap.get("APPLYLOCK"+paramData[i]) != null ? paramMap.get("APPLYLOCK"+paramData[i]).toString():"0";
				String Lotlengthonehour = paramMap.get("Lotlengthonehour"+paramData[i]) != null ? paramMap.get("Lotlengthonehour"+paramData[i]).toString():"0";		
				String Lotlengthonemin = paramMap.get("Lotlengthonemin"+paramData[i]) != null ? paramMap.get("Lotlengthonemin"+paramData[i]).toString():"0";		
				String allowance = paramMap.get("allowance"+paramData[i]) != null ? paramMap.get("allowance"+paramData[i]).toString():"0";		
				String FIRST_TIME = paramMap.get("FIRST_TIME"+paramData[i]) != null ? paramMap.get("FIRST_TIME"+paramData[i]).toString():"";		
				String LAST_TIME = paramMap.get("LAST_TIME"+paramData[i]) != null ? paramMap.get("LAST_TIME"+paramData[i]).toString():"";		
				String POST_GRADE_NO = paramMap.get("POST_GRADE_NO"+paramData[i]) != null ? paramMap.get("POST_GRADE_NO"+paramData[i]).toString():"";		
				String CONFIRM_FLAG = paramMap.get("CONFIRM_FLAG"+paramData[i]) != null ? paramMap.get("CONFIRM_FLAG"+paramData[i]).toString():"0";		
				String OLD_SHIFT_NO = paramMap.get("OLD_SHIFT_NO"+paramData[i]) != null ? 
						paramMap.get("OLD_SHIFT_NO"+paramData[i]).toString():"";
				String OLD_APPLY_NO = paramMap.get("OLD_APPLY_NO"+paramData[i]) != null ? paramMap.get("OLD_APPLY_NO"+paramData[i]).toString():"";
				
			
				map.put("SHIFT_NO", SHIFT_NO);
				map.put("CONFIRM_FLAG", CONFIRM_FLAG);
				map.put("OLD_SHIFT_NO", OLD_SHIFT_NO);
				map.put("interLanguage", admin.getLanguage());
				map.put("POST_GRADE_NO", POST_GRADE_NO);
				map.put("FIRST_TIME", FIRST_TIME);
				map.put("LAST_TIME", LAST_TIME);
				map.put("GROUP_ID", GROUP_ID);
				map.put("DATE_TYPE", DATE_TYPE);
				map.put("APPLY_LOCK", APPLY_LOCK);
				map.put("Lotlengthonehour", Lotlengthonehour);
				map.put("Lotlengthonemin", Lotlengthonemin);
				map.put("ADJST_YN","0");
				map.put("OLD_ADJST_YN","0");
				//获取考勤月
				SimpleDateFormat sdfDateFormat1=new SimpleDateFormat("yyyyMM");
			    Date AR_MONTH = sdfDateFormat1.parse(APPLY_DATE.substring(0,4)+   APPLY_DATE.substring(5,7));
			    Calendar calendar = Calendar.getInstance();//日历对象
			    calendar.setTime(AR_MONTH);//设置当前日期
			    calendar.add(Calendar.MONTH, 1);//月份加1
			    map.put("AR_MONTH_STR", sdfDateFormat1.format(calendar.getTime()));
			    map.put("AR_DATE_STR", APPLY_DATE);
				map.put("DEPTNO", DEPTNO);
				map.put("UNIT", UNIT);
				map.put("allowance", allowance);
				map.put("STATUS_CODE", STATUS_CODE);
				map.put("STATUS_NAME", STATUS_NAME);
				map.put("IWEEK", IWEEK);
				map.put("LOCK_YN", LOCK_YN);
				map.put("AFFIRM_FLAG", AFFIRM_FLAG);
				map.put("APPLY_TYPE_CODE", APPLY_TYPE_CODE);//申请类型
				map.put("ITEM_NO", ITEM_NO);		
				map.put("reason", reason);
				map.put("otherReason", otherReason);
				map.put("APPLY_LENGTH", APPLY_LENGTH);
				map.put("fromTime", fromTime);
				map.put("toTime", toTime);
				map.put("APPLY_DATE", APPLY_DATE);
				map.put("PERSON_ID", personId);
				map.put("CREATED_BY", admin.getPersonId());
				if (!"".equals(OLD_APPLY_NO)&&OLD_APPLY_NO != null) {
					map.put("APPLY_NO",OLD_APPLY_NO);
				}else {
					map.put("APPLY_NO", paramData[i]);
				}
				map.put("APPLY_TYPE", "21");
				map.put("CPNY_ID", admin.getCpnyId());
				map.put("CREATED_IP", admin.getAdminIP());
				map.put("UPDATED_IP", admin.getAdminIP());
				map.put("UPDATED_BY", admin.getPersonId());
				//通过决裁状态设置当前数据的有效性
				if ("14014306".equals(AFFIRM_FLAG)) {
					map.put("ACTIVITY", 0);
				}else if ("14014307".equals(AFFIRM_FLAG)) {
					map.put("ACTIVITY", 0);
				}else if ("14014308".equals(AFFIRM_FLAG)) {
					map.put("ACTIVITY", 1);
				}else if ("14014309".equals(AFFIRM_FLAG)) {
					map.put("ACTIVITY", 0);
				}else if ("14014310".equals(AFFIRM_FLAG)) {
					map.put("ACTIVITY", 0);
				}else if ("14014311".equals(AFFIRM_FLAG)) {
					map.put("ACTIVITY", 0);
				}else if ("14014312".equals(AFFIRM_FLAG)) {
					map.put("ACTIVITY", 1);
				}
				//map.put("OT_FROM_TIME",this.getOtFromTimeOrToTime(map,"START"));
				//map.put("OT_TO_TIME", this.getOtFromTimeOrToTime(map,"END"));
				map.put("OT_FROM_TIME",APPLY_DATE + " " + fromTime.substring(0,2) + ":"+fromTime.substring(2,4)+ ":" + "00" );
				map.put("OT_TO_TIME",APPLY_DATE + " " + toTime.substring(0,2) + ":"+toTime.substring(2,4)+ ":" + "00" );
				LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(map);
				if (APPLY_TYPE_CODE  != null && !"".equals(APPLY_TYPE_CODE)) {
					List  affirmList =  this.getAffirmorListByString("31", 
							 personId, this.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),APPLY_TYPE_CODE).toString(),APPLY_LENGTH,admin.getLanguage());
				map.put("affirmList", affirmList);
				}
				List affirmerList = (ArrayList<LinkedHashMap>)map.get("affirmList");
				if (affirmerList.size() == 0 ) {
					// 未给该员工设置决裁者时
					throw new CommonException(TipMessage.getTipMessage("alert.message.ess.infoApply.notFor", admin.getLanguage())
							+ personMap.get("LOCAL_NAME")+ TipMessage.getTipMessage("alert.message.ess.infoApply.pleaseSetAffirmorFirst",admin.getLanguage()));
				} else {
					for (int j = 0; j < affirmerList.size(); j++) {
						LinkedHashMap affirmerMap = (LinkedHashMap) affirmerList.get(j);
						if (affirmerMap.get("AFFIRMOR_ID") != null) {
								if(affirmerMap.get("AFFIRMOR_ID")!=null && "1".equals(affirmerMap.get("AFFIRM_LEVEL").toString())){
									//只将流程第一步的决裁者取出来存到加班申请表
									map.put("CURRENT_AFFIRM_ID", affirmerMap.get("AFFIRMOR_ID"));
								}
						}
					}
				}	
				
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	private List encapsulationApplyNoListForBatch2(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("c1");
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
				String AFFIRM_FLAG = paramMap.get("AFFIRM_FLAG"+paramData[i]) != null ? paramMap.get("AFFIRM_FLAG"+paramData[i]).toString():"14014306";
				map.put("AFFIRM_FLAG", AFFIRM_FLAG);
				map.put("APPLY_NO", paramData[i]);
				map.put("UPDATED_IP", admin.getAdminIP());
				map.put("UPDATED_BY", admin.getPersonId());
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delOvertimeApply(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return infoApplyDao.delOvertimeApply(paramMap);
	}
	
	/**
	 * 删除导入的加班信息申请(delete overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delOvertimeApplyImport(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return infoApplyDao.delOvertimeApplyImport(paramMap);
	}
	
	/**
	 * 删除临时表中所有导入的加班信息申请(delete overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int cancelOvertimeApplyImport(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		try {
			// 批量封装加班申请数据并处理
			this.infoApplyDao.cancelOvertimeApplyImport(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 取消已审核通过的加班申请( cancel overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean cancelOvertimeApply(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMIN_ID", admin.getPersonId());
		
		return infoApplyDao.cancelOvertimeApply(paramMap);
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
	public List getOtApplyInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		if (UiUtil.getPageNum(request) > 0) {
			returnList = infoApplyDao.getOtApplyInfoList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = infoApplyDao.getOtApplyInfoList(paramMap);
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
	@SuppressWarnings("unchecked")
	@Override
	public int getOtApplyInfoListCnt(HttpServletRequest request) throws Exception {
		// 封装查询条件
		int cnt = 0;
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,"seach_");
		cnt = this.infoApplyDao.getOtApplyInfoListCnt(paramMap);
		
		return cnt;
	}
		
	/**
	 * 修改加班申请(update overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateBatchOtApply(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtUpdateList = new ArrayList();
		String otTimeType = ""; 
		otTimeType = request.getParameter("OT_TIME_TYPE");
		
		String[] paramData = request.getParameterValues("c1");
		for (int k = 0; k <paramData.length; k++) {
			String applyNo = paramData[k];
			LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
			paramMap.put("APPLY_NO", applyNo);
			if("L".equals(otTimeType)){
				if(paramMap.get(applyNo + "_APPLY_OT_DATE")==null){
					continue;
				}
			}else{
				if(paramMap.get(applyNo + "_FROM_DATE")==null){
					continue;
				}
			}
			String applyTypeNo = paramMap.get("APPLY_TYPE_NO")!=null?paramMap.get("APPLY_TYPE_NO").toString(): "31";
		    String applyType = paramMap.get("APPLY_TYPE") != null ? paramMap.get("APPLY_TYPE").toString(): "";
		    String affirmFlag = paramMap.get("AFFIRM_FLAG") != null ? paramMap.get("AFFIRM_FLAG").toString(): "";;
	      
		    String fromDate = paramMap.get(applyNo+"_FROM_DATE") != null ? paramMap.get(applyNo + "_FROM_DATE").toString(): "";
		    String toDate = "";
		    
		    if("P".equals(otTimeType)){
		    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		    		Calendar c = Calendar.getInstance();
		          Date date=null;
		          date = format.parse(fromDate);
		          c.setTime(date); 
		          int day=c.get(Calendar.DATE); 
		          c.set(Calendar.DATE,day+1); 
		          
		          toDate=format.format(c.getTime());
		        
		     }
		    String personId = paramMap.get(applyNo+"_PERSON_ID")!=null?paramMap.get(applyNo+"_PERSON_ID").toString(): "";
		    String otFromTime = paramMap.get(applyNo+"_OT_FROM_TIME")!=null?paramMap.get(applyNo+"_OT_FROM_TIME").toString(): "";
		    String otToTime = paramMap.get(applyNo+"_OT_TO_TIME")!=null?paramMap.get(applyNo+"_OT_TO_TIME").toString(): "";
	      
		    String applyOtDate = paramMap.get(applyNo+"_APPLY_OT_DATE")!=null?paramMap.get(applyNo+"_APPLY_OT_DATE").toString(): "";
		    String otApplyHour = paramMap.get(applyNo+"_OT_APPLY_HOUR")!=null?paramMap.get(applyNo+"_OT_APPLY_HOUR").toString(): "0";
		    String otApplyMinute = paramMap.get(applyNo+"_OT_APPLY_MINUTE")!=null?paramMap.get(applyNo+"_OT_APPLY_MINUTE").toString(): "0";
		    
		    String applyTypeCode = paramMap.get(applyNo+"_OT_APPLY_TYPE_CODE")!=null?paramMap.get(applyNo+"_OT_APPLY_TYPE_CODE").toString():"";
		    String adjustYn = paramMap.get(applyNo+"_ADJUST_YN")!=null?paramMap.get(applyNo+"_ADJUST_YN").toString(): "0";
		    String otPlaceType = paramMap.get(applyNo+"_OT_PLACE_TYPE")!=null?paramMap.get(applyNo+"_OT_PLACE_TYPE").toString(): "INSIDE";
		    String applyRemark = paramMap.get(applyNo+"_APPLY_REMARK")!=null?paramMap.get(applyNo + "_APPLY_REMARK").toString(): "";
		    
		    LinkedHashMap dateMap = new LinkedHashMap();
		    dateMap = this.getLinkedMapByRequest(request, dateMap);
		    dateMap.put("PERSON_ID", personId);
		    dateMap.put("APPLY_NO", applyNo);
		    dateMap.put("OT_TIME_TYPE", otTimeType);
		    dateMap.put("APPLY_TYPE", applyType);
		    dateMap.put("AFFIRM_FLAG", affirmFlag);
		    //时间点方式申请用开始时间的日期
		    if("P".equals(otTimeType)){
		    	dateMap.put("APPLY_OT_DATE", fromDate);
		    	//时间长度的方式需要获取页面的参数
		    }else if("L".equals(otTimeType)){
		    	dateMap.put("APPLY_OT_DATE", applyOtDate);
		    }
		    //此次进行的是update操作，验证时应用
	        dateMap.put("OPE_FLAG", "UPDATE_OPE");
		    dateMap.put("FROM_TIME", fromDate + " " + otFromTime+":"+"00");
		    dateMap.put("TO_TIME", toDate + " " + otToTime+":"+"00");
		    dateMap.put("OT_DEDUCT_TIME", "0");
		    dateMap.put("BEGIN_DAY_OFFSET", "0");

		    dateMap.put("OT_APPLY_HOUR", otApplyHour);
		    dateMap.put("OT_APPLY_MINUTE", otApplyMinute);
	      
		    dateMap.put("APPLY_TYPE_NO", applyTypeNo);
		    dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
	      	dateMap.put("ADJUST_YN", adjustYn);
	      	dateMap.put("OT_PLACE_TYPE", otPlaceType);
	      	//页面获取到的加班类型是基础，不是最终保存到数据库中的加班类型，需要组合得出（目前只允许周末可调休）
	      	//1.根据加班类型（32：平日加班；33：周末加班；34：法定假加班；）
	      	//2.是否调休
	      	//3.社内、社外
	      	//4.在ess_ot_code_mapping表中获取对应的加班类型
	    	String otTypeCode ="";
			if(applyTypeCode.equals("32")||applyTypeCode.equals("33")||applyTypeCode.equals("34")){
				 otTypeCode = ((LinkedHashMap)this.infoApplyDao.getOtTypeCodeByTurn(dateMap)).get("OT_TYPE_CODE").toString();

			}else{
				otTypeCode=applyTypeCode;
			}
		 
			//Ta法人如果是页面加班且跨天，则转成  周末夜班加班(付薪)
			/*if("33".equals(applyTypeCode)&&"SST".equals(admin.getCpnyId())&&"1".equals(endDayOffset)){
			otTypeCode="217871";
			}
			if("34".equals(applyTypeCode)&&"SST".equals(admin.getCpnyId())&&"1".equals(endDayOffset)){
			otTypeCode="217872";
			}*/ 	
			dateMap.put("OT_TYPE_CODE", otTypeCode);
	      	dateMap.put("APPLY_REMARK", applyRemark);
	      	dateMap.put("CREATED_BY", admin.getPersonId());
	      	dateMap.put("CREATED_IP", admin.getAdminIP());
	      	dateMap.put("UPDATED_BY", admin.getPersonId());
	      	dateMap.put("UPDATED_IP", admin.getAdminIP());
	      
	      	//批量申请的员工无法进行决裁者的自主添加、修改、所以只能从系统里获取
	      	//List affirmList = this.getAffirmorListByMap(paramMap);
	      	//批量修改员工加班申请信息时，不需要重新更新决裁者信息，从ess_affirm表中取出即可
	      	List affirmList = this.getAffirmorListByApplyNo(paramMap);
	      	dateMap.put("affirmList", affirmList);
	      	LinkedHashMap otMap = this.preAddOvertimeApply(dateMap, admin.getLanguage());
	      	batchOtUpdateList.add(otMap);
		}

		this.infoApplyDao.updateOvertimeApplyInBatch(batchOtUpdateList);
		
		return 1;
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
	public List getOtImportInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		//导入的数据需要统一提交、取消，不需要分页
		 if (UiUtil.getPageNum(request) > 0) {
			returnList = infoApplyDao.getOtImportInfoList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = infoApplyDao.getOtImportInfoList(paramMap);
		} 
		//returnList = infoApplyDao.getOtImportInfoList(paramMap);
		return returnList;
	}
	
	/**
	 * 导入加班申请--查看信息列表总数(get overtime import list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getOtImportInfoListCnt(HttpServletRequest request,String cntType) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 封装查询条件
		int cnt = 0;
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		if("ERROR".equals(cntType)){
			cnt = this.infoApplyDao.getOtImportInfoListErrorCnt(paramMap);
		}else{
			cnt = this.infoApplyDao.getOtImportInfoListCnt(paramMap);
		}
		
		return cnt;
	}
		

		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 通过request请求封装查询条件(get search conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequestForSearch(
			HttpServletRequest request, String flag) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				flag);
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
	 * 显示个人信息申请修改页面（View personal information apply）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getPersonInfo(HttpServletRequest request) throws Exception {
		Map param = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		param.put("PERSON_ID", admin.getPersonId());
		return personInfoDao.getEssPersonInfo(param);
	}

	/**
	 * 查询公司用于扣除时间的LIST(search ot deduct time list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtDeductTimeList(HttpServletRequest request) throws Exception {
		Map param = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		param.put("CPNY_ID", admin.getCpnyId());
		param.put("PERSON_ID", admin.getAdminID());
		
		return infoApplyDao.getOtDeductTimeList(param);
	}
	
	
	/**
	 * 方法说明（批量加班申请人员列表,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		//KEY=, GROUP_NO=, DEPT_NO=C025
		if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
			if (UiUtil.getPageNum(request) > 0) {
				returnList = infoApplyDao.getPersonList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				returnList = infoApplyDao.getPersonList(paramMap);
			}
		}
		}
		List list = new ArrayList();
		LinkedHashMap lMap = new LinkedHashMap();
		LinkedHashMap searchAnLeave = paramMap;
		LinkedHashMap searchSurpLeave = paramMap;
		for (int i = 0; i < returnList.size(); i++) {
			lMap = (LinkedHashMap) returnList.get(i);
			searchAnLeave.put("EMPID", lMap.get("EMPID"));
			searchAnLeave.put("LOCAL_NAME", lMap.get("LOCAL_NAME"));
			searchAnLeave.put("PERSON_ID", lMap.get("PERSON_ID"));

			Date date = new Date();
			SimpleDateFormat sb = new SimpleDateFormat("yyyyMM");
			int dateInt = Integer.parseInt(sb.format(date));
			String yearAndMonth = String.valueOf(dateInt);
			String year = yearAndMonth.substring(0, 4);
			
			//lMap.put("REST_ANNUAL_LEAVE", this.retrieveVacationEmpREST(searchAnLeave, admin));
			//2013-12-27 lufeng 修改
			//2014/08/11 其中函数报错，未知是否需要查询剩余年假，先注释了  by:wangqiang 
		/*	lMap.put("REST_ANNUAL_LEAVE", this.getVacationEmpREST(searchAnLeave, admin));
			lMap.put("REST_ANNUAL_LEAVEQN", this.retrieveVacationEmpRESTQN(searchAnLeave, admin));*/
			searchSurpLeave.put("YEAR_NONTH", yearAndMonth);
			searchSurpLeave.put("YEAR", year);
			searchSurpLeave.put("CPNY_ID", paramMap.get("CPNY_ID"));
			searchSurpLeave.put("PERSON_ID", paramMap.get("PERSON_ID"));
			//lMap.put("SUR_ADJUST_REST", this.infoApplyDao.getSurplusAdjustRest(searchSurpLeave));
			//2013-12-11 lufeng 修改
			//2014/08/11 其中函数报错，未知是否需要查询剩余年假，先注释了  by:wangqiang 
			/*lMap.put("SUR_ADJUST_REST", this.infoApplyDao.getAdjustRestNew(searchSurpLeave));*/
			
			list.add(lMap);
		}
		return list;
	}

	

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonListOt(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("deptNo",admin.getDeptNo());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("ADMIN_ID", admin.getPersonId());
		String empNameStr=request.getParameter("LOCAL_NAME");
		try {
			paramMap.put("LOCAL_NAME", java.net.URLDecoder.decode(empNameStr,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList =null ;
		}else{
			retrunList= infoApplyDao.getPersonListOt(paramMap) ;
		}
		return retrunList ;
	}
	/**
	 * 批量加班申请的人员列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPersonListCnt(HttpServletRequest request) throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
			if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
				return this.infoApplyDao.getPersonListCnt(paramMap);
			}
		}else{
			return 0;
		}
		return 0;
	}

	/**
	 * 个人信息申请(add personal information apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean addPersonalInfoApply(HttpServletRequest request)
			throws Exception {
		Map param = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		param.put("PERSON_ID", admin.getAdminID());
		param.put("CREATED_BY", admin.getAdminID());// admin.getPersonId()
		param.put("CELLPHONE", request.getParameter("personInfo_CELLPHONE"));//手机号码
		param.put("HOME_ADDRESS", request.getParameter("personInfo_HOME_ADDRESS"));//现住址
		param.put("EMPLOYEE_ENGLISH_NAME", request.getParameter("personInfo_ENGLISH_NAME"));//英文名称
		param.put("EMAIL", request.getParameter("personInfo_EMAIL"));//email
		// if(vladate(param))//验证
		if (infoApplyDao.addPersonalInfoApply(param))
			return true;
		return false;
	}

	/**
	 * 得到班次 (get person time shift)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpShift(HttpServletRequest request) throws Exception {
		Map param = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		param.put("CPNY_ID", admin.getCpnyId());
		param.put("PERSON_ID", admin.getAdminID());
		return infoApplyDao.getEmpShift(param);
	}

	/**
	 * 添加加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addOvertimeApplyOld(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// List list = new ArrayList();
		LinkedHashMap essParamMap = new LinkedHashMap();
		List distinctList = new ArrayList();
		Object paramValueObj = null;
		String ifNeedAffirm = "";
		String ifNeedConfirm = "";
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);

		paramMap.put("FROM_TIME", paramMap.get("FROM_DATE") + " "
				+ paramMap.get("FROM_TIME"));
		paramMap.put("TO_TIME", paramMap.get("TO_DATE") + " "
				+ paramMap.get("TO_TIME"));

		// if (paramMap.get("PERSON_ID") == null) {
		// throw new CommonException("加班申请的人员标识不能为空,请重新申请!");
		// }

		// 需要决裁
		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
		essParamMap.put("ESS_PARAM_NO", "4159");// 4159是否需要决裁开关的NO
		paramValueObj = this.infoApplyDao
				.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedAffirm = paramValueObj != null ? paramValueObj.toString() : "0";
		essParamMap.put("ESS_PARAM_NO", "4160");// 4160是否需要人事确认开关的NO
		paramValueObj = this.infoApplyDao
				.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedConfirm = paramValueObj != null ? paramValueObj.toString() : "0";

		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao
				.getPersonInfoByPersonId(paramMap);
		List affirmerList = this.getAffirmorList(request);

		if (affirmerList.size() == 0 && "1".equals(ifNeedAffirm)) {
			// 未给该员工设置决裁者时
			throw new CommonException(
					TipMessage.getTipMessage(
							"alert.message.ess.infoApply.notFor", request)
							+ personMap.get("LOCAL_NAME")
							+ TipMessage
									.getTipMessage(
											"alert.message.ess.infoApply.pleaseSetAffirmorFirst",
											request));
		} else {
			int count = 1;
			boolean flag = true;
			Map filterMap = new LinkedHashMap();
			for (int i = 0; i < affirmerList.size(); i++) {
				LinkedHashMap affirmerMap = (LinkedHashMap) affirmerList.get(i);
				if (affirmerMap.get("AFFIRMOR_ID") != null) {
					if (flag) {
						// 只将流程第一步的决裁者取出来存到加班申请表
						paramMap.put("CURRENT_AFFIRM_ID", affirmerMap
								.get("AFFIRMOR_ID"));
						flag = false;
					}
					filterMap.put(affirmerMap.get("AFFIRMOR_ID").toString(),
							affirmerMap);
				}
			}

			// 判断saveAffirmorList里边重复的决裁者，然后重新排序
			for (Iterator iterator = filterMap.values().iterator(); iterator
					.hasNext();) {
				LinkedHashMap temp = (LinkedHashMap) iterator.next();
				temp.put("AFFIRM_LEVEL", count);
				count++;
				distinctList.add(temp);
			}

			String otFromDate = paramMap.get("FROM_TIME") != null ? paramMap
					.get("FROM_TIME").toString() : "";
			String otToDate = paramMap.get("TO_TIME") != null ? paramMap.get(
					"TO_TIME").toString() : "";
			// 检查申请开始时间和结束时间,如果格式不对提示用户
			this.formatFromAndToDate(otFromDate, otToDate);
			if (otApplyCheck(paramMap, personMap, admin.getLanguage()))// 验证
			{
				if ("1".equals(ifNeedAffirm)) {
					paramMap.put("ACTIVITY", "0");
					infoApplyDao.addOvertimeApply(paramMap, distinctList);
				} else if ("0".equals(ifNeedAffirm)
						&& "1".equals(ifNeedConfirm)) {
					// 不需要决裁,但需要人事确认
					paramMap.put("ACTIVITY", "0");
					infoApplyDao.addOvertimeApply(paramMap, null);
				} else if ("0".equals(ifNeedAffirm)
						&& "0".equals(ifNeedConfirm)) {
					// 既不需要决裁也不需要人事确认
					throw new CommonException(
							TipMessage
									.getTipMessage(
											"alert.message.ess.infoApply.otApplyShouldAffirmOrConfirm",
											request));

				}
			}
		}
		return 1;
	}

	/**
	 * 添加加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addOvertimeApply2(HttpServletRequest request) throws Exception {
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
		List dateList = this.infoApplyDao.getOvertimeApplyAllDateList(paramMap);
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
			LinkedHashMap otMap = this.preAddOvertimeApply(dateMap, admin.getLanguage());

			batchOtApplyList.add(otMap);
		}
		this.infoApplyDao.addOvertimeApplyInBatch(batchOtApplyList);
		return 1;
	}

	/**
	 * 添加加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addBatchOvertimeApply(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		String[] paramData = request.getParameterValues("c1");
		String continueApply = request.getParameter("continueApply") != null ? request.getParameter("continueApply") : "";
		String cloumeCount=request.getParameter("cloumeCount")!=null ? request.getParameter("cloumeCount") : "";
		int j=-1;
		for (int k = 0; k <paramData.length; k++) {
			j=Integer.parseInt(paramData[k].split(",")[1]);
			String personId = paramData[k].split(",")[0];
			LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
			paramMap.put("PERSON_ID", personId);
			if(paramMap.get(personId + "_FROM_DATE_"+j)==null){
				continue;
			}
			
			String fromDate = paramMap.get(personId + "_FROM_DATE_"+j) != null ? 
					paramMap.get(personId + "_FROM_DATE_"+j).toString(): "";
			String toDate = paramMap.get(personId + "_TO_DATE_"+j) != null ? 
					paramMap.get(personId + "_TO_DATE_"+j).toString(): paramMap.get(personId + "_FROM_DATE").toString();
			String fromTimeHour = paramMap.get(personId + "_FROM_TIME_HOUR_"+j) != null ? 
					paramMap.get(personId + "_FROM_TIME_HOUR_"+j).toString(): "";
			String toTimeHour = paramMap.get(personId + "_TO_TIME_HOUR_"+j) != null ? 
					paramMap.get(personId + "_TO_TIME_HOUR_"+j).toString(): "";
			String fromTimeMinute = paramMap.get(personId + "_FROM_TIME_MINUTE_"+j) != null ? 
					paramMap.get(personId + "_FROM_TIME_MINUTE_"+j).toString(): "";
			String toTimeMinute = paramMap.get(personId + "_TO_TIME_MINUTE_"+j) != null ? 
					paramMap.get(personId + "_TO_TIME_MINUTE_"+j).toString(): "";
			String applyTypeCode = paramMap.get(personId + "_OT_APPLY_TYPE_CODE_"+j) != null ? 
					paramMap.get(personId + "_OT_APPLY_TYPE_CODE_"+j).toString() : "";
			String applyRemark = paramMap.get(personId + "_APPLY_REMARK_"+j) != null ? 
					paramMap.get(personId + "_APPLY_REMARK_"+j).toString(): "";
			String applyTypeNo = paramMap.get("APPLY_TYPE_NO") != null ? 
					paramMap.get("APPLY_TYPE_NO").toString(): "";
			//String ifForcedTypeChoice = paramMap.get(personId + "_ifForcedTypeChoice_"+j) != null ? 
				//	paramMap.get(personId + "_ifForcedTypeChoice_"+j).toString() : "";

			paramMap.put("TO_DATE_TIME", toDate);
			paramMap.put("FROM_DATE_TIME", fromDate);

			List dateList = this.infoApplyDao.getOvertimeApplyAllDateList(paramMap);
			//判断是否为连续申请
			if("1".equals(continueApply)){
				for (int i = 0; i < dateList.size(); i++) {
					LinkedHashMap dateMap = (LinkedHashMap) dateList.get(i);
					String fromDateTime = dateMap.get("FROM_DATE") != null ? 
							dateMap.get("FROM_DATE").toString(): "";
					@SuppressWarnings("unused")
					String dateTypeId = dateMap.get("DATETYPE_ID") != null ? 
							dateMap.get("DATETYPE_ID").toString(): "";
					// if("1440".equals(dateTypeId))
					// break;
					dateMap = this.getLinkedMapByRequest(request, dateMap);
					dateMap.put("PERSON_ID", personId);
					dateMap.put("APPLY_DATE", fromDateTime);
					dateMap.put("FROM_TIME", fromDateTime + " " + fromTimeHour+":"+fromTimeMinute);
					dateMap.put("TO_TIME", fromDateTime + " " + toTimeHour+":"+toTimeMinute);
					//dateMap.put("ifForcedTypeChoice", ifForcedTypeChoice);
					dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
					dateMap.put("APPLY_REMARK", applyRemark);
					dateMap.put("APPLY_TYPE_NO", applyTypeNo);
	
					LinkedHashMap otMap = this.preAddOvertimeApply(dateMap, admin.getLanguage());
					batchOtApplyList.add(otMap);
				}
			}else{
				if(dateList.size() > 0){
					LinkedHashMap dateMap = (LinkedHashMap) dateList.get(0);
					@SuppressWarnings("unused")
					String dateTypeId = dateMap.get("DATETYPE_ID") != null ? 
							dateMap.get("DATETYPE_ID").toString(): "";
					// if("1440".equals(dateTypeId))
					// break;
					dateMap = this.getLinkedMapByRequest(request, dateMap);
					dateMap.put("PERSON_ID", personId);
					dateMap.put("APPLY_DATE", fromDate);
					dateMap.put("FROM_TIME", fromDate + " " + fromTimeHour+":"+fromTimeMinute);
					dateMap.put("TO_TIME", toDate + " " + toTimeHour+":"+toTimeMinute);
					//dateMap.put("ifForcedTypeChoice", ifForcedTypeChoice);
					dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
					dateMap.put("APPLY_REMARK", applyRemark);
					dateMap.put("APPLY_TYPE_NO", applyTypeNo);
	
					LinkedHashMap otMap = this.preAddOvertimeApply(dateMap, admin.getLanguage());
					batchOtApplyList.add(otMap);
				}
			}
		}

		this.infoApplyDao.addOvertimeApplyInBatch(batchOtApplyList);
		return 1;
	}

	/**
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddOvertimeApply2(LinkedHashMap paramMap,
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
		paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedAffirm = paramValueObj != null ? paramValueObj.toString() : "0";
		essParamMap.put("ESS_PARAM_NO", "4160");
		paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedConfirm = paramValueObj != null ? paramValueObj.toString() : "0";
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
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
			if (otApplyCheck(paramMap, personMap, language)){
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
	 * 批量添加休假申请(batch add leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addBatchLeaveApply(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		String[] paramData = request.getParameterValues("c1");
		for (int j = 0; j < paramData.length; j++) {
			String personId = paramData[j];
			LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
			paramMap.put("PERSON_ID", personId);
			String fromTime = paramMap.get(personId + "_FROM_TIME") != null ? 
					paramMap.get(personId + "_FROM_TIME").toString(): "";
			String toTime = paramMap.get(personId + "_TO_TIME") != null ? 
					paramMap.get(personId + "_TO_TIME").toString(): "";
			String applyTypeCode = paramMap.get(personId+ "_LEAVE_APPLY_TYPE_CODE") != null ? 
					paramMap.get(personId + "_LEAVE_APPLY_TYPE_CODE").toString() : "";
			String leaveReason = paramMap.get(personId + "_LEAVE_REASON") != null ? 
					paramMap.get(personId + "_LEAVE_REASON").toString(): "";
			String applyTypeNo = paramMap.get("APPLY_TYPE_NO") != null ? 
					paramMap.get("APPLY_TYPE_NO").toString(): "";
			
			paramMap.put("LEAVE_FROM_TIME", fromTime);
			paramMap.put("LEAVE_TO_TIME", toTime);
			paramMap.put("LEAVE_APPLY_TYPE_CODE", applyTypeCode);
			paramMap.put("LEAVE_REASON", leaveReason);
			paramMap.put("APPLY_TYPE_NO", applyTypeNo);
			//休假验证
			LinkedHashMap leaveMap = this.preAddLeaveApply(paramMap, "1", admin.getLanguage());
			batchOtApplyList.add(leaveMap);
		}

		this.infoApplyDao.addLeaveApplyInBatch(batchOtApplyList);
		return 1;
	}

	/**
	 * 批量添加出差申请(batch add evection apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addBatchEvectionApply(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		String[] paramData = request.getParameterValues("c1");
		for (int j = 0; j < paramData.length; j++) {
			String personId = paramData[j];
			LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
			paramMap.put("PERSON_ID", personId);

			String fromTime = paramMap.get(personId + "_EVE_FROM_TIME") != null ? 
					paramMap.get(personId + "_EVE_FROM_TIME").toString(): "";
			String toTime = paramMap.get(personId + "_EVE_TO_TIME") != null ? 
					paramMap.get(personId + "_EVE_TO_TIME").toString(): "";
			String applyTypeCode = paramMap.get(personId+ "_EVE_APPLY_TYPE_CODE") != null ? 
					paramMap.get(personId + "_EVE_APPLY_TYPE_CODE").toString() : "";
			String leaveReason = paramMap.get(personId + "_LEAVE_REASON") != null ? 
					paramMap.get(personId + "_LEAVE_REASON").toString(): "";
			String applyTypeNo = paramMap.get("EVE_APPLY_TYPE_NO") != null ? 
					paramMap.get("EVE_APPLY_TYPE_NO").toString(): "";

			paramMap.put("EVE_FROM_TIME", fromTime);
			paramMap.put("EVE_TO_TIME", toTime);
			paramMap.put("EVE_APPLY_TYPE_CODE", applyTypeCode);
			paramMap.put("LEAVE_REASON", leaveReason);
			paramMap.put("EVE_APPLY_TYPE_NO", applyTypeNo);

			LinkedHashMap leaveMap = this.preAddLeaveApply(paramMap, "2", admin.getLanguage());
			batchOtApplyList.add(leaveMap);
		}

		this.infoApplyDao.addLeaveApplyInBatch(batchOtApplyList);
		return 1;
	}

	/**
	 * 批量添加外出申请(batch add egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addBatchEgressionApply(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		String[] paramData = request.getParameterValues("c1");
		for (int j = 0; j < paramData.length; j++) {
			String personId = paramData[j];
			LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
			paramMap.put("PERSON_ID", personId);
			paramMap.put("interLanguage", admin.getLanguage());

			String fromTime = paramMap.get(personId + "_EG_FROM_TIME") != null ? 
					paramMap.get(personId + "_EG_FROM_TIME").toString(): "";
			String toTime = paramMap.get(personId + "_EG_TO_TIME") != null ? 
					paramMap.get(personId + "_EG_TO_TIME").toString(): "";
			String applyTypeCode = paramMap.get(personId+ "_EG_APPLY_TYPE_CODE") != null ? 
					paramMap.get(personId + "_EG_APPLY_TYPE_CODE").toString() : "";
			String leaveReason = paramMap.get(personId + "_LEAVE_REASON") != null ? 
					paramMap.get(personId + "_LEAVE_REASON").toString(): "";
			String applyTypeNo = paramMap.get("EG_APPLY_TYPE_NO") != null ? 
					paramMap.get("EG_APPLY_TYPE_NO").toString(): "";

			paramMap.put("EG_FROM_TIME", fromTime);
			paramMap.put("EG_TO_TIME", toTime);
			paramMap.put("EG_APPLY_TYPE_CODE", applyTypeCode);
			paramMap.put("LEAVE_REASON", leaveReason);
			paramMap.put("EG_APPLY_TYPE_NO", applyTypeNo);

			LinkedHashMap leaveMap = this.preAddLeaveApply(paramMap, "3", admin.getLanguage());
			batchOtApplyList.add(leaveMap);
		}

		this.infoApplyDao.addLeaveApplyInBatch(batchOtApplyList);
		return 1;
	}

	/**
	 * 封装要插入的申请数据:休假/出差/外出共用(encapsulation the apply
	 * data:Leave,Evection,Egression shared)
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddLeaveApply(LinkedHashMap paramMap,
			String leaveType, String language) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
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
		paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedAffirm = paramValueObj != null ? paramValueObj.toString() : "0";
		essParamMap.put("ESS_PARAM_NO", "4160");
		paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedConfirm = paramValueObj != null ? paramValueObj.toString() : "0";

		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);

		String leaveFromDate = "";
		String leaveToDate = "";
		if ("1".equals(leaveType)) {
			leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
			leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
			paramMap.put("FROM_TIME", paramMap.get("LEAVE_FROM_TIME"));
			paramMap.put("TO_TIME", paramMap.get("LEAVE_TO_TIME"));
			paramMap.put("APPLY_TYPE_NO", paramMap.get("APPLY_TYPE_NO"));
			paramMap.put("APPLY_TYPE_CODE", paramMap.get("LEAVE_APPLY_TYPE_CODE"));
			paramMap.put("APPLY_DATE", paramMap.get("LEAVE_FROM_TIME").toString().substring(0, 10));
		} else if ("2".equals(leaveType)) {
			leaveFromDate = paramMap.get("EVE_FROM_TIME") != null ? paramMap.get("EVE_FROM_TIME").toString() : "";
			leaveToDate = paramMap.get("EVE_TO_TIME") != null ? paramMap.get("EVE_TO_TIME").toString() : "";
			paramMap.put("FROM_TIME", paramMap.get("EVE_FROM_TIME"));
			paramMap.put("TO_TIME", paramMap.get("EVE_TO_TIME"));
			paramMap.put("APPLY_TYPE_NO", paramMap.get("EVE_APPLY_TYPE_NO"));
			paramMap.put("APPLY_TYPE_CODE", paramMap.get("EVE_APPLY_TYPE_CODE"));
			paramMap.put("APPLY_DATE", paramMap.get("EVE_FROM_TIME").toString().substring(0, 10));
		} else {
			leaveFromDate = paramMap.get("EG_FROM_TIME") != null ? paramMap.get("EG_FROM_TIME").toString() : "";
			leaveToDate = paramMap.get("EG_TO_TIME") != null ? paramMap.get("EG_TO_TIME").toString() : "";
			paramMap.put("FROM_TIME", paramMap.get("EG_FROM_TIME"));
			paramMap.put("TO_TIME", paramMap.get("EG_TO_TIME"));
			paramMap.put("APPLY_TYPE_NO", paramMap.get("EG_APPLY_TYPE_NO"));
			paramMap.put("APPLY_TYPE_CODE", paramMap.get("EG_APPLY_TYPE_CODE"));
			paramMap.put("APPLY_DATE", paramMap.get("EG_FROM_TIME").toString().substring(0, 10));
		}
		List affirmerList = this.getAffirmorListByMap(paramMap);
		if (affirmerList.size() == 0 && "1".equals(ifNeedAffirm)) {
			// 未给该员工设置决裁者时
			throw new CommonException(
					TipMessage.getTipMessage("alert.message.ess.infoApply.notFor", language)
							+ personMap.get("LOCAL_NAME")
							+ TipMessage.getTipMessage("alert.message.ess.infoApply.pleaseSetAffirmorFirst",
											language));// alert.ess.approval.no_approver
		} else {
			int count = 1;
			boolean flag = true;
			Map filterMap = new LinkedHashMap();
			for (int i = 0; i < affirmerList.size(); i++) {
				LinkedHashMap affirmerMap = (LinkedHashMap) affirmerList.get(i);
				if (affirmerMap.get("AFFIRMOR_ID") != null) {
					if (flag) {
						// 只将流程第一步的决裁者取出来存到休假申请表
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
			// 检查申请开始时间和结束时间,如果格式不对提示用户
			this.formatFromAndToDate(leaveFromDate, leaveToDate);
			boolean passFlag = false;
			// "1"表示校验休假;其他校验出差和外出
			if ("1".equals(leaveType)) {
				passFlag = this.leaveApplyCheck(paramMap, personMap, language);
			} else {
				passFlag = this.otherLeaveApplyCheck(paramMap, personMap,language);
			}
			// otherLeaveApplyCheck
			// 验证
			if (passFlag){
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
							TipMessage.getTipMessage("alert.message.ess.infoApply.otApplyShouldAffirmOrConfirm",
											language));// alert.ess.approval.no_approver
				}
			} else {
				throw new CommonException(TipMessage.getTipMessage(
						"alert.message.ess.infoApply.checkHasNotThrough",
						language));// alert.ess.overtime.fail_before_start
			}
		}
		return returnMap;
	}

	/**
	 * 计算剩余年假(Calculate the remaining annual leave )
	 * 
	 * @param request
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List retrieveVacationEmpREST(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String personId = request.getParameter("PERSON_ID");
		if (personId == null || "".equals(personId)) {
			personId = admin.getAdminID();
		}

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + admin.getCpnyId());
		paramMap.put("ITEM_NO", "2637");
		paramMap.put("PERSON_ID", personId);
		paramMap.put("VAC_TP", "2637");
		paramMap.put("LEAVE_FROM_TIME", date);
		paramMap.put("CPNYID", admin.getCpnyId() );
		return this.infoApplyDao.retrieveVacationEmpREST(paramMap);
	}

	/**
	 * 计算剩余年假(Calculate the remaining annual leave )
	 * 
	 * @param request
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List retrieveVacationEmpREST(LinkedHashMap paramMap, AdminBean admin)
			throws Exception {
		String personId = paramMap.get("PERSON_ID") != null ? paramMap.get(
				"PERSON_ID").toString() : "";

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + admin.getCpnyId());
		paramMap.put("ITEM_NO", "2637");
		paramMap.put("PERSON_ID", personId);
		paramMap.put("VAC_TP", "2637");
		paramMap.put("LEAVE_FROM_TIME", date);
		return this.infoApplyDao.retrieveVacationEmpREST(paramMap);
	}

	/**
	 * 2013-12-27 lufeng 计算剩余年假 new(Calculate the remaining annual leave )
	 * 
	 * @param request
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getVacationEmpREST(LinkedHashMap paramMap, AdminBean admin)
			throws Exception {
		String personId = paramMap.get("PERSON_ID") != null ? paramMap.get("PERSON_ID").toString() : "";
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", personId);
		paramMap.put("APPLY_DATE", date);
		return this.infoApplyDao.getVacationEmpREST(paramMap);
	}
	
	/**
	 * 计算剩余年假(Calculate the remaining annual leave )
	 * 
	 * @param request
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer retrieveVacationEmpRESTQN(LinkedHashMap paramMap, AdminBean admin)
			throws Exception {
		String personId = paramMap.get("PERSON_ID") != null ? paramMap.get(
				"PERSON_ID").toString() : "";

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + admin.getCpnyId());
		paramMap.put("ITEM_NO", "2637");
		paramMap.put("PERSON_ID", personId);
		paramMap.put("VAC_TP", "1487");
		paramMap.put("LEAVE_FROM_TIME", date);
		return this.infoApplyDao.retrieveVacationEmpRESTQN(paramMap);
	}
	
	/**
	 * 计算剩余调休(Calculate the remaining adjust rest )
	 * 
	 * @param request
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getSurplusAdjustRest(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String personId = request.getParameter("PERSON_ID");
		if (personId == null || "".equals(personId)) {
			personId = admin.getAdminID();
		}

		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyyMM");
		int dateInt = Integer.parseInt(sb.format(date));
		String yearAndMonth = String.valueOf(dateInt);
		String year = yearAndMonth.substring(0, 3);
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + admin.getCpnyId());

		paramMap.put("YEAR_NONTH", yearAndMonth);
		paramMap.put("YEAR", year);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", personId);
		return this.infoApplyDao.getSurplusAdjustRest(paramMap);
	}

	/**
	 * 计算剩余调休 2013-06-20 lufeng 新增(Calculate the remaining adjust rest )
	 * 
	 * @param request
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getAdjustRestNew(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String personId = request.getParameter("PERSON_ID");
		if (personId == null || "".equals(personId)) {
			personId = admin.getAdminID();
		}

		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyyMM");
		int dateInt = Integer.parseInt(sb.format(date));
		String yearAndMonth = String.valueOf(dateInt);
		String year = yearAndMonth.substring(0, 3);
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + admin.getCpnyId());

		paramMap.put("YEAR_NONTH", yearAndMonth);
		paramMap.put("YEAR", year);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", personId);
		
		return this.infoApplyDao.getAdjustRestNew(paramMap);
	}
	
	/**
	 * 添加休假申请(add leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean addLeaveApply(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String fromtime = request.getParameter("LEAVE_FROM_TIME");
		String totime = request.getParameter("LEAVE_TO_TIME");
        String applyTypeCode = request.getParameter("LEAVE_APPLY_TYPE_CODE");//休假类型
        if(applyTypeCode.equals("123641")||applyTypeCode.equals("123642")){//123641 半假前 9:00~14:00 半假后 14:00~18:00
        	if(applyTypeCode.equals("123641")){
        		fromtime=fromtime+" 09:00:00";
            	totime=totime+" 14:00:00";
        	}else{
        		fromtime=fromtime+" 14:00:00";
            	totime=totime+" 18:00:00";
        	}
        }else{
        	fromtime=fromtime+" 09:00:00";
        	totime=totime+" 18:00:00";
        }
        
        String restAnnualLeaveJN = request.getParameter("restAnnualLeaveJN");
        String restAnnualLeaveQN = request.getParameter("restAnnualLeaveQN");
    	//喜丧日
    	String	LikeLeaveTime =	request.getParameter("LIKELEAVE_TO_TIME") != null ? request.getParameter("LIKELEAVE_TO_TIME").toString() : "" ;	
    	//出生日期
    	String dobtime=request.getParameter("DOB_TIME") != null ? request.getParameter("DOB_TIME").toString() : "" ;
    	//预产期
    	String expectedDate=request.getParameter("EXPECTED_DATE") != null ? request.getParameter("EXPECTED_DATE").toString() : "" ;
    	//配偶出生日期
    	String spouseBirth=request.getParameter("SPOUSE_BIRTH") != null ? request.getParameter("SPOUSE_BIRTH").toString() : "" ;
    	//附件  附件存放的URL
    	String  fileUrl=request.getParameter("PROVE_FILE_URL") != null ? "/resources/temp/provefile/"+request.getParameter("PROVE_FILE_URL").toString():"";
		//附件的名称
    	String  fileName=request.getParameter("PROVE_FILE_NAME") != null ? request.getParameter("PROVE_FILE_NAME").toString():"";
    	
    	
    	String date = new SimpleDateFormat("yyyy").format(new Date());//截取当前时间的年份
		String fromdate = fromtime.substring(0, 5);//截取申请日期的年份
		//------------日期类型转换----------------
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date ss = s.parse(fromtime);
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date ss1 = s1.parse(totime);
		
		//原休息日
		String wokrFromDay=request.getParameter("LEAVE_FROM_TIME") !=null ? request.getParameter("LEAVE_FROM_TIME").toString():"";
		String wokrFromDayStart=wokrFromDay+" 09:00:00";
    	//调休日
		String wokrToDay=request.getParameter("LEAVE_TO_TIME");
		String wokrToDayStart="";
		String wokrToDayEnd="";
		if(!wokrToDay.equals("")){
			 wokrToDayStart=wokrToDay.substring(0,10)+" 09:00:00";
			 wokrToDayEnd=wokrToDay.substring(0,10)+" 18:00:00";
		}
		//--------------比较----------------------
		long length = ss1.getTime()-ss.getTime();//时间差（毫秒）
		int result = (int) (length * 1.0 / (1000 * 60 * 60));//（转换成小时）
        if( admin.getCpnyId().equals("C04") && applyTypeCode.equals("26") ){
        	if( fromdate.equals(date+"-")  ){
        		int j = Integer.parseInt(restAnnualLeaveJN);
        		if( result > j){
        			throw new CommonException(
        			 TipMessage.getTipMessage("alert.message.ess.infoApply.nianjiashubugou",admin.getLanguage()));
        		}
        	}else{
        		int q = Integer.parseInt(restAnnualLeaveQN);
        		if( result > q){
        			throw new CommonException(
        			 TipMessage.getTipMessage("alert.message.ess.infoApply.qvniannianjiashubugou",admin.getLanguage()));
        		}
        	}
        }
		//-------------------------------------
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		paramMap.put("LIKELEAVE_TIME", LikeLeaveTime);
		paramMap.put("DOB_TIME", LikeLeaveTime);
		paramMap.put("EXPECTED_DATE", expectedDate);
		paramMap.put("SPOUSE_BIRTH", spouseBirth);
		if(!("/resources/temp/provefile/").equals(fileUrl)){
			paramMap.put("FILE_URL", fileUrl);
		}else{
			paramMap.put("FILE_URL", "");
		}
		
		paramMap.put("FILE_NAME", fileName);
		if(applyTypeCode.equals("123646")){
			paramMap.put("LEAVE_FROM_TIME", wokrToDayStart);
			paramMap.put("LEAVE_TO_TIME", wokrToDayEnd);
			paramMap.put("OLD_DAY", wokrFromDayStart);
        }else{
			paramMap.put("LEAVE_FROM_TIME", fromtime);
			paramMap.put("LEAVE_TO_TIME", totime);
        }
		//休假验证开始
		LinkedHashMap leaveMap = this.preAddLeaveApply(paramMap, "1", admin.getLanguage());
		
		infoApplyDao.addLeaveApply(leaveMap);
		
		return true;
	}

	/**
	 * 添加出差申请(add evection apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean addEvectionApply(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		String continueApply = request.getParameter("continueApply_ess0230") != null ? request.getParameter("continueApply_ess0230") : "";
		if("1".equals(continueApply)){
			List batchOtApplyList = new ArrayList();
			String fromDate=paramMap.get("EVE_FROM_TIME").toString().substring(0,10);
			String fromTime=paramMap.get("EVE_FROM_TIME").toString().substring(10,paramMap.get("EVE_FROM_TIME").toString().length());
			String toDate=paramMap.get("EVE_TO_TIME").toString().substring(0,10);
			String toTime=paramMap.get("EVE_TO_TIME").toString().substring(10,paramMap.get("EVE_TO_TIME").toString().length());
			paramMap.put("TO_DATE_TIME",toDate);
			paramMap.put("FROM_DATE_TIME",fromDate);
			
			List dateList = this.infoApplyDao.getOvertimeApplyAllDateList(paramMap);
			for(int i=0;i<dateList.size();i++){
				LinkedHashMap dateMap = (LinkedHashMap) dateList.get(i);
				String fromDateTime = dateMap.get("FROM_DATE") != null ? 
						dateMap.get("FROM_DATE").toString(): "";
				LinkedHashMap dateMapA=new LinkedHashMap();
				dateMapA.putAll(paramMap);
				if(i==0){
					dateMapA.put("EVE_FROM_TIME",fromDateTime+fromTime);
				}else{
					dateMapA.put("EVE_FROM_TIME",fromDateTime+" 09:00:00");
				}
				if((i+1)==dateList.size()){
					dateMapA.put("EVE_TO_TIME",fromDateTime+toTime);
				}else{
					dateMapA.put("EVE_TO_TIME",fromDateTime+" 18:00:00");
				}
				LinkedHashMap leaveMap = this.preAddLeaveApply(dateMapA, "2", admin
						.getLanguage());
				batchOtApplyList.add(leaveMap);
			}
			infoApplyDao.addEvectionApplyList(batchOtApplyList);
		}else{
			LinkedHashMap leaveMap = this.preAddLeaveApply(paramMap, "2", admin
					.getLanguage());
			infoApplyDao.addEvectionApply(leaveMap);
		}
		
		// paramMap.put("APPLY_TYPE", "1679");// 1679为出差申请
		return true;
	}

	/**
	 * 添加外出申请(add egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean addEgressionApply(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		LinkedHashMap leaveMap = this.preAddLeaveApply(paramMap, "3", admin
				.getLanguage());
		infoApplyDao.addEgressionApply(leaveMap);
		// paramMap.put("APPLY_TYPE", "15479");// 15479为外出申请
		return true;
	}

	

	

	/**
	 * 加班申请是否与已有加班申请冲突(overtime apply conflict with exist overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean otApplyConflictWithExsitOtApply(Map paraMap)throws Exception {
		String otFromDate = paraMap.get("FROM_TIME") != null ? paraMap.get("FROM_TIME").toString() : "";
		String otToDate = paraMap.get("TO_TIME") != null ? paraMap.get("TO_TIME").toString() : "";
		
		List list = this.infoApplyDao.getExistOtApplyDate(paraMap);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		LinkedHashMap paramMap = new LinkedHashMap();
		
		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			String applyOtFrom = paramMap.get("FROM_TIME") != null ? paramMap.get("FROM_TIME").toString() : sb.format(date);
			String applyOtTo = paramMap.get("TO_TIME") != null ? paramMap.get("TO_TIME").toString() : sb.format(date);
			//要申请的加班开始、结束时间
			GregorianCalendar startTime = DateUtil.ParseGregorianCalendar(otFromDate);
			GregorianCalendar endTime = DateUtil.ParseGregorianCalendar(otToDate);
			//原有的加班开始、结束时间
			GregorianCalendar applyFrom = DateUtil.ParseGregorianCalendar(applyOtFrom);
			GregorianCalendar applyTo = DateUtil.ParseGregorianCalendar(applyOtTo);
			
			if (DateUtil.DateCross(startTime, endTime, applyFrom,applyTo, "MILLISECOND") > 0) {
				return true;
			}
		}
		return false;

	}
	

	 
	
	/**
	 * 加班申请社外的验证是否申请了出差(overtime apply conflict with exist overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean otApplyCheckOut(Map paraMap)throws Exception {
		String APPLY_OT_DATE = paraMap.get("APPLY_OT_DATE") != null ? paraMap.get("APPLY_OT_DATE").toString() : "";

		List list = this.infoApplyDao.otApplyCheckOutCh(paraMap);
		if(list!=null&!"".equals(list)){
			if(list.size()>0){
				return	false;
			}
		}
		
		return true;

	}
	/**
	 * 加班申请社外的验证是否申请了出差(overtime apply conflict with exist overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean otApplyCheckOutCh(Map paraMap)throws Exception {
		String otFromDate = paraMap.get("FROM_TIME") != null ? paraMap.get("FROM_TIME").toString() : "";
		String otToDate = paraMap.get("TO_TIME") != null ? paraMap.get("TO_TIME").toString() : "";
		
		List list = this.infoApplyDao.otApplyCheckOutCh(paraMap);
		if(list!=null&!"".equals(list)){
			if(list.size()>0){
				return	false; //有數據
			}
		}
		
		return true;

	}
	/**
	 * 当天的加班申请是否已经有过--申请类型为时间长度
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean otApplyExsitByApplyDate(Map paraMap)throws Exception {
		List list = this.infoApplyDao.otApplyExsitByApplyDate(paraMap);
		if(list!=null&&!"".equals(list) && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 导入的加班申请是否已经有过--申请类型为时间长度L
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean otImportExsitByApplyDate(Map paraMap)throws Exception {
		List list = this.infoApplyDao.otImportExsitByApplyDate(paraMap);
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 休假/出差/外出申请是否与已有加班申请冲突(leave/evection/egression apply conflict with exist
	 * overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean leaveApplyConflictWithExsitOtApply(Map paramMap)
			throws Exception {
		List list = this.infoApplyDao.getExistOtApplyDate(paramMap);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		for (int i = 0; i < list.size(); i++) {
			paramMap = (Map) list.get(i);
			String leaveApplyFrom = paramMap.get("FROM_TIME") != null ? paramMap
					.get("FROM_TIME").toString()
					: sb.format(date);
			String leaveApplyTo = paramMap.get("TO_TIME") != null ? paramMap
					.get("TO_TIME").toString() : sb.format(date);
			GregorianCalendar applyFrom = DateUtil
					.ParseGregorianCalendar(leaveApplyFrom);
			GregorianCalendar applyTo = DateUtil
					.ParseGregorianCalendar(leaveApplyTo);
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
	private boolean otApplyConflictWithExsitLeaveApply(Map paraMap)throws Exception {
		String otFromDate = paraMap.get("FROM_TIME") != null ? paraMap.get("FROM_TIME").toString() : "";
		String otToDate = paraMap.get("TO_TIME") != null ? paraMap.get("TO_TIME").toString() : "";
		//要申请的加班开始、结束时间
		GregorianCalendar startTime = DateUtil.ParseGregorianCalendar(otFromDate);
		GregorianCalendar endTime = DateUtil.ParseGregorianCalendar(otToDate);
		
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List list = this.infoApplyDao.getExistLeaveDate(paraMap);
		LinkedHashMap paramMap = new LinkedHashMap();
		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			String applyLeaveFrom = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString(): sb.format(date);
			String applyLeaveTo = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : sb.format(date);
			
			GregorianCalendar leaveFrom = DateUtil.ParseGregorianCalendar(applyLeaveFrom);
			GregorianCalendar leaveTo = DateUtil.ParseGregorianCalendar(applyLeaveTo);
			if (DateUtil.DateCross(startTime, endTime, leaveFrom,leaveTo, "MILLISECOND") > 0) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * 休假/出差/外出申请是否与已有休假/出差/外出申请冲突(leave/evection/egression apply conflict with
	 * exist leave/evection/egression apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean leaveApplyConflictWithExistLeaveApply(Map paramMap) {
		try {
			Date date = new Date();
			SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			List list = this.infoApplyDao.getExistLeaveDate(paramMap);

			for (int i = 0; i < list.size(); i++) {
				paramMap = (Map) list.get(i);
				String leaveApplyFrom = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap
						.get("LEAVE_FROM_TIME").toString()
						: sb.format(date);
				String leaveApplyTo = paramMap.get("LEAVE_TO_TIME") != null ? paramMap
						.get("LEAVE_TO_TIME").toString()
						: sb.format(date);
				GregorianCalendar applyFrom = DateUtil
						.ParseGregorianCalendar(leaveApplyFrom);
				GregorianCalendar applyTo = DateUtil
						.ParseGregorianCalendar(leaveApplyTo);
				if (DateUtil.DateCross(this.startTime, this.endTime, applyFrom,
						applyTo, "MILLISECOND") > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 取得加班申请日期的班次时间(get overtime apply with shift)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean getOtApplyDateWithShift(Map paraMap) {
		try {
			String teshu = paraMap.get("TESHU_YN")!=null?paraMap.get("TESHU_YN").toString():"0";
			if(teshu.equals("1")){
				return false;
			}
			String otFromDate = paraMap.get("FROM_TIME") != null ? paraMap.get("FROM_TIME").toString() : "";
			String otToDate = paraMap.get("TO_TIME") != null ? paraMap.get("TO_TIME").toString() : "";
			//要申请的加班开始、结束时间
			GregorianCalendar startTime = DateUtil.ParseGregorianCalendar(otFromDate);
			GregorianCalendar endTime = DateUtil.ParseGregorianCalendar(otToDate);
			
			Date date = new Date();
			SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			List list = this.infoApplyDao.getOtApplyDateWithShift(paraMap);
			LinkedHashMap paramMap = new LinkedHashMap();
			for (int i = 0; i < list.size(); i++) {
				paramMap = (LinkedHashMap) list.get(i);
				String shiftFrom = paramMap.get("FROM_TIME") != null ? paramMap.get("FROM_TIME").toString() : sb.format(date);
				String shiftTo = paramMap.get("TO_TIME") != null ? paramMap.get("TO_TIME").toString() : sb.format(date);
				
				GregorianCalendar shiftFromDate = DateUtil.ParseGregorianCalendar(shiftFrom);
				GregorianCalendar shiftToDate = DateUtil.ParseGregorianCalendar(shiftTo);
				
				if (DateUtil.DateCross(startTime, endTime, shiftFromDate,shiftToDate, "MILLISECOND") > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 取得加班申请导入临时表里的其它加班申请(get overtime apply with import other)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean getOtApplyDateWithImportOther(Map paraMap) {
		try {
			String otFromDate = paraMap.get("FROM_TIME") != null ? paraMap.get("FROM_TIME").toString() : "";
			String otToDate = paraMap.get("TO_TIME") != null ? paraMap.get("TO_TIME").toString() : "";
			//要申请的加班开始、结束时间
			GregorianCalendar startTime = DateUtil.ParseGregorianCalendar(otFromDate);
			GregorianCalendar endTime = DateUtil.ParseGregorianCalendar(otToDate);
			
			Date date = new Date();
			SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			List list = this.infoApplyDao.getOtApplyDateWithImportOther(paraMap);
			LinkedHashMap paramMap = new LinkedHashMap();
			for (int i = 0; i < list.size(); i++) {
				paramMap = (LinkedHashMap) list.get(i);
				String otherOtFrom = paramMap.get("FROM_TIME") != null ? paramMap.get("FROM_TIME").toString() : sb.format(date);
				String otherOtTo = paramMap.get("TO_TIME") != null ? paramMap.get("TO_TIME").toString() : sb.format(date);
				
				GregorianCalendar otherFromDate = DateUtil.ParseGregorianCalendar(otherOtFrom);
				GregorianCalendar otherToDate = DateUtil.ParseGregorianCalendar(otherOtTo);
				
				if (DateUtil.DateCross(startTime, endTime, otherFromDate,otherToDate, "MILLISECOND") > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
/**
	 * 取得加班申请导入临时表里的其它加班申请(get overtime apply with import other)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean getOtApplyDateWithImportOtherWQ(LinkedHashMap  paraMap) {
		try {
		 String flag = "";
			flag  = this.infoApplyDao.getOtApplyDateWithImportOtherWQ(paraMap);
			if("F".equals(flag)){
			  return true;
			} 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 加班导入验证存储
	 * by:wangqiang
	 * create_date 2015/1/30
	 * @param paraMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean getOtApplyDateWithImportOtherAll(LinkedHashMap  paraMap) {
		try {
		 String flag = "";
			flag  = this.infoApplyDao.getOtApplyDateWithImportOtherAll(paraMap);
			if("F".equals(flag)){
			  return true;
			} 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 加班36小时验证
	  * by:wangqiang
	 * create_date 2015/2/11
	 * @param paraMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean getOtApplyConfirmCpny(LinkedHashMap  paraMap) {
		try {
		 String flag = "";
			flag  = this.infoApplyDao.getOtApplyConfirmCpny(paraMap);
			if("F".equals(flag)){
			  return true;
			} 
			 
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * 检查休假申请是否合格,并提示相应的信息(examine overtime apply if correct and give message)
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean leaveApplyCheck(Map paramMap, LinkedHashMap personMap,
			String language) throws Exception {
		String otFromDate = paramMap.get("FROM_TIME") != null ? paramMap.get("FROM_TIME").toString() : "";
		String otToDate = paramMap.get("TO_TIME") != null ? paramMap.get("TO_TIME").toString() : "";
		// 检查申请开始时间和结束时间,如果格式不对提示用户
		this.formatFromAndToDate(otFromDate, otToDate);
		// 开始结束时间检查
		if (this.applyStartEndTimeCheck()) {
			throw new CommonException(
					TipMessage.getTipMessage("alert.message.ess.infoApply.finishDateShouldLaterThanStartDate",
									language));// alert.ess.overtime.fail_before_start
		}
		// 休假时间与之前申请加班时间检查
		if (this.leaveApplyConflictWithExsitOtApply(paramMap)) {
			throw new CommonException(
					TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
							+ personMap.get("LOCAL_NAME")
							+ TipMessage.getTipMessage(
											"alert.message.ess.infoApply.leaveApplyDateConflictWithExsitOtApply",
											language));// alert.ess.overtime.fail_ot_request
		}
		// 休假时间与之前申请休假时间检查
		if (this.leaveApplyConflictWithExistLeaveApply(paramMap)) {
			throw new CommonException(
					TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
							+ personMap.get("LOCAL_NAME")
							+ TipMessage.getTipMessage(
											"alert.message.ess.infoApply.leaveApplyDateConflictWithExsitLeaveApply",
											language));
		}
		// 如果是年假申请,检查年假数 26 
		if ("26".equals(paramMap.get("APPLY_TYPE_CODE"))||"123641".equals(paramMap.get("APPLY_TYPE_CODE"))||"123642".equals(paramMap.get("APPLY_TYPE_CODE"))) {
			if (this.leaveApplyAnnualCheck(paramMap)) {
				throw new CommonException(
						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
								+ personMap.get("LOCAL_NAME")
								+ TipMessage.getTipMessage(//剩余年假时数不够，故不能申请年假！
												"alert.message.ess.infoApply.noEnoughAnnualCannotApplyAnnualLeave",
												language));
			}
		}
		// 如果是调休申请,检查剩余调休时间长度
		//APPLY_TYPE_CODE  =123646  调休   以前是18135     取消现在的验证：99999999
		if ("99999999".equals(paramMap.get("APPLY_TYPE_CODE"))) {
			if (this.leaveApplyTiaoXiuCheck(paramMap)) {
				throw new CommonException(
						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
								+ personMap.get("LOCAL_NAME")
								+ TipMessage.getTipMessage(//剩余调休时数不够，故不能申请调休！
												"alert.message.ess.infoApply.noEnoughTiaoXiuCannotApplyAnnualLeave",
												language));
			}
		}
		
		LinkedHashMap essParamMap = new LinkedHashMap();
		Object paramValueObj = null;
		String paramValue = "";
		
		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
		// 加班申请是否在可以申请日期上限范围内
		essParamMap.put("ESS_PARAM_NO", "4165");// 4165有年假是否能申请事假 1为能申请;0为不能申请
		paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		paramValue = paramValueObj != null ? paramValueObj.toString() : "-1";
		// 有年假不能申请事假
		if ("0".equals(paramValue)) {
			// 如果有年假,则不能申请事假 15501为事假的国际化CODE
			if ("15501".equals(paramMap.get("APPLY_TYPE_CODE"))||"24".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				if (this.ifHaveAnnualCheck(paramMap)) {
					throw new CommonException(
							TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
									+ personMap.get("LOCAL_NAME")
									+ TipMessage.getTipMessage(
													"alert.message.ess.infoApply.haveAnnualCannotApplyPersonalLeave",
													language));// alert.ess.dayoff.fail_vacation_days
				}
			}
		}
		return true;
	}

	/**
	 * 检查外出/出差申请是否合格,并提示相应的信息(examine overtime apply if correct and give
	 * message)
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean otherLeaveApplyCheck(Map paramMap, LinkedHashMap personMap,
			String language) throws Exception {
		String otFromDate = paramMap.get("FROM_TIME") != null ? paramMap.get(
				"FROM_TIME").toString() : "";
		String otToDate = paramMap.get("TO_TIME") != null ? paramMap.get(
				"TO_TIME").toString() : "";
		// 检查申请开始时间和结束时间,如果格式不对提示用户
		this.formatFromAndToDate(otFromDate, otToDate);

		// 开始结束时间检查
		if (this.applyStartEndTimeCheck()) {
			throw new CommonException(
					TipMessage
							.getTipMessage(
									"alert.message.ess.infoApply.finishDateShouldLaterThanStartDate",
									language));
		}

		// 休假时间与之前申请加班时间检查
		if (this.leaveApplyConflictWithExsitOtApply(paramMap)) {
			throw new CommonException(
					TipMessage.getTipMessage(
							"alert.message.ess.infoApply.failedPerson",
							language)
							+ personMap.get("LOCAL_NAME")
							+ TipMessage
									.getTipMessage(
											"alert.message.ess.infoApply.applyDateConflictWithExsitOtApply",
											language));
		}

		// 休假时间与之前申请休假时间检查
		if (this.leaveApplyConflictWithExistLeaveApply(paramMap)) {
			throw new CommonException(
					TipMessage.getTipMessage(
							"alert.message.ess.infoApply.failedPerson",
							language)
							+ personMap.get("LOCAL_NAME")
							+ TipMessage
									.getTipMessage(
											"alert.message.ess.infoApply.applyDateConflictWithExsitLeaveApply",
											language));
		}
		return true;
	}

	/**
	 * 检查调休是否超过
	 * 
	 * @param essLeaveBean
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private boolean leaveApplyTiaoXiuCheck(Map paramMap) throws Exception {
		String leaveFromDate = paramMap.get("FROM_TIME") != null ? paramMap.get("FROM_TIME").toString() : "";
		String leaveToDate = paramMap.get("TO_TIME") != null ? paramMap.get("TO_TIME").toString() : "";
	
		double restTiaoXiu;
		restTiaoXiu = Double.parseDouble(this.infoApplyDao.getAdjustRestNew(paramMap).toString());
		
		String length = infoApplyDao.getLeaveApplyLength(paramMap);
		double leaveLength = Double.parseDouble(length);
		
		//如果申请调休时数大于剩余调休时数
		if ( restTiaoXiu < leaveLength ) {
			return true;
		}
		return false;
	}

	/**
	 * 检查年假是否超过
	 * 
	 * @param essLeaveBean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private boolean leaveApplyAnnualCheck(Map paramMap) throws Exception {
		String leaveFromDate = paramMap.get("FROM_TIME") != null ? paramMap.get("FROM_TIME").toString() : "";
		String leaveToDate = paramMap.get("TO_TIME") != null ? paramMap.get("TO_TIME").toString() : "";
		
		paramMap.put("empid", paramMap.get("EMPID"));
		paramMap.put("vac_id", leaveFromDate.substring(0, 4));
		paramMap.put("check", "1");
		paramMap.put("checkDate", paramMap.get("FROM_TIME"));
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + paramMap.get("CPNY_ID"));

		double restVac;
		restVac = Double.parseDouble(this.infoApplyDao.restVac(paramMap).toString());
		double applyingVac = Double.parseDouble(this.infoApplyDao.vacApplying(paramMap).toString());

		String length = infoApplyDao.getLeaveApplyLength(paramMap);
		double leaveLength = Double.parseDouble(length);
		//System.out.println("*************" + restVac + "**********" + applyingVac + "**********" + leaveLength);
		@SuppressWarnings("unused")
		GregorianCalendar applyTo = DateUtil.ParseGregorianCalendar(leaveToDate);

		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.set(Integer.parseInt(leaveToDate.substring(0, 4)), 3, 1, 0, 0, 0);

		if (restVac < (leaveLength + applyingVac)) {
			return true;
		}
		if(restVac + leaveLength + applyingVac==0){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查是否还有年假
	 * 
	 * @param essLeaveBean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private boolean ifHaveAnnualCheck(Map paramMap) throws Exception {
		String leaveFromDate = paramMap.get("FROM_TIME") != null ? paramMap
				.get("FROM_TIME").toString() : "";
		@SuppressWarnings("unused")
		String leaveToDate = paramMap.get("TO_TIME") != null ? paramMap.get(
				"TO_TIME").toString() : "";

		paramMap.put("empid", paramMap.get("EMPID"));
		paramMap.put("VAC_ID", leaveFromDate.substring(0, 4));

		paramMap.put("check", "1");
		paramMap.put("checkDate", paramMap.get("FROM_TIME"));
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + paramMap.get("CPNY_ID"));

		double restVac;
		restVac = Double.parseDouble(this.infoApplyDao.restVac(paramMap)
				.toString());

		double applyingVac = Double.parseDouble(this.infoApplyDao.vacApplying(
				paramMap).toString());

		if (restVac +applyingVac== 0) {//总年假天数为0直接 可以申请事假
			return false;
		}
		
		if (restVac-applyingVac> 0) {//剩余年假 减去申请中的年假数 大于0说明还有没修完的年假
			return true;
		}
		if (restVac <= applyingVac) {
			return true;
		}
		
		
		return false;
	}

	/**
	 * 申请始末时间是否正常
	 * 
	 * @param essLeaveBean
	 */
	private boolean applyStartEndTimeCheck() {
		if (startTime.after(endTime) || startTime.equals(endTime)) {
			return true;
		}
		return false;
	}

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
	
	/**
	 * 计算去年剩余 年假
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer retrieveVacationEmpRESTQN(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String personId = request.getParameter("PERSON_ID");
		if (personId == null || "".equals(personId)) {
			personId = admin.getAdminID();
		}
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + admin.getCpnyId());
		paramMap.put("ITEM_NO", "2637");
		paramMap.put("PERSON_ID", personId);
		paramMap.put("VAC_TP", "1487");
		paramMap.put("LEAVE_FROM_TIME", date);
		paramMap.put("CPNYID", admin.getCpnyId() );
		return this.infoApplyDao.retrieveVacationEmpRESTQN(paramMap);
	}

	/**
	 * 查询是否有个人的配偶出生日期
	 */
	@Override
	public String getspouseBirth(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String personId = request.getParameter("PERSON_ID");
		if (personId == null || "".equals(personId)) {
			personId = admin.getAdminID();
		}
		paramMap.put("PERSON_ID", personId);
		
		return this.infoApplyDao.getspouseBirth(paramMap);
	}

	@Override
	public String getviewVacationStandard(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String  returnString="";
		returnString=this.infoApplyDao.getviewVacationStandard(paramMap);
		/*if(!("".equals(returnString))){
			returnString=returnString.replace("<br>","\n");
		}*/
		return returnString;
	}
	
	@Override
	public String getLeaveTypeCode(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("LEAVE_TYPE_CODE", paramMap.get("applyTypeCode"));
		String  returnString="";
		returnString=this.infoApplyDao.getLeaveTypeCode(paramMap);
		return returnString;
	}
	
	@Override
	public String getOtTypeCode(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("OT_TYPE_CODE", paramMap.get("applyTypeCode"));
		String  returnString="";
		returnString=this.infoApplyDao.getOtTypeCode(paramMap);
		return returnString;
	}
	
	
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
	public List getAffirmorListByString2(String applyTypeNo , String personId , String applyTypeCode , String applyLength ) throws Exception {
		LinkedHashMap paramMap = new LinkedHashMap();
		if(applyTypeCode == null || "".equals(applyTypeCode)){
			applyTypeCode = applyTypeNo;
			applyLength = "0";
		}
		paramMap.put("APPLY_TYPE_NO", applyTypeNo);
		paramMap.put("APPLY_TYPE_CODE", applyTypeCode);
		paramMap.put("APPLY_LENGTH", applyLength);
		paramMap.put("PERSON_ID", personId);
		paramMap.put("interLanguage", "zh");
		//先取得裁决者的长度
		if(applyTypeCode!=null&&!applyTypeCode.equals("")&&applyLength!=null&&!applyLength.equals("")){
			LinkedHashMap affirmMap = new LinkedHashMap(); 
			affirmMap.put("APPLY_TYPE_CODE", applyTypeCode);
			affirmMap.put("APPLY_LENGTH", applyLength);
			affirmMap.put("PERSON_ID", personId);
			String affirmInfo = this.infoApplyDao.getLeaveAffirmLevel(affirmMap);
			if(affirmInfo!= null && !",,".equals(affirmInfo)){
				String[] affirmParam = affirmInfo.split(",");
				paramMap.put("AFFIRM_LENGTH", affirmParam[0]);
				paramMap.put("LOW_LEVEL", affirmParam[1]);
				paramMap.put("HIGH_LEVEL", affirmParam[2]);  
			}
		}
		Map objectMap = new LinkedHashMap();
		objectMap.put("PERSON_ID", paramMap.get("PERSON_ID"));
		Map objectPerson = (Map) this.infoApplyDao.getPersonInfoByPersonId(objectMap);
		if(objectPerson!=null){
			paramMap.put("DEPT_OBJECT", objectPerson.get("DEPT_NO"));
		}
		//判断是否有特殊决裁设置
		int isHasSpecialAffirm = this.infoApplyDao.isHasPersonAffirmSpecial(paramMap);
		//如果有特殊决裁设置就取特殊决裁设置  否则取TB里面的设置
		if(isHasSpecialAffirm > 0){
			int isHasSpecialPersonAffirm = this.infoApplyDao.isHasPersonAffirmSpecialPerson(paramMap);
			if(isHasSpecialPersonAffirm > 0){
				//取根据个人设置的特殊厥裁人
				List<LinkedHashMap> specialPersonList = this.infoApplyDao.getSpecialAffirmorListByPersonIdNew(paramMap);
				return specialPersonList;
			}else{
				//取根据部门设置的特殊决裁人
				List<LinkedHashMap> specialDeptList = this.infoApplyDao.getSpecialAffirmorListByDeptNoNew(paramMap);
				return specialDeptList;
			}
		}else{
		    int isHasPersonAffirm = this.infoApplyDao.isHasPersonAffirm(paramMap);
		    LinkedHashMap personMap = (LinkedHashMap)this.infoApplyDao.getPersonInfoByPersonId(paramMap);
		    paramMap.put("CPNY_ID",personMap.get("CPNY_ID"));
		    paramMap.put("DEPTNO",personMap.get("DEPT_NO"));
		    paramMap.put("BRANCH_NO",personMap.get("BRANCH_NO"));
		    if(isHasPersonAffirm > 0){
			    // 先取设置人员的决裁者(get approver by special-person's-approver setup first)(SY_TB)
			    List<LinkedHashMap> spePersonList = this.infoApplyDao.getAffirmorListByPersonIdNew(paramMap);
			    return spePersonList;
		    }else{
			    // 再取设置部门的决裁者(get approver by special-department's-approver setup second)(SY_TB)
			    List<LinkedHashMap> speDeptList = this.infoApplyDao.getAffirmorListByDeptNoNew(paramMap);			
			    return speDeptList;			
		    }
		}
	}
	

	/**
	 * 判断 促销员
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getPersonIdForAffirmList(String applyPersonId, String adminId, String cpnyId) {
		String returnPersonId = applyPersonId;
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("PERSON_ID", applyPersonId);
		if("TSTO".equals(cpnyId)){
			try {
				String empTypeCode = this.infoApplyDao.getEmpTypeCode(paramMap);
				if("211814".equals(empTypeCode)){
					returnPersonId = adminId;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnPersonId;
	}
	

	/**
	 * 判断 促销员
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getApplyTypeCodeForAffirmList(String applyPersonId, String cpnyId, String applyTypeCode) {
		String returnApplyTypeCode = applyTypeCode;
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("PERSON_ID", applyPersonId);
		if("TSTO".equals(cpnyId)){
			try {
				String empTypeCode = this.infoApplyDao.getEmpTypeCode(paramMap);
				if("211814".equals(empTypeCode)){
					returnApplyTypeCode = "14013535";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnApplyTypeCode;
	}
	
	/**
	 * 根据parent_code获取子code列表
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getCodeList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		List returnList = new ArrayList();
		try {
			returnList = this.infoApplyDao.getCodeList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 获取加班开始时间及增长间隔
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getApplyParam(HttpServletRequest request) throws Exception {
		Map param = ObjectBindUtil.getRequestParamData(request);
        AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	
		String APPLY_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		LinkedHashMap   shiftMap = new LinkedHashMap();
		
	
		shiftMap.put("CPNY_ID", admin.getCpnyId().toString());
		shiftMap.put("PERSON_ID", admin.getPersonId());
		shiftMap.put("APPLY_OT_DATE", APPLY_DATE);
		
		param.put("PERSON_ID",shiftMap.get("PERSON_ID") );
		param.put("AR_DATE_STR",shiftMap.get("APPLY_OT_DATE") );
		String SHIFT_NO = (String) infoApplyDao.getShiftNoForOt(shiftMap);
		param.put("SHIFT_NO", SHIFT_NO);

		LinkedHashMap data = (LinkedHashMap)this.infoApplyDao.getPersonalInfoForApplyShift(param);
			
		return data;
	}

	/**
	 * 员工班次结束时间查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public String getShiftEndTime(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String returnStr = "";
	 
		try {
			LinkedHashMap data = this.getApplyParam(request);
			data.put("OT_DATE", paramMap.get("OT_DATE"));
			returnStr = StringUtil.checkNull(this.infoApplyDao.getShiftEndTime(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}

	@SuppressWarnings("unchecked")
	public List getAffirmorListByDept(String applyTypeNo , String deptNo , String personId , String applyTypeCode , String applyLength ) throws Exception {
		LinkedHashMap paramMap = new LinkedHashMap();
		if(applyTypeCode == null || "".equals(applyTypeCode)){
			applyTypeCode = applyTypeNo;
			applyLength = "0";
		}
		paramMap.put("APPLY_TYPE_NO", applyTypeNo);
		paramMap.put("APPLY_TYPE_CODE", applyTypeCode);
		paramMap.put("APPLY_LENGTH", applyLength);
		paramMap.put("DEPT_NO", deptNo);
		paramMap.put("PERSON_ID", personId);
		paramMap.put("interLanguage", "zh");
		paramMap.put("CPNY_ID", "zh");
		//先取得裁决者的长度
		if(applyTypeCode!=null&&!applyTypeCode.equals("")&&applyLength!=null&&!applyLength.equals("")){
			LinkedHashMap affirmMap = new LinkedHashMap(); 
			affirmMap.put("APPLY_TYPE_CODE", applyTypeCode);
			affirmMap.put("APPLY_LENGTH", applyLength);
			affirmMap.put("PERSON_ID", personId);
			String affirmInfo = this.infoApplyDao.getLeaveAffirmLevel(affirmMap);
			if(affirmInfo!= null && !",,".equals(affirmInfo)){
				String[] affirmParam = affirmInfo.split(",");
				paramMap.put("AFFIRM_LENGTH", affirmParam[0]);
				paramMap.put("LOW_LEVEL", affirmParam[1]);
				paramMap.put("HIGH_LEVEL", affirmParam[2]);  
			}
		}		
		List<LinkedHashMap> speDeptList = this.infoApplyDao.getAffirmorListByDept(paramMap);			
		return speDeptList;	
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
	public int modifyAffirmorForBatch(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		
		//页面添加的决裁者列表
		List affirmList = new ArrayList();
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		if(affirmId == null || affirmId.length == 0){
			// 未给该员工设置决裁者时
			throw new CommonException("请先设置决裁者");// alert.ess.approval.no_approver
		}
		//添加决裁者
		int affirmLevel = 1;
		for(int i=0; i<affirmId.length; i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			if(affirmId[i] != null && !"".equals(affirmId[i])){
				affirmMap.put("AFFIRMOR_ID", affirmId[i]);
				affirmMap.put("AFFIRM_LEVEL", affirmLevel++ );
				affirmList.add(affirmMap);
			}
		}
		paramMap.put("affirmList", affirmList);
		this.infoApplyDao.modifyAffirmorForBatch(paramMap);
		if("1".equals(request.getParameter("yuty")) || request.getParameter("yuty")=="1"){
			if(!"".equals(request.getParameter("yutyNo"))){
				paramMap.put("EVENT_ID",request.getParameter("yutyNo"));
			}
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String FILE_URL =  StringUtil.checkNull(paramMap.get("fileUrl"));
			String FILE_NAME = StringUtil.checkNull(paramMap.get("fileName"));
			paramMap.put("FILE_NAME", FILE_NAME);
			paramMap.put("FILE_URL", FILE_URL);
			paramMap.put("APPLY_TYPE", "218064");
			paramMap.put("PERSON_ID_FILE", admin.getPersonId());
			this.paTempSalesDAO.updateYuTy(paramMap);
		}
		return 1;
	}
	
	/**
	 * 加班申请页面时间显示  取时间
	 * @param obj
	 * @return object
	 */
	public List getOtTimeByCpnyId(HttpServletRequest request,String timeFlag){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(!"".equals(timeFlag)){
			paramMap.put("TIME_FLAG", timeFlag);
		}
		List returnList = new ArrayList();
		try {
			returnList = this.infoApplyDao.getOtTimeByCpnyId(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
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
	public List getAffirmorListByString(String applyTypeNo , String personId , String applyTypeCode , String applyLength ,String language ) throws Exception {
		LinkedHashMap paramMap = new LinkedHashMap();
		if(applyTypeCode == null || "".equals(applyTypeCode)){
			applyTypeCode = "18135";
			applyLength = "0";
		}
		paramMap.put("APPLY_TYPE_NO", applyTypeNo);
		paramMap.put("APPLY_TYPE_CODE", applyTypeCode);
		paramMap.put("APPLY_LENGTH", applyLength);
		paramMap.put("PERSON_ID", personId);
		paramMap.put("LANGUAGE", language);
		String sqlStr = this.infoApplyDao.getAffirmorlistByPersonIdStr(paramMap);
		paramMap.put("sqlStr", sqlStr);
		List<LinkedHashMap> sqlList = this.infoApplyDao.getAffirmorlistByPersonIdList(paramMap);			
		return sqlList;			
	}
	
	/**
	 * 获取当月漏刷卡次数
	 * @param request
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public int arGetMacApplyCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		if(paramMap.get("APPLY_DATE")==null){
			paramMap.put("APPLY_DATE",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		try {
			return infoApplyDao.arGetMacApplyCnt(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
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
	public List getEssAbsenteeismTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = infoApplyDao.getEssAbsenteeismTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = infoApplyDao.getEssAbsenteeismTempList(paramMap);
		}
		return retrunList;
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
	public int getEssAbsenteeismTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = infoApplyDao.getEssAbsenteeismTempErrorCnt(paramMap);
		}else{
			retrunInt = infoApplyDao.getEssAbsenteeismTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	/**
	 * 休假旷工申请excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelEssAbsenteeismEmpData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_AR_CWA_EXCEL_IMP.PR_IMPORT_AR_CWA_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	

	/**
	 * 批量删除旷工申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delArCwaApplyInBatchForBatch(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap personMap = new LinkedHashMap();
		personMap.put("APPLY_PERSON", admin.getPersonId());
		String op_flag = request.getParameter("OP_FLAG");
		try {
			List list = this.encapsulationApplyNoListForArCwa(request);
			if("0".equals(op_flag)){//删除
				this.infoApplyDao.delArCwaApplyInfo(list);
			}else{//提交
				this.infoApplyDao.submitArCwaApplyInBatch(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	private List encapsulationApplyNoListForArCwa(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("BATCH_CWA_BATCH");
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("APPLY_NO", paramData[i]);
				map.put("APPLY_TYPE", "218197");
				
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * 旷工批量申请详细信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getArCwaBatchAffirmInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("APPLY_NO",  request.getParameter("APPLY_NO"));
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("LANGUAGE",  request.getParameter("LANGUAGE"));
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		if (UiUtil.getPageNum(request) > 0) {
			returnList = infoApplyDao.getEssArCwaList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = infoApplyDao.getEssArCwaList(paramMap);
		}
		
		if(returnList != null && returnList.size() > 0){
			for(int i=0;i<returnList.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)returnList.get(i);
				returnMap.put("APPLY_TYPE", "218197");
				returnMap.put("APPLY_NO", returnMap.get("APPLY_NO"));
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		
		return returnList;
	}

	/**
	 * 旷工批量申请详细信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getArCwaBatchAffirmInfoCnt(HttpServletRequest request){
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();

		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("LANGUAGE",  request.getParameter("LANGUAGE"));
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		try {
			retrunInt = infoApplyDao.getEssArCwaCnt(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retrunInt;
	}

	@Override
	public int getExceptionInt(HttpServletRequest request) throws Exception {
		int retrunInt = 0;
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		
		paramMap.put("EMPID",  request.getParameter("EMPID"));
		try {
			retrunInt = infoApplyDao.getExceptionInt(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  retrunInt;
	}

	@Override
	public void updateException(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		paramMap.put("EMPID",request.getParameter("EMPID"));
		//infoApplyDao.updateException(paramMap);
	}

	/**
	 * 获取班次
	 */
	@SuppressWarnings("unchecked")
	public Map getPersonIdShift(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("APPLY_OT_DATE",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}else {
			paramMap.put("APPLY_OT_DATE",paramMap.get("fromDate"));
		}
		paramMap.put("APPLY_OT_DATE",paramMap.get("fromDate")==null? new SimpleDateFormat("yyyy-MM-dd").format(new Date()):paramMap.get("fromDate"));
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		return infoApplyDao.getPersonIdShift(paramMap);
	}
	/**
	 * 获取工作时间字符串
	 */
	@SuppressWarnings("unchecked")
	public String getPersonIdWorkTime(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("APPLY_OT_DATE",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}else {
			paramMap.put("APPLY_OT_DATE",paramMap.get("fromDate"));
		}
		if(paramMap.get("fromDate")==null||"".equals(paramMap.get("fromDate"))){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return infoApplyDao.getPersonIdWorkTime(paramMap);
	}
	
	
	@Override
	public String getGradeYn(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		String str = "";
		try {
			str = infoApplyDao.getGradeYn(map) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	/**
	 * 部门员工加班查询信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDeptOtInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy"); 
		Calendar c = Calendar.getInstance();    
		if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
			//获取当前月第一天：
			c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());
			paramMap.put("FROM_DATE",first);
			//获取当前月最后一天：
			c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
			String last = format.format(c.getTime());
			paramMap.put("TO_DATE",last);
		}
			
		returnList = infoApplyDao.getDeptOtInfoList(paramMap);
		
		return returnList;
	}
	
	/**
	 * 加班信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCoordOtInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("PERSON_ID001",admin.getAdminID());
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
			returnList = infoApplyDao.getCoordOtInfoList(paramMap);
		return returnList;
	}
	/**
	 * 倒休信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCoordAdjustInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
//		paramMap.put("KEY",paramMap.get("dwz.person.empName"));
		paramMap.put("PERSON_ID001",admin.getAdminID());
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		returnList = infoApplyDao.getCoordAdjustInfoList(paramMap);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getOverTimeLimtList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("KEY",paramMap.get("dwz.person.empName"));
		paramMap.put("PERSON_ID",admin.getAdminID());
		
	    paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		
	
		returnList = infoApplyDao.getOverTimeLimtList(paramMap);
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewPersonOverTimeLimtList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		
		paramMap.put("PERSON_ID001",admin.getAdminID());
		
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		
		
		returnList = infoApplyDao.viewPersonOverTimeLimtList(paramMap);
		return returnList;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List getOverTimeLimtShenPiList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("KEY",paramMap.get("dwz.person.empName"));
		paramMap.put("PERSON_ID",admin.getAdminID());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		returnList = infoApplyDao.getOverTimeLimtShenPiList(paramMap);
		return returnList;
	}
	
	public int approveOtLimitBatch(HttpServletRequest request)
	throws Exception {
		try {
			// 批量封装
			this.infoApplyDao.approveOtLimitBatch(this
					.encapsulationApplyAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	public int approveOtLimitBatchHUB(HttpServletRequest request)
	throws Exception {
		try {
			// 批量封装
			this.infoApplyDao.approveOtLimitBatchHUB(this
					.encapsulationApplyAffirmListForBatchForHub(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	public int approveOtLimitBatchShenPi(HttpServletRequest request)
	throws Exception {
		try {
			// 批量封装
			this.infoApplyDao.approveOtLimitBatch(this
					.encapsulationApplyAffirmListForBatchShenPi(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	private List encapsulationApplyAffirmListForBatch(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String[] paramData = request.getParameterValues("c1");
			for (int i = 0; i < paramData.length; i++) {
				
				LinkedHashMap map = new LinkedHashMap();
				
				String AFFIRM_FLAG = (String) paramMap.get("AFFIRM_FLAG"+paramData[i]) != null ? (String) paramMap.get("AFFIRM_FLAG"+paramData[i]) : "";
				String ADJUST_RESON = (String) paramMap.get("ADJUST_RESON"+paramData[i])!=null ? (String) paramMap.get("ADJUST_RESON"+paramData[i]) : "";
				String FINAL_ADJUST_TIME = (String) paramMap.get("FINAL_ADJUST_TIME"+paramData[i])!=null ? (String) paramMap.get("FINAL_ADJUST_TIME"+paramData[i]) : "";
				map.put("AFFIRM_FLAG", AFFIRM_FLAG);
				map.put("ADJUST_RESON", ADJUST_RESON);
				map.put("OT_LIMIT_NO", paramData[i]);
				map.put("FINAL_ADJUST_TIME", FINAL_ADJUST_TIME);
				map.put("CREATED_BY", admin.getAdminID());
				map.put("CREATED_IP", admin.getAdminIP());
				map.put("UPDATED_IP",admin.getAdminIP());
				map.put("UPDATED_BY", admin.getAdminID());
				String navTabId=request.getParameter("navTabId")!=null?request.getParameter("navTabId"):"";
				map.put("navTabId", navTabId);
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private List encapsulationApplyAffirmListForBatchForHub(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String[] paramData = request.getParameterValues("ot_limit");
			for (int i = 0; i < paramData.length; i++) {
				
				LinkedHashMap map = new LinkedHashMap();
				
				String REMOVE_YN = (String) paramMap.get("REMOVE_YN"+paramData[i])!=null ? (String) paramMap.get("REMOVE_YN"+paramData[i]) : "1";
				//String FINAL_ADJUST_TIME = (String) paramMap.get("FINAL_ADJUST_TIME"+paramData[i])!=null ? (String) paramMap.get("FINAL_ADJUST_TIME"+paramData[i]) : "";
				String REMOVE_ADJUST_TIME = (String) paramMap.get("REMOVE_ADJUST_TIME"+paramData[i])!=null ? (String) paramMap.get("REMOVE_ADJUST_TIME"+paramData[i]) : "";
				//通过例外的时间判断是否例外
			    map.put("REMOVE_YN", REMOVE_YN);
				map.put("OT_LIMIT_NO", paramData[i]);
				map.put("REMOVE_ADJUST_TIME", REMOVE_ADJUST_TIME);
				map.put("CREATED_BY", admin.getAdminID());
				map.put("CREATED_IP", admin.getAdminIP());
				map.put("UPDATED_IP",admin.getAdminIP());
				map.put("UPDATED_BY", admin.getAdminID());
				String navTabId=request.getParameter("navTabId")!=null?request.getParameter("navTabId"):"";
				map.put("navTabId", navTabId);
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private List encapsulationApplyAffirmListForBatchShenPi(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String[] paramData = request.getParameterValues("c2");
			for (int i = 0; i < paramData.length; i++) {
				
				LinkedHashMap map = new LinkedHashMap();
				
				String AFFIRM_FLAG = (String) paramMap.get("AFFIRM_FLAG"+paramData[i]) != null ? (String) paramMap.get("AFFIRM_FLAG"+paramData[i]) : "";
				String ADJUST_RESON = (String) paramMap.get("ADJUST_RESON"+paramData[i])!=null ? (String) paramMap.get("ADJUST_RESON"+paramData[i]) : "";
				String FINAL_ADJUST_TIME = (String) paramMap.get("FINAL_ADJUST_TIME"+paramData[i])!=null ? (String) paramMap.get("FINAL_ADJUST_TIME"+paramData[i]) : "";
				map.put("AFFIRM_FLAG", AFFIRM_FLAG);
				map.put("ADJUST_RESON", ADJUST_RESON);
				map.put("OT_LIMIT_NO", paramData[i]);
				map.put("FINAL_ADJUST_TIME", FINAL_ADJUST_TIME);
				map.put("CREATED_BY", admin.getAdminID());
				map.put("CREATED_IP", admin.getAdminIP());
				map.put("UPDATED_IP",admin.getAdminIP());
				map.put("UPDATED_BY", admin.getAdminID());
				String navTabId=request.getParameter("navTabId")!=null?request.getParameter("navTabId"):"";
				map.put("navTabId", navTabId);
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	 
	/**
	 * SST加班申请 (add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addSSTOvertimeApply(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		
		String affirmJsonString = request.getParameter("affirmJsonData");
		List<LinkedHashMap<String, Object>> affirmList = ObjectBindUtil.getRequestJsonData(affirmJsonString);	
		
		if(affirmList == null || affirmList.size() == 0){
			// 未给该员工设置决裁者时
			throw new CommonException(TipMessage.getTipMessage("alert.message.pleaseFirstSetRuler.b", request));// 请先设置决裁者
		}
		/*List affirmList = new ArrayList(); 
		String[] affirmIdStart = request.getParameterValues("OT_AFFIRMOR_ID");
		String[] approvTypeIndex = request.getParameterValues("approvTypeIndex");
		if(affirmIdStart == null || affirmIdStart.length == 0){
			// 未给该员工设置决裁者时
			throw new CommonException(TipMessage.getTipMessage("alert.message.pleaseFirstSetRuler.b", request));// 清闲设置决裁者
		}
		List<String> affirmId=new ArrayList();
		List<String> approvTypeList=new ArrayList();
		for (int i = 0;i< affirmIdStart.length;i++) {
			String strings = affirmIdStart[i];
	    	if(!strings.equals("")){
	    		//if(admin.getAdminID().equals(strings)){
	    			//throw new CommonException("不能把自己设置为审判者");// alert.ess.approval.no_approver
	    		//}
	    		affirmId.add(strings);
	    		approvTypeList.add(request.getParameter("approvType" + approvTypeIndex[i]));
	    	}
	    }
		if(affirmId == null || affirmId.size()==0){
			throw new CommonException(TipMessage.getTipMessage("alert.message.pleaseFirstSetRuler.b", request));// "请先设置决裁者"
		}*/
		//添加决裁者 
		/*for(int i=0; i<affirmId.size(); i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			affirmMap.put("AFFIRMOR_ID", affirmId.get(i));
			affirmMap.put("AFFIRM_LEVEL", i+1);
			affirmMap.put("AFFIRM_TYPE", approvTypeList.get(i));
			affirmList.add(affirmMap);
		}*/
		paramMap.put("affirmList", affirmList);
		//插入数据库之前的数据的整理、验证，主要是决裁者与时间的验证，然后封装起来
		LinkedHashMap otMap = this.preAddOvertimeApplySST(paramMap);
		batchOtApplyList.add(otMap);
		
		this.infoApplyDao.addOvertimeApplySST(batchOtApplyList);
		
		return 1;
	}

	/**
	 * SST事后确认
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int modifySSTOvertimeApply(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		List affirmList = new ArrayList();
		String[] affirmIdStart = request.getParameterValues("AFFIRMOR_ID");
		String[] approvTypeIndex = request.getParameterValues("approvTypeIndex");
		if(affirmIdStart == null || affirmIdStart.length == 0){
			// 未给该员工设置决裁者时
			throw new CommonException("请先设置决裁者");// alert.ess.approval.no_approver
		}
		List<String> affirmId=new ArrayList();
		List<String> approvTypeList=new ArrayList();
		for (int i = 0;i< affirmIdStart.length;i++) {
			String strings = affirmIdStart[i];
	    	if(!strings.equals("")){
	    		if(admin.getAdminID().equals(strings)){
	    			throw new CommonException("不能把自己设置为审判者");// alert.ess.approval.no_approver
	    		}
	    		affirmId.add(strings);
	    		approvTypeList.add(request.getParameter("approvType" + approvTypeIndex[i]));
	    	}
	    }
		if(affirmId == null || affirmId.size()==0){
			throw new CommonException("请先设置决裁者");// alert.ess.approval.no_approver
		}
		//添加决裁者 
		for(int i=0; i<affirmId.size(); i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			affirmMap.put("AFFIRMOR_ID", affirmId.get(i));
			affirmMap.put("AFFIRM_LEVEL", i+1);
			affirmMap.put("AFFIRM_TYPE", approvTypeList.get(i));
			affirmList.add(affirmMap);
		}
		paramMap.put("affirmList", affirmList);
		//插入数据库之前的数据的整理、验证，主要是决裁者与时间的验证，然后封装起来
		LinkedHashMap otMap = this.preModifyOvertimeApplySST(paramMap);
		
		this.infoApplyDao.modifyOvertimeApplySST(otMap);
		
		return 1;
	}
	
	/**
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddOvertimeApplySST(LinkedHashMap paramMap) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		LinkedHashMap essParamMap = new LinkedHashMap();
		//List distinctList = new ArrayList();
		// 需要决裁
		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
		/*//从系统配置里获取决裁者
		//List affirmerList = this.getAffirmorListByMap(paramMap);
		//从系统配置、页面添加的决裁者组合以后得出的决裁者信息
		List affirmerList = (ArrayList<LinkedHashMap>)paramMap.get("affirmList");

		int count = 1;
		boolean flag = true;
		Map filterMap = new LinkedHashMap();
		for (int i = 0; i < affirmerList.size(); i++) {
			LinkedHashMap affirmerMap = (LinkedHashMap) affirmerList.get(i);
			if (affirmerMap.get("AFFIRMOR_ID") != null) {
				if (flag) {
					if(affirmerMap.get("AFFIRMOR_ID")!=null && "1".equals(affirmerMap.get("AFFIRM_LEVEL").toString())){
						//只将流程第一步的决裁者取出来存到加班申请表
						paramMap.put("CURRENT_AFFIRM_ID", affirmerMap.get("AFFIRMOR_ID"));
						flag = false;
					}
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
		}*/
		//各种验证（日期、时间、类型等）
		if (otApplyCheckSST(paramMap, personMap)){
			paramMap.put("ACTIVITY", "0");
			returnMap.put("PARAM_MAP", paramMap);
			returnMap.put("personMap", personMap);
		}
		return returnMap;
	}
	
	/**
	 * SST检查加班申请是否合格,并提示相应的信息(examine overtime apply if correct and give message)
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean otApplyCheckSST(LinkedHashMap paramMap,
			LinkedHashMap personMap) throws Exception {
		
		//加班时间验证
		String result = this.infoApplyDao.getOtCheckSST(paramMap);
		if ( !"OK".equals(result) ) {
			throw new CommonException( result);
		}
		return true;
	}
	

	/**
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preModifyOvertimeApplySST(LinkedHashMap paramMap) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		// 需要决裁
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);

		//各种验证（日期、时间、类型等）
		if (otApplyCheckSST2(paramMap, personMap)){
			paramMap.put("ACTIVITY", "0");
			returnMap.put("PARAM_MAP", paramMap);
			returnMap.put("personMap", personMap);
		}
		return returnMap;
	}

	/**
	 * SST检查加班申请是否合格,并提示相应的信息(examine overtime apply if correct and give message)
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean otApplyCheckSST2(LinkedHashMap paramMap,
			LinkedHashMap personMap) throws Exception {
		
		//加班时间验证
		String result = this.infoApplyDao.getOtCheckSST2(paramMap);
		if ( !"OK".equals(result) ) {
			throw new CommonException( result);
		}
		return true;
	}
	/**
	 * SST加班申请决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getSSTOtAffirmInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		
		returnList = infoApplyDao.getSSTOtAffirmInfoList(paramMap);
		
		return returnList;
	}
	
	/**
	 * 审批箱查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewApprovalInfo(HttpServletRequest request,String target) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		
		returnList = infoApplyDao.viewApprovalInfo(paramMap, target);
		
		return returnList;
	}
	

	@SuppressWarnings("unchecked")
	public String executePro(HttpServletRequest request,String target){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("DEDUCT_YN_OLD")!=null && paramMap.get("DEDUCT_YN_NEW")!= null && !paramMap.get("DEDUCT_YN_OLD").equals(paramMap.get("DEDUCT_YN_NEW"))){
			this.infoApplyDao.updateDeductYn(paramMap);
		}
		try {
			return this.infoApplyDao.executePro(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * 获取上次申请时的审批线
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorListByHistory(HttpServletRequest request,String applyTypeNo) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("APPLY_TYPE_CODE", applyTypeNo);
		return infoApplyDao.viewApprovalInfo(paramMap, "getAffirmListByHistory");			
	}
	
	@SuppressWarnings("unchecked")
	public String executeProBatch(HttpServletRequest request,String target){
		String result = "OK";
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			for(int i = 0;i<dataList.size();i++){
				Map paramMap = (Map) dataList.get(i);
				
				if(paramMap.get("DEDUCT_YN_OLD")!=null && paramMap.get("DEDUCT_YN_NEW")!= null && !paramMap.get("DEDUCT_YN_OLD").equals(paramMap.get("DEDUCT_YN_NEW"))){
					this.infoApplyDao.updateDeductYn(paramMap);
				}
				
				result = this.infoApplyDao.executePro(paramMap,target);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String executeProBatchByBatchNo(HttpServletRequest request,String target){
		String result = "OK";
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			for(int i = 0;i<dataList.size();i++){
				Map paramMap = (Map) dataList.get(i);
				List infoList = infoApplyDao.viewApprovalInfo(paramMap, "getBatchLeaveAffirmInfoList");
				if(infoList != null && infoList.size() != 0){
					for(int j=0;j<infoList.size();j++){
						Map infoParamMap = (Map) infoList.get(j);
						infoParamMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_FLAG"));
						infoParamMap.put("adminID", paramMap.get("adminID"));
						infoParamMap.put("adminIP", paramMap.get("adminIP"));
						infoParamMap.put("interCpnyID", paramMap.get("interCpnyID"));
						result = this.infoApplyDao.executePro(infoParamMap,target);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}
	
	/**
	 * 审批箱更新
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int viewModifyApprovalInfo(HttpServletRequest request,String target) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		
		infoApplyDao.viewModifyApprovalInfo(paramMap, target);
		
		return 1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public String getOTLimitList(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList,String flag) throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String name="";
		aliasNameList.add("社号(必填)");
		aliasNameList.add("月份");
		aliasNameList.add("例外小时数");
		
		if("load".equals(flag)){
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "20000013");
			map.put("CELL1", "201601");
			map.put("CELL2", "24");
			list.add(map);
			name="hub_ot_limit_demo";
		}
		return name;
	}
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public String getVacPlanList(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList,String flag) throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String name="";
		aliasNameList.add("社号(必填)");
		aliasNameList.add("年份");
		aliasNameList.add("1月");
		aliasNameList.add("2月");
		aliasNameList.add("3月");
		aliasNameList.add("4月");
		aliasNameList.add("5月");
		aliasNameList.add("6月");
		aliasNameList.add("7月");
		aliasNameList.add("8月");
		aliasNameList.add("9月");
		aliasNameList.add("10月");
		aliasNameList.add("11月");
		aliasNameList.add("12月");
		
		if("load".equals(flag)){
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "20000013");
			map.put("CELL1", "2016");
			map.put("CELL2", "1");
			
			map.put("CELL3", "1");
			map.put("CELL4", "1");
			map.put("CELL5", "0");
			map.put("CELL6", "0");
			map.put("CELL7", "1");
			map.put("CELL8", "0");
			map.put("CELL9", "1");
			map.put("CELL10", "0");
			map.put("CELL11", "0");
			map.put("CELL12", "1");
			map.put("CELL13", "0");
			list.add(map);
			name="hub_vac_plan_demo";
		}
		return name;
	}
	
	public String insertOTLimit(HttpServletRequest request){
		String retrunInt = "0" ;
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = infoApplyDao.insertOTLimit(paramMap) ;
		return retrunInt;
	}
	/**
	 * 
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtLimitDataImportResultList(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			retrunList =  infoApplyDao.getOtLimitDataImportResultList(paramMap);
		return retrunList;
	}
	/**
	 * 内务倒休管理的操作（增、删）
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int delAdjustApplyLeaveCoordForm(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap personMap = new LinkedHashMap();
		personMap.put("APPLY_PERSON", admin.getPersonId());
		String op_flag = request.getParameter("OP_FLAG")!=null?request.getParameter("OP_FLAG"):"0";
		List list =new ArrayList();
		List deleteList =new ArrayList();
		try {
			// 批量封装加班申请数据并处理
			if (!"2".equals(op_flag)&&!"0".equals(op_flag)) {
			     list = arDetailSerImp.encapsulationAdjustHolidayListForBatch(request);
			}
			if("0".equals(op_flag)){//删除
				deleteList = arDetailSerImp.encapsulationAdjustHolidayListForDelete(request);
				this.infoApplyDao.delOvertimeApplyInBatchAdjustTSTO(deleteList);//TSTO倒休数据的删除
			}else if("2".equals(op_flag)) {
				LinkedHashMap map = new LinkedHashMap();
				map.put("CPNY_ID",admin.getCpnyId());
				map.put("CREATED_BY",admin.getAdminID());
				map.put("CREATED_IP",admin.getAdminIP());
				map.put("applyBatchdate",request.getParameter("APPLY_DATE"));
				this.infoApplyLeaveDao.addtAdjustApplyInBatchTSTO(map);
			}else{//提交
				for(int i=0;i<list.size();i++){
					LinkedHashMap paramMap = (LinkedHashMap) list.get(i);
				    personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);
				    String AFFIRM_FLAG = (String) paramMap.get("AFFIRM_FLAG");
					boolean passFlag = false;
					passFlag = this.arCoordApplyCheckBatchAdjust(paramMap, personMap, admin.getLanguage());
					String PK_NO = (String) paramMap.get("APPLY_NO");
					paramMap.put("PK_NO", PK_NO);
					//SST用
					
					//获取数据库该条选中数据的决裁状态
					String oldAffrim = infoApplyLeaveDao.getChechedAffrim(paramMap) ;
					paramMap.put("oldAffrim", oldAffrim);
					
					//修改考勤的班次及时间
						if (!paramMap.get("OLD_SHIFT_NO").equals(paramMap.get("SHIFT_NO"))) {
							String kaoQinNo = "";
							   kaoQinNo = infoApplyLeaveDao.getkaoQinNo(paramMap);
							if ("141439".equals(kaoQinNo) || "141440".equals(kaoQinNo)) {
								String APPLY_NO_FOR_SHIFTNO = infoApplyLeaveDao.getkaoQinNoPkNo(paramMap);
								//通过加班修改班次，获取修改班次的开始结束时间
								LinkedHashMap flMap = new LinkedHashMap();
								flMap.put("CPNY_ID",admin.getCpnyId() );
								flMap.put("from_date", paramMap.get("APPLY_DATE"));
								flMap.put("SHIFT_NO", paramMap.get("SHIFT_NO"));
								LinkedHashMap timeMap  = infoApplyDao.getOtApplyWorkTime(flMap) ;
								paramMap.put("FIRST_FOR_SHIFTNO",timeMap.get("FIRST_TIME"));
								paramMap.put("LAST_FOR_SHIFTNO", timeMap.get("LAST_TIME"));
								//修改考勤的班次，开始结束时间
								paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
								this.infoApplyDao.updatArDetailBatchApplyForSHIFT(paramMap);
							}else {
								throw new CommonException(personMap.get("LOCAL_NAME")+ "已经进行了休假申请");
							}
							
						 }
					 //开始:14014306; 部门申请: 14014307;部门长批准 ：14014308;部门长返回:14014309;上申取消:14014310;部门申请(后):14014311;部门长批准(后):14014312
					if (passFlag==true) {
						if ("14014306".equals(oldAffrim)) {
							//如果做部门申请，初始状态就做插入
							this.infoApplyDao.addOtApplyInArDetailBatchAdjust(paramMap);
							this.infoApplyDao.updatSubmitArDetailBatchApplyForFirstAddAdjust(paramMap);//修改detail表
						} else {
							//部门长申请(后)
							if ("14014306".equals(AFFIRM_FLAG)) {
								//把原先的记录修改为初始
								String SHIFT_NO = (String) paramMap.get("SHIFT_NO");
								LinkedHashMap dataMap = new LinkedHashMap();
								dataMap.put("SHIFT_NO",SHIFT_NO);
								dataMap.put("FROM_DATE",paramMap.get("APPLY_DATE"));
								dataMap.put("CPNY_ID",admin.getCpnyId());
							    LinkedHashMap   shiftMap  = arDetailSerImp.getWorkTimeFirstLast(dataMap);  
								
								paramMap.put("OT_FROM_TIME", shiftMap.get("FIRST_TIME"));
								paramMap.put("OT_TO_TIME", shiftMap.get("LAST_TIME"));
								paramMap.put("APPLY_LENGTH", 0.00);
								this.infoApplyDao.updatSubmitArDetailBatchApplyAdjustForChuShi(paramMap);
								this.infoApplyDao.updateAdjustApplyInArDetailBatchApply(paramMap);//修改申请表
							}else {
								this.infoApplyDao.updatSubmitArDetailBatchApplyAdjust(paramMap);
								this.infoApplyDao.updateAdjustApplyInArDetailBatchApply(paramMap);//修改申请表
							}
						}
					} else {
						 String ERROR_MSG = (String) paramMap.get("ERROR_MSG");
						 throw new CommonException(ERROR_MSG);
					}
						 this.infoApplyLeaveDao.deleteAllDataForAdd(paramMap);
				}
			}
			
		}catch (CommonException e1) {
			throw e1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 内务倒休管理的验证
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean arCoordApplyCheckBatchAdjust(LinkedHashMap paramMap, LinkedHashMap personMap,
			String language) throws Exception {
		String AR_DATE_STR = paramMap.get("AR_DATE_STR") != null ? paramMap.get("AR_DATE_STR").toString() : "";
		String fromTime = paramMap.get("fromTime") != null ? paramMap.get("fromTime").toString() : "";
		String toTime = paramMap.get("toTime") != null ? paramMap.get("toTime").toString() : "";
		String APPLY_NO = paramMap.get("APPLY_NO") != null ? paramMap.get("APPLY_NO").toString() : "";
		String APPLY_LENGTH = paramMap.get("APPLY_LENGTH") != null ? paramMap.get("APPLY_LENGTH").toString() : "";
		String ITEM_NO = paramMap.get("ITEM_NO") != null ? paramMap.get("ITEM_NO").toString() : "";
			//加班时间验证
			LinkedHashMap checkMap = new LinkedHashMap();
			
			checkMap.put("AR_DATE_STR", AR_DATE_STR);
			checkMap.put("APPLY_LENGTH", APPLY_LENGTH);
			checkMap.put("PK_NO",APPLY_NO);
			checkMap.put("fromTime", fromTime);
			checkMap.put("toTime", toTime);
			checkMap.put("PERSON_ID", paramMap.get("PERSON_ID"));
			checkMap.put("ITEM_NO", ITEM_NO);
			String result = this.infoApplyDao.getCoordAdjustCheckTSTO(checkMap);
			if ( !"OK".equals(result) ) {
				paramMap.put("ERROR_MSG",result);
				return false;
			}
		return true;
	}
	/**
	 * 批量保存加班
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveOtApplyAffirmForBatch(HttpServletRequest request) throws Exception{
		int returnNum = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String jsonString = request.getParameter("jsonData");
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		List<LinkedHashMap<String, Object>> otApplyAffirmList = ObjectBindUtil.getRequestJsonData(jsonString);
		Map paramMap = new LinkedHashMap() ;
		paramMap.put("otApplyAffirmList", otApplyAffirmList);
		paramMap.put("paramMapo", paramMapo);
		returnNum = this.infoApplyDao.saveOtApplyAffirmForBatch(paramMap);
		return returnNum;
	}
	
	/**
	 * 批量保存加班(可添加删除审批者)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveOtApplyByAnyApproverForBatch(HttpServletRequest request) throws Exception{
		int returnNum = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String jsonString = request.getParameter("jsonData");
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		List<LinkedHashMap<String, Object>> otApplyAffirmList = ObjectBindUtil.getRequestJsonData(jsonString);
		Map paramMap = new LinkedHashMap() ;
		paramMap.put("otApplyAffirmList", otApplyAffirmList);
		paramMap.put("paramMapo", paramMapo);
		returnNum = this.infoApplyDao.saveOtApplyByAnyApproverForBatch(paramMap);
		return returnNum;
	}
	
	/**
	 * 生产值批量保存加班
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveOTApplyInfoForBatchHAE(HttpServletRequest request) throws Exception{
		int returnNum = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String jsonString = request.getParameter("jsonData");
		String affirmJsonString = request.getParameter("affirmJsonData");
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		List<LinkedHashMap<String, Object>> otApplyAffirmList = ObjectBindUtil.getRequestJsonData(jsonString);
		List<LinkedHashMap<String, Object>> affirmList = ObjectBindUtil.getRequestJsonData(affirmJsonString);
		Map paramMap = new LinkedHashMap() ;
		paramMap.put("otApplyAffirmList", otApplyAffirmList);
		paramMap.put("affirmList", affirmList);
		paramMap.put("paramMapo", paramMapo);
		returnNum = this.infoApplyDao.saveOTApplyInfoForBatchHAE(paramMap);
		if(returnNum == 1){
			this.infoApplyDao.delOTExForBatchInfoListHAE(paramMapo);
		}
		return returnNum;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int saveAdjustHolidayApplyForBatch(HttpServletRequest request) throws Exception {
		int returnNum = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String jsonString = request.getParameter("jsonData");
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		List<LinkedHashMap<String, Object>> adjustHolidayApplyList = ObjectBindUtil.getRequestJsonData(jsonString);
		Map paramMap = new LinkedHashMap() ;
		paramMap.put("adjustHolidayApplyList", adjustHolidayApplyList);
		paramMap.put("paramMapo", paramMapo);
		returnNum = this.infoApplyDao.saveAdjustHolidayApplyForBatch(paramMap);
		return returnNum;
	}
	/**
	 * 批量删除考勤
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delOtApplyAffirmForBatch(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			this.infoApplyDao.delOtApplyAffirmForBatch(this.encapsulationApplyNoListForBatch(request,"c1"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量删除考勤
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delAdjustHolidayApplyForBatch(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			this.infoApplyDao.delAdjustHolidayApplyForBatch(this.encapsulationApplyNoListForBatch(request,"BATCH_ADJUST"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量封装信息申请信息成List(encapsulation information apply from request to List for
	 * batch)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List encapsulationApplyNoListForBatch(HttpServletRequest request,String type) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues(type);
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("UPDATED_IP", admin.getAdminIP());
				map.put("APPLY_NO", paramData[i]);
				map.put("APPLY_TYPE", "31");
				map.put("CPNY_ID", admin.getCpnyId());
				list.add(map);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int addOtApplyAffirm(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		Map paramMap = new LinkedHashMap() ;
		paramMap.put("paramMapo", paramMapo);
		this.infoApplyDao.addOtApplyAffirm(paramMap);
		return 1;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int addAdjustHolidayApply(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		Map paramMap = new LinkedHashMap() ;
		paramMap.put("paramMapo", paramMapo);
		this.infoApplyDao.addAdjustHolidayApply(paramMap);
		return 1;
	}

	@Override
	public List getValidateInfo(Map searchMap) throws Exception {
		List returnList = new ArrayList();
		returnList = infoApplyDao.getValidateInfo(searchMap);
		return returnList;
	}
	

	
	@Override
	public String submitImportExcelOtData(HttpServletRequest request){
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_ESS_OT_EXCEL_IMP.PR_IMPORT_ESS_OT_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@Override
public List getOtTempList(HttpServletRequest request)
		throws Exception {
	List retrunList = new ArrayList();
	// 页面提交数据
	LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
			"seach_");
	AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	paramMap.put("PERSON_ID", admin.getPersonId());
	retrunList = infoApplyDao.getOtTempList(paramMap);
	return retrunList;
}
@Override
public int getOtTempCnt(HttpServletRequest request, String errorFlag) throws Exception{
	int retrunInt = 0;
	// 页面提交数据
	LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
			"seach_");
	AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	paramMap.put("PERSON_ID", admin.getPersonId());
	if("E".equals(errorFlag)){
		retrunInt = infoApplyDao.getOtTempErrorCnt(paramMap);
	}else{
		retrunInt = infoApplyDao.getOtTempCnt(paramMap);
	}

	return retrunInt;
}
	@SuppressWarnings("unchecked")
	@Override
	public Object getOtLength(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Object object;
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequest(request);
		object = infoApplyDao.getOtLength(paramMap);
		return object;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getOtShiftTime(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Object object;
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequest(request);
		object = infoApplyDao.getOtShiftTime(paramMap);
		return object;
	}
	
	@Override
	public String valImportExcelOtData(HttpServletRequest request){
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_ESS_OT_EXCEL_IMP.PR_VALID_ESS_OT_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
