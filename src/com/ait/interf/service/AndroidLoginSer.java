package com.ait.interf.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AndroidLoginSer {
	
	public String findUser(HttpServletRequest request);
	
	public Map getTips(HttpServletRequest request);
	
	public List getInfoNotAffirm(HttpServletRequest request) throws Exception;
	
	public List getInfoNotConfirm(HttpServletRequest request) throws Exception;
	
	public List getTransferOrderList(HttpServletRequest request) throws Exception;
}
