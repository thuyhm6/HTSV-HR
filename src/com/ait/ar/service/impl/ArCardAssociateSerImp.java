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

import com.ait.ar.dao.ArCardAssociateDao;
import com.ait.ar.service.ArCardAssociateSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArCardAssociateSerImp.java
 * @Description:
 * @Create date: 2012-6-4 上午11:32:00
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ArCardAssociateSerImp implements ArCardAssociateSer {

	Logger logger = Logger.getLogger(ArCardAssociateSerImp.class);
	
	@Autowired
	private ArCardAssociateDao arCardAssociateDao;
	
	/**
	 * 查看卡号列表(get AttendanceKeeper List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getCardAssociateList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				arCardAssociateDao.getCardAssociateList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = arCardAssociateDao.getCardAssociateList(paramMap) ;
		}
		
		return retrunList;
	}
	
	/**
	 * 取考勤员数量(get CardAssociate Cnt)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getCardAssociateCnt(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		return arCardAssociateDao.getCardAssociateCnt(paramMap) ;
	}
	
	/**
	 * 修改保存(update CardAssociate Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int updateCardAssociateInfo(HttpServletRequest request){
		
		// 页面参数
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> arDetailInfoList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		
		List<LinkedHashMap<String, Object>> paraList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		if(arDetailInfoList != null){
			for(LinkedHashMap lmap : arDetailInfoList){
				lmap.put("CPNY_ID", admin.getCpnyId());
				lmap.put("CREATE_BY", admin.getPersonId());
				paraList.add(lmap);
			}
		}
		
		try {
			
			arCardAssociateDao.updateCardAssociateInfo(paraList) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	public String checkCardValidity(HttpServletRequest request){
		
		String ruturnStr = "";
		// 页面参数
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> arDetailInfoList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		
		List<LinkedHashMap<String, Object>> paraList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		if(arDetailInfoList != null){
			for(LinkedHashMap lmap : arDetailInfoList){
				lmap.put("CPNY_ID", admin.getCpnyId());
				paraList.add(lmap);
			}
		}
		
		try {
			
			ruturnStr = arCardAssociateDao.checkCardValidity(paraList) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ruturnStr;
	}
}
