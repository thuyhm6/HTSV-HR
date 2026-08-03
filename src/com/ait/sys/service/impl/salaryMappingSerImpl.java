package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.salaryMappingDao;
import com.ait.sys.service.salaryMappingSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class salaryMappingSerImpl implements salaryMappingSer{
Logger logger = Logger.getLogger(salaryMappingSerImpl.class);
	
	@Autowired
	private salaryMappingDao salaryMappingDao;

	@Override
	public List getSalaryMappingList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		 paramMap.put("PROJECT_TYPE", request.getParameter("PROJECT_TYPE"));
		String cpny = request.getParameter("seach_CPNY");
		if (cpny != null && !"".equals(cpny)) {
			if (cpny.equals("HTSV") || cpny.equals("lgech")
					|| cpny.equals("CH") || cpny.equals("ch")) {
				paramMap.put("HTSV", "HTSV");
			} else if (cpny.equals("HAE") || cpny.equals("lgeta")
					|| cpny.equals("TA") || cpny.equals("ta")) {
				paramMap.put("HAE", "HAE");
			} 
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = salaryMappingDao.getSalaryMappingList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = salaryMappingDao.getSalaryMappingList(paramMap);
		}

		return retrunList;
	}
	
	/* 
	* Title: getSalaryMappingListForAll
	* Description:为报表查出所有某法人的工资项目
	* @author 孙鹏  
	* @date 2014年11月12日 下午7:38:33  
	* @param request
	* @param modelMap
	* @return 
	* @see com.ait.sys.service.salaryMappingSer#getSalaryMappingListForAll(javax.servlet.http.HttpServletRequest, org.springframework.ui.ModelMap) 
	*/
	@Override
	public List getSalaryMappingListForAll(HttpServletRequest request,ModelMap modelMap) {
		List retrunList = new ArrayList();
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		 paramMap.put("PROJECT_TYPE", request.getParameter("PROJECT_TYPE"));
		//String cpny = request.getParameter("seach_CPNY");
		 String cpny=(String) modelMap.get("defaultCpny");
		if (cpny != null && !"".equals(cpny)) {
			if (cpny.equals("TSTO") || cpny.equals("lgech")
					|| cpny.equals("CH") || cpny.equals("ch")) {
				paramMap.put("TSTO", "TSTO");
			} else if (cpny.equals("SST") || cpny.equals("lgeta")
					|| cpny.equals("TA") || cpny.equals("ta")) {
				paramMap.put("SST", "SST");
			} else if (cpny.equals("LGETR") || cpny.equals("lgetr")
					|| cpny.equals("TR") || cpny.equals("tr")) {
				paramMap.put("LGETR", "LGETR");
			} else if (cpny.equals("LGEHZ") || cpny.equals("lgehz")
					|| cpny.equals("HZ") || cpny.equals("hz")) {
				paramMap.put("LGEHZ", "LGEHZ");
			} else if (cpny.equals("LGEYT") || cpny.equals("lgeyt")
					|| cpny.equals("YT") || cpny.equals("yt")) {
				paramMap.put("LGEYT", "LGEYT");
			} else if (cpny.equals("LGEND") || cpny.equals("lgend")
					|| cpny.equals("ND") || cpny.equals("nd")) {
				paramMap.put("LGEND", "LGEND");
			} else if (cpny.equals("LGEQD") || cpny.equals("lgeqd")
					|| cpny.equals("QD") || cpny.equals("qd")) {
				paramMap.put("LGEQD", "LGEQD");
			} else if (cpny.equals("LGEHN") || cpny.equals("lgehn")
					|| cpny.equals("HN") || cpny.equals("hn")) {
				paramMap.put("LGEHN", "LGEHN");
			} else if (cpny.equals("LGESH") || cpny.equals("lgesh")
					|| cpny.equals("SH") || cpny.equals("sh")) {
				paramMap.put("LGESH", "LGESH");
			} else if (cpny.equals("LGEQH") || cpny.equals("lgeqh")
					|| cpny.equals("QH") || cpny.equals("qh")) {
				paramMap.put("LGEQH", "LGEQH");
			} else if (cpny.equals("LGEKS") || cpny.equals("lgeks")
					|| cpny.equals("KS") || cpny.equals("ks")) {
				paramMap.put("LGEKS", "LGEKS");
			} else if (cpny.equals("LGEPN") || cpny.equals("lgepn")
					|| cpny.equals("PN") || cpny.equals("pn")) {
				paramMap.put("LGEPN", "LGEPN");
			} else if (cpny.equals("LGEQA") || cpny.equals("lgeqa")
					|| cpny.equals("QA") || cpny.equals("qa")) {
				paramMap.put("LGEQA", "LGEQA");
			} else if (cpny.equals("LGECR") || cpny.equals("lgecr")
					|| cpny.equals("CR") || cpny.equals("cr")) {
				paramMap.put("LGECR", "LGECR");
			} else if (cpny.equals("LGESY") || cpny.equals("lgesy")
					|| cpny.equals("SY") || cpny.equals("sy")) {
				paramMap.put("LGESY", "LGESY");
			}
		}
		
			retrunList = salaryMappingDao.getSalaryMappingList(paramMap);

		return retrunList;
	}
	 /**
     * 根据item_no查找数据库中所有被指定的法人
     */
	public List SalaryCpnyList(HttpServletRequest request){
		List retrunList = new ArrayList();
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		return retrunList = salaryMappingDao.getSalaryMappingList(paramMap);
	}

	@Override
	public int getSalaryMappingCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String cpny = request.getParameter("seach_CPNY");
		if (cpny != null && !"".equals(cpny)) {
			if (cpny.equals("TSTO") || cpny.equals("lgech")
					|| cpny.equals("CH") || cpny.equals("ch")) {
				paramMap.put("TSTO", "TSTO");
			} else if (cpny.equals("SST") || cpny.equals("lgeta")
					|| cpny.equals("TA") || cpny.equals("ta")) {
				paramMap.put("SST", "SST");
			} else if (cpny.equals("LGETR") || cpny.equals("lgetr")
					|| cpny.equals("TR") || cpny.equals("tr")) {
				paramMap.put("LGETR", "LGETR");
			} else if (cpny.equals("LGEHZ") || cpny.equals("lgehz")
					|| cpny.equals("HZ") || cpny.equals("hz")) {
				paramMap.put("LGEHZ", "LGEHZ");
			} else if (cpny.equals("LGEYT") || cpny.equals("lgeyt")
					|| cpny.equals("YT") || cpny.equals("yt")) {
				paramMap.put("LGEYT", "LGEYT");
			} else if (cpny.equals("LGEND") || cpny.equals("lgend")
					|| cpny.equals("ND") || cpny.equals("nd")) {
				paramMap.put("LGEND", "LGEND");
			} else if (cpny.equals("LGEQD") || cpny.equals("lgeqd")
					|| cpny.equals("QD") || cpny.equals("qd")) {
				paramMap.put("LGEQD", "LGEQD");
			} else if (cpny.equals("LGEHN") || cpny.equals("lgehn")
					|| cpny.equals("HN") || cpny.equals("hn")) {
				paramMap.put("LGEHN", "LGEHN");
			} else if (cpny.equals("LGESH") || cpny.equals("lgesh")
					|| cpny.equals("SH") || cpny.equals("sh")) {
				paramMap.put("LGESH", "LGESH");
			} else if (cpny.equals("LGEQH") || cpny.equals("lgeqh")
					|| cpny.equals("QH") || cpny.equals("qh")) {
				paramMap.put("LGEQH", "LGEQH");
			} else if (cpny.equals("LGEKS") || cpny.equals("lgeks")
					|| cpny.equals("KS") || cpny.equals("ks")) {
				paramMap.put("LGEKS", "LGEKS");
			} else if (cpny.equals("LGEPN") || cpny.equals("lgepn")
					|| cpny.equals("PN") || cpny.equals("pn")) {
				paramMap.put("LGEPN", "LGEPN");
			} else if (cpny.equals("LGEQA") || cpny.equals("lgeqa")
					|| cpny.equals("QA") || cpny.equals("qa")) {
				paramMap.put("LGEQA", "LGEQA");
			} else if (cpny.equals("LGECR") || cpny.equals("lgecr")
					|| cpny.equals("CR") || cpny.equals("cr")) {
				paramMap.put("LGECR", "LGECR");
			} else if (cpny.equals("LGESY") || cpny.equals("lgesy")
					|| cpny.equals("SY") || cpny.equals("sy")) {
				paramMap.put("LGESY", "LGESY");
			}
		}
		retrunInt = salaryMappingDao.getSalaryMappingCnt(paramMap) ;
		return retrunInt ;
	}
	
	/**
	 * 查找所有工资项目（基本项目，输入项目，计算项目）的和法人匹配的信息列表  工资查看页面 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryItemCheckMapList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		 AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PROJECT_TYPE", request.getParameter("PROJECT_TYPE"));
		String CPNY = request.getParameter("defaultCpny");
		if(CPNY == null || "".equals(CPNY)){
			paramMap.put("CPNY", admin.getCpnyId());
			paramMap.put("CPNY_ID",admin.getCpnyId());
		}else{
			paramMap.put("CPNY",CPNY);
			paramMap.put("CPNY_ID",CPNY);
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = salaryMappingDao.getSalaryItemCheckMapList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = salaryMappingDao.getSalaryItemCheckMapList(paramMap);
		}

		return retrunList;
	}
	
	/**
	 * 查找所有工资项目(基本项目，输入项目，计算项目)的和法人匹配的信息的数量 工资代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getSalaryItemCheckMapCnt(HttpServletRequest request){
		 AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PROJECT_TYPE", request.getParameter("PROJECT_TYPE"));
		String CPNY = request.getParameter("defaultCpny");
		if(CPNY == null || "".equals(CPNY)){
			paramMap.put("CPNY", admin.getCpnyId());
			paramMap.put("CPNY_ID",admin.getCpnyId());
		}else{
			paramMap.put("CPNY",CPNY);
			paramMap.put("CPNY_ID",CPNY);
		}
		retrunInt = salaryMappingDao.getSalaryItemCheckMapCnt(paramMap) ;
		return retrunInt ;
	}

	@Override
	public Object getSalaryMappingInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.remove("sortname") ;
		returnObj = salaryMappingDao.getSalaryMappingInfo(paramMap) ;
		return returnObj ;
	}

	@Override
	public int addAffirmSalaryCodeInfo(HttpServletRequest request) throws Exception {
        AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
        String PROJECT_TYPE = request.getParameter("PROJECT_TYPE");
		String ITEM_ID = request.getParameter("ITEM_ID");
		String ACTIVITY_TYPE = request.getParameter("ACTIVITY_TYPE");
		String affirm_name = admin.getLocalName();
		// 页面提交数据
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		paramMap.put("ITEM_ID", ITEM_ID);
		paramMap.put("AFFIRM_NAME", affirm_name);
		paramMap.put("PROJECT_TYPE", PROJECT_TYPE);
		paramMap.put("ACTIVITY_TYPE", ACTIVITY_TYPE);
		return this.salaryMappingDao.addAffirmSalaryCodeInfo(paramMap) ;
	}
	
	@Override
	public int addAffirmSalaryInfo(HttpServletRequest request) throws Exception {
        AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
        String PROJECT_TYPE = request.getParameter("PROJECT_TYPE");
		String ITEM_ID = request.getParameter("ITEM_ID");
		String ITEM_NO = request.getParameter("ITEM_NO");
		String ACTIVITY_TYPE = request.getParameter("ACTIVITY_TYPE");
		String affirm_name = admin.getLocalName();
		// 页面提交数据
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		if(request.getParameter("ACTIVITY")==null){
			paramMap.put("ACTIVITY", 1);
		}
		paramMap.put("ITEM_ID", ITEM_ID);
		paramMap.put("ITEM_NO", ITEM_NO);
		paramMap.put("AFFIRM_NAME", affirm_name);
		paramMap.put("PROJECT_TYPE", PROJECT_TYPE);
		paramMap.put("ACTIVITY_TYPE", ACTIVITY_TYPE);
		return this.salaryMappingDao.addAffirmSalaryInfo(paramMap) ;
	}

	@Override
	public int updateAffirmSalaryInfo(HttpServletRequest request) {
        AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		
		return this.salaryMappingDao.updateAffirmSalaryInfo(paramMap) ;
	}

	@Override
	public int updateAffirmSalaryCodeInfo(HttpServletRequest request) {
        AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		
		return this.salaryMappingDao.updateAffirmSalaryCodeInfo(paramMap) ;
	}

	@Override
	public int deleteAffirmSalaryCodeInfo(HttpServletRequest request) {
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		try{
			String item_no = request.getParameter("PARAM_NO");
			if(item_no != null && !"".equals(item_no)){
			    paramMap.put("PARAM_NO", item_no);
			}
			return this.salaryMappingDao.deleteAffirmSalaryCodeInfo(paramMap) ;
		}
		catch(Exception e){
			e.printStackTrace() ;
			return 0;
		}
	}

}
