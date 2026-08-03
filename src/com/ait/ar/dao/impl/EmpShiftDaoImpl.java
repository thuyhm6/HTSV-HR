package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ait.ar.dao.EmpShiftDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpShiftDaoImpl.java
 * @Description:
 * @Create date: 2012-1-17 下午03:41:29
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class EmpShiftDaoImpl extends SqlMapClientSupport implements EmpShiftDao {

	/**
	 * 查看班次信息(get Shift010)
	 * @param
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getShift010() {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.empShift.getShift010");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 查看班次信息(get Shift010)
	 * @param
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getShift010List(Object obj) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.empShift.getShift010List", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 添加排班人员(add Emp Shift)
	 * @param request
	 * @return int
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addEmpShift(Object obj) throws Exception{
		
		for(int i=0;i<((List)obj).size();i++){
			this.insert("ar.empShift.CalladdEmpShift", (LinkedHashMap)((List)obj).get(i)) ;
		}
	}
	
	public int getPersonCountByDep(Object object) throws NumberFormatException, SQLException{
		return Integer.parseInt(this.queryForObject("ar.empShift.getEmpIdListCnt", (LinkedHashMap)object).toString());
	}
	
	@SuppressWarnings("unchecked")
	public int getPersonCountByDYNAMIC(Object object) throws NumberFormatException, SQLException{
		return Integer.parseInt(this.queryForObject("ar.empShift.getPersonCountByDYNAMICCount", (LinkedHashMap)object).toString());
		
	}
	/**
	 * 添加排班人员(add Emp Shift)
	 * @param request
	 * @return int
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addClassShift(Object obj) throws Exception{
		
		 
			this.insert("ar.empShift.CalladdClassShift", (LinkedHashMap)obj) ;
	 
	}
}
