package com.ait.pa.service.salary;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

public interface PaInputItemParamSer {
	
	public Object getPaInputItemParamInfo(HttpServletRequest request) ;
	
	public Object getPaInputItemParamDataInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemParamList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemParamListNotPageNum(HttpServletRequest request) ;
	
	public int getPaInputItemParamCnt(HttpServletRequest request);
	
	public int checkAddPaInputItemParamInfo(HttpServletRequest request);
	
	public int checkAddPaInputItemParamInfo(HttpServletRequest request,String cpny_id,String param_item_no);
	
	public int addPaInputItemParamInfo(HttpServletRequest request);
	
	public int addPaInputItemParamInfo(HttpServletRequest request,String cpny_id,String param_item_no);
	
	public int updatePaInputItemParamInfo(HttpServletRequest request,String cpny_id,String param_item_no);
	
	public int updatePaInputItemParamInfo(HttpServletRequest request);
	

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updatePaInputItemMappingInfo(HttpServletRequest request);
	
	public int deletePaInputItemParamInfo(HttpServletRequest request);
	
	public int updatePaInputItemParamInfoAll(HttpServletRequest request) throws Exception;
	
	public int checkDeletePaInputItemParamInfo(HttpServletRequest request);
	
	public int checkDeletePaInputItemParamInfoSummary(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int deleteCheckPaInputItemData(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int deleteCheckPaInputItemDataOther(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int clearPaInputItemDataCallback(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int clearCheckPaInputItemDataOther(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int deleteCheckPaBasicItemData(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int deleteCheckPaInputItemDataFSE(HttpServletRequest request);
	
	
}
