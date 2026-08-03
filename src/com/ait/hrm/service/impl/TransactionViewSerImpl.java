package com.ait.hrm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.TransactionAffirmDao;
import com.ait.ess.service.InfoApplySer;
import com.ait.hrm.dao.TransactionViewDao;
import com.ait.hrm.dao.TransferOrderDao;
import com.ait.hrm.service.TransactionViewSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 发令查看(Transaction view)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: TransactionViewSerImpl.java
 * @Description:
 * @Create date: Feb 27, 2012 1:42:43 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 27, 2012 1:42:43 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Service
public class TransactionViewSerImpl implements TransactionViewSer {

	Logger logger = Logger.getLogger(TransactionViewSerImpl.class);

	@Autowired
	private TransactionViewDao transactionViewDao;

	@Autowired
	private TransactionAffirmDao transactionAffirmDao;
	@Autowired
	private TransferOrderDao transferOrderDao;

	/**
	 * 通过request请求封装查询条件(get search conditions for request)
	 * 
	 * @param request
	 * @return
	 */
	private LinkedHashMap getMapByRequestForSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}

	/**
	 * 调动发令列表(view transaction transaction view list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTransactionTransViewList(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1365");// 1365为调动发令
		List list = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionViewDao.getTransactionTransViewList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionViewDao.getTransactionTransViewList(paramMap);
		}
		List returnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("affirmerList", this.transactionAffirmDao.getAffirmerListByExpInsideNo(paramMap));
			returnList.add(paramMap);
		}
		return returnList;
	}

	/**
	 * 调动发令总数(get transaction transaction view total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getTransactionTransViewListCnt(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1365");
		return transactionViewDao.getTransactionTransViewListCnt(paramMap);
	}

	/**
	 * 入职发令列表(view entry transaction list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getEntryTransViewList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("EMPID", paramMap.get("dwz.person.empId"));
		paramMap.put("USER_NO", admin.getUserNo());
		if(!paramMap.containsKey("FROM_TIME")){
			paramMap.put("FROM_TIME", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("TO_TIME", DateUtil.getSysdateStr());			
		}
//		paramMap.put("FROM_TIME", null);
//		paramMap.put("TO_TIME", null);
//		paramMap.put("Join_Company_Date_FROM_TIME", request.getParameter("seach_FROM_TIME"));//入职日期根据日期去hr_employee表里查询，其余调令从HR_EXPERIENCE_INSIDE查询
//		paramMap.put("Join_Company_Date_TO_TIME", request.getParameter("seach_TO_TIME"));
		paramMap.put("TRANS_NO", "1359");// 1359为入职发令
		
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
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);
		
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionViewDao.getEntryTransViewList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionViewDao.getEntryTransViewList(paramMap);
		}
//		List returnList = new ArrayList();
//
//		for (int i = 0; i < list.size(); i++) {
//			paramMap = (LinkedHashMap) list.get(i);
//			paramMap.put("affirmerList", this.transactionAffirmDao.getAffirmerListByExpInsideNo(paramMap));
//			returnList.add(paramMap);
//		}
//		return returnList;
		return list;
	}

	/**
	 * 入职发令总数(get entry transaction view total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEntryTransViewListCnt(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("EMPID", paramMap.get("dwz.person.empId"));
		paramMap.put("USER_NO", admin.getUserNo());
		if(!paramMap.containsKey("FROM_TIME")){
			paramMap.put("FROM_TIME", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("TO_TIME", DateUtil.getSysdateStr());			
		}
//		paramMap.put("FROM_TIME", null);
//		paramMap.put("TO_TIME", null);
//		paramMap.put("Join_Company_Date_FROM_TIME", request.getParameter("seach_FROM_TIME"));//入职日期根据日期去hr_employee表里查询，其余调令从HR_EXPERIENCE_INSIDE查询
//		paramMap.put("Join_Company_Date_TO_TIME", request.getParameter("seach_TO_TIME"));
		paramMap.put("TRANS_NO", "1359");// 入职发令
		
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
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);

		return transactionViewDao.getEntryTransViewListCnt(paramMap);
	}

	/**
	 * 批量取消发令(batch pass and reject PromotRelegat transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelHrExperienceInsideByNo(HttpServletRequest request)
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
				int count = this.transactionViewDao
						.getPassHrAffirmCountByExpInsideNo(map)
						+ this.transactionViewDao.ifEffectByExpInsideNo(map);
				if (count > 0) {
					return 2;
				}
				list.add(map);
			}
			this.transactionViewDao.cancelHrExperienceInsideByNo(list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 查看员工历史发令信息列表(get employee transaction history list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getEmployeeTransHistoryList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("TRANS_NO", "1365");
		paramMap.put("ADMIN_ID", admin.getAdminID());
		return this.transactionViewDao.getTransactionTransViewList(paramMap);
	}

	/**
	 * 员工历史发令信息总数(get employee transaction history total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getEmployeeTransHistoryListCnt(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
				paramMap.put("TRANS_NO", "1365");
		paramMap.put("ADMIN_ID", admin.getAdminID());
		return transactionViewDao.getEmployeeTransHistoryListCnt(paramMap);
	}

	/**
	 * 入职发令信息查看(view entry transaction information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map viewEntryTransInfo(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		if(paramMap.get("CPNY_ID")==null || "".equals(paramMap.get("CPNY_ID").toString())){
			paramMap.put("CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"");
		}
		Map map = this.transactionViewDao.viewEntryTransInfo(paramMap);
		if(null==map){
			map = this.transactionViewDao.viewEntryTransTempInfo(paramMap);
		}
		return map;
	}
	
	/**
	 * 入职发令信息查看temp 表数据(view entry transaction information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public Map viewEntryTransTempInfo(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return this.transactionViewDao.viewEntryTransTempInfo(paramMap);
	}

	/**
	 * 转正发令列表(view transferNormal transaction view list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTransferNormalViewList(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1360");// 1360为转正发令
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		List list = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionViewDao.getTransferNormalViewList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionViewDao.getTransferNormalViewList(paramMap);
		}

		List returnList = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("affirmerList", this.transactionAffirmDao
					.getAffirmerListByExpInsideNo(paramMap));
			returnList.add(paramMap);
		}
		return returnList;
	}

	/**
	 * 转正发令总数(get transferNormal transaction view total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getTransferNormalViewListCnt(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1360");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return transactionViewDao.getTransferNormalViewListCnt(paramMap);
	}

	/**
	 * 批量取消转正发令(batch pass and reject PromotRelegat transaction for
	 * transferNormal)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelHrTransferNormalInsideByNo(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		try {

			String[] paramData = request
					.getParameterValues("searchTransferNorma");
			for (int i = 0; i < paramData.length; i++) {
				Map map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
				map.put("EXP_INSIDE_NO", paramData[i]);

				// 决裁流程里未决裁且发令表里未生效
				int count = this.transactionViewDao
						.getPassHrAffirmCountByExpInsideNo(map)
						+ this.transactionViewDao
								.ifEffectByExpInsideNoForTransferNormal(map);
				if (count > 0) {
					return 2;
				}
				list.add(map);
			}
			this.transactionViewDao.cancelHrTransferNormalInsideByNo(list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 离职发令列表(view resignation transaction view list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getResignationViewList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "643");// 643为离职发令
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		List list = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionViewDao.getResignationViewList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
//			list = transactionViewDao.getTransferNormalViewList(paramMap);
			list = transactionViewDao.getResignationViewList(paramMap);
		}
		
		List returnList = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("affirmerList", this.transactionAffirmDao
					.getAffirmerListByExpInsideNo(paramMap));
			returnList.add(paramMap);
		}
		return returnList;
	}

	/**
	 * 转正发令总数(get resignation transaction view total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getResignationViewListCnt(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("TRANS_NO", "643");
		return transactionViewDao.getResignationViewListCnt(paramMap);
	}

	/**
	 * 批量取消离职发令(batch pass and reject PromotRelegat transaction for Resignation)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelResignationInBatchInsideByNo(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		try {

			String[] paramData = request.getParameterValues("searchResign");
			for (int i = 0; i < paramData.length; i++) {
				Map map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
				map.put("EXP_INSIDE_NO", paramData[i]);

				// 决裁流程里未决裁且发令表里未生效
				int count = this.transactionViewDao
						.getPassHrAffirmCountByExpInsideNo(map)
						+ this.transactionViewDao
								.ifEffectByExpInsideNoForResignation(map);
				if (count > 0) {
					return 2;
				}
				list.add(map);
			}
			this.transactionViewDao.cancelHrResignationInsideByNo(list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	/**
	 * 晋升发令列表(view transaction transaction view list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTransferPromoteForSearch(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1363");// 1363为晋升降职发令
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		List list = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionViewDao.getTransferPromoteForSearch(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionViewDao.getTransferPromoteForSearch(paramMap);
		}

		List returnList = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("affirmerList", this.transactionAffirmDao
					.getAffirmerListByExpInsideNo(paramMap));
			returnList.add(paramMap);
		}
		return returnList;
	}

	/**
	 * 晋升发令总数(get transaction transaction view total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getTransferPromoteForSearchCnt(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("TRANS_NO", "1363");
		return transactionViewDao.getTransferPromoteForSearchCnt(paramMap);
	}
	
	/**
	 * 批量取消兼职发令(batch cancel transaction for Plurality)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int cancelPluralityBatchInsideByNo(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("searchPlu");
			for (int i = 0; i < paramData.length; i++) {
				Map map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
				map.put("EXP_INSIDE_NO", paramData[i]);
				// 决裁流程里未决裁且发令表里未生效
				int count = this.transactionViewDao
						.getPassHrAffirmCountByExpInsideNo(map)
						+ this.transactionViewDao
								.ifEffectByExpInsidePluralityNo(map);
				if (count > 0) {
					return 2;
				}
				list.add(map);
			}
			this.transactionViewDao.cancelHrPluralityByNo(list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 兼职发令列表(Plurality the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPluralityViewList(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1361");// 1361为兼职发令
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		List list = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionViewDao.getPluralityViewList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionViewDao.getPluralityViewList(paramMap);
		}
		List returnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("affirmerList", this.transactionAffirmDao
					.getAffirmerListByExpInsideNo(paramMap));
			returnList.add(paramMap);
		}
		return returnList;
	}
	/**
	 * 兼职发令总数(Plurality the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPluralityViewListCnt(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("TRANS_NO", "1361");// 1361为兼职发令
		return transactionViewDao.getPluralityViewListCnt(paramMap);
	}
	

	/**
	 * 批量取消停职发令(batch cancel transaction for Suspend)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int cancelSuspendBatchInsideByNo(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("searchSus");
			for (int i = 0; i < paramData.length; i++) {
				Map map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
				map.put("EXP_INSIDE_NO", paramData[i]);
				// 决裁流程里未决裁且发令表里未生效
				int count = this.transactionViewDao
						.getPassHrAffirmCountByExpInsideNo(map)
						+ this.transactionViewDao
								.ifEffectByExpInsideSuspendNo(map);
				if (count > 0) {
					return 2;
				}
				list.add(map);
			}
			this.transactionViewDao.cancelHrSuspendByNo(list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 停职发令列表(Suspend the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getSuspendViewList(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "16193");// 16193为停职发令
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		List list = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionViewDao.getSuspendViewList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionViewDao.getSuspendViewList(paramMap);
		}
		List returnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("affirmerList", this.transactionAffirmDao
					.getAffirmerListByExpInsideNo(paramMap));
			returnList.add(paramMap);
		}
		return returnList;
	}
	/**
	 * 停职发令总数(Suspend the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getSuspendViewListCnt(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("TRANS_NO", "16193");// 1362为停职发令
		return transactionViewDao.getSuspendViewListCnt(paramMap);
	}
	/**
	 * 停职发令历史记录(Suspend the history)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEmployeeSuspendHistoryList(HttpServletRequest request)
		throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("TRANS_NO", "16193");// 16193为停职发令
		paramMap.put("ADMIN_ID", admin.getAdminID());
		List list = this.transactionViewDao.getSuspendViewList(paramMap); 
		List returnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("affirmerList", this.transactionAffirmDao
					.getAffirmerListByExpInsideNo(paramMap));
			returnList.add(paramMap);
		}
		return returnList;
	}

	/**
	 * 批量取消奖励发令(batch cancel transaction for Hortation)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int cancelHortationBatchInsideByNo(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("searchHortation");
			for (int i = 0; i < paramData.length; i++) {
				Map map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
				map.put("EXP_INSIDE_NO", paramData[i]);
				// 决裁流程里未决裁且发令表里未生效
				int count = this.transactionViewDao
						.getPassHrAffirmCountByExpInsideNo(map)
						+ this.transactionViewDao
								.ifEffectByExpInsideHortationNo(map);
				if (count > 0) {
					return 2;
				}
				list.add(map);
			}
			this.transactionViewDao.cancelHrHortationByNo(list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 奖励发令列表(Hortation the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getHortationViewList(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "641");// 641为奖励发令
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		
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
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);
		
		List list = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionViewDao.getHortationViewList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionViewDao.getHortationViewList(paramMap);
		}
		List returnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("affirmerList", this.transactionAffirmDao
					.getAffirmerListByExpInsideNo(paramMap));
			returnList.add(paramMap);
		}
		return returnList;
	}
	/**
	 * 奖励发令总数(Hortation the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getHortationViewListCnt(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		
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
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);
				
		paramMap.put("TRANS_NO", "641");// 641为奖励发令
		return transactionViewDao.getHortationViewListCnt(paramMap);
	}
	
	
	

	/**
	 * 批量取消惩戒发令(batch cancel transaction for PunishMent)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int cancelPunishMentBatchInsideByNo(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("searchPm");
			for (int i = 0; i < paramData.length; i++) {
				Map map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
				map.put("EXP_INSIDE_NO", paramData[i]);
				// 决裁流程里未决裁且发令表里未生效
				int count = this.transactionViewDao
						.getPassHrAffirmCountByExpInsideNo(map)
						+ this.transactionViewDao
								.ifEffectByExpInsidePunishMentNo(map);
				if (count > 0) {
					return 2;
				}
				list.add(map);
			}
			this.transactionViewDao.cancelHrPunishMentByNo(list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 惩戒发令列表(PunishMent the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPunishMentViewList(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "642");// 642为惩戒发令
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		
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
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);
				
		List list = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionViewDao.getPunishMentViewList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionViewDao.getPunishMentViewList(paramMap);
		}
		List returnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("affirmerList", this.transactionAffirmDao
					.getAffirmerListByExpInsideNo(paramMap));
			returnList.add(paramMap);
		}
		return returnList;
	}
	/**
	 * 惩戒发令总数(PunishMent the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPunishMentViewListCnt(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		
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
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);
				
		paramMap.put("TRANS_NO", "642");// 642为惩戒发令
		return transactionViewDao.getPunishMentViewListCnt(paramMap);
	}

	/**
	 * 批量取消薪资调整发令(batch cancel transaction for PaAdjust)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int cancelPaAdjustBatchInsideByNo(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("searchPa");
			for (int i = 0; i < paramData.length; i++) {
				Map map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
				map.put("EXP_INSIDE_NO", paramData[i]);
				// 决裁流程里未决裁且发令表里未生效
				int count = this.transactionViewDao
						.getPassHrAffirmCountByExpInsideNo(map)
						+ this.transactionViewDao
								.ifEffectByExpInsidePaAdjustNo(map);
				if (count > 0) {
					return 2;
				}
				list.add(map);
			}
			this.transactionViewDao.cancelHrPaAdjustByNo(list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 薪资调整发令列表(PaAdjust the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaAdjustViewList(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "16199");// 16199为薪资调整发令
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		List list = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionViewDao.getPaAdjustViewList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionViewDao.getPaAdjustViewList(paramMap);
		}
		List returnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("affirmerList", this.transactionAffirmDao
					.getAffirmerListByExpInsideNo(paramMap));
			returnList.add(paramMap);
		}
		return returnList;
	}
	/**
	 * 薪资调整发令总数(PaAdjust the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPaAdjustViewListCnt(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("TRANS_NO", "16199");// 16199为薪资调整发令
		return transactionViewDao.getPaAdjustViewListCnt(paramMap);
	}

	/**
	 * 批量取消代理发令(batch cancel transaction for Agent)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int cancelAgentBatchInsideByNo(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("searchAgent");
			for (int i = 0; i < paramData.length; i++) {
				Map map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
				map.put("EXP_INSIDE_NO", paramData[i]);
				// 决裁流程里未决裁且发令表里未生效
				int count = this.transactionViewDao
						.getPassHrAffirmCountByExpInsideNo(map)
						+ this.transactionViewDao
								.ifEffectByExpInsideAgentNo(map);
				if (count > 0) {
					return 2;
				}
				list.add(map);
			}
			this.transactionViewDao.cancelHrAgentByNo(list);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 代理发令列表(Agent the starting list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAgentViewList(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "16196");// 16196为代理发令
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		List list = new ArrayList();
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionViewDao.getAgentViewList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionViewDao.getAgentViewList(paramMap);
		}
		List returnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			paramMap.put("affirmerList", this.transactionAffirmDao
					.getAffirmerListByExpInsideNo(paramMap));
			returnList.add(paramMap);
		}
		return returnList;
	}
	/**
	 * 代理发令总数(Agent the total)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getAgentViewListCnt(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		if (request.getParameter("PERSON_ID") != null
				&& request.getParameter("PERSON_ID").length() > 0) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		paramMap.put("TRANS_NO", "16196");// 16196为代理发令
		return transactionViewDao.getAgentViewListCnt(paramMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getConfirmReqHire(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("USER_NO", admin.getUserNo());

		String   ls = "";
		String[] isChecked = request.getParameterValues("hr0504Check");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
			}
		}
		paramMap.put("REQ_IDS", ls);
		paramMap.put("TRANS_NO", "1359");// 1359为入职发令
		
		return transactionViewDao.getEntryTransViewList(paramMap);
	}

	@Autowired
	private InfoApplySer  infoApplySer ;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getApplyFeeList(String deptNo, HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		//return this.infoApplySer.getAffirmorListByDept("3325",deptNo, admin.getPersonId(), "", "");
		return this.infoApplySer.getAffirmorListByString("3325", admin.getPersonId(), "", "", admin.getLanguage());
	}

}
