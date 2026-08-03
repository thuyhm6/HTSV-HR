package com.ait.promoter.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

/**
 * Copyright:    LG CNS
 * Company:      LG CNS
 * @fileName:    PromoterSer.java
 * @Description: implement Class PromoterSerImp.java
 * @Create date: 2014.06.09
 * @Create by:   CH.W.G
 * @version 1.0
 */
public interface PromoterSer {
	// * @Create date: 2014.06.09
	@SuppressWarnings("rawtypes")
	public List getFixedPayList(HttpServletRequest request);

	// * @Create date: 2014.06.09
	public int getFixedPayListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.06.09
	public Object getFixedPayInfo(HttpServletRequest request) ;

	// * @Create date: 2014.06.09
	public int updateFixedPay(HttpServletRequest request);

	// * @Create date: 2014.06.13
	public int addFixedPay(HttpServletRequest request);
	
	// * @Create date: 2014.06.10
	@SuppressWarnings("rawtypes")
	public List getCityLevelList(HttpServletRequest request) ;
	
	// * @Create date: 2014.06.10
	public int getCityLevelListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.06.10
	@SuppressWarnings({ "rawtypes" })
	public List getCityLevelListExcel(HttpServletRequest request)throws Exception;

	// * @Create date: 2014.06.10
	public Object getCityLevelInfo(HttpServletRequest request);

	// * @Create date: 2014.06.10
	public int updateCityLevel(HttpServletRequest request);

	// * @Create date: 2014.06.10
	@SuppressWarnings("rawtypes")
	public List getCustInfoList(HttpServletRequest request) ;
	
	// * @Create date: 2014.06.10
	public int getCustInfoListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.06.10
	@SuppressWarnings("rawtypes")
	public List getCustInfoListExcel(HttpServletRequest request)throws Exception;

	// * @Create date: 2014.06.10
	public Object getCustInfo(HttpServletRequest request);

	// * @Create date: 2014.06.10
	public int updateCustInfo(HttpServletRequest request);

	// * @Create date: 2014.06.17
	@SuppressWarnings("rawtypes")
	public List getSalesAchLocalList(HttpServletRequest request);

	// * @Create date: 2014.06.17
	public int getSalesAchLocalListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.06.17
	@SuppressWarnings("rawtypes")
	public Map calSalesAchLocalPre(HttpServletRequest request) ;

	// * @Create date: 2014.08.05
	@SuppressWarnings("rawtypes")
	public List getSalesAchLocalPreList(HttpServletRequest request);

	// * @Create date: 2014.08.05
	public int getSalesAchLocalPreListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.08.05
	@SuppressWarnings("rawtypes")
	public Map calSalesAchLocal(HttpServletRequest request) ;

	// * @Create date: 2014.06.19
	@SuppressWarnings("rawtypes")
	public List getIncBasicSetupList(HttpServletRequest request) ;
	
	// * @Create date: 2014.06.19
	public int getIncBasicSetupListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.06.19
	@SuppressWarnings("rawtypes")
	public List getIncBasicSetupListExcel(HttpServletRequest request)throws Exception;

	// * @Create date: 2014.06.19
	public Object getIncBasicSetupInfo(HttpServletRequest request);

	// * @Create date: 2014.06.19
	public int updateIncBasicSetup(HttpServletRequest request);

	// * @Create date: 2014.06.20
	@SuppressWarnings({ "rawtypes" })
	public List getOfficeIncAdjustList(HttpServletRequest request) ;
	
	// * @Create date: 2014.06.20
	public int getOfficeIncAdjustListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.06.20
	@SuppressWarnings({ "rawtypes" })
	public List getOfficeIncAdjustListExcel(HttpServletRequest request)throws Exception;

	// * @Create date: 2014.06.20
	public Object getOfficeIncAdjustInfo(HttpServletRequest request);

	// * @Create date: 2014.06.20
	public int updateOfficeIncAdjust(HttpServletRequest request);

	// * @Create date: 2014.06.20
	public int addOfficeIncAdjust(HttpServletRequest request);

	// * @Create date: 2014.06.23
	@SuppressWarnings({ "rawtypes" })
	public List getIncBasicSetupByDayList(HttpServletRequest request) ;
	
	// * @Create date: 2014.06.23
	public int getIncBasicSetupByDayListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.06.23
	@SuppressWarnings({ "rawtypes" })
	public List getIncBasicSetupByDayListExcel(HttpServletRequest request)throws Exception;

	// * @Create date: 2014.06.23
	public Object getIncBasicSetupByDayInfo(HttpServletRequest request);

	// * @Create date: 2014.06.23
	public int updateIncBasicSetupByDay(HttpServletRequest request);

	// * @Create date: 2014.06.23
	public int addIncBasicSetupByDay(HttpServletRequest request);

	// * @Create date: 2014.08.07
	public int incBasicCheckProdId(HttpServletRequest request);

	// * @Create date: 2014.08.07
	public int incBasicCheckPrcDayCnt(HttpServletRequest request);

	// * @Create date: 2014.06.24
	@SuppressWarnings({ "rawtypes" })
	public List getMinGoalSetupList(HttpServletRequest request) ;
	
	// * @Create date: 2014.06.24
	public int getMinGoalSetupListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.06.24
	@SuppressWarnings({ "rawtypes" })
	public List getMinGoalSetupListExcel(HttpServletRequest request)throws Exception;

	// * @Create date: 2014.06.24
	public Object getMinGoalSetupInfo(HttpServletRequest request);

	// * @Create date: 2014.06.24
	public int updateMinGoalSetup(HttpServletRequest request);

	// * @Create date: 2014.06.24
	public int addMinGoalSetup(HttpServletRequest request);

	/*************************************************************/
	// * @Create date: 2014.06.24
	@SuppressWarnings({ "rawtypes" })
	public List getSalsRateList(HttpServletRequest request) ;
	
	// * @Create date: 2014.06.24
	public int getSalsRateListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.06.24
	@SuppressWarnings({ "rawtypes" })
	public List getSalsRateListExcel(HttpServletRequest request)throws Exception;

	// * @Create date: 2014.06.24
	public Object getSalsRateInfo(HttpServletRequest request);

	// * @Create date: 2014.06.24
	public int updateSalsRate(HttpServletRequest request);

	// * @Create date: 2014.06.24
	public int addSalsRate(HttpServletRequest request);

	/*************************************************************/
	// * @Create date: 2014.06.25
	@SuppressWarnings({ "rawtypes" })
	public List getIncProdTpByPromoterList(HttpServletRequest request) ;
	
	// * @Create date: 2014.06.25
	public int getIncProdTpByPromoterListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.06.25
	@SuppressWarnings({ "rawtypes" })
	public List getIncProdTpByPromoterListExcel(HttpServletRequest request)throws Exception;

	// * @Create date: 2014.06.25
	public Object getIncProdTpByPromoterInfo(HttpServletRequest request);

	// * @Create date: 2014.06.25
	public int updateIncProdTpByPromoter(HttpServletRequest request);

	/*************************************************************/
	// * @Create date: 2014.06.26
	@SuppressWarnings({ "rawtypes" })
	public List getPromoterSelloutUploadList(HttpServletRequest request);

	// * @Create date: 2014.06.26
	public int getPromoterSelloutUploadListCnt(HttpServletRequest request) ;

	/*************************************************************/
	// * @Create date: 2014.08.19
	@SuppressWarnings({ "rawtypes" })
	public List getPromoterSelloutConfirmList(HttpServletRequest request);

	// * @Create date: 2014.08.19
	public int viewPromoterSelloutConfirmListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.08.19
	@SuppressWarnings({ "rawtypes" })
	public List viewPromoterSelloutConfirmList(HttpServletRequest request);

	// * @Create date: 2014.08.19
	public int getPromoterSelloutConfirmListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.08.19
	@SuppressWarnings("rawtypes")
	public Map selloutConfirm(HttpServletRequest request) ;
	
	/*************************************************************/
	// * @Create date: 2014.06.26
	@SuppressWarnings({ "rawtypes" })
	public List getSalesAchievementList(HttpServletRequest request);

	// * @Create date: 2014.06.26
	public int getSalesAchievementListCnt(HttpServletRequest request) ;

	@SuppressWarnings({ "rawtypes" })
	public List getSalesAchievementListExcel(HttpServletRequest request);

	// * @Create date: 2014.06.26
	@SuppressWarnings("rawtypes")
	public Map reLoadSalesAchievement(HttpServletRequest request) ;

	// * @Create date: 2014.06.26
	@SuppressWarnings("rawtypes")
	public Map calSalesAchievement(HttpServletRequest request) ;

	/*************************************************************/
    /**
	 * @date 2014.7.10 CH.W.G
	 */
	@SuppressWarnings("rawtypes")
	public ModelMap importIncBasicSetup(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List getDataImportResultList(HttpServletRequest request, Map paramMap);
	
	@SuppressWarnings("rawtypes")
	public int getDataImportResultListCnt(HttpServletRequest request, Map paramMap);
	
	@SuppressWarnings("rawtypes")
	public int getDataImportErrCnt(HttpServletRequest request, Map paramMap);
	
	public String importIncBasicSetupFromExcel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap);

	// * @Create date: 2014.07.16
	@SuppressWarnings("rawtypes")
	public List getImportIncBasicSetupFromExcel(HttpServletRequest request)throws Exception;

	/*************************************************************/
    /**
	 * @date 2014.7.16 CH.W.G
	 */
	@SuppressWarnings("rawtypes")
	public ModelMap importOfficeIncAdjust(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) throws Exception;

	@SuppressWarnings("rawtypes")
	public List getImportOfficeIncAdjustResultList(HttpServletRequest request, Map paramMap);
	
	@SuppressWarnings("rawtypes")
	public int getImportOfficeIncAdjustResultCnt(HttpServletRequest request, Map paramMap);
	
	@SuppressWarnings("rawtypes")
	public int getImportOfficeIncAdjustErrCnt(HttpServletRequest request, Map paramMap);
	
	public String importOfficeIncAdjustFromExcel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap);

	@SuppressWarnings("rawtypes")
	public List getImportOfficeIncAdjustFromExcel(HttpServletRequest request)throws Exception;

	/*************************************************************/
    /**
	 * @date 2014.7.16 CH.W.G
	 */
	@SuppressWarnings("rawtypes")
	public ModelMap importSellout(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) throws Exception;

	@SuppressWarnings("rawtypes")
	public List getImportSelloutResultList(HttpServletRequest request, Map paramMap);
	
	@SuppressWarnings("rawtypes")
	public int getImportSelloutResultCnt(HttpServletRequest request, Map paramMap);
	
	@SuppressWarnings("rawtypes")
	public int getImportSelloutErrCnt(HttpServletRequest request, Map paramMap);
	
	public String importSelloutFromExcel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap);

	@SuppressWarnings("rawtypes")
	public List getImportSelloutFromExcel(HttpServletRequest request)throws Exception;

	/*************************************************************/

	@SuppressWarnings("rawtypes")
	public List getPayAreaCodeList(Map paramMap);

	// * @Create date: 2014.08.26
	@SuppressWarnings("rawtypes")
	public String getLastMonth(HttpServletRequest request, Map paramMap) ;

	/*************************************************************/
	/* 促销员实绩审批申请列表查询
	 * @Create date: 2014.08.29
	 */
	@SuppressWarnings("rawtypes")
	public List getSelloutRequestList(HttpServletRequest request);
	public int  getSelloutRequestListCnt(HttpServletRequest request);
	@SuppressWarnings("rawtypes")
	public List getSelloutReqDtlList(HttpServletRequest request);
	@SuppressWarnings("rawtypes")
	public List getSelloutReqDtl(HttpServletRequest request);

	// * @Create date: 2014.09.01
	@SuppressWarnings("rawtypes")
	public Map addSelloutReq(HttpServletRequest request) ;

	@SuppressWarnings("rawtypes")
	public LinkedHashMap getSelloutReqByReqId(HttpServletRequest request);
	@SuppressWarnings("rawtypes")
	public List getAffirmorListByReqId(HttpServletRequest request);
	@SuppressWarnings("rawtypes")
	public List getCheckListByReqId(HttpServletRequest request);
	
	/*************************************************************/
	// * @Create date: 2014.09.10
	@SuppressWarnings("rawtypes")
	public List getMenuThirdList(String menu_code, HttpServletRequest request);
	@SuppressWarnings("rawtypes")
	public List getMenuThirdConfrimList(String menu_code, HttpServletRequest request);
	@SuppressWarnings("rawtypes")
	public List getApplyFeeList(HttpServletRequest request) throws Exception;
	// * @Create date: 2014.09.16
	@SuppressWarnings("rawtypes")
	public Map affirmSellout(HttpServletRequest request) ;

	// * @Create date: 2014.09.18
	@SuppressWarnings("rawtypes")
	public Map submitSelloutReq(HttpServletRequest request) ;
	@SuppressWarnings("rawtypes")
	public Map getSelloutFromCnmas(HttpServletRequest request, Map paramMap) ;

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

	/*************************************************************/

	@SuppressWarnings("rawtypes")
	public ModelMap importTempEmp(HttpServletRequest request, HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) throws Exception;

}
