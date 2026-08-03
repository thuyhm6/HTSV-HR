package com.ait.pa.service.imp.wagebase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.PaCityCoefficientDao;
import com.ait.pa.service.wagebase.PaCityCoefficientSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class PaCityCoefficientSerImp implements PaCityCoefficientSer {

	Logger logger = Logger.getLogger(PaCityCoefficientSerImp.class);
	
	@Autowired
	private PaCityCoefficientDao paCityCoefficientDao;
	
	@SuppressWarnings("unchecked")
	public List paCityCoefficientList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
//			paramMap.put("CPNY_ID", admin.getCpnyId()); 
//		}

		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				paCityCoefficientDao.getPaCityCoefficientList(paramMap , 
						UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = paCityCoefficientDao.getPaCityCoefficientList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int paCityCoefficientCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId()); 
		}

		retrunInt = paCityCoefficientDao.getPaCityCoefficientCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public int checkAddCityCoefficientInfo(HttpServletRequest request) {
		
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		retrunInt = paCityCoefficientDao.checkAddCityCoefficientInfo(paramMap) ;
		
		return retrunInt ;
	}
	
	
	@SuppressWarnings("unchecked")
	public int addCityCoefficientInfo(HttpServletRequest request) {
		
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		paramMap.put("CREATED_BY", admin.getPersonId());

		retrunInt = paCityCoefficientDao.addCityCoefficientInfo(paramMap) ;
		
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public Object paCityCoefficientInfo(HttpServletRequest request) {
		
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		
		returnObj = paCityCoefficientDao.paCityCoefficientInfo(paramMap) ;
		
		
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	public int updateCityCoefficientInfo(HttpServletRequest request) {
		
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		paramMap.put("UPDATED_BY", admin.getPersonId());

		retrunInt = paCityCoefficientDao.updateCityCoefficientInfo(paramMap) ;
		
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteCityCoefficientInfo(HttpServletRequest request) {
		
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		retrunInt = paCityCoefficientDao.deleteCityCoefficientInfo(paramMap) ;
		
		return retrunInt ;
	}
}
