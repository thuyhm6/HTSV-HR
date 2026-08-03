package com.ait.pa.service.imp.workManagement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.PaEmpAccountDao;
import com.ait.pa.dao.PaProgressDao;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.pa.service.salary.PaProgressSer;
import com.ait.pa.service.workManagement.PaEmpAccountSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class PaEmpAccountSerImp implements PaEmpAccountSer {

	Logger logger = Logger.getLogger(PaEmpAccountSerImp.class);
	
	@Autowired
	private PaEmpAccountDao paEmpAccountDao ;
	@Autowired
	private PaTempSalesDAO paTempSalesDAO;
	
	@SuppressWarnings("unchecked")
	public List getPaEmpAccountList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if("1".equals(paramMap.get("search_null_NO"))){
			paramMap.put("search_nullACCOUNT_NO", '1');
			paramMap.put("search_null_NO", null);
		}else if("2".equals(paramMap.get("search_null_NO"))){
			paramMap.put("search_nullSECURITY_NO", '2');
			paramMap.put("search_null_NO", null);
		}else if("3".equals(paramMap.get("search_null_NO"))){
			paramMap.put("search_nullFUND_NO", '3');
			paramMap.put("search_null_NO", null);
		}else if("4".equals(paramMap.get("search_null_NO"))){
			paramMap.put("search_nullTAX_NO", '4');
			paramMap.put("search_null_NO", null);
		}
		paramMap.put("PA_SUPERVISOR_INFO", admin.getPersonId());
		// 第一次进入页面不查询
		if(paramMap.get("firstView")==null){
			retrunList = paEmpAccountDao.getPaEmpAccountList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getPaEmpAccountListCnt(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if("1".equals(paramMap.get("search_null_NO"))){
			paramMap.put("search_nullACCOUNT_NO", '1');
			paramMap.put("search_null_NO", null);
		}else if("2".equals(paramMap.get("search_null_NO"))){
			paramMap.put("search_nullSECURITY_NO", '2');
			paramMap.put("search_null_NO", null);
		}else if("3".equals(paramMap.get("search_null_NO"))){
			paramMap.put("search_nullFUND_NO", '3');
			paramMap.put("search_null_NO", null);
		}else if("4".equals(paramMap.get("search_null_NO"))){
			paramMap.put("search_nullFAX_NO", '4');
			paramMap.put("search_null_NO", null);
		}
		paramMap.put("PA_SUPERVISOR_INFO", admin.getPersonId());
		// 第一次进入页面不查询
		if(paramMap.get("firstView")==null){
			retrunInt = paEmpAccountDao.getPaEmpAccountListCnt(paramMap) ;
		}
		return retrunInt ;
	}
	
	@Override
	public List getPaEmpAccountTempList(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		retrunList = paEmpAccountDao.getPaEmpAccountTempList(paramMap);
		return retrunList;
	}
	
	
	@Override
	public List getPaEmpAccountTempList1(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		retrunList = paEmpAccountDao.getPaEmpAccountTempList1(paramMap);
		return retrunList;
	}
	@Override
	public String submitImportExcelPaEmpAccountData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_PA_EMPACCOUNT_EXCEL_IMP.PR_IMPORT_PA_EMPACCOUNT_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@Override
	public int getPaEmpAccountTempCnt(HttpServletRequest request, String errorFlag) throws Exception{
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = paEmpAccountDao.getPaEmpAccountTempErrorCnt(paramMap);
		}else{
			retrunInt = paEmpAccountDao.getPaEmpAccountTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	@Override
	public String valImportExcelPaEmpAccountData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_PA_EMPACCOUNT_EXCEL_IMP.PR_VALID_PA_EMPACCOUNT_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@Override
	public String valImportExcelPaEmpAccountData1(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_HR_TRAIN_EXCEL_IMP.HR_TRAIN_ACCOUNT_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@SuppressWarnings("unchecked")
	public int addPaEmpAccountInfo(HttpServletRequest request){
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		try {
			
			this.paEmpAccountDao.addPaEmpAccountInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public Object getPaEmpAccountInfo(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.paEmpAccountDao.getPaEmpAccountInfo(paramMap) ; 
	}
	/**
	 * 更新账户信息(update PaEmpAccount Info)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updatePaEmpAccountInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			
			this.paEmpAccountDao.updatePaEmpAccountInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
	/**
	/**
	 * 删除工资账户信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int doDeletePaEmpAccountInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			
			this.paEmpAccountDao.doDeletePaEmpAccountInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
}
