package com.ait.pa.dao;

import java.util.List;


	
public interface PaInputItemParamDao {
	
	public void addPaInputItemParamInfo(Object object) throws Exception;
	
	public void updatePaInputItemParam(Object object) throws Exception;
	
	public int checkAddPaInputItemParamInfo(Object object);
	
	public void deletePaInputItemParamInfo(Object object) throws Exception ;
	
	public int updatePaInputItemParamInfoAll(String item_no) throws Exception ;
	
	public int getPaInputItemParamCnt(Object object);
	
	public Object getPaInputItemParamInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemParamList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemParamList(Object object, int currentPage, int pageSize);
	
	public void updatePaInputItemParamInfo(Object object) throws Exception;
	
	public void updatePaInputItemMappingInfo(Object object) throws Exception;
	
	public int checkDeletePaInputItemParamInfo(Object object);
	
	public int checkDeletePaInputItemParamInfoSummary(Object object);
	 @SuppressWarnings("unchecked")
	public void deleteCheckPaInputItemData(Object object)throws Exception;
	 @SuppressWarnings("unchecked")
	public void deleteCheckPaBasicItemData(Object object)throws Exception;
	 @SuppressWarnings("unchecked")
	public void deleteCheckPaInputItemDataFSE(Object object)throws Exception;
	 @SuppressWarnings("unchecked")
	public void clearPaInputItemDataCallback(Object object)throws Exception;
}
