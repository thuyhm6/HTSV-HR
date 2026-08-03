package com.ait.sys.dao;

import java.util.List;

/**
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName ArAffirmDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-9 下午06:12:43
 * @version 5.0
 * 
 */
public interface ArAffirmDao {
	
	@SuppressWarnings("unchecked")
	public List getArAffirmList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getArAffirmList(Object object);
	
	public int getArAffirmListCnt(Object object);
	
	public void saveArAffirmInfo(Object object,String[] postNoArr) throws Exception;
	
	public void saveArAffirmInfo(Object object) throws Exception;
	
	public void updateArAffirmInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getApplyList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPostList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDutyList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDetailParamList(Object object);
	
	public void deleteArAffirmInfo(Object object) throws Exception;
	
	public Object getLeaveApplyParam(Object object); 
	
	public List getApplyTypeNoList(Object object);
	
	public List getApplyTypeCodeList(Object object);
	
	public List getArAffirmFinalList(Object object);
	
	public List getArAffirmFinalList(Object obj, int currentPage, int pageSize);
	
	public int getArAffirmFinalListCnt(Object object);
	
	public void saveArAffirmFinalInfo(Object object) throws Exception;
	
	public Object getLeaveApplyFinalParam(Object object);
	
	public void updateArAffirmFinalInfo(Object object) throws Exception;
	
	public void deleteArAffirmFinalInfo(Object object) throws Exception;
}
