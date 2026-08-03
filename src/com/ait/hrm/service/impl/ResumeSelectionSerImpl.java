package com.ait.hrm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.ResumeSelectionDao;
import com.ait.hrm.service.ResumeSelectionSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.UiUtil;

@Service
public class ResumeSelectionSerImpl implements ResumeSelectionSer {
	Logger logger = Logger.getLogger(ResumeSelectionSerImpl.class);
	@Autowired
	private ResumeSelectionDao resumeSelectionDao;
	@SuppressWarnings("unchecked")
	@Override
	public List getResumeSelectionInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String TYPE = request.getParameter("seach_TYPE") == null ? "0"
				: request.getParameter("seach_TYPE");
		paramMap.put("TYPE", TYPE);
		if (UiUtil.getPageNum(request) > 0) {
			 retrunList = resumeSelectionDao.getResumeSelectionInfoList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request, 20));
		} else {
			retrunList = resumeSelectionDao.getResumeSelectionInfoList(paramMap);
		}
		

		return retrunList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int getResumeSelectionCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String TYPE = request.getParameter("seach_TYPE") == null ? "0"
				: request.getParameter("seach_TYPE");
		paramMap.put("TYPE", TYPE);
		return resumeSelectionDao.getResumeSelectionCnt(paramMap);
	}
	@Override
	public int updateResumeInfoForUpdate(HttpServletRequest request) {
		String jsonString = request.getParameter("jsonData") ;
		List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;

		 

		return  resumeSelectionDao.updateResumeInfoForUpdate(dataList);

	}

}
