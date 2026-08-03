package com.ait.sys.action;

import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.ait.sys.service.SysSer;
import com.ait.web.util.CodeUtil;
import com.ait.web.util.ObjectBindUtil;

@Controller
@RequestMapping(value = "/sys")
public class SysCtroller {
	Logger logger = Logger.getLogger(SysCtroller.class);
	@Autowired
	private SysSer sysSer;
	
	@Autowired
	CodeUtil codeUtil ;
	
	@RequestMapping(value = "/updateModel",method = RequestMethod.POST)
	@ResponseBody
	public void updateModel(HttpServletRequest request)throws Exception{
		logger.info("start...");
		sysSer.updateModel(request);		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getModel",method = RequestMethod.GET)
	@ResponseBody
	public Map getModel(HttpServletRequest request)throws Exception{
		List list = sysSer.getModel(request);
		Map model=new HashMap();
		model.put("modelList", list);
		return model;		
	}
	
	@RequestMapping(value = "/refreshCodeMap")
	@ResponseBody
	public String refreshCodeMap(HttpServletRequest request)throws Exception{
		
		logger.debug("远程刷新codeMap.............." + new Date());
		
		codeUtil.initCode() ;
		
		return "OK" ;
	}
	/**
	 * 跳转到导出excel加密页面
	* @Description: TODO
	* @author penghaixia
	* @date 2014.7.23
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/encryptExcel")
	@ResponseBody
	public ModelAndView encryptExcel(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("exportFunName", paramMap.get("exportFunName"));
		modelMap.put("navTabId", paramMap.get("navTabId"));
		modelMap.put("formId", paramMap.get("formId"));		
		return new ModelAndView("/sys/sysSettings/encryptExcel",modelMap);		
	}
	/**
	 * 导出excel加密
	* @Description: TODO
	* @author penghaixia
	* @date 2014.7.23
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/confirmPassward")
	@ResponseBody
	public Map confirmPassward(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		Map map = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return map;		
	}
}
