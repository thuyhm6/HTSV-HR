package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName ArAffirmSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-9 下午06:10:33
 * @version 5.0
 * 
 */
public interface ArAffirmSer {

	@SuppressWarnings("unchecked")
	public List getArAffirmList(HttpServletRequest request,String type) ;
	
	public int getArAffirmListCnt(HttpServletRequest request,String type) ;
	
	public int saveArAffirmInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getApplyList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPostList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getDutyList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getDetailParamList(HttpServletRequest request);
	
	public int deleteArAffirmInfo(HttpServletRequest request);
	
	public int updateArAffirmInfo(HttpServletRequest request);
	
	public Object getLeaveApplyParam (HttpServletRequest request);
	
	public List getApplyTypeNoList(HttpServletRequest request);
	
	public List getApplyTypeCodeList(HttpServletRequest request);
	
	public List getArAffirmFinalList(HttpServletRequest request,String type);
	
	public int getArAffirmFinalListCnt(HttpServletRequest request,String type);
	
	public int saveArAffirmFinalInfo(HttpServletRequest request);
	
	public Object getLeaveApplyFinalParam (HttpServletRequest request);
	
	public int updateArAffirmFinalInfo(HttpServletRequest request) ;
	
	public int deleteArAffirmFinalInfo(HttpServletRequest request);
}
