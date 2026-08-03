package com.ait.is.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.is.dao.CompanyMaintainDAO;
import com.ait.is.service.CompanyMaintainSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
@Service
public class CompanyMaintainSerImpl implements CompanyMaintainSer{

	@Autowired
	private CompanyMaintainDAO maintainDAO;

	@Override
	public int addIsCompanyInfo(HttpServletRequest request) throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("CP_NAME", request.getParameter("CP_NAME"));
		map.put("CP_ADDR", request.getParameter("CP_ADDR"));
		map.put("COST", Integer.parseInt(request.getParameter("COST")));
		map.put("PERSON_TYPE", request.getParameter("PERSON_TYPE"));
		map.put("CREATE_BY", admin.getAdminID());
		return maintainDAO.addIsCompanyInfo(map);
	}

	@Override
	public int deleteIsCompanyInfo(HttpServletRequest request) throws Exception {
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> isCorpInfo = ObjectBindUtil
				.getRequestJsonData(jsonString);
		return maintainDAO.deleteIsCompanyInfo(isCorpInfo);
	}

	@Override
	public int updateIsCompanyInfo(HttpServletRequest request) throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("CP_NO", request.getParameter("CP_NO"));
		map.put("CP_NAME", request.getParameter("CP_NAME"));
		map.put("CP_ADDR", request.getParameter("CP_ADDR"));
		map.put("COST", request.getParameter("COST"));
		map.put("PERSON_TYPE", request.getParameter("PERSON_TYPE"));
		map.put("UPDATE_BY", admin.getAdminID());
		return maintainDAO.updateIsCompanyInfo(map);
	}

	@Override
	public List getIsCorpInfo(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("interCpnyID") != null
				&& !"".equals(paramMap.get("interCpnyID"))
				&& paramMap.get("interCpnyID").equals("TSTO")) {
			if (UiUtil.getPageNum(request) > 0) {
				retrunList = maintainDAO.getIsCorpInfo(paramMap,
						UiUtil.getPageNum(request),
						UiUtil.getNumPerPage(request));
			} else {
				retrunList = maintainDAO.getIsCorpInfo(paramMap);
			}
		}else{
			if (UiUtil.getPageNum(request) > 0) {
				retrunList = maintainDAO.getIsCorpInfoNotCH(paramMap,
						UiUtil.getPageNum(request),
						UiUtil.getNumPerPage(request));
			} else {
				retrunList = maintainDAO.getIsCorpInfoNotCH(paramMap);
			}
		}
		return retrunList;
	}

	@Override
	public Object getIsCorpInfoByNo(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CP_NO", request.getParameter("CP_NO"));
		List list = this.maintainDAO.getIsCorpInfo(paramMap);
		Object object = list.get(0);
		return object;
	}

	@Override
	public int getIsCorpCnt(HttpServletRequest request) throws Exception {
		int retrunInt = 0 ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (paramMap.get("interCpnyID") != null
				&& !"".equals(paramMap.get("interCpnyID"))
				&& paramMap.get("interCpnyID").equals("TSTO")) {
		    retrunInt = maintainDAO.getIsCorpCnt(paramMap) ;
		}else{
			retrunInt = maintainDAO.getIsCorpCntNotCH(paramMap) ;
		}
		return retrunInt ;
	}
}
