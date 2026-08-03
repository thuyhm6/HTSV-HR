package com.ait.ess.dao;

import java.util.Map;

public interface PersonInfoDao {

	public Map getEssPersonInfo(Object obj) throws Exception;

	public Map getHrPersonInfo(Object obj) throws Exception;

	public Map getEducationInfoList(Object object, int skipResults,
			int maxResults);

	public Map getEvaluateforList(Object object, int skipResults, int maxResults);
}
