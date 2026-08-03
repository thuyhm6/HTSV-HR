package com.ait.empsubject.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.empsubject.dao.EmpsubjectDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:    LG CNS
 * Company:      LG CNS
 * @fileName:    EmpsubjectDaoImpl.java
 * @Create date: 2014.06.25
 * @Create by:   L.H.H
 * @version 1.0
 */
@Repository
public class EmpsubjectDaoImpl extends SqlMapClientSupport implements
		EmpsubjectDao {

	/**
	 * 课程组管理
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.26
	 */
	@Override
	public List getSubjectGroupList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("empsubject.searchSubjectGroupList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("empsubject.searchSubjectGroupList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 课程组管理
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.26
	 */
	@Override
	public List getSubjectGroupList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getSubjectGroupList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * 课程组select box
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.26
	 */
	@Override
	public List getGroupList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("empsubject.searchGroupList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("empsubject.searchGroupList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 课程组select box
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.26
	 */
	@Override
	public List getGroupList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getGroupList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 课程组管理count
	 * @param obj
	 * @return int
	 * @Create date: 2014.06.26
	 */
	@Override
	public int getSubjectGroupListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("empsubject.searchSubjectGroupListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public Object getSubjectGroupInfo(Object obj) {
		Object returnObj = new Object();
		List returnList = this.getSubjectGroupList(obj);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}

	// @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public void updateSubjectGroup(Object obj) throws Exception {
		this.update("empsubject.updateSubjectGroup", obj) ;
	}

	// @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public void addSubjectGroup(Object obj) throws Exception {
		this.update("empsubject.addSubjectGroup", obj) ;
	}
	
	
	/**
	 * 课程管理
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.30
	 */
	@Override
	public List getSubjectList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("empsubject.searchSubjectList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("empsubject.searchSubjectList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 课程管理
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.30
	 */
	@Override
	public List getSubjectList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getSubjectList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 课程管理count
	 * @param obj
	 * @return int
	 * @Create date: 2014.06.30
	 */
	@Override
	public int getSubjectListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("empsubject.searchSubjectListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 课程管理
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.30
	 */
	@Override
	public List getSubjectInfo(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("empsubject.searchSubject", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("empsubject.searchSubject", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	// @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public Object getSubjectInfo(Object obj) {
		Object returnObj = new Object();

		List returnList = new ArrayList() ;		
		returnList = this.getSubjectInfo(obj, -1, -1) ;
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}

	// @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public void updateSubject(Object obj) throws Exception {
		this.update("empsubject.updateSubject", obj) ;
	}

	// @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public void addSubject(Object obj) throws Exception {
		this.update("empsubject.addSubject", obj) ;
	}	
	
	
	/**
	 * 课程别实绩汇总
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.07.01
	 */
	@Override
	public List getGradeStatisticsList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("empsubject.searchGradeStatisticsList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("empsubject.searchGradeStatisticsList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 课程别实绩汇总
	 * @param obj
	 * @return List
	 * @Create date: 2014.07.01
	 */
	@Override
	public List getGradeStatisticsList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getGradeStatisticsList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 课程别实绩汇总count
	 * @param obj
	 * @return int
	 * @Create date: 2014.07.01
	 */
	@Override
	public int getGradeStatisticsListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("empsubject.searchGradeStatisticsListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}	
	
	/**
	 * 课程周别汇总
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.07.04
	 */
	@Override
	public List getGradeWeekList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("empsubject.searchGradeWeekList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("empsubject.searchGradeWeekList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 课程周别汇总
	 * @param obj
	 * @return List
	 * @Create date: 2014.07.04
	 */
	@Override
	public List getGradeWeekList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getGradeWeekList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 课程周别汇总count
	 * @param obj
	 * @return int
	 * @Create date: 2014.07.04
	 */
	@Override
	public int getGradeWeekListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("empsubject.searchGradeWeekListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 教育实绩
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.07.04
	 */
	@Override
	public List getPromotoGradeList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("empsubject.searchPromotoGradeList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("empsubject.searchPromotoGradeList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 教育实绩
	 * @param obj
	 * @return List
	 * @Create date: 2014.07.04
	 */
	@Override
	public List getPromotoGradeList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getPromotoGradeList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 教育实绩count
	 * @param obj
	 * @return int
	 * @Create date: 2014.07.04
	 */
	@Override
	public int getPromotoGradeListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("empsubject.searchPromotoGradeListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 删除教育实绩
	 * @param List
	 * @return 
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings("unchecked")
	public void deletePromotoGrade(Object object) throws Exception {
		
		this.deleteForList("empsubject.deletePromotoGrade", (List)object) ;
	}
	
	/**
	 * 教育实绩导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPromotoGradeResultList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("empsubject.getPromotoGradeResultList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("empsubject.getPromotoGradeResultList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}	
	/**
	 * 教育实绩导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.07.10
	 */
	@Override
	public List getPromotoGradeResultList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getPromotoGradeResultList(obj, -1, -1) ;
		
		return returnList ;
	}
	/**
	 * 教育实绩导入结果count
	 * @param obj
	 * @return List
	 * @Create date: 2014.07.10
	 */
	@Override
	public int getPromotoGradeResultListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("empsubject.getPromotoGradeResultListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 教育实绩导入结果error count
	 * @param obj
	 * @return List
	 * @Create date: 2014.07.10
	 */
	@Override
	public int getPromotoGradeErrCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("empsubject.getPromotoGradeErrCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 教育实绩导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importPromotoGradeRAWFromExcel(LinkedHashMap paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("empsubject.importPromotoGradeRAWFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}
	
	/**
	 * 大区选择框
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDeptAreaList(Object object) {
		List returnList = new ArrayList() ;
		try {
			
			returnList = this.queryForList("empsubject.getDeptAreaList", object) ;
			
		} catch (SQLException e) {	
			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/*subject Group code list*/
	@Override
	public List getSubjectGroupCodeList(Object object){
		List returnList = new ArrayList() ;		
		try {
			returnList = this.queryForList("empsubject.getSubjectGroupCodeList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return returnList ;
	};
}
