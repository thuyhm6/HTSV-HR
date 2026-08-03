package com.ait.ar.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpShiftSer.java
 * @Description: implement Class EmpShiftSerImp.java
 * @Create date: 2012-1-17 下午03:38:29
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface EmpShiftSer {
	// EmpShiftSerImp
	
	@SuppressWarnings("unchecked")
	public List getShift010();
	@SuppressWarnings("unchecked")
	public List getShift010List(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int addEmpShift(HttpServletRequest request);
    @SuppressWarnings("unchecked")
	public int addEmpShiftByDeptId(HttpServletRequest request);
    @SuppressWarnings("unchecked")
	public int addEmpShiftBydynamicGroup(HttpServletRequest request);
    @SuppressWarnings("unchecked")
	public List getShift010List1(HttpServletRequest request);
    @SuppressWarnings("unchecked")
    public int getPersonCountByDep(HttpServletRequest request) throws NumberFormatException, SQLException;
    @SuppressWarnings("unchecked")
    public int getPersonCountByDYNAMIC(HttpServletRequest request) throws NumberFormatException, SQLException;
    
    public int addEmpShiftByClassNum(HttpServletRequest request);
}
