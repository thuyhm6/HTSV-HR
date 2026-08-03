package com.ait.ess.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.TransactionAffirmDao;
import com.ait.ess.service.TransactionAffirmSer;
import com.ait.hrm.dao.TransferOrderDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 发令决裁(Transaction affirm)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: TransactionAffirmSerImpl.java
 * @Description:
 * @Create date: Feb 20, 2012 2:49:56 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 20, 2012 2:49:56 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Service
public class TransactionAffirmSerImpl implements TransactionAffirmSer {

	Logger logger = Logger.getLogger(TransactionAffirmSerImpl.class);
	@Autowired
	private TransferOrderDao transferOrderDao;
	@Autowired
	private TransactionAffirmDao transactionAffirmDao;

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
		paramMap.put("ADMIN_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return paramMap;
	}

	/**
	 * 入职决裁列表(entry transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEntryTransAfiirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);

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
		if(request.getParameter("seach_STATUS_CODE")==null && ( paramMap.get("STATUS_CODE")==null || ((String)paramMap.get("STATUS_CODE")).equals(""))){
			paramMap.put("STATUS_CODE", "3530");
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMIN_ID", admin.getAdminID());
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);
				
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getEntryTransAfiirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionAffirmDao.getEntryTransAfiirmList(paramMap);
		}

		return this.getAffirmerListForTrans(list, request);
	}

	/**
	 * 入职决裁的总数(get entry transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getEntryTransAfiirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
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
		if(request.getParameter("seach_STATUS_CODE")==null && ( paramMap.get("STATUS_CODE")==null || ((String)paramMap.get("STATUS_CODE")).equals(""))){
			paramMap.put("STATUS_CODE", "3530");
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMIN_ID", admin.getAdminID());
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);
		
		return transactionAffirmDao.getEntryTransAfiirmListCnt(paramMap);
	}

	/**
	 * 薪资调整决裁列表(view salary adjustment transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaAdjustTransAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "16199");// 16199为薪资调整发令
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getPaAdjustTransAffirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionAffirmDao.getPaAdjustTransAffirmList(paramMap);
		}
		return this.getAffirmerListForTrans(list, request);
	}

	/**
	 * 薪资调整决裁总数(get salary adjustment transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPaAdjustTransAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "16199");// 16199为薪资调整发令
		return transactionAffirmDao.getPaAdjustTransAffirmListCnt(paramMap);
	}

	/**
	 * 转正决裁列表(view probation transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getProbationTransAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1360");// 1360为转正发令

		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getProbationTransAffirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionAffirmDao.getProbationTransAffirmList(paramMap);
		}

		return this.getAffirmerListForTrans(list, request);
	}

	/**
	 * 转正决裁总数(get probation transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getProbationTransAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1360");// 1360为转正发令
		return transactionAffirmDao.getProbationTransAffirmListCnt(paramMap);
	}

	/**
	 * 代理决裁列表(view agent transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAgentTransAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "16196");// 16196为代理发令
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getAgentTransAffirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionAffirmDao.getAgentTransAffirmList(paramMap);
		}

		return this.getAffirmerListForTrans(list, request);
	}

	/**
	 * 代理决裁总数(get agent transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getAgentTransAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "16196");// 16196为代理发令
		return transactionAffirmDao.getAgentTransAffirmListCnt(paramMap);
	}

	/**
	 * 奖励决裁列表(view reward transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getRewardTransAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "641");// 641为奖励发令
		
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
		if(request.getParameter("seach_STATUS_CODE")==null && ( paramMap.get("STATUS_CODE")==null || ((String)paramMap.get("STATUS_CODE")).equals(""))){
			paramMap.put("STATUS_CODE", "3530");
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMIN_ID", admin.getAdminID());
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);
		
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getRewardTransAffirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionAffirmDao.getRewardTransAffirmList(paramMap);
		}

		return this.getAffirmerListForTrans(list, request);
	}

	/**
	 * 奖励决裁总数(get reward transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getRewardTransAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "641");// 641为奖励发令
		
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
		if(request.getParameter("seach_STATUS_CODE")==null && ( paramMap.get("STATUS_CODE")==null || ((String)paramMap.get("STATUS_CODE")).equals(""))){
			paramMap.put("STATUS_CODE", "3530");
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMIN_ID", admin.getAdminID());
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);
		
		return transactionAffirmDao.getRewardTransAffirmListCnt(paramMap);
	}

	/**
	 * 惩戒决裁列表(view punishment transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPunishmentTransAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "642");// 642为惩戒发令
		
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
		if(request.getParameter("seach_STATUS_CODE")==null && ( paramMap.get("STATUS_CODE")==null || ((String)paramMap.get("STATUS_CODE")).equals(""))){
			paramMap.put("STATUS_CODE", "3530");
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMIN_ID", admin.getAdminID());
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);
								
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getPunishmentTransAffirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionAffirmDao.getPunishmentTransAffirmList(paramMap);
		}

		return this.getAffirmerListForTrans(list, request);
	}

	/**
	 * 惩戒决裁总数(get punishment transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPunishmentTransAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "642");// 642为惩戒发令
		
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
		if(request.getParameter("seach_STATUS_CODE")==null && ( paramMap.get("STATUS_CODE")==null || ((String)paramMap.get("STATUS_CODE")).equals(""))){
			paramMap.put("STATUS_CODE", "3530");
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMIN_ID", admin.getAdminID());
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);
				
		return transactionAffirmDao.getPunishmentTransAffirmListCnt(paramMap);
	}

	/**
	 * 离职决裁列表(view resignation transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getResignationTransAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "643");// 643为离职发令

		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getResignationTransAffirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionAffirmDao.getResignationTransAffirmList(paramMap);
		}

		return this.getAffirmerListForTrans(list, request);
	}

	/**
	 * 离职决裁总数(get resignation transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getResignationTransAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "643");// 643为离职发令
		return transactionAffirmDao.getResignationTransAffirmListCnt(paramMap);
	}

	/**
	 * 调动决裁列表(view transaction transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getTransferTransAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1365");// 1365为调动发令

		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getTransferTransAffirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionAffirmDao.getTransferTransAffirmList(paramMap);
		}

		return this.getAffirmerListForTrans(list, request);
	}

	/**
	 * 调动决裁总数(get transaction transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getTransferTransAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1365");// 1365为调动发令
		return transactionAffirmDao.getTransferTransAffirmListCnt(paramMap);
	}

	/**
	 * 升职/降职决裁列表(view promotion and relegation transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPromotRelegatTransAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1363");// 1363为升职/降职发令

		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getPromotRelegatTransAffirmList(
					paramMap, UiUtil.getPageNum(request), UiUtil
							.getNumPerPage(request));
		} else {
			list = transactionAffirmDao
					.getPromotRelegatTransAffirmList(paramMap);
		}

		return this.getAffirmerListForTrans(list, request);
	}

	/**
	 * 升职/降职决裁总数(get promotion and relegation transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPromotRelegatTransAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1363");// 1363为升职/降职发令

		return transactionAffirmDao
				.getPromotRelegatTransAffirmListCnt(paramMap);
	}

	/**
	 * 兼职决裁列表(view plurality transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPluralityTransAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1361");// 1361为兼职发令

		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getPluralityTransAffirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionAffirmDao.getPluralityTransAffirmList(paramMap);
		}

		return this.getAffirmerListForTrans(list, request);
	}

	/**
	 * 兼职决裁总数(get plurality transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPluralityTransAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "1361");// 1361为兼职发令

		return transactionAffirmDao.getPluralityTransAffirmListCnt(paramMap);
	}

	/**
	 * 停职/复职决裁列表(view suspension transaction affirm list
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSuspensionTransAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "16193");// 16193为停职/复职发令
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getSuspensionTransAffirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = transactionAffirmDao.getSuspensionTransAffirmList(paramMap);
		}

		return this.getAffirmerListForTrans(list, request);
	}

	/**
	 * 停职/复职决裁总数(get suspension transaction affirm total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getSuspensionTransAffirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "16193");// 16193为停职/复职发令
		return transactionAffirmDao.getSuspensionTransAffirmListCnt(paramMap);
	}

	/**
	 * 批量通过/否决入职发令(batch pass and reject entry transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveEntryTransInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装入职发令数据并处理
			this.transactionAffirmDao.saveApproveEntryTransInBatch(this
					.encapsulationTransAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 通过/否决入职发令(pass and reject entry transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveEntryTrans(HttpServletRequest request) throws Exception {
		try {
			// 封装入职发令数据并处理
			this.transactionAffirmDao.saveApproveEntryTrans(this
					.encapsulationTransAffirmMap(request, true));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量通过/否决薪资调整发令(batch pass and reject salary adjustment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveSalaryAdjustTransInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 封装发令数据并处理
			this.transactionAffirmDao.saveApproveSalaryAdjustTransInBatch(this
					.encapsulationTransAffirmListForBatch(request));
		} catch (Exception e) {

			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 通过/否决薪资调整发令(batch pass and reject salary adjustment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveSalaryAdjustTrans(HttpServletRequest request)
			throws Exception {
		try {
			// 封装薪资调整发令数据并处理
			this.transactionAffirmDao.saveApproveSalaryAdjustTrans(this
					.encapsulationTransAffirmMap(request, false));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量通过/否决转正调令(batch pass and reject Probation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveProbationTransInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装转正发令数据并处理
			this.transactionAffirmDao.saveApproveProbationTransInBatch(this
					.encapsulationTransAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 通过/否决转正调令(batch pass and reject Probation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveProbationTrans(HttpServletRequest request)
			throws Exception {
		try {
			// 封装转正发令数据并处理
			this.transactionAffirmDao.saveApproveProbationTrans(this
					.encapsulationTransAffirmMap(request, false));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量通过/否决代理调令(batch pass and reject agent transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveAgentTransInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装代理发令数据并处理
			this.transactionAffirmDao.saveApproveAgentTransInBatch(this
					.encapsulationTransAffirmListForBatch(request));
		} catch (Exception e) {

			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 通过/否决代理调令(pass and reject agent transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveAgentTrans(HttpServletRequest request) throws Exception {
		try {
			// 封装代理发令数据并处理
			this.transactionAffirmDao.saveApproveAgentTrans(this
					.encapsulationTransAffirmMap(request, false));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量通过/否决奖励调令(batch pass and reject Reward transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveRewardTransInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装奖励发令数据并处理
			this.transactionAffirmDao.saveApproveRewardTransInBatch(this
					.encapsulationTransAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 通过/否决奖励调令(pass and reject Reward transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveRewardTrans(HttpServletRequest request) throws Exception {
		try {
			// 封装奖励发令数据并处理
			this.transactionAffirmDao.saveApproveRewardTrans(this
					.encapsulationTransAffirmMap(request, false));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量通过/否决惩戒调令(batch pass and reject punishment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approvePunishmentTransInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装惩戒发令数据并处理
			this.transactionAffirmDao.saveApprovePunishmentTransInBatch(this
					.encapsulationTransAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 通过/否决惩戒调令(pass and reject Punishment transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approvePunishmentTrans(HttpServletRequest request)
			throws Exception {
		try {
			// 封装惩戒数据并处理
			this.transactionAffirmDao.saveApprovePunishmentTrans(this
					.encapsulationTransAffirmMap(request, false));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量通过/否决离职调令(batch pass and reject Resignation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveResignationTransInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装离职发令数据并处理
			this.transactionAffirmDao.saveApproveResignationTransInBatch(this
					.encapsulationTransAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 通过/否决离职调令(pass and reject Resignation transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveResignationTrans(HttpServletRequest request)
			throws Exception {
		try {
			// 封装离职发令数据并处理
			this.transactionAffirmDao.saveApproveResignationTrans(this
					.encapsulationTransAffirmMap(request, false));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量通过/否决调动发令(batch pass and reject Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveTransactionTransInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装调动发令数据并处理
			this.transactionAffirmDao.saveApproveTransactionTransInBatch(this
					.encapsulationTransAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 通过/否决调动发令(pass and reject Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveTransactionTrans(HttpServletRequest request)
			throws Exception {
		try {
			// 封装调动发令数据并处理
			this.transactionAffirmDao.saveApproveTransactionTrans(this.encapsulationTransAffirmMap(request, false));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量通过/否决升职/降职调令(batch pass and reject PromotRelegat transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approvePromotRelegatTransInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装发令数据并处理
			this.transactionAffirmDao.saveApprovePromotRelegatTransInBatch(this
					.encapsulationTransAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量通过/否决升职/降职调令(batch pass and reject PromotRelegat transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approvePromotRelegatTrans(HttpServletRequest request)
			throws Exception {
		try {
			// 封装发令数据并处理
			this.transactionAffirmDao.saveApprovePromotRelegatTrans(this
					.encapsulationTransAffirmMap(request, false));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量通过/否决兼职调令(batch pass and reject Plurality transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approvePluralityTransInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装发令数据并处理
			this.transactionAffirmDao.saveApprovePluralityTransInBatch(this
					.encapsulationTransAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 通过/否决兼职调令(pass and reject Plurality transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approvePluralityTrans(HttpServletRequest request)
			throws Exception {
		try {
			// 封装发令数据并处理
			this.transactionAffirmDao.saveApprovePluralityTrans(this
					.encapsulationTransAffirmMap(request, false));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量通过/否决停职/复职调令(batch pass and reject Suspension transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approveSuspensionTransInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装发令数据并进行处理
			this.transactionAffirmDao.saveApproveSuspensionTransInBatch(this.encapsulationTransAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 通过/否决 停职/复职调令(pass and reject Suspension transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approveSuspensionTrans(HttpServletRequest request)
			throws Exception {
		try {
			// 封装发令决裁数据并处理
			this.transactionAffirmDao.saveApproveSuspensionTrans(encapsulationTransAffirmMap(request, false));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 封装发令信息成map(encapsulation transaction from request to map)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap encapsulationTransAffirmMap(HttpServletRequest request, boolean ifEntryTrans) {
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面提交数据
			LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
			map.put("UPDATED_BY", admin.getPersonId());
			map.put("CREATED_BY", admin.getPersonId());
			map.put("CPNY_ID", admin.getCpnyId());
			map.put("USER_NO", this.transactionAffirmDao.getSyUserSeqNextVal());

			// 决裁级别
			String currentAffirmLevel = map.get("AFFIRM_LEVEL") != null ? map.get("AFFIRM_LEVEL").toString() : "0";
			// 决裁通过/否决标识
			String affirmFlag = map.get("AFFIRM_FLAG") != null ? map.get("AFFIRM_FLAG").toString() : "";
			// 生效时间
			String startDate = map.get("START_DATE") != null ? map.get("START_DATE").toString() : "";
			// 当前决裁者
			map.put("CURRENT_AFFIRM_ID", admin.getPersonId());
			// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
			int maxAffirmLevel = this.transactionAffirmDao.getMaxAffirmLevelByExpInsideNo(map);
			// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
			if (Integer.parseInt(currentAffirmLevel) < maxAffirmLevel) {
				if ("1".equals(affirmFlag)) {
					int nextAffirmLevel = Integer.parseInt(currentAffirmLevel) + 1;
					map.put("AFFIRM_LEVEL", nextAffirmLevel);
					LinkedHashMap nextHrAffirmMap = (LinkedHashMap) this.transactionAffirmDao.getHrAffirmInfoByExpInsideNoAndLevel(map);
					String nextAffirmerId = nextHrAffirmMap.get("AFFIRMOR_ID") != null ? nextHrAffirmMap.get("AFFIRMOR_ID").toString(): admin.getPersonId();
					map.put("NEXT_AFFIRM_ID", nextAffirmerId);
					map.put("ACTIVITY", "0");
				} else {
					map.put("NEXT_AFFIRM_ID", "");
					map.put("ACTIVITY", "2");
				}
			}
			Date sysDate = new Date();
			Date sysStartDate = null;
			SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");
			if ("".equals(startDate) || startDate.length() == 0) {
				sysStartDate = sysDate;
			} else {
				sysStartDate = sb.parse(startDate);
			}
			// 如果是决裁流程的最后一步且为通过时
			if (Integer.parseInt(currentAffirmLevel) == maxAffirmLevel) {
				if ("1".equals(affirmFlag)) {
					// 生效时间不晚于当前时间时,做个标识
					if (sysStartDate.before(sysDate) || sysStartDate.equals(sysDate)) {
						map.put("FLAG", "1");
						map.put("ACTIVITY", "1");
						map.put("PASS_REJ_FLAG", "1");
					} else {
						map.put("ACTIVITY", "0");
					}
				} else {
					map.put("ACTIVITY", "2");
				}
				map.put("NEXT_AFFIRM_ID", "");
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 批量封装发令信息成List(encapsulation transaction from request to List for batch)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List encapsulationTransAffirmListForBatch(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String affirmFlag = paramMap.get("AFFIRM_FLAG") != null ? paramMap.get("AFFIRM_FLAG").toString() : "";
			String[] paramData = request.getParameterValues("c1");
			
			for (int i = 0; i < paramData.length; i++) {
				Map map = new LinkedHashMap();
				String payStep = paramMap.get(paramData[i] + "_PAY_STEP_NO") != null ? paramMap.get(paramData[i] + "_PAY_STEP_NO").toString(): "";
				String oldPostGrade = paramMap.get(paramData[i] + "_OLD_POST_GRADE_NO") != null ? paramMap.get(paramData[i] + "_OLD_POST_GRADE_NO").toString(): "";
				map.put("PAY_STEP_NO", payStep);
				map.put("OLD_POST_GRADE_NO", oldPostGrade);	
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("CREATED_BY", admin.getPersonId());
				map.put("ADMIN_ID", admin.getPersonId());
				map.put("EXP_INSIDE_NO", paramData[i]);
				map.put("AFFIRM_FLAG", affirmFlag);
				map.put("CURRENT_AFFIRM_ID", admin.getPersonId());
				map.put("USER_NO", this.transactionAffirmDao.getSyUserSeqNextVal());
				map.put("CPNY_ID", admin.getCpnyId());

				String currentAffirmLevel = paramMap.get(paramData[i]+ "_AFFIRM_LEVEL") != null ? paramMap.get(paramData[i] + "_AFFIRM_LEVEL").toString() : "0";
				String hrAffirmNo = paramMap.get(paramData[i] + "_HR_AFFIRM_NO") != null ? paramMap.get(paramData[i] + "_HR_AFFIRM_NO").toString() : "";
				String affirmorNo = paramMap.get(paramData[i] + "_AFFIRMOR_ID") != null ? paramMap.get(paramData[i] + "_AFFIRMOR_ID").toString(): "";
				String startDate = paramMap.get(paramData[i] + "_START_DATE") != null ? paramMap.get(paramData[i] + "_START_DATE").toString(): "";
				String empId = paramMap.get(paramData[i] + "_EMPID") != null ? paramMap.get(paramData[i] + "_EMPID").toString(): "";
				String personId = paramMap.get(paramData[i] + "_PERSON_ID") != null ? paramMap.get(paramData[i] + "_PERSON_ID").toString(): "";
				String relExpInsideNo = paramMap.get(paramData[i]+ "_RELATION_EXP_INSIDE_NO") != null ? paramMap.get(paramData[i] + "_RELATION_EXP_INSIDE_NO").toString(): "";
				String itemNo = paramMap.get(paramData[i] + "_ITEM_NO") != null ? paramMap.get(paramData[i] + "_ITEM_NO").toString(): "";
				String empTypeCode = paramMap.get(paramData[i]+ "_EMP_TYPE_CODE") != null ? paramMap.get(paramData[i] + "_EMP_TYPE_CODE").toString() : "";
				String returnValue = paramMap.get(paramData[i]+ "_RETURN_VALUE") != null ? paramMap.get(paramData[i] + "_RETURN_VALUE").toString() : "";
				String startDates = paramMap.get(paramData[i] + "_START_DATE") != null ? paramMap.get(paramData[i] + "_START_DATE").toString(): "";
				String transCode = paramMap.get(paramData[i] + "_TRANS_CODE") != null ? paramMap.get(paramData[i] + "_TRANS_CODE").toString(): "";
				String statusCode = paramMap.get(paramData[i] + "_STATUS_CODE") != null ? paramMap.get(paramData[i] + "_STATUS_CODE").toString(): "";
				String workArea=paramMap.get(paramData[i] + "_WORK_AREA") != null ? paramMap.get(paramData[i] + "_WORK_AREA").toString(): "";
				//String empTypeCode=paramMap.get(paramData[i] + "_EMP_TYPE_CODE") != null ? paramMap.get(paramData[i] + "_EMP_TYPE_CODE").toString(): "";
				String gradeLevel=paramMap.get(paramData[i] + "_GRADE_LEVEL") != null ? paramMap.get(paramData[i] + "_GRADE_LEVEL").toString(): "";
				map.put("TRANS_CODE", transCode);
				map.put("ITEM_NO", itemNo);
				map.put("EMP_TYPE_CODE", empTypeCode);
				map.put("RETURN_VALUE", returnValue);
				map.put("AFFIRM_LEVEL", currentAffirmLevel);
				map.put("HR_AFFIRM_NO", hrAffirmNo);
				map.put("AFFIRMOR_ID", affirmorNo);
				map.put("AFFIRM_FLAG", affirmFlag);
				map.put("EMPID", empId);
				map.put("PERSON_ID", personId);
				map.put("RELATION_EXP_INSIDE_NO", relExpInsideNo);
				map.put("START_DATE", startDates);
				map.put("STATUS_CODE",statusCode);
				map.put("WORK_AREA", workArea);
				map.put("GRADE_LEVEL", gradeLevel);
				// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
				int maxAffirmLevel = this.transactionAffirmDao.getMaxAffirmLevelByExpInsideNo(map);
				// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
				if (Integer.parseInt(currentAffirmLevel) < maxAffirmLevel) {
					if ("1".equals(affirmFlag)) {
						int nextAffirmLevel = Integer.parseInt(currentAffirmLevel) + 1;
						map.put("AFFIRM_LEVEL", nextAffirmLevel);
						LinkedHashMap nextHrAffirmMap = (LinkedHashMap) this.transactionAffirmDao.getHrAffirmInfoByExpInsideNoAndLevel(map);
						String nextAffirmerId = nextHrAffirmMap.get("AFFIRMOR_ID") != null ? nextHrAffirmMap.get("AFFIRMOR_ID").toString() : admin.getPersonId();
						map.put("NEXT_AFFIRM_ID", nextAffirmerId);
						map.put("ACTIVITY", "0");
					} else {
						map.put("NEXT_AFFIRM_ID", "");
						map.put("ACTIVITY", "2");
					}
				}
				Date sysDate = new Date();
				Date sysStartDate = null;
				SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");

				if ("".equals(startDate) || startDate.length() == 0) {
					sysStartDate = sysDate;
				} else {
					sysStartDate = sb.parse(startDate);
				}
				// 如果是决裁流程的最后一步且为通过时
				if (currentAffirmLevel.equals(String.valueOf(maxAffirmLevel))) {
					if ("1".equals(affirmFlag)) {
						// 生效时间不晚于当前时间时,做个标识
						if (sysStartDate.before(sysDate) || sysStartDate.equals(sysDate)) {
							map.put("FLAG", "1");
							map.put("ACTIVITY", "1");
						} else {
							map.put("ACTIVITY", "0");
						}
					} else {						
						map.put("ACTIVITY", "2");
					}
					map.put("NEXT_AFFIRM_ID", "");
				}
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 遍历LIST取出每个发令的所有决裁者(traverse list for get every transaction's affimer
	 * list)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List getAffirmerListForTrans(List list, HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		LinkedHashMap paramMap = null;
		LinkedHashMap childMap = null;

		for (int i = 0; i < list.size(); i++) {
			paramMap = (LinkedHashMap) list.get(i);
			String hrefFlag = "1";
			List aList = this.transactionAffirmDao.getAffirmerListByExpInsideNo(paramMap);
			List childList = new ArrayList();
			for (int j = 0; j < aList.size(); j++) {
				childMap = (LinkedHashMap) aList.get(j);
				childMap.put("HREF_FLAG", hrefFlag);
				String affirmFlag = childMap.get("AFFIRM_FLAG") != null ? childMap.get("AFFIRM_FLAG").toString(): "";
				// 如果这一步未决裁或者决裁未通过,则下一步通过和否决的链接屏蔽
				if ("0".equals(affirmFlag) || "2".equals(affirmFlag)) {
					hrefFlag = "0";
				} else {
					hrefFlag = "1";
				}
				childList.add(childMap);
			}
			hrefFlag = "0";
			paramMap.put("affirmerList", childList);
			paramMap.put("ADMIN_ID", admin.getPersonId());
			returnList.add(paramMap);
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getPayStepAffirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "121975");// 121975为号俸发令
		
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getPayStepTransAffirmList(
					paramMap, UiUtil.getPageNum(request), UiUtil
							.getNumPerPage(request));
		} else {
			list = transactionAffirmDao
					.getPayStepTransAffirmList(paramMap);
		}

		return this.getAffirmerListForTrans(list, request);
	}
	
	/**
	 * 批量通过号俸发令决裁
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approvePayStepTransInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装发令数据并处理
			this.transactionAffirmDao.saveApprovePayStepTransInBatch(this
					.encapsulationTransAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public Object getPayStepTransAffirmListCnt(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", "121975");

		return transactionAffirmDao
				.getPayStepTransAffirmListCnt(paramMap);
	}
	
	public int approvePayStepTrans(HttpServletRequest request)
		throws Exception {
			try {
				// 封装发令数据并处理
				this.transactionAffirmDao.saveApprovePayStepTrans(this
						.encapsulationTransAffirmMap(request, false));
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
	}
	
	@SuppressWarnings("unchecked")
	public List getHrExperienceInsideSaveByTransCodeByEss(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANSNO", request.getParameter("OrderType"));// 121975为号俸发令
		paramMap.put("LAN", admin.getLanguage());// 121975为号俸发令
		paramMap.put("CREAATDBY", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getHrExperienceInsideSaveByTransCodeByEss(
					paramMap, UiUtil.getPageNum(request), UiUtil
							.getNumPerPage(request));
		} else {
			list = transactionAffirmDao
					.getHrExperienceInsideSaveByTransCodeByEss(paramMap);
		}
		return list;
		//return this.getAffirmerListForTrans(list, request);
	}
	@SuppressWarnings("unchecked")
	public int getHrExperienceInsideSaveByTransCodeByEssCnt(HttpServletRequest request)
			throws Exception {
		int obj=-1;
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANSNO", request.getParameter("OrderType"));// 121975为号俸发令
		paramMap.put("LAN", admin.getLanguage());// 121975为号俸发令
		paramMap.put("CREAATDBY", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			list = transactionAffirmDao.getHrExperienceInsideSaveByTransCodeByEss(
					paramMap, UiUtil.getPageNum(request), UiUtil
							.getNumPerPage(request));
		} else {
			list = transactionAffirmDao
					.getHrExperienceInsideSaveByTransCodeByEss(paramMap);
		}
		return obj;
		//return this.getAffirmerListForTrans(list, request);
	}
	/**
	 * 异动决裁列表(view reward transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List ViewHrExpInsideList(HttpServletRequest request)
			throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", request.getParameter("seach_OrderType"));
		paramMap.put("CODE", request.getParameter("seach_OrderType"));
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("LAN", admin.getLanguage());
		paramMap.put("TRANS_SEARCH_FLAG", "1");
		paramMap.put("ADMIN_ID", admin.getAdminID());
		List listTitle=this.transferOrderDao.getTranferOrderTitileInside(paramMap);
		String sql="";
		for(int i=0;i<listTitle.size();i++){
			sql=sql+","+((Map) listTitle.get(i)).get("INSIDE_TRANS").toString();	
		}
		sql=sql.substring( 1, sql.length());
		paramMap.put("sql",sql+",");
		paramMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
		paramMap.put("EMPID",request.getParameter("seach_KEY"));
		paramMap.put("START_DATE",request.getParameter("seach_FROM_TIME"));
		paramMap.put("END_DATE",request.getParameter("seach_TO_TIME"));
		paramMap.put("ACTIVITY",request.getParameter("seach_ACTIVITY"));
		paramMap.put("BIANHAO",request.getParameter("seach_DIAOLING_BIANHAO"));
		
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
		if(request.getParameter("seach_STATUS_CODE")==null && ( paramMap.get("STATUS_CODE")==null || ((String)paramMap.get("STATUS_CODE")).equals(""))){
			paramMap.put("STATUS_CODE", "3530");
		}
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);
		
		//List insideList=this.transactionAffirmDao.getTransferTransAffirmList(paramMap);
	
		List insideList1=new ArrayList<Object>();
		//for (int i = 0; i < insideList.size(); i++) {
		//	Map m= (LinkedHashMap) insideList.get(i);
		//	m.put("affirmerList", this.transactionAffirmDao.getAffirmerListByExpInsideNo(m));
		//	insideList1.add(m);		
		//}
		if (UiUtil.getPageNum(request) > 0) {
			insideList1 = transactionAffirmDao.getHrExpInsideList(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			insideList1 = transactionAffirmDao.getHrExpInsideList(paramMap);
		}

		return this.getAffirmerListForTrans(insideList1, request);
	}
	/**
	 * 异动决裁列表总数(view reward transaction affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int ViewHrExpInsideListCnt(HttpServletRequest request)
			throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("TRANS_NO", request.getParameter("seach_OrderType"));
		paramMap.put("CODE", request.getParameter("seach_OrderType"));
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("LAN", admin.getLanguage());
		paramMap.put("TRANS_SEARCH_FLAG", "1");
		paramMap.put("ADMIN_ID", admin.getAdminID());
		List listTitle=this.transferOrderDao.getTranferOrderTitileInside(paramMap);
		String sql="";
		for(int i=0;i<listTitle.size();i++){
			sql=sql+","+((Map) listTitle.get(i)).get("INSIDE_TRANS").toString();	
		}
		sql=sql.substring( 1, sql.length());
		paramMap.put("sql",sql+",");
		paramMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
		paramMap.put("EMPID",request.getParameter("seach_KEY"));
		paramMap.put("START_DATE",request.getParameter("seach_FROM_TIME"));
		paramMap.put("END_DATE",request.getParameter("seach_TO_TIME"));
		paramMap.put("ACTIVITY",request.getParameter("seach_ACTIVITY"));
		paramMap.put("BIANHAO",request.getParameter("seach_DIAOLING_BIANHAO"));
		
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
		if(request.getParameter("seach_STATUS_CODE")==null && ( paramMap.get("STATUS_CODE")==null || ((String)paramMap.get("STATUS_CODE")).equals(""))){
			paramMap.put("STATUS_CODE", "3530");
		}
		logger.debug("pMap2:::"+paramMap);
		paramMap.put("ACTIVITY", aCode);				
		
		int count=this.transactionAffirmDao.getHrExpInsideListCnt(paramMap);
	
		return count;
	}

	
	
}