package com.ait.ar.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PersonDynGroupDao.java
 * @Description: implement Class PersonDynGroupDaoImpl.java
 * @Create date: 2012-1-17 下午03:00:48
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface PersonDynGroupDao {
	
	@SuppressWarnings("unchecked")
	public Object getPersonDynGroup(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupList(Object object);
	
	@SuppressWarnings("unchecked")
	public void addPersonDynGroupInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updatePersonDynGroupInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void deletePersonDynGroupInfo(Object object)throws Exception;
	@SuppressWarnings("unchecked")
	public List getLoginUserDeptList(Object object);
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupInfoList(Object object);
    @SuppressWarnings("unchecked")
	public void addPersonDynGroupPerson(List PersonDynList)throws Exception;
    @SuppressWarnings("unchecked")
    public int checkPersonDynGroupPerson(Object object);
    @SuppressWarnings("unchecked")
    public void deletePersonDynGroupPerson(Object object)throws Exception;
    @SuppressWarnings("unchecked")
	public List getPersonDynGroupEmpList(Object object);
    @SuppressWarnings("unchecked")
	public int getPersonDynGroupEmpCnt(Object object);
    @SuppressWarnings("unchecked")
	public List getPersonDynGroupEmpList(Object object,int currentPage, int pageSize);
    @SuppressWarnings("unchecked")
	public List getPersonDynGroupInfoList(Object object,int currentPage, int pageSize);
    @SuppressWarnings("unchecked")
	public int getPersonDynGroupInfoCnt(Object object);
}
