package com.ait.hrm.service.impl;




import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.HrmDao;
import com.ait.hrm.service.HrmSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;


@Service
public class HrmSerImpl implements HrmSer {
	Logger logger = Logger.getLogger(HrmSerImpl.class);
	@Autowired
	private HrmDao hrmDao;

	public Object getDeptById(Object object) {	
		
		return hrmDao.getDeptById(object);
	}

	@SuppressWarnings("unchecked")
	public List getDeptTree(HttpServletRequest request,String limit) {
		List list=new ArrayList();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		
		if(limit.equals("hr")){
			list = hrmDao.getDeptTree("hrm.getDeptTreeForHr",paramMap);
		}else if(limit.equals("ar")){
			list = hrmDao.getDeptTree("hrm.getDeptTreeForAr",paramMap);
		}
		
		return list;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAllDept(Object object) {		
		return hrmDao.getDeptTree("hrm.getAllDept",object);
	}
	
	@SuppressWarnings("unchecked")
	public List getBusiness(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return hrmDao.getBusiness(paramMap);
	}
	
	
}
