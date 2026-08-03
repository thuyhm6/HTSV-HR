package com.ait.ar.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpShiftDao.java
 * @Description:
 * @Create date: 2012-1-17 下午03:40:15
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface EmpShiftDao {
	// EmpShiftDaoImpl
	@SuppressWarnings("unchecked")
	public List getShift010();
	@SuppressWarnings("unchecked")
	public void addEmpShift(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public List getShift010List(Object object);
	@SuppressWarnings("unchecked")
	public int getPersonCountByDep(Object object) throws NumberFormatException, SQLException;
	@SuppressWarnings("unchecked")
	public int getPersonCountByDYNAMIC(Object object) throws NumberFormatException, SQLException;
	
	public void addClassShift(Object object) throws Exception;
}
