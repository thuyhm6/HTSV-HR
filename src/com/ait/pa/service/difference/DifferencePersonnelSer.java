package com.ait.pa.service.difference;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface DifferencePersonnelSer {
	
	@SuppressWarnings("unchecked")
	public List getDifferencePersonnelList(HttpServletRequest request) ;
	
	public int getDifferencePersonnelCnt(HttpServletRequest request);
	
	public int addDifferencePersonnelInfo(HttpServletRequest request);
	
	public int deleteDifferencePersonnelInfo(HttpServletRequest request);
}
