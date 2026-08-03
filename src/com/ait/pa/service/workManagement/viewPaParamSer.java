package com.ait.pa.service.workManagement;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface viewPaParamSer {

	@SuppressWarnings("unchecked")
	public List getPaInputItemParamList(HttpServletRequest request);

	// 导入之前清除当前者上传的记录
	public int cleanTempListById(HttpServletRequest request);

	// 导入之前清除当前者上传的记录
	public int checkTempListById(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getImportCompareList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public int getImportCompareListCnt(HttpServletRequest request)
			throws Exception;

	@SuppressWarnings("unchecked")
	public Object getImportCompareNum(HttpServletRequest request);

	// 确认
	@SuppressWarnings("unchecked")
	public int setPaParam(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List monthPersonCountInfoList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List monthPersonIncreaseList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List monthPersonDecreaseList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List monthPersonCountInfoSonList(HttpServletRequest request);

	// 以前支付日期
	@SuppressWarnings("unchecked")
	public Object getPerPaInfo(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public String savePaResult(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewVerificationList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewPaResultList(HttpServletRequest request)
			throws ParseException;

	@SuppressWarnings("unchecked")
	public List viewPaResultListSum(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List detailPersonCountInfoList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getEmpSalaryInfoList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getEmpSalaryInfoListWithPamram(HttpServletRequest request, String param);

	@SuppressWarnings("unchecked")
	public Object getPaDetailEmpInfoByPersonId(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getPaDetailInfoList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List detailMonthCountInfoRight(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List detailYearCountInfoRight(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List detailPersonCountInfoRight(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List detailPersonCountInfoLeft(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List detailYearCountInfoLeft(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public int callProForPayMonthDif(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List detailItemCountInfoList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List itemValueInfo(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List checkViewPaResult(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewDeptPaResultList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List viewDeptPaResultListSum(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList0(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList1(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList2(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList3(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList4(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList5(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmSonList6(HttpServletRequest request);

	// 取银行卡号
	@SuppressWarnings("unchecked")
	public List paEmpAccount(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public Object getPersonalInfoForEmpSalaryInfo(HttpServletRequest request);

	// 特殊事项
	@SuppressWarnings("unchecked")
	public Object paOpenFlag(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List paPayScheduleNoByPersonId(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmList2Right(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmList2Bottom(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewResultConfirmList3Right(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewPaArSummaryList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List viewPaArSummarySearchList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List exportPayDetailTxtReport(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getPayScheduleList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPayrollPersonList(HttpServletRequest request) ; 

	@SuppressWarnings("unchecked")
	public List dayPersonCountInfoList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List dayPersonCountInfo2List(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPayDetailList(HttpServletRequest request) throws ParseException;
	
	public String getPayScheduleArDate(HttpServletRequest request);
	
	//Update ar_summary_htsv from excel
	public int updateArSummaryHTSV (HttpServletRequest request, String target);

}
