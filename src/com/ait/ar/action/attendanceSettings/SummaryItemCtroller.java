package com.ait.ar.action.attendanceSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.SummaryItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: SummaryItemCtroller.java
 * @Description:
 * @Create date: 2012-1-13 上午09:49:01
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class SummaryItemCtroller {
	Logger logger = Logger.getLogger(SummaryItemCtroller.class);
	
	@Autowired
	private SummaryItemSer summaryItemSer ;
	@Autowired
	private CompanySer companySer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	/**
	 * 进入汇总项目库页面(view SummaryItem List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewSummaryItem")
	public ModelAndView viewSummaryItemList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List itemList = this.summaryItemSer.getSummaryItemList(request);
		int totalCount = this.summaryItemSer.getSummaryItemCnt(request);
		
		modelMap.put("itemList", itemList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, totalCount) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2353")) ;
		
		return new ModelAndView("/ar/attendanceSettings/viewSummaryItem",modelMap);
	}
	
	/**
	 * 进入汇总项目添加页面(add SummaryItem View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSummaryItemView",method = RequestMethod.GET)
	public ModelAndView addSummaryItemView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		return new ModelAndView("/ar/attendanceSettings/addSummaryItemView",modelMap);
	}
	
	/**
	 * 添加汇总项目(add SummaryItem Info)
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSummaryItemInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addSummaryItemInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		int errorNum = this.summaryItemSer.checkSummaryItemInfo(request) ;
		if(errorNum == 0){
			int result = this.summaryItemSer.addSummaryItemInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
				map.put("navTabId", "ar0302");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		}
		else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.ID_conflict",request));//所添加的项目ID已经存在,不能重复添加
		}
		return map;
	}
	
	/**
	 * 进入汇总项目页面(update SummaryItem View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSummaryItemView",method = RequestMethod.GET)
	public ModelAndView updateSummaryItemView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("summaryItemInfo", summaryItemSer.getSummaryItem(request)) ;
		
		return new ModelAndView("/ar/attendanceSettings/updateSummaryItemView",modelMap);
	}
	
	/**
	 * 修改汇总项目(update SummaryItem Info)
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSummaryItemInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateSummaryItemInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int errorNum = this.summaryItemSer.checkSummaryItemInfo(request);
		if(errorNum == 0){
			int result = summaryItemSer.updateSummaryItemInfo(request);
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "ar0302");
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
	 * 删除汇总项目(delete SummaryItem Info)
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSummaryItemInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map deleteSummaryItemInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(this.summaryItemSer.checkForItemDelete(request) == 0){

			int result = this.summaryItemSer.deleteSummaryItemInfo(request) ; 
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
				map.put("navTabId", "ar0302");
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
	 * 进入汇总项目参数页面(view SummaryParamItem List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewSummaryParamItem")
	public ModelAndView viewSummaryParamItem(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List itemparamList = this.summaryItemSer.getSummaryParamItemList1(request);
//		int totalCount = this.summaryItemSer.getSummaryParamItemCnt(request);
		
		//List cpnyList = this.companySer.getCompanyItemList(request);
//		List cpnyList = this.companySer.getCompanyItemAllList(request);
		
//		modelMap.put("cpnyList", cpnyList) ;
		modelMap.put("ITEM_NAME", request.getParameter("ITEM_NAME")) ;
		modelMap.put("itemParamList", itemparamList) ;
//		modelMap.put(UiUtil.TOTAL_COUNT_NAME, totalCount) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2570")) ;
		
		return new ModelAndView("/ar/attendanceSettings/viewSummaryParamItem",modelMap);
	}
	
	
	@RequestMapping(value = "/viewShowSummaryParamItem",method = RequestMethod.POST)
	@ResponseBody
	public Map viewShowSummaryParamItem(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		 
			int result = this.summaryItemSer.addShowSummaryParamItemList(request);
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message","修改成功");
				map.put("navTabId", "ar0309");
			}else{
				map.put("statusCode", "300");
				map.put("message", "修改失败");
		    }
		 
		return map;
	}
	
	
	
	
	/**
	 * 进入汇总项目参数添加页面(add SummaryParamItem View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSummaryParamItemView",method = RequestMethod.GET)
	public ModelAndView addSummaryParamItemView(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List cpnyList = this.companySer.getCompanyItemList(request);
		List itemList = this.summaryItemSer.getSummaryItemByCpnyList(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		modelMap.put("defaultCpnyID", admin.getCpnyId()) ;
		modelMap.put("cpnyList", cpnyList) ;
		modelMap.put("itemList", itemList) ;
		return new ModelAndView("/ar/attendanceSettings/addSummaryParamItemView",modelMap);
	}
	

	
	
	
	/**
	 * 添加汇总项目参数(add SummaryParamItem Info)
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSummaryParamItemInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addSummaryParamItemInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(this.summaryItemSer.checkForItemParamUnique(request) == 0){
			int result = this.summaryItemSer.addSummaryParamItemInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
				map.put("navTabId", "ar0309");
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
	 * 进入汇总项目参数修改页面(update SummaryParamItem View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSummaryParamItemView",method = RequestMethod.GET)
	public ModelAndView updateSummaryParamItemView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List cpnyList = this.companySer.getCompanyItemList(request);
		List itemList = this.summaryItemSer.getSummaryItemList(request);
		
		modelMap.put("cpnyList", cpnyList) ;
		modelMap.put("itemList", itemList) ;
		modelMap.put("summaryParamItemInfo", summaryItemSer.getSummaryParamItem(request)) ;
		
		return new ModelAndView("/ar/attendanceSettings/updateSummaryParamItemView",modelMap);
	}
	
	/**
	 * 修改汇总项目参数(update SummaryParamItem Info)
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSummaryParamItemInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateSummaryParamItemInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(this.summaryItemSer.checkForItemParamUnique(request) == 0){
			int result = summaryItemSer.updateSummaryParamItemInfo(request);
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "ar0309");
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
	 * 删除汇总项目参数(delete SummaryParamItem Info)
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSummaryParamItemInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map deleteSummaryParamItemInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(this.summaryItemSer.checkForItemParamDelete(request) == 0){
			int result = this.summaryItemSer.deleteSummaryParamItemInfo(request) ; 
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
				map.put("navTabId", "ar0309");
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
	 * 调整参数顺序(update SummaryItemInfo CalOrder)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */	
	@RequestMapping(value = "/updateSummaryParamItemOrder",method = RequestMethod.POST)
	@ResponseBody
	public Map updateSummaryItemInfoCalOrder(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.summaryItemSer.updateSummaryParamItemInfoCalOrder(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ar.alert.message.viewsummaryitem.rerow",request));//重新排序成功
			map.put("navTabId", "ar0309");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("ar.alert.message.viewsummaryitem.rerowfail",request));//排序失败
		}
		return map;
	}
}
