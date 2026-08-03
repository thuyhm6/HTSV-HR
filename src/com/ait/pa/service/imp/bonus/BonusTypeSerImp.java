package com.ait.pa.service.imp.bonus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.BonusTypeDao;
import com.ait.pa.service.bonus.BonusTypeSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusTypeSerImp.java
 * @Description:
 * @Create date: 2012-1-13 下午04:12:32
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class BonusTypeSerImp implements BonusTypeSer {

	Logger logger = Logger.getLogger(BonusTypeSerImp.class);

	@Autowired
	private BonusTypeDao bonusTypeDao;
	/**
	 * 取得奖金类型信息（Get a bonus type information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getBonusTypeInfo(HttpServletRequest request) {
		Object returnObj = new Object();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		returnObj = bonusTypeDao.getBonusTypeInfo(paramMap);

		return returnObj;
	}
	
	/**
	 * 取出奖金类型集合(Remove bonus type LIST)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBonusTypeList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = this.setGetBonusTypeParam(request);

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = bonusTypeDao.getBonusTypeList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = bonusTypeDao.getBonusTypeList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 取出奖金类型个数（Remove bonus type number）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getBonusTypeCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = this.setGetBonusTypeParam(request);

		retrunInt = bonusTypeDao.getBonusTypeCnt(paramMap);

		return retrunInt;
	}
	
	/**
	 * 设置奖金类型参数（Set bonus type parameters）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap<String, Object> setGetBonusTypeParam(
			HttpServletRequest request) {

		// 页面提交数据
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request, "seach_");

		return paramMap;
	}

	/**
	 * 添加奖金类型信息（Added bonus type information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addBonusTypeInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.bonusTypeDao.addBonusTypeInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;

	}

	/**
	 * 修改奖金类型信息（Modified bonus type information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateBonusTypeInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.bonusTypeDao.updateBonusTypeInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 删除奖金类型信息（Delete bonus type information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteBonusTypeInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
			this.bonusTypeDao.deleteBonusTypeInfo(paramMap);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;

	}

	/**
	 * 检查奖金类型信息，是否可删除（Check bonus type information, whether to delete）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeleteBonusTypeInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		return this.bonusTypeDao.checkDeleteBonusTypeInfo(paramMap);

	}

	/**
	 * 检查奖金类型信息，是否可添加（Check bonus type information, whether to add）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddBonusTypeByTypeId(HttpServletRequest request) {

		int returnInt = 0;

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);

		try {
			returnInt = this.bonusTypeDao.checkAddBonusTypeByTypeId(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 获得奖金类型参数信息（Get bonus type parameter information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getBonusTypeParamInfo(HttpServletRequest request) {
		Object returnObj = new Object();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		returnObj = bonusTypeDao.getBonusTypeParamInfo(paramMap);

		return returnObj;
	}

	/**
	 * 获得奖金类型参数集合（Get bonus type parameter set）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBonusTypeParamList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = this.setGetBonusTypeParam(request);
		
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = bonusTypeDao.getBonusTypeParamList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = bonusTypeDao.getBonusTypeParamList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 获得奖金类型参数集合个数（Get bonus type parameter set number）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getBonusTypeParamCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = this.setGetBonusTypeParam(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunInt = bonusTypeDao.getBonusTypeParamCnt(paramMap);

		return retrunInt;
	}

	/**
	 * 添加奖金类型参数信息（Added bonus type parameter information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addBonusTypeParamInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.bonusTypeDao.addBonusTypeParamInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 修改奖金类型参数信息（Modified bonus type parameter information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateBonusTypeParamInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		paramMap.put("UPDATED_BY", admin.getAdminID());
		try {
			this.bonusTypeDao.updateBonusTypeParamInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 删除奖金类型参数信息（Delete bonus type parameter information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteBonusTypeParamInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
			this.bonusTypeDao.deleteBonusTypeParamInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1; 
	}

	/**
	 * 检查奖金类型参数信息，是否可添加（Check bonus type parameter information, whether to add）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkAddBonusTypeParamInfo(HttpServletRequest request) {

		int returnInt = 0;

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);

		try {
			returnInt = this.bonusTypeDao.checkAddBonusTypeParamInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnInt;
	}
}
