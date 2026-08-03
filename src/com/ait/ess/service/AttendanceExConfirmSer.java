package com.ait.ess.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface AttendanceExConfirmSer {
	/**
	 * 考勤异常人事确认
	 * */
	@SuppressWarnings("unchecked")
	public List viewAttendanceExList(HttpServletRequest request,String target) throws Exception;
	
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addAttendanceExJsonPro(HttpServletRequest request,String target) ;
	
	/**
	 * 加班人事确认
	 * */
	@SuppressWarnings("unchecked")
	public List viewPOtApplyInfoConfirmList(HttpServletRequest request,String target) throws Exception;
	
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addPOtApplyInfoJsonPro(HttpServletRequest request,String target) ;
	
	/**
	 * 休假人事确认
	 * */
	@SuppressWarnings("unchecked")
	public List viewLeaveConfirmList(HttpServletRequest request,String target) throws Exception;
	
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addLeaveJsonPro(HttpServletRequest request,String target) ;
	
	/**
	 * 病假证明人事确认
	 */
	@SuppressWarnings("unchecked")
	public int sickLeaveProofConfirm(HttpServletRequest request,String target) ;
}
