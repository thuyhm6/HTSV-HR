package com.ait.hrm.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface EssApplyInfoSer {

	// 变更明细查询
	public List getEssApplyList(HttpServletRequest request);

	public int getEssApplyListCnt(HttpServletRequest request);

	public List applyList(HttpServletRequest request);

	// ////////////////////////////////////////////////////////////////////////////
	public List getEssAddressApplyList(HttpServletRequest request);

	public List getEssEmergencyList(HttpServletRequest request);

	public List getEssHomeRelationApplyList(HttpServletRequest request);

	public List getEssWorkApplyList(HttpServletRequest request);

	public List getEssProductApplyList(HttpServletRequest request);

	public List getEssEducationApplyList(HttpServletRequest request);

	public List getEssQualificationApplyList(HttpServletRequest request);

	public List getEssRewardList(HttpServletRequest request);

	public List getPersonalApplyList(HttpServletRequest request);

	// ///////////////objiect
	public Object getEssAddressApplyObject(HttpServletRequest request);

	public Object getEssEmergencyObject(HttpServletRequest request);

	public Object getEssHomeRelationApplyObject(HttpServletRequest request);

	public Object getEssWorkApplyObject(HttpServletRequest request);

	public Object getEssProductApplyObject(HttpServletRequest request);

	public Object getEssEducationApplyObject(HttpServletRequest request);

	public Object getEssQualificationApplyObject(HttpServletRequest request);

	public Object getEssRewardObject(HttpServletRequest request);

	public Object getPersonalApplyObject(HttpServletRequest request);

	// //

	// ///////////////objiect 申请之前的信息
	public Object getEssAddressApplyObject2(HttpServletRequest request);

	public Object getEssEmergencyObject2(HttpServletRequest request);

	public Object getEssHomeRelationApplyObject2(HttpServletRequest request);

	public Object getEssWorkApplyObject2(HttpServletRequest request);

	public Object getEssProductApplyObject2(HttpServletRequest request);

	public Object getEssEducationApplyObject2(HttpServletRequest request);

	public Object getEssQualificationApplyObject2(HttpServletRequest request);

	public Object getEssRewardObject2(HttpServletRequest request);

	public Object getPersonalApplyObject2(HttpServletRequest request);

	// //
	//
	public Object getPersonalInfoByPid(HttpServletRequest request);

	// 通用的方法
	public Map getRequestMap(HttpServletRequest request);

	/**
	 * 审批十个 增删改统一为 update APPLY_TYPE 1添加 2修改 3删除
	 */
	public int updateEducationInfo(HttpServletRequest request);

	public int updateAddressInfo(HttpServletRequest request);

	public int updatePersonalInfo(HttpServletRequest request);

	public int updateHomeRelationInfo(HttpServletRequest request);

	public int updateEmergencyInfo(HttpServletRequest request);

	public int updateWorkInfo(HttpServletRequest request);

	public int updateProductInfo(HttpServletRequest request);

	public int updateQualificationInfo(HttpServletRequest request);

	public int updateRewardInfo(HttpServletRequest request);

	// 批量审批
	public int essApplyBatchApproval(HttpServletRequest request);

	/**
	 * 
	 */

	/**
	 * 修改申请表状态 activity 1提交 2审批 3退回 4取消
	 */

	/**
	 * 
	 */

	/**
	 * manage统计
	 * 
	 * @param request
	 * @return
	 */
	public List manageAgeCountList(HttpServletRequest request) throws Exception;

	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeAgeList(HttpServletRequest request);

	//
	public List manageGradeCountList(HttpServletRequest request)
			throws Exception;

	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeGradeList(HttpServletRequest request);

	//
	public List managePositionCountList(HttpServletRequest request)
			throws Exception;

	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodePositionList(HttpServletRequest request);

	//
	public List manageEduCountList(HttpServletRequest request) throws Exception;

	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeEduList(HttpServletRequest request);

	//
	public List manageEmpTypeCountList(HttpServletRequest request)
			throws Exception;

	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeEmpTypeList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeWorkAgeList(HttpServletRequest request);

	/**
	 * 统计 入职人数统计
	 * 
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeEmpTypeList1(HttpServletRequest request);

	//
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeGradeList1(HttpServletRequest request);

	//

	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodePositionList1(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeGradeList2(HttpServletRequest request);

	//

	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodePositionList2(HttpServletRequest request);

	//
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeEmpTypeList2(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public int callPD(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public LinkedHashMap getResignResonList(HttpServletRequest request);

	public Object getApplyNumber(HttpServletRequest request);

	public Object getPersonalPhotoNullNumber(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getAllDeptList(HttpServletRequest request);
	
	
	public int saveOrzTemp(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List monthPersonCountInfoSonList(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List monthPersonCountInfoSonList1(HttpServletRequest request);
}
