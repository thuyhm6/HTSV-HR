package com.ait.promoter.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright:    LG CNS
 * Company:      LG CNS
 * @fileName:    PromoterDao.java
 * @Create date: 2014.06.09
 * @Create by:   CH.W.G
 * @version 1.0
 */
public interface PromoterDao {
	// * @Create date: 2014.06.09
	@SuppressWarnings("rawtypes")
	public List getFixedPayList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.09
	@SuppressWarnings("rawtypes")
	public List getFixedPayList(Object object);

	// * @Create date: 2014.06.09
	public int getFixedPayListCnt(Object object);

	// * @Create date: 2014.06.09
	public Object getFixedPayInfo(Object obj) ;

	// * @Create date: 2014.06.09
	public void updateFixedPay(Object object) throws Exception;

	// * @Create date: 2014.06.13
	public void addFixedPay(Object object) throws Exception;
	
	// * @Create date: 2014.06.10
	@SuppressWarnings("rawtypes")
	public List getCityLevelList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.10
	@SuppressWarnings("rawtypes")
	public List getCityLevelList(Object object);
	
	// * @Create date: 2014.06.10
	public int getCityLevelListCnt(Object object);
	
	// @Create date: 2014.06.10
	public Object getCityLevelInfo(Object object);

	// * @Create date: 2014.06.10
	public void updateCityLevel(Object object) throws Exception;

	// * @Create date: 2014.06.10
	@SuppressWarnings("rawtypes")
	public List getCustInfoList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.10
	@SuppressWarnings("rawtypes")
	public List getCustInfoList(Object object);
	
	// * @Create date: 2014.06.10
	public int getCustInfoListCnt(Object object);
	
	// @Create date: 2014.06.10
	public Object getCustInfo(Object object);

	// * @Create date: 2014.06.10
	public void updateCustInfo(Object object) throws Exception;

	// * @Create date: 2014.06.17
	@SuppressWarnings({ "rawtypes" })
	public List getSalesAchLocalList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.17
	@SuppressWarnings({ "rawtypes" })
	public List getSalesAchLocalList(Object object);

	// * @Create date: 2014.06.17
	public int getSalesAchLocalListCnt(Object object);

	// * @Create date: 2014.06.17
	public void calSalesAchLocal(Object object) throws Exception;

	// * @Create date: 2014.06.19
	@SuppressWarnings({ "rawtypes" })
	public List getIncBasicSetupList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.19
	@SuppressWarnings({ "rawtypes" })
	public List getIncBasicSetupList(Object object);
	
	// * @Create date: 2014.06.19
	public int getIncBasicSetupListCnt(Object object);
	
	// @Create date: 2014.06.19
	public Object getIncBasicSetupInfo(Object object);

	// * @Create date: 2014.06.19
	public void updateIncBasicSetup(Object object) throws Exception;

	// * @Create date: 2014.06.20
	@SuppressWarnings({ "rawtypes" })
	public List getOfficeIncAdjustList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.20
	@SuppressWarnings({ "rawtypes" })
	public List getOfficeIncAdjustList(Object object);
	
	// * @Create date: 2014.06.20
	public int getOfficeIncAdjustListCnt(Object object);
	
	// @Create date: 2014.06.20
	public Object getOfficeIncAdjustInfo(Object object);

	// * @Create date: 2014.06.20
	public void updateOfficeIncAdjust(Object object) throws Exception;

	// * @Create date: 2014.06.20
	public void addOfficeIncAdjust(Object object) throws Exception;

	// * @Create date: 2014.06.23
	@SuppressWarnings({ "rawtypes" })
	public List getIncBasicSetupByDayList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.23
	@SuppressWarnings({ "rawtypes" })
	public List getIncBasicSetupByDayList(Object object);
	
	// * @Create date: 2014.06.23
	public int getIncBasicSetupByDayListCnt(Object object);
	
	// @Create date: 2014.06.23
	public Object getIncBasicSetupByDayInfo(Object object);

	// * @Create date: 2014.06.23
	public void updateIncBasicSetupByDay(Object object) throws Exception;

	// * @Create date: 2014.06.23
	public void addIncBasicSetupByDay(Object object) throws Exception;

	// * @Create date: 2014.08.07
	public int incBasicCheckProdId(Object object);

	// * @Create date: 2014.08.07
	public int incBasicCheckPrcDayCnt(Object object);
	
	// * @Create date: 2014.06.24
	@SuppressWarnings({ "rawtypes" })
	public List getMinGoalSetupList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.24
	@SuppressWarnings({ "rawtypes" })
	public List getMinGoalSetupList(Object object);
	
	// * @Create date: 2014.06.24
	public int getMinGoalSetupListCnt(Object object);
	
	// @Create date: 2014.06.24
	public Object getMinGoalSetupInfo(Object object);

	// * @Create date: 2014.06.24
	public void updateMinGoalSetup(Object object) throws Exception;

	// * @Create date: 2014.06.24
	public void addMinGoalSetup(Object object) throws Exception;
    
	/********************************/
	// * @Create date: 2014.06.24
	@SuppressWarnings({ "rawtypes" })
	public List getSalsRateList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.24
	@SuppressWarnings({ "rawtypes" })
	public List getSalsRateList(Object object);
	
	// * @Create date: 2014.06.24
	public int getSalsRateListCnt(Object object);
	
	// @Create date: 2014.06.24
	public Object getSalsRateInfo(Object object);

	// * @Create date: 2014.06.24
	public void updateSalsRate(Object object) throws Exception;

	// * @Create date: 2014.06.24
	public void addSalsRate(Object object) throws Exception;

	/********************************/
	// * @Create date: 2014.06.25
	@SuppressWarnings({ "rawtypes" })
	public List getIncProdTpByPromoterList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.25
	@SuppressWarnings({ "rawtypes" })
	public List getIncProdTpByPromoterList(Object object);
	
	// * @Create date: 2014.06.25
	public int getIncProdTpByPromoterListCnt(Object object);
	
	// @Create date: 2014.06.25
	public Object getIncProdTpByPromoterInfo(Object object);

	// * @Create date: 2014.06.25
	public void updateIncProdTpByPromoter(Object object) throws Exception;

	/********************************/
	// * @Create date: 2014.06.26
	@SuppressWarnings({ "rawtypes" })
	public List getPromoterSelloutUploadList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.26
	@SuppressWarnings({ "rawtypes" })
	public List getPromoterSelloutUploadList(Object object);

	// * @Create date: 2014.06.26
	public int getPromoterSelloutUploadListCnt(Object object);

	/********************************/
	// * @Create date: 2014.08.19
	@SuppressWarnings({ "rawtypes" })
	public List getPromoterSelloutConfirmList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.08.19
	@SuppressWarnings({ "rawtypes" })
	public List getPromoterSelloutConfirmList(Object object);

	// * @Create date: 2014.08.19
	public int getPromoterSelloutConfirmListCnt(Object object);

	// * @Create date: 2014.08.19
	@SuppressWarnings({ "rawtypes" })
	public List viewPromoterSelloutConfirmList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.08.19
	@SuppressWarnings({ "rawtypes" })
	public List viewPromoterSelloutConfirmList(Object object);

	// * @Create date: 2014.08.19
	public int viewPromoterSelloutConfirmListCnt(Object object);

	// * @Create date: 2014.08.19
	public void selloutConfirm(Object object) throws Exception;

	/********************************/
	// * @Create date: 2014.06.26
	@SuppressWarnings({ "rawtypes" })
	public List getSalesAchievementList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.26
	@SuppressWarnings({ "rawtypes" })
	public List getSalesAchievementList(Object object);

	// * @Create date: 2014.06.26
	public int getSalesAchievementListCnt(Object object);

	// * @Create date: 2014.06.26
	public void reLoadSalesAchievement(Object object) throws Exception;

	// * @Create date: 2014.06.26
	public void calSalesAchievement(Object object) throws Exception;

	/********************************/
	// * @Create date: 2014.07.10
	@SuppressWarnings("rawtypes")
	public void deleteIncBasicSetupTemp(LinkedHashMap paramMap) throws Exception;

	// * @Create date: 2014.07.10
	public void insertIncBasicSetupTemp(Object obj)throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List getDataImportResultList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("rawtypes")
	public List getDataImportResultList(Object obj);
	
	public int getDataImportResultListCnt(Object obj);
	
	public int getDataImportErrCnt(Object obj);
	
	@SuppressWarnings("rawtypes")
	public String importIncBasicSetupFromExcel(LinkedHashMap paramMap);

	/********************************/
	// * @Create date: 2014.07.16
	@SuppressWarnings("rawtypes")
	public void deleteOfficeIncAdjustTemp(LinkedHashMap paramMap) throws Exception;

	public void insertOfficeIncAdjustTemp(Object obj)throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List getImportOfficeIncAdjustResultList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("rawtypes")
	public List getImportOfficeIncAdjustResultList(Object obj);
	
	public int getImportOfficeIncAdjustResultCnt(Object obj);
	
	public int getImportOfficeIncAdjustErrCnt(Object obj);
	
	@SuppressWarnings("rawtypes")
	public String importOfficeIncAdjustFromExcel(LinkedHashMap paramMap);

	/********************************/
	// * @Create date: 2014.07.16
	@SuppressWarnings("rawtypes")
	public void deleteSelloutTemp(LinkedHashMap paramMap) throws Exception;

	public void insertSelloutTemp(Object obj)throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List getImportSelloutResultList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("rawtypes")
	public List getImportSelloutResultList(Object obj);
	
	public int getImportSelloutResultCnt(Object obj);
	
	public int getImportSelloutErrCnt(Object obj);
	
	@SuppressWarnings("rawtypes")
	public String importSelloutFromExcel(LinkedHashMap paramMap);
	/********************************/

	@SuppressWarnings("rawtypes")
	public List getPayAreaCodeList(Object object);

	public String getLastMonth(Object object);
	/********************************/
	// * @Create date: 2014.08.29
	@SuppressWarnings("rawtypes")
	public List getSelloutRequestList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("rawtypes")
	public List getSelloutRequestList(Object object);

	public int getSelloutRequestListCnt(Object object);
	
	@SuppressWarnings("rawtypes")
	public List getSelloutReqDtlList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("rawtypes")
	public List getSelloutReqDtlList(Object object);

	public int getSelloutReqDtlListCnt(Object object);

	// * @Create date: 2014.09.01
	public void addSelloutReq(Object object) throws Exception;
	public void updateSellInvoiceQty(Object obj) throws Exception;
	public void updateSellPayRate(Object obj) throws Exception;
	public void updateSellPayRateRep(Object obj) throws Exception;

	@SuppressWarnings("rawtypes")
	public LinkedHashMap getSelloutReqByReqId(Object object);

	public Long getNewReqId(Object object);
	
	@SuppressWarnings("rawtypes")
	public List getAffirmorList(Object object);
	
	@SuppressWarnings("rawtypes")
	public List getCheckList(Object object);
	
	/********************************/
	// * @Create date: 2014.09.10
	@SuppressWarnings("rawtypes")
	public List getMenuThirdList(Map paramMap);
	public void insertAffirmor(Object object)  throws Exception;
	public void deleteAffirmor(Object object)  throws Exception;
	// * @Create date: 2014.09.16
	public void affirmSellout(Object object) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getCheckListToLgep(Object object);

	// * @Create date: 2014.09.18
	public void delSelloutReq(Object object) throws Exception;
	public void submitSellout(Object object) throws Exception;
	public void getSelloutFromCnmas(Object paramMap) throws Exception;

	@SuppressWarnings("rawtypes")
	public List getReqOver10List(Map paramMap);
	@SuppressWarnings("rawtypes")
	public List getReqOver9kList(Map paramMap);
	@SuppressWarnings("rawtypes")
	public List getReqRatioList(Map paramMap);
	@SuppressWarnings("rawtypes")
	public List getReqExshopList(Map paramMap);
	@SuppressWarnings("rawtypes")
	public List getReqReportList(Map paramMap, int currentPage, int pageSize);
	@SuppressWarnings("rawtypes")
	public int getReqReportListCnt(Map paramMap);
	
	@SuppressWarnings("rawtypes")
	public List getReqTotalList(Map paramMap);
	@SuppressWarnings("rawtypes")
	public List getNewReqOver10List(Map paramMap);
	@SuppressWarnings("rawtypes")
	public List getNewReqOver9kList(Map paramMap);
	@SuppressWarnings("rawtypes")
	public List getNewReqRatioList(Map paramMap);
	@SuppressWarnings("rawtypes")
	public List getNewReqExshopList(Map paramMap);
	@SuppressWarnings("rawtypes")
	public List getNewReqReportList(Map paramMap, int currentPage, int pageSize);
	@SuppressWarnings("rawtypes")
	public int getNewReqReportListCnt(Map paramMap);
	/********************************/
	@SuppressWarnings("rawtypes")
	public void deleteimportTempEmp(LinkedHashMap paramMap) throws Exception;

	public void insertTempEmp(Object obj)throws Exception;
	
}
