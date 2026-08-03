package com.ait.promoter.dao;

import java.util.List;

/**
 * Copyright:    LG CNS
 * Company:      LG CNS
 * @fileName:    GetTagDataDao.java
 * @Create date: 2014.06.11
 * @Create by:   CH.W.G
 * @version 1.0
 */
public interface GetTagDataDao {

	@SuppressWarnings("rawtypes")
	public List getStateList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("rawtypes")
	public List getShengList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("rawtypes")
	public List getStateList(Object object);

	@SuppressWarnings({ "rawtypes" })
	public List getCityByStateList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings({ "rawtypes" })
	public List getCity2ByStateList(Object object, int currentPage, int pageSize);


	@SuppressWarnings({ "rawtypes" })
	public List getCityByStateList(Object object);
	
	@SuppressWarnings({ "rawtypes" })
	public List getRegionByCityList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings({ "rawtypes" })
	public List getRegionByCityTwoList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("rawtypes")
	public List getRegionByCityList(Object object);

	@SuppressWarnings("rawtypes")
	public List getBranchList(Object object);
	
}
