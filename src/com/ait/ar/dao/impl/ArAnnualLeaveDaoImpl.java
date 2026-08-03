package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArAnnualLeaveDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAnnualLeaveDaoImpl.java
 * @Description:
 * @Create date: 2012-2-14 下午12:58:46
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ArAnnualLeaveDaoImpl extends SqlMapClientSupport implements ArAnnualLeaveDao {
	
	/**
	 * 取得个人年假信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getArAnnualLeaveInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;

		List returnList = this.getArAnnualLeaveList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有个人年假列表(get ArAnnualLeave List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getArAnnualLeaveList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getArAnnualLeaveList(obj, -1, -1) ; 
		
		return returnList ;
	}
	
	/**
	 * 取得个人年假计数(get ArAnnualLeave Cnt)
	 * @param Object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getArAnnualLeaveCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.annualLeave.getArAnnualLeaveCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 取得所有个人年假列表(get ArAnnualLeave List)
	 * @param obj
	 * @param int
	 * @param int
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getArAnnualLeaveList(Object obj, int currentPage, int pageSize) {
		
		List returnList = new ArrayList() ;
		
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.annualLeave.getArAnnualLeaveList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.annualLeave.getArAnnualLeaveList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 删除个人年假信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void deleteArAnnualLeave(Object object) throws Exception {
		
		this.deleteForList("ar.annualLeave.deleteArAnnualLeave", (List)object) ;
	}
	
	/**
	 * 修改个人年假信息(update ArAnnualLeave Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void updateArAnnualLeave(Object object) throws Exception {
		
		this.update("ar.annualLeave.updateArAnnualLeave", object) ;
	}
	
	/**
	 * 检查个人年假添加信息(check AddArAnnualLeave Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int checkAddArAnnualLeaveInfo(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.annualLeave.checkAddArAnnualLeaveInfo", object), "0"), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 插入个人年假信息(add ArAnnualLeave)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addArAnnualLeave(Object object)  throws Exception {
		
		this.insert("ar.annualLeave.addArAnnualLeave", object) ;
	}
	
	/**
	 * 初始化个人年假(create ArAnnualLeave Info)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void createArAnnualLeaveInfo(Object object) throws Exception {
		
		this.insert("ar.annualLeave.createArAnnualLeaveInfo", object) ;
	}
	
	/**
	 * 每天自动初始化个人年假(create ArAnnualLeave Info auto)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void createArAnnualLeaveAuto(Object object) throws Exception {
		
		this.insert("ar.annualLeave.createArAnnualLeaveAuto", object) ;
	}
	
	/**
	 * 获得人员列表(get EmpForSupervisor List)
	 * @param Map
	 * @param int
	 * @param int
	 * @return List
	 * @throws 
	 */
	@Override
	public List getEmpForSupervisorList(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.annualLeave.getEmpForSupervisorList", paramMap, currentPage, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获得人员列表(get EmpForSupervisor Cnt)
	 * @param Map
	 * @return int
	 * @throws 
	 */
	@Override
	public int getEmpForSupervisorCnt(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.annualLeave.getEmpForSupervisorCnt", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return returnInt;
		}
		
		return returnInt ;
	}
	
	/**
	 * 添加生成年假日志信息时的IP地址
	 * @param object
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void addPortalIp(Object obj) throws Exception {
		this.insert("ar.annualLeave.insertAnnualLeaveIp", obj);
	}
}
