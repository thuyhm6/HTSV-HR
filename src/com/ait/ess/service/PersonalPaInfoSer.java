package com.ait.ess.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PersonalPaInfoSer.java
 * @Description:
 * @Create date: 2012-5-23 下午06:51:22
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface PersonalPaInfoSer {
	
	@SuppressWarnings("unchecked")
	public String makeDataTable(HttpServletRequest request, String str) ;
	@SuppressWarnings("unchecked")
	public List getAddProList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getMiProList(HttpServletRequest request) ;
	
	/**
	 * 关联出保险发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-23 下午8:04:08 
	* @version V1.0
	 */
	public List getSalaryProvideDateEss(HttpServletRequest request);
	/**
	 * 为查询工资是否开放 2.2修改
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-11-6 下午8:20:52 
	* @version V1.0
	 */
	public int getSalaryDispark(HttpServletRequest request);
	int getSalaryDisparkNew(Map paramMap);
}
