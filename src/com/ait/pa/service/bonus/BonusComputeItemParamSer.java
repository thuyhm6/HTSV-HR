package com.ait.pa.service.bonus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface BonusComputeItemParamSer {

	public Object getBonusComputeItemParamInfo(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getBonusComputeItemParamList(HttpServletRequest request);

//	public int getBonusComputeItemParamCnt(HttpServletRequest request);

	public int checkAddBonusComputeItemParamInfo(HttpServletRequest request);

	public int addBonusComputeItemParamInfo(HttpServletRequest request);

	public int updateBonusComputeItemParamInfo(HttpServletRequest request);

	public int checkDeleteBonusComputeItemParamInfo(HttpServletRequest request);

	public int deleteBonusComputeItemParamInfo(HttpServletRequest request);

	public int updateBonusItemParamCalcuOrder(HttpServletRequest request,
			int type, String pa_item_no, String calcu_order);

	public int updateBonusItemParamCalcuOrderByParamNo(
			HttpServletRequest request, int type, String ParamNo,
			String calcu_order);

	public int checkBonusComputeItemParamCanBeDeleted(HttpServletRequest request);
}
