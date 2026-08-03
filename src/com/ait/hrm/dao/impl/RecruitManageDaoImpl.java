package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import org.apache.commons.lang.ObjectUtils;

import com.ait.hrm.dao.RecruitManageDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class RecruitManageDaoImpl extends SqlMapClientSupport implements RecruitManageDao {	
	 
	@Autowired
	private SyLanguageDao syLanguageDao;

	public List viewRecruitList(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.viewRecruitList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	/**
	 * 添加招聘发令信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addRecruitInfo(Object object)throws Exception {
		Map obj=(Map)object; 
		if(obj.get("currentIndex") != null ){
			//基本信息
			if("0".equals(StringUtil.checkNull(obj.get("currentIndex"))) ){
				if(((LinkedHashMap)object).get("PERSON_ID")!=null && !"".equals(((LinkedHashMap)object).get("PERSON_ID").toString())){
					//需要update
					this.update("recruit.manage.updateEmployeeInfo",object);
				}else{
					//获取
					obj.put("V_EMP_TYPE", "NORMAL");
					String empTypeCode = StringUtil.checkNull(obj.get("EMP_TYPE_CODE"));
					if("14015550".equals(empTypeCode) && !"SPC_SH".equals(obj.get("interCpnyID"))){
						obj.put("V_EMP_TYPE", "TEMP");
					}else if("SPC_SH".equals(obj.get("interCpnyID"))){
						if("10418".equals(empTypeCode) || "14016138".equals(empTypeCode)){
							obj.put("V_EMP_TYPE", "F");
						}else{
							obj.put("V_EMP_TYPE", StringUtil.checkNull(obj.get("EMPLOYEE_BELONG")));
						}
					}
					if("HAE".equals(obj.get("interCpnyID"))){
						String empid = this.getEmpId(obj);
						obj.put("EMPID", empid);
					}
					
					this.insert("recruit.manage.addEmployeeInfo",obj);
				}
			//附加信息
			}else if("1".equals(StringUtil.checkNull(obj.get("currentIndex"))) ){
				//需要update
				this.update("recruit.manage.updatePersonalInfo",object);
			//教育信息
			}else if("2".equals(StringUtil.checkNull(obj.get("currentIndex"))) ){
				String seq = "";
				if(((LinkedHashMap)object).get("SEQ")!=null && !"".equals(((LinkedHashMap)object).get("SEQ").toString())){
					//需要update
					this.update("recruit.manage.updateEducationInfo",object);
				}else{
					seq = StringUtil.checkNull(this.insert("recruit.manage.addEducationInfo",object));
					obj.put("SEQ", seq);
					//附件上传
					if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
						String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
						String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
						if (fileUrl != null && fileUrl.length > 0) {
							for (int j=0;j<fileUrl.length ;j++) {
								LinkedHashMap fileMap = new LinkedHashMap();
								fileMap.put("fileName", fileName[j]);
								fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
								fileMap.put("APPLY_NO", seq);
								fileMap.put("APPLY_TYPE", "EDU_RECRUIT");
								fileMap.put("CREATED_BY", obj.get("adminID"));
								this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
							}
						}
					}
				}
			//经历信息
			}else if("3".equals(StringUtil.checkNull(obj.get("currentIndex"))) ){
				if(((LinkedHashMap)object).get("SEQ")!=null && !"".equals(((LinkedHashMap)object).get("SEQ").toString())){
					//需要update
					this.update("recruit.manage.updateWorkExperienceInfo",object);
				}else{
					this.insert("recruit.manage.addWorkExperienceInfo",object);
				}
			//家庭信息
			}else if("4".equals(StringUtil.checkNull(obj.get("currentIndex"))) ){
				if(((LinkedHashMap)object).get("SEQ")!=null && !"".equals(((LinkedHashMap)object).get("SEQ").toString())){
					//需要update
					this.update("recruit.manage.updateFamilyInfo",object);
				}else{
					this.insert("recruit.manage.addFamilyInfo",object);
				}
			//照片采集
			}else if("5".equals(StringUtil.checkNull(obj.get("currentIndex"))) ){
				this.update("recruit.manage.updatePhotoPathInfo",object);
			}
		}
		return 1;
	}
	

	/**
	 * 删除招聘发令
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteRecruitInfo(Object object)throws Exception {
		Map obj=(Map)object; 
		if(obj.get("currentIndex") != null ){
			//基本信息
			if("0".equals(StringUtil.checkNull(obj.get("currentIndex"))) ){
				this.update("recruit.manage.deleteEmployeeInfo",object);
			//教育信息
			}else if("2".equals(StringUtil.checkNull(obj.get("currentIndex"))) ){
				this.update("recruit.manage.deleteEducationInfo",object);
			//经历信息
			}else if("3".equals(StringUtil.checkNull(obj.get("currentIndex"))) ){
				this.update("recruit.manage.deleteWorkExperienceInfo",object);
			//家庭信息
			}else if("4".equals(StringUtil.checkNull(obj.get("currentIndex"))) ){
				this.update("recruit.manage.deleteFamilyInfo",object);
			}
		}
		return 1;
	}
	

	/**
	 * 工作经历管理
	 */
	public List viewWorkExperienceList(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.viewWorkExperienceList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	/**
	 * 教育经历管理
	 */
	public List viewEducationList(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.viewEducationList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	/**
	 * 家庭信息管理
	 */
	public List viewFamilyList(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.viewFamilyList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	

	/**
	 * 招聘执行
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String executeRecruit(Object object)  throws Exception{
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("recruit.manage.executeRecruit", paramMap) ;	
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage();			
			throw e;
		}
		return returnString ;
	}
	
	/**
	 * 执行sql
	 */
	public List doSql(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.doSql",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 添加招聘发令注册信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addRegisterInfo(Object object)throws Exception {
		this.insert("recruit.manage.addRegisterInfo",object);
		return 1;
	}

	/**
	 * 获取注册日信息
	 */
	public List viewRegisterInfoList(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.viewRegisterInfoList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}

	/**
	 * 获取招聘发令批量信息
	 */
	public List viewRecruitBatchList(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.viewRecruitBatchList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	/**
	 * 单条删除批量导入的人员信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteRecruitBatchInfo(Object object)throws Exception {
		this.update("recruit.manage.deleteRecruitBatchInfo",object);
		return 1;
	}

	/**
	 * 发令概要管理
	 */
	public List viewResumeList(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.viewResumeList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	@SuppressWarnings("unchecked")
	public int addResumeInfo(Object object)throws Exception {
		Map obj=(Map)object; 
		if(((LinkedHashMap)object).get("SEQ")!=null && !"".equals(((LinkedHashMap)object).get("SEQ").toString())){
			//需要update
			this.update("recruit.manage.updateResumeInfo",object);
		}else{
			this.insert("recruit.manage.addResumeInfo",object);
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteResumeInfo(Object object)throws Exception {
		this.update("recruit.manage.deleteResumeInfo",object);
		return 1;
	}
	
	/**
	 * 获取发令批量信息
	 */
	public List viewExperienceBatchList(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.viewExperienceBatchList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	/**
	 * 单条删除批量导入的发令信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteExperienceBatchInfo(Object object)throws Exception {
		this.update("recruit.manage.deleteExperienceBatchInfo",object);
		return 1;
	}
	
	/**
	 * 批量导入信息修改
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addRecruitBatchInfo(Object object)throws Exception {
		this.updateForList("recruit.manage.addRecruitBatchInfo",(List)object);
		return 1;
	}

	/**
	 * 增加一条空的数据
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addEmptyRecruitInfo(Object object)throws Exception {
		this.insert("recruit.manage.addEmptyRecruitInfo",object);
		return 1;
	}
	
	/**
	 * 删除空的数据 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteEmptyRecruitInfo(Object object)throws Exception {
		this.insert("recruit.manage.deleteEmptyRecruitInfo",object);
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteExperienceInfo(Object object)throws Exception {
		this.insert("recruit.manage.deleteExperienceInfo",object);
		return 1;
	}

	/**
	 * 获取发令信息
	 */
	public List viewExperienceList(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.viewExperienceList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 录用发令判断社号是否存在
	 * @param obj
	 * @return
	 */
	public int IS_EXISTS_EMPID(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("recruit.manage.IS_EXISTS_EMPID", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 统一录用发令判断社号是否存在
	 * @param obj
	 * @return
	 */
	public int IS_EXISTS_EMPID1(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("recruit.manage.IS_EXISTS_EMPID1", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 统一录用发令判断社号是否存在
	 * @param obj
	 * @return
	 */
	public List isExistsEmpidRecruitBatch(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.isExistsEmpidRecruitBatch",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	/**
	 * 判断姓名是否存在
	 * @param obj
	 * @return
	 */
	public int IS_EXISTS_NAME(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("recruit.manage.IS_EXISTS_NAME", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 主要业务说明书
	 */
	public List viewMainBusinessList(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.viewMainBusinessList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	@SuppressWarnings("unchecked")
	public int addMainBusinessInfo(Object object)throws Exception {
		Map obj=(Map)object; 
		//需要update
		this.update("recruit.manage.updateMainBusinessInfo",object);
		//附件上传
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", obj.get("CODE_NO"));
					fileMap.put("APPLY_TYPE", "MAIN_BUSINESS");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
		return 1;
	}
	
	/**
	 * 招聘执行
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getEmpId(Object object)  throws Exception{
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("recruit.manage.getEmpId", paramMap) ;	
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage();			
			throw e;
		}
		return returnString ;
	}
	
	/**
	 * 验证身份证号码是否重复
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String PR_VALID_EMP_DATA(Object object)  throws Exception{
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("recruit.manage.PR_VALID_EMP_DATA", paramMap) ;	
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage();			
			throw e;
		}
		return returnString ;
	}
	
	/**
	 * 获取供人公司
	 */
	public List viewPersonSupplier(Object object) {
		List result = null;
		try {
			result = this.queryForList("recruit.manage.viewPersonSupplier",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;		
	}
	
	/**
	 * 删除空的数据 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteEmptyPersonSupplier(Object object)throws Exception {
		this.insert("recruit.manage.deleteEmptyPersonSupplier",object);
		return 1;
	}
	
	/**
	 * 增加一条空的数据
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addEmptyPersonSupplier(Object object)throws Exception {
		this.insert("recruit.manage.addEmptyPersonSupplier",object);
		return 1;
	}
	
	/**
	 * 公司信息修改
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addPersonSupplier(Object object)throws Exception {
		this.updateForList("recruit.manage.addPersonSupplier",(List)object);
		return 1;
	}
	
	/**
	 * 单条删除公司信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deletePersonSupplierInfo(Object object)throws Exception {
		this.update("recruit.manage.deletePersonSupplierInfo",object);
		return 1;
	}
}