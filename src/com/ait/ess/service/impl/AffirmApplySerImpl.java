package com.ait.ess.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.AffirmApplyDao;
import com.ait.ess.dao.AffirmLeaveApplyDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.dao.ViewApplyDao;
import com.ait.ess.service.AffirmApplySer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class AffirmApplySerImpl implements AffirmApplySer {

	Logger logger = Logger.getLogger(AffirmApplySerImpl.class);

	@Autowired
	private AffirmApplyDao affirmApplyDao;
	@Autowired
	private AffirmLeaveApplyDao affirmLeaveApplyDao;

	@Autowired
	private ViewApplyDao viewApplyDao;
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	
	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmList(HttpServletRequest request)throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		String firstFlag = request.getParameter("firstFlag");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();    
		if (firstFlag ==null || "".equals(firstFlag)) {
			if((request.getParameter("seach_FROM_TIME")==""||request.getParameter("seach_FROM_TIME")==null )&& (request.getParameter("seach_TO_TIME")==""||request.getParameter("seach_TO_TIME")==null)){
				//获取当前月第一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				paramMap.put("FROM_TIME",first);
				//获取当前月最后一天：
				c.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				paramMap.put("TO_TIME",last);
			}
		}
		paramMap.put("KEY", request.getParameter("dwz.person.empName"));// 31为加班申请
		list = affirmApplyDao.getOtAffirmList(paramMap);
		
		return list;
	}
	@SuppressWarnings("unchecked")
	public List getOtAffirmListTwo(HttpServletRequest request)throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		 
			list = affirmApplyDao.getOtAffirmListTwo(paramMap);
		 
		return list;
	}
	@SuppressWarnings("unchecked")
	public List getOtAffirmListFinal(HttpServletRequest request,List list)throws Exception {
	 
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		paramMap.put("CodeList",list);
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getOtAffirmListFinal(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getOtAffirmListFinal(paramMap);
		}
 
		return list;
	}
	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getOtAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		
		return affirmApplyDao.getOtAffirmListCnt(paramMap);
	}
	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getOtAffirmListTwoCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		return affirmApplyDao.getOtAffirmListTwoCnt(paramMap);
	}
	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getOtAffirmListFinalCnt(HttpServletRequest request,List list)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		paramMap.put("CodeList",list);
		return affirmApplyDao.getOtAffirmListFinalCnt(paramMap);
	}
	
	/**
	 * 批量通过/否决加班申请(batch pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveOvertimeApplyInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装加班申请数据并处理
			this.affirmApplyDao.saveOvertimeApplyAffirmInBatch(this.encapsulationApplyAffirmListForBatch(request));
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
	private List encapsulationApplyAffirmListForBatch(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap linkedMap = new LinkedHashMap();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String affirmFlag = "";
			//属于加班、休假、出差、外出，哪种决裁，默认为加班决裁
			//String affirmType = paramMap.get("AFFIRM_TYPE") != null ? paramMap.get("AFFIRM_TYPE").toString() : "";
			String[] paramData = request.getParameterValues("c2");
			for (int i = 0; i < paramData.length; i++) {
				//
				String ADJST_YN =paramMap.get("ADJST_YN"+paramData[i]) != null ? paramMap.get("ADJST_YN"+paramData[i]).toString() : "0";
				String DATE_TYPE =paramMap.get("DATE_TYPE"+paramData[i]) != null ? paramMap.get("DATE_TYPE"+paramData[i]).toString() : "";
				String ITEM_NO =paramMap.get("ITEM_NO"+paramData[i]) != null ? paramMap.get("ITEM_NO"+paramData[i]).toString() : "";
				String APPLY_TYPE_CODE =paramMap.get("APPLY_TYPE_CODE"+paramData[i]) != null ? paramMap.get("APPLY_TYPE_CODE"+paramData[i]).toString() : "";
				affirmFlag = paramMap.get("AFFIRM_FLAG"+paramData[i]) != null ? paramMap.get("AFFIRM_FLAG"+paramData[i]).toString() : "";
				String NO = paramData[i];
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap affrimMap = new LinkedHashMap();
				//开始:14014306 部门申请: 14014307部门长批准 ：14014308部门长返回:14014309上申取消:14014310部门申请(后):14014311部门长批准(后):14014312
				if(!"".equals(affirmFlag)&&"14014307".equals(affirmFlag)){
					map.put("ACTIVITY", 0);
				}else if (!"".equals(affirmFlag)&&"14014308".equals(affirmFlag)) {
					map.put("ACTIVITY", 1);
				}else if (!"".equals(affirmFlag)&&"14014309".equals(affirmFlag)){
					map.put("ACTIVITY", 0);
			    }else if (!"".equals(affirmFlag)&&"14014310".equals(affirmFlag)) {
			    	map.put("ACTIVITY", 0);
				}else if (!"".equals(affirmFlag)&&"14014311".equals(affirmFlag)) {
					map.put("ACTIVITY", 0);
				}else {
					map.put("ACTIVITY", 1);
				}
				
				
				map.put("PK_NO", NO );
				String APPLY_NO = (String) this.affirmApplyDao.getEssAffirmInfoForApplyNo(map);
				//根据PK_NO获取相应的APPLY_NO
				map.put("APPLY_NO", APPLY_NO);
			   
				affrimMap.put("AFFIRMOR_ID",admin.getPersonId());//登陆者的ID
				affrimMap.put("APPLY_NO",APPLY_NO);//登陆者的ID
				String ESS_AFFIRM_NO = (String) this.affirmApplyDao.getEssAffirmInfoForEssAffrimNO(affrimMap);
				
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
				
				map.put("AFFIRM_FLAG", affirmFlag);
				map.put("ADJST_YN", ADJST_YN);
				map.put("ESS_AFFIRM_NO", ESS_AFFIRM_NO);
				map.put("CURRENT_AFFIRM_ID", admin.getPersonId());
				map.put("CPNY_ID", admin.getCpnyId());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("CREATED_IP", admin.getAdminIP());
				map.put("UPDATED_IP",admin.getAdminIP());
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
		
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
	 * 通过/否决加班申请(pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveOvertimeApply(HttpServletRequest request)throws Exception {
		// 页面提交数据
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		 
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			paramMap.put("interLanguage", paramMap.get("LANGUAGE"));
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		 
		//将页面添加的决裁者插入到已有的决裁者列表中，注意顺序
		int result = this.addNewAffirmorList(paramMap, request);
		//最新的决裁者插入之后再进行决裁
		if(result==1){
			// 封装加班申请数据并处理
			this.affirmApplyDao.saveOvertimeApplyAffirm(this.encapsulationApplyAffirmMapOtApply(request, false));
		}
		return 1;
	}
	
	
	/**
	 * 通过/否决年假调整申请(pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int appAUUNApply(HttpServletRequest request)throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		//将页面添加的决裁者插入到已有的决裁者列表中，注意顺序
		int result = this.addNewAffirmorList(paramMap, request);
		//最新的决裁者插入之后再进行决裁
		if(result==1){
			// 封装加班申请数据并处理
			this.affirmApplyDao.saveAnnualadjustmentAffirm(this.encapsulationApplyAffirmMap(request, false));
		}
		return 1;
	}
	
	
	/**
	 * 通过/否决年假调整申请(pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int appAUUNApplyEP(HttpServletRequest request)throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		//将页面添加的决裁者插入到已有的决裁者列表中，注意顺序
		int result = this.addNewAffirmorListOfTwo(paramMap, request);
		//最新的决裁者插入之后再进行决裁
		if(result==1){
			// 封装加班申请数据并处理
			this.affirmApplyDao.saveAnnualadjustmentAffirm(this.encapsulationApplyAffirmMapOfTwo(request, false));
		}
		return 1;
	}
	
	/**
	 * 批量通过/否决年假调整申请(batch pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int appAnnualadjustmentApplyInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装加班申请数据并处理
			this.affirmApplyDao.saveAnnualadjustmentAffirmInBatch(this.encapsulationApplyAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 封装发令信息成map(encapsulation transaction from request to map)
	 * 
	 * @param request
	 * @return
	 */
	private LinkedHashMap encapsulationApplyAffirmMapOfTwo(
			HttpServletRequest request, boolean ifEntryTrans) {
		LinkedHashMap linkedMap = new LinkedHashMap();
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面提交数据
			LinkedHashMap map = ObjectBindUtil.getRequestParamDataNoSession(request);
			String AFFIRMOR_ID = "";
			if(admin == null){
				AFFIRMOR_ID = map.get("AFFIRMOR_ID").toString();
			}else{
				AFFIRMOR_ID = admin.getAdminID();
			}
			map.put("UPDATED_BY", AFFIRMOR_ID);
			map.put("CREATED_BY", AFFIRMOR_ID);
			//map.put("CPNY_ID", admin.getCpnyId());
			// 当前决裁者
			map.put("CURRENT_AFFIRM_ID", AFFIRMOR_ID);
			linkedMap.put("ESS_AFFIRM_NO", map.get("ESS_AFFIRM_NO"));
			linkedMap = (LinkedHashMap) this.affirmApplyDao
					.getEssAffirmInfoByAffirmNo(linkedMap);
			// 决裁级别
			String currentAffirmLevel = linkedMap.get("AFFIRM_LEVEL") != null ? linkedMap
					.get("AFFIRM_LEVEL").toString()
					: "0";
			// 属于加班、休假、出差、外出，哪种决裁，默认为加班决裁
			// String affirmType = map.get("AFFIRM_TYPE") != null ?
			// map.get("AFFIRM_TYPE").toString() : "";
			// 决裁通过/否决标识
			String affirmFlag = map.get("AFFIRM_FLAG") != null ? map.get(
					"AFFIRM_FLAG").toString() : "0";
			map.put("APPLY_NO", linkedMap.get("APPLY_NO"));
			// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
			int maxAffirmLevel = this.affirmApplyDao
					.getMaxAffirmLevelByApplyNo(linkedMap);
			// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
			if (Integer.parseInt(currentAffirmLevel) < maxAffirmLevel) {
				if ("1".equals(affirmFlag)) {
					int nextAffirmLevel = Integer.parseInt(currentAffirmLevel) + 1;
					map.put("NEXT_AFFIRM_LEVEL", nextAffirmLevel);
					LinkedHashMap nextHrAffirmMap = (LinkedHashMap) this.affirmApplyDao
							.getEssAffirmInfoByApplyNoAndLevel(map);
					String nextAffirmerId = nextHrAffirmMap.get("AFFIRMOR_ID") != null ? nextHrAffirmMap
							.get("AFFIRMOR_ID").toString()
							: AFFIRMOR_ID;
					map.put("NEXT_AFFIRM_ID", nextAffirmerId);
					map.put("ACTIVITY", "0");
					// 用来标志申请信息正在：决裁中，为：4
					map.put("AFFIRM_READ_FLAG", "4");
				} else {
					map.put("NEXT_AFFIRM_ID", "");
					map.put("ACTIVITY", "2");
					// 用来标志申请信息正在：已否决，为：2
					map.put("AFFIRM_READ_FLAG", "2");
				}
			}
			// 如果是决裁流程的最后一步且为通过时
			if (Integer.parseInt(currentAffirmLevel) == maxAffirmLevel) {
				if ("1".equals(affirmFlag)) {
					map.put("FLAG", "1");
					map.put("ACTIVITY", "1");
					// 用来标志申请信息正在：已通过，为：1
					map.put("AFFIRM_READ_FLAG", "1");

				} else {
					map.put("ACTIVITY", "2");
					// 用来标志申请信息正在：已否决，为：2
					map.put("AFFIRM_READ_FLAG", "2");
				}
				map.put("NEXT_AFFIRM_ID", "");
			}

			//map.put("CPNY", admin.getCpnyId());
			map.put("CREATED_BY", AFFIRMOR_ID);
			map.put("UPDATED_BY", AFFIRMOR_ID);
			map.put("ADMIN_ID", AFFIRMOR_ID);
			map.put("navTabId", request.getParameter("navTabId"));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 封装要插入的加班申请数据EP
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private int addNewAffirmorListOfTwo(LinkedHashMap paramMap,
			HttpServletRequest request) throws Exception {
		String AFFIRMOR_ID = request.getParameter("AFFIRMOR_ID");
		int result = 1;
		List addAffirmList = new ArrayList();
		List personList = new ArrayList();
		Enumeration e = request.getParameterNames();
		// 获取页面添加的决裁者的决裁等级（页面等级）
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			if (key.indexOf("dwz.person.personId") > -1) {
				String dwzName = "dwz.person.personId";
				if (key.equals("dwz.person.personId")) {
					personList.add(0);
				} else {
					personList.add(Integer.parseInt(key.substring(dwzName
							.length())));
				}
			}
		}
		// 对页面获取的决裁者信息进行排序（按照页面决裁等级排序）
		Collections.sort(personList);
		for (int i = 0; i < personList.size(); i++) {
			LinkedHashMap affirmMap = new LinkedHashMap();
			String keyName = "";
			String affirmLevel = personList.get(i).toString();
			if (affirmLevel.equals(0)) {
				keyName = "dwz.person.personId";
			} else {
				keyName = "dwz.person.personId" + affirmLevel;
			}
			// 获取添加的决裁者的person_id
			String personId = paramMap.get(keyName).toString();
			affirmMap.put("AFFIRMOR_ID", personId);
			affirmMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
			affirmMap.put("APPLY_TYPE", paramMap.get("APPLY_TYPE").toString());
			affirmMap.put("AFFIRM_LEVEL", affirmLevel);
			affirmMap.put("CREATED_BY", AFFIRMOR_ID);
			affirmMap.put("UPDATED_BY", AFFIRMOR_ID);

			addAffirmList.add(affirmMap);
		}
		LinkedHashMap addAffirmorMap = new LinkedHashMap();
		int affirmorCnt = 0;
		int affirmLeave = 0;
		for (int j = 0; j < addAffirmList.size(); j++) {
			addAffirmorMap = (LinkedHashMap) addAffirmList.get(j);
			if (j == 0) {
				affirmLeave = Integer.parseInt(addAffirmorMap.get(
						"AFFIRM_LEVEL").toString());
			}
			affirmorCnt = this.affirmApplyDao
					.getAffirmorCntByApplyNo(addAffirmorMap);
			if (affirmorCnt == 0) {
				addAffirmorMap.put("AFFIRM_LEVEL", affirmLeave);
				this.affirmApplyDao.addNewApplyAffirmor(addAffirmorMap);
				affirmLeave = affirmLeave + 1;
			}
		}

		return result;
	}
	
	/**
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int addNewAffirmorList(LinkedHashMap paramMap,HttpServletRequest request) throws Exception {
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		int result = 1;
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
			affirmMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
			affirmMap.put("APPLY_TYPE", paramMap.get("APPLY_TYPE").toString());
			affirmMap.put("AFFIRM_LEVEL", affirmLevel);
			if(admin == null){
				paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
				affirmMap.put("CREATED_BY", paramMap.get("PERSON_ID").toString());
				affirmMap.put("UPDATED_BY", paramMap.get("PERSON_ID").toString());
			}else {
				affirmMap.put("CREATED_BY", admin.getPersonId());
				affirmMap.put("UPDATED_BY", admin.getPersonId());
			}
			
			addAffirmList.add(affirmMap);
		}
		LinkedHashMap addAffirmorMap = new LinkedHashMap();
		int affirmorCnt = 0;
		for(int j=0;j<addAffirmList.size();j++){
			addAffirmorMap = (LinkedHashMap)addAffirmList.get(j);
			affirmorCnt = this.affirmApplyDao.getAffirmorCntByApplyNo(addAffirmorMap);
			if(affirmorCnt==0){
				this.affirmApplyDao.addNewApplyAffirmor(addAffirmorMap);
			}
		}
		
		return result;
	}
	
	/**
	 * 封装发令信息成map(encapsulation transaction from request to map)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap encapsulationApplyAffirmMap(
			HttpServletRequest request, boolean ifEntryTrans) {
		LinkedHashMap linkedMap = new LinkedHashMap();
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面提交数据
			LinkedHashMap map =null;
			if(admin == null){
				map= ObjectBindUtil.getRequestParamDataNoSession(request);
				map.put("interLanguage", map.get("LANGUAGE"));
				map.put("ADMIN_ID", map.get("PERSON_ID"));
			}else{
				map = ObjectBindUtil.getRequestParamData(request);
			}
			String AFFIRMOR_ID = "";
			if(admin == null){
				if(map.get("d")==null){
					AFFIRMOR_ID = map.get("AFFIRMOR_ID").toString();
				}else{
					AFFIRMOR_ID = map.get("d").toString();
				}
			}else{
				AFFIRMOR_ID = admin.getAdminID();
			}
			
		 
			map.put("UPDATED_BY", AFFIRMOR_ID);
			map.put("CREATED_BY", AFFIRMOR_ID);
			//map.put("CPNY_ID", admin.getCpnyId());
			// 当前决裁者
			map.put("CURRENT_AFFIRM_ID", AFFIRMOR_ID);
			linkedMap.put("ESS_AFFIRM_NO", map.get("ESS_AFFIRM_NO"));
			linkedMap = (LinkedHashMap) this.affirmApplyDao.getEssAffirmInfoByAffirmNo(linkedMap);
			// 决裁级别
			String currentAffirmLevel = linkedMap.get("AFFIRM_LEVEL") != null ? linkedMap.get("AFFIRM_LEVEL").toString(): "0";
			// 决裁通过/否决标识
			String affirmFlag = map.get("AFFIRM_FLAG") != null ? map.get("AFFIRM_FLAG").toString() : "0";
			map.put("APPLY_NO", linkedMap.get("APPLY_NO"));
			map.put("APPLY_TYPE", linkedMap.get("APPLY_TYPE"));
			linkedMap.put("APPLY_TYPE_WQ", map.get("APPLY_TYPE_WQ"));
			// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
			int maxAffirmLevel = this.affirmApplyDao.getMaxAffirmLevelByApplyNo(linkedMap);
			// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
			if (Integer.parseInt(currentAffirmLevel) < maxAffirmLevel) {
				if ("1".equals(affirmFlag)) {
					int nextAffirmLevel = Integer.parseInt(currentAffirmLevel) + 1;
					map.put("NEXT_AFFIRM_LEVEL", nextAffirmLevel);
					LinkedHashMap nextHrAffirmMap = (LinkedHashMap) this.affirmApplyDao.getEssAffirmInfoByApplyNoAndLevel(map);
					String nextAffirmerId ="";
					if(nextHrAffirmMap!=null&&!"".equals(nextHrAffirmMap)){
						 nextAffirmerId = nextHrAffirmMap.get("AFFIRMOR_ID") != null ? nextHrAffirmMap
								.get("AFFIRMOR_ID").toString(): AFFIRMOR_ID;
					}
					//nextAffirmerId = AFFIRMOR_ID;
					map.put("NEXT_AFFIRM_ID", nextAffirmerId);
					map.put("ACTIVITY", "0");
					//用来标志申请信息正在：决裁中，为：4
					map.put("AFFIRM_READ_FLAG", "4");
				} else {
					map.put("NEXT_AFFIRM_ID", "");
					map.put("ACTIVITY", "2");
					//用来标志申请信息正在：已否决，为：2
					map.put("AFFIRM_READ_FLAG", "2");
				}
			}
			// 如果是决裁流程的最后一步且为通过时
			if (Integer.parseInt(currentAffirmLevel) == maxAffirmLevel) {
				if ("1".equals(affirmFlag)) {
					// 如果不需要人事确认,则最后一步决裁通过后直接生效
					map.put("FLAG", "1");
					map.put("ACTIVITY", "1");
					//用来标志申请信息正在：已通过，为：1
					map.put("AFFIRM_READ_FLAG", "1");
					
				} else {
					map.put("ACTIVITY", "2");
					//用来标志申请信息正在：已否决，为：2
					map.put("AFFIRM_READ_FLAG", "2");
				}
				map.put("NEXT_AFFIRM_ID", "");
			}
			map.put("currentAffirmLevel", currentAffirmLevel);
			//map.put("CPNY", admin.getCpnyId());
			map.put("CREATED_BY", AFFIRMOR_ID);
			map.put("UPDATED_BY", AFFIRMOR_ID);
			map.put("ADMIN_ID", AFFIRMOR_ID);
			map.put("AFFIRM_LEVEL", currentAffirmLevel);
			map.put("navTabId", request.getParameter("navTabId"));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 封装发令信息成map(encapsulation transaction from request to map)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap encapsulationApplyAffirmMapOtApply(
			HttpServletRequest request, boolean ifEntryTrans) {
		LinkedHashMap linkedMap = new LinkedHashMap();
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面提交数据
			LinkedHashMap map =null;
			if(admin == null){
				map= ObjectBindUtil.getRequestParamDataNoSession(request);
				map.put("interLanguage", map.get("LANGUAGE"));
				map.put("ADMIN_ID", map.get("PERSON_ID"));
			}else{
				map = ObjectBindUtil.getRequestParamData(request);
			}
			String AFFIRMOR_ID = "";
			if(admin == null){
				if(map.get("d")==null){
					AFFIRMOR_ID = map.get("AFFIRMOR_ID").toString();
				}else{
					AFFIRMOR_ID = map.get("d").toString();
				}
			}else{
				AFFIRMOR_ID = admin.getAdminID();
			}
			
		 
			map.put("UPDATED_BY", AFFIRMOR_ID);
			map.put("CREATED_BY", AFFIRMOR_ID);
			//map.put("CPNY_ID", admin.getCpnyId());
			// 当前决裁者
			map.put("CURRENT_AFFIRM_ID", AFFIRMOR_ID);
			linkedMap.put("ESS_AFFIRM_NO", map.get("ESS_AFFIRM_NO"));
			linkedMap = (LinkedHashMap) this.affirmApplyDao.getEssAffirmInfoByAffirmNo(linkedMap);
			// 决裁级别
			String currentAffirmLevel = linkedMap.get("AFFIRM_LEVEL") != null ? linkedMap.get("AFFIRM_LEVEL").toString(): "0";
			// 决裁通过/否决标识
			String affirmFlag = map.get("AFFIRM_FLAG") != null ? map.get("AFFIRM_FLAG").toString() : "0";
			map.put("APPLY_NO", linkedMap.get("APPLY_NO"));
			linkedMap.put("APPLY_TYPE_WQ", map.get("APPLY_TYPE_WQ"));
			// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
			int maxAffirmLevel = this.affirmApplyDao.getMaxAffirmLevelByApplyNo(linkedMap);
			// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
			if (Integer.parseInt(currentAffirmLevel) < maxAffirmLevel) {
				if ("1".equals(affirmFlag)) {
					int nextAffirmLevel = Integer.parseInt(currentAffirmLevel) + 1;
					map.put("NEXT_AFFIRM_LEVEL", nextAffirmLevel);
					LinkedHashMap nextHrAffirmMap = (LinkedHashMap) this.affirmApplyDao.getEssAffirmInfoByApplyNoAndLevel(map);
					String nextAffirmerId ="";
					if(nextHrAffirmMap!=null&&!"".equals(nextHrAffirmMap)){
						 nextAffirmerId = nextHrAffirmMap.get("AFFIRMOR_ID") != null ? nextHrAffirmMap
								.get("AFFIRMOR_ID").toString(): AFFIRMOR_ID;
					}else{
						nextAffirmerId = AFFIRMOR_ID;
					}
					
					map.put("NEXT_AFFIRM_ID", nextAffirmerId);
					map.put("ACTIVITY", "0");
					//用来标志申请信息正在：决裁中，为：4
					map.put("AFFIRM_READ_FLAG", "4");
				} else {
					map.put("NEXT_AFFIRM_ID", "");
					map.put("ACTIVITY", "2");
					//用来标志申请信息正在：已否决，为：2
					map.put("AFFIRM_READ_FLAG", "2");
				}
			}
			// 如果是决裁流程的最后一步且为通过时
			if (Integer.parseInt(currentAffirmLevel) == maxAffirmLevel) {
				if ("1".equals(affirmFlag)) {
					// 如果不需要人事确认,则最后一步决裁通过后直接生效
					map.put("FLAG", "1");
					map.put("ACTIVITY", "1");
					//用来标志申请信息正在：已通过，为：1
					map.put("AFFIRM_READ_FLAG", "1");
					
				} else {
					map.put("ACTIVITY", "2");
					//用来标志申请信息正在：已否决，为：2
					map.put("AFFIRM_READ_FLAG", "2");
				}
				map.put("NEXT_AFFIRM_ID", "");
			}
			map.put("currentAffirmLevel", currentAffirmLevel);
			//map.put("CPNY", admin.getCpnyId());
			map.put("CREATED_BY", AFFIRMOR_ID);
			map.put("UPDATED_BY", AFFIRMOR_ID);
			map.put("ADMIN_ID", AFFIRMOR_ID);
			map.put("navTabId", request.getParameter("navTabId"));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 加班申请check列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtCheckList(HttpServletRequest request)throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("PERSON_ID", paramMap.get("ADMIN_ID"));
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getOtCheckList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getOtCheckList(paramMap);
		}
		return list;
	}
	
	/**
	 * 加班申请check总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getOtCheckListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("PERSON_ID", paramMap.get("ADMIN_ID"));
		return affirmApplyDao.getOtCheckListCnt(paramMap);
	}
	
	/**
	 * check加班申请(check overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkApplyInfo(HttpServletRequest request)throws Exception {
		int result = 1;
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = new LinkedHashMap();
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("UPDATED_BY", paramMap.get("PERSON_ID"));
		}else{
			paramMap = this.encapsulationApplyCheckMap(request);
		}
		this.affirmApplyDao.checkApplyInfo(paramMap);
		
		return result;
	}
	
	/**
	 * check加班申请，最后一步check之后将ess_affirm中的current_check_id置空(check pa for left apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkPaForLeftApplyInfo(HttpServletRequest request)throws Exception {
		int result = 1;
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = new LinkedHashMap();
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("UPDATED_BY", paramMap.get("ADMIN_ID"));
		}else{
			paramMap = this.encapsulationApplyCheckMap(request);
		}
		this.affirmApplyDao.checkPaForLeftApplyInfo(paramMap);
		
		return result;
	}
	
	/**
	 * 封装check信息成map(encapsulation transaction from request to map)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap encapsulationApplyCheckMap(HttpServletRequest request) {
		LinkedHashMap linkedMap = new LinkedHashMap();
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面提交数据
			LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
			map.put("UPDATED_BY", admin.getPersonId());
			map.put("CREATED_BY", admin.getPersonId());
			map.put("CPNY_ID", admin.getCpnyId());
			// 当前check人
			map.put("CURRENT_CHECKOR_ID", admin.getPersonId());
			linkedMap.put("ESS_AFFIRM_NO", map.get("ESS_AFFIRM_NO"));
			linkedMap.put("ESS_CHECK_NO", map.get("ESS_CHECK_NO"));
			linkedMap.put("CHECK_CONTENT", map.get("CHECK_CONTENT"));
			linkedMap = (LinkedHashMap) this.affirmApplyDao.getEssCheckInfoByCheckNo(linkedMap);
			// 决裁级别
			String currentCheckLevel = linkedMap.get("CHECK_LEVEL") != null ? linkedMap.get("CHECK_LEVEL").toString(): "1";
			// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
			int maxCheckLevel = this.affirmApplyDao.getMaxCheckLevelByApplyNo(linkedMap);
			// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
			if (Integer.parseInt(currentCheckLevel) < maxCheckLevel) {
				int nextCheckLevel = Integer.parseInt(currentCheckLevel) + 1;
				map.put("NEXT_CHECK_LEVEL", nextCheckLevel);
				LinkedHashMap nextCheckMap = (LinkedHashMap) this.affirmApplyDao.getEssCheckInfoByApplyNoAndLevel(map);
				String nextCheckorId="";
				if(nextCheckMap!=null){
					  nextCheckorId = nextCheckMap.get("CHECKOR_ID") != null ? (String)nextCheckMap.get("CHECKOR_ID"): admin.getPersonId();
				}else{
					  nextCheckorId =admin.getPersonId();
				}
				map.put("NEXT_CHECKOR_ID", nextCheckorId);
				map.put("NEXT_CHECK_FLAG", 1);
			} else {
				map.put("NEXT_CHECK_FLAG", "2");
			}
			map.put("CPNY", admin.getCpnyId());
			map.put("CREATED_BY", admin.getAdminID());
			map.put("UPDATED_BY", admin.getAdminID());
			map.put("ADMIN_ID", admin.getAdminID());
			map.put("navTabId", request.getParameter("navTabId"));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加checkor(add checkor)
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addApplyCheckList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("ESS_AFFIRM_NO", request.getParameter("ESS_AFFIRM_NO"));
		paramMap.put("PERSON_ID", request.getParameter("dwz.person.personId"));
		//查看此checkor者是否已经存在
		LinkedHashMap linkedMap = new LinkedHashMap();
		linkedMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO"));
		linkedMap.put("CHECKOR_ID", paramMap.get("PERSON_ID"));
		
		//查看此决裁人是否还有当前checkor人了
		//linkedMap = (LinkedHashMap) this.affirmApplyDao.getEssAffirmInfoByAffirmNo(linkedMap);
		//String currentCheckFlag = linkedMap.get("CURRENT_CHECKOR_ID")!=null?linkedMap.get("CURRENT_CHECKOR_ID").toString():"";
		//int cnt = 0;
		//cnt = this.affirmApplyDao.getEssCheckCntByCheckorId(linkedMap);
		//如果此人之前没有加入为此条加班申请的checkor人，插入
		//在这个map里面添加申请类型和URL对应值
		Map<String, String> ipMap = new HashMap<String, String>();
	 
		ipMap.put("PROVEAPP", "proveApplicationList"); //费用申请
		ipMap.put("ARMAC", "RecordCheck");//漏刷卡
		ipMap.put("218197", "CwaCheck");//漏刷卡
		ipMap.put("216691", "AnnuCheck");//年假调整
		ipMap.put("L_OTAPPLY", "startApplyCheckInfo"); //加班CH
		ipMap.put("P_OTAPPLY", "startApplyCheckInfo"); //加班生产法人
		ipMap.put("LEAVE_APPLY", "viewLeaveCheck"); //休假
		ipMap.put("21", "viewLeaveCheck"); 
		ipMap.put("218296", "checkDimissionApplyInfo"); //离职申请
		ipMap.put("224", "checkLeftMenApplyInfo"); //离职人员工资补发
		int  checkno = this.affirmApplyDao.selectCheckNo();
		paramMap.put("smallpage", "smallpage");
		paramMap.put("ESS_CHECK_NO", checkno);
		//判断申请类型
		String applyType = "";
		String page_flag = paramMap.get("PAGE_FLAG") == null ? "" : paramMap.get("PAGE_FLAG").toString();
		if("LEAVE_APPLY".equals(page_flag)){
			applyType = "21";
		}
		if("L_OTAPPLY".equals(page_flag)){
		}
		if("P_OTAPPLY".equals(page_flag)){
		}
		if("PROVEAPP".equals(page_flag)){
		}
		if("ARMAC".equals(page_flag)){
			applyType = "218294";
		}
		//if(cnt==0){
			//插入新的checkor
			paramMap.put("CHECKOR_ID", paramMap.get("PERSON_ID")!=null?paramMap.get("PERSON_ID").toString():admin.getPersonId());
			int flag = this.affirmApplyDao.addApplyCheckList(paramMap);
			//其他人要用了这个方法下面有个参数PAGE_FLAG这个需从你的审批页面传过来，其他参数原来应该都是已经有的,原来是Apply_type下面已经改成了PAGE_FLAG
			if(flag == 1){//Check添加成功后，发送小页面
				LinkedHashMap lgepMap = new LinkedHashMap();
					lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
					lgepMap.put("APPLY_TYPE", paramMap.get("APPLY_TYPE") == null ? applyType : paramMap.get("APPLY_TYPE"));
					lgepMap.put("PRE_AFFIRM_EMPID", linkedMap.get("CHECKOR_ID"));
					lgepMap.put("AFFIRM_LEVEL", checkno);
					lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/"+ipMap.get(paramMap.get("PAGE_FLAG"))+"?APPLY_TYPE_NO="+paramMap.get("APPLY_TYPE")+"&LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("PERSON_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
				this.affirmInfoToLGEPSer.crateCheck(lgepMap);
			}
			//如果当前没有checkor，还需要更新ess_affirm中的current_checkor_id，如果已有current_checkor_id，则只插入新的checkor即可
			/*if(currentCheckFlag==null || "".equals(currentCheckFlag)){
				paramMap.put("NEXT_CHECKOR_ID", paramMap.get("PERSON_ID")!=null?paramMap.get("PERSON_ID").toString():admin.getPersonId());
				this.affirmApplyDao.updateCurrentCheckor(paramMap);
			}*/
		//如果此人之前已经加入为此条加班申请的checkor人，不需要插入
		//}
		return 1;
	}
	/**
	 * 添加checkor(add checkor)小页面用
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addApplyCheckListXiao(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
	 
		paramMap.put("CREATED_BY", paramMap.get("ADMIN_ID"));//ADMIN_ID从前台页面传过来的当前决裁者
		paramMap.put("UPDATED_BY", paramMap.get("ADMIN_ID"));
		paramMap.put("ADMIN_ID", paramMap.get("ADMIN_ID"));
		paramMap.put("ESS_AFFIRM_NO", request.getParameter("ESS_AFFIRM_NO"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		//查看此checkor者是否已经存在
		LinkedHashMap linkedMap = new LinkedHashMap();
		linkedMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO"));
		linkedMap.put("CHECKOR_ID", paramMap.get("PERSON_ID"));
		
		//查看此决裁人是否还有当前checkor人了
		linkedMap = (LinkedHashMap) this.affirmApplyDao.getEssAffirmInfoByAffirmNo(linkedMap);
		String currentCheckFlag = linkedMap.get("CURRENT_CHECKOR_ID")!=null?linkedMap.get("CURRENT_CHECKOR_ID").toString():"";
		int cnt = 0;
		linkedMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO"));
		linkedMap.put("CHECKOR_ID", paramMap.get("PERSON_ID"));
		cnt = 0;//this.affirmApplyDao.getEssCheckCntByCheckorId(linkedMap);
		//如果此人之前没有加入为此条加班申请的checkor人，插入
		if(cnt==0){
			//在这个map里面添加申请类型和URL对应值
			Map<String, String> ipMap = new HashMap<String, String>();
			ipMap.put("217886", "proveApplicationList"); //费用申请
			ipMap.put("218294", "RecordCheck");//漏刷卡
			ipMap.put("218197", "CwaCheck");//漏刷卡
			ipMap.put("216691", "AnnuCheck");//年假调整
			ipMap.put("31", "startApplyCheckInfo"); 
			ipMap.put("21", "viewLeaveCheck"); 
			ipMap.put("218064", "viewTempSaleAffirm"); 
			ipMap.put("218296", "checkDimissionApplyInfo"); //离职
			ipMap.put("224", "checkLeftMenApplyInfo"); //离职人员工资补发申请
			//插入新的checkor
			paramMap.put("CHECKOR_ID", paramMap.get("PERSON_ID")!=null?paramMap.get("PERSON_ID").toString():paramMap.get("ADMIN_ID"));
			int  checkno = this.affirmApplyDao.selectCheckNo();
			paramMap.put("smallpage", "smallpage");
			paramMap.put("ESS_CHECK_NO", checkno);
			int flag = this.affirmApplyDao.addApplyCheckList(paramMap);
			if(flag == 1){//Check添加成功后，发送小页面
				LinkedHashMap lgepMap = new LinkedHashMap();
					lgepMap.put("APPLY_NO", linkedMap.get("APPLY_NO"));
					lgepMap.put("APPLY_TYPE", paramMap.get("APPLY_TYPE"));
					lgepMap.put("CHECK_ID", linkedMap.get("CHECKOR_ID"));
					lgepMap.put("AFFIRM_LEVEL", checkno);
					lgepMap.put("PRE_AFFIRM_EMPID",paramMap.get("PERSON_ID"));
					lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/"+ipMap.get(paramMap.get("APPLY_TYPE"))+"?APPLY_TYPE_NO="+paramMap.get("APPLY_TYPE")+"&LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("PERSON_ID") + "&APPLY_NO=" + linkedMap.get("APPLY_NO").toString() + "&affirmOrCheck=2" + "&EVENT_ID=" + linkedMap.get("APPLY_NO").toString());

				this.affirmInfoToLGEPSer.crateCheck(lgepMap);
			}
			//如果当前没有checkor，还需要更新ess_affirm中的current_checkor_id，如果已有current_checkor_id，则只插入新的checkor即可
			/*if(currentCheckFlag==null || "".equals(currentCheckFlag)){
				paramMap.put("NEXT_CHECKOR_ID", paramMap.get("PERSON_ID")!=null?paramMap.get("PERSON_ID").toString():paramMap.get("ADMIN_ID"));
				this.affirmApplyDao.updateCurrentCheckor(paramMap);
			}*/
		//如果此人之前已经加入为此条加班申请的checkor人，不需要插入
		}
		return 1;
	}
	
	
	 
	
	/**
	 * 加班申请--编辑列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditOtApplyList(HttpServletRequest request)throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getEditOtApplyList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getEditOtApplyList(paramMap);
		}

		LinkedHashMap otApplyInfo = (LinkedHashMap)list.get(0);

		otApplyInfo.put("APPLY_TYPE", "31");
		List fileList = infoApplyLeaveDao.getEssFileList(otApplyInfo);
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
		otApplyInfo.put("FILE_NAME", fileName);
		otApplyInfo.put("FILE_URL", fileUrl);
		return list;
	}
	
	
	/**
	 * 加班申请--编辑列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditOtApplyBatchList(HttpServletRequest request)throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getEditOtApplyBatchList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getEditOtApplyBatchList(paramMap);
		}
		return list;
	}
	/**
	 * 加班申请--编辑列表总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEditOtApplyListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		return affirmApplyDao.getEditOtApplyListCnt(paramMap);
	}
	
	/**
	 * 加班申请--批量加班修改编辑列表总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEditOtApplyListBatchCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		return affirmApplyDao.getEditOtApplyListBatchCnt(paramMap);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 通过request请求封装查询条件(get search conditions for request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getMapByRequestForSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		//为了查出只有本人审批的申请，和不是本人审批的申请做的处理
		/*		if(paramMap.get("AFFIRM_FLAG")!=null&&paramMap.get("AFFIRM_FLAG").equals("onlypersonid")){
					paramMap.put("AFFIRM_FLAG", "4");
					paramMap.put("ONLYPERSONID", paramMap.get("supervisorPersonId"));
				}else if(paramMap.get("AFFIRM_FLAG")!=null&&paramMap.get("AFFIRM_FLAG").equals("notonlypersonid")){
					paramMap.put("AFFIRM_FLAG", "4");
					paramMap.put("NOTONLYPERSONID", paramMap.get("supervisorPersonId"));
				}*/
		if(request.getParameter("parentParam") != null){
			String tempStr = StringUtil.checkNull(request.getParameter("parentParam")) ;
			String[] tempStrs =  tempStr.split("@");
			String[] tempStrsForMap = null ;
			for (int i = 0; i < tempStrs.length; i++) {
				if(tempStrs[i].indexOf("=") != -1){
					tempStrsForMap = tempStrs[i].split("=");
					if(tempStrsForMap.length == 2)
						paramMap.put(""+tempStrsForMap[0],tempStrsForMap[1] );
				}
			}
		}
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}

	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOvertimeAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getOvertimeAffirmList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getOvertimeAffirmList(paramMap);
		}
		return this.getAffirmerListForInfoApply(list, request);
	}

	/**
	 * 加班申请决裁总数(overtime apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getOvertimeAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		return affirmApplyDao.getOvertimeAffirmListCnt(paramMap);
	}

	/**
	 * 休假申请决裁列表(leave apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getLeaveApplyAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		
		String menuNo=request.getParameter("menuNo");
		// 123634为喜丧假申请   123646 调休
		if(menuNo.equals("123649")||menuNo.equals("124822")||menuNo.equals("124824")||menuNo.equals("124826")){
			paramMap.put("TYPE", "123634");
			paramMap.put("APPLY_TYPE_NO", "123634");
		}else if(menuNo.equals("123650")||menuNo.equals("124823")||menuNo.equals("124825")||menuNo.equals("124827")){
			paramMap.put("TYPE", "123646");
			paramMap.put("APPLY_TYPE_NO", "123645");
		}else if(menuNo.equals("2441")||menuNo.equals("2449")||menuNo.equals("2454")||menuNo.equals("2479")){
			paramMap.put("TYPE", "21");
			paramMap.put("APPLY_TYPE_NO", "21");// 21为休假申请
		}else{
			paramMap.put("TYPE", "0");
		}
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getLeaveApplyAffirmList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getLeaveApplyAffirmList(paramMap);
		}
		return this.getAffirmerListForInfoApply(list, request);
	}

	/**
	 * 休假申请决裁总数(leave apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getLeaveApplyAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		
		String menuNo=request.getParameter("menuNo");
		paramMap.put("APPLY_TYPE_NO", "21");// 21为休假申请
		// 123634为喜丧假申请   123646 调休
		if(menuNo.equals("123649")||menuNo.equals("124822")||menuNo.equals("124824")||menuNo.equals("124826")){
			paramMap.put("TYPE", "123634");
		}else if(menuNo.equals("123650")||menuNo.equals("124823")||menuNo.equals("124825")||menuNo.equals("124827")){
			paramMap.put("TYPE", "123646");
		}else if(menuNo.equals("2441")||menuNo.equals("2449")||menuNo.equals("2454")||menuNo.equals("2479")){
			paramMap.put("TYPE", "21");
		}else{
			paramMap.put("TYPE", "0");
		}
		return affirmApplyDao.getLeaveApplyAffirmListCnt(paramMap);
	}

	/**
	 * 出差申请决裁列表(evection apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEvectionApplyAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "18");// 18为出差申请
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getLeaveApplyAffirmList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getLeaveApplyAffirmList(paramMap);
		}
		return this.getAffirmerListForInfoApply(list, request);
	}

	/**
	 * 出差申请决裁总数(evection apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEvectionApplyAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "18");// 18为出差申请
		return affirmApplyDao.getLeaveApplyAffirmListCnt(paramMap);
	}

	/**
	 * 外出申请决裁列表(egression apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEgeressionApplyAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "16201");// 16201为外出申请
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getLeaveApplyAffirmList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getLeaveApplyAffirmList(paramMap);
		}
		return this.getAffirmerListForInfoApply(list, request);
	}

	/**
	 * 外出申请决裁总数(egression apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEgressionApplyAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "16201");// 16201为外出申请
		return affirmApplyDao.getLeaveApplyAffirmListCnt(paramMap);
	}

	/**
	 * 遍历LIST取出每个信息申请的所有决裁者(traverse list for get every info-apply's affimer
	 * list)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List getAffirmerListForInfoApply(List list,
			HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		LinkedHashMap paramMap = null;
		LinkedHashMap childMap = null;
		@SuppressWarnings("unused")
		LinkedHashMap childrenMap = new LinkedHashMap();

		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			String hrefFlag = "1";
			List aList = this.affirmApplyDao
					.getEssAffirmInfoByApplyNo(paramMap);
			List childList = new ArrayList();
			for (int j = 0; j < aList.size(); j++) {
				childMap = (LinkedHashMap) aList.get(j);
				childMap.put("HREF_FLAG", hrefFlag);
				String affirmFlag = childMap.get("AFFIRM_FLAG") != null ? childMap
						.get("AFFIRM_FLAG").toString()
						: "";
				// 如果这一步未决裁或者决裁未通过,则下一步通过和否决的链接屏蔽
				if ("0".equals(affirmFlag) || "2".equals(affirmFlag)) {
					hrefFlag = "0";
				} else {
					hrefFlag = "1";
				}
				childList.add(childMap);
			}
			hrefFlag = "0";
			paramMap.put("affirmerList", childList);
			paramMap.put("ADMIN_ID", admin.getPersonId());

			String applyDateStr = "";
			String fromTIme = "";
			String toTime = "";
			String applyTypeCode = paramMap.get("APPLY_TYPE_CODE") != null ? paramMap
					.get("APPLY_TYPE_CODE").toString()
					: "";

			if (!"32".equals(applyTypeCode)) {
				if (paramMap.get("OT_FROM_TIME") != null
						&& !"".equals(paramMap.get("OT_FROM_TIME").toString())) {
					applyDateStr = paramMap.get("OT_FROM_TIME") != null ? paramMap
							.get("OT_FROM_TIME").toString()
							: "";
					fromTIme = paramMap.get("OT_FROM_TIME") != null ? paramMap
							.get("OT_FROM_TIME").toString() : "";
					toTime = paramMap.get("OT_FROM_TIME") != null ? paramMap
							.get("OT_FROM_TIME").toString() : "";

					applyDateStr = applyDateStr.substring(0, 10);
					childMap.put("CPNY_ID", admin.getCpnyId());
					childMap.put("APPLY_DATE_STR", applyDateStr);
					childMap.put("FROM_DATE", fromTIme);
					childMap.put("TO_DATE", toTime);

					String otLength = paramMap.get("OT_LENGTH") != null ? paramMap
							.get("OT_LENGTH").toString()
							: "0";

					String deductLength = "0";

					childMap.put("OT_APPLY_TYPE_CODE", applyTypeCode);
					if (this.viewApplyDao.getDeductFromTimeByCpnyId(childMap) > 0) {
						deductLength = this.viewApplyDao
								.getDeductTimeCountInOtTimeByCpnyId(childMap);
					}

					paramMap.put("OT_LENGTH", Double.parseDouble(otLength)
							- Double.parseDouble(deductLength));
				}
			}
			returnList.add(paramMap);
		}
		return returnList;
	}

	

	

	/**
	 * 批量通过/否决休假/出差/外出申请(batch pass and reject leave/evection/egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveLeaveApplyInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装加班申请数据并处理
			this.affirmApplyDao.saveLeaveApplyAffirmInBatch(this
					.encapsulationApplyAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 通过/否决休假/出差/外出申请(pass and reject leave/evection/egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveLeaveApply(HttpServletRequest request) throws Exception {
		try {
			// 封装加班申请数据并处理
			this.affirmApplyDao.saveLeaveApplyAffirm(this
					.encapsulationApplyAffirmMap(request, false));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 临时职入职审批
	 */
	@SuppressWarnings("unchecked")
	public List getHireAffirmList(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("USER_NO", admin.getUserNo());
		if(!paramMap.containsKey("FROM_TIME")){
			paramMap.put("FROM_TIME", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("TO_TIME", DateUtil.getSysdateStr());			
		}
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getHireAffirmList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getHireAffirmList(paramMap);
		}
		return list;
	}
	/**
	 * 临时职入职审批
	 */
	@SuppressWarnings("unchecked")
	public int getHireAffirmListCnt(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("USER_NO", admin.getUserNo());
		if(!paramMap.containsKey("FROM_TIME")){
			paramMap.put("FROM_TIME", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("TO_TIME", DateUtil.getSysdateStr());			
		}
		return affirmApplyDao.getHireAffirmListCnt(paramMap);
	}

	/**
	 * 临时职入职审批
	 */
	@SuppressWarnings("unchecked")
	public List getHireAffirmByReqID(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			if(request.getParameter("REQ_ID")!=null && !request.getParameter("REQ_ID").equals("")){
				paramMap.put("REQ_ID",  request.getParameter("REQ_ID"));
			}else{
				paramMap.put("REQ_ID",  "-1");
			}
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		return affirmApplyDao.getHireAffirmByReqID(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCheckorByByReqID(HttpServletRequest request) throws Exception {
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
		returnList = affirmApplyDao.getCheckorByByReqID(paramMap);
		
		return returnList;
	}
	@SuppressWarnings("unchecked")
	public List getHireAffirmListByReqID(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = new ArrayList();
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getHireAffirmListByReqID(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getHireAffirmListByReqID(paramMap);
		}
		return list;
	}
	public int getHireAffirmListByReqIDCnt(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = new ArrayList();
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		
		return affirmApplyDao.getHireAffirmListByReqIDCnt(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getHireAffirm(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			paramMap.put("PERSON_ID", paramMap.get("personId").toString());
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		return affirmApplyDao.getHireAffirmList(paramMap);
	}

	@Override
	public int approveApplyHire(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		paramMap.put("APPLY_TYPE", 23292329);
		int result = this.addNewAffirmorList(paramMap, request);
		if (result == 1) {
			this.affirmApplyDao.saveApplyHireAffirm(this.encapsulationApplyAffirmMap(request, false));
		}
		return 1;
	}
	
	/**
	 * 临时职离职发令审批
	 */
	@SuppressWarnings("unchecked")
	public List getResignAffirmList(LinkedHashMap paramMap, HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getResignAffirmList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getResignAffirmList(paramMap);
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public int getResignAffirmListCnt(LinkedHashMap paramMap, HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		//paramMap.put("USER_NO", admin.getUserNo());
		return affirmApplyDao.getResignAffirmListCnt(paramMap);
	}
	@SuppressWarnings("unchecked")
	public List getResignAffirmByReqID(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		return affirmApplyDao.getResignAffirmByReqID(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getResignCheckorByByReqID(HttpServletRequest request) throws Exception {
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
		returnList = affirmApplyDao.getResignCheckorByByReqID(paramMap);
		
		return returnList;
	}
	@SuppressWarnings("unchecked")
	public List getResignAffirmListByReqID(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		return affirmApplyDao.getResignAffirmListByReqID(paramMap);
	}
	@Override
	@SuppressWarnings("unchecked")
	public int getResignAffirmListByReqIDCnt(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		return affirmApplyDao.getResignAffirmListByReqIDCnt(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getResignAffirm(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			paramMap.put("PERSON_ID", paramMap.get("personId").toString());
			paramMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		return affirmApplyDao.getResignAffirmList(paramMap);
	}

	@Override
	public int approveApplyResign(HttpServletRequest request) throws Exception {
		int rtn = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		paramMap.put("APPLY_TYPE", 15823);
		int result = this.addNewAffirmorList(paramMap, request);
		LinkedHashMap affirmMap = this.encapsulationApplyAffirmMap(request, false);
		String cpnyId = "";
		String personId="";
		if(admin==null){
			cpnyId = paramMap.get("CPNY_ID").toString();
			personId=paramMap.get("PERSON_ID").toString();
		}else{
			cpnyId=admin.getCpnyId();
			personId=admin.getPersonId();
		}
		if (result == 1) {
			int finalState = this.affirmApplyDao.saveApplyResignAffirm(affirmMap);
			if(paramMap.get("TRANS_CODE").equals("RESIGNREVOKE")){
				paramMap.put("UPDATED_BY",personId);
				if(finalState==50){//通过				
					paramMap.put("REVOKE_STATE",paramMap.get("REF_STATE"));
					paramMap.put("REVOKE_TYPE","REQ");
					paramMap.put("RESIGN_NOS",paramMap.get("REQ_ID"));
					paramMap.put("CPNY_ID",cpnyId);
					Map<String, Object> map = new HashMap<String, Object>();
					map = affirmApplyDao.callRevokeResignation(paramMap);				
					String msg =  ObjectUtils.toString(map.get("MESSAGE")) ;
					if(msg.equals("OK")) rtn = 1; else rtn = 0;
				}else if(finalState==60){//拒绝				
					affirmApplyDao.rollbackApplyResignAffirm(paramMap);
				}
			}
			//审批发送LGEP
			affirmMap.put("CPNY_ID",cpnyId);
			if(!affirmMap.containsKey("REQ_ID")){
				affirmMap.put("REQ_ID", paramMap.get("REQ_ID"));
			}else if(affirmMap.get("REQ_ID")==null || affirmMap.get("REQ_ID")==""){
				affirmMap.put("REQ_ID", paramMap.get("REQ_ID"));
			}
			sendResignToLGEP(affirmMap);
			rtn=1;
		}
		return rtn;
	}
	@SuppressWarnings("unchecked")
	private void sendResignToLGEP(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", "15823");
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			if(!paramMap.containsKey("REQ_ID")){
				paramMap.put("REQ_ID", paramMap.get("APPLY_NO"));
			}else if(paramMap.get("REQ_ID")==null || paramMap.get("REQ_ID")==""){
				paramMap.put("REQ_ID", paramMap.get("APPLY_NO"));
			}
			
			if("4".equals(paramMap.get("AFFIRM_READ_FLAG").toString())){//决裁中是4
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("NEXT_AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{//决裁完成
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_READ_FLAG"));
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("currentAffirmLevel"));
			}
			lgepMap.put("CREATED_BY", paramMap.get("APPLY_PERSON_ID")==null? (paramMap.get("PERSON_ID")==null?paramMap.get("CREATED_BY"):paramMap.get("PERSON_ID").toString()):paramMap.get("APPLY_PERSON_ID").toString());
			lgepMap.put("AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewReqResignAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID="+paramMap.get("CPNY_ID")+"&personId="+paramMap.get("CURRENT_AFFIRM_ID")+"&REQ_ID=" + paramMap.get("REQ_ID").toString());
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewReqResignAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID="+paramMap.get("CPNY_ID")+"&personId="+paramMap.get("CURRENT_AFFIRM_ID")+"&REQ_ID=" + paramMap.get("REQ_ID").toString());
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewReqResignAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID="+paramMap.get("CPNY_ID")+"&personId=" + paramMap.get("NEXT_AFFIRM_ID") + "&REQ_ID=" + paramMap.get("REQ_ID").toString());
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("NEXT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("NEXT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}
	

	/**
	 * 考勤异常批量审批
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveCwaApplyInBatch(HttpServletRequest request)
			throws Exception {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			// 批量封装加班申请数据并处理
			this.affirmLeaveApplyDao.saveApplyCwaAffirmBatch(this
					.encapsulationCwaApplyAffirmListForBatch(request));
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
	private List encapsulationCwaApplyAffirmListForBatch(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap linkedMap = new LinkedHashMap();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String affirmFlag = paramMap.get("AFFIRM_FLAG") != null ? paramMap.get("AFFIRM_FLAG").toString() : "";
			//属于加班、休假、出差、外出，哪种决裁，默认为加班决裁
			//String affirmType = paramMap.get("AFFIRM_TYPE") != null ? paramMap.get("AFFIRM_TYPE").toString() : "";
			String[] paramData = request.getParameterValues("cwa_c1");
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("CREATED_BY", admin.getPersonId());
				map.put("ADMIN_ID", admin.getPersonId());
				map.put("CPNY_ID", admin.getCpnyId());
				map.put("CURRENT_AFFIRM_ID", admin.getPersonId());

				map.put("ESS_AFFIRM_NO", paramData[i]);
				linkedMap.put("ESS_AFFIRM_NO", map.get("ESS_AFFIRM_NO"));
				linkedMap = (LinkedHashMap) this.affirmApplyDao.getEssAffirmInfoByAffirmNo(linkedMap);
				map.put("AFFIRM_FLAG", affirmFlag);
				// 决裁级别
				String currentAffirmLevel = linkedMap.get("AFFIRM_LEVEL") != null ? linkedMap.get("AFFIRM_LEVEL").toString(): "0";
				map.put("AFFIRM_LEVEL", currentAffirmLevel);
				map.put("APPLY_NO", linkedMap.get("APPLY_NO"));
				// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
				int maxAffirmLevel = this.affirmApplyDao.getMaxAffirmLevelByApplyNo(linkedMap);
				// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
				if (Integer.parseInt(currentAffirmLevel) < maxAffirmLevel) {
					if ("1".equals(affirmFlag)) {
						int nextAffirmLevel = Integer.parseInt(currentAffirmLevel) + 1;
						map.put("NEXT_AFFIRM_LEVEL", nextAffirmLevel);
						LinkedHashMap nextHrAffirmMap = (LinkedHashMap) this.affirmApplyDao.getEssAffirmInfoByApplyNoAndLevel(map);
						String nextAffirmerId = nextHrAffirmMap
								.get("AFFIRMOR_ID") != null ? nextHrAffirmMap.get("AFFIRMOR_ID").toString() : admin.getPersonId();
						map.put("NEXT_AFFIRM_ID", nextAffirmerId);
						map.put("ACTIVITY", "0");
						//用来标志申请信息正在：决裁中，为：4
						map.put("AFFIRM_READ_FLAG", "4");
					} else {
						map.put("NEXT_AFFIRM_ID", "");
						map.put("ACTIVITY", "2");
						//用来标志申请信息正在：已否决，为：2
						map.put("AFFIRM_READ_FLAG", "2");
					}
				}
				// 查看是否需要人事确认.(4160为对应的CODE)
				//map.put("ESS_PARAM_NO", "4160");
				//Object obj = this.affirmApplyDao.getParamValueByCpnyIdAndParamNo(map);
				//String confirmFlag = obj != null ? obj.toString() : "0";

				// 如果是决裁流程的最后一步且为通过时
				if (currentAffirmLevel.equals(String.valueOf(maxAffirmLevel))) {
					if ("1".equals(affirmFlag)) {
						
						map.put("FLAG", "1");
						map.put("ACTIVITY", "1");
						//用来标志申请信息正在：已通过，为：1
						map.put("AFFIRM_READ_FLAG", "1");
					} else {
						map.put("ACTIVITY", "2");
						//用来标志申请信息正在：已否决，为：2
						map.put("AFFIRM_READ_FLAG", "2");
					}
					map.put("NEXT_AFFIRM_ID", "");
				}
				map.put("currentAffirmLevel", currentAffirmLevel);
				map.put("CPNY", admin.getCpnyId());
				map.put("CPNY_ID", admin.getCpnyId());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
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
	 * 实贩卖实绩审批查询列表
	 */
	@SuppressWarnings("unchecked")
	public List getSellOutAffirmList(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		if(!paramMap.containsKey("START_DATE")){
			paramMap.put("START_DATE", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("END_DATE", DateUtil.getSysdateStr());			
		}
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getSellOutAffirmList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getSellOutAffirmList(paramMap);
		}
		return list;
	}
	/**
	 * 实贩卖实绩审批查询列表
	 */
	@SuppressWarnings("unchecked")
	public int getSellOutAffirmListCnt(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		if(!paramMap.containsKey("START_DATE")){
			paramMap.put("START_DATE", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("END_DATE", DateUtil.getSysdateStr());			
		}
		return affirmApplyDao.getSellOutAffirmListCnt(paramMap);
	}
	
}