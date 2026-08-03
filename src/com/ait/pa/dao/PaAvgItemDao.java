package com.ait.pa.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaBasicItemDao.java
 * @Description:
 * @Create date: 2012-2-7 下午08:13:04
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface PaAvgItemDao {

	public Object getPaBasicItemInfo(Object object);
	
	public Object getPaBasicItemDataInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemList(Object object);
	
	public int getPaBasicItemCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaBasicItemList(Object object, int currentPage, int pageSize);
	
	
	
}
