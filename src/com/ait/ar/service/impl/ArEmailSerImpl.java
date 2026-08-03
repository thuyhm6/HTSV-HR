package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ar.dao.ArEmailDao;
import com.ait.ar.service.ArEmailSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
@Service
public class ArEmailSerImpl implements ArEmailSer{
	 Logger logger = Logger.getLogger(ArEmailSerImpl.class);
	 @Autowired	
	 private ArEmailDao arEmailDao;
	/**
	 * 月考勤汇总查找发送list
	 * @param request
	 * @return
	 */
	@Override
	public List getArEmailList(HttpServletRequest request,String person_id,String deptno){
		List arEmailList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		if(person_id != null && !"".equals(person_id)){
			paramMap.put("PERSON_ID", person_id);
		}
		if(deptno != null && !"".equals(deptno)){
			paramMap.put("DEPTNO", deptno);
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(!"".equals(paramMap.get("PAY_AREA_NO")) && paramMap.get("PAY_AREA_NO")!=null){
			paramMap.put("PAY_AREA_NO", paramMap.get("PAY_AREA_NO").toString().replace("!", ","));
		}
		arEmailList = arEmailDao.getArEmailList(paramMap) ;
	    return arEmailList;
	}
	
	/**
	 * 考勤明细查找发送list
	 * @param request
	 * @return
	 */
	@Override
	public List getArDetailListEmail(Map paramMap,String person_id){
		List arEmailList = new ArrayList();
		if(!"".equals(person_id) && person_id != null){
			paramMap.put("PERSON_ID", person_id);
		}
		arEmailList = arEmailDao.getArDetailListEmail(paramMap) ;
	    return arEmailList;
	}
	
	/**
	 * 查找考勤员
	 * @param request
	 * @return
	 */
	@Override
	public List getAttKeeperList(Map paramMap){
		List arEmailList = new ArrayList();
		arEmailList = arEmailDao.getAttKeeperList(paramMap) ;
	    return arEmailList;
	}
	
	//查找部门
	@SuppressWarnings("unchecked")
	public List getOrgDeptList(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		List arEmailList = new ArrayList();
		arEmailList = arEmailDao.getOrgDeptList(paramMap) ;
	    return arEmailList;
	}
}
