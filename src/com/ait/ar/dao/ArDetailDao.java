package com.ait.ar.dao;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArDetailDao.java
 * @Description: implement Class ArDetailDaoImpl.java
 * @Create date: 2012-2-7 下午04:32:10
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArDetailDao {
	@SuppressWarnings("unchecked")
	public List getArDetailList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAttendanceExceptionList(Object object);
	public List getAttendanceExceptionList(Object object,int currentPage, int pageSize);
	/**
	 * 考勤异常查看页面导出方法用
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArDetailListExcel(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArDetailList(Object object,int currentPage, int pageSize);
	public int getArDetailListCnt(Object object);
	@SuppressWarnings("unchecked")
	public List getItemList(Object object);
	public int getStartDateStr(Object object);
	@SuppressWarnings("unchecked")
	public void updateArDetailInfo(List list) throws Exception;
	public Object validateDailyLock(Object object) ;
	public Object validateDetailItemType(Object object) ;
	@SuppressWarnings("unchecked")
	public void deleteArDetailInfo(List list) throws Exception;
	@SuppressWarnings("unchecked")
	public void addArDetailInfo(List list) throws Exception;
	public Object getArDetailInfo(Object object);
	@SuppressWarnings("unchecked")
	public void addArDetailInfo1(List list,List list1) throws Exception;
	@SuppressWarnings("unchecked")
    public List SearchArDetailExceptionInfo(Object object);
	@SuppressWarnings("unchecked")
    public List SearchArDetailDeptExToMa(Object object);
	@SuppressWarnings("unchecked")
    public List SearchManagerInfo(Object object);
	/**
	 * 明细维护  进行操作的时候保存操作记录  该条记录重点信息和操作类型（增加  修改  删除）和操作人
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addArDetailHistoryInfo(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public String getModifyYnBySupervisorId(String object);
	/********************20150206 zyh 新增 start****************************/
	/**
	 *取得个人日考勤查看(get ArDetail List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPersonArDetailList(Object object) ;
	/**
	 * 取得个人日考勤查看(get ArDetail List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPersonArDetailList(Object obj, int currentPage, int pageSize) ;
	/********************20150206 zyh 新增 end****************************/
	
	/**
	 * 获取明细导入信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArDetailTempList(Object object);
	
	/**
	 * 获取明细导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getArDetailTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取明细导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getArDetailTempCnt(Object object);
	
	/**
	 * 获取出错的明细导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getArDetailTempErrorCnt(Object object);
	
	public String countArDetailLength(Object object);
	
	public int   getEssApplySeq();
	
	public int insertOverTimeApplyInfo(Object object) throws Exception;
	
	public int insertAffirmListInfo(Object object) throws Exception;
	
	public void updateArDetailInfoByNo(Object object) throws Exception;
	public void updateArDetailApplyInfoByNo(Object object) throws Exception;
	public void updateArDetailAffirmInfoByNo(Object object) throws Exception;
	public List getOtAffirmInfoListBatch(Object object);
	public List viewArAdjustHolidayManagent(Object object);
	public List viewArAdjustHolidayManagentNull(Object object);
	public List getOtAffirmInfoListBatchSST(Object object);
	

	/**
	 * 批量添加加班申请(batch add leave apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addOtApplyInArDetailBatch(LinkedHashMap paramMap) throws Exception;

	/**
	 * 修改detail表中的数据。
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updatSubmitArDetailBatchApply(LinkedHashMap paramMap) throws Exception;
	@SuppressWarnings("unchecked")
	public void updatSubmitArDetailBatchApplyForFirstAdd(LinkedHashMap paramMap) throws Exception;
	@SuppressWarnings("unchecked")
	public void updateOtApplyInArDetailBatchApply(LinkedHashMap paramMap)throws Exception;
	@SuppressWarnings("unchecked")
	public List getSearchApplyOtInfoList(Object object);
	@SuppressWarnings("unchecked")
	public List getMyhomeCarInfoList(Object object);
	@SuppressWarnings("unchecked")
	public List getSearchApplyAdjustInfoList(Object object);
	@SuppressWarnings("unchecked")
	public List getApplyAttenanceManagentInfoList(Object object);
	public List getBatchLeaveAffirmMoreDayInfoList(Object object);
	@SuppressWarnings("unchecked")
	public void addLeaveApplyInArDetailBatch(List list) throws Exception;
	@SuppressWarnings("unchecked")
	public void updatSubmitLeaveApplyInBatchForDeletePrepare(List list) throws Exception;
	@SuppressWarnings("unchecked")
	public void insertSubmitLeaveApplyInBatch(List list) throws Exception;
	@SuppressWarnings("unchecked")
	public void updatSubmitArDetailBatchForMangemant(List list) throws Exception;
	@SuppressWarnings("unchecked")
	public void updateLeaveApplyInArDetailBatchForManagent(List list) throws Exception;
	
	/**
	 * 加班管理删除(batch delete overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delLeaveApplyInBatch(List list, LinkedHashMap personMap) throws Exception;
	@SuppressWarnings("unchecked")
	public List getLeaveManagentForSearchInfoList(Object object);
	/**
	 * 加班管理里添加数据
	 * 
	 * @param obj
	 * @return
	 */
	public int addtLeaveApplyInBatch(LinkedHashMap map) throws Exception ;

	
	/**
	 * 综合工时加班查询
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewSearchOtInfo(Object obj) ;
	@SuppressWarnings("unchecked")
	public String getItemNoOnApplyCode(LinkedHashMap map);
	public void deleteOtAdjustForOnlyOne(LinkedHashMap deleteMap);
	public List viewAdjustRecords(LinkedHashMap paramMap);
	
	@SuppressWarnings("unchecked")
	public List viewAbnormalDetailInfo(Object object);
}
