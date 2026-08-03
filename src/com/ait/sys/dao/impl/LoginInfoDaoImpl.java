package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.sys.dao.LoginInfoDao;
import com.ait.web.util.SqlMapClientSupport;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName LoginInfoDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:21:04
 * @version 5.0
 *
 */
@Repository
public class LoginInfoDaoImpl extends SqlMapClientSupport implements LoginInfoDao {
	
	/**
	 * 取得所有登陆用户列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getLoginInfoList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getLoginInfoList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	@Override
	public int getLoginInfoCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.loginInfo.getLoginInfoCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 取得所有登陆用户列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getLoginInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.loginInfo.getLoginInfoList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("sys.loginInfo.getLoginInfoList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
}
