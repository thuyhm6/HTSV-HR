package com.ait.edu.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.edu.dao.TrainEducationDao;
import com.ait.edu.service.TrainEducationSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.sun.org.apache.xpath.internal.operations.And;

@Service
public class TrainEducationSerImpl implements TrainEducationSer {
	@Autowired
	private TrainEducationDao eduTrainDao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int addSystemManagerInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		    String TRAIN_DIFF_CODE=StringUtil.checkNull(request.getParameter("TRAIN_DIFF_CODE"));
			paramMap.put("TRAIN_DIFF_CODE", TRAIN_DIFF_CODE);
			paramMap.put("TRAIN_TYPE_CODE", StringUtil.checkNull(request.getParameter("TRAIN_TYPE_CODE")));
			paramMap.put("REMARK", StringUtil.checkNull(request.getParameter("REMARK")));
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CREATED_IP", admin.getAdminIP());
			String maxno=eduTrainDao.queryMaxno(paramMap);
			String trainno="";
			if("14014481".equals(TRAIN_DIFF_CODE)){
				trainno="SVP";
			}else if("14014482".equals(TRAIN_DIFF_CODE)){
				trainno="SLP";
			}else if("14014483".equals(TRAIN_DIFF_CODE)){
				trainno="SEP";
			}else if("14014484".equals(TRAIN_DIFF_CODE)){
				trainno="SGP";
			}
			if("".equals(maxno)||maxno==null){
				trainno=trainno+"000001";
			}else{
				int count=Integer.parseInt(maxno.substring(4,maxno.length()))+1;
				if(count<10){
					trainno=trainno+"00000"+String.valueOf(count);
				}else if(10<=count&&count<100){
					trainno=trainno+"0000"+String.valueOf(count);
				}else if(100<=count&&count<1000){
					trainno=trainno+"000"+String.valueOf(count);
				}
				else if(1000<=count&&count<10000){
					trainno=trainno+"00"+String.valueOf(count);
				}
				else if(10000<=count&&count<100000){
					trainno=trainno+"0"+String.valueOf(count);
				}else if(100000<=count&&count<1000000){
					trainno=trainno+String.valueOf(count);
				}
			}
			paramMap.put("TRAIN_TYPE_NO", trainno);
            this.eduTrainDao.addSystemManagerInfo(paramMap);			
		} catch (Exception e) {
			e.printStackTrace();
			/*String errmessage=e.getMessage();
			if(errmessage.indexOf("UK_EDU_SYSTEM_MANAGER")>-1){
				return 2;
			}else{
				return 0;
			}*/
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int addCourseManagerInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("SYSMANA_NO", StringUtil.checkNull(request.getParameter("TRAIN_TYPE_CODE")));
			LinkedHashMap param = null;
			param=(LinkedHashMap) eduTrainDao.systemManagerInfo(paramMap);
			String trtyno=(String) param.get("TRAIN_TYPE_NO");
			paramMap.put("TRAIN_TYPE_CODE", param.get("TRAIN_TYPE_CODE"));
			paramMap.put("TRAIN_TYPE_NO", trtyno);
			paramMap.put("COURSE_NAME_CODE", StringUtil.checkNull(request.getParameter("COURSE_NAME_CODE")));
			paramMap.put("REMARK", StringUtil.checkNull(request.getParameter("REMARK")));
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CREATED_IP", admin.getAdminIP());
			String maxno=eduTrainDao.queryMaxcourseNo(paramMap);
			String courseno="";
			if("".equals(maxno)||maxno==null){
				courseno=trtyno+"-0001";
			}else{
				int count=Integer.parseInt(maxno.substring(maxno.length()-4,maxno.length()))+1;
				if(count<10){
					courseno=trtyno+"-000"+String.valueOf(count);
				}else if(10<=count&&count<100){
					courseno=trtyno+"-00"+String.valueOf(count);
				}else if(100<=count&&count<1000){
					courseno=trtyno+"-0"+String.valueOf(count);
				}else if(1000<=count&&count<10000){
					courseno=trtyno+"-"+String.valueOf(count);
			    }
			} 		
			paramMap.put("COURSE_NUMBER", courseno);
			this.eduTrainDao.addCourseManagerInfo(paramMap);	
		}catch (Exception e) {
			e.printStackTrace();
			String errmessage=e.getMessage();
			if(errmessage.indexOf("UK_EDU_COURSE_MANAGER")>-1){
				return 2;
			}else{
				return 0;
			}
			
			
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int addPlanManagerInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String planno=StringUtil.checkNull(request.getParameter("PLAN_NO"));
			paramMap.put("PLAN_NO", planno);
			String courseno=StringUtil.checkNull(request.getParameter("TRAIN_TYPE_CODE"));
			paramMap.put("COURSE_NO", courseno);
			LinkedHashMap param = null;
			param=(LinkedHashMap)eduTrainDao.courseManagerInfo(paramMap);
			String TRAIN_DIFF_CODE=(String) param.get("TRAIN_TYPE_NO");
			paramMap.put("TRAIN_DIFF_CODE", TRAIN_DIFF_CODE.substring(0, 4));
			paramMap.put("TRAIN_TYPE_CODE", param.get("TRAIN_TYPE_CODE"));
			paramMap.put("COURSE_NAME_CODE", param.get("COURSE_NAME_CODE"));
			paramMap.put("COURSE_NUMBER", param.get("COURSE_NUMBER"));
			paramMap.put("PLAN_STARTDATE", StringUtil.checkNull(request.getParameter("PLAN_STARTDATE")));
			paramMap.put("PLAN_ENDDATE", StringUtil.checkNull(request.getParameter("PLAN_ENDDATE")));
			paramMap.put("CLASS_HOUR", StringUtil.checkNull(request.getParameter("CLASS_HOUR")));
			paramMap.put("CLASS_UNIT", StringUtil.checkNull(request.getParameter("CLASS_UNIT")));
			paramMap.put("TRAIN_FORM_CODE", StringUtil.checkNull(request.getParameter("TRAIN_FORM_CODE")));
			paramMap.put("ISNOT_TEST", StringUtil.checkNull(request.getParameter("ISNOT_TEST")));
			paramMap.put("ISNOT_APPLY", StringUtil.checkNull(request.getParameter("ISNOT_APPLY")));
			String DES_DEPARTMENT = StringUtil.checkNull(request.getParameter("DES_DEPARTMENT"));
			String DES_EMPLOYEE_ZHIDING = StringUtil.checkNull(request.getParameter("empidEmployee"));
			String DES_EMPLOYEE = "";
			String DES_EMPLOYEE_NAME = "";
			LinkedHashMap zhidingMap = new LinkedHashMap();
			if (DES_DEPARTMENT != null && !"".equals(DES_DEPARTMENT)) {
			  String [] array=DES_DEPARTMENT.split(",");
				String str="";
				for(int i=0;i<array.length;i++){
					str=str+"'"+array[i]+"',";
				}
				str=str.substring(0,str.length()-1);
				zhidingMap.put("DEPTNO", str);
			}
		    zhidingMap.put("CPNY_ID", admin.getCpnyId());
			List elseList = eduTrainDao.desEmployee(zhidingMap);
			for (Iterator iterator = elseList.iterator(); iterator.hasNext();) {
					LinkedHashMap person = (LinkedHashMap) iterator.next();
					String empid = person.get("EMPID").toString();
					String local_name = person.get("LOCAL_NAME").toString();
					DES_EMPLOYEE=DES_EMPLOYEE+empid+",";
					DES_EMPLOYEE_NAME=DES_EMPLOYEE_NAME+local_name+",";
			}
			DES_EMPLOYEE=DES_EMPLOYEE.substring(0,DES_EMPLOYEE.length()-1);
			DES_EMPLOYEE_NAME=DES_EMPLOYEE_NAME.substring(0,DES_EMPLOYEE_NAME.length()-1);
			if (DES_EMPLOYEE_ZHIDING != null && !"".equals(DES_EMPLOYEE_ZHIDING)) {
				paramMap.put("DES_EMPLOYEE", StringUtil.checkNull(request.getParameter("empidEmployee")));
				paramMap.put("DES_EMPLOYEE_NAME", StringUtil.checkNull(request.getParameter("empidEmployeeName")));
			}else{
				paramMap.put("DES_EMPLOYEE", DES_EMPLOYEE);
				paramMap.put("DES_EMPLOYEE_NAME",DES_EMPLOYEE_NAME);
			}
			paramMap.put("DES_DEPARTMENT", DES_DEPARTMENT);
			
			paramMap.put("BUDGET", StringUtil.checkNull(request.getParameter("BUDGET")));
			paramMap.put("BUDGET_SHOW", StringUtil.checkNull(request.getParameter("BUDGET_SHOW")));
			paramMap.put("TEACHER_NAME_EMPID", StringUtil.checkNull(request.getParameter("TEACHER_NAME")));
			String DEPART_MANA_CODE=StringUtil.checkNull(request.getParameter("DEPART_MANA_CODE"));
			if(!"".equals(DEPART_MANA_CODE) && DEPART_MANA_CODE != null){
				paramMap.put("DEPART_MANA_CODE", DEPART_MANA_CODE);
			}else{
				paramMap.put("DEPART_MANA_CODE", StringUtil.checkNull(request.getParameter("DEPART_MANA_CODE_NAME")));
			}
			String TEACHER_EMPID=StringUtil.checkNull(request.getParameter("TEACHER_EMPID"));
			if(!"".equals(TEACHER_EMPID) && TEACHER_EMPID !=null){
				paramMap.put("TEACHER_NAME", TEACHER_EMPID);
			}else{
				paramMap.put("TEACHER_NAME", StringUtil.checkNull(request.getParameter("TEACHER_NAME")));
			}
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CREATED_IP", admin.getAdminIP());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			String maxPeriodtime=eduTrainDao.queryMaxPeriodtime(paramMap);
			if(!"".equals(maxPeriodtime) && maxPeriodtime != null){
				paramMap.put("PERIOD_TIME", Integer.parseInt(maxPeriodtime)+1);
			}else{
				paramMap.put("PERIOD_TIME", 1);
			}
			
			//获取附件信息
			String[] fileName = request.getParameterValues("fileName");
			String[] fileUrl = request.getParameterValues("fileUrl");
			if(!"".equals(fileName) && !"".equals(fileUrl) && fileName!=null && fileUrl!=null){
			String fileNameStr = "";
			String fileUrlStr = "";
			for(int i = 0;i< fileName.length;i++ ){
				if(i == 0){
					fileNameStr = fileName[i];
				}else{
					fileNameStr += ";" + fileName[i];
				}
			}
			for(int i = 0;i< fileUrl.length;i++ ){
				if(i == 0){
					fileUrlStr = fileUrl[i];
				}else{
					fileUrlStr += ";" + fileUrl[i];
				}
			}
			paramMap.put("fileName", fileNameStr);
			paramMap.put("fileUrl", fileUrlStr);
			}
			this.eduTrainDao.addPlanManagerInfo(paramMap);	
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int addTeacherManagerInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String year=StringUtil.checkNull(request.getParameter("BUSINESS_ACT_TIME_YEAR"));
			String month=StringUtil.checkNull(request.getParameter("BUSINESS_ACT_TIME_MONTH"));
			String empid=StringUtil.checkNull(request.getParameter("EMPID"));
			if(!"".equals(year) || !"".equals(month)){
				int yearcount=0;
				if(!"".equals(year)){
					yearcount=Integer.parseInt(year);
				}
				int monthcount=0;
				if(!"".equals(month)){
					monthcount=Integer.parseInt(month);
				}
				int totalcount=yearcount*12+monthcount;
				paramMap.put("BUSINESS_ACT_TIME", String.valueOf(totalcount));
			}else{
				paramMap.put("BUSINESS_ACT_TIME", "0");
			}
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CREATED_IP", admin.getAdminIP());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			String teacher_no=eduTrainDao.queryTeacherNo(paramMap);
			paramMap.put("TEACHER_NO", teacher_no);
			if(empid==null||"".equals(empid)){
				//生成社外人员的empid
				String swempid=eduTrainDao.querySheWaiEmpid(paramMap);
				paramMap.put("EMPID", swempid);
			}
			this.eduTrainDao.addTeacherManagerInfo(paramMap);
			//this.eduTrainDao.addTeacherInformation(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
			String errmessage=e.getMessage();
			String ss="(\"TSTO_SST_CHRS\".\"EDU_TEACHER_MANAGER\".\"TEACHER_NAME\")";
			if(errmessage.indexOf("ORA-01400:")>-1 && errmessage.indexOf(ss)>-1){
				return 2;
			}else{
				return 0;
			}
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int addTrainOrganInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CREATED_IP", admin.getAdminIP());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			//获取附件信息
			String[] fileName = request.getParameterValues("fileName");
			String[] fileUrl = request.getParameterValues("fileUrl");
			if(!"".equals(fileName) && !"".equals(fileUrl) && fileName!=null && fileUrl!=null){
			String fileNameStr = "";
			String fileUrlStr = "";
			for(int i = 0;i< fileName.length;i++ ){
				if(i == 0){
					fileNameStr = fileName[i];
				}else{
					fileNameStr += ";" + fileName[i];
				}
			}
			for(int i = 0;i< fileUrl.length;i++ ){
				if(i == 0){
					fileUrlStr = fileUrl[i];
				}else{
					fileUrlStr += ";" + fileUrl[i];
				}
			}
			paramMap.put("fileName", fileNameStr);
			paramMap.put("fileUrl", fileUrlStr);
			}
			this.eduTrainDao.addTrainOrganInfo(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int addTrainAgreementInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CREATED_IP", admin.getAdminIP());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			//先查询出是否有协议信息
			String agreeid="TRA000001";
			String maxAgreeId=this.eduTrainDao.queryMaxAgreeId(paramMap);
			if(maxAgreeId!=null && !"".equals(maxAgreeId)){
				int a = maxAgreeId.indexOf("TRA");
				String str=maxAgreeId.substring(a+4);
				String result = ""+(Integer.parseInt(str)+1); 
				int size = 6-result.length(); 
				for(int j=0;j<size;j++){ 
				result="0"+result; 
				}
				agreeid="TRA"+result;
				paramMap.put("AGREE_ID", agreeid);
			}else{
				paramMap.put("AGREE_ID", agreeid);
			}
			
			//获取附件信息
			String[] fileName = request.getParameterValues("fileName");
			String[] fileUrl = request.getParameterValues("fileUrl");
			if(!"".equals(fileName) && !"".equals(fileUrl) && fileName!=null && fileUrl!=null){
			String fileNameStr = "";
			String fileUrlStr = "";
			for(int i = 0;i< fileName.length;i++ ){
				if(i == 0){
					fileNameStr = fileName[i];
				}else{
					fileNameStr += ";" + fileName[i];
				}
			}
			for(int i = 0;i< fileUrl.length;i++ ){
				if(i == 0){
					fileUrlStr = fileUrl[i];
				}else{
					fileUrlStr += ";" + fileUrl[i];
				}
			}
			paramMap.put("fileName", fileNameStr);
			paramMap.put("fileUrl", fileUrlStr);
			}
			this.eduTrainDao.addTrainAgreementInfo(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int addTrainBasicInformationInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CREATED_IP", admin.getAdminIP());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("DES_DEPARTMENT", request.getParameter("DES_DEPARTMENT"));
			String basicno=this.eduTrainDao.queryBasicno(paramMap);
			paramMap.put("BASIC_NO", basicno);
			//把计划管理表的activity又1变为2,标志已经添加到基本信息表里
			this.eduTrainDao.changePlanManagerActivity(paramMap);
			this.eduTrainDao.addTrainBasicInformationInfo(paramMap);
			//插入最后确定人员表
			String finalempid=request.getParameter("FINAL_STUDENT_EMPID");
			String finalname=request.getParameter("FINAL_STUDENT_NAME");
			if(finalempid!=null&&!"".equals(finalempid)&&finalname!=null&&!"".equals(finalname)){
				String []arrayfinalempid=finalempid.split(",");
				String []arrayfinalname=finalname.split(",");
				for(int i=0;i<arrayfinalempid.length;i++){
					paramMap.put("FINAL_STUDENT_EMPID", arrayfinalempid[i]);
					paramMap.put("FINAL_STUDENT_NAME", arrayfinalname[i]);
					this.eduTrainDao.finalEduFinalStudent(paramMap);
				}
			}
			
			
			//添加一条基本信息之后,讲师评价表插入讲师
			String comempid=request.getParameter("COM_TEACHER_EMPID");
			String comname=request.getParameter("COM_TEACHER_NAME");
			//插入学员表
			//实际自选人员
			String freeempid=request.getParameter("FREE_EMPLOYEE_EMPID");
			String freename=request.getParameter("FREE_EMPLOYEE_NAME");
			if(freeempid!=null&&!"".equals(freeempid)&&freename!=null&&!"".equals(freename)){
                String [] arrayempid=freeempid.split(",");
                String [] arrayname=freename.split(",");
                for(int i=0;i<arrayempid.length;i++){
                	paramMap.put("EMPID", arrayempid[i]);
                	paramMap.put("LOCAL_NAME", arrayname[i]);
                	paramMap.put("FLAG", "2");
                	this.eduTrainDao.insertEduFreeEmployee(paramMap);
                	//把person_id转化成empid
        			this.eduTrainDao.insertEduTrainResult(paramMap);
        			//讲师评价表插入讲师
        			if(comempid!=null&&!"".equals(comempid)&&comname!=null&&!"".equals(comname)){
        				String [] arraycomempid=comempid.split(",");
                        String [] arraycomname=comname.split(",");
                        for(int y=0;y<arraycomempid.length;y++){
                        	paramMap.put("TEA_EMPID", arraycomempid[y]);
                        	paramMap.put("TEA_LOCAL_NAME", arraycomname[y]);
                        	this.eduTrainDao.insertEduTeacherCheck(paramMap);
                        }
        			}
                }
			}
			//实际指定人员
			String actempid=request.getParameter("ACT_EMPLOYEE_EMPID");
			String actname=request.getParameter("ACT_EMPLOYEE_NAME");
			if(actempid!=null&&!"".equals(actempid)&&actname!=null&&!"".equals(actname)){
                String [] arrayempid=actempid.split(",");
                String [] arrayname=actname.split(",");
                for(int i=0;i<arrayempid.length;i++){
                	paramMap.put("EMPID", arrayempid[i]);
                	paramMap.put("LOCAL_NAME", arrayname[i]);
                	paramMap.put("FLAG", "1");
                	this.eduTrainDao.insertEduFreeEmployee(paramMap);
                	//添加一条基本信息之后,培训结果表插入基本信息外键
        			this.eduTrainDao.insertEduTrainResult(paramMap);
        			//讲师评价表插入讲师
        			if(comempid!=null&&!"".equals(comempid)&&comname!=null&&!"".equals(comname)){
        				String [] arraycomempid=comempid.split(",");
                        String [] arraycomname=comname.split(",");
                        for(int y=0;y<arraycomempid.length;y++){
                        	paramMap.put("TEA_EMPID", arraycomempid[y]);
                        	paramMap.put("TEA_LOCAL_NAME", arraycomname[y]);
                        	this.eduTrainDao.insertEduTeacherCheck(paramMap);
                        }
        			}
                }
			}
			
			//插入费用管理表
			this.eduTrainDao.insertEduCostManager(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int addCourseApply(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CREATED_IP", admin.getAdminIP());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			String basicno=request.getParameter("basicno");
			String personid=request.getParameter("arraypersonid");
			String localname=request.getParameter("arraylocalname");
			String task=request.getParameter("arraytask");
			String makerlevel=request.getParameter("arraymakerlevel");
			if(basicno!=null&&!"".equals(basicno)&&personid!=null&&!"".equals(personid)){
				String []arraybasicno=basicno.split(",");
				String []arraypersonid=personid.split(",");
				String []arraylocalname=localname.split(",");
				String []arraymakerlevel=makerlevel.split(",");
				String []arraytask=null;
				if(task!=null&&!"".equals(task)){
				arraytask=task.split(";");
				}
				for(int i=0;i<arraybasicno.length;i++){
					paramMap.put("BASIC_NO", arraybasicno[i]);
					paramMap.put("STU_PERSON_ID", admin.getPersonId());
					paramMap.put("STU_LOCAL_NAME", admin.getLocalName());
					if(task!=null&&!"".equals(task)){
						paramMap.put("APPLY_TASK", arraytask[i]);
					}
					String applyno=this.eduTrainDao.queryApplyno(paramMap);
					paramMap.put("APPLY_NO", applyno);
					//插入员工申请表
					this.eduTrainDao.addCourseApply(paramMap);
					for(int y=0;y<arraypersonid.length;y++){
						paramMap.put("MAKER_PERSON_ID", arraypersonid[y]);
						paramMap.put("MAKER_LOCAL_NAME", arraylocalname[y]);
						if(y==arraypersonid.length-1){
						paramMap.put("MAKER_LEVEL", arraymakerlevel[y]);
						}else{
						 paramMap.put("MAKER_LEVEL", "0");
						}
						
					//插入决裁表
					this.eduTrainDao.addEduTrainMaker(paramMap);
					}
					
				}
				
			}
			
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int addStudentKaoping(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			
			String checkno=StringUtil.checkNull(request.getParameter("CHECK_NO"));
			if(checkno!=null&&!"".equals(checkno)){
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("CPNY_ID", admin.getCpnyId());
				this.eduTrainDao.updateStudentKaoping(paramMap);
				return 2;
			}else{
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				paramMap.put("CPNY_ID", admin.getCpnyId());
				this.eduTrainDao.addStudentKaoping(paramMap);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateTeacherKaoping(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("CPNY_ID", admin.getCpnyId());
				this.eduTrainDao.updateTeacherKaoping(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateEduTrainResult(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("CPNY_ID", admin.getCpnyId());
				this.eduTrainDao.updateEduTrainResult(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deletePlanManager(HttpServletRequest request) {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("PLAN_NO", StringUtil.checkNull(request.getParameter("PLAN_NO")));
			//删除课程表
			this.eduTrainDao.deleteEduTrainSyllabus(paramMap);
			//删除paln管理表
			this.eduTrainDao.deletePlanManager(paramMap);	
			
			//获取基本信息中引用plan_no的基本信息basic_no
			String basic_no = eduTrainDao.getBasicNoBasePlanNo(paramMap);
			if (!"".equals(basic_no) && basic_no != null) {
				paramMap.put("BASIC_NO", basic_no);
				//删除基本信息中引用该计划管理的所有信息
				this.eduTrainDao.deleteTrainBasicInformation(paramMap);	
				//删除学员表
				this.eduTrainDao.deleteEduFreeEmployee(paramMap);
				//删除学员考评
				this.eduTrainDao.deleteEduStudentCheck(paramMap);
				//删除讲师考评
				this.eduTrainDao.deleteEduTeacherCheck(paramMap);
				//删除培训结果
				this.eduTrainDao.deleteEduTrainResult(paramMap);
				//删除费用管理
				this.eduTrainDao.deleteTrainCostManager_basicno(paramMap);
				//删除最后确定的学生
				this.eduTrainDao.deleteEduFinalStudent_basicno(paramMap);
				//查询课程决裁表的apply_no
				List elist=eduTrainDao.queryApply(paramMap);
				if(elist!=null&&elist.size()>0){
					for(int i=0;i<elist.size();i++){
					    LinkedHashMap linkmap=(LinkedHashMap) elist.get(i);
						paramMap.put("APPLY_NO",linkmap.get("APPLY_NO"));
						//删除部长决裁
						this.eduTrainDao.deleteEduTrainMaker(paramMap);
					}
				}
				//删除学生申请
				this.eduTrainDao.deleteEduStudentApply(paramMap);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deleteCourseSyllabus(HttpServletRequest request) {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("syll_no", StringUtil.checkNull(request.getParameter("syll_no")));
			//删除课程表
			this.eduTrainDao.deleteCourseSyllabus(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deleteTeacherManager(HttpServletRequest request) {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("TEACHER_NO", StringUtil.checkNull(request.getParameter("TEACHER_NO")));
			this.eduTrainDao.deleteTeacherManager(paramMap);	
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deleteSystemMan(HttpServletRequest request) {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("SYSMANA_NO", StringUtil.checkNull(request.getParameter("SYSMANA_NO")));
			this.eduTrainDao.deleteSystemMan(paramMap);	
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deleteTrainOrgan(HttpServletRequest request) {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("ORGAN_NO", StringUtil.checkNull(request.getParameter("ORGAN_NO")));
			this.eduTrainDao.deleteTrainOrgan(paramMap);	
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deleteTrainAgreement(HttpServletRequest request) {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("AGREE_NO", StringUtil.checkNull(request.getParameter("AGREE_NO")));
			this.eduTrainDao.deleteTrainAgreement(paramMap);	
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deleteTrainBasicInformation(HttpServletRequest request) {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("BASIC_NO", StringUtil.checkNull(request.getParameter("BASIC_NO")));
			//计划管理表activity又2变为1
			this.eduTrainDao.changePlanManagerActivityDel(paramMap);
			this.eduTrainDao.deleteTrainBasicInformation(paramMap);
			//删除学员表
			this.eduTrainDao.deleteEduFreeEmployee(paramMap);
			//删除学员考评
			this.eduTrainDao.deleteEduStudentCheck(paramMap);
			//删除讲师考评
			this.eduTrainDao.deleteEduTeacherCheck(paramMap);
			//删除培训结果
			this.eduTrainDao.deleteEduTrainResult(paramMap);
			//删除费用管理
			this.eduTrainDao.deleteTrainCostManager_basicno(paramMap);
			//删除最后确定的学生
			this.eduTrainDao.deleteEduFinalStudent_basicno(paramMap);
			//查询课程决裁表的apply_no
			List elist=eduTrainDao.queryApply(paramMap);
			if(elist!=null&&elist.size()>0){
				for(int i=0;i<elist.size();i++){
				    LinkedHashMap linkmap=(LinkedHashMap) elist.get(i);
					paramMap.put("APPLY_NO",linkmap.get("APPLY_NO"));
					//删除部长决裁
					this.eduTrainDao.deleteEduTrainMaker(paramMap);
				}
			}
			//删除学生申请
			this.eduTrainDao.deleteEduStudentApply(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public int deleteTrainCostManager(HttpServletRequest request) {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			this.eduTrainDao.deleteTrainCostManager(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public int cancelApply(HttpServletRequest request) {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			this.eduTrainDao.deleteEduTrainMaker(paramMap);
			this.eduTrainDao.cancelApply(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
				return 0;
		}
		return 1;
	}
	@SuppressWarnings({ "rawtypes" })
	@Override
	public int getcourseNum(HttpServletRequest request) {
		int result =0;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			result = this.eduTrainDao.getcourseNum(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateSystemManager(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("SYSMANA_NO", StringUtil.checkNull(request.getParameter("SYSMANA_NO")));
			paramMap.put("REMARK", StringUtil.checkNull(request.getParameter("REMARK")));
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
            this.eduTrainDao.updateSystemManager(paramMap);			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateCourseManager(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("COURSE_NO", StringUtil.checkNull(request.getParameter("COURSE_NO")));
			paramMap.put("REMARK", StringUtil.checkNull(request.getParameter("REMARK")));
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			//修改课程管理表的名称和备注
            this.eduTrainDao.updateCourseManager(paramMap);
            //修改计划管理表的名称
            this.eduTrainDao.updatePlanManager_course(paramMap);
            //修改基本信息表的名称
            this.eduTrainDao.updateBasicInformation_course(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updatePlanManager(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("PLAN_NO", StringUtil.checkNull(request.getParameter("PLAN_NO")));
			paramMap.put("PLAN_STARTDATE", StringUtil.checkNull(request.getParameter("PLAN_STARTDATE")));
			paramMap.put("PLAN_ENDDATE", StringUtil.checkNull(request.getParameter("PLAN_ENDDATE")));
			paramMap.put("CLASS_HOUR", StringUtil.checkNull(request.getParameter("CLASS_HOUR")));
			paramMap.put("CLASS_UNIT", StringUtil.checkNull(request.getParameter("CLASS_UNIT")));
			paramMap.put("TRAIN_FORM_CODE", StringUtil.checkNull(request.getParameter("TRAIN_FORM_CODE")));
			paramMap.put("ISNOT_TEST", StringUtil.checkNull(request.getParameter("ISNOT_TEST")));
			paramMap.put("ISNOT_APPLY", StringUtil.checkNull(request.getParameter("ISNOT_APPLY")));
			paramMap.put("DES_DEPARTMENT", StringUtil.checkNull(request.getParameter("DES_DEPARTMENT")));
			paramMap.put("DES_EMPLOYEE", StringUtil.checkNull(request.getParameter("empidEmployee")));
			paramMap.put("DES_EMPLOYEE_NAME", StringUtil.checkNull(request.getParameter("empidEmployeeName")));
			paramMap.put("BUDGET", StringUtil.checkNull(request.getParameter("BUDGET")));
			paramMap.put("BUDGET_SHOW", StringUtil.checkNull(request.getParameter("BUDGET_SHOW")));
			String DEPART_MANA_CODE=StringUtil.checkNull(request.getParameter("DEPART_MANA_CODE"));
			if(!"".equals(DEPART_MANA_CODE) && DEPART_MANA_CODE != null){
				paramMap.put("DEPART_MANA_CODE", DEPART_MANA_CODE);
			}else{
				paramMap.put("DEPART_MANA_CODE", StringUtil.checkNull(request.getParameter("DEPART_MANA_CODE_NAME")));
			}
			String TEACHER_EMPID=StringUtil.checkNull(request.getParameter("TEACHER_EMPID"));
			if(!"".equals(TEACHER_EMPID) && TEACHER_EMPID != null){
				paramMap.put("TEACHER_NAME", TEACHER_EMPID);
				paramMap.put("TEACHER_NAME_EMPID", StringUtil.checkNull(request.getParameter("TEACHER_NAME")));
			}else{
				paramMap.put("TEACHER_NAME", StringUtil.checkNull(request.getParameter("TEACHER_NAME")));
			}
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
            this.eduTrainDao.updatePlanManager(paramMap);	
            //修改基本信息中实施时间
            this.eduTrainDao.updateBasicInformation(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateTeacherManager(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String year=StringUtil.checkNull(request.getParameter("BUSINESS_ACT_TIME_year"));
			String month=StringUtil.checkNull(request.getParameter("BUSINESS_ACT_TIME_month"));
			if(!"".equals(year) || !"".equals(month)){
				int yearcount=0;
				if(!"".equals(year)){
					yearcount=Integer.parseInt(year);
				}
				int monthcount=0;
				if(!"".equals(month)){
					monthcount=Integer.parseInt(month);
				}
				int totalcount=yearcount*12+monthcount;
				paramMap.put("BUSINESS_ACT_TIME", String.valueOf(totalcount));
			}			
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
            this.eduTrainDao.updateTeacherManager(paramMap);
            /*LinkedHashMap linkMap = (LinkedHashMap)eduTrainDao.teacherManagerInfo(paramMap);
            String afterStatus=(String) linkMap.get("TEACH_STATUS_CODE");
            this.eduTrainDao.addTeacherInformation(paramMap);*/
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateTrainOrgan(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			//获取附件信息
			String[] fileName = request.getParameterValues("fileName");
			String[] fileUrl = request.getParameterValues("fileUrl");
			if(!"".equals(fileName) && !"".equals(fileUrl) && fileName!=null && fileUrl!=null){
			String fileNameStr = "";
			String fileUrlStr = "";
			for(int i = 0;i< fileName.length;i++ ){
				if(i == 0){
					fileNameStr = fileName[i];
				}else{
					fileNameStr += ";" + fileName[i];
				}
			}
			for(int i = 0;i< fileUrl.length;i++ ){
				if(i == 0){
					fileUrlStr = fileUrl[i];
				}else{
					fileUrlStr += ";" + fileUrl[i];
				}
			}
			paramMap.put("fileName", fileNameStr);
			paramMap.put("fileUrl", fileUrlStr);
			}
            this.eduTrainDao.updateTrainOrgan(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateTrainAgree(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			/*String isnotempty=eduTrainDao.queryAgreeid(paramMap);
			if(isnotempty==null || "".equals(isnotempty)){
				//先查询出是否有协议信息
				String agreeid="TRA000001";
				String maxAgreeId=this.eduTrainDao.queryMaxAgreeId(paramMap);
				if(maxAgreeId!=null && !"".equals(maxAgreeId)){
					String str=maxAgreeId.substring(maxAgreeId.indexOf("TRA")+3);
					String result = ""+(Integer.parseInt(str)+1); 
					int size = 6-result.length(); 
					for(int j=0;j<size;j++){ 
					result="0"+result; 
					}
					agreeid="TRA"+result;
					paramMap.put("AGREE_ID", agreeid);
				}else{
					paramMap.put("AGREE_ID", agreeid);
				}
			}*/
			
            this.eduTrainDao.updateTrainAgree(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateTrainBasicInformationInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("CREATRD_BY", admin.getAdminID());
			paramMap.put("CREATRD_IP", admin.getAdminIP());
			paramMap.put("DES_DEPARTMENT", request.getParameter("DES_DEPARTMENT"));
            this.eduTrainDao.updateTrainBasicInformationInfo(paramMap);
            //先删除所有的自选人员
            //this.eduTrainDao.deleteFreeEmployee(paramMap);
            //修改学员表
			//实际自选人员
            String oldFreeEmpid=request.getParameter("OLD_FREE_EMPLOYEE_EMPID");
			String freeempid=request.getParameter("FREE_EMPLOYEE_EMPID");
			String freename=request.getParameter("FREE_EMPLOYEE_NAME");
			//修改前的人不在修改后的人里,就删除修改前的这个人
			if(oldFreeEmpid!=null&&!"".equals(oldFreeEmpid)){
				String [] arrayOleFreeEmpid=oldFreeEmpid.split(",");
                for(int i=0;i<arrayOleFreeEmpid.length;i++){
                	String singleOldFreeEmpid=arrayOleFreeEmpid[i];
                	if(freeempid.indexOf(singleOldFreeEmpid)<0){
	            		paramMap.put("EMPID", arrayOleFreeEmpid[i]);
	            		this.eduTrainDao.deleteFreeEmployee(paramMap);
	            		//删除学生考评
	        			this.eduTrainDao.deleteSingleEduStudentCheck(paramMap);
                	}
                }
			}
			//修改后的人不在修改前的人里,就增加修改前的这个人
			if(freeempid!=null&&!"".equals(freeempid)&&freename!=null&&!"".equals(freename)){
                String [] arrayempid=freeempid.split(",");
                String [] arrayname=freename.split(",");
                for(int i=0;i<arrayempid.length;i++){
                	String singleFreeempid=arrayempid[i];
                	if(oldFreeEmpid.indexOf(singleFreeempid)<0){
                		paramMap.put("EMPID", arrayempid[i]);
                    	paramMap.put("LOCAL_NAME", arrayname[i]);
                    	paramMap.put("FLAG", "2");
                    	this.eduTrainDao.insertEduFreeEmployee(paramMap);
                	}
                	
                }
			}
			//实际指定人员
			String oldActempid=request.getParameter("OLD_ACT_EMPLOYEE_EMPID");
			String actempid=request.getParameter("ACT_EMPLOYEE_EMPID");
			String actname=request.getParameter("ACT_EMPLOYEE_NAME");
			//修改前的人不在修改后的人里,就删除修改前的这个人
			if(oldActempid!=null&&!"".equals(oldActempid)){
				String [] arrayoldActempid=oldActempid.split(",");
				for(int i=0;i<arrayoldActempid.length;i++){
					String singleOldActempid=arrayoldActempid[i];
					if(actempid.indexOf(singleOldActempid)<0){
						paramMap.put("EMPID", arrayoldActempid[i]);
	            		this.eduTrainDao.deleteFreeEmployee(paramMap);
	            		//删除学生考评
	        			this.eduTrainDao.deleteSingleEduStudentCheck(paramMap);
					}
					
				}
			}
			//修改后的人不在修改前的人里,就增加修改前的这个人
			if(actempid!=null&&!"".equals(actempid)&&actname!=null&&!"".equals(actname)){
                String [] arrayempid=actempid.split(",");
                String [] arrayname=actname.split(",");
                for(int i=0;i<arrayempid.length;i++){
                	if(oldActempid.indexOf(arrayempid[i])<0){
                		paramMap.put("EMPID", arrayempid[i]);
                    	paramMap.put("LOCAL_NAME", arrayname[i]);
                    	paramMap.put("FLAG", "1");
                    	this.eduTrainDao.insertEduFreeEmployee(paramMap);
                	}
                }
			}
			//先删除所有的最后的实际学生
			this.eduTrainDao.deleteEduFinalStudent_basicno(paramMap);
			//插入最后确定人员表
			String finalempid=request.getParameter("FINAL_STUDENT_EMPID");
			String finalname=request.getParameter("FINAL_STUDENT_NAME");
			if(finalempid!=null&&!"".equals(finalempid)&&finalname!=null&&!"".equals(finalname)){
				String []arrayfinalempid=finalempid.split(",");
				String []arrayfinalname=finalname.split(",");
				for(int i=0;i<arrayfinalempid.length;i++){
					paramMap.put("FINAL_STUDENT_EMPID", arrayfinalempid[i]);
					paramMap.put("FINAL_STUDENT_NAME", arrayfinalname[i]);
					this.eduTrainDao.finalEduFinalStudent(paramMap);
				}
			}
			//先删除讲师评价下basic_no对应的信息
			this.eduTrainDao.deleteEduCheckTeachear_basicno(paramMap);
			String comempid=request.getParameter("COM_TEACHER_EMPID");
			String comname=request.getParameter("COM_TEACHER_NAME");
			List teaList = this.eduTrainDao.getAllTeacherBaseonBasicNo(paramMap);
			if(comempid!=null&&!"".equals(comempid)&&comname!=null&&!"".equals(comname)){
                for (Iterator iterator = teaList.iterator(); iterator.hasNext();) {
					LinkedHashMap map = (LinkedHashMap) iterator.next();
					paramMap.put("EMPID", map.get("EMPID"));
                	paramMap.put("LOCAL_NAME", map.get("LOCAL_NAME"));
        			String [] arraycomempid=comempid.split(",");
                    String [] arraycomname=comname.split(",");
                    for(int y=0;y<arraycomempid.length;y++){
                        paramMap.put("TEA_EMPID", arraycomempid[y]);
                        paramMap.put("TEA_LOCAL_NAME", arraycomname[y]);
                        this.eduTrainDao.insertEduTeacherCheck(paramMap);
                    }
             }
		}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateTrainCostManagerInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
            this.eduTrainDao.updateTrainCostManagerInfo(paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateCourseMaker(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			String makerno=request.getParameter("arraymakerno");
			if(makerno!=null&&!"".equals(makerno)){
				String []arrayMakerno=makerno.split(",");
				for(int i=0;i<arrayMakerno.length;i++){
					paramMap.put("APPLY_NO",arrayMakerno[i]);
					this.eduTrainDao.updateCourseMaker(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateCourseConfirm(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			String confirmno=request.getParameter("arrayconfirmno");
			String basicno=request.getParameter("arraybasicno");
			String empid=request.getParameter("arrayempid");
			String name=request.getParameter("arrayname");
			String confirmflag=request.getParameter("CONFIRM_FLAG");
			if(confirmno!=null&&!"".equals(confirmno)){
				String []arrayConfirmno=confirmno.split(",");
				String []arraybasicno=basicno.split(",");
				String []arrayempid=empid.split(",");
				String []arrayname=name.split(",");
				
				for(int i=0;i<arrayConfirmno.length;i++){
					paramMap.put("APPLY_NO",arrayConfirmno[i]);
					this.eduTrainDao.updateCourseConfirm(paramMap);
					//把学生插入edu_free_employee
					paramMap.put("BASIC_NO",arraybasicno[i]);
					paramMap.put("EMPID",arrayempid[i]);
					paramMap.put("LOCAL_NAME",arrayname[i]);
					paramMap.put("FLAG","3");
					if("2".equals(confirmflag)){
						this.eduTrainDao.insertEduFreeEmployee(paramMap);
						this.eduTrainDao.deleteEduFreeEmployee_empid_more(paramMap);
	                	//添加一个学生之后,学生对应的培训结果表插入基本信息外键
	        			this.eduTrainDao.insertEduTrainResult(paramMap);
	        			this.eduTrainDao.deleteEduTrainResult_stuempid_more(paramMap);
	        			//根据basic_no查出所有讲师
	        			String comteacherempid=eduTrainDao.queryComTeacherEmpid(paramMap);
	        			String comteachername=eduTrainDao.queryComTeacherName(paramMap);
	        			if(!"".equals(comteacherempid) && comteacherempid != null && !"".equals(comteachername) && comteachername != null){
	        				String []arraycomempid=comteacherempid.split(",");
	        				String []arraycomname=comteachername.split(",");
	        				for(int y=0;y<arraycomempid.length;y++){
	        					paramMap.put("TEA_EMPID", arraycomempid[y]);
	                        	paramMap.put("TEA_LOCAL_NAME", arraycomname[y]);
	                        	this.eduTrainDao.insertEduTeacherCheck(paramMap);
	                        	this.eduTrainDao.deleteEduTeacherCheck_stuempid_more(paramMap);
	        				}
	        			}
					}else if("1".equals(confirmflag)||"0".equals(confirmflag)){
						//否决或者待确认之后删除实际学生表的学生
						this.eduTrainDao.deleteEduFreeEmployee_empid(paramMap);
						//删除一个学生之后,相应的删除学生对应的培训结果
						this.eduTrainDao.deleteEduTrainResult_stuempid(paramMap);
						//删除讲师评价表
						this.eduTrainDao.deleteEduTeacherCheck_stuempid(paramMap);
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int updateStudentEvaluateInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			String arrayfreeno=request.getParameter("arrayfreeno");
			/*String arrayallscore=request.getParameter("arrayallscore");*/
			String arrayevaresult=request.getParameter("arrayevaresult");
			if(arrayfreeno!=null&&!"".equals(arrayfreeno)){
				String [] array=arrayfreeno.split(",");
				/*String [] arraytestscore=arrayallscore.split(",");*/
				String [] arrayeva=arrayevaresult.split(",");
				for(int i=0;i<array.length;i++){
					paramMap.put("FREE_NO", array[i]);
					/*if(("zero").equals(arraytestscore[i])){
						paramMap.put("TEST_SCORE", "");
					}else{
						paramMap.put("TEST_SCORE", arraytestscore[i]);
					}*/
					if(("zero").equals(arrayeva[i])){
						paramMap.put("EVA_RESULT", "");
					}else{
						paramMap.put("EVA_RESULT", arrayeva[i]);
					}
					this.eduTrainDao.updateStudentEvaluateInfo(paramMap);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List systemManager(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.systemManager(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List planManager(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if ("14014481".equals(paramMap.get("TRAIN_DIFF_CODE"))) {
			paramMap.put("TRAIN_DIFF_NAME", "GSVP");
		}else if ("14014482".equals(paramMap.get("TRAIN_DIFF_CODE"))) {
			paramMap.put("TRAIN_DIFF_NAME", "GSLP");
		}else if ("14014483".equals(paramMap.get("TRAIN_DIFF_CODE"))) {
			paramMap.put("TRAIN_DIFF_NAME", "GSEP");
		}else if ("14014484".equals(paramMap.get("TRAIN_DIFF_CODE"))){
			paramMap.put("TRAIN_DIFF_NAME", "GSGP");
		}else {
			paramMap.put("TRAIN_DIFF_NAME", "");
		}
		return eduTrainDao.planManager(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List planManager_basic(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.planManager_basic(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List teacherManager(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.teacherManager(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List trainAgreement(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.trainAgreement(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List trainBasicInformation(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("adminempid", admin.getEmpID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.trainBasicInformation(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List trainCostManager(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.trainCostManager(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List courseApply(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.courseApply(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List courseMaker(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("adminPersonid", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.courseMaker(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List courseConfirm(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
		if(request.getParameter("confirmstartdate")==""||request.getParameter("confirmstartdate")==null){
			//获取当前月第一天：
			Calendar c = Calendar.getInstance();    
			c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());
			paramMap.put("confirmstartdate",first);
		}
	    if(request.getParameter("confirmenddate")==""||request.getParameter("confirmenddate")==null){
	    	Calendar c = Calendar.getInstance();
	    	c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
			String last = format.format(c.getTime());
			paramMap.put("confirmenddate",last);
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.courseConfirm(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List makerSituation(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
		if(request.getParameter("situationstartdate")==""||request.getParameter("situationstartdate")==null){
			//获取当前月第一天：
			Calendar c = Calendar.getInstance();    
			c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());
			paramMap.put("situationstartdate",first);
		}
	    if(request.getParameter("situationenddate")==""||request.getParameter("situationenddate")==null){
	    	Calendar c = Calendar.getInstance();
	    	c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
			String last = format.format(c.getTime());
			paramMap.put("situationenddate",last);
		}
		
		/*if(admin.getCpnyId().equals("TSTO")){
			paramMap.put("TRAIN_PERSON_ID", "2000560");
		}else if(admin.getCpnyId().equals("SST")){
			paramMap.put("TRAIN_PERSON_ID", "2000889");
		}*/
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.makerSituation(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryEmployee(HttpServletRequest request,String empid) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("EMPID", empid);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryEmployee(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryDefaultMaker(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("adminPersonId", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryDefaultMaker(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List trainArchives(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
		if(request.getParameter("actStartdate")==""||request.getParameter("actStartdate")==null){
			//获取当前月第一天：
			Calendar c = Calendar.getInstance();    
			c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());
			paramMap.put("actStartdate",first);
		}
	    if(request.getParameter("actEnddate")==""||request.getParameter("actEnddate")==null){
	    	Calendar c = Calendar.getInstance();
	    	c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
			String last = format.format(c.getTime());
			paramMap.put("actEnddate",last);
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String to_dateString = paramMap.get("actEnddate").toString().replace("-", "/");
		String from_dateString = paramMap.get("actStartdate").toString().replace("-", "/");
		String history_date_string = "07/08/2015";
		Date to_date = format.parse(to_dateString);
		Date from_date = format.parse(from_dateString);
		Date history_date = format.parse(history_date_string);
		if (to_date.getTime() > history_date.getTime()) {
			if ( history_date.getTime() >= from_date.getTime() && history_date.getTime() <= to_date.getTime()) {
				List alllist = new ArrayList();
				alllist.addAll(eduTrainDao.trainArchives(paramMap));
				alllist.addAll(eduTrainDao.trainArchiveshistory(paramMap));
				return alllist;
			}else {
				return eduTrainDao.trainArchives(paramMap);
			}
		}else {
			return eduTrainDao.trainArchiveshistory(paramMap);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryIsnotEva(HttpServletRequest request,String basicno) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("basicno", basicno);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryIsnotEva(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List studentChakan(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.studentChakan(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List teacherChakan(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.teacherChakan(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List studentChakanSecond(HttpServletRequest request,LinkedHashMap paramMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.studentChakan(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List trainOrgan(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.trainOrgan(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List desEmployee(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String deptno=StringUtil.checkNull(request.getParameter("DEPTNO"));
		if(!"".equals(deptno)){
			String [] array=deptno.split(",");
			String str="";
			for(int i=0;i<array.length;i++){
				str=str+"'"+array[i]+"',";
			}
			str=str.substring(0,str.length()-1);
			paramMap.put("DEPTNO", str);
		}
		if ("L".equals(paramMap.get("fenlei"))) {
			paramMap.put("KEY", "h.local_name");
		}else if("E".equals(paramMap.get("fenlei"))){
			paramMap.put("KEY", "h.empid");
		}else {
			paramMap.put("KEY", "h.local_name");
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.desEmployee(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List photoMissing(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.photoMissing(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List teacherSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.teacherSearch(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryCourseSyllabus(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryCourseSyllabus(paramMap);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryCourseSyllabus3(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryCourseSyllabus3(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List commonTeacher(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String tearcherempid=request.getParameter("teacherempid");
		String str="";
		if(tearcherempid!=null&&!"".equals(tearcherempid)){
			String [] array=tearcherempid.split(",");
			for(int i=0;i<array.length;i++){
				str=str+"'"+array[i]+"',";
			}
			str=str.substring(0,str.length()-1);
			paramMap.put("EMPID", str);
		}
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.commonTeacher(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryFreeEmployee(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryFreeEmployee(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryfinalstudent(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryfinalstudent(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryEvaAllemployee(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("tearcherPersonid", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryEvaAllemployee(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryEvaAllTeacher(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("stuEmpid", admin.getEmpID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryEvaAllTeacher(paramMap);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryEvaAllTSTOTeacher(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("stuEmpid", admin.getEmpID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryEvaAllTSTOTeacher(paramMap);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List planEmployee(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String planempid=request.getParameter("planempid");
		String str="";
		if(planempid!=null&&!"".equals(planempid)){
			String [] array=planempid.split(",");
			for(int i=0;i<array.length;i++){
				str=str+"'"+array[i]+"',";
			}
			str=str.substring(0,str.length()-1);
			paramMap.put("EMPID", str);
		}
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.planEmployee(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List otherPlanEmployee(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String planempid=request.getParameter("planempid");
		String deptno=StringUtil.checkNull(request.getParameter("DEPTNO"));
		if(!"".equals(deptno)){
			String [] array=deptno.split(",");
			String str="";
			for(int i=0;i<array.length;i++){
				str=str+"'"+array[i]+"',";
			}
			str=str.substring(0,str.length()-1);
			paramMap.put("DEPTNO", str);
		}
		String str="";
		if(planempid!=null&&!"".equals(planempid)){
			String [] array=planempid.split(",");
			for(int i=0;i<array.length;i++){
				str=str+"'"+array[i]+"',";
			}
			str=str.substring(0,str.length()-1);
			paramMap.put("EMPID", str);
		}
		if ("L".equals(paramMap.get("fenlei"))) {
			paramMap.put("KEY", "t.local_name");
		}else if("E".equals(paramMap.get("fenlei"))){
			paramMap.put("KEY", "t.empid");
		}else {
			paramMap.put("KEY", "t.local_name");
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.otherPlanEmployee(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List finalstudent(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String finalempid1=request.getParameter("finalempid1");
		String str="";
		if(finalempid1!=null&&!"".equals(finalempid1)){
			String [] array=finalempid1.split(",");
			for(int i=0;i<array.length;i++){
				str=str+"'"+array[i]+"',";
			}
			if (str.length() >1) {
				str=str.substring(0,str.length()-1);
			} else {
				str = "''";
			}
			paramMap.put("EMPID", str);
		}
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.finalstudent(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List querylocalname(HttpServletRequest request,String str) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("EMPID", str);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.querylocalname(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List courseManager(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if ("14014481".equals(paramMap.get("TRAIN_DIFF_CODE"))) {
			paramMap.put("TRAIN_DIFF_NAME", "GSVP");
		}else if ("14014482".equals(paramMap.get("TRAIN_DIFF_CODE"))) {
			paramMap.put("TRAIN_DIFF_NAME", "GSLP");
		}else if ("14014483".equals(paramMap.get("TRAIN_DIFF_CODE"))) {
			paramMap.put("TRAIN_DIFF_NAME", "GSEP");
		}else if ("14014484".equals(paramMap.get("TRAIN_DIFF_CODE"))){
			paramMap.put("TRAIN_DIFF_NAME", "GSGP");
		}else {
			paramMap.put("TRAIN_DIFF_NAME", "");
		}
		return eduTrainDao.courseManager(paramMap);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List queryTeacher(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryTeacher(paramMap);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List queryPeixun(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("LOCAL_NAME", request.getParameter("makername"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EmpOffice",request.getParameter("EmpOffice"));
		return eduTrainDao.queryPeixun(paramMap);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List queryBasicNo(HttpServletRequest request,String str) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("str", str);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryBasicNo(paramMap);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List queryStrBasicNo(HttpServletRequest request,String str) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("str", str);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryStrBasicNo(paramMap);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List queryResultBasicNo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryResultBasicNo(paramMap);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List alreadyTrainResultList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.alreadyTrainResultList(paramMap);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List alreadyTrainResultTSTOList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.alreadyTrainResultTSTOList(paramMap);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List checkTrainResultTSTOInfoPer(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.checkTrainResultTSTOInfoPer(paramMap);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List trainResultInfoEveList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("stuempid", admin.getEmpID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.trainResultInfoEveList(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object systemManagerInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("SYSMANA_NO")!=null){
			paramMap.put("SYSMANA_NO",request.getParameter("SYSMANA_NO"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.systemManagerInfo(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object courseManagerInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("COURSE_NO")!=null){
			paramMap.put("COURSE_NO",request.getParameter("COURSE_NO"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.courseManagerInfo(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object planManagerInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("PLAN_NO")!=null){
			paramMap.put("PLAN_NO",request.getParameter("PLAN_NO"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.planManagerInfo(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object planManagerInfoDetail(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("PLAN_NO")!=null){
			paramMap.put("PLAN_NO",request.getParameter("PLAN_NO"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.planManagerInfoDetail(paramMap);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List syllabusInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.syllabusInfo(paramMap);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List singleTeacherInformation(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("PERSON_ID")!=null){
			paramMap.put("PERSON_ID",request.getParameter("PERSON_ID"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.singleTeacherInformation(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object teacherManagerInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("TEACHER_NO")!=null){
			paramMap.put("TEACHER_NO",request.getParameter("TEACHER_NO"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.teacherManagerInfo(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object trainOrganInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("ORGAN_NO")!=null){
			paramMap.put("ORGAN_NO",request.getParameter("ORGAN_NO"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.trainOrganInfo(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object trainAgreementInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("AGREE_NO")!=null){
			paramMap.put("AGREE_NO",request.getParameter("AGREE_NO"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.trainAgreementInfo(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object trainBasicInformationInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("BASIC_NO")!=null){
			paramMap.put("BASIC_NO",request.getParameter("BASIC_NO"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.trainBasicInformationInfo(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object trainCostManagerInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.trainCostManagerInfo(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object studentKaopingInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("teacherPersonid", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.studentKaopingInfo(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object teacherKaopingInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.teacherKaopingInfo(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object trainResultInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("stuempid", admin.getEmpID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if ("TSTO".equals(admin.getCpnyId())) {
			return eduTrainDao.trainResultTSTOInfo(paramMap);
		}else {
			return eduTrainDao.trainResultInfo(paramMap);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryDesEmployee(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("PLAN_NO")!=null){
			paramMap.put("PLAN_NO",request.getParameter("PLAN_NO"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryDesEmployee(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryPlanNo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryPlanNo(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryClob(HttpServletRequest request,String str) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("BASIC_NO")!=null){
			paramMap.put("BASIC_NO",request.getParameter("BASIC_NO"));
		}
		paramMap.put("str", str);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryClob(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryClobSecond(HttpServletRequest request,String content,String table,String term) {
		String clobSecond ="";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("content", content);
		paramMap.put("table", table);
		paramMap.put("term", term);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		clobSecond = eduTrainDao.queryClobSecond(paramMap);
		return clobSecond;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryCountnum(HttpServletRequest request,String basicno,String adminPersonid) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("basicno", basicno);
		paramMap.put("adminPersonid", adminPersonid);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryCountnum(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String alreadycountnum(HttpServletRequest request,String basicno) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("basicno", basicno);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.alreadycountnum(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryDesEmployeeName(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("PLAN_NO")!=null){
			paramMap.put("PLAN_NO",request.getParameter("PLAN_NO"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryDesEmployeeName(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryTeacherName(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("PLAN_NO")!=null){
			paramMap.put("PLAN_NO",request.getParameter("PLAN_NO"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryTeacherName(paramMap);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryTeacherNameEmpid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(request.getParameter("PLAN_NO")!=null){
			paramMap.put("PLAN_NO",request.getParameter("PLAN_NO"));
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return eduTrainDao.queryTeacherNameEmpid(paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public String getTrainAgreeList(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList,String flag) throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String name="";
		aliasNameList.add("Contract name (not null)"); //协议名称(必填)
		aliasNameList.add("Employee numbere (not null)"); //社号(必填)
		aliasNameList.add("Employee name(not null)"); //姓名(必填)
		aliasNameList.add("Department");
		aliasNameList.add("Training starting date"); //研修开始日
		aliasNameList.add("Training finishing date"); //研修结束日
		
		aliasNameList.add("Training days"); //研修天数
		aliasNameList.add("Working days (year)"); //服务天数(年)
		aliasNameList.add("Contract starting date"); //合同开始日
		aliasNameList.add("Contract finishing date"); //合同到期日
		aliasNameList.add("Working starting date"); //服务开始日
		aliasNameList.add("Working finishing date"); //服务结束日
		
		aliasNameList.add("Exchange rate of the month"); //当月汇率
		aliasNameList.add("Passport and visa fee"); //护签费
		aliasNameList.add("Vaccination fee"); //出国防疫费 
		aliasNameList.add("Air ticket fee"); //机票费
		aliasNameList.add("Housing benefits"); //住房补助
		
		aliasNameList.add("Going abroad benefits"); //出国补助
		aliasNameList.add("Going abroad benefits(actual)"); //出国补助实际
		aliasNameList.add("Commercial insurance"); //商业保险费
		aliasNameList.add("Salary given to training people"); //研修工资
		
		aliasNameList.add("Transportation fee"); //交通费
		aliasNameList.add("Telecommunication fee"); //通信费
		aliasNameList.add("Total"); //合计
		aliasNameList.add("Contract sign date"); //协议签订日期
		aliasNameList.add("Contract release date"); //协议解除日期
		aliasNameList.add("Resignation date(predicted)"); //预离职日期
		aliasNameList.add("Actual amount paid"); //实际支付
		aliasNameList.add("Remark"); //
		if("load".equals(flag)){
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "Training agreement XXX");
			map.put("CELL1", "20000013");
			map.put("CELL2", "Name");
			map.put("CELL3", "HR Part");
			map.put("CELL4", "01/01/2018");
			map.put("CELL5", "30/01/2018");
			
			map.put("CELL6", "90");
			map.put("CELL7", "2");
			map.put("CELL8", "01/01/2018");
			map.put("CELL9", "30/01/2018");
			map.put("CELL10", "01/01/2018");
			map.put("CELL11", "30/01/2018");
			
			map.put("CELL12", "12");
			map.put("CELL13", "100");
			map.put("CELL14", "100");
			map.put("CELL15", "100");
			map.put("CELL16", "100");
			
			map.put("CELL17", "100");
			map.put("CELL18", "100");
			map.put("CELL19", "100");
			map.put("CELL20", "100");
			
			map.put("CELL21", "100");
			map.put("CELL22", "100");
			map.put("CELL23", "1000");
			map.put("CELL24", "01/01/2018");
			map.put("CELL25", "30/01/2018");
			map.put("CELL26", "01/01/2019");
			map.put("CELL27", "60");
			map.put("CELL28", "Remark");
			list.add(map);
			name="trainAgreement_demo";
		}else if("export".equals(flag)){
			paramMap.put("CPNY_ID", admin.getCpnyId());
			List elist=new ArrayList();
			elist=this.eduTrainDao.trainAgreement(paramMap);
			if(elist != null && elist.size() >0){
				for(int i=0;i<elist.size();i++){
					LinkedHashMap edumap = new LinkedHashMap();
					LinkedHashMap newedumap = new LinkedHashMap();
					edumap=(LinkedHashMap) elist.get(i);
					newedumap.put("CELL0", edumap.get("AGREE_NAME"));
					newedumap.put("CELL1", edumap.get("EMPID"));
					newedumap.put("CELL2", edumap.get("LOCAL_NAME"));
					newedumap.put("CELL3", edumap.get("DEPART_NAME"));
					newedumap.put("CELL4", edumap.get("STUDY_START_DATE"));
					newedumap.put("CELL5", edumap.get("STUDY_END_DATE"));
					
					newedumap.put("CELL6", edumap.get("STUDY_DAY"));
					newedumap.put("CELL7", edumap.get("SERVICE_YEAR"));
					newedumap.put("CELL8", edumap.get("CON_START_DATE"));
					newedumap.put("CELL9", edumap.get("CON_END_DATE"));
					newedumap.put("CELL10", edumap.get("SER_START_DATE"));
					newedumap.put("CELL11", edumap.get("SER_END_DATE"));
					
					newedumap.put("CELL12", edumap.get("EXCHANGE_RATE"));
					newedumap.put("CELL13", edumap.get("HQ_FREE"));
					newedumap.put("CELL14", edumap.get("CGFY_FREE"));
					newedumap.put("CELL15", edumap.get("JP_FREE"));
					newedumap.put("CELL16", edumap.get("ZFBZ_FREE"));
					
					newedumap.put("CELL17", edumap.get("CGBZ_FREE"));
					newedumap.put("CELL18", edumap.get("CGBZ_FREE_FACT"));
					newedumap.put("CELL19", edumap.get("SYBX_FREE"));
					newedumap.put("CELL20", edumap.get("YX_PAY"));
					
					newedumap.put("CELL21", edumap.get("JT_FREE"));
					newedumap.put("CELL22", edumap.get("TX_FREE"));
					newedumap.put("CELL23", edumap.get("WEIYUEJIN"));
					newedumap.put("CELL24", edumap.get("AGREE_START_DATE"));
					newedumap.put("CELL25", edumap.get("AGREE_END_DATE"));
					newedumap.put("CELL26", edumap.get("LEFT_DATE"));
					newedumap.put("CELL27", edumap.get("FACT_PAY"));
					newedumap.put("CELL28", edumap.get("REMARK"));
					list.add(newedumap);
					name="trainAgreement";
				}
				
			}
		}
		
	    
		
		return name;
	}
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public String gettrainResultImportDemoLoad(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList,String flag) throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String name="";
		aliasNameList.add("Evaluator ID (Not null)"); //评价者社号(必填)
		aliasNameList.add("Evaluator name"); //评价者姓名
		aliasNameList.add("Course overall satisfaction"); //课程整体满意程度
		aliasNameList.add("Courses are easy to grasp"); //课程易掌握程度
		aliasNameList.add("Course length"); //课程时间长度
		aliasNameList.add("Practical training"); //培训实用性
		if("load".equals(flag)){
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL1", "20000013");
			map.put("CELL2", "Name");
			map.put("CELL3", "5");
			map.put("CELL4", "5");
			map.put("CELL5", "5");
			map.put("CELL6", "5");
			list.add(map);
			name="trainResult_demo_ev";
		}
		return name;
	}
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public String getTeacherEvaluateInfo(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList,String flag) throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String name="";
		aliasNameList.add("Evaluator ID (Not null)"); //评价者社号(必填)
		aliasNameList.add("Evaluator name"); //评价者姓名
		aliasNameList.add("Result (from 1 to 5)");; //分数(分数范围1到5))
		if("load".equals(flag)){
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL1", "20000013");
			map.put("CELL2", "Name");
			map.put("CELL3", "5");
			list.add(map);
			name="TeacherEvaluate_demo";
		}
		return name;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public String getStudentEvaList(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList,String flag) throws SQLException {
		String name="";
		aliasNameList.add("ID(Not null)");
		aliasNameList.add("Name");
		aliasNameList.add("Examination results");
		if("load".equals(flag)){
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "20000013");
			map.put("CELL1", "Name");
			map.put("CELL2", "90");
			
			list.add(map);
			name="studentEva_demo";
		}
		
		return name;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public String getPlanCourse(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList,String flag) throws SQLException {
		String name="";
		aliasNameList.add("Course name");
		aliasNameList.add("Course date");
		aliasNameList.add("Start time");
		aliasNameList.add("End time");
		aliasNameList.add("Address");
		if("load".equals(flag)){
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "Training...");
			map.put("CELL1", "01/01/2018");
			map.put("CELL2", "08:00");
			map.put("CELL3", "10:00");
			map.put("CELL4", "Room");
			
			list.add(map);
			name="planCourse_demo";
		}
		
		return name;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getDeptTree(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String desdepart=StringUtil.checkNull(request.getParameter("DES_DEPARTMENT"));
		String str="";
		if(!"".equals(desdepart) && desdepart != null){
			String [] array=desdepart.split(",");
			for(int i=0;i<array.length;i++){
				str=str+"'"+array[i]+"',";
			}
			str=str.substring(0,str.length()-1);
		}else{
			str="''";
		}
		paramMap.put("DES_DEPARTMENT", str);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return this.eduTrainDao.getDeptTree(paramMap);
	}
	
	
}
