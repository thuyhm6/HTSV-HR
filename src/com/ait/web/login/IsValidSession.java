package com.ait.web.login;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ait.sys.bean.AdminBean;

@Controller
@RequestMapping("/login/validateSession")
public class IsValidSession {

	@RequestMapping(value="/checkSession",method=RequestMethod.POST)
	@ResponseBody
	public void checkSession(HttpServletRequest request,HttpServletResponse response)throws Exception{
		AdminBean admin=(AdminBean) request.getSession().getAttribute("LoginUser");
		if(admin == null){
			response.setContentType("text/html");
			response.getWriter().println("Session is invalidated");
		}else{
			response.setContentType("text/html");
			response.getWriter().write("0");
		}
	}
}

