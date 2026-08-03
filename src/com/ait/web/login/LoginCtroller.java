package com.ait.web.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import BI.IF;

import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.EssApplyInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.LoginSer;
import com.ait.sys.service.MyHomeSer;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.MailTo;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

@Controller
@RequestMapping(value = "/login")
public class LoginCtroller {

	Logger logger = Logger.getLogger(LoginCtroller.class);

	@Autowired
	@Qualifier("loginSerImpl")
	private LoginSer loginStr;
	@Autowired
	private MyHomeSer myHomeSer;

	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private AuthorityUtil authorityUtil;

	@Autowired
	private EssApplyInfoSer EssApplyInfoSer;

	@RequestMapping(value = "/in")
	@ResponseBody
	public String loginIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// logger.info("login waiting...");
		return loginStr.findUser(request, response);
	}

	@RequestMapping(value = "/out", method = RequestMethod.GET)
	public void loginOut(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute("LoginUser");
		request.getSession().removeAttribute("CurrentOperator");
		request.getRequestDispatcher("/").forward(request, response);
	}

	// 切换法人
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/changeCpny", method = RequestMethod.GET)
	public void changeCpny(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String CPNY_ID = request.getParameter("cpny_id");

		paramMap.put("CPNY_ID", CPNY_ID);
		List list = loginStr.getUserpass(paramMap);
		Iterator iter = list.iterator();
		String pass = "";
		while (iter.hasNext()) {
			Map map = (LinkedHashMap) iter.next();
			pass = (String) map.get("PASSWORD");
		}
		request.getSession().removeAttribute("LoginUser");
		request.setAttribute("COMPANY_ID", CPNY_ID);
		request.setAttribute("username", "admin");
		request.setAttribute("password", pass);
		request.getParameter("password");
		loginStr.findUserChage(request, response);
		// request.getRequestDispatcher("/login/home.do").forward(request,
		// response);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView loginHome(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		// 首先选出一级菜单 depth = 0
		List menuList = loginStr.getTopMenu(request, response);
		modelMap.put("menuList", menuList);
		// 让首页面左侧 默认显示人事模块信息
		List leftList = loginStr.getLeftMenu(request, response);
		modelMap.put("leftList", leftList);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Object tipsMap = myHomeSer.getTipsForLogin_home(request);
		Object manualMenu = myHomeSer.getManualMenu(request);
		modelMap.put("tipsMap", tipsMap);
		modelMap.put("manualMenu", manualMenu);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("USERNO", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", admin.getLanguage());
		paramMap.put("STATUS_CODE", "1375");// 离职人员
		paramMap.put("EMP_OFFICE", "15120");// 离职人员
		paramMap.put("PROBATION_STATUS_CODE", "('1373','1374')");// 实习

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		/********************************* 加权限控制 *******************************/
		// int isIndexUserContract =
		// authorityUtil.isIndexUserContract(admin.getPersonId());
		// int isIndexUserHr = authorityUtil.isIndexUserHr(admin.getPersonId());
		// modelMap.put("isIndexUserHr", isIndexUserHr);
		// modelMap.put("isIndexUserContract", isIndexUserContract);

		// 获取员工的总数 首页显示
		// 去年
		paramMap.put("FLAG", "lastYear");
		modelMap.put("totalEmpCountLastYear", authorityUtil
				.getTotalEmpCountForMain(paramMap));
		// 上个月
		paramMap.put("FLAG", "lastMonth");
		modelMap.put("totalEmpCountLastMonth", authorityUtil
				.getTotalEmpCountForMain(paramMap));
		// 当月
		paramMap.put("FLAG", "currMonth");
		modelMap.put("totalEmpCountCurrMonth", authorityUtil
				.getTotalEmpCountForMain(paramMap));
		// 获取在职的员工 首页显示
		// 去年
		paramMap.put("FLAG", "lastYear");
		modelMap.put("InCpnyTotalEmpCountLastYear", authorityUtil
				.getInCpnyTotalEmpCountForMain(paramMap));
		// 上个月
		paramMap.put("FLAG", "lastMonth");
		modelMap.put("InCpnyTotalEmpCountLastMonth", authorityUtil
				.getInCpnyTotalEmpCountForMain(paramMap));
		// 当月
		paramMap.put("FLAG", "currMonth");
		modelMap.put("InCpnyTotalEmpCountCurrMonth", authorityUtil
				.getInCpnyTotalEmpCountForMain(paramMap));
		// 获取离职的员工 首页显示
		// 去年
		paramMap.put("FLAG", "lastYear");
		modelMap.put("LeftManTotalEmpCountLastYear", authorityUtil
				.getLeftManTotalEmpCountForMain(paramMap));
		// 上个月
		paramMap.put("FLAG", "lastMonth");
		modelMap.put("LeftManTotalEmpCountLastMonth", authorityUtil
				.getLeftManTotalEmpCountForMain(paramMap));
		// 当月
		paramMap.put("FLAG", "currMonth");
		modelMap.put("LeftManTotalEmpCountCurrMonth", authorityUtil
				.getLeftManTotalEmpCountForMain(paramMap));
		// 获取新入职的员工 首页显示
		// 去年
		paramMap.put("FLAG", "lastYear");
		modelMap.put("NewManTotalEmpCountLastYear", authorityUtil
				.getNewManTotalEmpCountForMain(paramMap));
		// 上个月
		paramMap.put("FLAG", "lastMonth");
		modelMap.put("NewManTotalEmpCountLastMonth", authorityUtil
				.getNewManTotalEmpCountForMain(paramMap));
		// 当月
		paramMap.put("FLAG", "currMonth");
		modelMap.put("NewManTotalEmpCountCurrMonth", authorityUtil
				.getNewManTotalEmpCountForMain(paramMap));

		int isSuperUser = authorityUtil.isSuperUser(admin.getPersonId());
		int isSuperHrUser = authorityUtil.isSuperHrUser(admin.getPersonId());
		int isWageUser = authorityUtil.isWageUser(admin.getPersonId());
		int isBaoXianUser = authorityUtil.isBaoXianUser(admin.getPersonId());
		int isArUser = authorityUtil.isHrArUser(admin.getPersonId());
		int isOrgUser = authorityUtil.isOrgUser(admin.getPersonId());
		int isHrUser = authorityUtil.isHrUser(admin.getPersonId());
		int isRecruitUser = authorityUtil.isRecruitUser(admin.getPersonId());
		int isEvsUser = authorityUtil.isEvsUser(admin.getPersonId());
		int isTraUser = authorityUtil.isTraUser(admin.getPersonId());
		int isInformationUser = authorityUtil.isInformationUser(admin.getPersonId());  
		int isGaViewUser = authorityUtil.isGaViewUser(admin.getPersonId()); //2018/11 Start 新增考勤申请查看 页面

		modelMap.put("isSuperUser", isSuperUser);
		modelMap.put("isSuperHrUser", isSuperHrUser);
		modelMap.put("isWageUser", isWageUser);
		modelMap.put("isBaoXianUser", isBaoXianUser);
		modelMap.put("isArUser", isArUser);
		modelMap.put("isOrgUser", isOrgUser);
		modelMap.put("isHrUser", isHrUser);
		modelMap.put("isRecruitUser", isRecruitUser);
		modelMap.put("isEvsUser", isEvsUser);
		modelMap.put("isTraUser", isTraUser);
		modelMap.put("isInformationUser", isInformationUser);
		modelMap.put("isGaViewUser", isGaViewUser);  //2018/11 Start 新增考勤申请查看 页面
		
		admin.setIsBaoXianUser(isBaoXianUser);

		// 登陆法人，暂用于页面TITLE显示当前是哪个法人登陆
		modelMap.put("LOGIN_CPNY", admin.getCpnyId());
		modelMap.put("admin", admin);
		modelMap.put("companyList", empInfoSer.getCompanyListHome(request));
		// 人事-个人申请，空照片个数
		modelMap.put("getApplyNumber", EssApplyInfoSer.getApplyNumber(request));
		modelMap.put("getContractCnt", loginStr.getContractCnt(paramMap));
		modelMap.put("getIdCardCnt", loginStr.getIdCardCnt(paramMap));
		modelMap.put("getPaNotImportCnt", loginStr.getPaNotImportCnt(paramMap));
		//转正提醒
		modelMap.put("getBecomeRegulerWarn", loginStr.getBecomeRegulerWarn(paramMap));
		
		/**
		 * 人事主页转正提醒条件
		 */

		String defaultRoleGroupName = StringUtil.checkNull(request
				.getParameter("defaultRoleGroupName"), "MyHome");

		if (StringUtil.checkNull(request.getParameter("reqRole")).equals(
				"manager")) {
			defaultRoleGroupName = "Management";
		}
		modelMap.put("defaultRoleGroupName", defaultRoleGroupName);

		
		
		modelMap.put("getNotExistsContractCnt", loginStr
				.getNotExistsContractCnt(paramMap));
		modelMap.put("getPersonalPhotoNullNumber", EssApplyInfoSer
				.getPersonalPhotoNullNumber(request));
		modelMap.put("getLeaveConfirmCnt",loginStr.getLeaveConfirmCnt(paramMap));
		modelMap.put("getOtConfirmCnt",loginStr.getOtConfirmCnt(paramMap));
		modelMap.put("getTempConfirmCnt",loginStr.getTempConfirmCnt(paramMap));
		modelMap.put("getStartedLeftConfirmCnt",loginStr.getStartedLeftConfirmCnt(paramMap));
		modelMap.put("getChangeDeptConfirmCnt",loginStr.getChangeDeptConfirmCnt(paramMap));   
		return new ModelAndView("/login/home", modelMap);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPersonInfo")
	public ModelAndView getPersonInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List monthPersonCountInfoSonList = this.loginStr.getPersonInfo(request);
		modelMap.put("monthPersonCountInfoSonList", monthPersonCountInfoSonList);
		return new ModelAndView("/pa/workManagement/monthPersonCountInfoSonList", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/home_partner", method = RequestMethod.GET)
	public ModelAndView loginHomePartner(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String defaultRoleGroupId = StringUtil.checkNull(request
				.getParameter("defaultRoleGroupId"), "00002");
		if (StringUtil.checkNull(request.getParameter("reqRole")).equals(
				"manager")) {
			defaultRoleGroupId = "00004";
		}
		String defaultRoleGroupName = StringUtil.checkNull(request
				.getParameter("defaultRoleGroupName"), "MyHome");
		String defaultRoleGroupNameDis  = StringUtil.checkNull(request
				.getParameter("defaultRoleGroupNameDis"), "MyHome");
		if (StringUtil.checkNull(request.getParameter("reqRole")).equals(
				"manager")) {
			defaultRoleGroupName = "Management";
		}
		modelMap.put("defaultRoleGroupId", defaultRoleGroupId);
		modelMap.put("defaultRoleGroupName", defaultRoleGroupName);
		modelMap.put("defaultRoleGroupNameDis", defaultRoleGroupNameDis);
		// 根据personid获取该登陆者的所有权限组
		List roleGroupList = loginStr.getRoleGroupListByPersonId(request,
				response);
		// 移除掉当前选择的权限组 第一次登陆默认是MyHome权限组
		for (int i = 0; i < roleGroupList.size(); i++) {
			LinkedHashMap menuMap = (LinkedHashMap) roleGroupList.get(i);
			if (menuMap.get("ROLE_GROUP_ID").equals(defaultRoleGroupId))
				roleGroupList.remove(i);
		}
		modelMap.put("roleGroupList", roleGroupList);

		// 根据权限组ID获取对应的二级菜单
		List menuList = loginStr.getTopMenuByRoleGroupId(request, response);
		modelMap.put("menuList", menuList);
		// 获取默认的第一个二级菜单code 为了页面初始化左侧菜单
		if (menuList != null && menuList.size() > 0)
			modelMap.put("defaultSecMenu", ((LinkedHashMap) menuList.get(0))
					.get("MENU_CODE"));
		else
			modelMap.put("defaultSecMenu", "ess0100");
		// 让首页面左侧 默认显示人事模块信息
		List leftList = loginStr.getLeftMenuForPartner(request, response);
		modelMap.put("leftList", leftList);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Object tipsMap = myHomeSer.getTipsForLogin(request);
		Object manualMenu = myHomeSer.getManualMenu(request);
		modelMap.put("tipsMap", tipsMap);
		modelMap.put("manualMenu", manualMenu);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("USERNO", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", admin.getLanguage());
		paramMap.put("STATUS_CODE", "1375");// 离职人员
		paramMap.put("EMP_OFFICE", "15120");// 离职人员
		paramMap.put("PROBATION_STATUS_CODE", "('1373','1374')");// 实习

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		// paramMap.put("viewNotContractDays", 8);// 未签合同
		// paramMap.put("viewProbationDays", 8);// 试用转正 显示数量
		// paramMap.put("viewContractDays", 8);// 到期合同 显示数量
		// paramMap.put("alertProbationDays", 30);// 转正日期提醒天数
		// paramMap.put("alertContractDays", 60);// 到期合同提醒天数
		// paramMap.put("alertUpgradeDays", 30);// 人事令提醒天数
		// paramMap.put("alertNotContractDays", 0);// 未签合同提醒天数

		/********************************* 加权限控制 *******************************/
		// int isIndexUserContract =
		// authorityUtil.isIndexUserContract(admin.getPersonId());
		// int isIndexUserHr = authorityUtil.isIndexUserHr(admin.getPersonId());
		// modelMap.put("isIndexUserHr", isIndexUserHr);
		// modelMap.put("isIndexUserContract", isIndexUserContract);
		modelMap.put("isAccountUser", authorityUtil.isAccountUser(admin
				.getPersonId()));

		modelMap.put("isAccountTxtUser", authorityUtil.isAccountTxtUser(admin
				.getPersonId()));
		
		modelMap.put("isPersonTaxUser", authorityUtil.isPersonTaxUser(admin
				.getPersonId()));
		

		// 未签合同提醒
		/*
		 * if (1 == isIndexUserContract) { List getNotExistsContractList =
		 * loginStr .getNotExistsContractList(paramMap); int
		 * getNotExistsContractCnt = loginStr
		 * .getNotExistsContractCnt(paramMap);
		 * modelMap.put("getNotExistsContractList", getNotExistsContractList);
		 * modelMap.put("getNotExistsContractCnt", getNotExistsContractCnt);
		 * modelMap.put("isIndexUserContract", isIndexUserContract); } if (1 ==
		 * isIndexUserHr) { // 预转正日期提醒 List getProbationList =
		 * loginStr.getProbationList(paramMap); int getProbationCnt =
		 * loginStr.getProbationCnt(paramMap); modelMap.put("getProbationList",
		 * getProbationList); modelMap.put("getProbationCnt", getProbationCnt);
		 * modelMap.put("isIndexUserHr", isIndexUserHr); } if (1 ==
		 * isIndexUserContract) { // 到期合同提醒 List getContractList =
		 * loginStr.getContractList(paramMap); int getContractCnt =
		 * loginStr.getContractCnt(paramMap); modelMap.put("getContractList",
		 * getContractList); modelMap.put("getContractCnt", getContractCnt);
		 * modelMap.put("isIndexUserContract", isIndexUserContract); } if (1 ==
		 * isIndexUserHr) { // 人事令提醒 List getUpgradeList =
		 * loginStr.getUpgradeList(paramMap); modelMap.put("getUpgradeList",
		 * getUpgradeList); modelMap.put("getUpgradeCnt",
		 * getUpgradeList.size()); modelMap.put("isIndexUserHr", isIndexUserHr);
		 * }
		 * 
		 * paramMap.put("DEPTNO", admin.getDeptNo()); List getBirthdayList =
		 * loginStr.getBirthdayList(paramMap); int getBirthdayListCnt =
		 * getBirthdayList.size(); modelMap.put("getBirthdayList",
		 * getBirthdayList); modelMap.put("getBirthdayListCnt",
		 * getBirthdayListCnt);
		 */
		// 公告 --魏正晨 2014-06-19
		modelMap.put("getNoticeList", loginStr.getNoticeList(paramMap));
		// modelMap.put("getNoticeListCnt", getNoticeListCnt);
		// 登陆法人，暂用于页面TITLE显示当前是哪个法人登陆
		modelMap.put("LOGIN_CPNY", admin.getCpnyId());
		modelMap.put("admin", admin);
		modelMap.put("companyList", empInfoSer.getCompanyListHome(request));
		
		//待裁决
		List viewApprovalEmail = this.loginStr.viewApprovalInfo(request);
		modelMap.put("viewApprovalEmail", viewApprovalEmail);
		//通知
		List viewNoticeedEmail = this.loginStr.viewNoticeedEmail(request);
		modelMap.put("viewNoticeedEmail", viewNoticeedEmail);
		//MyHome权限 
		if("MyHome".equals(defaultRoleGroupName)||"Administrator".equals(defaultRoleGroupName)){
			//Attendance Status
			List viewAttendanceEx = this.loginStr.viewAttendanceEx(request);
			modelMap.put("viewAttendanceEx", viewAttendanceEx);
			modelMap.put("viewAttendanceExCnt", viewAttendanceEx == null ? 0:viewAttendanceEx.size());
			//Approval Status
			List viewApprovalStatusList = this.loginStr.viewApplyList(request);
			modelMap.put("viewApprovalStatusList", viewApprovalStatusList);
		}
		if("Coordinator".equals(defaultRoleGroupName)){
			//Coordinator权限
			List viewAttendanceExCoor = this.loginStr.viewAttendanceExCoor(request);
			modelMap.put("viewAttendanceExCoor", viewAttendanceExCoor);
			//Approval Status
			List viewApplyListCoor = new ArrayList();
			// viewApplyListCoor = this.loginStr.viewApplyListCoor(request); HMT_20230424 Tạm thời bỏ vì dữ liệu quá nặng
			modelMap.put("viewApplyListCoor",viewApplyListCoor);
		}
		if("Management".equals(defaultRoleGroupName)){
			//Management权限
			List viewAttendanceExManagement = this.loginStr.viewAttendanceExManagement(request);
			modelMap.put("viewAttendanceExManagement", viewAttendanceExManagement);
			//Management权限查询前一天休假申请数量
			List viewAttendanceManagement = this.loginStr.viewAttendanceManagement(request);
			modelMap.put("viewAttendanceManagement", viewAttendanceManagement);
			modelMap.put("viewAttendanceManagementCnt", viewAttendanceManagement == null ? 0:viewAttendanceManagement.size());
			//Management权限查询前一天加班申请数量
			List viewOTManagement = this.loginStr.viewOTManagement(request);
			modelMap.put("viewOTManagement", viewOTManagement);
			modelMap.put("viewOTManagementCnt", viewOTManagement == null ? 0:viewOTManagement.size());
			//Approval Status
			List viewApprovalListManage = this.loginStr.viewApprovalInfo(request);
			modelMap.put("viewApprovalListManage",viewApprovalListManage);
		}
		
		// seal system
		// Map sealCtrl=myHomeSer.getSealControl(paramMap);
		// modelMap.put("sealLogin", sealCtrl.get("SEAL_LOGIN"));

		return new ModelAndView("/login/home_partner", modelMap);
	}

	@RequestMapping(value = "/getMenuLoad", method = RequestMethod.POST)
	@ResponseBody
	public String getMenuLoad(HttpServletRequest request) throws Exception {

		return loginStr.getMenuLoad(request);
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView loginMain(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		modelMap.put("appcnt", (String) myHomeSer.getHomePage(request));
		modelMap.put("page", request.getParameter("page"));

		return new ModelAndView("/login/main", modelMap);
	}

	@RequestMapping(value = "/manualWindow", method = RequestMethod.GET)
	public ModelAndView manualWindow(HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/login/manualWindow", modelMap);
	}

	/**
	 * 语言切换(change Language)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/changeLanguage")
	public void changeLanguage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String language = request.getParameter("language");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		admin.setLanguage(language);
	}

	/**
	 * 根据url中的menuNo查找出当前的title
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author liangwei@ait.net.cn
	 * @date 2013-7-25 上午10:10:52
	 * @version V1.0
	 */
	@RequestMapping(value = "/titleName")
	@ResponseBody
	public Map titleName(HttpServletRequest request) throws Exception {
		Map titleName = loginStr.getTitleName(request);
		return titleName;
	}
	
	/**
	 * 根据url中的menuNo查找出当前的title
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lipeng@ait.net.cn
	 * @date 2017-9-12 下午14:53:52
	 * @version V1.0
	 */
	@RequestMapping(value = "/titleNamePartner")
	@ResponseBody
	public Map titleNamePartner(HttpServletRequest request) throws Exception {
		Map titleName = loginStr.getTitleNamePartner(request);
		return titleName;
	}

	/**
	 * 登录seal system
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSealLogin")
	public ModelAndView viewSealLogin(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("empId", admin.getEmpID());
		paramMap.put("cpnyId", admin.getCpnyId());
		// get system date
		Map result = this.myHomeSer.getSystemDate(paramMap);
		// 将oracle系统时间加密后传送和插入
		String v_date = result.get("V_DATE").toString();
		String pwd = this.crypt(v_date);
		paramMap.put("pwd", pwd);
		paramMap.put("cnt", result.get("CNT"));
		// 将加密后的日期插入到clealcn库中
		this.myHomeSer.createLoginInfo(request, paramMap);
		modelMap.put("paramMap", paramMap);
		return new ModelAndView("/login/viewSealLogin", modelMap);
	}

	// 将字符加密
	public static String crypt(String str) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException(
					"String to encript cannot be null or zero length");
		}

		StringBuffer hexString = new StringBuffer();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();

			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0"
							+ Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return hexString.toString();
	}

	/**
	 * 获取未签合同信息
	 * 
	 * @param request
	 * @author wendi
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNotExistsContractList")
	@ResponseBody
	public Map getNotExistsContractList(HttpServletRequest request)
			throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("USERNO", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", admin.getLanguage());
		paramMap.put("STATUS_CODE", "1375");// 离职人员
		paramMap.put("EMP_OFFICE", "15120");// 离职人员
		paramMap.put("PROBATION_STATUS_CODE", "('1373','1374')");// 实习

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		paramMap.put("viewNotContractDays", 8);// 未签合同
		paramMap.put("viewProbationDays", 8);// 试用转正 显示数量
		paramMap.put("viewContractDays", 8);// 到期合同 显示数量
		paramMap.put("alertProbationDays", 30);// 转正日期提醒天数
		paramMap.put("alertContractDays", 60);// 到期合同提醒天数
		paramMap.put("alertUpgradeDays", 30);// 人事令提醒天数
		paramMap.put("alertNotContractDays", 0);// 未签合同提醒天数
		Map<String, Object> map = new HashMap<String, Object>();

		List getNotExistsContractList = loginStr
				.getNotExistsContractList(paramMap);
		int getNotExistsContractCnt = loginStr
				.getNotExistsContractCnt(paramMap);
		map.put("getNotExistsContractList", getNotExistsContractList);
		map.put("getNotExistsContractCnt", getNotExistsContractCnt);

		map.put("statusCode", "200");
		return map;
	}

	/**
	 * 到期合同提醒
	 * 
	 * @param request
	 * @author wendi
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/getContractList")
	@ResponseBody
	public Map getContractList(HttpServletRequest request) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("USERNO", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", admin.getLanguage());
		paramMap.put("STATUS_CODE", "1375");// 离职人员
		paramMap.put("EMP_OFFICE", "15120");// 离职人员
		paramMap.put("PROBATION_STATUS_CODE", "('1373','1374')");// 实习

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		paramMap.put("viewNotContractDays", 8);// 未签合同
		paramMap.put("viewProbationDays", 8);// 试用转正 显示数量
		paramMap.put("viewContractDays", 8);// 到期合同 显示数量
		paramMap.put("alertProbationDays", 30);// 转正日期提醒天数
		paramMap.put("alertContractDays", 60);// 到期合同提醒天数
		paramMap.put("alertUpgradeDays", 30);// 人事令提醒天数
		paramMap.put("alertNotContractDays", 0);// 未签合同提醒天数
		Map<String, Object> map = new HashMap<String, Object>();

		// 到期合同提醒
		List getContractList = loginStr.getContractList(paramMap);
		int getContractCnt = loginStr.getContractCnt(paramMap);
		map.put("getContractList", getContractList);
		map.put("getContractCnt", getContractCnt);

		map.put("statusCode", "200");
		return map;
	}

	/**
	 * 人事令提醒
	 * 
	 * @param request
	 * @author wendi
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUpgradeList")
	@ResponseBody
	public Map getUpgradeList(HttpServletRequest request) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("USERNO", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", admin.getLanguage());
		paramMap.put("STATUS_CODE", "1375");// 离职人员
		paramMap.put("EMP_OFFICE", "15120");// 离职人员
		paramMap.put("PROBATION_STATUS_CODE", "('1373','1374')");// 实习

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		paramMap.put("viewNotContractDays", 8);// 未签合同
		paramMap.put("viewProbationDays", 8);// 试用转正 显示数量
		paramMap.put("viewContractDays", 8);// 到期合同 显示数量
		paramMap.put("alertProbationDays", 30);// 转正日期提醒天数
		paramMap.put("alertContractDays", 60);// 到期合同提醒天数
		paramMap.put("alertUpgradeDays", 30);// 人事令提醒天数
		paramMap.put("alertNotContractDays", 0);// 未签合同提醒天数
		Map<String, Object> map = new HashMap<String, Object>();

		// 人事令提醒
		List getUpgradeList = loginStr.getUpgradeList(paramMap);
		map.put("getUpgradeList", getUpgradeList);
		map.put("getUpgradeCnt", getUpgradeList.size());

		map.put("statusCode", "200");
		return map;
	}

	/**
	 * 预转正日期提醒
	 * 
	 * @param request
	 * @author wendi
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProbationList")
	@ResponseBody
	public Map getProbationList(HttpServletRequest request) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("USERNO", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", admin.getLanguage());
		paramMap.put("STATUS_CODE", "1375");// 离职人员
		paramMap.put("EMP_OFFICE", "15120");// 离职人员
		paramMap.put("PROBATION_STATUS_CODE", "('1373','1374')");// 实习

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		paramMap.put("viewNotContractDays", 8);// 未签合同
		paramMap.put("viewProbationDays", 8);// 试用转正 显示数量
		paramMap.put("viewContractDays", 8);// 到期合同 显示数量
		paramMap.put("alertProbationDays", 30);// 转正日期提醒天数
		paramMap.put("alertContractDays", 60);// 到期合同提醒天数
		paramMap.put("alertUpgradeDays", 30);// 人事令提醒天数
		paramMap.put("alertNotContractDays", 0);// 未签合同提醒天数
		Map<String, Object> map = new HashMap<String, Object>();

		// 预转正日期提醒
		List getProbationList = loginStr.getProbationList(paramMap);
		int getProbationCnt = loginStr.getProbationCnt(paramMap);
		map.put("getProbationList", getProbationList);
		map.put("getProbationCnt", getProbationCnt);

		map.put("statusCode", "200");
		return map;
	}

	/**
	 * 生日列表 当月
	 * 
	 * @param request
	 * @author wendi
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBirthdayList")
	@ResponseBody
	public Map getBirthdayList(HttpServletRequest request) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("USERNO", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", admin.getLanguage());
		paramMap.put("STATUS_CODE", "1375");// 离职人员
		paramMap.put("EMP_OFFICE", "15120");// 离职人员
		paramMap.put("PROBATION_STATUS_CODE", "('1373','1374')");// 实习

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		paramMap.put("viewNotContractDays", 8);// 未签合同
		paramMap.put("viewProbationDays", 8);// 试用转正 显示数量
		paramMap.put("viewContractDays", 8);// 到期合同 显示数量
		paramMap.put("alertProbationDays", 30);// 转正日期提醒天数
		paramMap.put("alertContractDays", 60);// 到期合同提醒天数
		paramMap.put("alertUpgradeDays", 30);// 人事令提醒天数
		paramMap.put("alertNotContractDays", 0);// 未签合同提醒天数
		Map<String, Object> map = new HashMap<String, Object>();

		// 生日列表 当月
		paramMap.put("DEPTNO", admin.getDeptNo());
		List getBirthdayList = loginStr.getBirthdayList(paramMap);
		int getBirthdayListCnt = getBirthdayList.size();
		map.put("getBirthdayList", getBirthdayList);
		map.put("getBirthdayListCnt", getBirthdayListCnt);

		map.put("statusCode", "200");
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendEmailPage", method = RequestMethod.GET)
	public ModelAndView sendEmailPage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String date = request.getParameter("date");

		modelMap.put("email", email);
		modelMap.put("name", name);
		modelMap.put("date", date);

		return new ModelAndView("/login/sendEmailPage", modelMap);

	}

	/**
	 * 发送邮件
	 * 
	 * @param request
	 * @author wendi
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendEmail")
	@ResponseBody
	public Map sendEmail(HttpServletRequest request) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		MailTo mailTo = new MailTo();
		Map<String, Object> map = new HashMap<String, Object>();

		int result = mailTo.sendMail(paramMap);

		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "Check成功！");// Check成功
		} else {
			map.put("statusCode", "300");
			map.put("message", "Check失败！");// Check失败
		}

		return map;
	}
	
	@RequestMapping(value = "/viewPromise", method = RequestMethod.GET)
	public ModelAndView viewPromise(HttpServletRequest request, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/login/viewPromise", modelMap);
	}
	
	@RequestMapping(value = "/viewChangePassword", method = RequestMethod.GET)
	public ModelAndView viewChangePassword(HttpServletRequest request, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/login/viewChangePassword", modelMap);
	}
	
	@RequestMapping(value = "/updatePromise")
	public void updatePromise(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		String language = request.getParameter("language");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		admin.setLanguage(language);
		loginStr.updateUserData(request, "updatePersonalDataFlag");
	}
	
	@RequestMapping(value = "/viewPromiseSendDataToKR", method = RequestMethod.GET)
	public ModelAndView viewPromiseSendDataToKR(HttpServletRequest request, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/login/viewPromiseSendDataToKR", modelMap);
	}
	
	@RequestMapping(value = "/updatePromiseSendDataToKR")
	public void updatePromiseSendDataToKR(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		String language = request.getParameter("language");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		admin.setLanguage(language);
		loginStr.updateUserData(request, "updatePersonalSendDataFlag");
	}

}
