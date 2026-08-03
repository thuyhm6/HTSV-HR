package com.ait.pa.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaBasicItemDao.java
 * @Description:
 * @Create date: 2012-2-7 下午08:13:04
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface PaBasicItemDao {

	public Object getPaBasicItemInfo(Object object);
	
	public Object getPaBasicItemDataInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemList(Object object);
	
	public int getPaBasicItemCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemList(Object object, int currentPage, int pageSize);
	
	public int checkAddPaBasicItemInfo(Object object);
	
	public void addPaBasicItemInfo(Object object) throws Exception;
	
	public void addPaBasicItemInfoAffirm(Object object) throws Exception;
	
	public void updatePaBasicItemInfo(Object object) throws Exception;
	
	public int checkDeletePaBasicItemInfo(Object object) ;
	
	public void deletePaBasicItemInfo(Object object) throws Exception ;
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsEmpid(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsEmpid(Object object,
			int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsPositionNo(Object object) ;
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsDeptArea(Object object) ;
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsPositionNo(Object object,
			int currentPage, int pageSize);

	public List getPaBasicItemDataListDistinctFieldIsDeptArea(Object object,
			int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsDeptNo(Object object) ;
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsDeptNo(Object object,
			int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsCpnyId(Object object) ;
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsCpnyId(Object object,
			int currentPage, int pageSize);
	
	public int getPaBasicItemDataListDistinctFieldIsEmpidCnt(Object object) ;
	
	public int getPaBasicItemDataListDistinctFieldIsPositionNoCnt(Object object) ;
	
	public int getPaBasicItemDataListDistinctFieldIsDeptAreaCnt(Object object) ;
	
	public int getPaBasicItemDataListDistinctFieldIsDeptNoCnt(Object object) ;
	
	public int getPaBasicItemDataListDistinctFieldIsCpnyIdCnt(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsNotEmpid(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemDataListDistinctFieldIsNotEmpid(Object object,
			int currentPage, int pageSize) ;
	
	public int getPaBasicItemDataListDistinctFieldIsNotEmpidCnt(Object object) ;
	
	public int updatePaBasicItemDataInfo(Object object) ;
	
	public int updatePaBasicItemDataInfoOther(Object object) ;
	
	public int createAddPaBasicItemDataInfo(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getAddPaBasicItemDataListDistinctFieldIsEmpid(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getAddPaBasicItemDataListDistinctFieldIsNotEmpid(Object object) ;
	
	@SuppressWarnings("unchecked")
	public int addPaBasicItemDataInfo(List list) ;
	
	@SuppressWarnings("unchecked")
	public int updateAndAddPaBasicItemDataInfo(List list) throws SQLException  ;
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemParamList(Object object);
	
	public int getPaBasicItemParamCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemParamList(Object object, int currentPage, int pageSize);
	
	public int checkAddPaBasicItemParamInfo(Object object) ;
	
	public int addPaBasicItemParamInfo(Object object);
	
	public int updatePaBasicItemParam(Object object);
	
	public Object getPaBasicItemParamInfo(Object object);
	
	/**
	 * 根据item_no查找和该项目已经mapping的记录删除掉
	 * @param object
	 * @return
	 */
	public int updatePaBasicItemParamInfoAll(String item_no) throws SQLException ;
	
	public int updatePaBasicItemParamInfo(Object object);
	
	public int updatePaBasicItemMappingInfo(Object object);
	
	public int checkDeletePaBasicItemParamInfo(Object object) ;
	
	public int deletePaBasicItemParamInfo(Object object) throws SQLException ;
	
	public int checkDeletePaBasicItemDataInfo(Object object);
	
	public int checkDeletePaBasicItemDataInfoType(Object object);	

	public int deletePaBasicItemDataInfo(Object object) ;
	
	public int deletePaBasicItemDataInfoType(Object object);
	
	public int deletePaBasicItemDataBatchInfo(Object object);
	
	public int deletePaBasicItemDataBatchInfoType(Object object);
	
	public int createAddPaBasicInputItemDataInfo(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataIsCpnyIDList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataPositionNoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataDeptAreaList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataFieldOneList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataFieldTwoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicParamDataIsDeptNoList(Object object);
	
	public int addPaBasicInputItemDataInfo(Object obj) ;
	
	public int checkAddPaBasicItemDataInfo(Object obj) ;
	
	public int checkAddPaBasicItemDataInfoMonth(Object obj) ;
	
	public int checkAddPaBasicItemDataOtherInfo(Object obj) ;
	
	public int checkAddPaBasicItemDataOtherInfoMonth(Object obj) ;
	
	public int addPaBasicInputItemOtherDataInfo(Object obj) ;
	
	public int updatePaBasicItemDataInfoMonth(Object object);
	
	public int updatePaBasicItemDataOtherInfoMonth(Object object);

	@SuppressWarnings("unchecked")
	public List getPaBasicInputItemDataList(Map paramMap)throws Exception;

	@SuppressWarnings("unchecked")
	public List paBasicInputItemAllList(Map paramMap)throws Exception;

	@SuppressWarnings("unchecked")
	public int updatePaBasicItemData(LinkedHashMap paramMap)throws Exception;

	@SuppressWarnings("unchecked")
	public int updatePaBasicItemDataValue(LinkedHashMap paramMap)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addPaSendToAdministrationItemInfo(LinkedHashMap paramMap)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getPaSendToAdministrationList(Map paramMap)throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getPaSendToAdministrationListCnt(Map paramMap)throws Exception;
	
	

	public int deletePaSendToAdministrationInfo(Object object) throws SQLException ;

	public List getImportExcelTempPaBasicItemList(LinkedHashMap paramMap,
			int pageNum, int numPerPage);

	public List getImportExcelTempPaBasicItemList(LinkedHashMap paramMap);

	public int getImportExcelTempPaBasicItemListCnt(LinkedHashMap paramMap);

	public int getImportExcelTempPaBasicItemListErrCnt(LinkedHashMap paramMap);

	public String importPaBasicItemExcelExcel(LinkedHashMap paramMap);

	public List getImportExcelTempPaParamList(LinkedHashMap paramMap,
			int pageNum, int numPerPage);
	
	public List getImportExcelTempPaParamMonthList(LinkedHashMap paramMap,
			int pageNum, int numPerPage);
	
	public List getImportExcelTempPaParamGradeList(LinkedHashMap paramMap,
			int pageNum, int numPerPage);

	public int getImportExcelTempPaParamListCnt(LinkedHashMap paramMap);
	
	public int getImportExcelTempPaParamMonthListCnt(LinkedHashMap paramMap);
	
	public int getImportExcelTempPaParamListGradeCnt(LinkedHashMap paramMap);

	public int getImportExcelTempPaParamListErrCnt(LinkedHashMap paramMap);
	
	public int getImportExcelTempPaParamMonthListErrCnt(LinkedHashMap paramMap);
	
	public int getImportExcelTempPaParamListErrGradeCnt(LinkedHashMap paramMap);

	public List getImportExcelTempPaParamList(LinkedHashMap paramMap);
	
	public List getImportExcelTempPaParamMonthList(LinkedHashMap paramMap);
	
	public List getImportExcelTempPaParamGradeList(LinkedHashMap paramMap);

	public String importPaParamExcel(LinkedHashMap paramMap);
	
	public String importPaParamMonthExcel(LinkedHashMap paramMap);
	
}
