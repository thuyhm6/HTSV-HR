package com.ait.ar.action.attendanceSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;
import com.ait.ar.service.ItemsSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ArAffirmSer;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ItemsCtroller.java
 * @Description:
 * @Create date: 2012-1-7 下午04:10:10
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class ItemsCtroller {
	Logger logger = Logger.getLogger(ItemsCtroller.class);
	
	@Autowired
	private ItemsSer itemsSer ;
	@Autowired
	private CompanySer companySer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	/**
	 * 显示项目列表(view Item List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewItem")
	public ModelAndView viewItemList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List itemList = this.itemsSer.getItemList(request) ;
		int itemCnt = this.itemsSer.getItemCnt(request) ;
		
		modelMap.put("itemList", itemList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2349")) ;
		
		return new ModelAndView("/ar/attendanceSettings/viewItem", modelMap);
	}
	
	/**
	 * 进入添加项目页面(add Item View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addItemView",method = RequestMethod.GET)
	public ModelAndView addItemView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId());
		
		return new ModelAndView("/ar/attendanceSettings/addItemView",modelMap);
	}
	
	/**
	 * 添加项目信息(add Item Info)
	 * @param request
	 * @return Map
	 * @throws 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addItemInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addItemInfo(HttpServletRequest request){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int unique = this.itemsSer.checkItemInfoUnique(request);
		
		if(unique == 0){
			int result = this.itemsSer.addItemInfo(request);
			
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
				map.put("navTabId", "ar0304");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.ID_conflict",request));//所添加的项目ID已经存在,不能重复添加
		}
		
		return map;		
	}
	
	/**
	 * 修改项目信息(add Item Info)
	 * @param request
	 * @return Map
	 * @throws 
	 */
	@RequestMapping(value = "/updateItemView",method = RequestMethod.GET)
	public ModelAndView updateItemView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("itemInfo", itemsSer.getItem(request)) ;
		
		return new ModelAndView("/ar/attendanceSettings/updateItemView",modelMap);
	}
	
	/**
	 * 修改项目法人参数信息(add Item Info)
	 * @param request
	 * @return Map
	 * @throws 
	 */
	@RequestMapping(value = "/updateItemParamView",method = RequestMethod.GET)
	public ModelAndView updateItemParamView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List applyList=this.itemsSer.getApplyList(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		Map map = (Map)itemsSer.getParamItem(request);
			String dataType = StringUtil.checkNull(map.get("DATE_TYPE"));
			String[] dataTypeArray = dataType.split(",");
			for(int j = 0;j<dataTypeArray.length;j++){
				map.put("DATE_TYPE"+j, dataTypeArray[j]);
			}
		modelMap.put("itemInfo", map) ;
		modelMap.put("applyList", applyList) ;
		
		return new ModelAndView("/ar/attendanceSettings/updateItemParamView",modelMap);
	}
	
	/**
	 * 点击查看  人事政策详细信息
	 * @param request
	 * @return Map
	 * @throws 
	 */
	@RequestMapping(value = "/viewHrPolicy",method = RequestMethod.GET)
	public ModelAndView viewHrPolicy(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("itemInfo", itemsSer.getItem(request)) ;
		return new ModelAndView("/ar/attendanceSettings/viewHrPolicy",modelMap);
	}
	
	/**
	 * 点击查看 项目法人参数人事政策详细信息
	 * @param request
	 * @return Map
	 * @throws 
	 */
	@RequestMapping(value = "/viewParamHrPolicy",method = RequestMethod.GET)
	public ModelAndView viewParamHrPolicy(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("itemInfo", itemsSer.getParamItem(request)) ;
		return new ModelAndView("/ar/attendanceSettings/viewParamHrPolicy",modelMap);
	}
	
	/**
	 * 更新项目信息(update Item Info)
	 * @param request
	 * @return Map
	 * @throws 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateItemInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateItemInfo(HttpServletRequest request){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int unique = this.itemsSer.checkItemInfoUnique(request);
		
		if(unique == 0){
			int result = itemsSer.updateItemInfo(request);
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "ar0304");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.ID_update_conflict",request));//所修改的项目ID已经存在,不能重复
		}
		
		return map;		
	}
	
	/**
	 * 更新项目参数信息(update Item Info)
	 * @param request
	 * @return Map
	 * @throws 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateParamItemInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateParamItemInfo(HttpServletRequest request){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int unique = this.itemsSer.checkParamItemInfoUnique(request);
		
		if(unique == 0){
			int result = itemsSer.updateParamItemInfo(request);
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "ar0304");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.ID_update_conflict",request));//所修改的项目ID已经存在,不能重复
		}
		
		return map;		
	}
	
	/**
	 * 删除项目信息(delete Item Info)
	 * @param request
	 * @return Map
	 * @throws 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/deleteItem",method = RequestMethod.POST)
	@ResponseBody
	public Map deleteItem(HttpServletRequest request){
		
		Map<String, Object> map = new HashMap<String, Object>();
	
		if(this.itemsSer.checkForItemDelete(request) == 0){
			int result = itemsSer.deleteItemInfo(request); 
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
				map.put("navTabId", "ar0304");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_info_use",request));//该信息使用中,不能删除
		}
		return map;		
	}
	
	/**
	 * 显示项目明细列表(view ItemParameter)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewItemParameter")
	public ModelAndView viewItemParameter(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List itemList = this.itemsSer.getItemParamList(request);
		modelMap.put("itemList", itemList) ;
		modelMap.put("menuNo", request.getParameter("menuNo")) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2350")) ;
		return new ModelAndView("/ar/attendanceSettings/viewItemParameter",modelMap);
	}
	
	/**
	 * 根据项目明细取得参数列表(get ItemParameter List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewItemParameterList")
	public ModelAndView getItemParameterList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List itemParameterList = new ArrayList();
		itemParameterList = this.itemsSer.getItemParameterList(request) ;
		
//		for(int i=0;i<itemParameterList.size();i++){
//			LinkedHashMap data = (LinkedHashMap)itemParameterList.get(i);
//			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
//			paramMap.put("AR_PARAM_NO", data.get("AR_PARAM_NO").toString());
//			List dataTypeList = this.itemsSer.getDataTypeList(paramMap) ;
//			Map datetypeMap = new LinkedHashMap();
//			datetypeMap.put("dataTypeList", dataTypeList);
//			itemParameterList.add("datetypeMap");
//		}
		
		modelMap.put("itemParameterList", itemParameterList);
		modelMap.put("AR_ITEM_NO", request.getParameter("AR_ITEM_NO"));
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2350")) ;
		return new ModelAndView("/ar/attendanceSettings/viewItemParameterList",modelMap);
	}
	
	/**
	 * 进入添加项目明细参数(add ItemParameter View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addItemParameterView",method = RequestMethod.GET)
	public ModelAndView addItemParameterView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List applyList=this.itemsSer.getApplyList(request);
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		modelMap.put("defaultCpnyID", admin.getCpnyId()) ;
		
		modelMap.put("itemInfo", itemsSer.getItem(request));
		List groupList = itemsSer.getDynamicGroupList(request,modelMap);
		List cpnyList = companySer.getCompanyItemList(request);
		
		modelMap.put("applyList", applyList);
		modelMap.put("arGroup", groupList);
		modelMap.put("cpnyList", cpnyList);
		return new ModelAndView("/ar/attendanceSettings/addItemParameterView",modelMap);
	}
	
	/**
	 * 添加项目明细参数(add ItemParameter View)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addItemParameterInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addItemParameterInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(this.itemsSer.checkForItemParamUnique(request) == 0){
			int result = itemsSer.addItemParameterInfo(request); 
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
				map.put("rel", "jbsxBoxItemParam");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.info_exits",request));//该信息已经存在,不能重复添加
		}
		return map;		
	}
	
	/**
	 * 进入修改项目明细参数页面(update ItemParameter View)
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateItemParameterView",method = RequestMethod.GET)
	public ModelAndView updateItemParameterView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List applyList=this.itemsSer.getApplyList(request);
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		List groupList = itemsSer.getDynamicGroupList(request,modelMap);
		List cpnyList = companySer.getCompanyItemList(request);
		modelMap.put("applyList", applyList) ;
		modelMap.put("defaultCpnyID", admin.getCpnyId()) ;
		modelMap.put("arGroup", groupList);
		modelMap.put("cpnyList", cpnyList);
		Map map = (Map)itemsSer.getItemParameter(request);
			String dataType = StringUtil.checkNull(map.get("DATE_TYPE"));
			String[] dataTypeArray = dataType.split(",");
			for(int j = 0;j<dataTypeArray.length;j++){
				map.put("DATE_TYPE"+j, dataTypeArray[j]);
			}
		modelMap.put("itemParameter", map);
		return new ModelAndView("/ar/attendanceSettings/updateItemParameterView",modelMap);
	}
	
	/**
	 * 修改项目明细参数(update ItemParameter View)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateItemParameterInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateItemParameterInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(this.itemsSer.checkForItemParamUnique(request) == 0){
			int result = itemsSer.updateItemParameterInfo(request); 
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("rel", "jbsxBoxItemParam");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.info_conflict",request));//所修改信息与之前已存在信息冲突,要求不能重复！
		}
		
		return map;		
	}
	
	/**
	 * 删除项目明细参数(delete ItemParameter Info)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/deleteItemParameterInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map deleteItemParameterInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
//		if(this.itemsSer.checkForItemParamDelete(request) == 0){
//			int result = itemsSer.deleteItemParameterInfo(request) ; 
//			if(result == 1){
//				map.put("statusCode", "200");
//				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
//				map.put("rel", "jbsxBoxItemParam");
//			}else{
//				map.put("statusCode", "300");
//				map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
//			}
//		}else{
//			map.put("statusCode", "300");
//			map.put("message", TipMessage.getTipMessage("alert.message.delete_info_use",request));//该信息使用中,不能删除
//		}
		
		int result = itemsSer.deleteItemParameterInfo(request) ; 
		int num = this.itemsSer.checkForItemParamUnique(request);
		if(result == 1 && num ==0){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("rel", "jbsxBoxItemParam");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		
		return map;		
	}
	
	//根据公司获取动态组(get DynamicGroup By CpnyId)
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getDynamicGroupByCpnyId")
	@ResponseBody
	public List getDynamicGroupByCpnyId(HttpServletRequest request,ModelMap modelMap)throws Exception{
		
		List groupList = itemsSer.getDynamicGroupList(request,modelMap);
		
		return groupList;		
	}
	
	
	
	/*******************************************************************************/
	
	/**
	 * 根据项目明细取得参数列表(get ItemParameter List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewItemParamList")
	public ModelAndView viewItemParamList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List itemParamList = new ArrayList();
		itemParamList = this.itemsSer.getItemParamList_cpny(request) ;
		
//		for(int i=0;i<itemParameterList.size();i++){
//			LinkedHashMap data = (LinkedHashMap)itemParameterList.get(i);
//			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
//			paramMap.put("AR_PARAM_NO", data.get("AR_PARAM_NO").toString());
//			List dataTypeList = this.itemsSer.getDataTypeList(paramMap) ;
//			Map datetypeMap = new LinkedHashMap();
//			datetypeMap.put("dataTypeList", dataTypeList);
//			itemParameterList.add("datetypeMap");
//		}
		int itemParamCnt = itemsSer.getItemParamCnt(request);
		modelMap.put("itemParamList", itemParamList);
		modelMap.put("AR_ITEM_NO", request.getParameter("AR_ITEM_NO"));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemParamCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2350")) ;
		return new ModelAndView("/ar/attendanceSettings/viewItemParamList",modelMap);
	}
	
	
	/**
	 * 进入添加项目明细参数(add ItemParameter View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addItemParamView",method = RequestMethod.GET)
	public ModelAndView addItemParamView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List applyList=this.itemsSer.getApplyList(request);
		List itemList1 = this.itemsSer.getItemListSelect(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		modelMap.put("defaultCpnyID", admin.getCpnyId()) ;
		
		modelMap.put("itemInfo", itemsSer.getItem(request));
		List groupList = itemsSer.getDynamicGroupList(request,modelMap);
		List cpnyList = companySer.getCompanyItemList(request);
		modelMap.put("applyList", applyList);
		modelMap.put("itemList", itemList1);
		modelMap.put("arGroup", groupList);
		modelMap.put("cpnyList", cpnyList);
		return new ModelAndView("/ar/attendanceSettings/addItemParamView",modelMap);
	}
	
	/**
	 * 添加项目明细参数(add ItemParameter View)法人
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addItemParamInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addItemParamInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(this.itemsSer.checkForItemParamUnique(request) == 0){
			int result = itemsSer.addItemParameterInfo(request); 
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
				map.put("navTabId", "ar0313");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.info_exits",request));//该信息已经存在,不能重复添加
		}
		return map;		
	}
	
	
	/**
	 * 调整参数顺序(update SummaryItemInfo CalOrder)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */	
	@RequestMapping(value = "/updateParamItemOrder",method = RequestMethod.POST)
	@ResponseBody
	public Map updateParamItemOrder(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.itemsSer.updateParamItemInfoCalOrder(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ar.alert.message.viewsummaryitem.rerow",request));//重新排序成功
			map.put("navTabId", "ar0313");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("ar.alert.message.viewsummaryitem.rerowfail",request));//排序失败
		}
		return map;
	}
	
	
	/**
	 * 删除项目明细参数(delete ItemParameter Info)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/deleteItemParamInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map deleteItemParamInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
//		if(this.itemsSer.checkForItemParamDelete(request) == 0){
//			int result = itemsSer.deleteItemParameterInfo(request) ; 
//			if(result == 1){
//				map.put("statusCode", "200");
//				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
//				map.put("rel", "jbsxBoxItemParam");
//			}else{
//				map.put("statusCode", "300");
//				map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
//			}
//		}else{
//			map.put("statusCode", "300");
//			map.put("message", TipMessage.getTipMessage("alert.message.delete_info_use",request));//该信息使用中,不能删除
//		}
		
		int result = itemsSer.deleteItemParamInfo(request) ; 
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "ar0313");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		
		return map;		
	}
}
