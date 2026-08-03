package com.ait.ar.action.attendanceSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.Interface.ParentCtroller;
import com.ait.ar.service.ItemsSer;
import com.ait.ar.service.ShiftSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ShiftCtroller.java
 * @Description:
 * @Create date: 2012-1-9 下午02:48:55
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class ShiftCtroller extends ParentCtroller{
	Logger logger = Logger.getLogger(ShiftCtroller.class);
	
	@Autowired
	private ShiftSer shiftSer ;
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	/**
	 * 显示班次列表(view Shift List)
	 * @param request
	 * @param response
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewShift")
	public ModelAndView viewShiftList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List shiftList = this.shiftSer.getShiftList1(request);
		modelMap.addAllAttributes(ObjectBindUtil.getRequestParamData(request,"seach_"));
		modelMap.put("shiftList", shiftList) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2351")) ;
		
		return new ModelAndView("/ar/attendanceSettings/viewShift",modelMap);
	}
	
	/**
	 * 添加班次页面(add Shift View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addShiftView")
	public ModelAndView addShiftView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List itemList = this.shiftSer.getItemList(request);
		modelMap.put("itemList", itemList);
		
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/ar/attendanceSettings/addShiftView",modelMap);
	}
	/**
	 * 保存班次信息(add Shift Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addShiftInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addShiftInfo(HttpServletRequest request)throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		
		int errorNum = this.shiftSer.checkShiftInfo(request) ;
		if(errorNum == 0){
			int result = this.shiftSer.addShiftInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
				map.put("navTabId", "ar0303");
				map.put("callbackType", "closeCurrent");
				
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
	 * 修改跳转
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateShiftView")
	public ModelAndView updateShiftView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("shiftInfo", shiftSer.getShift(request)) ;
		modelMap.put("shiftParameterList", shiftSer.getShiftParameterList(request)) ;
//		modelMap.put("itemList", this.shiftSer.getItemList(request)) ;
		
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/ar/attendanceSettings/updateShiftView",modelMap);
	}
	
	/**
	 * 修改保存
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateShiftInfo")
	@ResponseBody
	public Map<String, Object> updateShiftInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
//		int result = shiftSer.updateShiftInfo(request) ;
		int errorNum = this.shiftSer.checkShiftInfo(request) ;
		if(errorNum == 0){
			int result = shiftSer.updateShiftInfoByNO(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "ar0303");
				map.put("callbackType", "closeCurrent");
				
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
	 * 删除班次(delete Shift)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShift")
	@ResponseBody
	public String deleteShift(HttpServletRequest request)throws Exception{
		
		shiftSer.deleteShiftInfo(request) ;
		
		return "Y";		
	}
	
	/**
	 * 删除班次(delete Shift)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShiftInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteShiftInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = shiftSer.deleteShiftInfo(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "ar0303");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	
		
		return map;
	}
	
	/**
	 * 查询班次参数列表(view ShiftParameter)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewShiftParameter",method = RequestMethod.GET)
	public ModelAndView viewShiftParameter(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		initMenu(request, modelMap);
		List shiftParams = this.shiftSer.getShiftParameterList(request);
		modelMap.put("shiftParams", shiftParams);
		return new ModelAndView("/ar/attendanceSettings/viewShiftParameter",modelMap);
	}
}
