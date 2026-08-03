package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName PostSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:23:59
 * @version 5.0
 *
 */
public interface PostSer {
	
	public Object getPostItemInfo(HttpServletRequest request) ;
	
	public Object getPostGroupItemInfo(HttpServletRequest request) ;
	
	public Object getPostGradeItemInfo(HttpServletRequest request) ;
	
	public Object getDutyInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPostItemList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPostGroupItemList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPostGradeItemList(HttpServletRequest request) ;
	
	public int getPostItemCnt(HttpServletRequest request);
	
	public int addPostItemInfo(HttpServletRequest request);
	
	public int addPostGroupItemInfo(HttpServletRequest request);
	
	public int addPostGradeItemInfo(HttpServletRequest request);
	
	public int updatePostItemInfo(HttpServletRequest request);
	
	public int updatePostGroupItemInfo(HttpServletRequest request);
	
	public int updatePostGradeItemInfo(HttpServletRequest request);
	
	public int deletePostItemInfo(HttpServletRequest request);
	
	public int deletePostGroupItemInfo(HttpServletRequest request);
	
	public int deletePostGradeItemInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPostGradeLevelList(HttpServletRequest request,int parentNo);
	
	@SuppressWarnings("unchecked")
	public List getDutyList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getCheckDutyList(HttpServletRequest request,Object obj);
	
	@SuppressWarnings("unchecked")
	public List getDutyItemList(HttpServletRequest request);
	
	public int getDutyItemListCnt(HttpServletRequest request);
	
	public int saveDutyInfo(HttpServletRequest request);
	
	public int deleteDutyInfo(HttpServletRequest request);
	
	public int getPostGradeItemListCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPostItemSelectList(HttpServletRequest request);
    
	@SuppressWarnings("unchecked")
	public List getPositionList(HttpServletRequest request);
	
	public int getPositionListCnt(HttpServletRequest request);
	
	public int deletePositionInfo(HttpServletRequest request);
	
	public int savePositionInfo(HttpServletRequest request);
	
	public Object getPositionInfo(HttpServletRequest request) ;
	
}
