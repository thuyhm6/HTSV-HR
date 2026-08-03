package com.ait.pa.service.imp.bonus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.BonusInputItemDao;
import com.ait.pa.service.bonus.BonusInputItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusInputItemSerImp.java
 * @Description:
 * @Create date: 2012-1-9 上午11:30:37
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class BonusInputItemSerImp implements BonusInputItemSer {

	Logger logger = Logger.getLogger(BonusInputItemSerImp.class);

	@Autowired
	private BonusInputItemDao bonusInputItemDao;
	
	/**
	 * 获取奖金输入项目个数（get the item number to get bonus）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPaBonusInputItemCnt(HttpServletRequest request) {

		int retrunInt = 0;

		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		retrunInt = bonusInputItemDao.getPaBonusInputItemCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 获取奖金输入项目集合（get the bonus items for collection）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaBonusInputItemList(HttpServletRequest request) {

		List retrunList = new ArrayList();

		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
			paramMap.put("BONUS_IMPORT_PARAMETER", request.getAttribute("BONUS_IMPORT_PARAMETER"));
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = bonusInputItemDao.getPaBonusInputItemList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = bonusInputItemDao.getPaBonusInputItemList(paramMap);
		}

		return retrunList;

	}

	/**
	 * 添加奖金输入项目信息（Added bonus get project information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addPaBonusInputItemInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.bonusInputItemDao.addPaBonusInputItemInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 检查将近输入项目信息，是否可添加（Checking almost get project information, whether to add）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddPaBonusInputItemInfo(HttpServletRequest request) {

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.bonusInputItemDao.checkAddPaBonusInputItemInfo(paramMap);
	}

	/**
	 * 检查奖金输入项目参数个数（Check the bonus get the item number of parameters）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkCountPaBonusInputItemByParamItemNo(
			HttpServletRequest request) {
	
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.bonusInputItemDao
				.checkCountPaBonusInputItemByParamItemNo(paramMap);
	}

	/**
	 * 删除奖金输入项目信息（Remove the bonus input project information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaBonusInputItemInfo(HttpServletRequest request) {
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
			this.bonusInputItemDao.deletePaBonusInputItemInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 获取奖金输入项目信息（get the project information to get bonus）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaBonusInputItemInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Object returnObj = new Object();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		returnObj = bonusInputItemDao.getPaBonusInputItemInfo(paramMap);

		return returnObj;
	}

	/**
	 * 修改奖金输入项目信息（get the project information to modify prize）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaBonusInputItemInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.bonusInputItemDao.updatePaBonusInputItemInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
