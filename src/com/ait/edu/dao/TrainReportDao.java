package com.ait.edu.dao;

import java.util.List;

public interface TrainReportDao {
	
	@SuppressWarnings("rawtypes")
	public List courseTrainList(Object object);
	
	@SuppressWarnings("rawtypes")
	public List postGradeTrainList(Object object);
	
	@SuppressWarnings("rawtypes")
	public List deptTrainList(Object object);
	
	@SuppressWarnings("rawtypes")
	public List yearTrainList(Object object);
	
	@SuppressWarnings("rawtypes")
	public List monthTrainList(Object object);
	
	@SuppressWarnings("rawtypes")
	public List formTrainList(Object object);

}
