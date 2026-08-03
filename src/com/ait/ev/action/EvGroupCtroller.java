package com.ait.ev.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ev.service.EvGroupService;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;

@Controller
@RequestMapping(value="/ev/basicsetting")
public class EvGroupCtroller {

	@Autowired
	private EvGroupService service;
	@Autowired 
	private ToolMenuSer toolMenuSer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewGroupInfo")
	public ModelAndView viewGroupInfo(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		List objList = service.getEvGroupInfo(request);
		modelMap.put("objList", objList);
		modelMap.put("toolbarInfo",request.getParameter("menuNo") != null ? 
						toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124982"));
		return new ModelAndView("/ev/basicsetting/viewGroupInfo",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/addGroupInfoView")
	public ModelAndView addGroupInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		
		return new ModelAndView("/ev/basicsetting/addGroupInfoView",modelMap);
	}
	
	@RequestMapping(value = "/addGroupInfo")
	@ResponseBody
	public Map addGroupInfo(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = service.insertEvGroupInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));// 添加成功
			map.put("navTabId", "ev0101");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));// 保存失败
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateGroupInfoView")
	public ModelAndView updateGroupInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map item = service.getEvGroupInfoById(request);
		modelMap.put("item", item);
		return new ModelAndView("/ev/basicsetting/updateGroupInfoView", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateGroupInfo")
	@ResponseBody
	public Map updateGroupInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result =this.service.updateEvGroupInfo(request);
		if(result==1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));// 修改成功
			map.put("navTabId", "ev0101");
			
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));// 修改失败
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteGroupInfo")
	@ResponseBody
	public Map<String, Object> deleteGroupInfo(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.service.delEvGroupInfo(request);
		if(result==1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "ev0101");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getEvGroupTree")
	@ResponseBody
	public List getEvGroupTree(HttpServletRequest request) throws Exception {
		List info = service.getEvGroupTree(request);
		return info;
	}
}
