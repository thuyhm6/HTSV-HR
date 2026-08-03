package com.ait.pa.service.salary;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface PaResultSer {
	@SuppressWarnings("unchecked")
	public Map getPaResultAllItem(HttpServletRequest request) throws Exception ;		
	
	@SuppressWarnings("unchecked")
	public Map getPaHistoryAllItem(HttpServletRequest request)  ;	
	
	@SuppressWarnings("unchecked")
	public Map getPaPortalAllItem(HttpServletRequest request) ;		
	
	public String paBalance(HttpServletRequest request) ;

	/**
	 * 查询工资发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-20 下午06:10:07 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getPaGiveDate(HttpServletRequest request)throws Exception;
}
