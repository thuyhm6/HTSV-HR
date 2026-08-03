package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.ait.pa.dao.PaWorkerSalaryTableDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaWorkerSalaryTableDaoImpl extends SqlMapClientSupport implements PaWorkerSalaryTableDao {

	@SuppressWarnings("unchecked")
	public List paWorkerSalaryTableList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.workManagement.paWorkerSalaryTableList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	
	public int addPaWorkerSalaryTable(Object obj) {
		try {
			this.insert("pa.workManagement.addPaWorkerSalaryTable", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updatePaWorkerSalaryTableAll(Object obj) {
		try {
			this.update("pa.workManagement.updatePaWorkerSalaryTableAll", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	public int updatePaWorkerSalaryTable(Object obj) {
		try {
			this.insert("pa.workManagement.updatePaWorkerSalaryTable", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
