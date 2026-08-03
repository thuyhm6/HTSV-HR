package com.ait.ar.dao;

import java.util.List;
import java.util.Map;

public interface ArEatCardAssociateDao {
	@SuppressWarnings("unchecked")
	public List getEatCardAssociateList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getEatCardAssociateList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getEatCardAssociateCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public void updateEatCardAssociateInfo(Object object) throws Exception;


}
