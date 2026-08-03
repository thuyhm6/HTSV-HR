package com.ait.ar.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: SummaryItemDao.java
 * @Description: implement Class SummaryItemDaoImpl.java
 * @Create date: 2012-1-13 上午10:08:06
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface SummaryItemDao {
	
	@SuppressWarnings("unchecked")
	public Object getSummaryItem(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getSummaryItemList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getSummaryItemByCpnyList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getSummaryItemByCpnyList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getSummaryItemList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public void addSummaryItemInfo(Object object) throws Exception;
	
	/**
	 * 插入汇总项目信息(add SummaryItem Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addSummaryItemInfoAffirm(Object obj) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateSummaryItemInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void deleteSummaryItemInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int checkSummaryItemInfo(Object object) ;
	
	public int getItemCnt(Map paramMap);

	public List getSummaryParamItemList(Map paramMap, int pageNum,
			int numPerPage);

	public List getSummaryParamItemList(Map paramMap);

	public int getItemParamCnt(Map paramMap);
	
	@SuppressWarnings("unchecked")
	public void addSummaryParamItemInfo(LinkedHashMap paramMap) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateSummaryParamItem(LinkedHashMap paramMap) throws Exception;

	public Object getSummaryParamItem(Map paramMap);

	@SuppressWarnings("unchecked")
	public void updateSummaryParamItemInfo(LinkedHashMap paramMap) throws Exception;

	@SuppressWarnings("unchecked")
	public void deleteSummaryParamItemInfo(LinkedHashMap paramMap) throws Exception;

	public int updateSummaryParamItemInfoCalOrder(List<LinkedHashMap> list);

	public int checkForItemDelete(LinkedHashMap paramMap);

	public int checkForItemParamDelete(LinkedHashMap paramMap);

	public int checkForItemParamUnique(LinkedHashMap paramMap);
	
	public int checkForItemParam(LinkedHashMap paramMap);
	
	public List getSummaryParamItemList1(Map paramMap);
	
	public void addShowSummaryParamItemList(Map paramMap) throws Exception;
	
	/**
	 * 根据item_no删除所有记录
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateSummaryItemParamInfoAll(String item_no) throws Exception;
}
