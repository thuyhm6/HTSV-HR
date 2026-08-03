package com.ait.pa.service.imp.salarycode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.ApplicationSalaryCodeDao;
import com.ait.pa.service.salarycode.ApplicationSalaryCodeSer;
import com.ait.sys.bean.AdminBean;
import com.ait.pa.dao.salaryCodeDao;
import com.ait.sys.service.impl.AffirmSerImpl;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class ApplicationSalaryCodeSerImpl<SalaryCode> implements ApplicationSalaryCodeSer {
	Logger logger = Logger.getLogger(AffirmSerImpl.class);


	@Autowired
	private ApplicationSalaryCodeDao applicationSalaryCodeDao;
	
	@Autowired
	private salaryCodeDao salaryCodeDao;

	/**
	 * 查找人员和工资代码权限的匹配记录
	 */
	@SuppressWarnings("unchecked")
	public List getApplicationSalaryCodeList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		paramMap.put("CPNY_ID", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = applicationSalaryCodeDao.getApplicationSalaryCodeList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = applicationSalaryCodeDao.getApplicationSalaryCodeList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 查找人员和工资代码权限的匹配记录 数量
	 */
	@SuppressWarnings("unchecked")
	public int getApplicationSalaryCodeCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		paramMap.put("CPNY_ID", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		return applicationSalaryCodeDao.getApplicationSalaryCodeCnt(paramMap);
	}
	
	/**
	 * 根据工资代码的item_no查找工资代码相应的名称
	 */
	public List findSalaryNameByItemNo(HttpServletRequest request, String item_no){
		List retrunList = new ArrayList();
		retrunList = applicationSalaryCodeDao.findSalaryNameByItemNo(item_no);
		return retrunList;
	}
	
	/**
	 * 添加人员工资代码匹配的记录
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addApplicationSalaryInfo(HttpServletRequest request)throws Exception{
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			SalaryCode returnObj = (SalaryCode) new Object() ;
			List<SalaryCode> list = new ArrayList<SalaryCode>();
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			String personId = request.getParameter("dwz.person.personId");
			String cpny = request.getParameter("dwz.person.cpny");
			// 页面提交的JSON信息
			String[] item_nos = request.getParameterValues("check_pa");
			int num = 0;
			String SALARY_CODE = null;
			String SALARY_NAME = null;
			if(item_nos.length>0){
				for(int i=0;i<item_nos.length;i++){
					if(num==0){
						String[] a = item_nos[0].split(",");
						SALARY_CODE = a[0];
						SALARY_NAME = a[1];
					}else{
						String[] a = item_nos[i].split(",");
						SALARY_CODE+= ","+a[0];
						SALARY_NAME+= ","+a[1];
					}
					num++;
				}
			}
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("SALARY_CODE",SALARY_CODE);
			paramMap.put("SALARY_NAME",SALARY_NAME);
			paramMap.put("PERSON_ID",personId);
			paramMap.put("CPNY_ID",cpny);
			this.applicationSalaryCodeDao.addApplicationSalaryInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 检查该人员是否有相应的记录  如果有则不能添加  请修改
	 * @param object
	 * @return
	 */
	
	public int checkApplicationSalaryInfo(HttpServletRequest request){
		// 页面提交数据
	    LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
	    return this.applicationSalaryCodeDao.checkApplicationSalaryInfo(paramMap);
	}
	
	/**
	 * 删除人员工资代码匹配的记录
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int deleteApplicationSalaryInfo(HttpServletRequest request) throws Exception{
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.applicationSalaryCodeDao.deleteApplicationSalaryInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 修改人员工资代码匹配的记录
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateApplicationSalaryInfo(HttpServletRequest request)throws Exception{
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String[] item_nos = request.getParameterValues("check_pa");
			int num = 0;
			String SALARY_CODE = null;
			String SALARY_NAME = null;
			if(item_nos.length>0){
				for(int i=0;i<item_nos.length;i++){
					if(num==0){
						String[] a = item_nos[0].split(",");
						SALARY_CODE = a[0];
						SALARY_NAME = a[1];
					}else{
						String[] a = item_nos[i].split(",");
						SALARY_CODE+= ","+a[0];
						SALARY_NAME+= ","+a[1];
					}
					num++;
				}
			}
			paramMap.put("CPNY_ID",request.getParameter("CPNY_ID"));
			paramMap.put("UPDATED_BY", admin.getPersonId());
			paramMap.put("SALARY_CODE",SALARY_CODE);
			paramMap.put("SALARY_NAME",SALARY_NAME);
			this.applicationSalaryCodeDao.updateApplicationSalaryInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public Map getApplicationSalaryInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID",request.getParameter("CPNY_ID"));
		paramMap.put("language",Messages.getLanguage(request));
		return this.applicationSalaryCodeDao.getApplicationSalaryInfo(paramMap);
	}
	
	/***
	 * 添加页面的代码表
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemApplicationList(HttpServletRequest request){
        List retrunList = new ArrayList() ;
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = applicationSalaryCodeDao.getPaInputItemApplicationList(paramMap) ;
		return retrunList ;
	}
}
