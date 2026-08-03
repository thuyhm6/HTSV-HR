package com.ait.edu.service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface TrainEducationSer {

	public int addSystemManagerInfo(HttpServletRequest request);
	
	public int addCourseManagerInfo(HttpServletRequest request);
	
	public int addPlanManagerInfo(HttpServletRequest request);
	
	public int addTeacherManagerInfo(HttpServletRequest request);
	
	public int addTrainOrganInfo(HttpServletRequest request);
	
	public int addTrainAgreementInfo(HttpServletRequest request);
	
	public int addTrainBasicInformationInfo(HttpServletRequest request);
	
	public int addCourseApply(HttpServletRequest request);
	
	public int addStudentKaoping(HttpServletRequest request);
	
	public int updateTeacherKaoping(HttpServletRequest request);
	
	public int updateEduTrainResult(HttpServletRequest request);
	
	public int deletePlanManager(HttpServletRequest request);
	
	public int deleteCourseSyllabus(HttpServletRequest request);
	
	public int deleteTeacherManager(HttpServletRequest request);
	
	public int deleteSystemMan(HttpServletRequest request);
	
	public int deleteTrainOrgan(HttpServletRequest request);
	
	public int deleteTrainAgreement(HttpServletRequest request);
	
	public int deleteTrainBasicInformation(HttpServletRequest request);
	
	public int deleteTrainCostManager(HttpServletRequest request);
	
	public int cancelApply(HttpServletRequest request);
	
	public int getcourseNum(HttpServletRequest request);
	
	public int updateSystemManager(HttpServletRequest request);
	
	public int updateCourseManager(HttpServletRequest request);
	
	public int updatePlanManager(HttpServletRequest request);
	
	public int updateTeacherManager(HttpServletRequest request);
	
	public int updateTrainOrgan(HttpServletRequest request);
	
	public int updateTrainAgree(HttpServletRequest request);
	
	public int updateTrainBasicInformationInfo(HttpServletRequest request);
	
	public int updateTrainCostManagerInfo(HttpServletRequest request);
	
	public int updateCourseMaker(HttpServletRequest request);
	
	public int updateCourseConfirm(HttpServletRequest request);
	
	public int updateStudentEvaluateInfo(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List systemManager(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List courseManager(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List syllabusInfo(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List planManager(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List planManager_basic(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List teacherManager(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List trainOrgan(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List trainAgreement(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List trainBasicInformation(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List trainCostManager(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List courseApply(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List courseMaker(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List courseConfirm(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List makerSituation(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List queryEmployee(HttpServletRequest request,String empid);
	
	@SuppressWarnings("rawtypes")
	public List queryDefaultMaker(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List trainArchives(HttpServletRequest request)throws Exception;
	
	public String queryIsnotEva(HttpServletRequest request,String basicno);
	
	@SuppressWarnings("rawtypes")
	public List studentChakan(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List teacherChakan(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List studentChakanSecond(HttpServletRequest request,LinkedHashMap paramMap);
	
	@SuppressWarnings("rawtypes")
	public List desEmployee(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List photoMissing(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List teacherSearch(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List queryCourseSyllabus(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List queryCourseSyllabus3(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List commonTeacher(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List queryFreeEmployee(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List queryfinalstudent(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List queryEvaAllemployee(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List queryEvaAllTeacher(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List queryEvaAllTSTOTeacher(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List planEmployee(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List otherPlanEmployee(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List finalstudent(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List querylocalname(HttpServletRequest request,String str);
	
	@SuppressWarnings("rawtypes")
	public List queryTeacher(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List queryPeixun(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List queryBasicNo(HttpServletRequest request,String str);
	
	@SuppressWarnings("rawtypes")
	public List queryStrBasicNo(HttpServletRequest request,String str);
	
	@SuppressWarnings("rawtypes")
	public List queryResultBasicNo(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List alreadyTrainResultList(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List alreadyTrainResultTSTOList(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List checkTrainResultTSTOInfoPer(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List trainResultInfoEveList(HttpServletRequest request);
	
	public Object systemManagerInfo(HttpServletRequest request);
	
	public Object courseManagerInfo(HttpServletRequest request);
	
	public Object planManagerInfo(HttpServletRequest request);
	
	public Object planManagerInfoDetail(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public List singleTeacherInformation(HttpServletRequest request);
	
	public Object teacherManagerInfo(HttpServletRequest request);
	
	public Object trainOrganInfo(HttpServletRequest request);
	
	public Object trainAgreementInfo(HttpServletRequest request);
	
	public Object trainBasicInformationInfo(HttpServletRequest request);
	
	public Object trainCostManagerInfo(HttpServletRequest request);
	
	public Object studentKaopingInfo(HttpServletRequest request);
	
	public Object teacherKaopingInfo(HttpServletRequest request);
	
	public Object trainResultInfo(HttpServletRequest request);
	
	public String queryDesEmployee(HttpServletRequest request);
	
	public String queryPlanNo(HttpServletRequest request);
	
	public String queryClob(HttpServletRequest request,String str);
	
	public String queryClobSecond(HttpServletRequest request,String content,String table,String term);
	
	public String queryCountnum(HttpServletRequest request,String basicno,String adminPersonid);
	
	public String alreadycountnum(HttpServletRequest request,String basicno);
	
	public String queryDesEmployeeName(HttpServletRequest request);
	
	public String queryTeacherName(HttpServletRequest request);
	
	public String queryTeacherNameEmpid(HttpServletRequest request);
	
	@SuppressWarnings("rawtypes")
	public String getTrainAgreeList(HttpServletRequest request,List aliasNameList, List list, List mapList, List mapNameList,String flag)throws SQLException;
	@SuppressWarnings("rawtypes")
	public String gettrainResultImportDemoLoad(HttpServletRequest request,List aliasNameList, List list, List mapList, List mapNameList,String flag)throws SQLException;
	@SuppressWarnings("rawtypes")
	public String getTeacherEvaluateInfo(HttpServletRequest request,List aliasNameList, List list, List mapList, List mapNameList,String flag)throws SQLException;
	
	@SuppressWarnings("rawtypes")
	public String getStudentEvaList(HttpServletRequest request,List aliasNameList, List list, List mapList, List mapNameList,String flag)throws SQLException;
	
	@SuppressWarnings("rawtypes")
	public String getPlanCourse(HttpServletRequest request,List aliasNameList, List list, List mapList, List mapNameList,String flag)throws SQLException;
	
	
	@SuppressWarnings("rawtypes")
	public List getDeptTree(HttpServletRequest request);
	
}
