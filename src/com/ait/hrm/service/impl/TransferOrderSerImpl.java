package com.ait.hrm.service.impl;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.TransactionAffirmDao;
import com.ait.ess.service.InfoApplySer;
import com.ait.hrm.dao.EmpInfoDao;
import com.ait.hrm.dao.TransferOrderDao;
import com.ait.hrm.service.TransferOrderSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.PostDao;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 额
 * @fileName: TransferOrderSerImpl.java
 * @Description:
 * @Create date: 2012-2-23 下午10:33:50
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class TransferOrderSerImpl implements TransferOrderSer {

	Logger logger = Logger.getLogger(TransferOrderSerImpl.class);

	@Autowired
	private TransferOrderDao transferOrderDao;
	@Autowired
	private EmpInfoDao empInfoDao;
	@Autowired
	private TransactionAffirmDao transactionAffirmDao;
	@Autowired
	private PostDao postDao;	
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	@Autowired
	private InfoApplySer  infoApplySer ;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getTransferOrderList(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		param.put("adminid", admin.getAdminID());

		String emplist = request.getParameter("emplist");
		if (emplist != null && !emplist.equals("")) {
			String emp_list[] = emplist.split(",");
			param.put("emp_list", emp_list);

			param.remove("deptid");
		}
		
		String trans = request.getParameter("pluralityend");
		if (trans != null && !trans.equals("")) {
			return this.transferOrderDao.getPluralityList(param);
		} else {
			return this.transferOrderDao.getTransferOrderList(param);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getDispatchInfo(HttpServletRequest request) {

		Map param = ObjectBindUtil.getRequestParamData(request);
		return this.transferOrderDao.getDispatchInfo(param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getValidationEmpid(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return transferOrderDao.getValidationEmpid(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getValidationIdCardNo(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return transferOrderDao.getValidationIdCardNo(paramMap);
	}

	/**
	 * 提交入职发令（save Transfer Order Hire）
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveTransferOrderHire(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int result = 0;
		try {
			Map paramMap = ObjectBindUtil.getRequestParamData(request);
			String expInsideNo = transferOrderDao.getNextExpInside();//理论上的工资信息
			String personId = transferOrderDao.getNextPersonId();//PersonId
			String userNo = transferOrderDao.getNextUserNo();//userno
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("EXP_INSIDE_NO", expInsideNo);
			paramMap.put("PERSON_ID", personId);
			paramMap.put("TYPE", "hire");
			//(08-23 页面上取消的字段)
			//paramMap.put("OUTER_WORK_YEAR", request.getParameter("OUTER_WORK_YEAR")); //外部工作年资 取消 去 通过基本信息中司外经历来计算
			//paramMap.put("BEFORE_SCHOOL", request.getParameter("BEFORE_SCHOOL"));//入职学校
			//paramMap.put("BEFORE_SUBJECT", request.getParameter("BEFORE_SUBJECT_NAME"));//入职学校
			//paramMap.put("BEFORE_DEGREE_CODE", request.getParameter("BEFORE_DEGREE_CODE"));//入职学历
			//paramMap.put("OFFICE_PHONE", request.getParameter("OFFICE_PHONE"));//办公室电话 
			//paramMap.put("LAST_DATE_STARTED", request.getParameter("LAST_DATE_STARTED"));//末次入职日期
			//paramMap.put("SOCIAL_SECURITY_START_DATE", request.getParameter("SOCIAL_SECURITY_START_DATE"));//社保开始日期
			//这页面上没有字段等待确定信息
//			paramMap.put("FINAL_SUBJECT", request.getParameter("FINAL_SUBJECT_NAME"));//最终专业
//			paramMap.put("FINAL_SCHOOL", request.getParameter("FINAL_SCHOOL"));//最终学校
//			paramMap.put("FINAL_DEGREE_CODE", request.getParameter("FINAL_DEGREE_CODE"));//最终学校
//			
//			
//			paramMap.put("SOCIAL_SECURITY_AREA", request.getParameter("SOCIAL_SECURITY_AREA"));//社会保险地
//
//			paramMap.put("HOME_PHONE", request.getParameter("HOME_PHONE"));//家庭电话

			String statusCode ="";
//			if(request.getParameter("IN_THE_DIFFERENCE").toString().equals("Y")){
//				statusCode="1373";//实习
//			}else{
//				if(request.getParameter("EMP_TYPE_CODE").toString().equals("3946")){
//					statusCode="14891";///小时工是否有状态
//				}else{
					statusCode="14891";//14891正式
//				}
//			}
			paramMap.put("STATUS_CODE", statusCode);//员工状态
			paramMap.put("DEPT_NO",request.getParameter("DEPT_NO"));

			String empID = transferOrderDao.getNextPrEmpid(paramMap).toString();//EMPID 
			paramMap.put("EMPID", empID);
			String[] paramDataL = request.getParameterValues("PRODUCT_NO");
			if (paramDataL != null) {
				paramMap.put("PART_TIME_YN", "123225");
				this.empInfoDao.deleteProductInfo(paramMap);
				for (int i = 0; i < paramDataL.length; i++) {
					paramMap.put("PRODUCT_NO", paramDataL[i]);
					this.empInfoDao.addProductInfo(paramMap);
				}
			}else{
				paramMap.put("PART_TIME_YN", "123226");
			}

			transferOrderDao.saveHireAffirmList(paramMap,null);
			
//			if (transferOrderDao.getParamInfoValue(paramMap) == 1) { //是否需要裁决
//				// 取出被发令人的决裁者信息（个人设置优先）
//				List affirmorList = transferOrderDao.getAffirmorIdListByPersonal(paramMap);
//				// 如果特殊设置不为空
//				if (affirmorList.size() != 0) {
//					for (int j = 0; j < affirmorList.size(); j++) {
//						if (j == 0) {
//							// 取当前决裁者
//							paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
//						}
//					}
//					// 保存发令信息
//					transferOrderDao.saveHireAffirmList(paramMap, affirmorList);
//				} else {
//					// 取出被发令人的决裁者信息(部门设置)
//					List affirmorListByDept = transferOrderDao
//							.getAffirmorIdListByDept(paramMap);
//					if (affirmorListByDept.size() != 0) {
//						for (int j = 0; j < affirmorListByDept.size(); j++) {
//							// 取当前决裁者
//							if (j == 0) {
//								paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorListByDept.get(j)).get("AFFIRMOR_ID"));
//							}
//						}
//						// 保存发令信息
//						transferOrderDao.saveHireAffirmList(paramMap,affirmorListByDept);
//					} else {
//						// 自定义取决裁者
//						Map flagMap = getAffiram(paramMap, expInsideNo,request, admin);
//						String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap.get("AFFIRM_FLAG").toString() : "";
//						if ("2".equals(flag)) {
//							return 2;
//						}
//						List newSaveAffirmorList = (List) paramMap.get("newSaveAffirmorList");
//						transferOrderDao.saveHireAffirmList(paramMap,newSaveAffirmorList);
//					}
//				}
//				/****保存毕业信息到临时表***/
//				int count=Integer.parseInt(request.getParameter("count"));
//				
//				for (int i = 0; i < count; i++) {
//					if(request.getParameter("INSTITUTION_NAME"+i)!=null){
//						paramMap.put("INSTITUTION_NAME", request.getParameter("INSTITUTION_NAME"+i));//学校名称
//						paramMap.put("SUBJECT_CLASSIFY", request.getParameter("SUBJECT_CLASSIFY"+i));//专业分类
//						paramMap.put("SUBJECT", request.getParameter("SUBJECT"+i));//专业
//						
//						paramMap.put("SUBJECT_CLASSIFY_TWO", request.getParameter("SUBJECT_CLASSIFY_TWO"+i));//第二专业分类
//						paramMap.put("SUBJECT_SECOND", request.getParameter("SUBJECT_SECOND"+i));//第二专业
//						paramMap.put("START_DATE", request.getParameter("START_YEAR"+i)+"-"+request.getParameter("START_MONTH"+i));//入学时间
//						paramMap.put("END_DATE", request.getParameter("END_YEAR"+i)+"-"+request.getParameter("END_MONTH"+i));//毕业时间
//						paramMap.put("DEGREE_CODE", request.getParameter("DEGREE_CODE"+i));//学历
//						//paramMap.put("EDUC_CARD_NO", request.getParameter("EDUC_CARD_NO"+i));
//						paramMap.put("SITE_PROVINCE", request.getParameter("SITE_PROVINCE"+i));//所在地省CODE
//						paramMap.put("SITE_CITY", request.getParameter("SITE_CITY"+i));//所在地市CODE
//						paramMap.put("FINAL_DEGREE_WHETHER", request.getParameter("FINAL_DEGREE_WHETHER"+i));//是否最终学历
//						
//						paramMap.put("REMARKS", request.getParameter("REMARKS"+i));//备注
//						
//						this.transferOrderDao.addEduactionInfo(paramMap);
//						if(request.getParameter("FINAL_DEGREE_WHETHER"+i).toString().equals("Y")){
//							this.transferOrderDao.updataEduaction(paramMap);//修改员工个人信息临时表最终学历信息	
//						}
//					}
//				}
//				/***END****/
//			} else {
//				DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
//				String temp_strS = DateUtil.getSysdateStr();
//				String joinCompanyDateS = ObjectUtils.toString(paramMap.get("JOIN_COMPANY_DATE"));
//				Date joinCompanyDate = dateSys.parse(joinCompanyDateS);
//				Date temp_str = dateSys.parse(temp_strS);
//				paramMap.put("USER_NO", userNo);
//
//				List roleGroupList = transferOrderDao.getRoleGroupList(paramMap);//是否赋予赋予权限组
//				if (joinCompanyDate.before(temp_str) || joinCompanyDate.equals(temp_str)) {
//					if (roleGroupList.size() > 0) {
//						transferOrderDao.saveHireEffectiveANDUserRelation(paramMap);
//					} else {
//						transferOrderDao.saveHireEffective(paramMap);
//					}
//				} else {
//					transferOrderDao.saveHire(paramMap);
//				}
//				this.saveChangeInfo(request, personId);
//				/****保存毕业信息表非临时表*****/
//				int count=Integer.parseInt(request.getParameter("count"));
//				
//				for (int i = 0; i < count; i++) {
//					if(request.getParameter("INSTITUTION_NAME"+i)!=null){
//						
//						paramMap.put("INSTITUTION_NAME", request.getParameter("INSTITUTION_NAME"+i));//学校名称
//						paramMap.put("SUBJECT_CLASSIFY", request.getParameter("SUBJECT_CLASSIFY"+i));//专业分类
//						paramMap.put("SUBJECT", request.getParameter("SUBJECT"+i));//专业
//						
//						paramMap.put("SUBJECT_CLASSIFY_TWO", request.getParameter("SUBJECT_CLASSIFY_TWO"+i));//第二专业分类
//						paramMap.put("SUBJECT_SECOND", request.getParameter("SUBJECT_SECOND"+i));//第二专业
//						paramMap.put("START_DATE", request.getParameter("START_YEAR"+i)+"-"+request.getParameter("START_MONTH"+i));//入学时间
//						paramMap.put("END_DATE", request.getParameter("END_YEAR"+i)+"-"+request.getParameter("END_MONTH"+i));//毕业时间
//						paramMap.put("DEGREE_CODE", request.getParameter("DEGREE_CODE"+i));//学历
//						//paramMap.put("EDUC_CARD_NO", request.getParameter("EDUC_CARD_NO"+i));
//						paramMap.put("SITE_PROVINCE", request.getParameter("SITE_PROVINCE"+i));//所在地省CODE
//						paramMap.put("SITE_CITY", request.getParameter("SITE_CITY"+i));//所在地市CODE
//						paramMap.put("FINAL_DEGREE_WHETHER", request.getParameter("FINAL_DEGREE_WHETHER"+i));//是否最终学历
//						
//						paramMap.put("REMARKS", request.getParameter("REMARKS"+i));//备注
//						this.empInfoDao.addEduactionInfo(paramMap);
//						if(request.getParameter("FINAL_DEGREE_WHETHER"+i).toString().equals("Y")){
//							this.empInfoDao.updataEduaction(paramMap);//修改员工个人信息表最终学历信息
//						}
//					}
//				}
//				/****END*****/
//			}
			
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	
	/**
	 * 新入职发令时更新HR_CHANGE_STATUS表
	 */
	public int saveChangeInfo(HttpServletRequest request , String personId){
		String beginDate = request.getParameter("START_DATE");
		LinkedHashMap param = new LinkedHashMap ();
		param.put("START_DATE", beginDate);
		param.put("personId", personId);
		try {
			transferOrderDao.savePersonChangeInfo(param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 提交调动发令（save Transfer Order Upgrade）
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map saveTransferOrderUpgrade(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//saveType用于区分发令新增还是修改
		String saveType = request.getParameter("SAVETYPE");
		//mgtType用于区分正规职发令还是临时职发令
		String mgtType = request.getParameter("MGT_TYPE");
		Map paramMap  = ObjectBindUtil.getRequestParamData(request, "seach_");
		Map map = this.checkSaveTransferOrderUpgrade(request);
		if(!map.get("result").equals("OK")){
			return map;
		}
		//验证通过
		String check[] = request.getParameterValues("trCKB");
		int result = 0;
		try {
			Map paramValueMap = new LinkedHashMap();
			paramValueMap.put("CPNY_ID", admin.getCpnyId());
			paramValueMap.put("TYPE", "upGrade");
			// 判断是否需要决裁1为开，0为关
			if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {			
//				for (int i = 0; i < check.length; i++) {
//					Map trMap = new LinkedHashMap();
//					trMap.put("EMPID", check[i]);
//					trMap.put("CPNY_ID", admin.getCpnyId());
//					trMap.put("PERSON_ID",request.getParameter("PERSON_ID_"+ check[i]));
//					trMap.put("TRANS_NO",1365);
//					trMap.put("TRANS_CODE",request.getParameter("TRANS_CODE"));
//					trMap.put("DEPTNO", request.getParameter("DEPTMENTNO_"+ check[i]));
//					trMap.put("START_DATE",request.getParameter("START_DATE_" + check[i]));	
//					trMap.put("TRANSFER_ORDER_REASON",request.getParameter("TRANSFER_ORDER_REASON_" + check[i]));
//					trMap.put("ACTIVITY", 0); //未生效
//					trMap.put("CREATED_BY", admin.getEmpID());
//					trMap.put("MGT_TYPE", mgtType);	//TEMPEMP：临时职，REGUEMP：正规职	
//					
//					String transCode = request.getParameter("TRANS_CODE");				
//					if("15861".equals(transCode)){
//						//发令类型： 部门变更 
//						trMap.put("NEW_DEPTNO", request.getParameter("NEW_DEPTMENTNO_"+ check[i]));
//					}else if("278706".equals(transCode)){
//						//发令类型： 人员类型变更
//						trMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE_"+ check[i]));
//						trMap.put("NEW_EMP_TYPE_CODE", request.getParameter("NEW_EMP_TYPE_CODE_"+ check[i]));
//					}else if("278705".equals(transCode)){
//						//发令类型： 班号变更
//					}else if("278704".equals(transCode)){
//						//发令类型： 级号变更
//					}
//					
//					String expInsideNo = transferOrderDao.getNextExpInside();
//					trMap.put("EXP_INSIDE_NO", expInsideNo);				
	
//					// 取出被发令人的决裁者信息（个人设置优先）
//					List affirmorList = transferOrderDao.getAffirmorIdListByPersonal(paramMap);
//					// 如果特殊设置不为空
//					if (affirmorList.size() != 0) {
//						for (int j = 0; j < affirmorList.size(); j++) {
//							// 取当前决裁者
//							if (j == 0) {
//								paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
//							}
//						}
//						// 保存发令信息
//						transferOrderDao.saveExperienceForRemoveAndAffirmList(paramMap, affirmorList);
//					} else {
//						// 取出被发令人的决裁者信息(部门设置)
//						List affirmorListByDept = transferOrderDao.getAffirmorIdListByDept(paramMap);
//						if (affirmorListByDept.size() != 0) {
//							for (int j = 0; j < affirmorListByDept.size(); j++) {
//								// 取当前决裁者
//								if (j == 0) {
//									paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorListByDept.get(j)).get("AFFIRMOR_ID"));
//								}
//							}
//							// 保存发令信息
//							transferOrderDao.saveExperienceForRemoveAndAffirmList(paramMap, affirmorListByDept);
//						} else {
//							// 自定义取决裁者
//							Map flagMap = getAffiram(paramMap, expInsideNo,request, admin);
//							String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap.get("AFFIRM_FLAG").toString() : "";
//							if ("2".equals(flag)) {
//								return 2;
//							}
//							List newSaveAffirmorList = (List) paramMap.get("newSaveAffirmorList");
//							transferOrderDao.saveExperienceForRemoveAndAffirmList(paramMap, newSaveAffirmorList);
//						}
//					}
//				}			
			}else {
				//不需要决裁时；
				for (int i = 0; i < check.length; i++) {
					Map trMap = new LinkedHashMap();
					trMap.put("EMPID", check[i]);
					trMap.put("CPNY_ID", admin.getCpnyId());
					trMap.put("PERSON_ID",request.getParameter("PERSON_ID_"+ check[i]));
					trMap.put("TRANS_NO",1365);
					trMap.put("START_DATE",request.getParameter("START_DATE_" + check[i]));
					trMap.put("TRANSFER_ORDER_REASON",request.getParameter("TRANSFER_ORDER_REASON_" + check[i]));
					trMap.put("ACTIVITY", 0);//未生效	
					trMap.put("CREATED_BY", admin.getEmpID());
					trMap.put("CURRENT_AFFIRM_ID", null);
					trMap.put("OLD_DEPTNO", null);
					trMap.put("NEW_DEPTNO", null);
					trMap.put("OLD_EMP_TYPE_CODE", null);
					trMap.put("NEW_EMP_TYPE_CODE", null);
					trMap.put("OLD_SHIFT_NO", null);
					trMap.put("NEW_SHIFT_NO", null);
					trMap.put("OLD_POSITION_NO", null);
					trMap.put("NEW_POSITION_NO", null);
					trMap.put("OLD_BASE_PAY", null);
					trMap.put("OLD_VARB_PAY", null);
					trMap.put("OLD_PAY_GRADE", null);
					trMap.put("OLD_PAY_STEP", null);
					trMap.put("OLD_ANSAL", null);
					trMap.put("NEW_BASE_PAY", null);
					trMap.put("NEW_VARB_PAY", null);
					trMap.put("NEW_PAY_GRADE", null);
					trMap.put("NEW_PAY_STEP", null);
					trMap.put("NEW_ANSAL", null);
					trMap.put("OLD_ID_CARD_NO", null);
					trMap.put("NEW_ID_CARD_NO", null);
					trMap.put("OLD_JOB_TITLE_CD", null);
					trMap.put("NEW_JOB_TITLE_CD", null);
					trMap.put("MGT_TYPE", mgtType);	//TEMPEMP：临时职，REGUEMP：正规职				
					
					String transCode = request.getParameter("TRANS_CODE");	
					trMap.put("TRANS_CODE",transCode);
					trMap.put("defaultCpny",admin.getCpnyId());
					trMap.put("interLanguage",paramMap.get("interLanguage"));
					trMap.put("USER_NO",admin.getUserNo());
					List hrEmpList = transferOrderDao.getViewTempEmpList(trMap);
					Map personInfo = ((LinkedHashMap)hrEmpList.get(0));	
					if("15861".equals(transCode)){
						//发令类型： 部门变更 
						trMap.put("OLD_DEPTNO", personInfo.get("DEPTNO"));
						trMap.put("NEW_DEPTNO", request.getParameter("NEW_DEPTMENTNO_"+ check[i]));						
					}else if("278706".equals(transCode)){
						//发令类型： 人员类型变更						
						trMap.put("OLD_EMP_TYPE_CODE", personInfo.get("EMP_TYPE_CODE"));
						trMap.put("NEW_EMP_TYPE_CODE", request.getParameter("NEW_EMP_TYPE_CODE_"+ check[i]));
					}else if("278705".equals(transCode)){
						//发令类型： 班号变更
						trMap.put("OLD_SHIFT_NO", personInfo.get("SHIFT_NO"));
						trMap.put("NEW_SHIFT_NO", request.getParameter("NEW_SHIFT_NO_"+ check[i]));
					}else if("278720".equals(transCode)){
						//发令类型： 班号变更
						trMap.put("OLD_POSITION_NO", personInfo.get("POSITION_NO"));
						trMap.put("NEW_POSITION_NO", request.getParameter("NEW_POSITION_NO_"+ check[i]));
					}else if("278704".equals(transCode)){
						//发令类型： 级号变更
						trMap.put("OLD_ANSAL", personInfo.get("ANSAL"));
						trMap.put("OLD_BASE_PAY", personInfo.get("BASE_PAY"));
						trMap.put("OLD_VARB_PAY", personInfo.get("VARB_PAY"));
						trMap.put("OLD_PAY_GRADE", personInfo.get("PAY_GRADE"));
						trMap.put("OLD_PAY_STEP", personInfo.get("PAY_STEP"));
						trMap.put("NEW_ANSAL", request.getParameter("NEW_ANSAL_"+ check[i]));
						trMap.put("NEW_BASE_PAY", request.getParameter("NEW_BASE_PAY_"+ check[i]));
						trMap.put("NEW_VARB_PAY", request.getParameter("NEW_VARB_PAY_"+ check[i]));
						trMap.put("NEW_PAY_GRADE", request.getParameter("NEW_PAY_GRADE_"+ check[i]));
						trMap.put("NEW_PAY_STEP", request.getParameter("NEW_PAY_STEP_"+ check[i]));
					}else if("14013588".equals(transCode)){
						//发令类型： ID卡号变更 
						trMap.put("OLD_ID_CARD_NO", personInfo.get("ID_CARD_NO"));
						trMap.put("NEW_ID_CARD_NO", request.getParameter("NEW_ID_CARD_NO_"+ check[i]));						
					}else if("14013616".equals(transCode)){
						//发令类型： 职务变更 
						trMap.put("OLD_JOB_TITLE_CD", personInfo.get("JOB_TITLE_CD"));
						trMap.put("NEW_JOB_TITLE_CD", request.getParameter("NEW_JOB_TITLE_CD_"+ check[i]));						
					}
					if(saveType.equals("ADD")){
						String expInsideNo = transferOrderDao.getNextExpInside();
						trMap.put("EXP_INSIDE_NO", expInsideNo);
						transferOrderDao.saveExperienceForTransfer(trMap);
					}else{
						trMap.put("UPDATED_BY", admin.getEmpID());
						trMap.put("EXP_INSIDE_NO", request.getParameter("EXP_INSIDE_NO_" + check[i]));
						transferOrderDao.updateExperienceForTransfer(trMap);
					}						
				}
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 提交人员类型变更调动发令（save Transfer Order Upgrade）
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map saveEmpTypeTransferOrderUpgrade(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap  = ObjectBindUtil.getRequestParamData(request, "seach_");
		Map map = this.checkSaveEmpTypeTransferOrderUpgrade(request);
		if(!map.get("result").equals("OK")){
			if(map.get("result").equals("OK")) map.put("RET", 1); else map.put("RET", 0);
			return map;
		}
		//验证通过
		String check[] = request.getParameterValues("trCKB");
		int result = 0;
		try {
			Map paramValueMap = new LinkedHashMap();
			paramValueMap.put("CPNY_ID", admin.getCpnyId());
			paramValueMap.put("TYPE", "upGrade");
			// 判断是否需要决裁1为开，0为关
			if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {			
			}else {
				//不需要决裁时；
				for (int i = 0; i < check.length; i++) {
					Map trMap = new LinkedHashMap();
					trMap.put("EMPID", check[i]);
					trMap.put("CPNY_ID", admin.getCpnyId());
					trMap.put("PERSON_ID",request.getParameter("PERSON_ID_"+ check[i]));
					trMap.put("TRANS_NO",1365);
					trMap.put("START_DATE",request.getParameter("START_DATE_" + check[i]));
					trMap.put("TRANSFER_ORDER_REASON",request.getParameter("TRANSFER_ORDER_REASON_" + check[i]));
					trMap.put("ACTIVITY", 0);//0-未生效，1-已生效	
					trMap.put("STATE", 0);//0-未生效，1-已生效	
					trMap.put("CREATED_BY", admin.getEmpID());
					trMap.put("CURRENT_AFFIRM_ID", null);
					trMap.put("OLD_EMP_TYPE_CODE", null);
					trMap.put("NEW_EMP_TYPE_CODE", null);
					
					String transCode = request.getParameter("TRANS_CODE");	
					trMap.put("TRANS_CODE",transCode);
					trMap.put("defaultCpny",admin.getCpnyId());
					trMap.put("interLanguage",paramMap.get("interLanguage"));
					trMap.put("USER_NO",admin.getUserNo());
					List hrEmpList = transferOrderDao.getViewTempEmpList(trMap);
					Map personInfo = ((LinkedHashMap)hrEmpList.get(0));	
					
					//发令类型： 人员类型变更						
					trMap.put("OLD_EMP_TYPE_CODE", personInfo.get("EMP_TYPE_CODE"));
					trMap.put("NEW_EMP_TYPE_CODE", request.getParameter("NEW_EMP_TYPE_CODE_"+ check[i]));
										
					String expInsideNo = transferOrderDao.getNextExpInside();
					trMap.put("EXP_INSIDE_NO", expInsideNo);
					transferOrderDao.saveExperienceForEmpTypeTransfer(trMap);					
				}
			}
			paramValueMap.put("CREATED_BY", admin.getEmpID());
			paramValueMap.put("TRANS_CODE", 278706);
			map = transferOrderDao.callActiveEmpTypeTransfer(paramValueMap);//生效处理			
			String msg =  ObjectUtils.toString(map.get("MESSAGE")) ;
			if(msg.equals("OK")) map.put("RET", 1); else map.put("RET", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("RET", 0);
		}
		return map;
	}
	
	/**
	 * 临时职人员信息变更发令check（用于部门、人员类型、班号，级号的变更）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map checkSaveTransferOrderUpgrade(HttpServletRequest request) {
		Map map = new LinkedHashMap();
		map.put("result","");
		int checkInprogressOrder=0;
		String checkPersonId = "";
		String checkDeptNo = "";
		String checkDeptNoUseYn = "";
		String checkShiftNo = "";
		String checkPayGrade = "";
		String checkPositionNo = "";
		String checkEmpTypeCode= "";
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("trCKB");
		String transCode=request.getParameter("TRANS_CODE");
		Map paramMap  = ObjectBindUtil.getRequestParamData(request, "seach_");
		//取当前申请的全部personId
		String personIds="";
		for(int i=0;i<check.length;i++){
			personIds += request.getParameter("PERSON_ID_" + check[i])+",";
		}
		int personIdNum=personIds.lastIndexOf(",");
		paramMap.put("PERSONIDS", personIds.substring(0,personIdNum));
		//.验证此人是否有未生效的调动发令
		paramMap.put("TRANS_NO", 1365);
		String saveType = request.getParameter("SAVETYPE");
		if(saveType.equals("ADD")){
			checkInprogressOrder += this.transferOrderDao.checkSaveTransferOrderUpgradeInprogres(paramMap);
		}
		if(checkInprogressOrder>0){			
			// 此人已有未生效发令,请生效后再进行发令
			map.put("result","NG");
			map.put("statusCode", "300");
			map.put("message", "存在未生效发令,请生效后再进行发令！");
			return map;
		}
		
		for (int i = 0; i < check.length; i++) {
			Map personMap = new LinkedHashMap();
			personMap.put("PERSON_ID",request.getParameter("PERSON_ID_" + check[i]));
			personMap.put("START_DATE",request.getParameter("START_DATE_" + check[i]));
			//.验证一下所有人的发令日期是否早于其入职日期
			int resultPersonId = transferOrderDao.checkPersonId(personMap);
			if (resultPersonId > 0 && resultPersonId != 10000) {
				checkPersonId += check[i];
			}
			personMap.put("defaultCpny",admin.getCpnyId());
			personMap.put("interLanguage",paramMap.get("interLanguage"));
			personMap.put("EMPID",check[i]);
			personMap.put("USER_NO",admin.getUserNo());
			List hrEmpList = transferOrderDao.getViewTempEmpList(personMap);
			Map personInfo = ((LinkedHashMap)hrEmpList.get(0));			
			if(transCode.equals("15861")){
				//验证部门是否变更 				
				String newDeptNo=request.getParameter("NEW_DEPTMENTNO_" + check[i]);
				if(newDeptNo.equals(personInfo.get("DEPTNO"))){
					checkDeptNo += check[i];
				}
				Map pMap = new LinkedHashMap();
				pMap.put("DEPTNO", newDeptNo);
				int validCnt = transferOrderDao.getValidDeptNoCnt(pMap);
				if(validCnt==0){
					checkDeptNoUseYn += check[i];
				}
			}else if(transCode.equals("278706")){
				//验证人员类型是否变更 				
				String newEmpTypeCode=request.getParameter("NEW_EMP_TYPE_CODE_" + check[i]);
				if(newEmpTypeCode.equals(personInfo.get("EMP_TYPE_CODE"))){
					checkEmpTypeCode += check[i];
				}
			}else if(transCode.equals("278705")){
				//验证班号是否变更 
				String newShiftNo=request.getParameter("NEW_SHIFT_NO_" + check[i]) != null ? request.getParameter("NEW_SHIFT_NO_" + check[i]) : "";
				if(newShiftNo.equals(personInfo.get("SHIFT_NO"))){
					checkShiftNo += check[i];
				}
			}else if(transCode.equals("278720")){
				//验证班号是否变更 
				String newPositionNo=request.getParameter("NEW_POSITION_NO_" + check[i]) != null ? request.getParameter("NEW_POSITION_NO_" + check[i]) : "";
				if(newPositionNo.equals(personInfo.get("POSITION_NO"))){
					checkPositionNo += check[i];
				}
			}else if(transCode.equals("278704")){
				//验证级号是否变更 
				//String newAnsal   =request.getParameter("NEW_ANSAL_" + check[i]) != null ? request.getParameter("NEW_ANSAL_" + check[i]) : "0";
				String newBasePay =request.getParameter("NEW_BASE_PAY_" + check[i]) != null ? request.getParameter("NEW_BASE_PAY_" + check[i]) : "0";
				String newVarbPay =request.getParameter("NEW_VARB_PAY_" + check[i]) != null ? request.getParameter("NEW_VARB_PAY_" + check[i]) : "0";
				String newPayGrade=request.getParameter("NEW_PAY_GRADE_" + check[i]) != null ? request.getParameter("NEW_PAY_GRADE_" + check[i]) : "";
				String newPayStep =request.getParameter("NEW_PAY_STEP_" + check[i]) != null ? request.getParameter("NEW_PAY_STEP_" + check[i]) : "";
				if(//newAnsal.equals(personInfo.get("ANSAL"))
					newBasePay.equals(personInfo.get("BASE_PAY"))
					&& newVarbPay.equals(personInfo.get("VARB_PAY"))
					&& newPayGrade.equals(personInfo.get("PAY_GRADE"))
					&& newPayStep.equals(personInfo.get("PAY_STEP"))){
					checkPayGrade += check[i];
				}
			}
		}		
		if((checkPersonId+checkDeptNo+checkDeptNoUseYn+checkEmpTypeCode+checkShiftNo+checkPayGrade+checkPositionNo).length()>0){
			map.put("result","NG");
			map.put("statusCode", "300");	
			if(checkPersonId.length()>0){
				//如果有人的发令日期是否早于其入职日期，给出提示					
				map.put("message",TipMessage.getTipMessage("hr.alert.message.this_empid",request)
					+ checkPersonId + TipMessage.getTipMessage("hr.alert.message.data_question_fail",request));
			}else if(checkDeptNo.length()>0){		
				map.put("message","此社编：" + checkDeptNo + "的部门未发生变更！");
			}else if(checkDeptNoUseYn.length()>0){		
				map.put("message", "变更的部门已停用！");
			}else if(checkEmpTypeCode.length()>0){			
				map.put("message","此社编："+ checkEmpTypeCode + "的人员类型未变更！");
			}else if(checkShiftNo.length()>0){
				map.put("message","此社编：" + checkShiftNo + "的班号未发生变更！");
			}else if(checkPayGrade.length()>0){	
				map.put("message","此社编：" + checkPayGrade + "的级号未发生变更！");
			}else if(checkPositionNo.length()>0){	
				map.put("message","此社编：" + checkPositionNo + "的级号未发生变更！");
			}
		}else{
			//发令成功
			map.put("result","OK");
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("hr.alert.message.dekreti_success", request));
		}
		return map;
	}
	/**
	 * 人员类型变更发令check
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map checkSaveEmpTypeTransferOrderUpgrade(HttpServletRequest request) {
		Map map = new LinkedHashMap();
		map.put("result","");
		int checkInprogressOrder=0;
		String checkPersonId = "";		
		String checkEmpTypeCode = "";
		String checkTempEmpType = "";
		String errMsg = "";
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("trCKB");
		String transCode=request.getParameter("TRANS_CODE");
		Map paramMap  = ObjectBindUtil.getRequestParamData(request, "seach_");
		//取当前申请的全部personId
		String personIds="";
		for(int i=0;i<check.length;i++){
			personIds += request.getParameter("PERSON_ID_" + check[i])+",";
		}
		int personIdNum=personIds.lastIndexOf(",");
		paramMap.put("PERSONIDS", personIds.substring(0,personIdNum));
		//.验证此人是否有未生效的调动发令
		paramMap.put("TRANS_NO", 1365);
		checkInprogressOrder += this.transferOrderDao.checkSaveTransferOrderUpgradeInprogres(paramMap);

		if(checkInprogressOrder>0){			
			// 此人已有未生效发令,请生效后再进行发令
			map.put("result","NG");
			map.put("statusCode", "300");
			map.put("message", "存在未生效发令,请生效后再进行发令！");
			return map;
		}
		
		for (int i = 0; i < check.length; i++) {
			Map personMap = new LinkedHashMap();
			personMap.put("PERSON_ID",request.getParameter("PERSON_ID_" + check[i]));
			personMap.put("START_DATE",request.getParameter("START_DATE_" + check[i]));
			//.验证一下所有人的发令日期是否早于其入职日期
			int resultPersonId = transferOrderDao.checkPersonId(personMap);
			if (resultPersonId > 0 && resultPersonId != 10000) {
				checkPersonId += check[i];
			}
			personMap.put("defaultCpny",admin.getCpnyId());
			personMap.put("interLanguage",paramMap.get("interLanguage"));
			personMap.put("EMPID",check[i]);
			personMap.put("USER_NO",admin.getUserNo());
			List hrEmpList = transferOrderDao.getViewTempEmpList(personMap);
			Map personInfo = ((LinkedHashMap)hrEmpList.get(0));			
			
			//验证人员类型是否变更 				
			String newEmpTypeCode=request.getParameter("NEW_EMP_TYPE_CODE_" + check[i]);
			if(newEmpTypeCode.equals(personInfo.get("EMP_TYPE_CODE"))){
				checkEmpTypeCode += check[i];
			}
			personMap.put("EMP_TYPE_CODE", personInfo.get("EMP_TYPE_CODE"));
			personMap.put("NEW_EMP_TYPE_CODE", newEmpTypeCode);
			int validTempEmpTypeCnt = transferOrderDao.getValidTempEmpTypeTrCnt(personMap);
			String checkTempEmpTypeCreatedBy = personInfo.get("CREATED_BY").toString();
			if( validTempEmpTypeCnt == 0 && !checkTempEmpTypeCreatedBy.equals("GERP")){
				checkTempEmpType += check[i];
			}			
		}		
		if((checkPersonId+checkEmpTypeCode+checkTempEmpType).length()>0){
			map.put("result","NG");
			map.put("statusCode", "300");	
			if(checkPersonId.length()>0){
				//如果有人的发令日期是否早于其入职日期，给出提示					
				errMsg += TipMessage.getTipMessage("hr.alert.message.this_empid",request)
					+ checkPersonId + TipMessage.getTipMessage("hr.alert.message.data_question_fail",request);
			}else if(checkEmpTypeCode.length()>0){	
				errMsg += "社编：" + checkEmpTypeCode + "的人员类型未发生变更！";
			}else if(checkTempEmpType.length()>0 ){	
				errMsg += "社编：" + checkTempEmpType + "临时职不可以变更为管理职！";
			}
			map.put("message",errMsg);
		}else{
			//发令成功
			map.put("result","OK");
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("hr.alert.message.dekreti_success", request));
		}
		return map;
	}
	public int checkSaveTransferOrderUpgrade1(Object obj) {
		int result = 0;
		LinkedHashMap paramMap1 =(LinkedHashMap)obj;
		//String check[] = request.getParameterValues("c1");
		
	
		paramMap1.put("TRANS_NO", paramMap1.get("TRANSNO"));
		paramMap1.put("PERSON_ID", paramMap1.get("personid"));
		//for (int i = 0; i < check.length; i++) {
			//paramMap.put("PERSON_ID",request.getParameter("PERSON_ID_" + check[i]));
			result = this.transferOrderDao.checkSaveTransferOrderUpgrade1(paramMap1);
		//}
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String checkSaveTransfer(HttpServletRequest request) {
		String empid = "";
		int result = 0;
		String check[] = request.getParameterValues("c1");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			empid = "";
			paramMap.put("PERSON_ID",request.getParameter("PERSON_ID_" + check[i]));
			paramMap.put("FUNCTION_DATE",request.getParameter("FUNCTION_DATE_" + check[i]));
			result += this.transferOrderDao.checkSaveTransfer(paramMap);
			if (result > 0) {
				empid = check[i];
				return empid;
			}
		}
		return empid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String checkSaveTransferPormote(HttpServletRequest request) {
		String empid = "";
		int result = 0;
		String check[] = request.getParameterValues("d1");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			empid = "";
			paramMap.put("PERSON_ID",request.getParameter("PERSON_ID_" + check[i]));
			paramMap.put("FUNCTION_DATE",request.getParameter("START_DATE_" + check[i]));
			result += this.transferOrderDao.checkSaveTransfer(paramMap);
			if (result > 0) {
				empid = check[i];
				return empid;
			}
		}
		return empid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int checkSaveTransferNormal(HttpServletRequest request) {

		int result = 0;
		String check[] = request.getParameterValues("d1");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			paramMap.put("PERSON_ID",
					request.getParameter("PERSON_ID_" + check[i]));
			result += this.transferOrderDao.checkSaveTransferNormal(paramMap);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int checkSaveTransferPromote(HttpServletRequest request) {

		int result = 0;
		String check[] = request.getParameterValues("d1");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			paramMap.put("PERSON_ID",request.getParameter("PERSON_ID_" + check[i]));
			paramMap.put("START_DATE",request.getParameter("START_DATE_" + check[i]));
			result += this.transferOrderDao.checkSaveTransferPromote(paramMap);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int checkSaveResignation(HttpServletRequest request) {

		int result = 0;
		String check[] = request.getParameterValues("resignCKB");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			paramMap.put("PERSON_ID",request.getParameter("PERSON_ID_" + check[i]));
			result += this.transferOrderDao.checkSaveResignation(paramMap);
		}
		return result;
	}

	/**
	 * 提交转正发令（save Transfer Order Upgrade）
	 * 
	 * @param request
	 * @return
	 * @throws result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveTransferNormal(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("d1");
		int result = 0;
		try {
			Map paramValueMap = new LinkedHashMap();
			paramValueMap.put("CPNY_ID", admin.getCpnyId());
			paramValueMap.put("TYPE", "transferNormal");
			// 判断是否需要决裁1为开，0为关
			if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {
				// 判断是否有特殊设置
				for (int i = 0; i < check.length; i++) {
					Map paramMap = new LinkedHashMap();
					paramMap.put("EMPID", check[i]);
					paramMap.put("CPNY_ID", admin.getCpnyId());
					paramMap.put("PERSON_ID",request.getParameter("PERSON_ID_" + check[i]));
					paramMap.put("START_DATE",request.getParameter("START_DATE_" + check[i]));
					paramMap.put("TRANS_CODE",request.getParameter("BECOME_TYPE_" + check[i]));
					paramMap.put("REMARK",request.getParameter("REMARK_" + check[i]));
					paramMap.put("EMP_TYPE_CODE",request.getParameter("EMP_TYPE_CODE_" + check[i]));
					paramMap.put("CREATED_BY", admin.getPersonId());
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("TYPE", "transferNormal");
					paramMap.put("ACTIVITY", 0);

					// 取出被发令人的决裁者信息（个人设置优先）
					List affirmorList = transferOrderDao
							.getAffirmorIdListByPersonal(paramMap);
					// 如果特殊设置不为空
					if (affirmorList.size() != 0) {
						for (int j = 0; j < affirmorList.size(); j++) {
							// 取当前决裁者
							if (j == 0) {
								paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
							}
						}
						// 保存发令信息
						transferOrderDao.saveExperienceForTransNormalAndAffirmList(paramMap, affirmorList);
					} else {
						// 取出被发令人的决裁者信息(部门设置)
						List affirmorListByDept = transferOrderDao.getAffirmorIdListByDept(paramMap);
						if (affirmorListByDept.size() != 0) {
							for (int j = 0; j < affirmorListByDept.size(); j++) {
								// 取当前决裁者
								if (j == 0) {
									paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorListByDept.get(j)).get("AFFIRMOR_ID"));
								}
							}
							// 保存发令信息
							transferOrderDao.saveExperienceForTransNormalAndAffirmList(paramMap, affirmorListByDept);
						} else {
							// 自定义取决裁者
							Map flagMap = getAffiram(paramMap, expInsideNo,request, admin);
							String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap.get("AFFIRM_FLAG").toString() : "";

							if ("2".equals(flag)) {
								return 2;
							}
							List newSaveAffirmorList = (List) paramMap.get("newSaveAffirmorList");
							transferOrderDao.saveExperienceForTransNormalAndAffirmList(paramMap, newSaveAffirmorList);
						}
					}

				}
			} else {
				for (int i = 0; i < check.length; i++) {
					Map paramMap = new LinkedHashMap();
					paramMap.put("EMPID", check[i]);
					paramMap.put("CPNY_ID", admin.getCpnyId());
					paramMap.put("PERSON_ID",request.getParameter("PERSON_ID_" + check[i]));
					paramMap.put("START_DATE",request.getParameter("START_DATE_" + check[i]));
					paramMap.put("TRANS_CODE",request.getParameter("BECOME_TYPE_" + check[i]));
					paramMap.put("REMARK",request.getParameter("REMARK_" + check[i]));
					paramMap.put("EMP_TYPE_CODE",request.getParameter("EMP_TYPE_CODE_" + check[i]));
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("TYPE", "transferNormal");

					// 判断是否需要修改hr_employee表中的emp_type_code
					int returnresult = transferOrderDao.checkNeedChangeHeEmptypeCode(paramMap);
					if (returnresult < 0) {
						paramMap.put("EMP_TYPE_CODE",request.getParameter("EMP_TYPE_CODE_R_"+ check[i]));
					}

					// 如果转正类型是实习转试用 那么员工状态就改为试用 code=1374
					if (paramMap.get("TRANS_CODE").equals("4085")) {
						paramMap.put("STATUS_CODE", "1374");
					} else {
						paramMap.put("STATUS_CODE", "14891");
					}

					DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
					String temp_strS = DateUtil.getSysdateStr();
					String joinCompanyDateS = request.getParameter("START_DATE_" + check[i]);
					Date joinCompanyDate = dateSys.parse(joinCompanyDateS);
					Date temp_str = dateSys.parse(temp_strS);
					if (joinCompanyDate.before(temp_str) || joinCompanyDate.equals(temp_str)) {
						paramMap.put("ACTIVITY", 1);
						if (paramMap.get("TRANS_CODE").equals("1678")) {
							transferOrderDao.saveExperienceForTransNormalAndUpHrEmployeeAndUpDateStarted(paramMap);
						} else {
							transferOrderDao.saveExperienceForTransNormalAndUpHrEmployee(paramMap);
							// transferOrderDao.updateHrEmployeeForTransferNormal(paramMap);
						}
					} else {
						paramMap.put("ACTIVITY", 0);
						transferOrderDao.saveExperienceForTransNormal(paramMap);
					}
					//如果为法人为C04，劳务转正时，修改员工职级1级为4级，并修改入职日期
					if(admin.getCpnyId().toString().equals("C04")&& paramMap.get("TRANS_CODE").toString().equals("21368") && paramMap.get("EMP_TYPE_CODE").toString().equals("1369")){
						transferOrderDao.specialUpdateForC01(paramMap);
					}
				}
				this.saveAndUpdatePersonStatus(request);
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	
	/**
	 * 转正发令时将更新hr_change_status表
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void saveAndUpdatePersonStatus(HttpServletRequest request){
		String check[] = request.getParameterValues("d1");
		for(int i =0 , len = check.length; i < len ; i++ ){
			Map paramMap = new LinkedHashMap();
			paramMap.put("PERSON_ID",request.getParameter("PERSON_ID_" + check[i]));
			paramMap.put("START_DATE",request.getParameter("START_DATE_" + check[i]));
			transferOrderDao.savePersonStatus((LinkedHashMap) paramMap);
			transferOrderDao.updatePersonStatus((LinkedHashMap) paramMap);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getDispatchForSearch(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		param.put("EFFECTTRANS", 2);
		return this.transferOrderDao.getDispatchForSearch(param,
				Integer.parseInt(request.getParameter("page").toString()),
				Integer.parseInt(request.getParameter("pagesize").toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getHortationForSearch(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		param.put("EFFECTTRANS", 2);
		return this.transferOrderDao.getHortationForSearch(param,
				Integer.parseInt(request.getParameter("page").toString()),
				Integer.parseInt(request.getParameter("pagesize").toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getPluralityForSearch(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		param.put("EFFECTTRANS", 2);
		return this.transferOrderDao.getPluralityForSearch(param,
				Integer.parseInt(request.getParameter("page").toString()),
				Integer.parseInt(request.getParameter("pagesize").toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getPunishMentForSearch(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		param.put("EFFECTTRANS", 2);
		return this.transferOrderDao.getPunishMentForSearch(param,
				Integer.parseInt(request.getParameter("page").toString()),
				Integer.parseInt(request.getParameter("pagesize").toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getResignForSearch(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		param.put("EFFECTTRANS", 2);
		return this.transferOrderDao.getResignForSearch(param,
				Integer.parseInt(request.getParameter("page").toString()),
				Integer.parseInt(request.getParameter("pagesize").toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getSuspendForSearch(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		param.put("SUSPEND", "trans");
		param.put("EFFECTTRANS", 2);
		return this.transferOrderDao.getSuspendForSearch(param,
				Integer.parseInt(request.getParameter("page").toString()),
				Integer.parseInt(request.getParameter("pagesize").toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getTransferActBusinessForSearch(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		param.put("EFFECTTRANS", 2);
		return this.transferOrderDao.getTransferActBusinessForSearch(param,
				Integer.parseInt(request.getParameter("page").toString()),
				Integer.parseInt(request.getParameter("pagesize").toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getTransferNormalForSearch(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		param.put("TRANSPROBATION", "trans");
		param.put("DATELEFT", "aa");
		param.put("EFFECTTRANS", 2);
		return this.transferOrderDao.getTransferNormalForSearch(param,
				Integer.parseInt(request.getParameter("page").toString()),
				Integer.parseInt(request.getParameter("pagesize").toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getTransferPostForSearch(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		param.put("TransferPost", "trans");
		param.put("EFFECTTRANS", 2);
		return this.transferOrderDao.getTransferPostForSearch(param,
				Integer.parseInt(request.getParameter("page").toString()),
				Integer.parseInt(request.getParameter("pagesize").toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getTransferPromoteForSearch(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		param.put("PROMOTE", "trans");
		param.put("EFFECTTRANS", 2);
		return this.transferOrderDao.getTransferPromoteForSearch(param,
				Integer.parseInt(request.getParameter("page").toString()),
				Integer.parseInt(request.getParameter("pagesize").toString()));
	}

	@SuppressWarnings("unchecked")
	public List getViewUpgradeList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getViewUpgradeList(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getViewUpgradeList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 获取参保人员个数（get Insurance Personnel Cnt）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getViewUpgradeCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);
		retrunInt = transferOrderDao.getViewUpgradeCnt(paramMap);
		return retrunInt;
	}

	/**
	 * 获取职（岗）位列表(List for position)
	 * 
	 * @param request
	 * @return request
	 */
	@SuppressWarnings("unchecked")
	public List getPositionList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getPositionList(paramMap);
		return retrunList;
	}

	/**
	 * 获取职责列表(List for duty)
	 * 
	 * @param request
	 * @return request
	 */
	@SuppressWarnings("unchecked")
	public List getDutyList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getDutyList(paramMap);
		return retrunList;
	}

	/**
	 * 获取职级列表(List for postGrade)
	 * 
	 * @param request
	 * @return request
	 */
	@SuppressWarnings("unchecked")
	public List getPostGradeList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getPostGradeList(paramMap);
		return retrunList;
	}

	/**
	 * 获取号俸列表(List for postGrade)
	 * 
	 * @param request
	 * @return request
	 */
	@SuppressWarnings("unchecked")
	public List getOldPostGradeList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		//
		// AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// // if (paramMap != null && paramMap.get("CPNY_ID") == null) {
		// // paramMap.put("CPNY_ID", admin.getCpnyId());
		// // }
		// paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = transferOrderDao.getOldPostGradeList(paramMap);
		return retrunList;
	}

	/**
	 * 获取职级名称(职务)列表(List for post)
	 * 
	 * @param request
	 * @return request
	 */
	@SuppressWarnings("unchecked")
	public List getPostList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getPostList(paramMap);
		return retrunList;
	}

	/**
	 * 获取工作地列表(List for workArea)
	 * 
	 * @param request
	 * @return request
	 */
	@SuppressWarnings("unchecked")
	public List getWorkAreaList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getWorkAreaList(paramMap);
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	public List getPostListByPostGradeNo(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getPostListByPostGradeNo(paramMap);
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	public List getDutyListByPostGradeNo(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getDutyListByPostGradeNo(paramMap);
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	public List getWorkAreaByDept(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getWorkAreaByDept(paramMap);
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	public List getSocialSecurityAreaByWorkArea(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getSocialSecurityAreaByWorkArea(paramMap);
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	public List getTransferNormalList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		eidsForcommand(paramMap, request);

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getTransferNormalList(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getTransferNormalList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 获取参保人员个数（get Insurance Personnel Cnt）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getTransferNormalCnt(HttpServletRequest request) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		eidsForcommand(paramMap, request);
		retrunInt = transferOrderDao.getTransferNormalCnt(paramMap);
		return retrunInt;
	}

	@SuppressWarnings("unchecked")
	public List getTransferPromoteList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getTransferPromoteList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getTransferPromoteList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 获取参保人员个数（get Insurance Personnel Cnt）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getTransferPromoteCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		retrunInt = transferOrderDao.getTransferPromoteCnt(paramMap);

		return retrunInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map searchEmpHistory(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		return this.transferOrderDao.searchEmpHistory(param, -999999, -999999);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String rollBackUpgrade(List<LinkedHashMap<String, Object>> list,
			HttpServletRequest request) throws Exception {
		if ("ActBusiness".equals(request.getParameter("rollbackTyep"))) {
			for (Map data : list) {
				this.transferOrderDao.updateRollBackActBusiness(data);
			}
		} else if ("Resign".equals(request.getParameter("rollbackTyep"))) {
			for (Map data : list) {
				this.transferOrderDao.updateRollBackResign(data);
			}
		} else if ("Dispatch".equals(request.getParameter("rollbackTyep"))) {
			for (Map data : list) {
				this.transferOrderDao.updateRollBackDispatch(data);
			}
		} else if ("Hortation".equals(request.getParameter("rollbackTyep"))) {
			for (Map data : list) {
				this.transferOrderDao.updateRollBackHortation(data);
			}
		} else if ("Plurality".equals(request.getParameter("rollbackTyep"))) {
			for (Map data : list) {
				this.transferOrderDao.updateRollBackPlurality(data);
			}
		} else if ("PunishMent".equals(request.getParameter("rollbackTyep"))) {
			for (Map data : list) {
				this.transferOrderDao.updateRollBackPunishMent(data);
			}
		} else {
			for (Map data : list) {
				this.transferOrderDao.updateRollBackUpgrade(data);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map searchEmpHistoryForActBusiness(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		return this.transferOrderDao.searchEmpHistoryForActBusiness(param,
				-999999, -999999);
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@Override
	public int getNextEmpid(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return this.transferOrderDao.getNextEmpid(paramMap);
	}

	/**
	 * 离职发令查询(Left the inquires)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getViewResignList(LinkedHashMap paramMap, HttpServletRequest request) {
		List retrunList = new ArrayList();
		eidsForcommand(paramMap, request);
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getViewResignList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getViewResignList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 离职发令条数查询(Left the inquires number)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public int getViewResignCnt(LinkedHashMap paramMap, HttpServletRequest request) {
		int retrunInt = 0;
		eidsForcommand(paramMap, request);
		retrunInt = transferOrderDao.getViewResignCnt(paramMap);
		return retrunInt;
	}
	
	/**
	 * 添加离职发令查询(Left the inquires)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getViewTempEmpList(LinkedHashMap paramMap, HttpServletRequest request) {
		List retrunList = new ArrayList();
		eidsForcommand(paramMap, request);
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getViewTempEmpList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getViewTempEmpList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 添加离职发令条数查询(Left the inquires number)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public int getViewTempEmpCnt(LinkedHashMap paramMap, HttpServletRequest request) {
		int retrunInt = 0;
		eidsForcommand(paramMap, request);
		retrunInt = transferOrderDao.getViewTempEmpCnt(paramMap);
		return retrunInt;
	}
	
	/**
	 * 修改离职发令查询(Left the inquires)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getViewResignEditList(LinkedHashMap paramMap, HttpServletRequest request) {
		List retrunList = new ArrayList();
		eidsForcommand(paramMap, request);
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getViewResignEditList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getViewResignEditList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 修改离职发令查询(Left the inquires) 不分页时用
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getViewResignEditListAll(LinkedHashMap paramMap, HttpServletRequest request) {
		List retrunList = new ArrayList();
		retrunList = transferOrderDao.getViewResignEditList(paramMap);
		return retrunList;
	}	
	
	/**
	 * 修改离职发令条数查询(Left the inquires number)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public int getViewResignEditCnt(LinkedHashMap paramMap, HttpServletRequest request) {
		int retrunInt = 0;
		eidsForcommand(paramMap, request);
		retrunInt = transferOrderDao.getViewResignEditCnt(paramMap);
		return retrunInt;
	}
	
	/**
	 * saveResignation 临时职人员离职信息保存
	 * SAVETYPE: ADD-新增 ，EDIT-修改
	 * REQTYPE: DRAFT-临时保存，SAVE-正式保存
	 * 判断离职日期<=当前日期时,生效处理
	 * 生效处理procedure: SY_SYNC_ASSIGNMENT_INFO_CHRS2
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public int saveResignation(HttpServletRequest request) {
		int result = 0;
		String saveType = request.getParameter("SAVETYPE");
		String reqType = request.getParameter("REQTYPE");
		if(saveType.equals("ADD")){
			int valid = this.checkSaveResignation(request);
			//0-当前发令,可以继续  ;否则存在未生效的发令，返回
			if (valid > 0) {
				result = 3;
				return result;
			}
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("resignCKB");	
		try {
			for (int i = 0; i < check.length; i++) {
				Map paramMap = new LinkedHashMap();
				paramMap.put("interLanguage", admin.getLanguage());
				paramMap.put("TRANS_NO", "643");
				paramMap.put("TRANS_CODE","RESIGN");
				paramMap.put("RESIGN_TYPE_CODE",request.getParameter("RESIGN_TYPE_CODE_" + check[i]));
				paramMap.put("PERSON_ID",request.getParameter("PERSON_ID_" + check[i]));
				paramMap.put("CPNY_ID", admin.getCpnyId());				
				paramMap.put("EMPID",check[i]);				
				paramMap.put("DEPT_NO", request.getParameter("DEPTNO_" + check[i]));
				paramMap.put("EMP_OFFICE",request.getParameter("EMP_OFFICE_" + check[i]));
				paramMap.put("STATUS_CODE",request.getParameter("STATUS_CODE_" + check[i]));
				paramMap.put("DEPTNO", admin.getDeptNo());
				String resignDate = request.getParameter("RESIGN_DATE_" + check[i]);
				resignDate=resignDate.replace("-", "");
				paramMap.put("RESIGN_DATE",resignDate);
				paramMap.put("RESIGN_REASON",request.getParameter("RESIGN_REASON_" + check[i]));
				paramMap.put("REMARK",request.getParameter("REMARK_" + check[i]));
				paramMap.put("BLACKLIST_REASON", request.getParameter("BLACKLIST_REASON_" + check[i]));
				paramMap.put("BLACKLIST_YN",request.getParameter("BLACKLIST_YN_" + check[i]));
				paramMap.put("CREATED_BY", admin.getPersonId());
				paramMap.put("CURRENT_AFFIRM_ID", null);
				paramMap.put("ACTIVITY", 0);
				if(reqType.equals("DRAFT")){						
					paramMap.put("STATE", 10);//临时保存
				}else{
					paramMap.put("STATE", 20);//正式保存
				}					
				//保存离职发令
				if(saveType.equals("ADD")){//新增		
					String resignNo = transferOrderDao.getNextResignNo();
					paramMap.put("RESIGN_NO", resignNo);
					transferOrderDao.saveExperienceForResignation(paramMap);
				}else{//修改						
					paramMap.put("UPDATED_BY", admin.getPersonId());
					paramMap.put("RESIGN_NO", request.getParameter("RESIGN_NO_" + check[i]));
					transferOrderDao.updateExperienceForResignation(paramMap);
				}
				//取最后一次打卡记录
				paramMap.put("AR_MAC_RECORDS","AR_MAC_RECORDS_"+admin.getCpnyId());
				String lastRecord = transferOrderDao.getLastRecord(paramMap);
				if(lastRecord != ""){
					paramMap.put("LAST_RECORD", lastRecord);
					transferOrderDao.changeLastRecord(paramMap);
				}
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}
	
	/**
	 * confirmResignation 临时职人员离职发令申请
	 * 判断离职日期<=当前日期时,生效处理
	 * 生效处理procedure: SY_SYNC_ASSIGNMENT_INFO_CHRS2
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public Map confirmResignation(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");

		String   ls = "";
		String[] isChecked = request.getParameterValues("hr0206ResignNo");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
			}
		}
		if(ls.equals(""))
		{
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "请至少选择一条信息！");
			return retMap;
		}
		paramMap.put("RESIGN_NOS", ls);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		// 判断是否需要决裁1为开，0为关
		Map paramValueMap = new LinkedHashMap();
		paramValueMap.put("CPNY_ID", admin.getCpnyId());
		paramValueMap.put("TYPE", "resign");	
		int affirmFlag = transferOrderDao.getParamInfoValue(paramValueMap);
		String[] affirmId = {""};
		if (affirmFlag == 1) {
			//检查决裁线
			affirmId = request.getParameterValues("AFFIRMOR_ID");
			if(affirmId==null){
				retMap.put("RET", 0);
				retMap.put("MESSAGE", "没有设置审批人信息！");
				return retMap;
			}else{
				String nullAffirmorId = "OK";
				for(int i=0; i<affirmId.length; i++){
					if(affirmId[i]!=null && affirmId[i].equals("")){nullAffirmorId="NG";}
				}
				if(nullAffirmorId.equals("NG")){
					retMap.put("RET", 0);
					retMap.put("MESSAGE", "审批人不可以为空,请检查或重新设置！");
					return retMap;
				}
			}
			paramMap.put("STATE", 30); //提交
			paramMap.put("APPLY_TYPE_NO", 15823);
		}else{
			paramMap.put("STATE", 50); //通过
		}
		try {
			String reqNo = transferOrderDao.getNextExpInside();			
			paramMap.put("REQ_ID", reqNo);
			paramMap.put("CURRENT_AFFIRMOR_ID", affirmId[0]);
			paramMap.put("PERSON_ID", admin.getPersonId());			
			paramMap.put("UPDATED_BY", admin.getPersonId());
			transferOrderDao.addConfirmReqResign(paramMap);
			
			if (affirmFlag == 1) {
				transferOrderDao.addAttachFiles(paramMap);
				//保存决裁线
				for(int i=0; i<affirmId.length; i++)
				{
					paramMap.put("AFFIRMOR_ID", affirmId[i]);
					paramMap.put("AFFIRM_LEVEL", i+1);
					transferOrderDao.insertAffirmor(paramMap);
					if(i==0){
						this.sendToLGEPForResignation(paramMap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			return retMap;
		}
		return retMap;
	}
	
	/**
	 * 临时职人员离职数据导入结果查询
	 * @param request
	 * @return
	 * @Create date: 2014.10.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getTempEmpResignDataImportResultList(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		if (UiUtil.getPageNum(request) > 0){
			retrunList =  transferOrderDao.getTempEmpResignDataImportResultList(paramMap , 
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
					);
		}else{
			retrunList =  transferOrderDao.getTempEmpResignDataImportResultList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 临时职人员离职导入结果导出
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getTempEmpResignDataImportResultListExcel(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		retrunList =  transferOrderDao.getTempEmpResignDataImportResultList(paramMap);
		return retrunList;
	}
	
	/**
	 * 临时职人员离职数据导入结果查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getTempEmpResignDataImportResultListCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		return transferOrderDao.getTempEmpResignDataImportResultListCnt(paramMap);
	}
	
	/**
	 * 临时职人员离职导入结果查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getTempEmpResignDataImportResultListErrCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		return transferOrderDao.getTempEmpResignDataImportResultListErrCnt(paramMap);
	}
	
	/**
	 * 临时职人员离职数据验证并导入正式表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String importTempEmpResignDataRAWFromExcel(HttpServletRequest request, Map paramMap) {
		String result = "";
		result = this.transferOrderDao.importTempEmpResignDataRAWFromExcel(paramMap);
		return result;
	}
	
	/**
	 * 执行晋升降职(Executive promotion demoted)
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveTransferPromote(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("d1");
		int result = 0;
		try {
			Map paramValueMap = new LinkedHashMap();
			paramValueMap.put("CPNY_ID", admin.getCpnyId());
			paramValueMap.put("TYPE", "promote");
			// 判断是否需要决裁1为开，0为关
			if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {
				// 判断是否有特殊设置
				for (int i = 0; i < check.length; i++) {
					Map paramMap = new LinkedHashMap();
					paramMap.put("EMPID", check[i]);
					paramMap.put("CPNY_ID", admin.getCpnyId());
					paramMap.put("PERSON_ID",
							request.getParameter("PERSON_ID_" + check[i]));
					paramMap.put("interLanguage", admin.getLanguage());
					// 原部门
					paramMap.put("DEPT_NO", ((LinkedHashMap) transferOrderDao
							.getViewUpgradeList(paramMap).get(0)).get("DEPTNO"));
					// 调动后部门
					paramMap.put("DEPTNO",
							request.getParameter("DEPTNO_" + check[i]));
					paramMap.put("TRANS_CODE",
							request.getParameter("PROMOTE_TYPE_" + check[i]));
					paramMap.put("PERSON_ID",
							request.getParameter("PERSON_ID_" + check[i]));
					paramMap.put("POST_GRADE_NO",
							request.getParameter("POST_GRADE_NO_" + check[i]));
					paramMap.put("POSITION_NO",
							request.getParameter("POSITION_NO_" + check[i]));
					paramMap.put("START_DATE",
							request.getParameter("START_DATE_" + check[i]));
					paramMap.put("POST_NO",
							request.getParameter("POST_NO_" + check[i]));
					paramMap.put("DUTY_NO",
							request.getParameter("DUTY_NO_" + check[i]));
					paramMap.put("WORK_AREA",
							request.getParameter("WORK_AREA_" + check[i]));
					paramMap.put("CREATED_BY", admin.getPersonId());
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("TYPE", "promote");
					paramMap.put("ACTIVITY", 0);

					// 取出被发令人的决裁者信息（个人设置优先）
					List affirmorList = transferOrderDao
							.getAffirmorIdListByPersonal(paramMap);
					// 如果特殊设置不为空
					if (affirmorList.size() != 0) {
						for (int j = 0; j < affirmorList.size(); j++) {
							// 取当前决裁者
							if (j == 0) {
								paramMap.put("CURRENT_AFFIRM_ID",
										((LinkedHashMap) affirmorList.get(j))
												.get("AFFIRMOR_ID"));
							}
						}
						// 保存发令信息
						transferOrderDao.saveExperienceForPromoteAndAffirmList(
								paramMap, affirmorList);
					} else {
						// 取出被发令人的决裁者信息(部门设置)
						List affirmorListByDept = transferOrderDao
								.getAffirmorIdListByDept(paramMap);
						if (affirmorListByDept.size() != 0) {
							for (int j = 0; j < affirmorListByDept.size(); j++) {
								// 取当前决裁者
								if (j == 0) {
									paramMap.put("CURRENT_AFFIRM_ID",
											((LinkedHashMap) affirmorListByDept
													.get(j)).get("AFFIRMOR_ID"));
								}
							}
							// 保存发令信息
							transferOrderDao
									.saveExperienceForPromoteAndAffirmList(
											paramMap, affirmorListByDept);
						} else {

							// 自定义取决裁者
							Map flagMap = getAffiram(paramMap, expInsideNo,
									request, admin);
							String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap
									.get("AFFIRM_FLAG").toString() : "";

							if ("2".equals(flag)) {
								return 2;
							}
							List newSaveAffirmorList = (List) paramMap
									.get("newSaveAffirmorList");

							transferOrderDao
									.saveExperienceForPromoteAndAffirmList(
											paramMap, newSaveAffirmorList);
						}
					}

				}
			} else {
				for (int i = 0; i < check.length; i++) {
					Map paramMap = new LinkedHashMap();

					paramMap.put("CPNY_ID", admin.getCpnyId());
					paramMap.put("PERSON_ID",
							request.getParameter("PERSON_ID_" + check[i]));
					paramMap.put("interLanguage", admin.getLanguage());
					// 原部门
					paramMap.put("DEPT_NO", ((LinkedHashMap) transferOrderDao
							.getViewUpgradeList(paramMap).get(0)).get("DEPTNO"));
					// 调动后部门
					paramMap.put("DEPTNO",
							request.getParameter("DEPTNO_" + check[i]));
					paramMap.put("TRANS_CODE",
							request.getParameter("PROMOTE_TYPE_" + check[i]));
					paramMap.put("PERSON_ID",
							request.getParameter("PERSON_ID_" + check[i]));
					paramMap.put("POST_GRADE_NO",
							request.getParameter("POST_GRADE_NO_" + check[i]));
					paramMap.put("POSITION_NO",
							request.getParameter("POSITION_NO_" + check[i]));
					paramMap.put("START_DATE",
							request.getParameter("START_DATE_" + check[i]));
					paramMap.put("POST_NO",
							request.getParameter("POST_NO_" + check[i]));
					paramMap.put("DUTY_NO",
							request.getParameter("DUTY_NO_" + check[i]));
					paramMap.put("WORK_AREA",
							request.getParameter("WORK_AREA_" + check[i]));
					paramMap.put("CREATED_BY", admin.getPersonId());
					paramMap.put("UPDATED_BY", admin.getPersonId());
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("TYPE", "promote");

					List hrEmpList = transferOrderDao
							.getTransferPromoteList(paramMap);

					String dept_no = ((LinkedHashMap) hrEmpList.get(0))
							.get("DEPTNO") != null ? ((LinkedHashMap) hrEmpList
							.get(0)).get("DEPTNO").toString() : "";
					String duty_no = ((LinkedHashMap) hrEmpList.get(0))
							.get("DUTY_NO") != null ? ((LinkedHashMap) hrEmpList
							.get(0)).get("DUTY_NO").toString() : "";
					String post_no = ((LinkedHashMap) hrEmpList.get(0))
							.get("POST_NO") != null ? ((LinkedHashMap) hrEmpList
							.get(0)).get("POST_NO").toString() : "";
					String post_grade_no = ((LinkedHashMap) hrEmpList.get(0))
							.get("POST_GRADE_NO") != null ? ((LinkedHashMap) hrEmpList
							.get(0)).get("POST_GRADE_NO").toString() : "";
					String position_no = ((LinkedHashMap) hrEmpList.get(0))
							.get("POSITION_NO") != null ? ((LinkedHashMap) hrEmpList
							.get(0)).get("POSITION_NO").toString() : "";
					String work_area = ((LinkedHashMap) hrEmpList.get(0))
							.get("WORK_AREA") != null ? ((LinkedHashMap) hrEmpList
							.get(0)).get("WORK_AREA").toString() : "";

					// 部门变动
					if (!dept_no.equals(request.getParameter("DEPTNO_"
							+ check[i]))) {
						// transferOrderDao.updateHrEmpDept(paramMap);
						// transferOrderDao.saveHrEmpDept(paramMap);

						// 根据当前生效时间和person_id取出HR_EMP_DEPT表里面的 当前生效时间前一条
						List beforeDataList = transferOrderDao
								.getBeforeDataForDept(paramMap);
						// 根据当前生效时间和person_id取出HR_EMP_DEPT表里面的 当前生效时间后一条数据
						List afterDataList = transferOrderDao
								.getAfterDataForDept(paramMap);

						// 如果取到2个,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
						// 修改前一条数据的结束时间（=当前生效日期-1） 此情况不修改HR_EMPLOYEE里面的DEPTNO
						if (beforeDataList.size() != 0
								&& afterDataList.size() != 0) {
							paramMap.put("AFTERSTARTDATE", ((Map) afterDataList
									.get(0)).get("STARTDATE"));// 插入数据的结束日期
							// 取后一条数据的开始日期
							// 后台用的时候日期要-1
							paramMap.put("AFTERS_EXP_INSIDE_NO",
									((Map) afterDataList.get(0))
											.get("S_EXP_INSIDE_NO"));// 插入数据的
							// E_EXP_INSIDE_NO
							// 取后一条的开始
							// S_EXP_INSIDE_NO

							paramMap.put("BEFORE_PERSONID",
									((Map) beforeDataList.get(0))
											.get("PERSON_ID"));// 修改前一条数据的
							// persno_id
							// (修改条件)
							paramMap.put("BEFORE_ENDDATE",
									paramMap.get("START_DATE"));// 修改前一条数据的 结束日期
							// 后台用的时候日期要-1
							paramMap.put("BEFOREE_EXP_INSIDE_NO",
									paramMap.get("EXP_INSIDE_NO"));// 修改前一条数据的
							// E_EXP_INSIDE_NO
							// 修改前一条数据的 条件 -start
							paramMap.put("BEFORE_STARTDATE",
									((Map) beforeDataList.get(0))
											.get("STARTDATE"));// 修改前一条数据的条件
							// 开始日期STARTDATE(修改条件)
							paramMap.put("BEFORE_DEPTNO",
									((Map) beforeDataList.get(0)).get("DEPTNO"));// 修改前一条数据的条件
							// DEPTNO(修改条件)
							// 修改前一条数据的 条件 -end
							transferOrderDao
									.saveAndUpdateForUpGradeHrEmpDept(paramMap);

							paramMap.put("DEPTNO", dept_no);// 此种情况不需要修改HE_EMPLOYEE
							// 所以DEPTNO的值应该是原部门

						}

						// 如果只取到后面的,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
						// 此情况不修改HR_EMPLOYEE里面的DEPTNO
						if (beforeDataList.size() == 0
								&& afterDataList.size() != 0) {
							paramMap.put("AFTERSTARTDATE", ((Map) afterDataList
									.get(0)).get("STARTDATE"));// 插入数据的结束日期
							// 取后一条数据的开始日期
							// 后台用的时候日期要-1
							paramMap.put("AFTERS_EXP_INSIDE_NO",
									((Map) afterDataList.get(0))
											.get("S_EXP_INSIDE_NO"));// 插入数据的
							// E_EXP_INSIDE_NO
							// 取后一条的开始
							// S_EXP_INSIDE_NO
							transferOrderDao.saveHrEmpDeptForUpGrade(paramMap);
							paramMap.put("DEPTNO", dept_no);// 此种情况不需要修改HE_EMPLOYEE
							// 所以DEPTNO的值应该是原部门
						}

						// 如果只取到前面的,修改结束日期为空的记录,结束日期=当前生效时间,插入一条数据,生效时间是当前生效日期
						if (beforeDataList.size() != 0
								&& afterDataList.size() == 0) {
							transferOrderDao.saveHrEmpDept(paramMap);
						}

					}

					// 职责变动
					if (!duty_no.equals(request.getParameter("DUTY_NO_"
							+ check[i]))) {
						// transferOrderDao.updateHrEmpDuty(paramMap);
						// transferOrderDao.saveHrEmpDuty(paramMap);

						// 根据当前生效时间和person_id取出HR_EMP_DEPT表里面的 当前生效时间前一条
						List beforeDataListForDuty = transferOrderDao
								.getBeforeDataForDuty(paramMap);
						// 根据当前生效时间和person_id取出HR_EMP_DEPT表里面的 当前生效时间后一条数据
						List afterDataListForDuty = transferOrderDao
								.getAfterDataForDuty(paramMap);

						// 如果取到2个,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
						// 修改前一条数据的结束时间（=当前生效日期-1） 此情况不修改HR_EMPLOYEE里面的DUTY_NO
						if (beforeDataListForDuty.size() != 0
								&& afterDataListForDuty.size() != 0) {
							paramMap.put("AFTERSTARTDATE",
									((Map) afterDataListForDuty.get(0))
											.get("STARTDATE"));// 插入数据的结束日期
							// 取后一条数据的开始日期
							// 后台用的时候日期要-1
							paramMap.put("AFTERS_EXP_INSIDE_NO",
									((Map) afterDataListForDuty.get(0))
											.get("S_EXP_INSIDE_NO"));// 插入数据的
							// E_EXP_INSIDE_NO
							// 取后一条的开始
							// S_EXP_INSIDE_NO

							paramMap.put("BEFORE_PERSONID",
									((Map) beforeDataListForDuty.get(0))
											.get("PERSON_ID"));// 修改前一条数据的
							// persno_id
							// (修改条件)
							paramMap.put("BEFORE_ENDDATE",
									paramMap.get("START_DATE"));// 修改前一条数据的 结束日期
							// 后台用的时候日期要-1
							paramMap.put("BEFOREE_EXP_INSIDE_NO",
									paramMap.get("EXP_INSIDE_NO"));// 修改前一条数据的
							// E_EXP_INSIDE_NO
							// 修改前一条数据的 条件 -start
							paramMap.put("BEFORE_STARTDATE",
									((Map) beforeDataListForDuty.get(0))
											.get("STARTDATE"));// 修改前一条数据的条件
							// 开始日期STARTDATE(修改条件)
							paramMap.put("BEFORE_DUTY_NO",
									((Map) beforeDataListForDuty.get(0))
											.get("DUTY_NO"));// 修改前一条数据的条件
							// DUTY_NO(修改条件)
							// 修改前一条数据的 条件 -end
							transferOrderDao
									.saveAndUpdateForUpGradeHrEmpDuty(paramMap);

							paramMap.put("DUTY_NO", duty_no);// 此种情况不需要修改HE_EMPLOYEE
							// 所以DUTYNO的值应该是原职责

						}

						// 如果只取到后面的,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
						// 此情况不修改HR_EMPLOYEE里面的DUTYNO
						if (beforeDataListForDuty.size() == 0
								&& afterDataListForDuty.size() != 0) {
							paramMap.put("AFTERSTARTDATE",
									((Map) afterDataListForDuty.get(0))
											.get("STARTDATE"));// 插入数据的结束日期
							// 取后一条数据的开始日期
							// 后台用的时候日期要-1
							paramMap.put("AFTERS_EXP_INSIDE_NO",
									((Map) afterDataListForDuty.get(0))
											.get("S_EXP_INSIDE_NO"));// 插入数据的
							// E_EXP_INSIDE_NO
							// 取后一条的开始
							// S_EXP_INSIDE_NO
							transferOrderDao.saveHrEmpDutyForUpGrade(paramMap);
							paramMap.put("DUTY_NO", duty_no);// 此种情况不需要修改HE_EMPLOYEE
							// 所以DUTYNO的值应该是原职责
						}

						// 如果只取到前面的,修改结束日期为空的记录,结束日期=当前生效时间,插入一条数据,生效时间是当前生效日期
						if (beforeDataListForDuty.size() != 0
								&& afterDataListForDuty.size() == 0) {
							transferOrderDao.saveHrEmpDuty(paramMap);
						}

					}

					// 调动 职级名称(职务)变动
					if (!post_no.equals(request.getParameter("POST_NO_"
							+ check[i]))) {
						// transferOrderDao.updateHrEmpPost(paramMap);
						// transferOrderDao.saveHrEmpPost(paramMap);

						// 根据当前生效时间和person_id取出HR_EMP_POST表里面的 当前生效时间前一条
						List beforeDataListForPost = transferOrderDao
								.getBeforeDataForPost(paramMap);
						// 根据当前生效时间和person_id取出HR_EMP_POST表里面的 当前生效时间后一条数据
						List afterDataListForPost = transferOrderDao
								.getAfterDataForPost(paramMap);

						// 如果取到2个,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
						// 修改前一条数据的结束时间（=当前生效日期-1） 此情况不修改HR_EMPLOYEE里面的DUTY_NO
						if (beforeDataListForPost.size() != 0
								&& afterDataListForPost.size() != 0) {
							paramMap.put("AFTERSTARTDATE",
									((Map) afterDataListForPost.get(0))
											.get("STARTDATE"));// 插入数据的结束日期
							// 取后一条数据的开始日期
							// 后台用的时候日期要-1
							paramMap.put("AFTERS_EXP_INSIDE_NO",
									((Map) afterDataListForPost.get(0))
											.get("S_EXP_INSIDE_NO"));// 插入数据的
							// E_EXP_INSIDE_NO
							// 取后一条的开始
							// S_EXP_INSIDE_NO

							paramMap.put("BEFORE_PERSONID",
									((Map) beforeDataListForPost.get(0))
											.get("PERSON_ID"));// 修改前一条数据的
							// persno_id
							// (修改条件)
							paramMap.put("BEFORE_ENDDATE",
									paramMap.get("START_DATE"));// 修改前一条数据的 结束日期
							// 后台用的时候日期要-1
							paramMap.put("BEFOREE_EXP_INSIDE_NO",
									paramMap.get("EXP_INSIDE_NO"));// 修改前一条数据的
							// E_EXP_INSIDE_NO
							// 修改前一条数据的 条件 -start
							paramMap.put("BEFORE_STARTDATE",
									((Map) beforeDataListForPost.get(0))
											.get("STARTDATE"));// 修改前一条数据的条件
							// 开始日期STARTDATE(修改条件)
							paramMap.put("BEFORE_POST_NO",
									((Map) beforeDataListForPost.get(0))
											.get("POST_NO"));// 修改前一条数据的条件
							// POST_NO(修改条件)
							// 修改前一条数据的 条件 -end
							transferOrderDao
									.saveAndUpdateForUpGradeHrEmpPost(paramMap);

							paramMap.put("POST_NO", post_no);// 此种情况不需要修改HE_EMPLOYEE
							// 所以POST_NO的值应该是原职责

						}

						// 如果只取到后面的,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
						// 此情况不修改HR_EMPLOYEE里面的DUTYNO
						if (beforeDataListForPost.size() == 0
								&& afterDataListForPost.size() != 0) {
							paramMap.put("AFTERSTARTDATE",
									((Map) afterDataListForPost.get(0))
											.get("STARTDATE"));// 插入数据的结束日期
							// 取后一条数据的开始日期
							// 后台用的时候日期要-1
							paramMap.put("AFTERS_EXP_INSIDE_NO",
									((Map) afterDataListForPost.get(0))
											.get("S_EXP_INSIDE_NO"));// 插入数据的
							// E_EXP_INSIDE_NO
							// 取后一条的开始
							// S_EXP_INSIDE_NO
							transferOrderDao.saveHrEmpPostForUpGrade(paramMap);
							paramMap.put("POST_NO", post_no);// 此种情况不需要修改HE_EMPLOYEE
							// 所以POST_NO的值应该是原职责
						}

						// 如果只取到前面的,修改结束日期为空的记录,结束日期=当前生效时间,插入一条数据,生效时间是当前生效日期
						if (beforeDataListForPost.size() != 0
								&& afterDataListForPost.size() == 0) {
							transferOrderDao.saveHrEmpPost(paramMap);
						}

					}

					// 职级变动
					if (!post_grade_no.equals(request
							.getParameter("POST_GRADE_NO_" + check[i]))) {
						// transferOrderDao.updateHrEmpPostGrade(paramMap);
						// transferOrderDao.saveHrEmpPostGrade(paramMap);

						// 根据当前生效时间和person_id取出HR_EMP_POST_GRADE表里面的 当前生效时间前一条
						List beforeDataListForPostGradeNo = transferOrderDao
								.getBeforeDataForPostGrade(paramMap);
						// 根据当前生效时间和person_id取出HR_EMP_POST_GRADE表里面的 当前生效时间后一条数据
						List afterDataListForPostGradeNo = transferOrderDao
								.getAfterDataForPostGrade(paramMap);

						// 如果取到2个,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
						// 修改前一条数据的结束时间（=当前生效日期-1）
						// 此情况不修改HR_EMPLOYEE里面的POST_GRADE_NO
						if (beforeDataListForPostGradeNo.size() != 0
								&& afterDataListForPostGradeNo.size() != 0) {
							paramMap.put("AFTERSTARTDATE",
									((Map) afterDataListForPostGradeNo.get(0))
											.get("STARTDATE"));// 插入数据的结束日期
							// 取后一条数据的开始日期
							// 后台用的时候日期要-1
							paramMap.put("AFTERS_EXP_INSIDE_NO",
									((Map) afterDataListForPostGradeNo.get(0))
											.get("S_EXP_INSIDE_NO"));// 插入数据的
							// E_EXP_INSIDE_NO
							// 取后一条的开始
							// S_EXP_INSIDE_NO

							paramMap.put("BEFORE_PERSONID",
									((Map) beforeDataListForPostGradeNo.get(0))
											.get("PERSON_ID"));// 修改前一条数据的
							// persno_id
							// (修改条件)
							paramMap.put("BEFORE_ENDDATE",
									paramMap.get("START_DATE"));// 修改前一条数据的 结束日期
							// 后台用的时候日期要-1
							paramMap.put("BEFOREE_EXP_INSIDE_NO",
									paramMap.get("EXP_INSIDE_NO"));// 修改前一条数据的
							// E_EXP_INSIDE_NO
							// 修改前一条数据的 条件 -start
							paramMap.put("BEFORE_STARTDATE",
									((Map) beforeDataListForPostGradeNo.get(0))
											.get("STARTDATE"));// 修改前一条数据的条件
							// 开始日期STARTDATE(修改条件)
							paramMap.put("BEFORE_POST_GRADE_NO",
									((Map) beforeDataListForPostGradeNo.get(0))
											.get("POST_GRADE_NO"));// 修改前一条数据的条件
							// POST_GRADE_NO(修改条件)
							// 修改前一条数据的 条件 -end
							transferOrderDao
									.saveAndUpdateForUpGradeHrEmpPostGrade(paramMap);

							paramMap.put("POST_GRADE_NO", post_grade_no);// 此种情况不需要修改HE_EMPLOYEE
							// 所以POST_GRADE_NO的值应该是原职级

						}

						// 如果只取到后面的,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
						// 此情况不修改HR_EMPLOYEE里面的POST_GRADE_NO
						if (beforeDataListForPostGradeNo.size() == 0
								&& afterDataListForPostGradeNo.size() != 0) {
							paramMap.put("AFTERSTARTDATE",
									((Map) afterDataListForPostGradeNo.get(0))
											.get("STARTDATE"));// 插入数据的结束日期
							// 取后一条数据的开始日期
							// 后台用的时候日期要-1
							paramMap.put("AFTERS_EXP_INSIDE_NO",
									((Map) afterDataListForPostGradeNo.get(0))
											.get("S_EXP_INSIDE_NO"));// 插入数据的
							// E_EXP_INSIDE_NO
							// 取后一条的开始
							// S_EXP_INSIDE_NO
							transferOrderDao
									.saveHrEmpPostGradeForUpGrade(paramMap);
							paramMap.put("POST_GRADE_NO", post_grade_no);// 此种情况不需要修改HE_EMPLOYEE
							// 所以POST_GRADE_NO的值应该是原职级
						}

						// 如果只取到前面的,修改结束日期为空的记录,结束日期=当前生效时间,插入一条数据,生效时间是当前生效日期
						if (beforeDataListForPostGradeNo.size() != 0
								&& afterDataListForPostGradeNo.size() == 0) {
							transferOrderDao.saveHrEmpPostGrade(paramMap);
						}

					}

					// 职(岗)位变动
					if (!position_no.equals(request.getParameter("POSITION_NO_"
							+ check[i]))) {
						// transferOrderDao.updateHrEmpPosition(paramMap);
						// transferOrderDao.saveHrEmpPosition(paramMap);

						// 根据当前生效时间和person_id取出HR_EMP_POSITION表里面的 当前生效时间前一条
						List beforeDataListForPosition = transferOrderDao
								.getBeforeDataForPosition(paramMap);
						// 根据当前生效时间和person_id取出HR_EMP_POSITION表里面的 当前生效时间后一条数据
						List afterDataListForPosition = transferOrderDao
								.getAfterDataForPosition(paramMap);

						// 如果取到2个,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
						// 修改前一条数据的结束时间（=当前生效日期-1）
						// 此情况不修改HR_EMPLOYEE里面的POSITION_NO
						if (beforeDataListForPosition.size() != 0
								&& afterDataListForPosition.size() != 0) {
							paramMap.put("AFTERSTARTDATE",
									((Map) afterDataListForPosition.get(0))
											.get("STARTDATE"));// 插入数据的结束日期
							// 取后一条数据的开始日期
							// 后台用的时候日期要-1
							paramMap.put("AFTERS_EXP_INSIDE_NO",
									((Map) afterDataListForPosition.get(0))
											.get("S_EXP_INSIDE_NO"));// 插入数据的
							// E_EXP_INSIDE_NO
							// 取后一条的开始
							// S_EXP_INSIDE_NO

							paramMap.put("BEFORE_PERSONID",
									((Map) beforeDataListForPosition.get(0))
											.get("PERSON_ID"));// 修改前一条数据的
							// persno_id
							// (修改条件)
							paramMap.put("BEFORE_ENDDATE",
									paramMap.get("START_DATE"));// 修改前一条数据的 结束日期
							// 后台用的时候日期要-1
							paramMap.put("BEFOREE_EXP_INSIDE_NO",
									paramMap.get("EXP_INSIDE_NO"));// 修改前一条数据的
							// E_EXP_INSIDE_NO
							// 修改前一条数据的 条件 -start
							paramMap.put("BEFORE_STARTDATE",
									((Map) beforeDataListForPosition.get(0))
											.get("STARTDATE"));// 修改前一条数据的条件
							// 开始日期STARTDATE(修改条件)
							paramMap.put("BEFORE_POSITION_NO",
									((Map) beforeDataListForPosition.get(0))
											.get("POSITION_NO"));// 修改前一条数据的条件
							// POSITION_NO(修改条件)
							// 修改前一条数据的 条件 -end
							transferOrderDao
									.saveAndUpdateForUpGradeHrEmpPosition(paramMap);

							paramMap.put("POSITION_NO", position_no);// 此种情况不需要修改HE_EMPLOYEE
							// 所以POSITION_NO的值应该是原职(岗)位

						}

						// 如果只取到后面的,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
						// 此情况不修改HR_EMPLOYEE里面的POSITION_NO
						if (beforeDataListForPosition.size() == 0
								&& afterDataListForPosition.size() != 0) {
							paramMap.put("AFTERSTARTDATE",
									((Map) afterDataListForPosition.get(0))
											.get("STARTDATE"));// 插入数据的结束日期
							// 取后一条数据的开始日期
							// 后台用的时候日期要-1
							paramMap.put("AFTERS_EXP_INSIDE_NO",
									((Map) afterDataListForPosition.get(0))
											.get("S_EXP_INSIDE_NO"));// 插入数据的
							// E_EXP_INSIDE_NO
							// 取后一条的开始
							// S_EXP_INSIDE_NO
							transferOrderDao
									.saveHrEmpPositionForUpGrade(paramMap);
							paramMap.put("POSITION_NO", position_no);// 此种情况不需要修改HE_EMPLOYEE
							// 所以POSITION_NO的值应该是原职(岗)位
						}

						// 如果只取到前面的,修改结束日期为空的记录,结束日期=当前生效时间,插入一条数据,生效时间是当前生效日期
						if (beforeDataListForPosition.size() != 0
								&& afterDataListForPosition.size() == 0) {
							transferOrderDao.saveHrEmpPosition(paramMap);
						}

					}

					// 工作地变动
					if (!work_area.equals(request.getParameter("WORK_AREA_"
							+ check[i]))) {
						// transferOrderDao.updateHrEmpWorkArea(paramMap);
						// transferOrderDao.saveHrEmpWorkArea(paramMap);

						// 根据当前生效时间和person_id取出HR_EMP_POSITION表里面的 当前生效时间前一条
						List beforeDataListForWorkArea = transferOrderDao
								.getBeforeDataForWorkArea(paramMap);
						// 根据当前生效时间和person_id取出HR_EMP_POSITION表里面的 当前生效时间后一条数据
						List afterDataListForWorkArea = transferOrderDao
								.getAfterDataForWorkArea(paramMap);

						// 如果取到2个,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
						// 修改前一条数据的结束时间（=当前生效日期-1） 此情况不修改HR_EMPLOYEE里面的WORK_AREA
						if (beforeDataListForWorkArea.size() != 0
								&& afterDataListForWorkArea.size() != 0) {
							paramMap.put("AFTERSTARTDATE",
									((Map) afterDataListForWorkArea.get(0))
											.get("STARTDATE"));// 插入数据的结束日期
							// 取后一条数据的开始日期
							// 后台用的时候日期要-1
							paramMap.put("AFTERS_EXP_INSIDE_NO",
									((Map) afterDataListForWorkArea.get(0))
											.get("S_EXP_INSIDE_NO"));// 插入数据的
							// E_EXP_INSIDE_NO
							// 取后一条的开始
							// S_EXP_INSIDE_NO

							paramMap.put("BEFORE_PERSONID",
									((Map) afterDataListForWorkArea.get(0))
											.get("PERSON_ID"));// 修改前一条数据的
							// persno_id
							// (修改条件)
							paramMap.put("BEFORE_ENDDATE",
									paramMap.get("START_DATE"));// 修改前一条数据的 结束日期
							// 后台用的时候日期要-1
							paramMap.put("BEFOREE_EXP_INSIDE_NO",
									paramMap.get("EXP_INSIDE_NO"));// 修改前一条数据的
							// E_EXP_INSIDE_NO
							// 修改前一条数据的 条件 -start
							paramMap.put("BEFORE_STARTDATE",
									((Map) beforeDataListForWorkArea.get(0))
											.get("STARTDATE"));// 修改前一条数据的条件
							// 开始日期STARTDATE(修改条件)
							paramMap.put("BEFORE_WORK_AREA",
									((Map) beforeDataListForWorkArea.get(0))
											.get("WORK_AREA"));// 修改前一条数据的条件
							// WORK_AREA(修改条件)
							// 修改前一条数据的 条件 -end
							transferOrderDao
									.saveAndUpdateForUpGradeHrEmpWorkArea(paramMap);

							paramMap.put("WORK_AREA", work_area);// 此种情况不需要修改HE_EMPLOYEE
							// 所以WORK_AREA的值应该是原工作地

						}

						// 如果只取到后面的,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
						// 此情况不修改HR_EMPLOYEE里面的WORK_AREA
						if (beforeDataListForWorkArea.size() == 0
								&& afterDataListForWorkArea.size() != 0) {
							paramMap.put("AFTERSTARTDATE",
									((Map) afterDataListForWorkArea.get(0))
											.get("STARTDATE"));// 插入数据的结束日期
							// 取后一条数据的开始日期
							// 后台用的时候日期要-1
							paramMap.put("AFTERS_EXP_INSIDE_NO",
									((Map) afterDataListForWorkArea.get(0))
											.get("S_EXP_INSIDE_NO"));// 插入数据的
							// E_EXP_INSIDE_NO
							// 取后一条的开始
							// S_EXP_INSIDE_NO
							transferOrderDao
									.saveHrEmpWorkAreaForUpGrade(paramMap);
							paramMap.put("WORK_AREA", work_area);// 此种情况不需要修改HE_EMPLOYEE
							// 所以WORK_AREA的值应该是原工作地
						}

						// 如果只取到前面的,修改结束日期为空的记录,结束日期=当前生效时间,插入一条数据,生效时间是当前生效日期
						if (beforeDataListForWorkArea.size() != 0
								&& afterDataListForWorkArea.size() == 0) {
							transferOrderDao.saveHrEmpWorkArea(paramMap);
						}

					}

					DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
					String temp_strS = DateUtil.getSysdateStr();
					String joinCompanyDateS = request
							.getParameter("START_DATE_" + check[i]);
					Date joinCompanyDate = dateSys.parse(joinCompanyDateS);
					Date temp_str = dateSys.parse(temp_strS);
					if (joinCompanyDate.before(temp_str)
							|| joinCompanyDate.equals(temp_str)) {
						paramMap.put("ACTIVITY", 1);
						// if(transferOrderDao.getDateCount(paramMap) < 0 ){
						// transferOrderDao.saveExperienceForPromoteAndNoUpHEe(paramMap);
						// }else{
						transferOrderDao
								.saveExperienceForPromoteAndUpHrEmployee(paramMap);
						// }
						// transferOrderDao.updateHrEmployee(paramMap);
					} else {
						paramMap.put("ACTIVITY", 0);
						transferOrderDao.saveExperienceForPromote(paramMap);
					}
				}
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 兼职发令查询(Left the inquires)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getViewPluralityList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		if (paramMap != null && paramMap.get("PLURALITY_TYPE") == null) {
			paramMap.put("PLURALITY_TYPE_AFFIRM", "PLURALITY_TYPE_AFFIRM");
		}
		if (paramMap != null && "1685".equals(paramMap.get("PLURALITY_TYPE"))) {
			paramMap.put("PLURALITY_TYPE_AFFIRM", "PLURALITY_TYPE_AFFIRM");
		}
		if (paramMap != null && "16176".equals(paramMap.get("PLURALITY_TYPE"))) {
			paramMap.put("PLURALITY_TYPE_CANCLE", "PLURALITY_TYPE_CANCLE");
		}

		eidsForcommand(paramMap, request);

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getViewPluralityList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getViewPluralityList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 兼职发令条数查询(Left the inquires number)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public int getViewPluralityCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		if (paramMap != null && paramMap.get("PLURALITY_TYPE") == null) {
			paramMap.put("PLURALITY_TYPE_AFFIRM", "PLURALITY_TYPE_AFFIRM");
		}
		if (paramMap != null && "1685".equals(paramMap.get("PLURALITY_TYPE"))) {
			paramMap.put("PLURALITY_TYPE_AFFIRM", "PLURALITY_TYPE_AFFIRM");
		}
		if (paramMap != null && "1686".equals(paramMap.get("PLURALITY_TYPE"))) {
			paramMap.put("PLURALITY_TYPE_CANCLE", "PLURALITY_TYPE_CANCLE");
		}

		eidsForcommand(paramMap, request);

		retrunInt = transferOrderDao.getViewPluralityCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 检查是否可以做兼职发令
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkSavePlurality(HttpServletRequest request) {

		int result = 0;
		String check[] = request.getParameterValues("pluralityCKB");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			paramMap.put("PERSON_ID",
					request.getParameter("PERSON_ID_" + check[i]));
			result += this.transferOrderDao.checkSavePlurality(paramMap);
		}
		return result;
	}

	/**
	 * 兼职(取消兼职)发令
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int savePlurality(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("pluralityCKB");
		int result = 0;

		if ("".equals(request.getParameter("PLURALITY_TYPE"))) {
			request.setAttribute("PLURALITY_TYPE", "1685");
		}

		if ("1685".equals(request.getAttribute("PLURALITY_TYPE"))
				|| "1685".equals(request.getParameter("PLURALITY_TYPE"))) {
			// System.out.println("执行兼职发令");
			try {
				Map paramValueMap = new LinkedHashMap();
				paramValueMap.put("CPNY_ID", admin.getCpnyId());
				paramValueMap.put("TYPE", "plurality");

				paramValueMap.put("interLanguage", admin.getLanguage());
				paramValueMap.put("interCpnyID", admin.getCpnyId());

				// 判断是否需要决裁1为开，0为关
				if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {
					// 判断是否有特殊设置
					for (int i = 0; i < check.length; i++) {
						Map paramMap = new LinkedHashMap();
						paramMap.put("EMPID", check[i]);
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("PERSON_ID",
								request.getParameter("PERSON_ID_" + check[i]));
						paramMap.put("interLanguage", admin.getLanguage());
						paramMap.put("DEPT_NO",
								((LinkedHashMap) transferOrderDao
										.getViewUpgradeList(paramMap).get(0))
										.get("DEPTNO"));
						paramMap.put("TRANS_NO", "1361");
						paramMap.put("TRANS_CODE", "1685");
						paramMap.put("POSITION_NO",
								request.getParameter("POSITION_NO_" + check[i]));
						paramMap.put("DEPTNO",
								request.getParameter("DEPTNO_" + check[i]));
						paramMap.put("START_DATE",
								request.getParameter("START_DATE_" + check[i]));
						paramMap.put(
								"PLU_POSITION_NO",
								request.getParameter("PLU_POSITION_NO_"
										+ check[i]));
						paramMap.put("PLU_DEPTNO",
								request.getParameter("DEPTNO_" + check[i]));
						paramMap.put("PLU_DUTY_NO",
								request.getParameter("PLU_DUTY_NO_" + check[i]));
						paramMap.put("CREATED_BY", admin.getPersonId());
						String expInsideNo = transferOrderDao
								.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("PLU_REASON",
								request.getParameter("PLU_REASON_" + check[i]));
						paramMap.put("TYPE", "plurality");
						paramMap.put("ACTIVITY", 0);

						// 取出被发令人的决裁者信息（个人设置优先）
						List affirmorList = transferOrderDao
								.getAffirmorIdListByPersonal(paramMap);
						// 如果特殊设置不为空
						if (affirmorList.size() != 0) {
							for (int j = 0; j < affirmorList.size(); j++) {
								// 取当前决裁者
								if (j == 0) {
									paramMap.put("CURRENT_AFFIRM_ID",
											((LinkedHashMap) affirmorList
													.get(j)).get("AFFIRMOR_ID"));
								}
							}
							// 保存发令信息
							transferOrderDao
									.saveExperienceForPluralityAndAffirmList(
											paramMap, affirmorList);
						} else {
							// 取出被发令人的决裁者信息(部门设置)
							List affirmorListByDept = transferOrderDao
									.getAffirmorIdListByDept(paramMap);

							if (affirmorListByDept.size() != 0) {
								for (int j = 0; j < affirmorListByDept.size(); j++) {
									// 取当前决裁者
									if (j == 0) {
										paramMap.put(
												"CURRENT_AFFIRM_ID",
												((LinkedHashMap) affirmorListByDept
														.get(j))
														.get("AFFIRMOR_ID"));
									}
								}
								// 保存发令信息
								transferOrderDao
										.saveExperienceForPluralityAndAffirmList(
												paramMap, affirmorListByDept);
							} else {
								// 自定义取决裁者
								Map flagMap = getAffiram(paramMap, expInsideNo,
										request, admin);
								String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap
										.get("AFFIRM_FLAG").toString() : "";

								if ("2".equals(flag)) {
									return 2;
								}
								List newSaveAffirmorList = (List) paramMap
										.get("newSaveAffirmorList");

								transferOrderDao
										.saveExperienceForPluralityAndAffirmList(
												paramMap, newSaveAffirmorList);
							}
						}

					}
				} else {
					for (int i = 0; i < check.length; i++) {
						Map paramMap = new LinkedHashMap();
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("PERSON_ID",
								request.getParameter("PERSON_ID_" + check[i]));
						paramMap.put("interLanguage", admin.getLanguage());
						paramMap.put("DEPT_NO",
								((LinkedHashMap) transferOrderDao
										.getViewUpgradeList(paramMap).get(0))
										.get("DEPTNO"));
						paramMap.put("TRANS_NO", "1361");
						paramMap.put("TRANS_CODE", "1685");
						paramMap.put("POSITION_NO",
								request.getParameter("POSITION_NO_" + check[i]));
						paramMap.put("DEPTNO",
								request.getParameter("DEPTNO_" + check[i]));
						paramMap.put(
								"PLU_POSITION_NO",
								request.getParameter("PLU_POSITION_NO_"
										+ check[i]));
						paramMap.put("PLU_DEPTNO",
								request.getParameter("DEPTNO_" + check[i]));
						paramMap.put("PLU_DUTY_NO",
								request.getParameter("PLU_DUTY_NO_" + check[i]));
						paramMap.put("CREATED_BY", admin.getPersonId());
						paramMap.put("START_DATE",
								request.getParameter("START_DATE_" + check[i]));
						String expInsideNo = transferOrderDao
								.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("TYPE", "plurality");

						DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
						String temp_strS = DateUtil.getSysdateStr();
						String joinCompanyDateS = request
								.getParameter("START_DATE_" + check[i]);
						Date joinCompanyDate = dateSys.parse(joinCompanyDateS);
						Date temp_str = dateSys.parse(temp_strS);

						if (joinCompanyDate.before(temp_str)
								|| joinCompanyDate.equals(temp_str)) {
							paramMap.put("ACTIVITY", 1);
							transferOrderDao
									.saveExperienceForPlurality(paramMap);
						} else {
							paramMap.put("ACTIVITY", 0);
							transferOrderDao
									.saveExperienceForPlurality(paramMap);
						}
					}
				}
				result = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// System.out.println("执行解除兼职发令");

			try {

				Map paramValueMap = new LinkedHashMap();
				paramValueMap.put("CPNY_ID", admin.getCpnyId());
				paramValueMap.put("TYPE", "plurality");

				paramValueMap.put("interLanguage", admin.getLanguage());
				paramValueMap.put("interCpnyID", admin.getCpnyId());

				// 判断是否需要决裁1为开，0为关
				if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {
					// 判断是否有特殊设置
					for (int i = 0; i < check.length; i++) {
						Map paramMap = new LinkedHashMap();
						paramMap.put("EMPID", check[i]);
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("PERSON_ID",
								request.getParameter("PERSON_ID_" + check[i]));
						paramMap.put("interLanguage", admin.getLanguage());
						paramMap.put("TRANS_NO", "1361");
						paramMap.put("TRANS_CODE", "16176");
						paramMap.put("DEPT_NO",
								((LinkedHashMap) transferOrderDao
										.getViewUpgradeList(paramMap).get(0))
										.get("DEPTNO"));
						String expInsideNo = transferOrderDao
								.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("PLU_REASON",
								request.getParameter("PLU_REASON_" + check[i]));
						paramMap.put("START_DATE",
								request.getParameter("START_DATE_" + check[i]));
						paramMap.put("TYPE", "plurality");
						paramMap.put("ACTIVITY", 0);

						Map pu = (Map) transferOrderDao.getPluralityForRemove(
								paramMap).get(0);
						paramMap.put("TRANS_NO", pu.get("TRANS_NO"));
						paramMap.put("PLU_DEPTNO", pu.get("PLU_DEPTNO"));
						paramMap.put("PLU_TYPE_CODE", pu.get("PLU_TYPE_CODE"));
						paramMap.put("PLU_DUTY_NO", pu.get("PLU_DUTY_NO"));
						paramMap.put("CREATED_BY", admin.getPersonId());
						paramMap.put("RELATION_EXP_INSIDE_NO",
								transferOrderDao.getExpInsideNo(paramMap));

						// 查看这个人有没有做过 解除兼职 发令。 如果有,看看决裁是否通过（是否生效）
						// 如果没决裁就不能发此人的取消兼职令 ，如果没有是决裁否决 那可以继续发令
						// 如果之前的解除兼职发令生效了active=1 那么这个人就不应该出现在 解除兼职发令列表里
						if (transferOrderDao.checkedExpInsideNo(paramMap) > 0) {
							return 2;// return2 表示 此人不能发解除兼职领。 1表示成功
						} else {

							// 取出被发令人的决裁者信息（个人设置优先）
							List affirmorList = transferOrderDao
									.getAffirmorIdListByPersonal(paramMap);
							// 如果特殊设置不为空
							if (affirmorList.size() != 0) {
								for (int j = 0; j < affirmorList.size(); j++) {
									// 取当前决裁者
									if (j == 0) {
										paramMap.put("CURRENT_AFFIRM_ID",
												((LinkedHashMap) affirmorList
														.get(j))
														.get("AFFIRMOR_ID"));
									}
								}
								// 保存发令信息
								transferOrderDao
										.saveExperienceForRemovePluralityAndAffirmList(
												paramMap, affirmorList);
							} else {
								// 取出被发令人的决裁者信息(部门设置)
								List affirmorListByDept = transferOrderDao
										.getAffirmorIdListByDept(paramMap);
								if (affirmorListByDept.size() != 0) {
									for (int j = 0; j < affirmorListByDept
											.size(); j++) {
										// 取当前决裁者
										if (j == 0) {
											paramMap.put(
													"CURRENT_AFFIRM_ID",
													((LinkedHashMap) affirmorListByDept
															.get(j))
															.get("AFFIRMOR_ID"));
										}
									}
									// 保存发令信息
									transferOrderDao
											.saveExperienceForRemovePluralityAndAffirmList(
													paramMap,
													affirmorListByDept);
								} else {
									// 自定义取决裁者
									Map flagMap = getAffiram(paramMap,
											expInsideNo, request, admin);
									String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap
											.get("AFFIRM_FLAG").toString() : "";

									if ("2".equals(flag)) {
										return 2;
									}
									List newSaveAffirmorList = (List) paramMap
											.get("newSaveAffirmorList");

									transferOrderDao
											.saveExperienceForRemovePluralityAndAffirmList(
													paramMap,
													newSaveAffirmorList);
								}
							}

						}
					}

				} else {
					for (int i = 0; i < check.length; i++) {

						Map paramMap = new LinkedHashMap();
						paramMap.put("EMPID", check[i]);
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("PERSON_ID",
								request.getParameter("PERSON_ID_" + check[i]));
						paramMap.put("interLanguage", admin.getLanguage());
						paramMap.put("TRANS_NO", "1361");
						paramMap.put("TRANS_CODE", "16176");
						paramMap.put("DEPT_NO",
								((LinkedHashMap) transferOrderDao
										.getViewUpgradeList(paramMap).get(0))
										.get("DEPTNO"));
						String expInsideNo = transferOrderDao
								.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("PLU_REASON",
								request.getParameter("PLU_REASON_" + check[i]));
						paramMap.put("START_DATE",
								request.getParameter("START_DATE_" + check[i]));
						paramMap.put("TYPE", "plurality");
						paramMap.put("CREATED_BY", admin.getPersonId());
						paramMap.put("ACTIVITY", 0);

						Map pu = (Map) transferOrderDao.getPluralityForRemove(
								paramMap).get(0);
						paramMap.put("TRANS_NO", pu.get("TRANS_NO"));
						paramMap.put("PLU_DEPTNO", pu.get("PLU_DEPTNO"));
						paramMap.put("PLU_TYPE_CODE", pu.get("PLU_TYPE_CODE"));
						paramMap.put("PLU_DUTY_NO", pu.get("PLU_DUTY_NO"));
						paramMap.put("PLU_POSITION_NO",
								pu.get("PLU_POSITION_NO"));

						paramMap.put("RELATION_EXP_INSIDE_NO",
								transferOrderDao.getExpInsideNo(paramMap));

						DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
						String temp_strS = DateUtil.getSysdateStr();
						String joinCompanyDateS = request
								.getParameter("START_DATE_" + check[i]);
						Date joinCompanyDate = dateSys.parse(joinCompanyDateS);
						Date temp_str = dateSys.parse(temp_strS);

						if (joinCompanyDate.before(temp_str)
								|| joinCompanyDate.equals(temp_str)) {
							paramMap.put("ACTIVITY", 1);
							paramMap.put("UPDATED_BY", admin.getPersonId());
							transferOrderDao
									.saveExperienceForRemovePluralityAndUpPlurality(paramMap);
							// transferOrderDao.updatePlurality(paramMap);
						} else {
							paramMap.put("ACTIVITY", 0);
							transferOrderDao
									.saveExperienceForRemovePlurality(paramMap);
						}
					}

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
			result = 1;
		}

		return result;

	}

	/**
	 * 停职发令查询(Suspended Query)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getSuspendList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		if (paramMap != null && paramMap.get("SUSPEND_TYPE") == null) {
			paramMap.put("SUSPEND_TYPE_SUSPENSION", "SUSPEND_TYPE_SUSPENSION");
		}
		if (paramMap != null && "16194".equals(paramMap.get("SUSPEND_TYPE"))) {
			paramMap.put("SUSPEND_TYPE_SUSPENSION", "SUSPEND_TYPE_SUSPENSION");
		}
		if (paramMap != null && "16195".equals(paramMap.get("SUSPEND_TYPE"))) {
			paramMap.put("SUSPEND_TYPE_REINSTATED", "SUSPEND_TYPE_REINSTATED");
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getSuspendList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getSuspendList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 停职发令条数查询(Suspended Query count)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public int getSuspendListCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		if (paramMap != null && paramMap.get("SUSPEND_TYPE") == null) {
			paramMap.put("SUSPEND_TYPE_SUSPENSION", "SUSPEND_TYPE_SUSPENSION");
		}
		if (paramMap != null && "16194".equals(paramMap.get("SUSPEND_TYPE"))) {
			paramMap.put("SUSPEND_TYPE_SUSPENSION", "SUSPEND_TYPE_SUSPENSION");
		}
		if (paramMap != null && "16195".equals(paramMap.get("SUSPEND_TYPE"))) {
			paramMap.put("SUSPEND_TYPE_REINSTATED", "SUSPEND_TYPE_REINSTATED");
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		retrunInt = transferOrderDao.getSuspendListCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 检查是否可以做停职发令
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkSaveSuspend(HttpServletRequest request) {

		int result = 0;
		String check[] = request.getParameterValues("suspend");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			paramMap.put("PERSON_ID",
					request.getParameter("PERSON_ID_" + check[i]));
			result += this.transferOrderDao.checkSaveSuspend(paramMap);
		}
		return result;
	}

	/**
	 * 保存停职发令(save suspend)
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveSuspend(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("suspend");
		int result = 0;
		try {
			Map paramValueMap = new LinkedHashMap();
			paramValueMap.put("CPNY_ID", admin.getCpnyId());
			paramValueMap.put("TYPE", "suspend");

			if ("".equals(request.getParameter("SUSPEND_TYPE"))) {
				request.setAttribute("SUSPEND_TYPE", "16194");
			}

			if ("16194".equals(request.getAttribute("SUSPEND_TYPE"))
					|| "16194".equals(request.getParameter("SUSPEND_TYPE"))) {
//				System.out.println("停职发令start");
				// 判断是否需要决裁1为开，0为关
				if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {
					// 判断是否有特殊设置
					for (int i = 0; i < check.length; i++) {
						Map paramMap = new LinkedHashMap();
						paramMap.put("EMPID", check[i]);
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("PERSON_ID",
								request.getParameter("PERSON_ID_" + check[i]));
						paramMap.put("interLanguage", admin.getLanguage());
						paramMap.put("TRANS_NO", "16193");
						paramMap.put("TRANS_CODE", "16194");
						paramMap.put(
								"SUSPEND_REASON",
								request.getParameter("SUSPEND_REASON_"
										+ check[i]));
						paramMap.put("CREATED_BY", admin.getPersonId());
						paramMap.put("START_DATE",
								request.getParameter("START_DATE_" + check[i]));
						String expInsideNo = transferOrderDao
								.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("STATUS_CODE",
								request.getParameter("STATUS_CODE_" + check[i]));
						paramMap.put("TYPE", "suspend");
						paramMap.put("ACTIVITY", 0);

						// 取出被发令人的决裁者信息（个人设置优先）
						List affirmorList = transferOrderDao
								.getAffirmorIdListByPersonal(paramMap);
						// 如果特殊设置不为空
						if (affirmorList.size() != 0) {
							for (int j = 0; j < affirmorList.size(); j++) {
								// 取当前决裁者
								if (j == 0) {
									paramMap.put("CURRENT_AFFIRM_ID",
											((LinkedHashMap) affirmorList
													.get(j)).get("AFFIRMOR_ID"));
								}
							}
							// 保存发令信息
							transferOrderDao
									.saveExperienceForSuspendAndAffirmList(
											paramMap, affirmorList);
						} else {
							// 取出被发令人的决裁者信息(部门设置)
							List affirmorListByDept = transferOrderDao
									.getAffirmorIdListByDept(paramMap);
							if (affirmorListByDept.size() != 0) {
								for (int j = 0; j < affirmorListByDept.size(); j++) {
									// 取当前决裁者
									if (j == 0) {
										paramMap.put(
												"CURRENT_AFFIRM_ID",
												((LinkedHashMap) affirmorListByDept
														.get(j))
														.get("AFFIRMOR_ID"));
									}
								}
								// 保存发令信息
								transferOrderDao
										.saveExperienceForSuspendAndAffirmList(
												paramMap, affirmorListByDept);
							} else {
								// 自定义取决裁者
								Map flagMap = getAffiram(paramMap, expInsideNo,
										request, admin);
								String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap
										.get("AFFIRM_FLAG").toString() : "";

								if ("2".equals(flag)) {
									return 2;
								}
								List newSaveAffirmorList = (List) paramMap
										.get("newSaveAffirmorList");

								transferOrderDao
										.saveExperienceForSuspendAndAffirmList(
												paramMap, newSaveAffirmorList);
							}
						}

					}
				} else {
					for (int i = 0; i < check.length; i++) {
						Map paramMap = new LinkedHashMap();
						paramMap.put("EMPID", check[i]);
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("ADMIN_ID", admin.getAdminID());
						paramMap.put("PERSON_ID",
								request.getParameter("PERSON_ID_" + check[i]));
						paramMap.put("interLanguage", admin.getLanguage());
						paramMap.put("TRANS_NO", "16193");
						paramMap.put("TRANS_CODE", "16194");
						paramMap.put(
								"SUSPEND_REASON",
								request.getParameter("SUSPEND_REASON_"
										+ check[i]));
						paramMap.put("CREATED_BY", admin.getPersonId());
						String expInsideNo = transferOrderDao
								.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("START_DATE",
								request.getParameter("START_DATE_" + check[i]));
						paramMap.put("STATUS_CODE",
								request.getParameter("STATUS_CODE_" + check[i]));

						// 调用存储过程所用参数start
						paramMap.put("AR_FROM_TIME",
								request.getParameter("START_DATE_" + check[i]));
						paramMap.put("AR_TO_TIME", "9999-12-31");
						paramMap.put("caltype", "emp");
						// 调用存储过程所用参数end

						DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
						String temp_strS = DateUtil.getSysdateStr();
						String joinCompanyDateS = request
								.getParameter("START_DATE_" + check[i]);
						Date joinCompanyDate = dateSys.parse(joinCompanyDateS);
						Date temp_str = dateSys.parse(temp_strS);

						if (joinCompanyDate.before(temp_str)
								|| joinCompanyDate.equals(temp_str)) {
							paramMap.put("ACTIVITY", 1);
							// transferOrderDao.saveExperienceForSuspend(paramMap);
							transferOrderDao
									.updateHrEmployeeForSuspend(paramMap);
						} else {
							paramMap.put("ACTIVITY", 0);
							transferOrderDao
									.saveExperienceForSuspendNoaffirm(paramMap);
						}
					}
				}
			} else {
//				System.out.println("复职发令start");
				// start
				if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {
					// 判断是否有特殊设置
					for (int i = 0; i < check.length; i++) {
						Map paramMap = new LinkedHashMap();
						paramMap.put("EMPID", check[i]);
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("PERSON_ID",
								request.getParameter("PERSON_ID_" + check[i]));
						paramMap.put("interLanguage", admin.getLanguage());
						paramMap.put("TRANS_NO", "16193");
						paramMap.put("TRANS_CODE", "16195");
						paramMap.put(
								"SUSPEND_REASON",
								request.getParameter("SUSPEND_REASON_"
										+ check[i]));
						paramMap.put("CREATED_BY", admin.getPersonId());
						paramMap.put("START_DATE",
								request.getParameter("START_DATE_" + check[i]));
						String expInsideNo = transferOrderDao
								.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("STATUS_CODE",
								request.getParameter("STATUS_CODE_" + check[i]));
						paramMap.put("TYPE", "suspend");
						paramMap.put("RELATION_EXP_INSIDE_NO", transferOrderDao
								.getExpInsideNoForSuspend(paramMap));
						paramMap.put("ACTIVITY", 0);
						// 取出被发令人的决裁者信息（个人设置优先）
						List affirmorList = transferOrderDao
								.getAffirmorIdListByPersonal(paramMap);
						// 如果特殊设置不为空
						if (affirmorList.size() != 0) {
							for (int j = 0; j < affirmorList.size(); j++) {
								// 取当前决裁者
								if (j == 0) {
									paramMap.put("CURRENT_AFFIRM_ID",
											((LinkedHashMap) affirmorList
													.get(j)).get("AFFIRMOR_ID"));
								}
							}
							// 保存发令信息
							transferOrderDao
									.saveExperienceForSuspendAndAffirmList(
											paramMap, affirmorList);
						} else {
							// 取出被发令人的决裁者信息(部门设置)
							List affirmorListByDept = transferOrderDao
									.getAffirmorIdListByDept(paramMap);
							if (affirmorListByDept.size() != 0) {
								for (int j = 0; j < affirmorListByDept.size(); j++) {
									// 取当前决裁者
									if (j == 0) {
										paramMap.put(
												"CURRENT_AFFIRM_ID",
												((LinkedHashMap) affirmorListByDept
														.get(j))
														.get("AFFIRMOR_ID"));
									}
								}
								// 保存发令信息
								transferOrderDao
										.saveExperienceForSuspendAndAffirmList(
												paramMap, affirmorListByDept);
							} else {
								// 自定义取决裁者
								Map flagMap = getAffiram(paramMap, expInsideNo,
										request, admin);
								String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap
										.get("AFFIRM_FLAG").toString() : "";

								if ("2".equals(flag)) {
									return 2;
								}
								List newSaveAffirmorList = (List) paramMap
										.get("newSaveAffirmorList");

								transferOrderDao
										.saveExperienceForSuspendAndAffirmList(
												paramMap, newSaveAffirmorList);
							}
						}

					}
				} else {
					for (int i = 0; i < check.length; i++) {
						Map paramMap = new LinkedHashMap();
						paramMap.put("EMPID", check[i]);
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("ADMIN_ID", admin.getAdminID());
						paramMap.put("PERSON_ID",
								request.getParameter("PERSON_ID_" + check[i]));
						paramMap.put("interLanguage", admin.getLanguage());
						paramMap.put("TRANS_NO", "16193");
						paramMap.put("TRANS_CODE", "16195");
						paramMap.put(
								"SUSPEND_REASON",
								request.getParameter("SUSPEND_REASON_"
										+ check[i]));
						paramMap.put("CREATED_BY", admin.getPersonId());
						String expInsideNo = transferOrderDao
								.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("START_DATE",
								request.getParameter("START_DATE_" + check[i]));
						paramMap.put("STATUS_CODE",
								request.getParameter("STATUS_CODE_" + check[i]));

						paramMap.put("RELATION_EXP_INSIDE_NO", transferOrderDao
								.getExpInsideNoForSuspend(paramMap));

						// 调用存储过程参数start
						Calendar c = Calendar.getInstance();
						int year = c.get(Calendar.YEAR);
						int month = c.get(Calendar.MONTH);
						c.set(c.YEAR, c.get(Calendar.YEAR));
						c.set(c.MONTH, c.get(Calendar.MONTH));
						String fromTime = year + "-" + (month + 1) + "-"
								+ c.getActualMinimum(c.DAY_OF_MONTH);
						String toTIme = year + "-" + (month + 1) + "-"
								+ c.getActualMaximum(c.DAY_OF_MONTH);

						paramMap.put("AR_FROM_TIME", fromTime);
						paramMap.put("AR_TO_TIME", toTIme);
						paramMap.put("caltype", "emp");
						// 调用存储过程参数end

						DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
						String temp_strS = DateUtil.getSysdateStr();
						String joinCompanyDateS = request
								.getParameter("START_DATE_" + check[i]);
						Date joinCompanyDate = dateSys.parse(joinCompanyDateS);
						Date temp_str = dateSys.parse(temp_strS);

						if (joinCompanyDate.before(temp_str)
								|| joinCompanyDate.equals(temp_str)) {
							paramMap.put("ACTIVITY", 1);
							paramMap.put("UPDATED_BY", admin.getPersonId());
							// transferOrderDao.saveExperienceForSuspend(paramMap);
							transferOrderDao
									.updateHrEmployeeForReinstated(paramMap);//
							// transferOrderDao.updateSuspend(paramMap);
						} else {
							paramMap.put("ACTIVITY", 0);
							transferOrderDao
									.saveExperienceForSuspendNoaffirm(paramMap);
						}

					}

				}

				// end
			}

			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 奖励发令查询(reward Query)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getRewardList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getRewardList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getRewardList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 奖励发令条数查询(reward Query count)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public int getRewardListCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		retrunInt = transferOrderDao.getRewardListCnt(paramMap);

		return retrunInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getOrderParmList(HttpServletRequest request, String sortNameNo)throws Exception {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("SORT_TYPE_NO", sortNameNo);
		retrunList = transferOrderDao.getOrderParmList(paramMap) ;
		
		return retrunList ;
	}
	
	/**
	 * 检查是否可以做奖励发令
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkSaveReward(HttpServletRequest request) {

		int result = 0;
		String check[] = request.getParameterValues("reward");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			paramMap.put("PERSON_ID",
					request.getParameter("PERSON_ID_" + check[i]));
			paramMap.put("REWARD_DATE",
					request.getParameter("REWARD_DATE_" + check[i]));
			result += this.transferOrderDao.checkSaveReward(paramMap);
		}
		return result;
	}

	/**
	 * 根据EMP_ID查询员工
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getTransferOrderEmpList(HttpServletRequest request)
			throws Exception {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("deptNo",admin.getDeptNo());
		paramMap.put("userNo", admin.getUserNo());
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID());
		}

		if (UiUtil.getPageNum(request) > 0){
			retrunList = transferOrderDao.getTransferOrderEmpList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = transferOrderDao.getTransferOrderEmpList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * 保存“奖励”发令信息(临时储存)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int storeReward(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("reward");
		int result = 0;
		try {
			LinkedHashMap map=new LinkedHashMap();
			map.put("CREATED_BY", admin.getPersonId());
			map.put("TRANS_NO", "641");
			map.put("TABLE_NAME", "HR_REWARD_SAVE");
			map.put("CPNY_ID", admin.getCpnyId());
			for (int i = 0; i < check.length; i++) {
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("PERSON_ID",
						request.getParameter("reward_PERSONID_" + check[i]));
				paramMap.put("TRANS_CODE",
						request.getParameter("TRANS_CODE_" + check[i]));
				paramMap.put("TRANS_NO", "641");
				paramMap.put("POSITION_NO",
						request.getParameter("reward_POSITION_NO_" + check[i]));
				paramMap.put("POST_NO",
						request.getParameter("reward_POST_NO_" + check[i]));
				paramMap.put("ORDERNO", i);
				paramMap.put("DEPTNO",
						request.getParameter("reward_DEPTNO_" + check[i]));
				paramMap.put("CREATED_BY", admin.getPersonId());
				paramMap.put("REWARD_DATE",
						request.getParameter("REWARD_DATE_" + check[i]));
				paramMap.put("REWARD_CONTENTS",
						request.getParameter("REWARD_CONTENTS_" + check[i]));
				paramMap.put("REWARD_BONUS",
						request.getParameter("REWARD_MONEY_" + check[i]));
				paramMap.put("EXP_INSIDE_NO", request.getParameter("reward_EXP_INSIDE_NO_"+check[i]));
				paramMap.put("CPNY_ID", admin.getCpnyId());
				this.transferOrderDao.storeReward(paramMap);
			}
			result= 1;
		} catch (Exception e) {
			e.printStackTrace();
			result= 2;
		}
		return result;
	}
	
	/**
	 * 查询"奖励"保存过的记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getStoredRewardList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap=new LinkedHashMap();
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("TRANS_NO", "641");//"641"表示奖励
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("DEPTNO1", admin.getDeptNo());
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("interCpnyID", admin.getCpnyId());
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		List rewardList=this.transferOrderDao.getStoredRewardList(paramMap);
		return rewardList;
	}
	
	/**
	 * 奖励发令()
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveReward(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("reward");
		int result = 0;
		try {
			Map paramValueMap = new LinkedHashMap();
			paramValueMap.put("CPNY_ID", admin.getCpnyId());
			paramValueMap.put("TYPE", "reward");
			// 判断是否需要决裁1为开，0为关
			if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {
				// 判断是否有特殊设置
				for (int i = 0; i < check.length; i++) {
					Map paramMap = new LinkedHashMap();
					paramMap.put("EMPID", check[i]);
					paramMap.put("CPNY_ID", admin.getCpnyId());
					paramMap.put("PERSON_ID",
							request.getParameter("reward_PERSONID_" + check[i]));
					paramMap.put("interLanguage", admin.getLanguage());
					paramMap.put("TRANS_CODE",
							request.getParameter("TRANS_CODE_" + check[i]));
					paramMap.put("TRANS_NO", "641");
					paramMap.put("POSITION_NO",
							request.getParameter("reward_POSITION_NO_" + check[i]));
					paramMap.put("POST_NO",
							request.getParameter("reward_POST_NO_" + check[i]));
					paramMap.put("DEPTNO",
							request.getParameter("reward_DEPTNO_" + check[i]));
					paramMap.put("CREATED_BY", admin.getPersonId());
					paramMap.put("REWARD_DATE",
							request.getParameter("REWARD_DATE_" + check[i]));
					paramMap.put("REWARD_CONTENTS",
							request.getParameter("REWARD_CONTENTS_" + check[i]));
					paramMap.put("REWARD_BONUS",
							request.getParameter("REWARD_MONEY_" + check[i]));
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("TYPE", "reward");
					paramMap.put("ACTIVITY", 0);
					paramMap.put("TRANS_NUM", new Integer(request.getParameter("transferOrder_no"))+i);

					// 取出被发令人的决裁者信息（个人设置优先）
					List affirmorList = transferOrderDao
							.getAffirmorIdListByPersonal(paramMap);
					// 如果特殊设置不为空
					if (affirmorList.size() != 0) {
						for (int j = 0; j < affirmorList.size(); j++) {
							// 取当前决裁者
							if (j == 0) {
								paramMap.put("CURRENT_AFFIRM_ID",
										((LinkedHashMap) affirmorList.get(j))
												.get("AFFIRMOR_ID"));
							}
						}
						// 保存发令信息
						transferOrderDao.saveExperienceForRewardAndAffirmList(
								paramMap, affirmorList);
						
						//删除临时保存的“奖励”发令记录
						this.transferOrderDao.deleteStoredRewardRecordsByParams(paramMap);
					} else {
						// 取出被发令人的决裁者信息(部门设置)
						List affirmorListByDept = transferOrderDao
								.getAffirmorIdListByDept(paramMap);
						if (affirmorListByDept.size() != 0) {
							for (int j = 0; j < affirmorListByDept.size(); j++) {
								// 取当前决裁者
								if (j == 0) {
									paramMap.put("CURRENT_AFFIRM_ID",
											((LinkedHashMap) affirmorListByDept
													.get(j)).get("AFFIRMOR_ID"));
								}
							}
							// 保存发令信息
							transferOrderDao
									.saveExperienceForRewardAndAffirmList(
											paramMap, affirmorListByDept);
							//删除临时保存的“奖励”发令记录
							this.transferOrderDao.deleteStoredRewardRecordsByParams(paramMap);
						} else {
							// 自定义取决裁者
							Map flagMap = getAffiram(paramMap, expInsideNo,
									request, admin);
							String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap
									.get("AFFIRM_FLAG").toString() : "";

							if ("2".equals(flag)) {
								return 2;
							}
							List newSaveAffirmorList = (List) paramMap
									.get("newSaveAffirmorList");

							transferOrderDao
									.saveExperienceForRewardAndAffirmList(
											paramMap, newSaveAffirmorList);
							//删除临时保存的“奖励”发令记录
							this.transferOrderDao.deleteStoredRewardRecordsByParams(paramMap);
						}
					}

				}
			} else {
				for (int i = 0; i < check.length; i++) {
					Map paramMap = new LinkedHashMap();
					paramMap.put("EMPID", check[i]);
					paramMap.put("CPNY_ID", admin.getCpnyId());
					paramMap.put("PERSON_ID",
							request.getParameter("reward_PERSONID_" + check[i]));
					paramMap.put("interLanguage", admin.getLanguage());
					paramMap.put("TRANS_CODE",
							request.getParameter("TRANS_CODE_" + check[i]));
					paramMap.put("TRANS_NO", "641");
					paramMap.put("POSITION_NO",
							request.getParameter("reward_POSITION_NO_" + check[i]));
					paramMap.put("POST_NO",
							request.getParameter("reward_POST_NO_" + check[i]));
					paramMap.put("DEPTNO",
							request.getParameter("reward_DEPTNO_" + check[i]));
					paramMap.put("CREATED_BY", admin.getPersonId());
					paramMap.put("REWARD_DATE",
							request.getParameter("REWARD_DATE_" + check[i]));
					paramMap.put("REWARD_CONTENTS",
							request.getParameter("REWARD_CONTENTS_" + check[i]));
					paramMap.put("REWARD_BONUS",
							request.getParameter("REWARD_MONEY_" + check[i]));
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("TYPE", "reward");
					paramMap.put("TRANS_NUM", new Integer(request.getParameter("transferOrder_no"))+i);
					DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
					String temp_strS = DateUtil.getSysdateStr();
					String joinCompanyDateS = request
							.getParameter("REWARD_DATE_" + check[i]);
					Date joinCompanyDate = dateSys.parse(joinCompanyDateS);
					Date temp_str = dateSys.parse(temp_strS);

					if (joinCompanyDate.before(temp_str)
							|| joinCompanyDate.equals(temp_str)) {
						paramMap.put("ACTIVITY", 1);
						transferOrderDao.saveExperienceForReward(paramMap);
						//删除临时保存的“奖励”发令记录
						this.transferOrderDao.deleteStoredRewardRecordsByParams(paramMap);
					} else {
						paramMap.put("ACTIVITY", 0);
						transferOrderDao.saveExperienceForReward(paramMap);
						//删除临时保存的“奖励”发令记录
						this.transferOrderDao.deleteStoredRewardRecordsByParams(paramMap);
					}
				}
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 惩戒发令查询(punishment Query)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getPunishMentList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getPunishMentList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getPunishMentList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 惩戒发令条数查询(punishment Query count)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public int getPunishMentListCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		retrunInt = transferOrderDao.getPunishMentListCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 检查是否可以做惩戒发令
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkSavePunishMent(HttpServletRequest request) {

		int result = 0;
		String check[] = request.getParameterValues("punishment");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			paramMap.put("PERSON_ID",
					request.getParameter("punishment_EMPID_" + check[i]));
			paramMap.put("PUN_TYPE_ID",
					request.getParameter("TRANS_CODE_" + check[i]));
			paramMap.put("DATE_PUNISHED",
					request.getParameter("PUNISHMENT_DATE_" + check[i]));

			result += this.transferOrderDao.checkSavePunishMent(paramMap);
		}
		return result;
	}

	/**
	 * 保存"惩罚"发令信息(临时储存)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int storePunishment(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("punishment");
		int result = 0;
		try {
			LinkedHashMap map=new LinkedHashMap();
			map.put("CREATED_BY", admin.getPersonId());
			map.put("TRANS_NO", "642");
			map.put("TABLE_NAME", "HR_PUNISHMENT_SAVE");
			map.put("CPNY_ID", admin.getCpnyId());
			for (int i = 0; i < check.length; i++) {
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("PERSON_ID",
						request.getParameter("punishment_PERSONID_" + check[i]));
				paramMap.put("TRANS_NO", "642");
				paramMap.put("TRANS_CODE",
						request.getParameter("TRANS_CODE_" + check[i]));
				paramMap.put("PUN_TYPE_ID",
						request.getParameter("TRANS_CODE_" + check[i]));
				paramMap.put("POSITION_NO",
						request.getParameter("punishment_POSITION_NO_" + check[i]));
				paramMap.put("POST_NO",
						request.getParameter("punishment_POST_NO_" + check[i]));
				paramMap.put("ORDERNO", i);
				paramMap.put("DEPTNO",
						request.getParameter("punishment_DEPTNO_" + check[i]));
				paramMap.put("CREATED_BY", admin.getPersonId());
				paramMap.put("DATE_PUNISHED",
						request.getParameter("PUNISHMENT_DATE_" + check[i]));
				paramMap.put("PUN_REASON",
						request.getParameter("PUNISHMENT_CONTENTS_" + check[i]));
				paramMap.put("PUN_BONUS",
						request.getParameter("PUNISHMENT_MONEY_" + check[i]));
				paramMap.put("EXP_INSIDE_NO", request.getParameter("punishment_EXP_INSIDE_NO_"+check[i]));
				paramMap.put("CPNY_ID", admin.getCpnyId());
				this.transferOrderDao.storePunishment(paramMap);
			}
			result= 1;
		} catch (Exception e) {
			e.printStackTrace();
			result= 2;
		}
		return result;
	}

	/**
	 * 查询"惩罚"保存过的记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getStoredPunishmentList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap=new LinkedHashMap();
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("TRANS_NO", "642");//"642"表示惩罚
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("DEPTNO1", admin.getDeptNo());
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("interCpnyID", admin.getCpnyId());
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		return this.transferOrderDao.getStoredPunishmentList(paramMap);
	}

	/**
	 * 惩戒发令()
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int savePunishMent(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("punishment");
		int result = 0;
		try {
			Map paramValueMap = new LinkedHashMap();
			paramValueMap.put("CPNY_ID", admin.getCpnyId());
			paramValueMap.put("TYPE", "punishMent");
			// 判断是否需要决裁1为开，0为关
			if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {
				// 判断是否有特殊设置
				for (int i = 0; i < check.length; i++) {
					Map paramMap = new LinkedHashMap();
					paramMap.put("EMPID", check[i]);
					paramMap.put("CPNY_ID", admin.getCpnyId());
					paramMap.put("PERSON_ID",
							request.getParameter("punishment_PERSONID_" + check[i]));
					paramMap.put("interLanguage", admin.getLanguage());
					paramMap.put("TRANS_CODE",
							request.getParameter("TRANS_CODE_" + check[i]));
					paramMap.put("PUN_TYPE_ID",
							request.getParameter("TRANS_CODE_" + check[i]));
					paramMap.put("TRANS_NO", "642");
					paramMap.put("POSITION_NO",
							request.getParameter("punishment_POSITION_NO_" + check[i]));
					paramMap.put("POST_NO",
							request.getParameter("punishment_POST_NO_" + check[i]));
					paramMap.put("DEPTNO",
							request.getParameter("punishment_DEPTNO_" + check[i]));
					paramMap.put("CREATED_BY", admin.getPersonId());
					paramMap.put("DATE_PUNISHED",
							request.getParameter("PUNISHMENT_DATE_" + check[i]));
					paramMap.put("PUN_REASON",
							request.getParameter("PUNISHMENT_CONTENTS_" + check[i]));
					paramMap.put("PUN_BONUS",
							request.getParameter("PUNISHMENT_MONEY_" + check[i]));
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("TYPE", "punishMent");
					paramMap.put("ACTIVITY", 0);
					paramMap.put("TRANS_NUM", new Integer(request.getParameter("transferOrder_no"))+i);
					
					// 取出被发令人的决裁者信息（个人设置优先）
					List affirmorList = transferOrderDao
							.getAffirmorIdListByPersonal(paramMap);
					// 如果特殊设置不为空
					if (affirmorList.size() != 0) {
						for (int j = 0; j < affirmorList.size(); j++) {
							// 取当前决裁者
							if (j == 0) {
								paramMap.put("CURRENT_AFFIRM_ID",
										((LinkedHashMap) affirmorList.get(j))
												.get("AFFIRMOR_ID"));
							}
						}
						// 保存发令信息
						transferOrderDao
								.saveExperienceForPunishMentAndAffirmList(
										paramMap, affirmorList);
						//删除临时保存的“惩罚”发令记录
						this.transferOrderDao.deleteStoredPunishmentRecordsByParams(paramMap);
					} else {
						// 取出被发令人的决裁者信息(部门设置)
						List affirmorListByDept = transferOrderDao
								.getAffirmorIdListByDept(paramMap);
						if (affirmorListByDept.size() != 0) {
							for (int j = 0; j < affirmorListByDept.size(); j++) {
								// 取当前决裁者
								if (j == 0) {
									paramMap.put("CURRENT_AFFIRM_ID",
											((LinkedHashMap) affirmorListByDept
													.get(j)).get("AFFIRMOR_ID"));
								}
							}
							// 保存发令信息
							transferOrderDao
									.saveExperienceForPunishMentAndAffirmList(
											paramMap, affirmorListByDept);
							//删除临时保存的“惩罚”发令记录
							this.transferOrderDao.deleteStoredPunishmentRecordsByParams(paramMap);
						} else {
							// 自定义取决裁者
							Map flagMap = getAffiram(paramMap, expInsideNo,
									request, admin);
							String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap
									.get("AFFIRM_FLAG").toString() : "";

							if ("2".equals(flag)) {
								return 2;
							}
							List newSaveAffirmorList = (List) paramMap
									.get("newSaveAffirmorList");

							transferOrderDao
									.saveExperienceForPunishMentAndAffirmList(
											paramMap, newSaveAffirmorList);
							//删除临时保存的“惩罚”发令记录
							this.transferOrderDao.deleteStoredPunishmentRecordsByParams(paramMap);
						}
					}

				}
			} else {
				for (int i = 0; i < check.length; i++) {
					Map paramMap = new LinkedHashMap();
					paramMap.put("EMPID", check[i]);
					paramMap.put("CPNY_ID", admin.getCpnyId());
					paramMap.put("PERSON_ID",
							request.getParameter("punishment_PERSONID_" + check[i]));
					paramMap.put("interLanguage", admin.getLanguage());
					paramMap.put("TRANS_CODE",
							request.getParameter("TRANS_CODE_" + check[i]));
					paramMap.put("PUN_TYPE_ID",
							request.getParameter("TRANS_CODE_" + check[i]));
					paramMap.put("TRANS_NO", "642");
					paramMap.put("POSITION_NO",
							request.getParameter("punishment_POSITION_NO_" + check[i]));
					paramMap.put("POST_NO",
							request.getParameter("punishment_POST_NO_" + check[i]));
					paramMap.put("DEPTNO",
							request.getParameter("punishment_DEPTNO_" + check[i]));
					paramMap.put("CREATED_BY", admin.getPersonId());
					paramMap.put("DATE_PUNISHED",
							request.getParameter("PUNISHMENT_DATE_" + check[i]));
					paramMap.put("PUN_REASON",
							request.getParameter("PUNISHMENT_CONTENTS_" + check[i]));
					paramMap.put("PUN_BONUS",
							request.getParameter("PUNISHMENT_MONEY_" + check[i]));
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("TYPE", "punishMent");
					paramMap.put("TRANS_NUM", new Integer(request.getParameter("transferOrder_no"))+i);
					DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
					String temp_strS = DateUtil.getSysdateStr();
					String joinCompanyDateS = request
							.getParameter("PUNISHMENT_DATE_" + check[i]);
					Date joinCompanyDate = dateSys.parse(joinCompanyDateS);
					Date temp_str = dateSys.parse(temp_strS);

					if (joinCompanyDate.before(temp_str)
							|| joinCompanyDate.equals(temp_str)) {
						paramMap.put("ACTIVITY", 1);
						transferOrderDao.saveExperienceForPunishMent(paramMap);
						//删除临时保存的“惩罚”发令记录
						this.transferOrderDao.deleteStoredPunishmentRecordsByParams(paramMap);
					} else {
						paramMap.put("ACTIVITY", 0);
						transferOrderDao.saveExperienceForPunishMent(paramMap);
						//删除临时保存的“惩罚”发令记录
						this.transferOrderDao.deleteStoredPunishmentRecordsByParams(paramMap);
					}
				}
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 薪资调整查询(payrise Query)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getPayriseList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getPayriseList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getPayriseList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 薪资调整条数查询(payrise Query count)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public int getPayriseListCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		retrunInt = transferOrderDao.getPayriseListCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 查询pa_basic_item
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List paBasicItemList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		retrunList = transferOrderDao.paBasicItemList(paramMap);

		return retrunList;
	}

	@SuppressWarnings("unchecked")
	public List getReturnValueByItemNo(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		retrunList = transferOrderDao.getReturnValueByItemNo(paramMap);

		return retrunList;
	}

	/**
	 * 检查是否可做薪资调整发令( check payrise )
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkSavePayrise(HttpServletRequest request) {

		int result = 0;
		String check[] = request.getParameterValues("payrise");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			paramMap.put("PERSON_ID",
					request.getParameter("PERSON_ID_" + check[i]));
			result += this.transferOrderDao.checkSavePayrise(paramMap);
		}
		return result;
	}

	/**
	 * 检查是否可做薪资调整发令( check payrise by startDate )
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkSavePayriseByStartDate(HttpServletRequest request) {

		int result = 0;
		String check[] = request.getParameterValues("payrise");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			paramMap.put("PERSON_ID",
					request.getParameter("PERSON_ID_" + check[i]));
			paramMap.put("START_DATE",
					request.getParameter("START_DATE_" + check[i]));
			result += this.transferOrderDao
					.checkSavePayriseByStartDate(paramMap);
		}
		return result;
	}

	/**
	 * 保存薪资调整
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int savePayrise(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("payrise");
		int result = 0;
		try {
			Map paramValueMap = new LinkedHashMap();
			paramValueMap.put("CPNY_ID", admin.getCpnyId());
			paramValueMap.put("TYPE", "payrise");
			// 判断是否需要决裁1为开，0为关
			if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {
				// 判断是否有特殊设置
				for (int i = 0; i < check.length; i++) {
					Map paramMap = new LinkedHashMap();
					paramMap.put("EMPID", check[i]);
					paramMap.put("CPNY_ID", admin.getCpnyId());
					paramMap.put("PERSON_ID",
							request.getParameter("PERSON_ID_" + check[i]));
					paramMap.put("interLanguage", admin.getLanguage());
					paramMap.put("TRANS_NO", "16199");
					paramMap.put("TRANS_CODE", "16200");
					paramMap.put("POSITION_NO",
							request.getParameter("POSITION_NO_" + check[i]));
					paramMap.put("DEPTNO",
							request.getParameter("DEPTNO_" + check[i]));
					paramMap.put("DUTY_NO",
							request.getParameter("DUTY_NO_" + check[i]));
					paramMap.put("POST_GRADE_NO",
							request.getParameter("POST_GRADE_NO_" + check[i]));
					paramMap.put("RETURN_VALUE",
							request.getParameter("RETURN_VALUE_" + check[i]));
					paramMap.put("CREATED_BY", admin.getPersonId());
					paramMap.put("START_DATE",
							request.getParameter("START_DATE_" + check[i]));
					paramMap.put("ITEM_NO",
							request.getParameter("ITEM_NO_" + check[i]));
					paramMap.put("ADJUST_REASON",
							request.getParameter("ADJUST_REASON_" + check[i]));
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("TYPE", "payrise");
					paramMap.put("ACTIVITY", 0);

					// 取出被发令人的决裁者信息（个人设置优先）
					List affirmorList = transferOrderDao
							.getAffirmorIdListByPersonal(paramMap);
					// 如果特殊设置不为空
					if (affirmorList.size() != 0) {
						for (int j = 0; j < affirmorList.size(); j++) {
							// 取当前决裁者
							if (j == 0) {
								paramMap.put("CURRENT_AFFIRM_ID",
										((LinkedHashMap) affirmorList.get(j))
												.get("AFFIRMOR_ID"));
							}
						}
						// 保存发令信息
						transferOrderDao.saveExperienceForPayriseAndAffirmList(
								paramMap, affirmorList);
					} else {
						// 取出被发令人的决裁者信息(部门设置)
						List affirmorListByDept = transferOrderDao
								.getAffirmorIdListByDept(paramMap);
						if (affirmorListByDept.size() != 0) {
							for (int j = 0; j < affirmorListByDept.size(); j++) {
								// 取当前决裁者
								if (j == 0) {
									paramMap.put("CURRENT_AFFIRM_ID",
											((LinkedHashMap) affirmorListByDept
													.get(j)).get("AFFIRMOR_ID"));
								}
							}
							// 保存发令信息
							transferOrderDao
									.saveExperienceForPayriseAndAffirmList(
											paramMap, affirmorListByDept);
						} else {
							// 自定义取决裁者
							Map flagMap = getAffiram(paramMap, expInsideNo,
									request, admin);
							String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap
									.get("AFFIRM_FLAG").toString() : "";

							if ("2".equals(flag)) {
								return 2;
							}
							List newSaveAffirmorList = (List) paramMap
									.get("newSaveAffirmorList");

							transferOrderDao
									.saveExperienceForPayriseAndAffirmList(
											paramMap, newSaveAffirmorList);
						}
					}

				}
			} else {
				for (int i = 0; i < check.length; i++) {
					Map paramMap = new LinkedHashMap();
					paramMap.put("EMPID", check[i]);
					paramMap.put("CPNY_ID", admin.getCpnyId());
					paramMap.put("PERSON_ID",
							request.getParameter("PERSON_ID_" + check[i]));
					paramMap.put("interLanguage", admin.getLanguage());
					paramMap.put("TRANS_NO", "16199");
					paramMap.put("TRANS_CODE", "16200");
					paramMap.put("POSITION_NO",
							request.getParameter("POSITION_NO_" + check[i]));
					paramMap.put("DEPTNO",
							request.getParameter("DEPTNO_" + check[i]));
					paramMap.put("DUTY_NO",
							request.getParameter("DUTY_NO_" + check[i]));
					paramMap.put("POST_GRADE_NO",
							request.getParameter("POST_GRADE_NO_" + check[i]));
					paramMap.put("RETURN_VALUE",
							request.getParameter("RETURN_VALUE_" + check[i]));
					paramMap.put("CREATED_BY", admin.getPersonId());
					paramMap.put("START_DATE",
							request.getParameter("START_DATE_" + check[i]));
					paramMap.put("ITEM_NO",
							request.getParameter("ITEM_NO_" + check[i]));
					paramMap.put("ADJUST_REASON",
							request.getParameter("ADJUST_REASON_" + check[i]));
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("TYPE", "payrise");

					DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
					String temp_strS = DateUtil.getSysdateStr();
					String joinCompanyDateS = request
							.getParameter("START_DATE_" + check[i]);
					Date joinCompanyDate = dateSys.parse(joinCompanyDateS);
					Date temp_str = dateSys.parse(temp_strS);

					if (joinCompanyDate.before(temp_str)
							|| joinCompanyDate.equals(temp_str)) {
						paramMap.put("ACTIVITY", 1);

						// 向 hr_payrise表里插入发令数据
						// 修改pa_basic_date表里 条件person_id and end_date is null
						// activite=0
						// 向pa_basic_date 插入一条数据
						transferOrderDao
								.saveExperienceForPayriseNoAffirm(paramMap);
					} else {
						paramMap.put("ACTIVITY", 0);
						transferOrderDao.saveExperienceOnlyPayrise(paramMap);
					}
				}
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	public Map getAffiram(Map paramMap, String expInsideNo,
			HttpServletRequest request, AdminBean admin) {

		// 取出被发令人的决裁者信息(部门设置)
		List affirmorListByDept = transferOrderDao.getAffirmorIdListByDept(paramMap);
		// 自定义取决裁者
		List saveAffirmorList = new ArrayList();
		
		//jjy only first affirmor exclude // 결재선 결재자 제외없이 모두 나올수 있도록 결재선 person_id 값을 없는 값으로 셋팅 
		paramMap.put("EXTRA_PERSON_ID", "ANYONE"); // set some string like 'ANYONE'
		List specialAffirmorList = transferOrderDao.getSpecialAffirmorList(paramMap);
		//logger.debug("jjy:::paramMap:::"+paramMap);
		//logger.debug("jjy:::specialAffirmorList.size():::"+specialAffirmorList.size());
		
		List newSaveAffirmorList = new ArrayList();
		int count = 0;
		int affirm_level = 0;
		boolean flag = false;
		for (int k = 0; k < specialAffirmorList.size(); k++) {
			Map affirmorMap = new LinkedHashMap();
			String person_id = ((LinkedHashMap) specialAffirmorList.get(k)).get("PERSON_ID") != null ? 
					((LinkedHashMap) specialAffirmorList.get(k)).get("PERSON_ID").toString() : "";
			affirm_level = NumberUtils.parseNumber(((LinkedHashMap) specialAffirmorList.get(k)).get("AFFIRM_LEVEL").toString(), Integer.class);
			affirmorMap.put("AFFIRMOR_ID", person_id);
			affirmorMap.put("AFFIRM_LEVEL", affirm_level - count);
			affirmorMap.put("EXP_INSIDE_NO", expInsideNo);
			affirmorMap.put("CREATED_BY", admin.getPersonId());
            //logger.debug("jjy:::affirmorMap["+k+"]:::"+affirmorMap);

			if (person_id.length() > 0) {
				//jjy only first affirmor exclude //첫번째 결재자가 본인이 아닌경우만 진행
				if(k == 0 && (affirmorMap.get("CREATED_BY")).equals(affirmorMap.get("AFFIRMOR_ID"))){
					// 첫번째 이고 결재 올리는 대상자가 첫번째 결재자인 경우는 생략처리한다.
					logger.debug("exclude affirmor(affirmorMap):::"+affirmorMap);
				}else{
					flag = true;
					saveAffirmorList.add(affirmorMap);
				}
			}
		}
		if (saveAffirmorList.size() != 0) {
			Map filterMap = new LinkedHashMap();
			for (int m = 0; m < saveAffirmorList.size(); m++) {
				Map temp = (Map) saveAffirmorList.get(m);
				filterMap.put(temp.get("AFFIRMOR_ID").toString(), temp);
			}
			int a = 1;
			for (Iterator iterator = filterMap.values().iterator(); iterator.hasNext();) {
				Map temp = (Map) iterator.next();
				temp.put("AFFIRM_LEVEL", a);
				a++;
				newSaveAffirmorList.add(temp);
			}
			paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) newSaveAffirmorList.get(0)).get("AFFIRMOR_ID"));
		}
		if (flag) {
			paramMap.put("AFFIRM_FLAG", "1");
		} else {
			paramMap.put("AFFIRM_FLAG", "2");
		}
		paramMap.put("newSaveAffirmorList", newSaveAffirmorList);
		return paramMap;
	}

	/**
	 * 代理查询(agent Query)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getAgentList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		if (paramMap != null && paramMap.get("AGENT_TYPE") == null) {
			paramMap.put("AGENT_TYPE_AFFIRM", "AGENT_TYPE_AFFIRM");
		}
		if (paramMap != null && "16197".equals(paramMap.get("AGENT_TYPE"))) {
			paramMap.put("AGENT_TYPE_AFFIRM", "AGENT_TYPE_AFFIRM");
		}
		if (paramMap != null && "16198".equals(paramMap.get("AGENT_TYPE"))) {
			paramMap.put("AGENT_TYPE_CANCLE", "AGENT_TYPE_CANCLE");
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getAgentList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getAgentList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 代理条数查询(agent Query count)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public int getAgentCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		if (paramMap != null && paramMap.get("AGENT_TYPE") == null) {
			paramMap.put("AGENT_TYPE_AFFIRM", "AGENT_TYPE_AFFIRM");
		}
		if (paramMap != null && "16197".equals(paramMap.get("AGENT_TYPE"))) {
			paramMap.put("AGENT_TYPE_AFFIRM", "AGENT_TYPE_AFFIRM");
		}
		if (paramMap != null && "16198".equals(paramMap.get("AGENT_TYPE"))) {
			paramMap.put("AGENT_TYPE_CANCLE", "AGENT_TYPE_CANCLE");
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		retrunInt = transferOrderDao.getAgentCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 检查是否可做代理发令( check agent )
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkSaveTransferOrderAgent(HttpServletRequest request) {

		int result = 0;
		String check[] = request.getParameterValues("agentGp");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			paramMap.put("PERSON_ID",
					request.getParameter("PERSON_ID_" + check[i]));
			result += this.transferOrderDao
					.checkSaveTransferOrderAgent(paramMap);
		}
		return result;
	}

	/**
	 * 检查是否可做代理发令( check agent )
	 * 
	 * @param request
	 * @return empid
	 */

	@SuppressWarnings("unchecked")
	@Override
	public String checkSaveTransferAgent(HttpServletRequest request) {
		String empid = "";
		int result = 0;
		String check[] = request.getParameterValues("agentGp");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			empid = "";
			paramMap.put("PERSON_ID",
					request.getParameter("PERSON_ID_" + check[i]));
			paramMap.put("FUNCTION_DATE",
					request.getParameter("START_DATE_" + check[i]));
			result += this.transferOrderDao.checkSaveTransfer(paramMap);
			if (result > 0) {
				empid = check[i];
				return empid;
			}
		}
		return empid;
	}

	/**
	 * 提交代理发令（save Transfer Order agent）
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveTransferOrderAgent(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("agentGp");
		int result = 0;

		if ("".equals(request.getParameter("AGENT_TYPE"))
				|| null == request.getParameter("AGENT_TYPE")) {
			request.setAttribute("AGENT_TYPE", "16197");
		}

		if ("16197".equals(request.getAttribute("AGENT_TYPE"))
				|| "16197".equals(request.getParameter("AGENT_TYPE"))) {
			// 代理发令start
			try {
				Map paramValueMap = new LinkedHashMap();
				paramValueMap.put("CPNY_ID", admin.getCpnyId());
				paramValueMap.put("TYPE", "agent");
				// 判断是否需要决裁1为开，0为关
				if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {
					// 判断是否有特殊设置
					for (int i = 0; i < check.length; i++) {
						Map paramMap = new LinkedHashMap();
						paramMap.put("EMPID", check[i]);
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("PERSON_ID",
								request.getParameter("PERSON_ID_" + check[i]));

						paramMap.put("TRANS_NO", "16196");
						paramMap.put("TRANS_CODE", "16197");
						paramMap.put("AGENT_REASON", request
								.getParameter("AGENT_REASON_" + check[i]));
						paramMap.put("START_DATE",
								request.getParameter("START_DATE_" + check[i]));
						paramMap.put("AGENT_DEPTNO",
								request.getParameter("DEPTNO_" + check[i]));
						paramMap.put(
								"AGENT_POSITION_NO",
								request.getParameter("AGENT_POSITION_NO_"
										+ check[i]));
						paramMap.put("DEPT_NO",
								((LinkedHashMap) transferOrderDao
										.getViewUpgradeList(paramMap).get(0))
										.get("DEPTNO"));
						paramMap.put(
								"AGENT_DUTY_NO",
								request.getParameter("AGENT_DUTY_NO_"
										+ check[i]));
						paramMap.put(
								"AGENT_POST_GRADE_NO",
								request.getParameter("AGENT_POST_GRADE_NO_"
										+ check[i]));
						paramMap.put(
								"AGENT_WORK_AREA",
								request.getParameter("AGENT_WORK_AREA_"
										+ check[i]));

						paramMap.put("DEPTNO",
								request.getParameter("DEPTNO_" + check[i]));
						paramMap.put("POSITION_NO",
								request.getParameter("POSITION_NO_" + check[i]));
						paramMap.put(
								"POST_GRADE_NO",
								request.getParameter("POST_GRADE_NO_"
										+ check[i]));
						paramMap.put("DUTY_NO",
								request.getParameter("DUTY_NO_" + check[i]));
						paramMap.put("WORK_AREA",
								request.getParameter("WORK_AREA_" + check[i]));

						paramMap.put("CREATED_BY", admin.getPersonId());
						String expInsideNo = transferOrderDao
								.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("TYPE", "agent");
						paramMap.put("ACTIVITY", 0);

						// 取出被发令人的决裁者信息（个人设置优先）
						List affirmorList = transferOrderDao
								.getAffirmorIdListByPersonal(paramMap);
						// 如果特殊设置不为空
						if (affirmorList.size() != 0) {
							for (int j = 0; j < affirmorList.size(); j++) {
								// 取当前决裁者
								if (j == 0) {
									paramMap.put("CURRENT_AFFIRM_ID",
											((LinkedHashMap) affirmorList
													.get(j)).get("AFFIRMOR_ID"));
								}
							}
							// 保存发令信息
							transferOrderDao
									.saveExperienceForAgentAndAffirmList(
											paramMap, affirmorList);
						} else {
							// 取出被发令人的决裁者信息(部门设置)
							List affirmorListByDept = transferOrderDao
									.getAffirmorIdListByDept(paramMap);
							if (affirmorListByDept.size() != 0) {
								for (int j = 0; j < affirmorListByDept.size(); j++) {
									// 取当前决裁者
									if (j == 0) {
										paramMap.put(
												"CURRENT_AFFIRM_ID",
												((LinkedHashMap) affirmorListByDept
														.get(j))
														.get("AFFIRMOR_ID"));
									}
								}
								// 保存发令信息
								transferOrderDao
										.saveExperienceForAgentAndAffirmList(
												paramMap, affirmorListByDept);
							} else {

								// 自定义取决裁者
								Map flagMap = getAffiram(paramMap, expInsideNo,
										request, admin);
								String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap
										.get("AFFIRM_FLAG").toString() : "";

								if ("2".equals(flag)) {
									return 2;
								}
								List newSaveAffirmorList = (List) paramMap
										.get("newSaveAffirmorList");

								transferOrderDao
										.saveExperienceForAgentAndAffirmList(
												paramMap, newSaveAffirmorList);
							}
						}

					}
				} else {
					for (int i = 0; i < check.length; i++) {
						Map paramMap = new LinkedHashMap();
						paramMap.put("EMPID", check[i]);
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("PERSON_ID",
								request.getParameter("PERSON_ID_" + check[i]));

						paramMap.put("TRANS_NO", "16196");
						paramMap.put("TRANS_CODE", "16197");
						paramMap.put("AGENT_REASON", request
								.getParameter("AGENT_REASON_" + check[i]));
						paramMap.put("START_DATE",
								request.getParameter("START_DATE_" + check[i]));
						paramMap.put("AGENT_DEPTNO",
								request.getParameter("DEPTNO_" + check[i]));
						paramMap.put(
								"AGENT_POSITION_NO",
								request.getParameter("AGENT_POSITION_NO_"
										+ check[i]));
						paramMap.put("POSITION_NO",
								request.getParameter("POSITION_NO_" + check[i]));
						paramMap.put(
								"AGENT_DUTY_NO",
								request.getParameter("AGENT_DUTY_NO_"
										+ check[i]));
						paramMap.put(
								"AGENT_POST_GRADE_NO",
								request.getParameter("AGENT_POST_GRADE_NO_"
										+ check[i]));
						paramMap.put(
								"AGENT_WORK_AREA",
								request.getParameter("AGENT_WORK_AREA_"
										+ check[i]));

						paramMap.put("DEPTNO",
								request.getParameter("OLD_DEPTNO_" + check[i]));
						paramMap.put("POSITION_NO",
								request.getParameter("POSITION_NO_" + check[i]));
						paramMap.put("DUTY_NO",
								request.getParameter("OLD_DEPTNO_" + check[i]));
						paramMap.put("WORK_AREA",
								request.getParameter("WORK_AREA_" + check[i]));

						paramMap.put("CREATED_BY", admin.getPersonId());
						String expInsideNo = transferOrderDao
								.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("TYPE", "agent");

						List hrEmpList = transferOrderDao
								.getViewUpgradeList(paramMap);

						String dept_no = ((LinkedHashMap) hrEmpList.get(0))
								.get("DEPTNO") != null ? ((LinkedHashMap) hrEmpList
								.get(0)).get("DEPTNO").toString() : "";
						String duty_no = ((LinkedHashMap) hrEmpList.get(0))
								.get("DUTY_NO") != null ? ((LinkedHashMap) hrEmpList
								.get(0)).get("DUTY_NO").toString() : "";
						String post_no = ((LinkedHashMap) hrEmpList.get(0))
								.get("POST_NO") != null ? ((LinkedHashMap) hrEmpList
								.get(0)).get("POST_NO").toString() : "";
						String agent_post_grade_no = ((LinkedHashMap) hrEmpList
								.get(0)).get("AGENT_POST_GRADE_NO") != null ? ((LinkedHashMap) hrEmpList
								.get(0)).get("AGENT_POST_GRADE_NO").toString()
								: "";
						String post_grade_no = ((LinkedHashMap) hrEmpList
								.get(0)).get("POST_GRADE_NO") != null ? ((LinkedHashMap) hrEmpList
								.get(0)).get("POST_GRADE_NO").toString() : "";
						String position_no = ((LinkedHashMap) hrEmpList.get(0))
								.get("POSITION_NO") != null ? ((LinkedHashMap) hrEmpList
								.get(0)).get("POSITION_NO").toString() : "";
						String work_area = ((LinkedHashMap) hrEmpList.get(0))
								.get("WORK_AREA") != null ? ((LinkedHashMap) hrEmpList
								.get(0)).get("WORK_AREA").toString() : "";

						paramMap.put("POST_NO", post_no);
						paramMap.put("POST_GRADE_NO", post_grade_no);

						// 部门变动
						if (!dept_no.equals(request.getParameter("DEPTNO_"
								+ check[i]))) {

							// 根据当前生效时间和person_id取出HR_EMP_DEPT表里面的 当前生效时间前一条
							List beforeDataList = transferOrderDao
									.getBeforeDataForDept(paramMap);
							// 根据当前生效时间和person_id取出HR_EMP_DEPT表里面的 当前生效时间后一条数据
							List afterDataList = transferOrderDao
									.getAfterDataForDept(paramMap);

							// 如果取到2个,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
							// 修改前一条数据的结束时间（=当前生效日期-1）
							// 此情况不修改HR_EMPLOYEE里面的DEPTNO
							if (beforeDataList.size() != 0
									&& afterDataList.size() != 0) {
								paramMap.put("AFTERSTARTDATE",
										((Map) afterDataList.get(0))
												.get("STARTDATE"));// 插入数据的结束日期
								// 取后一条数据的开始日期
								// 后台用的时候日期要-1

								paramMap.put("AFTERS_EXP_INSIDE_NO",
										((Map) afterDataList.get(0))
												.get("S_EXP_INSIDE_NO"));// 插入数据的
								// E_EXP_INSIDE_NO
								// 取后一条的开始
								// S_EXP_INSIDE_NO

								paramMap.put("BEFORE_PERSONID",
										((Map) beforeDataList.get(0))
												.get("PERSON_ID"));// 修改前一条数据的persno_id(修改条件)

								paramMap.put("BEFORE_ENDDATE",
										paramMap.get("START_DATE"));// 修改前一条数据的
																	// 结束日期
								// 后台用的时候日期要-1

								paramMap.put("BEFOREE_EXP_INSIDE_NO",
										paramMap.get("EXP_INSIDE_NO"));// 修改前一条数据的
								// E_EXP_INSIDE_NO
								// 修改前一条数据的 条件
								// -start

								paramMap.put("BEFORE_STARTDATE",
										((Map) beforeDataList.get(0))
												.get("STARTDATE"));// 修改前一条数据的条件
								// 开始日期STARTDATE(修改条件)

								paramMap.put("BEFORE_DEPTNO",
										((Map) beforeDataList.get(0))
												.get("DEPTNO"));// 修改前一条数据的条件
								// DEPTNO(修改条件)
								// 修改前一条数据的 条件
								// -end

								transferOrderDao
										.saveAndUpdateForAgentHrEmpDept(paramMap);

								paramMap.put("DEPTNO", dept_no);

							}

							// 如果只取到后面的,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
							// 此情况不修改HR_EMPLOYEE里面的DEPTNO
							if (beforeDataList.size() == 0
									&& afterDataList.size() != 0) {
								paramMap.put("AFTERSTARTDATE",
										((Map) afterDataList.get(0))
												.get("STARTDATE"));// 插入数据的结束日期
								// 取后一条数据的开始日期
								// 后台用的时候日期要-1
								paramMap.put("AFTERS_EXP_INSIDE_NO",
										((Map) afterDataList.get(0))
												.get("S_EXP_INSIDE_NO"));// 插入数据的
								// E_EXP_INSIDE_NO
								// 取后一条的开始
								// S_EXP_INSIDE_NO
								transferOrderDao
										.saveHrEmpDeptForAgent(paramMap);
								paramMap.put("DEPTNO", dept_no);

							}

							// 如果只取到前面的,修改结束日期为空的记录,结束日期=当前生效时间,插入一条数据,生效时间是当前生效日期
							if (beforeDataList.size() != 0
									&& afterDataList.size() == 0) {
								transferOrderDao.saveHrEmpDept(paramMap);
								paramMap.put(
										"DEPTNO",
										request.getParameter("DEPTNO_"
												+ check[i]));
							}

						}

						// 职责变动
						if (!duty_no.equals(request
								.getParameter("AGENT_DUTY_NO_" + check[i]))) {

							// 根据当前生效时间和person_id取出HR_EMP_DEPT表里面的 当前生效时间前一条
							List beforeDataListForDuty = transferOrderDao
									.getBeforeDataForDuty(paramMap);
							// 根据当前生效时间和person_id取出HR_EMP_DEPT表里面的 当前生效时间后一条数据
							List afterDataListForDuty = transferOrderDao
									.getAfterDataForDuty(paramMap);

							// 如果取到2个,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
							// 修改前一条数据的结束时间（=当前生效日期-1）
							// 此情况不修改HR_EMPLOYEE里面的DUTY_NO
							if (beforeDataListForDuty.size() != 0
									&& afterDataListForDuty.size() != 0) {
								paramMap.put("AFTERSTARTDATE",
										((Map) afterDataListForDuty.get(0))
												.get("STARTDATE"));// 插入数据的结束日期
								// 取后一条数据的开始日期
								// 后台用的时候日期要-1
								paramMap.put("AFTERS_EXP_INSIDE_NO",
										((Map) afterDataListForDuty.get(0))
												.get("S_EXP_INSIDE_NO"));// 插入数据的
								// E_EXP_INSIDE_NO
								// 取后一条的开始
								// S_EXP_INSIDE_NO

								paramMap.put("BEFORE_PERSONID",
										((Map) beforeDataListForDuty.get(0))
												.get("PERSON_ID"));// 修改前一条数据的
								// persno_id
								// (修改条件)
								paramMap.put("BEFORE_ENDDATE",
										paramMap.get("START_DATE"));// 修改前一条数据的
																	// 结束日期
								// 后台用的时候日期要-1
								paramMap.put("BEFOREE_EXP_INSIDE_NO",
										paramMap.get("EXP_INSIDE_NO"));// 修改前一条数据的
								// E_EXP_INSIDE_NO
								// 修改前一条数据的 条件
								// -start
								paramMap.put("BEFORE_STARTDATE",
										((Map) beforeDataListForDuty.get(0))
												.get("STARTDATE"));// 修改前一条数据的条件
								// 开始日期STARTDATE(修改条件)
								paramMap.put("BEFORE_DUTY_NO",
										((Map) beforeDataListForDuty.get(0))
												.get("DUTY_NO"));// 修改前一条数据的条件
								// DUTY_NO(修改条件)
								// 修改前一条数据的
								// 条件 -end
								transferOrderDao
										.saveAndUpdateForAgentHrEmpDuty(paramMap);
								paramMap.put("DUTY_NO", duty_no);

							}

							// 如果只取到后面的,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
							// 此情况不修改HR_EMPLOYEE里面的DUTYNO
							if (beforeDataListForDuty.size() == 0
									&& afterDataListForDuty.size() != 0) {
								paramMap.put("AFTERSTARTDATE",
										((Map) afterDataListForDuty.get(0))
												.get("STARTDATE"));// 插入数据的结束日期
								// 取后一条数据的开始日期
								// 后台用的时候日期要-1
								paramMap.put("AFTERS_EXP_INSIDE_NO",
										((Map) afterDataListForDuty.get(0))
												.get("S_EXP_INSIDE_NO"));// 插入数据的
								// E_EXP_INSIDE_NO
								// 取后一条的开始
								// S_EXP_INSIDE_NO
								transferOrderDao
										.saveHrEmpDutyForAgent(paramMap);
								paramMap.put("DUTY_NO", duty_no);
							}

							// 如果只取到前面的,修改结束日期为空的记录,结束日期=当前生效时间,插入一条数据,生效时间是当前生效日期
							if (beforeDataListForDuty.size() != 0
									&& afterDataListForDuty.size() == 0) {
								transferOrderDao.saveHrEmpDuty(paramMap);
								paramMap.put(
										"DUTY_NO",
										request.getParameter("AGENT_DUTY_NO_"
												+ check[i]));
							}

						}

						// 职(岗)位变动
						if (!position_no.equals(request
								.getParameter("AGENT_POSITION_NO_" + check[i]))) {

							// 根据当前生效时间和person_id取出HR_EMP_POSITION表里面的 当前生效时间前一条
							List beforeDataListForPosition = transferOrderDao
									.getBeforeDataForPosition(paramMap);
							// 根据当前生效时间和person_id取出HR_EMP_POSITION表里面的
							// 当前生效时间后一条数据
							List afterDataListForPosition = transferOrderDao
									.getAfterDataForPosition(paramMap);

							// 如果取到2个,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
							// 修改前一条数据的结束时间（=当前生效日期-1）
							// 此情况不修改HR_EMPLOYEE里面的POSITION_NO
							if (beforeDataListForPosition.size() != 0
									&& afterDataListForPosition.size() != 0) {
								paramMap.put("AFTERSTARTDATE",
										((Map) afterDataListForPosition.get(0))
												.get("STARTDATE"));// 插入数据的结束日期
								// 取后一条数据的开始日期
								// 后台用的时候日期要-1
								paramMap.put("AFTERS_EXP_INSIDE_NO",
										((Map) afterDataListForPosition.get(0))
												.get("S_EXP_INSIDE_NO"));// 插入数据的
								// E_EXP_INSIDE_NO
								// 取后一条的开始
								// S_EXP_INSIDE_NO

								paramMap.put(
										"BEFORE_PERSONID",
										((Map) beforeDataListForPosition.get(0))
												.get("PERSON_ID"));// 修改前一条数据的
								// persno_id
								// (修改条件)
								paramMap.put("BEFORE_ENDDATE",
										paramMap.get("START_DATE"));// 修改前一条数据的
																	// 结束日期
								// 后台用的时候日期要-1
								paramMap.put("BEFOREE_EXP_INSIDE_NO",
										paramMap.get("EXP_INSIDE_NO"));// 修改前一条数据的
								// E_EXP_INSIDE_NO
								// 修改前一条数据的 条件
								// -start
								paramMap.put(
										"BEFORE_STARTDATE",
										((Map) beforeDataListForPosition.get(0))
												.get("STARTDATE"));// 修改前一条数据的条件
								// 开始日期STARTDATE(修改条件)
								paramMap.put(
										"BEFORE_POSITION_NO",
										((Map) beforeDataListForPosition.get(0))
												.get("POSITION_NO"));// 修改前一条数据的条件
								// POSITION_NO(修改条件)
								// 修改前一条数据的
								// 条件
								// -end
								transferOrderDao
										.saveAndUpdateForAgentHrEmpPosition(paramMap);
								paramMap.put("POSITION_NO", position_no);

							}

							// 如果只取到后面的,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
							// 此情况不修改HR_EMPLOYEE里面的POSITION_NO
							if (beforeDataListForPosition.size() == 0
									&& afterDataListForPosition.size() != 0) {
								paramMap.put("AFTERSTARTDATE",
										((Map) afterDataListForPosition.get(0))
												.get("STARTDATE"));// 插入数据的结束日期
								// 取后一条数据的开始日期
								// 后台用的时候日期要-1
								paramMap.put("AFTERS_EXP_INSIDE_NO",
										((Map) afterDataListForPosition.get(0))
												.get("S_EXP_INSIDE_NO"));// 插入数据的
								// E_EXP_INSIDE_NO
								// 取后一条的开始
								// S_EXP_INSIDE_NO
								transferOrderDao
										.saveHrEmpPositionForAgent(paramMap);
								paramMap.put("POSITION_NO", position_no);
							}

							// 如果只取到前面的,修改结束日期为空的记录,结束日期=当前生效时间,插入一条数据,生效时间是当前生效日期
							if (beforeDataListForPosition.size() != 0
									&& afterDataListForPosition.size() == 0) {
								transferOrderDao.saveHrEmpPosition(paramMap);
								paramMap.put("POSITION_NO", request
										.getParameter("AGENT_POSITION_NO_"
												+ check[i]));
							}

						}

						// 工作地变动
						if (!work_area.equals(request
								.getParameter("AGENT_WORK_AREA_" + check[i]))) {

							// 根据当前生效时间和person_id取出HR_EMP_POSITION表里面的 当前生效时间前一条
							List beforeDataListForWorkArea = transferOrderDao
									.getBeforeDataForWorkArea(paramMap);
							// 根据当前生效时间和person_id取出HR_EMP_POSITION表里面的
							// 当前生效时间后一条数据
							List afterDataListForWorkArea = transferOrderDao
									.getAfterDataForWorkArea(paramMap);

							// 如果取到2个,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
							// 修改前一条数据的结束时间（=当前生效日期-1）
							// 此情况不修改HR_EMPLOYEE里面的WORK_AREA
							if (beforeDataListForWorkArea.size() != 0
									&& afterDataListForWorkArea.size() != 0) {
								paramMap.put("AFTERSTARTDATE",
										((Map) afterDataListForWorkArea.get(0))
												.get("STARTDATE"));// 插入数据的结束日期
								// 取后一条数据的开始日期
								// 后台用的时候日期要-1
								paramMap.put("AFTERS_EXP_INSIDE_NO",
										((Map) afterDataListForWorkArea.get(0))
												.get("S_EXP_INSIDE_NO"));// 插入数据的
								// E_EXP_INSIDE_NO
								// 取后一条的开始
								// S_EXP_INSIDE_NO

								paramMap.put("BEFORE_PERSONID",
										((Map) afterDataListForWorkArea.get(0))
												.get("PERSON_ID"));// 修改前一条数据的
								// persno_id
								// (修改条件)
								paramMap.put("BEFORE_ENDDATE",
										paramMap.get("START_DATE"));// 修改前一条数据的
																	// 结束日期
								// 后台用的时候日期要-1
								paramMap.put("BEFOREE_EXP_INSIDE_NO",
										paramMap.get("EXP_INSIDE_NO"));// 修改前一条数据的
								// E_EXP_INSIDE_NO
								// 修改前一条数据的 条件
								// -start
								paramMap.put(
										"BEFORE_STARTDATE",
										((Map) beforeDataListForWorkArea.get(0))
												.get("STARTDATE"));// 修改前一条数据的条件
								// 开始日期STARTDATE(修改条件)
								paramMap.put(
										"BEFORE_WORK_AREA",
										((Map) beforeDataListForWorkArea.get(0))
												.get("WORK_AREA"));// 修改前一条数据的条件
								// WORK_AREA(修改条件)
								// 修改前一条数据的
								// 条件
								// -end
								transferOrderDao
										.saveAndUpdateForAgentHrEmpWorkArea(paramMap);
								paramMap.put("WORK_AREA", work_area);
							}

							// 如果只取到后面的,向中间表插入一条数据,开始时间=当前生效时间，结束时间=后一条数据的开始时间-1
							// 此情况不修改HR_EMPLOYEE里面的WORK_AREA
							if (beforeDataListForWorkArea.size() == 0
									&& afterDataListForWorkArea.size() != 0) {
								paramMap.put("AFTERSTARTDATE",
										((Map) afterDataListForWorkArea.get(0))
												.get("STARTDATE"));// 插入数据的结束日期
								// 取后一条数据的开始日期
								// 后台用的时候日期要-1
								paramMap.put("AFTERS_EXP_INSIDE_NO",
										((Map) afterDataListForWorkArea.get(0))
												.get("S_EXP_INSIDE_NO"));// 插入数据的
								// E_EXP_INSIDE_NO
								// 取后一条的开始
								// S_EXP_INSIDE_NO
								transferOrderDao
										.saveHrEmpWorkAreaForAgent(paramMap);
								paramMap.put("WORK_AREA", work_area);
							}

							// 如果只取到前面的,修改结束日期为空的记录,结束日期=当前生效时间,插入一条数据,生效时间是当前生效日期
							if (beforeDataListForWorkArea.size() != 0
									&& afterDataListForWorkArea.size() == 0) {
								transferOrderDao.saveHrEmpWorkArea(paramMap);
								paramMap.put(
										"WORK_AREA",
										request.getParameter("AGENT_WORK_AREA_"
												+ check[i]));
							}

						}

						// 在hr_agent 日期比对 看当前生效日期 和表里面的日期相比

						DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
						String temp_strS = DateUtil.getSysdateStr();
						String joinCompanyDateS = request
								.getParameter("START_DATE_" + check[i]);
						Date joinCompanyDate = dateSys.parse(joinCompanyDateS);
						Date temp_str = dateSys.parse(temp_strS);
						if (joinCompanyDate.before(temp_str)
								|| joinCompanyDate.equals(temp_str)) {
							paramMap.put("ACTIVITY", 1);

							int resultcount = transferOrderDao
									.getAgentCountForHrEmployeesAgentPostGradeNo(paramMap);
							if (resultcount == 0) {
								paramMap.put("AGENT_POST_GRADE_NO", request
										.getParameter("AGENT_POST_GRADE_NO_"
												+ check[i]));
							} else {
								paramMap.put("AGENT_POST_GRADE_NO",
										agent_post_grade_no);
							}

							transferOrderDao
									.saveExperienceForAgentAndUpHrEmployee(paramMap);
						} else {
							paramMap.put("ACTIVITY", 0);
							transferOrderDao.saveExperienceForAgent(paramMap);
						}
					}
				}
				result = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 代理发令end

		} else {

			// 取消代理发令start
			try {
				Map paramValueMap = new LinkedHashMap();
				paramValueMap.put("CPNY_ID", admin.getCpnyId());
				paramValueMap.put("TYPE", "agent");

				// 判断是否需要决裁1为开，0为关
				if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {
					// 判断是否有特殊设置
					for (int i = 0; i < check.length; i++) {
						Map paramMap = new LinkedHashMap();
						paramMap.put("EMPID", check[i]);
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("PERSON_ID",
								request.getParameter("PERSON_ID_" + check[i]));

						paramMap.put("TRANS_NO", "16196");
						paramMap.put("TRANS_CODE", "16198");

						paramMap.put("START_DATE",
								request.getParameter("START_DATE_" + check[i]));
						paramMap.put("CREATED_BY", admin.getPersonId());
						paramMap.put("UPDATED_BY", admin.getPersonId());
						String expInsideNo = transferOrderDao
								.getNextExpInside();

						String relationExpInsideNo = transferOrderDao
								.getExpInsideNoForAgent(paramMap);
						paramMap.put("RELATION_EXP_INSIDE_NO",
								relationExpInsideNo);

						paramMap.put("EXP_INSIDE_NO", expInsideNo);
						paramMap.put("TYPE", "agent");
						paramMap.put("ACTIVITY", 0);

						// 取出这个人最后一次生效代理发令的记录 取出生效日期 与取消代理生效日期 比对
						// 判断是否可以在此时间进行取消代理发令 如果当前time<代理生效time 则不可以发令
						int resultcount = transferOrderDao
								.getDateDifference(paramMap);
						if (resultcount >= 0) {
							// 不可以做 取消代理发令
							return 3;
						}

						// 判断此人是否可以做取消代理发令 条件 hr_agent 里面有没有 相关的发令
						int agentCount = transferOrderDao
								.getAgentCountByPid(paramMap);
						if (agentCount > 0) {
							return 4;
						}

						// 取出被发令人的决裁者信息（个人设置优先）
						List affirmorList = transferOrderDao
								.getAffirmorIdListByPersonal(paramMap);
						// 如果特殊设置不为空
						if (affirmorList.size() != 0) {
							for (int j = 0; j < affirmorList.size(); j++) {
								// 取当前决裁者
								if (j == 0) {
									paramMap.put("CURRENT_AFFIRM_ID",
											((LinkedHashMap) affirmorList
													.get(j)).get("AFFIRMOR_ID"));
								}
							}
							// 保存发令信息
							transferOrderDao
									.saveExperienceForAgentAndAffirmList(
											paramMap, affirmorList);
						} else {
							// 取出被发令人的决裁者信息(部门设置)
							List affirmorListByDept = transferOrderDao
									.getAffirmorIdListByDept(paramMap);
							if (affirmorListByDept.size() != 0) {
								for (int j = 0; j < affirmorListByDept.size(); j++) {
									// 取当前决裁者
									if (j == 0) {
										paramMap.put(
												"CURRENT_AFFIRM_ID",
												((LinkedHashMap) affirmorListByDept
														.get(j))
														.get("AFFIRMOR_ID"));
									}
								}
								// 保存发令信息
								transferOrderDao
										.saveExperienceForAgentAndAffirmList(
												paramMap, affirmorListByDept);
							} else {

								// 自定义取决裁者
								Map flagMap = getAffiram(paramMap, expInsideNo,
										request, admin);
								String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap
										.get("AFFIRM_FLAG").toString() : "";

								if ("2".equals(flag)) {
									return 2;
								}
								List newSaveAffirmorList = (List) paramMap
										.get("newSaveAffirmorList");

								transferOrderDao
										.saveExperienceForAgentAndAffirmList(
												paramMap, newSaveAffirmorList);
							}
						}

					}
				} else {
					for (int i = 0; i < check.length; i++) {
						Map paramMap = new LinkedHashMap();
						paramMap.put("EMPID", check[i]);
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("PERSON_ID",
								request.getParameter("PERSON_ID_" + check[i]));

						paramMap.put("TRANS_NO", "16196");
						paramMap.put("TRANS_CODE", "16198");

						paramMap.put("START_DATE",
								request.getParameter("START_DATE_" + check[i]));
						paramMap.put("UPDATED_BY", admin.getPersonId());
						String expInsideNo = transferOrderDao
								.getNextExpInside();
						paramMap.put("EXP_INSIDE_NO", expInsideNo);

						paramMap.put("AGENT_POST_GRADE_NO", "");

						String relationExpInsideNo = transferOrderDao
								.getExpInsideNoForAgent(paramMap);
						paramMap.put("RELATION_EXP_INSIDE_NO",
								relationExpInsideNo);

						// 取出这个人最后一次生效代理发令的记录 取出生效日期 与取消代理生效日期 比对
						// 判断是否可以在此时间进行取消代理发令 如果当前time<代理生效time 则不可以发令
						int resultcount = transferOrderDao
								.getDateDifference(paramMap);
						if (resultcount >= 0) {
							// 不可以做 取消代理发令
							return 3;
						}

						// 判断此人是否可以做取消代理发令 条件 hr_agent 里面有没有 相关的发令
						int agentCount = transferOrderDao
								.getAgentCountByPid(paramMap);
						if (agentCount > 0) {
							return 4;
						}

						// 在hr_agent 日期比对 看当前生效日期 和表里面的日期相比
						DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
						String temp_strS = DateUtil.getSysdateStr();
						String joinCompanyDateS = request
								.getParameter("START_DATE_" + check[i]);
						Date joinCompanyDate = dateSys.parse(joinCompanyDateS);
						Date temp_str = dateSys.parse(temp_strS);
						if (joinCompanyDate.before(temp_str)
								|| joinCompanyDate.equals(temp_str)) {
							paramMap.put("ACTIVITY", 1);
							transferOrderDao
									.saveAgentAndUpHrEmployeeForCancel(paramMap);
						} else {
							paramMap.put("ACTIVITY", 0);
							transferOrderDao.saveAgentForCancel(paramMap);
						}
					}
				}
				result = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 取消代理发令end
		}

		return result;

	}

	// 定时器start
	/**
	 * 查询需要更新到hr_employee表里面的 from hr_employee_temp (入职发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getEmployeeTempList() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getEmployeeTempList();

		return retrunList;
	}

	/**
	 * 修改updateHrEmployeeTemp activity=1 (入职发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public void updateHrEmployeeTemp(Object obj) {
		transferOrderDao.updateHrEmployeeTemp(obj);
	}

	/**
	 * 查询需要更新到hr_personal_info表里面的 from hr_personal_info_temp (入职发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalInfoTempList() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getPersonalInfoTempList();

		return retrunList;
	}

	/**
	 * 保存到hr_employee,hr_personal_info(入职发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public void saveEmpAndPerForHireTimers(List empList, List perList) {
		transferOrderDao.saveEmpAndPerForHireTimers(empList, perList);
	}

	/**
	 * 查询出调动已生效未更新的数据list (调动发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getExperienceInsideListForUpgrade() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getExperienceInsideListForUpgrade();

		return retrunList;
	}
	
	/**
	 * 查询出号俸发令已成功未更新的数据list (调动发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getExperienceInsideListForPayStep() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getExperienceInsideListForPayStep();

		return retrunList;
	}

	/**
	 * 修改HR_EMPLOYEE HR_EXPERIENCE_INSIDE(调动发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public void updateEmpAndExpForUpgradeTimers(Object obj) {
		transferOrderDao.updateEmpAndExpForUpgradeTimers(obj);
	}

	/**
	 * 查询需要更新到hr_employee表里面的 from HR_PROBATION (转正发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getProbationListForTransferNormal() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getProbationListForTransferNormal();

		return retrunList;
	}

	/**
	 * 修改hr_employee (转正发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public void updateHrEmployeeForTN(Object obj) {
		transferOrderDao.updateHrEmployeeForTN(obj);
	}

	/**
	 * 修改hr_employee DATE_STARTED(转正发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public void updateHrEmployeeDateStated(Object obj) {
		transferOrderDao.updateHrEmployeeDateStated(obj);
	}

	/**
	 * 修改probation activity=1(转正发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public void updateProbationForTransferNormal(Object obj) {
		transferOrderDao.updateProbationForTransferNormal(obj);
	}

	/**
	 * 查询需要更新到hr_employee表里面的 from HR_EXPERIENCE_INSIDE (晋升降职发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getExperienceInsideListForTransferPromote() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao
				.getExperienceInsideListForTransferPromote();

		return retrunList;
	}

	/**
	 * 获取兼职发令列表 (兼职、取消兼职发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getPluralityList() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getPluralityList();

		return retrunList;
	}

	/**
	 * 获取取消兼职发令列表 (兼职、取消兼职发令)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List PluralityListForCancle() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.PluralityListForCancle();

		return retrunList;
	}

	/**
	 * 修改Plurality ACTIVITY(兼职发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updatePluralityForTimer(Object obj) {
		transferOrderDao.updatePluralityForTimer(obj);
	}

	/**
	 * 修改hr_plurality activity(取消兼职发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updatePluralityCancleForTimer(Object obj) {
		transferOrderDao.updatePluralityCancleForTimer(obj);
	}

	/**
	 * 获取停职发令列表 (停职发令)
	 */
	@SuppressWarnings("unchecked")
	public List getSuspendListForTimer() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getSuspendListForTimer();

		return retrunList;
	}

	/**
	 * 修改hr_employee STATUS_CODE(停职发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrEmployeesStatusCode(Object obj) {
		transferOrderDao.updateHrEmployeesStatusCode(obj);
	}

	/**
	 * 根据personId获取cpnyId
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public String getCpnyIdByPersonId(Object obj) {
		return transferOrderDao.getCpnyIdByPersonId(obj);
	}

	/**
	 * (停职发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateDateForSuspendTimers(Object obj) {
		transferOrderDao.updateDateForSuspendTimers(obj);
	}

	/**
	 * (复职发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateDateForReinstatedTimers(Object obj) {
		transferOrderDao.updateDateForReinstatedTimers(obj);
	}

	/**
	 * 获取复职发令列表 (复职发令)
	 */
	@SuppressWarnings("unchecked")
	public List getReinstatedListForTimer() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getReinstatedListForTimer();

		return retrunList;
	}

	/**
	 * 获取奖励发令列表 (奖励发令)
	 */
	@SuppressWarnings("unchecked")
	public List getRewardListForTimer() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getRewardListForTimer();

		return retrunList;
	}

	/**
	 * 修改HrReward (奖励发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrRewardForTimers(Object obj) {
		transferOrderDao.updateHrRewardForTimers(obj);
	}

	/**
	 * 获取惩戒发令列表 (惩戒发令)
	 */
	@SuppressWarnings("unchecked")
	public List getPunishmentListForTimer() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getPunishmentListForTimer();

		return retrunList;
	}

	/**
	 * 修改HrPunishMent (惩戒发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrPunishmentForTimers(Object obj) {
		transferOrderDao.updateHrPunishmentForTimers(obj);
	}

	/**
	 * 获取离职发令列表 (离职发令)
	 */
	@SuppressWarnings("unchecked")
	public List getResignListForTimer() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getResignListForTimer();

		return retrunList;
	}

	/**
	 * 修改HrResign (离职发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrResignForTimers(Object obj) {
		transferOrderDao.updateHrResignForTimers(obj);
	}

	/**
	 * 获取薪资调整发令列表 (薪资调整发令)
	 */
	@SuppressWarnings("unchecked")
	public List getPayriseListForTimer() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getPayriseListForTimer();

		return retrunList;
	}

	/**
	 * 修改HrPayrise (薪资调整发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHrPayriseForTimers(Object obj) {
		transferOrderDao.updateHrPayriseForTimers(obj);
	}

	/**
	 * 获取代理发令列表 (代理发令)
	 */
	@SuppressWarnings("unchecked")
	public List getAgentListForTimer() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getAgentListForTimer();

		return retrunList;
	}

	/**
	 * 修改HrEmployee (代理发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHremployeeForTimers(Object obj) {
		transferOrderDao.updateHremployeeForTimers(obj);
	}

	/**
	 * 获取取消代理发令列表 (取消代理发令)
	 */
	@SuppressWarnings("unchecked")
	public List getCancleAgentListForTimer() {
		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getCancleAgentListForTimer();

		return retrunList;
	}

	/**
	 * 修改HrEmployee (取消代理发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void updateHremployeeForCancleAgentTimers(Object obj) {
		transferOrderDao.updateHremployeeForCancleAgentTimers(obj);
	}
	
	
	/**
	 * 修改HrEmployee (取消代理发令)
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void savePayStepForTimer(Object obj) {
		try {
			transferOrderDao.savePayStepForTimer(obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 定时器end

	/**
	 * 人员搜索(agent Query)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getEmpSearchList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("theType", request.getParameter("theType"));
		paramMap.put("EIDS", request.getParameter("eids"));

		if (paramMap.get("EIDS") != null
				&& paramMap.get("EIDS").toString().length() > 0) {
			String empids = paramMap.get("EIDS").toString();
			String eids = "(";
			if (empids.length() > 0 && empids != null) {
				String[] empidsArg = empids.split(",");
				for (int i = 0; i < empidsArg.length; i++) {
					if (i == empidsArg.length - 1) {
						eids = eids + "'" + empidsArg[i] + "')";
					} else {
						eids = eids + "'" + empidsArg[i] + "',";
					}
				}
			}
			paramMap.put("EIDS", eids);
		}

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getEmpSearchList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getEmpSearchList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 人员搜索条数查询()
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public int getEmpSearchCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("theType", request.getParameter("theType"));
		if (paramMap.get("eids") != null
				&& paramMap.get("eids").toString().length() > 0) {
			String empids = paramMap.get("eids").toString();
			String eids = "(";
			if (empids.length() > 0 && empids != null) {
				String[] empidsArg = empids.split(",");
				for (int i = 0; i < empidsArg.length; i++) {
					if (i == empidsArg.length - 1) {
						eids = eids + "'" + empidsArg[i] + "')";
					} else {
						eids = eids + "'" + empidsArg[i] + "',";
					}
				}
			}
			paramMap.put("EIDS", eids);
		}
		retrunInt = transferOrderDao.getEmpSearchCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 搜索( Query)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getEidListForSearch(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		if (request.getParameter("eidsForSearch1") != null
				&& request.getParameter("eidsForSearch1").length() > 0) {
			paramMap.put("eidsForSearch",
					request.getParameter("eidsForSearch1"));
		}
		if (request.getParameter("eidsForSearch") != null
				&& request.getParameter("eidsForSearch").length() > 0) {
			paramMap.put("eidsForSearch", request.getParameter("eidsForSearch"));
		}
		if (paramMap.get("eidsForSearch") != null
				&& paramMap.get("eidsForSearch").toString().length() > 0) {
			String empids = paramMap.get("eidsForSearch").toString();
			String eids = "(";
			if (empids.length() > 0 && empids != null) {
				String[] empidsArg = empids.split(",");
				for (int i = 0; i < empidsArg.length; i++) {
					if (i == empidsArg.length - 1) {
						eids = eids + "'" + empidsArg[i] + "')";
					} else {
						eids = eids + "'" + empidsArg[i] + "',";
					}
				}
			}
			paramMap.put("EIDS", eids);
		}
		
		paramMap.put("CPNY_ID", admin.getCpnyId());

		List retrunList = new ArrayList();

		retrunList = transferOrderDao.getEidListForSearch(paramMap);

		return retrunList;
	}

	public void eidsForcommand(LinkedHashMap paramMap,
			HttpServletRequest request) {
		paramMap.put("eids", request.getParameter("eids"));
		if (paramMap.get("eids") != null
				&& paramMap.get("eids").toString().length() > 0) {
			String empids = paramMap.get("eids").toString();
			String eids = "(";
			if (empids.length() > 0 && empids != null) {
				String[] empidsArg = empids.split(",");
				for (int i = 0; i < empidsArg.length; i++) {
					if (i == empidsArg.length - 1) {
						eids = eids + "'" + empidsArg[i] + "')";
					} else {
						eids = eids + "'" + empidsArg[i] + "',";
					}
				}
			}
			paramMap.put("EIDS", eids);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String checkPersonId(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("c1");
		Map paramMap = new LinkedHashMap();
		int rno = 0;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		for (int i = 0; i < check.length; i++) {
			paramMap.put("PERSON_ID",request.getParameter("PERSON_ID_" + check[i]));
			paramMap.put("START_DATE",request.getParameter("FUNCTION_DATE_" + check[i]));
			int resultnum = transferOrderDao.checkPersonId(paramMap);
			if (resultnum > 0 && resultnum != 10000) {
				return check[i];
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public int checkedIdcardNoReqStatus(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String idcardNo = request.getParameter("IDCARD_NO");
		Map paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("IDCARD_NO", idcardNo);
		int resultnum = transferOrderDao.checkedIdcardNoReqStatus(paramMap);
		
		return resultnum;
	}

	@SuppressWarnings("unchecked")
	public int checkIdcardNo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String idcardNo = request.getParameter("IDCARD_NO");
		Map paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("IDCARD_NO", idcardNo);
		int resultnum = transferOrderDao.checkIdcardNo(paramMap);
		if (resultnum == 0 && idcardNo.length()==18)//18转15
		{
			String idcardNo15 = "";
			StringBuffer sb = new StringBuffer(idcardNo);
	        sb.deleteCharAt(17);
	        sb.deleteCharAt(7);
	        sb.deleteCharAt(6);
	        idcardNo15 = sb.toString();
			paramMap.put("IDCARD_NO15", idcardNo15);
			int resultnum15 = transferOrderDao.checkIdcardNo15(paramMap);
			return resultnum15;
		}
		if( resultnum ==0 && idcardNo.length()==15 )//15转18
		{
			String idcardNo18 = "";
			 // 获取出生年月日
            String birthday = idcardNo.substring(6, 12);
            Date birthDate = null;
            try {
				birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Calendar cal = Calendar.getInstance();
            if (birthDate != null)
                cal.setTime(birthDate);
            // 获取出生年(完全表现形式,如：2010)
            String sYear = String.valueOf(cal.get(Calendar.YEAR));
            idcardNo18 = idcardNo.substring(0, 6) + sYear + idcardNo.substring(8);
            //转换字符数组
            char[] cArr = idcardNo18.toCharArray();
            if (cArr != null) {
                int[] iCard = converCharToInt(cArr);
                int iSum17 = getPowerSum(iCard);
                // 获取校验位
                String sVal = getCheckCode18(iSum17);
                if (sVal.length() > 0) {
             	   idcardNo18 += sVal;
                } else {
                    return 0;
                }
            }
            paramMap.put("IDCARD_NO18", idcardNo18);
			int resultnum18 = transferOrderDao.checkIdcardNo18(paramMap);
			return resultnum18;
		}
		return resultnum;
	}

	@SuppressWarnings("unchecked")
	public int checkedPassportNo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String foreignIdcardNo = request.getParameter("FOREIGNER_IDCARD_NO");//外国人身份证号
		String passportNo = request.getParameter("PASSPORT_NO");//护照号
		Map paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("FOREIGN_IDCARD_NO", foreignIdcardNo);
		paramMap.put("PASSPORT_NO", passportNo);
		int resultnum = transferOrderDao.checkedPassportNo(paramMap);
		
		return resultnum;
	}
	
	 /**
    * 将字符数组转换成数字数组
    * 
    * @param ca
    *            字符数组
    * @return 数字数组
    */
	private static int[] converCharToInt(char[] ca) {
		int len = ca.length;
       int[] iArr = new int[len];
       try {
           for (int i = 0; i < len; i++) {
               iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
           }
       } catch (NumberFormatException e) {
           e.printStackTrace();
       }
       return iArr;
	}
	 /** 每位加权因子 */
   public static final int power[] = {
           7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
   };
   /**
    * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
    * 
    * @param iArr
    * @return 身份证编码。
    */
	private static int getPowerSum(int[] iArr) {
		 int iSum = 0;
	        if (power.length == iArr.length) {
	            for (int i = 0; i < iArr.length; i++) {
	                for (int j = 0; j < power.length; j++) {
	                    if (i == j) {
	                        iSum = iSum + iArr[i] * power[j];
	                    }
	                }
	            }
	        }
	        return iSum;
	}

   /**
    * 将power和值与11取模获得余数进行校验码判断
    * 
    * @param iSum
    * @return 校验位
    */
	private static String getCheckCode18(int iSum) {
		 String sCode = "";
	        switch (iSum % 11) {
	        case 10:
	            sCode = "2";
	            break;
	        case 9:
	            sCode = "3";
	            break;
	        case 8:
	            sCode = "4";
	            break;
	        case 7:
	            sCode = "5";
	            break;
	        case 6:
	            sCode = "6";
	            break;
	        case 5:
	            sCode = "7";
	            break;
	        case 4:
	            sCode = "8";
	            break;
	        case 3:
	            sCode = "9";
	            break;
	        case 2:
	            sCode = "x";
	            break;
	        case 1:
	            sCode = "0";
	            break;
	        case 0:
	            sCode = "1";
	            break;
	        }
	        return sCode;
	}
		
		
	/**
	 * 检查再入职时是否为离职
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List checkIdcardNoAgains(HttpServletRequest request) {
		List retrunList = new ArrayList();
		String idcardNo = request.getParameter("IDCARD_NO");
		String passportNo = request.getParameter("PASSPORT_NO");
		String foreignerIdcardNo = request.getParameter("FOREIGNER_IDCARD_NO");
		Map paramMap = new LinkedHashMap();
		paramMap.put("IDCARD_NO", idcardNo);
		paramMap.put("PASSPORT_NO", passportNo);
		paramMap.put("FOREIGNER_IDCARD_NO", foreignerIdcardNo);
		retrunList = transferOrderDao.checkIdcardNoAgain(paramMap);
		if (retrunList.size() == 0 && idcardNo.length()==18)//18转15
		{
			String idcardNo15 = "";
			StringBuffer sb = new StringBuffer(idcardNo);
	        sb.deleteCharAt(17);
	        sb.deleteCharAt(7);
	        sb.deleteCharAt(6);
	        idcardNo15 = sb.toString();
			paramMap.put("IDCARD_NO15", idcardNo15);
			retrunList = transferOrderDao.checkIdcardNoAgain15(paramMap);
			return retrunList;
		}
		if( retrunList.size() ==0 && idcardNo.length()==15 )//15转18
		{
			String idcardNo18 = "";
			 // 获取出生年月日
            String birthday = idcardNo.substring(6, 12);
            Date birthDate = null;
                try {
					birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            Calendar cal = Calendar.getInstance();
            if (birthDate != null)
                cal.setTime(birthDate);
            // 获取出生年(完全表现形式,如：2010)
            String sYear = String.valueOf(cal.get(Calendar.YEAR));
            idcardNo18 = idcardNo.substring(0, 6) + sYear + idcardNo.substring(8);
            // 转换字符数组
            char[] cArr = idcardNo18.toCharArray();
            if (cArr != null) {
                int[] iCard = converCharToInt(cArr);
                int iSum17 = getPowerSum(iCard);
                // 获取校验位
                String sVal = getCheckCode18(iSum17);
                if (sVal.length() > 0) {
             	   idcardNo18 += sVal;
                } else {
                    return null;
                }
            }
            paramMap.put("IDCARD_NO18", idcardNo18);
			retrunList = transferOrderDao.checkIdcardNoAgain18(paramMap);
			return retrunList;
		}
		return retrunList;
	}

	/**
	 * 获取号俸列表
	 */
	@Override
	public List getHaoFengLists(HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		retrunList = transferOrderDao.getHaoFengList(paramMap);

		return retrunList;
	}
	
	
	/**
	 * 号奉发令列表查询(payStep Query)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getPayStepList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("interLanguage", admin.getLanguage());
		eidsForcommand(paramMap, request);

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getPayStepList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getPayStepList(paramMap);
		}
		
		return retrunList;
	}
	public List getHaoFengList(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		paramMap.put("interLanguage", admin.getLanguage());
		List haofengList = transferOrderDao.getHaoFengList(paramMap);
		return haofengList;
	}
	
	public List getOldPostGradeList2(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		paramMap.put("interLanguage", admin.getLanguage());
		List oldGradeNoList = transferOrderDao.getOldPostGradeList2(paramMap);
		return oldGradeNoList;
	}

	/**
	 * 号奉调整条数查询(paySetp Query count) 
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int getPayStepListCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		eidsForcommand(paramMap, request);

		retrunInt = transferOrderDao.getPayStepListCnt(paramMap);

		return retrunInt;
	}

	
	
	/**
	 * 判断是否需要发令决裁
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int getParamInfoValue(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("TYPE", "hire");
		return transferOrderDao.getParamInfoValue(paramMap);
	}
	
	
	/**
	 * 判断入职发令成功后是否弹出 录入数据页面
	 * 
	 * @param request
	 * @return retrunList
	 * @throws Exception 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int getPopMarkFlag(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PARAM_NO", "122095");
		return transferOrderDao.getPopMarkFlag(paramMap);
	}

	
	//保存号俸发令
	public int savePayStep(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("payStep");
		int result = 0;
		try {
			Map paramValueMap = new LinkedHashMap();
			paramValueMap.put("CPNY_ID", admin.getCpnyId());
			paramValueMap.put("TYPE", "savePayStep");
			// 判断是否需要决裁1为开，0为关
			if (transferOrderDao.getParamInfoValue(paramValueMap) == 1) {
			// 判断是否有特殊设置
				for (int i = 0; i < check.length; i++) {
					Map paramMap = new LinkedHashMap();
					paramMap.put("EMPID", check[i]);
					paramMap.put("CPNY_ID", admin.getCpnyId());
					paramMap.put("PERSON_ID",
							request.getParameter("PERSON_ID_" + check[i]));
					paramMap.put("START_DATE",
							request.getParameter("START_DATE_" + check[i]));
					paramMap.put("ADJUST_REASON",
							request.getParameter("ADJUST_REASON_" + check[i]));
					paramMap.put("CREATED_BY", StringUtil.checkNull(admin.getPersonId(), "CREATED_BY"));
					paramMap.put("DEPTNO",
							request.getParameter("DEPTNO_" + check[i]));
					paramMap.put("POST_GRADE_NO",
							request.getParameter("POST_GRADE_NO_" + check[i]));
					paramMap.put("POSITION_NO",
							request.getParameter("POSITION_NO_" + check[i]));
					paramMap.put("DUTY_NO",
							request.getParameter("DUTY_NO_" + check[i]));
					paramMap.put("PAY_STEP_NO",
							Integer.parseInt(request.getParameter("PAY_STEP_NO_" + check[i])));
					paramMap.put("OLD_POST_GRADE_NO",
							request.getParameter("OLD_POST_GRADE_NO_" + check[i]));
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("TYPE", "savePayStep");
					paramMap.put("ACTIVITY", 0);
					paramMap.put("TYPE", "yidong");
					// 取出被发令人的决裁者信息（个人设置优先）
					List affirmorList = transferOrderDao
							.getAffirmorIdListByPersonal(paramMap);
					// 如果特殊设置不为空
					if (affirmorList.size() != 0) {
						for (int j = 0; j < affirmorList.size(); j++) {
							// 取当前决裁者
							if (j == 0) {
								paramMap.put("CURRENT_AFFIRM_ID",
										((LinkedHashMap) affirmorList.get(j))
												.get("AFFIRMOR_ID"));
							}
						}
						// 保存发令信息
						transferOrderDao.saveExperienceForPayStepAndAffirm(paramMap,affirmorList);
					} else {
						// 取出被发令人的决裁者信息(部门设置)
						List affirmorListByDept = transferOrderDao
								.getAffirmorIdListByDept(paramMap);
						if (affirmorListByDept.size() != 0) {
							for (int j = 0; j < affirmorListByDept.size(); j++) {
								// 取当前决裁者
								if (j == 0) {
									paramMap.put("CURRENT_AFFIRM_ID",
											((LinkedHashMap) affirmorListByDept
													.get(j)).get("AFFIRMOR_ID"));
								}
							}
							// 保存发令信息
							transferOrderDao.saveExperienceForPayStepAndAffirm(paramMap,affirmorListByDept);
						} else {
							// 自定义取决裁者
							Map flagMap = getAffiram(paramMap, expInsideNo,
									request, admin);
							String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap
									.get("AFFIRM_FLAG").toString() : "";

							if ("2".equals(flag)) {
								return 2;
							}
							List newSaveAffirmorList = (List) paramMap
									.get("newSaveAffirmorList");
							transferOrderDao.saveExperienceForPayStepAndAffirm(paramMap,newSaveAffirmorList);
						}
					}

				}
			} else {
				for (int i = 0; i < check.length; i++) {
					Map paramMap = new LinkedHashMap();
					paramMap.put("EMPID", check[i]);
					paramMap.put("CPNY_ID", admin.getCpnyId());
					paramMap.put("PERSON_ID",
							request.getParameter("PERSON_ID_" + check[i]));
					paramMap.put("START_DATE",
							request.getParameter("START_DATE_" + check[i]));
					paramMap.put("ADJUST_REASON",
							request.getParameter("ADJUST_REASON_" + check[i]));
					paramMap.put("CREATED_BY", StringUtil.checkNull(admin.getPersonId(), "CREATED_BY"));
					paramMap.put("DEPTNO",
							request.getParameter("DEPTNO_" + check[i]));
					paramMap.put("POST_GRADE_NO",
							request.getParameter("POST_GRADE_NO_" + check[i]));
					paramMap.put("POSITION_NO",
							request.getParameter("POSITION_NO_" + check[i]));
					paramMap.put("DUTY_NO",
							request.getParameter("DUTY_NO_" + check[i]));
					paramMap.put("PAY_STEP_NO",
							Integer.parseInt(request.getParameter("PAY_STEP_NO_" + check[i])));
					paramMap.put("OLD_POST_GRADE_NO",
							request.getParameter("OLD_POST_GRADE_NO_" + check[i]));
					String expInsideNo = transferOrderDao.getNextExpInside();
					paramMap.put("EXP_INSIDE_NO", expInsideNo);
					paramMap.put("TYPE", "savePayStep");
					paramMap.put("ACTIVITY", 0);
					DateFormat dateSys = new SimpleDateFormat("yyyy-MM-dd");
					String temp_strS = DateUtil.getSysdateStr();
					String startDate = request
							.getParameter("START_DATE_" + check[i]);
					Date startDates = dateSys.parse(startDate);
					Date temp_str = dateSys.parse(temp_strS);
					if (startDates.before(temp_str)
							|| startDates.equals(temp_str)) {
						paramMap.put("ACTIVITY", 1);
						transferOrderDao.savePayStep(paramMap);
					} else {
						paramMap.put("ACTIVITY", 0);
						transferOrderDao.saveExperienceForPayStep(paramMap);
					}
					
				}
				
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	//检查是否有其他号俸发令
	
	public int checkSavePayStep(HttpServletRequest request) {

		int result = 0;
		String check[] = request.getParameterValues("payStep");
		Map paramMap = new LinkedHashMap();
		for (int i = 0; i < check.length; i++) {
			paramMap.put("PERSON_ID",
					request.getParameter("PERSON_ID_" + check[i]));
			result += this.transferOrderDao.checkSavePayStep(paramMap);
		}
		return result;
	}
	
	//判断是否有比生效日期早的发令
	public String haveEalierPayStepData(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String check[] = request.getParameterValues("payStep");
		String empid = "" ;
		for(int i = 0 , len = check.length; i < len ; i++){
			Map paramMap = new LinkedHashMap();
			paramMap.put("START_DATE",
					request.getParameter("START_DATE_" + check[i]));
			paramMap.put("PERSON_ID",
					request.getParameter("PERSON_ID_" + check[i]));
			paramMap.put("EMPID", check[i]);
			if(this.transferOrderDao.haveEalierPayStepData(paramMap) > 0){
				empid = check[i];
				break;
			}
		}
		return empid;
	}

	/**
	 * 查询工资输入项目参数（）
	 * 
	 * @param request
	 * @return request
	 */
	@SuppressWarnings("unchecked")
	public List getSaParamItemParamList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = transferOrderDao.getSaParamItemParamList(paramMap);
		return retrunList;
	}
	
	/**
	 *  查询奖金输入项目参数
	 * 
	 * @param request
	 * @return request
	 */
	@SuppressWarnings("unchecked")
	public List getBnParamItemParamList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = transferOrderDao.getBnParamItemParamList(paramMap);
		return retrunList;
	}
	
	/**
	 *  查询保险输入项目参数
	 * 
	 * @param request
	 * @return request
	 */
	@SuppressWarnings("unchecked")
	public List getInParamItemParamList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = transferOrderDao.getInParamItemParamList(paramMap);
		return retrunList;
	}
	
	
	
	/**
	 *  查询工资基础项目
	 * 
	 * @param request
	 * @return request
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemParamList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = transferOrderDao.getPaBasicItemParamList(paramMap);
		return retrunList;
	}
	

	/**
	 * 保存工资保险奖金数据
	 * 
	 * @param request
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveSaBnIn(HttpServletRequest request) {
		LinkedHashMap sadata = getRequestParamDataForSaBnIn(request,"sa_");
		LinkedHashMap bndata = getRequestParamDataForSaBnIn(request,"bn_");
		LinkedHashMap indata = getRequestParamDataForSaBnIn(request,"in_");
		LinkedHashMap padata = getRequestParamDataForSaBnIn(request,"pa_");
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int result = 0;
		try {
			transferOrderDao.saveSaBnIn(request,sadata,bndata,indata,padata,paramMap);
			result=1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * bind parameter data to Map object from Request Object
	 * 
	 * @param req
	 * @return LinkedHashMap
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap getRequestParamDataForSaBnIn(HttpServletRequest request,String embellish) {
		LinkedHashMap data = new LinkedHashMap();
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();

			if(key.indexOf(embellish)>0){
			    data.put(key.replaceAll(embellish, ""), request.getParameter(key));
			}
			else if(key.startsWith(embellish))
			{	
				data.put(key.replaceAll(embellish, ""), request.getParameter(key));
			}
		}
		return data;
	}

	@Override
	@SuppressWarnings("unchecked")
	/**
	 * 根据职级参数   查询对应的职等
	 */
	public List getGradeLevelNoByPostGradeNoList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getGradeLevelNoByPostGradeNoList(paramMap);
		return retrunList;
	}


	/**
	 * 查询详细路径信息
	 */
	@Override
	public List getRecSourceList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		paramMap.put("PARENT_CODE_NO", "3306");
		paramMap.put("interLanguage", admin.getLanguage());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getRecSourceList(paramMap);

		return retrunList;
	}

	/**
	 * 查询详细路径信息
	 */
	@Override
	public List getRecSourceListForUpdate(HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		paramMap.put("PARENT_CODE_NO", "3306");
		paramMap.put("interLanguage", admin.getLanguage());
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getRecSourceListForUpdate(paramMap);

		return retrunList;
	}
	
	@Override
	public List getRecSourceDetailByRecSource(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = transferOrderDao.getRecSourceDetailByRecSource(paramMap);
		return retrunList;
	}

	@Override
	public List getTransList(LinkedHashMap paramMap, HttpServletRequest request) {
		List retrunList = new ArrayList();
		eidsForcommand(paramMap, request);
		retrunList = transferOrderDao.getTransList(paramMap);	
		return retrunList;
	}

	@Override
	public List getBLACKLISTList(LinkedHashMap paramMap, HttpServletRequest request) {
		List retrunList = new ArrayList();
		eidsForcommand(paramMap, request);
		retrunList = transferOrderDao.getBLACKLISTList(paramMap);
		return retrunList;
	}
	public List getHrEmployeeList(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("GGS",  request.getParameter("ggs"));
		paramMap.put("cpnyid",admin.getCpnyId());
		paramMap.put("POSTNO",request.getParameter("postno"));
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("EMPID", request.getParameter("name"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		List personidList =null;
		String selectType= request.getParameter("selectType");
		//logger.debug("jjy:::paramMap:::"+paramMap);
		
		if(request.getParameter("selectType")!=null&&!request.	getParameter("selectType").equals("")){
			
			paramMap.put("TYPE", "1");
			//123346 复职,123315 兼职解除,123358 借调解除,123360 待处理解除
			if("1".equals(selectType)){//奖励
				paramMap.put("TRANS_NO", "641");
				paramMap.put("CREATED_BY", admin.getPersonId());
				paramMap.put("TABLE_NAME", "HR_REWARD");
				try {
					personidList = this.transferOrderDao.getStoredRewardList(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if("2".equals(selectType)){//惩罚
				paramMap.put("TRANS_NO", "642");
				paramMap.put("CREATED_BY", admin.getPersonId());
				paramMap.put("TABLE_NAME", "HR_PUNISHMENT");
				try {
					personidList = this.transferOrderDao.getStoredPunishmentList(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(selectType.equals("123353")||selectType.equals("123315")||selectType.equals("123358")||selectType.equals("123360")||selectType.equals("123346")){
				//123353:대리해제, 123315:겸직해제, 123358:파견해제, 123360:대기해제, 123346:복직
				paramMap.put("TYPE", "2");	
					//代理
				if(selectType.equals("123353")){
					paramMap.put("CODE",  "123352");
					paramMap.put("CODE1",  "123353");
				}else if(selectType.equals("123315")){
					//兼职
					paramMap.put("CODE",  "123314");
					paramMap.put("CODE1",  "123315");
				}else if(selectType.equals("123358")){
					//借调
					paramMap.put("CODE",  "123357");
					paramMap.put("CODE1",  "123358");
				}else if(selectType.equals("123360")){
					//待处理
					paramMap.put("CODE",  "123359");
					paramMap.put("CODE1",  "123360");
				}else if(selectType.equals("123346")){
					//休职
					paramMap.put("CODE",  "123318");
					paramMap.put("CODE1",  "123346");
				}
				
				paramMap.put("createdBy", admin.getAdminID());
				
				paramMap.put("CPNYID",admin.getCpnyId());
				//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
				paramMap.put("LAN", admin.getLanguage());
				paramMap.put("PERSON_ID", admin.getPersonId());
				//personidList = transferOrderDao.getHrExperienceInsideSaveByTransCode(paramMap);
				personidList=transferOrderDao.getHrExperienceInsideByTransCode(paramMap);
				//logger.debug("personidList1:::"+personidList.size());
				if(personidList.size()<=0){
					return retrunList;
				}
			}else{
				
				
				paramMap.put("TYPE", "1");	
				paramMap.put("createdBy", admin.getAdminID());
				paramMap.put("CODE",  selectType);
				paramMap.put("CPNYID",admin.getCpnyId());
				//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
				paramMap.put("LAN", admin.getLanguage());
				personidList = transferOrderDao.getHrExperienceInsideByTransCodeDefault(paramMap);
				//personidList = transferOrderDao.getHrExperienceInsideSaveByTransCode(paramMap);
				//personidList = transferOrderDao.getHrExperienceInsideByTransCode(paramMap);
				//logger.debug("personidList2:::"+personidList.size());
				
			}
			
			String personId="";
			for(int i=0;i<personidList.size();i++){
				Map map=(Map)personidList.get(i);
				personId+=map.get("PERSON_ID").toString()+",";
			}
			int personIdNum=-1;
			if(personidList.size()>0){
				personIdNum=personId.lastIndexOf(",");
				paramMap.put("per", personId.substring(0,personIdNum));
			}
		}  
		if(selectType.equals("123347")){
			paramMap.put("zhuanzhengType", "Y");	
		}
		
		retrunList = transferOrderDao.getHrEmployeeList(paramMap);
		return retrunList;
	}
	public List getTranferOrderTitile(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("DEPTNO", request.getParameter("dep"));
		paramMap.put("ADMIN_ID", admin.getAdminID());
		String code="";
		if(request.getParameter("code")!=null){
			code=request.getParameter("code");
		}else{
			code=request.getParameter("OrderType");
		}
		paramMap.put("CODE",  code);
		paramMap.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("LAN", admin.getLanguage());
		paramMap.put("NOT_TRANS_CONFIG_FLAG", "'0'");
		retrunList = transferOrderDao.getTranferOrderTitile(paramMap);
		return retrunList;
	}
	public List getHrExperienceInsideByPersonId(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		List retrunList = new ArrayList();
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("DEPTNO", request.getParameter("dep"));
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CODE",  request.getParameter("code"));
		paramMap.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("LAN", admin.getLanguage());
		String personid1=request.getParameter("personId");
		String personid2 []=personid1.split("-");
		String personid4="";
		for(int i=0;i<personid2.length;i++){
			String personid3 []=personid2[i].split(",");
			personid4+=personid3[0]+",";
		}
		String personid5=personid4.substring(0,personid4.length()-1);
		              
		paramMap.put("TRANSNO",request.getParameter("transno"));
		
		              
		paramMap.put("PERSONID",personid5);
		retrunList = transferOrderDao.getHrExperienceInsideByPersonId(paramMap);
		
		
		
		return retrunList;
	}
	/**
	 * 根据EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
	 * @param request
	 * @return List
	 */
	@Override
	public List getEmpIdList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("deptNo",admin.getDeptNo());
		paramMap.put("userNo", admin.getUserNo());
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID());
		}

		if (UiUtil.getPageNum(request) > 0){
			retrunList = transferOrderDao.getEmpIdList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = transferOrderDao.getEmpIdList(paramMap) ;
		}
		
		return retrunList ;

	}
	/**
	 * 获取员工信息个数(For the number of staff information)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEmpIdListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("deptNo",admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		retrunInt = transferOrderDao.getEmpIdListCnt(paramMap) ;
		
		return retrunInt ;
		
	}
	
	
	/**
	 * 派遣地（SaveHrExperienceInside）
	 * 
	 * @param request
	 * @return result
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getHrDispatch(HttpServletRequest request) throws Exception {
		int result=-1;
		List list=new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String OrderType=request.getParameter("OrderType");//调令类型
		String searchDeptNo=request.getParameter("seach_DEPTNO");
		String searchName=request.getParameter("seach_EMPID");
		String searchActivity=request.getParameter("seach_ACTIVITY");
		if("3".equals(searchActivity)){
			searchActivity=null;
		}
		String searchDuty=request.getParameter("seach_DUTY");
		String transNo=StringUtil.checkNull(request.getParameter("transId"));
		if(!"".equals(transNo)){
			transferOrderDao.SaveHrDispInside(transNo);
		}
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
	 
		
		
		paramMap.put("SEARCHDEPTNO",searchDeptNo);
		paramMap.put("SEARCHEMPID", searchName);
		paramMap.put("SEARCHACTIVITY", searchActivity);
		paramMap.put("SEARCHDUTY", searchDuty);
		paramMap.put("CODE",  OrderType);
		paramMap.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("LAN", admin.getLanguage());
	 
		 
		//List list=transferOrderDao.getHrDispatch(paramMap);
		if (UiUtil.getPageNum(request) > 0){
			list=transferOrderDao.getHrDispatch(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			//retrunList = transferOrderDao.getEmpIdList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			list=transferOrderDao.getHrDispatch(paramMap);
			//retrunList = transferOrderDao.getEmpIdList(paramMap) ;
		}
		
		return list;
	}
	
	/**
	 * 派遣地（SaveHrExperienceInside）
	 * 
	 * @param request
	 * @return result
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getHrDispatchUpdate(HttpServletRequest request) throws Exception {
		int result=-1;
		List list=new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String OrderType=request.getParameter("OrderType");//调令类型
		String searchDeptNo=request.getParameter("seach_DEPTNO");
		String searchName=request.getParameter("seach_EMPID");
		String searchActivity=request.getParameter("seach_ACTIVITY");
		if("3".equals(searchActivity)){
			searchActivity=null;
		}
		String searchDuty=request.getParameter("seach_DUTY");
		String transNo=StringUtil.checkNull(request.getParameter("transId"));
	 
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
	 
		
		
		paramMap.put("SEARCHDEPTNO",searchDeptNo);
		paramMap.put("SEARCHEMPID", searchName);
		paramMap.put("SEARCHACTIVITY", searchActivity);
		paramMap.put("SEARCHDUTY", searchDuty);
		paramMap.put("CODE",  OrderType);
		paramMap.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("LAN", admin.getLanguage());
		 
		 
		//List list=transferOrderDao.getHrDispatch(paramMap);
		if (UiUtil.getPageNum(request) > 0){
			list=transferOrderDao.getHrDispatchUpdate(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			//retrunList = transferOrderDao.getEmpIdList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			list=transferOrderDao.getHrDispatchUpdate(paramMap);
			//retrunList = transferOrderDao.getEmpIdList(paramMap) ;
		}
		
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map submitAddHrDispatch(HttpServletRequest request) {
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		int vResult =0;
		String date = "";
		String item = "";
		Map messMap = new LinkedHashMap();
		// 页面参数
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> paDetailInfoList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		for (int i = 0; i < paDetailInfoList.size(); i++) {
			LinkedHashMap arDetailInfoMap = (LinkedHashMap) paDetailInfoList
					.get(i);
			//arDetailInfoMap.put("interCpnyID", admin.getCpnyId());
			//arDetailInfoMap.put("interLanguage", admin.getLanguage());
			arDetailInfoMap.put("paStart",(String)arDetailInfoMap.get("paYearStartDate")+(String)arDetailInfoMap.get("paMonthStartDate"));
			arDetailInfoMap.put("paEnd",(String)arDetailInfoMap.get("paYearEndDate")+(String)arDetailInfoMap.get("paMonthEndDate"));
			
			vResult=transferOrderDao.getAddHrDispatchCnt(arDetailInfoMap)+vResult;//验证是否已经存在
		}
		
		try {
			if(vResult>0){
				messMap.put("scode", "0");
			}else{
				this.transferOrderDao.submitAddHrDispatch(paDetailInfoList);
				messMap.put("scode", "1");
			}
				
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messMap.put("scode", "0");
			return messMap;
		}
		return messMap;
	}
	
	@SuppressWarnings("unchecked")
	public Map updatesubmitSendAndSendOff(HttpServletRequest request) {
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		int vResult =0;
		String date = "";
		String item = "";
		Map messMap = new LinkedHashMap();
		// 页面参数
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> paDetailInfoList = ObjectBindUtil
				.getRequestJsonData(jsonString);
		for (int i = 0; i < paDetailInfoList.size(); i++) {
			LinkedHashMap arDetailInfoMap = (LinkedHashMap) paDetailInfoList
					.get(i);
			//arDetailInfoMap.put("interCpnyID", admin.getCpnyId());
			//arDetailInfoMap.put("interLanguage", admin.getLanguage());
			arDetailInfoMap.put("paStart",(String)arDetailInfoMap.get("paYearStartDate")+(String)arDetailInfoMap.get("paMonthStartDate"));
			arDetailInfoMap.put("paEnd",(String)arDetailInfoMap.get("paYearEndDate")+(String)arDetailInfoMap.get("paMonthEndDate"));
			arDetailInfoMap.put("PERSON_ID", admin.getPersonId());
			vResult=transferOrderDao.getAddHrDispatchCnt(arDetailInfoMap)+vResult;//验证工资确认的无法撤销
		}
		
		try {
			/*if(vResult>0){
				messMap.put("scode", "0");
			}else{*/
				this.transferOrderDao.updatesubmitAddHrDispatch(paDetailInfoList);
				messMap.put("scode", "1");
			 
	//	}
				
				
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messMap.put("scode", "0");
			return messMap;
		}
		return messMap;
	}
	
	
	/**
	 * 派遣地（SaveHrExperienceInside）
	 * 
	 * @param request
	 * @return result
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getHrDispatch2(HttpServletRequest request) throws Exception {
		int result=-1;
		List list=new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String OrderType=request.getParameter("OrderType");//调令类型
		String searchDeptNo=request.getParameter("seach_DEPTNO");
		String searchName=request.getParameter("seach_EMPID");
		String searchActivity=request.getParameter("seach_ACTIVITY");
		if("3".equals(searchActivity)){
			searchActivity=null;
		}
		String searchDuty=request.getParameter("seach_DUTY");
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("SEARCHDEPTNO",searchDeptNo);
		paramMap.put("SEARCHEMPID", searchName);
		paramMap.put("SEARCHACTIVITY", searchActivity);
		paramMap.put("SEARCHDUTY", searchDuty);
		paramMap.put("CODE",  OrderType);
		paramMap.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("LAN", admin.getLanguage());
	 
		//List list=transferOrderDao.getHrDispatch(paramMap);
		if (UiUtil.getPageNum(request) > 0){
			list=transferOrderDao.getHrDispatch2(paramMap);
			//retrunList = transferOrderDao.getEmpIdList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			list=transferOrderDao.getHrDispatch2(paramMap);
			//retrunList = transferOrderDao.getEmpIdList(paramMap) ;
		}
		
		return list;
	}
	
	/**
	 * 派遣地（SaveHrExperienceInside）
	 * 
	 * @param request
	 * @return result
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getHrDispatch3(HttpServletRequest request) throws Exception {
		int result=-1;
		List list=new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String OrderType=request.getParameter("OrderType");//调令类型
		String searchDeptNo=request.getParameter("seach_DEPTNO");
		String searchName=request.getParameter("seach_EMPID");
		String searchActivity=request.getParameter("seach_ACTIVITY");
		if("3".equals(searchActivity)){
			searchActivity=null;
		}
		String searchDuty=request.getParameter("seach_DUTY");
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("SEARCHDEPTNO",searchDeptNo);
		paramMap.put("SEARCHEMPID", searchName);
		paramMap.put("SEARCHACTIVITY", searchActivity);
		paramMap.put("SEARCHDUTY", searchDuty);
		paramMap.put("CODE",  OrderType);
		paramMap.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("LAN", admin.getLanguage());
		 
		//List list=transferOrderDao.getHrDispatch(paramMap);
		if (UiUtil.getPageNum(request) > 0){
			list=transferOrderDao.getHrDispatch3(paramMap);
			//retrunList = transferOrderDao.getEmpIdList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			list=transferOrderDao.getHrDispatch3(paramMap);
			//retrunList = transferOrderDao.getEmpIdList(paramMap) ;
		}
		
		return list;
	}
	
	

	/**
	 * 派遣地（SaveHrExperienceInside）
	 * 
	 * @param request
	 * @return result
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getHrDispatchCnt(HttpServletRequest request) throws Exception {
		int result=-1;
		List list=new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String OrderType=request.getParameter("OrderType");//调令类型
		String searchDeptNo=request.getParameter("seach_DEPTNO");
		String searchName=request.getParameter("seach_EMPID");
		String searchActivity=request.getParameter("seach_ACTIVITY");
		if("3".equals(searchActivity)){
			searchActivity=null;
		}
		String searchDuty=request.getParameter("seach_DUTY");
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
	 
		paramMap.put("SEARCHDEPTNO",searchDeptNo);
		paramMap.put("SEARCHEMPID", searchName);
		paramMap.put("SEARCHACTIVITY", searchActivity);
		paramMap.put("SEARCHDUTY", searchDuty);
		paramMap.put("CODE",  OrderType);
		paramMap.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("LAN", admin.getLanguage());
		 
		
		
		
		result=transferOrderDao.getHrDispatchCnt(paramMap);
			
		return result;
		
	}
	/**
	 * 派遣地（SaveHrExperienceInside）
	 * 
	 * @param request
	 * @return result
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getHrDispatchUpdateCnt(HttpServletRequest request) throws Exception {
		int result=-1;
		List list=new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String OrderType=request.getParameter("OrderType");//调令类型
		String searchDeptNo=request.getParameter("seach_DEPTNO");
		String searchName=request.getParameter("seach_EMPID");
		String searchActivity=request.getParameter("seach_ACTIVITY");
		if("3".equals(searchActivity)){
			searchActivity=null;
		}
		String searchDuty=request.getParameter("seach_DUTY");
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
	 
		paramMap.put("SEARCHDEPTNO",searchDeptNo);
		paramMap.put("SEARCHEMPID", searchName);
		paramMap.put("SEARCHACTIVITY", searchActivity);
		paramMap.put("SEARCHDUTY", searchDuty);
		paramMap.put("CODE",  OrderType);
		paramMap.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("LAN", admin.getLanguage());
		 
		result=transferOrderDao.getHrDispatchUpdateCnt(paramMap);
			
		return result;
		
	}
	
	
	/**
	 * 提交调令（SaveHrExperienceInside）
	 * 
	 * @param request
	 * @return result
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int SaveHrExperienceInside(HttpServletRequest request) throws Exception {
		int result=-1;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String OrderType=request.getParameter("OrderType");//调令类型
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("DEPTNO", request.getParameter("dep"));
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CODE",  OrderType);
		paramMap.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("LAN", admin.getLanguage());
		  
		
		
		transferOrderDao.SaveHrExperienceInside(paramMap);
		return result;
	}
	/**
	 * 提交调令保存到临时表（SaveHrExperienceInsideSave）
	 * 
	 * @param request
	 * @return result
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int SaveHrExperienceInsideSave(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String type=request.getParameter("diaolingType");
		int result=-1;
		int cloumeNum =Integer.parseInt(request.getParameter("cloumeNum").toString())-1;
		LinkedHashMap paramMap1 = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		paramMap1.put("specialParam", admin.getSpecialParam());
		paramMap1.put("userNo", admin.getUserNo());
		paramMap1.put("DEPTNO", request.getParameter("dep"));
		paramMap1.put("ADMIN_ID", admin.getAdminID());
		String code=request.getParameter("OrderType");
		if(request.getParameter("code")!=null){
			code=request.getParameter("code");
		}
		paramMap1.put("CODE",  code);
		paramMap1.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap1.put("LAN", admin.getLanguage());
		String[] personid=request.getParameterValues("personid");
		String[] ExpInsideNos=request.getParameterValues("EXP_INSIDE_NO");
		
		
		List listTitle=this.transferOrderDao.getTranferOrderTitile(paramMap1);
		String reasonType="";
		for(int i=0;i<listTitle.size();i++){
			
			Map map=(Map)listTitle.get(i);
			if(map.get("DISTINCT_FIELD").equals("TRANSFER_ORDER_REASON")){
				reasonType=map.get("OUTPUT_TYPE").toString();
			}
		}
		//Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map paramMap=new LinkedHashMap<String, Object>();
		String sql="";
		paramMap.put("TRANS_NO", code);
		paramMap.put("CPNY_ID", admin.getAdminID());
		//String [] cloumeNumValue=request.getParameterValues("cloumeNumValue");
		String [] cloumeNumValue=request.getParameterValues("cloumeNumValue");
		String chexkBoxValue=request.getParameter("checkBoxValue");
		int checkBoxValueNum=chexkBoxValue.lastIndexOf("-");
		String [] chexkBoxValues=chexkBoxValue.substring(0,checkBoxValueNum).split("-");
		try{
		for(int i=0;i<chexkBoxValues.length;i++){
			String [] colValue=chexkBoxValues[i].split(",");
			String personNew=colValue[0];
			int colNew=Integer.parseInt(colValue[1]);
			paramMap.put("PERSONID", personNew);
			//transferOrderDao.deleteHrExperienceInsideSaveByPersonIds(paramMap);
			//i=Integer.parseInt(cloumeNumValue[i]);
			/*for(int j=0;j<listTitle.size();j++){
				Map map=(Map)listTitle.get(j);
				sql+=map.get("DISTINCT_FIELD")+","+map.get("CONTENT")+"-";
				String colValue="";
				if((request.getParameter(map.get("DISTINCT_FIELD")+"_"+i))!=null){
					colValue=request.getParameter(map.get("DISTINCT_FIELD")+"_"+i);
				}else{
					colValue="";
				}
				
				
			paramMap.put(map.get("DISTINCT_FIELD"), colValue);
			
			}*/
			/*String [] expInsizeSave=
			{         "EXP_INSI _NO","TRANS_NO","TRANS_CODE","PERSON_ID","POST_GRADE_NO",
					  "POSITION_NO","START_DATE", "END_DATE","REMARK","CREATE_DATE",
					  "CREATED_BY","UPDATE_DATE", "UPDATED_BY", "ORDERNO","ACTIVITY",
					  "STATUS_CODE","POST_NO","DEPTNO","DUTY_NO","WORK_AREA",
					  "OLD_POST_GRADE_NO","PAY_STEP","AGENT_POST_GRADE_NO" ,
					  "CURRENT_AFFIRM_ID","OLD_POSITION_NO","OLD_POST_NO",
					  "OLD_DEPTNO","OLD_DUTY_NO","GRADE_LEVEL","PARTTIME_DEPT"
					  ,"DEPT_POST_GRADE_NO","DEPT_POST_NO","PARTTIME_DUTY",
					  "DEPT_POSITION_NO","DETAIL_HR_DIFF"      ,
					  "SAL_CALCULATE_DATE","TRANSFER_ORDER_REASON","TRANS_NUM" 
			};*/
			
			
			//调令类
			
			if(reasonType.equals("2")){
				
				LinkedHashMap paramMap2 = ObjectBindUtil.getRequestParamData(request,"seach_");
				paramMap2.put("NO", request.getParameter("TRANSFER_ORDER_REASON_"+colNew));
				paramMap2.put("LAN", admin.getLanguage());
				List reason=this.transferOrderDao.getSycodeByCodeId(paramMap2);
				String reason1="";
				if(reason.size()==0){
					reason1="";
				}else{
					reason1=((Map)reason.get(0)).get("SCONTENT").toString();
				}
				 
				//调令事由
				if(request.getParameter("TRANSFER_ORDER_REASON_"+colNew)!=null){
					paramMap.put("TRANSFER_ORDER_REASON",reason1 );
				}	
				//if(request.getParameter("TRANS_CODE_"+i)!=null){
					paramMap.put("TRANS_CODE",request.getParameter("TRANSFER_ORDER_REASON_"+colNew) );
				//}
			}else{
				if(request.getParameter("TRANS_CODE_"+colNew)!=null){
					paramMap.put("TRANS_CODE",request.getParameter("TRANS_CODE"+colNew) );
				}
				//调令事由
				if(request.getParameter("TRANSFER_ORDER_REASON_"+colNew)!=null){
					paramMap.put("TRANSFER_ORDER_REASON",request.getParameter("TRANSFER_ORDER_REASON_"+colNew) );
				}	
			}
			
			
			
			
			
			//调令日期
			if(request.getParameter("TRANS_ORDER_DATE_"+colNew)!=null){
				paramMap.put("START_DATE",request.getParameter("TRANS_ORDER_DATE_"+colNew) );
			}
			//调令结束日
			if(request.getParameter("TRANS_ORDER_ENDDATE_"+colNew)!=null){
				paramMap.put("END_DATE",request.getParameter("TRANS_ORDER_ENDDATE_"+colNew) );
			}
			
			//部门
			if(request.getParameter("DEPTNO_"+colNew)!=null){
				paramMap.put("DEPTNO",request.getParameter("DEPTNO_"+colNew) );
			}
			//职等
			if(request.getParameter("GRADE_LEVEL_"+colNew)!=null){
				paramMap.put("GRADE_LEVEL",request.getParameter("GRADE_LEVEL_"+colNew) );
			}
			//职责
			if(request.getParameter("DUTY_NO_"+colNew)!=null){
				paramMap.put("DUTY_NO",request.getParameter("DUTY_NO_"+colNew) );
			}
			//职级
			if(request.getParameter("POST_GRADE_NO_"+colNew)!=null){
				paramMap.put("POST_GRADE_NO",request.getParameter("POST_GRADE_NO_"+colNew) );
			}
			//职级名称
			if(request.getParameter("POST_NO_"+colNew)!=null){
				paramMap.put("POST_NO",request.getParameter("POST_NO_"+colNew) );
			}
			//职位
			if(request.getParameter("POSITION_NO_"+colNew)!=null){
							
				paramMap.put("POSITION_NO",request.getParameter("POSITION_NO_"+colNew) );
			}
			//工作地
			if(request.getParameter("WORK_AREA_NAME_"+colNew)!=null){
				paramMap.put("WORK_AREA",request.getParameter("WORK_AREA_NAME_"+colNew) );
			}
			//兼职/代职 部门
			if(request.getParameter("PARTTIME_DEPT_"+colNew)!=null){
				paramMap.put("PARTTIME_DEPT",request.getParameter("PARTTIME_DEPT_"+colNew) );
			}
			//兼职/代职 职级
			if(request.getParameter("DEPT_POST_GRADE_NO_"+colNew)!=null){
				paramMap.put("DEPT_POST_GRADE_NO",request.getParameter("DEPT_POST_GRADE_NO_"+colNew) );
			}
			//兼职/代职 职级名称
			if(request.getParameter("DEPT_POST_NO_"+colNew)!=null){
				paramMap.put("DEPT_POST_NO",request.getParameter("DEPT_POST_NO_"+colNew) );
			}
			//兼职职责
			if(request.getParameter("PARTTIME_DUTY_"+colNew)!=null){
				paramMap.put("PARTTIME_DUTY",request.getParameter("PARTTIME_DUTY_"+colNew) );
			}
			//兼职/代职 职位
			if(request.getParameter("DEPT_POSITION_NO_"+colNew)!=null){
				paramMap.put("DEPT_POSITION_NO",request.getParameter("DEPT_POSITION_NO_"+colNew) );
			}
			//详细人力区分DETAIL_HR_DIFF
			if(request.getParameter("DETAIL_HR_DIFF_"+colNew)!=null){
				paramMap.put("DETAIL_HR_DIFF",request.getParameter("DETAIL_HR_DIFF_"+colNew) );
			}	
			//工资结算日SAL_CALCULATE_DATE
			if(request.getParameter("SAL_CALCULATE_DATE_"+colNew)!=null){
				paramMap.put("SAL_CALCULATE_DATE",request.getParameter("SAL_CALCULATE_DATE_"+colNew) );
			}	
			//备注REMARK
			if(request.getParameter("REMARK_"+colNew)!=null){
				paramMap.put("REMARK",request.getParameter("REMARK_"+colNew) );
			}
			if(request.getParameter("CUR_POST_GRADE_NO_"+colNew)!=null){
				paramMap.put("CUR_POST_GRADE_NO",request.getParameter("CUR_POST_GRADE_NO_"+colNew) );
			}
			if(request.getParameter("CUR_POST_NO_"+colNew)!=null){
				paramMap.put("CUR_POST_NO",request.getParameter("CUR_POST_NO_"+colNew) );
			}
			if(request.getParameter("CUR_POSITION_NO_"+colNew)!=null){
				paramMap.put("CUR_POSITION_NO",request.getParameter("CUR_POSITION_NO_"+colNew) );
			}
			if(request.getParameter("REPLACE_DEPT_"+colNew)!=null){
				paramMap.put("REPLACE_DEPT",request.getParameter("REPLACE_DEPT_"+colNew) );
			}
			if(request.getParameter("REPLACE_POST_GRADE_NO_"+colNew)!=null){
				paramMap.put("REPLACE_POST_GRADE_NO",request.getParameter("REPLACE_POST_GRADE_NO"+colNew) );
			}
			if(request.getParameter("REPLACE_POST_NO_"+colNew)!=null){
				paramMap.put("REPLACE_POST_NO",request.getParameter("REPLACE_POST_NO_"+colNew) );
			}
			if(request.getParameter("REPLACE_DUTY_"+colNew)!=null){
				paramMap.put("REPLACE_DUTY",request.getParameter("REPLACE_DUTY_"+colNew) );
			}
			if(request.getParameter("REPLACE_POSITION_NO_"+colNew)!=null){
				paramMap.put("REPLACE_POSITION_NO",request.getParameter("REPLACE_POSITION_NO_"+colNew) );
			}
			if(request.getParameter("POSITIVE_DATES_"+colNew)!=null){
				paramMap.put("POSITIVE_DATES",request.getParameter("POSITIVE_DATES_"+colNew) );
			}
			if(request.getParameter("POSITION_PAUSE_TYPE_"+colNew)!=null){
				paramMap.put("POSITION_PAUSE_TYPE",request.getParameter("POSITION_PAUSE_TYPE_"+colNew) );
			}
			if(request.getParameter("RESIGN_TYPE_"+colNew)!=null){
				paramMap.put("RESIGN_TYPE",request.getParameter("RESIGN_TYPE_"+colNew) );
			}
			if(request.getParameter("RESIGN_REASON_"+colNew)!=null){
				paramMap.put("RESIGN_REASON",request.getParameter("RESIGN_REASON_"+colNew) );
			}
			if(request.getParameter("TEMP_CPNY_"+colNew)!=null){
				paramMap.put("TEMP_CPNY",request.getParameter("TEMP_CPNY_"+colNew) );
			}
			if(request.getParameter("TEMP_DATE_"+colNew)!=null){
				paramMap.put("TEMP_DATE",request.getParameter("TEMP_DATE_"+colNew) );
			}                               
			String aa=request.getParameter("CUR_DEPTNO_1");
			if(request.getParameter("CUR_DEPTNO_"+colNew)!=null){
				paramMap.put("CUR_DEPTNO",request.getParameter("CUR_DEPTNO_"+colNew) );
			}
			if(request.getParameter("CUR_GRADE_LEVEL_"+colNew)!=null){
				paramMap.put("CUR_GRADE_LEVEL",request.getParameter("CUR_GRADE_LEVEL_"+colNew) );
			}
			
			if(request.getParameter("CUR_DUTY_NO_"+colNew)!=null){
				paramMap.put("CUR_DUTY_NO",request.getParameter("CUR_DUTY_NO_"+colNew) );
			}
			//WORK_AREA_NAME PARTTIME_POSITION_NO PARTTIME_POST_GRADE_NO PARTTIME_POST_NO
			if(request.getParameter("WORK_AREA_NAME_"+colNew)!=null){
				paramMap.put("WORK_AREA_NAME",request.getParameter("WORK_AREA_NAME_"+colNew) );
			}
			if(request.getParameter("PARTTIME_POSITION_NO_"+colNew)!=null){
				paramMap.put("PARTTIME_POSITION_NO",request.getParameter("PARTTIME_POSITION_NO_"+colNew) );
			}
			if(request.getParameter("PARTTIME_POST_GRADE_NO_"+colNew)!=null){
				paramMap.put("PARTTIME_POST_GRADE_NO",request.getParameter("PARTTIME_POST_GRADE_NO_"+colNew) );
			}
			if(request.getParameter("PARTTIME_POST_NO_"+colNew)!=null){
				paramMap.put("PARTTIME_POST_NO",request.getParameter("PARTTIME_POST_NO_"+colNew) );
			}
			//调令编号
			if(request.getParameter("TRANSFER_ORDER_NO")!=null){
				paramMap.put("TRANS_NUM",request.getParameter("TRANSFER_ORDER_NO") );
			}
			
			
			
			
			
			
			paramMap.put("PERSON_ID", personNew);
			//取消调令 123353,123315 123358 123360
			if(code.equals("123315")||code.equals("123353")||code.equals("123358")||code.equals("123360")){
				if(code.equals("123353")){
					paramMap.put("CODE",  "123352");
				}else if(code.equals("123315")){
					//兼职
					paramMap.put("CODE",  "123314");
				}else if(code.equals("123358")){
					//借调
					paramMap.put("CODE",  "123357");
				}else if(code.equals("123360")){
					//待处理
					paramMap.put("CODE",  "123359");
				}
				
				String expNo=transferOrderDao.getExpInsideNo1(paramMap);
				paramMap.put("RELATION_EXP_INSIDE_NO", expNo);
			}
			
			
			//调令类型
			paramMap.put("TRANS_ORDER_TYPE", code);
			////工号+姓名
			
			
			// 取出人员的详细信息
			LinkedHashMap paramMap2 = ObjectBindUtil.getRequestParamData(request,"seach_");
			paramMap2.put("PERSONID", personNew);
			List paHrVList=transferOrderDao.getPaHrVList(paramMap2);
			
			/*#OLD_POST_GRADE_NO#:VARCHAR)                  ,       <!-- 前职务等级-->                                      
		  	#OLD_POSITION_NO#:VARCHAR)                    ,       <!-- 前职位-->                                      
		  	#OLD_POST_NO#:VARCHAR)                        ,       <!-- 前职务代码-->                                      
		  	#OLD_DEPTNO#:VARCHAR)                         ,       <!-- 前部门序号-->                                      
		  	#OLD_DUTY_NO#:VARCHAR)                        ,       <!-- 前职责-->   */
			Map mapPa=(Map)paHrVList.get(0);
			paramMap.put("OLD_POST_GRADE_NO",mapPa.get("POST_GRADE_NO"));
			paramMap.put("OLD_POSITION_NO",mapPa.get("POSITION_NO"));
			paramMap.put("OLD_POST_NO",mapPa.get("POST_NO"));
			paramMap.put("OLD_DEPTNO",mapPa.get("DEPTNO1"));
			paramMap.put("OLD_DUTY_NO",mapPa.get("DUTY_NO"));
			paramMap.put("STATUS_CODE", mapPa.get("STATUS_CODE"));//员工状态
			paramMap.put("AGENT_POST_GRADE_NO", "AGENT_POST_GRADE_NO");	// 代理职级AGENT_POST_GRADE_NO	
			//查询当前裁决人
				/**
				 start
				 */
			String expInsideNo="";
			LinkedHashMap paramMapDiaoling = ObjectBindUtil.getRequestParamData(request);
			/*String expInsid="";
			if(ExpInsideNo!=null&&ExpInsideNo[o1]!=null){
				expInsid=ExpInsideNo[o1];
			}else{
				expInsid="0";
			}*/
			paramMapDiaoling.put("EXP_INSIDE_NO","0");
			paramMapDiaoling.put("CREATED_BY", admin.getPersonId());
			paramMapDiaoling.put("PERSON_ID", personNew);
			paramMapDiaoling.put("CPNY_ID", admin.getCpnyId());
			paramMapDiaoling.put("TRANS_CODE", code);
			//EXP_INSIDE_NO，CREATED_BY，PERSON_ID，CPNY_ID，TRANS_CODE
			List affirmorList = transferOrderDao.getAffirmorIdListByPersonal1(paramMapDiaoling);
			String Current_Affirm_id="";
			// 如果特殊设置不为空
			if (affirmorList.size() != 0) {
				for (int j = 0; j < affirmorList.size(); j++) {
					if (j == 0) {
						// 取当前决裁者
						paramMapDiaoling.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
						Current_Affirm_id=((Map)affirmorList.get(j)).get("AFFIRMOR_ID").toString();
					}
				}
				// 保存发令信息
				//transferOrderDao.saveHireAffirmList(paramMap, affirmorList);
			} else {
				// 取出被发令人的决裁者信息(部门设置)
				List affirmorListByDept = transferOrderDao
						.getAffirmorIdListByDept(paramMapDiaoling);
				if (affirmorListByDept.size() != 0) {
					for (int j = 0; j < affirmorListByDept.size(); j++) {
						// 取当前决裁者
						if (j == 0) {
							//paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
							paramMapDiaoling.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorListByDept.get(j)).get("AFFIRMOR_ID"));
							Map map99=(Map)affirmorListByDept.get(j);
							Current_Affirm_id=map99.get("AFFIRMOR_ID").toString();
						}
					}
					// 保存发令信息
					//transferOrderDao.saveHireAffirmList(paramMapDiaoling,affirmorListByDept);
				} else {
					// 自定义取决裁者
					Map flagMap = getAffiram(paramMapDiaoling, expInsideNo,request, admin);
					String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap.get("AFFIRM_FLAG").toString() : "";
					if ("2".equals(flag)) {
						return 2;
					}
					List newSaveAffirmorList = (List) paramMapDiaoling.get("newSaveAffirmorList");
					//transferOrderDao.saveHireAffirmList(paramMapDiaoling,newSaveAffirmorList);
				}
			}
			/**
			 	end
			 */
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("CURRENT_AFFIRM_ID", Current_Affirm_id);	// 当前裁决人CURRENT_AFFIRM_ID                                        
			paramMap.put("CREATED_BY", admin.getPersonId());//CREATED_BY     
			//jjy //신규로 넘어오는 경우는 EXP_INSIDE_NO 가 없음;
			try{
				//ExpInsideNos == null ? paramMap.put("EXP_INSIDE_NO", ExpInsideNos[i]) : paramMap.put("EXP_INSIDE_NO", ""); 
			    paramMap.put("EXP_INSIDE_NO", ExpInsideNos[i]);
			}catch(NullPointerException ne){
				paramMap.put("EXP_INSIDE_NO", "");
			}catch(ArrayIndexOutOfBoundsException ae){
				paramMap.put("EXP_INSIDE_NO", "");
			}
			logger.debug("==============================");
			logger.debug(paramMap);
			logger.debug("==============================");
			transferOrderDao.SaveHrExperienceInsideSave(paramMap);
			//int result = transferOrderSer.SaveHrExperienceInside(request);
			
			
		}
		result=1;
		}catch(Exception e){
			result=-1;
		}
		return result;
	}
	/**
	 * 提交调令保存到正式表（SaveHrExperienceInsideSave）
	 * 
	 * @param request
	 * @return result
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int SaveHrExperienceInsideSave1(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String type=request.getParameter("diaolingType");
		int result=-1;
		try{
		int cloumeNum =Integer.parseInt(request.getParameter("cloumeNum").toString())-1;
		LinkedHashMap paramMap1 = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		paramMap1.put("specialParam", admin.getSpecialParam());
		paramMap1.put("userNo", admin.getUserNo());
		paramMap1.put("DEPTNO", request.getParameter("dep"));
		paramMap1.put("ADMIN_ID", admin.getAdminID());
		String code=request.getParameter("OrderType");
		if(request.getParameter("code")!=null){
			code=request.getParameter("code");
		}
		paramMap1.put("CODE",  code);
		paramMap1.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap1.put("LAN", admin.getLanguage());
		String[] personid=request.getParameterValues("personid");
		String[] ExpInsideNo=request.getParameterValues("EXP_INSIDE_NO");
		
		
		List listTitle=this.transferOrderDao.getTranferOrderTitile(paramMap1);
		//Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map paramMap=new LinkedHashMap<String, Object>();
		String reasonType="";
		
		for(int i=0;i<listTitle.size();i++){
			
			Map map=(Map)listTitle.get(i);
			if(map.get("DISTINCT_FIELD").equals("TRANSFER_ORDER_REASON")){
				reasonType=map.get("OUTPUT_TYPE").toString();
			}
		}
		String sql="";
		paramMap.put("TRANS_NO", code);
		paramMap.put("CREATED_BY", admin.getPersonId());//CREATED_BY     
		String [] cloumeNumValue=request.getParameterValues("cloumeNumValue");
		String chexkBoxValue=request.getParameter("checkBoxValue");
		int checkBoxValueNum=chexkBoxValue.lastIndexOf("-");
		String [] chexkBoxValues=chexkBoxValue.substring(0,checkBoxValueNum).split("-");
		for(int i=0;i<chexkBoxValues.length;i++){
			//logger.debug("jjy:::checkBoxValues["+i+"]:::"+chexkBoxValues[i]);
						//增加o1的值 
			String [] colValue=chexkBoxValues[i].split(",");
			String personNew=colValue[0];
			int colNew=Integer.parseInt(colValue[1]);
			paramMap.put("PERSONID", personNew);
			
		
			/*for(int j=0;j<listTitle.size();j++){
				Map map=(Map)listTitle.get(j);
				sql+=map.get("DISTINCT_FIELD")+","+map.get("CONTENT")+"-";
				String colValue="";
				if((request.getParameter(map.get("DISTINCT_FIELD")+"_"+i))!=null){
					colValue=request.getParameter(map.get("DISTINCT_FIELD")+"_"+i);
				}else{
					colValue="";
				}
				
				
			paramMap.put(map.get("DISTINCT_FIELD"), colValue);
			
			}*/
			/*String [] expInsizeSave=
			{         "EXP_INSI _NO","TRANS_NO","TRANS_CODE","PERSON_ID","POST_GRADE_NO",
					  "POSITION_NO","START_DATE", "END_DATE","REMARK","CREATE_DATE",
					  "CREATED_BY","UPDATE_DATE", "UPDATED_BY", "ORDERNO","ACTIVITY",
					  "STATUS_CODE","POST_NO","DEPTNO","DUTY_NO","WORK_AREA",
					  "OLD_POST_GRADE_NO","PAY_STEP","AGENT_POST_GRADE_NO" ,
					  "CURRENT_AFFIRM_ID","OLD_POSITION_NO","OLD_POST_NO",
					  "OLD_DEPTNO","OLD_DUTY_NO","GRADE_LEVEL","PARTTIME_DEPT"
					  ,"DEPT_POST_GRADE_NO","DEPT_POST_NO","PARTTIME_DUTY",
					  "DEPT_POSITION_NO","DETAIL_HR_DIFF"      ,
					  "SAL_CALCULATE_DATE","TRANSFER_ORDER_REASON","TRANS_NUM" 
			};*/
			
				if(reasonType.equals("2")){
				
				LinkedHashMap paramMap2 = ObjectBindUtil.getRequestParamData(request,"seach_");
				paramMap2.put("NO", request.getParameter("TRANSFER_ORDER_REASON_"+colNew));
				paramMap2.put("LAN", admin.getLanguage());
				List reason=this.transferOrderDao.getSycodeByCodeId(paramMap2);
				String reason1="";
				if(reason.size()==0){
					reason1="";
				}else{
					reason1=((Map)reason.get(0)).get("SCONTENT").toString();
				}
				//调令事由
				if(request.getParameter("TRANSFER_ORDER_REASON_"+colNew)!=null){
					paramMap.put("TRANSFER_ORDER_REASON",reason1 );
				}	
				//if(request.getParameter("TRANS_CODE_"+i)!=null){
					paramMap.put("TRANS_CODE",request.getParameter("TRANSFER_ORDER_REASON_"+colNew) );
				//}
			}else{
				if(request.getParameter("TRANS_CODE_"+colNew)!=null){
					paramMap.put("TRANS_CODE",request.getParameter("TRANS_CODE"+colNew) );
				}else{
					paramMap.put("TRANS_CODE",code);
				}
				//调令事由
				if(request.getParameter("TRANSFER_ORDER_REASON_"+colNew)!=null){
					paramMap.put("TRANSFER_ORDER_REASON",request.getParameter("TRANSFER_ORDER_REASON_"+colNew) );
				}	
			}
			/*	//调令是由
				if(request.getParameter("TRANSFER_ORDER_REASON_"+i)!=null){
				paramMap.put("TRANSFER_ORDER_REASON",request.getParameter("TRANSFER_ORDER_REASON_"+i) );
				}	
			*/
			
			
			//调令类
			if(request.getParameter("TRANS_CODE_"+colNew)!=null){
				paramMap.put("TRANS_CODE",request.getParameter("TRANS_CODE"+colNew) );
			}
			//调令日期
			if(request.getParameter("TRANS_ORDER_DATE_"+colNew)!=null){
				paramMap.put("START_DATE",request.getParameter("TRANS_ORDER_DATE_"+colNew) );
			}
			//调令结束日
			if(request.getParameter("TRANS_ORDER_ENDDATE_"+colNew)!=null){
				paramMap.put("END_DATE",request.getParameter("TRANS_ORDER_ENDDATE_"+colNew) );
			}
			
			//部门
			if(request.getParameter("DEPTNO_"+colNew)!=null){
				paramMap.put("DEPTNO",request.getParameter("DEPTNO_"+colNew) );
			}
			//职等
			if(request.getParameter("GRADE_LEVEL_"+colNew)!=null){
				paramMap.put("GRADE_LEVEL",request.getParameter("GRADE_LEVEL_"+colNew) );
			}
			//职责
			if(request.getParameter("DUTY_NO_"+colNew)!=null){
				paramMap.put("DUTY_NO",request.getParameter("DUTY_NO_"+colNew) );
			}
			//职级
			if(request.getParameter("POST_GRADE_NO_"+colNew)!=null){
				paramMap.put("POST_GRADE_NO",request.getParameter("POST_GRADE_NO_"+colNew) );
			}
			//职级名称
			if(request.getParameter("POST_NO_"+colNew)!=null){
				paramMap.put("POST_NO",request.getParameter("POST_NO_"+colNew) );
			}
			//职位
			if(request.getParameter("POSITION_NO_"+colNew)!=null){
							
				paramMap.put("POSITION_NO",request.getParameter("POSITION_NO_"+colNew) );
			}
			//工作地
			if(request.getParameter("WORK_AREA_NAME_"+colNew)!=null){
				paramMap.put("WORK_AREA",request.getParameter("WORK_AREA_NAME_"+colNew) );
			}
			//兼职/代职 部门
			if(request.getParameter("PARTTIME_DEPT_"+colNew)!=null){
				paramMap.put("PARTTIME_DEPT",request.getParameter("PARTTIME_DEPT_"+colNew) );
			}
			//兼职/代职 职级
			if(request.getParameter("DEPT_POST_GRADE_NO_"+colNew)!=null){
				paramMap.put("DEPT_POST_GRADE_NO",request.getParameter("DEPT_POST_GRADE_NO_"+colNew) );
			}
			//兼职/代职 职级名称
			if(request.getParameter("DEPT_POST_NO_"+colNew)!=null){
				paramMap.put("DEPT_POST_NO",request.getParameter("DEPT_POST_NO_"+colNew) );
			}
			//兼职职责
			if(request.getParameter("PARTTIME_DUTY_"+colNew)!=null){
				paramMap.put("PARTTIME_DUTY",request.getParameter("PARTTIME_DUTY_"+colNew) );
			}
			//兼职/代职 职位
			if(request.getParameter("DEPT_POSITION_NO_"+colNew)!=null){
				paramMap.put("DEPT_POSITION_NO",request.getParameter("DEPT_POSITION_NO_"+colNew) );
			}
			//详细人力区分DETAIL_HR_DIFF
			if(request.getParameter("DETAIL_HR_DIFF_"+colNew)!=null){
				paramMap.put("DETAIL_HR_DIFF",request.getParameter("DETAIL_HR_DIFF_"+colNew) );
			}	
			//工资结算日SAL_CALCULATE_DATE
			if(request.getParameter("SAL_CALCULATE_DATE_"+colNew)!=null){
				paramMap.put("SAL_CALCULATE_DATE",request.getParameter("SAL_CALCULATE_DATE_"+colNew) );
			}	
			//备注REMARK
			if(request.getParameter("REMARK_"+colNew)!=null){
				paramMap.put("REMARK",request.getParameter("REMARK_"+colNew) );
			}
			if(request.getParameter("CUR_POST_GRADE_NO_"+colNew)!=null){
				paramMap.put("CUR_POST_GRADE_NO",request.getParameter("CUR_POST_GRADE_NO_"+colNew) );
			}
			if(request.getParameter("CUR_POST_NO_"+colNew)!=null){
				paramMap.put("CUR_POST_NO",request.getParameter("CUR_POST_NO_"+colNew) );
			}
			if(request.getParameter("CUR_POSITION_NO_"+colNew)!=null){
				paramMap.put("CUR_POSITION_NO",request.getParameter("CUR_POSITION_NO_"+colNew) );
			}
			if(request.getParameter("REPLACE_DEPT_"+colNew)!=null){
				paramMap.put("REPLACE_DEPT",request.getParameter("REPLACE_DEPT_"+colNew) );
			}
			if(request.getParameter("REPLACE_POST_GRADE_NO_"+colNew)!=null){
				paramMap.put("REPLACE_POST_GRADE_NO",request.getParameter("REPLACE_POST_GRADE_NO"+colNew) );
			}
			if(request.getParameter("REPLACE_POST_NO_"+colNew)!=null){
				paramMap.put("REPLACE_POST_NO",request.getParameter("REPLACE_POST_NO_"+colNew) );
			}
			if(request.getParameter("REPLACE_DUTY_"+colNew)!=null){
				paramMap.put("REPLACE_DUTY",request.getParameter("REPLACE_DUTY_"+colNew) );
			}
			if(request.getParameter("REPLACE_POSITION_NO_"+colNew)!=null){
				paramMap.put("REPLACE_POSITION_NO",request.getParameter("REPLACE_POSITION_NO_"+colNew) );
			}
			if(request.getParameter("POSITIVE_DATES_"+colNew)!=null){
				paramMap.put("POSITIVE_DATES",request.getParameter("POSITIVE_DATES_"+colNew) );
			}
			if(request.getParameter("POSITION_PAUSE_TYPE_"+colNew)!=null){
				paramMap.put("POSITION_PAUSE_TYPE",request.getParameter("POSITION_PAUSE_TYPE_"+colNew) );
			}
			if(request.getParameter("RESIGN_TYPE_"+colNew)!=null){
				paramMap.put("RESIGN_TYPE",request.getParameter("RESIGN_TYPE_"+colNew) );
			}
			if(request.getParameter("RESIGN_REASON_"+colNew)!=null){
				paramMap.put("RESIGN_REASON",request.getParameter("RESIGN_REASON_"+colNew) );
			}
			if(request.getParameter("TEMP_CPNY_"+colNew)!=null){
				paramMap.put("TEMP_CPNY",request.getParameter("TEMP_CPNY_"+colNew) );
			}
			if(request.getParameter("TEMP_DATE_"+colNew)!=null){
				paramMap.put("TEMP_DATE",request.getParameter("TEMP_DATE_"+colNew) );
			}
			if(request.getParameter("CUR_DEPTNO_"+colNew)!=null){
				paramMap.put("CUR_DEPTNO",request.getParameter("CUR_DEPTNO_"+colNew) );
			}
			if(request.getParameter("CUR_GRADE_LEVEL_"+colNew)!=null){
				paramMap.put("CUR_GRADE_LEVEL",request.getParameter("CUR_GRADE_LEVEL_"+colNew) );
			}
			if(request.getParameter("CUR_DUTY_NO_"+colNew)!=null){
				paramMap.put("CUR_DUTY_NO",request.getParameter("CUR_DUTY_NO_"+colNew) );
			}
			if(request.getParameter("WORK_AREA_NAME_"+colNew)!=null){
				paramMap.put("WORK_AREA_NAME",request.getParameter("WORK_AREA_NAME_"+colNew) );
			}
			if(request.getParameter("PARTTIME_POSITION_NO_"+colNew)!=null){
				paramMap.put("PARTTIME_POSITION_NO",request.getParameter("PARTTIME_POSITION_NO_"+colNew) );
			}
			if(request.getParameter("PARTTIME_POST_GRADE_NO_"+colNew)!=null){
				paramMap.put("PARTTIME_POST_GRADE_NO",request.getParameter("PARTTIME_POST_GRADE_NO_"+colNew) );
			}
			if(request.getParameter("PARTTIME_POST_NO_"+colNew)!=null){
				paramMap.put("PARTTIME_POST_NO",request.getParameter("PARTTIME_POST_NO_"+colNew) );
			}
			
			//调令编号
			if(request.getParameter("TRANSFER_ORDER_NO")!=null){
				paramMap.put("TRANS_NUM",request.getParameter("TRANSFER_ORDER_NO") );
			}
			//调令类型
			paramMap.put("TRANS_ORDER_TYPE", code);
			////工号+姓名
			paramMap.put("PERSON_ID",  personNew);
			if(code.equals("123315")||code.equals("123353")||code.equals("123358")||code.equals("123360")){
				if(code.equals("123353")){
					paramMap.put("CODE",  "123352");
				}else if(code.equals("123315")){
					//兼职
					paramMap.put("CODE",  "123314");
				}else if(code.equals("123358")){
					//借调
					paramMap.put("CODE",  "123357");
				}else if(code.equals("123360")){
					//待处理
					paramMap.put("CODE",  "123359");
				}
				
				String expNo=transferOrderDao.getExpInsideNo1(paramMap);
				paramMap.put("RELATION_EXP_INSIDE_NO", expNo);
			}
			// 取出人员的详细信息
			LinkedHashMap paramMap2 = ObjectBindUtil.getRequestParamData(request,"seach_");
			paramMap2.put("PERSONID", personNew);
			List paHrVList=transferOrderDao.getPaHrVList(paramMap2);
			
			/*#OLD_POST_GRADE_NO#:VARCHAR)                  ,       <!-- 前职务等级-->                                      
		  	#OLD_POSITION_NO#:VARCHAR)                    ,       <!-- 前职位-->                                      
		  	#OLD_POST_NO#:VARCHAR)                        ,       <!-- 前职务代码-->                                      
		  	#OLD_DEPTNO#:VARCHAR)                         ,       <!-- 前部门序号-->                                      
		  	#OLD_DUTY_NO#:VARCHAR)                        ,       <!-- 前职责-->   */
			Map mapPa=(Map)paHrVList.get(0);
			paramMap.put("OLD_GRADE_LEVEL",mapPa.get("GRADE_LEVEL"));//前职等
			paramMap.put("OLD_WORK_AREA",mapPa.get("WORK_AREA"));//前工作地
			paramMap.put("OLD_EMP_TYPE_CODE",mapPa.get("DETAIL_HR_DIFF"));//前详细人力区分
			
			paramMap.put("OLD_POST_GRADE_NO",mapPa.get("POST_GRADE_NO"));
			paramMap.put("OLD_POSITION_NO",mapPa.get("POSITION_NO"));
			paramMap.put("OLD_POST_NO",mapPa.get("POST_NO"));
			paramMap.put("OLD_DEPTNO",mapPa.get("DEPTNO1"));
			paramMap.put("OLD_DUTY_NO",mapPa.get("DUTY_NO"));
			paramMap.put("STATUS_CODE", mapPa.get("STATUS_CODE"));//员工状态
			paramMap.put("AGENT_POST_GRADE_NO", "AGENT_POST_GRADE_NO");	// 代理职级AGENT_POST_GRADE_NO	
			//查询当前裁决人
				/**
				 start
				 */
			String expInsideNo="";
			LinkedHashMap paramMapDiaoling = ObjectBindUtil.getRequestParamData(request);
			/*String expInsid="";
			if(ExpInsideNo!=null){
				expInsid=ExpInsideNo[i];
			}else{
				expInsid="0";
			}*/
			//String expInsideNo2 = transferOrderDao.getNextExpInside();
			String expInsideNo1 = transferOrderDao.getNextExpInside();
			paramMapDiaoling.put("EXP_INSIDE_NO",expInsideNo1);
			paramMapDiaoling.put("CREATED_BY", admin.getPersonId());
			paramMapDiaoling.put("PERSON_ID", personNew);
			paramMapDiaoling.put("CPNY_ID", admin.getCpnyId());
			paramMapDiaoling.put("TRANS_CODE", code);
			paramMapDiaoling.put("TYPE", "yidong");
			//EXP_INSIDE_NO，CREATED_BY，PERSON_ID，CPNY_ID，TRANS_CODE
			List affirmorList = transferOrderDao.getAffirmorIdListByPersonal1(paramMapDiaoling);
			String Current_Affirm_id="";
			// 如果特殊设置不为空
			if (affirmorList.size() != 0) {
				for (int j = 0; j < affirmorList.size(); j++) {
					if (j == 0) {
						// 取当前决裁者
						paramMapDiaoling.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
						Current_Affirm_id=((Map)affirmorList.get(j)).get("AFFIRMOR_ID").toString();
					}
				}
				// 保存发令信息
				transferOrderDao.saveDiaoDongAffirmList(paramMap, affirmorList);
			} else {
				// 取出被发令人的决裁者信息(部门设置)
				List affirmorListByDept = transferOrderDao
						.getAffirmorIdListByDept(paramMapDiaoling);
				if (affirmorListByDept.size() != 0) {
					for (int j = 0; j < affirmorListByDept.size(); j++) {
						// 取当前决裁者
						if (j == 0) {
							//paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
							paramMapDiaoling.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorListByDept.get(j)).get("AFFIRMOR_ID"));
							Map map99=(Map)affirmorListByDept.get(j);
							Current_Affirm_id=map99.get("AFFIRMOR_ID").toString();
						}
					}
					// 保存发令信息
					transferOrderDao.saveDiaoDongAffirmList(paramMapDiaoling,affirmorListByDept);
				} else {
					// 自定义取决裁者
					Map flagMap = getAffiram(paramMapDiaoling, expInsideNo1,request, admin);
					String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap.get("AFFIRM_FLAG").toString() : "";
					if ("2".equals(flag)) {
						return 2;
					}
					List newSaveAffirmorList = (List) paramMapDiaoling.get("newSaveAffirmorList");
					Map map99=(Map)newSaveAffirmorList.get(0);
					Current_Affirm_id=map99.get("AFFIRMOR_ID").toString();
					transferOrderDao.saveDiaoDongAffirmList(paramMapDiaoling,newSaveAffirmorList);
				}
			}
			/**
			 	end
			 */
			//	#EXP_INSIDE_NO:NUMERIC#,
			paramMap.put("ACTIVITY","0");
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("EXP_INSIDE_NO", expInsideNo1);
			paramMap.put("CURRENT_AFFIRM_ID", Current_Affirm_id);	// 当前裁决人CURRENT_AFFIRM_ID                                        
			//logger.debug("jjy:::paramMap_first:::"+paramMap);
			transferOrderDao.SaveHrExperienceInsideSave1(paramMap);
			transferOrderDao.deleteHrExperienceInsideSaveByPersonId(paramMap);
			result=1;
			//int result = transferOrderSer.SaveHrExperienceInside(request);
			
		}
		}catch(Exception e){
			result=-1;
		}
		
		return result;
	}

	
	/**
	 * 根据传入的PARENT_CODE_NO 查询出对应的CODE和NAME
	 */
	@Override
	public List getCodeList(String parent_code_no, HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", "zh");
		paramMap.put("PARENT_CODE_NO", parent_code_no);
		retrunList = transferOrderDao.getCodeList(paramMap);
		return retrunList;
		
	}
	
	/**
	 * 根据传入的PARENT_CODE_NO 查询出对应的CODE和NAME
	 */
	@Override
	public List getCodeListByParam(LinkedHashMap paramMap) {
		List retrunList = new ArrayList();
		retrunList = transferOrderDao.getCodeList(paramMap);
		return retrunList;		
	}
	
	/**
	 *  通过职等 关联职级
	 */
	@Override
	public List getZhiDengAndZhiJi(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("GRADE_LEVEL", request.getParameter("GRADE_LEVEL"));//职等CODE
		
		
		retrunList = transferOrderDao.getZhiDengAndZhiJi(paramMap) ;
		
		return retrunList ;
	}

	/**
	 * 通过职级关联职责
	 */
	@Override
	public List getZhiJiAndZhiZe(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("POST_GRADE_NO", request.getParameter("POST_GRADE_NO"));//职级CODE
		
		
		retrunList = transferOrderDao.getZhiJiAndZhiZe(paramMap) ;
		
		return retrunList ;
	}

	/**
	 * 通过职级关联职级名称
	 */
	@Override
	public List getZhiJiAndZhiJiMing(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("POST_GRADE_NO", request.getParameter("POST_GRADE_NO"));//职级CODE
		
		
		retrunList = transferOrderDao.getZhiJiAndZhiJiMing(paramMap) ;
		
		return retrunList ;
	}


	/**
	 * 根据输入的员工编号查询员工
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getEmployeeByEmpId(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("EMPID", request.getParameter("EMPID")) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return this.transferOrderDao.getEmployeeByEmpId(paramMap);
	}

	@Override
	public int getTransferOrderEmpListCnt(HttpServletRequest request)throws Exception {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("deptNo",admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		retrunInt = transferOrderDao.getTransferOrderEmpListCnt(paramMap) ;
		
		return retrunInt ;
	}
	public List getHrExperienceInsideSaveByTransCode(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("DEPTNO", request.getParameter("dep"));
		paramMap.put("ADMIN_ID", admin.getAdminID());
		String code="";
		if(request.getParameter("code")!=null){
			code=request.getParameter("code");
		}else{
			code=request.getParameter("OrderType");
		}
		paramMap.put("createdBy", admin.getAdminID());
		paramMap.put("CODE",  code);
		paramMap.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("LAN", admin.getLanguage());
		paramMap.put("DEPTNO1",admin.getDeptNo());//当前登陆人部门编号，权用限
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("interCpnyID", admin.getCpnyId());
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		retrunList = transferOrderDao.getHrExperienceInsideSaveByTransCode(paramMap);
		return retrunList;
	}
	
	public int deleteHrExperienceInsideSaveByPersonIds(HttpServletRequest request) {
		int result=-1;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("DEPTNO", request.getParameter("dep"));
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("createdBy", admin.getAdminID());
		paramMap.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("LAN", admin.getLanguage());
		String[] personid=request.getParameterValues("personid");
		String personId="";
		
		for(int i=0;i<personid.length;i++){
			personId+=personid[i]+",";
		}
		int colNum= Integer.parseInt(request.getParameter("cloumeNum"))-1;
		String code=request.getParameter("OrderType");
		int num=personId.lastIndexOf(",");
		String personId1=personId.substring(0,num);
		paramMap.put("PERSONID", admin.getPersonId());
		paramMap.put("TRANS_NO", code);
		
			try{
			transferOrderDao.deleteHrExperienceInsideSaveByPersonIds(paramMap);
			result=1;
			}catch(Exception e){
			result=-1;
			}
		
		
		return result;
	}

	@Override
	public List viewTranferOrderinsideList(HttpServletRequest request) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap1 = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap1.put("CODE", paramMap1.get("OrderType"));
		paramMap1.put("LAN",admin.getLanguage());
		paramMap1.put("interLanguage",admin.getLanguage());
		paramMap1.put("CPNYID", admin.getCpnyId());
		paramMap1.put("TRANS_SEARCH_FLAG", "1");
		paramMap1.put("ADMIN_ID", admin.getAdminID());
		
		//jjy search condition(approval status) change
		String aCode = "";
		if("123490".equals(paramMap1.get("ACTIVITY"))){
			paramMap1.remove("ACTIVITY");
			aCode = "0";
		}else if("123491".equals(paramMap1.get("ACTIVITY"))){
			paramMap1.remove("ACTIVITY");
			aCode = "1";
		}else if("123492".equals(paramMap1.get("ACTIVITY"))){
			paramMap1.remove("ACTIVITY");
			aCode = "2";
		}else if("123493".equals(paramMap1.get("ACTIVITY"))){
			paramMap1.remove("ACTIVITY");
			aCode = "3";
		}
		paramMap1.put("ACTIVITY", aCode);
		
		//paramMap1.put("ACTIVITY", paramMap1.get("ACTIVITY"));
		List listTitle=this.transferOrderDao.getTranferOrderTitileInside1(paramMap1);
		String sql="";
		for(int i=0;i<listTitle.size();i++){
			sql=sql+","+((Map) listTitle.get(i)).get("INSIDE_TRANS").toString();	
		}
		sql=sql.substring( 1, sql.length());
		paramMap1.put("sql",sql+",");
		List insideList = new ArrayList();
		
		if (UiUtil.getPageNum(request) > 0) {
			insideList =transferOrderDao.getTranferOrderinsideList(paramMap1,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			insideList = transferOrderDao.getTranferOrderinsideList(paramMap1);
		}
		List insideList1=new ArrayList<Object>();
		 for (int i = 0; i < insideList.size(); i++) {
				Map m= (LinkedHashMap) insideList.get(i);
				m.put("affirmerList", this.transactionAffirmDao.getAffirmerListByExpInsideNo(m));
				insideList1.add(m);
		}
		 return insideList1;
	}
	@Override
	public List viewTranferOrderinsideListSerach(HttpServletRequest request) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap1 = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap1.put("CODE", request.getParameter("code"));
		paramMap1.put("LAN",admin.getLanguage());
		paramMap1.put("CPNYID", admin.getCpnyId());
		paramMap1.put("TRANS_SEARCH_FLAG", "1");
		//paramMap1.put("ADMIN_ID", admin.getAdminID());
		paramMap1.put("PERSONID", request.getParameter("personid"));
		List listTitle=this.transferOrderDao.getTranferOrderTitileInside(paramMap1);
		String sql="";
		for(int i=0;i<listTitle.size();i++){
			sql=sql+","+((Map) listTitle.get(i)).get("INSIDE_TRANS").toString();	
		}
		sql=sql.substring( 1, sql.length());
		paramMap1.put("sql",sql+",");
		List insideList = new ArrayList();
		
		if (UiUtil.getPageNum(request) > 0) {
			insideList =transferOrderDao.getTranferOrderinsideList(paramMap1,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			insideList = transferOrderDao.getTranferOrderinsideListSerach(paramMap1);
		}
		List insideList1=new ArrayList<Object>();
		 for (int i = 0; i < insideList.size(); i++) {
				Map m= (LinkedHashMap) insideList.get(i);
				m.put("affirmerList", this.transactionAffirmDao.getAffirmerListByExpInsideNo(m));
				insideList1.add(m);
		}
		 return insideList1;
	}
	@Override
	public int getTranferOrderinsideListCnt(HttpServletRequest request,
			LinkedHashMap paramMap) {
		int retrunInt = 0;

		//jjy search condition(approval status) change
		String aCode = "";
		if("123490".equals(paramMap.get("ACTIVITY"))){
			paramMap.remove("ACTIVITY");
			aCode = "0";
		}else if("123491".equals(paramMap.get("ACTIVITY"))){
			paramMap.remove("ACTIVITY");
			aCode = "1";
		}else if("123492".equals(paramMap.get("ACTIVITY"))){
			paramMap.remove("ACTIVITY");
			aCode = "2";
		}else if("123493".equals(paramMap.get("ACTIVITY"))){
			paramMap.remove("ACTIVITY");
			aCode = "3";
		}
		paramMap.put("ACTIVITY", aCode);
		
		retrunInt = transferOrderDao.getTranferOrderinsideListCnt(paramMap);
		return retrunInt;
	}

	@Override
	public int cancelPluralityBatchInsideByNo(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("c1");
			for (int i = 0; i < paramData.length; i++) {
				Map map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
				map.put("EXP_INSIDE_NO", paramData[i]);
				// 决裁流程里未决裁且发令表里未生效
				int count = this.transferOrderDao.getPassHrAffirmCountByExpInsideNo(map);//查询出 裁决是未裁决的数量
				if (count > 0) {
					return 2;
				}
				list.add(map);
			}
			this.transferOrderDao.cancelHrPluralityByNo(list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int getHrExperienceInsideTrans_Num(HttpServletRequest request) throws NumberFormatException, SQLException {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String code="";
		if(request.getParameter("code")!=null){
			code=request.getParameter("code");
		}else{
			code=request.getParameter("OrderType");
		}
		paramMap.put("TRANSNO", code);
		return transferOrderDao.getHrExperienceInsideTransNum(paramMap);
	}

	@Override
	public Integer getRewardAndPunishmentInsideTransNum(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String orderType=request.getParameter("transferOrder_type");
		if("1".equals(orderType)){
			paramMap.put("TRANS_NO", "641");
			paramMap.put("TABLE_NAME", "HR_REWARD");
		}else{
			paramMap.put("TRANS_NO", "642");
			paramMap.put("TABLE_NAME", "HR_PUNISHMENT");
		}
		return this.transferOrderDao.getRewardAndPunishmentInsideTransNum(paramMap);
	}

	/**
	 * 根据选择的员工编号查询指定员工
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getSelectedEmpList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap=new LinkedHashMap();
		
		Integer selectedEmpSize=new Integer(request.getParameter("selectedEmpSize"));
		String[] personIdArray=new String[selectedEmpSize];
		for(int i = 0; i < selectedEmpSize; i++){
			personIdArray[i]=request.getParameter("PERSONID_"+i);
		}
		paramMap.put("PERSON_ID", personIdArray);
		
		Integer hasCurrentPersonIdSize=new Integer(request.getParameter("hasCurrentPersonIdSize"));
		String[] hasCurrentEmpArray=new String[hasCurrentPersonIdSize];
		for(int j = 0; j <hasCurrentPersonIdSize; j++){
			hasCurrentEmpArray[j]=request.getParameter("hasCurrentPersonId_"+j);
		}
		
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		List resultList=this.transferOrderDao.getEmployeeByEmpId(paramMap);
		//排除页面上已有的员工
		for(int x = 0; x < hasCurrentEmpArray.length; x++){
			String hasCurrentEmp = hasCurrentEmpArray[x];
			for(int y = 0; y < resultList.size(); y++){
				if(hasCurrentEmp.equals(((Map)resultList.get(y)).get("PERSON_ID"))){
					resultList.remove(y);
				}
			}
		}
		return resultList;
	}
	/**
	 * 根据职责查询对应职级
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPostGradeNoByDuty(HttpServletRequest request)throws Exception{
		LinkedHashMap paramMap=new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String dutyNoValue=request.getParameter("dutyNoValue");
		paramMap.put("DUTYNO", dutyNoValue);
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("LAN", admin.getLanguage());
		//根据职责获得对应的职级
		List list=this.transferOrderDao.getPostGradeNoByDuty(paramMap);
		/*String postgradeidStr="";
		//根据职级获得对应的职级post_grade_no
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			postgradeidStr+=map.get("POST_GRADE_NO")+",";
		}
		int postgradeidNum=postgradeidStr.lastIndexOf(",");
		paramMap.put("POSTGRADEID",postgradeidStr.substring(0,postgradeidNum) );
		List list1=this.transferOrderDao.getHrPostGradeByPostGradeNo(paramMap);
		String postGradeNo="";
		for(int i=0;i<list1.size();i++){
			Map map=(Map)list1.get(i);
			postGradeNo+=map.get("POST_GRADE_NO")+",";
		}
		int postGradeNoNum=postGradeNo.lastIndexOf(",");
		paramMap.put("POSTGRADENOS", postGradeNo.substring(0,postGradeNoNum));
		//根据职级获得职级名称
		List list2=this.transferOrderDao.getPostNoNameByPostsNo(paramMap);*/
		return list;
	}
	/**
	 * 根据职级获得职级名称
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPositionInfoByPostGradeNo(HttpServletRequest request)throws Exception{
		LinkedHashMap paramMap=new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("interCpnyID", admin.getCpnyId());
		paramMap.put("POSTGRADENO", request.getParameter("postGradeNo"));
		List list=this.postDao.getPositionInfoByPostGradeNo(paramMap);
		return list;
	}
	
	/**
	 * 根据职种获得职位
	 */
	@Override
	public List getZhiZhongAndZhiWei(HttpServletRequest request)
			throws Exception {
			
			LinkedHashMap paramMap=new LinkedHashMap();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("interLanguage", admin.getLanguage());
			paramMap.put("CPNYID", admin.getCpnyId());
			paramMap.put("obj", request.getParameter("obj"));//职种CODE 
			List list=this.transferOrderDao.getZhiZhongAndZhiWei(paramMap);
			return list;
	}
	/**
	 * 根据ID获得级联
	 */
	@Override
	public List viewSelectTag(HttpServletRequest request)
			throws Exception {
			LinkedHashMap paramMap=new LinkedHashMap();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("interLanguage", admin.getLanguage());
			paramMap.put("CPNYID", admin.getCpnyId());
			paramMap.put("obj", request.getParameter("obj"));//职种CODE 
			String selectName=request.getParameter("name");
			String selectName1=selectName.substring(0,selectName.lastIndexOf("_"))  ;
			String selectValue=request.getParameter("value");
			String table="";
			String where="";
			String content="";
			List list = new ArrayList();
			/*SELECT T.POST_NO,
			   GET_GLOBAL_NAME(T.POST_NO,#language:VARCHAR#) POST_NAME
		FROM HR_POST_GRADE  T 
		WHERE  T.ACTIVITY = 1
		AND T.CPNY_ID = #CPNY_ID:VARCHAR#
		AND T.POST_GRADE_NO =#POST_GRADE_NO:VARCHAR#*/
			if(selectName1.equals("POST_GRADE_NO")){
				content=" POST_NO CODE_NO,GET_GLOBAL_NAME(POST_NO,'"+admin.getLanguage()+"') CODE_NAME";
				table=" HR_POST_GRADE";
				where=" activity=1 and cpny_id='"+admin.getCpnyId()+"' and POST_GRADE_NO='"+selectValue+"'";
			}
			else if(selectName1.equals("GRADE_LEVEL")){
				content=" post_grade_no CODE_NO,get_global_name(post_grade_no,'"+admin.getLanguage()+"') CODE_NAME";
				table=" hr_post_grade";
				where=" activity=1 and cpny_id='"+admin.getCpnyId()+"' and grade_level='"+selectValue+"'";
			}else{
				content=" * ";
				table=" dual ";
				where=" 1=1 ";
				return list;
				
			}
			paramMap.put("CONTENT", content);
			paramMap.put("TABLE", table);
			paramMap.put("WHERE", where);
			list=this.transferOrderDao.viewSelectTag(paramMap);
			
			return list;
	}
	
	/* (non-Javadoc)
	 * @see com.ait.hrm.service.TransferOrderSer#deleteHrExperienceInsideSaveByPersonIdNExpInsideNo(javax.servlet.http.HttpServletRequest)
	 */
	public int deleteHrExperienceInsideSaveByPersonIdNExpInsideNo(HttpServletRequest request) throws Exception{
		int result=-1;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("DEPTNO", request.getParameter("dep"));
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("createdBy", admin.getAdminID());
		paramMap.put("CPNYID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap.put("LAN", admin.getLanguage());
		String delPersonId=(String)request.getParameter("del_personid");
		String delExpInsideNo = (String)request.getParameter("del_exp_inside_no");
		paramMap.put("DEL_PERSON_ID", delPersonId);
		paramMap.put("DEL_EXP_INSIDE_NO", delExpInsideNo);
		
		try{
		    transferOrderDao.deleteHrExperienceInsideSaveByPersonIdNExpInsideNo(paramMap);
		    result=1;
		}catch(Exception e){
			e.printStackTrace();
			result=-1;
		}
		return result;
	}

	/**
	 * 删除奖励或惩戒的一行数据
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-29 下午04:12:20 
	* @version V1.0
	 */
	@Override
	public int deleteCurrentRewardOrPunishment(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap=new LinkedHashMap();
		int result = 0;
		
		paramMap.put("EXP_INSIDE_NO", request.getParameter("EXP_INSIDE_NO"));
		
		if("REWARD".equals(request.getParameter("TRANSFER_ORDER_TYPE"))){
			result = this.transferOrderDao.deleteCurrentReward(paramMap);
		}else{
			result = this.transferOrderDao.deleteCurrentPunishment(paramMap);
		}
		return result;
	}
	
	/**
	 * 根据ID获得级联
	 */
	@Override
	public List viewEmpinfoList(HttpServletRequest request)throws Exception {
			LinkedHashMap paramMap=new LinkedHashMap();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List list = new ArrayList();
			paramMap.put("interLanguage", admin.getLanguage());
			paramMap.put("CPNYID", admin.getCpnyId());
			paramMap.put("obj", request.getParameter("obj"));//职种CODE 
			String personIds=request.getParameter("personids");
			String personId []=personIds.split("-");
			String pers="";
			for(int i=0;i<personId.length;i++){
				String perId []=personId[0].split(",");
				pers=pers+perId[0]+",";
			}
			
			paramMap.put("PERSONID", pers);
			list=this.transferOrderDao.getEmpInfoByPersonIdAndExinsideNo(paramMap);
			
			return list;
	}

	@Override
	//保存到临时表
	public int SaveHrExperienceInsideSave_send_1(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int result=-1;
		String t2=request.getParameter("checkBoxValue");
		t2.split("-");
		try{
		String t1 []=t2.split("-");
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("TRANS_NO", "123314");   
		transferOrderDao.deleteHrExperienceInsideSaveByPersonId(paramMap);
		for(int i=0;i<t1.length;i++){
			//1,3
			if(t1[i]==null||t1[i]==""){
				continue;
			}
			paramMap.put("START_DATE", request.getParameter("sdate_"+t1[i]));
			paramMap.put("END_DATE", request.getParameter("edate_"+t1[i]));
			paramMap.put("PERSON_ID", request.getParameter("personid_"+t1[i]));
			paramMap.put("REMARK", request.getParameter("saddress_"+t1[i]));
			paramMap.put("DEPTNO_", request.getParameter("DEPTNO_"+t1[i]));
			paramMap.put("POSITION_NO_", request.getParameter("POSITION_NO_"+t1[i]));
			paramMap.put("DUTY_NO_", request.getParameter("DUTY_NO_"+t1[i]));
			paramMap.put("POST_GRADE_NO_", request.getParameter("POST_GRADE_NO_"+t1[i]));
			paramMap.put("EMP_TP_", request.getParameter("EMP_TP_"+t1[i]));
			paramMap.put("WORK_AREA_", request.getParameter("WORK_AREA_"+t1[i]));
			paramMap.put("SENDADDRESS_NO", request.getParameter("saddress_"+t1[i]));
			paramMap.put("REMARK", request.getParameter("remark_"+t1[i]));
			paramMap.put("TRANS_NO", "123314");
			
		
			//查询当前裁决人
			/**
			 start
			 */
		String expInsideNo="";
		LinkedHashMap paramMapDiaoling = new LinkedHashMap<String, String>();
		/*String expInsid="";
		if(ExpInsideNo!=null){
			expInsid=ExpInsideNo[i];
		}else{
			expInsid="0";
		}*/
		//String expInsideNo2 = transferOrderDao.getNextExpInside();
		String expInsideNo1 = transferOrderDao.getNextExpInside();
		paramMapDiaoling.put("EXP_INSIDE_NO",expInsideNo1);
		paramMapDiaoling.put("CREATED_BY", admin.getPersonId());
		paramMapDiaoling.put("PERSON_ID", request.getParameter("personid_"+t1[i]));
		paramMapDiaoling.put("CPNY_ID", admin.getCpnyId());
		//paramMapDiaoling.put("TRANS_CODE", code);
		paramMapDiaoling.put("TYPE", "yidong");
		paramMapDiaoling.put("TRANS_CODE", "125231");
		//EXP_INSIDE_NO，CREATED_BY，PERSON_ID，CPNY_ID，TRANS_CODE
		List affirmorList = transferOrderDao.getAffirmorIdListByPersonal1(paramMapDiaoling);
		String Current_Affirm_id="";
		// 如果特殊设置不为空
		if (affirmorList.size() != 0) {
			for (int j = 0; j < affirmorList.size(); j++) {
				if (j == 0) {
					// 取当前决裁者
					paramMapDiaoling.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
					Current_Affirm_id=((Map)affirmorList.get(j)).get("AFFIRMOR_ID").toString();
				}
			}
			// 保存发令信息
			transferOrderDao.saveDiaoDongAffirmList(paramMap, affirmorList);
		} else {
			// 取出被发令人的决裁者信息(部门设置)
			List affirmorListByDept = transferOrderDao
					.getAffirmorIdListByDept(paramMapDiaoling);
			if (affirmorListByDept.size() != 0) {
				for (int j = 0; j < affirmorListByDept.size(); j++) {
					// 取当前决裁者
					if (j == 0) {
						//paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
						paramMapDiaoling.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorListByDept.get(j)).get("AFFIRMOR_ID"));
						Map map99=(Map)affirmorListByDept.get(j);
						Current_Affirm_id=map99.get("AFFIRMOR_ID").toString();
					}
				}
				// 保存发令信息
				transferOrderDao.saveDiaoDongAffirmList(paramMapDiaoling,affirmorListByDept);
			} else {
				// 自定义取决裁者
				Map flagMap = getAffiram(paramMapDiaoling, expInsideNo1,request, admin);
				String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap.get("AFFIRM_FLAG").toString() : "";
				if ("2".equals(flag)) {
					return 2;
				}
				List newSaveAffirmorList = (List) paramMapDiaoling.get("newSaveAffirmorList");
				Map map99=(Map)newSaveAffirmorList.get(0);
				Current_Affirm_id=map99.get("AFFIRMOR_ID").toString();
				transferOrderDao.saveDiaoDongAffirmList(paramMapDiaoling,newSaveAffirmorList);
			}
		}
		/**
		 	end
		 */
		//	#EXP_INSIDE_NO:NUMERIC#,
		paramMap.put("ACTIVITY","0");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EXP_INSIDE_NO", expInsideNo1);
		paramMap.put("CURRENT_AFFIRM_ID", Current_Affirm_id);
		                            
		//logger.debug("jjy:::paramMap_first:::"+paramMap);
		
		transferOrderDao.SaveHrExperienceInsideSave_send_1(paramMap);
			
			result=1;
		}
		}catch(Exception e){
			e.printStackTrace();
			result=-1;
		}
		
	
		return result;
}
		
		
		
	

	@Override
	public int SaveHrExperienceInsideSave_send_2(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int result=-1;
		String t2=request.getParameter("checkBoxValue");
		t2.split("-");
		try{
		String t1 []=t2.split("-");
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("TRANS_NO", "123314");   
		
		for(int i=0;i<t1.length;i++){
			//1,3
			if(t1[i]==null||t1[i]==""){
				continue;
			}
			paramMap.put("START_DATE", request.getParameter("sdate_"+t1[i]));
			paramMap.put("END_DATE", request.getParameter("edate_"+t1[i]));
			paramMap.put("PERSON_ID", request.getParameter("personid_"+t1[i]));
			paramMap.put("REMARK", request.getParameter("saddress_"+t1[i]));
			paramMap.put("DEPTNO_", request.getParameter("DEPTNO_"+t1[i]));
			paramMap.put("POSITION_NO_", request.getParameter("POSITION_NO_"+t1[i]));
			paramMap.put("DUTY_NO_", request.getParameter("DUTY_NO_"+t1[i]));
			paramMap.put("POST_GRADE_NO_", request.getParameter("POST_GRADE_NO_"+t1[i]));
			paramMap.put("EMP_TP_", request.getParameter("EMP_TP_"+t1[i]));
			paramMap.put("WORK_AREA_", request.getParameter("WORK_AREA_"+t1[i]));
			paramMap.put("SENDADDRESS_NO", request.getParameter("saddress_"+t1[i]));
			paramMap.put("REMARK", request.getParameter("remark_"+t1[i]));
			paramMap.put("TRANS_NO", "123314");
			
		
			//查询当前裁决人
			/**
			 start
			 */
		String expInsideNo="";
		LinkedHashMap paramMapDiaoling = new LinkedHashMap<String, String>();
		/*String expInsid="";
		if(ExpInsideNo!=null){
			expInsid=ExpInsideNo[i];
		}else{
			expInsid="0";
		}*/
		//String expInsideNo2 = transferOrderDao.getNextExpInside();
		String expInsideNo1 = transferOrderDao.getNextExpInside();
		paramMapDiaoling.put("EXP_INSIDE_NO",expInsideNo1);
		paramMapDiaoling.put("CREATED_BY", admin.getPersonId());
		paramMapDiaoling.put("PERSON_ID", request.getParameter("personid_"+t1[i]));
		paramMapDiaoling.put("CPNY_ID", admin.getCpnyId());
		//paramMapDiaoling.put("TRANS_CODE", code);
		paramMapDiaoling.put("TYPE", "yidong");
		paramMapDiaoling.put("TRANS_CODE", "125231");
		//EXP_INSIDE_NO，CREATED_BY，PERSON_ID，CPNY_ID，TRANS_CODE
		List affirmorList = transferOrderDao.getAffirmorIdListByPersonal1(paramMapDiaoling);
		String Current_Affirm_id="";
		// 如果特殊设置不为空
		if (affirmorList.size() != 0) {
			for (int j = 0; j < affirmorList.size(); j++) {
				if (j == 0) {
					// 取当前决裁者
					paramMapDiaoling.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
					Current_Affirm_id=((Map)affirmorList.get(j)).get("AFFIRMOR_ID").toString();
				}
			}
			// 保存发令信息
			transferOrderDao.saveDiaoDongAffirmList(paramMap, affirmorList);
		} else {
			// 取出被发令人的决裁者信息(部门设置)
			List affirmorListByDept = transferOrderDao
					.getAffirmorIdListByDept(paramMapDiaoling);
			if (affirmorListByDept.size() != 0) {
				for (int j = 0; j < affirmorListByDept.size(); j++) {
					// 取当前决裁者
					if (j == 0) {
						//paramMap.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorList.get(j)).get("AFFIRMOR_ID"));
						paramMapDiaoling.put("CURRENT_AFFIRM_ID",((LinkedHashMap) affirmorListByDept.get(j)).get("AFFIRMOR_ID"));
						Map map99=(Map)affirmorListByDept.get(j);
						Current_Affirm_id=map99.get("AFFIRMOR_ID").toString();
					}
				}
				// 保存发令信息
				transferOrderDao.saveDiaoDongAffirmList(paramMapDiaoling,affirmorListByDept);
			} else {
				// 自定义取决裁者
				Map flagMap = getAffiram(paramMapDiaoling, expInsideNo1,request, admin);
				String flag = flagMap.get("AFFIRM_FLAG") != null ? flagMap.get("AFFIRM_FLAG").toString() : "";
				if ("2".equals(flag)) {
					return 2;
				}
				List newSaveAffirmorList = (List) paramMapDiaoling.get("newSaveAffirmorList");
				Map map99=(Map)newSaveAffirmorList.get(0);
				Current_Affirm_id=map99.get("AFFIRMOR_ID").toString();
				transferOrderDao.saveDiaoDongAffirmList(paramMapDiaoling,newSaveAffirmorList);
			}
		}
		/**
		 	end
		 */
		//	#EXP_INSIDE_NO:NUMERIC#,
		paramMap.put("ACTIVITY","0");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EXP_INSIDE_NO", expInsideNo1);
		paramMap.put("CURRENT_AFFIRM_ID", Current_Affirm_id);
		                            
		//logger.debug("jjy:::paramMap_first:::"+paramMap);
		String expids=request.getParameter("expids");
		int expidsNum=expids.lastIndexOf(",");	
		paramMap.put("EXPIDS", expids.substring(0,expidsNum));
		transferOrderDao.deleteHrExperienceInsideSaveByPersonId(paramMap);
		transferOrderDao.SaveHrExperienceInsideSave_send_2(paramMap);
			
			result=1;
		}
		}catch(Exception e){
			e.printStackTrace();
			result=-1;
		}	
		return result;		
	}
	
	/**
	 * 临时职人员发令数据导入结果查询
	 * @param request
	 * @return
	 * @Create date: 2014.10.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getTransferOrderImpResultList(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		if (UiUtil.getPageNum(request) > 0){
			retrunList =  transferOrderDao.getTransferOrderImpResultList(paramMap , 
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
					);
		}else{
			retrunList =  transferOrderDao.getTransferOrderImpResultList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 临时职人员发令导入结果导出
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getTransferOrderImpResultListExcel(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		retrunList =  transferOrderDao.getTransferOrderImpResultList(paramMap);
		return retrunList;
	}
	
	/**
	 * 临时职人员发令数据导入结果查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getTransferOrderImpResultListCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		return transferOrderDao.getTransferOrderImpResultListCnt(paramMap);
	}
	
	/**
	 * 临时职人员发令导入结果查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getTransferOrderImpResultListErrCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		return transferOrderDao.getTransferOrderImpResultListErrCnt(paramMap);
	}
	
	/**
	 * 临时职人员发令数据验证并导入正式表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String importTransferOrderImpRAWFromExcel(HttpServletRequest request, Map paramMap) {
		String result = "";
		result = this.transferOrderDao.importTransferOrderImpRAWFromExcel(paramMap);
		return result;
	}
	/**
	 * 人员类型变更发令数据验证并导入正式表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String importEmpTypeTransferOrderImpRAWFromExcel(HttpServletRequest request, Map paramMap) {
		String result = "";
		result = this.transferOrderDao.importEmpTypeTransferOrderImpRAWFromExcel(paramMap);
		return result;
	}
	
	/*撤消临时职发令*/
	@Override
	public Map deleteTransferOrderUpgrade(Map paramMap) {	
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int activeFlag = transferOrderDao.checkDeleteTransferOrderUpgrade(paramMap);
			if (activeFlag==0){
				transferOrderDao.deleteTransferOrderUpgrade(paramMap);	
				map.put("result", 1);
			}else{
				map.put("result", 4);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", 0);
		}
		return map;
	}
	
	/**
	 * 临时职调动发令列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTempEmpTransferOrderList(Map paramMap,HttpServletRequest request){
		List retrunList = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getTempEmpTransferOrderList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getTempEmpTransferOrderList(paramMap);
		}
		return retrunList;

//		List returnList = new ArrayList();
//		for (int i = 0; i < list.size(); i++) {
//			paramMap = (LinkedHashMap) list.get(i);
//			paramMap.put("affirmerList", this.transactionAffirmDao.getAffirmerListByExpInsideNo(paramMap));
//			returnList.add(paramMap);
//		}
	}

	/**
	 * 临时职调动发令总数(get transaction transaction view total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getTempEmpTransferOrderListCnt(Map paramMap,HttpServletRequest request){
		paramMap.put("TRANS_NO", "1365");
		return transferOrderDao.getTempEmpTransferOrderListCnt(paramMap);
	}
	
	/**
	 * 临时职修改调动发令查询(Left the inquires)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getTempEmpTransferOrderEditList(LinkedHashMap paramMap, HttpServletRequest request) {
		List retrunList = new ArrayList();
		eidsForcommand(paramMap, request);
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getTempEmpTransferOrderEditList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getTempEmpTransferOrderEditList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 临时职修改调动发令条数查询(Left the inquires number)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public int getTempEmpTransferOrderEditListCnt(LinkedHashMap paramMap, HttpServletRequest request) {
		int retrunInt = 0;
		eidsForcommand(paramMap, request);
		retrunInt = transferOrderDao.getTempEmpTransferOrderEditListCnt(paramMap);
		return retrunInt;
	}
	
	/**
	 * 正规职调动发令列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getReguEmpTransferOrderList(Map paramMap,HttpServletRequest request){
		List retrunList = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getReguEmpTransferOrderList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getReguEmpTransferOrderList(paramMap);
		}
		return retrunList;

//		List returnList = new ArrayList();
//		for (int i = 0; i < list.size(); i++) {
//			paramMap = (LinkedHashMap) list.get(i);
//			paramMap.put("affirmerList", this.transactionAffirmDao.getAffirmerListByExpInsideNo(paramMap));
//			returnList.add(paramMap);
//		}
	}

	/**
	 * 正规职调动发令总数(get transaction transaction view total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getReguEmpTransferOrderListCnt(Map paramMap,HttpServletRequest request){
		paramMap.put("TRANS_NO", "1365");
		return transferOrderDao.getReguEmpTransferOrderListCnt(paramMap);
	}
	
	/**
	 * 正规职修改调动发令查询(Left the inquires)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public List getReguEmpTransferOrderEditList(LinkedHashMap paramMap, HttpServletRequest request) {
		List retrunList = new ArrayList();
		eidsForcommand(paramMap, request);
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = transferOrderDao.getReguEmpTransferOrderEditList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = transferOrderDao.getReguEmpTransferOrderEditList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 正规职修改调动发令条数查询(Left the inquires number)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	public int getReguEmpTransferOrderEditListCnt(LinkedHashMap paramMap, HttpServletRequest request) {
		int retrunInt = 0;
		eidsForcommand(paramMap, request);
		retrunInt = transferOrderDao.getReguEmpTransferOrderEditListCnt(paramMap);
		return retrunInt;
	}

	private static String APPLY_TYPE_NO = "23292329";
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public Map confirmReqHire(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");

		String   ls = "";
		String[] isChecked = request.getParameterValues("hr0504Person");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
			}
		}
		if(ls.equals(""))
		{
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "请至少选择一条信息！");
			return retMap;
		}
		paramMap.put("REQ_IDS", ls);

//		String[] deptNo = request.getParameterValues("hr0504DeptNo");
//		if(deptNo != null)
//		{
//			for(int i = 0; i < deptNo.length; i++){
//				if(deptNo[i].toString() != deptNo[0].toString()){
//					retMap.put("RET", 0);
//					retMap.put("MESSAGE", "批量申请只能申请同一部门下的人员信息！");
//					return retMap;
//				}
//			}
//		}
		
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		if(affirmId==null)
		{
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "没有设置审批人信息！");
			return retMap;
		}else{
			for(int j=0; j<affirmId.length; j++)
			{
				if(affirmId[j]==null || affirmId[j].trim().equals("")){
					retMap.put("RET", 0);
					retMap.put("MESSAGE", "审批人信息设置不正确！");
					return retMap;
				}
			}
		}
		try {
			paramMap.put("AFFIRMOR_ID", affirmId[0]);
			paramMap.put("REQ_ID", isChecked[0].toString());
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
			paramMap.put("CREATED_BY", admin.getPersonId());
			transferOrderDao.addconfirmReqHire(paramMap);

			for(int i=0; i<affirmId.length; i++)
			{
				paramMap.put("AFFIRMOR_ID", affirmId[i]);
				paramMap.put("AFFIRM_LEVEL", i+1);
				transferOrderDao.insertAffirmor(paramMap);
				if(i==0){
					this.sendToLGEP(paramMap.get("REQ_ID").toString(), affirmId[0], admin.getPersonId());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			return retMap;
		}

		return retMap;
	}

	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void sendToLGEP(String reqId, String CURRENT_AFFIRM_ID, String CREATED_BY){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", reqId);
			lgepMap.put("APPLY_TYPE_NAME", "临时职人员入职审批申请");
			lgepMap.put("APPLY_TITLE", "临时职人员入职审批申请");
			lgepMap.put("APPLY_EMPID", CREATED_BY);
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewReqHireAffirm?pageNum=1&LGEP=LGEP&LANGUAGE=zh&CPNY_ID=LGECH&personId=123&REQ_ID=" + reqId + "&affirmOrCheck=1");
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewReqHireAffirm?pageNum=1&LGEP=LGEP&LANGUAGE=zh&CPNY_ID=LGECH&personId=" + CURRENT_AFFIRM_ID + "&REQ_ID=" + reqId + "&affirmOrCheck=1");
			lgepMap.put("PRE_AFFIRM_EMPID", CURRENT_AFFIRM_ID);
			lgepMap.put("AFFIRM_LEVEL", "1");
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public Map delTempEmpInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");

		String   ls = "";
		String[] isChecked = request.getParameterValues("hr0504Check");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
				try {
					paramMap.put("PERSON_ID", isChecked[i].toString());
					this.empInfoDao.deleteProductInfo(paramMap);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(ls.equals(""))
		{
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "请至少选择一条信息！");
			return retMap;
		}
		paramMap.put("REQ_IDS", ls);

		try {
			transferOrderDao.delTempEmpInfoPe(paramMap);
			transferOrderDao.delTempEmpInfoPa(paramMap);
			transferOrderDao.delTempEmpInfoExp(paramMap);
			transferOrderDao.delTempEmpInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			return retMap;
		}

		return retMap;
	}
	
	/**
	 * 取得审批人列表:1.先取特殊设置人员的决裁者;2.再取特殊设置部门的决裁者;3.最后按流程取决裁者 (get approver list:1
	 * get approver by special-person's-approver setup.2 get approver by
	 * special-department's-approver setup.3 get approver by approve-flow)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getAffirmorList(String applyTypeNo, HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		paramMap.put("APPLY_TYPE_NO", applyTypeNo);
		if (paramMap.get("PERSON_ID") == null || "".equals(paramMap.get("PERSON_ID"))) {			
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		return this.getAffirmorListByMap(paramMap);
	}
	
	/**
	 * 通过request请求封装查询条件(get conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequest(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if ("1".equals(request.getParameter("flag"))) {
			if (request.getParameter("PERSON_ID") == null
					|| "".equals(request.getParameter("PERSON_ID"))) {
				paramMap.put("PERSON_ID", request.getParameter("personId"));
			} else {
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			}
		} else {
			if (paramMap.get("PERSON_ID") == null
					|| "".equals(paramMap.get("PERSON_ID"))) {
				paramMap.put("PERSON_ID", admin.getPersonId());
			}
		}
		return paramMap;
	}
	/**
	 * 取得审批人列表:1.先取特殊设置人员的决裁者;2.再取特殊设置部门的决裁者;3.最后按流程取决裁者 (get approver list:1
	 * get approver by special-person's-approver setup.2 get approver by
	 * special-department's-approver setup.3 get approver by approve-flow)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private List getAffirmorListByMap(LinkedHashMap paramMap) throws Exception {
		return infoApplySer.getAffirmorListByString(paramMap.get("APPLY_TYPE_NO").toString(), 
							paramMap.get("PERSON_ID").toString(), 
							"", "", paramMap.get("LANGUAGE").toString());
	}
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void sendToLGEPForResignation(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", paramMap.get("APPLY_TYPE_NO"));
			lgepMap.put("APPLY_NO", paramMap.get("REQ_ID"));
			lgepMap.put("APPLY_TYPE_NAME", "临时职人员离职审批申请");
			lgepMap.put("APPLY_TITLE", "临时职人员离职审批申请");
			lgepMap.put("APPLY_EMPID", paramMap.get("UPDATED_BY"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewReqResignAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID="+paramMap.get("CPNY_ID")+"&personId=123&REQ_ID=" + paramMap.get("REQ_ID") + "&affirmOrCheck=1");
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewReqResignAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID="+paramMap.get("CPNY_ID")+"&personId=" + paramMap.get("CURRENT_AFFIRMOR_ID") + "&REQ_ID=" + paramMap.get("REQ_ID") + "&affirmOrCheck=1");
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRMOR_ID"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}
	/*删除离职发令*/
	public Map deleteResignation(Map paramMap) {	
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			transferOrderDao.deleteResignation(paramMap);				
			map.put("result", 1);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", 0);
		}
		return map;
	}
	
	/*撤销离职发令*/
	@Override
	public Map confirmRevokeResignation(HttpServletRequest request) {	
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");
		//取当前撤销状态
		String revokeState = paramMap.get("hr0206RevokeResignState").toString();
		//取当前撤销的resign_no
		String   ls = "";
		String[] isChecked = request.getParameterValues("hr0206RevokeResignNo");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
			}
		}
		if(ls.equals(""))
		{
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "请至少选择一条信息！");
			return retMap;
		}
		paramMap.put("RESIGN_NOS", ls);
		paramMap.put("REVOKE_STATE", revokeState);
		paramMap.put("REVOKE_TYPE", paramMap.get("hr0206RevokeResignType").toString());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		// 判断是否需要决裁1为开，0为关
		Map paramValueMap = new LinkedHashMap();
		paramValueMap.put("CPNY_ID", admin.getCpnyId());
		paramValueMap.put("TYPE", "resign");	
		int affirmFlag = transferOrderDao.getParamInfoValue(paramValueMap);
		String[] affirmId = {""};
		if("5080".indexOf(revokeState)==-1){
			//撤销提交状态
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map = transferOrderDao.callRevokeResignation(paramMap);				
				String msg =  ObjectUtils.toString(map.get("MESSAGE")) ;
				if(msg.equals("OK")) retMap.put("RET", 1); else retMap.put("RET", 0);
			} catch (Exception e) {
				e.printStackTrace();
				retMap.put("RET", 0);
			}
		}else{//撤销状态:通过/已生效
			try{
				if (affirmFlag == 1) {
					//检查决裁线
					affirmId = request.getParameterValues("AFFIRMOR_ID");
					if(affirmId==null)
					{
						retMap.put("RET", 0);
						retMap.put("MESSAGE", "没有设置审批人信息！");
						return retMap;
					}
					paramMap.put("APPLY_TYPE_NO", 15823);
					paramMap.put("STATE", 30);//提交
					paramMap.put("AFTERSTATE", 70);	//审批中	
				}else{
					if("80".indexOf(revokeState)>-1){
						//撤销提交状态
						Map<String, Object> map = new HashMap<String, Object>();
						map = transferOrderDao.callRevokeResignation(paramMap);				
						String msg =  ObjectUtils.toString(map.get("MESSAGE")) ;
						if(msg.equals("OK")) retMap.put("RET", 1); else retMap.put("RET", 0);
					}
					paramMap.put("STATE", 80);//已生效
					paramMap.put("AFTERSTATE", 90);//已撤销
				}
			
				String reqNo = transferOrderDao.getNextExpInside();				
				paramMap.put("REQ_ID", reqNo);
				paramMap.put("CURRENT_AFFIRMOR_ID", affirmId[0]);
				paramMap.put("PERSON_ID", admin.getPersonId());				
				transferOrderDao.addConfirmReqRevokeResign(paramMap);
				
				if (affirmFlag == 1) {
					transferOrderDao.addAttachFiles(paramMap);
					//保存决裁线
					for(int i=0; i<affirmId.length; i++){
						paramMap.put("AFFIRMOR_ID", affirmId[i]);
						paramMap.put("AFFIRM_LEVEL", i+1);
						transferOrderDao.insertAffirmor(paramMap);
						if(i==0){
							this.sendToLGEPForRevokeResignation(paramMap);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				retMap.put("RET", 0);
				return retMap;
			}
		}		
		return retMap;
	}
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void sendToLGEPForRevokeResignation(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", paramMap.get("APPLY_TYPE_NO"));
			lgepMap.put("APPLY_NO", paramMap.get("REQ_ID"));
			lgepMap.put("APPLY_TYPE_NAME", "临时职人员撤销离职审批申请");
			lgepMap.put("APPLY_TITLE", "临时职人员撤销离职审批申请");
			lgepMap.put("APPLY_EMPID", paramMap.get("UPDATED_BY"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewReqResignAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID="+paramMap.get("CPNY_ID")+"&personId=123&REQ_ID=" + paramMap.get("REQ_ID") + "&affirmOrCheck=1");
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewReqResignAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID="+paramMap.get("CPNY_ID")+"&personId=" + paramMap.get("CURRENT_AFFIRMOR_ID") + "&REQ_ID=" + paramMap.get("REQ_ID") + "&affirmOrCheck=1");
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRMOR_ID"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public Map confirmTempEmpBatch(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");

		String   ls = "";
		List itemList=null;
		try {
			itemList = empInfoDao.getTempEmpBatchReqList(paramMap);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Map<String, Object> tempMap = new HashMap<String, Object>();
		for(int i=0;i<itemList.size();i++)
		{
			tempMap = (Map<String, Object>) itemList.get(i);
			ls = ls.equals("")?tempMap.get("PERSON_ID").toString():(ls + "," + tempMap.get("PERSON_ID").toString());
			if(i==0){
				paramMap.put("REQ_ID", tempMap.get("PERSON_ID").toString());
			}
		}
		if(ls.equals(""))
		{
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "未找到需要审批的记录信息！");
			return retMap;
		}
		paramMap.put("REQ_IDS", ls);

		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		if(affirmId==null)
		{
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "没有设置审批人信息！");
			return retMap;
		}else{
			for(int j=0; j<affirmId.length; j++)
			{
				if(affirmId[j]==null || affirmId[j].trim().equals("")){
					retMap.put("RET", 0);
					retMap.put("MESSAGE", "审批人信息设置不正确！");
					return retMap;
				}
			}
		}
		try {
			paramMap.put("AFFIRMOR_ID", affirmId[0]);
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("APPLY_TYPE_NO", "23292329");
			paramMap.put("CREATED_BY", admin.getPersonId());
			transferOrderDao.addconfirmReqHire(paramMap);

			for(int i=0; i<affirmId.length; i++)
			{
				paramMap.put("AFFIRMOR_ID", affirmId[i]);
				paramMap.put("AFFIRM_LEVEL", i+1);
				transferOrderDao.insertAffirmor(paramMap);
				if(i==0){
					this.sendToLGEP(paramMap.get("REQ_ID").toString(), affirmId[0], admin.getPersonId());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			return retMap;
		}

		return retMap;
	}
	
	/*撤销临时职发令*/
	@Override
	public Map cancelTransferOrderInBatch(HttpServletRequest request) {	
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");
		//取当前撤销的experience_inside_no
		String   ls = "";
		String[] isChecked = request.getParameterValues("hr0515DelTempTROCKB");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
			}
		}
		if(ls.equals(""))
		{
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "请至少选择一条信息！");
			return retMap;
		}
		paramMap.put("EXPERIENCE_INSIDE_NOS", ls);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
			
		//检查当前数据库中发令状态
		try {
			int invalidCnt = transferOrderDao.getDelTempTROInvalidStateCnt(paramMap);
			if(invalidCnt > 0) {
				retMap.put("RET", 0);
				retMap.put("MESSAGE", "只可以删除未生效状态的发令！");
			}else{
				transferOrderDao.cancelTransferOrderInBatch(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "删除失败！");
		}		
		return retMap;
	}
	/**
	 * 派遣地发令修改
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateSendAndSendOffNew(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//修改人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		if(!"".equals(paramMap.get("paYearStartDate")) && !"".equals(paramMap.get("paMonthStartDate")) && paramMap.get("paYearStartDate")!=null && paramMap.get("paMonthStartDate")!=null){
			paramMap.put("START_DATE",paramMap.get("paYearStartDate").toString()+paramMap.get("paMonthStartDate").toString());
		}
		if(!"".equals(paramMap.get("paYearEndDate")) && !"".equals(paramMap.get("paMonthEndDate")) && paramMap.get("paYearEndDate")!=null && paramMap.get("paMonthEndDate")!=null){
			paramMap.put("END_DATE",paramMap.get("paYearEndDate").toString()+paramMap.get("paMonthEndDate").toString());
		}
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.transferOrderDao.updateSendAndSendOffNew(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/*撤销正规职发令*/
	@Override
	public Map cancelReguEmpTransferOrder(HttpServletRequest request) {	
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");
		//取当前撤销的experience_inside_no
		String   ls = "";
		String[] isChecked = request.getParameterValues("hr0516DelReguTROCKB");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
			}
		}
		if(ls.equals(""))
		{
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "请至少选择一条信息！");
			return retMap;
		}
		paramMap.put("EXPERIENCE_INSIDE_NOS", ls);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
			
		//检查当前数据库中发令状态
		try {
			int invalidCnt = transferOrderDao.getDelTempTROInvalidStateCnt(paramMap);
			if(invalidCnt > 0) {
				retMap.put("RET", 0);
				retMap.put("MESSAGE", "只可以删除未生效状态的发令！");
			}else{
				transferOrderDao.cancelTransferOrderInBatch(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "删除失败！");
		}		
		return retMap;
	}
}
