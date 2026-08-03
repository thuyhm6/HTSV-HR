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
import com.ait.ar.service.PersonDynGroupSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PersonDynGroupCtroller.java
 * @Description:
 * @Create date: 2012-1-17 下午02:57:24
 * @Create by: 
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class PersonDynGroupCtroller {
	Logger logger = Logger.getLogger(PersonDynGroupCtroller.class);
	
	@Autowired
	private PersonDynGroupSer PersonDynGroupSer ;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private CycleSer cycleSer;
	
	/**
	 * 查看动态组页面(view PersonDyn Group)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonDynGroup",method = RequestMethod.GET)
	public ModelAndView viewPersonDynGroup(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List PersonDynGroupList = this.PersonDynGroupSer.getPersonDynGroup1List(request) ;
		
		modelMap.put("PersonDynGroupList", PersonDynGroupList) ;
		modelMap.put("menuNo", request.getParameter("menuNo") != null ? request.getParameter("menuNo") : "2358") ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
					toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2358")) ;
		   
		return new ModelAndView("/ar/attendanceSettings/viewPersonDynGroup",modelMap);
	}
	
	
	/**
	 * 查看动态组页面(view PersonDyn Group)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPwesonDynGroup",method = RequestMethod.GET)
	public ModelAndView viewPwesonDynGroup(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List personDynGroupList = this.PersonDynGroupSer.getPersonDynGroupList(request) ;
		
		modelMap.put("personDynGroupList", personDynGroupList) ;
		modelMap.put("menuNo", request.getParameter("menuNo") != null ? request.getParameter("menuNo") : "300017") ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
					toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "300017")) ;
		   
		return new ModelAndView("/ar/attendanceSettings/viewPwesonDynGroup",modelMap);
	}
	
	/**
	 * 进入动态组添加页面(add PersonDynGroup View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPersonDynGroupView",method = RequestMethod.GET)
	public ModelAndView addPersonDynGroupView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
        return new ModelAndView("/ar/attendanceSettings/addPersonDynGroupView",modelMap);
	}
	
	/**
	 * 添加动态组(add PersonDynGroup)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPersonDynGroup",method = RequestMethod.POST)
	@ResponseBody
	public Map addPersonDynGroup(HttpServletRequest request) throws Exception{
        
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.PersonDynGroupSer.addPersonDynGroupInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
			map.put("navTabId", "ar0010ta");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return map;
	}
	
	/**
	 * 删除动态组(delete PersonDynGroup)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePersonDynGroup",method = RequestMethod.POST)
	@ResponseBody
	public Map deletePersonDynGroup(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.PersonDynGroupSer.deletePersonDynGroupInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("navTabId", "ar0010ta");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		
		return map;
	}
	
	/**
	 * 修改动态组(update PersonDynGroup View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePersonDynGroupView",method = RequestMethod.GET)
	public ModelAndView updatePersonDynGroupView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("PersonDynGroup", PersonDynGroupSer.getPersonDynGroup(request)) ;
        return new ModelAndView("/ar/attendanceSettings/updatePersonDynGroupView",modelMap);
	}
	
	/**
	 * 更新动态组(update PersonDynGroup)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePersonDynGroup",method = RequestMethod.POST)
	@ResponseBody
	public Map updatePersonDynGroup(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.PersonDynGroupSer.updatePersonDynGroupInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "ar0010ta");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		
		return map;
	}
	
	
	/**
	 * 查看动态组人员信息页面(view PersonDynGroup List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonDynGroupInfoList")
	public ModelAndView viewPersonDynGroupInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List PersonDynGroupInfoList = this.PersonDynGroupSer.getPersonDynGroupInfoList(request);
		int PersonDynGroupInfoCnt = this.PersonDynGroupSer.getPersonDynGroupInfoCnt(request);
		modelMap.put("PersonDynGroupInfoList", PersonDynGroupInfoList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, PersonDynGroupInfoCnt) ;
		modelMap.put("GROUP_NO", request.getParameter("NO")) ;
		modelMap.put("reqest_NO", StringUtil.checkNull(request.getParameter("NO")));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request,"2358")) ;
		
		return new ModelAndView("/ar/attendanceSettings/viewPersonDynGroupInfoList",modelMap);
	}
	
	/**
	 * 进入动态组信息添加页面(add PersonDynGroupInfo View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonDynGroupEmpList")
	public ModelAndView viewPersonDynGroupEmpList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List PersonDynGroupEmpList = this.PersonDynGroupSer.getPersonDynGroupEmpList(request);
		int PersonDynGroupEmpCnt = this.PersonDynGroupSer.getPersonDynGroupEmpCnt(request);
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList); 
		modelMap.put("PersonDynGroupEmpList", PersonDynGroupEmpList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, PersonDynGroupEmpCnt) ;
		modelMap.put("reqest_NO", StringUtil.checkNull(request.getParameter("NO")));
		
        return new ModelAndView("/ar/attendanceSettings/viewPersonDynGroupEmpList",modelMap);
	}
	
	/**
	 * 添加动态组人员信息(add PersonDynGroup Person)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPersonDynGroupPerson",method = RequestMethod.POST)
	@ResponseBody
	public Map addPersonDynGroupPerson(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.PersonDynGroupSer.addPersonDynGroupPerson(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
			map.put("navTabId", "ar0010ta");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		
		return map;
	}
	
	/**
	 * 删除动态组人员(delete PersonDynGroup Person)
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePersonDynGroupPerson",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePersonDynGroupPerson(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.PersonDynGroupSer.deletePersonDynGroupPerson(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "ar0010ta");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	
		return map;
	}
}
