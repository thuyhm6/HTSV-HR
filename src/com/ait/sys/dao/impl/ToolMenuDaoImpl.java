package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.sys.dao.ToolMenuDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ToolMenuDaoImpl.java
 * @Description:
 * @Create date: 2012-3-21 上午11:27:01
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ToolMenuDaoImpl extends SqlMapClientSupport implements ToolMenuDao{	
	
	/**
	 * 取得按钮权限(get Tool Menu)
	 * @param Object
	 * @return Object
	 */
	@Override
	public Object getToolMenu(Object obj) {
		
		LinkedHashMap returnToolMenu = new LinkedHashMap() ;
		
		List toolMenuList = new ArrayList();
		
		try {
			
			toolMenuList = this.queryForList("sys.toolMenu.getToolMenu", obj);
			
			for (int i = 0; i < toolMenuList.size(); i++) {
				
				LinkedHashMap data = new LinkedHashMap() ;
				
				data = (LinkedHashMap) toolMenuList.get(i);

				if((data.get("SELECTR") != null ? data.get("SELECTR").toString() : "").equals("1")){
					
					returnToolMenu.put("SELECTR", "1");
					
				}
				
				if((data.get("INSERTR") != null ? data.get("INSERTR").toString() : "").equals("1")){
					
					returnToolMenu.put("INSERTR", "1");
					
				}

				if((data.get("UPDATER") != null ? data.get("UPDATER").toString() : "").equals("1")){
	
					returnToolMenu.put("UPDATER", "1");
	
				}

				if((data.get("DELETER") != null ? data.get("DELETER").toString() : "").equals("1")){
					
					returnToolMenu.put("DELETER", "1");
					
				}
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnToolMenu ;
	}

	@Override
	public Object getLinkMapByName(Map map, String sqlName) {
		try {
			return this.queryForList(sqlName, map);
		} catch (SQLException e) {
			return null;
		}
	}
}
