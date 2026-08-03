package com.ait.is.dao;

import java.util.List;
import java.util.Map;

public interface InsureDiscussDao {

	void paBenCompuationCreateApplyBz(Map map);

	int checkExistsForPaBenApplyBz(Map param);

	int checkPaBenPaymentInfoBz(Map param);

	int checkPaCalBz(Map map);

	void paBenCompuationCancelApplyBz(Map map);

	List getPaBenManageLastCurrBz(Map param);

	List getPaBenPaymentInfoListBz(Map param);

	List<Map> getAdjustValueBz(Map param);

}
