package com.ait.edu.dao;

import java.util.List;
import java.util.Map;


public interface TrainEducationDao {
	
	public void addSystemManagerInfo(Object object) throws Exception;
	
	public void addCourseManagerInfo(Object object) throws Exception;
	
	public void addPlanManagerInfo(Object object) throws Exception;
	
	public void addTeacherManagerInfo(Object object) throws Exception;
	
	public void addTrainOrganInfo(Object object) throws Exception;
	
	public void addTrainAgreementInfo(Object object) throws Exception;
	
	public void addTrainBasicInformationInfo(Object object) throws Exception;
	
	public void finalEduFinalStudent(Object object) throws Exception;
	
	public void addCourseApply(Object object) throws Exception;
	
	public void addEduTrainMaker(Object object) throws Exception;
	
	public void insertEduFreeEmployee(Object object) throws Exception;
	
	public void updateEduFreeEmployee(Object object) throws Exception;
	
	public void insertEduTeacherCheck(Object object) throws Exception;
	
	public void insertEduCostManager(Object object) throws Exception;
	
	public void insertEduTrainResult(Object object) throws Exception;
	
	public void addStudentKaoping(Object object) throws Exception;
	
	public void addTeacherInformation(Object object) throws Exception;
	
	public void deletePlanManager(Object object) throws Exception;
	
	public void deleteEduTrainSyllabus(Object object) throws Exception;
	
	public void deleteCourseSyllabus(Object object) throws Exception;
	
	public void deleteTeacherManager(Object object) throws Exception;
	
	public void deleteSystemMan(Object object) throws Exception;
	
	public void deleteTrainOrgan(Object object) throws Exception;
	
	public void deleteTrainAgreement(Object object) throws Exception;
	
	public void deleteTrainBasicInformation(Object object) throws Exception;
	
	public void deleteEduFreeEmployee(Object object) throws Exception;
	
	public void deleteEduFreeEmployee_empid(Object object) throws Exception;
	
	public void deleteEduFreeEmployee_empid_more(Object object) throws Exception;
	
	public void deleteTrainCostManager(Object object) throws Exception;
	
	public void deleteTrainCostManager_basicno(Object object) throws Exception;
	
	public void deleteEduFinalStudent_basicno(Object object) throws Exception;
	
	public void deleteEduCheckTeachear_basicno(Object object) throws Exception;
	
	public void deleteEduStudentApply(Object object) throws Exception;
	
	public void cancelApply(Object object) throws Exception;
	
	public void deleteEduTrainMaker(Object object) throws Exception;
	
	public int getcourseNum(Object object) throws Exception;
	
	public void deleteEduStudentCheck(Object object) throws Exception;
	
	public void deleteEduTeacherCheck(Object object) throws Exception;
	
	public void deleteEduTrainResult(Object object) throws Exception;
	
	public void deleteEduTrainResult_stuempid(Object object) throws Exception;
	
	public void deleteEduTrainResult_stuempid_more(Object object) throws Exception;
	
	public void deleteEduTeacherCheck_stuempid(Object object) throws Exception;
	
	public void deleteEduTeacherCheck_stuempid_more(Object object) throws Exception;
	
	public void deleteFreeEmployee(Object object) throws Exception;
	
	public void deleteSingleEduStudentCheck(Object object) throws Exception;
	
	public void updateSystemManager(Object object) throws Exception;
	
	public void updateStudentKaoping(Object object) throws Exception;
	
	public void updateTeacherKaoping(Object object) throws Exception;
	
	public void updateEduTrainResult(Object object) throws Exception;
	
	public void updateCourseManager(Object object) throws Exception;
	
	public void updatePlanManager_course(Object object) throws Exception;
	
	public void updateBasicInformation_course(Object object) throws Exception;
	
	public void updatePlanManager(Object object) throws Exception;
	
	public void updateBasicInformation(Object object) throws Exception;
	
	public void updateTeacherManager(Object object) throws Exception;
	
	public void updateTrainOrgan(Object object) throws Exception;
	
	public void updateTrainAgree(Object object) throws Exception;
	
	public void updateAgreeid(Object object) throws Exception;
	
	public void updateTrainBasicInformationInfo(Object object) throws Exception;
	
	public void updateTrainCostManagerInfo(Object object) throws Exception;
	
	public void updateCourseMaker(Object object) throws Exception;
	
	public void updateCourseConfirm(Object object) throws Exception;
	
	public void updateStudentEvaluateInfo(Object object) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List systemManager(Object object);
	
	@SuppressWarnings("rawtypes")
	public List planManager(Object object);
	
	@SuppressWarnings("rawtypes")
	public List planManager_basic(Object object);
	
	@SuppressWarnings("rawtypes")
	public List teacherManager(Object object);
	
	@SuppressWarnings("rawtypes")
	public List trainAgreement(Object object);
	
	@SuppressWarnings("rawtypes")
	public List getAllTeacherBaseonBasicNo(Object object);
	
	@SuppressWarnings("rawtypes")
	public List trainBasicInformation(Object object);
	
	@SuppressWarnings("rawtypes")
	public List trainCostManager(Object object);
	
	@SuppressWarnings("rawtypes")
	public List courseApply(Object object);
	
	@SuppressWarnings("rawtypes")
	public List courseMaker(Object object);
	
	@SuppressWarnings("rawtypes")
	public List courseConfirm(Object object);
	
	@SuppressWarnings("rawtypes")
	public List makerSituation(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryEmployee(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryDefaultMaker(Object object);
	
	@SuppressWarnings("rawtypes")
	public List trainArchives(Object object);
	
	@SuppressWarnings("rawtypes")
	public List trainArchiveshistory(Object object);
	
	public String queryIsnotEva(Object object);
	
	public String getBasicNoBasePlanNo(Object object);
	
	@SuppressWarnings("rawtypes")
	public List studentChakan(Object object);
	
	@SuppressWarnings("rawtypes")
	public List teacherChakan(Object object);
	
	@SuppressWarnings("rawtypes")
	public List trainOrgan(Object object);
	
	@SuppressWarnings("rawtypes")
	public List desEmployee(Object object);
	
	@SuppressWarnings("rawtypes")
	public List photoMissing(Object object);
	
	@SuppressWarnings("rawtypes")
	public List teacherSearch(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryCourseSyllabus(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryCourseSyllabus3(Object object);
	
	@SuppressWarnings("rawtypes")
	public List commonTeacher(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryFreeEmployee(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryfinalstudent(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryApply(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryEvaAllemployee(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryEvaAllTeacher(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryEvaAllTSTOTeacher(Object object);
	
	@SuppressWarnings("rawtypes")
	public List planEmployee(Object object);
	
	@SuppressWarnings("rawtypes")
	public List otherPlanEmployee(Object object);
	
	@SuppressWarnings("rawtypes")
	public List finalstudent(Object object);
	
	@SuppressWarnings("rawtypes")
	public List querylocalname(Object object);
	
	@SuppressWarnings("rawtypes")
	public List courseManager(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryTeacher(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryPeixun(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryBasicNo(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryStrBasicNo(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryResultBasicNo(Object object);
	
	@SuppressWarnings("rawtypes")
	public List alreadyTrainResultList(Object object);
	
	@SuppressWarnings("rawtypes")
	public List alreadyTrainResultTSTOList(Object object);
	
	@SuppressWarnings("rawtypes")
	public List checkTrainResultTSTOInfoPer(Object object);
	
	@SuppressWarnings("rawtypes")
	public List trainResultInfoEveList(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryAllBasicInformation(Object object);
	
	@SuppressWarnings("rawtypes")
	public List queryEmptyAgreeid(Object object);
	
	public Object systemManagerInfo(Object object);
	
	public Object courseManagerInfo(Object object);
	
	public Object planManagerInfo(Object object);
	
	public Object planManagerInfoDetail(Object object);
	
	@SuppressWarnings("rawtypes")
	public List syllabusInfo(Object object);
	
	@SuppressWarnings("rawtypes")
	public List singleTeacherInformation(Object object);
	
	public Object teacherManagerInfo(Object object);
	
	public Object trainOrganInfo(Object object);
	
	public Object trainAgreementInfo(Object object);
	
	public Object trainBasicInformationInfo(Object object);
	
	public Object trainCostManagerInfo(Object object);
	
	public Object studentKaopingInfo(Object object);
	
	public Object teacherKaopingInfo(Object object);
	
	public Object trainResultInfo(Object object);
	
	public Object trainResultTSTOInfo(Object object);
	
	public String queryDesEmployee(Object object);
	
	public String queryPlanNo(Object object);
	
	public String queryClob(Object object);
	
	public String queryClobSecond(Object object);
	
	public String queryCountnum(Object object);
	
	public String alreadycountnum(Object object);
	
	public String queryDesEmployeeName(Object object);
	
	public String queryTeacherName(Object object);
	
	public String queryComTeacherName(Object object);
	
	public String queryTeacherNameEmpid(Object object);
	
	public String queryComTeacherEmpid(Object object);
	
	public String queryMaxno(Object object);
	
	public String queryBasicno(Object object);
	
	public void changePlanManagerActivity(Object object);
	
	public void changePlanManagerActivityDel(Object object);
	
	public String queryApplyno(Object object);
	
	public String queryAgreeid(Object object);
	
	public String queryMaxcourseNo(Object object);
	
	public String queryMaxPeriodtime(Object object);
	
	public String queryTeacherNo(Object object);
	
	public String querySheWaiEmpid(Object object);
	
	public String queryMaxAgreeId(Object object);
	
	@SuppressWarnings("rawtypes")
	public List getDeptTree(Map paramMap);
	
}
