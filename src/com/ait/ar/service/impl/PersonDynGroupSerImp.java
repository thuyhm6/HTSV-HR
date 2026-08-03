package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ar.dao.PersonDynGroupDao;
import com.ait.ar.dao.ShiftDao;
import com.ait.ar.service.PersonDynGroupSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PersonDynGroupSerImp.java
 * @Description:
 * @Create date: 2012-1-17 下午03:00:02
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class PersonDynGroupSerImp implements PersonDynGroupSer {

	Logger logger = Logger.getLogger(PersonDynGroupSerImp.class);	
	@Autowired
	private PersonDynGroupDao PersonDynGroupDao;
	@Autowired
	private ShiftDao shiftDao;
	
	/**
	 * 取得动态组信息(get PersonDyn Group)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPersonDynGroup(HttpServletRequest request) {		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.PersonDynGroupDao.getPersonDynGroup(paramMap) ; 
	}
	
	/**
	 * 查看动态组列表(get PersonDynGroup List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;

		retrunList = PersonDynGroupDao.getPersonDynGroupList(paramMap) ;
		
		return retrunList ;
	}
	
	/**
	 * 查看动态组列表(get PersonDynGroup List)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getPersonDynGroup1List(HttpServletRequest request) {
		List retrunList = new ArrayList() ;	
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("DEPT_DISTINGUISH_NO", "1");
		paramMap.put("PERSON_ID", admin.getPersonId());
		retrunList = PersonDynGroupDao.getPersonDynGroupList(paramMap) ;		
		return retrunList ;
	}
	
	/**
	 * 添加动态组(add PersonDynGroup)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int addPersonDynGroupInfo(HttpServletRequest request){
		
		//页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//创建人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		LinkedHashMap tempMap = new LinkedHashMap() ;
		tempMap.put("PERSON_ID", admin.getPersonId());
		String distinguishNo = "";
		distinguishNo = this.shiftDao.getDeptDistinguishNo(tempMap) ;
		
		paramMap.put("distinguishNo", distinguishNo);
		
		try {
			
			this.PersonDynGroupDao.addPersonDynGroupInfo(paramMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 更新动态组信息(update PersonDynGroup Info)
	 * @param request
	 * @return int 
	 */
	@SuppressWarnings("unchecked")
	public int updatePersonDynGroupInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		//修改人
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			
			this.PersonDynGroupDao.updatePersonDynGroupInfo(paramMap) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除动态组信息(delete Cycle Info)
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int deletePersonDynGroupInfo(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		try {
			
			this.PersonDynGroupDao.deletePersonDynGroupInfo(paramMap) ;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	@SuppressWarnings("unchecked")
	public List getDeptList (HttpServletRequest request) {
		
           Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return PersonDynGroupDao.getLoginUserDeptList(paramMap) ;
		
		 
	}

	/**
	 * 查看动态组人员信息(get PersonDynGroupInfo List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupInfoList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				PersonDynGroupDao.getPersonDynGroupInfoList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = PersonDynGroupDao.getPersonDynGroupInfoList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 查看人员列表(get PersonDynGroupEmp List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getPersonDynGroupEmpList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		paramMap.put("AR_GROUP_NO", request.getParameter("NO")!=null?request.getParameter("NO").toString():"");
		paramMap.put("CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"C00");
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				PersonDynGroupDao.getPersonDynGroupEmpList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = PersonDynGroupDao.getPersonDynGroupEmpList(paramMap);
		}
		
		return retrunList;
	}
	
	/**
	 * 查询人员数量(get PersonDynGroupEmp count)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getPersonDynGroupEmpCnt(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		paramMap.put("AR_GROUP_NO", request.getParameter("NO")!=null?request.getParameter("NO").toString():"");
		paramMap.put("CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"C00");
		return PersonDynGroupDao.getPersonDynGroupEmpCnt(paramMap);
	}
	
	/**
	 * 查询动态组人员数量(get PersonDynGroupEmp count)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int getPersonDynGroupInfoCnt(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		return PersonDynGroupDao.getPersonDynGroupInfoCnt(paramMap);
	}

	/**
	 * 添加动态组人员信息(add PersonDynGroup Person)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int addPersonDynGroupPerson(HttpServletRequest request) {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String[] person_id = request.getParameterValues("c2");
		String group_no = paramMap.get("NO").toString();
		List PersonDynList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		try {
			
			if(!group_no.equals("") && person_id != null){
				for(int i=0;i<person_id.length;i++)
				{
					LinkedHashMap map=new LinkedHashMap(); 
					map.put("PERSON_ID", person_id[i]);
					map.put("GROUP_NO", group_no);
					map.put("CREATED_BY", admin.getPersonId());
					 if(PersonDynGroupDao.checkPersonDynGroupPerson(map)==0)
					 {	 
						 PersonDynList.add(map);
					 }
				}
				
				PersonDynGroupDao.addPersonDynGroupPerson(PersonDynList);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	/**
	 * 删除动态组人员(delete PersonDynGroup Person)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int deletePersonDynGroupPerson(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		List list=new ArrayList();
		String[] person_id = request.getParameterValues("c1");
		String no = paramMap.get("NO").toString() ;

		try {
			if(person_id.length > 0){
				for(int i=0;i<person_id.length;i++){
					LinkedHashMap map=new LinkedHashMap(); 
					map.put("PERSON_ID", person_id[i]);
					map.put("NO", no);
					list.add(map);
				}
			}
			
			this.PersonDynGroupDao.deletePersonDynGroupPerson(list) ;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
}
