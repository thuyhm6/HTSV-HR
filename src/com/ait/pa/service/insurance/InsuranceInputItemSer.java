package com.ait.pa.service.insurance;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceInputItemSer.java
 * @Description:
 * @Create date: 2012-1-16 下午06:47:32
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@SuppressWarnings("unchecked")
public interface InsuranceInputItemSer {
	
	public Object getInsuranceInputItemInfo(HttpServletRequest request) ;
	
	public List getInsuranceInputItemList(HttpServletRequest request) ;
	
	public int getInsuranceInputItemCnt(HttpServletRequest request);
	
	public Object getInsuranceInputItemParamInfo(HttpServletRequest request) ;
	
	public List getIsParamDataList(HttpServletRequest request) ;
	
	public List getIsParamDataTwoList(HttpServletRequest request) ;
	
	public List getInsuranceInputItemParamList(HttpServletRequest request) ;
	
	public List getInsuranceInputItemParamListForData(HttpServletRequest request) ;
	
	public int getInsuranceInputItemParamCnt(HttpServletRequest request);
	
	public int checkAddInsuranceInputItemInfo(HttpServletRequest request);
	
	public int checkAddInsuranceInputItemParamInfo(HttpServletRequest request);
	
	public int addInsuranceInputItemInfo(HttpServletRequest request);
	
	public int addInsuranceInputItemParamInfo(HttpServletRequest request);
	
	public int updateInsuranceInputItemInfo(HttpServletRequest request);
	
	public int updateInsuranceInputItemParamInfo(HttpServletRequest request);
	
	/***
	 * mapping旧系统保险项目  只修改  map_code
	 * @param request
	 * @return
	 */
	public int updateIsInputItemParamInfo(HttpServletRequest request);
	
	public int checkDeleteInsuranceInputItemInfo(HttpServletRequest request) ;
	
	public int deleteInsuranceInputItemInfo(HttpServletRequest request);
	
	public int checkDeleteInsuranceInputItemParamInfo(HttpServletRequest request) ;
	
	public int deleteInsuranceInputItemParamInfo(HttpServletRequest request);
	
	public List getDistinctFieldList(HttpServletRequest request);
	
	public List getInsuranceInputItemDataList(HttpServletRequest request);
	
	public int deleteInsuranceInputItemDataInfo(HttpServletRequest request);
	
	public int checkDeleteInsuranceInputItemDataInfo(HttpServletRequest request);
	
	public int checkDeleteInsuranceInputItemDataInfoType(HttpServletRequest request);
	
	public int deleteInsuranceInputItemDataInfoType(HttpServletRequest request);
	
	public int deleteInsuranceInputItemDataBatchInfo(HttpServletRequest request);
	
	public int deleteInsuranceInputItemDataBatchInfoType(HttpServletRequest request);
	
	public int createInsuranceInputItemInfo(HttpServletRequest request) ;
	
	public int createAddInsuranceInputItemDataInfo(HttpServletRequest request) ;
	
	public int updateInsuranceInputItemDataInfo(HttpServletRequest request);
	
	public int deleteAllInsuranceInputItemDataInfo(HttpServletRequest request);
	
	
	public List getAddInsuranceInputItemDataList(HttpServletRequest request) ;
	
	public int addInsuranceInputItemDataInfo(HttpServletRequest request);
	
	public int addInsuranceInputItemOtherDataInfo(HttpServletRequest request);
	
	public List getInsuranceInputItemDataListByParamNo(HttpServletRequest request);
	
	public int getInsuranceInputItemDataListByParamNoCnt(HttpServletRequest request);
	
	public Object getInsuranceInputItemDataInfo(HttpServletRequest request) ;
	
	public List getInsuranceInputItemDataPersonList(HttpServletRequest request);
	
	public List getInsuranceInputItemDataPersonListNoPage(HttpServletRequest request);
	
	public int getInsuranceInputItemDataPersonListCnt(HttpServletRequest request);
	
	public List getAddInsurancePersonalInputList(HttpServletRequest request);
	
	public int getAddInsurancePersonalInputListCnt(HttpServletRequest request);
	
	public int updateInsuranceInputItemDataPersonInfo(HttpServletRequest request) throws Exception;
	
	//2013-09-01 添加
	public List getInsuranceInputItemDataForApply(HttpServletRequest request) throws Exception ;
	
	public List getInsuranceInputApplyDataListByParamNo(HttpServletRequest request,int flag,int flagtow) throws Exception;
	
	public int getInsuranceInputApplyDataListByParamNoCnt(HttpServletRequest request,int flag,int flagtow);
	
	public Object getInsuranceInputApplyDataInfo(HttpServletRequest request) ;
	
	public int addInsuranceInputItemDataApply(HttpServletRequest request);
	
	public int addInsuranceInputItemOtherDataApply(HttpServletRequest request);
	
	/**
	 * 通过/否决--保险输入项目申请数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveInsDataApply(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决--保险输入项目申请数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveInsDataApplyInBatch(HttpServletRequest request)
			throws Exception;
	
	public int updateInsDataApply(HttpServletRequest request);
	
	/**
	 * 删除--保险输入项目申请数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteInsDataApply(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 删除--保险输入项目申请数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteInsDataApplyInBatch(HttpServletRequest request)
	throws Exception;
	/**
	 * 批量提交保险申请申请
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-5 上午11:45:54 
	* @version V1.0
	 */
	public int updateInsuranceApply(HttpServletRequest request);
	
	/**
	 * 查询全部申请项目 是Y的字段
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-5 下午8:41:40 
	* @version V1.0
	 */
	public List getInsuranceInputItemDataForApplyName(HttpServletRequest request);

	/**
	 * 根据申请项目查询出相同所在地不同法人的相同的需要申请的项目
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-9 下午11:23:30 
	* @version V1.0
	 */
	public List getUnifySuitCompanyList(HttpServletRequest request);

	/**
	 * 提前获取到申请信息的编号 用关联到附件字段
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-11 下午1:02:12 
	* @version V1.0
	 */
	public String getNextparamDataNo();

	/**
	 * 保存社保申请的附件信息
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-11 下午2:25:15 
	* @version V1.0
	 */
	public int insertAccessory(LinkedHashMap map);
	
	/**
	 * 验证登陆用户的
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-16 下午2:58:40 
	* @version V1.0
	 */
	public int getUserRolesGroupCnt(HttpServletRequest request);

	/**
	 * 查找输入项目的NO和名称
	 * 只查询DISTINCT_FIELD='PERSON_ID' 并且  apply_flag 是不等于Y的数据
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-20 下午6:36:23 
	* @version V1.0
	 */
	public List getItemNameList(HttpServletRequest request);

	/**
	 * 查找输入项目 名称符合的数据 加字段 这里只显示 导入的数据
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-20 下午7:28:32 
	* @version V1.0
	 */
	public List getItemBatchImportList(HttpServletRequest request);
	
	/**
	 * 查找输入项目 名称符合的数据 加字段 这里只显示 导入的数据 数量
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-20 下午8:05:35 
	* @version V1.0
	 */
	public int getItemBatchImportListoCnt(HttpServletRequest request);


	/**
	 * PA 工资输入项目
	 * 查找输入项目的NO和名称
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-22 下午2:57:24 
	* @version V1.0
	 */
	public List getItemNameListPa(HttpServletRequest request);

	/**
	 * 查找输入项目 名称符合的数据 加字段 这里只显示 导入的数据 pa 工资用
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-22 下午3:22:40 
	* @version V1.0
	 */
	public List getItemBatchImportListPa(HttpServletRequest request);

	/**
	 * 查找输入项目 名称符合的数据 加字段 这里只显示 导入的数据 数量  pa 工资用
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-22 下午3:44:57 
	* @version V1.0
	 */
	public int getItemBatchImportListoCntPa(HttpServletRequest request);

	
	public List getAddInsurancePersonalInputItemList(HttpServletRequest request)throws Exception;

	public List getInsuranceInputItemPersonTempAllList(
			HttpServletRequest request)throws Exception;

	public int insertOrUpdateInsuranceInputItemData(HttpServletRequest request)throws Exception;

	/**
	 * 保险申请项目通知
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-25 下午5:45:34 
	* @version V1.0
	 */
	public int addApplyInform(HttpServletRequest request);

	public int getViewInsureSelfCnt(HttpServletRequest request);
	/**
	 * 保险查看（个人别）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-30 下午04:00:04 
	* @version V1.0
	 */
	public List getViewInsureSelfList(HttpServletRequest request);

	public int getViewInsureDeptCnt(HttpServletRequest request);
	/**
	 * 保险查看（公司别）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-30 下午04:00:25 
	* @version V1.0
	 */
	public List getViewInsureDeptList(HttpServletRequest request);

	public List getItemNameListPa2(HttpServletRequest request);

	public List getWelfareArea(HttpServletRequest request);

	public List getImportExcelTempISParamDataList(HttpServletRequest request);

	public int getImportExcelTempISParamDataListCnt(HttpServletRequest request);

	public int getImportExcelTempISParamDataListErrCnt(
			HttpServletRequest request);

	public String importISParamDataExcelExcel(HttpServletRequest request);



	public List getItemBatchImportDataTemp(HttpServletRequest request);

	public int getItemBatchImportDataTempCnt(HttpServletRequest request);

	public String submitItemBatchData(HttpServletRequest request);

	public int getItemBatchImportDataTempErrorCnt(HttpServletRequest request);

	public boolean delExceplImportLine(HttpServletRequest request);

	public String updateItemBatchData(HttpServletRequest request);



	void deleteErrorOldItemBatchData(HttpServletRequest request,
			String ITEM_DISTINGUISH);

}
