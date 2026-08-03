package com.ait.ess.service;

import javax.servlet.http.HttpServletRequest;

public interface RecordTestSer {

	/**
	 * 添加打卡记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addRecordTest(HttpServletRequest request) ;
}
