package com.ait.pa.service.imp.insurance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.InsuranceInputItemDao;
import com.ait.pa.dao.PaBasicItemDao;
import com.ait.pa.service.insurance.InsuranceInputItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceInputItemSerImp.java
 * @Description:
 * @Create date: 2012-1-16 下午06:47:41
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@SuppressWarnings({"unchecked","unused"})
@Service
public class InsuranceInputItemSerImp implements InsuranceInputItemSer {

	Logger logger = Logger.getLogger(InsuranceInputItemSerImp.class);
	
	@Autowired
	private InsuranceInputItemDao insuranceInputItemDao;
	
	@Autowired
	private PaBasicItemDao paBasicItemDao;
	
	/**
	 * 获取保险输入项目信息（get Insurance Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getInsuranceInputItemInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnObj = insuranceInputItemDao.getInsuranceInputItemInfo(paramMap) ;
		
		
		return returnObj ;
	}
	
	/**
	 * 获取保险输入项目参数信息（get Insurance Input Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getInsuranceInputItemParamInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		returnObj = insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap) ;
		
		
		return returnObj ;
	}
	
	/**
	 * 获取保险输入项目列表（get Insurance Input Item List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public List getInsuranceInputItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		paramMap.put("INSURANCE_INPUT_PARAM", request.getAttribute("INSURANCE_INPUT_PARAM"));
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				insuranceInputItemDao.getInsuranceInputItemList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = insuranceInputItemDao.getInsuranceInputItemList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public List getInsuranceInputItemParamList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				insuranceInputItemDao.getInsuranceInputItemParamList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
	
		}
		else{
			retrunList = insuranceInputItemDao.getInsuranceInputItemParamList(paramMap);
		}
		return retrunList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public List getInsuranceInputItemParamListForData(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("ACTIVITY", "1");
		retrunList = insuranceInputItemDao.getInsuranceInputItemParamList(paramMap);
		
		return retrunList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public List getIsParamDataList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		LinkedHashMap isInputItemParamInfo = (LinkedHashMap)this.insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		String distinctField1 = ObjectUtils.toString(isInputItemParamInfo
				.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(isInputItemParamInfo
				.get("DISTINCT_FIELD_2ND"));
		paramMap.put("distinctField1", distinctField1);
		paramMap.put("distinctField2", distinctField2);
		if("CPNY_ID".equals(distinctField1)){	
			retrunList = insuranceInputItemDao.getIsParamDataIsCpnyIdList(paramMap);
		}else if("DEPTNO".equals(distinctField2)){
			retrunList = insuranceInputItemDao.getIsParamDataList(paramMap);
		}else{
			retrunList = insuranceInputItemDao.getIsParamDataList(paramMap);
		}
		return retrunList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public List getIsParamDataTwoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		LinkedHashMap isInputItemParamInfo = (LinkedHashMap)this.insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		String distinctField2 = ObjectUtils.toString(isInputItemParamInfo
				.get("DISTINCT_FIELD_2ND"));
		paramMap.put("distinctField2", distinctField2);
		if("DEPTNO".equals(distinctField2)){
			retrunList = insuranceInputItemDao.getIsParamDataIsDeptNoList(paramMap);
		}else{
			retrunList = insuranceInputItemDao.getIsParamDataTwoList(paramMap);
		}
		
		
		
		
		return retrunList ;
	}
	
	/**
	 * 获取保险输入项目个数（get Insurance Input Item Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public int getInsuranceInputItemCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");

		retrunInt = insuranceInputItemDao.getInsuranceInputItemCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	/**
	 * 获取保险输入项目参数个数（get Insurance Input Item Param Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public int getInsuranceInputItemParamCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = insuranceInputItemDao.getInsuranceInputItemParamCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	/**
	 * 获取保险输入项目参数页面提交数据（Get Insurance Input Item Param）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	/*
	
	private LinkedHashMap setGetInsuranceInputItemParam(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		
		// 判读PA_MONTH参数是否为空,为空赋予当前月
		String paMonth = ObjectUtils.toString(paramMap.get("PA_MONTH_STR")) ;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
			paramMap.put("PA_MONTH_STR", paMonth) ;
		}
		
		
		return paramMap ;
	}*/
	/**
	 * 验证保险输入项目信息，是否可添加（check Add Insurance Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int checkAddInsuranceInputItemInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.insuranceInputItemDao.checkAddInsuranceInputItemInfo(paramMap) ;
		
	}
	
	
	/**
	 * 验证保险输入项目参数信息，是否可添加（check Add Insurance Input Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int checkAddInsuranceInputItemParamInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.insuranceInputItemDao.checkAddInsuranceInputItemParamInfo(paramMap) ;
		
	}

	/**
	 * 添加保险输入项目信息（add Insurance Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int addInsuranceInputItemInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		try {
			this.insuranceInputItemDao.addInsuranceInputItemInfo(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 添加保险输入项目参数信息（add Insurance Input Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int addInsuranceInputItemParamInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		
		return this.insuranceInputItemDao.addInsuranceInputItemParamInfo(paramMap) ;
	}
	
	/**
	 * 修改保险输入项目信息（update Insurance Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int updateInsuranceInputItemInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		try {
			this.insuranceInputItemDao.updateInsuranceInputItemInfo(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 修改保险输入项目参数信息（update Insurance Input Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int updateInsuranceInputItemParamInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		
		this.insuranceInputItemDao.updateInsuranceInputItemParamInfo(paramMap) ;
		
		return 0;
	}
	
	/***
	 * mapping旧系统保险项目  只修改  map_code
	 * @param request
	 * @return
	 */
	public int updateIsInputItemParamInfo(HttpServletRequest request){
        AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		try {
			this.insuranceInputItemDao.updateIsInputItemParamInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 验证保险输入项目信息是否可删除（check Delete Insurance Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int checkDeleteInsuranceInputItemInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("TABLE_NAME", "IS_SUMMARY");
		
		return this.insuranceInputItemDao.checkDeleteInsuranceInputItemInfo(paramMap) ;
	}
	
	/**
	 * 删除保险输入项目信息（delete Insurance Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int deleteInsuranceInputItemInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try{
			this.insuranceInputItemDao.deleteInsuranceInputItemInfo(paramMap) ;
		}
		catch(Exception e){
			e.printStackTrace() ;
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 验证保险输入项目信息是否可删除（check Delete Insurance Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int checkDeleteInsuranceInputItemParamInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.insuranceInputItemDao.checkDeleteInsuranceInputItemParamInfo(paramMap) ;
	}
	
	/**
	 * 删除保险输入项目参数信息（delete Insurance Input Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int deleteInsuranceInputItemParamInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try{
			this.insuranceInputItemDao.deleteInsuranceInputItemParamInfo(paramMap) ;
		}
		catch(Exception e){
			e.printStackTrace() ;
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 获取不同参数列表（get Distinct Field List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public List getDistinctFieldList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());

		paramMap.put("PARAM_FLAG", "1");
		retrunList = insuranceInputItemDao.getDistinctFieldList(paramMap) ;
		
		return retrunList ;
	}
	
	/**
	 * 获取保险输入项目数据列表（get Insurance Input Item Data List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public List getInsuranceInputItemDataList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		// 取得数据项目信息
		LinkedHashMap insuranceInputItemInfo = (LinkedHashMap)this.getInsuranceInputItemInfo(request) ;
		
		if(ObjectUtils.toString(insuranceInputItemInfo.get("DISTINCT_FIELD")).equals("PERSON_ID")){
			retrunList = insuranceInputItemDao.getInsuranceInputItemDataListDistinctFieldIsEmpid(paramMap) ;
		}
		else{
			retrunList = insuranceInputItemDao.getInsuranceInputItemDataListDistinctFieldIsNotEmpid(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 删除保险输入项目输入信息（delete Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int deleteInsuranceInputItemDataInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		this.insuranceInputItemDao.deleteInsuranceInputItemDataInfo(paramMap) ;
		
		return 0;
	}
	
	/**
	 * 删除保险输入项目输入信息（delete Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int deleteInsuranceInputItemDataInfoType(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		this.insuranceInputItemDao.deleteInsuranceInputItemDataInfoType(paramMap) ;
		
		return 0;
	}
	
	/**
	 * 删除保险输入项目输入信息（delete Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int deleteInsuranceInputItemDataBatchInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.insuranceInputItemDao.deleteInsuranceInputItemDataBatchInfo(paramMap) ;
		
	}
	
	/**
	 * 删除保险输入项目输入信息（delete Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int deleteInsuranceInputItemDataBatchInfoType(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.insuranceInputItemDao.deleteInsuranceInputItemDataBatchInfoType(paramMap) ;
		
	}
	
	
	/**
	 * 验证删除保险输入项目输入信息（check Delete Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int checkDeleteInsuranceInputItemDataInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.insuranceInputItemDao.checkDeleteInsuranceInputItemDataInfo(paramMap);
	}
	/**
	 * 验证删除保险输入项目输入信息（check Delete Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int checkDeleteInsuranceInputItemDataInfoType(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.insuranceInputItemDao.checkDeleteInsuranceInputItemDataInfoType(paramMap);
	}
	
	/**
	 * 初始化保险输入项目信息（create Insurance Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public int createInsuranceInputItemInfo(HttpServletRequest request) {
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		this.insuranceInputItemDao.createInsuranceInputItemInfo(paramMap) ;
		
		return 0 ;
	}
	
	/**
	 * 初始化添加保险输入项目数据信息（create Add Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public int createAddInsuranceInputItemDataInfo(HttpServletRequest request) {
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		this.insuranceInputItemDao.createAddInsuranceInputItemDataInfo(paramMap) ;
		
		return 0 ;
	}
	
	/**
	 * 修改保险输入项目数据信息（update Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public int updateInsuranceInputItemDataInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String[] paramData = request.getParameterValues("c1");
		LinkedHashMap paInputItemInfo = (LinkedHashMap)this.getInsuranceInputItemParamInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);

		for (int i = 0; i < paramData.length; i++) {
			
			paramMap.put("PARAM_DATA_NO", paramData[i]);
			paramMap.put("START_MONTH", request.getParameter("START_MONTH_"+paramData[i]));
			paramMap.put("END_MONTH", request.getParameter("END_MONTH_"+paramData[i]));
			paramMap.put("RETURN_VALUE", request.getParameter("RETURN_VALUE_"+paramData[i]));
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("REMARK", request.getParameter("REMARK_"+paramData[i]));
			if(distinctField.equals("PERSON_ID")){
				this.insuranceInputItemDao.updateInsuranceInputItemDataInfo(paramMap) ;
			}else{
				this.insuranceInputItemDao.updateInsuranceInputItemDataInfoOther(paramMap);
			}
			
		}
		return 1 ;
	}
	
	
	
	public int deleteAllInsuranceInputItemDataInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String[] paramData = request.getParameterValues("c1");
		LinkedHashMap paInputItemInfo = (LinkedHashMap)this.getInsuranceInputItemParamInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);

		for (int i = 0; i < paramData.length; i++) {
			
			paramMap.put("PARAM_DATA_NO", paramData[i]);
			this.insuranceInputItemDao.deleteInsuranceInputItemDataBatchInfo(paramMap) ;
		 }
		return 1 ;
	}
	/**
	 * 获取添加保险输入项目数据列表（get Add Insurance Input Item Data List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public List getAddInsuranceInputItemDataList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		this.createAddInsuranceInputItemDataInfo(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		// 取得数据项目信息
		LinkedHashMap insuranceInputItemInfo = (LinkedHashMap)this.getInsuranceInputItemInfo(request) ;
		
		if(ObjectUtils.toString(insuranceInputItemInfo.get("DISTINCT_FIELD")).equals("EMPID")){
			retrunList = insuranceInputItemDao.getAddInsuranceInputItemDataListDistinctFieldIsEmpid(paramMap) ;
		}
		else{
			retrunList = insuranceInputItemDao.getAddInsuranceInputItemDataListDistinctFieldIsNotEmpid(paramMap) ;
		}
		
		return retrunList ;
		
	}
	
	/**
	 * 获取保险输入项目数据列表通过ParamNo（get Insurance Input Item Data List By ParamNo）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public List getInsuranceInputItemDataListByParamNo(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		// 取得数据项目信息
		LinkedHashMap isInputItemInfo = (LinkedHashMap)this.insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(isInputItemInfo.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String distinctField2 = ObjectUtils.toString(isInputItemInfo.get("DISTINCT_FIELD_2ND"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		Calendar c = Calendar.getInstance();
		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH)+1);
		String insYear=request.getParameter("seach_insYear") == null ? year : request.getParameter("seach_insYear");
		String insMonth=request.getParameter("seach_insMonth") == null ? month : request.getParameter("seach_insMonth");
		if(insYear != null && insMonth != null &&!("").equals(insYear) &&!("").equals(insMonth)){
			paramMap.put("IS_MONTH", insYear+insMonth);
		}
		/*if(insYear == null && insMonth ==null){
			paramMap.put("IS_MONTH", new SimpleDateFormat("yyyyMM").format(new Date()));
		}*/
		
		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			// 判断DISTINCT_FIELD是否是EMPID 两者返回的列表不同
			if (distinctField.equals("PERSON_ID")) {
				retrunList = insuranceInputItemDao
						.getInsuranceInputItemDataListDistinctFieldIsEmpid(
								paramMap, UiUtil.getPageNum(request), UiUtil
										.getNumPerPage(request));
			}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
				retrunList = insuranceInputItemDao
						.getInsuranceInputItemDataListDistinctFieldIsCpnyId(
								paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage(request));
			}
			//只针对与C01权限
			else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
				paramMap.put("C01_ADMINID", admin.getPersonId());
				retrunList = insuranceInputItemDao
						.getInsuranceInputItemDataListDistinctFieldIsNotEmpid(
								paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage(request));
			}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
				retrunList = insuranceInputItemDao
						.getInsuranceInputItemDataListDistinctFieldIsDeptNo(
								paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage(request));
			}else {
				retrunList = insuranceInputItemDao
						.getInsuranceInputItemDataListDistinctFieldIsNotEmpid(
								paramMap, UiUtil.getPageNum(request), UiUtil
										.getNumPerPage(request));
			}
		} else {
			// 判断DISTINCT_FIELD是否是EMPID 两者返回的列表不同
			if (distinctField.equals("PERSON_ID")) {
				retrunList = insuranceInputItemDao
						.getInsuranceInputItemDataListDistinctFieldIsEmpid(paramMap);
			}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
				retrunList = insuranceInputItemDao
				.getInsuranceInputItemDataListDistinctFieldIsCpnyId(paramMap);
			}
			//只针对与C01权限
			else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
				paramMap.put("C01_ADMINID", admin.getPersonId());
				retrunList = insuranceInputItemDao
				.getInsuranceInputItemDataListDistinctFieldIsNotEmpid(paramMap);
			}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
				retrunList = insuranceInputItemDao
				.getInsuranceInputItemDataListDistinctFieldIsDeptNo(paramMap);
			}else {
				retrunList = insuranceInputItemDao
						.getInsuranceInputItemDataListDistinctFieldIsNotEmpid(paramMap);
			}
		}
		return retrunList;
	}
	
	/**
	 * 获取保险输入项目数据列表通过ParamNo（get Insurance Input Item Data List By ParamNo）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public int getInsuranceInputItemDataListByParamNoCnt(HttpServletRequest request) {
		int retrunInt = 0;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 取得数据项目信息
		LinkedHashMap paInputItemInfo = (LinkedHashMap)this.insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD_2ND"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_ADMIN_ID", admin.getAdminID());
		Calendar c = Calendar.getInstance();
		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH)+1);
		String insYear=request.getParameter("seach_insYear") == null ? year : request.getParameter("seach_insYear");
		String insMonth=request.getParameter("seach_insMonth") == null ? month : request.getParameter("seach_insMonth");
		if(insYear != null && insMonth != null &&!("").equals(insYear) &&!("").equals(insMonth)){
			paramMap.put("IS_MONTH", insYear+insMonth);
		}
		/*if(insYear == null && insMonth ==null){
			paramMap.put("IS_MONTH", new SimpleDateFormat("yyyyMM").format(new Date()));
		}*/
		
		if (distinctField.equals("PERSON_ID")) {
			retrunInt = insuranceInputItemDao
					.getInsuranceInputItemDataListDistinctFieldIsEmpidCnt(paramMap);
		}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
			retrunInt = insuranceInputItemDao
			.getInsuranceInputItemDataListDistinctFieldIsCpnyIdCnt(paramMap);
		}
		//只针对与C01权限
		else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
			paramMap.put("C01_ADMINID", admin.getPersonId());
			retrunInt = insuranceInputItemDao
			.getInsuranceInputItemDataListDistinctFieldIsNotEmpidCnt(paramMap);
		}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
			retrunInt = insuranceInputItemDao
			.getInsuranceInputItemDataListDistinctFieldIsDeptNoCnt(paramMap);
		} else {
			retrunInt = insuranceInputItemDao
					.getInsuranceInputItemDataListDistinctFieldIsNotEmpidCnt(paramMap);
		}

		return retrunInt;	
	}
	
	/**
	 * 获取保险输入项目数据信息（get Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public Object getInsuranceInputItemDataInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnObj = insuranceInputItemDao.getInsuranceInputItemDataInfo(paramMap) ;
		return returnObj ;
	}
	
	/**
	 * 添加保险输入项目数据信息（add Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public int addInsuranceInputItemDataInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int result = 0;
		String itemNO = request.getParameter("ITEM_NO");
		String person_id = paramMap.get("dwz.person.personId")!=null ? paramMap.get("dwz.person.personId").toString() : "";
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("TABLE_NAME", "IS_PARAM_DATA");
		paramMap.put("PERSON_ID", person_id);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("START_MONTH_STR", paramMap.get("START_MONTH"));
		paramMap.put("END_MONTH_STR", paramMap.get("END_MONTH"));
		//如果是保险补扣 则可以累计数据
		if(this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) != 0 && !itemNO.equals("218445")&& !itemNO.equals("278661")){
			result = 2;
		}else{
			if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0 || itemNO.equals("218445") || itemNO.equals("278661")){
				this.insuranceInputItemDao.addInsuranceInputItemDataInfo(paramMap);
				result = 0;
			}else{
				this.insuranceInputItemDao.updateInsuranceInputItemDataInfoMonth(paramMap);
				this.insuranceInputItemDao.addInsuranceInputItemDataInfo(paramMap);
				result = 0;
			}
		}
		return result ;
	}
	
	/**
	 * 添加保险输入项目数据信息（add Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public int addInsuranceInputItemOtherDataInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int result = 0;
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("TABLE_NAME", "IS_PARAM_DATA_OTHER");
		paramMap.put("START_MONTH_STR", paramMap.get("START_MONTH"));
		if(this.paBasicItemDao.checkAddPaBasicItemDataOtherInfoMonth(paramMap) != 0){
			result = 2;
		}else{
			if(this.paBasicItemDao.checkAddPaBasicItemDataOtherInfo(paramMap) == 0){
				this.insuranceInputItemDao.addInsuranceInputItemOtherDataInfo(paramMap);
				result = 0;
			}else{
				this.insuranceInputItemDao.updateInsuranceInputItemDataOtherInfoMonth(paramMap);
				this.insuranceInputItemDao.addInsuranceInputItemOtherDataInfo(paramMap);
				result = 0;
			}
		}
		
		return result ;
	}
	
	/**
	 * 获取保险输入项目数据列表（get Insurance Input Item Data Person List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getInsuranceInputItemDataPersonList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = insuranceInputItemDao.getInsuranceInputItemDataPersonList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = insuranceInputItemDao.getInsuranceInputItemDataPersonList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取保险输入项目数据列表（get Insurance Input Item Data Person List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getInsuranceInputItemDataPersonListNoPage(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		retrunList = insuranceInputItemDao.getInsuranceInputItemDataPersonList(paramMap);
		
		return retrunList;
	}


	/**
	 * 获取保险输入项目数据个数（get Insurance Input Item Data Person List Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getInsuranceInputItemDataPersonListCnt(HttpServletRequest request) {
		int retrunInt = 0;
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		retrunInt = insuranceInputItemDao.getInsuranceInputItemDataPersonListCnt(paramMap);

		return retrunInt;
	}
	
	/**
	 * 获取添加保险个人输入列表（get Add Insurance Personal Input List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getAddInsurancePersonalInputList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = insuranceInputItemDao.getAddInsurancePersonalInputList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));

		} else {
			retrunList = insuranceInputItemDao.getAddInsurancePersonalInputList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 获取添加保险输入项目（get Add Insurance Personal Input List Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public int getAddInsurancePersonalInputListCnt(HttpServletRequest request) {
		int retrunInt = 0;
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		retrunInt = insuranceInputItemDao.getAddInsurancePersonalInputListCnt(paramMap);

		return retrunInt;
	}
	
	/**
	 * 方修改保险输入项目数据个人信息（update Insurance Input Item Data Person Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateInsuranceInputItemDataPersonInfo(HttpServletRequest request) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int result =0;
		String[] paramData = request.getParameterValues("c1");
		for (int i = 0; i < paramData.length; i++) {
			paramMap.put("TABLE_NAME", "IS_PARAM_DATA");
			paramMap.put("START_MONTH_STR", paramMap.get("START_MONTH"));
			paramMap.put("PARAM_NO", paramData[i]);
			paramMap.put("START_MONTH", request.getParameter("START_MONTH_"+paramData[i]));
			paramMap.put("END_MONTH", request.getParameter("END_MONTH_"+paramData[i]));
			paramMap.put("RETURN_VALUE", request.getParameter("RETURN_VALUE_"+paramData[i]));
			paramMap.put("PARAM_DATA_NO", request.getParameter("PARAM_DATA_NO_"+paramData[i]));
			paramMap.put("CPNY_ID", request.getParameter("CPNY_ID_"+paramData[i]));
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID_"+paramData[i]));
			paramMap.put("REMARK", request.getParameter("REMARK_"+paramData[i]));
			paramMap.put("CREATED_BY", admin.getAdminID());
			// 检测下数据库里是否存在这条记录
			//int count = this.insuranceInputItemDao.checkUpdateInsuranceInputItemDataPersonInfo(paramMap);
			// 如果存在记录则修改 没有记录则添加
			//if (count == 1) {
				//paramMap.put("UPDATED_BY", admin.getAdminID());
				//this.insuranceInputItemDao.updateInsuranceInputItemDataPersonInfo(paramMap);
			//}else{ PARAM_NO:NUMERIC PERSON_ID:VARCHAR
				/*if(paramMap.get("PARAM_NO")!=null&&paramMap.get("PERSON_ID")!=null&&!paramMap.get("PARAM_NO").equals("")&&!paramMap.get("PERSON_ID").equals("")){
					this.insuranceInputItemDao.deleteInsuranceInputItemDataPersonInfo(paramMap);
				}
				this.insuranceInputItemDao.addInsuranceInputItemDataPersonInfo(paramMap);*/
			//}
			//如果同一个人的同一个开始月的数据已经存在，则不允许其在此添加！
			if(this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) != 0){
				result = 0;
			}else{
				if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){//添加成功
					this.insuranceInputItemDao.addInsuranceInputItemDataPersonInfo(paramMap);
					result = 1;
				}else{//修改之后又添加
					this.insuranceInputItemDao.updateInsuranceInputItemDataInfoMonth(paramMap);
					this.insuranceInputItemDao.addInsuranceInputItemDataPersonInfo(paramMap);
					result = 1;
				}
			}
		}
		return result;
	}
	
	
	
	//-------------------------2013-09-01 lufeng---------------------
	/**
	 * 获取保险输入项目参数中允许申请的项目列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getInsuranceInputItemDataForApply(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("APPLY_FLAG", 'Y');
		retrunList = insuranceInputItemDao.getInsuranceInputItemParamList(paramMap);
		
		
		 return retrunList;
		
	}
	
	/**
	 * 获取保险输入项目数据申请的列表通过ParamNo（get Insurance Input Item Data List By ParamNo）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getInsuranceInputApplyDataListByParamNo(HttpServletRequest request,int flag,int flagtow) throws Exception {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		// 取得数据项目信息
		paramMap.put("APPLY_FLAG", 'Y');
		//申请状态 (0:未申请;1申请)
		paramMap.put("APPLY_STATUS", flagtow);
		LinkedHashMap isInputItemInfo = (LinkedHashMap)this.insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(isInputItemInfo.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String distinctField2 = ObjectUtils.toString(isInputItemInfo.get("DISTINCT_FIELD_2ND"));
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		//根据flag的不同查询不同时候的数据：1：已通过；2：已否决；0：未决裁;-1:查询所有状态
		if(paramMap.get("AFFIRM_FLAGA")!=null && !("-1").equals(paramMap.get("AFFIRM_FLAGA")) ){
			paramMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_FLAGA"));
		}else{
			if( flag>-1 ){
				paramMap.put("AFFIRM_FLAG", flag);
			}
		}
		
		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			// 判断DISTINCT_FIELD是否是EMPID 两者返回的列表不同
			if (distinctField.equals("PERSON_ID")) {
				retrunList = insuranceInputItemDao
						.getInsuranceInputApplyDataListDistinctFieldIsEmpid(
								paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
				retrunList = insuranceInputItemDao
						.getInsuranceInputApplyDataListDistinctFieldIsCpnyId(
								paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			}
			//只针对与C01权限
			else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
				paramMap.put("C01_ADMINID", admin.getPersonId());
				retrunList = insuranceInputItemDao
						.getInsuranceInputApplyDataListDistinctFieldIsNotEmpid(
								paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
				retrunList = insuranceInputItemDao
						.getInsuranceInputApplyDataListDistinctFieldIsDeptNo(
								paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			}else {
				retrunList = insuranceInputItemDao
						.getInsuranceInputApplyDataListDistinctFieldIsNotEmpid(
								paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			}
		} else {
			// 判断DISTINCT_FIELD是否是EMPID 两者返回的列表不同
			if (distinctField.equals("PERSON_ID")) {
				retrunList = insuranceInputItemDao.getInsuranceInputApplyDataListDistinctFieldIsEmpid(paramMap);
			}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
				retrunList = insuranceInputItemDao.getInsuranceInputApplyDataListDistinctFieldIsCpnyId(paramMap);
			}
			//只针对与C01权限
			else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
				paramMap.put("C01_ADMINID", admin.getPersonId());
				retrunList = insuranceInputItemDao.getInsuranceInputApplyDataListDistinctFieldIsNotEmpid(paramMap);
			}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
				retrunList = insuranceInputItemDao.getInsuranceInputApplyDataListDistinctFieldIsDeptNo(paramMap);
			}else {
				retrunList = insuranceInputItemDao.getInsuranceInputApplyDataListDistinctFieldIsNotEmpid(paramMap);
			}
		}
		List retrunList1=new ArrayList<Object>();
		 for (int i = 0; i < retrunList.size(); i++) {
				Map m= (LinkedHashMap) retrunList.get(i);
				m.put("accessoryList", this.insuranceInputItemDao.getaccessoryList(m));
				retrunList1.add(m);
		}
		return retrunList1;
	}
	
	/**
	 * 获取保险输入项目数据列表申请的通过ParamNo（get Insurance Input Item Data List By ParamNo）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getInsuranceInputApplyDataListByParamNoCnt(HttpServletRequest request,int flag,int flagtow) {
		int retrunInt = 0;
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		// 取得数据项目信息
		paramMap.put("APPLY_FLAG", 'Y');
		LinkedHashMap paInputItemInfo = (LinkedHashMap)this.insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap);
		//申请状态 (0:未申请;1申请)
		paramMap.put("APPLY_STATUS", flagtow);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paInputItemInfo.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(paInputItemInfo.get("DISTINCT_FIELD_2ND"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		paramMap.put("PA_ADMIN_ID", admin.getAdminID());
		//根据flag的不同查询不同时候的数据：1：已通过；2：已否决；0：未决裁;-1:查询所有状态
		if( flag>-1 ){
			paramMap.put("AFFIRM_FLAG", flag);
		}
		
		if (distinctField.equals("PERSON_ID")) {
			retrunInt = insuranceInputItemDao.getInsuranceInputApplyDataListDistinctFieldIsEmpidCnt(paramMap);
		}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
			retrunInt = insuranceInputItemDao.getInsuranceInputApplyDataListDistinctFieldIsCpnyIdCnt(paramMap);
		}
		//只针对与C01权限
		else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
			paramMap.put("C01_ADMINID", admin.getPersonId());
			retrunInt = insuranceInputItemDao.getInsuranceInputApplyDataListDistinctFieldIsNotEmpidCnt(paramMap);
		}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
			retrunInt = insuranceInputItemDao.getInsuranceInputItemDataListDistinctFieldIsDeptNoCnt(paramMap);
		} else {
			retrunInt = insuranceInputItemDao.getInsuranceInputItemDataListDistinctFieldIsNotEmpidCnt1(paramMap);
		}

		return retrunInt;	
	}
	
	/**
	 * 获取申请的保险输入项目参数信息（get Insurance Input Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public Object getInsuranceInputApplyDataInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnObj = insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap) ;
		
		return returnObj ;
	}
	
	/**
	 * 申请的保险输入项目数据信息（add Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
	public int addInsuranceInputItemDataApply(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int result = 0;
		String person_id = paramMap.get("dwz.person.personId")!=null ? paramMap.get("dwz.person.personId").toString() : "";
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("TABLE_NAME", "IS_PARAM_DATA_APPLY");
		paramMap.put("PERSON_ID", person_id);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("START_MONTH_STR", paramMap.get("START_MONTH"));
		paramMap.put("END_MONTH_STR", paramMap.get("END_MONTH"));
		paramMap.put("END_MONTH_STR", paramMap.get("END_MONTH"));
		paramMap.put("PARAM_DATA_NO", paramMap.get("seach_PARAMDATANO"));
		//申请的时候同样验证正式数据表里的数据
		paramMap.put("IS_PARAM_DATA_APPLY", "IS_PARAM_DATA_APPLY");
		if(this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) != 0){
			result = 2;
		}else{//申请的时候同样验证正式数据表里的数据
			paramMap.put("TABLE_NAME", "IS_PARAM_DATA");
			if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){
				paramMap.put("TABLE_NAME", "IS_PARAM_DATA_APPLY");
				this.insuranceInputItemDao.addInsuranceInputItemDataApply(paramMap);
				result = 0;
			}else{
				//this.insuranceInputItemDao.updateInsuranceInputItemDataApplyMonth(paramMap);
				//this.insuranceInputItemDao.addInsuranceInputItemDataApply(paramMap);
				result = 2;
			}
		}
		return result ;
	}
	
	/**
	 * 申请的保险输入项目数据信息（add Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int addInsuranceInputItemOtherDataApply(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int result = 0;
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("TABLE_NAME", "IS_PARAM_DATA_OTHER_APPLY");
		paramMap.put("START_MONTH_STR", paramMap.get("START_MONTH"));
		paramMap.put("PARAM_DATA_NO", paramMap.get("seach_PARAMDATANO"));
		
		paramMap.put("IS_PARAM_DATA_OTHER_APPLY", "IS_PARAM_DATA_OTHER_APPLY");
		
		//依然验证正式表里的数据
		if(this.paBasicItemDao.checkAddPaBasicItemDataOtherInfoMonth(paramMap) != 0){
			result = 2;
		}else{//依然验证正式表里的数据
			paramMap.put("TABLE_NAME", "IS_PARAM_DATA_OTHER");
			if(this.paBasicItemDao.checkAddPaBasicItemDataOtherInfo(paramMap) == 0){
				paramMap.put("TABLE_NAME", "IS_PARAM_DATA_OTHER_APPLY");
				this.insuranceInputItemDao.addInsuranceInputItemOtherDataApply(paramMap);
				result = 0;
			}else{
				//paramMap.put("TABLE_NAME", "IS_PARAM_DATA_OTHER_APPLY");
				//this.insuranceInputItemDao.updateInsuranceInputItemDataOtherApplyMonth(paramMap);
				//this.insuranceInputItemDao.addInsuranceInputItemOtherDataApply(paramMap);
				result = 2;
			}
		}
		
		return result ;
	}
	
	/**
	 * 通过/否决--保险输入项目申请数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveInsDataApply(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("AFFIRM_ID", admin.getPersonId()!=null?admin.getPersonId().toString():"");
		paramMap.put("CREATED_BY", admin.getPersonId()!=null?admin.getPersonId().toString():"");
		paramMap.put("UPDATED_BY", admin.getPersonId()!=null?admin.getPersonId().toString():"");
		
		LinkedHashMap insItemInfo = new LinkedHashMap();
		insItemInfo = (LinkedHashMap)insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap) ;
		if(insItemInfo!=null && insItemInfo.get("DISTINCT_FIELD")!=null && "PERSON_ID".equals(insItemInfo.get("DISTINCT_FIELD").toString())){
			paramMap.put("TABLE_NAME", "IS_PARAM_DATA_APPLY");
		}else{
			paramMap.put("TABLE_NAME", "IS_PARAM_DATA_OTHER_APPLY");
		}
		try {
			// 封装薪资调整发令数据并处理
			this.insuranceInputItemDao.approveInsDataApply(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量通过/否决--保险输入项目申请数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	public int approveInsDataApplyInBatch(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("AFFIRM_ID", admin.getPersonId()!=null?admin.getPersonId().toString():"");
		paramMap.put("CREATED_BY", admin.getPersonId()!=null?admin.getPersonId().toString():"");
		paramMap.put("UPDATED_BY", admin.getPersonId()!=null?admin.getPersonId().toString():"");
		
		LinkedHashMap insItemInfo = new LinkedHashMap();
		insItemInfo = (LinkedHashMap)insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap) ;
		if(insItemInfo!=null && insItemInfo.get("DISTINCT_FIELD")!=null && "PERSON_ID".equals(insItemInfo.get("DISTINCT_FIELD").toString())){
			paramMap.put("TABLE_NAME", "IS_PARAM_DATA_APPLY");
		}else{
			paramMap.put("TABLE_NAME", "IS_PARAM_DATA_OTHER_APPLY");
		}
		String[] paramData = request.getParameterValues("c1");
		for (int i = 0; i < paramData.length; i++) {	
			paramMap.remove("PARAM_DATA_NO");
			paramMap.put("PARAM_DATA_NO", paramData[i]);
			try {
				// 封装发令数据并处理
				this.insuranceInputItemDao.approveInsDataApply(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 1;
	}
	
	/**
	 * 修改保险输入项目数据信息（update Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateInsDataApply(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String[] paramData = request.getParameterValues("c1");
		LinkedHashMap paInputItemInfo = (LinkedHashMap)this.getInsuranceInputItemParamInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paInputItemInfo.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);

		for (int i = 0; i < paramData.length; i++) {
			
			paramMap.put("PARAM_DATA_NO", paramData[i]);
			paramMap.put("START_MONTH", request.getParameter("START_MONTH_"+paramData[i]));
			paramMap.put("END_MONTH", request.getParameter("END_MONTH_"+paramData[i]));
			paramMap.put("RETURN_VALUE", request.getParameter("RETURN_VALUE_"+paramData[i]));
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("REMARK", request.getParameter("REMARK_"+paramData[i]));
			if(distinctField.equals("PERSON_ID")){
				this.insuranceInputItemDao.updateInsuranceInputItemDataInfo(paramMap) ;
			}else{
				this.insuranceInputItemDao.updateInsuranceInputItemDataInfoOther(paramMap);
			}
			
		}
		return 1 ;
	}
	
	/**
	 * 删除--保险输入项目申请数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteInsDataApply(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		LinkedHashMap insItemInfo = new LinkedHashMap();
		insItemInfo = (LinkedHashMap)insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap) ;
		if(insItemInfo!=null && insItemInfo.get("DISTINCT_FIELD")!=null && "PERSON_ID".equals(insItemInfo.get("DISTINCT_FIELD").toString())){
			paramMap.put("TABLE_NAME", "IS_PARAM_DATA_APPLY");
		}else{
			paramMap.put("TABLE_NAME", "IS_PARAM_DATA_OTHER_APPLY");
		}
		try {
			// 封装薪资调整发令数据并处理
			this.insuranceInputItemDao.deleteInsDataApply(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除--保险输入项目申请数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteInsDataApplyInBatch(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		LinkedHashMap insItemInfo = new LinkedHashMap();
		insItemInfo = (LinkedHashMap)insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap) ;
		if(insItemInfo!=null && insItemInfo.get("DISTINCT_FIELD")!=null && "PERSON_ID".equals(insItemInfo.get("DISTINCT_FIELD").toString())){
			paramMap.put("TABLE_NAME", "IS_PARAM_DATA_APPLY");
		}else{
			paramMap.put("TABLE_NAME", "IS_PARAM_DATA_OTHER_APPLY");
		}
		String[] paramData = request.getParameterValues("c1");
		for (int i = 0; i < paramData.length; i++) {	
			paramMap.remove("PARAM_DATA_NO");
			paramMap.put("PARAM_DATA_NO", paramData[i]);
			try {
				// 封装发令数据并处理
				this.insuranceInputItemDao.deleteInsDataApply(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 1;
	}

	@Override
	public int updateInsuranceApply(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String[] paramData = request.getParameterValues("c1");
		LinkedHashMap paInputItemInfo = (LinkedHashMap)this.getInsuranceInputItemParamInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paInputItemInfo.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		for (int i = 0; i < paramData.length; i++) {
			paramMap.put("PARAM_DATA_NO", paramData[i]);
			if(distinctField.equals("PERSON_ID")){
				this.insuranceInputItemDao.updateInsuranceApply(paramMap) ;
			}else{
				this.insuranceInputItemDao.updateInsuranceApplyOther(paramMap);
			}
			
		}
		return 1 ;
	}

	@Override
	public List getInsuranceInputItemDataForApplyName(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("APPLY_FLAG", 'Y');
		retrunList = insuranceInputItemDao.getInsuranceInputItemDataForApplyName(paramMap);
		return retrunList ;
	}
	
	/**
	 * 根据申请项目查询出相同所在地不同法人的相同的需要申请的项目
	 */
	@Override
	public List getUnifySuitCompanyList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		String DISTINCT_FIELD = StringUtil.checkNull(paramMap.get("DISTINCT_FIELD"));
		String DISTINCT_FIELD2 = StringUtil.checkNull(paramMap.get("DISTINCT_FIELD2"));
		
		if("SOCIAL_SECURITY_AREA".equals(DISTINCT_FIELD)){
			//interLanguage 国际化  interCpnyID
			retrunList = insuranceInputItemDao.getUnifySuitCompanyList(paramMap);
		}else if("SOCIAL_SECURITY_AREA_NAME".equals(DISTINCT_FIELD)){
			//interLanguage 国际化  interCpnyID
			retrunList = insuranceInputItemDao.getUnifySuitCompanyList2(paramMap);
		}else if("SOCIAL_SECURITY_AREA".equals(DISTINCT_FIELD2)){
			//interLanguage 国际化  interCpnyID
			retrunList = insuranceInputItemDao.getUnifySuitCompanyList3(paramMap);
		}else if("SOCIAL_SECURITY_AREA_NAME".equals(DISTINCT_FIELD2)){
			//interLanguage 国际化  interCpnyID
			retrunList = insuranceInputItemDao.getUnifySuitCompanyList4(paramMap);
		}
		
		return retrunList;
	}
	
	/**
	 * 提前获取到申请信息的编号 用关联到附件字段
	 */
	@Override
	public String getNextparamDataNo() {
		
		return insuranceInputItemDao.getNextparamDataNo();
	}
	
	@Override
	public int insertAccessory(LinkedHashMap map) {
		LinkedHashMap paramMap =new LinkedHashMap();
		paramMap.put("PARAM_DATA_NO", map.get("PARAMDATANO"));
		paramMap.put("ACCESSORY_SITE", map.get("fileName"));
		paramMap.put("ORIGINAL_NAME", map.get("ORIGINAL_NAME"));
		paramMap.put("CREATED_BY",map.get("CREATED_BY"));
		try {
			this.insuranceInputItemDao.insertAccessory(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	@Override
	public int getUserRolesGroupCnt(HttpServletRequest request) {
		int returnInt=0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getAdminID());
		try {
			returnInt=this.insuranceInputItemDao.getUserRolesGroupCnt(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return returnInt;
	}

	/**
	 * 查找输入项目的NO和名称
	 * 只查询DISTINCT_FIELD='PERSON_ID' 并且  apply_flag 是不等于Y的数据
	 */
	
	@Override
	public List getItemNameList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
			retrunList = insuranceInputItemDao.getItemNameList(paramMap);
		return retrunList ;
	}

	@Override
	public List getItemBatchImportList(HttpServletRequest request) {
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("PA_ADMIN_ID", admin.getAdminID());
		List retrunList = new ArrayList();
		
		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = insuranceInputItemDao.getItemBatchImportList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));

		} else {
			retrunList = insuranceInputItemDao.getItemBatchImportList(paramMap);
		}
		return retrunList;
	}

	@Override
	public int getItemBatchImportListoCnt(HttpServletRequest request) {
		int returnInt=0;
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("PA_ADMIN_ID", admin.getAdminID());
		try {
			returnInt=this.insuranceInputItemDao.getItemBatchImportListoCnt(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return returnInt;
	}


	/**
	 * PA 工资输入项目
	 * 查找输入项目的NO和名称
	 */
	@Override
	public List getItemNameListPa(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("amp;ITEM_DISTINGUISH")!=null){
			paramMap.put("ITEM_DISTINGUISH",paramMap.get("amp;ITEM_DISTINGUISH"));
		}
		
		if(paramMap.get("ITEM_DISTINGUISH").toString().equals("507")){
			paramMap.put("TNAME", "PA_BASIC_ITEM_PARAM");//基础项目数据
			paramMap.put("PARAMETER1", "ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", " AND T.ACTIVITY=1 ");
		} else{
			paramMap.put("TNAME", "PA_PARAM_ITEM_PARAM");//输入项目数据
			paramMap.put("PARAMETER1", "PARAM_ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", "AND T.ACTIVITY=1");
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
			retrunList = insuranceInputItemDao.getItemNameListPa(paramMap);
		return retrunList ;
	}

	
	/**
	 * PA 工资输入项目带计算项目
	 * 查找输入项目的NO和名称
	 */
	@Override
	public List getItemNameListPa2(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("amp;ITEM_DISTINGUISH")!=null){
			paramMap.put("ITEM_DISTINGUISH",paramMap.get("amp;ITEM_DISTINGUISH"));
		}
		
		if(paramMap.get("ITEM_DISTINGUISH").toString().equals("507")){
			paramMap.put("TNAME", "PA_BASIC_ITEM_PARAM");//基础项目数据
			paramMap.put("PARAMETER1", "ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", " AND T.ACTIVITY=1 ");
		} else if (paramMap.get("ITEM_DISTINGUISH").toString().equals("2372")){
			paramMap.put("TNAME", "PA_ITEM_PARAM");//计算项目
			paramMap.put("PARAMETER1", "ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", " AND T.ACTIVITY=1 ");
			
		}
		else{
			paramMap.put("TNAME", "PA_PARAM_ITEM_PARAM");//输入项目数据
			paramMap.put("PARAMETER1", "PARAM_ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", "AND T.ACTIVITY=1");
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
			retrunList = insuranceInputItemDao.getItemNameListPa2(paramMap);
		return retrunList ;
	}

	
	/**
	 * 查找福利地区
	 */
	@Override
	public List getWelfareArea(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = insuranceInputItemDao.getWelfareArea(paramMap);
		return retrunList ;
	}

	@Override
	public List getItemBatchImportListPa(HttpServletRequest request) {
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		Calendar c = Calendar.getInstance();
		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH)+1);
		String paYear=request.getParameter("seach_paYear") == null ? year : request.getParameter("seach_paYear");
		//String paMonth=request.getParameter("seach_paMonth") == null ? month : request.getParameter("seach_paMonth");
		//之前的取值方式会出现BUG,导入、添加、删除时，request.getParameter("seach_paMonth")的参数值是null
		String paMonth="";
		if (request.getParameter("seach_paMonth") != null 
				&& !"".equals(request.getParameter("seach_paMonth"))
				&& !"null".equals(request.getParameter("seach_paMonth"))) {
			paMonth=request.getParameter("seach_paMonth");
		}else {
			paMonth = month;
		}
		if(paYear != null && paMonth != null&&!("").equals(paYear) &&!("").equals(paMonth)){
			paramMap.put("PA_MONTH", paYear+paMonth);
		}
		List retrunList = new ArrayList();
		if(paramMap.get("amp;ITEM_DISTINGUISH")!=null){
			paramMap.put("ITEM_DISTINGUISH",paramMap.get("amp;ITEM_DISTINGUISH"));
		}
		if(paramMap.get("ITEM_DISTINGUISH").toString().equals("507")){//507 基础项目basis
				paramMap.put("CPNY_ID", admin.getCpnyId());
			if (UiUtil.getPageNum(request) > 0) {
				
				retrunList = insuranceInputItemDao.getItemBatchImportListPaBasis(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
				
			} else {
				retrunList = insuranceInputItemDao.getItemBatchImportListPaBasis(paramMap);
			}
		}
		if(paramMap.get("ITEM_DISTINGUISH").toString().equals("508")){//508输入项目
				paramMap.put("CPNY_ID", admin.getCpnyId());
			// 是否分页
			if (UiUtil.getPageNum(request) > 0) {
				retrunList = insuranceInputItemDao.getItemBatchImportListPa(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));

			} else {
				retrunList = insuranceInputItemDao.getItemBatchImportListPa(paramMap);
			}
		}
		if(!paramMap.get("ITEM_DISTINGUISH").toString().equals("507")&&!paramMap.get("ITEM_DISTINGUISH").toString().equals("508")){//507 基础项目basis
			paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0) {
			
			retrunList = insuranceInputItemDao.getItemBatchImportListPaBasisAndParam(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			
		} else {
			retrunList = insuranceInputItemDao.getItemBatchImportListPaBasisAndParam(paramMap);
		}
	}
		
		return retrunList;
	}

	@Override
	public int getItemBatchImportListoCntPa(HttpServletRequest request) {
		int returnInt=0;
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		Calendar c = Calendar.getInstance();
		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH)+1);
		String paYear=request.getParameter("seach_paYear") == null ? year : request.getParameter("seach_paYear");
		//String paMonth=request.getParameter("seach_paMonth") == null ? month : request.getParameter("seach_paMonth");
		//之前的取值方式会出现BUG,导入、添加、删除时，request.getParameter("seach_paMonth")的参数值是null
		String paMonth="";
		if (request.getParameter("seach_paMonth") != null 
				&& !"".equals(request.getParameter("seach_paMonth"))
				&& !"null".equals(request.getParameter("seach_paMonth"))) {
			paMonth=request.getParameter("seach_paMonth");
		}else {
			paMonth = month;
		}
		if(paYear != null && paMonth != null&&!("").equals(paYear) &&!("").equals(paMonth)){
			paramMap.put("PA_MONTH", paYear+paMonth);
		}
		if(paramMap.get("amp;ITEM_DISTINGUISH")!=null){
			paramMap.put("ITEM_DISTINGUISH",paramMap.get("amp;ITEM_DISTINGUISH"));
		}
		try {
			paramMap.put("CPNY_ID", admin.getCpnyId());//无故丢失
			if(paramMap.get("ITEM_DISTINGUISH").toString().equals("507")){//507 基础项目basisBasis
				
				returnInt=this.insuranceInputItemDao.getItemBatchImportListoCntPaBasis(paramMap);
			}
			if(paramMap.get("ITEM_DISTINGUISH").toString().equals("508")){//507 输入项目basis
				returnInt=this.insuranceInputItemDao.getItemBatchImportListoCntPa(paramMap);
			}
			if(!paramMap.get("ITEM_DISTINGUISH").toString().equals("507")&&!paramMap.get("ITEM_DISTINGUISH").toString().equals("508")){//所有项目
				returnInt=this.insuranceInputItemDao.getItemBatchImportListoCntPa(paramMap)
						+this.insuranceInputItemDao.getItemBatchImportListoCntPaBasis(paramMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return returnInt;
	}

	/* 
	* Title: updateItemBatchData
	* Description:提交修改的批量导入数据
	* @author 孙鹏  
	* @date 2015年4月21日 下午3:56:52  
	* @param request
	* @return 
	* @see com.ait.pa.service.insurance.InsuranceInputItemSer#updateItemBatchData(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public String updateItemBatchData(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int i=0;
		//String ret="";
		
		if(paramMap.get("ITEM_DISTINGUISH").toString().equals("507")){//507 基础项目basis
		i=	this.insuranceInputItemDao.updateItemBatchDataForBasic(paramMap);
		}else if(paramMap.get("ITEM_DISTINGUISH").toString().equals("508")){
		i=this.insuranceInputItemDao.updateItemBatchDataForParam(paramMap);
		}
		
		return i==1?"OK":"NG";
	}
	@Override
	public List getAddInsurancePersonalInputItemList(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("ACTIVITY", "1");
		return insuranceInputItemDao.getAddInsurancePersonalInputItemList(paramMap);
	}

	@Override
	public List getInsuranceInputItemPersonTempAllList(
			HttpServletRequest request) throws Exception {
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		return this.insuranceInputItemDao.getInsuranceInputItemPersonTempAllList(paramMap);
	}

	@Override
	public int insertOrUpdateInsuranceInputItemData(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int result =0;
		String[] paramData = request.getParameterValues("h1");
		if(paramData == null){
			result = 1;
		}else{
			for (int i = 0; i < paramData.length; i++) {
				paramMap.put("TABLE_NAME", "IS_PARAM_DATA");
				paramMap.put("PARAM_NO", paramData[i]);
				paramMap.put("CPNY_ID", request.getParameter("INS_CPNY_ID_"+paramData[i]));
				paramMap.put("START_MONTH", request.getParameter("INS_START_MONTH_"+paramData[i]));
				paramMap.put("START_MONTH_STR", request.getParameter("INS_START_MONTH_"+paramData[i]));
				paramMap.put("END_MONTH", request.getParameter("INS_END_MONTH_"+paramData[i]));
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("RETURN_VALUE", request.getParameter("INS_RETURN_VALUE_"+paramData[i]));
				paramMap.put("PERSON_ID", request.getParameter("INS_PERSON_ID_"+paramData[i]));
				paramMap.put("REMARK", request.getParameter("INS_REMARK_"+paramData[i]));
				paramMap.put("PARAM_DATA_NO", request.getParameter("INS_PARAM_DATA_NO_"+paramData[i]));
				//paramMap.put("NOT_PARAM_DATA_NO", request.getParameter("INS_PARAM_DATA_NO_"+paramData[i]));
				
				if(!"".equals(paramMap.get("START_MONTH"))&&!"".equals(paramMap.get("RETURN_VALUE"))){
					if("INSERT".equals(request.getParameter("INS_FLAG_"+paramData[i]))){
						if(this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) != 0){
							result = 2;
						}else{
							if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){
								this.insuranceInputItemDao.addInsuranceInputItemDataInfo(paramMap);
								result = 1;
							}else{
								this.insuranceInputItemDao.updateInsuranceInputItemDataInfoMonth(paramMap);
								this.insuranceInputItemDao.addInsuranceInputItemDataInfo(paramMap);
								result = 1;
							}
						}
					}else{
						String startMonth = StringUtil.checkNull(paramMap.get("START_MONTH"));
						String startMonthOld = StringUtil.checkNull(request.getParameter("INS_START_MONTH_OLD_"+paramData[i]));
						if(startMonth.equals(startMonthOld)){
							this.insuranceInputItemDao.updateInsuranceInputItemDataInfo(paramMap) ;
							result = 1;
						}else{
							if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){
								this.insuranceInputItemDao.updateInsuranceInputItemDataInfo(paramMap) ;
								result = 1;
							}else{
								this.insuranceInputItemDao.updateInsuranceInputItemDataInfoMonth(paramMap);
								this.insuranceInputItemDao.addInsuranceInputItemDataInfo(paramMap) ;
								result = 1;
							}
						}

	//					if(!"".equals(paramMap.get("START_MONTH"))&&!"".equals(paramMap.get("RETURN_VALUE"))
	//							&&this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 1){
	//						if(this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) == 0){
	//							this.insuranceInputItemDao.updateInsuranceInputItemDataInfoMonth(paramMap);
	//							this.insuranceInputItemDao.addInsuranceInputItemDataPersonInfo(paramMap);
	//							result=1;
	//						}else{
	//							this.insuranceInputItemDao.updateInsuranceInputItemDataValue(paramMap);
	//							result=1;
	//						}
	//					}else{
	//						result=1;
	//						continue;
	//					}
					}
				}else{
					result = 1;
				}
			}
		}
		return result;
	}

	@Override
	public int addApplyInform(HttpServletRequest request) {
		
		int result =0;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY",admin.getAdminID());
		result=insuranceInputItemDao.addApplyInform(paramMap);
		return result;
	}
	
	@Override
	public int getViewInsureSelfCnt(HttpServletRequest request){
		int result =0;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("CREATED_BY",admin.getAdminID());
		result=insuranceInputItemDao.getViewInsureSelfCnt(paramMap);
		return result;
	}
	/**
	 * 保险查看（个人别）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-30 下午04:00:04 
	* @version V1.0
	 */
	@Override
	public List getViewInsureSelfList(HttpServletRequest request) {
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("ADMINID", admin.getPersonId());
		List retrunList = new ArrayList();
		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = insuranceInputItemDao.getViewInsureSelfList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));

		} else {
			retrunList = insuranceInputItemDao.getViewInsureSelfList(paramMap);
		}
		return retrunList;
	}

	@Override
	public int getViewInsureDeptCnt(HttpServletRequest request){
		int result =0;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("CREATED_BY",admin.getAdminID());
		result=insuranceInputItemDao.getViewInsureDeptCnt(paramMap);
		return result;
	}
	/**
	 * 保险查看（公司别）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-30 下午04:00:25 
	* @version V1.0
	 */
	@Override
	public List getViewInsureDeptList(HttpServletRequest request) {
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		 paramMap.put("ADMINID", admin.getPersonId());
		List retrunList = new ArrayList();
		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = insuranceInputItemDao.getViewInsureDeptList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));

		} else {
			retrunList = insuranceInputItemDao.getViewInsureDeptList(paramMap);
		}
		return retrunList;
	}
	
	/*------------------------------------------------------------------*/
	@Override
	public List getImportExcelTempISParamDataList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = insuranceInputItemDao.getImportExcelTempISParamDataList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = insuranceInputItemDao.getImportExcelTempISParamDataList(paramMap) ;
		}
		return retrunList;
	}

	@Override
	public int getImportExcelTempISParamDataListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = insuranceInputItemDao.getImportExcelTempISParamDataListCnt(paramMap) ;
		return retrunInt ;
	}

	@Override
	public int getImportExcelTempISParamDataListErrCnt(
			HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = insuranceInputItemDao.getImportExcelTempISParamDataListErrCnt(paramMap) ;
		return retrunInt ;
	}
	
	public String importISParamDataExcelExcel(HttpServletRequest request){
		String retrunInt = "0" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = insuranceInputItemDao.importISParamDataExcelExcel(paramMap) ;
		return retrunInt;
	}

	/* 
	* Title: getItemDataTemp
	* Description:获取临时表的数据
	* @author 孙鹏  
	* @date 2015年2月27日 上午10:26:02  
	* @param request
	* @return 
	* @see com.ait.pa.service.insurance.InsuranceInputItemSer#getItemDataTemp(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public List getItemBatchImportDataTemp(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		LinkedHashMap paramMap =  ObjectBindUtil.getRequestParamData(request, "seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSONID",admin.getPersonId());
		if(paramMap.get("amp;ITEM_DISTINGUISH")!=null){
			paramMap.put("ITEM_DISTINGUISH",paramMap.get("amp;ITEM_DISTINGUISH"));
		}
		if(paramMap.get("ITEM_DISTINGUISH").toString().equals("507")){
			paramMap.put("TNAME", "PA_BASIC_ITEM_PARAM");//基础项目数据
			paramMap.put("TNAMETEMP", "PA_BASIC_DATA_TEMP");
			paramMap.put("PARAMETER1", "ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", " AND T.ACTIVITY=1 ");
		} else{
			paramMap.put("TNAME", "PA_PARAM_ITEM_PARAM");//输入项目数据
			paramMap.put("TNAMETEMP", "PA_PARAM_DATA_TEMP");
			paramMap.put("PARAMETER1", "PARAM_ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", "AND T.ACTIVITY=1");
		}
		if(paramMap.get("ITEM_DISTINGUISH").toString().equals("507")){//507 基础项目basis
				paramMap.put("CPNY_ID", admin.getCpnyId());
			if (UiUtil.getPageNum(request) > 0) {

				retrunList = insuranceInputItemDao.getItemBatchImportDataTemp(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
			} else {
				retrunList = insuranceInputItemDao.getItemBatchImportDataTemp(paramMap) ;
				
			}
		}
		if(paramMap.get("ITEM_DISTINGUISH").toString().equals("508")){//508输入项目
				paramMap.put("CPNY_ID", admin.getCpnyId());
			// 是否分页
			if (UiUtil.getPageNum(request) > 0) {
				retrunList = insuranceInputItemDao.getItemBatchImportDataTemp(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));

			} else {
				retrunList = insuranceInputItemDao.getItemBatchImportDataTemp(paramMap);
			}
		}
		
		return retrunList;
	}
	/* 
	* Title: deleteErrorOldItemBatchData
	* Description:删除旧的错误数据
	* @author 孙鹏  
	* @date 2015年4月23日 上午9:44:29  
	* @param request 
	* @see com.ait.pa.service.insurance.InsuranceInputItemSer#deleteErrorOldItemBatchData(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public void deleteErrorOldItemBatchData(HttpServletRequest request,String ITEM_DISTINGUISH) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		LinkedHashMap paramMap =  ObjectBindUtil.getRequestParamData(request, "seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSONID",admin.getPersonId());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		if(paramMap.get("amp;ITEM_DISTINGUISH")!=null){
			paramMap.put("ITEM_DISTINGUISH",paramMap.get("amp;ITEM_DISTINGUISH"));
		}
		if(ITEM_DISTINGUISH.equals("507")){
			paramMap.put("TNAME", "PA_BASIC_ITEM_PARAM");//基础项目数据
			paramMap.put("TNAMETEMP", "PA_BASIC_DATA_TEMP");
			paramMap.put("PARAMETER1", "ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", " AND T.ACTIVITY=1 ");
		} else{
			paramMap.put("TNAME", "PA_PARAM_ITEM_PARAM");//输入项目数据
			paramMap.put("TNAMETEMP", "PA_PARAM_DATA_TEMP");
			paramMap.put("PARAMETER1", "PARAM_ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", "AND T.ACTIVITY=1");
		}
		
		insuranceInputItemDao.deleteErrorOldItemBatchData(paramMap) ;
		
		
		
	}
	/* 
	* Title: getItemDataTempCnt
	* Description:获取临时表数据的数量
	* @author 孙鹏  
	* @date 2015年2月27日 上午10:26:16  
	* @param request
	* @return 
	* @see com.ait.pa.service.insurance.InsuranceInputItemSer#getItemDataTempCnt(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public int getItemBatchImportDataTempCnt(HttpServletRequest request) {
		
		int returnint=0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSONID",admin.getPersonId());
		if(paramMap.get("amp;ITEM_DISTINGUISH")!=null){
			paramMap.put("ITEM_DISTINGUISH",paramMap.get("amp;ITEM_DISTINGUISH"));
		}
		if(paramMap.get("ITEM_DISTINGUISH").toString().equals("507")){
			paramMap.put("TNAME", "PA_BASIC_ITEM_PARAM");//基础项目数据
			paramMap.put("TNAMETEMP", "PA_BASIC_DATA_TEMP");
			paramMap.put("PARAMETER1", "ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", " AND T.ACTIVITY=1 ");
		} else{
			paramMap.put("TNAME", "PA_PARAM_ITEM_PARAM");//输入项目数据
			paramMap.put("TNAMETEMP", "PA_PARAM_DATA_TEMP");
			paramMap.put("PARAMETER1", "PARAM_ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", "AND T.ACTIVITY=1");
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		returnint = insuranceInputItemDao.getItemBatchImportDataTempCnt(paramMap) ;
		return returnint;
	}

	/* 
	* Title: getItemDataTempCnt
	* Description:获取临时表数据错误的数量
	* @author 孙鹏  
	* @date 2015年2月27日 上午10:26:16  
	* @param request
	* @return 
	* @see com.ait.pa.service.insurance.InsuranceInputItemSer#getItemDataTempCnt(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public int getItemBatchImportDataTempErrorCnt(HttpServletRequest request) {
		
		int returnint=0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSONID",admin.getPersonId());
		if(paramMap.get("amp;ITEM_DISTINGUISH")!=null){
			paramMap.put("ITEM_DISTINGUISH",paramMap.get("amp;ITEM_DISTINGUISH"));
		}
		if(paramMap.get("ITEM_DISTINGUISH").toString().equals("507")){
			paramMap.put("TNAME", "PA_BASIC_ITEM_PARAM");//基础项目数据
			paramMap.put("TNAMETEMP", "PA_BASIC_DATA_TEMP");
			paramMap.put("PARAMETER1", "ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", " AND T.ACTIVITY=1 ");
		} else{
			paramMap.put("TNAME", "PA_PARAM_ITEM_PARAM");//输入项目数据
			paramMap.put("TNAMETEMP", "PA_PARAM_DATA_TEMP");
			paramMap.put("PARAMETER1", "PARAM_ITEM_NO");//参数1 对应的syCode的是那个字段
			paramMap.put("PARAMETER2", "AND T.ACTIVITY=1");
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		returnint = insuranceInputItemDao.getItemBatchImportDataTempErrorCnt(paramMap) ;
		return returnint;
	}
	/* 
	* Title: submitItemBatchData
	* Description:临时数据的提交方法
	* @author 孙鹏  
	* @date 2015年3月2日 上午9:52:30  
	* @param request
	* @return 
	* @see com.ait.pa.service.insurance.InsuranceInputItemSer#submitItemBatchData(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public String submitItemBatchData(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String type = request.getParameter("accrual");
		if(paramMap.get("ITEM_DISTINGUISH").equals("507")){
			paramMap.put("PR_NAME", "PKG_WAGEBASE_EXCEL_TEMP.PR_IMPORT_BASICITEM_DATA");
		}else if(paramMap.get("ITEM_DISTINGUISH").equals("508")){
			paramMap.put("PR_NAME", "PKG_WAGEBASE_EXCEL_TEMP.PR_IMPORT_PAPARAM_DATA");
		}
//		}else if (type.equals("evsInfo")) {
//			paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.pr_import_temp_evsInfo_data");
//		}else if (type.equals("trade")) {
//			paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_TRADEU_DATA");
//		}else if (type.equals("training")) {
//			paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_TRAINING_DATA");
//		}else if (type.equals("baseEmpInfo")){
//			paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_BASEEMP_DATA");
//		}else if (type.equals("product")){
//			paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_PRODUCT_DATA");
//		}else if(type.equals("qual")){
//			paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_QUAL_DATA");
//		}
		try {
			return this.insuranceInputItemDao.submitItemBatchData(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		
		}

	}

	/* 
	* Title: delExceplImportLine
	* Description:删除导入临时表的错误数据
	* @author 孙鹏  
	* @date 2015年3月30日 下午4:24:16  
	* @param request
	* @return 
	* @see com.ait.pa.service.insurance.InsuranceInputItemSer#delExceplImportLine(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public boolean delExceplImportLine(HttpServletRequest request) {
	
			Map paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("PERSONID",admin.getPersonId());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			if(paramMap.get("ITEM_DISTINGUISH").toString().equals("507")){
				paramMap.put("TNAME", "PA_BASIC_ITEM_PARAM");//基础项目数据
				paramMap.put("TNAMETEMP", "PA_BASIC_DATA_TEMP");
				paramMap.put("PARAMETER1", "ITEM_NO");//参数1 对应的syCode的是那个字段
				paramMap.put("PARAMETER2", " AND T.ACTIVITY=1 ");
			} else{
				paramMap.put("TNAME", "PA_PARAM_ITEM_PARAM");//输入项目数据
				paramMap.put("TNAMETEMP", "PA_PARAM_DATA_TEMP");
				paramMap.put("PARAMETER1", "PARAM_ITEM_NO");//参数1 对应的syCode的是那个字段
				paramMap.put("PARAMETER2", "AND T.ACTIVITY=1");
			}
			return this.insuranceInputItemDao.delExceplImportLine(paramMap);
	
		
	}
}
