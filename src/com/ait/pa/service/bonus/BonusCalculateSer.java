package com.ait.pa.service.bonus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface BonusCalculateSer {
		
	public String bonusCalculate(HttpServletRequest request) ;
	
	public int getCheckPaCalculateType(HttpServletRequest request) ;

	/**
	 * 保险月 关联出保险发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-23 下午4:35:58 
	* @version V1.0
	 */
	public List getSalaryProvideDateBn(HttpServletRequest request);
}
