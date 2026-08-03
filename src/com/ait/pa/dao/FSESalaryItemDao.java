package com.ait.pa.dao;

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
public interface FSESalaryItemDao {
	/**
	 * 分页查看法人、  职种（人员类型组）、和人员类型的对应关系   页面只显示职种和人员类型的对应关系
	 * @param paramMap
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getFSESalaryList(Map paramMap, int pageNum, int numPerPage);

	/**
	 * 同上  只是不分页
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getFSESalaryList(Map paramMap);

	/**
	 * 查找上面信息的总条数
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getFSESalaryCnt(Map paramMap);

	/**
	 * 获取人员类型和人员类型组导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getFSESalaryTempList(Object object);
	
	/**
	 * 获取人员类型和人员类型组导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getFSESalaryTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取人员类型和人员类型组导入信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getFSESalaryTempCnt(Object object);
	
	/**
	 * 获取出错的人员类型和人员类型组导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getFSESalaryTempErrorCnt(Object object);

	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getFSESalaryImportTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getFSESalaryImportTempList(Object object);
	
	/**
	 * 获取人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getFSESalaryImportTempCnt(Object object);
	
	/**
	 * 获取出错的人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getFSESalaryImportTempErrorCnt(Object object);
	
	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importFSESalaryFromExcel(Object object)  throws Exception;
}
