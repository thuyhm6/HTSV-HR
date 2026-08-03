package com.ait.ess.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface PersonShiftSer {

	

	@SuppressWarnings("unchecked")
	public List viewPersonShiftList(HttpServletRequest request);

	public int viewPersonShiftListCnt(HttpServletRequest request);

	public List viewArShiftGroupList(HttpServletRequest request) ;
	
}
