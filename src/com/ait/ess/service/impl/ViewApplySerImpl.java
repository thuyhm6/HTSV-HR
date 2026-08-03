package com.ait.ess.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.ViewApplyDao;
import com.ait.ess.service.ViewApplySer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 申请信息查看(apply information view) Copyright: LDCC Company: LDCC
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
public class ViewApplySerImpl implements ViewApplySer {

	Logger logger = Logger.getLogger(ViewApplySerImpl.class);

	@Autowired
	private ViewApplyDao viewApplyDao;

	/**
	 * 通过request请求封装查询条件(get search conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	private LinkedHashMap getLinkedMapByRequestForSearch(
			HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}

	/**
	 * 通过request请求封装查询条件(get conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	private LinkedHashMap getLinkedMapByRequest(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		return paramMap;
	}

	/**
	 * 个人信息申请(view personal apply information)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewPersonalInfoList(HttpServletRequest request)
			throws Exception {
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMIN_ID", admin.getAdminID()!=null?admin.getAdminID().toString():"");
		
		if (UiUtil.getPageNum(request) > 0) {
			returnList = viewApplyDao.viewPersonalInfoList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = viewApplyDao.viewPersonalInfoList(paramMap);
		}
		return returnList;
	}

	/**
	 * 加班信息申请(view overtime apply information)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewOvertimeInfoList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		paramMap.put("PERSON_ID",admin.getPersonId());
		paramMap.put("menuNum", request.getParameter("menuNum"));
		
		if (UiUtil.getPageNum(request) > 0) {
			list = viewApplyDao.viewOvertimeInfoList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = viewApplyDao.viewOvertimeInfoList(paramMap);
		}
		List returnList = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		LinkedHashMap childMap = new LinkedHashMap();
		List affirmerList = null;
		for (int i = 0; i < list.size(); i++) {
			// 获得人事决裁状态和是否可删除标识(get person-confirm-status and if can be deleted)
			map = (LinkedHashMap) list.get(i);
			// 查询决裁者集合并存入返回的list里面
			paramMap.put("APPLY_NO", map.get("APPLY_NO"));
			affirmerList = viewApplyDao.getAffirmorList(paramMap);
			map.put("affirmerList", affirmerList);

			String applyFromDateStr = "";
			String applyToDateStr = "";
			String fromTIme = "";
			String toTime = "";
			String applyTypeCode = map.get("APPLY_TYPE_CODE") != null ? map.get("APPLY_TYPE_CODE").toString() : "";

			if (!"32".equals(applyTypeCode)) {
				if (map.get("OT_FROM_TIME") != null && !"".equals(map.get("OT_FROM_TIME").toString())) {
					fromTIme = map.get("OT_FROM_TIME") != null ? map.get("OT_FROM_TIME").toString() : "";
					toTime = map.get("OT_TO_TIME") != null ? map.get("OT_TO_TIME").toString() : "";

					applyFromDateStr = fromTIme.substring(0, 10);
					applyToDateStr = toTime.substring(0, 10);
					childMap.put("CPNY_ID", paramMap.get("CPNY_ID"));
					childMap.put("APPLY_FROM_DATE_STR", applyFromDateStr);
					childMap.put("APPLY_TO_DATE_STR", applyToDateStr);
					childMap.put("FROM_DATE", fromTIme);
					childMap.put("TO_DATE", toTime);
					String otLength = map.get("OT_LENGTH") != null ? map.get("OT_LENGTH").toString() : "0";
					String deductLength = "0";

					childMap.put("OT_APPLY_TYPE_CODE", applyTypeCode);
					if (this.viewApplyDao.getDeductFromTimeByCpnyId(childMap) > 0) {
						//----- 获得申请的长度
						deductLength = this.viewApplyDao.getDeductTimeCountInOtTimeByCpnyId(childMap);
					}
					map.put("OT_LENGTH", Double.parseDouble(otLength) - Double.parseDouble(deductLength));
				}
			}
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 休假信息申请(view leave apply information)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewLeaveInfoList(HttpServletRequest request) throws Exception {
		List list = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String menuNo=request.getParameter("menuNo");
		// 123634为喜丧假申请   123646 调休 21休假
		if(menuNo.equals("123649")||menuNo.equals("124822")){
			paramMap.put("TYPE", "123634");
			paramMap.put("APPLY_TYPE_NO", "123634");// 21为休假申请
		}else if(menuNo.equals("123650")||menuNo.equals("124823")){
			paramMap.put("TYPE", "123646");
			paramMap.put("APPLY_TYPE_NO", "123645");// 21为休假申请
		}else if(menuNo.equals("2441")||menuNo.equals("2449")||menuNo.equals("2454")||menuNo.equals("2479")){
			paramMap.put("TYPE", "21");
			paramMap.put("APPLY_TYPE_NO", "21");// 21为休假申请
		}else{
			paramMap.put("TYPE", "0");
		}
		
		if( admin.getCpnyId() != "C11" )
		{
			if (UiUtil.getPageNum(request) > 0) {
				list = viewApplyDao.viewLeaveInfoList(paramMap, UiUtil
						.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				list = viewApplyDao.viewLeaveInfoList(paramMap);
			}
		}
		else
		{
			if (UiUtil.getPageNum(request) > 0) {
				list = viewApplyDao.viewLeaveInfoList_c11(paramMap, UiUtil
						.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				list = viewApplyDao.viewLeaveInfoList_c11(paramMap);
			}
		}
		List returnList = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		List affirmerList = null;
		for (int i = 0; i < list.size(); i++) {
			// 获得人事决裁状态和是否可删除标识(get person-confirm-status and if can be deleted)
			map = (LinkedHashMap) list.get(i);
			// 查询决裁者集合并存入返回的list里面
			paramMap.put("APPLY_NO", map.get("APPLY_NO"));
			affirmerList = viewApplyDao.getAffirmorList(paramMap);
			map.put("AFFIRM_FLAG", viewApplyDao
					.getAffirmedApplyInfoCntByApplyNo(paramMap));
			map = this.getOpStatusAndDeleleStatus(map);
			map.put("affirmerList", affirmerList);
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 出差信息申请(view evection apply information)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewEvectionInfoList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "18");// 18为出差申请

		if (UiUtil.getPageNum(request) > 0) {
			list = viewApplyDao.viewEvectionInfoList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = viewApplyDao.viewEvectionInfoList(paramMap);
		}
		List returnList = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		List affirmerList = null;
		for (int i = 0; i < list.size(); i++) {
			// 获得人事决裁状态和是否可删除标识(get person-confirm-status and if can be deleted)
			map = (LinkedHashMap) list.get(i);
			// 查询决裁者集合并存入返回的list里面
			paramMap.put("APPLY_NO", map.get("APPLY_NO"));
			affirmerList = viewApplyDao.getAffirmorList(paramMap);
			map.put("AFFIRM_FLAG", viewApplyDao
					.getAffirmedApplyInfoCntByApplyNo(paramMap));
			map = this.getOpStatusAndDeleleStatus(map);
			map.put("affirmerList", affirmerList);
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 外出信息申请(view egression apply information)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewEgressionInfoList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "16201");// 16201为外出申请

		if (UiUtil.getPageNum(request) > 0) {
			list = viewApplyDao.viewEgressionInfoList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = viewApplyDao.viewEgressionInfoList(paramMap);
		}
		List returnList = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		List affirmerList = null;
		for (int i = 0; i < list.size(); i++) {
			// 获得人事决裁状态和是否可删除标识(get person-confirm-status and if can be deleted)
			map = (LinkedHashMap) list.get(i);
			// 查询决裁者集合并存入返回的list里面
			paramMap.put("APPLY_NO", map.get("APPLY_NO"));
			affirmerList = viewApplyDao.getAffirmorList(paramMap);
			map.put("AFFIRM_FLAG", viewApplyDao
					.getAffirmedApplyInfoCntByApplyNo(paramMap));
			map = this.getOpStatusAndDeleleStatus(map);
			map.put("affirmerList", affirmerList);
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 删除未审核个人信息申请(delete personal apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delPersonInfoApply(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return viewApplyDao.delPersonInfoApply(paramMap);
	}

	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delViewOtviewApply(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + admin.getCpnyId());
		return viewApplyDao.delViewOtviewApply(paramMap);
	}

	/**
	 * 删除未审核休假信息申请(delete leave apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delViewLeaveviewApply(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + admin.getCpnyId());
		return viewApplyDao.delViewLeaveviewApply(paramMap);
	}

	/**
	 * 删除未审核出差信息申请(delete evection apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delViewEvectionview(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + admin.getCpnyId());
		return viewApplyDao.delViewEvectionview(paramMap);
	}

	/**
	 * 删除未审核外出信息申请(delete egress apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delViewEgressionview(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("AR_DETAIL", "AR_DETAIL_" + admin.getCpnyId());
		return viewApplyDao.delViewEvectionview(paramMap);
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getAffirmorList(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return viewApplyDao.getAffirmorList(paramMap);
	}

	/**
	 * 个人信息申请个数(view personal apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int viewPersonalInfoListCnt(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("ADMIN_ID", admin.getAdminID()!=null?admin.getAdminID().toString():"");
		
		return viewApplyDao.viewPersonalInfoListCnt(paramMap);
	}

	/**
	 * 加班信息申请个数(view overtime apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int viewOvertimeInfoListCnt(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);

		paramMap.put("APPLY_TYPE_NO", "31");// 31为加班申请
		paramMap.put("menuNum", request.getParameter("menuNum"));
		return viewApplyDao.viewOvertimeInfoListCnt(paramMap);
	}
	
	/**
	 * 休假信息申请个数(view leave apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int viewLeaveInfoListCnt(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		String menuNo=request.getParameter("menuNo");
		paramMap.put("APPLY_TYPE_NO", "21");// 21为休假申请
		// 123634为喜丧假申请   123646 调休
		if(menuNo.equals("123649")||menuNo.equals("124822")){
			paramMap.put("TYPE", "123634");
			paramMap.put("APPLY_TYPE_NO", "123634");// 21为休假申请
		}else if(menuNo.equals("123650")||menuNo.equals("124823")){
			paramMap.put("TYPE", "123646");
			paramMap.put("APPLY_TYPE_NO", "123645");// 21为休假申请
		}else if(menuNo.equals("2441")||menuNo.equals("2449")||menuNo.equals("2454")||menuNo.equals("2479")){
			paramMap.put("TYPE", "21");
			paramMap.put("APPLY_TYPE_NO", "21");// 21为休假申请
		}else{
			paramMap.put("TYPE", "0");
		}
		return viewApplyDao.viewLeaveInfoListCnt(paramMap);
	}

	/**
	 * 出差信息申请个数(view evection apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int viewEvectionInfoListCnt(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "18");// 18为出差申请
		return viewApplyDao.viewEvectionInfoListCnt(paramMap);
	}

	/**
	 * 外出信息申请个数(view egression apply information count)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int viewEgressionInfoListCnt(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		paramMap.put("APPLY_TYPE_NO", "16201");// 16201为外出申请

		return viewApplyDao.viewEgressionInfoListCnt(paramMap);
	}

	/**
	 * 获得人事决裁状态和是否可删除标识(get person-confirm-status and if can be deleted)
	 * 
	 * @param map
	 * @return
	 */
	private LinkedHashMap getOpStatusAndDeleleStatus(LinkedHashMap map) {
		String activity = map.get("ACTIVITY") != null ? map.get("ACTIVITY")
				.toString() : "";// 人事确认标识
		String affirmFlag = map.get("AFFIRM_FLAG") != null ? map.get(
				"AFFIRM_FLAG").toString() : "";// 决裁标识

		if ("0".equals(activity)) {
			if ("0".equals(affirmFlag)) {
				map.put("DEL_FLAG", "1");// 设置是否删除标识
			} else {
				map.put("DEL_FLAG", "0");
			}
		} else {
			map.put("DEL_FLAG", "0");
		}
		return map;
	}

	
}