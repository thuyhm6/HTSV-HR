package com.ait.pa.service.bonus;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface BonusResultSer {
	@SuppressWarnings("unchecked")
	public Map getBonusResultAllItem(HttpServletRequest request) throws Exception ;		
	
	public String bonusBalance(HttpServletRequest request) ;
}
