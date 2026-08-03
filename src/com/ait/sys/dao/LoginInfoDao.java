package com.ait.sys.dao;

import java.util.List;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName LoginInfoDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:22:28
 * @version 5.0
 *
 */
public interface LoginInfoDao {
	
	@SuppressWarnings("unchecked")
	public List getLoginInfoList(Object object);
	
	public int getLoginInfoCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getLoginInfoList(Object object, int currentPage, int pageSize);
	
}
