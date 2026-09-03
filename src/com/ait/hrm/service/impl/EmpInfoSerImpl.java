package com.ait.hrm.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DateFormat;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.hrm.dao.EmpInfoDao;
import com.ait.hrm.dao.JobTypeDao;
import com.ait.hrm.dao.RecruitManageDao;
import com.ait.hrm.dao.TransferOrderDao;
import com.ait.hrm.service.EmpInfoSer;
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

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: EmpInfoSerImpl.java
 * @Description: implement Class EmpInfoSer.java
 * @Create date: Jan 16, 2012 3:28:57 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
@Service
@SuppressWarnings( { "unchecked", "unused" })
public class EmpInfoSerImpl implements EmpInfoSer {

	Logger logger = Logger.getLogger(EmpInfoSerImpl.class);
	@Autowired
	private EmpInfoDao empInfoDao;
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
	private RecruitManageDao recruitManageDao;

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
		for (Map data : list) {
			if ("add".equals(data.get("__status"))) {
				this.empInfoDao.addFamilyInfo(data);
			} else if ("update".equals(data.get("__status"))) {
				this.empInfoDao.updateFamilyInfo(data);
			} else if ("delete".equals(data.get("__status"))) {
				this.empInfoDao.deleteFamilyInfo(data);
			}
		}
		return null;
	}

	@Override
	public String updateSocietyRelationGrid(
			List<LinkedHashMap<String, Object>> list) throws Exception {
		String temp = "";
		for (Map data : list) {
			if ("add".equals(data.get("__status"))) {
				this.empInfoDao.addSocietyRelation(data);
			} else if ("update".equals(data.get("__status"))) {
				this.empInfoDao.updateSocietyRelation(data);
			} else if ("delete".equals(data.get("__status"))) {
				this.empInfoDao.deleteSocietyRelation(data);
			}
		}
		return null;
	}

	@Override
	public String updateExperienceInfoGrid(
			List<LinkedHashMap<String, Object>> list) throws Exception {
		for (Map data : list) {
			if ("add".equals(data.get("__status"))) {
				this.empInfoDao.addExperienceInfo(data);
			} else if ("update".equals(data.get("__status"))) {
				this.empInfoDao.updateExperienceInfo(data);
			} else if ("delete".equals(data.get("__status"))) {
				this.empInfoDao.deleteExperienceInfo(data);
			}
		}
		return null;
	}

	@Override
	public String updateFappendInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception {
		return updateFappendInfoGrid(list, null);
	}

	@Override
	public String updateFappendInfoGrid(
			List<LinkedHashMap<String, Object>> list, HttpServletRequest request)
			throws Exception {
		String updateBy = null;
		if (request != null) {
			updateBy = request.getParameter("updateBy");
		}
		for (Map data : list) {
			data.put("updateBy", updateBy);
			data.put("registerID", updateBy);
			if ("add".equals(data.get("__status"))) {
				this.empInfoDao.addFappendInfo(data);
			} else if ("update".equals(data.get("__status"))) {
				this.empInfoDao.updateFappendInfo(data);
			} else if ("delete".equals(data.get("__status"))) {
				this.empInfoDao.deleteFappendInfo(data);
			}
		}
		return null;
	}

	@Override
	public String updateHealthInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception {
		for (Map data : list) {
			if ("add".equals(data.get("__status"))) {
				this.empInfoDao.addHealthInfo(data);
			} else if ("update".equals(data.get("__status"))) {
				this.empInfoDao.updateHealthInfo(data);
			} else if ("delete".equals(data.get("__status"))) {
				this.empInfoDao.deleteHealthInfo(data);
			}
		}
		return null;
	}

	@Override
	public String updateLanuageInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception {
		for (Map data : list) {
			if ("add".equals(data.get("__status"))) {
				this.empInfoDao.addLanuageInfo(data);
			} else if ("update".equals(data.get("__status"))) {
				this.empInfoDao.updateLanuageInfo(data);
			} else if ("delete".equals(data.get("__status"))) {
				this.empInfoDao.deleteLanuageInfo(data);
			}
		}
		return null;
	}

	@Override
	public String updatePaEmpInfoGrid(List<LinkedHashMap<String, Object>> list)
			throws Exception {
		for (Map data : list) {
			if ("add".equals(data.get("__status"))) {
				this.empInfoDao.addPaEmpInfo(data);
			} else if ("update".equals(data.get("__status"))) {
				this.empInfoDao.updatePaEmpInfo(data);
			} else if ("delete".equals(data.get("__status"))) {
				this.empInfoDao.deletePaEmpInfo(data);
			}
		}
		return null;
	}

	public String updatePaEmpInfoGrid(List<LinkedHashMap<String, Object>> list,
			HttpServletRequest request) throws Exception {
		String updateBy = null;
		if (request != null) {
			updateBy = request.getParameter("updateBy");
		}
		for (Map data : list) {
			data.put("EMPID", updateBy);
			if ("add".equals(data.get("__status"))) {
				this.empInfoDao.addPaEmpInfo(data);
			} else if ("update".equals(data.get("__status"))) {
				this.empInfoDao.updatePaEmpInfo(data);
			} else if ("delete".equals(data.get("__status"))) {
				this.empInfoDao.deletePaEmpInfo(data);
			}
		}
		return null;
	}

	@Override
	public String updateQualificationInfoGrid(
			List<LinkedHashMap<String, Object>> list) throws Exception {
		for (Map data : list) {
			if ("add".equals(data.get("__status"))) {
				this.empInfoDao.addQualificationInfo(data);
			} else if ("update".equals(data.get("__status"))) {
				this.empInfoDao.updateQualificationInfo(data);
			} else if ("delete".equals(data.get("__status"))) {
				this.empInfoDao.deleteQualificationInfo(data);
			}
		}
		return null;
	}

	public Object getPersonalInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getPersonId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getPersonalInfoByPid(paramMap);
		// return empInfoDao.getPersonalInfo(paramMap);
	}

	/**
	 * 员工基础信息 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object getPersonalInfoByPid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getPersonalInfoByPid(paramMap);
	}

	/**
	 * 转正员工基础信息 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */

	@Override
	public Object getPersonalInfoByPid2(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getPersonalInfoByPid2(paramMap);
	}
	
	
	
	
	@Override
	public Object getTitle(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		return empInfoDao.getTitle(paramMap);
	}

	/**
	 * 员工基础信息 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object SinglePersonalInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.SinglePersonalInfo(paramMap);
	}

	/**
	 * 最终学校信息 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object getfinaEdu(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getfinaEdu(paramMap);
	}

	/**
	 * 基本信息页面的employee信息 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewEmpInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewEmpInfo(paramMap);
	}

	/**
	 * 复制地址 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public String copyAddress(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.copyAddress(paramMap);
	}

	/**
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List codeReason(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.codeReason(paramMap);
	}

	/**
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List queryDepartment(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String QUERYDEPTNAME = StringUtil.checkNull(request
				.getParameter("QUERYDEPTNAME"));
		if (!"".equals(QUERYDEPTNAME)) {
			paramMap.put("QUERYDEPTNAME", QUERYDEPTNAME);
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.queryDepartment(paramMap);
	}

	/**
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List searchTitlename(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.searchTitlename(paramMap);
	}

	/**
	 * 紧急联系人列表 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List gethrEmergencyAddressList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.gethrEmergencyAddressList(paramMap);
	}

	/**
	 * 地址类型列表 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewAddressMattersList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewAddressMattersList(paramMap);
	}

	/**
	 * 
	 */
	@Override
	public List searchTanchu(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.searchTanchu(paramMap);
	}

	/**
	 * 
	 */
	@Override
	public List zhiji(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (admin.getCpnyId().equals("HTSV")) {
			paramMap.put("PARENT_CODE_NO",
					"'14015813','14015815'");
		} else if (admin.getCpnyId().equals("SST")) {
			paramMap.put("PARENT_CODE_NO",
					"'14015088','14015089','14015090','14015091'");
		} else {
			paramMap.put("PARENT_CODE_NO", "''");
		}

		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.zhiji(paramMap);
	}

	@Override
	public List zhijiInfor(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		if (request.getParameter("PARENT_CODE_NO") == null
				|| "".equals(request.getParameter("PARENT_CODE_NO"))) {
			paramMap.put("PARENT_CODE_NO", "''");
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.zhiji(paramMap);
	}

	/**
	 * 
	 */
	@Override
	public List employeeSearchResultsTanchu(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String sqlcontent = empInfoDao.querySearchContent(paramMap);
		if ("".equals(sqlcontent) || sqlcontent == null) {
			//sqlcontent = " from hr_employee h,hr_personal_info p,HR_EMPLOYEE_RECRUIT s,hr_department d,pa_emp_account pea,HR_EXPERIENCE_INSIDE hei where h.deptno=d.deptno(+) and h.person_id=s.person_id(+) and h.person_id=p.person_id(+) and h.person_id = pea.person_id(+) and pea.activity(+) = 1 and h.person_id = hei.person_id(+) and h.empid not like '11111%' and h.cpny_id=#CPNY_ID:VARCHAR# ";
			sqlcontent = " from hr_employee h,hr_personal_info p where h.person_id=p.person_id(+) and h.empid not like '11111%' and h.cpny_id=#CPNY_ID:VARCHAR# ";
		}
		if (sqlcontent.indexOf("#CPNY_ID:VARCHAR#") > -1) {
			sqlcontent = sqlcontent.replace("#CPNY_ID:VARCHAR#", "'"
					+ admin.getCpnyId() + "'");
			sqlcontent = sqlcontent.replace("#CPNY_ID:VARCHAR#", "'"
					+ admin.getCpnyId() + "'");
		}
		String postgradeno = StringUtil.checkNull(request
				.getParameter("POST_GRADE_NO"));
		String DEGREE_CODE = StringUtil.checkNull(request
				.getParameter("DEGREE_CODE"));
		if (DEGREE_CODE != null && !"".equals(DEGREE_CODE)) {
			int index1 = sqlcontent
					.indexOf("hr_employee h, hr_personal_info p");
			if (index1 > -1) {
				sqlcontent = sqlcontent.substring(0, index1)
						+ "hr_education edu,"
						+ sqlcontent.substring(index1, sqlcontent.length());
			}
			int index2 = sqlcontent.indexOf("h.person_id=p.person_id(+)");
			if (index2 > -1) {
				sqlcontent = sqlcontent.substring(0, index2)
						+ "h.person_id=edu.person_id(+) and "
						+ sqlcontent.substring(index2, sqlcontent.length());
			}

		}
		paramMap.put("CONTENT", sqlcontent);
		// 提取资料的方法
		String str = this.tiquziliao(request, sqlcontent);
		try {
			paramMap.put("SQL_STMT", str);
			this.empInfoDao.tiquziliao(paramMap);
		} catch (Exception e) {

			e.printStackTrace();
		}
		// 提取资料的方法
		return empInfoDao.employeeSearchResultsTanchu(paramMap);
	}

	public String tiquziliao(HttpServletRequest request, String sqlcontent) {
		String str = sqlcontent;
		if (str.indexOf("ROWNUM") > -1) {
			str = str.replace("ROWNUM", "ROWNUM NO");
		}
		if (str.indexOf("h.empid") > -1) {
			str = str.replace("h.empid", "h.empid EMPID");
		}
		if (str.indexOf("h.person_id,") > -1) {
			str = str.replace("h.person_id,", "");
		}
		if (str.indexOf("h.local_name") > -1) {
			str = str.replace("h.local_name", "h.local_name NAME");
		}
		
		if (str.indexOf("GET_DEPT_NAME(h.DEPTNO, 'vi') DEPTNO") > -1) {
			str = str.replace("GET_DEPT_NAME(h.DEPTNO, 'vi') DEPTNO", 
					"GET_DEPT_NAME(h.DEPTNO, 'vi') DEPARTMENT");
		}
		
		if (str.indexOf("HEAD_DEPARTMENT") > -1) {
			str = str.replace("GET_DEPT_MANAGER_INFO(h.DEPTNO) HEAD_DEPARTMENT", 
					"GET_DEPT_MANAGER_INFO(h.DEPTNO) DEPARTMENT_HEAD");
		}

		if (str.indexOf("GET_GLOBAL_NAME(h.POST_FAMILY, 'vi') POST_FAMILY") > -1) {
			str = str.replace(
					"GET_GLOBAL_NAME(h.POST_FAMILY, 'vi') POST_FAMILY",
					"GET_GLOBAL_NAME(h.POST_FAMILY, 'vi') POST_GROUP");
		}
		
		if (str.indexOf("GET_GLOBAL_NAME(h.POST_GRADE_NO, 'vi') POST_GRADE_NO") > -1) {
			str = str.replace(
					"GET_GLOBAL_NAME(h.POST_GRADE_NO, 'vi') POST_GRADE_NO",
					"GET_GLOBAL_NAME(h.POST_GRADE_NO, 'vi') RANK");
		}

		if (str.indexOf("GET_GLOBAL_NAME(h.POSITION_NO, 'vi') POSITION_NO") > -1) {
			str = str.replace(
					"GET_GLOBAL_NAME(h.POSITION_NO, 'vi') POSITION_NO",
					"GET_GLOBAL_NAME(h.POSITION_NO, 'vi') DUTY");
		}
		
		if (str.indexOf("GET_GLOBAL_NAME(h.EMP_TYPE_CODE, 'vi') EMP_TYPE_CODE") > -1) {
			str = str.replace(
					"GET_GLOBAL_NAME(h.EMP_TYPE_CODE, 'vi') EMP_TYPE_CODE",
					"GET_GLOBAL_NAME(h.EMP_TYPE_CODE, 'vi') EMPLOYEE_TYPE");
		}
		
		if (str.indexOf("GET_GLOBAL_NAME(h.MAIN_BUSINESS, 'vi') MAIN_BUSINESS") > -1) {
			str = str.replace(
					"GET_GLOBAL_NAME(h.MAIN_BUSINESS, 'vi') MAIN_BUSINESS",
					"GET_GLOBAL_NAME(h.MAIN_BUSINESS, 'vi') MAJOR_BUSINESS");
		}
		
		if (str.indexOf("GET_GLOBAL_NAME(h.COST_CENTER, 'vi') COST_CENTER") > -1) {
			str = str.replace(
					"GET_GLOBAL_NAME(h.COST_CENTER, 'vi') COST_CENTER",
					"GET_GLOBAL_NAME(h.COST_CENTER, 'vi') COST_CENTER");
		}

		if (str.indexOf("GET_GLOBAL_NAME(h.EMP_OFFICE, 'vi') EMP_OFFICE") > -1) {
			str = str.replace("GET_GLOBAL_NAME(h.EMP_OFFICE, 'vi') EMP_OFFICE",
					"GET_GLOBAL_NAME(h.EMP_OFFICE, 'vi') STATE_SERVICE");
		}

		if (str.indexOf("to_char(h.DATE_STARTED,'dd/MM/yyyy') DATE_STARTED") > -1) {
			str = str.replace(
					"to_char(h.DATE_STARTED,'dd/MM/yyyy') DATE_STARTED",
					"to_char(h.DATE_STARTED,'dd/MM/yyyy') ENTRY_DATE");
		}
		
		if (str.indexOf("to_char(h.END_PROBATION_DATE,'dd/MM/yyyy') END_PROBATION_DATE") > -1) {
			str = str.replace(
					"to_char(h.END_PROBATION_DATE,'dd/MM/yyyy') END_PROBATION_DATE",
					"to_char(h.END_PROBATION_DATE,'dd/MM/yyyy') PROBATION_END_DATE");
		}
		
		if (str.indexOf("to_char(h.DATE_LEFT,'dd/MM/yyyy') DATE_LEFT") > -1) {
			str = str.replace("to_char(h.DATE_LEFT,'dd/MM/yyyy') DATE_LEFT",
					"to_char(h.DATE_LEFT,'dd/MM/yyyy') LEAVE_DATE");
		}
		
		if (str.indexOf("p.IDCARD_NO") > -1) {
			str = str.replace("p.IDCARD_NO IDCARD_NO", 
					"p.IDCARD_NO ID_NUMBER");
		}

		if (str.indexOf("p.IDCARD_START_DATE") > -1) {
			str = str.replace(
					"p.IDCARD_START_DATE", "p.IDCARD_START_DATE DATE_EVIDENCE");
		}

		if (str.indexOf("GET_GLOBAL_NAME(p.SEXCODE, 'vi') SEXCODE") > -1) {
			str = str.replace("GET_GLOBAL_NAME(p.SEXCODE, 'vi') SEXCODE",
					"GET_GLOBAL_NAME(p.SEXCODE, 'vi') GENDER");
		}
		
		if (str.indexOf("to_char(p.DOB,'dd/MM/yyyy') DOB") > -1) {
			str = str.replace("to_char(p.DOB,'dd/MM/yyyy') DOB",
					"to_char(p.DOB,'dd/MM/yyyy') BIRTHDAY");
		}
		
		if (str.indexOf("to_char(SYSDATE,'yyyy')-to_char(p.DOB,'yyyy') AGE") > -1) {
			str = str.replace(
					"to_char(SYSDATE,'yyyy')-to_char(p.DOB,'yyyy') AGE",
					"to_char(SYSDATE,'yyyy')-to_char(p.DOB,'yyyy') AGE");
		}
		
		if (str.indexOf("GET_GLOBAL_NAME(p.MARITAL_STATUS_CODE, 'vi') MARITAL_STATUS_CODE") > -1) {
			str = str.replace(
					"GET_GLOBAL_NAME(p.MARITAL_STATUS_CODE, 'vi') MARITAL_STATUS_CODE",
					"GET_GLOBAL_NAME(p.MARITAL_STATUS_CODE, 'vi') MARRIAGE_DISTINCTION");
		}
		
		if (str.indexOf("to_char(p.WEDDING_DATE,'dd/MM/yyyy') WEDDING_DATE") > -1) {
			str = str.replace(
					"to_char(p.WEDDING_DATE,'dd/MM/yyyy') WEDDING_DATE",
					"to_char(p.WEDDING_DATE,'dd/MM/yyyy') MARRIAGE_DATE");
		}
		
		if (str.indexOf("GET_GLOBAL_NAME(p.NATIONALITY_CODE, 'vi') NATIONALITY_CODE") > -1) {
			str = str.replace("GET_GLOBAL_NAME(p.NATIONALITY_CODE, 'vi') NATIONALITY_CODE",
					"GET_GLOBAL_NAME(p.NATIONALITY_CODE, 'vi') NATIONALITY");
		}
		
		if (str.indexOf("GET_GLOBAL_NAME(p.NATION_CODE, 'vi') NATION_CODE") > -1) {
			str = str.replace("GET_GLOBAL_NAME(p.NATION_CODE, 'vi') NATION_CODE",
					"GET_GLOBAL_NAME(p.NATION_CODE, 'vi') NATION");
		}
		
		if (str.indexOf("GET_GLOBAL_NAME(p.POLITICAL_OUTLOOK, 'vi') POLITICAL_OUTLOOK") > -1) {
			str = str.replace("GET_GLOBAL_NAME(p.POLITICAL_OUTLOOK, 'vi') POLITICAL_OUTLOOK",
					"GET_GLOBAL_NAME(p.POLITICAL_OUTLOOK, 'vi') POLITICAL_OUTLOOK");
		}
		
		if (str.indexOf("p.HOME_PHONE") > -1) {
			str = str.replace("p.HOME_PHONE HOME_PHONE", 
					"p.HOME_PHONE HOME_PHONE");
		}
		
		if (str.indexOf("p.CELLPHONE") > -1) {
			str = str.replace("p.CELLPHONE CELLPHONE", 
					"p.CELLPHONE PHONE_NUMBER");
		}
		
		if (str.indexOf("GET_GLOBAL_NAME(p.EXIST_SINGLE, 'vi') EXIST_SINGLE") > -1) {
			str = str.replace("GET_GLOBAL_NAME(p.EXIST_SINGLE, 'vi') EXIST_SINGLE",
					"GET_GLOBAL_NAME(p.EXIST_SINGLE, 'vi') EagleM_YN");
		}
		
		if (str.indexOf("p.SING_ID") > -1) {
			str = str.replace("p.SING_ID", "p.SING_ID EagleM_ID");
		}
		
		if (str.indexOf("p.RELIGION") > -1) {
			str = str.replace("p.RELIGION", "p.RELIGION RELIGION");
		}
		
		if (str.indexOf("p.ISSUING_AUTHORITY") > -1) {
			str = str.replace("p.ISSUING_AUTHORITY","p.ISSUING_AUTHORITY SIGNING_ORGANIZATION");
		}
		
		if (str.indexOf("p.EMAIL") > -1) {
			str = str.replace("p.EMAIL,", "p.EMAIL COMPANY_MAILBOX,");
		}
		
		if (str.indexOf("p.EMAIL_SECOND") > -1) {
			str = str.replace("p.EMAIL_SECOND", "p.EMAIL_SECOND PERSONAL_MAILBOX");
		}
		
		if (str.indexOf("GET_GLOBAL_NAME(p.RESIDENTIAL_DISTINCTION, 'vi') RESIDENTIAL_DISTINCTION") > -1) {
			str = str.replace("GET_GLOBAL_NAME(p.RESIDENTIAL_DISTINCTION, 'vi') RESIDENTIAL_DISTINCTION",
					"GET_GLOBAL_NAME(p.RESIDENTIAL_DISTINCTION, 'vi') RESIDENCE_DISTINCTION");
		}
		
		if (str.indexOf("p.REG_PLACE") > -1) {
			str = str.replace("p.REG_PLACE","p.REG_PLACE REGISTERRED_RESIDENCE");
		}
		
		if (str.indexOf("p.ORIGIN") > -1) {
			str = str.replace("p.ORIGIN","p.ORIGIN NATIVE_HEATH");
		}
		
		if (str.indexOf("p.ARMY_OR_NOT") > -1) {
			str = str.replace("p.ARMY_OR_NOT","p.ARMY_OR_NOT JOIN_ARMY_YN");
		}
		
		if (str.indexOf("p.OBSTACLE_OR_NOT") > -1) {
			str = str.replace("p.OBSTACLE_OR_NOT","p.OBSTACLE_OR_NOT OBSTACLE_YN");
		}
		
		if (str.indexOf("GET_GLOBAL_NAME(p.CV_UPDATE_STATUS, 'vi') CV_UPDATE_STATUS") > -1) {
			str = str.replace("GET_GLOBAL_NAME(p.CV_UPDATE_STATUS, 'vi') CV_UPDATE_STATUS",
					"GET_GLOBAL_NAME(p.CV_UPDATE_STATUS, 'vi') CV_UPDATE_STATUS");
		}
		
		if (str.indexOf("p.FILE_LOCATION") > -1) {
			str = str.replace("p.FILE_LOCATION","p.FILE_LOCATION ARCHIVES_LOCATION");
		}
		
		if (str.indexOf("to_char(p.FILE_ENTER,'dd/MM/yyyy') FILE_ENTER") > -1) {
			str = str.replace(
					"to_char(p.FILE_ENTER,'dd/MM/yyyy') FILE_ENTER",
					"to_char(p.FILE_ENTER,'dd/MM/yyyy') ARCHIVES_INTO");
		}
		
		if (str.indexOf("to_char(p.FILE_OUT,'dd/MM/yyyy') FILE_OUT") > -1) {
			str = str.replace(
					"to_char(p.FILE_OUT,'dd/MM/yyyy') FILE_OUT",
					"to_char(p.FILE_OUT,'dd/MM/yyyy') ARCHIVES_OUT");
		}
		/*if (str.indexOf("HC1.CONTRACT_DATE CONTRACT_DATE") > -1) {
			str = str.replace("HC1.CONTRACT_DATE CONTRACT_DATE", "HC1.CONTRACT_DATE 合同起止时间");
		}
		


		if (str.indexOf("GET_GLOBAL_NAME(h.DIRECT_INDIRECT_DISTINCTION, 'zh') DIRECT_INDIRECT_DISTINCTION") > -1) {
			str = str.replace(
					"GET_GLOBAL_NAME(h.DIRECT_INDIRECT_DISTINCTION, 'zh') DIRECT_INDIRECT_DISTINCTION",
					"GET_GLOBAL_NAME(h.DIRECT_INDIRECT_DISTINCTION, 'zh') 直间接区分");
		}*/

		String DEPTNO20 = StringUtil
				.checkNull(request.getParameter("seach_DEPTNO_Multi"));
		if (DEPTNO20 != null && !"".equals(DEPTNO20)) {
			str = str
					+ "and  h.deptno in (" + DEPTNO20 + ")";
		}
		String LOCAL_NAME = StringUtil.checkNull(request
				.getParameter("LOCAL_NAME"));
		if (LOCAL_NAME != null && !"".equals(LOCAL_NAME)) {
			str = str + "and (h.LOCAL_NAME LIKE '%' || '" + LOCAL_NAME
					+ "' || '%' OR h.EMPID LIKE ('%' || '" + LOCAL_NAME
					+ "' || '%') OR upper(h.CHINESE_PINYIN) LIKE ('%' || '"
					+ LOCAL_NAME
					+ "' || '%') or lower(h.CHINESE_PINYIN) LIKE ('%' || '"
					+ LOCAL_NAME + "' || '%') OR h.ENGLISH_NAME LIKE ('%' || '"
					+ LOCAL_NAME + "' || '%') OR h.KOREAN_NAME LIKE ('%' || '"
					+ LOCAL_NAME + "' || '%')) ";
		}
		String EMPID = StringUtil.checkNull(request.getParameter("EMPID"));
		if (EMPID != null && !"".equals(EMPID)) {
			str = str + "and h.EMPID = '" + EMPID + "' ";
		}
		String POST_FAMILY = StringUtil.checkNull(request
				.getParameter("POST_FAMILY"));
		if (POST_FAMILY != null && !"".equals(POST_FAMILY)) {
			str = str + "and h.POST_FAMILY in (" + POST_FAMILY + ") ";
		}
		String MAIN_BUSINESS = StringUtil.checkNull(request
				.getParameter("MAIN_BUSINESS"));
		if (MAIN_BUSINESS != null && !"".equals(MAIN_BUSINESS)) {
			str = str + "and h.MAIN_BUSINESS in (" + MAIN_BUSINESS + ") ";
		}
		String EMP_TYPE_CODE = StringUtil.checkNull(request
				.getParameter("EMP_TYPE_CODE"));
		if (EMP_TYPE_CODE != null && !"".equals(EMP_TYPE_CODE)) {
			str = str + "and h.EMP_TYPE_CODE in (" + EMP_TYPE_CODE + ") ";
		}
		String EMP_OFFICE = StringUtil.checkNull(request
				.getParameter("EMP_OFFICE"));
		if (EMP_OFFICE != null && !"".equals(EMP_OFFICE)) {
			str = str + "and h.EMP_OFFICE in (" + EMP_OFFICE + ") ";
		}
		String DEGREE_CODE = StringUtil.checkNull(request
				.getParameter("DEGREE_CODE"));
		if (DEGREE_CODE != null && !"".equals(DEGREE_CODE)) {
			str = str + "and edu.DEGREE_CODE in (" + DEGREE_CODE + ") ";
		}
		String NATIONALITY_CODE = StringUtil.checkNull(request.getParameter("NATIONALITY_CODE"));
		if (NATIONALITY_CODE != null && !"".equals(NATIONALITY_CODE)) {
			str = str + "and p.NATIONALITY_CODE in (" + NATIONALITY_CODE + ") ";
		}
		String SEXCODE = StringUtil.checkNull(request.getParameter("SEXCODE"));
		if (SEXCODE != null && !"".equals(SEXCODE)) {
			str = str + "and p.SEXCODE in (" + SEXCODE + ") ";
		}
		String DOB_START_DATE = StringUtil.checkNull(request
				.getParameter("DOB_START_DATE"));
		if (DOB_START_DATE != null && !"".equals(DOB_START_DATE)) {
			str = str + "and p.dob>=to_date('" + DOB_START_DATE
					+ "','dd/MM/yyyy') ";
		}
		String DOB_END_DATE = StringUtil.checkNull(request
				.getParameter("DOB_END_DATE"));
		if (DOB_END_DATE != null && !"".equals(DOB_END_DATE)) {
			str = str + "and p.dob<=to_date('" + DOB_END_DATE
					+ "','dd/MM/yyyy') ";
		}
		/*String BIRTHDAY = StringUtil.checkNull(request
				.getParameter("BIRTHDAY"));
		if (BIRTHDAY != null && !"".equals(BIRTHDAY)) {
			str = str + "and to_char(p.dob,'MM') = '" + BIRTHDAY + "' ";
		}*/
		String age_start = StringUtil.checkNull(request
				.getParameter("age_start"));
		if (age_start != null && !"".equals(age_start)) {
			str = str + "and to_char(sysdate,'yyyy')-'" + age_start
					+ "' >=to_char(p.dob,'yyyy') ";
		}
		String age_end = StringUtil.checkNull(request.getParameter("age_end"));
		if (age_end != null && !"".equals(age_end)) {
			str = str + "and to_char(sysdate,'yyyy')-'" + age_end
					+ "' <=to_char(p.dob,'yyyy') ";
		}
		String DATE_STARTED = StringUtil.checkNull(request
				.getParameter("DATE_STARTED"));
		if (DATE_STARTED != null && !"".equals(DATE_STARTED)) {
			str = str + "and h.DATE_STARTED>=to_date('" + DATE_STARTED
					+ "','dd/MM/yyyy') ";
		}
		String DATE_END = StringUtil
				.checkNull(request.getParameter("DATE_END"));
		if (DATE_END != null && !"".equals(DATE_END)) {
			str = str + "and h.DATE_STARTED<=to_date('" + DATE_END
					+ "','dd/MM/yyyy') ";
		}

		return str;
	}

	/**
	 * 
	 */
	@Override
	public List searchname(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.searchname(paramMap);
	}

	/**
	 * 
	 */
	@Override
	public List chengbenzhongxin(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.chengbenzhongxin(paramMap);
	}

	/**
	 * 家庭关系列表 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewFamilyList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewFamilyList(paramMap);
	}

	/**
	 * 地址类型表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List gethrAddressMattersLists(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.gethrAddressMattersLists(paramMap);
	}

	/**
	 * 家庭关系表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List gethrFamilyList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.gethrFamilyList(paramMap);
	}
	
	@Override
	public List getStatisticsBureau(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getStatisticsBureau(paramMap);
	}
	
	@Override
	public List getHRDaily(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getHRDaily(paramMap);
	}
	
	@Override
	public List getPostStores(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getPostStores(paramMap);
	}
	
	@Override
	public List BirthdayWelfare(HttpServletRequest request) {
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

		return empInfoDao.BirthdayWelfare(paramMap);
	}
	
	public List RecruitReport(HttpServletRequest request) {
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

		return empInfoDao.RecruitReport(paramMap);
	}
	
	@Override
	public List RuZhiEmployee(HttpServletRequest request) {
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

		return empInfoDao.RuZhiEmployee(paramMap);
	}
	
	@Override
	public List LiZhiEmployee(HttpServletRequest request) {
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

		return empInfoDao.LiZhiEmployee(paramMap);
	}
	
	@Override
	public List RuZhiStatistic(HttpServletRequest request) {
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

		return empInfoDao.RuZhiStatistic(paramMap);
	}
	
	@Override
	public List LiZhiStatistic(HttpServletRequest request) {
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

		return empInfoDao.LiZhiStatistic(paramMap);
	}
	
	@Override	
	public List getLaborDispatch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getLaborDispatch(paramMap);
	}
	
	@Override
	public List getResident(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getResident(paramMap);
	}
	
	@Override
	public List getWorkArea(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getWorkArea(paramMap);
	}
	
	@Override
	public List getCaiWu(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getCaiWu(paramMap);
	}
	
	@Override
	public List getWorkPerson(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getWorkPerson(paramMap);
	}
	
	@Override
	public List getShangYe(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getShangYe(paramMap);
	}
	
	@Override
	public List getCheJian(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getCheJian(paramMap);
	}
	
	@Override
	public List getIndividualIncomeTax(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getIndividualIncomeTax(paramMap);
	}
	
	@Override
	public List getCount(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String year = request.getParameter("YEAR");
		String January = year + "01";
		String February = year + "02";
		String March = year + "03";
		String April = year + "04";
		String May = year + "05";
		String June = year + "06";
		String July = year + "07";
		String August = year + "08";
		String September = year + "09";
		String October = year + "10";
		String November = year + "11";
		String December = year + "12";
		paramMap.put("YEAR", request.getParameter("YEAR"));
		paramMap.put("JANUARY", January);
		paramMap.put("FEBRUARY", February);
		paramMap.put("MARCH", March);
		paramMap.put("APRIL", April);
		paramMap.put("MAY", May);
		paramMap.put("JUNE", June);
		paramMap.put("JULY", July);
		paramMap.put("AUGUST", August);
		paramMap.put("SEPTEMBER", September);
		paramMap.put("OCTOBER", October);
		paramMap.put("NOVEMBER", November);
		paramMap.put("DECEMBER", December);
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getCount(paramMap);
	}
	
	@Override
	public List getCountWages(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String year = request.getParameter("YEAR");
		String January = year + "01";
		String February = year + "02";
		String March = year + "03";
		String April = year + "04";
		String May = year + "05";
		String June = year + "06";
		String July = year + "07";
		String August = year + "08";
		String September = year + "09";
		String October = year + "10";
		String November = year + "11";
		String December = year + "12";
		paramMap.put("YEAR", request.getParameter("YEAR"));
		paramMap.put("JANUARY", January);
		paramMap.put("FEBRUARY", February);
		paramMap.put("MARCH", March);
		paramMap.put("APRIL", April);
		paramMap.put("MAY", May);
		paramMap.put("JUNE", June);
		paramMap.put("JULY", July);
		paramMap.put("AUGUST", August);
		paramMap.put("SEPTEMBER", September);
		paramMap.put("OCTOBER", October);
		paramMap.put("NOVEMBER", November);
		paramMap.put("DECEMBER", December);
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getCountWages(paramMap);
	}
	
	@Override
	public List getCOntractDaoqi(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getCOntractDaoqi(paramMap);
	}
	
	@Override
	public List getPersonalInfo1(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getPersonalInfo1(paramMap);
	}
	
	@Override
	public List getBanGongShi(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getBanGongShi(paramMap);
	}
	
	@Override
	public List getJishiDingban(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getJishiDingban(paramMap);
	}
	
	@Override
	public List getCZJishiDingban(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getCZJishiDingban(paramMap);
	}
	
	@Override
	public List getTC(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getTC(paramMap);
	}
	
	@Override
	public List getDongyuanDian(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getDongyuanDian(paramMap);
	}
	
	@Override
	public List getNinghaiDian(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getNinghaiDian(paramMap);
	}
	
	@Override
	public List getShiguDian(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getShiguDian(paramMap);
	}
	
	@Override
	public List getHongYueCheng(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getHongYueCheng(paramMap);
	}
	
	@Override
	public List getLongJiang(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getLongJiang(paramMap);
	}
	
	@Override
	public List getZhuJiang(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getZhuJiang(paramMap);
	}
	
	@Override
	public List getHuNan(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getHuNan(paramMap);
	}
	
	@Override
	public List getHuDengFang(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getHuDengFang(paramMap);
	}
	
	@Override
	public List getQingJiang(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getQingJiang(paramMap);
	}
	
	@Override
	public List getWanDa(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getWanDa(paramMap);
	}
	
	@Override
	public List getBingRunHui(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getBingRunHui(paramMap);
	}
	
	@Override
	public List getCenterShopping(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getCenterShopping(paramMap);
	}
	
	@Override
	public List getJingFeng(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getJingFeng(paramMap);
	}
	
	@Override
	public List getSunCity(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getSunCity(paramMap);
	}
	
	@Override
	public List getMaoYe(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getMaoYe(paramMap);
	}
	
	@Override
	public List getHuanQiuGang(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getHuanQiuGang(paramMap);
	}
	
	@Override
	public List getNewCentury(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getNewCentury(paramMap);
	}
	
	@Override
	public List getChangFa(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getChangFa(paramMap);
	}
	
	@Override
	public List getBaoLong(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getBaoLong(paramMap);
	}
	
	@Override
	public List getJiuZhou(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getJiuZhou(paramMap);
	}
	
	@Override
	public List getCStatistics(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getCStatistics(paramMap);
	}
	
	@Override
	public List getLastWeek(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getLastWeek(paramMap);
	}
	
	@Override
	public List XingbXuelMinz(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.XingbXuelMinz(paramMap);
	}
	
	@Override
	public List AgeStatus(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.AgeStatus(paramMap);
	}
	
	@Override
	public List WorkStatus(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.WorkStatus(paramMap);
	}
	
	@Override
	public List getHuKou(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getHuKou(paramMap);
	}

	/**
	 * 发令表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List getStartPointList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return empInfoDao.getStartPointList(paramMap);
	}

	@Override
	public List getStartPointList1(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		return empInfoDao.getStartPointList1(paramMap);
	}
	
	/**
	 * 工作经历表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List getExperiencePointList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
        /*
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}*/
		//2017-06-01,chenchao
		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getExperiencePointList(paramMap);
	}

	/**
	 * 学历表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewEducationMatter(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewEducationMatter(paramMap);
	}

	/**
	 * 资格表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewBidMatter(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewBidMatter(paramMap);
	}
	
	@Override
	public List getAccountInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getAccountInfo(paramMap);
	}
	/**
	 * 评价信息表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewEvaluateInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return empInfoDao.viewEvaluateInfo(paramMap);
	}

	/**
	 * 语言表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewForeignLanguage(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewForeignLanguage(paramMap);
	}
	
	
	/**
	 * 培训表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewTrainingBasic(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewTrainingBasic(paramMap);
	}

	/**
	 * 表彰表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewRecognition(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewRecognition(paramMap);
	}

	/**
	 * 惩戒表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewPunishment(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewPunishment(paramMap);
	}
	
	@Override
	public List viewSingleTrain(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleTrain(paramMap);
	}

	public List viewTrain(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewTrain(paramMap);
	}
	
	public List viewEvaInformation(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewEvaInformation(paramMap);
	}

	/**
	 * 特记事项表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewSpecialMatter(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		/*if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}*/
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSpecialMatter(paramMap);
	}

	/**
	 * 护照签证表(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewPassportPerson(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		/*if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}*/
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String flg = StringUtil.checkNull((String) request.getAttribute("flg"));
		/*if ("1".equals(flg) || "2".equals(flg)) {
			paramMap.put("FLAG", flg);
		} else {
			paramMap.put("FLAG", StringUtil.checkNull(request
					.getParameter("flag")));
		}*/
		return empInfoDao.viewPassportPerson(paramMap);
	}

	/**
	 * 照片员工查询
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewEmployeePhoto(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String empid_name = request.getParameter("empid_name");
		String empid_namevalue = StringUtil.checkNull(request
				.getParameter("empid_namevalue"));
		if (empid_name.equals("localname")) {
			paramMap.put("LOCAL_NAME_EMPID", "LOCAL_NAME like '%"
					+ empid_namevalue + "%'");
		} else {
			paramMap.put("LOCAL_NAME_EMPID", "EMPID='" + empid_namevalue + "'");
		}
		String deptName = StringUtil
				.checkNull(request.getParameter("deptName"));
		String QUERYDEPTNAME = StringUtil.checkNull(request
				.getParameter("QUERYDEPTNAME"));
		String twodeptname = StringUtil.checkNull(request
				.getParameter("twodeptname"));
		if ("1".equals(twodeptname)) {
			if (!"".equals(deptName)) {
				paramMap.put("ORG_NAME_LOCAL", deptName);
				String deptno = empInfoDao.querydeptno(paramMap);
				String isWith = request.getParameter("isWith");
				if (!"".equals(deptno)) {
					String str = "";
					if ("YES".equals(isWith)) {
						str = "and h.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
								+ deptno
								+ "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
					} else {
						str = "and h.DEPTNO= '" + deptno + "'";
					}
					paramMap.put("DEPTNO", str);
				}
			}
		} else {
			if (!"".equals(QUERYDEPTNAME)) {
				paramMap.put("ORG_NAME_LOCAL", QUERYDEPTNAME);
			}
			String deptno = empInfoDao.querydeptno(paramMap);
			String isWith = request.getParameter("isWith");
			if (!"".equals(deptno)) {
				String str = "";
				if ("YES".equals(isWith)) {
					str = "and h.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
							+ deptno
							+ "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				} else {
					str = "and h.DEPTNO= '" + deptno + "'";
				}
				paramMap.put("DEPTNOSECOND", str);
			}
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		return empInfoDao.viewEmployeePhoto(paramMap);
	}
	
	@Override
	public List viewEmployeePhotoInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		String empid_name = request.getParameter("empid_name");
		String empid_namevalue = StringUtil.checkNull(request
				.getParameter("empid_namevalue"));
		if (empid_name.equals("localname")) {
			paramMap.put("LOCAL_NAME_EMPID", "LOCAL_NAME like '%"
					+ empid_namevalue + "%'");
		} else {
			paramMap.put("LOCAL_NAME_EMPID", "EMPID='" + empid_namevalue + "'");
		}
		String deptName = StringUtil
				.checkNull(request.getParameter("deptName"));
		String QUERYDEPTNAME = StringUtil.checkNull(request
				.getParameter("QUERYDEPTNAME"));
		String twodeptname = StringUtil.checkNull(request
				.getParameter("twodeptname"));
		if ("1".equals(twodeptname)) {
			if (!"".equals(deptName)) {
				paramMap.put("ORG_NAME_LOCAL", deptName);
				String deptno = empInfoDao.querydeptno(paramMap);
				String isWith = request.getParameter("isWith");
				if (!"".equals(deptno)) {
					String str = "";
					if ("YES".equals(isWith)) {
						str = "and d.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
								+ deptno
								+ "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
					} else {
						str = "and d.DEPTNO= '" + deptno + "'";
					}
					paramMap.put("DEPTNO", str);
				}
			}
		} else {
			if (!"".equals(QUERYDEPTNAME)) {
				paramMap.put("ORG_NAME_LOCAL", QUERYDEPTNAME);
			}
			String deptno = empInfoDao.querydeptno(paramMap);
			String isWith = request.getParameter("isWith");
			if (!"".equals(deptno)) {
				String str = "";
				if ("YES".equals(isWith)) {
					str = "and d.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
							+ deptno
							+ "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				} else {
					str = "and d.DEPTNO= '" + deptno + "'";
				}
				paramMap.put("DEPTNOSECOND", str);
			}
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		return empInfoDao.viewEmployeePhotoInfo(paramMap);
	}
	
	public int deleteEmployeePhotoInfo(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.empInfoDao.deleteEmployeePhotoInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	/**
	 * 紧急联系人搜索页面(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List emergencyAddress(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		String deptno = StringUtil
				.checkNull(request.getParameter("ISDEPTNO10"));
		String lowerDepart = StringUtil.checkNull(request
				.getParameter("lowerDepart"));
		String str = "";
		if (!"".equals(deptno)) {
			if (lowerDepart.equals("Y")) {
				str = " and h.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
						+ deptno + "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				paramMap.put("DEPTNO", str);
			} else {
				str = " and h.DEPTNO= '" + deptno + "'";
			}
			paramMap.put("deptContent", str);
		}
		String sql = " select h.local_name EMPLOYEE_NAME,h.empid EMPID,get_dept_name(h.deptno,'vi') DEPARTMENT,get_global_name(h.POST_GRADE_NO, 'vi') RANK,get_global_name(a.EMER_TYPE_CODE, 'vi') RELATIONS,a.emer_name EMERGENCY_NAME,a.EMER_PHONE CONTACT_NUMBER,a.emer_email EMail,a.EMER_ADDRESS ADDRESS  from hr_emergency_address a, hr_employee h where h.person_id=a.person_id and h.empid not like '111111%' and h.cpny_id ='"
				+ admin.getCpnyId()
				+ "' and a.activity='1' ";
		String personid = "";
		if (request.getParameter("PERSON_ID") != null
				&& !"".equals(request.getParameter("PERSON_ID"))) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			personid = " and a.person_id='" + request.getParameter("PERSON_ID")
					+ "'";
		}
		String post_family = "";
		if (request.getParameter("POST_FAMILY") != null
				&& !"".equals(request.getParameter("POST_FAMILY"))) {
			post_family = " and h.POST_FAMILY in ("
					+ request.getParameter("POST_FAMILY") + ")";
		}
		String grade_no = "";
		if (request.getParameter("GRADE_NO") != null
				&& !"".equals(request.getParameter("GRADE_NO"))) {
			grade_no = " and h.POST_GRADE_NO in ("
					+ request.getParameter("GRADE_NO") + ")";
		}
		String emp_type_code = "";
		if (request.getParameter("EMP_TYPE_CODE") != null
				&& !"".equals(request.getParameter("EMP_TYPE_CODE"))) {
			emp_type_code = " and h.EMP_TYPE_CODE in ("
					+ request.getParameter("EMP_TYPE_CODE") + ")";
		}
		String date_started = "";
		if (request.getParameter("START_DATE") != null
				&& !"".equals(request.getParameter("START_DATE"))) {
			date_started = " and h.DATE_STARTED>=to_date('"
					+ request.getParameter("START_DATE") + "','dd/MM/yyyy')";
		}
		String end_started = "";
		if (request.getParameter("END_DATE") != null
				&& !"".equals(request.getParameter("END_DATE"))) {
			end_started = " and h.DATE_STARTED<=to_date('"
					+ request.getParameter("END_DATE") + "','dd/MM/yyyy')";
		}
		String emer_type_code = "";
		if (request.getParameter("EMER_TYPE_CODE") != null
				&& !"".equals(request.getParameter("EMER_TYPE_CODE"))) {
			emer_type_code = " and a.EMER_TYPE_CODE in ("
					+ request.getParameter("EMER_TYPE_CODE") + ")";
		}
		String main_business = "";
		if (request.getParameter("MAIN_BUSINESS") != null
				&& !"".equals(request.getParameter("MAIN_BUSINESS"))) {
			main_business = " and h.MAIN_BUSINESS in ("
					+ request.getParameter("MAIN_BUSINESS") + ")";
		}
		String emp_office = "";
		if (request.getParameter("EMP_OFFICE") != null
				&& !"".equals(request.getParameter("EMP_OFFICE"))) {
			emp_office = " and h.EMP_OFFICE in ("
					+ request.getParameter("EMP_OFFICE") + ")";
		}
		String emer_type_office = "";
		if (request.getParameter("MAIN_LIAISON_OFFICE") != null
				&& !"".equals(request.getParameter("MAIN_LIAISON_OFFICE"))) {
			emer_type_office = " and a.MAIN_LIAISON_OFFICE='"
					+ request.getParameter("MAIN_LIAISON_OFFICE") + "'";
		}
		sql = sql + personid + str + post_family + grade_no + emp_type_code + date_started 
			+ end_started + emer_type_code + main_business + emp_office + emer_type_office;
		paramMap.put("SQL_STMT", sql);
		paramMap.put("sql_seq", "46");
		try {
			this.empInfoDao.tiquziliao_new(paramMap);
		} catch (Exception e) {

			e.printStackTrace();
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return empInfoDao.emergencyAddress(paramMap);
	}

	/**
	 * 家庭搜索(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List familySearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		//if (request.getParameter("PERSON_ID") != null) {
			//paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		//}
		String deptno = StringUtil
				.checkNull(request.getParameter("ISDEPTNO11"));
		String lowerDepart = StringUtil.checkNull(request
				.getParameter("lowerDepart"));
		String str = "";
		if (!"".equals(deptno)) {
			if (lowerDepart.equals("Y")) {
				str = " and h.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
						+ deptno + "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				paramMap.put("DEPTNO", str);
			} else {
				str = " and h.DEPTNO= '" + deptno + "'";
			}
			paramMap.put("deptContent", str);
		}
		String sql = "select h.local_name NAME,h.empid EMPID,GET_DEPT_NAME(h.deptno,'vi') DEPARTMENT,GET_GLOBAL_NAME(h.post_grade_no,'vi') RANK,GET_GLOBAL_NAME(h.emp_type_code,'vi') EMPLOYEE_TYPE,to_char(h.date_started,'DD/MM/YYYY') ENTRY_DATE,get_global_name(f.FAM_TYPE_CODE,'vi') RELATIONS,f.FAM_NAME FAMILY_NAME,get_global_name(f.NATIONALITY,'vi') COUNTRY,f.AGE AGE,to_char(f.FAM_BORNDATE,'DD/MM/YYYY') BIRTHDAY,f.FAM_FAMILY_PHONE HOME_PHONE,get_global_name(f.FAM_EDUCATION,'vi') EDUCATION,f.FAM_COMPANY_NAME WORK_UNIT from hr_employee h,hr_personal_info p,hr_family f where h.person_id=f.person_id and h.person_id=p.person_id and f.activity='1' and h.empid not like '111111%' and h.cpny_id='"
				+ admin.getCpnyId() + "'  ";
		String personid = "";
		if (request.getParameter("PERSON_ID") != null
				&& !"".equals(request.getParameter("PERSON_ID"))) {
			//paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			personid = " and h.person_id='" + request.getParameter("PERSON_ID")
					+ "'";
		}
		String post_family = "";
		if (request.getParameter("POST_FAMILY") != null
				&& !"".equals(request.getParameter("POST_FAMILY"))) {
			post_family = " and h.POST_FAMILY in ("
					+ request.getParameter("POST_FAMILY") + ")";
		}
		String grade_no = "";
		if (request.getParameter("GRADE_NO") != null
				&& !"".equals(request.getParameter("GRADE_NO"))) {
			grade_no = " and h.POST_GRADE_NO in ("
					+ request.getParameter("GRADE_NO") + ")";
		}
		String main_business = "";
		if (request.getParameter("MAIN_BUSINESS") != null
				&& !"".equals(request.getParameter("MAIN_BUSINESS"))) {
			main_business = " and h.MAIN_BUSINESS in ("
					+ request.getParameter("MAIN_BUSINESS") + ")";
		}
		String emp_type_code = "";
		if (request.getParameter("EMP_TYPE_CODE") != null
				&& !"".equals(request.getParameter("EMP_TYPE_CODE"))) {
			emp_type_code = " and h.EMP_TYPE_CODE in ("
					+ request.getParameter("EMP_TYPE_CODE") + ")";
		}
		String emp_office = "";
		if (request.getParameter("EMP_OFFICE") != null
				&& !"".equals(request.getParameter("EMP_OFFICE"))) {
			emp_office = " and h.EMP_OFFICE in ("
					+ request.getParameter("EMP_OFFICE") + ")";
		}
		String date_started = "";
		if (request.getParameter("START_DATE") != null
				&& !"".equals(request.getParameter("START_DATE"))) {
			date_started = " and h.DATE_STARTED>=to_date('"
					+ request.getParameter("START_DATE") + "','dd/MM/yyyy')";
		}
		String end_started = "";
		if (request.getParameter("END_DATE") != null
				&& !"".equals(request.getParameter("END_DATE"))) {
			end_started = " and h.DATE_STARTED<=to_date('"
					+ request.getParameter("END_DATE") + "','dd/MM/yyyy')";
		}
		String FAM_TYPE_CODE = "";
		if (request.getParameter("FAM_TYPE_CODE") != null
				&& !"".equals(request.getParameter("FAM_TYPE_CODE"))) {
			FAM_TYPE_CODE = " and f.FAM_TYPE_CODE in ("
					+ request.getParameter("FAM_TYPE_CODE") + ")";
		}
		String FAM_NAME = "";
		if (request.getParameter("FAM_NAME") != null
				&& !"".equals(request.getParameter("FAM_NAME"))) {
			FAM_NAME = " and f.FAM_NAME like '%"
					+ request.getParameter("FAM_NAME") + "%'";
		}
		sql = sql + personid + str + post_family + grade_no + main_business
				+ emp_type_code + emp_office + date_started + end_started
				+ FAM_TYPE_CODE + FAM_NAME;
		paramMap.put("SQL_STMT", sql);
		paramMap.put("sql_seq", "47");
		try {
			this.empInfoDao.tiquziliao_new(paramMap);
		} catch (Exception e) {

			e.printStackTrace();
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return empInfoDao.familySearch(paramMap);
	}

	/**
	 * 经历搜索(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List experienceSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		String deptno = StringUtil
				.checkNull(request.getParameter("ISDEPTNO12"));
		String lowerDepart = StringUtil.checkNull(request
				.getParameter("lowerDepart"));
		String str = "";
		if (!"".equals(deptno)) {
			if (lowerDepart.equals("Y")) {
				str = " and h.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
						+ deptno + "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				paramMap.put("DEPTNO", str);
			} else {
				str = " and h.DEPTNO= '" + deptno + "'";
			}
			paramMap.put("deptContent", str);
		}
		String sql = "select h.local_name NAME,h.empid EMPID,get_dept_name(h.deptno,'vi') DEPARTMENT,get_global_name(h.POST_GRADE_NO,'vi') RANK,to_char(w.START_DATE,'dd/MM/yyyy') ENTRY_DATE,to_char(w.END_DATE,'dd/MM/yyyy') LEAVE_DATE,w.CPNY_NAME CORPORATE_NAME,w.POSITION POST,w.PAY_YEAR SALARY_MONTHLY  from hr_employee h,hr_work_experience w where h.person_id=w.person_id and w.activity='1' and h.empid not like '111111%' and h.cpny_id='"
				+ admin.getCpnyId() + "'  ";
		String personid = "";
		if (request.getParameter("PERSON_ID") != null
				&& !"".equals(request.getParameter("PERSON_ID"))) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			personid = " and h.person_id='" + request.getParameter("PERSON_ID")
					+ "'";
		}
		String post_family = "";
		if (request.getParameter("POST_FAMILY") != null
				&& !"".equals(request.getParameter("POST_FAMILY"))) {
			post_family = " and h.POST_FAMILY in ("
					+ request.getParameter("POST_FAMILY") + ")";
		}
		String grade_no = "";
		if (request.getParameter("GRADE_NO") != null
				&& !"".equals(request.getParameter("GRADE_NO"))) {
			grade_no = " and h.POST_GRADE_NO in ("
					+ request.getParameter("GRADE_NO") + ")";
		}
		String main_business = "";
		if (request.getParameter("MAIN_BUSINESS") != null
				&& !"".equals(request.getParameter("MAIN_BUSINESS"))) {
			main_business = " and h.MAIN_BUSINESS in ("
					+ request.getParameter("MAIN_BUSINESS") + ")";
		}
		String emp_type_code = "";
		if (request.getParameter("EMP_TYPE_CODE") != null
				&& !"".equals(request.getParameter("EMP_TYPE_CODE"))) {
			emp_type_code = " and h.EMP_TYPE_CODE in ("
					+ request.getParameter("EMP_TYPE_CODE") + ")";
		}
		String emp_office = "";
		if (request.getParameter("EMP_OFFICE") != null
				&& !"".equals(request.getParameter("EMP_OFFICE"))) {
			emp_office = " and h.EMP_OFFICE in ("
					+ request.getParameter("EMP_OFFICE") + ")";
		}
		String date_started = "";
		if (request.getParameter("START_DATE") != null
				&& !"".equals(request.getParameter("START_DATE"))) {
			date_started = " and h.DATE_STARTED>=to_date('"
					+ request.getParameter("START_DATE") + "','dd/MM/yyyy')";
		}
		String end_started = "";
		if (request.getParameter("END_DATE") != null
				&& !"".equals(request.getParameter("END_DATE"))) {
			end_started = " and h.DATE_STARTED<=to_date('"
					+ request.getParameter("END_DATE") + "','dd/MM/yyyy')";
		}
		String START_DATE_EXP = "";
		if (request.getParameter("START_DATE_EXP") != null
				&& !"".equals(request.getParameter("START_DATE_EXP"))) {
			START_DATE_EXP = " and w.START_DATE>=to_date('"
					+ request.getParameter("START_DATE_EXP")
					+ "','dd/MM/yyyy')";
		}
		String END_DATE_EXP = "";
		if (request.getParameter("END_DATE_EXP") != null
				&& !"".equals(request.getParameter("END_DATE_EXP"))) {
			END_DATE_EXP = " and w.END_DATE<=to_date('"
					+ request.getParameter("END_DATE_EXP") + "','dd/MM/yyyy')";
		}
		String CPNY_NAME = "";
		if (request.getParameter("CPNY_NAME") != null
				&& !"".equals(request.getParameter("CPNY_NAME"))) {
			CPNY_NAME = " and w.CPNY_NAME like '%"
					+ request.getParameter("CPNY_NAME") + "%'";
		}
		String position = "";
		if (request.getParameter("POSITION") != null
				&& !"".equals(request.getParameter("POSITION"))) {
			position = " and w.POSITION like '%"
					+ request.getParameter("POSITION") + "%'";
		}
		sql = sql + personid + str + grade_no + main_business + emp_type_code
				+ emp_office + date_started + end_started + START_DATE_EXP
				+ END_DATE_EXP + CPNY_NAME + position;
		paramMap.put("SQL_STMT", sql);
		paramMap.put("sql_seq", "48");
		try {
			this.empInfoDao.tiquziliao_new(paramMap);
		} catch (Exception e) {

			e.printStackTrace();
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return empInfoDao.experienceSearch(paramMap);
	}

	/**
	 * 学历搜索(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List educationSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		String deptno = StringUtil
		.checkNull(request.getParameter("ISDEPTNO13"));
		String lowerDepart = StringUtil.checkNull(request
				.getParameter("lowerDepart"));
	
		String str = "";
		if (!"".equals(deptno)) {
			if (lowerDepart.equals("Y")) {
				str = " and h.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
						+ deptno + "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				paramMap.put("DEPTNO", str);
			} else {
				str = " and h.DEPTNO= '" + deptno + "'";
			}
			paramMap.put("deptContent", str);
		}
		String sql = "select rownum NO, h.local_name NAME,h.empid EMPID,get_dept_name(h.deptno,'vi') DEPARTMENT,get_global_name(h.POST_GRADE_NO,'vi') RANK,get_global_name(e.DEGREE_CODE,'vi') EDUCATION,e.START_DATE ADMISSION_DATE,e.END_DATE GRADUATION_DATE,e.INSTITUTION_NAME GRADUATE_SCHOOL,e.SUBJECT MAJOR from hr_employee h,hr_education e where h.person_id=e.person_id and e.activity='1' and h.empid not like '111111%' and h.cpny_id='"
				+ admin.getCpnyId() + "'  ";
		String personid = "";
		if (request.getParameter("PERSON_ID") != null
				&& !"".equals(request.getParameter("PERSON_ID"))) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			personid = " and h.person_id='" + request.getParameter("PERSON_ID")
					+ "'";
		}
		String post_family = "";
		if (request.getParameter("POST_FAMILY") != null
				&& !"".equals(request.getParameter("POST_FAMILY"))) {
			post_family = " and h.POST_FAMILY in ("
					+ request.getParameter("POST_FAMILY") + ")";
		}
		String grade_no = "";
		if (request.getParameter("GRADE_NO") != null
				&& !"".equals(request.getParameter("GRADE_NO"))) {
			grade_no = " and h.POST_GRADE_NO in ("
					+ request.getParameter("GRADE_NO") + ")";
		}
		String main_business = "";
		if (request.getParameter("MAIN_BUSINESS") != null
				&& !"".equals(request.getParameter("MAIN_BUSINESS"))) {
			main_business = " and h.MAIN_BUSINESS in ("
					+ request.getParameter("MAIN_BUSINESS") + ")";
		}
		String emp_type_code = "";
		if (request.getParameter("EMP_TYPE_CODE") != null
				&& !"".equals(request.getParameter("EMP_TYPE_CODE"))) {
			emp_type_code = " and h.EMP_TYPE_CODE in ("
					+ request.getParameter("EMP_TYPE_CODE") + ")";
		}
		String emp_office = "";
		if (request.getParameter("EMP_OFFICE") != null
				&& !"".equals(request.getParameter("EMP_OFFICE"))) {
			emp_office = " and h.EMP_OFFICE in ("
					+ request.getParameter("EMP_OFFICE") + ")";
		}
		String date_started = "";
		if (request.getParameter("START_DATE") != null
				&& !"".equals(request.getParameter("START_DATE"))) {
			date_started = " and h.DATE_STARTED>=to_date('"
					+ request.getParameter("START_DATE") + "','dd/MM/yyyy')";
		}
		String end_started = "";
		if (request.getParameter("END_DATE") != null
				&& !"".equals(request.getParameter("END_DATE"))) {
			end_started = " and h.DATE_STARTED<=to_date('"
					+ request.getParameter("END_DATE") + "','dd/MM/yyyy')";
		}
		String START_DATE_EDU = "";
		if (request.getParameter("START_DATE_EDU") != null
				&& !"".equals(request.getParameter("START_DATE_EDU"))) {
			START_DATE_EDU = " and TO_DATE(e.START_DATE,'MM/YYYY') >= TO_DATE('"
					+ request.getParameter("START_DATE_EDU") + "','MM/YYYY')";
		}
		String END_DATE_EDU = "";
		if (request.getParameter("END_DATE_EDU") != null
				&& !"".equals(request.getParameter("END_DATE_EDU"))) {
			END_DATE_EDU = " and TO_DATE(e.END_DATE,'MM/YYYY') <= TO_DATE('"
					+ request.getParameter("END_DATE_EDU") + "','MM/YYYY')";
		}
		String DEGREE_CODE = "";
		if (request.getParameter("DEGREE_CODE") != null
				&& !"".equals(request.getParameter("DEGREE_CODE"))) {
			DEGREE_CODE = " and e.DEGREE_CODE in ("
					+ request.getParameter("DEGREE_CODE") + ")";
		}
		String FINAL_DEGREE_WHETHER = "";
		if (request.getParameter("FINAL_DEGREE_WHETHER") != null
				&& !"".equals(request.getParameter("FINAL_DEGREE_WHETHER"))) {
			FINAL_DEGREE_WHETHER = " and e.FINAL_DEGREE_WHETHER='Y'";
		}
		String INSTITUTION_NAME = "";
		if (request.getParameter("INSTITUTION_NAME") != null
				&& !"".equals(request.getParameter("INSTITUTION_NAME"))) {
			INSTITUTION_NAME = " and e.INSTITUTION_NAME like '%"
					+ request.getParameter("INSTITUTION_NAME") + "%'";
		}
		String SUBJECT = "";
		if (request.getParameter("SUBJECT") != null
				&& !"".equals(request.getParameter("SUBJECT"))) {
			SUBJECT = " and e.SUBJECT like '%"
					+ request.getParameter("SUBJECT") + "%'";
		}
		sql = sql + personid + str + post_family + grade_no + main_business
				+ emp_type_code + emp_office + date_started + end_started
				+ START_DATE_EDU + END_DATE_EDU + DEGREE_CODE
				+ FINAL_DEGREE_WHETHER + INSTITUTION_NAME + SUBJECT;
		paramMap.put("SQL_STMT", sql);
		paramMap.put("sql_seq", "49");
		try {
			this.empInfoDao.tiquziliao_new(paramMap);
		} catch (Exception e) {

			e.printStackTrace();
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return empInfoDao.educationSearch(paramMap);
	}

	/**
	 * 资格搜索(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List bidSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		String deptno = StringUtil
				.checkNull(request.getParameter("ISDEPTNO14"));
		String lowerDepart = StringUtil.checkNull(request
				.getParameter("lowerDepart"));
		String str = "";
		if (!"".equals(deptno)) {
			if (lowerDepart.equals("Y")) {
				str = " and h.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
						+ deptno + "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				paramMap.put("DEPTNO", str);
			} else {
				str = " and h.DEPTNO= '" + deptno + "'";
			}
			paramMap.put("deptContent", str);
		}
		String sql = "select rownum NO,h.local_name NAME,h.empid EMPID,get_dept_name(h.deptno,'vi') DEPARTMENT,get_global_name(h.POST_GRADE_NO,'vi') RANK,q.QUAL_NAME QUALIFICATION,to_char(q.DATE_OBTAINED,'DD/MM/YYYY') DATE_EVIDENCE,to_char(q.VALIDITY_DATE,'DD/MM/YYYY') EFFECTIVE_DATE,q.QUAL_LEVEL QUALIFICATION_GRADE,q.QUAL_INSTITUTE ISSUING_ORGAN from hr_employee h,hr_qualification q where h.person_id=q.person_id and q.activity='1' and h.empid not like '111111%' and h.cpny_id='"
				+ admin.getCpnyId() + "'  ";
		String personid = "";
		if (request.getParameter("PERSON_ID") != null
				&& !"".equals(request.getParameter("PERSON_ID"))) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			personid = " and h.person_id='" + request.getParameter("PERSON_ID")
					+ "'";
		}
		String post_family = "";
		if (request.getParameter("POST_FAMILY") != null
				&& !"".equals(request.getParameter("POST_FAMILY"))) {
			post_family = " and h.POST_FAMILY in ("
					+ request.getParameter("POST_FAMILY") + ")";
		}
		String grade_no = "";
		if (request.getParameter("GRADE_NO") != null
				&& !"".equals(request.getParameter("GRADE_NO"))) {
			grade_no = " and h.POST_GRADE_NO in ("
					+ request.getParameter("GRADE_NO") + ")";
		}
		String main_business = "";
		if (request.getParameter("MAIN_BUSINESS") != null
				&& !"".equals(request.getParameter("MAIN_BUSINESS"))) {
			main_business = " and h.MAIN_BUSINESS in ("
					+ request.getParameter("MAIN_BUSINESS") + ")";
		}
		String emp_type_code = "";
		if (request.getParameter("EMP_TYPE_CODE") != null
				&& !"".equals(request.getParameter("EMP_TYPE_CODE"))) {
			emp_type_code = " and h.EMP_TYPE_CODE in ("
					+ request.getParameter("EMP_TYPE_CODE") + ")";
		}
		String emp_office = "";
		if (request.getParameter("EMP_OFFICE") != null
				&& !"".equals(request.getParameter("EMP_OFFICE"))) {
			emp_office = " and h.EMP_OFFICE in ("
					+ request.getParameter("EMP_OFFICE") + ")";
		}
		String date_started = "";
		if (request.getParameter("START_DATE") != null
				&& !"".equals(request.getParameter("START_DATE"))) {
			date_started = " and h.DATE_STARTED>=to_date('"
					+ request.getParameter("START_DATE") + "','dd/MM/yyyy')";
		}
		String end_started = "";
		if (request.getParameter("END_DATE") != null
				&& !"".equals(request.getParameter("END_DATE"))) {
			end_started = " and h.DATE_STARTED<=to_date('"
					+ request.getParameter("END_DATE") + "','dd/MM/yyyy')";
		}
		String START_DATE_BID = "";
		if (request.getParameter("START_DATE_BID") != null
				&& !"".equals(request.getParameter("START_DATE_BID"))) {
			date_started = " and q.DATE_OBTAINED>=to_date('"
					+ request.getParameter("START_DATE_BID")
					+ "','dd/MM/yyyy')";
		}
		String END_DATE_BID = "";
		if (request.getParameter("END_DATE_BID") != null
				&& !"".equals(request.getParameter("END_DATE_BID"))) {
			end_started = " and q.DATE_OBTAINED<=to_date('"
					+ request.getParameter("END_DATE_BID") + "','dd/MM/yyyy')";
		}
		String QUAL_NAME = "";
		if (request.getParameter("QUAL_NAME") != null
				&& !"".equals(request.getParameter("QUAL_NAME"))) {
			QUAL_NAME = " and q.QUAL_NAME like '%"
					+ request.getParameter("QUAL_NAME") + "%'";
		}
		String QUAL_LEVEL = "";
		if (request.getParameter("QUAL_LEVEL") != null
				&& !"".equals(request.getParameter("QUAL_LEVEL"))) {
			QUAL_LEVEL = " and q.QUAL_LEVEL like '%"
					+ request.getParameter("QUAL_LEVEL") + "%'";
		}
		sql = sql + personid + str + grade_no + main_business + emp_type_code + post_family 
				+ emp_office + date_started + end_started + START_DATE_BID
				+ END_DATE_BID + QUAL_NAME + QUAL_LEVEL;
		paramMap.put("SQL_STMT", sql);
		paramMap.put("sql_seq", "50");
		try {
			this.empInfoDao.tiquziliao_new(paramMap);
		} catch (Exception e) {

			e.printStackTrace();
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return empInfoDao.bidSearch(paramMap);
	}

	/**
	 * 职级搜索(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List gradeSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		String deptno = StringUtil
				.checkNull(request.getParameter("ISDEPTNO15"));
		String lowerDepart = StringUtil.checkNull(request
				.getParameter("lowerDepart"));
		String str = "";
		if (!"".equals(deptno)) {
			if (lowerDepart.equals("Y")) {
				str = " and h.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
						+ deptno + "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				paramMap.put("DEPTNO", str);
			} else {
				str = " and h.DEPTNO= '" + deptno + "'";
			}
			paramMap.put("deptContent", str);
		}
		String sql = "select rownum NO,h.local_name NAME,h.empid EMPID,get_dept_name(h.deptno,'vi') DEPARTMENT,get_global_name(h.post_grade_no,'vi') RANK,get_global_name(h.POSITION_NO,'vi') DUTY,to_char(h.DATE_STARTED,'dd/MM/yyyy') ENTRY_DATE,h.PROMOTION_DAY PROMOTION_DATE from hr_employee h where h.empid not like '111111%' and h.cpny_id='"
				+ admin.getCpnyId() + "'  ";
		String personid = "";
		if (request.getParameter("PERSON_ID") != null
				&& !"".equals(request.getParameter("PERSON_ID"))) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			personid = " and h.person_id='" + request.getParameter("PERSON_ID")
					+ "'";
		}
		String post_family = "";
		if (request.getParameter("POST_FAMILY") != null
				&& !"".equals(request.getParameter("POST_FAMILY"))) {
			post_family = " and h.POST_FAMILY in ("
					+ request.getParameter("POST_FAMILY") + ")";
		}
		String grade_no = "";
		if (request.getParameter("GRADE_NO") != null
				&& !"".equals(request.getParameter("GRADE_NO"))) {
			grade_no = " and h.POST_GRADE_NO in ("
					+ request.getParameter("GRADE_NO") + ")";
		}
		String emp_office = "";
		if (request.getParameter("EMP_OFFICE") != null
				&& !"".equals(request.getParameter("EMP_OFFICE"))) {
			emp_office = " and h.EMP_OFFICE in ("
					+ request.getParameter("EMP_OFFICE") + ")";
		}
		String date_started = "";
		if (request.getParameter("START_DATE") != null
				&& !"".equals(request.getParameter("START_DATE"))) {
			date_started = " and h.DATE_STARTED>=to_date('"
					+ request.getParameter("START_DATE") + "','dd/MM/yyyy')";
		}
		String end_started = "";
		if (request.getParameter("END_DATE") != null
				&& !"".equals(request.getParameter("END_DATE"))) {
			end_started = " and h.DATE_STARTED<=to_date('"
					+ request.getParameter("END_DATE") + "','dd/MM/yyyy')";
		}
		sql = sql + personid + str + post_family + grade_no + emp_office
				+ date_started + end_started;
		paramMap.put("SQL_STMT", sql);
		paramMap.put("sql_seq", "51");
		try {
			this.empInfoDao.tiquziliao_new(paramMap);
		} catch (Exception e) {

			e.printStackTrace();
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return empInfoDao.gradeSearch(paramMap);
	}

	/**
	 * 地址搜索(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List addressSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		String deptno = StringUtil
				.checkNull(request.getParameter("ISDEPTNO16"));
		String lowerDepart = StringUtil.checkNull(request
				.getParameter("lowerDepart"));
		String str = "";
		if (!"".equals(deptno)) {
			if (lowerDepart.equals("Y")) {
				str = " and h.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
						+ deptno + "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				paramMap.put("DEPTNO", str);
			} else {
				str = " and h.DEPTNO= '" + deptno + "'";
			}
			paramMap.put("deptContent", str);
		}
		String sql = "select rownum NO,h.local_name NAME,h.empid EMPID,get_dept_name(h.deptno,'vi') DEPARTMENT,get_global_name(h.post_grade_no,'vi') RANK,get_global_name(a.ADDRESS_TYPE,'vi') ADDRESS_TYPE,a.ADDRESS_CONTENT ADDRESS,TO_CHAR(a.EFFECTIVE_START_DATE,'MM/DD/YYYY') EFFECTIVE_START_DATE from hr_employee h,hr_address_matters a where h.person_id=a.person_id and h.empid not like '111111%' /*and a.activity='1'*/ and h.cpny_id='"
				+ admin.getCpnyId() + "'  ";
		String personid = "";
		if (request.getParameter("PERSON_ID") != null
				&& !"".equals(request.getParameter("PERSON_ID"))) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			personid = " and h.person_id='" + request.getParameter("PERSON_ID")
					+ "'";
		}
		String post_family = "";
		if (request.getParameter("POST_FAMILY") != null
				&& !"".equals(request.getParameter("POST_FAMILY"))) {
			post_family = " and h.POST_FAMILY in ("
					+ request.getParameter("POST_FAMILY") + ")";
		}
		String grade_no = "";
		if (request.getParameter("GRADE_NO") != null
				&& !"".equals(request.getParameter("GRADE_NO"))) {
			grade_no = " and h.POST_GRADE_NO in ("
					+ request.getParameter("GRADE_NO") + ")";
		}
		String emp_type_code = "";
		if (request.getParameter("EMP_TYPE_CODE") != null
				&& !"".equals(request.getParameter("EMP_TYPE_CODE"))) {
			emp_type_code = " and h.EMP_TYPE_CODE in ("
					+ request.getParameter("EMP_TYPE_CODE") + ")";
		}
		String main_business = "";
		if (request.getParameter("MAIN_BUSINESS") != null
				&& !"".equals(request.getParameter("MAIN_BUSINESS"))) {
			main_business = " and h.MAIN_BUSINESS in ("
					+ request.getParameter("MAIN_BUSINESS") + ")";
		}
		String emp_office = "";
		if (request.getParameter("EMP_OFFICE") != null
				&& !"".equals(request.getParameter("EMP_OFFICE"))) {
			emp_office = " and h.EMP_OFFICE in ("
					+ request.getParameter("EMP_OFFICE") + ")";
		}
		String date_started = "";
		if (request.getParameter("START_DATE") != null
				&& !"".equals(request.getParameter("START_DATE"))) {
			date_started = " and h.DATE_STARTED>=to_date('"
					+ request.getParameter("START_DATE") + "','dd/MM/yyyy')";
		}
		String end_started = "";
		if (request.getParameter("END_DATE") != null
				&& !"".equals(request.getParameter("END_DATE"))) {
			end_started = " and h.DATE_STARTED<=to_date('"
					+ request.getParameter("END_DATE") + "','dd/MM/yyyy')";
		}
		String address_content = "";
		if (request.getParameter("ADDRESS_CONTENT") != null
				&& !"".equals(request.getParameter("ADDRESS_CONTENT"))) {
			address_content = " and a.ADDRESS_CONTENT like '%" + request.getParameter("ADDRESS_CONTENT") + "%'";
		}
		sql = sql + personid + str + post_family + grade_no + main_business
				+ emp_type_code + emp_office + date_started + end_started + address_content;
		paramMap.put("SQL_STMT", sql);
		paramMap.put("sql_seq", "52");
		try {
			this.empInfoDao.tiquziliao_new(paramMap);
		} catch (Exception e) {

			e.printStackTrace();
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return empInfoDao.addressSearch(paramMap);
	}

	/**
	 * 表彰搜索(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List recognitionSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		String deptno = StringUtil
				.checkNull(request.getParameter("ISDEPTNO17"));
		String lowerDepart = StringUtil.checkNull(request
				.getParameter("lowerDepart"));
		String str = "";
		if (!"".equals(deptno)) {
			if (lowerDepart.equals("Y")) {
				str = " and h.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
						+ deptno + "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				paramMap.put("DEPTNO", str);
			} else {
				str = " and h.DEPTNO= '" + deptno + "'";
			}
			paramMap.put("deptContent", str);
		}
		String sql = "select rownum NO,h.local_name NAME,h.empid EMPID,get_dept_name(h.deptno,'vi') DEPARTMENT,get_global_name(h.emp_type_code, 'vi') EMPLOYEE_TYPE,get_global_name(h.post_grade_no, 'vi') RANK,to_char(h.DATE_STARTED,'dd/MM/yyyy') ENTRY_DATE,get_global_name(h.emp_office,'vi') STATE_SERVICE,get_global_name(r.REWARD_TYPE,'vi') AWARD,to_char(r.REWARD_DATE, 'dd/MM/yyyy') AWARD_DATE,r.REWARD_CNPY GRANTING_ORGAN,r.reward BONUS,get_global_name(r.REWARD_TYPE_CODE,'zh') BONUS_PAYMENT_CODE,r.REMARKS REMARKS,r.PERSONNEL_CARD_INQUIRY PERSONNEL_CARD_YN from hr_employee h, hr_reward r where h.person_id = r.person_id and r.activity = '1' and h.empid not like '111111%' and h.cpny_id='"
				+ admin.getCpnyId() + "'  ";
		String personid = "";
		if (request.getParameter("PERSON_ID") != null
				&& !"".equals(request.getParameter("PERSON_ID"))) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			personid = " and h.person_id='" + request.getParameter("PERSON_ID")
					+ "'";
		}
		String post_family = "";
		if (request.getParameter("POST_FAMILY") != null
				&& !"".equals(request.getParameter("POST_FAMILY"))) {
			post_family = " and h.POST_FAMILY in ("
					+ request.getParameter("POST_FAMILY") + ")";
		}
		String grade_no = "";
		if (request.getParameter("GRADE_NO") != null
				&& !"".equals(request.getParameter("GRADE_NO"))) {
			grade_no = " and h.POST_GRADE_NO in ("
					+ request.getParameter("GRADE_NO") + ")";
		}
		String main_business = "";
		if (request.getParameter("MAIN_BUSINESS") != null
				&& !"".equals(request.getParameter("MAIN_BUSINESS"))) {
			main_business = " and h.MAIN_BUSINESS='"
					+ request.getParameter("MAIN_BUSINESS") + "'";
		}
		String emp_type_code = "";
		if (request.getParameter("EMP_TYPE_CODE") != null
				&& !"".equals(request.getParameter("EMP_TYPE_CODE"))) {
			emp_type_code = " and h.EMP_TYPE_CODE in ("
					+ request.getParameter("EMP_TYPE_CODE") + ")";
		}
		String emp_office = "";
		if (request.getParameter("EMP_OFFICE") != null
				&& !"".equals(request.getParameter("EMP_OFFICE"))) {
			emp_office = " and h.EMP_OFFICE in ("
					+ request.getParameter("EMP_OFFICE") + ")";
		}
		String date_started = "";
		if (request.getParameter("START_DATE") != null
				&& !"".equals(request.getParameter("START_DATE"))) {
			date_started = " and h.DATE_STARTED>=to_date('"
					+ request.getParameter("START_DATE") + "','dd/MM/yyyy')";
		}
		String end_started = "";
		if (request.getParameter("END_DATE") != null
				&& !"".equals(request.getParameter("END_DATE"))) {
			end_started = " and h.DATE_STARTED<=to_date('"
					+ request.getParameter("END_DATE") + "','dd/MM/yyyy')";
		}
		String START_DATE_REC = "";
		if (request.getParameter("START_DATE_REC") != null
				&& !"".equals(request.getParameter("START_DATE_REC"))) {
			START_DATE_REC = " and r.REWARD_DATE>=to_date('"
					+ request.getParameter("START_DATE_REC")
					+ "','dd/MM/yyyy')";
		}
		String END_DATE_REC = "";
		if (request.getParameter("END_DATE_REC") != null
				&& !"".equals(request.getParameter("END_DATE_REC"))) {
			END_DATE_REC = " and r.REWARD_DATE<=to_date('"
					+ request.getParameter("END_DATE_REC") + "','dd/MM/yyyy')";
		}
		String REWARD_TYPE = "";
		if (request.getParameter("REWARD_TYPE") != null
				&& !"".equals(request.getParameter("REWARD_TYPE"))) {
			REWARD_TYPE = " and r.REWARD_TYPE='"
					+ request.getParameter("REWARD_TYPE") + "'";
		}
		sql = sql + personid + str + post_family + grade_no + main_business
				+ emp_type_code + emp_office + date_started + end_started
				+ START_DATE_REC + END_DATE_REC + REWARD_TYPE;
		paramMap.put("SQL_STMT", sql);
		paramMap.put("sql_seq", "53");
		try {
			this.empInfoDao.tiquziliao_new(paramMap);
		} catch (Exception e) {

			e.printStackTrace();
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return empInfoDao.recognitionSearch(paramMap);
	}

	/**
	 * 惩罚搜索(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List punishmentSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		String deptno = StringUtil
				.checkNull(request.getParameter("ISDEPTNO18"));
		String lowerDepart = StringUtil.checkNull(request
				.getParameter("lowerDepart"));
		String str = "";
		if (!"".equals(deptno)) {
			if (lowerDepart.equals("Y")) {
				str = " and h.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
						+ deptno + "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				paramMap.put("DEPTNO", str);
			} else {
				str = " and h.DEPTNO= '" + deptno + "'";
			}
			paramMap.put("deptContent", str);
		}
		String sql = "select rownum NO,h.local_name NAME,h.empid EMPID,get_dept_name(h.deptno,'vi') DEPARTMENT,get_global_name(h.emp_type_code,'vi') EMPLOYEE_TYPE,get_global_name(h.post_grade_no,'vi') RANK,to_char(h.DATE_STARTED,'dd/MM/yyyy') ENTRY_DATE,get_global_name(p.FAULT_TYPE_CODE,'vi') PENALTY_CODE,to_char(p.PUNISH_DATE,'dd/MM/yyyy') PUNISHMENT_DATE,p.PUNISH_DEPARTMENT PUNISH_AGENCY_NAME,to_char(p.PAYCUT_START_DATE,'dd/MM/yyyy') PAY_CUT_SDATE,to_char(p.PAYCUT_END_DATE,'dd/MM/yyyy') PAY_CUT_EDATE,to_char(p.RELEASE_DATE,'dd/MM/yyyy') DISSOLUTION_DATE,P.PUNISH_REASON PUNISHMENT_CAUSE  from hr_employee h,hr_punishment p where h.person_id=p.person_id and p.activity='1' and h.empid not like '111111%' and h.cpny_id='"
				+ admin.getCpnyId() + "'  ";
		String personid = "";
		if (request.getParameter("PERSON_ID") != null
				&& !"".equals(request.getParameter("PERSON_ID"))) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			personid = " and h.person_id='" + request.getParameter("PERSON_ID")
					+ "'";
		}
		String post_family = "";
		if (request.getParameter("POST_FAMILY") != null
				&& !"".equals(request.getParameter("POST_FAMILY"))) {
			post_family = " and h.POST_FAMILY in ("
					+ request.getParameter("POST_FAMILY") + ")";
		}
		String grade_no = "";
		if (request.getParameter("GRADE_NO") != null
				&& !"".equals(request.getParameter("GRADE_NO"))) {
			grade_no = " and h.POST_GRADE_NO in ("
					+ request.getParameter("GRADE_NO") + ")";
		}
		String main_business = "";
		if (request.getParameter("MAIN_BUSINESS") != null
				&& !"".equals(request.getParameter("MAIN_BUSINESS"))) {
			main_business = " and h.MAIN_BUSINESS in ("
					+ request.getParameter("MAIN_BUSINESS") + ")";
		}
		String emp_type_code = "";
		if (request.getParameter("EMP_TYPE_CODE") != null
				&& !"".equals(request.getParameter("EMP_TYPE_CODE"))) {
			emp_type_code = " and h.EMP_TYPE_CODE in ("
					+ request.getParameter("EMP_TYPE_CODE") + ")";
		}
		String emp_office = "";
		if (request.getParameter("EMP_OFFICE") != null
				&& !"".equals(request.getParameter("EMP_OFFICE"))) {
			emp_office = " and h.EMP_OFFICE in ("
					+ request.getParameter("EMP_OFFICE") + ")";
		}
		String date_started = "";
		if (request.getParameter("START_DATE") != null
				&& !"".equals(request.getParameter("START_DATE"))) {
			date_started = " and h.DATE_STARTED>=to_date('"
					+ request.getParameter("START_DATE") + "','dd/MM/yyyy')";
		}
		String end_started = "";
		if (request.getParameter("END_DATE") != null
				&& !"".equals(request.getParameter("END_DATE"))) {
			end_started = " and h.DATE_STARTED<=to_date('"
					+ request.getParameter("END_DATE") + "','dd/MM/yyyy')";
		}
		String START_DATE_PUN = "";
		if (request.getParameter("START_DATE_PUN") != null
				&& !"".equals(request.getParameter("START_DATE_PUN"))) {
			START_DATE_PUN = " and p.PUNISH_DATE>=to_date('"
					+ request.getParameter("START_DATE_PUN")
					+ "','dd/MM/yyyy')";
		}
		String END_DATE_PUN = "";
		if (request.getParameter("END_DATE_PUN") != null
				&& !"".equals(request.getParameter("END_DATE_PUN"))) {
			END_DATE_PUN = " and p.PUNISH_DATE<=to_date('"
					+ request.getParameter("END_DATE_PUN") + "','dd/MM/yyyy')";
		}
		String punish_code = "";
		if (request.getParameter("PUNISH_CODE") != null
				&& !"".equals(request.getParameter("PUNISH_CODE"))) {
			punish_code = " and p.PUNISH_CODE in ("
					+ request.getParameter("PUNISH_CODE") + ")";
		}
		sql = sql + personid + str + post_family + grade_no + main_business
				+ emp_type_code + emp_office + date_started + end_started
				+ START_DATE_PUN + END_DATE_PUN + punish_code;
		paramMap.put("SQL_STMT", sql);
		paramMap.put("sql_seq", "54");
		try {
			this.empInfoDao.tiquziliao_new(paramMap);
		} catch (Exception e) {

			e.printStackTrace();
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return empInfoDao.punishmentSearch(paramMap);
	}

	/**
	 * 退职搜索(Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List retireSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		String deptno = StringUtil
				.checkNull(request.getParameter("ISDEPTNO19"));
		String lowerDepart = StringUtil.checkNull(request
				.getParameter("lowerDepart"));
		String str = "";
		if (!"".equals(deptno)) {
			if (lowerDepart.equals("Y")) {
				str = " and h.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
						+ deptno + "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				paramMap.put("DEPTNO", str);
			} else {
				str = " and h.DEPTNO= '" + deptno + "'";
			}
			paramMap.put("deptContent", str);
		}
		String sql = "select rownum NO,h.local_name NAME,h.empid EMPID,get_dept_name(h.deptno,'vi') DEPARTMENT,get_global_name(h.post_grade_no,'vi') RANK,to_char(h.DATE_STARTED,'dd/MM/yyyy') ENTRY_DATE,to_char(h.DATE_LEFT,'dd/MM/yyyy') LEAVE_DATE,p.cellphone CONTACT_NUMBER,get_global_name(h.main_business,'vi') MAJOR_BUSINESS,get_global_name(h.LIZHIREASON,'vi') REMARKS from hr_employee h,hr_personal_info p where h.person_id=p.person_id(+) and h.emp_office = '15120' and h.empid not like '111111%' and h.cpny_id='"
				+ admin.getCpnyId() + "'  ";
		String personid = "";
		if (request.getParameter("PERSON_ID") != null
				&& !"".equals(request.getParameter("PERSON_ID"))) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			personid = " and h.person_id='" + request.getParameter("PERSON_ID")
					+ "'";
		}
		String post_family = "";
		if (request.getParameter("POST_FAMILY") != null
				&& !"".equals(request.getParameter("POST_FAMILY"))) {
			post_family = " and h.POST_FAMILY in ("
					+ request.getParameter("POST_FAMILY") + ")";
		}
		String grade_no = "";
		if (request.getParameter("GRADE_NO") != null
				&& !"".equals(request.getParameter("GRADE_NO"))) {
			grade_no = " and h.POST_GRADE_NO in ("
					+ request.getParameter("GRADE_NO") + ")";
		}
		String main_business = "";
		if (request.getParameter("MAIN_BUSINESS") != null
				&& !"".equals(request.getParameter("MAIN_BUSINESS"))) {
			main_business = " and h.MAIN_BUSINESS in("
					+ request.getParameter("MAIN_BUSINESS") + ")";
		}
		String emp_type_code = "";
		if (request.getParameter("EMP_TYPE_CODE") != null
				&& !"".equals(request.getParameter("EMP_TYPE_CODE"))) {
			emp_type_code = " and h.EMP_TYPE_CODE in ("
					+ request.getParameter("EMP_TYPE_CODE") + ")";
		}
		String emp_office = "";
		if (request.getParameter("EMP_OFFICE") != null
				&& !"".equals(request.getParameter("EMP_OFFICE"))) {
			emp_office = " and h.EMP_OFFICE in ("
					+ request.getParameter("EMP_OFFICE") + ")";
		}
		String date_started = "";
		if (request.getParameter("START_DATE") != null
				&& !"".equals(request.getParameter("START_DATE"))) {
			date_started = " and h.DATE_STARTED>=to_date('"
					+ request.getParameter("START_DATE") + "','dd/MM/yyyy')";
		}
		String end_started = "";
		if (request.getParameter("END_DATE") != null
				&& !"".equals(request.getParameter("END_DATE"))) {
			end_started = " and h.DATE_STARTED<=to_date('"
					+ request.getParameter("END_DATE") + "','dd/MM/yyyy')";
		}
		String START_DATE_RET = "";
		if (request.getParameter("START_DATE_RET") != null
				&& !"".equals(request.getParameter("START_DATE_RET"))) {
			START_DATE_RET = " and h.DATE_LEFT>=to_date('"
					+ request.getParameter("START_DATE_RET")
					+ "','dd/MM/yyyy')";
		}
		String END_DATE_RET = "";
		if (request.getParameter("END_DATE_RET") != null
				&& !"".equals(request.getParameter("END_DATE_RET"))) {
			END_DATE_RET = " and h.DATE_LEFT<=to_date('"
					+ request.getParameter("END_DATE_RET") + "','dd/MM/yyyy')";
		}
		
		sql = sql + personid + str + post_family + grade_no + main_business
				+ emp_type_code + emp_office + date_started + end_started
				+ START_DATE_RET + END_DATE_RET;
		paramMap.put("SQL_STMT", sql);
		paramMap.put("sql_seq", "55");
		try {
			this.empInfoDao.tiquziliao_new(paramMap);
		} catch (Exception e) {

			e.printStackTrace();
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return empInfoDao.retireSearch(paramMap);
	}

	/**
	 * 基本信息页面的hr_personal_info信息 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewHrPersonalInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewHrPersonalInfo(paramMap);
	}

	/**
	 * 基本信息页面的hr_personal_info信息 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewHrPersonalInfo2(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewHrPersonalInfo(paramMap);
	}

	/**
	 * 获取Supervisor履历信息
	 */
	public List viewSupervisorInfoList(HttpServletRequest request) {
		List list = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		list = empInfoDao.viewSupervisorInfoList(paramMap);
		return list;
	}

	/**
	 *紧急联系人单一的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSingleHrEmergencyAddress(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (StringUtil.checkNull(request.getParameter("EMERGENCY_NO")) != null) {
			paramMap.put("EMERGENCY_NO", request.getParameter("EMERGENCY_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleHrEmergencyAddress(paramMap);
	}

	/**
	 * 地址类型单一的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSingleAddressMatters(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (StringUtil.checkNull(request.getParameter("ADDRESS_NO")) != null) {
			paramMap.put("ADDRESS_NO", request.getParameter("ADDRESS_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleAddressMatters(paramMap);
	}

	/**
	 * 家庭关系单一的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSingleFamily(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (StringUtil.checkNull(request.getParameter("FAMILY_NO")) != null) {
			paramMap.put("FAMILY_NO", request.getParameter("FAMILY_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleFamily(paramMap);
	}

	/**
	 * 经历事项的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSingleExperiencePoint(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		if (StringUtil.checkNull(request.getParameter("WORK_EXPER_NO")) != null) {
			paramMap
					.put("WORK_EXPER_NO", request.getParameter("WORK_EXPER_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleExperiencePoint(paramMap);
	}

	/**
	 * 学历事项的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSingleEducationMatter(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (StringUtil.checkNull(request.getParameter("EDUC_NO")) != null) {
			paramMap.put("EDUC_NO", request.getParameter("EDUC_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleEducationMatter(paramMap);
	}

	/**
	 * 资格事项的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSingleBidMatter(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (StringUtil.checkNull(request.getParameter("QUAL_NO")) != null) {
			paramMap.put("QUAL_NO", request.getParameter("QUAL_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleBidMatter(paramMap);
	}
	
	
	/**
	 * 评价的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSingleEvaluateInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (StringUtil.checkNull(request.getParameter("EVALUATE_NO")) != null) {
			paramMap.put("EVALUATE_NO", request.getParameter("EVALUATE_NO"));
		}
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return empInfoDao.viewSingleEvaluateInfo(paramMap);
	}

	/**
	 * 外语能力的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List viewSingleForeignLanguage(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
	/*	if (StringUtil.checkNull(request.getParameter("LANGUAGE_NO")) != null) {
			paramMap.put("LANGUAGE_NO", request.getParameter("LANGUAGE_NO"));
		}*/

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleForeignLanguage(paramMap);
	}
	
	/**
	 * 外语能力的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSingleForeignLanguage1(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (!"".equals(StringUtil.checkNull(request.getAttribute("PERSON_ID"))) ) {
			paramMap.put("PERSON_ID", request.getAttribute("PERSON_ID"));
		}else if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		if (StringUtil.checkNull(request.getParameter("LANGUAGE_NO")) != null) {
			paramMap.put("LANGUAGE_NO", request.getParameter("LANGUAGE_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleForeignLanguage1(paramMap);
	}
	
	/**
	 * 培训信息的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSingleTrainingBasic(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (StringUtil.checkNull(request.getParameter("TRAIN_NO")) != null) {
			paramMap.put("TRAIN_NO", request.getParameter("TRAIN_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleTrainingBasic(paramMap);
	}
	

	/**
	 * 表彰事项的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSingleRecognition(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (StringUtil.checkNull(request.getParameter("REWARD_NO")) != null) {
			paramMap.put("REWARD_NO", request.getParameter("REWARD_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleRecognition(paramMap);
	}

	/**
	 * 惩戒事项的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSinglePunishment(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (StringUtil.checkNull(request.getParameter("PUNISH_NO")) != null) {
			paramMap.put("PUNISH_NO", request.getParameter("PUNISH_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSinglePunishment(paramMap);
	}

	/**
	 * 特记事项的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSingleSpecialMatter(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		/*if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}*/
		if (StringUtil.checkNull(request.getParameter("SPECIAL_NO")) != null) {
			paramMap.put("SPECIAL_NO", request.getParameter("SPECIAL_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleSpecialMatter(paramMap);
	}

	/**
	 * 特记事项的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSinglePassportPerson(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (StringUtil.checkNull(request.getParameter("PASSPER_NO")) != null) {
			paramMap.put("PASSPER_NO", request.getParameter("PASSPER_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap
				.put("FLAG", StringUtil.checkNull(request.getParameter("flag")));
		return empInfoDao.viewSinglePassportPerson(paramMap);
	}

	/**
	 * 特记事项的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object informationSearchMain(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return empInfoDao.informationSearchMain(paramMap);
	}

	/**
	 * 发令事项单一的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSingleStartPoint(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (StringUtil.checkNull(request.getParameter("EXP_INSIDE_NO")) != null) {
			paramMap
					.put("EXP_INSIDE_NO", request.getParameter("EXP_INSIDE_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSingleStartPoint(paramMap);
	}

	/**
	 * 发令事项添加页面
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object addStartPointEmployee(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (StringUtil.checkNull(request.getParameter("EXP_INSIDE_NO")) != null) {
			paramMap
					.put("EXP_INSIDE_NO", request.getParameter("EXP_INSIDE_NO"));
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.addStartPointEmployee(paramMap);
	}

	@Override
	public Object querydepartNo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.querydepartNo(paramMap);
	}

	@Override
	public List querydepartPrep(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.querydepartPrep(paramMap);
	}

	@Override
	public Object querydepartNo_prep(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.querydepartNo_prep(paramMap);
	}

	@Override
	public Object viewPrepdepart(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		if (StringUtil.checkNull(request.getParameter("EXP_INSIDE_NO")) != null) {
			paramMap
					.put("EXP_INSIDE_NO", request.getParameter("EXP_INSIDE_NO"));
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewPrepdepart(paramMap);
	}

	/**
	 * 紧急联系人的信息的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int gethrEmergencyAddressList_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.gethrEmergencyAddressList_count(paramMap);
	}

	/**
	 * 地址类型的信息的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int gethrviewAddressMattersList_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.gethrviewAddressMattersList_count(paramMap);
	}

	/**
	 * 地址类型的信息的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int getStartPointList_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getStartPointList_count(paramMap);
	}

	/**
	 * 工作经历的信息的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int getExperiencePointList_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getExperiencePointList_count(paramMap);
	}

	/**
	 * 资格事项的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int viewBidMatter_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewBidMatter_count(paramMap);
	}
	/**
	 * 评价信息的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int viewEvaluateInfo_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return empInfoDao.viewEvaluateInfo_count(paramMap);
	}

	/**
	 * 外语能力的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int viewForeignLanguage_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewTrainingBasic_count(paramMap);
	}
	
	
	
	/**
	 * 培训信息的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int viewTrainingBasic_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewTrainingBasic_count(paramMap);
	}

	/**
	 * 表彰事项的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int viewRecognition_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewRecognition_count(paramMap);
	}

	/**
	 * 惩戒事项的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int viewPunishment_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewPunishment_count(paramMap);
	}

	/**
	 * 特记事项的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int viewSpecialMatter_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSpecialMatter_count(paramMap);
	}

	/**
	 * 护照信息的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int viewPassportPerson_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewPassportPerson_count(paramMap);
	}

	/**
	 * 学历事项的信息的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int viewEducationMatter_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewEducationMatter_count(paramMap);
	}

	/**
	 * 家庭关系的信息的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int getviewFamilyList_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getviewFamilyList_count(paramMap);
	}

	/**
	 * 基本信息页面的hr_emergency_address信息 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewEmergencyAddress(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewEmergencyAddress(paramMap);
	}

	/**
	 * 员工基础信息 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object getPersonalInfoByPid1(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
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
	 * 
	 * @param request
	 * @return List
	 */
	@Override
	public List getPersonalList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));

		// 还未解决是登陆人的PERSONID 还是传回来的PERSONID
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getPersonalList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getPersonalList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 员工基础信息数 (personal number based information)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int getPersonalCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		return empInfoDao.getPersonalCnt(paramMap);

	}

	/**
	 * 毕业学校查询(Graduate school inquires)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public List getEducationList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getEducationList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getEducationList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 添加毕业学校信息(Add graduate school information)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int addEduactionInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		try {
			int count = Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				if (request.getParameter("INSTITUTION_NAME" + i) != null) {

					paramMap.put("INSTITUTION_NAME", request
							.getParameter("INSTITUTION_NAME" + i));// 学校名称
					paramMap.put("SUBJECT_CLASSIFY", request
							.getParameter("SUBJECT_CLASSIFY" + i));// 专业分类
					paramMap
							.put("SUBJECT", request.getParameter("SUBJECT" + i));// 专业

					paramMap.put("SUBJECT_CLASSIFY_TWO", request
							.getParameter("SUBJECT_CLASSIFY_TWO" + i));// 第二专业分类
					paramMap.put("SUBJECT_SECOND", request
							.getParameter("SUBJECT_SECOND" + i));// 第二专业
					paramMap.put("START_DATE", request
							.getParameter("START_YEAR" + i)
							+ "-" + request.getParameter("START_MONTH" + i));// 入学时间
					paramMap.put("END_DATE", request.getParameter("END_YEAR"
							+ i)
							+ "-" + request.getParameter("END_MONTH" + i));// 毕业时间
					paramMap.put("DEGREE_CODE", request
							.getParameter("DEGREE_CODE" + i));// 学历
					// paramMap.put("EDUC_CARD_NO",
					// request.getParameter("EDUC_CARD_NO"+i));
					paramMap.put("SITE_PROVINCE", request
							.getParameter("SITE_PROVINCE" + i));// 所在地省CODE
					paramMap.put("SITE_CITY", request.getParameter("SITE_CITY"
							+ i));// 所在地市CODE
					paramMap.put("FINAL_DEGREE_WHETHER", request
							.getParameter("FINAL_DEGREE_WHETHER" + i));// 是否最终学历

					paramMap
							.put("REMARKS", request.getParameter("REMARKS" + i));// 备注
					this.empInfoDao.addEduactionInfo(paramMap);
					if (request.getParameter("FINAL_DEGREE_WHETHER" + i)
							.toString().equals("Y")) {
						this.empInfoDao.updataEduaction(paramMap);// 修改员工个人信息表最终学历信息
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteEduactionInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String[] paramData = request.getParameterValues("DNO");
			if (paramData != null) {
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int editEduPerInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));

			// ------------------------------employee---------------------------------------
			paramMap.put("WHETHER_COMMUNIST", request
					.getParameter("WHETHER_COMMUNIST"));// 是否共产党员
			paramMap.put("WORK_AREA", request.getParameter("WORK_AREA"));// 工作地
			paramMap
					.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));// 详细人力区分
																					// 37个
			paramMap.put("PROMTR_WORK_TP", request
					.getParameter("PROMTR_WORK_TP"));// 工作类型
			paramMap.put("OUTER_WORK_YEAR", request
					.getParameter("OUTER_WORK_YEAR"));// 社外工龄
			paramMap.put("INSRAREA_ID", request.getParameter("INSRAREA_ID"));// 福利地区
			paramMap.put("INSRAREA_ID_INS", request
					.getParameter("INSRAREA_ID_INS"));// 福利地区
			paramMap.put("PAY_AREA_CD", request.getParameter("PAY_AREA_CD"));// 分公司(大区)
			paramMap.put("PROD_TP", request.getParameter("PROD_TP"));// 产品
			paramMap.put("PROMTR_TP", request.getParameter("PROMTR_TP"));// 促销员所属
			paramMap.put("STAR_TP", request.getParameter("STAR_TP"));// 星级级别
			paramMap.put("PART_TIME_YN", request.getParameter("PART_TIME_YN"));// 是否兼卖
			paramMap.put("COMM_YN", request.getParameter("COMM_YN"));// 是否共建促销员
			paramMap.put("YY_VAC_STD_DATE", request
					.getParameter("YY_VAC_STD_DATE"));// 年假基准
			paramMap
					.put("EVS_TYPE_CODE", request.getParameter("EVS_TYPE_CODE"));// 评价类型
			paramMap.put("EMP_TYPE_START_DATE ", request
					.getParameter("EMP_TYPE_START_DATE "));// 人员类型生效日期
			paramMap.put("C_DATE_LEFT ", request.getParameter("C_DATE_LEFT "));// 实际离职日期

			// ------------------------------HR_PERSONAL_INFO---------------------------------------
			paramMap.put("IDCARD_ADDR", request.getParameter("IDCARD_ADDR"));// 家庭地址
			paramMap.put("BORNPLACE_CODE", request
					.getParameter("BORNPLACE_CODE"));// 籍贯
			paramMap.put("NATION_CODE", request.getParameter("NATION_CODE"));// 民族
			paramMap.put("POLITY_CODE", request.getParameter("POLITY_CODE"));// 政治面貌
			paramMap.put("HEIGHT", request.getParameter("HEIGHT"));// 身高
			paramMap.put("WEIGHT", request.getParameter("WEIGHT"));// 体重
			paramMap.put("BLOOD_TYPE", request.getParameter("BLOOD_TYPE"));// 血型
			paramMap
					.put("DISABILITY_YN", request.getParameter("DISABILITY_YN"));// 是否残疾
			paramMap.put("RECRUITMENT_SOURCE_TYPE", request
					.getParameter("RECRUITMENT_SOURCE_TYPE"));// 采用详细路径
			paramMap.put("LEAVE_REASON", request.getParameter("LEAVE_REASON"));// 离职原因
			paramMap.put("REMARK", request.getParameter("REMARK"));// 备注
			paramMap.put("INSURANCE_TYPE_CODE", request
					.getParameter("INSURANCE_TYPE_CODE"));// 保险类型
			paramMap.put("MANUAL_NUM", request.getParameter("MANUAL_NUM"));// 员工手册编号
			paramMap.put("INSURANCE_COMPANY", request
					.getParameter("INSURANCE_COMPANY"));// 保险公司

			// ------------------------------HR_EMP_PA_INFO---------------------------------------
			paramMap.put("LOVE_FUND_PAYMENT_TYPE", request
					.getParameter("LOVE_FUND_PAYMENT_TYPE"));// 爱心基金支付方式
			paramMap.put("IF_PAYMENT_LOVE_FUND", request
					.getParameter("IF_PAYMENT_LOVE_FUND"));// 是否支付爱心基金
			paramMap.put("IF_PAYMENT_RENT", request
					.getParameter("IF_PAYMENT_RENT"));// 负担房租标志
			paramMap.put("IF_PAYMENT_MEDICAL", request
					.getParameter("IF_PAYMENT_MEDICAL"));// 负担医疗费标志
			paramMap.put("IF_PAYMENT_EDUCATION", request
					.getParameter("IF_PAYMENT_EDUCATION"));// 负担教育费标志

			this.empInfoDao.editEduPerInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改个人基本信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editEmpInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));

			// ------------------------------employee---------------------------------------
			
			this.empInfoDao.editEmpInfo(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_personal_info,hr_employee,hr_address_matters信息(Modify personal
	 * information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editHrPersonInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));

			// ------------------------------employee---------------------------------------
			paramMap.put("LOCAL_NAME", request.getParameter("LOCAL_NAME"));// 中文姓名
			paramMap.put("ENGLISH_NAME", request.getParameter("ENGLISH_NAME"));// 英文姓名
			paramMap.put("ARMY_OR_NOT", request.getParameter("ARMY_OR_NOT"));// 参军与否
			paramMap.put("OBSTACLE_OR_NOT", request.getParameter("OBSTACLE_OR_NOT"));// 障碍与否
			paramMap.put("IDCARD_NO", request.getParameter("IDCARD_NO"));// 证件号
			paramMap.put("IDCARD_START_DATE", request.getParameter("IDCARD_START_DATE"));// 获证日期
			paramMap.put("ISSUING_AUTHORITY", request.getParameter("ISSUING_AUTHORITY"));// 签发机构
			paramMap.put("CV_UPDATE_STATUS", request.getParameter("CV_UPDATE_STATUS"));// CV update status
			paramMap.put("SEXCODE", request.getParameter("SEXCODE"));// 性别
			paramMap.put("DOB", request.getParameter("DOB"));// 出生日期
			paramMap.put("ORIGIN", request.getParameter("ORIGIN")); //出生地
			paramMap.put("FINAL_DEGREE_CODE", request.getParameter("FINAL_DEGREE_CODE")); //最终学历
			paramMap.put("NATIONALITY_CODE", request.getParameter("NATIONALITY_CODE"));// 国籍
			paramMap.put("NATION_CODE", request.getParameter("NATION_CODE"));// 民族
			paramMap.put("RELIGION", request.getParameter("RELIGION")); //宗教
			paramMap.put("CELLPHONE", request.getParameter("CELLPHONE"));// 手机号码
			paramMap.put("MARITAL_STATUS_CODE", request.getParameter("MARITAL_STATUS_CODE"));// 结婚状态
			paramMap.put("WEDDING_DATE", request.getParameter("WEDDING_DATE"));// 结婚日期
			paramMap.put("POLITICAL_OUTLOOK", request.getParameter("POLITICAL_OUTLOOK"));// 政治面貌
			paramMap.put("SING_ID", request.getParameter("SING_ID"));// EagLem_ID
			paramMap.put("EXIST_SINGLE", request.getParameter("EXIST_SINGLE"));// EagLem 与否
			paramMap.put("HOME_PHONE", request.getParameter("HOME_PHONE"));// 家庭电话
			paramMap.put("OFFICE_PHONE", request.getParameter("OFFICE_PHONE"));// 公司电话
			paramMap.put("RESIDENTIAL_DISTINCTION", request.getParameter("RESIDENTIAL_DISTINCTION"));// 住宅区分
			paramMap.put("REG_PLACE", request.getParameter("REG_PLACE"));// 户口所在地
			paramMap.put("EMAIL", request.getParameter("EMAIL"));// 公司邮箱
			paramMap.put("EMAIL_SECOND", request.getParameter("EMAIL_SECOND"));// 个人邮箱
			paramMap.put("FILE_LOCATION", request.getParameter("FILE_LOCATION"));// 档案所在地
			paramMap.put("FILE_ENTER", request.getParameter("FILE_ENTER"));// 档案转入
			paramMap.put("FILE_OUT", request.getParameter("FILE_OUT"));// 档案转出
			paramMap.put("HEIGHT", request.getParameter("HEIGHT"));// 身高
			paramMap.put("WEIGHT", request.getParameter("WEIGHT"));// 体重
			
			this.empInfoDao.editHrPersonInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int updateHrEmployee(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			
			String[] isChecked = request.getParameterValues("viewCheck");
			for(int i = 0; i < isChecked.length; i++){
				paramMap.put("PERSON_ID", paramMap.get("PERSON_ID" + "_" + isChecked[i]));
				paramMap.put("OT_LIMIT", paramMap.get("OT_LIMIT" + "_" + isChecked[i]));
				paramMap.put("OT_LIMIT_100", paramMap.get("OT_LIMIT_100" + "_" + isChecked[i]));
				this.empInfoDao.updateHrEmployee(paramMap);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 修改hr_emergency_address,hr_personal_info信息(Modify personal information and
	 * school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editHrEmergencyAddress(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);

			// --------------------------------------------------------------------
			String emergency_no = StringUtil.checkNull(request
					.getParameter("EMERGENCY_NO"));
			paramMap.put("EMERGENCY_NO", emergency_no);// 主键no
			paramMap.put("EMER_NAME", request.getParameter("EMER_NAME"));// 联系人姓名
			paramMap.put("MAIN_LIAISON_OFFICE", request
					.getParameter("MAIN_LIAISON_OFFICE"));// 紧急联络处与否
			paramMap.put("EMER_TYPE_CODE", request
					.getParameter("EMER_TYPE_CODE"));// 关系
			paramMap.put("EMER_PHONE", request.getParameter("EMER_PHONE"));// 联系电话1
			paramMap.put("EMER_PHONE_SECOND", request
					.getParameter("EMER_PHONE_SECOND"));// 联系电话2
			paramMap.put("EMER_EMAIL", request.getParameter("EMER_EMAIL"));// 邮箱
			paramMap.put("EMER_CELLPHONE", request
					.getParameter("EMER_CELLPHONE"));// 手机
			paramMap.put("EMER_WORK_PHONE", request
					.getParameter("EMER_WORK_PHONE"));// 工作处电话
			paramMap.put("NATIONALITY", request.getParameter("NATIONALITY"));// 国家
			paramMap.put("EMER_ADDRESS", request.getParameter("EMER_ADDRESS"));// 地址
			if (emergency_no != null && !"".equals(emergency_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editHrEmergencyAddress(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				// 插入的时候获得员工的personid
				paramMap.put("PERSON_ID", request
						.getParameter("SINGLE_PERSON_ID"));
				this.empInfoDao.insertHrEmergencyAddress(paramMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_address_matters信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editHrAddressMatters(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);

			// --------------------------------------------------------------------
			String address_no = StringUtil.checkNull(request.getParameter("ADDRESS_NO"));
			paramMap.put("ADDRESS_NO", address_no);// 主键no
			paramMap.put("ADDRESS_TYPE", request.getParameter("ADDRESS_TYPE"));
			paramMap.put("EFFECTIVE_START_DATE", request.getParameter("EFFECTIVE_START_DATE"));// 有效开始日期
			paramMap.put("NATIONALITY", request.getParameter("NATIONALITY"));// 国家
			paramMap.put("ADDRESS_CONTENT", request.getParameter("ADDRESS_CONTENT"));// 地址
			
			if("14013841".equals(StringUtil.checkNull(request.getParameter("ADDRESS_TYPE")))){
				this.empInfoDao.editHrEMPMatters(paramMap);
			}
			if (address_no != null && !"".equals(address_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editHrAddressMatters(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				// 插入的时候获得员工的personid
				paramMap.put("PERSON_ID", request.getParameter("SINGLE_PERSON_ID"));
				this.empInfoDao.insertHrAddressMatters(paramMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int insertResults(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);

			// --------------------------------------------------------------------
			String arrayno = StringUtil.checkNull(request
					.getParameter("arrayno"));
			String arrayname = StringUtil.checkNull(request
					.getParameter("arrayname"));
			String titlename = StringUtil.checkNull(request
					.getParameter("titlename"));
			String strno = "select ROWNUM,h.person_id,h.empid,h.local_name,";
			if (arrayno != null && !"".equals(arrayno)) {
				String[] arrno = arrayno.substring(0, arrayno.length() - 1)
						.split(",");
				for (int i = 0; i < arrno.length; i++) {
					String s1 = arrno[i];
					if (s1.equals("SEXCODE") || s1.equals("DOB")
							|| s1.equals("IDCARD_NO") || s1.equals("IDCARD_START_DATE")
							|| s1.equals("ISSUING_AUTHORITY") || s1.equals("MARITAL_STATUS_CODE")
							|| s1.equals("WEDDING_DATE") || s1.equals("AGE")
							|| s1.equals("NATIONALITY_CODE") || s1.equals("NATION_CODE")
							|| s1.equals("WEDDING_DATE") || s1.equals("WEDDING_DATE")
							|| s1.equals("WEDDING_DATE") || s1.equals("WEDDING_DATE")
							|| s1.equals("WEDDING_DATE") || s1.equals("WEDDING_DATE")
							|| s1.equals("WEDDING_DATE") || s1.equals("WEDDING_DATE")
							|| s1.equals("RELIGION") || s1.equals("POLITICAL_OUTLOOK")
							|| s1.equals("HOME_PHONE") || s1.equals("CELLPHONE")
							|| s1.equals("EXIST_SINGLE") || s1.equals("SING_ID")
							|| s1.equals("EMAIL") || s1.equals("EMAIL_SECOND")
							|| s1.equals("RESIDENTIAL_DISTINCTION") || s1.equals("REG_PLACE")
							|| s1.equals("ORIGIN") || s1.equals("ARMY_OR_NOT")
							|| s1.equals("OBSTACLE_OR_NOT") || s1.equals("CV_UPDATE_STATUS")
							|| s1.equals("FILE_LOCATION") || s1.equals("FILE_ENTER")
							|| s1.equals("FILE_OUT")) {
						if (s1.equals("SEXCODE")) {
							strno = strno + "GET_GLOBAL_NAME(p.SEXCODE, 'vi') SEXCODE,";
						} else if (s1.equals("DOB")) {
							strno = strno + "to_char(p.DOB,'dd/MM/yyyy') DOB,";
						} else if (s1.equals("IDCARD_NO")) {
							strno = strno + "p.IDCARD_NO IDCARD_NO,";
						} else if (s1.equals("IDCARD_START_DATE")) {
							strno = strno + "p.IDCARD_START_DATE,";
						} else if (s1.equals("ISSUING_AUTHORITY")) {
							strno = strno + "p.ISSUING_AUTHORITY,";
						}else if (s1.equals("MARITAL_STATUS_CODE")) {
							strno = strno + "GET_GLOBAL_NAME(p.MARITAL_STATUS_CODE, 'vi') MARITAL_STATUS_CODE,";
						} else if (s1.equals("WEDDING_DATE")) {
							strno = strno + "to_char(p.WEDDING_DATE,'dd/MM/yyyy') WEDDING_DATE,";
						} else if (s1.equals("AGE")) {
							strno = strno + "to_char(SYSDATE,'yyyy')-to_char(p.DOB,'yyyy') AGE,";
				        } else if(s1.equals("NATIONALITY_CODE")) {
							strno = strno + "GET_GLOBAL_NAME(p.NATIONALITY_CODE, 'vi') NATIONALITY_CODE,";
						} else if(s1.equals("NATION_CODE")) {
							strno = strno + "GET_GLOBAL_NAME(p.NATION_CODE, 'vi') NATION_CODE,";
						} else if(s1.equals("RELIGION")) {
							strno = strno + "p.RELIGION,";
						} else if(s1.equals("POLITICAL_OUTLOOK")) {
							strno = strno + "GET_GLOBAL_NAME(p.POLITICAL_OUTLOOK, 'vi') POLITICAL_OUTLOOK,";
						} else if(s1.equals("HOME_PHONE")) {
							strno = strno + "p.HOME_PHONE HOME_PHONE,";
						} else if(s1.equals("CELLPHONE")) {
							strno = strno + "p.CELLPHONE CELLPHONE,";
						} else if(s1.equals("EXIST_SINGLE")) {
							strno = strno + "GET_GLOBAL_NAME(p.EXIST_SINGLE, 'vi') EXIST_SINGLE,";
						} else if(s1.equals("SING_ID")) {
							strno = strno + "p.SING_ID,";
						} else if (s1.equals("EMAIL")) {
							strno = strno + "p.EMAIL,";
						} else if (s1.equals("EMAIL_SECOND")) {
							strno = strno + "p.EMAIL_SECOND,";
						} else if(s1.equals("RESIDENTIAL_DISTINCTION")) {
							strno = strno + "GET_GLOBAL_NAME(p.RESIDENTIAL_DISTINCTION, 'vi') RESIDENTIAL_DISTINCTION,";
						} else if(s1.equals("REG_PLACE")) {
							strno = strno + "p.REG_PLACE,";
						} else if(s1.equals("ORIGIN")) {
							strno = strno + "p.ORIGIN,";
						} else if(s1.equals("ARMY_OR_NOT")) {
							strno = strno + "p.ARMY_OR_NOT,";
						} else if(s1.equals("OBSTACLE_OR_NOT")) {
							strno = strno + "p.OBSTACLE_OR_NOT,";
						} else if(s1.equals("CV_UPDATE_STATUS")) {
							strno = strno + "GET_GLOBAL_NAME(p.CV_UPDATE_STATUS, 'vi') CV_UPDATE_STATUS,";
						} else if(s1.equals("FILE_LOCATION")) {
							strno = strno + "p.FILE_LOCATION,";
						} else if (s1.equals("FILE_ENTER")) {
							strno = strno + "to_char(p.FILE_ENTER,'dd/MM/yyyy') FILE_ENTER,";
						} else if (s1.equals("FILE_OUT")) {
							strno = strno + "to_char(p.FILE_OUT,'dd/MM/yyyy') FILE_OUT,";
						} else {
							strno = strno + "p." + s1 + ",";
						}
					}else if (s1.equals("DEPTNO")) {
							strno = strno + "GET_DEPT_NAME(h.DEPTNO, 'vi') DEPTNO,";
						} else if (s1.equals("HEAD_DEPARTMENT")) {
							strno = strno + "GET_DEPT_MANAGER_INFO(h.DEPTNO) HEAD_DEPARTMENT,";
						} else if (s1.equals("POST_FAMILY")) {
							strno = strno + "GET_GLOBAL_NAME(h.POST_FAMILY, 'vi') POST_FAMILY,";
				        } else if (s1.equals("POST_GRADE_NO")) {
							strno = strno + "GET_GLOBAL_NAME(h.POST_GRADE_NO, 'vi') POST_GRADE_NO,";
				        } else if (s1.equals("POSITION_NO")) {
							strno = strno + "GET_GLOBAL_NAME(h.POSITION_NO, 'vi') POSITION_NO,";
				        } else if (s1.equals("EMP_TYPE_CODE")) {
							strno = strno + "GET_GLOBAL_NAME(h.EMP_TYPE_CODE, 'vi') EMP_TYPE_CODE,";
				        } else if (s1.equals("MAIN_BUSINESS")) {
							strno = strno + "GET_GLOBAL_NAME(h.MAIN_BUSINESS, 'vi') MAIN_BUSINESS,";
				        } else if (s1.equals("COST_CENTER")) {
							strno = strno + "GET_GLOBAL_NAME(h.COST_CENTER, 'vi') COST_CENTER,";
				        } else if (s1.equals("DATE_STARTED")) {
							strno = strno
							+ "to_char(h.DATE_STARTED,'dd/MM/yyyy') DATE_STARTED,";
				        } else if (s1.equals("END_PROBATION_DATE")) {
				        	strno = strno
				        	+ "to_char(h.END_PROBATION_DATE,'dd/MM/yyyy') END_PROBATION_DATE,";
				        } else if (s1.equals("DATE_LEFT")) {
							strno = strno + "to_char(h.DATE_LEFT,'dd/MM/yyyy') DATE_LEFT,";
				        } else if (s1.equals("EMP_OFFICE")) {
							strno = strno + "GET_GLOBAL_NAME(h.EMP_OFFICE, 'vi') EMP_OFFICE,";
						} else {
							strno = strno + "h." + s1 + ",";
						}
				}
			}
			strno = strno.substring(0, strno.length() - 1);
			
			String strwhere = " from hr_employee h, hr_personal_info p where h.person_id=p.person_id(+) and h.EMPID NOT LIKE '111111%' and h.cpny_id=#CPNY_ID:VARCHAR# ";
			String searchNo = this.empInfoDao.querySearchNo(paramMap);
			paramMap.put("TITLENAME", titlename);
			paramMap.put("CONTENT", strno + strwhere);
			paramMap.put("SEARCHNO", searchNo);
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CREATED_IP", admin.getAdminIP());
			this.empInfoDao.insertResults(paramMap);

			if (arrayname != null && !"".equals(arrayname)) {
				String[] arrname = arrayname.substring(0,
						arrayname.length() - 1).split(",");
				String[] arrno = arrayno.substring(0, arrayno.length() - 1)
						.split(",");
				// 先插入姓名,工号
				paramMap.put("SEARCH_NAME", TipMessage.getTipMessage(
						"alert.pa.pasalarycanshu.xingming", request));//姓名
				paramMap.put("ORDERNO", "2");
				paramMap.put("NAME_NO", "LOCAL_NAME");
				this.empInfoDao.insertResultsName(paramMap);
				paramMap.put("SEARCH_NAME", TipMessage.getTipMessage(
						"alert.pa.pasalarycanshu.shehao", request));//工号
				paramMap.put("ORDERNO", "1");
				paramMap.put("NAME_NO", "EMPID");
				this.empInfoDao.insertResultsName(paramMap);
				for (int i = 0; i < arrname.length; i++) {
					String s2 = arrname[i];
					String s3 = arrno[i];
					paramMap.put("SEARCH_NAME", s2);
					paramMap.put("ORDERNO", i + 3);
					paramMap.put("NAME_NO", s3);
					this.empInfoDao.insertResultsName(paramMap);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_family信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editHrFamily(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);

			// --------------------------------------------------------------------
			String family_no = StringUtil.checkNull(request.getParameter("FAMILY_NO"));
			paramMap.put("FAMILY_NO", family_no);// 主键no
			paramMap.put("FAM_NAME", request.getParameter("FAM_NAME"));
			paramMap.put("FAM_ENGLISH_NAME", request.getParameter("FAM_ENGLISH_NAME"));
			paramMap.put("FAM_KOREAN_NAME", request.getParameter("FAM_KOREAN_NAME"));
			paramMap.put("FAM_ANOTHER_NAME", request.getParameter("FAM_ANOTHER_NAME"));
			paramMap.put("FAM_KOREAN_NAME", request.getParameter("FAM_KOREAN_NAME"));
			paramMap.put("FAM_ANOTHER_NAME", request.getParameter("FAM_ANOTHER_NAME"));
			paramMap.put("FAM_TYPE_CODE", request.getParameter("FAM_TYPE_CODE"));
			paramMap.put("GENDER", request.getParameter("GENDER"));
			paramMap.put("FAM_BORNDATE", request.getParameter("FAM_BORNDATE"));
			paramMap.put("EMERGENCY_LIAISON_OFFICE", request.getParameter("EMERGENCY_LIAISON_OFFICE"));
			paramMap.put("FAM_ADDRESS", request.getParameter("FAM_ADDRESS"));
			paramMap.put("EFFECTIVE_START_DATE", request.getParameter("EFFECTIVE_START_DATE"));
			paramMap.put("NATIONALITY", request.getParameter("NATIONALITY"));
			paramMap.put("FAM_FAMILY_PHONE", request.getParameter("FAM_FAMILY_PHONE"));
			paramMap.put("FAM_EMAIL", request.getParameter("FAM_EMAIL"));
			paramMap.put("FAM_PHONE", request.getParameter("FAM_PHONE"));
			paramMap.put("WORK_PHONE", request.getParameter("WORK_PHONE"));
			paramMap.put("AGE", request.getParameter("AGE"));
			paramMap.put("FAM_EDUCATION", request.getParameter("FAM_EDUCATION"));
			paramMap.put("FAM_COMPANY_NAME", request.getParameter("FAM_COMPANY_NAME"));
			paramMap.put("OCUPATION", request.getParameter("OCUPATION"));
			paramMap.put("REMARKS", request.getParameter("REMARKS"));
			if (family_no != null && !"".equals(family_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editHrFamily(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				// 插入的时候获得员工的personid
				paramMap.put("PERSON_ID", request
						.getParameter("SINGLE_PERSON_ID"));
				this.empInfoDao.insertHrFamily(paramMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_work_experience信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editExperiencePoint(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// --------------------------------------------------------------------
			String work_exper_no = StringUtil.checkNull(request
					.getParameter("WORK_EXPER_NO"));
			paramMap.put("WORK_EXPER_NO", work_exper_no);// 主键no
			paramMap.put("CPNY_NAME", request.getParameter("CPNY_NAME"));
			paramMap.put("DEPT_NAME", request.getParameter("DEPT_NAME"));
			paramMap.put("DUTY", request.getParameter("DUTY"));
			paramMap.put("START_DATE", request.getParameter("START_DATE"));
			paramMap.put("END_DATE", request.getParameter("END_DATE"));
			paramMap.put("POSITION", request.getParameter("POSITION"));
			paramMap.put("PAY_YEAR", request.getParameter("PAY_YEAR"));
			paramMap.put("RESIGN_REASON", request.getParameter("RESIGN_REASON"));
			paramMap.put("REMARK", request.getParameter("REMARK"));
			if (work_exper_no != null && !"".equals(work_exper_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editExperiencePoint(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				// 插入的时候获得员工的personid
				paramMap.put("PERSON_ID", request
						.getParameter("SINGLE_PERSON_ID"));
				this.empInfoDao.insertExperiencePoint(paramMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_education信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editEducationMatter(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// --------------------------------------------------------------------
			String educ_no = StringUtil.checkNull(request
					.getParameter("EDUC_NO"));
			
			LinkedHashMap fileMapDel = new LinkedHashMap();
			if (educ_no != null && !"".equals(educ_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editEducationMatter(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				// 首先查出生成的educNo
				String educNo = this.empInfoDao.queryEducNo(paramMap);
				paramMap.put("EDUCNO", educNo);
				// 插入的时候获得员工的personid
				// 获取附件信息
				String[] fileName = request.getParameterValues("fileName");
				String[] fileUrl = request.getParameterValues("fileUrl");
				if (!"".equals(fileName) && !"".equals(fileUrl)
						&& fileName != null && fileUrl != null) {
					String fileNameStr = "";
					String fileUrlStr = "";
					for (int i = 0; i < fileName.length; i++) {
						if (i == 0) {
							fileNameStr = fileName[i];
						} else {
							fileNameStr += ";" + fileName[i];
						}
					}
					for (int i = 0; i < fileUrl.length; i++) {
						if (i == 0) {
							fileUrlStr = fileUrl[i];
						} else {
							fileUrlStr += ";" + fileUrl[i];
						}
					}
					paramMap.put("fileName", fileNameStr);
					paramMap.put("fileUrl", fileUrlStr);
				}
				this.empInfoDao.insertEducationMatter(paramMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_education信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editRecognition(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// --------------------------------------------------------------------
			String reward_no = StringUtil.checkNull(request
					.getParameter("REWARD_NO"));
			paramMap.put("REWARD_NO", reward_no);// 主键no
			paramMap.put("REWARD_TYPE", request.getParameter("REWARD_TYPE"));
			paramMap.put("REWARD_DATE", request.getParameter("REWARD_DATE"));
			paramMap.put("REWARD_CNPY", request.getParameter("REWARD_CNPY"));
			paramMap.put("OTHER_TYPE", request.getParameter("OTHER_TYPE"));
			paramMap.put("REWARD", request.getParameter("REWARD"));
			paramMap.put("REWARD_TYPE_CODE", request.getParameter("REWARD_TYPE_CODE"));
			paramMap.put("PERSONNEL_CARD_INQUIRY", request.getParameter("PERSONNEL_CARD_INQUIRY"));
			paramMap.put("REMARKS", request.getParameter("REMARKS"));
			LinkedHashMap fileMapDel = new LinkedHashMap();
			if (reward_no != null && !"".equals(reward_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editRecognition(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request
						.getParameter("SINGLE_PERSON_ID"));
				// 获取附件信息
				String[] fileName = request.getParameterValues("fileName");
				String[] fileUrl = request.getParameterValues("fileUrl");
				if (!"".equals(fileName) && !"".equals(fileUrl)
						&& fileName != null && fileUrl != null) {
					String fileNameStr = "";
					String fileUrlStr = "";
					for (int i = 0; i < fileName.length; i++) {
						if (i == 0) {
							fileNameStr = fileName[i];
						} else {
							fileNameStr += ";" + fileName[i];
						}
					}
					for (int i = 0; i < fileUrl.length; i++) {
						if (i == 0) {
							fileUrlStr = fileUrl[i];
						} else {
							fileUrlStr += ";" + fileUrl[i];
						}
					}
					paramMap.put("fileName", fileNameStr);
					paramMap.put("fileUrl", fileUrlStr);
				}
				this.empInfoDao.insertRecognition(paramMap);
			}
			this.empInfoDao.falingcunchuRecognition(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_Punishment信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editPunishment(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// --------------------------------------------------------------------
			String punish_no = StringUtil.checkNull(request
					.getParameter("PUNISH_NO"));
			paramMap.put("PUNISH_NO", punish_no);// 主键no
			
			LinkedHashMap fileMapDel = new LinkedHashMap();
			if (punish_no != null && !"".equals(punish_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editPunishment(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request
						.getParameter("SINGLE_PERSON_ID"));
				// 获取附件信息
				String[] fileName = request.getParameterValues("fileName");
				String[] fileUrl = request.getParameterValues("fileUrl");
				if (!"".equals(fileName) && !"".equals(fileUrl)
						&& fileName != null && fileUrl != null) {
					String fileNameStr = "";
					String fileUrlStr = "";
					for (int i = 0; i < fileName.length; i++) {
						if (i == 0) {
							fileNameStr = fileName[i];
						} else {
							fileNameStr += ";" + fileName[i];
						}
					}
					for (int i = 0; i < fileUrl.length; i++) {
						if (i == 0) {
							fileUrlStr = fileUrl[i];
						} else {
							fileUrlStr += ";" + fileUrl[i];
						}
					}
					paramMap.put("fileName", fileNameStr);
					paramMap.put("fileUrl", fileUrlStr);
				}
				this.empInfoDao.insertPunishment(paramMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_special_matter信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editSpecialMatter(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// --------------------------------------------------------------------
			String special_no = StringUtil.checkNull(request
					.getParameter("SPECIAL_NO"));
			paramMap.put("SPECIAL_NO", special_no);// 主键no
			paramMap.put("INFOR_DIS_CODE", request
					.getParameter("INFOR_DIS_CODE"));
			paramMap.put("GENERATION_TITLE", request
					.getParameter("GENERATION_TITLE"));
			paramMap.put("SPECIAL_CONTENT", request
					.getParameter("SPECIAL_CONTENT"));
			paramMap.put("REGISTRATION_DATE", request
					.getParameter("REGISTRATION_DATE"));
			LinkedHashMap fileMapDel = new LinkedHashMap();
			if (special_no != null && !"".equals(special_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editSpecialMatter(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request
						.getParameter("SINGLE_PERSON_ID"));
				// 获取附件信息
				String[] fileName = request.getParameterValues("fileName");
				String[] fileUrl = request.getParameterValues("fileUrl");
				if (!"".equals(fileName) && !"".equals(fileUrl)
						&& fileName != null && fileUrl != null) {
					String fileNameStr = "";
					String fileUrlStr = "";
					for (int i = 0; i < fileName.length; i++) {
						if (i == 0) {
							fileNameStr = fileName[i];
						} else {
							fileNameStr += ";" + fileName[i];
						}
					}
					for (int i = 0; i < fileUrl.length; i++) {
						if (i == 0) {
							fileUrlStr = fileUrl[i];
						} else {
							fileUrlStr += ";" + fileUrl[i];
						}
					}
					paramMap.put("fileName", fileNameStr);
					paramMap.put("fileUrl", fileUrlStr);
				}
				this.empInfoDao.insertSpecialMatter(paramMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_special_matter信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editPassportPerson(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// --------------------------------------------------------------------
			String passper_no = StringUtil.checkNull(request
					.getParameter("PASSPER_NO"));
			paramMap.put("PASSPER_NO", passper_no);// 主键no
			LinkedHashMap fileMapDel = new LinkedHashMap();
			if (passper_no != null && !"".equals(passper_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editPassportPerson(paramMap);

			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request
						.getParameter("SINGLE_PERSON_ID"));
				// 获取附件信息
				String[] fileName = request.getParameterValues("fileName");
				String[] fileUrl = request.getParameterValues("fileUrl");
				if (!"".equals(fileName) && !"".equals(fileUrl)
						&& fileName != null && fileUrl != null) {
					String fileNameStr = "";
					String fileUrlStr = "";
					for (int i = 0; i < fileName.length; i++) {
						if (i == 0) {
							fileNameStr = fileName[i];
						} else {
							fileNameStr += ";" + fileName[i];
						}
					}
					for (int i = 0; i < fileUrl.length; i++) {
						if (i == 0) {
							fileUrlStr = fileUrl[i];
						} else {
							fileUrlStr += ";" + fileUrl[i];
						}
					}
					paramMap.put("fileName", fileNameStr);
					paramMap.put("fileUrl", fileUrlStr);
				}
				this.empInfoDao.insertPassportPerson(paramMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_qualification信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editBidMatter(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// --------------------------------------------------------------------
			String qual_no = StringUtil.checkNull(request
					.getParameter("QUAL_NO"));
			paramMap.put("QUAL_NO", qual_no);// 主键no
			paramMap.put("QUAL_NAME", request.getParameter("QUAL_NAME"));
			paramMap.put("DATE_OBTAINED", request.getParameter("DATE_OBTAINED"));
			paramMap.put("QUAL_INSTITUTE", request.getParameter("QUAL_INSTITUTE"));
			paramMap.put("VALIDITY_DATE", request.getParameter("VALIDITY_DATE"));
			paramMap.put("QUAL_CARD_NO", request.getParameter("QUAL_CARD_NO"));
			paramMap.put("QUAL_LEVEL", request.getParameter("QUAL_LEVEL"));
			paramMap.put("QUAL_GRADE", request.getParameter("QUAL_GRADE"));
			paramMap.put("QUAL_REMARK", request.getParameter("QUAL_REMARK"));
			paramMap.put("QUAL_SUBMIT_DATE", request.getParameter("QUAL_SUBMIT_DATE"));
			LinkedHashMap fileMapDel = new LinkedHashMap();
			if (qual_no != null && !"".equals(qual_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editBidMatter(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				// 首先查出生成的educNo
				String qualNo = this.empInfoDao.queryQualNo(paramMap);
				paramMap.put("qualNo", qualNo);
				// 插入的时候获得员工的personid
				paramMap.put("PERSON_ID", request
						.getParameter("SINGLE_PERSON_ID"));
				// 获取附件信息
				String[] fileName = request.getParameterValues("fileName");
				String[] fileUrl = request.getParameterValues("fileUrl");
				if (!"".equals(fileName) && !"".equals(fileUrl)
						&& fileName != null && fileUrl != null) {
					String fileNameStr = "";
					String fileUrlStr = "";
					for (int i = 0; i < fileName.length; i++) {
						if (i == 0) {
							fileNameStr = fileName[i];
						} else {
							fileNameStr += ";" + fileName[i];
						}
					}
					for (int i = 0; i < fileUrl.length; i++) {
						if (i == 0) {
							fileUrlStr = fileUrl[i];
						} else {
							fileUrlStr += ";" + fileUrl[i];
						}
					}
					paramMap.put("fileName", fileNameStr);
					paramMap.put("fileUrl", fileUrlStr);
				}
				this.empInfoDao.insertBidMatter(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 修改hr_evaluate_info信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editEvaluateInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
			.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// --------------------------------------------------------------------
			String qual_no = StringUtil.checkNull(request
					.getParameter("EVALUATE_NO"));
			
			LinkedHashMap fileMapDel = new LinkedHashMap();
			if (qual_no != null && !"".equals(qual_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editEvaluateInfo(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				// 首先查出生成的educNo
				String evaluateNo = this.empInfoDao.queryEvaluateNo(paramMap);
				paramMap.put("evaluateNo", evaluateNo);
				// 插入的时候获得员工的personid
				paramMap.put("PERSON_ID", request
						.getParameter("SINGLE_PERSON_ID"));
				// 获取附件信息
				String[] fileName = request.getParameterValues("fileName");
				String[] fileUrl = request.getParameterValues("fileUrl");
				if (!"".equals(fileName) && !"".equals(fileUrl)
						&& fileName != null && fileUrl != null) {
					String fileNameStr = "";
					String fileUrlStr = "";
					for (int i = 0; i < fileName.length; i++) {
						if (i == 0) {
							fileNameStr = fileName[i];
						} else {
							fileNameStr += ";" + fileName[i];
						}
					}
					for (int i = 0; i < fileUrl.length; i++) {
						if (i == 0) {
							fileUrlStr = fileUrl[i];
						} else {
							fileUrlStr += ";" + fileUrl[i];
						}
					}
					paramMap.put("fileName", fileNameStr);
					paramMap.put("fileUrl", fileUrlStr);
				}
				this.empInfoDao.insertEvaluateInfo(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_language_level信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editForeignLanguage(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// --------------------------------------------------------------------
			String language_no = StringUtil.checkNull(request
					.getParameter("LANGUAGE_NO"));
			paramMap.put("LANGUAGE_NO", language_no);// 主键no
			String basic_language = StringUtil.checkNull(request
					.getParameter("BASIC_LANGUAGE"));
			if("".equals(basic_language) || !"Y".equals(basic_language)){
				paramMap.put("BASIC_LANGUAGE", "N");
			}

			LinkedHashMap fileMapDel = new LinkedHashMap();
			if (language_no != null && !"".equals(language_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editForeignLanguage(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				// 首先查出生成的educNo
				String languageNo = this.empInfoDao.queryLanguageNo(paramMap);
				paramMap.put("languageNo", languageNo);
				// 插入的时候获得员工的personid
				paramMap.put("PERSON_ID", request
						.getParameter("SINGLE_PERSON_ID"));
				// 获取附件信息
				String[] fileName = request.getParameterValues("fileName");
				String[] fileUrl = request.getParameterValues("fileUrl");
				if (!"".equals(fileName) && !"".equals(fileUrl)
						&& fileName != null && fileUrl != null) {
					String fileNameStr = "";
					String fileUrlStr = "";
					for (int i = 0; i < fileName.length; i++) {
						if (i == 0) {
							fileNameStr = fileName[i];
						} else {
							fileNameStr += ";" + fileName[i];
						}
					}
					for (int i = 0; i < fileUrl.length; i++) {
						if (i == 0) {
							fileUrlStr = fileUrl[i];
						} else {
							fileUrlStr += ";" + fileUrl[i];
						}
					}
					paramMap.put("fileName", fileNameStr);
					paramMap.put("fileUrl", fileUrlStr);
				}
				this.empInfoDao.insertForeignLanguage(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	/**
	 * 修改hr_training_info信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editTrainingBasic(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// --------------------------------------------------------------------
			String train_no = StringUtil.checkNull(request
					.getParameter("TRAIN_NO"));
			
			LinkedHashMap fileMapDel = new LinkedHashMap();
			if (train_no != null && !"".equals(train_no)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editTrainingBasic(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				// 首先查出生成的educNo
				String trainNo = this.empInfoDao.queryTrainNo(paramMap);
				paramMap.put("trainNo", trainNo);
				// 插入的时候获得员工的personid
				paramMap.put("PERSON_ID", request
						.getParameter("SINGLE_PERSON_ID"));
				// 获取附件信息
				String[] fileName = request.getParameterValues("fileName");
				String[] fileUrl = request.getParameterValues("fileUrl");
				if (!"".equals(fileName) && !"".equals(fileUrl)
						&& fileName != null && fileUrl != null) {
					String fileNameStr = "";
					String fileUrlStr = "";
					for (int i = 0; i < fileName.length; i++) {
						if (i == 0) {
							fileNameStr = fileName[i];
						} else {
							fileNameStr += ";" + fileName[i];
						}
					}
					for (int i = 0; i < fileUrl.length; i++) {
						if (i == 0) {
							fileUrlStr = fileUrl[i];
						} else {
							fileUrlStr += ";" + fileUrl[i];
						}
					}
					paramMap.put("fileName", fileNameStr);
					paramMap.put("fileUrl", fileUrlStr);
				}
				this.empInfoDao.insertTrainingBasic(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_language_level信息(Modify personal information and school information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int updatePersonInfoPhoto(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// --------------------------------------------------------------------
			String empid = StringUtil.checkNull(request.getParameter("empid"));
			String ess_no = StringUtil
					.checkNull(request.getParameter("ess_no"));
			String photourl = StringUtil.checkNull(request
					.getParameter("photourl"));
			String radiophoto = StringUtil.checkNull(request
					.getParameter("radiophoto"));
			if (!"".equals(empid) && !"".equals(ess_no) && !"".equals(photourl)) {
				String[] empidarray = empid.split(",");
				String[] ess_noarray = ess_no.split(",");
				String[] photourlarray = photourl.split(",");
				paramMap.put("CPNY_ID", admin.getCpnyId());
				for (int i = 0; i < empidarray.length; i++) {
					paramMap.put("EMPID", empidarray[i]);
					paramMap.put("ESS_NO", ess_noarray[i]);
					paramMap.put("PHOTOURL", photourlarray[i]);
					this.empInfoDao.updatePersonInfoPhoto(paramMap);
					this.empInfoDao.updateEssFile(paramMap);

				}
			}
			// 删除查无此人的照片
			this.empInfoDao.deletePhotoEssFile(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改hr_experience_inside信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editStartPoint(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);

			if(!"".equals(StringUtil.checkNull(paramMap.get("SEQ")))){
				this.empInfoDao.editStartPoint(paramMap);
				paramMap.put("expInsideNo", paramMap.get("SEQ"));
			}else{
				this.empInfoDao.insertStartPoint(paramMap);
			}
			//lipeng 2018/03/13 更新离职发令后的转正发令为删除状态,不然离职令失效
			this.empInfoDao.updatePositivePointActivity(paramMap);
			this.empInfoDao.falingcunchu(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteHrEmergencyAddress(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap.put("EMERGENCY_NO", request.getParameter("EMERGENCY_NO"));// 紧急联系人no
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));// 英文姓名
			this.empInfoDao.deleteHrEmergencyAddress(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteHrAddressMatters(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap.put("ADDRESS_NO", request.getParameter("ADDRESS_NO"));// 主键no
			this.empInfoDao.deleteHrAddressMatters(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteHrFamily(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap.put("FAMILY_NO", request.getParameter("FAMILY_NO"));// 主键no
			this.empInfoDao.deleteHrFamily(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteStartPoint(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			String cpnyid = admin.getCpnyId();
			// ------------------------------employee---------------------------------------
			paramMap.put("SEQ", request.getParameter("SEQ"));// 主键no
			this.empInfoDao.deleteStartPoint(paramMap);

			// 查出删除这一条后面的最近的一条发令
			LinkedHashMap linkMap = (LinkedHashMap) empInfoDao
					.queryMaxCreateDate(paramMap);
			//paramMap.put("START_DATE", String.valueOf(linkMap
				//	.get("START_DATE")));
			// 把删除这一条后面的最近的一条发令 的activity由1变为0
			this.empInfoDao.updateActivity(paramMap);
			// 调用发令存储
			this.empInfoDao.falingcunchu(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteExperiencePoint(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap
					.put("WORK_EXPER_NO", request.getParameter("WORK_EXPER_NO"));// 主键no
			this.empInfoDao.deleteExperiencePoint(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteEducationMatter(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap.put("EDUC_NO", request.getParameter("EDUC_NO"));// 主键no
			this.empInfoDao.deleteEducationMatter(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteBidMatter(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap.put("QUAL_NO", request.getParameter("QUAL_NO"));// 主键no
			this.empInfoDao.deleteBidMatter(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@Override
	public int deleteEvaluateInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
			.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap.put("EVALUATE_NO", request.getParameter("EVALUATE_NO"));// 主键no
			this.empInfoDao.deleteEvaluateInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@Override
	public int deleteTrainingBasic(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
			.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap.put("TRAIN_NO", request.getParameter("TRAIN_NO"));// 主键no
			this.empInfoDao.deleteTrainingBasic(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteForeignLanguage(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap.put("LANGUAGE_NO", request.getParameter("LANGUAGE_NO"));// 主键no
			this.empInfoDao.deleteForeignLanguage(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteRecognition(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap.put("REWARD_NO", request.getParameter("REWARD_NO"));// 主键no
			this.empInfoDao.deleteRecognition(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deletePunishment(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap.put("PUNISH_NO", request.getParameter("PUNISH_NO"));// 主键no
			this.empInfoDao.deletePunishment(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteSpecialMatter(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String jsonString = request.getParameter("jsonData");
			List<LinkedHashMap<String, Object>> specialMatterList = ObjectBindUtil.getRequestJsonData(jsonString);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			if (jsonString.equals("")) {
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				paramMap.put("SPECIAL_NO", request.getParameter("SPECIAL_NO"));// 主键no
				this.empInfoDao.deleteSpecialMatter(paramMap);
			} else {
				this.empInfoDao.updateList("deleteSpecialMatterList", specialMatterList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deletePassportPerson(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap.put("PASSPER_NO", request.getParameter("PASSPER_NO"));// 主键no
			this.empInfoDao.deletePassportPerson(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteTitlename(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			String searchno = StringUtil.checkNull(request
					.getParameter("searchno"));
			String titlename = StringUtil.checkNull(request
					.getParameter("titlename"));
			if (searchno != null && !"".equals(searchno) && titlename != null
					&& !"".equals(titlename)) {
				String[] arrayno = searchno.split(",");
				String[] arrayname = titlename.split(",");
				for (int i = 0; i < arrayno.length; i++) {
					paramMap.put("SEARCHNO", arrayno[i]);
					paramMap.put("TITLENAME", arrayname[i]);
					this.empInfoDao.deleteSearchname(paramMap);
					this.empInfoDao.deleteTitlename(paramMap);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int editTempEduPerInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));

			// ------------------------------employee---------------------------------------
			paramMap.put("ID_CARD_NO", request.getParameter("ID_CARD_NO"));// ID卡号
			if (request.getParameter("ID_CARD_NO").length() > 1) {// 检查ID卡号是否重复
				paramMap.put("CPNY_ID", admin.getCpnyId());
				int idCardNoCnt = empInfoDao.getEmpIdCardNoCnt(paramMap);
				if (idCardNoCnt > 0) {
					return 2;
				}
			}
			paramMap.put("JOB_TITLE_CD", request.getParameter("JOB_TITLE_CD"));// 职务
			paramMap.put("WHETHER_COMMUNIST", request
					.getParameter("WHETHER_COMMUNIST"));// 是否共产党员
			paramMap.put("WORK_AREA", request.getParameter("WORK_AREA"));// 工作地
			paramMap.put("SHIFT_NO", request.getParameter("SHIFT_NO"));// 班号

			paramMap
					.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));// 详细人力区分
																					// 37个
			paramMap.put("PROMTR_WORK_TP", request
					.getParameter("PROMTR_WORK_TP"));// 工作类型
			paramMap.put("OUTER_WORK_YEAR", request
					.getParameter("OUTER_WORK_YEAR"));// 社外工龄
			paramMap.put("INSRAREA_ID", request.getParameter("INSRAREA_ID"));// 福利地区
			paramMap.put("INSRAREA_ID_INS", request
					.getParameter("INSRAREA_ID_INS"));// 福利地区
			paramMap.put("PAY_AREA_CD", request.getParameter("PAY_AREA_CD"));// 分公司(大区)
			paramMap.put("PROD_TP", request.getParameter("PROD_TP"));// 产品
			paramMap.put("PROMTR_TP", request.getParameter("PROMTR_TP"));// 促销员所属
			paramMap.put("STAR_TP", request.getParameter("STAR_TP"));// 星级级别
			paramMap.put("PART_TIME_YN", request.getParameter("PART_TIME_YN"));// 是否兼卖
			paramMap.put("COMM_YN", request.getParameter("COMM_YN"));// 是否共建促销员
			paramMap.put("YY_VAC_STD_DATE", request
					.getParameter("YY_VAC_STD_DATE"));// 年假基准
			paramMap
					.put("EVS_TYPE_CODE", request.getParameter("EVS_TYPE_CODE"));// 评价类型
			paramMap.put("C_DATE_LEFT ", request.getParameter("C_DATE_LEFT "));// 实际离职日期
			// paramMap.put("EMP_TYPE_START_DATE ",
			// request.getParameter("EMP_TYPE_START_DATE "));//人员类型生效日期

			// ------------------------------HR_PERSONAL_INFO---------------------------------------
			paramMap.put("SEXCODE", request.getParameter("SEX"));// 性别
			paramMap.put("FINAL_DEGREE_CODE", request
					.getParameter("FINAL_DEGREE_CODE"));// 学位
			paramMap
					.put("REG_TYPE_CODE", request.getParameter("REG_TYPE_CODE"));// 户口性质
			paramMap.put("DOB", request.getParameter("DOB"));// 出生日期
			paramMap.put("WEDD_DATE", request.getParameter("WEDD_DATE"));// 结婚纪念日
			paramMap.put("IDCARD_NO", request.getParameter("IDCARD_NO"));// 身份证号
			paramMap.put("EMAIL", request.getParameter("EMAIL"));// 邮箱
			paramMap.put("HOME_PHONE", request.getParameter("HOME_PHONE"));// 家庭电话
			paramMap.put("OFFICE_PHONE", request.getParameter("OFFICE_PHONE"));// 办公电话
			paramMap.put("CELLPHONE", request.getParameter("CELLPHONE"));// 手机号码
			paramMap.put("REG_PLACE", request.getParameter("REG_PLACE"));// 户口所在地
			paramMap.put("HOME_ADDRESS", request.getParameter("HOME_ADDRESS"));// 家庭住址
			paramMap.put("POSTALCODE", request.getParameter("POSTALCODE"));// 邮编

			paramMap.put("IDCARD_ADDR", request.getParameter("IDCARD_ADDR"));// 现地址
			paramMap.put("BORNPLACE_CODE", request
					.getParameter("BORNPLACE_CODE"));// 籍贯
			paramMap.put("NATION_CODE", request.getParameter("NATION_CODE"));// 民族
			paramMap.put("POLITY_CODE", request.getParameter("POLITY_CODE"));// 政治面貌
			paramMap.put("HEIGHT", request.getParameter("HEIGHT"));// 身高
			paramMap.put("WEIGHT", request.getParameter("WEIGHT"));// 体重
			paramMap.put("BLOOD_TYPE", request.getParameter("BLOOD_TYPE"));// 血型
			paramMap
					.put("DISABILITY_YN", request.getParameter("DISABILITY_YN"));// 是否残疾
			paramMap.put("RECRUITMENT_SOURCE_TYPE", request
					.getParameter("RECRUITMENT_SOURCE_TYPE"));// 采用详细路径（招聘来源）
			paramMap.put("LEAVE_REASON", request.getParameter("LEAVE_REASON"));// 离职原因
			paramMap.put("REMARK", request.getParameter("REMARK"));// 备注
			paramMap.put("INSURANCE_TYPE_CODE", request
					.getParameter("INSURANCE_TYPE_CODE"));// 保险类型
			paramMap.put("MANUAL_NUM", request.getParameter("MANUAL_NUM"));// 员工手册编号
			paramMap.put("INSURANCE_COMPANY", request
					.getParameter("INSURANCE_COMPANY"));// 保险公司

			// ------------------------------HR_EMP_PA_INFO---------------------------------------
			// paramMap.put("PAY_GRADE",
			// request.getParameter("PAY_GRADE"));//工资级号
			// paramMap.put("PAY_STEP",
			// request.getParameter("PAY_STEP"));//工资级号等级
			paramMap.put("BASE_PAY", request.getParameter("BASE_PAY"));// 基本工资
			paramMap.put("VARB_PAY", request.getParameter("VARB_PAY"));// 变动工资
			// paramMap.put("ANSAL", request.getParameter("ANSAL"));//年薪
			paramMap.put("BANK_ID", request.getParameter("BANK_ID"));// 银行代码
			paramMap.put("CARD_NAME", request.getParameter("CARD_NAME"));// 开户行
			paramMap.put("CARD_NO", request.getParameter("CARD_NO"));// 银行账号
			paramMap
					.put("EXPNS_BANK_CD", request.getParameter("EXPNS_BANK_CD"));// 费用银行代码
			paramMap.put("EXPNS_BANK_BRNCH_NM", request
					.getParameter("EXPNS_BANK_BRNCH_NM"));// 费用开户行
			paramMap.put("EXPNS_BANK_ACCT_NO", request
					.getParameter("EXPNS_BANK_ACCT_NO"));// 费用银行账号
			paramMap.put("PROB_PAY_RAT", request.getParameter("PROB_PAY_RAT"));// 试用期比例

			paramMap.put("LOVE_FUND_PAYMENT_TYPE", request
					.getParameter("LOVE_FUND_PAYMENT_TYPE"));// 爱心基金支付方式
			paramMap.put("IF_PAYMENT_LOVE_FUND", request
					.getParameter("IF_PAYMENT_LOVE_FUND"));// 是否支付爱心基金
			paramMap.put("IF_PAYMENT_RENT", request
					.getParameter("IF_PAYMENT_RENT"));// 负担房租标志
			paramMap.put("IF_PAYMENT_MEDICAL", request
					.getParameter("IF_PAYMENT_MEDICAL"));// 负担医疗费标志
			paramMap.put("IF_PAYMENT_EDUCATION", request
					.getParameter("IF_PAYMENT_EDUCATION"));// 负担教育费标志

			this.empInfoDao.editTempEduPerInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 发令查询(dekreti inquires)
	 * 
	 * @param obj
	 * @return list
	 */
	@Override
	public List getExpInsideList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getExpInsideList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getExpInsideList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 发令查询(dekreti inquires)
	 * 
	 * @param obj
	 * @return list
	 */
	@Override
	public List getAssignmentList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getAssignmentList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getAssignmentList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 工会信息查询(Left information query)
	 * 
	 * @param obj
	 * @return list
	 */
	public List getTradeunionList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getTradeunionList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getTradeunionList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 离职信息查询(Left information query)
	 * 
	 * @param obj
	 * @return list
	 */
	@Override
	public List getResignationInfo(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getResignationInfo(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getResignationInfo(paramMap);
		}
		return retrunList;
	}

	/**
	 * 评价信息(Evaluation information)
	 * 
	 * @param obj
	 * @return list
	 */
	@Override
	public List getEvsInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		retrunList = empInfoDao.getEvsInfoList(paramMap);
		return retrunList;
	}

	/**
	 * 添加评价信息(add Evaluation information)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int addEvsInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		try {
			int count = Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				if (request.getParameter("EV_PERIOD" + i) != null) {

					paramMap.put("EV_PERIOD", request.getParameter("EV_PERIOD"
							+ i));
					paramMap.put("EV_TYPE_ID", request
							.getParameter("EV_TYPE_ID" + i));
					paramMap
							.put("EV_ACHI", request.getParameter("EV_ACHI" + i));
					paramMap
							.put("EV_ATTI", request.getParameter("EV_ATTI" + i));
					paramMap
							.put("EV_ABIL", request.getParameter("EV_ABIL" + i));
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
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@Override
	public Object getEvsInfo(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		param.put("PERSON_ID", request.getParameter("PERSON_ID"));
		param.put("EV_PERIOD", request.getParameter("EV_PERIOD"));
		return empInfoDao.getEvsInfo(param);
	}

	/**
	 * 修改评价信息(Modify assessment information)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int editEvsInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));

			String[] paramData = request.getParameterValues("EPNO");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("EV_PERIOD", paramData[i]);
					paramMap.put("EV_PERIOD", request.getParameter("EV_PERIOD_"
							+ paramData[i]));
					paramMap.put("EV_PERIOD1", request
							.getParameter("EV_PERIOD1_" + paramData[i]));
					paramMap.put("EV_TYPE_ID", request
							.getParameter("EV_TYPE_ID_" + paramData[i]));
					paramMap.put("EV_ACHI", request.getParameter("EV_ACHI_"
							+ paramData[i]));
					paramMap.put("EV_ATTI", request.getParameter("EV_ATTI_"
							+ paramData[i]));
					paramMap.put("EV_ABIL", request.getParameter("EV_ABIL_"
							+ paramData[i]));
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteEvsInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		try {
			String[] paramData = request.getParameterValues("EPNO");
			if (paramData != null) {
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
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getReward(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getReward(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getReward(paramMap);
		}
		return retrunList;
	}

	/**
	 * 惩戒(Punishment)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getPunishment(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getPunishment(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getPunishment(paramMap);
		}
		return retrunList;
	}

	/**
	 * 兼职(Plurality)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getPluralityList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		retrunList = empInfoDao.getPluralityList(paramMap);
		return retrunList;
	}

	/**
	 * 培训(Training)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getTrainingInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		retrunList = empInfoDao.getTrainingInfoList(paramMap);
		return retrunList;
	}

	/**
	 * 添加培训(add Training)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int addTrainingInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getPersonId());
			int count = Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				LinkedHashMap trainingMap = ObjectBindUtil
						.getRequestParamData(request);
				if (request.getParameter("COURSE_NAME" + i) != null) {
					trainingMap.put("COURSE_NAME", request
							.getParameter("COURSE_NAME" + i));// 课程名
					trainingMap.put("MUST_CODE", request
							.getParameter("MUST_CODE" + i));// 选择必选
					trainingMap.put("INSTITUTION_NAME", request
							.getParameter("INSTITUTION_NAME" + i));// 培训机关
					trainingMap.put("START_DATE", request
							.getParameter("START_DATE" + i));// 开始时间
					trainingMap.put("END_DATE", request.getParameter("END_DATE"
							+ i));// 结束时间
					trainingMap.put("TRAINING_RESULT", request
							.getParameter("TRAINING_RESULT" + i));// 培训日期

					trainingMap.put("TRAINING_METHOD", request
							.getParameter("TRAINING_METHOD" + i));
					trainingMap.put("REMARKS", request.getParameter("REMARKS"
							+ i));
					trainingMap.put("TRAINING_DIFFERENTIATE", request
							.getParameter("TRAINING_DIFFERENTIATE" + i));
					trainingMap.put("TRAINING_TIME", request
							.getParameter("TRAINING_TIME" + i));
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteTrainingInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String[] paramData = request.getParameterValues("TN");
			if (paramData != null) {
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
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getFamilyList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("FAM_TYPE_CODE", "1693"); // 等于1693的都是朋友
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		retrunList = empInfoDao.getFamilyList(paramMap);
		return retrunList;
	}

	/**
	 * 添加社会关系(add FamilyInfo)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int addFamilyInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			int count = Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				paramMap.put("FAM_TYPE_CODE", request
						.getParameter("FAM_TYPE_CODE" + i));
				paramMap.put("FAM_NAME", request.getParameter("FAM_NAME" + i));
				paramMap.put("FAM_IDCARD", request.getParameter("FAM_IDCARD"
						+ i));
				paramMap.put("FAM_BORNDATE", request
						.getParameter("FAM_BORNDATE" + i));
				paramMap.put("FAM_ADDRESS", request.getParameter("FAM_ADDRESS"
						+ i));
				paramMap
						.put("FAM_PHONE", request.getParameter("FAM_PHONE" + i));
				paramMap.put("FAM_COMPANY_NAME", request
						.getParameter("FAM_COMPANY_NAME" + i));
				paramMap.put("LIVE_YN", request.getParameter("LIVE_YN" + i));
				paramMap.put("EMERGENCY_CONTACT_YN", request
						.getParameter("EMERGENCY_CONTACT_YN" + i));
				paramMap.put("FAM_PERSON_ID", request
						.getParameter("FAM_PERSON_ID" + i));
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteFamilyInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String[] paramData = request.getParameterValues("FN");
			if (paramData != null) {
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int editFamilyInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			String[] paramData = request.getParameterValues("FN");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("FAMILY_NO", paramData[i]);
					paramMap.put("FAM_TYPE_CODE", request
							.getParameter("FAM_TYPE_CODE_" + paramData[i]));
					paramMap.put("FAM_NAME", request.getParameter("FAM_NAME_"
							+ paramData[i]));
					paramMap.put("FAM_IDCARD", request
							.getParameter("FAM_IDCARD_" + paramData[i]));
					paramMap.put("FAM_BORNDATE", request
							.getParameter("FAM_BORNDATE_" + paramData[i]));
					paramMap.put("FAM_ADDRESS", request
							.getParameter("FAM_ADDRESS_" + paramData[i]));
					paramMap.put("FAM_PHONE", request.getParameter("FAM_PHONE_"
							+ paramData[i]));
					paramMap.put("FAM_COMPANY_NAME", request
							.getParameter("FAM_COMPANY_NAME_" + paramData[i]));
					paramMap.put("LIVE_YN", request.getParameter("LIVE_YN_"
							+ paramData[i]));
					paramMap.put("EMERGENCY_CONTACT_YN", request
							.getParameter("EMERGENCY_CONTACT_YN_"
									+ paramData[i]));
					paramMap.put("FAM_PERSON_ID", request
							.getParameter("FAM_PERSON_ID_" + paramData[i]));
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
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getHomeRelationList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("INTERFACE", "INTERFACE"); // 等于950的都是家人
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		returnList = empInfoDao.getHomeRelationList(paramMap);
		return returnList;
	}

	/**
	 * 添加家人关系(add FamilyInfo)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int addHomeRelationInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			int count = Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				paramMap.put("FAM_TYPE_CODE", request
						.getParameter("FAM_TYPE_CODE" + i));
				paramMap.put("FAM_NAME", request.getParameter("FAM_NAME" + i));
				paramMap.put("FAM_IDCARD", request.getParameter("FAM_IDCARD"
						+ i));
				paramMap.put("FAM_BORNDATE", request
						.getParameter("FAM_BORNDATE" + i));
				paramMap.put("FAM_ADDRESS", request.getParameter("FAM_ADDRESS"
						+ i));
				paramMap
						.put("FAM_PHONE", request.getParameter("FAM_PHONE" + i));
				paramMap.put("FAM_COMPANY_NAME", request
						.getParameter("FAM_COMPANY_NAME" + i));
				paramMap.put("LIVE_YN", request.getParameter("LIVE_YN" + i));
				paramMap.put("EMERGENCY_CONTACT_YN", request
						.getParameter("EMERGENCY_CONTACT_YN" + i));
				paramMap.put("FAM_PERSON_ID", request
						.getParameter("FAM_PERSON_ID" + i));
				paramMap.put("FAM_YINYANGLI", request
						.getParameter("FAM_YINYANGLI" + i));
				this.empInfoDao.addHomeRelationInfo(paramMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 删除家人信息(delete homerelation information)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteHomeRelationInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String[] paramData = request.getParameterValues("FN");
			if (paramData != null) {
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int editHomeRelation(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			String[] paramData = request.getParameterValues("FN");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("FAMILY_NO", paramData[i]);
					paramMap.put("FAM_TYPE_CODE", request
							.getParameter("FAM_TYPE_CODE_" + paramData[i]));
					paramMap.put("FAM_NAME", request.getParameter("FAM_NAME_"
							+ paramData[i]));
					paramMap.put("FAM_IDCARD", request
							.getParameter("FAM_IDCARD_" + paramData[i]));
					paramMap.put("FAM_BORNDATE", request
							.getParameter("FAM_BORNDATE_" + paramData[i]));
					paramMap.put("FAM_ADDRESS", request
							.getParameter("FAM_ADDRESS_" + paramData[i]));
					paramMap.put("FAM_PHONE", request.getParameter("FAM_PHONE_"
							+ paramData[i]));
					paramMap.put("FAM_COMPANY_NAME", request
							.getParameter("FAM_COMPANY_NAME_" + paramData[i]));
					paramMap.put("LIVE_YN", request.getParameter("LIVE_YN_"
							+ paramData[i]));
					paramMap.put("EMERGENCY_CONTACT_YN", request
							.getParameter("EMERGENCY_CONTACT_YN_"
									+ paramData[i]));
					paramMap.put("FAM_PERSON_ID", request
							.getParameter("FAM_PERSON_ID_" + paramData[i]));
					paramMap.put("FAM_YINYANGLI", request
							.getParameter("FAM_YINYANGLI" + paramData[i]));
					this.empInfoDao.editHomeRelation(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改培训信息(Modify trainging information)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int editTrainingInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			String[] paramData = request.getParameterValues("TNO");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("TRAIN_NO", paramData[i]);
					paramMap.put("COURSE_NAME", request
							.getParameter("COURSE_NAME_" + paramData[i]));
					paramMap.put("MUST_CODE", request.getParameter("MUST_CODE_"
							+ paramData[i]));
					paramMap.put("INSTITUTION_NAME", request
							.getParameter("INSTITUTION_NAME_" + paramData[i]));
					paramMap.put("START_DATE", request
							.getParameter("START_DATE_" + paramData[i]));
					paramMap.put("END_DATE", request.getParameter("END_DATE_"
							+ paramData[i]));
					paramMap.put("TRAINING_METHOD", request
							.getParameter("TRAINING_METHOD_" + paramData[i]));
					paramMap.put("REMARKS", request.getParameter("REMARKS_"
							+ paramData[i]));
					paramMap.put("TRAINING_DIFFERENTIATE", request
							.getParameter("TRAINING_DIFFERENTIATE_"
									+ paramData[i]));
					paramMap.put("TRAINING_TIME", request
							.getParameter("TRAINING_TIME_" + paramData[i]));
					paramMap.put("TRAINING_RESULT", request
							.getParameter("TRAINING_RESULT_" + paramData[i]));
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
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getHealthList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		retrunList = empInfoDao.getHealthList(paramMap);
		return retrunList;
	}

	/**
	 * 添加健康信息(add health information)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int addHealthInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		try {
			int count = Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				if (request.getParameter("PHYSICAL_DATE" + i) != null) {
					paramMap.put("PHYSICAL_DATE", request
							.getParameter("PHYSICAL_DATE" + i));
					paramMap.put("PHYSICAL_TYPE_CODE", request
							.getParameter("PHYSICAL_TYPE_CODE" + i));
					paramMap.put("INDUSTRY_DISTINGUISH_CODE", request
							.getParameter("INDUSTRY_DISTINGUISH_CODE" + i));
					paramMap.put("EFFECTIVE_DATE", request
							.getParameter("EFFECTIVE_DATE" + i));
					paramMap.put("CHECK_YN", request.getParameter("CHECK_YN"
							+ i));
					paramMap.put("GENERAL_HEALTH", request
							.getParameter("GENERAL_HEALTH" + i));
					paramMap.put("BLOOD_TYPE_CODE", request
							.getParameter("BLOOD_TYPE_CODE" + i));
					paramMap.put("HEALTH_CERTIFICATE_YN", request
							.getParameter("HEALTH_CERTIFICATE_YN" + i));
					paramMap.put("REMARK", request.getParameter("REMARK" + i));
					paramMap.put("FAM_TYPE_CODE", request
							.getParameter("FAM_TYPE_CODE" + i));
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int editHealthInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			String[] paramData = request.getParameterValues("HNO");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("HEALTH_NO", paramData[i]);
					paramMap.put("PHYSICAL_DATE", request
							.getParameter("PHYSICAL_DATE_" + paramData[i]));
					paramMap
							.put("PHYSICAL_TYPE_CODE", request
									.getParameter("PHYSICAL_TYPE_CODE_"
											+ paramData[i]));
					paramMap.put("INDUSTRY_DISTINGUISH_CODE", request
							.getParameter("INDUSTRY_DISTINGUISH_CODE_"
									+ paramData[i]));
					paramMap.put("EFFECTIVE_DATE", request
							.getParameter("EFFECTIVE_DATE_" + paramData[i]));
					paramMap.put("CHECK_YN", request.getParameter("CHECK_YN_"
							+ paramData[i]));
					paramMap.put("GENERAL_HEALTH", request
							.getParameter("GENERAL_HEALTH_" + paramData[i]));
					paramMap.put("BLOOD_TYPE_CODE", request
							.getParameter("BLOOD_TYPE_CODE_" + paramData[i]));
					paramMap.put("HEALTH_CERTIFICATE_YN", request
							.getParameter("HEALTH_CERTIFICATE_YN_"
									+ paramData[i]));
					paramMap.put("REMARK", request.getParameter("REMARK_"
							+ paramData[i]));
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteHealthInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String[] paramData = request.getParameterValues("HN");
			if (paramData != null) {
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
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getWorkExperienceList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		retrunList = empInfoDao.getWorkExperienceList(paramMap);
		return retrunList;
	}

	/**
	 * 添加工作经验(add work experience information)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int addWorkExperienceInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		try {
			int count = Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				if (request.getParameter("START_DATE" + i) != null) {
					paramMap.put("START_DATE", request
							.getParameter("START_DATE" + i));// 开始时间
					paramMap.put("END_DATE", request.getParameter("END_DATE"
							+ i));// 结束时间
					paramMap.put("CPNY_NAME", request.getParameter("CPNY_NAME"
							+ i));// 工作单位
					paramMap.put("DEPT_NAME", request.getParameter("DEPT_NAME"
							+ i));//
					paramMap.put("DUTY", request.getParameter("DUTY" + i));// 职级
					paramMap.put("POSITION", request.getParameter("POSITION"
							+ i));// 职种
					paramMap
							.put("PAYROLL", request.getParameter("PAYROLL" + i));// 工资待遇
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int editWorkExperienceInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			String[] paramData = request.getParameterValues("WNO");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("WORK_EXPER_NO", paramData[i]);
					paramMap.put("CPNY_NAME", request.getParameter("CPNY_NAME_"
							+ paramData[i]));
					paramMap.put("POSITION", request.getParameter("POSITION_"
							+ paramData[i]));
					paramMap.put("DUTY", request.getParameter("DUTY_"
							+ paramData[i]));
					paramMap.put("DEPT_NAME", request.getParameter("DEPT_NAME_"
							+ paramData[i]));
					paramMap.put("START_DATE", request
							.getParameter("START_DATE_" + paramData[i]));
					paramMap.put("END_DATE", request.getParameter("END_DATE_"
							+ paramData[i]));
					paramMap.put("PAYROLL", request.getParameter("PAYROLL_"
							+ paramData[i]));
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteWorkExpreienceInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("WENO");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("WORK_EXPER_NO", paramData[i]);
					this.empInfoDao.deleteWorkExpreienceInfo(paramMap);
				}
			}
			if (paramMap.get("PERSON_ID") == null) {
				paramMap.put("PERSON_ID", admin.getAdminID());
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
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getQualificationList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		retrunList = empInfoDao.getQualificationList(paramMap);
		return retrunList;
	}

	/**
	 * 外国语(language level)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getLanguageLevelList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		retrunList = empInfoDao.getLanguageLevelList(paramMap);
		return retrunList;
	}

	/**
	 * 添加资格信息(add competence information)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int addCompetenceInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		try {
			int count = Integer.parseInt(request.getParameter("count"));
			// int countL=Integer.parseInt(request.getParameter("countL"));
			for (int i = 0; i < count; i++) {
				if (request.getParameter("QUAL_NAME" + i) != null) {
					paramMap.put("QUAL_NAME", request.getParameter("QUAL_NAME"
							+ i));
					paramMap.put("QUAL_CARD_NO", request
							.getParameter("QUAL_CARD_NO" + i));
					paramMap.put("QUAL_LEVEL", request
							.getParameter("QUAL_LEVEL" + i));
					paramMap.put("QUAL_GRADE", request
							.getParameter("QUAL_GRADE" + i));
					paramMap.put("QUAL_INSTITUTE", request
							.getParameter("QUAL_INSTITUTE" + i));
					paramMap.put("ACQUISITION_MODES", request
							.getParameter("ACQUISITION_MODES" + i));
					paramMap.put("DATE_OBTAINED", request
							.getParameter("DATE_OBTAINED" + i));
					paramMap.put("VALIDITY_DATE", request
							.getParameter("VALIDITY_DATE" + i));
					paramMap.put("QUAL_REMARK", request
							.getParameter("QUAL_REMARK" + i));
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int editCompetenceInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			String[] paramData = request.getParameterValues("QNO");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("QUAL_NO", paramData[i]);
					paramMap.put("QUAL_NAME", request.getParameter("QUAL_NAME_"
							+ paramData[i]));
					paramMap.put("QUAL_CARD_NO", request
							.getParameter("QUAL_CARD_NO_" + paramData[i]));
					paramMap.put("QUAL_LEVEL", request
							.getParameter("QUAL_LEVEL_" + paramData[i]));
					paramMap.put("QUAL_GRADE", request
							.getParameter("QUAL_GRADE_" + paramData[i]));
					paramMap.put("QUAL_INSTITUTE", request
							.getParameter("QUAL_INSTITUTE_" + paramData[i]));
					paramMap.put("ACQUISITION_MODES", request
							.getParameter("ACQUISITION_MODES_" + paramData[i]));
					paramMap.put("DATE_OBTAINED", request
							.getParameter("DATE_OBTAINED_" + paramData[i]));
					paramMap.put("VALIDITY_DATE", request
							.getParameter("VALIDITY_DATE_" + paramData[i]));
					paramMap.put("QUAL_REMARK", request
							.getParameter("QUAL_REMARK_" + paramData[i]));
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteCompetenceInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String[] paramData = request.getParameterValues("QN");
			if (paramData != null) {
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
	 * 
	 * @param parent_code_no
	 * @return retrunList
	 */
	@Override
	public List getCodeList(String parent_code_no, HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PARENT_CODE_NO", parent_code_no);
		retrunList = empInfoDao.getCodeList(paramMap);
		return retrunList;
	}
	
	@Override
	public List getCodeListNO(String parent_code_no, HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("NO", parent_code_no);
		retrunList = empInfoDao.getCodeListNO(paramMap);
		return retrunList;
	}
	@Override
	public List getCodeList1(String parent_code_no, HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PARENT_CODE_NO", parent_code_no);
		retrunList = empInfoDao.getCodeList1(paramMap);
		return retrunList;
	}
	@Override
	public List getCodeListRecruitType(String parent_code_no, HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PARENT_CODE_NO", parent_code_no);
		retrunList = empInfoDao.getCodeListRecruitType(paramMap);
		return retrunList;
	}
	
	@Override
	public List getCodeListEmpStatus(String parent_code_no, HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PARENT_CODE_NO", parent_code_no);
		retrunList = empInfoDao.getCodeListEmpStatus(paramMap);
		return retrunList;
	}

	/**
	 * 标签查询(QueryLabelInformationVolume)
	 * 
	 * @param parent_code_no
	 * @return retrunList
	 */
	@Override
	public List getCodeListForEvs(String parent_code_no,
			HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PARENT_CODE_NO", parent_code_no);
		retrunList = empInfoDao.getCodeListForEvs(paramMap);
		return retrunList;
	}

	/**
	 * 特殊事项(Special matters)
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getAdditionalList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		retrunList = empInfoDao.getAdditionalList(paramMap);
		return retrunList;
	}

	/**
	 * 添加特殊事项(add special matters)
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int addAdditionalInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		try {
			int count = Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				if (request.getParameter("EVENT_DATE" + i) != null) {
					paramMap.put("EVENT_DATE", request
							.getParameter("EVENT_DATE" + i));
					paramMap.put("INFO_TYPE_CODE", request
							.getParameter("INFO_TYPE_CODE" + i));
					paramMap.put("REMARK", request.getParameter("REMARK" + i));
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int editAdditionalInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			// 创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());

			String[] paramData = request.getParameterValues("AN");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("ADDITIONAL_NO", paramData[i]);
					paramMap.put("EVENT_DATE", request
							.getParameter("EVENT_DATE_" + paramData[i]));
					paramMap.put("INFO_TYPE_CODE", request
							.getParameter("INFO_TYPE_CODE_" + paramData[i]));
					paramMap.put("REMARK", request.getParameter("REMARK_"
							+ paramData[i]));
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
	 * 
	 * @param obj
	 * @return int
	 */
	@Override
	public int deleteAdditionalInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		try {

			String[] paramData = request.getParameterValues("AN");

			if (paramData != null) {
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
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getAccountList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getAccountList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getAccountList(paramMap);
		}

		return retrunList;

	}

	/**
	 * 合同信息(contract)
	 * 
	 * @param request
	 * @return retrunList
	 */
	public List getContractList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		retrunList = empInfoDao.getContractList(paramMap);

		return retrunList;

	}

	/**
	 * 档案信息(file)
	 * 
	 * @param request
	 * @return retrunList
	 */
	public List getFileList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		retrunList = empInfoDao.getFileList(paramMap);

		return retrunList;

	}

	/**
	 * 添加档案(add file)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int addFileInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		// System.out.println(request.getParameter("PERSON_ID"));
		try {

			int count = Integer.parseInt(request.getParameter("count"));

			for (int i = 0; i < count; i++) {
				if (request.getParameter("FILE_NO" + i) != null) {
					paramMap
							.put("FILE_NO", request.getParameter("FILE_NO" + i));
					paramMap.put("FILE_TYPE", request.getParameter("FILE_TYPE"
							+ i));
					paramMap.put("FILE_RELATION", request
							.getParameter("FILE_RELATION" + i));
					paramMap.put("FILE_INTO_YN", request
							.getParameter("FILE_INTO_YN" + i));
					paramMap.put("FILE_DATE", request.getParameter("FILE_DATE"
							+ i));
					paramMap.put("FILE_CONTENT", request
							.getParameter("FILE_CONTENT" + i));
					paramMap.put("FILE_AREA", request.getParameter("FILE_AREA"
							+ i));
					paramMap.put("COST_END_DATE", request
							.getParameter("COST_END_DATE" + i));
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
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int editFileInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			// 创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());

			String[] paramData = request.getParameterValues("FEN");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("FILE_EMP_NO", paramData[i]);
					paramMap.put("FILE_NO", request.getParameter("FILE_NO_"
							+ paramData[i]));
					paramMap.put("FILE_TYPE", request.getParameter("FILE_TYPE_"
							+ paramData[i]));
					paramMap.put("FILE_RELATION", request
							.getParameter("FILE_RELATION_" + paramData[i]));
					paramMap.put("FILE_INTO_YN", request
							.getParameter("FILE_INTO_YN_" + paramData[i]));
					paramMap.put("FILE_DATE", request.getParameter("FILE_DATE_"
							+ paramData[i]));
					paramMap.put("FILE_CONTENT", request
							.getParameter("FILE_CONTENT_" + paramData[i]));
					paramMap.put("FILE_AREA", request.getParameter("FILE_AREA_"
							+ paramData[i]));
					paramMap.put("COST_END_DATE", request
							.getParameter("COST_END_DATE_" + paramData[i]));
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
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int deleteFileInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		try {

			String[] paramData = request.getParameterValues("FEN");

			if (paramData != null) {
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
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getGoAbroadList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		retrunList = empInfoDao.getGoAbroadList(paramMap);

		return retrunList;
	}

	/**
	 * 添加出国信息(add goabroad information)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int addGoAbroadInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));

		try {

			int count = Integer.parseInt(request.getParameter("count"));

			for (int i = 0; i < count; i++) {
				if (request.getParameter("COUNTRY" + i) != null) {
					paramMap
							.put("COUNTRY", request.getParameter("COUNTRY" + i));
					paramMap.put("START_DATE", request
							.getParameter("START_DATE" + i));
					paramMap.put("END_DATE", request.getParameter("END_DATE"
							+ i));
					paramMap.put("COST", request.getParameter("COST" + i));
					paramMap
							.put("PURPOSE", request.getParameter("PURPOSE" + i));
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
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int editGoAbroadInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			// 创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());

			String[] paramData = request.getParameterValues("NO");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("NO", paramData[i]);
					paramMap.put("FILE_NO", request.getParameter("FILE_NO_"
							+ paramData[i]));
					paramMap.put("COUNTRY", request.getParameter("COUNTRY_"
							+ paramData[i]));
					paramMap.put("START_DATE", request
							.getParameter("START_DATE_" + paramData[i]));
					paramMap.put("END_DATE", request.getParameter("END_DATE_"
							+ paramData[i]));
					paramMap.put("COST", request.getParameter("COST_"
							+ paramData[i]));
					paramMap.put("PURPOSE", request.getParameter("PURPOSE_"
							+ paramData[i]));
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
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int deleteGoAbroadInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		try {

			String[] paramData = request.getParameterValues("NO");

			if (paramData != null) {
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
	 * 
	 * @param request
	 * @return retrunList
	 */
	@Override
	public List getCredentialList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		retrunList = empInfoDao.getCredentialList(paramMap);

		return retrunList;
	}

	/**
	 * 添加证照信息(add credential)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int addCredentialInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));

		try {

			int count = Integer.parseInt(request.getParameter("count"));

			for (int i = 0; i < count; i++) {
				if (request.getParameter("CREDENTIAL_TYPE" + i) != null) {
					paramMap.put("CREDENTIAL_TYPE", request
							.getParameter("CREDENTIAL_TYPE" + i));
					paramMap.put("CREDENTIAL_NO", request
							.getParameter("CREDENTIAL_NO" + i));
					paramMap.put("CREDENTIAL_SOURCE", request
							.getParameter("CREDENTIAL_SOURCE" + i));
					paramMap.put("CREDENTIAL_BEGIN_DATE", request
							.getParameter("CREDENTIAL_BEGIN_DATE" + i));
					paramMap.put("CREDENTIAL_END_DATE", request
							.getParameter("CREDENTIAL_END_DATE" + i));
					paramMap.put("REMARK", request.getParameter("REMARK" + i));

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
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int editCredentialInfo(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			// 创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());

			String[] paramData = request.getParameterValues("CN");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("CRED_NO", paramData[i]);
					paramMap.put("FILE_NO", request.getParameter("FILE_NO_"
							+ paramData[i]));
					paramMap.put("CREDENTIAL_TYPE", request
							.getParameter("CREDENTIAL_TYPE_" + paramData[i]));
					paramMap.put("CREDENTIAL_NO", request
							.getParameter("CREDENTIAL_NO_" + paramData[i]));
					paramMap.put("CREDENTIAL_SOURCE", request
							.getParameter("CREDENTIAL_SOURCE_" + paramData[i]));
					paramMap.put("CREDENTIAL_BEGIN_DATE", request
							.getParameter("CREDENTIAL_BEGIN_DATE_"
									+ paramData[i]));
					paramMap.put("CREDENTIAL_END_DATE",
							request.getParameter("CREDENTIAL_END_DATE_"
									+ paramData[i]));
					paramMap.put("REMARK", request.getParameter("REMARK_"
							+ paramData[i]));
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
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int deleteCredentialInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		try {

			String[] paramData = request.getParameterValues("CN");

			if (paramData != null) {
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
	 * 
	 * @param request
	 * @return List
	 */
	@Override
	public List getEmpIdList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("userNo", admin.getUserNo());

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getEmpIdList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getEmpIdList(paramMap);
		}

		return retrunList;

	}

	/**
	 * 获取员工信息个数(For the number of staff information)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEmpIdListCnt(HttpServletRequest request) {
		int retrunInt = 0;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		retrunInt = empInfoDao.getEmpIdListCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 根据EMPID查询出人员信息条数(EMPID inquires according to the number of personnel
	 * article information)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getPersonCnt(HttpServletRequest request) {
		int retrunInt = 0;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap
				.put("EMPID", request.getParameter("viewPersonalInfoHeadEmpId"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunInt = empInfoDao.getPersonCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@Override
	public List getPidEidList(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("PERSON_ID", admin.getPersonId());

		paramMap.put("CPNY_ID", admin.getCpnyId());

		paramMap.put("specialParam", admin.getSpecialParam());

		paramMap.put("deptNo", admin.getDeptNo());

		paramMap.put("userNo", admin.getUserNo());

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getPidEidList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getPidEidList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@Override
	public List getPidEidListXiao(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getPidEidListXiao(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getPidEidListXiao(paramMap);
		}

		return retrunList;
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@Override
	public List getPidEidList2(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("PERSON_ID", admin.getPersonId());

		paramMap.put("CPNY_ID", admin.getCpnyId());

		paramMap.put("specialParam", admin.getSpecialParam());

		paramMap.put("deptNo", admin.getDeptNo());

		paramMap.put("userNo", admin.getUserNo());

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getPidEidList2(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getPidEidList2(paramMap);
		}

		return retrunList;
	}

	/**
	 * 查询old(薪资)职级,号俸(Inquires the old (salary) rank, no pay)
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public List getOldPostGradeList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		retrunList = empInfoDao.getOldPostGradeList(paramMap, UiUtil
				.getPageNum(request), UiUtil.getNumPerPage(request));

		return retrunList;
	}

	/**
	 * 查询代理职级(Inquires the agency rank)
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public List getPostGradeList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		retrunList = empInfoDao.getPostGradeList(paramMap, UiUtil
				.getPageNum(request), UiUtil.getNumPerPage(request));

		return retrunList;
	}

	@Override
	public int editPhotoPath(HttpServletRequest request, String path) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		paramMap.put("PHOTO_PATH", path + request.getParameter("CPNY_ID") + "/"
				+ request.getParameter("CPNY_ID")
				+ request.getParameter("EMPID") + ".jpg");

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
	public List getOrderParmList(HttpServletRequest request, String sortNameNo) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("SORT_TYPE_NO", sortNameNo);
		retrunList = empInfoDao.getOrderParmList(paramMap);

		return retrunList;
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
	 * 基本信息的子菜单查询
	 * 
	 * @param
	 * @return
	 */
	@Override
	public List getMenuThirdListList(String menu_code,
			HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USERNO", admin.getUserNo());
		paramMap.put("interLanguage", Messages.getLanguage(request));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		retrunList = empInfoDao.getMenuThirdListList(paramMap);

		return retrunList;
	}

	/**
	 * 根据法人获取公司人员信息 导出模板使用
	 */
	@Override
	public List getEmpListToModelExcel(HttpServletRequest request) {
		Map paramMap = new HashMap();
		paramMap.put("STATUS_CODE", "1375");// 离职
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getAdminID() != null ? admin
				.getAdminID().toString() : "");
		paramMap.put("language", admin.getLanguage());
		paramMap.put("DEPTNO",
				request.getParameter("seach_DEPTNO") != null ? request
						.getParameter("seach_DEPTNO").toString() : (request
						.getParameter("DEPTNO") != null ? request
						.getParameter("DEPTNO") : ""));
		return empInfoDao.getEmpListToModelExcel(paramMap);
	}

	/**
	 * 查询ess tab菜单
	 */
	@Override
	public List getTabMenuListList(String menu_code, HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USERNO", admin.getUserNo());
		paramMap.put("interLanguage", Messages.getLanguage(request));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		retrunList = empInfoDao.getTabMenuListList(paramMap);

		return retrunList;
	}

	// 残疾信息
	public List getDisabilityinfoList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getDisabilityinfoList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getDisabilityinfoList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 添加残疾信息
	 */
	@Override
	public int addDisabledInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		try {
			int count = Integer.parseInt(request.getParameter("count"));

			for (int i = 0; i < count; i++) {
				if (request.getParameter("DISABILITY_TYPE_NAME" + i) != null) {

					paramMap.put("DISABILITY_TYPE_NAME", request
							.getParameter("DISABILITY_TYPE_NAME" + i));
					paramMap
							.put("ADDDATE", request.getParameter("ADDDATE" + i));
					String DISABILITY_VALIDITY = request.getParameter(
							"DISABILITY_VALIDITY" + i).toString();
					paramMap.put("DISABILITY_VALIDITY", DISABILITY_VALIDITY);
					int j = 0;
					switch (Integer.parseInt(DISABILITY_VALIDITY)) {
					case 123463:
						j = 1;
						break;
					case 123464:
						j = 2;
						break;
					case 123465:
						j = 3;
						break;
					case 123466:
						j = 4;
						break;
					case 123467:
						j = 5;
						break;
					case 123468:
						j = 6;
						break;
					case 123469:
						j = 7;
						break;
					case 123470:
						j = 8;
						break;
					case 123471:
						j = 9;
						break;
					case 123472:
						j = 10;
						break;
					}

					paramMap.put("QUITDATE", j * 12);

					paramMap.put("REMARK", request.getParameter("REMARK" + i));

					this.empInfoDao.addDisabledInfo(paramMap);
					/*
					 * int disabledCnt=this.empInfoDao.getdisabledCnt(paramMap);
					 * if(disabledCnt>0){ paramMap.put("DISABLED_OR_NOT","Y");
					 * }else{ paramMap.put("DISABLED_OR_NOT","N"); }
					 * this.empInfoDao
					 * .updateEmployeeDisabled(paramMap);//修改基本中的残疾状态
					 */
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
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		try {

			String[] paramData = request.getParameterValues("CJ");

			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("T_ID", paramData[i]);
					this.empInfoDao.deleteDisabledInfo(paramMap);
				}
			}

			// if(request.getParameter("PERSON_ID")==null){
			// paramMap.put("PERSON_ID",admin.getAdminID() );
			// }else{
			// paramMap.put("PERSON_ID",request.getParameter("PERSON_ID") );
			// }
			// int disabledCnt=this.empInfoDao.getdisabledCnt(paramMap);
			// if(disabledCnt>0){
			// paramMap.put("DISABLED_OR_NOT","Y");
			// }else{
			// paramMap.put("DISABLED_OR_NOT","N");
			// }
			// this.empInfoDao.updateEmployeeDisabled(paramMap);//修改基本中的残疾状态

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
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			// 创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());

			String[] paramData = request.getParameterValues("HNO");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("T_ID", paramData[i]);
					paramMap.put("DISABILITY_TYPE_NAME", request
							.getParameter("DISABILITY_TYPE_NAME_"
									+ paramData[i]));
					paramMap.put("ADDDATE", request.getParameter("ADDDATE_"
							+ paramData[i]));
					String DISABILITY_VALIDITY = request.getParameter(
							"DISABILITY_VALIDITY_" + paramData[i]).toString();
					paramMap.put("DISABILITY_VALIDITY", DISABILITY_VALIDITY);
					int j = 0;
					switch (Integer.parseInt(DISABILITY_VALIDITY)) {
					case 123463:
						j = 1;
						break;
					case 123464:
						j = 2;
						break;
					case 123465:
						j = 3;
						break;
					case 123466:
						j = 4;
						break;
					case 123467:
						j = 5;
						break;
					case 123468:
						j = 6;
						break;
					case 123469:
						j = 7;
						break;
					case 123470:
						j = 8;
						break;
					case 123471:
						j = 9;
						break;
					case 123472:
						j = 10;
						break;
					}

					paramMap.put("QUITDATE", j * 12);
					paramMap.put("REMARK", request.getParameter("REMARK_"
							+ paramData[i]));

					this.empInfoDao.editDisabledInfo(paramMap);
				}
			}
			// int disabledCnt=this.empInfoDao.getdisabledCnt(paramMap);
			// if(disabledCnt>0){
			// paramMap.put("DISABLED_OR_NOT","Y");
			// }else{
			// paramMap.put("DISABLED_OR_NOT","N");
			// }
			// this.empInfoDao.updateEmployeeDisabled(paramMap);//修改基本中的残疾状态
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
						.getParameter("PHYSICAL_TYPE_CODE" + i));
				paramMap.put("TRADEUNION_ADDDATE", request
						.getParameter("TRADEUNION_ADDDATE" + i));
				paramMap.put("TRADEUNION_QUITDATE", request
						.getParameter("TRADEUNION_QUITDATE" + i));
				paramMap.put("TRADEUNION_REMARK", request
						.getParameter("TRADEUNION_REMARK" + i));

				paramMap.put("PAY_FLAG", request.getParameter("PAY_FLAG" + i));
				paramMap.put("PAY_TYPE", request.getParameter("PAY_TYPE" + i));
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

			// int count = Integer.parseInt(request.getParameter("count"));
			int countL = Integer.parseInt(request.getParameter("countL"));

			for (int j = 0; j < countL; j++) {

				// paramMap.put("LANGUAGE_TYPE_CODE", request
				// .getParameter("LANGUAGE_TYPE_CODE" + j));
				paramMap.put("EXAM_NAME_CODE", request
						.getParameter("EXAM_NAME_CODE" + j));
				// paramMap.put("QUALIFICATION_NAME", request
				// .getParameter("QUALIFICATION_NAME" + j));
				paramMap.put("LANGUAGE_LEVEL_CODE", request
						.getParameter("LANGUAGE_LEVEL_CODE" + j));
				paramMap.put("MARK", request.getParameter("MARK" + j));
				paramMap.put("KAOSHIDATE", request.getParameter("KAOSHIDATE"
						+ j));
				paramMap.put("ALLWANCE", request.getParameter("ALLWANCE" + j));
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
					paramMap.put("ALLWANCE", request.getParameter("ALLWANCE_"
							+ paramDataL[i]));
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
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			// 创建人
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			int biaoji = 0;
			String[] paramData = request.getParameterValues("HNO");
			if (paramData != null) {
				for (int i = 0; i < paramData.length; i++) {
					paramMap.put("EDUC_NO", paramData[i]);// id
					paramMap.put("INSTITUTION_NAME", request
							.getParameter("INSTITUTION_NAME_" + paramData[i]));// 学校名
					paramMap.put("SUBJECT_CLASSIFY", request
							.getParameter("SUBJECT_CLASSIFY_" + paramData[i]));// 专业分类
					paramMap.put("SUBJECT", request.getParameter("SUBJECT_"
							+ paramData[i]));// 专业
					paramMap.put("SUBJECT_CLASSIFY_TWO", request
							.getParameter("SUBJECT_CLASSIFY_TWO_"
									+ paramData[i]));// 第二专业分类
					paramMap.put("SUBJECT_SECOND", request
							.getParameter("SUBJECT_SECOND_" + paramData[i]));// 第二专业
					paramMap.put("START_DATE", request
							.getParameter("START_YEAR_" + paramData[i])
							+ "-"
							+ request.getParameter("START_MONTH_"
									+ paramData[i]));// 开始时间
					paramMap
							.put("END_DATE", request.getParameter("END_YEAR_"
									+ paramData[i])
									+ "-"
									+ request.getParameter("END_MONTH_"
											+ paramData[i]));// 结束时间
					paramMap.put("DEGREE_CODE", request
							.getParameter("DEGREE_CODE_" + paramData[i]));// 学历
					// paramMap.put("PARTICULAR_DEGREE",
					// request.getParameter("PARTICULAR_DEGREE_"+paramData[i]));//详细学历
					// paramMap.put("SCHOOL_ADDRESS",
					// request.getParameter("SCHOOL_ADDRESS_"+paramData[i]));//所在地
					paramMap.put("SITE_PROVINCE", request
							.getParameter("SITE_PROVINCE_" + paramData[i]));// 所在地省CODE
					paramMap.put("SITE_CITY", request.getParameter("SITE_CITY_"
							+ paramData[i]));// 所在地市CODE
					paramMap.put("FINAL_DEGREE_WHETHER", request
							.getParameter("FINAL_DEGREE_WHETHER_"
									+ paramData[i]));// 是否最终学历
					paramMap.put("REMARKS", request.getParameter("REMARKS_"
							+ paramData[i]));// 所在地
					this.empInfoDao.editEducation(paramMap);

					if (request.getParameter(
							"FINAL_DEGREE_WHETHER_" + paramData[i]).toString()
							.equals("Y")) {
						biaoji = 1;
						this.empInfoDao.updataEduaction(paramMap);// 修改员工个人信息表最终学历信息
					}
				}
				if (biaoji == 0) {// 当修改的信息中没有 最终学历信息时
					if (this.empInfoDao.getEduactionY(paramMap) == 0) {// 判断用户是否有最终学历
																		// 没有将基本表中的信息设置为空
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
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());

		if (request.getParameter("status_code_emp") != null) {
			if (request.getParameter("status_code_emp").equals("0")) {
				paramMap.put("STATUS", null);
			} else {
				paramMap.put("STATUS", "abc");
			}
		}
		String CPNYSTATUS = request.getParameter("CPNYSTATUS");

		if (CPNYSTATUS == null) {
			paramMap.put("CPNYSTATUS", null);
		} else if (request.getParameter("seach_DEPTNO") == null
				|| request.getParameter("seach_DEPTNO").equals("")) {
			paramMap.put("CPNYSTATUS", null);
		} else {
			paramMap.put("CPNYSTATUS", request.getParameter("seach_DEPTNO"));
		}
		if (paramMap.get("CPNYSTATUS") != null) {
			paramMap.put("DEPTNO", null);
		} else {
			paramMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getPersonalInfoForInformation(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getPersonalInfoForInformation(paramMap);
		}
		return retrunList;
	}

	public Object getPersonalInfoForInformationCount(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		if (request.getParameter("status_code_emp") != null) {
			if (request.getParameter("status_code_emp").equals("0")) {
				paramMap.put("STATUS", null);
			} else {
				paramMap.put("STATUS", "abc");
			}
		}
		String CPNYSTATUS = request.getParameter("CPNYSTATUS");

		if (CPNYSTATUS == null) {
			paramMap.put("CPNYSTATUS", null);
		} else if (request.getParameter("seach_DEPTNO") == null
				|| request.getParameter("seach_DEPTNO").equals("")) {
			paramMap.put("CPNYSTATUS", null);
		} else {
			paramMap.put("CPNYSTATUS", request.getParameter("seach_DEPTNO"));
		}
		if (paramMap.get("CPNYSTATUS") != null) {
			paramMap.put("DEPTNO", null);
		} else {
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
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		return empInfoDao.getFinalNum(paramMap);
	}

	/**
	 * 通过人力关联表查询出关联的下拉列表 hr_human_relevance
	 */
	@Override
	public List getRelevance(HttpServletRequest request) {
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CODE_ONE", request.getParameter("CODE_ONE"));
		paramMap.put("CODE_TWO", request.getParameter("CODE_TWO"));
		paramMap.put("CODE_THREE", request.getParameter("CODE_THREE"));

		retrunList = empInfoDao.getRelevance(paramMap);

		return retrunList;
	}

	@Override
	public int addTestInfo(HttpServletRequest request) throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("ID", request.getParameter("addTestZhutest0000"));
		paramMap.put("CON", request.getParameter("addTestCon_test0000"));
		paramMap.put("NUM", request.getParameter("addTestNum_test0000"));
		empInfoDao.addTestInfo(paramMap);
		return 1;
	}

	@Override
	public String getJoinFlagByPersonId(String personId) throws SQLException {
		return empInfoDao.getJoinFlagByPersonId(personId);
	}

	@Override
	public int addBadArchivesInfo(HttpServletRequest request)
			throws SQLException {// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		try {
			int count = Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				paramMap.put("PERSON_ID", request
						.getParameter("dwz.person.personId"));
				paramMap.put("PERSON_ID_FILE", admin.getPersonId());
				if (paramMap.get("PERSON_ID") == null) {
					return 0;
				}
				String FILE_URL = StringUtil.checkNull(paramMap.get("fileUrl"));
				String FILE_NAME = StringUtil.checkNull(paramMap
						.get("fileName"));
				paramMap.put("HAPPEN_DATE", request.getParameter("HAPPEN_DATE"
						+ i));
				paramMap.put("ARCHIVES_TYPE", request
						.getParameter("ARCHIVES_TYPE" + i));
				paramMap.put("DETAIL_DESCRIPT", request
						.getParameter("DETAIL_DESCRIPT" + i));
				paramMap.put("FILE_NAME", FILE_NAME);
				paramMap.put("FILE_URL", FILE_URL);
				paramMap.put("REMARK", request.getParameter("REMARK" + i));
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
			/*
			 * String[] paramDataL = request.getParameterValues("BAID"); if
			 * (paramDataL != null) { for (int i = 0; i < paramDataL.length;
			 * i++) { paramMap.put("ID", paramDataL[i]);
			 * this.empInfoDao.deleteBadArchivesInfo(paramMap); } }
			 */
			paramMap.put("ID", paramMap.get("ARCH_ID"));
			paramMap.put("APPLY_NO", paramMap.get("ARCH_ID"));
			paramMap.put("APPLY_TYPE", "0");
			this.empInfoDao.deleteBadArchivesInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int editBadArchivesInfo(HttpServletRequest request)
			throws SQLException {
		try {
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getPersonId());
			/*
			 * String[] paramDataL = request.getParameterValues("BAID"); if
			 * (paramDataL != null) { for (int i = 0; i < paramDataL.length;
			 * i++) { paramMap.put("ID", paramDataL[i]);
			 * paramMap.put("HAPPEN_DATE", request.getParameter("HAPPEN_DATE_" +
			 * paramDataL[i])); paramMap.put("ARCHIVES_TYPE",
			 * request.getParameter("ARCHIVES_TYPE_" + paramDataL[i]));
			 * paramMap.put("DETAIL_DESCRIPT",
			 * request.getParameter("DETAIL_DESCRIPT_" + paramDataL[i]));
			 * paramMap.put("FILE_URL", request.getParameter("FILE_URL_"+
			 * paramDataL[i])); paramMap.put("REMARK",
			 * request.getParameter("REMARK_"+ paramDataL[i]));
			 * this.empInfoDao.editBadArchivesInfo(paramMap); } }
			 */
			int count = Integer.parseInt(request.getParameter("count"));
			for (int i = 0; i < count; i++) {
				paramMap.put("PERSON_ID_FILE", admin.getPersonId());
				String FILE_URL = StringUtil.checkNull(paramMap.get("fileUrl"));
				String FILE_NAME = StringUtil.checkNull(paramMap
						.get("fileName"));
				paramMap.put("REMARK", request.getParameter("REMARK" + i));
				paramMap.put("ID", request.getParameter("ID"));
				paramMap.put("HAPPEN_DATE", request.getParameter("HAPPEN_DATE"
						+ i));
				paramMap.put("ARCHIVES_TYPE", request
						.getParameter("ARCHIVES_TYPE" + i));
				paramMap.put("DETAIL_DESCRIPT", request
						.getParameter("DETAIL_DESCRIPT" + i));
				paramMap.put("FILE_NAME", FILE_NAME);
				paramMap.put("FILE_URL", FILE_URL);
				paramMap.put("REMARK", request.getParameter("REMARK" + i));
				this.empInfoDao.editBadArchivesInfo(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List getBadArchivesList(HttpServletRequest request)
			throws SQLException {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getBadArchivesList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getBadArchivesList(paramMap);
		}
		if (retrunList != null && retrunList.size() > 0) {
			for (int i = 0; i < retrunList.size(); i++) {
				LinkedHashMap returnMap = (LinkedHashMap) retrunList.get(i);
				returnMap.put("APPLY_NO", returnMap.get("ID"));
				returnMap.put("APPLY_TYPE", "0");
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		return retrunList;
	}

	@Override
	public int getBadArchivesListCnt(HttpServletRequest request) {
		int count = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		count = empInfoDao.getBadArchivesListCnt(paramMap);
		return count;
	}

	@Override
	public List getEmpInfoList(HttpServletRequest request) throws SQLException {

		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", admin.getLanguage());
		paramMap.put("CPNY_ID",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		paramMap.put("PERSON_ID", admin.getAdminID());
		String TANCHUEMPOFFICE = request.getParameter("TANCHUEMPOFFICE") == null ? "15119"
				: request.getParameter("TANCHUEMPOFFICE");
		paramMap.put("TANCHUEMPOFFICE", TANCHUEMPOFFICE);
		retrunList = empInfoDao.getEmpInfoList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		return retrunList;
	}
	
	@Override
	public List getEmpInfoSHList(HttpServletRequest request) throws SQLException {

		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", admin.getLanguage());
		paramMap.put("CPNY_ID",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		paramMap.put("PERSON_ID", admin.getAdminID());
		String TANCHUEMPOFFICE = request.getParameter("TANCHUEMPOFFICE") == null ? "15119"
				: request.getParameter("TANCHUEMPOFFICE");
		paramMap.put("TANCHUEMPOFFICE", TANCHUEMPOFFICE);
		retrunList = empInfoDao.getEmpInfoSHList(paramMap, UiUtil
				.getPageNum(request), UiUtil.getNumPerPage(request));
		return retrunList;
	}
	
	@Override
	public List getEmpInfoListAr(HttpServletRequest request) throws SQLException {
		
		List retrunList = new ArrayList();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", admin.getLanguage());
		paramMap.put("CPNY_ID",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		paramMap.put("PERSON_ID", admin.getAdminID());
		String TANCHUEMPOFFICE = request.getParameter("TANCHUEMPOFFICE") == null ? "15119"
				: request.getParameter("TANCHUEMPOFFICE");
		paramMap.put("TANCHUEMPOFFICE", TANCHUEMPOFFICE);
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		retrunList = empInfoDao.getEmpInfoListAr(paramMap, UiUtil
				.getPageNum(request), UiUtil.getNumPerPage(request));
		return retrunList;
	}

	@Override
	public int getEmpInfoListCnt(HttpServletRequest request)
			throws SQLException {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		paramMap.put("PERSON_ID", admin.getAdminID());
		// paramMap.put("specialParam",admin.getSpecialParam());//这个参数判断用户是普通用户还是特殊用户
		// paramMap.put("deptNo",admin.getDeptNo());//判断部门权限用
		// paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		// 判断是否有超级管理员权限
		/*
		 * int authority = authorityUtil.isSuperUser(admin.getPersonId());
		 * if(authority == 1){
		 * 
		 * }
		 */
		String TANCHUEMPOFFICE = request.getParameter("TANCHUEMPOFFICE") == null ? "15119"
				: request.getParameter("TANCHUEMPOFFICE");
		paramMap.put("TANCHUEMPOFFICE", TANCHUEMPOFFICE);
		paramMap.put("authority", "SuperUser");// 超级用户权限
		return empInfoDao.getEmpInfoListCnt(paramMap);
	}
	@Override
	public int getEmpInfoListArCnt(HttpServletRequest request)
	throws SQLException {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		paramMap.put("PERSON_ID", admin.getAdminID());
		
		String TANCHUEMPOFFICE = request.getParameter("TANCHUEMPOFFICE") == null ? "15119"
				: request.getParameter("TANCHUEMPOFFICE");
		paramMap.put("TANCHUEMPOFFICE", TANCHUEMPOFFICE);
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
	//	paramMap.put("authority", "SuperUser");// 超级用户权限
		return empInfoDao.getEmpInfoListArCnt(paramMap);
	}

	/**
	 * 下载派遣津贴标准的模版
	 */
	public String getPaiQianDiJinTieBiaoZhunMoBanModleInfo(
			HttpServletRequest request, List aliasNameList, List list,
			List mapList, List mapNameList) throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String faRen = admin.getCpnyId();
		String codeSql = "SELECT distinct  NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		String faRenSql = " SELECT  g.cpny_id CONTENT  from hr_company g  ";
		String zhiZeSql1 = "SELECT   SY.CONTENT CONTENT FROM HR_POSITION    T, SY_GLOBAL_NAME SY  WHERE T.POSITION_NO = SY.NO(+)  AND SY.LANGUAGE(+) = 'zh' AND T.ACTIVITY = 1 ";
		String zhiZeSql = "SELECT DISTINCT POSITION_NO CONTENT FROM HR_EMPLOYEE HR WHERE HR.CPNY_ID = '"
				+ faRen + "' AND POSITION_NO IS NOT NULL";
		String codeDqmcSql = " select distinct  m.region_nm CONTENT from INC_CITY_TO_CITYLEVEL_MAPP_DIS m ";
		String name = "";
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.zhiZe",
				request));// 职责
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.chengShiDengJi", request));// 城市等级
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.diQuMingCheng", request));// 地区名称
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.shuZhi",
				request));// 数值
		// aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.beiZhu",
		// request));// 备注

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

		// 这些是sheet名字
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
	public String getPaiQianDiGuanLiMoBanModleInfo(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String codeSql = "SELECT NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		String faRenSql = "   SELECT g.cpny_id CONTENT  from hr_company g     ";
		String codeSfSql = " select distinct m.state_nm CONTENT from INC_CITY_TO_CITYLEVEL_MAPP_DIS m ";
		String codeCsmcSql = " select distinct m.city_nm CONTENT, state_nm PARENT_CODE from INC_CITY_TO_CITYLEVEL_MAPP_DIS m group by state_nm, city_nm order by state_nm, city_nm ";
		String codeDqmcSql = " select distinct m.region_nm CONTENT,m.city_nm PARENT_CODE from INC_CITY_TO_CITYLEVEL_MAPP_DIS m group by m.city_nm,m.region_nm order by m.city_nm,m.region_nm ";
		String name = "";
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.chengShiDengJi", request));// 城市等级
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.shengFen",
				request));// 省份
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.chengShiMingCheng", request));// 城市名称
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.diQuMingCheng", request));// 地区名称

		String cpnyId = admin.getCpnyId() != null ? admin.getCpnyId()
				.toString() : "TSTO";
		String sqlDetailInfo = " select '" + cpnyId
				+ "', 'P1', '广东省', '广州市', '珠海区'  from dual ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}
		// 这些是sheet名字
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
	public String getPaiQianDiImportInfo(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String codeSql = "SELECT NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		String faRenSql = "   SELECT g.cpny_id CONTENT  from hr_company g     ";
		String codeSfSql = " select distinct m.state_nm CONTENT from INC_CITY_TO_CITYLEVEL_MAPP m ";
		String codeCsmcSql = " select distinct m.city_nm CONTENT, state_nm PARENT_CODE from INC_CITY_TO_CITYLEVEL_MAPP m group by state_nm, city_nm order by state_nm, city_nm ";
		String codeDqmcSql = " select distinct m.region_nm CONTENT,m.city_nm PARENT_CODE from INC_CITY_TO_CITYLEVEL_MAPP m group by m.city_nm,m.region_nm order by m.city_nm,m.region_nm ";
		String name = "";
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.chengShiDengJi", request));// 城市等级
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.shengFen",
				request));// 省份
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.chengShiMingCheng", request));// 城市名称
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.diQuMingCheng", request));// 地区名称
		aliasNameList.add("正/异常");// 正/异常
		aliasNameList.add("错误提示");// 错误提示

		String personId = admin.getPersonId() != null ? admin.getPersonId()
				.toString() : "";
		String sqlDetailInfo = "SELECT TT.PQD_FAREN, "
				+ "         TT.PQD_CHENGSHIDENGJI, "
				+ "         TT.PQD_SHENGFEN, "
				+ "         TT.PQD_CHENGSHIMINGCHENG, "
				+ "         TT.PQD_DIQUMINGCHENG, "
				+ "         DECODE(TT.PQD_DAORU_RESULT,'E','异常','正常') CHECK_FLAG, "
				+ "         NVL(TT.PQD_001,'无') CHECK_ERROR "
				+ "     FROM SY_DISPATCH_TEMP TT "
				+ "    WHERE TT.CREATED_BY = '" + personId + "' "
				+ " ORDER BY TT.PQD_NO ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}
		// 这些是sheet名字
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
	public String exportZuiDiGongZiBiaoZhunCuXiaoYuanMoBanModle(
			HttpServletRequest request, List aliasNameList, List list,
			List mapList, List mapNameList) throws SQLException {

		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		String quFenSql = " select '应发' CONTENt from dual union  select '实得' CONTENt from dual  ";
		String nianDuSql = " select '2014' CONTENt from dual union  select '2015' CONTENt from dual  union select '2016' CONTENt from dual ";
		// String cityCdSql =
		// " select '500015' CONTENt from dual union  select '500016' CONTENt from dual  union select '500017' CONTENt from dual ";
		String name = "";
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.niandu",
				request));// 年度
		aliasNameList.add("省份");// CITY_CD
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.chengShiMingCheng", request));// 城市名称
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuidigongzi", request));// 最低工资
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.qufen",
				request));// 区分

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

		// 这些是sheet名字
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
	public String exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanMoBanModle(
			HttpServletRequest request, List aliasNameList, List list,
			List mapList, List mapNameList) throws SQLException {

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

		// 这些是sheet名字
		mapNameList.add("法人");
		mapNameList.add("福利地区");
		mapNameList.add("区分");

		mapList.add(faRenSql);
		mapList.add(codeSql + "216736");
		mapList.add(quFenSql);

		name = "zuiDiGongZiBiaoZhunFeiCuXiaoYuanModle";
		return name;
	}

	public String getTemplateInfo(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		String cpnySql = "SELECT T.CP_NAME CONTENT FROM IS_COMPANY T WHERE T.CPNY_ID = ";
		String TEMPLATE_TYPE = request.getParameter("TEMPLATE_TYPE");
		String EMP_TYPE_CODE = request.getParameter("EMP_TYPE_CODE");
		String name = "";

		if ("215977".equals(TEMPLATE_TYPE)) {// 人员基本信息
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
			/* aliasNameList.add("人员类型(CHR)"); */

			aliasNameList.add("工作类型(CHR)");
			/*
			 * aliasNameList.add("人员类型生效日期");//2111111111111
			 */aliasNameList.add("福利地区(保险)");// 2222222222222
			aliasNameList.add("工作地区");
			aliasNameList.add("劳动手册编号");

			aliasNameList.add("社外工龄 ");
			aliasNameList.add("福利地区(公积金)");// 266666666666
			aliasNameList.add("保险公司");
			aliasNameList.add("保险类型 ");
			aliasNameList.add("年假基准");// 299999999999

			aliasNameList.add("产品");
			aliasNameList.add("促销员所属");
			aliasNameList.add("星级级别");
			aliasNameList.add("是否兼卖");
			aliasNameList.add("是否共建促销员");

			aliasNameList.add("评价类型");// 35555555555
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
			/* map.put("CELL19", "管理职"); */

			map.put("CELL19", "不定时工作制");
			/*
			 * map.put("CELL21", "2014-06-16");//2111111111
			 */map.put("CELL20", "北京");// 2222222222
			map.put("CELL21", "F2级地区");
			map.put("CELL22", "001");

			map.put("CELL23", "22");
			map.put("CELL24", "兰州");// 2666666
			map.put("CELL25", "");
			map.put("CELL26", "上海城镇");
			map.put("CELL27", "2014-08-16");// 29999999

			map.put("CELL28", "斯黛乐");
			map.put("CELL29", "天音");
			map.put("CELL30", "5");
			map.put("CELL31", "Y");
			map.put("CELL32", "Y");
			map.put("CELL33", "Y");// 36666666
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
			mapList.add(cpnySql + "'" + admin.getCpnyId() + "'");
			mapList.add(codeSql + "211424");
			mapList.add(codeSql + "211837");
			mapList.add(codeSql + "215954");
			mapList.add(codeSql + "123224");
			mapList.add(codeSql + "123224");

			name = "basicEmpInfo";
		} else if ("215978".equals(TEMPLATE_TYPE)) {// 外国语
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
		} else if ("215979".equals(TEMPLATE_TYPE)) {// 资格证
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
		} else if ("215980".equals(TEMPLATE_TYPE)) {// 工作经历
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
		} else if ("215981".equals(TEMPLATE_TYPE)) {// 培训
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
		} else if ("215982".equals(TEMPLATE_TYPE)) {// 评价
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
		} else if ("215983".equals(TEMPLATE_TYPE)) {// 工会
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
		} else if ("215984".equals(TEMPLATE_TYPE)) {// 残疾证
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
		} else if ("215985".equals(TEMPLATE_TYPE)) {// 紧急联系人
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
		} else if ("215986".equals(TEMPLATE_TYPE)) {// 黑色档案
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
		} else if ("216002".equals(TEMPLATE_TYPE)) {// 辅助信息
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
		} else if ("278650".equals(TEMPLATE_TYPE)) {// 兼卖信息
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
	public List getAssistList(HttpServletRequest request) throws SQLException {

		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getAssistList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getAssistList(paramMap);
		}

		return retrunList;
	}

	@Override
	public List getPerConversionList(HttpServletRequest request)
			throws SQLException {

		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam", admin.getSpecialParam());// 这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo", admin.getDeptNo());// 判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getPerConversionList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getPerConversionList(paramMap);
		}

		return retrunList;
	}

	@Override
	public int getPerConversionListCnt(HttpServletRequest request)
			throws SQLException {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getAdminID());
		paramMap.put("specialParam", admin.getSpecialParam());// 这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo", admin.getDeptNo());// 判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用

		return empInfoDao.getPerConversionListCnt(paramMap);
	}

	@Override
	public int addAssistInfo(HttpServletRequest request) throws SQLException {// 页面提交数据
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
				paramMap.put("TITLE", request.getParameter("TITLE" + i));
				paramMap.put("CONTENT", request.getParameter("CONTENT" + i));

				this.empInfoDao.addAssistInfo(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	@Override
	public int deleteAssistInfo(HttpServletRequest request) throws SQLException {// 页面提交数据
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
	public int editAssistInfo(HttpServletRequest request) throws SQLException {
		try {
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
					paramMap.put("TITLE", request.getParameter("TITLE_"
							+ paramDataL[i]));
					paramMap.put("CONTENT", request.getParameter("CONTENT_"
							+ paramDataL[i]));
					this.empInfoDao.editAssistInfo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	public int editProductInfo(HttpServletRequest request) throws SQLException {
		try {
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
	public List getPositionList(HttpServletRequest request) throws SQLException {

		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));

		retrunList = empInfoDao.getPositionList(paramMap);

		return retrunList;
	}

	@Override
	public List getDqmcList(HttpServletRequest request) throws SQLException {

		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));

		retrunList = empInfoDao.getDqmcList(paramMap);

		return retrunList;
	}

	@Override
	public List getDqmcListNew(HttpServletRequest request) throws SQLException {

		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));

		retrunList = empInfoDao.getDqmcListNew(paramMap);

		return retrunList;
	}

	public List getCompanyList(HttpServletRequest request) throws SQLException {

		List retrunList = new ArrayList();

		Map paramMap = new LinkedHashMap();

		paramMap.put("ACTIVITY", 1);
		if ("".equals(paramMap.get("interLanguage"))) {
			paramMap.put("interLanguage", "zh");
		}
		retrunList = companyDao.getCompanyItemAllList(paramMap);
		return retrunList;
	}

	public List getCompanyListHome(HttpServletRequest request)
			throws SQLException {

		List retrunList = new ArrayList();

		Map paramMap = new LinkedHashMap();

		paramMap.put("ACTIVITY", 1);
		if ("".equals(paramMap.get("interLanguage"))) {
			paramMap.put("interLanguage", "zh");
		}
		retrunList = companyDao.getCompanyItemAllListHome(paramMap);
		return retrunList;
	}

	public List getEmpInfoLxjList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		List retrunList = companyDao.getEmpInfoLxjList(paramMap);
		return retrunList;
	}

	public List getPaInfoLxjList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		List retrunList = companyDao.getPaInfoLxjList(paramMap);
		return retrunList;
	}

	public List getPaInfoLxjLgechList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		List retrunList = companyDao.getPaInfoLxjLgechList(paramMap);
		return retrunList;
	}

	public List getPaInfoLxjLgetaList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		List retrunList = companyDao.getPaInfoLxjLgetaList(paramMap);
		return retrunList;
	}

	public List getYearInfoLxjList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getYearInfoLxjList(paramMap);
		return retrunList;
	}

	public List getpayDetilInfoLxjList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getpayDetilInfoLxjList(paramMap);
		return retrunList;
	}

	public List getpayDetilInfoLxjLgechList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getpayDetilInfoLxjLgechList(paramMap);
		return retrunList;
	}

	public List getpayDetilInfoLxjLgetaList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getpayDetilInfoLxjLgetaList(paramMap);
		return retrunList;
	}

	public List getotherPayInfoLxjList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getotherPayInfoLxjList(paramMap);
		return retrunList;
	}

	public List getotherPayInfoLxjLgechList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getotherPayInfoLxjLgechList(paramMap);
		return retrunList;
	}

	public List getotherPayInfoLxjLgetaList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getotherPayInfoLxjLgetaList(paramMap);
		return retrunList;
	}

	public List getwelfarePayInfoLxjList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getwelfarePayInfoLxjList(paramMap);
		return retrunList;
	}

	public List getwelfarePayInfoLxjLgechList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getwelfarePayInfoLxjLgechList(paramMap);
		return retrunList;
	}

	public List getwelfarePayInfoLxjLgetaList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getwelfarePayInfoLxjLgechList(paramMap);
		return retrunList;
	}

	public List getadministrationPayInfoLxjList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getadministrationPayInfoLxjList(paramMap);
		return retrunList;
	}

	public List getadministrationPayInfoLxjLgechList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao
				.getadministrationPayInfoLxjLgechList(paramMap);
		return retrunList;
	}

	public List getadministrationPayInfoLxjLgetaList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao
				.getadministrationPayInfoLxjLgetaList(paramMap);
		return retrunList;
	}

	public List getpaManuallyInfoLxjList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getpaManuallyInfoLxjList(paramMap);
		return retrunList;
	}

	public List getpaManuallyInfoLxjLgechList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getpaManuallyInfoLxjLgechList(paramMap);
		return retrunList;
	}

	public List getpaManuallyInfoLxjLgetaList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (paramMap.get("KEY") == null)
			paramMap.put("KEY", admin.getEmpID());
		if (paramMap.get("CPNY_ID") == null)
			paramMap.put("CPNY_ID", admin.getCpnyId());
		List retrunList = companyDao.getpaManuallyInfoLxjLgetaList(paramMap);
		return retrunList;
	}

	public Object getPersonalInfoByLeave(HttpServletRequest request,
			String PERSON_ID) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (null != PERSON_ID && !"".equals(PERSON_ID)) {
			paramMap.put("PERSON_ID", PERSON_ID);
		} else {
			if (paramMap.get("PERSON_ID") == null) {
				paramMap.put("PERSON_ID", admin.getPersonId());
			}
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getPersonalInfoByLeave(paramMap);
		// return empInfoDao.getPersonalInfo(paramMap);
	}

	public Object getPersonalInfoByLeaveApply(HttpServletRequest request,
			String PERSON_ID) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (null != PERSON_ID && !"".equals(PERSON_ID)) {
			paramMap.put("PERSON_ID", PERSON_ID);
		} else {
			if (paramMap.get("PERSON_ID") == null) {
				paramMap.put("PERSON_ID", admin.getPersonId());
			}
		}

		return empInfoDao.getPersonalInfoByLeaveApply(paramMap);
	}

	public Object getArchivesInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		paramMap.put("CPNY_ID", admin.getCpnyId());
		LinkedHashMap archivseInfo = (LinkedHashMap) empInfoDao
				.getArchivesInfo(paramMap);
		LinkedHashMap fileParam = new LinkedHashMap();
		fileParam.put("APPLY_TYPE", "0");
		fileParam.put("APPLY_NO", paramMap.get("ARCH_ID"));
		List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
		String fileName = "";
		String fileUrl = "";
		for (int i = 0; i < fileList.size(); i++) {
			LinkedHashMap fileMap = (LinkedHashMap) fileList.get(i);
			String fileUrlStr = fileMap.get("FILE_URL").toString();
			fileUrlStr = fileUrlStr.substring(fileUrlStr.lastIndexOf("/") + 1);
			if (i == 0) {
				fileName = fileMap.get("FILE_NAME").toString();
				fileUrl = fileUrlStr;
			} else {
				fileName += ";" + fileMap.get("FILE_NAME").toString();
				fileUrl += ";" + fileUrlStr;
			}
		}
		archivseInfo.put("FILE_NAME", fileName);
		archivseInfo.put("FILE_URL", fileUrl);
		archivseInfo.put("fileList", fileList);
		return archivseInfo;
	}

	/**
	 * 获取社员兼卖产品类型
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public List getEmpProductList(HttpServletRequest request)
			throws SQLException {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.empInfoDao.getEmpProductList(paramMap);
	}

	/**
	 * 根据法人获取人员类型组
	 */
	public List getEmpTypeGroup(HttpServletRequest request) throws SQLException {

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		List retrunList = empInfoDao.getEmpTypeGroup(paramMap);

		return retrunList;
	}

	public List getEmpInfoTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
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
		return retrunList;
		/*
		 * AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		 * LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		 * paramMap.put("PERSON_ID", admin.getPersonId()); return
		 * this.empInfoDao
		 * .getEmpInfoTempList(paramMap,UiUtil.getPageNum(request),
		 * UiUtil.getNumPerPage(request));
		 */
	}

	/*
	 * public int getEmpInfoTempCnt(HttpServletRequest request){ AdminBean admin
	 * = SessionUtil.getLoginUserFromSession(request); LinkedHashMap paramMap =
	 * ObjectBindUtil.getRequestParamData(request); paramMap.put("PERSON_ID",
	 * admin.getPersonId()); return this.empInfoDao.getEmpInfoTempCnt(paramMap);
	 * }
	 * 
	 * public int getEmpInfoTempErrCnt(HttpServletRequest request){ AdminBean
	 * admin = SessionUtil.getLoginUserFromSession(request); LinkedHashMap
	 * paramMap = ObjectBindUtil.getRequestParamData(request);
	 * paramMap.put("PERSON_ID", admin.getPersonId()); return
	 * this.empInfoDao.getEmpInfoTempErrCnt(paramMap); }
	 */

	public int getEmpInfoTempCnt(HttpServletRequest request, String errorFlag) {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("E".equals(errorFlag)) {
			retrunInt = empInfoDao.getEmpInfoTempErrCnt(paramMap);
		} else {
			retrunInt = empInfoDao.getEmpInfoTempCnt(paramMap);
		}

		return retrunInt;
	}

	public String importEmpInfoTempListExcel(HttpServletRequest request) {
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
	public int getTempLanguageTempCnt(HttpServletRequest request,
			String errorFlag) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("E".equals(errorFlag)) {
			retrunInt = empInfoDao.getTempLanguageTempErrorCnt(paramMap);
		} else {
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
		List retrunList = new ArrayList();
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
		return retrunList;
	}

	@Override
	public int getQualInfoTempCnt(HttpServletRequest request, String errorFlag) {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("E".equals(errorFlag)) {
			retrunInt = empInfoDao.getQualInfoTempErrCnt(paramMap);
		} else {
			retrunInt = empInfoDao.getQualInfoTempCnt(paramMap);
		}

		return retrunInt;
	}

	/**
	 * 获取兼卖信息导入信息
	 */
	@Override
	public List getProductInfoTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
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
		return retrunList;
	}

	@Override
	public int getProductInfoTempCnt(HttpServletRequest request,
			String errorFlag) {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("E".equals(errorFlag)) {
			retrunInt = empInfoDao.getProductInfoTempErrCnt(paramMap);
		} else {
			retrunInt = empInfoDao.getProductInfoTempCnt(paramMap);
		}

		return retrunInt;
	}

	@Override
	public List getWorkExperienceInfoTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getWorkExperienceInfoTempList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getWorkExperienceInfoTempList(paramMap);
		}
		return retrunList;
	}

	@Override
	public int getWorkExperienceInfoTempCnt(HttpServletRequest request,
			String errorFlag) {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("E".equals(errorFlag)) {
			retrunInt = empInfoDao.getWorkExperienceInfoTempErrCnt(paramMap);
		} else {
			retrunInt = empInfoDao.getWorkExperienceInfoTempCnt(paramMap);
		}

		return retrunInt;
	}

	@Override
	public List getEvsInfoTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
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
		return retrunList;
	}

	@Override
	public int getEvsInfoTempCnt(HttpServletRequest request, String errorFlag) {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("E".equals(errorFlag)) {
			retrunInt = empInfoDao.getEvsInfoTempErrCnt(paramMap);
		} else {
			retrunInt = empInfoDao.getEvsInfoTempCnt(paramMap);
		}

		return retrunInt;
	}

	@Override
	public List getTradeUnionInfoTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
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
		return retrunList;
	}

	@Override
	public List getTradeUnionInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		retrunList = empInfoDao.getTradeunionList(paramMap);
		return retrunList;
	}

	@Override
	public int getTradeUnionInfoTempCnt(HttpServletRequest request,
			String errorFlag) {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("E".equals(errorFlag)) {
			retrunInt = empInfoDao.getTradeUnionInfoTempErrCnt(paramMap);
		} else {
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
	public int getTempDisabledTempCnt(HttpServletRequest request,
			String errorFlag) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("E".equals(errorFlag)) {
			retrunInt = empInfoDao.getTempDisabledTempErrorCnt(paramMap);
		} else {
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
		if (type.equals("workExperience")) {
			paramMap.put("PR_NAME",
					"PKG_EMP_WORKEX_EXCEL_IMP.pr_import_temp_WORKEX_data");
		} else if (type.equals("evsInfo")) {
			paramMap.put("PR_NAME",
					"PKG_EMP_WORKEX_EXCEL_IMP.pr_import_temp_evsInfo_data");
		} else if (type.equals("trade")) {
			paramMap.put("PR_NAME",
					"PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_TRADEU_DATA");
		} else if (type.equals("training")) {
			paramMap.put("PR_NAME",
					"PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_TRAINING_DATA");
		} else if (type.equals("baseEmpInfo")) {
			paramMap.put("PR_NAME",
					"PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_BASEEMP_DATA");
		} else if (type.equals("product")) {
			paramMap.put("PR_NAME",
					"PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_PRODUCT_DATA");
		} else if (type.equals("qual")) {
			paramMap.put("PR_NAME",
					"PKG_EMP_WORKEX_EXCEL_IMP.PR_IMPORT_TEMP_QUAL_DATA");
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
	public List getTrainingImportTempList(HttpServletRequest request) {
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
	public int getTrainingImportTempCnt(HttpServletRequest request,
			String errorFlag) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("E".equals(errorFlag)) {
			retrunInt = empInfoDao.getTrainingImportTempErrorCnt(paramMap);
		} else {
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
		// 模版类型1:工作经历信息 2：评价信息 3：工会信息
		String type = request.getParameter("type");
		// 模版名称
		String name = "";
		if ("work".equals(type)) {
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
			List workExperienceTempList = this.empInfoDao
					.getworkExperienceTempList(paramMap, -1, -1);
			for (int i = 0; i < workExperienceTempList.size(); i++) {
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap) workExperienceTempList
						.get(i);
				map.put("CELL0", map1.get("EMPID") == null ? "" : map1
						.get("EMPID"));
				map.put("CELL1", map1.get("LOCAL_NAME") == null ? "" : map1
						.get("LOCAL_NAME"));
				map.put("CELL2", map1.get("START_DATE") == null ? "" : map1
						.get("START_DATE"));
				map.put("CELL3", map1.get("END_DATE") == null ? "" : map1
						.get("END_DATE"));
				map.put("CELL4", map1.get("CPNY_NAME") == null ? "" : map1
						.get("CPNY_NAME"));
				map.put("CELL5", map1.get("DEPT_NAME") == null ? "" : map1
						.get("DEPT_NAME"));
				map.put("CELL6", map1.get("POSITION") == null ? "" : map1
						.get("POSITION"));
				map.put("CELL7", map1.get("DUTY") == null ? "" : map1
						.get("DUTY"));
				map.put("CELL8", map1.get("PAYROLL") == null ? "" : map1
						.get("PAYROLL"));
				map.put("CELL9", map1.get("UPLOAD_ERROR_MSG") == null ? ""
						: map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			name = "tempworkExperienceInfo";
		} else if ("evs".equals(type)) {
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
			List evsTempList = this.empInfoDao.getEvsInfoTempList(paramMap, -1,
					-1);
			for (int i = 0; i < evsTempList.size(); i++) {
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap) evsTempList.get(i);
				map.put("CELL0", map1.get("EMPID") == null ? "" : map1
						.get("EMPID"));
				map.put("CELL1", map1.get("LOCAL_NAME") == null ? "" : map1
						.get("LOCAL_NAME"));
				map.put("CELL2", map1.get("EV_PERIOD") == null ? "" : map1
						.get("EV_PERIOD"));
				map.put("CELL3", map1.get("EV_ACHI") == null ? "" : map1
						.get("EV_ACHI"));
				map.put("CELL4", map1.get("EV_ATTI") == null ? "" : map1
						.get("EV_ATTI"));
				map.put("CELL5", map1.get("EV_ABIL") == null ? "" : map1
						.get("EV_ABIL"));
				map.put("CELL6", map1.get("EV_MARK") == null ? "" : map1
						.get("EV_MARK"));
				map.put("CELL7", map1.get("EV_GRADE") == null ? "" : map1
						.get("EV_GRADE"));
				map.put("CELL8", map1.get("SUGGESTION") == null ? "" : map1
						.get("SUGGESTION"));
				map.put("CELL9", map1.get("FINAL_SEQUENCE") == null ? "" : map1
						.get("FINAL_SEQUENCE"));
				map.put("CELL10", map1.get("TOTAL_PEOPLE") == null ? "" : map1
						.get("TOTAL_PEOPLE"));
				map.put("CELL11", map1.get("EV_REMARK") == null ? "" : map1
						.get("EV_REMARK"));
				map.put("CELL12", map1.get("UPLOAD_ERROR_MSG") == null ? ""
						: map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			mapNameList.add("评价等级参考");
			mapList.add(codeSql + "3538");
			name = "tempEvsInfo";
		} else if ("trade".equals(type)) {
			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("职责");
			aliasNameList.add("起始日期");
			aliasNameList.add("终止日期");
			aliasNameList.add("会费支付状态");
			aliasNameList.add("支付方式");
			aliasNameList.add("备注");
			aliasNameList.add("验证结果");
			List tradeUnionTempList = this.empInfoDao.getTradeunionList(
					paramMap, -1, -1);
			for (int i = 0; i < tradeUnionTempList.size(); i++) {
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap) tradeUnionTempList.get(i);
				map.put("CELL0", map1.get("EMPID") == null ? "" : map1
						.get("EMPID"));
				map.put("CELL1", map1.get("LOCAL_NAME") == null ? "" : map1
						.get("LOCAL_NAME"));
				map.put("CELL2", map1.get("RESPONSIBITITY") == null ? "" : map1
						.get("RESPONSIBITITY"));
				map.put("CELL3", map1.get("ADDDATE") == null ? "" : map1
						.get("ADDDATE"));
				map.put("CELL4", map1.get("QUITDATE") == null ? "" : map1
						.get("QUITDATE"));
				map.put("CELL5", map1.get("PAY_FLAG") == null ? "" : map1
						.get("PAY_FLAG"));
				map.put("CELL6", map1.get("PAY_TYPE") == null ? "" : map1
						.get("PAY_TYPE"));
				map.put("CELL7", map1.get("REMARK") == null ? "" : map1
						.get("REMARK"));
				map.put("CELL8", map1.get("UPLOAD_ERROR_MSG") == null ? ""
						: map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			mapNameList.add("职责参考");
			mapNameList.add("会费支付状态");
			mapNameList.add("支付方式");
			mapList.add(codeSql + "123251");
			mapList.add(codeSql + "123224");
			mapList.add(codeSql + "211654");
			name = "tempTradeUnionInfo";
		} else if ("training".equals(type)) {
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
			List trainingTempList = this.empInfoDao.getTrainingImportTempList(
					paramMap, -1, -1);
			for (int i = 0; i < trainingTempList.size(); i++) {
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap) trainingTempList.get(i);
				map.put("CELL0", map1.get("EMPID") == null ? "" : map1
						.get("EMPID"));
				map.put("CELL1", map1.get("LOCAL_NAME") == null ? "" : map1
						.get("LOCAL_NAME"));
				map.put("CELL2", map1.get("COURSE_NAME") == null ? "" : map1
						.get("COURSE_NAME"));
				map.put("CELL3", map1.get("MUST_CODE") == null ? "" : map1
						.get("MUST_CODE"));
				map.put("CELL4",
						map1.get("TRAINING_DIFFERENTIATE") == null ? "" : map1
								.get("TRAINING_DIFFERENTIATE"));
				map.put("CELL5", map1.get("START_DATE") == null ? "" : map1
						.get("START_DATE"));
				map.put("CELL6", map1.get("END_DATE") == null ? "" : map1
						.get("END_DATE"));
				map.put("CELL7", map1.get("INSTITUTION_NAME") == null ? ""
						: map1.get("INSTITUTION_NAME"));
				map.put("CELL8", map1.get("TRAINING_METHOD") == null ? ""
						: map1.get("TRAINING_METHOD"));
				map.put("CELL9", map1.get("TRAINING_TIME") == null ? "" : map1
						.get("TRAINING_TIME"));
				map.put("CELL10", map1.get("TRAINING_RESULT") == null ? ""
						: map1.get("TRAINING_RESULT"));
				map.put("CELL11", map1.get("REMARKS") == null ? "" : map1
						.get("REMARKS"));
				map.put("CELL12", map1.get("UPLOAD_ERROR_MSG") == null ? ""
						: map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			mapNameList.add("选修必修参考");
			mapNameList.add("培训区分参考");
			mapNameList.add("培训方法参考");
			mapList.add(codeSql + "123376");
			mapList.add(codeSql + "123459");
			mapList.add(codeSql + "123271");

			name = "tempTrainingInfo";
		} else if ("product".equals(type)) {
			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("产品名称");
			aliasNameList.add("备注");
			aliasNameList.add("验证结果");
			List tradeUnionTempList = this.empInfoDao.getProductInfoTempList(
					paramMap, -1, -1);
			for (int i = 0; i < tradeUnionTempList.size(); i++) {
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap) tradeUnionTempList.get(i);
				map.put("CELL0", map1.get("EMPID") == null ? "" : map1
						.get("EMPID"));
				map.put("CELL1", map1.get("LOCAL_NAME") == null ? "" : map1
						.get("LOCAL_NAME"));
				map.put("CELL2", map1.get("PRODUCT_NO") == null ? "" : map1
						.get("PRODUCT_NO"));
				map.put("CELL7", map1.get("REMARK") == null ? "" : map1
						.get("REMARK"));
				map.put("CELL8", map1.get("UPLOAD_ERROR_MSG") == null ? ""
						: map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			mapNameList.add("产品类型参考");
			mapList.add(codeSql + "211424");
			name = "tempSellProductInfo";
		} else if ("qual".equals(type)) {
			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("资格证名称");
			aliasNameList.add("证件级别");
			aliasNameList.add("职称");
			aliasNameList.add("颁发机构");
			aliasNameList.add("颁发日期");
			aliasNameList.add("结贴标准(金额)");
			aliasNameList.add("验证结果");
			List qualInfoTempList = this.empInfoDao.getQualInfoTempList(
					paramMap, -1, -1);
			for (int i = 0; i < qualInfoTempList.size(); i++) {
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap) qualInfoTempList.get(i);
				map.put("CELL0", map1.get("EMPID") == null ? "" : map1
						.get("EMPID"));
				map.put("CELL1", map1.get("LOCAL_NAME") == null ? "" : map1
						.get("LOCAL_NAME"));
				map.put("CELL2", map1.get("QUAL_NAME") == null ? "" : map1
						.get("QUAL_NAME"));
				map.put("CELL3", map1.get("QUAL_LEVEL") == null ? "" : map1
						.get("QUAL_LEVEL"));
				map.put("CELL4", map1.get("QUAL_CARD_NO") == null ? "" : map1
						.get("QUAL_CARD_NO"));
				map.put("CELL5", map1.get("QUAL_INSTITUTE") == null ? "" : map1
						.get("QUAL_INSTITUTE"));
				map.put("CELL6", map1.get("DATE_OBTAINED") == null ? "" : map1
						.get("DATE_OBTAINED"));
				map.put("CELL7", map1.get("REMARK") == null ? "" : map1
						.get("REMARK"));
				map.put("CELL8", map1.get("UPLOAD_ERROR_MSG") == null ? ""
						: map1.get("UPLOAD_ERROR_MSG"));
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
			paramMap.put("old_personId", paramMap
					.get("dwz.person_old.personId"));
			// 验证身份证号是否一致
			if (this.empInfoDao.validIdCard(paramMap) == 0) {
				return 2;
			}
			// 验证是否已经mapping过
			if (this.empInfoDao.validIsDuplicate(paramMap) == 1) {
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
	public LinkedHashMap getEmpMappingByNo(HttpServletRequest request)
			throws SQLException {
		LinkedHashMap empInfo = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		List list = this.empInfoDao.getEmpMappingByNo(paramMap);
		if (list != null && list.size() > 0) {
			empInfo = (LinkedHashMap) list.get(0);
		}
		return empInfo;
	}

	/**
	 * 获取员工年假信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map getEmpVacInfo(Map param) throws Exception {
		if ("TSTO".equals(param.get("CPNY_ID"))) {
			param.put("YEAR", DateUtil.getCurrentYearStr());
		} else {
			param.put("YEAR", this.infoApplyLeaveDao.getCurrentYear(param));
		}
		param.put("APPLY_NO", param.get("APPLY_NO") == null ? "" : param
				.get("APPLY_NO"));
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
	public List viewEmpMappingList(HttpServletRequest request)
			throws SQLException {
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
	public int viewEmpMappingListCnt(HttpServletRequest request)
			throws SQLException {
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
	public String getTemplateInfoByExcelData11(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList) {
		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("interCpnyID", admin.getCpnyId());
		String type = request.getParameter("type");

		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT,'' CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		// 模版名称
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
			List paTempLanguageTempList = this.empInfoDao
					.getTempLanguageTempList(paramMap, -1, -1);
			for (int i = 0; i < paTempLanguageTempList.size(); i++) {
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap) paTempLanguageTempList
						.get(i);
				map.put("CELL0", map1.get("LANGUAGE_NO") == null ? "" : map1
						.get("LANGUAGE_NO"));
				map.put("CELL1", map1.get("PERSON_ID") == null ? "" : map1
						.get("PERSON_ID"));
				map.put("CELL2", map1.get("KAOSHIDATE") == null ? "" : map1
						.get("KAOSHIDATE"));
				map.put("CELL3", map1.get("EXAM_NAME_CODE") == null ? "" : map1
						.get("EXAM_NAME_CODE"));
				map.put("CELL4", map1.get("LANGUAGE_LEVEL_CODE") == null ? ""
						: map1.get("LANGUAGE_LEVEL_CODE"));
				map.put("CELL5", map1.get("MARK") == null ? "" : map1
						.get("MARK"));
				map.put("CELL6", map1.get("ALLWANCE") == null ? "" : map1
						.get("ALLWANCE"));
				map.put("CELL7", map1.get("UPLOAD_ERROR_MSG") == null ? ""
						: map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			mapNameList.add("考试名参考");
			mapNameList.add("等级参考");
			mapList.add(codeSql + "1394");
			mapList.add(codeSql + "1401");
			name = "languageInfo";
		} else if ("2".equals(type)) {
			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("颁发日期");
			aliasNameList.add("残疾人类型");
			aliasNameList.add("有效期");
			aliasNameList.add("备注");
			aliasNameList.add("验证结果");
			List paTempDisableTempList = this.empInfoDao
					.getTempDisabledTempList(paramMap, -1, -1);
			for (int i = 0; i < paTempDisableTempList.size(); i++) {
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap) paTempDisableTempList
						.get(i);
				map.put("CELL0", map1.get("T_ID") == null ? "" : map1
						.get("T_ID"));
				map.put("CELL1", map1.get("PERSON_ID") == null ? "" : map1
						.get("PERSON_ID"));
				map.put("CELL2", map1.get("ADDDATE") == null ? "" : map1
						.get("ADDDATE"));
				map.put("CELL3", map1.get("DISABILITY_TYPE") == null ? ""
						: map1.get("DISABILITY_TYPE"));
				map.put("CELL4", map1.get("DISABILITY_VALIDITY") == null ? ""
						: map1.get("DISABILITY_VALIDITY"));
				map.put("CELL5", map1.get("REMARK") == null ? "" : map1
						.get("REMARK"));
				map.put("CELL6", map1.get("UPLOAD_ERROR_MSG") == null ? ""
						: map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			mapNameList.add("残疾类型参考");
			mapNameList.add("有效期参考");
			mapList.add(codeSql + "123264");
			mapList.add(codeSql + "123462");
			name = "disabledInfo";
		} else if ("3".equals(type)) {
			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("关系");
			aliasNameList.add("紧急联系人姓名");
			aliasNameList.add("电话");
			aliasNameList.add("验证结果");
			List paTempContactTempList = this.empInfoDao
					.getTempContactTempList(paramMap, -1, -1);
			for (int i = 0; i < paTempContactTempList.size(); i++) {
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap) paTempContactTempList
						.get(i);
				map.put("CELL0", map1.get("FAMILY_NO") == null ? "" : map1
						.get("FAMILY_NO"));
				map.put("CELL1", map1.get("PERSON_ID") == null ? "" : map1
						.get("PERSON_ID"));
				map.put("CELL2", map1.get("FAM_TYPE_CODE") == null ? "" : map1
						.get("FAM_TYPE_CODE"));
				map.put("CELL3", map1.get("FAM_NAME") == null ? "" : map1
						.get("FAM_NAME"));
				map.put("CELL4", map1.get("FAM_PHONE") == null ? "" : map1
						.get("FAM_PHONE"));
				map.put("CELL5", map1.get("UPLOAD_ERROR_MSG") == null ? ""
						: map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			mapNameList.add("关系参考");
			mapList.add(codeSql + "1693");
			name = "contactInfo";
		} else if ("4".equals(type)) {

			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("标题");
			aliasNameList.add("内容");
			aliasNameList.add("验证结果");
			List paTempAssistTempList = this.empInfoDao.getTempAssistTempList(
					paramMap, -1, -1);
			for (int i = 0; i < paTempAssistTempList.size(); i++) {
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap) paTempAssistTempList
						.get(i);
				map.put("CELL0", map1.get("ASSIST_NO") == null ? "" : map1
						.get("ASSIST_NO"));
				map.put("CELL1", map1.get("PERSON_ID") == null ? "" : map1
						.get("PERSON_ID"));
				map.put("CELL2", map1.get("TITLE") == null ? "" : map1
						.get("TITLE"));
				map.put("CELL3", map1.get("CONTENTA") == null ? "" : map1
						.get("CONTENTA"));
				map.put("CELL4", map1.get("UPLOAD_ERROR_MSG") == null ? ""
						: map1.get("UPLOAD_ERROR_MSG"));
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
	public int getTempContactTempCnt(HttpServletRequest request,
			String errorFlag) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("E".equals(errorFlag)) {
			retrunInt = empInfoDao.getTempContactTempErrorCnt(paramMap);
		} else {
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
		if ("E".equals(errorFlag)) {
			retrunInt = empInfoDao.getTempAssistTempErrorCnt(paramMap);
		} else {
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
		String accrual = request.getParameter("accrual");
		if ("assist".equals(accrual)) {
			paramMap.put("PR_INFO_NAME",
					"pkg_hrm_info_excel_imp.pr_import_temp_assist");
		} else if ("contact".equals(accrual)) {
			paramMap.put("PR_INFO_NAME",
					"pkg_hrm_info_excel_imp.pr_import_temp_contact");
		} else if ("disable".equals(accrual)) {
			paramMap.put("PR_INFO_NAME",
					"pkg_hrm_info_excel_imp.pr_import_temp_disable");
		} else if ("language".equals(accrual)) {
			paramMap.put("PR_INFO_NAME",
					"pkg_hrm_info_excel_imp.pr_import_temp_language");
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
		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", admin.getLanguage());
		paramMap.put("CPNY_ID",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		paramMap.put("PERSON_ID", admin.getAdminID());
		paramMap.put("specialParam", admin.getSpecialParam());// 这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo", admin.getDeptNo());// 判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		// 判断是否有超级管理员权限
		int authority = authorityUtil.isSuperUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "SuperUser");// 超级用户权限
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getTempEmpInfoList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getTempEmpInfoList(paramMap);
		}

		return retrunList;
	}

	@Override
	public Object getTempEmpInfoListCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		paramMap.put("PERSON_ID", admin.getAdminID());
		paramMap.put("specialParam", admin.getSpecialParam());// 这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo", admin.getDeptNo());// 判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		// 判断是否有超级管理员权限
		int authority = authorityUtil.isSuperUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "SuperUser");// 超级用户权限
		}

		return empInfoDao.getTempEmpInfoListCnt(paramMap);
	}

	@Override
	public Object getEmpTypeList(HttpServletRequest request) {

		List retrunList = new ArrayList();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		retrunList = empInfoDao.getEmpTypeList(paramMap);

		return retrunList;
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
		if (!paramMap.containsKey("FROM_TIME")) {
			paramMap.put("FROM_TIME", DateUtil.getCurrentMonthStrFormat()
					+ "-01");
			paramMap.put("TO_TIME", DateUtil.getSysdateStr());
		}
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("FLAG", "3");
		List viewRegisterInfoList = recruitManageDao.viewRegisterInfoList(paramMap);
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
			returnList = empInfoDao.getTempEmpAffirmInfoListBatch(paramMap);
//		}
		return returnList;
	}

	/**
	 * 临时职批量入职总数
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getTmpEmpAffirmInfoListCntBatch(HttpServletRequest request)
			throws Exception {
		int ListCnt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("PERSON_ID", admin.getPersonId());
		ListCnt = empInfoDao.getTempEmpAffirmInfoListCntBatch(paramMap);

		return ListCnt;
	}

	@Override
	@SuppressWarnings( { "unchecked", "rawtypes" })
	public List getImportTmpEmpResultList(HttpServletRequest request,
			Map paramMap) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = empInfoDao.getImportTmpEmpResultList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = empInfoDao.getImportTmpEmpResultList(paramMap, 1, 10);
		}
		return retrunList;
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	@Override
	public int getImportTmpEmpResultCnt(HttpServletRequest request, Map paramMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		return empInfoDao.getImportTmpEmpResultCnt(paramMap);
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	@Override
	public int getImportTmpEmpErrCnt(HttpServletRequest request, Map paramMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		return empInfoDao.getImportTmpEmpErrCnt(paramMap);
	}

	@Override
	@SuppressWarnings( { "rawtypes", "unchecked" })
	public String importTmpEmpFromExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		String result = "";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		paramMap.put("UPDT_PERSON_ID", admin.getPersonId());
		result = empInfoDao.importTmpEmpFromExcel(paramMap);
		return result;
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	@Override
	public List getImportTmpEmpFromExcel(HttpServletRequest request)
			throws Exception {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));

		retrunList = empInfoDao.getImportTmpEmpResultList(paramMap);

		return retrunList;
	}

	public int delTmpEmpInBatch(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("language", Messages.getLanguage(request));

		String[] isChecked = request.getParameterValues("tempEmpCheck");
		if (isChecked != null) {
			for (int i = 0; i < isChecked.length; i++) {
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

	@SuppressWarnings( { "unchecked", "rawtypes" })
	public List viewTempEmpBatchReq(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		String ls = "";
		String[] isChecked = request.getParameterValues("tempEmpCheck");
		if (isChecked != null) {
			for (int i = 0; i < isChecked.length; i++) {
				ls = ls.equals("") ? isChecked[i].toString()
						: (ls + "," + isChecked[i].toString());
			}
		}
		paramMap.put("BATCH_NOS", ls);
		paramMap.put("TRANS_NO", "1359");// 1359为入职发令

		return empInfoDao.getTempEmpBatchReqList(paramMap, UiUtil
				.getPageNum(request), UiUtil.getNumPerPage(request));
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	@Override
	public int getTempEmpBatchReqCnt(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		String ls = "";
		String[] isChecked = request.getParameterValues("tempEmpCheck");
		if (isChecked != null) {
			for (int i = 0; i < isChecked.length; i++) {
				ls = ls.equals("") ? isChecked[i].toString()
						: (ls + "," + isChecked[i].toString());
			}
		}
		paramMap.put("BATCH_NOS", ls);
		paramMap.put("TRANS_NO", "1359");// 1359为入职发令

		return empInfoDao.getTempEmpBatchReqCnt(paramMap);
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	@Override
	public List getTempEmpBatchAffirmList(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		paramMap.put("BATCH_NOS", paramMap.get("BATCH_NO"));
		paramMap.put("REQ_ID", paramMap.get("APPLY_NO"));
		paramMap.put("TRANS_NO", "1359");// 1359为入职发令

		return empInfoDao.getTempEmpBatchReqList(paramMap, UiUtil
				.getPageNum(request), UiUtil.getNumPerPage(request));
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	@Override
	public int getTempEmpBatchAffirmCnt(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		paramMap.put("BATCH_NOS", paramMap.get("BATCH_NO"));
		paramMap.put("REQ_ID", paramMap.get("APPLY_NO"));
		paramMap.put("TRANS_NO", "1359");// 1359为入职发令

		return empInfoDao.getTempEmpBatchReqCnt(paramMap);
	}

	public String getEmpBatchTemp(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException {
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
		mapList
				.add(" SELECT D.DEPTID NO, D.ORG_NAME_LOCAL CONTENT FROM HR_DEPARTMENT D "
						+ " WHERE (D.DATE_ENDED IS NULL OR D.DATE_ENDED > SYSDATE) "
						+ " AND D.CPNY_ID = '"
						+ admin.getCpnyId()
						+ "' "
						+ " AND D.ACTIVITY = 1 "
						+ " AND f_check_user_dept_auth(D.DEPTNO,'"
						+ admin.getUserNo() + "','hr') = 1 ");
		mapNameList.add("最终学历");
		mapList.add(nameSql + "211777");
		mapNameList.add("户口性质");
		mapList.add(" SELECT DISTINCT A.REG_TYPE_CODE NO,' ' CONTENT "
				+ " FROM hr_personal_info A,HR_EMPLOYEE B "
				+ " WHERE A.PERSON_ID = B.PERSON_ID AND B.CPNY_ID = '"
				+ admin.getCpnyId() + "' "
				+ " AND A.REG_TYPE_CODE IS NOT NULL ORDER BY A.REG_TYPE_CODE ");
		mapNameList.add("工资级号");
		mapList.add(" SELECT DISTINCT A.PAY_GRADE NO,' ' CONTENT "
				+ " FROM  HR_EMP_PA_INFO A, HR_EMPLOYEE B "
				+ " WHERE A.PERSON_ID = B.PERSON_ID AND B.CPNY_ID = '"
				+ admin.getCpnyId() + "' " + " ORDER BY A.PAY_GRADE ");
		mapNameList.add("工资级号等级");
		mapList.add(" SELECT DISTINCT A.PAY_STEP NO,' ' CONTENT "
				+ " FROM  HR_EMP_PA_INFO A, HR_EMPLOYEE B "
				+ " WHERE A.PERSON_ID = B.PERSON_ID AND B.CPNY_ID = '"
				+ admin.getCpnyId() + "' " + " ORDER BY A.PAY_STEP ");
		mapNameList.add("福利地区");
		mapList.add(codeSql + "216736 AND SP.CPNY_ID(+) = '"
				+ admin.getCpnyId() + "'");
		mapNameList.add("人员类型");
		mapList
				.add(" SELECT T.TEMP_EMPTYPE NO,SY.CONTENT CONTENT "
						+ " FROM HR_TEMP_EMPTYPE   T,SY_GLOBAL_NAME SY "
						+ " WHERE T.TEMP_EMPTYPE = SY.NO(+) AND SY.LANGUAGE(+) = 'zh' AND T.ACTIVITY = 1 "
						+ " AND EXISTS ( SELECT 1 FROM HR_JOB_TYPE_SETUP D WHERE D.JOBTYPE_NO = T.TEMP_EMPTYPE AND EXISTS "
						+ " (SELECT 1 FROM SY_SUPERVISOR_EMPTYPE_INFO E  WHERE E.EMP_TYPE_CODE = JOBTYPE_GROUP_NO AND PERSON_ID = '"
						+ admin.getPersonId() + "') " + " AND CPNY_ID = '"
						+ admin.getCpnyId() + "') AND T.CPNY_NAME = '"
						+ admin.getCpnyId() + "' ");
		mapNameList.add("工作地区");
		mapList
				.add(" SELECT NO, CONTENT FROM ( "
						+ " SELECT DISTINCT A.STATE_CD NO, A.STATE_NM CONTENT, A.STATE_CD IDX "
						+ " FROM  INC_CITY_TO_CITYLEVEL_MAPP A "
						+ " UNION ALL "
						+ " SELECT DISTINCT B.CITY_CD NO, B.CITY_NM CONTENT, B.STATE_CD||B.CITY_CD IDX "
						+ " FROM  INC_CITY_TO_CITYLEVEL_MAPP B "
						+ " UNION ALL "
						+ " SELECT DISTINCT C.REGION_CD NO, C.REGION_NM CONTENT, C.STATE_CD||C.CITY_CD||C.REGION_CD IDX "
						+ " FROM  INC_CITY_TO_CITYLEVEL_MAPP C "
						+ " ) ORDER BY IDX ");
		mapNameList.add("工作类型(CHR)");
		mapList.add(nameSql + "211554 AND SP.CPNY_ID(+) = '"
				+ admin.getCpnyId() + "'");
		mapNameList.add("班号");
		mapList
				.add(" SELECT DISTINCT SHIFT_NO NO,' ' CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID = '"
						+ admin.getCpnyId() + "' AND SHIFT_NO IS NOT NULL");
		mapNameList.add("促销员所属");
		mapList.add(codeSql + "211837 AND SP.CPNY_ID(+) = '"
				+ admin.getCpnyId() + "'");
		mapNameList.add("星级级别");
		mapList.add(codeSql + "215954 AND SP.CPNY_ID(+) = '"
				+ admin.getCpnyId() + "'");
		mapNameList.add("产品");
		mapList
				.add("SELECT distinct T.DESCRIPTION NO,NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U, SY_CODE_PARAM SP WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.CODE_NO = SP.CODE_NO(+) AND T.PARENT_CODE_NO = 211424 AND SP.CPNY_ID(+) = '"
						+ admin.getCpnyId() + "'");
		mapNameList.add("职务");
		mapList
				.add("SELECT distinct T.DESCRIPTION NO,NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U, SY_CODE_PARAM SP WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.CODE_NO = SP.CODE_NO(+) AND T.PARENT_CODE_NO = 14013573 AND SP.CPNY_ID(+) = '"
						+ admin.getCpnyId() + "'");

		return "EmpImport";
	}

	/**************************************************************************************************************/
	@Override
	@SuppressWarnings( { "rawtypes", "unchecked" })
	public Object getTempEmpReqDetail(HttpServletRequest request) {
		Object returnObj = new Object();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO", admin.getUserNo());

		returnObj = empInfoDao.getTempEmpReqDetail(paramMap);
		return returnObj;
	}

	/*
	 * Title: getInsrareaList Description:查询法人人员所用的所有福利地区
	 * 
	 * @author 孙鹏
	 * 
	 * @date 2015年3月16日 下午4:22:35
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @seecom.ait.hrm.service.EmpInfoSer#getInsrareaList(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public Object getInsrareaList(HttpServletRequest request) {
		Object returnObj = new Object();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("defaultCpny", admin.getCpnyId());
		returnObj = empInfoDao.getInsrareaForCpnyId(paramMap);
		return returnObj;
	}

	/**
	 * 批量删除加班申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int syncEmpInfo(HttpServletRequest request) throws CommonException {
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
				if ("1".equals(op_flag)) {// 删除
					this.empInfoDao.deleteEmpMapping(map);
				} else if ("2".equals(op_flag)) {// 同步年假
					map.put("FLAG", 1);
					this.empInfoDao.updateEmpMapping(map);
				} else {// 同步调休
					map.put("FLAG", 2);
					this.empInfoDao.updateEmpMapping(map);
				}
			}
		} catch (CommonException e1) {
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
	public int getMappingTempCnt(HttpServletRequest request, String errorFlag) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("E".equals(errorFlag)) {
			retrunInt = empInfoDao.getMappingTempErrorCnt(paramMap);
		} else {
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
	public String submitImportExcelMappingEmpData(HttpServletRequest request) {

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME",
				"PKG_SY_MAPPING_EXCEL_IMP.PR_IMPORT_EMP_MAPPING_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 标签查询(sql)
	 * 
	 * @param parent_code_no
	 * @return retrunList
	 */
	public List getCodeListBySql(String sql) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("sql", sql);
		retrunList = empInfoDao.getCodeListBySql(paramMap);
		return retrunList;
	}

	/**
	 * 人事信息卡查询
	 * 
	 * @param request
	 * @return retrunList
	 */
	public List viewCardInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		retrunList = empInfoDao.viewCardInfoList(paramMap);

		return retrunList;

	}

	/**
	 * 人事信息卡1查询
	 * 
	 * @param request
	 * @return retrunList
	 */
	public List viewCardInfoList1(HttpServletRequest request) {
		List retrunList = new ArrayList();
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		retrunList = empInfoDao.viewCardInfoList1(paramMap);
		List personInfoList = new ArrayList();
		if(retrunList != null){
			for(int i=0;i<retrunList.size();i++){
				LinkedHashMap personMap = new LinkedHashMap();
				paramMap.put("PERSON_ID",((Map)retrunList.get(i)).get("PERSON_ID"));
			List viewBaseInfoList = this.empInfoDao.viewBaseInfoList(paramMap);
			List viewJiaoyuInfoList = this.empInfoDao.viewJiaoyuInfoList(paramMap);
			List viewJingliInfoList = this.empInfoDao.viewJingliInfoList(paramMap);
			List viewJiatingInfoList = this.empInfoDao.viewJiatingInfoList(paramMap);
			List viewPingjiaInfoList = this.empInfoDao.viewPingjiaInfoList(paramMap);
			List viewZigeInfoList = this.empInfoDao.viewZigeInfoList(paramMap);
			List viewPeixunInfoList = this.empInfoDao.viewPeixunInfoList(paramMap);
			List viewJiangliInfoList = this.empInfoDao.viewJiangliInfoList(paramMap);
			List viewChengfaInfoList = this.empInfoDao.viewChengfaInfoList(paramMap);
			List viewFalingInfoList = this.empInfoDao.viewFalingInfoList(paramMap);
			personMap.put("viewBaseInfoList", viewBaseInfoList);
			personMap.put("viewJiaoyuInfoList", viewJiaoyuInfoList);
			personMap.put("viewJingliInfoList", viewJingliInfoList);
			personMap.put("viewJiatingInfoList", viewJiatingInfoList);
			personMap.put("viewPingjiaInfoList", viewPingjiaInfoList);
			personMap.put("viewZigeInfoList", viewZigeInfoList);
			personMap.put("viewPeixunInfoList", viewPeixunInfoList);
			personMap.put("viewJiangliInfoList", viewJiangliInfoList);
			personMap.put("viewChengfaInfoList", viewChengfaInfoList);
			personMap.put("viewFalingInfoList", viewFalingInfoList);
			personInfoList.add(personMap);
			}
		}
		return personInfoList;
		
	}
	
	/**
	 * 标签查询(QueryLabelInformationVolume)
	 * 
	 * @param parent_code_no
	 * @return retrunList
	 */
	@Override
	public List getCodeListDESCRIPTION(String parent_code_no,
			HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PARENT_CODE_NO", parent_code_no);
		retrunList = empInfoDao.getCodeListDESCRIPTION(paramMap);
		return retrunList;
	}

	/**
	 * viewPregnantManagement
	 */
	@Override
	public List getPregnantManagementList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getPregnantManagementList(paramMap);
	}

	/**
	 * viewPregnantManagement的数量 (Staff foundation information)
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public int getPregnantManagementList_count(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.getPregnantManagementList_count(paramMap);
	}

	/**
	 * 修改viewPregnantManagement信息(Modify personal information and school
	 * information)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@Override
	public int editPregnantManagement(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// --------------------------------------------------------------------
			String HR_PREGNANT_MANAGE_NO = StringUtil.checkNull(request
					.getParameter("HR_PREGNANT_MANAGE_NO"));
			paramMap.put("HR_PREGNANT_MANAGE_NO", HR_PREGNANT_MANAGE_NO);// 主键no
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			paramMap
					.put("FETATION_DATE", request.getParameter("FETATION_DATE"));
			paramMap.put("EXPECTED_BIRTH_DATE", request
					.getParameter("EXPECTED_BIRTH_DATE"));
			paramMap.put("CHILDBIRTH_DATE", request
					.getParameter("CHILDBIRTH_DATE"));
			paramMap.put("START_BABYCARE_DATE", request
					.getParameter("START_BABYCARE_DATE"));
			paramMap.put("END_BABYCARE_DATE", request
					.getParameter("END_BABYCARE_DATE"));
			paramMap.put("REMARK", request.getParameter("REMARK"));
			if (HR_PREGNANT_MANAGE_NO != null
					&& !"".equals(HR_PREGNANT_MANAGE_NO)) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				paramMap.put("UPDATED_IP", admin.getAdminIP());
				paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
				this.empInfoDao.editPregnantManagement(paramMap);
			} else {
				paramMap.put("CREATED_BY", admin.getAdminID());
				paramMap.put("CREATED_IP", admin.getAdminIP());
				// 插入的时候获得员工的personid
				paramMap.put("PERSON_ID", request
						.getParameter("SINGLE_PERSON_ID"));
				this.empInfoDao.insertPregnantManagement(paramMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * PregnantManagement的基本信息
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public Object viewSinglePregnantManagement(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}

		if (paramMap.get("PERSON_ID") == null) {
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		if (StringUtil.checkNull(request.getParameter("HR_PREGNANT_MANAGE_NO")) != null) {
			paramMap.put("HR_PREGNANT_MANAGE_NO", request
					.getParameter("HR_PREGNANT_MANAGE_NO"));
		}

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return empInfoDao.viewSinglePregnantManagement(paramMap);
	}

	@Override
	public int deletePregnantManagement(HttpServletRequest request) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			// ------------------------------employee---------------------------------------
			paramMap.put("HR_PREGNANT_MANAGE_NO", request
					.getParameter("HR_PREGNANT_MANAGE_NO"));// 主键no
			this.empInfoDao.deletePregnantManagement(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public Object viewCurrentHrInfo(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request);
		return empInfoDao.viewCurrentHrInfo(param);
	}
	
	/**
	 * 
	 * @param request
	 * @return Object
	 */
	@Override
	public List getEmpSimpleInfoList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return empInfoDao.getEmpSimpleInfoList(paramMap);
	}
	
	/**
	 * 上海离职率
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List viewDeptDemissionList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = empInfoDao.viewDeptDemissionList(paramMap) ;
		return returnList ;
	}
	/**
	 * 离职率
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List hrDemissionRateReport(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = empInfoDao.hrDemissionRateReport(paramMap) ;
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getDeptAllList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = empInfoDao.getDeptAllList(paramMap) ;
		return returnList ;
	}
	/**
	 * 离职率
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap hrDemissionRateByDeptNoReport(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List list = new ArrayList();
		paramMap.put("DEPTNO", "'YYB'");
		lMap.put("YYB", empInfoDao.hrDemissionRateByDeptNoReport(paramMap));
		paramMap.put("DEPTNO", "'GLB'");
		lMap.put("GLB", empInfoDao.hrDemissionRateByDeptNoReport(paramMap));
		paramMap.put("DEPTNO", "'DPZZ'");
		lMap.put("DPZZ", empInfoDao.hrDemissionRateByDeptNoReport(paramMap));
		paramMap.put("DEPTNO", "'DPYY'");
		lMap.put("DPYY", empInfoDao.hrDemissionRateByDeptNoReport(paramMap));
		lMap.put("DP", empInfoDao.hrDemissionRateByShopReport(paramMap));
		return lMap ;
	}
	

	/**
	 * 离职率
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap hrDemissionRateFullThreeMonthsReport(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List list = new ArrayList();
		paramMap.put("DEPTNO", "'YYB'");
		lMap.put("YYB", empInfoDao.hrDemissionRateFullThreeMonthsReport(paramMap));
		paramMap.put("DEPTNO", "'GLB'");
		lMap.put("GLB", empInfoDao.hrDemissionRateFullThreeMonthsReport(paramMap));
		paramMap.put("DEPTNO", "'DPZZ'");
		lMap.put("DPZZ", empInfoDao.hrDemissionRateFullThreeMonthsReport(paramMap));
		paramMap.put("DEPTNO", "'DPYY'");
		lMap.put("DPYY", empInfoDao.hrDemissionRateFullThreeMonthsReport(paramMap));
		lMap.put("DP", empInfoDao.hrDemissionRateFullShopThreeMonthsReport(paramMap));
		return lMap ;
	}
	/**
	 * 离职率
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List hrDemissionRateByXdfMonthsReport(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("RECRUIT_TYPE", "80000040");
		returnList = empInfoDao.hrDemissionRateByXdfMonthsReport(paramMap) ;
		return returnList ;
	}
	/**
	 * 店铺人员率
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List hrStroeNumberReport(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = empInfoDao.hrStroeNumberReport(paramMap) ;
		return returnList ;
	}
	
	@Override
	public int updateEmpinfo(HttpServletRequest request, String target) {
		try {
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_IP", admin.getAdminIP());
			if (request.getParameter("PERSON_ID") == null || "".equals(request.getParameter("PERSON_ID")))
			{
				paramMap.put("PERSON_ID", request.getParameter("dwz.person.personId"));
			}
			this.empInfoDao.updateEmpinfo(paramMap, target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
