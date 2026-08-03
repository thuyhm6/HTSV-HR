package com.ait.inct.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ait.sys.bean.AdminBean;
import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.AffirmApplyDao;
import com.ait.inct.dao.SalesmanIncentiveDao;
import com.ait.inct.service.SalesmanIncentiveSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
/**
 * 
 * @fileName SalesmanIncentiveSerImpl.java
 * @author 			@Date			@version		@Desc
 * ---------------------------------------------------------------------------------------------------------------
 * penghaixia 		2014-6-4 		1.0				initial  
 *----------------------------------------------------------------------------------------------------------------
 */
@Service
public class SalesmanIncentiveSerImpl implements SalesmanIncentiveSer {

	@Autowired
	private SalesmanIncentiveDao salesmanIncentiveDao;
	@Autowired
	private AffirmApplyDao affirmApplyDao;
	
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	
	//营业员提成调整申请类型代码
	private static String APPLY_TYPE_NO = "219763"; 
	
	//营业员提成调整邀请名称
	private static String APPLY_TYPE_NAME = "营业员提成调整决裁邀请";

	//营业员提成调整邀请名称
	private static String APPLY_TITLE_SUFFIX = "营业员提成调整决裁邀请";

	/**
	 * 营业员提成   查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getIncentiveCalcList(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = salesmanIncentiveDao.getIncentiveCalcList(paramMap , 
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
				);
		}else{
			retrunList = salesmanIncentiveDao.getIncentiveCalcList(paramMap) ;
		}
		return retrunList;
	}
	/**
	 * 营业员提成
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getIncentiveCalcListCnt(HttpServletRequest request, Map paramMap){
		return salesmanIncentiveDao.getIncentiveCalcListCnt(paramMap);
	}
	
	/**
	 * 取工资关帐标志 
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public String getSalesInctClosedFlag(HttpServletRequest request, Map paramMap){
		String rtn = "";
		if(paramMap.get("ACCRUAL_YN").equals("Y")){
			rtn = salesmanIncentiveDao.getSalesInctAccrualCalcClosFlagByQuarter(paramMap)==0?"N":"Y";
		}else{
			rtn = salesmanIncentiveDao.getSalesInctCalcClosFlagByQuarter(paramMap)==0?"N":"Y";
		}
		return rtn;
	}
	@Override
	public String getSalesInctPayClosedFlagByMonth(HttpServletRequest request, Map paramMap) {
		String rtn = "";
		if(paramMap.get("ACCRUAL_YN").equals("Y")){
			rtn = salesmanIncentiveDao.getSalesInctAccrualCalcClosFlagByMonth(paramMap)==0?"N":"Y" ;
		}else{
			rtn = salesmanIncentiveDao.getSalesInctCalcClosFlagByMonth(paramMap)==0?"N":"Y" ;
		}
		return rtn;
	}

	/**
	 * 营业员提成  提成计算执行
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public int callSalesmanIncentiveCalc(HttpServletRequest request, Map paramMap) {
		int rtn=0;
		try {
			String result = salesmanIncentiveDao.callSalesmanIncentiveCalc(paramMap);
			if(result.equals("OK")) rtn = 1; else rtn = 0;
		} catch (Exception e) {
			e.printStackTrace();
			rtn = 0;
		}
		return rtn;
	}

	/**
	 * 营业员提成  读取变动工资执行执行
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public int callSalesmanVariablePayRead(HttpServletRequest request, Map paramMap) {
		int rtn=0;
		try {
			String result = salesmanIncentiveDao.callSalesmanVariablePayRead(paramMap);
			if(result.equals("OK")) rtn = 1; else rtn = 0;
		} catch (Exception e) {
			e.printStackTrace();
			rtn = 0;
		}
		return rtn;
	}
	/**
	 * 营业员提成   员工别查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public Map getIncentiveCalcByItem(HttpServletRequest request, Map paramMap) {
		return salesmanIncentiveDao.getSalesIncentiveCalcItem(paramMap);
	}
	
	/**
	 * 营业员   员工别提成修改申请
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public int editIncentiveCalcItemReq(HttpServletRequest request, Map paramMap) {
		int rtn=0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		paramMap.put("UPDT_USER", admin.getEmpID());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("REQ_TITLE", paramMap.get("EMPNO") + APPLY_TITLE_SUFFIX);
		//取审批线
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		try{
			//生成提成修改申请
			int reqId = salesmanIncentiveDao.getIncCalcAdjuMstReqId(paramMap);
			paramMap.put("REQ_ID", reqId);
			paramMap.put("SUBMIT_STATUS", 1);  //0:未提交，1：已提交
			paramMap.put("CURRENT_AFFIRM_ID", affirmId[0]); //当前裁决者person_id
			paramMap.put("CURRENT_CHECK_ID", "");//当前check者ID
			paramMap.put("COMMON_EMPID", "");//共同社编
			paramMap.put("AFFIRM_FLAG", 0);//0：未决裁；1：通过；2：否决
			salesmanIncentiveDao.insertIncCalcAdjuMst(paramMap);
			salesmanIncentiveDao.insertIncCalcAdjuDtl(paramMap);
			//生成决裁线
			paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
			for(int i=0; i<affirmId.length; i++){
				paramMap.put("AFFIRMOR_ID", affirmId[i]);
				paramMap.put("AFFIRM_LEVEL", i+1);
				salesmanIncentiveDao.insertAffirmor(paramMap);
			}						
			//提交状态发送LGEP
			this.sendToLGEP(paramMap);
			rtn = 1;		
		} catch (Exception e) {
			e.printStackTrace();
			rtn = 0;
		}
		return rtn;
	}
	
	/**
	 * 营业员提成   员工别修改
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public int editIncentiveCalcItem(HttpServletRequest request, Map paramMap) {
		int rtn=0;
		try {
			salesmanIncentiveDao.updateSalesIncAdjustByEmp(paramMap);
			salesmanIncentiveDao.updateSalesIncCalculateByEmp(paramMap);

			rtn=1;
		} catch (Exception e) {
			e.printStackTrace();
			rtn = 0;
		}
		return rtn;
	}

	/**
	 * 营业员提成   excel数据 上传
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getSalesIncCalculateImportList(HttpServletRequest request,
			Map paramMap) {
		List retrunList = new ArrayList() ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = salesmanIncentiveDao.getSalesIncCalculateImportList(paramMap , 
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
				);
		}else{
			retrunList = salesmanIncentiveDao.getSalesIncCalculateImportList(paramMap) ;
		}
		return retrunList;
	}
	/**
	 * 营业员提成   excel数据 导出
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getSalesIncCalculateImportListExcel(HttpServletRequest request,
			Map paramMap) {
		List retrunList = new ArrayList() ;
		retrunList = salesmanIncentiveDao.getSalesIncCalculateImportList(paramMap) ;
		return retrunList;
	}
	@Override
	public int getSalesIncCalculateImportListCnt(HttpServletRequest request, Map paramMap){
		return salesmanIncentiveDao.getSalesIncCalculateImportListCnt(paramMap);
	}
	@Override
	public int getSalesIncCalculateImportErrCnt(HttpServletRequest request,
			Map paramMap) {
		return salesmanIncentiveDao.getSalesIncCalculateImportErrCnt(paramMap);
	}
	@Override
	public int callSalesmanIncentiveCalcImport(HttpServletRequest request,
			Map paramMap) {
		int rtn=0;
		try {
			String result = salesmanIncentiveDao.callSalesmanIncentiveCalcImport(paramMap);
			if(result.equals("OK")) rtn = 1; else rtn = 0;
		} catch (Exception e) {
			e.printStackTrace();
			rtn = 0;
		}
		return rtn;
	}
	/**
	 * 营业员提成调整   查询
	 * @param request
	 * @return
	 * @Create date: 2014.08.18
	 */
	@Override
	public List getIncentiveCalcAdjuList(HttpServletRequest request,
			Map paramMap) {
		List retrunList = new ArrayList() ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = salesmanIncentiveDao.getIncentiveCalcAdjuList(paramMap , 
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
				);
		}else{
			retrunList = salesmanIncentiveDao.getIncentiveCalcAdjuList(paramMap) ;
		}
		return retrunList;
	}
	@Override
	public int getIncentiveCalcAdjuListCnt(HttpServletRequest request,
			Map paramMap) {
		return salesmanIncentiveDao.getIncentiveCalcAdjuListCnt(paramMap);
	}
	/**
	 * 营业员提成   单个申请别查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public Map getIncentiveCalcAdjuListByReqId(HttpServletRequest request, Map paramMap) {
		return salesmanIncentiveDao.getIncentiveCalcAdjuListByReqId(paramMap);
	}
	
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEP(Map paramMap){
		LinkedHashMap lgepMap = new LinkedHashMap();
		lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
		lgepMap.put("APPLY_NO", paramMap.get("REQ_ID"));
		lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
		lgepMap.put("APPLY_TITLE", paramMap.get("REQ_TITLE"));
		lgepMap.put("APPLY_EMPID", paramMap.get("UPDT_USER"));
		lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewSalesmanInctCalcAdjuAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&REQ_ID=" + paramMap.get("REQ_ID") + "&pageNum=1");
		lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewSalesmanInctCalcAdjuAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&REQ_ID=" + paramMap.get("REQ_ID") + "&pageNum=1");
		lgepMap.put("AFFIRM_LEVEL", "1");
		lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
		lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
		this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}
	
	/**
	 * 审批后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEP(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = (LinkedHashMap)this.salesmanIncentiveDao.getIncentiveCalcAdjuListByReqId(paramMap);
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("REQ_ID"));
			lgepMap.put("CREATED_BY", lgepMap.get("PERSON_ID"));
			if("0".equals(paramMap.get("FLAG").toString())){
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{
				//决裁完成
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("FLAG"));
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("dept_level"));
			}
			lgepMap.put("AFFIRM_EMPID", paramMap.get("AFFIRM_EMPID"));
			lgepMap.put("AAI_URL", "");
			lgepMap.put("AAF_URL", "");
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewSalesmanInctCalcAdjuAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + lgepMap.get("CURRENT_AFFIRM_ID") + "&REQ_ID=" + lgepMap.get("REQ_ID") + "&pageNum=1");
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}
	
	/**
	 * 获取决裁情况
	 * 
	 * @author penghaixia
	 * @date 2014-8-20
	 * @version V1.0
	 */
	public List getAffirmorListByReqId(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("REQ_ID", request.getParameter("REQ_ID"));
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		retrunList = salesmanIncentiveDao.getAffirmorList(paramMap);
		return retrunList;
	}
	
	/**
	 * 获取check信息
	 *
	 * @author penghaixia
	 * @date 2014-8-19
	 * @version V1.0
	 */
	public List getCheckListByReqId(HttpServletRequest request){
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("REQ_ID", request.getParameter("REQ_ID"));
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		retrunList = salesmanIncentiveDao.getCheckList(paramMap);
		return retrunList;
	}
	@Override
	public List getIncentiveCalcAdjuDtlList(HttpServletRequest request,
			Map paramMap) {
		List retrunList = new ArrayList() ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = salesmanIncentiveDao.getIncentiveCalcAdjuDtlList(paramMap , 
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
				);
		}else{
			retrunList = salesmanIncentiveDao.getIncentiveCalcAdjuDtlList(paramMap) ;
		}
		return retrunList;
	}
	@Override
	public int getIncentiveCalcAdjuDtlListCnt(HttpServletRequest request,
			Map paramMap) {
		return salesmanIncentiveDao.getIncentiveCalcAdjuDtlListCnt(paramMap);
	}
	
	/**
	 * 营业员提成调整审批
	 * 
	 * @author PengHaixia
	 * @date 2014-8-20
	 * @version V1.0
	 */
	public int affirmSalesmanInctCalcAdju(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			String personid = request.getParameter("AFFIRMOR_ID");
			if(personid == null || "".equals(personid)){
				personid = admin.getAdminID();
			}
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			if(request.getParameter("LANGUAGE") == null || "".equals(request.getParameter("LANGUAGE"))){
				paramMap.put("interLanguage", admin.getLanguage());
			}else{
				paramMap.put("interLanguage", request.getParameter("LANGUAGE"));
			}
			this.addNewAffirmorList( request);
			String[] isChecked = request.getParameterValues("se0203Check");
			paramMap.put("FLAG", paramMap.get("FLAG"));
			paramMap.put("UPDATED_BY", admin.getEmpID()) ;
			paramMap.put("UPDT_USER", admin.getEmpID()) ;
			paramMap.put("SUBSD_CD", admin.getCpnyId()) ;
			paramMap.put("PERSON_ID", personid) ;
			for(int i = 0; i < isChecked.length; i++){
				paramMap.put("REQ_ID", paramMap.get("REQ_ID" + "_" + isChecked[i]));
				paramMap.put("APPLY_NO", paramMap.get("REQ_ID" + "_" + isChecked[i]));
				paramMap.put("AFFIRM_CONTENT", paramMap.get("AFFIRM_CONTENT" + "_" + isChecked[i]));
				paramMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO" + "_" + isChecked[i]));
				paramMap.put("AFFIRM_LEVEL_CURRENT", paramMap.get("AFFIRM_LEVEL_CURRENT" + "_" + isChecked[i]));
				paramMap.put("CURRENT_AFFIRM_ID",  "");
				paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
				this.salesmanIncentiveDao.affirmSalesmanInctCalcAdju(paramMap);
				if(!"2".equals(paramMap.get("FLAG"))){//否决直接修改标志位,通过继续下面操作
					//获取未决裁者列表
					paramMap.put("ACTIVITY", "0");
					List<LinkedHashMap> affirmList = this.salesmanIncentiveDao.getAffirmorList(paramMap);
					if (affirmList != null && affirmList.size() > 0) {
						for (LinkedHashMap parmers : affirmList) {
							paramMap.put("CURRENT_AFFIRM_ID",  parmers.get("AFFIRMOR_ID"));
							paramMap.put("AFFIRM_EMPID",  parmers.get("EMPID"));
							paramMap.put("FLAG", "0");
							paramMap.put("AFFIRM_LEVEL", parmers.get("AFFIRM_LEVEL"));
							break;
						}
					}
				}
				this.salesmanIncentiveDao.approveSalesmanInctCalcAdju(paramMap);
				this.sendToLGEP(paramMap);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 新增审批线人员处理
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private int addNewAffirmorList(
			HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
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
			affirmMap.put("APPLY_NO", paramMap.get("REQ_ID_0").toString());
			affirmMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			affirmMap.put("AFFIRM_LEVEL", affirmLevel);
			affirmMap.put("CREATED_BY", admin.getEmpID());
			affirmMap.put("UPDATED_BY", admin.getEmpID());

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
			affirmorCnt = this.affirmApplyDao.getAffirmorCntByApplyNo(addAffirmorMap);
			if (affirmorCnt == 0) {
				addAffirmorMap.put("AFFIRM_LEVEL", affirmLeave);
				this.affirmApplyDao.addNewApplyAffirmor(addAffirmorMap);
				affirmLeave = affirmLeave + 1;
			}
		}

		return result;
	}
}
