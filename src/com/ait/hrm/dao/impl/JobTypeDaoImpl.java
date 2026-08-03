package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.hrm.dao.JobTypeDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class JobTypeDaoImpl extends SqlMapClientSupport implements JobTypeDao{
	
	@Autowired
	private SyLanguageDao syLanguageDao;

	/**
	 * 分页查看法人、  职种（人员类型组）、和人员类型的对应关系   页面只显示职种和人员类型的对应关系
	 * @param paramMap
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getJobTypeList(Map paramMap, int pageNum, int numPerPage) {
		List returnList = new ArrayList() ;
		try {
			if(pageNum > -1 && numPerPage > -1){
				returnList = this.queryForList("hrm.jobType.getJobTypeList", paramMap, pageNum, numPerPage);
			}
			else{
				returnList = this.queryForList("hrm.jobType.getJobTypeList", paramMap);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 同上  只是不分页
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getJobTypeList(Map paramMap) {
        List returnList = new ArrayList() ;
		returnList = this.getJobTypeList(paramMap, -1, -1) ;
		return returnList ;
	}

	/**
	 * 查找上面信息的总条数
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getJobTypeCnt(Map paramMap) {
        int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.jobType.getJobTypeCnt", paramMap)), Integer.class) ;	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 添加  法人、职种、人员类型的对应关系
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addJobTypeInfo(LinkedHashMap paramMap) throws Exception {
		this.insert("hrm.jobType.insertJobType", paramMap);
	}

	/**
	 * 修改 法人、职种、人员类型的对应关系     主要是调整职种和人员类型的对应关系
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateJobTypeInfo(LinkedHashMap paramMap) throws Exception {
		this.update("hrm.jobType.updateJobType", paramMap);
	}

	/**
	 * 删除法人   职种    人员类型的对应关系
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteJobTypeInfo(LinkedHashMap paramMap) throws Exception {
		this.delete("hrm.jobType.deleteJobType", paramMap) ;
	}

	/**
	 * 显示详细信息
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getJobType(Map paramMap) {
        LinkedHashMap returnObj = new LinkedHashMap() ;
        List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("hrm.jobType.getJobType", paramMap);
			if(returnList.size()>0){
				returnObj = (LinkedHashMap) returnList.get(0);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnObj ;
	}

	/**
	 * 查找所有的人员类型
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getJobTypeNameList(Map paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("hrm.jobType.getJobTypeNameList", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 查找所有的人员类型分组也就是职种
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getJobTypeGroupNameList(Map paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("hrm.jobType.getJobTypeGroupNameList", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 根据要添加修改的记录查找该条记录是否已经存在，如果存在就阻止该操作
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkJobType(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.jobType.checkJobType", object), "0"), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 获取人员类型和人员类型组导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getJobTypeTempList(Object object){
		return this.getJobTypeTempList(object, 1, 10);
	}
	
	/**
	 * 获取人员类型和人员类型组导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getJobTypeTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.jobType.getTempSalesTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.jobType.getTempSalesTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取人员类型和人员类型组导入信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getJobTypeTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.jobType.getTempSalesTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取出错的人员类型和人员类型组导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getJobTypeTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.jobType.getTempSalesTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 根绝人员类型组CODE 获取人员类型
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpJobTypeList(Object object) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("hrm.jobType.getEmpJobTypeList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/** 
	* @Title: getEmpForGroupToList 
	* @Description: TODO 根据人员类型组查询人员类型，为联动查询服务，11.20修改
	* @param @param object
	* @param @return    
	* @return List    
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpTypeForGroupToList(Object object){
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("hrm.jobType.getEmpTypeForGroupToList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getJobTypeImportTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.jobType.getJobTypeImportTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.jobType.getJobTypeImportTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getJobTypeImportTempList(Object object){
		List returnList = new ArrayList() ;
		try {
		   returnList = this.queryForList("hrm.jobType.getJobTypeImportTempList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getJobTypeImportTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.jobType.getJobTypeImportTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取出错的人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getJobTypeImportTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.jobType.getJobTypeImportTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importJobTypeFromExcel(Object object)  throws Exception{
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("hrm.jobType.importJobTypeFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			throw e;
		}
		return returnString ;
	}
}
