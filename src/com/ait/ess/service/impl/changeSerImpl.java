package com.ait.ess.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ait.ess.dao.changeDao;
import com.ait.ess.service.changeSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.LoginDao;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;


@Service
public class changeSerImpl implements changeSer {

	Logger logger = Logger.getLogger(changeSerImpl.class);

	@Autowired
	private changeDao changeDao;

	@Autowired
	private LoginDao loginDao;


	@Override
	public int changePassword(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
	try{
		
		paramMap.put("PERSON_ID", admin.getPersonId());
		changeDao.changePassword(paramMap);
		return 1;
	}catch (Exception e) {
		// TODO: handle exception
		return 0;
	}
		
	}
	/**
	 * 员工基础信息 (Staff foundation information)
	 * @param request
	 * @return Object
	 */
	@Override
	public Object getPersonalInfoByPid(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		if(request.getParameter("PERSON_ID")!=null){
			paramMap.put("PERSON_ID_ID",request.getParameter("PERSON_ID"));
		}
		
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID_ID",admin.getPersonId());
		}
		
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return changeDao.getPersonalInfoByPid(paramMap);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String findUser(HttpServletRequest request,HttpServletResponse response){
		//根据浏览器取语言信息 默认中文
//		String language = request.getLocale().getLanguage();
		String language = "vi";
		//根据cookie信息中的信息判断语言信息 没有cookie默认为中文
		Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
		for(Cookie cookie : cookies){
		    if(cookie.getName().toString().equals("language_cookie")){// get the cookie name
		    	language=cookie.getValue().toString(); // get the cookie value
		    }
		}
		
		String cpnyId = StringUtil.checkNull(request.getParameter("COMPANY_IDS")) ;
		
		Map hm = new LinkedHashMap();
		hm.put("username",request.getParameter("usernames"));
		hm.put("password",request.getParameter("passwords"));
		//单点登录判断，如果是单点登录查询出真实密码
		String txt_userId = request.getParameter("txt_userId");
		if(txt_userId != null && !"null".equals(txt_userId)&& !"".equals(txt_userId)){
			String password = loginDao.findPasswordByUser(hm).toString() ;
			hm.put("password",password);
		}
		if(cpnyId.equals("")){
			cpnyId = loginDao.findCpnyIdByUser(hm).toString() ;
		}
		
		hm.put("cpny_id",cpnyId);
		
		
		//查询当前语言是否活跃 默认中文
//		Map acMap = new LinkedHashMap();
//		acMap.put("language", language);
//		language = loginDao.activityLanguage(acMap);
		
		hm.put("language",language);
		
		AdminBean user = (AdminBean) loginDao.findUserSpecial(hm);
		
		ModelMap modelMap=new ModelMap();

		if (user==null){
			modelMap.put("msg",Messages.getMessage(request, "loginFail"));				
			return "loginFail";
		}else{
			Map uhm = new LinkedHashMap();
			
			if(user.getSpecialParam()!=null&&!user.getSpecialParam().equals("administrator")&&user.getPersonId()!=null){
				user=(AdminBean) loginDao.findUser(hm);
				if (user==null){
					modelMap.put("msg",Messages.getMessage(request, "loginFail"));				
					return "loginFail";
				}
			}
			uhm.put("USERNO",user.getUserNo());
			List limitsMapList=loginDao.getLimitsMap(uhm);
			Map limitMap=new LinkedHashMap();
			for(int i=0;i<limitsMapList.size();i++){
				Map mapt = (LinkedHashMap)limitsMapList.get(i);
				limitMap.put(mapt.get("MENU_NO"), mapt) ;
			}
			user.setLimitsMap(limitMap);
			
			LinkedHashMap info = new LinkedHashMap();
			String IP = this.getRemortIP(request);
			info.put("PERSON_ID", user.getPersonId()==null?"":(user.getPersonId()));
			info.put("ip", IP);
//			info.put("mac", UserListen.getMACAddress(IP));
			info.put("mac", "");
			loginDao.addLoginInfo(info);
			
			user.setLanguage(language);
			user.setAdminIP(IP);
			request.getSession().setAttribute("LoginUser", user);
			return "1";	
		}
		
	}
	
	
	//取得实际IP
	public String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
	
}