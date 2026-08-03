package com.ait.pa.service.imp.bonus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.BonusFormulaDao;
import com.ait.pa.service.bonus.BonusFormulaSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusFormulaSerImp.java
 * @Description:
 * @Create date: 2012-1-17 下午03:18:54
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class BonusFormulaSerImp implements BonusFormulaSer {

	Logger logger = Logger.getLogger(BonusFormulaSerImp.class);

	@Autowired
	private BonusFormulaDao bonusFormulaDao;

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getBonusFormulaInfo(HttpServletRequest request) {
		Object returnObj = new Object();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		returnObj = bonusFormulaDao.getBonusFormulaInfo(paramMap);

		return returnObj;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBonusFormulaList(HttpServletRequest request) {
		List returnList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		returnList = bonusFormulaDao.getBonusFormulaList(paramMap);

		return returnList;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getBonusFormulaCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = this.setGetBonusComputeItemParam(request);

		retrunInt = bonusFormulaDao.getBonusFormulaCnt(paramMap);

		return retrunInt;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateBnFormularByCalcuOrder(HttpServletRequest request,int type,String formular_no,String condition_seq,String item_no) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
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

		this.bonusFormulaDao.updateBnFormularByCalcuOrderOne(paramMap);
		this.bonusFormulaDao.updateBnFormularByCalcuOrderTwo(paramMap);
		this.bonusFormulaDao.updateBnFormularByCalcuOrderThree(paramMap);
		
		return 1;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addBonusFormulaInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());

		bonusFormulaDao.addBonusFormulaInfo(paramMap);

		return 0;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateBonusFormulaInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getPersonId());

		bonusFormulaDao.updateBonusFormulaInfo(paramMap);

		return 0;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int deleteBonusFormulaInfo(HttpServletRequest request) {
		
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		//如果删除其中一个公式,那么condition_seq要相应减1
		paramMap.put("TABLE_NAME", "BN_FORMULAR");
		paramMap.put("UPDATED_BY", admin.getPersonId());
		int item_no = bonusFormulaDao.getBnFormularByItemNo(paramMap);
		int deleteSeq = bonusFormulaDao.getBnFormularBySeq(paramMap);
		paramMap.put("DELETESEQ", deleteSeq);
		paramMap.put("ITEM_NO", item_no);
		bonusFormulaDao.deleteBonusFormulaInfo(paramMap);
		bonusFormulaDao.updateBnFormularBySeq(paramMap);
		
		return 0;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap setGetBonusComputeItemParam(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");

		return paramMap;
	}
	
}
