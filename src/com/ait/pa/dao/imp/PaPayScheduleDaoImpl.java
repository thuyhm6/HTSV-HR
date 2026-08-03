package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaPayScheduleDao;
import com.ait.pa.dao.PaProgressDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaPayScheduleDaoImpl extends SqlMapClientSupport implements PaPayScheduleDao {
	
	/**
	 * 取得工资支付计划信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPayScheduleList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.workManagement.getPayScheduleList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.workManagement.getPayScheduleList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得工资支付计划信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPayScheduleAllList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.workManagement.getPayScheduleAllList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得工资支付计划信息列表 带工资确认标示的
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPayScheduleAllWithPaConfirmList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.workManagement.getPayScheduleAllWithPaConfirmList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 取得工资支付计划信息总数
	 * @param List
	 * @return
	 */
	public int getPayScheduleCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.workManagement.getPayScheduleCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 保存工资支付计划信息(addPaPayScheduleInfo)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addPaPayScheduleInfo(Object obj)throws Exception {
		
		this.insert("pa.workManagement.addPaPayScheduleInfo", obj) ;
		this.insert("pa.workManagement.addPaWorkFlowInfo", obj) ;
		
	}
	/**
	 * 取得工资支付计划信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaPayScheduleInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPayScheduleList(obj,-1,-1) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	/**
	 * 更新工资支付计划信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updatePaPayScheduleInfo(Object obj)throws Exception {
		
		this.update("pa.workManagement.updatePaPayScheduleInfo", obj) ;
		
	}
	/**
	 * 确认或解除工资支付计划信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void confirmOrRelievePaySchedule(Object obj)throws Exception {
		
		this.update("pa.workManagement.confirmOrRelievePaySchedule", obj) ;
		
	}
	/**
	 * 删除工资支付计划信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void doDeletePayScheduleInfo(Object obj)throws Exception {
		
		this.update("pa.workManagement.doDeletePayScheduleInfo", obj) ;
		
	}
	
	
	/**
	 * 日别工资支付列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List dayPersonCountInfoList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.workManagement.getPayScheduleAllList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	

}
