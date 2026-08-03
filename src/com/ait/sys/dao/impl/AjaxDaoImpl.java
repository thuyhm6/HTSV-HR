package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ait.sys.dao.AjaxDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AjaxDaoImpl.java
 * @Description: implement Class AjaxDao.java
 * @Create date: Jan 16, 2012 3:36:16 PM
 * @author : hanzhe(hanzhe@ait.net.cn)
 * @version 5.1
 */
@Repository
public class AjaxDaoImpl extends SqlMapClientSupport implements AjaxDao {
	 /**
  	 * 查询所有公司信息(query all the company info)
  	 * @param request
  	 * @return list
  	 */
     @SuppressWarnings("unchecked")
	public List getCompanyInfoList(Object obj){
   		List returnList = new ArrayList() ;
   		try {
   			returnList = this.queryForList("sys.ajax.getCompanyInfoList", obj);
   		} catch (SQLException e) {			
   			e.printStackTrace();
   		}
   		
   		return returnList ;
   	 }
     
     /**
   	 * 根据法人Cpny_id查询工资月份信息（query the pa_month info list by cpny_id）
   	 * @param request
   	 * @return list
   	 */
      @SuppressWarnings("unchecked")
 	public List getPaMonthList(Object obj){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.ajax.getPaMonthList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	 }
      
     /**
 	  * 根据法人Cpny_id、工资月份查询薪资发放日期（query the GIVE_DATE info list by cpny_id 、pa_month）
 	  * @param request
 	  * @return list
 	  */
    @SuppressWarnings("unchecked")
   	public List getPaGiveDateList(Object obj){
  		List returnList = new ArrayList() ;
  		try {
  			returnList = this.queryForList("sys.ajax.getPaGiveDateList", obj);
  		} catch (SQLException e) {			
  			e.printStackTrace();
  		}
  		
  		return returnList ;
  	 }
      
      /**
    	 * 根据法人Cpny_id查询工资项目信息（query the pa item info list by cpny_id）
    	 * @param request
    	 * @return list
    	 */
       @SuppressWarnings("unchecked")
  	public List getPaItemInfoList(Object obj){
 		List returnList = new ArrayList() ;
 		try {
 			returnList = this.queryForList("sys.ajax.getPaItemInfoList", obj);
 		} catch (SQLException e) {			
 			e.printStackTrace();
 		}
 		
 		return returnList ;
 	 }
	
     /**
  	 * 根据公司ID查询该公司的部门树（query the dept tree by the company id ）
  	 * @param request
  	 * @return list
  	 */
     @SuppressWarnings("unchecked")
	public List getDeptInfoTree(Object obj){
   		List returnList = new ArrayList() ;
   		try {
   			returnList = this.queryForList("sys.ajax.getDeptInfoTree", obj);
   		} catch (SQLException e) {			
   			e.printStackTrace();
   		}
   		
   		return returnList ;
   	 }
     
     /**
  	 * 根据传入的年月输出该月所以的 *月*日格式
  	 * @param object
  	 * @return list
  	 */
  	@SuppressWarnings("unchecked")
  	public List getDayAll(Object object){
  		List returnList = new ArrayList() ;
  		try {
  			returnList = this.queryForList("sys.ajax.getDayAll", object);
  		} catch (SQLException e) {			
  			e.printStackTrace();
  		}
  		return returnList ;
  	}
  	
  	/**
  	 * 根据公司ID查询该公司考勤区间
  	 * @param object
  	 * @return list
  	 */
  	@SuppressWarnings("unchecked")
  	public List getTimeIntervalList(Object object){
  		List returnList = new ArrayList() ;
  		try {
  			returnList = this.queryForList("sys.ajax.getTimeIntervalList", object);
  		} catch (SQLException e) {			
  			e.printStackTrace();
  		}
  		return returnList ;
  	}
  	
  	/**
  	 * 未刷卡查询的状态(旷工)
  	 * @param object
  	 * @return list
  	 */
  	@SuppressWarnings("unchecked")
  	public List statusList(Object object){
  		List returnList = new ArrayList() ;
  		try {
  			returnList = this.queryForList("sys.ajax.statusList", object);
  		} catch (SQLException e) {			
  			e.printStackTrace();
  		}
  		return returnList ;
  	}
}