package com.ait.hrm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.RecHrConfirmDao;
import com.ait.hrm.service.RecHrConfirmSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class RecHrConfirmSerImpl implements RecHrConfirmSer {
	Logger logger = Logger.getLogger(RecHrConfirmSerImpl.class);
	@Autowired
	private RecHrConfirmDao recHrConfirmDao;
	/**
	 * 人事确认页面查询 
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getReadyToHrConfirmInfoList(HttpServletRequest request) {

		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		if(paramMap.get("FINAL_CONFIRM") == null)
			paramMap.put("FINAL_CONFIRM","0");

		retrunList = recHrConfirmDao.getReadyToHrConfirmInfoList(paramMap, UiUtil
				.getPageNum(request), UiUtil.getNumPerPage(request));

		return retrunList;

	}
	
	/**
	 * 
	 * 简历人事确认
	 * 
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int recruitmentHrConfirm(HttpServletRequest request) throws Exception{
		// TODO Auto-generated method stub

	    LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
	    AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	    paramMap.put("createBy", admin.getAdminID());
	    this.recHrConfirmDao.recruitmentHrConfirm(request,paramMap);
		    
		return 1;
	}

}
