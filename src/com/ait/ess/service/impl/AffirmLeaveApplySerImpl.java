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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.AffirmApplyDao;
import com.ait.ess.dao.AffirmLeaveApplyDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.dao.ViewApplyDao;
import com.ait.ess.service.AffirmLeaveApplySer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
@SuppressWarnings("unchecked")
public class AffirmLeaveApplySerImpl implements AffirmLeaveApplySer {

	Logger logger = Logger.getLogger(AffirmLeaveApplySerImpl.class);
	@Autowired
	private AffirmLeaveApplyDao affirmApplyDao;
	@Autowired
	private ViewApplyDao viewApplyDao;
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	
	@Autowired
	private AffirmApplyDao affirmOtApplyDao;
	/**
	 * 加班申请决裁列表(overtime apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getOtAffirmList(HttpServletRequest request) throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getOtAffirmList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getOtAffirmList(paramMap);
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
	public int getOtAffirmListCnt(HttpServletRequest request) throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		return affirmApplyDao.getOtAffirmListCnt(paramMap);
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
			this.affirmApplyDao.saveOvertimeApplyAffirmInBatch(this
					.encapsulationApplyAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
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
			this.affirmApplyDao.saveOvertimeApplyAffirmInBatch(this
					.encapsulationApplyAffirmListForBatch(request));
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
	private List encapsulationApplyAffirmListForBatch(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap linkedMap = new LinkedHashMap();
		// 页面提交数据linkedMap
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String affirmFlag = paramMap.get("AFFIRM_FLAG") != null ? paramMap
					.get("AFFIRM_FLAG").toString() : "";
			// 属于加班、休假、出差、外出，哪种决裁，默认为加班决裁
			// String affirmType = paramMap.get("AFFIRM_TYPE") != null ?
			// paramMap.get("AFFIRM_TYPE").toString() : "";
			String[] paramData = request.getParameterValues("c1");
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("CREATED_BY", admin.getPersonId());
				map.put("ADMIN_ID", admin.getPersonId());
				map.put("CPNY_ID", admin.getCpnyId());
				map.put("CURRENT_AFFIRM_ID", admin.getPersonId());

				map.put("ESS_AFFIRM_NO", paramData[i]);
				linkedMap.put("ESS_AFFIRM_NO", map.get("ESS_AFFIRM_NO"));
				linkedMap = (LinkedHashMap) this.affirmApplyDao
						.getEssAffirmInfoByAffirmNo(linkedMap);
				map.put("AFFIRM_FLAG", affirmFlag);
				// 决裁级别
				String currentAffirmLevel = linkedMap.get("AFFIRM_LEVEL") != null ? linkedMap
						.get("AFFIRM_LEVEL").toString()
						: "0";
				map.put("APPLY_NO", linkedMap.get("APPLY_NO"));
				map.put("APPLY_TYPE", linkedMap.get("APPLY_TYPE"));
				map.put("currentAffirmLevel", currentAffirmLevel);
				// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
				int maxAffirmLevel = this.affirmApplyDao
						.getMaxAffirmLevelByApplyNo(map);
				// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
				if (Integer.parseInt(currentAffirmLevel) < maxAffirmLevel) {
					if ("1".equals(affirmFlag)) {
						int nextAffirmLevel = Integer
								.parseInt(currentAffirmLevel) + 1;
						map.put("NEXT_AFFIRM_LEVEL", nextAffirmLevel);
						LinkedHashMap nextHrAffirmMap = (LinkedHashMap) this.affirmApplyDao
								.getEssAffirmInfoByApplyNoAndLevel(map);
						String nextAffirmerId = nextHrAffirmMap
								.get("AFFIRMOR_ID") != null ? nextHrAffirmMap
								.get("AFFIRMOR_ID").toString() : admin
								.getPersonId();
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
				if (currentAffirmLevel.equals(String.valueOf(maxAffirmLevel))) {
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

				map.put("CPNY", admin.getCpnyId());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
				map.put("LANGUAGE", admin.getLanguage());
				String navTabId = request.getParameter("navTabId") != null ? request
						.getParameter("navTabId")
						: "";
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
	 * 批量封装信息申请信息成List(encapsulation information apply from request to List for
	 * batch)
	 * 
	 * @param request
	 * @return
	 */
	private List encapsulationApplyAffirmListForBatchForMaamgement(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap linkedMap = new LinkedHashMap();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			
			String[] paramData = request.getParameterValues("c1");
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap affrimMap = new LinkedHashMap();
		 //开始:14014306; 部门申请: 14014307;部门长批准 ：14014308;部门长返回:14014309;上申取消:14014310;部门申请(后):14014311;部门长批准(后):14014312;
				String affirmFlag = paramMap.get("AFFIRM_FLAG"+paramData[i]) != null ? paramMap
						.get("AFFIRM_FLAG"+paramData[i]).toString() : "";
			    String FROM_DATE = paramMap.get("FROM_DATE"+paramData[i]) != null ? paramMap
								.get("FROM_DATE"+paramData[i]).toString() : "";
				String TO_DATE = paramMap.get("TO_DATE"+paramData[i]) != null ? paramMap
										.get("TO_DATE"+paramData[i]).toString() : "";
				String ITEM_NO = paramMap.get("ITEM_NO"+paramData[i]) != null ? paramMap
										.get("ITEM_NO"+paramData[i]).toString() : "";
				String PERSON_ID = paramMap.get("PERSON_ID"+paramData[i]) != null ? paramMap
												.get("PERSON_ID"+paramData[i]).toString() : "";
				String AFFIRM_CONTENT = paramMap.get("AFFIRM_CONTENT"+paramData[i]) != null ? paramMap
												.get("AFFIRM_CONTENT"+paramData[i]).toString() : "";
												
			String NO = paramData[i];
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
		     
		    map.put("PK_NO", NO);
/*			String APPLY_NO = (String) this.affirmOtApplyDao.getEssAffirmInfoForApplyNo(map);
			
			if (!"".equals(APPLY_NO)&&APPLY_NO != null) {
				//根据PK_NO获取相应的APPLY_NO
				map.put("APPLY_NO", APPLY_NO);
				affrimMap.put("AFFIRMOR_ID",admin.getPersonId());//登陆者的ID
				affrimMap.put("APPLY_NO",APPLY_NO);//登陆者的ID
			    String ESS_AFFIRM_NO = (String) this.affirmOtApplyDao.getEssAffirmInfoForEssAffrimNO(affrimMap);
				 map.put("ESS_AFFIRM_NO", ESS_AFFIRM_NO);
			}*/
			
		        map.put("AFFIRM_FLAG", affirmFlag);
		        map.put("ITEM_NO", ITEM_NO);
		        map.put("FROM_DATE", FROM_DATE);
		        map.put("TO_DATE", TO_DATE);
		        map.put("PERSON_ID", PERSON_ID);
				map.put("CPNY", admin.getCpnyId());
				map.put("CREATED_BY", admin.getPersonId());
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("ADMIN_ID", admin.getAdminID());
				map.put("LANGUAGE", admin.getLanguage());
				map.put("AFFIRM_CONTENT",AFFIRM_CONTENT );
/*				String navTabId = request.getParameter("navTabId") != null ? request
						.getParameter("navTabId")
						: "";
				map.put("navTabId", navTabId);*/
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
	public int approveOvertimeApply(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 将页面添加的决裁者插入到已有的决裁者列表中，注意顺序
		int result = this.addNewAffirmorList(paramMap, request);
		// 最新的决裁者插入之后再进行决裁
		if (result == 1) {
			// 封装加班申请数据并处理
			this.affirmApplyDao.saveOvertimeApplyAffirm(this
					.encapsulationApplyAffirmMap(request, false));
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
	public int appAUUNApply(HttpServletRequest request) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 将页面添加的决裁者插入到已有的决裁者列表中，注意顺序
		int result = this.addNewAffirmorList(paramMap, request);
		// 最新的决裁者插入之后再进行决裁
		if (result == 1) {
			// 封装加班申请数据并处理
			this.affirmApplyDao.saveAnnualadjustmentAffirm(this
					.encapsulationApplyAffirmMap(request, false));
		}
		return 1;
	}

	/**
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private int addNewAffirmorList(LinkedHashMap paramMap,
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
			if(personId != null && !"".equals(personId)){
				affirmMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
				affirmMap.put("APPLY_TYPE", paramMap.get("APPLY_TYPE").toString());
				affirmMap.put("AFFIRM_LEVEL", affirmLevel);
				affirmMap.put("CREATED_BY", AFFIRMOR_ID);
				affirmMap.put("UPDATED_BY", AFFIRMOR_ID);
				addAffirmList.add(affirmMap);
			}
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
	 * 封装发令信息成map(encapsulation transaction from request to map)
	 * 
	 * @param request
	 * @return
	 */
	private LinkedHashMap encapsulationApplyAffirmMap(
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
			String cpnyId = affirmApplyDao.getCpnyIdByPersonId(map);
			map.put("currentAffirmLevel", currentAffirmLevel);
			map.put("CPNY", cpnyId);
			map.put("CPNY_ID", cpnyId);
			map.put("CREATED_BY", AFFIRMOR_ID);
			map.put("UPDATED_BY", AFFIRMOR_ID);
			map.put("ADMIN_ID", AFFIRMOR_ID);
			map.put("LANGUAGE", map.get("LANGUAGE"));
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
	public List getOtCheckList(HttpServletRequest request) throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getOtCheckList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
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
	public int getOtCheckListCnt(HttpServletRequest request) throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		return affirmApplyDao.getOtCheckListCnt(paramMap);
	}

	/**
	 * check加班申请(check overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int checkApplyInfo(HttpServletRequest request) throws Exception {
		int result = 1;
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		if (request.getParameter("PERSON_ID") == null || "".equals(request.getParameter("PERSON_ID"))) {
			paramMap = ObjectBindUtil.getRequestParamData(request);
		} else {
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("supervisorPersonId", paramMap.get("PERSON_ID"));
		}
		// LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int flag = this.affirmApplyDao.checkApplyInfo(paramMap);
		if(flag == 1){
			//发送check通过命令
			Map<String, String> ipMap = new HashMap<String, String>();
			ipMap.put("217886", "proveApplicationList");
			ipMap.put("21", "viewLeaveCheck");
			ipMap.put("218294", "RecordCheck");
			ipMap.put("218296", "checkDimissionApplyInfo");
			ipMap.put("224", "checkLeftMenApplyInfo");
			ipMap.put("218197", "CwaCheck");
			ipMap.put("31", "startApplyCheckInfo");
			ipMap.put("216691", "AnnuCheck");
			LinkedHashMap map = new LinkedHashMap();
			map.put("APPLY_NO", paramMap.get("APPLY_NO"));
			map.put("APPLY_TYPE", paramMap.get("APPLY_TYPE"));
			map.put("AFFIRM_LEVEL", paramMap.get("ESS_CHECK_NO"));
			map.put("AFFIRM_FLAG", 1);
			map.put("AFFIRM_EMPID", paramMap.get("PERSON_ID"));
			map.put("AAI_URL", "http://{serverIp}/LGEP/affirm/"+ipMap.get(paramMap.get("APPLY_TYPE"))+"?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("PERSON_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			affirmInfoToLGEPSer.check(map);
		}
		return result;
	}

	/**
	 * 封装check信息成map(encapsulation transaction from request to map)
	 * 
	 * @param request
	 * @return
	 */
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
			linkedMap = (LinkedHashMap) this.affirmApplyDao
					.getEssCheckInfoByCheckNo(linkedMap);
			// 决裁级别
			String currentCheckLevel = linkedMap.get("CHECK_LEVEL") != null ? linkedMap
					.get("CHECK_LEVEL").toString()
					: "1";
			// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
			int maxCheckLevel = this.affirmApplyDao
					.getMaxCheckLevelByApplyNo(linkedMap);
			// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
			if (Integer.parseInt(currentCheckLevel) < maxCheckLevel) {
				int nextCheckLevel = Integer.parseInt(currentCheckLevel) + 1;
				map.put("NEXT_CHECK_LEVEL", nextCheckLevel);
				LinkedHashMap nextCheckMap = (LinkedHashMap) this.affirmApplyDao
						.getEssCheckInfoByApplyNoAndLevel(map);
				String nextCheckorId = nextCheckMap.get("CHECKOR_ID") != null ? nextCheckMap
						.get("CHECKOR_ID").toString()
						: admin.getPersonId();
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
	 */
	public int addApplyCheckList(HttpServletRequest request) throws Exception {
		int result = 1;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("ESS_AFFIRM_NO", request.getParameter("ESS_AFFIRM_NO"));
		paramMap.put("PERSON_ID", request.getParameter("dwz.person.personId"));
		paramMap.put("navTabId", request.getParameter("navTabId"));
		// 查看此checkor者是否已经存在
		LinkedHashMap linkedMap = new LinkedHashMap();
		linkedMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO"));
		linkedMap.put("CHECKOR_ID", paramMap.get("PERSON_ID"));

		// 查看此决裁人是否还有当前checkor人了
		linkedMap = (LinkedHashMap) this.affirmApplyDao
				.getEssAffirmInfoByAffirmNo(linkedMap);
		String currentCheckFlag = null != linkedMap.get("CURRENT_CHECKOR_ID") ? linkedMap
				.get("CURRENT_CHECKOR_ID").toString()
				: "";
		linkedMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO"));
		linkedMap.put("CHECKOR_ID", paramMap.get("PERSON_ID"));
		// 如果此人之前没有加入为此条加班申请的checkor人，插入
		// if(cnt==0){
		// 插入新的checkor
		paramMap.put("CHECKOR_ID", paramMap.get("PERSON_ID") != null ? paramMap
				.get("PERSON_ID").toString() : admin.getPersonId());
		this.affirmApplyDao.addApplyCheckList(paramMap);
		// 如果当前没有checkor，还需要更新ess_affirm中的current_checkor_id，如果已有current_checkor_id，则只插入新的checkor即可
		if (null == currentCheckFlag || "".equals(currentCheckFlag)) {
			paramMap.put("NEXT_CHECKOR_ID",
					paramMap.get("PERSON_ID") != null ? paramMap.get(
							"PERSON_ID").toString() : admin.getPersonId());
			this.affirmApplyDao.updateCurrentCheckor(paramMap);
		}
		// 如果此人之前已经加入为此条加班申请的checkor人，不需要插入
		// }else{
		// result = -1;
		// }
		return result;
	}

	/**
	 * 通过request请求封装查询条件(get search conditions for request)
	 * 
	 * @param request
	 * @return
	 */
	private LinkedHashMap getMapByRequestForSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		//为了查出只有本人审批的申请，和不是本人审批的申请做的处理
		if(paramMap.get("AFFIRM_FLAG")!=null&&paramMap.get("AFFIRM_FLAG").equals("onlypersonid")){
			paramMap.put("AFFIRM_FLAG", "4");
			paramMap.put("ONLYPERSONID", paramMap.get("supervisorPersonId"));
		}else if(paramMap.get("AFFIRM_FLAG")!=null&&paramMap.get("AFFIRM_FLAG").equals("notonlypersonid")){
			paramMap.put("AFFIRM_FLAG", "4");
			paramMap.put("NOTONLYPERSONID", paramMap.get("supervisorPersonId"));
		}
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
	public List getLeaveApplyAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		
		paramMap.put("APPLY_TYPE_NO", "21");// 21为休假申请
		list = affirmApplyDao.getLeaveApplyAffirmList(paramMap);
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getLeaveApplyAffirmList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getLeaveApplyAffirmList(paramMap);
		}
		if(list != null && list.size() > 0 ){
			for(int i = 0;i<list.size() ; i++){
				LinkedHashMap leaveMap = (LinkedHashMap)list.get(i);
				List affirmorList = infoApplyLeaveDao.getAffirmorByApplyNoList(leaveMap);
				leaveMap.put("affirmorList", affirmorList);
			}
		}
		return list;
	}
	/**
	 * 休假申请决裁列表(leave apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getAttendanceAffirmList(HttpServletRequest request)
	throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		String firstFlag = request.getParameter("firstFlag");
		//第一次进页面 默认只查询当月
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar c = Calendar.getInstance();    
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
		paramMap.put("ADMIN_ID", admin.getAdminID());// 21为休假申请
		list = affirmApplyDao.getAttendanceAffirmList(paramMap);
		return list;
	}

	/**
	 * 休假申请决裁总数(leave apply affirm list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getLeaveApplyAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		// String menuNo=request.getParameter("menuNo");
		// paramMap.put("APPLY_TYPE_NO", "21");// 21为休假申请
		// // 123634为喜丧假申请 123646 调休
		// if(menuNo.equals("123649")||menuNo.equals("124822")||menuNo.equals("124824")||menuNo.equals("124826")){
		// paramMap.put("TYPE", "123634");
		// }else
		// if(menuNo.equals("123650")||menuNo.equals("124823")||menuNo.equals("124825")||menuNo.equals("124827")){
		// paramMap.put("TYPE", "123646");
		// }else
		// if(menuNo.equals("2441")||menuNo.equals("2449")||menuNo.equals("2454")||menuNo.equals("2479")){
		// paramMap.put("TYPE", "21");
		// }else{
		// paramMap.put("TYPE", "0");
		// }
		// paramMap.put("TYPE", "21");
		paramMap.put("APPLY_TYPE_NO", "21");// 21为休假申请
		//为了查出只有本人审批的申请，和不是本人审批的申请做的处理
		
		// paramMap.put("APPLY_TYPE_NO", "16413");// 考勤申请类型
		return affirmApplyDao.getLeaveApplyAffirmListCnt(paramMap);
	}

	/**
	 * 出差申请决裁列表(evection apply affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
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
	 * 批量通过/否决休假(batch pass and reject leave/evection/egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveLeaveApplyInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装加班申请数据并处理
			this.affirmApplyDao.saveLeaveApplyAffirmInBatchForMangement(this
					.encapsulationApplyAffirmListForBatchForMaamgement(request));
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

	@Override
	public int approveApplyLeave(HttpServletRequest request) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		// 将页面添加的决裁者插入到已有的决裁者列表中，注意顺序
		int result = this.addNewAffirmorList(paramMap, request);
		// 最新的决裁者插入之后再进行决裁
		if (result == 1) {
			// 封装加班申请数据并处理
			this.affirmApplyDao.saveApplyLeaveAffirm(this
					.encapsulationApplyAffirmMap(request, false));
		}
		return 1;
	}

	public void updateApplicatCheck(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		affirmApplyDao.updateApplicatCheck(paramMap);
	}

	public int approveApplication(HttpServletRequest request) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();//ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("PERSON_ID") == null || "".equals(request.getParameter("PERSON_ID"))){
			//AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap = ObjectBindUtil.getRequestParamData(request) ;
		}else{
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		}
		// 将页面添加的决裁者插入到已有的决裁者列表中，注意顺序
		int result = this.addNewAffirmorList(paramMap, request);
		// 更新决裁数据
		if (result == 1) {
			paramMap.put("UPDATED_BY", paramMap.get("PERSON_ID"));
			affirmApplyDao.approveApplication(paramMap);
		}
		// 如果是最后一个决裁者，则向工资计算表里添加数据
		if (paramMap.get("lastLevel") != null) {
			affirmApplyDao.addPaParamApplication(paramMap);
		}
		//如果当前审批状态为通过状态或者不是最后一级决裁者时，则发送邮件
		if(paramMap.get("lastLevel") == null){
			if("1".equals(paramMap.get("AFFIRM_FLAG"))){
				paramMap.put("APPLY_TYPE", 217886);
				LinkedHashMap lgepMap = (LinkedHashMap) affirmApplyDao.getNextAffirmProver(paramMap);
				if (paramMap.get("lastLevel") == null) {
					lgepMap.put("AFFIRM_FLAG", 1);  
				} else {// 决裁完成
					lgepMap.put("FINISH", "FINISH");
					lgepMap.put("AFFIRM_FLAG", paramMap.get("FLAG"));
					lgepMap.put("AFFIRM_LEVEL", paramMap.get("dept_level"));
				}
				lgepMap.put("AFFIRM_EMPID", paramMap.get("PERSON_ID"));
				lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/proveApplicationList?APPLY_NO="+paramMap.get("APPLY_NO")+"&PERSON_ID="+lgepMap.get("AFFIRMOR_ID")+"&ESS_AFFIRM_NO="+lgepMap.get("ESS_AFFIRM_NO"));
				lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/proveApplicationList?APPLY_NO="+paramMap.get("APPLY_NO")+"&PERSON_ID="+lgepMap.get("AFFIRMOR_ID")+"&ESS_AFFIRM_NO="+lgepMap.get("ESS_AFFIRM_NO"));
				lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/proveApplicationList?APPLY_NO="+paramMap.get("APPLY_NO")+"&PERSON_ID="+lgepMap.get("AFFIRMOR_ID")+"&ESS_AFFIRM_NO="+lgepMap.get("ESS_AFFIRM_NO"));
				lgepMap.put("PRE_AFFIRM_EMPID", lgepMap.get("AFFIRMOR_ID"));
				this.affirmInfoToLGEPSer.affirm(lgepMap);
			}
		}
		return 1;
	}

	@Override
	public int approveApplyCwa(HttpServletRequest request) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 将页面添加的决裁者插入到已有的决裁者列表中，注意顺序
		int result = this.addNewAffirmorList(paramMap, request);
		// 最新的决裁者插入之后再进行决裁
		if (result == 1) {
			// 封装加班申请数据并处理
			this.affirmApplyDao.saveApplyCwaAffirm(this
					.encapsulationApplyAffirmMap(request, false));
		}
		return 1;
	}
	
	
	@Override
	public int approveApplyCwaEP(HttpServletRequest request) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		//将页面添加的决裁者插入到已有的决裁者列表中，注意顺序
		int result = this.addNewAffirmorListOfTwo(paramMap, request);
		//最新的决裁者插入之后再进行决裁
		if(result == 1){
		// 封装加班申请数据并处理
			this.affirmApplyDao.saveApplyCwaAffirm(this.encapsulationApplyAffirmMapOfTwo(request, false));
		}
		return 1;
	}
	
	/**
	 * 封装发令信息成map(encapsulation transaction from request to map)
	 * EP
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
			String cpnyId = affirmApplyDao.getCpnyIdByPersonId(map);
			map.put("CPNY", cpnyId);
			map.put("CPNY_ID", cpnyId);
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

	@Override
	public List getLeaveCheckList(HttpServletRequest request) throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("PERSON_ID", paramMap.get("ADMIN_ID"));
		if (UiUtil.getPageNum(request) > 0) {
			list = affirmApplyDao.getLeaveCheckList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = affirmApplyDao.getLeaveCheckList(paramMap);
		}
		return list;
	}

	@Override
	public int getLeaveCheckListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("PERSON_ID", paramMap.get("ADMIN_ID"));
		return affirmApplyDao.getLeaveCheckListCnt(paramMap);
	}
}