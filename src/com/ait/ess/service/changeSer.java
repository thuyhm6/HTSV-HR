package com.ait.ess.service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface changeSer {
	public String findUser(HttpServletRequest request,HttpServletResponse response);

	public int changePassword(HttpServletRequest request);
	public Object getPersonalInfoByPid(HttpServletRequest request);

}
