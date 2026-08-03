package com.ait.hrm.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface RecruitManageSer {
	
	public List viewRecruitList(HttpServletRequest request);
	
	public int addRecruitInfo(HttpServletRequest request) ;
	/**
	 * 更新招聘发令照片
	 */
	@SuppressWarnings("unchecked")
	public int updateRecruitPhotoInfo(HttpServletRequest request, String photoPath) ;
	
	@SuppressWarnings("unchecked")
	public int deleteRecruitInfo(HttpServletRequest request) ;
	
	/**
	 * 工作经历管理
	 * @param request
	 * @return
	 */
	public List viewWorkExperienceList(HttpServletRequest request);

	/**
	 * 教育经历管理
	 */
	public List viewEducationList(HttpServletRequest request);
	
	/**
	 * 家庭信息管理
	 */
	public List viewFamilyList(HttpServletRequest request);

	/**
	 * 招聘发令确认 、拉回
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String executeRecruit(HttpServletRequest request) ;
	
	/**
	 * 新增招聘注册信息
	 */
	@SuppressWarnings("unchecked")
	public int addRegisterInfo(HttpServletRequest request) ;
	
	/**
	 * 执行sql
	 */
	public List doSql(HttpServletRequest request) ;

	/**
	 * 获取注册日信息
	 */
	public List viewRegisterInfoList(HttpServletRequest request,int flag) ;

	/**
	 * 获取招聘发令批量信息
	 */
	public List viewRecruitBatchList(LinkedHashMap paramData) ;
	
	/**
	 * 单条删除批量导入的人员信息
	 */
	@SuppressWarnings("unchecked")
	public int deleteRecruitBatchInfo(HttpServletRequest request) ;

	/**
	 * 发令概要管理
	 */
	public List viewResumeList(HttpServletRequest request);
	public int addResumeInfo(HttpServletRequest request) ;
	public int deleteResumeInfo(HttpServletRequest request);

	/**
	 * 获取发令批量信息
	 */
	public List viewExperienceBatchList(LinkedHashMap paramData) ;
	
	/**
	 * 单条删除批量导入的发令信息
	 */
	@SuppressWarnings("unchecked")
	public int deleteExperienceBatchInfo(HttpServletRequest request) ;

	/**
	 * 批量导入信息修改
	 */
	@SuppressWarnings("unchecked")
	public int addRecruitBatchInfo(HttpServletRequest request);
	
	/**
	 * 增加一条空的数据
	 */
	@SuppressWarnings("unchecked")
	public int addEmptyRecruitInfo(HttpServletRequest request);
	
	/**
	 * 删除空的数据 
	 */
	@SuppressWarnings("unchecked")
	public int deleteEmptyRecruitInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int deleteExperienceInfo(HttpServletRequest request);

	/**
	 * 获取发令信息
	 */
	public List viewExperienceList(HttpServletRequest request) ;

	/**
	 * 录用发令  判断社号是否存在
	 */
	public int IS_EXISTS_EMPID(HttpServletRequest request);
	/**
	 * 统一录用发令  判断社号是否存在
	 */
	public int IS_EXISTS_EMPID1(HttpServletRequest request);
	
	/**
	 * 判断姓名是否存在
	 */
	public int IS_EXISTS_NAME(HttpServletRequest request);

	/**
	 * 主要业务说明书
	 */
	public List viewMainBusinessList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public int addMainBusinessInfo(HttpServletRequest request);
	
	/**
	 * 验证身份证号码是否重复
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String PR_VALID_EMP_DATA(HttpServletRequest request);
	
	/**
	 * 获取供人公司
	 */
	public List viewPersonSupplier(LinkedHashMap paramData) ;
	/**
	 * 删除空的供人公司
	 */
	@SuppressWarnings("unchecked")
	public int deleteEmptyPersonSupplier(HttpServletRequest request);
	
	/**
	 * 增加一条空的数据
	 */
	@SuppressWarnings("unchecked")
	public int addEmptyPersonSupplier(HttpServletRequest request);
	
	/**
	 * 供人公司修改
	 */
	@SuppressWarnings("unchecked")
	public int addPersonSupplier(HttpServletRequest request);
	
	/**
	 * 单条删除公司信息
	 */
	@SuppressWarnings("unchecked")
	public int deletePersonSupplierInfo(HttpServletRequest request) ;
}
