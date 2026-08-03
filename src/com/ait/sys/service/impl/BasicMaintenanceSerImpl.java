package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.service.BasicMaintenanceSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class BasicMaintenanceSerImpl implements BasicMaintenanceSer {
	
	Logger logger = Logger.getLogger(BasicMaintenanceSerImpl.class);
	
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;

	@SuppressWarnings("unchecked")
	public List getParentCodeList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return basicMaintenanceDao.getParentCodeList(paramMap) ;
	}

	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(HttpServletRequest request) {
		List returnList=null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			returnList=basicMaintenanceDao.getCodeListByParentCode(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 	 
		}else{
			returnList = basicMaintenanceDao.getCodeListByParentCode(paramMap) ;
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getCodeListByParentCodeWithParam(HttpServletRequest request) {
		List returnList=null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			returnList=basicMaintenanceDao.getCodeListByParentCodeWithParam(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 	 
		}else{
			returnList = basicMaintenanceDao.getCodeListByParentCodeWithParam(paramMap) ;
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public int addCodeInfo(HttpServletRequest request) {
		try {
			@SuppressWarnings("unused")
			HttpSession session = request.getSession() ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			LinkedHashMap<String, Object> appendMap = ObjectBindUtil.getRequestParamData(request);
			appendMap.put("CREATED_BY", admin.getPersonId()) ;
			this.basicMaintenanceDao.addCodeInfo(appendMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int updateCodeInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap<String, Object> appendMap =  ObjectBindUtil.getRequestParamData(request);
		appendMap.put("UPDATED_BY", admin.getPersonId()) ;
		try {
			this.basicMaintenanceDao.updateCodeInfo(appendMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteCodeInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.basicMaintenanceDao.deleteCodeInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getCodeListByParentCodeCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		int temp=0;
		 try {
			 temp=basicMaintenanceDao.getCodeListByParentCodeCnt(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getCodeByCodeNo(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CODE_NO", paramMap.get("NO"));
		Map temp=basicMaintenanceDao.getCodeByCodeNo(paramMap);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCodeTreeForAll(HttpServletRequest request) throws Exception {
		Map paramMap =ObjectBindUtil.getRequestParamData(request);
		paramMap.put("DEPTH_START", 2);
		return basicMaintenanceDao.getCodeTreeForAll(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCodeTreeForAll(HttpServletRequest request, Map temp)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		temp.putAll(paramMap);
		return basicMaintenanceDao.getCodeTreeForAll(temp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCodeTreeByParentCode(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return basicMaintenanceDao.getCodeTreeByParentCode(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int saveCodeParam(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.basicMaintenanceDao.saveCodeParam(paramMap,request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCodePamersList(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List returnList=null;
		if (UiUtil.getPageNum(request) > 0){
			returnList=basicMaintenanceDao.getCodePamersList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			returnList = basicMaintenanceDao.getCodePamersList(paramMap) ;
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getCodePamersListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		int temp=0;
		 try {
			 temp=basicMaintenanceDao.getCodePamersListCnt(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getCodePamasByParamNo(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		Map temp=basicMaintenanceDao.getCodePamasByParamNo(paramMap);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCodeTreeForEditCodePamas(HttpServletRequest request, Map temp)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		temp.putAll(paramMap);
		return basicMaintenanceDao.getCodeTreeForEditCodePamas(temp);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getcodeTreeForEdit(HttpServletRequest request, Map temp)
			throws Exception {
		return basicMaintenanceDao.getcodeTreeForEdit(temp);
	}
	
	/**
	 * 代码参数修改
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int editCodeParam(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataForCode(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin!=null?admin.getPersonId():null);
		paramMap.put("CREATED_BY", admin!=null?admin.getPersonId():null);
		try {
			this.basicMaintenanceDao.editCodeParam(paramMap,request);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 根据公司ID取得CODE列表(get ParamCodeList By CpnyID)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getParamCodeListByCpnyID(HttpServletRequest request,ModelMap modelMap){
		List retrunList = new ArrayList() ;
		Map paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String seachCpny = request.getParameter("seach_cpnyId");
		String seachParentNo = request.getParameter("seach_parentNo");
		paramMap.put("PARENT_CODE_NO",seachParentNo);
		paramMap.put("CPNY_ID", StringUtils.isEmpty(seachCpny) ? admin.getCpnyId() : seachCpny);
		paramMap.put("interLanguage", admin.getLanguage());
		modelMap.put("defaultCpny", admin.getCpnyId());
		retrunList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1);
		return retrunList ;
	}
	
	@Override
	public List getParamCodeCombinListByCpnyID(Map temp){
		
		return basicMaintenanceDao.getParamCodeCombinListByCpnyID(temp, -1, -1);
	}
}
