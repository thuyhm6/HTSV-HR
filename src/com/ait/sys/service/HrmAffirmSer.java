package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface HrmAffirmSer {

	@SuppressWarnings("unchecked")
	public List getHrmAffirmList(HttpServletRequest request) ;
	
	public int getHrmAffirmListCnt(HttpServletRequest request) ;
	
	public int saveHrmAffirmInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getApplyList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPostList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getDutyList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getDetailParamList(HttpServletRequest request);
	
	public int deleteHrmAffirmInfo(HttpServletRequest request);
	
	public Object getLeaveApplyParam (HttpServletRequest request);
	
	public int validateExistsDutyApplyTypeCpnyId (HttpServletRequest request);

}
