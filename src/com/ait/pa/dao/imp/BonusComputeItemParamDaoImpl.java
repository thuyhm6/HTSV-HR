package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.pa.dao.BonusComputeItemParamDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: BonusComputeItemParamDaoImpl.java
 * @Description:
 * @Create date: 2012-5-31 上午10:44:47
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class BonusComputeItemParamDaoImpl extends SqlMapClientSupport implements
		BonusComputeItemParamDao {

	/**
	 * 取得奖金计算项目信息
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getBonusComputeItemParamInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap();

		List returnList = this.getBonusComputeItemParamList(obj);
		if (returnList.size() > 0) {
			returnObj = (LinkedHashMap) returnList.get(0);
		}

		return returnObj;
	}

	/**
	 * 取得所有奖金计算项目信息列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusComputeItemParamList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getBonusComputeItemParamList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有奖金计算项目信息列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusComputeItemParamList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this
						.queryForList(
								"pa.bonusComputeItemParam.getBonusComputeItemParamList",
								obj, currentPage, pageSize);
			} else {
				returnList = this
						.queryForList(
								"pa.bonusComputeItemParam.getBonusComputeItemParamList",
								obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有奖金计算项目信息总数
	 * 
	 * @param List
	 * @return
	 * 
	 *         public int getBonusComputeItemParamCnt(Object obj) { int
	 *         returnInt = 0;
	 * 
	 *         try { returnInt = NumberUtils .parseNumber( ObjectUtils
	 *         .toString(this .queryForObject(
	 *         "pa.bonusComputeItemParam.getBonusComputeItemParamCnt", obj)),
	 *         Integer.class); } catch (SQLException e) { e.printStackTrace(); }
	 * 
	 *         return returnInt; }
	 */

	/**
	 * 插入奖金计算项目信息
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public void addBonusComputeItemParamInfo(Object obj) throws Exception {

		this.insert("pa.bonusComputeItemParam.addBonusComputeItemParamInfo",
				obj);
	}

	/**
	 * 对插入奖金计算项目信息进行是否重复验证
	 * 
	 * @param List
	 * @return
	 */
	public int checkAddBonusComputeItemParamInfo(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusComputeItemParam.checkAddBonusComputeItemParamInfo",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 修改奖金计算项目信息
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int updateBonusComputeItemParamInfo(Object obj) {
		try {
			this.update(
					"pa.bonusComputeItemParam.updateBonusComputeItemParamInfo",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 验证删除奖金计算项目信息
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int checkDeleteBonusComputeItemParamInfo(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusComputeItemParam.checkDeleteBonusComputeItemParamInfo",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 删除奖金计算项目信息
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public void deleteBonusComputeItemParamInfo(Object obj) throws Exception {

		this.delete("pa.bonusComputeItemParam.deleteBonusComputeItemParamInfo",
				obj);
	}

	/**
	 * 修改CALCU_ORDER ByCalcuOrder
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int updateBonusItemParamCalcuOrder(Object obj) {

		try {
			this.update(
					"pa.bonusComputeItemParam.updateBonusItemParamCalcuOrder",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 修改CALCU_ORDER ByItemNo
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int updateBonusItemParamCalcuOrderByParamNo(Object obj) {

		try {
			this
					.update(
							"pa.bonusComputeItemParam.updateBonusItemParamCalcuOrderByParamNo",
							obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 验证该计算项目数据是否被计算项目参数引用
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int checkBonusComputeItemParamCanBeDeleted(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusComputeItemParam.checkBonusComputeItemParamCanBeDeleted",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
}