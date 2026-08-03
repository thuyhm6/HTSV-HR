package com.ait.ess.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.service.AbsenteeismSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * @fileName: AbsenteeismCtroller
 * @Description:旷工申请 
 */
@Service
public class AbsenteeismSerImpl implements AbsenteeismSer {

	Logger logger = Logger.getLogger(AbsenteeismSerImpl.class);

	@Autowired
	private AuthorityUtil authorityUtil;
	
	/**
	 * 旷工申请 信息查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAbsenteeismList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("FLAG") == null || !"1".equals(paramMap.get("FLAG").toString())){
			paramMap.put("KEY", admin.getEmpID());
		}
		paramMap.put("specialParam",admin.getSpecialParam());//这个参数判断用户是普通用户还是特殊用户
		paramMap.put("deptNo",admin.getDeptNo());//判断部门权限用
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		return returnList;
	}
}
