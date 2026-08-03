package com.ait.ar.service.impl;

import java.sql.SQLException;
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

import com.ait.ar.dao.ArMacMasterDao;
import com.ait.ar.service.ArMacMasterSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArMacMasterhSerImp.java
 * @Description:
 * @Create date: 2013-8-26 下午15:49:39
 * @Create by: lufeng(lufeng@ait.net.cn)
 * @version 5.1
 */
@Service
public class ArMacMasterSerImp implements ArMacMasterSer {
Logger logger = Logger.getLogger(ArMacMasterSerImp.class);
	
	@Autowired
	private ArMacMasterDao arMacMasterDao;
	
	/*刷卡机--人事信息--接口*/
	/**
	 * 考勤机传送人事Master信息，传送信息查询页面
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArHrmMasterList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("supervisor", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId().toString());
		//默认查询未读取过的人事数据
		if(paramMap.get("MAS_SEND_TYPE")==null || "".equals(paramMap.get("MAS_SEND_TYPE").toString())){
			return retrunList ;
		}
		if(UiUtil.getPageNum(request) > 0){
			retrunList = 
				arMacMasterDao.getArHrmMasterList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}else{
			retrunList = arMacMasterDao.getArHrmMasterList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 考勤机传送人事Master信息，传送信息查询页面，查询数量
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getArHrmMasterListCnt(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;

		paramMap.put("supervisor", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId().toString());
		//默认查询未读取过的人事数据
		if(paramMap.get("MAS_SEND_TYPE")==null || "".equals(paramMap.get("MAS_SEND_TYPE").toString())){
			return 0;
		}
		return arMacMasterDao.getArHrmMasterListCnt(paramMap);
	}
	
	/**
	 * 考勤机传送人事Master信息，传送信息查询页面--导出信息用
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArHrmMasterExcelList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//默认查询未读取过的人事数据
		if(paramMap.get("MAS_SEND_TYPE")==null || "".equals(paramMap.get("MAS_SEND_TYPE").toString())){
			return retrunList ;
		}
		retrunList = arMacMasterDao.getArHrmMasterExcelList(paramMap) ;
		
		return retrunList ;
	}
	
	/**
	 * @throws SQLException 
	 * 考勤机传送人事Master信息，传送过程
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String addHrmMasterInfo(LinkedHashMap paramMap){
		List hrmMasterList = new ArrayList();
		hrmMasterList = arMacMasterDao.getArHrmMasterPreExcelList(paramMap) ;
		
		if(hrmMasterList!=null && hrmMasterList.size()>0){
			try {
				arMacMasterDao.insertHrmMasterInfo(hrmMasterList) ;
			} catch (SQLException e) {
				e.printStackTrace();
				return "N";
			}
		}
		return "Y";
	}
	
	/**
	 * @throws SQLException 
	 * 考勤机传送部门department信息，传送过程
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String addDeptMasterInfo(LinkedHashMap paramMap){
		List deptMasterList = new ArrayList();
		deptMasterList = arMacMasterDao.getArDeptMasterPreExcelList(paramMap) ;
		
		if(deptMasterList!=null && deptMasterList.size()>0){
			try {
				arMacMasterDao.insertDeptMasterInfo(deptMasterList) ;
			} catch (SQLException e) {
				e.printStackTrace();
				return "N";
			}
		}
		return "Y";
	}
	
	/**
	 * @throws SQLException 
	 * 考勤机接口部分--插入接口日志
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String addArMacLogInfo(LinkedHashMap paramMap){
		try {
			arMacMasterDao.addArMacLogInfo(paramMap) ;
		} catch (SQLException e) {
			e.printStackTrace();
			return "E";//考勤机接口日志更新异常（失败）！
		}
		return "Y";
	}
	
	/*刷卡机--刷卡数据信息--接口*/
	/**
	 * 考勤机--获取考勤机编号（get the ar card mac no ）信息查询页面
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArCardMacNoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("supervisor", admin.getPersonId());
		//paramMap.put("CPNY_ID", admin.getCpnyId().toString());
		retrunList = arMacMasterDao.getArCardMacNoList(paramMap) ;
		
		
		return retrunList ;
	}
	
	/**
	 * 考勤机--读取刷卡数据（get the ar card data from mac）信息查询页面
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArCardRecordList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("supervisor", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId().toString());
		//默认查询未读取过的刷卡数据
		if(paramMap.get("READ_TYPE")==null || "".equals(paramMap.get("READ_TYPE").toString())){
			paramMap.put("READ_TYPE", "N");
		}
		if(UiUtil.getPageNum(request) > 0){
			retrunList = 
				arMacMasterDao.getArCardRecordList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}else{
			retrunList = arMacMasterDao.getArCardRecordList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 考勤机--读取刷卡数据（get the ar card data from mac），查询数量
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getArCardRecordListCnt(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;

		paramMap.put("supervisor", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId().toString());
		//默认查询未读取过的刷卡数据
		if(paramMap.get("READ_TYPE")==null || "".equals(paramMap.get("READ_TYPE").toString())){
			paramMap.put("READ_TYPE", "N");
		}
		return arMacMasterDao.getArCardRecordListCnt(paramMap);
	}
	
	/**
	 * 考勤机--读取刷卡数据（get the ar card data from mac）--导出信息用
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArCardRecordExcelList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//默认查询未读取过的刷卡数据
		if(paramMap.get("READ_TYPE")==null || "".equals(paramMap.get("READ_TYPE").toString())){
			paramMap.put("READ_TYPE", "N");
		}
		retrunList = arMacMasterDao.getArCardRecordExcelList(paramMap) ;
		
		return retrunList ;
	}
	
	/**
	 * @throws SQLException 
	 * 考勤机--读取刷卡数据（get the ar card data from mac），传送过程
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String addCardRecordInfo(LinkedHashMap paramMap){
		List cardRecordList = new ArrayList();
		cardRecordList = arMacMasterDao.getArCardRecordExcelList(paramMap) ;
		
		if(cardRecordList!=null && cardRecordList.size()>0){
			try {
				arMacMasterDao.insertCardRecordInfo(cardRecordList) ;
			} catch (SQLException e) {
				e.printStackTrace();
				return "N";
			}
		}
		return "Y";
	}
	
	/**
	 * 考勤机--查询员工指纹编号（get the ar mac hand no data for mac）信息查询页面
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArFingerPrintList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String dataFlag = paramMap.get("DATA_FLAG")!=null?paramMap.get("DATA_FLAG").toString():"";
		//第一次打开页面(点击菜单打开时)不查询数据，点击查询按钮时才查询
		if(dataFlag==null || !"1".equals(dataFlag)){
			return returnList;
		}
		paramMap.put("supervisor", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId().toString());
		if(UiUtil.getPageNum(request) > 0){
			returnList = 
				arMacMasterDao.getArFingerPrintList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}else{
			returnList = arMacMasterDao.getArFingerPrintList(paramMap) ;
		}
		
		return returnList ;
	}
	
	/**
	 * 考勤机--查询员工指纹编号（get the ar mac hand no data for mac），查询数量
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getArFingerPrintListCnt(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String dataFlag = paramMap.get("DATA_FLAG")!=null?paramMap.get("DATA_FLAG").toString():"";
		//第一次打开页面(点击菜单打开时)不查询数据，点击查询按钮时才查询
		if(dataFlag==null || !"1".equals(dataFlag)){
			return 0;
		}
		paramMap.put("supervisor", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId().toString());
		return arMacMasterDao.getArFingerPrintListCnt(paramMap);
	}
	
	/**
	 * 考勤机--查询员工指纹编号（get the ar mac hand no data for mac），导出用
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArFingerPrintExcelList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//默认查询未读取过的刷卡数据
		if(paramMap.get("READ_TYPE")==null || "".equals(paramMap.get("READ_TYPE").toString())){
			paramMap.put("READ_TYPE", "N");
		}
		retrunList = arMacMasterDao.getArFingerPrintExcelList(paramMap) ;
		
		return retrunList ;
	}
}
