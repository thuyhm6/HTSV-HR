package com.ait.pa.service.imp.salary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.PaComputeItemDao;
import com.ait.pa.service.salary.PaComputeItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: PaComputeItemSerImp.java
 * @Description:
 * @Create date: 2012-1-16 下午06:45:40
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class PaComputeItemSerImp implements PaComputeItemSer {

	Logger logger = Logger.getLogger(PaComputeItemSerImp.class);

	@Autowired
	private PaComputeItemDao paComputeItemDao;

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getPaComputeItemInfo(HttpServletRequest request) {
		Object returnObj = new Object();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		returnObj = paComputeItemDao.getPaComputeItemInfo(paramMap);

		return returnObj;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaComputeItemList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("INPUT_ITEM_PARAM", request.getAttribute("INPUT_ITEM_PARAM"));
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = paComputeItemDao.getPaComputeItemList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = paComputeItemDao.getPaComputeItemList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPaComputeItemCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		retrunInt = paComputeItemDao.getPaComputeItemCnt(paramMap);
		return retrunInt;

	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "unused" })
	private LinkedHashMap setGetPaComputeItemParam(HttpServletRequest request) {
		// 从session中取得登陆用户信息
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return paramMap;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddPaComputeItemInfo(HttpServletRequest request) {

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
        paramMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		return this.paComputeItemDao.checkAddPaComputeItemInfo(paramMap);

	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addPaComputeItemInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
//		if(request.getParameter("DATA_TYPE")!=null&&!"".equals(request.getParameter("DATA_TYPE"))){
//		    paramMap.put("DATATYPE", request.getParameter("DATA_TYPE"));
//		}
//		if(request.getParameter("ACTIVITY")!=null && !"".equals(request.getParameter("ACTIVITY"))){
//			paramMap.put("ACTIVITY", request.getParameter("ACTIVITY")) ;
//		}
//		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.paComputeItemDao.addPaComputeItemInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int addPaComputeItemInfo(HttpServletRequest request,String item_no){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String personId = request.getParameter("persomId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getAdminID());
		}else{
			paramMap.put("CREATED_BY", personId);
		}
		if(request.getParameter("DATA_TYPE")!=null&&!"".equals(request.getParameter("DATA_TYPE"))){
		    paramMap.put("DATATYPE", request.getParameter("DATA_TYPE"));
		}
		if(request.getParameter("ACTIVITY")!=null && !"".equals(request.getParameter("ACTIVITY"))){
			paramMap.put("ACTIVITY", request.getParameter("ACTIVITY")) ;
		}else{
			paramMap.put("ACTIVITY",1) ;
		}
		paramMap.put("ITEM_NO", item_no);
		try {
			this.paComputeItemDao.addPaComputeItemInfoAffirm(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaComputeItemInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		if(request.getParameter("DATA_TYPE")!=null&&!"".equals(request.getParameter("DATA_TYPE"))){
		    paramMap.put("DATATYPE", request.getParameter("DATA_TYPE"));
		}
		try {
			this.paComputeItemDao.updatePaComputeItemInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeletePaComputeItemInfo(HttpServletRequest request) {
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		String TEBLENAME = "PA_SUMMARY";
		paramMap.put("TABLE_NAME", TEBLENAME);
		return this.paComputeItemDao.checkDeletePaComputeItemInfo(paramMap);
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaComputeItemInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
			this.paComputeItemDao.deletePaComputeItemInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;

	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updatePaComputeItemInfoCalOrder(HttpServletRequest request) {
		// 页面提交的JSON信息
		String jsonString = request.getParameter("jsonData");

		List<LinkedHashMap<String, Object>> summaryItemList = ObjectBindUtil
				.getRequestJsonData(jsonString);

		return this.paComputeItemDao
				.updatePaComputeItemInfoCalOrder(summaryItemList);
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePCInfoByCalcuOrder(HttpServletRequest request, int type,
			String param_no, String calcu_order) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("PARAM_NO", param_no);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		// 如果type=1 是up 那么CALCU_ORDER的值-1
		if (type == 1) {
			int order = Integer.parseInt(calcu_order);
			int calorder = order - 1;
			paramMap.put("ORDER", order);
			paramMap.put("CALORDER", calorder);
		}

		// 如果type=0 是down 那么CALCU_ORDER的值+1
		if (type == 0) {
			int order = Integer.parseInt(calcu_order);
			int calorder = order + 1;
			paramMap.put("ORDER", order);
			paramMap.put("CALORDER", calorder);
		}

		this.paComputeItemDao.updatePCInfoByCalcuOrder(paramMap);

		return 1;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePCInfoByParamNo(HttpServletRequest request, int type,
			String param_no, String calcu_order) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("PARAM_NO", param_no);
		// paramMap.put("CALCU_ORDER", calcu_order) ;

		if (type == 1) {
			int order = Integer.parseInt(calcu_order);
			int calorder = order - 1;
			paramMap.put("ORDER", order);
			paramMap.put("CALORDER", calorder);
		}

		// 如果type=0 是down 那么CALCU_ORDER的值+1
		if (type == 0) {
			int order = Integer.parseInt(calcu_order);
			int calorder = order + 1;
			paramMap.put("ORDER", order);
			paramMap.put("CALORDER", calorder);
		}

		this.paComputeItemDao.updatePCInfoByParamNo(paramMap);

		return 1;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaComputeItemParamList(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List retrunList = new ArrayList();

		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());

		retrunList = paComputeItemDao.getPaComputeItemParamList(paramMap);

		return retrunList;

	}

	
	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaComputeItemParamListYN(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List retrunList = new ArrayList();

		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());

		retrunList = paComputeItemDao.getPaComputeItemParamListYN(paramMap);

		return retrunList;

	}
	/**
	 * 为ess里的个人工资查询服务的。可以查出保险明细项目
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaComputeItemParamESSListYN(HttpServletRequest request,String str) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List retrunList = new ArrayList();

		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
        if(!"".equals(str) && str!=null){
        	paramMap.put("DATA_TYPE", str);
        }
		retrunList = paComputeItemDao.getPaComputeItemParamESSListYN(paramMap);

		return retrunList;

	}
	/**
	 * 为ess里的个人工资查询服务的。可以查出工资输入明细项目
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaParamDateList(HttpServletRequest request,String arMonth,String personId) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("dwz.person.personId")!=null){
		paramMap.put("PERSON_ID", paramMap.get("dwz.person.personId"));
		}
		else{
		paramMap.put("PERSON_ID", personId);
		}
		if(!"".equals(paramMap.get("EMP_ID")) && paramMap.get("EMP_ID")!=null){
			if("1".equals(paramMap.get("personId_flag")) && paramMap.get("personId_flag")!="1"){
			   paramMap.put("EMP_ID", paramMap.get("EMP_ID"));
			   paramMap.put("PERSON_ID_INFO",personId);
			}
	    }
		paramMap.put("arMonth", arMonth);
		retrunList = paComputeItemDao.getPaParamDateList(paramMap);

		return retrunList;

	}
	/* 
	* Title: getEssEmpId
	* Description查询工资根据personid查询
	* @author 孙鹏  
	* @date 2014年11月27日 下午2:41:06  
	* @param request
	* @return 
	* @see com.ait.pa.service.salary.PaComputeItemSer#getEssEmpId(javax.servlet.http.HttpServletRequest) 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List getEssPa(HttpServletRequest request, List getArColumnsList, String arMonth,String str){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		if(!"".equals(str) && str!=null){
			paramMap.put("type", str);
		}
		//paramMap.put("supervisor", admin.getPersonId());
		paramMap.put("supervisor", admin.getPersonId());
		if(paramMap.get("dwz.person.personId")!=null){
		      paramMap.put("PERSON_ID", paramMap.get("dwz.person.personId"));
		}else{
		      paramMap.put("PERSON_ID", admin.getPersonId());
		}
		if(!"".equals(paramMap.get("EMP_ID")) && paramMap.get("EMP_ID")!=null){
			if("1".equals(paramMap.get("personId_flag")) && paramMap.get("personId_flag")!="1"){
			   paramMap.put("EMP_ID", paramMap.get("EMP_ID"));
			   paramMap.put("PERSON_ID_INFO",admin.getPersonId());
			}
	    }
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		paramMap.put("arMonth", arMonth);
		
//		paramMap.put("sqlStatement", getArColumnsParam(request));
		
		paramMap.put("sqlStatement", this.getArColumnsParam1(getArColumnsList));
		
		retrunList = paComputeItemDao.getEssPa(paramMap) ;

		return retrunList;
		
	}
	
	/**
	 * 取工资数据(get ArMonth List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getPaMonthListYN(HttpServletRequest request, List getArColumnsList, String arMonth) {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("supervisor", admin.getPersonId());
		
		paramMap.put("arMonth", arMonth);
		
//		paramMap.put("sqlStatement", getArColumnsParam(request));
		
		paramMap.put("sqlStatement", this.getArColumnsParam1(getArColumnsList));
		
		retrunList = paComputeItemDao.getPaListYN(paramMap) ;
		 
		
		return retrunList ;
	}
	
	
	/**
	 * 拼接查询条件(get ArColumnsParam)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String getArColumnsParam1(List paramList) {
		// TODO Auto-generated method stub
		
		String paramString="";
		
		for(int i=0;i<paramList.size();i++){
	    	
	    	 LinkedHashMap map=(LinkedHashMap)paramList.get(i);
	    	 if("DEPTNO".equals(map.get("COLUMN_NAME"))){
	    		 map.remove(map.get("COLUMN_NAME"));
	    		 map.put("COLUMN_NAME","E.DEPTNO");
	    	 }
	    	      if(i == 0){
	    	    	  if("DATE_STARTED".equals(map.get("COLUMN_NAME"))){
	    	    		  paramString = ",TO_CHAR(" + map.get("COLUMN_NAME")+",'YYYY-MM-DD') DATE_STARTED"; 
	    	    	  }else if("DATE_LEFT".equals(map.get("COLUMN_NAME"))){
	    	    		  paramString = ",TO_CHAR(" + map.get("COLUMN_NAME")+",'YYYY-MM-DD') DATE_LEFT"; 
	    	    	  }else if("END_PROBATION_DATE".equals(map.get("COLUMN_NAME"))){
	    	    		  paramString = ",TO_CHAR(" + map.get("COLUMN_NAME")+",'YYYY-MM-DD') END_PROBATION_DATE"; 
	    	    	  }else {
	    	    		  paramString = "," + map.get("COLUMN_NAME");
	    	    	  }
	    		     
	    	      }else{
	    	    	  
	    	    	  if("DATE_STARTED".equals(map.get("COLUMN_NAME"))){
	    	    		  paramString+= "TO_CHAR(" + map.get("COLUMN_NAME")+",'YYYY-MM-DD') DATE_STARTED"; 
	    	    	  }else if("DATE_LEFT".equals(map.get("COLUMN_NAME"))){
	    	    		  paramString+= "TO_CHAR(" + map.get("COLUMN_NAME")+",'YYYY-MM-DD') DATE_LEFT"; 
	    	    	  }else if("END_PROBATION_DATE".equals(map.get("COLUMN_NAME"))){
	    	    		  paramString+="TO_CHAR(" + map.get("COLUMN_NAME")+",'YYYY-MM-DD') END_PROBATION_DATE"; 
	    	    	  }else{
	    	    		  paramString+=map.get("COLUMN_NAME");
	    	    	  }
	    	    	  
	    	      }
	    	 
	    	      if(i!=paramList.size()-1)
	    	      {
	    		      paramString+=",";
	    	      }
		}
		
        return paramString;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddPaComputeItemParamInfo(HttpServletRequest request) {

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", request.getParameter("cpny"));
		return this.paComputeItemDao.checkAddPaComputeItemParamInfo(paramMap);

	}
	
	public int checkAddPaComputeItemParamInfo(HttpServletRequest request,String cpny_id,String item_no){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		if(item_no != null && !"".equals(item_no)){
			paramMap.put("ITEM_NO", item_no);
		}
		return this.paComputeItemDao.checkAddPaComputeItemParamInfo(paramMap);
	}

	

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addPaComputeItemParamInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else{
			paramMap.put("CREATED_BY", personId);
			paramMap.put("CPNY_ID", request.getParameter("PERSON_ID"));
		}
		try {
			this.paComputeItemDao.addPaComputeItemParamInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;

	}
	
	
	public int addPaComputeItemParamInfo(HttpServletRequest request ,String cpny_id,String item_no){
		

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CREATED_BY", admin.getAdminID());
			
		}else{
			paramMap.put("CREATED_BY", personId);
		}
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		if(item_no != null && !"".equals(item_no)){
			paramMap.put("ITEM_NO", item_no);
		}
		try {
			this.paComputeItemDao.addPaComputeItemParamInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;

	}
	
	
	public int updatePaComputeItemParamInfo(HttpServletRequest request ,String cpny_id,String item_no){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		if(item_no != null && !"".equals(item_no)){
			paramMap.put("ITEM_NO", item_no);
		}
		try {
			this.paComputeItemDao.updatePaComputeItemParam(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;

	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int upPaComputeItemParamView(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());

		return this.paComputeItemDao.upPaComputeItemParamView(paramMap);

	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaComputeItemParamInfo(HttpServletRequest request) {

		Object returnObj = new Object();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		returnObj = paComputeItemDao.getPaComputeItemParamInfo(paramMap);

		return returnObj;

	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int upPaComputeItemParamInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());

		return this.paComputeItemDao.upPaComputeItemParamInfo(paramMap);

	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int upPaComputeItemMappingInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		return this.paComputeItemDao.upPaComputeItemMappingInfo(paramMap);
	}


	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaComputeItemParamInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
			this.paComputeItemDao.deletePaComputeItemParamInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;

	}
	
	public int updatePaComputeItemParamInfoAll(HttpServletRequest request) throws Exception{
		String item_no = request.getParameter("ITEM_NO");
		int num=0;
		if(item_no != null && !"".equals(item_no)){
		    num=this.paComputeItemDao.updatePaComputeItemParamInfoAll(item_no);
		}
		return num;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 * @SuppressWarnings("unchecked")
	 * @Override public int getPaComputeItemParamListCnt(HttpServletRequest
	 *           request) { int retrunInt = 0 ; AdminBean admin =
	 *           SessionUtil.getLoginUserFromSession(request) ; // 页面提交数据
	 *           Map<String,Object> paramMap =
	 *           ObjectBindUtil.getRequestParamData(request,"seach_") ;
	 *           paramMap.put("CPNY_ID", admin.getCpnyId()); retrunInt =
	 *           paComputeItemDao.getPaComputeItemParamListCnt(paramMap) ;
	 * 
	 *           return retrunInt ; }
	 */
	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaItemListForFormula(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = paComputeItemDao.getPaItemListForFormula(paramMap);

		return retrunList;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaItemListForDayFormula(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = paComputeItemDao.getPaItemListForDayFormula(paramMap);

		return retrunList;
	}
	
	
	@Override
	public int addShowPaComputeItemParamList(HttpServletRequest request) {
		 
		int num = 1;
		  try {
			Map paramMap = new LinkedHashMap();
			  String[] c1 = request.getParameterValues("c1");
			  for (int i = 0; i < c1.length; i++) {
				String showynkey = "showyn_"+c1[i];
				String showorderkey = "showorder_"+c1[i];
				paramMap.put("item", c1[i]);
				paramMap.put("showyn", request.getParameter(showynkey));
				paramMap.put("showorder", request.getParameter(showorderkey));
				paComputeItemDao.addShowPaComputeItemParamList(paramMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			num = 0;
			e.printStackTrace();
		}
		return  num;
	 
	}

	/* 
	* Title: getPaRemark
	* Description:查询 工资调整的备注
	* @author 孙鹏  
	* @date 2015年2月6日 上午9:18:25  
	* @param request
	* @param arMonth
	* @param personId
	* @return 
	* @see com.ait.pa.service.salary.PaComputeItemSer#getPaRemark(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String) 
	*/
	@Override
	public List getPaRemark(HttpServletRequest request, String arMonth,
			String personId) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", personId);
		paramMap.put("arMonth", arMonth);
		paramMap.put("param_item_id", "'P_ADJUST_FEE'");
		return paComputeItemDao.getPaRemark(paramMap);
	}
	
	/**
	 * 查Pn工资项目
	 * @param request
	 * @return
	 */
	public Object getLgepnPaInfo(HttpServletRequest request){
		Map retrunObj = new LinkedHashMap();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String paMonth = "";
		if(!"".equals(paramMap.get("year_ar0106")) && paramMap.get("year_ar0106")!=null && !"".equals(paramMap.get("month_ar0106")) && paramMap.get("month_ar0106")!=null){
			paMonth = paramMap.get("year_ar0106").toString()+paramMap.get("month_ar0106").toString();
		}else{
			paMonth = this.getToday("yyyy") + this.getToday("MM");
		}
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("".equals(paramMap.get("PA_MONTH")) || paramMap.get("PA_MONTH")==null){
		    paramMap.put("PA_MONTH", paMonth);
		}
		retrunObj = (Map) paComputeItemDao.getLgepnPaInfo(paramMap);
		if(!"".equals(retrunObj.get("EESH_COREMAN_FEE")) && retrunObj.get("EESH_COREMAN_FEE")!=null && retrunObj.get("EESH_COREMAN_FEE").toString()!="0"){
			paramMap.put("PARAM_ID", "EESH_COREMAN_FEE");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("EESHLIST", list);
		}
		if(!"".equals(retrunObj.get("P_TRAFFIC_FSE")) && retrunObj.get("P_TRAFFIC_FSE")!=null && retrunObj.get("P_TRAFFIC_FSE").toString()!="0"){
			paramMap.put("PARAM_ID", "P_TRAFFIC_FSE");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("P_TRAFFIC_FSELIST", list);
		}
		if(!"".equals(retrunObj.get("P_LOVE_FEE")) && retrunObj.get("P_LOVE_FEE")!=null && retrunObj.get("P_LOVE_FEE").toString()!="0"){
			paramMap.put("PARAM_ID", "P_LOVE_FEE");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("P_LOVE_FEELIST", list);
		}
		if(!"".equals(retrunObj.get("P_SUBSIDY")) && retrunObj.get("P_SUBSIDY")!=null && retrunObj.get("P_SUBSIDY").toString()!="0"){
			paramMap.put("PARAM_ID", "P_SUBSIDY");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("P_SUBSIDYLIST", list);
		}
		if(!"".equals(retrunObj.get("WORK_AGE_MONEY")) && retrunObj.get("EESH_COREMAN_FEE")!=null && retrunObj.get("WORK_AGE_MONEY").toString()!="0"){
			paramMap.put("PARAM_ID", "WORK_AGE_MONEY");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("WORK_AGE_MONEYLIST", list);
		}
		if(!"".equals(retrunObj.get("P_ADJUST_FEE")) && retrunObj.get("P_ADJUST_FEE")!=null && retrunObj.get("P_ADJUST_FEE")!="0"){
			paramMap.put("PARAM_ID", "P_ADJUST_FEE");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("P_ADJUST_FEELIST", list);
		}
		if(!"".equals(retrunObj.get("P_ADJUST_MINUS_FEE")) && retrunObj.get("P_ADJUST_MINUS_FEE")!=null && retrunObj.get("P_ADJUST_MINUS_FEE").toString()!="0"){
			paramMap.put("PARAM_ID", "P_ADJUST_MINUS_FEE");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("P_ADJUST_MINUS_FEELIST", list);
		}
		if(!"".equals(retrunObj.get("P_FIXATION_OTFEE")) && retrunObj.get("P_FIXATION_OTFEE")!=null && retrunObj.get("P_FIXATION_OTFEE").toString()!="0"){
			paramMap.put("PARAM_ID", "P_FIXATION_OTFEE");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("P_FIXATION_OTFEELIST", list);
		}
		if(!"".equals(retrunObj.get("AR_MEMBER_MONEY")) && retrunObj.get("AR_MEMBER_MONEY")!=null && retrunObj.get("AR_MEMBER_MONEY").toString()!="0"){
			paramMap.put("PARAM_ID", "AR_MEMBER_MONEY");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("AR_MEMBER_MONEYLIST", list);
		}
		if(!"".equals(retrunObj.get("P_JIABAN_MONTY")) && retrunObj.get("P_JIABAN_MONTY")!=null && retrunObj.get("P_JIABAN_MONTY").toString()!="0"){
			paramMap.put("PARAM_ID", "P_JIABAN_MONTY");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("P_JIABAN_MONTYLIST", list);
		}
		if(!"".equals(retrunObj.get("P_LEFT_FEE")) && retrunObj.get("P_LEFT_FEE")!=null && retrunObj.get("P_LEFT_FEE").toString()!="0"){
			paramMap.put("PARAM_ID", "P_LEFT_FEE");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("P_LEFT_FEELIST", list);
		}
		if(!"".equals(retrunObj.get("P_PD_FEE")) && retrunObj.get("P_PD_FEE")!=null && retrunObj.get("P_PD_FEE").toString()!="0"){
			paramMap.put("PARAM_ID", "P_PD_FEE");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("P_PD_FEELIST", list);
		}
		if(!"".equals(retrunObj.get("P_HOUSE_FEE")) && retrunObj.get("P_HOUSE_FEE")!=null && retrunObj.get("P_HOUSE_FEE").toString()!="0"){
			paramMap.put("PARAM_ID", "P_HOUSE_FEE");
			List list = this.paComputeItemDao.getLgepnPaParamInfo(paramMap);
			retrunObj.put("P_HOUSE_FEELIST", list);
		}
		return retrunObj;
	}
	/**
	 * 根据格式参数返回当前日期
	 * @param pOutformat
	 * @return String
	 */
    private String getToday(String pOutformat) {

        SimpleDateFormat pOutformatter = new SimpleDateFormat(pOutformat,
                java.util.Locale.CHINA);

        String rDateString = null;
        Date vDate = new Date();

        try {
            rDateString = pOutformatter.format(vDate);

        } catch (Exception e) {
        }

        return rDateString;
    }

	/* 
	* Title: getEmpTypeCodeForPersonId
	* Description:查询人员类型
	* @author 孙鹏  
	* @date 2015年3月27日 下午1:47:38  
	* @param request
	* @param personId
	* @return 
	* @see com.ait.pa.service.salary.PaComputeItemSer#getEmpTypeCodeForPersonId(javax.servlet.http.HttpServletRequest, java.lang.String) 
	*/
	@Override
	public List getEmpTypeCodeForPersonId(HttpServletRequest request,
			String personId) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("PERSON_ID", personId);
		return paComputeItemDao.getEmpTypeCodeForPersonId(paramMap);
	}
	
}
