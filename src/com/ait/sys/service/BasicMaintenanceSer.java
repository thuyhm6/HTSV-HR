package com.ait.sys.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName BasicMaintenanceSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:23:27
 * @version 5.0
 *
 */
public interface BasicMaintenanceSer {
	
	@SuppressWarnings("unchecked")
	public List getParentCodeList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCodeWithParam(HttpServletRequest request) ;
	
	public int addCodeInfo(HttpServletRequest request) ;
	
	public int updateCodeInfo(HttpServletRequest request) ;
	
	public int deleteCodeInfo(HttpServletRequest request) ;
	
	public int getCodeListByParentCodeCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public Map getCodeByCodeNo(HttpServletRequest request)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public List getCodeTreeForAll(HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCodeTreeForAll(HttpServletRequest request,Map temp)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCodeTreeByParentCode(HttpServletRequest request)throws Exception;
	
	public int saveCodeParam(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCodePamersList(HttpServletRequest request)throws Exception;
	
	public int getCodePamersListCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public Map getCodePamasByParamNo(HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCodeTreeForEditCodePamas(HttpServletRequest request,Map temp)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getcodeTreeForEdit(HttpServletRequest request,Map temp)throws Exception;
	
	public int editCodeParam(HttpServletRequest request)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getParamCodeListByCpnyID(HttpServletRequest request,ModelMap modelMap);
	
	public List getParamCodeCombinListByCpnyID(Map temp);
}
