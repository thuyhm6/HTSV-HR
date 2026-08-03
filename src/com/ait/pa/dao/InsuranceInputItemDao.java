package com.ait.pa.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceInputItemDao.java
 * @Description:
 * @Create date: 2012-1-16 下午06:55:08
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@SuppressWarnings("unchecked")
public interface InsuranceInputItemDao {

	public Object getInsuranceInputItemInfo(Object object);
	
	public List getInsuranceInputItemList(Object object);
	
	public int getInsuranceInputItemCnt(Object object);
	
	public List getInsuranceInputItemList(Object object, int currentPage, int pageSize);
	
	public int checkAddInsuranceInputItemInfo(Object object);
	
	public int checkAddInsuranceInputItemParamInfo(Object object);
	
	public void addInsuranceInputItemInfo(Object object) throws Exception;
	
	public int addInsuranceInputItemParamInfo(Object object);
	
	public void updateInsuranceInputItemInfo(Object object) throws Exception;
	
	/****
	 * 修改和老系统保险项目的mapping关系
	 * @param object
	 * @return
	 */
	public int updateIsInputItemParamInfo(Object object) throws Exception;
	
	public int updateInsuranceInputItemParamInfo(Object object);
	
	public int checkDeleteInsuranceInputItemInfo(Object object) ;
	
	public void deleteInsuranceInputItemInfo(Object object) throws Exception ;
	
	public int checkDeleteInsuranceInputItemParamInfo(Object object) ;
	
	public int deleteInsuranceInputItemParamInfo(Object object) throws SQLException ;
	
	public Object getInsuranceInputItemParamInfo(Object object);
	
	public List getInsuranceInputItemParamList(Object object);
	
	public List getIsParamDataList(Object object);
	
	public List getIsParamDataIsCpnyIdList(Object object);
	
	public List getIsParamDataIsDeptNoList(Object object);
	
	public List getIsParamDataTwoList(Object object);
	
	public int getInsuranceInputItemParamCnt(Object object);
	
	public List getInsuranceInputItemParamList(Object object, int currentPage, int pageSize);
	
	public List getDistinctFieldList(Object object) ;
	
	public List getInsuranceInputItemDataListDistinctFieldIsEmpid(Object object) ;
	
	public List getInsuranceInputItemDataListDistinctFieldIsEmpid(Object object,
			int currentPage, int pageSize);
	
	public List getInsuranceInputItemDataListDistinctFieldIsCpnyId(Object object) ;
	
	public List getInsuranceInputItemDataListDistinctFieldIsCpnyId(Object object,
			int currentPage, int pageSize);
	
	public List getInsuranceInputItemDataListDistinctFieldIsDeptNo(Object object) ;
	
	public List getInsuranceInputItemDataListDistinctFieldIsDeptNo(Object object,
			int currentPage, int pageSize);
	
	public List getInsuranceInputItemDataListDistinctFieldIsNotEmpid(Object object) ;
	
	public List getInsuranceInputItemDataListDistinctFieldIsNotEmpid(Object object,
			int currentPage, int pageSize);
	
	public int createInsuranceInputItemInfo(Object obj) ;
	
	public int createAddInsuranceInputItemDataInfo(Object object) ;
	
	public List getAddInsuranceInputItemDataListDistinctFieldIsEmpid(Object object) ;
	
	public List getAddInsuranceInputItemDataListDistinctFieldIsNotEmpid(Object object) ;
	
	public List getInsuranceInputItemDataListByParamNo(Object object) ;
	
	public Object getInsuranceInputItemDataInfo(Object object);
	
	public int addInsuranceInputItemDataInfo(Object obj) ;
	
	public int addInsuranceInputItemOtherDataInfo(Object obj) ;
	
	public int deleteInsuranceInputItemDataInfo(Object object) ;
	
	public int deleteInsuranceInputItemDataInfoType(Object object) ;
	
	public int checkDeleteInsuranceInputItemDataInfo(Object object);
	
	public int checkDeleteInsuranceInputItemDataInfoType(Object object);
	
	public int deleteInsuranceInputItemDataBatchInfo(Object object) ;
	
	public int deleteInsuranceInputItemDataBatchInfoType(Object object) ;
	
	public int updateInsuranceInputItemDataInfo(Object object) ;
	
	public int updateInsuranceInputItemDataInfoMonth(Object object) ;
	
	public int updateInsuranceInputItemDataOtherInfoMonth(Object object) ;
	
	public int updateInsuranceInputItemDataInfoOther(Object object) ;
	
	public int getInsuranceInputItemDataListDistinctFieldIsEmpidCnt(Object object) ;
	
	public int getInsuranceInputItemDataListDistinctFieldIsCpnyIdCnt(Object object) ;
	
	public int getInsuranceInputItemDataListDistinctFieldIsDeptNoCnt(Object object) ;
	
	public int getInsuranceInputItemDataListDistinctFieldIsNotEmpidCnt(Object object) ;
	
	public List getInsuranceInputItemDataPersonList(Object object);

	public List getInsuranceInputItemDataPersonList(Object object, int currentPage,
			int pageSize);

	public int getInsuranceInputItemDataPersonListCnt(Object object);
	
	public List getAddInsurancePersonalInputList(Object object);
	
	public List getAddInsurancePersonalInputList(Object object, int currentPage,
			int pageSize);
	
	public int getAddInsurancePersonalInputListCnt(Object object);
	
	public int checkUpdateInsuranceInputItemDataPersonInfo(Object object);
	
	public int updateInsuranceInputItemDataPersonInfo(Object object);
	
	public int addInsuranceInputItemDataPersonInfo(Object object);
	
	public void deleteInsuranceInputItemDataPersonInfo(Object object) throws Exception;
	
	
	
	//2013-09-01 lufeng
	public List getInsuranceInputApplyDataListDistinctFieldIsEmpid(Object object) ;
	
	public List getInsuranceInputApplyDataListDistinctFieldIsEmpid(Object object,
			int currentPage, int pageSize);
	
	public List getInsuranceInputApplyDataListDistinctFieldIsCpnyId(Object object) ;
	
	public List getInsuranceInputApplyDataListDistinctFieldIsCpnyId(Object object,
			int currentPage, int pageSize);
	
	public List getInsuranceInputApplyDataListDistinctFieldIsDeptNo(Object object) ;
	
	public List getInsuranceInputApplyDataListDistinctFieldIsDeptNo(Object object,
			int currentPage, int pageSize);
	
	public List getInsuranceInputApplyDataListDistinctFieldIsNotEmpid(Object object) ;
	
	public List getInsuranceInputApplyDataListDistinctFieldIsNotEmpid(Object object,
			int currentPage, int pageSize);
	
	public int getInsuranceInputApplyDataListDistinctFieldIsEmpidCnt(Object object) ;
	
	public int getInsuranceInputApplyDataListDistinctFieldIsCpnyIdCnt(Object object) ;
	
	public int getInsuranceInputApplyDataListDistinctFieldIsDeptNoCnt(Object object) ;
	
	public int getInsuranceInputApplyDataListDistinctFieldIsNotEmpidCnt(Object object) ;
	
	public int addInsuranceInputItemDataApply(Object obj) ;
	
	public int updateInsuranceInputItemDataApplyMonth(Object object) ;
	
	public int addInsuranceInputItemOtherDataApply(Object obj) ;
	
	public int updateInsuranceInputItemDataOtherApplyMonth(Object object) ;
	
	/**
	 * 通过/否决--保险输入项目申请数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveInsDataApply(Object object) throws Exception;
	
	/**
	 * 删除--保险输入项目申请数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteInsDataApply(Object object) throws Exception;
	
	/**
	 * 批量提交保险申请申请  有PERSON_ID
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-5 上午11:50:40 
	* @version V1.0
	 */
	public int updateInsuranceApply(Object object) ;
	
	/**
	 * 批量提交保险申请申请  无PERSON_ID
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-5 上午11:55:26 
	* @version V1.0
	 */
	public int updateInsuranceApplyOther(Object object) ;
	
	/**
	 * 询全部申请项目 是Y的字段
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-5 下午8:43:10 
	* @version V1.0
	 */
	public List getInsuranceInputItemDataForApplyName(Object object);
	
	/**
	 * 根据申请项目查询出相同所在地不同法人的相同的需要申请的项目
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-9 下午11:29:45 
	* @version V1.0
	 */
	public List getUnifySuitCompanyList(Object object);
	public List getUnifySuitCompanyList2(Object object);
	public List getUnifySuitCompanyList3(Object object);
	public List getUnifySuitCompanyList4(Object object);
	
	/**
	 * 提前获取到申请信息的编号 用关联到附件字段
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-11 下午1:09:12 
	* @version V1.0
	 */
	public String getNextparamDataNo();

	/**
	 *  保存社保申请的附件信息
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-11 下午2:36:52 
	* @version V1.0
	 */
	public void insertAccessory(Object object) throws Exception;
	
	/**
	 * 根据申请的id查询出相应的附件内容
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-12 上午12:05:25 
	* @version V1.0
	 */
	public List getaccessoryList(Object object)throws Exception;
	
	/**
	 * 查询登陆用户是否拥有法人管理权限  123293 否则返回0
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-16 下午3:22:51 
	* @version V1.0
	 */
	public int getUserRolesGroupCnt(Object object)throws Exception;

	
	/**
	 * 查找输入项目的NO和名称
	 * 只查询DISTINCT_FIELD='PERSON_ID' 并且  apply_flag 是不等于Y的数据
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-20 下午6:43:39 
	* @version V1.0
	 */
	public List getItemNameList(Object object);

	/**
	 *  查找输入项目 名称符合的数据 加字段 这里只显示 导入的数据
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-20 下午7:34:13 
	* @version V1.0
	 */
	public List getItemBatchImportList(Object obj, int currentPage, int pageSize);

	public List getItemBatchImportList(Object obj);

	public int getItemBatchImportListoCnt(Object object) throws Exception;


	/**
	 *PA 工资输入项目
	 * 查找输入项目的NO和名称
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-22 下午3:01:19 
	* @version V1.0
	 */
	public List getItemNameListPa(Object object);

	/**
	 * 查找输入项目 名称符合的数据 加字段 这里只显示 导入的数据 pa工资
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-22 下午3:25:05 
	* @version V1.0
	 */
	public List getItemBatchImportListPa(Object obj, int currentPage, int pageSize);

	public List getItemBatchImportListPa(Object object);

	/**
	 * 查找输入项目 名称符合的数据 加字段 这里只显示 导入的数据 数量  pa 工资用
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-22 下午3:46:15 
	* @version V1.0
	 */
	public int getItemBatchImportListoCntPa(Object object) throws Exception;

	public List getAddInsurancePersonalInputItemList(Map paramMap)throws Exception;
	/**
	 * 查找基础项目 名称符合的数据 加字段 这里只显示 导入的数据 pa工资
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-23 上午10:39:44 
	* @version V1.0
	 */
	public List getItemBatchImportListPaBasis(Object obj, int currentPage, int pageSize);

	public List getItemBatchImportListPaBasis(Object obj);
	/**
	 * 找基础项目 名称符合的数据 加字段 这里只显示 导入的数据 数量  pa 工资用
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-23 上午11:00:13 
	* @version V1.0
	 */
	public int getItemBatchImportListoCntPaBasis(Object obj)throws Exception;

	public List getInsuranceInputItemPersonTempAllList(Map paramMap)throws Exception;
	/**
	 * 保险申请项目通知
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-25 下午5:48:36 
	* @version V1.0
	 */
	public int addApplyInform(Object obj);

	public int updateInsuranceInputItemDataValue(LinkedHashMap paramMap)throws Exception;

	public int getInsuranceInputItemDataListDistinctFieldIsNotEmpidCnt1(Object obj);

	public int getViewInsureSelfCnt(LinkedHashMap paramMap);
	/**
	 * 保险查看（个人别）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-30 下午04:07:51 
	* @version V1.0
	 */
	public List getViewInsureSelfList(Map paramMap);
	
	public List getViewInsureSelfList(Map paramMap, int pageNum, int numPerPage);

	public int getViewInsureDeptCnt(LinkedHashMap paramMap);
	/**
	 * 保险查看（部门别）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-30 下午04:08:17 
	* @version V1.0
	 */
	public List getViewInsureDeptList(Map paramMap);

	public List getViewInsureDeptList(Map paramMap, int pageNum, int numPerPage);

	public List getItemNameListPa2(Object object);

	public List getWelfareArea(Map paramMap);

	public String importISParamDataExcelExcel(LinkedHashMap paramMap);

	public int getImportExcelTempISParamDataListErrCnt(LinkedHashMap paramMap);

	public int getImportExcelTempISParamDataListCnt(LinkedHashMap paramMap);

	public List getImportExcelTempISParamDataList(LinkedHashMap paramMap);

	public List getImportExcelTempISParamDataList(LinkedHashMap paramMap,
			int pageNum, int numPerPage);

	/** 
	* @Title: getItemBatchImportDataTemp 
	* @Description: TODO 获取项目临时表导入数据
	* @param @param paramMap
	* @param @return    
	* @return List    
	* @throws 
	*/
	public List getItemBatchImportDataTemp(LinkedHashMap paramMap);
	public List getItemBatchImportDataTemp(LinkedHashMap paramMap, int currentPage,
			int pageSize);

	/** 
	* @Title: getItemBatchImportDataTempCnt 
	* @Description: TODO 获取项目临时表的数据量
	* @param @param paramMap
	* @param @param pageNum
	* @param @param numPerPage
	* @param @return    
	* @return List    
	* @throws 
	*/
	public int getItemBatchImportDataTempCnt(LinkedHashMap paramMap);

	public String submitItemBatchData(LinkedHashMap paramMap) throws SQLException;

	int getItemBatchImportDataTempErrorCnt(LinkedHashMap paramMap);

	public boolean delExceplImportLine(Map paramMap);

	List getItemBatchImportListPaBasisAndParam(Object obj, int currentPage,
			int pageSize);

	List getItemBatchImportListPaBasisAndParam(Object object);

	public int updateItemBatchDataForBasic(Object paramMap);

	int updateItemBatchDataForParam(Object obj);

	public void deleteErrorOldItemBatchData(LinkedHashMap paramMap);

}