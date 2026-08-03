package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaChainStafftDao;
import com.ait.pa.dao.PaEmpAccountDao;
import com.ait.pa.dao.PaMonthChainDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaMonthChainDaoImpl extends SqlMapClientSupport implements PaMonthChainDao {
	
	/**
	 * 取得工资账户信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaMonthList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			 
				returnList = this.queryForList("pa.viewpaparam.getPaMonthList", obj);
			 
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	

}
