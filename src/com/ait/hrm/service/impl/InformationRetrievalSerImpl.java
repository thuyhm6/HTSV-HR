package com.ait.hrm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.InformationRetrievalDao;
import com.ait.hrm.dao.TransactionViewDao;
import com.ait.hrm.service.InformationRetrievalSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class InformationRetrievalSerImpl implements InformationRetrievalSer {
	
	Logger logger = Logger.getLogger(InformationRetrievalSerImpl.class);

	@Autowired
	private InformationRetrievalDao informationRetrievalDao;
	
 
 
	@SuppressWarnings("unchecked")
	public List getEmpRetrieveShowList(HttpServletRequest request) throws Exception {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		if(!ObjectUtils.toString(request.getAttribute("CondSql")).equals("")){
			paramMap.put("CondSql", request.getAttribute("CondSql").toString());
		} 
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				informationRetrievalDao.getEmpRetrieveShowList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = informationRetrievalDao.getEmpRetrieveShowList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getCustomerTableList(HttpServletRequest request) throws Exception {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		 
	 
	   retrunList = informationRetrievalDao.getCustomerTableList(paramMap) ;
	 
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getCodeParamList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PARENT_CODE_NO", request.getParameter("PARENT_CODE_NO"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		 
	 
	   retrunList = informationRetrievalDao.getCodeParamList(paramMap) ;
	 
		
		return retrunList ;
	}
	@SuppressWarnings("unchecked")
	public List getPostGradeForCheckBoxList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
	 
		paramMap.put("CPNY_ID", admin.getCpnyId());
		 
	 
	   retrunList = informationRetrievalDao.getPostGradeForCheckBoxList(paramMap) ;
	 
		
		return retrunList ;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List getPostForCheckBoxList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
	 
		paramMap.put("CPNY_ID", admin.getCpnyId());
		 
	 
	   retrunList = informationRetrievalDao.getPostForCheckBoxList(paramMap) ;
	 
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getDutyForCheckBoxList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
	 
		paramMap.put("CPNY_ID", admin.getCpnyId());
		 
	 
	   retrunList = informationRetrievalDao.getDutyForCheckBoxList(paramMap) ;
	 
		
		return retrunList ;
	}
	@SuppressWarnings("unchecked")
	public List getPositionForCheckBoxList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
	 
		paramMap.put("CPNY_ID", admin.getCpnyId());
		 
	 
	   retrunList = informationRetrievalDao.getPositionForCheckBoxList(paramMap) ;
	 
		
		return retrunList ;
	}
	
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap getCustomerTableListByNO(HttpServletRequest request) throws Exception {
		
		LinkedHashMap map = new LinkedHashMap() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("NO", request.getParameter("CUST_TABLE").toString());
	 
		map = informationRetrievalDao.getCustomerTableListByNO(paramMap) ;
	 
		
		return map ;
	}
	@SuppressWarnings("unchecked")
	public List getEmpRetrieveShowListAll(HttpServletRequest request) throws Exception {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
	 
			retrunList = informationRetrievalDao.getEmpRetrieveShowList(paramMap) ;
		 
		
		return retrunList ;
	}
   
	@SuppressWarnings("unchecked")
	public int getEmpRetrieveShowCnt(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		if(!ObjectUtils.toString(request.getAttribute("CondSqlCnt")).equals(""))
		{
			 
			paramMap.put("CondSqlCnt", request.getAttribute("CondSqlCnt").toString());
			
		} 
		return informationRetrievalDao.getEmpRetrieveShowCnt(paramMap);
	}
	
	
	public List getInfoFieldByTableNameList(HttpServletRequest request, String tableId)
	{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList() ;
		
		Map paramMap = new LinkedHashMap();
		paramMap.put("TABLE_ID", tableId);
		paramMap.put("interLanguage", admin.getLanguage());
	    retrunList = informationRetrievalDao.getInfoFieldByTableNameList(paramMap) ;
		 
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int saveEmpRetrieveInfo(HttpServletRequest request) {
		//页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("proName_ZH", request.getParameter("proName_zh"));
		paramMap.put("proName_EN", request.getParameter("proName_en"));
		paramMap.put("proName_KO", request.getParameter("proName_ko"));
	 
		paramMap.put("PERSON_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CONDSQL",request.getParameter("CondSql").toString());  // sql String
		paramMap.put("SQLKEY",request.getParameter("SqlKey").toString());   //key String
	 	paramMap.put("COLNAME",request.getParameter("ColName").toString());  //title String
	 	paramMap.put("CONDSQLCNT",request.getParameter("CondSqlCnt").toString());  //CNT
	 	
		try {
			this.informationRetrievalDao.saveEmpRetrieveInfo(request,paramMap);
			String str=request.getParameter("personids").toString();
			String [] personids=str.split("\\|");
			for(int i=1 ; i<personids.length;i++){
				if(! admin.getAdminID().toString().equals(personids[i].toString())){
				paramMap.put("PERSON_ID", personids[i]);
				this.informationRetrievalDao.saveEmpRetrieveInfo(request,paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int deleteCustTable(HttpServletRequest request) {
		//页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PERSON_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		 
	 	
		try {
			this.informationRetrievalDao.deleteCustTable(request,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}

	
	@Override
	public List getEmpIdRetrieveList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = informationRetrievalDao.getEmpIdRetrieveList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList =informationRetrievalDao.getEmpIdRetrieveList(paramMap) ;
		}
		return retrunList;
	}

	
	@Override
	public int getEmpIdRetrieveListtCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("deptNo",admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		retrunInt = informationRetrievalDao.getEmpIdListCnt(paramMap) ;
		return retrunInt;
	}

	@Override
	public List viewStructureDept(HttpServletRequest request)
	{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList() ;
		
		Map paramMap = new LinkedHashMap();
		
		paramMap.put("interLanguage", admin.getLanguage());
	    retrunList = informationRetrievalDao.viewStructureDept(paramMap) ;
		 
		
		return retrunList ;
	}
	
	
}
