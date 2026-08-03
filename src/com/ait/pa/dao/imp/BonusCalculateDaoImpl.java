package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.BonusCalculateDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class BonusCalculateDaoImpl extends SqlMapClientSupport implements BonusCalculateDao {

	/**
	 * 奖金计算
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String bonusCalculate(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			this.insert("pa.bonusCalculate.bonusCalculate", paramMap) ;
			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			
			e.printStackTrace();
		}
		
		return returnString ;
	}
	
	/**
	 * 验证工资是否也是以合并计税方式,计算工资
	 * @param List
	 * @return
	 */
	public int getCheckPaCalculateType(Object object){
		int returnInt = 0 ;
		try {
			
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.bonusCalculate.getCheckPaCalculateType", object), "0"), Integer.class) ;
			
		} catch (SQLException e) {	
			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	@Override
	public List getSalaryProvideDateBn(LinkedHashMap object) {
List returnList = new ArrayList() ;
		
		try {
			returnList = this.queryForList("pa.bonusCalculate.getSalaryProvideDateBn", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return returnList ;
	}
}
