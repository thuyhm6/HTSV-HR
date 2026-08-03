package com.ait.pa.service.imp.salary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.PaProgressDao;
import com.ait.pa.service.salary.PaProgressSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class PaProgressSerImp implements PaProgressSer {

	Logger logger = Logger.getLogger(PaProgressSerImp.class);
	
	@Autowired
	private PaProgressDao paProgressDao ;
	
	@SuppressWarnings("unchecked")
	public List getPaProgressList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paSalaryLockYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paSalaryLockMonth")) ;
		String GiveDate =ObjectUtils.toString(paramMap.get("paSalaryLockGiveDate"));//工资发放日
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PA_MONTH_STR", paMonth) ;
		paramMap.put("GIVE_DATE", GiveDate) ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paProgressDao.getPaProgressList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = paProgressDao.getPaProgressList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getPaProgressCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paSalaryLockYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paSalaryLockMonth")) ;
		String GiveDate =ObjectUtils.toString(paramMap.get("paSalaryLockGiveDate"));//工资发放日
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PA_MONTH_STR", paMonth) ;
		paramMap.put("GIVE_DATE", GiveDate) ;
		retrunInt = paProgressDao.getPaProgressCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPawithholdingProgressList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String  emptype = request.getParameter("empType_isChecked");
		
		String year = ObjectUtils.toString(paramMap.get("paYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paMonth")) ;
		String paMonth = year + month;
		 
		paramMap.put("EMPTYPE", emptype);
		paramMap.put("PA_MONTH_STR", paMonth) ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paProgressDao.getPawithholdingProgressList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = paProgressDao.getPawithholdingProgressList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getPawithholdingProgressCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		String  emptype = request.getParameter("empType_isChecked");
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paMonth")) ;
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PA_MONTH_STR", paMonth) ;
		paramMap.put("EMPTYPE", emptype);
		retrunInt = paProgressDao.getPawithholdingProgressCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public int updatePaProgressInfo(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMIN_ID",admin.getPersonId()) ;
		paramMap.put("CPNY_ID",admin.getCpnyId()) ;
		
		return this.paProgressDao.updatePaProgressInfo(paramMap) ;
	}

	@SuppressWarnings("unchecked")
	public List getDeptDistinguishList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("PERSON_ID", admin.getPersonId()!=null?admin.getPersonId().toString():admin.getUserNo());
		paramMap.put("USER_NAME", admin.getUsername()!=null?admin.getUsername():admin.getAdminNo());
		
		retrunList = paProgressDao.getDeptDistinguishList(paramMap) ;
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaProgressByDeptList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paMonth")) ;
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PERSON_ID", admin.getPersonId()!=null?admin.getPersonId().toString():admin.getUserNo());
		paramMap.put("USER_NAME", admin.getUsername()!=null?admin.getUsername():admin.getAdminNo());
		paramMap.put("PA_MONTH_STR", paMonth) ;
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = paProgressDao.getPaProgressByDeptList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = paProgressDao.getPaProgressByDeptList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getPaProgressByDeptCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		// 判断PA_MONTH参数是否为空,为空赋予当前月
		String year = ObjectUtils.toString(paramMap.get("paYear")) ;
		String month = ObjectUtils.toString(paramMap.get("paMonth")) ;
		String paMonth = year + month;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
		}
		paramMap.put("PERSON_ID", admin.getPersonId()!=null?admin.getPersonId().toString():admin.getUserNo());
		paramMap.put("USER_NAME", admin.getUsername()!=null?admin.getUsername():admin.getAdminNo());
		paramMap.put("PA_MONTH_STR", paMonth) ;
		
		retrunInt = paProgressDao.getPaProgressByDeptCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public int updatePaProgressByDept(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMIN_ID",admin.getPersonId()) ;
		paramMap.put("CPNY_ID",admin.getCpnyId()) ;
		
		return this.paProgressDao.updatePaProgressByDept(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	public int deletePaProgressByDept(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMIN_ID",admin.getPersonId()) ;
		paramMap.put("CPNY_ID",admin.getCpnyId()) ;
		
		return this.paProgressDao.deletePaProgressByDept(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	public int checkPaProgressByDeptCnt(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMIN_ID",admin.getPersonId()) ;
		paramMap.put("CPNY_ID",admin.getCpnyId()) ;
		
		return this.paProgressDao.checkPaProgressByDeptCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	public int copyToNextMonthPaProgressByDept(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("ADMIN_ID",admin.getPersonId()) ;
		paramMap.put("CPNY_ID",admin.getCpnyId()) ;
		
		return this.paProgressDao.copyToNextMonthPaProgressByDept(paramMap) ;
	}

	/**
	 * 工资锁定 根据工资月查询出 工资发放日期
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryLockDatePa(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		LinkedHashMap  paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		retrunList = paProgressDao.getSalaryLockDatePa(paramMap) ;
		
		
		return retrunList ; 
	}

	@Override
	public List getSalaryLockStatNo(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap  paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		retrunList = paProgressDao.getSalaryLockStatNo(paramMap) ;
		
		
		return retrunList ; 
	}
}
