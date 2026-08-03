package com.ait.sys.dao;

import java.util.List;
import java.util.Map;
 
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ToolMenuDao.java
 * @Description: implement Class ToolMenuDaoImpl.java
 * @Create date: 2012-3-21 上午11:25:23
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ToolMenuDao {
	
	@SuppressWarnings("unchecked")
	public Object getToolMenu(Object obj) ;

	public Object getLinkMapByName(Map map, String sqlName);
}
