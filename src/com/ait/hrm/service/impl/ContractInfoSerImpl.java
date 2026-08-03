package com.ait.hrm.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.AffirmLeaveApplyDao;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.service.InfoApplySer;
import com.ait.hrm.dao.ContractInfoDao;
import com.ait.hrm.dao.EmpInfoDao;
import com.ait.hrm.service.ContractInfoSer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.LoginDao;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.MailManager;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ContractInfoSer.java
 * @Create date: Jan 6, 2012 4:19:45 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ContractInfoSerImpl implements ContractInfoSer {

	Logger logger = Logger.getLogger(ContractInfoSerImpl.class);

	@Autowired
	private EmpInfoDao empInfoDao;
	
	@Autowired
	private ContractInfoDao contractInfoDao;

	@Autowired
	private LoginDao loginDao;

	@Autowired
	private InfoApplySer infoApplySer;

	@Autowired
	PaTempSalesDAO paTempSalesDAO;

	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;

	@Autowired
	private AffirmLeaveApplyDao affirmApplyDao;

	@Autowired
	MailManager mailManger;

	// 合同裁决no
	private static String APPLY_TYPE_NO = "218394";

	// 合同邀请名称
	private static String APPLY_TYPE_NAME = "合同续签审批邀请";
	// 合同邀请名称
	private static String APPLY_TYPE_NAME_CHANGE = "合同变更审批邀请";

	/**
	 * 合同查询 (Contract inquires)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractInfoListForSearch(HttpServletRequest request) {

		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		/*String EmpOffice = request.getParameter("seach_EmpOffice") == null ? "15119"
				: request.getParameter("seach_EmpOffice");

		paramMap.put("EmpOffice", EmpOffice);
*/
		String showAllFlag = request.getParameter("showAllFlag") == null ? "N"
				: request.getParameter("showAllFlag");

		paramMap.put("showAllFlag", showAllFlag);

		retrunList = contractInfoDao.getContractListForSearch(paramMap);

		return retrunList;

	}

	/**
	 * 合同变更查询 (Contract inquires)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractChangeList(HttpServletRequest request) {

		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

			retrunList = contractInfoDao.getContractChangeList(paramMap);

		return retrunList;

	}
	
	/**
	 * 合同修改查询 (Contract inquires)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractUpdateList(HttpServletRequest request) {

		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		retrunList = contractInfoDao.getContractUpdateList(paramMap);

		return retrunList;

	}
	
	@SuppressWarnings("unchecked")
	public void deleteContractUpdateList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		this.contractInfoDao.deleteContractUpdateList(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List getNullContractUpdateList(HttpServletRequest request) {

		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		retrunList = contractInfoDao.getNullContractUpdateList(paramMap);

		return retrunList;

	}

	/**
	 * 合同变更数量 (Contract count inquires)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int getContractChangeCnt(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		return contractInfoDao.getContractChangeCnt(paramMap);
	}

	/**
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: 到期合同查询 ：HQ 法人管理者 查看所有法人的 到期合同信息
	 * @author yorio youjia@ait.net.cn
	 * @date Aug 23, 2013 6:55:28 PM
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getContractInfoListForSearchALL(HttpServletRequest request) {

		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpny_id = StringUtil.checkNull(paramMap.get("COMPANY_ID"), admin
				.getCpnyId());
		paramMap.put("CPNY_ID", cpny_id);

		int alertProbationDays = 0;
		int alertContractDays = 0;
		int alertHrCredentialDays = 0;
		int alertUpgradeDays = 0;
		int alertHealthListDays = 0;
		int alertNotContractDays = 0;
		paramMap.put("PARAM_NO", "('4542')");

		List<LinkedHashMap> alertDaysList = loginDao.getAlertDaysList(paramMap);
		if (alertDaysList != null) {
			for (LinkedHashMap getMap : alertDaysList) {
				// 到期合同
				if (getMap.get("PARAM_NO") != null
						&& !"".equals(getMap.get("PARAM_NO").toString())
						&& "4542".equals(getMap.get("PARAM_NO").toString())) {
					if (getMap.get("PARAM_VALUE") != null
							&& !"".equals(getMap.get("PARAM_VALUE").toString())) {
						alertContractDays = Integer.parseInt(getMap.get(
								"PARAM_VALUE").toString());// 合同到期天数
					}
				}
			}
		}
		paramMap.put("alertContractDays", alertContractDays);
		// paramMap.put("specialParam", admin.getSpecialParam());
		// paramMap.put("userNo", admin.getUserNo());
		// paramMap.put("deptNo", admin.getDeptNo());
		// paramMap.put("ADMIN_ID", admin.getAdminID());

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = contractInfoDao.getContractListForSearchALL(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = contractInfoDao.getContractListForSearchALL(paramMap);
		}

		return retrunList;

	}

	/**
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: 未签合同查询 ：HQ 法人管理者 查看所有法人的 未签合同信息
	 * @author yorio youjia@ait.net.cn
	 * @date Aug 23, 2013 6:55:28 PM
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getNOContractInfoList(HttpServletRequest request) {

		List retrunList = new ArrayList(); 

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		retrunList = contractInfoDao.getNOContractList(paramMap);

		return retrunList;

	}

	/**
	 * 合同查询导出 (Contract inquires)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractInfoListForSearchExcel(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		retrunList = contractInfoDao.getContractListForSearch(paramMap);
		return retrunList;

	}

	/**
	 * 合同查询导出 (Contract inquires)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractInfoListForSearchALLExcel(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpny_id = StringUtil.checkNull(paramMap.get("COMPANY_ID"), admin
				.getCpnyId());
		paramMap.put("CPNY_ID", cpny_id);
		// paramMap.put("specialParam", admin.getSpecialParam());
		// paramMap.put("userNo", admin.getUserNo());
		// paramMap.put("deptNo", admin.getDeptNo());
		// paramMap.put("ADMIN_ID", admin.getAdminID());

		retrunList = contractInfoDao.getContractListForSearchALL(paramMap);
		return retrunList;

	}

	/**
	 * 未签合同查询导出 (Contract inquires)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getNOContractInfoExcel(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpny_id = StringUtil.checkNull(paramMap.get("COMPANY_ID"), admin
				.getCpnyId());
		paramMap.put("CPNY_ID", cpny_id);
		// paramMap.put("specialParam", admin.getSpecialParam());
		// paramMap.put("userNo", admin.getUserNo());
		// paramMap.put("deptNo", admin.getDeptNo());
		// paramMap.put("ADMIN_ID", admin.getAdminID());

		retrunList = contractInfoDao.getNOContractList(paramMap);
		return retrunList;

	}

	/**
	 * 合同查询数量 (Contract count inquires)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int getContractCntForSearch(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		String EmpOffice = request.getParameter("seach_EmpOffice") == null ? "15119"
				: request.getParameter("seach_EmpOffice");

		paramMap.put("EmpOffice", EmpOffice);
		return contractInfoDao.getContractCntForSearch(paramMap);
	}

	/**
	 * 合同查询数量 (Contract count inquires)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int getContractCntForSearchALL(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		String cpny_id = StringUtil.checkNull(paramMap.get("COMPANY_ID"), admin
				.getCpnyId());
		paramMap.put("CPNY_ID", cpny_id);
		int alertProbationDays = 0;
		int alertContractDays = 0;
		int alertHrCredentialDays = 0;
		int alertUpgradeDays = 0;
		int alertHealthListDays = 0;
		int alertNotContractDays = 0;
		paramMap.put("PARAM_NO", "('4542')");

		List<LinkedHashMap> alertDaysList = loginDao.getAlertDaysList(paramMap);
		if (alertDaysList != null) {
			for (LinkedHashMap getMap : alertDaysList) {
				// 到期合同
				if (getMap.get("PARAM_NO") != null
						&& !"".equals(getMap.get("PARAM_NO").toString())
						&& "4542".equals(getMap.get("PARAM_NO").toString())) {
					if (getMap.get("PARAM_VALUE") != null
							&& !"".equals(getMap.get("PARAM_VALUE").toString())) {
						alertContractDays = Integer.parseInt(getMap.get(
								"PARAM_VALUE").toString());// 合同到期天数
					}
				}
			}
		}
		paramMap.put("alertContractDays", alertContractDays);
		// paramMap.put("specialParam", admin.getSpecialParam());
		// paramMap.put("userNo", admin.getUserNo());
		// paramMap.put("deptNo", admin.getDeptNo());
		// paramMap.put("ADMIN_ID", admin.getAdminID());
		return contractInfoDao.getContractCntForSearchALL(paramMap);
	}

	/**
	 * 未签合同查询数量 (Contract count inquires)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int getNOContractCnt(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		String cpny_id = StringUtil.checkNull(paramMap.get("COMPANY_ID"), admin
				.getCpnyId());
		paramMap.put("CPNY_ID", cpny_id);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());

		return contractInfoDao.getNOContractCnt(paramMap);
	}

	/**
	 * 签订合同(sign a contract)
	 * 
	 * @param request
	 * @return List
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getContractByInsertForGrid(HttpServletRequest request) {

		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = contractInfoDao.getContractByInsertForGrid(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = contractInfoDao.getContractByInsertForGrid(paramMap);
		}

		return retrunList;

	}

	/**
	 * 签订契约Excel导出
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getContractByInsertForSearchExcel(HttpServletRequest request)
			throws Exception {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		retrunList = contractInfoDao.getContractByInsertForGrid(paramMap);

		return retrunList;
	}

	/**
	 * 执行添加签订合同(Executive add sign the contract)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int updateContract(HttpServletRequest request) {
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			paramMap.put("CREATED_BY", admin.getAdminID());
			this.contractInfoDao.saveNewContract(paramMap);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 提交转正评价
	 * 
	 * @param request
	 * @return int
	 */

	@SuppressWarnings("unchecked")
	@Override
	public int insertBecomeRegularEvaluate(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.contractInfoDao.insertBecomeRegularEvaluate(dataList);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 签订合同数量查询 (Sign a contract number query)
	 * 
	 * @param request
	 * @return int
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getContractByInsertCntForSearch(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		return contractInfoDao.getContractByInsertCntForSearch(paramMap);

	}

	/**
	 * 续签合同(Contract Renewal)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getRenewContractForGrid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		if (request.getParameter("PERSON_ID") != null) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		String deptno = StringUtil
		.checkNull(request.getParameter("seach_DEPTNO"));
		
		String str = "";
		if (!"".equals(deptno)) {
			str = " and B.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
				+ deptno + "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
		
			paramMap.put("deptContent", str);
		}
		String sql = "SELECT B.LOCAL_NAME NAME,B.EMPID EMPID,GET_DEPT_NAME(B.DEPTNO, 'vi') DEPARTMENT,get_global_name(B.POST_GRADE_NO,'vi') RANK,get_global_name(B.POSITION_NO,'vi') DUTY,TO_CHAR(B.DATE_STARTED,'dd/MM/yyyy') ENTRY_DATE,get_global_name(C.CONTRACT_TYPE_CODE, 'vi') CONTRACT_TYPE,TO_CHAR(C.START_CONTRACT_DATE, 'dd/MM/yyyy') START_CONTRACT_DATE,TO_CHAR(C.END_CONTRACT_DATE, 'dd/MM/yyyy') END_CONTRACT_DATE,get_global_name(C.WORK_HOUR_TYPE,'vi') WORKING_HOURS from HR_EMPLOYEE B,HR_PERSONAL_INFO HPI,(SELECT CONTRACT_NO,TOTAL_PERIOD,HRC.PERSON_ID,CONTRACT_TYPE_CODE,HRC.WORK_HOUR_TYPE,CONTRACT_TYPE,REMARK,HRC.START_CONTRACT_DATE,HRC.END_CONTRACT_DATE,ROW_NUMBER() OVER(PARTITION BY HRC.PERSON_ID ORDER BY HRC.START_CONTRACT_DATE DESC) CONCOUNT FROM HR_CONTRACT HRC WHERE HRC.ACTIVITY = 1) C WHERE C.PERSON_ID = B.PERSON_ID AND B.PERSON_ID = HPI.PERSON_ID AND C.CONCOUNT = 1 AND B.EMPID NOT LIKE '111111%' AND B.EMP_OFFICE <> '15120' AND C.END_CONTRACT_DATE IS NOT NULL AND ((C.CONTRACT_TYPE_CODE = '14014302' AND C.END_CONTRACT_DATE <= SYSDATE + 7) OR (C.CONTRACT_TYPE_CODE = '123204' AND C.END_CONTRACT_DATE <= SYSDATE + 14) OR (C.CONTRACT_TYPE_CODE NOT IN ('123204','14014302') AND C.END_CONTRACT_DATE <= ADD_MONTHS(SYSDATE,1))) AND B.CPNY_ID ='" 
        	 + admin.getCpnyId() + "'  ";
		String personid = "";
		if (request.getParameter("PERSON_ID") != null
				&& !"".equals(request.getParameter("PERSON_ID"))) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			personid = " and B.person_id='" + request.getParameter("PERSON_ID")
					+ "'";
		}
		String employee_owned = "";
		if (request.getParameter("EMPLOYEE_OWNED") != null
				&& !"".equals(request.getParameter("EMPLOYEE_OWNED"))) {
			employee_owned = " and B.EMPLOYEE_OWNED in ("
					+ request.getParameter("EMPLOYEE_OWNED") + ")";
		}
		
		String contract_type_code = "";
		if (request.getParameter("seach_CONTRACT_TYPE_CODE") != null
				&& !"".equals(request.getParameter("seach_CONTRACT_TYPE_CODE"))) {
			contract_type_code = " and CASE WHEN TOTAL_PERIOD = '1' THEN '14014303' WHEN TOTAL_PERIOD = '2' THEN '123203' WHEN TOTAL_PERIOD > '2' THEN '14014305' END = ("
					+ request.getParameter("seach_CONTRACT_TYPE_CODE") + ")";
		}
		
		String date_started = "";
		if (request.getParameter("seach_S_END_DATE") != null
				&& !"".equals(request.getParameter("seach_S_END_DATE"))) {
			date_started = " and C.END_CONTRACT_DATE>=to_date('"
					+ request.getParameter("seach_S_END_DATE") + "','dd/MM/yyyy')";
		}
		String end_started = "";
		if (request.getParameter("seach_E_END_DATE") != null
				&& !"".equals(request.getParameter("seach_E_END_DATE"))) {
			end_started = " and C.END_CONTRACT_DATE<=to_date('"
					+ request.getParameter("seach_E_END_DATE") + "','dd/MM/yyyy')";
		}
		
		
		sql = sql + personid + str + employee_owned + contract_type_code + date_started + end_started + "ORDER　BY C.END_CONTRACT_DATE DESC,C.person_id";
		
		paramMap.put("SQL_STMT", sql);
		paramMap.put("sql_seq", "16");
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
		
		return contractInfoDao.getRenewContractForGrid(paramMap);
	}
	
	/**
	 * 续签契约Excel导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getExpiredIdCardList(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		retrunList = contractInfoDao.getExpiredIdCardList(paramMap);

		return retrunList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPaNotImportList(HttpServletRequest request) throws Exception {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		retrunList = contractInfoDao.getPaNotImportList(paramMap);

		return retrunList;
	}
	
	
	/**
	 * 转正提醒
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getBecomeRegularWarn(HttpServletRequest request) {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		retrunList = contractInfoDao.getBecomeRegularWarnList(paramMap);

		return retrunList;
	}
	
	/**
	 * 续签契约Excel导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getExpiredContractForSearchExcel(HttpServletRequest request)
			throws Exception {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		paramMap.put("PARAM_NO", "15713");

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		try {
			String paramValue = contractInfoDao.getParamVale(paramMap);
			if (paramValue == null || "".equals(paramValue)
					|| paramValue.length() <= 0) {
				paramMap.put("PARAM_VALUE", "30");
			} else {
				paramMap.put("PARAM_VALUE", paramValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// retrunList = contractInfoDao.getContractListForSearch(paramMap) ;
		retrunList = contractInfoDao.getRenewContractForGrid(paramMap);

		return retrunList;
	}

	/**
	 * 续签合同数量查询 (Current contract number query)
	 * 
	 * @param request
	 * @return int
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getRenewContractCntForGrid(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		return contractInfoDao.getRenewContractCntForGrid(paramMap);

	}

	/**
	 * 续签合同数量查询 (Current contract number query)
	 * 
	 * @param request
	 * @return int
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int expiredContractApproveCnt(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		return contractInfoDao.expiredContractApproveCnt(paramMap);

	}

	/**
	 * 执行添加续签合同(Executive add sign the contract)
	 * 
	 * @Description: TODO
	 * @param request
	 * @author weizhengchen
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateRenewContractByInsert(HttpServletRequest request) {
		try {
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			this.contractInfoDao.updateRenewContractByInsert(request, paramMap);// 插入续签合同信息
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 签订合同
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2013-10-14 下午5:21:33
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int insertContract(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.contractInfoDao.insertContract(dataList);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonalInfoForContract(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID", request.getParameter("NO"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return contractInfoDao.getPersonalInfoForContract(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getContractForUpdate(HttpServletRequest request) {
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CONTRACT_NO", request.getParameter("CONTRACT_NO"));
		if (request.getParameter("LANGUAGE") == null
				|| "".equals(request.getParameter("LANGUAGE"))) {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("interLanguage", admin.getLanguage());
		} else {
			paramMap.put("interLanguage", request.getParameter("LANGUAGE"));
		}
		return contractInfoDao.getContractForUpdate(paramMap);
	}

	/**
	 * 执行修改合同(update contract)
	 * 
	 * @Description: TODO
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateContractInfo(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.contractInfoDao.updateContractInfo(dataList);// 插入变更合同信息
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 执行修改合同(update contract)
	 * 
	 * @Description: TODO
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateContractInfo1(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.contractInfoDao.updateContractInfo1(dataList);// 插入变更合同信息
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int addContractInfo1(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		this.contractInfoDao.addContractInfo1(paramMapo);
		return 1;
	}
	
	/**
	 * 批量删除考勤
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteContractInfo1(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			this.contractInfoDao.deleteContractInfo1(this.encapsulationApplyNoListForBatch(request,"hr0307Check"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量封装信息申请信息成List(encapsulation information apply from request to List for
	 * batch)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List encapsulationApplyNoListForBatch(HttpServletRequest request,String type) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues(type);
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("UPDATED_IP", admin.getAdminIP());
				map.put("CONTRACT_NO", paramData[i]);
				map.put("CPNY_ID", admin.getCpnyId());
				list.add(map);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询详细人力区分类型
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getParticularHumanDistinguishList(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.contractInfoDao.getParticularHumanDistinguishList(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCodeList(String parentCodeNo, HttpServletRequest request)
			throws Exception {

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PARENT_CODE_NO", parentCodeNo);

		return this.contractInfoDao.getCodeList(paramMap);
	}

	@Override
	public List getRenLiAndQiYueList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		// paramMap.put("EMP_TYPE_CODE", request.getParameter("EMP_TYPE_CODE"));
		// paramMap.put("CPNY_ID", request.getParameter("NO"));
		// paramMap.put("CPNY_ID", request.getParameter("NO"));

		return contractInfoDao.getRenLiAndQiYueList(paramMap);
	}

	/**
	 * 续签审批
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List expiredContractApprove(HttpServletRequest request) {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("PERSON_ID", admin.getAdminID());

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = contractInfoDao.expiredContractApprove(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = contractInfoDao.expiredContractApprove(paramMap);
		}

		return retrunList;
	}

	/**
	 * 续签决裁
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author weizhengchen@ait.net.cn
	 * @date 2013-10-14 下午5:21:33
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int approveExpiredContract(HttpServletRequest request) {
		try {
			String personid = request.getParameter("AFFIRMOR_ID");
			if (personid == null || "".equals(personid)) {
				AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				personid = admin.getAdminID();
			}
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamDataNoSession(request);
			if (request.getParameter("LANGUAGE") == null
					|| "".equals(request.getParameter("LANGUAGE"))) {
				AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				paramMap.put("interLanguage", admin.getLanguage());
			} else {
				paramMap.put("interLanguage", request.getParameter("LANGUAGE"));
			}
			this.addNewAffirmorList(request);
			String[] isChecked = request.getParameterValues("hr0306Check");
			paramMap.put("FLAG", paramMap.get("FLAG"));
			paramMap.put("UPDATED_BY", personid);
			paramMap.put("PERSON_ID", personid);
			for (int i = 0; i < isChecked.length; i++) {
				paramMap.put("CONTRACT_NO", paramMap.get("CONTRACT_NO" + "_"
						+ isChecked[i]));
				paramMap.put("APPLY_NO", paramMap.get("CONTRACT_NO" + "_"
						+ isChecked[i]));
				paramMap.put("AFFIRM_CONTENT", paramMap.get("AFFIRM_CONTENT"
						+ "_" + isChecked[i]));
				paramMap.put("ESS_AFFIRM_NO", paramMap.get("ESS_AFFIRM_NO"
						+ "_" + isChecked[i]));
				paramMap.put("AFFIRM_LEVEL_CURRENT", paramMap
						.get("AFFIRM_LEVEL_CURRENT" + "_" + isChecked[i]));
				paramMap.put("CURRENT_AFFIRM_ID", "");
				paramMap.put("APPLY_TYPE", APPLY_TYPE_NO);
				this.contractInfoDao.affirmContract(paramMap);
				if (!"2".equals(paramMap.get("FLAG"))) {// 否决直接修改标志位,通过继续下面操作
					// 获取未决裁者列表
					paramMap.put("ACTIVITY", "0");
					List<LinkedHashMap> affirmList = this.contractInfoDao
							.getEssAffirmList(paramMap);
					if (affirmList != null && affirmList.size() > 0) {
						for (LinkedHashMap parmers : affirmList) {
							paramMap.put("CURRENT_AFFIRM_ID", parmers
									.get("AFFIRMOR_ID"));
							paramMap.put("FLAG", "0");
							paramMap.put("AFFIRM_LEVEL", parmers
									.get("AFFIRM_LEVEL"));
							break;
						}
					}
				}
				this.contractInfoDao.approveExpiredContract(paramMap);
				this.sendToLGEP(paramMap);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取决裁情况
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmorListByContractNo(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("EVENT_ID", request.getParameter("CONTRACT_NO"));
		if (request.getParameter("LANGUAGE") == null
				|| "".equals(request.getParameter("LANGUAGE"))) {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("LANGUAGE", admin.getLanguage());
		} else {
			paramMap.put("LANGUAGE", request.getParameter("LANGUAGE"));
		}
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		retrunList = paTempSalesDAO.getAffirmorList(paramMap);
		return retrunList;
	}

	/**
	 * 获取check信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getCheckListByContractNo(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("EVENT_ID", request.getParameter("CONTRACT_NO"));
		if (request.getParameter("LANGUAGE") == null
				|| "".equals(request.getParameter("LANGUAGE"))) {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("LANGUAGE", admin.getLanguage());
		} else {
			paramMap.put("LANGUAGE", request.getParameter("LANGUAGE"));
		}
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		retrunList = paTempSalesDAO.getCheckList(paramMap);
		return retrunList;
	}

	/**
	 * 提交后发送LGEP
	 * 
	 * @param eventId
	 */
	private void sendToLGEPInsert(LinkedHashMap paramMap, String title) {
		LinkedHashMap lgepMap = new LinkedHashMap();
		lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
		lgepMap.put("APPLY_NO", paramMap.get("HR_CONTRACT_SEQ"));
		lgepMap.put("APPLY_TYPE_NAME", title);
		lgepMap.put("APPLY_TITLE", title);
		lgepMap.put("APPLY_EMPID", paramMap.get("PERSON_ID"));
		lgepMap
				.put(
						"ARI_URL",
						"http://{serverIp}/LGEP/affirm/viewContractAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&CONTRACT_NO="
								+ paramMap.get("HR_CONTRACT_SEQ"));
		lgepMap
				.put(
						"ABY_URL",
						"http://{serverIp}/LGEP/affirm/viewContractAffirm?LGEP=LGEP&LANGUAGE=zh&personId="
								+ paramMap.get("CURRENT_AFFIRM_ID")
								+ "&CONTRACT_NO="
								+ paramMap.get("HR_CONTRACT_SEQ"));
		lgepMap.put("AFFIRM_LEVEL", "1");
		lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
		lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
		this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}

	/**
	 * 审批后发送LGEP
	 * 
	 * @param eventId
	 */
	private void sendToLGEP(LinkedHashMap paramMap) {
		LinkedHashMap lgepMap = new LinkedHashMap();
		lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
		lgepMap.put("APPLY_NO", paramMap.get("CONTRACT_NO"));
		if ("0".equals(paramMap.get("FLAG").toString())) {
			lgepMap.put("AFFIRM_LEVEL", paramMap.get("AFFIRM_LEVEL"));
			lgepMap.put("AFFIRM_FLAG", 1);
		} else {// 决裁完成
			lgepMap.put("FINISH", "FINISH");
			lgepMap.put("AFFIRM_FLAG", paramMap.get("FLAG"));
			lgepMap.put("AFFIRM_LEVEL", paramMap.get("AFFIRM_LEVEL_CURRENT"));
		}
		lgepMap.put("CREATED_BY", paramMap.get("APPLY_PERSON_ID"));
		lgepMap.put("AFFIRM_EMPID", paramMap.get("PERSON_ID"));
		lgepMap
				.put(
						"AAI_URL",
						"http://{serverIp}/LGEP/affirm/viewContractAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&CONTRACT_NO="
								+ paramMap.get("CONTRACT_NO").toString());
		lgepMap
				.put(
						"AAF_URL",
						"http://{serverIp}/LGEP/affirm/viewContractAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&CONTRACT_NO="
								+ paramMap.get("CONTRACT_NO").toString());
		lgepMap
				.put(
						"ABY_URL",
						"http://{serverIp}/LGEP/affirm/viewContractAffirm?LGEP=LGEP&LANGUAGE=zh&personId="
								+ paramMap.get("CURRENT_AFFIRM_ID")
								+ "&CONTRACT_NO="
								+ paramMap.get("CONTRACT_NO").toString());
		lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
		lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
		this.affirmInfoToLGEPSer.affirm(lgepMap);
	}

	/**
	 * 检测合同编号是否重复
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int checkContractNo(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return contractInfoDao.checkContractNo(paramMap);
	}

	/**
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private int addNewAffirmorList(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil
				.getRequestParamDataNoSession(request);
		int result = 1;
		List addAffirmList = new ArrayList();
		List personList = new ArrayList();
		Enumeration e = request.getParameterNames();
		// 获取页面添加的决裁者的决裁等级（页面等级）
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			if (key.indexOf("dwz.person.personId") > -1) {
				String dwzName = "dwz.person.personId";
				if (key.equals("dwz.person.personId")) {
					personList.add(0);
				} else {
					personList.add(Integer.parseInt(key.substring(dwzName
							.length())));
				}
			}
		}
		// 对页面获取的决裁者信息进行排序（按照页面决裁等级排序）
		Collections.sort(personList);
		for (int i = 0; i < personList.size(); i++) {
			LinkedHashMap affirmMap = new LinkedHashMap();
			String keyName = "";
			String affirmLevel = personList.get(i).toString();
			if (affirmLevel.equals(0)) {
				keyName = "dwz.person.personId";
			} else {
				keyName = "dwz.person.personId" + affirmLevel;
			}
			// 获取添加的决裁者的person_id
			String personId = paramMap.get(keyName).toString();
			affirmMap.put("AFFIRMOR_ID", personId);
			if (personId != null && !"".equals(personId)) {
				affirmMap.put("APPLY_NO", paramMap.get("CONTRACT_NO_0")
						.toString());
				affirmMap.put("APPLY_TYPE", APPLY_TYPE_NO);
				affirmMap.put("AFFIRM_LEVEL", affirmLevel);
				affirmMap.put("CREATED_BY", paramMap.get("AFFIRMOR_ID"));
				affirmMap.put("UPDATED_BY", paramMap.get("AFFIRMOR_ID"));
				addAffirmList.add(affirmMap);
			}
		}
		LinkedHashMap addAffirmorMap = new LinkedHashMap();
		int affirmorCnt = 0;
		int affirmLeave = 0;
		for (int j = 0; j < addAffirmList.size(); j++) {
			addAffirmorMap = (LinkedHashMap) addAffirmList.get(j);
			if (j == 0) {
				affirmLeave = Integer.parseInt(addAffirmorMap.get(
						"AFFIRM_LEVEL").toString());
			}
			affirmorCnt = this.affirmApplyDao
					.getAffirmorCntByApplyNo(addAffirmorMap);
			if (affirmorCnt == 0) {
				addAffirmorMap.put("AFFIRM_LEVEL", affirmLeave);
				this.affirmApplyDao.addNewApplyAffirmor(addAffirmorMap);
				affirmLeave = affirmLeave + 1;
			}
		}

		return result;
	}

	/**
	 * 组装未签合同信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getNotSignContractInfo(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT,'' CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		// 模版名称
		String name = "";
		aliasNameList.add("社号");
		aliasNameList.add("姓名");
		aliasNameList.add("部门");
		aliasNameList.add("工作地区");
		aliasNameList.add("职位");
		aliasNameList.add("职责");
		aliasNameList.add("人员类型");
		aliasNameList.add("试用期月数");
		aliasNameList.add("试用期比例");
		aliasNameList.add("合同编号");
		aliasNameList.add("合同类型");
		aliasNameList.add("合同版本");
		aliasNameList.add("起始日期");
		aliasNameList.add("终止日期");
		aliasNameList.add("备注");

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		String cpny_id = StringUtil.checkNull(paramMap.get("COMPANY_ID"), admin
				.getCpnyId());
		paramMap.put("CPNY_ID", cpny_id);
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		List retrunList = contractInfoDao.getNOContractList(paramMap);
		for (int i = 0; i < retrunList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap) retrunList.get(i);
			map.put("CELL0", StringUtil.checkNull(map1.get("EMPID")));
			map.put("CELL1", StringUtil.checkNull(map1.get("LOCAL_NAME")));
			map.put("CELL2", StringUtil.checkNull(map1.get("DEPARTMENT_NAME")));
			map.put("CELL3", StringUtil.checkNull(map1.get("WORK_AREA_NAME")));
			map.put("CELL4", StringUtil.checkNull(map1.get("DUTY_NAME")));
			map.put("CELL5", StringUtil.checkNull(map1.get("POSITION_NAME")));
			map.put("CELL6", StringUtil.checkNull(map1.get("EMP_TYPE_NAME")));
			map.put("CELL7", StringUtil.checkNull(map1.get("MONTH_TOTAL")));
			map.put("CELL8", StringUtil.checkNull(map1.get("PROB_PAY_RAT")));
			map.put("CELL9", StringUtil.checkNull(map1.get("EMPID")) + "_1");
			map.put("CELL10", "固定期限劳动合同");
			map.put("CELL11", "");
			map.put("CELL12", StringUtil.checkNull(map1.get("DATE_STARTED")));
			map.put("CELL13", StringUtil.checkNull(map1.get("DATE_END")));
			map.put("CELL14", " ");
			list.add(map);
		}

		mapNameList.add("合同类型参考");
		mapList
				.add("SELECT NVL(U.CONTENT, ' ') CONTENT,'' CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U,SY_CODE_PARAM SY WHERE T.CODE_NO = SY.CODE_NO AND SY.CPNY_ID = '"
						+ admin.getCpnyId()
						+ "' AND T.ACTIVITY = 1 AND T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 1649");
		name = "tempNotSignContractInfo";
		return name;
	}

	/**
	 * 组装续签合同信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getContractInfo(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT,'' CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		// 模版名称
		String name = "";
		aliasNameList.add("社号");
		aliasNameList.add("姓名");
		aliasNameList.add("工作地区");
		aliasNameList.add("08年后合同次数");
		aliasNameList.add("合同编号");
		aliasNameList.add("合同类型");
		aliasNameList.add("合同版本");
		aliasNameList.add("起始日期");
		aliasNameList.add("终止日期");
		aliasNameList.add("备注");

		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "11111111");
		map.put("CELL1", "王某某");
		map.put("CELL2", "北京");
		map.put("CELL3", "2");
		map.put("CELL4", "11111111_2");
		map.put("CELL5", "固定期限劳动合同");
		map.put("CELL6", "2014");
		map.put("CELL7", "2014/07/07");
		map.put("CELL8", "2017/07/06");
		map.put("CELL9", "OK");
		map.put("CELL10", " ");
		list.add(map);

		mapNameList.add("合同类型参考");
		mapList
				.add("SELECT NVL(U.CONTENT, ' ') CONTENT,'' CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U,SY_CODE_PARAM SY WHERE T.CODE_NO = SY.CODE_NO AND SY.CPNY_ID = '"
						+ admin.getCpnyId()
						+ "' AND T.ACTIVITY = 1 AND T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 1649");
		name = "tempContractInfo";
		return name;
	}

	/**
	 * 组装未签合同信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getNotSignContractTempInfo(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT,'' CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		// 模版名称
		String name = "";
		aliasNameList.add("社号");
		aliasNameList.add("姓名");
		aliasNameList.add("部门");
		aliasNameList.add("工作地区");
		aliasNameList.add("职位");
		aliasNameList.add("职责");
		aliasNameList.add("人员类型");
		aliasNameList.add("试用期月数");
		aliasNameList.add("试用期比例");
		aliasNameList.add("合同编号");
		aliasNameList.add("合同类型");
		aliasNameList.add("合同版本");
		aliasNameList.add("起始日期");
		aliasNameList.add("终止日期");
		aliasNameList.add("备注");
		aliasNameList.add("验证结果");

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		List retrunList = contractInfoDao.getContractTempList(paramMap);
		for (int i = 0; i < retrunList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap) retrunList.get(i);
			map.put("CELL0", StringUtil.checkNull(map1.get("EMPID")));
			map.put("CELL1", StringUtil.checkNull(map1.get("LOCAL_NAME")));
			map.put("CELL2", StringUtil.checkNull(map1.get("DEPT_NAME")));
			map.put("CELL3", StringUtil.checkNull(map1.get("WORK_AREA_NAME")));
			map.put("CELL4", StringUtil.checkNull(map1.get("DUTY_NO")));
			map.put("CELL5", StringUtil.checkNull(map1.get("POSITION_NO")));
			map.put("CELL6", StringUtil.checkNull(map1.get("EMP_TYPE_NAME")));
			map.put("CELL7", StringUtil.checkNull(map1.get("MONTH_TOTAL")));
			map.put("CELL8", StringUtil.checkNull(map1.get("PROB_PAY_RAT")));
			map.put("CELL9", StringUtil.checkNull(map1.get("CONTRACT_NUMBER")));
			map.put("CELL10", StringUtil.checkNull(map1.get("CONTRACT_TYPE")));
			map.put("CELL11", StringUtil
					.checkNull(map1.get("CONTRACT_VERSION")));
			map.put("CELL12", StringUtil.checkNull(map1
					.get("START_CONTRACT_DATE")));
			map.put("CELL13", StringUtil.checkNull(map1
					.get("END_CONTRACT_DATE")));
			map.put("CELL14", StringUtil.checkNull(map1.get("REMARK")));
			map.put("CELL14", StringUtil
					.checkNull(map1.get("UPLOAD_ERROR_MSG")));
			list.add(map);
		}

		mapNameList.add("合同类型参考");
		mapList
				.add("SELECT NVL(U.CONTENT, ' ') CONTENT,'' CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U,SY_CODE_PARAM SY WHERE T.CODE_NO = SY.CODE_NO AND SY.CPNY_ID = '"
						+ admin.getCpnyId()
						+ "' AND T.ACTIVITY = 1 AND T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 1649");
		name = "tempNotSignContractInfo";
		return name;
	}

	/**
	 * 获取合同导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getContractTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = contractInfoDao.getContractTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = contractInfoDao.getContractTempList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 获取合同导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getContractTempCnt(HttpServletRequest request, String errorFlag) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if ("E".equals(errorFlag)) {
			retrunInt = contractInfoDao.getContractTempErrorCnt(paramMap);
		} else {
			retrunInt = contractInfoDao.getContractTempCnt(paramMap);
		}

		return retrunInt;
	}

	/**
	 * 未签合同excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelContractData(HttpServletRequest request) {

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME",
				"PKG_CONTRACT_EXCEL_IMP.PR_IMPORT_CONTRACT_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 续签合同excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelContractData2(HttpServletRequest request) {

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME",
				"PKG_CONTRACT_EXCEL_IMP.PR_IMPORT_EXP_CONTRACT_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 合同担当查询 (Contract inquires)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractManagerList() {

		List retrunList = contractInfoDao.getContractManagerList();

		return retrunList;

	}

	/**
	 * 需要续签的合同信息查询 (Contract inquires)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractRemindList() {

		List retrunList = contractInfoDao.getContractRemindList();

		return retrunList;

	}
	
	
	
	/**
	 * 合同查询 修改(Contract inquires)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int updateContractInfoForUpdate(HttpServletRequest request) {

	 
		String jsonString = request.getParameter("jsonData") ;
		List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;

		 

		return  contractInfoDao.updateContractInfoForUpdate(dataList);

	}
	/**
	 * 合同变更履历查询 (Contract inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List viewChangeContractHistoryList(HttpServletRequest request) {

		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		retrunList = contractInfoDao.viewChangeContractHistoryList(paramMap);

		return retrunList;

	}
}
