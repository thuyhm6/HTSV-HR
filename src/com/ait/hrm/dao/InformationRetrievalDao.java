package com.ait.hrm.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface InformationRetrievalDao {
	
	@SuppressWarnings("unchecked")
	public List getEmpRetrieveShowList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getCustomerTableList(Object object);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap getCustomerTableListByNO(Object object);
	
	@SuppressWarnings("unchecked")
	public List getEmpRetrieveShowList(Object object, int currentPage, int pageSize);
	
	public int getEmpRetrieveShowCnt(Object object);
	
	public List getInfoFieldByTableNameList(Object object);
	
	public void saveEmpRetrieveInfo(HttpServletRequest request,Map paramMap) throws Exception;
	
	public void deleteCustTable(HttpServletRequest request,Map paramMap) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCodeParamList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPostGradeForCheckBoxList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPostForCheckBoxList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDutyForCheckBoxList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPositionForCheckBoxList(Object object);

	@SuppressWarnings("unchecked")
	public List getEmpIdRetrieveList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public List getEmpIdRetrieveList(Object object);

	/** 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author liangwei@ait.net.cn 
	* @date 2013-7-29 上午11:15:18 
	* @version V1.0   
	*/
	public int getEmpIdListCnt(Object object);
	
	
	@SuppressWarnings("unchecked")
	public List viewStructureDept(Object object);
	
	
	
	
	
}
