package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArAnnualLeaveDao;
import com.ait.ar.dao.ArAnnualStandardDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAnnualStandardDaoImpl.java
 * @Description:
 * @Create date: 2012-2-14 下午12:58:46
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ArAnnualStandardDaoImpl extends SqlMapClientSupport implements ArAnnualStandardDao {
	
	/**
	 * 取得年假标准信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getArAnnualStandardInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;

		List returnList = this.getArAnnualStandardList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得年假标准列表(get ArAnnualStandard List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getArAnnualStandardList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getArAnnualStandardList(obj, -1, -1) ; 
		
		return returnList ;
	}
	
	/**
	 * 取得年假计数(get ArAnnualStandard Cnt)
	 * @param Object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getArAnnualStandardCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.annualStandard.getArAnnualStandardCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 取得所有年假标准列表(get ArAnnualStandard List)
	 * @param obj
	 * @param int
	 * @param int
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getArAnnualStandardList(Object obj, int currentPage, int pageSize) {
		
		List returnList = new ArrayList() ;
		
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.annualStandard.getArAnnualStandardList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.annualStandard.getArAnnualStandardList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 删除年假信息(delete ArAnnual Standard)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void deleteArAnnualStandard(Object object) throws Exception {
		
		this.deleteForList("ar.annualStandard.deleteArAnnualStandard", (List)object) ;
	}
	
	/**
	 * 修改年假标准信息(update ArAnnualStandard Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void updateArAnnualStandard(Object object) throws Exception {
		
		this.update("ar.annualStandard.updateArAnnualStandard", object) ;
	}
	
	/**
	 * 检查年假添加信息(check AddArAnnualStandard Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int checkAddArAnnualStandardInfo(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.annualStandard.checkAddArAnnualStandardInfo", object), "0"), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 插入个人年假信息(add ArAnnualStandard)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addArAnnualStandard(Object object)  throws Exception {
		
		int returnInt = 0;
		returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.annualStandard.getArAnnualOrderno", object), "0"), Integer.class) ;
		((LinkedHashMap)object).put("ORDERNO", returnInt);
		
		this.insert("ar.annualStandard.addArAnnualStandard", object) ;
	}
	
	/**
	 * 年假标准开始月LIST(get ArAnnual Month List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArAnnualMonthList(Object obj) {
		
		List returnList = new ArrayList() ;
		
		try {
			
			returnList = this.queryForList("ar.annualStandard.getArAnnualMonthList", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}

		return returnList ;
	}
}
