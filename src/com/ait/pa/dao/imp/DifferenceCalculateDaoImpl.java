package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;

import com.ait.pa.dao.DifferenceCalculateDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class DifferenceCalculateDaoImpl extends SqlMapClientSupport implements DifferenceCalculateDao {

	/**
	 * 补差计算
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String differenceCalculate(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			this.insert("pa.differenceCalculate.differenceCalculate", paramMap) ;
			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}
}
