package com.ait.edu.dao.impl;

import java.sql.SQLException;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ait.edu.dao.TrainReportDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class TrainReportDaoImpl extends SqlMapClientSupport implements TrainReportDao{
	
	@Override
	public List courseTrainList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.trainreport.courseTrainList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List postGradeTrainList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.trainreport.postGradeTrainList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	@Override
	public List deptTrainList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.trainreport.deptTrainList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List yearTrainList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.trainreport.yearTrainList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List monthTrainList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.trainreport.monthTrainList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List formTrainList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.trainreport.formTrainList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}

}
