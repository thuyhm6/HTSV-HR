package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.PersonDynGroupDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PersonDynGroupDaoImpl.java
 * @Description:
 * @Create date: 2012-1-17 下午03:02:08
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PersonDynGroupDaoImpl extends SqlMapClientSupport implements PersonDynGroupDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	/**
	 * 取得动态组信息(get PersonDyn Group)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPersonDynGroup(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;

		List returnList = this.getPersonDynGroupList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有动态组列表(get PersonDynGroup List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getPersonDynGroupList(obj, -1, -1) ;
			
		return returnList ;
	}
	
	/**
	 * 取得所有动态组信息列表(get PersonDynGroup List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.PersonDynGroup.getPersonDynGroupList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.PersonDynGroup.getPersonDynGroupList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 插入动态组信息(add PersonDynGroup Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addPersonDynGroupInfo(Object obj) throws Exception {
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		
		this.insert("ar.PersonDynGroup.addPersonDynGroupInfo", object) ;
		
	}
	
	/**
	 * 更新动态组信息(update PersonDynGroup Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updatePersonDynGroupInfo(Object obj) throws Exception {
		
		this.syLanguageDao.updateSyGlobalName(obj);
		
		this.update("ar.PersonDynGroup.updatePersonDynGroupInfo", obj) ;
		
	}
	
	/**
	 * 删除动态组信息(delete PersonDynGroup Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void deletePersonDynGroupInfo(Object obj) throws Exception {
		
		this.syLanguageDao.deleteSyGlobalName(obj);
		this.delete("ar.PersonDynGroup.deletePersonDynGroupInfo", obj);
		this.delete("ar.PersonDynGroup.deletePersonDynGroupInfoPerson", obj) ;
		
	}

	@SuppressWarnings("unchecked")
	public List getLoginUserDeptList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.PersonDynGroup.getDeptList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 查看动态组人员信息(get PersonDynGroupInfo List)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.PersonDynGroup.getPersonDynGroupInfoList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.PersonDynGroup.getPersonDynGroupInfoList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 查看动态组人员信息(get PersonDynGroupInfo List)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupInfoList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getPersonDynGroupInfoList(obj, -1, -1) ;
			
		return returnList ;
	}
	
	/**
	 * 查询人员数量(get PersonDynGroupEmp count)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getPersonDynGroupEmpCnt(Object object) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.PersonDynGroup.getPersonDynGroupEmpCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 查询动态组人员数量(get PersonDynGroupInfo count)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getPersonDynGroupInfoCnt(Object object) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.PersonDynGroup.getPersonDynGroupInfoCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 查看人员列表(get PersonDynGroupEmp List)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupEmpList(Object object) {
		
		List returnList = new ArrayList() ;
		
		returnList = this.getPersonDynGroupEmpList(object, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 查看人员列表(get PersonDynGroupEmp List)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupEmpList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.PersonDynGroup.getPersonDynGroupEmpList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.PersonDynGroup.getPersonDynGroupEmpList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 添加动态组人员(add PersonDynGroup Person)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void addPersonDynGroupPerson(List PersonDynList)throws Exception {
		// TODO Auto-generated method stub
		this.insertForList("ar.PersonDynGroup.addPersonDynGroupPerson", PersonDynList);
	}

	/**
	 * 检查动态组人员是否存在(check PersonDynGroup Person)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int checkPersonDynGroupPerson(Object object) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.PersonDynGroup.checkPersonDynGroupPerson", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;	
	}

	/**
	 * 删除动态组人员(delete PersonDynGroup Person)
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deletePersonDynGroupPerson(Object object) throws Exception{
		this.deleteForList("ar.PersonDynGroup.deletePersonDynGroupPerson", (List)object) ;
	}

}
