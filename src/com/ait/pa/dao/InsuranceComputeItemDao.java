package com.ait.pa.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceComputeItemDao.java
 * @Description:
 * @Create date: 2012-1-19 下午04:59:01
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface InsuranceComputeItemDao {

	public Object getInsuranceComputeItemInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemList(Object object);
	
	public int getInsuranceComputeItemCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemList(Object object, int currentPage, int pageSize);
	
	public int checkAddInsuranceComputeItemInfo(Object object);
	
	public void addInsuranceComputeItemInfo(Object object) throws Exception;
	
	public void updateInsuranceComputeItemInfo(Object object) throws Exception;
	
	/****
	 * 保险项目和老系统mapping关系修改
	 * @param request
	 * @return
	 */
	public int updateIsItemParamInfo(Object object);
	
	public int checkDeleteInsuranceComputeItemInfo(Object object) ;
	
	public int checkDeleteInsuranceComputeItemParamInfo(Object object) ;
	
	public void deleteInsuranceComputeItemInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int updateInsuranceComputeItemInfoCalOrder(List list) ;
	
	public int updateICInfoByCalcuOrder(Object obj);

	public int updateICInfoByItemNo(Object obj);
	
	public Object getInsuranceComputeItemParamInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemParamList(Object object) ;
	
//	public int getInsuranceComputeItemParamListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemParamList(Object object, int currentPage, int pageSize);
	
	public int checkAddInsuranceComputeItemParamInfo(Object object);
	
	public int addInsuranceComputeItemParamInfo(Object object);
	
	public void updateInsuranceComputeItemParamInfo(Object object) throws Exception;
	
	public int deleteInsuranceComputeItemParamInfo(Object object) ;
	
}
