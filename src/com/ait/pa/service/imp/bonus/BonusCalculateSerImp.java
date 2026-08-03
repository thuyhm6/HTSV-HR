package com.ait.pa.service.imp.bonus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.BonusCalculateDao;
import com.ait.pa.service.bonus.BonusCalculateSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Service
public class BonusCalculateSerImp implements BonusCalculateSer {

	Logger logger = Logger.getLogger(BonusCalculateSerImp.class);
	
	@Autowired
	private BonusCalculateDao bonusCalculateDao ;
	
	@SuppressWarnings("unchecked")
	public String bonusCalculate(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMINID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		System.out.println("===============工资发放日=============="+paramMap.get("GIVE_DATE"));

		returnString = bonusCalculateDao.bonusCalculate(paramMap) ;
		
		return returnString ;
	}
	
	@SuppressWarnings("unchecked")
	public int getCheckPaCalculateType(HttpServletRequest request){
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.bonusCalculateDao.getCheckPaCalculateType(paramMap) ;
	}

	@Override
	public List getSalaryProvideDateBn(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		LinkedHashMap  paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		retrunList = bonusCalculateDao.getSalaryProvideDateBn(paramMap) ;
		
		
		return retrunList ; 
	}
}
