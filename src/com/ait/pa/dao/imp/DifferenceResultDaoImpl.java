package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;

import com.ait.pa.dao.DifferenceResultDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class DifferenceResultDaoImpl extends SqlMapClientSupport implements DifferenceResultDao {
	
	/**
	 * 补差结算
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String differenceBalance(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			this.insert("pa.differenceResult.differenceBalance", paramMap) ;
			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}
}
