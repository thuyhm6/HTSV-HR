package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.HrmAffirmDao;
import com.ait.sys.service.HrmAffirmSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;


/**
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName HrmAffirmSerImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-2-9 下午06:11:44
 * @version 5.0
 * 
 */
@Service
public class HrmAffirmSerImpl implements HrmAffirmSer{
	
	Logger logger = Logger.getLogger(HrmAffirmSerImpl.class);

	@Autowired
	private HrmAffirmDao hrmAffirmDao;
	
	@SuppressWarnings("unchecked")
	public List getHrmAffirmList(HttpServletRequest request){
		
		List returnList = new ArrayList() ;
		
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		if (UiUtil.getPageNum(request) > 0){
			returnList = hrmAffirmDao.getHrmAffirmList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}
		else{
			returnList = hrmAffirmDao.getHrmAffirmList(paramMap) ;
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	public int getHrmAffirmListCnt(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return hrmAffirmDao.getHrmAffirmListCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	public int saveHrmAffirmInfo(HttpServletRequest request){
		int result=0;
		try{
			AdminBean admin=SessionUtil.getLoginUserFromSession(request);
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			String[] postNos=request.getParameterValues("AFFIRMOR_NO");
			paramMap.put("CREATED_BY", admin.getAdminID());
			this.hrmAffirmDao.saveHrmAffirmInfo(paramMap,postNos); 
			result=1;
		}catch(Exception e){
			e.printStackTrace();
			result=0;
		}
		return result;
	}
	/**
	 * 获取申请类型
	 */
	@SuppressWarnings("unchecked")
	public List getApplyList(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return hrmAffirmDao.getApplyList(paramMap);
	}
	/**
	 * 获取职责类型
	 */
	@SuppressWarnings("unchecked")
	public List getDutyList(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return hrmAffirmDao.getDutyList(paramMap);
	}
	/**
	 * 获取职务类型
	 */
	@SuppressWarnings("unchecked")
	public List getPostList(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return hrmAffirmDao.getPostList(paramMap);
	}
	/**
	 * 列表页面查看详细
	 */
	@SuppressWarnings("unchecked")
	public List getDetailParamList(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return hrmAffirmDao.getDetailParamList(paramMap);
	}
	@SuppressWarnings("unchecked")
	public int deleteHrmAffirmInfo(HttpServletRequest request){
		int result=0;
		try {
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			this.hrmAffirmDao.deleteHrmAffirmInfo(paramMap);
			result=1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public Object getLeaveApplyParam (HttpServletRequest request){
		Object returnObj = new Object() ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		
		returnObj = this.hrmAffirmDao.getLeaveApplyParam(paramMap) ;
		
		
		return returnObj ;
	}
	
	/**
	 * 提交表单时验证符合条件(申请人的dutyNo,申请类型，以及法人)的决裁流程是否已经设置
	 */
	@SuppressWarnings("unchecked")
	public int validateExistsDutyApplyTypeCpnyId(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.hrmAffirmDao.validateExistsDutyApplyTypeCpnyId(paramMap); 
	}
}
