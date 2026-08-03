package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaInputItemDao.java
 * @Description:
 * @Create date: 2012-1-19 下午09:23:14
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface PaInputItemDao {

	public Object getPaInputItemInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemList(Object object);
	
	public int getPaInputItemCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemList(Object object, int currentPage, int pageSize);
	
	public int checkAddPaInputItemInfo(Object object);
	
	public void addPaInputItemInfo(Object object) throws Exception;
	
	public void addPaInputItemInfoAffirm(Object object) throws Exception;
	
	public void updatePaInputItemInfo(Object object) throws Exception;
	
	public int checkDeletePaInputItemInfo(Object object) ;
	
	public void deletePaInputItemInfo(Object object) throws Exception ;
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsEmpid(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsPositionNo(Object object) ;
	
	public int getPaInputItemDataListDistinctFieldIsEmpidCnt(Object object) ;
	
	public int getPaInputItemDataListDistinctFieldIsPositionNoCnt(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsEmpid(Object object,
			int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsPositionNo(Object object,
			int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsWorkAreaNo(Object object,
			int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsCpnyId(Object object) ;
	
	public int getPaInputItemDataListDistinctFieldIsCpnyIdCnt(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsCpnyId(Object object,
			int currentPage, int pageSize);
	
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsDeptNo(Object object) ;
	
	public int getPaInputItemDataListDistinctFieldIsDeptNoCnt(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsDeptNo(Object object,
			int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsNotEmpid(Object object) ;
	
	public int getPaInputItemDataListDistinctFieldIsNotEmpidCnt(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListDistinctFieldIsNotEmpid(Object object,
			int currentPage, int pageSize) ;
	
	public int updatePaInputItemDataInfo(Object object) ;
	
	public int updatePaInputItemDataInfoOther(Object object) ;
	
	public int createPaInputItemInfo(Object obj) ;
	
	public int createAddPaInputItemDataInfo(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getAddPaInputItemDataListDistinctFieldIsEmpid(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getAddPaInputItemDataListDistinctFieldIsNotEmpid(Object object) ;
	
	public int addPaInputItemDataInfo(Object obj) ;
	
	public int addPaInputItemOtherDataInfo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getPaParamDataList(Object object);
	
	public List getPaParamDataWorkAreaList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaParamDataIsCpnyIdList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaParamDataIsPositionNoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaParamDataIsDeptNoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaParamDataTwoList(Object object);
	
//	@SuppressWarnings("unchecked")
//	public int addPaInputItemDataInfo(List list) ;
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataListByParamNo(Object object) ;
	
	public Object getPaInputItemDataInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaInputItemDataPersonList(Object object);

	@SuppressWarnings("unchecked")
	public List getPaInputItemDataPersonList(Object object, int currentPage,
			int pageSize);

	public int getPaInputItemDataPersonListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAddPaPersonalInputList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAddPaPersonalInputList(Object object, int currentPage,
			int pageSize);
	
	public int getAddPaPersonalInputListCnt(Object object);
	
	public int checkUpdatePaInputItemDataPersonInfo(Object object);
	
	public int updatePaInputItemDataPersonInfo(Object object);
	
	public int updatePaInputItemDataPersonInfoForMonth(Object object);
	
	public int addPaInputItemDataPersonInfo(Object object);
	
	public int checkDeletePaInputItemDataInfo(Object object);
	
	public int checkDeletePaInputItemDataInfoType(Object object);	

	public int deletePaInputItemDataInfo(Object object) ;
	
	public int deletePaInputItemDataInfoType(Object object);
	
	public int clearPaInputItemDataInfoType(Object object);
	
	public int deletePaInputItemDataBatchInfo(Object object);
	
	public int deletePaInputItemDataBatchInfoType(Object object);
	
	@SuppressWarnings("unchecked")
	public List getEmpIdList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getEmpIdList(Object object, int currentPage, int pageSize);
	
	public int getEmpIdListCnt(Object object);
	
	public void deletePaInputItemDataPersonInfo (Object object) throws Exception;
	
	public int updatePaInputItemDataInfoMonth(Object object);
	
	public int updatePaInputItemDataOtherInfoMonth(Object object);

	@SuppressWarnings("unchecked")
	public List getAddPaPersonalInputItemList(Map paramMap)throws Exception;

	@SuppressWarnings("unchecked")
	public List getPaInputItemPersonAllList(Map paramMap)throws Exception;

	@SuppressWarnings("unchecked")
	public int updatePaInputItemDataValue(LinkedHashMap paramMap)throws Exception;
	
}
