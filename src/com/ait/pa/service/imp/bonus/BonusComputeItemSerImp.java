package com.ait.pa.service.imp.bonus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.BonusComputeItemDao;
import com.ait.pa.service.bonus.BonusComputeItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusComputeItemSerImp.java
 * @Description:
 * @Create date: 2012-1-13 下午05:11:07
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class BonusComputeItemSerImp implements BonusComputeItemSer {

	Logger logger = Logger.getLogger(BonusComputeItemSerImp.class);

	@Autowired
	private BonusComputeItemDao bonusComputeItemDao;

	/**
	 * 获得奖金计算项目信息（Get bonus calculation project information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getBonusComputeItemInfo(HttpServletRequest request) {
		Object returnObj = new Object();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		returnObj = bonusComputeItemDao.getBonusComputeItemInfo(paramMap);

		return returnObj;
	}

	/**
	 * 获得奖金计算项目集合（Get bonus calculation item set）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBonusComputeItemList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = this.setGetBonusComputeItemParam(request);

		paramMap.put("CPNY_ID", admin.getCpnyId());

		if (paramMap.get("page") != null && paramMap.get("pagesize") != null) {
			retrunList = bonusComputeItemDao.getBonusComputeItemList(paramMap,
					NumberUtils.parseNumber(ObjectUtils.toString(paramMap
							.get("page")), Integer.class), NumberUtils
							.parseNumber(ObjectUtils.toString(paramMap
									.get("pagesize")), Integer.class));
		} else {
			retrunList = bonusComputeItemDao.getBonusComputeItemList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 获得奖金计算项目个数（Get bonus calculation item number）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getBonusComputeItemCnt(HttpServletRequest request) {
		int retrunInt = 0;

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = this.setGetBonusComputeItemParam(request);

		paramMap.put("CPNY_ID", admin.getCpnyId());

		retrunInt = bonusComputeItemDao.getBonusComputeItemCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 设置奖金计算项目参数（Setting calculation of bonus item parameters）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap setGetBonusComputeItemParam(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		return paramMap;
	}

	/**
	 * 检查奖金计算项目信息，是否可删除（Check calculation of bonus item information, whether to delete）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddBonusComputeItemInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.bonusComputeItemDao.checkAddBonusComputeItemInfo(paramMap);

	}

	/**
	 * 添加奖金计算项目信息（Added bonus calculation project information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addBonusComputeItemInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.bonusComputeItemDao.addBonusComputeItemInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改奖金计算项目信息（Modification of calculating bonus item information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateBonusComputeItemInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.bonusComputeItemDao.updateBonusComputeItemInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 检查奖金计算项目信息，是否可删除（Check calculation of bonus item information, whether to delete）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeleteBonusComputeItemInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return this.bonusComputeItemDao
				.checkDeleteBonusComputeItemInfo(paramMap);

	}

	/**
	 * 删除奖金计算项目信息（Delete bonus calculation project information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteBonusComputeItemInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
			this.bonusComputeItemDao.deleteBonusComputeItemInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 取出奖金计算项目参数集合（Remove bonus calculation item parameter set）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBonusComputeItemNoParamList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = this.setGetBonusComputeItemParam(request);

		paramMap.put("CPNY_ID", admin.getCpnyId());

		if (paramMap.get("page") != null && paramMap.get("pagesize") != null) {
			retrunList = bonusComputeItemDao.getBonusComputeItemList(paramMap,
					NumberUtils.parseNumber(ObjectUtils.toString(paramMap
							.get("page")), Integer.class), NumberUtils
							.parseNumber(ObjectUtils.toString(paramMap
									.get("pagesize")), Integer.class));
		} else {
			retrunList = bonusComputeItemDao
					.getBonusComputeItemNoParamList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 取得奖金计算项目参数个数（Get bonus calculation item parameter number）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getBonusComputeItemNoParamCnt(HttpServletRequest request) {
		int retrunInt = 0;

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = this.setGetBonusComputeItemParam(request);

		paramMap.put("CPNY_ID", admin.getCpnyId());

		retrunInt = bonusComputeItemDao.getBonusComputeItemNoParamCnt(paramMap);

		return retrunInt;
	}
}
