package com.ait.pa.service.wagebase;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaBasicItemSer.java
 * @Description:
 * @Create date: 2012-2-7 下午07:49:51
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface PaBasicItemSer {
	
	public Object getPaBasicItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemList(HttpServletRequest request) ;
	
	public int getPaBasicItemCnt(HttpServletRequest request);
	
	public int checkAddPaBasicItemInfo(HttpServletRequest request);
	
	public int addPaBasicItemInfo(HttpServletRequest request);
	
	public int addPaBasicItemInfo(HttpServletRequest request,String item_no);
	
	public int updatePaBasicItemInfo(HttpServletRequest request);
	
	public int checkDeletePaBasicItemInfo(HttpServletRequest request) ;
	
	public int deletePaBasicItemInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemParamListNotPageNum(HttpServletRequest request);
	
	public int getPaBasicItemDataListCnt(HttpServletRequest request);
	
	public int createAddPaBasicItemDataInfo(HttpServletRequest request) ;
	
	public int updatePaBasicItemDataInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getAddPaBasicItemDataList(HttpServletRequest request) ;
	
	public int addPaBasicItemDataInfo(HttpServletRequest request);
	
	public int updateAndAddPaBasicItemDataInfo(HttpServletRequest request) ;
	
	public Object getPaBasicItemParamInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemParamList(HttpServletRequest request) ;
	
	public int getPaBasicItemParamCnt(HttpServletRequest request);
	
	public int checkAddPaBasicItemParamInfo(HttpServletRequest request);
	
	public int checkAddPaBasicItemParamInfo(HttpServletRequest request, String cpny_id, String item_no);
	
	public int addPaBasicItemParamInfo(HttpServletRequest request);
	
	public int addPaBasicItemParamInfo(HttpServletRequest request, String cpny_id, String item_no);
	
	public int updatePaBasicItemParamInfo(HttpServletRequest request, String cpny_id, String item_no);
	
	public int updatePaBasicItemParamInfo(HttpServletRequest request);
	
	public int updatePaBasicItemMappingInfo(HttpServletRequest request);
	
	public int checkDeletePaBasicItemParamInfo(HttpServletRequest request) ;
	
	public int deletePaBasicItemParamInfo(HttpServletRequest request);
	
	public Object getPaBasicItemDataInfo(HttpServletRequest request) ;
	
	public int deletePaBasicItemDataBatchInfo(HttpServletRequest request);
	
	public int deletePaBasicItemDataBatchInfoType(HttpServletRequest request);
	
	public int checkDeletePaBasicItemDataInfo(HttpServletRequest request);
	
	public int checkDeletePaBasicItemDataInfoType(HttpServletRequest request);
	
	public int deletePaBasicItemDataInfo(HttpServletRequest request);
	
	public int deletePaBasicItemDataInfoType(HttpServletRequest request);
	
	public int createAddPaBasicInputItemDataInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataTwoList(HttpServletRequest request) ;
	
	public int addPaBasicInputItemDataInfo(HttpServletRequest request);
	
	public int addPaBasicInputItemOtherDataInfo(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getPaBasicInputItemDataList(HttpServletRequest request)throws Exception;

	@SuppressWarnings("unchecked")
	public List paBasicInputItemAllList(HttpServletRequest request)throws Exception;

	public int insertOrUpdatePaBasicItemData(HttpServletRequest request)throws Exception;
	
	//添加派遣地
	public int addPaSendToAdministrationItemInfo(HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List viewPaSendToAdministrationList(HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getPaSendToAdministrationListCnt(HttpServletRequest request)throws Exception;
	
	
	public int deletePaSendToAdministrationInfo(HttpServletRequest request);

	/**
	 * 根据item_no查找和该项目已经mapping的记录删除掉
	 * @param object
	 * @return
	 */
	public int updatePaBasicItemParamInfoAll(HttpServletRequest request) throws SQLException;

	public List getImportExcelTempPaBasicItemList(HttpServletRequest request);

	public int getImportExcelTempPaBasicItemListCnt(HttpServletRequest request);

	public int getImportExcelTempPaBasicItemListErrCnt(
			HttpServletRequest request);

	public String importPaBasicItemExcelExcel(HttpServletRequest request);

	public int getImportExcelTempPaParamListCnt(HttpServletRequest request);
	
	public int getImportExcelTempPaParamMonthListCnt(HttpServletRequest request);
	
	public int getImportExcelTempPaParamListGradeCnt(HttpServletRequest request);

	public List getImportExcelTempPaParamList(HttpServletRequest request);
	
	public List getImportExcelTempPaParamMonthList(HttpServletRequest request);
	
	public List getImportExcelTempPaParamGradeList(HttpServletRequest request);

	public int getImportExcelTempPaParamListErrCnt(HttpServletRequest request);
	
	public int getImportExcelTempPaParamMonthListErrCnt(HttpServletRequest request);
	
	public int getImportExcelTempPaParamListErrGradeCnt(HttpServletRequest request);

	public String importPaParamExcel(HttpServletRequest request);
	
	public String importPaParamMonthExcel(HttpServletRequest request);
}
