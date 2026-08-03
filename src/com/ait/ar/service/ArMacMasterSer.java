package com.ait.ar.service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArMacMasterSer.java
 * @Description: implement Class ArMacMasterSerImp.java
 * @Create date: 2013-8-26 下午15:49:39
 * @Create by: lufeng(lufeng@ait.net.cn)
 * @version 5.1
 */
public interface ArMacMasterSer {
	/*刷卡机--人事信息--接口*/
	@SuppressWarnings("unchecked")
    public List getArHrmMasterList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
    public List getArHrmMasterExcelList(HttpServletRequest request);
	
	public int getArHrmMasterListCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public String addHrmMasterInfo(LinkedHashMap paramMap);
	
	@SuppressWarnings("unchecked")
	public String addArMacLogInfo(LinkedHashMap paramMap);
	
	/*刷卡机--刷卡数据信息--接口*/
	@SuppressWarnings("unchecked")
    public List getArCardMacNoList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
    public List getArCardRecordList(HttpServletRequest request);
	
	public int getArCardRecordListCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
    public List getArCardRecordExcelList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public String addCardRecordInfo(LinkedHashMap paramMap);
	
	/*刷卡机--部门信息--接口*/
	@SuppressWarnings("unchecked")
	public String addDeptMasterInfo(LinkedHashMap paramMap);
	
	/*刷卡机--指纹信息--接口*/
	@SuppressWarnings("unchecked")
    public List getArFingerPrintList(HttpServletRequest request);
	
	public int getArFingerPrintListCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
    public List getArFingerPrintExcelList(HttpServletRequest request);
}