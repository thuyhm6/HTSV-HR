package com.ait.sys.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface NoticeManageDAO {

	public List getNoticeInfo(Object parameterObject)throws Exception;
	
	public List getNoticeInfo(Object parameterObject,int pageNum,int pageSize) throws Exception;
	
	public int getNoticeInfoCn(Object parameterObject) throws Exception;
	
	public Map getNoticeInfoById(Object parameterObject)throws Exception;
	
	public int insertNoticeInfo(Object parameterObject);
	
	public int updateNoticeInfo(Object parameterObject);
	
	public int delNoticeInfo(List list);
	
	public int delOverNoticeInfo(Object parameterObject);
	
	public int uploadAtt(Object parameterObject);
	
	public int uploadAttPhoto(Object parameterObject);
}
