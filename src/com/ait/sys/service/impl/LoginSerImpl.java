
package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.NumberUtils;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.LoginDao;
import com.ait.sys.service.LoginSer;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UserListen;

@Service
public class LoginSerImpl implements LoginSer {
	
	Logger logger = Logger.getLogger(LoginSerImpl.class);
	
	@Autowired
	private LoginDao loginDao;

	@SuppressWarnings("unchecked")
	public String findUser(HttpServletRequest request,HttpServletResponse response){
		//根据浏览器取语言信息 默认中文
		//String language = request.getLocale().getLanguage();
		String language = request.getParameter("language");
		//String language = "vi";
		//根据cookie信息中的信息判断语言信息 没有cookie默认为中文
		/*Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
		for(Cookie cookie : cookies){
		    if(cookie.getName().toString().equals("language_cookie")){// get the cookie name
		    	language=cookie.getValue().toString(); // get the cookie value
		    }
		}*/
		
		String cpnyId = StringUtil.checkNull(request.getParameter("COMPANY_ID")) ;
		String loginType = StringUtil.checkNull(request.getParameter("loginType")) ;
		String reqSysType = StringUtil.checkNull(request.getParameter("reqSysType")) ;
		String reqCpny = StringUtil.checkNull(request.getParameter("reqCpny")) ;
		String sysType = StringUtil.checkNull(request.getParameter("sysType")) ;
		String reqLanguage = StringUtil.checkNull(request.getParameter("reqLanguage")) ;
		if(!reqLanguage.equals("")){
			language = reqLanguage;
		}
//		String checkHubByIP = StringUtil.checkNull(request.getParameter("checkHubByIP")) ;
		
		ModelMap modelMap=new ModelMap();

//		String checkIp = StringUtil.checkNull(this.getRemortIP(request));
//		//没有权限的IP地址无法登陆hub系统
//		if(checkHubByIP.equals("Y")){
//			if((cpnyId.equals("TSTO")||reqCpny.equals("TSTO"))&&(sysType.equals("Hub")||reqSysType.equals("Hub"))){
//				if(!checkIp.equals("")){
//					Map hm = new LinkedHashMap();
//					hm.put("checkIp",checkIp);
//					hm.put("CPNYID",reqCpny.equals("")?cpnyId:reqCpny);
//					if(loginDao.getIsCanInHubByIP(hm)==0){
//						modelMap.put("msg","没有hub权限 无法登陆!");		
//						return "loginFail";
//					}
//					
//				}else{
//					modelMap.put("msg","没有IP地址 无法登陆!");	
//					return "loginFail";
//				}
//			}
//		}
		
		Map hm = new LinkedHashMap();
		hm.put("username",request.getParameter("username"));
		hm.put("password",request.getParameter("password"));
		
//		boolean isCanIn = true ;
		//没有hub权限的员工无法登陆hub系统
		if(sysType.equals("Hub")||reqSysType.equals("Hub")){
			if(loginDao.getIsCanInHub(hm)==0){
//				isCanIn = false ;
				modelMap.put("msg",Messages.getMessage(request, "loginLimited"));//没有hub权限 无法登陆!			
				return "Cant login because authorization is not granted!";
			}
		}
		
		AdminBean user = null;
		hm.put("language",language);
		
		//如果是单点登录
		if(loginType.equals("sso")){
			hm.put("cpny_id",reqCpny);
			String password = loginDao.findPasswordByUser(hm).toString() ;
			hm.put("password",password);
		}else{
			hm.put("cpny_id",cpnyId);
		}
		user = (AdminBean) loginDao.findUserSpecial(hm);
//			String txt_userId = request.getParameter("txt_userId");
//			if(txt_userId != null && !"null".equals(txt_userId)&& !"".equals(txt_userId)){
//				String password = loginDao.findPasswordByUser(hm).toString() ;
//				hm.put("password",password);
//			}
//			if(cpnyId.equals("")){
//				cpnyId = loginDao.findCpnyIdByUser(hm).toString() ;
//			}
			
			
			
			//查询当前语言是否活跃 默认中文
	//		Map acMap = new LinkedHashMap();
	//		acMap.put("language", language);
	//		language = loginDao.activityLanguage(acMap);

			if (user==null){
				modelMap.put("msg",Messages.getMessage(request, "loginFail"));				
				return "loginFail";
			}else{
				Map uhm = new LinkedHashMap();
				
				if(user.getSpecialParam()!=null&&!user.getSpecialParam().equals("administrator")&&user.getPersonId()!=null){
					user=(AdminBean) loginDao.findUser(hm);
					if (user==null){
						modelMap.put("msg",Messages.getMessage(request, "loginFail"));				
						return "This employee ID does not exist";
					}
				}
				uhm.put("USERNO",user.getUserNo());
				List limitsMapList=loginDao.getLimitsMap(uhm);
				if(limitsMapList.size() == 0){
					modelMap.put("msg",Messages.getMessage(request, "loginLimited"));//没有hub权限 无法登陆!			
					return "Cant login because authorization is not granted, Please contact HR team!";
				}
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
				request.getSession().setAttribute("RESUME_SEQ_SESSION", "");
				return "1";	
			}
//		}
	}

	
	

	@SuppressWarnings("unchecked")
	public String findUserChage(HttpServletRequest request,HttpServletResponse response){
		//根据浏览器取语言信息 默认中文
		//String language = request.getLocale().getLanguage();
		String language = "vi";
		//根据cookie信息中的信息判断语言信息 没有cookie默认为中文
		Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
		for(Cookie cookie : cookies){
		    if(cookie.getName().toString().equals("language_cookie")){// get the cookie name
		    	language=cookie.getValue().toString(); // get the cookie value
		    }
		}
		
		
		Map hm = new LinkedHashMap();
		hm.put("username",request.getAttribute("username"));
		hm.put("password",request.getAttribute("password"));
		hm.put("cpny_id",request.getAttribute("COMPANY_ID"));
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
	
	/**
	 * 取得登陆者所有的权限组(getRoleGroupListByPersonId)
	 * @param request
	 * @param response
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getRoleGroupListByPersonId(HttpServletRequest request,
			HttpServletResponse response) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("personId", admin.getPersonId()) ;
		paramMap.put("language",admin.getLanguage());
		List menuList = loginDao.getRoleGroupListByPersonId(paramMap) ;
		 
		return menuList ;
	}
	
	/**
	 * 取左侧菜单(get Left Menu)
	 * @param request
	 * @param response
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getLeftMenu(HttpServletRequest request,
			HttpServletResponse response) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
//		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("USERNO", admin.getUserNo()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		
		paramMap.put("language",admin.getLanguage());
		
		List menuList = new ArrayList() ;
		List menuTempList = loginDao.getLeftMenu(paramMap) ;
		
		LinkedHashMap topMenuMap = null ;
		LinkedHashMap oneLevelMenuMap = null ;
		LinkedHashMap twoLevelMenuMap = null ;
		
		
		
		for(int i = 0 ; i < menuTempList.size() ; ++ i ){
			LinkedHashMap menuMap = (LinkedHashMap)menuTempList.get(i) ;
			int depth = NumberUtils.parseNumber(ObjectUtils.toString(menuMap.get("DEPTH")), Integer.class) ;
			
			if(depth == 0){
				topMenuMap = menuMap ;
				menuList.add(topMenuMap) ;
			}
			
			if(depth == 1){
				oneLevelMenuMap = menuMap ;
				
				List<LinkedHashMap> childMenuList = null ;
				if(topMenuMap != null){
					if (topMenuMap.get("childMenuList") != null){
						childMenuList = (List)topMenuMap.get("childMenuList") ;
					}
					else{
						childMenuList = new ArrayList() ;
						
						topMenuMap.put("childMenuList" , childMenuList) ;
					}
					
					childMenuList.add(oneLevelMenuMap) ;
					
				}
			}
			
			if(depth == 2){
				twoLevelMenuMap = menuMap ;
				List<LinkedHashMap> childMenuList = null ;
				if(oneLevelMenuMap != null){
					if (oneLevelMenuMap.get("childMenuList") != null){
						childMenuList = (List)oneLevelMenuMap.get("childMenuList") ;
					}
					else{
						childMenuList = new ArrayList() ;
						
						oneLevelMenuMap.put("childMenuList" , childMenuList) ;
					}
					
					childMenuList.add(twoLevelMenuMap) ;
				}
			}
			if(depth == 3){
				List<LinkedHashMap> childMenuList = null ;
				if(twoLevelMenuMap != null){
					if (twoLevelMenuMap.get("childMenuList") != null){
						childMenuList = (List)twoLevelMenuMap.get("childMenuList") ;
					}
					else{
						childMenuList = new ArrayList() ;
						
						twoLevelMenuMap.put("childMenuList" , childMenuList) ;
					}
					
					childMenuList.add(menuMap) ;
				}
			}
		}
		 
			
			
		 
		return menuList ;
	}
	
	/**
	 * 取左侧菜单(get Left Menu for partner)
	 * @param request
	 * @param response
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getLeftMenuForPartner(HttpServletRequest request,
			HttpServletResponse response) {
		//获得权限组ID 默认是00002(MyHome) 其它：00003(Coordinator) 00004(Management) 00005(Administrator)
		String roleGroupId = StringUtil.checkNull(request.getParameter("defaultRoleGroupId"), "00002") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
//		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("USERNO", admin.getUserNo()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("roleGroupId", roleGroupId) ;
		
		paramMap.put("language",admin.getLanguage());
		
		List menuList = new ArrayList() ;
		List menuTempList = loginDao.getLeftMenuForPartner(paramMap) ;
		
		LinkedHashMap topMenuMap = null ;
		LinkedHashMap oneLevelMenuMap = null ;
		LinkedHashMap twoLevelMenuMap = null ;
		
		
		
		for(int i = 0 ; i < menuTempList.size() ; ++ i ){
			LinkedHashMap menuMap = (LinkedHashMap)menuTempList.get(i) ;
			int depth = NumberUtils.parseNumber(ObjectUtils.toString(menuMap.get("DEPTH")), Integer.class) ;
			
			if(depth == 1){
				topMenuMap = menuMap ;
				menuList.add(topMenuMap) ;
			}
			
			if(depth == 2){
				oneLevelMenuMap = menuMap ;
				
				List<LinkedHashMap> childMenuList = null ;
				if(topMenuMap != null){
					if (topMenuMap.get("childMenuList") != null){
						childMenuList = (List)topMenuMap.get("childMenuList") ;
					}
					else{
						childMenuList = new ArrayList() ;
						
						topMenuMap.put("childMenuList" , childMenuList) ;
					}
					
					childMenuList.add(oneLevelMenuMap) ;
					
				}
			}
			
			if(depth == 3){
				twoLevelMenuMap = menuMap ;
				List<LinkedHashMap> childMenuList = null ;
				if(oneLevelMenuMap != null){
					if (oneLevelMenuMap.get("childMenuList") != null){
						childMenuList = (List)oneLevelMenuMap.get("childMenuList") ;
					}
					else{
						childMenuList = new ArrayList() ;
						
						oneLevelMenuMap.put("childMenuList" , childMenuList) ;
					}
					
					childMenuList.add(twoLevelMenuMap) ;
				}
			}
//			if(depth == 4){
//				List<LinkedHashMap> childMenuList = null ;
//				if(twoLevelMenuMap != null){
//					if (twoLevelMenuMap.get("childMenuList") != null){
//						childMenuList = (List)twoLevelMenuMap.get("childMenuList") ;
//					}
//					else{
//						childMenuList = new ArrayList() ;
//						
//						twoLevelMenuMap.put("childMenuList" , childMenuList) ;
//					}
//					
//					childMenuList.add(menuMap) ;
//				}
//			}
		}
		 
			
			
		 
		return menuList ;
	}
	
	/**
	 * 页面重新加载(get Top Menu)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public String getMenuLoad(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("language",Messages.getLanguage(request));
		return loginDao.getMenuLoad(paramMap);
	}
	
	/**
	 * 取一级菜单(get Top Menu by role group)
	 * @param request
	 * @param response
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getTopMenuByRoleGroupId(HttpServletRequest request,
			HttpServletResponse response) {
		//获得权限组ID 默认是00002(MyHome) 其它：00003(Coordinator) 00004(Management) 00005(Administrator)
		String roleGroupId = StringUtil.checkNull(request.getParameter("defaultRoleGroupId"), "00002") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		paramMap.put("USERNO", admin.getUserNo()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("roleGroupId", roleGroupId) ;
		
		paramMap.put("language",admin.getLanguage());
		
		List menuTopList = loginDao.getTopMenuByRoleGroupId(paramMap) ;
		
		return menuTopList ;
	}

	/**
	 * 取一级菜单(get Top Menu)
	 * @param request
	 * @param response
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getTopMenu(HttpServletRequest request,
			HttpServletResponse response) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		paramMap.put("USERNO", admin.getUserNo()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		
		paramMap.put("language",admin.getLanguage());
		
		List menuTopList = loginDao.getTopMenu(paramMap) ;
		
		return menuTopList ;
	}
	
	/**
	 * 取左侧默认菜单(get default Menu)
	 * @param request
	 * @param response
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getDefaultMenu(HttpServletRequest request,
			HttpServletResponse response) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("USERNO", admin.getUserNo()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		
		paramMap.put("language",admin.getLanguage());
		//左侧默认人事模块菜单
		List menuList = new ArrayList() ;
		List menuTempList = loginDao.getLeftMenu(paramMap) ;
		
		LinkedHashMap topMenuMap = null ;
		LinkedHashMap oneLevelMenuMap = null ;
		LinkedHashMap twoLevelMenuMap = null ;
		
		
		for(int i = 0 ; i < menuTempList.size() ; ++ i ){
			LinkedHashMap menuMap = (LinkedHashMap)menuTempList.get(i) ;
			int depth = NumberUtils.parseNumber(ObjectUtils.toString(menuMap.get("DEPTH")), Integer.class) ;
			
			if(depth == 0){
				topMenuMap = menuMap ;
				menuList.add(topMenuMap) ;
			}
			
			if(depth == 1){
				oneLevelMenuMap = menuMap ;
				
				List<LinkedHashMap> childMenuList = null ;
				if(topMenuMap != null){
					if (topMenuMap.get("childMenuList") != null){
						childMenuList = (List)topMenuMap.get("childMenuList") ;
					}
					else{
						childMenuList = new ArrayList() ;
						
						topMenuMap.put("childMenuList" , childMenuList) ;
					}
					
					childMenuList.add(oneLevelMenuMap) ;
					
				}
			}
			
			if(depth == 2){
				twoLevelMenuMap = menuMap ;
				List<LinkedHashMap> childMenuList = null ;
				if(oneLevelMenuMap != null){
					if (oneLevelMenuMap.get("childMenuList") != null){
						childMenuList = (List)oneLevelMenuMap.get("childMenuList") ;
					}
					else{
						childMenuList = new ArrayList() ;
						
						oneLevelMenuMap.put("childMenuList" , childMenuList) ;
					}
					
					childMenuList.add(twoLevelMenuMap) ;
				}
			}
			if(depth == 3){
				List<LinkedHashMap> childMenuList = null ;
				if(twoLevelMenuMap != null){
					if (twoLevelMenuMap.get("childMenuList") != null){
						childMenuList = (List)twoLevelMenuMap.get("childMenuList") ;
					}
					else{
						childMenuList = new ArrayList() ;
						
						twoLevelMenuMap.put("childMenuList" , childMenuList) ;
					}
					
					childMenuList.add(menuMap) ;
				}
			}
			
		}
		
		return menuList ;
	}
	
	/**
	 * 取预转正日期(get ExpiredProbation)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getProbationList(Object obj) {
		
		List probationList = loginDao.getProbationList(obj) ;
		
		return probationList ;
	}
	/**
	 * 取预转正日期(get ExpiredProbation)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getUserpass(Object obj) {
		
		List probationList = loginDao.getUserpass(obj) ;
		
		return probationList ;
	}
	
	/**
	 * 取预转正日期条数(get ExpiredProbation count)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getProbationCnt(Object obj) {
		
		int notExistsContractList = loginDao.getProbationCnt(obj) ;
		
		return notExistsContractList ;
	}
	
	/**
	 * 取到期合同(get ExpiredContract)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractList(Object obj) {
		
		List contractList = loginDao.getContractList(obj) ;
		
		return contractList ;
	}
	
	/**
	 * 取到期合同条数(get ExpiredContract count)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getContractCnt(Object obj) {
		
		int notExistsContractList = loginDao.getContractCnt(obj) ;
		
		return notExistsContractList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getIdCardCnt(Object obj) {
		
		int notExistsContractList = loginDao.getIdCardCnt(obj) ;
		
		return notExistsContractList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getBecomeRegulerWarn(Object obj) {
		
		int notImportList = loginDao.getBecomeRegulerWarn(obj) ;
		
		return notImportList ;
	}
	
	
	@SuppressWarnings("unchecked")
	public int getPaNotImportCnt(Object obj) {
		
		int notImportList = loginDao.getPaNotImportCnt(obj) ;
		
		return notImportList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getLeaveConfirmCnt(Object obj) {
		
		int getLeaveConfirmList = loginDao.getLeaveConfirmCnt(obj) ;
		
		return getLeaveConfirmList  ;
	}
	
	@SuppressWarnings("unchecked")
	public int getStartedLeftConfirmCnt(Object obj) {
		
		int getStartedLeftConfirmCnt = loginDao.getStartedLeftConfirmCnt(obj) ;
		
		return getStartedLeftConfirmCnt  ;
	}
	
	@SuppressWarnings("unchecked")
	public int getChangeDeptConfirmCnt(Object obj) {
		
		int getChangeDeptConfirmCnt = loginDao.getChangeDeptConfirmCnt(obj) ;
		
		return getChangeDeptConfirmCnt  ;
	}
	
	@SuppressWarnings("unchecked")
	public int getOtConfirmCnt(Object obj) {
		
		int getOtConfirmCnt = loginDao.getOtConfirmCnt(obj) ;
		
		return getOtConfirmCnt  ;
	}
	
	@SuppressWarnings("unchecked")
	public int getTempConfirmCnt(Object obj) {
		
		int getTempConfirmCnt = loginDao.getTempConfirmCnt(obj) ;
		
		return getTempConfirmCnt  ;
	}
	
	/**
	 * 到期证件(get HrCredential List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHrCredentialList(Object obj) {
		
		List credentialList = loginDao.getHrCredentialList(obj) ;
		
		return credentialList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonInfo(HttpServletRequest request) {

		List retrunList = new ArrayList();

		// 页面提交数据
		Map<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		retrunList = loginDao.getPersonInfo(paramMap);
		return retrunList;
	}
	
	/**
	 * 到期证件条数(get HrCredential List count)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getHrCredentialCnt(Object obj) {
		
		int notExistsContractList = loginDao.getHrCredentialCnt(obj) ;
		
		return notExistsContractList ;
	}
	
	/**
	 * 取人事令列表(get Upgrade List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getUpgradeList(Object obj) {
		
		List upgradeList = loginDao.getUpgradeList(obj) ;
		
		return upgradeList ;
	}
	
	/**
	 * 取人事令列表(get Upgrade List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getUpgradeCnt(Object obj) {
		
		int notExistsContractList = loginDao.getUpgradeCnt(obj) ;
		
		return notExistsContractList ;
	}
	
	
	/**
	 * 取健康证令列表(get Health List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getHealthList(Object obj) {
		
		List healthList = loginDao.getHealthList(obj) ;
		
		return healthList ;
	}
	
	/**
	 * 取健康证令列表条数(get Health List count)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getHealthCnt(Object obj) {
		
		int notExistsContractList = loginDao.getHealthCnt(obj) ;
		
		return notExistsContractList ;
	}
	
	/**
	 * 取生日列表(get Health List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getBirthdayList(Object obj) {
		
		List birthdayList = loginDao.getBirthdayList(obj) ;
		
		return birthdayList ;
	}
	
	/**
	 * 取保险申请表 是否有权限查看列表
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getInsurancePowerList(Object obj) {
		
		List getInsurancePowerList = loginDao.getInsurancePowerList(obj) ;
		
		return getInsurancePowerList ;
	}

	/**
	 * 取保险申请表 是否有权限查看列表
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getInsurancePowerManList(Object obj) {
		
		List getInsurancePowerManList = loginDao.getInsurancePowerManList(obj) ;
		
		return getInsurancePowerManList ;
	}
	
	/**
	 * 取保险申请表  列表
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceList(Object obj) {
		
		List getInsuranceList = loginDao.getInsuranceList(obj) ;
		
		return getInsuranceList ;
	}
	/**
	 * 取语言列表(get Language List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getLanguageList(Object obj) {
		
		List languageList = loginDao.getLanguageList(obj) ;
		
		return languageList ;
	}
	
	/**
	 * 未签合同(get NotExists ContractList)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getNotExistsContractList(Object obj) {
		
		List notExistsContractList = loginDao.getNotExistsContractList(obj) ;
		
		return notExistsContractList ;
	}
	
	/**
	 * 未签合同条数(get NotExists ContractCnt)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public int getNotExistsContractCnt(Object obj) {
		
		int notExistsContractList = loginDao.getNotExistsContractCnt(obj) ;
		
		return notExistsContractList ;
	}
	
	/**
	 * 加班待决裁(get Count OT)
	 * @param Object
	 * @return List
	 */
//	@SuppressWarnings("unchecked")
//	public int getCountOT(Object obj) {
//		
//		int countOT = loginDao.getCountOT(obj) ;
//		
//		return countOT ;
//	}
	
	/**
	 * 未签合同(get NotExists ContractList)
	 * @param request
	 * @param response
	 * @return List
	 */
//	@SuppressWarnings("unchecked")
//	public int getCountLeave(Object obj) {
//		
//		int countLeave = loginDao.getCountLeave(obj) ;
//		
//		return countLeave ;
//	}
	
	public boolean checkPermission(HttpServletRequest request,String flag){
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("MAIN_PARAM_INFO_NO", flag);
		paramMap.put("USER_NO", admin.getUserNo());
		return this.loginDao.checkPermission(paramMap);
	}
	
	/**
	 * 提醒天数(get AlertDays List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getAlertDaysList(Object obj) {
		
		List alertDaysList = loginDao.getAlertDaysList(obj) ;
		
		return alertDaysList ;
	}
	
	/**
	 * 首页面个人信息查看、待决裁、待确认提醒(get MenuMemo List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getMenuMemoList(Object obj) {
		
		List menuMemoList = loginDao.getMenuMemoList(obj) ;
		
		return menuMemoList ;
	}

	/**
	 * 根据url中的menuNo查找出当前的title
	 */
	@Override
	public Map getTitleName(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		
		paramMap.put("language",admin.getLanguage());
		return loginDao.getTitleName(paramMap);
	}
	
	/**
	 * 根据url中的menuNo查找出当前的title(partner系统)
	 */
	@Override
	public Map getTitleNamePartner(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		
		paramMap.put("language",admin.getLanguage());
		return loginDao.getTitleNamePartner(paramMap);
	}
	
	/** 
	 * 获取公告列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author    weizhengchen@ait.net.cn 
	*/
	@SuppressWarnings("unchecked")
	public List getNoticeList(Object obj) {
		
		List noticeList = loginDao.getNoticeList(obj) ;
		if(noticeList != null && noticeList.size() > 0){
			for(int i=0;i<noticeList.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)noticeList.get(i);
				returnMap.put("APPLY_NO", returnMap.get("ID"));
				returnMap.put("APPLY_TYPE", "0303");
				List fileList = loginDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		return noticeList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewApprovalInfo(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		returnList = loginDao.viewApprovalInfo(paramMap);
		
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewNoticeedEmail(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		returnList = loginDao.viewNoticeedEmail(paramMap);
		
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceEx(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		returnList = loginDao.viewAttendanceEx(paramMap);
		
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceExCoor(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getAdminID());
		returnList = loginDao.viewAttendanceExCoor(paramMap);
		
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceExManagement(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("MANAGEMENT_INFO", admin.getAdminID());
		returnList = loginDao.viewAttendanceExCoor(paramMap);
		
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceManagement(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		returnList = loginDao.viewAttendanceManagement(paramMap);
		
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewOTManagement(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		returnList = loginDao.viewOTManagement(paramMap);
		
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewApplyList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		returnList = loginDao.viewApplyList(paramMap);
		
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewApplyListCoor(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		returnList = loginDao.viewApplyListCoor(paramMap);
		
		return returnList;
	}
	
	@Override
	public void updateUserData(HttpServletRequest request, String target) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		paramMap.put("PERSON_ID",admin.getAdminID());
		paramMap.put("PERSON_IP",admin.getAdminIP());
		loginDao.updateUserData(paramMap, target);
	}

}
