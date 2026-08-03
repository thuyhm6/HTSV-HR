package com.ait.ar.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AttendanceKeeperDao.java
 * @Description: implement Class AttendanceKeeperDaoImpl.java
 * @Create date: 2012-1-14 下午01:46:43
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArCardAssociateDao {
	
	@SuppressWarnings("unchecked")
	public List getCardAssociateList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getCardAssociateList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getCardAssociateCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public void updateCardAssociateInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public String checkCardValidity(List list);
}
