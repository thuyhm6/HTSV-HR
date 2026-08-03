package com.ait.pa.service.bonus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface BonusInputItemDataSer {
	@SuppressWarnings("unchecked")
	public List getPaBonusInputItemDataList(HttpServletRequest request);

	public int getPaBonusInputItemDataCnt(HttpServletRequest request);

	public Object getPaBonusInputItemDataInfo(HttpServletRequest request);

	public int checkAddPaBonusInputItemDataInfo(HttpServletRequest request);

	public int addPaBonusInputItemDataInfo(HttpServletRequest request);

	public int updatePaBonusInputItemDataInfo(HttpServletRequest request);

	public int deletePaBonusInputItemDataInfo(HttpServletRequest request);
	
	public int deletePaBonusInputItemDataInfoType(HttpServletRequest request);

	public int deletePaBonusInputItemDataBatchInfo(HttpServletRequest request);
	
	public int deletePaBonusInputItemDataBatchInfoType(HttpServletRequest request);

	public int createAddBonusInputItemDataInfo(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getAddBonusInputItemDataList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getBnParamDataList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getBnParamDataTwoList(HttpServletRequest request) ;
	
	public int checkBonusInputItemDataInfo(HttpServletRequest request);
	
	public int checkBonusInputItemDataInfoType(HttpServletRequest request);
	
	public Object getPaBonusInputItemParamInfo(HttpServletRequest request);
	
	public int addBonusInputItemDataInfo(HttpServletRequest request);
	
	public int addBonusInputItemOtherDataInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataPersonList(HttpServletRequest request);
	
	public int getBonusInputItemDataPersonCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getAddBonusPersonalInputList(HttpServletRequest request);
	
	public int getAddBonusPersonalInputListCnt(HttpServletRequest request);
	
	public int updateBonusInputItemDataPersonInfo(HttpServletRequest request);
}
