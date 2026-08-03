package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;

import com.ait.pa.dao.InsuranceCalculateDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceCalculateDaoImpl.java
 * @Description:
 * @Create date: 2012-2-17 下午02:57:07
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class InsuranceCalculateDaoImpl extends SqlMapClientSupport implements InsuranceCalculateDao {

	/**
	 * 保险计算(insurance Calculate)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String insuranceCalculate(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			this.insert("pa.insuranceCalculate.insuranceCalculate", paramMap) ;
			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}
	
	/**
	 * 新保险计算
	 */
	@Override
	public String insuranceCalculateNew(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			//this.insert("pa.insuranceCalculate.insuranceCalculateNew", paramMap) ;
			this.insert("pa.insuranceCalculate.insuranceCalculateLGE", paramMap) ;
			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}

	@Override
	public List getSalaryProvideDate(LinkedHashMap object) {
		List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.insuranceCalculate.getSalaryProvideDate", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			
	
		
		return returnList ;
	}
}
