package com.ait.pa.dao;

import java.util.List;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusInputItemDataDao.java
 * @Description:
 * @Create date: 2012-2-3 上午10:08:06
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusInputItemDataDao {
	public int checkAddPaBonusInputItemDataInfo(Object object);

	public int checkUpdateBonusInputItemDataPersonInfo(Object object);
	
	public int checkBonusInputItemDataInfo(Object object);
	
	public int checkBonusInputItemDataInfoType(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataListDistinctFieldIsEmpid(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataListDistinctFieldIsNotEmpid(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataListDistinctFieldIsEmpid(Object object,
			int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataListDistinctFieldIsCpnyId(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataListDistinctFieldIsCpnyId(Object object,
			int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataListDistinctFieldIsDeptNo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataListDistinctFieldIsDeptNo(Object object,
			int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataListDistinctFieldIsNotEmpid(Object object,
			int currentPage, int pageSize);

	public int getBonusInputItemDataListDistinctFieldIsEmpidCnt(Object object);

	public int getBonusInputItemDataListDistinctFieldIsNotEmpidCnt(Object object);
	
	public int getBonusInputItemDataListDistinctFieldIsCpnyIdCnt(Object object) ;
	
	public int getBonusInputItemDataListDistinctFieldIsDeptNoCnt(Object object) ;

	public int deleteBonusInputItemDataInfo(Object object);
	
	public int deleteBonusInputItemDataInfoType(Object object);
	
	public int deleteBonusInputItemDataBatchInfo(Object object);
	
	public int deleteBonusInputItemDataBatchInfoType(Object object);

	public int updateBonusInputItemDataInfo(Object object);
	
	public int updateBonusInputItemDataInfoOther(Object object);

	public int createBonusInputItemInfo(Object obj);

	public int createAddBonusInputItemDataInfo(Object object);

	@SuppressWarnings("unchecked")
	public List getAddBonusInputItemDataListDistinctFieldIsEmpid(Object object);

	@SuppressWarnings("unchecked")
	public List getAddBonusInputItemDataListDistinctFieldIsNotEmpid(
			Object object);

	@SuppressWarnings("unchecked")
	public List getAddBonusInputItemDataListDistinctFieldIsEmpid(Object object,
			int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public List getAddBonusInputItemDataListDistinctFieldIsNotEmpid(
			Object object, int currentPage, int pageSize);

	public int getAddBonusInputItemDataListDistinctFieldIsEmpidCnt(Object object);

	public int getAddBonusInputItemDataListDistinctFieldIsNotEmpidCnt(
			Object object);

	@SuppressWarnings("unchecked")
	public int addBonusInputItemDataInfo(List list);
	
	@SuppressWarnings("unchecked")
	public List getBnParamDataList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getBnParamDataIsCpnyIdList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getBnParamDataIsDeptNoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getBnParamDataTwoList(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataPersonList(Object object);
	
	public int addBonusInputItemDataInfo(Object obj) ;
	
	public int addBonusInputItemOtherDataInfo(Object obj) ;
	
	public int updateBonusInputItemDataInfoMonth(Object object) ;
	
	public int updateBonusInputItemDataOtherInfoMonth(Object object) ;

	@SuppressWarnings("unchecked")
	public List getBonusInputItemDataPersonList(Object object, int currentPage,
			int pageSize);

	public int getBonusInputItemDataPersonCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAddBonusPersonalInputList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAddBonusPersonalInputList(Object object, int currentPage,
			int pageSize);
	
	public int getAddBonusPersonalInputListCnt(Object object);
	
	public int updateBonusInputItemDataPersonInfo(Object object);
	
	public int addBonusInputItemDataPersonInfo(Object object);
}
