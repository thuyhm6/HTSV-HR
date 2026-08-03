package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.PersonShiftDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PersonShiftDaoImpl extends SqlMapClientSupport implements PersonShiftDao{

	@Override
	public int viewPersonShiftListCnt(Object object) {
		 int returnInt = 0 ;
			try {
				returnInt = 
					NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.personShift.getPersonShiftCnt", object)), Integer.class) ;
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			
			return returnInt ;
	}

	@SuppressWarnings("unchecked")
	public List viewPersonShiftList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.personShift.getPersonShift", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.personShift.getPersonShift", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	public List viewArShiftGroupList(Object object) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("ess.personShift.viewArShiftGroupList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	@SuppressWarnings("unchecked")
	public List viewPersonShiftList(Object object) {
		List returnList = new ArrayList() ;
		returnList = this.viewPersonShiftList(object, -1, -1) ;
		return returnList ;
	}

}
