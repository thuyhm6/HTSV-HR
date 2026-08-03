package com.ait.empsubject.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright:    LG CNS
 * Company:      LG CNS
 * @fileName:    EmpsubjectDao.java
 * @Create date: 2014.06.25
 * @Create by:   L.H.H
 * @version 1.0
 */
public interface EmpsubjectDao {
	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public List getSubjectGroupList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public List getSubjectGroupList(Object object);

	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public List getGroupList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public List getGroupList(Object object);
	
	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public int getSubjectGroupListCnt(Object object);

	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public Object getSubjectGroupInfo(Object obj) ;

	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public void updateSubjectGroup(Object object) throws Exception;

	// * @Create date: 2014.06.26
	@SuppressWarnings("unchecked")
	public void addSubjectGroup(Object object) throws Exception;


	// * @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public List getSubjectList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public List getSubjectList(Object object);
	
	// * @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public int getSubjectListCnt(Object object);

	// * @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public List getSubjectInfo(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public Object getSubjectInfo(Object obj) ;

	// * @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public void updateSubject(Object object) throws Exception;

	// * @Create date: 2014.06.30
	@SuppressWarnings("unchecked")
	public void addSubject(Object object) throws Exception;


	// * @Create date: 2014.07.01
	@SuppressWarnings("unchecked")
	public List getGradeStatisticsList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.07.01
	@SuppressWarnings("unchecked")
	public List getGradeStatisticsList(Object object);
	
	// * @Create date: 2014.07.01
	@SuppressWarnings("unchecked")
	public int getGradeStatisticsListCnt(Object object);


	// * @Create date: 2014.07.04
	@SuppressWarnings("unchecked")
	public List getGradeWeekList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.07.04
	@SuppressWarnings("unchecked")
	public List getGradeWeekList(Object object);
	
	// * @Create date: 2014.07.04
	@SuppressWarnings("unchecked")
	public int getGradeWeekListCnt(Object object);


	// * @Create date: 2014.07.08
	@SuppressWarnings("unchecked")
	public List getPromotoGradeList(Object object, int currentPage, int pageSize);
	
	// * @Create date: 2014.07.08
	@SuppressWarnings("unchecked")
	public List getPromotoGradeList(Object object);
	
	// * @Create date: 2014.07.08
	@SuppressWarnings("unchecked")
	public int getPromotoGradeListCnt(Object object);

	// * @Create date: 2014.07.10
	@SuppressWarnings("unchecked")
	public void deletePromotoGrade(Object object) throws Exception;
	
	/* 教育实绩导入结果 */
	// * @Create date: 2014.07.10
	@SuppressWarnings("unchecked")
	public List getPromotoGradeResultList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getPromotoGradeResultList(Object object);
	@SuppressWarnings("unchecked")
	public int getPromotoGradeResultListCnt(Object obj);
	@SuppressWarnings("unchecked")
	public int getPromotoGradeErrCnt(Object obj);
	@SuppressWarnings("unchecked")
	public String importPromotoGradeRAWFromExcel(LinkedHashMap paramMap);
	
	@SuppressWarnings("unchecked")
	public List getDeptAreaList(Object object) ;

	/* subject group code list */
	// * @Create date: 2014.07.10
	@SuppressWarnings("unchecked")
	public List getSubjectGroupCodeList(Object object) ;
}
