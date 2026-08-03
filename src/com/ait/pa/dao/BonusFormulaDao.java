package com.ait.pa.dao;

import java.util.List;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: BonusFormulaDao.java
 * @Description:
 * @Create date: 2012-5-31 上午10:46:20
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusFormulaDao {

	public Object getBonusFormulaInfo(Object object);

	@SuppressWarnings("unchecked")
	public List getBonusFormulaList(Object object);

	public int getBonusFormulaCnt(Object object);

	public int updateBnFormularByCalcuOrderOne(Object obj);

	public int updateBnFormularByCalcuOrderTwo(Object obj);

	public int updateBnFormularByCalcuOrderThree(Object obj);

	public int addBonusFormulaInfo(Object obj);

	public int updateBonusFormulaInfo(Object obj);

	public int deleteBonusFormulaInfo(Object obj);

	public int getBnFormularBySeq(Object obj);

	public int getBnFormularByItemNo(Object obj);

	public int updateBnFormularBySeq(Object obj);

}
