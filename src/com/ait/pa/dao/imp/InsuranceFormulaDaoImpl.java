package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.ait.pa.dao.InsuranceFormulaDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: InsuranceFormulaDaoImpl.java
 * @Description:
 * @Create date: 2012-1-17 下午03:15:54
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class InsuranceFormulaDaoImpl extends SqlMapClientSupport implements
		InsuranceFormulaDao {

	/**
	 * 取得保险计算公式信息(get Insurance Formula Info)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getInsuranceFormulaInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap();

		List returnList = this.getInsuranceFormulaList(obj);

		if (returnList.size() > 0) {
			returnObj = (LinkedHashMap) returnList.get(0);
		}

		return returnObj;
	}

	/**
	 * 取得所有保险计算公式列表(get Insurance Formula List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceFormulaList(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this.queryForList(
					"pa.insuranceFormula.getInsuranceFormulaList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取得所有保险计算公式国际化列表(get Insurance Formula For CNList)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getInsuranceFormulaForCNList(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this.queryForList(
					"pa.insuranceFormula.getInsuranceFormulaForCNList", obj);

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
	public int updateIsFormularByCalcuOrderOne(Object obj) {

		try {
			this.update("pa.insuranceFormula.updateIsFormularByCalcuOrderOne",
					obj);
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
	public int updateIsFormularByCalcuOrderTwo(Object obj) {

		try {
			this.update("pa.insuranceFormula.updateIsFormularByCalcuOrderTwo",
					obj);
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
	public int updateIsFormularByCalcuOrderThree(Object obj) {

		try {
			this.update(
					"pa.insuranceFormula.updateIsFormularByCalcuOrderThree",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 插入保险公式 (add Insurance Formula Info)
	 * 
	 * @param Object
	 * @return
	 */
	public int addInsuranceFormulaInfo(Object obj) {

		try {
			this.insert("pa.insuranceFormula.addInsuranceFormulaInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 更新保险公式 (update Insurance Formula Info)
	 * 
	 * @param Object
	 * @return
	 */
	public int updateInsuranceFormulaInfo(Object obj) {

		try {
			this.update("pa.insuranceFormula.updateInsuranceFormulaInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 删除保险公式 (delete Insurance Formula Info)
	 * 
	 * @param Object
	 * @return
	 */
	public int deleteInsuranceFormulaInfo(Object obj) {

		try {
			this.delete("pa.insuranceFormula.deleteInsuranceFormulaInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

}
