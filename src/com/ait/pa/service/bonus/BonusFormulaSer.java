package com.ait.pa.service.bonus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusFormulaSer.java
 * @Description:
 * @Create date: 2012-1-17 下午03:20:13
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface BonusFormulaSer {

	public Object getBonusFormulaInfo(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getBonusFormulaList(HttpServletRequest request);

	public int getBonusFormulaCnt(HttpServletRequest request);
	
	public int updateBnFormularByCalcuOrder(HttpServletRequest request,int type,String formular_no,String condition_seq,String item_no);

	public int addBonusFormulaInfo(HttpServletRequest request);

	public int updateBonusFormulaInfo(HttpServletRequest request);

	public int deleteBonusFormulaInfo(HttpServletRequest request);

}
