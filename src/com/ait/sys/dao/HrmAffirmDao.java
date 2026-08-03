package com.ait.sys.dao;

import java.util.List;

/**
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName HrmAffirmDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-9 下午06:12:43
 * @version 5.0
 * 
 */
public interface HrmAffirmDao {
	
	@SuppressWarnings("unchecked")
	public List getHrmAffirmList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getHrmAffirmList(Object object);
	
	public int getHrmAffirmListCnt(Object object);
	
	public void saveHrmAffirmInfo(Object object,String[] postNoArr) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getApplyList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPostList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDutyList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDetailParamList(Object object);
	
	public void deleteHrmAffirmInfo(Object object) throws Exception;
	
	public Object getLeaveApplyParam(Object object); 
	
	public int validateExistsDutyApplyTypeCpnyId(Object object);
	
}
