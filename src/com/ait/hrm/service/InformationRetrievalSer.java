package com.ait.hrm.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

public interface InformationRetrievalSer {
	
	@SuppressWarnings("unchecked")
	public List getEmpRetrieveShowList(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCustomerTableList(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap  getCustomerTableListByNO(HttpServletRequest request) throws Exception;
	
    @SuppressWarnings("unchecked")
	public int getEmpRetrieveShowCnt(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getEmpRetrieveShowListAll(HttpServletRequest request) throws Exception;
	
	public List getInfoFieldByTableNameList(HttpServletRequest request,String tableId);
	 
	public int saveEmpRetrieveInfo(HttpServletRequest request);
	
	public int deleteCustTable(HttpServletRequest request);
	
	public List getCodeParamList(HttpServletRequest request)  ;
	
	public List getPostGradeForCheckBoxList(HttpServletRequest request)  ;
	
	public List getPostForCheckBoxList(HttpServletRequest request)  ;
	
	public List getDutyForCheckBoxList(HttpServletRequest request)  ;
	
	public List getPositionForCheckBoxList(HttpServletRequest request)  ;

	/** 
	 * 查询当前法人的 人员信息
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yorio   youjia@ait.net.cn 
	* @date 2013-7-26 下午5:10:43 
	* @version V1.0   
	*/
	public List getEmpIdRetrieveList(HttpServletRequest request);

	/** 
	 * 查询当前法人的 人员信息的数目
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author liangwei@ait.net.cn 
	* @date 2013-7-29 上午11:04:50 
	* @version V1.0   
	*/
	 @SuppressWarnings("unchecked")
	public int getEmpIdRetrieveListtCnt(HttpServletRequest request);
	
	
	public List viewStructureDept(HttpServletRequest request);
	
	
	
}
