package com.ait.pa.dao;

import java.util.List;

public interface BonusComputeItemParamDao {

	public Object getBonusComputeItemParamInfo(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusComputeItemParamList(Object object);

//	public int getBonusComputeItemParamCnt(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusComputeItemParamList(Object object, int currentPage,
			int pageSize);

	public int checkAddBonusComputeItemParamInfo(Object object);

	public void addBonusComputeItemParamInfo(Object object) throws Exception;

	public int updateBonusComputeItemParamInfo(Object object);

	public int checkDeleteBonusComputeItemParamInfo(Object object);

	public void deleteBonusComputeItemParamInfo(Object object) throws Exception;

	public int updateBonusItemParamCalcuOrder(Object obj);

	public int updateBonusItemParamCalcuOrderByParamNo(Object obj);

	public int checkBonusComputeItemParamCanBeDeleted(Object object);
}
