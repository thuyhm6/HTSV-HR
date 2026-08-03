package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.EssDeptEmpAttDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class EssDeptEmpAttDaoImpl extends SqlMapClientSupport implements EssDeptEmpAttDao{

	@SuppressWarnings("unchecked")
	public List viewArShiftGroupList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.deptEmpAtt.getArShiftGroupList", object);
			//returnList = this.queryForList("ess.deptEmpAtt.getArShiftChangeList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	@SuppressWarnings("unchecked")
	public int viewArShiftGroupCnt(Object object) {
		 int returnInt = 0 ;
			try {
				returnInt = 
					NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.deptEmpAtt.getArShiftGroupCnt", object)), Integer.class) ;
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			
			return returnInt ;
	}
	
	@SuppressWarnings("unchecked")
	public void addArShiftGroupInfo(Object obj)throws Exception {
		
		this.insert("ess.deptEmpAtt.execShiftGroupChangeP", obj) ;
		
	}
	
	@SuppressWarnings("unchecked")
	public int delArShiftGroupInfo(Object obj)throws Exception {
		
		try {
			this.update("ess.deptEmpAtt.updateArShiftGroupInfo", obj) ;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
		
	}

}
