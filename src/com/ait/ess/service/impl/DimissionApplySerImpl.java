package com.ait.ess.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.AffirmApplyDao;
import com.ait.ess.dao.DimissionApplyDao;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.service.DimissionApplySer;
import com.ait.ess.service.InfoApplySer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class DimissionApplySerImpl implements DimissionApplySer {
	@Autowired
	private InfoApplyDao infoApplyDao;

	@Autowired
	private InfoApplySer infoApplySer;

	@Autowired
	AffirmApplyDao affirmApplyDao;

	@Autowired
	private DimissionApplyDao dimissionApplyDao;

	@Autowired
	PaTempSalesDAO paTempSalesDAO;

	// 离职申请项目代码
	private static String APPLY_TYPE_NO = "218296";

	public String getDimissionInfoSeq(HttpServletRequest request)
			throws Exception {
		String str = "";
		try {
			str = this.dimissionApplyDao.getDimissionInfoSeq();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 离职申请检查该员工是否已经申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int checkAddDimissionInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String flag = request.getParameter("flag");
		if (flag == "1" || "1".equals(flag)) {
			paramMap.put("PERSON_ID",
					request.getParameter("dwz.person.personId"));
		} else {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		}
		return this.dimissionApplyDao.checkAddDimissionInfo(paramMap);
	}

	/**
	 * 添加离职申请(apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addDimissionInfoApply(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		String personId = paramMap.get("PERSON_ID") != null ? paramMap.get(
				"PERSON_ID").toString() : "";
		String applyleaveDate = paramMap.get("APPLY_LEAVE_TIME") != null ? paramMap
				.get("APPLY_LEAVE_TIME").toString() : "";
		String apply_reason = paramMap.get("APPLY_REASON") != null ? paramMap
				.get("APPLY_REASON").toString() : "";
		String APPLY_TYPE_NO = paramMap.get("AFFIRM_TYPE_ID") != null ? paramMap
				.get("AFFIRM_TYPE_ID").toString() : "218296";

		LinkedHashMap dateMap = new LinkedHashMap();
		dateMap = this.getLinkedMapByRequest(request, dateMap);
		dateMap.put("CPNY_ID", admin.getCpnyId());
		dateMap.put("PERSON_ID", personId);
		dateMap.put("APPLY_LEAVE_TIME", applyleaveDate);
		dateMap.put("APPLY_REASON", apply_reason);
		dateMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		dateMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		List listAffirmors = new ArrayList();
		if (affirmId.length > 0) {
			for (int i = 0; i < affirmId.length; i++) {
				listAffirmors.add(affirmId[i]);
			}
		}
		dateMap.put("listAffirmors", listAffirmors);
		LinkedHashMap otMap = this.preAddArMacRecordApply(dateMap,
				admin.getLanguage());
		this.dimissionApplyDao.addDimissionInfoApply(otMap);
		return 1;
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
	 * 通过request请求封装查询条件(get conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequest(HttpServletRequest request,
			LinkedHashMap paramMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}

	/**
	 * 封装要插入的离职申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddArMacRecordApply(LinkedHashMap paramMap,
			String language) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		List distinctList = new ArrayList();
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao
				.getPersonInfoByPersonId(paramMap);
		List affirmerList = new ArrayList();
		// this.getAffirmorListByMap(paramMap);
		List affirmList = (List) paramMap.get("listAffirmors");
		for (int i = 0; i < affirmList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap mapvalue = new LinkedHashMap();
			map.put("AFFIRMOR_ID", affirmList.get(i));
			map.put("AFFIRM_LEVEL", i);
			affirmerList.add(map);
		}
		int count = 1;
		boolean flag = true;
		Map filterMap = new LinkedHashMap();
		for (int i = 0; i < affirmList.size(); i++) {
			LinkedHashMap affirmerMap = (LinkedHashMap) affirmerList.get(i);
			if (affirmerMap.get("AFFIRMOR_ID") != null) {
				if (flag) {
					// 只将流程第一步的决裁者取出来存到刷卡申请表
					paramMap.put("CURRENT_AFFIRM_ID",
							affirmerMap.get("AFFIRMOR_ID"));
					flag = false;
				}
				filterMap.put(affirmerMap.get("AFFIRMOR_ID").toString(),
						affirmerMap);
			}
		}
		// 判断saveAffirmorList里边重复的决裁者，然后重新排序
		for (Iterator iterator = filterMap.values().iterator(); iterator
				.hasNext();) {
			LinkedHashMap temp = (LinkedHashMap) iterator.next();
			temp.put("AFFIRM_LEVEL", count);
			count++;
			distinctList.add(temp);
		}
		paramMap.put("ACTIVITY", "0");
		returnMap.put("PARAM_MAP", paramMap);
		returnMap.put("DISTINCT_LIST", distinctList);
		return returnMap;
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
	public List getAffirmorList(HttpServletRequest request) throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		if (paramMap == null || paramMap.get("APPLY_TYPE_NO") == null
				|| "".equals(paramMap.get("APPLY_TYPE_NO").toString())) {
			paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		}
		if (paramMap.get("PERSON_ID") == null
				|| "".equals(paramMap.get("PERSON_ID"))) {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		return this.getAffirmorListByMap(paramMap);
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
		return infoApplySer.getAffirmorListByString(APPLY_TYPE_NO, paramMap
				.get("PERSON_ID").toString(), "", "", paramMap.get("LANGUAGE").toString());
	}

	/**
	 * 查找离职申请数量
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getDimissionCnt(HttpServletRequest request) throws SQLException {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		retrunInt = dimissionApplyDao.getDimissionCnt(paramMap);
		return retrunInt;
	}

	/**
	 * 查找离职申请的List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDimissionList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = dimissionApplyDao.getDimissionList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = dimissionApplyDao.getDimissionList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 删除还没有开始审批的申请信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int deleteDimissionInfo(HttpServletRequest request) {
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.dimissionApplyDao.deleteDimissionInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 检查要删除的申请信息是否开始审批  如果开始则不能删除
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public int checkDimissionInfo(HttpServletRequest request){
		int num = 0;
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		returnList =  dimissionApplyDao.getDimissionList(paramMap);
		if(returnList.size()>0){
			Map objectDimission = (Map) returnList.get(0);
			if(objectDimission.get("AFFIRM_FLAG").toString()=="-1" ||"-1".equals(objectDimission.get("AFFIRM_FLAG").toString())){
				num = 1;
			}
		}
		return num;
	}
}
