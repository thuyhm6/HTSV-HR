package com.ait.pa.service.imp.insurance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.InsurancePersonnelDao;
import com.ait.pa.dao.SalaryCanShuDao;
import com.ait.pa.service.insurance.InsurancePersonnelSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsurancePersonnelSerImp.java
 * @Description:
 * @Create date: 2012-2-10 下午06:16:48
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class InsurancePersonnelSerImp implements InsurancePersonnelSer {

	Logger logger = Logger.getLogger(InsurancePersonnelSerImp.class);
	
	@Autowired
	private InsurancePersonnelDao insurancePersonnelDao;
	@Autowired
	private SalaryCanShuDao SalaryCanShuDao;
	
//	@Autowired
//	private PaHistoryDao paHistoryDao;
	/**
	 * 获取保险输入项目信息（get Insurance Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getInsurancePersonnelInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		returnObj = insurancePersonnelDao.getInsurancePersonnelInfo(paramMap) ;
		
		return returnObj ;
	}
	
	/**
	 * 获取参保人员（get Insurance Personnel List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsurancePersonnelList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				insurancePersonnelDao.getInsurancePersonnelList(paramMap,
						UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = insurancePersonnelDao.getInsurancePersonnelList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 获取参保人员个数（get Insurance Personnel Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getInsurancePersonnelCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		retrunInt = insurancePersonnelDao.getInsurancePersonnelCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public int updateIsCalcFlagByPersonId(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMIN_ID",admin.getPersonId()) ;
		paramMap.put("CPNY_ID",admin.getCpnyId()) ;
		
		return this.insurancePersonnelDao.updateIsCalcFlagByPersonId(paramMap) ;
	}
	
	/**
	 * 设置获得参保人员信息（set Get Insurance Personnel Param）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 
	@SuppressWarnings("unchecked")
	private LinkedHashMap setGetInsurancePersonnelParam(HttpServletRequest request){
		// 从session中取得登陆用户信息
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		
		// 判读PA_MONTH参数是否为空,为空赋予当前月
		String paMonth = ObjectUtils.toString(paramMap.get("PA_MONTH")) ;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
			paramMap.put("PA_MONTH", paMonth) ;
		}
		
		paramMap.put("paSummeryTable", "PA_HISTORY_" + admin.getCpnyId());
		
		// 判断是否存在工资历史信息
		int checkPaHistroyFlag = this.paHistoryDao.getCheckPaHistoryFlag(paramMap) ;
		if (checkPaHistroyFlag == 0){
			paramMap.put("DATA_SOURCE", "HR_EMPLOYEE") ;
		}
		else{
			paramMap.put("DATA_SOURCE", "PA_HISTORY") ;
		}
		
		return paramMap ;
	}*/

	/**
	 * 添加参保人员（add Insurance Personnel Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateInsurancePersonnelInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getPersonId());
		this.insurancePersonnelDao.updateInsurancePersonnelInfo(paramMap);
		return 0;
	}

	/**
	 * 删除参保人员信息（delete Insurance Personnel Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteInsurancePersonnelInfo(HttpServletRequest request){
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PA_MONTH", request.getParameter("PA_MONTH"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		int flag = 0;
		if (paramMap.get("PA_MONTH")!=null && paramMap.get("PERSON_ID")!=null) {
			flag = this.insurancePersonnelDao.deleteInsurancePersonnelInfo(paramMap) ;
		}
		return flag;
	}
	
	/**
	 * 获取参保人员（get Insurance Personnel List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceObjectList(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList() ;
		
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat ("yyyy-MM-dd");
		String ddate = "";
		ddate = sb.format(date);
		String b[] = ddate.split("-");
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		String insYear = paramMap.get("INS_YEAR_PA0413")!=null?paramMap.get("INS_YEAR_PA0413").toString():"";
		String insMonth = paramMap.get("INS_MONTH_PA0413")!=null?paramMap.get("INS_MONTH_PA0413").toString():"";
		if(insYear==null || "".equals(insYear) || insMonth==null || "".equals(insMonth)){
			paramMap.put("INS_YEAR_PA0413", b[0].trim().toString());
			paramMap.put("INS_MONTH_PA0413", b[1].trim().toString());
		}
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				insurancePersonnelDao.getInsuranceObjectList(paramMap,
						UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			returnList = insurancePersonnelDao.getInsuranceObjectList(paramMap) ;
		}
		
		return returnList ;
	}
	
	/**
	 * 获取参保人员个数（get Insurance Personnel Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getInsuranceObjectListCnt(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int retrunInt = 0 ;
		
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat ("yyyy-MM-dd");
		String ddate = "";
		ddate = sb.format(date);
		String b[] = ddate.split("-");
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		String insYear = paramMap.get("INS_YEAR_PA0413")!=null?paramMap.get("INS_YEAR_PA0413").toString():"";
		String insMonth = paramMap.get("INS_MONTH_PA0413")!=null?paramMap.get("INS_MONTH_PA0413").toString():"";
		if(insYear==null || "".equals(insYear) || insMonth==null || "".equals(insMonth)){
			paramMap.put("INS_YEAR_PA0413", b[0].trim().toString());
			paramMap.put("INS_MONTH_PA0413", b[1].trim().toString());
		}
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		retrunInt = insurancePersonnelDao.getInsuranceObjectListCnt(paramMap) ;
		
		return retrunInt ;
	}

	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:18:41 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateInsCalcFlagByPersonId(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("UPDATED_BY", admin.getPersonId() == null ? "" : admin.getPersonId());

		return this.insurancePersonnelDao.updateInsCalcFlagByPersonId(paramMap) ;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int updateInsCalcGJJFlagByPersonId(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("UPDATED_BY", admin.getPersonId() == null ? "" : admin.getPersonId());

		return this.insurancePersonnelDao.updateInsCalcGJJFlagByPersonId(paramMap) ;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Object getInsuranceObjectInfo(HttpServletRequest request)
			throws Exception {
		Object returnObj = new Object() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = this.insurancePersonnelDao.getInsuranceObjectInfo(paramMap) ;
		return returnObj ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateInsuranceObjectInfo(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()!=null?admin.getAdminID().toString():admin.getUsername()) ;
		paramMap.put("CALC_FLAG", request.getParameter("CALC_FLAG"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return this.insurancePersonnelDao.updateInsuranceObjectInfo(paramMap) ;
	}
	
	/**
	 * 初始化保险计算对象
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lufeng@ait.net.cn
	* @date 2014-8-28 下午16:21:52 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int initInsCalcObject(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		int paLockFlag = 0;//薪资是否锁定标志：1:锁定；0:未锁定；
		LinkedHashMap paramMap=new LinkedHashMap();
		paramMap.put("IS_MONTH", request.getParameter("IS_MONTH"));
		paramMap.put("SUPERVISOR_NO", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("CALTYPE", request.getParameter("CALTYPE"));
		paramMap.put("OUTPUT_STR", "");
		paLockFlag = this.insurancePersonnelDao.getPaLockFlagByMonth(paramMap);
		if(paLockFlag == 0){//如果没有锁定或者没有生成的时候
			return this.insurancePersonnelDao.initInsCalcObject(paramMap);
		}else{//如果被锁定则不允许再进行初始化//"该月薪资已锁定，不允许再初始化保险计算对象!"
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List getInsCalcObjectTempList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//RESULT_FLAG
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = insurancePersonnelDao.getInsCalcObjectTempList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = insurancePersonnelDao.getInsCalcObjectTempList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getInsCalcObjectTempCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		
		return insurancePersonnelDao.getInsCalcObjectTempCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getInsCalcObjectTempErrorCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		
		return insurancePersonnelDao.getInsCalcObjectTempErrorCnt(paramMap) ;
	}
	
	/**
	 * 单条删除保险计算对象( delete insurance object)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delInsCalcObject(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return insurancePersonnelDao.delInsCalcObject(paramMap);
	}
	
	/**
	 * 添加导入的保险计算对象数据(add insurance object data)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addImportInsObjectData(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchInsObjDataList = new ArrayList();
		List importInsObjDataList = new ArrayList();
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		importInsObjDataList = this.insurancePersonnelDao.getInsCalcObjectTempList(paramMap);
		int checkFlag = 0;
		//这里对所有导入保险计算对象临时表里的保险计算对象数据进行验证，并将验证结果存入保险计算对象信息临时表中
		checkFlag = checkImportInsObjectData(importInsObjDataList,paramMap,request);
		//只有当所有的保险计算对象验证全部通过之后才能进行保险计算对象信息插入
		
		if(checkFlag==0){
			for (int k = 0; k <importInsObjDataList.size(); k++) {
				LinkedHashMap insObjMap = new LinkedHashMap();
				insObjMap = (LinkedHashMap)importInsObjDataList.get(k);
				String pa_month = insObjMap.get("PA_MONTH")!=null?insObjMap.get("PA_MONTH").toString():"";
				String cpnyId = insObjMap.get("CPNY_ID")!=null?insObjMap.get("CPNY_ID").toString():admin.getCpnyId();
				String empid = insObjMap.get("PERSON_ID") != null ? insObjMap.get("PERSON_ID").toString(): "";
				String calc_flag = insObjMap.get("CALC_FLAG") != null ? insObjMap.get("CALC_FLAG").toString(): "";
				String calc_gjj_flag = insObjMap.get("CALC_GJJ_FLAG") != null ? insObjMap.get("CALC_GJJ_FLAG").toString(): "";
				String remark = insObjMap.get("REMARK")!=null?insObjMap.get("REMARK").toString():"1";
				String activity = insObjMap.get("ACTIVITY")!=null?insObjMap.get("ACTIVITY").toString():"1";
				
				LinkedHashMap dataMap = new LinkedHashMap();
				dataMap.put("PA_MONTH", pa_month);
				dataMap.put("CPNY_ID", cpnyId);
				
				//根据工号获取person_id
				dataMap.put("PERSON_ID", empid);
				dataMap.put("CHECK_TYPE", "EMPID");
				List empList = this.insurancePersonnelDao.getInsObjectInfoExistList(dataMap);
				LinkedHashMap empMap = new LinkedHashMap();
				empMap = (LinkedHashMap)empList.get(0);
				dataMap.put("PERSON_ID", empMap.get("PERSON_ID").toString());
				
				dataMap.put("CALC_FLAG", calc_flag);
				dataMap.put("CALC_GJJ_FLAG", calc_gjj_flag);
				dataMap.put("REMARK", remark);
				
				dataMap.put("ACTIVITY", activity);
				dataMap.put("CREATED_BY", admin.getPersonId());
				
				batchInsObjDataList.add(dataMap);
			}
			this.insurancePersonnelDao.addInsObjectDataImport(batchInsObjDataList);
			return 1;
		}else{
			return -1;
		}
	}
	
	/**
	 * 验证导入的保险计算对象数据(check insurance object data info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkImportInsObjectData(List importInsObjDataList,LinkedHashMap paramMap,HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		//将导入的保险计算对象的check_flag统一为0
		this.insurancePersonnelDao.updateInsObjectDataCheckFlag(importInsObjDataList);
		int result = 0;
		for (int k = 0; k <importInsObjDataList.size(); k++) {
			int checkFlag = 0;
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap = (LinkedHashMap)importInsObjDataList.get(k);
			dataMap.put("UPDATED_BY", admin.getPersonId());
			
			String pa_month = dataMap.get("PA_MONTH")!=null?dataMap.get("PA_MONTH").toString():"";
			String calcFlag = dataMap.get("CALC_FLAG")!=null?dataMap.get("CALC_FLAG").toString():"";
			String calcGjjFlag = dataMap.get("CALC_GJJ_FLAG")!=null?dataMap.get("CALC_GJJ_FLAG").toString():"";
			List dataExistList = new ArrayList();
			int dataExistFlag = 0;
			List empList = new ArrayList();
			int empFlag = 0;
			
			String errorContent = "";
			
			//4.验证该保险计算对象信息在正式表中是否存在
			dataMap.put("CHECK_TYPE", "TEMP_NO");
			dataExistList = this.insurancePersonnelDao.getInsObjectInfoExistList(dataMap);
			dataExistFlag = dataExistList!=null?dataExistList.size():0;
			if(dataExistFlag >= 1) {
				/*errorContent = "[本月该员工信息已存在，无法再导入!]";
				dataMap.put("ERROR_CONTENT", errorContent);*/
				//删除原有信息
				dataMap.put("CREATED_BY", admin.getPersonId());
				dataMap.put("CPNY_ID", admin.getCpnyId());
				checkFlag = this.insurancePersonnelDao.updateInsObjectDataFormalResult(dataMap);
				result = result + 0;
			}
			//1.验证保险月份是否正确（目前只验证长度，6位）
			if(pa_month==null || "".equals(pa_month)) {
				errorContent = "[工资月为空!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateInsObjectDataCheckResult(dataMap);
				result = result + 1;
			}else if(pa_month.length()>6 || pa_month.length()<6){
				errorContent = "[工资月数值不对!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateInsObjectDataCheckResult(dataMap);
				result = result + 1;
			}
			//2.验证计算标志(社保)是否正确（只能是Y或者N）
			if(calcFlag==null || "".equals(calcFlag)) {
				errorContent = "[计算标志(社保)为空!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateInsObjectDataCheckResult(dataMap);
				result = result + 1;
			}else if(!"Y".equals(calcFlag) && !"N".equals(calcFlag)){
				errorContent = "[计算标志(社保)不对，只能为Y或N!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateInsObjectDataCheckResult(dataMap);
				result = result + 1;
			}
			if(admin.getCpnyId().equals("LGEHN")){
			//2-1.验证计算标志(公积金)是否正确（只能是Y或者N）
			if(calcGjjFlag==null || "".equals(calcGjjFlag)) {
				errorContent = "[计算标志(公积金)为空!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateInsObjectDataCheckResult(dataMap);
				result = result + 1;
			}else if(!"Y".equals(calcGjjFlag) && !"N".equals(calcGjjFlag)){
				errorContent = "[计算标志(公积金)不对，只能为Y或N!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateInsObjectDataCheckResult(dataMap);
				result = result + 1;
			}}
			//3.验证该社号是否存在
			dataMap.put("CHECK_TYPE", "EMPID");
			empList = this.insurancePersonnelDao.getInsObjectInfoExistList(dataMap);
			empFlag = empList!=null?empList.size():0;
			if(empFlag < 1) {
				errorContent = "[该社号不存在!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateInsObjectDataCheckResult(dataMap);
				result = result + 1;
			}else if(empFlag > 1){
				errorContent = "[该社号在本法人中存在多个，无法导入!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateInsObjectDataCheckResult(dataMap);
				result = result + 1;
			}
			
			//5.验证该保险计算对象信息在临时表中是否有重复
			dataMap.put("CHECK_TYPE", "TEMP");
			dataExistList = this.insurancePersonnelDao.getInsObjectInfoExistList(dataMap);
			dataExistFlag = dataExistList!=null?dataExistList.size():0;
			if(dataExistFlag > 1) {
				errorContent = "[导入的员工信息重复!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateInsObjectDataCheckResult(dataMap);
				result = result + 1;
			}
			
			result = result + checkFlag;
		}
		return result;
	}
	
	/**
	 * 删除临时表中所有导入的加班信息申请(delete insurance object information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int cancelInsObjectImport(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		try {
			//批量封装加班申请数据并处理
			this.insurancePersonnelDao.cancelInsObjectImport(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除导入的加班信息申请(delete insurance object information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delInsObjectImport(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return insurancePersonnelDao.delInsObjectImport(paramMap);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获取公积金计算人员（get fund Personnel List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getFundObjectList(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList() ;
		
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat ("yyyy-MM-dd");
		String ddate = "";
		ddate = sb.format(date);
		String b[] = ddate.split("-");
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		String insYear = paramMap.get("FUND_YEAR_PA0421")!=null?paramMap.get("FUND_YEAR_PA0421").toString():"";
		String insMonth = paramMap.get("FUND_MONTH_PA0421")!=null?paramMap.get("FUND_MONTH_PA0421").toString():"";
		if(insYear==null || "".equals(insYear) || insMonth==null || "".equals(insMonth)){
			paramMap.put("FUND_YEAR_PA0421", b[0].trim().toString());
			paramMap.put("FUND_MONTH_PA0421", b[1].trim().toString());
		}
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				insurancePersonnelDao.getFundObjectList(paramMap,
						UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			returnList = insurancePersonnelDao.getFundObjectList(paramMap) ;
		}
		
		return returnList ;
	}
	
	/**
	 * 获取公积金计算人员个数（get fund Personnel Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getFundObjectListCnt(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int retrunInt = 0 ;
		
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat ("yyyy-MM-dd");
		String ddate = "";
		ddate = sb.format(date);
		String b[] = ddate.split("-");
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		String insYear = paramMap.get("FUND_YEAR_PA0421")!=null?paramMap.get("FUND_YEAR_PA0421").toString():"";
		String insMonth = paramMap.get("FUND_MONTH_PA0421")!=null?paramMap.get("FUND_MONTH_PA0421").toString():"";
		if(insYear==null || "".equals(insYear) || insMonth==null || "".equals(insMonth)){
			paramMap.put("FUND_YEAR_PA0421", b[0].trim().toString());
			paramMap.put("FUND_MONTH_PA0421", b[1].trim().toString());
		}
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		retrunInt = insurancePersonnelDao.getFundObjectListCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	/**
	 * 初始化公积金计算对象
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lufeng@ait.net.cn
	* @date 2014-8-28 下午16:21:52 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int initFundCalcObject(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		int paLockFlag = 0;//薪资是否锁定标志：1:锁定；0:未锁定；
		LinkedHashMap paramMap=new LinkedHashMap();
		paramMap.put("IS_MONTH", request.getParameter("IS_MONTH"));
		paramMap.put("SUPERVISOR_NO", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("CALTYPE", request.getParameter("CALTYPE"));
		paramMap.put("OUTPUT_STR", "");
		paLockFlag = this.insurancePersonnelDao.getPaLockFlagByMonth(paramMap);
		if(paLockFlag == 0){//如果没有锁定或者没有生成的时候
			return this.insurancePersonnelDao.initInsCalcObject(paramMap);
		}else{//如果被锁定则不允许再进行初始化//"该月薪资已锁定，不允许再初始化保险计算对象!"
			return 0;
		}
	}
	
	/**
	 * 删除公积金人员信息（delete fund Personnel Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteFundPersonnelInfo(HttpServletRequest request){
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PA_MONTH", request.getParameter("PA_MONTH"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		int flag = 0;
		if (paramMap.get("PA_MONTH")!=null && paramMap.get("PERSON_ID")!=null) {
			flag = this.insurancePersonnelDao.deleteFundPersonnelInfo(paramMap) ;
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int updateFundCalcFlagByPersonId(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("UPDATED_BY", admin.getPersonId() == null ? "" : admin.getPersonId());

		return this.insurancePersonnelDao.updateFundCalcFlagByPersonId(paramMap) ;
	}
	
	/**
	 * 单条删除公积金计算对象( delete fund object)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delFundCalcObject(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return insurancePersonnelDao.delFundCalcObject(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getFundObjectInfo(HttpServletRequest request)throws Exception {
		Object returnObj = new Object() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = this.insurancePersonnelDao.getFundObjectInfo(paramMap) ;
		
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int updateFundObjectInfo(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()!=null?admin.getAdminID().toString():admin.getUsername()) ;
		paramMap.put("CALC_FLAG", request.getParameter("CALC_FLAG"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		
		return this.insurancePersonnelDao.updateFundObjectInfo(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	public List getFundCalcObjectTempList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//RESULT_FLAG
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = insurancePersonnelDao.getFundCalcObjectTempList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = insurancePersonnelDao.getFundCalcObjectTempList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getFundCalcObjectTempCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		
		return insurancePersonnelDao.getFundCalcObjectTempCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getFundCalcObjectTempErrorCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		
		return insurancePersonnelDao.getFundCalcObjectTempErrorCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int addImportFundObjectData(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchFundObjDataList = new ArrayList();
		List importFundObjDataList = new ArrayList();
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		importFundObjDataList = this.insurancePersonnelDao.getFundCalcObjectTempList(paramMap);
		int checkFlag = 0;
		//这里对所有导入公积金计算对象临时表里的公积金计算对象数据进行验证，并将验证结果存入公积金计算对象信息临时表中
		checkFlag = checkImportFundObjectData(importFundObjDataList,paramMap,request);
		//只有当所有的公积金计算对象验证全部通过之后才能进行公积金计算对象信息插入
		if(checkFlag==0){
			for (int k = 0; k <importFundObjDataList.size(); k++) {
				LinkedHashMap insObjMap = new LinkedHashMap();
				insObjMap = (LinkedHashMap)importFundObjDataList.get(k);
				String pa_month = insObjMap.get("PA_MONTH")!=null?insObjMap.get("PA_MONTH").toString():"";
				String cpnyId = insObjMap.get("CPNY_ID")!=null?insObjMap.get("CPNY_ID").toString():admin.getCpnyId();
				String empid = insObjMap.get("PERSON_ID") != null ? insObjMap.get("PERSON_ID").toString(): "";
				String calc_flag = insObjMap.get("CALC_FLAG") != null ? insObjMap.get("CALC_FLAG").toString(): "";
				String remark = insObjMap.get("REMARK")!=null?insObjMap.get("REMARK").toString():"1";
				String activity = insObjMap.get("ACTIVITY")!=null?insObjMap.get("ACTIVITY").toString():"1";
				
				LinkedHashMap dataMap = new LinkedHashMap();
				dataMap.put("PA_MONTH", pa_month);
				dataMap.put("CPNY_ID", cpnyId);
				
				//根据工号获取person_id
				dataMap.put("PERSON_ID", empid);
				dataMap.put("CHECK_TYPE", "EMPID");
				List empList = this.insurancePersonnelDao.getFundObjectInfoExistList(dataMap);
				LinkedHashMap empMap = new LinkedHashMap();
				empMap = (LinkedHashMap)empList.get(0);
				dataMap.put("PERSON_ID", empMap.get("PERSON_ID").toString());
				
				dataMap.put("CALC_FLAG", calc_flag);
				dataMap.put("REMARK", remark);
				
				dataMap.put("ACTIVITY", activity);
				dataMap.put("CREATED_BY", admin.getPersonId());
				
				batchFundObjDataList.add(dataMap);
			}
			this.insurancePersonnelDao.addFundObjectDataImport(batchFundObjDataList);
			return 1;
		}else{
			return -1;
		}
	}
	
	/**
	 * 验证导入的公积金计算对象数据(check fund object data info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkImportFundObjectData(List importFundObjDataList,LinkedHashMap paramMap,HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		//将导入的公积金计算对象的check_flag统一为0
		this.insurancePersonnelDao.updateFundObjectDataCheckFlag(importFundObjDataList);
		int result = 0;
		for (int k = 0; k <importFundObjDataList.size(); k++) {
			int checkFlag = 0;
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap = (LinkedHashMap)importFundObjDataList.get(k);
			dataMap.put("UPDATED_BY", admin.getPersonId());
			
			String pa_month = dataMap.get("PA_MONTH")!=null?dataMap.get("PA_MONTH").toString():"";
			String calcFlag = dataMap.get("CALC_FLAG")!=null?dataMap.get("CALC_FLAG").toString():"";
			List dataExistList = new ArrayList();
			int dataExistFlag = 0;
			List empList = new ArrayList();
			int empFlag = 0;
			
			String errorContent = "";
			//1.验证保险月份是否正确（目前只验证长度，6位）
			if(pa_month==null || "".equals(pa_month)) {
				errorContent = "[工资月为空!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateFundObjectDataCheckResult(dataMap);
				result = result + 1;
			}else if(pa_month.length()>6 || pa_month.length()<6){
				errorContent = "[工资月数值不对!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateFundObjectDataCheckResult(dataMap);
				result = result + 1;
			}
			//2.验证计算标志是否正确（只能是Y或者N）
			if(calcFlag==null || "".equals(calcFlag)) {
				errorContent = "[计算标志为空!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateFundObjectDataCheckResult(dataMap);
				result = result + 1;
			}else if(!"Y".equals(calcFlag) && !"N".equals(calcFlag)){
				errorContent = "[计算标志不对，只能为Y或N!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateFundObjectDataCheckResult(dataMap);
				result = result + 1;
			}
			//3.验证该社号是否存在
			dataMap.put("CHECK_TYPE", "EMPID");
			empList = this.insurancePersonnelDao.getFundObjectInfoExistList(dataMap);
			empFlag = empList!=null?empList.size():0;
			if(empFlag < 1) {
				errorContent = "[该社号不存在!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateFundObjectDataCheckResult(dataMap);
				result = result + 1;
			}else if(empFlag > 1){
				errorContent = "[该社号在本法人中存在多个，无法导入!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateFundObjectDataCheckResult(dataMap);
				result = result + 1;
			}
			//4.验证该公积金计算对象信息在正式表中是否存在
			dataMap.put("CHECK_TYPE", "TEMP_NO");
			dataExistList = this.insurancePersonnelDao.getFundObjectInfoExistList(dataMap);
			dataExistFlag = dataExistList!=null?dataExistList.size():0;
			if(dataExistFlag >= 1) {
				errorContent = "[本月该员工信息已存在，无法再导入!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateFundObjectDataCheckResult(dataMap);
				result = result + 1;
			}
			//5.验证该公积金计算对象信息在临时表中是否有重复
			dataMap.put("CHECK_TYPE", "TEMP");
			dataExistList = this.insurancePersonnelDao.getFundObjectInfoExistList(dataMap);
			dataExistFlag = dataExistList!=null?dataExistList.size():0;
			if(dataExistFlag > 1) {
				errorContent = "[导入的员工信息重复!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.insurancePersonnelDao.updateFundObjectDataCheckResult(dataMap);
				result = result + 1;
			}
			
			result = result + checkFlag;
		}
		return result;
	}
	
	/**
	 * 删除临时表中所有导入的公积金计算对象(delete fund object information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int cancelFundObjectImport(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		try {
			//批量封装公积金计算对象数据并处理
			this.insurancePersonnelDao.cancelFundObjectImport(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除导入的公积金计算对象申请(delete fund object information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delFundObjectImport(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return insurancePersonnelDao.delFundObjectImport(paramMap);
	}
	/**
	 * 获取参保为Y人员（get Insurance Personnel List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceObjectPerList(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList() ;
		
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat ("yyyy-MM-dd");
		String ddate = "";
		ddate = sb.format(date);
		String b[] = ddate.split("-");
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		String insYear = paramMap.get("INS_YEAR_PA0413")!=null?paramMap.get("INS_YEAR_PA0413").toString():"";
		String insMonth = paramMap.get("INS_MONTH_PA0413")!=null?paramMap.get("INS_MONTH_PA0413").toString():"";
		if(insYear==null || "".equals(insYear) || insMonth==null || "".equals(insMonth)){
			paramMap.put("INS_YEAR_PA0413", b[0].trim().toString());
			paramMap.put("INS_MONTH_PA0413", b[1].trim().toString());
		}
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("INS_CALC_FLAG","Y");
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				insurancePersonnelDao.getInsuranceObjectList(paramMap,
						UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			returnList = insurancePersonnelDao.getInsuranceObjectList(paramMap) ;
		}
		
		return returnList ;
	}
	/**
	 * 获取参保为Y人员个数（get Insurance Personnel Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getInsuranceObjectPerListCnt(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int retrunInt = 0 ;
		
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat ("yyyy-MM-dd");
		String ddate = "";
		ddate = sb.format(date);
		String b[] = ddate.split("-");
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		String insYear = paramMap.get("INS_YEAR_PA0413")!=null?paramMap.get("INS_YEAR_PA0413").toString():"";
		String insMonth = paramMap.get("INS_MONTH_PA0413")!=null?paramMap.get("INS_MONTH_PA0413").toString():"";
		if(insYear==null || "".equals(insYear) || insMonth==null || "".equals(insMonth)){
			paramMap.put("INS_YEAR_PA0413", b[0].trim().toString());
			paramMap.put("INS_MONTH_PA0413", b[1].trim().toString());
		}
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("INS_CALC_FLAG","Y");

		retrunInt = insurancePersonnelDao.getInsuranceObjectListCnt(paramMap) ;
		
		return retrunInt ;
	}
}
