package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;

import com.ait.pa.dao.BonusResultDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class BonusResultDaoImpl extends SqlMapClientSupport implements BonusResultDao {
	
	/**
	 * 奖金结算
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String bonusBalance(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			this.insert("pa.bonusResult.bonusBalance", paramMap) ;
			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}
}
