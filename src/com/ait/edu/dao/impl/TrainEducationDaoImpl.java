package com.ait.edu.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.edu.dao.TrainEducationDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;



@Repository
public class TrainEducationDaoImpl extends SqlMapClientSupport implements TrainEducationDao {

	@Override
	public void addSystemManagerInfo(Object object) throws Exception {
		
		this.update("edu.traineducation.addSystemManagerInfo", object);
	}
	
	@Override
	public void addCourseManagerInfo(Object object) throws Exception {
		
		this.update("edu.traineducation.addCourseManagerInfo", object);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addPlanManagerInfo(Object object) throws Exception {
		this.insert("edu.traineducation.addPlanManagerInfo", object);
		Map obj=(Map)object;
		String seq=StringUtil.checkNull(obj.get("PLAN_NO"));
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", seq);
					fileMap.put("APPLY_TYPE", "eduPlanManager");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
	}
	
	@Override
	public void addTeacherManagerInfo(Object object) throws Exception {
		this.insert("edu.traineducation.addTeacherManagerInfo", object);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addTrainOrganInfo(Object object) throws Exception {
		Map obj=(Map)object;
		String seq=String.valueOf(this.insert("edu.traineducation.addTrainOrganInfo", object));
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", seq);
					fileMap.put("APPLY_TYPE", "eduTrainOrgan");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addTrainAgreementInfo(Object object) throws Exception {
		Map obj=(Map)object;
		String seq=String.valueOf(this.insert("edu.traineducation.addTrainAgreementInfo", object));
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", seq);
					fileMap.put("APPLY_TYPE", "eduTrainAgreement");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
	}
	
	@Override
	public void addTeacherInformation(Object object) throws Exception {
		this.insert("edu.traineducation.addTeacherInformation", object);
	}
	
	@Override
	public void addTrainBasicInformationInfo(Object object) throws Exception {
		this.insert("edu.traineducation.addTrainBasicInformationInfo", object);
	}
	
	@Override
	public void finalEduFinalStudent(Object object) throws Exception {
		this.insert("edu.traineducation.finalEduFinalStudent", object);
	}
	
	@Override
	public void addCourseApply(Object object) throws Exception {
		this.insert("edu.traineducation.addCourseApply", object);
	}
	
	@Override
	public void addEduTrainMaker(Object object) throws Exception {
		this.insert("edu.traineducation.addEduTrainMaker", object);
	}
	
	@Override
	public void insertEduFreeEmployee(Object object) throws Exception {
		this.insert("edu.traineducation.insertEduFreeEmployee", object);
	}
	
	@Override
	public void updateEduFreeEmployee(Object object) throws Exception {
		this.insert("edu.traineducation.updateEduFreeEmployee", object);
	}
	
	@Override
	public void insertEduTeacherCheck(Object object) throws Exception {
		this.insert("edu.traineducation.insertEduTeacherCheck", object);
	}
	
	@Override
	public void insertEduCostManager(Object object) throws Exception {
		this.insert("edu.traineducation.insertEduCostManager", object);
	}
	
	@Override
	public void insertEduTrainResult(Object object) throws Exception {
		this.insert("edu.traineducation.insertEduTrainResult", object);
	}
	
	@Override
	public void addStudentKaoping(Object object) throws Exception {
		this.insert("edu.traineducation.addStudentKaoping", object);
	}
	
	@Override
	public void deletePlanManager(Object object) throws Exception {
		
		this.update("edu.traineducation.deletePlanManager", object);
	}
	
	@Override
	public void deleteEduTrainSyllabus(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduTrainSyllabus", object);
	}
	
	@Override
	public void deleteCourseSyllabus(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteCourseSyllabus", object);
	}
	
	@Override
	public void deleteTeacherManager(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteTeacherManager", object);
	}
	@Override
	public void deleteSystemMan(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteSystemMan", object);
	}
	
	@Override
	public void deleteTrainOrgan(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteTrainOrgan", object);
	}
	
	@Override
	public void deleteTrainAgreement(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteTrainAgreement", object);
	}
	
	@Override
	public void deleteTrainBasicInformation(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteTrainBasicInformation", object);
	}
	
	@Override
	public void deleteEduFreeEmployee(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduFreeEmployee", object);
	}
	
	@Override
	public void deleteEduFreeEmployee_empid(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduFreeEmployee_empid", object);
	}
	@Override
	public void deleteEduFreeEmployee_empid_more(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduFreeEmployee_empid_more", object);
	}
	
	@Override
	public void deleteTrainCostManager(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteTrainCostManager", object);
	}
	
	@Override
	public void deleteTrainCostManager_basicno(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteTrainCostManager_basicno", object);
	}
	
	@Override
	public void deleteEduFinalStudent_basicno(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduFinalStudent_basicno", object);
	}
	@Override
	public void deleteEduCheckTeachear_basicno(Object object) throws Exception {
		this.update("edu.traineducation.deleteEduCheckTeachear_basicno", object);
	}
	
	@Override
	public void deleteEduStudentApply(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduStudentApply", object);
	}
	
	@Override
	public void cancelApply(Object object) throws Exception {
		
		this.update("edu.traineducation.cancelApply", object);
	}
	
	@Override
	public void deleteEduTrainMaker(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduTrainMaker", object);
	}
	@Override
	public int getcourseNum(Object object) throws Exception {
         int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("edu.traineducation.getcourseNum", object)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@Override
	public void deleteEduStudentCheck(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduStudentCheck", object);
	}
	
	@Override
	public void deleteEduTeacherCheck(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduTeacherCheck", object);
	}
	
	@Override
	public void deleteEduTrainResult(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduTrainResult", object);
	}
	
	@Override
	public void deleteEduTrainResult_stuempid(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduTrainResult_stuempid", object);
	}
	@Override
	public void deleteEduTrainResult_stuempid_more(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduTrainResult_stuempid_more", object);
	}
	
	@Override
	public void deleteEduTeacherCheck_stuempid(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduTeacherCheck_stuempid", object);
	}
	@Override
	public void deleteEduTeacherCheck_stuempid_more(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteEduTeacherCheck_stuempid_more", object);
	}
	
	@Override
	public void deleteFreeEmployee(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteFreeEmployee", object);
	}
	
	@Override
	public void deleteSingleEduStudentCheck(Object object) throws Exception {
		
		this.update("edu.traineducation.deleteSingleEduStudentCheck", object);
	}
	
	@Override
	public void updateSystemManager(Object object) throws Exception {
		
		this.update("edu.traineducation.updateSystemManager", object);
	}
	
	@Override
	public void updateStudentKaoping(Object object) throws Exception {
		
		this.update("edu.traineducation.updateStudentKaoping", object);
	}
	
	@Override
	public void updateTeacherKaoping(Object object) throws Exception {
		
		this.update("edu.traineducation.updateTeacherKaoping", object);
	}
	
	@Override
	public void updateEduTrainResult(Object object) throws Exception {
		
		this.update("edu.traineducation.updateEduTrainResult", object);
	}
	
	@Override
	public void updateCourseManager(Object object) throws Exception {
		
		this.update("edu.traineducation.updateCourseManager", object);
	}
	
	@Override
	public void updatePlanManager_course(Object object) throws Exception {
		
		this.update("edu.traineducation.updatePlanManager_course", object);
	}
	
	@Override
	public void updateBasicInformation_course(Object object) throws Exception {
		
		this.update("edu.traineducation.updateBasicInformation_course", object);
	}
	
	@Override
	public void updatePlanManager(Object object) throws Exception {
		
		this.update("edu.traineducation.updatePlanManager", object);
	}
	
	@Override
	public void updateBasicInformation(Object object) throws Exception {
		
		this.update("edu.traineducation.updateBasicInformation", object);
	}
	
	@Override
	public void updateTeacherManager(Object object) throws Exception {
		
		this.update("edu.traineducation.updateTeacherManager", object);
	}
	
	@Override
	public void updateTrainOrgan(Object object) throws Exception {
		
		this.update("edu.traineducation.updateTrainOrgan", object);
		Map obj=(Map)object;
		String seq=StringUtil.checkNull(obj.get("ORGAN_NO"));
		LinkedHashMap delMap = new LinkedHashMap();
		delMap.put("APPLY_NO", seq);
		delMap.put("APPLY_TYPE", "eduTrainOrgan");
		this.insert("ess.infoApplyLeave.deleteEssFile",delMap);
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", seq);
					fileMap.put("APPLY_TYPE", "eduTrainOrgan");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
	}
	
	@Override
	public void updateTrainAgree(Object object) throws Exception {
		
		this.update("edu.traineducation.updateTrainAgree", object);
	}
	
	@Override
	public void updateAgreeid(Object object) throws Exception {
		
		this.update("edu.traineducation.updateAgreeid", object);
	}
	
	@Override
	public void updateTrainBasicInformationInfo(Object object) throws Exception {
		
		this.update("edu.traineducation.updateTrainBasicInformationInfo", object);
	}
	
	@Override
	public void updateTrainCostManagerInfo(Object object) throws Exception {
		
		this.update("edu.traineducation.updateTrainCostManagerInfo", object);
		Map obj=(Map)object;
		String seq=StringUtil.checkNull(obj.get("COST_NO"));
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileName = StringUtil.checkNull(obj.get("fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					LinkedHashMap fileMap = new LinkedHashMap();
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("adminID") + "/" + fileUrl[j]);
					fileMap.put("APPLY_NO", seq);
					fileMap.put("APPLY_TYPE", "eduCostManager");
					fileMap.put("CREATED_BY", obj.get("adminID"));
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
	}
	
	@Override
	public void updateCourseMaker(Object object) throws Exception {
		
		this.update("edu.traineducation.updateCourseMaker", object);
	}
	
	@Override
	public void updateCourseConfirm(Object object) throws Exception {
		
		this.update("edu.traineducation.updateCourseConfirm", object);
	}
	
	@Override
	public void updateStudentEvaluateInfo(Object object) throws Exception {
		
		this.update("edu.traineducation.updateStudentEvaluateInfo", object);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List systemManager(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.systemManager",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List planManager(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.planManager",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List planManager_basic(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.planManager_basic",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List teacherManager(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.teacherManager",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List trainAgreement(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.trainAgreement",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getAllTeacherBaseonBasicNo(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.getAllTeacherBaseonBasicNo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List trainBasicInformation(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.trainBasicInformation",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List trainCostManager(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.trainCostManager",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List courseApply(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.courseApply",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List courseMaker(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.courseMaker",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List courseConfirm(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.courseConfirm",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List makerSituation(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.makerSituation",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryEmployee(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryEmployee",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryDefaultMaker(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryDefaultMaker",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List trainArchives(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.trainArchives",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List trainArchiveshistory(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.trainArchiveshistory",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List studentChakan(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.studentChakan",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List teacherChakan(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.teacherChakan",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List trainOrgan(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.trainOrgan",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List desEmployee(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.desEmployee",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List photoMissing(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.photoMissing",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List teacherSearch(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.teacherSearch",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryCourseSyllabus(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryCourseSyllabus",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List queryCourseSyllabus3(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryCourseSyllabus3",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List commonTeacher(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.commonTeacher",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryFreeEmployee(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryFreeEmployee",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryfinalstudent(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryfinalstudent",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryApply(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryApply",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryEvaAllemployee(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryEvaAllemployee",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryEvaAllTeacher(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryEvaAllTeacher",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List queryEvaAllTSTOTeacher(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryEvaAllTSTOTeacher",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List planEmployee(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.planEmployee",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List otherPlanEmployee(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.otherPlanEmployee",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List finalstudent(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.finalstudent",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List querylocalname(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.querylocalname",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List courseManager(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.courseManager",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryTeacher(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryTeacher",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryPeixun(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryPeixun",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryBasicNo(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryBasicNo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryStrBasicNo(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryStrBasicNo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryResultBasicNo(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryResultBasicNo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List alreadyTrainResultList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.alreadyTrainResultList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List alreadyTrainResultTSTOList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.alreadyTrainResultTSTOList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List checkTrainResultTSTOInfoPer(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.checkTrainResultTSTOInfoPer",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List trainResultInfoEveList(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.trainResultInfoEveList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryAllBasicInformation(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryAllBasicInformation",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryEmptyAgreeid(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.queryEmptyAgreeid",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public Object systemManagerInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.systemManagerInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object courseManagerInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.courseManagerInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object planManagerInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.planManagerInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object planManagerInfoDetail(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.planManagerInfoDetail", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List syllabusInfo(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.syllabusInfo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public List singleTeacherInformation(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("edu.traineducation.singleTeacherInformation",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public Object teacherManagerInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.teacherManagerInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object trainOrganInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.trainOrganInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object trainAgreementInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.trainAgreementInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object trainBasicInformationInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.trainBasicInformationInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object trainCostManagerInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.trainCostManagerInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object studentKaopingInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.studentKaopingInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object teacherKaopingInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.teacherKaopingInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public Object trainResultInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.trainResultInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	@Override
	public Object trainResultTSTOInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"edu.traineducation.trainResultTSTOInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public String queryDesEmployee(Object object) {
		String object2 = null;
		try {
			object2 = (String) this.queryForObject(
					"edu.traineducation.queryDesEmployee", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public String queryPlanNo(Object object) {
		String object2 = null;
		try {
			object2 = (String) this.queryForObject(
					"edu.traineducation.queryPlanNo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public String queryClob(Object object) {
		String object2 = null;
		try {
			object2 = (String) this.queryForObject(
					"edu.traineducation.queryClob", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	public String queryClobSecond(Object object) {
		String object2 = null;
		try {
			object2 = (String) this.queryForObject(
					"edu.traineducation.queryClobSecond", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	public String queryCountnum(Object object) {
		String object2 = null;
		try {
			object2 = (String) this.queryForObject(
					"edu.traineducation.queryCountnum", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	public String alreadycountnum(Object object) {
		String object2 = null;
		try {
			object2 = (String) this.queryForObject(
					"edu.traineducation.alreadycountnum", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public String queryDesEmployeeName(Object object) {
		String object2 = null;
		try {
			object2 = (String) this.queryForObject(
					"edu.traineducation.queryDesEmployeeName", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public String queryTeacherName(Object object) {
		String object2 = null;
		try {
			object2 = (String) this.queryForObject(
					"edu.traineducation.queryTeacherName", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public String queryComTeacherName(Object object) {
		String object2 = null;
		try {
			object2 = (String) this.queryForObject(
					"edu.traineducation.queryComTeacherName", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	
	@Override
	public String queryTeacherNameEmpid(Object object) {
		String object2 = null;
		try {
			object2 = (String) this.queryForObject(
					"edu.traineducation.queryTeacherNameEmpid", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public String queryComTeacherEmpid(Object object) {
		String object2 = null;
		try {
			object2 = (String) this.queryForObject(
					"edu.traineducation.queryComTeacherEmpid", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
	
	@Override
	public String queryMaxno(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"edu.traineducation.queryMaxno", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	
	@Override
	public String queryBasicno(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"edu.traineducation.queryBasicno", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public void changePlanManagerActivity(Object object) {
		try {
			this.update("edu.traineducation.changePlanManagerActivity", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void changePlanManagerActivityDel(Object object) {
		try {
			this.update("edu.traineducation.changePlanManagerActivityDel", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String queryApplyno(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"edu.traineducation.queryApplyno", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String queryAgreeid(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"edu.traineducation.queryAgreeid", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String queryMaxcourseNo(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"edu.traineducation.queryMaxcourseNo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String queryMaxPeriodtime(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"edu.traineducation.queryMaxPeriodtime", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String queryTeacherNo(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"edu.traineducation.queryTeacherNo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String querySheWaiEmpid(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"edu.traineducation.querySheWaiEmpid", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String queryMaxAgreeId(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"edu.traineducation.queryMaxAgreeId", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	public String queryIsnotEva(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"edu.traineducation.queryIsnotEva", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	@Override
	public String getBasicNoBasePlanNo(Object object) {
		String str= "";
		try {
			str = (String) this.queryForObject(
					"edu.traineducation.getBasicNoBasePlanNo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	
	@SuppressWarnings("rawtypes")
	public List getDeptTree(Map paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("edu.traineducation.getDeptTree", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
}
