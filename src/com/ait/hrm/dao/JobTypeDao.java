package com.ait.hrm.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName PostDao.java
 * @author wendi@ait.net.cn
 * @Date 2012-5-26 下午05:23:04
 * @version 5.0
 */
public interface JobTypeDao {
	/**
	 * 分页查看法人、  职种（人员类型组）、和人员类型的对应关系   页面只显示职种和人员类型的对应关系
	 * @param paramMap
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getJobTypeList(Map paramMap, int pageNum, int numPerPage);

	/**
	 * 同上  只是不分页
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getJobTypeList(Map paramMap);

	/**
	 * 查找上面信息的总条数
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getJobTypeCnt(Map paramMap);

	/**
	 * 添加  法人、职种、人员类型的对应关系
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addJobTypeInfo(LinkedHashMap paramMap) throws Exception;

	/**
	 * 修改 法人、职种、人员类型的对应关系     主要是调整职种和人员类型的对应关系
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateJobTypeInfo(LinkedHashMap paramMap) throws Exception;

	/**
	 * 删除法人   职种    人员类型的对应关系
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteJobTypeInfo(LinkedHashMap paramMap) throws Exception;

	/**
	 * 显示详细信息
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getJobType(Map paramMap);
	
	/**
	 * 查找所有的人员类型
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getJobTypeNameList(Map paramMap);
	
	/**
	 * 查找所有的职种（人员类型组）
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getJobTypeGroupNameList(Map paramMap);
	
	/**
	 * 添加货修改前检查该法人    职种    人员类型的关系是否已经存在   如果存在不允许该操作
	 * @param paramMap
	 * @return
	 */
	public int checkJobType(Object object);
	
	/**
	 * 获取人员类型和人员类型组导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getJobTypeTempList(Object object);
	
	/**
	 * 获取人员类型和人员类型组导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getJobTypeTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取人员类型和人员类型组导入信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getJobTypeTempCnt(Object object);
	
	/**
	 * 获取出错的人员类型和人员类型组导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getJobTypeTempErrorCnt(Object object);
	

	/**
	 * 根绝人员类型组CODE 获取人员类型
	 * @param paramMap
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEmpJobTypeList(Object object);
	
	/** 
	* @Title: getEmpForGroupToList 
	* @Description: TODO 根据人员类型组查询人员类型，为联动查询服务，11.20修改
	* @param @param object
	* @param @return    
	* @return List    
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	public List getEmpTypeForGroupToList(Object object);
	
	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getJobTypeImportTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getJobTypeImportTempList(Object object);
	
	/**
	 * 获取人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getJobTypeImportTempCnt(Object object);
	
	/**
	 * 获取出错的人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getJobTypeImportTempErrorCnt(Object object);
	
	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importJobTypeFromExcel(Object object)  throws Exception;
}
