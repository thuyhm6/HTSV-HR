package com.ait.pa.dao;

import java.util.List;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: InsuranceFormulaDao.java
 * @Description:interface InsuranceFormulaDao
 * @Create date: 2012-2-17 下午02:44:48
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface InsuranceFormulaDao {

	public Object getInsuranceFormulaInfo(Object object);

	@SuppressWarnings("unchecked")
	public List getInsuranceFormulaList(Object object);

	@SuppressWarnings("unchecked")
	public List getInsuranceFormulaForCNList(Object object);

	public int updateIsFormularByCalcuOrderOne(Object obj);

	public int updateIsFormularByCalcuOrderTwo(Object obj);

	public int updateIsFormularByCalcuOrderThree(Object obj);

	public int addInsuranceFormulaInfo(Object obj);

	public int updateInsuranceFormulaInfo(Object obj);

	public int deleteInsuranceFormulaInfo(Object obj);

}
