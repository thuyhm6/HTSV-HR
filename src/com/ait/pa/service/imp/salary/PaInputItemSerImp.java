package com.ait.pa.service.imp.salary;

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
import com.ait.pa.dao.PaInputItemDao;
import com.ait.pa.dao.PaInputItemParamDao;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.pa.service.salary.PaInputItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaInputItemSerImp.java
 * @Description:
 * @Create date: 2012-1-19 下午07:52:04
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class PaInputItemSerImp implements PaInputItemSer {

	Logger logger = Logger.getLogger(PaInputItemSerImp.class);
	
	@Autowired
	private PaInputItemDao paInputItemDao;
	
	@Autowired
	private PaInputItemParamSer paInputItemParamSer;
	
	@Autowired
	private PaBasicItemDao paBasicItemDao;
	
	@Autowired
	private PaInputItemParamDao paInputItemParamDao;
	
	/**
	 * 获取工资输入项目信息（get Pa Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getPaInputItemInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnObj = paInputItemDao.getPaInputItemInfo(paramMap) ;
		
		
		return returnObj ;
	}
	
	/**
	 * 获取工资输入项目列表（get Pa Input Item List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = this.getPaInputItemParamMap(request) ;
		paramMap.put("INPUT_ITEM_PARAM", request.getAttribute("INPUT_ITEM_PARAM"));
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				paInputItemDao.getPaInputItemList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paInputItemDao.getPaInputItemList(paramMap) ;
		}
		
		return retrunList ;
		
	}
	
	/**
	 * 获取工资输入项目个数（get Pa Input Item Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPaInputItemCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		// 页面提交数据
		Map paramMap = this.getPaInputItemParamMap(request) ;
		retrunInt = paInputItemDao.getPaInputItemCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	/**
	 * 获取工资输入项目参数（get Pa Input Item Param Map）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getPaInputItemParamMap(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		return paramMap ;
	}
	
	/**
	 * 验证添加工资输入项目信息（check Add Pa Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddPaInputItemInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PARAM_ITEM_NO", request.getParameter("ITEM_NO"));
		return this.paInputItemDao.checkAddPaInputItemInfo(paramMap) ;
		
	}

	/**
	 * 添加工资输入项目信息（add Pa Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addPaInputItemInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
//		if(request.getParameter("ITEM_ID")!=null && !"".equals(request.getParameter("ITEM_ID"))){
//			paramMap.put("CREATED_BY", admin.getAdminID()) ;
//		}
//		if(request.getParameter("ACTIVITY")!=null && !"".equals(request.getParameter("ACTIVITY"))){
//			paramMap.put("ACTIVITY", request.getParameter("ACTIVITY")) ;
//		}
//		paramMap.put("PARAM_ITEM_ID", request.getParameter("ITEM_ID")) ;
		try {
			this.paInputItemDao.addPaInputItemInfo(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
		 
	}
	
	public int addPaInputItemInfo(HttpServletRequest request,String item_no){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getAdminID()) ;
			paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		}else{
			paramMap.put("CREATED_BY", personId) ;
		}
		if(request.getParameter("ITEM_NO")!=null && !"".equals(request.getParameter("ITEM_NO"))){
			paramMap.put("ITEM_NO", request.getParameter("ITEM_NO")) ;
		}
		paramMap.put("ACTIVITY", 1) ;
		try {
			this.paInputItemDao.addPaInputItemInfoAffirm(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 修改工资输入项目信息（update Pa Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaInputItemInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
//		if(request.getParameter("ITEM_ID")!=null && !"".equals(request.getParameter("ITEM_ID"))){
//		    paramMap.put("PARAM_ITEM_ID", request.getParameter("ITEM_ID"));
//		}
//		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		try {
			this.paInputItemDao.updatePaInputItemInfo(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 验证删除工资输入项目信息（check Delete Pa Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeletePaInputItemInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("TABLE_NAME", "PA_SUMMARY");
		
		return this.paInputItemDao.checkDeletePaInputItemInfo(paramMap) ;
	}
	
	/**
	 * 删除工资输入项目信息（delete Pa Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaInputItemInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try{
			 this.paInputItemDao.deletePaInputItemInfo(paramMap) ;
		}
		catch(Exception e){
			e.printStackTrace() ;
			return 0;
		}
		return 1;
	}
	
	/**
	 * 获取工资输入项目数据列表（get Pa Input Item Data List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		// 取得数据项目信息
		LinkedHashMap paInputItemInfo = (LinkedHashMap)this.getPaInputItemInfo(request) ;
		
		if(ObjectUtils.toString(paInputItemInfo.get("DISTINCT_FIELD")).equals("EMPID")){
			retrunList = paInputItemDao.getPaInputItemDataListDistinctFieldIsEmpid(paramMap) ;
		}
		else{
			retrunList = paInputItemDao.getPaInputItemDataListDistinctFieldIsNotEmpid(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 初始化工资输入项目信息（create Pa Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int createPaInputItemInfo(HttpServletRequest request) {
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		this.paInputItemDao.createPaInputItemInfo(paramMap) ;
		
		return 0 ;
	}
	
	/**
	 * 初始化添加工资输入项目数据信息（create Add Pa Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int createAddPaInputItemDataInfo(HttpServletRequest request) {
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		this.paInputItemDao.createAddPaInputItemDataInfo(paramMap) ;
		
		return 0 ;
	}
	
	/**
	 * 修改工资输入项目数据信息（update Pa Input Item Data Info）
	 * @param parameterObject
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updatePaInputItemDataInfo(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String[] paramData = request.getParameterValues("c1");
		LinkedHashMap paInputItemInfo = (LinkedHashMap)this.paInputItemParamSer.getPaInputItemParamInfo(request);
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
				this.paInputItemDao.updatePaInputItemDataInfo(paramMap) ;
			}else{
				this.paInputItemDao.updatePaInputItemDataInfoOther(paramMap);
			}
			
		}
		return 1 ;
	}
	
	/**
	 * 获取添加工资输入项目数据列表（get Add Pa Input Item Data List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAddPaInputItemDataList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		this.createAddPaInputItemDataInfo(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		// 取得数据项目信息
		LinkedHashMap paInputItemInfo = (LinkedHashMap)this.getPaInputItemInfo(request) ;
		
		if(ObjectUtils.toString(paInputItemInfo.get("DISTINCT_FIELD")).equals("EMPID")){
			retrunList = paInputItemDao.getAddPaInputItemDataListDistinctFieldIsEmpid(paramMap) ;
		}
		else{
			retrunList = paInputItemDao.getAddPaInputItemDataListDistinctFieldIsNotEmpid(paramMap) ;
		}
		
		return retrunList ;
		
	}
	
	/**
	 * 添加工资输入项目数据信息（add Pa Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addPaInputItemDataInfo(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		int result = 0;
		String person_id = paramMap.get("personId")!=null ? paramMap.get("personId").toString() : "";
		paramMap.put("CREATED_BY", admin.getAdminID());
//		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("TABLE_NAME", "PA_PARAM_DATA");
		paramMap.put("PERSON_ID", person_id);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("START_MONTH_STR", paramMap.get("START_MONTH"));
		paramMap.put("END_MONTH_STR", paramMap.get("END_MONTH"));
		//paramMap.put("UP_FLAG", "Y");
		//输入项目数据允许多条数据存在（不做检验，不做修改，只做添加）
		this.paInputItemDao.addPaInputItemDataInfo(paramMap);
		result = 0;
//		if(this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) != 0){
//			result = 2;
//		}else{
//			if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){
//				this.paInputItemDao.addPaInputItemDataInfo(paramMap);
//				result = 0;
//			}else{
//				this.paInputItemDao.updatePaInputItemDataInfoMonth(paramMap);
//				this.paInputItemDao.addPaInputItemDataInfo(paramMap);
//				result = 0;
//			}
//		}
		
		return result ;
	}
	
	/**
	 * 添加工资输入项目数据信息（add Pa Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addPaInputItemOtherDataInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		int result = 0;
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("TABLE_NAME", "PA_PARAM_DATA_OTHER");
		paramMap.put("START_MONTH_STR", paramMap.get("START_MONTH"));
		paramMap.put("END_MONTH_STR", paramMap.get("END_MONTH"));
		//输入项目数据允许多条数据存在（不做检验，不做修改，只做添加）
		this.paInputItemDao.addPaInputItemOtherDataInfo(paramMap);
		result = 0;
//		if(this.paBasicItemDao.checkAddPaBasicItemDataOtherInfoMonth(paramMap) != 0){
//			result = 2;
//		}else{
//			if(this.paBasicItemDao.checkAddPaBasicItemDataOtherInfo(paramMap) == 0){
//				this.paInputItemDao.addPaInputItemOtherDataInfo(paramMap);
//				result = 0;
//			}else{
//				this.paInputItemDao.updatePaInputItemDataOtherInfoMonth(paramMap);
//				this.paInputItemDao.addPaInputItemOtherDataInfo(paramMap);
//				result = 0;
//				}
//		}		
		return result ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaParamDataList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		LinkedHashMap paInputItemParamInfo = (LinkedHashMap)paInputItemParamDao.getPaInputItemParamInfo(paramMap);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		String distinctField1 = ObjectUtils.toString(paInputItemParamInfo
				.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(paInputItemParamInfo
				.get("DISTINCT_FIELD_2ND"));
		paramMap.put("distinctField1", distinctField1);
		paramMap.put("distinctField2", distinctField2);
		if("CPNY_ID".equals(distinctField1)){	
			retrunList = paInputItemDao.getPaParamDataIsCpnyIdList(paramMap);
		}else if("POSITION_NO".equals(distinctField1)){
			retrunList = paInputItemDao.getPaParamDataIsPositionNoList(paramMap);
		}else if("WORK_AREA_NO".equals(distinctField1)){
			retrunList = paInputItemDao.getPaParamDataWorkAreaList(paramMap);
		}else if("DEPTNO".equals(distinctField2)){
			retrunList = paInputItemDao.getPaParamDataList(paramMap);
		}else{
			retrunList = paInputItemDao.getPaParamDataList(paramMap);
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
	public List getPaParamDataTwoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		LinkedHashMap paInputItemParamInfo = (LinkedHashMap)paInputItemParamDao.getPaInputItemParamInfo(paramMap);
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		String distinctField2 = ObjectUtils.toString(paInputItemParamInfo
				.get("DISTINCT_FIELD_2ND"));
		paramMap.put("distinctField2", distinctField2);
		if("DEPTNO".equals(distinctField2)){
			retrunList = paInputItemDao.getPaParamDataIsDeptNoList(paramMap);
		}else{
			retrunList = paInputItemDao.getPaParamDataTwoList(paramMap);
		}
		
		
		
		
		return retrunList ;
	}
	
	
	/**
	 * 通过输入项目号活的工资输入项目数据（get Pa Input Item Data List By Param No）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListByParamNo(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		/*AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		String person_id=admin.getAdminID();
		paramMap.put("PERSON_ID", person_id);*/

		// 取得数据项目信息
		LinkedHashMap paInputItemInfo = (LinkedHashMap)this.paInputItemParamSer.getPaInputItemParamDataInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String distinctField2 = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD_2ND"));
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		Calendar c = Calendar.getInstance();
		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH)+1);
		String paYear=request.getParameter("seach_paYear") == null ? year : request.getParameter("seach_paYear");
		String paMonth=request.getParameter("seach_paMonth") == null ? month : request.getParameter("seach_paMonth");
		
		paramMap.put("PA_MONTH", paYear+paMonth);
		
		/*if(paYear == null && paMonth ==null){
			paramMap.put("PA_MONTH", new SimpleDateFormat("yyyyMM").format(new Date()));
		}*/
		
		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			// 判断DISTINCT_FIELD是否是EMPID 两者返回的列表不同
			if (distinctField.equals("PERSON_ID")) {
				retrunList = paInputItemDao
						.getPaInputItemDataListDistinctFieldIsEmpid(
								paramMap, UiUtil.getPageNum(request), UiUtil
										.getNumPerPage2(request));
			}else if((distinctField.equals("POSITION_NO")||distinctField.equals("POST_GRADE_NO")) && distinctField2.equals("")){
				retrunList = paInputItemDao
				.getPaInputItemDataListDistinctFieldIsPositionNo(
						paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage2(request));
			}else if(distinctField.equals("WORK_AREA_NO")&& distinctField2.equals("")){
				retrunList = paInputItemDao
				.getPaInputItemDataListDistinctFieldIsWorkAreaNo(
						paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage2(request));
			}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
				retrunList = paInputItemDao
				.getPaInputItemDataListDistinctFieldIsCpnyId(
						paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage2(request));
			}
			//只针对与C01权限
			else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
				paramMap.put("C01_ADMINID", admin.getPersonId());
				retrunList = paInputItemDao
				.getPaInputItemDataListDistinctFieldIsNotEmpid(
						paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage2(request));
			}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
				retrunList = paInputItemDao
				.getPaInputItemDataListDistinctFieldIsDeptNo(
						paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage2(request));
			}else {
				retrunList = paInputItemDao
						.getPaInputItemDataListDistinctFieldIsNotEmpid(
								paramMap, UiUtil.getPageNum(request), UiUtil
										.getNumPerPage2(request));
			}
		} else {
			// 判断DISTINCT_FIELD是否是EMPID 两者返回的列表不同
			if (distinctField.equals("PERSON_ID")) {
				retrunList = paInputItemDao
						.getPaInputItemDataListDistinctFieldIsEmpid(paramMap);
			}else if((distinctField.equals("POSITION_NO")||distinctField.equals("POST_GRADE_NO"))  && distinctField2.equals("")){
				retrunList = paInputItemDao
				.getPaInputItemDataListDistinctFieldIsPositionNo(paramMap);
			}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
				retrunList = paInputItemDao
				.getPaInputItemDataListDistinctFieldIsCpnyId(
						paramMap);
			}//只针对与C01权限
			else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
				paramMap.put("C01_ADMINID", admin.getPersonId());
				retrunList = paInputItemDao
				.getPaInputItemDataListDistinctFieldIsNotEmpid(
						paramMap);
			}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
				retrunList = paInputItemDao
				.getPaInputItemDataListDistinctFieldIsDeptNo(
						paramMap);
			}else {
				retrunList = paInputItemDao
						.getPaInputItemDataListDistinctFieldIsNotEmpid(paramMap);
			}
		}
		return retrunList;
		
		
	}
	
	/**
	 * 通过ParamNo获取工资输入项目数据列表个数（get Pa Input Item Data List By Param No Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPaInputItemDataListByParamNoCnt(HttpServletRequest request) {
		int retrunInt = 0;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 取得数据项目信息
		LinkedHashMap paInputItemInfo = (LinkedHashMap)this.paInputItemParamSer.getPaInputItemParamDataInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String distinctField2 = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD_2ND"));
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
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
		
		if(paYear != null && paMonth != null &&!("").equals(paYear) &&!("").equals(paMonth)){
			paramMap.put("PA_MONTH", paYear+paMonth);
		}
		
		/*if(paYear == null && paMonth ==null){
			paramMap.put("PA_MONTH", new SimpleDateFormat("yyyyMM").format(new Date()));
		}*/
		
		if (distinctField.equals("PERSON_ID")) {
			retrunInt = paInputItemDao
					.getPaInputItemDataListDistinctFieldIsEmpidCnt(paramMap);
		}else if((distinctField.equals("POSITION_NO")||distinctField.equals("POST_GRADE_NO")) && distinctField2.equals("")){
			retrunInt = paInputItemDao.getPaInputItemDataListDistinctFieldIsPositionNoCnt(paramMap);
		} 
		//只针对与C01权限
		else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
			paramMap.put("C01_ADMINID", admin.getPersonId());
			retrunInt = paInputItemDao
			.getPaInputItemDataListDistinctFieldIsDeptNoCnt(
					paramMap);
		}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
			retrunInt = paInputItemDao
			.getPaInputItemDataListDistinctFieldIsCpnyIdCnt(paramMap);
		}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
			retrunInt = paInputItemDao
			.getPaInputItemDataListDistinctFieldIsDeptNoCnt(paramMap);
		} else {
			retrunInt = paInputItemDao
					.getPaInputItemDataListDistinctFieldIsNotEmpidCnt(paramMap);
		}

		return retrunInt;	
	}
	
	
	/**
	 * 获取工资输入项目数据信息（get Pa Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getPaInputItemDataInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnObj = paInputItemDao.getPaInputItemDataInfo(paramMap) ;
		
		
		return returnObj ;
	}
	
	/**
	 * 获取工资输入项目数据（get Pa Input Item Data Person List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemDataPersonList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());

		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = paInputItemDao.getPaInputItemDataPersonList(
					paramMap, UiUtil.getPageNum(request), UiUtil
							.getNumPerPage(request));

		} else {
			retrunList = paInputItemDao
					.getPaInputItemDataPersonList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取工资输入项目数据（get Pa Input Item Data Person List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemDataPersonListNoPage(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());

		retrunList = paInputItemDao
					.getPaInputItemDataPersonList(paramMap);

		return retrunList;
	}

	/**
	 * 获取添加个人输入项目数据个数（get Pa Input Item Data Person List Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPaInputItemDataPersonListCnt(HttpServletRequest request) {

		int retrunInt = 0;

		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());

		retrunInt = paInputItemDao
				.getPaInputItemDataPersonListCnt(paramMap);

		return retrunInt;
	}
	
	/**
	 * 获取添加个人输入项目（get Add Pa Personal Input List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAddPaPersonalInputList(HttpServletRequest request) {
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
			retrunList = paInputItemDao.getAddPaPersonalInputList(
					paramMap, UiUtil.getPageNum(request), UiUtil
							.getNumPerPage(request));

		} else {
			retrunList = paInputItemDao
					.getAddPaPersonalInputList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 获取工资个人输入项目个数（get Add Pa Personal Input List Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getAddPaPersonalInputListCnt(HttpServletRequest request) {

		int retrunInt = 0;

		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());

		retrunInt = paInputItemDao
				.getAddPaPersonalInputListCnt(paramMap);

		return retrunInt;
	}
	/**
	 * 修改工资输入项目数据个人信息（update Pa Input Item Data Person Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaInputItemDataPersonInfo(HttpServletRequest request) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		int result =0;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String[] paramData = request.getParameterValues("c1");
		for (int i = 0; i < paramData.length; i++) {
			paramMap.put("TABLE_NAME", "PA_PARAM_DATA");
			paramMap.put("PARAM_NO", paramData[i]);
			paramMap.put("START_MONTH", request.getParameter("START_MONTH_"+paramData[i]));
			paramMap.put("END_MONTH", request.getParameter("END_MONTH_"+paramData[i]));
			paramMap.put("RETURN_VALUE", request.getParameter("RETURN_VALUE_"+paramData[i]));
			paramMap.put("PARAM_DATA_NO", request.getParameter("PARAM_DATA_NO_"+paramData[i]));
			paramMap.put("CPNY_ID", request.getParameter("CPNY_ID_"+paramData[i]));
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID_"+paramData[i]));
			paramMap.put("REMARK", request.getParameter("REMARK_"+paramData[i]));
			// 检测下数据库里是否存在这条记录
			if(this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) != 0){
				result = 0;
			}else{
				if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){//添加成功
					this.paInputItemDao.addPaInputItemDataPersonInfo(paramMap);
					result = 1;
				}else{//修改之后又添加
					this.paInputItemDao.updatePaInputItemDataPersonInfoForMonth(paramMap);
					this.paInputItemDao.addPaInputItemDataPersonInfo(paramMap);
					result = 1;
				}
			}
		}

		return 1;
	}
	
	/**
	 * 检查删除工资输入项目数据信息（check Delete Pa Input Item Data Info Type）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeletePaInputItemDataInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.paInputItemDao.checkDeletePaInputItemDataInfo(paramMap);
	}
	
	/**
	 * 检查删除工资输入项目数据信息（check Delete Pa Input Item Data Info Type）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeletePaInputItemDataInfoType(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.paInputItemDao.checkDeletePaInputItemDataInfoType(paramMap);
	}
	
	/**
	 * 删除工资输入项目数据信息（delete Pa Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaInputItemDataInfo(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATE_DATE", "SYSDATE");
		paramMap.put("UPDATED_BY", admin.getPersonId());
		this.paInputItemDao.deletePaInputItemDataInfo(paramMap) ;
		
		return 0;
	}
	
	/**
	 * 删除输入项目数据（delete Pa Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaInputItemDataInfoType(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
			this.paInputItemDao.deletePaInputItemDataInfoType(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	/**
	 * 批量删除输入项目数据（delete Pa Input Item Data Batch Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaInputItemDataBatchInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
				this.paInputItemDao
						.deletePaInputItemDataBatchInfo(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	

	/**
	 * 批量删除输入项目数据（delete Pa Input Item Data Batch Info Type）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaInputItemDataBatchInfoType(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
				this.paInputItemDao
						.deletePaInputItemDataBatchInfoType(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 根据EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpIdList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PA_ADMIN_ID",admin.getPersonId());
		paramMap.put("CPNY_ID",admin.getCpnyId());

		if (UiUtil.getPageNum(request) > 0){
			retrunList = paInputItemDao.getEmpIdList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paInputItemDao.getEmpIdList(paramMap) ;
		}
		
		return retrunList ;

	}
	
	/**
	 * 获取员工信息个数(For the number of staff information)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEmpIdListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_ADMIN_ID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		retrunInt = paInputItemDao.getEmpIdListCnt(paramMap) ;
		
		return retrunInt ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAddPaPersonalInputItemList(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("ACTIVITY", "1");
		return paInputItemDao.getAddPaPersonalInputItemList(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemPersonAllList(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("ACTIVITY", "1");
		return this.paInputItemDao.getPaInputItemPersonAllList(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertOrUpdatePaInputItemData(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		int result =0;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String[] paramData = request.getParameterValues("h2");
		if(paramData == null){
			result = 1;
		}else{
			for (int i = 0; i < paramData.length; i++) {
				paramMap.put("TABLE_NAME", "PA_PARAM_DATA");
				paramMap.put("PARAM_NO", paramData[i]);
				paramMap.put("CPNY_ID", request.getParameter("INPUT_CPNY_ID_"+paramData[i]));
				paramMap.put("START_MONTH", request.getParameter("INPUT_START_MONTH_"+paramData[i]));
				paramMap.put("START_MONTH_STR", request.getParameter("INPUT_START_MONTH_"+paramData[i]));
				paramMap.put("END_MONTH", request.getParameter("INPUT_END_MONTH_"+paramData[i]));
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("RETURN_VALUE", request.getParameter("INPUT_RETURN_VALUE_"+paramData[i]));
				paramMap.put("PARAM_DATA_NO", request.getParameter("INPUT_PARAM_DATA_NO_"+paramData[i]));
				//paramMap.put("NOT_PARAM_DATA_NO", request.getParameter("INPUT_PARAM_DATA_NO_"+paramData[i]));
				paramMap.put("PERSON_ID", request.getParameter("INPUT_PERSON_ID_"+paramData[i]));
				paramMap.put("REMARK", request.getParameter("INPUT_REMARK_"+paramData[i]));
				
				if(!"".equals(paramMap.get("START_MONTH"))&&!"".equals(paramMap.get("RETURN_VALUE"))){
					
					if("INSERT".equals(request.getParameter("PA_INPUT_FLAG_"+paramData[i]))){
						if(this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) != 0){
							result = 2;
						}else{
							if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){
								this.paInputItemDao.addPaInputItemDataInfo(paramMap);
								result = 1;
							}else{
								this.paInputItemDao.updatePaInputItemDataInfoMonth(paramMap);
								this.paInputItemDao.addPaInputItemDataInfo(paramMap);
								result = 1;
							}
						}
					}else{
						String startMonth = StringUtil.checkNull(paramMap.get("START_MONTH"));
						String startMonthOld = StringUtil.checkNull(request.getParameter("INPUT_START_MONTH_OLD_"+paramData[i]));
						if(startMonth.equals(startMonthOld)){
							this.paInputItemDao.updatePaInputItemDataInfo(paramMap) ;
							result = 1;
						}else{
							if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){
								this.paInputItemDao.updatePaInputItemDataInfo(paramMap) ;
								result = 1;
							}else{
								this.paInputItemDao.updatePaInputItemDataInfoMonth(paramMap);
								this.paInputItemDao.addPaInputItemDataInfo(paramMap) ;
								result = 1;
							}
						}
						
	//					if(!"".equals(paramMap.get("START_MONTH"))&&!"".equals(paramMap.get("RETURN_VALUE"))
	//							&&this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 1){
	//						if(this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) == 0){
	//							this.paInputItemDao.updatePaInputItemDataPersonInfoForMonth(paramMap);
	//							this.paInputItemDao.addPaInputItemDataPersonInfo(paramMap);
	//							result = 1;
	//						}else{
	//							this.paInputItemDao.updatePaInputItemDataValue(paramMap);
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
		return result;
	}

}
