package com.ait.ess.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeFactory;

import org.apache.axis.server.ParamList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.dao.PersonInfoDao;
import com.ait.ess.dao.ViewApplyDao;
import com.ait.ar.dao.ItemsDao;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.lowagie.text.pdf.PdfString;
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
public class InfoApplyLeaveSerImpl implements InfoApplyLeaveSer {

	Logger logger = Logger.getLogger(InfoApplyLeaveSerImpl.class);

	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;

	@SuppressWarnings("unused")
	@Autowired
	private ViewApplyDao viewApplyDao;
	@Autowired
	private InfoApplyDao infoApplyDao;
	@Autowired
	private PaTempSalesDAO paTempSalesDAO;
	@Autowired
	private AuthorityUtil authorityUtil;

	private GregorianCalendar startTime = new GregorianCalendar();

	private GregorianCalendar endTime = new GregorianCalendar();

	@Autowired
	private PersonInfoDao personInfoDao;
	@Autowired
	private InfoApplySer  infoApplySer ;
	@Autowired
	private ItemsDao ItemsDao;
	
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
		
		return infoApplyLeaveDao.getPersonalInfoByPid(paramMap);
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
	public List getLeaveApplyArMonthList(HttpServletRequest request) throws Exception {
		@SuppressWarnings("unused")
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		returnList = infoApplyLeaveDao.getLeaveApplyArMonthList(paramMap);
		
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
	public List getLeaveAffirmInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		if (!"".equals(paramMap.get("LEAVE_APPLY_TYPE_CODE"))) {
			String ITEM_NO = infoApplyLeaveDao.getArDetailItemNoBaseApplyCode(paramMap);
			paramMap.put("ITEM_NO", ITEM_NO);
		}
		//将考勤申请类型转换为对应的考勤状态
		
		paramMap.put("PERSON_ID",admin.getAdminID());
		
		
			returnList = infoApplyLeaveDao.getLeaveAffirmInfoList(paramMap);

		return returnList;
	}
	/**
	 * 个人考勤申请明细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonalAttInfoDetailList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		String firstFlag= request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				paramMap.put("FROM_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				paramMap.put("TO_DATE",last);
			}
		}
//		if (!"".equals(paramMap.get("LEAVE_APPLY_TYPE_CODE"))) {
//			String ITEM_NO = infoApplyLeaveDao.getArDetailItemNoBaseApplyCode(paramMap);
//			paramMap.put("ITEM_NO", ITEM_NO);
//		}
		//将考勤申请类型转换为对应的考勤状态
		
		paramMap.put("PERSON_ID",admin.getAdminID());
		
		
		returnList = infoApplyLeaveDao.getPersonalAttInfoDetailList(paramMap);

		return returnList;
	}
	/**
	 * 异常考勤申请明细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalAttInfoDetailList1(HttpServletRequest request,ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		String firstFlag= request.getParameter("firstFlag");
		//将考勤申请类型转换为对应的考勤状态
		if (paramMap.get("FROM_DATE") == null && paramMap.get("TO_DATE") == null) {// 页面搜索
			if (firstFlag ==null || "".equals(firstFlag)) {
	            paramMap.put("FROM_DATE", modelMap.get("FROM_DATE").toString().replaceAll("-", "/"));
				paramMap.put("TO_DATE", modelMap.get("TO_DATE").toString().replaceAll("-", "/"));
			}
		}else{
			    paramMap.put("FROM_DATE", paramMap.get("FROM_DATE").toString().replaceAll("-", "/"));
				paramMap.put("TO_DATE", paramMap.get("TO_DATE").toString().replaceAll("-", "/"));
		}
		paramMap.put("PERSON_ID",admin.getAdminID());
		
		
		returnList = infoApplyLeaveDao.getPersonalAttInfoDetailList1(paramMap);

		return returnList;
	}
	
	/**
	 * 异常考勤申请明细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewCheckAttencetanceExForBatchList(HttpServletRequest request,ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		//将考勤申请类型转换为对应的考勤状态
		if (paramMap.get("FROM_DATE") == null && paramMap.get("TO_DATE") == null) {// 页面搜索
			
            paramMap.put("FROM_DATE", modelMap.get("FROM_DATE").toString().replaceAll("-", "/"));
			paramMap.put("TO_DATE", modelMap.get("TO_DATE").toString().replaceAll("-", "/"));
		}else{
			    paramMap.put("FROM_DATE", paramMap.get("FROM_DATE").toString().replaceAll("-", "/"));
				paramMap.put("TO_DATE", paramMap.get("TO_DATE").toString().replaceAll("-", "/"));
		}
		paramMap.put("PERSON_ID",admin.getAdminID());
		returnList = infoApplyLeaveDao.viewCheckAttencetanceExForBatchList(paramMap);
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
	public List getCoordLeaveInfoList(HttpServletRequest request) throws Exception { 
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("PERSON_ID001",admin.getAdminID());
		String firstFlag= request.getParameter("firstFlag");
		if(firstFlag==null){
			//当前日期的前一天
			SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);		
			String first = format.format(c.getTime());
			paramMap.put("FROM_DATE",first);
			paramMap.put("TO_DATE", first); 
		}
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
			returnList = infoApplyLeaveDao.getCoordLeaveInfoList(paramMap);
		return returnList;
	}
	
	public String changeCodeAll(String code) {
		String codeString = "";
		if(code != null&&!code.equals("null")&&!code.equals("")){
			String[] array = code.split(",");
			for (int i = 0; i < array.length; i++) {
				if(i==array.length-1){
					codeString  = "'" + array[i] +"'" + codeString;
				}else{
					codeString  = ",'" + array[i] +"'" + codeString;
				}
			}
		}
		return codeString;
	}
	/**
	 * 部门员工休假查询信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDeptLeaveInfoList(HttpServletRequest request) throws Exception {
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
		/*if ("".equals(paramMap.get("DEPTNO"))) {
			paramMap.put("DEPTNO",admin.getDeptNo());
		}*/
			returnList = infoApplyLeaveDao.getDeptLeaveInfoList(paramMap);
		
		return returnList;
	}

	/**
	 * 休假批量申请查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getBatchLeaveAffirmInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("APPLY_TYPE", "21");
		paramMap.put("PERSON_ID001", admin.getPersonId());
		
//		paramMap.put("KEY", paramMap.get("dwz.person.empName"));
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		/*if (UiUtil.getPageNum(request) > 0) {
			returnList = infoApplyLeaveDao.getBatchLeaveAffirmInfoList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = infoApplyLeaveDao.getBatchLeaveAffirmInfoList(paramMap);
		}*/
		returnList = infoApplyLeaveDao.getBatchLeaveAffirmInfoList(paramMap);
		return returnList;
	}
	/**
	 * 多天假查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getBatchLeaveAffirmMoreDayInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("APPLY_TYPE", "21");
		paramMap.put("PERSON_ID001", admin.getPersonId());
		
//		paramMap.put("KEY", paramMap.get("dwz.person.empName"));
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		returnList = infoApplyLeaveDao.getBatchLeaveAffirmMoreDayInfoList(paramMap);
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewAddAttendanceApplyInfoForBatch(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		returnList = infoApplyLeaveDao.viewAddAttendanceApplyInfoForBatch(paramMap);
		return returnList;
	}
	
	/**
	 * 休假批量申请查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getNullBatchLeaveAffirmInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("CANCEL_FLAG", request.getParameter("CANCEL_FLAG"));
		String firstFlag = request.getParameter("firstFlag");
		
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				/*c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				paramMap.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				paramMap.put("END_DATE",last);*/
				paramMap.put("START_DATE", DateUtil.getSysdateStr("dd/MM/yyyy"));
				paramMap.put("END_DATE", DateUtil.getSysdateStr("dd/MM/yyyy"));
			}
			infoApplyLeaveDao.delAttendanceExForBatchInfoList(paramMap);//删除为空的数据
		}
		returnList = infoApplyLeaveDao.getNullBatchLeaveAffirmInfoList(paramMap);
		return returnList;
	}
	
	/**
	 * 身份证到期导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getIdCardExpire(HttpServletRequest request) throws Exception {
		List returnList = new ArrayList();
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		returnList = infoApplyLeaveDao.getIdCardExpire(paramMap);
		return returnList;
	}
	


	@SuppressWarnings("unchecked")
	@Override
	public List getFileList(Map map) throws Exception {
		List returnList = new ArrayList();
		returnList = infoApplyLeaveDao.getFileList(map);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAttendanceExForBatchInfoList(HttpServletRequest request, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		if ((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)) {// 页面搜索
            paramMap.put("START_DATE", modelMap.get("START_DATE").toString().replaceAll("-", "/"));
			paramMap.put("END_DATE", modelMap.get("END_DATE").toString().replaceAll("-", "/"));
		}else{
			paramMap.put("START_DATE", paramMap.get("START_DATE").toString().replaceAll("-", "/"));
			paramMap.put("END_DATE", paramMap.get("END_DATE").toString().replaceAll("-", "/"));
		}
		returnList = infoApplyLeaveDao.getAttendanceExForBatchInfoList(paramMap);
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int getAttendanceExInfoListCnt(HttpServletRequest request, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int returnInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		if ((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)) {// 页面搜索
            paramMap.put("START_DATE", modelMap.get("START_DATE").toString().replaceAll("-", "/"));
			paramMap.put("END_DATE", modelMap.get("END_DATE").toString().replaceAll("-", "/"));
		}else{
			paramMap.put("START_DATE", paramMap.get("START_DATE").toString().replaceAll("-", "/"));
			paramMap.put("END_DATE", paramMap.get("END_DATE").toString().replaceAll("-", "/"));
		}
		returnInt = infoApplyLeaveDao.getAttendanceExInfoListCnt(paramMap);
		return returnInt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getNullBatchOTSSTAffirmInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		
		returnList = infoApplyLeaveDao.getNullBatchOTSSTAffirmInfoList(paramMap);
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getNullBatchAJTSTOAffirmInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		
		returnList = infoApplyLeaveDao.getNullBatchAJTSTOAffirmInfoList(paramMap);
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getNullBatchOTTSTOAffirmInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("CANCEL_FLAG", request.getParameter("CANCEL_FLAG"));
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, 0);
				//c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				paramMap.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				//c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				paramMap.put("END_DATE",last);
			}
			infoApplyLeaveDao.delNullBatchOTTSTOAffirmInfoList(paramMap);
		}
		returnList = infoApplyLeaveDao.getNullBatchOTTSTOAffirmInfoList(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewNullBatchOTTSTOAffirmInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		List returnList = new ArrayList();
		returnList = infoApplyLeaveDao.viewNullBatchOTTSTOAffirmInfoList(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewAddOTApplyInfoForBatchHAE(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		returnList = infoApplyLeaveDao.viewAddOTApplyInfoForBatchHAE(paramMap);
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
	 * 休假申请决裁信息总数(get Leave Affirm Info List Cnt)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getLeaveAffirmInfoListCnt(HttpServletRequest request) throws Exception {
		int ListCnt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		if(paramMap.get("FLAG") == null || !"1".equals(paramMap.get("FLAG").toString())){
			paramMap.put("KEY", admin.getEmpID());
		}
		paramMap.put("APPLY_TYPE", "PERSON");
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID",  request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		paramMap.put("PERSON_ID",admin.getAdminID());
		paramMap.put("specialParam",admin.getSpecialParam());//这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo",admin.getDeptNo());//判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		ListCnt =  this.infoApplyLeaveDao.getLeaveAffirmInfoListCnt(paramMap);
		
		return ListCnt;
	}
	/**
	 * 休假批量申请决裁信息总数(get Leave Affirm Info List Cnt)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getBatchLeaveAffirmInfoListCnt(HttpServletRequest request) throws Exception {
		int ListCnt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,"seach_");
		String PERSON_ID = request.getParameter("dwz.person.personId");
		paramMap.put("APPLY_TYPE", "21");
		//分页用
		if ( "".equals(PERSON_ID)) {
			paramMap.put("PERSON_ID", admin.getPersonId());
		}else {
			paramMap.put("PERSON_ID", PERSON_ID);
		}
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		ListCnt =  this.infoApplyLeaveDao.getBatchLeaveAffirmInfoListCnt(paramMap);
		
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
	private List getAffirmorListByMap(LinkedHashMap paramMap) throws Exception {
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);
		paramMap.put("CPNY_ID", personMap.get("CPNY_ID"));
		paramMap.put("DEPT_NO", personMap.get("DEPT_NO"));
		paramMap.put("DUTY_NO", personMap.get("DUTY_NO"));
		// 先取特殊设置人员的决裁者(get approver by special-person's-approver setup first)
		List<LinkedHashMap> spePersonList = this.infoApplyLeaveDao.getAffirmorListByPersonID(paramMap);
		if (spePersonList.size() == 0) {
			// 再取特殊设置部门的决裁者(get approver by special-department's-approver setup second)
			List<LinkedHashMap> speDeptList = this.infoApplyLeaveDao.getAffirmorListByDeptNo(paramMap);
			if (speDeptList.size() == 0) {
				// 最后按流程取决裁者 (get approver by approve-flow last)
				return this.infoApplyLeaveDao.getAffirmorListByNormal(paramMap);
			} else {
				return speDeptList;
			}
		} else {
			return spePersonList;
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
	public int addLeaveApply(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchLeaveApplyList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		String person_id = paramMap.get("dwz.person.personId1") != null ? paramMap.get("dwz.person.personId1").toString() : "";
//		批量/个人申请
		String applyType= paramMap.get("APPLY_TYPE") != null ? paramMap.get("APPLY_TYPE").toString() : "0";
		String affirmFlag ="14014307";
		String leaveType = paramMap.get("LEAVE_TIME_TYPE") != null ? paramMap.get("LEAVE_TIME_TYPE").toString() : "";
		String applyTypeNo = paramMap.get("APPLY_TYPE_NO") != null ? paramMap.get("APPLY_TYPE_NO").toString() : "";
		String applyTypeCode = paramMap.get("LEAVE_APPLY_TYPE_CODE") != null ? paramMap.get("LEAVE_APPLY_TYPE_CODE").toString() : "";
//		申请日期
		String applyTime = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
		if(applyTime==null || "".equals(applyTime)){
			applyTime =    paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
		}
		//获取考勤日期
		 SimpleDateFormat sdfDateFormat1=new SimpleDateFormat("yyyyMM");
		 Date AR_MONTH = sdfDateFormat1.parse(applyTime.substring(0,4)+   applyTime.substring(5,7));
		 Calendar calendar = Calendar.getInstance();//日历对象
		 calendar.setTime(AR_MONTH);//设置当前日期
		 calendar.add(Calendar.MONTH, 1);//月份加1

		String fromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
		String toDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
		String fromTime = paramMap.get("fromTime") != null ? paramMap.get("fromTime").toString() : "";
		String toTime = paramMap.get("toTime") != null ? paramMap.get("toTime").toString() : "";
//		申请时长
		String applyLength = paramMap.get("APPLY_LENGTH") != null ? paramMap.get("APPLY_LENGTH").toString() : "";

//		申请原因
		String leaveReason = paramMap.get("LEAVE_REASON") != null ? paramMap.get("LEAVE_REASON").toString() : "";
		//目的地
		String destination = paramMap.get("destination") != null ? paramMap.get("destination").toString() : "";
		//联络处
		String liaison = paramMap.get("liaison") != null ? paramMap.get("liaison").toString() : "";

		
		LinkedHashMap dateMap = new LinkedHashMap();
		dateMap = this.getLinkedMapByRequest(request, dateMap);
		dateMap.put("PERSON_ID", person_id);
		dateMap.put("APPLY_TYPE", applyType);
		dateMap.put("AFFIRM_FLAG", affirmFlag);
		dateMap.put("LEAVE_TIME_TYPE", leaveType);
		dateMap.put("APPLY_TYPE_NO", applyTypeNo);
		dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
		dateMap.put("APPLY_TIME", applyTime);
		dateMap.put("LEAVE_FROM_TIME", fromDate + " " + fromTime + ":"+ "00");
		dateMap.put("LEAVE_TO_TIME", toDate + " " + toTime + ":"+ "00");
		dateMap.put("AR_MONTH_STR", sdfDateFormat1.format(calendar.getTime()));
		dateMap.put("R_DATE", fromDate);
		dateMap.put("APPLY_LENGTH", applyLength);
		dateMap.put("DEPTNO", admin.getDeptNo());
		
		dateMap.put("LEAVE_REASON", leaveReason);
		dateMap.put("DESTINATION", destination);
		dateMap.put("LIAISON", liaison);
		
		dateMap.put("CPNY_ID", admin.getCpnyId());
		dateMap.put("interCpnyID", admin.getCpnyId());
		dateMap.put("interLanguage", admin.getLanguage());
		dateMap.put("applyTypeCode_forShiChang", applyTypeCode);
		dateMap.put("CREATED_IP", admin.getAdminIP());
		dateMap.put("CREATED_BY", admin.getPersonId());
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
		dateMap.put("affirmList", affirmList);
		//插入数据库之前的数据的整理、验证，主要是决裁者与时间的验证，然后封装起来
		
			LinkedHashMap leaveMap = this.preAddApplyLeave(dateMap, admin.getLanguage());
			batchLeaveApplyList.add(leaveMap);
			this.infoApplyLeaveDao.addLeaveApplyInPerson(batchLeaveApplyList);
			//拆分多天的出差申请
			this.infoApplyLeaveDao.insertSubmitLeaveApplyInBatchForMoreDay(dateMap);
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
			//LinkedHashMap personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);
			//获取添加的决裁者的person_id
			String personId=paramMap.get(keyName).toString();
			affirmMap.put("AFFIRMOR_ID", personId);
			affirmMap.put("AFFIRM_LEVEL", affirmLevel);
			
			addAffirmList.add(affirmMap);
		}
		//从数据库中根据决裁设置获取决裁者信息
		List affirmerList = this.getAffirmorListByMap(paramMap);
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
		Object paramValueObj = null;
		String ifNeedAffirm = "";
		String ifNeedConfirm = "";
		// 需要决裁
		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
		essParamMap.put("ESS_PARAM_NO", "4159");
		paramValueObj = this.infoApplyLeaveDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedAffirm = paramValueObj != null ? paramValueObj.toString() : "0";
		essParamMap.put("ESS_PARAM_NO", "4160");
		paramValueObj = this.infoApplyLeaveDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedConfirm = paramValueObj != null ? paramValueObj.toString() : "0";
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);
		//从系统配置里获取决裁者
		//List affirmerList = this.getAffirmorListByMap(paramMap);
		//从系统配置、页面添加的决裁者组合以后得出的决裁者信息
		List affirmerList = (ArrayList<LinkedHashMap>)paramMap.get("affirmList");
		if (affirmerList.size() == 0 && "1".equals(ifNeedAffirm)) {
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
			//if (otApplyCheck(paramMap, personMap, language)){
			if(1==1){
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
		String ifForcedTypeChoice = paramMap.get("ifForcedTypeChoice") != null ? paramMap.get("ifForcedTypeChoice").toString(): "0";
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
			int dataType = this.infoApplyLeaveDao.getDataType(paramMap);
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
		paramValueObj = this.infoApplyLeaveDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		
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
		paramValueObj = this.infoApplyLeaveDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
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
		retrunList = infoApplyLeaveDao.getDateByPersonIdAndCpny(paramMap) ;		
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
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List returnList = new ArrayList();
		// 页面提交数据
		returnList = infoApplyLeaveDao.getApplyorByApplyNoList(paramMap);
		
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

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		returnList = infoApplyLeaveDao.getAffirmorByApplyNoList(paramMap);
		
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
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			paramMap.put("ADMIN_ID", paramMap.get("personId"));
			paramMap.put("interLanguage",  paramMap.get("LANGUAGE"));
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("ADMIN_ID", admin.getPersonId());
		}
		returnList = infoApplyLeaveDao.getCheckorByApplyNoList(paramMap);
		
		return returnList;
	}
	
	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delLeaveApplyInBatch(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			this.infoApplyLeaveDao.delLeaveApplyInBatch(this.encapsulationApplyNoListForBatch(request,"SINGLE_LEAVE"));
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
	public int delAttendanceApplyInfoForBatch(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			this.infoApplyLeaveDao.delAttendanceApplyInfoForBatch(this.encapsulationApplyNoListForBatch(request,"BATCH_LEAVE"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public int delAttendanceExInBatchForBatch(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			this.infoApplyLeaveDao.delAttendanceExInBatchForBatch(this.encapsulationApplyNoListForBatch(request,"EX_LEAVE"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量保存考勤
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveAttendanceApplyInfoForBatch(HttpServletRequest request) throws Exception {
		int returnNum = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String jsonString = request.getParameter("jsonData");
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		List<LinkedHashMap<String, Object>> attendanceApplyInfoList = ObjectBindUtil.getRequestJsonData(jsonString);
		Map paramMap = new LinkedHashMap() ;
		paramMap.put("attendanceApplyInfoList", attendanceApplyInfoList);
		paramMap.put("paramMapo", paramMapo);
		returnNum = this.infoApplyLeaveDao.saveAttendanceApplyInfoForBatch(paramMap);
		return returnNum;
	}
	
	/**
	 * 批量保存考勤(可添加删除审批者)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveAttApplyInfoByAnyApproverForBatch(HttpServletRequest request) throws Exception {
		int returnNum = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String jsonString = request.getParameter("jsonData");
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		List<LinkedHashMap<String, Object>> attendanceApplyInfoList = ObjectBindUtil.getRequestJsonData(jsonString);
		Map paramMap = new LinkedHashMap() ;
		paramMap.put("attendanceApplyInfoList", attendanceApplyInfoList);
		paramMap.put("paramMapo", paramMapo);
		returnNum = this.infoApplyLeaveDao.saveAttApplyInfoByAnyApproverForBatch(paramMap);
		return returnNum;
	}
	
	/**
	 * 生产值批量保存考勤
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveAttendanceApplyInfoForBatchHAE(HttpServletRequest request) throws Exception {
		int returnNum = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String jsonString = request.getParameter("jsonData");
		String affirmJsonString = request.getParameter("affirmJsonData");
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		List<LinkedHashMap<String, Object>> attendanceApplyInfoList = ObjectBindUtil.getRequestJsonData(jsonString);
		List<LinkedHashMap<String, Object>> affirmList = ObjectBindUtil.getRequestJsonData(affirmJsonString);
		Map paramMap = new LinkedHashMap() ;
		paramMap.put("attendanceApplyInfoList", attendanceApplyInfoList);
		paramMap.put("affirmList", affirmList);		
		paramMap.put("paramMapo", paramMapo);
		returnNum = this.infoApplyLeaveDao.saveAttendanceApplyInfoForBatchHAE(paramMap);
		if(returnNum == 1){
			this.infoApplyLeaveDao.delAttendanceExForBatchInfoListHAE(paramMapo);
		}
		return returnNum;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int saveAttenanceExBatchInfo(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String jsonString = request.getParameter("jsonData");
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		List<LinkedHashMap<String, Object>> attendanceApplyExInfoList = ObjectBindUtil.getRequestJsonData(jsonString);
		Map paramMap = new LinkedHashMap() ;
		paramMap.put("attendanceApplyExInfoList", attendanceApplyExInfoList);
		paramMap.put("paramMapo", paramMapo);
		this.infoApplyLeaveDao.saveAttenanceExBatchInfo(paramMap);
		return 1;
	}
	
	/**
	 * 批量添加考勤
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addAttendanceApplyInfoForBatch(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		Map paramMap = new LinkedHashMap() ;
		paramMap.put("paramMapo", paramMapo);
		this.infoApplyLeaveDao.addAttendanceApplyInfoForBatch(paramMap);
		return 1;
	}
	
	
	/**
	 * 批量删除考勤异常申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delApplyLeaveInfo(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			this.infoApplyLeaveDao.delApplyLeaveInfo(this.encapsulationApplyNoListForBatch(request,"c1"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量删除考勤异常申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delAttencetanceEx(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			this.infoApplyLeaveDao.delAttencetanceEx(this.encapsulationApplyNoListForBatch(request,"c1"));
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
				map.put("APPLY_TYPE", "21");
				map.put("CPNY_ID", admin.getCpnyId());
				list.add(map);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 批量封装删除的数据list
	 * @param request
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List encapsulationApplyAttdanceNoListForBatchDelete(HttpServletRequest request,String type) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String[] paramData = request.getParameterValues("BATCH_LEAVE");
		for (int i = 0; i < paramData.length; i++) {
			String APPLY_NO = paramData[i];
			LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
			LinkedHashMap dataMap = new LinkedHashMap();
			String FROM_DATE = paramMap.get("FROM_DATE"+paramData[i]).toString() != null ? 
					paramMap.get("FROM_DATE"+paramData[i]).toString():"";
			String TO_DATE = paramMap.get("TO_DATE"+paramData[i]) != null ? 
					paramMap.get("TO_DATE"+paramData[i]).toString():"";
			String OLD_FROM_DATE = paramMap.get("oldFROM_DATE"+paramData[i]) != null ? 
					paramMap.get("oldFROM_DATE"+paramData[i]).toString():"";
            String OLD_TO_DATE = paramMap.get("oldTO_DATE"+paramData[i]) != null ? 
					paramMap.get("oldTO_DATE"+paramData[i]).toString():"";
			String ITEM_NO = paramMap.get("ITEM_NO"+paramData[i]) != null ? 
					paramMap.get("ITEM_NO"+paramData[i]).toString():"";
			String toTime = paramMap.get("toTime"+paramData[i]) != null ? 
					paramMap.get("toTime"+paramData[i]).toString():"";
			String ajSeq = paramMap.get("ajSeq"+paramData[i]) != null ? 
							paramMap.get("ajSeq"+paramData[i]).toString():"";
			String personId = paramMap.get("personid"+paramData[i]) != null ? 
					paramMap.get("personid"+paramData[i]).toString():admin.getPersonId();
		    String reason = paramMap.get("reason"+paramData[i]) != null ? 
					paramMap.get("reason"+paramData[i]).toString():"";
			String APPLY_LENGTH = paramMap.get("APPLY_LENGTH"+paramData[i]) != null ? 
					paramMap.get("APPLY_LENGTH"+paramData[i]).toString():"";
			dataMap.put("FROM_DATE", FROM_DATE);
			dataMap.put("AR_DATE_STR", FROM_DATE);
			dataMap.put("PERSON_ID", personId);
			dataMap.put("FROM_DATE", FROM_DATE);
			dataMap.put("TO_DATE", TO_DATE);
			dataMap.put("OLD_FROM_DATE", OLD_FROM_DATE);
			dataMap.put("OLD_TO_DATE", OLD_TO_DATE);
			dataMap.put("LEAVE_TO_TIME", TO_DATE + " " + toTime + ":"+ "00");
			dataMap.put("ITEM_NO", ITEM_NO);
		    dataMap.put("APPLY_NO", APPLY_NO);
			dataMap.put("CPNY_ID", admin.getCpnyId());
			dataMap.put("ajSeq", ajSeq);
			dataMap.put("reason", reason);
			dataMap.put("APPLY_LENGTH", APPLY_LENGTH);
			list.add(dataMap);
		}
			
		return list;
	}
	
	
	/**
	 * 批量封装信息申请信息成List(encapsulation information apply from request to List for
	 * batch)
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private List encapsulationApplyAttdanceNoListForBatch(HttpServletRequest request,String type) throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		try {
			String[] paramData = request.getParameterValues(type);
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = new LinkedHashMap();
				String personId = paramMap.get("personid"+paramData[i]) != null ? 
						paramMap.get("personid"+paramData[i]).toString():admin.getPersonId();
				String FROM_DATE = paramMap.get("FROM_DATE"+paramData[i]) != null ? 
						paramMap.get("FROM_DATE"+paramData[i]).toString():"";
				String TO_DATE = paramMap.get("TO_DATE"+paramData[i]) != null ? 
						paramMap.get("TO_DATE"+paramData[i]).toString():"";
				String fromTime = paramMap.get("fromTime"+paramData[i]) != null ? 
						paramMap.get("fromTime"+paramData[i]).toString():"";
				String toTime = paramMap.get("toTime"+paramData[i]) != null ? 
						paramMap.get("toTime"+paramData[i]).toString():"";
				String fromTime_first = paramMap.get("fromTime_first"+paramData[i]) != null ? 
								paramMap.get("fromTime_first"+paramData[i]).toString():"";
				String toTime_first = paramMap.get("toTime_first"+paramData[i]) != null ? 
								paramMap.get("toTime_first"+paramData[i]).toString():"";
				String APPLY_LENGTH = paramMap.get("APPLY_LENGTH"+paramData[i]) != null ? 
				        paramMap.get("APPLY_LENGTH"+paramData[i]).toString():"";
				String reason = paramMap.get("reason"+paramData[i]) != null ? 
						paramMap.get("reason"+paramData[i]).toString():"";
				String AFFIRM_FLAG = paramMap.get("AFFIRM_FLAG"+paramData[i]) != null ? 
						paramMap.get("AFFIRM_FLAG"+paramData[i]).toString():"0";
				String ITEM_NO = paramMap.get("ITEM_NO"+paramData[i]) != null ? 
						paramMap.get("ITEM_NO"+paramData[i]).toString():"";
				String SHIFT_NO = paramMap.get("SHIFT_NO"+paramData[i]) != null ? 
						paramMap.get("SHIFT_NO"+paramData[i]).toString():"";
				String GROUP_ID = paramMap.get("GROUP_ID"+paramData[i]) != null ? 
						paramMap.get("GROUP_ID"+paramData[i]).toString():"";
				String DEPTNO = paramMap.get("DEPTNO"+paramData[i]) != null ? 
						paramMap.get("DEPTNO"+paramData[i]).toString():"";
				String DATE_TYPE = paramMap.get("DATE_TYPE"+paramData[i]) != null ? 
								paramMap.get("DATE_TYPE"+paramData[i]).toString():"";
				String UNIT = paramMap.get("UNIT"+paramData[i]) != null ? 
					   paramMap.get("UNIT"+paramData[i]).toString():"";
				String STATUS_CODE = paramMap.get("STATUS_CODE"+paramData[i]) != null ? 
							   paramMap.get("STATUS_CODE"+paramData[i]).toString():"";
				String STATUS_NAME = paramMap.get("STATUS_NAME"+paramData[i]) != null ? 
							   paramMap.get("STATUS_NAME"+paramData[i]).toString():"";
				String IWEEK = paramMap.get("IWEEK"+paramData[i]) != null ? 
								paramMap.get("IWEEK"+paramData[i]).toString():"";
				String LOCK_YN = paramMap.get("LOCK_YN"+paramData[i]) != null ? 
								paramMap.get("LOCK_YN"+paramData[i]).toString():"";
				String POST_GRADE_NO = paramMap.get("POST_GRADE_NO"+paramData[i]) != null ? 
								paramMap.get("POST_GRADE_NO"+paramData[i]).toString():"";
				String AR_MONTH_STR = paramMap.get("AR_MONTH_STR"+paramData[i]) != null ? 
										paramMap.get("AR_MONTH_STR"+paramData[i]).toString():"";				
			    String ajSeq = paramMap.get("ajSeq"+paramData[i]) != null ? 
												paramMap.get("ajSeq"+paramData[i]).toString():"";				
				String ajQUANTITY = paramMap.get("ajQUANTITY"+paramData[i]) != null ? 
														paramMap.get("ajQUANTITY"+paramData[i]).toString():"0";				
								
				//拆分假期用
								
				String OLD_FROM_DATE = paramMap.get("oldFROM_DATE"+paramData[i]) != null ? 
										paramMap.get("oldFROM_DATE"+paramData[i]).toString():"";
				String OLD_TO_DATE = paramMap.get("oldTO_DATE"+paramData[i]) != null ? 
										paramMap.get("oldTO_DATE"+paramData[i]).toString():"";
				String old_fromTime = paramMap.get("oldfromTime"+paramData[i]) != null ? 
										paramMap.get("oldfromTime"+paramData[i]).toString():"";
				String old_toTime = paramMap.get("oldtoTime"+paramData[i]) != null ? 
										paramMap.get("oldtoTime"+paramData[i]).toString():"";
				String OLD_ITEM_NO = paramMap.get("OLD_ITEM_NO"+paramData[i]) != null ? 
										paramMap.get("OLD_ITEM_NO"+paramData[i]).toString():"";
				if (!"".equals(personId)) {
					map.put("GROUP_ID", GROUP_ID);
					map.put("SHIFT_NO", SHIFT_NO);
					map.put("POST_GRADE_NO", POST_GRADE_NO);
				    map.put("DEPTNO", DEPTNO);
				    map.put("LOCK_YN", LOCK_YN);
					map.put("DATE_TYPE", DATE_TYPE);
					//141442(早退)  141441(迟到)  14013783(厂车迟到)对应的UNIT 是  MINUTE
					if ("141442".equals(ITEM_NO)||"141441".equals(ITEM_NO)||"14013783".equals(ITEM_NO)) {
						map.put("UNIT", "MINUTE");
					}else {
						map.put("UNIT", "HOUR");
					}
					
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
					
					map.put("STATUS_CODE", STATUS_CODE);				
					map.put("STATUS_NAME", STATUS_NAME);				
					map.put("IWEEK", IWEEK);				
				    map.put("personId", personId);
				    map.put("ITEM_NO", ITEM_NO);
				    map.put("OLD_ITEM_NO", OLD_ITEM_NO);
				    map.put("FROM_DATE", FROM_DATE);
				    map.put("TO_DATE", TO_DATE);
				    map.put("fromTime", fromTime);
				    map.put("toTime", toTime);
				    map.put("fromTime_first", fromTime_first);
				    map.put("toTime_first", toTime_first);
				    map.put("AR_DATE_STR", FROM_DATE);
				    map.put("AR_MONTH_STR", AR_MONTH_STR);
				    map.put("ajSeq", ajSeq);//调休的数据序列号
				    map.put("ajQUANTITY", ajQUANTITY);//调休的数据序列号
				    
				    map.put("LEAVE_FROM_TIME", FROM_DATE + " " + fromTime + ":"+ "00");
					map.put("LEAVE_TO_TIME", TO_DATE + " " + toTime + ":"+ "00");
					//为了拆分用
					map.put("OLD_LEAVE_FROM_TIME", OLD_FROM_DATE + " " + old_fromTime + ":"+ "00");
					map.put("OLD_LEAVE_TO_TIME", OLD_TO_DATE + " " + old_toTime + ":"+ "00");
					map.put("OLD_FROM_DATE", OLD_FROM_DATE);
					map.put("OLD_TO_DATE", OLD_TO_DATE);
					map.put("FROM_DATE",FROM_DATE);
					map.put("TO_DATE", TO_DATE);
					
					map.put("LEAVE_FROM_TIME_FIRST", FROM_DATE + " " + fromTime_first + ":"+ "00");
					map.put("LEAVE_TO_TIME_FIRST", TO_DATE + " " + toTime_first + ":"+ "00");
				    map.put("APPLY_LENGTH", APPLY_LENGTH);
				    map.put("reason", reason);
				    map.put("AFFIRM_FLAG", AFFIRM_FLAG);
					map.put("APPLY_TYPE", "21");
					map.put("CPNY_ID", admin.getCpnyId());
					map.put("UPDATED_IP", admin.getAdminIP());
					map.put("UPDATED_BY", admin.getPersonId());
					map.put("PERSON_ID", personId);
					map.put("APPLY_NO", paramData[i]);
					if (!"141440".equals(ITEM_NO)||!"141441".equals(ITEM_NO)||!"141442".equals(ITEM_NO)||!"141443".equals(ITEM_NO)||!"14013783".equals(ITEM_NO)) {
						String leaveTypeCode = infoApplyLeaveDao.getLeaveApplyCode(map);
						map.put("APPLY_TYPE_CODE", leaveTypeCode);
						if (leaveTypeCode  != null && !"".equals(leaveTypeCode)) {
							List  affirmList =  this.getAffirmorListByString("21", 
									 personId, this.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),leaveTypeCode).toString(),APPLY_LENGTH);
							map.put("affirmList", affirmList);
						}else {
							 throw new CommonException("找不到对应的申请类型，请重新选择考勤状态");
						}
						LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(map);
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
					}
					
				}
				    
					map.put("CREATED_IP", admin.getAdminIP());
					map.put("CREATED_BY", admin.getPersonId());
					list.add(map);
				}
			return list;
		} catch (CommonException e1) {
			throw e1;
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
	public boolean delLeaveApply(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return infoApplyLeaveDao.delLeaveApply(paramMap);
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
	public boolean cancelLeaveApply(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMIN_ID", admin.getPersonId());
		
		return infoApplyLeaveDao.cancelLeaveApply(paramMap);
	}
	
	/**
	 * 申请销假( cancel leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addXiaojiaLeaveApply(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMIN_ID", admin.getPersonId());
		List tbList = new ArrayList();
		tbList = infoApplyLeaveDao.getLeaveAffirmInfo2List(paramMap);
		Map paramMap2 = (Map) tbList.get(0);
		paramMap.put("APPLY_TYPE", paramMap2.get("LEAVE_TYPE_CODE"));
		List<LinkedHashMap> afffirmList = new ArrayList<LinkedHashMap>();
		afffirmList = infoApplyLeaveDao.getAffirmInfo2List(paramMap);
//		要撤销的APPLY_NO
		paramMap2.put("UNDO_APPLY_NO", null == paramMap.get("APPLY_NO") ? ""
				: paramMap.get("APPLY_NO"));
		String CURRENT_AFFIRM_ID = "";
		for(Object obj : afffirmList){
			if(null != ((LinkedHashMap) obj).get("AFFIRM_LEVEL")
					&& !"".equals(((LinkedHashMap) obj).get("AFFIRM_LEVEL").toString())
					&& "1".equals(((LinkedHashMap) obj).get("AFFIRM_LEVEL").toString())){
				
				CURRENT_AFFIRM_ID = null != ((LinkedHashMap) obj).get("AFFIRMOR_ID") 
						? ((LinkedHashMap) obj).get("AFFIRMOR_ID").toString() : "";
			}
			
			((LinkedHashMap) obj).put("APPLY_TYPE_NO", "218112");
			((LinkedHashMap) obj).put("AffirmLevel", ((LinkedHashMap) obj).get("AFFIRM_LEVEL"));
			((LinkedHashMap) obj).put("AffirmorId", ((LinkedHashMap) obj).get("AFFIRMOR_ID"));
			if(null != admin.getPersonId()
					&& !"".equals(admin.getPersonId()))
				((LinkedHashMap) obj).put("CREATED_BY", admin.getPersonId());
		}
		paramMap2.put("BATCH_APPLY_NO", StringUtil.checkNull(paramMap2.get("BATCH_APPLY_NO")));
		paramMap2.put("LEAVE_REASON", StringUtil.checkNull(paramMap2.get("LEAVE_REASON")));
		if(paramMap2.get("APPLY_TYPE") != null && "BATCH".equals(paramMap2.get("APPLY_TYPE").toString())){
			paramMap2.put("LEAVE_FROM_TIME", "");
			paramMap2.put("LEAVE_TO_TIME", "");
			paramMap2.put("LEAVE_REASON", "");
		}
//		销假code
		paramMap2.put("CURRENT_AFFIRM_ID", CURRENT_AFFIRM_ID);
		paramMap2.put("LEAVE_TYPE_CODE", "218112");
		LinkedHashMap map = new LinkedHashMap();
		map.put("tb",paramMap2);
		map.put("affirm",afffirmList);
		infoApplyLeaveDao.addXiaojiaLeaveApplyInfo(map);
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
		
		return infoApplyLeaveDao.getOtDeductTimeList(param);
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
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		//KEY=, GROUP_NO=, DEPT_NO=C025
		if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
			if (UiUtil.getPageNum(request) > 0) {
				returnList = infoApplyLeaveDao.getPersonList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				returnList = infoApplyLeaveDao.getPersonList(paramMap);
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
			lMap.put("REST_ANNUAL_LEAVE", this.getVacationEmpREST(searchAnLeave, admin));
			lMap.put("REST_ANNUAL_LEAVEQN", this.retrieveVacationEmpRESTQN(searchAnLeave, admin));
			searchSurpLeave.put("YEAR_NONTH", yearAndMonth);
			searchSurpLeave.put("YEAR", year);
			searchSurpLeave.put("CPNY_ID", paramMap.get("CPNY_ID"));
			searchSurpLeave.put("PERSON_ID", paramMap.get("PERSON_ID"));
			//lMap.put("SUR_ADJUST_REST", this.infoApplyLeaveDao.getSurplusAdjustRest(searchSurpLeave));
			//2013-12-11 lufeng 修改
			lMap.put("SUR_ADJUST_REST", this.infoApplyLeaveDao.getAdjustRestNew(searchSurpLeave));
			
			list.add(lMap);
		}
		return list;
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
	public Object getAttendanceInformation(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Object object;
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequest(request);
		object = infoApplyLeaveDao.getAttendanceInformation(paramMap);
		return object;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map getOTSSTInformation(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map map;
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequest(request);
		map = infoApplyLeaveDao.getOTSSTInformation(paramMap);
		if (map == null ) {
			//插入一条加班
			infoApplyLeaveDao.insertSSTOtIsNull(paramMap);
			map = infoApplyLeaveDao.getOTSSTInformation(paramMap);//再次查询
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map getAJTSTOInformation(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map map;
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequest(request);
		map = infoApplyLeaveDao.getAJTSTOInformation(paramMap);
		if (map == null ) {
			//插入一条加班
			infoApplyLeaveDao.insertTSTOADJUSTIsNull(paramMap);
			map = infoApplyLeaveDao.getAJTSTOInformation(paramMap);//再次查询
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map getOTTSTOInformation(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map map;
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequest(request);
		map = infoApplyLeaveDao.getOTTSTOInformation(paramMap);
		if (map == null ) {
			//插入一条加班
			infoApplyLeaveDao.insertTSTOADJUSTIsNull(paramMap);
			map = infoApplyLeaveDao.getOTTSTOInformation(paramMap);//再次查询
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
	@SuppressWarnings("unchecked")
	@Override
	public int getPersonListCnt(HttpServletRequest request) throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
			if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
				return this.infoApplyLeaveDao.getPersonListCnt(paramMap);
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
		if (infoApplyLeaveDao.addPersonalInfoApply(param))
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
		return infoApplyLeaveDao.getEmpShift(param);
	}

	/**
	 * 添加加班申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addLeaveApplyOld(HttpServletRequest request) throws Exception {
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
		paramValueObj = this.infoApplyLeaveDao
				.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedAffirm = paramValueObj != null ? paramValueObj.toString() : "0";
		essParamMap.put("ESS_PARAM_NO", "4160");// 4160是否需要人事确认开关的NO
		paramValueObj = this.infoApplyLeaveDao
				.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedConfirm = paramValueObj != null ? paramValueObj.toString() : "0";

		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyLeaveDao
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
					infoApplyLeaveDao.addOvertimeApply(paramMap, distinctList);
				} else if ("0".equals(ifNeedAffirm)
						&& "1".equals(ifNeedConfirm)) {
					// 不需要决裁,但需要人事确认
					paramMap.put("ACTIVITY", "0");
					infoApplyLeaveDao.addOvertimeApply(paramMap, null);
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
	public int addLeaveApply2(HttpServletRequest request) throws Exception {
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
		List dateList = this.infoApplyLeaveDao.getOvertimeApplyAllDateList(paramMap);
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
		this.infoApplyLeaveDao.addLeaveApplyInBatch(batchOtApplyList);
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

			List dateList = this.infoApplyLeaveDao.getOvertimeApplyAllDateList(paramMap);
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

		this.infoApplyLeaveDao.addLeaveApplyInBatch(batchOtApplyList);
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
		paramValueObj = this.infoApplyLeaveDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedAffirm = paramValueObj != null ? paramValueObj.toString() : "0";
		essParamMap.put("ESS_PARAM_NO", "4160");
		paramValueObj = this.infoApplyLeaveDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedConfirm = paramValueObj != null ? paramValueObj.toString() : "0";
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);
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

		this.infoApplyLeaveDao.addLeaveApplyInBatch(batchOtApplyList);
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

		this.infoApplyLeaveDao.addLeaveApplyInBatch(batchOtApplyList);
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

		this.infoApplyLeaveDao.addLeaveApplyInBatch(batchOtApplyList);
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
		paramValueObj = this.infoApplyLeaveDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedAffirm = paramValueObj != null ? paramValueObj.toString() : "0";
		essParamMap.put("ESS_PARAM_NO", "4160");
		paramValueObj = this.infoApplyLeaveDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedConfirm = paramValueObj != null ? paramValueObj.toString() : "0";

		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);

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
		paramMap.put("VAC_TP", "1487");
		paramMap.put("LEAVE_FROM_TIME", date);
		paramMap.put("CPNYID", admin.getCpnyId() );
		return this.infoApplyLeaveDao.retrieveVacationEmpREST(paramMap);
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
		paramMap.put("VAC_TP", "1487");
		paramMap.put("LEAVE_FROM_TIME", date);
		return this.infoApplyLeaveDao.retrieveVacationEmpREST(paramMap);
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
		return this.infoApplyLeaveDao.getVacationEmpREST(paramMap);
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
		return this.infoApplyLeaveDao.retrieveVacationEmpRESTQN(paramMap);
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
		return this.infoApplyLeaveDao.getSurplusAdjustRest(paramMap);
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
		
		return this.infoApplyLeaveDao.getAdjustRestNew(paramMap);
	}
	
	/**
	 * 添加休假申请(add leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public boolean addLeaveApply(HttpServletRequest request) throws Exception {
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//		String fromtime = request.getParameter("LEAVE_FROM_TIME");
//		String totime = request.getParameter("LEAVE_TO_TIME");
//        String applyTypeCode = request.getParameter("LEAVE_APPLY_TYPE_CODE");//休假类型
//        if(applyTypeCode.equals("123641")||applyTypeCode.equals("123642")){//123641 半假前 9:00~14:00 半假后 14:00~18:00
//        	if(applyTypeCode.equals("123641")){
//        		fromtime=fromtime+" 09:00:00";
//            	totime=totime+" 14:00:00";
//        	}else{
//        		fromtime=fromtime+" 14:00:00";
//            	totime=totime+" 18:00:00";
//        	}
//        }else{
//        	fromtime=fromtime+" 09:00:00";
//        	totime=totime+" 18:00:00";
//        }
//        
//        String restAnnualLeaveJN = request.getParameter("restAnnualLeaveJN");
//        String restAnnualLeaveQN = request.getParameter("restAnnualLeaveQN");
//    	//喜丧日
//    	String	LikeLeaveTime =	request.getParameter("LIKELEAVE_TO_TIME") != null ? request.getParameter("LIKELEAVE_TO_TIME").toString() : "" ;	
//    	//出生日期
//    	String dobtime=request.getParameter("DOB_TIME") != null ? request.getParameter("DOB_TIME").toString() : "" ;
//    	//预产期
//    	String expectedDate=request.getParameter("EXPECTED_DATE") != null ? request.getParameter("EXPECTED_DATE").toString() : "" ;
//    	//配偶出生日期
//    	String spouseBirth=request.getParameter("SPOUSE_BIRTH") != null ? request.getParameter("SPOUSE_BIRTH").toString() : "" ;
//    	//附件  附件存放的URL
//    	String  fileUrl=request.getParameter("PROVE_FILE_URL") != null ? "/resources/temp/provefile/"+request.getParameter("PROVE_FILE_URL").toString():"";
//		//附件的名称
//    	String  fileName=request.getParameter("PROVE_FILE_NAME") != null ? request.getParameter("PROVE_FILE_NAME").toString():"";
//    	
//    	
//    	String date = new SimpleDateFormat("yyyy").format(new Date());//截取当前时间的年份
//		String fromdate = fromtime.substring(0, 5);//截取申请日期的年份
//		//------------日期类型转换----------------
//		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		Date ss = s.parse(fromtime);
//		SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		Date ss1 = s1.parse(totime);
//		
//		//原休息日
//		String wokrFromDay=request.getParameter("LEAVE_FROM_TIME") !=null ? request.getParameter("LEAVE_FROM_TIME").toString():"";
//		String wokrFromDayStart=wokrFromDay+" 09:00:00";
//    	//调休日
//		String wokrToDay=request.getParameter("LEAVE_TO_TIME");
//		String wokrToDayStart="";
//		String wokrToDayEnd="";
//		if(!wokrToDay.equals("")){
//			 wokrToDayStart=wokrToDay.substring(0,10)+" 09:00:00";
//			 wokrToDayEnd=wokrToDay.substring(0,10)+" 18:00:00";
//		}
//		//--------------比较----------------------
//		long length = ss1.getTime()-ss.getTime();//时间差（毫秒）
//		int result = (int) (length * 1.0 / (1000 * 60 * 60));//（转换成小时）
//        if( admin.getCpnyId().equals("C04") && applyTypeCode.equals("26") ){
//        	if( fromdate.equals(date+"-")  ){
//        		int j = Integer.parseInt(restAnnualLeaveJN);
//        		if( result > j){
//        			throw new CommonException(
//        			 TipMessage.getTipMessage("alert.message.ess.infoApply.nianjiashubugou",admin.getLanguage()));
//        		}
//        	}else{
//        		int q = Integer.parseInt(restAnnualLeaveQN);
//        		if( result > q){
//        			throw new CommonException(
//        			 TipMessage.getTipMessage("alert.message.ess.infoApply.qvniannianjiashubugou",admin.getLanguage()));
//        		}
//        	}
//        }
//		//-------------------------------------
//		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
//		paramMap.put("LIKELEAVE_TIME", LikeLeaveTime);
//		paramMap.put("DOB_TIME", LikeLeaveTime);
//		paramMap.put("EXPECTED_DATE", expectedDate);
//		paramMap.put("SPOUSE_BIRTH", spouseBirth);
//		if(!("/resources/temp/provefile/").equals(fileUrl)){
//			paramMap.put("FILE_URL", fileUrl);
//		}else{
//			paramMap.put("FILE_URL", "");
//		}
//		
//		paramMap.put("FILE_NAME", fileName);
//		if(applyTypeCode.equals("123646")){
//			paramMap.put("LEAVE_FROM_TIME", wokrToDayStart);
//			paramMap.put("LEAVE_TO_TIME", wokrToDayEnd);
//			paramMap.put("OLD_DAY", wokrFromDayStart);
//        }else{
//			paramMap.put("LEAVE_FROM_TIME", fromtime);
//			paramMap.put("LEAVE_TO_TIME", totime);
//        }
//		//休假验证开始
//		LinkedHashMap leaveMap = this.preAddLeaveApply(paramMap, "1", admin.getLanguage());
//		
//		infoApplyLeaveDao.addLeaveApply(leaveMap);
//		
//		return true;
//	}

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
			
			List dateList = this.infoApplyLeaveDao.getOvertimeApplyAllDateList(paramMap);
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
			infoApplyLeaveDao.addEvectionApplyList(batchOtApplyList);
		}else{
			LinkedHashMap leaveMap = this.preAddLeaveApply(paramMap, "2", admin
					.getLanguage());
			infoApplyLeaveDao.addEvectionApply(leaveMap);
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
		infoApplyLeaveDao.addEgressionApply(leaveMap);
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
	private boolean otApplyConflictWithExsitOtApply(Map paraMap)
			throws Exception {
		List list = this.infoApplyLeaveDao.getExistOtApplyDate(paraMap);
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
	 * 休假/出差/外出申请是否与已有加班申请冲突(leave/evection/egression apply conflict with exist
	 * overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean leaveApplyConflictWithExsitOtApply(Map paramMap)
			throws Exception {
		List list = this.infoApplyLeaveDao.getExistOtApplyDate(paramMap);
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
	 * 验证开始日期是否超出限制
	 * overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean leaveApplyFromTimeLeaveApplyForUpdate(Map paramMap)
			throws Exception {
		paramMap.put("FLAG", "S");//获取考勤月开始日期
		String arDateStr = this.infoApplyLeaveDao.getCurrentArDate(paramMap);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		String leaveApplyFrom = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap
					.get("LEAVE_FROM_TIME").toString()
					: sb.format(date);
		GregorianCalendar applyFrom = DateUtil
					.ParseGregorianCalendar(leaveApplyFrom);
		GregorianCalendar arDate = DateUtil
					.ParseGregorianCalendar(arDateStr);
		GregorianCalendar arDate2 = DateUtil.ParseGregorianCalendar(arDateStr);
		GregorianCalendar sysDate = DateUtil.ParseGregorianCalendar(sb.format(date));
		if("TSTO".equals(paramMap.get("CPNY_ID"))){
			arDate.add(2, -1);
		}
		
		GregorianCalendar dateCurrent = DateUtil.ParseGregorianCalendar(arDateStr);
		dateCurrent.add(2, 1);
		
		if (applyFrom.before(arDate)){
			paramMap.put("zhuisuMsg", "休假日期不能早于" + DatatypeFactory.newInstance().newXMLGregorianCalendar(arDate).toString().substring(0, 10));
			return true;
		}
		if(applyFrom.before(arDate2) && sysDate.before(dateCurrent)){ 
			paramMap.put("zhuisuMsg", "追溯休假只能在" + DatatypeFactory.newInstance().newXMLGregorianCalendar(dateCurrent).toString().substring(0, 10)+ "之后申请");
			return true;
		}
		return false;
	}
	
	/**
	 * 验证追溯休假不能跨月
	 * overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean leaveApplyToTimeLeaveApplyForUpdate(Map paramMap)
			throws Exception {
		paramMap.put("FLAG", "S");//获取考勤月开始日期
		String arStartDateStr = this.infoApplyLeaveDao.getCurrentArDate(paramMap);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
		GregorianCalendar arStartDate = DateUtil
					.ParseGregorianCalendar(arStartDateStr);
		if("TSTO".equals(paramMap.get("CPNY_ID"))){
			if (applyFrom.before(arStartDate) && applyTo.after(arStartDate) ){
				paramMap.put("zhuisuMsg", DatatypeFactory.newInstance().newXMLGregorianCalendar(arStartDate).toString().substring(0, 10));
				return true;
			}
		}
		return false;
	}
	

	/**
	 * 年假申请开始日期限制
	 * overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean getCurrentYearVacStartDateLimit(Map paramMap)
			throws Exception {
		paramMap.put("LIMIT_FLAG", "S");//获取考勤月开始日期
		paramMap.put("interCpnyID", paramMap.get("CPNY_ID"));
		String arStartDateStr = this.infoApplyLeaveDao.getCurrentYearVacLimit(paramMap);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
		GregorianCalendar arStartDate = DateUtil
					.ParseGregorianCalendar(arStartDateStr);
		if (applyFrom.before(arStartDate) ){
			paramMap.put("zhuisuMsg", DatatypeFactory.newInstance().newXMLGregorianCalendar(arStartDate).toString().substring(0, 10));
			return true;
		}
		return false;
	}
	

	/**
	 * 年假申请开始日期限制
	 * overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean getCurrentYearVacEndDateLimit(Map paramMap)
			throws Exception {
		paramMap.put("LIMIT_FLAG", "E");//获取考勤月开始日期
		paramMap.put("interCpnyID", paramMap.get("CPNY_ID"));
		String arStartDateStr = this.infoApplyLeaveDao.getCurrentYearVacLimit(paramMap);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
		GregorianCalendar arStartDate = DateUtil
					.ParseGregorianCalendar(arStartDateStr);
		if (applyTo.after(arStartDate) ){
			paramMap.put("zhuisuMsg", DatatypeFactory.newInstance().newXMLGregorianCalendar(arStartDate).toString().substring(0, 10));
			return true;
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
		List list = this.infoApplyLeaveDao.getExistLeaveDate(paramMap);

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
			List list = this.infoApplyLeaveDao.getExistLeaveDate(paramMap);

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
	private boolean getOtApplyDateWithShift(Map paramMap) {
		try {
			List list = this.infoApplyLeaveDao.getOtApplyDateWithShift(paramMap);
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
		paramValueObj = this.infoApplyLeaveDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
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
		restTiaoXiu = Double.parseDouble(this.infoApplyLeaveDao.getAdjustRestNew(paramMap).toString());
		
		String length = infoApplyLeaveDao.getLeaveApplyLength(paramMap);
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
		String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
		String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
		
		paramMap.put("empid", paramMap.get("EMPID"));
		paramMap.put("vac_id", leaveFromDate.substring(0, 4));
		paramMap.put("check", "1");
		paramMap.put("checkDate", paramMap.get("LEAVE_FROM_TIME"));
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + paramMap.get("CPNY_ID"));

		double restVac;
//		年假对应考勤项目NO 后期更改
		paramMap.put("AR_ITEM_NO","('16395','124831','124832')");
		restVac = Double.parseDouble(this.infoApplyLeaveDao.restVac(paramMap).toString());
//		ess年假NO
		paramMap.put("ESS_APPLY_NO","('26','123641','123642')");
		double applyingVac = Double.parseDouble(this.infoApplyLeaveDao.vacApplying(paramMap).toString());

		String length = infoApplyLeaveDao.getLeaveApplyLength(paramMap);
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
		restVac = Double.parseDouble(this.infoApplyLeaveDao.restVac(paramMap)
				.toString());

		double applyingVac = Double.parseDouble(this.infoApplyLeaveDao.vacApplying(
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
		return this.infoApplyLeaveDao.retrieveVacationEmpRESTQN(paramMap);
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
		
		return this.infoApplyLeaveDao.getspouseBirth(paramMap);
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
		returnString=this.infoApplyLeaveDao.getviewVacationStandard(paramMap);
		/*if(!("".equals(returnString))){
			returnString=returnString.replace("<br>","\n");
		}*/
		return returnString;
	}

	@Override
	public String getZhengce(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String apply_type = request.getParameter("apply_type");
		String cpnyid = request.getParameter("cpnyid");
		if(null == cpnyid || "".equals(cpnyid))
			map.put("cpnyid", admin.getCpnyId());
		map.put("apply_type", apply_type);
		
		String str = "";
		
		try {
			
			str = infoApplyLeaveDao.getZhengce(map) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return str;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getShenqingshichang(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String PERSON_ID = request.getParameter("PERSON_ID");
		String CPNY_ID = request.getParameter("CPNY_ID");
		String leavefromtime = request.getParameter("leavefromtime");
		String leavetotime = request.getParameter("leavetotime");
		String applyTypeCode = request.getParameter("applyTypeCode") ==null ? request.getParameter("ITEM_NO"): request.getParameter("applyTypeCode");
		String ITEM_NO = request.getParameter("ITEM_NO");
		map.put("interLanguage", admin.getLanguage());
		map.put("PERSON_ID", PERSON_ID);
		map.put("CPNY_ID", CPNY_ID);
		map.put("ITEM_NO",ITEM_NO);
		//内务考勤用
		if (!"".equals(ITEM_NO)&& ITEM_NO != null) {
			if (!"141439".equals(ITEM_NO)&&!"141440".equals(ITEM_NO)&&!"141441".equals(ITEM_NO)&&
					!"141442".equals(ITEM_NO)&&!"14013783".equals(ITEM_NO)&&!"141443".equals(ITEM_NO)) {
				 String APPLY_TYPE_CODE =   infoApplyLeaveDao.getApplyTypeCode(map);
					map.put("APPLY_TYPE_CODE", APPLY_TYPE_CODE);
			}else {
				map.put("APPLY_TYPE_CODE", applyTypeCode);
			}
		  
		}else {
			map.put("APPLY_TYPE_CODE", applyTypeCode);
		}
		map.put("leavefromtime", leavefromtime);
		map.put("leavetotime", leavetotime);
		
		String str = "";
		
		try {
			
			str = infoApplyLeaveDao.getShenqingshichang(map) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getLeaveDispalydaoxiuOrnianjMap(HttpServletRequest request) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String PERSON_ID = request.getParameter("PERSON_ID");
		String CPNY_ID = request.getParameter("CPNY_ID");
		String arDateStr = request.getParameter("arDateStr");
		
		SimpleDateFormat sdfDateFormat1=new SimpleDateFormat("yyyyMM");
	    Date AR_MONTH = sdfDateFormat1.parse(arDateStr.substring(0,4)+   arDateStr.substring(5,7));
	    Calendar calendar = Calendar.getInstance();//日历对象
	    calendar.setTime(AR_MONTH);//设置当前日期
	    calendar.add(Calendar.MONTH, 1);//月份加1
	    map.put("AR_MONTH_STR", sdfDateFormat1.format(calendar.getTime()));
		
		map.put("PERSON_ID", PERSON_ID);
		map.put("CPNY_ID", CPNY_ID);
		map.put("arDateStr", arDateStr);
		
		return infoApplyLeaveDao.getLeaveDispalydaoxiuOrnianjMap(map) ;
	}
	
	@Override
	public String getChechedAffrim(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String PK_NO = request.getParameter("PK_NO");
		map.put("PK_NO", PK_NO);
		map.put("CPNY_ID", admin.getCpnyId());
		
		String str = "";
		
		try {
			
			str = infoApplyLeaveDao.getChechedAffrim(map) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String getYesBefAffrimNo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String PERSON_ID = request.getParameter("PERSON_ID");
		map.put("PERSON_ID", PERSON_ID);
		Date d=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		map.put("APPLY_DATE", df.format(new Date(d.getTime()-2*60*60*24*1000)));
		
		String str = "";
		
		try {
			
			str = infoApplyLeaveDao.getYesBefAffrimNo(map) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int getApplyCountYN(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String PERSON_ID = request.getParameter("PERSON_ID");
		String fromDate = request.getParameter("leave_from_date");
		String toDate = request.getParameter("leave_to_date");
		String fromTime = request.getParameter("fromTime");
		String toTime = request.getParameter("toTime");
		map.put("PERSON_ID", PERSON_ID);
		map.put("LEAVE_FROM_TIME", fromDate + " " + fromTime + ":"+ "00");
		map.put("LEAVE_TO_TIME", toDate + " " + toTime + ":"+ "00");
		
		
		int str = 0;
		
		try {
			
			str = infoApplyLeaveDao.getApplyCountYN(map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int getApplyLOCKYN(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String applyBatchdate = request.getParameter("applyBatchdate");
		map.put("applyBatchdate", applyBatchdate);
		String PERSON_ID =  request.getParameter("PERSON_ID") != null ? request.getParameter("PERSON_ID") : admin.getPersonId();
		map.put("PERSON_ID", PERSON_ID);
		int str = 0;
		
		try {
			
			str = infoApplyLeaveDao.getApplyLOCKYN(map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int getApplyOTLOCKYN(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String applyBatchdate = request.getParameter("applyBatchdate");
		map.put("applyBatchdate", applyBatchdate);
		String PERSON_ID =  request.getParameter("PERSON_ID") != null ? request.getParameter("PERSON_ID") : admin.getPersonId();
		map.put("PERSON_ID", PERSON_ID);
		int str = 0;
		try {
			str = infoApplyLeaveDao.getApplyOTLOCKYN(map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	@Override
	public Object getChechedSex(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String PERSON_ID = request.getParameter("PERSON_ID");
		map.put("PERSON_ID", PERSON_ID);
		Object str = null;
		
		try {
			
			str = infoApplyLeaveDao.getChechedSex(map) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	@Override
	public Object getTrainLocalName(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String EMPID = request.getParameter("EMPID");
		map.put("EMPID", EMPID);
		Object str = null;
		
		try {
			
			str = infoApplyLeaveDao.getTrainLocalName(map) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	@Override
	public String getChechedItemName(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String itemNo = request.getParameter("ITEM_NO");
		map.put("ITEM_NO", itemNo);
		map.put("language", admin.getLanguage());
		
		String str = "";
		
		try {
			
			str = infoApplyLeaveDao.getChechedItemName(map) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	/**
	 * 封装要插入的考勤申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddApplyLeave(LinkedHashMap paramMap,String language) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		LinkedHashMap essParamMap = new LinkedHashMap();
		List distinctList = new ArrayList();
		
		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));

		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);

		List affirmerList = (ArrayList<LinkedHashMap>)paramMap.get("affirmList");
		if (affirmerList.size() == 0 ) {
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
			String leaveTimeType = paramMap.get("LEAVE_TIME_TYPE").toString();
			if(leaveTimeType!=null && "P".equals(leaveTimeType)){
				String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
				String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
				//检查申请开始时间和结束时间,如果格式不对提示用户
				this.formatFromAndToDate(leaveFromDate, leaveToDate);
			}
			boolean passFlag = false;

			passFlag = this.arApplyCheck(paramMap, personMap, language);
			// 验证
			if (passFlag){
				paramMap.put("ACTIVITY", "0");
				returnMap.put("PARAM_MAP", paramMap);
				returnMap.put("DISTINCT_LIST", distinctList);
			} else {
				throw new CommonException(TipMessage.getTipMessage(
						"alert.message.ess.infoApply.checkHasNotThrough",
						language));
			}
		}
		return returnMap;
	}
	
	/**
	 * 考勤申请检查
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean arApplyCheck(LinkedHashMap paramMap, LinkedHashMap personMap,
			String language) throws Exception {
		String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
		String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
		String leaveTimeType = paramMap.get("LEAVE_TIME_TYPE").toString();
		String length = infoApplyLeaveDao.getLeaveApplyLength(paramMap);
		double leaveLength = Double.parseDouble(length);

		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private LinkedHashMap getAttenceArDetailForPkNo(LinkedHashMap paramMap) {
		return infoApplyLeaveDao.getAttenceArDetailForPkNo(paramMap);
	}
	
	public String getArDetailItemNo(Map paramMap)
			throws Exception {
		String personId = paramMap.get("PERSON_ID") != null ? paramMap.get("PERSON_ID").toString() : "";
		paramMap.put("PERSON_ID", personId);
		paramMap.put("APPLY_DATE", paramMap.get("APPLY_TIME"));
		return this.infoApplyLeaveDao.getArDetailItemNo(paramMap);
	}
	
	/**
	 * 查询个人休假信息(get LeaveInfo ByLeave)
	 * @param request
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public Object getLeaveInfoByLeave(HttpServletRequest request) throws Exception {
		String interLanguage = request.getParameter("LANGUAGE");
		Map paramMap = null;
		if(interLanguage == null || "".equals(interLanguage)){
			paramMap = ObjectBindUtil.getRequestParamData(request);
		}else{
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("interLanguage", interLanguage);
		}
		LinkedHashMap leaveApplyMap = (LinkedHashMap)infoApplyLeaveDao.getLeaveInfoByLeave(paramMap);
		
		LinkedHashMap fileParam = new LinkedHashMap();
		fileParam.put("APPLY_TYPE", "21");
		fileParam.put("APPLY_NO", leaveApplyMap.get("APPLY_NO"));
		List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
		String fileName = "";
		String fileUrl = "";
		for(int i = 0;i<fileList.size();i++){
			LinkedHashMap fileMap = (LinkedHashMap)fileList.get(i);
			String fileUrlStr = fileMap.get("FILE_URL").toString();
			fileUrlStr = fileUrlStr.substring(fileUrlStr.lastIndexOf("/") + 1);
			if(i == 0){
				fileName = fileMap.get("FILE_NAME").toString();
				fileUrl = fileUrlStr;
			}else{
				fileName += ";" + fileMap.get("FILE_NAME").toString();
				fileUrl += ";" +  fileUrlStr;
			}
		}
		leaveApplyMap.put("FILE_NAME", fileName);
		leaveApplyMap.put("FILE_URL", fileUrl);
		leaveApplyMap.put("fileList",fileList);
		return leaveApplyMap;
	}
	
	 
	/**
	 * 查询个人加班信息 
	 * @param request
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public Object getOtApplyPersonalXiao(HttpServletRequest request) throws Exception {
		String interLanguage = request.getParameter("LANGUAGE");
		 
		 		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			paramMap.put("interLanguage", interLanguage);
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		LinkedHashMap otApplyMap = (LinkedHashMap)infoApplyLeaveDao.getOtApplyPersonalXiao(paramMap);
		
		LinkedHashMap fileParam = new LinkedHashMap();
		fileParam.put("APPLY_TYPE", "31");
		fileParam.put("APPLY_NO", otApplyMap.get("APPLY_NO"));
		List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
		String fileName = "";
		String fileUrl = "";
		for(int i = 0;i<fileList.size();i++){
			LinkedHashMap fileMap = (LinkedHashMap)fileList.get(i);
			String fileUrlStr = fileMap.get("FILE_URL").toString();
			fileUrlStr = fileUrlStr.substring(fileUrlStr.lastIndexOf("/") + 1);
			if(i == 0){
				fileName = fileMap.get("FILE_NAME").toString();
				fileUrl = fileUrlStr;
			}else{
				fileName += ";" + fileMap.get("FILE_NAME").toString();
				fileUrl += ";" +  fileUrlStr;
			}
		}
		otApplyMap.put("FILE_NAME", fileName);
		otApplyMap.put("FILE_URL", fileUrl);
		otApplyMap.put("fileList",fileList);
		return otApplyMap;
	}
	
	 
	/**
	 * 查询个人加班信息 
	 * @param request
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public Object getOtApplyPersonalIfDisplay(HttpServletRequest request) throws Exception {
		String interLanguage = request.getParameter("LANGUAGE");
		 
		 		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			paramMap.put("interLanguage", interLanguage);
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		
		
		return infoApplyLeaveDao.getOtApplyPersonalIfDisplay(paramMap);
	}
	/**
	 * 查询个人加班信息 
	 * @param request
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public Object getOtApplyPersonal(HttpServletRequest request) throws Exception {
		@SuppressWarnings("unused")
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	 
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		LinkedHashMap OtApplyPersonal = (LinkedHashMap)infoApplyLeaveDao.getOtApplyPersonal(paramMap);
		LinkedHashMap fileParam = new LinkedHashMap();
		fileParam.put("APPLY_TYPE", "31");
		fileParam.put("APPLY_NO", OtApplyPersonal.get("APPLY_NO"));
		List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
		String fileName = "";
		String fileUrl = "";
		for(int i = 0;i<fileList.size();i++){
			LinkedHashMap fileMap = (LinkedHashMap)fileList.get(i);
			String fileUrlStr = fileMap.get("FILE_URL").toString();
			fileUrlStr = fileUrlStr.substring(fileUrlStr.lastIndexOf("/") + 1);
			if(i == 0){
				fileName = fileMap.get("FILE_NAME").toString();
				fileUrl = fileUrlStr;
			}else{
				fileName += ";" + fileMap.get("FILE_NAME").toString();
				fileUrl += ";" +  fileUrlStr;
			}
		}
		OtApplyPersonal.put("FILE_NAME", fileName);
		OtApplyPersonal.put("FILE_URL", fileUrl);
		OtApplyPersonal.put("fileList",fileList);
		
		return OtApplyPersonal;
	}


	@Override
	public List getAffirmorListByLeave(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.infoApplyLeaveDao.getAffirmorListByLeave(paramMap);
	}
	
	/**
	 * 封装要插入的考勤申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddApplyLeaveForUpdate(LinkedHashMap paramMap,String language) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		LinkedHashMap essParamMap = new LinkedHashMap();
		List distinctList = new ArrayList();
		
		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));

		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);

		List affirmerList = (ArrayList<LinkedHashMap>)paramMap.get("affirmList");
		if (affirmerList.size() == 0 ) {
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
			String leaveTimeType = paramMap.get("LEAVE_TIME_TYPE").toString();
			if(leaveTimeType!=null && "P".equals(leaveTimeType)){
				String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
				String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
				//检查申请开始时间和结束时间,如果格式不对提示用户
				this.formatFromAndToDate(leaveFromDate, leaveToDate);
			}

			if("LGEPN".equals(paramMap.get("CPNY_ID")) 
					&& (!"124851".equals(paramMap.get("APPLY_TYPE_CODE")) && 
							!"4084".equals(paramMap.get("APPLY_TYPE_CODE")) && 
							!"218108".equals(paramMap.get("APPLY_TYPE_CODE"))) 
					&& "F".equals(this.isValidDate(paramMap))){
				throw new CommonException("只能申请七个工作日之内的休假。");
			}
			
			if(this.isProbation(paramMap) && !"SST".equals(paramMap.get("CPNY_ID"))){
				throw new CommonException("试用期社员不能申请年假。");
			}
			
			boolean passFlag = false;
			
			passFlag = this.arApplyCheckForUpdate(paramMap, personMap, language);
			// 验证
			if (passFlag){
				paramMap.put("ACTIVITY", "0");
				returnMap.put("PARAM_MAP", paramMap);
				returnMap.put("DISTINCT_LIST", distinctList);
			} else {
				throw new CommonException(TipMessage.getTipMessage(
						"alert.message.ess.infoApply.checkHasNotThrough",
						language));
			}
		}
		return returnMap;
	}

	/**
	 * 考勤申请检查
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean arApplyCheckForUpdate(Map paramMap, LinkedHashMap personMap,
			String language) throws Exception {
		String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
		String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
		String leaveTimeType = paramMap.get("LEAVE_TIME_TYPE").toString();
		String length = infoApplyLeaveDao.getLeaveApplyLength(paramMap);
		double leaveLength = Double.parseDouble(length);
//		暂时只检查存在开始结束时间
		if(leaveTimeType!=null ){
			// 检查申请开始时间和结束时间,如果格式不对提示用户
			this.formatFromAndToDate(leaveFromDate, leaveToDate);
			// 开始结束时间检查
			if (this.applyStartEndTimeCheck()) {
				throw new CommonException(
						TipMessage.getTipMessage("alert.message.ess.infoApply.finishDateShouldLaterThanStartDate",
										language));// alert.ess.overtime.fail_before_start
			}
			
			/*// 休假时间与之前申请加班时间检查
			if (this.leaveApplyConflictWithExsitOtApply(paramMap)) {
				throw new CommonException(
						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
								+ personMap.get("LOCAL_NAME")
								+ TipMessage.getTipMessage(
												"alert.message.ess.infoApply.leaveApplyDateConflictWithExsitOtApply",
												language));// alert.ess.overtime.fail_ot_request
			}*/
			String lastMonthFlag = infoApplyLeaveDao.arValidLastMonth(paramMap);
			if(!"OK".equals(lastMonthFlag)){
				throw new CommonException(lastMonthFlag);
			}

			//休假申请不能跨月的判断（哺乳假除外）
			if(!"16415".equals(paramMap.get("APPLY_TYPE_CODE"))){
				String arValidKuaYue = infoApplyLeaveDao.arValidKuaYue(paramMap);
				if(!"OK".equals(arValidKuaYue)){
					throw new CommonException(arValidKuaYue);
				}
			}
			// 休假开始日期检查
			/*if (this.leaveApplyFromTimeLeaveApplyForUpdate(paramMap)) {
				throw new CommonException(
						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
								+ personMap.get("LOCAL_NAME") + paramMap.get("zhuisuMsg"));
			}*/
			// 追溯加班不能跨月的判断
			if (this.leaveApplyToTimeLeaveApplyForUpdate(paramMap)) {
				throw new CommonException(
						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
								+ personMap.get("LOCAL_NAME")
								+ "追溯休假不能跨月，结束日期应小于" + paramMap.get("zhuisuMsg") + ",如有需要可以分段申请。");
			}

			// 休假时间与之前申请休假时间检查
			if (this.leaveApplyConflictWithExistLeaveApplyForUpdate(paramMap) && !"16415".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				throw new CommonException(
						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
								+ personMap.get("LOCAL_NAME")
								+ TipMessage.getTipMessage(
												"alert.message.ess.infoApply.leaveApplyDateConflictWithExsitLeaveApply",
												language));
			}

			//外出和出差不加判断
			if(!"124851".equals(paramMap.get("APPLY_TYPE_CODE")) &&
					!"4084".equals(paramMap.get("APPLY_TYPE_CODE"))){
				// 休假时长验证  最小申请时长限制
				paramMap.put("leaveLength", length);
				String valResult = this.infoApplyLeaveDao.valLeaveLength(paramMap);
				if (!"OK".equals(valResult)) {
					throw new CommonException(valResult);
				}
				//如果是产期检查假,妇女节假，青年节假 ,申请时长长度限制
				if("482".equals(paramMap.get("APPLY_TYPE_CODE")) ||
						"141474".equals(paramMap.get("APPLY_TYPE_CODE")) ||
						"141475".equals(paramMap.get("APPLY_TYPE_CODE"))){
					String valMaxResult = this.infoApplyLeaveDao.valLeaveMaxLength(paramMap);
					if (!"OK".equals(valMaxResult)) {
						throw new CommonException(valMaxResult);
					}
				}
				//哺乳假最大申请时长判断
				if("16415".equals(paramMap.get("APPLY_TYPE_CODE"))){
					if( "SST".equals(paramMap.get("CPNY_ID")) ||
						"LGEND".equals(paramMap.get("CPNY_ID")) ||
						"LGEPN".equals(paramMap.get("CPNY_ID")) ||
						"LGESY".equals(paramMap.get("CPNY_ID"))){
						String fromDate = leaveFromDate.substring(0, 10);
						String toDate = leaveToDate.substring(0, 10);
						if(!fromDate.equals(toDate)){
							throw new CommonException("哺乳假申请开始和结束日期必须同一天");
						}
						String valMaxResult = this.infoApplyLeaveDao.valLeaveMaxLength(paramMap);
						if (!"OK".equals(valMaxResult)) {
							throw new CommonException(valMaxResult);
						}
					}
				}
				// 休假时长单位验证
				paramMap.put("leaveLength", length);
				String valUtilResult = this.infoApplyLeaveDao.valLeaveLengthUtil(paramMap);
				if (!"OK".equals(valUtilResult)) {
					throw new CommonException(valUtilResult);
				}
			}
			// 如果是加班调休,18135
			if ("18135".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				double restTiaoXiu; 
				restTiaoXiu = Double.parseDouble(this.infoApplyLeaveDao.getTiaoxiu(paramMap).toString());
				
				//如果申请调休时数大于剩余调休时数
				if ( restTiaoXiu*8 < leaveLength ) {
					throw new CommonException(
							TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
									+ personMap.get("LOCAL_NAME")
									+ " 剩余调休天数不足，无法申请加班调休");
				}
				
			}

			//LGETA法人长期假别限制
			if ( "SST".equals(paramMap.get("CPNY_ID")) ) {
				//如果是病假，不能大于10天
				if ("24".equals(paramMap.get("APPLY_TYPE_CODE"))) {
					
					if ( leaveLength > 10 * 8) {
						throw new CommonException("超过10天的病假，请申请长期病假。");
					}
				}
				//如果是事假，不能大于10天
				if ("15501".equals(paramMap.get("APPLY_TYPE_CODE"))) {
					
					if ( leaveLength > 10 * 8) {
						throw new CommonException("超过10天的事假，请申请休职。");
					}
				}
			}

//			LGEYT法人产期检查假判断
			if ( "LGEYT".equals(paramMap.get("CPNY_ID")) ) {
				if ("482".equals(paramMap.get("APPLY_TYPE_CODE"))) {
					String msg = this.infoApplyLeaveDao.VALID_482_LGEYT(paramMap);
					if ( !"OK".equals(msg)) {
						throw new CommonException(msg);
					}
				}
			}
			
			//如果是长期病假，不能少于10天
			if ("15822".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				
				//长期病假不能少于10天
				if ( leaveLength < 10 * 8) {
					throw new CommonException("长期病假不能少于10天。");
				}
			}

			//如果是休职，不能少于10天
			if ("3326".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				
				//休职不能少于10天
				if ( leaveLength < 10 * 8) {
					throw new CommonException("休职不能少于10天。");
				}
			}

			//HN法人不满一年不能申请年假
			if ("LGEHN".equals(paramMap.get("CPNY_ID"))) {
				if( "26".equals(paramMap.get("APPLY_TYPE_CODE"))){
					String isFullOneYear = this.infoApplyLeaveDao.isFullOneYear(paramMap);
					if (!"OK".equals(isFullOneYear)) {
						throw new CommonException("入职不满一年不能申请年假。");
					}
				}
			}
			

			//HZ法人不满一年不能申请年假
			if ( "LGEHZ".equals(paramMap.get("CPNY_ID"))) {
				if( "26".equals(paramMap.get("APPLY_TYPE_CODE"))){
					String isFullOneYear = this.infoApplyLeaveDao.isFullOneYearYY(paramMap);
					if (!"OK".equals(isFullOneYear)) {
						throw new CommonException("入职不满一年不能申请年假。");
					}
				}
			}
			//如果是法定年假26，判断时长是否合理
			if ("26".equals(paramMap.get("APPLY_TYPE_CODE"))) {

				// 年假申请开始日期限制
				if (this.getCurrentYearVacStartDateLimit(paramMap)) {
					throw new CommonException( "年假申请开始日期应大于" + paramMap.get("zhuisuMsg") );
				}
				// 年假申请结束日期限制
				if (this.getCurrentYearVacEndDateLimit(paramMap)) {
					throw new CommonException("年假申请结束日期应小于" + paramMap.get("zhuisuMsg") );
				}
				
				if("TSTO".equals(paramMap.get("CPNY_ID") )){
					paramMap.put("YEAR", DateUtil.getCurrentYearStr());
				}else{
					paramMap.put("interCpnyID", paramMap.get("CPNY_ID"));
					paramMap.put("YEAR", this.infoApplyLeaveDao.getCurrentYear(paramMap));
				}
				double restFadingnianjia = Double.parseDouble(this.infoApplyLeaveDao.getRestFadingnianjia(paramMap).toString());
				
				//如果申请法定年假时数大于剩余法定年假时数
				if (restFadingnianjia * 8 < leaveLength) {
					throw new CommonException(
							TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
									+ personMap.get("LOCAL_NAME")
									+ "剩余年假天数不足，无法申请年假"
													);
				}
			}
		}
		return true;
	}

	/**
	 * 休假/出差/外出申请是否与已有休假/出差/外出申请冲突(leave/evection/egression apply conflict with
	 * exist leave/evection/egression apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean leaveApplyConflictWithExistLeaveApplyForUpdate(Map paramMap) {
		try {
			Date date = new Date();
			SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			List list = this.infoApplyLeaveDao.getExistLeaveDateForUpdate(paramMap);

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
			e.printStackTrace();
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
	private boolean leaveApplyAnnualCheckForUpdate(Map paramMap) throws Exception {
		String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
		String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
		
		paramMap.put("empid", paramMap.get("EMPID"));
		paramMap.put("vac_id", leaveFromDate.substring(0, 4));
		paramMap.put("check", "1");
		paramMap.put("checkDate", paramMap.get("LEAVE_FROM_TIME"));
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + paramMap.get("CPNY_ID"));

		double restVac;
//		年假对应考勤项目NO 后期更改
		paramMap.put("AR_ITEM_NO","('16395','124831','124832')");
		restVac = Double.parseDouble(this.infoApplyLeaveDao.restVac(paramMap).toString());
//		ess年假NO
		paramMap.put("ESS_APPLY_NO","('26','123641','123642')");
		double applyingVac = Double.parseDouble(this.infoApplyLeaveDao.vacApplyingForUpdate(paramMap).toString());

		String length = infoApplyLeaveDao.getLeaveApplyLength(paramMap);
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
	 * 休假申请决裁信息查询(get Leave Affirm Batch Info List)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getLeaveAffirmBatchInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("APPLY_TYPE", "BATCH");
			returnList = infoApplyLeaveDao.getLeaveAffirmInfoList(paramMap);
		return returnList;
	}

	/**
	 * 休假申请决裁信息总数(get Leave Affirm Info List Cnt)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getLeaveAffirmBatchInfoListCnt(HttpServletRequest request) throws Exception {
		int ListCnt = 0;
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("APPLY_TYPE", "BATCH");
		ListCnt =  this.infoApplyLeaveDao.getLeaveAffirmInfoListCnt(paramMap);
		
		return ListCnt;
	}

	@Override
	public List getLeaveAffirmBatchTempInfoList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		paramMap.put("APPLY_PERSON", admin.getPersonId());
//		不分页
		returnList = infoApplyLeaveDao.getLeaveAffirmBatchTempInfoList(paramMap);
		
		return returnList;
	}

	/**
	 * 休假申请决裁信息总数(get Leave Affirm Info List Cnt)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getLeaveAffirmBatchTempInfoListCnt(HttpServletRequest request) throws Exception {
		int ListCnt = 0;
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		paramMap.put("APPLY_PERSON", admin.getPersonId());
		paramMap.put("APPLY_TYPE", "BATCH");
		ListCnt =  this.infoApplyLeaveDao.getLeaveAffirmBatchTempInfoListCnt(paramMap);
		
		return ListCnt;
	}

	@Override
	public int delTempLeaveApply(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("APPLY_PERSON", admin.getPersonId());
		
		try {
			// 批量封装加班申请数据并处理
			this.infoApplyLeaveDao.delTempLeaveApply(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
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
	public int addBatchApplyLeave(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//		必须单条执行
//		List batchLeaveApplyList = new ArrayList();
		List<LinkedHashMap> returnList = this.getLeaveAffirmBatchTempInfoList(request);
		for(LinkedHashMap paramMap : returnList){
			List batchLeaveApplyList = new ArrayList();
			String person_id = paramMap.get("PERSON_ID") != null ? paramMap.get("PERSON_ID").toString() : "";
//			批量/个人申请
			String applyType= "BATCH";
			String affirmFlag = paramMap.get("AFFIRM_FLAG") != null ? paramMap.get("AFFIRM_FLAG").toString() : "0";
			String leaveType = "P";
			String applyTypeNo = paramMap.get("APPLY_TYPE_NO") != null ? paramMap.get("APPLY_TYPE_NO").toString() : "";
			String applyTypeCode = paramMap.get("LEAVE_TYPE_CODE") != null ? paramMap.get("LEAVE_TYPE_CODE").toString() : "";
//			申请日期
			String applyTime = paramMap.get("APPLY_TIME") != null ? paramMap.get("APPLY_TIME").toString() : "";
			if(applyTime==null || "".equals(applyTime)){
				applyTime = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString().split(" ")[0] : "";
			}
			String fromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
			String toDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
			String fromTime = paramMap.get("fromTime") != null ? paramMap.get("fromTime").toString() : "";
			String toTime = paramMap.get("toTime") != null ? paramMap.get("toTime").toString() : "";
//			申请时长
			String applyLength = paramMap.get("APPLY_LENGTH") != null ? paramMap.get("APPLY_LENGTH").toString() : "";
//			哺乳假类型
			String subAapplyTypeCode = paramMap.get("SUB_LEAVE_APPLY_TYPE_CODE") != null ? paramMap.get("SUB_LEAVE_APPLY_TYPE_CODE").toString() : "";
			String FILE_URL = (null != paramMap.get("file2") ? ("/resources/temp/apply/applyleave/"+person_id) : "");
			String FILE_NAME = null != paramMap.get("file2") ? paramMap.get("file2").toString() : "";

//			申请是由
			String leaveReason = paramMap.get("LEAVE_REASON") != null ? paramMap.get("LEAVE_REASON").toString() : "";
//			适用修改
			String APPLY_NO = paramMap.get("APPLY_NO") != null ? paramMap.get("APPLY_NO").toString() : "";
			String UPDATE_FLAG = paramMap.get("UPDATE_FLAG") != null ? paramMap.get("UPDATE_FLAG").toString() : "";
			String IS_UPLOAD = paramMap.get("IS_UPLOAD") != null ? paramMap.get("IS_UPLOAD").toString() : "";
			
			LinkedHashMap dateMap = new LinkedHashMap();
			dateMap = this.getLinkedMapByRequest(request, dateMap);
			dateMap.put("PERSON_ID", person_id);
			dateMap.put("APPLY_TYPE", applyType);
			dateMap.put("AFFIRM_FLAG", affirmFlag);
			dateMap.put("LEAVE_TIME_TYPE", leaveType);
			dateMap.put("APPLY_TYPE_NO", applyTypeNo);
			dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
			dateMap.put("APPLY_TIME", applyTime);
			dateMap.put("LEAVE_FROM_TIME", fromDate + fromTime + ":"+ "00");
			dateMap.put("LEAVE_TO_TIME", toDate + toTime + ":"+ "00");
			dateMap.put("APPLY_LENGTH", applyLength);
			dateMap.put("APPLY_NO", APPLY_NO);
//			哺乳假不支持批量
			if("16415".equals(applyTypeCode)){
				dateMap.put("ERROR_MSG", "哺乳假不支持批量");
				this.updateEssLeaveApplyTbTemp(dateMap);
				continue;
			}
			dateMap.put("LEAVE_REASON", leaveReason);
			dateMap.put("FILE_NAME", FILE_NAME);
			dateMap.put("FILE_URL", FILE_URL);
			dateMap.put("APPLY_NO", APPLY_NO);
			dateMap.put("UPDATE_FLAG", UPDATE_FLAG);
			dateMap.put("IS_UPLOAD", IS_UPLOAD);
			
//			默认决策者
			paramMap.put("interLanguage", admin.getLanguage());
			List affirmerList = this.getAffirmorListByMap(paramMap);
//			
//			//页面添加的决裁者列表
//			List affirmList = new ArrayList();
//			affirmList = this.preAddAffirmList(paramMap, request);
			dateMap.put("affirmList", affirmerList);
			//插入数据库之前的数据的整理、验证，主要是决裁者与时间的验证，然后封装起来

			LinkedHashMap leaveMap = new LinkedHashMap();
			boolean flag = false;
			flag = this.preAddApplyLeaveBatch(leaveMap, dateMap, admin.getLanguage());
			if(!flag)
				continue;
			leaveMap.put("APPLY_NO", APPLY_NO);
			batchLeaveApplyList.add(leaveMap);
			
			this.infoApplyLeaveDao.addLeaveApplyInBatchNew(batchLeaveApplyList);
		}
		
		return 1;
	}

	private void updateEssLeaveApplyTbTemp(LinkedHashMap dateMap) {
		
		try {
			this.infoApplyLeaveDao.updateEssLeaveApplyTbTemp(dateMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 封装要插入的考勤申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private boolean preAddApplyLeaveBatch(LinkedHashMap returnMap, LinkedHashMap paramMap,String language) throws Exception {
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
		paramValueObj = this.infoApplyLeaveDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedAffirm = paramValueObj != null ? paramValueObj.toString() : "0";
		essParamMap.put("ESS_PARAM_NO", "4160");
		paramValueObj = this.infoApplyLeaveDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
		ifNeedConfirm = paramValueObj != null ? paramValueObj.toString() : "0";

		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);

//		String leaveFromDate = "";
//		String leaveToDate = "";
//		休假、出差、外出不做区分
//		if ("1".equals(leaveType)) {
//			leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
//			leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
//			paramMap.put("FROM_TIME", paramMap.get("LEAVE_FROM_TIME"));
//			paramMap.put("TO_TIME", paramMap.get("LEAVE_TO_TIME"));
//			paramMap.put("APPLY_TYPE_NO", paramMap.get("APPLY_TYPE_NO"));
//			paramMap.put("APPLY_TYPE_CODE", paramMap.get("LEAVE_APPLY_TYPE_CODE"));
//			paramMap.put("APPLY_DATE", paramMap.get("LEAVE_FROM_TIME").toString().substring(0, 10));
//		} else if ("2".equals(leaveType)) {
//			leaveFromDate = paramMap.get("EVE_FROM_TIME") != null ? paramMap.get("EVE_FROM_TIME").toString() : "";
//			leaveToDate = paramMap.get("EVE_TO_TIME") != null ? paramMap.get("EVE_TO_TIME").toString() : "";
//			paramMap.put("FROM_TIME", paramMap.get("EVE_FROM_TIME"));
//			paramMap.put("TO_TIME", paramMap.get("EVE_TO_TIME"));
//			paramMap.put("APPLY_TYPE_NO", paramMap.get("EVE_APPLY_TYPE_NO"));
//			paramMap.put("APPLY_TYPE_CODE", paramMap.get("EVE_APPLY_TYPE_CODE"));
//			paramMap.put("APPLY_DATE", paramMap.get("EVE_FROM_TIME").toString().substring(0, 10));
//		} else {
//			leaveFromDate = paramMap.get("EG_FROM_TIME") != null ? paramMap.get("EG_FROM_TIME").toString() : "";
//			leaveToDate = paramMap.get("EG_TO_TIME") != null ? paramMap.get("EG_TO_TIME").toString() : "";
//			paramMap.put("FROM_TIME", paramMap.get("EG_FROM_TIME"));
//			paramMap.put("TO_TIME", paramMap.get("EG_TO_TIME"));
//			paramMap.put("APPLY_TYPE_NO", paramMap.get("EG_APPLY_TYPE_NO"));
//			paramMap.put("APPLY_TYPE_CODE", paramMap.get("EG_APPLY_TYPE_CODE"));
//			paramMap.put("APPLY_DATE", paramMap.get("EG_FROM_TIME").toString().substring(0, 10));
//		}
//		List affirmerList = this.getAffirmorListByMap(paramMap);
		List affirmerList = (ArrayList<LinkedHashMap>)paramMap.get("affirmList");
		if (affirmerList.size() == 0 && "1".equals(ifNeedAffirm)) {
			// 未给该员工设置决裁者时
//			throw new CommonException(
//					TipMessage.getTipMessage("alert.message.ess.infoApply.notFor", language)
//							+ personMap.get("LOCAL_NAME")
//							+ TipMessage.getTipMessage("alert.message.ess.infoApply.pleaseSetAffirmorFirst",
//											language));// alert.ess.approval.no_approver
			paramMap.put("ERROR_MSG", TipMessage.getTipMessage("alert.message.ess.infoApply.pleaseSetAffirmorFirst",
					language));
			this.updateEssLeaveApplyTbTemp(paramMap);
			return false;
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
			String leaveTimeType = paramMap.get("LEAVE_TIME_TYPE").toString();
			if(leaveTimeType!=null && "P".equals(leaveTimeType)){
				String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
				String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
				//检查申请开始时间和结束时间,如果格式不对提示用户
				this.formatFromAndToDate(leaveFromDate, leaveToDate);
			}
			// 检查申请开始时间和结束时间,如果格式不对提示用户
//			this.formatFromAndToDate(leaveFromDate, leaveToDate);
			boolean passFlag = false;
			// "1"表示校验休假;其他校验出差和外出
//			if ("1".equals(leaveType)) {
//				passFlag = this.arApplyCheck(paramMap, personMap, language);
//			} else {
//				passFlag = this.otherLeaveApplyCheck(paramMap, personMap,language);
//			}
			// otherLeaveApplyCheck
			passFlag = this.arApplyCheckBatch(paramMap, personMap, language);
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
//					throw new CommonException(
//							TipMessage.getTipMessage("alert.message.ess.infoApply.otApplyShouldAffirmOrConfirm",
//											language));// alert.ess.approval.no_approver
					paramMap.put("ERROR_MSG", TipMessage.getTipMessage("alert.message.ess.infoApply.otApplyShouldAffirmOrConfirm",
							language));
					this.updateEssLeaveApplyTbTemp(paramMap);
					return false;
				}
			} else {
//				throw new CommonException(TipMessage.getTipMessage(
//						"alert.message.ess.infoApply.checkHasNotThrough",
//						language));// alert.ess.overtime.fail_before_start
//				paramMap.put("ERROR_MSG", TipMessage.getTipMessage(
//						"alert.message.ess.infoApply.checkHasNotThrough",
//						language));
//				this.updateEssLeaveApplyTbTemp(paramMap);
				return false;
			}
		}
		return true;
	}

	/**
	 * 考勤申请检查
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean arApplyCheckBatch(LinkedHashMap paramMap, LinkedHashMap personMap,
			String language) throws Exception {
		String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
		String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
		String leaveTimeType = paramMap.get("LEAVE_TIME_TYPE").toString();
		String APPLY_NO =  paramMap.get("APPLY_NO").toString();
		String AFFIRM_FLAG = paramMap.get("AFFIRM_FLAG").toString();
		String APPLY_LENGTH = paramMap.get("APPLY_LENGTH").toString();
		String ITEM_NO = paramMap.get("ITEM_NO").toString();
		
//		暂时只检查存在开始结束时间
		if(leaveTimeType!=null && "P".equals(leaveTimeType)){
			// 检查申请开始时间和结束时间,如果格式不对提示用户
			this.formatFromAndToDate(leaveFromDate, leaveToDate);
			// 开始结束时间检查
			if (this.applyStartEndTimeCheck()) {
//				throw new CommonException(
//						TipMessage.getTipMessage("alert.message.ess.infoApply.finishDateShouldLaterThanStartDate",
//										language));// alert.ess.overtime.fail_before_start
				paramMap.put("ERROR_MSG", TipMessage.getTipMessage("alert.message.ess.infoApply.finishDateShouldLaterThanStartDate",
						language));
				this.updateEssLeaveApplyTbTemp(paramMap);
				return false;
			}
			
			// 休假时间与之前申请加班时间检查
			if (this.leaveApplyConflictWithExsitOtApply(paramMap)) {
//				throw new CommonException(
//						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
//								+ personMap.get("LOCAL_NAME")
//								+ TipMessage.getTipMessage(
//												"alert.message.ess.infoApply.leaveApplyDateConflictWithExsitOtApply",
//												language));// alert.ess.overtime.fail_ot_request
				paramMap.put("ERROR_MSG", TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
						+ personMap.get("LOCAL_NAME")
						+ TipMessage.getTipMessage(
										"alert.message.ess.infoApply.leaveApplyDateConflictWithExsitOtApply",
										language));
				this.updateEssLeaveApplyTbTemp(paramMap);
				return false;
			}
			
			// 休假时间与之前申请休假时间检查
			if (this.leaveApplyConflictWithExistLeaveApply(paramMap)) {
//				throw new CommonException(
//						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
//								+ personMap.get("LOCAL_NAME")
//								+ TipMessage.getTipMessage(
//												"alert.message.ess.infoApply.leaveApplyDateConflictWithExsitLeaveApply",
//												language));
				paramMap.put("ERROR_MSG", TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
						+ personMap.get("LOCAL_NAME")
						+ TipMessage.getTipMessage(
										"alert.message.ess.infoApply.leaveApplyDateConflictWithExsitLeaveApply",
										language));
				this.updateEssLeaveApplyTbTemp(paramMap);
				return false;
			}
			// 如果是年假申请,检查年假数 26 
//			if ("26".equals(paramMap.get("APPLY_TYPE_CODE"))||"123641".equals(paramMap.get("APPLY_TYPE_CODE"))||"123642".equals(paramMap.get("APPLY_TYPE_CODE"))) {
			if ("26".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				if (this.leaveApplyAnnualCheck(paramMap)) {
//					throw new CommonException(
//							TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
//									+ personMap.get("LOCAL_NAME")
//									+ TipMessage.getTipMessage(//剩余年假时数不够，故不能申请年假！
//													"alert.message.ess.infoApply.noEnoughAnnualCannotApplyAnnualLeave",
//													language));
					paramMap.put("ERROR_MSG", TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
							+ personMap.get("LOCAL_NAME")
							+ TipMessage.getTipMessage(//剩余年假时数不够，故不能申请年假！
											"alert.message.ess.infoApply.noEnoughAnnualCannotApplyAnnualLeave",
											language));
					this.updateEssLeaveApplyTbTemp(paramMap);
					return false;
				}
			}
		}
		
		// 如果是调休申请,检查剩余调休时间长度
		//APPLY_TYPE_CODE  =123646  调休   以前是18135     取消现在的验证：99999999
//		暂不检查调休
//		if ("99999999".equals(paramMap.get("APPLY_TYPE_CODE"))) {
//			if (this.leaveApplyTiaoXiuCheck(paramMap)) {
//				throw new CommonException(
//						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
//								+ personMap.get("LOCAL_NAME")
//								+ TipMessage.getTipMessage(//剩余调休时数不够，故不能申请调休！
//												"alert.message.ess.infoApply.noEnoughTiaoXiuCannotApplyAnnualLeave",
//												language));
//			}
//		}
		
//		LinkedHashMap essParamMap = new LinkedHashMap();
//		Object paramValueObj = null;
//		String paramValue = "";
//		
//		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
		// 加班申请是否在可以申请日期上限范围内
//		暂不检查
//		essParamMap.put("ESS_PARAM_NO", "4165");// 4165有年假是否能申请事假 1为能申请;0为不能申请
//		paramValueObj = this.infoApplyLeaveDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
//		paramValue = paramValueObj != null ? paramValueObj.toString() : "-1";
//		// 有年假不能申请事假
//		if ("0".equals(paramValue)) {
//			// 如果有年假,则不能申请事假 15501为事假的国际化CODE
//			if ("15501".equals(paramMap.get("APPLY_TYPE_CODE"))||"24".equals(paramMap.get("APPLY_TYPE_CODE"))) {
//				if (this.ifHaveAnnualCheck(paramMap)) {
//					throw new CommonException(
//							TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
//									+ personMap.get("LOCAL_NAME")
//									+ TipMessage.getTipMessage(
//													"alert.message.ess.infoApply.haveAnnualCannotApplyPersonalLeave",
//													language));// alert.ess.dayoff.fail_vacation_days
//				}
//			}
//		}
		return true;
	}
	
	
	/**
	 * 内务考勤申请检查
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean arApplyCheckBatch2(LinkedHashMap paramMap, LinkedHashMap personMap,
			String language) throws Exception {
		String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
		String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
		String FROM_DATE = paramMap.get("FROM_DATE") != null ? paramMap.get("FROM_DATE").toString() : "";
		String TO_DATE = paramMap.get("TO_DATE") != null ? paramMap.get("TO_DATE").toString() : "";
		String OLD_TO_DATE = paramMap.get("OLD_TO_DATE") != null ? paramMap.get("OLD_TO_DATE").toString() : "";
		String APPLY_LENGTH =paramMap.get("APPLY_LENGTH")!= null ?  paramMap.get("APPLY_LENGTH").toString():"0";
		String CPNY_ID = paramMap.get("CPNY_ID")!= null ? paramMap.get("CPNY_ID").toString():"";
		String leaveTimeType = paramMap.get("LEAVE_TIME_TYPE")!= null ? paramMap.get("LEAVE_TIME_TYPE").toString():"";
			// 检查申请开始时间和结束时间,如果格式不对提示用户
			this.formatFromAndToDate(leaveFromDate, leaveToDate);
			// 休假时间与之前申请加班时间检查
			if (this.leaveApplyConflictWithExsitOtApply(paramMap)) {
				paramMap.put("ERROR_MSG", TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
						+ personMap.get("LOCAL_NAME")
						+ TipMessage.getTipMessage(
										"alert.message.ess.infoApply.leaveApplyDateConflictWithExsitOtApply",
										language));
				return false;
			}
			
			//休假时间与之前已申请的休假时间的之间的验证
			
			if("TSTO".equals(CPNY_ID)){
			   if (FROM_DATE.equals(TO_DATE) ) {
					LinkedHashMap checkMap = new LinkedHashMap();
					checkMap.put("LEAVE_FROM_TIME", paramMap.get("FROM_DATE"));
					checkMap.put("LEAVE_TO_TIME", paramMap.get("TO_DATE"));
					checkMap.put("fromTime", paramMap.get("fromTime"));
					checkMap.put("toTime", paramMap.get("toTime"));
					checkMap.put("PERSON_ID", paramMap.get("PERSON_ID"));
					checkMap.put("APPLY_TYPE_CODE", paramMap.get("ITEM_NO"));
					checkMap.put("APPLY_LENGTH", APPLY_LENGTH);
					checkMap.put("PK_NO",paramMap.get("APPLY_NO"));
					String result = this.infoApplyLeaveDao.getLeaveCheckTSTO(checkMap);
					if ( !"OK".equals(result) ) {
						paramMap.put("ERROR_MSG",result);
						return false;
					}
			   }
			}
			
			if (Float.parseFloat(APPLY_LENGTH)>8 && "HOUR".equals(paramMap.get("UNIT"))) {
				if (Float.parseFloat(APPLY_LENGTH)%8 != 0) {
					paramMap.put("ERROR_MSG", personMap.get("LOCAL_NAME")+"做多天假申请的情况下,不能有半天的,只能做整数天的申请");
					return false;
				}
			}
			
			
			
			/*//当前累计申请假期不允许超过8小时
				if(OLD_TO_DATE.equals(TO_DATE)) {
					List arList = this.getDayArData(paramMap) ; //获取当天考勤状态
					double submitLentht = 0.0;//定义申请的时长，获取到分钟数
					double  appliedCount = 0.0; //已申请的时长
					if("HOUR".equals(paramMap.get("UNIT"))){
						submitLentht = Double.parseDouble(APPLY_LENGTH)* 60;
					}else {
						submitLentht = Double.parseDouble(APPLY_LENGTH);
					}
					//累计获取已申请的考勤时长
					if (arList.size() != 0) {
						for (Iterator iterator = arList.iterator(); iterator.hasNext();) {
							LinkedHashMap arMap = (LinkedHashMap) iterator.next();
							double  timeing = 0.0; //已申请的时长
							//获取已申请考勤的累计加班时长
							if ("HOUR".equals(arMap.get("UNIT").toString())) {
								timeing = Double.parseDouble(arMap.get("QUANTITY").toString())*60;
							}else {
								timeing = Double.parseDouble(arMap.get("QUANTITY").toString());
							}
					   appliedCount += timeing;//获取到当天累计的时长分钟数
						}
					}
					if (arList.size() != 0) { 
						if (submitLentht + appliedCount > 8*60) {
							paramMap.put("ERROR_MSG", personMap.get("LOCAL_NAME")+"当天累计申请的休假时长超出了8小时,重新选择申请时长");
							return false;
						}
				    }
		        }*/
		/*	//当前累计申请假期不允许超过8小时
			if(OLD_TO_DATE.equals(TO_DATE)) {
				String OLD_ITEM_NO = this.getOldItemNo(paramMap) ; //获取当天考勤状态
				if (OLD_ITEM_NO !=null &&!"".equals(OLD_ITEM_NO)) {
					if(FROM_DATE.equals(TO_DATE)&& !OLD_ITEM_NO.equals(paramMap.get("ITEM_NO"))&&!"".equals(OLD_ITEM_NO)&& null!=OLD_ITEM_NO){//说明做的是同一天的申请
						if (Float.parseFloat(APPLY_LENGTH) <= 8 && "HOUR".equals(paramMap.get("UNIT"))) {
							//获取已做申请的分钟数
							int  appliedCount = this.getAppliedCount(paramMap);
							if (appliedCount != 0) {
								int  appliedHour = this.getAppliedHour(paramMap);
								if ((Float.parseFloat(APPLY_LENGTH)* 60 + appliedHour) > 8*60) {
									paramMap.put("ERROR_MSG", personMap.get("LOCAL_NAME")+"当天累计申请的休假时长超出了8小时,重新选择申请时长");
									return false;
								}
							}
							
						}
						if (Float.parseFloat(APPLY_LENGTH) <= 8*60 && "MINUTE".equals(paramMap.get("UNIT"))) {
							//获取已做申请的分钟数
							int  appliedCount = this.getAppliedCount(paramMap);
							if (appliedCount != 0) {
								int  appliedHour = this.getAppliedHour(paramMap);
								if ((Float.parseFloat(APPLY_LENGTH) + appliedHour) > 8*60) {
									paramMap.put("ERROR_MSG", personMap.get("LOCAL_NAME")+"当天累计申请的休假时长超出了8小时,重新选择申请时长");
									return false;
								}
							}
							
						}
					}
				}*/
	      
			// 休假时间与之前申请休假时间检查
			if (this.leaveApplyConflictWithExistLeaveApply(paramMap)) {
				paramMap.put("ERROR_MSG", TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
						+ personMap.get("LOCAL_NAME")
						+ TipMessage.getTipMessage(
										"alert.message.ess.infoApply.leaveApplyDateConflictWithExsitLeaveApply",
										language));
				
				return false;
			}
				// 如果是年假申请,检查年假数 14013814
			if ("14013814".equals(paramMap.get("APPLY_TYPE_CODE"))) {
					
				if (Float.parseFloat(APPLY_LENGTH) % 4 != 0  ) {
					paramMap.put("ERROR_MSG", personMap.get("LOCAL_NAME")+"申请的年假不是4小时的整数倍,请重新做申请");
						return false;
				}    
			}
			//事假累计不能超过21天  事假ID:18135
			int  affairsHour = this.getAllYearaffairsHour(paramMap);
			
			if ("18135".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				
				if (affairsHour>168) {
					paramMap.put("ERROR_MSG", personMap.get("LOCAL_NAME")+"申请的事假天数超过了21天,不允许再做事假申请");
						return false;
				}    
			}
		return true;
	}

	private int getAllYearaffairsHour(LinkedHashMap paramMap) throws Exception {
		return  infoApplyLeaveDao.getAllYearaffairsHour(paramMap);
	}
	private int getAppliedHour(LinkedHashMap paramMap) throws Exception {
		return  infoApplyLeaveDao.getAppliedHour(paramMap);
	}
	private int getAppliedCount(LinkedHashMap paramMap) throws Exception {
		return  infoApplyLeaveDao.getAppliedCount(paramMap);
	}
	private String  getOldItemNo(LinkedHashMap paramMap) throws Exception {
		return  infoApplyLeaveDao.getOldItemNo(paramMap);
	}
	private List  getDayArData(LinkedHashMap paramMap) throws Exception {
		return  infoApplyLeaveDao.getDayArData(paramMap);
	}

	/**
	 * 批量删除休假申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
/*	@SuppressWarnings("unchecked")
	public int delLeaveApplyInBatchForBatch(HttpServletRequest request)
			throws CommonException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap personMap = new LinkedHashMap();
		
		LinkedHashMap map = new LinkedHashMap();
		LinkedHashMap mapAffMap = new LinkedHashMap();
		List list =new ArrayList();
		List deleteList =new ArrayList();
		List affirmList = new ArrayList();
		personMap.put("APPLY_PERSON", admin.getPersonId());
		String op_flag = request.getParameter("OP_FLAG");
		String applyBatchdate = request.getParameter("applyBatchdate");//搜索日期
		try {
			if (!"2".equals(op_flag)&&!"0".equals(op_flag)) {
				 list = this.encapsulationApplyAttdanceNoListForBatch(request,"BATCH_LEAVE");
			}
			if("0".equals(op_flag)){//删除
				deleteList = this.encapsulationApplyAttdanceNoListForBatchDelete(request,"BATCH_LEAVE");
				this.infoApplyLeaveDao.delLeaveApplyInBatch(deleteList, personMap);
			}else if ("2".equals(op_flag)) {
				map.put("CPNY_ID",admin.getCpnyId());
				map.put("CREATED_BY",admin.getPersonId());
				map.put("CREATED_IP",admin.getAdminIP());
				map.put("applyBatchdate",applyBatchdate);
				this.infoApplyLeaveDao.addtLeaveApplyInBatch(map);
			}else{//提交
				for(int i=0;i<list.size();i++){
					LinkedHashMap paramMap = (LinkedHashMap) list.get(i);
				    personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);
				    
				    String PK_NO = (String) paramMap.get("APPLY_NO");
					paramMap.put("PK_NO", PK_NO);
					String oldAffrim = infoApplyLeaveDao.getChechedAffrim(paramMap);
					String ITEM_NO =paramMap.get("ITEM_NO")!= null ? paramMap.get("ITEM_NO").toString():"";
					boolean passFlag = false;
					//内务考勤申请检查
					passFlag = this.arApplyCheckBatch2(paramMap, personMap, admin.getLanguage());
				  //根据审批状态判定进行不同的操作
				 //开始:14014306 部门申请: 14014307部门长批准 ：14014308部门长返回:14014309上申取消:14014310部门申请(后):14014311部门长批准(后):14014312
				   if (passFlag==true) {
				   if ("14014306".equals(oldAffrim)) {
					   String personIdIsNull= infoApplyLeaveDao.getChechedPersonidIsNull(paramMap);
					   int personNum = infoApplyLeaveDao.getCheckPersonCount(paramMap);
						 //如果是初始的话需要插入（休息(141440)、迟到(141441)、早退(141442)、旷工(141443)、厂车迟到(14013783))
							   if ("141440".equals(ITEM_NO)||"141441".equals(ITEM_NO)||"141442".equals(ITEM_NO)||"141443".equals(ITEM_NO)||"14013783".equals(ITEM_NO)) {
								   if ( !"NULL".equals(personIdIsNull)) {
										   this.infoApplyLeaveDao.updatSubmitLeaveApplyInBatchForDeletePrepare(paramMap); //修改最初的数据，为删除做准备
								           this.infoApplyLeaveDao.insertSubmitLeaveApplyInBatch(paramMap); //插入新的申请数据到ar_detail表中
									} else {
										if (personNum==0) {
										   this.infoApplyLeaveDao.updatSubmitArDetailBatchForNullPersonId(paramMap);//修改detail表
										}else {
										    this.infoApplyLeaveDao.updatSubmitLeaveApplyInBatchForDeletePrepareOnPersonid(paramMap); //根据人      修改最初的数据，为删除做准备
											this.infoApplyLeaveDao.updatSubmitArDetailBatchForNullPersonId(paramMap);//修改detail表
										}
									}
								  
								}else {
									if (!"NULL".equals(personIdIsNull)) {
										 //如果是初始的话需要插入（除去休息(141440)、迟到(141441)、早退(141442)、旷工(141443)、厂车迟到(14013783))
										   this.infoApplyLeaveDao.addLeaveApplyInArDetailBatch(paramMap);//给申请表出入一条数据
										   this.infoApplyLeaveDao.updatSubmitLeaveApplyInBatchForDeletePrepare(paramMap); //修改最初的数据，为删除做准备
								           this.infoApplyLeaveDao.insertSubmitLeaveApplyInBatch(paramMap); //插入新的申请数据到ar_detail表中
									} else {
										    //添加的时候判断新加的表中是否存在正常出勤，如果存在就得删除
										if (personNum==0) {//不存在正常出勤
										    this.infoApplyLeaveDao.addLeaveApplyInArDetailBatch(paramMap);//给申请表插入一条数据
											this.infoApplyLeaveDao.updatSubmitArDetailBatchForNullPersonId(paramMap);//修改detail表
										}else {
											this.infoApplyLeaveDao.addLeaveApplyInArDetailBatch(paramMap);//给申请表出入一条数据
											this.infoApplyLeaveDao.updatSubmitLeaveApplyInBatchForDeletePrepareOnPersonid(paramMap); //修改最初的数据，为删除做准备
											this.infoApplyLeaveDao.updatSubmitArDetailBatchForNullPersonId(paramMap);//修改detail表
										}
									}
								}
							
						}else  {
								//如果申请表已存在的数据只需要修改就行
							  if ("141440".equals(ITEM_NO)||"141441".equals(ITEM_NO)||"141442".equals(ITEM_NO)||"141443".equals(ITEM_NO)||"14013783".equals(ITEM_NO)) {
								   this.infoApplyLeaveDao.updatSubmitArDetailBatchForTwoApply(paramMap);//修改detail表
							   }else {
								   this.infoApplyLeaveDao.updatSubmitArDetailBatchForTwoApply(paramMap);//修改detail表
								   this.infoApplyLeaveDao.updateLeaveApplyInArDetailBatchForTwoApply(paramMap);//修改申请表
							   }     
						}
				      //TSTO单申请倒休的时候，修改倒休标识
				       if ("TSTO".equals(admin.getCpnyId())) {
						   if ("14013845".equals(ITEM_NO)) {
							  String OLD_ADJUST_FLAG = infoApplyLeaveDao.getOldAdjustFlag(paramMap);
							 if (paramMap.get("ajQUANTITY").toString().equals(paramMap.get("APPLY_LENGTH").toString())) {
								 paramMap.put("ADJUST_FLAG", paramMap.get("ACTIVITY"));
							 }else if(!paramMap.get("ajQUANTITY").toString().equals(paramMap.get("APPLY_LENGTH").toString())&&"2".equals(OLD_ADJUST_FLAG)) {
								 paramMap.put("ADJUST_FLAG", paramMap.get("ACTIVITY"));
							 }else {
								 paramMap.put("ADJUST_FLAG", 2);
							 }
							 this.infoApplyLeaveDao.updatAdjustFlag(paramMap);//修改detail表
						   }
					   }
				   }else {
					     String ERROR_MSG = (String) paramMap.get("ERROR_MSG");
						 throw new CommonException(ERROR_MSG);
					}	
				   //拆分多天的假期,满足开始和结束的日期不同，同时申请时长大于15小时才是多天的假
						 this.infoApplyLeaveDao.insertSubmitLeaveApplyInBatchForMoreDay(paramMap);//拆分多天的
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
	*/
	/**
	 * 获取休假导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssLeaveTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = infoApplyLeaveDao.getEssLeaveTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = infoApplyLeaveDao.getEssLeaveTempList(paramMap);
		}
		return retrunList;
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
	public int getEssLeaveTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = infoApplyLeaveDao.getEssLeaveTempErrorCnt(paramMap);
		}else{
			retrunInt = infoApplyLeaveDao.getEssLeaveTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	/**
	 * 休假批量申请excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelEssLeaveEmpData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if("ess0240".equals(StringUtil.checkNull(paramMap.get("LEAVE_TYPE")))){
			paramMap.put("PR_NAME", "pkg_ess_leave_excel_imp.pr_import_ass_leave_data");
		}else{
			paramMap.put("PR_NAME", "pkg_ess_leave_excel_imp.pr_import_ess_leave_data");
		}
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * 获取员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfo (HttpServletRequest request) throws Exception {
		Map param = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(param.get("PERSON_ID") == null){
			param.put("PERSON_ID", admin.getPersonId());
		}
		if("TSTO".equals(admin.getCpnyId() )){
			param.put("YEAR", DateUtil.getCurrentYearStr());
		}else{
			param.put("YEAR", this.infoApplyLeaveDao.getCurrentYear(param));
		}
		param.put("APPLY_NO", param.get("APPLY_NO") == null ? "" : param.get("APPLY_NO"));
		return infoApplyLeaveDao.getEmpVacInfo(param);
	}

	/**
	 * 检查调休是否超过
	 * 
	 * @param Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private boolean leaveApplyTXCheck(Map paramMap) throws Exception {
//		String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
//		String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
//		String APPLY_NO = paramMap.get("APPLY_NO") != null ? paramMap.get("APPLY_NO").toString() : "";
		
		double restTiaoXiu;
		restTiaoXiu = Double.parseDouble(this.infoApplyLeaveDao.getTiaoxiu(paramMap).toString());
		
		String length = infoApplyLeaveDao.getLeaveApplyLength(paramMap);
		double leaveLength = Double.parseDouble(length);
		
		//如果申请调休时数大于剩余调休时数
		if ( restTiaoXiu < leaveLength ) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查调休是否超过
	 * 
	 * @param Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private boolean yiniannianjiaCheck(Map paramMap) throws Exception {

		double restyinian;
		restyinian = Double.parseDouble(this.infoApplyLeaveDao.getYiniannianjia(paramMap).toString());
		
		//如果移年年假>0
		if ( restyinian > 0 ) {
			return true;
		}
		return false;
	}

	/**
	 * 检查申请的福利年假数是否超过剩余数
	 * 
	 * @param Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private boolean leaveApplyFulinianjiaCheck(Map paramMap) throws Exception {
//		String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
//		String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
//		String APPLY_NO = paramMap.get("APPLY_NO") != null ? paramMap.get("APPLY_NO").toString() : "";
		
		double restfulinianjia;
		restfulinianjia = Double.parseDouble(this.infoApplyLeaveDao.getRestFulinianjia(paramMap).toString());
		
		String length = infoApplyLeaveDao.getLeaveApplyLength(paramMap);
		double leaveLength = Double.parseDouble(length);
		
		//如果申请调休时数大于剩余调休时数
		if ( restfulinianjia < leaveLength ) {
			return true;
		}
		return false;
	}
	
	/**
	 * 休假批量申请详细信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getLeaveBatchAffirmInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		String apply_type = request.getParameter("unDoApplyNo");
		if(apply_type != null && !"".equals(apply_type)){
			paramMap.put("APPLY_NO",  apply_type);
		}else{
			paramMap.put("APPLY_NO",  request.getParameter("APPLY_NO"));
		}
		if(admin == null){
			paramMap.put("LANGUAGE",  request.getParameter("LANGUAGE"));
		}else {
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		if (UiUtil.getPageNum(request) > 0) {
			returnList = infoApplyLeaveDao.getLeaveBatchAffirmInfoList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = infoApplyLeaveDao.getLeaveBatchAffirmInfoList(paramMap);
		}
		if(returnList != null && returnList.size() > 0){
			for(int i=0;i<returnList.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)returnList.get(i);
				returnMap.put("APPLY_TYPE", "21");
				returnMap.put("APPLY_NO", returnMap.get("BATCH_APPLY_NO"));
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		return returnList;
	}
	
	/**
	 * 加班批量申请详细信息查询(search ot info list)
	 * by:wangqiang
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtBatchAffirmInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		String  str = request.getParameter("APPLY_NO")==null?request.getParameter("seach_APPLY_NO"):request.getParameter("APPLY_NO");
		paramMap.put("APPLY_NO",  str);
		if(admin == null){
			paramMap.put("LANGUAGE",  request.getParameter("LANGUAGE"));
		}else {
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		if (UiUtil.getPageNum(request) > 0) {
			returnList = infoApplyLeaveDao.getOtBatchAffirmInfoList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = infoApplyLeaveDao.getOtBatchAffirmInfoList(paramMap);
		}
		if(returnList != null && returnList.size() > 0){
			for(int i=0;i<returnList.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)returnList.get(i);
				returnMap.put("APPLY_TYPE", "31");
				returnMap.put("APPLY_NO", returnMap.get("BATCH_APPLY_NO"));
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		return returnList;
	}
	
	/**
	 * 休假批量申请详细信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getLeaveBatchAffirmInfoCnt(HttpServletRequest request){
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();

		String apply_type = request.getParameter("unDoApplyNo");
		if(apply_type != null && !"".equals(apply_type)){
			paramMap.put("APPLY_NO",  apply_type);
		}else{
			paramMap.put("APPLY_NO",  request.getParameter("APPLY_NO"));
		}
		if(admin == null){
			paramMap.put("LANGUAGE",  request.getParameter("LANGUAGE"));
		}else {
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		try {
			retrunInt = infoApplyLeaveDao.getLeaveBatchAffirmInfoListCnt(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retrunInt;
	}
	/**
	 * 加班批量申请详细信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wangqiang@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public int getOtBatchAffirmInfoCnt(HttpServletRequest request){
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		 
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		String str = request.getParameter("APPLY_NO")==null?request.getParameter("seach_APPLY_NO"):request.getParameter("APPLY_NO");
		paramMap.put("APPLY_NO",  str);
		if(admin == null){
			paramMap.put("LANGUAGE",  request.getParameter("LANGUAGE"));
		}else {
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		try {
			retrunInt = infoApplyLeaveDao.getOtBatchAffirmInfoListCnt(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retrunInt;
	}
	
	/**
	 * 获取员工性别
	 * @param request
	 * @return
	 */
	public String getSex(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = new LinkedHashMap() ;
		String PERSON_ID = request.getParameter("PERSON_ID");
		String CPNY_ID = request.getParameter("CPNY_ID");
		String leavefromtime = request.getParameter("leavefromtime");
		String leavetotime = request.getParameter("leavetotime");

		String apply_type = request.getParameter("apply_type");
		if("141475".equals(apply_type)){
			map.put("AGE", "AGE");
		}
		if("219977".equals(apply_type)){
			map.put("NATION", "NATION");
		}
		map.put("PERSON_ID", PERSON_ID);
		map.put("CPNY_ID", CPNY_ID);
		map.put("leavefromtime", leavefromtime);
		map.put("leavetotime", leavetotime);
		String str = "";
		try {
			str = infoApplyLeaveDao.getSex(map) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	

	/**
	 * 判断是否为追溯休假 overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	public boolean isZhuisuLeave(HttpServletRequest request) throws Exception {

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("FLAG", "S");// 获取考勤月开始日期
		paramMap.put("PERSON_ID", paramMap.get("dwz.person.personId1"));// 获取考勤月开始日期
		String arStartDateStr = this.infoApplyLeaveDao
				.getCurrentArDate(paramMap);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");

		String leaveApplyTo = paramMap.get("LEAVE_TO_TIME") != null ? paramMap
				.get("LEAVE_TO_TIME").toString() : sb.format(date);
		GregorianCalendar applyTo = DateUtil
				.ParseGregorianCalendar(leaveApplyTo);
		GregorianCalendar arStartDate = DateUtil
				.ParseGregorianCalendar(arStartDateStr);
		if ("TSTO".equals(paramMap.get("CPNY_ID"))) {
			if (applyTo.before(arStartDate)) {
				return true;
			}
		}
		return false;
	}
	

	/**
	 * 判断是否TA法人试用期
	 * overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean isProbation(Map paramMap)
			throws Exception {
		if("SST".equals(paramMap.get("CPNY_ID").toString()) && 
				("141473".equals(paramMap.get("APPLY_TYPE_CODE").toString()) || "26".equals(paramMap.get("APPLY_TYPE_CODE").toString()))){
			List list = this.infoApplyLeaveDao.isProbation(paramMap);
			if(list != null && list.size() > 0){
				return true;
			}
		}
		return false;

	}
	
	/**
	 * 判断PN法人工作日
	 * overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String isValidDate(Map paramMap)
			throws Exception {
		String str = "";
		if("LGEPN".equals(paramMap.get("CPNY_ID").toString())){
			str = this.infoApplyLeaveDao.isValidDate(paramMap);
		}
		return str;

	}
	
	/**
	 * 员工班次结束时间查询(Employee Information Management)
	 * @param obj
	 * @return object
	 */
	public String getShiftTime(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String returnStr = "";
		try {
			returnStr = StringUtil.checkNull(this.infoApplyLeaveDao.getShiftTime(paramMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}
	
	public List getEssFileList(Object paramMap) throws Exception {
		return infoApplyLeaveDao.getEssFileList(paramMap);
	}

	/**
	 * 申请销假( cancel leave apply)不走审批线
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addXiaojiaLeaveApplyNoAffirm(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		infoApplyLeaveDao.addXiaojiaLeaveApplyInfoNoAffirm(paramMap);
	}
	
	/**
	 * 申请销假( cancel leave apply)不走审批线
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void cancelLeaveApplyNoAffirm(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		infoApplyLeaveDao.cancelLeaveApplyNoAffirm(paramMap);
	}
	/**
	 * leave发令
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addLeaveAssigment(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchLeaveApplyList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		String person_id = paramMap.get("dwz.person.personId1") != null ? paramMap.get("dwz.person.personId1").toString() : "";
//		批量/个人申请
		String applyType= paramMap.get("APPLY_TYPE") != null ? paramMap.get("APPLY_TYPE").toString() : "0";
		String affirmFlag = paramMap.get("AFFIRM_FLAG") != null ? paramMap.get("AFFIRM_FLAG").toString() : "0";
		String leaveType = paramMap.get("LEAVE_TIME_TYPE") != null ? paramMap.get("LEAVE_TIME_TYPE").toString() : "";
		String applyTypeNo = paramMap.get("APPLY_TYPE_NO") != null ? paramMap.get("APPLY_TYPE_NO").toString() : "";
		String applyTypeCode = paramMap.get("LEAVE_APPLY_TYPE_CODE") != null ? paramMap.get("LEAVE_APPLY_TYPE_CODE").toString() : "";
//		申请日期
		String applyTime = paramMap.get("APPLY_TIME") != null ? paramMap.get("APPLY_TIME").toString() : "";
		if(applyTime==null || "".equals(applyTime)){
			applyTime = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
		}
		String fromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
		String toDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
		String fromTime = paramMap.get("fromTime") != null ? paramMap.get("fromTime").toString() : "";
		String toTime = paramMap.get("toTime") != null ? paramMap.get("toTime").toString() : "";
//		申请时长
		String applyLength = paramMap.get("APPLY_LENGTH") != null ? paramMap.get("APPLY_LENGTH").toString() : "";
//		哺乳假类型
		String subAapplyTypeCode = paramMap.get("SUB_LEAVE_APPLY_TYPE_CODE") != null ? paramMap.get("SUB_LEAVE_APPLY_TYPE_CODE").toString() : "";
		String FILE_URL =  StringUtil.checkNull(paramMap.get("fileUrl"));
		String FILE_NAME = StringUtil.checkNull(paramMap.get("fileName"));

//		申请是由
		String leaveReason = paramMap.get("LEAVE_REASON") != null ? paramMap.get("LEAVE_REASON").toString() : "";
//		适用修改
		String APPLY_NO = paramMap.get("APPLY_NO") != null ? paramMap.get("APPLY_NO").toString() : "";
		String UPDATE_FLAG = paramMap.get("UPDATE_FLAG") != null ? paramMap.get("UPDATE_FLAG").toString() : "";
		String IS_UPLOAD = paramMap.get("IS_UPLOAD") != null ? paramMap.get("IS_UPLOAD").toString() : "";
		
		LinkedHashMap dateMap = new LinkedHashMap();
		dateMap = this.getLinkedMapByRequest(request, dateMap);
		dateMap.put("PERSON_ID", person_id);
		dateMap.put("APPLY_TYPE", applyType);
		dateMap.put("AFFIRM_FLAG", affirmFlag);
		dateMap.put("LEAVE_TIME_TYPE", leaveType);
		dateMap.put("APPLY_TYPE_NO", applyTypeNo);
		dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
		dateMap.put("APPLY_TIME", applyTime);
		dateMap.put("LEAVE_FROM_TIME", fromDate + " " + fromTime + ":"+ "00");
		dateMap.put("LEAVE_TO_TIME", toDate + " " + toTime + ":"+ "00");
		dateMap.put("APPLY_LENGTH", applyLength);
		if("16415".equals(applyTypeCode)){
			dateMap.put("SUB_LEAVE_TYPE_CODE", subAapplyTypeCode);
		}
		dateMap.put("LEAVE_REASON", leaveReason);
		dateMap.put("FILE_NAME", FILE_NAME);
		dateMap.put("FILE_URL", FILE_URL);
		dateMap.put("APPLY_NO", APPLY_NO);
		dateMap.put("UPDATE_FLAG", UPDATE_FLAG);
		dateMap.put("IS_UPLOAD", IS_UPLOAD);
		dateMap.put("CPNY_ID", admin.getCpnyId());
		dateMap.put("applyTypeCode_forShiChang", applyTypeCode);
		
		//页面添加的决裁者列表
		List affirmList = new ArrayList();
		//添加决裁者
		LinkedHashMap affirmMap = new LinkedHashMap() ;
		affirmMap.put("AFFIRMOR_ID", "");
		affirmMap.put("AFFIRM_LEVEL", "1" );
		affirmList.add(affirmMap);
		dateMap.put("affirmList", affirmList);
		//插入数据库之前的数据的整理、验证，主要是决裁者与时间的验证，然后封装起来
		LinkedHashMap leaveMap = this.preAddAssignmentLeave(dateMap, admin.getLanguage());
		batchLeaveApplyList.add(leaveMap);
		this.infoApplyLeaveDao.addLeaveAssigmentInBatch(batchLeaveApplyList);
		return 1;
	}
	
	
	/**
	 * 封装要插入的考勤申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddAssignmentLeave(LinkedHashMap paramMap,
			String language) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		LinkedHashMap essParamMap = new LinkedHashMap();
		List distinctList = new ArrayList();

		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));

		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyLeaveDao
				.getPersonInfoByPersonId(paramMap);

		String leaveTimeType = paramMap.get("LEAVE_TIME_TYPE").toString();
		if (leaveTimeType != null && "P".equals(leaveTimeType)) {
			String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap
					.get("LEAVE_FROM_TIME").toString()
					: "";
			String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap
					.get("LEAVE_TO_TIME").toString()
					: "";
			// 检查申请开始时间和结束时间,如果格式不对提示用户
			this.formatFromAndToDate(leaveFromDate, leaveToDate);
		}

		if (this.isProbation(paramMap)) {
			throw new CommonException("试用期社员不能申请年假。");
		}
		// 检查申请开始时间和结束时间,如果格式不对提示用户
		boolean passFlag = false;

		passFlag = this.arAssigmentCheck(paramMap, personMap, language);
		// 验证
		if (passFlag) {
			paramMap.put("ACTIVITY", "0");
			returnMap.put("PARAM_MAP", paramMap);
			returnMap.put("DISTINCT_LIST", distinctList);
		} else {
			throw new CommonException(TipMessage.getTipMessage(
					"alert.message.ess.infoApply.checkHasNotThrough", language));
		}
		return returnMap;
	}
	
	/**
	 * 考勤申请检查
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean arAssigmentCheck(Map paramMap, LinkedHashMap personMap,
			String language) throws Exception {
		String leaveFromDate = paramMap.get("LEAVE_FROM_TIME") != null ? paramMap.get("LEAVE_FROM_TIME").toString() : "";
		String leaveToDate = paramMap.get("LEAVE_TO_TIME") != null ? paramMap.get("LEAVE_TO_TIME").toString() : "";
		String leaveTimeType = paramMap.get("LEAVE_TIME_TYPE").toString();
		String length = infoApplyLeaveDao.getLeaveApplyLength(paramMap);
		double leaveLength = Double.parseDouble(length);
//		暂时只检查存在开始结束时间
		if(leaveTimeType!=null){
			// 检查申请开始时间和结束时间,如果格式不对提示用户
			this.formatFromAndToDate(leaveFromDate, leaveToDate);
			// 开始结束时间检查
			if (this.applyStartEndTimeCheck()) {
				throw new CommonException(
						TipMessage.getTipMessage("alert.message.ess.infoApply.finishDateShouldLaterThanStartDate",
										language));// alert.ess.overtime.fail_before_start
			}
			// 休假开始日期检查
			if (this.leaveApplyFromTimeLeaveApplyForUpdate(paramMap)) {
				throw new CommonException(
						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
								+ personMap.get("LOCAL_NAME")
								+ "休假申请开始日期不能早于" + paramMap.get("zhuisuMsg"));
			}
/*			// 追溯加班不能跨月的判断
			if (this.leaveApplyToTimeLeaveApplyForUpdate(paramMap)) {
				throw new CommonException(
						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
								+ personMap.get("LOCAL_NAME")
								+ "追溯休假不能跨月，结束日期应小于" + paramMap.get("zhuisuMsg") + ",如有需要可以分段申请。");
			}*/
			
			// 休假时间与之前申请休假时间检查
			if (this.leaveApplyConflictWithExistLeaveApply(paramMap) && !"16415".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				throw new CommonException(
						TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
								+ personMap.get("LOCAL_NAME")
								+ TipMessage.getTipMessage(
												"alert.message.ess.infoApply.leaveApplyDateConflictWithExsitLeaveApply",
												language));
			}
			//外出和出差不加判断
			/*if(!"124851".equals(paramMap.get("APPLY_TYPE_CODE")) &&
					!"4084".equals(paramMap.get("APPLY_TYPE_CODE"))){
				// 休假时长验证  最小申请时长限制
				paramMap.put("leaveLength", length);
				String valResult = this.infoApplyLeaveDao.valLeaveLength(paramMap);
				if (!"OK".equals(valResult)) {
					throw new CommonException(valResult);
				}
				//如果是产期检查假,妇女节假，青年节假 ,申请时长长度限制
				if("482".equals(paramMap.get("APPLY_TYPE_CODE")) ||
						"141474".equals(paramMap.get("APPLY_TYPE_CODE")) ||
						"141475".equals(paramMap.get("APPLY_TYPE_CODE"))){
					String valMaxResult = this.infoApplyLeaveDao.valLeaveMaxLength(paramMap);
					if (!"OK".equals(valMaxResult)) {
						throw new CommonException(valMaxResult);
					}
				}

				// 休假时长单位验证
				paramMap.put("leaveLength", length);
				String valUtilResult = this.infoApplyLeaveDao.valLeaveLengthUtil(paramMap);
				if (!"OK".equals(valUtilResult)) {
					throw new CommonException(valUtilResult);
				}
			}*/
			// 如果是加班调休,18135
			if ("18135".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				double restTiaoXiu;
				restTiaoXiu = Double.parseDouble(this.infoApplyLeaveDao.getTiaoxiu(paramMap).toString());
				
				//如果申请调休时数大于剩余调休时数
				if ( restTiaoXiu*8 < leaveLength ) {
					throw new CommonException(
							TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
									+ personMap.get("LOCAL_NAME")
									+ "剩余调休天数不足，无法申请加班调休");
				}
				
			} 

			//HN法人不满一年不能申请年假
			if ("LGEHN".equals(paramMap.get("CPNY_ID"))) {
				if( "26".equals(paramMap.get("APPLY_TYPE_CODE"))){
					String isFullOneYear = this.infoApplyLeaveDao.isFullOneYear(paramMap);
					if (!"OK".equals(isFullOneYear)) {
						throw new CommonException("入职不满一年不能申请年假。");
					}
				}
			}
			

			//HZ法人不满一年不能申请年假
			if ( "LGEHZ".equals(paramMap.get("CPNY_ID"))) {
				if( "26".equals(paramMap.get("APPLY_TYPE_CODE"))){
					String isFullOneYear = this.infoApplyLeaveDao.isFullOneYearYY(paramMap);
					if (!"OK".equals(isFullOneYear)) {
						throw new CommonException("入职不满一年不能申请年假。");
					}
				}
			}
			
//			如果是法定年假26，判断时长是否合理
			if ("26".equals(paramMap.get("APPLY_TYPE_CODE"))) {

				// 年假申请开始日期限制
				if (this.getCurrentYearVacStartDateLimit(paramMap)) {
					throw new CommonException( "年假申请开始日期应大于" + paramMap.get("zhuisuMsg") );
				}
				// 年假申请结束日期限制
				if (this.getCurrentYearVacEndDateLimit(paramMap)) {
					throw new CommonException("年假申请结束日期应小于" + paramMap.get("zhuisuMsg") );
				}
				if("TSTO".equals(paramMap.get("CPNY_ID") )){
					paramMap.put("YEAR", DateUtil.getCurrentYearStr());
				}else{
					paramMap.put("interCpnyID", paramMap.get("CPNY_ID"));
					paramMap.put("YEAR", this.infoApplyLeaveDao.getCurrentYear(paramMap));
				}
				double restFadingnianjia = Double.parseDouble(this.infoApplyLeaveDao.getRestFadingnianjia(paramMap).toString());
				
				//如果申请法定年假时数大于剩余法定年假时数
				if (restFadingnianjia * 8 < leaveLength) {
					throw new CommonException(
							TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
									+ personMap.get("LOCAL_NAME")
									+ "剩余年假天数不足，无法申请年假");
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 加班申请决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveAffirmInfoBatchList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		if(paramMap.get("FLAG") == null || !"1".equals(paramMap.get("FLAG").toString())){
			paramMap.put("KEY", admin.getEmpID());
		}
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID",  request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		paramMap.put("PERSON_ID",admin.getAdminID());
		
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		if (UiUtil.getPageNum(request) > 0) {
			returnList = infoApplyLeaveDao.getLeaveAffirmInfoBatchList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = infoApplyLeaveDao.getLeaveAffirmInfoBatchList(paramMap);
		}
		if(returnList != null && returnList.size() > 0){
			for(int i=0;i<returnList.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)returnList.get(i);
				LinkedHashMap fileMap = new LinkedHashMap();
				fileMap.put("APPLY_TYPE", "21");
				fileMap.put("APPLY_NO", returnMap.get("BATCH_APPLY_NO_B"));
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		if(returnList != null && returnList.size() > 0 ){
			for(int i = 0;i<returnList.size() ; i++){
				LinkedHashMap leaveMap = (LinkedHashMap)returnList.get(i);
				LinkedHashMap affirmMap = new LinkedHashMap();
				if("0".equals(StringUtil.checkNull(leaveMap.get("CAL_FLAG")))){
					affirmMap.put("APPLY_NO", leaveMap.get("APPLY_NO"));
				}else{
					affirmMap.put("APPLY_NO", leaveMap.get("CAL_FLAG"));
				}
				List affirmorList = infoApplyLeaveDao.getAffirmorByApplyNoList(affirmMap);
				leaveMap.put("affirmorList", affirmorList);
			}
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	public List viewAdjustRecords(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			returnList = infoApplyLeaveDao.viewAdjustRecords(paramMap);
		return returnList;
	}
	
	/**
	 * 休假申请决裁信息总数(get Leave Affirm Info List Cnt)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getLeaveAffirmInfoBatchListCnt(HttpServletRequest request) throws Exception {
		int ListCnt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		if(paramMap.get("FLAG") == null || !"1".equals(paramMap.get("FLAG").toString())){
			paramMap.put("KEY", admin.getEmpID());
		}
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID",  request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		paramMap.put("PERSON_ID",admin.getAdminID());
		paramMap.put("specialParam",admin.getSpecialParam());//这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo",admin.getDeptNo());//判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		ListCnt =  this.infoApplyLeaveDao.getLeaveAffirmInfoBatchListCnt(paramMap);
		
		return ListCnt;
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
	public List getAffirmorListByString(String applyTypeNo , String personId , String applyTypeCode , String applyLength ) throws Exception {
		LinkedHashMap paramMap = new LinkedHashMap();
		if(applyTypeCode == null || "".equals(applyTypeCode)){
			applyTypeCode = applyTypeNo;
			applyLength = "0";
		}
		paramMap.put("APPLY_TYPE_NO", applyTypeNo);
		paramMap.put("APPLY_TYPE_CODE", applyTypeCode);
		paramMap.put("APPLY_LENGTH", applyLength);
		paramMap.put("PERSON_ID", personId);
		String sqlStr = this.infoApplyDao.getAffirmorlistByPersonIdStr(paramMap);
		paramMap.put("sqlStr", sqlStr);
		List<LinkedHashMap> sqlList = this.infoApplyDao.getAffirmorlistByPersonIdList(paramMap);			
		return sqlList;			
	}

	@Override
	public void deleteAllDataForAdd(HttpServletRequest request) throws Exception {
		
		LinkedHashMap paramMap =ObjectBindUtil.getRequestParamData(request);
		 this.infoApplyLeaveDao.deleteAllDataForAdd(paramMap);
	}
	
	/**
	 * 添加考勤申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addLeaveApplySST(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		
		String affirmJsonString = request.getParameter("affirmJsonData");
		List<LinkedHashMap<String, Object>> affirmList = ObjectBindUtil.getRequestJsonData(affirmJsonString);
		paramMap.put("APPLY_LEAVE_FLAG", request.getParameter("APPLY_FLAG"));
		/*List itemParamList = ItemsDao.getItemParameterList(paramMap);
		float i = 0;
		float n = 0;
		if (itemParamList.size() > 0) {
			LinkedHashMap itemMap = (LinkedHashMap)itemParamList.get(0);
			i = Float.parseFloat( itemMap.get("MIN_VALUE").toString()); //最小值
			n = Float.parseFloat(request.getParameter("APPLY_LENGTH")) ; //申请时长
			i = itemMap.get("UNIT").equals("DAY") ? i*8 : i;
			i = itemMap.get("UNIT").equals("MINUTE") ? i/60 : i;
			
		}
		if (n % i != 0) { //时长不能小于最小值.!
			throw new CommonException(TipMessage.getTipMessage("alert.message.ess.infoApply.applyTimeCanNotLessThanMinValue", request));
		}*/
		//页面添加的决裁者列表
		/*List affirmList = new ArrayList();
		String[] affirmIdStart = request.getParameterValues("ATT_AFFIRMOR_ID");
		String[] approvTypeIndex = request.getParameterValues("approvTypeIndex");*/
		if(affirmList == null || affirmList.size() == 0){
			// 未给该员工设置决裁者时
			throw new CommonException(TipMessage.getTipMessage("alert.message.pleaseFirstSetRuler.b", request));// 请先设置决裁者
		}
		/*List<String> affirmId=new ArrayList();
		List<String> approvTypeList=new ArrayList();
		for (int i = 0;i< affirmList.size();i++) {
			LinkedHashMap<String, Object> affirmor = affirmList.get(i);
	    	if(affirmor != null){
	    		if(admin.getAdminID().equals(strings)){
	    			throw new CommonException("不能把自己设置为审判者");// alert.ess.approval.no_approver
	    		}
	    		affirmId.add(strings);
	    		approvTypeList.add(request.getParameter("approvType" + approvTypeIndex[i]));
	    	}
	    }
		if(affirmId == null || affirmId.size()==0){
			throw new CommonException(TipMessage.getTipMessage("alert.message.pleaseFirstSetRuler.b", request));// 请先设置决裁者
		}
		//添加决裁者 
		for(int i=0; i<affirmList.size(); i++){
			LinkedHashMap<String, Object> affirmor = affirmList.get(i);
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			affirmMap.put("AFFIRMOR_ID", affirmor.get("AFFIRMOR_ID"));
			affirmMap.put("AFFIRM_LEVEL", affirmor.get("AFFIRM_LEVEL"));
			affirmMap.put("AFFIRM_TYPE", affirmor.get("AFFIRM_TYPE"));
			affirmList.add(affirmMap);
		}*/
		paramMap.put("affirmList", affirmList);
		//插入数据库之前的数据的整理、验证，主要是决裁者与时间的验证，然后封装起来
		
		LinkedHashMap leaveMap = this.preAddApplyLeaveSST(paramMap);
		this.infoApplyLeaveDao.addLeaveApplySST(leaveMap);
		
		return 1;
	}
	
	/**
	 * 天津年假信息申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addVacInfo(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap vacInfoMap = ObjectBindUtil.getRequestParamData(request);
		this.infoApplyLeaveDao.addVacInfo(vacInfoMap);
		
		return 1;
	}

	/**
	 * 封装要插入的考勤申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddApplyLeaveSST(LinkedHashMap paramMap) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);

		//各种验证（日期、时间、类型等）
		if (arApplyCheckSST(paramMap, personMap)){
			paramMap.put("ACTIVITY", "0");
			returnMap.put("PARAM_MAP", paramMap);
			returnMap.put("personMap", personMap);
		}
		return returnMap;
	}
	
	/**
	 * 考勤申请检查
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean arApplyCheckSST(LinkedHashMap paramMap, LinkedHashMap personMap) throws Exception {

		//加班时间验证
		String result = this.infoApplyLeaveDao.getLeaveCheckSST(paramMap);
		if ( !"OK".equals(result) ) {
			throw new CommonException( result);
		}
		return true;
	}
	
	/**
	 * 获取SST员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfoSST (HttpServletRequest request) throws Exception {
		Map param = ObjectBindUtil.getRequestParamData(request);
		return infoApplyLeaveDao.getEmpVacInfoSST(param);
	}
	
	/**
	 * 获取SST员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfoSSTForDisplay (Object obj) throws Exception {
		return infoApplyLeaveDao.getEmpVacInfoSSTForDisplay(obj);
	}
	
	
	
	@Override
	public String valImportExcelAttendanceData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_ESS_LEAVE_EXCEL_IMP.PR_VALID_ESS_LEAVE_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@Override
	public List getAttendanceTempList(HttpServletRequest request)
			throws Exception {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		retrunList = infoApplyLeaveDao.getAttendanceTempList(paramMap);
		return retrunList;
	}
	@Override
	public int getAttendanceTempCnt(HttpServletRequest request, String errorFlag) throws Exception{
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = infoApplyLeaveDao.getAttendanceTempErrorCnt(paramMap);
		}else{
			retrunInt = infoApplyLeaveDao.getAttendanceTempCnt(paramMap);
		}

		return retrunInt;
	}
	@Override
	public String submitImportExcelAttendanceData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_ESS_LEAVE_EXCEL_IMP.PR_IMPORT_ASS_LEAVE_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 个人考勤明细查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public List viewAttendancePersonalInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		returnList = infoApplyLeaveDao.viewAttendancePersonalInfoList(paramMap);
		return returnList;
	}
	
	/**
	 * 获得人员列表(view BatchApplyEmp List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@Override
	public List getPersonListView(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		String supervisor = (String)paramMap.get("supervisor") != null ? (String)paramMap.get("supervisor") : "";
		
		if(paramMap.get("DEPTNO")=="" || paramMap.get("DEPTNO")==null){
			paramMap.put("deptNo", admin.getDeptNo());
		}
		
		if(!"".equals(supervisor)){
			paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		}
		retrunList = infoApplyLeaveDao.getPersonListView(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList;
	}
	
	/**
	 * 获得人员列表(view BatchApplyEmp List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@Override
	public int getPersonListViewCnt(HttpServletRequest request) throws Exception{
		int resultCnt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		String supervisor = (String)paramMap.get("supervisor") != null ? (String)paramMap.get("supervisor") : "";
		
		if(paramMap.get("DEPTNO")=="" || paramMap.get("DEPTNO")==null){
			paramMap.put("deptNo", admin.getDeptNo());
		}
		
		if(!"".equals(supervisor)){
			paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		}
		resultCnt = infoApplyLeaveDao.getPersonListViewCnt(paramMap) ;
		
		return resultCnt;
	}
	
	/**
	 * 批量添加考勤
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addAttendanceApplyInfoForBatchHAE(HttpServletRequest request) throws Exception {
		List paramList = new ArrayList();
		//Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		
		String[] checks = request.getParameterValues("orgId");
		for(int i = 0;i<checks.length;i++){
			Map paramMapo = new HashMap();
			paramMapo = ObjectBindUtil.getRequestParamData(request);
			paramMapo.put("ApplyPersonId", checks[i]);
			paramList.add(paramMapo);
		}
		
		this.infoApplyLeaveDao.addAttendanceApplyInfoForBatchHAE(paramList);
		return 1;
	}
	
	/**
	 * 批量添加加班
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addOTApplyInfoForBatchHAE(HttpServletRequest request) throws Exception {
		List paramList = new ArrayList();
		
		String[] checks = request.getParameterValues("orgId");
		for(int i = 0;i<checks.length;i++){
			Map paramMapo = new HashMap();
			paramMapo = ObjectBindUtil.getRequestParamData(request);
			paramMapo.put("ApplyPersonId", checks[i]);
			paramList.add(paramMapo);
		}
		
		this.infoApplyLeaveDao.addOTApplyInfoForBatchHAE(paramList);
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewAddAttendanceApplyInfoForBatchHAE(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		returnList = infoApplyLeaveDao.viewAddAttendanceApplyInfoForBatchHAE(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewApplyAttenanceBatchInfoHAEList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		String firstFlag = request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if ("Y".equals(firstFlag)) {
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
		returnList = infoApplyLeaveDao.viewApplyAttenanceBatchInfoHAEList(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public Object getApplyAttenanceBatchInfoHAEDetail(HttpServletRequest request) throws Exception {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
				
		return this.infoApplyLeaveDao.getApplyAttenanceBatchInfoHAEDetail(paramMap); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewApplyOTBatchInfoHAEList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		String firstFlag = request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if ("Y".equals(firstFlag)) {
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
		returnList = infoApplyLeaveDao.viewApplyOTBatchInfoHAEList(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public Object getApplyOTBatchInfoHAEDetail(HttpServletRequest request) throws Exception {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
				
		return this.infoApplyLeaveDao.getApplyOTBatchInfoHAEDetail(paramMap); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void delAttendanceExForBatchInfoListHAE(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		this.infoApplyLeaveDao.delAttendanceExForBatchInfoListHAE(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void delOTApplyInfoForBatchInfoListHAE(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		this.infoApplyLeaveDao.delOTApplyInfoForBatchInfoListHAE(paramMap);
	}
}
