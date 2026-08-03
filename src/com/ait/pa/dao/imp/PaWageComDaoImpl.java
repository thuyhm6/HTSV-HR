package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaAvgItemDao;
import com.ait.pa.dao.PaBasicItemDao;
import com.ait.pa.dao.PaWageComItemDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaBasicItemDaoImpl.java
 * @Description:
 * @Create date: 2012-2-7 下午08:23:33
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PaWageComDaoImpl extends SqlMapClientSupport implements PaWageComItemDao {
	
	
	/**
	 * 取得所有计算项目信息列表(get Pa Basic Item List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaBasicItemList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.basicItem.getPaBasicItemList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.basicItem.getPaBasicItemList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;

	}
	

	@Override
	public List getPaContrastList(LinkedHashMap<String, Object> paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.WageCom.getPaContrastList", paramMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getPaScreeningList(LinkedHashMap<String, Object> paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.WageCom.getPaScreeningList", paramMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getEmpType(String cpnyId) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.WageCom.getEmpType", cpnyId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getPaItem2(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		Map obj = ObjectBindUtil.getRequestParamData(request) ;
		 
		String item =(String) (obj.get("seach_ITEM_DISTINGUISH")!=null?obj.get("seach_ITEM_DISTINGUISH"):"");
		if (item.equals("")){
			return null;
		}
		String sql="";
		if(item.equals("2372")){
		sql = "select item_id id ,get_global_name(item_no,'zh') name from pa_item where item_no =(select item_no from pa_item_param  where param_no ='"+ obj.get("seach_PARAM_NO") +"') ";
		}else if (item.equals("507")) {
		sql = "select item_id id ,get_global_name(item_no,'zh') name from pa_basic_item where item_no =(select item_no from pa_basic_item_param  where param_no ='"+ obj.get("seach_PARAM_NO") +"')";
		}else{
		sql = "select param_item_id id ,get_global_name(param_item_no,'zh') name from pa_param_item where param_item_no =(select param_item_no from pa_param_item_param  where param_no  ='"+ obj.get("seach_PARAM_NO") +"')";
		}
		obj.put("sql", sql);
		try {
			returnList = this.queryForList("pa.WageCom.getPaItem", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}


	

}
