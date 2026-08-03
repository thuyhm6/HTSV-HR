package com.ait.pa.service.imp.insurance;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.InsuranceCalculateDao;
import com.ait.pa.service.insurance.InsuranceCalculateSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceCalculateSerImp.java
 * @Description:
 * @Create date: 2012-2-17 下午02:56:31
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class InsuranceCalculateSerImp implements InsuranceCalculateSer {

	Logger logger = Logger.getLogger(InsuranceCalculateSerImp.class);
	
	@Autowired
	private InsuranceCalculateDao insuranceCalculateDao ;
	
	/**
	 * 保险计算（insurance Calculate）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String insuranceCalculate(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMINID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		returnString = insuranceCalculateDao.insuranceCalculate(paramMap) ;
		
		return returnString ;
	}
	/**
	 * 新保险计算（insurance Calculate）2013-09-13
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String insuranceCalculateNew(HttpServletRequest request) {
		String returnString = "" ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMINID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		returnString = insuranceCalculateDao.insuranceCalculateNew(paramMap) ;
		
		return returnString ;
	}
	@Override
	public List getSalaryProvideDate(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		LinkedHashMap  paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		retrunList = insuranceCalculateDao.getSalaryProvideDate(paramMap) ;
		
		
		return retrunList ; 
	}
}
