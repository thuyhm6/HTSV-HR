package com.ait.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface TrainReportSer {
	
	@SuppressWarnings("rawtypes")
	public List courseTrainList(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List postGradeTrainList(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List deptTrainList(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List yearTrainList(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List monthTrainList(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List formTrainList(HttpServletRequest request);

}
