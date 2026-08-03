package com.ait.ess.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface AffirmApplyDao {
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmList(Object obj) throws Exception;
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmListTwo(Object obj) throws Exception;
	
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorByApplyNoList(Object object);
	
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmListFinal(Object obj) throws Exception;
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmList(Object obj, int currentPage, int pageSize)
			throws Exception;
	
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmListTwo(Object obj, int currentPage, int pageSize)
			throws Exception;
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getOtAffirmListFinal(Object obj, int currentPage, int pageSize)
			throws Exception;
	/**
	 * 加班决裁列表总数(get overtime apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtAffirmListCnt(Object obj) throws Exception;
	/**
	 * 加班决裁列表总数(get overtime apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtAffirmListTwoCnt(Object obj) throws Exception;
	/**
	 * 加班决裁列表总数(get overtime apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtAffirmListFinalCnt(Object obj) throws Exception;
	/**
	 * 批量通过/否决加班申请(batch pass and reject overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveOvertimeApplyAffirmInBatch(List list) throws Exception;
	
	/**
	 * 批量通过/否决年假调整申请(batch pass and reject overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveAnnualadjustmentAffirmInBatch(List list) throws Exception;
	
	/**
	 * 通过/否决加班申请(pass and reject overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveOvertimeApplyAffirm(LinkedHashMap object) throws Exception;
	
	/**
	 * 通过/否决年假调整申请(pass and reject overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveAnnualadjustmentAffirm(LinkedHashMap object) throws Exception;
	
	/**
	 * 根据apply_no,person_id查询此人是否是此次申请的决裁者
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getAffirmorCntByApplyNo(LinkedHashMap obj) throws Exception;
	
	/**
	 * 添加决裁者信息(add apply affirmor)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addNewApplyAffirmor(LinkedHashMap map) throws Exception;
	
	/**
	 * 加班Check列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOtCheckList(Object obj) throws Exception;
	
	/**
	 * 加班Check列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getOtCheckList(Object obj, int currentPage, int pageSize)
			throws Exception;
	/**
	 * 加班Check列表总数(get overtime apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOtCheckListCnt(Object obj) throws Exception;
	
	/**
	 * check加班申请(check overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkApplyInfo(LinkedHashMap object) throws Exception;
	
	/**
	 * check加班申请，最后一步check之后将ess_affirm中的current_check_id置空(check pa for left apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkPaForLeftApplyInfo(LinkedHashMap object) throws Exception;
	
	/**
	 * 添加checkor(add checkor)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addApplyCheckList(LinkedHashMap object) throws Exception;
	
	/**
	 * 添加checkor(add checkor)--checke_level自增
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addApplyCheckListNew(LinkedHashMap object) throws Exception;
	
	/**
	 * 
	 * 查询checkno
	 * @param object
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public int selectCheckNo() throws Exception;
	
	/**
	 * update current_checkor_id(update current_checkor_id of ess_affirm)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateCurrentCheckor(LinkedHashMap object) throws Exception;
	
	/**
	 * 获得决裁信息(get ess_affirm information by affirmNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getEssAffirmInfoByAffirmNo(LinkedHashMap obj)
			throws Exception;
	@SuppressWarnings("unchecked")
	public Object getEssAffirmInfoForApplyNo(LinkedHashMap obj)
	throws Exception;
	@SuppressWarnings("unchecked")
	public Object getEssAffirmInfoForEssAffrimNO(LinkedHashMap obj)
	throws Exception;
	
	/**
	 * 获得Check信息(get ess_Check information by checkNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getEssCheckInfoByCheckNo(LinkedHashMap obj)
			throws Exception;	
	
	/**
	 * 获得Check信息(get ess_Check information by CheckorId)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getEssCheckInfoByCheckorId(LinkedHashMap obj)
			throws Exception;	
	
	/**
	 * 获得Check信息Cnt(get ess_Check information by CheckorId)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEssCheckCntByCheckorId(LinkedHashMap obj)
			throws Exception;	
	
	/**
	 * 获得当前信息决裁流程中最大的决裁级别(get Max Affirm level By ApplyNo )
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getMaxAffirmLevelByApplyNo(LinkedHashMap obj) throws Exception;
	
	/**
	 * 获得当前信息Check流程中最大的Check级别(get Max Affirm level By ApplyNo )
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getMaxCheckLevelByApplyNo(LinkedHashMap obj) throws Exception;
	
	/**
	 * 获得决裁信息(get ess_affirm information by applyNo and level)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getEssAffirmInfoByApplyNoAndLevel(LinkedHashMap obj)
			throws Exception;
	
	/**
	 * 获得下一级Check信息(get ess_check information by applyNo and level)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getEssCheckInfoByApplyNoAndLevel(LinkedHashMap obj)
			throws Exception;
	
	/**
	 * 加班申请--编辑列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditOtApplyList(Object obj) throws Exception;
	
	/**
	 * 加班申请--编辑列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditOtApplyBatchList(Object obj) throws Exception;
	
	
	/**
	 * 加班申请--编辑列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEditOtApplyList(Object obj, int currentPage, int pageSize)
			throws Exception;
	
	/**
	 * 加班申请--批量修改编辑列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEditOtApplyBatchList(Object obj, int currentPage, int pageSize)
			throws Exception;
	/**
	 * 加班申请--编辑列表总数(overtime apply affirm list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getEditOtApplyListCnt(Object obj) throws Exception;
	
	/**
	 * 加班申请--编辑列表总数(overtime apply affirm list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getEditOtApplyListBatchCnt(Object obj) throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getOvertimeAffirmList(Object obj) throws Exception;

	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List getOvertimeAffirmList(Object obj, int currentPage, int pageSize)
			throws Exception;

	/**
	 * 加班决裁列表总数(get overtime apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getOvertimeAffirmListCnt(Object obj) throws Exception;

	/**
	 * 休假/出差/外出申请决裁列表(leave/evection/egression apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getLeaveApplyAffirmList(Object obj) throws Exception;

	/**
	 * 休假/出差/外出申请决裁列表(leave/evection/egression apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List getLeaveApplyAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception;

	/**
	 * 休假/出差/外出申请决裁列表总数(leave/evection/egression apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getLeaveApplyAffirmListCnt(Object obj) throws Exception;
	
	/**
	 * 批量通过/否决休假/出差/外出申请(batch pass and reject leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveLeaveApplyAffirmInBatch(List list) throws Exception ;
	
	/**
	 * 通过/否决休假/出差/外出申请(pass and reject leave apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */ 
	public int saveLeaveApplyAffirm(LinkedHashMap object) throws Exception ;

	/**
	 * 根据法人和参数号查找对应的值(get parameter Value By CpnyId And ParamNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getParamValueByCpnyIdAndParamNo(LinkedHashMap obj)
			throws Exception;

	/**
	 * 通过信息申请NO获得该信息决裁流程的决裁者(get affirmor list by applyNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getEssAffirmInfoByApplyNo(LinkedHashMap obj) throws Exception;

	@SuppressWarnings("rawtypes")
	public List getHireAffirmList(Object obj) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getHireAffirmList(Object obj, int currentPage, int pageSize) throws Exception;
	public int getHireAffirmListCnt(Object obj) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getHireAffirmByReqID(Object object);
	@SuppressWarnings("rawtypes")
	public List getCheckorByByReqID(Object object);
	@SuppressWarnings("rawtypes")
	public List getHireAffirmListByReqID(Object obj) throws Exception;
	public List getHireAffirmListByReqID(Object obj, int currentPage, int pageSize) throws Exception;
	public int getHireAffirmListByReqIDCnt(Object obj) throws Exception;
	@SuppressWarnings("rawtypes")
	public int saveApplyHireAffirm(LinkedHashMap object) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List getResignAffirmList(Object obj) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getResignAffirmList(Object obj, int currentPage, int pageSize) throws Exception;
	@SuppressWarnings("rawtypes")
	public int getResignAffirmListCnt(Object obj) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getResignAffirmByReqID(Object object);
	@SuppressWarnings("rawtypes")
	public List getResignCheckorByByReqID(Object object);
	@SuppressWarnings("rawtypes")
	public List getResignAffirmListByReqID(Object obj) throws Exception;
	@SuppressWarnings("rawtypes")
	public int getResignAffirmListByReqIDCnt(Object obj) throws Exception;
	@SuppressWarnings("rawtypes")
	public int saveApplyResignAffirm(LinkedHashMap object) throws Exception;
	@SuppressWarnings("rawtypes")
	public Map callRevokeResignation(Map paramMap) throws Exception;
	@SuppressWarnings("rawtypes")
	public void rollbackApplyResignAffirm(LinkedHashMap object) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getSellOutAffirmList(Object obj) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getSellOutAffirmList(Object obj, int currentPage, int pageSize) throws Exception;
	public int getSellOutAffirmListCnt(Object obj) throws Exception;
}
