package com.ait.is.service;

import javax.servlet.http.HttpServletRequest;

import java.util.LinkedHashMap;
import java.util.List;

public interface CompanyMaintainSer {

	public int addIsCompanyInfo(HttpServletRequest request) throws Exception;
	
	public int updateIsCompanyInfo(HttpServletRequest request) throws Exception;
	
	public int deleteIsCompanyInfo(HttpServletRequest request) throws Exception;
	
	public List getIsCorpInfo(HttpServletRequest request) throws Exception;
	
	public int getIsCorpCnt(HttpServletRequest request) throws Exception;
	
	public Object getIsCorpInfoByNo(HttpServletRequest request) throws Exception;
}
