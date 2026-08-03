package com.ait.sys.dao;

import java.sql.SQLException;
import java.util.List;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName PostDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:23:04
 * @version 5.0
 *
 */
public interface PostDao {
	
	public Object getPost(Object obj) ;
	
	public Object getPostGroup(Object obj) ;
	
	public Object getDutyInfo(Object obj);
	
	public Object getPostGrade(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getPostList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPostGroupList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPostGradeList(Object object);
	
	public int getPostListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPostList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPostGroupList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPostGradeList(Object object, int currentPage, int pageSize);
	
	public void addPostInfo(Object object) throws Exception;
	
	public int addPostGroupInfo(Object object) throws SQLException;
	
	public void addPostGradeInfo(Object object) throws Exception;
	
	public void updatePostInfo(Object object) throws Exception;
	
	public int updatePostGroupInfo(Object object);
	
	public int updatePostGradeInfo(Object object) throws Exception;
	
	public void deletePostInfo(Object object) throws Exception;
	
	public int deletePostGroupInfo(Object object);
	
	public int deletePostGradeInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getPostGradeLevelList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDutyList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getCheckDutyList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDutyItemList(Object object);
	
	public int getDutyItemListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDutyItemList(Object object, int currentPage, int pageSize);
	
	public void saveDutyInfo(Object object)throws Exception;
	
	public void deleteDutyInfo(Object object)throws Exception;
	
	public int getPostGradeItemListCnt(Object object);
	
	public void deletePositionInfo(Object object)throws Exception;
	
	public void savePositionInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getPositionList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPositionList(Object object);
	
	public int getPositionListCnt(Object object);
	
	public Object getPositionInfo(Object object);
	public List getPositionInfoByPostGradeNo(Object object);
	
}
