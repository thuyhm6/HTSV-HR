package com.ait.is.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface StopInsureSer {

	@SuppressWarnings("unchecked")	
	public List getStopInsureList(HttpServletRequest request);
	
	public int getStopInsureCnt(HttpServletRequest request);
	
	public int deleteStopInsureInfo(HttpServletRequest request) throws SQLException;
	
	public void allowPaBenStopInsureUpdateBz(String id);
	
	@SuppressWarnings("unchecked")
	public List getAllowPaBenStopInsureUpdateBz(HttpServletRequest request);
	
	public void backPaBenStopInsureUpdateBz();
	
	public int updatePaBenManageAddInfoBz(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getInsStopNumInfoExcel(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getInsuranceAreaListByCpnyId(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getInsuranceParamDataChList(HttpServletRequest request);
	
	public int getInsuranceParamDataChCnt(HttpServletRequest request) throws Exception;
	
	/**
	 * 添加保险参数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addInsuranceParamData(HttpServletRequest request) throws Exception;
	
	/**
	 * 修改保险参数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int updateInsuranceParamData(HttpServletRequest request) throws Exception;
	
	/**
	 * 导入保险参数--查看信息列表(get insurance param data import list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getIsParamImportInfoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 导入保险参数--查看信息列表总数(get insurance param data import list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getIsParamImportInfoListCnt(HttpServletRequest request,String cntType) throws Exception;
	
	/**
	 * 删除导入的保险参数信息(delete insurance param data information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delIsParamDataImport(HttpServletRequest request)throws Exception;
	
	/**
	 * 添加导入的保险参数数据(add insurance param data)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addImportIsParamData(HttpServletRequest request)throws Exception;
	
	/**
	 * 添加导入的保险参数数据(add insurance param data)---没有大区编码的法人
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addImportIsParamDataNch(HttpServletRequest request)throws Exception;
	
	/**
	 * 删除临时表中所有导入的保险参数(delete insurance param data information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelIsParamDataImport(HttpServletRequest request)throws Exception;
	
	public List getInsAreaInfoListByKey(HttpServletRequest request) throws Exception;
	
	public List getInsureInfoListByKey(HttpServletRequest request) throws Exception;
}
