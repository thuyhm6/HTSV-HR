package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.AttendItemDao;
import com.ait.sys.dao.AttendItemMappingDao;
import com.ait.sys.service.AttendItemSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class AttendItemSerImpl implements AttendItemSer{
Logger logger = Logger.getLogger(AttendItemSerImpl.class);
	
	@Autowired
	private AttendItemDao attenditemDao;
	
	//离职申请类型代码
		private static String APPLY_TYPE_NO = "1102";
		
		@Autowired
		private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
		
		@Autowired
		private AttendItemMappingDao attendItemMappingDao;

	@Override
	public List getAttendItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PROJECT_TYPE", request.getParameter("PROJECT_TYPE"));
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					attenditemDao.getAttendItemList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = attenditemDao.getAttendItemList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 需要决裁的(决裁查看页面)
	 */
	public List getAffirmAttendItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PROJECT_TYPE", request.getParameter("PROJECT_TYPE"));
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				attenditemDao.getAffirmAttendItemList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = attenditemDao.getAffirmAttendItemList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 需要决裁的(决裁页面)
	 */
	public List getAffirmAttendList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		String personId = request.getParameter("personId");
		Map paramMap = new LinkedHashMap();
		if(personId == null || "".equals(personId)){
		    paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		    paramMap.put("PROJECT_TYPE", request.getParameter("PROJECT_TYPE"));
			paramMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		}else{
			paramMap.put("interLanguage", request.getParameter("LANGUAGE"));
			paramMap.put("ITEM_NO", request.getParameter("APPLY_NO"));
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				attenditemDao.getAffirmAttendList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = attenditemDao.getAffirmAttendList(paramMap) ;
		}
		
		return retrunList ;
	}


	@Override
	public int getAttendItemCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		retrunInt = attenditemDao.getAttendItemCnt(paramMap) ;
		
		return retrunInt ;
	}
	/**
	 *需要决裁的（决裁查看页面）
	 */
	@Override
	public int getAffirmAttendItemCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunInt = attenditemDao.getAffirmAttendItemCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	/**
	 *需要决裁的（决裁页面）
	 */
	@Override
	public int getAffirmAttendCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		retrunInt = attenditemDao.getAffirmAttendCnt(paramMap) ;
		
		return retrunInt ;
	}


	@Override
	public Object getAttendItemInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.remove("sortname") ;
		returnObj = attenditemDao.getAttendItemInfo(paramMap) ;
		return returnObj ;
	}
	
	/**
	 * 决裁通过（决裁页面）
	 */
	public int updateAffirmAttendInfo(HttpServletRequest request){
		LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			// 页面提交数据
			paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		}else{
			paramMap.put("UPDATED_BY", personId) ;
		}
		String NO = request.getParameter("ITEM_NO");
		paramMap.put("ACTIVITY", request.getParameter("flag"));
		paramMap.put("AFFIRM_DESCR", request.getParameter("AFFIRM_DESCR"));
		paramMap.put("CPNY_ID", request.getParameter("cpny"));
		if(NO !=null && !"".equals(NO)){
			paramMap.put("NO", NO);
		}
		  try {
			  this.attenditemDao.updateAffirmAttendInfo(paramMap) ;
			//审批发送LGEP
			  paramMap.put("FLAG", "1");
			  paramMap.put("APPLY_NO", NO);
			  List list = new ArrayList();
				list = this.attendItemMappingDao.findAfirmorByRelation();
				LinkedHashMap affirmor = new LinkedHashMap();
				if(list != null && list.size()>0){
					affirmor = (LinkedHashMap) list.get(0);
				}
				paramMap.put("AFFIRM_FLAG",paramMap.get("ACTIVITY"));
				paramMap.put("CURRENT_AFFIRM_ID",affirmor.get("PERSON_ID"));
			  sendToLGEP(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
	}
	
	/**
	 * 审批后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEP(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			if("0".equals(paramMap.get("FLAG").toString())){
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{//决裁完成\
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_FLAG"));
				lgepMap.put("AFFIRM_LEVEL", "1");
			}
			lgepMap.put("AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CREATED_BY", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewAffirmArItemInfo?LGEP=LGEP&LANGUAGE=zh&personId=123" + "&APPLY_NO=" + paramMap.get("APPLY_NO") + "&CPNY_ID=" + paramMap.get("CPNY_ID"));
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewAffirmArItemInfo?LGEP=LGEP&LANGUAGE=zh&personId=123" + "&APPLY_NO=" + paramMap.get("APPLY_NO") + "&CPNY_ID=" + paramMap.get("CPNY_ID"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewAffirmArItemInfo?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO") + "&CPNY_ID=" + paramMap.get("CPNY_ID"));
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}

	
	/**
	 * 没有决裁的申请记录可以被删除（决裁查看页面）
	 */
	public int deleteAffirmAttendItemInfo(HttpServletRequest request){
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		try{
			paramMap.put("CPNY_ID", admin.getCpnyId());
			this.attenditemDao.deleteAffirmAttendItemInfo(paramMap);
			String ITEM_NO = paramMap.get("NO").toString();
			this.deleteSendToLGEP(ITEM_NO);
		}
		catch(Exception e){
			e.printStackTrace() ;
			return 0;
		}
		
		return 1;
	}

	/**
	 * 删除发送LGEP
	 * @param eventId
	 */
	private void deleteSendToLGEP(String ITEM_NO){
		LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_NO",ITEM_NO);
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			this.affirmInfoToLGEPSer.deleteAffirm(lgepMap);
	}
}
