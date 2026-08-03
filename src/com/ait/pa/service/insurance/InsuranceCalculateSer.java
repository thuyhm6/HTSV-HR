package com.ait.pa.service.insurance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceCalculateSer.java
 * @Description:
 * @Create date: 2012-2-17 下午02:56:24
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface InsuranceCalculateSer {
		
	public String insuranceCalculate(HttpServletRequest request) ;
	
	/**
	 * 新保险计算
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-13 下午5:14:23 
	* @version V1.0
	 */
	public String insuranceCalculateNew(HttpServletRequest request);

	/**
	 * 保险月 关联出保险发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-18 下午6:57:50 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryProvideDate(HttpServletRequest request);
	
}
