package com.ait.ess.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.PersonInfoDao;
import com.ait.ess.service.PersonInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Service
public class PersonInfoSerImpl implements PersonInfoSer {

	Logger logger = Logger.getLogger(PersonInfoSerImpl.class);

	@Autowired
	private PersonInfoDao personInfoDao;

	/**
	 * 显示个人信息申请修改页面（View personal information apply）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getEssPersonInfo(HttpServletRequest request) throws Exception {
		Map param = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (request.getParameter("PERSON_ID") == null){
			param.put("PERSON_ID", admin.getPersonId());
		}
		return personInfoDao.getEssPersonInfo(param);
	}

	/**
	 * 显示个人信息申请修改页面（View personal information apply）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getHrPersonInfo(HttpServletRequest request) throws Exception {
		Map param = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (request.getParameter("PERSON_ID") == null){
			param.put("PERSON_ID", admin.getPersonId());
		}
		return personInfoDao.getHrPersonInfo(param);
	}

	/**
	 * 查询毕业学校
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getEducationInfoList(HttpServletRequest request)
			throws Exception {
		Map param = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		param.put("EMPID", admin.getAdminID());
		return personInfoDao.getEducationInfoList(param, Integer
				.parseInt(request.getParameter("page").toString()), Integer
				.parseInt(request.getParameter("pagesize").toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getEvaluateforList(HttpServletRequest request) throws Exception {
		Map param = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		param.put("EMPID", admin.getAdminID());
		return personInfoDao.getEvaluateforList(param, Integer.parseInt(request
				.getParameter("page").toString()), Integer.parseInt(request
				.getParameter("pagesize").toString()));
	}

}
