package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PersonalAttendanceSer.java
 * @Description:
 * @Create date: 2012-5-9 上午11:04:16
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface PersonalAttendanceSer {
	
	@SuppressWarnings("unchecked")
	public String makeDataTable(HttpServletRequest request, String str) ;

}
