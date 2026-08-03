package com.ait.hrm.dao;

import java.sql.SQLException;
import java.util.List;

public interface EssApplyInfoDao {

	@SuppressWarnings("unchecked")
	public List getEssApplyList(Object object, int pageNum, int numPerPage);

	@SuppressWarnings("unchecked")
	public List getEssApplyList(Object object);

	public int getEssApplyListCnt(Object object);

	public List applyList(Object object);

	public List getEssAddressApplyList(Object object);

	public List getEssEmergencyList(Object object);

	public List getEssHomeRelationApplyList(Object object);

	public List getEssWorkApplyList(Object object);

	public List getEssProductApplyList(Object object);

	public List getEssEducationApplyList(Object object);

	public List getEssQualificationApplyList(Object object);

	public List getEssRewardList(Object object);

	public List getPersonalApplyList(Object object);

	// ////////////Object
	public Object getEssAddressApplyObject(Object object);

	public Object getEssEmergencyObject(Object object);

	public Object getEssHomeRelationApplyObject(Object object);

	public Object getEssWorkApplyObject(Object object);

	public Object getEssProductApplyObject(Object object);

	public Object getEssEducationApplyObject(Object object);

	public Object getEssQualificationApplyObject(Object object);

	public Object getEssRewardObject(Object object);

	public Object getPersonalApplyObject(Object object);

	// ////////////////

	// ////////////Object申请前的信息查看
	public Object getEssAddressApplyObject2(Object object);

	public Object getEssEmergencyObject2(Object object);

	public Object getEssHomeRelationApplyObject2(Object object);

	public Object getEssWorkApplyObject2(Object object);

	public Object getEssProductApplyObject2(Object object);

	public Object getEssEducationApplyObject2(Object object);

	public Object getEssQualificationApplyObject2(Object object);

	public Object getEssRewardObject2(Object object);

	public Object getPersonalApplyObject2(Object object);

	// ////////////////

	public Object getPersonalInfoByPid(Object object);

	/**
	 * 审批十个 增删改统一为 update APPLY_TYPE 1添加 2修改 3删除
	 */
	public void updateEducationInfo(Object object, String status)
			throws Exception;
	
	public void callbackEducationInfo(Object object)
			throws Exception;
	
	public void callbackAddressInfo(Object object)
	throws Exception;
	
	public void insertEducationInfo(Object object)
			throws Exception;

	public void updateAddressInfo(Object object, String status)
			throws Exception;

	public void updatePersonalInfo(Object object, String status)
			throws Exception;
	
	//回复信息和错误内容
	public void callbackPersonalInfo(Object object)
			throws Exception;
	
	public void insertPersonalInfo(Object object)
			throws Exception;

	public void updatePersonalInfoForPic(Object object, String status)
			throws Exception;

	public void updateHomeRelationInfo(Object object, String status)
			throws Exception;
	
	public void callbackHomeRelationInfo(Object object)
			throws Exception;
	
	public void insertHomeRelationInfo(Object object)
			throws Exception;

	public void updateEmergencyInfo(Object object, String status)
			throws Exception;
	public void callbackEmergencyInfo(Object object)
			throws Exception;
	
	public void insertEmergencyInfo(Object object)
			throws Exception;

	public void updateWorkInfo(Object object, String status) throws Exception;
	
	public void callbackWorkInfo(Object object) throws Exception;
	
	public void insertWorkInfo(Object object) throws Exception;

	public void updateProductInfo(Object object, String status)
			throws Exception;

	public void updateQualificationInfo(Object object, String status)
			throws Exception;
	public void callbackQualificationInfo(Object object)
			throws Exception;
	
	public void insertQualificationInfo(Object object)
			throws Exception;

	public void updateRewardInfo(Object object, String status) throws Exception;

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
	 * 
	 */
	public List manageAgeCountList(Object object);

	/**
	 * 生成部门树
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParentCodeList(Object object);

	/**
	 * 生成部门区分列表
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParentCodeDifList(Object object);

	/**
	 * 得到所有部门
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAllDept(Object object);

	// 部门区分
	@SuppressWarnings("unchecked")
	public List getAllDeptDif(Object object);

	/**
	 * 根据部门
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAgeListByDeptNo(Object object);

	//
	@SuppressWarnings("unchecked")
	public List getGradeBGZListByDeptNo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getGradeSCZListByDeptNo(Object object);

	//
	@SuppressWarnings("unchecked")
	public List getPositionListByDeptNo(Object object);

	//
	@SuppressWarnings("unchecked")
	public List getEduListByDeptNo(Object object);

	//
	@SuppressWarnings("unchecked")
	public List getSexListByDeptNo(Object object);

	//
	@SuppressWarnings("unchecked")
	public List getEmpTypeListByDeptNo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getWorkAgeListByDeptNo(Object object);

	/**
	 * 入职人数统计
	 */
	@SuppressWarnings("unchecked")
	public List getEmpTypeListByDeptNo1(Object object);

	@SuppressWarnings("unchecked")
	public List getGradeBGZListByDeptNo1(Object object);
	
	@SuppressWarnings("unchecked")
	public List getGradeSCZListByDeptNo1(Object object);

	//
	@SuppressWarnings("unchecked")
	public List getPositionListByDeptNo1(Object object);

	//

	// /star退职

	//
	@SuppressWarnings("unchecked")
	public List getGradeBGZListByDeptNo2(Object object);
	
	@SuppressWarnings("unchecked")
	public List getGradeSCZListByDeptNo2(Object object);

	//
	@SuppressWarnings("unchecked")
	public List getPositionListByDeptNo2(Object object);

	//
	@SuppressWarnings("unchecked")
	public List getEmpTypeListByDeptNo2(Object object);

	//
	@SuppressWarnings("unchecked")
	public List getResignResonList(Object object);

	@SuppressWarnings("unchecked")
	public void collPD(Object object) throws SQLException;

	public Object getApplyNumber(Object object);

	public Object getPersonalPhotoNullNumber(Object object);

	/**
	 * 生成部门树
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAllDeptList(Object object);

	public void saveOrzTemp(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public List monthPersonCountMonthInfoSonList(Object object);
	@SuppressWarnings("unchecked")
	public List monthPersonCountMonthInLIZHIfoSonList(Object object);
	@SuppressWarnings("unchecked")
	public List monthPersonCountAgeInfoSonList(Object object);
	@SuppressWarnings("unchecked")
	public List monthPersonCountWorkAgeInfoSonList(Object object);
	@SuppressWarnings("unchecked")
	public List monthPersonCountEduInfoSonList(Object object);
	@SuppressWarnings("unchecked")
	public List monthPersonCountSexInfoSonList(Object object);
	@SuppressWarnings("unchecked")
	public List monthPersonCountGradeInfoSonList(Object object);
	@SuppressWarnings("unchecked")
	public List monthPersonCountGradeForLiftInfoSonList(Object object);
	@SuppressWarnings("unchecked")
	public List monthPersonCountEmpTypeSonList(Object object);
	@SuppressWarnings("unchecked")
	public List monthPersonCountRESIGNRESONInfoSonList(Object object);
	@SuppressWarnings("unchecked")
	public List monthPersonCountEmpTypeInfoSonList1(Object object);
	@SuppressWarnings("unchecked")
	public List monthPersonCountGradeInfoSonList1(Object object);
	@SuppressWarnings("unchecked")
	public List monthPersonCountSocialInfoSonList1(Object object);
}
