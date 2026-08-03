package com.ait.sys.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface NoticeManageSer {

	public List getNoticeInfo(HttpServletRequest request)throws Exception;
	
	public int getNoticeInfoCn(HttpServletRequest request) throws Exception;
	
	public Map getNoticeInfoById(HttpServletRequest request)throws Exception;
	
	public int insertNoticeInfo(HttpServletRequest request)throws Exception;
	
	public int updateNoticeInfo(HttpServletRequest request)throws Exception;
	
	public int delNoticeInfo(HttpServletRequest request)throws Exception;
	
	public String printClob(Object clob) throws Exception;
	
	public int delOverNoticeInfo(HttpServletRequest request) throws Exception;
	
	//上传附件
	public int uploadAtt(HttpServletRequest request) throws Exception ;
	
	public int uploadAttPhoto(HttpServletRequest request) throws Exception ;
}
