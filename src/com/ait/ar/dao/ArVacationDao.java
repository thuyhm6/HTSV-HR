package com.ait.ar.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import com.ait.web.util.SqlMapClientSupport;


public interface ArVacationDao {
	
	
	
	@SuppressWarnings("unchecked")
	public List getArVacationLiquidationList(Object object) ;
	
	public int getArVacationLiquidationCnt(Object object);
	
	
	@SuppressWarnings("unchecked")
	public void saveVacationLiquidation(Object object) throws SQLException;
	
	
	@SuppressWarnings("unchecked")
	public List getArVacationUpdateMonthList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getArVacationMonthExcel(Object object) ;
	
	@SuppressWarnings("unchecked")
    public String monthVacCalculate(LinkedHashMap object) ;
	
	public int RetrieveAttStatus(Object object);
	
	public int monthVacationCnt(Object object);
	
	@SuppressWarnings("unchecked")
    public String monthVacCreate(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public int getArVacationUpdateYearCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArVacationUpdateYearList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
    public List getLeaveViewList(Object object) ;
	

	@SuppressWarnings("unchecked")
    public int getLeaveViewListCnt(Object object) ;
	
	
	
	
	@SuppressWarnings("unchecked")
	public void updateArVacationYear(Object object) throws Exception;
	
	public int yearVacationCnt(Object object);
	
	@SuppressWarnings("unchecked")
    public String yearVacCalculate(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
    public String yearVacCreate(LinkedHashMap object) ;
	
	@SuppressWarnings("unchecked")
	public int getArVacationNextCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArVacationNextList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
    public String nextVacationMove(LinkedHashMap object) ;
	

	@SuppressWarnings("unchecked")
    public String getTAWelfare(LinkedHashMap object) ;
	
	
}
