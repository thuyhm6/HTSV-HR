package com.ait.pa.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

public interface viewPaParamDao {

	@SuppressWarnings("unchecked")
	public List getPaInputItemParamList(Object object);

	@SuppressWarnings("unchecked")
	public void cleanTempListById(Object object) throws SQLException;

	public void checkTempListById(Object object) throws SQLException;

	@SuppressWarnings("unchecked")
	public List getImportCompareList(Object object, int currentPage,
			int pageSize);

	@SuppressWarnings("unchecked")
	public List getImportCompareList(Object object);

	@SuppressWarnings("unchecked")
	public int getImportCompareListCnt(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public Object getImportCompareNum(Object object);

	@SuppressWarnings("unchecked")
	public void setPaParam(Object object) throws SQLException;

	@SuppressWarnings("unchecked")
	public List monthPersonCountInfoList(Object object);

	@SuppressWarnings("unchecked")
	public List monthPersonIncreaseList(Object object);

	@SuppressWarnings("unchecked")
	public List monthPersonDecreaseList(Object object);

	@SuppressWarnings("unchecked")
	public List monthPersonCountInfoSonList(Object object);

	@SuppressWarnings("unchecked")
	public Object getPerPaInfo(Object object);

	@SuppressWarnings("unchecked")
	public String savePaResult(LinkedHashMap paramMap);

	@SuppressWarnings("unchecked")
	public String updatePaResult(LinkedHashMap paramMap);

	// 获取项目字段名
	@SuppressWarnings("unchecked")
	public List getItemList(Object object);

	// 调取存储生成月对比
	@SuppressWarnings("unchecked")
	public int callItemList(Object object);

	@SuppressWarnings("unchecked")
	public List viewVerificationList(Object object);

	@SuppressWarnings("unchecked")
	public List viewPaResultList(Object object);

	@SuppressWarnings("unchecked")
	public List viewPaResultHISTORYList(Object object);

	@SuppressWarnings("unchecked")
	public List viewPaResultSSTList(Object object);

	@SuppressWarnings("unchecked")
	public List viewPaResultListSum(Object object);

	@SuppressWarnings("unchecked")
	public List viewPaResultHISTORYListSum(Object object);

	@SuppressWarnings("unchecked")
	public List viewPaResultSSTListSum(Object object);

	@SuppressWarnings("unchecked")
	public List detailPersonCountInfoList(Object object);

	@SuppressWarnings("unchecked")
	public List getEmpSalaryInfoList(Object object);

	@SuppressWarnings("unchecked")
	public List detailMonthCountInfoRight(Object object);

	@SuppressWarnings("unchecked")
	public List detailYearCountInfoRight(Object object);

	@SuppressWarnings("unchecked")
	public List detailPersonCountInfoRight(Object object);

	@SuppressWarnings("unchecked")
	public List detailPersonCountInfoLeft(Object object);

	@SuppressWarnings("unchecked")
	public List detailYearCountInfoLeft(Object object);

	@SuppressWarnings("unchecked")
	public Object getPaDetailEmpInfoByPersonId(Object object);

	@SuppressWarnings("unchecked")
	public List getPaDetailInfoList(Object object);

	@SuppressWarnings("unchecked")
	public int callPayP(Object object);

	@SuppressWarnings("unchecked")
	public void callProForPayMonthDif(Object object) throws SQLException;

	@SuppressWarnings("unchecked")
	public List detailItemCountInfoList(Object object);

	@SuppressWarnings("unchecked")
	public List itemValueInfo(Object object);

	@SuppressWarnings("unchecked")
	public List checkViewPaResult(Object object);

	@SuppressWarnings("unchecked")
	public List viewDeptPaResultList(Object object);
	
	@SuppressWarnings("unchecked")
	public List viewDeptPaResultListSum(Object obj);

	@SuppressWarnings("unchecked")
	public List viewDeptPaResultSSTList(Object object);

	@SuppressWarnings("unchecked")
	// 获得上个月的PAY_SCHEDULE_NO
	public Object getLastMonthPAY_SCHEDULE_NO(Object object);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList0(Object object);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList1(Object object);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList2(Object object);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList3(Object object);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList4(Object object);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList5(Object object);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList6(Object object);

	@SuppressWarnings("unchecked")
	public List paEmpAccount(Object object);

	@SuppressWarnings("unchecked")
	public Object getPersonalInfoForEmpSalaryInfo(Object object);

	@SuppressWarnings("unchecked")
	public Object paOpenFlag(Object object);

	@SuppressWarnings("unchecked")
	public List paPayScheduleNoByPersonId(Object object);
	
	@SuppressWarnings("unchecked")
	public List getParamList(Object object, String target);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmList2Right(Object object);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmList2Bottom(Object object);

	@SuppressWarnings("unchecked")
	public int callProForPayMonthDifItem(Object object);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmList3Right(Object object);

	@SuppressWarnings("unchecked")
	public List viewPaArSummarySearchList(Object object);

	@SuppressWarnings("unchecked")
	public List viewPaArSummarySearchLowList(Object object);

	@SuppressWarnings("unchecked")
	public List viewPaArSummarySearchListSST(Object object);

	@SuppressWarnings("unchecked")
	public int callPaArSummarySearch(Object object);

	@SuppressWarnings("unchecked")
	public List viewPaArSummaryList(Object object);

	@SuppressWarnings("unchecked")
	public List exportPayDetailTxtReport(Object object);

	@SuppressWarnings("unchecked")
	public List getPayScheduleList(Object object);

	@SuppressWarnings("unchecked")
	public List dayPersonCountInfoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPayDetailList(Object obj);
	
	public String getPayScheduleArDate(Object obj);
	
	//Update ar_summary_htsv from excel
	@SuppressWarnings("unchecked")
	public void updateArSummaryHTSV(Object object, String target) throws SQLException;

}
