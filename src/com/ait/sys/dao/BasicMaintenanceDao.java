package com.ait.sys.dao;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName BasicMaintenanceDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:22:13
 * @version 5.0
 *
 */
public interface BasicMaintenanceDao {
	@SuppressWarnings("unchecked")
	public List getParentCodeList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(Object object);
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCodeWithParam(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCodeWithParam(Object object);
	
	
	public void addCodeInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public int addCodeInfo(List object);
	
	public void updateCodeInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int updateCodeInfo(List object);
	
	public void deleteCodeInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int deleteCodeInfo(List object);
	
	public int getCodeListByParentCodeCnt(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public Map getCodeByCodeNo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCodeTreeForAll(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCodeTreeByParentCode(Object object)throws Exception;
	
	public String saveCodeParam(Object object,HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCodePamersList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getCodePamersList(Object object);
	
	public int getCodePamersListCnt(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public Map getCodePamasByParamNo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCodeTreeForEditCodePamas(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getcodeTreeForEdit(Object object)throws Exception;
	
	public String editCodeParam(Object object,HttpServletRequest request)throws Exception;

	@SuppressWarnings("unchecked")
	public List getParamCodeListByCpnyID(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getParamCodeListByCpnyID(Object object);
	
	public int getParamCodeListByCpnyIDCnt(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getParamCodeListByTableName(Object object);
	@SuppressWarnings("unchecked")
	public List getDeptListByCpnyID(Object object);
	/*部门Combo*/
	@SuppressWarnings("unchecked")
	public List getDeptListByCpnyIDCombo(Object object,int currentPage, int pageSize);
	/*人员类型Combo*/
	@SuppressWarnings("unchecked")
	public List getJobTypeByCpnyIDCombo(Object object,int currentPage, int pageSize);
	/*营业员职责Combo*/
	@SuppressWarnings("unchecked")
	public List getJobPositionByCpnyIDCombo(Object object,int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public List getParamCodeCombinListByCpnyID(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getSyCodeDescByCpnyID(Object object);

	@SuppressWarnings("unchecked")
	public List getParamCodeListByCpnyID2(Object object, int currentPage, int pageSize);/**
	 * 取得人员类型
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEmpType(Object obj) ;
	/** 取工资支付计划
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getScheduleNo(Object obj);
	/**
	 * 取得非sy_code表里的code
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getNonSyCodeListByCpnyID(Map paramMap, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getNonSyCodeListByCpnyID(Map paramMap);
	@SuppressWarnings("unchecked")
	public List getCodeListForArItem(Map paramMap, int i, int j);
}
