package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * @author Administrator
 *   业务接口
 */
public interface ArEatCardAssociateSer {
	@SuppressWarnings("unchecked")
	public List getEatCardAssociateList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getEatCardAssociateCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateEatCardAssociateInfo(HttpServletRequest request) ;
	
}
