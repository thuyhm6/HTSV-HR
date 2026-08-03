package com.ait.sys.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;


/**
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName ArAffirmPostSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-13 上午10:14:29
 * @version 5.0
 * 
 */
public interface ArAffirmPostSer {
	
	@SuppressWarnings("unchecked")
	public List getArAffirmPostList(HttpServletRequest request,Object dutyNo) ;
	
	@SuppressWarnings("unchecked")
	public List getArAffirmDutyList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getApplyDutyList(HttpServletRequest request);
	
	public int getArAffirmDutyListCnt(HttpServletRequest request) ;
	
	public int saveArAffirmPost(HttpServletRequest request) ;
	public int updateArAffirmPost(HttpServletRequest request) ;
	public int deleteArAffirmPostInfo(HttpServletRequest request) ;
	
	public Object getAffirmPostById(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getSortByParentNo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getDutyListByCpnyId (HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int validateArAffirmPostExist (HttpServletRequest request);
}
