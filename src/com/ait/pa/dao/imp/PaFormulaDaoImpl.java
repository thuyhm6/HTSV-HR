package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ait.pa.dao.PaFormulaDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: PaFormulaDaoImpl.java
 * @Description:
 * @Create date: 2012-1-17 下午03:13:34
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PaFormulaDaoImpl extends SqlMapClientSupport implements
		PaFormulaDao {

	/**
	 * 取得工资计算公式信息
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaFormulaInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap();

		List returnList = this.getPaFormulaList(obj);

		if (returnList.size() > 0) {
			returnObj = (LinkedHashMap) returnList.get(0);
		}

		return returnObj;
	}

	/**
	 * 取得工资计算公式信息
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaDayFormulaInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap();

		List returnList = this.getPaDayFormulaList(obj);

		if (returnList.size() > 0) {
			returnObj = (LinkedHashMap) returnList.get(0);
		}

		return returnObj;
	}

	/**
	 * 取得所有工资计算公式列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaFormulaList(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this
					.queryForList("pa.paFormula.getPaFormulaList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有工资计算公式列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaDayFormulaList(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this.queryForList("pa.paFormula.getPaDayFormulaList",
					obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 修改CALCU_ORDER ByCalcuOrder
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public int updatePaFormularByCalcuOrderOne(Object obj) {

		try {
			this.update("pa.paFormula.updatePaFormularByCalcuOrderOne", obj);
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
	public int updatePaFormularByCalcuOrderTwo(Object obj) {

		try {
			this.update("pa.paFormula.updatePaFormularByCalcuOrderTwo", obj);
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
	public int updatePaFormularByCalcuOrderThree(Object obj) {

		try {
			this.update("pa.paFormula.updatePaFormularByCalcuOrderThree", obj);
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
	public int updatePaDayFormularByCalcuOrderOne(Object obj) {

		try {
			this.update("pa.paFormula.updatePaDayFormularByCalcuOrderOne", obj);
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
	public int updatePaDayFormularByCalcuOrderTwo(Object obj) {

		try {
			this.update("pa.paFormula.updatePaDayFormularByCalcuOrderTwo", obj);
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
	public int updatePaDayFormularByCalcuOrderThree(Object obj) {

		try {
			this.update("pa.paFormula.updatePaDayFormularByCalcuOrderThree",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 插入工资公式
	 * 
	 * @param Object
	 * @return
	 */
	public int addPaFormulaInfo(Object obj) {

		try {
			this.insert("pa.paFormula.addPaFormulaInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 插入工资公式
	 * 
	 * @param Object
	 * @return
	 */
	public int addPaDayFormulaInfo(Object obj) {

		try {
			this.insert("pa.paFormula.addPaDayFormulaInfo", obj);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * 更新工资公式
	 * 
	 * @param Object
	 * @return
	 */
	public int updatePaFormulaInfo(Object obj) {

		try {
			this.update("pa.paFormula.updatePaFormulaInfo", obj);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * 更新工资公式
	 * 
	 * @param Object
	 * @return
	 */
	public int updatePaDayFormulaInfo(Object obj) {

		try {
			this.update("pa.paFormula.updatePaDayFormulaInfo", obj);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * 删除工资公式
	 * 
	 * @param Object
	 * @return
	 */
	public int deletePaFormulaInfo(Object obj) {
		try {
			this.delete("pa.paFormula.deletePaFormulaInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 删除工资公式
	 * 
	 * @param Object
	 * @return
	 */
	public int deletePaDayFormulaInfo(Object obj) {
		try {
			this.delete("pa.paFormula.deletePaDayFormulaInfo", obj);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}
}
