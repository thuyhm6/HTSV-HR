package com.ait.ess.dao;

import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public interface AffirmLeaveApplyDao {
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getOtAffirmList(Object obj) throws Exception;

	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List getOtAffirmList(Object obj, int currentPage, int pageSize)
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
	 * 批量通过/否决加班申请(batch pass and reject overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int saveOvertimeApplyAffirmInBatch(List list) throws Exception;

	/**
	 * 批量通过/否决年假调整申请(batch pass and reject overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int saveAnnualadjustmentAffirmInBatch(List list) throws Exception;

	/**
	 * 通过/否决加班申请(pass and reject overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int saveOvertimeApplyAffirm(LinkedHashMap object) throws Exception;

	/**
	 * 通过/否决年假调整申请(pass and reject overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int saveAnnualadjustmentAffirm(LinkedHashMap object)
			throws Exception;

	/**
	 * 根据apply_no,person_id查询此人是否是此次申请的决裁者
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getAffirmorCntByApplyNo(LinkedHashMap obj) throws Exception;

	/**
	 * 添加决裁者信息(add apply affirmor)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addNewApplyAffirmor(LinkedHashMap map) throws Exception;

	/**
	 * 加班Check列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getOtCheckList(Object obj) throws Exception;

	/**
	 * 加班Check列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
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
	public int checkApplyInfo(LinkedHashMap object) throws Exception;

	/**
	 * 添加checkor(add checkor)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int addApplyCheckList(LinkedHashMap object) throws Exception;

	/**
	 * update current_checkor_id(update current_checkor_id of ess_affirm)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int updateCurrentCheckor(LinkedHashMap object) throws Exception;

	/**
	 * 获得决裁信息(get ess_affirm information by affirmNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getEssAffirmInfoByAffirmNo(LinkedHashMap obj)
			throws Exception;

	/**
	 * 获得Check信息(get ess_Check information by checkNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getEssCheckInfoByCheckNo(LinkedHashMap obj) throws Exception;

	/**
	 * 获得Check信息(get ess_Check information by CheckorId)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getEssCheckInfoByCheckorId(LinkedHashMap obj)
			throws Exception;

	/**
	 * 获得Check信息Cnt(get ess_Check information by CheckorId)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getEssCheckCntByCheckorId(LinkedHashMap obj) throws Exception;

	/**
	 * 获得当前信息决裁流程中最大的决裁级别(get Max Affirm level By ApplyNo )
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getMaxAffirmLevelByApplyNo(LinkedHashMap obj) throws Exception;

	/**
	 * 获得当前信息Check流程中最大的Check级别(get Max Affirm level By ApplyNo )
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getMaxCheckLevelByApplyNo(LinkedHashMap obj) throws Exception;

	/**
	 * 获得决裁信息(get ess_affirm information by applyNo and level)
	 * 
	 * @param object
	 * @return
	 */
	public Object getEssAffirmInfoByApplyNoAndLevel(LinkedHashMap obj)
			throws Exception;

	/**
	 * 获得下一级Check信息(get ess_check information by applyNo and level)
	 * 
	 * @param object
	 * @return
	 */
	public Object getEssCheckInfoByApplyNoAndLevel(LinkedHashMap obj)
			throws Exception;

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
	 * @return
	 * @throws Exception
	 */
	public List getAttendanceAffirmList(Object obj) throws Exception;

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
	public int saveLeaveApplyAffirmInBatch(List list) throws Exception;
	/**
	 * TSTO审批页面(batch pass and reject leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveLeaveApplyAffirmInBatchForMangement(List list) throws Exception;

	/**
	 * 通过/否决休假/出差/外出申请(pass and reject leave apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int saveLeaveApplyAffirm(LinkedHashMap object) throws Exception;

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

	/**
	 * 通过/否决加班申请(pass and reject overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int saveApplyLeaveAffirm(LinkedHashMap object) throws Exception;

	public int saveApplyCwaAffirm(LinkedHashMap object) throws Exception;

	/**
	 * 休假Check列表(leave apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List getLeaveCheckList(Object obj, int currentPage, int pageSize)
			throws Exception;

	/**
	 * 休假Check列表(leave apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getLeaveCheckList(Object obj) throws Exception;

	/**
	 * 休假Check列表总数(get leave apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getLeaveCheckListCnt(Object obj) throws Exception;

	public int approveApplication(LinkedHashMap paramMap);

	public void addPaParamApplication(LinkedHashMap paramMap);

	public void updateApplicatCheck(LinkedHashMap paramMap);

	/**
	 * 查找下一级决裁者
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author yuanxq@ait.net.cn
	 * @date 2014-8-15 下午02:51:52
	 * @version V1.0
	 */
	public Object getNextAffirmProver(LinkedHashMap paramMap);
	/**
	 * 判断是否为追溯休假 overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	public boolean isZhuisuLeave(LinkedHashMap paramMap) throws Exception ;
	
	/**
	 * 根据perosnid获取法人代码
	 * @param leaveApply
	 * @return
	 */
	public String getCpnyIdByPersonId(LinkedHashMap leaveApply) ;
	

	public int saveApplyCwaAffirmBatch(List list) throws Exception ;
}
