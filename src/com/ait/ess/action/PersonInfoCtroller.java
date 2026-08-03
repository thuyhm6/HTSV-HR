package com.ait.ess.action;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.service.PersonInfoSer;
import com.ait.hrm.action.TransferOrderCtroller;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.BeanUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UserConfiguration;

/**
 * 个人信息申请(View personal information apply)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: PersonInfoCtroller.java
 * @Description:
 * @Create date: Feb 20, 2012 1:34:55 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 20, 2012 1:34:55 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/personinfo")
public class PersonInfoCtroller {

	Logger logger = Logger.getLogger(TransferOrderCtroller.class);

	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	@Autowired
	private EmpInfoSer empInfoSer;

	@Autowired
	private PersonInfoSer personInfoSer;

	/**
	 * 显示个人信息申请修改页面（View personal information apply）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonInfo")
	public ModelAndView getPersonInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		//modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID+ EMPID + ".jpg");
		modelMap.put("photoId", "essPersonalInfoView");
		//modelMap.put("personalInfo", this.personInfoSer.getHrPersonInfo(request));
		return new ModelAndView("/ess/personinfo/viewPersonInfo", modelMap);
	}
}
