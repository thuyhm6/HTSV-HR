package com.ait.hrm.service;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface JobTypeSer {
	/**
	 * 分页查看法人、  职种（人员类型组）、和人员类型的对应关系   页面只显示职种和人员类型的对应关系
	 * @param paramMap
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getJobTypeList(HttpServletRequest request);

	/**
	 * 同上  只是不分页
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getJobTypeCnt(HttpServletRequest request);

	/**
	 * 查找上面信息的总条数
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addJobTypeInfo(HttpServletRequest request);

	/**
	 * 添加  法人、职种、人员类型的对应关系
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateJobTypeInfo(HttpServletRequest request);

	/**
	 * 删除法人   职种    人员类型的对应关系
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int deleteJobTypeInfo(HttpServletRequest request);

	/**
	 * 显示详细信息
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getJobType(HttpServletRequest request);

	/**
	 * 查找所有的人员类型
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getJobTypeNameList(HttpServletRequest request);
	
	/**
	 * 查找所有的职种（人员类型组）
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getJobTypeGroupNameList(HttpServletRequest request);

	/**
	 * 根据添加或者修改的记录查找该记录是否已经存在，如果存在就禁止该操作
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int checkJobType(HttpServletRequest request) ;
	
	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getJobTypeTempList(HttpServletRequest request) ;
	
	/**
	 * 获取人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getJobTypeTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 人员类型excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelJobTypeData(HttpServletRequest request);
	/**
	 * 根绝人员类型组CODE 获取人员类型
	 * @param paramMap
	 * @return
	 */
	public List getEmpJobTypeList(HttpServletRequest request) ;
	
	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getJobTypeImportTempList(HttpServletRequest request) ;
	
	/**
	 * 获取人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getJobTypeImportTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 组装临促模版信息,导出带错误提示的数据
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getJobTypelateInfoByExcelData(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException;
	/** 
	* @Title: getEmpForGroupToList 
	* @Description: TODO 根据人员类型组查询人员类型，为联动查询服务，11.20修改
	* @param @param object
	* @param @return    
	* @return List    
	* @throws 
	*/
	public List getEmpTypeForGroupToList(HttpServletRequest request);
}
