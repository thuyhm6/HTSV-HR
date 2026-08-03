package com.ait.pa.service.salary;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaFormulaSer.java
 * @Description:
 * @Create date: 2012-1-17 下午03:14:42
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface PaFormulaSer {
	
	public Object getPaFormulaInfo(HttpServletRequest request) ;
	
	public Object getPaDayFormulaInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaFormulaList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaDayFormulaList(HttpServletRequest request) ;
	
	public int updatePaFormularByCalcuOrder(HttpServletRequest request,int type,String formular_no,String condition_seq,String item_no);
	
	public int updatePaDayFormularByCalcuOrder(HttpServletRequest request,int type,String formular_no,String condition_seq,String item_no);
	
	public int addPaFormulaInfo(HttpServletRequest request) ;
	
	public int addPaDayFormulaInfo(HttpServletRequest request) ;
	
	public int updatePaFormulaInfo(HttpServletRequest request) ;
	
	public int updatePaDayFormulaInfo(HttpServletRequest request) ;
	
	public int deletePaFormulaInfo(HttpServletRequest request) ;
	
	public int deletePaDayFormulaInfo(HttpServletRequest request) ;

}
