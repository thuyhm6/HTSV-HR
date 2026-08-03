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
import com.ait.pa.dao.InsuranceInputItemDao;
import com.ait.pa.dao.InsuranceResultDao;
import com.ait.pa.dao.PaResultDao;
import com.ait.pa.service.insurance.InsuranceResultSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceResultSerImp.java
 * @Description:
 * @Create date: 2012-2-17 下午02:51:25
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class InsuranceResultSerImp implements InsuranceResultSer {

	Logger logger = Logger.getLogger(InsuranceResultSerImp.class);
	
	@Autowired
	private InsuranceResultDao insuranceResultDao ;
	@Autowired
	private PaResultDao paResultDao ;
	
	@Autowired
	private InsuranceComputeItemDao insuranceComputeItemDao ;
	
	@Autowired
	private InsuranceInputItemDao insuranceInputItemDao ;
	
	/**
	 * 获取保险结果所需的所有项目（get Insurance Result All Item）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map getInsuranceResultAllItem(HttpServletRequest request) throws Exception{

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("PERSON_ID", admin.getPersonId() !=null ? admin.getPersonId() : "");
		paramMap.put("USERNAME", admin.getPersonId() !=null ? admin.getUsername() : "");
		paramMap.put("FUNCTIONFLAG", "3");
		String distinguish=null;
		String menuNo = StringUtil.checkNull(request.getParameter("menuNo"));
		//if(menuNo.equals("2366")){
		//	distinguish="1";
		//}else if(menuNo.equals("123499")){
		//	distinguish="2";
		//}
		distinguish = menuNo;
		paramMap.put("DISTINGUISH", distinguish);
		//计算项目
		List insuranceComputeItemList = this.insuranceComputeItemDao.getInsuranceComputeItemParamList(paramMap);
		//输入项目
		List insuranceInputItemList = this.insuranceInputItemDao.getInsuranceInputItemParamList(paramMap);
		
		//人事项目
		paramMap.put("TABLE_NAME", "PA_HR_V");
		List hrItemList = new ArrayList() ;
		LinkedHashMap tMap = new LinkedHashMap();
		tMap.put("DISTINCT_FIELD", "IS_MONTH");
		tMap.put("FIELD_NAME", TipMessage.getTipMessage(
				"pa.insurance.title.insuranceMonth", request)) ;
		LinkedHashMap gMap = new LinkedHashMap();
		gMap.put("DISTINCT_FIELD", "GIVE_DATE");
		//gMap.put("FIELD_NAME", "保险发放日");
		if(("ko").equals(admin.getLanguage().toString())){
			gMap.put("FIELD_NAME", "보험발급일") ;
		}else{
			gMap.put("FIELD_NAME", "保险发放日") ;
		}
		hrItemList.add(tMap) ;
		hrItemList.add(gMap);
		
		hrItemList.addAll(this.insuranceInputItemDao.getDistinctFieldList(paramMap));
		
		//考勤项目
		paramMap.put("TABLE_NAME", "PA_AR_V");
		List arItemList = this.insuranceInputItemDao.getDistinctFieldList(paramMap);
		
		//
		LinkedHashMap itemMap = new LinkedHashMap();
		itemMap.put("insuranceComputeItemList", insuranceComputeItemList);
		itemMap.put("insuranceInputItemList", insuranceInputItemList);
		itemMap.put("hrItemList", hrItemList);
		itemMap.put("arItemList", arItemList);
		itemMap.put("menuNo", menuNo);
		
		
		
		return itemMap;
		
	}
	
	/**
	 * 保险结算（insurance Balance）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String insuranceBalance(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		paramMap.put("SUPERVISOR_ID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		returnString = insuranceResultDao.insuranceBalance(paramMap) ;
		
		return returnString ;
	}
}
