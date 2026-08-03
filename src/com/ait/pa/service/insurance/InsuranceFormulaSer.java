package com.ait.pa.service.insurance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InsuranceFormulaSer.java
 * @Description:
 * @Create date: 2012-2-17 下午02:35:59
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface InsuranceFormulaSer {
	
	public Object getInsuranceFormulaInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getInsuranceFormulaList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getInsuranceFormulaForCNList(HttpServletRequest request) ;
	
	public int updateIsFormularByCalcuOrder(HttpServletRequest request,int type,String formular_no,String condition_seq,String item_no);
	
	public int addInsuranceFormulaInfo(HttpServletRequest request) ;
	
	public int updateInsuranceFormulaInfo(HttpServletRequest request) ;
	
	public int deleteInsuranceFormulaInfo(HttpServletRequest request) ;
	
}
