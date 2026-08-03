package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;

import com.ait.pa.dao.InsuranceResultDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class InsuranceResultDaoImpl extends SqlMapClientSupport implements InsuranceResultDao {
	
	/**
	 * 保险结算(insurance Balance)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String insuranceBalance(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			this.insert("pa.insuranceResult.insuranceBalance", paramMap) ;
			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}
}
