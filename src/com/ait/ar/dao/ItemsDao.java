package com.ait.ar.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ItemsDao.java
 * @Description: implement Class ItemsDaoImp.java
 * @Create date: 2012-1-7 下午04:15:56
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ItemsDao {
	
	@SuppressWarnings("unchecked")
	public Object getItem(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getItemList(Object object);
	
	/**
	 * 添加法人匹配参数时候的列表
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getItemListSelect(Object object);
	
	public int updateParamItemInfoCalOrder(List<LinkedHashMap> list);
	
	@SuppressWarnings("unchecked")
	public int getItemCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getItemList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public void addItemInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addItemInfoAffirm(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateItemInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void deleteItemInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getItemParameterList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getItemParamList(Object object);
	@SuppressWarnings("unchecked")
	public List getItemParamList2(Object object);
	@SuppressWarnings("unchecked")
	public List getItemParamListForKaoqin(Object object);
	
	@SuppressWarnings("unchecked")
	public List getItemParamList(Object object, int currentPage, int pageSize) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public int getItemParamCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getItemParamCnt();
	
	@SuppressWarnings("unchecked")
	public Object getItemParameter(Object object);
	
	@SuppressWarnings("unchecked")
	public void addItemParameterInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateItemParameter(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateItemParameterInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void deleteItemParameterInfo(Object object)throws Exception;
	
	/**
	 * 明细项目法人参数页面的删除
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteItemParamInfo(Object object)throws Exception;

	public int checkForItemDelete(Map paramMap);

	public int checkForItemParamDelete(Map paramMap);

	public int checkForItemParamUnique(Map paramMap);
	
	public int checkForItemParam(Map paramMap);
	
	public int checkItemInfoUnique(LinkedHashMap paramMap);
	
	@SuppressWarnings("unchecked")
	public List getDataTypeList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getApplyList(Object object);
	
	/**
	 * 根据item_no删除所有记录
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateItemParamInfoAll(String item_no) throws Exception;
	@SuppressWarnings("unchecked")
	public Object getParamItem(Object object);

	public int checkParamItemInfoUnique(LinkedHashMap paramMap);

	public void updateParamItemInfo(Object obj)throws Exception;
	
}
