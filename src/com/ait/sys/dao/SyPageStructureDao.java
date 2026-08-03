package com.ait.sys.dao;

import java.util.List;

public interface SyPageStructureDao {
	

	@SuppressWarnings("unchecked")
	public List getIsCanBeBuildPage(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPageStructureInfoList(Object object) ;
	
	public int addPageStructure(Object object) ;
	
	@SuppressWarnings("unchecked")
	public int deletePageStructure(List list);
	
	@SuppressWarnings("unchecked")
	public List getAddPageStructureDetail(Object object) ;
	
	public int getAddPageStructureDetailCnt(Object object) ;
	
	public int AddPageStructureDetailInfo(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getUpdatePageStructureDetail(Object object) ;
	
	public int getUpdatePageStructureDetailCnt(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getUpdatePageStructureDetail(Object object,int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getAddPageStructureDetail(Object object,int currentPage, int pageSize);
	
	public int deletePageStructureDetail(Object object);
	
	public int updatePageStructureDetailInfo(Object object);
}
