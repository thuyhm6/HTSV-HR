package com.ait.hrm.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface RecruitManageDao {
	
	public List viewRecruitList(Object object) ;
	
	/**
	 * 插入概要改编insert
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addRecruitInfo(Object object)throws Exception ;
	

	/**
	 * 删除招聘信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteRecruitInfo(Object object)throws Exception ;
	
	/**
	 *	工作经历管理 
	 */
	public List viewWorkExperienceList(Object object);

	/**
	 * 教育经历管理
	 */
	public List viewEducationList(Object object);
	/**
	 * 家庭信息管理
	 */
	public List viewFamilyList(Object object);

	/**
	 * 招聘执行
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String executeRecruit(Object object)  throws Exception;
	
	/**
	 * 执行sql
	 */
	public List doSql(Object object);

	/**
	 * 添加招聘发令注册信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addRegisterInfo(Object object)throws Exception;
	

	/**
	 * 获取注册日信息
	 */
	public List viewRegisterInfoList(Object object);

	/**
	 * 获取招聘发令批量信息
	 */
	public List viewRecruitBatchList(Object object);

	/**
	 * 单条删除批量导入的人员信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteRecruitBatchInfo(Object object)throws Exception ;

	/**
	 *	发令概要管理 
	 */
	public List viewResumeList(Object object);
	public int addResumeInfo(Object object)throws Exception ;
	public int deleteResumeInfo(Object object)throws Exception ;

	/**
	 * 获取发令批量信息
	 */
	public List viewExperienceBatchList(Object object)  ;

	/**
	 * 单条删除批量导入的发令信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteExperienceBatchInfo(Object object)throws Exception;

	/**
	 * 批量导入信息修改
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addRecruitBatchInfo(Object object)throws Exception;
	
	/**
	 * 增加一条空的数据
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addEmptyRecruitInfo(Object object)throws Exception ;
	
	/**
	 * 删除空的数据 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteEmptyRecruitInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public int deleteExperienceInfo(Object object)throws Exception;

	/**
	 * 获取发令信息
	 */
	public List viewExperienceList(Object object);

	/**
	 * 录用发令 判断社号是否存在
	 * @param obj
	 * @return
	 */
	public int IS_EXISTS_EMPID(Object obj);
	
	/**
	 * 统一录用发令 判断社号是否存在
	 * @param obj
	 * @return
	 */
	public int IS_EXISTS_EMPID1(Object obj);
	
	/**
	 * 统一录用发令 判断社号是否存在
	 * @param obj
	 * @return
	 */
	public List isExistsEmpidRecruitBatch(Object obj);
	
	/**
	 * 判断姓名是否存在
	 * @param obj
	 * @return
	 */
	public int IS_EXISTS_NAME(Object obj);

	/**
	 * 主要业务说明书
	 */
	public List viewMainBusinessList(Object object);

	@SuppressWarnings("unchecked")
	public int addMainBusinessInfo(Object object)throws Exception;
	
	/**
	 * 验证身份证号码是否重复
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String PR_VALID_EMP_DATA(Object object)  throws Exception;
	
	/**
	 * 获取供人公司
	 */
	public List viewPersonSupplier(Object object)  ;
	
	/**
	 * 删除空的数据 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteEmptyPersonSupplier(Object object)throws Exception;
	
	/**
	 * 增加一条空的数据
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addEmptyPersonSupplier(Object object)throws Exception ;
	
	/**
	 * 供人公司修改
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addPersonSupplier(Object object)throws Exception;
	
	/**
	 * 单条删除公司信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deletePersonSupplierInfo(Object object)throws Exception;
}