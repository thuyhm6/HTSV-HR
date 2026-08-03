package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.HomeParamDao;
import com.ait.sys.service.HomeParamSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC  (c)
 * Company:     LDCC 
 * @fileName HomeParamSerImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-3-19 pm 05:47:54
 * @version 5.0
 */
@Service
public class HomeParamSerImpl implements HomeParamSer{

	Logger logger = Logger.getLogger(HomeParamSerImpl.class);
	
	@Autowired
	private HomeParamDao homeParamDao;
	
	@SuppressWarnings("unchecked")
	public List getHomeParamList(HttpServletRequest request){
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnList = this.homeParamDao.getHomeParamList(paramMap) ;
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public Object getHomeParam(HttpServletRequest request){
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = this.homeParamDao.getHomeParam(paramMap) ;
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	public int updateHomeParamInfo(HttpServletRequest request){
		int resultInt=0;
		try{
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			String[] roleIds=request.getParameterValues("ROLE_IDS");
			String paramValue=StringUtils.join(roleIds,",");
			paramMap.put("PARAM_VALUE", paramValue);
			 
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("CREATED_BY", admin.getAdminID());
			this.homeParamDao.updateHomeParamInfo(paramMap);
			resultInt=1;
		}catch(Exception e){
			resultInt=0;
			e.printStackTrace();
		}
		return resultInt;
	}
	
	@SuppressWarnings("unchecked")
	public List getCpnyList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnList = this.homeParamDao.getCpnyList(paramMap) ;
		return returnList;
	}
	/**
	 * 查看已经为该法人设置过了的首页参数
	 */
	@SuppressWarnings("unchecked")
	public List getHomeCheckParamList(HttpServletRequest request){
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnList = this.homeParamDao.getHomeCheckParamList(paramMap) ;
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public int updateHomeCheckParamInfo(HttpServletRequest request){
		int resultInt=0;
		try{
			Object obj=request.getParameterValues("PARAM_NOS");
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("PARAM_NOS", obj);	
			this.homeParamDao.updateHomeCheckParamInfo(paramMap);
			resultInt=1;
		}catch(Exception e){
			resultInt=0;
			e.printStackTrace();
		}
		return resultInt;
	}
	@SuppressWarnings("unchecked")
	public List getRoleListByCpnyId(HttpServletRequest request){
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		Object homeParam=this.homeParamDao.getHomeParamCheck(paramMap);
		String paramValues="";
		if(homeParam!=null){
			paramValues=((Map)homeParam).get("PARAM_VALUE")==null?"":((Map)homeParam).get("PARAM_VALUE").toString();
		}
		String[] roleIds=paramValues.split(",");
		returnList = this.homeParamDao.getRoleListByCpnyId(paramMap) ;
		if(!paramValues.equals("")&&roleIds.length>0){
			for(int i=0;i<returnList.size();i++){
				String roleId=((Map)returnList.get(i)).get("ROLE_ID").toString();
				for(int j=0;j<roleIds.length;j++){
					if(roleId.equals(roleIds[j])){
						((Map)returnList.get(i)).put("CHECKED", "true");
						break;
					} 
				}
			}
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmpTypeListByCpnyId(HttpServletRequest request){
		List empTypeList = new ArrayList() ;
		List empTypeParamList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		empTypeList = this.homeParamDao.getEmpTypeListByCpnyId(paramMap) ;
		empTypeParamList = this.homeParamDao.getEmpTypeParamListByCpnyId(paramMap) ;
		
		if(empTypeParamList!=null && empTypeParamList.size()>0){
			for(int i=0;i<empTypeParamList.size();i++){
				String empTypeParam=((Map)empTypeParamList.get(i)).get("EMP_TYPE_CODE").toString();
				for(int j=0;j<empTypeList.size();j++){
					String empType=((Map)empTypeList.get(j)).get("EMP_TYPE_CODE").toString();
					if(empTypeParam.equals(empType)){
						((Map)empTypeList.get(j)).put("CHECKED", "true");
						break;
					} 
				}
			}
		}
		return empTypeList;
	}
	
	@SuppressWarnings("unchecked")
	public int updateHomeParamCpnyInfo(HttpServletRequest request){
		int resultInt=0;
		try{
			String[] roleIds=request.getParameterValues("ROLE_IDS");
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("ROLEIDS", roleIds);
			this.homeParamDao.updateHomeParamCpnyInfo(paramMap);

			String paramNo = paramMap.get("PARAM_NO")!=null?paramMap.get("PARAM_NO").toString():"";
			//目前为止首页参数设置跟员工类型有关的只有生日列表一个参数，故此设置
			if(!"".equals(paramNo) && paramNo.equals("15817")){
				String[] empTypeCodes=request.getParameterValues("EMP_TYPE_CODES");
				paramMap.remove("ROLEIDS");
				//2013-10-24 此时只有生日列表跟员工类型挂钩，若其它参数也需要修改，此参数从页面获取即可
				paramMap.put("PARAM_NO", "15817");
				//首先删除此页面参数的所有员工类型
				this.homeParamDao.deleteHomeParamCpnyAndEmpTypeInfo(paramMap);
				for(int i=0;i<empTypeCodes.length;i++){
					String empType = empTypeCodes[i]!=null?empTypeCodes[i].toString():"";
					paramMap.remove("EMP_TYPE");
					paramMap.put("EMP_TYPE", empType);
					//删除所有员工类型之后，插入重新选择的员工类型
					this.homeParamDao.insertHomeParamCpnyAndEmpTypeInfo(paramMap);
				}
			}
			resultInt=1;
		}catch(Exception e){
			resultInt=0;
			e.printStackTrace();
		}
		return resultInt;
	}
}
