package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArCardRecordDao;
import com.ait.ar.service.ArCardRecordSer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class ArCardRecordSerImp implements ArCardRecordSer {
	Logger logger = Logger.getLogger(ArCardRecordSerImp.class);

	@Autowired
	private ArCardRecordDao arCardRecordDao;

	@Autowired
	private PaTempSalesDAO paTempSalesDAO;
	
	/**
	 * 取刷卡数据列表(get ArCardRecord List)
	 * 
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArCardRecordList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("supervisor", admin.getPersonId());
			retrunList = arCardRecordDao.getArCardRecordList(paramMap);
		return retrunList;
	}
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordForSelfList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PERSONID", admin.getPersonId());
		/*
		 * if (UiUtil.getPageNum(request) > 0) { retrunList =
		 * arCardRecordDao.getArCardRecordForSelfList(paramMap, UiUtil
		 * .getPageNum(request), UiUtil.getNumPerPage(request)); } else {
		 */
			retrunList = arCardRecordDao.getArCardRecordForSelfList(paramMap);
		//}
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	public int getArCardRecordListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("supervisor", admin.getPersonId());
		
		return arCardRecordDao.getArCardRecordListCnt(paramMap);
	};
	
	@SuppressWarnings("unchecked")
	public int getArCardRecordListForSelfCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PERSONID", admin.getPersonId());
		
		return arCardRecordDao.getArCardRecordListForSelfCnt(paramMap);
	};

	/**
	 * 更新刷卡信息(update ArCardRecord Info)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updateArCardRecordInfo(HttpServletRequest request) {

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//修改人
		String R_TIME = "";
		String R_DATE = request.getParameter("R_DATE") == null ? "" : request.getParameter("R_DATE");
		String R_HOUR = request.getParameter("R_HOUR") == null ? "00" : request.getParameter("R_HOUR");
		String R_MINITE = request.getParameter("R_MINITE") == null ? "00" : request.getParameter("R_MINITE");
		if(!"".equals(R_DATE)){
			R_TIME = R_DATE + " " + R_HOUR + ":" + R_MINITE;
		}
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("R_TIME", R_TIME);
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		try {
			
			this.arCardRecordDao.updateArCardRecordInfo(paramMap);
			paramMap.put("CHANGE_TYPE", "页面修改打卡");
			this.arCardRecordDao.addArShiftChangeInfo(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 删除刷卡信息(delete ArCardRecord Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int deleteArCardRecordInfo(HttpServletRequest request) {

		String jsonString = request.getParameter("jsonData");
		
		List<LinkedHashMap<String, Object>> arArCardRecordInfoList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		
		try {
			//this.arCardRecordDao.addArShiftChangeForPreDeleteInfo(arArCardRecordInfoList);
			this.arCardRecordDao.deleteArCardRecordInfo(arArCardRecordInfoList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
		
	}
	
	@SuppressWarnings("unchecked")
	public int deleteArCardInfoCommon(HttpServletRequest request, String target) {

		String jsonString = request.getParameter("jsonData");
		
		List<LinkedHashMap<String, Object>> arArCardInfoList = ObjectBindUtil.getRequestJsonData(jsonString);
		
		try {
			this.arCardRecordDao.deleteArCardInfoCommon(arArCardInfoList, target);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
		
	}

	/**
	 * 添加刷卡数据(add ArCardRecord Info)
	 * 
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int addArCardRecordInfo(HttpServletRequest request) {
		
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		//创建人
		String R_TIME = "";
		String R_DATE = request.getParameter("R_DATE") == null ? "" : request.getParameter("R_DATE");
		String R_HOUR = request.getParameter("R_HOUR") == null ? "00" : request.getParameter("R_HOUR");
		String R_MINITE = request.getParameter("R_MINITE") == null ? "00" : request.getParameter("R_MINITE");
		if(!"".equals(R_DATE)){
			R_TIME = R_DATE + " " + R_HOUR + ":" + R_MINITE;
		}
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("R_TIME", R_TIME);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		try {
			
			this.arCardRecordDao.addArCardRecordInfo(paramMap);
			paramMap.put("CHANGE_TYPE", "页面添加打卡");
			this.arCardRecordDao.addArShiftChangeInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 取刷卡信息(getArCardRecordInfo)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getArCardRecordInfo(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("LANGUAGE", admin.getLanguage());
		
		return this.arCardRecordDao.getArCardRecordInfoS(paramMap) ; 
	}

	/**
	 * 取刷卡数据列表(get ArCardRecord List)
	 * 
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArCardRecordDayList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		retrunList = arCardRecordDao.getArCardRecordDayList(paramMap);
		return retrunList;
	}
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordCommon(Map paramMap, String target) {
		List retrunList = new ArrayList();
		//Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		retrunList = arCardRecordDao.getArCardRecordCommon(paramMap, target);
		return retrunList;
	}
	
	
	/**
	 * 验证刷卡数据维护页面的Excel导入数据(check import ArCardRecord List)
	 * 
	 * @param request
	 * @return String
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String valImportExcelArCardData(HttpServletRequest request){
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_MACRECORD_EXCEL_IMP.PR_VALID_TEMP_MACRECORD_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordMealList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("supervisor", admin.getPersonId());
			retrunList = arCardRecordDao.getArCardRecordMealList(paramMap);
		return retrunList;
	}
}
