package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PersonDynGroupSer.java
 * @Description: implement Class PersonDynGroupSerImp.java
 * @Create date: 2012-1-17 下午02:59:11
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface PersonDynGroupSer {

	@SuppressWarnings("unchecked")
	public Object getPersonDynGroup(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addPersonDynGroupInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updatePersonDynGroupInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deletePersonDynGroupInfo(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getDeptList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupInfoList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int addPersonDynGroupPerson(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int deletePersonDynGroupPerson(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupEmpList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int getPersonDynGroupEmpCnt(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int getPersonDynGroupInfoCnt(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getPersonDynGroup1List(HttpServletRequest request) ;
}
