package com.ait.pa.service.insurance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceComputeItemSer.java
 * @Description:
 * @Create date: 2012-1-16 下午06:51:03
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface InsuranceComputeItemSer {
	
	public Object getInsuranceComputeItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemList(HttpServletRequest request) ;
	
	public int getInsuranceComputeItemCnt(HttpServletRequest request);
	
	public int checkAddInsuranceComputeItemInfo(HttpServletRequest request);
	
	public int addInsuranceComputeItemInfo(HttpServletRequest request);
	
	public int updateInsuranceComputeItemInfo(HttpServletRequest request);
	
	/****
	 * 保险项目和老系统mapping关系修改
	 * @param request
	 * @return
	 */
	public int updateIsItemParamInfo(HttpServletRequest request);
	
	public int checkDeleteInsuranceComputeItemInfo(HttpServletRequest request) ;
	
	public int checkDeleteInsuranceComputeItemParamInfo(HttpServletRequest request) ;
	
	public int deleteInsuranceComputeItemInfo(HttpServletRequest request);
	
	public int updateInsuranceComputeItemInfoCalOrder(HttpServletRequest request);
	
	
	public int updateICInfoByCalcuOrder(HttpServletRequest request,int type,String item_no,String calcu_order);
	
	public int updateICInfoByItemNo(HttpServletRequest request,int type,String item_no,String calcu_order);
	
	public Object getInsuranceComputeItemParamInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemParamList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemParamListForFormula(HttpServletRequest request) ;
	
//	public int getInsuranceComputeItemParamCnt(HttpServletRequest request);
	
	public int checkAddInsuranceComputeItemParamInfo(HttpServletRequest request);
	
	public int addInsuranceComputeItemParamInfo(HttpServletRequest request);
	
	public int updateInsuranceComputeItemParamInfo(HttpServletRequest request);
	
	public int deleteInsuranceComputeItemParamInfo(HttpServletRequest request);
}
