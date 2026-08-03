package com.ait.pa.service.insurance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsurancePersonnelSer.java
 * @Description:
 * @Create date: 2012-2-10 下午06:16:32
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface InsurancePersonnelSer {
	
	@SuppressWarnings("unchecked")
	public List getInsurancePersonnelList(HttpServletRequest request) ;
	
	public Object getInsurancePersonnelInfo(HttpServletRequest request);
	
	public int getInsurancePersonnelCnt(HttpServletRequest request);
	
	public int updateInsurancePersonnelInfo(HttpServletRequest request);
	
	public int deleteInsurancePersonnelInfo(HttpServletRequest request);
	
	public int updateIsCalcFlagByPersonId(HttpServletRequest request) ;
	
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
	public List getInsuranceObjectList(HttpServletRequest request)throws Exception ;
	
	/**
	 * 获得所有保险参保人员总数
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:17:51 
	* @version V1.0
	 */
	public int getInsuranceObjectListCnt(HttpServletRequest request)throws Exception;

	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:18:41 
	* @version V1.0
	 */
	public int updateInsCalcFlagByPersonId(HttpServletRequest request)throws Exception;

	public Object getInsuranceObjectInfo(HttpServletRequest request)throws Exception;

	public int updateInsuranceObjectInfo(HttpServletRequest request)throws Exception;
	
	public int initInsCalcObject(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getInsCalcObjectTempList(HttpServletRequest request) ;
	
	public int getInsCalcObjectTempCnt(HttpServletRequest request) ;
	
	public int getInsCalcObjectTempErrorCnt(HttpServletRequest request) ;
	
	/**
	 * 单条删除保险计算对象( delete insurance object)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delInsCalcObject(HttpServletRequest request)throws Exception;
	
	/**
	 * 添加导入的保险计算对象数据(add insurance object data)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addImportInsObjectData(HttpServletRequest request)throws Exception;
	
	/**
	 * 删除临时表中所有导入的保险计算对象申请(delete insurance object information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelInsObjectImport(HttpServletRequest request)throws Exception;
	
	/**
	 * 删除导入的保险计算对象申请(delete insurance object information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delInsObjectImport(HttpServletRequest request)throws Exception;
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getFundObjectList(HttpServletRequest request)throws Exception ;
	
	public int getFundObjectListCnt(HttpServletRequest request)throws Exception;
	
	public int initFundCalcObject(HttpServletRequest request) throws Exception;
	
	public int deleteFundPersonnelInfo(HttpServletRequest request);
	
	public int updateFundCalcFlagByPersonId(HttpServletRequest request)throws Exception;
	
	public boolean delFundCalcObject(HttpServletRequest request)throws Exception;
	
	public Object getFundObjectInfo(HttpServletRequest request)throws Exception;
	
	public int updateFundObjectInfo(HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getFundCalcObjectTempList(HttpServletRequest request) ;
	
	public int getFundCalcObjectTempCnt(HttpServletRequest request) ;
	
	public int getFundCalcObjectTempErrorCnt(HttpServletRequest request) ;
	
	public int addImportFundObjectData(HttpServletRequest request)throws Exception;
	
	public int cancelFundObjectImport(HttpServletRequest request)throws Exception;

	public boolean delFundObjectImport(HttpServletRequest request)throws Exception;

	public int updateInsCalcGJJFlagByPersonId(HttpServletRequest request) throws Exception;
}
