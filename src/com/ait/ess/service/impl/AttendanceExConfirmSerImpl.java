package com.ait.ess.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.AttendanceExConfirmDao;
import com.ait.ess.service.AttendanceExConfirmSer;
import com.ait.web.util.ObjectBindUtil;
@Service
public class AttendanceExConfirmSerImpl implements AttendanceExConfirmSer{
	
	@Autowired
	private AttendanceExConfirmDao attendanceExConfirmDao;
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceExList(HttpServletRequest request, String target) throws Exception{
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				param.put("START_DATE",first);
				//获取当前月最后一天：
			}
		}
		return attendanceExConfirmDao.viewAttendanceExList(param,target);
	}
	
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addAttendanceExJsonPro(HttpServletRequest request,String target) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.attendanceExConfirmDao.addAttendanceExJsonPro(dataList,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewPOtApplyInfoConfirmList(HttpServletRequest request, String target) throws Exception{
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		/*String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				param.put("START_DATE",first);
			}
		}*/
		return attendanceExConfirmDao.viewPOtApplyInfoConfirmList(param,target);
	}
	
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addPOtApplyInfoJsonPro(HttpServletRequest request,String target) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.attendanceExConfirmDao.addPOtApplyInfoJsonPro(dataList,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewLeaveConfirmList(HttpServletRequest request, String target) throws Exception{
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		/*String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				param.put("START_DATE",first);
			}
		}*/
		return attendanceExConfirmDao.viewLeaveConfirmList(param,target);
	}
	
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addLeaveJsonPro(HttpServletRequest request,String target) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.attendanceExConfirmDao.addLeaveJsonPro(dataList,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int sickLeaveProofConfirm(HttpServletRequest request,String target) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.attendanceExConfirmDao.sickLeaveProofConfirm(dataList,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
}
