package com.ait.ar.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.fop.fo.properties.LastLineEndIndentMaker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ait.ar.dao.ArDetailDao;
import com.ait.ar.service.ArDetailSer;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.service.InfoApplySer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.mail.ar.SendArDetailMail;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.businessobjects.crystalreports.viewer.core.ad;
import com.businessobjects.crystalreports.viewer.core.l;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArDetailSerImp.java
 * @Description:
 * @Create date: 2012-2-7 下午04:30:45
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ArDetailSerImp implements ArDetailSer {
	Logger logger = Logger.getLogger(ArDetailSerImp.class);

	@Autowired
	private ArDetailDao arDetailDao;
	
	@Autowired
	private InfoApplyDao infoApplyDao;

	@Autowired
	private InfoApplySer infoApplySer;
	@Autowired
	private PaTempSalesDAO paTempSalesDAO;
	
	@Autowired
	private AuthorityUtil authorityUtil;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	
	private GregorianCalendar startTime = new GregorianCalendar();

	private GregorianCalendar endTime = new GregorianCalendar();
	/**
	 * 取得人员考勤明细列表(get ArDetail List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getArDetailList(HttpServletRequest request, ModelMap modelMap) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("supervisor", admin.getPersonId());
		String ar_date_str= ObjectUtils.toString(paramMap.get("ar_date_str")) ;
		if(ar_date_str == null || ar_date_str.length() == 0){
			Calendar calendar = Calendar.getInstance();
			ar_date_str = new java.text.SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
			paramMap.put("AR_DATE_STR", ar_date_str);
		}
		paramMap.put("MANAGER_TYPE", "ATTOVERTIME");

		if (paramMap.get("sDate") == null && paramMap.get("eDate") == null) {// 页面搜索
			
            paramMap.put("sDate", modelMap.get("sDate").toString().replaceAll("-", "/"));
			paramMap.put("eDate", modelMap.get("eDate").toString().replaceAll("-", "/"));
		}else
		{
			    paramMap.put("sDate", paramMap.get("sDate").toString().replaceAll("-", "/"));
				paramMap.put("eDate", paramMap.get("eDate").toString().replaceAll("-", "/"));
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = arDetailDao.getArDetailList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = arDetailDao.getArDetailList(paramMap);
		}
		return retrunList;
		
	}
	
	/**
	 * 取得人员考勤异常明细列表(get ArDetail List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getAttendanceExceptionList(HttpServletRequest request, ModelMap modelMap) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("supervisor", admin.getPersonId());
		String ar_date_str= ObjectUtils.toString(paramMap.get("ar_date_str")) ;
		if(ar_date_str == null || ar_date_str.length() == 0){
			Calendar calendar = Calendar.getInstance();
			ar_date_str = new java.text.SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
			paramMap.put("AR_DATE_STR", ar_date_str);
		}
		paramMap.put("MANAGER_TYPE", "ATTOVERTIME");

		if (paramMap.get("sDate") == null && paramMap.get("eDate") == null) {// 页面搜索
			
            paramMap.put("sDate", modelMap.get("sDate").toString().replaceAll("-", "/"));
			paramMap.put("eDate", modelMap.get("eDate").toString().replaceAll("-", "/"));
		}else{
			    paramMap.put("sDate", paramMap.get("sDate").toString().replaceAll("-", "/"));
				paramMap.put("eDate", paramMap.get("eDate").toString().replaceAll("-", "/"));
		}
		/*if (UiUtil.getPageNum(request) > 0) {
			retrunList = arDetailDao.getAttendanceExceptionList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {*/
			retrunList = arDetailDao.getAttendanceExceptionList(paramMap);
		/*}*/
		return retrunList;
		
	}
	
	
	/**
	 * 取得人员考勤明细列表(get ArDetail List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getArDetailListExcel(HttpServletRequest request, ModelMap modelMap) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap.get("sDate") == null && paramMap.get("eDate") == null) {// 页面搜索
			
            paramMap.put("sDate", modelMap.get("sDate").toString().replaceAll("-", "/"));
			paramMap.put("eDate", modelMap.get("eDate").toString().replaceAll("-", "/"));
		}else
		{
			    paramMap.put("sDate", paramMap.get("sDate").toString().replaceAll("-", "/"));
				paramMap.put("eDate", paramMap.get("eDate").toString().replaceAll("-", "/"));
		}
	    retrunList = arDetailDao.getArDetailListExcel(paramMap);
		return retrunList;
	}

	/**
	 * 取考勤项目列表(get Item List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getItemList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return arDetailDao.getItemList(paramMap);
	}

	@SuppressWarnings("unchecked")
	public int getArDetailListCnt(HttpServletRequest request, ModelMap modelMap) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("supervisor", admin.getPersonId());
		/*if (paramMap.get("sDate") == null && paramMap.get("eDate") == null) {// 页面搜索
			paramMap.put("sDate", modelMap.get("sDate").toString().replaceAll("-", "/"));
			paramMap.put("eDate", modelMap.get("eDate").toString().replaceAll("-", "/"));
		}else
		{
		    paramMap.put("sDate", paramMap.get("sDate").toString().replaceAll("-", "/"));
			paramMap.put("eDate", paramMap.get("eDate").toString().replaceAll("-", "/"));
     	}
		if(modelMap.get("BIAO") != null){
			paramMap.put("BIAO", "BIAO");
		}
		if(modelMap.get("ABSENT") != null){
			paramMap.put("BIAO", "ABSENT");
		}*/
		return arDetailDao.getArDetailListCnt(paramMap);
	};
	

	

	/**
	 * 取考勤开始日期(get StartDateStr)
	 * 
	 * @param
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String getStartDateStr() {
		GregorianCalendar date = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		LinkedHashMap map = new LinkedHashMap();
		map.put("start_date", sdf.format(date.getTime()));
		int result = arDetailDao.getStartDateStr(map);
		if (result == 0) {
			date.set(GregorianCalendar.DAY_OF_MONTH, 1);
			return sdf.format(date.getTime());
		}
		if (date.get(GregorianCalendar.DAY_OF_MONTH) < result) {
			date.add(GregorianCalendar.MONTH, -1);
		}
		date.set(GregorianCalendar.DAY_OF_MONTH, result);

		return sdf.format(date.getTime());
	}

	/**
	 * 取考勤结束日期(get EndDateStr)
	 * 
	 * @param
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String getEndDateStr() {
		GregorianCalendar date = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		LinkedHashMap map = new LinkedHashMap();
		map.put("start_date", sdf.format(date.getTime()));
		int result = arDetailDao.getStartDateStr(map);
		if (result == 0) {
			date.set(GregorianCalendar.DAY_OF_MONTH, 1);
			date.add(GregorianCalendar.MONTH, 1);
			date.add(GregorianCalendar.DAY_OF_MONTH, -1);

			return sdf.format(date.getTime());
		}
		if (date.get(GregorianCalendar.DAY_OF_MONTH) < result) {
			date.add(GregorianCalendar.MONTH, -1);
		}
		date.set(GregorianCalendar.DAY_OF_MONTH, result);

		date.add(GregorianCalendar.MONTH, 1);
		date.add(GregorianCalendar.DAY_OF_MONTH, -1);

		return sdf.format(date.getTime());

	}

	/**
	 * 修改考勤明细信息(update ArDetail Info)
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map updateArDetailInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int vResult = -1;
		String vVac	= "0";
		String date = "";
		String item = "";
		Map messMap = new LinkedHashMap();
		boolean flag = true;
		// 页面参数
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> arDetailInfoList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		/*for (int i = 0; i < arDetailInfoList.size(); i++) {
			LinkedHashMap arDetailInfoMap = (LinkedHashMap) arDetailInfoList
					.get(i);
			arDetailInfoMap.put("CPNY_ID", admin.getCpnyId());
			arDetailInfoMap.put("interLanguage", admin.getLanguage());
			/*LinkedHashMap map = (LinkedHashMap) arDetailDao
					.validateDailyLock(arDetailInfoMap);
			vResult = Integer.parseInt(map.get("CNT").toString());
			vVac = map.get("VAC").toString();
			date = map.get("AR_DATE_STR").toString();
			arDetailInfoMap.put("UPDATED_BY", admin.getPersonId());
			if (vResult == 0) {
				messMap.put("scode", "2");
				// 日考勤已经锁定,不能进行明细维护.
				messMap.put("message", TipMessage.getTipMessage(
						"ar.viewardetail.title.rikaoqin", request)
						+ "["
						+ date
						+ "]"
						+ TipMessage.getTipMessage(
								"ar.viewardetail.title.locked", request));
				flag = false;
				break;
			}*/
			/*if ( !"1".equals(vVac)) {
				messMap.put("scode", "4");
				// 年假天数不足
				messMap.put("message", arDetailInfoMap.get("EMP_ID") + "年假天数不足,剩余" + vVac + "天");
				flag = false;
				break;
			}
		 	map = (LinkedHashMap) arDetailDao
					.validateDetailItemType(arDetailInfoMap);
			vResult = Integer.parseInt(map.get("CNT").toString());
		
			date = map.get("AR_DATE_STR").toString();
			item = map.get("ITEM_NAME").toString();
				 
			vResult = 1;
			if (vResult == 0) {
				messMap.put("scode", "3");
				// 考勤项目与日期类型不符.
				messMap.put("message", TipMessage.getTipMessage(
						"ar.viewitemparameter.title.kaoqingxiangmu", request)
						+ "["
						+ item
						+ "]"
						+ TipMessage.getTipMessage("ar.viewardetail.title.yu",
								request)
						+ "["
						+ date
						+ "]"
						+ TipMessage.getTipMessage(
								"ar.viewardetail.title.datetype", request));
				flag = false;
				break;
			}
		}*/
		try {
			if (flag) {
				//在修改前把修改的这条数据存放在RA_DETAIL_HISTORY  和修改后的项目和长度
				if(arDetailInfoList.size()>0){
					for(int j=0;j<arDetailInfoList.size();j++){
						Map mapInfo = arDetailInfoList.get(j);
						mapInfo.put("interCpnyID",admin.getCpnyId());
						mapInfo.put("interLanguage", admin.getLanguage());
						String affirmType= ObjectUtils.toString(mapInfo.get("AFFIRM_FLAG")) ;
						String affirmDefaultFlag=ObjectUtils.toString(mapInfo.get("AFFIRM_FLAG_DEFAULT")) ;
						String applyNo=ObjectUtils.toString(mapInfo.get("APPLY_NO")) ;
						if( "".equals(applyNo) || applyNo==null ){//APPLY_NO为空说明没有申请
							if(affirmType.equals("14014307")){//部门申请状态，申请表里也插入
								//查询SEQ
								int essApplySeq =arDetailDao.getEssApplySeq();
								//查询出此人的加班决裁者
								String applyTypeNo = "31";
								String personId = (String) mapInfo.get("PERSON_ID");
								String applyTypeCode = (String) mapInfo.get("OT_TYPE_CODE");
								String applyLength = "";
								List affirmorList = this.infoApplySer.getAffirmorListByString(applyTypeNo, personId, applyTypeCode, applyLength, admin.getLanguage());
								//插入申请表
								if(affirmorList.size() > 0 ){
									mapInfo.put("APPLY_NO_SEQ", essApplySeq);
									mapInfo.put("APPLY_TYPE_NO", "31");
									mapInfo.put("APPLY_OT_DATE", mapInfo.get("AR_DATE_STR").toString());
									mapInfo.put("APPLY_TYPE", "PERSON");
									mapInfo.put("OT_TIME_TYPE", "P");
									mapInfo.put("FROM_TIME", mapInfo.get("AR_DATE_STR").toString() + " " + mapInfo.get("FROM_TIME").toString());
									mapInfo.put("TO_TIME",  mapInfo.get("AR_DATE_STR").toString() + " " + mapInfo.get("TO_TIME").toString());
									mapInfo.put("CREATED_IP", mapInfo.get("CREATED_IP") == null? admin.getAdminIP():mapInfo.get("CREATED_IP"));
									mapInfo.put("CREATED_BY",  mapInfo.get("CREATED_BY") == null? admin.getAdminID():mapInfo.get("CREATED_BY"));
									mapInfo.put("CREATE_DATE",  mapInfo.get("CREATE_DATE"));
									mapInfo.put("UPDATED_IP", admin.getAdminIP());
									String quantity=ObjectUtils.toString(mapInfo.get("QUANTITY"));
									String [] lenghtStr=null;
									if(quantity.indexOf(".")>0){
										lenghtStr=quantity.split("\\.");
										mapInfo.put("hour", lenghtStr[0]);
										mapInfo.put("min", "30");
									}else{
										mapInfo.put("hour", mapInfo.get("QUANTITY"));
										mapInfo.put("min", "0");
									}
									
									mapInfo.put("AFFIRM_FLAG",mapInfo.get("AFFIRM_FLAG"));
									mapInfo.put("ADJUST_YN","0");
									mapInfo.put("APPLY_REMARK","");
									mapInfo.put("CREATED_BY",admin.getAdminID());
									mapInfo.put("CREATED_IP",admin.getAdminIP());
									mapInfo.put("ACTIVITY",0);
									int result1=this.arDetailDao.insertOverTimeApplyInfo(mapInfo);
									LinkedHashMap tempMap=new  LinkedHashMap();
									if(result1>0){
										//插入决裁者
									
										 	for(int i=0;i<affirmorList.size();i++) {
											LinkedHashMap parmers= (LinkedHashMap)affirmorList.get(i);
											tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
											tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
											tempMap.put("AFFIRM_COM_TYPE", parmers.get("AFFIRM_COM_TYPE"));
											tempMap.put("CREATED_BY", admin.getAdminIP());
											tempMap.put("APPLY_NO_SEQ", essApplySeq);
											this.arDetailDao.insertAffirmListInfo(tempMap);
										}
									//同时更行ar_detail 表里的 字段
										 	this.arDetailDao.updateArDetailInfoByNo(mapInfo);	 	
									}
								}else{
									messMap.put("scode", "3");
									messMap.put("LOCAL_NAME", mapInfo.get("LOCAL_NAME").toString());
									break;
								}
							}
						}else{//
							//APPLY_NO不为空，修改明细表、申请表、决裁表数据
							//明细表
							this.arDetailDao.updateArDetailInfoByNo(mapInfo);
							//申请表
							this.arDetailDao.updateArDetailApplyInfoByNo(mapInfo);
							//决裁表
							this.arDetailDao.updateArDetailAffirmInfoByNo(mapInfo);
							
						}
						 
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messMap.put("scode", "0");
			return messMap;
		}

		return messMap;
	}

	/**
	 * 删除考勤明细信息(delete ArDetail Info)
	 * 
	 * @param request
	 * @return Map
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Map deleteArDetailInfo(HttpServletRequest request) {

		int vResult = -1;
		String date = "";
		Map messMap = new LinkedHashMap();
		boolean flag = true;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面参数
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> arDetailInfoList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		for (int i = 0; i < arDetailInfoList.size(); i++) {
			LinkedHashMap arDetailInfoMap = (LinkedHashMap) arDetailInfoList
					.get(i);
			arDetailInfoMap.put("CPNY_ID", admin.getCpnyId());
			LinkedHashMap map = (LinkedHashMap) arDetailDao
					.validateDailyLock(arDetailInfoMap);
			if(map == null || map.isEmpty()){
				messMap.put("scode", "2");
				// 未找到日考勤,不能进行明细维护.
				messMap.put("message", "未找到日考勤,不能进行明细维护.");
				flag = false;
				break;
			}
			vResult = Integer.parseInt(map.get("CNT").toString());
			date = map.get("AR_DATE_STR").toString();
			if (vResult == 0) {
				messMap.put("scode", "2");
				// 日考勤已经锁定,不能进行明细维护.
				messMap.put("message", TipMessage.getTipMessage(
						"ar.viewardetail.title.rikaoqin", request)
						+ "["
						+ date
						+ "]"
						+ TipMessage.getTipMessage(
								"ar.viewardetail.title.locked", request));
				flag = false;
				break;
			}

		}

		try {
			if (flag) {
				//在删除前把删除的这条数据存放在RA_DETAIL_HISTORY
				if(arDetailInfoList.size()>0){
					for(int j=0;j<arDetailInfoList.size();j++){
						Map mapInfo = arDetailInfoList.get(j);
						mapInfo.put("interCpnyID", mapInfo.get("CPNY_ID"));
						mapInfo.put("interLanguage", admin.getLanguage());
						List listInfo = arDetailDao.getArDetailList(mapInfo);
						if(listInfo!=null && listInfo.size()>0){
						    Map object = (Map)listInfo.get(0);
						    object.put("CPNY_ID", mapInfo.get("CPNY_ID"));
						    object.put("OLD_ITEM_NO", object.get("ITEM_NO"));
						    object.put("OLD_QUANTITY", object.get("QUANTITY"));
						    object.put("CREATED_BY", admin.getPersonId());
						    object.put("OPERATION", "DEL");
						    this.arDetailDao.addArDetailHistoryInfo(object);
						}
					}
				}
				this.arDetailDao.deleteArDetailInfo(arDetailInfoList);
				messMap.put("scode", "1");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messMap.put("scode", "0");
			return messMap;
		}

		return messMap;
	}

	/**
	 * 添加考勤明细信息(add ArDetail Info)
	 * 
	 * @param request
	 * @return int
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map addArDetailInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		int vResult = -1;
		String date = "";
		String item = "";
		Map messMap = new LinkedHashMap();
		boolean flag = true;

		// 页面参数
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> arDetailInfoList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		List addDetailInfoList = new ArrayList();
		String empid = "";
		for (int i = 0; i < arDetailInfoList.size(); i++) {
			LinkedHashMap arDetailInfoMap = (LinkedHashMap) arDetailInfoList
					.get(i);
			arDetailInfoMap.put("CPNY_ID", admin.getCpnyId());
			arDetailInfoMap.put("interLanguage", admin.getLanguage());
			LinkedHashMap map = (LinkedHashMap) arDetailDao
					.validateDailyLock(arDetailInfoMap);
			vResult = Integer.parseInt(map.get("CNT").toString());
			date = map.get("AR_DATE_STR").toString();
			empid = arDetailInfoMap.get("EMP_ID").toString();
			if (vResult == 0) {
				messMap.put("scode", "2");
				// 日考勤已经锁定,不能进行明细维护.
				messMap.put("message", TipMessage.getTipMessage(
						"ar.viewardetail.title.rikaoqin", request)
						+ "["
						+ date
						+ "]"
						+ TipMessage.getTipMessage(
								"ar.viewardetail.title.locked", request));
				flag = false;
				break;
			}

		/*	map = (LinkedHashMap) arDetailDao
					.validateDetailItemType(arDetailInfoMap);
	   
			vResult = Integer.parseInt(map.get("CNT").toString());
			date = map.get("AR_DATE_STR").toString();
			item = map.get("ITEM_NAME").toString();
		 */
			vResult = 1;
			
			if (vResult == 0) {
				messMap.put("scode", "3");
				// 考勤项目与日期类型不符.
				messMap.put("message", TipMessage.getTipMessage(
						"ar.viewitemparameter.title.kaoqingxiangmu", request)
						+ "["
						+ item
						+ "]"
						+ TipMessage.getTipMessage("ar.viewardetail.title.yu",
								request)
						+ "["
						+ date
						+ "]"
						+ TipMessage.getTipMessage(
								"ar.viewardetail.title.datetype", request));
				flag = false;
				break;
			}

			arDetailInfoMap.put("interLanguage", admin.getLanguage());
			arDetailInfoMap.put("CPNY_ID", admin.getCpnyId());
			arDetailInfoMap.put("interCpnyID", admin.getCpnyId());
			arDetailInfoMap.put("supervisor", admin.getPersonId());

			Object detail = this.arDetailDao.getArDetailInfo(arDetailInfoMap);
			Map detailMap = (detail == null ? null : (Map) detail);
			if (detailMap != null) {
				detailMap.put("ITEM_NO", arDetailInfoMap.get("ITEM_NO"));
				detailMap.put("QUANTITY", arDetailInfoMap.get("QUANTITY"));
				detailMap.put("LOCK_YN", arDetailInfoMap.get("LOCK_YN"));
				detailMap.put("CPNY_ID", admin.getCpnyId());
				detailMap.put("REMARK", arDetailInfoMap.get("REMARK"));
				detailMap.put("UPDATED_BY", admin.getPersonId());
			}
			addDetailInfoList.add(detail == null ? null
					: (LinkedHashMap<String, Object>) detailMap);
		}

		try {
			if (flag) {
				//在添加前把添加的这条数据存放在RA_DETAIL_HISTORY
				if(arDetailInfoList.size()>0){
					for(int j=0;j<arDetailInfoList.size();j++){
						Map mapInfo = arDetailInfoList.get(j);
						mapInfo.put("interCpnyID", mapInfo.get("CPNY_ID"));
						mapInfo.put("interLanguage", admin.getLanguage());
						List listInfo = arDetailDao.getArDetailList(mapInfo);
						if(listInfo!=null && listInfo.size()>0){
						    Map object = (Map)listInfo.get(0);
						    object.put("CPNY_ID", mapInfo.get("CPNY_ID"));
						    object.put("NEW_ITEM_NO", mapInfo.get("ITEM_NO"));
						    object.put("NEW_QUANTITY", mapInfo.get("QUANTITY"));
						    object.put("CREATED_BY", admin.getPersonId());
						    object.put("OPERATION", "ADD");
						    this.arDetailDao.addArDetailHistoryInfo(object);
						}
					}
				}
				// this.arDetailDao.addArDetailInfo(arDetailInfoList);

				// this.arDetailDao.deleteArDetailInfo(arDetailInfoList);
				// this.arDetailDao.addArDetailInfo(addDetailInfoList);

				this.arDetailDao.addArDetailInfo1(arDetailInfoList,
						addDetailInfoList);

				messMap.put("scode", "1");
				messMap.put("condition", empid);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messMap.put("scode", "0");
			return messMap;
		}

		return messMap;
	}

	public List SearchArDetailExceptionInfo(HttpServletRequest request) {
	
		Object paramMap = null;
		return arDetailDao.SearchArDetailExceptionInfo(paramMap);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ait.ar.service.ArDetailSer#SearchArDetailDeptExToMa(javax.servlet
	 * .http.HttpServletRequest)
	 */
	@Override
	public List SearchArDetailDeptExToMa(String string) {
		
		return arDetailDao.SearchArDetailDeptExToMa(string);
	}

	
	@Override
	public List SearchManagerInfo(HttpServletRequest request) {
		Object paramMap =null;
	    return arDetailDao.SearchManagerInfo(paramMap);
	}

	
	@Override
	public void SearchArDetailExceptionInfo() {
		Object paramMap = null;
		List arDetailExInfo = arDetailDao.SearchArDetailExceptionInfo(paramMap);
		SendArDetailMail sendardetailmail = new SendArDetailMail();
		sendardetailmail.setList(arDetailExInfo);
		new Thread(sendardetailmail).start();
	}

	/**
	 * 取得人员考勤明细列表，仅限查询本人的明细信息(get ArDetail List，for only self)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getArDetailEssList(HttpServletRequest request, ModelMap modelMap) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());//只能查询自己的
		if (paramMap.get("sDate") == null && paramMap.get("eDate") == null) {// 页面搜索
			paramMap.put("sDate", modelMap.get("sDate"));
			paramMap.put("eDate", modelMap.get("eDate"));
		}
		if(modelMap.get("BIAO") != null){
			paramMap.put("BIAO", "BIAO");
		}
		//zyh 
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = arDetailDao.getPersonArDetailList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = arDetailDao.getPersonArDetailList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 取得人员考勤明细数量，仅限查询本人的明细信息(get ArDetail List，for only self)
	 * 
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int getArDetailEssListCnt(HttpServletRequest request, ModelMap modelMap) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());//只能查询自己的
		if (paramMap.get("sDate") == null && paramMap.get("eDate") == null) {// 页面搜索
			paramMap.put("sDate", modelMap.get("sDate"));
			paramMap.put("eDate", modelMap.get("eDate"));
		}
		if(modelMap.get("BIAO") != null){
			paramMap.put("BIAO", "BIAO");
		}
		return arDetailDao.getArDetailListCnt(paramMap);
	};
	/**
	 * @throws Exception 
	 * 取考勤员权限是否生效
	 * @param String
	 * @return String
	 * @throws
	 */
	 @SuppressWarnings("unchecked")
	  public String getModifyYnBySupervisorId(String supervisorId) {
	    // TODO Auto-generated method stub
	    return arDetailDao.getModifyYnBySupervisorId(supervisorId);
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
		public List getArDetailTempList(HttpServletRequest request) {
			List retrunList = new ArrayList();
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
					"seach_");
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("PERSON_ID", admin.getPersonId());
			if (UiUtil.getPageNum(request) > 0) {
				retrunList = arDetailDao.getArDetailTempList(paramMap, UiUtil
						.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				retrunList = arDetailDao.getArDetailTempList(paramMap);
			}
			return retrunList;
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
		public int getArDetailTempCnt(HttpServletRequest request, String errorFlag){
			int retrunInt = 0;
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
					"seach_");
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("PERSON_ID", admin.getPersonId());
			if("E".equals(errorFlag)){
				retrunInt = arDetailDao.getArDetailTempErrorCnt(paramMap);
			}else{
				retrunInt = arDetailDao.getArDetailTempCnt(paramMap);
			}

			return retrunInt;
		}
		

		/**
		 * 明细excel信息提交
		 * 
		 * @Copyright: AIT (c)
		 * @Company: AIT
		 * @author weizhengchen@ait.net.cn
		 * @date 2014-7-03
		 * @version V1.0
		 */
		public String submitImportExcelArDetailData(HttpServletRequest request){

			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
					"seach_");
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("PR_NAME", "PKG_AR_DETAIL_EXCEL_IMP.PR_IMPORT_AR_DETAIL_DATA");
			try {
				return this.paTempSalesDAO.importFromExcel(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}


		@Override
		public String countArDetailLengthInfo(HttpServletRequest request) {
			// TODO Auto-generated method stub
			 LinkedHashMap param = new LinkedHashMap();
			 String result="";
			    String fromTime = request.getParameter("FROM_TIME");
			    String toTime = request.getParameter("TO_TIME");
			    String arDateStr = request.getParameter("AR_DATE_STR");
			    String shiftNo=request.getParameter("SHIFT_NO");
			    String firstTime=request.getParameter("FIRST_TIME");
			    String lastTime=request.getParameter("LAST_TIME");
			    param.put("SELECT_STIME", fromTime);
			    param.put("SELECT_ETIME", toTime);
			    param.put("FIRST_TIME",firstTime );
			    param.put("LAST_TIME",lastTime );
			    param.put("AR_DATE_STR", arDateStr);
			    param.put("SHIFT_NO", shiftNo);
			    AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			    param.put("interCpnyID",admin.getCpnyId());
			    String jsonString = request.getParameter("jsonData");
				List<LinkedHashMap<String, Object>> arDetailInfoList = ObjectBindUtil
						.getRequestJsonData(jsonString);
				 LinkedHashMap arDetailInfoMap=new LinkedHashMap();
				 if(arDetailInfoList.size() > 0){
					  arDetailInfoMap = (LinkedHashMap) arDetailInfoList
						.get(0);
				 }
			     
			    try {
			    	result=this.arDetailDao.countArDetailLength(arDetailInfoMap);
			    
			    } catch (Exception e) {
			      e.printStackTrace();
			      return "0";
			    }
			    return result;
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
			returnList = arDetailDao.getOtAffirmInfoListBatch(paramMap);
			return returnList;
		}
		/**
		 * 倒休管理页面(search ot info list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List viewArAdjustHolidayManagent(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List returnList = new ArrayList();
			// 页面提交数据
			LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
			paramMap.put("APPLY_TYPE", "BATCH");
			paramMap.put("PERSON_ID001", admin.getPersonId());
			Date d=new Date();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
			paramMap.put("ot_date", df.format(new Date(d.getTime()-60*60*24*1000)));
			String firstFlag = request.getParameter("firstFlag");
			if (firstFlag ==null || "".equals(firstFlag)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
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
				infoApplyLeaveDao.delNullBatchOTTSTOAffirmInfoList(paramMap);
			}
			//考勤担当权限
			int authority = authorityUtil.isArUser(admin.getPersonId());
			if(authority == 1){
				paramMap.put("authority", "ArUser");
			} 
			returnList = arDetailDao.viewArAdjustHolidayManagent(paramMap);
			return returnList;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List viewArAdjustHolidayManagentNull(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List returnList = new ArrayList();
			// 页面提交数据
			LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
			int authority = authorityUtil.isArUser(admin.getPersonId());
			if(authority == 1){
				paramMap.put("authority", "ArUser");
			} 
			returnList = arDetailDao.viewArAdjustHolidayManagentNull(paramMap);
			return returnList;
		}
		
		/**
		 * 加班管理SST(search ot info list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getOtAffirmInfoListBatchSST(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List returnList = new ArrayList();
			// 页面提交数据
			LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
			paramMap.put("APPLY_TYPE", "BATCH");
			paramMap.put("PERSON_ID", admin.getPersonId());
			Date d=new Date();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
			paramMap.put("ot_date", df.format(new Date(d.getTime()-60*60*24*1000)));
			//考勤担当权限
			int authority = authorityUtil.isArUser(admin.getPersonId());
			if(authority == 1){
				paramMap.put("authority", "ArUser");
			} 
			returnList = arDetailDao.getOtAffirmInfoListBatchSST(paramMap);
			return returnList;
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


		@SuppressWarnings("unchecked")
		@Override
		public int delOvertimeApplyInBatch(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap personMap = new LinkedHashMap();
			personMap.put("APPLY_PERSON", admin.getPersonId());
			String op_flag = request.getParameter("OP_FLAG")!=null?request.getParameter("OP_FLAG"):"0";
			List list =new ArrayList();
			List deleteList =new ArrayList();
			try {
				// 批量封装加班申请数据并处理
				if (!"2".equals(op_flag)&&!"0".equals(op_flag)) {
				     list = this.encapsulationApplyNoListForBatch(request);
				}
				if("0".equals(op_flag)){//删除
					deleteList = encapsulationOverTimeListForDelete(request);
					this.infoApplyDao.delOvertimeApplyInBatchTSTO(deleteList);//加班数据的删除
				}else if("2".equals(op_flag)) {//SST添加
					LinkedHashMap map = new LinkedHashMap();
					map.put("CPNY_ID",admin.getCpnyId());
					map.put("CREATED_BY",admin.getAdminID());
					map.put("CREATED_IP",admin.getAdminIP());
					map.put("applyBatchdate",request.getParameter("APPLY_DATE"));
					this.infoApplyLeaveDao.addtOtTSTOApplyInBatch(map);
				}else{//提交
					for(int i=0;i<list.size();i++){
						LinkedHashMap paramMap = (LinkedHashMap) list.get(i);
					    personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);
					    String AFFIRM_FLAG = (String) paramMap.get("AFFIRM_FLAG");
						boolean passFlag = false;
						passFlag = this.arApplyCheckBatchOverTime(paramMap, personMap, admin.getLanguage());
						String PK_NO = (String) paramMap.get("APPLY_NO");
						paramMap.put("PK_NO", PK_NO);
						//SST用
						
						//获取数据库该条选中数据的决裁状态
						String oldAffrim = infoApplyLeaveDao.getChechedAffrim(paramMap) ;
						paramMap.put("oldAffrim", oldAffrim);
						
						//修改考勤的班次及时间
						if("TSTO".equals(paramMap.get("CPNY_ID"))){
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
									LinkedHashMap timeMap  = infoApplyDao.getOtApplyWorkTimeU(flMap) ;
									paramMap.put("FIRST_FOR_SHIFTNO",timeMap.get("FIRST_TIME"));
									paramMap.put("LAST_FOR_SHIFTNO", timeMap.get("LAST_TIME"));
									//修改考勤的班次，开始结束时间
									paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
									this.infoApplyDao.updatArDetailBatchApplyForSHIFT(paramMap);
								}else {
									throw new CommonException(personMap.get("LOCAL_NAME")+ "已经进行了休假申请");
								}
								
							 }
						}
						//修改考勤的类型
						if("TSTO".equals(paramMap.get("CPNY_ID"))){
							if (!paramMap.get("OLD_DATE_TYPE").equals(paramMap.get("DATE_TYPE"))) {
								String kaoQinNo = "";
									 kaoQinNo = infoApplyLeaveDao.getkaoQinNo(paramMap);
								if ("141439".equals(kaoQinNo) || "141440".equals(kaoQinNo)) {
									String APPLY_NO_FOR_SHIFTNO = infoApplyLeaveDao.getkaoQinNoPkNo(paramMap);
									paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
									this.infoApplyDao.updatArDetailBatchApplyForDATETYPE(paramMap);
								}else {
									throw new CommonException(personMap.get("LOCAL_NAME")+ "已经进行了休假申请");
								}
								
							}
						}
						//修改考勤的班组
						if("TSTO".equals(paramMap.get("CPNY_ID"))){
							if (!paramMap.get("OLD_GROUP_ID").equals(paramMap.get("GROUP_ID"))) {
								String kaoQinNo = "";
								
								   kaoQinNo = infoApplyLeaveDao.getkaoQinNo(paramMap);
								if ("141439".equals(kaoQinNo) || "141440".equals(kaoQinNo)) {
									String APPLY_NO_FOR_SHIFTNO = infoApplyLeaveDao.getkaoQinNoPkNo(paramMap);
									paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
									this.infoApplyDao.updatArDetailBatchApplyForGROUP(paramMap);
								}else {
									throw new CommonException(personMap.get("LOCAL_NAME")+ "已经进行了休假申请");
								}
								
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
								//部门长申请(后)
								if ("14014306".equals(AFFIRM_FLAG)) {
									//把原先的记录修改为初始
									String SHIFT_NO = (String) paramMap.get("OLD_SHIFT_NO");
									LinkedHashMap dataMap = new LinkedHashMap();
									dataMap.put("SHIFT_NO",SHIFT_NO);
									dataMap.put("FROM_DATE",paramMap.get("APPLY_DATE"));
									dataMap.put("CPNY_ID",admin.getCpnyId());
								    LinkedHashMap   shiftMap  = this.getWorkTimeFirstLast(dataMap);  
									
									paramMap.put("OT_FROM_TIME", shiftMap.get("FIRST_TIME"));
									paramMap.put("OT_TO_TIME", shiftMap.get("LAST_TIME"));
									paramMap.put("APPLY_LENGTH", 0.00);
									this.infoApplyDao.updatSubmitArDetailBatchApplyForChuShi(paramMap);
									this.infoApplyDao.updateOtApplyInArDetailBatchApply(paramMap);//修改申请表
								}else {
									this.infoApplyDao.updatSubmitArDetailBatchApply(paramMap);
									this.infoApplyDao.updateOtApplyInArDetailBatchApply(paramMap);//修改申请表
								}
								
							}
						} else {
							String ERROR_MSG = (String) paramMap.get("ERROR_MSG");
							 throw new CommonException(ERROR_MSG);
						}
						//SST删除空数据
						if ("SST".equals(paramMap.get("CPNY_ID"))) {
							 this.infoApplyLeaveDao.deleteAllDataForAdd(paramMap);
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
		
		@SuppressWarnings("unchecked")
		@Override
		public int saveArOvertimeManagent(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap personMap = new LinkedHashMap();
			personMap.put("APPLY_PERSON", admin.getPersonId());
			String op_flag = request.getParameter("OP_FLAG")!=null?request.getParameter("OP_FLAG"):"0";
			String jsonString = request.getParameter("jsonData") ;
			//List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			try {
				// 批量封装加班申请数据并处理
				//dataList = this.doOTApplyListForBatchByJson(dataList,admin);
				List<LinkedHashMap<String, Object>> dataList = this.encapsulationApplyNoListForBatch(request);
				
				for(int i=0;i<dataList.size();i++){
					LinkedHashMap paramMap = (LinkedHashMap) dataList.get(i);
				    personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);
				    String AFFIRM_FLAG = (String) paramMap.get("AFFIRM_FLAG");
					boolean passFlag = false;
					passFlag = this.arApplyCheckBatchOverTime(paramMap, personMap, admin.getLanguage());
					String PK_NO = (String) paramMap.get("APPLY_NO");
					paramMap.put("PK_NO", PK_NO);
					paramMap.put("CPNY_ID", "TSTO");
					
					//获取数据库该条选中数据的决裁状态
//					String oldAffrim = infoApplyLeaveDao.getChechedAffrim(paramMap) ;
//					paramMap.put("oldAffrim", oldAffrim);
					LinkedHashMap oldOTMap = this.infoApplyDao.getOldWorkTimeInfoByPkNO(paramMap);
					paramMap.put("OLD_ADJST_YN", oldOTMap.get("ADJST_YN"));
					
					//修改考勤的班次及时间
					if (!oldOTMap.get("SHIFT_NO").toString().equals(paramMap.get("SHIFT_NO").toString())) {
						String kaoQinNo = "";
						   kaoQinNo = infoApplyLeaveDao.getkaoQinNo(paramMap);
						
						if ("141439".equals(kaoQinNo) || "141440".equals(kaoQinNo)) {
							String APPLY_NO_FOR_SHIFTNO = infoApplyLeaveDao.getkaoQinNoPkNo(paramMap);
							//通过加班修改班次，获取修改班次的开始结束时间
							LinkedHashMap flMap = new LinkedHashMap();
							flMap.put("CPNY_ID",admin.getCpnyId() );
							flMap.put("from_date", paramMap.get("APPLY_DATE"));
							flMap.put("SHIFT_NO", paramMap.get("SHIFT_NO"));
							LinkedHashMap timeMap  = infoApplyDao.getOtApplyWorkTimeU(flMap) ;
							paramMap.put("FIRST_FOR_SHIFTNO",timeMap.get("FIRST_TIME"));
							paramMap.put("LAST_FOR_SHIFTNO", timeMap.get("LAST_TIME"));
							//修改考勤的班次，开始结束时间
							paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
							this.infoApplyDao.updatArDetailBatchApplyForSHIFT(paramMap);
						}else {
							throw new CommonException(personMap.get("LOCAL_NAME")+ "已经进行了休假申请");
						}
						
					 }
					//修改考勤的类型
					if (!oldOTMap.get("DATE_TYPE").toString().equals(paramMap.get("DATE_TYPE").toString())) {
						String kaoQinNo = "";
							 kaoQinNo = infoApplyLeaveDao.getkaoQinNo(paramMap);
						if ("141439".equals(kaoQinNo) || "141440".equals(kaoQinNo)) {
							String APPLY_NO_FOR_SHIFTNO = infoApplyLeaveDao.getkaoQinNoPkNo(paramMap);
							paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
							this.infoApplyDao.updatArDetailBatchApplyForDATETYPE(paramMap);
						}else {
							throw new CommonException(personMap.get("LOCAL_NAME")+ "已经进行了休假申请");
						}
						
					}
					//修改考勤的班组
					if (!oldOTMap.get("GROUP_ID").toString().equals(paramMap.get("GROUP_ID").toString())) {
						String kaoQinNo = "";
						
						   kaoQinNo = infoApplyLeaveDao.getkaoQinNo(paramMap);
						if ("141439".equals(kaoQinNo) || "141440".equals(kaoQinNo)) {
							String APPLY_NO_FOR_SHIFTNO = infoApplyLeaveDao.getkaoQinNoPkNo(paramMap);
							paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
							this.infoApplyDao.updatArDetailBatchApplyForGROUP(paramMap);
						}else {
							throw new CommonException(personMap.get("LOCAL_NAME")+ "已经进行了休假申请");
						}
						
					}
					
					 //开始:14014306; 部门申请: 14014307;部门长批准 ：14014308;部门长返回:14014309;上申取消:14014310;部门申请(后):14014311;部门长批准(后):14014312
					if (passFlag==true) {
						if ("14014306".equals(oldOTMap.get("AFFIRM_FLAG").toString())) {
							//如果做部门申请，初始状态就做插入
							this.infoApplyDao.addOtApplyInArDetailBatch(paramMap);
							//this.infoApplyDao.updatSubmitLeaveApplyInBatchForDeletePrepare(paramMap); 
					        //this.infoApplyDao.insertSubmitLeaveApplyInBatch(paramMap); 
							this.infoApplyDao.updatSubmitArDetailBatchApplyForFirstAdd(paramMap);//修改detail表
						} else {
							//部门长申请(后)
							if ("14014306".equals(paramMap.get("AFFIRM_FLAG").toString())) {
								//把原先的记录修改为初始
								String SHIFT_NO = (String) oldOTMap.get("SHIFT_NO");
								LinkedHashMap dataMap = new LinkedHashMap();
								dataMap.put("SHIFT_NO",SHIFT_NO);
								dataMap.put("FROM_DATE",paramMap.get("APPLY_DATE"));
								dataMap.put("CPNY_ID",admin.getCpnyId());
							    LinkedHashMap   shiftMap  = this.getWorkTimeFirstLast(dataMap);  
								
								paramMap.put("OT_FROM_TIME", shiftMap.get("FIRST_TIME"));
								paramMap.put("OT_TO_TIME", shiftMap.get("LAST_TIME"));
								paramMap.put("APPLY_LENGTH", 0.00);
								this.infoApplyDao.updatSubmitArDetailBatchApplyForChuShi(paramMap);
								this.infoApplyDao.updateOtApplyInArDetailBatchApply(paramMap);//修改申请表
							}else {
								this.infoApplyDao.updatSubmitArDetailBatchApply(paramMap);
								this.infoApplyDao.updateOtApplyInArDetailBatchApply(paramMap);//修改申请表
							}
							
						}
					} else {
						String ERROR_MSG = (String) paramMap.get("ERROR_MSG");
						 throw new CommonException(ERROR_MSG);
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
		 * 倒休管理的操作（增、删）
		 */
		@SuppressWarnings("unchecked")
		@Override
		public int delOtAdjustApplyAffirmForm(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap personMap = new LinkedHashMap();
			personMap.put("APPLY_PERSON", admin.getPersonId());
			String op_flag = request.getParameter("OP_FLAG")!=null?request.getParameter("OP_FLAG"):"0";
			List list =new ArrayList();
			List deleteList =new ArrayList();
			try {
				// 批量封装加班申请数据并处理
				if (!"2".equals(op_flag)&&!"0".equals(op_flag)) {
				     list = this.encapsulationAdjustHolidayListForBatch(request);
				}
				if("0".equals(op_flag)){//删除
					deleteList = encapsulationAdjustHolidayListForDelete(request);
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
						passFlag = this.arApplyCheckBatchAdjust(paramMap, personMap, admin.getLanguage());
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
						
						//修改类型
						
							if (!paramMap.get("OLD_DATE_TYPE").equals(paramMap.get("DATE_TYPE"))) {
								String kaoQinNo = "";
									 kaoQinNo = infoApplyLeaveDao.getkaoQinNo(paramMap);
								if ("141439".equals(kaoQinNo) || "141440".equals(kaoQinNo)) {
									String APPLY_NO_FOR_SHIFTNO = infoApplyLeaveDao.getkaoQinNoPkNo(paramMap);
									paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
									this.infoApplyDao.updatArDetailBatchApplyForDATETYPE(paramMap);
								}else {
									throw new CommonException(personMap.get("LOCAL_NAME")+ "已经进行了休假申请");
								}
								
							}
						
						//修改考勤的班组
							if (!paramMap.get("OLD_GROUP_ID").equals(paramMap.get("GROUP_ID"))) {
								String kaoQinNo = "";
								
								   kaoQinNo = infoApplyLeaveDao.getkaoQinNo(paramMap);
								if ("141439".equals(kaoQinNo) || "141440".equals(kaoQinNo)) {
									String APPLY_NO_FOR_SHIFTNO = infoApplyLeaveDao.getkaoQinNoPkNo(paramMap);
									paramMap.put("APPLY_NO_FOR_SHIFTNO", APPLY_NO_FOR_SHIFTNO);
									this.infoApplyDao.updatArDetailBatchApplyForGROUP(paramMap);
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
								    LinkedHashMap   shiftMap  = this.getWorkTimeFirstLast(dataMap);  
									
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
		 * SST加班管理
		 */
		@SuppressWarnings("unchecked")
		@Override
		public int delLOvertimeApplyInBatchSST(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap personMap = new LinkedHashMap();
			personMap.put("APPLY_PERSON", admin.getPersonId());
			String op_flag = request.getParameter("OP_FLAG")!=null?request.getParameter("OP_FLAG"):"0";
			List list =new ArrayList();
			List deleteList =new ArrayList();
			try {
				// 批量封装加班申请数据并处理
				if (!"2".equals(op_flag)&&!"0".equals(op_flag)) {
					list = this.encapsulationApplyNoListForBatchSST(request);
				}
				if("0".equals(op_flag)){//删除
					String[] paramData = request.getParameterValues("BATCH_OT");
					for (int i = 0; i < paramData.length; i++) {
						String APPLY_NO = paramData[i];
						LinkedHashMap paramMap= new LinkedHashMap();
						paramMap.put("APPLY_NO", APPLY_NO);
						paramMap.put("CPNY_ID", admin.getCpnyId());
						deleteList.add(paramMap);
					}
					this.infoApplyDao.delOvertimeApplyInBatch(deleteList);//SST加班数据的删除
				}else if("2".equals(op_flag)) {//SST添加
					LinkedHashMap map = new LinkedHashMap();
					map.put("CPNY_ID",admin.getCpnyId());
					map.put("CREATED_BY",admin.getAdminID());
					map.put("CREATED_IP",admin.getAdminIP());
					map.put("applyBatchdate",request.getParameter("APPLY_DATE"));
					this.infoApplyLeaveDao.addtOtApplyInBatch(map);
				}else{//提交
					for(int i=0;i<list.size();i++){
						LinkedHashMap paramMap = (LinkedHashMap) list.get(i);
						personMap = (LinkedHashMap) this.infoApplyLeaveDao.getPersonInfoByPersonId(paramMap);
						String AFFIRM_FLAG = (String) paramMap.get("AFFIRM_FLAG");
						boolean passFlag = false;
						passFlag = this.arApplyCheckBatch2(paramMap, personMap, admin.getLanguage());
						String PK_NO = (String) paramMap.get("APPLY_NO");
						paramMap.put("PK_NO", PK_NO);
						//SST用
						
						//获取数据库该条选中数据的决裁状态
						String oldAffrim = infoApplyLeaveDao.getChechedAffrim(paramMap) ;
						paramMap.put("oldAffrim", oldAffrim);
						//开始:14014306; 部门申请: 14014307;部门长批准 ：14014308;部门长返回:14014309;上申取消:14014310;部门申请(后):14014311;部门长批准(后):14014312
						if (passFlag==true) {
							if ("14014306".equals(oldAffrim)) {
								//如果做部门申请，初始状态就做插入
								this.infoApplyDao.addOtApplyInArDetailBatch(paramMap);
								this.infoApplyDao.updatSubmitArDetailBatchApplyForFirstAddSST(paramMap);//修改detail表
							} else {
								//部门长申请(后)
								if ("14014306".equals(AFFIRM_FLAG)) {
									this.infoApplyDao.updatSubmitArDetailBatchApplyForChuShiSST(paramMap);
									this.infoApplyDao.updateOtApplyInArDetailBatchApply(paramMap);//修改申请表
								}else {
									this.infoApplyDao.updatSubmitArDetailBatchApplySST(paramMap);
									this.infoApplyDao.updateOtApplyInArDetailBatchApply(paramMap);//修改申请表
								}
								
							}
						} else {
							throw new CommonException(TipMessage.getTipMessage(
									"alert.message.ess.infoApply.checkHasNotThrough",
									admin.getLanguage()));
						}
						//SST删除空数据
						if ("SST".equals(paramMap.get("CPNY_ID"))) {
							this.infoApplyLeaveDao.deleteAllDataForAdd(paramMap);
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
		
		
		public LinkedHashMap getWorkTimeFirstLast(LinkedHashMap dataMap) {
			
			return infoApplyDao.getWorkTimeFirstLast(dataMap);
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
					String ADJST_YN = paramMap.get("ADJST_YN"+paramData[i]) != null ? paramMap.get("ADJST_YN"+paramData[i]).toString():"0";//调休标识
					String OLD_ADJST_YN = paramMap.get("OLD_ADJST_YN"+paramData[i]) != null ? paramMap.get("OLD_ADJST_YN"+paramData[i]).toString():"0";//OLD调休标识
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
					String APPLY_LOCK = paramMap.get("APPLY_LOCK"+paramData[i]) != null ? paramMap.get("APPLY_LOCK"+paramData[i]).toString():"0";		
					String Lotlengthonehour = paramMap.get("Lotlengthonehour"+paramData[i]) != null ? paramMap.get("Lotlengthonehour"+paramData[i]).toString():"0";		
					String Lotlengthonemin = paramMap.get("Lotlengthonemin"+paramData[i]) != null ? paramMap.get("Lotlengthonemin"+paramData[i]).toString():"0";		
					String allowance = paramMap.get("allowance"+paramData[i]) != null ? paramMap.get("allowance"+paramData[i]).toString():"0";		
					String FIRST_TIME = paramMap.get("FIRST_TIME"+paramData[i]) != null ? paramMap.get("FIRST_TIME"+paramData[i]).toString():"";		
					String LAST_TIME = paramMap.get("LAST_TIME"+paramData[i]) != null ? paramMap.get("LAST_TIME"+paramData[i]).toString():"";		
					String POST_GRADE_NO = paramMap.get("POST_GRADE_NO"+paramData[i]) != null ? paramMap.get("POST_GRADE_NO"+paramData[i]).toString():"";		
					String CONFIRM_FLAG = paramMap.get("CONFIRM_FLAG"+paramData[i]) != null ? paramMap.get("CONFIRM_FLAG"+paramData[i]).toString():"0";		
					String OLD_SHIFT_NO = paramMap.get("OLD_SHIFT_NO"+paramData[i]) != null ? 
							paramMap.get("OLD_SHIFT_NO"+paramData[i]).toString():"";
					String OLD_DATE_TYPE = paramMap.get("OLD_DATE_TYPE"+paramData[i]) != null ? 
									paramMap.get("OLD_DATE_TYPE"+paramData[i]).toString():"";
					String OLD_GROUP_ID = paramMap.get("OLD_GROUP_ID"+paramData[i]) != null ? 
											paramMap.get("OLD_GROUP_ID"+paramData[i]).toString():"";
					String OLD_APPLY_NO = paramMap.get("OLD_APPLY_NO"+paramData[i]) != null ? 
													paramMap.get("OLD_APPLY_NO"+paramData[i]).toString():"";
					
					
				
					map.put("SHIFT_NO", SHIFT_NO);
					map.put("OLD_SHIFT_NO", OLD_SHIFT_NO);
					map.put("interLanguage", admin.getLanguage());
					map.put("POST_GRADE_NO", POST_GRADE_NO);
					map.put("FIRST_TIME", FIRST_TIME);
					map.put("LAST_TIME", LAST_TIME);
					map.put("GROUP_ID", GROUP_ID);
					map.put("OLD_GROUP_ID", OLD_GROUP_ID);
					map.put("DATE_TYPE", DATE_TYPE);
					map.put("APPLY_LOCK", APPLY_LOCK);
					map.put("OLD_DATE_TYPE", OLD_DATE_TYPE);
					map.put("CONFIRM_FLAG", CONFIRM_FLAG);
					map.put("Lotlengthonehour", Lotlengthonehour);
					map.put("Lotlengthonemin", Lotlengthonemin);
					
					
				    map.put("AR_DATE_STR", APPLY_DATE);
					map.put("DEPTNO", DEPTNO);
					map.put("UNIT", UNIT);
					map.put("allowance", allowance);
					map.put("STATUS_CODE", STATUS_CODE);
					map.put("STATUS_NAME", STATUS_NAME);
					map.put("IWEEK", IWEEK);
					map.put("LOCK_YN", LOCK_YN);
					map.put("ADJST_YN", ADJST_YN);
					map.put("OLD_ADJST_YN", OLD_ADJST_YN);//判断用
					if ("1".equals(ADJST_YN)) {//调休
						map.put("ITEM_NO", "141452");
					} else {
						if ("1440".equals(DATE_TYPE)) {
							map.put("ITEM_NO", "141444");
						}else if ("1441".equals(DATE_TYPE)) {
							map.put("ITEM_NO", "141445");
						}else {
							map.put("ITEM_NO", "141446");
						}
					}
					if ("1".equals(ADJST_YN)) {//调休
						map.put("APPLY_TYPE_CODE","141471");
					} else {
						map.put("APPLY_TYPE_CODE", APPLY_TYPE_CODE);//申请类型
						if ("1440".equals(DATE_TYPE)) {
							map.put("APPLY_TYPE_CODE", "32");
						}else if ("1441".equals(DATE_TYPE)) {
							map.put("APPLY_TYPE_CODE", "33");
						}else {
							map.put("APPLY_TYPE_CODE", "34");
						}
					}
					
					map.put("AFFIRM_FLAG", AFFIRM_FLAG);
					map.put("reason", reason);
					map.put("otherReason", otherReason);
					map.put("APPLY_LENGTH", APPLY_LENGTH);
					map.put("fromTime", fromTime);
					map.put("toTime", toTime);
					map.put("APPLY_DATE", APPLY_DATE);
					map.put("PERSON_ID", personId);
					map.put("CREATED_BY", admin.getPersonId());
					if(OLD_APPLY_NO !=null && !"".equals(OLD_APPLY_NO)){
						map.put("APPLY_NO", OLD_APPLY_NO);
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
								 personId, this.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),APPLY_TYPE_CODE).toString(),APPLY_LENGTH);
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
		/**
		 * 加班管理页面 保存时 批量封装List
		 * 
		 * @param request
		 * @return
		 */
		@SuppressWarnings("unchecked")
		private List<LinkedHashMap<String, Object>> doOTApplyListForBatchByJson(List<LinkedHashMap<String, Object>> list,AdminBean admin) {
			List<LinkedHashMap<String, Object>> dataList = new ArrayList();
			try {
				for (int i = 0; i < list.size(); i++) {
					LinkedHashMap map = list.get(i);
					String personId = map.get("PERSON_ID").toString();
					String ADJST_YN = map.get("ADJST_YN").toString();
					String DATE_TYPE = map.get("DATE_TYPE").toString();
					String AFFIRM_FLAG = map.get("AFFIRM_FLAG").toString();
					String fromTime = map.get("fromTime").toString();
					String toTime = map.get("toTime").toString();
					String APPLY_TYPE_CODE = map.get("APPLY_TYPE_CODE").toString();
					String APPLY_DATE = map.get("AR_DATE_STR").toString();
					map.put("APPLY_DATE", APPLY_DATE.replaceAll("\\.", "/"));
					String APPLY_LENGTH = map.get("APPLY_LENGTH").toString();
					if ("1".equals(ADJST_YN)) {//调休
						map.put("ITEM_NO", "141452");
					} else {
						if ("1440".equals(DATE_TYPE)) {
							map.put("ITEM_NO", "141444");
						}else if ("1441".equals(DATE_TYPE)) {
							map.put("ITEM_NO", "141445");
						}else {
							map.put("ITEM_NO", "141446");
						}
					}
					if ("1".equals(ADJST_YN)) {//调休
						map.put("APPLY_TYPE_CODE","141471");
					} else {
						if ("1440".equals(DATE_TYPE)) {
							APPLY_TYPE_CODE = "32";
							map.put("APPLY_TYPE_CODE", "32");
						}else if ("1441".equals(DATE_TYPE)) {
							APPLY_TYPE_CODE = "33";
							map.put("APPLY_TYPE_CODE", "33");
						}else {
							APPLY_TYPE_CODE = "34";
							map.put("APPLY_TYPE_CODE", "34");
						}
					}
					map.put("CREATED_BY", admin.getPersonId());
					map.put("APPLY_TYPE", "21");//??
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
								 personId, this.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),APPLY_TYPE_CODE).toString(),APPLY_LENGTH);
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
					dataList.add(map);
				}
				return dataList;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		/**
		 * 批量封装倒休信息成List(encapsulation information apply from request to List for
		 * batch)
		 * 
		 * @param request
		 * @return
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		public List encapsulationAdjustHolidayListForBatch(HttpServletRequest request) throws Exception {
			List list = new ArrayList();
			LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				String[] paramData = request.getParameterValues("BATCH_ADJUST");
				for (int i = 0; i < paramData.length; i++) {
					LinkedHashMap map = this.getEncapsulationAdjustHoliday(paramMap,paramData[i],admin);
					list.add(map);
				}
				return list;
		}
		/**
		 * 批量封装倒休删除信息成List(encapsulation information apply from request to List for
		 * batch)
		 * 
		 * @param request
		 * @return
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		public List encapsulationAdjustHolidayListForDelete(HttpServletRequest request) throws Exception {
			List deleteList = new ArrayList();
			LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String[] paramData = request.getParameterValues("BATCH_ADJUST");
			for (int i = 0; i < paramData.length; i++) {
				String APPLY_NO = paramData[i];
				LinkedHashMap map= new LinkedHashMap();
				String APPLY_DATE = paramMap.get("APPLY_DATE"+APPLY_NO) != null ? paramMap.get("APPLY_DATE"+APPLY_NO).toString():"";
				String SHIFT_NO = paramMap.get("SHIFT_NO"+APPLY_NO) != null ? paramMap.get("SHIFT_NO"+APPLY_NO).toString():"";
				String DATE_TYPE = paramMap.get("DATE_TYPE"+APPLY_NO) != null ? paramMap.get("DATE_TYPE"+APPLY_NO).toString():"";
				map.put("APPLY_NO", APPLY_NO);
				map.put("APPLY_DATE", APPLY_DATE);
				map.put("SHIFT_NO", SHIFT_NO);
				map.put("CPNY_ID", admin.getCpnyId());
				
					if ("1440".equals(DATE_TYPE)) {
						map.put("ITEM_NO", "141444");
					}else if ("1441".equals(DATE_TYPE)) {
						map.put("ITEM_NO", "141445");
					}else {
						map.put("ITEM_NO", "141446");
					}
				
				deleteList.add(map);
			}
			return deleteList;
		}
		
		/**
		 * 加班管理--批量封装加班删除信息成List(encapsulation information apply from request to List for
		 * batch)
		 * 
		 * @param request
		 * @return
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		public List encapsulationOverTimeListForDelete(HttpServletRequest request) throws Exception {
			List deleteList = new ArrayList();
			LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String[] paramData = request.getParameterValues("c1");
			for (int i = 0; i < paramData.length; i++) {
				String APPLY_NO = paramData[i];
				LinkedHashMap map= new LinkedHashMap();
				String APPLY_DATE = paramMap.get("APPLY_DATE"+APPLY_NO) != null ? paramMap.get("APPLY_DATE"+APPLY_NO).toString():"";
				String SHIFT_NO = paramMap.get("SHIFT_NO"+APPLY_NO) != null ? paramMap.get("SHIFT_NO"+APPLY_NO).toString():"";
				String DATE_TYPE = paramMap.get("DATE_TYPE"+APPLY_NO) != null ? paramMap.get("DATE_TYPE"+APPLY_NO).toString():"";
				map.put("APPLY_NO", APPLY_NO);
				map.put("APPLY_DATE", APPLY_DATE);
				map.put("SHIFT_NO", SHIFT_NO);
				map.put("CPNY_ID", admin.getCpnyId());
				
					if ("1440".equals(DATE_TYPE)) {
						map.put("ITEM_NO", "141444");
					}else if ("1441".equals(DATE_TYPE)) {
						map.put("ITEM_NO", "141445");
					}else {
						map.put("ITEM_NO", "141446");
					}
				
				deleteList.add(map);
			}
			return deleteList;
		}
		
		@SuppressWarnings("unchecked")
		public LinkedHashMap getEncapsulationAdjustHoliday(LinkedHashMap paramMap, String adjustIndex,AdminBean admin)throws Exception {
			LinkedHashMap map = new LinkedHashMap();
			String personId = paramMap.get("personid"+adjustIndex) != null ? paramMap.get("personid"+adjustIndex).toString():admin.getPersonId();
			String APPLY_DATE = paramMap.get("APPLY_DATE"+adjustIndex) != null ? paramMap.get("APPLY_DATE"+adjustIndex).toString():"";
			String fromTime = paramMap.get("fromTime"+adjustIndex) != null ? paramMap.get("fromTime"+adjustIndex).toString():"";
			String toTime = paramMap.get("toTime"+adjustIndex) != null ? paramMap.get("toTime"+adjustIndex).toString():"";
			String APPLY_LENGTH = paramMap.get("APPLY_LENGTH"+adjustIndex) != null ? paramMap.get("APPLY_LENGTH"+adjustIndex).toString():"0";
			String reason = paramMap.get("reason"+adjustIndex) != null ? paramMap.get("reason"+adjustIndex).toString():"";
			String otherReason = paramMap.get("otherReason"+adjustIndex) != null ? paramMap.get("otherReason"+adjustIndex).toString():"";
			String AFFIRM_FLAG = paramMap.get("AFFIRM_FLAG"+adjustIndex) != null ? paramMap.get("AFFIRM_FLAG"+adjustIndex).toString():"14014306";
			String ITEM_NO = paramMap.get("ITEM_NO"+adjustIndex) != null ? paramMap.get("ITEM_NO"+adjustIndex).toString():"";
			String ADJST_YN = paramMap.get("ADJST_YN"+adjustIndex) != null ? paramMap.get("ADJST_YN"+adjustIndex).toString():"0";//调休标识
			String APPLY_TYPE_CODE = paramMap.get("APPLY_TYPE_CODE"+adjustIndex) != null ? paramMap.get("APPLY_TYPE_CODE"+adjustIndex).toString():"";
			String SHIFT_NO = paramMap.get("SHIFT_NO"+adjustIndex) != null ? paramMap.get("SHIFT_NO"+adjustIndex).toString():"";
			String GROUP_ID = paramMap.get("GROUP_ID"+adjustIndex) != null ? paramMap.get("GROUP_ID"+adjustIndex).toString():"";
			String DEPTNO = paramMap.get("DEPTNO"+adjustIndex) != null ? paramMap.get("DEPTNO"+adjustIndex).toString():"";
			String DATE_TYPE = paramMap.get("DATE_TYPE"+adjustIndex) != null ? paramMap.get("DATE_TYPE"+adjustIndex).toString():"";
			String UNIT = paramMap.get("UNIT"+adjustIndex) != null ? paramMap.get("UNIT"+adjustIndex).toString():"";
			String STATUS_CODE = paramMap.get("STATUS_CODE"+adjustIndex) != null ? paramMap.get("STATUS_CODE"+adjustIndex).toString():"";
			String STATUS_NAME = paramMap.get("STATUS_NAME"+adjustIndex) != null ? paramMap.get("STATUS_NAME"+adjustIndex).toString():"";
			String IWEEK = paramMap.get("IWEEK"+adjustIndex) != null ? paramMap.get("IWEEK"+adjustIndex).toString():"";
			String LOCK_YN = paramMap.get("LOCK_YN"+adjustIndex) != null ? paramMap.get("LOCK_YN"+adjustIndex).toString():"";		
			String APPLY_LOCK = paramMap.get("APPLY_LOCK"+adjustIndex) != null ? paramMap.get("APPLY_LOCK"+adjustIndex).toString():"0";		
			String Lotlengthonehour = paramMap.get("Lotlengthonehour"+adjustIndex) != null ? paramMap.get("Lotlengthonehour"+adjustIndex).toString():"0";		
			String Lotlengthonemin = paramMap.get("Lotlengthonemin"+adjustIndex) != null ? paramMap.get("Lotlengthonemin"+adjustIndex).toString():"0";		
			String allowance = paramMap.get("allowance"+adjustIndex) != null ? paramMap.get("allowance"+adjustIndex).toString():"0";		
			String FIRST_TIME = paramMap.get("FIRST_TIME"+adjustIndex) != null ? paramMap.get("FIRST_TIME"+adjustIndex).toString():"";		
			String LAST_TIME = paramMap.get("LAST_TIME"+adjustIndex) != null ? paramMap.get("LAST_TIME"+adjustIndex).toString():"";		
			String POST_GRADE_NO = paramMap.get("POST_GRADE_NO"+adjustIndex) != null ? paramMap.get("POST_GRADE_NO"+adjustIndex).toString():"";		
			String CONFIRM_FLAG = paramMap.get("CONFIRM_FLAG"+adjustIndex) != null ? paramMap.get("CONFIRM_FLAG"+adjustIndex).toString():"0";		
			String OLD_SHIFT_NO = paramMap.get("OLD_SHIFT_NO"+adjustIndex) != null ? paramMap.get("OLD_SHIFT_NO"+adjustIndex).toString():"";
			String OLD_DATE_TYPE = paramMap.get("OLD_DATE_TYPE"+adjustIndex) != null ? paramMap.get("OLD_DATE_TYPE"+adjustIndex).toString():"";
			String OLD_GROUP_ID = paramMap.get("OLD_GROUP_ID"+adjustIndex) != null ? paramMap.get("OLD_GROUP_ID"+adjustIndex).toString():"";
			String OLD_APPLY_NO = paramMap.get("OLD_APPLY_NO"+adjustIndex) != null ? paramMap.get("OLD_APPLY_NO"+adjustIndex).toString():"";
			String FINAL_DATE = paramMap.get("FINAL_DATE"+adjustIndex) != null ? paramMap.get("FINAL_DATE"+adjustIndex).toString():"";//倒休截止日期
											
			map.put("SHIFT_NO", SHIFT_NO);
			map.put("OLD_SHIFT_NO", OLD_SHIFT_NO);
			map.put("interLanguage", admin.getLanguage());
			map.put("POST_GRADE_NO", POST_GRADE_NO);
			map.put("FIRST_TIME", FIRST_TIME);
			map.put("LAST_TIME", LAST_TIME);
			map.put("GROUP_ID", GROUP_ID);
			map.put("OLD_GROUP_ID", OLD_GROUP_ID);
			map.put("DATE_TYPE", DATE_TYPE);
			map.put("APPLY_LOCK", APPLY_LOCK);
			map.put("OLD_DATE_TYPE", OLD_DATE_TYPE);
			map.put("CONFIRM_FLAG", CONFIRM_FLAG);
			map.put("Lotlengthonehour", Lotlengthonehour);
			map.put("Lotlengthonemin", Lotlengthonemin);
			map.put("FINAL_DATE", FINAL_DATE);//倒休截止日期								
											
			map.put("AR_DATE_STR", APPLY_DATE);
			map.put("DEPTNO", DEPTNO);
			map.put("UNIT", UNIT);
			map.put("allowance", allowance);
			map.put("STATUS_CODE", STATUS_CODE);
			map.put("STATUS_NAME", STATUS_NAME);
			map.put("IWEEK", IWEEK);
			map.put("LOCK_YN", LOCK_YN);
			map.put("ADJST_YN", ADJST_YN);
		if ("1".equals(ADJST_YN)) {//调休
			map.put("ITEM_NO", "141452");
		} else {
			if ("1440".equals(DATE_TYPE)) {
				map.put("ITEM_NO", "141444");
			}else if ("1441".equals(DATE_TYPE)) {
				map.put("ITEM_NO", "141445");
			}else {
				map.put("ITEM_NO", "141446");
			}
		}
		if ("1".equals(ADJST_YN)) {//调休
			map.put("APPLY_TYPE_CODE","141471");
		} else {
			map.put("APPLY_TYPE_CODE", APPLY_TYPE_CODE);//申请类型
			if ("1440".equals(DATE_TYPE)) {
				map.put("APPLY_TYPE_CODE", "32");
			}else if ("1441".equals(DATE_TYPE)) {
				map.put("APPLY_TYPE_CODE", "33");
			}else {
				map.put("APPLY_TYPE_CODE", "34");
			}
		}
											
			map.put("AFFIRM_FLAG", AFFIRM_FLAG);
			map.put("reason", reason);
			map.put("otherReason", otherReason);
			map.put("APPLY_LENGTH", APPLY_LENGTH);
			map.put("fromTime", fromTime);
			map.put("toTime", toTime);
			map.put("APPLY_DATE", APPLY_DATE);
			map.put("PERSON_ID", personId);
			map.put("CREATED_BY", admin.getPersonId());
			if (OLD_APPLY_NO != null && !"".equals(OLD_APPLY_NO)) {//新旧是相对添加时判断
				map.put("APPLY_NO", OLD_APPLY_NO);
			}else {
				map.put("APPLY_NO", adjustIndex);
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
											
			map.put("OT_FROM_TIME",APPLY_DATE + " " + fromTime.substring(0,2) + ":"+fromTime.substring(2,4)+ ":" + "00" );
		    map.put("OT_TO_TIME",APPLY_DATE + " " + toTime.substring(0,2) + ":"+toTime.substring(2,4)+ ":" + "00" );
		 try {
			LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(map);
			if (APPLY_TYPE_CODE  != null && !"".equals(APPLY_TYPE_CODE)) {
				List  affirmList =  this.getAffirmorListByString("31", 
				personId, this.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),APPLY_TYPE_CODE).toString(),APPLY_LENGTH);
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
			return map;
		} catch (Exception e) {
			throw new Exception("Encapsulation Adjust Data  Exception", e);
		}
  }


		/**
		 * 批量封装信息申请信息成List(encapsulation information apply from request to List for
		 * batch)
		 * 
		 * @param request
		 * @return
		 */
		@SuppressWarnings("unchecked")
		private List encapsulationApplyNoListForBatchSST(HttpServletRequest request) {
			List list = new ArrayList();
			LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			try {
				String[] paramData = request.getParameterValues("BATCH_OT");
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
					String ADJST_YN = paramMap.get("ADJST_YN"+paramData[i]) != null ? paramMap.get("ADJST_YN"+paramData[i]).toString():"0";//调休标识
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
					String APPLY_LOCK = paramMap.get("APPLY_LOCK"+paramData[i]) != null ? paramMap.get("APPLY_LOCK"+paramData[i]).toString():"0";		
					String Lotlengthonehour = paramMap.get("Lotlengthonehour"+paramData[i]) != null ? paramMap.get("Lotlengthonehour"+paramData[i]).toString():"0";		
					String Lotlengthonemin = paramMap.get("Lotlengthonemin"+paramData[i]) != null ? paramMap.get("Lotlengthonemin"+paramData[i]).toString():"0";		
					String allowance = paramMap.get("allowance"+paramData[i]) != null ? paramMap.get("allowance"+paramData[i]).toString():"0";		
					String FIRST_TIME = paramMap.get("FIRST_TIME"+paramData[i]) != null ? paramMap.get("FIRST_TIME"+paramData[i]).toString():"";		
					String LAST_TIME = paramMap.get("LAST_TIME"+paramData[i]) != null ? paramMap.get("LAST_TIME"+paramData[i]).toString():"";		
					String POST_GRADE_NO = paramMap.get("POST_GRADE_NO"+paramData[i]) != null ? paramMap.get("POST_GRADE_NO"+paramData[i]).toString():"";		
					String CONFIRM_FLAG = paramMap.get("CONFIRM_FLAG"+paramData[i]) != null ? paramMap.get("CONFIRM_FLAG"+paramData[i]).toString():"0";		
					String OLD_SHIFT_NO = paramMap.get("OLD_SHIFT_NO"+paramData[i]) != null ? 
							paramMap.get("OLD_SHIFT_NO"+paramData[i]).toString():"";
					String OLD_DATE_TYPE = paramMap.get("OLD_DATE_TYPE"+paramData[i]) != null ? 
									paramMap.get("OLD_DATE_TYPE"+paramData[i]).toString():"";
					String OLD_GROUP_ID = paramMap.get("OLD_GROUP_ID"+paramData[i]) != null ? 
											paramMap.get("OLD_GROUP_ID"+paramData[i]).toString():"";
					String OLD_APPLY_NO = paramMap.get("OLD_APPLY_NO"+paramData[i]) != null ? 
													paramMap.get("OLD_APPLY_NO"+paramData[i]).toString():"";
					
					
					
				
					map.put("SHIFT_NO", SHIFT_NO);
					map.put("OLD_SHIFT_NO", OLD_SHIFT_NO);
					map.put("interLanguage", admin.getLanguage());
					map.put("POST_GRADE_NO", POST_GRADE_NO);
					map.put("FIRST_TIME", FIRST_TIME);
					map.put("LAST_TIME", LAST_TIME);
					map.put("GROUP_ID", GROUP_ID);
					map.put("OLD_GROUP_ID", OLD_GROUP_ID);
					map.put("DATE_TYPE", DATE_TYPE);
					map.put("APPLY_LOCK", APPLY_LOCK);
					map.put("OLD_DATE_TYPE", OLD_DATE_TYPE);
					map.put("CONFIRM_FLAG", CONFIRM_FLAG);
					map.put("Lotlengthonehour", Lotlengthonehour);
					map.put("Lotlengthonemin", Lotlengthonemin);
					
					
				    map.put("AR_DATE_STR", APPLY_DATE);
					map.put("DEPTNO", DEPTNO);
					map.put("UNIT", UNIT);
					map.put("allowance", allowance);
					map.put("STATUS_CODE", STATUS_CODE);
					map.put("STATUS_NAME", STATUS_NAME);
					map.put("IWEEK", IWEEK);
					map.put("LOCK_YN", LOCK_YN);
					map.put("ADJST_YN", ADJST_YN);
					if ("1".equals(ADJST_YN)) {//调休
						map.put("ITEM_NO", "141452");
					} else {
						if ("1440".equals(DATE_TYPE)) {
							map.put("ITEM_NO", "141444");
						}else if ("1441".equals(DATE_TYPE)) {
							map.put("ITEM_NO", "141445");
						}else {
							map.put("ITEM_NO", "141446");
						}
					}
					if ("1".equals(ADJST_YN)) {//调休
						map.put("APPLY_TYPE_CODE","141471");
					} else {
						map.put("APPLY_TYPE_CODE", APPLY_TYPE_CODE);//申请类型
						if ("1440".equals(DATE_TYPE)) {
							map.put("APPLY_TYPE_CODE", "32");
						}else if ("1441".equals(DATE_TYPE)) {
							map.put("APPLY_TYPE_CODE", "33");
						}else {
							map.put("APPLY_TYPE_CODE", "34");
						}
					}
					
					map.put("AFFIRM_FLAG", AFFIRM_FLAG);
					map.put("reason", reason);
					map.put("otherReason", otherReason);
					map.put("APPLY_LENGTH", APPLY_LENGTH);
					map.put("fromTime", fromTime);
					map.put("toTime", toTime);
					map.put("APPLY_DATE", APPLY_DATE);
					map.put("PERSON_ID", personId);
					map.put("CREATED_BY", admin.getPersonId());
					if (!"".equals(OLD_APPLY_NO)) {
						map.put("APPLY_NO", OLD_APPLY_NO);
					}else {
						map.put("APPLY_NO", paramData[i]);
					}
					
					map.put("APPLY_TYPE", "21");
					map.put("CPNY_ID", admin.getCpnyId());
					map.put("CREATED_IP", admin.getAdminIP());
					map.put("UPDATED_IP", admin.getAdminIP());
					map.put("UPDATED_BY", admin.getPersonId());
					//通过决裁状态设置当前数据的有效性
					int ACTIVITY = 0;
					if ("14014306".equals(AFFIRM_FLAG)) {       ACTIVITY = 0;
					}else if ("14014307".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
					}else if ("14014309".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
					}else if ("14014310".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
					}else if ("14014311".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
					}else if ("14015193".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
				    }else if ("14015194".equals(AFFIRM_FLAG)) { ACTIVITY = 1;
				    }else if ("14015210".equals(AFFIRM_FLAG)) { ACTIVITY = 1;}
					map.put("ACTIVITY", ACTIVITY);
					
					map.put("OT_FROM_TIME",APPLY_DATE + " " + fromTime.substring(0,2) + ":"+fromTime.substring(2,4)+ ":" + "00" );
					map.put("OT_TO_TIME",APPLY_DATE + " " + toTime.substring(0,2) + ":"+toTime.substring(2,4)+ ":" + "00" );
					LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(map);
					if (APPLY_TYPE_CODE  != null && !"".equals(APPLY_TYPE_CODE)) {
						List  affirmList =  this.getAffirmorListByString("31", 
								 personId, this.getApplyTypeCodeForAffirmList(personId, admin.getCpnyId(),APPLY_TYPE_CODE).toString(),APPLY_LENGTH);
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
		
		/**
		 * 
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
		
		/**
		 * 加班信息查询(search ot info list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getSearchApplyOtInfoList(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List returnList = new ArrayList();
		
			LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
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
			}
			/*paramMap.put("PERSON_ID001",admin.getAdminID());
			//考勤担当权限
			int authority = authorityUtil.isArUser(admin.getPersonId());
			if(authority == 1){
				paramMap.put("authority", "ArUser");
			}*/
				returnList = arDetailDao.getSearchApplyOtInfoList(paramMap);
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
		public List getMyhomeCarInfoList(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List returnList = new ArrayList();
			
			LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
			//paramMap.put("PERSON_ID001",admin.getAdminID());
			returnList = arDetailDao.getMyhomeCarInfoList(paramMap);
			return returnList;
		}
		/**
		 * 倒休搜索(search ot info list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getSearchApplyAdjustInfoList(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List returnList = new ArrayList();
			
			LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
			paramMap.put("PERSON_ID001",admin.getAdminID());
			String firstFlag = request.getParameter("firstFlag");
			if (firstFlag ==null || "".equals(firstFlag)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
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
			//考勤担当权限
			int authority = authorityUtil.isArUser(admin.getPersonId());
			if(authority == 1){
				paramMap.put("authority", "ArUser");
			}
			returnList = arDetailDao.getSearchApplyAdjustInfoList(paramMap);
			return returnList;
		}
		
		/**
		 * 考勤管理
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getApplyAttenanceManagentInfoList(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List returnList = new ArrayList();
			// 页面提交数据
			LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
			if ("TSTO".equals(admin.getCpnyId())) {
				String ITEM_NO = this.getItemNoOnApplyCode(paramMap);
				paramMap.put("ITEM_NO", ITEM_NO);
			}
			
			paramMap.put("APPLY_TYPE", "21");
			paramMap.put("PERSON_ID001", admin.getPersonId());
			
			//考勤担当权限
			int authority = authorityUtil.isArUser(admin.getPersonId());
			if(authority == 1){
				paramMap.put("authority", "ArUser");
			}
			returnList = arDetailDao.getApplyAttenanceManagentInfoList(paramMap);
			return returnList;
		}
		/**
		 * 考勤管理多天假的查询
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
			if ("TSTO".equals(admin.getCpnyId())) {
				String ITEM_NO = this.getItemNoOnApplyCode(paramMap);
				paramMap.put("ITEM_NO", ITEM_NO);
			}
			
			paramMap.put("APPLY_TYPE", "21");
			paramMap.put("PERSON_ID001", admin.getPersonId());
			
			//考勤担当权限
			int authority = authorityUtil.isArUser(admin.getPersonId());
			if(authority == 1){
				paramMap.put("authority", "ArUser");
			}
			returnList = arDetailDao.getBatchLeaveAffirmMoreDayInfoList(paramMap);
			return returnList;
		}
		/**
		 * 获取ITEM_NO
		 * @param paramMap
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public String getItemNoOnApplyCode(LinkedHashMap paramMap) {
			String APPLY_CODE = paramMap.get("APPLY_CODE").toString();
			String[] applyCodes = APPLY_CODE.split(",");  
			String itemNos = "";
			for (int i = 0; i < applyCodes.length; i++) {
				String applyCode = applyCodes[i].replace("\'","");
				LinkedHashMap map = new LinkedHashMap();
				map.put("interCpnyID", paramMap.get("interCpnyID"));
				map.put("APPLY_TYPE",applyCode);
				String itemNo = arDetailDao.getItemNoOnApplyCode(map);
				itemNos += itemNo+",";
			}
			
			return itemNos.substring(0,itemNos.length()-1);
		}
		/**
		 * 获取ITEM_NO
		 * @param paramMap
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public String getItemNoOnApplyCode2(LinkedHashMap paramMap) {
			String APPLY_CODE = paramMap.get("seach_APPLY_CODE").toString();
			String[] applyCodes = APPLY_CODE.split(",");  
			String itemNos = "";
			for (int i = 0; i < applyCodes.length; i++) {
				String applyCode = applyCodes[i].replace("\'","");
				LinkedHashMap map = new LinkedHashMap();
				map.put("interCpnyID", paramMap.get("interCpnyID"));
				map.put("APPLY_TYPE",applyCode);
				String itemNo = arDetailDao.getItemNoOnApplyCode(map);
				itemNos += itemNo+",";
			}
			
			return itemNos.substring(0,itemNos.length()-1);
		}


		/**
		 * 考勤管理(batch delete overtime apply)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
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
					this.arDetailDao.addtLeaveApplyInBatch(map);
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
					        //拆分多天的假
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
		
		/**
		 * 批量封装信息申请信息成List(encapsulation information apply from request to List for
		 * batch)
		 * 
		 * @param request
		 * @return
		 */
		@SuppressWarnings("unchecked")
		private List encapsulationApplyAttdanceNoListForBatch(HttpServletRequest request,String type) throws CommonException{
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
						int ACTIVITY = 0;
						if ("TSTO".equals(admin.getCpnyId())) {
							//通过决裁状态设置当前数据的有效性
							if ("14014306".equals(AFFIRM_FLAG)) {       ACTIVITY = 0;
							}else if ("14014307".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
							}else if ("14014308".equals(AFFIRM_FLAG)) { ACTIVITY = 1;
							}else if ("14014309".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
							}else if ("14014310".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
							}else if ("14014311".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
							}else if ("14014312".equals(AFFIRM_FLAG)) { ACTIVITY = 1;}
						}else {
							//通过决裁状态设置当前数据的有效性
							if ("14014306".equals(AFFIRM_FLAG)) {       ACTIVITY = 0;
							}else if ("14014307".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
							}else if ("14014309".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
							}else if ("14014310".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
							}else if ("14014311".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
							}else if ("14015193".equals(AFFIRM_FLAG)) { ACTIVITY = 0;
						    }else if ("14015194".equals(AFFIRM_FLAG)) { ACTIVITY = 1;
						    }else if ("14015210".equals(AFFIRM_FLAG)) { ACTIVITY = 1;}
						}
						map.put("ACTIVITY", ACTIVITY);
						map.put("STATUS_CODE", STATUS_CODE);				
						map.put("STATUS_NAME", STATUS_NAME);				
						map.put("IWEEK", IWEEK);				
					    map.put("personId", personId);
					    map.put("ITEM_NO", ITEM_NO);
					    map.put("FROM_DATE", FROM_DATE);
					    map.put("TO_DATE", TO_DATE);
					    map.put("OLD_FROM_DATE", OLD_FROM_DATE);
					    map.put("OLD_TO_DATE", OLD_TO_DATE);
					    map.put("fromTime", fromTime);
					    map.put("toTime", toTime);
					    map.put("fromTime_first", fromTime_first);
					    map.put("toTime_first", toTime_first);
					    map.put("AR_DATE_STR", FROM_DATE);
						map.put("AR_MONTH_STR", AR_MONTH_STR);
					    map.put("LEAVE_FROM_TIME", FROM_DATE + " " + fromTime + ":"+ "00");
						map.put("LEAVE_TO_TIME", TO_DATE + " " + toTime + ":"+ "00");
						map.put("OLD_LEAVE_FROM_TIME", OLD_FROM_DATE + " " + old_fromTime + ":"+ "00");
						map.put("OLD_LEAVE_TO_TIME", OLD_TO_DATE + " " + old_toTime + ":"+ "00");
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
						if ("TSTO".equals(admin.getCpnyId())) {
					    map.put("ajSeq", ajSeq);//调休的数据序列号
						map.put("ajQUANTITY", ajQUANTITY);//调休的数据序列号	
						}		
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
					    
						map.put("APPLY_NO", paramData[i]);
						map.put("CREATED_IP", admin.getAdminIP());
						map.put("CREATED_BY", admin.getPersonId());
						list.add(map);
					}
				return list;
			}catch (CommonException e1) {
				throw e1;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
			
		}
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
				String personId = paramMap.get("personid"+paramData[i]) != null ? 
						paramMap.get("personid"+paramData[i]).toString():admin.getPersonId();	
				String ajSeq = paramMap.get("ajSeq"+paramData[i]) != null ? 
								paramMap.get("ajSeq"+paramData[i]).toString():"";
			    String reason = paramMap.get("reason"+paramData[i]) != null ? 
								paramMap.get("reason"+paramData[i]).toString():"";
				String APPLY_LENGTH = paramMap.get("APPLY_LENGTH"+paramData[i]) != null ? 
								        paramMap.get("APPLY_LENGTH"+paramData[i]).toString():"";
				if ("TSTO".equals(admin.getCpnyId())) {
					dataMap.put("ajSeq", ajSeq);
					dataMap.put("reason", reason);
					dataMap.put("APPLY_LENGTH", APPLY_LENGTH);
				}	
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
				list.add(dataMap);
			}
				
			return list;
		}
		
		
		/**
		 * 倒休管理的验证
		 * 
		 * @param essOverTimeBean
		 * @return 返回 HashMap
		 */
		@SuppressWarnings("unchecked")
		private boolean arApplyCheckBatchAdjust(LinkedHashMap paramMap, LinkedHashMap personMap,
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
				checkMap.put("ITEM_NO",ITEM_NO);
				checkMap.put("fromTime", fromTime);
				checkMap.put("toTime", toTime);
				checkMap.put("PERSON_ID", paramMap.get("PERSON_ID"));
				String result = this.infoApplyLeaveDao.getAdjustCheckTSTO(checkMap);
				if ( !"OK".equals(result) ) {
					paramMap.put("ERROR_MSG",result);
					return false;
				}
			return true;
		}
		
		/**
		 * 加班管理的验证
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
				//加班时间验证
				LinkedHashMap checkMap = new LinkedHashMap();
				
				checkMap.put("AR_DATE_STR", AR_DATE_STR);
				checkMap.put("APPLY_LENGTH", APPLY_LENGTH);
				checkMap.put("ITEM_NO", ITEM_NO);
				checkMap.put("PK_NO",APPLY_NO);
				checkMap.put("fromTime", fromTime);
				checkMap.put("toTime", toTime);
				checkMap.put("PERSON_ID", paramMap.get("PERSON_ID"));
				String result = this.infoApplyLeaveDao.getOverTimeCheckTSTO(checkMap);
				if ( !"OK".equals(result) ) {
					paramMap.put("ERROR_MSG",result);
					return false;
				}
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
			String leaveTimeType = paramMap.get("LEAVE_TIME_TYPE")!= null ? paramMap.get("LEAVE_TIME_TYPE").toString():"";
			String APPLY_NO = paramMap.get("APPLY_NO")!= null ? paramMap.get("APPLY_NO").toString():"";
			String CPNY_ID = paramMap.get("CPNY_ID")!= null ? paramMap.get("CPNY_ID").toString():"";
			String AFFIRM_FLAG =paramMap.get("AFFIRM_FLAG")!= null ? paramMap.get("AFFIRM_FLAG").toString():"";
			String APPLY_LENGTH =paramMap.get("APPLY_LENGTH")!= null ?  paramMap.get("APPLY_LENGTH").toString():"";
			String ITEM_NO =  paramMap.get("ITEM_NO") != null ? paramMap.get("ITEM_NO").toString(): "";
			//原先对SST的验证
			if("HANHUA".equals(CPNY_ID)){
				//加班时间验证
				LinkedHashMap checkMap = new LinkedHashMap();
				checkMap.put("LEAVE_FROM_TIME", paramMap.get("FROM_DATE"));
				checkMap.put("LEAVE_TO_TIME", paramMap.get("TO_DATE"));
				checkMap.put("fromTime", paramMap.get("fromTime"));
				checkMap.put("toTime", paramMap.get("toTime"));
				checkMap.put("adminID", paramMap.get("PERSON_ID"));
				checkMap.put("APPLY_TYPE_CODE", leaveTimeType);
				String result = this.infoApplyLeaveDao.getLeaveCheckSST(checkMap);
				if ( !"OK".equals(result) ) {
					paramMap.put("ERROR_MSG",result);
					return false;
				}
				
			}
//			暂时只检查存在开始结束时间
			if(leaveTimeType!=null && "P".equals(leaveTimeType)){
				// 检查申请开始时间和结束时间,如果格式不对提示用户
				this.formatFromAndToDate(leaveFromDate, leaveToDate);
				// 开始结束时间检查
				if (this.applyStartEndTimeCheck()) {
					paramMap.put("ERROR_MSG", TipMessage.getTipMessage("alert.message.ess.infoApply.finishDateShouldLaterThanStartDate",
							language));
					this.updateEssLeaveApplyTbTemp(paramMap);
					return false;
				}
				
				// 休假时间与之前申请加班时间检查
				if (this.leaveApplyConflictWithExsitOtApply(paramMap)) {
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
					paramMap.put("ERROR_MSG", TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)
							+ personMap.get("LOCAL_NAME")
							+ TipMessage.getTipMessage(
											"alert.message.ess.infoApply.leaveApplyDateConflictWithExsitLeaveApply",
											language));
					this.updateEssLeaveApplyTbTemp(paramMap);
					return false;
				}
				// 如果是年假申请,检查年假数 26 
//				if ("26".equals(paramMap.get("APPLY_TYPE_CODE"))||"123641".equals(paramMap.get("APPLY_TYPE_CODE"))||"123642".equals(paramMap.get("APPLY_TYPE_CODE"))) {
				if("TSTO".equals(CPNY_ID)){
					if ("26".equals(paramMap.get("APPLY_TYPE_CODE"))) {
						if (this.leaveApplyAnnualCheck(paramMap)) {
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
			}
			return true;
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


		public void setStartTime(GregorianCalendar startTime) {
			this.startTime = startTime;
		}


		public GregorianCalendar getStartTime() {
			return startTime;
		}


		public void setEndTime(GregorianCalendar endTime) {
			this.endTime = endTime;
		}


		public GregorianCalendar getEndTime() {
			return endTime;
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
		private void updateEssLeaveApplyTbTemp(LinkedHashMap dateMap) {
			
			try {
				this.infoApplyLeaveDao.updateEssLeaveApplyTbTemp(dateMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
//			年假对应考勤项目NO 后期更改
			paramMap.put("AR_ITEM_NO","('16395','124831','124832')");
			restVac = Double.parseDouble(this.infoApplyLeaveDao.restVac(paramMap).toString());
//			ess年假NO
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
		 * 加班信息查询(search ot info list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getLeaveManagentForSearchInfoList(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request); 
			List returnList = new ArrayList();
			LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
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
			}
			returnList = arDetailDao.getLeaveManagentForSearchInfoList(paramMap);
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
		public List viewSearchOtInfo(HttpServletRequest request) throws Exception {

			Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
			
			return arDetailDao.viewSearchOtInfo(paramMap);
		}


		@Override
		public void deleteOtAdjustForOnlyOne(LinkedHashMap deleteMap) {
			arDetailDao.deleteOtAdjustForOnlyOne(deleteMap);
			
		}
		@SuppressWarnings("unchecked")
		public List viewAdjustRecords(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List returnList = new ArrayList();
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
				returnList = arDetailDao.viewAdjustRecords(paramMap);
			return returnList;
		}
		
		/**
		 * 取得人员考勤异常明细列表(get ArDetail List)
		 * 
		 * @param request
		 * @return List
		 * @throws
		 */
		@SuppressWarnings("unchecked")
		public List viewAbnormalDetailInfo(HttpServletRequest request) {
			List retrunList = new ArrayList();
			Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

			retrunList = arDetailDao.viewAbnormalDetailInfo(paramMap);
				
			return retrunList;
			
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked"})
		public String overTimeLimitImportDemo(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList,String flag) throws SQLException {
			String name="";
			aliasNameList.add("Employee_ID");
			aliasNameList.add("Employee_Name");
			aliasNameList.add("Lock or not (25 hours) (1: lock, 0: unlock)");
			aliasNameList.add("Lock or not (40 hours)(1: lock, 0: unlock)");
				LinkedHashMap map = new LinkedHashMap();
				map.put("CELL0", "43170001");
				map.put("CELL1", "Employee 1");
				map.put("CELL2", "0");
				map.put("CELL3", "0");
				
				list.add(map);
				name="Over_Time_Limit";
			return name;
		}
}
