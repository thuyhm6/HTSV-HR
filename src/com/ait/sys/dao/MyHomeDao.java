package com.ait.sys.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * Copyright: AIT (c) Company: AIT
 * 
 * @fileName MyHomeDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:22:59
 * @version 5.0
 * 
 */
public interface MyHomeDao {
	@SuppressWarnings("unchecked")
	public void deleteModel(List list);

	@SuppressWarnings("unchecked")
	public void insertModel(List list);

	@SuppressWarnings("unchecked")
	public List getModel(Object object);

	@SuppressWarnings("unchecked")
	public List getHomePurview(Object object);

	public Object getHomePage(Object object);

	public void updateApp(Object object);

	@SuppressWarnings("unchecked")
	public List getApp(String type, Object object);

	public void deletePage(Object object);

	public void updatePage(Object object);

	@SuppressWarnings("unchecked")
	public List getSyMenu(Object object);

	@SuppressWarnings("unchecked")
	public void deleteShort(List list);

	@SuppressWarnings("unchecked")
	public void insertShort(List list);

	@SuppressWarnings("unchecked")
	public List getShort(Object object);

	@SuppressWarnings("unchecked")
	public List getTipsMenu(Object object);
	public List getManualMenu(Object object) ;
	public int getTipsCnt(String sm, Object object);

	@SuppressWarnings("unchecked")
	public List getMenuCodeList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getTipsInMenu(Object object);
	
	@SuppressWarnings("unchecked")
	public List getInfoNotAffirm(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getInfoNotConfirm(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getOtInfoNotAffirm(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getOtInfoNotConfirm(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getPersonInfoNotConfirm(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getTransferOrderList(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getRewardNoAffirmInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getPunishNoAffirmInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public Map getSystemDate(Object object);
	
	@SuppressWarnings("unchecked")
	public void updateLoginInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addLoginInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public Map getSealControl(Object object);
}
