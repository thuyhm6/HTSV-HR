package com.ait.is.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;





/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceCalculateDao.java
 * @Description:
 * @Create date: 2012-2-17 下午02:56:56
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface InsuranceSystemCalculateDao {
	@SuppressWarnings("unchecked")
	public List getInsuranceSystemInfoListForSearch(Object object) throws SQLException;
	@SuppressWarnings("unchecked")
	public List getVersionDateListBz(Object object) throws SQLException;
	@SuppressWarnings("unchecked")
	public List getModifyStandardSeriousBz(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public int ifUpdatedVersion(Object object) throws SQLException;
	@SuppressWarnings("unchecked")
	public String getMaxManageCreateDate(Object object) throws SQLException;
	
	
	@SuppressWarnings("unchecked")
	public int selectPaBenFalgByFalg(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public int createBenchmarkStandardVersionBz(Object object) throws SQLException;
	@SuppressWarnings("unchecked")
	public void freshPaBenManageBz(Object object) throws SQLException;
	@SuppressWarnings("unchecked")
	public int deleteBenchmarkStandardBz(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public int updateBenchmarkStandardBz1(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public List getPaBenStandardNotSeriousBz(Object object) throws SQLException;
	@SuppressWarnings("unchecked")
	public List getPaBenStandardSeriousBz(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public void createDataToPaBenBaseBz(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public void deletePaBenBaseWrongEmpBz(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public void updatePaBenBaseAvgSalaryBz(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public String getMaxYearMonthOfComputionBz(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public String afterComputationAffirmBz(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public int paBenBaseNumOrderCountBz() throws SQLException;
	
	@SuppressWarnings("unchecked")
	public int paBenBaseNumTrueOrderCountBz() throws SQLException;
	
	@SuppressWarnings("unchecked")
	public List getPaBenBaseNumOrderListBz(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public boolean copyDataToPaBenManageBz(Map object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public int paBenBaseNumOrderCountBz(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public int paBenBaseNumTrueOrderCountBz(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public Object callPaBenBaseCalBz(Object object) throws SQLException;
	@SuppressWarnings("unchecked")
	public Object callPaBenBaseCheckCalBz(Object object) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public List getPaBenBaseNumCheckListDetailBz(Object object) throws SQLException;
	
	
	@SuppressWarnings("unchecked")
	public List getPaBenBaseNumCheckListBz() throws SQLException;
	
	@SuppressWarnings("unchecked")
	public int backPaBenBaseNumUpdateBz() throws SQLException;
	

	@SuppressWarnings("unchecked")
	public List getPaBenBaseNumListBz(Object object) throws SQLException;
	

	
}
