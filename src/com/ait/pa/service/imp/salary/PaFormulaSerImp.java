package com.ait.pa.service.imp.salary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.BonusFormulaDao;
import com.ait.pa.dao.PaFormulaDao;
import com.ait.pa.service.salary.PaFormulaSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaFormulaSerImp.java
 * @Description:
 * @Create date: 2012-1-17 下午03:13:41
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class PaFormulaSerImp implements PaFormulaSer {

	Logger logger = Logger.getLogger(PaFormulaSerImp.class);
	
	@Autowired
	private PaFormulaDao paFormulaDao;
	
	@Autowired
	private BonusFormulaDao bonusFormulaDao;
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getPaFormulaInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnObj = paFormulaDao.getPaFormulaInfo(paramMap) ;
		
		return returnObj ;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getPaDayFormulaInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnObj = paFormulaDao.getPaDayFormulaInfo(paramMap) ;
		
		return returnObj ;
	}
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaFormulaList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnList = paFormulaDao.getPaFormulaList(paramMap) ;
		
		return returnList ;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaDayFormulaList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		returnList = paFormulaDao.getPaDayFormulaList(paramMap) ;
		
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
	public int updatePaFormularByCalcuOrder(HttpServletRequest request,int type,String formular_no,String condition_seq,String item_no) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()!=null?admin.getCpnyId().toString():"");
		paramMap.put("FORMULAR_NO", formular_no) ;
		paramMap.put("ITEM_NO", item_no) ;
		//paramMap.put("CALCU_ORDER", calcu_order) ;
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

		this.paFormulaDao.updatePaFormularByCalcuOrderOne(paramMap);
		this.paFormulaDao.updatePaFormularByCalcuOrderTwo(paramMap);
		this.paFormulaDao.updatePaFormularByCalcuOrderThree(paramMap);
		
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
	public int updatePaDayFormularByCalcuOrder(HttpServletRequest request,int type,String formular_no,String condition_seq,String item_no) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		paramMap.put("FORMULAR_NO", formular_no) ;
		paramMap.put("ITEM_NO", item_no) ;
		//paramMap.put("CALCU_ORDER", calcu_order) ;
		//如果type=1 是up  那么CALCU_ORDER的值-1
		if(type==1){
			int order= Integer.parseInt(condition_seq);
			int calorder=order-1;
			paramMap.put("ORDER", order) ;
			paramMap.put("CALORDER", calorder) ;
		}
		
		//如果type=0 是down  那么CALCU_ORDER的值+1
		if(type==0){
			int order= Integer.parseInt(condition_seq);
			int calorder=order+1;
			paramMap.put("ORDER", order) ;
			paramMap.put("CALORDER", calorder) ;
		}
		paramMap.put("CONDITIONSEQ", 0);

		this.paFormulaDao.updatePaDayFormularByCalcuOrderOne(paramMap);
		this.paFormulaDao.updatePaDayFormularByCalcuOrderTwo(paramMap);
		this.paFormulaDao.updatePaDayFormularByCalcuOrderThree(paramMap);
		
		return 1;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addPaFormulaInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;

		return paFormulaDao.addPaFormulaInfo(paramMap) ;
		
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addPaDayFormulaInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID()) ;

		return paFormulaDao.addPaDayFormulaInfo(paramMap) ;
		
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updatePaFormulaInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		return paFormulaDao.updatePaFormulaInfo(paramMap) ;
		
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updatePaDayFormulaInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		return paFormulaDao.updatePaDayFormulaInfo(paramMap) ;
		
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int deletePaFormulaInfo(HttpServletRequest request){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		//如果删除其中一个公式,那么condition_seq要相应减1
		paramMap.put("TABLE_NAME", "PA_FORMULAR");
		paramMap.put("UPDATED_BY", admin.getPersonId());
		int item_no = bonusFormulaDao.getBnFormularByItemNo(paramMap);
		int deleteSeq = bonusFormulaDao.getBnFormularBySeq(paramMap);
		paramMap.put("DELETESEQ", deleteSeq);
		paramMap.put("ITEM_NO", item_no);

		paFormulaDao.deletePaFormulaInfo(paramMap) ;
		
		bonusFormulaDao.updateBnFormularBySeq(paramMap);
		
		return 1;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int deletePaDayFormulaInfo(HttpServletRequest request){
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;

		return paFormulaDao.deletePaDayFormulaInfo(paramMap) ;
		
	}
}
