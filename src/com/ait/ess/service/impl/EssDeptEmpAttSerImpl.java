package com.ait.ess.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.EssDeptEmpAttDao;
import com.ait.ess.service.EssDeptEmpAttSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class EssDeptEmpAttSerImpl implements EssDeptEmpAttSer{
  
	@Autowired
	private EssDeptEmpAttDao essDeptEmpAttDao;
	
	Logger logger = Logger.getLogger(EssDeptEmpAttSerImpl.class);

	@SuppressWarnings("unchecked")
	public List viewArShiftGroupList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		if(request.getParameter("firstView")!=null)
			retrunList = essDeptEmpAttDao.viewArShiftGroupList(paramMap) ;
		
		return retrunList ;
	}

	@SuppressWarnings("unchecked")
	public int viewArShiftGroupCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		if(request.getParameter("firstView")!=null)
			retrunInt = essDeptEmpAttDao.viewArShiftGroupCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	public int addArShiftGroupInfo(HttpServletRequest request){
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String[] paramData = request.getParameterValues("c1");
		try {
			for (int i = 0; i < paramData.length; i++) {
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID_"+paramData[i]));
				paramMap.put("BEFOR_SHIFT_NO", request.getParameter("BEFOR_SHIFT_NO_"+paramData[i]));
				paramMap.put("SHIFT_NO", request.getParameter("SHIFT_NO_"+paramData[i]));
				paramMap.put("START_DATE", request.getParameter("START_DATE_"+paramData[i]));
				paramMap.put("REMARK", request.getParameter("REMARK_"+paramData[i]));

				this.essDeptEmpAttDao.addArShiftGroupInfo(paramMap);
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	@SuppressWarnings("unchecked")
	public int delArShiftGroupInfo(HttpServletRequest request){
		LinkedHashMap paramMap = new  LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String[] paramData = request.getParameterValues("c1");
		try {
			for (int i = 0; i < paramData.length; i++) {
				paramMap.put("SEQ", paramData[i]);
				paramMap.put("UPDATED_BY", admin.getPersonId());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("CPNY_ID", admin.getCpnyId());
				this.essDeptEmpAttDao.delArShiftGroupInfo(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
}
