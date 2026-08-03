package com.ait.ar.dao;

import java.util.List;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAdjustRestDao.java
 * @Description: implement Class ArAdjustRestDaoImpl.java 
 * @Create date: 2012-3-23 下午04:34:38
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArAdjustRestDao {
	
	@SuppressWarnings("unchecked")
	public Object getArAdjustRestInfo(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getArAdjustRestList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getArAdjustRestList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getArAdjustRestCnt(Object object) ;
}
