package com.ait.ess.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ait.ess.dao.EssEmpInfoDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.service.EssEmpInfoSer;
import com.ait.hrm.dao.JobTypeDao;
import com.ait.hrm.dao.TransferOrderDao;
import com.ait.hrm.service.TransferOrderSer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.CompanyDao;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.hrm.service.EssApplyInfoSer;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpInfoSerImpl.java
 * @Description: implement Class EmpInfoSer.java
 * @Create date: Jan 16, 2012 3:28:57 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
@Service
@SuppressWarnings({"unchecked","unused"})
public class EssEmpInfoSerImpl implements EssEmpInfoSer {
	
	Logger logger = Logger.getLogger(EssEmpInfoSerImpl.class);
	@Autowired
	private EssEmpInfoDao empInfoDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private AuthorityUtil authorityUtil;
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	@Autowired
	public JobTypeDao jobTypeDao;
	@Autowired
	private TransferOrderSer transferOrderSer;
	@Autowired
	private TransferOrderDao transferOrderDao;
	@Autowired
	private PaTempSalesDAO paTempSalesDAO;
	@Autowired
	private EssApplyInfoSer EssApplyInfoSer;

	public Map getEmpList(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		param.put("admin", admin.getAdminID());
		Map map = new HashMap();
		map.put("list", empInfoDao.getEmpList(param));
		map.put("count", empInfoDao.getEmpCnt(param));
		return map;
	}

	public Object getBasicInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		logger.info("basic.empid=" + empid);
		return empInfoDao.getBasicInfo(param);
	}

	@Override
	public Map getPaEmpInfoForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);

		return empInfoDao.getPaEmpInfoForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getSinfoForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getSinfoForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getBizlistForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getBizlistForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getAppendInfoForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getAppendInfoForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getITLevelInfoForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getITLevelInfoForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getLanuageInfoForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getLanuageInfoForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getQualificationInfoForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getQualificationInfoForGrid(param, Integer
				.parseInt(request.getParameter("page").toString()), Integer
				.parseInt(request.getParameter("pagesize").toString()));
	}

	@Override
	public Map getEvalForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getEvalForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getEvaluateForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getEvaluateForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getHealthInfoForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getHealthInfoForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getExpInsideForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		param.put("DATELEFT", "NULL");
		// sMap2.put("NOT_LIKE_UPGRADE", "TransCode3");
		param.put("ACTIVITY", 1);
		return empInfoDao.getExpInsideForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getResignationForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		param.put("DATELEFT", "NULL");
		// sMap2.put("NOT_LIKE_UPGRADE", "TransCode3");
		param.put("ACTIVITY", 1);
		Map temp = empInfoDao.getResignationForGrid(param, Integer
				.parseInt(request.getParameter("page").toString()), Integer
				.parseInt(request.getParameter("pagesize").toString()));
		if (temp.isEmpty()) {
			param.put("ACTIVITY", 2);
			return empInfoDao.getResignationForGrid(param, Integer
					.parseInt(request.getParameter("page").toString()), Integer
					.parseInt(request.getParameter("pagesize").toString()));
		}
		return temp;
	}

	@Override
	public Map getFamilyInfoForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getFamilyInfoForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getSocietyRelationForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getSocietyRelationForGrid(param, Integer
				.parseInt(request.getParameter("page").toString()), Integer
				.parseInt(request.getParameter("pagesize").toString()));
	}

	@Override
	public Map getPunishMentForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getPunishMentForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getRewardForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getRewardForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getEduForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getEduForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getDispatchForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getDispatchForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getPluralityForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getPluralityForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getSuspendForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getSuspendForGrid(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

	@Override
	public Map getExperienceInfoForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map param = ObjectBindUtil.getRequestParamData(request);
		String empid = request.getParameter("empid");
		if (empid == null || empid.equals("")) {
			empid = admin.getAdminID();
		}
		param.put("EMPID", empid);
		return empInfoDao.getExperienceInfoForGrid(param, Integer
				.parseInt(request.getParameter("page").toString()), Integer
				.parseInt(request.getParameter("pagesize").toString()));
	}

	@Override
	public Map getBankNameCodeForSelect(HttpServletRequest request) {
		Map param = new LinkedHashMap();
		param.put("PARENTCODE", "BankNameCode");
		return empInfoDao.getSysCodeForSelect(param);
	}

	@Override
	public Map getCheckResultForSelect(HttpServletRequest request) {
		Map param = new LinkedHashMap();
		param.put("PARENTCODE", "CheckResult");
		return empInfoDao.getSysCodeForSelect(param);
	}

	@Override
	public Map getCheckWhetherForSelect(HttpServletRequest request) {
		Map param = new LinkedHashMap();
		param.put("PARENTCODE", "CheckWhether");
		return empInfoDao.getSysCodeForSelect(param);
	}

	@Override
	public Map getDegreeCodeForSelect(HttpServletRequest request) {
		Map param = new LinkedHashMap();
		param.put("PARENTCODE", "DegreeCode");
		return empInfoDao.getSysCodeForSelect(param);
	}

	@Override
	public Map getLanguageExamCodeForSelect(HttpServletRequest request) {
		Map param = new LinkedHashMap();
		param.put("PARENTCODE", "LanguageExamCode");
		return empInfoDao.getSysCodeForSelect(param);
	}

	@Override
	public Map getLanguageLevelCodeForSelect(HttpServletRequest request) {
		Map param = new LinkedHashMap();
		param.put("PARENTCODE", "LanguageLevelCode");
		return empInfoDao.getSysCodeForSelect(param);
	}

	@Override
	public Map getLanguageTypeCodeForSelect(HttpServletRequest request) {
		Map param = new LinkedHashMap();
		param.put("PARENTCODE", "LanguageTypeCode");
		return empInfoDao.getSysCodeForSelect(param);
	}

	@Override
	public Map getOtherRelationForSelect(HttpServletRequest request) {
		Map param = new LinkedHashMap();
		param.put("PARENTCODE", "OtherRelation");
		return empInfoDao.getSysCodeForSelect(param);
	}

	@Override
	public Map getQualNameCodeForSelect(HttpServletRequest request) {
		Map param = new LinkedHashMap();
		param.put("PARENTCODE", "QualNameCode");
		return empInfoDao.getSysCodeForSelect(param);
	}

	@Override
	public Map getRelationalTypeCodeForSelect(HttpServletRequest request) {
		Map param = new LinkedHashMap();
		param.put("PARENTCODE", "RelationalTypeCode");
		return empInfoDao.getSysCodeForSelect(param);
	}

	@Override
	public Map getTogetherFlagForSelect(HttpServletRequest request) {
		Map param = new LinkedHashMap();
		param.put("PARENTCODE", "LiveTogetherFlag");
		return empInfoDao.getSysCodeForSelect(param);
	}

	@Override
	public String updateFamilyInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception {
		for(Map data : list)
		{
			if("add".equals(data.get("__status")))
			{
				this.empInfoDao.addFamilyInfo(data);
			}else if("update".equals(data.get("__status")))
			{
				this.empInfoDao.updateFamilyInfo(data);
			}else if("delete".equals(data.get("__status")))
			{
				this.empInfoDao.deleteFamilyInfo(data);
			}
		}
		return null;
	}
	@Override
	public String updateSocietyRelationGrid(
			List<LinkedHashMap<String, Object>> list) throws Exception {
		String temp="";
		for(Map data : list)
		{
			if("add".equals(data.get("__status")))
			{
				this.empInfoDao.addSocietyRelation(data);
			}else if("update".equals(data.get("__status")))
			{
				this.empInfoDao.updateSocietyRelation(data);
			}else if("delete".equals(data.get("__status")))
			{
				this.empInfoDao.deleteSocietyRelation(data);
			}
		}
		return null;
	}

	@Override
	public String updateExperienceInfoGrid(
			List<LinkedHashMap<String, Object>> list) throws Exception {
		for(Map data : list)
		{
			if("add".equals(data.get("__status")))
			{
				this.empInfoDao.addExperienceInfo(data);
			}else if("update".equals(data.get("__status")))
			{
				this.empInfoDao.updateExperienceInfo(data);
			}else if("delete".equals(data.get("__status")))
			{
				this.empInfoDao.deleteExperienceInfo(data);
			}
		}
		return null;
	}

	@Override
	public String updateFappendInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception {
		return updateFappendInfoGrid(list,null);
	}
	
	@Override
	public String updateFappendInfoGrid(List<LinkedHashMap<String, Object>> list,HttpServletRequest request)
			throws Exception {
		String updateBy=null;
		if(request!=null){
			updateBy=request.getParameter("updateBy");
		}
		for(Map data : list)
		{	data.put("updateBy", updateBy);
			data.put("registerID", updateBy);
			if("add".equals(data.get("__status")))
			{
				this.empInfoDao.addFappendInfo(data);
			}else if("update".equals(data.get("__status")))
			{
				this.empInfoDao.updateFappendInfo(data);
			}else if("delete".equals(data.get("__status")))
			{
				this.empInfoDao.deleteFappendInfo(data);
			}
		}
		return null;
	}
	@Override
	public String updateHealthInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception {
		for(Map data : list)
		{
			if("add".equals(data.get("__status")))
			{
				this.empInfoDao.addHealthInfo(data);
			}else if("update".equals(data.get("__status")))
			{
				this.empInfoDao.updateHealthInfo(data);
			}else if("delete".equals(data.get("__status")))
			{
				this.empInfoDao.deleteHealthInfo(data);
			}
		}
		return null;
	}

	@Override
	public String updateLanuageInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception {
		for(Map data : list)
		{
			if("add".equals(data.get("__status")))
			{
				this.empInfoDao.addLanuageInfo(data);
			}else if("update".equals(data.get("__status")))
			{
				this.empInfoDao.updateLanuageInfo(data);
			}else if("delete".equals(data.get("__status")))
			{
				this.empInfoDao.deleteLanuageInfo(data);
			}
		}
		return null;
	}

	@Override
	public String updatePaEmpInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception {
		for(Map data : list)
		{
			if("add".equals(data.get("__status")))
			{
				this.empInfoDao.addPaEmpInfo(data);
			}else if("update".equals(data.get("__status")))
			{
				this.empInfoDao.updatePaEmpInfo(data);
			}else if("delete".equals(data.get("__status")))
			{
				this.empInfoDao.deletePaEmpInfo(data);
			}
		}
		return null;
	}

	public String updatePaEmpInfoGrid(List<LinkedHashMap<String, Object>> list,HttpServletRequest request)
	throws Exception {
		String updateBy=null;
		if(request!=null){
			updateBy=request.getParameter("updateBy");
		}
		for(Map data : list)
		{
			data.put("EMPID", updateBy);
			if("add".equals(data.get("__status")))
			{
				this.empInfoDao.addPaEmpInfo(data);
			}else if("update".equals(data.get("__status")))
			{
				this.empInfoDao.updatePaEmpInfo(data);
			}else if("delete".equals(data.get("__status")))
			{
				this.empInfoDao.deletePaEmpInfo(data);
			}
		}
		return null;
	}
	
	@Override
	public String updateQualificationInfoGrid(
			List<LinkedHashMap<String, Object>> list) throws Exception {
		for(Map data : list)
		{
			if("add".equals(data.get("__status")))
			{
				this.empInfoDao.addQualificationInfo(data);
			}else if("update".equals(data.get("__status")))
			{
				this.empInfoDao.updateQualificationInfo(data);
			}else if("delete".equals(data.get("__status")))
			{
				this.empInfoDao.deleteQualificationInfo(data);
			}
		}
		return null;
	}
	
	public Object getPersonalInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return empInfoDao.getPersonalInfoByPid(paramMap);
		//return empInfoDao.getPersonalInfo(paramMap);
	}
	
	/**
	 * 员工基础信息 (Staff foundation information)
	 * @param request
	 * @return Object
	 */
	@Override
	public Object getPersonalInfoByPid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PAY_DATE_SS", request.getParameter("PAY_DATE_SS"));
		return empInfoDao.getPersonalInfoByPid(paramMap);
	}
	
	
	/**
	 * 员工基础信息 (Staff foundation information)
	 * @param request
	 * @return Object
	 */
	@Override
	public Object getEssPersonInfoById(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
	
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId() );
		}
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return empInfoDao.getEssPersonInfoById(paramMap);
	}
	/**
	 * 员工基础信息 (Staff foundation information)
	 * @param request
	 * @return Object
	 */
	@Override
	public Object getPersonalInfoByPid1(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		if(request.getParameter("PERSON_ID")!=null){
			paramMap.put("PERSON_ID",request.getParameter("PERSON_ID"));
		}
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return empInfoDao.getPersonalInfoByPid1(paramMap);
	}
	/**
	 * 员工基础信息 (Staff foundation information)
	 * @param request
	 * @return List
	 */
	@Override
	public List getPersonalList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		
		//还未解决是登陆人的PERSONID 还是传回来的PERSONID
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getPersonalList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getPersonalList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * 员工基础信息数 (personal number based information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int getPersonalCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return empInfoDao.getPersonalCnt(paramMap);
		
	}

	/**
	 * 毕业学校查询(Graduate school inquires)
	 * @param obj
	 * @return int
	 */
	@Override
	public List getEducationList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		
		
	
			retrunList = empInfoDao.getEducationList(paramMap) ;
	
		
		return retrunList ;
	}
	
	/**
	 * 添加毕业学校信息(Add graduate school information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int addEduactionInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		try {
			int count=Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				if(request.getParameter("INSTITUTION_NAME"+i)!=null){
					
					paramMap.put("INSTITUTION_NAME", request.getParameter("INSTITUTION_NAME"+i));//学校名称
					paramMap.put("SUBJECT_CLASSIFY", request.getParameter("SUBJECT_CLASSIFY"+i));//专业分类
					paramMap.put("SUBJECT", request.getParameter("SUBJECT"+i));//专业
					
					paramMap.put("SUBJECT_CLASSIFY_TWO", request.getParameter("SUBJECT_CLASSIFY_TWO"+i));//第二专业分类
					paramMap.put("SUBJECT_SECOND", request.getParameter("SUBJECT_SECOND"+i));//第二专业
					paramMap.put("START_DATE", request.getParameter("START_YEAR"+i)+"-"+request.getParameter("START_MONTH"+i));//入学时间
					paramMap.put("END_DATE", request.getParameter("END_YEAR"+i)+"-"+request.getParameter("END_MONTH"+i));//毕业时间
					paramMap.put("DEGREE_CODE", request.getParameter("DEGREE_CODE"+i));//学历
					//paramMap.put("EDUC_CARD_NO", request.getParameter("EDUC_CARD_NO"+i));
					paramMap.put("SITE_PROVINCE", request.getParameter("SITE_PROVINCE"+i));//所在地省CODE
					paramMap.put("SITE_CITY", request.getParameter("SITE_CITY"+i));//所在地市CODE
					paramMap.put("FINAL_DEGREE_WHETHER", request.getParameter("FINAL_DEGREE_WHETHER"+i));//是否最终学历
					
					paramMap.put("REMARKS", request.getParameter("REMARKS"+i));//备注
					this.empInfoDao.addEduactionInfo(paramMap);
					if(request.getParameter("FINAL_DEGREE_WHETHER"+i).toString().equals("Y")){
						this.empInfoDao.updataEduaction(paramMap);//修改员工个人信息表最终学历信息
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除毕业学校信息(Delete graduate school information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteEduactionInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			String[] paramData = request.getParameterValues("DNO");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("EDUC_NO", paramData[i]);
						this.empInfoDao.deleteEduactionInfo(paramMap);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改个人信和毕业学校信息(Modify personal letters and school information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int editEduPerInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			
			//------------------------------employee---------------------------------------
			paramMap.put("WHETHER_COMMUNIST", request.getParameter("WHETHER_COMMUNIST"));//是否共产党员
			paramMap.put("WORK_AREA", request.getParameter("WORK_AREA"));//工作地
			paramMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));//详细人力区分 37个
			paramMap.put("PROMTR_WORK_TP", request.getParameter("PROMTR_WORK_TP"));//工作类型
			paramMap.put("OUTER_WORK_YEAR", request.getParameter("OUTER_WORK_YEAR"));//社外工龄
			paramMap.put("INSRAREA_ID", request.getParameter("INSRAREA_ID"));//福利地区
			paramMap.put("INSRAREA_ID_INS", request.getParameter("INSRAREA_ID_INS"));//福利地区
			paramMap.put("PAY_AREA_CD", request.getParameter("PAY_AREA_CD"));//分公司(大区)
			paramMap.put("PROD_TP", request.getParameter("PROD_TP"));//产品
			paramMap.put("PROMTR_TP", request.getParameter("PROMTR_TP"));//促销员所属
			paramMap.put("STAR_TP", request.getParameter("STAR_TP"));//星级级别
			paramMap.put("PART_TIME_YN", request.getParameter("PART_TIME_YN"));//是否兼卖
			paramMap.put("COMM_YN", request.getParameter("COMM_YN"));//是否共建促销员
			paramMap.put("YY_VAC_STD_DATE", request.getParameter("YY_VAC_STD_DATE"));//年假基准
			paramMap.put("EVS_TYPE_CODE", request.getParameter("EVS_TYPE_CODE"));//评价类型
			paramMap.put("EMP_TYPE_START_DATE ", request.getParameter("EMP_TYPE_START_DATE "));//人员类型生效日期
			paramMap.put("C_DATE_LEFT ", request.getParameter("C_DATE_LEFT "));//实际离职日期
			
			//------------------------------HR_PERSONAL_INFO---------------------------------------
			paramMap.put("IDCARD_ADDR", request.getParameter("IDCARD_ADDR"));//家庭地址
			paramMap.put("BORNPLACE_CODE", request.getParameter("BORNPLACE_CODE"));//籍贯
			paramMap.put("NATION_CODE", request.getParameter("NATION_CODE"));//民族
			paramMap.put("POLITY_CODE", request.getParameter("POLITY_CODE"));//政治面貌
			paramMap.put("HEIGHT", request.getParameter("HEIGHT"));//身高
			paramMap.put("WEIGHT", request.getParameter("WEIGHT"));//体重
			paramMap.put("BLOOD_TYPE", request.getParameter("BLOOD_TYPE"));//血型
			paramMap.put("DISABILITY_YN", request.getParameter("DISABILITY_YN"));//是否残疾
			paramMap.put("RECRUITMENT_SOURCE_TYPE", request.getParameter("RECRUITMENT_SOURCE_TYPE"));//采用详细路径 
			paramMap.put("LEAVE_REASON", request.getParameter("LEAVE_REASON"));//离职原因
			paramMap.put("REMARK", request.getParameter("REMARK"));//备注
			paramMap.put("INSURANCE_TYPE_CODE", request.getParameter("INSURANCE_TYPE_CODE"));//保险类型
			paramMap.put("MANUAL_NUM", request.getParameter("MANUAL_NUM"));//员工手册编号
			paramMap.put("INSURANCE_COMPANY", request.getParameter("INSURANCE_COMPANY"));//保险公司
			
			//------------------------------HR_EMP_PA_INFO---------------------------------------
			paramMap.put("LOVE_FUND_PAYMENT_TYPE", request.getParameter("LOVE_FUND_PAYMENT_TYPE"));//爱心基金支付方式
			paramMap.put("IF_PAYMENT_LOVE_FUND", request.getParameter("IF_PAYMENT_LOVE_FUND"));//是否支付爱心基金
			paramMap.put("IF_PAYMENT_RENT", request.getParameter("IF_PAYMENT_RENT"));//负担房租标志
			paramMap.put("IF_PAYMENT_MEDICAL", request.getParameter("IF_PAYMENT_MEDICAL"));//负担医疗费标志
			paramMap.put("IF_PAYMENT_EDUCATION", request.getParameter("IF_PAYMENT_EDUCATION"));//负担教育费标志
			
			this.empInfoDao.editEduPerInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	@Override
	public int editTempEduPerInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			
			//------------------------------employee---------------------------------------
			paramMap.put("ID_CARD_NO", request.getParameter("ID_CARD_NO"));//ID卡号
			if(request.getParameter("ID_CARD_NO").length()>1){//检查ID卡号是否重复
				paramMap.put("CPNY_ID", admin.getCpnyId());
				int idCardNoCnt = empInfoDao.getEmpIdCardNoCnt(paramMap);
				if(idCardNoCnt > 0){
					return 2;
				}
			}
			paramMap.put("JOB_TITLE_CD", request.getParameter("JOB_TITLE_CD"));//职务			
			paramMap.put("WHETHER_COMMUNIST", request.getParameter("WHETHER_COMMUNIST"));//是否共产党员
			paramMap.put("WORK_AREA", request.getParameter("WORK_AREA"));//工作地
			paramMap.put("SHIFT_NO", request.getParameter("SHIFT_NO"));//班号
			
			paramMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));//详细人力区分 37个
			paramMap.put("PROMTR_WORK_TP", request.getParameter("PROMTR_WORK_TP"));//工作类型
			paramMap.put("OUTER_WORK_YEAR", request.getParameter("OUTER_WORK_YEAR"));//社外工龄
			paramMap.put("INSRAREA_ID", request.getParameter("INSRAREA_ID"));//福利地区
			paramMap.put("INSRAREA_ID_INS", request.getParameter("INSRAREA_ID_INS"));//福利地区
			paramMap.put("PAY_AREA_CD", request.getParameter("PAY_AREA_CD"));//分公司(大区)
			paramMap.put("PROD_TP", request.getParameter("PROD_TP"));//产品
			paramMap.put("PROMTR_TP", request.getParameter("PROMTR_TP"));//促销员所属
			paramMap.put("STAR_TP", request.getParameter("STAR_TP"));//星级级别
			paramMap.put("PART_TIME_YN", request.getParameter("PART_TIME_YN"));//是否兼卖
			paramMap.put("COMM_YN", request.getParameter("COMM_YN"));//是否共建促销员
			paramMap.put("YY_VAC_STD_DATE", request.getParameter("YY_VAC_STD_DATE"));//年假基准
			paramMap.put("EVS_TYPE_CODE", request.getParameter("EVS_TYPE_CODE"));//评价类型
			paramMap.put("C_DATE_LEFT ", request.getParameter("C_DATE_LEFT "));//实际离职日期
			//paramMap.put("EMP_TYPE_START_DATE ", request.getParameter("EMP_TYPE_START_DATE "));//人员类型生效日期
			
			//------------------------------HR_PERSONAL_INFO---------------------------------------
			paramMap.put("SEXCODE", request.getParameter("SEX"));//性别
			paramMap.put("FINAL_DEGREE_CODE", request.getParameter("FINAL_DEGREE_CODE"));//学位
			paramMap.put("REG_TYPE_CODE", request.getParameter("REG_TYPE_CODE"));//户口性质
			paramMap.put("DOB", request.getParameter("DOB"));//出生日期
			paramMap.put("WEDD_DATE", request.getParameter("WEDD_DATE"));//结婚纪念日
			paramMap.put("IDCARD_NO", request.getParameter("IDCARD_NO"));//身份证号
			paramMap.put("EMAIL", request.getParameter("EMAIL"));//邮箱
			paramMap.put("HOME_PHONE", request.getParameter("HOME_PHONE"));//家庭电话 
			paramMap.put("OFFICE_PHONE", request.getParameter("OFFICE_PHONE"));//办公电话
			paramMap.put("CELLPHONE", request.getParameter("CELLPHONE"));//手机号码
			paramMap.put("REG_PLACE", request.getParameter("REG_PLACE"));//户口所在地
			paramMap.put("HOME_ADDRESS", request.getParameter("HOME_ADDRESS"));//家庭住址
			paramMap.put("POSTALCODE", request.getParameter("POSTALCODE"));//邮编
			
			paramMap.put("IDCARD_ADDR", request.getParameter("IDCARD_ADDR"));//现地址
			paramMap.put("BORNPLACE_CODE", request.getParameter("BORNPLACE_CODE"));//籍贯
			paramMap.put("NATION_CODE", request.getParameter("NATION_CODE"));//民族
			paramMap.put("POLITY_CODE", request.getParameter("POLITY_CODE"));//政治面貌
			paramMap.put("HEIGHT", request.getParameter("HEIGHT"));//身高
			paramMap.put("WEIGHT", request.getParameter("WEIGHT"));//体重
			paramMap.put("BLOOD_TYPE", request.getParameter("BLOOD_TYPE"));//血型
			paramMap.put("DISABILITY_YN", request.getParameter("DISABILITY_YN"));//是否残疾
			paramMap.put("RECRUITMENT_SOURCE_TYPE", request.getParameter("RECRUITMENT_SOURCE_TYPE"));//采用详细路径（招聘来源） 
			paramMap.put("LEAVE_REASON", request.getParameter("LEAVE_REASON"));//离职原因
			paramMap.put("REMARK", request.getParameter("REMARK"));//备注
			paramMap.put("INSURANCE_TYPE_CODE", request.getParameter("INSURANCE_TYPE_CODE"));//保险类型
			paramMap.put("MANUAL_NUM", request.getParameter("MANUAL_NUM"));//员工手册编号
			paramMap.put("INSURANCE_COMPANY", request.getParameter("INSURANCE_COMPANY"));//保险公司
			
			//------------------------------HR_EMP_PA_INFO---------------------------------------
			//paramMap.put("PAY_GRADE", request.getParameter("PAY_GRADE"));//工资级号
			//paramMap.put("PAY_STEP", request.getParameter("PAY_STEP"));//工资级号等级
			paramMap.put("BASE_PAY", request.getParameter("BASE_PAY"));//基本工资
			paramMap.put("VARB_PAY", request.getParameter("VARB_PAY"));//变动工资
			//paramMap.put("ANSAL", request.getParameter("ANSAL"));//年薪
			paramMap.put("BANK_ID", request.getParameter("BANK_ID"));//银行代码
			paramMap.put("CARD_NAME", request.getParameter("CARD_NAME"));//开户行
			paramMap.put("CARD_NO", request.getParameter("CARD_NO"));//银行账号
			paramMap.put("EXPNS_BANK_CD", request.getParameter("EXPNS_BANK_CD"));//费用银行代码
			paramMap.put("EXPNS_BANK_BRNCH_NM", request.getParameter("EXPNS_BANK_BRNCH_NM"));//费用开户行
			paramMap.put("EXPNS_BANK_ACCT_NO", request.getParameter("EXPNS_BANK_ACCT_NO"));//费用银行账号 
			paramMap.put("PROB_PAY_RAT", request.getParameter("PROB_PAY_RAT"));//试用期比例
			
			paramMap.put("LOVE_FUND_PAYMENT_TYPE", request.getParameter("LOVE_FUND_PAYMENT_TYPE"));//爱心基金支付方式
			paramMap.put("IF_PAYMENT_LOVE_FUND", request.getParameter("IF_PAYMENT_LOVE_FUND"));//是否支付爱心基金
			paramMap.put("IF_PAYMENT_RENT", request.getParameter("IF_PAYMENT_RENT"));//负担房租标志
			paramMap.put("IF_PAYMENT_MEDICAL", request.getParameter("IF_PAYMENT_MEDICAL"));//负担医疗费标志
			paramMap.put("IF_PAYMENT_EDUCATION", request.getParameter("IF_PAYMENT_EDUCATION"));//负担教育费标志
			
			this.empInfoDao.editTempEduPerInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 发令查询(dekreti inquires)
	 * @param obj
	 * @return list
	 */
	@Override
	public List getExpInsideList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("interLanguage", Messages.getLanguage(request));
	
			paramMap.put("PERSON_ID",admin.getPersonId() );
		
			retrunList = empInfoDao.getExpInsideList(paramMap) ;
		
		return retrunList ;
	}
	
	/**
	 * 发令查询(dekreti inquires)
	 * @param obj
	 * @return list
	 */
	@Override
	public List getAssignmentList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getAssignmentList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getAssignmentList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 工会信息查询(Left information query)
	 * @param obj
	 * @return list
	 */
	public List getTradeunionList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getTradeunionList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getTradeunionList(paramMap) ;
		}
		return retrunList ;
	}
	/**
	 * 离职信息查询(Left information query)
	 * @param obj
	 * @return list
	 */
	@Override
	public List getResignationInfo(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getResignationInfo(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getResignationInfo(paramMap) ;
		}
		return retrunList ;
	}

	/**
	 * 评价信息(Evaluation information)
	 * @param obj
	 * @return list
	 */
	@Override
	public List getEvsInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		retrunList = empInfoDao.getEvsInfoList(paramMap) ;
		return retrunList ;
	}

	/**
	 * 添加评价信息(add Evaluation information)
	 * @param request
	 * @return int
	 */
	@Override
	public int addEvsInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		try {
			int count=Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				if(request.getParameter("EV_PERIOD"+i)!=null){
					
					paramMap.put("EV_PERIOD", request.getParameter("EV_PERIOD"
							+ i));
					paramMap.put("EV_TYPE_ID", request
							.getParameter("EV_TYPE_ID" + i));
					paramMap.put("EV_ACHI", request.getParameter("EV_ACHI" + i));
					paramMap.put("EV_ATTI", request.getParameter("EV_ATTI" + i));
					paramMap.put("EV_ABIL", request.getParameter("EV_ABIL" + i));					
					paramMap
							.put("EV_MARK", request.getParameter("EV_MARK" + i));
					paramMap.put("EV_GRADE", request.getParameter("EV_GRADE"
							+ i));
					paramMap.put("SUGGESTION", request
							.getParameter("SUGGESTION" + i));
					paramMap.put("FINAL_SEQUENCE", request
							.getParameter("FINAL_SEQUENCE" + i));
					paramMap.put("TOTAL_PEOPLE", request
							.getParameter("TOTAL_PEOPLE" + i));
					paramMap.put("EV_REMARK", request.getParameter("EV_REMARK"
							+ i));
					this.empInfoDao.addEvsInfo(paramMap);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	/**
	 * 查询评价信息(select assessment information)
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@Override
	public Object getEvsInfo(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		param.put("PERSON_ID",request.getParameter("PERSON_ID") );
		param.put("EV_PERIOD",request.getParameter("EV_PERIOD") );
		return empInfoDao.getEvsInfo(param);
	}

	/**
	 * 修改评价信息(Modify assessment information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int editEvsInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));

			String[] paramData = request.getParameterValues("EPNO");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("EV_PERIOD", paramData[i]);
					paramMap.put("EV_PERIOD", request.getParameter("EV_PERIOD_"
							+ paramData[i]));
					paramMap.put("EV_PERIOD1", request
							.getParameter("EV_PERIOD1_" + paramData[i]));
					paramMap.put("EV_TYPE_ID", request
							.getParameter("EV_TYPE_ID_" + paramData[i]));
					paramMap.put("EV_ACHI", request.getParameter("EV_ACHI_"+ paramData[i]));
					paramMap.put("EV_ATTI", request.getParameter("EV_ATTI_"+ paramData[i]));
					paramMap.put("EV_ABIL", request.getParameter("EV_ABIL_"+ paramData[i]));					
					paramMap.put("EV_MARK", request.getParameter("EV_MARK_"
							+ paramData[i]));
					paramMap.put("EV_GRADE", request.getParameter("EV_GRADE_"
							+ paramData[i]));
					paramMap.put("SUGGESTION", request
							.getParameter("SUGGESTION_" + paramData[i]));
					paramMap.put("FINAL_SEQUENCE", request
							.getParameter("FINAL_SEQUENCE_" + paramData[i]));
					paramMap.put("TOTAL_PEOPLE", request
							.getParameter("TOTAL_PEOPLE_" + paramData[i]));

					paramMap.put("EV_REMARK", request.getParameter("EV_REMARK_"
							+ paramData[i]));
						this.empInfoDao.editEvsInfo(paramMap);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 删除评价信息(delete assessment information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteEvsInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		try {
			String[] paramData = request.getParameterValues("EPNO");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("EV_PERIOD", paramData[i]);
						this.empInfoDao.deleteEvsInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 奖励(Reward)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getReward(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("interLanguage", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		
			retrunList = empInfoDao.getReward(paramMap) ;
		
		return retrunList ;
	}
	
	

	/**
	 * 奖励单个(Reward)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public Object getRewardInfo(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("REWARD_NO",request.getParameter("REWARD_NO"));

		
			
		
		return empInfoDao.getRewardInfo(paramMap);
	}

	/**
	 * 惩戒(Punishment)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getPunishment(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getPunishment(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getPunishment(paramMap) ;
		}
		return retrunList ;
	}

	/**
	 * 兼职(Plurality)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getPluralityList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		retrunList = empInfoDao.getPluralityList(paramMap) ;
		return retrunList ;
	}

	/**
	 * 培训(Training)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getTrainingInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		retrunList = empInfoDao.getTrainingInfoList(paramMap) ;
		return retrunList ;
	}
	
	/**
	 * 添加培训(add Training)
	 * @param request
	 * @return int
	 */
	@Override
	public int addTrainingInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getPersonId());
			int count=Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				LinkedHashMap trainingMap = ObjectBindUtil.getRequestParamData(request) ;
				if(request.getParameter("COURSE_NAME"+i)!=null){
					trainingMap.put("COURSE_NAME", request.getParameter("COURSE_NAME"+i));//课程名
					trainingMap.put("MUST_CODE", request.getParameter("MUST_CODE"+i));//选择必选
					trainingMap.put("INSTITUTION_NAME", request.getParameter("INSTITUTION_NAME"+i));//培训机关
					trainingMap.put("START_DATE", request.getParameter("START_DATE"+i));//开始时间
					trainingMap.put("END_DATE", request.getParameter("END_DATE"+i));//结束时间
					trainingMap.put("TRAINING_RESULT", request.getParameter("TRAINING_RESULT"+i));//培训日期
				
					trainingMap.put("TRAINING_METHOD", request.getParameter("TRAINING_METHOD"+i));
					trainingMap.put("REMARKS", request.getParameter("REMARKS"+i));
					trainingMap.put("TRAINING_DIFFERENTIATE", request.getParameter("TRAINING_DIFFERENTIATE"+i));
					trainingMap.put("TRAINING_TIME", request.getParameter("TRAINING_TIME"+i));
					this.empInfoDao.addTrainingInfo(trainingMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除培训信息(delete training information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteTrainingInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			String[] paramData = request.getParameterValues("TN");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("TRAIN_NO", paramData[i]);
						this.empInfoDao.deleteTrainingInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	
	/**
	 * 社会关系(social relations)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getFamilyList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("FAM_TYPE_CODE", "1693");		//等于1693的都是朋友
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		retrunList = empInfoDao.getFamilyList(paramMap) ;
		return retrunList ;
	}
	
	/**
	 * 添加紧急联系人(add FamilyInfo)
	 * @param request
	 * @return int
	 */
	@Override
	public int addEmergencyAddressInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CREATED_IP", admin.getAdminIP());
			int count=Integer.parseInt(request.getParameter("count"));
			
				this.empInfoDao.addEmergencyAddressInfo(paramMap);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除紧急联系地址(delete famliy information)
	 * @param obj
	 * @return int
	 */
	@Override  
	public int deleteEmergencyAddressInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			String[] paramData = request.getParameterValues("FN");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("FAMILY_NO", paramData[i]);
						this.empInfoDao.deleteFamilyInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 修改紧急联系地址(Modify family information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int editEmergencyAddressInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			
						this.empInfoDao.editEmergencyAddressInfo(paramMap);
				
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	/**
	 * 添加社会关系(add FamilyInfo)
	 * @param request
	 * @return int
	 */
	@Override
	public int addFamilyInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			int count=Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				paramMap.put("FAM_TYPE_CODE", request.getParameter("FAM_TYPE_CODE"+i));
				paramMap.put("FAM_NAME", request.getParameter("FAM_NAME"+i));
				paramMap.put("FAM_IDCARD", request.getParameter("FAM_IDCARD"+i));
				paramMap.put("FAM_BORNDATE", request.getParameter("FAM_BORNDATE"+i));
				paramMap.put("FAM_ADDRESS", request.getParameter("FAM_ADDRESS"+i));
				paramMap.put("FAM_PHONE", request.getParameter("FAM_PHONE"+i));
				paramMap.put("FAM_COMPANY_NAME", request.getParameter("FAM_COMPANY_NAME"+i));
				paramMap.put("LIVE_YN", request.getParameter("LIVE_YN"+i));
				paramMap.put("EMERGENCY_CONTACT_YN", request.getParameter("EMERGENCY_CONTACT_YN"+i));
				paramMap.put("FAM_PERSON_ID", request.getParameter("FAM_PERSON_ID"+i));
				this.empInfoDao.addFamilyInfo(paramMap);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除社会信息(delete famliy information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteFamilyInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			String[] paramData = request.getParameterValues("FN");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("FAMILY_NO", paramData[i]);
						this.empInfoDao.deleteFamilyInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 修改社会信息(Modify family information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int editFamilyInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			String[] paramData = request.getParameterValues("FN");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("FAMILY_NO", paramData[i]);
						paramMap.put("FAM_TYPE_CODE", request.getParameter("FAM_TYPE_CODE_"+paramData[i]));
						paramMap.put("FAM_NAME", request.getParameter("FAM_NAME_"+paramData[i]));
						paramMap.put("FAM_IDCARD", request.getParameter("FAM_IDCARD_"+paramData[i]));
						paramMap.put("FAM_BORNDATE", request.getParameter("FAM_BORNDATE_"+paramData[i]));
						paramMap.put("FAM_ADDRESS", request.getParameter("FAM_ADDRESS_"+paramData[i]));
						paramMap.put("FAM_PHONE", request.getParameter("FAM_PHONE_"+paramData[i]));
						paramMap.put("FAM_COMPANY_NAME", request.getParameter("FAM_COMPANY_NAME_"+paramData[i]));
						paramMap.put("LIVE_YN", request.getParameter("LIVE_YN_"+paramData[i]));
						paramMap.put("EMERGENCY_CONTACT_YN", request.getParameter("EMERGENCY_CONTACT_YN_"+paramData[i]));
						paramMap.put("FAM_PERSON_ID", request.getParameter("FAM_PERSON_ID_"+paramData[i]));
						this.empInfoDao.editFamilyInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 家人关系(social relations)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getHomeRelationList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		paramMap.put("FAMILY_NO",request.getParameter("FAMILY_NO"));
		returnList = empInfoDao.getHomeRelationList(paramMap) ;
		return returnList ;
	}
	/**
	 * 紧急联系地址
	 */
	@Override
	public List getEmergencyAddressList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		paramMap.put("EMERGENCY_NO",request.getParameter("EMERGENCY_NO"));
		returnList = empInfoDao.getEmergencyAddressList(paramMap) ;
		return returnList ;
	}
	/**
	 * 地址信息
	 */
	@Override
	public List getAddressList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		//paramMap.put("ADDRESS_NO",Integer.getInteger(request.getParameter("ADDRESS_NO")));	
		
		
		paramMap.put("INTERFACE", "INTERFACE");		//等于950的都是家人
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		returnList = empInfoDao.getAddressList(paramMap) ;
		return returnList ;
	}
	
	
	@Override
	public Object getAddressInfo(HttpServletRequest request) {
		Object returnList = new Object() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		//paramMap.put("ADDRESS_NO",Integer.getInteger(request.getParameter("ADDRESS_NO")));	
		paramMap.put("PERSON_ID",admin.getPersonId());

		
		
		returnList = empInfoDao.getAddressInfo(paramMap) ;
		return returnList ;
	}
	
	/**
	 * 添加家人关系(add FamilyInfo)
	 * @param request
	 * @return int
	 */
	@Override
	public int addHomeRelationInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getPersonId());
			if(paramMap.get("PERSON_ID")==null||paramMap.get("PERSON_ID")==""){
				paramMap.put("PERSON_ID",admin.getAdminID() );
			}
			paramMap.put("CREATED_IP", admin.getAdminIP());

				this.empInfoDao.addHomeRelationInfo(paramMap);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	
	/**
	 * 删除家人信息(delete homerelation information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteHomeRelationInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			String[] paramData = request.getParameterValues("FN");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("FAMILY_NO", paramData[i]);
						this.empInfoDao.deleteHomeRelationInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 修改家人信息(Modify family information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int editHomeRelation(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getPersonId());
			
			
			if(paramMap.get("PERSON_ID")==null){
				paramMap.put("PERSON_ID",admin.getAdminID() );
			}
						this.empInfoDao.editHomeRelation(paramMap);
				
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}	

	/**
	 * 修改培训信息(Modify trainging information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int editTrainingInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			String[] paramData = request.getParameterValues("TNO");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("TRAIN_NO", paramData[i]);
						paramMap.put("COURSE_NAME", request.getParameter("COURSE_NAME_"+paramData[i]));
						paramMap.put("MUST_CODE", request.getParameter("MUST_CODE_"+paramData[i]));
						paramMap.put("INSTITUTION_NAME", request.getParameter("INSTITUTION_NAME_"+paramData[i]));
						paramMap.put("START_DATE", request.getParameter("START_DATE_"+paramData[i]));
						paramMap.put("END_DATE", request.getParameter("END_DATE_"+paramData[i]));
						paramMap.put("TRAINING_METHOD", request.getParameter("TRAINING_METHOD_"+paramData[i]));
						paramMap.put("REMARKS", request.getParameter("REMARKS_"+paramData[i]));
						paramMap.put("TRAINING_DIFFERENTIATE", request.getParameter("TRAINING_DIFFERENTIATE_"+paramData[i]));
						paramMap.put("TRAINING_TIME", request.getParameter("TRAINING_TIME_"+paramData[i]));
						paramMap.put("TRAINING_RESULT", request.getParameter("TRAINING_RESULT_"+paramData[i]));
						this.empInfoDao.editTrainingInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 健康信息(Health information)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getHealthList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		retrunList = empInfoDao.getHealthList(paramMap) ;
		return retrunList ;
	}
	
	/**
	 * 添加健康信息(add health information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int addHealthInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		try {
			int count=Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				if(request.getParameter("PHYSICAL_DATE"+i)!=null){
					paramMap.put("PHYSICAL_DATE", request.getParameter("PHYSICAL_DATE"+i));
					paramMap.put("PHYSICAL_TYPE_CODE", request.getParameter("PHYSICAL_TYPE_CODE"+i));
					paramMap.put("INDUSTRY_DISTINGUISH_CODE", request.getParameter("INDUSTRY_DISTINGUISH_CODE"+i));
					paramMap.put("EFFECTIVE_DATE", request.getParameter("EFFECTIVE_DATE"+i));
					paramMap.put("CHECK_YN", request.getParameter("CHECK_YN"+i));
					paramMap.put("GENERAL_HEALTH", request.getParameter("GENERAL_HEALTH"+i));
					paramMap.put("BLOOD_TYPE_CODE", request.getParameter("BLOOD_TYPE_CODE"+i));
					paramMap.put("HEALTH_CERTIFICATE_YN", request.getParameter("HEALTH_CERTIFICATE_YN"+i));
					paramMap.put("REMARK", request.getParameter("REMARK"+i));
					paramMap.put("FAM_TYPE_CODE", request.getParameter("FAM_TYPE_CODE"+i));
					this.empInfoDao.addHealthInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改健康信息(Modify health information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int editHealthInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			String[] paramData = request.getParameterValues("HNO");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("HEALTH_NO", paramData[i]);
						paramMap.put("PHYSICAL_DATE", request.getParameter("PHYSICAL_DATE_"+paramData[i]));
						paramMap.put("PHYSICAL_TYPE_CODE", request.getParameter("PHYSICAL_TYPE_CODE_"+paramData[i]));
						paramMap.put("INDUSTRY_DISTINGUISH_CODE", request.getParameter("INDUSTRY_DISTINGUISH_CODE_"+paramData[i]));
						paramMap.put("EFFECTIVE_DATE", request.getParameter("EFFECTIVE_DATE_"+paramData[i]));
						paramMap.put("CHECK_YN", request.getParameter("CHECK_YN_"+paramData[i]));
						paramMap.put("GENERAL_HEALTH", request.getParameter("GENERAL_HEALTH_"+paramData[i]));
						paramMap.put("BLOOD_TYPE_CODE", request.getParameter("BLOOD_TYPE_CODE_"+paramData[i]));
						paramMap.put("HEALTH_CERTIFICATE_YN", request.getParameter("HEALTH_CERTIFICATE_YN_"+paramData[i]));
						paramMap.put("REMARK", request.getParameter("REMARK_"+paramData[i]));
						this.empInfoDao.editHealthInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除健康信息(delete health information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteHealthInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			String[] paramData = request.getParameterValues("HN");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("HEALTH_NO", paramData[i]);
						this.empInfoDao.deleteHealthInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 工作经验(work experience)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public Object getWorkExperienceList(HttpServletRequest request) {
		Object  obj;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId() );
		}
		paramMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
		if(request.getParameter("WORK_EXPER_NO")!=null&&!request.getParameter("WORK_EXPER_NO").equals("")){
			
			obj= empInfoDao.getWorkExperienceList(paramMap) ;
		}else{
			obj= empInfoDao.getWorkExperienceList2(paramMap) ;

		}
		
		return obj;
	}
	
	
	/**
	 * 工作经验单个(work experience)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public Object getWorkExperienceInfo(HttpServletRequest request) {
		Object  obj;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
		if(request.getParameter("WORK_EXPER_NO")!=null&&!request.getParameter("WORK_EXPER_NO").equals("")){
			
			obj= empInfoDao.getWorkExperienceList(paramMap) ;
		}else{
			obj= empInfoDao.getWorkExperienceList2(paramMap) ;

		}
		
		return obj;
	}
	
	/**
	 * 产品信息得到(work experience)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public Object getProductInfo(HttpServletRequest request) {
		Object  obj;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		paramMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
			
			obj= empInfoDao.getProductInfo(paramMap) ;

		return obj;
	}
	

	/**
	 * 产品信息得到(work experience)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getProductInfoList(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		paramMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
			
			

		return  empInfoDao.getProductInfoList(paramMap) ;
	}
	
	/**
	 * 添加工作经验(add work experience information)
	 * @param request
	 * @return int
	 */
	@Override
	public int addWorkExperienceInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		try {
			int count=Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				if(request.getParameter("START_DATE"+i)!=null){
					paramMap.put("START_DATE", request.getParameter("START_DATE"+i));//开始时间
					paramMap.put("END_DATE", request.getParameter("END_DATE"+i));//结束时间
					paramMap.put("CPNY_NAME", request.getParameter("CPNY_NAME"+i));//工作单位
					paramMap.put("DEPT_NAME", request.getParameter("DEPT_NAME"+i));//
					paramMap.put("DUTY", request.getParameter("DUTY"+i));//职级
					paramMap.put("POSITION", request.getParameter("POSITION"+i));//职位
					paramMap.put("PAYROLL", request.getParameter("PAYROLL"+i));//工资待遇
					this.empInfoDao.addWorkExperienceInfo(paramMap);
				}
			}
			this.empInfoDao.updateEmployeeWorkExperience(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改工作经验(Modify work experience information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int editWorkExperienceInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			String[] paramData = request.getParameterValues("WNO");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("WORK_EXPER_NO", paramData[i]);
						paramMap.put("CPNY_NAME", request.getParameter("CPNY_NAME_"+paramData[i]));
						paramMap.put("POSITION", request.getParameter("POSITION_"+paramData[i]));
						paramMap.put("DUTY", request.getParameter("DUTY_"+paramData[i]));
						paramMap.put("DEPT_NAME", request.getParameter("DEPT_NAME_"+paramData[i]));
						paramMap.put("START_DATE", request.getParameter("START_DATE_"+paramData[i]));
						paramMap.put("END_DATE", request.getParameter("END_DATE_"+paramData[i]));
						paramMap.put("PAYROLL", request.getParameter("PAYROLL_"+paramData[i]));
						this.empInfoDao.editWorkExperienceInfo(paramMap);
				}
			}
			this.empInfoDao.updateEmployeeWorkExperience(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	/**
	 * 删除工作经历(delete WorkExpreienceInfo )
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteWorkExpreienceInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			String[] paramData = request.getParameterValues("WENO");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("WORK_EXPER_NO", paramData[i]);
						this.empInfoDao.deleteWorkExpreienceInfo(paramMap);
				}
			}
			if(paramMap.get("PERSON_ID")==null){
				paramMap.put("PERSON_ID",admin.getAdminID() );
			}
			this.empInfoDao.updateEmployeeWorkExperience(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 资格信息(Competence information)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getQualificationList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId() );
		}
		retrunList = empInfoDao.getQualificationList(paramMap) ;
		return retrunList ;
	}
	
	/**
	 * 资格信息(Competence information)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public Object getQualificationInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId() );
		}
		paramMap.put("QUAL_NO", request.getParameter("QUAL_NO"));

		
		return  empInfoDao.getQualificationInfo(paramMap) ;
	}

	/**
	 * 外国语(language level)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getLanguageLevelList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		retrunList = empInfoDao.getLanguageLevelList(paramMap) ;
		return retrunList ;
	}
	
	/**
	 * 添加资格信息(add competence information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int addCompetenceInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		try {
			int count=Integer.parseInt(request.getParameter("count"));
			//int countL=Integer.parseInt(request.getParameter("countL"));
			for (int i = 0; i < count; i++) {
				if(request.getParameter("QUAL_NAME"+i)!=null ){
					paramMap.put("QUAL_NAME", request.getParameter("QUAL_NAME"+i));
					paramMap.put("QUAL_CARD_NO", request.getParameter("QUAL_CARD_NO"+i));
					paramMap.put("QUAL_LEVEL", request.getParameter("QUAL_LEVEL"+i));
					paramMap.put("QUAL_GRADE", request.getParameter("QUAL_GRADE"+i));
					paramMap.put("QUAL_INSTITUTE", request.getParameter("QUAL_INSTITUTE"+i));
					paramMap.put("ACQUISITION_MODES", request.getParameter("ACQUISITION_MODES"+i));
					paramMap.put("DATE_OBTAINED", request.getParameter("DATE_OBTAINED"+i));
					paramMap.put("VALIDITY_DATE", request.getParameter("VALIDITY_DATE"+i));
					paramMap.put("QUAL_REMARK", request.getParameter("QUAL_REMARK"+i));
					this.empInfoDao.addQualificationInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改资格信息(Modify competence information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int editCompetenceInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			String[] paramData = request.getParameterValues("QNO");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("QUAL_NO", paramData[i]);
						paramMap.put("QUAL_NAME", request.getParameter("QUAL_NAME_"+paramData[i]));
						paramMap.put("QUAL_CARD_NO", request.getParameter("QUAL_CARD_NO_"+paramData[i]));
						paramMap.put("QUAL_LEVEL", request.getParameter("QUAL_LEVEL_"+paramData[i]));
						paramMap.put("QUAL_GRADE", request.getParameter("QUAL_GRADE_"+paramData[i]));
						paramMap.put("QUAL_INSTITUTE", request.getParameter("QUAL_INSTITUTE_"+paramData[i]));
						paramMap.put("ACQUISITION_MODES", request.getParameter("ACQUISITION_MODES_"+paramData[i]));
						paramMap.put("DATE_OBTAINED", request.getParameter("DATE_OBTAINED_"+paramData[i]));
						paramMap.put("VALIDITY_DATE", request.getParameter("VALIDITY_DATE_"+paramData[i]));
						paramMap.put("QUAL_REMARK", request.getParameter("QUAL_REMARK_"+paramData[i]));
						this.empInfoDao.editQualificationInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除资格信息(delete Competence )
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteCompetenceInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			String[] paramData = request.getParameterValues("QN");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("QUAL_NO", paramData[i]);
						this.empInfoDao.deleteQualicationInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 标签查询(QueryLabelInformationVolume)
	 * @param parent_code_no
	 * @return retrunList
	 */
	@Override
	public List getCodeList(String parent_code_no,HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("PARENT_CODE_NO", parent_code_no);
		retrunList = empInfoDao.getCodeList(paramMap) ;
		return retrunList ;
	}

	/**
	 * 特殊事项(Special matters)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getAdditionalList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		retrunList = empInfoDao.getAdditionalList(paramMap) ;
		return retrunList ;
	}

	/**
	 * 添加特殊事项(add special matters)
	 * @param obj
	 * @return int
	 */
	@Override
	public int addAdditionalInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		try {
			int count=Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				if(request.getParameter("EVENT_DATE"+i)!=null){
					paramMap.put("EVENT_DATE", request.getParameter("EVENT_DATE"+i));
					paramMap.put("INFO_TYPE_CODE", request.getParameter("INFO_TYPE_CODE"+i));
					paramMap.put("REMARK", request.getParameter("REMARK"+i));
					this.empInfoDao.addAdditionalInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改特殊事项(edit special matters)
	 * @param obj
	 * @return int
	 */
	@Override
	public int editAdditionalInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			//创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			
			String[] paramData = request.getParameterValues("AN");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("ADDITIONAL_NO", paramData[i]);
						paramMap.put("EVENT_DATE", request.getParameter("EVENT_DATE_"+paramData[i]));
						paramMap.put("INFO_TYPE_CODE", request.getParameter("INFO_TYPE_CODE_"+paramData[i]));
						paramMap.put("REMARK", request.getParameter("REMARK_"+paramData[i]));
						this.empInfoDao.editAdditionalInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;

	}

	/**
	 * 删除特殊事项(delete special matters)
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteAdditionalInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		try {
			
			String[] paramData = request.getParameterValues("AN");
			
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("ADDITIONAL_NO", paramData[i]);
						this.empInfoDao.deleteAdditionalInfo(paramMap);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	/**
	 * 账户(account)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getAccountList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getAccountList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getAccountList(paramMap) ;
		}
		
		return retrunList ;
		
	}

	
	/**
	 * 合同信息(contract)
	 * @param request
	 * @return retrunList
	 */
	public List getContractList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId() );
		}
		
		retrunList = empInfoDao.getContractList(paramMap) ;
		
		return retrunList ;
		
	}
	
	/**
	 * 档案信息(file)
	 * @param request
	 * @return retrunList
	 */
	public List getFileList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		retrunList = empInfoDao.getFileList(paramMap) ;
		
		return retrunList ;
		
	}

	
	/**
	 * 添加档案(add file)
	 * @param request
	 * @return int
	 */
	@Override
	public int addFileInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
//		System.out.println(request.getParameter("PERSON_ID"));
		try {
			
			int count=Integer.parseInt(request.getParameter("count"));
			
			for (int i = 0; i < count; i++) {
				if(request.getParameter("FILE_NO"+i)!=null){
					paramMap.put("FILE_NO", request.getParameter("FILE_NO"+i));
					paramMap.put("FILE_TYPE", request.getParameter("FILE_TYPE"+i));
					paramMap.put("FILE_RELATION", request.getParameter("FILE_RELATION"+i));
					paramMap.put("FILE_INTO_YN", request.getParameter("FILE_INTO_YN"+i));
					paramMap.put("FILE_DATE", request.getParameter("FILE_DATE"+i));
					paramMap.put("FILE_CONTENT", request.getParameter("FILE_CONTENT"+i));
					paramMap.put("FILE_AREA", request.getParameter("FILE_AREA"+i));
					paramMap.put("COST_END_DATE", request.getParameter("COST_END_DATE"+i));
					this.empInfoDao.addFileInfo(paramMap);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	/**
	 * 修改档案(modify file)
	 * @param request
	 * @return int
	 */
	@Override
	public int editFileInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			//创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			
			String[] paramData = request.getParameterValues("FEN");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("FILE_EMP_NO", paramData[i]);
						paramMap.put("FILE_NO", request.getParameter("FILE_NO_"+paramData[i]));
						paramMap.put("FILE_TYPE", request.getParameter("FILE_TYPE_"+paramData[i]));
						paramMap.put("FILE_RELATION", request.getParameter("FILE_RELATION_"+paramData[i]));
						paramMap.put("FILE_INTO_YN", request.getParameter("FILE_INTO_YN_"+paramData[i]));
						paramMap.put("FILE_DATE", request.getParameter("FILE_DATE_"+paramData[i]));
						paramMap.put("FILE_CONTENT", request.getParameter("FILE_CONTENT_"+paramData[i]));
						paramMap.put("FILE_AREA", request.getParameter("FILE_AREA_"+paramData[i]));
						paramMap.put("COST_END_DATE", request.getParameter("COST_END_DATE_"+paramData[i]));
						this.empInfoDao.editFileInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	/**
	 * 删除档案(delete file)
	 * @param request
	 * @return int
	 */
	@Override
	public int deleteFileInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		try {
			
			String[] paramData = request.getParameterValues("FEN");
			
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("FILE_EMP_NO", paramData[i]);
						this.empInfoDao.deleteFileInfo(paramMap);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	
	/**
	 * 出国信息(goabroad information)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getGoAbroadList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		List viewRegisterInfoList = this.empInfoDao.viewInfoList(paramMap, "viewRegisterInfoListESS");
		if(paramMap.get("SEQ") == null || "".equals(StringUtil.checkNull(paramMap.get("SEQ")))){
			if(viewRegisterInfoList!=null && viewRegisterInfoList.size() > 0){
				LinkedHashMap param = (LinkedHashMap)viewRegisterInfoList.get(0);
				paramMap.put("SEQ", param.get("SEQ"));
			}
		}else{
			for(int i=0;i<viewRegisterInfoList.size();i++){
				Map subMap = (LinkedHashMap)viewRegisterInfoList.get(i);
				if(StringUtil.checkNull(paramMap.get("SEQ")).equals(StringUtil.checkNull(subMap.get("SEQ")))){
					paramMap.put("SEQ", subMap.get("SEQ"));
					break;
				}
			}
		}
		paramMap.put("language", Messages.getLanguage(request));
		
		retrunList = empInfoDao.getGoAbroadList(paramMap) ;
		
		return retrunList ;
	}

	
	/**
	 * 添加出国信息(add goabroad information)
	 * @param request
	 * @return int
	 */
	@Override
	public int addGoAbroadInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));

		try {
			
			int count=Integer.parseInt(request.getParameter("count"));
			
			for (int i = 0; i < count; i++) {
				if(request.getParameter("COUNTRY"+i)!=null){
					paramMap.put("COUNTRY", request.getParameter("COUNTRY"+i));
					paramMap.put("START_DATE", request.getParameter("START_DATE"+i));
					paramMap.put("END_DATE", request.getParameter("END_DATE"+i));
					paramMap.put("COST", request.getParameter("COST"+i));
					paramMap.put("PURPOSE", request.getParameter("PURPOSE"+i));
					this.empInfoDao.addGoAbroadInfo(paramMap);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	
	/**
	 * 修改出国信息(modify goabroad information)
	 * @param request
	 * @return int
	 */
	@Override
	public int editGoAbroadInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			//创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			
			String[] paramData = request.getParameterValues("NO");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("NO", paramData[i]);
						paramMap.put("FILE_NO", request.getParameter("FILE_NO_"+paramData[i]));
						paramMap.put("COUNTRY", request.getParameter("COUNTRY_"+paramData[i]));
						paramMap.put("START_DATE", request.getParameter("START_DATE_"+paramData[i]));
						paramMap.put("END_DATE", request.getParameter("END_DATE_"+paramData[i]));
						paramMap.put("COST", request.getParameter("COST_"+paramData[i]));
						paramMap.put("PURPOSE", request.getParameter("PURPOSE_"+paramData[i]));
						this.empInfoDao.editGoAbroadInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	
	/**
	 * 删除出国信息(delete goabroad information)
	 * @param request
	 * @return int
	 */
	@Override
	public int deleteGoAbroadInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		try {
			
			String[] paramData = request.getParameterValues("NO");
			
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("NO", paramData[i]);
						this.empInfoDao.deleteGoAbroadInfo(paramMap);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}


	
	/**
	 * 证照信息(credential information)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getCredentialList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		retrunList = empInfoDao.getCredentialList(paramMap) ;
		
		return retrunList ;
	}


	
	
	/**
	 * 添加证照信息(add credential)
	 * @param request
	 * @return int
	 */
	@Override
	public int addCredentialInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));

		try {
			
			int count=Integer.parseInt(request.getParameter("count"));
			
			for (int i = 0; i < count; i++) {
				if(request.getParameter("CREDENTIAL_TYPE"+i)!=null){
					paramMap.put("CREDENTIAL_TYPE", request.getParameter("CREDENTIAL_TYPE"+i));
					paramMap.put("CREDENTIAL_NO", request.getParameter("CREDENTIAL_NO"+i));
					paramMap.put("CREDENTIAL_SOURCE", request.getParameter("CREDENTIAL_SOURCE"+i));
					paramMap.put("CREDENTIAL_BEGIN_DATE", request.getParameter("CREDENTIAL_BEGIN_DATE"+i));
					paramMap.put("CREDENTIAL_END_DATE", request.getParameter("CREDENTIAL_END_DATE"+i));
					paramMap.put("REMARK", request.getParameter("REMARK"+i));
					
					this.empInfoDao.addCredentialInfo(paramMap);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	

	/**
	 * 修改证照信息(modify credential information)
	 * @param request
	 * @return int
	 */
	@Override
	public int editCredentialInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			//创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			
			String[] paramData = request.getParameterValues("CN");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("CRED_NO", paramData[i]);
						paramMap.put("FILE_NO", request.getParameter("FILE_NO_"+paramData[i]));
						paramMap.put("CREDENTIAL_TYPE", request.getParameter("CREDENTIAL_TYPE_"+paramData[i]));
						paramMap.put("CREDENTIAL_NO", request.getParameter("CREDENTIAL_NO_"+paramData[i]));
						paramMap.put("CREDENTIAL_SOURCE", request.getParameter("CREDENTIAL_SOURCE_"+paramData[i]));
						paramMap.put("CREDENTIAL_BEGIN_DATE", request.getParameter("CREDENTIAL_BEGIN_DATE_"+paramData[i]));
						paramMap.put("CREDENTIAL_END_DATE", request.getParameter("CREDENTIAL_END_DATE_"+paramData[i]));
						paramMap.put("REMARK", request.getParameter("REMARK_"+paramData[i]));
						this.empInfoDao.editCredentialInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	
	/**
	 * 删除证照信息(delete credential information)
	 * @param request
	 * @return int
	 */
	@Override
	public int deleteCredentialInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		try {
			
			String[] paramData = request.getParameterValues("CN");
			
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("CRED_NO", paramData[i]);
						this.empInfoDao.deleteCredentialInfo(paramMap);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
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
			retrunList = empInfoDao.getEmpIdList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getEmpIdList(paramMap) ;
		}
		
		return retrunList ;

	}

	

	/**
	 * 获取员工信息个数(For the number of staff information)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEmpIdListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("specialParam",admin.getSpecialParam());
		paramMap.put("deptNo",admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		retrunInt = empInfoDao.getEmpIdListCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	/**
	 * 根据EMPID查询出人员信息条数(EMPID inquires according to the number of personnel article information)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getPersonCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("EMPID", request.getParameter("viewPersonalInfoHeadEmpId"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunInt = empInfoDao.getPersonCnt(paramMap) ;
		
		return retrunInt ;
	}

	
	
	
	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param parameterObject
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@Override
	public List getPidEidList(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		paramMap.put("specialParam",admin.getSpecialParam());
		
		paramMap.put("deptNo",admin.getDeptNo());
		
		paramMap.put("userNo", admin.getUserNo());

		if (UiUtil.getPageNum(request) > 0){
			retrunList = empInfoDao.getPidEidList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getPidEidList(paramMap) ;
		}
		
		return retrunList ;
	}
	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param parameterObject
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@Override
	public List getPidEidListXiao(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;
 
		Map paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		
		 
		if (UiUtil.getPageNum(request) > 0){
			retrunList = empInfoDao.getPidEidListXiao(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getPidEidListXiao(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * @param parameterObject
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@Override
	public List getPidEidList2(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		paramMap.put("specialParam",admin.getSpecialParam());
		
		paramMap.put("deptNo",admin.getDeptNo());
		
		paramMap.put("userNo", admin.getUserNo());

		if (UiUtil.getPageNum(request) > 0){
			retrunList = empInfoDao.getPidEidList2(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getPidEidList2(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * 查询old(薪资)职级,号俸(Inquires the old (salary) rank, no pay) 
	 * @param request
	 * @return
	 */
	@Override
	public List getOldPostGradeList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		retrunList = empInfoDao.getOldPostGradeList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		
		return retrunList ;
	}
	
	/**
	 * 查询代理职级(Inquires the agency rank) 
	 * @param request
	 * @return
	 */
	@Override
	public List getPostGradeList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		retrunList = empInfoDao.getPostGradeList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList ;
	}

	@Override
	public int editPhotoPath(HttpServletRequest request,String path) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		paramMap.put("PHOTO_PATH", path + request.getParameter("CPNY_ID") + "/" 
				+ request.getParameter("CPNY_ID") + request.getParameter("EMPID")+".jpg");
		
		try {
			this.empInfoDao.editPhotoPath(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
		
	}

	@Override
	public List getOrderParmList(HttpServletRequest request,String sortNameNo) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("SORT_TYPE_NO", sortNameNo);
		retrunList = empInfoDao.getOrderParmList(paramMap) ;
		
		return retrunList ;
	}
	/**
	 * 获取号俸列表
	 */
	@Override
	public List getHaoFengLists(HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		retrunList = empInfoDao.getHaoFengList(paramMap);
		return retrunList;
	}
	
	/**
	 *  基本信息的子菜单查询
	 * @param  
	 * @return  
	 */
	@Override
	public List getMenuThirdListList(String menu_code,HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USERNO", admin.getUserNo());
		paramMap.put("interLanguage",  Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
	 
			retrunList = empInfoDao.getMenuThirdListList(paramMap) ;
		 
		
		return retrunList ;
	}
	
	/**
	 * 根据法人获取公司人员信息    导出模板使用
	 */
	@Override
	public List getEmpListToModelExcel(HttpServletRequest request) {
		Map paramMap = new HashMap();
		paramMap.put("STATUS_CODE", "1375");//离职
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getAdminID()!=null?admin.getAdminID().toString():"");
		paramMap.put("language", admin.getLanguage());
		paramMap.put("DEPTNO", request.getParameter("seach_DEPTNO")!=null?request.getParameter("seach_DEPTNO").toString():(
				request.getParameter("DEPTNO")!=null?request.getParameter("DEPTNO"):""));
		return empInfoDao.getEmpListToModelExcel(paramMap) ;
	}

	
	/**
	 * 查询ess tab菜单
	 */
	@Override
	public List getTabMenuListList(String menu_code, HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USERNO", admin.getUserNo());
		paramMap.put("interLanguage",  Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
	 
			retrunList = empInfoDao.getTabMenuListList(paramMap) ;
		 
		
		return retrunList ;
	}

	//残疾信息
	public List getDisabilityinfoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getDisabilityinfoList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getDisabilityinfoList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * 添加残疾信息
	 */
	@Override
	public int addDisabledInfo(HttpServletRequest request) {
		//页面提交数据
				LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
				//创建人
				HttpSession session = request.getSession();
				AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
				paramMap.put("CREATED_BY", admin.getPersonId());
				paramMap.put("CPNY_ID", admin.getCpnyId());

				if(paramMap.get("PERSON_ID")==null){
					paramMap.put("PERSON_ID",admin.getAdminID() );
				}

				try {
					int count=Integer.parseInt(request.getParameter("count"));
				
					for (int i = 0; i < count; i++) {
						if(request.getParameter("DISABILITY_TYPE_NAME"+i)!=null){
						
						paramMap.put("DISABILITY_TYPE_NAME", request.getParameter("DISABILITY_TYPE_NAME"+i));
						paramMap.put("ADDDATE", request.getParameter("ADDDATE"+i));
						String DISABILITY_VALIDITY=request.getParameter("DISABILITY_VALIDITY"+i).toString();
						paramMap.put("DISABILITY_VALIDITY", DISABILITY_VALIDITY);
						int j=0;
						switch(Integer.parseInt(DISABILITY_VALIDITY))
						{
						case 123463 :j=1; break;
						case 123464 :j=2; break;
						case 123465 :j=3; break;
						case 123466 :j=4; break;
						case 123467 :j=5; break;
						case 123468 :j=6; break;
						case 123469 :j=7; break;
						case 123470 :j=8; break;
						case 123471 :j=9; break;
						case 123472 :j=10; break;
						}
						
						paramMap.put("QUITDATE", j*12);
						
						paramMap.put("REMARK", request.getParameter("REMARK"+i));
						
						this.empInfoDao.addDisabledInfo(paramMap);
						/*int disabledCnt=this.empInfoDao.getdisabledCnt(paramMap);
							if(disabledCnt>0){
								paramMap.put("DISABLED_OR_NOT","Y");
							}else{
								paramMap.put("DISABLED_OR_NOT","N");
							}
							this.empInfoDao.updateEmployeeDisabled(paramMap);//修改基本中的残疾状态*/
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
				
				return 1;
	}

	/**
	 * 删除残疾信息
	 */
	@Override
	public int deleteDisabledInfo(HttpServletRequest request) {
		//页面提交数据
				LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
				//创建人
				HttpSession session = request.getSession();
				AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
				
				try {
					
					String[] paramData = request.getParameterValues("CJ");
					
					if(paramData !=null){
						for (int i = 0; i < paramData.length; i++) {
								paramMap.put("T_ID", paramData[i]);
								this.empInfoDao.deleteDisabledInfo(paramMap);
						}
					}
					
					
//					if(request.getParameter("PERSON_ID")==null){
//						paramMap.put("PERSON_ID",admin.getAdminID() );
//					}else{
//						paramMap.put("PERSON_ID",request.getParameter("PERSON_ID") );
//					}
//					int disabledCnt=this.empInfoDao.getdisabledCnt(paramMap);
//					if(disabledCnt>0){
//						paramMap.put("DISABLED_OR_NOT","Y");
//					}else{
//						paramMap.put("DISABLED_OR_NOT","N");
//					}
//					this.empInfoDao.updateEmployeeDisabled(paramMap);//修改基本中的残疾状态
					
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
				
				return 1;
	}

	/**
	 * 修改残疾信息
	 */
	@Override
	public int editDisabledInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			//创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			
			String[] paramData = request.getParameterValues("HNO");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("T_ID", paramData[i]);
						paramMap.put("DISABILITY_TYPE_NAME", request.getParameter("DISABILITY_TYPE_NAME_"+paramData[i]));
						paramMap.put("ADDDATE", request.getParameter("ADDDATE_"+paramData[i]));
						String DISABILITY_VALIDITY=request.getParameter("DISABILITY_VALIDITY_"+paramData[i]).toString();
						paramMap.put("DISABILITY_VALIDITY", DISABILITY_VALIDITY);
						int j=0;
						switch(Integer.parseInt(DISABILITY_VALIDITY))
						{
						case 123463 :j=1; break;
						case 123464 :j=2; break;
						case 123465 :j=3; break;
						case 123466 :j=4; break;
						case 123467 :j=5; break;
						case 123468 :j=6; break;
						case 123469 :j=7; break;
						case 123470 :j=8; break;
						case 123471 :j=9; break;
						case 123472 :j=10; break;
						}
						
						paramMap.put("QUITDATE", j*12);
						paramMap.put("REMARK", request.getParameter("REMARK_"+paramData[i]));
						
						this.empInfoDao.editDisabledInfo(paramMap);
				}
			}
//			int disabledCnt=this.empInfoDao.getdisabledCnt(paramMap);
//			if(disabledCnt>0){
//				paramMap.put("DISABLED_OR_NOT","Y");
//			}else{
//				paramMap.put("DISABLED_OR_NOT","N");
//			}
//			this.empInfoDao.updateEmployeeDisabled(paramMap);//修改基本中的残疾状态
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	/**
	 * 添加工会信息(add addTradeunionInfo)
	 */
	@Override
	public int addTradeunionInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("USERID", request.getParameter("PERSON_ID"));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		try {
			
			
			int count = Integer.parseInt(request.getParameter("count"));

			for (int i = 0; i < count; i++) {
			paramMap.put("PHYSICAL_TYPE_CODE", request
					.getParameter("PHYSICAL_TYPE_CODE"+i));
			paramMap.put("TRADEUNION_ADDDATE", request
					.getParameter("TRADEUNION_ADDDATE"+i));
			paramMap.put("TRADEUNION_QUITDATE", request
					.getParameter("TRADEUNION_QUITDATE"+i));
			paramMap.put("TRADEUNION_REMARK", request
					.getParameter("TRADEUNION_REMARK"+i));
			

			paramMap.put("PAY_FLAG", request
					.getParameter("PAY_FLAG"+i));
			paramMap.put("PAY_TYPE", request
					.getParameter("PAY_TYPE"+i));
			// paramMap.put("USERID", request.getParameter("PERSON_ID"));
			paramMap.put("CPNYID", admin.getCpnyId());
			this.empInfoDao.addTradeunionInfo(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	/**
	 * 删除工会信息(add deleteTradeunionInfo)
	 */
	@Override
	public int deleteTradeunionInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		try {

			String[] paramData = request.getParameterValues("TID");

			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("TID", paramData[i]);
					this.empInfoDao.deleteTradeunionInfo(paramMap);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	/**
	 * 修改工会信息(add updateTradeunionInfo)
	 */
	@Override
	public int editTradeunionInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			// 创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			// paramMap.put("UPDATED_DATE",new Date());

			String[] paramData = request.getParameterValues("TID");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("TID", paramData[i]);
					paramMap.put("RES",
							request.getParameter("PHYSICAL_TYPE_CODE0_"
									+ paramData[i]));
					paramMap.put("ADDDATE", request.getParameter("ADDDATE_"
							+ paramData[i]));
					if (request.getParameter("QUITDATE_" + paramData[i]) == null
							|| request.getParameter("QUITDATE_" + paramData[i])
									.equals("")) {
						paramMap.put("QUITDATE", null);
					} else {
						paramMap.put("QUITDATE", request
								.getParameter("QUITDATE_" + paramData[i]));
					}

					paramMap.put("PAY_FLAG", request.getParameter("PAY_FLAG_"
							+ paramData[i]));

					paramMap.put("PAY_TYPE", request.getParameter("PAY_TYPE_"
							+ paramData[i]));
					
					paramMap.put("REMARK", request.getParameter("REMARK_"
							+ paramData[i]));
					this.empInfoDao.editTradeunionInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}
	/**
	 * 添加外国语信息(add addLanguageInfo)
	 */
	@Override
	public int addLanguageInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));

		try {

			//int count = Integer.parseInt(request.getParameter("count"));
			int countL = Integer.parseInt(request.getParameter("countL"));

			

			for (int j = 0; j < countL; j++) {
				
					//paramMap.put("LANGUAGE_TYPE_CODE", request
						//	.getParameter("LANGUAGE_TYPE_CODE" + j));
					paramMap.put("EXAM_NAME_CODE", request
							.getParameter("EXAM_NAME_CODE" + j));
					//paramMap.put("QUALIFICATION_NAME", request
						//	.getParameter("QUALIFICATION_NAME" + j));
					paramMap.put("LANGUAGE_LEVEL_CODE", request
							.getParameter("LANGUAGE_LEVEL_CODE" + j));
					paramMap.put("MARK", request.getParameter("MARK" + j));
					paramMap.put("KAOSHIDATE", request
							.getParameter("KAOSHIDATE" + j));
					paramMap.put("ALLWANCE", request
							.getParameter("ALLWANCE" + j));
					this.empInfoDao.addLanguageLevelInfo(paramMap);// 123
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}
	/**
	 * 修改外国语信息(add updateLanguageInfo)
	 */
	@Override
	public int updateLanguageInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			// 创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			String[] paramDataL = request.getParameterValues("LNO");
			if (paramDataL != null) {
				for (int i = 0; i < paramDataL.length; i++) {
					paramMap.put("LANGUAGE_NO", paramDataL[i]);
					paramMap.put("LANGUAGE_TYPE_CODE",
							request.getParameter("LANGUAGE_TYPE_CODE_"
									+ paramDataL[i]));
					paramMap.put("EXAM_NAME_CODE", request
							.getParameter("EXAM_NAME_CODE_" + paramDataL[i]));
					paramMap.put("QUALIFICATION_NAME",
							request.getParameter("QUALIFICATION_NAME_"
									+ paramDataL[i]));
					paramMap.put("LANGUAGE_LEVEL_CODE", request
							.getParameter("LANGUAGE_LEVEL_CODE_"
									+ paramDataL[i]));
					paramMap.put("MARK", request.getParameter("MARK_"
							+ paramDataL[i]));
					paramMap.put("DATE_OBTAINED", request
							.getParameter("DATE_OBTAINED_" + paramDataL[i]));
					paramMap.put("KAOSHIDATE", request
							.getParameter("KAOSHIDATE_" + paramDataL[i]));
					paramMap.put("ALLWANCE", request
							.getParameter("ALLWANCE_" + paramDataL[i]));
					this.empInfoDao.editLanguageLevelInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}
	/**
	 * 删除外国语信息(delete Language )
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteLanguageInfo(HttpServletRequest request) {

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		try {
			String[] paramDataL = request.getParameterValues("LN");

			if (paramDataL != null) {
				for (int i = 0; i < paramDataL.length; i++) {
					paramMap.put("LANGUAGE_NO", paramDataL[i]);
					this.empInfoDao.deleteLanguageLevelInfo(paramMap);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}



	

	/**
	 * 修改毕业学校
	 */
	@Override
	public int editEducation(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			//创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			int biaoji=0;
			String[] paramData = request.getParameterValues("HNO");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("EDUC_NO", paramData[i]);//id
					paramMap.put("INSTITUTION_NAME", request.getParameter("INSTITUTION_NAME_"+paramData[i]));//学校名
					paramMap.put("SUBJECT_CLASSIFY", request.getParameter("SUBJECT_CLASSIFY_"+paramData[i]));//专业分类
					paramMap.put("SUBJECT", request.getParameter("SUBJECT_"+paramData[i]));//专业
					paramMap.put("SUBJECT_CLASSIFY_TWO", request.getParameter("SUBJECT_CLASSIFY_TWO_"+paramData[i]));//第二专业分类
					paramMap.put("SUBJECT_SECOND", request.getParameter("SUBJECT_SECOND_"+paramData[i]));//第二专业
					paramMap.put("START_DATE", request.getParameter("START_YEAR_"+paramData[i])+"-"+request.getParameter("START_MONTH_"+paramData[i]));//开始时间
					paramMap.put("END_DATE", request.getParameter("END_YEAR_"+paramData[i])+"-"+request.getParameter("END_MONTH_"+paramData[i]));//结束时间
					paramMap.put("DEGREE_CODE", request.getParameter("DEGREE_CODE_"+paramData[i]));//学历
					//paramMap.put("PARTICULAR_DEGREE", request.getParameter("PARTICULAR_DEGREE_"+paramData[i]));//详细学历
					//paramMap.put("SCHOOL_ADDRESS", request.getParameter("SCHOOL_ADDRESS_"+paramData[i]));//所在地
					paramMap.put("SITE_PROVINCE", request.getParameter("SITE_PROVINCE_"+paramData[i]));//所在地省CODE
					paramMap.put("SITE_CITY", request.getParameter("SITE_CITY_"+paramData[i]));//所在地市CODE
					paramMap.put("FINAL_DEGREE_WHETHER", request.getParameter("FINAL_DEGREE_WHETHER_"+paramData[i]));//是否最终学历
					paramMap.put("REMARKS", request.getParameter("REMARKS_"+paramData[i]));//所在地
					this.empInfoDao.editEducation(paramMap);
					
					if(request.getParameter("FINAL_DEGREE_WHETHER_"+paramData[i]).toString().equals("Y")){
						 biaoji=1;
						this.empInfoDao.updataEduaction(paramMap);//修改员工个人信息表最终学历信息
					}
				}
				if(biaoji==0){//当修改的信息中没有 最终学历信息时
					if(this.empInfoDao.getEduactionY(paramMap)==0){//判断用户是否有最终学历 没有将基本表中的信息设置为空
					     paramMap.put("DEGREE_CODE", "");
						 paramMap.put("INSTITUTION_NAME", "");
						 paramMap.put("SUBJECT", "");
						this.empInfoDao.updataEduaction(paramMap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;

	}
	public Object getPersonalInfoForInformation(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		
		if(request.getParameter("status_code_emp")!=null){
			if(request.getParameter("status_code_emp").equals("0")){
				paramMap.put("STATUS", null);
			}else{
				paramMap.put("STATUS", "abc");
			}
		}
		String CPNYSTATUS=request.getParameter("CPNYSTATUS");
		
		if(CPNYSTATUS==null){
			paramMap.put("CPNYSTATUS", null);
		}
		else if(request.getParameter("seach_DEPTNO")==null||request.getParameter("seach_DEPTNO").equals("")){
			paramMap.put("CPNYSTATUS", null);
		}else{
			paramMap.put("CPNYSTATUS",  request.getParameter("seach_DEPTNO"));
		}
		if(paramMap.get("CPNYSTATUS")!=null){
			paramMap.put("DEPTNO", null);
		}else{
			paramMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getPersonalInfoForInformation(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getPersonalInfoForInformation(paramMap) ;
		}
		return retrunList;
	}
	public Object getPersonalInfoForInformationCount(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		if(request.getParameter("status_code_emp")!=null){
			if(request.getParameter("status_code_emp").equals("0")){
				paramMap.put("STATUS", null);
			}else{
				paramMap.put("STATUS", "abc");
			}
		}
		String CPNYSTATUS=request.getParameter("CPNYSTATUS");
		
		if(CPNYSTATUS==null){
			paramMap.put("CPNYSTATUS", null);
		}
		else if(request.getParameter("seach_DEPTNO")==null||request.getParameter("seach_DEPTNO").equals("")){
			paramMap.put("CPNYSTATUS", null);
		}else{
			paramMap.put("CPNYSTATUS",  request.getParameter("seach_DEPTNO"));
		}
		if(paramMap.get("CPNYSTATUS")!=null){
			paramMap.put("DEPTNO", null);
		}else{
			paramMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		}
		
		
		return empInfoDao.getPersonalInfoForInformationCount(paramMap);
	}

	/**
	 * 统计用户是否有最终学历
	 */
	@Override
	public int getFinalNum(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		return empInfoDao.getFinalNum(paramMap);
	}

	/**
	 * 通过人力关联表查询出关联的下拉列表 hr_human_relevance
	 */
	@Override
	public List getRelevance(HttpServletRequest request) {
			List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CODE_ONE", request.getParameter("CODE_ONE"));
		paramMap.put("CODE_TWO", request.getParameter("CODE_TWO"));
		paramMap.put("CODE_THREE", request.getParameter("CODE_THREE"));
		
		retrunList = empInfoDao.getRelevance(paramMap) ;
		
		return retrunList ;
	}
	@Override
	public int addTestInfo(HttpServletRequest request) throws SQLException{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("ID", request.getParameter("addTestZhutest0000"));
		paramMap.put("CON", request.getParameter("addTestCon_test0000"));
		paramMap.put("NUM", request.getParameter("addTestNum_test0000"));
		empInfoDao.addTestInfo(paramMap);
		return 1;
	}
	
	@Override
	public String getJoinFlagByPersonId(String personId) throws SQLException{
		return empInfoDao.getJoinFlagByPersonId(personId);
	}

	@Override
	public int addBadArchivesInfo(HttpServletRequest request) throws SQLException {// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		try {
			int count = Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				paramMap.put("PERSON_ID", request.getParameter("dwz.person.personId"));
				paramMap.put("PERSON_ID_FILE", admin.getPersonId());
				if (paramMap.get("PERSON_ID") == null) {
					return 0;
				}
				String FILE_URL =  StringUtil.checkNull(paramMap.get("fileUrl"));
				String FILE_NAME = StringUtil.checkNull(paramMap.get("fileName"));
				paramMap.put("HAPPEN_DATE", request.getParameter("HAPPEN_DATE"+i));
				paramMap.put("ARCHIVES_TYPE", request.getParameter("ARCHIVES_TYPE"+i));
				paramMap.put("DETAIL_DESCRIPT", request.getParameter("DETAIL_DESCRIPT"+i));
				paramMap.put("FILE_NAME", FILE_NAME);
				paramMap.put("FILE_URL", FILE_URL);
				paramMap.put("REMARK", request.getParameter("REMARK"+i));
				this.empInfoDao.addBadArchivesInfo(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteBadArchivesInfo(HttpServletRequest request)
			throws SQLException {// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			/*String[] paramDataL = request.getParameterValues("BAID");
			if (paramDataL != null) {
				for (int i = 0; i < paramDataL.length; i++) {
					paramMap.put("ID", paramDataL[i]);
					this.empInfoDao.deleteBadArchivesInfo(paramMap);
				}
			}*/
			paramMap.put("ID", paramMap.get("ARCH_ID"));
			paramMap.put("APPLY_NO", paramMap.get("ARCH_ID"));
			paramMap.put("APPLY_TYPE","0");
			this.empInfoDao.deleteBadArchivesInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int editBadArchivesInfo(HttpServletRequest request) throws SQLException {
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getPersonId());
			/*String[] paramDataL = request.getParameterValues("BAID");
			if (paramDataL != null) {
				for (int i = 0; i < paramDataL.length; i++) {
					paramMap.put("ID", paramDataL[i]);
					paramMap.put("HAPPEN_DATE", request.getParameter("HAPPEN_DATE_" + paramDataL[i]));
					paramMap.put("ARCHIVES_TYPE", request.getParameter("ARCHIVES_TYPE_" + paramDataL[i]));
					paramMap.put("DETAIL_DESCRIPT", request.getParameter("DETAIL_DESCRIPT_" + paramDataL[i]));
					paramMap.put("FILE_URL", request.getParameter("FILE_URL_"+ paramDataL[i]));
					paramMap.put("REMARK", request.getParameter("REMARK_"+ paramDataL[i]));
					this.empInfoDao.editBadArchivesInfo(paramMap);
				}
			}*/
			int count = Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				paramMap.put("PERSON_ID_FILE", admin.getPersonId());
				String FILE_URL =  StringUtil.checkNull(paramMap.get("fileUrl"));
				String FILE_NAME = StringUtil.checkNull(paramMap.get("fileName"));
				paramMap.put("REMARK", request.getParameter("REMARK"+i));
				paramMap.put("ID", request.getParameter("ID"));
				paramMap.put("HAPPEN_DATE", request.getParameter("HAPPEN_DATE"+i));
				paramMap.put("ARCHIVES_TYPE", request.getParameter("ARCHIVES_TYPE"+i));
				paramMap.put("DETAIL_DESCRIPT", request.getParameter("DETAIL_DESCRIPT"+i));
				paramMap.put("FILE_NAME", FILE_NAME);
				paramMap.put("FILE_URL", FILE_URL);
				paramMap.put("REMARK", request.getParameter("REMARK"+i));
				this.empInfoDao.editBadArchivesInfo(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List getBadArchivesList(HttpServletRequest request) throws SQLException {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = empInfoDao.getBadArchivesList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getBadArchivesList(paramMap) ;
		}
		if(retrunList != null && retrunList.size() > 0){
			for(int i=0;i<retrunList.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)retrunList.get(i);
				returnMap.put("APPLY_NO", returnMap.get("ID"));
				returnMap.put("APPLY_TYPE", "0");
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		return retrunList ;
	}
	
	@Override
	public int getBadArchivesListCnt(HttpServletRequest request) {
		int count = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		count = empInfoDao.getBadArchivesListCnt(paramMap) ;
		return count ;
	}
	
	@Override
	public List getEmpInfoList(HttpServletRequest request)
			throws SQLException {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", admin.getLanguage());
		paramMap.put("CPNY_ID",  request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		paramMap.put("PERSON_ID",admin.getAdminID());
		paramMap.put("specialParam",admin.getSpecialParam());//这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo",admin.getDeptNo());//判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		//判断是否有超级管理员权限
		int authority = authorityUtil.isSuperUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "SuperUser");//超级用户权限
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getEmpInfoList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getEmpInfoList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	@Override
	public int getEmpInfoListCnt(HttpServletRequest request)
			throws SQLException {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID",  request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		paramMap.put("PERSON_ID",admin.getAdminID() );
		paramMap.put("specialParam",admin.getSpecialParam());//这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo",admin.getDeptNo());//判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		//判断是否有超级管理员权限
		int authority = authorityUtil.isSuperUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "SuperUser");//超级用户权限
		}
		
		return empInfoDao.getEmpInfoListCnt(paramMap) ;
	}
	
	/**
	 * 下载派遣津贴标准的模版
	 */
	public String getPaiQianDiJinTieBiaoZhunMoBanModleInfo(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String faRen = admin.getCpnyId();
		String codeSql = "SELECT distinct  NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		String faRenSql = " SELECT  g.cpny_id CONTENT  from hr_company g  ";
		String zhiZeSql1 = "SELECT   SY.CONTENT CONTENT FROM HR_POSITION    T, SY_GLOBAL_NAME SY  WHERE T.POSITION_NO = SY.NO(+)  AND SY.LANGUAGE(+) = 'zh' AND T.ACTIVITY = 1 ";
		String zhiZeSql = "SELECT DISTINCT POSITION_NO CONTENT FROM HR_EMPLOYEE HR WHERE HR.CPNY_ID = '" + faRen + "' AND POSITION_NO IS NOT NULL";
		String codeDqmcSql = " select distinct  m.region_nm CONTENT from INC_CITY_TO_CITYLEVEL_MAPP_DIS m ";
		String name = "";
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.zhiZe",
				request));// 职责
		aliasNameList.add(TipMessage
				.getTipMessage("pa.salary.canShu.chengShiDengJi", request));// 城市等级
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.diQuMingCheng", request));// 地区名称
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.shuZhi", request));// 数值
//		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.beiZhu",
//				request));// 备注

		String sqlDetailInfo = " select 'TSTO', 'Team Leader', 'P1', '福州', '8888' from dual ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

			//这些是sheet名字
			mapNameList.add("法人");
			mapNameList.add("职责");
			mapNameList.add("城市等级");
			mapNameList.add("地区名称");
			
			mapList.add(faRenSql);
			mapList.add(zhiZeSql);
			mapList.add(codeSql + "218067");
			mapList.add(codeDqmcSql);
			
			name = "paiQianDiJinTieBiaoZhunMoBanModle"; 
		return name;
	}
	/**
	 * 下载派遣地管理的模版
	 */
	public String getPaiQianDiGuanLiMoBanModleInfo(HttpServletRequest request,List aliasNameList, List list, 
			List mapList, List mapNameList)throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String codeSql = "SELECT NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		String faRenSql = "   SELECT g.cpny_id CONTENT  from hr_company g     ";
		String codeSfSql = " select distinct m.state_nm CONTENT from INC_CITY_TO_CITYLEVEL_MAPP_DIS m ";
		String codeCsmcSql = " select distinct m.city_nm CONTENT, state_nm PARENT_CODE from INC_CITY_TO_CITYLEVEL_MAPP_DIS m group by state_nm, city_nm order by state_nm, city_nm ";
		String codeDqmcSql = " select distinct m.region_nm CONTENT,m.city_nm PARENT_CODE from INC_CITY_TO_CITYLEVEL_MAPP_DIS m group by m.city_nm,m.region_nm order by m.city_nm,m.region_nm ";
		String name = "";
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.chengShiDengJi", request));// 城市等级
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.shengFen",request));// 省份
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.chengShiMingCheng", request));// 城市名称
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.diQuMingCheng", request));// 地区名称

		String cpnyId = admin.getCpnyId()!=null?admin.getCpnyId().toString():"TSTO";
		String sqlDetailInfo = " select '" + cpnyId +"', 'P1', '广东省', '广州市', '珠海区'  from dual ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}
		//这些是sheet名字
		mapNameList.add("法人");
		mapNameList.add("城市等级");
		mapNameList.add("省份");
		mapNameList.add("城市列表");
		mapNameList.add("地区列表");
		
		mapList.add(faRenSql);
		mapList.add(codeSql + " '218067' ");
		mapList.add(codeSfSql);
		mapList.add(codeCsmcSql);
		mapList.add(codeDqmcSql);

		name = "paiQianDiImportModle";
		
		return name;
	}
	
	/**
	 * 下载派遣地管理的错误数据
	 */
	public String getPaiQianDiImportInfo(HttpServletRequest request,List aliasNameList, List list, 
			List mapList, List mapNameList)throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String codeSql = "SELECT NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		String faRenSql = "   SELECT g.cpny_id CONTENT  from hr_company g     ";
		String codeSfSql = " select distinct m.state_nm CONTENT from INC_CITY_TO_CITYLEVEL_MAPP m ";
		String codeCsmcSql = " select distinct m.city_nm CONTENT, state_nm PARENT_CODE from INC_CITY_TO_CITYLEVEL_MAPP m group by state_nm, city_nm order by state_nm, city_nm ";
		String codeDqmcSql = " select distinct m.region_nm CONTENT,m.city_nm PARENT_CODE from INC_CITY_TO_CITYLEVEL_MAPP m group by m.city_nm,m.region_nm order by m.city_nm,m.region_nm ";
		String name = "";
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.chengShiDengJi", request));// 城市等级
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.shengFen",request));// 省份
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.chengShiMingCheng", request));// 城市名称
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.diQuMingCheng", request));// 地区名称
		aliasNameList.add("正/异常");//正/异常
		aliasNameList.add("错误提示");//错误提示

		String personId = admin.getPersonId()!=null?admin.getPersonId().toString():"";
		String sqlDetailInfo = "SELECT TT.PQD_FAREN, "
							+"         TT.PQD_CHENGSHIDENGJI, "
							+"         TT.PQD_SHENGFEN, "
							+"         TT.PQD_CHENGSHIMINGCHENG, "
							+"         TT.PQD_DIQUMINGCHENG, "
							+"         DECODE(TT.PQD_DAORU_RESULT,'E','异常','正常') CHECK_FLAG, "
							+"         NVL(TT.PQD_001,'无') CHECK_ERROR "
						    +"     FROM SY_DISPATCH_TEMP TT "
							+"    WHERE TT.CREATED_BY = '"+personId+ "' "
							+" ORDER BY TT.PQD_NO ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}
		//这些是sheet名字
		mapNameList.add("法人");
		mapNameList.add("城市等级");
		mapNameList.add("省份");
		mapNameList.add("城市列表");
		mapNameList.add("地区列表");
		
		mapList.add(faRenSql);
		mapList.add(codeSql + " '218067' ");
		mapList.add(codeSfSql);
		mapList.add(codeCsmcSql);
		mapList.add(codeDqmcSql);

		name = "pqdImportInfo";
		
		return name;
	}
	
	/**
	 * 下载派遣地管理的模版
	 */
	public String exportZuiDiGongZiBiaoZhunCuXiaoYuanMoBanModle(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException {

		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		String quFenSql = " select '应发' CONTENt from dual union  select '实得' CONTENt from dual  ";
		String nianDuSql = " select '2014' CONTENt from dual union  select '2015' CONTENt from dual  union select '2016' CONTENt from dual ";
		//String cityCdSql = " select '500015' CONTENt from dual union  select '500016' CONTENt from dual  union select '500017' CONTENt from dual ";
		String name = "";
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.niandu",
				request));// 年度
		aliasNameList.add("省份");// CITY_CD
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.chengShiMingCheng", request));// 城市名称
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuidigongzi", request));// 最低工资
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.qufen", request));// 区分

		String sqlDetailInfo = " select '2014', '广东省', '广州市', '20000', '应发' from dual";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

			//这些是sheet名字
			mapNameList.add("年度");
			mapNameList.add("CITY_CD");
			mapNameList.add("城市名称");
			mapNameList.add("区分");
			
			mapList.add(nianDuSql);
			mapList.add(codeSql + "219597");
			mapList.add(codeSql + "218074");
			mapList.add(quFenSql);

			name = "zuiDiGongZiBiaoZhunCuXiaoYuanModle";
		return name;
	}
	
	/**
	 * 下载派遣地管理的模版
	 */
	public String exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanMoBanModle(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException {

		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		String faRenSql = "   SELECT g.cpny_id CONTENT  from hr_company g     ";
		String quFenSql = " select '应发' CONTENt from dual union  select '实得' CONTENt from dual  ";
		String name = "";
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.niandu",
				request));// 年度
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.fulidiqu",
				request));// 福利地区
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuidigongzi", request));// 最低工资
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.shepinggongzi", request));// 社平工资
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuixiaojishu", request));// 最小基数
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuidajishu", request));// 最大基数
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.qufen",
				request));// 区分

		String sqlDetailInfo = " select 'TSTO', '2014', '北京', '10000', '20000', '30000', '50000', '应发' from dual";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

			//这些是sheet名字
			mapNameList.add("法人");
			mapNameList.add("福利地区");
			mapNameList.add("区分");
			
			mapList.add(faRenSql);
			mapList.add(codeSql + "216736");
			mapList.add(quFenSql);

			name = "zuiDiGongZiBiaoZhunFeiCuXiaoYuanModle";
		return name;
	}
	
	public String getTemplateInfo(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		String cpnySql = "SELECT T.CP_NAME CONTENT FROM IS_COMPANY T WHERE T.CPNY_ID = ";
		String TEMPLATE_TYPE = request.getParameter("TEMPLATE_TYPE");
		String EMP_TYPE_CODE = request.getParameter("EMP_TYPE_CODE");
		String name = "";
		
		if("215977".equals(TEMPLATE_TYPE)){//人员基本信息
			aliasNameList.add("社号(必填)");
			aliasNameList.add("员工姓名(可为空)");
			aliasNameList.add("家庭住址");
			aliasNameList.add("籍贯");
			aliasNameList.add("民族");
			
			aliasNameList.add("政治面貌");
			aliasNameList.add("是否共产党员");
			aliasNameList.add("身高");
			aliasNameList.add("体重");
			aliasNameList.add("血型");
			
			aliasNameList.add("是否残疾");
			aliasNameList.add("招聘来源");
			aliasNameList.add("离职原因 ");
			aliasNameList.add("奖惩备注");
			aliasNameList.add("爱心基金支付方式");
			
			aliasNameList.add("是否支付爱心基金");
			aliasNameList.add("负担房租标志");
			aliasNameList.add("负担医疗费标志");
			aliasNameList.add("负担教育费标志");
			/*aliasNameList.add("人员类型(CHR)");*/
			
			aliasNameList.add("工作类型(CHR)");
			/*aliasNameList.add("人员类型生效日期");//2111111111111
*/			aliasNameList.add("福利地区(保险)");//2222222222222
			aliasNameList.add("工作地区");
			aliasNameList.add("劳动手册编号");
			
			aliasNameList.add("社外工龄 ");
			aliasNameList.add("福利地区(公积金)");//266666666666
			aliasNameList.add("保险公司");
			aliasNameList.add("保险类型 ");
			aliasNameList.add("年假基准");//299999999999
			
			aliasNameList.add("产品");
			aliasNameList.add("促销员所属");
			aliasNameList.add("星级级别");
			aliasNameList.add("是否兼卖");
			aliasNameList.add("是否共建促销员");
			
			aliasNameList.add("评价类型");//35555555555
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12000003");
			map.put("CELL1", "魏某某");
			map.put("CELL2", "河南省南阳市新野县");
			map.put("CELL3", "河南省");
			map.put("CELL4", "汉族");
			
			map.put("CELL5", "无党派人士");
			map.put("CELL6", "Y");
			map.put("CELL7", "170");
			map.put("CELL8", "60");
			map.put("CELL9", "A");
			
			map.put("CELL10", "N");
			map.put("CELL11", "内部推荐");
			map.put("CELL12", "主动离职");
			map.put("CELL13", "文本");
			map.put("CELL14", "小数点取正");
			
			map.put("CELL15", "Y");
			map.put("CELL16", "个人");
			map.put("CELL17", "Y");
			map.put("CELL18", "Y");
			/*map.put("CELL19", "管理职");*/
			
			map.put("CELL19", "不定时工作制");
			/*map.put("CELL21", "2014-06-16");//2111111111
*/			map.put("CELL20", "北京");//2222222222
			map.put("CELL21", "F2级地区");
			map.put("CELL22", "001");
			
			map.put("CELL23", "22");
			map.put("CELL24", "兰州");//2666666
			map.put("CELL25", "");
			map.put("CELL26", "上海城镇");
			map.put("CELL27", "2014-08-16");//29999999
			
			map.put("CELL28", "斯黛乐");
			map.put("CELL29", "天音");
			map.put("CELL30", "5");
			map.put("CELL31", "Y");
			map.put("CELL32", "Y");
			map.put("CELL33", "Y");//36666666
			list.add(map);

			mapNameList.add("籍贯参考");
			mapNameList.add("民族参考");
			mapNameList.add("政治面貌参考");
			mapNameList.add("是否共产党员参考");
			mapNameList.add("血型参考");			
			mapNameList.add("是否残疾参考");
			mapNameList.add("招聘来源参考");
			mapNameList.add("爱心基金支付方式参考");
			mapNameList.add("是否支付爱心基金参考");
			mapNameList.add("负担房租标志参考");			
			mapNameList.add("负担医疗费标志参考");
			mapNameList.add("负担教育费标志参考");
			mapNameList.add("人员类型(CHR)参考");
			mapNameList.add("工作类型(CHR)参考");
			mapNameList.add("工作地区参考");			
			mapNameList.add("福利地区参考");
			mapNameList.add("保险类型参考");
			mapNameList.add("保险公司参考");
			mapNameList.add("产品参考");
			mapNameList.add("促销员所属参考");
			mapNameList.add("星级级别参考");			
			mapNameList.add("是否兼卖参考");
			mapNameList.add("是否共建促销员参考");
			mapList.add(codeSql + "774");
			mapList.add(codeSql + "210942");
			mapList.add(codeSql + "210938");
			mapList.add(codeSql + "123224");
			mapList.add(codeSql + "4573");
			mapList.add(codeSql + "123224");
			mapList.add(codeSql + "3306");
			mapList.add(codeSql + "211654");

			mapList.add(codeSql + "123224");
			mapList.add(codeSql + "278667");
			mapList.add(codeSql + "123224");
			mapList.add(codeSql + "123224");
			mapList.add(codeSql + "1368");
			mapList.add(codeSql + "211554");
			mapList.add(codeSql + "211557");
			mapList.add(codeSql + "216736");
			mapList.add(codeSql + "483");
			mapList.add(cpnySql + "'"+admin.getCpnyId()+"'");
			mapList.add(codeSql + "211424");
			mapList.add(codeSql + "211837");
			mapList.add(codeSql + "215954");
			mapList.add(codeSql + "123224");
			mapList.add(codeSql + "123224");
			
			name = "basicEmpInfo";
		}else if("215978".equals(TEMPLATE_TYPE)){//外国语
			aliasNameList.add("社号(必填)");
			aliasNameList.add("员工姓名(可为空)");
			aliasNameList.add("证书颁发日期");
			aliasNameList.add("考试名");
			aliasNameList.add("等级");
			aliasNameList.add("分数");
			aliasNameList.add("津贴标准（金额）");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12000003");
			map.put("CELL1", "魏某某");
			map.put("CELL2", "2014/06/17");
			map.put("CELL3", "CET");
			map.put("CELL4", "四级");
			map.put("CELL5", "521");
			map.put("CELL6", "1234");
			list.add(map);
			mapNameList.add("考试名参考");
			mapNameList.add("等级参考");
			mapList.add(codeSql + "1394");
			mapList.add(codeSql + "1401");
			name = "languageInfo";
		}else if("215979".equals(TEMPLATE_TYPE)){//资格证
			aliasNameList.add("社号(必填)");
			aliasNameList.add("员工姓名(可为空)");
			aliasNameList.add("资格证名称");
			aliasNameList.add("证件级别");
			aliasNameList.add("职称");
			aliasNameList.add("颁发机构");
			aliasNameList.add("颁发日期");
			aliasNameList.add("津贴标准（金额）");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12000003");
			map.put("CELL1", "李某某");
			map.put("CELL2", "软件资格证");
			map.put("CELL3", "2级");
			map.put("CELL4", "中级");
			map.put("CELL5", "政府某部门");
			map.put("CELL6", "2014/06/18");
			map.put("CELL7", "213");
			list.add(map);
			mapNameList.add("证件级别参考");
			mapNameList.add("职称参考");
			mapList.add(codeSql + "14910");
			mapList.add(codeSql + "123485");
			name = "qualification";
		}else if("215980".equals(TEMPLATE_TYPE)){//工作经历
			aliasNameList.add("社号(必填)");
			aliasNameList.add("员工姓名(可为空)");
			aliasNameList.add("开始日期");
			aliasNameList.add("结束日期");
			aliasNameList.add("工作单位");
			aliasNameList.add("部门");
			aliasNameList.add("职位");
			aliasNameList.add("职级");
			aliasNameList.add("工资待遇");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12000003");
			map.put("CELL1", "魏某某");
			map.put("CELL2", "2014/06/16");
			map.put("CELL3", "2014/06/17");
			map.put("CELL4", "AIT");
			map.put("CELL5", "研发部");
			map.put("CELL6", "主管");
			map.put("CELL7", "3级");
			map.put("CELL8", "1234");
			list.add(map);
			name = "workExperience";
		}else if("215981".equals(TEMPLATE_TYPE)){//培训
			aliasNameList.add("社号(必填)");
			aliasNameList.add("员工姓名(可为空)");
			aliasNameList.add("培训名称");
			aliasNameList.add("选修/必修 ");
			aliasNameList.add("培训区分");
			aliasNameList.add("起始日期");
			aliasNameList.add("终止日期");
			aliasNameList.add("培训机构");
			aliasNameList.add("培训方法");
			aliasNameList.add("培训时间");
			aliasNameList.add("培训结果");
			aliasNameList.add("备注");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12000003");
			map.put("CELL1", "魏某某");
			map.put("CELL2", "oracle培训 ");
			map.put("CELL3", "选修");
			map.put("CELL4", "内部");
			map.put("CELL5", "2014/06/17");
			map.put("CELL6", "2014/06/19");
			map.put("CELL7", "LGCNS");
			map.put("CELL8", "现场");
			map.put("CELL9", "8");
			map.put("CELL10", "good");
			map.put("CELL11", "全体通过");
			list.add(map);
			mapNameList.add("选修必修参考");
			mapNameList.add("培训区分参考");
			mapNameList.add("培训方法参考");
			mapList.add(codeSql + "123376");
			mapList.add(codeSql + "123459");
			mapList.add(codeSql + "123271");
			name = "training";
		}else if("215982".equals(TEMPLATE_TYPE)){//评价
			aliasNameList.add("社号(必填)");
			aliasNameList.add("员工姓名(可为空)");
			aliasNameList.add("评价期间");
			aliasNameList.add("绩效");
			aliasNameList.add("态度");
			aliasNameList.add("能力");
			aliasNameList.add("评价分数");
			aliasNameList.add("评价等级");
			aliasNameList.add("意见");
			aliasNameList.add("最终顺位");
			aliasNameList.add("总评价人员数");
			aliasNameList.add("备注");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12000003");
			map.put("CELL1", "魏某某");
			map.put("CELL2", "2014上半年 ");
			map.put("CELL3", "60");
			map.put("CELL4", "60");
			map.put("CELL5", "60");
			map.put("CELL6", "90");
			map.put("CELL7", "S");
			map.put("CELL8", "优秀");
			map.put("CELL9", "12");
			map.put("CELL10", "21");
			map.put("CELL11", "通过");
			list.add(map);
			mapNameList.add("评价等级参考");
			mapList.add(codeSql + "3538");
			name = "evaluation";
		}else if("215983".equals(TEMPLATE_TYPE)){//工会
			aliasNameList.add("社号(必填)");
			aliasNameList.add("员工姓名(可为空)");
			aliasNameList.add("职责");
			aliasNameList.add("起始日期");
			aliasNameList.add("终止日期");
			aliasNameList.add("会费支付状态");
			aliasNameList.add("支付方式");
			aliasNameList.add("备注");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12000003");
			map.put("CELL1", "魏某某");
			map.put("CELL2", "主席 ");
			map.put("CELL3", "2014/06/17");
			map.put("CELL4", "2014/06/19");
			map.put("CELL5", "Y");
			map.put("CELL6", "小数点取正");
			map.put("CELL7", "从没交过会费");
			list.add(map);
			mapNameList.add("职责参考");
			mapNameList.add("会费支付状态");
			mapNameList.add("支付方式");
			mapList.add(codeSql + "123251");
			mapList.add(codeSql + "123224");
			mapList.add(codeSql + "211654");
			name = "labourUnion";
		}else if("215984".equals(TEMPLATE_TYPE)){//残疾证
			aliasNameList.add("社号(必填)");
			aliasNameList.add("员工姓名(可为空)");
			aliasNameList.add("残疾类型");
			aliasNameList.add("签发日期");
			aliasNameList.add("有效期");
			aliasNameList.add("备注");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12000003");
			map.put("CELL1", "魏某某");
			map.put("CELL2", "视力残疾 ");
			map.put("CELL3", "2014/06/17");
			map.put("CELL4", "2年");
			map.put("CELL5", "意外事故");
			list.add(map);
			
			mapNameList.add("残疾类型参考");
			mapNameList.add("有效期参考");
			mapList.add(codeSql + "123264");
			mapList.add(codeSql + "123462");
			
			name = "disable";
		}else if("215985".equals(TEMPLATE_TYPE)){//紧急联系人
			aliasNameList.add("社号(必填)");
			aliasNameList.add("员工姓名(可为空)");
			aliasNameList.add("关系");
			aliasNameList.add("姓名");
			aliasNameList.add("联系电话");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12000003");
			map.put("CELL1", "魏某某");
			map.put("CELL2", "家属 ");
			map.put("CELL3", "魏某某");
			map.put("CELL4", "110110110");
			list.add(map);
			
			mapNameList.add("关系参考");
			mapList.add(codeSql + "1693");
			
			name = "contacts";
		}else if("215986".equals(TEMPLATE_TYPE)){//黑色档案
			aliasNameList.add("社号(必填)");
			aliasNameList.add("员工姓名(可为空)");
			aliasNameList.add("发生日期");
			aliasNameList.add("类型");
			aliasNameList.add("详细描述");
			aliasNameList.add("备注");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12000003");
			map.put("CELL1", "魏某某");
			map.put("CELL2", "2014/06/17");
			map.put("CELL3", "迟到 ");
			map.put("CELL4", "迟到时间过长");
			map.put("CELL5", "经常迟到");
			list.add(map);
			name = "badArchives";
		}else if("216002".equals(TEMPLATE_TYPE)){//辅助信息
			aliasNameList.add("社号(必填)");
			aliasNameList.add("员工姓名(可为空)");
			aliasNameList.add("标题");
			aliasNameList.add("内容");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12000003");
			map.put("CELL1", "魏某某");
			map.put("CELL2", "姓名");
			map.put("CELL3", "魏某某 ");
			list.add(map);
			name = "assistInfo";
		}else if("278650".equals(TEMPLATE_TYPE)){//兼卖信息
			aliasNameList.add("社号(必填)");
			aliasNameList.add("员工姓名(可为空)");
			aliasNameList.add("产品名称");
			aliasNameList.add("备注");
			
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12000003");
			map.put("CELL1", "魏某某");
			map.put("CELL2", "净水器");
			map.put("CELL3", "热卖");
			list.add(map);
			mapNameList.add("产品类型参考");
			mapList.add(codeSql + "211424");
			name = "productInfo";
		}
		return name;
	}
	
	@Override
	public List getAssistList(HttpServletRequest request)
			throws SQLException {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getAssistList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getAssistList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	@Override
	public List getPerConversionList(HttpServletRequest request)
			throws SQLException {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam",admin.getSpecialParam());//这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo",admin.getDeptNo());//判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getPerConversionList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getPerConversionList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	@Override
	public int getPerConversionListCnt(HttpServletRequest request)
			throws SQLException {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID",admin.getAdminID() );
		paramMap.put("specialParam",admin.getSpecialParam());//这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo",admin.getDeptNo());//判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		
		return empInfoDao.getPerConversionListCnt(paramMap) ;
	}
	
	@Override
	public int addAssistInfo(HttpServletRequest request)
			throws SQLException {// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		try {
			
			
			int count = Integer.parseInt(request.getParameter("count"));

			for (int i = 0; i < count; i++) {
			paramMap.put("TITLE", request
					.getParameter("TITLE"+i));
			paramMap.put("CONTENT", request
					.getParameter("CONTENT"+i));
			
			this.empInfoDao.addAssistInfo(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	@Override
	public int deleteAssistInfo(HttpServletRequest request)
			throws SQLException {// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		try {
			String[] paramDataL = request.getParameterValues("ASSIST_NO");

			if (paramDataL != null) {
				for (int i = 0; i < paramDataL.length; i++) {
					paramMap.put("ASSIST_NO", paramDataL[i]);
					this.empInfoDao.deleteAssistInfo(paramMap);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	@Override
	public int editAssistInfo(HttpServletRequest request)
			throws SQLException {try {
				// 页面提交数据
				LinkedHashMap paramMap = ObjectBindUtil
						.getRequestParamData(request);
				// 创建人
				HttpSession session = request.getSession();
				AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				paramMap.put("UPDATED_BY", admin.getAdminID());
				String[] paramDataL = request.getParameterValues("ASSIST_NO");
				if (paramDataL != null) {
					for (int i = 0; i < paramDataL.length; i++) {
						paramMap.put("ASSIST_NO", paramDataL[i]);
						paramMap.put("TITLE",
								request.getParameter("TITLE_"
										+ paramDataL[i]));
						paramMap.put("CONTENT", request
								.getParameter("CONTENT_" + paramDataL[i]));
						this.empInfoDao.editAssistInfo(paramMap);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}

			return 1;
	}
	
	public int editProductInfo(HttpServletRequest request)
		throws SQLException {try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			// 创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getAdminID());
			String[] paramDataL = request.getParameterValues("PRODUCT_NO");
			this.empInfoDao.deleteProductInfo(paramMap);
			if (paramDataL != null) {
				for (int i = 0; i < paramDataL.length; i++) {
					paramMap.put("PRODUCT_NO", paramDataL[i]);
					this.empInfoDao.addProductInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@Override
	public List getPositionList(HttpServletRequest request)
			throws SQLException {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny"));
		
		retrunList = empInfoDao.getPositionList(paramMap) ;
		
		return retrunList ;
	}
	
	@Override
	public List getDqmcList(HttpServletRequest request)
			throws SQLException {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny"));
		
		retrunList = empInfoDao.getDqmcList(paramMap) ;
		
		return retrunList ;
	}

	@Override
	public List getDqmcListNew(HttpServletRequest request)
			throws SQLException {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny"));
		
		retrunList = empInfoDao.getDqmcListNew(paramMap) ;
		
		return retrunList ;
	}
	
	public List getCompanyList(HttpServletRequest request)
	throws SQLException {

		List retrunList = new ArrayList() ;
		
		Map paramMap=new LinkedHashMap();
		
		paramMap.put("ACTIVITY",1);
		if("".equals(paramMap.get("interLanguage"))){
		    paramMap.put("interLanguage","zh");
		}
		retrunList = companyDao.getCompanyItemAllList(paramMap) ;
		return retrunList ;
	}
	

	public List getCompanyListHome(HttpServletRequest request)
	throws SQLException {

		List retrunList = new ArrayList() ;
		
		Map paramMap=new LinkedHashMap();
		
		paramMap.put("ACTIVITY",1);
		if("".equals(paramMap.get("interLanguage"))){
		    paramMap.put("interLanguage","zh");
		}
		retrunList = companyDao.getCompanyItemAllListHome(paramMap) ;
		return retrunList ;
	}
	
	public List getEmpInfoLxjList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		List retrunList = companyDao.getEmpInfoLxjList(paramMap) ;
		return retrunList ;
	}
	
	public List getPaInfoLxjList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		List retrunList = companyDao.getPaInfoLxjList(paramMap) ;
		return retrunList ;
	}
	
	public List getPaInfoLxjLgechList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		List retrunList = companyDao.getPaInfoLxjLgechList(paramMap) ;
		return retrunList ;
	}
	
	public List getPaInfoLxjLgetaList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		List retrunList = companyDao.getPaInfoLxjLgetaList(paramMap) ;
		return retrunList ;
	}
	
	public List getYearInfoLxjList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getYearInfoLxjList(paramMap) ;
		return retrunList ;
	}
	
	public List getpayDetilInfoLxjList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getpayDetilInfoLxjList(paramMap) ;
		return retrunList ;
	}
	
	public List getpayDetilInfoLxjLgechList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getpayDetilInfoLxjLgechList(paramMap) ;
		return retrunList ;
	}
	
	public List getpayDetilInfoLxjLgetaList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getpayDetilInfoLxjLgetaList(paramMap) ;
		return retrunList ;
	}
	
	public List getotherPayInfoLxjList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getotherPayInfoLxjList(paramMap) ;
		return retrunList ;
	}
	
	public List getotherPayInfoLxjLgechList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getotherPayInfoLxjLgechList(paramMap) ;
		return retrunList ;
	}
	
	public List getotherPayInfoLxjLgetaList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getotherPayInfoLxjLgetaList(paramMap) ;
		return retrunList ;
	}
	
	public List getwelfarePayInfoLxjList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getwelfarePayInfoLxjList(paramMap) ;
		return retrunList ;
	}
	
	public List getwelfarePayInfoLxjLgechList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getwelfarePayInfoLxjLgechList(paramMap) ;
		return retrunList ;
	}
	
	public List getwelfarePayInfoLxjLgetaList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getwelfarePayInfoLxjLgechList(paramMap) ;
		return retrunList ;
	}
	
	public List getadministrationPayInfoLxjList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getadministrationPayInfoLxjList(paramMap) ;
		return retrunList ;
	}
	
	public List getadministrationPayInfoLxjLgechList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getadministrationPayInfoLxjLgechList(paramMap) ;
		return retrunList ;
	}
	
	public List getadministrationPayInfoLxjLgetaList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getadministrationPayInfoLxjLgetaList(paramMap) ;
		return retrunList ;
	}
	
	public List getpaManuallyInfoLxjList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getpaManuallyInfoLxjList(paramMap) ;
		return retrunList ;
	}
	
	public List getpaManuallyInfoLxjLgechList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getpaManuallyInfoLxjLgechList(paramMap) ;
		return retrunList ;
	}
	
	public List getpaManuallyInfoLxjLgetaList(HttpServletRequest request)
	throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("KEY")==null)
			paramMap.put("KEY", admin.getEmpID());
		if(paramMap.get("CPNY_ID")==null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getpaManuallyInfoLxjLgetaList(paramMap) ;
		return retrunList ;
	}
	
	public Object getPersonalInfoByLeave(HttpServletRequest request, String PERSON_ID) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(null != PERSON_ID
				&& !"".equals(PERSON_ID)){
			paramMap.put("PERSON_ID",PERSON_ID);
		}else{
			if(paramMap.get("PERSON_ID")==null){
				paramMap.put("PERSON_ID",admin.getPersonId());
			}
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return empInfoDao.getPersonalInfoByLeave(paramMap);
		//return empInfoDao.getPersonalInfo(paramMap);
	}

	
	public Object getPersonalInfoByLeaveApply(HttpServletRequest request, String PERSON_ID) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(null != PERSON_ID
				&& !"".equals(PERSON_ID)){
			paramMap.put("PERSON_ID",PERSON_ID);
		}else{
			if(paramMap.get("PERSON_ID")==null){
				paramMap.put("PERSON_ID",admin.getPersonId());
			}
		}
		
		return empInfoDao.getPersonalInfoByLeaveApply(paramMap);
	}
	
	public Object getArchivesInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		LinkedHashMap archivseInfo = (LinkedHashMap) empInfoDao.getArchivesInfo(paramMap);
		LinkedHashMap fileParam = new LinkedHashMap();
		fileParam.put("APPLY_TYPE", "0");
		fileParam.put("APPLY_NO", paramMap.get("ARCH_ID"));
		List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
		String fileName = "";
		String fileUrl = "";
		for(int i = 0;i<fileList.size();i++){
			LinkedHashMap fileMap = (LinkedHashMap)fileList.get(i);
			String fileUrlStr = fileMap.get("FILE_URL").toString();
			fileUrlStr = fileUrlStr.substring(fileUrlStr.lastIndexOf("/") + 1);
			if(i == 0){
				fileName = fileMap.get("FILE_NAME").toString();
				fileUrl = fileUrlStr;
			}else{
				fileName += ";" + fileMap.get("FILE_NAME").toString();
				fileUrl += ";" +  fileUrlStr;
			}
		}
		archivseInfo.put("FILE_NAME", fileName);
		archivseInfo.put("FILE_URL", fileUrl);
		archivseInfo.put("fileList",fileList);
		return archivseInfo;
	}
	
	/**
	 * 获取社员兼卖产品类型
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpProductList(HttpServletRequest request)
		throws SQLException {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return  this.empInfoDao.getEmpProductList(paramMap);
	}
	
	/**
	 * 根据法人获取人员类型组
	 */
	public List getEmpTypeGroup(HttpServletRequest request)
			throws SQLException {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		List retrunList = empInfoDao.getEmpTypeGroup(paramMap) ;
		
		return retrunList ;
	}
	
	public List getEmpInfoTempList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getEmpInfoTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getEmpInfoTempList(paramMap);
		}
		return retrunList ;
		/*AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		return  this.empInfoDao.getEmpInfoTempList(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));*/
	}

	/*public int getEmpInfoTempCnt(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		return  this.empInfoDao.getEmpInfoTempCnt(paramMap);
	}

	public int getEmpInfoTempErrCnt(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		return  this.empInfoDao.getEmpInfoTempErrCnt(paramMap);
	}*/
	
	public int getEmpInfoTempCnt(HttpServletRequest request,String errorFlag){
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = empInfoDao.getEmpInfoTempErrCnt(paramMap);
		}else{
			retrunInt = empInfoDao.getEmpInfoTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	public String importEmpInfoTempListExcel(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		return empInfoDao.importEmpInfoTempListExcel(paramMap);
	}


	/**
	 * 获取外国语导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public int getTempLanguageTempCnt(HttpServletRequest request, String errorFlag) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = empInfoDao.getTempLanguageTempErrorCnt(paramMap);
		}else{
			retrunInt = empInfoDao.getTempLanguageTempCnt(paramMap);
		}

		return retrunInt;
	}
	/**
	 * 获取外国语导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	@Override
	public List getTempLanguageTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getTempLanguageTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getTempLanguageTempList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取资格证导入信息
	 */
	@Override
	public List getQualInfoTempList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getQualInfoTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getQualInfoTempList(paramMap);
		}
		return retrunList ;
	}

	@Override
	public int getQualInfoTempCnt(HttpServletRequest request,
			String errorFlag) {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = empInfoDao.getQualInfoTempErrCnt(paramMap);
		}else{
			retrunInt = empInfoDao.getQualInfoTempCnt(paramMap);
		}

		return retrunInt;
	}

	/**
	 * 获取兼卖信息导入信息
	 */
	@Override
	public List getProductInfoTempList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getProductInfoTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getProductInfoTempList(paramMap);
		}
		return retrunList ;
	}

	@Override
	public int getProductInfoTempCnt(HttpServletRequest request,
			String errorFlag) {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = empInfoDao.getProductInfoTempErrCnt(paramMap);
		}else{
			retrunInt = empInfoDao.getProductInfoTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	@Override
	public List getWorkExperienceInfoTempList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getWorkExperienceInfoTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getWorkExperienceInfoTempList(paramMap);
		}
		return retrunList ;
	}

	@Override
	public int getWorkExperienceInfoTempCnt(HttpServletRequest request,
			String errorFlag) {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = empInfoDao.getWorkExperienceInfoTempErrCnt(paramMap);
		}else{
			retrunInt = empInfoDao.getWorkExperienceInfoTempCnt(paramMap);
		}

		return retrunInt;
	}

	
	@Override
	public List getEvsInfoTempList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getEvsInfoTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getEvsInfoTempList(paramMap);
		}
		return retrunList ;
	}


	@Override
	public int getEvsInfoTempCnt(HttpServletRequest request, String errorFlag) {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = empInfoDao.getEvsInfoTempErrCnt(paramMap);
		}else{
			retrunInt = empInfoDao.getEvsInfoTempCnt(paramMap);
		}

		return retrunInt;
	}

	@Override
	public List getTradeUnionInfoTempList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getTradeUnionInfoTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getTradeUnionInfoTempList(paramMap);
		}
		return retrunList ;
	}
	
	@Override
	public List getTradeUnionInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		retrunList = empInfoDao.getTradeunionList(paramMap) ;
		return retrunList ;
	}
	
	@Override
	public int getTradeUnionInfoTempCnt(HttpServletRequest request,
			String errorFlag) {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = empInfoDao.getTradeUnionInfoTempErrCnt(paramMap);
		}else{
			retrunInt = empInfoDao.getTradeUnionInfoTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	/**
	 * 获取残疾人信息导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public int getTempDisabledTempCnt(HttpServletRequest request, String errorFlag) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = empInfoDao.getTempDisabledTempErrorCnt(paramMap);
		}else{
			retrunInt = empInfoDao.getTempDisabledTempCnt(paramMap);
		}

		return retrunInt;
	}

	/**
	 * 显示残疾证信息导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempDisabledTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getTempDisabledTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getTempDisabledTempList(paramMap);
		}
		return retrunList;
	}

	
	/**
	 * 员工工作经历excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author chenfeifei@ait.net.cn
	 * @date 2014-9-01
	 */
	@Override
	public String submitImportExcelWorkExperienceData(HttpServletRequest request) {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
					"seach_");
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			String type = request.getParameter("accrual");
			if(type.equals("workExperience")){
				paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.pr_import_temp_WORKEX_data");
			}else if (type.equals("evsInfo")) {
				paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.pr_import_temp_evsInfo_data");
			}else if (type.equals("trade")) {
				paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_TRADEU_DATA");
			}else if (type.equals("training")) {
				paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_TRAINING_DATA");
			}else if (type.equals("baseEmpInfo")){
				paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_BASEEMP_DATA");
			}else if (type.equals("product")){
				paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_PRODUCT_DATA");
			}else if(type.equals("qual")){
				paramMap.put("PR_NAME", "PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_QUAL_DATA");
			}
			try {
				return this.empInfoDao.importInfoFromExcel(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}

	
	/**
	 * 获取培训信息导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @version V1.0
	 */
	public List getTrainingImportTempList(HttpServletRequest request){
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getTrainingImportTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getTrainingImportTempList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取培训信息导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTrainingImportTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = empInfoDao.getTrainingImportTempErrorCnt(paramMap);
		}else{
			retrunInt = empInfoDao.getTrainingImportTempCnt(paramMap);
		}

		return retrunInt;
	}

	@Override
	public String getWorkTemplateInfoByExcelData(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList) {
		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("interCpnyID", admin.getCpnyId());
		
		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT,'' CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		//模版类型1:工作经历信息 2：评价信息 3：工会信息
		String type = request.getParameter("type");
		//模版名称
		String name = "";
		if("work".equals(type)){
			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("开始日期");
			aliasNameList.add("结束日期");
			aliasNameList.add("工作单位");
			aliasNameList.add("部门");
			aliasNameList.add("职位");
			aliasNameList.add("职级");
			aliasNameList.add("工资待遇");
			aliasNameList.add("验证结果");
			List workExperienceTempList = this.empInfoDao.getworkExperienceTempList(paramMap,-1,-1);
			for(int i=0;i<workExperienceTempList.size();i++){
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap)workExperienceTempList.get(i);
				map.put("CELL0", map1.get("EMPID") == null ? "" : map1.get("EMPID"));
				map.put("CELL1", map1.get("LOCAL_NAME") == null ? "" : map1.get("LOCAL_NAME"));
				map.put("CELL2", map1.get("START_DATE") == null ? "" : map1.get("START_DATE"));
				map.put("CELL3", map1.get("END_DATE") == null ? "" : map1.get("END_DATE"));
				map.put("CELL4", map1.get("CPNY_NAME") == null ? "" : map1.get("CPNY_NAME"));
				map.put("CELL5", map1.get("DEPT_NAME") == null ? "" : map1.get("DEPT_NAME"));
				map.put("CELL6", map1.get("POSITION") == null ? "" : map1.get("POSITION"));
				map.put("CELL7", map1.get("DUTY") == null ? "" : map1.get("DUTY"));
				map.put("CELL8", map1.get("PAYROLL") == null ? "" : map1.get("PAYROLL"));
				map.put("CELL9", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			name = "tempworkExperienceInfo";
	}else if ("evs".equals(type)) {
		aliasNameList.add("社号");
		aliasNameList.add("员工姓名");
		aliasNameList.add("评价期间");
		aliasNameList.add("绩效");
		aliasNameList.add("态度");
		aliasNameList.add("能力");
		aliasNameList.add("评价分数");
		aliasNameList.add("评价等级");
		aliasNameList.add("意见");
		aliasNameList.add("最终顺位");
		aliasNameList.add("总评价人员数");
		aliasNameList.add("备注");
		aliasNameList.add("验证结果");
		List evsTempList = this.empInfoDao.getEvsInfoTempList(paramMap,-1,-1);
		for(int i=0;i<evsTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)evsTempList.get(i);
			map.put("CELL0", map1.get("EMPID") == null ? "" : map1.get("EMPID"));
			map.put("CELL1", map1.get("LOCAL_NAME") == null ? "" : map1.get("LOCAL_NAME"));
			map.put("CELL2", map1.get("EV_PERIOD") == null ? "" : map1.get("EV_PERIOD"));
			map.put("CELL3", map1.get("EV_ACHI") == null ? "" : map1.get("EV_ACHI"));
			map.put("CELL4", map1.get("EV_ATTI") == null ? "" : map1.get("EV_ATTI"));
			map.put("CELL5", map1.get("EV_ABIL") == null ? "" : map1.get("EV_ABIL"));
			map.put("CELL6", map1.get("EV_MARK") == null ? "" : map1.get("EV_MARK"));
			map.put("CELL7", map1.get("EV_GRADE") == null ? "" : map1.get("EV_GRADE"));
			map.put("CELL8", map1.get("SUGGESTION") == null ? "" : map1.get("SUGGESTION"));
			map.put("CELL9", map1.get("FINAL_SEQUENCE") == null ? "" : map1.get("FINAL_SEQUENCE"));
			map.put("CELL10", map1.get("TOTAL_PEOPLE") == null ? "" : map1.get("TOTAL_PEOPLE"));
			map.put("CELL11", map1.get("EV_REMARK") == null ? "" : map1.get("EV_REMARK"));
			map.put("CELL12", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		mapNameList.add("评价等级参考");
		mapList.add(codeSql + "3538");
		name = "tempEvsInfo";
	}else if ("trade".equals(type)) {
		aliasNameList.add("社号");
		aliasNameList.add("员工姓名");
		aliasNameList.add("职责");
		aliasNameList.add("起始日期");
		aliasNameList.add("终止日期");
		aliasNameList.add("会费支付状态");
		aliasNameList.add("支付方式");
		aliasNameList.add("备注");
		aliasNameList.add("验证结果");
		List tradeUnionTempList = this.empInfoDao.getTradeunionList(paramMap,-1,-1);
		for(int i=0;i<tradeUnionTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)tradeUnionTempList.get(i);
			map.put("CELL0", map1.get("EMPID") == null ? "" : map1.get("EMPID"));
			map.put("CELL1", map1.get("LOCAL_NAME") == null ? "" : map1.get("LOCAL_NAME"));
			map.put("CELL2", map1.get("RESPONSIBITITY") == null ? "" : map1.get("RESPONSIBITITY"));
			map.put("CELL3", map1.get("ADDDATE") == null ? "" : map1.get("ADDDATE"));
			map.put("CELL4", map1.get("QUITDATE") == null ? "" : map1.get("QUITDATE"));
			map.put("CELL5", map1.get("PAY_FLAG") == null ? "" : map1.get("PAY_FLAG"));
			map.put("CELL6", map1.get("PAY_TYPE") == null ? "" : map1.get("PAY_TYPE"));
			map.put("CELL7", map1.get("REMARK") == null ? "" : map1.get("REMARK"));
			map.put("CELL8", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		mapNameList.add("职责参考");
		mapNameList.add("会费支付状态");
		mapNameList.add("支付方式");
		mapList.add(codeSql + "123251");
		mapList.add(codeSql + "123224");
		mapList.add(codeSql + "211654");
		name = "tempTradeUnionInfo";
	}else if ("training".equals(type)) {
		aliasNameList.add("社号");
		aliasNameList.add("员工姓名");
		aliasNameList.add("培训名称");
		aliasNameList.add("选修/必修");
		aliasNameList.add("培训区分");
		aliasNameList.add("起始日期");
		aliasNameList.add("终止日期");
		aliasNameList.add("培训机构");
		aliasNameList.add("培训方法");
		aliasNameList.add("培训时间");
		aliasNameList.add("培训结果");
		aliasNameList.add("备注");
		aliasNameList.add("验证结果");
		List trainingTempList = this.empInfoDao.getTrainingImportTempList(paramMap,-1,-1);
		for(int i=0;i<trainingTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)trainingTempList.get(i);
			map.put("CELL0", map1.get("EMPID") == null ? "" : map1.get("EMPID"));
			map.put("CELL1", map1.get("LOCAL_NAME") == null ? "" : map1.get("LOCAL_NAME"));
			map.put("CELL2", map1.get("COURSE_NAME") == null ? "" : map1.get("COURSE_NAME"));
			map.put("CELL3", map1.get("MUST_CODE") == null ? "" : map1.get("MUST_CODE"));
			map.put("CELL4", map1.get("TRAINING_DIFFERENTIATE") == null ? "" : map1.get("TRAINING_DIFFERENTIATE"));
			map.put("CELL5", map1.get("START_DATE") == null ? "" : map1.get("START_DATE"));
			map.put("CELL6", map1.get("END_DATE") == null ? "" : map1.get("END_DATE"));
			map.put("CELL7", map1.get("INSTITUTION_NAME") == null ? "" : map1.get("INSTITUTION_NAME"));
			map.put("CELL8", map1.get("TRAINING_METHOD") == null ? "" : map1.get("TRAINING_METHOD"));
			map.put("CELL9", map1.get("TRAINING_TIME") == null ? "" : map1.get("TRAINING_TIME"));
			map.put("CELL10", map1.get("TRAINING_RESULT") == null ? "" : map1.get("TRAINING_RESULT"));
			map.put("CELL11", map1.get("REMARKS") == null ? "" : map1.get("REMARKS"));
			map.put("CELL12", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		mapNameList.add("选修必修参考");
		mapNameList.add("培训区分参考");
		mapNameList.add("培训方法参考");
		mapList.add(codeSql + "123376");
		mapList.add(codeSql + "123459");
		mapList.add(codeSql + "123271");
		
		name = "tempTrainingInfo";
	}else if ("product".equals(type)) {
		aliasNameList.add("社号");
		aliasNameList.add("员工姓名");
		aliasNameList.add("产品名称");
		aliasNameList.add("备注");
		aliasNameList.add("验证结果");
		List tradeUnionTempList = this.empInfoDao.getProductInfoTempList(paramMap,-1,-1);
		for(int i=0;i<tradeUnionTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)tradeUnionTempList.get(i);
			map.put("CELL0", map1.get("EMPID") == null ? "" : map1.get("EMPID"));
			map.put("CELL1", map1.get("LOCAL_NAME") == null ? "" : map1.get("LOCAL_NAME"));
			map.put("CELL2", map1.get("PRODUCT_NO") == null ? "" : map1.get("PRODUCT_NO"));
			map.put("CELL7", map1.get("REMARK") == null ? "" : map1.get("REMARK"));
			map.put("CELL8", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		mapNameList.add("产品类型参考");
		mapList.add(codeSql + "211424");
		name = "tempSellProductInfo";
	}else if ("qual".equals(type)) {
		aliasNameList.add("社号");
		aliasNameList.add("员工姓名");
		aliasNameList.add("资格证名称");
		aliasNameList.add("证件级别");
		aliasNameList.add("职称");
		aliasNameList.add("颁发机构");
		aliasNameList.add("颁发日期");
		aliasNameList.add("结贴标准(金额)");
		aliasNameList.add("验证结果");
		List qualInfoTempList = this.empInfoDao.getQualInfoTempList(paramMap,-1,-1);
		for(int i=0;i<qualInfoTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)qualInfoTempList.get(i);
			map.put("CELL0", map1.get("EMPID") == null ? "" : map1.get("EMPID"));
			map.put("CELL1", map1.get("LOCAL_NAME") == null ? "" : map1.get("LOCAL_NAME"));
			map.put("CELL2", map1.get("QUAL_NAME") == null ? "" : map1.get("QUAL_NAME"));
			map.put("CELL3", map1.get("QUAL_LEVEL") == null ? "" : map1.get("QUAL_LEVEL"));
			map.put("CELL4", map1.get("QUAL_CARD_NO") == null ? "" : map1.get("QUAL_CARD_NO"));
			map.put("CELL5", map1.get("QUAL_INSTITUTE") == null ? "" : map1.get("QUAL_INSTITUTE"));
			map.put("CELL6", map1.get("DATE_OBTAINED") == null ? "" : map1.get("DATE_OBTAINED"));
			map.put("CELL7", map1.get("REMARK") == null ? "" : map1.get("REMARK"));
			map.put("CELL8", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		mapNameList.add("证件级别参考");
		mapList.add(codeSql + "123485");
		name = "tempQualificationInfo";
	}
		return name;
	}

	@Override
	public int addEmpMapping(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			paramMap.put("new_personId", paramMap.get("dwz.person.personId"));
			paramMap.put("old_personId", paramMap.get("dwz.person_old.personId"));
			//验证身份证号是否一致
			if(this.empInfoDao.validIdCard(paramMap) == 0){
				return 2;
			}
			//验证是否已经mapping过
			if(this.empInfoDao.validIsDuplicate(paramMap) == 1){
				return 3;
			}
			this.empInfoDao.addEmpMapping(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteEmpMapping(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.empInfoDao.deleteEmpMapping(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public LinkedHashMap getEmpMappingByNo(HttpServletRequest request) throws SQLException {
		LinkedHashMap empInfo = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		List list = this.empInfoDao.getEmpMappingByNo(paramMap);
		if(list!=null && list.size()>0){
			empInfo = (LinkedHashMap)list.get(0);
		}
		return  empInfo;
	}
	/**
	 * 获取员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfo (Map param) throws Exception {
		if("TSTO".equals(param.get("CPNY_ID") )){
			param.put("YEAR", DateUtil.getCurrentYearStr());
		}else{
			param.put("YEAR", this.infoApplyLeaveDao.getCurrentYear(param));
		}
		param.put("APPLY_NO", param.get("APPLY_NO") == null ? "" : param.get("APPLY_NO"));
		return infoApplyLeaveDao.getEmpVacInfo(param);
	}
	@Override
	public int updateEmpMapping(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.empInfoDao.updateEmpMapping(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List viewEmpMappingList(HttpServletRequest request) throws SQLException {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.viewEmpMappingList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.viewEmpMappingList(paramMap);
		}
		return retrunList;
	}

	@Override
	public int viewEmpMappingListCnt(HttpServletRequest request) throws SQLException {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			
		return empInfoDao.viewEmpMappingListCnt(paramMap);
	}

	/**
	 * 组装残疾证数据模板,导出带错误提示的数据
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @date 2014-09-03
	 * @version V1.0
	 */
	public String getTemplateInfoByExcelData11(HttpServletRequest request, List aliasNameList, List list, List mapList, List mapNameList) {
		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("interCpnyID", admin.getCpnyId());
		String type=request.getParameter("type");
		
		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT,'' CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		//模版名称
		String name = "";
		if ("1".equals(type)) {
			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("颁发日期");
			aliasNameList.add("考试名");
			aliasNameList.add("等级");
			aliasNameList.add("分数");
			aliasNameList.add("津贴");
			aliasNameList.add("验证结果");
			List paTempLanguageTempList = this.empInfoDao.getTempLanguageTempList(paramMap,-1,-1);
			for(int i=0;i<paTempLanguageTempList.size();i++){
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap)paTempLanguageTempList.get(i);
				map.put("CELL0", map1.get("LANGUAGE_NO") == null ? "" : map1.get("LANGUAGE_NO"));
				map.put("CELL1", map1.get("PERSON_ID") == null ? "" : map1.get("PERSON_ID"));
				map.put("CELL2", map1.get("KAOSHIDATE") == null ? "" : map1.get("KAOSHIDATE"));
				map.put("CELL3", map1.get("EXAM_NAME_CODE") == null ? "" : map1.get("EXAM_NAME_CODE"));
				map.put("CELL4", map1.get("LANGUAGE_LEVEL_CODE") == null ? "" : map1.get("LANGUAGE_LEVEL_CODE"));
				map.put("CELL5", map1.get("MARK") == null ? "" : map1.get("MARK"));
				map.put("CELL6", map1.get("ALLWANCE") == null ? "" : map1.get("ALLWANCE"));
				map.put("CELL7", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			mapNameList.add("考试名参考");
			mapNameList.add("等级参考");
			mapList.add(codeSql + "1394");
			mapList.add(codeSql + "1401");
		    name = "languageInfo";
		}else if ("2".equals(type)) {
			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("颁发日期");
			aliasNameList.add("残疾人类型");
			aliasNameList.add("有效期");
			aliasNameList.add("备注");
			aliasNameList.add("验证结果");
			List paTempDisableTempList = this.empInfoDao.getTempDisabledTempList(paramMap,-1,-1);
			for(int i=0;i<paTempDisableTempList.size();i++){
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap)paTempDisableTempList.get(i);
				map.put("CELL0", map1.get("T_ID") == null ? "" : map1.get("T_ID"));
				map.put("CELL1", map1.get("PERSON_ID") == null ? "" : map1.get("PERSON_ID"));
				map.put("CELL2", map1.get("ADDDATE") == null ? "" : map1.get("ADDDATE"));
				map.put("CELL3", map1.get("DISABILITY_TYPE") == null ? "" : map1.get("DISABILITY_TYPE"));
				map.put("CELL4", map1.get("DISABILITY_VALIDITY") == null ? "" : map1.get("DISABILITY_VALIDITY"));
				map.put("CELL5", map1.get("REMARK") == null ? "" : map1.get("REMARK"));
				map.put("CELL6", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			mapNameList.add("残疾类型参考");
			mapNameList.add("有效期参考");
			mapList.add(codeSql + "123264");
			mapList.add(codeSql + "123462");
		    name = "disabledInfo";
		}else if ("3".equals(type)) {
			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("关系");
			aliasNameList.add("紧急联系人姓名");
			aliasNameList.add("电话");
			aliasNameList.add("验证结果");
			List paTempContactTempList = this.empInfoDao.getTempContactTempList(paramMap,-1,-1);
			for(int i=0;i<paTempContactTempList.size();i++){
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap)paTempContactTempList.get(i);
				map.put("CELL0", map1.get("FAMILY_NO") == null ? "" : map1.get("FAMILY_NO"));
				map.put("CELL1", map1.get("PERSON_ID") == null ? "" : map1.get("PERSON_ID"));
				map.put("CELL2", map1.get("FAM_TYPE_CODE") == null ? "" : map1.get("FAM_TYPE_CODE"));
				map.put("CELL3", map1.get("FAM_NAME") == null ? "" : map1.get("FAM_NAME"));
				map.put("CELL4", map1.get("FAM_PHONE") == null ? "" : map1.get("FAM_PHONE"));
				map.put("CELL5", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			mapNameList.add("关系参考");
			mapList.add(codeSql + "1693");
		    name = "contactInfo";
		}else if ("4".equals(type)) {
			
			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("标题");
			aliasNameList.add("内容");
			aliasNameList.add("验证结果");
			List paTempAssistTempList = this.empInfoDao.getTempAssistTempList(paramMap,-1,-1);
			for(int i=0;i<paTempAssistTempList.size();i++){
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap)paTempAssistTempList.get(i);
				map.put("CELL0", map1.get("ASSIST_NO") == null ? "" : map1.get("ASSIST_NO"));
				map.put("CELL1", map1.get("PERSON_ID") == null ? "" : map1.get("PERSON_ID"));
				map.put("CELL2", map1.get("TITLE") == null ? "" : map1.get("TITLE"));
				map.put("CELL3", map1.get("CONTENTA") == null ? "" : map1.get("CONTENTA"));
				map.put("CELL4", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
		    name = "assistInfo";
		}
		return name;
	}

	/**
	 * 获取紧急联系人信息导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @date 2014-09-03
	 * @version V1.0
	 */
	public int getTempContactTempCnt(HttpServletRequest request, String errorFlag) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = empInfoDao.getTempContactTempErrorCnt(paramMap);
		}else{
			retrunInt = empInfoDao.getTempContactTempCnt(paramMap);
		}

		return retrunInt;
	}

	/**
	 * 获取紧急联系人导入的信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempcontactTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getTempContactTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getTempContactTempList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 获取辅助信息导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @date 2014-09-03
	 * @version V1.0
	 */
	public int getTempAssistTempCnt(HttpServletRequest request, String errorFlag) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = empInfoDao.getTempAssistTempErrorCnt(paramMap);
		}else{
			retrunInt = empInfoDao.getTempAssistTempCnt(paramMap);
		}

		return retrunInt;
	}

	/**
	 * 获取辅助导入的信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @version V1.0
	 */
	public List getTempAssistTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getTempAssistTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getTempAssistTempList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 辅助信息excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author zhaozhiyong@ait.net.cn
	 * @date 2014-09-13
	 * @version V1.0
	 */
	public String submitImportInfoExcelTempData(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String accrual=request.getParameter("accrual");
		if("assist".equals(accrual)) {
			paramMap.put("PR_INFO_NAME", "pkg_hrm_info_excel_imp.pr_import_temp_assist");
		}else if("contact".equals(accrual)) {                           
			paramMap.put("PR_INFO_NAME", "pkg_hrm_info_excel_imp.pr_import_temp_contact");
		}else if("disable".equals(accrual)){
			paramMap.put("PR_INFO_NAME", "pkg_hrm_info_excel_imp.pr_import_temp_disable");
		}else if("language".equals(accrual)){
			paramMap.put("PR_INFO_NAME", "pkg_hrm_info_excel_imp.pr_import_temp_language");
		}
		try {
			return this.empInfoDao.importInfoExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@Override
	public Object getTempEmpInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", admin.getLanguage());
		paramMap.put("CPNY_ID",  request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		paramMap.put("PERSON_ID",admin.getAdminID());
		paramMap.put("specialParam",admin.getSpecialParam());//这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo",admin.getDeptNo());//判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		//判断是否有超级管理员权限
		int authority = authorityUtil.isSuperUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "SuperUser");//超级用户权限
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getTempEmpInfoList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getTempEmpInfoList(paramMap) ;
		}
		
		return retrunList ;
	}

	@Override
	public Object getTempEmpInfoListCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID",  request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		paramMap.put("PERSON_ID",admin.getAdminID() );
		paramMap.put("specialParam",admin.getSpecialParam());//这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo",admin.getDeptNo());//判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		//判断是否有超级管理员权限
		int authority = authorityUtil.isSuperUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "SuperUser");//超级用户权限
		}
		
		return empInfoDao.getTempEmpInfoListCnt(paramMap) ;
	}

	@Override
	public Object getEmpTypeList(HttpServletRequest request) {

		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny"));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		retrunList = empInfoDao.getEmpTypeList(paramMap) ;
		
		return retrunList ;
	}

	/**
	 * 临时职批量入职列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getTmpEmpAffirmInfoListBatch(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		if(!paramMap.containsKey("FROM_TIME")){
			paramMap.put("FROM_TIME", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("TO_TIME", DateUtil.getSysdateStr());			
		}
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			returnList = empInfoDao.getTempEmpAffirmInfoListBatch(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = empInfoDao.getTempEmpAffirmInfoListBatch(paramMap);
		}
		return returnList;
	}

	/**
	 * 临时职批量入职总数
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getTmpEmpAffirmInfoListCntBatch(HttpServletRequest request) throws Exception {
		int ListCnt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("PERSON_ID", admin.getPersonId());
		ListCnt =  empInfoDao.getTempEmpAffirmInfoListCntBatch(paramMap);
		
		return ListCnt;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getImportTmpEmpResultList(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		if (UiUtil.getPageNum(request) > 0){
			retrunList =  empInfoDao.getImportTmpEmpResultList(paramMap , 
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
					);
		}else{
			retrunList =  empInfoDao.getImportTmpEmpResultList(paramMap, 1, 10);
		}
		return retrunList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getImportTmpEmpResultCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		return empInfoDao.getImportTmpEmpResultCnt(paramMap);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getImportTmpEmpErrCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		return empInfoDao.getImportTmpEmpErrCnt(paramMap);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String importTmpEmpFromExcel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String result = "";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDT_USER", admin.getUserNo()) ;
		paramMap.put("SUBSD_CD", admin.getCpnyId()) ;
		paramMap.put("UPDT_PERSON_ID", admin.getPersonId()) ;
		result = empInfoDao.importTmpEmpFromExcel(paramMap);
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getImportTmpEmpFromExcel(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		retrunList = empInfoDao.getImportTmpEmpResultList(paramMap) ;
		
		return retrunList ;
	}

	public int delTmpEmpInBatch(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("language", Messages.getLanguage(request));

		String[] isChecked = request.getParameterValues("tempEmpCheck");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				try {
					paramMap.put("BATCH_NO", isChecked[i].toString());
					empInfoDao.delTmpEmpInBatch(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
			}
		}
		
		return 1;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List viewTempEmpBatchReq(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		String   ls = "";
		String[] isChecked = request.getParameterValues("tempEmpCheck");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
			}
		}
		paramMap.put("BATCH_NOS", ls);
		paramMap.put("TRANS_NO", "1359");// 1359为入职发令
		
		return empInfoDao.getTempEmpBatchReqList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getTempEmpBatchReqCnt(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		String   ls = "";
		String[] isChecked = request.getParameterValues("tempEmpCheck");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
			}
		}
		paramMap.put("BATCH_NOS", ls);
		paramMap.put("TRANS_NO", "1359");// 1359为入职发令
		
		return empInfoDao.getTempEmpBatchReqCnt(paramMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getTempEmpBatchAffirmList(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		paramMap.put("BATCH_NOS", paramMap.get("BATCH_NO"));
		paramMap.put("REQ_ID", paramMap.get("APPLY_NO"));
		paramMap.put("TRANS_NO", "1359");// 1359为入职发令
		
		return empInfoDao.getTempEmpBatchReqList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getTempEmpBatchAffirmCnt(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		paramMap.put("BATCH_NOS", paramMap.get("BATCH_NO"));
		paramMap.put("REQ_ID", paramMap.get("APPLY_NO"));
		paramMap.put("TRANS_NO", "1359");// 1359为入职发令
		
		return empInfoDao.getTempEmpBatchReqCnt(paramMap);
	}

	public String getEmpBatchTemp(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String codeSql = "SELECT distinct T.CODE_NO NO,NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U, SY_CODE_PARAM SP WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.CODE_NO = SP.CODE_NO(+) AND T.PARENT_CODE_NO = ";
		String nameSql = "SELECT distinct NVL(U.CONTENT, ' ') NO, ' ' CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U, SY_CODE_PARAM SP WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.CODE_NO = SP.CODE_NO(+) AND T.PARENT_CODE_NO = ";
		
		aliasNameList.add("法人代码*");
		aliasNameList.add("部门代码*");
		aliasNameList.add("法人入职日期*");
		aliasNameList.add("入职类型*");
		aliasNameList.add("中文姓名*");
		aliasNameList.add("英文姓名*");
		aliasNameList.add("试用期开始日期*");
		aliasNameList.add("试用期结束日期*");
		aliasNameList.add("试用期比例[0~100]%*");
		aliasNameList.add("性别 [男/女]");
		aliasNameList.add("身份证号*");
		aliasNameList.add("最终学历");
		aliasNameList.add("生日");
		aliasNameList.add("ID卡号");
		aliasNameList.add("手机号码");
		aliasNameList.add("邮箱");
		aliasNameList.add("户口性质");
		aliasNameList.add("户口所在地");
		aliasNameList.add("工资级号");
		aliasNameList.add("工资级号等级");
		aliasNameList.add("基本工资");
		aliasNameList.add("变动工资");
		aliasNameList.add("开户行");
		aliasNameList.add("银行账号");
		aliasNameList.add("福利地区");
		aliasNameList.add("人员类型(CHR)");
		aliasNameList.add("工作地区");
		aliasNameList.add("工作类型(CHR)[定时工作制/不定时工作制]");
		aliasNameList.add("班号");
		aliasNameList.add("促销员所属");
		aliasNameList.add("星级级别");
		aliasNameList.add("产品");
		aliasNameList.add("兼卖产品");
		aliasNameList.add("职务");
		
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "TSTO");
		map.put("CELL1", "35261989");
		map.put("CELL2", "2014-12-01");
		map.put("CELL3", "新入职/再入职");
		map.put("CELL4", "中文名");
		map.put("CELL5", "Zhong Wenming");
		map.put("CELL6", "2014-12-01");
		map.put("CELL7", "2014-12-31");
		map.put("CELL8", "100");
		map.put("CELL9", "男/女");
		map.put("CELL10", "110102199012015555");
		map.put("CELL11", "211本科");
		map.put("CELL12", "1990-12-01");
		map.put("CELL13", "11111111111111");
		map.put("CELL14", "13900000000");
		map.put("CELL15", "exam@lge.com");
		map.put("CELL16", "Other");
		map.put("CELL17", "北京市朝阳区建国门外大街");
		map.put("CELL18", "A");
		map.put("CELL19", "G1");
		map.put("CELL20", "2000");
		map.put("CELL21", "500");
		map.put("CELL22", "中国银行建国门支行");
		map.put("CELL23", "6288888888888888");
		map.put("CELL24", "216765");
		map.put("CELL25", "211804");
		map.put("CELL26", "0132");
		map.put("CELL27", "定时工作制");
		map.put("CELL28", "CH_A2");
		map.put("CELL29", "211838");
		map.put("CELL30", "215955");
		map.put("CELL31", "LTV");
		map.put("CELL32", "WM;REF;A/C;MNT");
		map.put("CELL33", "employee 1");
		list.add(map);

		mapNameList.add("部门代码");
		mapList.add(" SELECT D.DEPTID NO, D.ORG_NAME_LOCAL CONTENT FROM HR_DEPARTMENT D "
                +" WHERE (D.DATE_ENDED IS NULL OR D.DATE_ENDED > SYSDATE) "
                +" AND D.CPNY_ID = '"+ admin.getCpnyId() +"' "
                +" AND D.ACTIVITY = 1 "
                +" AND f_check_user_dept_auth(D.DEPTNO,'"+ admin.getUserNo() +"','hr') = 1 " );
		mapNameList.add("最终学历");
		mapList.add(nameSql + "211777");
		mapNameList.add("户口性质");
		mapList.add(" SELECT DISTINCT A.REG_TYPE_CODE NO,' ' CONTENT "
                +" FROM hr_personal_info A,HR_EMPLOYEE B "
                +" WHERE A.PERSON_ID = B.PERSON_ID AND B.CPNY_ID = '"+ admin.getCpnyId() +"' "
                +" AND A.REG_TYPE_CODE IS NOT NULL ORDER BY A.REG_TYPE_CODE " );
		mapNameList.add("工资级号");
		mapList.add(" SELECT DISTINCT A.PAY_GRADE NO,' ' CONTENT "
                +" FROM  HR_EMP_PA_INFO A, HR_EMPLOYEE B "
                +" WHERE A.PERSON_ID = B.PERSON_ID AND B.CPNY_ID = '"+ admin.getCpnyId() +"' "
                +" ORDER BY A.PAY_GRADE " );
		mapNameList.add("工资级号等级");
		mapList.add(" SELECT DISTINCT A.PAY_STEP NO,' ' CONTENT "
                +" FROM  HR_EMP_PA_INFO A, HR_EMPLOYEE B "
                +" WHERE A.PERSON_ID = B.PERSON_ID AND B.CPNY_ID = '"+ admin.getCpnyId() +"' "
                +" ORDER BY A.PAY_STEP " );
		mapNameList.add("福利地区");
		mapList.add(codeSql + "216736 AND SP.CPNY_ID(+) = '"+ admin.getCpnyId() +"'");
		mapNameList.add("人员类型");
		mapList.add(" SELECT T.TEMP_EMPTYPE NO,SY.CONTENT CONTENT "
                +" FROM HR_TEMP_EMPTYPE   T,SY_GLOBAL_NAME SY "
                +" WHERE T.TEMP_EMPTYPE = SY.NO(+) AND SY.LANGUAGE(+) = 'zh' AND T.ACTIVITY = 1 "
                +" AND EXISTS ( SELECT 1 FROM HR_JOB_TYPE_SETUP D WHERE D.JOBTYPE_NO = T.TEMP_EMPTYPE AND EXISTS "
                +" (SELECT 1 FROM SY_SUPERVISOR_EMPTYPE_INFO E  WHERE E.EMP_TYPE_CODE = JOBTYPE_GROUP_NO AND PERSON_ID = '"+ admin.getPersonId() +"') "
                +" AND CPNY_ID = '"+ admin.getCpnyId() +"') AND T.CPNY_NAME = '"+ admin.getCpnyId() +"' " );
		mapNameList.add("工作地区");
		mapList.add(" SELECT NO, CONTENT FROM ( "
                +" SELECT DISTINCT A.STATE_CD NO, A.STATE_NM CONTENT, A.STATE_CD IDX "
                +" FROM  INC_CITY_TO_CITYLEVEL_MAPP A "
                +" UNION ALL "
                +" SELECT DISTINCT B.CITY_CD NO, B.CITY_NM CONTENT, B.STATE_CD||B.CITY_CD IDX "
                +" FROM  INC_CITY_TO_CITYLEVEL_MAPP B "
                +" UNION ALL "
                +" SELECT DISTINCT C.REGION_CD NO, C.REGION_NM CONTENT, C.STATE_CD||C.CITY_CD||C.REGION_CD IDX "
                +" FROM  INC_CITY_TO_CITYLEVEL_MAPP C "
                +" ) ORDER BY IDX " );
		mapNameList.add("工作类型(CHR)");
		mapList.add(nameSql + "211554 AND SP.CPNY_ID(+) = '"+ admin.getCpnyId() +"'");
		mapNameList.add("班号");
		mapList.add(" SELECT DISTINCT SHIFT_NO NO,' ' CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID = '"+ admin.getCpnyId() +"' AND SHIFT_NO IS NOT NULL" );
		mapNameList.add("促销员所属");
		mapList.add(codeSql + "211837 AND SP.CPNY_ID(+) = '"+ admin.getCpnyId() +"'");
		mapNameList.add("星级级别");
		mapList.add(codeSql + "215954 AND SP.CPNY_ID(+) = '"+ admin.getCpnyId() +"'");
		mapNameList.add("产品");
		mapList.add("SELECT distinct T.DESCRIPTION NO,NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U, SY_CODE_PARAM SP WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.CODE_NO = SP.CODE_NO(+) AND T.PARENT_CODE_NO = 211424 AND SP.CPNY_ID(+) = '"+ admin.getCpnyId() +"'");
		mapNameList.add("职务");
		mapList.add("SELECT distinct T.DESCRIPTION NO,NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U, SY_CODE_PARAM SP WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.CODE_NO = SP.CODE_NO(+) AND T.PARENT_CODE_NO = 14013573 AND SP.CPNY_ID(+) = '"+ admin.getCpnyId() +"'");

		return "EmpImport";
	}
	/**************************************************************************************************************/
	@Override
	@SuppressWarnings({"rawtypes", "unchecked" })
	public Object getTempEmpReqDetail(HttpServletRequest request) {
		Object returnObj = new Object() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		returnObj = empInfoDao.getTempEmpReqDetail(paramMap) ;
		return returnObj ;
	}

	/* 
	* Title: getInsrareaList
	* Description:查询法人人员所用的所有福利地区
	* @author 孙鹏  
	* @date 2015年3月16日 下午4:22:35  
	* @param request
	* @return 
	* @see com.ait.hrm.service.EmpInfoSer#getInsrareaList(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public Object getInsrareaList(HttpServletRequest request) {
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("defaultCpny", admin.getCpnyId());
		returnObj = empInfoDao.getInsrareaForCpnyId(paramMap) ;
		return returnObj ;
	}
	

	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int syncEmpInfo(HttpServletRequest request)
			throws CommonException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap personMap = new LinkedHashMap();
		personMap.put("APPLY_PERSON", admin.getPersonId());
		String op_flag = request.getParameter("OP_FLAG");
		String no = request.getParameter("SYNC_EMP");
		try {
			String[] paramData = no.split(",");
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = new LinkedHashMap();
				map.put("NO", paramData[i]);
				if("1".equals(op_flag)){//删除
					this.empInfoDao.deleteEmpMapping(map);
				}else if("2".equals(op_flag)){//同步年假
					map.put("FLAG", 1);
					this.empInfoDao.updateEmpMapping(map);
				}else {//同步调休
					map.put("FLAG", 2);
					this.empInfoDao.updateEmpMapping(map);
				}
			}
		}catch (CommonException e1) {
			throw e1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 新就社号mapping批量导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getMappingTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getMappingTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getMappingTempList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 新就社号mapping批量导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getMappingTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = empInfoDao.getMappingTempErrorCnt(paramMap);
		}else{
			retrunInt = empInfoDao.getMappingTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	/**
	 * 新就社号mapping批量excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelMappingEmpData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_SY_MAPPING_EXCEL_IMP.PR_IMPORT_EMP_MAPPING_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	

	
	
	/**
	 * 添加地址信息(add FamilyInfo)
	 * @param request
	 * @return int
	 */
	@Override
	public int addAddressInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			paramMap.put("CREATED_IP", admin.getAdminIP());

			this.empInfoDao.addAddressInfo(paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除社会信息(delete famliy information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteAddressInfo(HttpServletRequest request) {
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			String[] paramData = request.getParameterValues("FN");
			if(paramData !=null){
				for (int i = 0; i < paramData.length; i++) {
						paramMap.put("FAMILY_NO", paramData[i]);
						this.empInfoDao.deleteFamilyInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 修改社会信息(Modify family information)
	 * @param obj
	 * @return int
	 */
	@Override
	public int editAddressInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			
						
						this.empInfoDao.editAddressInfo(paramMap);
				
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	/**
	 * 工作经历信息(social relations)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getWorkInfo(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getAdminID() );
		}
		paramMap.put("WORK_EXPER_NO",request.getParameter("WORK_EXPER_NO"));
		returnList = empInfoDao.getHomeRelationList(paramMap) ;
		return returnList ;
	}
	
	/**
	 * 添加工作经历(add FamilyInfo)
	 * @param request
	 * @return int
	 */
	@Override
	public int essAddWorkInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CREATED_IP",admin.getAdminIP());
		

			int count=Integer.parseInt(request.getParameter("count"));
		
				this.empInfoDao.essAddWorkInfo(paramMap);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	/**
	 * 添加产品信息(add FamilyInfo)
	 * @param request
	 * @return int
	 */
	@Override
	public int essAddProductInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CREATED_IP",admin.getAdminIP());
		
            
			int count=Integer.parseInt(request.getParameter("count"));
		
				this.empInfoDao.essAddProductInfo(paramMap);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	/**
	 * 添加学历信息(add FamilyInfo)
	 * @param request
	 * @return int
	 */
	@Override
	public int essAddEducationInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CREATED_IP",admin.getAdminIP());
			paramMap.put("STUDY_EXPERIENCE",request.getParameter("STUDY_EXPERIENCE"));
			paramMap.put("FILENOSSTR",request.getParameter("FILENOSSTR"));
			int count=Integer.parseInt(request.getParameter("count"));
		
				this.empInfoDao.essAddEducationInfo(paramMap);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	
	/**
	 * 添加资格信息(add FamilyInfo)
	 * @param request
	 * @return int
	 */
	@Override
	public int essAddQualificationInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CREATED_IP",admin.getAdminIP());
			paramMap.put("FILENOSSTR",request.getParameter("FILENOSSTR"));
			int count=Integer.parseInt(request.getParameter("count"));
		
				this.empInfoDao.essAddQualificationInfo(paramMap);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 添加奖章信息(add FamilyInfo)
	 * @param request
	 * @return int
	 */
	@Override
	public int essAddRewardInfo(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CREATED_IP",admin.getAdminIP());
           
			
		

			int count=Integer.parseInt(request.getParameter("count"));
		
				this.empInfoDao.essAddRewardInfo(paramMap);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	/**
	 * 得到学历信息(work experience)
	 * @param request
	 * @return retrunList
	 */
	@Override
	public Object getEducationInfo(HttpServletRequest request) {
		Object  obj;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("APPLY_TYPE",request.getParameter("APPLY_TYPE"));
		paramMap.put("EDUC_NO",request.getParameter("EDUC_NO"));
			
			obj= empInfoDao.getEducationInfo(paramMap) ;

		return obj;
	}
	/**
	 * 变更明细申请
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public int getEssApplyListCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		if(admin.getStatNo() != null && !admin.getStatNo().equals("")){
			paramMap.put("STAT_NO", admin.getStatNo());
		}
		
		retrunInt = empInfoDao.getEssApplyListCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getEssApplyList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		if(admin.getStatNo() != null && !admin.getStatNo().equals("")){
			paramMap.put("STAT_NO", admin.getStatNo());
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				empInfoDao.getEssApplyList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = empInfoDao.getEssApplyList(paramMap) ;
		}
		
		return retrunList;
	}
	/**
	 * start  申请所用数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonalApplyList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("PERSON_NO", request.getParameter("PERSON_NO"));
        
		retrunList = empInfoDao.getPersonalApplyList(paramMap) ;
		return retrunList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getEssAddressApplyList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("ADDRESS_NO", request.getParameter("ADDRESS_NO"));

		retrunList = empInfoDao.getEssAddressApplyList(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEssEmergencyList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("EMERGENCY_NO", request.getParameter("EMERGENCY_NO"));

		retrunList = empInfoDao.getEssEmergencyList(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEssHomeRelationApplyList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("FAMILY_NO", request.getParameter("FAMILY_NO"));

		retrunList = empInfoDao.getEssHomeRelationApplyList(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEssWorkApplyList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("WORK_EXPER_NO", request.getParameter("WORK_EXPER_NO"));

		retrunList = empInfoDao.getEssWorkApplyList(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEssProductApplyList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("PRODUCT_NO", request.getParameter("PRODUCT_NO"));

		retrunList = empInfoDao.getEssProductApplyList(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEssEducationApplyList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("EDUC_NO", request.getParameter("EDUC_NO"));

		retrunList = empInfoDao.getEssEducationApplyList(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEssQualificationApplyList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("QUAL_NO", request.getParameter("QUAL_NO"));

		retrunList = empInfoDao.getEssQualificationApplyList(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEssRewardList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("REWARD_NO", request.getParameter("REWARD_NO"));
		retrunList = empInfoDao.getEssRewardList(paramMap) ;
		return retrunList;
	}
	
	/**
	 * end 申请所用数据
	 */
	
	
	
	/**
	 * start  申请所用数据 单个  Object 
	 * @param request
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonalApplyObject(HttpServletRequest request) {
		Object retrunList = new Object();
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("ADDRESS_NO", request.getParameter("ADDRESS_NO"));

		retrunList = empInfoDao.getPersonalApplyObject(paramMap) ;
		return retrunList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssAddressApplyObject(HttpServletRequest request) {
		Object retrunList = new Object();
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("ADDRESS_NO", request.getParameter("ADDRESS_NO"));

		retrunList = empInfoDao.getEssAddressApplyObject(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssEmergencyObject(HttpServletRequest request) {
		Object retrunList = new Object() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("EMERGENCY_NO", request.getParameter("EMERGENCY_NO"));

		retrunList = empInfoDao.getEssEmergencyObject(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssHomeRelationApplyObject(HttpServletRequest request) {
		Object retrunList = new Object() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("FAMILY_NO", request.getParameter("FAMILY_NO"));

		retrunList = empInfoDao.getEssHomeRelationApplyObject(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssWorkApplyObject(HttpServletRequest request) {
		Object retrunList = new Object() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("WORK_EXPER_NO", request.getParameter("WORK_EXPER_NO"));

		retrunList = empInfoDao.getEssWorkApplyObject(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssProductApplyObject(HttpServletRequest request) {
		Object retrunList = new Object() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("PRODUCT_NO", request.getParameter("PRODUCT_NO"));

		retrunList = empInfoDao.getEssProductApplyObject(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssEducationApplyObject(HttpServletRequest request) {
		Object retrunList = new Object() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("EDUC_NO", request.getParameter("EDUC_NO"));

		retrunList = empInfoDao.getEssEducationApplyObject(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssQualificationApplyObject(HttpServletRequest request) {
		Object retrunList = new Object() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("QUAL_NO", request.getParameter("QUAL_NO"));

		retrunList = empInfoDao.getEssQualificationApplyObject(paramMap) ;
		return retrunList;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssRewardObject(HttpServletRequest request) {
		Object retrunList = new Object() ;
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage",admin.getLanguage());
		
		paramMap.put("REWARD_NO", request.getParameter("REWARD_NO"));
		retrunList = empInfoDao.getEssRewardObject(paramMap) ;
		return retrunList;
	}
	
	/**
	 * end 申请所用数据
	 */
	/**
	 * 添加个人申请信息(add FamilyInfo)
	 * @param request
	 * @return int
	 */
	@Override
	public int addPersonal(HttpServletRequest request) {
		try {
			//页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CREATED_IP",admin.getAdminIP());
			paramMap.put("interLanguage",admin.getLanguage());
			
			paramMap.put("MARITAL_STATUS_CODE",request.getParameter("MARITAL_STATUS_CODE"));
			paramMap.put("OFFICE_PHONE",request.getParameter("OFFICE_PHONE"));
			paramMap.put("EMAIL_SECOND",request.getParameter("EMAIL_SECOND"));
			paramMap.put("CELLPHONE",request.getParameter("CELLPHONE"));
			paramMap.put("HOME_PHONE",request.getParameter("HOME_PHONE"));
			paramMap.put("REG_PLACE",request.getParameter("REG_PLACE"));
			paramMap.put("HOME_ADDRESS",request.getParameter("HOME_ADDRESS"));
			paramMap.put("CHILDREN_CODE",request.getParameter("CHILDREN_CODE"));
			paramMap.put("LONG_TERM",request.getParameter("LONG_TERM"));
			
			int count=Integer.parseInt(request.getParameter("count"));
			
		
				this.empInfoDao.addPersonal(paramMap);
				
				/*if(admin.getCpnyId().equals("SPC_NJ")){
					//更新
					this.EssApplyInfoSer.updatePersonalInfo(request);
				}*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	
	/**
	 * 个人照片上传申请
	 */
	@SuppressWarnings("unchecked")
	public int updateRecruitPhotoInfo(HttpServletRequest request, String photoPath) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PHOTO_PATH", photoPath);
		paramMap.put("CREATED_IP",admin.getAdminIP());
		paramMap.put("currentIndex", 5);
		try {
			this.empInfoDao.addRecruitInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public List viewInfoList(HttpServletRequest request, String target) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		param.put("PERSON_ID", admin.getPersonId());
		list = this.empInfoDao.viewInfoList(param, target);
		return list;
	}
}
