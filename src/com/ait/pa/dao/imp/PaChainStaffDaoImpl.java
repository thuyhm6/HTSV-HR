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
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaChainStaffDaoImpl extends SqlMapClientSupport implements PaChainStafftDao {
	
	/**
	 * 取得工资账户信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaEmpAccountList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.workManagement.getPaEmpAccountList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("pa.workManagement.getPaEmpAccountList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	

}
