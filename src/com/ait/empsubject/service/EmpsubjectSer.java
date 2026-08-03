package com.ait.empsubject.service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

/**
 * Copyright:    LG CNS
 * Company:      LG CNS
 * @fileName:    EmpsubjectSer.java
 * @Description: implement Class EmpsubjectSerImp.java
 * @Create date: 2014.06.25
 * @Create by:   L.H.H
 * @version 1.0
 */
public interface EmpsubjectSer {
	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public List getSubjectGroupList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getGroupList(HttpServletRequest request);

	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public int getSubjectGroupListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public Object getSubjectGroupInfo(HttpServletRequest request) ;

	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public int updateSubjectGroup(HttpServletRequest request);

	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public int addSubjectGroup(HttpServletRequest request);
	
	
	// * @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public List getSubjectList(HttpServletRequest request);

	// * @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public int getSubjectListCnt(HttpServletRequest request) ;

	// * @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public Object getSubjectInfo(HttpServletRequest request) ;

	// * @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public int updateSubject(HttpServletRequest request);

	// * @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public int addSubject(HttpServletRequest request);
		
	
	// * @Create date: 2014.07.01
	@SuppressWarnings("unchecked")
	public List getGradeStatisticsList(HttpServletRequest request);

	// * @Create date: 2014.07.01
	@SuppressWarnings("unchecked")
	public int getGradeStatisticsListCnt(HttpServletRequest request) ;
	
	// * @Create date: 2014.07.02
	@SuppressWarnings("unchecked")
	public List getGradeStatisticsListExcel(HttpServletRequest request);

	// * @Create date: 2014.07.04
	@SuppressWarnings("unchecked")
	public List getGradeWeekList(HttpServletRequest request) throws Exception;

	// * @Create date: 2014.07.04
	@SuppressWarnings("unchecked")
	public List getGradeWeekListExcel(HttpServletRequest request) throws Exception;

	// * @Create date: 2014.07.08
	@SuppressWarnings("unchecked")
	public int getGradeWeekListCnt(HttpServletRequest request) throws Exception;
	
	// * @Create date: 2014.07.08
	@SuppressWarnings("unchecked")
	public List getPromotoGradeList(HttpServletRequest request) throws Exception;

	//教育实绩
	// * @Create date: 2014.07.08
	@SuppressWarnings("unchecked")
	public List getPromotoGradeListExcel(HttpServletRequest request) throws Exception;

	// * @Create date: 2014.07.08
	@SuppressWarnings("unchecked")
	public int getPromotoGradeListCnt(HttpServletRequest request) throws Exception;
	
	// * @Create date: 2014.07.10	
	@SuppressWarnings("unchecked")
	public int deletePromotoGrade(HttpServletRequest request) ;
	
	//教育实绩导入结果
	// * @Create date: 2014.07.10
	@SuppressWarnings("unchecked")
	public List getPromotoGradeResultList(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getPromotoGradeResultListCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getPromotoGradeErrCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public String importPromotoGradeRAWFromExcel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap);

	@SuppressWarnings("unchecked")
	public List getDeptAreaList(HttpServletRequest request) ;
	
	/*Subject Group code list*/
	// * @Create date: 2014.08.18
	@SuppressWarnings("unchecked")
	public List getSubjectGroupCodeList(Map paramMap);

}
