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

import com.ait.ar.dao.DynamicGroupDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: DynamicGroupDaoImpl.java
 * @Description:
 * @Create date: 2012-1-17 下午03:02:08
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class DynamicGroupDaoImpl extends SqlMapClientSupport implements DynamicGroupDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	/**
	 * 取得动态组信息(get Dynamic Group)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getDynamicGroup(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;

		List returnList = this.getDynamicGroupList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有动态组列表(get DynamicGroup List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDynamicGroupList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getDynamicGroupList(obj, -1, -1) ;
			
		return returnList ;
	}
	
	/**
	 * 取得所有动态组信息列表(get DynamicGroup List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDynamicGroupList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.dynamicGroup.getDynamicGroupList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.dynamicGroup.getDynamicGroupList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 插入动态组信息(add DynamicGroup Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addDynamicGroupInfo(Object obj) throws Exception {
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		
		this.insert("ar.dynamicGroup.addDynamicGroupInfo", object) ;
		
	}
	
	/**
	 * 更新动态组信息(update DynamicGroup Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updateDynamicGroupInfo(Object obj) throws Exception {
		
		this.syLanguageDao.updateSyGlobalName(obj);
		
		this.update("ar.dynamicGroup.updateDynamicGroupInfo", obj) ;
		
	}
	
	/**
	 * 删除动态组信息(delete DynamicGroup Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void deleteDynamicGroupInfo(Object obj) throws Exception {
		
		this.syLanguageDao.deleteSyGlobalName(obj);
		this.delete("ar.dynamicGroup.deleteDynamicGroupInfo", obj);
		this.delete("ar.dynamicGroup.deleteDynamicGroupInfoPerson", obj) ;
		
	}

	@SuppressWarnings("unchecked")
	public List getLoginUserDeptList(Object obj) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.dynamicGroup.getDeptList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 查看动态组人员信息(get DynamicGroupInfo List)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getDynamicGroupInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.dynamicGroup.getDynamicGroupInfoList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.dynamicGroup.getDynamicGroupInfoList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 查看动态组人员信息(get DynamicGroupInfo List)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getDynamicGroupInfoList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getDynamicGroupInfoList(obj, -1, -1) ;
			
		return returnList ;
	}
	
	/**
	 * 查询人员数量(get DynamicGroupEmp count)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getDynamicGroupEmpCnt(Object object) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.dynamicGroup.getDynamicGroupEmpCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 查询动态组人员数量(get DynamicGroupInfo count)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getDynamicGroupInfoCnt(Object object) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.dynamicGroup.getDynamicGroupInfoCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 查看人员列表(get DynamicGroupEmp List)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getDynamicGroupEmpList(Object object) {
		
		List returnList = new ArrayList() ;
		
		returnList = this.getDynamicGroupEmpList(object, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 查看人员列表(get DynamicGroupEmp List)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getDynamicGroupEmpList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.dynamicGroup.getDynamicGroupEmpList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.dynamicGroup.getDynamicGroupEmpList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 添加动态组人员(add DynamicGroup Person)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void addDynamicGroupPerson(List dynamicList)throws Exception {
		// TODO Auto-generated method stub
		this.insertForList("ar.dynamicGroup.addDynamicGroupPerson", dynamicList);
	}

	/**
	 * 检查动态组人员是否存在(check DynamicGroup Person)
	 * @param Object
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int checkDynamicGroupPerson(Object object) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.dynamicGroup.checkDynamicGroupPerson", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;	
	}

	/**
	 * 删除动态组人员(delete DynamicGroup Person)
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteDynamicGroupPerson(Object object) throws Exception{
		// TODO Auto-generated method stub
		this.deleteForList("ar.dynamicGroup.deleteDynamicGroupPerson", (List)object) ;
	}

}
