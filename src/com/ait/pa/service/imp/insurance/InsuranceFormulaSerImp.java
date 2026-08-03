package com.ait.pa.service.imp.insurance;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.BonusFormulaDao;
import com.ait.pa.dao.InsuranceFormulaDao;
import com.ait.pa.service.insurance.InsuranceFormulaSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceFormulaSerImp.java
 * @Description:
 * @Create date: 2012-1-17 下午03:15:15
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class InsuranceFormulaSerImp implements InsuranceFormulaSer {

	Logger logger = Logger.getLogger(InsuranceFormulaSerImp.class);
	
	@Autowired
	private InsuranceFormulaDao insuranceFormulaDao;
	
	@Autowired
	private BonusFormulaDao bonusFormulaDao;
	
	/**
	 * 获取保险计算信息（get Insurance Formula Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getInsuranceFormulaInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnObj = insuranceFormulaDao.getInsuranceFormulaInfo(paramMap) ;
		
		return returnObj ;
	}
	
	/**
	 * 获取保险计算公式（get Insurance Formula List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceFormulaList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnList = insuranceFormulaDao.getInsuranceFormulaList(paramMap) ;
		
		return returnList ;
	}
	
	/**
	 * 获取国际化后的保险计算公式（get Insurance Formula For CN List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceFormulaForCNList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnList = insuranceFormulaDao.getInsuranceFormulaForCNList(paramMap) ;
		
		return returnList ;
	}	
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateIsFormularByCalcuOrder(HttpServletRequest request,int type,String formular_no,String condition_seq,String item_no) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		paramMap.put("FORMULAR_NO", formular_no) ;
		paramMap.put("ITEM_NO", item_no) ;
		//如果type=1 是up  那么CALCU_ORDER的值+1
		if(type==1){
			int order= Integer.parseInt(condition_seq);
			int calorder=order+1;
			paramMap.put("ORDER", order) ;
			paramMap.put("CALORDER", calorder) ;
		}
		
		//如果type=0 是down  那么CALCU_ORDER的值-1
		if(type==0){
			int order= Integer.parseInt(condition_seq);
			int calorder=order-1;
			paramMap.put("ORDER", order) ;
			paramMap.put("CALORDER", calorder) ;
		}
		paramMap.put("CONDITIONSEQ", 0);

		this.insuranceFormulaDao.updateIsFormularByCalcuOrderOne(paramMap);
		this.insuranceFormulaDao.updateIsFormularByCalcuOrderTwo(paramMap);
		this.insuranceFormulaDao.updateIsFormularByCalcuOrderThree(paramMap);
		
		return 1;
	}
	/**
	 * 添加保险计算公式（add Insurance Formula Info）
	 * @param parameterObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addInsuranceFormulaInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;
		
		insuranceFormulaDao.addInsuranceFormulaInfo(paramMap) ;
		
		return 0 ;
	}
	
	/**
	 * 修改保险计算公式（update Insurance Formula Info）
	 * @param parameterObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updateInsuranceFormulaInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		
		insuranceFormulaDao.updateInsuranceFormulaInfo(paramMap) ;
		
		return 0 ;
	}
	
	/**
	 * 删除保险计算公式（delete Insurance Formula Info）
	 * @param parameterObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteInsuranceFormulaInfo(HttpServletRequest request){
		
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("TABLE_NAME", "IS_FORMULAR");
		paramMap.put("UPDATED_BY", admin.getPersonId());
		int item_no = bonusFormulaDao.getBnFormularByItemNo(paramMap);
		int deleteSeq = bonusFormulaDao.getBnFormularBySeq(paramMap);
		paramMap.put("DELETESEQ", deleteSeq);
		paramMap.put("ITEM_NO", item_no);
		insuranceFormulaDao.deleteInsuranceFormulaInfo(paramMap) ;
		bonusFormulaDao.updateBnFormularBySeq(paramMap);
		return 0 ;
	}

}
