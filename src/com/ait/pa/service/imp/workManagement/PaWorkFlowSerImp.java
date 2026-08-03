package com.ait.pa.service.imp.workManagement;

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

import com.ait.pa.dao.PaPayScheduleDao;
import com.ait.pa.dao.PaProgressDao;
import com.ait.pa.dao.PaWorkFlowDao;
import com.ait.pa.service.salary.PaProgressSer;
import com.ait.pa.service.workManagement.PaPayScheduleSer;
import com.ait.pa.service.workManagement.PaWorkFlowSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class PaWorkFlowSerImp implements PaWorkFlowSer {

	Logger logger = Logger.getLogger(PaWorkFlowSerImp.class);
	
	@Autowired
	private PaWorkFlowDao paWorkFlowDao ;
	
	@SuppressWarnings("unchecked")
	public Object getPaWorkFlowInfoByScheduleNo(HttpServletRequest request,String scheduleNo) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PAY_SCHEDULE_NO", scheduleNo);
		
		return this.paWorkFlowDao.getPaWorkFlowInfoByScheduleNo(paramMap) ; 
	}
	/**
	 * 工资流程执行
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public String execPaWorkFlow(HttpServletRequest request) {
		String returnString = "" ;		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String[] processType = request.getParameterValues("processType") ;
			if( processType != null){
				for (int i = 0; i < processType.length; i++) {
					paramMap.put("type",processType[i]);
					returnString += this.paWorkFlowDao.execPaWorkFlow(paramMap) + "\n";
				}
			}else{
				if(request.getParameter("arLockFlag")!=null){
					paramMap.put("type",request.getParameter("arLockFlag"));
					returnString = this.paWorkFlowDao.execPaWorkFlow(paramMap) ;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return returnString;
	}
	
	@SuppressWarnings("unchecked")
	public int getPayObjNumByScheduleNo(String scheduleNo){
		int retrunInt = 0 ;
		retrunInt = this.paWorkFlowDao.getPayObjNumByScheduleNo(scheduleNo) ;
		return retrunInt ;
	}
	@SuppressWarnings("unchecked")
	public List getPaWorkFlowOperationRecordList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		retrunList = this.paWorkFlowDao.getPaWorkFlowOperationRecordList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getPaWorkFlowOperationRecordListCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		retrunInt = this.paWorkFlowDao.getPaWorkFlowOperationRecordListCnt(paramMap) ;
		
		return retrunInt ;
	}
	
}
