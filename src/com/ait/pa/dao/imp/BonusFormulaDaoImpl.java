package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.BonusFormulaDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: BonusFormulaDaoImpl.java
 * @Description:
 * @Create date: 2012-1-17 下午03:20:05
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class BonusFormulaDaoImpl extends SqlMapClientSupport implements
		BonusFormulaDao {

	/**
	 * 取得奖金计算公式信息
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getBonusFormulaInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap();

		List returnList = this.getBonusFormulaList(obj);

		if (returnList.size() > 0) {
			returnObj = (LinkedHashMap) returnList.get(0);
		}

		return returnObj;
	}

	/**
	 * 取得所有奖金计算公式列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBonusFormulaList(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this.queryForList(
					"pa.bonusFormula.getBonusFormulaList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getBonusFormulaCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"pa.bonusFormula.getBonusFormulaCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 修改CALCU_ORDER ByCalcuOrder
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int updateBnFormularByCalcuOrderOne(Object obj) {

		try {
			this.update("pa.bonusFormula.updateBnFormularByCalcuOrderOne", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 修改CALCU_ORDER ByCalcuOrder
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int updateBnFormularByCalcuOrderTwo(Object obj) {

		try {
			this.update("pa.bonusFormula.updateBnFormularByCalcuOrderTwo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 修改CALCU_ORDER ByCalcuOrder
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int updateBnFormularByCalcuOrderThree(Object obj) {

		try {
			this.update("pa.bonusFormula.updateBnFormularByCalcuOrderThree",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 插入奖金公式
	 * 
	 * @param Object
	 * @return
	 */
	public int addBonusFormulaInfo(Object obj) {

		try {
			this.insert("pa.bonusFormula.addBonusFormulaInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 更新奖金公式
	 * 
	 * @param Object
	 * @return
	 */
	public int updateBonusFormulaInfo(Object obj) {

		try {
			this.update("pa.bonusFormula.updateBonusFormulaInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 删除奖金公式
	 * 
	 * @param Object
	 * @return
	 */
	public int deleteBonusFormulaInfo(Object obj) {

		try {
			this.delete("pa.bonusFormula.deleteBonusFormulaInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getBnFormularBySeq(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject(
							"pa.bonusFormula.getBnFormularBySeq", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getBnFormularByItemNo(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("pa.bonusFormula.getBnFormularByItemNo",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateBnFormularBySeq(Object obj) {
		try {
			this.update("pa.bonusFormula.updateBnFormularBySeq", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
