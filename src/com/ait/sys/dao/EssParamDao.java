package com.ait.sys.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EssParamDao {
	
	@SuppressWarnings("unchecked")
	public List getEssParamList(Object object);
	
	public Object getEssParam(Object object);
	
	public void updateEssParamInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCpnyList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getEssCheckParamList(Object object);
	
	public void updateEssCheckParamInfo(Object object) throws Exception;
	
	
	
	
	public Object getOtConverParam(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getOtConverParamList(Object object);
	
	public int getOtConverParamCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getOtConverParamList(Object object, int currentPage, int pageSize);
	
	public int addOtConverParamInfo(Object object) throws Exception;
	
	public int updateOtConverParamInfo(Object object) throws Exception;
	
	public int deleteOtConverParam(Object object) throws SQLException;

	@SuppressWarnings("unchecked")
	public List getVacationStandardManageList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getVacationStandardManageList(Object object);
	
	@SuppressWarnings("unchecked")
	public int getVacationStandardManageCnt(Object object);
	@SuppressWarnings("unchecked")
	public List getWorkAreaList(Object obj);
	@SuppressWarnings("unchecked")
	public int saveVacationStandardManage(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public int deleteVacationStandardManage(Object object) throws SQLException;

	/**
	 * 根据id查询相应的调休说明
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-3 下午2:41:38 
	* @version V1.0
	 */
	public List getManageList(Object object);

	public int updateVacationStandardManage(Object object) throws Exception;
}
