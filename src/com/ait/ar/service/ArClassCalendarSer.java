/**
 * 
 */
package com.ait.ar.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author XUEHAIFEI
 *
 * 下午01:25:06
 */
public interface ArClassCalendarSer {
	//EmpCalendarSerImp
	@SuppressWarnings("unchecked")
	public String getArClassCalendarViewHtml(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int updateArClassCalendarInfo(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getShiftNo(HttpServletRequest request);
}


