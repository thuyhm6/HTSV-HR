package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsurancePersonnelDao.java
 * @Description:
 * @Create date: 2012-2-10 下午06:17:14
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface InsurancePersonnelDao {

	@SuppressWarnings("unchecked")
	public List getInsurancePersonnelList(Object object);
	
	public int getInsurancePersonnelCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInsurancePersonnelList(Object object, int currentPage, int pageSize);
	
	public Object getInsurancePersonnelInfo(Object object);
	
	public int updateInsurancePersonnelInfo(Object object);
//	
	public int deleteInsurancePersonnelInfo(Object object) ;
//	
//	public int checkAddInsurancePersonnelInfo(Object object) ;
	
	public int updateIsCalcFlagByPersonId(Object object) ;
	
	/**
	 * 获得所有保险参保人员
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:17:51 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceObjectList(Object object)throws Exception;
	
	/**
	 * 获得所有保险参保人员
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:17:51 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceObjectList(Object object, int currentPage, int pageSize)throws Exception;
	
	/**
	 * 获得所有保险参保人员总数
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:17:51 
	* @version V1.0
	 */
	public int getInsuranceObjectListCnt(Object object)throws Exception;

	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:18:41 
	* @version V1.0
	 */
	public int updateInsCalcFlagByPersonId(Object obj)throws Exception;

	@SuppressWarnings("unchecked")
	public Object getInsuranceObjectInfo(LinkedHashMap paramMap)throws Exception;

	@SuppressWarnings("unchecked")
	public int updateInsuranceObjectInfo(LinkedHashMap paramMap);
	
	/**
	 * 初始化保险计算对象
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lufeng@ait.net.cn
	* @date 2014-8-28 下午16:21:52 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public int initInsCalcObject(LinkedHashMap paramMap)throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getPaLockFlagByMonth(LinkedHashMap paramMap)throws Exception;
	
	public int getInsCalcObjectTempErrorCnt(Object object);
	
	public int getInsCalcObjectTempCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInsCalcObjectTempList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInsCalcObjectTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 单条删除保险计算对象( delete insurance object)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delInsCalcObject(Object object) throws Exception;
	
	/**
	 * 批量保存导入的保险计算对象数据(add insurance object data)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addInsObjectDataImport(List list) throws Exception;
	
	/**
	 * 将导入的信息的check_flag=0
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateInsObjectDataCheckFlag(List list) throws Exception;
	
	/**
	 * 更新导入的保险计算对象的check结果(update insurance object data of import for check result)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int updateInsObjectDataCheckResult(Object object) throws Exception;
	

	/**
	 * 验证正式表里是否已经存在该保险计算对象信息
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsObjectInfoExistList(Object obj);
	
	/**
	 * 删除临时表中所有导入的保险计算对象申请(delete insurance object information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean cancelInsObjectImport(Object object) throws Exception;
	
	/**
	 * 删除导入的保险计算对象申请(delete insurance object information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delInsObjectImport(Object object)throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getFundObjectList(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getFundObjectList(Object object, int currentPage, int pageSize)throws Exception;
	
	public int getFundObjectListCnt(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public int initFundCalcObject(LinkedHashMap paramMap)throws Exception;
	
	public int deleteFundPersonnelInfo(Object object) ;
	
	public int updateFundCalcFlagByPersonId(Object obj)throws Exception;
	
	public boolean delFundCalcObject(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public Object getFundObjectInfo(LinkedHashMap paramMap)throws Exception;
	
	@SuppressWarnings("unchecked")
	public int updateFundObjectInfo(LinkedHashMap paramMap);
	
	public int getFundCalcObjectTempErrorCnt(Object object);
	
	public int getFundCalcObjectTempCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getFundCalcObjectTempList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getFundCalcObjectTempList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getFundObjectInfoExistList(Object obj);
	
	public boolean cancelFundObjectImport(Object object) throws Exception;

	public boolean delFundObjectImport(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addFundObjectDataImport(List list) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int updateFundObjectDataCheckFlag(List list) throws Exception;
	
	public int updateFundObjectDataCheckResult(Object object) throws Exception;

	public int updateInsObjectDataFormalResult(Object object)  throws Exception;

	public int updateInsCalcGJJFlagByPersonId(Object obj) throws Exception;
	
}
