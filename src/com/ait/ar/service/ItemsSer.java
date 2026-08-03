package com.ait.ar.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ItemsSer.java
 * @Description: implement Class ItemsSerImp.java
 * @Create date: 2012-1-7 下午04:12:55
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ItemsSer {

	@SuppressWarnings("unchecked")
	public Object getItem(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getItemList(HttpServletRequest request) ;
	
	/**********
	 * 添加法人匹配的时候添加项目
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getItemListSelect(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
	public int updateParamItemInfoCalOrder(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getItemCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addItemInfo(HttpServletRequest request,String cpny_id,String item_no) ;
	
	@SuppressWarnings("unchecked")
	public int updateItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deleteItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getItemParameterList(HttpServletRequest request) ;
	
	/**
	 * 默认只是显示默认组（明细项目法人参数页面）
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getItemParamList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getItemParamList2(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getItemParamListForKaoqin(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getItemParamCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getItemParamCnt() ;
	
	@SuppressWarnings("unchecked")
	public Object getItemParameter(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addItemParameterInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateItemParameterInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deleteItemParameterInfo(HttpServletRequest request) ;
	
	/**
	 * 明细项目法人参数  删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteItemParamInfo(HttpServletRequest request) ;

	public List getAllItemList(HttpServletRequest request);

	public List getDynamicGroupList(HttpServletRequest request,ModelMap modelMap);

	public int checkForItemDelete(HttpServletRequest request);

	public int checkForItemParamDelete(HttpServletRequest request);

	public int checkForItemParamUnique(HttpServletRequest request);
	
	public int checkForItemParam(HttpServletRequest request,String cpny_id,String item_no,String group_no);
	
	public int checkItemInfoUnique(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getDataTypeList(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getApplyList(HttpServletRequest request) ;
	
	/**
	 * 根据item_no查找和该项目已经mapping的记录删除掉
	 * @param object
	 * @return
	 * @throws SQLException 
	 */
	public int updateItemParamInfoAll(String item_no) throws Exception;
	
	/**
	 * 根据不同的参数来添加考勤代码（明细项目参数）
	 * @param request
	 * @param cpny_id
	 * @param item_no
	 * @return
	 * @throws Exception 
	 */
	public int addItemParamInfo(HttpServletRequest request, String cpny_id, String item_no) throws Exception;
	
	public int updateItemParamInfo(HttpServletRequest request, String cpny_id, String item_no) throws Exception;

	public Object getParamItem(HttpServletRequest request);

	public int checkParamItemInfoUnique(HttpServletRequest request);

	public int updateParamItemInfo(HttpServletRequest request);

	public List getItemParamList_cpny(HttpServletRequest request);
}
