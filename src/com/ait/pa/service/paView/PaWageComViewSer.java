package com.ait.pa.service.paView;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaBasicViewSer.java
 * @Description:
 * @Create date: 2012-5-23 下午02:55:48
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface PaWageComViewSer {
	
	public String makeDataTable(HttpServletRequest request, String str) ;


	public List getWmpTypeLikst(String cpnyId);

	public List getPaWageComList(HttpServletRequest request);

	public List getPaItem2(HttpServletRequest request);

}
