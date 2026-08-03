package com.ait.hrm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.AddRecPageHubDao;
import com.ait.hrm.service.AddRecPageHubSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

@Service
public class AddRecPageHubSerImpl implements AddRecPageHubSer {
	Logger logger = Logger.getLogger(HrmSerImpl.class);
	@Autowired
	private AddRecPageHubDao addRecPageHubDao;
	
	/**
	 * 通过request请求封装查询条件(get conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequest(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(paramMap.get("PERSON_ID")==null || "".equals(paramMap.get("PERSON_ID"))){
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		return paramMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int addRecPageInfo(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("createBy", admin.getAdminID());
		this.addRecPageHubDao.addRecPageInfo(paramMap);
		
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getRecPageList(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		returnList = this.addRecPageHubDao.getRecPageList(paramMap);
		
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getRecPageListCnt(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		int count=0;
		count = this.addRecPageHubDao.getRecPageListCnt(paramMap);
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getRecPageInfo(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		returnList = this.addRecPageHubDao.getRecPageInfo(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getRecPageworkInfo(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		returnList = this.addRecPageHubDao.getRecPageworkInfo(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int addEditRecPageInfoHub(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("createBy", admin.getAdminID());
		String EMP_BIRTHDAY = DateUtil.convertStringDateFormat(StringUtil.checkNull(paramMap.get("EMP_BIRTHDAY")), "dd/MM/yyyy", "yyyy/MM/dd");
		String FINAL_GRAD_DATE = DateUtil.convertStringDateFormat(StringUtil.checkNull(paramMap.get("FINAL_GRAD_DATE")), "dd/MM/yyyy", "yyyy/MM/dd");
		String IDCARD_START_DATE = DateUtil.convertStringDateFormat(StringUtil.checkNull(paramMap.get("IDCARD_START_DATE")), "dd/MM/yyyy", "yyyy/MM/dd");
		String INTERVIEW_PERIOD = DateUtil.convertStringDateFormat(StringUtil.checkNull(paramMap.get("INTERVIEW_PERIOD")), "dd/MM/yyyy", "yyyy/MM/dd");
		paramMap.put("EMP_BIRTHDAY", EMP_BIRTHDAY);
		paramMap.put("FINAL_GRAD_DATE", FINAL_GRAD_DATE);
		paramMap.put("IDCARD_START_DATE", IDCARD_START_DATE);
		paramMap.put("INTERVIEW_PERIOD", INTERVIEW_PERIOD);
		
		if(paramMap.get("REC_EMPLOYEE_NO")==null || "".equals(paramMap.get("REC_EMPLOYEE_NO"))){
			this.addRecPageHubDao.addRecPageInfo(paramMap);
		}else{
			this.addRecPageHubDao.addEditRecPageInfoHub(paramMap);
		}
		
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int deleteRecPageInfo(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		if(paramMap.get("REC_EMPLOYEE_NO")!=null || !"".equals(paramMap.get("REC_EMPLOYEE_NO"))){
			this.addRecPageHubDao.deleteRecPageInfo(paramMap);
		}else{
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 删除空的数据 
	 */
	@SuppressWarnings("unchecked")
	public int deleteRecPageList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.addRecPageHubDao.deleteRecPageList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

}
