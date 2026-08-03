package com.ait.pa.service.imp.bonus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.BonusComputeItemParamDao;
import com.ait.pa.service.bonus.BonusComputeItemParamSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusComputeItemParamSerImp.java
 * @Description:
 * @Create date: 2012-4-10 下午05:35:11
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class BonusComputeItemParamSerImp implements BonusComputeItemParamSer {

	Logger logger = Logger.getLogger(BonusComputeItemSerImp.class);

	@Autowired
	private BonusComputeItemParamDao bonusComputeItemParamDao;

	@SuppressWarnings("unchecked")
	public Object getBonusComputeItemParamInfo(HttpServletRequest request) {
		Object returnObj = new Object();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		returnObj = bonusComputeItemParamDao
				.getBonusComputeItemParamInfo(paramMap);

		return returnObj;
	}

	@SuppressWarnings("unchecked")
	public List getBonusComputeItemParamList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = this.setGetBonusComputeItemParam(request);
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		retrunList = bonusComputeItemParamDao.getBonusComputeItemParamList(paramMap);
	

		return retrunList;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 
	@SuppressWarnings("unchecked")
	public int getBonusComputeItemParamCnt(HttpServletRequest request) {
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = this.setGetBonusComputeItemParam(request);
		retrunInt = bonusComputeItemParamDao
				.getBonusComputeItemParamCnt(paramMap);
		return retrunInt;
	}*/

	@SuppressWarnings("unchecked")
	private LinkedHashMap setGetBonusComputeItemParam(HttpServletRequest request) {
		// 从session中取得登陆用户信息
		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		return paramMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int checkAddBonusComputeItemParamInfo(HttpServletRequest request) {

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.bonusComputeItemParamDao
				.checkAddBonusComputeItemParamInfo(paramMap);

	}

	@SuppressWarnings("unchecked")
	@Override
	public int addBonusComputeItemParamInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("PA_RELEVANCE_FLAG",request.getParameter("PA_RELEVANCE_FLAG"));
		System.out.println("===============是否关联工资计算==================="+request.getParameter("PA_RELEVANCE_FLAG"));
		paramMap.put("PA_BONUS_MONTH",request.getParameter("PA_MONTH"));
		System.out.println("===============关联工资月==================="+request.getParameter("PA_MONTH"));
		try {
			this.bonusComputeItemParamDao.addBonusComputeItemParamInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateBonusComputeItemParamInfo(HttpServletRequest request) {

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("PA_BONUS_MONTH", request.getParameter("PA_MONTH"));
		System.out.println("======================关联工资月======================="+request.getParameter("PA_MONTH"));
		this.bonusComputeItemParamDao.updateBonusComputeItemParamInfo(paramMap);
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int checkDeleteBonusComputeItemParamInfo(HttpServletRequest request) {
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		// paramMap.put("", returnObj.)

		return this.bonusComputeItemParamDao
				.checkDeleteBonusComputeItemParamInfo(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteBonusComputeItemParamInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
			this.bonusComputeItemParamDao.deleteBonusComputeItemParamInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateBonusItemParamCalcuOrder(HttpServletRequest request,
			int type, String param_no, String calcu_order) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("PARAM_NO", param_no);
		paramMap.put("CPNY_ID",admin.getCpnyId());
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

		this.bonusComputeItemParamDao.updateBonusItemParamCalcuOrder(paramMap);

		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateBonusItemParamCalcuOrderByParamNo(
			HttpServletRequest request, int type, String param_no,
			String calcu_order) {

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

		this.bonusComputeItemParamDao
				.updateBonusItemParamCalcuOrderByParamNo(paramMap);

		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int checkBonusComputeItemParamCanBeDeleted(HttpServletRequest request) {

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.bonusComputeItemParamDao
				.checkBonusComputeItemParamCanBeDeleted(paramMap);

	}
}
