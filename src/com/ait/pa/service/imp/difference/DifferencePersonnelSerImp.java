package com.ait.pa.service.imp.difference;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.DifferencePersonnelDao;
import com.ait.pa.dao.PaHistoryDao;
import com.ait.pa.service.difference.DifferencePersonnelSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Service
public class DifferencePersonnelSerImp implements DifferencePersonnelSer {

	Logger logger = Logger.getLogger(DifferencePersonnelSerImp.class);
	
	@Autowired
	private DifferencePersonnelDao differencePersonnelDao;
	
	@Autowired
	private PaHistoryDao paHistoryDao;
	
	@SuppressWarnings("unchecked")
	public List getDifferencePersonnelList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = this.setGetDifferencePersonnelParam(request) ;

		if (paramMap.get("page") != null && paramMap.get("pagesize") != null){
			retrunList = 
				differencePersonnelDao.getDifferencePersonnelList(paramMap , 
							NumberUtils.parseNumber(ObjectUtils.toString(paramMap.get("page")), Integer.class), 
							NumberUtils.parseNumber(ObjectUtils.toString(paramMap.get("pagesize")), Integer.class) 
						) ;
		}
		else{
			retrunList = differencePersonnelDao.getDifferencePersonnelList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getDifferencePersonnelCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		// 页面提交数据
		LinkedHashMap paramMap = this.setGetDifferencePersonnelParam(request) ;

		retrunInt = differencePersonnelDao.getDifferencePersonnelCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	private LinkedHashMap setGetDifferencePersonnelParam(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		// 判读PA_MONTH参数是否为空,为空赋予当前月
		String paMonth = ObjectUtils.toString(paramMap.get("PA_MONTH")) ;
		if(paMonth.length() == 0){
			paMonth = DateUtil.getCurrentMonthStr() ;
			paramMap.put("PA_MONTH", paMonth) ;
		}
		
		// 判断是否存在工资历史信息
		int checkPaHistroyFlag = this.paHistoryDao.getCheckPaHistoryFlag(paramMap) ;
		if (checkPaHistroyFlag == 0){
			paramMap.put("DATA_SOURCE", "HR_EMPLOYEE") ;
		}
		else{
			paramMap.put("DATA_SOURCE", "PA_HISTORY") ;
		}
		
		return paramMap ;
	}

	@Override
	public int addDifferencePersonnelInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 附加信息
		LinkedHashMap<String, Object> appendMap = new LinkedHashMap<String, Object>() ;;
		appendMap.put("CREATED_BY", admin.getAdminID()) ;
		
		// 页面提交的JSON信息
		String jsonString = request.getParameter("jsonData") ;

		List<LinkedHashMap<String, Object>> insertDifferencePersonnelList = ObjectBindUtil.getRequestJsonData(jsonString, appendMap) ;
		
		this.differencePersonnelDao.addDifferencePersonnelInfo(insertDifferencePersonnelList) ;
		
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int deleteDifferencePersonnelInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		this.differencePersonnelDao.deleteDifferencePersonnelInfo(paramMap) ;
		
		return 0;
	}
	
}
