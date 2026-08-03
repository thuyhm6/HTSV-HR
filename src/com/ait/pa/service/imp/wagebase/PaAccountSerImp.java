package com.ait.pa.service.imp.wagebase;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.PaAccountDao;
import com.ait.pa.service.wagebase.PaAccountSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
@SuppressWarnings("unchecked")
public class PaAccountSerImp implements PaAccountSer {

	Logger logger = Logger.getLogger(PaAccountSerImp.class);
	
	@Autowired
	private PaAccountDao paAccountDao;
	
	public List getPaAccountList(HttpServletRequest request,String flag) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId()); 
		}
		//本考勤月开始前离职人员选择条件默认不选择
	    if("".equals(request.getParameter("do_DIMISSION_PA_COUNT")) || request.getParameter("do_DIMISSION_PA_COUNT")==null){
	    	paramMap.put("do_DIMISSION_PA_COUNT", "NO");
		}
		String SALS_MON = DateUtil.getLastMonthStr();
		paramMap.put("YEAR", SALS_MON.substring(0, 4));
		paramMap.put("MONTH", SALS_MON.substring(4, 6));
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		if("1".equals(flag)){
			paramMap.put("flag", "1");
		}
		if(!"".equals(paramMap.get("YEAR")) && !"".equals(paramMap.get("MONTH")) && paramMap.get("YEAR")!=null && paramMap.get("MONTH")!=null){
			paramMap.put("PA_MONTH", paramMap.get("YEAR").toString()+paramMap.get("MONTH").toString());
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				paAccountDao.getPaAccountList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paAccountDao.getPaAccountList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	public int getPaAccountCnt(HttpServletRequest request,String flag) {
		int retrunInt = 0 ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId()); 
		}
		//本考勤月开始前离职人员选择条件默认不选择
	    if("".equals(request.getParameter("do_DIMISSION_PA_COUNT")) || request.getParameter("do_DIMISSION_PA_COUNT")==null){
	    	paramMap.put("do_DIMISSION_PA_COUNT", "NO");
		}
		String SALS_MON = DateUtil.getLastMonthStr();
		paramMap.put("YEAR", SALS_MON.substring(0, 4));
		paramMap.put("MONTH", SALS_MON.substring(4, 6));
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		if("1".equals(flag)){
			paramMap.put("flag", "1");
		}
		if(!"".equals(paramMap.get("YEAR")) && !"".equals(paramMap.get("MONTH")) && paramMap.get("YEAR")!=null && paramMap.get("MONTH")!=null){
			paramMap.put("PA_MONTH", paramMap.get("YEAR").toString()+paramMap.get("MONTH").toString());
		}
		retrunInt = paAccountDao.getPaAccountCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	public int updatePaCalcFlagByPersonId(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		int result = 0;
		Map paramMap = new HashedMap();
		String[] paStrings = request.getParameterValues("params");
		for (int i = 0; i < paStrings.length; i++) {
		  paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		  paramMap.put("ADMIN_ID",admin.getPersonId()) ;
			paramMap.put("CPNY_ID",admin.getCpnyId()) ;
			paramMap.put("UPDATED_BY",admin.getPersonId()) ;
		  result = this.paAccountDao.updatePaCalcFlagByPersonId(paramMap);
		}
		return  result;
	}
	
	public int updatePaAccountInfo(HttpServletRequest request) throws SQLException{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()!=null?admin.getAdminID().toString():admin.getUsername()) ;
		paramMap.put("CREATED_BY", admin.getAdminID()!=null?admin.getAdminID().toString():admin.getUsername());
		int count=Integer.parseInt(((Map)this.paAccountDao.getPaAccountInfoByPersonid(paramMap)).get("COUNT").toString());
		if(count<=0){
		}
		
		return this.paAccountDao.updatePaAccountInfo(paramMap) ;
		
	}

	public Object getPaAccountCtrollerInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = paAccountDao.getPaAccountCtrollerInfo(paramMap) ;
		return returnObj ;
	}
	
	public List getBankList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		retrunList = paAccountDao.getBankList(paramMap) ;
		return retrunList ;
	}
	
	public List getBankBranchList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String person_id = request.getParameter("PERSON_ID")!=null?request.getParameter("PERSON_ID").toString():"";
		if(!"".equals(person_id) && person_id!=null){
			LinkedHashMap paramMapPre = ObjectBindUtil.getRequestParamData(request) ;
			LinkedHashMap empInfo = (LinkedHashMap)paAccountDao.getPaAccountCtrollerInfo(paramMapPre) ;	
			//如果是要修改员工的账号信息，则放入要查询的员工的person_id
			paramMap.remove("BANK_NO");
			paramMap.put("BANK_NO", empInfo.get("BANK_ID")!=null?empInfo.get("BANK_ID").toString():"");
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String cpny_id = paramMap.get("CPNY_ID")!=null?paramMap.get("CPNY_ID").toString():(admin.getCpnyId()!=null?admin.getCpnyId().toString():"");
		paramMap.put("CPNY_ID", cpny_id);
		retrunList = paAccountDao.getBankBranchList(paramMap) ;
		return retrunList ;
	}

	/**
	 * 查询出工资对象列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-4 上午11:44:09 
	* @version V1.0
	 */
	@Override
	public List getPaSalaryObjectList(HttpServletRequest request)
			throws Exception {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		if(!paramMap.containsKey("paYear")&&!paramMap.containsKey("paMonth")){
			String date = new SimpleDateFormat("yyyy-MM-dd").format((new Date(System.currentTimeMillis())));
			paramMap.put("paYear", date.substring(0, date.indexOf("-")));
			paramMap.put("paMonth", date.substring(date.indexOf("-")+1, date.lastIndexOf("-")));
		}
		
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
				paAccountDao.getPaSalaryObjectList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paAccountDao.getPaSalaryObjectList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * 查询出工资对象列表数量
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-4 上午11:56:03 
	* @version V1.0
	 */
	@Override
	public int getPaSalaryObjectCnt(HttpServletRequest request)throws Exception {
		int retrunInt = 0 ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId()); 
		}
		
		if(!paramMap.containsKey("paYear")&&!paramMap.containsKey("paMonth")){
			String date = new SimpleDateFormat("yyyy-MM-dd").format((new Date(System.currentTimeMillis())));
			paramMap.put("paYear", date.substring(0, date.indexOf("-")));
			paramMap.put("paMonth", date.substring(date.indexOf("-")+1, date.lastIndexOf("-")));
		}
		
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		retrunInt = paAccountDao.getPaSalaryObjectCnt(paramMap) ;
		
		return retrunInt ;
	}

	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午01:55:39 
	* @version V1.0
	 */
	@Override
	public int updatePaSalCalcFlagByPersonId(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("UPDATED_BY", admin.getPersonId() == null ? "" : admin.getPersonId());

		return this.paAccountDao.updatePaSalCalcFlagByPersonId(paramMap) ;
	}

	/**
	 * 查询员工状态
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-6 下午03:58:09 
	* @version V1.0
	 */
	@Override
	public List getEmpOfficeList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PARENT_CODE_NO", "15118");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return this.paAccountDao.getEmpOfficeList(paramMap);
	}

	/**
	 * 计算对象(奖金,工资,保险)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-15 下午11:21:52 
	* @version V1.0
	 */
	@Override
	public String calculateObject(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap=new LinkedHashMap();
		paramMap.put("PA_MONTH_STR", request.getParameter("PA_MONTH_pa0413"));//工资月
		paramMap.put("PA_GIVE_DATE", request.getParameter("PA_GIVE_DATE"));//工资发放日
		paramMap.put("SUPERVISOR_NO", admin.getAdminID());//登录者ID
		paramMap.put("CPNY_ID", admin.getCpnyId());//公司ID
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CALTYPE", request.getParameter("CALTYPE"));//计算类型("奖金","工资","保险")
		paramMap.put("OUTPUT_STR", "");
		return this.paAccountDao.calculateObject(paramMap);
	}

	@Override
	public Object getPaObjectCtrollerInfo(HttpServletRequest request)
			throws Exception {
		Object returnObj = new Object() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = paAccountDao.getPaObjectCtrollerInfo(paramMap) ;
		return returnObj ;
	}

	@Override
	public int updatePaObjectInfo(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()!=null?admin.getAdminID().toString():admin.getUsername()) ;
		paramMap.put("CALC_FLAG", request.getParameter("CALC_FLAG"));
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return this.paAccountDao.updatePaObjectInfo(paramMap) ;
	}
	
	@Override
	public List getPersonType(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", admin.getLanguage());
		return this.paAccountDao.getPersonType(paramMap);
	}
	
	@Override
	public List getPositionList(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", admin.getLanguage());
		return this.paAccountDao.getPositionList(paramMap);
	}
	/**
	 * 职责津贴(个人)查询
	 */
	@Override
	public List getPersonAllowance(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paAccountDao.getPersonAllowance(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paAccountDao.getPersonAllowance(paramMap) ;
		}
		return retrunList;
	}
	
	/**
	 * 获取职责津贴参数个数（get Pa Basic Item Param Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPersonAllowanceCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paAccountDao.getPersonAllowanceCnt(paramMap) ;
		return retrunInt ;
	}
	
	public Object getPaAllowanceSelf(HttpServletRequest request){
		Object returnObj = new Object() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = paAccountDao.getPaAllowanceSelf(paramMap) ;
		return returnObj ;
	}
	
	public int updatePaAllowanceSelfInfo(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()!=null?admin.getAdminID().toString():admin.getUsername()) ;
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		return paAccountDao.updatePaAllowanceSelfInfo(paramMap) ;
	}
	
	/**
	 * 按照Id号查找职责津贴标准
	 */
	public Object getPaAllowance(HttpServletRequest request){
		Object returnObj = new Object() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = paAccountDao.getPaAllowance(paramMap) ;
		return returnObj ;
	}
	/**
	 * 新增职责津贴标准
	 */
	public int insertPaAllowanceInfo(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		return paAccountDao.insertPaAllowanceInfo(paramMap);
	}
	/**
	 * 更新职责津贴标准
	 */
	public int updatePaAllowanceInfo(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getPersonId());
		return paAccountDao.updatePaAllowanceInfo(paramMap);
	}
	/**
	 * 获取职责津贴列表
	 */
	public List getAllowanceList(HttpServletRequest request){
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paAccountDao.getAllowance(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paAccountDao.getAllowance(paramMap) ;
		}
		return retrunList;
	}
	
	public int getAllowanceCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paAccountDao.getAllowanceCnt(paramMap) ;
		return retrunInt ;
	}
	
	public int deletePaAllowanceInfo(HttpServletRequest request){
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try{
			String ALLOWANCE_ID = request.getParameter("ALLOWANCE_ID");
			if(ALLOWANCE_ID != null && !"".equals(ALLOWANCE_ID)){
			    paramMap.put("ALLOWANCE_ID", ALLOWANCE_ID);
			}
			return paAccountDao.deletePaAllowanceInfo(paramMap) ;
		}
		catch(Exception e){
			e.printStackTrace() ;
			return 0;
		}
	}
	
	public List getEvaluationDataImportResultList(HttpServletRequest request){
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paAccountDao.getEvaluationDataImportResultList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paAccountDao.getEvaluationDataImportResultList(paramMap) ;
		}
		return retrunList;
	}

	public int getEvaluationDataImportResultListCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paAccountDao.getEvaluationDataImportResultListCnt(paramMap) ;
		return retrunInt ;
	}

	public int getEvaluationDataImportErrCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paAccountDao.getEvaluationDataImportErrCnt(paramMap) ;
		return retrunInt ;
	}
	
	public String importPaAllowanceExcelTempExcel(HttpServletRequest request){
		String retrunInt = "0" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paAccountDao.importPaAllowanceExcelTempExcel(paramMap) ;
		return retrunInt;
	}
	
	public List getEvaluationDataImportResultSelfList(HttpServletRequest request){
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paAccountDao.getEvaluationDataImportResultSelfList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paAccountDao.getEvaluationDataImportResultSelfList(paramMap) ;
		}
		return retrunList;
	}
	
	public List getPaAccountDataImportResultList(HttpServletRequest request){
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paAccountDao.getPaAccountDataImportResultList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paAccountDao.getPaAccountDataImportResultList(paramMap) ;
		}
		return retrunList;
	}
	
	public String importPaAllowanceExcelSelfExcel(HttpServletRequest request){
		String retrunInt = "0" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paAccountDao.importPaAllowanceExcelSelfExcel(paramMap) ;
		return retrunInt;
	}
	public String importPaAccountExcelExcel(HttpServletRequest request){
		String retrunInt = "0" ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paAccountDao.importPaAccountExcelExcel(paramMap) ;
		return retrunInt;
	}
	
	public int getPaAccountDataImportResultListCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paAccountDao.getPaAccountDataImportResultListCnt(paramMap) ;
		return retrunInt ;
	}
	
	public int getPaAccountDataImportResultListErrCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paAccountDao.getPaAccountDataImportResultListErrCnt(paramMap) ;
		return retrunInt ;
	}
	
	public int getEvaluationDataImportResultSelfListCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paAccountDao.getEvaluationDataImportResultListSelfCnt(paramMap) ;
		return retrunInt ;
	}

	public int getEvaluationDataImportErrSelfCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paAccountDao.getEvaluationDataImportErrSelfCnt(paramMap) ;
		return retrunInt ;
	}
	
	/**
	 * sy工资计算状态页面修改试用支付比例
	 * @param request
	 * @return
	 */
	public int updateC_PROB_PAY_RAT(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()!=null?admin.getAdminID().toString():admin.getUsername()) ;
		String[] personids =request.getParameter("PERSONIDS").split(","); 
		String[] params =request.getParameter("PARAM").split(","); 
		if(personids!=null && params!=null){
			if(personids.length>0 && params.length>0){
				for(int i=0;i<personids.length;i++){
					if(!"".equals(personids[i])){
						paramMap.put("PERSON_ID", personids[i]);
					}
					if(!"".equals(params[i])){
						paramMap.put("C_PROB_PAY_RAT", params[i]);
					}
					this.paAccountDao.updateC_PROB_PAY_RAT(paramMap) ;
				}
			}
		}
		return 1;
	}
}
