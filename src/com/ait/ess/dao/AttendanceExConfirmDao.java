package com.ait.ess.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface AttendanceExConfirmDao {
	/**
	 * 考勤异常人事确认
	 * */
	@SuppressWarnings("unchecked")
	public List viewAttendanceExList(Object object,String target) throws Exception;
	
	/**
	 * 调用存储过程
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addAttendanceExJsonPro(Object object,String target)  throws Exception;
	
	/**
	 * 加班人事确认
	 * */
	@SuppressWarnings("unchecked")
	public List viewPOtApplyInfoConfirmList(Object object,String target) throws Exception;
	
	/**
	 * 调用存储过程
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addPOtApplyInfoJsonPro(Object object,String target)  throws Exception;
	
	/**
	 * 休假人事确认
	 * */
	@SuppressWarnings("unchecked")
	public List viewLeaveConfirmList(Object object,String target) throws Exception;
	
	/**
	 * 调用存储过程
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addLeaveJsonPro(Object object,String target)  throws Exception;
	
	/**
	 * 病假证明人事确认
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int sickLeaveProofConfirm(Object object,String target)  throws Exception;
}
