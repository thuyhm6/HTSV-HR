package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
import com.ait.ar.dao.SummaryFormulaDao;
import com.ait.ar.service.SummaryFormulaSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.GetMapByPaArUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: SummaryFormulaSerImp.java
 * @Description:
 * @Create date: 2012-1-13 下午05:10:31
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class SummaryFormulaSerImp implements SummaryFormulaSer {
	Logger logger = Logger.getLogger(SummaryFormulaSerImp.class);
	
	@Autowired
	private SummaryFormulaDao summaryFormulaDao;

	/**
	 * 取汇总公式列表(get SummaryFormula Item List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaItemList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("language",admin.getLanguage());
		return summaryFormulaDao.getSummaryFormulaItemList(paramMap);
	}
	/**
	 * 根据汇总项目查询考勤项目列表
	 */
	@SuppressWarnings("unchecked")
	public List getsummaryFormulaList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return summaryFormulaDao.getsummaryFormulaList(paramMap);
	}
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaItemToCN() 
	{
		// TODO Auto-generated method stub
		return summaryFormulaDao.getSummaryFormulaItemToCN();
	}
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaAR_STA_ITEM()
	{
		// TODO Auto-generated method stub
		return summaryFormulaDao.getSummaryFormulaAR_STA_ITEM();
	}
	
	/**
	 * 取个人信息列表(get PersonBasic Info)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getPersonBasicInfo(HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		LinkedHashMap paramMap = new LinkedHashMap();
		
		// session用户信息
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language",admin.getLanguage());
		return summaryFormulaDao.getPersonBasicInfo(paramMap);
	}
	
	/**
	 * 取项目列表(get Item List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getItemList(HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		LinkedHashMap paramMap = new LinkedHashMap();
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("language",admin.getLanguage());
		return summaryFormulaDao.getItemList(paramMap);
	}
	@SuppressWarnings("unchecked")
	public int addFormulaItem(HttpServletRequest request) {
	   
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		return this.summaryFormulaDao.addFormulaItem(paramMap) ;
	}
	@SuppressWarnings("unchecked")
	public int updateFormulaItem(HttpServletRequest request) {
		
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID());
		
		return this.summaryFormulaDao.updateFormulaItem(paramMap);
		
	}
	
	/**
	 * 取汇总公式明细(get SummaryFormula ToCN)
	 * @param request
	 * @return List
	 * @throws
	 */
	public List getSummaryFormulaToCN(HttpServletRequest request)
	{
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		List getsummaryFormulaList = this.summaryFormulaDao.getsummaryFormulaList(paramMap) ;
		List list=new ArrayList();
		Map<String, String> returnValue = new HashMap<String, String>();
		list= this.summaryFormulaDao.getSummaryFormulaItemToInter(paramMap);
		 for(int i=0;i<list.size();i++)
	     {
	    	 LinkedHashMap map=(LinkedHashMap)list.get(i);
	    	 if(map.get("ID")!=null&&map.get("NAME")!=null){
	    	 returnValue.put(map.get("ID").toString() , map.get("NAME").toString());
	    	 }
	     }

		 	returnValue.put("ATT_ITEM.SHIFT_ID", "班次ID");
			returnValue.put("ATT_ITEM.SHIFT_NO", "班次");
			returnValue.put("ATT_ITEM.SHIFT_NAME", "班次名称");
			returnValue.put("STA_ITEM.AR_MONTH", "考勤月");
			returnValue.put("ATT_ITEM.AR_DATE_STR", "考勤日期");
			returnValue.put("ATT_ITEM.STATUS_NAME", "员工状态");
			returnValue.put("ATT_ITEM.DATE_TYPE_NAME", "日期类型");
			
		  GetMapByPaArUtil.formularToCN(getsummaryFormulaList,returnValue,"AR");
		  return getsummaryFormulaList;
	}
	public Object getFormulaInfo(HttpServletRequest request) 
	{
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.summaryFormulaDao.getFormulaInfo(paramMap) ;
		
	}
	public int deleteFormulaInfo(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.summaryFormulaDao.deleteFormulaInfo(paramMap) ;
	}
	
}
