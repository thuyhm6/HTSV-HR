package com.ait.sys.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import com.ait.sys.service.LoginInfoSer;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC  (c)
 * Company:     LDCC 
 * @fileName LoginInfoCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 pm 04:37:54
 * @version 5.0
 */
@Controller
@RequestMapping(value = "/sys/rightsManagement")
public class LoginInfoCtroller {
	Logger logger = Logger.getLogger(LoginInfoCtroller.class);
	
	@Autowired
	private LoginInfoSer loginInfoSer ;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLoginInfo")
	public ModelAndView viewLoginInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List loginInfoList = this.loginInfoSer.getLoginInfoList(request) ;
		int loginInfoCnt = this.loginInfoSer.getLoginInfoCnt(request) ;
		modelMap.put("loginInfoList", loginInfoList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, loginInfoCnt) ;
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/sys/rightsManagement/viewLoginInfo",modelMap);
	}
	
}
