package com.ait.pa.service.imp.salary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.PaInputItemDao;
import com.ait.pa.dao.PaInputItemParamDao;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class PaInputItemParamSerImp implements PaInputItemParamSer {

	Logger logger = Logger.getLogger(PaInputItemParamSerImp.class);
	
	@Autowired
	private PaInputItemParamDao paInputItemParamDao;

	@Autowired
	private PaInputItemDao paInputItemDao;
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addPaInputItemParamInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub

		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		try {
			this.paInputItemParamDao.addPaInputItemParamInfo(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1; 
		
	}
	
    
	
	
	public int addPaInputItemParamInfo(HttpServletRequest request,String cpny_id,String param_item_no){
		
		// 页面提交数据
	    LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
	    String personId = request.getParameter("personId");
	    if(personId == null || "".equals(personId)){
	    	AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
	    	paramMap.put("CREATED_BY", admin.getAdminID()) ;
	    }else{
	    	paramMap.put("CREATED_BY", personId) ;
	    }
	    if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		if(param_item_no != null && !"".equals(param_item_no)){
			paramMap.put("PARAM_ITEM_NO", param_item_no);
		}
		paramMap.put("DISTINCT_FIELD","PERSON_ID") ;
		
		paramMap.put("ACTIVITY", 1) ;
		try {
			this.paInputItemParamDao.addPaInputItemParamInfo(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
			}
		return 1; 
	}
	
	public int updatePaInputItemParamInfo(HttpServletRequest request,String cpny_id,String param_item_no){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
	    LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
	    if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		if(param_item_no != null && !"".equals(param_item_no)){
			paramMap.put("PARAM_ITEM_NO", param_item_no);
		}
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		try {
			this.paInputItemParamDao.updatePaInputItemParam(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
			}
		return 1; 
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddPaInputItemParamInfo(HttpServletRequest request) {
		@SuppressWarnings("unused")
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", request.getParameter("cpny"));
		return this.paInputItemParamDao.checkAddPaInputItemParamInfo(paramMap) ;
	}
	
	public int checkAddPaInputItemParamInfo(HttpServletRequest request,String cpny_id,String param_item_no){
		@SuppressWarnings("unused")
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		if(param_item_no != null && !"".equals(param_item_no)){
			paramMap.put("PARAM_ITEM_NO", param_item_no);
		}
		return this.paInputItemParamDao.checkAddPaInputItemParamInfo(paramMap) ;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaInputItemParamInfo(HttpServletRequest request) {
		
		@SuppressWarnings("unused")
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try{
			 this.paInputItemParamDao.deletePaInputItemParamInfo(paramMap) ;
		}
		catch(Exception e){
			e.printStackTrace() ;
			return 0;
		}
		return 1;
	}
	
	/**
	 * 根据param_item_id删除所有记录
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaInputItemParamInfoAll(HttpServletRequest request) throws Exception{
		String item_no = request.getParameter("ITEM_NO");
		return this.paInputItemParamDao.updatePaInputItemParamInfoAll(item_no);
	}

	

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaInputItemParamInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Object returnObj = new Object() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		returnObj = paInputItemParamDao.getPaInputItemParamInfo(paramMap) ;
		
		return returnObj ;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaInputItemParamDataInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Object returnObj = new Object() ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		returnObj = paInputItemParamDao.getPaInputItemParamInfo(paramMap) ;
		
		
		return returnObj ;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemParamList(HttpServletRequest request) {

		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				paInputItemParamDao.getPaInputItemParamList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = paInputItemParamDao.getPaInputItemParamList(paramMap) ;
		}
		
		return retrunList ;

	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemParamListNotPageNum(HttpServletRequest request) {

		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("ACTIVITY", "1");
		
		if (request.getAttribute("FSE_FLAG") != null && "Y".equals(request.getAttribute("FSE_FLAG"))) {
			paramMap.put("FSE_FLAG", "Y");
		}else {
			paramMap.put("FSE_FLAG", "N");
		}
		retrunList = paInputItemParamDao.getPaInputItemParamList(paramMap) ;
		
		return retrunList ;

	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPaInputItemParamCnt(HttpServletRequest request) {
		
		int retrunInt = 0 ;
		
		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = paInputItemParamDao.getPaInputItemParamCnt(paramMap) ;
		
		return retrunInt ;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaInputItemParamInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		
		//因页面未获取到值时，取值 当前登陆者的法人代码
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		
		try {
			this.paInputItemParamDao.updatePaInputItemParamInfo(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;	
	}

	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaInputItemMappingInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		try {
			this.paInputItemParamDao.updatePaInputItemMappingInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;	
	}
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeletePaInputItemParamInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.paInputItemParamDao.checkDeletePaInputItemParamInfo(paramMap) ;
		
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeletePaInputItemParamInfoSummary(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.paInputItemParamDao.checkDeletePaInputItemParamInfoSummary(paramMap) ;
		
	}


	/**
	 * 批量删除输入项目参数
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int deleteCheckPaInputItemData(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
	 
		List list=new ArrayList();
		String[] PARAM_DATA_NOS = request.getParameterValues("c1");
		try {
			if(PARAM_DATA_NOS.length > 0){
				for(int i=0;i<PARAM_DATA_NOS.length;i++){
					LinkedHashMap map=new LinkedHashMap();
					map.put("PARAM_DATA_NO", PARAM_DATA_NOS[i]);
					map.put("UPDATED_BY", paramMap.get("adminID"));
					map.put("UPDATED_IP", paramMap.get("adminIP"));
					list.add(map);
					this.paInputItemParamDao.deleteCheckPaInputItemData(list) ;
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 清除输入项目参数
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int clearPaInputItemDataCallback(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String PARAM_NO = request.getParameter("seach_PARAM_NO");
		try {
				LinkedHashMap map=new LinkedHashMap();
				map.put("PARAM_NO", PARAM_NO);
				map.put("UPDATED_BY", paramMap.get("adminID"));
				map.put("UPDATED_IP", paramMap.get("adminIP"));
				this.paInputItemParamDao.clearPaInputItemDataCallback(map) ;	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 批量删除输入项目参数
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int deleteCheckPaInputItemDataOther(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
	 
		List list=new ArrayList();
		String[] PARAM_DATA_NOS = request.getParameterValues("c1");
		try {
			if(PARAM_DATA_NOS.length > 0){
				for(int i=0;i<PARAM_DATA_NOS.length;i++){
					LinkedHashMap map=new LinkedHashMap();
					map.put("PARAM_DATA_NO", PARAM_DATA_NOS[i]);
					map.put("UPDATED_BY", paramMap.get("adminID"));
					map.put("UPDATED_IP", paramMap.get("adminIP"));
					this.paInputItemDao.deletePaInputItemDataInfoType(map);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 清除输入项目参数
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int clearCheckPaInputItemDataOther(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String PARAM_NO = request.getParameter("seach_PARAM_NO");
		try {
				LinkedHashMap map=new LinkedHashMap();
				map.put("PARAM_NO", PARAM_NO);
				map.put("UPDATED_BY", paramMap.get("adminID"));
				map.put("UPDATED_IP", paramMap.get("adminIP"));
				this.paInputItemDao.clearPaInputItemDataInfoType(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}


    /**
     * 
     * 基础项目数据参数批量删除
     * 
     */

	@SuppressWarnings("unchecked")
	public int deleteCheckPaBasicItemData(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		List list=new ArrayList();
		String[] BASIC_DATA_NOS = request.getParameterValues("c1");
		try {
			if(BASIC_DATA_NOS.length > 0){
				for(int i=0;i<BASIC_DATA_NOS.length;i++){
					LinkedHashMap map=new LinkedHashMap(); 
					map.put("BASIC_DATA_NO", BASIC_DATA_NOS[i]);
					map.put("UPDATE_DATE", "SYSDATE");
					map.put("UPDATED_BY", admin.getPersonId());
					list.add(map);
					this.paInputItemParamDao.deleteCheckPaBasicItemData(list) ;
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}


	/**
	 * TA FSE批量删除输入项目参数
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public int deleteCheckPaInputItemDataFSE(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		List list=new ArrayList();
		String[] PARAM_DATA_NOS = request.getParameterValues("c1");
		try {
			if(PARAM_DATA_NOS.length > 0){
				for(int i=0;i<PARAM_DATA_NOS.length;i++){
					LinkedHashMap map=new LinkedHashMap(); 
					map.put("PARAM_DATA_NO", PARAM_DATA_NOS[i]);
					list.add(map);
				}
			}
			this.paInputItemParamDao.deleteCheckPaInputItemDataFSE(list) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	}
	
	
