package com.ait.ess.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaInfoSer.java
 * @Description:
 * @Create date: 2012-5-23 下午06:51:22
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface PaInfoSer {
	
	@SuppressWarnings("unchecked")
	public String makeDataTable(HttpServletRequest request, String str) ;

}
