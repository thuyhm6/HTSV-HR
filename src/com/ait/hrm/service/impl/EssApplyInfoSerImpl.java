package com.ait.hrm.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.EssApplyInfoDao;
import com.ait.hrm.service.EssApplyInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
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
public class EssApplyInfoSerImpl implements EssApplyInfoSer {

	Logger logger = Logger.getLogger(EssApplyInfoSerImpl.class);

	@Autowired
	private EssApplyInfoDao EssApplyInfoDao;
	

	@Autowired
	private AuthorityUtil authorityUtil;

	/**
	 * 变更明细申请
	 */

	@SuppressWarnings("unchecked")
	@Override
	public int getEssApplyListCnt(HttpServletRequest request) {
		int retrunInt = 0;

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		if (admin.getStatNo() != null && !admin.getStatNo().equals("")) {
			paramMap.put("STAT_NO", admin.getStatNo());
		}

		retrunInt = EssApplyInfoDao.getEssApplyListCnt(paramMap);

		return retrunInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssApplyList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		if (admin.getStatNo() != null && !admin.getStatNo().equals("")) {
			paramMap.put("STAT_NO", admin.getStatNo());
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = EssApplyInfoDao.getEssApplyList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = EssApplyInfoDao.getEssApplyList(paramMap);
		}

		return retrunList;
	}

	/**
	 * start 申请所用数据 List
	 * 
	 * @param request
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List applyList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = this.getRequestMap(request);
		paramMap.put("ADDRESS_NO", request.getParameter("ADDRESS_NO"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		/*if(admin.getCpnyId().equals("SPC_NJ")){
			//当前时间
			Date data =new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String time = dateFormat.format(data); 
			paramMap.put("sDate", time);
			paramMap.put("eDate", time);
			paramMap.put("PERSON_ID_ID", admin.getPersonId());
			
		}*/
		retrunList = EssApplyInfoDao.applyList(paramMap);
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getPersonalApplyList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = this.getRequestMap(request);
		paramMap.put("PERSON_NO", request.getParameter("PERSON_NO"));

		retrunList = EssApplyInfoDao.getPersonalApplyList(paramMap);
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEssAddressApplyList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		Map paramMap = this.getRequestMap(request);
		paramMap.put("ADDRESS_NO", request.getParameter("ADDRESS_NO"));

		retrunList = EssApplyInfoDao.getEssAddressApplyList(paramMap);
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
		List retrunList = new ArrayList();
		Map paramMap = this.getRequestMap(request);
		paramMap.put("EMERGENCY_NO", request.getParameter("EMERGENCY_NO"));

		retrunList = EssApplyInfoDao.getEssEmergencyList(paramMap);
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
		List retrunList = new ArrayList();

		Map paramMap = this.getRequestMap(request);
		paramMap.put("FAMILY_NO", request.getParameter("FAMILY_NO"));

		retrunList = EssApplyInfoDao.getEssHomeRelationApplyList(paramMap);
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
		List retrunList = new ArrayList();

		Map paramMap = this.getRequestMap(request);
		paramMap.put("WORK_EXPER_NO", request.getParameter("WORK_EXPER_NO"));

		retrunList = EssApplyInfoDao.getEssWorkApplyList(paramMap);
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
		List retrunList = new ArrayList();

		Map paramMap = this.getRequestMap(request);
		paramMap.put("PRODUCT_NO", request.getParameter("PRODUCT_NO"));

		retrunList = EssApplyInfoDao.getEssProductApplyList(paramMap);
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
		List retrunList = new ArrayList();

		Map paramMap = this.getRequestMap(request);
		paramMap.put("EDUC_NO", request.getParameter("EDUC_NO"));

		retrunList = EssApplyInfoDao.getEssEducationApplyList(paramMap);
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
		List retrunList = new ArrayList();

		Map paramMap = this.getRequestMap(request);
		paramMap.put("QUAL_NO", request.getParameter("QUAL_NO"));

		retrunList = EssApplyInfoDao.getEssQualificationApplyList(paramMap);
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
		List retrunList = new ArrayList();

		Map paramMap = this.getRequestMap(request);
		paramMap.put("REWARD_NO", request.getParameter("REWARD_NO"));
		retrunList = EssApplyInfoDao.getEssRewardList(paramMap);
		return retrunList;
	}

	/**
	 * end 申请所用数据
	 */

	/**
	 * start 申请所用数据 单个 Object
	 * 
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
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("ADDRESS_NO", request.getParameter("ADDRESS_NO"));

		retrunList = EssApplyInfoDao.getPersonalApplyObject(paramMap);
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssAddressApplyObject(HttpServletRequest request) {
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("ADDRESS_NO", request.getParameter("ADDRESS_NO"));

		retrunList = EssApplyInfoDao.getEssAddressApplyObject(paramMap);
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
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("EMERGENCY_NO", request.getParameter("EMERGENCY_NO"));

		retrunList = EssApplyInfoDao.getEssEmergencyObject(paramMap);
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
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("FAMILY_NO", request.getParameter("FAMILY_NO"));

		retrunList = EssApplyInfoDao.getEssHomeRelationApplyObject(paramMap);
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
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("WORK_EXPER_NO", request.getParameter("WORK_EXPER_NO"));

		retrunList = EssApplyInfoDao.getEssWorkApplyObject(paramMap);
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
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("PRODUCT_NO", request.getParameter("PRODUCT_NO"));

		retrunList = EssApplyInfoDao.getEssProductApplyObject(paramMap);
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
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("EDUC_NO", request.getParameter("EDUC_NO"));

		retrunList = EssApplyInfoDao.getEssEducationApplyObject(paramMap);
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
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("QUAL_NO", request.getParameter("QUAL_NO"));

		retrunList = EssApplyInfoDao.getEssQualificationApplyObject(paramMap);
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
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());

		paramMap.put("REWARD_NO", request.getParameter("REWARD_NO"));
		retrunList = EssApplyInfoDao.getEssRewardObject(paramMap);
		return retrunList;
	}

	/**
	 * end 申请所用数据
	 */

	/**
	 * start 申请之前的 Object
	 * 
	 * @param request
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonalApplyObject2(HttpServletRequest request) {
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("ADDRESS_NO", request.getParameter("ADDRESS_NO"));
		paramMap.put("PERSON_ID_ID", request.getParameter("PERSON_ID_ID"));

		retrunList = EssApplyInfoDao.getPersonalApplyObject2(paramMap);
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEssAddressApplyObject2(HttpServletRequest request) {
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("ADDRESS_NO_NO", request.getParameter("ADDRESS_NO_NO"));

		retrunList = EssApplyInfoDao.getEssAddressApplyObject2(paramMap);
		return retrunList;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssEmergencyObject2(HttpServletRequest request) {
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("EMERGENCY_NO_NO", request.getParameter("EMERGENCY_NO_NO"));

		
		
		retrunList = EssApplyInfoDao.getEssEmergencyObject2(paramMap);
		
		return retrunList;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssHomeRelationApplyObject2(HttpServletRequest request) {
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("FAMILY_NO_NO", request.getParameter("FAMILY_NO_NO"));

		retrunList = EssApplyInfoDao.getEssHomeRelationApplyObject2(paramMap);
		return retrunList;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssWorkApplyObject2(HttpServletRequest request) {
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("WORK_EXPER_NO_NO", request
				.getParameter("WORK_EXPER_NO_NO"));

		retrunList = EssApplyInfoDao.getEssWorkApplyObject2(paramMap);
		return retrunList;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssProductApplyObject2(HttpServletRequest request) {
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("PRODUCT_NO_NO", request.getParameter("PRODUCT_NO_NO"));

		retrunList = EssApplyInfoDao.getEssProductApplyObject2(paramMap);
		return retrunList;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssEducationApplyObject2(HttpServletRequest request) {
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("EDUC_NO_NO", request.getParameter("EDUC_NO_NO"));

		retrunList = EssApplyInfoDao.getEssEducationApplyObject2(paramMap);
		return retrunList;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssQualificationApplyObject2(HttpServletRequest request) {
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("QUAL_NO_NO", request.getParameter("QUAL_NO_NO"));

		retrunList = EssApplyInfoDao.getEssQualificationApplyObject2(paramMap);
		return retrunList;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssRewardObject2(HttpServletRequest request) {
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("PERSON_ID_ID", request.getParameter("PERSON_ID_ID"));

		paramMap.put("REWARD_NO_NO", request.getParameter("REWARD_NO_NO"));
		retrunList = EssApplyInfoDao.getEssRewardObject2(paramMap);
		return retrunList;
	}

	/**
	 * end 申请所用数据
	 */

	// 获取个人信息
	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonalInfoByPid(HttpServletRequest request) {
		Object retrunList = new Object();
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("PERSON_ID_ID", request.getParameter("PERSON_ID"));
		retrunList = EssApplyInfoDao.getPersonalInfoByPid(paramMap);
		return retrunList;
	}

	/**
	 * 通用方法 申请所用数据
	 */
	public Map getRequestMap(HttpServletRequest request) {
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("PERSON_ID_ID", request.getParameter("PERSON_ID_ID"));

		if (request.getParameter("seach_sDate") == null
				|| request.getParameter("seach_sDate") == "") {

			paramMap.put("sDate", "");
			paramMap.put("eDate", "");
		} else {
			paramMap.put("sDate", request.getParameter("seach_sDate"));
			paramMap.put("eDate", request.getParameter("seach_eDate"));

		}

		if (request.getParameter("seach_ACTIVITY") == null
				/*|| request.getParameter("seach_ACTIVITY") == ""*/) {

			paramMap.put("ACTIVITY", "1");
		} else {

			paramMap.put("ACTIVITY", request.getParameter("seach_ACTIVITY"));

		}

		return paramMap;
	}

	/**
	 *审批
	 */
	@Override
	public int updateAddressInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			// 页面提交数据
			Map paramMap = this.getUpdateRequestMapInfo(request);
			String status = request.getParameter("APPLY_TYPE");

			EssApplyInfoDao.updateAddressInfo(paramMap, status);
			
			//回复信息和内容
			String EARROR=request.getParameter("EARROR").trim();
			String CALLBACK=request.getParameter("CALLBACK").trim();
			if(!EARROR.equals("")||!CALLBACK.equals("")){
				EssApplyInfoDao.callbackAddressInfo(paramMap);
			}
			//发送邮件
			String title=request.getParameter("ESS_TYPE_CODE");
			String apply_types=request.getParameter("APPLY_TYPES");
			String content="";
			if(status.equals("2")){
				content=title+apply_types+"成功";
			}else{
				content=title+apply_types+"失败";
				if(!CALLBACK.equals("")){
					content+=",回复信息:"+CALLBACK;
				}
				if(!EARROR.equals("")){
					content+=",错误内容:"+EARROR;
				}
			}
			paramMap.put("CONTENT", content);
			paramMap.put("ESS_TYPE_CODE", title);
			EssApplyInfoDao.insertEducationInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int updateEducationInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			// 页面提交数据
			Map paramMap = this.getUpdateRequestMapInfo(request);
			String status = request.getParameter("APPLY_TYPE");
			
			EssApplyInfoDao.updateEducationInfo(paramMap, status);
			
			//回复信息和内容
			String EARROR=request.getParameter("EARROR").trim();
			String CALLBACK=request.getParameter("CALLBACK").trim();
			if(!EARROR.equals("")||!CALLBACK.equals("")){
				EssApplyInfoDao.callbackEducationInfo(paramMap);
			}
			//发送邮件
			String title=request.getParameter("ESS_TYPE_CODE");
			String apply_types=request.getParameter("APPLY_TYPES");
			String content="";
			if(status.equals("2")){
				content=title+apply_types+"成功";
			}else{
				content=title+apply_types+"失败";
				if(!CALLBACK.equals("")){
					content+=",回复信息:"+CALLBACK;
				}
				if(!EARROR.equals("")){
					content+=",错误内容:"+EARROR;
				}
			}
			paramMap.put("CONTENT", content);
			paramMap.put("ESS_TYPE_CODE", title);
			EssApplyInfoDao.insertEducationInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int updateEmergencyInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			// 页面提交数据
			Map paramMap = this.getUpdateRequestMapInfo(request);
			String status = request.getParameter("APPLY_TYPE");
			EssApplyInfoDao.updateEmergencyInfo(paramMap, status);
			//回复信息和内容
			String EARROR=request.getParameter("EARROR").trim();
			String CALLBACK=request.getParameter("CALLBACK").trim();
			if(!EARROR.equals("")||!CALLBACK.equals("")){
				EssApplyInfoDao.callbackEmergencyInfo(paramMap);
			}
			//发送邮件
			String title=request.getParameter("ESS_TYPE_CODE");
			String apply_types=request.getParameter("APPLY_TYPES");
			String content="";
			if(status.equals("2")){
				content=title+apply_types+"成功";
			}else{
				content=title+apply_types+"失败";
				if(!CALLBACK.equals("")){
					content+=",回复信息:"+CALLBACK;
				}
				if(!EARROR.equals("")){
					content+=",错误内容:"+EARROR;
				}
			}
			paramMap.put("CONTENT", content);
			paramMap.put("ESS_TYPE_CODE", title);
			EssApplyInfoDao.insertPersonalInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int updateHomeRelationInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			// 页面提交数据
			Map paramMap = this.getUpdateRequestMapInfo(request);
			String status = request.getParameter("APPLY_TYPE");
			EssApplyInfoDao.updateHomeRelationInfo(paramMap, status);
			//回复信息和内容
			String EARROR=request.getParameter("EARROR").trim();
			String CALLBACK=request.getParameter("CALLBACK").trim();
			if(!EARROR.equals("")||!CALLBACK.equals("")){
				EssApplyInfoDao.callbackHomeRelationInfo(paramMap);
			}
			//发送邮件
			String title=request.getParameter("ESS_TYPE_CODE");
			String apply_types=request.getParameter("APPLY_TYPES");
			String content="";
			if(status.equals("2")){
				content=title+apply_types+"成功";
			}else{
				content=title+apply_types+"失败";
				if(!CALLBACK.equals("")){
					content+=",回复信息:"+CALLBACK;
				}
				if(!EARROR.equals("")){
					content+=",错误内容:"+EARROR;
				}
			}
			paramMap.put("CONTENT", content);
			paramMap.put("ESS_TYPE_CODE", title);
			EssApplyInfoDao.insertHomeRelationInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int updatePersonalInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			// 页面提交数据
			Map paramMap = this.getUpdateRequestMapInfo(request);
			//审批状态
			String status = request.getParameter("APPLY_TYPE");
			
			
			String picDif = request.getParameter("picDif");
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			/*if(admin.getCpnyId().equals("SPC_NJ")){
				List list= new ArrayList();
				list=this.applyList(request);
				String s=list.get(0).toString();
				System.out.println(s);
				String[] ss=s.split(",");
				for(int i =0;i<ss.length;i++){
					String sss=ss[i].toString().trim();
					String ssss=sss.substring(0,9).trim();
					if(ssss.trim().equals("APPLY_NOW")){
						paramMap.put("PERSON_NO",sss.substring(10));
						paramMap.put("PERSON_ID",admin.getPersonId());
					}
				}
			}*/
			
			if (picDif.equals("1")) {
				EssApplyInfoDao.updatePersonalInfo(paramMap, status);

			} else if (picDif.equals("2")) {
				EssApplyInfoDao.updatePersonalInfoForPic(paramMap, status);

			}
			//回复的信息和错误内容
			String EARROR=request.getParameter("EARROR").trim();
			String CALLBACK=request.getParameter("CALLBACK").trim();
			if(!EARROR.equals("")||!CALLBACK.equals("")){
				EssApplyInfoDao.callbackPersonalInfo(paramMap);
			}
			//发送邮件
			String title=request.getParameter("ESS_TYPE_CODE");
			String apply_types=request.getParameter("APPLY_TYPES");
			String content="";
			if(status.equals("2")){
				content=title+apply_types+"成功";
			}else{
				content=title+apply_types+"失败";
				if(!CALLBACK.equals("")){
					content+=",回复信息:"+CALLBACK;
				}
				if(!EARROR.equals("")){
					content+=",错误内容:"+EARROR;
				}
			}
			paramMap.put("CONTENT", content);
			paramMap.put("ESS_TYPE_CODE", title);
			EssApplyInfoDao.insertPersonalInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int updateProductInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			// 页面提交数据
			Map paramMap = this.getUpdateRequestMapInfo(request);
			String status = request.getParameter("APPLY_TYPE");
			EssApplyInfoDao.updateProductInfo(paramMap, status);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int updateQualificationInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			// 页面提交数据
			Map paramMap = this.getUpdateRequestMapInfo(request);
			String status = request.getParameter("APPLY_TYPE");
			EssApplyInfoDao.updateQualificationInfo(paramMap, status);
			//回复信息和内容
			String EARROR=request.getParameter("EARROR").trim();
			String CALLBACK=request.getParameter("CALLBACK").trim();
			if(!EARROR.equals("")||!CALLBACK.equals("")){
				EssApplyInfoDao.callbackQualificationInfo(paramMap);
			}
			//发送邮件
			String title=request.getParameter("ESS_TYPE_CODE");
			String apply_types=request.getParameter("APPLY_TYPES");
			String content="";
			if(status.equals("2")){
				content=title+apply_types+"成功";
			}else{
				content=title+apply_types+"失败";
				if(!CALLBACK.equals("")){
					content+=",回复信息:"+CALLBACK;
				}
				if(!EARROR.equals("")){
					content+=",错误内容:"+EARROR;
				}
			}
			paramMap.put("CONTENT", content);
			paramMap.put("ESS_TYPE_CODE", title);
			EssApplyInfoDao.insertQualificationInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int updateRewardInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			// 页面提交数据
			Map paramMap = this.getUpdateRequestMapInfo(request);
			String status = request.getParameter("APPLY_TYPE");

			EssApplyInfoDao.updateRewardInfo(paramMap, status);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int essApplyBatchApproval(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = this.getUpdateRequestMapInfo(request);

		String value = request.getParameter("getValueS");

		String[] values = value.split(",,");
		for (int i = 0; i < values.length; i++) {
			if (values != null) {
				String[] valuess = values[i].split("--"); // 不为空
				System.out.println(valuess.length);
				if (valuess != null && valuess.length == 11) { // 信息比较全

					String PERSON_ID = valuess[0].trim();
					String SUBMIT_TYPE = valuess[1].trim();
					String APPLY_TYPE_NUM = valuess[2].trim();
					String APPLY_PRO = valuess[3].trim();
					String APPLY_NOW = valuess[4].trim();
					String ESS_TYPE_CODE_S = valuess[5].trim();
					String FILE_URL = valuess[6].trim();
					String FILE_NAME = valuess[7].trim();
					String FILENOSSTR = valuess[8].trim();
					String APPLY_TYPE = valuess[9].trim();//状态 ： 修改 删除  添加 
					String ESS_TYPE_CODE = valuess[10].trim();//信息名称
					Map paramM = paramMap;
					paramM.put("PERSON_ID_ID", PERSON_ID);
					paramM.put("SUBMIT_TYPE", SUBMIT_TYPE);
					paramM.put("APPLY_TYPE_NUM", APPLY_TYPE_NUM);// 申请
					paramM.put("APPLY_NOW", APPLY_NOW);// apply序号
					paramM.put("APPLY_PRO", APPLY_PRO);// 以前的序号
					String status = APPLY_TYPE_NUM;

					paramM.put("ACTIVITY_NUM", 2);// 统一为审批状态
					try {
						if (ESS_TYPE_CODE_S.equals("14013945")) {
							// 个人信息
							paramM.put("PERSON_NO", APPLY_NOW);// apply序号
							paramM.put("PERSON_ID", APPLY_PRO);// 以前的序号
							EssApplyInfoDao.updatePersonalInfo(paramM, status);
						}

						if (ESS_TYPE_CODE_S.equals("14014395")) {
							// 个人照片
							paramM.put("PERSON_NO", APPLY_NOW);// apply序号
							paramM.put("PERSON_ID", APPLY_PRO);// 以前的序号
							EssApplyInfoDao.updatePersonalInfoForPic(paramM,
									status);
						}
						if (ESS_TYPE_CODE_S.equals("14013918")) {
							// 地址信息
							paramM.put("ADDRESS_NO", APPLY_NOW);// apply序号
							paramM.put("UPDATE_ADDRESS_NO", APPLY_PRO);// 以前的序号
							EssApplyInfoDao.updateAddressInfo(paramM, status);
						}
						if (ESS_TYPE_CODE_S.equals("14013921")) {
							// 经历信息
							paramM.put("WORK_EXPER_NO", APPLY_NOW);// apply序号
							paramM.put("UPDATE_WORK_EXPER_NO", APPLY_PRO);// 以前的序号
							EssApplyInfoDao.updateWorkInfo(paramM, status);
						}
						if (ESS_TYPE_CODE_S.equals("14013919")) {
							// 家庭关系信息
							paramM.put("FAMILY_NO", APPLY_NOW);// apply序号
							paramM.put("UPDATE_FAMILY_NO", APPLY_PRO);// 以前的序号
							EssApplyInfoDao.updateHomeRelationInfo(paramM,
									status);
						}
						if (ESS_TYPE_CODE_S.equals("14013920")) {
							// 紧急联系人信息
							paramM.put("EMERGENCY_NO", APPLY_NOW);// apply序号
							paramM.put("UPDATE_EMERGENCY_NO", APPLY_PRO);// 以前的序号
							EssApplyInfoDao.updateEmergencyInfo(paramM, status);
						}
						if (ESS_TYPE_CODE_S.equals("14013923")) {
							// 学历信息
							paramM.put("FILE_URL", FILE_URL);
							paramM.put("FILE_NAME", FILE_NAME);
							paramM.put("FILENOSSTR", FILENOSSTR);
							paramM.put("EDUC_NO", APPLY_NOW);// apply序号
							paramM.put("UPDATE_EDUC_NO", APPLY_PRO);// 以前的序号
							EssApplyInfoDao.updateEducationInfo(paramM, status);
						}
						if (ESS_TYPE_CODE_S.equals("14013924")) {
							// 资格信息
							paramM.put("FILE_URL", FILE_URL);
							paramM.put("FILE_NAME", FILE_NAME);
							paramM.put("FILENOSSTR", FILENOSSTR);
							paramM.put("QUAL_NO", APPLY_NOW);// apply序号
							paramM.put("UPDATE_QUAL_NO", APPLY_PRO);// 以前的序号
							EssApplyInfoDao.updateQualificationInfo(paramM,
									status);
						}
						if (ESS_TYPE_CODE_S.equals("14013922")) {
							// 产品信息
							paramM.put("PRODUCT_NO", APPLY_NOW);// apply序号
							paramM.put("UPDATE_PRODUCT_NO", APPLY_PRO);// 以前的序号
							EssApplyInfoDao.updateProductInfo(paramM, status);
						}
						if (ESS_TYPE_CODE_S.equals("14013925")) {
							// 表彰信息
							paramM.put("REWARD_NO", APPLY_NOW);// apply序号
							paramM.put("UPDATE_REWARD_NO", APPLY_PRO);// 以前的序号
							EssApplyInfoDao.updateRewardInfo(paramM, status);
						}
						//发送邮件
						String content =ESS_TYPE_CODE+APPLY_TYPE+"成功";
						paramM.put("ESS_TYPE_CODE", ESS_TYPE_CODE);
						paramM.put("CONTENT", content);
						paramM.put("PERSON_ID", PERSON_ID);
						EssApplyInfoDao.insertEducationInfo(paramMap);
					} catch (Exception e) {
						e.printStackTrace();
						return 0;
					}

				}
					
			}

		}

		return 1;
	}

	@Override
	public int updateWorkInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			// 页面提交数据
			Map paramMap = this.getUpdateRequestMapInfo(request);
			String status = request.getParameter("APPLY_TYPE");
			EssApplyInfoDao.updateWorkInfo(paramMap, status);
			//回复信息和内容
			String CALLBACK=request.getParameter("CALLBACK").trim();
			String EARROR=request.getParameter("EARROR").trim();
			if(!CALLBACK.equals("")||!EARROR.equals("")){
				EssApplyInfoDao.callbackWorkInfo(paramMap);
			}
			//发送邮件
			String title=request.getParameter("ESS_TYPE_CODE");
			String apply_types=request.getParameter("APPLY_TYPES");
			String content="";
			if(status.equals("2")){
				content=title+apply_types+"成功";
			}else{
				content=title+apply_types+"失败";
				if(!CALLBACK.equals("")){
					content+=",回复信息:"+CALLBACK;
				}
				if(!EARROR.equals("")){
					content+=",错误内容:"+EARROR;
				}
			}
			paramMap.put("CONTENT", content);
			paramMap.put("ESS_TYPE_CODE", title);
			EssApplyInfoDao.insertWorkInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 审批十个 增删改统一为 update APPLY_TYPE 1添加 2修改 3删除
	 */

	/**
	 * 通用方法 申请所用数据
	 */
	public Map getUpdateRequestMapInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("PERSON_ID_ID", request.getParameter("PERSON_ID_ID"));

		return paramMap;
	}

	/**
	 * 
	 */

	/**
	 * 修改申请表状态 activity 1提交 2审批 3退回 4取消
	 */

	/**
	 * 
	 */

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

		returnList = EssApplyInfoDao.manageAgeCountList(paramMap);

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
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		//paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		List list = new ArrayList();
		list = EssApplyInfoDao.getAllDept(paramMap);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), EssApplyInfoDao
							.getAgeListByDeptNo(parMap));
				}
			}
		}
		lMap.put("deptList", EssApplyInfoDao.getParentCodeList(paramMap));
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

		returnList = EssApplyInfoDao.manageAgeCountList(paramMap);

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
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		Map parMap = paramMap;
		List list = new ArrayList();
		list = EssApplyInfoDao.getAllDept(paramMap);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {// 遍历部门
					// 不为空的传入递归
					// 得到LIST
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));

					// key为 部门ID value=查询结果LIST

					lMap2.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getGradeBGZListByDeptNo(parMap));
					lMap3.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getGradeSCZListByDeptNo(parMap));

				}
			}
		}

		lMap.put("deptList", EssApplyInfoDao.getParentCodeList(paramMap));
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

		returnList = EssApplyInfoDao.manageAgeCountList(paramMap);

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
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		//paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		List list = new ArrayList();

		list = EssApplyInfoDao.getAllDeptDif(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门区分DEPT_DISTINGUISH_STANDARD
					parMap.put("DEPT_DISTINGUISH_STANDARD", dept_id
							.get("DEPT_DISTINGUISH_STANDARD"));

					// key为 部门ID value=查询结果LIST

					lMap2.put(dept_id.get("DEPT_DISTINGUISH_STANDARD"),
							EssApplyInfoDao.getPositionListByDeptNo(parMap));

				}
			}
		}

		lMap.put("deptList", EssApplyInfoDao.getParentCodeDifList(paramMap));
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

		returnList = EssApplyInfoDao.manageAgeCountList(paramMap);

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
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		//paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		List list = new ArrayList();

		list = EssApplyInfoDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getEduListByDeptNo(parMap));
					lMap3.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getSexListByDeptNo(parMap));
				}
			}
		}
		lMap.put("deptList", EssApplyInfoDao.getParentCodeList(paramMap));
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

		returnList = EssApplyInfoDao.manageAgeCountList(paramMap);

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
		// 获取选定月份最后一天
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		//paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;

		List list = new ArrayList();

		list = EssApplyInfoDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getEmpTypeListByDeptNo(parMap));
				}
			}
		}

		lMap.put("deptList", EssApplyInfoDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}
	
	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeWorkAgeList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		// 获取选定月份最后一天
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		//paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;

		List list = new ArrayList();

		list = EssApplyInfoDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getWorkAgeListByDeptNo(parMap));
				}
			}
		}

		lMap.put("deptList", EssApplyInfoDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	// //职员类型end

	// //一月中最大天数 若是时间为空 默认为本月最大天数
	public String getMonthLastDay(String month) {
		Calendar a = Calendar.getInstance();
		
		if (month != null && !month.equals("")) {
			String dates[] = month.replace(".", "/").split("/");
			if(month.length() > 4){
				int year = Integer.parseInt(dates[1]);
				int months = Integer.parseInt(dates[0]);
				int day = 1;
				Calendar cal = Calendar.getInstance();
				cal.set(year, months - 1, day);
				int last = cal.getActualMaximum(Calendar.DATE);
				String date = last + "." + months + "." + year;
				return date;
			}else{
				int year = Integer.parseInt(dates[0]);
				int months = 12;
				int day = 1;
				Calendar cal = Calendar.getInstance();
				cal.set(year, months - 1, day);
				int last = cal.getActualMaximum(Calendar.DATE);
				String date = last + "." + months + "." + year;
				return date;
			}

		} else {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			Calendar ca = Calendar.getInstance();

			String last = format.format(ca.getTime());

			return last;

		}
	}

	// /MANAGE人员类型条件
	public Map changeCode(Map paramMap) {
		String codeType = "";
		int j = 0;
		for (int i = 0; i < 5; i++) {
			String code = (String) paramMap.get("EMP_TYPE_CODE" + i);
			if (code != null && !code.equals("")) {
				j++;
				if (j == 1) {
					codeType = "'" + code + "'";

				} else {
					codeType += ",'" + code + "'";
				}

			}
		}

		paramMap.put("EMP_TYPE_CODE", codeType);

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

	/**
	 * 
	 * 入职人数统计
	 */

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeEmpTypeList1(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		// 获取选定月份最后一天
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		Map parMap = paramMap;
		List list = new ArrayList();

		list = EssApplyInfoDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getEmpTypeListByDeptNo1(parMap));
				}
			}
		}

		lMap.put("deptList", EssApplyInfoDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeGradeList1(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		
		Map lMap2 = new LinkedHashMap();
		Map lMap3 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		//paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		List list = new ArrayList();
		list = EssApplyInfoDao.getAllDept(paramMap);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {// 遍历部门
					// 不为空的传入递归
					// 得到LIST
					Map dept_id = (Map) list.get(i);// 取出部门ID
					// key为 部门ID value=查询结果LIST
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					lMap2.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getGradeBGZListByDeptNo1(parMap));
					lMap3.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getGradeSCZListByDeptNo1(parMap));
				}
			}
		}
		lMap.put("deptList", EssApplyInfoDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		lMap.put("deptListCount3", lMap3);
		return lMap;
	}

	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodePositionList1(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		Map parMap = paramMap;
		List list = new ArrayList();
		list = EssApplyInfoDao.getAllDept(paramMap);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门区分DEPT_DISTINGUISH_STANDARD
					parMap.put("PARENT_DEPT_NO", dept_id
							.get("DEPTNO"));

					// key为 部门ID value=查询结果LIST

					lMap2.put(dept_id.get("DEPTNO"),EssApplyInfoDao.getPositionListByDeptNo1(parMap));

				}
			}
		}
		lMap.put("deptList", EssApplyInfoDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	// end入职人数统计

	// 退职start
	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeGradeList2(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map lMap3 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		List list = new ArrayList();

		list = EssApplyInfoDao.getAllDept(paramMap);

		// test

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {// 遍历部门
					// 不为空的传入递归
					// 得到LIST
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));

					// key为 部门ID value=查询结果LIST

					lMap2.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getGradeBGZListByDeptNo2(parMap));
					lMap3.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getGradeSCZListByDeptNo2(parMap));

				}
			}
		}

		lMap.put("deptList", EssApplyInfoDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		lMap.put("deptListCount3", lMap3);
		return lMap;
	}

	// //职级END

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodePositionList2(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;
		List list = new ArrayList();

		list = EssApplyInfoDao.getAllDeptDif(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门区分DEPT_DISTINGUISH_STANDARD
					parMap.put("DEPT_DISTINGUISH_STANDARD", dept_id
							.get("DEPT_DISTINGUISH_STANDARD"));

					// key为 部门ID value=查询结果LIST

					lMap2.put(dept_id.get("DEPT_DISTINGUISH_STANDARD"),
							EssApplyInfoDao.getPositionListByDeptNo2(parMap));

				}
			}
		}

		lMap.put("deptList", EssApplyInfoDao.getParentCodeDifList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	// //职务end

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeEmpTypeList2(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		// 获取选定月份最后一天
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;

		List list = new ArrayList();

		list = EssApplyInfoDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getEmpTypeListByDeptNo2(parMap));
				}
			}
		}

		lMap.put("deptList", EssApplyInfoDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	// 退职end

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public int callPD(HttpServletRequest request) {
		int result = 1;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("person_id", admin.getAdminID());
		paramMap.put("language", admin.getLanguage());
		paramMap.put("cpny_id", admin.getCpnyId());
		paramMap.put("ar_date", request.getParameter("AR_DATE"));
		try {
			EssApplyInfoDao.collPD(paramMap);
		} catch (Exception e) {
			result = 0;
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getApplyNumber(HttpServletRequest request) {
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());

		retrunList = EssApplyInfoDao.getApplyNumber(paramMap);
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonalPhotoNullNumber(HttpServletRequest request) {
		Object retrunList = new Object();

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());

		retrunList = EssApplyInfoDao.getPersonalPhotoNullNumber(paramMap);
		return retrunList;
	}

	/**
	 * 生成部门树
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getResignResonList(HttpServletRequest request) {
		LinkedHashMap lMap = new LinkedHashMap();
		Map lMap2 = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		// 获取选定月份最后一天
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		paramMap = this.changeCode(paramMap);
		Map parMap = paramMap;

		List list = new ArrayList();

		list = EssApplyInfoDao.getAllDept(paramMap);

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null && !list.get(i).equals("")) {
					Map dept_id = (Map) list.get(i);// 取出部门ID
					parMap.put("PARENT_DEPT_NO", dept_id.get("DEPTNO"));
					// key为 部门ID value=查询结果LIST
					lMap2.put(dept_id.get("DEPTNO"), EssApplyInfoDao.getResignResonList(parMap));
				}
			}
		}

		lMap.put("deptList", EssApplyInfoDao.getParentCodeList(paramMap));
		lMap.put("deptListCount", lMap2);
		return lMap;
	}

	/**
	 * 所有部门
	 */
	@SuppressWarnings("unchecked")
	public List getAllDeptList(HttpServletRequest request) {

		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		retrunList = EssApplyInfoDao.getAllDeptList(paramMap);
		return retrunList;
	}

	@Override
	public int saveOrzTemp(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		int result = 1;
		try {

		 this.EssApplyInfoDao.saveOrzTemp(paramMap);
 
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}
	
	/**
	 * clean
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountInfoSonList(HttpServletRequest request) {

		List retrunList = new ArrayList();

		// 页面提交数据
		Map<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(paramMap.get("INDEX").equals("0")){
			String gradeCode = this.changeCodeAll((String)paramMap.get("GRADE"));
			paramMap.put("GRADECODE",gradeCode);
			if(paramMap.get("strFlag").toString().equals("add")){
				retrunList = EssApplyInfoDao.monthPersonCountGradeInfoSonList(paramMap);
			}else{
				retrunList = EssApplyInfoDao.monthPersonCountGradeForLiftInfoSonList(paramMap);
			}
			
		}else if(paramMap.get("INDEX").equals("1")){
			String type = request.getParameter("type");
			if(type.equals("lizhi") && type != null){
				retrunList = EssApplyInfoDao.monthPersonCountMonthInLIZHIfoSonList(paramMap);
			}else{
				retrunList = EssApplyInfoDao.monthPersonCountMonthInfoSonList(paramMap);
			}
		}else if(paramMap.get("INDEX").equals("2")){
			if(paramMap.get("strFlag").toString().equals("add")){
				if(!paramMap.get("EDU").equals("0")){
					String gradeCode = this.changeCodeAll((String)paramMap.get("EDU"));
					paramMap.put("EDUCODE",gradeCode);
					retrunList = EssApplyInfoDao.monthPersonCountEduInfoSonList(paramMap);
				}else{
					String gradeCode = this.changeCodeAll((String)paramMap.get("SEX"));
					paramMap.put("SEXCODE",gradeCode);
					retrunList = EssApplyInfoDao.monthPersonCountSexInfoSonList(paramMap);
				}
			}else{
				String gradeCode = this.changeCodeAll((String)paramMap.get("RESIGNRESON"));
				paramMap.put("RESIGNRESON",gradeCode);
				retrunList = EssApplyInfoDao.monthPersonCountRESIGNRESONInfoSonList(paramMap);
			}
		}else if(paramMap.get("INDEX").equals("3")){
				retrunList = EssApplyInfoDao.monthPersonCountAgeInfoSonList(paramMap);
		}else if(paramMap.get("INDEX").equals("4")){
			String EmpTypeCode = this.changeCodeAll((String)paramMap.get("empTypeForTable"));
			paramMap.put("empTypeForTable",EmpTypeCode);
			retrunList = EssApplyInfoDao.monthPersonCountEmpTypeSonList(paramMap);
		}else if(paramMap.get("INDEX").equals("5")){
			retrunList = EssApplyInfoDao.monthPersonCountWorkAgeInfoSonList(paramMap);
		}else if(paramMap.get("INDEX").equals("6")){
			retrunList = EssApplyInfoDao.monthPersonCountMonthInLIZHIfoSonList(paramMap);
		}
		
		return retrunList;
	}
	
	/**
	 * clean
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List monthPersonCountInfoSonList1(HttpServletRequest request) {

		List retrunList = new ArrayList();

		// 页面提交数据
		Map<String, Object> paramMap = ObjectBindUtil.getRequestParamData(
				request, "seach_");
		paramMap.put("YEARMONTHDAY", this.getMonthLastDay((String) paramMap
				.get("YEAR")));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(paramMap.get("INDEX").equals("0")){
			String empType = this.changeCodeAll((String)paramMap.get("EMPTYPE"));
			paramMap.put("EMPTYPE",empType);
			retrunList = EssApplyInfoDao.monthPersonCountEmpTypeInfoSonList1(paramMap);
		}else if(paramMap.get("INDEX").equals("1")){
			String social_differentiation = this.changeCodeAll((String)paramMap.get("SOCIAL_DIFFERENTIATION"));
			paramMap.put("SOCIAL_DIFFERENTIATION",social_differentiation);
			retrunList = EssApplyInfoDao.monthPersonCountSocialInfoSonList1(paramMap);
		}else if(paramMap.get("INDEX").equals("2")){
			String gradeCode = this.changeCodeAll((String)paramMap.get("GRADE"));
			paramMap.put("GRADECODE",gradeCode);
			retrunList = EssApplyInfoDao.monthPersonCountGradeInfoSonList1(paramMap);
		}
		return retrunList;
		
	}
	
	public String changeCodeAll(String code) {
		String codeString = "";
		String[] array = code.split(",");
		for (int i = 0; i < array.length; i++) {
			if(i==array.length-1){
				codeString  = "'" + array[i] +"'" + codeString;
			}else{
				codeString  = ",'" + array[i] +"'" + codeString;
			}
		}
		return codeString;
	}
}