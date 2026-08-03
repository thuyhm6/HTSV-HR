package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;


public interface BonusCalculateDao {

	@SuppressWarnings("unchecked")
	public String bonusCalculate(LinkedHashMap object) ;
	
	public int getCheckPaCalculateType(Object object) ;

	/**
	 * 保险月 关联出保险发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-23 下午4:37:17 
	* @version V1.0
	 */
	public List getSalaryProvideDateBn(LinkedHashMap object);
}
