package com.ait.ar.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.DynamicGroupDao;
import com.ait.ar.dao.ItemsDao;
import com.ait.ar.service.ItemsSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ItemsSerImp.java
 * @Description:
 * @Create date: 2012-1-7 下午04:13:37
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ItemsSerImp implements ItemsSer {

	Logger logger = Logger.getLogger(ItemsSerImp.class);

	@Autowired
	private ItemsDao itemsDao;
	@Autowired
	private DynamicGroupDao dynamicGroupDao;

	/**
	 * 取得项目(get Item)
	 * 
	 * @param request
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object getItem(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.itemsDao.getItem(paramMap);
	}

	/**
	 * 显示项目列表(get Item List)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getItemList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = itemsDao.getItemList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = itemsDao.getItemList(paramMap);
		}

		return retrunList;
	}
	
	
	/**
	 * 显示项目列表(get Item List)添加明细项目法人参数的时候的列表
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getItemListSelect(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String CPNY_ID = admin.getCpnyId();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(CPNY_ID != null && !"".equals(CPNY_ID)){
			paramMap.put("CPNY_ID", CPNY_ID);
		}
		retrunList = itemsDao.getItemListSelect(paramMap);
		return retrunList;
	}
	
	
	/**
	 * 调整参数顺序(update SummaryItemInfo CalOrder)
	 * @param request
	 * @return int
	 * @throws
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int updateParamItemInfoCalOrder(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		List<LinkedHashMap> list = new ArrayList<LinkedHashMap>();
		
		LinkedHashMap param1 = new LinkedHashMap();
		param1.put("PARAM_NO", paramMap.get("downParamNo"));
		param1.put("CAL_ORDER", paramMap.get("downOrder"));
		list.add(param1);
		LinkedHashMap param2 = new LinkedHashMap();
		param2.put("PARAM_NO", paramMap.get("upParamNo"));
		param2.put("CAL_ORDER", paramMap.get("upOrder"));
		list.add(param2);
		
		return this.itemsDao.updateParamItemInfoCalOrder(list) ;
	}

	 /**
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getAllItemList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PERSON_ID",
				admin.getPersonId() != null ? admin.getPersonId() : "");
		paramMap.put("USERNAME",
				admin.getUsername() != null ? admin.getUsername() : "");
		// "1"表示为工资的计算结果
		paramMap.put("FUNCTIONFLAG", request.getAttribute("FUNCTIONFLAG"));
		paramMap.put("DISTINGUISH", request.getAttribute("DISTINGUISH"));
		return itemsDao.getItemList(paramMap);
	}

	/**
	 * 保存项目库信息(add Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addItemInfo(HttpServletRequest request) {
       
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());

		try {

			this.itemsDao.addItemInfo(paramMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return 1;

	}
	
	
	@SuppressWarnings("unchecked")
	public int addItemInfo(HttpServletRequest request,String cpny_id,String item_no) {
		String personId = request.getParameter("personId");
		LinkedHashMap paramMap = new LinkedHashMap();
		if(personId != null || "".equals(personId)){
			paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getAdminID());
		}else{
			paramMap.put("CREATED_BY", personId);
		}
		
		if (cpny_id != null && !"".equals(cpny_id)) {
			paramMap.put("CPNY_ID", cpny_id);
		}
		if (item_no != null && !"".equals(item_no)) {
			paramMap.put("AR_ITEM_NO", item_no);
		}
		
		if (request.getParameter("ACTIVITY") == null) {
			paramMap.put("ACTIVITY", 1);
		}
		 if(request.getParameter("flag")=="1" || "1".equals(request.getParameter("flag"))){
	        	paramMap.put("SHORT_NAME", request.getParameter("ITEM_NAME"));
	        }
		try {

			this.itemsDao.addItemInfoAffirm(paramMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return 1;

	}

	/*
	 * 更新项目库信息(add Item Info)
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateItemInfo(HttpServletRequest request) {

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 修改人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDATED_BY", admin.getPersonId());
		try {

			this.itemsDao.updateItemInfo(paramMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;

	}

	/**
	 * 删除项目信息(delete Item Info)
	 * 
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int deleteItemInfo(HttpServletRequest request) {

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		try {

			this.itemsDao.deleteItemInfo(paramMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	@Override
	public int getItemCnt(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		return itemsDao.getItemCnt(paramMap);
	}

	/**
	 * 根据项目明细取得参数列表(get ItemParameter List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getItemParameterList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
      
		return itemsDao.getItemParameterList(paramMap);
	}
	
	/**
	 * 根据项目明细取得参数列表(get ItemParameter List)（明细项目法人参数页面）
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getItemParamList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
	    retrunList = itemsDao.getItemParamList(paramMap);
		return retrunList;
	}
	/**
	 * 根据项目明细取得参数列表(get ItemParameter List)（明细项目法人参数页面）
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getItemParamList2(HttpServletRequest request) {
		List retrunList = new ArrayList();
		        
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		retrunList = itemsDao.getItemParamList2(paramMap);
		return retrunList;
	}
	@SuppressWarnings("unchecked")
	public List getItemParamListForKaoqin(HttpServletRequest request) {
		List retrunList = new ArrayList();
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		retrunList = itemsDao.getItemParamListForKaoqin(paramMap);
		return retrunList;
	}
	
	
	/**
	 * （明细项目法人参数页面）
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getItemParamList_cpny(HttpServletRequest request) {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		String ITEM_NAME = request.getParameter("ITEM_NAME");
		if(ITEM_NAME != null && !"".equals(ITEM_NAME)){
			paramMap.put("ITEM_NAME", ITEM_NAME);
		}
		if (UiUtil.getPageNum(request) > 0) {
			try {
				retrunList = itemsDao.getItemParamList(paramMap,
						UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			retrunList = itemsDao.getItemParamList(paramMap);
		}
		return retrunList;
	}
	

	@Override
	public int getItemParamCnt(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		String ITEM_NAME = request.getParameter("ITEM_NAME");
		if(ITEM_NAME != null && !"".equals(ITEM_NAME)){
			paramMap.put("ITEM_NAME", ITEM_NAME);
		}
		return itemsDao.getItemParamCnt(paramMap);
	}
	
	
	public int getItemParamCnt() {
		return itemsDao.getItemParamCnt();
	}


	@SuppressWarnings("unchecked")
	public Object getItemParameter(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		return itemsDao.getItemParameter(paramMap);
	}

	/**
	 * 添加项目明细参数(add ItemParameter Info)
	 * 
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int addItemParameterInfo(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		String[] dataTypes = null;
		dataTypes = request.getParameterValues("DATE_TYPE");
		String dataType = "";
		if (dataTypes != null) {
			for (int i = 0; i < dataTypes.length; ++i) {
				dataType += dataTypes[i];
				if (i < dataTypes.length - 1) {
					dataType += ",";
				}
			}
		}

		paramMap.put("DATE_TYPE", dataType);

		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getPersonId());
			itemsDao.addItemParameterInfo(paramMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	/**
	 * 修改明细项目参数(add CycleParam Info)
	 * 
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int updateItemParameterInfo(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		String[] dataTypes = null;
		dataTypes = request.getParameterValues("DATE_TYPE");
		String dataType = "";
		if (dataTypes != null) {
			for (int i = 0; i < dataTypes.length; ++i) {
				dataType += dataTypes[i];
				if (i < dataTypes.length - 1) {
					dataType += ",";
				}
			}
		}

		paramMap.put("DATE_TYPE", dataType);

		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("UPDATED_BY", admin.getPersonId());
			itemsDao.updateItemParameterInfo(paramMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	/**
	 * 删除明细项目参数(delete ItemParameter Info)
	 * 
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int deleteItemParameterInfo(HttpServletRequest request) {

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {

			itemsDao.deleteItemParameterInfo(paramMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return 1;

	}
	
	/**
	 * 明细项目法人参数  删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteItemParamInfo(HttpServletRequest request) {

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		try {

			itemsDao.deleteItemParamInfo(paramMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return 1;

	}

	/**
	 * 取得动态组列表(get DynamicGroup List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	public List getDynamicGroupList(HttpServletRequest request,
			ModelMap modelMap) {

		List retrunList = new ArrayList();
		Map paramMap = new LinkedHashMap();
		// session用户信息
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String seachCpny = request.getParameter("seach_cpnyId");

		paramMap.put("CPNY_ID",
				StringUtils.isEmpty(seachCpny) ? admin.getCpnyId() : seachCpny);
		paramMap.put("interLanguage", admin.getLanguage());
		modelMap.put("defaultCpny", admin.getCpnyId());
		retrunList = dynamicGroupDao.getDynamicGroupList(paramMap);

		return retrunList;
	}

	/**
	 * 检查项目是否存在(check For Item Delete)
	 * 
	 * @param request
	 * @return int
	 * @throws
	 */
	@Override
	public int checkForItemDelete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		return itemsDao.checkForItemDelete(paramMap);
	}

	/**
	 * 检查项目是否存在(check ForItemParam Delete)
	 * 
	 * @param request
	 * @return int
	 * @throws
	 */
	@Override
	public int checkForItemParamDelete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		return itemsDao.checkForItemParamDelete(paramMap);
	}

	/**
	 * 检查项目明细参数(check For ItemParam Unique)
	 * 
	 * @param request
	 * @return int
	 * @throws
	 */
	@Override
	public int checkForItemParamUnique(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if(paramMap.get("GROUP_NO")!=null && !"".equals(paramMap.get("GROUP_NO"))){
			paramMap.put("AR_GROUP_NO", paramMap.get("GROUP_NO"));
		}else{
			paramMap.put("AR_GROUP_NO", "constant");
		}
		if(paramMap.get("ITEM_NO")!=null && !"".equals(paramMap.get("ITEM_NO"))){
			paramMap.put("AR_ITEM_NO",paramMap.get("ITEM_NO"));
		}else{
			paramMap.put("AR_ITEM_NO",request.getParameter("ITEM_NO"));
		}
		return itemsDao.checkForItemParamUnique(paramMap);
	}
	
	public int checkForItemParam(HttpServletRequest request,String cpny_id,String item_no,String group_no) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", cpny_id);
		paramMap.put("GROUP_NO", group_no);
		paramMap.put("ITEM_NO", item_no);
		return itemsDao.checkForItemParam(paramMap);
	}

	/**
	 * 检查唯一性(check ItemInfo Unique)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int checkItemInfoUnique(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return itemsDao.checkItemInfoUnique(paramMap);
	}

	/**
	 * 根据项目取有效日期类型列表(get DataType List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getDataTypeList(Object obj) {

		return itemsDao.getDataTypeList(obj);
	}

	/**
	 * 获取申请类型
	 */
	@SuppressWarnings("unchecked")
	public List getApplyList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return itemsDao.getApplyList(paramMap);
	}

	/**
	 * 根据item_no查找和该项目已经mapping的记录删除掉
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateItemParamInfoAll(String item_no)
			throws Exception {
	 
		return this.itemsDao.updateItemParamInfoAll(item_no);
	}

	/**
	 * 根据不同的参数来添加考勤代码（明细项目参数）
	 * 
	 * @param request
	 * @param cpny_id
	 * @param item_no
	 * @return 
	 * @throws Exception 
	 */
	public int addItemParamInfo(HttpServletRequest request, String cpny_id,
			String item_no){
		String personId = request.getParameter("personId");
		LinkedHashMap paramMap = new LinkedHashMap();
		if(personId != null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("CREATED_BY", admin.getAdminID());
		}else{
			paramMap.put("CREATED_BY", personId);
		}
		String group_no = request.getParameter("GROUP_NO");
		
		if (cpny_id != null && !"".equals(cpny_id)) {
			paramMap.put("CPNY_ID", cpny_id);
		}
		if (group_no == null || "".equals(group_no)) {
			paramMap.put("GROUP_NO", "constant");
		}
		if (item_no != null && !"".equals(item_no)) {
			paramMap.put("AR_ITEM_NO", item_no);
		}
		
		if (request.getParameter("ACTIVITY") == null) {
			paramMap.put("ACTIVITY", 1);
		}
		paramMap.put("UNIT_VALUE", "0.5");
		paramMap.put("MIN_VALUE", "0.5");
		paramMap.put("MAX_VALUE", "8");
		paramMap.put("CARD_FLAG", 0);
		paramMap.put("CARD_FROM_FLAG", 1);
		paramMap.put("CARD_FROM_OFFSET", 0);
		paramMap.put("CARD_FROM_RELATION", "＝");
		paramMap.put("CARD_TO_FLAG", 1);
		paramMap.put("CARD_TO_OFFSET", 0);
		paramMap.put("CARD_TO_RELATION", "＝");
		paramMap.put("APPLY_FLAG", 1);
		paramMap.put("APPLY_TYPE", "141440");
		paramMap.put("APPLY_FULLDAY_VALUE", "8");
		paramMap.put("APPLY_CARD_PRIORITY", 1);
		paramMap.put("DATE_TYPE", "1440");
		paramMap.put("UNIT", "HOUR");
		try {
			itemsDao.addItemParameterInfo(paramMap);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int updateItemParamInfo(HttpServletRequest request, String cpny_id,
			String item_no){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		if (cpny_id != null && !"".equals(cpny_id)) {
			paramMap.put("CPNY_ID", cpny_id);
		}
		if (item_no != null && !"".equals(item_no)) {
			paramMap.put("ITEM_NO", item_no);
		}
		paramMap.put("UPDATED_BY", admin.getAdminID());
		if (request.getParameter("ACTIVITY") == null) {
			paramMap.put("ACTIVITY", 1);
		}
		try {
			itemsDao.updateItemParameter(paramMap);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 取得项目法人参数的人事政策(get Item)
	 * 
	 * @param request
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object getParamItem(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.itemsDao.getParamItem(paramMap);
	}

	/**
	 * 检查唯一性(check ParamItemInfo Unique)
	 * 
	 * @param request
	 * @return int
	 */
	@Override
	public int checkParamItemInfoUnique(HttpServletRequest request) {
		// TODO Auto-generated method stub
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return itemsDao.checkParamItemInfoUnique(paramMap);
	}

	/*
	 * 更新项目参数库信息(add Item Info)
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateParamItemInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 修改人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDATED_BY", admin.getPersonId());
		try {

			this.itemsDao.updateParamItemInfo(paramMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
