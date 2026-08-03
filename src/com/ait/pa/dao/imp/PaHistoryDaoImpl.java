package com.ait.pa.dao.imp;

import java.sql.SQLException;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaHistoryDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaHistoryDaoImpl extends SqlMapClientSupport implements PaHistoryDao {
	/**
	 * 验证是否存在工资历史信息
	 * @param List
	 * @return
	 */
	public int getCheckPaHistoryFlag(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.history.getCheckPaHistoryFlag", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
}
