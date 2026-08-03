package com.ait.ar.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: SummaryItemSer.java
 * @Description: implement Class SummaryItemSerImp.java
 * @Create date: 2012-1-13 上午10:02:57
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface SummaryItemSer {

	@SuppressWarnings("unchecked")
	public Object getSummaryItem(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getSummaryItemList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getSummaryItemByCpnyList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addSummaryItemInfo(HttpServletRequest request) ;
	
	/**
	 * 插入汇总项目信息(add SummaryItem Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addSummaryItemInfoAffirm(HttpServletRequest request,String cpny_id,String item_no) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int updateSummaryItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deleteSummaryItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int checkSummaryItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateSummaryParamItemInfoCalOrder(HttpServletRequest request) ;

	public int getSummaryItemCnt(HttpServletRequest request);

	public List getSummaryParamItemList(HttpServletRequest request);

	public int getSummaryParamItemCnt(HttpServletRequest request);

	public int addSummaryParamItemInfo(HttpServletRequest request);

	public Object getSummaryParamItem(HttpServletRequest request);

	public int updateSummaryParamItemInfo(HttpServletRequest request);

	public int deleteSummaryParamItemInfo(HttpServletRequest request);

	public int checkForItemDelete(HttpServletRequest request);

	public int checkForItemParamDelete(HttpServletRequest request);

	public int checkForItemParamUnique(HttpServletRequest request);
	public int checkForItemParam(HttpServletRequest request,String item_no,String cpny_id,String group_no);
	
	public List getSummaryParamItemList1(HttpServletRequest request);
	
	
	public int addShowSummaryParamItemList(HttpServletRequest request);
	/**
	 * 根据item_no查找和该项目已经mapping的记录删除掉
	 * @param object
	 * @return
	 * @throws SQLException 
	 */
	public int updateSummaryItemParamInfoAll(String item_no) throws Exception;
	
	/**
	 * 根据不同的参数来添加考勤代码（汇总项目参数）
	 * @param request
	 * @param cpny_id
	 * @param item_no
	 * @return
	 */
	public int addSummaryItemParamInfo(HttpServletRequest request, String cpny_id, String item_no);

	public int updateSummaryItemParamInfo(HttpServletRequest request, String cpny_id, String item_no);
}
