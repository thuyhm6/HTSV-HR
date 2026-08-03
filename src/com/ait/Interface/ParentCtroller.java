package com.ait.Interface;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.ObjectBindUtil;

public abstract class ParentCtroller {
	protected Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	protected ToolMenuSer toolMenuSer;
	@SuppressWarnings("unchecked")
	protected ModelMap initMenu(HttpServletRequest request, ModelMap modelMap){
		modelMap.put("toolbarInfo", toolMenuSer.getToolMenu(request));
		modelMap.addAllAttributes(ObjectBindUtil.getRequestParamData(request,"seach_"));
		return modelMap;
	}
}
