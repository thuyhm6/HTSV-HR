package com.ait.ar.action.attendanceSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.CycleSer;
import com.ait.ar.service.DynamicGroupSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: DynamicGroupCtroller.java
 * @Description:
 * @Create date: 2012-1-17 下午02:57:24
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class DynamicGroupCtroller {
	Logger logger = Logger.getLogger(DynamicGroupCtroller.class);
	
	@Autowired
	private DynamicGroupSer dynamicGroupSer ;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private CycleSer cycleSer;
	
	/**
	 * 查看动态组页面(view Dynamic Group)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewDynamicGroup",method = RequestMethod.GET)
	public ModelAndView viewDynamicGroup(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List dynamicGroupList = this.dynamicGroupSer.getDynamicGroup1List(request) ;
		
		modelMap.put("username", admin.getUsername()) ;
		modelMap.put("dynamicGroupList", dynamicGroupList) ;
		modelMap.put("menuNo", request.getParameter("menuNo") != null ? request.getParameter("menuNo") : "2358") ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
					toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2358")) ;
		   
		return new ModelAndView("/ar/attendanceSettings/viewDynamicGroup",modelMap);
	}
	
	/**
	 * 查看动态组人员信息页面(view DynamicGroup List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewDynamicGroupInfoList")
	public ModelAndView viewDynamicGroupInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List dynamicGroupInfoList = this.dynamicGroupSer.getDynamicGroupInfoList(request);
		int dynamicGroupInfoCnt = this.dynamicGroupSer.getDynamicGroupInfoCnt(request);
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList); 
		modelMap.put("dynamicGroupInfoList", dynamicGroupInfoList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, dynamicGroupInfoCnt) ;
		modelMap.put("GROUP_NO", request.getParameter("NO")) ;
		modelMap.put("reqest_NO", StringUtil.checkNull(request.getParameter("NO")));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request,"2358")) ;
		
		return new ModelAndView("/ar/attendanceSettings/viewDynamicGroupInfoList",modelMap);
	}
	
	/**
	 * 进入动态组添加页面(add DynamicGroup View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDynamicGroupView",method = RequestMethod.GET)
	public ModelAndView addDynamicGroupView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
        return new ModelAndView("/ar/attendanceSettings/addDynamicGroupView",modelMap);
	}
	
	/**
	 * 添加动态组(add DynamicGroup)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDynamicGroup",method = RequestMethod.POST)
	@ResponseBody
	public Map addDynamicGroup(HttpServletRequest request) throws Exception{
        
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.dynamicGroupSer.addDynamicGroupInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
			map.put("navTabId", "ar0601");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;
	}
	
	/**
	 * 删除动态组(delete DynamicGroup)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteDynamicGroup",method = RequestMethod.POST)
	@ResponseBody
	public Map deleteDynamicGroup(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.dynamicGroupSer.deleteDynamicGroupInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("navTabId", "ar0601");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		
		return map;
	}
	
	/**
	 * 修改动态组(update DynamicGroup View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDynamicGroupView",method = RequestMethod.GET)
	public ModelAndView updateDynamicGroupView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("dynamicGroup", dynamicGroupSer.getDynamicGroup(request)) ;
        return new ModelAndView("/ar/attendanceSettings/updateDynamicGroupView",modelMap);
	}
	
	/**
	 * 更新动态组(update DynamicGroup)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDynamicGroup",method = RequestMethod.POST)
	@ResponseBody
	public Map updateDynamicGroup(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.dynamicGroupSer.updateDynamicGroupInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "ar0601");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		
		return map;
	}
	
	/**
	 * 进入动态组信息添加页面(add DynamicGroupInfo View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewDynamicGroupEmpList")
	public ModelAndView viewDynamicGroupEmpList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List dynamicGroupEmpList = this.dynamicGroupSer.getDynamicGroupEmpList(request);
		int dynamicGroupEmpCnt = this.dynamicGroupSer.getDynamicGroupEmpCnt(request);
		modelMap.put("dynamicGroupEmpList", dynamicGroupEmpList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, dynamicGroupEmpCnt) ;
		modelMap.put("reqest_NO", StringUtil.checkNull(request.getParameter("NO")));
		
        return new ModelAndView("/ar/attendanceSettings/viewDynamicGroupEmpList",modelMap);
	}
	
	/**
	 * 添加动态组人员信息(add DynamicGroup Person)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDynamicGroupPerson",method = RequestMethod.POST)
	@ResponseBody
	public Map addDynamicGroupPerson(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.dynamicGroupSer.addDynamicGroupPerson(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
			map.put("navTabId", "ar0203");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		
		return map;
	}
	
	/**
	 * 删除动态组人员(delete DynamicGroup Person)
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteDynamicGroupPerson",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDynamicGroupPerson(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.dynamicGroupSer.deleteDynamicGroupPerson(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "ar0203");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	
		return map;
	}
}
