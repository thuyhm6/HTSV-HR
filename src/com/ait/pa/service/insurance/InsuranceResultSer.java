package com.ait.pa.service.insurance;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceResultSer.java
 * @Description:
 * @Create date: 2012-2-17 下午02:50:50
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface InsuranceResultSer {
	@SuppressWarnings("unchecked")
	public Map getInsuranceResultAllItem(HttpServletRequest request) throws Exception ;		
	
	public String insuranceBalance(HttpServletRequest request) ;
}
