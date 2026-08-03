package com.ait.is.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface StopInsureDao {
	@SuppressWarnings("unchecked")
	public List getPaBenStopInsureListBz(Object obj, int pageNum, int numPerPage);
	@SuppressWarnings("unchecked")
	public List getPaBenStopInsureListBz(Object obj);

	public int getStopInsureCnt(Object obj);

	public void deleteStopInsureInfo(Object obj);

	public void allowPaBenStopInsureUpdate(String id);
	@SuppressWarnings("unchecked")
	public List getAllowPaBenStopInsureUpdate(Object obj);

	public void backPaBenStopInsureUpdateBz();

	public void updatePaBenManageAddInfoBz(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getNOInsStopNumList(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getInsuranceAreaListByCpnyId(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getInsuranceParamDataChList(Object obj, int pageNum, int numPerPage);
	
	@SuppressWarnings("unchecked")
	public List getInsuranceParamDataChList(Object obj);

	public int getInsuranceParamDataChCnt(Object obj);
	
	/**
	 * 添加保险参数
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addInsuranceParamData(Object obj) throws Exception;
	
	/**
	 * 修改保险参数
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void updateInsuranceParamData(Object obj) throws Exception;
	
	/**
	 * 导入保险参数--查看信息列表(get insurance param data import list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getIsParamImportInfoList(Object object);
	
	/**
	 * 导入保险参数--查看信息列表(get insurance param data import list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getIsParamImportInfoList(Object object, int currentPage, int pageSize);
	
	/**
	 * 导入保险参数--查看信息列表总数(get insurance param data import list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getIsParamImportInfoListCnt(Object obj) throws Exception;
	
	/**
	 * 导入保险参数--查看错误的信息列表总数(get error insurance param data import list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getIsParamImportInfoListErrorCnt(Object obj) throws Exception;
	
	/**
	 * 删除保险参数信息(delete insurance param data information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delIsParamDataImport(Object object) throws Exception;
	
	/**
	 * 批量保存导入的保险参数(add insurance param data of import)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addIsParamDataImport(List list) throws Exception;
	
	/**
	 * 更新导入的保险参数的check结果(update insurance param data of import for check result)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int updateIsParamDataCheckResult(Object object) throws Exception;
	
	/**
	 * 验证导入的大区编码是否存在
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceAreaCheckListByCpnyId(Object obj);
	
	/**
	 * 验证导入的福利地区是否存在
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsrareaCheckListByCpnyId(Object obj);
	
	/**
	 * 验证导入的福利项目是否存在
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsureCheckListByCpnyId(Object obj);
	
	/**
	 * 删除临时表中所有导入的保险参数(delete insurance param data information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean cancelIsParamDataImport(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getInsAreaInfoListByKey(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInsAreaInfoListByKey(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getInsureInfoListByKey(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInsureInfoListByKey(Object object, int currentPage, int pageSize);
}
