package com.ait.pa.service.imp.wagebase;

import java.sql.SQLException;
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
import com.ait.pa.dao.PaBasicItemDao;
import com.ait.pa.service.wagebase.PaBasicItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaBasicItemSerImp.java
 * @Description:
 * @Create date: 2012-2-7 下午07:50:50
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class PaBasicItemSerImp implements PaBasicItemSer {

	Logger logger = Logger.getLogger(PaBasicItemSerImp.class);
	
	@Autowired
	private PaBasicItemDao paBasicItemDao;
	
	/**
	 * 获取工资基础项目信息（get Pa Basic Item Info）
	 * @param parameterObject
	 * @return Object
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Object getPaBasicItemInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.remove("sortname") ;
		
		returnObj = paBasicItemDao.getPaBasicItemInfo(paramMap) ;
		
		return returnObj ;
	}
	
	/**
	 * 获取工资基础项目数据信息（get Pa Basic Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getPaBasicItemDataInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		returnObj = paBasicItemDao.getPaBasicItemDataInfo(paramMap) ;
		
		return returnObj ;
	}
	
	/**
	 * 获取工资基础项目（get Pa Basic Item List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("BASIC_ITEM_PARAM", request.getAttribute("BASIC_ITEM_PARAM"));
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				paBasicItemDao.getPaBasicItemList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paBasicItemDao.getPaBasicItemList(paramMap) ;
		}
		
		return retrunList ;
		
	}
	
	/**
	 * 获取工资基础项目个数（get Pa Basic Item Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPaBasicItemCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		retrunInt = paBasicItemDao.getPaBasicItemCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	/**
	 * 获取页面参数（set Get Pa Basic Item Param）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private LinkedHashMap setGetPaBasicItemParam(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return paramMap ;
	}
	
	/**
	 * 验证添加工资基础项目信息（check Add Pa Basic Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddPaBasicItemInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ACTIVITY", 1);
		return this.paBasicItemDao.checkAddPaBasicItemInfo(paramMap) ;
		
	}

	/**
	 * 添加工资基础项目信息（add Pa Basic Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addPaBasicItemInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
	    String ACTIVITY = request.getParameter("ACTIVITY");
		if(ACTIVITY != null && !"".equals(ACTIVITY)){
			paramMap.put("ACTIVITY", ACTIVITY);
		}
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		try {
			this.paBasicItemDao.addPaBasicItemInfo(paramMap) ;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;	
	}
	
	public int addPaBasicItemInfo(HttpServletRequest request,String item_no){
		String personId = request.getParameter("personId");
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getAdminID()) ;
		}else{
			paramMap.put("CREATED_BY", personId) ;
		}
		paramMap.put("ACTIVITY", 1);
		paramMap.put("ITEM_NO", item_no) ;
		try {
			this.paBasicItemDao.addPaBasicItemInfoAffirm(paramMap) ;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;	
	}
	
	/**
	 * 修改工资基础项目信息（update Pa Basic Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaBasicItemInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		
		  try {
				
			  this.paBasicItemDao.updatePaBasicItemInfo(paramMap) ;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
			return 1;
		
	}
	
	/**
	 * 验证删除工资基础项目信息（check Delete Pa Basic Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeletePaBasicItemInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("TABLE_NAME", "PA_SUMMARY");
		
		return this.paBasicItemDao.checkDeletePaBasicItemInfo(paramMap) ;
		
	}
	
	/**
	 * 删除工资基础项目信息（delete Pa Basic Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaBasicItemInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		try{
			this.paBasicItemDao.deletePaBasicItemInfo(paramMap) ;
		}
		catch(Exception e){
			e.printStackTrace() ;
			return 0;
		}
		
		return 1;
		
	}
	
	/**
	 * 获取工资基础项目数据集合（get Pa Basic Item Data List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		// 取得数据项目信息
		LinkedHashMap paBasicItemDataInfo = (LinkedHashMap)this.paBasicItemDao.getPaBasicItemDataInfo(paramMap) ;
		
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paBasicItemDataInfo
				.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String distinctField2 = ObjectUtils.toString(paBasicItemDataInfo
				.get("DISTINCT_FIELD_2ND"));
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		Calendar c = Calendar.getInstance();
		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH)+1);
		String paBasicYear=request.getParameter("seach_paBasicYear") == null ? year : request.getParameter("seach_paBasicYear");
		String paBasicMonth=request.getParameter("seach_paBasicMonth") == null ? month : request.getParameter("seach_paBasicMonth");
		if(paBasicYear != null && paBasicMonth != null &&!("").equals(paBasicYear) &&!("").equals(paBasicMonth)){
			paramMap.put("PA_MONTH", paBasicYear+"-"+paBasicMonth+"-01");
		}
		/*if(paBasicYear == null && paBasicMonth ==null){
			paramMap.put("PA_MONTH", new SimpleDateFormat("yyyy-MM").format(new Date())+"-01");
		}*/
		
		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			// 判断DISTINCT_FIELD是否是EMPID 两者返回的列表不同
			if (distinctField.equals("PERSON_ID")) {
				retrunList = paBasicItemDao
						.getPaBasicItemDataListDistinctFieldIsEmpid(
								paramMap, UiUtil.getPageNum(request), UiUtil
										.getNumPerPage(request));
			}else if(distinctField.equals("POSITION_NO") && distinctField2.equals("")){
				retrunList = paBasicItemDao
				.getPaBasicItemDataListDistinctFieldIsPositionNo(
						paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage(request));
			}else if(distinctField.equals("PAY_AREA_NO") && distinctField2.equals("")){
				retrunList = paBasicItemDao
				.getPaBasicItemDataListDistinctFieldIsDeptArea(
						paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage(request));
			}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
				retrunList = paBasicItemDao
				.getPaBasicItemDataListDistinctFieldIsCpnyId(
						paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage(request));
			}//只针对与C01权限
			else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
				paramMap.put("C01_ADMINID", admin.getPersonId());
				retrunList = paBasicItemDao
				.getPaBasicItemDataListDistinctFieldIsNotEmpid(
						paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage(request));
			}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
				retrunList = paBasicItemDao
				.getPaBasicItemDataListDistinctFieldIsDeptNo(
						paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage(request));
			}else {
				retrunList = paBasicItemDao
						.getPaBasicItemDataListDistinctFieldIsNotEmpid(
								paramMap, UiUtil.getPageNum(request), UiUtil
										.getNumPerPage(request));
			}
		} else {
			// 判断DISTINCT_FIELD是否是EMPID 两者返回的列表不同
			if (distinctField.equals("PERSON_ID")) {
				retrunList = paBasicItemDao
						.getPaBasicItemDataListDistinctFieldIsEmpid(paramMap);
			}else if(distinctField.equals("POSITION_NO") && distinctField2.equals("")){
				retrunList = paBasicItemDao
				.getPaBasicItemDataListDistinctFieldIsPositionNo(paramMap);
			}else if(distinctField.equals("PAY_AREA_NO") && distinctField2.equals("")){
				retrunList = paBasicItemDao
				.getPaBasicItemDataListDistinctFieldIsDeptArea(paramMap);
			}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
				retrunList = paBasicItemDao
				.getPaBasicItemDataListDistinctFieldIsCpnyId(paramMap);
			}
			//只针对与C01权限
			else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
				retrunList = paBasicItemDao
				.getPaBasicItemDataListDistinctFieldIsNotEmpid(paramMap);
			}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
				retrunList = paBasicItemDao
						.getPaBasicItemDataListDistinctFieldIsDeptNo(paramMap);
			}else {
				retrunList = paBasicItemDao
						.getPaBasicItemDataListDistinctFieldIsNotEmpid(paramMap);
			}
		}
		
		return retrunList ;
	}
	
	/**
	 * 获取工资基础项目数据集合个数（get Pa Basic Item Data List Count）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPaBasicItemDataListCnt(HttpServletRequest request) {
		int retrunInt = 0;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 取得数据项目信息
		LinkedHashMap paBasicItemDataInfo = (LinkedHashMap)this.paBasicItemDao.getPaBasicItemDataInfo(paramMap);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paBasicItemDataInfo
				.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String distinctField2 = ObjectUtils.toString(paBasicItemDataInfo
				.get("DISTINCT_FIELD_2ND"));
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_ADMIN_ID", admin.getAdminID());
		Calendar c = Calendar.getInstance();
		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH)+1);
		String paBasicYear=request.getParameter("seach_paBasicYear") == null ? year : request.getParameter("seach_paBasicYear");
		String paBasicMonth=request.getParameter("seach_paBasicMonth") == null ? month : request.getParameter("seach_paBasicMonth");
		if(paBasicYear != null && paBasicMonth != null &&!("").equals(paBasicYear) &&!("").equals(paBasicMonth)){
			paramMap.put("PA_MONTH", paBasicYear+"-"+paBasicMonth+"-01");
		}
		/*if(paBasicYear == null && paBasicMonth ==null){
			paramMap.put("PA_MONTH", new SimpleDateFormat("yyyy-MM").format(new Date())+"-01");
		}*/
		
		if (distinctField.equals("PERSON_ID")) {
			retrunInt = paBasicItemDao
					.getPaBasicItemDataListDistinctFieldIsEmpidCnt(paramMap);
		}else if(distinctField.equals("POSITION_NO") && distinctField2.equals("")){
			retrunInt = paBasicItemDao
	        .getPaBasicItemDataListDistinctFieldIsPositionNoCnt(paramMap);
		}else if(distinctField.equals("PAY_AREA_NO") && distinctField2.equals("")){
			retrunInt = paBasicItemDao
			        .getPaBasicItemDataListDistinctFieldIsDeptAreaCnt(paramMap);
		}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
			retrunInt = paBasicItemDao
	        .getPaBasicItemDataListDistinctFieldIsCpnyIdCnt(paramMap);
		}
		//只针对与C01权限
		else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
			paramMap.put("C01_ADMINID", admin.getPersonId());
			retrunInt = paBasicItemDao
			.getPaBasicItemDataListDistinctFieldIsNotEmpidCnt(paramMap);
		}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
			retrunInt = paBasicItemDao
			        .getPaBasicItemDataListDistinctFieldIsDeptNoCnt(paramMap);
		}else {
			retrunInt = paBasicItemDao
					.getPaBasicItemDataListDistinctFieldIsNotEmpidCnt(paramMap);
		}

		return retrunInt;	
	}
	/**
	 * 修改工资基础项目数据信息（update Pa Basic Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updatePaBasicItemDataInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		LinkedHashMap paInputItemInfo = (LinkedHashMap)this.getPaBasicItemDataInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String[] paramData = request.getParameterValues("c1");
		for (int i = 0; i < paramData.length; i++) {
			
			paramMap.put("BASIC_DATA_NO", paramData[i]);
			paramMap.put("START_DATE", request.getParameter("START_DATE_"+paramData[i]));
			paramMap.put("END_DATE", request.getParameter("END_DATE_"+paramData[i]));
			paramMap.put("RETURN_VALUE", request.getParameter("RETURN_VALUE_"+paramData[i]));
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("REMARK", request.getParameter("REMARK_"+paramData[i]));
			if(distinctField.equals("PERSON_ID")){
				this.paBasicItemDao.updatePaBasicItemDataInfo(paramMap) ;
			}else{
				this.paBasicItemDao.updatePaBasicItemDataInfoOther(paramMap);
			}
			
		}
		return 1 ;
	}
	
	/**
	 * 创建工资基础项目数据信息（create Add Pa Basic Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int createAddPaBasicItemDataInfo(HttpServletRequest request) {
		
		//AdminBean admin = ObjectBindUtil.getLoginUserFromSession(request) ;
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		this.paBasicItemDao.createAddPaBasicItemDataInfo(paramMap) ;
		
		return 0 ;
	}
	
	
	/**
	 * 获取工资基础项目数据集合（get Add Pa Basic Item Data List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAddPaBasicItemDataList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		this.createAddPaBasicItemDataInfo(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		// 取得数据项目信息
		LinkedHashMap paBasicItemInfo = (LinkedHashMap)this.getPaBasicItemInfo(request) ;
		
		if(ObjectUtils.toString(paBasicItemInfo.get("DISTINCT_FIELD")).equals("EMPID")){
			retrunList = paBasicItemDao.getAddPaBasicItemDataListDistinctFieldIsEmpid(paramMap) ;
		}
		else{
			retrunList = paBasicItemDao.getAddPaBasicItemDataListDistinctFieldIsNotEmpid(paramMap) ;
		}
		
		return retrunList ;
		
	}
	
	/**
	 * 添加工资基础项目数据信息（add Pa Basic Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int addPaBasicItemDataInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap<String, Object> appendMap = new LinkedHashMap<String, Object>() ;
		appendMap.put("CREATED_BY", admin.getAdminID()) ;
		
		// 页面参数
		String jsonString = request.getParameter("jsonData") ;
		List<LinkedHashMap<String, Object>> addPaBasicItemDataList = ObjectBindUtil.getRequestJsonData(jsonString, appendMap) ;
		
		this.paBasicItemDao.addPaBasicItemDataInfo(addPaBasicItemDataList) ;
		
		return 0 ;
	}
	
	/**
	 * 添加并修改工资基础项目数据信息（update And Add Pa Basic Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateAndAddPaBasicItemDataInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap<String, Object> appendMap = new LinkedHashMap<String, Object>() ;
		appendMap.put("CREATED_BY", admin.getAdminID()) ;
		
		// 页面参数
		String jsonString = request.getParameter("jsonData") ;
		List<LinkedHashMap<String, Object>> paBasicItemDataList = ObjectBindUtil.getRequestJsonData(jsonString, appendMap) ;
		
		try {
			this.paBasicItemDao.updateAndAddPaBasicItemDataInfo(paBasicItemDataList) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0 ;
	}
	
	
	/**
	 * 获取工资基础输入项目集合（get Pa Basic Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemParamList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				paBasicItemDao.getPaBasicItemParamList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
				
		}
		else{
			retrunList = paBasicItemDao.getPaBasicItemParamList(paramMap) ;
		}
		
		return retrunList ;
		
	}
	
	/**
	 * 获取工资基础输入项目集合（get Pa Basic Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemParamListNotPageNum(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("ACTIVITY", "1");
		retrunList = paBasicItemDao.getPaBasicItemParamList(paramMap) ;

		
		return retrunList ;
		
	}
	
	/**
	 * 验证添加工资基础输入项目信息（check Add Pa Basic Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddPaBasicItemParamInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", request.getParameter("cpny"));
		return this.paBasicItemDao.checkAddPaBasicItemParamInfo(paramMap) ;
	}
	
	public int checkAddPaBasicItemParamInfo(HttpServletRequest request,String cpny_id,String item_no){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		if( item_no != null && !"".equals(item_no)){
			paramMap.put("ITEM_NO", item_no);
		}
		return this.paBasicItemDao.checkAddPaBasicItemParamInfo(paramMap) ;
	}
	
	/**
	 * 添加工资基础输入项目信息（add Pa Basic Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addPaBasicItemParamInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getAdminID()) ;
			paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		}else{
			paramMap.put("CREATED_BY",personId) ;
		}
		if(request.getParameter("ACTIVITY")==null){
			paramMap.put("ACTIVITY", 1);
		}
		return this.paBasicItemDao.addPaBasicItemParamInfo(paramMap) ;
	}
	public int addPaBasicItemParamInfo(HttpServletRequest request, String cpny_id, String item_no){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			 AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			 paramMap.put("CREATED_BY", admin.getAdminID()) ;
		}else{
			paramMap.put("CREATED_BY", personId) ;
		}
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		if( item_no != null && !"".equals(item_no)){
			paramMap.put("ITEM_NO", item_no);
		}
		
		paramMap.put("DISTINCT_FIELD","PERSON_ID") ;
		if(request.getParameter("ACTIVITY")==null){
			paramMap.put("ACTIVITY", 1);
		}
		return this.paBasicItemDao.addPaBasicItemParamInfo(paramMap) ;
	}
	
	public int updatePaBasicItemParamInfo(HttpServletRequest request, String cpny_id, String item_no){
        AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		if( item_no != null && !"".equals(item_no)){
			paramMap.put("ITEM_NO", item_no);
		}
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		if(request.getParameter("ACTIVITY")==null){
			paramMap.put("ACTIVITY", 1);
		}
		return this.paBasicItemDao.updatePaBasicItemParam(paramMap) ;
	}


	/**
	 * 获取工资基础项目参数个数（get Pa Basic Item Param Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPaBasicItemParamCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paBasicItemDao.getPaBasicItemParamCnt(paramMap) ;
		
		return retrunInt ;
	}

	/**
	 * 获取工资基础项目参数信息（get Pa Basic Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaBasicItemParamInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnObj = paBasicItemDao.getPaBasicItemParamInfo(paramMap) ;
		
		
		return returnObj ;
	}

	/**
	 * 修改工资基础项目参数信息（update Pa Basic Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaBasicItemParamInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		
		//因页面未获取到值时，取值 当前登陆者的法人代码
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		return this.paBasicItemDao.updatePaBasicItemParamInfo(paramMap) ;
		
	}
	
	/**
	 * 修改工资基础项目参数信息（update Pa Basic Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaBasicItemMappingInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		return this.paBasicItemDao.updatePaBasicItemMappingInfo(paramMap) ;
	}
	
	/**
	 * 根据item_no查找和该项目已经mapping的记录删除掉
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaBasicItemParamInfoAll(HttpServletRequest request) throws SQLException{
		String item_no = request.getParameter("ITEM_NO");
		return this.paBasicItemDao.updatePaBasicItemParamInfoAll(item_no);
	}

	/**
	 * 验证删除工资基础项目参数信息（check Delete Pa Basic Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeletePaBasicItemParamInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return this.paBasicItemDao.checkDeletePaBasicItemParamInfo(paramMap) ;
	}

	/**
	 * 删除工资基础项目参数信息（delete Pa Basic Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaBasicItemParamInfo(HttpServletRequest request) {
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		try{
			String item_no = request.getParameter("PARAM_NO");
			if(item_no != null && !"".equals(item_no)){
			    paramMap.put("PARAM_NO", item_no);
			}
			return this.paBasicItemDao.deletePaBasicItemParamInfo(paramMap) ;
		}
		catch(Exception e){
			e.printStackTrace() ;
			return 0;
		}
	}
	
	/**
	 * 验证删除工资基础项目数据信息（check Delete Pa Basic Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeletePaBasicItemDataInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.paBasicItemDao.checkDeletePaBasicItemDataInfo(paramMap);
	}
	
	/**
	 * 验证删除工资基础项目其他数据（check Delete Pa Basic Item Data Info Type）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeletePaBasicItemDataInfoType(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.paBasicItemDao.checkDeletePaBasicItemDataInfoType(paramMap);
	}
	
	/**
	 * 删除工资基础项目数据信息（delete Pa Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaBasicItemDataInfo(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request); 
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATE_DATE", "SYSDATE");
		paramMap.put("UPDATED_BY", admin.getPersonId());
		return this.paBasicItemDao.deletePaBasicItemDataInfo(paramMap) ;

	}
	
	/**
	 * 删除工资基础项目数据信息（delete Pa Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaBasicItemDataInfoType(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
			this.paBasicItemDao.deletePaBasicItemDataInfoType(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	/**
	 * 批量删除工资基础项目数据（delete Pa Basic Item Data Batch Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaBasicItemDataBatchInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
				this.paBasicItemDao
						.deletePaBasicItemDataBatchInfo(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量删除工资基础项目数据（delete Pa Basic Item Data Batch Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaBasicItemDataBatchInfoType(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
				this.paBasicItemDao
						.deletePaBasicItemDataBatchInfoType(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 初始化添加保险输入项目数据信息（create Add Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int createAddPaBasicInputItemDataInfo(HttpServletRequest request) {
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		this.paBasicItemDao.createAddPaBasicInputItemDataInfo(paramMap) ;
		
		return 0 ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		LinkedHashMap paBasicItemDataInfo = (LinkedHashMap)this.paBasicItemDao.getPaBasicItemDataInfo(paramMap);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		String distinctField1 = ObjectUtils.toString(paBasicItemDataInfo
				.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(paBasicItemDataInfo
				.get("DISTINCT_FIELD_2ND"));
		paramMap.put("distinctField1", distinctField1);
		paramMap.put("distinctField2", distinctField2);
		if("CPNY_ID".equals(distinctField1)){	
			retrunList = paBasicItemDao.getPaBasicParamDataIsCpnyIDList(paramMap);
		}else if("POSITION_NO".equals(distinctField1)){//因职责存储的值为NAME，故增加新的LIST
			retrunList = paBasicItemDao.getPaBasicParamDataPositionNoList(paramMap);
		}else if("PAY_AREA_NO".equals(distinctField1)){//因职责存储的值为NAME，故增加新的LIST
			retrunList = paBasicItemDao.getPaBasicParamDataDeptAreaList(paramMap);
		}
		else if("DEPTNO".equals(distinctField2)){
			retrunList = paBasicItemDao.getPaBasicParamDataFieldOneList(paramMap);
		}else{
			retrunList = paBasicItemDao.getPaBasicParamDataFieldOneList(paramMap);
		}
		
		
		return retrunList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataTwoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		LinkedHashMap paBasicItemDataInfo = (LinkedHashMap)this.paBasicItemDao.getPaBasicItemDataInfo(paramMap);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		String distinctField2 = ObjectUtils.toString(paBasicItemDataInfo
				.get("DISTINCT_FIELD_2ND"));
		paramMap.put("distinctField2", distinctField2);
		if("DEPTNO".equals(distinctField2)){
			retrunList = paBasicItemDao.getPaBasicParamDataIsDeptNoList(paramMap);
		}else{
			retrunList = paBasicItemDao.getPaBasicParamDataFieldTwoList(paramMap);
		}
		
		
		return retrunList ;
	}
	
	/**
	 * 添加保险输入项目数据信息（add Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addPaBasicInputItemDataInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		int result = 0;
		String person_id = paramMap.get("dwz.person.personId")!=null ? paramMap.get("dwz.person.personId").toString() : "";
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("TABLE_NAME", "PA_BASIC_DATA");
		paramMap.put("PERSON_ID", person_id);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("START_DATE_STR", paramMap.get("START_DATE"));
		if(this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) != 0){
			result = 2;
		}else{	
			if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){
				this.paBasicItemDao.addPaBasicInputItemDataInfo(paramMap);
				result = 0;
			}else{
				this.paBasicItemDao.updatePaBasicItemDataInfoMonth(paramMap);
				this.paBasicItemDao.addPaBasicInputItemDataInfo(paramMap);
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
	@SuppressWarnings("unchecked")
	public int addPaBasicInputItemOtherDataInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		

		int result = 0;
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("TABLE_NAME", "PA_BASIC_DATA_OTHER");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("START_DATE_STR", paramMap.get("START_DATE"));
		if(this.paBasicItemDao.checkAddPaBasicItemDataOtherInfoMonth(paramMap) != 0){
			result = 2;
		}else{
			if(this.paBasicItemDao.checkAddPaBasicItemDataOtherInfo(paramMap) == 0){
				this.paBasicItemDao.addPaBasicInputItemOtherDataInfo(paramMap);
				result = 0;
			}else{
				this.paBasicItemDao.updatePaBasicItemDataOtherInfoMonth(paramMap);
				this.paBasicItemDao.addPaBasicInputItemOtherDataInfo(paramMap);
				result = 0;
			}
		}

		return result ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getPaBasicInputItemDataList(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("ACTIVITY", "1");
		return this.paBasicItemDao.getPaBasicInputItemDataList(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List paBasicInputItemAllList(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("ACTIVITY", "1");
		return this.paBasicItemDao.paBasicInputItemAllList(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertOrUpdatePaBasicItemData(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		int result = 0;
		paramMap.put("DISTINCT_FIELD", "PERSON_ID");
		String[] paramData = request.getParameterValues("h3");
		if(paramData == null){
			result = 1;
		}else{
			for (int i = 0; i < paramData.length; i++) {
				paramMap.put("TABLE_NAME", "PA_BASIC_DATA");
				paramMap.put("BASIC_DATA_NO", request.getParameter("BASIC_BASIC_DATA_NO_"+paramData[i]));
				//paramMap.put("NOT_BASIC_DATA_NO", request.getParameter("BASIC_BASIC_DATA_NO_"+paramData[i]));
				paramMap.put("PARAM_NO", request.getParameter("BASIC_PARAM_NO_"+paramData[i]));
				paramMap.put("PERSON_ID", request.getParameter("BASIC_PERSON_ID_"+paramData[i]));
				paramMap.put("RETURN_VALUE", request.getParameter("BASIC_RETURN_VALUE_"+paramData[i]));
				paramMap.put("CPNY_ID", request.getParameter("BASIC_CPNY_ID_"+paramData[i]));
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("START_DATE", request.getParameter("BASIC_START_DATE_"+paramData[i]));
				paramMap.put("START_DATE_STR", request.getParameter("BASIC_START_DATE_"+paramData[i]));
				paramMap.put("END_DATE", request.getParameter("BASIC_END_DATE_"+paramData[i]));
				paramMap.put("REMARK", request.getParameter("BASIC_REMARK_"+paramData[i]));
				if(!"".equals(paramMap.get("START_DATE"))&&!"".equals(paramMap.get("RETURN_VALUE"))){
					if("INSERT".equals(request.getParameter("PA_BASIC_FLAG_"+paramData[i]))){
						if(this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) != 0){
							result = 2;
						}else{	
							if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){
								this.paBasicItemDao.addPaBasicInputItemDataInfo(paramMap);
								result = 1;
							}else{
								this.paBasicItemDao.updatePaBasicItemDataInfoMonth(paramMap);
								this.paBasicItemDao.addPaBasicInputItemDataInfo(paramMap);
								result = 1;
							}
						}
					}else{
						String startDate = StringUtil.checkNull(paramMap.get("START_DATE"));
						String startDateOld = StringUtil.checkNull(request.getParameter("BASIC_START_DATE_OLD_"+paramData[i]));
						if(startDate.equals(startDateOld)){
							this.paBasicItemDao.updatePaBasicItemDataInfo(paramMap) ;
							result = 1;
						}else{	
							if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){
								this.paBasicItemDao.updatePaBasicItemDataInfo(paramMap) ;
								result = 1;
							}else{
								this.paBasicItemDao.updatePaBasicItemDataInfoMonth(paramMap);
								this.paBasicItemDao.addPaBasicInputItemDataInfo(paramMap) ;
								result = 1;
							}
						}
	//					if(!"".equals(paramMap.get("START_DATE"))&&!"".equals(paramMap.get("RETURN_VALUE"))
	//							&&this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) == 1){
	//						if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){
	//							this.paBasicItemDao.updatePaBasicItemData(paramMap) ;
	//							this.paBasicItemDao.addPaBasicInputItemDataInfo(paramMap);
	//							result = 1;
	//						}else{
	//							this.paBasicItemDao.updatePaBasicItemDataValue(paramMap);
	//							result = 1;
	//						}
	//					}else{
	//						result = 1;
	//						continue;
	//					}
					}
				}else{
					result = 1;
				}
			}
		}
		return result ;
	}
	/**
	 * 添加 派遣地项目信息（add Pa SendToAdministration Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addPaSendToAdministrationItemInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		paramMap.put("SENDNAME", request.getParameter("sendto_pa9999")) ;
		paramMap.put("DUTYNO", request.getParameter("dutyno_pa9999")) ;
		paramMap.put("SENDMONEY", request.getParameter("amount_pa9999")) ;
		paramMap.put("SENDTITLE", request.getParameter("itemName_pa9999")) ;
		paramMap.put("ACTIVITY", request.getParameter("ableStatus_pa9999")) ;
		try {
			this.paBasicItemDao.addPaSendToAdministrationItemInfo(paramMap) ;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;	
	}
	 /**
	 * 获取派遣地项目（get Pa SendToAdministration Item List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewPaSendToAdministrationList(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;	
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("BASIC_ITEM_PARAM", request.getAttribute("BASIC_ITEM_PARAM"));
		retrunList = paBasicItemDao.getPaSendToAdministrationList(paramMap) ;
		
		
		
		return retrunList ;
		
	}
	/**
	 * 获取派遣地项目个数（get Pa Basic Item Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPaSendToAdministrationListCnt(HttpServletRequest request) throws Exception {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		retrunInt = paBasicItemDao.getPaSendToAdministrationListCnt(paramMap) ;
		
		return retrunInt ;
	}
	/**
	 * 删除派遣地项目参数(delete Pa Basic Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaSendToAdministrationInfo(HttpServletRequest request) {
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("SENDID",request.getParameter("SENDID"));
		try{
			return this.paBasicItemDao.deletePaSendToAdministrationInfo(paramMap) ;
		}
		catch(Exception e){
			e.printStackTrace() ;
			return 0;
		}
	}

	@Override
	public List getImportExcelTempPaBasicItemList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paBasicItemDao.getImportExcelTempPaBasicItemList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paBasicItemDao.getImportExcelTempPaBasicItemList(paramMap) ;
		}
		return retrunList;
	}

	@Override
	public int getImportExcelTempPaBasicItemListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paBasicItemDao.getImportExcelTempPaBasicItemListCnt(paramMap) ;
		return retrunInt ;
	}

	@Override
	public int getImportExcelTempPaBasicItemListErrCnt(
			HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paBasicItemDao.getImportExcelTempPaBasicItemListErrCnt(paramMap) ;
		return retrunInt ;
	}
	
	public String importPaBasicItemExcelExcel(HttpServletRequest request){
		String retrunInt = "0" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paBasicItemDao.importPaBasicItemExcelExcel(paramMap) ;
		return retrunInt;
	}
	
	/*---------------------------------------------------------------------------*/
	@Override
	public List getImportExcelTempPaParamList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paBasicItemDao.getImportExcelTempPaParamList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paBasicItemDao.getImportExcelTempPaParamList(paramMap) ;
		}
		return retrunList;
	}
	/*---------------------------------------------------------------------------*/
	@Override
	public List getImportExcelTempPaParamMonthList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paBasicItemDao.getImportExcelTempPaParamMonthList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paBasicItemDao.getImportExcelTempPaParamMonthList(paramMap) ;
		}
		return retrunList;
	}
	/*---------------------------------------------------------------------------按职级*/
	@Override
	public List getImportExcelTempPaParamGradeList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paBasicItemDao.getImportExcelTempPaParamGradeList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paBasicItemDao.getImportExcelTempPaParamGradeList(paramMap) ;
		}
		return retrunList;
	}

	@Override
	public int getImportExcelTempPaParamListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paBasicItemDao.getImportExcelTempPaParamListCnt(paramMap) ;
		return retrunInt ;
	}
	@Override
	public int getImportExcelTempPaParamMonthListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paBasicItemDao.getImportExcelTempPaParamMonthListCnt(paramMap) ;
		return retrunInt ;
	}
	@Override
	public int getImportExcelTempPaParamListGradeCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paBasicItemDao.getImportExcelTempPaParamListGradeCnt(paramMap) ;
		return retrunInt ;
	}

	@Override
	public int getImportExcelTempPaParamListErrCnt(
			HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paBasicItemDao.getImportExcelTempPaParamListErrCnt(paramMap) ;
		return retrunInt ;
	}
	@Override
	public int getImportExcelTempPaParamMonthListErrCnt(
			HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paBasicItemDao.getImportExcelTempPaParamMonthListErrCnt(paramMap) ;
		return retrunInt ;
	}
	@Override
	public int getImportExcelTempPaParamListErrGradeCnt(
			HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paBasicItemDao.getImportExcelTempPaParamListErrGradeCnt(paramMap) ;
		return retrunInt ;
	}
	
	public String importPaParamExcel(HttpServletRequest request){
		String retrunInt = "0" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("DISTINCT_FIELD", paramMap.get("DISTINCT_FIELD"));
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paBasicItemDao.importPaParamExcel(paramMap) ;
		return retrunInt;
	}
	
	public String importPaParamMonthExcel(HttpServletRequest request){
		String retrunInt = "0" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("DISTINCT_FIELD", paramMap.get("DISTINCT_FIELD"));
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paBasicItemDao.importPaParamMonthExcel(paramMap) ;
		return retrunInt;
	}
}	


