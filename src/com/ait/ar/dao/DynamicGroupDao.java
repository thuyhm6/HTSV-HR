package com.ait.ar.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: DynamicGroupDao.java
 * @Description: implement Class DynamicGroupDaoImpl.java
 * @Create date: 2012-1-17 下午03:00:48
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface DynamicGroupDao {
	
	@SuppressWarnings("unchecked")
	public Object getDynamicGroup(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getDynamicGroupList(Object object);
	
	@SuppressWarnings("unchecked")
	public void addDynamicGroupInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateDynamicGroupInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void deleteDynamicGroupInfo(Object object)throws Exception;
	@SuppressWarnings("unchecked")
	public List getLoginUserDeptList(Object object);
	@SuppressWarnings("unchecked")
	public List getDynamicGroupInfoList(Object object);
    @SuppressWarnings("unchecked")
	public void addDynamicGroupPerson(List dynamicList)throws Exception;
    @SuppressWarnings("unchecked")
    public int checkDynamicGroupPerson(Object object);
    @SuppressWarnings("unchecked")
    public void deleteDynamicGroupPerson(Object object)throws Exception;
    @SuppressWarnings("unchecked")
	public List getDynamicGroupEmpList(Object object);
    @SuppressWarnings("unchecked")
	public int getDynamicGroupEmpCnt(Object object);
    @SuppressWarnings("unchecked")
	public List getDynamicGroupEmpList(Object object,int currentPage, int pageSize);
    @SuppressWarnings("unchecked")
	public List getDynamicGroupInfoList(Object object,int currentPage, int pageSize);
    @SuppressWarnings("unchecked")
	public int getDynamicGroupInfoCnt(Object object);
}
