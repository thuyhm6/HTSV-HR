package com.ait.edu.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.edu.dao.TrainReportDao;
import com.ait.edu.service.TrainReportSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Service
public class TrainReportSerImpl implements TrainReportSer{
	
	@Autowired
	private TrainReportDao trainReportDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List courseTrainList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return trainReportDao.courseTrainList(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List postGradeTrainList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return trainReportDao.postGradeTrainList(paramMap);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List deptTrainList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return trainReportDao.deptTrainList(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List yearTrainList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return trainReportDao.yearTrainList(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List monthTrainList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return trainReportDao.monthTrainList(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List formTrainList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return trainReportDao.formTrainList(paramMap);
	}
	

}
