package com.ait.ar.dao;

import java.util.List;

import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArProgressDao.java
 * @Description: implement Class ArProgressDaoImpl.java
 * @Create date: 2012-2-12 下午12:04:58
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArProgressDao {
	
	@SuppressWarnings("unchecked")
	public List getArProgressList(Object object);
	@SuppressWarnings("unchecked")
	public List getArProgressList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public int getArProgressCnt(Object object);
	@SuppressWarnings("unchecked")
	public int updateArProgressInfo(Object object) ;

}
