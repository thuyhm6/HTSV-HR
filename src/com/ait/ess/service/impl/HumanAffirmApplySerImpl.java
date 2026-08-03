package com.ait.ess.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.HumanAffirmApplyDao;
import com.ait.ess.dao.PersonInfoDao;
import com.ait.ess.dao.ViewApplyDao;
import com.ait.ess.service.HumanAffirmApplySer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class HumanAffirmApplySerImpl implements HumanAffirmApplySer {

	Logger logger = Logger.getLogger(HumanAffirmApplySerImpl.class);

	@Autowired
	private HumanAffirmApplyDao humanAffirmApplyDao;

	@Autowired
	private ViewApplyDao viewApplyDao;

	@Autowired
	private PersonInfoDao personInfoDao;
	
	/**
	 * 通过request请求封装查询条件(get search conditions for request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getMapByRequestForSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}

	/**
	 * 通过request请求封装查询条件(get search conditions for request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getMapByRequest(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		return paramMap;
	}

	/**
	 * 个人信息申请集合(personal information apply list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonInfoApplyConfirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);

		if (UiUtil.getPageNum(request) > 0) {
			list = humanAffirmApplyDao.getPersonInfoApplyConfirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = humanAffirmApplyDao.getPersonInfoApplyConfirmList(paramMap);
		}
		return list;
	}

	/**
	 * 个人信息申请总数(personal information apply total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPersonInfoApplyConfirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		return humanAffirmApplyDao.getPersonInfoApplyConfirmListCnt(paramMap);
	}

	/**
	 * 人事确认通过/否决(human confirm pass or reject)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int savePersonInfoHumanConfirm(HttpServletRequest request)
			throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = getMapByRequest(request);
		Map map= personInfoDao.getEssPersonInfo(paramMap);
		
		paramMap.put("CELLPHONE",map.get("CELLPHONE"));//手机号码
		paramMap.put("HOME_ADDRESS",map.get("HOME_ADDRESS"));//现住址
		paramMap.put("EMAIL",map.get("EMAIL"));//EMAIL
		paramMap.put("ENGLISH_NAME",map.get("ENGLISH_NAME"));//英文名称
		
		
		return this.humanAffirmApplyDao.savePersonInfoHumanConfirm(paramMap);
	}

	/**
	 * 批量人事确认通过/否决(batch human confirm pass or reject)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int savePersonInfoHumanConfirmInBatch(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getMapByRequest(request);
		String[] paramData = request.getParameterValues("c1");
		for (int i = 0; i < paramData.length; i++) {
			paramMap.put("PERSON_ID", paramData[i]);
			Map map= personInfoDao.getEssPersonInfo(paramMap);
			
			paramMap.put("CELLPHONE",map.get("CELLPHONE"));//手机号码
			paramMap.put("HOME_ADDRESS",map.get("HOME_ADDRESS"));//现住址
			paramMap.put("EMAIL",map.get("EMAIL"));//EMAIL
			paramMap.put("ENGLISH_NAME",map.get("ENGLISH_NAME"));//英文名称
			list.add(paramMap);
		}
		return this.humanAffirmApplyDao
				.savePersonInfoHumanConfirmForBatch(list);
	}

	/**
	 * 查询属于该法人的加班类型List
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOverTimeApplyTypeList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return this.humanAffirmApplyDao.getOverTimeApplyTypeList(paramMap);
	}
	
	/**
	 * 查询属于该法人的加班转换类型List
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getConverTypeList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return this.humanAffirmApplyDao.getConverTypeList(paramMap);
	}
	
	/**
	 * 查询属于该法人的加班转默认调休类型List
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDefaultDaoXiuList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return this.humanAffirmApplyDao.getDefaultDaoXiuList(paramMap);
	}
	
	/**
	 * 人事确认--加班申请信息列表(personnel confirm:overtime apply information list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOvertimeApplyConfirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("OT_APPLY_NO", "31");// 31是加班申请NO
		paramMap.put("SY_PARAM_NO", "4166");// 4166为是否需要加班转换NO
		paramMap.put("IF_CONVERT", this.humanAffirmApplyDao.getParamValueByCpnyIdAndParamNo(paramMap));
		paramMap.put("SY_PARAM_NO", "4156");// 4156为是否可以提前进行人事确认NO
		paramMap.put("IF_PRE_CONF", this.humanAffirmApplyDao.getParamValueByCpnyIdAndParamNo(paramMap));
		if (UiUtil.getPageNum(request) > 0) {
			list = humanAffirmApplyDao.getOvertimeApplyConfirmList(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = humanAffirmApplyDao.getOvertimeApplyConfirmList(paramMap);
		}

		List returnList = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		LinkedHashMap childMap = new LinkedHashMap();
		List affirmerList = null;
		for (int i = 0; i < list.size(); i++) {
			map = (LinkedHashMap) list.get(i);
			paramMap.put("APPLY_NO", map.get("APPLY_NO"));
			//-----2013-06-17添加 根据申请加班类型查询该加班类型对应的默认转换（一般是调休加班或者付薪加班两种）类型 start-----------
			LinkedHashMap defaultMap = new LinkedHashMap();
			defaultMap = (LinkedHashMap)viewApplyDao.getDefaultTurnDaoxiu(paramMap);
			if(defaultMap!=null){
			map.put("DEFAULTTYPE", defaultMap.get("PARAM_VALUE")!=null?defaultMap.get("PARAM_VALUE").toString():"NULL");
			//-----2013-06-17添加 加班默认转换类型  end-----------
			}else{
				map.put("DEFAULTTYPE","NULL");
			}
			// 查询决裁者集合并存入返回的list里面
			// 获得人事决裁状态和是否可删除标识(get person-confirm-status and if can be deleted)
			affirmerList = viewApplyDao.getAffirmorList(paramMap);
			map.put("affirmerList", affirmerList);

			String applyFromDateStr = "";
			String applyToDateStr = "";
			String fromTIme = "";
			String toTime = "";
			String applyTypeCode = map.get("APPLY_TYPE_CODE") != null ? map.get("APPLY_TYPE_CODE").toString(): "";
			
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
	 * 人事确认--加班申请信息总数(personnel confirm:overtime apply information total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getOvertimeApplyConfirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("OT_APPLY_NO", "31");// 31是加班申请NO
		paramMap.put("SY_PARAM_NO", "4156");// 4156为是否可以提前进行人事确认NO
		paramMap.put("IF_PRE_CONF", this.humanAffirmApplyDao
				.getParamValueByCpnyIdAndParamNo(paramMap));
		return humanAffirmApplyDao.getOvertimeApplyConfirmListCnt(paramMap);
	}

	/**
	 * 加班申请人事确认通过/否决(human confirm pass or reject)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveOvertimeApplyHumanConfirm(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String confirmFlag = paramMap.get("CONFIRM_FLAG") != null ? paramMap.get("CONFIRM_FLAG").toString() : "";
		String convertCode = paramMap.get("CONVERT_CODE") != null ? paramMap.get("CONVERT_CODE").toString() : "";
		if (!("C01".equals(admin.getCpnyId())||"C11".equals(admin.getCpnyId()))) {//将法人是C11的用户也加入判断 5.29修改 lw
			convertCode = "";
		}

		LinkedHashMap applyMap = (LinkedHashMap) this.humanAffirmApplyDao.getOvertimeApplyInfoByApplyNo(paramMap);
		applyMap.put("deptNo", admin.getDeptNo());
		applyMap.put("ADMIN_ID", admin.getPersonId());
		applyMap.put("CPNY_ID", admin.getCpnyId());
		applyMap.put("CONFIRM_FLAG", confirmFlag);
		applyMap.put("CONVERT_CODE", convertCode);

		return this.humanAffirmApplyDao.saveOvertimeApplyHumanConfirm(applyMap);
	}

	/**
	 * 加班申请批量人事确认通过/否决(batch human confirm pass or reject)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveOvertimeApplyHumanConfirmInBatch(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		LinkedHashMap applyMap = null;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String confirmFlag = paramMap.get("CONFIRM_FLAG") != null ? paramMap.get("CONFIRM_FLAG").toString() : "";
		String[] paramData = request.getParameterValues("c1");
		if(paramData != null){
			for (int i = 0; i < paramData.length; i++) {
				paramMap.put("APPLY_NO", paramData[i]);
				applyMap = (LinkedHashMap) this.humanAffirmApplyDao.getOvertimeApplyInfoByApplyNo(paramMap);
				String convertCode = paramMap.get(paramData[i] + "_CONVERT_CODE") != null ? paramMap
						.get(paramData[i] + "_CONVERT_CODE").toString(): "";
				if (!("C01".equals(admin.getCpnyId())||"C11".equals(admin.getCpnyId()))) {//将法人是C11的用户也加入判断 5.29修改 lw
					convertCode = "";
				}
				applyMap.put("deptNo", admin.getDeptNo());
				applyMap.put("ADMIN_ID", admin.getPersonId());
				applyMap.put("CPNY_ID", admin.getCpnyId());
				applyMap.put("CONFIRM_FLAG", confirmFlag);
				applyMap.put("CONVERT_CODE", convertCode);
				list.add(applyMap);
			}
		}
		return this.humanAffirmApplyDao.saveOvertimeApplyHumanConfirmForBatch(list);
	}

	/**
	 * 人事确认--休假申请列表(personnel confirm:leave apply information list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getLeaveApplyConfirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		String menuNo=request.getParameter("menuNo");
		// 123634为喜丧假申请   123646 调休
		if(menuNo.equals("123649")||menuNo.equals("124822")||menuNo.equals("124824")){
			paramMap.put("TYPE", "123634");
			paramMap.put("LEAVE_TYPE_NO", "123634");
		}else if(menuNo.equals("123650")||menuNo.equals("124823")||menuNo.equals("124825")){
			paramMap.put("TYPE", "123646");
			paramMap.put("LEAVE_TYPE_NO", "123645");
		}else if(menuNo.equals("2441")||menuNo.equals("2449")||menuNo.equals("2454")||menuNo.equals("2479")){
			paramMap.put("TYPE", "21");
			paramMap.put("LEAVE_TYPE_NO", "21");// 21为休假申请
			//paramMap.put("APPLY_TYPE_NO", "21");// 21为休假申请
		}else{
			paramMap.put("TYPE", "0");
		}
		paramMap.put("SY_PARAM_NO", "4166");// 4166为是否需要加班转换NO
		paramMap.put("IF_CONVERT", this.humanAffirmApplyDao.getParamValueByCpnyIdAndParamNo(paramMap));
		paramMap.put("SY_PARAM_NO", "4156");// 4156为是否可以提前进行人事确认NO
		paramMap.put("IF_PRE_CONF", this.humanAffirmApplyDao.getParamValueByCpnyIdAndParamNo(paramMap));
		if (UiUtil.getPageNum(request) > 0) {
			list = humanAffirmApplyDao.getLeaveApplyConfirmList(paramMap,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = humanAffirmApplyDao.getLeaveApplyConfirmList(paramMap);
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
			map.put("affirmerList", affirmerList);
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 人事确认--休假申请列表(personnel confirm:leave apply information total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getLeaveApplyConfirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		String menuNo=request.getParameter("menuNo");
		paramMap.put("APPLY_TYPE_NO", "21");// 21为休假申请
		// 123634为喜丧假申请   123646 调休
		if(menuNo.equals("123649")||menuNo.equals("124822")||menuNo.equals("124824")){
			paramMap.put("TYPE", "123634");
			paramMap.put("LEAVE_TYPE_NO", "123634");
		}else if(menuNo.equals("123650")||menuNo.equals("124823")||menuNo.equals("124825")){
			paramMap.put("TYPE", "123646");
			paramMap.put("LEAVE_TYPE_NO", "123646");
		}else if(menuNo.equals("2441")||menuNo.equals("2449")||menuNo.equals("2454")||menuNo.equals("2479")){
			paramMap.put("TYPE", "21");
			paramMap.put("LEAVE_TYPE_NO", "21");
		}else{
			paramMap.put("TYPE", "0");
		}
		paramMap.put("SY_PARAM_NO", "4156");// 4156为是否可以提前进行人事确认NO
		paramMap.put("IF_PRE_CONF", this.humanAffirmApplyDao
				.getParamValueByCpnyIdAndParamNo(paramMap));
		return humanAffirmApplyDao.getLeaveApplyConfirmListCnt(paramMap);
	}

	/**
	 * 人事确认--出差申请列表(personnel confirm:Evection apply information list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEvectionApplyConfirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("LEAVE_TYPE_NO", "18");// 18是出差申请NO
		paramMap.put("SY_PARAM_NO", "4166");// 4166为是否需要加班转换NO
		paramMap.put("IF_CONVERT", this.humanAffirmApplyDao
				.getParamValueByCpnyIdAndParamNo(paramMap));
		paramMap.put("SY_PARAM_NO", "4156");// 4156为是否可以提前进行人事确认NO
		paramMap.put("IF_PRE_CONF", this.humanAffirmApplyDao
				.getParamValueByCpnyIdAndParamNo(paramMap));
		if (UiUtil.getPageNum(request) > 0) {
			list = humanAffirmApplyDao.getLeaveApplyConfirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = humanAffirmApplyDao.getLeaveApplyConfirmList(paramMap);
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
			map.put("affirmerList", affirmerList);
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 人事确认--出差申请列表(personnel confirm:Evection apply information total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getEvectionApplyConfirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("LEAVE_TYPE_NO", "18");// 18是出差申请NO
		paramMap.put("SY_PARAM_NO", "4156");// 4156为是否可以提前进行人事确认NO
		paramMap.put("IF_PRE_CONF", this.humanAffirmApplyDao
				.getParamValueByCpnyIdAndParamNo(paramMap));
		return humanAffirmApplyDao.getLeaveApplyConfirmListCnt(paramMap);
	}

	/**
	 * 人事确认-外出申请列表(personnel confirm:Egression apply information list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEgressionApplyConfirmList(HttpServletRequest request)
			throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("LEAVE_TYPE_NO", "16201");// 16201是外出申请NO
		paramMap.put("SY_PARAM_NO", "4166");// 4166为是否需要加班转换NO
		paramMap.put("IF_CONVERT", this.humanAffirmApplyDao
				.getParamValueByCpnyIdAndParamNo(paramMap));
		paramMap.put("SY_PARAM_NO", "4156");// 4156为是否可以提前进行人事确认NO
		paramMap.put("IF_PRE_CONF", this.humanAffirmApplyDao
				.getParamValueByCpnyIdAndParamNo(paramMap));
		if (UiUtil.getPageNum(request) > 0) {
			list = humanAffirmApplyDao.getLeaveApplyConfirmList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = humanAffirmApplyDao.getLeaveApplyConfirmList(paramMap);
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
			map.put("affirmerList", affirmerList);
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 人事确认-外出申请总数(personnel confirm:Egression apply information total count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getEgressionApplyConfirmListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("LEAVE_TYPE_NO", "16201");// 16201是外出申请NO
		paramMap.put("SY_PARAM_NO", "4156");// 4156为是否可以提前进行人事确认NO
		paramMap.put("IF_PRE_CONF", this.humanAffirmApplyDao
				.getParamValueByCpnyIdAndParamNo(paramMap));
		return humanAffirmApplyDao.getLeaveApplyConfirmListCnt(paramMap);
	}

	/**
	 * 休假申请人事确认通过/否决(human confirm pass or reject)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveLeaveApplyHumanConfirm(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String confirmFlag = paramMap.get("CONFIRM_FLAG") != null ? paramMap
				.get("CONFIRM_FLAG").toString() : "";
		LinkedHashMap applyMap = (LinkedHashMap) this.humanAffirmApplyDao
				.getLeaveApplyInfoByApplyNo(paramMap);
		applyMap.put("deptNo", admin.getDeptNo());
		applyMap.put("ADMIN_ID", admin.getPersonId());
		applyMap.put("CPNY_ID", admin.getCpnyId());
		applyMap.put("CONFIRM_FLAG", confirmFlag);
		String navTabId=request.getParameter("navTabId");
		applyMap.put("navTabId", navTabId);
		return this.humanAffirmApplyDao.saveLeaveApplyHumanConfirm(applyMap);
	}

	/**
	 * 休假申请批量人事确认通过/否决(batch human confirm pass or reject)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveLeaveApplyHumanConfirmInBatch(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List list = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String confirmFlag = paramMap.get("CONFIRM_FLAG") != null ? paramMap
				.get("CONFIRM_FLAG").toString() : "";
		String[] paramData = request.getParameterValues("c1");
		for (int i = 0; i < paramData.length; i++) {
			paramMap.put("APPLY_NO", paramData[i]);
			LinkedHashMap applyMap = (LinkedHashMap) this.humanAffirmApplyDao
					.getLeaveApplyInfoByApplyNo(paramMap);

			applyMap.put("deptNo", admin.getDeptNo());
			applyMap.put("ADMIN_ID", admin.getPersonId());
			applyMap.put("CPNY_ID", admin.getCpnyId());
			applyMap.put("CONFIRM_FLAG", confirmFlag);
			String navTabId=request.getParameter("navTabId");
			applyMap.put("navTabId", navTabId);
			list.add(applyMap);
		}
		return this.humanAffirmApplyDao
				.saveLeaveApplyHumanConfirmForBatch(list);
	}

	/**
	 * 查看是否需要加班转换(get If Conver Value)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getIfConverValue(HttpServletRequest request) throws Exception {
		// 页面提交数据
		LinkedHashMap paramMap = getMapByRequest(request);
		paramMap.put("SY_PARAM_NO", "4166");// 4166为是否需要加班转换NO
		Object obj = this.humanAffirmApplyDao
				.getParamValueByCpnyIdAndParamNo(paramMap);
		return obj != null ? obj.toString() : "0";
	}
}