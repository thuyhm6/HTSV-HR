package com.ait.paEcc.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.EmpInfoDao;
import com.ait.paEcc.dao.PaEccDAO;
import com.ait.paEcc.service.PaEccService;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class PaEccServiceImpl implements PaEccService{

	@Autowired
	private PaEccDAO dao;
	
	@Autowired
	private EmpInfoDao empInfoDao;

	@Override
	@SuppressWarnings("unchecked")
	public List getEccEmpInfo(HttpServletRequest request) {
		List returnList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		paramMap.put("EMPID", request.getParameter("seach_EMPID"));
		paramMap.put("LOCAL_NAME", request.getParameter("seach_LOCAL_NAME"));
//		paramMap.put("STIME",request.getParameter("seach_STIME"));
		if(UiUtil.getPageNum(request)>0){
			returnList = empInfoDao.getEmpIdList(paramMap, UiUtil.getPageNum(request), 
							UiUtil.getNumPerPage(request));
		}else{
			returnList = empInfoDao.getEmpIdList(paramMap);
		}
		return returnList;
	}

	@Override
	public int getEccEstEmpCn(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		paramMap.put("EMPID", request.getParameter("seach_EMPID"));
		paramMap.put("LOCAL_NAME", request.getParameter("seach_LOCAL_NAME"));
//		paramMap.put("KEY", request.getParameter("seach_KEY"));
//		paramMap.put("STIME",request.getParameter("seach_STIME"));
//		return dao.getEccEstEmpCn(paramMap);
		return empInfoDao.getEmpIdListCnt(paramMap);
	}

	@Override
	public List getEmpPaEcc(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
//		Map paramMap = new LinkedHashMap();
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EMPID", request.getParameter("dwz.person.empId"));
		paramMap.put("EXPECTDATELEFT", request.getParameter("STIME"));
		paramMap.put("XIEYIJIN", Integer.parseInt(request.getParameter("xieyijin")));
		paramMap.put("ECCMIN", "2000");
		paramMap.put("ECCMAX", "8000");
		paramMap.put("ECCMIANSHUI", "172404");
		return dao.getEmpPaEcc(paramMap);
	}

	@Override
	public List getPaEccInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EMPID", request.getParameter("dwz.person.empId"));
		paramMap.put("PA_MONTH", request.getParameter("year")+request.getParameter("month"));
		paramMap.put("batches", request.getParameter("batches"));
//		paramMap.put("PERSON_ID", dao.getEmpPersonId(request.getParameter("dwz.person.empId"),
//				admin.getCpnyId()));
		if(UiUtil.getPageNum(request)>0){
			return dao.getPaEccInfo(paramMap,UiUtil.getPageNum(request),
							UiUtil.getNumPerPage(request));
		}else{
			return dao.getPaEccInfo(paramMap);
		}
	}
	@Override
	public int getPaEccCnt(HttpServletRequest request) {
		Map paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EMPID", request.getParameter("dwz.person.empId"));
		paramMap.put("PA_MONTH", request.getParameter("year")+request.getParameter("month"));
		paramMap.put("batches", request.getParameter("batches"));
		return dao.getPaEccCnt(paramMap);
	}

	@Override
	public int delPaEccInfo(HttpServletRequest request) {
		int returnInt = -1;
		try {
			Map parameterMap = new LinkedHashMap();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			parameterMap.put("CPNY_ID", admin.getCpnyId());
			parameterMap.put("dels", request.getParameter("dels"));
			parameterMap.put("PA_MONTH", request.getParameter("PA_MONTH"));
			parameterMap.put("batches", request.getParameter("batches"));
			returnInt = dao.delPaEccInfo(parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@Override
	public int calculatePaEcc(Map parameterMap) {
		int returnInt = 0;
		try {
			returnInt = dao.calculatePaEcc(parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@Override
	public int updatePaEccInfo(List list) {
		return dao.updatePaEcc(list);
	}

	@Override
	public int cancelSettlementPaEcc(HttpServletRequest request) {
		Map parameterMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		parameterMap.put("CPNY_ID", admin.getCpnyId());
		parameterMap.put("PA_MONTH", request.getParameter("PA_MONTH"));
//		parameterMap.put("PAYDATE", request.getParameter("payDate"));
		parameterMap.put("batches", request.getParameter("batches"));
		return dao.cancelSettlementPaEcc(parameterMap);
	}

	@Override
	public int settlementPaEcc(HttpServletRequest request) {
		Map parameterMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		parameterMap.put("CPNY_ID", admin.getCpnyId());
		parameterMap.put("PA_MONTH", request.getParameter("PA_MONTH"));
		parameterMap.put("PAYDATE", request.getParameter("payDate"));
		parameterMap.put("batches", request.getParameter("batches"));
		if(dao.getEccResultNo(parameterMap).equals("")){
			return 2;//没有计算不能结算
		}
		return dao.settlementPaEcc(parameterMap);
	}

	@Override
	public List searchBatchesByPaMonth(Map parameterMap) {
		return dao.searchBatchesByPaMonth(parameterMap);
	}

	@Override
	public String getEccFlag(HttpServletRequest request) {
		String returnStr = "";
		Map parameterMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String batchs = request.getParameter("batches");
		String paMonth = request.getParameter("year")+request.getParameter("month");
		if(request.getParameter("year")==null||request.getParameter("month")==null){
			paMonth = getDateStr();
		}
		if(batchs==null)
			batchs = "1";
		parameterMap.put("CPNY_ID", admin.getCpnyId());
		parameterMap.put("PA_MONTH",paMonth);
		parameterMap.put("BATCHES",batchs);
		try {
			returnStr = StringUtil.checkNull(dao.getEccFlag(parameterMap));
			if(returnStr.equals("")){
				returnStr = "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}
	
	private String getDateStr(){
		GregorianCalendar currentDay = new GregorianCalendar();
		int m = currentDay.get(Calendar.MONTH) + 1;
		int y = currentDay.get(Calendar.YEAR);
		String month = String.valueOf(m).length()>1?String.valueOf(m):"0"+String.valueOf(m);
		String year = String.valueOf(y);
		return year+month;
	}

	@Override
	public Map getResignInfo(HttpServletRequest request) {
		Map map = ObjectBindUtil.getRequestParamData(request);
		map.put("EMPID", request.getParameter("dwz.person.empId"));
		return dao.getResignInfo(map);
	}

	@Override
	public List getEccEmpPaInfo(HttpServletRequest request,Map parameterMap) {
		Map map = ObjectBindUtil.getRequestParamData(request);
		map.put("EMPID", request.getParameter("dwz.person.empId"));
		map.put("PA_MONTH", parameterMap.get("PA_MONTH").toString());
		map.put("PA_MONTH_START", parameterMap.get("PA_MONTH_START").toString());
		return dao.getEccEmpPaInfo(map);
	}

	@Override
	public String getEmpPAStartMonth(Object parameterObject) {
		return dao.getEmpPAStartMonth(parameterObject);
	}
	
	
	
}
