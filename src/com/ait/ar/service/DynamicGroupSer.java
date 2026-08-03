package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: DynamicGroupSer.java
 * @Description: implement Class DynamicGroupSerImp.java
 * @Create date: 2012-1-17 下午02:59:11
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface DynamicGroupSer {

	@SuppressWarnings("unchecked")
	public Object getDynamicGroup(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getDynamicGroupList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addDynamicGroupInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateDynamicGroupInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deleteDynamicGroupInfo(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getDeptList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getDynamicGroupInfoList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int addDynamicGroupPerson(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int deleteDynamicGroupPerson(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getDynamicGroupEmpList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int getDynamicGroupEmpCnt(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int getDynamicGroupInfoCnt(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getDynamicGroup1List(HttpServletRequest request) ;
}
