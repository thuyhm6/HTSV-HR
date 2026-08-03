package com.ait.ess.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.DimissionEditionDao;
import com.ait.ess.dao.EditionAffirmDao;
import com.ait.ess.service.DimissionEditionSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.businessobjects.crystalreports.viewer.core.e;

@Service
public class DimissionEditionSerImpl implements DimissionEditionSer {
	@Autowired
	private DimissionEditionDao dimissionEditionDao;
	
	@Autowired
	private EditionAffirmDao editionAffirmDao;
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	//离职交接决裁邀请名称
	private static String APPLY_TYPE_NAME = "离职交接审批邀请";
	
	

	/**
	 * 添加离职交接模版
	 */
	@Override
	public int addDimissionEditionInfo(HttpServletRequest request)
			throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("EDITION_NO", request.getParameter("EDITION_NO"));
		String[] editionNo = request.getParameterValues("check_EMP_TYPE_CODE");
		map.put("REMARK", request.getParameter("REMARK"));
		map.put("CPNY_ID", admin.getCpnyId());
		map.put("ACTIVITY", request.getParameter("ACTIVITY"));
		map.put("CREATED_BY", admin.getAdminID());
		int num = 0;
		if (editionNo.length > 0) {
			for (int i = 0; i < editionNo.length; i++) {
				map.put("EMP_TYPE_CODE", editionNo[i]);
				num = dimissionEditionDao.addDimissionEditionInfo(map);
			}
		}
		return num;
	}

	/**
	 * 删除离职交接模版
	 */
	@Override
	public int deleteDimissionEditionInfo(HttpServletRequest request)
			throws Exception {
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> isCorpInfo = ObjectBindUtil
				.getRequestJsonData(jsonString);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int num = 0;
		if (isCorpInfo.size() > 0) {
			for (int i = 0; i < isCorpInfo.size(); i++) {
				paramMap.put("CPNY_ID", admin.getCpnyId());
				paramMap.put("EDITION_NO", isCorpInfo.get(i).get("EDITION_NO"));
				num = dimissionEditionDao.deleteDimissionEditionInfo(paramMap);
			}
		}
		return num;
	}

	/**
	 * 修改离职交接模版
	 */
	@Override
	public int updateDimissionEditionInfo(HttpServletRequest request)
			throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("EDITION_NO", request.getParameter("EDITION_NO"));
		map.put("CPNY_ID", admin.getCpnyId());
		return dimissionEditionDao.updateDimissionEditionInfo(map);
	}

	/**
	 * 查找离职交接模版LIst
	 */
	@Override
	public List getDimissionEditionList(HttpServletRequest request)
			throws Exception {
		List retrunList = new ArrayList();
		List retrunlist = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0) {
			List list = dimissionEditionDao.getDimissionEditionList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					LinkedHashMap map = (LinkedHashMap) list.get(i);
					paramMap.put("EDITION_NO", map.get("EDITION_NO"));
					retrunlist = dimissionEditionDao
							.findEmpTypeByeditionNo(paramMap);
					String str = null;
					int num = 0;
					if (retrunlist.size() > 0) {
						for (int j = 0; j < retrunlist.size(); j++) {
							LinkedHashMap mapType = (LinkedHashMap) retrunlist
									.get(j);
							if (num == 0) {
								str = mapType.get("EMP_TYPE_NAME").toString();
							} else {
								str += ","
										+ mapType.get("EMP_TYPE_NAME")
												.toString();
							}
							map.put("EMP_TYPE_NAME", str);
							num++;
						}
					}
					retrunList.add(map);
				}
			}
		} else {
			retrunList = dimissionEditionDao.getDimissionEditionList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 查找全部离职交接模版
	 */
	public List getDimissionEditionListUpdate(HttpServletRequest request)
			throws Exception {
		List retrunList = new ArrayList();
		List retrunlist = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0) {
			List list = dimissionEditionDao.getDimissionEditionListUpdate(
					paramMap, UiUtil.getPageNum(request),
					UiUtil.getNumPerPage(request));
		} else {
			retrunList = dimissionEditionDao
					.getDimissionEditionListUpdate(paramMap);
		}
		return retrunList;
	}

	public List findEmpTypeByeditionNo(HttpServletRequest request) {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = dimissionEditionDao.findEmpTypeByeditionNo(paramMap);
		return retrunList;
	}

	/**
	 * 查找离职交接模版LIst
	 */
	public List getDimissionEditionlist(HttpServletRequest request)
			throws Exception {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = dimissionEditionDao.getDimissionEditionList(paramMap);
		return retrunList;
	}

	/**
	 * 查找离职交接模版单行记录的详细信息
	 */
	@Override
	public Object getDimissionEditionInfo(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return this.dimissionEditionDao.getDimissionEditionInfo(paramMap);
	}

	/**
	 * 查找离职交接模版数量
	 */
	@Override
	public int getDimissionEditionCnt(HttpServletRequest request)
			throws Exception {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunInt = dimissionEditionDao.getDimissionEditionCnt(paramMap);
		return retrunInt;
	}

	/**
	 * 查找人员类型（修改）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEmpTypeNameListUpdate(HttpServletRequest request)
			throws SQLException {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("EDITION_NO", request.getParameter("EDITION_NO"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = dimissionEditionDao.getEmpTypeNameListUpdate(paramMap);
		return retrunList;
	}

	/**
	 * 查找人员类型
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEmpTypeNameListAdd(HttpServletRequest request)
			throws SQLException {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EDITION_NO", request.getParameter("EDITION_NO"));
		retrunList = dimissionEditionDao.getEmpTypeNameListAdd(paramMap);
		return retrunList;
	}

	/***
	 * 查找离职交接模版的版本号 添加的时候不可以重复
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getDimissionEditionListAdd(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EDITION_NO1", request.getParameter("EDITION_NO"));
		return this.dimissionEditionDao.getDimissionEditionListAdd(paramMap);
	}

	/********************************** 离职交接类型 ***********************************************/

	/**
	 * 添加离职交接类型
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int addEditionItemTypeInfo(HttpServletRequest request)
			throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("EDITION_NO", request.getParameter("EDITION_NO"));
		map.put("EDITION_ITEM_TYPE", request.getParameter("EDITION_ITEM_TYPE"));
		map.put("REMARK", request.getParameter("REMARK"));
		map.put("CPNY_ID", admin.getCpnyId());
		map.put("ACTIVITY", request.getParameter("ACTIVITY"));
		map.put("CREATED_BY", admin.getAdminID());
		return dimissionEditionDao.addEditionItemTypeInfo(map);
	}

	/**
	 * 修改离职交接类型
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateEditionItemTypeInfo(HttpServletRequest request)
			throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int num = 0;
		map.put("EDITION_NO", request.getParameter("EDITION_NO"));
		map.put("EDITION_ITEM_TYPE", request.getParameter("EDITION_ITEM_TYPE"));
		map.put("REMARK", request.getParameter("REMARK"));
		map.put("CPNY_ID", admin.getCpnyId());
		map.put("ACTIVITY", request.getParameter("ACTIVITY"));
		map.put("ORDERNO", request.getParameter("ORDERNO"));
		map.put("UPDATED_BY", admin.getAdminID());
		num = dimissionEditionDao.updateEditionItemTypeInfo(map);
		return num;
	}

	/**
	 * 删除离职交接类型
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int deleteEditionItemTypeInfo(HttpServletRequest request)
			throws Exception {
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> isCorpInfo = ObjectBindUtil
				.getRequestJsonData(jsonString);

		List<LinkedHashMap<String, Object>> isCorpInfo1 = ObjectBindUtil
				.getRequestJsonData(jsonString);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int num = 0;
		if (isCorpInfo.size() > 0) {
			for (int i = 0; i < isCorpInfo.size(); i++) {
				paramMap.put("EDITION_ITEM_TYPE",
						isCorpInfo.get(i).get("EDITION_ITEM_TYPE"));
				num = dimissionEditionDao.deleteEditionItemTypeInfo(paramMap);
			}
		}
		return num;
	}

	/**
	 * 查找离职交接类型单行记录的详细信息
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getEditionItemTypeInfo(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EDITION_NO", request.getParameter("EDITION_NO"));
		paramMap.put("EDITION_ITEM_TYPE",
				request.getParameter("EDITION_ITEM_TYPE"));
		return this.dimissionEditionDao.getEditionItemTypeInfo(paramMap);
	}

	/**
	 * 查找离职交接类型数量
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionItemTypeCnt(HttpServletRequest request)
			throws SQLException {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunInt = dimissionEditionDao.getEditionItemTypeCnt(paramMap);
		return retrunInt;
	}

	/**
	 * 查找离职交接内容表的离职交接内容类型的List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionItemTypeList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EDITION_NO", request.getParameter("EDITION_NO"));
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = dimissionEditionDao.getEditionItemTypeList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = dimissionEditionDao.getEditionItemTypeList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 查找版同一版本的交接类型 添加的时候类型名称不可以重复
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionItemList(HttpServletRequest request)
			throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EDITION_NO", request.getParameter("EDITION_NO"));
		paramMap.put("EDITION_ITEM_TYPE1",
				request.getParameter("EDITION_ITEM_TYPE"));
		return this.dimissionEditionDao.getEditionItemList(paramMap);
	}

	/************************* 交接类型的具体项目 ***************************/

	/**
	 * 查找离职交接内容表的离职交接内容项目的List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionItemTypeParamList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EDITION_NO", request.getParameter("EDITION_NO1"));
		paramMap.put("EDITION_ITEM_TYPE",
				request.getParameter("EDITION_ITEM_TYPE"));
		if (request.getParameter("EDITION_ITEM_NO") != null
				&& !"".equals(request.getParameter("EDITION_ITEM_NO"))) {
			paramMap.put("EDITION_ITEM_NO",
					request.getParameter("EDITION_ITEM_NO"));
		}
		retrunList = dimissionEditionDao.getEditionItemTypeParamList(paramMap);
		return retrunList;
	}

	/**
	 * 查找离职交接表类型项目数量
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionItemTypeParamCnt(HttpServletRequest request)
			throws SQLException {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EDITION_NO", request.getParameter("EDITION_NO1"));
		paramMap.put("EDITION_ITEM_TYPE",
				request.getParameter("EDITION_ITEM_TYPE"));
		retrunInt = dimissionEditionDao.getEditionItemTypeParamCnt(paramMap);
		return retrunInt;
	}

	/**
	 * 添加离职交接类型的具体项目
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int addEditionItemTypeParamInfo(HttpServletRequest request)
			throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("EDITION_NO", request.getParameter("EDITION_NO"));
		map.put("EDITION_ITEM_TYPE", request.getParameter("EDITION_ITEM_TYPE"));
		map.put("EDITION_ITEM_NO", request.getParameter("EDITION_ITEM_NO"));
		map.put("REMARK", request.getParameter("REMARK"));
		map.put("CPNY_ID", admin.getCpnyId());
		map.put("CREATED_BY", admin.getAdminID());
		return dimissionEditionDao.addEditionItemTypeParamInfo(map);
	}

	/**
	 * 修改离职交接类型的具体项目
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateEditionItemTypeParamInfo(HttpServletRequest request)
			throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("CPNY_ID", admin.getCpnyId());
		int num = 0;
		if (request.getParameter("index")!=null && !"".equals(request.getParameter("index"))) {
			int count = Integer.parseInt(request.getParameter("index"));
			for (int i = 0; i < count; i++) {
				int count1 = Integer
						.parseInt(request.getParameter("index" + i));
				for (int j = 0; j < count1; j++) {
					map.put("EDITION_ITEM_NO", request
							.getParameter("EDITION_ITEM_NO" + i + "" + j));
					if(request.getParameter("dwz.dept" +i+""+j+ ".manageId")!=null
							&& !"".equals(request.getParameter("dwz.dept" + i + "" + j + ".manageId"))){
						map.put("PERSON_ID",
								request.getParameter("dwz.dept" + i + "" + j
										+ ".manageId"));
					}else{
					map.put("PERSON_ID",
							request.getParameter("dwz.person" + i + "" + j
									+ ".personId"));
					}
					num = dimissionEditionDao
							.updateEditionItemTypeParamInfo(map);
				}
			}
		}else{
			num = dimissionEditionDao
					.updateEditionItemTypeParamInfo1(map);
		}
		return num;
	}

	public int updateEditionItemTypeParam(HttpServletRequest request)
			throws Exception {
		Map map = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("CPNY_ID", admin.getCpnyId());
		map.put("EDITION_ITEM_NO", request.getParameter("EDITION_ITEM_NO"));
		map.put("ACTIVITY", request.getParameter("ACTIVITY"));
		map.put("PERSON_ID", admin.getPersonId());
		return dimissionEditionDao.updateEditionItemTypeParamInfo(map);
	}

	/**
	 * 删除离职交接类型的具体项目
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int deleteEditionItemTypeParamInfo(HttpServletRequest request)
			throws Exception {
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> isCorpInfo = ObjectBindUtil
				.getRequestJsonData(jsonString);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		int num = 0;
		if (isCorpInfo.size() > 0) {
			for (int i = 0; i < isCorpInfo.size(); i++) {
				paramMap.put("EDITION_ITEM_NO",
						isCorpInfo.get(i).get("EDITION_ITEM_NO"));
				num = dimissionEditionDao
						.deleteEditionItemTypeParamInfo(paramMap);
			}
		}
		return num;
	}

	/********************************** 交接进度查询 *********************************/

	/**
	 * 查找离职交接进度详情数量
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionCheckCnt(HttpServletRequest request)
			throws SQLException {
		int retrunInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(request.getParameter("flag")=="1" || "1".equals(request.getParameter("flag"))){
		    paramMap.put("PERSON_ID_MYSELF", admin.getPersonId());
		}else{
			paramMap.put("AFFIRM_ID", admin.getPersonId());
		}
		retrunInt = dimissionEditionDao.getEditionCheckCnt(paramMap);
		return retrunInt;
	}

	/**
	 * 查找离职交接进度的List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionCheckList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(request.getParameter("flag")=="1" || "1".equals(request.getParameter("flag"))){
		    paramMap.put("PERSON_ID_MYSELF", admin.getPersonId());
		}else{
			paramMap.put("AFFIRM_ID", admin.getPersonId());
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = dimissionEditionDao.getEditionCheckList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = dimissionEditionDao.getEditionCheckList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 修改离职交接项目
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateEditionItemParamInfo(HttpServletRequest request)
			throws Exception {
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap paramMap = ObjectBindUtil
					.getRequestParamData(request);
			String[] isChecked = request.getParameterValues("isChecked");
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			for (int i = 0; i < isChecked.length; i++) {
				paramMap.put("LEAVE_TIME",
						paramMap.get("LEAVE_TIME" + "_" + isChecked[i]));
				paramMap.put("LEAVE_TYPE",
						paramMap.get("LEAVE_TYPE" + "_" + isChecked[i]));
				paramMap.put("PERSON_ID",
						paramMap.get("PERSON_ID" + "_" + isChecked[i]));
				paramMap.put("APPLY_NO",
						paramMap.get("APPLY_NO" + "_" + isChecked[i]));
				this.dimissionEditionDao.updateEditionItemParamInfo(paramMap);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/********************************** 交接项目设置（添加审批人） *********************************/

	/**
	 * 根据模版号查找所有交接项目
	 * 
	 * @param object
	 * @return
	 */
	public Map<String, Map<String, List>> findEditionItem(
			HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		Map map = new HashMap<String, List>();
		paramMap.put("CPNY_ID", admin.getCpnyId());
		try {
			List listEditionType = this.dimissionEditionDao
					.getEditionItemTypeList(paramMap);
			if (listEditionType.size() > 0) {
				for (int j = 0; j < listEditionType.size(); j++) {
					LinkedHashMap paramMap1 = (LinkedHashMap) listEditionType
							.get(j);
					paramMap.put("EDITION_ITEM_TYPE",
							paramMap1.get("EDITION_ITEM_TYPE"));
					List listEditionItem = this.dimissionEditionDao
							.getEditionItemTypeParamList(paramMap);
					map.put(listEditionType.get(j), listEditionItem);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
		return map;
	}
	
	/**
	 * 审批交接项目
	 */
	 public int updatepersonEditionParamInfo(HttpServletRequest request) throws Exception{
		 LinkedHashMap map = new LinkedHashMap();
			map = ObjectBindUtil.getRequestParamDataNoSession(request);
			map.put("EDITION_PERSON_NO", request.getParameter("EDITION_PERSON_NO"));
			map.put("PERSON_ID",request.getParameter("PERSON_ID"));
			map.put("AFFIRM_FLAG", Integer.parseInt(request.getParameter("FLAG").toString()));
			map.put("PID", request.getParameter("PERSON_ID"));
			map.put("interLanguage", "zh");
			map.put("LANGUAGE", "zh");
		try {	
			//决裁
			this.dimissionEditionDao.updatepersonEditionParamInfo(map);
			if(map.get("AFFIRM_FLAG")!= null && !"2".equals(map.get("AFFIRM_FLAG").toString())){//终止或者否决直接修改标志位,通过继续下面操作
				//获取同部门决裁者列表
				map.put("DEPTNO", request.getParameter("DEPTNO"));
				map.put("AFFIRM_FLAG", "0");
				List<LinkedHashMap> affirmList = editionAffirmDao.getAffirmEditionItemList(map);
				//获取不是同部门的决裁者决裁情况（如果有决裁的则不能再发送邀请）
				map.put("DEPTNO","");
				map.put("DEPTNO1", request.getParameter("DEPTNO"));
				map.put("AFFIRM_FLAG", "1");
				List<LinkedHashMap> affirmQt = editionAffirmDao.getAffirmEditionItemList(map);
				if (affirmQt == null || affirmQt.size() == 0) {
					// 查看本部门的人是不是都通过 如果本部门的人全部通过则 则查找除了人事部门的人未审批的人发送审批邀请
					if (affirmList == null || affirmList.size() == 0) {
						map.put("AFFIRM_FLAG", "0");
						// 查找除了本部门和人事部门的审批者
						List<LinkedHashMap> affirmListor = editionAffirmDao
								.getAffirmEditionItemList(map);
						if (affirmListor != null || affirmListor.size() > 0) {
							for (int i = 0; i < affirmListor.size(); i++) {
								map.put("AFFIRM_FLAG", "0");
								map.put("APPLY_NO",
										request.getParameter("APPLY_NO"));
								map.put("CURRENT_AFFIRM_ID",affirmListor.get(i).get("AFFIRM_PERSON_ID"));
								map.put("EDITION_PERSON_NO",affirmListor.get(i).get("EDITION_PERSON_NO"));
								this.sendToLGEPInsert(map);
							}
						}
					}
				}else{
					int num = 0;
					//查看除了本部门的人和人事部门的人是不是都审批通过  如果都通过就给其他
					map.put("AFFIRM_FLAG", "0");
					// 查找除了本部门和人事部门的审批者
					List<LinkedHashMap> affirmListQtor = editionAffirmDao
							.getAffirmEditionItemList(map);
					if((affirmListQtor==null || affirmListQtor.size()==0) && num==0){
						//给剩下没审批的人发送 审批邀请   剩下的审批人 也就是人事部门的审批人
						num =1;
						List<LinkedHashMap> affirmListHr = this.editionAffirmDao.getAffirmEditionItemList(map);
						if(affirmListHr!=null || affirmListHr.size()>0){
							for(int i=0;i<affirmListHr.size();i++){
								map.put("CURRENT_AFFIRM_ID",affirmListHr.get(i).get("AFFIRM_PERSON_ID"));
								map.put("EDITION_PERSON_NO",affirmListHr.get(i).get("EDITION_PERSON_NO"));
								this.sendToLGEPInsert(map);
							}
						}
					}
				}
				map.put("DEPTNO1","");
				List listAffirm = this.editionAffirmDao.getAffirmEditionItemList(map);
				map.put("AFFIRM_FLAG","1");
				List listAffirmor = this.editionAffirmDao.getAffirmEditionItemList(map);
				if(request.getParameter("personId")==null || "".equals(request.getParameter("personId"))){
					AdminBean admin = SessionUtil.getLoginUserFromSession(request);
					map.put("CURRENT_AFFIRM_ID", admin.getAdminID());
				}else{
					map.put("CURRENT_AFFIRM_ID", request.getParameter("personId"));
				}
				map.put("APPLY_NO",request.getParameter("APPLY_NO"));
				this.sendToLGEP(map);
				if(listAffirm.size()>0 && listAffirmor.size()>0 && listAffirm.size()==listAffirmor.size()){
					editionAffirmDao.affirmDimissionItemInfo(map);
				}
			}else{
				map.put("APPLY_NO",request.getParameter("APPLY_NO"));
				editionAffirmDao.affirmDimissionItemInfo(map);
				if(request.getParameter("personId")==null || "".equals(request.getParameter("personId"))){
					AdminBean admin = SessionUtil.getLoginUserFromSession(request);
					map.put("CURRENT_AFFIRM_ID", admin.getAdminID());
				}else{
					map.put("CURRENT_AFFIRM_ID", request.getParameter("personId"));
				}
				map.put("FLAG", "2");
				map.put("EDITION_PERSON_NO", "");
				this.sendToLGEP(map);
			}
	    } catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	 }
	 
	 /**
		 * 审批后发送LGEP
		 * @param eventId
		 */
		private void sendToLGEP(LinkedHashMap paramMap){
				LinkedHashMap lgepMap = new LinkedHashMap();
				lgepMap.put("APPLY_TYPE", "1103");
				lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
				if("0".equals(paramMap.get("FLAG").toString())){
					lgepMap.put("AFFIRM_LEVEL","1");
					lgepMap.put("AFFIRM_FLAG", 1);
				}else{//决裁完成\
					lgepMap.put("FINISH", "FINISH");
					lgepMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_FLAG"));
					lgepMap.put("AFFIRM_LEVEL","1");
				}
				lgepMap.put("AFFIRM_EMPID", paramMap.get("PERSON_ID"));
				lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewAffirmEditionItem?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO") + "&PERSON_ID=" + paramMap.get("PERSON_ID") + "&EDITION_PERSON_NO=" + paramMap.get("EDITION_PERSON_NO"));
				lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewAffirmEditionItem?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO") + "&PERSON_ID=" + paramMap.get("PERSON_ID") + "&EDITION_PERSON_NO=" + paramMap.get("EDITION_PERSON_NO"));
				lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewAffirmEditionItem?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO") + "&PERSON_ID=" + paramMap.get("PERSON_ID") + "&EDITION_PERSON_NO=" + paramMap.get("EDITION_PERSON_NO"));
				lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
				lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
				lgepMap.put("CREATED_BY", paramMap.get("CURRENT_AFFIRM_ID"));
				this.affirmInfoToLGEPSer.affirm(lgepMap);
		}
		
		
		/**
		 * 提交后发送LGEP
		 * @param eventId
		 */
		private void sendToLGEPInsert(LinkedHashMap paramMap){
				LinkedHashMap lgepMap = new LinkedHashMap();
				lgepMap.put("APPLY_TYPE", "1103");
				lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
				lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
				lgepMap.put("APPLY_TITLE", APPLY_TYPE_NAME);
				lgepMap.put("APPLY_EMPID", paramMap.get("PERSON_ID"));
				lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewAffirmEditionItem?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO") + "&EDITION_PERSON_NO=" + paramMap.get("EDITION_PERSON_NO") + "&PERSON_ID=" + paramMap.get("PERSON_ID"));
				lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewAffirmEditionItem?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO") + "&EDITION_PERSON_NO=" + paramMap.get("EDITION_PERSON_NO") + "&PERSON_ID=" + paramMap.get("PERSON_ID"));
				lgepMap.put("AFFIRM_LEVEL", "1");
				lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
				lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
				this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
		}
}
