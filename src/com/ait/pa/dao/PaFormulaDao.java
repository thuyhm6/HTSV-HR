package com.ait.pa.dao;

import java.util.List;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaFormulaDao.java
 * @Description:
 * @Create date: 2012-5-31 上午10:43:23
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface PaFormulaDao {

	public Object getPaFormulaInfo(Object object) ;
	
	public Object getPaDayFormulaInfo(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaFormulaList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getPaDayFormulaList(Object object) ;
	
	public int updatePaFormularByCalcuOrderOne(Object obj);
	
	public int updatePaFormularByCalcuOrderTwo(Object obj);
	
	public int updatePaFormularByCalcuOrderThree(Object obj);

	public int updatePaDayFormularByCalcuOrderOne(Object obj);
	
	public int updatePaDayFormularByCalcuOrderTwo(Object obj);
	
	public int updatePaDayFormularByCalcuOrderThree(Object obj);
	
	public int addPaFormulaInfo(Object obj) ;
	
	public int addPaDayFormulaInfo(Object obj) ;
	
	public int updatePaFormulaInfo(Object obj) ;
	
	public int updatePaDayFormulaInfo(Object obj) ;
	
	public int deletePaFormulaInfo(Object obj) ;
	
	public int deletePaDayFormulaInfo(Object obj) ;
}
