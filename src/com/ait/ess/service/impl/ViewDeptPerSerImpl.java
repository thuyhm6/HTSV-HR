package com.ait.ess.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.ait.ess.dao.ViewDeptPerDao;
import com.ait.ess.service.ViewDeptPerSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 *部门员工信息查询(dept employee Info view) Copyright: LDCC Company: LDCC
 * 
 * @fileName: ViewApplySerImpl.java
 * @Description:
 * @Create date: Feb 10, 2012 2:47:58 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 10, 2012 2:47:58 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Service
public class ViewDeptPerSerImpl implements ViewDeptPerSer {

	Logger logger = Logger.getLogger(ViewDeptPerSerImpl.class);

	@Autowired
	private ViewDeptPerDao ViewDeptPerDao;

	@Autowired
	private AuthorityUtil authorityUtil;

	/**
	 * 部门员工信息查询,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");

		// LinkedHashMap paramMap =
		// getLinkedMapByRequestForSearch(request,"seach_");
		// KEY=, GROUP_NO=, DEPT_NO=C025
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		/*
		 * if (UiUtil.getPageNum(request) > 0) { returnList =
		 * ViewDeptPerDao.getPersonList(paramMap, UiUtil.getPageNum(request),
		 * UiUtil.getNumPerPage(request)); } else { returnList =
		 * ViewDeptPerDao.getPersonList(paramMap); }
		 */
		// }
		// }

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			returnList = ViewDeptPerDao.getPersonList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = ViewDeptPerDao.getPersonList(paramMap);
		}
		/*
		 * List list = new ArrayList(); LinkedHashMap lMap = new
		 * LinkedHashMap(); LinkedHashMap searchAnLeave = paramMap;
		 * LinkedHashMap searchSurpLeave = paramMap; for (int i = 0; i <
		 * returnList.size(); i++) { searchAnLeave = (LinkedHashMap)
		 * returnList.get(i); searchAnLeave.put("EMPID",admin.getPersonId());
		 * searchAnLeave.put("PERSON_ID", admin.getPersonId());
		 * 
		 * Date date = new Date(); SimpleDateFormat sb = new
		 * SimpleDateFormat("yyyyMM"); int dateInt =
		 * Integer.parseInt(sb.format(date)); String yearAndMonth =
		 * String.valueOf(dateInt); String year = yearAndMonth.substring(0, 4);
		 * 
		 * //lMap.put("REST_ANNUAL_LEAVE",
		 * this.retrieveVacationEmpREST(searchAnLeave, admin)); //2013-12-27
		 * lufeng 修改 //2014/08/11 其中函数报错，未知是否需要查询剩余年假，先注释了 by:wangqiang
		 * lMap.put("REST_ANNUAL_LEAVE", this.getVacationEmpREST(searchAnLeave,
		 * admin)); lMap.put("REST_ANNUAL_LEAVEQN",
		 * this.retrieveVacationEmpRESTQN(searchAnLeave, admin));
		 * searchAnLeave.put("YEAR_NONTH", yearAndMonth);
		 * searchAnLeave.put("YEAR", year);
		 * searchAnLeave.put("CPNY_ID",admin.getCpnyId());
		 * searchAnLeave.put("PERSON_ID", paramMap.get("PERSON_ID"));
		 * //lMap.put("SUR_ADJUST_REST",
		 * this.ViewDeptPerDao.getSurplusAdjustRest(searchSurpLeave));
		 * //2013-12-11 lufeng 修改 //2014/08/11 其中函数报错，未知是否需要查询剩余年假，先注释了
		 * by:wangqiang lMap.put("SUR_ADJUST_REST",
		 * this.ViewDeptPerDao.getAdjustRestNew(searchSurpLeave));
		 * 
		 * list.add(searchAnLeave); }
		 */
		return returnList;

	}

	/**
	 * 部门员工信息查询,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonManageList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("seach_KEY", request.getParameter("seach_KEY"));
		paramMap.put("EMP_OFFICE", request.getParameter("seach_EMP_OFFICE"));
		paramMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE"));
		//if (UiUtil.getPageNum(request) > 0) {
		//	returnList = ViewDeptPerDao.getPersonManageList(paramMap, UiUtil
		//			.getPageNum(request), UiUtil.getNumPerPage(request));
		//} else {
			returnList = ViewDeptPerDao.getPersonManageList(paramMap);
		//}

		return returnList;

	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List getPersonListOt(HttpServletRequest request) { List
	 * retrunList = new ArrayList() ; AdminBean admin =
	 * SessionUtil.getLoginUserFromSession(request); Map paramMap =
	 * ObjectBindUtil.getRequestParamData(request) ; paramMap.put("CPNY_ID",
	 * admin.getCpnyId()); paramMap.put("specialParam",admin.getSpecialParam());
	 * paramMap.put("deptNo",admin.getDeptNo()); paramMap.put("userNo",
	 * admin.getUserNo()); paramMap.put("ADMIN_ID", admin.getPersonId()); String
	 * empNameStr=request.getParameter("LOCAL_NAME"); try {
	 * paramMap.put("LOCAL_NAME",
	 * java.net.URLDecoder.decode(empNameStr,"UTF-8")); } catch
	 * (UnsupportedEncodingException e) { e.printStackTrace(); } if
	 * (UiUtil.getPageNum(request) > 0){ retrunList =null ; }else{ retrunList=
	 * ViewDeptPerDao.getPersonListOt(paramMap) ; } return retrunList ; }
	 */
	/**
	 * 分页单位(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPersonListCnt(HttpServletRequest request) throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.getPersonListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getPersonManageListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.getPersonManageListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	/**
	 * 通过request请求封装查询条件(get search conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequestForSearch(
			HttpServletRequest request, String flag) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				flag);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}

	/**
	 * 通过request请求封装查询条件 manage(get search conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequestForManageSearch(
			HttpServletRequest request, String flag) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				flag);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return paramMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("EMPID", request.getParameter("EMPID"));
		return this.ViewDeptPerDao.getPersonInfo(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonInfo2(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("EMPID", request.getParameter("EMPID"));
		return this.ViewDeptPerDao.getPersonInfo2(paramMap);
	}

	/**
	 * 查看动态组列表(get DynamicGroup List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getDynamicGroup1List(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("DEPT_DISTINGUISH_NO", "1");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = ViewDeptPerDao.getDynamicGroupList(paramMap);
		return retrunList;
	}

	/**
	 * 单个考勤明细(get DynamicGroup List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List viewArPersonalSingleList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		/*if ("".equals(paramMap.get("DEPT_NO")) || paramMap.get("DEPT_NO") == null) {
			paramMap.put("DEPT_NO", request.getParameter("seach_DEPT_NO"));
		}*/
		String PERSON_ID = request.getParameter("PERSON_ID");
		if (PERSON_ID != null && PERSON_ID != "") {
			String P_ID[] = PERSON_ID.split(",");
			String PERSON_ID_ID = "";
			for (int i = 0; i < P_ID.length; i++) {
				if (P_ID[i] != null && P_ID[i] != "") {
					if (i == 0) {
						PERSON_ID_ID = "'" + P_ID[i] + "'";
					} else {
						PERSON_ID_ID += ",'" + P_ID[i] + "'";
					}
				}
			}
			if (P_ID.length == 0) {
				PERSON_ID_ID = "''";
			}
			paramMap.put("PERSON_ID_THIS", PERSON_ID_ID);
		}
		
		
		// 考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "ArUser");
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		retrunList = ViewDeptPerDao.viewArPersonalSingleList(paramMap);
		return retrunList;
	}

	
	//部门汇总
	
	public List viewArSummarySingleList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		retrunList = ViewDeptPerDao.viewArSummarySingleList(paramMap);
		return retrunList;
	}

	
	
	
	/**
	 *单个待条件加班明细(get DynamicGroup List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List viewOtApplySingleList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String PERSON_ID = request.getParameter("PERSON_ID");
		if (PERSON_ID != null && PERSON_ID != "") {
			String P_ID[] = PERSON_ID.split(",");
			String PERSON_ID_ID = "";
			for (int i = 0; i < P_ID.length; i++) {
				if (P_ID[i] != null && P_ID[i] != "") {
					if (i == 0) {
						PERSON_ID_ID = "'" + P_ID[i] + "'";

					} else {
						PERSON_ID_ID += ",'" + P_ID[i] + "'";
					}
				}

			}
			if (P_ID.length == 0) {

				PERSON_ID_ID = "''";
			}

			paramMap.put("PERSON_ID_THIS", PERSON_ID_ID);
		}
		// 考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "ArUser");
			paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		}

		retrunList = ViewDeptPerDao.viewOtApplySingleList(paramMap);
		return retrunList;
	}
	/**
	 *单个带条件中夜班津贴明细
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List viewAllowanceSingleList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String PERSON_ID = request.getParameter("PERSON_ID");
		if (PERSON_ID != null && PERSON_ID != "") {
			String P_ID[] = PERSON_ID.split(",");
			String PERSON_ID_ID = "";
			for (int i = 0; i < P_ID.length; i++) {
				if (P_ID[i] != null && P_ID[i] != "") {
					if (i == 0) {
						PERSON_ID_ID = "'" + P_ID[i] + "'";

					} else {
						PERSON_ID_ID += ",'" + P_ID[i] + "'";
					}
				}

			}
			if (P_ID.length == 0) {

				PERSON_ID_ID = "''";
			}

			paramMap.put("PERSON_ID_THIS", PERSON_ID_ID);
		}
		// 考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "ArUser");
			paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		}

		retrunList = ViewDeptPerDao.viewAllowanceSingleList(paramMap);
		return retrunList;
	}

	/**
	 *个人考勤现况,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewArPersonalList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");

		if ("".equals(request.getParameter("seach_DEPT_NO"))
				|| request.getParameter("seach_DEPT_NO") == null) {
			paramMap.put("DEPTNO", request.getParameter("seach_DEPT_NO"));
		}
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);
		}
		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("ETIME", sysdate);
		}
		//为了Myhome权限下只查看登陆者信息
		if("MyHome".equals(request.getParameter("firstType"))){
			paramMap.put("PERSON_ID", admin.getAdminID());
			paramMap.put("DEPTNO", admin.getDeptNo());
		}

		// 考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "ArUser");
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		returnList = ViewDeptPerDao.viewArPersonalList(paramMap);
		
		return returnList;

	}

	/**
	 *动态列,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAutoColumnList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		returnList = ViewDeptPerDao.viewAutoColumnList(paramMap);

		return returnList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public int viewArPersonalListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		if ("".equals(request.getParameter("seach_DEPT_NO"))
				|| request.getParameter("seach_DEPT_NO") == null) {
			paramMap.put("DEPTNO", request.getParameter("seach_DEPT_NO"));
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);

		}

		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("ETIME", sysdate);

		}
		// 考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "ArUser");
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.viewArPersonalListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	// end
	/**
	 *个人加班现况,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewOtApplyPersonalList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);

		}

		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("ETIME", sysdate);

		}

		if ("".equals(request.getParameter("seach_DEPT_NO"))
				|| request.getParameter("seach_DEPT_NO") == null) {
			paramMap.put("DEPTNO", request.getParameter("seach_DEPT_NO"));
		}
		// 考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "ArUser");

		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		//MyHome 权限下只查看个人信息
		if("MyHome".equals(request.getParameter("firstType"))){
			paramMap.put("PERSON_ID", admin.getAdminID());
			paramMap.put("DEPTNO", admin.getDeptNo());
		}
		/*if (UiUtil.getPageNum(request) > 0) {
			returnList = ViewDeptPerDao.viewOtApplyPersonalList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request,20));
		} else {}*/
			returnList = ViewDeptPerDao.viewOtApplyPersonalList(paramMap);
		
		return returnList;

	}
	/**
	 *年假现况,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewUseOfAnnualLeaveList(HttpServletRequest request)
	throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
		"seach_");
		paramMap.put("PERSON_ID001",admin.getAdminID());
		Date d=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");
		String FROM_DATE=request.getParameter("seach_FROM_DATE");
		String TO_DATE=request.getParameter("seach_TO_DATE");
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		if(FROM_DATE==null&&TO_DATE==null){
			paramMap.put("FROM_DATE", df.format(d));
			paramMap.put("TO_DATE", df.format(d)); 
		}
		if (request.getParameter("seach_VAR_YEAR") == ""
				|| request.getParameter("seach_VAR_YEAR") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
            paramMap.put("VAR_YEAR", sysdate);
		}
		// 考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "ArUser");
			
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		returnList = ViewDeptPerDao.viewUseOfAnnualLeaveList(paramMap);
		return returnList;
		
	}
	
	/**
	 *医疗期天数信息
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List arForMedicalCountInfoList(HttpServletRequest request)
	throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
		"seach_");
		paramMap.put("PERSON_ID001",admin.getAdminID());
		paramMap.put("interCpnyID",admin.getCpnyId());
       /* 
		if ("".equals(request.getParameter("seach_YEAR"))||request.getParameter("seach_YEAR") == null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy"); 
			String YEAR = format.format(new Date().getTime());
			paramMap.put("YEAR", YEAR);
		}
		*/
		// 考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "ArUser");
			
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		returnList = ViewDeptPerDao.arForMedicalCountInfoList(paramMap);
		return returnList;
		
	}
	/**
	 *年假现况,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewUseOfAdjustLeaveList(HttpServletRequest request)
	throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
		"seach_");
		if ("".equals(request.getParameter("seach_AR_MONTH"))||request.getParameter("seach_AR_MONTH") == null) {
			SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
			String ar_month_str =  sformat.format(new Date());
			paramMap.put("AR_MONTH", ar_month_str);
		}
		// 考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "ArUser");
			
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			returnList = ViewDeptPerDao.viewUseOfAdjustLeaveList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request,20));
		} else {
			returnList = ViewDeptPerDao.viewUseOfAdjustLeaveList(paramMap);
		}
		return returnList;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public int viewOtApplyPersonalListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);

		}

		if ("".equals(request.getParameter("seach_DEPT_NO"))
				|| request.getParameter("seach_DEPT_NO") == null) {
			paramMap.put("DEPTNO", request.getParameter("seach_DEPT_NO"));
		}

		// 考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "ArUser");
		}

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("ETIME", sysdate);

		}

		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.viewOtApplyPersonalListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	 
	// //end
	@SuppressWarnings("unchecked")
	@Override
	public int viewUseOfAnnualLeaveListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		// 考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "ArUser");
		}

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if ("".equals(request.getParameter("seach_YEAR"))||request.getParameter("seach_YEAR") == null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy"); 
			String YEAR = format.format(new Date().getTime());
			paramMap.put("YEAR", YEAR);
		}
		return this.ViewDeptPerDao.viewUseOfAnnualLeaveListCnt(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewMedicalInfo(HttpServletRequest request) throws Exception { 
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,"seach_");
		returnList = ViewDeptPerDao.viewMedicalInfo(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List leaveInfoList(HttpServletRequest request) throws Exception { 
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		returnList = ViewDeptPerDao.leaveInfoList(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int viewUseOfAdjustLeaveListCnt(HttpServletRequest request)
	throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
		"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		// 考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if (authority == 1) {
			paramMap.put("authority", "ArUser");
		}
		
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		return this.ViewDeptPerDao.viewUseOfAdjustLeaveListCnt(paramMap);
	}
	/**
	 *旷工查询,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAbsenteeismInfoList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		if (request.getParameter("seach_EMP_OFFICE") == null) {
			paramMap.put("EMP_OFFICE", "15119");
		}
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		if ((request.getParameter("seach_FROM_DATE") == "" || request
				.getParameter("seach_FROM_DATE") == null)
				&& (request.getParameter("seach_TO_DATE") == "" || request
						.getParameter("seach_TO_DATE") == null)) {
			// 获取当前年第一天：
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());
			paramMap.put("FROM_DATE", first);
			// 获取当前月最后一天：
			c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, c
					.getActualMaximum(Calendar.DAY_OF_MONTH));
			String last = format.format(c.getTime());
			paramMap.put("TO_DATE", last);
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		returnList = ViewDeptPerDao.viewAbsenteeismInfoList(paramMap);
		return returnList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public int viewAbsenteeismInfoListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		if (request.getParameter("seach_EMP_OFFICE") == null) {
			paramMap.put("EMP_OFFICE", "15119");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		if ((request.getParameter("seach_FROM_DATE") == "" || request
				.getParameter("seach_FROM_DATE") == null)
				&& (request.getParameter("seach_TO_DATE") == "" || request
						.getParameter("seach_TO_DATE") == null)) {
			// 获取当前年第一天：
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_YEAR, 1);
			String first = format.format(c.getTime());
			paramMap.put("FROM_DATE", first);
			// 获取当前月最后一天：
			c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, c
					.getActualMaximum(Calendar.DAY_OF_MONTH));
			String last = format.format(c.getTime());
			paramMap.put("TO_DATE", last);
		}
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.viewAbsenteeismInfoListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	// //end

	/**
	 *考勤出入数据查询,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewEntryInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		String firstFlag= request.getParameter("firstFlag");
		if ("2".equals(firstFlag)) {
			paramMap.put("STIME", DateUtil.getSysdateStr("dd/MM/yyyy"));
			paramMap.put("ETIME", DateUtil.getSysdateStr("dd/MM/yyyy"));
		}
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		
		returnList = ViewDeptPerDao.viewEntryInfoList(paramMap);
		
		return returnList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public int viewEntryInfoListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.viewEntryInfoListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	// //end

	/**
	 *manage下的部门员工任职经历 ,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List ManageEmpPositionInfoList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		String EMP_OFFICE=request.getParameter("seach_EMP_OFFICE");
		paramMap.put("EMP_OFFICE",EMP_OFFICE);
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("seach_KEY", request.getParameter("seach_KEY"));
		paramMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE"));
		// if (UiUtil.getPageNum(request) > 0) {
		// returnList = ViewDeptPerDao.ManageEmpPositionInfoList(paramMap,
		// UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		// } else {
		returnList = ViewDeptPerDao.ManageEmpPositionInfoList(paramMap);
		// }
		return returnList;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getMonthAttDetailList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList(); 
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request, "seach_");  
		returnList = ViewDeptPerDao.getMonthAttDetailList(paramMap); 
		return returnList;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List totalEmpCountLastYearTJ(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		String YEAR =request.getParameter("YEAR");
		if(YEAR==null || "".equals(YEAR)){
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
			YEAR = timeFormatter.format(Calendar.getInstance().getTime());
		}
		paramMap.put("END_PROBATION_DATE", request.getParameter("END_PROBATION_DATE"));
		paramMap.put("YEAR", YEAR);
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		/*paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE"));*/
		returnList = ViewDeptPerDao.totalEmpCountLastYearTJ(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List LeftManTotalEmpCountLastYear(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		String year = request.getParameter("YEAR");
		if (year == null || "".equals(year)) {
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
			year = timeFormatter.format(Calendar.getInstance().getTime());
		}
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
		if(year==null || "".equals(year)){
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
			year = timeFormatter.format(Calendar.getInstance().getTime());
		}
		paramMap.put("END_PROBATION_DATE", request.getParameter("END_PROBATION_DATE"));
		paramMap.put("EMPLOYEE_OWNED", request.getParameter("EMPLOYEE_OWNED"));
		paramMap.put("YEAR", year);
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		/*paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("seach_KEY", request.getParameter("seach_KEY"));
		paramMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE"));*/
		returnList = ViewDeptPerDao.LeftManTotalEmpCountLastYear(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List LeftManTotalEmpCountLastYearTJ(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		String YEAR =request.getParameter("YEAR");
		if(YEAR==null || "".equals(YEAR)){
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
			YEAR = timeFormatter.format(Calendar.getInstance().getTime());
		}
		paramMap.put("END_PROBATION_DATE", request.getParameter("END_PROBATION_DATE"));
		paramMap.put("YEAR", YEAR);
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		/*paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("seach_KEY", request.getParameter("seach_KEY"));
		paramMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE"));*/
		returnList = ViewDeptPerDao.LeftManTotalEmpCountLastYearTJ(paramMap);
		return returnList;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List totalEmpCountLastYear(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		returnList = ViewDeptPerDao.totalEmpCountLastYear(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getDemissionRateSpcSh(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		returnList = ViewDeptPerDao.getDemissionRateSpcSh(paramMap);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List NewManTotalEmpCountLastYear(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		String year = request.getParameter("YEAR");
		if (year == null || "".equals(year)) {
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
			year = timeFormatter.format(Calendar.getInstance().getTime());
		}
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
		if(year==null || "".equals(year)){
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
			year = timeFormatter.format(Calendar.getInstance().getTime());
		}
		paramMap.put("END_PROBATION_DATE", request.getParameter("END_PROBATION_DATE"));
		paramMap.put("EMPLOYEE_OWNED", request.getParameter("EMPLOYEE_OWNED"));
		paramMap.put("YEAR", year);
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		returnList = ViewDeptPerDao.NewManTotalEmpCountLastYear(paramMap);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List NewManTotalEmpCountLastYearTJ(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		String YEAR =request.getParameter("YEAR");
		if(YEAR==null || "".equals(YEAR)){
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
			YEAR = timeFormatter.format(Calendar.getInstance().getTime());
		}
		paramMap.put("END_PROBATION_DATE", request.getParameter("END_PROBATION_DATE"));
		paramMap.put("YEAR", YEAR);
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		/*paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("seach_KEY", request.getParameter("seach_KEY"));
		paramMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE"));*/
		returnList = ViewDeptPerDao.NewManTotalEmpCountLastYearTJ(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCountPosition(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		String year = request.getParameter("MONTH_DAY");
		if (year == null || "".equals(year)) {
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy/MM/dd");
			year = timeFormatter.format(Calendar.getInstance().getTime());
		}
       
		paramMap.put("YEAR", year);
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		returnList = ViewDeptPerDao.getCountPosition(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getOthers(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		String year = request.getParameter("MONTH_DAY");
		if (year == null || "".equals(year)) {
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy/MM/dd");
			year = timeFormatter.format(Calendar.getInstance().getTime());
		}
       
		paramMap.put("YEAR", year);
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		returnList = ViewDeptPerDao.getOthers(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getSearchMonth(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		String year = request.getParameter("MONTH_DAY");
		if (year == null || "".equals(year)) {
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy/MM/dd");
			year = timeFormatter.format(Calendar.getInstance().getTime());
		}
       
		paramMap.put("YEAR", year);
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		returnList = ViewDeptPerDao.getSearchMonth(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPositionRULIzhi(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		returnList = ViewDeptPerDao.getPositionRULIzhi(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpTypeRULIzhi(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		returnList = ViewDeptPerDao.getEmpTypeRULIzhi(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getGradeRuzhi(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		returnList = ViewDeptPerDao.getGradeRuzhi(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getGradeLizhi(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		returnList = ViewDeptPerDao.getGradeLizhi(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPCountEMP(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		returnList = ViewDeptPerDao.getPCountEMP(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpTypeCountEMP(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		returnList = ViewDeptPerDao.getEmpTypeCountEMP(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getGradeCountEmp(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");
		
		paramMap.put("DEPTNO", request.getParameter("DEPTNO"));
		returnList = ViewDeptPerDao.getGradeCountEmp(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int ManageEmpPositionInfoListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForManageSearch(
				request, "seach_");
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.ManageEmpPositionInfoListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	// //end

	/**
	 *manage下的部门员工任职经历 ,个人
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List ManageEmpPositionSinglList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("interLanguage", admin.getLanguage());

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.ManageEmpPositionSinglList(paramMap);

		return returnList;

	}

	/**
	 *manage下的部门员工任职经历 ,个人
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEmpInfoById(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		if (request.getParameter("PERSON_ID") == null
				|| request.getParameter("PERSON_ID") == "") {
			// group_id=ViewDeptPerDao.getDefaultGroup(paramMap);
			paramMap.put("PERSON_ID", admin.getAdminID());
		}

		paramMap.put("interLanguage", admin.getLanguage());

		return ViewDeptPerDao.getEmpInfoById(paramMap);

	}

	// //end

	// /////////公司日历班次////////start/////////////////////////////

	/*
	 * @author xuehaifei
	 * 
	 * 班次日历 2014-7-9
	 */
	@SuppressWarnings("unchecked")
	public String getCompanyCalendarViewHtml(HttpServletRequest request) {
		String actionType = ObjectUtils.toString(request
				.getParameter("actionType"));

		List calendarList = this.getArClassCalendarList(request);
		if (calendarList.size() == 0) {
			return "";
		}
		String temp = this.getFrist(request, calendarList, actionType);

		int out = Integer.parseInt(temp.substring(temp.lastIndexOf("*") + 1,
				temp.length()));
		String frist = temp.substring(0, temp.lastIndexOf("*"));
		String Default = this
				.getDefault(request, out, calendarList, actionType);
		return frist + Default;
	}

	/**
	 * 查看班次日历(get EmpCalendar List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getArClassCalendarList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyid = admin.getCpnyId();
		String arMonth = "";
		String year = ObjectUtils.toString(request.getParameter("year"));
		String month = ObjectUtils.toString(request.getParameter("month"));
		String group_id = ObjectUtils.toString(request.getParameter("GROUP"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (group_id == null || group_id.length() == 0) {
			// group_id=ViewDeptPerDao.getDefaultGroup(paramMap);
			group_id = "400224";
		}

		// String STAT_NO=group_id.equals("CH_W2")?"219948":"141436";
		if (year == null || year.length() == 0) {
			Calendar calendar = Calendar.getInstance();
			year = new java.text.SimpleDateFormat("yyyy").format(calendar
					.getTime());
			month = new java.text.SimpleDateFormat("MM").format(calendar
					.getTime());
		}
		arMonth = year + month;

		HttpSession session = request.getSession();
		// AdminBean user = SessionUtil.getLoginUserFromSession(request);

		paramMap.put("AR_MONTH", arMonth);
		paramMap.put("GROUP_ID", group_id);
		paramMap.put("interLanguage", admin.getLanguage());
		// paramMap.put("STAT_NO", STAT_NO);

		retrunList = ViewDeptPerDao.getArClassCalendarList(paramMap);
		/*
		 * int countlist=retrunList.size(); if (countlist==0){ retrunList =
		 * ViewDeptPerDao.getArClassCalendarListGs(paramMap); }
		 */
		return retrunList;
	}

	/**
	 * 得到第1天是星期几(get )
	 * 
	 * @param List
	 * @param String
	 * @return String
	 * @throws
	 */
	private String getFrist(HttpServletRequest request, List calendarList,
			String actionType) {

		String frist = "";
		int out = 0;

		if (calendarList.size() > 0) {
			// 得到第1天是星期几
			LinkedHashMap calendarMap0 = (LinkedHashMap) calendarList.get(0);
			out = 7 - NumberUtils.parseNumber(calendarMap0.get("IWEEK")
					.toString(), Integer.class);

			for (int i = 0; i < 7 - out; ++i) {
				frist += "<td ><div></div></td>";
			}

			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap paramMap = new LinkedHashMap();
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("interLanguage", admin.getLanguage());

			// paramMap.put("DEPT_DISTINGUISH_NO", "1");
			paramMap.put("PERSON_ID", admin.getPersonId());

			for (int i = 0; i < out; i++) {
				LinkedHashMap calendarMap = (LinkedHashMap) calendarList.get(i);

				frist += this.createViewCalendarHtml(paramMap, calendarMap,
						actionType);
			}
		}

		return "<tr height=\"60px\">" + frist + "</tr>*" + out;
	}

	/**
	 * 动态拼装个人日历(create ViewCalendar Html)
	 * 
	 * @param LinkedHashMap
	 * @param String
	 * @return String
	 * @throws
	 */
	private String createViewCalendarHtml(LinkedHashMap paramMap,
			LinkedHashMap calendarMap, String actionType) {

		List shiftList = (List) ViewDeptPerDao.getShiftList(paramMap);// 获取班次

		boolean flag = false;
		if (!ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO")).equals(
				"")) {
			flag = true;
		}

		String calendarHtml = "";

		calendarHtml += "<td>";
		calendarHtml += "<div><p>";

		calendarHtml += "<span class=\"day_input\"><input name=\"day\" value=\""
				+ calendarMap.get("IDAY").toString()
				+ "\" type= \"checkbox\"  "
				+ "\" title= \""
				+ calendarMap.get("DDATE_STR").toString() + "\" /></span>";

		calendarHtml += "<b>"
				+ this.getDayColor(NumberUtils.parseNumber(calendarMap.get(
						"IDAY").toString(), Integer.class), NumberUtils
						.parseNumber(calendarMap.get("IWEEK").toString(),
								Integer.class)) + "</b><span class=\"onwork\">";
		// String type = flag ?
		// "&nbsp;&nbsp<font color=\"red\">休</font>&nbsp;&nbsp;班"
		// : "&nbsp;&nbsp工&nbsp;&nbsp;班";
		String language = "zh";
		if (paramMap.get("interLanguage") != null
				&& !"".equals(paramMap.get("interLanguage").toString())) {
			language = paramMap.get("interLanguage").toString();
		}

		String type = "";
		if (!ObjectUtils.toString(calendarMap.get("TYPEID")).equals("")
				&& ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
						"1441")) {
			// 公班
			/*
			 * if(flag){ type =
			 * "<b><font style=\"color:red\">"+TipMessage.getTipMessage
			 * ("ar.viewempcalender.title.gongxiu"
			 * ,language)+"&nbsp;</font><font style=\"color:red\">"
			 * +TipMessage.getTipMessage
			 * ("ar.viewempcalender.title.ban",language)+"</font></b>"; }else{
			 */
			type = "<b><font style=\"color:red\">"
					+ TipMessage.getTipMessage(
							"ar.viewempcalender.title.gongxiu", language)
					+ "&nbsp;"
					+ TipMessage.getTipMessage("ar.viewempcalender.title.rest",
							language) + "</font></b>";
			// }

		} else if (!ObjectUtils.toString(calendarMap.get("TYPEID")).equals("")
				&& ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
						"1442")) {
			/*
			 * if(flag){ //节班 type =
			 * "<b><font style=\"color:red\">"+TipMessage.getTipMessage
			 * ("ar.viewempcalender.title.jie",language)
			 * +"&nbsp;</font><font style=\"color:red\">"
			 * +TipMessage.getTipMessage
			 * ("ar.viewempcalender.title.ban",language)+"</font></b>"; }else{
			 */
			type = "<b><font style=\"color:red\">"
					+ TipMessage.getTipMessage("ar.viewempcalender.title.jie",
							language)
					+ "&nbsp;"
					+ TipMessage.getTipMessage("ar.viewempcalender.title.rest",
							language) + "</font></b>";
			// }

		} else {
			// 工班
			/*
			 * if(flag){ type =
			 * "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong"
			 * ,language)+"&nbsp;</font><font style=\"color:red\">"+TipMessage.
			 * getTipMessage
			 * ("ar.viewempcalender.title.ban",language)+"</font></b>"; }else{
			 */
			type = "<b><font>"
					+ TipMessage.getTipMessage("ar.viewempcalender.title.gong",
							language)
					+ "&nbsp;</font>"
					+ TipMessage.getTipMessage("ar.viewempcalender.title.ban",
							language) + "</b>";
			// }
		}

		calendarHtml += type + "</span></p>";
		calendarHtml += "<h4>";

		calendarHtml += "<select name=\"SHIFT_NO_\"  "
				+ calendarMap.get("IDAY").toString() + "\" id=\"SHIFT_NO_"
				+ calendarMap.get("IDAY").toString() + "\">";
		for (int i = 0; i < shiftList.size(); i++) {
			LinkedHashMap map = (LinkedHashMap) shiftList.get(i);
			String temp = "";
			String tempShitNo = "";

			if (flag) {
				tempShitNo = calendarMap.get("SCHEDULE_SHIFT_NO") != null ? calendarMap
						.get("SCHEDULE_SHIFT_NO").toString()
						: "";
			} else {
				tempShitNo = calendarMap.get("SHIFT_NO") != null ? calendarMap
						.get("SHIFT_NO").toString() : "";
			}
			if (tempShitNo.equals(map.get("SHIFT_NO").toString())) {
				temp = "selected";
			}
			calendarHtml += "<option value=\"" + map.get("SHIFT_NO").toString()
					+ "\"" + temp + ">";
			calendarHtml += map.get("SHIFT_NAME").toString() + "</option>";
		}

		calendarHtml += "</h4>";
		calendarHtml += "</div></td>";

		// System.out.println("--------------------------------"+calendarHtml);

		return calendarHtml;
	}

	private String getDayColor(int day, int week) {
		String daycolor = null;
		switch (week) {
		case 0:
			daycolor = "<span>" + day + "</span>";
			break;
		case 1:
			daycolor = Integer.toString(day);
			break;
		case 2:
			daycolor = Integer.toString(day);
			break;
		case 3:
			daycolor = Integer.toString(day);
			break;
		case 4:
			daycolor = Integer.toString(day);
			break;
		case 5:
			daycolor = Integer.toString(day);
			break;
		case 6:
			daycolor = "<span>" + day + "</span>";
			break;
		}
		return daycolor;
	}

	private String getWorkName(int i, String language) {
		if (language != null && language.equals("zh")) {
			if (i == 0)
				return "<span class=\"nowork\"><b>休息</b></span>";
			else
				return "<span class=\"onwork\"><b>工作</b></span>";
		} else if (language != null && language.equals("ko")) {

		} else {
			if (i == 0)
				return "<span class=\"nowork\"><b>Rest Day</b></span>";
			else
				return "<span class=\"onwork\"><b>Work Day</b></span>";
		}
		return "";
	}

	/**
	 * 取默认日历(get Default)
	 * 
	 * @param int
	 * @param List
	 * @param String
	 * @return String
	 * @throws
	 */
	private String getDefault(HttpServletRequest request, int out,
			List calendarList, String actionType) {

		int r = 0;
		int rows = 0;
		if ((calendarList.size() - out) % 7 != 0) {// 算出 剩余的有几行
			rows = (calendarList.size() - out) / 7 + 1;
		} else {
			rows = (calendarList.size() - out) / 7;
		}
		rows = rows * 7;// 总共多少格子
		rows = rows - (calendarList.size() - out);// 到最后一行剩余几格子
		String Default = "";

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());

		// paramMap.put("DEPT_DISTINGUISH_NO", "1");
		paramMap.put("PERSON_ID", admin.getPersonId());

		for (int i = out; i < calendarList.size(); i++) {
			LinkedHashMap calendarMap = (LinkedHashMap) calendarList.get(i);
			if (r == 0 || r == 14 || r == 28) {// 加换行
				Default += "<tr   height=\"60px\"   style=\"background:#FFFFCC\">";
			}
			if (r == 7 || r == 21) {
				Default += "<tr height=\"60px\">";
			}
			// ---------------------------------------------
			List shiftList = (List) ViewDeptPerDao.getShiftList(paramMap);// 获取班次

			boolean flag = false;
			if (!ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO"))
					.equals("")) {
				flag = true;
			}

			String calendarHtml = "";

			calendarHtml += "<td>";
			calendarHtml += "<div><p>";

			calendarHtml += "<span class=\"day_input\"><input name=\"day\" value=\""
					+ calendarMap.get("IDAY").toString()
					+ "\" type= \"checkbox\""
					+ "\" title= \""
					+ calendarMap.get("DDATE_STR").toString() + "\" /></span>";

			calendarHtml += "<b>"
					+ this.getDayColor(NumberUtils.parseNumber(calendarMap.get(
							"IDAY").toString(), Integer.class), NumberUtils
							.parseNumber(calendarMap.get("IWEEK").toString(),
									Integer.class))
					+ "</b><span class=\"onwork\">";
			// String type = flag ?
			// "&nbsp;&nbsp<font color=\"red\">休</font>&nbsp;&nbsp;班"
			// : "&nbsp;&nbsp工&nbsp;&nbsp;班";
			String language = "zh";
			if (paramMap.get("interLanguage") != null
					&& !"".equals(paramMap.get("interLanguage").toString())) {
				language = paramMap.get("interLanguage").toString();
			}

			String type = "";
			if (!ObjectUtils.toString(calendarMap.get("TYPEID")).equals("")
					&& ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
							"1441")) {
				// 公班
				if (flag) {
					type = "<b><font style=\"color:red\">"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.gongxiu",
									language)
							+ "&nbsp;</font><font style=\"color:red\">"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.rest", language)
							+ "</font></b>";
				} else {
					type = "<b><font style=\"color:red\">"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.gongxiu",
									language)
							+ "&nbsp;</font>"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.ban", language)
							+ "</b>";
				}

			} else if (!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
					"")
					&& ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
							"1442")) {
				if (flag) {
					// 节班
					type = "<b><font style=\"color:red\">"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.jie", language)
							+ "&nbsp;</font><font style=\"color:red\">"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.rest", language)
							+ "</font></b>";
				} else {
					type = "<b><font style=\"color:red\">"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.jie", language)
							+ "&nbsp;</font>"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.ban", language)
							+ "</b>";
				}

			} else {
				// 工班
				if (flag) {
					type = "<b><font>"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.gong", language)
							+ "&nbsp;</font>"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.ban", language)
							+ "</b>";
				} else {
					type = "<b><font>"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.gong", language)
							+ "&nbsp;</font>"
							+ TipMessage.getTipMessage(
									"ar.viewempcalender.title.ban", language)
							+ "</b>";
				}
			}

			calendarHtml += type + "</span></p>";
			calendarHtml += "<h4>";

			if (r == 0 || r == 2 || r == 1 || r == 3 || r == 4 || r == 5
					|| r == 6 || r == 14 || r == 15 || r == 16 || r == 17
					|| r == 18 || r == 19 || r == 20 || r == 28 || r == 29
					|| r == 30 || r == 31 || r == 32 || r == 33 || r == 34
					|| r == 35) {// 加换行
				calendarHtml += "<select name=\"SHIFT_NO_\" style=\"background:#FFFFCC\" "
						+ calendarMap.get("IDAY").toString()
						+ "\" id=\"SHIFT_NO_"
						+ calendarMap.get("IDAY").toString() + "\">";
			}

			if (r == 7 || r == 8 || r == 9 || r == 10 || r == 11 || r == 12
					|| r == 13 || r == 21 || r == 22 || r == 23 || r == 24
					|| r == 25 || r == 26 || r == 27) {
				calendarHtml += "<select name=\"SHIFT_NO_\"  "
						+ calendarMap.get("IDAY").toString()
						+ "\" id=\"SHIFT_NO_"
						+ calendarMap.get("IDAY").toString() + "\">";
			}

			for (int j = 0; j < shiftList.size(); j++) {
				LinkedHashMap map = (LinkedHashMap) shiftList.get(j);
				String temp = "";
				String tempShitNo = "";

				if (flag) {
					tempShitNo = calendarMap.get("SCHEDULE_SHIFT_NO") != null ? calendarMap
							.get("SCHEDULE_SHIFT_NO").toString()
							: "";
				} else {
					tempShitNo = calendarMap.get("SHIFT_NO") != null ? calendarMap
							.get("SHIFT_NO").toString()
							: "";
				}
				if (tempShitNo.equals(map.get("SHIFT_NO").toString())) {
					temp = "selected";
				}
				calendarHtml += "<option value=\""
						+ map.get("SHIFT_NO").toString() + "\"" + temp + ">";
				calendarHtml += map.get("SHIFT_NAME").toString() + "</option>";
			}

			calendarHtml += "</h4>";
			calendarHtml += "</div></td>";
			// -----------------------------------------------
			// Default += this.createViewCalendarHtml(paramMap, calendarMap,
			// actionType);
			Default += calendarHtml;
			if (r == 6 || r == 13 || r == 20 || r == 27 || r == 34) {// 加换行
				Default += "</tr>";
			}
			r += 1;
		}
		// 补空格
		if (rows > 0) {
			String temp = "";
			for (int i = 0; i < rows; i++) {
				temp += "<td border=\"2\"><div></div></td>";
			}
			Default += temp + "</tr>";
		}
		// System.out.println("---------------------整个样式-------------------------------"+Default);
		return Default;
	}

	// ///公司日历 end

	/**
	 * manage count
	 */
	/**
	 *manage下年龄统计,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List manageAgeCountList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.manageAgeCountList(paramMap);

		return returnList;

	}

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeAgeList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		List list = new ArrayList();
		parMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		list = ViewDeptPerDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));

					// key为 部门ID value=查询结果LIST

					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao
							.getAgeListByDeptNo(parMap));

				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	// /年龄end

	/**
	 * manage count
	 */
	/**
	 *manage下职级统计,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List manageGradeCountList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.manageAgeCountList(paramMap);

		return returnList;

	}

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeGradeList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map lMap3 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		Map parMap = paramMap;
		List list = new ArrayList();
		list = ViewDeptPerDao.getAllDept(paramMap);
		parMap.put("AR_SUPERVISIOR_INFO", admin.getAdminID());
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {// 遍历部门 不为空的传入递归
					// 得到LIST
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));

					// key为 部门ID value=查询结果LIST

					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao.getGradeBGZListByDeptNo(parMap));
					lMap3.put(dept_id.get("DEPTNO"), ViewDeptPerDao.getGradeSCZListByDeptNo(parMap));
				}
			}
		}
		lMap.put("deptList", ViewDeptPerDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		lMap.put("deptListCount3", lMap3);
		return lMap;
	}

	// //职级END

	/**
	 * manage count
	 */
	/**
	 *manage下职务统计,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List managePositionCountList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.manageAgeCountList(paramMap);

		return returnList;

	}

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodePositionList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		List list = new ArrayList();

		list = ViewDeptPerDao.getAllDeptDif(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门区分DEPT_DISTINGUISH_STANDARD
					parMap.put("DEPT_DISTINGUISH_STANDARD", dept_id
							.get("DEPT_DISTINGUISH_STANDARD"));

					// key为 部门ID value=查询结果LIST

					lMap2.put(dept_id.get("DEPT_DISTINGUISH_STANDARD"),
							ViewDeptPerDao.getPositionListByDeptNo(parMap));

				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeDifList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	// //职务end

	/**
	 * manage count
	 */
	/**
	 *manage下学历统计,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List manageEduCountList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.manageAgeCountList(paramMap);

		return returnList;

	}

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeEduList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map lMap3 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		Map parMap = paramMap;
		parMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		List list = new ArrayList();
		list = ViewDeptPerDao.getAllDept(paramMap);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao.getEduListByDeptNo(parMap));
					lMap3.put(dept_id.get("DEPTNO"), ViewDeptPerDao.getSexListByDeptNo(parMap));
				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		lMap.put("deptListCount3", lMap3);
		return lMap;
	}

	// /学历end

	/**
	 * manage count
	 */
	/**
	 *manage下职员类型统计,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List manageEmpTypeCountList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		// 重新配置权限 manage
		LinkedHashMap paramMap = getLinkedMapByRequestForManageSearch(request,
				"seach_");

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());

		returnList = ViewDeptPerDao.manageAgeCountList(paramMap);

		return returnList;

	}

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeEmpTypeList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 获取选定月份最后一天
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		Map parMap = paramMap;

		List list = new ArrayList();

		list = ViewDeptPerDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao
							.getEmpTypeListByDeptNo(parMap));
				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	// //职员类型end

	// //一月中最大天数 若是时间为空 默认为本月最大天数
	public String getMonthLastDay(String month) {
		Calendar a = Calendar.getInstance();

		if (month != null && month != "") {
			String dates[] = month.replace(".", "/").split("/");
			int year = Integer.parseInt(dates[1]);
			int months = Integer.parseInt(dates[0]);
			int day = 1;
			Calendar cal = Calendar.getInstance();
			cal.set(year, months - 1, day);
			int last = cal.getActualMaximum(Calendar.DATE);
			String date = last + "." + months + "." + year;
			return date;

		} else {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			Calendar ca = Calendar.getInstance();

			String last = format.format(ca.getTime());

			return last;
		}

	}
	
	public String getMonthLastDay1(String year) {
		Calendar a = Calendar.getInstance();

		if (year != null && year != "") {
			String dates[] = year.replace(".", "/").split("/");
			int year1 = Integer.parseInt(dates[0]);
			Calendar cal = Calendar.getInstance();
			int months = cal.getActualMaximum(Calendar.MONTH) + 1;
			int last = cal.getActualMaximum(Calendar.DATE);
			String date = last + "." + months + "." + year1;
			return date;

		} else {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			Calendar ca = Calendar.getInstance();

			String last = format.format(ca.getTime());

			return last;
		}

	}

	// /MANAGE人员类型条件
	public Map changeCode(Map paramMap) {
		/*String codeType = "";
		int j = 0;
		for (int i = 0; i < 5; i++) {
			String code = (String) paramMap.get("EMP_TYPE_CODE" + i);
			if (code != null && code != "") {
				j++;
				if (j == 1) {
					codeType = "'" + code + "'";

				} else {
					codeType += ",'" + code + "'";
				}

			}
		}

		paramMap.put("EMP_TYPE_CODE", codeType);*/

		return paramMap;

	}

	/**
	 * 月别采用现状
	 */

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeMonthList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 默认为当前的一天
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay1((String) paramMap
				.get("YEAR")));
		// 若 页面条件 为空 获取当前年份
		Calendar a = Calendar.getInstance();
		if (paramMap.get("YEAR") == null || paramMap.get("YEAR") == "") {

			paramMap.put("YEAR", a.get(Calendar.YEAR));

		}
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;

		List list = new ArrayList();

		list = ViewDeptPerDao.getAllDept1(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao
							.getMonthListByDeptNo(parMap));
				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	/**
	 * 月别退职现状
	 */
	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeMonthResignList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 默认为当前的一天
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay1((String) paramMap
				.get("YEAR")));
		// 若 页面条件 为空 获取当前年份
		Calendar a = Calendar.getInstance();
		if (paramMap.get("YEAR") == null || paramMap.get("YEAR") == "") {

			paramMap.put("YEAR", a.get(Calendar.YEAR));

		}
		//paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;

		List list = new ArrayList();

		list = ViewDeptPerDao.getAllDept1(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao
							.getMonthResignListByDeptNo(parMap));
				}
			}
		}

		lMap.put("deptList", ViewDeptPerDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	/**
	 * 加班年假
	 */
	/**
	 *个
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewArPersonalYearList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		SimpleDateFormat format = new SimpleDateFormat("yyyy");

		if (request.getParameter("YEAR") == ""
				|| request.getParameter("YEAR") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("YEAR", sysdate);

		}

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CpnyId", admin.getCpnyId());
		returnList = ViewDeptPerDao.viewArPersonalYearList(paramMap);
		return returnList;

	}
	
	
	

	/**
	 * 考勤汇总
	 */
	/**
	 *个
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewArSummaryList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		paramMap.put("PERSON_ID",request.getParameter("PERSON_ID"));
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat("yyyy/MM");
		String firstFlag= request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			Date date= new Date();
			String arMonth = format.format(date);
			paramMap.put("AR_MONTH", arMonth);
		}
		paramMap.put("CpnyId", admin.getCpnyId());
		returnList = ViewDeptPerDao.viewArSummaryList(paramMap);
		return returnList;

	}
	
	/**
	 * 考勤汇总（个人）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewArDetailSummaryForMonthList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		paramMap.put("PERSON_ID",request.getParameter("PERSON_ID"));
		paramMap.put("CpnyId", admin.getCpnyId());
		returnList = ViewDeptPerDao.viewArDetailSummaryForMonthList(paramMap);
		return returnList;
	}
	
	/**
	 * 考勤汇总（考勤员）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewArDetailSummaryForAttenceList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		paramMap.put("PERSON_ID",request.getParameter("PERSON_ID"));
		paramMap.put("CpnyId", admin.getCpnyId());
		returnList = ViewDeptPerDao.viewArDetailSummaryForAttenceList(paramMap);
		return returnList;
	}
	
	/**
	 * 考勤汇总（报表）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceForMonthList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		paramMap.put("PERSON_ID",request.getParameter("PERSON_ID"));
		paramMap.put("CpnyId", admin.getCpnyId());
		returnList = ViewDeptPerDao.viewAttendanceForMonthList(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap viewAttendanceForSpcBjMonthList(HttpServletRequest request,String deptLeve,String include)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		LinkedHashMap lMap2 = new LinkedHashMap();
		LinkedHashMap deptMap = new LinkedHashMap();
		List list = new ArrayList();
		paramMap.put("DEPT_LEVE", deptLeve);
		paramMap.put("INCLUDE_DEPTNO", include);
		list = ViewDeptPerDao.getAllDeptForHr(paramMap);
		Map parmMap  = paramMap;
		String deptStr= "";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
						parmMap.put("DEPTNO", dept_id.get("DEPTNO"));
					    lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao.viewAttendanceForSpcBjMonthList(paramMap));
				}
			}
		}
		lMap2.put("deptList", list);
		return lMap2;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap viewAttendanceForSpcBjMonthList1(HttpServletRequest request,String deptLeve,String include)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		LinkedHashMap lMap2 = new LinkedHashMap();
		LinkedHashMap deptMap = new LinkedHashMap();
		List list = new ArrayList();
		paramMap.put("DEPT_LEVE", deptLeve);
		paramMap.put("INCLUDE_DEPTNO", include);
		list = ViewDeptPerDao.getAllDeptForHr(paramMap);
		Map parmMap  = paramMap;
		String deptStr= "";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
						parmMap.put("DEPTNO", dept_id.get("DEPTNO"));
					    lMap2.put(dept_id.get("DEPTNO"), ViewDeptPerDao.viewAttendanceForSpcBjMonthList1(paramMap));
				}
			}
		}
		lMap2.put("deptList", list);
		return lMap2;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getShopItemList(HttpServletRequest request,String cpnyId)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		LinkedHashMap lMap2 = new LinkedHashMap();
		LinkedHashMap deptMap = new LinkedHashMap();
		List list = new ArrayList();
		paramMap.put("IN_CPNY_ID", cpnyId);
		list = ViewDeptPerDao.getAllShopItem(paramMap);
		Map parmMap  = paramMap;
		String deptStr= "";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
						parmMap.put("DEPTNAME", dept_id.get("DEPTNAME"));
					    lMap2.put(dept_id.get("DEPTNAME"), ViewDeptPerDao.getAllShopDetailItemsh(paramMap));
				}
			}
		}
		lMap2.put("deptList", list);
		return lMap2;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getShopItemListsh(HttpServletRequest request,String cpnyId)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		LinkedHashMap lMap2 = new LinkedHashMap();
		LinkedHashMap deptMap = new LinkedHashMap();
		List list = new ArrayList();
		paramMap.put("IN_CPNY_ID", cpnyId);
		list = ViewDeptPerDao.getAllShopItemsh(paramMap);
		Map parmMap  = paramMap;
		String deptStr= "";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
						parmMap.put("DEPTNAME", dept_id.get("DEPTNAME"));
					    lMap2.put(dept_id.get("DEPTNAME"), ViewDeptPerDao.getAllShopDetailItemsh(paramMap));
				}
			}
		}
		lMap2.put("deptList", list);
		return lMap2;
	}
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getShopItemListsh1(HttpServletRequest request,String cpnyId)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		LinkedHashMap lMap2 = new LinkedHashMap();
		LinkedHashMap deptMap = new LinkedHashMap();
		List list = new ArrayList();
		paramMap.put("IN_CPNY_ID", cpnyId);
		list = ViewDeptPerDao.getAllShopItemsh(paramMap);
		Map parmMap  = paramMap;
		String deptStr= "";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
						parmMap.put("DEPTNAME", dept_id.get("DEPTNAME"));
					    lMap2.put(dept_id.get("DEPTNAME"), ViewDeptPerDao.getAllShopDetailItemsh1(paramMap));
				}
			}
		}
		lMap2.put("deptList", list);
		return lMap2;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getShopCountItemList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		LinkedHashMap lMap2 = new LinkedHashMap();
		LinkedHashMap deptMap = new LinkedHashMap();
		List list = new ArrayList();
		list = ViewDeptPerDao.getAllShopCountItem(paramMap); 
		lMap2.put("ShopCountItem", list);
		return lMap2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getShopCountItemListsh(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		LinkedHashMap lMap2 = new LinkedHashMap();
		LinkedHashMap deptMap = new LinkedHashMap();
		List list = new ArrayList();
		list = ViewDeptPerDao.getAllShopCountItemsh1(paramMap); 
		lMap2.put("ShopCountItem", list);
		return lMap2;
	}
	/**
	 * 年假信息
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List yearInfo(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		if (request.getParameter("seach_VAR_YEAR") == ""
				|| request.getParameter("seach_VAR_YEAR") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
				paramMap.put("VAR_YEAR", sysdate);
		}

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CpnyId", admin.getCpnyId());
		returnList = ViewDeptPerDao.yearInfo(paramMap);
		return returnList;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List vacInfo(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
		if (request.getParameter("seach_VAR_YEAR") == ""
				|| request.getParameter("seach_VAR_YEAR") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
			String sysdate1 = format1.format(c.getTime());
			if("HTSV".equals(admin.getCpnyId()) || 
					"HAE".equals(admin.getCpnyId()) || 
					"SPC_DL".equals(admin.getCpnyId())){
				paramMap.put("VAR_YEAR", sysdate1);
			}else{
				paramMap.put("VAR_YEAR", sysdate);
			}
		}else{
			if("HTSV".equals(admin.getCpnyId()) || 
					"HAE".equals(admin.getCpnyId()) || 
					"SPC_DL".equals(admin.getCpnyId())){
				paramMap.put("VAR_YEAR",request.getParameter("seach_VAR_YEAR"));
			}
		}

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CpnyId", admin.getCpnyId());
		returnList = ViewDeptPerDao.vacInfo(paramMap);
		return returnList;

	}

	/**
	 * 年假使用信息
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List yearUseInfo(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		if (request.getParameter("seach_VAR_YEAR") == ""
				|| request.getParameter("seach_VAR_YEAR") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
				paramMap.put("VAR_YEAR", sysdate);
		}

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CpnyId", admin.getCpnyId());
		returnList = ViewDeptPerDao.yearUseInfo(paramMap);
		return returnList;

	}

	// end

	/**
	 *个人考勤现况,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewArPersonalManageList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);

		}

		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("ETIME", sysdate);

		}

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			returnList = ViewDeptPerDao.viewArPersonalManageList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = ViewDeptPerDao.viewArPersonalManageList(paramMap);
		}
		return returnList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public int viewArPersonalManageListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		if (request.getParameter("seach_STIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("STIME", first);

		}

		if (request.getParameter("seach_ETIME") == ""
				|| request.getParameter("seach_STIME") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("ETIME", sysdate);

		}
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.viewArPersonalManageListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	/**
	 *个人加班现况,batch overtime apply person list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewOtApplyPersonalManageList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request,
				"seach_");

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		if (request.getParameter("seach_FROM_DATE") == ""
				|| request.getParameter("seach_FROM_DATE") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("FROM_DATE", first);

		}

		if (request.getParameter("seach_TO_DATE") == ""
				|| request.getParameter("seach_TO_DATE") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("TO_DATE", sysdate);

		}

		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			returnList = ViewDeptPerDao.viewOtApplyPersonalManageList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = ViewDeptPerDao.viewOtApplyPersonalManageList(paramMap);
		}
		return returnList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public int viewOtApplyPersonalManageListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request,
				"seach_");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		if (request.getParameter("seach_FROM_DATE") == ""
				|| request.getParameter("seach_FROM_DATE") == null) {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			// 设置为1号,当前日期既为本月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());

			paramMap.put("FROM_DATE", first);

		}

		if (request.getParameter("seach_TO_DATE") == ""
				|| request.getParameter("seach_TO_DATE") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());

			paramMap.put("TO_DATE", sysdate);

		}
		// if(paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null&&paramMap.get("KEY")!=null){
		// if(!paramMap.get("KEY").equals("")||!paramMap.get("GROUP_NO").equals("")||!paramMap.get("DEPT_NO").equals("")){
		return this.ViewDeptPerDao.viewOtApplyPersonalManageListCnt(paramMap);
		/*
		 * } }else{ return 0; } return 0;
		 */
	}

	
}