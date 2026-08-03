package com.ait.affirm.service;

import java.util.LinkedHashMap;
import java.util.List;

public interface AffirmInfoToLGEPSer {
	
	
	@SuppressWarnings("unchecked") 
	public void crateAffirm(LinkedHashMap paramMap);
	
	public void affirm(LinkedHashMap paramMap);

	public void check(LinkedHashMap paHashMap);
	public void crateCheck(LinkedHashMap paHashMap);
	
	/**
	 * 删除审批信息：
	 * 	       未审批的申请删除后需要向LGEP发送删除命令。
	 */
	public void deleteAffirm(LinkedHashMap paramMap);
	
	/**
	 * 创建委任：
	 * 	1、APROVAL_EMPID:当前审批人社号
	 *	   DELEGATE_EMPID：替换人社号
	 *	   START_DATE:创建时间 yyyy-mm-dd
	 */
	public void crateDelegate(LinkedHashMap paramMap) ;
	/**
	 * 取消委任：
	 * 	1、APROVAL_EMPID:当前审批人社号
	 *	   DELEGATE_EMPID：替换人社号
	 *	   START_DATE:创建时间 yyyy-mm-dd
	 */
	public void deleteDelegate(LinkedHashMap paramMap);
	public void affirmWithReqDetail(LinkedHashMap paramMap, List paramList);
}
