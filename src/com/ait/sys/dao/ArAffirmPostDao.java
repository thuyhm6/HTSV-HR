package com.ait.sys.dao;

import java.util.List;


/**
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName ArAffirmPostDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-13 上午10:18:09
 * @version 5.0
 * 
 */
public interface ArAffirmPostDao {
	
	@SuppressWarnings("unchecked")
	public List getArAffirmDutyList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getArAffirmPostList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArAffirmDutyList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getApplyDutyList(Object object);
	
	public int getArAffirmDutyListCnt(Object object);
	
	public void saveArAffirmPost(Object object) throws Exception;
	
	public void deleteArAffirmPostInfo(Object object) throws Exception;
	
	public Object getAffirmPostById(Object object) throws Exception;
	
	public void updateArAffirmPost(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getSortByParentNo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDutyListByCpnyId (Object object);
	
	public void saveArAffirmPostInside (Object object) throws Exception;
	
	public int validateArAffirmPostExist(Object object);
}
