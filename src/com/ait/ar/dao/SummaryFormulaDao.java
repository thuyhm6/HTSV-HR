package com.ait.ar.dao;

import java.util.List;

import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: SummaryFormulaDao.java
 * @Description: implement Class SummaryFormulaDaoImpl.java
 * @Create date: 2012-1-13 上午10:46:07
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface SummaryFormulaDao {
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaItemList(Object obj) ;
	@SuppressWarnings("unchecked")
	public List getsummaryFormulaList(Object obj) ;
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaItemToCN() ;
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaAR_STA_ITEM() ;
	@SuppressWarnings("unchecked")
	public List getPersonBasicInfo(Object object) ;
	@SuppressWarnings("unchecked")
	public List getItemList(Object object);
	@SuppressWarnings("unchecked")
	public int addFormulaItem(Object object);
	@SuppressWarnings("unchecked")
	public Object getFormulaInfo(Object object);
	@SuppressWarnings("unchecked")
	public int updateFormulaItem(Object object);
	@SuppressWarnings("unchecked")
	public int deleteFormulaInfo(Object object);
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaItemToInter(Object obj);
}
