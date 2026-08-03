package com.ait.ar.dao;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArMonthCalculateDao.java
 * @Description: implement Class ArMonthCalculateDaoImpl.java
 * @Create date: 2012-2-10 下午05:53:21
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArMonthCalculateDao {
	 @SuppressWarnings("unchecked")
     public String monthCalculate(LinkedHashMap object) ;
	 
	 @SuppressWarnings("unchecked")
     public String monthCalculateConfirm(LinkedHashMap object) ;
	 
	 @SuppressWarnings("unchecked")
     public String monthCalculateConfirmApply(LinkedHashMap object) ;
	 
	 @SuppressWarnings("unchecked")
	 public int getMonthlyStatusCnt(Object object);
	 
	 @SuppressWarnings("unchecked")
	 public int getMonthlyStatusByDeptDisCnt(Object object);
	 
	 @SuppressWarnings("unchecked")
	 public void updateAttStatus(Object obj) throws Exception;
	 
	 @SuppressWarnings("unchecked")
	 public void insertMonthlyStatus(Object object);
	 
	 @SuppressWarnings("unchecked")
	 public List getMonthlyStatusList(Object parameterObject);
	 
	 @SuppressWarnings("unchecked")
	 public int getMonthlyStatusByDeptCnt(Object parameterObject);
	 
	 @SuppressWarnings("unchecked")
	 public List getDeptDistinguishListBySupervisior(Object parameterObject);
	 
	 @SuppressWarnings("unchecked")
	 public void insertMonthlyStatusByDept(Object object);
	 
	 @SuppressWarnings("unchecked")
	 public void updateMonthlyStatusByDept(Object object);
	 
	 @SuppressWarnings("unchecked")
	 public List getStatNoList(Object object);
	 
	 
	 @SuppressWarnings("unchecked")
	 public List getDeptAreaList(Object object);
	 
	 @SuppressWarnings("unchecked")
	 public List getDeptTypeList(Object object);
	 
	 @SuppressWarnings("unchecked")
	 public void updatearapplyCloseGuan(Object object);
	 
	 @SuppressWarnings("unchecked")
	 public void updatepaapplyCloseGuan(Object object);
	 
	 @SuppressWarnings("unchecked")
	 public void deleteArPaSummaryapplyClose(Object object);
	 
	 @SuppressWarnings("unchecked")
	 public void rowTranscolsPa(Object object);
	 
	 @SuppressWarnings("unchecked")
	 public List getarEssNOApplyCount(Object parameterObject);
	 
	 @SuppressWarnings("unchecked")
	 public List getpaEssNOApplyCount(Object parameterObject);
	 
	 @SuppressWarnings("unchecked")
	 public String getdeptNamebyno(Object parameterObject);
	 
	 @SuppressWarnings("unchecked")
	 public String getATTMOLOCKFLAG(Object object);
	 
	 @SuppressWarnings("unchecked")
	 public int getPaProgressLockFlag(Object parameterObject);
	 
	 
	 @SuppressWarnings("unchecked")
	 public int updateArOffLeave(Object parameterObject);
	 @SuppressWarnings("unchecked")
	 public int updateArOffLeaveP(Object parameterObject);
	 
	 @SuppressWarnings("unchecked")
	 public int updateArOffApplyOt(Object parameterObject);
	 
	 @SuppressWarnings("unchecked")
	 public int updateArOffCwa(Object parameterObject);
	 
	 @SuppressWarnings("unchecked")
	 public int updateArOffAnnu(Object parameterObject);
	 
	 @SuppressWarnings("unchecked")
	 public int updatepawage(Object parameterObject);
	 
	 @SuppressWarnings("unchecked")
	 public int updateArOffMac(Object parameterObject);
}
