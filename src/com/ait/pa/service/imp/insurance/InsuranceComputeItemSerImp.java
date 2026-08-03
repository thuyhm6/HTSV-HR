package com.ait.pa.service.imp.insurance;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.InsuranceComputeItemDao;
import com.ait.pa.service.insurance.InsuranceComputeItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceComputeItemSerImp.java
 * @Description:
 * @Create date: 2012-1-16 下午06:55:22
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class InsuranceComputeItemSerImp implements InsuranceComputeItemSer {

	Logger logger = Logger.getLogger(InsuranceComputeItemSerImp.class);
	
	@Autowired
	private InsuranceComputeItemDao insuranceComputeItemDao;
	
	/****
	 * 保险项目和老系统mapping关系修改
	 * @param request
	 * @return
	 */
	public int updateIsItemParamInfo(HttpServletRequest request){
        AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		try {
			this.insuranceComputeItemDao.updateIsItemParamInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 获取保险计算项目信息（get Insurance Compute Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getInsuranceComputeItemInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
	
		returnObj = insuranceComputeItemDao.getInsuranceComputeItemInfo(paramMap) ;
		
		return returnObj ;
	}
	
	/**
	 * 获取保险计算项目参数信息（get Insurance Compute Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getInsuranceComputeItemParamInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
	
		returnObj = insuranceComputeItemDao.getInsuranceComputeItemParamInfo(paramMap) ;
		
		return returnObj ;
	}
	
	/**
	 * 获取保险计算项目列表（get Insurance Compute Item List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());

		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				insuranceComputeItemDao.getInsuranceComputeItemList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = insuranceComputeItemDao.getInsuranceComputeItemList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 获取保险计算项目参数列表（get Insurance Compute Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemParamList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		retrunList = insuranceComputeItemDao.getInsuranceComputeItemParamList(paramMap) ;
		
		return retrunList ;
	}
	
	/**
	 * 获取保险计算项目参数列表（get Insurance Compute Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceComputeItemParamListForFormula(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		retrunList = insuranceComputeItemDao.getInsuranceComputeItemParamList(paramMap) ;
		
		
		return retrunList ;
	}
	
	/**
	 * 获取保险计算项目个数（get Insurance Compute Item Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getInsuranceComputeItemCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		// 页面提交数据
		LinkedHashMap paramMap = this.setGetInsuranceComputeItemParam(request) ;

		retrunInt = insuranceComputeItemDao.getInsuranceComputeItemCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	/**
	 * 获取保险计算项目参数个数（get Insurance Compute Item Param Cnt）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 
	@SuppressWarnings("unchecked")
	public int getInsuranceComputeItemParamCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		// 页面提交数据
		LinkedHashMap paramMap = this.setGetInsuranceComputeItemParam(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunInt = insuranceComputeItemDao.getInsuranceComputeItemParamListCnt(paramMap) ;
		
		return retrunInt ;
	}*/
	
	/**
	 * 获取保险计算项目参数页面提交数据（Get Insurance Compute Item Param）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap setGetInsuranceComputeItemParam(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		return paramMap ;
	}
	
	/**
	 * 验证保险计算项目信息，是否可添加（check Add Insurance Compute Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddInsuranceComputeItemInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return this.insuranceComputeItemDao.checkAddInsuranceComputeItemInfo(paramMap) ;
		
	}
	
	/**
	 * 验证保险计算项目参数信息，是否可添加（check Add Insurance Compute Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddInsuranceComputeItemParamInfo(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.insuranceComputeItemDao.checkAddInsuranceComputeItemParamInfo(paramMap) ;
		
	}
	
	/**
	 * 添加保险计算项目信息（add Insurance Compute Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addInsuranceComputeItemInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		try {
			this.insuranceComputeItemDao.addInsuranceComputeItemInfo(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 添加保险计算项目参数信息（add Insurance Compute Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addInsuranceComputeItemParamInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		
		return this.insuranceComputeItemDao.addInsuranceComputeItemParamInfo(paramMap) ;
	}
	
	/**
	 * 修改保险计算项目信息（update Insurance Compute Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateInsuranceComputeItemInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		try {
			this.insuranceComputeItemDao.updateInsuranceComputeItemInfo(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 修改保险计算项目参数信息（update Insurance Compute Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateInsuranceComputeItemParamInfo(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		try {
			this.insuranceComputeItemDao.updateInsuranceComputeItemParamInfo(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 验证保险计算项目信息，是否可删除（check Delete Insurance Compute Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeleteInsuranceComputeItemInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("TABLE_NAME", "PA_SUMMARY");
		
		return this.insuranceComputeItemDao.checkDeleteInsuranceComputeItemInfo(paramMap) ;
	}
	
	/**
	 * 验证保险计算项目参数信息，是否可删除（check Delete Insurance Compute Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkDeleteInsuranceComputeItemParamInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.insuranceComputeItemDao.checkDeleteInsuranceComputeItemParamInfo(paramMap) ;
	}
	/**
	 * 删除保险计算项目信息（delete Insurance Compute Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteInsuranceComputeItemInfo(HttpServletRequest request){
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			this.insuranceComputeItemDao.deleteInsuranceComputeItemInfo(paramMap) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除保险计算项目参数信息（delete Insurance Compute Item Param Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteInsuranceComputeItemParamInfo(HttpServletRequest request){
		
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.insuranceComputeItemDao.deleteInsuranceComputeItemParamInfo(paramMap) ;
		
		
	}

	/**
	 * 修改保险计算项目计算顺序（update Insurance Compute Item Info CalOrder）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateInsuranceComputeItemInfoCalOrder(HttpServletRequest request) {
		// 页面提交的JSON信息
		String jsonString = request.getParameter("jsonData") ;

		List<LinkedHashMap<String, Object>> summaryItemList = ObjectBindUtil.getRequestJsonData(jsonString) ;
		
		return this.insuranceComputeItemDao.updateInsuranceComputeItemInfoCalOrder(summaryItemList) ;
	}
	
	
	/**
	 * 修改保险计算项目计算顺序（update ICInfo By CalcuOrder）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateICInfoByCalcuOrder(HttpServletRequest request,int type,String param_no,String calcu_order) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		paramMap.put("PARAM_NO", param_no) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		//如果type=1 是up  那么CALCU_ORDER的值-1
		if(type==1){
			int order= Integer.parseInt(calcu_order);
			int calorder=order-1;
			paramMap.put("ORDER", order) ;
			paramMap.put("CALORDER", calorder) ;
		}
		
		//如果type=0 是down  那么CALCU_ORDER的值+1
		if(type==0){
			int order= Integer.parseInt(calcu_order);
			int calorder=order+1;
			paramMap.put("ORDER", order) ;
			paramMap.put("CALORDER", calorder) ;
		}
		
		this.insuranceComputeItemDao.updateICInfoByCalcuOrder(paramMap) ;
		
		return 1;
	}
	
	/**
	 * 修改保险计算项目计算顺序（update ICInfo By ItemNo）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateICInfoByItemNo(HttpServletRequest request,int type,String param_no,String calcu_order) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		paramMap.put("PARAM_NO", param_no) ;
		
		if(type==1){
			int order= Integer.parseInt(calcu_order);
			int calorder=order-1;
			paramMap.put("ORDER", order) ;
			paramMap.put("CALORDER", calorder) ;
		}
		
		//如果type=0 是down  那么CALCU_ORDER的值+1
		if(type==0){
			int order= Integer.parseInt(calcu_order);
			int calorder=order+1;
			paramMap.put("ORDER", order) ;
			paramMap.put("CALORDER", calorder) ;
		}
		
		this.insuranceComputeItemDao.updateICInfoByItemNo(paramMap) ;
		
		return 1;
	}
	
	
}
