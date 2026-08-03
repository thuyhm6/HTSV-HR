package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public interface ExcelUtilDao {

	public int getCodeNoByName(Object name);
	
	public String queryAgreeno();
	
	public void insertEduTrainAgreement(Object obj);
	public int insertOTLimit(Object obj);
	public int insertTrainResult(Object obj);
	public int insertTeacherEvaluate(Object obj);
	public int insertVacPlan(Object obj);
	
	public void updateEduFreeEmployee(Object obj);
	
	public void updateEduTrainSyllabus(Object obj);
	
	public void deleteEduTrainSyllabus(Object obj) throws Exception;
	
	public void insertExcelData(Object obj) throws Exception;
	
	public void insertExcelDataOtApply(Object obj) throws Exception;
	
	public void insertExcelDataPaForLeftApply(Object obj) throws Exception;
	
	public void insertExcelDataInsObject(Object obj) throws Exception;
	
	public void insertExcelDataFundObject(Object obj) throws Exception;
	
	public int checkAddItemDataInfo(Object obj) ;
	
	public int checkAddItemDataInfoMonth(Object obj) ;
	
	public int updateItemDataInfoMonth(Object object);
	
	public int updatePaBasicItemDataInfoMonth(Object object);
	
	public int checkAddItemDataOtherInfo(Object obj) ;
	
	public int checkAddItemDataOtherInfoMonth(Object obj) ;
	
	public int updateItemDataOtherInfoMonth(Object object);
	
	public int updatePaBasicItemDataOtherInfoMonth(Object object);
	
	@SuppressWarnings("unchecked")
	public int insertHrLaborUnion(Object object) throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public List getExcelExportDataList(Object obj);
	
	
	
	//历史工资信息导出Excel专用2012-09-10(Lufeng)
	@SuppressWarnings("unchecked")
	public List getPaHistoryDataList(Object obj);
	
	@SuppressWarnings("unchecked")
	public List trainAgreementTemp(Object obj);
	
	public String getArItemID(Object name);
	
	@SuppressWarnings("unchecked")
	public List getContentNoByFiled(Object filed);
	
	public int checkPaProgressLock(Object name);
	
	public void deleteSummaryInfo(Object object) throws Exception;
	
	public int checkPersonalInfo(Object name);
	
	public int checkSpecialEmpImportCnt(Object name);
	
	@SuppressWarnings("unchecked")
	public List exportPersonalList(Object obj);
	
	public void deleteScheduleInfo(Object object) throws Exception;
	
	public void deleteIsParamDateInfo(Object object) throws Exception;
	
	public void deleteCordInfo(Object object) throws Exception;
	
	public String getDeptNo(Object name);
	
	public int getColumnCnt(Object name);
	
	public void deleteCordInfoByCardNo(Object object) throws Exception;
	
	public void deleteCordInfoByCardNo2(Object object) throws Exception;
	
	public void deleteWithTarget(Object obj, String target);
	
	public void insertWithTarget(Object obj, String target);
	
	public int checkCardNo(Object name);
	
	public int checkCardNo3(Object name);
	
	public int checkCardNo2(Object name);
	
	public int checkPaiQianDiGuanLiInfo(Object obj);
	
	public int checkPaiQianDiGuanLiFrInfo(Object obj);
	
	public int checkPaiQianDiGuanLiCsdjInfo(Object obj);
	
	public int checkPaiQianDiGuanLiSfInfo(Object obj);
	
	public int checkPaiQianDiGuanLiCsmcInfo(Object obj);
	
	public int checkPaiQianDiGuanLiDqmcInfo(Object obj);
	
	public int deletePaiQianDiGuanLiInfo(Object obj);
	
	public void deleteArMacRecordsInfo(Object object) throws Exception;
	
	public void deleteSapSpecialCaseAndWaitEmp(Object object) throws Exception;

	public void insertSelectedOptionsByExprotExcel(Object object)throws Exception;

	/**
	 * 查询excel导出需要填充的下拉列表值
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 下午02:57:43 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getExcelExportTypeList(LinkedHashMap paramMap)throws Exception;

	/**
	 * 查询之前是否有相同的记录
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 下午10:03:25 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public Integer preHasRecordForReward(LinkedHashMap tempMap)throws Exception;

	/**
	 * 查询之前是否有相同的记录
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 下午10:03:25 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public Integer preHasRecordForPunishment(LinkedHashMap tempMap)throws Exception;

	/**
	 * 将excel的数据导入到数据库中
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-30 下午01:55:04 
	* @version V1.0
	 */
	public void insertExcelDataForTransferOrder(Object obj)throws Exception;
	
	/**
	 * 将excel的数据导入到数据库中
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-30 下午01:55:04 
	* @version V1.0
	 */
	public void insertPaiQianDiExcelDataForTransferOrder(Object obj)throws Exception;
	
	/**
	 * 将excel的数据导入到数据库中
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-30 下午01:55:04 
	* @version V1.0
	 */
	public void insertPaForLeftMenImportTemp(Object obj)throws Exception;
	
	/**
	 * 将excel的数据导入到数据库中
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-30 下午01:55:04 
	* @version V1.0
	 */
	public void insertZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelDataForTransferOrder(Object obj)throws Exception;
	
	/**
	 * 查询当前员工的部门编号，职位编号，职务编号
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 下午10:34:09 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getCurrentEmpInfo(LinkedHashMap tempMap)throws Exception;

	/**
	 * 获得导入Excel的类型配置集合
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-2 上午01:34:51 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getImportTransferOrderTypeConfigList(LinkedHashMap paramMap)throws Exception;

	/**
	 * 获得导入Excel的类型code
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-2 上午02:31:50 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public String getImportExcelTransferOrderType(LinkedHashMap paramMap)throws Exception;

	/**
	 * 查询当前调令类型是否在HR_EXPERIENCE_INSIDE_SAVE表中有相应记录
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-2 下午03:25:46 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public Integer preHasRecordInCurrentType(LinkedHashMap tempMap)throws Exception;

	/**
	 * 查询显示不修改且可查看显示的字段
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-19 上午02:54:35 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getTransferOrderTypeByParams(LinkedHashMap paramMap)throws Exception;
	
	/**
	 * 导入经济补偿金
	 * @param object
	 * @throws Exception
	 */
	public int insertPaEccInfo(Object object) throws Exception;
	
	/**
	 * 检查该月经济补偿金表中是否存在该员工记录
	 * @param object
	 * @return
	 */
	public int checkAddPaEccInfo(Object object);
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String checkEccImportHasEmp(Object object) throws Exception;
	
	/**
	 * 
	 * @param parameterObject
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updatePaEccInfo(Object parameterObject) throws Exception;
	/**
	 * 营业员评价数据导入  临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteImportSalesEvalTemp(LinkedHashMap paramMap) throws Exception;
	
	/**
	 * 根据导入的报表数据 更新对应的信息
	 * @param List
	 * @author weizhengchen
	 * @return
	 */
	public int updateImportData(Object obj);
	
	/**
	 * 临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteImportTemp(LinkedHashMap paramMap) throws Exception;
	
	/**
	 * 表数据修改 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void updateImportTemp(LinkedHashMap paramMap) throws Exception;
	/**
	 * 临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void deletePaiQianDiImportTemp(LinkedHashMap paramMap) throws Exception;
	
	/**
	 * 临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void deletePaForLeftMenImportTemp(LinkedHashMap paramMap) throws Exception;
	
	/**
	 * 临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteZuiDiGongZiBiaoZhunFeiCuXiaoYuanImportTemp(LinkedHashMap paramMap) throws Exception;
	
	/**
	 * 营业员提成数据导入  临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteImportSalesInctTemp(LinkedHashMap paramMap) throws Exception;

	@SuppressWarnings("unchecked")
	public int insertEssLeaveApplyTbTemp(Object object) throws Exception;

	/**
	 * 教育实绩数据导入  临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteImportPromotoGradeTemp(LinkedHashMap paramMap) throws Exception;

	public void insertExcelDataIsParam(Object obj) throws Exception;
	
	/**
	 * 临时人员离职数据导入  临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.10.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteImportTempEmpResignTemp(LinkedHashMap paramMap) throws Exception;
	/**
	 * 临时人员发令数据导入  临时表数据清理 
	 * @param obj
	 * @return List
	 * @Create date: 2014.10.10
	 */
	@SuppressWarnings("unchecked")
	public void deleteImportTempEmpTransferOrderTemp(LinkedHashMap paramMap) throws Exception;

	public int selectPersonidCount(Object obj);
	
	public void insertParamTempBatch(List<Object> list) throws Exception;
	public void changeParamDataBatch(Map<String, Object> param) throws Exception;

	public void createTempTable(Map<String, Object> param) throws Exception;
	public void dropTempTable(Map<String, Object> param) throws Exception;

	public void insertPaAccountTempBatch(List<Object> list) throws Exception;
}