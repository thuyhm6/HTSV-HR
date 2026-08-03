package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArVacationDao;
import com.ait.web.util.SqlMapClientSupport;


@Repository
public class ArVacationDaoImpl extends SqlMapClientSupport implements ArVacationDao {

	
	
	@SuppressWarnings("unchecked")
	public List getArVacationLiquidationList(Object object) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.vacation.getArVacationLiquidationList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
		
	}
	
	@SuppressWarnings("unchecked")
	public int getArVacationLiquidationCnt(Object object) {
		// TODO Auto-generated method stub
		int returnInt = 0;
		try {
			returnInt=NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.vacation.getArVacationLiquidationCnt", object)),
					Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
		
	}
	

	@SuppressWarnings("unchecked")
	public void saveVacationLiquidation(Object object) throws SQLException {
		// TODO Auto-generated method stub
		
			this.insert("ar.vacation.saveVacationLiquidation", object);
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List getArVacationUpdateMonthList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.vacation.getArVacationUpdateMonthList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getArVacationMonthExcel(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.vacation.getArVacationMonthExcel", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public String monthVacCalculate(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			this.insert("ar.vacation.monthVacCalculate", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("msg"));
		} catch (SQLException e) {
			returnString = e.getMessage();

			e.printStackTrace();
		}

		return returnString;
	}
	


	@SuppressWarnings("unchecked")
	public List getLeaveViewList(Object object) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.vacation.RetrieveVacationEmpList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
		
	}
	@SuppressWarnings("unchecked")
	public int getLeaveViewListCnt(Object object) {
		// TODO Auto-generated method stub
		
		int returnInt = 0;
		try {
			returnInt=NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.vacation.RetrieveVacationEmpListCnt", object)),
					Integer.class);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
		
	}
	@SuppressWarnings("unchecked")
	public int RetrieveAttStatus(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.vacation.RetrieveAttStatus", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public int monthVacationCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.vacation.monthVacationCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	public String monthVacCreate(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			this.insert("ar.vacation.saveVacationEmp", paramMap);
			returnString = "生成成功";
		} catch (SQLException e) {
			returnString = e.getMessage();

			e.printStackTrace();
		}

		return returnString;
	}

	
	@SuppressWarnings("unchecked")
	public int getArVacationUpdateYearCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.vacation.getArVacationUpdateYearCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public List getArVacationUpdateYearList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.vacation.getArVacationUpdateYearList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.vacation.getArVacationUpdateYearList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public void updateArVacationYear(Object object) throws Exception {
		this.update("ar.vacation.updateArVacationYear", object);
	}
	
	@SuppressWarnings("unchecked")
	public int yearVacationCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.vacation.yearVacationCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	@SuppressWarnings("unchecked")
	public String yearVacCalculate(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			this.insert("ar.vacation.yearVacCalculate", paramMap);
			returnString = "OK";
		} catch (SQLException e) {
			returnString = e.getMessage();
			e.printStackTrace();
		}
		return returnString;
	}
	
	@SuppressWarnings("unchecked")
	public String yearVacCreate(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			this.insert("ar.vacation.yearVacCreate", paramMap);
			returnString = "生成成功";
		} catch (SQLException e) {
			returnString = e.getMessage();

			e.printStackTrace();
		}

		return returnString;
	}
	
	@SuppressWarnings("unchecked")
	public int getArVacationNextCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.vacation.getArVacationNextCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public List getArVacationNextList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.vacation.getArVacationNextList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.vacation.getArVacationNextList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public String nextVacationMove(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			this.insert("ar.vacation.nextVacationMove", paramMap);
			returnString = "移年成功";
		} catch (SQLException e) {
			returnString = e.getMessage();

			e.printStackTrace();
		}

		return returnString;
	}
	
	@SuppressWarnings("unchecked")
	public String getTAWelfare(LinkedHashMap paramMap) {
		String returnString = "";
		try {
			this.insert("ar.vacation.getTAWelfare", paramMap);
			returnString = "福利年假生成成功";
		} catch (SQLException e) {
			returnString = e.getMessage();

			e.printStackTrace();
		}

		return returnString;
	}
	
}
