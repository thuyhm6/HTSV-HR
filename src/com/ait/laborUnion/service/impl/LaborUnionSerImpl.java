package com.ait.laborUnion.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.laborUnion.dao.LaborUnionInfoDao;
import com.ait.laborUnion.service.LaborUnionSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
@Service
public class LaborUnionSerImpl implements LaborUnionSer{
	@Autowired
	private LaborUnionInfoDao laborUnionInfoDao;
	
	/**
	 * 工会查询 (Contract inquires)
	 * @param request
	 * @return List
	 */
	
	@SuppressWarnings("unchecked")
	public Object getLaborUnionSum(Map paramMap) {
		Object obj1 = null;		
		obj1 = laborUnionInfoDao.getLaborUnionSumForSearch(paramMap) ;
		return obj1 ;
		
	}
	
	
	/**
	 * 上月工会查询 (Contract inquires)
	 * @param request
	 * @return List
	 */
	
	@SuppressWarnings("unchecked")
	public Object getLaborUnionSum2(Map paramMap) {
		Object obj1 = null;		
		obj1 = laborUnionInfoDao.getLaborUnionSumForSearch2(paramMap) ;
		return obj1 ;
		
	}
	
	/**
	 * 工会EXCEL (Contract inquires)
	 * @param request
	 * @return List
	 */
	
	@SuppressWarnings("unchecked")
	public Object getLaborUnionExcel(Map paramMap) {
		Object obj1 = null;		
		obj1 = laborUnionInfoDao.getLaborUnionExcel(paramMap) ;
		return obj1 ;
		
	}
	
	
	
	
	/**
	 * 学生查询CNC (Contract inquires)
	 * @param request
	 * @return List
	 */
	
	@SuppressWarnings("unchecked")
	public int getLaborUnionCnt(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		return laborUnionInfoDao.getLaborUnionCnt(paramMap);
		 
		
	}
}
