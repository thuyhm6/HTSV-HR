package com.ait.hrm.dao.impl;

import java.sql.SQLException;

import com.ait.org.dao.OrgManageDao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.hrm.dao.EssApplyInfoDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;
@Repository
public class EssApplyInfoDaoImpl extends SqlMapClientSupport implements
		EssApplyInfoDao {
	
	@Autowired
	private OrgManageDao orgManageDao;

	/*
	 * 变更明细申请
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List applyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.approve.applyList", object);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("QUERY FAILURE");
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getPersonalApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.approve.getPersonalApplyList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("QUERY FAILURE");
		}
		return returnList;
	}

	@Override
	public int getEssApplyListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.approve.getEssApplyListCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssApplyList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("hrm.approve.getEssApplyList",
						object, currentPage, pageSize);
			} else {
				returnList = this.queryForList("hrm.approve.getEssApplyList",
						object);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssApplyList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getEssApplyList(object, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssAddressApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.approve.getEssAddressApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("QUERY FAILURE");
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssEmergencyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.approve.getEssEmergencyList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssHomeRelationApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.approve.getEssHomeRelationApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssWorkApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.approve.getEssWorkApplyList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssProductApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.approve.getEssProductApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssEducationApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.approve.getEssEducationApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssQualificationApplyList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.approve.getEssQualificationApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssRewardList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.approve.getEssRewardList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	/*
	 * end list
	 */

	/*
	 * 变更明细申请单个Object start
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonalApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getPersonalApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("QUERY FAILURE");
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssAddressApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getEssAddressApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("QUERY FAILURE");
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssEmergencyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject("hrm.approve.getEssEmergencyList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssHomeRelationApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getEssHomeRelationApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssWorkApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject("hrm.approve.getEssWorkApplyList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssProductApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getEssProductApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssEducationApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getEssEducationApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssQualificationApplyObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getEssQualificationApplyList", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssRewardObject(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject("hrm.approve.getEssRewardList",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	/*
	 * end list
	 */

	/*
	 * 变更明细申请单个Object 根据ID申请查询之前的信息start
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonalApplyObject2(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getPersonalApplyObject2", object);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("QUERY FAILURE");
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssAddressApplyObject2(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getEssAddressApplyObject2", object);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("QUERY FAILURE");
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssEmergencyObject2(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getEssEmergencyObject2", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssHomeRelationApplyObject2(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getEssHomeRelationApplyObject2", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssWorkApplyObject2(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getEssWorkApplyObject2", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssProductApplyObject2(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getEssProductApplyObject2", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssEducationApplyObject2(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getEssEducationApplyObject2", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssQualificationApplyObject2(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getEssQualificationApplyObject2", object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssRewardObject2(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject("hrm.approve.getEssRewardObject2",
					object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	/*
	 * end list
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonalInfoByPid(Object object) {
		Object returnResult = null;
		List returnList = null;
		try {
			returnList = this.queryForList(
					"hrm.approve.getPersonalInfoByPid", object);
			if(returnList != null && returnList.size() > 0){
				returnResult = returnList.get(0);
			}
		} catch (Exception e) {
		}
		return returnResult;
	}

	/**
	 * 审批十个 增删改统一为 update APPLY_TYPE 1添加 2修改 3删除
	 */
	@Override
	public void updateAddressInfo(Object object, String status)
			throws Exception {
		// TODO Auto-generated method stub

		if (status.equals("1")) {
			this.insert("hrm.approve.updateAddressInfo1", object);
		} else if (status.equals("2")) {
			this.update("hrm.approve.updateAddressInfo2", object);
		} else if (status.equals("3")) {
			this.update("hrm.approve.updateAddressInfo3", object);
		}
		this.update("hrm.approve.updateAddressInfoApply", object);
	}

	@Override
	public void updateEducationInfo(Object object, String status)
			throws Exception {
		// TODO Auto-generated method stub
		if (status.equals("1")) {
			Map obj=(Map)object;
			String seq = StringUtil.checkNull(this.insert("hrm.approve.updateEducationInfo1", object));
			//附件上传
			if (obj.get("FILE_NAME") != null && !"".equals(StringUtil.checkNull(obj.get("FILE_NAME"))) && !"".equals(seq) && !"1".equals(seq)) {
				String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME")).split(";");
				String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(";");
				if (fileUrl != null && fileUrl.length > 0) {
					for (int j=0;j<fileUrl.length ;j++) {
						LinkedHashMap fileMap = new LinkedHashMap();
						fileMap.put("fileName", fileName[j]);
						fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
						fileMap.put("APPLY_NO", seq);
						fileMap.put("APPLY_TYPE", "hrEducation");
						fileMap.put("CREATED_BY", obj.get("adminID"));
						this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
					}
				}
			}
		} else if (status.equals("2")) {
			Map objs = (Map) object;
			//保存附件
			if (objs.get("FILE_NAME") != null && !"".equals(StringUtil.checkNull(objs.get("FILE_NAME")))) {
				String[] fileName = StringUtil.checkNull(objs.get("FILE_NAME")).split(";");
				String[] fileUrl = StringUtil.checkNull(objs.get("FILE_URL")).split(";");
				if (fileUrl != null && fileUrl.length > 0) {
					for (int j=0;j<fileUrl.length ;j++) {
						LinkedHashMap fileMap = new LinkedHashMap();
						fileMap.put("fileName", fileName[j]);
						fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + objs.get("adminID") + "/" + fileUrl[j]);
						fileMap.put("APPLY_NO", objs.get("UPDATE_EDUC_NO"));
						fileMap.put("APPLY_TYPE", "hrEducation");
						/*fileMap.put("CREATED_BY", objs.get("CREATE_BY"));*/
						fileMap.put("CREATED_BY", objs.get("adminID"));
						this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
		}
			}
		}
			//删除附件
			LinkedHashMap obj=(LinkedHashMap)object;
			String fileNos=(String)obj.get("FILENOSSTR");
			LinkedHashMap fileMap = new LinkedHashMap();
			fileMap.put("fileNos", fileNos);
			this.orgManageDao.deleteFile(fileMap);
			//修改成功
			this.update("hrm.approve.updateEducationInfo2", object);
		} else if (status.equals("3")) {
			this.update("hrm.approve.updateEducationInfo3", object);
		}
		this.update("hrm.approve.updateEducationInfoApply", object);
	}
	
	@Override
	public void callbackEducationInfo(Object object)
			throws Exception {
		this.update("hrm.approve.callbackEducationInfo", object);
	}
	
	@Override
	public void callbackAddressInfo(Object object)
			throws Exception {
		this.update("hrm.approve.callbackAddressInfo", object);
	}
	@Override
	public void insertEducationInfo(Object object)
			throws Exception {
		this.insert("hrm.approve.insertEducationInfo", object);
	}

	@Override
	public void updateEmergencyInfo(Object object, String status)
			throws Exception {
		// TODO Auto-generated method stub
		if (status.equals("1")) {
			this.insert("hrm.approve.updateEmergencyInfo1", object);
		} else if (status.equals("2")) {
			this.update("hrm.approve.updateEmergencyInfo2", object);
		} else if (status.equals("3")) {
			this.update("hrm.approve.updateEmergencyInfo3", object);
		}
		this.update("hrm.approve.updateEmergencyInfoApply", object);
	}
	@Override
	public void callbackEmergencyInfo(Object object)
			throws Exception {
		this.update("hrm.approve.callbackEmergencyInfo", object);
	}
	@Override
	public void insertEmergencyInfo(Object object)
			throws Exception {
		this.insert("hrm.approve.insertEmergencyInfo", object);
	}


	@Override
	public void updateHomeRelationInfo(Object object, String status)
			throws Exception {
		// TODO Auto-generated method stub
		if (status.equals("1")) {
			this.insert("hrm.approve.updateHomeRelationInfo1", object);
		} else if (status.equals("2")) {
			this.update("hrm.approve.updateHomeRelationInfo2", object);
		} else if (status.equals("3")) {
			this.update("hrm.approve.updateHomeRelationInfo3", object);
		}
		this.update("hrm.approve.updateHomeRelationInfoApply", object);
	}
	@Override
	public void callbackHomeRelationInfo(Object object)
			throws Exception {
		this.update("hrm.approve.callbackHomeRelationInfo", object);
	}
	@Override
	public void insertHomeRelationInfo(Object object)
			throws Exception {
		this.insert("hrm.approve.insertHomeRelationInfo", object);
	}

	@Override
	public void updatePersonalInfo(Object object, String status)
			throws Exception {
		// TODO Auto-generated method stub
		if (status.equals("1")) {
			this.insert("hrm.approve.updatePersonalInfo1", object);
		} else if (status.equals("2")) {
			this.update("hrm.approve.updatePersonalInfo2", object);
		} else if (status.equals("3")) {
			this.update("hrm.approve.updatePersonalInfo3", object);
		}
		this.update("hrm.approve.updatePersonalInfoApply", object);
	}
	
	/**
	 * 回复信息和错误内容
	 */
	@Override
	public void callbackPersonalInfo(Object object)
			throws Exception {
		
		this.update("hrm.approve.callbackPersonalInfo", object);
	}
	
	
	@Override
	public void insertPersonalInfo(Object object)
			throws Exception {
		
		this.insert("hrm.approve.insertPersonalInfo", object);
	}

	@Override
	public void updatePersonalInfoForPic(Object object, String status)
			throws Exception {
		// TODO Auto-generated method stub
		if (status.equals("1")) {
			this.insert("hrm.approve.updatePersonalInfoForPic1", object);
		} else if (status.equals("2")) {
			this.update("hrm.approve.updatePersonalInfoForPic2", object);
		} else if (status.equals("3")) {
			this.update("hrm.approve.updatePersonalInfoForPic3", object);
		}
		this.update("hrm.approve.updatePersonalInfoApply", object);
	}

	@Override
	public void updateProductInfo(Object object, String status)
			throws Exception {
		// TODO Auto-generated method stub
		if (status.equals("1")) {
			this.insert("hrm.approve.updateProductInfo1", object);
		} else if (status.equals("2")) {
			this.update("hrm.approve.updateProductInfo2", object);
		} else if (status.equals("3")) {
			this.update("hrm.approve.updateProductInfo3", object);
		}
		this.update("hrm.approve.updateProductInfoApply", object);
	}

	@Override
	public void updateQualificationInfo(Object object, String status)
			throws Exception {
		// TODO Auto-generated method stub
		if (status.equals("1")) {
			Map obj=(Map)object;
			String seq = StringUtil.checkNull(this.insert("hrm.approve.updateQualificationInfo1", object));
			//附件上传
			if (obj.get("FILE_NAME") != null && !"".equals(StringUtil.checkNull(obj.get("FILE_NAME"))) && !"".equals(seq) && !"1".equals(seq)) {
				String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME")).split(";");
				String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(";");
				if (fileUrl != null && fileUrl.length > 0) {
					for (int j=0;j<fileUrl.length ;j++) {
						LinkedHashMap fileMap = new LinkedHashMap();
						fileMap.put("fileName", fileName[j]);
						fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
						fileMap.put("APPLY_NO", seq);
						fileMap.put("APPLY_TYPE", "hrQualification");
						fileMap.put("CREATED_BY", obj.get("adminID"));
						this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
					}
				}
			}
		} else if (status.equals("2")) {
			Map objs = (Map) object;
			//保存附件
			if (objs.get("FILE_NAME") != null && !"".equals(StringUtil.checkNull(objs.get("FILE_NAME")))) {
				String[] fileName = StringUtil.checkNull(objs.get("FILE_NAME")).split(";");
				String[] fileUrl = StringUtil.checkNull(objs.get("FILE_URL")).split(";");
				if (fileUrl != null && fileUrl.length > 0) {
					for (int j=0;j<fileUrl.length ;j++) {
						LinkedHashMap fileMap = new LinkedHashMap();
						fileMap.put("fileName", fileName[j]);
						fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + objs.get("adminID") + "/" + fileUrl[j]);
						fileMap.put("APPLY_NO", objs.get("UPDATE_QUAL_NO"));
						fileMap.put("APPLY_TYPE", "hrQualification");
						/*fileMap.put("CREATED_BY", objs.get("CREATE_BY"));*/
						fileMap.put("CREATED_BY", objs.get("adminID"));
						this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
		}
			}
		}
			//删除附件
			LinkedHashMap obj=(LinkedHashMap)object;
			String fileNos=(String)obj.get("FILENOSSTR");
			LinkedHashMap fileMap = new LinkedHashMap();
			fileMap.put("fileNos", fileNos);
			this.orgManageDao.deleteFile(fileMap);
			//修改成功
			this.update("hrm.approve.updateQualificationInfo2", object);
		} else if (status.equals("3")) {
			this.update("hrm.approve.updateQualificationInfo3", object);
		}
		this.update("hrm.approve.updateQualificationInfoApply", object);
	}
	@Override
	public void callbackQualificationInfo(Object object)
			throws Exception {
		this.update("hrm.approve.callbackQualificationInfo", object);
	}
	@Override
	public void insertQualificationInfo(Object object)
			throws Exception {
		this.insert("hrm.approve.insertQualificationInfo", object);
	}

	@Override
	public void updateRewardInfo(Object object, String status) throws Exception {
		// TODO Auto-generated method stub
		if (status.equals("1")) {
			this.insert("hrm.approve.updateRewardInfo1", object);
		} else if (status.equals("2")) {
			this.update("hrm.approve.updateRewardInfo2", object);
		} else if (status.equals("3")) {
			this.update("hrm.approve.updateRewardInfo3", object);
		}
		this.update("hrm.approve.updateRewardInfoApply", object);
	}
	


	@Override
	public void updateWorkInfo(Object object, String status) throws Exception {
		// TODO Auto-generated method stub
		if (status.equals("1")) {
			this.insert("hrm.approve.updateWorkInfo1", object);
		} else if (status.equals("2")) {
			this.update("hrm.approve.updateWorkInfo2", object);
		} else if (status.equals("3")) {
			this.update("hrm.approve.updateWorkInfo3", object);
		}
		this.update("hrm.approve.updateWorkInfoApply", object);
	}
	
	@Override
	public void callbackWorkInfo(Object object) throws Exception {
		this.update("hrm.approve.callbackWorkInfo", object);
	}
	@Override
	public void insertWorkInfo(Object object) throws Exception {
		this.insert("hrm.approve.insertWorkInfo", object);
	}

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
	 * 生成部门树
	 */

	@SuppressWarnings("unchecked")
	public List getParentCodeList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.getParentCodeList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 生成部门区分列表
	 */

	@SuppressWarnings("unchecked")
	public List getParentCodeDifList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.getParentCodeDifList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 根据部门编号统计年龄
	 */
	@SuppressWarnings("unchecked")
	public List manageAgeCountList(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this.queryForList("hrm.count.manageAgeCountList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getAgeListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.manageAgeCountList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getGradeBGZListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.manageGradeBGZCountList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getGradeSCZListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.manageGradeSCZCountList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getPositionListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.managePositionCountList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getEduListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.manageEduCountList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getSexListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.manageSexCountList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getEmpTypeListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.manageEmpTypeCountList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getWorkAgeListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.manageWorkAgeCountList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 得到所有部门
	 */

	@SuppressWarnings("unchecked")
	public List getAllDept(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.getAllDept", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 得到所有部门 区分
	 */

	@SuppressWarnings("unchecked")
	public List getAllDeptDif(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.getAllDeptDif", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 入职人数统计
	 */

	@SuppressWarnings("unchecked")
	public List getEmpTypeListByDeptNo1(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.manageEmpTypeCountList1",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getGradeBGZListByDeptNo1(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.getGradeBGZListByDeptNo1",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getGradeSCZListByDeptNo1(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.getGradeSCZListByDeptNo1",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getPositionListByDeptNo1(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.managePositionCountList1", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	// //退职统计

	@SuppressWarnings("unchecked")
	public List getEmpTypeListByDeptNo2(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.manageEmpTypeCountList2",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getResignResonList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.getResignResonList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getGradeBGZListByDeptNo2(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.manageGradeBGZCountListForLift",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getGradeSCZListByDeptNo2(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.manageGradeSCZCountListForLift",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getPositionListByDeptNo2(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.managePositionCountList2", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public void collPD(Object obj) throws SQLException {

		this.insert("hrm.count.callPD", obj);

	}

	/*
	 * 变更明细申请单个Object start
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Object getApplyNumber(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject("hrm.approve.getApplyNumber",
					object);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("查询失败");
		}
		return returnList;
	}

	/*
	 * 变更明细申请单个Object start
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonalPhotoNullNumber(Object object) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject(
					"hrm.approve.getPersonalPhotoNullNumber", object);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("查询失败");
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getAllDeptList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("hrm.count.getAllDeptList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public void saveOrzTemp(Object object) throws Exception {
		// TODO Auto-generated method stub

		this.insert("hrm.count.saveOrzTemp", object);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountAgeInfoSonList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountAgeInfoSonList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountMonthInfoSonList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountMonthInfoSonList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountMonthInLIZHIfoSonList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountMonthInLIZHIfoSonList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountWorkAgeInfoSonList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountWorkAgeInfoSonList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountEduInfoSonList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountEduInfoSonList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountSexInfoSonList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountSexInfoSonList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountGradeInfoSonList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountGradeInfoSonList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountEmpTypeSonList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountEmpTypeSonList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountRESIGNRESONInfoSonList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountRESIGNRESONInfoSonList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountGradeForLiftInfoSonList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountGradeForLiftInfoSonList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountEmpTypeInfoSonList1(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountEmpTypeInfoSonList1", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountGradeInfoSonList1(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountGradeInfoSonList1", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountSocialInfoSonList1(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"hrm.count.monthPersonCountSocialInfoSonList1", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
}