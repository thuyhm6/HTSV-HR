package com.ait.ess.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.AffirmApplyDao;
import com.ait.ess.dao.ArMacRecordApplyDao;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.service.ArMacRecordApplySer;
import com.ait.ess.service.InfoApplySer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.CommonException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: ArMacRecordApplySerImpl.java
 * @Description:
 * @Create date: Jan 31, 2012 6:44:09 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Jan 31, 2012 6:44:09 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Service
public class ArMacRecordApplySerImpl implements ArMacRecordApplySer {

	Logger logger = Logger.getLogger(ArMacRecordApplySerImpl.class);

	@Autowired
	private AuthorityUtil authorityUtil;
	
	@Autowired
	private ArMacRecordApplyDao ArMacRecordApplyDao;
	
	@Autowired
	private InfoApplyDao infoApplyDao;
	
	@Autowired
	private InfoApplySer  infoApplySer ;
	
	@Autowired
	AffirmApplyDao affirmApplyDao;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	@Autowired
	private PaTempSalesDAO paTempSalesDAO;
	
	/**
	 * 添加进出门刷卡申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addArMacRecordApply(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		
		//获取页面参数、并剔除为空的参数
		String flag = request.getParameter("flag");
		String personId = null;
		if(flag=="1"){
			personId = request.getParameter("dwz.person.personId");
		}else{
		    personId = paramMap.get("PERSON_ID")!=null?paramMap.get("PERSON_ID").toString():"";
		}
		String applyTypeNo = paramMap.get("APPLY_TYPE_NO")!=null?paramMap.get("APPLY_TYPE_NO").toString():"";
		String applyTypeCode = paramMap.get("APPLY_TYPE_CODE")!=null?paramMap.get("APPLY_TYPE_CODE").toString():"";
		String doorType = paramMap.get("DOOR_TYPE")!=null?paramMap.get("DOOR_TYPE").toString():"";
		String recordType = paramMap.get("RECORD_TYPE")!=null?paramMap.get("RECORD_TYPE").toString():"";
		
		
		String remark = paramMap.get("REMARK")!=null?paramMap.get("REMARK").toString():"";
		
		String rDate = paramMap.get("R_DATE")!=null?paramMap.get("R_DATE").toString():"";
		//String applyDate = paramMap.get("APPLY_DATE")!=null?paramMap.get("APPLY_DATE").toString():"";
		String rHour = paramMap.get("R_HOUR")!=null?paramMap.get("R_HOUR").toString():"00";
		String rMinute = paramMap.get("R_MINUTE")!=null?paramMap.get("R_MINUTE").toString():"00";
		String FILE_URL =  StringUtil.checkNull(paramMap.get("fileUrl"));
		String FILE_NAME = StringUtil.checkNull(paramMap.get("fileName"));
		LinkedHashMap dateMap = new LinkedHashMap();
		dateMap = this.getLinkedMapByRequest(request, dateMap);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", personId);
		paramMap.put("APPLY_TYPE", applyTypeNo);
		paramMap.put("APPLY_TYPE_NO", applyTypeNo);
		paramMap.put("APPLY_TYPE_CODE", applyTypeCode);
		paramMap.put("DOOR_TYPE", doorType);
		paramMap.put("RECORD_TYPE", recordType);
		paramMap.put("APPLY_DATE", rDate);
		paramMap.put("REMARK", remark);
		if("LGEKS".equals(admin.getCpnyId())){
			paramMap.put("R_TIME", rDate+" "+rHour+":00");
		}else{
			paramMap.put("R_TIME", rDate+" "+rHour+":"+rMinute+":00");
		}
		paramMap.put("AFFIRM_FLAG",0);
		paramMap.put("FILE_NAME", FILE_NAME);
		paramMap.put("FILE_URL", FILE_URL);
		LinkedHashMap otMap = this.preAddArMacRecordApply(paramMap, admin.getLanguage(),request);
		batchOtApplyList.add(otMap);
		
		this.ArMacRecordApplyDao.addArMacRecordApplyInBatch(batchOtApplyList);
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
	private int addNewAffirmorListnotwo(LinkedHashMap paramMap,HttpServletRequest request) throws Exception {
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
			affirmMap.put("CREATED_BY", admin.getPersonId());
			affirmMap.put("UPDATED_BY", admin.getPersonId());
			
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
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List addNewAffirmorList(LinkedHashMap paramMap,HttpServletRequest request) throws Exception {
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
			//affirmMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
			affirmMap.put("APPLY_TYPE", paramMap.get("APPLY_TYPE").toString());
			affirmMap.put("AFFIRM_LEVEL", affirmLevel);
			affirmMap.put("CREATED_BY", admin.getPersonId());
			affirmMap.put("UPDATED_BY", admin.getPersonId());
			
			addAffirmList.add(affirmMap);
		}
		
		return addAffirmList;
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
	 * 通过request请求封装查询条件(get search conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequestForSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
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
	
	/**
	 * 通过request请求封装查询条件(get conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequest(HttpServletRequest request,LinkedHashMap paramMap) {
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
	 * 封装要插入的刷卡申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddArMacRecordApply(LinkedHashMap paramMap,String language) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		LinkedHashMap essParamMap = new LinkedHashMap();
		List distinctList = new ArrayList();
		Object paramValueObj = null;
		String ifNeedAffirm = "";
		String ifNeedConfirm = "";
		
		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
//		essParamMap.put("ESS_PARAM_NO", "4159");//是否需要决裁
//		paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
//		ifNeedAffirm = paramValueObj != null ? paramValueObj.toString() : "0";
//		essParamMap.put("ESS_PARAM_NO", "4160");//是否需要人事确认
//		paramValueObj = this.infoApplyDao.getEssParamInfoByParamNoAndCpnyId(essParamMap);
//		ifNeedConfirm = paramValueObj != null ? paramValueObj.toString() : "0";
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
		List affirmerList = this.getAffirmorListByMap(paramMap);

		if (affirmerList.size() == 0 ) {//&& "1".equals(ifNeedAffirm)
			// 未给该员工设置决裁者时
			throw new CommonException("不能为"+ personMap.get("LOCAL_NAME")+"做刷卡申请，请先为其设置决裁者！");
		} else {
			int count = 1;
			boolean flag = true;
			Map filterMap = new LinkedHashMap();
			for (int i = 0; i < affirmerList.size(); i++) {
				LinkedHashMap affirmerMap = (LinkedHashMap) affirmerList.get(i);
				if (affirmerMap.get("AFFIRMOR_ID") != null) {
					if (flag) {
						// 只将流程第一步的决裁者取出来存到刷卡申请表
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
			//各种验证（日期、时间、类型等）
			if (arCardRecordApplyCheck(paramMap, personMap, language)){
 				paramMap.put("ACTIVITY", "0");
 				returnMap.put("PARAM_MAP", paramMap);
 				returnMap.put("DISTINCT_LIST", distinctList);
//				if ("1".equals(ifNeedAffirm)) {
//					paramMap.put("ACTIVITY", "0");
//					returnMap.put("PARAM_MAP", paramMap);
//					returnMap.put("DISTINCT_LIST", distinctList);
//				} else if ("0".equals(ifNeedAffirm) && "1".equals(ifNeedConfirm)) {
//					// 不需要决裁,但需要人事确认
//					paramMap.put("ACTIVITY", "0");
//					returnMap.put("PARAM_MAP", paramMap);
//				} else if ("0".equals(ifNeedAffirm) && "0".equals(ifNeedConfirm)) {
//					// 既不需要决裁也不需要人事确认
//					throw new CommonException(
//							TipMessage.getTipMessage("alert.message.ess.infoApply.otApplyShouldAffirmOrConfirm",language));
//				}
				
			}
		}
		return returnMap;
	}
	
	
	/**
	 * 封装要插入的刷卡申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddArMacRecordApply(LinkedHashMap paramMap,String language,HttpServletRequest request) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		LinkedHashMap essParamMap = new LinkedHashMap();
		
		essParamMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
		

		//页面添加的决裁者列表
		List affirmerList = new ArrayList();
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
				affirmerList.add(affirmMap);
			}
		}
		
		LinkedHashMap affirmerMap = (LinkedHashMap) affirmerList.get(0);
		if (affirmerMap.get("AFFIRMOR_ID") != null) {
			// 只将流程第一步的决裁者取出来存到刷卡申请表
			paramMap.put("CURRENT_AFFIRM_ID", affirmerMap.get("AFFIRMOR_ID"));
		}
		//各种验证（日期、时间、类型等）
		if (arCardRecordApplyCheck(paramMap, personMap, language)){
			paramMap.put("ACTIVITY", "0");
			returnMap.put("PARAM_MAP", paramMap);
			returnMap.put("DISTINCT_LIST", affirmerList);
		}
		return returnMap;
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
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
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
		}
	}
	
	/**
	 * 检查刷卡申请是否合格,并提示相应的信息(examine mac record apply if correct and give message)
	 * 
	 * @param essOverTimeBean
	 * @return 返回 HashMap
	 */
	@SuppressWarnings("unchecked")
	private boolean arCardRecordApplyCheck(LinkedHashMap paramMap,
			LinkedHashMap personMap, String language) throws Exception {
		// 加班时间与之前申请加班时间检查
		int cnt = this.isExsitApplyData(paramMap);
		if (cnt>0) { 
			throw new CommonException(
					TipMessage.getTipMessage("alert.message.ess.infoApply.failedPerson",language)+ personMap.get("LOCAL_NAME")
							+ "当天已有相同时间的刷卡申请记录，请勿重复申请");
		}
		
		if("LGEPN".equals(paramMap.get("CPNY_ID")) && "C".equals(paramMap.get("RECORD_TYPE"))){
			int applyDataCount = this.ArMacRecordApplyDao.isExistArRecordApplyLeaveCnt(paramMap);
			if (applyDataCount==0) {
				throw new CommonException("因公未打卡必须先申请外出/出差/培训");
			}
		}
		if("LGEKS".equals(paramMap.get("CPNY_ID"))){
			int applyDataCount = this.ArMacRecordApplyDao.isXiuXi(paramMap);
			if (applyDataCount==0) {
				throw new CommonException("休息日期不能申请漏刷卡，如有需要找考勤担当协助申请");
			}
		}
		return true;
	}

	/**
	 * 刷卡申请是否与已有刷卡申请冲突(mac record apply conflict with exist mac record apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int arCardRecordConflictWithExsitApplyData(Map paraMap)throws Exception {
		int applyDataCount = 0;
		applyDataCount = this.ArMacRecordApplyDao.getExistArRecordApplyCnt(paraMap);
		
		return applyDataCount;
	}

	/**
	 * 刷卡申请是否与已有刷卡申请冲突(mac record apply conflict with exist mac record apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int isExsitApplyData(Map paraMap)throws Exception {
		int applyDataCount = 0;
		applyDataCount = this.ArMacRecordApplyDao.isExistArRecordApplyCnt(paraMap);
		
		return applyDataCount;
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
		//封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		return this.infoApplySer.getAffirmorListByString("218294", StringUtil.checkNull(paramMap.get("PERSON_ID")), null, null, StringUtil.checkNull(paramMap.get("LANGUAGE")));
	}
	
	/**
	 * 方法说明（批量进出门刷卡申请人员列表,batch mac record apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		if (UiUtil.getPageNum(request) > 0) {
			returnList = ArMacRecordApplyDao.getPersonList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = ArMacRecordApplyDao.getPersonList(paramMap);
		}
		return returnList;
	}
	
	/**
	 * 批量进出门刷卡申请的人员列表总数(get mac record employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPersonListCnt(HttpServletRequest request) throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,"seach_");
		return this.infoApplyDao.getPersonListCnt(paramMap);
	}
	
	/**
	 * 批量添加进出门刷卡申请(add overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addArMacRecordApplyInBatch(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		String[] paramData = request.getParameterValues("c1");
		for (int j = 0; j < paramData.length; j++) {
			String personId = paramData[j];
			LinkedHashMap paramMap = this.getLinkedMapByRequest(request);

			String applyDate = paramMap.get(personId+"_BATCH_APPLY_DATE")!=null?paramMap.get(personId+"_BATCH_APPLY_DATE").toString():"";
			String rDate = paramMap.get(personId+"_BATCH_R_DATE")!=null?paramMap.get(personId+"_BATCH_R_DATE").toString():"";
			String rHour = paramMap.get(personId+"_BATCH_R_HOUR")!=null?paramMap.get(personId+"_BATCH_R_HOUR").toString():"00";
			String rMinute = paramMap.get(personId+"_BATCH_R_MINUTE")!=null ?paramMap.get(personId+"_BATCH_R_MINUTE").toString():"00";
			String remark = paramMap.get(personId+"_BATCH_REMARK")!=null ?paramMap.get(personId+"_BATCH_REMARK").toString():"";
			String applyTypeCode = paramMap.get(personId+"_BATCH_APPLY_TYPE_CODE")!=null?paramMap.get(personId+"_BATCH_APPLY_TYPE_CODE").toString():"";
			String applyTypeNo = paramMap.get("BATCH_APPLY_TYPE_NO")!=null?paramMap.get("BATCH_APPLY_TYPE_NO").toString():"";
			//获取页面参数、并剔除为空的参数
			String doorType = "";
			if(applyTypeCode!=null && "123500".equals(applyTypeCode)){
				doorType = "IN";//进门标志
			}else if(applyTypeCode!=null && "123501".equals(applyTypeCode)){
				doorType = "OUT";//出门标志
			}
			LinkedHashMap dateMap = new LinkedHashMap();
			dateMap = this.getLinkedMapByRequest(request, dateMap);
			dateMap.put("CPNY_ID", admin.getCpnyId());
			dateMap.put("PERSON_ID", personId);
			dateMap.put("APPLY_TYPE_NO", applyTypeNo);
			dateMap.put("APPLY_TYPE_CODE", applyTypeCode);
			dateMap.put("DOOR_TYPE", doorType);
			dateMap.put("APPLY_DATE", applyDate);
			dateMap.put("REMARK", remark);
			dateMap.put("R_TIME", rDate+" "+rHour+":"+rMinute+":00");
			
			LinkedHashMap otMap = this.preAddArMacRecordApply(dateMap, admin.getLanguage());
			batchOtApplyList.add(otMap);
		}

		this.ArMacRecordApplyDao.addArMacRecordApplyInBatch(batchOtApplyList);
		return 1;
	}
	
	/**
	 * 通过request请求封装查询条件(get search conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequestForSearch(HttpServletRequest request, String flag) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,flag);
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
	 * 信息决裁用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getArMacRecordAffirmList(HttpServletRequest request)throws Exception {
		List list = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("APPLY_TYPE_NO", "218294");// 123499为IN/OUT进出门刷卡申请
		paramMap.put("CURRENT_ID",admin.getPersonId());
		paramMap.put("menuNum", request.getParameter("menuNum"));
		
		if (UiUtil.getPageNum(request) > 0) {
			list = ArMacRecordApplyDao.getArMacRecordAffirmList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = ArMacRecordApplyDao.getArMacRecordAffirmList(paramMap);
		}
		return this.getAffirmerListForInfoApply(list, request);
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
	private List getAffirmerListForInfoApply(List list,HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		LinkedHashMap paramMap = null;
		LinkedHashMap childMap = null;
		@SuppressWarnings("unused")
		LinkedHashMap childrenMap = new LinkedHashMap();
		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			String hrefFlag = "1";
			//借用别的方法，所以这里要转换一下参数名称
			paramMap.put("APPLY_NO", paramMap.get("RECORD_NO")!=null?paramMap.get("RECORD_NO").toString():"1");
			List aList = this.affirmApplyDao.getEssAffirmInfoByApplyNo(paramMap);
			List childList = new ArrayList();
			for (int j = 0; j < aList.size(); j++) {
				childMap = (LinkedHashMap) aList.get(j);
				childMap.put("HREF_FLAG", hrefFlag);
				String affirmFlag = childMap.get("AFFIRM_FLAG") != null ? childMap.get("AFFIRM_FLAG").toString(): "";
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

			returnList.add(paramMap);
		}
		return returnList;
	}
	
	/**
	 * 信息决裁用，in/out进出门刷卡数据信息申请个数(view ar mac record apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getArMacRecordAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("APPLY_TYPE_NO", "218294");// 31为加班申请
		paramMap.put("CURRENT_ID",admin.getPersonId());
		paramMap.put("menuNum", request.getParameter("menuNum"));
		return ArMacRecordApplyDao.getArMacRecordAffirmListCnt(paramMap);
	}
	
	/**
	 * 决裁情况，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getArMacRecordAffirmViewList(HttpServletRequest request,String batchFlag)throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap =  null;
		
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			paramMap.put("PERSON_ID",paramMap.get("personId"));
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("PERSON_ID", paramMap.get("personId") == null ? admin.getPersonId() : paramMap.get("personId"));
		}
		if(paramMap.get("FLAG") == null || !"1".equals(paramMap.get("FLAG").toString())){
			paramMap.put("KEY", admin.getEmpID());
		}
		paramMap.put("batchFlag", batchFlag);//是否批量申请的标示
		paramMap.put("APPLY_TYPE_NO", "218294");//123499为IN/OUT进出门刷卡申请 218294
		paramMap.put("menuNum", request.getParameter("menuNum"));
		paramMap.put("AR_SUPERVISIOR_YN","YES");
		paramMap.put("interLanguage", paramMap.get("LANGUAGE"));

		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		
		if (UiUtil.getPageNum(request) > 0) {
			list = ArMacRecordApplyDao.getArMacRecordAffirmViewList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = ArMacRecordApplyDao.getArMacRecordAffirmViewList(paramMap);
		}
		/*if(admin != null){
		  list = this.getAffirmerListForInfoApply(list, request);
		}*/
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)list.get(i);
				returnMap.put("APPLY_TYPE", "218294");
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		return list;
	}
	
	/**
	 * 决裁情况，in/out进出门刷卡数据信息申请个数(view ar mac record apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getArMacRecordAffirmViewListCnt(HttpServletRequest request,String batchFlag)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		if(paramMap.get("FLAG") == null || !"1".equals(paramMap.get("FLAG").toString())){
			paramMap.put("KEY", admin.getEmpID());
		}
		paramMap.put("APPLY_TYPE_NO", "218294");// 31为加班申请
		paramMap.put("AR_SUPERVISIOR_YN","YES");
		paramMap.put("menuNum", request.getParameter("menuNum"));
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		paramMap.put("batchFlag", batchFlag);//是否批量申请的标示
		
		return ArMacRecordApplyDao.getArMacRecordAffirmViewListCnt(paramMap);
	}
	
	/**
	 * 通过/否决：in/out进出门刷卡申请(pass and reject ar mac record apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveArMacRecordApplyEP(HttpServletRequest request)
			throws Exception {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			//将页面添加的决裁者插入到已有的决裁者列表中，注意顺序
			int result = this.addNewAffirmorListOfTwo(paramMap, request);
			//最新的决裁者插入之后再进行决裁
			if(result == 1){
			// 封装加班申请数据并处理
				this.ArMacRecordApplyDao.saveArMacRecordApplyAffirm(this.encapsulationApplyAffirmMapOfTwo(request, false));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 通过/否决：in/out进出门刷卡申请(pass and reject ar mac record apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveArMacRecordApply(HttpServletRequest request)
			throws Exception {
		try {
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
			int result = this.addNewAffirmorListnotwo(paramMap, request);
			//最新的决裁者插入之后再进行决裁
			if(result == 1){
			// 封装加班申请数据并处理
				this.ArMacRecordApplyDao.saveArMacRecordApplyAffirm(this.encapsulationApplyAffirmMap(request, false));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
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
	 * 批量通过/否决：in/out进出门刷卡申请(batch pass and reject ar mac record apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveArMacRecordApplyInBatch(HttpServletRequest request)
			throws Exception {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			//将页面添加的决裁者插入到已有的决裁者列表中，注意顺序
			int result = this.addNewAffirmorListnotwo(paramMap, request);
			//最新的决裁者插入之后再进行决裁
			if(result == 1){
			// 批量封装加班申请数据并处理
				this.ArMacRecordApplyDao.saveArMacRecordApplyAffirmInBatch(this.encapsulationApplyAffirmListForBatch(request));
			}
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
	@SuppressWarnings("unchecked")
	private LinkedHashMap encapsulationApplyAffirmMap(
			HttpServletRequest request, boolean ifEntryTrans) {
		LinkedHashMap linkedMap = new LinkedHashMap();
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面提交数据
			LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
			map.put("UPDATED_BY", admin.getPersonId());
			map.put("CREATED_BY", admin.getPersonId());
			map.put("CPNY_ID", admin.getCpnyId());
			// 当前决裁者
			map.put("CURRENT_AFFIRM_ID", admin.getPersonId());
			linkedMap.put("ESS_AFFIRM_NO", map.get("ESS_AFFIRM_NO"));
			linkedMap = (LinkedHashMap) this.affirmApplyDao.getEssAffirmInfoByAffirmNo(linkedMap);
			// 决裁级别
			String currentAffirmLevel = linkedMap.get("AFFIRM_LEVEL") != null ? linkedMap.get("AFFIRM_LEVEL").toString(): "0";
			// 决裁通过/否决标识
			String affirmFlag = map.get("AFFIRM_FLAG") != null ? map.get("AFFIRM_FLAG").toString() : "0";
			map.put("APPLY_NO", linkedMap.get("APPLY_NO"));
			// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
			int maxAffirmLevel = this.affirmApplyDao.getMaxAffirmLevelByApplyNo(map);
			// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
			if (Integer.parseInt(currentAffirmLevel) < maxAffirmLevel) {
				if ("1".equals(affirmFlag)) {
					int nextAffirmLevel = Integer.parseInt(currentAffirmLevel) + 1;
					map.put("NEXT_AFFIRM_LEVEL", nextAffirmLevel);
					LinkedHashMap nextHrAffirmMap = (LinkedHashMap) this.affirmApplyDao.getEssAffirmInfoByApplyNoAndLevel(map);
					String nextAffirmerId = nextHrAffirmMap.get("AFFIRMOR_ID") != null ? nextHrAffirmMap
							.get("AFFIRMOR_ID").toString(): admin.getPersonId();
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

			map.put("CPNY", admin.getCpnyId());
			map.put("CREATED_BY", admin.getAdminID());
			map.put("UPDATED_BY", admin.getAdminID());
			map.put("ADMIN_ID", admin.getAdminID());
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
			String affirmFlag = paramMap.get("AFFIRM_FLAG") != null ? paramMap.get("AFFIRM_FLAG").toString() : "";
			//属于加班、休假、出差、外出，哪种决裁，默认为加班决裁
			//String affirmType = paramMap.get("AFFIRM_TYPE") != null ? paramMap.get("AFFIRM_TYPE").toString() : "";
			String[] paramData = request.getParameterValues("AR_MAC_AFFIRM");
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
				map.put("APPLY_NO", linkedMap.get("APPLY_NO"));
				// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
				int maxAffirmLevel = this.affirmApplyDao.getMaxAffirmLevelByApplyNo(linkedMap);
				// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
				if (Integer.parseInt(currentAffirmLevel) < maxAffirmLevel) {
					if ("1".equals(affirmFlag)) {
						int nextAffirmLevel = Integer.parseInt(currentAffirmLevel) + 1;
						map.put("NEXT_AFFIRM_LEVEL", nextAffirmLevel);
						if(linkedMap != null && linkedMap.get("APPLY_TYPE") != null){
							map.put("APPLY_TYPE", linkedMap.get("APPLY_TYPE"));
						}
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

				map.put("CPNY", admin.getCpnyId());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
				map.put("AFFIRM_LEVEL", currentAffirmLevel);
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
	 * 删除未审核：in/out进出门刷卡信息申请(delete ar mac record apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delArMacRecordApplyInfo(HttpServletRequest request)
			throws Exception {
		//Map paramMap = ObjectBindUtil.getRequestParamData(request);

		return ArMacRecordApplyDao.delArMacRecordApplyInfo(this.encapsulationApplyNoListForBatch(request));
	}
	
	@SuppressWarnings("unchecked")
	private List encapsulationApplyNoListForBatch(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("AR_MAC_DEL");
			String applytype = request.getParameter("APPLY_TYPE");
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("APPLY_NO", paramData[i]);
				map.put("APPLY_TYPE", applytype);
				
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int saveRecordAppFile(HttpServletRequest request, Map<String, Object> map) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("FILEURL", map.get("fileUrl"));
		paramMap.put("OLDNAME", map.get("oldName"));
		paramMap.put("NEWNAME", map.get("newName"));
		paramMap.put("RECORD_NO", map.get("RECORD_NO"));
		return ArMacRecordApplyDao.saveRecordAppFile(paramMap);
		
	}

	@Override
	public List getCardRecordFileList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else if (paramMap.get("RECORD_NO")==null) {
			paramMap.put("RECORD_NO", "");
		}
		return ArMacRecordApplyDao.getCardRecordFileList(paramMap);
	}
	

	/**
	 * 决裁情况，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmCheckViewList(HttpServletRequest request)throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap =  null;
		
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			paramMap.put("PERSON_ID",paramMap.get("personId"));
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("PERSON_ID", paramMap.get("personId") == null ? admin.getPersonId() : paramMap.get("personId"));
		}
		paramMap.put("APPLY_TYPE_NO", "218294");//123499为IN/OUT进出门刷卡申请 218294
		paramMap.put("interLanguage", paramMap.get("LANGUAGE"));
		
		if (UiUtil.getPageNum(request) > 0) {
			list = ArMacRecordApplyDao.getArMacRecordAffirmViewCheckList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = ArMacRecordApplyDao.getArMacRecordAffirmCheckViewList(paramMap);
		}
		if(admin != null){
		  list = this.getAffirmerListForInfoApply(list, request);
		}
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)list.get(i);
				returnMap.put("APPLY_TYPE", "218294");
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		return list;
	}
	
	/**
	 * 决裁情况，in/out进出门刷卡数据信息申请个数(view ar mac record apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getArMacRecordAffirmCheckViewListCnt(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "218294");// 31为加班申请
		
		return ArMacRecordApplyDao.getArMacRecordAffirmCheckListCnt(paramMap);
	}
	

	/**
	 * 决裁情况，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getArMacRecordAffirmViewListBySingle(HttpServletRequest request)throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap =  null;
		
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			paramMap.put("PERSON_ID",paramMap.get("personId"));
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("PERSON_ID", paramMap.get("personId") == null ? admin.getPersonId() : paramMap.get("personId"));
		}
		paramMap.put("APPLY_TYPE_NO", "218294");//123499为IN/OUT进出门刷卡申请 218294
		paramMap.put("menuNum", request.getParameter("menuNum"));
		paramMap.put("AR_SUPERVISIOR_YN","YES");
		paramMap.put("interLanguage", paramMap.get("LANGUAGE"));

		list = ArMacRecordApplyDao.getArMacRecordAffirmViewListBySingle(paramMap);
		if(admin != null){
		  list = this.getAffirmerListForInfoApply(list, request);
		}
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)list.get(i);
				returnMap.put("APPLY_TYPE", "218294");
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		return list;
	}
	


	/**
	 * 获取漏刷卡导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArMacTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = ArMacRecordApplyDao.getEssArMacTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = ArMacRecordApplyDao.getEssArMacTempList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取漏刷卡导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArMacTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = ArMacRecordApplyDao.getEssArMacTempErrorCnt(paramMap);
		}else{
			retrunInt = ArMacRecordApplyDao.getEssArMacTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	/**
	 * 漏刷卡批量申请excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelEssArMacEmpData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "pkg_ar_mac_excel_imp.pr_import_ar_mac_data");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	

	/**
	 * 漏刷卡批量申请详细信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getArMacBatchAffirmInfoList(HttpServletRequest request) throws Exception {
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
			returnList = ArMacRecordApplyDao.getEssArMacList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = ArMacRecordApplyDao.getEssArMacList(paramMap);
		}
		
		if(returnList != null && returnList.size() > 0){
			for(int i=0;i<returnList.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)returnList.get(i);
				returnMap.put("APPLY_TYPE", "218294");
				returnMap.put("APPLY_NO", returnMap.get("RECORD_NO"));
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		
		return returnList;
	}
	
	/**
	 * 漏刷卡批量申请详细信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getArMacBatchAffirmInfoCnt(HttpServletRequest request){
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
			retrunInt = ArMacRecordApplyDao.getEssArMacCnt(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retrunInt;
	}
	

	/**
	 * 批量删除漏刷卡申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delArMacApplyInBatchForBatch(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap personMap = new LinkedHashMap();
		personMap.put("APPLY_PERSON", admin.getPersonId());
		String op_flag = request.getParameter("OP_FLAG");
		try {
			List list = this.encapsulationApplyNoListForArMac(request);
			if("0".equals(op_flag)){//删除
				this.ArMacRecordApplyDao.delArMacRecordApplyInfo(list);
			}else{//提交
				this.ArMacRecordApplyDao.submitArMacApplyInBatch(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	private List encapsulationApplyNoListForArMac(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("BATCH_MAC");
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("APPLY_NO", paramData[i]);
				map.put("APPLY_TYPE", "218294");
				
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getArMacRecordApplyInfoForDisplay(HttpServletRequest request)throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request, "seach_") ;

		LinkedHashMap returnMap = ArMacRecordApplyDao.getArMacRecordApplyInfoForDisplay(paramMap);
		if("N".equals(returnMap.get("BATCH_YN"))){
			returnMap.put("APPLY_TYPE", "218294");
			List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
			returnMap.put("fileList", fileList);
		}
		return returnMap;
	}

	/**
	 * 取消已审核通过的漏刷卡申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean cancelCardApply(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMIN_ID", admin.getPersonId());
		
		return ArMacRecordApplyDao.cancelCardApply(paramMap);
	}
}
