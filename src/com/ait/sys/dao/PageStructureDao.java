package com.ait.sys.dao;

import java.util.List;
public interface PageStructureDao {
	
	@SuppressWarnings("unchecked")
	public List getPageStructureList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPsDataList(Object object);
	
	@SuppressWarnings("unchecked")
	public List retrieveReportItemListByTableName(Object object);
	
	@SuppressWarnings("unchecked")
	public void addNewAliasInfo (List list) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List retrieveReportItemList(Object object);
	
    @SuppressWarnings("unchecked")
	public void deleteNewAliasInfo(List list) throws Exception;
    
    @SuppressWarnings("unchecked")
	public void addAliasInfo(List addList) throws Exception;
    
    @SuppressWarnings("unchecked")
	public void deleteAliasInfo(List delList) throws Exception;
} 
