package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ar.dao.SummaryItemDao;
import com.ait.ar.service.SummaryItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: SummaryItemSerImp.java
 * @Description:
 * @Create date: 2012-1-13 上午10:52:31
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class SummaryItemSerImp implements SummaryItemSer {

	Logger logger = Logger.getLogger(SummaryItemSerImp.class);
	
	@Autowired
	private SummaryItemDao summaryItemDao;
	
	/**
	 * 取得汇总项目(get Summary Item)
	 * @param request
	 * @return Object
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public Object getSummaryItem(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.summaryItemDao.getSummaryItem(paramMap) ; 
	}
	
	/**
	 * 取汇总项目库(get SummaryItem List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getSummaryItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		 Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
         AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		 paramMap.put("defaultCpnyID", admin.getCpnyId()) ;
		 paramMap.put("ACTIVITY", 1) ;
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList =
				summaryItemDao.getSummaryItemList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = summaryItemDao.getSummaryItemList(paramMap) ;
		}
		return retrunList ;
	}
	/**
	 * 取汇总项目库抛去当前法人(get SummaryItem List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getSummaryItemByCpnyList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		 Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
         AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		 paramMap.put("defaultCpnyID", admin.getCpnyId()) ;
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList =
				summaryItemDao.getSummaryItemByCpnyList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = summaryItemDao.getSummaryItemByCpnyList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 添加汇总项目(add SummaryItem Info)
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int addSummaryItemInfo(HttpServletRequest request){
		
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());

		try {
			
			this.summaryItemDao.addSummaryItemInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 插入汇总项目信息(add SummaryItem Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addSummaryItemInfoAffirm(HttpServletRequest request,String cpny_id,String item_no) throws Exception{
		//页面提交数据
		        String personId = request.getParameter("personId");
				LinkedHashMap paramMap = new LinkedHashMap();
				if(personId != null || "".equals(personId)){
					paramMap = ObjectBindUtil.getRequestParamData(request) ;
					//创建人
					AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
					paramMap.put("CREATED_BY", admin.getAdminID());
				}else{
					paramMap.put("CREATED_BY", personId);
				}
				
				if (cpny_id != null && !"".equals(cpny_id)) {
					paramMap.put("CPNY_ID", cpny_id);
				}
				if (item_no != null && !"".equals(item_no)) {
					paramMap.put("AR_ITEM_NO", item_no);
				}
				

				try {
					
					this.summaryItemDao.addSummaryItemInfoAffirm(paramMap);
					
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
				
				return 1;
	}
	
	/**
	 * 修改汇总项目(update SummaryItem Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int updateSummaryItemInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//修改人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			
			this.summaryItemDao.updateSummaryItemInfo(paramMap) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除汇总项目(delete SummaryItem Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int deleteSummaryItemInfo(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("ACTIVITY",0);
		try {
			
			this.summaryItemDao.deleteSummaryItemInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	/**
	 * 检查汇总项目(check SummaryItem Info)
	 * @param request
	 * @return Map
	 * @throws
	 */
	@Override
	public int checkSummaryItemInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.summaryItemDao.checkSummaryItemInfo(paramMap) ;
	}

	/**
	 * 调整参数顺序(update SummaryItemInfo CalOrder)
	 * @param request
	 * @return int
	 * @throws
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int updateSummaryParamItemInfoCalOrder(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		List<LinkedHashMap> list = new ArrayList<LinkedHashMap>();
		
		LinkedHashMap param1 = new LinkedHashMap();
		param1.put("PARAM_NO", paramMap.get("downParamNo"));
		param1.put("CAL_ORDER", paramMap.get("downOrder"));
		list.add(param1);
		LinkedHashMap param2 = new LinkedHashMap();
		param2.put("PARAM_NO", paramMap.get("upParamNo"));
		param2.put("CAL_ORDER", paramMap.get("upOrder"));
		list.add(param2);
		
		return this.summaryItemDao.updateSummaryParamItemInfoCalOrder(list) ;
	}

	@Override
	public int getSummaryItemCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language",Messages.getLanguage(request));
		paramMap.put("ACTIVITY",1);
		return summaryItemDao.getItemCnt(paramMap) ;
	}

	/**
	 * 汇总项目参数列表(get SummaryParamItem List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@Override
	public List getSummaryParamItemList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List retrunList = new ArrayList() ;
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
//		if(StringUtils.isEmpty(request.getParameter("seach_CPNY_ID"))){
//			paramMap.put("CPNY_ID", admin.getCpnyId());
//		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		paramMap.put("language",Messages.getLanguage(request));
		if (UiUtil.getPageNum(request) > 0){
			retrunList =
				summaryItemDao.getSummaryParamItemList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = summaryItemDao.getSummaryParamItemList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 汇总项目参数列表(get SummaryParamItem List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@Override
	public List getSummaryParamItemList1(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List retrunList = new ArrayList() ;
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
//		if(StringUtils.isEmpty(request.getParameter("seach_CPNY_ID"))){
//			paramMap.put("CPNY_ID", admin.getCpnyId());
//		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		paramMap.put("language",Messages.getLanguage(request));
		paramMap.put("ACTIVITY",1);
		
		retrunList = summaryItemDao.getSummaryParamItemList1(paramMap) ;
		
		return retrunList ;
	}
	
	
	@Override
	public int addShowSummaryParamItemList(HttpServletRequest request) {
		 
		int num = 1;
		  try {
			Map paramMap = new LinkedHashMap();
			  String[] c1 = request.getParameterValues("c1");
			  for (int i = 0; i < c1.length; i++) {
				String showynkey = "showyn_"+c1[i];
				String showorderkey = "showorder_"+c1[i];
				paramMap.put("item", c1[i]);
				paramMap.put("showyn", request.getParameter(showynkey));
				paramMap.put("showorder", request.getParameter(showorderkey));
				summaryItemDao.addShowSummaryParamItemList(paramMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			num = 0;
			e.printStackTrace();
		}
		return  num;
	 
	}
	
	/**
	 * 汇总项目参数条数(get SummaryParamItem count)
	 * @param request
	 * @return int
	 * @throws
	 */
	@Override
	public int getSummaryParamItemCnt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(StringUtils.isEmpty(request.getParameter("seach_CPNY_ID"))){
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		return summaryItemDao.getItemParamCnt(paramMap) ;
	}

	/**
	 * 添加汇总项目参数(add SummaryParamItem Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@Override
	public int addSummaryParamItemInfo(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());

		try {
			
			this.summaryItemDao.addSummaryParamItemInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	@Override
	public Object getSummaryParamItem(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.summaryItemDao.getSummaryParamItem(paramMap) ; 
	}

	/**
	 * 修改汇总项目参数(update SummaryParamItem Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@Override
	public int updateSummaryParamItemInfo(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//修改人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			
			this.summaryItemDao.updateSummaryParamItemInfo(paramMap) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 删除汇总项目参数(delete SummaryParamItem Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@Override
	public int deleteSummaryParamItemInfo(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("ACTIVITY", 0);
		
		try {
			
			this.summaryItemDao.deleteSummaryParamItemInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	/**
	 * 检查删除汇总项目(check For ItemDelete)
	 * @param request
	 * @return int
	 * @throws
	 */
	@Override
	public int checkForItemDelete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.summaryItemDao.checkForItemDelete(paramMap);
	}

	/**
	 * 检查删除汇总项目参数(check For ItemDelete)
	 * @param request
	 * @return int
	 * @throws
	 */
	@Override
	public int checkForItemParamDelete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.summaryItemDao.checkForItemParamDelete(paramMap);
	}

	/**
	 * 检查汇总项目参数(check For ItemParam Unique)
	 * @param request
	 * @return int
	 * @throws
	 */
	@Override
	public int checkForItemParamUnique(HttpServletRequest request) {
		// TODO Auto-generated method stub
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.summaryItemDao.checkForItemParamUnique(paramMap);
	}
	
	public int checkForItemParam(HttpServletRequest request,String item_no,String cpny_id,String group_no) {
		// TODO Auto-generated method stub
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ITEM_NO", item_no);
		paramMap.put("CPNY_ID", cpny_id);
		return this.summaryItemDao.checkForItemParamUnique(paramMap);
	}
	
	
	
	/**
	 * 根据item_no查找和该项目已经mapping的记录删除掉
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateSummaryItemParamInfoAll(String item_no ) throws Exception{
		 
		return this.summaryItemDao.updateSummaryItemParamInfoAll(item_no);
	}

	/**
	 * 根据不同的参数来添加考勤代码（明细项目参数）
	 * @param request
	 * @param cpny_id
	 * @param item_no
	 * @return
	 */
	public int addSummaryItemParamInfo(HttpServletRequest request, String cpny_id, String item_no){
		String personId = request.getParameter("personId");
		LinkedHashMap paramMap = new LinkedHashMap();
		if(personId != null || "".equals(personId)){
			paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getAdminID());
		}else{
			paramMap.put("CREATED_BY",personId);
		}
		
		if (cpny_id != null && !"".equals(cpny_id)) {
			paramMap.put("CPNY_ID", cpny_id);
		}
		if (item_no != null && !"".equals(item_no)) {
			paramMap.put("ITEM_NO", item_no);
		}
		
		if (request.getParameter("ACTIVITY") == null) {
			paramMap.put("ACTIVITY", 1);
		}
		paramMap.put("MIN_UNIT", "0.5");
		paramMap.put("UNIT", "HOUR");
		try {
			summaryItemDao.addSummaryParamItemInfo(paramMap);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int updateSummaryItemParamInfo(HttpServletRequest request, String cpny_id, String item_no){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		if (cpny_id != null && !"".equals(cpny_id)) {
			paramMap.put("CPNY_ID", cpny_id);
		}
		if (item_no != null && !"".equals(item_no)) {
			paramMap.put("ITEM_NO", item_no);
		}
		paramMap.put("UPDATED_BY", admin.getAdminID());
		if (request.getParameter("ACTIVITY") == null) {
			paramMap.put("ACTIVITY", 1);
		}
		try {
			summaryItemDao.updateSummaryParamItem(paramMap);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}
