package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.pa.dao.BonusInputItemParamDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusInputItemParamDaoImpl.java
 * @Description:BonusInputItemParamDaoImpl extends SqlMapClientSupport implements BonusInputItemParamDao
 * @Create date: 2012-1-16 上午10:03:39
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class BonusInputItemParamDaoImpl extends SqlMapClientSupport implements
		BonusInputItemParamDao {
	
	/**
	 * 添加奖金输入项目参数信息（Add a bonus item parameter information input）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addPaBonusInputItemParamInfo(Object obj) throws Exception{
		
		this.insert("pa.bonusInputItemParam.addPaBonusInputItemParamInfo",obj);
		
	}

	/**
	 * 检查奖金输入项目参数信息，是否可删除（Check bonus input item parameter information, whether to delete）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkDeletePaBonusInputItemParamInfo(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemParam.checkDeletePaBonusInputItemParamInfo",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 检查奖金输入项目参数信息，是否可添加（Check bonus input item parameter information, whether to add）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkAddPaBonusInputItemParamInfo(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemParam.checkAddPaBonusInputItemParamInfo",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 删除奖金输入项目参数信息（Delete bonus input item parameter information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deletePaBonusInputItemParamInfo(Object obj) throws Exception {
		
		this.delete("pa.bonusInputItemParam.deletePaBonusInputItemParamInfo",obj);
	}

	/**
	 * 获取奖金输入项目参数信息个数（Gets the bonus item parameter information input number）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPaBonusInputItemParamCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemParam.getPaBonusInputItemParamCnt",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 获取奖金输入项目参数信息（Gets the bonus item parameter information input）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaBonusInputItemParamInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap();

		List returnList = this.viewBonusInputItemParamList(obj);
		if (returnList.size() > 0) {
			returnObj = (LinkedHashMap) returnList.get(0);
		}

		return returnObj;
	}

	/**
	 * 查看奖金输入项目参数信息集合（View input item parameter information set bonus）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewBonusInputItemParamList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"pa.bonusInputItemParam.getPaBonusInputItemParamList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"pa.bonusInputItemParam.getPaBonusInputItemParamList",
						obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改奖金输入项目参数信息（Modified bonus input item parameter information）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updatePaBonusInputItemParamInfo(Object obj) throws Exception {
		
		this.update("pa.bonusInputItemParam.updatePaBonusInputItemParamInfo",obj);
		
	}

	/**
	 * 查看奖金输入项目参数信息集合（View input item parameter information set bonus）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewBonusInputItemParamList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.viewBonusInputItemParamList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 检查奖金输入项目参数信息（Check bonus item parameter information input）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkPaBonusInputItemParamByParamItemNo(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"pa.bonusInputItemParam.checkPaBonusInputItemParamByParamItemNo",
													obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
}
